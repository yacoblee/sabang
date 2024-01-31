package or.sabang.utl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.supermap.data.ColorDictionary;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetGrid;
import com.supermap.data.DatasetType;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasource;
import com.supermap.data.GeoLine;
import com.supermap.data.GeoPoint;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.Recordset;
import com.supermap.data.Size2D;
import com.supermap.data.StatisticsResult;
import com.supermap.data.TextAlignment;
import com.supermap.data.TextStyle;
import com.supermap.data.Workspace;
import com.supermap.layout.PrintFileType;
import com.supermap.mapping.LabelBackShape;
import com.supermap.mapping.ThemeGridRange;
import com.supermap.mapping.ThemeGridRangeItem;
import com.supermap.mapping.ThemeLabel;
import com.supermap.mapping.ThemeUnique;
import com.supermap.mapping.ThemeUniqueItem;
import com.supermap.ui.MapLayoutControl;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import or.sabang.sys.gis.service.AnalFileVO;

public class SuperMapBasicUtils {
	public static final Logger LOGGER = LoggerFactory.getLogger(SuperMapBasicUtils.class);
	private static final String fileStoragePath = EgovProperties.getProperty("Globals.fileStorePath");
	
	public static final boolean deleted = true;
	
	/** 첨부파일 위치 지정  => globals.properties */
	public static final String superMapStoragePath = EgovProperties.getProperty("Globals.fileStorePath.superMap");
	public static final String downloadMidDir = "analysis";
	public static final String shpMidDir = "shpDown";
	public static final String superMapMidDir = "superMap";
	public static final String smwuFileNm = "feis.smwu";
//	public static final String pGisAlias = "feis2023";
	//개발
	public static final String pGisAlias = "sabang";
	//운영
//	public static final String pGisAlias = "feisdb";
	
	static List<Double> slopeCdInterval = new ArrayList<>(Arrays.asList(10.0,20.0,30.0,40.0,50.0,60.0,70.0,80.0,Double.MAX_VALUE));
	static List<Double> aspectCdInterval = new ArrayList<>(Arrays.asList(0.0,22.5,67.5,112.5,157.5,202.5,247.5,292.5,337.5,360.0));
	static List<Double> demCdInterval = new ArrayList<>(Arrays.asList(100.0,200.0,300.0,400.0,500.0,600.0,700.0,800.0,900.0,1000.0,1100.0,1200.0,Double.MAX_VALUE));
	static List<Double> lndCdInterval = new ArrayList<>(Arrays.asList(1.1,2.1,3.1,4.1,5.1));
	
	//경사
	static List<String> slopeCdIntervalLabel = new ArrayList<>(Arrays.asList("10도 미만","10-20도","20-30도","30-40도","40-50도","50-60도","60-70도","70-80도","80도 이상"));
	//향
	static List<String> aspectCdIntervalLabel = new ArrayList<>(Arrays.asList("평지","북","북동","동","남동","남","남서","서","북서","북"));
	//표고
	static List<String> demCdIntervalLabel = new ArrayList<>(Arrays.asList("0-100m","100-200m","200-300m","300-400m","400-500m","500-600m","600-700m","700-800m","800-900m","900-1000m","1000-1100m","1100-1200m","1200m 이상"));
	//산사태위험지도
	static List<String> lndCdIntervalLabel = new ArrayList<>(Arrays.asList("1등급","2등급","3등급","4등급","5등급"));//사방사업타평에 해당없음 항목이 있는데 체크.
	//임도(수종), 사방(임상)
	static List<String> koftrCdIntervalLabel = new ArrayList<>(Arrays.asList("기타침엽수","소나무","잣나무","낙엽송","리기다소나무","곰솔","전나무","편백나무","삼나무","가분비나무","비자나무","은행나무","기타활엽수","상수리나무","신갈나무","굴참나무","기타참나무류","오리나무","고로쇠나무","자작나무","박달나무","밤나무","물푸레나무","서어나무","때죽나무","호두나무","백합나무","포플러","벚나무","느티나무","층층나무","아까시나무","기타상록활엽수","가시나무","구실잣밤나무","녹나무","굴거리나무","황칠나무","사스레피나무","후박나무","새덕이","침활혼효림","죽림","미립목지","제지","관목덤불","주거지","초지","경작지","수체","과수원","기타"));
	//임도(임상)
	static List<String> frtpCdIntervalLabel = new ArrayList<>(Arrays.asList("무립지/비산림","침엽수림","활엽수림","혼효림","죽림"));
	//임종
	static List<String> frorCdIntervalLabel = new ArrayList<>(Arrays.asList("무립지/비산림","임공림","천연림"));
	//영급
	static List<String> agclsCdIntervalLabel = new ArrayList<>(Arrays.asList("1영급","2영급","3영급","4영급","5영급","6영급","7영급","8영급","9영급"));
	//경급
	static List<String> dmclsCdIntervalLabel = new ArrayList<>(Arrays.asList("치수","소경목","중경목","대경목"));
	//밀도
	static List<String> dnstCdIntervalLabel = new ArrayList<>(Arrays.asList("소","중","밀"));
	//토성
	static List<String> soilCdIntervalLabel = new ArrayList<>(Arrays.asList("사양토","양토","미사질양토","미사질식양토","사질식양토","미사질식토","식양토","미사","양질사토","사토","점토","기타"));
	//지질
	static List<String> geologyCdIntervalLabel = new ArrayList<>(Arrays.asList("각섬석화강암","각섬암","갈두층","격포리층","고성층","규암","길왕리층","낙동층","달길층","덕용산규암","두무진층","두원층","류문암,류문암질응회암","마이산층","만덕산층","매립지","맥암","면수산규암","문수산층","미고결 퇴적층","미그마타이트질 편마암","미그마타이트질편마암","박달령화강편마암","반려암","반상변성질편마암","반상변성질편편마암","반상변정질편마암","반상화강암","반송층군","반송층군 대동층군","반암류","반휘암","방림층군","방이리층","변성사질암","변성사질암대","변성사질암류","변성석영반암","변성화산암류","부산반상질화강편마암","북평층군","분천 화강편마암","사문암","사암 및 이암","사암및셰일","사암및응회암","사암및이암","산성맥암류","산성반암","산성암","산성암맥","산성화강암류","산성화산암","산성화산암류","산수동층","상부남포층군(성주리층)","상부대석회암층군","상부연일층군","상부천매암","상부천매암대","상부평안층군","서귀포층","석문층","석영반암","석회암","석회암대","선소층","설옥리층","섬록암","섬장암","세립질화강암","신라력암","신라역암","신성리층","안구장편마암","안산암","안산암,안산암질응회암","안산암및안산암질응회암","양덕층군","양평화성복합체","어일층군","역암","역암 및 사암","역질사암","염기성화산암","엽리상화강암","엽리상화강암류","영월형 조선누층군","용암산층","우백질편마암","우백질화강암","우항리층","원남층군","유문암및유문암질응회암","유치역암","율리층군","응회암","장기층군","장동층","장목리층","장촌층","장평리층","적상산층","적성층","정선형 조선누층군","조면안산암","조면안산암 분석구","조면암","조면암 및 조면안산암","조면암 및 현무암","조면암 분석구","조면현무암(I)","조면현무암(I) 응회암","조면현무암(II)","조면현무암(II) 분석구","조면현무암(III)","조면현무암(III) 분석구","조면현무암(IV)","조면현무암(V)","조면현무암(V) 분석구","조면현무암(VI)","조면현무암(VI) 분석구","조면현무암(VII)","조면현무암(VII) 분석구","조면현무암(VIII)","조면현무암(VIII) 분석구","조면현무암(VIII) 응회암","중봉산 화강편마암","중부공주층군","중부남포층군(백운사층,초계리층)","중부대석회암층군","중부연일층군","중부초평층군","중부평안층군","중성,염기성반암","중성및염기성화산암","중성및염기성화산암류","중성암","중성암맥","중화동층","진동층","진주층","청산화강암","충적층","칠곡층","태안층군","통진층","퇴적암","퇴적암 및 화산암","퇴적층","편마암류","편암","편암류","평창형 조선누층군","평해층군","하부공주층군","하부남포층군(아미산층,하조층)","하부대석회암층군","하부연일층군","하부천매암","하부천매암대","하부초평층군","하부평안층군","하산동층","함력천매암","함력천매암대","함석류석 화강편마암","함석류석화강편마암","함안층","함역천매암대","현무암","현무암(I)","현무암(I) 응회암","현무암(II)","현무암(II) 분석구","현무암(III)","현무암(III) 분석구","호상편마암","홍도층","홍제사 화강암","화강반암","화강섬록암","화강암","화강암류","화강암질편마암","화강편마암","화산암류","회장암","휘록암","흑운모편마암","흑운모화강암","흙,모래,자갈"));
	//모암
	static List<String> prrckCdIntervalLabel = new ArrayList<>(Arrays.asList("화성암","퇴적암","변성암"));
	//퇴적양식
	static List<String> accmaCdIntervalLabel = new ArrayList<>(Arrays.asList("잔적토","보행토(포행토)","봉적토"));
	//암석노출도
	static List<String> rockCdIntervalLabel = new ArrayList<>(Arrays.asList("10% 이하","10~30%","30~50%","50~75%"));
	//생태자연도
	static List<String> natureCdIntervalLabel = new ArrayList<>(Arrays.asList("1등급","2등급","3등급"));
	/**
	 * 데이터셋 이름 확인
	 * @param caption
	 * @return
	 */
	public static String getLayerName(String caption) {
		String layerNm = null;
		
		if(caption.equals("ecrtcnl")) {
			layerNm = "tf_feis_ecrtcnl";
		}else if(caption.equals("watershed")) {
			layerNm = "tf_feis_watershed";
		}else if(caption.equals("lgstr")) {
			layerNm = "tf_feis_lgstr";
		}else if(caption.equals("ctrln")) {
			layerNm = "tf_feis_ctrln";
		}else if(caption.equals("river")) {
			layerNm = "tf_feis_river";
		}else if(caption.equals("slope")) {
			layerNm = "tf_feis_slope";
		}else if(caption.equals("aspect")) {
			layerNm = "tf_feis_aspect";
		}else if(caption.equals("dem")) {
			layerNm = "tf_feis_dem";
		}else if(caption.equals("koftr")) {
			layerNm = "tf_feis_im5000";
		}else if(caption.equals("agcls")) {
			layerNm = "tf_feis_im5000";
		}else if(caption.equals("dnst")) {
			layerNm = "tf_feis_im5000";
		}else if(caption.equals("dmcls")) {
			layerNm = "tf_feis_im5000";
		}else if(caption.equals("nature")) {
			layerNm = "tf_feis_eclgy_nature";
		}else if(caption.equals("forestroad")) {
			layerNm = "tf_feis_frd_svysldinfo";
		}else if(caption.equals("geological")) {
			layerNm = "tf_feis_geological_lclas";
		}else if(caption.equals("frtp")) {
			layerNm = "tf_feis_im5000";
		}else if(caption.equals("fror")) {
			layerNm = "tf_feis_im5000";
		}else if(caption.equals("soil")) {
			layerNm = "tf_feis_ij100";
		}else if(caption.equals("geology")) {
			layerNm = "tf_feis_geology_250k_litho";
		}else if(caption.equals("prrck")) {
			layerNm = "tf_feis_ij100";
		}else if(caption.equals("accma")) {
			layerNm = "tf_feis_ij100";
		}else if(caption.equals("rock")) {
			layerNm = "tf_feis_ij100";
		}else if(caption.equals("landslide")) {
			layerNm = "tf_feis_landslide";
		}else if(caption.equals("hillshade")) {
			layerNm = "tf_feis_hillshade";
		}else if(caption.equals("stream3ha")) {
			layerNm = "tf_feis_stream_3ha";
		}else if(caption.equals("stream5ha")) {
			layerNm = "tf_feis_stream_5ha";
		}else if(caption.equals("sido")) {
			layerNm = "tf_feis_ctprvn";
		}else if(caption.equals("signgu")) {
			layerNm = "tf_feis_signgu";
		}else if(caption.equals("road")) {
			layerNm = "tf_feis_road";
		}else if(caption.equals("building")) {
			layerNm = "tf_feis_building";
		}else if(caption.equals("li")) {
			layerNm = "tf_feis_li";
		}else if(caption.equals("bsc")) {
			layerNm = "tf_feis_bsc_svycompt";
		}else if(caption.equals("apr")) {
			layerNm = "tf_feis_fck_aprcompt";
		}else if(caption.equals("lcp")) {
			layerNm = "tf_feis_lcp_svycompt";
		}else if(caption.equals("wka")) {
			layerNm = "tf_feis_wka_svycompt";
		}else if(caption.equals("cnl")) {
			layerNm = "tf_feis_cnl_svycompt";
		}else if(caption.equals("frd")) {
			layerNm = "tf_feis_frd_svycompt";
		}else if(caption.equals("pcs")) {
			layerNm = "tf_feis_pcs_svycompt";
		}else if(caption.equals("mse")) {
			layerNm = "tf_feis_mse_svycompt";
		}
		
		return layerNm;
	}
	
	/**
	 * 그리드 데이터셋 통계분류
	 * @param grid
	 * @param caption
	 * @return
	 * @throws Exception
	 */
	public static Map<Integer,Integer> caculateGridInterval(DatasetGrid grid,String caption) throws Exception{
		Map<Integer,Integer> results = new HashMap<Integer, Integer>();
		double noValue = grid.getNoValue();
		int checkValueCnt = 0;
		for (int row = 0; row < grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                double cellValue = grid.getValue(col, row); // Note the order of col and row
                //list.add(cellValue);
                if(cellValue != noValue) {
                	List<Double> selectInterval = null;
                	if(caption.equals("slope")) {
                		selectInterval = slopeCdInterval;
                	}else if(caption.equals("aspect")) {
                		selectInterval = aspectCdInterval;
                	}else if(caption.equals("dem")) {
                		selectInterval = demCdInterval;
                	}else if(caption.equals("landslide")) {
                		selectInterval = lndCdInterval;
                	}
                	
                	for (int loop = 0; loop < selectInterval.size(); loop++) {
            			double interval = selectInterval.get(loop);
            			if(loop == 0) {
            				if(cellValue < interval) {
            					if(results.get(loop) == null) {
            						results.put(loop, 1);
            					}else {
            						results.put(loop, results.get(loop)+1);
            					}
            				}
            			}else {
            				if(cellValue >= selectInterval.get(loop-1) && cellValue < interval) {
            					if(results.get(loop) == null) {
            						results.put(loop, 1);
            					}else {
            						results.put(loop, results.get(loop)+1);
            					}
            				}
            			}
        		    }
                	checkValueCnt++;
                }
            }
        }
		results.put(100, checkValueCnt);
		
		if(caption.equals("aspect")) {
			if(results.get(1) != null && results.get(9) != null) {
				results.put(1,results.get(1)+results.get(9));
				results.remove(9);
			}
		}
		
		System.out.println("caculateGridInterval" + results.toString());
		return results;
	}
	
	/**
	 * 분석통계 단계별 명칭
	 * @param caption
	 * @return
	 * @throws Exception
	 */
	public static String getCaculateIntervalValue(Integer indx, String caption) throws Exception{
		String intervalValue = "-";
		if(caption.equals("slope")) {
			intervalValue = slopeCdIntervalLabel.get(indx);
		}else if(caption.equals("aspect")) {
			intervalValue = aspectCdIntervalLabel.get(indx);
		}else if(caption.equals("dem")) {
			intervalValue = demCdIntervalLabel.get(indx);
		}else if(caption.equals("landslide")) {
			intervalValue = lndCdIntervalLabel.get(indx);
		}
		return intervalValue;
	}
	
	/**
	 * 그리드 데이터셋 통계생성
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	public static JSONObject caculateStatisticsGrid(Dataset ds,String caption,Double dsArea) throws Exception{
		//HashMap<String, String> map = new HashMap<String, String>();
		JSONObject results = new JSONObject();
		JSONObject ratioResult = new JSONObject();
		JSONObject areaResult = new JSONObject();
		JSONObject originAreaResult = new JSONObject();
		DatasetType dt = ds.getType();
//		BigDecimal maxValue = new BigDecimal("0");
		
		//String dsNm = ds.getName();
		
		if(dt == DatasetType.GRID) {
			DatasetGrid grid = (DatasetGrid)ds;
			
			StatisticsResult statisticsResult = grid.buildStatistics();
			results.put("종류", "grid");
			results.put("평균", statisticsResult.getAverage());
			results.put("최고", statisticsResult.getMaxValue());
			results.put("최소", statisticsResult.getMinValue());
			results.put("표준편차", statisticsResult.getStdDeviation());
			
			Map<Integer,Integer> caculateGridMapRatio = caculateGridInterval(grid,caption);
			
			int intevalSum = caculateGridMapRatio.get(100);
			
			for( Map.Entry<Integer,Integer> entry : caculateGridMapRatio.entrySet() ){
				int strKey = entry.getKey();
				int strValue = entry.getValue();
//			    System.out.println( strKey +":"+ strValue );
			    
			    if(strKey != 100) {
			    	String keyLabel = getCaculateIntervalValue(strKey,caption);
			    	
			    	// 기존 BigDecimal 계산버전
//			    	BigDecimal ratioValue = new BigDecimal(Double.toString((double)strValue/(double)intevalSum*100.0));
//			    	BigDecimal areaValue = new BigDecimal(Double.toString(dsArea*((double)strValue/(double)intevalSum)/10000.0));
//			    	ratioResult.put(keyLabel, ratioValue.setScale(1, RoundingMode.HALF_UP)+"%");
//			    	areaResult.put(keyLabel, areaValue.setScale(1, RoundingMode.HALF_UP)+"ha");
			    	
			    	// 신규 double 계산버전
			    	double ratioValue =  Math.round((((double)strValue/(double)intevalSum)*100.0)*10)/10.0;
			    	double areaValue =  Math.round(dsArea*(((double)strValue/(double)intevalSum)/10000.0)*10)/10.0;
			    	
					ratioResult.put(keyLabel, Double.toString(ratioValue)+"%");
					areaResult.put(keyLabel, Double.toString(areaValue)+"ha");
			    	
			    	originAreaResult.put(keyLabel,dsArea*((double)strValue/(double)intevalSum));
			    }
			}
			results.put("비율", ratioResult);
			results.put("면적", areaResult);
			results.put("면적(m2)", originAreaResult);
			
			System.out.println(results.toString());
		}
		
		return results;
	}
	
	
	
	/**
	 * 벡터데이터셋 통계생성
	 * @param ds
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static JSONObject caculateStatisticsVector(Dataset ds, String captionNm) throws Exception{
		JSONObject results = new JSONObject();
		DatasetType dt = ds.getType();
		String fieldName = getUniqueExprNm(captionNm);
		double maxValue = 0.0;
		
		if((dt == DatasetType.POINT || dt == DatasetType.LINE || dt == DatasetType.REGION) && fieldName != null) {
			results.put("종류", "vector");
			HashMap<String, String> ratioResult = new HashMap<String, String>();//비율
			HashMap<String, String> areaResult = new HashMap<String, String>();//면적
			JSONObject originAreaResult = new JSONObject();//면적(m2)
			HashMap<String, Double> map = new HashMap<String, Double>();
			 
			DatasetVector vector = (DatasetVector)ds;
			Recordset recordset = vector.getRecordset(false, CursorType.STATIC);
			
			recordset.moveFirst();
			
			double totalArea = 0;
			
			while(!recordset.isEOF()) {
				String nm = recordset.getString(fieldName);
				if(nm != null) {
					GeoRegion region = (GeoRegion) recordset.getGeometry();				
					double area = region.getArea();
					totalArea+=area;
					if(map.get(nm) == null) {
						map.put(nm, area);
					}else {
						double val = Double.valueOf(map.get(nm));
						map.put(nm, val+area);
					}
					//System.out.println(nm+" : "+area);
				}
				recordset.moveNext();
			}
			recordset.close();
			vector.close();
			
			Iterator<String> keys = map.keySet().iterator();
			while( keys.hasNext() ){
				String strKey = keys.next();
				double strValue = map.get(strKey);
				
				//기존 BigDecimal 계산버전
				//BigDecimal chkValue = new BigDecimal(Double.toString(strValue/totalArea*100.0));
				//BigDecimal areaValue = new BigDecimal(Double.toString(strValue/10000.0));
				
				// 신규 double 계산버전
				double chkValue = strValue/totalArea*100.0;
				double areaValue = strValue/10000.0;
				
				if(fieldName.equals("생태자연도")) {
					strKey = strKey+"등급";
				}else if(fieldName.equals("sibflr_scs")) {
					if(strKey.equals("01")) {
						strKey = "사양토";
					}else if(strKey.equals("02")) {
						strKey = "양토";
					}else if(strKey.equals("03")) {
						strKey = "미사질양토";
					}else if(strKey.equals("04")) {
						strKey = "미사질식양토";
					}else if(strKey.equals("05")) {
						strKey = "사질식양토";
					}else if(strKey.equals("06")) {
						strKey = "미사질식토";
					}else if(strKey.equals("07")) {
						strKey = "식양토";
					}else if(strKey.equals("08")) {
						strKey = "미사";
					}else if(strKey.equals("09")) {
						strKey = "양질사토";
					}else if(strKey.equals("10")) {
						strKey = "사토";
					}else if(strKey.equals("11")) {
						strKey = "점토";
					}else if(strKey.equals("99")) {
						strKey = "기타";
					}
				}else if(fieldName.equals("prrck_larg")) {
					if(strKey.equals("1")) {
						strKey = "화성암";
					}else if(strKey.equals("2")) {
						strKey = "퇴적암";
					}else if(strKey.equals("3")) {
						strKey = "변성암";
					}
				}else if(fieldName.equals("accma_for")) {
					if(strKey.equals("1")) {
						strKey = "잔적토";
					}else if(strKey.equals("2")) {
						strKey = "보행토(포행토)";
					}else if(strKey.equals("3")) {
						strKey = "봉적토";
					}
				}else if(fieldName.equals("rock_exdgr")) {
					if(strKey.equals("1")) {
						strKey = "10% 이하";
					}else if(strKey.equals("2")) {
						strKey = "10~30%";
					}else if(strKey.equals("3")) {
						strKey = "30~50%";
					}else if(strKey.equals("4")) {
						strKey = "50~75%";
					}
				}
				
				//기존 BigDecimal 계산버전
//				ratioResult.put(strKey, chkValue.setScale(1, RoundingMode.HALF_UP)+"%");
//				areaResult.put(strKey, areaValue.setScale(1, RoundingMode.HALF_UP)+"ha");
				
				// 신규 double 계산버전
				double convertChkValue = Math.round(chkValue*10)/10.0;
				double convertAreaValue = Math.round(areaValue*10)/10.0;
				
				ratioResult.put(strKey, Double.toString(convertChkValue)+"%");
				areaResult.put(strKey, Double.toString(convertAreaValue)+"ha");
				
				originAreaResult.put(strKey, strValue);
				
				if(maxValue < strValue) {
					results.put("최고", strKey);
					maxValue = strValue;
				}
				
			}
 			JSONObject ratioJsonObject = new JSONObject(ratioResult);
			JSONObject areaJsonObject = new JSONObject(areaResult);
			
			results.put("비율", ratioJsonObject);
			results.put("면적", areaJsonObject);
			results.put("면적(m2)", originAreaResult);
			//System.out.println(map.toString());
			//System.out.println(ratioJsonObject.toString());
			System.out.println(results.toString());
		}
		return results;
	}
	
	/**
	 * 벡터데이터셋 통계 컬럼
	 * @param caption
	 * @return
	 */
	private static String getUniqueExprNm(String caption) {
		String expr = null;
		if(caption.matches("koftr.*")) {
			expr = "koftr_nm";
		}else if(caption.matches("agcls.*")) {
			expr = "agcls_nm";
		}else if(caption.matches("dnst.*")) {
			expr = "dnst_nm";
		}else if(caption.matches("dmcls.*")) {
			expr = "dmcls_nm";
		}else if(caption.matches("nature.*")) {
			expr = "생태자연도";
		}else if(caption.matches("geological.*")) {
			expr = "구분";
		}else if(caption.matches("frtp.*")) {
			expr = "frtp_nm";
		}else if(caption.matches("fror.*")) {
			expr = "fror_nm";
		}else if(caption.matches("soil.*")) {
			expr = "sibflr_scs";
		}else if(caption.matches("geology.*")) {
			expr = "refrock";
		}else if(caption.matches("prrck.*")) {
			expr = "prrck_larg";
		}else if(caption.matches("accma.*")) {
			expr = "accma_for";
		}else if(caption.matches("rock.*")) {
			expr = "rock_exdgr";
		}
		
		return expr;
	}
	
	public static AnalFileVO createXYChartToImage(String type, XYDataset dataset,int upperBound, AnalFileVO vo) throws Exception{
		JFreeChart chart = null;
		
		AnalFileVO fileVo = new AnalFileVO();
		BeanUtils.copyProperties(vo,fileVo);
		
		String title = "";
		String xAxisLabel = "";
		String yAxisLabel = "";
		
		String strePathString = fileVo.getFileStreCours();
		String streFileNm = fileVo.getStreFileNm();
		String streFullPath = strePathString + File.separator + streFileNm;
		String saveFileNm = streFullPath.concat(".jpg");
		
		if(type.equals("line")) {
			chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset);
		}else if(type.equals("area")) {
			chart = ChartFactory.createXYAreaChart(title, xAxisLabel, yAxisLabel, dataset);
		}else if(type.equals("bar")) {
			//chart = ChartFactory.createXYBarChart(title, xAxisLabel,false, yAxisLabel, dataset);
		}else if(type.equals("steparea")) {
			chart = ChartFactory.createXYStepAreaChart(title, xAxisLabel, yAxisLabel, dataset);
		}else if(type.equals("step")) {
			chart = ChartFactory.createXYStepChart(title, xAxisLabel, yAxisLabel, dataset);
		}else if(type.equals("stackxyarea")) {
			//chart = ChartFactory.createStackedXYAreaChart(title, xAxisLabel, yAxisLabel, dataset);
		}
		
		if(chart != null) {
			/* 한글 처리 문제 */
			//차트 제목
			Font labelFont = chart.getTitle().getFont();
			chart.getTitle().setFont(new Font("굴림",labelFont.getStyle(),labelFont.getSize()));
			//CategoryPlot categoryPlot = chart.getCategoryPlot();
			XYPlot xyPlot = chart.getXYPlot();
			
			//렌더러
			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
			renderer.setSeriesPaint(0,new Color(0,176,240));
			renderer.setSeriesStroke(0,new BasicStroke(2.0f));
			xyPlot.setRenderer(renderer); 
			
			//X축 제목
			labelFont = xyPlot.getDomainAxis().getLabelFont();
			xyPlot.getDomainAxis().setLabelFont(new Font("돋움",labelFont.getStyle(),labelFont.getSize()));
			//X축 값에 대한 레이블
			labelFont = xyPlot.getDomainAxis().getTickLabelFont();
			xyPlot.getDomainAxis().setTickLabelFont(new Font("돋움",labelFont.getStyle(),labelFont.getSize()));
			//Y축 제목
			labelFont = xyPlot.getDomainAxis().getLabelFont();
			xyPlot.getRangeAxis().setLabelFont(new Font("돋움",labelFont.getStyle(),labelFont.getSize()));
			//Y축  값에 대한 레이블
			labelFont = xyPlot.getRangeAxis().getTickLabelFont();
			xyPlot.getRangeAxis().setTickLabelFont(new Font("돋움",labelFont.getStyle(),labelFont.getSize()));
			//범례
			chart.removeLegend();
			//chart.getLegend().setItemFont(new Font("돋움",Font.PLAIN,10));
			//x축 시작 값
			xyPlot.getDomainAxis().setLowerBound(0); // 0부터 시작
			xyPlot.getDomainAxis().setUpperBound(upperBound); // 종료값
			//String outputPath = "D://home/LineChartOutput_".concat(analId).concat(".jpg");
			
			try {
	            ChartUtils.saveChartAsJPEG(new File(saveFileNm), chart, 800, 600);
	            
				fileVo.setFileExtsn("jpg");//파일확장자
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		System.out.println(saveFileNm);
		return fileVo;
	}
	
	/**
	 * 지오메트리 생성
	 * @param geomStr
	 * @param geomType
	 * @return
	 * @throws Exception
	 * @Description point 좌표변환 확인해볼것....
	 */
	public static Geometry createGeometry(String geomStr,String geomType) throws Exception{
		Geometry geom = null;
		String geom_str = null;
		
		if(geomType.equals("POINT")) {
			geom_str = geomStr.replaceAll("POINT \\(|POINT\\(|\\)", "");
			
			String pt = geom_str;
			
			double x = Double.parseDouble(pt.split(" ")[0]);
			double y = Double.parseDouble(pt.split(" ")[1]);
			
			Point2D pt2d = new Point2D(x,y);
			
			//GeoPoint geoPt = new GeoPoint(pt2d);
			geom = new GeoPoint(pt2d);
		}else if(geomType.equals("POLYGON")) {
			geom_str = geomStr.replaceAll("POLYGON \\(\\(|POLYGON\\(\\(|\\)\\)\\)", "");
		}else if(geomType.equals("MULTIPOLYGON")) {
			geom_str = geomStr.replaceAll("MULTIPOLYGON \\(\\(\\(|MULTIPOLYGON\\(\\(\\(|\\)\\)\\)", "");
			String[] multiPolygons = geom_str.split("\\),\\(|\\), \\(");
			
			GeoRegion geoRegion = new GeoRegion();
			for (int i = 0; i < multiPolygons.length; i++) {
				String polygonStr = multiPolygons[i];//.replaceAll("\\(|\\)","");
				String[] multiPolygon = polygonStr.split(",");
				
				Point2Ds point2ds = new Point2Ds();
				
				for (int j = 0; j < multiPolygon.length; j++) {
					String polygon = multiPolygon[j].trim();
					
					double x = Double.parseDouble(polygon.split(" ")[0]);
					double y = Double.parseDouble(polygon.split(" ")[1]);
					
					point2ds.add(new Point2D(x, y));
				}
				geoRegion.addPart(point2ds);
			}
			
			geom = geoRegion;
		}else if(geomType.equals("POLYLINE")) {
			geom_str = geomStr.replaceAll("LINESTRING \\(|LINESTRING\\(|\\)", "");
			String[] multilines = geom_str.split(",");
			
			GeoLine geoLine = new GeoLine();
			Point2Ds point2ds = new Point2Ds();
			
			for (int i = 0; i < multilines.length; i++) {
				String line = multilines[i].trim();
				
				double x = Double.parseDouble(line.split(" ")[0]);
				double y = Double.parseDouble(line.split(" ")[1]);
				
				point2ds.add(new Point2D(x, y));
			}
			geoLine.addPart(point2ds);
			
			geom = geoLine;
		}
		
//		PrjCoordSys srcPrjCoordSys = PrjCoordSys.fromEPSG(epsg);
//		PrjCoordSys desPrjCoordSys = srcPrjCoordSys.getProjection() != null ? dataset.getPrjCoordSys() : PrjCoordSys.fromEPSG(5186);
//		
//		//printPrjCoordSys(dataset.getPrjCoordSys());
//		//if(!checkPrjCoordSys(desPrjCoordSys,srcPrjCoordSys)) {
//		if(epsg != 5186) {
//			boolean transCheck = CoordSysTranslator.convert(geom, srcPrjCoordSys, desPrjCoordSys, new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
//			if(!transCheck) {
//				LOGGER.error("좌표변환에 실패하였습니다.");
//				throw new Exception("좌표변환에 실패하였습니다.");
//			}
//		}
		
		return geom;
	}
	
	/**
	 * UniqeValue Theme Map 테마설정
	 * @param caption
	 * @param exprlist
	 * @return
	 */
	public static ThemeUnique getThemeUnique(String caption,LinkedHashSet<String> exprlist) {
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
			themeUnique.add(getItem(new Color(8,150,76), 100,"1","1등급",caption));
			themeUnique.add(getItem(new Color(149,199,138), 100,"2","2등급",caption));
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
		}else if(caption.matches("geological.*")) {
			for (String expr : exprlist) {
				if(expr.equals("1")) {
					themeUnique.add(getItem(new Color(155,187,89), 100,"1","화성암",caption));
				}else if(expr.equals("2")) {
					themeUnique.add(getItem(new Color(75,172,198), 100,"2","퇴적암",caption));
				}else if(expr.equals("3")) {
					themeUnique.add(getItem(new Color(192,80,77), 100,"3","변성암",caption));
				}
			}
		}else if(caption.matches("frd_.*")) {
			if(caption.indexOf("_frtp_") > 0) {	// 임상
				for (String expr : exprlist) {
					if(expr.equals("0")) {themeUnique.add(getItem(new Color(205,205,205), 100,"0","무립지/비산림",caption));
					}else if(expr.equals("1")) {themeUnique.add(getItem(new Color(35,123,0), 100,"1","침엽수림",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(66,211,0), 100,"2","활엽수림",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(96,142,86), 100,"3","혼효림",caption));
					}else if(expr.equals("4")) {themeUnique.add(getItem(new Color(247,240,200), 100,"4","죽림",caption));
					}
				}
			}else if(caption.indexOf("_fror_") > 0) { //임종
				for (String expr : exprlist) {
					if(expr.equals("0")) {themeUnique.add(getItem(new Color(205,205,205), 100,"0","무립지/비산림",caption));
					}else if(expr.equals("1")) {themeUnique.add(getItem(new Color(255,255,128), 100,"1","임공림",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(154,212,127), 100,"2","천연림",caption));
					}
				}
			}else if(caption.indexOf("_agcls_") > 0) { //영급
				for (String expr : exprlist) {
					if(expr.equals("1")) {themeUnique.add(getItem(new Color(53,130,53), 100,"1","1영급",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(63,140,63), 100,"2","2영급",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(73,150,73), 100,"3","3영급",caption));
					}else if(expr.equals("4")) {themeUnique.add(getItem(new Color(83,160,83), 100,"4","4영급",caption));
					}else if(expr.equals("5")) {themeUnique.add(getItem(new Color(93,170,93), 100,"5","5영급",caption));
					}else if(expr.equals("6")) {themeUnique.add(getItem(new Color(103,180,103), 100,"6","6영급",caption));
					}else if(expr.equals("7")) {themeUnique.add(getItem(new Color(129,193,129), 100,"7","7영급",caption));
					}else if(expr.equals("8")) {themeUnique.add(getItem(new Color(146,202,146), 100,"8","8영급",caption));
					}else if(expr.equals("9")) {themeUnique.add(getItem(new Color(173,215,173), 100,"9","9영급",caption));
					}
				}
			}else if(caption.indexOf("_dmcls_") > 0) { //경급
				for (String expr : exprlist) {
					if(expr.equals("0")) {themeUnique.add(getItem(new Color(154,212,127), 100,"0","치수",caption));
					}else if(expr.equals("1")) {themeUnique.add(getItem(new Color(216,240,128), 100,"1","소경목",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(254,212,128), 100,"2","중경목",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(254,128,129), 100,"3","대경목",caption));
					}
				}
			}else if(caption.indexOf("_dnst_") > 0) { //밀도
				for (String expr : exprlist) {
					if(expr.equals("A")) {themeUnique.add(getItem(new Color(232,255,185), 100,"A","소",caption));
					}else if(expr.equals("B")) {themeUnique.add(getItem(new Color(204,243,128), 100,"B","중",caption));
					}else if(expr.equals("C")) {themeUnique.add(getItem(new Color(166,185,129), 100,"C","밀",caption));
					}
				}
			}else if(caption.indexOf("_koftr_") > 0) { //수종
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
			}else if(caption.indexOf("_soil_") > 0) { //토성
				for (String expr : exprlist) {
					if(expr.equals("01")) {themeUnique.add(getItem(new Color(204,194,169), 100,"01","사양토",caption));
					}else if(expr.equals("02")) {themeUnique.add(getItem(new Color(242,227,184), 100,"02","양토",caption));
					}else if(expr.equals("03")) {themeUnique.add(getItem(new Color(242,209,194), 100,"03","미사질양토",caption));
					}else if(expr.equals("04")) {themeUnique.add(getItem(new Color(215,205,175), 100,"04","미사질식양토",caption));
					}else if(expr.equals("05")) {themeUnique.add(getItem(new Color(225,214,185), 100,"05","사질식양토",caption));
					}else if(expr.equals("06")) {themeUnique.add(getItem(new Color(235,223,195), 100,"06","미사질식토",caption));
					}else if(expr.equals("07")) {themeUnique.add(getItem(new Color(245,232,205), 100,"07","식양토",caption));
					}else if(expr.equals("08")) {themeUnique.add(getItem(new Color(255,241,215), 100,"08","미사",caption));
					}else if(expr.equals("09")) {themeUnique.add(getItem(new Color(220,230,175), 100,"09","양질사토",caption));
					}else if(expr.equals("10")) {themeUnique.add(getItem(new Color(230,240,185), 100,"10","사토",caption));
					}else if(expr.equals("11")) {themeUnique.add(getItem(new Color(240,250,195), 100,"11","점토",caption));
					}else if(expr.equals("99")) {themeUnique.add(getItem(new Color(250,255,205), 100,"99","기타",caption));
					}
				}
			}else if(caption.indexOf("_geology_") > 0) { //지질분포도
				//추후 정보 받으면 추가해야함
				for (String expr : exprlist) {
					if(expr.equals("각섬석화강암")) {themeUnique.add(getItem(new Color(48,8,82), 100,"각섬석화강암","각섬석화강암",caption));
					}else if(expr.equals("각섬암")) {themeUnique.add(getItem(new Color(159,157,184), 100,"각섬암","각섬암",caption));
					}else if(expr.equals("갈두층")) {themeUnique.add(getItem(new Color(164,31,83), 100,"갈두층","갈두층",caption));
					}else if(expr.equals("격포리층")) {themeUnique.add(getItem(new Color(64,129,204), 100,"격포리층","격포리층",caption));
					}else if(expr.equals("고성층")) {themeUnique.add(getItem(new Color(88,32,193), 100,"고성층","고성층",caption));
					}else if(expr.equals("규암")) {themeUnique.add(getItem(new Color(221,150,244), 100,"규암","규암",caption));
					}else if(expr.equals("길왕리층")) {themeUnique.add(getItem(new Color(121,51,146), 100,"길왕리층","길왕리층",caption));
					}else if(expr.equals("낙동층")) {themeUnique.add(getItem(new Color(20,39,241), 100,"낙동층","낙동층",caption));
					}else if(expr.equals("달길층")) {themeUnique.add(getItem(new Color(166,117,140), 100,"달길층","달길층",caption));
					}else if(expr.equals("덕용산규암")) {themeUnique.add(getItem(new Color(222,198,200), 100,"덕용산규암","덕용산규암",caption));
					}else if(expr.equals("두무진층")) {themeUnique.add(getItem(new Color(227,42,75), 100,"두무진층","두무진층",caption));
					}else if(expr.equals("두원층")) {themeUnique.add(getItem(new Color(185,238,99), 100,"두원층","두원층",caption));
					}else if(expr.equals("류문암,류문암질응회암")) {themeUnique.add(getItem(new Color(47,72,73), 100,"류문암,류문암질응회암","류문암,류문암질응회암",caption));
					}else if(expr.equals("마이산층")) {themeUnique.add(getItem(new Color(90,2,86), 100,"마이산층","마이산층",caption));
					}else if(expr.equals("만덕산층")) {themeUnique.add(getItem(new Color(175,134,153), 100,"만덕산층","만덕산층",caption));
					}else if(expr.equals("매립지")) {themeUnique.add(getItem(new Color(36,211,39), 100,"매립지","매립지",caption));
					}else if(expr.equals("맥암")) {themeUnique.add(getItem(new Color(207,143,11), 100,"맥암","맥암",caption));
					}else if(expr.equals("면수산규암")) {themeUnique.add(getItem(new Color(230,255,113), 100,"면수산규암","면수산규암",caption));
					}else if(expr.equals("문수산층")) {themeUnique.add(getItem(new Color(13,134,82), 100,"문수산층","문수산층",caption));
					}else if(expr.equals("미고결 퇴적층")) {themeUnique.add(getItem(new Color(148,132,207), 100,"미고결 퇴적층","미고결 퇴적층",caption));
					}else if(expr.equals("미그마타이트질 편마암")) {themeUnique.add(getItem(new Color(146,246,155), 100,"미그마타이트질 편마암","미그마타이트질 편마암",caption));
					}else if(expr.equals("미그마타이트질편마암")) {themeUnique.add(getItem(new Color(34,188,23), 100,"미그마타이트질편마암","미그마타이트질편마암",caption));
					}else if(expr.equals("박달령화강편마암")) {themeUnique.add(getItem(new Color(183,34,107), 100,"박달령화강편마암","박달령화강편마암",caption));
					}else if(expr.equals("반려암")) {themeUnique.add(getItem(new Color(82,157,146), 100,"반려암","반려암",caption));
					}else if(expr.equals("반상변성질편마암")) {themeUnique.add(getItem(new Color(170,243,54), 100,"반상변성질편마암","반상변성질편마암",caption));
					}else if(expr.equals("반상변성질편편마암")) {themeUnique.add(getItem(new Color(188,85,203), 100,"반상변성질편편마암","반상변성질편편마암",caption));
					}else if(expr.equals("반상변정질편마암")) {themeUnique.add(getItem(new Color(18,75,6), 100,"반상변정질편마암","반상변정질편마암",caption));
					}else if(expr.equals("반상화강암")) {themeUnique.add(getItem(new Color(39,202,224), 100,"반상화강암","반상화강암",caption));
					}else if(expr.equals("반송층군")) {themeUnique.add(getItem(new Color(5140109), 100,"반송층군","반송층군",caption));
					}else if(expr.equals("반송층군 대동층군")) {themeUnique.add(getItem(new Color(89,72,111), 100,"반송층군/대동층군","반송층군/대동층군",caption));
					}else if(expr.equals("반암류")) {themeUnique.add(getItem(new Color(48,80,236), 100,"반암류","반암류",caption));
					}else if(expr.equals("반휘암")) {themeUnique.add(getItem(new Color(253,116,225), 100,"반휘암","반휘암",caption));
					}else if(expr.equals("방림층군")) {themeUnique.add(getItem(new Color(111,172,107), 100,"방림층군","방림층군",caption));
					}else if(expr.equals("방이리층")) {themeUnique.add(getItem(new Color(155,188,204), 100,"방이리층","방이리층",caption));
					}else if(expr.equals("변성사질암")) {themeUnique.add(getItem(new Color(76,206,147), 100,"변성사질암","변성사질암",caption));
					}else if(expr.equals("변성사질암대")) {themeUnique.add(getItem(new Color(32,158,140), 100,"변성사질암대","변성사질암대",caption));
					}else if(expr.equals("변성사질암류")) {themeUnique.add(getItem(new Color(146,28,4), 100,"변성사질암류","변성사질암류",caption));
					}else if(expr.equals("변성석영반암")) {themeUnique.add(getItem(new Color(101,105,233), 100,"변성석영반암","변성석영반암",caption));
					}else if(expr.equals("변성화산암류")) {themeUnique.add(getItem(new Color(71,8,174), 100,"변성화산암류","변성화산암류",caption));
					}else if(expr.equals("부산반상질화강편마암")) {themeUnique.add(getItem(new Color(75,49,0), 100,"부산반상질화강편마암","부산반상질화강편마암",caption));
					}else if(expr.equals("북평층군")) {themeUnique.add(getItem(new Color(191,112,189), 100,"북평층군","북평층군",caption));
					}else if(expr.equals("분천 화강편마암")) {themeUnique.add(getItem(new Color(229,179,22), 100,"분천 화강편마암","분천 화강편마암",caption));
					}else if(expr.equals("사문암")) {themeUnique.add(getItem(new Color(1,55,162), 100,"사문암","사문암",caption));
					}else if(expr.equals("사암 및 이암")) {themeUnique.add(getItem(new Color(140,239,178), 100,"사암 및 이암","사암 및 이암",caption));
					}else if(expr.equals("사암및셰일")) {themeUnique.add(getItem(new Color(114,52,10), 100,"사암및셰일","사암및셰일",caption));
					}else if(expr.equals("사암및응회암")) {themeUnique.add(getItem(new Color(81,60,201), 100,"사암및응회암","사암및응회암",caption));
					}else if(expr.equals("사암및이암")) {themeUnique.add(getItem(new Color(216,15,116), 100,"사암및이암","사암및이암",caption));
					}else if(expr.equals("산성맥암류")) {themeUnique.add(getItem(new Color(73,246,109), 100,"산성맥암류","산성맥암류",caption));
					}else if(expr.equals("산성반암")) {themeUnique.add(getItem(new Color(85,241,40), 100,"산성반암","산성반암",caption));
					}else if(expr.equals("산성암")) {themeUnique.add(getItem(new Color(144,99,207), 100,"산성암","산성암",caption));
					}else if(expr.equals("산성암맥")) {themeUnique.add(getItem(new Color(51,89,176), 100,"산성암맥","산성암맥",caption));
					}else if(expr.equals("산성화강암류")) {themeUnique.add(getItem(new Color(206,74,183), 100,"산성화강암류","산성화강암류",caption));
					}else if(expr.equals("산성화산암")) {themeUnique.add(getItem(new Color(206,105,106), 100,"산성화산암","산성화산암",caption));
					}else if(expr.equals("산성화산암류")) {themeUnique.add(getItem(new Color(182,45,173), 100,"산성화산암류","산성화산암류",caption));
					}else if(expr.equals("산수동층")) {themeUnique.add(getItem(new Color(53,141,166), 100,"산수동층","산수동층",caption));
					}else if(expr.equals("상부남포층군(성주리층)")) {themeUnique.add(getItem(new Color(255,189,57), 100,"상부남포층군(성주리층)","상부남포층군(성주리층)",caption));
					}else if(expr.equals("상부대석회암층군")) {themeUnique.add(getItem(new Color(41,85,184), 100,"상부대석회암층군","상부대석회암층군",caption));
					}else if(expr.equals("상부연일층군")) {themeUnique.add(getItem(new Color(143,112,119), 100,"상부연일층군","상부연일층군",caption));
					}else if(expr.equals("상부천매암")) {themeUnique.add(getItem(new Color(143,164,82), 100,"상부천매암","상부천매암",caption));
					}else if(expr.equals("상부천매암대")) {themeUnique.add(getItem(new Color(254,156,39), 100,"상부천매암대","상부천매암대",caption));
					}else if(expr.equals("상부평안층군")) {themeUnique.add(getItem(new Color(215,167,97), 100,"상부평안층군","상부평안층군",caption));
					}else if(expr.equals("서귀포층")) {themeUnique.add(getItem(new Color(21,209,42), 100,"서귀포층","서귀포층",caption));
					}else if(expr.equals("석문층")) {themeUnique.add(getItem(new Color(79,234,80), 100,"석문층","석문층",caption));
					}else if(expr.equals("석영반암")) {themeUnique.add(getItem(new Color(0,135,219), 100,"석영반암","석영반암",caption));
					}else if(expr.equals("석회암")) {themeUnique.add(getItem(new Color(146,189,228), 100,"석회암","석회암",caption));
					}else if(expr.equals("석회암대")) {themeUnique.add(getItem(new Color(26,200,189), 100,"석회암대","석회암대",caption));
					}else if(expr.equals("선소층")) {themeUnique.add(getItem(new Color(222,178,112), 100,"선소층","선소층",caption));
					}else if(expr.equals("설옥리층")) {themeUnique.add(getItem(new Color(193,22,0), 100,"설옥리층","설옥리층",caption));
					}else if(expr.equals("섬록암")) {themeUnique.add(getItem(new Color(22,19,183), 100,"섬록암","섬록암",caption));
					}else if(expr.equals("섬장암")) {themeUnique.add(getItem(new Color(174,22,180), 100,"섬장암","섬장암",caption));
					}else if(expr.equals("세립질화강암")) {themeUnique.add(getItem(new Color(134,213,160), 100,"세립질화강암","세립질화강암",caption));
					}else if(expr.equals("신라력암")) {themeUnique.add(getItem(new Color(233,25,231), 100,"신라력암","신라력암",caption));
					}else if(expr.equals("신라역암")) {themeUnique.add(getItem(new Color(86,175,63), 100,"신라역암","신라역암",caption));
					}else if(expr.equals("신성리층")) {themeUnique.add(getItem(new Color(72,53,77), 100,"신성리층","신성리층",caption));
					}else if(expr.equals("안구장편마암")) {themeUnique.add(getItem(new Color(151,181,116), 100,"안구장편마암","안구장편마암",caption));
					}else if(expr.equals("안산암")) {themeUnique.add(getItem(new Color(229,57,184), 100,"안산암","안산암",caption));
					}else if(expr.equals("안산암,안산암질응회암")) {themeUnique.add(getItem(new Color(183,65,78), 100,"안산암,안산암질응회암","안산암,안산암질응회암",caption));
					}else if(expr.equals("안산암및안산암질응회암")) {themeUnique.add(getItem(new Color(172,225,11), 100,"안산암및안산암질응회암","안산암및안산암질응회암",caption));
					}else if(expr.equals("양덕층군")) {themeUnique.add(getItem(new Color(187,152,231), 100,"양덕층군","양덕층군",caption));
					}else if(expr.equals("양평화성복합체")) {themeUnique.add(getItem(new Color(209,166,246), 100,"양평화성복합체","양평화성복합체",caption));
					}else if(expr.equals("어일층군")) {themeUnique.add(getItem(new Color(69,217,118), 100,"어일층군","어일층군",caption));
					}else if(expr.equals("역암")) {themeUnique.add(getItem(new Color(131,209,115), 100,"역암","역암",caption));
					}else if(expr.equals("역암 및 사암")) {themeUnique.add(getItem(new Color(129,45,253), 100,"역암 및 사암","역암 및 사암",caption));
					}else if(expr.equals("역질사암")) {themeUnique.add(getItem(new Color(162,212,243), 100,"역질사암","역질사암",caption));
					}else if(expr.equals("염기성화산암")) {themeUnique.add(getItem(new Color(75,137,41), 100,"염기성화산암","염기성화산암",caption));
					}else if(expr.equals("엽리상화강암")) {themeUnique.add(getItem(new Color(207,42,30), 100,"엽리상화강암","엽리상화강암",caption));
					}else if(expr.equals("엽리상화강암류")) {themeUnique.add(getItem(new Color(117,55,229), 100,"엽리상화강암류","엽리상화강암류",caption));
					}else if(expr.equals("영월형 조선누층군")) {themeUnique.add(getItem(new Color(13,61,117), 100,"영월형 조선누층군","영월형 조선누층군",caption));
					}else if(expr.equals("용암산층")) {themeUnique.add(getItem(new Color(51,122,24), 100,"용암산층","용암산층",caption));
					}else if(expr.equals("우백질편마암")) {themeUnique.add(getItem(new Color(26,171,117), 100,"우백질편마암","우백질편마암",caption));
					}else if(expr.equals("우백질화강암")) {themeUnique.add(getItem(new Color(75,141,96), 100,"우백질화강암","우백질화강암",caption));
					}else if(expr.equals("우항리층")) {themeUnique.add(getItem(new Color(32,42,133), 100,"우항리층","우항리층",caption));
					}else if(expr.equals("원남층군")) {themeUnique.add(getItem(new Color(231,89,92), 100,"원남층군","원남층군",caption));
					}else if(expr.equals("유문암및유문암질응회암")) {themeUnique.add(getItem(new Color(100,206,200), 100,"유문암및유문암질응회암","유문암및유문암질응회암",caption));
					}else if(expr.equals("유치역암")) {themeUnique.add(getItem(new Color(193,217,198), 100,"유치역암","유치역암",caption));
					}else if(expr.equals("율리층군")) {themeUnique.add(getItem(new Color(206,254,41), 100,"율리층군","율리층군",caption));
					}else if(expr.equals("응회암")) {themeUnique.add(getItem(new Color(77,138,199), 100,"응회암","응회암",caption));
					}else if(expr.equals("장기층군")) {themeUnique.add(getItem(new Color(43,105,104), 100,"장기층군","장기층군",caption));
					}else if(expr.equals("장동층")) {themeUnique.add(getItem(new Color(160,119,85), 100,"장동층","장동층",caption));
					}else if(expr.equals("장목리층")) {themeUnique.add(getItem(new Color(239,136,26), 100,"장목리층","장목리층",caption));
					}else if(expr.equals("장촌층")) {themeUnique.add(getItem(new Color(202,73,166), 100,"장촌층","장촌층",caption));
					}else if(expr.equals("장평리층")) {themeUnique.add(getItem(new Color(200,130,12), 100,"장평리층","장평리층",caption));
					}else if(expr.equals("적상산층")) {themeUnique.add(getItem(new Color(60,251,128), 100,"적상산층","적상산층",caption));
					}else if(expr.equals("적성층")) {themeUnique.add(getItem(new Color(105,214,87), 100,"적성층","적성층",caption));
					}else if(expr.equals("정선형 조선누층군")) {themeUnique.add(getItem(new Color(151,5,187), 100,"정선형 조선누층군","정선형 조선누층군",caption));
					}else if(expr.equals("조면안산암")) {themeUnique.add(getItem(new Color(244,58,202), 100,"조면안산암","조면안산암",caption));
					}else if(expr.equals("조면안산암 분석구")) {themeUnique.add(getItem(new Color(252,173,120), 100,"조면안산암 분석구","조면안산암 분석구",caption));
					}else if(expr.equals("조면암")) {themeUnique.add(getItem(new Color(244,103,158), 100,"조면암","조면암",caption));
					}else if(expr.equals("조면암 및 조면안산암")) {themeUnique.add(getItem(new Color(84,152,143), 100,"조면암 및 조면안산암","조면암 및 조면안산암",caption));
					}else if(expr.equals("조면암 및 현무암")) {themeUnique.add(getItem(new Color(231,78,46), 100,"조면암 및 현무암","조면암 및 현무암",caption));
					}else if(expr.equals("조면암 분석구")) {themeUnique.add(getItem(new Color(209,3,11), 100,"조면암 분석구","조면암 분석구",caption));
					}else if(expr.equals("조면현무암(I)")) {themeUnique.add(getItem(new Color(112,55,5), 100,"조면현무암(I)","조면현무암(I)",caption));
					}else if(expr.equals("조면현무암(I) 응회암")) {themeUnique.add(getItem(new Color(77,5,64), 100,"조면현무암(I) 응회암","조면현무암(I) 응회암",caption));
					}else if(expr.equals("조면현무암(II)")) {themeUnique.add(getItem(new Color(51,96,33), 100,"조면현무암(II)","조면현무암(II)",caption));
					}else if(expr.equals("조면현무암(II) 분석구")) {themeUnique.add(getItem(new Color(122,195,1), 100,"조면현무암(II) 분석구","조면현무암(II) 분석구",caption));
					}else if(expr.equals("조면현무암(III)")) {themeUnique.add(getItem(new Color(79,6,237), 100,"조면현무암(III)","조면현무암(III)",caption));
					}else if(expr.equals("조면현무암(III) 분석구")) {themeUnique.add(getItem(new Color(194,137,92), 100,"조면현무암(III) 분석구","조면현무암(III) 분석구",caption));
					}else if(expr.equals("조면현무암(IV)")) {themeUnique.add(getItem(new Color(103,232,199), 100,"조면현무암(IV)","조면현무암(IV)",caption));
					}else if(expr.equals("조면현무암(V)")) {themeUnique.add(getItem(new Color(201,234,19), 100,"조면현무암(V)","조면현무암(V)",caption));
					}else if(expr.equals("조면현무암(V) 분석구")) {themeUnique.add(getItem(new Color(195,26,237), 100,"조면현무암(V) 분석구","조면현무암(V) 분석구",caption));
					}else if(expr.equals("조면현무암(VI)")) {themeUnique.add(getItem(new Color(177,245,162), 100,"조면현무암(VI)","조면현무암(VI)",caption));
					}else if(expr.equals("조면현무암(VI) 분석구")) {themeUnique.add(getItem(new Color(209,6,162), 100,"조면현무암(VI) 분석구","조면현무암(VI) 분석구",caption));
					}else if(expr.equals("조면현무암(VII)")) {themeUnique.add(getItem(new Color(16,65,88), 100,"조면현무암(VII)","조면현무암(VII)",caption));
					}else if(expr.equals("조면현무암(VII) 분석구")) {themeUnique.add(getItem(new Color(208,153,85), 100,"조면현무암(VII) 분석구","조면현무암(VII) 분석구",caption));
					}else if(expr.equals("조면현무암(VIII)")) {themeUnique.add(getItem(new Color(180,184,117), 100,"조면현무암(VIII)","조면현무암(VIII)",caption));
					}else if(expr.equals("조면현무암(VIII) 분석구")) {themeUnique.add(getItem(new Color(1,67,136), 100,"조면현무암(VIII) 분석구","조면현무암(VIII) 분석구",caption));
					}else if(expr.equals("조면현무암(VIII) 응회암")) {themeUnique.add(getItem(new Color(175,223,50), 100,"조면현무암(VIII) 응회암","조면현무암(VIII) 응회암",caption));
					}else if(expr.equals("중봉산 화강편마암")) {themeUnique.add(getItem(new Color(401,121,21), 100,"중봉산 화강편마암","중봉산 화강편마암",caption));
					}else if(expr.equals("중부공주층군")) {themeUnique.add(getItem(new Color(30,221,43), 100,"중부공주층군","중부공주층군",caption));
					}else if(expr.equals("중부남포층군(백운사층,초계리층)")) {themeUnique.add(getItem(new Color(213,24,170), 100,"중부남포층군(백운사층,초계리층)","중부남포층군(백운사층,초계리층)",caption));
					}else if(expr.equals("중부대석회암층군")) {themeUnique.add(getItem(new Color(192,99,195), 100,"중부대석회암층군","중부대석회암층군",caption));
					}else if(expr.equals("중부연일층군")) {themeUnique.add(getItem(new Color(228,140,199), 100,"중부연일층군","중부연일층군",caption));
					}else if(expr.equals("중부초평층군")) {themeUnique.add(getItem(new Color(81,89,180), 100,"중부초평층군","중부초평층군",caption));
					}else if(expr.equals("중부평안층군")) {themeUnique.add(getItem(new Color(75,179,239), 100,"중부평안층군","중부평안층군",caption));
					}else if(expr.equals("중성,염기성반암")) {themeUnique.add(getItem(new Color(151,235,79), 100,"중성,염기성반암","중성,염기성반암",caption));
					}else if(expr.equals("중성및염기성화산암")) {themeUnique.add(getItem(new Color(176,78,223), 100,"중성및염기성화산암","중성및염기성화산암",caption));
					}else if(expr.equals("중성및염기성화산암류")) {themeUnique.add(getItem(new Color(72,28,122), 100,"중성및염기성화산암류","중성및염기성화산암류",caption));
					}else if(expr.equals("중성암")) {themeUnique.add(getItem(new Color(251,139,107), 100,"중성암","중성암",caption));
					}else if(expr.equals("중성암맥")) {themeUnique.add(getItem(new Color(46,144,40), 100,"중성암맥","중성암맥",caption));
					}else if(expr.equals("중화동층")) {themeUnique.add(getItem(new Color(241,157,66), 100,"중화동층","중화동층",caption));
					}else if(expr.equals("진동층")) {themeUnique.add(getItem(new Color(63,43,103), 100,"진동층","진동층",caption));
					}else if(expr.equals("진주층")) {themeUnique.add(getItem(new Color(69,198,167), 100,"진주층","진주층",caption));
					}else if(expr.equals("청산화강암")) {themeUnique.add(getItem(new Color(147,86,5), 100,"청산화강암","청산화강암",caption));
					}else if(expr.equals("충적층")) {themeUnique.add(getItem(new Color(142,072,27), 100,"충적층","충적층",caption));
					}else if(expr.equals("칠곡층")) {themeUnique.add(getItem(new Color(55,90,187), 100,"칠곡층","칠곡층",caption));
					}else if(expr.equals("태안층군")) {themeUnique.add(getItem(new Color(223,147,27), 100,"태안층군","태안층군",caption));
					}else if(expr.equals("통진층")) {themeUnique.add(getItem(new Color(47,240,71), 100,"통진층","통진층",caption));
					}else if(expr.equals("퇴적암")) {themeUnique.add(getItem(new Color(41,244,166), 100,"퇴적암","퇴적암",caption));
					}else if(expr.equals("퇴적암 및 화산암")) {themeUnique.add(getItem(new Color(134,249,134), 100,"퇴적암 및 화산암","퇴적암 및 화산암",caption));
					}else if(expr.equals("퇴적층")) {themeUnique.add(getItem(new Color(195,5,144), 100,"퇴적층","퇴적층",caption));
					}else if(expr.equals("편마암류")) {themeUnique.add(getItem(new Color(18,22,38), 100,"편마암류","편마암류",caption));
					}else if(expr.equals("편암")) {themeUnique.add(getItem(new Color(250,77,197), 100,"편암","편암",caption));
					}else if(expr.equals("편암류")) {themeUnique.add(getItem(new Color(199,90,78), 100,"편암류","편암류",caption));
					}else if(expr.equals("평창형 조선누층군")) {themeUnique.add(getItem(new Color(91,237,146), 100,"평창형 조선누층군","평창형 조선누층군",caption));
					}else if(expr.equals("평해층군")) {themeUnique.add(getItem(new Color(181,36,15), 100,"평해층군","평해층군",caption));
					}else if(expr.equals("하부공주층군")) {themeUnique.add(getItem(new Color(107,20,103), 100,"하부공주층군","하부공주층군",caption));
					}else if(expr.equals("하부남포층군(아미산층,하조층)")) {themeUnique.add(getItem(new Color(83,78,205), 100,"하부남포층군(아미산층,하조층)","하부남포층군(아미산층,하조층)",caption));
					}else if(expr.equals("하부대석회암층군")) {themeUnique.add(getItem(new Color(252,39,138), 100,"하부대석회암층군","하부대석회암층군",caption));
					}else if(expr.equals("하부연일층군")) {themeUnique.add(getItem(new Color(138,43,90), 100,"하부연일층군","하부연일층군",caption));
					}else if(expr.equals("하부천매암")) {themeUnique.add(getItem(new Color(48,118,145), 100,"하부천매암","하부천매암",caption));
					}else if(expr.equals("하부천매암대")) {themeUnique.add(getItem(new Color(120,81,136), 100,"하부천매암대","하부천매암대",caption));
					}else if(expr.equals("하부초평층군")) {themeUnique.add(getItem(new Color(154,110,135), 100,"하부초평층군","하부초평층군",caption));
					}else if(expr.equals("하부평안층군")) {themeUnique.add(getItem(new Color(175,77,121), 100,"하부평안층군","하부평안층군",caption));
					}else if(expr.equals("하산동층")) {themeUnique.add(getItem(new Color(145,168,104), 100,"하산동층","하산동층",caption));
					}else if(expr.equals("함력천매암")) {themeUnique.add(getItem(new Color(251,84,170), 100,"함력천매암","함력천매암",caption));
					}else if(expr.equals("함력천매암대")) {themeUnique.add(getItem(new Color(222,222,228), 100,"함력천매암대","함력천매암대",caption));
					}else if(expr.equals("함석류석 화강편마암")) {themeUnique.add(getItem(new Color(154,169,114), 100,"함석류석 화강편마암","함석류석 화강편마암",caption));
					}else if(expr.equals("함석류석화강편마암")) {themeUnique.add(getItem(new Color(233,5,71), 100,"함석류석화강편마암","함석류석화강편마암",caption));
					}else if(expr.equals("함안층")) {themeUnique.add(getItem(new Color(141,75,146), 100,"함안층","함안층",caption));
					}else if(expr.equals("함역천매암대")) {themeUnique.add(getItem(new Color(167,222,104), 100,"함역천매암대","함역천매암대",caption));
					}else if(expr.equals("현무암")) {themeUnique.add(getItem(new Color(34,19,93), 100,"현무암","현무암",caption));
					}else if(expr.equals("현무암(I)")) {themeUnique.add(getItem(new Color(146,116,218), 100,"현무암(I)","현무암(I)",caption));
					}else if(expr.equals("현무암(I) 응회암")) {themeUnique.add(getItem(new Color(200,193,173), 100,"현무암(I) 응회암","현무암(I) 응회암",caption));
					}else if(expr.equals("현무암(II)")) {themeUnique.add(getItem(new Color(95,86,99), 100,"현무암(II)","현무암(II)",caption));
					}else if(expr.equals("현무암(II) 분석구")) {themeUnique.add(getItem(new Color(200,116,92), 100,"현무암(II) 분석구","현무암(II) 분석구",caption));
					}else if(expr.equals("현무암(III)")) {themeUnique.add(getItem(new Color(150,185,215), 100,"현무암(III)","현무암(III)",caption));
					}else if(expr.equals("현무암(III) 분석구")) {themeUnique.add(getItem(new Color(75,34,70), 100,"현무암(III) 분석구","현무암(III) 분석구",caption));
					}else if(expr.equals("호상편마암")) {themeUnique.add(getItem(new Color(228,254,95), 100,"호상편마암","호상편마암",caption));
					}else if(expr.equals("홍도층")) {themeUnique.add(getItem(new Color(254,31,212), 100,"홍도층","홍도층",caption));
					}else if(expr.equals("홍제사 화강암")) {themeUnique.add(getItem(new Color(65,226,225), 100,"홍제사 화강암","홍제사 화강암",caption));
					}else if(expr.equals("화강반암")) {themeUnique.add(getItem(new Color(79,32,33), 100,"화강반암","화강반암",caption));
					}else if(expr.equals("화강섬록암")) {themeUnique.add(getItem(new Color(202,215,39), 100,"화강섬록암","화강섬록암",caption));
					}else if(expr.equals("화강암")) {themeUnique.add(getItem(new Color(103,133,79), 100,"화강암","화강암",caption));
					}else if(expr.equals("화강암류")) {themeUnique.add(getItem(new Color(40,49,31), 100,"화강암류","화강암류",caption));
					}else if(expr.equals("화강암질편마암")) {themeUnique.add(getItem(new Color(55,227,27), 100,"화강암질편마암","화강암질편마암",caption));
					}else if(expr.equals("화강편마암")) {themeUnique.add(getItem(new Color(98,89,95), 100,"화강편마암","화강편마암",caption));
					}else if(expr.equals("화산암류")) {themeUnique.add(getItem(new Color(191,21,16), 100,"화산암류","화산암류",caption));
					}else if(expr.equals("회장암")) {themeUnique.add(getItem(new Color(227,237,233), 100,"회장암","회장암",caption));
					}else if(expr.equals("휘록암")) {themeUnique.add(getItem(new Color(55,175,122), 100,"휘록암","휘록암",caption));
					}else if(expr.equals("흑운모편마암")) {themeUnique.add(getItem(new Color(34,240,190), 100,"흑운모편마암","흑운모편마암",caption));
					}else if(expr.equals("흑운모화강암")) {themeUnique.add(getItem(new Color(148,213,207), 100,"흑운모화강암","흑운모화강암",caption));
					}else if(expr.equals("흙,모래,자갈")) {themeUnique.add(getItem(new Color(191,5,58), 100,"흙,모래,자갈","흙,모래,자갈",caption));
					}
				}
			}else if(caption.indexOf("_prrck_") > 0) { //모암분포도
				for (String expr : exprlist) {
					if(expr.equals("1")) {themeUnique.add(getItem(new Color(155,187,89), 100,"1","화성암",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(75,172,198), 100,"2","퇴적암",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(192,80,77), 100,"3","변성암",caption));
					}
				}
			}else if(caption.indexOf("_accma_") > 0) { //퇴적양식분포도
				for (String expr : exprlist) {
					if(expr.equals("1")) {themeUnique.add(getItem(new Color(242,209,190), 100,"1","잔적토",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(206,178,167), 100,"2","보행토(포행토)",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(240,222,182), 100,"3","봉적토",caption));
					}
				}
			}else if(caption.indexOf("_rock_") > 0) { //암석노출도
				for (String expr : exprlist) {
					if(expr.equals("1")) {themeUnique.add(getItem(new Color(154,212,127), 100,"1","10% 이하",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(124,182,97), 100,"2","10~30%",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(94,152,67), 100,"3","30~50%",caption));
					}else if(expr.equals("4")) {themeUnique.add(getItem(new Color(64,122,37), 100,"4","50~75%",caption));
					}
				}
			}else if(caption.indexOf("_nature_") > 0) {//생태자연도
				for (String expr : exprlist) {
					if(expr.equals("1")) {themeUnique.add(getItem(new Color(47,152,47), 100,"1","1등급",caption));
					}else if(expr.equals("2")) {themeUnique.add(getItem(new Color(207,227,201), 100,"2","2등급",caption));
					}else if(expr.equals("3")) {themeUnique.add(getItem(new Color(255,255,255), 100,"3","3등급",caption));
					}
				}
			}
		}
		
		return themeUnique;
	}
	
	private static ThemeUniqueItem getItem(Color color, int opaqueRate, String value, String caption, String type) {
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
			geostyle.setMarkerSize(new Size2D(7, 7));
		}else {
			geostyle.setFillForeColor(color);
			geostyle.setFillOpaqueRate(opaqueRate);
			geostyle.setLineSymbolID(5);
		}

		item.setStyle(geostyle);

		return item;
	}
	
	public static String getUniqueExpr(String caption) {
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
		}else if(caption.matches("geological.*")) {
			expr = "분류_2";
		}else if(caption.matches("frd.*")) {//임도전용
			if(caption.indexOf("_frtp_") > 0) {//임상분포도
				expr = "frtp_cd";
			}else if(caption.indexOf("_fror_") > 0) {//임종분포도
				expr = "fror_cd";
			}else if(caption.indexOf("_agcls_") > 0) {//영급분포도
				expr = "agcls_cd";
			}else if(caption.indexOf("_dmcls_") > 0) {//경급분포도
				expr = "dmcls_cd";
			}else if(caption.indexOf("_dnst_") > 0) {//밀도분포도
				expr = "dnst_cd";
			}else if(caption.indexOf("_koftr_") > 0) {//수종분포도
				expr = "koftr_grou";
			}else if(caption.indexOf("_soil_") > 0) {//토성분포도
				expr = "sibflr_scs";
			}else if(caption.indexOf("_geology_") > 0) {//지질분포도
				expr = "refrock";
			}else if(caption.indexOf("_prrck_") > 0) {//모암분포도
				expr = "prrck_larg";
			}else if(caption.indexOf("_accma_") > 0) {//퇴적양식분포도
				expr = "accma_for";
			}else if(caption.indexOf("_rock_") > 0) {//암석노출도
				expr = "rock_exdgr";
			}else if(caption.indexOf("_nature_") > 0) {//생태자연도
				expr = "생태자연도";
			}
		}
		
		return expr;
	}
	
	public static ThemeGridRange getThemeGridRange(String caption) {
		ThemeGridRange themeGridRange = new ThemeGridRange();
		//themeGridRange.setSpecialValue(-99999);
		themeGridRange.setSpecialValueTransparent(true);
		if(caption.matches("aspect.*")) {
			themeGridRange.addToTail(new ThemeGridRangeItem(-1, 0, new Color(176,176,178), "평지"));
			themeGridRange.addToTail(new ThemeGridRangeItem(0, 22.5, new Color(250,18,20), "북"));
			themeGridRange.addToTail(new ThemeGridRangeItem(22.5, 67.5, new Color(252,170,18), "북동"));
			themeGridRange.addToTail(new ThemeGridRangeItem(67.5, 112.5, new Color(252,255,19), "동"));
			themeGridRange.addToTail(new ThemeGridRangeItem(112.5, 157.5, new Color(6,255,2), "남동"));
			themeGridRange.addToTail(new ThemeGridRangeItem(157.5, 202.5, new Color(43,255,249), "남"));
			themeGridRange.addToTail(new ThemeGridRangeItem(202.5, 247.5, new Color(46,157,254), "남서"));
			themeGridRange.addToTail(new ThemeGridRangeItem(247.5, 292.5, new Color(42,0,247), "서"));
			themeGridRange.addToTail(new ThemeGridRangeItem(292.5, 337.5, new Color(250,0,254), "북서"));
			themeGridRange.addToTail(new ThemeGridRangeItem(337.5, 360, new Color(250,18,20), "북"));
		}else if(caption.matches("slope.*")) {
			themeGridRange.addToTail(new ThemeGridRangeItem(Double.MIN_VALUE, 10, new Color(56,167,0), "10도 미만"));
			themeGridRange.addToTail(new ThemeGridRangeItem(10, 15, new Color(102,191,1), "10~15도"));
			themeGridRange.addToTail(new ThemeGridRangeItem(15, 20, new Color(156,217,0), "15~20도"));
			themeGridRange.addToTail(new ThemeGridRangeItem(20, 25, new Color(222,241,0), "20~25도"));
			themeGridRange.addToTail(new ThemeGridRangeItem(25, 30, new Color(255,221,0), "25~30도"));
			themeGridRange.addToTail(new ThemeGridRangeItem(30, 35, new Color(255,144,0), "30~35도"));
			themeGridRange.addToTail(new ThemeGridRangeItem(35, 40, new Color(255,73,0), "35~40도"));
			themeGridRange.addToTail(new ThemeGridRangeItem(40, Double.MAX_VALUE, new Color(254,0,0), "40도 이상"));
		}else if(caption.matches("dem.*")) {
			themeGridRange.addToTail(new ThemeGridRangeItem(0, 100, new Color(255,255,128), "0~100m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(100, 200, new Color(255,241,116), "100~200m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(200, 300, new Color(252,224,98), "200~300m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(300, 400, new Color(250,209,85), "300~400m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(400, 500, new Color(247,195,73), "400~500m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(500, 600, new Color(245,180,60), "500~600m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(600, 700, new Color(242,168,47), "600~700m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(700, 800, new Color(219,137,37), "700~800m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(800, 900, new Color(196,110,27), "800~900m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(900, 1000, new Color(173,83,20), "900~1000m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(1000, 1100, new Color(150,58,11), "1000~1100m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(1100, 1200, new Color(129,35,7), "1100~1200m"));
			themeGridRange.addToTail(new ThemeGridRangeItem(1200, Double.MAX_VALUE, new Color(108,6,1), "1200m 이상"));
		}else if(caption.matches("landslide.*")) {
			themeGridRange.addToTail(new ThemeGridRangeItem(Double.MIN_VALUE, 1, new Color(254,0,0), "1등급"));
			themeGridRange.addToTail(new ThemeGridRangeItem(1, 2, new Color(255,200,1), "2등급"));
			themeGridRange.addToTail(new ThemeGridRangeItem(2, 3, new Color(182,254,142), "3등급"));
			themeGridRange.addToTail(new ThemeGridRangeItem(3, 4, new Color(51,195,255), "4등급"));
			themeGridRange.addToTail(new ThemeGridRangeItem(4, Double.MAX_VALUE, new Color(0,1,255), "5등급"));
			
			
//			themeGridRange.addToTail(new ThemeGridRangeItem(Double.MIN_VALUE,1.12903225806452, new Color(170,240,233), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.12903225806452, 1.25806451612903, new Color(176,242,211), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.25806451612903, 1.38709677419355, new Color(176,244,188), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.38709677419355, 1.51612903225806, new Color(191,248,178), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.51612903225806, 1.64516129032258, new Color(218,251,178), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.64516129032258, 1.7741935483871, new Color(237,249,173), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.7741935483871 , 1.90322580645161, new Color(220,236,145), "1등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(1.90322580645161, 2.03225806451613, new Color(160,213,103), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.03225806451613, 2.16129032258064, new Color(96,189,66), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.16129032258064, 2.29032258064516, new Color(44,164,50), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.29032258064516, 2.41935483870968, new Color(20,142,53), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.41935483870968, 2.54838709677419, new Color(32,133,61), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.54838709677419, 2.67741935483871, new Color(85,144,55), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.67741935483871, 2.80645161290322, new Color(129,157,47), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.80645161290322, 2.93548387096774, new Color(171,168,38), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(2.93548387096774, 3.06451612903226, new Color(215,177,23), "2등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.06451612903226, 3.19354838709677, new Color(235,165,9), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.19354838709677, 3.32258064516129, new Color(225,131,2), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.32258064516129, 3.4516129032258, new Color(203,89,2), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.4516129032258 , 3.58064516129032, new Color(197,54,2), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.58064516129032, 3.70967741935484, new Color(157,25,1), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.70967741935484, 3.83870967741935, new Color(130,12,1), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.83870967741935, 3.96774193548387, new Color(118,15,3), "3등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(3.96774193548387, 4.09677419354838, new Color(114,25,6), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.09677419354838, 4.2258064516129, new Color(112,32,7), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.2258064516129 , 4.35483870967742, new Color(110,40,10), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.35483870967742, 4.48387096774193, new Color(106,45,12), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.48387096774193, 4.61290322580645, new Color(115,60,27), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.61290322580645, 4.74193548387096, new Color(129,80,50), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.74193548387096, 4.87096774193548, new Color(142,102,77), "4등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(4.87096774193548, 5, new Color(153,124,108), "5등급"));
//			themeGridRange.addToTail(new ThemeGridRangeItem(5, Double.MAX_VALUE, new Color(166,153,146), "5등급"));
		}
		return themeGridRange;
	}
	
	/**
	 * layout 명 조회
	 * @param caption
	 * @return
	 */
	public static String getLayoutTemplateNm(String caption) {
		String templateNm = "";
		
		if(caption.matches("agcls|dnst|dmcls|slope|dem|aspect|river|nature|landslide")) {
			templateNm = "tp_vyt_ecb_".concat(caption);
		}else {
			templateNm = "tp_clip";
		}
		
		return templateNm;
	}
	
	/**
	 * 좌표변환
	 * @param dataset
	 * @return
	 */
	public static boolean coordSysTranslator(Dataset dataset,int epsg) {
		return CoordSysTranslator.convert(dataset, new PrjCoordSys(epsg), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
	}
	
	public static Dataset coordSysTranslator(Dataset dataset,Datasource mDatasource,String resultDatasetNm,int epsg) {
		//return CoordSysTranslator.convert(dataset, new PrjCoordSys(epsg), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
		return CoordSysTranslator.convert(dataset, new PrjCoordSys(3857), mDatasource, resultDatasetNm,new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
	}
	
	/**
	 * 래스터 데이터셋 심볼설정
	 * @param caption
	 * @return
	 */
	public static ColorDictionary getColorDictionary(String caption) {
		ColorDictionary cd = new ColorDictionary();
		
		if(caption.matches("slope.*")) {
			cd.setColor(10, new Color(56,167,0));
			cd.setColor(15, new Color(102,191,1));
			cd.setColor(20, new Color(156,217,0));
			cd.setColor(25, new Color(222,241,0));
			cd.setColor(30, new Color(255,221,0));
			cd.setColor(35, new Color(255,144,0));
			cd.setColor(40, new Color(255,73,0));
			cd.setColor(Double.MAX_VALUE, new Color(254,0,0));
		}else if(caption.matches("dem.*")) {
			cd.setColor(100, new Color(255,255,128));
			cd.setColor(200, new Color(255,241,116));
			cd.setColor(300, new Color(252,224,98));
			cd.setColor(400, new Color(250,209,85));
			cd.setColor(500, new Color(247,195,73));
			cd.setColor(600, new Color(245,180,60));
			cd.setColor(700, new Color(242,168,47));
			cd.setColor(800, new Color(219,137,37));
			cd.setColor(900, new Color(196,110,27));
			cd.setColor(1000, new Color(173,83,20));
			cd.setColor(1100, new Color(150,58,11));
			cd.setColor(1200, new Color(129,35,7));
			cd.setColor(Double.MAX_VALUE, new Color(108,6,1));
		}else if(caption.matches("aspect.*")) {
			cd.setColor(-1, new Color(175,176,176));
			cd.setColor(22.5, new Color(251,18,23));
			cd.setColor(67.5, new Color(252,166,21));
			cd.setColor(112.5, new Color(252,255,18));
			cd.setColor(157.5, new Color(3,255,0));
			cd.setColor(202.5, new Color(44,254,252));
			cd.setColor(247.5, new Color(44,158,254));
			cd.setColor(292.5, new Color(42,0,247));
			cd.setColor(337.5, new Color(250,0,254));
			cd.setColor(360, new Color(247,20,18));
		}else if(caption.matches("hillshade.*")){
			cd.setColor(0, new Color(0,0,0));
			cd.setColor(8.19354838709677, new Color(8,8,8));
			cd.setColor(16.3870967741935, new Color(16,16,16));
			cd.setColor(24.5806451612903, new Color(25,25,25));
			cd.setColor(32.7741935483871, new Color(33,33,33));
			cd.setColor(40.9677419354839, new Color(41,41,41));
			cd.setColor(49.1612903225806, new Color(49,49,49));
			cd.setColor(57.3548387096774, new Color(58,58,58));
			cd.setColor(65.5483870967742, new Color(66,66,66));
			cd.setColor(73.741935483871, new Color(74,74,74));
			cd.setColor(81.9354838709677, new Color(82,82,82));
			cd.setColor(90.1290322580645, new Color(90,90,90));
			cd.setColor(98.3225806451613, new Color(99,99,99));
			cd.setColor(106.516129032258, new Color(107,107,107));
			cd.setColor(114.709677419355, new Color(115,115,115));
			cd.setColor(122.903225806452, new Color(123,123,123));
			cd.setColor(131.096774193548, new Color(132,132,132));
			cd.setColor(139.290322580645, new Color(140,140,140));
			cd.setColor(147.483870967742, new Color(148,148,148));
			cd.setColor(155.677419354839, new Color(156,156,156));
			cd.setColor(163.870967741935, new Color(165,165,165));
			cd.setColor(172.064516129032, new Color(173,173,173));
			cd.setColor(180.258064516129, new Color(181,181,181));
			cd.setColor(188.451612903226, new Color(189,189,189));
			cd.setColor(196.645161290323, new Color(197,197,197));
			cd.setColor(204.838709677419, new Color(206,206,206));
			cd.setColor(213.032258064516, new Color(214,214,214));
			cd.setColor(221.225806451613, new Color(222,222,222));
			cd.setColor(229.41935483871, new Color(230,230,230));
			cd.setColor(237.612903225806, new Color(239,239,239));
			cd.setColor(245.806451612903, new Color(247,247,247));
			cd.setColor(254, new Color(255,255,255));
		}else if(caption.matches("landslide.*")){
			cd.setColor(1, new Color(254,0,0));
			cd.setColor(2, new Color(255,200,1));
			cd.setColor(3, new Color(182,254,142));
			cd.setColor(4, new Color(51,195,255));
			cd.setColor(5, new Color(0,1,255));
//			cd.setColor(1, new Color(170,240,233));
//			cd.setColor(1.12903225806452, new Color(176,242,211));
//			cd.setColor(1.25806451612903, new Color(176,244,188));
//			cd.setColor(1.38709677419355, new Color(191,248,178));
//			cd.setColor(1.51612903225806, new Color(218,251,178));
//			cd.setColor(1.64516129032258, new Color(237,249,173));
//			cd.setColor(1.7741935483871 , new Color(220,236,145));
//			cd.setColor(1.90322580645161, new Color(160,213,103));
//			cd.setColor(2.03225806451613, new Color(96,189,66));
//			cd.setColor(2.16129032258064, new Color(44,164,50));
//			cd.setColor(2.29032258064516, new Color(20,142,53));
//			cd.setColor(2.41935483870968, new Color(32,133,61));
//			cd.setColor(2.54838709677419, new Color(85,144,55));
//			cd.setColor(2.67741935483871, new Color(129,157,47));
//			cd.setColor(2.80645161290322, new Color(171,168,38));
//			cd.setColor(2.93548387096774, new Color(215,177,23));
//			cd.setColor(3.06451612903226, new Color(235,165,9));
//			cd.setColor(3.19354838709677, new Color(225,131,2));
//			cd.setColor(3.32258064516129, new Color(203,89,2));
//			cd.setColor(3.4516129032258 , new Color(197,54,2));
//			cd.setColor(3.58064516129032, new Color(157,25,1));
//			cd.setColor(3.70967741935484, new Color(130,12,1));
//			cd.setColor(3.83870967741935, new Color(118,15,3));
//			cd.setColor(3.96774193548387, new Color(114,25,6));
//			cd.setColor(4.09677419354838, new Color(112,32,7));
//			cd.setColor(4.2258064516129 , new Color(110,40,10));
//			cd.setColor(4.35483870967742, new Color(106,45,12));
//			cd.setColor(4.48387096774193, new Color(115,60,27));
//			cd.setColor(4.61290322580645, new Color(129,80,50));
//			cd.setColor(4.74193548387096, new Color(142,102,77));
//			cd.setColor(4.87096774193548, new Color(153,124,108));
//			cd.setColor(5, new Color(166,153,146));
		}else if(caption.matches("sdem.*")) {
			cd.setColor(-4.15472507476807, new Color(255,255,128));
			cd.setColor(58.6317838238132, new Color(255,241,116));
			cd.setColor(121.418292722394, new Color(252,224,98));
			cd.setColor(184.204801620976, new Color(250,209,85));
			cd.setColor(246.991310519557, new Color(247,195,73));
			cd.setColor(309.777819418138, new Color(245,180,60));
			cd.setColor(372.564328316719, new Color(242,168,47));
			cd.setColor(435.350837215301, new Color(219,137,37));
			cd.setColor(498.137346113882, new Color(196,110,27));
			cd.setColor(560.923855012463, new Color(173,83,20));
			cd.setColor(623.710363911044, new Color(150,58,11));
			cd.setColor(686.496872809626, new Color(129,35,7));
			cd.setColor(749.283381708207, new Color(129,35,7));
			cd.setColor(812.069890606788, new Color(129,35,7));
			cd.setColor(874.856399505369, new Color(129,35,7));
			cd.setColor(937.642908403951, new Color(129,35,7));
			cd.setColor(1000.42941730253, new Color(129,35,7));
			cd.setColor(1063.21592620111, new Color(129,35,7));
			cd.setColor(1126.00243509969, new Color(129,35,7));
			cd.setColor(1188.78894399828, new Color(129,35,7));
			cd.setColor(1251.57545289686, new Color(129,35,7));
			cd.setColor(1314.36196179544, new Color(129,35,7));
			cd.setColor(1377.14847069402, new Color(129,35,7));
			cd.setColor(1439.9349795926, new Color(129,35,7));
			cd.setColor(1502.72148849118, new Color(129,35,7));
			cd.setColor(1565.50799738976, new Color(129,35,7));
			cd.setColor(1628.29450628834, new Color(129,35,7));
			cd.setColor(1691.08101518692, new Color(129,35,7));
			cd.setColor(1753.86752408551, new Color(129,35,7));
			cd.setColor(1816.65403298409, new Color(129,35,7));
			cd.setColor(1879.44054188267, new Color(129,35,7));
			cd.setColor(1942.22705078125, new Color(129,35,7));
		}
		return cd;
	}
	
	/**
	 * Label Theme Map 라벨설정
	 * @param caption
	 * @return
	 */
	public static ThemeLabel getThemeLabel(String caption) {
		ThemeLabel themeLabel = new ThemeLabel();
		TextStyle textStyle = new TextStyle();
		
		if(caption.matches("lgstr.*")) {
			textStyle.setForeColor(new Color(196,196,196));
//			textStyle.setFontWidth(25);
			textStyle.setFontHeight(2.5);
			textStyle.setSizeFixed(true);
			textStyle.setAlignment(TextAlignment.MIDDLECENTER);
		}else if(caption.matches("rank.*")) {
			textStyle.setForeColor(new Color(0,78,205));
			textStyle.setBackColor(new Color(255,255,255));
//			textStyle.setFontWidth(25);
			textStyle.setBold(true);
			textStyle.setOutline(true);
			textStyle.setOutlineWidth(1);
			textStyle.setFontHeight(3);
			textStyle.setSizeFixed(true);
			textStyle.setAlignment(TextAlignment.MIDDLECENTER);
		}else if(caption.matches("wlgstr.*")) {
			textStyle.setForeColor(new Color(107,107,107));
			textStyle.setFontHeight(2.5);
			textStyle.setSizeFixed(true);
			textStyle.setAlignment(TextAlignment.MIDDLECENTER);
		}else if(caption.matches("ecb.*")) {
			if(caption.indexOf("lgstr") > 0) {
				textStyle.setForeColor(Color.BLACK);
//				textStyle.setFontWidth(25);
				textStyle.setFontHeight(2.5);
				textStyle.setSizeFixed(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}else if(caption.indexOf("sido") > 0) {
				textStyle.setForeColor(Color.BLACK);
				textStyle.setFontHeight(4);
				textStyle.setSizeFixed(true);
				textStyle.setBold(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}else if(caption.indexOf("signgu") > 0) {
				textStyle.setForeColor(Color.BLACK);
				textStyle.setFontHeight(4);
				textStyle.setSizeFixed(true);
				textStyle.setBold(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}else if(caption.indexOf("ecrtcnlcoord") > 0) {
				themeLabel.setBackShape(LabelBackShape.RECT);
				textStyle.setForeColor(Color.BLACK);
				textStyle.setFontHeight(4);
				textStyle.setSizeFixed(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}
		}else if(caption.matches("frd.*")) {
			if(caption.indexOf("_ea_") > 0) {
				textStyle.setForeColor(Color.BLACK);
				textStyle.setFontHeight(3);
				textStyle.setSizeFixed(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}else if(caption.indexOf("_lgstr_") > 0) {
				textStyle.setForeColor(Color.BLACK);
				textStyle.setFontHeight(5);
				textStyle.setBold(true);
				textStyle.setSizeFixed(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}else if(caption.indexOf("_cul_") > 0) {
//				textStyle.setForeColor(new Color(103,165,56));
				textStyle.setForeColor(new Color(103,165,56));
				textStyle.setBackColor(new Color(255,255,255));
//				textStyle.setFontWidth(25);
				textStyle.setBold(true);
				textStyle.setOutline(true);
				textStyle.setOutlineWidth(1);
				textStyle.setFontHeight(3);
				textStyle.setSizeFixed(true);
				textStyle.setAlignment(TextAlignment.MIDDLECENTER);
			}
			
		}
		themeLabel.setLabelExpression(getLabelExpr(caption));
		themeLabel.setUniformStyle(textStyle);
		
		return themeLabel;
	}
	/**
	 * Label Theme Map LabelExprssion 설정
	 * @param caption
	 * @return
	 */
	private static String getLabelExpr(String caption) {
		String expr = null;
		if(caption.matches("lgstr.*") || caption.matches("wlgstr.*") || caption.matches("ecb_lgstr.*")) {
			expr = "a5";
		}else if(caption.matches("rank.*")) {
			expr = "svy_label";
		}else if(caption.matches("frd.*")) {
			if(caption.indexOf("_ea_") > 0) {
				expr = "emd_nm";
			}else if(caption.indexOf("_lgstr_") > 0) {
				expr = "a5";
			}else if(caption.indexOf("_cul_") > 0) {
				expr = "cult_nm";
			}
		}else if(caption.matches("ecb_sido.*")) {
			expr = "ctprvn_nm";
		}else if(caption.matches("ecb_signgu.*")) {
			expr = "signgu_nm";
		}else if(caption.matches("ecb_ecrtcnlcoord.*")) {
			expr = "coord";
		}
		return expr;
	}
	
	/**
	 * 벡터 데이터셋 심볼 설정
	 * @param caption
	 * @return
	 * @throws Exception
	 */
	public static GeoStyle getGeoStyle(String caption) throws Exception{
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
		}else if(caption.matches("stream3ha.*|stream5ha.*")) {
			style.setLineColor(new Color(0,112,192));
			style.setLineWidth(0.5);
		}else if(caption.matches("ecb.*")) {
			if(caption.indexOf("ctrln") > 0) {
				style.setLineColor(new Color(216,204,1));
			}else if(caption.indexOf("lgstr") > 0) {
				style.setLineColor(Color.black);
			}else if(caption.indexOf("sido") > 0) {
				style.setFillForeColor(Color.red);
				style.setFillOpaqueRate(100);
			}else if(caption.indexOf("ctprvn") > 0) {
				style.setFillForeColor(new Color(67,175,116));
				style.setLineColor(Color.white);
				style.setLineWidth(1);
				style.setFillOpaqueRate(100);
			}else if(caption.indexOf("_signgus_") > 0) {
				style.setFillForeColor(new Color(173,219,125));
				style.setLineColor(Color.white);
				style.setLineWidth(1);
				style.setFillOpaqueRate(100);
			}else if(caption.indexOf("_signgu_") > 0) {
				style.setFillForeColor(Color.red);
				style.setFillOpaqueRate(100);
			}else if(caption.indexOf("road") > 0) {
				style.setLineColor(new Color(176,55,55));
				style.setLineWidth(0.2);
			}else if(caption.indexOf("building") > 0) {
				style.setLineColor(new Color(0,0,0));
				style.setLineWidth(0.1);
			}else if(caption.indexOf("river") > 0) {
				style.setLineColor(new Color(16,110,190));
				style.setLineWidth(0.2);
			}else if(caption.indexOf("li") > 0) {
				style.setLineColor(new Color(0,0,0));
				style.setLineWidth(0.1);
			}else if(caption.indexOf("buffer") > 0) {
				style.setFillForeColor(Color.red);
				style.setFillOpaqueRate(0);
				style.setLineColor(Color.red);
				style.setLineWidth(0.3);
				style.setLineSymbolID(1);
			}
		}else if(caption.matches("frd.*")) {
			if(caption.indexOf("_y_") > 0) {
				style.setLineColor(new Color(255,255,0));
				style.setLineWidth(0.5);
			}else if(caption.indexOf("_blue_") > 0) {
				style.setLineColor(new Color(0,0,255));
				style.setLineWidth(0.5);
			}else if(caption.indexOf("_ea_") > 0) {
				style.setLineColor(Color.black);
				style.setLineWidth(0.5);
			}else if(caption.indexOf("_eo_") > 0) {
				style.setFillForeColor(new Color(225,225,225));
				style.setFillOpaqueRate(100);
				style.setLineColor(Color.black);
				style.setLineWidth(0.5);
			}else if(caption.indexOf("_one_") > 0) {
				style.setFillForeColor(new Color(254,0,0));
				style.setFillOpaqueRate(1);
				style.setMarkerSize(new Size2D(6, 6));
				style.setLineColor(new Color(254,0,0));
			}else if(caption.indexOf("_b_") > 0) {
				style.setLineColor(new Color(0, 0, 255));
				style.setLineWidth(0.5);
			}else if(caption.indexOf("_cul_") > 0) {
				style.setLineColor(Color.red);
				style.setLineWidth(0.5);
			}else if(caption.indexOf("_buffer_") > 0) {
				style.setFillForeColor(Color.red);
				style.setFillOpaqueRate(20);
				style.setLineColor(Color.red);
				style.setLineWidth(0.5);
			}
		}
		else {
			style.setLineColor(Color.black);
		}
		
		return style;
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
	
	/**
	 * 파일저장 경로 생성
	 * @return
	 * @throws Exception
	 */
	public static AnalFileVO getSavePath(String analId, String midPath) throws Exception{
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
				LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
			}else{
				LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
			}
		}
		
		vo.setAnalId(analId);
		vo.setFileStreCours(strePathString);
		vo.setStreFileNm(streFileNm);
		
		return vo;
	}
	
	/**
	 * 지도 이미지 저장 export JPG, DPI150
	 * @return
	 * @throws Exception
	 */
	public static AnalFileVO saveMapImageToJpg(MapLayoutControl mapLayoutcontrol,AnalFileVO vo) throws Exception{
		LOGGER.info("지도 이미지 저장 시작");
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
	public static void deleteMap(Workspace workspace, String mapNm) {
		//workspace.getMaps().get
		workspace.getMaps().remove(mapNm);
	}
	
	/**
	 * 조사완료 공간정보데이터셋 쿼리 생성
	 * @param maps
	 * @return
	 */
	public static String getQueryString(HashMap<String, Object> maps) {
		String queryWhere = "";
		String svyType = maps.get("type").toString();
		
		if(maps.get("startdt") != null && maps.get("enddt") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "(to_date(to_char(creat_dt,'YYYY-MM-DD'),'YYYY-MM-DD') >= to_date('".concat(maps.get("startdt").toString()).concat("', 'YYYY-MM-DD') AND to_date(to_char(creat_dt,'YYYY-MM-DD'),'YYYY-MM-DD') <= TO_DATE('").concat(maps.get("enddt").toString()).concat("', 'YYYY-MM-DD'))");
		}
		
		if(maps.get("mstids") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "mst_id in (".concat(maps.get("mstids").toString()).concat(")");
		}
		
		if(maps.get("svytype") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_type = '".concat(maps.get("svytype").toString()).concat("'");
		}
		
		if(maps.get("sdnm") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_sd = '".concat(maps.get("sdnm").toString()).concat("'");
		}
		
		if(maps.get("sggnm") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_sgg = '".concat(maps.get("sggnm").toString()).concat("'");
		}
		
		if(maps.get("emdnm") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_emd = '".concat(maps.get("emdnm").toString()).concat("'");
		}
		
		if(maps.get("rinm") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_ri = '".concat(maps.get("rinm").toString()).concat("'");
		}
		
		if(maps.get("jibun") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_jibun = '".concat(maps.get("jibun").toString()).concat("'");
		}
		
		if(maps.get("user") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_user = '".concat(maps.get("user").toString()).concat("'");
		}
		
		if(maps.get("svyid") != null) {
			if(queryWhere.length() > 0) {queryWhere += " and ";}
			queryWhere += "svy_label = '".concat(maps.get("svyid").toString()).concat("'");
		}
		
		if(svyType.equals("bsc")) {
			if(maps.get("safty") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_safty = '".concat(maps.get("safty").toString()).concat("'");
			}
			
			if(maps.get("safty2") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_safty = '".concat(maps.get("safty2").toString()).concat("'");
			}
			
			if(maps.get("slen") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_slen = '".concat(maps.get("slen").toString()).concat("'");
			}
			
			if(maps.get("slope") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_slope = '".concat(maps.get("slope").toString()).concat("'");
			}
			
			if(maps.get("sform") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_sform = '".concat(maps.get("sform").toString()).concat("'");
			}
			
			if(maps.get("frstfr") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_frstfr = '".concat(maps.get("frstfr").toString()).concat("'");
			}
			
			if(maps.get("prntrck") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_prntrck = '".concat(maps.get("prntrck").toString()).concat("'");
			}
			
			if(maps.get("devoccause") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_devoccause = '".concat(maps.get("devoccause").toString()).concat("'");
			}
			
			if(maps.get("trntavgslp") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_trntavgslp = '".concat(maps.get("trntavgslp").toString()).concat("'");
			}
			
			if(maps.get("wclctarea") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_wclctarea = '".concat(maps.get("wclctarea").toString()).concat("'");
			}
			
			if(maps.get("tltrntlt") != null) {
				if(queryWhere.length() > 0) {queryWhere += " and ";}
				queryWhere += "svy_tltrntlt = '".concat(maps.get("tltrntlt").toString()).concat("'");
			}
		}else if(svyType.equals("apr")) {
//			if(maps.get("orginldamval") != null) {
//				if(queryWhere.length() > 0) {queryWhere += " and ";}
//				queryWhere += 
//			}
		}
		
		LOGGER.info(queryWhere);
		
		return queryWhere;
	}
	
	/**
	 * smid 조회쿼리 생성
	 * @param smidList
	 * @return
	 */
	public static String getQueryString(String smidList) {
		return "smid in(".concat(smidList).concat(")");
	}
}