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

public class VytEcbSupMapUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(VytEcbSupMapUtils.class);
	
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
	
	public VytEcbSupMapUtils() {
		try {
			processId = SuperMapBasicUtils.getDatasetUuid();
			getConnectionInfo(processId);
		} catch (Exception e) {
			LOGGER.error("워크스페이스 생성 중 오류가 발생하였습니다.");
		}
	}

	public VytEcbSupMapUtils(String analId) {
		if(analId != null) {
			processId = analId;
			try {
				getConnectionInfo(processId);
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
		conn.setServer(SuperMapBasicUtils.superMapStoragePath + SuperMapBasicUtils.superMapMidDir + File.separator + SuperMapBasicUtils.smwuFileNm);

		workspace.open(conn);
		
		//작업공간을 열고 데이터소스 가져오기
		datasources = workspace.getDatasources();
		
		if(datasources.contains(SuperMapBasicUtils.pGisAlias)) {
			datasource = datasources.get(SuperMapBasicUtils.pGisAlias);
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
	 * 사방사업 타당성평가 대상지위치(시도)
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param adminCode
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbSidoClipImg(String mstId,String sldId,String analId,String adminCode) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;

		String mapId = "Map_sido_".concat(analId);
		String ecrtcnlDatasetNm = SuperMapBasicUtils.getLayerName("ecrtcnl");//"tf_feis_ecrtcnl";
		String sidoDatasetNm = SuperMapBasicUtils.getLayerName("sido");
		
		String sidoCode = adminCode.substring(0,2);
		
		String ecrtcnlQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		String sidoQuery = "ctprvn_cod = '".concat(sidoCode).concat("'");
		
		String ecrtcnlCopyDatasetNm = "ecrtcnl_".concat(analId);
		String sidoCopyDatasetNm = "ecb_sido_".concat(analId);
		
		DatasetVector ecrtcnlCopyDatastVector = null;
		DatasetVector sidoDatasetVector = null;
		DatasetVector sidoCopyDatasetVector = null;
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유출구 데이터 DB에서 UDB로 복사
			copyPggisToMemoryUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,ecrtcnlQuery);
			//copyPggisToUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			copyPggisToMemoryUdb(sidoDatasetNm,sidoCopyDatasetNm,sidoQuery);
			
			//지적데이터셋 자르기
			if(mDatasets.contains(ecrtcnlCopyDatasetNm)) {
				
				sidoDatasetVector = (DatasetVector) datasets.get(sidoDatasetNm);
				
				Rectangle2D bound = sidoDatasetVector.getBounds();
				double maxlength = 100;
				//이미지 영역설정
				newBound = new Rectangle2D();
				newBound.setTop(bound.getTop()+maxlength);
				newBound.setBottom(bound.getBottom()-maxlength);
				newBound.setLeft(bound.getLeft()-maxlength);
				newBound.setRight(bound.getRight()+maxlength);
				
				ecrtcnlCopyDatastVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
				sidoCopyDatasetVector = (DatasetVector) mDatasets.get(sidoCopyDatasetNm);
			}
			
			addLayerSingleBandVector(sidoDatasetVector,mapId,"ecb_".concat(sidoDatasetNm));
			addLayerSingleBandVector(sidoCopyDatasetVector,mapId,sidoCopyDatasetNm);
			addLayerThemeLabelVector(sidoCopyDatasetVector,mapId,sidoCopyDatasetNm);
			
			vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm("대상지(시도)");
			vo.setAnalType("대상지(시도)");
			vo.setMstId(Integer.valueOf(mstId));
			vo.setSldId(sldId);
			
			if(setMapLayoutControl("tp_none",mapId,newBound)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			
			sidoDatasetVector.close();
			sidoCopyDatasetVector.close();
			ecrtcnlCopyDatastVector.close();
			
			mExportUdbToFile(sidoCopyDatasetNm, vo, "shp");
			mExportUdbToFile(ecrtcnlCopyDatasetNm, vo, "shp");
			
			vo.setFileExtsn("zip");
			list.add(vo);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 대상지위치(시군구)
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param adminCode
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbSignguClipImg(String mstId,String sldId,String analId,String adminCode) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;

		String mapId = "Map_sido_".concat(analId);
		String ecrtcnlDatasetNm = SuperMapBasicUtils.getLayerName("ecrtcnl");//"tf_feis_ecrtcnl";
		String signguDatasetNm = SuperMapBasicUtils.getLayerName("signgu");
		
		String sidoCode = adminCode.substring(0,2);
		String signguCode = adminCode.substring(0,5);
		
		String ecrtcnlQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		String signgusQuery = "ctprvn_cod = '".concat(sidoCode).concat("'");
		String signguQuery = "signgu_cod = '".concat(signguCode).concat("'");
		
		String ecrtcnlCopyDatasetNm = "ecrtcnl_".concat(analId);
		String signgusCopyDatasetNm = "ecb_signgus_".concat(analId);
		String signguCopyDatasetNm = "ecb_signgu_".concat(analId);
		
		DatasetVector ecrtcnlCopyDatastVector = null;
		DatasetVector signgusCopyDatasetVector = null;
		DatasetVector signguCopyDatasetVector = null;
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유출구 데이터 DB에서 UDB로 복사
			copyPggisToMemoryUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,ecrtcnlQuery);
			//copyPggisToUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			copyPggisToMemoryUdb(signguDatasetNm,signgusCopyDatasetNm,signgusQuery);
			copyPggisToMemoryUdb(signguDatasetNm,signguCopyDatasetNm,signguQuery);
			
			//지적데이터셋 자르기
			if(mDatasets.contains(ecrtcnlCopyDatasetNm)) {
				
				signgusCopyDatasetVector = (DatasetVector) mDatasets.get(signgusCopyDatasetNm);
				
				Rectangle2D bound = signgusCopyDatasetVector.getBounds();
				double maxlength = 100;
				//이미지 영역설정
				newBound = new Rectangle2D();
				newBound.setTop(bound.getTop()+maxlength);
				newBound.setBottom(bound.getBottom()-maxlength);
				newBound.setLeft(bound.getLeft()-maxlength);
				newBound.setRight(bound.getRight()+maxlength);
				
				ecrtcnlCopyDatastVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
				signguCopyDatasetVector = (DatasetVector) mDatasets.get(signguCopyDatasetNm);
			}
			
			addLayerSingleBandVector(signgusCopyDatasetVector,mapId,signgusCopyDatasetNm);
			addLayerSingleBandVector(signguCopyDatasetVector,mapId,signguCopyDatasetNm);
			addLayerThemeLabelVector(signgusCopyDatasetVector,mapId,signgusCopyDatasetNm);
			
			vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm("대상지(시군구)");
			vo.setAnalType("대상지(시군구)");
			vo.setMstId(Integer.valueOf(mstId));
			vo.setSldId(sldId);
			
			if(setMapLayoutControl("tp_none",mapId,newBound)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			
			signgusCopyDatasetVector.close();
			signguCopyDatasetVector.close();
			ecrtcnlCopyDatastVector.close();
			
			mExportUdbToFile(signgusCopyDatasetNm, vo, "shp");
			mExportUdbToFile(signguCopyDatasetNm, vo, "shp");
			mExportUdbToFile(ecrtcnlCopyDatasetNm, vo, "shp");
			
			vo.setFileExtsn("zip");
			list.add(vo);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 수치지형도
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbSldLocationTopoMapImg(String mstId,String sldId,String analId,String lonLatText) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		//String saveImgNm = null;
		String mapId = "Map_".concat(analId);
		String ecrtcnlDatasetNm = SuperMapBasicUtils.getLayerName("ecrtcnl");
		String ctrlnDatasetNm = SuperMapBasicUtils.getLayerName("ctrln");
		String roadDatasetNm = SuperMapBasicUtils.getLayerName("road");
		String riverDatasetNm = SuperMapBasicUtils.getLayerName("river");
		String buildingDatasetNm = SuperMapBasicUtils.getLayerName("building");
		String liDatasetNm = SuperMapBasicUtils.getLayerName("li");
		
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		String ecrtcnlCopyDatasetNm = "ecrtcnl_".concat(analId);
		String ctrlnCopyDatasetNm = "ecb_ctrln_".concat(analId);
		String roadCopyDatasetNm = "ecb_road_".concat(analId);
		String riverCopyDatasetNm = "ecb_river_".concat(analId); 
		String buildingCopyDatasetNm = "ecb_building_".concat(analId); 
		String liCopyDatasetNm = "ecb_li_".concat(analId); 
		
		DatasetVector ecrtcnlCopyDatasetVector = null;
		DatasetVector ctrlnClipDatasetVector = null;
		DatasetVector roadClipDatasetVector = null;
		DatasetVector riverClipDatasetVector = null;
		DatasetVector buildingClipDatasetVector = null;
		DatasetVector liClipDatasetVector = null;
		
		Rectangle2D newBound = null;
		Point2D newCenter = null;
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유출구 데이터 DB에서 UDB로 복사
			copyPggisToMemoryUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			
			//지적데이터셋 자르기
			if(mDatasets.contains(ecrtcnlCopyDatasetNm)) {
				ecrtcnlCopyDatasetVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
				Recordset mRecordset = ecrtcnlCopyDatasetVector.query("",CursorType.STATIC);
				
				double addLen = 3500.0;
				Rectangle2D bound = mRecordset.getGeometry().getBounds();
				
				newBound = new Rectangle2D();
				//클립할 영역 설정(이미지 영역보다 조금 더 많이 자름)
				newBound.setTop(bound.getTop()+addLen);
				newBound.setBottom(bound.getBottom()-addLen);
				newBound.setLeft(bound.getLeft()-addLen);
				newBound.setRight(bound.getRight()+addLen);
				
				GeoRectangle geoRect = new GeoRectangle(newBound, 0);
				GeoRegion region = geoRect.convertToRegion();

				mRecordset.dispose();
				
				ctrlnClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(ctrlnDatasetNm), region, true, false, mDatasource, ctrlnCopyDatasetNm);
				roadClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(roadDatasetNm), region, true, false, mDatasource, roadCopyDatasetNm);
				riverClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(riverDatasetNm), region, true, false, mDatasource, riverCopyDatasetNm);
				buildingClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(buildingDatasetNm), region, true, false, mDatasource, buildingCopyDatasetNm);
				liClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(liDatasetNm), region, true, false, mDatasource, liCopyDatasetNm);
			}
			
			FieldInfo fieldInfo = new FieldInfo();
			fieldInfo.setName("coord");
			fieldInfo.setType(FieldType.TEXT);
			ecrtcnlCopyDatasetVector.getFieldInfos().add(fieldInfo);
			
			Recordset ecrtcnlRecordset = ecrtcnlCopyDatasetVector.query("",CursorType.DYNAMIC);
			
			newCenter = ecrtcnlRecordset.getGeometry().getInnerPoint();
			
			ecrtcnlRecordset.edit();
			ecrtcnlRecordset.setFieldValue("coord", lonLatText);
			ecrtcnlRecordset.update();
			ecrtcnlRecordset.close();
			
			addLayerSingleBandVector(ctrlnClipDatasetVector,mapId,ctrlnCopyDatasetNm);
			addLayerSingleBandVector(roadClipDatasetVector,mapId,roadCopyDatasetNm);
			addLayerSingleBandVector(riverClipDatasetVector,mapId,riverCopyDatasetNm);
			addLayerSingleBandVector(buildingClipDatasetVector,mapId,buildingCopyDatasetNm);
			addLayerSingleBandVector(liClipDatasetVector,mapId,liCopyDatasetNm);
			addLayerSingleBandVector(ecrtcnlCopyDatasetVector,mapId,ecrtcnlCopyDatasetNm);
			addLayerThemeLabelVector(ecrtcnlCopyDatasetVector,mapId,"ecb_ecrtcnlcoord");
			
			vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm("대상지(수치지형도)");
			vo.setAnalType("대상지(수치지형도)");
			vo.setMstId(Integer.valueOf(mstId));
			vo.setSldId(sldId);
			
			if(setMapLayoutControl("locTemplate",mapId,newCenter,0.000049392)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			roadClipDatasetVector.close();
			ecrtcnlCopyDatasetVector.close();
			
			mExportUdbToFile(ctrlnCopyDatasetNm, vo, "shp");
			mExportUdbToFile(roadCopyDatasetNm, vo, "shp");
			mExportUdbToFile(riverCopyDatasetNm, vo, "shp");
			mExportUdbToFile(buildingCopyDatasetNm, vo, "shp");
			mExportUdbToFile(liCopyDatasetNm, vo, "shp");
			mExportUdbToFile(ecrtcnlCopyDatasetNm, vo, "shp");
			
			vo.setFileExtsn("zip");
			list.add(vo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 관계지적도
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbCadastralMapImg(String mstId,String sldId,String analId,boolean cadastralPnt,double scale) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		//String saveImgNm = null;
		String mapId = "Map_".concat(analId);
		String ecrtcnlDatasetNm = SuperMapBasicUtils.getLayerName("ecrtcnl");//"tf_feis_ecrtcnl";
		String lgstrDatasetNm = SuperMapBasicUtils.getLayerName("lgstr");//지적
		String analNm = "관계지적도";
		
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		String ecrtcnlCopyDatasetNm = "ecrtcnl_".concat(analId);
		String lgstrCopyDatasetNm = "ecb_lgstr_".concat(analId);
		
		DatasetVector ecrtcnlCopyDatastVector = null;
		DatasetVector lgstrClipDatasetVector = null;
		
		Rectangle2D newBound = null;
		Point2D newCenter = null;
		
		if(scale == 0.001029) {
			analNm = analNm.concat("(1/1200)");
		}else {
			analNm = analNm.concat("(1/2400)");
		}
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유출구 데이터 DB에서 UDB로 복사
			copyPggisToMemoryUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			
			//지적데이터셋 자르기
			if(mDatasets.contains(ecrtcnlCopyDatasetNm)) {
				
				ecrtcnlCopyDatastVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
				Recordset mRecordset = ecrtcnlCopyDatastVector.query("",CursorType.STATIC);
				newCenter = mRecordset.getGeometry().getInnerPoint();
				
				double addLen = 350.0;
				Rectangle2D bound = mRecordset.getGeometry().getBounds();
				
				newBound = new Rectangle2D();
				//클립할 영역 설정(이미지 영역보다 조금 더 많이 자름)
				newBound.setTop(bound.getTop()+addLen);
				newBound.setBottom(bound.getBottom()-addLen);
				newBound.setLeft(bound.getLeft()-addLen);
				newBound.setRight(bound.getRight()+addLen);
				
				GeoRectangle geoRect = new GeoRectangle(newBound, 0);
				GeoRegion region = geoRect.convertToRegion();

				mRecordset.dispose();
				
				lgstrClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(lgstrDatasetNm), region, true, false, mDatasource, lgstrCopyDatasetNm);
			}
			
			addLayerSingleBandVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
			addLayerThemeLabelVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
			if(cadastralPnt) {
				addLayerSingleBandVector(ecrtcnlCopyDatastVector,mapId,ecrtcnlCopyDatasetNm);
			}
			
			vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm(analNm);
			vo.setAnalType(analNm);
			vo.setMstId(Integer.valueOf(mstId));
			vo.setSldId(sldId);
			
			if(setMapLayoutControl("locTemplate",mapId,newCenter,scale)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			lgstrClipDatasetVector.close();
			ecrtcnlCopyDatastVector.close();
			
			mExportUdbToFile(lgstrCopyDatasetNm, vo, "shp");
			if(cadastralPnt) {
				mExportUdbToFile(ecrtcnlCopyDatasetNm, vo, "shp");
			}
			
			vo.setFileExtsn("zip");
			list.add(vo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 대상지 위치
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbSldLocationImg(String mstId,String sldId,String analId) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		//String saveImgNm = null;
		String mapId = "Map_".concat(analId);
		String ecrtcnlDatasetNm = SuperMapBasicUtils.getLayerName("ecrtcnl");//"tf_feis_ecrtcnl";
		String watershedDatasetNm = SuperMapBasicUtils.getLayerName("watershed");//"tf_feis_watershed";
		String lgstrDatasetNm = SuperMapBasicUtils.getLayerName("lgstr");//지적
		String demDatasetNm = SuperMapBasicUtils.getLayerName("dem");//dem
		String hillshadeDatasetNm = SuperMapBasicUtils.getLayerName("hillshade");//hillshade
		String ctrlnDatasetNm = SuperMapBasicUtils.getLayerName("ctrln");//hillshade
		
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		String ecrtcnlCopyDatasetNm = "ecrtcnl_".concat(analId);
		String watershedCopyDatasetNm = "watershed_".concat(analId);
		String lgstrCopyDatasetNm = "ecb_lgstr_".concat(analId);
		String demCopyDatasetNm = "sdem_".concat(analId);
		String hillshadeCopyDatasetNm = "hillshade_".concat(analId);
		String ctrlnCopyDatasetNm = "ecb_ctrln_".concat(analId);
		
		DatasetVector ecrtcnlCopyDatastVector = null;
		DatasetVector watershedCopyDatasetVector = null;
		DatasetVector lgstrClipDatasetVector = null;
		DatasetVector ctrlnClipDatasetVector = null;
		Dataset demClipDataset = null;
		Dataset hillshadeClipDataset = null;
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유출구 데이터 DB에서 UDB로 복사
			copyPggisToMemoryUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,watershedQuery);
			
			//유역분석 결과 벡터레이어 DB에서 UDB로 복사
			copyPggisToMemoryUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			
			//지적데이터셋 자르기
			if(mDatasets.contains(watershedCopyDatasetNm)) {
				
				watershedCopyDatasetVector = (DatasetVector) mDatasets.get(watershedCopyDatasetNm);
				Recordset mRecordset = watershedCopyDatasetVector.query("",CursorType.STATIC);
				
				double addLen = 0.0;
				Rectangle2D bound = mRecordset.getGeometry().getBounds();
				
				boolean checkLen = bound.getWidth() > bound.getHeight() ? true : false;
				double maxlength = checkLen ? bound.getWidth()*0.1 : bound.getHeight()*0.1;
				
				if(checkLen) {
					addLen = (bound.getWidth() - bound.getHeight())/2;
				}else {
					addLen = (bound.getHeight() - bound.getWidth())/2;
				}
				
				newBound = new Rectangle2D();
				//클립할 영역 설정(이미지 영역보다 조금 더 많이 자름)
				newBound.setTop((checkLen ? bound.getTop()+addLen : bound.getTop())+1000);
				newBound.setBottom((checkLen ? bound.getBottom()-addLen : bound.getBottom())-1000);
				newBound.setLeft((checkLen ? bound.getLeft() : bound.getLeft()-addLen)-1000);
				newBound.setRight((checkLen ? bound.getRight() : bound.getRight()+addLen)+1000);
				
				GeoRectangle geoRect = new GeoRectangle(newBound, 0);
				GeoRegion region = geoRect.convertToRegion();

				mRecordset.dispose();
				
				lgstrClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(lgstrDatasetNm), region, true, false, mDatasource, lgstrCopyDatasetNm);
				ctrlnClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(ctrlnDatasetNm), region, true, false, mDatasource, ctrlnCopyDatasetNm);
				demClipDataset = RasterClip.clip((DatasetGrid)datasource.getDatasets().get(demDatasetNm),region, true, false, mDatasource, demCopyDatasetNm);
				hillshadeClipDataset = RasterClip.clip((DatasetGrid)datasource.getDatasets().get(hillshadeDatasetNm),region, true, false, mDatasource, hillshadeCopyDatasetNm);
				
				//이미지 영역설정
				newBound = new Rectangle2D();
				newBound.setTop(bound.getTop()+maxlength);
				newBound.setBottom(bound.getBottom()-maxlength);
				newBound.setLeft(bound.getLeft()-maxlength);
				newBound.setRight(bound.getRight()+maxlength);
			}
			
			ecrtcnlCopyDatastVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
			
			addLayerSingleBandGrid(demClipDataset, mapId, demCopyDatasetNm);
			addLayerSingleBandGrid(hillshadeClipDataset, mapId, hillshadeCopyDatasetNm);
			addLayerSingleBandVector(ctrlnClipDatasetVector,mapId,ctrlnCopyDatasetNm);
			addLayerSingleBandVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
			addLayerThemeLabelVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
			addLayerSingleBandVector(watershedCopyDatasetVector,mapId,watershedCopyDatasetNm);
			addLayerSingleBandVector(ecrtcnlCopyDatastVector,mapId,ecrtcnlCopyDatasetNm);
			
			vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm("위치도");
			vo.setAnalType("위치도");
			vo.setMstId(Integer.valueOf(mstId));
			vo.setSldId(sldId);
			//vo.setCreatUser(attr.get("creat_user").toString());
			//vo.setFileExtsn("zip");
			
			if(setMapLayoutControl("locTemplate",mapId,newBound)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			demClipDataset.close();
			hillshadeClipDataset.close();
			lgstrClipDatasetVector.close();
			watershedCopyDatasetVector.close();
			ecrtcnlCopyDatastVector.close();
			
			mExportUdbToFile(demCopyDatasetNm, vo, "tif");
			mExportUdbToFile(hillshadeCopyDatasetNm, vo, "tif");
			mExportUdbToFile(ctrlnCopyDatasetNm, vo, "shp");
			mExportUdbToFile(lgstrCopyDatasetNm, vo, "shp");
			mExportUdbToFile(watershedCopyDatasetNm, vo, "shp");
			mExportUdbToFile(ecrtcnlCopyDatasetNm, vo, "shp");
			
			vo.setFileExtsn("zip");
			list.add(vo);
			//"D:\\home\\tomcat\\FEIStorage\\analysis\\20220923\\D9CB988E19CA428FB5AB3C6FFFFD3EB9.png";//
			//String type = "shp";
			//HashMap<String, FileType> hash = manage.getFileType(type);
			//manage.exportDataset(vTest, type, hash);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 대상지 항공사진
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbSldLocationSatImg(String mstId,String sldId,String analId) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_sat_".concat(analId);
		String watershedDatasetNm = SuperMapBasicUtils.getLayerName("watershed");//"tf_feis_watershed";
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		String watershedCopyDatasetNm = "watershed_".concat(analId);
		//String boundaryDatasetNm = "boundary_".concat(analId);
		
		DatasetVector watershedCopyDatasetVector = null;
		//DatasetVector boundaryDatasetVector = null;
		//GeoRegion region = null;
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			//유역분석 결과 벡터레이어 DB에서 UDB로 복사
			copyPggisToMemoryUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			
			//데이터셋 자르기
			if(mDatasets.contains(watershedCopyDatasetNm)) {
				watershedCopyDatasetVector = (DatasetVector) mDatasets.get(watershedCopyDatasetNm);
			}
			
			if(SuperMapBasicUtils.coordSysTranslator(watershedCopyDatasetVector,3857)) {
				
				Recordset mRecordset = watershedCopyDatasetVector.query("",CursorType.STATIC);
				//uRecordset = clipDatasetVector.query("",CursorType.STATIC);
				
				Rectangle2D bound = mRecordset.getGeometry().getBounds();
				
				boolean checkLen = bound.getWidth() > bound.getHeight() ? true : false;
				double maxlength = checkLen ? bound.getWidth()*0.1 : bound.getHeight()*0.1;
				
				newBound = new Rectangle2D();

				newBound.setTop(bound.getTop()+maxlength);
				newBound.setBottom(bound.getBottom()-maxlength);
				newBound.setLeft(bound.getLeft()-maxlength);
				newBound.setRight(bound.getRight()+maxlength);
				
				mRecordset.dispose();
				
//				if(SuperMapBasicUtils.deleted) {
//					uDatasets.delete(watershedCopyDatasetNm);
//				}
				
				vDataset = vDatasets.get("VworldSatellite");
				
				addLayerImage(vDataset,mapId,"vworld");
				addLayerSingleBandVector(watershedCopyDatasetVector,mapId,watershedCopyDatasetNm);
				
				vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
				vo.setOrignlFileNm("위치도(영상)");
				vo.setAnalType("위치도(영상)");
				vo.setMstId(Integer.valueOf(mstId));
				vo.setSldId(sldId);
				
				if(setMapLayoutControl("locTemplate",mapId,newBound)) {
					AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
					list.add(imgVo);
					
					if(SuperMapBasicUtils.deleted) {
						SuperMapBasicUtils.deleteMap(workspace,mapId);
					}
				}
				watershedCopyDatasetVector.close();
				
				mExportUdbToFile(watershedCopyDatasetNm, vo, "shp");
				
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
	 * 사방사업 타당성평가 수계분석(3ha)
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param captionNm
	 * @param mapStyle
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbStream3haClipImg(String mstId,String sldId,String analId) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_river_".concat("_"+analId);
		String watershedDatasetNm = SuperMapBasicUtils.getLayerName("watershed");//"tf_feis_watershed";
		String hillshadeDatasetNm = SuperMapBasicUtils.getLayerName("hillshade");//hillshade
		String stream3hDatasetNm = SuperMapBasicUtils.getLayerName("stream3ha");//stream_3ha
		
		String templateNm = SuperMapBasicUtils.getLayoutTemplateNm("river");
		
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		
		String watershedCopyDatasetNm = "watershed_".concat(analId);
		String stream3haClipDatasetNm = "stream3ha_".concat(analId);
		String hillshadeClipDatasetNm = "hillshade_".concat(analId);
		
		DatasetVector watershedCopyDatasetVector = null;
		DatasetVector stream3haClipDatasetVector = null;
		Dataset hillshadeClipDataset = null;
		
		GeoRegion region = null;
		Rectangle2D bound = null;
		
		vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
		vo.setOrignlFileNm("stream3ha");
		vo.setAnalType("stream3ha");
		vo.setMstId(Integer.valueOf(mstId));
		vo.setSldId(sldId);
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유역분석 결과 벡터레이어 DB에서 UDB로 복사
			copyPggisToMemoryUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			
			//데이터셋 자르기
			if(mDatasets.contains(watershedCopyDatasetNm)) {
				watershedCopyDatasetVector = (DatasetVector) mDatasets.get(watershedCopyDatasetNm);
				Recordset mRecordset = watershedCopyDatasetVector.query("",CursorType.STATIC);
				
				bound = mRecordset.getGeometry().getBounds();
				
				region = new GeoRegion();
				
				while (!mRecordset.isEOF()) {
					for (int i = 0; i < ((GeoRegion)mRecordset.getGeometry()).getPartCount(); i++) {
						region.addPart(((GeoRegion) mRecordset.getGeometry()).getPart(i));
					}
					mRecordset.moveNext();
				}
				mRecordset.dispose();
				
				DatasetVector stream3haData = (DatasetVector)datasource.getDatasets().get(stream3hDatasetNm);
				Dataset hillshadeData = (DatasetGrid)datasource.getDatasets().get(hillshadeDatasetNm);
				
				stream3haClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)stream3haData, region, true, false, mDatasource, stream3haClipDatasetNm);
				hillshadeClipDataset = RasterClip.clip(hillshadeData,region, true, false, mDatasource, hillshadeClipDatasetNm);
				
				stream3haData.close();
				hillshadeData.close();
			}
			
			addLayerSingleBandGrid(hillshadeClipDataset, mapId, hillshadeClipDatasetNm);
			addLayerSingleBandVector(stream3haClipDatasetVector,mapId,stream3haClipDatasetNm);
			addLayerSingleBandVector(watershedCopyDatasetVector,mapId,watershedCopyDatasetNm);
			
			if(setMapLayoutControl(templateNm,mapId,bound)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			
			stream3haClipDatasetVector.close();
			hillshadeClipDataset.close();
			watershedCopyDatasetVector.close();
			
			mExportUdbToFile(stream3haClipDatasetNm, vo, "shp");
			mExportUdbToFile(watershedCopyDatasetNm, vo, "shp");
			mExportUdbToFile(hillshadeClipDatasetNm, vo, "tif");
			
			vo.setFileExtsn("zip");
			list.add(vo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 수계분석(5ha)
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param captionNm
	 * @param mapStyle
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbStream5haClipImg(String mstId,String sldId,String analId) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_river_".concat("_"+analId);
		String watershedDatasetNm = SuperMapBasicUtils.getLayerName("watershed");//"tf_feis_watershed";
		String hillshadeDatasetNm = SuperMapBasicUtils.getLayerName("hillshade");//hillshade
		String stream5hDatasetNm = SuperMapBasicUtils.getLayerName("stream5ha");//stream_5ha
		
		String templateNm = SuperMapBasicUtils.getLayoutTemplateNm("river");
		
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		
		String watershedCopyDatasetNm = "watershed_".concat(analId);
		String stream5haClipDatasetNm = "stream5ha_".concat(analId);
		String hillshadeClipDatasetNm = "hillshade_".concat(analId);
		
		DatasetVector watershedCopyDatasetVector = null;
		DatasetVector stream5haClipDatasetVector = null;
		Dataset hillshadeClipDataset = null;
		
		GeoRegion region = null;
		Rectangle2D bound = null;
		
		vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
		vo.setOrignlFileNm("stream5ha");
		vo.setAnalType("stream5ha");
		vo.setMstId(Integer.valueOf(mstId));
		vo.setSldId(sldId);
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유역분석 결과 벡터레이어 DB에서 UDB로 복사
			copyPggisToMemoryUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			
			//데이터셋 자르기
			if(mDatasets.contains(watershedCopyDatasetNm)) {
				watershedCopyDatasetVector = (DatasetVector) mDatasets.get(watershedCopyDatasetNm);
				Recordset mRecordset = watershedCopyDatasetVector.query("",CursorType.STATIC);
				
				bound = mRecordset.getGeometry().getBounds();
				
				region = new GeoRegion();
				
				while (!mRecordset.isEOF()) {
					for (int i = 0; i < ((GeoRegion)mRecordset.getGeometry()).getPartCount(); i++) {
						region.addPart(((GeoRegion) mRecordset.getGeometry()).getPart(i));
					}
					mRecordset.moveNext();
				}
				mRecordset.dispose();
				
				DatasetVector stream5haData = (DatasetVector)datasource.getDatasets().get(stream5hDatasetNm);
				Dataset hillshadeData = (DatasetGrid)datasource.getDatasets().get(hillshadeDatasetNm);
				
				stream5haClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)stream5haData, region, true, false, mDatasource, stream5haClipDatasetNm);
				hillshadeClipDataset = RasterClip.clip(hillshadeData,region, true, false, mDatasource, hillshadeClipDatasetNm);
				
				stream5haData.close();
				hillshadeData.close();
			}
			
			addLayerSingleBandGrid(hillshadeClipDataset, mapId, hillshadeClipDatasetNm);
			addLayerSingleBandVector(stream5haClipDatasetVector,mapId,stream5haClipDatasetNm);
			addLayerSingleBandVector(watershedCopyDatasetVector,mapId,watershedCopyDatasetNm);
			
			if(setMapLayoutControl(templateNm,mapId,bound)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			
			stream5haClipDatasetVector.close();
			hillshadeClipDataset.close();
			watershedCopyDatasetVector.close();
			
			mExportUdbToFile(stream5haClipDatasetNm, vo, "shp");
			mExportUdbToFile(watershedCopyDatasetNm, vo, "shp");
			mExportUdbToFile(hillshadeClipDatasetNm, vo, "tif");
			
			vo.setFileExtsn("zip");
			list.add(vo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}
	
	/**
	 * 사방사업 타당성평가 분석사진
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param captionNm
	 * @param mapStyle
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbClipImg(String mstId,String sldId,String analId, String captionNm, String mapStyle) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String mapId = "Map_".concat(captionNm).concat("_"+analId);
		String watershedDatasetNm = SuperMapBasicUtils.getLayerName("watershed");//"tf_feis_watershed";
		String hillshadeDatasetNm = SuperMapBasicUtils.getLayerName("hillshade");//hillshade
		String clipDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);
		String templateNm = SuperMapBasicUtils.getLayoutTemplateNm(captionNm);
		
		String watershedQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		
		String watershedCopyDatasetNm = "watershed_".concat(analId);
		String clipCopyDatasetNm = captionNm.concat("_"+analId);
		String hillshadeCopyDatasetNm = "hillshade_".concat(analId);
		
		JSONObject statisticMap = null;
		
		DatasetVector watershedCopyDatasetVector = null;
		Dataset clipDataset = null;
		Dataset hillshadeClipDataset = null;
		
		DatasetType clipType = null;
		GeoRegion region = null;
		
		Rectangle2D bound = null;
		
		vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
		vo.setOrignlFileNm(captionNm);
		vo.setAnalType(captionNm);
		vo.setMstId(Integer.valueOf(mstId));
		vo.setSldId(sldId);
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,5186);
			
			//유역분석 결과 벡터레이어 DB에서 UDB로 복사
			copyPggisToMemoryUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			//copyPggisToUdb(watershedDatasetNm,watershedCopyDatasetNm,watershedQuery);
			
			//데이터셋 자르기
			if(mDatasets.contains(watershedCopyDatasetNm)) {
				watershedCopyDatasetVector = (DatasetVector) mDatasets.get(watershedCopyDatasetNm);
				Recordset mRecordset = watershedCopyDatasetVector.query("",CursorType.STATIC);
				
				bound = mRecordset.getGeometry().getBounds();
				
				region = new GeoRegion();
				
				while (!mRecordset.isEOF()) {
					for (int i = 0; i < ((GeoRegion)mRecordset.getGeometry()).getPartCount(); i++) {
						region.addPart(((GeoRegion) mRecordset.getGeometry()).getPart(i));
					}
					mRecordset.moveNext();
				}
				mRecordset.dispose();
				
				Dataset clipData =  datasource.getDatasets().get(clipDatasetNm);
				Dataset hillshadeData = (DatasetGrid)datasource.getDatasets().get(hillshadeDatasetNm);
				
				//Dataset clipData =  uDatasource.getDatasets().get(clipDatasetNm);
				clipType = clipData.getType();

				if(clipType.equals(DatasetType.GRID)) {
					clipDataset = RasterClip.clip((DatasetGrid)clipData,region, true, false, mDatasource, clipCopyDatasetNm);
					statisticMap = SuperMapBasicUtils.caculateStatisticsGrid(clipDataset,captionNm,region.getArea());
				}else {
					clipDataset = VectorClip.clipDatasetVector((DatasetVector)clipData, region, true, false, mDatasource, clipCopyDatasetNm);
					statisticMap = SuperMapBasicUtils.caculateStatisticsVector(clipDataset, captionNm);
				}
				
				hillshadeClipDataset = RasterClip.clip(hillshadeData,region, true, false, mDatasource, hillshadeCopyDatasetNm);
				
				if(statisticMap != null) {
					statisticMap.put("전체면적", region.getArea());
					vo.setStatData(statisticMap.toString());
				}
				
				clipData.close();
				hillshadeData.close();
			}
			
			if(clipType.equals(DatasetType.GRID)) {
				if(mapStyle.equals("range")){
					addLayerThemeRangeGrid(clipDataset,mapId,clipCopyDatasetNm);
				}else if(mapStyle.equals("uniqe")){
					
				}else {
					addLayerSingleBandGrid(clipDataset,mapId,clipCopyDatasetNm);
				}
				
				
			}else {
				if(mapStyle.equals("uniqe")){
					addLayerThemeUniqueVector(clipDataset,mapId,clipCopyDatasetNm);
				}else {
					addLayerSingleBandVector(clipDataset,mapId,clipCopyDatasetNm);
				}
			}
			addLayerSingleBandGrid(hillshadeClipDataset, mapId, hillshadeCopyDatasetNm);
			addLayerSingleBandVector(watershedCopyDatasetVector,mapId,watershedCopyDatasetNm);
			
			if(setMapLayoutControl(templateNm,mapId,bound)) {
				AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				list.add(imgVo);
				
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			hillshadeClipDataset.close();
			watershedCopyDatasetVector.close();
			clipDataset.close();
			
			if(clipType.equals(DatasetType.GRID)) {
				mExportUdbToFile(clipCopyDatasetNm, vo, "tif");
			}else {
				mExportUdbToFile(clipCopyDatasetNm, vo, "shp");
			}
			mExportUdbToFile(watershedCopyDatasetNm, vo, "shp");
			mExportUdbToFile(hillshadeCopyDatasetNm, vo, "tif");
			
			vo.setFileExtsn("zip");
			list.add(vo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		
		return list;
	}

	/**
	 * 사방사업 타당성평가 생태자연도 분석
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param captionNm
	 * @param mapStyle
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbEclgyNatureMapImg(String mstId,String sldId,String analId) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		
		String captionNm = "nature";
		String mapId = "Map_".concat(captionNm).concat("_"+analId);
		String ecrtcnlDatasetNm = SuperMapBasicUtils.getLayerName("ecrtcnl");
		String hillshadeDatasetNm = SuperMapBasicUtils.getLayerName("hillshade");//hillshade
		String eclgyNatureDatasetNm = SuperMapBasicUtils.getLayerName(captionNm);
		String templateNm = SuperMapBasicUtils.getLayoutTemplateNm(captionNm);
		
		String ecrtcnlQuery = "mst_id = ".concat(mstId).concat(" and sld_id = '").concat(sldId).concat("' and anal_id = '").concat(analId).concat("'");
		
		String ecrtcnlCopyDatasetNm = "ecrtcnl_".concat(analId);
		String eclgyNatureBufferClipDatasetNm = captionNm.concat("_buffer").concat("_"+analId);
		String eclgyNatureClipDatasetNm = captionNm.concat("_"+analId);
		String hillshadeClipDatasetNm = "hillshade_".concat(analId);
		String bufferDatasetNm = "buffer_".concat(analId);
		
		String bufferProjectDatasetNm = "buffer_project_".concat(analId);
		String ecrtcnlCopyProjectDatasetNm = "ecrtcnl_project_".concat(analId);
		String eclgyNatureClipProjectDatasetNm = captionNm.concat("_project_").concat(analId);
		String hillshadeClipProjectDatasetNm = "hillshade_project_".concat(analId);
		
		JSONObject statisticMap = null;
		
		DatasetVector ecrtcnlCopyDatasetVector = null;
		DatasetVector bufferDatasetVector = null;
		DatasetVector eclgyNatureClipDataset = null;
		Dataset eclgyNatureBufferClipDatasetVector = null;
		Dataset hillshadeClipDataset = null;
		
		Point2D newCenter = null;
		Rectangle2D newBound = null;
		
		vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
		vo.setOrignlFileNm(captionNm);
		vo.setAnalType(captionNm);
		vo.setMstId(Integer.valueOf(mstId));
		vo.setSldId(sldId);
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			copyPggisToMemoryUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,ecrtcnlQuery);
			//copyPggisToUdb(ecrtcnlDatasetNm,ecrtcnlCopyDatasetNm,ecrtcnlQuery);
			
			//데이터셋 자르기
			if(mDatasets.contains(ecrtcnlCopyDatasetNm)) {
				ecrtcnlCopyDatasetVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
				bufferDatasetVector = mDatasets.create(new DatasetVectorInfo(bufferDatasetNm,DatasetType.REGION),PrjCoordSys.fromEPSG(5186));
				
				BufferAnalystParameter bap = new BufferAnalystParameter();
				bap.setEndType(BufferEndType.ROUND);
				bap.setRadiusUnit(BufferRadiusUnit.Meter);
				bap.setLeftDistance("50");
				bap.setRightDistance("50");
				boolean isBufferCreated = BufferAnalyst.createBuffer(ecrtcnlCopyDatasetVector, bufferDatasetVector, bap, false, false);
				
				if(!isBufferCreated) {
					LOGGER.error("버퍼생성을 실패하였습니다.");
					return null;
				}
				
				Recordset mRecordset = bufferDatasetVector.query("",CursorType.STATIC);
				Rectangle2D bound = mRecordset.getGeometry().getBounds();
				
				GeoRegion region = new GeoRegion();
				
				while (!mRecordset.isEOF()) {
					for (int i = 0; i < ((GeoRegion)mRecordset.getGeometry()).getPartCount(); i++) {
						region.addPart(((GeoRegion) mRecordset.getGeometry()).getPart(i));
					}
					mRecordset.moveNext();
				}
				mRecordset.dispose();
				
				double addLen = 1200.0;
				
				newBound = new Rectangle2D();
				//클립할 영역 설정(이미지 영역보다 조금 더 많이 자름)
				newBound.setTop(bound.getTop()+addLen);
				newBound.setBottom(bound.getBottom()-addLen);
				newBound.setLeft(bound.getLeft()-addLen);
				newBound.setRight(bound.getRight()+addLen);
				
				GeoRectangle geoRect = new GeoRectangle(newBound, 0);
				GeoRegion clipRegion = geoRect.convertToRegion();

//				mRecordset.dispose();
				eclgyNatureBufferClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(eclgyNatureDatasetNm), region, true, false, mDatasource, eclgyNatureBufferClipDatasetNm);
				eclgyNatureClipDataset = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(eclgyNatureDatasetNm), clipRegion, true, false, mDatasource, eclgyNatureClipDatasetNm);
				hillshadeClipDataset = RasterClip.clip((DatasetGrid)datasource.getDatasets().get(hillshadeDatasetNm),clipRegion, true, false, mDatasource, hillshadeClipDatasetNm);
				
				statisticMap = SuperMapBasicUtils.caculateStatisticsVector(eclgyNatureBufferClipDatasetVector, captionNm);
				
				if(statisticMap != null) {
					statisticMap.put("전체면적", region.getArea());
					vo.setStatData(statisticMap.toString());
				}
				
				//ecrtcnlCopyDatasetVector = (DatasetVector) mDatasets.get(ecrtcnlCopyDatasetNm);
				
//				boolean ecrtcnlConverted = SuperMapBasicUtils.coordSysTranslator(ecrtcnlCopyDatasetVector,3857);
//				boolean eclgyNatureConverted = SuperMapBasicUtils.coordSysTranslator(eclgyNatureClipDataset,3857);
//				boolean hillshadeConverted = SuperMapBasicUtils.coordSysTranslator(hillshadeClipDataset,3857);
				
				Dataset ecrtcnlProjectDataset = SuperMapBasicUtils.coordSysTranslator(ecrtcnlCopyDatasetVector,mDatasource,ecrtcnlCopyProjectDatasetNm,3857);
				Dataset bufferProjectDataset = SuperMapBasicUtils.coordSysTranslator(bufferDatasetVector, mDatasource, bufferProjectDatasetNm, 3857);
				Dataset eclgyNatureClipProjectDataset = SuperMapBasicUtils.coordSysTranslator(eclgyNatureClipDataset,mDatasource,eclgyNatureClipProjectDatasetNm,3857);
				Dataset hillshadeClipProjectDataset = SuperMapBasicUtils.coordSysTranslator(hillshadeClipDataset,mDatasource,hillshadeClipProjectDatasetNm,3857);
				
				if(ecrtcnlProjectDataset != null && eclgyNatureClipProjectDataset != null && hillshadeClipProjectDataset != null) {
					vDataset = vDatasets.get("VworldSatellite");
					
					addLayerImage(vDataset,mapId,"vworld");
					addLayerThemeUniqueVector(eclgyNatureClipProjectDataset, mapId, captionNm);
					addLayerSingleBandGrid(hillshadeClipProjectDataset, mapId, hillshadeClipProjectDatasetNm);
					addLayerSingleBandVector(bufferProjectDataset, mapId, "ecb_"+bufferProjectDatasetNm);
					
					vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
					vo.setOrignlFileNm(captionNm);
					vo.setAnalType(captionNm);
					vo.setMstId(Integer.valueOf(mstId));
					vo.setSldId(sldId);
					
					Recordset ecrtcnlRecordset = ((DatasetVector)ecrtcnlProjectDataset).query("",CursorType.STATIC);
					newCenter = ecrtcnlRecordset.getGeometry().getInnerPoint();
					
					if(setMapLayoutControl(templateNm,mapId,newCenter,0.000098784)) {
						AnalFileVO imgVo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
						list.add(imgVo);
						
						if(SuperMapBasicUtils.deleted) {
							SuperMapBasicUtils.deleteMap(workspace,mapId);
						}
					}
					
					ecrtcnlCopyDatasetVector.close();
					eclgyNatureBufferClipDatasetVector.close();
					bufferDatasetVector.close();
					eclgyNatureClipDataset.close();
					hillshadeClipDataset.close();
					ecrtcnlProjectDataset.close();
					eclgyNatureClipProjectDataset.close();
					hillshadeClipProjectDataset.close();
					
					mExportUdbToFile(ecrtcnlCopyDatasetNm, vo, "shp");
					mExportUdbToFile(bufferDatasetNm, vo, "shp");
					mExportUdbToFile(eclgyNatureBufferClipDatasetNm, vo, "shp");
					mExportUdbToFile(eclgyNatureClipDatasetNm, vo, "shp");
					mExportUdbToFile(hillshadeClipDatasetNm, vo, "tif");
					vo.setFileExtsn("zip");
					list.add(vo);
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
	 * 사방사업 타당성평가 계류경사 분석
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param lineWkt
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> createEcbMntnTrntSlopeImg(String mstId,String sldId,String analId,String lineWkt,Map<String, Object> lineWktAttr) throws Exception{
		mDatasets.deleteAll();
		List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		AnalFileVO vo = null;
		//String saveImgNm = null;
		
		String demDatasetNm = SuperMapBasicUtils.getLayerName("dem");
		String mntnTrntCopyDatasetNm = "mntnTrnt_".concat(analId);
		String mntnTrntPntDatasetNm = "mntnTrntPnt_".concat(analId);
		
		DatasetVector mntnTrntCopyDatasetVector = null;
		DatasetVector mntnTrntPntDatasetVector = null;
		DatasetGrid demDatasetGrid = null;
		
		try {
			//슈퍼맵 연결
//			getConnectionInfo(analId);
			//라이데이터셋 생성
			Geometry lnewktGeometry = SuperMapBasicUtils.createGeometry(lineWkt, "POLYLINE");
			
			addRecordset(lnewktGeometry,lineWktAttr,"tf_feis_mntntrnt");
			
			String mntnTrntQuery = "anal_id = '".concat(lineWktAttr.get("anal_id").toString()).concat("'");
			
			copyPggisToMemoryUdb("tf_feis_mntntrnt",mntnTrntCopyDatasetNm,mntnTrntQuery);
			
			//createDataset("Line",lneWktDatasetNm);
			
//			lneWktDatasetVector = (DatasetVector) mDatasets.get(lneWktDatasetNm);
//			lneWktDatasetVector.setPrjCoordSys(PrjCoordSys.fromEPSG(5186));
//			Recordset lneWktRecordset = lneWktDatasetVector.query("",CursorType.DYNAMIC);
//			lneWktRecordset.addNew(lnewktGeometry);
//			lneWktRecordset.update();
//			lneWktRecordset.close();
			
			//포인트데이터셋 생성
			createDataset("Point", mntnTrntPntDatasetNm);
			
			mntnTrntPntDatasetVector = (DatasetVector) mDatasets.get(mntnTrntPntDatasetNm);
			mntnTrntPntDatasetVector.setPrjCoordSys(PrjCoordSys.fromEPSG(5186));
			FieldInfos lnePntFieldInfos = mntnTrntPntDatasetVector.getFieldInfos();
			FieldInfo valueInfo = new FieldInfo("value", FieldType.DOUBLE);
			lnePntFieldInfos.add(valueInfo);
			
			Recordset mntnTrntPntRecordset = mntnTrntPntDatasetVector.query("",CursorType.DYNAMIC);
			
			demDatasetGrid = (DatasetGrid) datasets.get(demDatasetNm);
			mntnTrntCopyDatasetVector = (DatasetVector) mDatasets.get(mntnTrntCopyDatasetNm);
			Recordset mntnTrntCopyRecordset = mntnTrntCopyDatasetVector.query("",CursorType.STATIC);
			
			mntnTrntCopyRecordset.moveFirst();
//			Point2D startPoint = null;
//			Point2D endPoint = null;
			double distance = 20.0;
			
//			double totalArea = 0;
			int i = 0;
//			int maxBound = 0;
			
			XYSeriesCollection xySeriesCollection = new XYSeriesCollection( );
			
			while(!mntnTrntCopyRecordset.isEOF()) {
				i++;
				GeoLine geoLine = (GeoLine)mntnTrntCopyRecordset.getGeometry();
				double len = geoLine.getLength();
				int lCnt = (int) Math.ceil(len / distance);
				
				XYSeries xySeries = new XYSeries("Series"+i);
				for (int j = 0; j <= lCnt; j++) {
					Map<String, Object> attr = new HashMap<String, Object>();
					Point2D findPoint = geoLine.findPointOnLineByDistance(j*distance);
					Point pt = demDatasetGrid.xyToGrid(findPoint);
					
					double gridValue = demDatasetGrid.getValue(pt.x, pt.y);
					attr.put("value", gridValue);
					Geometry geom = new GeoPoint(findPoint);
					mntnTrntPntRecordset.addNew(geom,attr);
					mntnTrntPntRecordset.update();
					
					xySeries.add(j*(int)distance,gridValue);
				}
				xySeriesCollection.addSeries(xySeries);
				
//				startPoint = geoLine.getPart(0).getItem(0);
				
				int l_cnt = geoLine.getPart(0).getCount();
//				endPoint = geoLine.getPart(0).getItem(l_cnt-1);
				
				mntnTrntCopyRecordset.moveNext();
				
				vo = SuperMapBasicUtils.getSavePath(analId,SuperMapBasicUtils.downloadMidDir);
				vo.setOrignlFileNm("계류경사분석");
				vo.setAnalType("계류경사분석");
				vo.setMstId(Integer.valueOf(mstId));
				vo.setSldId(sldId);
				
				AnalFileVO imgVo = SuperMapBasicUtils.createXYChartToImage("line",xySeriesCollection,(lCnt*(int)distance),vo);
				list.add(imgVo);
				
			}
			mntnTrntCopyRecordset.close();
			mntnTrntPntRecordset.dispose();
			mntnTrntPntRecordset.close();
			
			mntnTrntCopyDatasetVector.close();
			mntnTrntPntDatasetVector.close();
			demDatasetGrid.close();
			
			mExportUdbToFile(mntnTrntCopyDatasetNm, vo, "shp");
			mExportUdbToFile(mntnTrntPntDatasetNm, vo, "shp");
			
			vo.setFileExtsn("zip");
			list.add(vo);
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
	 * 템플릿 레이아웃 설정
	 * @param name
	 * @throws Exception
	 */
	public boolean setMapLayoutControl(String layoutName, String mapName,Rectangle2D rect){
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
				if(geometry instanceof GeoLegend) {
					GeoLegend legend = createEcbGeoLegend(layoutName,geometry, mapName);
					layoutElements.setGeometry(legend);
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
	
	public boolean setMapLayoutControl(String layoutName, String mapName,Point2D center,double scale){
		System.out.println("템플릿 레이아웃 설정 시작");
		
		mapLayoutcontrol = new MapLayoutControl();
		mapLayoutcontrol.getMapLayout().setWorkspace(workspace);
		mapLayoutcontrol.getMapLayout().open(layoutName);
		
		try {
			LayoutElements layoutElements = mapLayoutcontrol.getMapLayout().getElements();
			while(!layoutElements.isEOF()) {
				Geometry geometry = layoutElements.getGeometry();
				
				if(geometry instanceof GeoText) {
					
				}
				if(geometry instanceof GeoLegend) {
					GeoLegend legend = createEcbGeoLegend(layoutName,geometry, mapName);
					layoutElements.setGeometry(legend);
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
					mapLayoutcontrol.getActiveMap().setCenter(center);
					mapLayoutcontrol.getActiveMap().setScale(scale);
					
					BigDecimal big = new BigDecimal(mapLayoutcontrol.getActiveMap().getScale());
					
					System.out.println(big);
					mapLayoutcontrol.getActiveMap().refresh();
					mapLayoutcontrol.setActiveGeoMapID(-1);
					
				}
				layoutElements.moveNext();
			}
			layoutElements.refresh();
			mapLayoutcontrol.getMapLayout().refresh();
			System.out.println("템플릿 레이아웃 설정 완료");
		} catch (Exception e) {
			System.err.println("템플릿 레이아웃 설정 오류 : "+e.getMessage());
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
	private GeoLegend createEcbGeoLegend(String layoutName, Geometry geometry, String mapName) throws Exception{
		String legendTitle = "범 례";
		GeoLegend target = (GeoLegend)geometry;
		GeoLegend legend = new GeoLegend(mapName, datasource.getWorkspace(), legendTitle);
		
		for(int i=0; i<legend.getItemNames().length; i++) {
			String lyNm = legend.getItemNames()[i];
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
				if(idx == 0) {	
					positionY = rec.getInnerPoint().y;
					positionYMap.put(idx, positionY);
					
					//GeoStyle style = rec.getStyle();
					//style.setFillForeColor(new Color(0,0,0,0));
					//style.setFillBackColor(new Color(0,0,0,0));
					
					//rec.setStyle(style);
				}else {
					if(idx == 1) {
						positionY = positionY + (legend.getHeight()/2)-110;
					}
					
					rec.setWidth(100);
					rec.setHeight(40);
					rec.setCenter(new Point2D(positionX, positionY - ((idx -1) * 60) + gpc));
					
					positionYMap.put(idx,  positionY - ((idx -1) * 60) + gpc);
				}
				idx++;
			}
			
			if((typeCheck instanceof GeoText)) {
				GeoText text = (GeoText) typeCheck;
				
				if(text.getText().matches("범 례")) {
					TextPart part = new TextPart();
					part.setText(text.getText());
					part.setX(positionX-25);
					
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
					
					if(text.getText().contains("watershed")) {
						part.setText("유역경계");
					}else {
						part.setText(text.getText());
						positionY = positionYMap.get(tIdx+1);
					}
					part.setX(positionX+120);
					part.setY(positionY+10);
					

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
				style.setLineColor(new Color(255,255,0));
				style.setLineWidth(0.5);
				line.setStyle(style);
				
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
			
			if(caption.matches("sdem.*")) {
				lsg.setColorTable(Colors.makeGradient(grid.getWidth(), ColorGradientType.TERRAIN, false));
			}else {
				lsg.setColorDictionary(SuperMapBasicUtils.getColorDictionary(caption));
			}
			
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
}