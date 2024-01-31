import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;

import com.supermap.analyst.spatialanalyst.BufferAnalyst;
import com.supermap.analyst.spatialanalyst.BufferAnalystGeometry;
import com.supermap.analyst.spatialanalyst.BufferAnalystParameter;
import com.supermap.analyst.spatialanalyst.BufferEndType;
import com.supermap.analyst.spatialanalyst.BufferRadiusUnit;
import com.supermap.analyst.spatialanalyst.RasterClip;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetGrid;
import com.supermap.data.DatasetImage;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.DatasetVectorInfo;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EngineType;
import com.supermap.data.GeoCompound;
import com.supermap.data.GeoLegend;
import com.supermap.data.GeoLine;
import com.supermap.data.GeoMap;
import com.supermap.data.GeoRectangle;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.GeoText;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.Recordset;
import com.supermap.data.Rectangle2D;
import com.supermap.data.Size2D;
import com.supermap.data.TextPart;
import com.supermap.data.TextStyle;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.layout.LayoutElements;
import com.supermap.layout.PrintFileType;
import com.supermap.mapping.Layer;
import com.supermap.mapping.LayerSettingImage;
import com.supermap.mapping.LayerSettingVector;
import com.supermap.mapping.ThemeUnique;
import com.supermap.mapping.ThemeUniqueItem;
import com.supermap.ui.Action;
import com.supermap.ui.MapLayoutControl;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import or.sabang.sys.gis.service.AnalFileVO;

public class superMapTest {
	
	private static Workspace workspace = null;
	private static Datasources datasources = null;
	
	//database
	private static Datasource datasource = null;
	private static Datasets datasets = null;
	private static DatasetVector dataset = null;
	private static Recordset recordset = null;
	
	//udb
	private static Datasource uDatasource = null;
	private static Datasets uDatasets = null;
	private static DatasetVector uDataset = null;
	private static Recordset uRecordset = null;
	
	//vworld
	private static Datasource vDatasource = null;
	private static Datasets vDatasets = null;
	private static Dataset vDataset = null;
	private static Recordset vRecordset = null;
	
	//memory udb
	private static Datasource mDatasource = null;
	private static Datasets mDatasets = null;
	
	private MapLayoutControl mapLayoutcontrol = null;
	
	private Map<String, Object> attributes = null;
	private JSONObject returnLog = null;
	private int epsg = 0;
	
	private boolean deleted = true;
	
	/** 첨부파일 위치 지정  => globals.properties */
	private final String fileStoragePath = "/home/tomcat/FEIStorage/";
	private final static String superMapStoragePath = "/home/tomcat/FEIStorage/";
    private final String downloadMidDir = "analysis";
    private final String shpMidDir = "shpDown";
    private final static String superMapMidDir = "superMap";
	private final static String smwuFileNm = "feis.smwu";
//	private final String pGisAlias = "feis2023";
	private final static String pGisAlias = "sabang";
//	private final String pGisAlias = "feisdb";
	
	private static String analId = null;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//List<AnalFileVO> list = new ArrayList<AnalFileVO>();
		analId = getDatasetUuid();
		AnalFileVO vo = null;
		//맵이름
		String mapId = "Map_".concat(analId);
		//임도라인
		String frdLneDatasetNm = "TF_FEIS_FRD_SVYSLDINFO", frdSlopeDatasetNm = "TF_FEIS_SLOPE";
		//임도라인 복사데이터셋명, 임도라인버퍼데이터셋명
		String frdLneCopyDatasetNm = "frdLne_copy_".concat(analId), frdLneBufferDatasetVectorNm = "frdLne_buff_".concat(analId);
		//경사도 클립 데이터셋명
		String frdSlopeClipDatasetVectorNm = "frdLne_clip_".concat(analId);
		
		//임도라인 복사데이터셋벡터, 임도라인버퍼데이터셋벡터
		DatasetVector frdLneCopyDatasetVector = null, frdLneBufferDatasetVector = null;
		//임도 where조건 SQL
		String frdLneQuery = "smid = ".concat("314");
		
		Rectangle2D newBound = null;
		
		try {
			//슈퍼맵 연결
			System.out.println("슈퍼맵 연결 시작 : ".concat(analId));
			getConnectionInfo(analId);
			System.out.println("슈퍼맵 연결 완료 : ".concat(analId));
			//smwu프로젝트에 Map 생성하기
			System.out.println("Map 생성 시작 : ".concat(analId));
			createNewMap(mapId,5186);
			System.out.println("Map 생성 완료 : ".concat(analId));
			
			//임도 라인 where 조건값으로 조회 후 DataBase에서 UDB로 복사
			copyPggisToUdb(frdLneDatasetNm,frdLneCopyDatasetNm,frdLneQuery);
			//임도라인 복사데이터셋 조회
			frdLneCopyDatasetVector = (DatasetVector) uDatasets.get(frdLneCopyDatasetNm);
			
			//Recordset frdLneCopyRecordset = frdLneCopyDatasetVector.getRecordset(false, CursorType.DYNAMIC);
			
			//frdLneBufferDatasetVectorNm = uDatasets.getAvailableDatasetName(frdLneBufferDatasetVectorNm);
			//임도라인버퍼데이터셋 생성
			frdLneBufferDatasetVector = uDatasets.create(new DatasetVectorInfo(frdLneBufferDatasetVectorNm,DatasetType.REGION),PrjCoordSys.fromEPSG(5186));
			//버퍼설정값
			BufferAnalystParameter bap = new BufferAnalystParameter();
			bap.setEndType(BufferEndType.ROUND);
			bap.setRadiusUnit(BufferRadiusUnit.Meter);
			bap.setLeftDistance("200");
			bap.setRightDistance("200");
			//병합여부, 기존속성값 유무
			boolean isUnion = false,isAttributeRetained = false;
			//버퍼분석
			boolean isBufferCreated = BufferAnalyst.createBuffer(frdLneCopyDatasetVector, frdLneBufferDatasetVector, bap, isUnion, isAttributeRetained);
			
			if(!isBufferCreated) {
				System.out.println("버퍼 생성 실패");
				return;
			}
			
			//임도라인버퍼데이터셋 레코드셋 조회
			Recordset uFrdLneBufferRecordset = frdLneBufferDatasetVector.query("",CursorType.STATIC);
			//임도라인버퍼데이터셋 레코드셋 bound 조회
			///Rectangle2D frdLneBufferRecordsetBound = uFrdLneBufferRecordset.getGeometry().getBounds();
			
			GeoRegion frdLneBufferRegion = new GeoRegion();
			
			while (!uFrdLneBufferRecordset.isEOF()) {
				for (int i = 0; i < ((GeoRegion)uFrdLneBufferRecordset.getGeometry()).getPartCount(); i++) {
					frdLneBufferRegion.addPart(((GeoRegion) uFrdLneBufferRecordset.getGeometry()).getPart(i));
				}
				uFrdLneBufferRecordset.moveNext();
			}
			uFrdLneBufferRecordset.dispose();
			
			//경사도 데이터셋 조회
			Dataset slopeDataset =  datasource.getDatasets().get(frdSlopeDatasetNm);
			
			Dataset clipSlopeData = RasterClip.clip((DatasetGrid)slopeDataset,frdLneBufferRegion, true, false, uDatasource, frdSlopeClipDatasetVectorNm);
			
			uFrdLneBufferRecordset.close();
			slopeDataset.close();
			clipSlopeData.close();
			frdLneBufferDatasetVector.close();
			frdLneCopyDatasetVector.close();
			
			
			
//			if(coordSysTranslator(vnaraPlgnCopyDatasetVector,3857) && coordSysTranslator(vnaraPntCopyDatasetVector,3857) && coordSysTranslator(vnaraLneCopyDatasetVector,3857)) {
//				Recordset mRecordset = vnaraLneCopyDatasetVector.query("",CursorType.STATIC);
//				
//				Rectangle2D bound = mRecordset.getGeometry().getBounds();
//				
//				double wd = bound.getWidth()*1.5;
//				
//				newBound = new Rectangle2D();
//				
//				newBound.setTop(bound.getTop()+wd);
//				newBound.setBottom(bound.getBottom()-wd);
//				newBound.setLeft(bound.getLeft()-wd);
//				newBound.setRight(bound.getRight()+wd);
//				
//				vDataset = vDatasets.get("VworldSatellite");
//				
//				System.out.println("Map에 데이터셋 추가 시작");
//				addLayerImage(vDataset,mapId,"vworld");
//				addLayerThemeUniqueVector(vnaraPlgnCopyDatasetVector, mapId, vnaraPlgnCopyDatasetNm);
//				addLayerSingleBandVector(vnaraLneCopyDatasetVector,mapId,vnaraLneCopyDatasetNm);
//				addLayerSingleBandVector(vnaraPntCopyDatasetVector,mapId,vnaraPntCopyDatasetNm);
//				System.out.println("Map에 데이터셋 추가 완료");
//				
//				vo = getSavePath(analId,downloadMidDir);
//				vo.setOrignlFileNm("대피체계");
//				vo.setAnalType("대피체계");
//				vo.setMstId(Integer.valueOf(mstId));
//				vo.setSldId(svyLabel);
//				
//				if(setMapLayoutControl("satelliteTemplate",mapId,newBound)) {
//					vo = saveMapImage(vo);
//					if(deleted) {
//						deleteMap(mapId);
//					}
//				}
//			}else {
//				System.out.println("좌표변환에 실패하였습니다.");
//			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
	
	private static void getConnectionInfo(String uniqId) throws Exception{
		System.out.println("워크스페이스 생성 시작 : ".concat(uniqId));
		workspace = new Workspace();
		System.out.println("워크스페이스 생성 종료 : ".concat(uniqId));
		WorkspaceConnectionInfo conn = new WorkspaceConnectionInfo();
		//String path = "";//SphUtil.getFilePath(this.item.getFileDefaultLocPath(), "smwu", this.item.getServerInfohash());
		conn.setType(WorkspaceType.SMWU);
		conn.setName("feis");
		conn.setServer(superMapStoragePath + superMapMidDir + File.separator + smwuFileNm);

		workspace.open(conn);
		System.out.println("SMWU 파일 연결 : ".concat(uniqId));
		//작업공간을 열고 데이터소스 가져오기
		datasources = workspace.getDatasources();
		
		DatasourceConnectionInfo connectionInfo = null;
		
		if(datasources.contains(pGisAlias)) {
			datasource = datasources.get(pGisAlias);
			datasets = datasource.getDatasets();
			System.out.println("(OLD)"+pGisAlias+" 연결 : ".concat(uniqId));
		}
		
		if(datasources.contains("230811_test")) {
			uDatasource = datasources.get("230811_test");
			uDatasets = uDatasource.getDatasets();
			System.out.println("230811_test udb 연결 : ".concat(uniqId));
		}
		
		if(datasources.contains("api.vworld.kr")) {
			vDatasource = datasources.get("api.vworld.kr");
			vDatasets = vDatasource.getDatasets();
			System.out.println("(OLD)vworld 연결 : ".concat(uniqId));
		}
		
		if(uniqId != null) {
			connectionInfo = new DatasourceConnectionInfo();
			connectionInfo.setServer(":memory:");
			connectionInfo.setEngineType(EngineType.MEMORY);
			//connInfo.setReadOnly(readOnly);
			connectionInfo.setAlias("m".concat(uniqId));
			mDatasource = datasources.create(connectionInfo);
			mDatasets = mDatasource.getDatasets();
			System.out.println("memory udb 연결 : ".concat(uniqId));
		}
	}
	
	
	/**
	 * 슈퍼맵 연결 닫기
	 * @param type
	 * @throws Exception
	 */
	public static void closeConnection() throws Exception{
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
			System.out.println("pggis datasource closed.");
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
			System.out.println("udb datasource closed.");
		}
		
		if(vRecordset != null) {
			vRecordset.close();
			vRecordset.dispose();
			vRecordset = null;
		}
		if(vDataset != null && !vDataset.isDisposed()){
			vDataset.close();
		}
		
		if(vDatasource != null && vDatasource.isOpened()){
			vDatasource.close();
			System.out.println("vworld datasource closed.");
		}
		
		if(mDatasource != null && mDatasource.isOpened()) {
			mDatasource.close();
			System.out.println("memory udb closed.");
		}
		
		if(workspace != null) {
			//workspace.save();
			//workspace.getDatasources().closeAll();
			workspace.close();
        	workspace.dispose();
		}
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
	 * Map 생성
	 * @param mapNm
	 */
	public static void createNewMap(String mapNm, int epsg) {
		com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
		//map.setPrjCoordSys(getCustomPrj5179(epsg));
		map.setPrjCoordSys(new PrjCoordSys(epsg));
		workspace.getMaps().add(mapNm, map.toXML());
		workspace.getMaps().setMapXML(mapNm, map.toXML());
	}
	
	/**
	 * 좌표변환
	 * @param dataset
	 * @return
	 */
	private boolean coordSysTranslator(Dataset dataset,int epsg) {
		return CoordSysTranslator.convert(dataset, new PrjCoordSys(epsg), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
	}
	
	/**
	 * Pggis 데이터셋의 조건값 결과를 Memory Udb에 데이터셋 생성
	 * @param targetDatasetNm
	 * @param resultDatasetNm
	 * @param resultQuery
	 * @throws Exception
	 */
	private void copyPggisToMemoryUdb(String targetDatasetNm, String resultDatasetNm, String resultQuery) throws Exception{
		System.out.println("copyPggisToMemoryUdb 시작");
		if(datasets.contains(targetDatasetNm)) {
			System.out.println("targetDatasetNm 존재 확인");
			dataset = (DatasetVector) datasets.get(targetDatasetNm);
			System.out.println("dataset 체크");
			recordset = dataset.query(resultQuery,CursorType.STATIC);
			System.out.println("recordset 체크");
			DatasetVector copyDatastVector = (DatasetVector) mDatasets.createFromTemplate(mDatasets.getAvailableDatasetName(resultDatasetNm),dataset);
			System.out.println("copyDatastVector 템플릿 생성");
			copyDatastVector.append(recordset);
			System.out.println("recordset append");
			//CoordSysTranslator.convert(copyDatastVector, getCustomPrj5179(5179), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
			
			copyDatastVector.close();
			System.out.println("copyDatastVector closed");
			recordset.dispose();
			System.out.println("recordset dispose");
			dataset.close();
			System.out.println("dataset close");
		}
	}
	
	/**
	 * Pggis 데이터셋의 조건값 결과를 Udb에 데이터셋 생성
	 * @param targetDatasetNm
	 * @param resultDatasetNm
	 * @param resultQuery
	 * @throws Exception
	 */
	private static void copyPggisToUdb(String targetDatasetNm, String resultDatasetNm, String resultQuery) throws Exception{
		if(datasets.contains(targetDatasetNm)) {
			dataset = (DatasetVector) datasets.get(targetDatasetNm);
			recordset = dataset.query(resultQuery,CursorType.STATIC);

			DatasetVector copyDatastVector = (DatasetVector) uDatasets.createFromTemplate(uDatasets.getAvailableDatasetName(resultDatasetNm),dataset);
			
			copyDatastVector.append(recordset);
			
			//CoordSysTranslator.convert(copyDatastVector, getCustomPrj5179(5179), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
			
			copyDatastVector.close();
			
			recordset.dispose();
			dataset.close();
		}
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
			Layer layer = map.getLayers().add(image,lsi, true);
			
			map.viewEntire();
			layer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			
			map.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
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
					Object getFieldValue = record.getFieldValue(getUniqueExpr(caption));
					
					String expr = getFieldValue != null ? getFieldValue.toString() : null;
					if(expr != null) {
						exprList.add(expr);
					}
				}while(record.moveNext());
			}
			record.dispose();
			
			com.supermap.mapping.Map map = new com.supermap.mapping.Map(workspace);
			map.open(mapName);
			
			ThemeUnique themeUnique = getThemeUnique(caption,exprList);
			//Layer vectorlayer = map.getLayers().add(vector, true);
			Layer themeLayer = map.getLayers().add(vector,themeUnique,true);
			
			//map.viewEntire();
			//vectorlayer.setVisible(false);
			themeLayer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			map.refresh();
			map.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	private String getUniqueExpr(String caption) {
		String expr = null;
		if(caption.matches("koftr.*")) {
			expr = "koftr_grou";
		}else if(caption.matches("agcls.*")) {
			expr = "agcls_cd";
		}else if(caption.matches("dnst.*")) {
			expr = "dnst_cd";
		}else if(caption.matches("dmcls.*")) {
			expr = "dmcls_cd";
		}else if(caption.matches("ctrln.*")) {
			expr = "cont";
		}else if(caption.matches("nature.*")) {
			expr = "생태자연도";
		}else if(caption.matches("wplgn.*")) {
			expr = "vnara_plgn";
		}else if(caption.matches("wstat.*")) {
			expr = "smid";
		}
		
		return expr;
	}
	
	/**
	 * UniqeValue Theme Map 테마설정
	 * @param caption
	 * @param exprlist
	 * @return
	 */
	private ThemeUnique getThemeUnique(String caption,LinkedHashSet<String> exprlist) {
		ThemeUnique themeUnique = new ThemeUnique();
		themeUnique.setDefaultStyleVisible(false);
		
		themeUnique.setUniqueExpression(getUniqueExpr(caption));
		if(caption.matches("koftr.*")) {
			for (String expr : exprlist) {
				if(expr.equals("10")) {themeUnique.add(getItem(new Color(129,94,41), 100,"10","기타침엽수",caption));
				}else if(expr.equals("11")) {themeUnique.add(getItem(new Color(165,145,104), 100,"11","소나무",caption));
				}else if(expr.equals("12")) {themeUnique.add(getItem(new Color(198,154,135), 100,"12","잣나무",caption));
				}else if(expr.equals("13")) {themeUnique.add(getItem(new Color(191,150,119), 100,"13","낙엽송",caption));
				}else if(expr.equals("14")) {themeUnique.add(getItem(new Color(146,107,44), 100,"14","리기다소나무",caption));
				}else if(expr.equals("15")) {themeUnique.add(getItem(new Color(173,135,1), 100,"15","곰솔",caption));
				}else if(expr.equals("16")) {themeUnique.add(getItem(new Color(176,111,0), 100,"16","전나무",caption));
				}else if(expr.equals("17")) {themeUnique.add(getItem(new Color(177,91,0), 100,"17","편백나무",caption));
				}else if(expr.equals("18")) {themeUnique.add(getItem(new Color(153,81,0), 100,"18","삼나무",caption));
				}else if(expr.equals("19")) {themeUnique.add(getItem(new Color(138,66,18), 100,"19","가분비나무",caption));
				}else if(expr.equals("20")) {themeUnique.add(getItem(new Color(198,125,0), 100,"20","비자나무",caption));
				}else if(expr.equals("21")) {themeUnique.add(getItem(new Color(214,179,0), 100,"21","은행나무",caption));
				}else if(expr.equals("30")) {themeUnique.add(getItem(new Color(198,235,97), 100,"30","기타활엽수",caption));
				}else if(expr.equals("31")) {themeUnique.add(getItem(new Color(222,248,41), 100,"31","상수리나무",caption));
				}else if(expr.equals("32")) {themeUnique.add(getItem(new Color(193,220,7), 100,"32","신갈나무",caption));
				}else if(expr.equals("33")) {themeUnique.add(getItem(new Color(149,169,5), 100,"33","굴참나무",caption));
				}else if(expr.equals("34")) {themeUnique.add(getItem(new Color(207,249,66), 100,"34","기타참나무류",caption));
				}else if(expr.equals("35")) {themeUnique.add(getItem(new Color(230,243,0), 100,"35","오리나무",caption));
				}else if(expr.equals("36")) {themeUnique.add(getItem(new Color(230,233,16), 100,"36","고로쇠나무",caption));
				}else if(expr.equals("37")) {themeUnique.add(getItem(new Color(230,213,0), 100,"37","자작나무",caption));
				}else if(expr.equals("38")) {themeUnique.add(getItem(new Color(230,200,0), 100,"38","박달나무",caption));
				}else if(expr.equals("39")) {themeUnique.add(getItem(new Color(175,224,7), 100,"39","밤나무",caption));
				}else if(expr.equals("40")) {themeUnique.add(getItem(new Color(137,175,5), 100,"40","물푸레나무",caption));
				}else if(expr.equals("41")) {themeUnique.add(getItem(new Color(100,129,3), 100,"41","서어나무",caption));
				}else if(expr.equals("42")) {themeUnique.add(getItem(new Color(78,100,2), 100,"42","때죽나무",caption));
				}else if(expr.equals("43")) {themeUnique.add(getItem(new Color(218,237,154), 100,"43","호두나무",caption));
				}else if(expr.equals("44")) {themeUnique.add(getItem(new Color(181,230,51), 100,"44","백합나무",caption));
				}else if(expr.equals("45")) {themeUnique.add(getItem(new Color(158,181,2), 100,"45","포플러",caption));
				}else if(expr.equals("46")) {themeUnique.add(getItem(new Color(131,188,34), 100,"46","벚나무",caption));
				}else if(expr.equals("47")) {themeUnique.add(getItem(new Color(155,186,61), 100,"47","느티나무",caption));
				}else if(expr.equals("48")) {themeUnique.add(getItem(new Color(118,169,32), 100,"48","층층나무",caption));
				}else if(expr.equals("49")) {themeUnique.add(getItem(new Color(0,149,55), 100,"49","아까시나무",caption));
				}else if(expr.equals("60")) {themeUnique.add(getItem(new Color(33,102,139), 100,"60","기타상록활엽수",caption));
				}else if(expr.equals("61")) {themeUnique.add(getItem(new Color(179,225,172), 100,"61","가시나무",caption));
				}else if(expr.equals("62")) {themeUnique.add(getItem(new Color(179,222,105), 100,"62","구실잣밤나무",caption));
				}else if(expr.equals("63")) {themeUnique.add(getItem(new Color(179,220,16), 100,"63","녹나무",caption));
				}else if(expr.equals("64")) {themeUnique.add(getItem(new Color(102,184,33), 100,"64","굴거리나무",caption));
				}else if(expr.equals("65")) {themeUnique.add(getItem(new Color(102,189,108), 100,"65","황칠나무",caption));
				}else if(expr.equals("66")) {themeUnique.add(getItem(new Color(102,194,165), 100,"66","사스레피나무",caption));
				}else if(expr.equals("67")) {themeUnique.add(getItem(new Color(0,153,155), 100,"67","후박나무",caption));
				}else if(expr.equals("68")) {themeUnique.add(getItem(new Color(0,146,112), 100,"68","새덕이",caption));
				}else if(expr.equals("77")) {themeUnique.add(getItem(new Color(155,192,41), 100,"77","침활혼효림",caption));
				}else if(expr.equals("78")) {themeUnique.add(getItem(new Color(217,182,115), 100,"78","죽림",caption));
				}else if(expr.equals("81")) {themeUnique.add(getItem(new Color(204,204,204), 100,"81","미립목지",caption));
				}else if(expr.equals("82")) {themeUnique.add(getItem(new Color(228,177,155), 100,"82","제지",caption));
				}else if(expr.equals("83")) {themeUnique.add(getItem(new Color(236,249,199), 100,"83","관목덤불",caption));
				}else if(expr.equals("91")) {themeUnique.add(getItem(new Color(254,246,164), 100,"91","주거지",caption));
				}else if(expr.equals("92")) {themeUnique.add(getItem(new Color(170,218,50), 100,"92","초지",caption));
				}else if(expr.equals("93")) {themeUnique.add(getItem(new Color(219,205,0), 100,"93","경작지",caption));
				}else if(expr.equals("94")) {themeUnique.add(getItem(new Color(190,210,255), 100,"94","수체",caption));
				}else if(expr.equals("95")) {themeUnique.add(getItem(new Color(217,125,23), 100,"95","과수원",caption));
				}else if(expr.equals("99")) {themeUnique.add(getItem(new Color(255,255,193), 100,"99","기타",caption));}
			}
		}else if(caption.matches("agcls.*")) {
			for (String expr : exprlist) {
				if(expr.equals("1")) {
					themeUnique.add(getItem(new Color(53,130,53), 100,"1","1영급",caption));
				}else if(expr.equals("2")) {
					themeUnique.add(getItem(new Color(63,140,63), 100,"2","2영급",caption));
				}else if(expr.equals("3")) {
					themeUnique.add(getItem(new Color(73,150,73), 100,"3","3영급",caption));
				}else if(expr.equals("4")) {
					themeUnique.add(getItem(new Color(83,160,83), 100,"4","4영급",caption));
				}else if(expr.equals("5")) {
					themeUnique.add(getItem(new Color(93,170,93), 100,"5","5영급",caption));
				}else if(expr.equals("6")) {
					themeUnique.add(getItem(new Color(103,180,103), 100,"6","6영급",caption));
				}else if(expr.equals("7")) {
					themeUnique.add(getItem(new Color(129,193,129), 100,"7","7영급",caption));
				}else if(expr.equals("8")) {
					themeUnique.add(getItem(new Color(146,202,146), 100,"8","8영급",caption));
				}else if(expr.equals("9")) {
					themeUnique.add(getItem(new Color(173,215,173), 100,"9","9영급",caption));
				}
			}
		}else if(caption.matches("dnst.*")) {
			for (String expr : exprlist) {
				if(expr.equals("A")) {
					themeUnique.add(getItem(new Color(0,236,118), 100,"A","소",caption));
				}else if(expr.equals("B")) {
					themeUnique.add(getItem(new Color(0,196,118), 100,"B","중",caption));
				}else if(expr.equals("C")) {
					themeUnique.add(getItem(new Color(0,156,118), 100,"C","밀",caption));
				}
			}
		}else if(caption.matches("dmcls.*")) {
			for (String expr : exprlist) {
				if(expr.equals("0")) {
					themeUnique.add(getItem(new Color(252,213,181), 100,"0","치수",caption));
				}else if(expr.equals("1")) {
					themeUnique.add(getItem(new Color(250,192,144), 100,"1","소경목",caption));
				}else if(expr.equals("2")) {
					themeUnique.add(getItem(new Color(228,108,10), 100,"2","중경목",caption));
				}else if(expr.equals("3")) {
					themeUnique.add(getItem(new Color(152,72,7), 100,"3","대경목",caption));
				}
			}
		}else if(caption.matches("ctrln.*")) {
			for (String expr : exprlist) {
				double cont = Double.parseDouble(expr);
				if(cont%100 == 0) {
					themeUnique.add(getItem(new Color(185,93,49), 100,expr,expr,caption));
				}else if(cont%20 == 0){
					themeUnique.add(getItem(new Color(255,192,162), 100,expr,expr,caption));
				}
			}
		}else if(caption.matches("nature.*")) {
			themeUnique.add(getItem(new Color(47,152,47), 100,"1","1등급",caption));
			themeUnique.add(getItem(new Color(207,227,201), 100,"2","2등급",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"3","3등급",caption));
		}else if(caption.matches("wplgn.*")) {
			themeUnique.add(getItem(new Color(3,4,189), 0,"01","사방댐",caption));
			themeUnique.add(getItem(new Color(244,209,111), 0,"02","계류보전",caption));
			themeUnique.add(getItem(new Color(3,4,189), 0,"03","유역면적",caption));
			themeUnique.add(getItem(new Color(211,34,4), 0,"04","산지사방",caption));
		}else if(caption.matches("wstat.*")) {
			themeUnique.add(getItem(new Color(255,255,255), 100,"1","1",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"2","2",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"3","3",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"4","4",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"5","5",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"6","6",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"7","7",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"8","8",caption));
			themeUnique.add(getItem(new Color(255,255,255), 100,"9","9",caption));
		}
		
		return themeUnique;
	}
	
	private ThemeUniqueItem getItem(Color color, int opaqueRate, String value, String caption, String type) {
		// Set the items to the object of themeunique
		ThemeUniqueItem item = new ThemeUniqueItem();
		item.setUnique(value);
		item.setCaption(caption);
		item.setVisible(true);

		// Set the style of the items
		GeoStyle geostyle = new GeoStyle();
		if(type.matches("ctrln.*")) {
			double cont = Double.parseDouble(value);
			geostyle.setLineColor(color);
			if(cont%100 == 0) {
				geostyle.setLineWidth(0.5);
			}else if(cont%20 == 0){
				geostyle.setLineWidth(0.1);
			}
		}else if(type.matches("wplgn.*")) {
			geostyle.setFillForeColor(color);
			geostyle.setFillOpaqueRate(opaqueRate);
			geostyle.setLineWidth(0.5);
			geostyle.setLineColor(color);
			if(value.equals("01")) {
				geostyle.setLineSymbolID(0);
			}else if(value.equals("02")) {
				geostyle.setLineSymbolID(1);
			}else if(value.equals("03")) {
				geostyle.setLineSymbolID(1);
			}else if(value.equals("04")) {
				geostyle.setLineSymbolID(0);
			}
		}else if(type.matches("wstat.*")) {
			int valnum = Integer.valueOf(value);
			geostyle.setMarkerSymbolID(322+valnum);
			geostyle.setMarkerSize(new Size2D(8, 8));
		}else {
			geostyle.setFillForeColor(color);
			geostyle.setFillOpaqueRate(opaqueRate);
			geostyle.setLineSymbolID(5);
		}

		item.setStyle(geostyle);

		return item;
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
			lsv.setStyle(getGeoStyle(caption));
			
			map.viewEntire();
			layer.setVisible(true);
			workspace.getMaps().setMapXML(mapName, map.toXML());
			
			map.close();
		}catch (Exception e) {
			System.out.println("Map에 Vector DataSet 추가 : "+e.getMessage());
		}
		return true;
	}
	
	/**
	 * 벡터 데이터셋 심볼 설정
	 * @param caption
	 * @return
	 * @throws Exception
	 */
	private GeoStyle getGeoStyle(String caption) throws Exception{
		GeoStyle style = new GeoStyle();
		
		style.setFillForeColor(Color.white);
		style.setFillOpaqueRate(0);
		
		if(caption.matches("lgstr.*")) {
			style.setLineColor(Color.lightGray);
		}else if(caption.matches("watershed.*")) {
			style.setLineColor(Color.red);
			style.setLineWidth(0.2);
		}else if(caption.matches("ecrtcnl.*")) {
			style.setFillForeColor(Color.red);
			style.setFillOpaqueRate(1);
			style.setMarkerSize(new Size2D(8, 8));
			style.setLineColor(Color.red);
		}else if(caption.matches("ctrln.*")) {
			style.setLineColor(Color.lightGray);
		}else if(caption.matches("lcp_ctrln.*")) {
			style.setLineColor(new Color(228,194,107));
		}else if(caption.matches("road.*")) {
			style.setLineColor(new Color(48,120,235));
			style.setLineWidth(0.5);
		}else if(caption.matches("rank.*")) {
			style.setLineColor(new Color(156,18,18));
			style.setLineWidth(0.5);
		}else if(caption.matches("river.*")) {
			style.setLineColor(new Color(0,112,192));
			style.setLineWidth(0.5);
		}else if(caption.matches("wlgstr.*")) {//취약지역 지적선
			style.setLineColor(new Color(107,107,107));
			style.setLineWidth(0.1);
		}else if(caption.matches("wpnt.*")) {//취약지역 유출구
			style.setFillForeColor(new Color(254,0,0));
			style.setFillOpaqueRate(1);
			style.setMarkerSize(new Size2D(8, 8));
			style.setLineColor(new Color(254,0,0));
//			style.setLineColor(new Color(0,0,0));
//			style.setLineWidth(1);
//			style.setMarkerSymbolID(908129);
		}else if(caption.matches("wlne.*")) {//취약지역 대피로
			style.setLineColor(new Color(211,34,4));
			style.setLineWidth(0.5);
		}else if(caption.matches("frd.*")) {
			if(caption.indexOf("_y_") > 0) {
				style.setLineColor(new Color(255,255,0));
				style.setLineWidth(0.5);
			}
		}
		else {
			style.setLineColor(Color.black);
		}
		
		return style;
	}
	
	/**
	 * 파일저장 경로 생성
	 * @return
	 * @throws Exception
	 */
	private AnalFileVO getSavePath(String analId, String midPath) throws Exception{
		AnalFileVO vo = new AnalFileVO();
		
		Date date = new Date();
		String year = (new SimpleDateFormat("yyyy").format(date)); 
		String month = (new SimpleDateFormat("MM").format(date)); 
		String day = (new SimpleDateFormat("dd").format(date));
		
		//analisis/img/2022/10/21/
		String midDatePath = year + File.separator + month + File.separator + day;
		String strePathString = fileStoragePath.concat(midPath) + File.separator + midDatePath + File.separator + analId;
		String streFileNm = EgovFileUploadUtil.getPhysicalFileName();
		//String streFullPath = strePathString + File.separator + streFileNm;
		
		File saveFolder = new File(EgovWebUtil.filePathBlackList(strePathString));
		
		if (!saveFolder.exists() || saveFolder.isFile()) {
			if (saveFolder.mkdirs()){
				System.out.println("[file.mkdirs] saveFolder : Creation Success ");
			}else{
				System.out.println("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}
		
		vo.setAnalId(analId);
		vo.setFileStreCours(strePathString);
		vo.setStreFileNm(streFileNm);
		
		return vo;
	}
	
	/**
	 * 템플릿 레이아웃 설정
	 * @param name
	 * @throws Exception
	 */
	public boolean setMapLayoutControl(String layoutName, String mapName,Rectangle2D rect){
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
			System.out.println("템플릿 레이아웃 설정 완료");
		} catch (Exception e) {
			System.out.println("템플릿 레이아웃 설정 오류 : "+e.getMessage());
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
	 * 지도 이미지 저장
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO saveMapImage(AnalFileVO vo) throws Exception{
		System.out.println("지도 이미지 저장 시작");
		String saveFileNm = null;
		AnalFileVO fileVo = new AnalFileVO();
		
		BeanUtils.copyProperties(vo,fileVo);
		
		if(mapLayoutcontrol != null) {
			try {
				//fileVo = getSavePath(analId);
				//analisis/img/2022/10/21/
				String strePathString = fileVo.getFileStreCours();
				String streFileNm = fileVo.getStreFileNm();
				String streFullPath = strePathString + File.separator + streFileNm;
				
				boolean status = mapLayoutcontrol.getMapLayout().printToFile(streFullPath.concat(".png"), PrintFileType.PNG, 96);
				if(status) {
					saveFileNm = streFullPath.concat(".png");
					fileVo.setFileExtsn("png");//파일확장자
					System.out.println("지도 이미지 저장 완료");
				}else {
					System.out.println("분석이미지 저장을 실패하였습니다.");
				}
			} catch (Exception e) {
				System.out.println("지도 이미지 저장 : "+e.getMessage());
			}
			
		}
		System.out.println(saveFileNm);
		return fileVo;
	}
	
	/**
	 * 분석결과 고유 데이터셋명 생성
	 * @return
	 * @throws Exception
	 */
	public static String getDatasetUuid() throws Exception{
		return String.valueOf((new Date().getTime()));
		//return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
