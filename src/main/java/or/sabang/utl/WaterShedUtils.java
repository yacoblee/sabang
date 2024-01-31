package or.sabang.utl;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supermap.analyst.spatialanalyst.ConversionAnalyst;
import com.supermap.analyst.spatialanalyst.ConversionAnalystParameter;
import com.supermap.analyst.spatialanalyst.SmoothMethod;
import com.supermap.analyst.terrainanalyst.HydrologyAnalyst;
import com.supermap.data.Charset;
import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetGrid;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EngineType;
import com.supermap.data.GeoPoint;
import com.supermap.data.GeoRegion;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.data.conversion.DataExport;
import com.supermap.data.conversion.ExportResult;
import com.supermap.data.conversion.ExportSetting;
import com.supermap.data.conversion.FileType;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.property.EgovPropertyService;
import or.sabang.sys.gis.service.AnalFileVO;

public class WaterShedUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(WaterShedUtils.class);
	
	/** 암호화서비스 */
	@Resource(name = "egovEnvCryptoService")
	EgovEnvCryptoService cryptoService;
	/** 프러퍼티서비스 */
	@Resource(name="propertiesService")
    protected EgovPropertyService propertyService;
	
	private Workspace workspace = null;
	private Datasources datasources = null;
	
	//database
	private Datasource datasource = null;
	private Datasets datasets = null;
	private DatasetVector dataset = null;
	private Recordset recordset = null;
	
	//udb
	private Datasource uDatasource = null;
	private Datasets uDatasets = null;
	private DatasetVector uDataset = null;
	private Recordset uRecordset = null;
	
	//memory udb
	private Datasource mDatasource = null;
	private Datasets mDatasets = null;
	
	private String processId = null;
	
	public WaterShedUtils() throws Exception {
		processId = SuperMapBasicUtils.getDatasetUuid();
		getConnectionInfo(processId);
	}

	public WaterShedUtils(String analId) throws Exception {
		if(analId != null) {
			processId = analId;
			getConnectionInfo(processId);
		}
	}
	
	private void getConnectionInfo(String uniqId) throws Exception{
		workspace = new Workspace();
		LOGGER.info("워크스페이스 생성 : ".concat(uniqId));
		WorkspaceConnectionInfo conn = new WorkspaceConnectionInfo();
		//String path = "";//SphUtil.getFilePath(this.item.getFileDefaultLocPath(), "smwu", this.item.getServerInfohash());
		conn.setType(WorkspaceType.SMWU);
		conn.setName("feis");
		conn.setServer(SuperMapBasicUtils.superMapStoragePath + SuperMapBasicUtils.superMapMidDir + File.separator + SuperMapBasicUtils.smwuFileNm);

		workspace.open(conn);
		LOGGER.debug("SMWU 파일 연결 : ".concat(uniqId));
		//작업공간을 열고 데이터소스 가져오기
		datasources = workspace.getDatasources();
		
		DatasourceConnectionInfo connectionInfo = null;
		
		if(datasources.contains(SuperMapBasicUtils.pGisAlias)) {
			datasource = datasources.get(SuperMapBasicUtils.pGisAlias);
			datasets = datasource.getDatasets();
			LOGGER.debug("(OLD)"+SuperMapBasicUtils.pGisAlias+" 연결 : ".concat(uniqId));
		}else {
			connectionInfo = new DatasourceConnectionInfo();
			
			String server = propertyService.getString("supermap.postgres.server");
			//String driver = propertyService.getString("Globals.postgres.DriverClassName");
			String database = propertyService.getString("supermap.postgres.database");
			//String alias = propertyService.getString("supermap.postgres.alias");
			
			//연결정보 입력하기
			connectionInfo.setEngineType(EngineType.PGGIS);
			connectionInfo.setServer(server);
			connectionInfo.setDatabase(database);
			connectionInfo.setUser(cryptoService.getUsername());
			connectionInfo.setPassword(cryptoService.getPassword());
			connectionInfo.setAlias(database);
			
			datasource = datasources.open(connectionInfo);
			datasets = datasource.getDatasets();
			LOGGER.debug("(NEW)"+SuperMapBasicUtils.pGisAlias+" 연결 : ".concat(uniqId));
		}
		
		if(datasources.contains("feis")) {
			uDatasource = datasources.get("feis");
			uDatasets = uDatasource.getDatasets();
			LOGGER.debug("feis udb 연결 : ".concat(uniqId));
		}
		
		if(uniqId != null) {
			connectionInfo = new DatasourceConnectionInfo();
			connectionInfo.setServer(":memory:");
			connectionInfo.setEngineType(EngineType.MEMORY);
			//connInfo.setReadOnly(readOnly);
			connectionInfo.setAlias("m".concat(uniqId));
			mDatasource = datasources.create(connectionInfo);
			mDatasets = mDatasource.getDatasets();
			LOGGER.debug("memory udb 연결 : ".concat(uniqId));
		}
	}
	
	/**
	 * 유역분석(WaterShed)
	 * @throws Exception
	 */
	public AnalFileVO waterShedProcessing(double x, double y, String lgstrCd, Map<String, Object> attr) throws Exception{
		LOGGER.info("유역분석 시작");
		AnalFileVO vo = null;
		//DatasetGrid grid = null;
		String analId = attr.get("anal_id").toString();
		String sdCd = lgstrCd.substring(0, 2);
		
		String directionGridNm = "tf_feis_flowdirection_".concat(sdCd);
		String watershedRaseterNm = "watershed".concat("_raster_").concat(analId);
		String watershedPolygonNm = "watershed".concat("_polygon_").concat(analId);
		String watershedPointNm = "watershed".concat("_point_").concat(analId);
		//String outputPath = "";
		try {
			//getConnectionInfo(analId);
			
			DatasetGrid directionGrid = (DatasetGrid)uDatasource.getDatasets().get(directionGridNm);//flow_direction은 feis udbx에서 가져오기
			Point2Ds point2ds = new Point2Ds();
			Point2D point2d = new Point2D(x,y);
			
			point2ds.add(point2d);
			
			HydrologyAnalyst.watershed(directionGrid, point2ds, mDatasource, watershedRaseterNm);
//			HydrologyAnalyst.watershed(directionGrid, point2ds, uDatasource, watershedRaseterNm);
			
			addRecordset(new GeoPoint(point2d),attr,"tf_feis_ecrtcnl");
			
			mRasterToVector(watershedRaseterNm,watershedPolygonNm,"tf_feis_watershed",attr);
//			uRasterToVector(watershedRaseterNm,watershedPolygonNm,"tf_feis_watershed",attr);
			
			String ecrtcnlQuery = "anal_id = '".concat(attr.get("anal_id").toString()).concat("'");
			
			copyPggisToMemoryUdb("tf_feis_ecrtcnl",watershedPointNm,ecrtcnlQuery);
//			copyPggisToUdb("tf_feis_ecrtcnl",watershedPointNm,ecrtcnlQuery);
			
			vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm("유역분석");
			vo.setAnalType("유역분석");
			vo.setMstId(Integer.valueOf(attr.get("mst_id").toString()));
			vo.setSldId(attr.get("sld_id").toString());
			vo.setCreatUser(attr.get("creat_user").toString());
			vo.setFileExtsn("zip");
			//레스터데이터는 삭제
//			if(deleted && uDatasets.contains(watershedRaseterNm)) {
//				uDatasets.delete(watershedRaseterNm);
//			}
			
			mExportUdbToFile(watershedPolygonNm, vo, "shp");
			mExportUdbToFile(watershedPointNm, vo, "shp");
//			uExportUdbToFile(watershedPolygonNm, vo, "shp");
//			uExportUdbToFile(watershedPointNm, vo, "shp");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return vo;
	}
	
	/**
	 * 유역면적 저장
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO waterShedDrawProcessing(AnalFileVO vo) throws Exception{
		LOGGER.info("유역면적 저장 시작");

		String mstId = String.valueOf(vo.getMstId());
		String sldId = vo.getSldId();
		String analId = vo.getAnalId();
		String creatUser = vo.getCreatUser();
		
		String waterShedDatasetName = "tf_feis_watershed";
		String waterShedCopyDatasetNm = "watershed".concat("_polygon_").concat(analId);
		String waterShedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		try {
			copyPggisToMemoryUdb(waterShedDatasetName,waterShedCopyDatasetNm,waterShedQuery);

			if(mDatasets.contains(waterShedCopyDatasetNm)) {
				vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
				vo.setOrignlFileNm("유역분석");
				vo.setAnalType("유역분석");
				vo.setMstId(Integer.valueOf(mstId));
				vo.setSldId(sldId);
				vo.setCreatUser(creatUser);
				vo.setFileExtsn("zip");
				
				mExportUdbToFile(waterShedCopyDatasetNm, vo, "shp");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return vo;
	}
	
	/**
	 * 레코드셋 추가
	 * @param geom
	 * @param attr
	 * @param vectorName
	 * @throws Exception
	 */
	public void addRecordset(Geometry geom, Map<String, Object> attr, String vectorName) throws Exception{
		try {
			if(datasets.contains(vectorName)) {
				dataset = (DatasetVector) datasets.get(vectorName);
				
				recordset = dataset.getRecordset(false, CursorType.DYNAMIC);
				recordset.addNew(geom, attr);
				recordset.update();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			if(geom != null) {
				geom.dispose();
			}
			recordset.dispose();
			recordset.close();
			dataset.close();
		}
		
	}
	
	/**
	 * 래스터를 벡터로 변환(memory udb)
	 * @param sourceDataset
	 * @param targetDatasetName
	 * @throws Exception
	 */
	public void mRasterToVector(String sourceDataset, String targetDatasetName, String saveDatasetName, Map<String, Object> saveAttr) throws Exception{
		try {
			Dataset sourceDs = null;
			double area = 0.0;
			int position = 0;
			int checkCnt = 0;
			if(mDatasets.contains(sourceDataset)) {
				sourceDs = (Dataset) mDatasets.get(sourceDataset);
				
				ConversionAnalystParameter parameter = new ConversionAnalystParameter();
				parameter.setSourceDataset(sourceDs);
				parameter.setTargetDatasource(mDatasource);
				parameter.setTargetDatasetName(targetDatasetName);
				parameter.setSmoothDegree(40);
				parameter.setNoValue(-9999.0);
				parameter.setSpecifiedValue(1);
				parameter.setSmoothMethod(SmoothMethod.BSPLINE);
				parameter.setTargetDatasetType(DatasetType.REGION);
				parameter.setValueFieldName("value");
				
				DatasetVector mDataset = ConversionAnalyst.rasterToVector(parameter);
				mDataset.smooth(40, false);
				
				Recordset mRecordset = mDataset.getRecordset(false, CursorType.DYNAMIC);
				
				if(mRecordset.getRecordCount() > 0) {
					if(mRecordset.getRecordCount() > 1) {
						mRecordset.moveFirst();
						
						while(!mRecordset.isEOF()) {
							GeoRegion region = (GeoRegion) mRecordset.getGeometry();
							System.out.println(region.getArea());
							
							if(area < region.getArea()) {
								area = region.getArea();
								position = checkCnt;
							}
							checkCnt++;
							mRecordset.moveNext();
						}
					}
					mRecordset.moveTo(position);
					copyUdbToPggisRcordset(mRecordset,saveDatasetName,saveAttr);
				}
				
//				mRecordset.close();
				mDataset.close();
			}else {
				LOGGER.error("rasterToVector Data가 존재하지 않습니다.");
				//return null;
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} 
	}
	
	/**
	 * 결과 레코드셋를 db 테이블로 복사
	 * @param udbDatasetName
	 * @param pggisDatasetName
	 * @throws Exception
	 */
	public void copyUdbToPggisRcordset(Recordset resultRecordSet ,String saveDatasetName, Map<String, Object> saveAttr) throws Exception{
		try {
			if(datasets.contains(saveDatasetName)) {
				
				if(resultRecordSet.getRecordCount() > 0) {
					
					dataset = (DatasetVector) datasets.get(saveDatasetName);
					recordset = dataset.getRecordset(false, CursorType.DYNAMIC);
					
					recordset.addNew(resultRecordSet.getGeometry(), saveAttr);
					recordset.update();
					//dataset.append(uRecordset);
					//dataset.close();
				}else {
					LOGGER.error("udb의 ".concat(resultRecordSet.getDataset().getName()).concat(" 레코드셋 정보가 없습니다."));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * Pggis 데이터셋의 조건값 결과를 Memory Udb에 데이터셋 생성
	 * @param targetDatasetNm
	 * @param resultDatasetNm
	 * @param resultQuery
	 * @throws Exception
	 */
	private void copyPggisToMemoryUdb(String targetDatasetNm, String resultDatasetNm, String resultQuery) throws Exception{
		LOGGER.debug("copyPggisToMemoryUdb 시작");
		if(datasets.contains(targetDatasetNm)) {
			LOGGER.debug("targetDatasetNm 존재 확인");
			dataset = (DatasetVector) datasets.get(targetDatasetNm);
			LOGGER.debug("dataset 체크");
			recordset = dataset.query(resultQuery,CursorType.STATIC);
			LOGGER.debug("recordset 체크");
			DatasetVector copyDatastVector = (DatasetVector) mDatasets.createFromTemplate(mDatasets.getAvailableDatasetName(resultDatasetNm),dataset);
			LOGGER.debug("copyDatastVector 템플릿 생성");
			copyDatastVector.append(recordset);
			LOGGER.debug("recordset append");
			//CoordSysTranslator.convert(copyDatastVector, getCustomPrj5179(5179), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
			
			copyDatastVector.close();
			LOGGER.debug("copyDatastVector closed");
			recordset.dispose();
			LOGGER.debug("recordset dispose");
			dataset.close();
			LOGGER.debug("dataset close");
		}
	}
	
	/**
	 * 메모리 udb에서 파일로 내보내기
	 * @param datasetName
	 * @param vo
	 * @param extension
	 * @throws Exception
	 */
	public void mExportUdbToFile(String datasetName, AnalFileVO vo, String extension) throws Exception{
		try {
			Dataset ds = null;
			FileType fType = null;
			if(mDatasets.contains(datasetName)) {
				if(extension.equals("tif")) {
					ds = (DatasetGrid) mDatasets.get(datasetName);
					fType = FileType.TIF;
				}else {
					ds = (DatasetVector) mDatasets.get(datasetName);
					fType = FileType.SHP;
				}
				
				File file = new File(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
				file.mkdirs();
				
				ExportSetting exportSetting = new ExportSetting();
				exportSetting.setOverwrite(true);

				exportSetting.setTargetFileCharset(Charset.UTF8);
				exportSetting.setTargetFileType(fType);
				exportSetting.setTargetFilePath(vo.getFileStreCours()+File.separator+vo.getStreFileNm()+File.separator+ds.getName()+".".concat(extension));
				exportSetting.setSourceData(ds);

				DataExport dataExport = new DataExport();
				dataExport.getExportSettings().add(exportSetting);
				ExportResult exportRslt = dataExport.run();
				
//				if(exportRslt.getSucceedSettings().length > 0) {
//					if(deleted) {
//						uDatasets.delete(datasetName);
//					}
//				}
				
				if(exportRslt.getFailedSettings().length > 0) {
					LOGGER.error("분석결과 파일저장을 실패하였습니다.");
				}
			}else {
				LOGGER.error("요청한 테이블 정보가 존재하지 않습니다.");
				throw new Exception("요청한 테이블 정보가 존재하지 않습니다.");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * 슈퍼맵 연결 닫기
	 * @param type
	 * @throws Exception
	 */
	public void closeConnection() throws Exception{
		if(recordset != null) {
			recordset.close();
			recordset.dispose();
			recordset = null;
		}
		if(dataset != null && !dataset.isDisposed()){
			dataset.close();
		}
		
		if(datasource != null && datasource.isOpened()){
			datasource.close();
			LOGGER.debug("pggis datasource closed.");
		}
		
		
		if(uRecordset != null) {
			uRecordset.close();
			uRecordset.dispose();
			uRecordset = null;
		}
		if(uDataset != null && !uDataset.isDisposed()){
			uDataset.close();
		}
		
		if(uDatasource != null && uDatasource.isOpened()){
			uDatasource.close();
			LOGGER.debug("udb datasource closed.");
		}
		
//		if(vRecordset != null) {
//			vRecordset.close();
//			vRecordset.dispose();
//			vRecordset = null;
//		}
//		if(vDataset != null && !vDataset.isDisposed()){
//			vDataset.close();
//		}
//		
//		if(vDatasource != null && vDatasource.isOpened()){
//			vDatasource.close();
//			LOGGER.debug("vworld datasource closed.");
//		}
		
		if(mDatasource != null && mDatasource.isOpened()) {
			mDatasource.close();
			LOGGER.debug("memory udb closed.");
		}
		
		if(workspace != null) {
			//workspace.save();
			//workspace.getDatasources().closeAll();
			workspace.close();
        	workspace.dispose();
		}
	}
}