package or.sabang.utl;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.supermap.analyst.spatialanalyst.BufferAnalyst;
import com.supermap.analyst.spatialanalyst.BufferAnalystParameter;
import com.supermap.analyst.spatialanalyst.BufferEndType;
import com.supermap.analyst.spatialanalyst.BufferRadiusUnit;
import com.supermap.analyst.spatialanalyst.ConversionAnalyst;
import com.supermap.analyst.spatialanalyst.ConversionAnalystParameter;
import com.supermap.analyst.spatialanalyst.RasterClip;
import com.supermap.analyst.spatialanalyst.SmoothMethod;
import com.supermap.analyst.spatialanalyst.VectorClip;
import com.supermap.data.BlockSizeOption;
import com.supermap.data.Charset;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetGrid;
import com.supermap.data.DatasetGridInfo;
import com.supermap.data.DatasetImage;
import com.supermap.data.DatasetImageInfo;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.DatasetVectorInfo;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EncodeType;
import com.supermap.data.EngineType;
import com.supermap.data.FieldInfo;
import com.supermap.data.FieldInfos;
import com.supermap.data.FieldType;
import com.supermap.data.GeoCompound;
import com.supermap.data.GeoLegend;
import com.supermap.data.GeoLine;
import com.supermap.data.GeoMap;
import com.supermap.data.GeoPoint;
import com.supermap.data.GeoRectangle;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.GeoText;
import com.supermap.data.Geometry;
import com.supermap.data.PixelFormat;
import com.supermap.data.Point2D;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;
import com.supermap.data.TextPart;
import com.supermap.data.TextStyle;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.data.conversion.DataExport;
import com.supermap.data.conversion.DataImport;
import com.supermap.data.conversion.ExportResult;
import com.supermap.data.conversion.ExportSetting;
import com.supermap.data.conversion.FileType;
import com.supermap.data.conversion.ImportMode;
import com.supermap.data.conversion.ImportResult;
import com.supermap.data.conversion.ImportSettingSHP;
import com.supermap.layout.LayoutElements;
import com.supermap.layout.PrintFileType;
import com.supermap.mapping.Layer;
import com.supermap.mapping.LayerSettingGrid;
import com.supermap.mapping.LayerSettingImage;
import com.supermap.mapping.LayerSettingVector;
import com.supermap.mapping.ThemeGridRange;
import com.supermap.mapping.ThemeLabel;
import com.supermap.mapping.ThemeUnique;
import com.supermap.ui.Action;
import com.supermap.ui.MapLayoutControl;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;

public class VytFrdSupMapUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(VytFrdSupMapUtils.class);
	
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
	
	//vworld
	private Datasource vDatasource = null;
	private Datasets vDatasets = null;
	private Dataset vDataset = null;
	private Recordset vRecordset = null;
	
	//memory udb
	private Datasource mDatasource = null;
	private Datasets mDatasets = null;
	
	private MapLayoutControl mapLayoutcontrol = null;
	
	private Map<String, Object> attributes = null;
	private JSONObject returnLog = null;
	private int epsg = 0;
	
	private boolean deleted = true;
	
	/** 첨부파일 위치 지정  => globals.properties */
	private final String superMapStoragePath = EgovProperties.getProperty("Globals.fileStorePath.superMap");
    private final String downloadMidDir = "analysis";
    private final String shpMidDir = "shpDown";
    private final String superMapMidDir = "superMap";
	private final String smwuFileNm = "feis.smwu";
//	private final String pGisAlias = "feis2023";
	//개발
	private final String pGisAlias = "sabang";
	//운영
//	private final String pGisAlias = "feisdb";

	public VytFrdSupMapUtils() {}

	public VytFrdSupMapUtils(String analId) {
		if(analId != null) {
			String mapId = "Map_".concat(analId);
			try {
				getConnectionInfo(mapId);
			} catch (Exception e) {
				LOGGER.error("워크스페이스 생성 중 오류가 발생하였습니다.");
			}
		}
	}
	
	/**
	 * 슈퍼맵 연결정보 생성
	 * @return
	 * @throws Exception
	 */
	private void getConnectionInfo() throws Exception{
		workspace = new Workspace();
		LOGGER.info("워크스페이스 생성");
		WorkspaceConnectionInfo conn = new WorkspaceConnectionInfo();
		//String path = "";//SphUtil.getFilePath(this.item.getFileDefaultLocPath(), "smwu", this.item.getServerInfohash());
		conn.setType(WorkspaceType.SMWU);
		conn.setName("feis");
		conn.setServer(superMapStoragePath + superMapMidDir + File.separator + smwuFileNm);

		workspace.open(conn);
		
		//작업공간을 열고 데이터소스 가져오기
		datasources = workspace.getDatasources();
		
		if(datasources.contains(pGisAlias)) {
			datasource = datasources.get(pGisAlias);
			datasets = datasource.getDatasets();
		}
		
		if(datasources.contains("feis")) {
			uDatasource = datasources.get("feis");
			uDatasets = uDatasource.getDatasets();
		}
		
		if(datasources.contains("api.vworld.kr")) {
			vDatasource = datasources.get("api.vworld.kr");
			vDatasets = vDatasource.getDatasets();
		}
	}
	
	private void getConnectionInfo(String uniqId) throws Exception{
		workspace = new Workspace();
		LOGGER.info("워크스페이스 생성 : ".concat(uniqId));
		WorkspaceConnectionInfo conn = new WorkspaceConnectionInfo();
		//String path = "";//SphUtil.getFilePath(this.item.getFileDefaultLocPath(), "smwu", this.item.getServerInfohash());
		conn.setType(WorkspaceType.SMWU);
		conn.setName("feis");
		conn.setServer(superMapStoragePath + superMapMidDir + File.separator + smwuFileNm);

		workspace.open(conn);
		LOGGER.debug("SMWU 파일 연결 : ".concat(uniqId));
		//작업공간을 열고 데이터소스 가져오기
		datasources = workspace.getDatasources();
		
		DatasourceConnectionInfo connectionInfo = null;
		
		if(datasources.contains(pGisAlias)) {
			datasource = datasources.get(pGisAlias);
			datasets = datasource.getDatasets();
			LOGGER.debug("(OLD)"+pGisAlias+" 연결 : ".concat(uniqId));
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
			LOGGER.debug("(NEW)"+pGisAlias+" 연결 : ".concat(uniqId));
		}
		
		if(datasources.contains("feis")) {
			uDatasource = datasources.get("feis");
			uDatasets = uDatasource.getDatasets();
			LOGGER.debug("feis udb 연결 : ".concat(uniqId));
		}
		
		if(datasources.contains("api.vworld.kr")) {
			vDatasource = datasources.get("api.vworld.kr");
			vDatasets = vDatasource.getDatasets();
			LOGGER.debug("(OLD)vworld 연결 : ".concat(uniqId));
		}else {
			connectionInfo = new DatasourceConnectionInfo();
			//연결정보 입력하기
			connectionInfo.setEngineType(EngineType.OGC);
			connectionInfo.setServer("http://api.vworld.kr/req/wmts/1.0.0/199ECDF7-42EA-300D-B487-B7001CC9C0E6/WMTSCapabilities.xml");
			connectionInfo.setDriver("WMTS");
			connectionInfo.setAlias("api.vworld.kr");
			
			vDatasource = datasources.open(connectionInfo);
			vDatasets = vDatasource.getDatasets();
			LOGGER.debug("(NEW)vworld 연결 : ".concat(uniqId));
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
	 * 임도 대상지 위치 이미지 생성
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @param smid2 
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createFrdClipSatImg(String sldId,String smid,String analId, String captionNm, String smid2, String pageType) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String smidNum = null;
		if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
			smidNum = smid2;
		}else {
			smidNum = smid;
		}
		
		String mapId = "Map_frd_sat_".concat(analId);//맵 이름
		String frdDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);//임도테이블명 가져오기
		String frdQuery = "smid = ".concat(smidNum);//임도 where절
		String frdCopyDatasetNm = "frdlne_y_".concat(analId);//임도 복사 레이어명
		
		DatasetVector frdCopyDatasetVector = null;
		GeoRegion region = null;
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			copyPggisToMemoryUdb(frdDatasetNm,frdCopyDatasetNm,frdQuery);
			
			//데이터셋 자르기
			if(mDatasets.contains(frdCopyDatasetNm)) {
				frdCopyDatasetVector = (DatasetVector) mDatasets.get(frdCopyDatasetNm);
				
				if(SuperMapBasicUtils.coordSysTranslator(frdCopyDatasetVector,3857)) {
					Recordset mRecordset = frdCopyDatasetVector.query("",CursorType.STATIC);
					
					Rectangle2D bound = mRecordset.getBounds();
					
					double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*1.5 : bound.getHeight()*1.5;
					
					//height 값이랑 비교해서 큰값으로  :::승현
					newBound = new Rectangle2D();
					
					newBound.setTop(bound.getTop()+maxlength);
					newBound.setBottom(bound.getBottom()-maxlength);
					newBound.setLeft(bound.getLeft()-maxlength);
					newBound.setRight(bound.getRight()+maxlength);
					
					
					GeoRectangle geoRect = new GeoRectangle(newBound, 0);
					region = geoRect.convertToRegion();

					mRecordset.dispose();
//					frdCopyDatasetVector.close();
					
					vDataset = vDatasets.get("VworldSatellite");
					
					addLayerImage(vDataset,mapId,"vworld");
					addLayerSingleBandVector(frdCopyDatasetVector,mapId,frdCopyDatasetNm);
					
					vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
					vo.setOrignlFileNm("destloc");
					vo.setAnalType("destloc");
					vo.setSldId(sldId);
					if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
		            	vo.setRouteCode("02");
		            	vo.setSmid(smid2);
		            }else{
		            	vo.setRouteCode("01");
		            	vo.setSmid(smid);
		            }
					
					if(setFrdMapLayoutControl("satelliteTemplate",mapId,newBound)) {
						//saveImgNm = saveMapImage();
						AnalFileVO imgVo = saveMapImageToJpg(vo);
						list.add(imgVo);
						
						if(deleted) {
							deleteMap(mapId);
						}
					}
					frdCopyDatasetVector.close();
					
					mExportUdbToFile(frdCopyDatasetNm, vo, "shp");
					
					vo.setFileExtsn("zip");
					list.add(vo);
				}else {
					LOGGER.error("좌표변환에 실패하였습니다.");
				}
				
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}
	/**
	 * 임도, 행정구역 이미지 생성
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @param sggCodeOne 
	 * @param emdCodeOne 
	 * @param emdList 
	 * @param smid2 
	 * @param pageType 
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createFrdClipAdmImg(String sldId,String smid,String analId, String captionNm, double x, double y, String sggCodeOne, String emdCodeOne, List<EgovMap> emdList, String smid2, String pageType) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_frd_adm_".concat(analId);//맵 이름
		
		// 전체 읍면동 조회
		String emdAllDatasetNm = "tf_feis_emd";
		String emdAllDataQuery = "signgu_cod = '".concat(sggCodeOne)+"'";
		String emdAllCopyDatasetNm = "frdplgn_ea_emdAll_".concat(analId);	//emdAll
		DatasetVector emdAllCopyDatasetVector = null;
		
		
		// 해당 포인트의 읍면동 조회
		DatasetVector emdOneCopyDatasetVector = null;
		String emdOneDataQuery = "emd_code = '".concat(emdCodeOne)+"'";
		String emdOneCopyDatasetNm = "frdplgn_eo_emdOne_".concat(analId);		//emdOne
		
		// 센터 포인트 조회
		DatasetVector emdPntCopyDatasetVector = null;
		String emdPntCopyDatasetNm = "frd_one_admPnt_".concat(analId);	//emdAll
		
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
			LOGGER.info("슈퍼맵 연결 시작 : ".concat(analId));
			getConnectionInfo(analId);
			LOGGER.info("슈퍼맵 연결 완료 : ".concat(analId));
			//smwu프로젝트에 Map 생성하기
			LOGGER.info("Map 생성 시작 : ".concat(analId));
			createNewMap(mapId,3857);
			LOGGER.info("Map 생성 완료 : ".concat(analId));
			
			// 읍면동 All 폴리곤 데이터셋에서 시군구코드로 대상 조회
			LOGGER.info(emdAllDatasetNm+"데이터셋을 memory udb에 "+emdAllDataQuery+" 조건의 "+emdAllCopyDatasetNm+" 데이터셋으로 복사 : ".concat(analId));
			copyPggisToMemoryUdb(emdAllDatasetNm,emdAllCopyDatasetNm,emdAllDataQuery);
			LOGGER.info("데이터셋 복사 완료 : ".concat(analId));
			// 읍면동 One 폴리곤 데이터셋에서 시군구코드로 대상 조회
			LOGGER.info(emdAllDatasetNm+"데이터셋을 memory udb에 "+emdOneDataQuery+" 조건의 "+emdOneCopyDatasetNm+" 데이터셋으로 복사 : ".concat(analId));
			copyPggisToMemoryUdb(emdAllDatasetNm,emdOneCopyDatasetNm,emdOneDataQuery);
			LOGGER.info("데이터셋 복사 완료 : ".concat(analId));
			
			//센터포인트 데이터셋 생성
			LOGGER.info(emdPntCopyDatasetNm+"데이터셋을 생성 : ".concat(analId));
			createDataset("Point", emdPntCopyDatasetNm);
			LOGGER.info("데이터셋 생성 완료 : ".concat(analId));
			
			
			emdAllCopyDatasetVector = (DatasetVector) mDatasets.get(emdAllCopyDatasetNm);
			emdOneCopyDatasetVector = (DatasetVector) mDatasets.get(emdOneCopyDatasetNm);
			
			emdPntCopyDatasetVector = (DatasetVector) mDatasets.get(emdPntCopyDatasetNm);
			emdPntCopyDatasetVector.setPrjCoordSys(PrjCoordSys.fromEPSG(5186));
			Recordset pntRecordset = emdPntCopyDatasetVector.query("",CursorType.DYNAMIC);
			
			Point2D pt2d = new Point2D(x,y);
			Geometry geom = new GeoPoint(pt2d);
			
			pntRecordset.addNew(geom);
			pntRecordset.update();
			
			pntRecordset.dispose();
			pntRecordset.close();
			
			if(SuperMapBasicUtils.coordSysTranslator(emdAllCopyDatasetVector, 3857) && SuperMapBasicUtils.coordSysTranslator(emdOneCopyDatasetVector, 3857) && SuperMapBasicUtils.coordSysTranslator(emdPntCopyDatasetVector,3857)) {
				Recordset mRecordset = emdAllCopyDatasetVector.query("",CursorType.STATIC);
				
				if(mRecordset.getGeometry() != null) {
					Rectangle2D bound = mRecordset.getBounds();
					
					double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*0.01 : bound.getHeight()*0.01;
					
					newBound = new Rectangle2D();
					
					newBound.setTop(bound.getTop()+maxlength);
					newBound.setBottom(bound.getBottom()-maxlength);
					newBound.setLeft(bound.getLeft()-maxlength);
					newBound.setRight(bound.getRight()+maxlength);
					
					addLayerSingleBandVector(emdAllCopyDatasetVector, mapId, emdAllCopyDatasetNm);
					addLayerSingleBandVector(emdOneCopyDatasetVector, mapId, emdOneCopyDatasetNm);
					addLayerThemeLabelVector(emdAllCopyDatasetVector, mapId, emdAllCopyDatasetNm);
					addLayerSingleBandVector(emdPntCopyDatasetVector, mapId, emdPntCopyDatasetNm);
					
					LOGGER.info("Map에 데이터셋 추가 완료");
					
					vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
					vo.setOrignlFileNm("admin");
					vo.setAnalType("admin");
					vo.setSldId(sldId);
					if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
		            	vo.setRouteCode("02");
		            	vo.setSmid(smid2);
		            }else{
		            	vo.setRouteCode("01");
		            	vo.setSmid(smid);
		            }
					
					if(setFrdMapLayoutControl("satelliteTemplate",mapId,newBound)) {
						AnalFileVO imgVo = saveMapImageToJpg(vo);
						list.add(imgVo);
						
						if(deleted) {
							deleteMap(mapId);
						}
					}
					mExportUdbToFile(emdAllCopyDatasetNm, vo, "shp");
					mExportUdbToFile(emdOneCopyDatasetNm, vo, "shp");
					mExportUdbToFile(emdPntCopyDatasetNm, vo, "shp");
					
					emdAllCopyDatasetVector.close();
					emdOneCopyDatasetVector.close();
					emdPntCopyDatasetVector.close();
					
					
					vo.setFileExtsn("zip");
					list.add(vo);
				}else {
					LOGGER.info("레코드셋 정보가 없습니다.");
				}
				
			}else {
				LOGGER.error("좌표변환에 실패하였습니다.");
			}
			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}
	/**
	 * 임도, 관계지적도 이미지 생성
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @param sggCodeOne 
	 * @param sggCodeOne 
	 * @param pageType 
	 * @param smid2 
	 * @param emdCodeOne 
	 * @param emdList 
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createFrdClipRelImg(String sldId,String smid,String analId, String captionNm, String sggCodeOne, String smid2, String pageType) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_frd_sat_".concat(analId);//맵 이름
		String frdDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);//임도테이블명 가져오기
		String frdQuery = "smid = ".concat(smid);//임도 where절
		String frdCopyDatasetNm = "frdlne_b_".concat(analId);//임도 복사 레이어명
		
		DatasetVector frdCopyDatasetVector = null;
		
		String lgstrDatasetNm = "tf_feis_lgstr";//지적
		String lgstrCopyDatasetNm = "wlgstr_".concat(analId);//지적 클립 데이터셋명
		
		DatasetVector lgstrClipDatasetVector = null;
		
		try {
			//슈퍼맵 연결
			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			//유역분석 결과 벡터레이어 DB에서 UDB로 복사
			copyPggisToMemoryUdb(frdDatasetNm,frdCopyDatasetNm,frdQuery);
			
			if(mDatasets.contains(frdCopyDatasetNm)) {
				frdCopyDatasetVector = (DatasetVector) mDatasets.get(frdCopyDatasetNm);
				Recordset mRecordset = frdCopyDatasetVector.query("",CursorType.STATIC);
				
				if(mRecordset.getGeometry() != null) {
					Rectangle2D bound = mRecordset.getGeometry().getBounds();
					
					Rectangle2D newBound = new Rectangle2D();
					//클립할 영역 설정(이미지 영역보다 조금 더 많이 자름)
					
					if((bound.getHeight()/2) > bound.getWidth()) {
						double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*0.5 : bound.getHeight()*0.5;
						newBound = new Rectangle2D();
						newBound.setTop(bound.getTop()+maxlength);
						newBound.setBottom(bound.getBottom()-maxlength);
						newBound.setLeft(bound.getLeft()-maxlength*3);
						newBound.setRight(bound.getRight()+maxlength*3);
					}else {
						double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*0.5 : bound.getHeight()*0.5;
						newBound = new Rectangle2D();
						newBound.setTop(bound.getTop()+maxlength);
						newBound.setBottom(bound.getBottom()-maxlength);
						newBound.setLeft(bound.getLeft()-maxlength);
						newBound.setRight(bound.getRight()+maxlength);
					}
					
					GeoRectangle geoRect = new GeoRectangle(newBound, 0);
					GeoRegion region = geoRect.convertToRegion();
					
					mRecordset.dispose();
					
					lgstrClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(lgstrDatasetNm), region, true, false, mDatasource, lgstrCopyDatasetNm);
					if(lgstrClipDatasetVector.getRecordCount() > 0) {
						LOGGER.info("지적 데이터셋 Clip 완료");
					}else {
						LOGGER.info("지적 데이터셋 Clip 실패");
					}
					
					double newMaxLength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*0.1 : bound.getHeight()*0.1;
					newBound = new Rectangle2D();
					newBound.setTop(bound.getTop()+newMaxLength);
					newBound.setBottom(bound.getBottom()-newMaxLength);
					newBound.setLeft(bound.getLeft()-newMaxLength);
					newBound.setRight(bound.getRight()+newMaxLength);
					
					
					addLayerSingleBandVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
					addLayerThemeLabelVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
					addLayerSingleBandVector(frdCopyDatasetVector, mapId, frdCopyDatasetNm);
					
					
					LOGGER.info("Map에 데이터셋 추가 완료");
					
					vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
					vo.setOrignlFileNm("rllgstr");
					vo.setAnalType("rllgstr");
					vo.setSldId(sldId);
					if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
		            	vo.setRouteCode("02");
		            	vo.setSmid(smid2);
		            }else{
		            	vo.setRouteCode("01");
		            	vo.setSmid(smid);
		            }
					
					if(setFrdMapLayoutControl("satelliteTemplate",mapId,newBound)) {
						AnalFileVO imgVo = saveMapImageToJpg(vo);
						list.add(imgVo);
						
						if(deleted) {
							deleteMap(mapId);
						}
					}
					
					
					frdCopyDatasetVector.close();
					lgstrClipDatasetVector.close();
					
					mExportUdbToFile(frdCopyDatasetNm, vo, "shp");
					mExportUdbToFile(lgstrCopyDatasetNm, vo, "shp");
					vo.setFileExtsn("zip");
					list.add(vo);
					
				}else {
					LOGGER.info("레코드셋 정보가 없습니다.");
				}
				
			}else {
				LOGGER.error("좌표변환에 실패하였습니다.");
			}
				
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}
	/**
	 * 임도, 문화재보존관리지도 이미지 생성
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @param culY 
	 * @param pageType 
	 * @param smid2 
	 * @param list2 
	 * @param sggCodeOne 
	 * @param sggCodeOne 
	 * @param emdCodeOne 
	 * @param emdList 
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createFrdClipCulImg(String sldId,String smid,String analId, String captionNm, Double culX, Double culY, String smidList, String smid2, String pageType) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_frd_sat_".concat(analId);//맵 이름
		
		String frdDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);//임도테이블명 가져오기
		String frdQuery = "smid = ".concat(smid);//임도 where절
		String frdCopyDatasetNm = "frdlne_y_".concat(analId);//임도 복사 레이어명
		DatasetVector frdCopyDatasetVector = null;
		
		String culPntCopyDatasetNm = "frd_cul_pnt_".concat(analId);
		DatasetVector culCopyDatasetVector = null;
		
		String frdLneBufferDatasetNm = "frd_buffer_".concat(analId);
		DatasetVector frdLneBufferDatasetVector = null;
		
		String frdCultureDatasetNm = "tf_feis_cultural";
//		String frdCulLabelQuery = "smid in ("+smidList+")";
		String frdCulLabelQuery = "smid in (".concat(smidList).concat(")");
		String frdCulLabelDatasetNm = "frd_cul_".concat(analId);
		DatasetVector frdCulLabelDatasetVector = null;
		
		GeoRegion region = null;
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			copyPggisToMemoryUdb(frdDatasetNm,frdCopyDatasetNm,frdQuery);
			
			
			if(culX != null && culY != null) {	// 좌표가 있는 경우만 실행
				copyPggisToMemoryUdb(frdCultureDatasetNm,frdCulLabelDatasetNm,frdCulLabelQuery);
				
				createDataset("Point", culPntCopyDatasetNm);
				// 문화재 중심점 좌표에 대해서 중심 벡터 생성
				culCopyDatasetVector = (DatasetVector) mDatasets.get(culPntCopyDatasetNm);
				culCopyDatasetVector.setPrjCoordSys(PrjCoordSys.fromEPSG(5186));
				Recordset pntRecordset = culCopyDatasetVector.query("",CursorType.DYNAMIC);
				
				Point2D pt2d = new Point2D(culX, culY);
				Geometry geom = new GeoPoint(pt2d);
				
				pntRecordset.addNew(geom);
				pntRecordset.update();
				
				pntRecordset.dispose();
				pntRecordset.close();
			
				// 생성된 중심 벡터에 대해 buffer 200m 생성
				frdLneBufferDatasetVector = mDatasets.create(new DatasetVectorInfo(frdLneBufferDatasetNm,DatasetType.REGION),PrjCoordSys.fromEPSG(5186));
				
				BufferAnalystParameter bap = new BufferAnalystParameter();
				bap.setEndType(BufferEndType.ROUND);
				bap.setRadiusUnit(BufferRadiusUnit.Meter);
				bap.setLeftDistance("200");
				bap.setRightDistance("200");
				boolean isBufferCreated = BufferAnalyst.createBuffer(culCopyDatasetVector, frdLneBufferDatasetVector, bap, false, false);
				
				if(!isBufferCreated) {
					System.out.println("버퍼 생성 실패");
				}
				SuperMapBasicUtils.coordSysTranslator(culCopyDatasetVector,3857);
				
				// buffer 벡터 갖고오기
				frdLneBufferDatasetVector = (DatasetVector) mDatasets.get(frdLneBufferDatasetNm);
				SuperMapBasicUtils.coordSysTranslator(frdLneBufferDatasetVector,3857);
				frdCulLabelDatasetVector = (DatasetVector) mDatasets.get(frdCulLabelDatasetNm); 
				SuperMapBasicUtils.coordSysTranslator(frdCulLabelDatasetVector,3857);
			}
			
			//데이터셋 자르기
			if(mDatasets.contains(frdCopyDatasetNm)) {
				frdCopyDatasetVector = (DatasetVector) mDatasets.get(frdCopyDatasetNm);
				
				Recordset mRecordset = frdCopyDatasetVector.query("",CursorType.STATIC);
				
				if(mRecordset.getGeometry() != null) {
					
					SuperMapBasicUtils.coordSysTranslator(frdCopyDatasetVector,3857);
					
					Rectangle2D bound = mRecordset.getBounds();
					
					double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*1.1 : bound.getHeight()*1.1;
					newBound = new Rectangle2D();
					newBound.setTop(bound.getTop()+maxlength);
					newBound.setBottom(bound.getBottom()-maxlength);
					newBound.setLeft(bound.getLeft()-maxlength);
					newBound.setRight(bound.getRight()+maxlength);
					
					
					GeoRectangle geoRect = new GeoRectangle(newBound, 0);
					region = geoRect.convertToRegion();
					
					mRecordset.dispose();
					
					vDataset = vDatasets.get("VworldSatellite");
					
					addLayerImage(vDataset,mapId,"vworld");	// 브이월드 바탕
					addLayerSingleBandVector(frdCopyDatasetVector,mapId,frdCopyDatasetNm);		//임도 라인
					
					if(culX != null && culY != null) {
						addLayerSingleBandVector(frdLneBufferDatasetVector,mapId,frdLneBufferDatasetNm);	// 버퍼가 있는 원
						addLayerThemeLabelVector(frdCulLabelDatasetVector,mapId,frdCulLabelDatasetNm);	// 문화재 라벨
					}
					
					vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
					vo.setOrignlFileNm("culture");
					vo.setAnalType("culture");
					vo.setSldId(sldId);
					if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
		            	vo.setRouteCode("02");
		            	vo.setSmid(smid2);
		            }else{
		            	vo.setRouteCode("01");
		            	vo.setSmid(smid);
		            }
					
					if(setFrdMapLayoutControl("satelliteTemplate",mapId,newBound)) {
						//saveImgNm = saveMapImage();
						AnalFileVO imgVo = saveMapImageToJpg(vo);
						list.add(imgVo);
						
						if(deleted) {
							deleteMap(mapId);
						}
					}
					
					mExportUdbToFile(frdCopyDatasetNm, vo, "shp");
					if(culX != null && culY != null) {
						mExportUdbToFile(culPntCopyDatasetNm, vo, "shp");
						mExportUdbToFile(frdLneBufferDatasetNm, vo, "shp");
						mExportUdbToFile(frdCulLabelDatasetNm, vo, "shp");
						
						culCopyDatasetVector.close();
						frdLneBufferDatasetVector.close();
						frdCulLabelDatasetVector.close();
					}
					
					vo.setFileExtsn("zip");
					list.add(vo);
					
					frdCopyDatasetVector.close();
				}else {
					LOGGER.error("좌표변환에 실패하였습니다.");
				}
				
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}
	
	/**
	 * 임도, 분포도
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createFrdClipDestImg(String sldId,String smid,String analId, String captionNm, String analType, String mapStyle, String bufferSize, String smid2, String pageType) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;

		String mapId = "Map_frd_sat_".concat(analId);//맵 이름
		  
        // 임도 테이블명 조회
		String frdDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);//임도테이블명 가져오기

		// 기존노선
		String frdQuery = null;//임도 where절
		String frdCopyDatasetNm = null; 
		DatasetVector frdCopyDatasetVector = null;

		// 수정노선
		String frdModiQuery = null;
		String frdModiCopyDatasetNm = null;
		DatasetVector frdModiCopyDatasetVector = null;
		if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
			frdModiQuery = "smid = ".concat(smid2);
			frdModiCopyDatasetNm = "frdlne_blue_".concat(analId);
			frdModiCopyDatasetVector = null;
		}else {
			frdQuery = "smid = ".concat(smid);//임도 where절
			frdCopyDatasetNm = "frdlne_y_".concat(analId); 
			frdCopyDatasetVector = null;
		}
			

        //힐쉐이드 테이블명 조회
		String clipRasterDatasetNm = "tf_feis_hillshade";

		// 힐쉐이드 레스터 (전체)
		String clipRasterCopyDatasetNm = "hillshade".concat("_"+analId);
		Dataset clipRasterDataset = null;

		// 힐쉐이드 레스터 (클립)
		String convertClipRasterCopyDatasetNm = "hillshade_3857_".concat(analType).concat("_").concat(analId);
		Dataset convertClipRasterDataset = null;

        // 버퍼 벡터 (전체)
        DatasetVector frdLneBufferDatasetVector = null;
		String frdLneBufferDatasetVectorNm =  "frdLne_buff_".concat(analId);

        // 버퍼 벡터 (클립)
		DatasetVector frdLneBufferClipDatasetVector = null;
		String frdLneBufferClipDatasetVectorNm = "frd_"+analType.concat("_").concat(analId);

        // 통계 JSONObject
        JSONObject statisticMap = null;

        GeoRegion region = null;
		Rectangle2D newBound = null;
		try {
	
			// 슈퍼맵 연결
			getConnectionInfo(analId);
	
			// Map 생성
			createNewMap(mapId,3857);
	
			if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
				// 수정노선
				copyPggisToMemoryUdb(frdDatasetNm,frdModiCopyDatasetNm,frdModiQuery);
			}else {
				// 기존노선
				copyPggisToMemoryUdb(frdDatasetNm,frdCopyDatasetNm,frdQuery);
			}
	
	        if(frdCopyDatasetNm != null || frdModiCopyDatasetNm != null) {
	            frdLneBufferDatasetVector = mDatasets.create(new DatasetVectorInfo(frdLneBufferDatasetVectorNm,DatasetType.REGION),PrjCoordSys.fromEPSG(5186));
	            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	                frdModiCopyDatasetVector = (DatasetVector) mDatasets.get(frdModiCopyDatasetNm);
	            }else {
	            	frdCopyDatasetVector = (DatasetVector) mDatasets.get(frdCopyDatasetNm);
	            }
	
	            // 버퍼설정 값
	            BufferAnalystParameter bap = new BufferAnalystParameter();
	            bap.setEndType(BufferEndType.ROUND);
	            bap.setRadiusUnit(BufferRadiusUnit.Meter);
	            bap.setLeftDistance(bufferSize);
	            bap.setRightDistance(bufferSize);
	            
	            // 병합여부
	            boolean isUnion = false;
	            // 기존속성값 유무
	            boolean isAttributeRetained = false;
	            
	            // 버퍼 생성
	            boolean isBufferCreated;
	            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	                isBufferCreated = BufferAnalyst.createBuffer(frdModiCopyDatasetVector, frdLneBufferDatasetVector, bap, isUnion, isAttributeRetained);
	            }else {
	                isBufferCreated = BufferAnalyst.createBuffer(frdCopyDatasetVector, frdLneBufferDatasetVector, bap, isUnion, isAttributeRetained);
	            }
	            if(!isBufferCreated) LOGGER.info("임도 버퍼 생성 실패!");
	            
	            //임도라인 버퍼데이터셋 레코드셋 조회
	            Recordset mFrdLneBufferRecordset = frdLneBufferDatasetVector.query("",CursorType.STATIC);
	            GeoRegion frdLneBufferRegion = new GeoRegion();
	            
	            while (!mFrdLneBufferRecordset.isEOF()) {
	                for (int i = 0; i < ((GeoRegion)mFrdLneBufferRecordset.getGeometry()).getPartCount(); i++) {
	                    frdLneBufferRegion.addPart(((GeoRegion) mFrdLneBufferRecordset.getGeometry()).getPart(i));
	                }
	                mFrdLneBufferRecordset.moveNext();
	            }
	            mFrdLneBufferRecordset.dispose();
	            
	            // 버퍼 클립
	            frdLneBufferClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(SuperMapBasicUtils.getLayerName(analType)), frdLneBufferRegion, true, false, mDatasource, frdLneBufferClipDatasetVectorNm);
	            if(frdLneBufferClipDatasetVector.getRecordCount() > 0) {
	                LOGGER.info("버퍼 데이터셋 Clip 완료");
	            }else {
	                LOGGER.info("버퍼 데이터셋 Clip 실패");
	            }
	            
	            // 힐쉐이드 데이터셋 불러오기
	            Dataset clipRasterData =  datasource.getDatasets().get(clipRasterDatasetNm);
	            
	            // 힐쉐이드 클립
	            clipRasterDataset = RasterClip.clip((DatasetGrid)clipRasterData,frdLneBufferRegion, true, false, mDatasource, clipRasterCopyDatasetNm);
	            if(clipRasterDataset != null) {
	                LOGGER.info("힐쉐이드 레스터 클립 완료");
	            }else {
	                LOGGER.info("힐쉐이드 레스터 클립 실패");
	            }
	        
	            clipRasterData.close();
	            
	            vDataset = vDatasets.get("VworldSatellite");
	            
	            // 벡터 좌표변환
	            SuperMapBasicUtils.coordSysTranslator(frdLneBufferClipDatasetVector,3857);
	            SuperMapBasicUtils.coordSysTranslator(frdLneBufferDatasetVector,3857);
	            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	            	SuperMapBasicUtils.coordSysTranslator(frdModiCopyDatasetVector,3857);
	            }else {
	            	SuperMapBasicUtils.coordSysTranslator(frdCopyDatasetVector,3857);
	            }
	            // 레스트 좌표변환 (raster는 좌표변환이 boolean타입의 SuperMapBasicUtils.coordSysTranslator로 안돼서 새로운 dataset으로 받아와야해서 아래코드 사용)
	            convertClipRasterDataset = CoordSysTranslator.convert(clipRasterDataset, new PrjCoordSys(3857), mDatasource, convertClipRasterCopyDatasetNm,new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
	            
	            // 이미지 영역 지정
	            Recordset mRecordsetBuff = frdLneBufferDatasetVector.query("", CursorType.STATIC);
	            Rectangle2D bound = mRecordsetBuff.getBounds();
	            double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*0.4 : bound.getHeight()*0.4;
	            
	            newBound = new Rectangle2D();
	            newBound.setTop(bound.getTop()+maxlength);
	            newBound.setBottom(bound.getBottom()-maxlength);
	            newBound.setLeft(bound.getLeft()-maxlength);
	            newBound.setRight(bound.getRight()+maxlength);
	            
	            GeoRectangle geoRect = new GeoRectangle(newBound, 0);
	            region = geoRect.convertToRegion();
	            
	            // 통계치 계산
	            statisticMap = SuperMapBasicUtils.caculateStatisticsVector(frdLneBufferClipDatasetVector, analType);
	            
	            // Map에 Setting
	            addLayerImage(vDataset,mapId,"vworld");	// 브이월드 바탕
	            addLayerThemeUniqueVector(frdLneBufferClipDatasetVector,mapId,frdLneBufferClipDatasetVectorNm);	// 임도 버퍼 벡터
	            addLayerSingleBandGrid(convertClipRasterDataset,mapId,convertClipRasterCopyDatasetNm);	// 힐쉐이드
	            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	                addLayerSingleBandVector(frdModiCopyDatasetVector,mapId,frdModiCopyDatasetNm);	// 수정노선
	            }else {
	            	addLayerSingleBandVector(frdCopyDatasetVector,mapId,frdCopyDatasetNm);	// 임도라인 벡터
	            }
	            
	            mRecordsetBuff.dispose();
	            
	            vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
	            
	            if(statisticMap != null) {
	            	vo.setStatData(statisticMap.toString());
	            }
	            
	            vo.setOrignlFileNm(analType);
	            vo.setAnalType(analType);
	            vo.setSldId(sldId);
	            vo.setBufferSize(bufferSize);
	            
	            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	            	vo.setRouteCode("02");
	            	vo.setSmid(smid2);
	            }else{
	            	vo.setRouteCode("01");
	            	vo.setSmid(smid);
	            }
	            
	            if(setFrdMapLayoutControl("tp_vyt_frd_clip",mapId,newBound)) {
	                AnalFileVO imgVo = saveMapImageToJpg(vo);
	                list.add(imgVo);
	                
	                if(deleted) {
	                    deleteMap(mapId);
	                }
	            }
	            
	            mExportUdbToFile(frdLneBufferClipDatasetVectorNm, vo, "shp");
	            mExportUdbToFile(convertClipRasterCopyDatasetNm, vo, "tif");
	            
	            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	                mExportUdbToFile(frdModiCopyDatasetNm, vo, "shp");
	                frdModiCopyDatasetVector.close();
	            }else {
	            	mExportUdbToFile(frdCopyDatasetNm, vo, "shp");
	            	frdCopyDatasetVector.close();
	            }
	            
	            frdLneBufferClipDatasetVector.close();
	            frdLneBufferDatasetVector.close();
	            clipRasterDataset.close();
	            convertClipRasterDataset.close();
	            
	            vo.setFileExtsn("zip");
	            list.add(vo);
	        
	        }else {
	            LOGGER.error("좌표변환에 실패하였습니다.");
	        }
	    } catch (Exception e) {
	        LOGGER.error(e.getMessage());
	    } finally {
	        closeConnection();
	    }
		
		return list;
	}

	
	/**
	 * 임도, 분포도(래스터이미지)
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createFrdClipRstrImg(String sldId,String smid,String analId, String captionNm, String analType, String mapStyle, String bufferSize, String smid2 ,String pageType) throws Exception{
		mDatasets.deleteAll();
        List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;

        String mapId = "Map_frd_sat_".concat(analId);//맵 이름

        // 임도 테이블명 조회
		String frdDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);//임도테이블명 가져오기

        // 기존노선
		String frdQuery = null;//임도 where절
		String frdCopyDatasetNm = null; 
		DatasetVector frdCopyDatasetVector = null;

		
		// 수정노선
		String frdModiQuery = null;
		String frdModiCopyDatasetNm = null;
		DatasetVector frdModiCopyDatasetVector = null;
		if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
			frdModiQuery = "smid = ".concat(smid2);
			frdModiCopyDatasetNm = "frdlne_blue_".concat(analId);
			frdModiCopyDatasetVector = null;
		}else {
			frdQuery = "smid = ".concat(smid);//임도 where절
			frdCopyDatasetNm = "frdlne_y_".concat(analId); 
			frdCopyDatasetVector = null;
		}

        //힐쉐이드 테이블명 조회
		String clipRasterDatasetNm = "tf_feis_hillshade";

        // 힐쉐이드 레스터 (전체)
		String clipRasterCopyDatasetNm = "hillshade".concat("_"+analId);
		Dataset clipRasterDataset = null;

		// 힐쉐이드 레스터 (클립)
		String convertClipRasterCopyDatasetNm = "hillshade_3857_".concat(analType).concat("_").concat(analId);
		Dataset convertClipRasterDataset = null;

        // 버퍼 벡터 (전체)
        DatasetVector frdLneBufferDatasetVector = null;
        String frdLneBufferDatasetVectorNm =  "frdLne_buff_".concat(analId);

        // 분석데이터 (전체)
        String clipRasterCopyOverDatasetNm = analType.concat("_"+analId);
		String clipRasterOverDatasetNm = SuperMapBasicUtils.getLayerName(analType);
		Dataset clipRasterOverDataset = null;
		
        // 분석데이터 (클립)
        String convertClipRasterCopyOverDatasetNm = analType+"_3857_".concat(analType).concat("_").concat(analId);
		Dataset convertClipRasterOverDataset = null;

		JSONObject statisticMap = null;

        GeoRegion region = null;
		Rectangle2D newBound = null;
		
        try{
            
            // 슈퍼맵 연결
            getConnectionInfo(analId);

            // Map 생성
            createNewMap(mapId,3857);

            if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
                copyPggisToMemoryUdb(frdDatasetNm,frdModiCopyDatasetNm,frdModiQuery);
            }else {
            	// 기존노선
            	copyPggisToMemoryUdb(frdDatasetNm,frdCopyDatasetNm,frdQuery);
            }

            if(frdCopyDatasetNm != null || frdModiCopyDatasetNm != null) {
				if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
                    frdModiCopyDatasetVector = (DatasetVector) mDatasets.get(frdModiCopyDatasetNm);
				}else {
					frdCopyDatasetVector = (DatasetVector) mDatasets.get(frdCopyDatasetNm);
				}

                frdLneBufferDatasetVector = mDatasets.create(new DatasetVectorInfo(frdLneBufferDatasetVectorNm,DatasetType.REGION),PrjCoordSys.fromEPSG(5186));

                // 버퍼설정 값
                BufferAnalystParameter bap = new BufferAnalystParameter();
                bap.setEndType(BufferEndType.ROUND);
                bap.setRadiusUnit(BufferRadiusUnit.Meter);
                bap.setLeftDistance(bufferSize);
                bap.setRightDistance(bufferSize);
                
                // 병합여부
                boolean isUnion = false;
                // 기존속성값 유무
                boolean isAttributeRetained = false;

                // 버퍼 생성
                boolean isBufferCreated;
                if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
                    isBufferCreated = BufferAnalyst.createBuffer(frdModiCopyDatasetVector, frdLneBufferDatasetVector, bap, isUnion, isAttributeRetained);
                }else {
                    isBufferCreated = BufferAnalyst.createBuffer(frdCopyDatasetVector, frdLneBufferDatasetVector, bap, isUnion, isAttributeRetained);
                }
                if(!isBufferCreated) LOGGER.info("임도 버퍼 생성 실패!");

                //임도라인버퍼데이터셋 레코드셋 조회
				Recordset mFrdLneBufferRecordset = frdLneBufferDatasetVector.query("",CursorType.STATIC);
				GeoRegion frdLneBufferRegion = new GeoRegion();
				
				while (!mFrdLneBufferRecordset.isEOF()) {
					for (int i = 0; i < ((GeoRegion)mFrdLneBufferRecordset.getGeometry()).getPartCount(); i++) {
						frdLneBufferRegion.addPart(((GeoRegion) mFrdLneBufferRecordset.getGeometry()).getPart(i));
					}
					mFrdLneBufferRecordset.moveNext();
				}
				mFrdLneBufferRecordset.dispose();

				Dataset clipRasterData =  datasource.getDatasets().get(clipRasterDatasetNm);
				clipRasterDataset = RasterClip.clip((DatasetGrid)clipRasterData,frdLneBufferRegion, true, false, mDatasource, clipRasterCopyDatasetNm);
				if(clipRasterDataset != null) {
					LOGGER.info("힐쉐이드 레스터 클립 완료");
				}else {
					LOGGER.info("힐쉐이드 레스터 클립 실패");
				}
				clipRasterData.close();
				
				Dataset clipRasterOverData =  datasource.getDatasets().get(clipRasterOverDatasetNm);
				clipRasterOverDataset = RasterClip.clip((DatasetGrid)clipRasterOverData,frdLneBufferRegion, true, false, mDatasource, clipRasterCopyOverDatasetNm);
				if(clipRasterOverDataset != null) {
					LOGGER.info(analType+" 레스터 클립 완료");
				}else {
					LOGGER.info(analType+" 레스터 클립 실패");
				}
				clipRasterOverData.close();

                // 벡터 좌표변환
                SuperMapBasicUtils.coordSysTranslator(frdLneBufferDatasetVector,3857);
                if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
                    SuperMapBasicUtils.coordSysTranslator(frdModiCopyDatasetVector,3857);
                }else {
                	SuperMapBasicUtils.coordSysTranslator(frdCopyDatasetVector,3857);
                }
                // 레스트 좌표변환 (raster는 좌표변환이 boolean타입의 SuperMapBasicUtils.coordSysTranslator로 안돼서 새로운 dataset으로 받아와야해서 아래코드 사용)
                convertClipRasterDataset = CoordSysTranslator.convert(clipRasterDataset, new PrjCoordSys(3857), mDatasource, convertClipRasterCopyDatasetNm,new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
				convertClipRasterOverDataset = CoordSysTranslator.convert(clipRasterOverDataset, new PrjCoordSys(3857), mDatasource, convertClipRasterCopyOverDatasetNm,new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);

                // 이미지 영역 지정
                Recordset mRecordsetBuff = frdLneBufferDatasetVector.query("", CursorType.STATIC);
                Rectangle2D bound = mRecordsetBuff.getBounds();
                double maxlength = bound.getWidth() > bound.getHeight() ? bound.getWidth()*0.4 : bound.getHeight()*0.4;
                
                newBound = new Rectangle2D();
                newBound.setTop(bound.getTop()+maxlength);
                newBound.setBottom(bound.getBottom()-maxlength);
                newBound.setLeft(bound.getLeft()-maxlength);
                newBound.setRight(bound.getRight()+maxlength);
                
                GeoRectangle geoRect = new GeoRectangle(newBound, 0);
                region = geoRect.convertToRegion();

                // 통계치 계산
                statisticMap = SuperMapBasicUtils.caculateStatisticsGrid(convertClipRasterOverDataset, analType, region.getArea());

                // aspect, landslide -> singleBandGrid
				// dem, slope -> ThemeRangeGrid

                vDataset = vDatasets.get("VworldSatellite");
                addLayerImage(vDataset,mapId,"vworld");	// 브이월드 바탕

                if(analType.equals("slope") || analType.equals("dem")) {
					addLayerThemeRangeGrid(convertClipRasterOverDataset,mapId,convertClipRasterCopyOverDatasetNm); //데이터
				}else if(analType.equals("aspect") || analType.equals("landslide")) {
					addLayerSingleBandGrid(convertClipRasterOverDataset,mapId,convertClipRasterCopyOverDatasetNm); //데이터
				}
                addLayerSingleBandGrid(convertClipRasterDataset,mapId,convertClipRasterCopyDatasetNm);	// 힐쉐이드
				if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
					addLayerSingleBandVector(frdModiCopyDatasetVector,mapId,frdModiCopyDatasetNm);	// 수정노선
				}else {
					addLayerSingleBandVector(frdCopyDatasetVector,mapId,frdCopyDatasetNm);	// 임도라인 벡터
				}

                mRecordsetBuff.dispose();

                vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
				
				if(statisticMap != null) {
					vo.setStatData(statisticMap.toString());
				}
				
                vo.setOrignlFileNm(analType);
				vo.setAnalType(analType);
				vo.setSldId(sldId);
				vo.setBufferSize(bufferSize);

				if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
	            	vo.setRouteCode("02");
	            	vo.setSmid(smid2);
	            }else{
	            	vo.setRouteCode("01");
	            	vo.setSmid(smid);
	            }
				
                String templeNm = null; 
				
				if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
					templeNm = "tp_vyt_frd_"+analType+"_r";
				}else {
					templeNm = "tp_vyt_frd_"+analType;
				}
				
				if(setFrdMapLayoutControl(templeNm,mapId,newBound)) {
					AnalFileVO imgVo = saveMapImageToJpg(vo);
					list.add(imgVo);
					
					if(deleted) {
						deleteMap(mapId);
					}
				}

				mExportUdbToFile(convertClipRasterCopyDatasetNm, vo, "tif");
				mExportUdbToFile(convertClipRasterCopyOverDatasetNm, vo, "tif");
				if(smid2 != null && !smid2.isEmpty() && pageType.equals("svyCompt")) {
					mExportUdbToFile(frdModiCopyDatasetNm, vo, "shp");
					frdModiCopyDatasetVector.close();
				}else {
					mExportUdbToFile(frdCopyDatasetNm, vo, "shp");
					frdCopyDatasetVector.close();
				}

                frdLneBufferDatasetVector.close();
				clipRasterDataset.close();
				convertClipRasterDataset.close();
				convertClipRasterOverDataset.close();

                vo.setFileExtsn("zip");
				list.add(vo);
            }else {
				LOGGER.error("좌표변환에 실패하였습니다.");
			}

        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }finally{
            closeConnection();
        }
        return list;
	}
	
	/**
	 * 임도, 조사완료 항목 좌표, create Point and SHP 
	 * @param data1_value 
	 * @param data1_key 
	 * @param lat 
	 * @param lon 
	 * @param analId 
	 * @param sldId {대상지그룹ID}
	 * @param smid {대상지 고유ID}
	 * @param analId {분석ID}
	 * @param captionNm {테이블ID조회를 위한 키워드}
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createSvyComptPointShp(String smid, String sldId, String lon1, String lat1, String lon2, String lat2, String data1_key, String data1_value, String data2_key, String data2_value, String data3_key, String data3_value, String analId, String analType) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_frd_sat_".concat(analId);//맵 이름
		
		String pntCopyDatasetNm = "frd_svyCompt_pnt_".concat(analId);
		DatasetVector copyDatasetVector = null;
		
		
		try {
			//슈퍼맵 연결
			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			if(!lon1.equals("") && !lat1.equals("")) {
				
				createDataset("Point", pntCopyDatasetNm);
				
				copyDatasetVector = (DatasetVector) mDatasets.get(pntCopyDatasetNm);
				copyDatasetVector.setPrjCoordSys(PrjCoordSys.fromEPSG(5186));
				
				Point2D pt2d1 = new Point2D(Double.parseDouble(lon1), Double.parseDouble(lat1));
				Geometry geom1 = new GeoPoint(pt2d1);
				
				FieldInfos lnePntFieldInfos = copyDatasetVector.getFieldInfos();
				
				// 조사명
				FieldInfo valueInfo1 = new FieldInfo("조사명", FieldType.TEXT);
				lnePntFieldInfos.add(valueInfo1);
				
				// data1 key 입력
				FieldInfo valueInfo = new FieldInfo(data1_key, FieldType.TEXT);
				lnePntFieldInfos.add(valueInfo);
				
				Recordset pntRecordset = copyDatasetVector.query("",CursorType.DYNAMIC);
				
				pntRecordset.edit();
				
				pntRecordset.addNew(geom1);
				// 조사명 입력
				if(!lon2.equals("") && !lat2.equals("")) {
					pntRecordset.setString("조사명", analType.concat("_시점"));
				}else {
					pntRecordset.setString("조사명", analType);
				}
				// data1 값 입력
				pntRecordset.setString(data1_key, data1_value);
				pntRecordset.update();
				
				// data2 값 입력
				if(!data2_key.equals("") && !data2_value.equals("")) {
					pntRecordset.setString(data2_key, data2_value);
					pntRecordset.update();
				}
				
				// data3 값 입력
				if(!data3_key.equals("") && !data3_value.equals("")) {
					pntRecordset.setString(data3_key, data3_value);
					pntRecordset.update();
				}
				
				// 좌표가 2개인 경우
				if(!lon2.equals("") && !lat2.equals("")) {
					Point2D pt2d2= new Point2D(Double.parseDouble(lon2), Double.parseDouble(lat2));
					Geometry geom2 = new GeoPoint(pt2d2);
					
					pntRecordset.addNew(geom2);
					// 조사명 입력
					pntRecordset.setString("조사명", analType.concat("_종점"));
					// data1 값 입력
					pntRecordset.setString(data1_key, data1_value);
					pntRecordset.update();
				}
				
				vo = SuperMapBasicUtils.getSavePath(analId,downloadMidDir);
				vo.setSmid(smid);
				vo.setSldId(sldId);
				
				String analNum = analType.replaceAll("[^0-9]","");
				String convertAnalNm = null;
				
				if(analType.contains("보호시설")) {
					vo.setRouteCode("20");
					convertAnalNm = "safeFct_".concat(analNum);
				}else if(analType.contains("종단기울기")) {
					vo.setRouteCode("21");
					convertAnalNm = "lonSlope_".concat(analNum);
				}else if(analType.contains("산지경사")) {
					vo.setRouteCode("22");
					convertAnalNm = "mtSlope_".concat(analNum);
				}else if(analType.contains("암반노출")) {
					vo.setRouteCode("23");
					convertAnalNm = "rockExpos_".concat(analNum);
				}else if(analType.contains("조림지")) {
					vo.setRouteCode("24");
					convertAnalNm = "afrst_".concat(analNum);
				}else if(analType.contains("벌채지")) {
					vo.setRouteCode("25");
					convertAnalNm = "cutting_".concat(analNum);
				}else if(analType.contains("석력지")) {
					vo.setRouteCode("26");
					convertAnalNm = "stmi_".concat(analNum);
				}else if(analType.contains("계류종류및현황")) {
					vo.setRouteCode("27");
					convertAnalNm = "mrngKind_".concat(analNum);
				}else if(analType.contains("습지")) {
					vo.setRouteCode("28");
					convertAnalNm = "wetLand_".concat(analNum);
				}else if(analType.contains("묘지")) {
					vo.setRouteCode("29");
					convertAnalNm = "cmtry_".concat(analNum);
				}else if(analType.contains("용출수")) {
					vo.setRouteCode("30");
					convertAnalNm = "eltnWater_".concat(analNum);
				}else if(analType.contains("연약지반")) {
					vo.setRouteCode("31");
					convertAnalNm = "sofrtGrnd_".concat(analNum);
				}else if(analType.contains("붕괴우려지역")) {
					vo.setRouteCode("32");
					convertAnalNm = "clpsCnrn_".concat(analNum);
				}else if(analType.contains("주요수종")) {
					vo.setRouteCode("33");
					convertAnalNm = "maintreeknd_".concat(analNum);
				}else if(analType.contains("주요식생")) {
					vo.setRouteCode("34");
					convertAnalNm = "mainveg_".concat(analNum);
				}else if(analType.contains("상수원오염")) {
					vo.setRouteCode("35");
					convertAnalNm = "wtrPltn_".concat(analNum);
				}else if(analType.contains("훼손우려지")) {
					vo.setRouteCode("36");
					convertAnalNm = "dmgCncrn_".concat(analNum);
				}else if(analType.contains("산림재해")) {
					vo.setRouteCode("37");
					convertAnalNm = "frstDsstr_".concat(analNum);
				}else if(analType.contains("야생동물")) {
					vo.setRouteCode("38");
					convertAnalNm = "wildAnml_".concat(analNum);
				}else if(analType.contains("사방시설설치")) {
					vo.setRouteCode("39");
					convertAnalNm = "ecnrFcltyInstl_".concat(analNum);
				}else if(analType.contains("사방시설필요")) {
					vo.setRouteCode("40");
					convertAnalNm = "ecnrFcltyNcsty_".concat(analNum);
				}
				vo.setAnalType(convertAnalNm);
				vo.setOrignlFileNm(convertAnalNm);
				
				
				mExportUdbToFile(pntCopyDatasetNm, vo, "shp");
				
				pntRecordset.dispose();
				pntRecordset.close();
				
				copyDatasetVector.close();
				
				vo.setFileExtsn("zip");
				list.add(vo);
			}
			
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return list;
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
			//SuperMapBasicUtils.coordSysTranslator.convert(copyDatastVector, getCustomPrj5179(5179), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
			
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
		if(uRecordset != null) {
			uRecordset.close();
			uRecordset.dispose();
			uRecordset = null;
		}
		if(uDataset != null && !uDataset.isDisposed()){
			uDataset.close();
		}
		if(vRecordset != null) {
			vRecordset.close();
			vRecordset.dispose();
			vRecordset = null;
		}
		if(vDataset != null && !vDataset.isDisposed()){
			vDataset.close();
		}
	}
	
	public void closeWorkspace() {
		if(datasource != null && datasource.isOpened()){
			datasource.close();
			LOGGER.debug("pggis datasource closed.");
		}
		if(uDatasource != null && uDatasource.isOpened()){
			uDatasource.close();
			LOGGER.debug("udb datasource closed.");
		}
		if(vDatasource != null && vDatasource.isOpened()){
			vDatasource.close();
			LOGGER.debug("vworld datasource closed.");
		}
		
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
	
	/**
	 * Map 생성
	 * @param mapNm
	 */
	public void createNewMap(String mapNm, int epsg) {
		com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
		//map.setPrjCoordSys(getCustomPrj5179(epsg));
		map.setPrjCoordSys(new PrjCoordSys(epsg));
		workspace.getMaps().add(mapNm, map.toXML());
		workspace.getMaps().setMapXML(mapNm, map.toXML());
	}
	
	/**
	 * Map에 벡터 데이터셋 추가
	 * @param dataset
	 * @param mapName
	 * @param caption
	 * @return
	 */
	private boolean addLayerSingleBandVector(Dataset dataset, String mapName, String caption) {
		try {
			DatasetVector vector = (DatasetVector)dataset;
			System.out.println(vector.getName());
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			LayerSettingVector lsv = new LayerSettingVector();
			
			Layer layer = map.getLayers().add(vector,lsv, true);
			lsv = (LayerSettingVector)layer.getAdditionalSetting();
			lsv.setStyle(SuperMapBasicUtils.getGeoStyle(caption));
			
			map.viewEntire();
			layer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			
			map.close();
		}catch (Exception e) {
			LOGGER.error("Map에 Vector DataSet 추가 : "+e.getMessage());
		}
		return true;
	}
	
	/**
	 * Map에 테마라벨 맵 추가
	 * @param dataset
	 * @param mapName
	 * @param caption
	 * @return
	 */
	private boolean addLayerThemeLabelVector(Dataset dataset, String mapName, String caption) {
		try {
			DatasetVector vector = (DatasetVector)dataset;
			System.out.println(vector.getName());
			
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			
			ThemeLabel themeLabel = SuperMapBasicUtils.getThemeLabel(caption);
			//Layer vectorlayer = map.getLayers().add(vector, true);
			Layer themeLayer = map.getLayers().add(vector,themeLabel,true);
			
			//map.viewEntire();
			//vectorlayer.setVisible(false);
			themeLayer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			map.refresh();
			map.close();
		}catch (Exception e) {
			LOGGER.error("Map에 TehmeLabelVector DataSet 추가 : "+e.getMessage());
		}
		return true;
	}
	
	/**
	 * 임도용 템플릿 레이아웃 설정
	 * @param name
	 * @throws Exception
	 */
	public boolean setFrdMapLayoutControl(String layoutName, String mapName,Rectangle2D rect){
		LOGGER.info("템플릿 레이아웃 설정 시작");
		
		mapLayoutcontrol = new MapLayoutControl();
		mapLayoutcontrol.getMapLayout().setWorkspace(workspace);
		mapLayoutcontrol.getMapLayout().open(layoutName);
		
		try {
			LayoutElements layoutElements = mapLayoutcontrol.getMapLayout().getElements();
			while(!layoutElements.isEOF()) {
				Geometry geometry = layoutElements.getGeometry();
				
				if(geometry instanceof GeoText) {
					
				}
				if(geometry instanceof GeoMap) {
					GeoMap target = (GeoMap)geometry;
					target.setMapName(mapName);
					
					layoutElements.setGeometry(target);
					
					com.supermap.mapping.Map map = new com.supermap.mapping.Map();
					map.setWorkspace(workspace);
					System.out.println(mapName);
					map.open(mapName);
					
					//Rectangle2D rect = datasource.getDatasets().get(this.dataSetNm).getBounds();
					//rect.inflate(100, 100);
					
					mapLayoutcontrol.setMapAction(Action.NULL);
					mapLayoutcontrol.setActiveGeoMapID(layoutElements.getID());
					mapLayoutcontrol.getActiveMap().setViewBounds(rect);
					//mapLayoutcontrol.getActiveMap().setScale(100);
					mapLayoutcontrol.getActiveMap().setCenter(rect.getCenter());
					mapLayoutcontrol.getActiveMap().refresh();
					mapLayoutcontrol.setActiveGeoMapID(-1);
					
				}
				if(geometry instanceof GeoLegend) {
					GeoLegend legend = createFrdGeoLegend(layoutName,geometry, mapName);
					layoutElements.setGeometry(legend);
				}
				
				layoutElements.moveNext();
			}
			layoutElements.refresh();
			mapLayoutcontrol.getMapLayout().refresh();
			LOGGER.info("템플릿 레이아웃 설정 완료");
		} catch (Exception e) {
			LOGGER.error("템플릿 레이아웃 설정 오류 : "+e.getMessage());
		}
		return true;
	}
	
	/**
	 * 지도템플릿 범례생성
	 * @param geometry
	 * @param mapName
	 * @return
	 * @throws Exception
	 */
	private GeoLegend createFrdGeoLegend(String layoutName, Geometry geometry, String mapName) throws Exception{
		String legendTitle = "<범 례>";
		GeoLegend target = (GeoLegend)geometry;
		GeoLegend legend = new GeoLegend(mapName, datasource.getWorkspace(), legendTitle);
		
		
		String lineType = null;
		for(int i=0; i<legend.getItemNames().length; i++) {
			String lyNm = legend.getItemNames()[i];
			
			if(lyNm.contains("_y_")) {
				lineType = "타당성평가노선";
	        }else if(lyNm.contains("_blue_")) {
	        	lineType = "수정노선";
	        }
			
			if(lyNm.contains("hillshade") || lyNm.contains("VworldSatellite")) {
				legend.setItemVisible(lyNm,false);
			}
		}
		
		legend.setColumnCount(1);	// 범례 컬럼갯수
		legend.setWidth(target.getBounds().getWidth());
		legend.setCenter(new Point2D(target.getInnerPoint().x, target.getInnerPoint().y));
		
		GeoCompound compound = legend.getInnerGeometry();
		legend.setHeight((compound.getPartCount()/2)*65);
		
		int idx = 0;
		double positionX = 88;
		double positionY = 0;
		
		double lineHeight = 0;
		
		int tIdx = 0;
		
		LinkedHashMap<Integer, Double> positionYMap = new LinkedHashMap<Integer, Double>();
		
		int pc = compound.getPartCount();
		
		double gpc = 0;	// 보정값
		
		if(pc > 12){
			gpc = ((compound.getPartCount()/(legend.getHeight()/2))*100)-10;
		}else {
			gpc = ((compound.getPartCount()/(legend.getHeight()/2))*100);
		}
		
		for (int i =0; i < compound.getPartCount(); i++) {
			//System.out.println(compound.getPart(i).getType());
			Geometry typeCheck = (Geometry)compound.getPart(i);
			
			if(typeCheck instanceof GeoRectangle) {
				GeoRectangle rec = (GeoRectangle) typeCheck;
				
				GeoStyle style = new GeoStyle();
				if(idx == 0) {	
					positionY = rec.getInnerPoint().y;
					positionYMap.put(idx, positionY);
				}else {
					if(idx == 1) {
						positionY = positionY + (legend.getHeight()/2)-170;
					}
					
					rec.setWidth(100);
					rec.setHeight(40);
					
					// 범례 항목에 테두리
					style.setLineColor(new Color(0,0,0));
	                style.setLineWidth(0.01);
	                style.setFillForeColor(rec.getStyle().getFillForeColor());
	                style.setFillBackColor(rec.getStyle().getFillBackColor());
	                
	                
	                rec.setStyle(style);
					
					rec.setCenter(new Point2D(positionX, positionY - ((idx -1) * 60) + gpc));
					
					positionYMap.put(idx,  positionY - ((idx -1) * 60) + gpc);
				}
				idx++;
			}
			
			if((typeCheck instanceof GeoText)) {
				GeoText text = (GeoText) typeCheck;
				
				if(text.getText().matches("<범 례>")) {
					TextPart part = new TextPart();
					part.setText(text.getText());
					part.setX(200);
					
					positionY = positionYMap.get(0);
					double legendHeight = positionY + (legend.getHeight()/2)-30;
					part.setY(legendHeight);
					
					//Text Style Set
					TextStyle style = new TextStyle();
					style.setFontWidth(5);
					style.setFontHeight(5);
					style.setSizeFixed(true);
					style.setFontName("Malgun Gothic");
					text.setTextStyle(style);
					text.setPart(0, part);
				}else {
					TextPart part = new TextPart();
					
					if(text.getText().contains("_y_")) {
						part.setText("타당성평가노선");
						part.setX(positionX+120);
						part.setY(lineHeight+10);
					}else if(text.getText().contains("_blue_")) {
						part.setText("수정노선");
						part.setX(positionX+120);
						part.setY(lineHeight+10);
					}else {
						part.setText(text.getText());
						positionY = positionYMap.get(tIdx);
						part.setX(positionX+120);
						part.setY(positionY+10);
					}

					TextStyle style = new TextStyle();
					style.setFontWidth(3);
					style.setFontHeight(3);
					style.setSizeFixed(true);
					style.setFontName("Malgun Gothic");
					text.setTextStyle(style);
					text.setPart(0, part);
					
					tIdx++;
					
				}
			}
			
			if(typeCheck instanceof GeoLine) {
				GeoLine line = (GeoLine) typeCheck;
				
				GeoStyle style = new GeoStyle();
				
				if(lineType.equals("타당성평가노선")) {
					style.setLineColor(new Color(255,255,0));
					style.setLineWidth(0.5);
					line.setStyle(style);
				}else if(lineType.equals("수정노선")) {
					style.setLineColor(new Color(0,0,255));
					style.setLineWidth(0.5);
					line.setStyle(style);
				}
				
				
				GeoRectangle rec = new GeoRectangle();
				rec.setWidth(100);
				rec.setHeight(40);
				
				positionY = positionYMap.get(0);
				lineHeight = positionY + (legend.getHeight()/2)-120+gpc;
				rec.setCenter(new Point2D(positionX, lineHeight));
				line.resize(rec.getBounds());
				
				idx++;
			}
		}
		legend.setCenter(new Point2D(target.getInnerPoint().x-10, legend.getHeight()/2+20+gpc));
		return legend;
	}
	
	/**
	 * 지도 이미지 저장 export JPG, DPI150
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO saveMapImageToJpg(AnalFileVO vo) throws Exception{
		LOGGER.info("지도 이미지 저장 시작");
		String saveFileNm = null;
		AnalFileVO fileVo = new AnalFileVO();
		
		BeanUtils.copyProperties(vo,fileVo);
		
		if(mapLayoutcontrol != null) {
			try {
				//fileVo = SuperMapBasicUtils.getSavePath(analId);
				//analisis/img/2022/10/21/
				String strePathString = fileVo.getFileStreCours();
				String streFileNm = fileVo.getStreFileNm();
				String streFullPath = strePathString + File.separator + streFileNm;
				
				boolean status = mapLayoutcontrol.getMapLayout().printToFile(streFullPath, PrintFileType.JPG, 300);
				if(status) {
					saveFileNm = streFullPath.concat(".jpg");
					fileVo.setFileExtsn("jpg");//파일확장자
					LOGGER.info("지도 이미지 저장 완료");
				}else {
					LOGGER.error("분석이미지 저장을 실패하였습니다.");
				}
			} catch (Exception e) {
				LOGGER.error("지도 이미지 저장 : "+e.getMessage());
			}
			
		}
		System.out.println(saveFileNm);
		return fileVo;
	}
	
	/**
	 * Map 삭제
	 * @param mapNm
	 */
	public void deleteMap(String mapNm) {
		//workspace.getMaps().get
		workspace.getMaps().remove(mapNm);
	}
	
	/**
	 * Map에 래스터 데이터셋 추가
	 * @param dataset
	 * @param mapName
	 * @param caption
	 * @return
	 */
	private boolean addLayerSingleBandGrid(Dataset dataset, String mapName, String caption) {
		try {
			DatasetGrid grid = (DatasetGrid)dataset;
			System.out.println(grid.getName());
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			
			LayerSettingGrid lsg = new LayerSettingGrid();
			lsg.setSpecialValue(-9999.0);
			lsg.setSpecialValueTransparent(true);
			
			Layer layer = map.getLayers().add(grid,lsg, true);
			lsg = (LayerSettingGrid)layer.getAdditionalSetting();
			
			lsg.setColorDictionary(SuperMapBasicUtils.getColorDictionary(caption));
			
			if(caption.matches("hillshade.*")) {
				lsg.setOpaqueRate(40);
			}
			
			map.viewEntire();
			layer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			
			map.close();
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return true;
	}
	
	/**
	 * Map에 이미지 데이터셋 추가
	 * @param dataset
	 * @param mapName
	 * @param caption
	 * @return
	 */
	private boolean addLayerImage(Dataset dataset, String mapName, String caption) {
		try {
			DatasetImage image = (DatasetImage)dataset;
			System.out.println(image.getName());
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			
			LayerSettingImage lsi = new LayerSettingImage();
			//lsi.
			//lsg.setSpecialValue(-9999.0);
			//lsg.setSpecialValueTransparent(true);
			
			Layer layer = map.getLayers().add(image,lsi, true);
			//lsg = (LayerSettingGrid)layer.getAdditionalSetting();

			//lsg.setSpecialValueColor(Color.WHITE);
			//lsg.setColorDictionary(getColorDictionary(caption));
			
			map.viewEntire();
			layer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			
			map.close();
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return true;
	}
	
	private boolean addLayerThemeRangeGrid(Dataset dataset, String mapName, String caption) {
		try {
			DatasetGrid grid =(DatasetGrid) dataset;
			System.out.println(grid.getName());
			
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			
			ThemeGridRange themeGridRange = SuperMapBasicUtils.getThemeGridRange(caption);
			//ThemeRange themeRange = 
			
			Layer themeLayer = map.getLayers().add(grid,themeGridRange,true);
			
			//map.viewEntire();
			//vectorlayer.setVisible(false);
			themeLayer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			map.refresh();
			map.close();
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return true;
	}
	
	/**
	 * Map에 테마맵 추가
	 * @param dataset
	 * @param mapName
	 * @param caption
	 * @return
	 */
	private boolean addLayerThemeUniqueVector(Dataset dataset, String mapName, String caption) {
		try {
			DatasetVector vector = (DatasetVector)dataset;
			System.out.println(vector.getName());
			
			Recordset record = vector.query("",CursorType.STATIC);
			LinkedHashSet<String> exprList = new LinkedHashSet<String>();
			
			if(record.getRecordCount() > 0 ) {
				do {
					//String expr = (String)record.getFieldValue(getUniqueExpr(caption));
					Object getFieldValue = record.getFieldValue(SuperMapBasicUtils.getUniqueExpr(caption));
					
					String expr = getFieldValue != null ? getFieldValue.toString() : null;
					if(expr != null) {
						exprList.add(expr);
					}
				}while(record.moveNext());
			}
			record.dispose();
			
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			
			ThemeUnique themeUnique = SuperMapBasicUtils.getThemeUnique(caption,exprList);
			//Layer vectorlayer = map.getLayers().add(vector, true);
			Layer themeLayer = map.getLayers().add(vector,themeUnique,true);
			
			//map.viewEntire();
			//vectorlayer.setVisible(false);
			themeLayer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			map.refresh();
			map.close();
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 데이터셋 생성
	 * @param datasetType
	 * @param name
	 */
	private void createDataset(String datasetType,String name) {
		if(!mDatasets.isAvailableDatasetName(name)) {
			mDatasets.delete(name);
		}
		
		if(datasetType.equals("Image")) {
			DatasetImageInfo datasetImageInfo = new DatasetImageInfo();
			datasetImageInfo.setName(name);
			datasetImageInfo.setBlockSizeOption(BlockSizeOption.BS_128);
			datasetImageInfo.setHeight(200);
			datasetImageInfo.setWidth(200);
			datasetImageInfo.setEncodeType(EncodeType.NONE);
			
			mDatasets.create(datasetImageInfo);
		}else if(datasetType.equals("Grid")) {
			DatasetGridInfo datasetGridInfo = new DatasetGridInfo();
			datasetGridInfo.setName(name);
			datasetGridInfo.setBlockSizeOption(BlockSizeOption.BS_128);
			datasetGridInfo.setHeight(200);
			datasetGridInfo.setWidth(200);
			datasetGridInfo.setNoValue(1.0);
			datasetGridInfo.setPixelFormat(PixelFormat.SINGLE);
			datasetGridInfo.setEncodeType(EncodeType.LZW);
			
			mDatasets.create(datasetGridInfo);
		}else {
			DatasetVectorInfo datasetVectorInfo = new DatasetVectorInfo();
			
			if(datasetType.equals("Point")) {
				datasetVectorInfo.setType(DatasetType.POINT);
				datasetVectorInfo.setName(name);
			}else if(datasetType.equals("Line")) {
				datasetVectorInfo.setType(DatasetType.LINE);
				datasetVectorInfo.setName(name);
			}else if(datasetType.equals("Region")) {
				datasetVectorInfo.setType(DatasetType.REGION);
				datasetVectorInfo.setName(name);
			}else if(datasetType.equals("Text")) {
				datasetVectorInfo.setType(DatasetType.TEXT);
				datasetVectorInfo.setName(name);
			}else if(datasetType.equals("Cad")) {
				datasetVectorInfo.setType(DatasetType.CAD);
				datasetVectorInfo.setName(name);
			}else if(datasetType.equals("Tabular")) {
				datasetVectorInfo.setType(DatasetType.TABULAR);
				datasetVectorInfo.setName(name);
			}
			
			mDatasets.create(datasetVectorInfo);
		}
	}
	
	/**
	 * 쉐이프파일 업로드 저장
	 * @param url 파일경로
	 * @param vectorName 테이블명
	 * @param delAll 전체삭제여무
	 * @return
	 * @throws Exception
	 */
	public JSONObject uploadInsertShape(String url, String vectorName, boolean delAll) throws Exception{
		returnLog = new JSONObject();
		boolean processed = true;
		
		try {
			getConnectionInfo();
			//테이블 존재여부 확인
			if(datasets.contains(vectorName)) {
				//datasets.delete(dataSetVectorNmae); //테이블 삭제
				dataset = (DatasetVector) datasets.get(vectorName);
				
				if(delAll) {
					LOGGER.info("dataset truncate start...");
					processed = dataset.truncate();//데이터셋 레코드 전체 삭제
					LOGGER.info("dataset truncate end...");
				}
				
				if(processed) {
					ImportResult result = importShpToDb(url,vectorName);
					
	                if(result.getFailedSettings().length == 0) {
	                	LOGGER.info("등록완료.");
	                	returnLog.put("status", "success");
			        	returnLog.put("message", "등록완료.");
	                }else {
	                	LOGGER.info("저장실패.");
	                	returnLog.put("status", "fail");
			        	returnLog.put("message", "등록실패.");
	                }
				}else {
					returnLog.put("status", "fail");
					returnLog.put("message", "랭크 데이터를 삭제하지 못했습니다.\n관리자에게 문의하세요.");
				}
			}else {
				returnLog.put("status", "fail");
				returnLog.put("message", "테이블을 찾을 수 없습니다\n관리자에게 문의하세요.");
			}
		} catch (Exception e) {
			returnLog.put("error", e.getLocalizedMessage());
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			closeConnection();
		}
		
		return returnLog;
	}
	
	/**
	 * 쉐이프파일 임포트
	 * @param url
	 * @param vectorName
	 * @return
	 * @throws Exception
	 */
	public ImportResult importShpToDb(String url, String vectorName) throws Exception{
		ImportSettingSHP importSettingSHP = new ImportSettingSHP();
        importSettingSHP.setAttributeIgnored(false);
		
        importSettingSHP.setImportMode(ImportMode.APPEND);
        importSettingSHP.setSourceFilePath(url);
        importSettingSHP.setSourceFileCharset(Charset.UTF8);

        importSettingSHP.setTargetDatasource(datasource);
        importSettingSHP.setTargetDatasetName(vectorName);

        DataImport importer = new DataImport();
        importer.getImportSettings().add(importSettingSHP);
        ImportResult result = importer.run();
        
        return result;
	}
}