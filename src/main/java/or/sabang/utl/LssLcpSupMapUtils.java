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
import java.util.Calendar;
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

public class LssLcpSupMapUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LssLcpSupMapUtils.class);
	
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
	
	public LssLcpSupMapUtils() {
		try {
			processId = SuperMapBasicUtils.getDatasetUuid();
			getConnectionInfo(processId);
		} catch (Exception e) {
			LOGGER.error("워크스페이스 생성 중 오류가 발생하였습니다.");
		}
	}

	public LssLcpSupMapUtils(String analId) {
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
	 * 땅밀림 실태조사 위치도
	 * @param schMap
	 * @throws Exception
	 */
	public AnalFileVO creatLcpLocLgstrMap(HashMap<String, Object> schMap) throws Exception{
		mDatasets.deleteAll();
		
		AnalFileVO vo = null;
		
		String mstId = schMap.get("mstId").toString();
		String svyLabel = schMap.get("sldId").toString();
		//String saveImgNm = null;
		String mapId = "Map_".concat(processId);
		String ctrlnDatasetNm = "tf_feis_ctrln";//등고선
		String lgstrDatasetNm = "tf_feis_lgstr";//지적
		String roadDatasetNm = "tf_feis_road";//도로중심선
		String rankDatasetNm = "tf_feis_lcp_fieldinfo";//랭크대상지
		
		String rankCopydatasetNm = "rank_".concat(processId);//랭크대상지 클립 데이터셋명
		String ctrlnCopyDatasetNm = "lcp_ctrln_".concat(processId);//등고선 클립 데이터셋명
		String lgstrCopyDatasetNm = "lgstr_".concat(processId);//지적 클립 데이터셋명
		String roadCopyDatasetNm = "road_".concat(processId);//도로중심선 클립 데이터셋명
		
		DatasetVector rankCopyDatasetVector = null;
		DatasetVector ctrlnClipDatasetVector = null;
		DatasetVector lgstrClipDatasetVector = null;
		DatasetVector roadClipDatasetVector = null;
		
		String stripLandQuery = "mst_id = ".concat(mstId).concat(" and svy_label = '").concat(svyLabel).concat("'");
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
//			LOGGER.info("슈퍼맵 연결 시작 : ".concat(analId));
//			getConnectionInfo(analId);
//			LOGGER.info("슈퍼맵 연결 완료 : ".concat(analId));
			//smwu프로젝트에 Map 생성하기
//			LOGGER.info("Map 생성 시작 : ".concat(analId));
			createNewMap(mapId,5186);
			LOGGER.info("Map 생성 : ".concat(processId));
			//랭크대상지 데이터 DB에서 UDB로 복사
			LOGGER.info(rankDatasetNm+"데이터셋을 udb에 "+stripLandQuery+" 조건의 "+rankCopydatasetNm+" 데이터셋으로 복사 : ".concat(processId));
			copyPggisToMemoryUdb(rankDatasetNm,rankCopydatasetNm,stripLandQuery);
			//copyPggisToUdb(rankDatasetNm,rankCopydatasetNm,stripLandQuery);
			LOGGER.info("데이터셋 복사 완료 : ".concat(processId));
			//지적데이터셋 자르기
			if(mDatasets.contains(rankCopydatasetNm)) {
				LOGGER.info("데이터셋 Clip 시작");
				rankCopyDatasetVector = (DatasetVector) mDatasets.get(rankCopydatasetNm);
				Recordset mRecordset = rankCopyDatasetVector.query("",CursorType.STATIC);
				
				Rectangle2D bound = mRecordset.getGeometry().getBounds();
				double wd = bound.getWidth();
				
				newBound = new Rectangle2D();
				//클립할 영역 설정(이미지 영역보다 조금 더 많이 자름)
				newBound.setTop(bound.getTop()+(wd*10));
				newBound.setBottom(bound.getBottom()-(wd*10));
				newBound.setLeft(bound.getLeft()-(wd*10));
				newBound.setRight(bound.getRight()+(wd*10));
				
				GeoRectangle geoRect = new GeoRectangle(newBound, 0);
				GeoRegion region = geoRect.convertToRegion();

				mRecordset.dispose();
				
				ctrlnClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(ctrlnDatasetNm), region, true, false, mDatasource, ctrlnCopyDatasetNm);
				if(ctrlnClipDatasetVector.getRecordCount() > 0) {
					LOGGER.info("등고 데이터셋 Clip 완료");
				}else {
					LOGGER.info("등고 데이터셋 Clip 실패");
				}
				
				lgstrClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(lgstrDatasetNm), region, true, false, mDatasource, lgstrCopyDatasetNm);
				if(lgstrClipDatasetVector.getRecordCount() > 0) {
					LOGGER.info("지적 데이터셋 Clip 완료");
				}else {
					LOGGER.info("지적 데이터셋 Clip 실패");
				}
				roadClipDatasetVector = VectorClip.clipDatasetVector((DatasetVector)datasource.getDatasets().get(roadDatasetNm), region, true, false, mDatasource, roadCopyDatasetNm);
				if(roadClipDatasetVector.getRecordCount() > 0) {
					LOGGER.info("도로 데이터셋 Clip 완료");
				}else {
					LOGGER.info("도로 데이터셋 Clip 실패");
				}
				//이미지 영역설정
				newBound.setTop(bound.getTop()+(wd*2));
				newBound.setBottom(bound.getBottom()-(wd*2));
				newBound.setLeft(bound.getLeft()-(wd*2));
				newBound.setRight(bound.getRight()+(wd*2));
			}
			
			addLayerSingleBandVector(ctrlnClipDatasetVector,mapId,ctrlnCopyDatasetNm);
			addLayerSingleBandVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
			addLayerThemeLabelVector(lgstrClipDatasetVector,mapId,lgstrCopyDatasetNm);
			addLayerSingleBandVector(roadClipDatasetVector,mapId,roadCopyDatasetNm);
			addLayerSingleBandVector(rankCopyDatasetVector,mapId,rankCopydatasetNm);
			
			vo = SuperMapBasicUtils.getSavePath(processId,SuperMapBasicUtils.downloadMidDir);
			vo.setOrignlFileNm("위치도");
			vo.setAnalType("위치도");
			vo.setMstId(Integer.valueOf(mstId));
			vo.setSldId(svyLabel);
			
			if(setMapLayoutControl("lcpVectorLocTemplate",mapId,newBound)) {
				vo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol,vo);
				if(SuperMapBasicUtils.deleted) {
					SuperMapBasicUtils.deleteMap(workspace,mapId);
				}
			}
			
			ctrlnClipDatasetVector.close();
			lgstrClipDatasetVector.close();
			roadClipDatasetVector.close();
			rankCopyDatasetVector.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return vo;
		
		
	}

	/**
	 * 땅밀림 실태조사 위성영상 위치도
	 * @param schMap
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO creatLcpLocSatMap(HashMap<String, Object> schMap) throws Exception{
		mDatasets.deleteAll();
		
		AnalFileVO vo = null;
		
		String mstId = schMap.get("mstId").toString();
		String svyLabel = schMap.get("sldId").toString();
		//String saveImgNm = null;
		String mapId = "Map_".concat(processId);
		String rankDatasetNm = "tf_feis_lcp_fieldinfo";//랭크대상지
		
		String rankCopydatasetNm = "rank_".concat(processId);//랭크대상지 클립 데이터셋명
		
		DatasetVector rankCopyDatasetVector = null;
		
		String stripLandQuery = "mst_id = ".concat(mstId).concat(" and svy_label = '").concat(svyLabel).concat("'");
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
			//getConnectionInfo(processId);
			
			//smwu프로젝트에 Map 생성하기
			createNewMap(mapId,3857);
			
			//랭크대상지 데이터 DB에서 UDB로 복사
			copyPggisToMemoryUdb(rankDatasetNm,rankCopydatasetNm,stripLandQuery);
			//copyPggisToUdb(rankDatasetNm,rankCopydatasetNm,stripLandQuery);
			
			if(mDatasets.contains(rankCopydatasetNm)) {
				rankCopyDatasetVector = (DatasetVector) mDatasets.get(rankCopydatasetNm);
				
				if(SuperMapBasicUtils.coordSysTranslator(rankCopyDatasetVector,3857)) {
					Recordset mRecordset = rankCopyDatasetVector.query("",CursorType.STATIC);
					
					Rectangle2D bound = mRecordset.getGeometry().getBounds();
					
					double wd = bound.getWidth()*2;
					
					newBound = new Rectangle2D();
					
					newBound.setTop(bound.getTop()+wd);
					newBound.setBottom(bound.getBottom()-wd);
					newBound.setLeft(bound.getLeft()-wd);
					newBound.setRight(bound.getRight()+wd);
					
					vDataset = vDatasets.get("VworldSatellite");
					
					addLayerImage(vDataset,mapId,"vworld");
					addLayerSingleBandVector(rankCopyDatasetVector,mapId,rankCopydatasetNm);
					addLayerThemeLabelVector(rankCopyDatasetVector,mapId,rankCopydatasetNm);
					
					vo = SuperMapBasicUtils.getSavePath(processId,SuperMapBasicUtils.downloadMidDir);
					vo.setOrignlFileNm("위치도(영상)");
					vo.setAnalType("위치도(영상)");
					vo.setMstId(Integer.valueOf(mstId));
					vo.setSldId(svyLabel);
					
					if(setMapLayoutControl("lcpSatelliteLocTemplate",mapId,newBound)) {
						vo = SuperMapBasicUtils.saveMapImageToJpg(mapLayoutcontrol, vo);
						if(SuperMapBasicUtils.deleted) {
							SuperMapBasicUtils.deleteMap(workspace, mapId);
						}
					}
					
					rankCopyDatasetVector.close();
				}else {
					LOGGER.error("좌표변환에 실패하였습니다.");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return vo;
	}
	
	/**
	 * 랭크데이터 고도값 추출
	 * @param analId
	 * @param creatYearRankTableNm
	 * @param creatYearRankWhere
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> createLssLcpRankElevation(String analId,String creatYearRankTableNm,String creatYearRankWhere) throws Exception{
		//mDatasets.deleteAll();
		List<ZonalStatisticVO> list = null;
		
		String rankDatasetNm = "tf_feis_lcp_rank";
		String demDatasetNm = "tf_feis_dem";
		String outputElevationDatasetNm = "ta_elev_".concat(analId);//"ta_feis_lcp_elevation_result";
		String outputElevationTableNm = "ta_elev_tb_".concat(analId);//"ta_feis_lcp_elevation_result_tb";
		String zonalFieldName = "SmID"; 
		
		copyDataSet(rankDatasetNm,creatYearRankTableNm,creatYearRankWhere);
		
		list = zonalStatisticsProcessing(creatYearRankTableNm, demDatasetNm, outputElevationDatasetNm, outputElevationTableNm, zonalFieldName);
		
		return list;
	}
	
	/**
	 * 랭크데이터 경사도값 추출
	 * @param analId
	 * @param creatYearRankTableNm
	 * @param creatYearRankWhere
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> createLssLcpRankSlope(String analId,String creatYearRankTableNm,String creatYearRankWhere) throws Exception{
		//mDatasets.deleteAll();
		List<ZonalStatisticVO> list = null;
		
		String rankDatasetNm = "tf_feis_lcp_rank";
		String slopeDatasetNm = "tf_feis_slope";
		String outputSLopeDatasetNm = "ta_slop_".concat(analId);//"ta_feis_lcp_slope_result";
		String outputSLopeTableNm = "ta_slop_tb_".concat(analId);//"ta_feis_lcp_rank_slope_tb";
		String zonalFieldName = "SmID"; 
		
		if(!mDatasets.contains(creatYearRankTableNm)) {
			copyDataSet(rankDatasetNm,creatYearRankTableNm,creatYearRankWhere);
		}
		
		list = zonalStatisticsProcessing(creatYearRankTableNm, slopeDatasetNm, outputSLopeDatasetNm, outputSLopeTableNm, zonalFieldName);
		
		return list;
	}
	
	/**
	 * 랭크데이터 토심값 추출
	 * @param analId
	 * @param creatYearRankTableNm
	 * @param creatYearRankWhere
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> createLssLcpRankSld(String analId,String creatYearRankTableNm,String creatYearRankWhere) throws Exception{
		//mDatasets.deleteAll();
		List<ZonalStatisticVO> list = null;
		
		String gvfDatasetNm = "tf_feis_lcp_rank";
		String sldDatasetNm = "tf_feis_ij100_sld";
		String outputSldDatasetNm = "ta_sld_".concat(analId);//"ta_feis_lcp_sld_result";
		String outputSldTableNm = "ta_sld_tb_".concat(analId);//"ta_feis_lcp_rank_sld_tb";
		String zonalFieldName = "SmID"; 
		
		if(!mDatasets.contains(creatYearRankTableNm)) {
			copyDataSet(gvfDatasetNm,creatYearRankTableNm,creatYearRankWhere);
		}
		
		list = zonalStatisticsProcessing(creatYearRankTableNm, sldDatasetNm, outputSldDatasetNm, outputSldTableNm, zonalFieldName);
		
		return list;
	}
	
	/**
	 * 제보데이터 고도값 추출
	 * @param analId
	 * @param creatYearRankTableNm
	 * @param creatYearRankWhere
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> createLssLcpGvfElevation(String analId,String creatYearGvfTableNm,String creatYearGvfWhere) throws Exception{
		//mDatasets.deleteAll();
		List<ZonalStatisticVO> list = null;
		
		String gvfDatasetNm = "tf_feis_lcp_gvf";
		String demDatasetNm = "tf_feis_dem";
		String outputElevationDatasetNm = "ta_elev_".concat(analId);//"ta_feis_lcp_elevation_result";
		String outputElevationTableNm = "ta_elev_tb_".concat(analId);//"ta_feis_lcp_elevation_result_tb";
		String zonalFieldName = "SmID"; 
		
		copyDataSet(gvfDatasetNm,creatYearGvfTableNm,creatYearGvfWhere);
		
		list = zonalStatisticsProcessing(creatYearGvfTableNm, demDatasetNm, outputElevationDatasetNm, outputElevationTableNm, zonalFieldName);
		
		return list;
	}
	
	/**
	 * 제보데이터 경사도값 추출
	 * @param analId
	 * @param creatYearRankTableNm
	 * @param creatYearRankWhere
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> createLssLcpGvfSlope(String analId,String creatYearGvfTableNm,String creatYearGvfWhere) throws Exception{
		//mDatasets.deleteAll();
		List<ZonalStatisticVO> list = null;
		
		String gvfDatasetNm = "tf_feis_lcp_gvf";
		String slopeDatasetNm = "tf_feis_slope";
		String outputSLopeDatasetNm = "ta_slop_".concat(analId);//"ta_feis_lcp_slope_result";
		String outputSLopeTableNm = "ta_slop_tb_".concat(analId);//"ta_feis_lcp_rank_slope_tb";
		String zonalFieldName = "SmID"; 
		
		if(!mDatasets.contains(creatYearGvfTableNm)) {
			copyDataSet(gvfDatasetNm,creatYearGvfTableNm,creatYearGvfWhere);
		}
		
		list = zonalStatisticsProcessing(creatYearGvfTableNm, slopeDatasetNm, outputSLopeDatasetNm, outputSLopeTableNm, zonalFieldName);
		
		return list;
	}
	
	/**
	 * 제보데이터 토심값 추출
	 * @param analId
	 * @param creatYearRankTableNm
	 * @param creatYearRankWhere
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> createLssLcpGvfSld(String analId,String creatYearGvfTableNm,String creatYearGvfWhere) throws Exception{
		//mDatasets.deleteAll();
		List<ZonalStatisticVO> list = null;
		
		String gvfDatasetNm = "tf_feis_lcp_gvf";
		String sldDatasetNm = "tf_feis_ij100_sld";
		String outputSldDatasetNm = "ta_sld_".concat(analId);//"ta_feis_lcp_sld_result";
		String outputSldTableNm = "ta_sld_tb_".concat(analId);//"ta_feis_lcp_rank_sld_tb";
		String zonalFieldName = "SmID"; 
		
		if(!mDatasets.contains(creatYearGvfTableNm)) {
			copyDataSet(gvfDatasetNm,creatYearGvfTableNm,creatYearGvfWhere);
		}
		
		list = zonalStatisticsProcessing(creatYearGvfTableNm, sldDatasetNm, outputSldDatasetNm, outputSldTableNm, zonalFieldName);
		
		return list;
	}
	
	/**
	 * 구역통계 분석(Zonal Statistics)
	 * @param zonalName 구역데이터명(vector)
	 * @param valueName 추출데이터명(raster)
	 * @param outputDatasetName 결과데이터명
	 * @param outputTableName 결과테이블 명
	 * @return
	 * @throws Exception
	 */
	public List<ZonalStatisticVO> zonalStatisticsProcessing(String zonalName,String valueName,String outputDatasetName, String outputTableName, String zonalFieldName) throws Exception{
		List<ZonalStatisticVO> resultList = null;
		
		try {
			//getConnectionInfo();
			
			Dataset zonalDataset = null;
			DatasetGrid valueDataset = null;
			
			if(mDatasets.contains(zonalName)) {
				zonalDataset = (Dataset) mDatasets.get(zonalName);
			}else {
				LOGGER.error("구역통계(Zonal Statistics)의 Zonal Data가 존재하지 않습니다.");
				return null;
			}
			
			if(datasets.contains(valueName)) {
				valueDataset = (DatasetGrid) datasets.get(valueName);
			}else {
				LOGGER.error("구역통계(Zonal Statistics)의 Value Data가 존재하지 않습니다.");
				return null;
			}
			
			if (mDatasets.contains(outputDatasetName))
			{
				mDatasets.delete(outputDatasetName);
			}
		
			if (mDatasets.contains(outputTableName))
			{
				mDatasets.delete(outputTableName);
			}
		
			ZonalStatisticsAnalystParameter parameter = new ZonalStatisticsAnalystParameter();
			parameter.setTargetDatasource(mDatasource);
			parameter.setTargetDatasetName(outputDatasetName);
			parameter.setTargetTableName(outputTableName);
			parameter.setZonalFieldName(zonalFieldName);
			parameter.setIgnoreNoValue(true);
			parameter.setStatisticsMode(GridStatisticsMode.MEAN);
			//parameter.setPixelFormat(PixelFormat.DOUBLE);
			parameter.setValueDataset(valueDataset);
			parameter.setZonalDataset(zonalDataset);
			
			ZonalStatisticsAnalystResult zonalStatisticResult = StatisticsAnalyst.zonalStatisticsOnRasterValue(parameter);
			DatasetVector resultDatasetVector = zonalStatisticResult.getResultTable();
			
			DatasetVector zonalDatasetVector = (DatasetVector) mDatasets.get(zonalName);
			Recordset zonalRecordSet = null;
			
			Map<Integer, Feature> list = resultDatasetVector.getAllFeatures();
			
			resultList = new ArrayList<ZonalStatisticVO>();
			
			for(Map.Entry<Integer, Feature> item : list.entrySet()) {
				Feature feature = item.getValue();
				
				String zonalId = feature.getValue("ZonalID").toString();
				double minValue = Double.valueOf(feature.getValue("Minimum").toString());
				double maxValue = Double.valueOf(feature.getValue("Maximum").toString());
				double meanValue = Double.valueOf(feature.getValue("Mean").toString());
				
				zonalRecordSet = zonalDatasetVector.query("SmID = ".concat(zonalId),CursorType.STATIC);
				
				String uniqId = (String) zonalRecordSet.getFieldValue("uniq_id");
				int zonalMin = (int) Math.round(minValue);
				int zonalMax = (int) Math.round(maxValue);
				int zonalMean = (int) Math.round(meanValue);
				
				ZonalStatisticVO resultVo = new ZonalStatisticVO();
				resultVo.setUniqId(uniqId);
				resultVo.setZonalMin(zonalMin);
				resultVo.setZonalMax(zonalMax);
				resultVo.setZonalMean(zonalMean);
				
				resultList.add(resultVo);
				
//				LOGGER.info("-------------------------------------------------------");
//				LOGGER.info("ZonalID : "+feature.getValue("ZonalID").toString());
//				LOGGER.info("Minimum : "+feature.getValue("Minimum").toString());
//				LOGGER.info("Maximum : "+feature.getValue("Maximum").toString());
//				LOGGER.info("Mean : "+feature.getValue("Mean").toString());
//				LOGGER.info("-------------------------------------------------------");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeConnection();
		}
		return resultList;
	}
	
	
	/**
	 * 데이터셋 복사
	 * @param vectorName
	 * @param copyName
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public boolean copyDataSet(String vectorName, String copyName, String query) throws Exception{
		try {
			if(datasets.contains(vectorName)) {
				dataset = (DatasetVector) datasets.get(vectorName);
				recordset = dataset.query(query,CursorType.STATIC);
				
				DatasetVector datasetVector = (DatasetVector) mDatasets.createFromTemplate(mDatasets.getAvailableDatasetName(copyName),dataset);
				
				datasetVector.append(recordset);
				datasetVector.close();
			}
		}catch(Exception e) {
			LOGGER.error("레코드셋 복사를 실패하였습니다.");
			return false;
		}finally {
			closeConnection();
		}
		return true;
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
			//getConnectionInfo();
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
	
	/** 
	 * 파일 저장
	 * @param vectorName
	 * @param outputPath
	 * @throws Exception
	 */
	public AnalFileVO exportDbToFile(String datasetName, String query, String[] ignoreFields) throws Exception{
		AnalFileVO vo = null;
		try {
			//getConnectionInfo();
			Dataset ds = null;
			FileType fType = null;
			
			//String uniquId = SuperMapBasicUtils.getDatasetUuid();
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
		if(datasets.contains(targetDatasetNm)) {
			dataset = (DatasetVector) datasets.get(targetDatasetNm);
			recordset = dataset.query(resultQuery,CursorType.STATIC);
			DatasetVector copyDatastVector = (DatasetVector) mDatasets.createFromTemplate(mDatasets.getAvailableDatasetName(resultDatasetNm),dataset);
			copyDatastVector.append(recordset);
			//CoordSysTranslator.convert(copyDatastVector, getCustomPrj5179(5179), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
			
			copyDatastVector.close();
			recordset.dispose();
			dataset.close();
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