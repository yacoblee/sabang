package or.sabang.utl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.supermap.analyst.spatialanalyst.BufferAnalyst;
import com.supermap.analyst.spatialanalyst.BufferAnalystParameter;
import com.supermap.analyst.spatialanalyst.BufferEndType;
import com.supermap.analyst.spatialanalyst.BufferRadiusUnit;
import com.supermap.analyst.spatialanalyst.ConversionAnalyst;
import com.supermap.analyst.spatialanalyst.ConversionAnalystParameter;
import com.supermap.analyst.spatialanalyst.GridStatisticsMode;
import com.supermap.analyst.spatialanalyst.OverlayAnalyst;
import com.supermap.analyst.spatialanalyst.OverlayAnalystParameter;
import com.supermap.analyst.spatialanalyst.RasterClip;
import com.supermap.analyst.spatialanalyst.SmoothMethod;
import com.supermap.analyst.spatialanalyst.StatisticsAnalyst;
import com.supermap.analyst.spatialanalyst.VectorClip;
import com.supermap.analyst.spatialanalyst.ZonalStatisticsAnalystParameter;
import com.supermap.analyst.spatialanalyst.ZonalStatisticsAnalystResult;
import com.supermap.analyst.terrainanalyst.HydrologyAnalyst;
import com.supermap.analyst.terrainanalyst.StreamOrderType;
import com.supermap.data.BlockSizeOption;
import com.supermap.data.Charset;
import com.supermap.data.ColorDictionary;
import com.supermap.data.ColorGradientType;
import com.supermap.data.Colors;
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
import com.supermap.data.Feature;
import com.supermap.data.FieldInfo;
import com.supermap.data.FieldInfos;
import com.supermap.data.FieldType;
import com.supermap.data.GeoCompound;
import com.supermap.data.GeoCoordSys;
import com.supermap.data.GeoLegend;
import com.supermap.data.GeoLine;
import com.supermap.data.GeoMap;
import com.supermap.data.GeoPoint;
import com.supermap.data.GeoRectangle;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.GeoText;
import com.supermap.data.Geometry;
import com.supermap.data.GeometryType;
import com.supermap.data.PixelFormat;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.PrjParameter;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;
import com.supermap.data.Size2D;
import com.supermap.data.StatisticsResult;
import com.supermap.data.TextAlignment;
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
import com.supermap.mapping.LabelBackShape;
import com.supermap.mapping.Layer;
import com.supermap.mapping.LayerSettingGrid;
import com.supermap.mapping.LayerSettingImage;
import com.supermap.mapping.LayerSettingVector;
import com.supermap.mapping.ThemeGridRange;
import com.supermap.mapping.ThemeGridRangeItem;
import com.supermap.mapping.ThemeLabel;
import com.supermap.mapping.ThemeUnique;
import com.supermap.mapping.ThemeUniqueItem;
import com.supermap.ui.Action;
import com.supermap.ui.MapLayoutControl;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.service.SysComptVO;
import or.sabang.sys.service.SysFieldInfoVO;
import or.sabang.sys.service.ZonalStatisticVO;

public class SupMapCommonUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SupMapCommonUtils.class);
	
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
	private String processId = null;
	
	public SupMapCommonUtils() {
		try {
			processId = SuperMapBasicUtils.getDatasetUuid();
			getConnectionInfo(processId);
		} catch (Exception e) {
			LOGGER.error("워크스페이스 생성 중 오류가 발생하였습니다.");
		}
	}

	public SupMapCommonUtils(String analId) {
		if(analId != null) {
			processId = analId;
			try {
				getConnectionInfo(processId);
			} catch (Exception e) {
				LOGGER.error("워크스페이스 생성 중 오류가 발생하였습니다.");
			}
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
	 * 조사완료정보 쉐이프파일 다운로드
	 * @param svyType
	 * @param svyWhere
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO exportSvyComptShp(HashMap<String, Object> maps) throws Exception{
		LOGGER.info("공간정보 다운로드 : ".concat(processId));
		
		String svyType = maps.get("type").toString();
		
		AnalFileVO vo = null;
		String datasetNm = SuperMapBasicUtils.getLayerName(svyType);
		String svyWhere = SuperMapBasicUtils.getQueryString(maps);
		
		try {
			copyPggisToMemoryUdb(datasetNm,datasetNm,svyWhere);
			if(mDatasets.contains(datasetNm)) {
				vo = SuperMapBasicUtils.getSavePath(processId,SuperMapBasicUtils.shpMidDir);
				mExportUdbToFile(datasetNm, vo, "shp");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
			closeWorkspace();
		}
		return vo;
	}
	
	public AnalFileVO exportSvyComptShp(String svyType, String smidList) throws Exception{
		LOGGER.info("공간정보 다운로드 : ".concat(processId));
		
		AnalFileVO vo = null;
		String datasetNm = SuperMapBasicUtils.getLayerName(svyType);
		String svyWhere = SuperMapBasicUtils.getQueryString(smidList);
		
		try {
			copyPggisToMemoryUdb(datasetNm,datasetNm,svyWhere);
			if(mDatasets.contains(datasetNm)) {
				vo = SuperMapBasicUtils.getSavePath(processId,SuperMapBasicUtils.shpMidDir);
				mExportUdbToFile(datasetNm, vo, "shp");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
			closeWorkspace();
		}
		return vo;
	}
	
	
	
	/** 
	 * 파일 저장
	 * @param vectorName
	 * @param outputPath
	 * @throws Exception
	 */
	public AnalFileVO exportDbToFile(String datasetName, String query, String[] ignoreFields) throws Exception{
		mDatasets.deleteAll();
		
		AnalFileVO vo = null;
		try {
			//getConnectionInfo();
			Dataset ds = null;
			FileType fType = null;
			
			//String uniquId = getDatasetUuid();
			//getConnectionInfo(uniquId);
			
			if(datasets.contains(datasetName)) {
				ds = datasets.get(datasetName);
				DatasetType datasetType = ds.getType();
				
				if(datasetType.equals(DatasetType.GRID) || datasetType.equals(DatasetType.IMAGE)) {
					fType = FileType.TIF;
				}else {
					fType = FileType.SHP;
					if(query != null && query.length() > 0) {
						DatasetVector queryDsVector = (DatasetVector) ds;
						Recordset rs = queryDsVector.query(query,CursorType.STATIC);
						DatasetVector copyDatastVector = (DatasetVector) mDatasets.createFromTemplate(mDatasets.getAvailableDatasetName(datasetName.concat("_"+processId)),queryDsVector);
						copyDatastVector.append(rs);
						copyDatastVector.close();
						
						rs.dispose();
						ds.close();
						
						ds = mDatasets.get(datasetName.concat("_"+processId));
					}
				}
				
				vo = SuperMapBasicUtils.getSavePath(processId,SuperMapBasicUtils.shpMidDir);
				vo.setOrignlFileNm(datasetName);
				vo.setAnalType("shp");
				//vo.setMstId(Integer.valueOf(mstId));
				//vo.setSldId(sldId);
				
				File file = new File(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
				file.mkdirs();
				
				ExportSetting exportSetting = new ExportSetting();
				
				if(ignoreFields != null) {
					exportSetting.setIgnoreFieldNames(ignoreFields);
				}
				
				exportSetting.setOverwrite(true);
				exportSetting.setTargetFileCharset(Charset.UTF8);
				exportSetting.setTargetFileType(fType);
				exportSetting.setTargetFilePath(vo.getFileStreCours()+File.separator+vo.getStreFileNm()+File.separator+ds.getName().replace("_"+processId, "")+".".concat(fType.toString()));
				exportSetting.setSourceData(ds);

				DataExport dataExport = new DataExport();
				dataExport.getExportSettings().add(exportSetting);
				ExportResult exportRslt = dataExport.run();
				
//				if(exportRslt.getSucceedSettings().length > 0) {
//					if(deleted) {
//						if(uDatasets.contains(datasetName.concat("_"+uniquId)))
//						uDatasets.delete(datasetName.concat("_"+uniquId));
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
		} finally {
			closeConnection();
		}
		
		return vo;
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
	private void mExportUdbToFile(String datasetName, AnalFileVO vo, String extension) throws Exception{
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
//					if(SuperMapBasicUtils.deleted) {
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
	private void closeConnection() throws Exception{
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
	
	private void closeWorkspace() {
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
}