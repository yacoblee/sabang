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

public class LssCnlSupMapUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LssCnlSupMapUtils.class);
	
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
	
	public LssCnlSupMapUtils() {
		try {
			processId = SuperMapBasicUtils.getDatasetUuid();
			getConnectionInfo(processId);
		} catch (Exception e) {
			LOGGER.error("워크스페이스 생성 중 오류가 발생하였습니다.");
		}
	}

	public LssCnlSupMapUtils(String analId) {
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
	 * 취약지역해제조사 현황도
	 * @param schMap
	 * @param analId
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO creatCnlLocExitMap(HashMap<String, Object> schMap) throws Exception{
		LOGGER.info("1.취약지역해제조사 현황도");
		mDatasets.deleteAll();
		AnalFileVO vo = null;
		
		String gId = schMap.get("gid").toString();
		String mstId = schMap.get("mstId").toString();
		String svyLabel = schMap.get("sldId").toString();
		String mapId = "Map_".concat(processId);
		
		String vnaraPntDatasetNm = "tf_feis_cnl_vnarapnt";//포인트(유출구)
		String vnaraPlgnDatasetNm = "tf_feis_cnl_vnaraplgn";//폴리곤(사방댐,계류보전,유역면적,산지사방)
		String vnaraLneDatasetNm = "tf_feis_cnl_vnaralne";//폴리라인(대피로)
		String vnaraPntCopyDatasetNm = "wpnt_".concat(processId);//포인트(유출구) 조회 데이터셋명
		String vnaraPlgnCopyDatasetNm = "wplgn_".concat(processId);//폴리곤(사방댐,계류보전,유역면적,산지사방) 조회 데이터셋명
		String vnaraLneCopyDatasetNm = "wlne_".concat(processId);//폴리라인(대피로) 조회 데이터셋명
		
		DatasetVector vnaraPntCopyDatasetVector = null;
		DatasetVector vnaraPlgnCopyDatasetVector = null;
		DatasetVector vnaraLneCopyDatasetVector = null;
		
		String vnaraQuery = "gid = ".concat(gId);
		
		Rectangle2D newBound = null;
		
		try {
			//smwu프로젝트에 Map 생성하기
			LOGGER.info("Map 생성 시작 : ".concat(processId));
			createNewMap(mapId,3857);
			LOGGER.info("Map 생성 완료 : ".concat(processId));
			
			//포인트 데이터셋에서 vnara_exmnn_id로 대상 조회
			LOGGER.info(vnaraPntDatasetNm+"데이터셋을 memory udb에 "+vnaraQuery+" 조건의 "+vnaraPntCopyDatasetNm+" 데이터셋으로 복사 : ".concat(processId));
			copyPggisToMemoryUdb(vnaraPntDatasetNm,vnaraPntCopyDatasetNm,vnaraQuery);
			LOGGER.info("데이터셋 복사 완료 : ".concat(processId));
			
			//폴리곤 데이터셋에서 vnara_exmnn_id로 대상 조회
			LOGGER.info(vnaraPlgnDatasetNm+"데이터셋을 memory udb에 "+vnaraQuery+" 조건의 "+vnaraPlgnCopyDatasetNm+" 데이터셋으로 복사 : ".concat(processId));
			copyPggisToMemoryUdb(vnaraPlgnDatasetNm,vnaraPlgnCopyDatasetNm,vnaraQuery);
			LOGGER.info("데이터셋 복사 완료 : ".concat(processId));
			
			//폴리라인 데이터셋에서 vnara_exmnn_id로 대상 조회
			LOGGER.info(vnaraLneDatasetNm+"데이터셋을 memory udb에 "+vnaraQuery+" 조건의 "+vnaraLneCopyDatasetNm+" 데이터셋으로 복사 : ".concat(processId));
			copyPggisToMemoryUdb(vnaraLneDatasetNm,vnaraLneCopyDatasetNm,vnaraQuery);
			LOGGER.info("데이터셋 복사 완료 : ".concat(processId));
			
			vnaraPlgnCopyDatasetVector = (DatasetVector) mDatasets.get(vnaraPlgnCopyDatasetNm);
			vnaraPntCopyDatasetVector = (DatasetVector) mDatasets.get(vnaraPntCopyDatasetNm);
			vnaraLneCopyDatasetVector = (DatasetVector) mDatasets.get(vnaraLneCopyDatasetNm);
			
			if(vnaraLneCopyDatasetVector.getRecordCount() > 0) {
				SuperMapBasicUtils.coordSysTranslator(vnaraLneCopyDatasetVector,3857);
			}
			if(SuperMapBasicUtils.coordSysTranslator(vnaraPlgnCopyDatasetVector,3857) && 
					SuperMapBasicUtils.coordSysTranslator(vnaraPntCopyDatasetVector,3857)) {
				
				Recordset mRecordset = vnaraPlgnCopyDatasetVector.query("",CursorType.STATIC);
				
				GeoRegion bigRegion = null;
				
				while (!mRecordset.isEOF()) {
					Rectangle2D recordSetBound = mRecordset.getGeometry().getBounds();
					
					GeoRectangle geoRect = new GeoRectangle(recordSetBound, 0);
					GeoRegion region = geoRect.convertToRegion();
					
					if(bigRegion == null) {
						bigRegion = region;
					}else {
						if(region.getArea() > bigRegion.getArea()) {
							bigRegion = region;
						}
					}
					mRecordset.moveNext();
				}
				
				mRecordset.dispose();
				
				Rectangle2D bound = bigRegion.getBounds();
				
				double wd = bound.getWidth()*1.5;
				
				newBound = new Rectangle2D();
				
				newBound.setTop(bound.getTop()+wd);
				newBound.setBottom(bound.getBottom()-wd);
				newBound.setLeft(bound.getLeft()-wd);
				newBound.setRight(bound.getRight()+wd);
				
				vDataset = vDatasets.get("VworldSatellite");
				
				LOGGER.info("Map에 데이터셋 추가 시작");
				addLayerImage(vDataset,mapId,"vworld");
				addLayerThemeUniqueVector(vnaraPlgnCopyDatasetVector, mapId, vnaraPlgnCopyDatasetNm);
				
				if(vnaraLneCopyDatasetVector.getRecordCount() > 0) {
					addLayerSingleBandVector(vnaraLneCopyDatasetVector,mapId,vnaraLneCopyDatasetNm);
				}
				addLayerSingleBandVector(vnaraPntCopyDatasetVector,mapId,vnaraPntCopyDatasetNm);
				LOGGER.info("Map에 데이터셋 추가 완료");
				
				vo = SuperMapBasicUtils.getSavePath(processId,SuperMapBasicUtils.downloadMidDir);
				vo.setOrignlFileNm("대피체계");
				vo.setAnalType("대피체계");
				vo.setMstId(Integer.valueOf(mstId));
				vo.setSldId(svyLabel);
				
				if(setMapLayoutControl("wkaVectorLocTemplate",mapId,newBound)) {
					vo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol, vo);
					if(SuperMapBasicUtils.deleted) {
						SuperMapBasicUtils.deleteMap(workspace,mapId);
					}
				}
			}else {
				LOGGER.error("좌표변환에 실패하였습니다.");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return vo;
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
					GeoLegend legend = createGeoLegend(layoutName,geometry, mapName);
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
	
	/**
	 * 지도템플릿 범례생성
	 * @param geometry
	 * @param mapName
	 * @return
	 * @throws Exception
	 */
	private GeoLegend createGeoLegend(String layoutName, Geometry geometry, String mapName) throws Exception{
		String legendTitle = "범례";
		GeoLegend target = (GeoLegend)geometry;
		GeoLegend legend = new GeoLegend(mapName, datasource.getWorkspace(), legendTitle);
		
		legend.setColumnCount(1);
		legend.setWidth(target.getBounds().getWidth());
		//legend.setHeight(target.getBounds().getHeight());
		legend.setCenter(new Point2D(target.getInnerPoint().x, target.getInnerPoint().y));
		
		GeoCompound compound = legend.getInnerGeometry();
		
		legend.setHeight((compound.getPartCount()/2)*65);
		//legend.setCenter(new Point2D(legend.getInnerPoint().x, legend.getHeight()/2+30));
		
		int idx =0, recIdx = 0, lineIdx = 0;
		double positionX = 0, positionY = 0;
		
		LinkedHashMap<Integer, Double> positionYMap = new LinkedHashMap<Integer, Double>();
		for (int i =0; i < compound.getPartCount(); i++) {
			//System.out.println(compound.getPart(i).getType());
			Geometry typeCheck = (Geometry)compound.getPart(i);
			
			if((typeCheck instanceof GeoText)) {
				GeoText text = (GeoText) typeCheck;
				if(idx == 0 ) {
					positionX =  text.getInnerPoint().x;
					//positionY = text.getInnerPoint().y;
					//System.out.println("POSITION X / Y : " + positionX +" / "+ positionY);
					idx++;
				}
				if(text.getText().matches("범례")) {
					//positionY = text.getInnerPoint().y;
					positionY = positionYMap.get(0);
					positionY = positionY + (legend.getHeight()/2)-40;
					
					TextPart part = new TextPart();
					part.setText(text.getText());
					part.setX(positionX);
					part.setY(positionY);
					
					//Text Style Set
					TextStyle style = new TextStyle();
					style.setFontWidth(5);
					style.setFontHeight(5);
					style.setSizeFixed(true);
//					style.setFontScale(10);
					text.setTextStyle(style);
					text.setPart(0, part);
				}else {
					TextPart part = new TextPart();
					if(text.getText().matches("watershed.*")) {
						part.setText("유역경계");
					}else {
						part.setText(text.getText());
					}
					positionY = positionYMap.get(idx);
					//Text position & text Set
					part.setX(positionX+100);
					part.setY(positionY);
					//part.setY((positionY) - ((idx -1) * 60));
					
					//Text Style Set
					TextStyle style = new TextStyle();
					style.setFontWidth(3);
					style.setFontHeight(3);
					style.setSizeFixed(true);
					text.setTextStyle(style);
					text.setPart(0, part);
					
					idx++;
				}
			}
			
			if(typeCheck instanceof GeoLine) {
				GeoLine line = (GeoLine) typeCheck;
				//System.out.println(line.getInnerPoint().x+","+line.getInnerPoint().y);
				if(lineIdx == 0) {
					positionY = line.getInnerPoint().y;
					positionYMap.put(lineIdx, positionY);
				}else {
					if(lineIdx == 1) {
						positionX =  line.getInnerPoint().x+40;
						positionY = positionY + (legend.getHeight()/2)-120;//rec.getInnerPoint().y;
					}
					line.offset(positionX, positionY - ((lineIdx -1) * 60));
					
					positionYMap.put(lineIdx, positionY - ((lineIdx -1) * 60));
				}
				//System.out.println("after ::: "+line.getInnerPoint().x+","+line.getInnerPoint().y);
				lineIdx++;
			}
			
			if(typeCheck instanceof GeoRectangle) {
				GeoRectangle rec = (GeoRectangle) typeCheck;
				//System.out.println(rec.getInnerPoint().x+","+rec.getInnerPoint().y);
				if(recIdx == 0) {
					positionY = rec.getInnerPoint().y;
					positionYMap.put(recIdx, positionY);
				}else {
					if(recIdx == 1) {
						positionX =  rec.getInnerPoint().x+40;
						positionY = positionY + (legend.getHeight()/2)-120;//rec.getInnerPoint().y;
					}
					rec.setWidth(100);
					rec.setHeight(40);
					rec.setCenter(new Point2D(positionX, positionY - ((recIdx -1) * 60)));
					
					positionYMap.put(recIdx, positionY - ((recIdx -1) * 60));
				}
				//System.out.println("after ::: "+rec.getInnerPoint().x+","+rec.getInnerPoint().y);
				recIdx++;
			}
		}
		legend.setCenter(new Point2D(target.getInnerPoint().x, legend.getHeight()/2+30));
		return legend;
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

}