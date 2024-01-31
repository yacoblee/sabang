package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.servlet.view.AbstractView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class EcbStatExcelView extends AbstractView {

	/** The content type for an Excel response */
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	private static XSSFCellStyle HeaderStyle1 = null;
	private static XSSFCellStyle HeaderStyle2 = null;

	/**
	 * Default Constructor. Sets the content type of the view for excel files.
	 */
	public EcbStatExcelView() {
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	/**
	 * Renders the Excel view, given the specified model.
	 */
	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		logger.debug("Created Excel Workbook from scratch");

		setContentType(CONTENT_TYPE_XLSX);

		HeaderStyle1 = getHeaderCellStyle1(workbook);
		HeaderStyle2 = getHeaderCellStyle2(workbook);

		buildExcelDocument(model, workbook, request, response);
		
		// Set the filename
		String sFilename = "";
		if (model.get("filename") != null) {
			sFilename = (String) model.get("filename");
		} else if (request.getAttribute("filename") != null) {
			sFilename = (String) request.getAttribute("filename");
		} else {
			sFilename = getClass().getSimpleName();
		}

		response.setContentType(getContentType());

		String header = request.getHeader("User-Agent");
		sFilename = sFilename.replaceAll("\r", "").replaceAll("\n", "");
		if (header.contains("MSIE") || header.contains("Trident") || header.contains("Chrome")) {
			sFilename = URLEncoder.encode(sFilename, "UTF-8").replaceAll("\\+", "%20");
			response.setHeader("Content-Disposition", "attachment;filename="+sFilename+".xlsx;");
		} else {
			sFilename = new String(sFilename.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\""+sFilename+".xlsx\"");
		}

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		out.flush();
		workbook.write(out);
		out.flush();

		// Don't close the stream - we didn't open it, so let the container handle it.
		// http://stackoverflow.com/questions/1829784/should-i-close-the-servlet-outputstream
	}

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		XSSFCell cell = null;
		JSONParser parser = new JSONParser();
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		//지황분석		
		//		slope
		//		aspect
		//		dem
		//임황분석
		//		koftr
		//		agcls
		//		dnst
		//		dmcls
		//생태자연도		
		//		nature
		List<String> slopeCdIntervalLabel = new ArrayList<>(Arrays.asList("10도 미만","10-20도","20-30도","30-40도","40-50도","50-60도","60-70도","70-80도","80도 이상"));
		List<String> aspectCdIntervalLabel = new ArrayList<>(Arrays.asList("평지","북","북동","동","남동","남","남서","서","북서"));
		List<String> demCdIntervalLabel = new ArrayList<>(Arrays.asList("0-100m","100-200m","200-300m","300-400m","400-500m","500-600m","600-700m","700-800m","800-900m","900-1000m","1000-1100m","1100-1200m","1200m 이상"));
		List<String> geoCdIntervalLabel = new ArrayList<>(Arrays.asList("화성암","퇴적암","변성암"));
		List<String> koftrCdIntervalLabel = new ArrayList<>(Arrays.asList("기타침엽수","소나무","잣나무","낙엽송","리기다소나무","곰솔","전나무","편백나무","삼나무","가분비나무","비자나무","은행나무","기타활엽수","상수리나무","신갈나무","굴참나무","기타 참나무류","오리나무","고로쇠나무","자작나무","박달나무","밤나무","물푸레나무","서어나무","때죽나무","호두나무","백합나무","포플러","벚나무","느티나무","층층나무","아까시나무","기타상록활엽수","가시나무","구실잣밤나무","녹나무","굴거리나무","황칠나무","사스레피나무","후박나무","새덕이","침활혼효림","죽림","미립목지","제지","관목덤불","주거지","초지","경작지","수체","과수원","기타"));
		List<String> agclsCdIntervalLabel = new ArrayList<>(Arrays.asList("1영급","2영급","3영급","4영급","5영급","6영급","7영급","8영급","9영급"));
		List<String> dnstCdIntervalLabel = new ArrayList<>(Arrays.asList("소","중","밀"));
		List<String> dmclsCdIntervalLabel = new ArrayList<>(Arrays.asList("치수","소경목","중경목","대경목"));
		List<String> natureCdIntervalLabel = new ArrayList<>(Arrays.asList("1등급","2등급","3등급"));
		
		int aspectRatioCnt = 0;
		int slopeRatioCnt = 0;
		int demRatioCnt = 0;
		int koftrRatioCnt = 0;
		int agclsRatioCnt = 0;
		int dnstRatioCnt = 0;
		int dmclsRatioCnt = 0;
		int natureRatioCnt = 0;
		int geoRatioCnt = 0;
		
		JSONObject aspectRatioJson = null;
		JSONObject slopeRatioJson = null;
		JSONObject demRatioJson = null;
		JSONObject koftrRatioJson = null;
		JSONObject agclsRatioJson = null;
		JSONObject dnstRatioJson = null;
		JSONObject dmclsRatioJson = null;
		JSONObject natureRatioJson = null;
		JSONObject geoRatioJson = null;
		
		JSONObject aspectAreaJson = null;
		JSONObject slopeAreaJson = null;
		JSONObject demAreaJson = null;
		JSONObject koftrAreaJson = null;
		JSONObject agclsAreaJson = null;
		JSONObject dnstAreaJson = null;
		JSONObject dmclsAreaJson = null;
		JSONObject natureAreaJson = null;
		JSONObject geoAreaJson = null;
		
		JSONObject aspectStatistics = new JSONObject();
		JSONObject slopeStatistics = new JSONObject();
		JSONObject demStatistics = new JSONObject();
		
		String sheetNm1 = "지황분석";
		String sheetNm2 = "임황분석";
		String sheetNm3 = "생태자연도";
		
		XSSFSheet sheet = null;
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				String analType = dataList.get(i).get("analType") != null ? dataList.get(i).get("analType").toString() : "{}";
				String analRatio = dataList.get(i).get("analRatio") != null ? dataList.get(i).get("analRatio").toString() : "{}";
				String analArea = dataList.get(i).get("analArea") != null ? dataList.get(i).get("analArea").toString() : "{}";
				String analMin = dataList.get(i).get("analMin") != null ? dataList.get(i).get("analMin").toString() : null;
				String analMax = dataList.get(i).get("analMax") != null ? dataList.get(i).get("analMax").toString() : null;
				String analAvg = dataList.get(i).get("analAvg") != null ? dataList.get(i).get("analAvg").toString() : null;
				String analStd = dataList.get(i).get("analStd") != null ? dataList.get(i).get("analStd").toString() : null;
				
				Object analRatioObj = parser.parse(analRatio);
				Object analAreaObj = parser.parse(analArea);
				
				JSONObject analRatioJson = (JSONObject) analRatioObj;
				JSONObject analAreaJson = (JSONObject) analAreaObj;
				
				if(analType.equals("aspect")) {//향
					aspectRatioJson = analRatioJson;
					aspectAreaJson = analAreaJson;
					aspectRatioCnt = aspectRatioJson.size();
					aspectStatistics.put("최소", analMin);
					aspectStatistics.put("최대", analMax);
					aspectStatistics.put("평균", analAvg);
					aspectStatistics.put("표준편차", analStd);
				}else if(analType.equals("slope")) {//경사
					slopeRatioJson = analRatioJson;
					slopeAreaJson = analAreaJson;
					slopeRatioCnt = slopeRatioJson.size();
					slopeStatistics.put("최소", analMin);
					slopeStatistics.put("최대", analMax);
					slopeStatistics.put("평균", analAvg);
					slopeStatistics.put("표준편차", analStd);
				}else if(analType.equals("dem")) {//표고
					demRatioJson = analRatioJson;
					demAreaJson = analAreaJson;
					demRatioCnt = demRatioJson.size();
					demStatistics.put("최소", analMin);
					demStatistics.put("최대", analMax);
					demStatistics.put("평균", analAvg);
					demStatistics.put("표준편차", analStd);
				}else if(analType.equals("koftr")) {//임상
					koftrRatioJson = analRatioJson;
					koftrAreaJson = analAreaJson;
					koftrRatioCnt = koftrRatioJson.size();
				}else if(analType.equals("agcls")) {//영급
					agclsRatioJson = analRatioJson;
					agclsAreaJson = analAreaJson;
					agclsRatioCnt = agclsRatioJson.size();
				}else if(analType.equals("dnst")) {//소밀도
					dnstRatioJson = analRatioJson;
					dnstAreaJson = analAreaJson;
					dnstRatioCnt = dnstRatioJson.size();
				}else if(analType.equals("dmcls")) {//경급
					dmclsRatioJson = analRatioJson;
					dmclsAreaJson = analAreaJson;
					dmclsRatioCnt = dmclsRatioJson.size();
				}else if(analType.equals("nature")) {//생태자연도
					natureRatioJson = analRatioJson;
					natureAreaJson = analAreaJson;
					natureRatioCnt = natureRatioJson.size();
				}else if(analType.equals("geological")) {//지질도
					geoRatioJson = analRatioJson;
					geoAreaJson = analAreaJson;
					geoRatioCnt = geoRatioJson.size();
				}
			}
			
			int cnt1 = (aspectRatioJson != null ? aspectRatioCnt : 0)+(slopeRatioJson != null ? slopeRatioCnt : 0)+(demRatioJson != null ? demRatioCnt : 0)+(geoRatioJson != null ? geoRatioCnt : 0);
			int cnt2 = (koftrRatioJson != null ? koftrRatioCnt : 0)+(agclsRatioJson != null ? agclsRatioCnt : 0)+(dnstRatioJson != null ? dnstRatioCnt : 0)+(dmclsRatioJson != null ? dmclsRatioCnt : 0);
			int cnt3 = (natureRatioJson != null ? natureRatioCnt : 0);
			
			sheet = wb.createSheet(sheetNm1);
			sheet.setDefaultColumnWidth(10);
			
			if(cnt1 > 0) {
				Row row = sheet.createRow(1);
				//row.setHeight((short)2000);
				int rowNo = 0, colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "");//0
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				colNo++;//1
				
				for (int i = 0; i < aspectRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "향분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-aspectRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-aspectRatioCnt, colNo-1));
				}
				
				for (int i = 0; i < slopeRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "경사분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-slopeRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-slopeRatioCnt, colNo-1));
				}
				
				for (int i = 0; i < demRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "표고분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-demRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-demRatioCnt, colNo-1));
				}
				
				for (int i = 0; i < geoRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "지질도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-geoRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-geoRatioCnt, colNo-1));
				}
				
				rowNo++;
				colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "분류");
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), "면적(ha)");
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+2, colNo), "비율(%)");
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
				
				colNo++;
				
				for(String i : aspectCdIntervalLabel) {
					if(aspectAreaJson != null && aspectAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), aspectAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), aspectRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						
						colNo++;
					}
				}
				
				//colNo = 1;
				
				for(String i : slopeCdIntervalLabel) {
					if(slopeAreaJson != null && slopeAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), slopeAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), slopeRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						
						colNo++;
					}
				}
				
				//colNo = 1;
				
				for(String i : demCdIntervalLabel) {
					if(demAreaJson != null && demAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), demAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), demRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						
						colNo++;
					}
				}
				
				//colNo = 1;
				
				for(String i : geoCdIntervalLabel) {
					if(geoAreaJson != null && geoAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), geoAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), geoRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						
						colNo++;
					}
				}
				
				//최저,최고,평균,표준편차
				rowNo = rowNo+4;
				colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "");//0
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), "최저");
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+2, colNo), "최고");
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+3, colNo), "평균");
				getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+4, colNo), "표준편차");
				getCell(sheet, rowNo+4, colNo).setCellStyle(HeaderStyle1);
				
				colNo++;//1
				
				setText(getCell(sheet, rowNo, colNo), "향분포도");
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), (aspectStatistics.get("최소") != null ? aspectStatistics.get("최소").toString() : ""));
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+2, colNo), (aspectStatistics.get("최대") != null ? aspectStatistics.get("최대").toString() : ""));
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+3, colNo), (aspectStatistics.get("평균") != null ? aspectStatistics.get("평균").toString() : ""));
				getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+4, colNo), (aspectStatistics.get("표준편차") != null ? aspectStatistics.get("표준편차").toString() : ""));
				getCell(sheet, rowNo+4, colNo).setCellStyle(HeaderStyle2);
				
				colNo++;
				
				setText(getCell(sheet, rowNo, colNo), "경사분포도");
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), (slopeStatistics.get("최소") != null ? slopeStatistics.get("최소").toString() : ""));
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+2, colNo), (slopeStatistics.get("최대") != null ? slopeStatistics.get("최대").toString() : ""));
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+3, colNo), (slopeStatistics.get("평균") != null ? slopeStatistics.get("평균").toString() : ""));
				getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+4, colNo), (slopeStatistics.get("표준편차") != null ? slopeStatistics.get("표준편차").toString() : ""));
				getCell(sheet, rowNo+4, colNo).setCellStyle(HeaderStyle2);
				
				colNo++;
				
				setText(getCell(sheet, rowNo, colNo), "표고분포도");
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), (demStatistics.get("최소") != null ? demStatistics.get("최소").toString() : ""));
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+2, colNo), (demStatistics.get("최대") != null ? demStatistics.get("최대").toString() : ""));
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+3, colNo), (demStatistics.get("평균") != null ? demStatistics.get("평균").toString() : ""));
				getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo+4, colNo), (demStatistics.get("표준편차") != null ? demStatistics.get("표준편차").toString() : ""));
				getCell(sheet, rowNo+4, colNo).setCellStyle(HeaderStyle2);
//				setText(getCell(sheet, rowNo, colNo), "면적(ha)");
//				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
//				colNo++;
				
//				for(String i : aspectCdIntervalLabel) {
//					if(aspectAreaJson != null && aspectAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), aspectAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : slopeCdIntervalLabel) {
//					if(slopeAreaJson != null && slopeAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), slopeAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : demCdIntervalLabel) {
//					if(demAreaJson != null && demAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), demAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : geoCdIntervalLabel) {
//					if(geoAreaJson != null && geoAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), geoAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				rowNo++;
//				colNo = 0;
				
//				setText(getCell(sheet, rowNo, colNo), "비율(%)");
//				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
//				colNo++;
				
//				for(String i : aspectCdIntervalLabel) {
//					if(aspectRatioJson != null && aspectRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), aspectRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : slopeCdIntervalLabel) {
//					if(slopeRatioJson != null && slopeRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), slopeRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : demCdIntervalLabel) {
//					if(demRatioJson != null && demRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), demRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : geoCdIntervalLabel) {
//					if(geoRatioJson != null && geoRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), geoRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
			}
			
			sheet = wb.createSheet(sheetNm2);
			sheet.setDefaultColumnWidth(10);
			
			if(cnt2 > 0) {
				Row row = sheet.createRow(1);
				//row.setHeight((short)2000);
				int rowNo = 0, colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "");//0
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				colNo++;//1
				
				for (int i = 0; i < koftrRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "임상분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-koftrRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-koftrRatioCnt, colNo-1));
				}
				
				for (int i = 0; i < agclsRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "영급분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-agclsRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-agclsRatioCnt, colNo-1));
				}
				
				for (int i = 0; i < dnstRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "소밀도분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-dnstRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-dnstRatioCnt, colNo-1));
				}
				
				for (int i = 0; i < dmclsRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "경급분포도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-dmclsRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-dmclsRatioCnt, colNo-1));
				}
				
				rowNo++;
				colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "분류");
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), "면적(ha)");
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+2, colNo), "비율(%)");
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
				colNo++;
				
				for(String i : koftrCdIntervalLabel) {
					if(koftrAreaJson != null && koftrAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), koftrAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), koftrRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						colNo++;
					}
				}
				
				for(String i : agclsCdIntervalLabel) {
					if(agclsAreaJson != null && agclsAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), agclsAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), agclsRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						colNo++;
					}
				}
				
				for(String i : dnstCdIntervalLabel) {
					if(dnstAreaJson != null && dnstAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), dnstAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), dnstRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						colNo++;
					}
				}
				
				for(String i : dmclsCdIntervalLabel) {
					if(dmclsAreaJson != null && dmclsAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), dmclsAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), dmclsRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						colNo++;
					}
				}
				
				//rowNo++;
				//colNo = 0;
				
//				setText(getCell(sheet, rowNo, colNo), "면적(ha)");
//				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
//				colNo++;
				
//				for(String i : koftrCdIntervalLabel) {
//					if(koftrAreaJson != null && koftrAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), koftrAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : agclsCdIntervalLabel) {
//					if(agclsAreaJson != null && agclsAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), agclsAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : dnstCdIntervalLabel) {
//					if(dnstAreaJson != null && dnstAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), dnstAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : dmclsCdIntervalLabel) {
//					if(dmclsAreaJson != null && dmclsAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), dmclsAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				rowNo++;
//				colNo = 0;
				
//				setText(getCell(sheet, rowNo, colNo), "비율(%)");
//				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
//				colNo++;
				
//				for(String i : koftrCdIntervalLabel) {
//					if(koftrRatioJson != null && koftrRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), koftrRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : agclsCdIntervalLabel) {
//					if(agclsRatioJson != null && agclsRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), agclsRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : dnstCdIntervalLabel) {
//					if(dnstRatioJson != null && dnstRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), dnstRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				for(String i : dmclsCdIntervalLabel) {
//					if(dmclsRatioJson != null && dmclsRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), dmclsRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
			}
			
			sheet = wb.createSheet(sheetNm3);
			sheet.setDefaultColumnWidth(10);
			
			if(cnt3 > 0) {
				Row row = sheet.createRow(1);
				//row.setHeight((short)2000);
				int rowNo = 0, colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "");//0
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				colNo++;//1
				
				for (int i = 0; i < natureRatioCnt; i++) {
					if(i == 0) {
						setText(getCell(sheet, rowNo, colNo), "생태자연도");
					}
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
					colNo++;
				}
				
				if(colNo-natureRatioCnt < colNo-1) {
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo-natureRatioCnt, colNo-1));
				}
				
				rowNo++;
				colNo = 0;
				
				setText(getCell(sheet, rowNo, colNo), "분류");
				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+1, colNo), "면적(ha)");
				getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo+2, colNo), "비율(%)");
				getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
				colNo++;
				
				for(String i : natureCdIntervalLabel) {
					if(natureAreaJson != null && natureAreaJson.get(i) != null) {
						setText(getCell(sheet, rowNo, colNo), i);
						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+1, colNo), natureAreaJson.get(i).toString());
						getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle2);
						
						setText(getCell(sheet, rowNo+2, colNo), natureRatioJson.get(i).toString());
						getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle2);
						colNo++;
					}
				}
				
//				rowNo++;
//				colNo = 0;
				
//				setText(getCell(sheet, rowNo, colNo), "면적(ha)");
//				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
//				colNo++;
				
//				for(String i : natureCdIntervalLabel) {
//					if(natureAreaJson != null && natureAreaJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), natureAreaJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
				
//				rowNo++;
//				colNo = 0;
				
//				setText(getCell(sheet, rowNo, colNo), "비율(%)");
//				getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
//				colNo++;
				
//				for(String i : natureCdIntervalLabel) {
//					if(natureRatioJson != null && natureRatioJson.get(i) != null) {
//						setText(getCell(sheet, rowNo, colNo), natureRatioJson.get(i).toString());
//						getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle2);
//						colNo++;
//					}
//				}
			}
		}
	}

	/**
	 * Convenient method to obtain the cell in the given sheet, row and column.
	 * 
	 * <p>
	 * Creates the row and the cell if they still doesn't already exist. Thus, the
	 * column can be passed as an int, the method making the needed downcasts.
	 * </p>
	 * 
	 * @param sheet a sheet object. The first sheet is usually obtained by
	 *              workbook.getSheetAt(0)
	 * @param row   thr row number
	 * @param col   the column number
	 * @return the XSSFCell
	 */
	protected XSSFCell getCell(XSSFSheet sheet, int row, int col) {
		XSSFRow sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		XSSFCell cell = sheetRow.getCell(col);
		if (cell == null) {
			cell = sheetRow.createCell(col);
		}
		return cell;
	}

	/**
	 * Convenient method to set a String as text content in a cell.
	 * 
	 * @param cell the cell in which the text must be put
	 * @param text the text to put in the cell
	 */
	protected void setText(XSSFCell cell, String text) {
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(text);
	}

	/**
	 * Title Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getTitleCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setDataFormat((short) 0x31);

		return cellStyle;
	}

	/**
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle1(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(217, 217, 217)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		Font font = wb.createFont();
		font.setBold(true);
		
		cellStyle.setFont(font);
		
		return cellStyle;
	}

	/**
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle2(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255,255,255)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

	/**
	 * Value Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getValueCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 데이터셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		return cellStyle;
	}

}
