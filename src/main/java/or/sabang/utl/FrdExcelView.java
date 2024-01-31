package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.JSONParser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.GeoPoint;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.PrjCoordSys;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptService;
import or.sabang.sys.vyt.frd.service.impl.VytFrdSvyComptDAO;
import or.sabang.sys.vyt.frd.web.VytFrdSvyComptController;

public class FrdExcelView extends AbstractView {

	/** The content type for an Excel response */
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	private static XSSFCellStyle HeaderStyle = null;
	private static XSSFCellStyle HeaderStyle1 = null;
	private static XSSFCellStyle HeaderStyle2 = null;
	private static XSSFCellStyle HeaderStyle3 = null;
	private static XSSFCellStyle HeaderStyle4 = null;
	private static XSSFCellStyle HeaderStyle5 = null;
	private static XSSFCellStyle HeaderStyle6 = null;
	private static XSSFCellStyle HeaderStyle7 = null;
	private static XSSFCellStyle HeaderStyle1_b = null;
	private static XSSFCellStyle HeaderStyle2_b = null;
	private static XSSFCellStyle HeaderStyle3_b = null;
	private static XSSFCellStyle HeaderStyle4_b = null;
	private static XSSFCellStyle HeaderStyle5_b = null;
	private static XSSFCellStyle HeaderStyle6_b = null;
	private static XSSFCellStyle HeaderStyle7_b = null;
	private static XSSFCellStyle HeaderStyle8 = null;
	private static XSSFCellStyle HeaderStyle9 = null;
	private static XSSFCellStyle HeaderStyle10 = null;
	private static XSSFCellStyle HeaderStyle11 = null;
	private static XSSFCellStyle HeaderStyle12 = null;
	private static XSSFCellStyle HeaderStyle13 = null;
	private static XSSFCellStyle HeaderStyle14 = null;
	private static XSSFCellStyle HeaderStyle15 = null;
	private static XSSFCellStyle HeaderStyle16 = null;
	private static XSSFCellStyle HeaderStyle17 = null;
	private static XSSFCellStyle ValueStyle = null;

	/**
	 * Default Constructor. Sets the content type of the view for excel files.
	 */
	public FrdExcelView() {
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
		HeaderStyle3 = getHeaderCellStyle3(workbook);
		HeaderStyle4 = getHeaderCellStyle4(workbook);
		HeaderStyle5 = getHeaderCellStyle5(workbook);
		HeaderStyle6 = getHeaderCellStyle6(workbook);
		HeaderStyle7 = getHeaderCellStyle7(workbook);
		HeaderStyle8 = getHeaderCellStyle8(workbook);
		HeaderStyle9 = getHeaderCellStyle9(workbook);
		HeaderStyle10 = getHeaderCellStyle10(workbook);
		HeaderStyle11 = getHeaderCellStyle11(workbook);
		HeaderStyle12 = getHeaderCellStyle12(workbook);
		HeaderStyle13 = getHeaderCellStyle13(workbook);
		HeaderStyle14 = getHeaderCellStyle14(workbook);
		HeaderStyle15 = getHeaderCellStyle15(workbook);
		HeaderStyle16 = getHeaderCellStyle16(workbook);
		HeaderStyle17 = getHeaderCellStyle17(workbook);
		
		HeaderStyle1_b = getHeaderCellStyle1_b(workbook);
		HeaderStyle2_b = getHeaderCellStyle2_b(workbook);
		HeaderStyle3_b = getHeaderCellStyle3_b(workbook);
		HeaderStyle4_b = getHeaderCellStyle4_b(workbook);
		HeaderStyle5_b = getHeaderCellStyle5_b(workbook);
		HeaderStyle6_b = getHeaderCellStyle6_b(workbook);
		HeaderStyle7_b = getHeaderCellStyle7_b(workbook);

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
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		String sheetNm = "조사정보"; // 엑셀 시트 이름

		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		XSSFSheet sheet = wb.createSheet(sheetNm);
		sheet.setDefaultColumnWidth(14);
		Row row = sheet.createRow(1);
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		setText(getCell(sheet, rowNo, colNo), "구분");
		getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1_b);
		getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle1_b);
		getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle1_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo, colNo+2));
		
		setText(getCell(sheet, rowNo, colNo+3), "행정구역");
		getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle2_b);
		getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle2_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+3, colNo+10));
		
		setText(getCell(sheet, rowNo, colNo+11), "조사자");
		getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle3_b);
		getCell(sheet, rowNo, colNo+12).setCellStyle(HeaderStyle3_b);
		getCell(sheet, rowNo, colNo+13).setCellStyle(HeaderStyle3_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+11, colNo+13));
		
		setText(getCell(sheet, rowNo, colNo+14), "시점경도");
		getCell(sheet, rowNo, colNo+14).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+15).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+16).setCellStyle(HeaderStyle4_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+14, colNo+16));
		
		setText(getCell(sheet, rowNo, colNo+17), "시점위도");
		getCell(sheet, rowNo, colNo+17).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+18).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+19).setCellStyle(HeaderStyle4_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+17, colNo+19));
		
		setText(getCell(sheet, rowNo, colNo+20), "종점경도");
		getCell(sheet, rowNo, colNo+20).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+21).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+22).setCellStyle(HeaderStyle4_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+20, colNo+22));
		
		setText(getCell(sheet, rowNo, colNo+23), "종점위도");
		getCell(sheet, rowNo, colNo+23).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+24).setCellStyle(HeaderStyle4_b);
		getCell(sheet, rowNo, colNo+25).setCellStyle(HeaderStyle4_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+23, colNo+25));
		
		setText(getCell(sheet, rowNo, colNo+26), "조사정보");
		getCell(sheet, rowNo, colNo+26).setCellStyle(HeaderStyle5_b);
		getCell(sheet, rowNo, colNo+27).setCellStyle(HeaderStyle5_b);
		getCell(sheet, rowNo, colNo+28).setCellStyle(HeaderStyle5_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+26, colNo+28));
		
		setText(getCell(sheet, rowNo, colNo+29), "조사자 의견 및 특이사항");
		getCell(sheet, rowNo, colNo+29).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+30).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+31).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+32).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+33).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+34).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+35).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+36).setCellStyle(HeaderStyle6_b);
		getCell(sheet, rowNo, colNo+37).setCellStyle(HeaderStyle6_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+29, colNo+37));
		
		colNo = 0;
		
		setText(getCell(sheet, rowNo+1, colNo+0), "조사ID");
		getCell(sheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle1_b);
		setText(getCell(sheet, rowNo+1, colNo+1), "조사구분");
		getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1_b);
		setText(getCell(sheet, rowNo+1, colNo+2), "임도종류");
		getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle1_b);
		
		setText(getCell(sheet, rowNo+1, colNo+3), "시도");
		getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+4), "시군구");
		getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+5), "읍면동");
		getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+6), "리");
		getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+7), "지번");
		getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+8), "유림");
		getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+9), "관할청");
		getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle2_b);
		setText(getCell(sheet, rowNo+1, colNo+10), "세부관할");
		getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle2_b);
		
		setText(getCell(sheet, rowNo+1, colNo+11), "소속");
		getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle3_b);
		setText(getCell(sheet, rowNo+1, colNo+12), "성명");
		getCell(sheet, rowNo+1, colNo+12).setCellStyle(HeaderStyle3_b);
		setText(getCell(sheet, rowNo+1, colNo+13), "조사일자");
		getCell(sheet, rowNo+1, colNo+13).setCellStyle(HeaderStyle3_b);
		
		setText(getCell(sheet, rowNo+1, colNo+14), "도");
		getCell(sheet, rowNo+1, colNo+14).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+15), "분");
		getCell(sheet, rowNo+1, colNo+15).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+16), "초");
		getCell(sheet, rowNo+1, colNo+16).setCellStyle(HeaderStyle4_b);
		
		setText(getCell(sheet, rowNo+1, colNo+17), "도");
		getCell(sheet, rowNo+1, colNo+17).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+18), "분");
		getCell(sheet, rowNo+1, colNo+18).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+19), "초");
		getCell(sheet, rowNo+1, colNo+19).setCellStyle(HeaderStyle4_b);
		
		setText(getCell(sheet, rowNo+1, colNo+20), "도");
		getCell(sheet, rowNo+1, colNo+20).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+21), "분");
		getCell(sheet, rowNo+1, colNo+21).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+22), "초");
		getCell(sheet, rowNo+1, colNo+22).setCellStyle(HeaderStyle4_b);
		
		setText(getCell(sheet, rowNo+1, colNo+23), "도");
		getCell(sheet, rowNo+1, colNo+23).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+24), "분");
		getCell(sheet, rowNo+1, colNo+24).setCellStyle(HeaderStyle4_b);
		setText(getCell(sheet, rowNo+1, colNo+25), "초");
		getCell(sheet, rowNo+1, colNo+25).setCellStyle(HeaderStyle4_b);
		
		setText(getCell(sheet, rowNo+1, colNo+26), "속칭");
		getCell(sheet, rowNo+1, colNo+26).setCellStyle(HeaderStyle5_b);
		setText(getCell(sheet, rowNo+1, colNo+27), "예정거리(km)");
		getCell(sheet, rowNo+1, colNo+27).setCellStyle(HeaderStyle5_b);
		setText(getCell(sheet, rowNo+1, colNo+28), "임도연장(km)");
		getCell(sheet, rowNo+1, colNo+28).setCellStyle(HeaderStyle5_b);
		
		setText(getCell(sheet, rowNo+1, colNo+29), "일반현황");
		getCell(sheet, rowNo+1, colNo+29).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+30), "종단기울기");
		getCell(sheet, rowNo+1, colNo+30).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+31), "산지경사");
		getCell(sheet, rowNo+1, colNo+31).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+32), "사면현황");
		getCell(sheet, rowNo+1, colNo+32).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+33), "대상지 내 암반");
		getCell(sheet, rowNo+1, colNo+33).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+34), "대상지 내 사면침식현황");
		getCell(sheet, rowNo+1, colNo+34).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+35), "대상지 계류현황");
		getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+36), "계류 내 침식현황");
		getCell(sheet, rowNo+1, colNo+36).setCellStyle(HeaderStyle6_b);
		setText(getCell(sheet, rowNo+1, colNo+37), "기타(용출수, 묘지 등)");
		getCell(sheet, rowNo+1, colNo+37).setCellStyle(HeaderStyle6_b);
		
		
		rowNo = 2;
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frdrnk").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frdtype").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysd").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysgg").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyemd").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyri").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyjibun").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				// 유림
				String forestType = "";
				if(dataList.get(i).get("compentauth") != null) {
					String compentauth = dataList.get(i).get("compentauth").toString();
					if(compentauth.contains("부청")) {
						// 국유림
						forestType = "국유림";
					}else {
						// 민유림
						forestType = "민유림";
					}
				}
				setText(getCell(sheet, rowNo, colNo),forestType);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("compentauth").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("subcompentauth").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo, colNo),"한국치산기술협회");
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("svyuser").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("svydt").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				
				
				DmsFormalization dmsformal = new DmsFormalization();
				
				String bpy = dataList.get(i).get("bpy").toString();
				String bpx = dataList.get(i).get("bpx").toString();
				
				String epy = dataList.get(i).get("epy").toString();
				String epx = dataList.get(i).get("epx").toString();
				
				String bpyD = "";
				String bpyM = "";
				String bpyS = "";
				
				String bpxD = "";
				String bpxM = "";
				String bpxS = "";
				
				String epyD = "";
				String epyM = "";
				String epyS = "";
				
				String epxD = "";
				String epxM = "";
				String epxS = "";
				
				String bp = bpy+" "+bpx;
				String ep = epy+" "+epx;
				
				pattern = Pattern.compile(regex);
				
				Matcher matcherBp = pattern.matcher(bp);
				Matcher matcherEp1 = pattern.matcher(ep);
				
				while (matcherBp.find()) {
				    bpyD = matcherBp.group(1);
				    bpyM = matcherBp.group(2);
				    bpyS = matcherBp.group(3);
				    
				    bpxD = matcherBp.group(5);
				    bpxM = matcherBp.group(6);
				    bpxS = matcherBp.group(7);
				    
				    if(bpyD != null && !bpyD.equals("")) {
				    	dmsformal.setDmsLatDeg(bpyD);
				    	bpyD = dmsformal.getDmsLatDeg();
				    }
				    if(bpyM != null && !bpyM.equals("")) {
				    	dmsformal.setDmsLatMin(bpyM);
				    	bpyM = dmsformal.getDmsLatMin();
				    }
				    if(bpyS != null && !bpyS.equals("")) {
				    	dmsformal.setDmsLatSec(bpyS);
				    	bpyS = dmsformal.getDmsLatSec();
				    }
				    if(bpxD != null && !bpxD.equals("")) {
				    	dmsformal.setDmsLonDeg(bpxD);
				    	bpxD = dmsformal.getDmsLonDeg();
				    }
				    if(bpxM != null && !bpxM.equals("")) {
				    	dmsformal.setDmsLonMin(bpxM);
				    	bpxM = dmsformal.getDmsLonMin();
				    }
				    if(bpxS != null && !bpxS.equals("")) {
				    	dmsformal.setDmsLonSec(bpxS);
				    	bpxS = dmsformal.getDmsLonSec();
				    }
				}
				
				while (matcherEp1.find()) {
					String epy1D = matcherEp1.group(1);
					String epy1M = matcherEp1.group(2);
					String epy1S = matcherEp1.group(3);
					
					String epx1D = matcherEp1.group(5);
					String epx1M = matcherEp1.group(6);
					String epx1S = matcherEp1.group(7);
					
					if(epy1D != null && !epy1D.equals("")) {
						dmsformal.setDmsLatDeg(epy1D);
						epyD = dmsformal.getDmsLatDeg();
					}
					if(epy1M != null && !epy1M.equals("")) {
						dmsformal.setDmsLatMin(epy1M);
						epyM = dmsformal.getDmsLatMin();
					}
					if(epy1S != null && !epy1S.equals("")) {
						dmsformal.setDmsLatSec(epy1S);
						epyS = dmsformal.getDmsLatSec();
					}
					if(epx1D != null && !epx1D.equals("")) {
						dmsformal.setDmsLonDeg(epx1D);
						epxD = dmsformal.getDmsLonDeg();
					}
					if(epx1M != null && !epx1M.equals("")) {
						dmsformal.setDmsLonMin(epx1M);
						epxM = dmsformal.getDmsLonMin();
					}
					if(epx1S != null && !epx1S.equals("")) {
						dmsformal.setDmsLonSec(epx1S);
						epxS = dmsformal.getDmsLonSec();
					}
				}
				
				setText(getCell(sheet, rowNo, colNo),bpxD);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),bpxM);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),bpxS);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo),bpyD);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),bpyM);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),bpyS);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo),epxD);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),epxM);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),epxS);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo),epyD);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),epyM);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				setText(getCell(sheet, rowNo, colNo),epyS);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("commonly").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("schdst").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("frdextns").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("gnrlstts").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("lonslope").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("mtslope").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("slopestatus").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("destrock").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("destersn").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("destmrngstat").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("mrngersnstat").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				setText(getCell(sheet, rowNo, colNo),dataList.get(i).get("etc").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				
				// 보호시설
				String safeFctList = dataList.get(i).get("safefctlist").toString();
				if(!safeFctList.equals("[]") && safeFctList != null && !safeFctList.equals("")) {
					buildSheetSafeFct(model, wb, req, resp);
				}
				
				// 종단기울기
				String lonSlopeList = dataList.get(i).get("lonslopelist").toString();
				if(!lonSlopeList.equals("[]") && lonSlopeList != null && !lonSlopeList.equals("")) {
					buildSheetLonSlope(model, wb, req, resp);
				}
				
				// 산지경사
				String mtSlopeList = dataList.get(i).get("mtslopelist").toString();
				if(!mtSlopeList.equals("[]") && mtSlopeList != null && !mtSlopeList.equals("")) {
					buildSheetMtSlope(model, wb, req, resp);
				}
				
				// 암반노출
				String rockExposList = dataList.get(i).get("rockexposlist").toString();
				if(!rockExposList.equals("[]") && rockExposList != null && !rockExposList.equals("")) {
					buildSheetRockExpos(model, wb, req, resp);
				}
				
				// 조림지
				String afrstList = dataList.get(i).get("afrstlist").toString();
				if(!afrstList.equals("[]") && afrstList != null && !afrstList.equals("")) {
					buildSheetAfrst(model, wb, req, resp);
				}
				
				// 벌채지
				String cuttingList = dataList.get(i).get("cuttinglist").toString();
				if(!cuttingList.equals("[]") && cuttingList != null && !cuttingList.equals("")) {
					buildSheetCutting(model, wb, req, resp);
				}
				
				// 석력지
				String stmiList = dataList.get(i).get("stmilist").toString();
				if(!stmiList.equals("[]") && stmiList != null && !stmiList.equals("")) {
					buildSheetStmi(model, wb, req, resp);
				}
				
				// 계류종류 및 현황
				String mrngkind = dataList.get(i).get("mrngkind").toString();
				if(!mrngkind.equals("[]") && mrngkind != null && !mrngkind.equals("")) {
					buildSheetMrngkind(model, wb, req, resp);
				}
				
				// 습지
				String wetLandList = dataList.get(i).get("wetlandlist").toString();
				if(!wetLandList.equals("[]") && wetLandList != null && !wetLandList.equals("")) {
					buildSheetWetLand(model, wb, req, resp);
				}
				
				// 묘지
				String cmtryList = dataList.get(i).get("cmtrylist").toString();
				if(!cmtryList.equals("[]") && cmtryList != null && !cmtryList.equals("")) {
					buildSheetCmtry(model, wb, req, resp);
				}
				
				// 용출수
				String eltnWaterList = dataList.get(i).get("eltnwaterlist").toString();
				if(!eltnWaterList.equals("[]") && eltnWaterList != null && !eltnWaterList.equals("")) {
					buildSheetEltnwater(model, wb, req, resp);
				}
				
				// 연약지반
				String sofrtgrndList = dataList.get(i).get("sofrtgrndlist").toString();
				if(!sofrtgrndList.equals("[]") && sofrtgrndList != null && !sofrtgrndList.equals("")) {
					buildSheetSofrtgrnd(model, wb, req, resp);
				}
				
				// 붕괴우려지역
				String clpscnrnList = dataList.get(i).get("clpscnrnlist").toString();
				if(!clpscnrnList.equals("[]") && clpscnrnList != null && !clpscnrnList.equals("")) {
					buildSheetClpscnrn(model, wb, req, resp);
				}
				
				// 주요수종
				String maintreekndList = dataList.get(i).get("maintreekndlist").toString();
				if(!maintreekndList.equals("[]") && maintreekndList != null && !maintreekndList.equals("")) {
					buildSheetMaintreeknd(model, wb, req, resp);
				}
				
				// 주요식생
				String mainvegList = dataList.get(i).get("mainveglist").toString();
				if(!mainvegList.equals("[]") && mainvegList != null && !mainvegList.equals("")) {
					buildSheetMainveg(model, wb, req, resp);
				}
				
				// 상수원 오염
				String wtrpltnList = dataList.get(i).get("wtrpltnlist").toString();
				if(!wtrpltnList.equals("[]") && wtrpltnList != null && !wtrpltnList.equals("")) {
					buildSheetWtrPltn(model, wb, req, resp);
				}
				
				// 훼손우려지
				String dmgcncrnList = dataList.get(i).get("dmgcncrnlist").toString();
				if(!dmgcncrnList.equals("[]") && dmgcncrnList != null && !dmgcncrnList.equals("")) {
					buildSheetDmgcncrn(model, wb, req, resp);
				}
				
				// 산림재해
				String frstDsstrList = dataList.get(i).get("frstdsstrlist").toString();
				if(!frstDsstrList.equals("[]") && frstDsstrList != null && !frstDsstrList.equals("")) {
					buildSheetFrstDsstr(model, wb, req, resp);
				}
				
				// 야생동물
				String wildAnmlList = dataList.get(i).get("wildanmllist").toString();
				if(!wildAnmlList.equals("[]") && wildAnmlList != null && !wildAnmlList.equals("")) {
					buildSheetWildAnml(model, wb, req, resp);
				}
				
				// 사방시설설치
				String ecnrFcltyInstlList = dataList.get(i).get("ecnrfcltyinstllist").toString();
				if(!ecnrFcltyInstlList.equals("[]") && ecnrFcltyInstlList != null && !ecnrFcltyInstlList.equals("")) {
					buildSheetEcnrFcltyInstl(model, wb, req, resp);
				}
				
				// 사방시설 필요
				String ecnrFcltyNcstyList = dataList.get(i).get("ecnrfcltyncstylist").toString();
				if(!ecnrFcltyNcstyList.equals("[]") && ecnrFcltyNcstyList != null && !ecnrFcltyNcstyList.equals("")) {
					buildSheetEcnrFcltyNcstyList(model, wb, req, resp);
				}
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
	protected static XSSFCell getCell(XSSFSheet sheet, int row, int col) {
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
	protected static void setText(XSSFCell cell, String text) {
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
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
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle2(XSSFWorkbook wb) {
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

		return cellStyle;
	}

	public static XSSFCellStyle getHeaderCellStyle3(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(172, 185, 202)));
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

	public static XSSFCellStyle getHeaderCellStyle4(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(252, 228, 214)));
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

	public static XSSFCellStyle getHeaderCellStyle5(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 242, 204)));
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

	public static XSSFCellStyle getHeaderCellStyle6(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(189, 215, 238)));
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

	public static XSSFCellStyle getHeaderCellStyle7(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(166, 166, 166)));
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
	
	public static XSSFCellStyle getHeaderCellStyle8(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(208, 206, 206)));
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
	
	public static XSSFCellStyle getHeaderCellStyle9(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(217, 225, 242)));
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
	
	public static XSSFCellStyle getHeaderCellStyle10(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 242, 204)));
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
	
	public static XSSFCellStyle getHeaderCellStyle11(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(226, 239, 218)));
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
	
	public static XSSFCellStyle getHeaderCellStyle12(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(214, 220, 228)));
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
	
	public static XSSFCellStyle getHeaderCellStyle13(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(252, 228, 214)));
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
	
	public static XSSFCellStyle getHeaderCellStyle14(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(237, 237, 237)));
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
	
	public static XSSFCellStyle getHeaderCellStyle15(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 242, 204)));
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
	
	public static XSSFCellStyle getHeaderCellStyle16(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(221, 235, 247)));
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
	
	public static XSSFCellStyle getHeaderCellStyle17(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(226, 239, 218)));
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
	
	/**
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle1_b(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);
        
		return cellStyle;
	}

	/**
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle2_b(XSSFWorkbook wb) {
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
		
		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);

		return cellStyle;
	}

	public static XSSFCellStyle getHeaderCellStyle3_b(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(172, 185, 202)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);

		return cellStyle;
	}

	public static XSSFCellStyle getHeaderCellStyle4_b(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(252, 228, 214)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);

		return cellStyle;
	}

	public static XSSFCellStyle getHeaderCellStyle5_b(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 242, 204)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);
        
		return cellStyle;
	}

	public static XSSFCellStyle getHeaderCellStyle6_b(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(189, 215, 238)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);

		return cellStyle;
	}

	public static XSSFCellStyle getHeaderCellStyle7_b(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		// cellStyle.setWrapText(true); // 줄 바꿈
		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 셀 색상
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(166, 166, 166)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		Font boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);
		
		return cellStyle;
	}
	
	/**
	 * DEGREE to DMS
	 */
	public static String degree2Dms(double coord) {
		String dms = "";
		
		if (!Double.isNaN(coord)) {
            int d = (int) Math.floor(coord);
            int m = (int) Math.floor((coord - d) * 60);
            double s = (((coord - d) * 60) - Math.floor((coord - d) * 60)) * 60;

            dms = d + "°" + m + "'" + String.format("%.2f", s) + "\"";
        }
        return dms;
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 보호시설 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetSafeFct(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 보호시설
				String safeFctList = dataList.get(i).get("safefctlist").toString();
				if(!safeFctList.equals("[]") && safeFctList != null && !safeFctList.equals("")) {
					// 보호시설 시트 생성
					XSSFSheet newSheet = wb.createSheet("보호시설");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
		
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(safeFctList.toString());
					
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 보호시설 key -> 경도, 위도, 유형
				        String lon = jsonNodeMap.get(j).get("경도").asText();
				        String lat = jsonNodeMap.get(j).get("위도").asText();
				        String type = jsonNodeMap.get(j).get("유형").asText();
				      
				        ListMap.put("type"+(j+1), type);
				        
				        Point2D pt2d = new Point2D(Double.parseDouble(lon),Double.parseDouble(lat));
				        Geometry geom = new GeoPoint(pt2d);
				        
				        CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
				        
				        String convertLon = degree2Dms(geom.getInnerPoint().x);
				        String convertLat = degree2Dms(geom.getInnerPoint().y);
				        
				        ListMap.put("lon"+(j+1), convertLon);
				        ListMap.put("lat"+(j+1), convertLat);
				        
				        // degree to dms
				        String lonLat = convertLat+"N"+" "+convertLon+"E";
						pattern = Pattern.compile(regex);
						Matcher matcherLonLat = pattern.matcher(lonLat);
				        
						while (matcherLonLat.find()) {
						    String latD = matcherLonLat.group(1);
						    String latM = matcherLonLat.group(2);
						    String latS = matcherLonLat.group(3);
						    
						    String lonD = matcherLonLat.group(5);
						    String lonM = matcherLonLat.group(6);
						    String lonS = matcherLonLat.group(7);
						    
						    if(latD != null && !latD.equals("")) {
						    	dmsformal.setDmsLatDeg(latD);
						    	ListMap.put("latD"+(j+1), dmsformal.getDmsLatDeg());
						    }
						    
						    if(latM != null && !latM.equals("")) {
						    	dmsformal.setDmsLatMin(latM);
						    	ListMap.put("latM"+(j+1), dmsformal.getDmsLatMin());
						    }
						    
						    if(latS != null && !latS.equals("")) {
						    	dmsformal.setDmsLatSec(latS);
						    	ListMap.put("latS"+(j+1), dmsformal.getDmsLatSec());
						    }
						    
						    if(lonD != null && !lonD.equals("")) {
						    	dmsformal.setDmsLonDeg(lonD);
						    	ListMap.put("lonD"+(j+1), dmsformal.getDmsLonDeg());
						    }
						    
						    if(lonM != null && !lonM.equals("")) {
						    	dmsformal.setDmsLonMin(lonM);
						    	ListMap.put("lonM"+(j+1), dmsformal.getDmsLonMin());
						    }
						    
						    if(lonS != null && !lonS.equals("")) {
						    	dmsformal.setDmsLonSec(lonS);
						    	ListMap.put("lonS"+(j+1), dmsformal.getDmsLonSec());
						    }
						}
						
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
			        setText(getCell(newSheet, rowNo, colNo+0), "보호시설");
			        for(int k=0; k<=colNo+5+(contentCnt*8); k++){
			        	getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
			        }
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+5+(contentCnt*8)));
				
					setText(getCell(newSheet, rowNo+1, colNo+0), "보호시설");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("safefct").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "전답");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), dataList.get(i).get("field").toString());
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					setText(getCell(newSheet, rowNo+1, colNo+4), "접근성");
					getCell(newSheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+4).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+4, colNo+4));
					
					setText(getCell(newSheet, rowNo+1, colNo+5), dataList.get(i).get("acsbl").toString());
					getCell(newSheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+5).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+5, colNo+5));
					
					int initialCol = 6;  // 시작 열
					int colStep = 8;    // 컬럼 증가량
		
					for (int k=0; k < contentCnt; k++) {
					    int startCol = colNo + initialCol + (colStep * k);
					    int endCol = startCol + 7;
		
					    setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 보호시설");
					    for (int m=0; m<7; m++) {
					        getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
					    }
					    newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
					    
					    setText(getCell(newSheet, rowNo+2, startCol), "유형");
					    for(int m=2; m<=4; m++) {
					    	getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
					    }
					    newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
					    
					    setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
					    for(int m=2; m<=4; m++) {
					    	getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
					    }
					    newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
					    
					    setText(getCell(newSheet, rowNo+2, startCol+2), "위도");
					    for (int m=0; m<3; m++) {
					        getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
					    }
					    newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
					    
					    setText(getCell(newSheet, rowNo+2, startCol+5), "경도");
					    for (int m=0; m<3; m++) {
					    	getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
					    }
					    newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
					    
					    setText(getCell(newSheet, rowNo+3, startCol+2), "도");
				        getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
					    setText(getCell(newSheet, rowNo+3, startCol+3), "분");
					    getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
					    setText(getCell(newSheet, rowNo+3, startCol+4), "초");
					    getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
					    
					    setText(getCell(newSheet, rowNo+3, startCol+5), "도");
					    getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
					    setText(getCell(newSheet, rowNo+3, startCol+6), "분");
					    getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
					    setText(getCell(newSheet, rowNo+3, startCol+7), "초");
					    getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
					    
					    setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
					    
					    setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 종단기울기 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetLonSlope(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 종단기울기
				String lonSlopeList = dataList.get(i).get("lonslopelist").toString();
				if(!lonSlopeList.equals("[]") && lonSlopeList != null && !lonSlopeList.equals("")) {
					// 종단기울기 시트 생성
					XSSFSheet newSheet = wb.createSheet("종단기울기");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(lonSlopeList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 종단기울기 key -> 위도1, 경도1, 위도2, 경도2, 기울기, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("기울기").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
						
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
						
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "종단기울기");
					for(int k=0; k<=colNo+5+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+5+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "최소(%)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("lonslopemin").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "최대(%)");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), dataList.get(i).get("lonslopemax").toString());
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					setText(getCell(newSheet, rowNo+1, colNo+4), "평균(%)");
					getCell(newSheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+4).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+4, colNo+4));
					
					setText(getCell(newSheet, rowNo+1, colNo+5), dataList.get(i).get("lonslopeavg").toString());
					getCell(newSheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+5).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+5, colNo+5));
					
					int initialCol = 6;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 종단기울기");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "기울기(%)");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						
						setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산지경사 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetMtSlope(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 산지경사
				String mtSlopeList = dataList.get(i).get("mtslopelist").toString();
				if(!mtSlopeList.equals("[]") && mtSlopeList != null && !mtSlopeList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("산지경사");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(mtSlopeList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 산지경사 key -> 위도1, 경도1, 위도2, 경도2, 경사도, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("경사도").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "산지경사");
					for(int k=0; k<=colNo+5+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+5+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "최소(°)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("mtslopemin").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "최대(°)");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), dataList.get(i).get("mtslopemax").toString());
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					setText(getCell(newSheet, rowNo+1, colNo+4), "평균(°)");
					getCell(newSheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+4).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+4, colNo+4));
					
					setText(getCell(newSheet, rowNo+1, colNo+5), dataList.get(i).get("mtslopeavg").toString());
					getCell(newSheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+5).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+5, colNo+5));
					
					int initialCol = 6;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 산지경사");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "경사(%)");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						
						setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 암반노출 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetRockExpos(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 암반노출
				String rockExposList = dataList.get(i).get("rockexposlist").toString();
				if(!rockExposList.equals("[]") && rockExposList != null && !rockExposList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("암반노출");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(rockExposList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					double rockExposTotal = 0.0;
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 암반노출 key -> 위도1, 경도1, 위도2, 경도2, 암반노출, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("암반노출").asText();
						
						ListMap.put("type"+(j+1), type);
						
						rockExposTotal += Double.parseDouble(type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "암반노출");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "암반 전체 노출(m)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), Double.toString(rockExposTotal));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 암반노출");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "암반노출(m)");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						
						setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 조림지 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetAfrst(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 조림지
				String afrstList = dataList.get(i).get("afrstlist").toString();
				if(!afrstList.equals("[]") && afrstList != null && !afrstList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("조림지");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(afrstList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 조림지 key -> 위도1, 경도1, 위도2, 경도2, 조림지, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("조림지").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "조림지");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("afrstat").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 조림지");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "조림지");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						
						setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 벌채지 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetCutting(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 벌채지
				String cuttingList = dataList.get(i).get("cuttinglist").toString();
				if(!cuttingList.equals("[]") && cuttingList != null && !cuttingList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("벌채지");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(cuttingList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 벌채지 key -> 위도1, 경도1, 위도2, 경도2, 벌채지, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("벌채지").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "벌채지");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("cuttingat").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 벌채지");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "벌채지");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						
						setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 석력지 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetStmi(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 석력지
				String stmilist = dataList.get(i).get("stmilist").toString();
				if(!stmilist.equals("[]") && stmilist != null && !stmilist.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("석력지");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(stmilist.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					String stmiTotalAt = "무"; // 석력지 유무는 야장에서 따로 넘어오지 않기때문에 체크해서 넘겨줌.
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 석력지 key -> 위도1, 경도1, 위도2, 경도2, 석력지, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("석력지").asText();
						
						ListMap.put("type"+(j+1), type);
						
						if(type != null && !type.equals("")) {	stmiTotalAt = "유";	}
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "석력지");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), stmiTotalAt);
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 석력지");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "석력지");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						
						setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 계류종류 및 현황 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetMrngkind(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 계류종류 및 현황
				String mrngKindList = dataList.get(i).get("mrngkind").toString();
				if(!mrngKindList.equals("[]") && mrngKindList != null && !mrngKindList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("계류종류 및 현황");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(mrngKindList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					// 대계류 합계
					int bigMrng1 = 0, bigMrng2 = 0, bigMrng3 = 0;
					
					// 중계류 합계
					int middleMrng1 = 0, middleMrng2 = 0, middleMrng3 = 0;
					
					// 소계류 합계
					int smallMrng1 = 0, smallMrng2 = 0, smallMrng3 = 0;
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 암반노출 key -> 위도1, 경도1, 위도2, 경도2, 암반노출, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String bigMrng = jsonNodeMap.get(j).get("대분류").asText();
						String smallMrng = jsonNodeMap.get(j).get("소분류").asText();
						
						ListMap.put("bigMrng"+(j+1), bigMrng);
						ListMap.put("smallMrng"+(j+1), smallMrng);
						
						if(bigMrng.equals("대계류")) {
							bigMrng1 += 1;
							
							if(smallMrng.equals("상시천")) {
								bigMrng2 += 1;
							}else if(smallMrng.equals("건천")) {
								bigMrng3 += 1;
							}
							
						}else if(bigMrng.equals("중계류")) {
							middleMrng1 += 1;
							
							if(smallMrng.equals("상시천")) {
								middleMrng2 += 1;
							}else if(smallMrng.equals("건천")) {
								middleMrng3 += 1;
							}
						}else if(bigMrng.equals("소계류")) {
							smallMrng1 += 1;
							
							if(smallMrng.equals("상시천")) {
								smallMrng2 += 1;
							}else if(smallMrng.equals("건천")) {
								smallMrng3 += 1;
							}
						}
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					String bigMrngTotal = Integer.toString(bigMrng1)+"/"+Integer.toString(bigMrng2)+"/"+Integer.toString(bigMrng3);
					String middleMrngTotal = Integer.toString(middleMrng1)+"/"+Integer.toString(middleMrng2)+"/"+Integer.toString(middleMrng3);
					String smallMrngTotal = Integer.toString(smallMrng1)+"/"+Integer.toString(smallMrng2)+"/"+Integer.toString(smallMrng3);
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "계류종류 및 현황");
					for(int k=0; k<=colNo+5+(contentCnt*10); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+5+(contentCnt*10)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "대계류");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), bigMrngTotal);
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "중계류");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), middleMrngTotal);
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					setText(getCell(newSheet, rowNo+1, colNo+4), "소계류");
					getCell(newSheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+4).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+4, colNo+4));
					
					setText(getCell(newSheet, rowNo+1, colNo+5), smallMrngTotal);
					getCell(newSheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+5).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+5, colNo+5));
					
					int initialCol = 6;  // 시작 열
					int colStep = 10;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 9;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 계류종류 및 현황");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "대분류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("bigMrng"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "소분류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+2, startCol+2));
						
						setText(getCell(newSheet, rowNo+2, startCol+3), ListMap.get("smallMrng"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+3).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+3, startCol+3));
						
						setText(getCell(newSheet, rowNo+2, startCol+4), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+4).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+4, startCol+6));
						
						setText(getCell(newSheet, rowNo+2, startCol+7), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+7).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+7, startCol+9));
						
						setText(getCell(newSheet, rowNo+3, startCol+4), "도");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+5), "분");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "초");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+7), "도");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+8), "분");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "초");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 습지 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetWetLand(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 습지
				String wetLandList = dataList.get(i).get("wetlandlist").toString();
				if(!wetLandList.equals("[]") && wetLandList != null && !wetLandList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("습지");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(wetLandList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 습지 key -> 위도1, 경도1, 위도2, 경도2, 습지, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("습지").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "습지");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("wetlandat").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 습지");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "습지");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
				        
				        setText(getCell(newSheet, rowNo+3, startCol+2), "도");
				        getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+3), "분");
				        getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+4), "초");
				        getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+5), "도");
				        getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+6), "분");
				        getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+7), "초");
				        getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+8), "도");
				        getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+9), "분");
				        getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+10), "초");
				        getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+11), "도");
				        getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+12), "분");
				        getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+13), "초");
				        getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);

				        setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
				        
				        setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
						
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 묘지 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetCmtry(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 묘지
				String cmtryList = dataList.get(i).get("cmtrylist").toString();
				if(!cmtryList.equals("[]") && cmtryList != null && !cmtryList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("묘지");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(cmtryList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					// 묘지 위치
					int cmtryLoc1 = 0, cmtryLoc2 = 0;
					
					// 묘지 관리
					int cmtryMng1 = 0, cmtryMng2 = 0;
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 묘지 key -> 위도1, 경도1, 위도2, 경도2, 묘지_유무, 묘지_위치, 묘지_관리, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String cmtryAt = jsonNodeMap.get(i).get("묘지_유무").asText();
						String cmtryLoc = jsonNodeMap.get(i).get("묘지_위치").asText();
						String cmtryMng = jsonNodeMap.get(i).get("묘지_관리").asText();
						
						ListMap.put("cmtryLoc"+(j+1), cmtryLoc);
						ListMap.put("cmtryMng"+(j+1), cmtryMng);
						
						if(cmtryLoc.equals("노선 상부")) {
							cmtryLoc1 += 1;
						}else if(cmtryLoc.equals("노선 하부")) {
							cmtryLoc2 += 1;
						}
						
						if(cmtryMng.equals("관리O")) {
							cmtryMng1 += 1;
						}else if(cmtryMng.equals("관리X")) {
							cmtryMng2 += 1;
						}
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					String cmtryLocTotal = Integer.toString(cmtryLoc1)+"/"+Integer.toString(cmtryLoc2);
					String cmtryMngTotal = Integer.toString(cmtryMng1)+"/"+Integer.toString(cmtryMng2);
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "묘지");
					for(int k=0; k<=colNo+5+(contentCnt*12); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+5+(contentCnt*10)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "묘지 개수");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), Integer.toString(jsonNodeMap.size()));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "묘지 위치");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), cmtryLocTotal);
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					setText(getCell(newSheet, rowNo+1, colNo+4), "묘지 관리");
					getCell(newSheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+4).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+4, colNo+4));
					
					setText(getCell(newSheet, rowNo+1, colNo+5), cmtryMngTotal);
					getCell(newSheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+5).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+5, colNo+5));
					
					int initialCol = 6;  // 시작 열
					int colStep = 12;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 11;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 묘지");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "묘지 유무");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), "유");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "묘지 위치");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+2, startCol+2));
						
						setText(getCell(newSheet, rowNo+2, startCol+3), ListMap.get("cmtryLoc"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+3).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+3, startCol+3));
						
						setText(getCell(newSheet, rowNo+2, startCol+4), "묘지 관리");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+4).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+4, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), ListMap.get("cmtryMng"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+5).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+5, startCol+5));
						
						setText(getCell(newSheet, rowNo+2, startCol+6), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+6).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+6, startCol+8));
						
						setText(getCell(newSheet, rowNo+2, startCol+9), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+9).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+9, startCol+11));
						
						setText(getCell(newSheet, rowNo+3, startCol+6), "도");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "분");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+8), "초");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+9), "도");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+10), "분");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+11), "초");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 용출수 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetEltnwater(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 용출수
				String eltnWaterList = dataList.get(i).get("eltnwaterlist").toString();
				if(!eltnWaterList.equals("[]") && eltnWaterList != null && !eltnWaterList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("용출수");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(eltnWaterList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 용출수 key -> 위도1, 경도1, 위도2, 경도2, 용출수, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String type = jsonNodeMap.get(j).get("용출수").asText();
						
						ListMap.put("type"+(j+1), type);
						
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "용출수");
					for(int k=0; k<=colNo+1+(contentCnt*8); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*8)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("eltnwaterat").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 8;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 7;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 용출수");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "용출수");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 암반노출 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetSofrtgrnd(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 연약지반
				String sofrtGrndList = dataList.get(i).get("sofrtgrndlist").toString();
				if(!sofrtGrndList.equals("[]") && sofrtGrndList != null && !sofrtGrndList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("연약지반");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(sofrtGrndList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 연약지반 key -> 위도1, 경도1, 위도2, 경도2, 연약지반, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("연약지반").asText();
						
						ListMap.put("type"+(j+1), type);
						
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "연약지반");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*8)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), "유");
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 연약지반");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "연약지반");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
				        setText(getCell(newSheet, rowNo+3, startCol+8), "도");
				        getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+9), "분");
				        getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+10), "초");
				        getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+11), "도");
				        getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+12), "분");
				        getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+13), "초");
				        getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
				        setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
				        
				        setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
						
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 붕괴우려지역 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetClpscnrn(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 붕괴우려지역
				String clpsCnrnList = dataList.get(i).get("clpscnrnlist").toString();
				if(!clpsCnrnList.equals("[]") && clpsCnrnList != null && !clpsCnrnList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("붕괴우려지역");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(clpsCnrnList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					// 붕괴우려 사면
					int slope1 = 0, slope2 = 0, slope3 = 0, slope4 = 0, slope5 = 0;
					
					// 붕괴우려 계류
					int mtTrnt1 = 0, mtTrnt2 = 0, mtTrnt3 = 0, mtTrnt4 = 0, mtTrnt5 = 0;
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 붕괴우려지역 key -> 위도1, 경도1, 위도2, 경도2, 붕괴우려_대분류, 붕괴우려_소분류, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String clpsCnrnBig = jsonNodeMap.get(i).get("붕괴우려_대분류").asText();
						String clpsCnrnSmall = jsonNodeMap.get(i).get("붕괴우려_소분류").asText();
						
						ListMap.put("clpsCnrnBig"+(j+1), clpsCnrnBig);
						ListMap.put("clpsCnrnSmall"+(j+1), clpsCnrnSmall);
						
						if(clpsCnrnBig.equals("사면")) {
							slope1 += 1;
							
							if(clpsCnrnSmall.equals("침식")) {
								slope2 += 1;
							}else if(clpsCnrnSmall.equals("붕괴")) {
								slope3 += 1;
							}else if(clpsCnrnSmall.equals("포락")) {
								slope4 += 1;
							}else{
								slope5 += 1;
							}
							
						}else if(clpsCnrnBig.equals("계류")) {
							mtTrnt1 += 1;
							
							if(clpsCnrnSmall.equals("침식")) {
								mtTrnt2 += 1;
							}else if(clpsCnrnSmall.equals("붕괴")) {
								mtTrnt3 += 1;
							}else if(clpsCnrnSmall.equals("포락")) {
								mtTrnt4 += 1;
							}else{
								mtTrnt5 += 1;
							}
						}
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					String slopeTotal = Integer.toString(slope1)+"/"+Integer.toString(slope2)+"/"+Integer.toString(slope3)+"/"+Integer.toString(slope4)+"/"+Integer.toString(slope5);
					String mtTrntTotal = Integer.toString(mtTrnt1)+"/"+Integer.toString(mtTrnt2)+"/"+Integer.toString(mtTrnt3)+"/"+Integer.toString(mtTrnt4)+"/"+Integer.toString(mtTrnt5);
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "붕괴우려지역");
					for(int k=0; k<=colNo+3+(contentCnt*16); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+3+(contentCnt*16)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "사면");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), slopeTotal);
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "계류");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), mtTrntTotal);
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					int initialCol = 4;  // 시작 열
					int colStep = 16;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 15;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 붕괴우려지역");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "대분류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("clpsCnrnBig"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "소분류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+2, startCol+2));
						
						setText(getCell(newSheet, rowNo+2, startCol+3), ListMap.get("clpsCnrnSmall"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+3).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+3, startCol+3));
						
						setText(getCell(newSheet, rowNo+2, startCol+4), "위도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+4).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+4, startCol+6));
						
						setText(getCell(newSheet, rowNo+2, startCol+7), "경도1");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+7).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+7, startCol+9));
						
						setText(getCell(newSheet, rowNo+2, startCol+10), "위도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+10).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+10, startCol+12));
						
						setText(getCell(newSheet, rowNo+2, startCol+13), "경도2");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+13).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+13, startCol+15));
						
						setText(getCell(newSheet, rowNo+3, startCol+4), "도");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+5), "분");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "초");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+7), "도");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+8), "분");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "초");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+10), "도");
						getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+11), "분");
						getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+12), "초");
						getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+13), "도");
						getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+14), "분");
						getCell(newSheet, rowNo+3, startCol+14).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+15), "초");
						getCell(newSheet, rowNo+3, startCol+15).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						
					    setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latD2_"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("latM2_"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("latS2_"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
					    
					    setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonD2_"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+14), ListMap.get("lonM2_"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+14).setCellStyle(HeaderStyle1);
					    setText(getCell(newSheet, rowNo+4, startCol+15), ListMap.get("lonS2_"+(k+1)));
					    getCell(newSheet, rowNo+4, startCol+15).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 주요수종 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetMaintreeknd(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 주요수종
				String maintreekndList = dataList.get(i).get("maintreekndlist").toString();
				if(!maintreekndList.equals("[]") && maintreekndList != null && !maintreekndList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("주요수종");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(maintreekndList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 주요수종 key -> 위도1, 경도1, 위도2, 경도2, 주요수종, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String type = jsonNodeMap.get(j).get("주요수종").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "주요수종");
					for(int k=0; k<=colNo+1+(contentCnt*8); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*8)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "건수");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("maintreekndcnt").toString().concat("건"));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 8;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 7;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 주요수종");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "주요수종");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 주요식생 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetMainveg(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 주요식생
				String mainvegList = dataList.get(i).get("mainveglist").toString();
				if(!mainvegList.equals("[]") && mainvegList != null && !mainvegList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("주요식생");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(mainvegList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 주요식생 key -> 위도1, 경도1, 위도2, 경도2, 주요식생, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String type = jsonNodeMap.get(j).get("주요식생").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "주요식생");
					for(int k=0; k<=colNo+1+(contentCnt*8); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*8)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "건수");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("mainvegcnt").toString().concat("건"));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 8;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 7;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 주요식생");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "주요식생");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
					}
				}
				
				
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 상수원오염 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetWtrPltn(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 상수원오염
				String wtrPltnList = dataList.get(i).get("wtrpltnlist").toString();
				if(!wtrPltnList.equals("[]") && wtrPltnList != null && !wtrPltnList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("상수원오염");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(wtrPltnList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 상수원 오염 key -> 위도1, 경도1, 위도2, 경도2, 상수원오염, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String type = jsonNodeMap.get(j).get("상수원오염").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "상수원오염");
					for(int k=0; k<=colNo+1+(contentCnt*8); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*8)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("wtrpltnat").toString());
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 8;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 7;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 상수원오염");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "상수원오염");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						
						setText(getCell(newSheet, rowNo+2, startCol+5), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						
						setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 훼손우려지 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetDmgcncrn(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				String dmgCncrnList = dataList.get(i).get("dmgcncrnlist").toString();
				if(!dmgCncrnList.equals("[]") && dmgCncrnList != null && !dmgCncrnList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("훼손우려지");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(dmgCncrnList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 훼손우려지 key -> 위도1, 경도1, 위도2, 경도2, 훼손우려지, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String type = jsonNodeMap.get(j).get("훼손우려지").asText();
						
						ListMap.put("type"+(j+1), type);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "훼손우려지");
					for(int k=0; k<=colNo+1+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7_b);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+1+(contentCnt*8)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "유/무");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), "유");
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					int initialCol = 2;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 13;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 훼손우려지");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "훼손우려지");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("type"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
				        
				        setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
				        for (int m=0; m<3; m++) {
				          getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
				        }
				        newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
				        
				        setText(getCell(newSheet, rowNo+3, startCol+2), "도");
				        getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+3), "분");
				        getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+4), "초");
				        getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+5), "도");
				        getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+6), "분");
				        getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+7), "초");
				        getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+8), "도");
				        getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+9), "분");
				        getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+10), "초");
				        getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+3, startCol+11), "도");
				        getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+12), "분");
				        getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
				        setText(getCell(newSheet, rowNo+3, startCol+13), "초");
				        getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
				        
				        setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
				        
				        setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
				        
				        setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
				        
				        setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
				        setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
				        getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
						
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산림재해 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetFrstDsstr(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 산림재해(산불/병해충)
				String frstDsstrList = dataList.get(i).get("frstdsstrlist").toString();
				if(!frstDsstrList.equals("[]") && frstDsstrList != null && !frstDsstrList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("산림재해");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(frstDsstrList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					// 산불(건)
					int frstFireCnt = 0;
					
					// 병해충(건)
					int pestCnt = 0;
					
					// 기타(건)
					int etcCnt = 0;
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 산림재해 key -> 위도1, 경도1, 위도2, 경도2, 재해유형, 산림재해, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String lon2 = jsonNodeMap.get(j).get("경도2").asText();
						String lat2 = jsonNodeMap.get(j).get("위도2").asText();
						String dmgType = jsonNodeMap.get(j).get("재해유형").asText();
						String dmgAt = jsonNodeMap.get(j).get("산림재해").asText();//유무
						
						ListMap.put("dmgType"+(j+1), dmgType);
						ListMap.put("dmgAt"+(j+1), dmgAt);
						
						if(dmgType.equals("산불")) {
							frstFireCnt += 1;
						}else if(dmgType.equals("병해충")) {
							pestCnt += 1;
						}else {
							etcCnt += 1;
						}
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
						
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
						
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
						if(lon2 != null && lat2 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon2_"+(j+1), convertLon);
							ListMap.put("lat2_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD2_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM2_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS2_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD2_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM2_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS2_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "산림재해");
					for(int k=0; k<=colNo+5+(contentCnt*14); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+5+(contentCnt*14)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "산불(건)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), Integer.toString(frstFireCnt).concat("건"));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "병해충(건)");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), Integer.toString(pestCnt).concat("건"));
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					setText(getCell(newSheet, rowNo+1, colNo+4), "기타(건)");
					getCell(newSheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+4).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+4, colNo+4));
					
					setText(getCell(newSheet, rowNo+1, colNo+5), Integer.toString(etcCnt).concat("건"));
					getCell(newSheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+5).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+5, colNo+5));
					
					int initialCol = 6;  // 시작 열
					int colStep = 14;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						  int startCol = colNo + initialCol + (colStep * k);
						  int endCol = startCol + 13;
						  
						  setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 산림재해");
						  for (int m=0; m<7; m++) {
						    getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						  
						  setText(getCell(newSheet, rowNo+2, startCol), "재해유형");
						  for(int m=2; m<=4; m++) {
						    getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						  
						  setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("dmgType"+(k+1)));
						  for(int m=2; m<=4; m++) {
						    getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						  
						  setText(getCell(newSheet, rowNo+2, startCol+2), "위도1");
						  for (int m=0; m<3; m++) {
						    getCell(newSheet, rowNo+2, startCol+m+2).setCellStyle(HeaderStyle2_b);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+2, startCol+4));
						  
						  setText(getCell(newSheet, rowNo+2, startCol+5), "경도1");
						  for (int m=0; m<3; m++) {
						    getCell(newSheet, rowNo+2, startCol+m+5).setCellStyle(HeaderStyle2_b);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+5, startCol+7));
						  
						  setText(getCell(newSheet, rowNo+2, startCol+8), "위도2");
						  for (int m=0; m<3; m++) {
						    getCell(newSheet, rowNo+2, startCol+m+8).setCellStyle(HeaderStyle2_b);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+8, startCol+10));
						  
						  setText(getCell(newSheet, rowNo+2, startCol+11), "경도2");
						  for (int m=0; m<3; m++) {
						    getCell(newSheet, rowNo+2, startCol+m+11).setCellStyle(HeaderStyle2_b);
						  }
						  newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+11, startCol+13));
						  
						  setText(getCell(newSheet, rowNo+3, startCol+2), "도");
						  getCell(newSheet, rowNo+3, startCol+2).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+3), "분");
						  getCell(newSheet, rowNo+3, startCol+3).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+4), "초");
						  getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						  
						  setText(getCell(newSheet, rowNo+3, startCol+5), "도");
						  getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+6), "분");
						  getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+7), "초");
						  getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						  
						  setText(getCell(newSheet, rowNo+3, startCol+8), "도");
						  getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+9), "분");
						  getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+10), "초");
						  getCell(newSheet, rowNo+3, startCol+10).setCellStyle(HeaderStyle2_b);
						  
						  setText(getCell(newSheet, rowNo+3, startCol+11), "도");
						  getCell(newSheet, rowNo+3, startCol+11).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+12), "분");
						  getCell(newSheet, rowNo+3, startCol+12).setCellStyle(HeaderStyle2_b);
						  setText(getCell(newSheet, rowNo+3, startCol+13), "초");
						  getCell(newSheet, rowNo+3, startCol+13).setCellStyle(HeaderStyle2_b);
						  
						  setText(getCell(newSheet, rowNo+4, startCol+2), ListMap.get("latD1_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+2).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+3), ListMap.get("latM1_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+3).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latS1_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						  
						  setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("lonD1_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("lonM1_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonS1_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						  
						  setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("latD2_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("latM2_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+10), ListMap.get("latS2_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+10).setCellStyle(HeaderStyle1);
						  
						  setText(getCell(newSheet, rowNo+4, startCol+11), ListMap.get("lonD2_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+11).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+12), ListMap.get("lonM2_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+12).setCellStyle(HeaderStyle1);
						  setText(getCell(newSheet, rowNo+4, startCol+13), ListMap.get("lonS2_"+(k+1)));
						  getCell(newSheet, rowNo+4, startCol+13).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 야생동물 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetWildAnml(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 야생동물
				String wildAnmlList = dataList.get(i).get("wildanmllist").toString();
				if(!wildAnmlList.equals("[]") && wildAnmlList != null && !wildAnmlList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("야생동물");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(wildAnmlList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 야생동물 key -> 위도1, 경도1, 위도2, 경도2, 야생동물_유무, 야생동물_유형, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String anmlAt = jsonNodeMap.get(i).get("야생동물_유형").asText();
						String anmlType = jsonNodeMap.get(i).get("야생동물_종류").asText();
						
						ListMap.put("anmlAt"+(j+1), anmlAt);
						ListMap.put("anmlType"+(j+1), anmlType);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "야생동물");
					for(int k=0; k<=colNo+3+(contentCnt*10); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+3+(contentCnt*10)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "전체(건)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("wildanmlcnt").toString().concat("건"));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "종류");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), dataList.get(i).get("wildanmlkind").toString());
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					int initialCol = 4;  // 시작 열
					int colStep = 10;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 9;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 야생동물");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "유무");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("anmlAt"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "종류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+2, startCol+2));
						
						setText(getCell(newSheet, rowNo+2, startCol+3), ListMap.get("anmlType"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+3).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+3, startCol+3));
						
						setText(getCell(newSheet, rowNo+2, startCol+4), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+4).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+4, startCol+6));
						
						setText(getCell(newSheet, rowNo+2, startCol+7), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+7).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+7, startCol+9));
						
						setText(getCell(newSheet, rowNo+3, startCol+4), "도");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+5), "분");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "초");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+7), "도");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+8), "분");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "초");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 사방시설 설치 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetEcnrFcltyInstl(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 사방시설설치
				String ecnrFcltyInstlList = dataList.get(i).get("ecnrfcltyinstllist").toString();
				if(!ecnrFcltyInstlList.equals("[]") && ecnrFcltyInstlList != null && !ecnrFcltyInstlList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("사방시설설치");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(ecnrFcltyInstlList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 사방시설 설치 여부 key -> 위도1, 경도1, 위도2, 경도2, 사방시설설치_유무, 사방시설설치_유형, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String ecnrFcltyInstlAt = jsonNodeMap.get(i).get("사방시설설치_유무").asText();
						String ecnrFcltyInstlType = jsonNodeMap.get(i).get("사방시설설치_유형").asText();
						
						ListMap.put("ecnrFcltyInstlAt"+(j+1), ecnrFcltyInstlAt);
						ListMap.put("ecnrFcltyInstlType"+(j+1), ecnrFcltyInstlType);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "사방시설설치");
					for(int k=0; k<=colNo+3+(contentCnt*10); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+3+(contentCnt*10)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "전체(건)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("ecnrfcltyinstlcnt").toString().concat("건"));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "종류");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), dataList.get(i).get("ecnrfcltyinstltype").toString());
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					int initialCol = 4;  // 시작 열
					int colStep = 10;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 9;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 사방시설설치");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "유무");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("ecnrFcltyInstlAt"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "종류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+2, startCol+2));
						
						setText(getCell(newSheet, rowNo+2, startCol+3), ListMap.get("ecnrFcltyInstlType"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+3).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+3, startCol+3));
						
						setText(getCell(newSheet, rowNo+2, startCol+4), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+4).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+4, startCol+6));
						
						setText(getCell(newSheet, rowNo+2, startCol+7), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+7).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+7, startCol+9));
						
						setText(getCell(newSheet, rowNo+3, startCol+4), "도");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+5), "분");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "초");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+7), "도");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+8), "분");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "초");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 사방시설 필요 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheetEcnrFcltyNcstyList(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		int rowNo = 0, colNo = 0;
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = null;
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 사방시설필요
				String ecnrFcltyNcstyList = dataList.get(i).get("ecnrfcltyncstylist").toString();
				if(!ecnrFcltyNcstyList.equals("[]") && ecnrFcltyNcstyList != null && !ecnrFcltyNcstyList.equals("")) {
					// 시트 생성
					XSSFSheet newSheet = wb.createSheet("사방시설필요");
					newSheet.setDefaultColumnWidth(14);
					newSheet.createRow(1);
					
					rowNo = 0;
					colNo = 0;
					
					// 데이터 가공
					ObjectMapper ojm = new ObjectMapper();
					JsonNode jsonNodeMap = ojm.readTree(ecnrFcltyNcstyList.toString());
					
					HashMap<String, String> ListMap = new HashMap<String, String>();
					
					for (int j = 0; j < jsonNodeMap.size(); j++) {
						// 사방시설 필요 여부 key -> 위도1, 경도1, 위도2, 경도2, 사방시설필요_유무, 사방시설필요_유형, 사진, 사진태그
						String lon1 = jsonNodeMap.get(j).get("경도1").asText();
						String lat1 = jsonNodeMap.get(j).get("위도1").asText();
						String ecnrFcltyNcstyAt = jsonNodeMap.get(i).get("사방시설필요_유무").asText();
						String ecnrFcltyNcstyType = jsonNodeMap.get(i).get("사방시설필요_유형").asText();
						
						ListMap.put("ecnrFcltyNcstyAt"+(j+1), ecnrFcltyNcstyAt);
						ListMap.put("ecnrFcltyNcstyType"+(j+1), ecnrFcltyNcstyType);
						
						Geometry geom;
						
						if(lon1 != null && lat1 != null) {
							Point2D pt2d = new Point2D(Double.parseDouble(lon1),Double.parseDouble(lat1));
							geom = new GeoPoint(pt2d);
							
							CoordSysTranslator.convert(geom, new PrjCoordSys(5186), new PrjCoordSys(4326), new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
							
							String convertLon = degree2Dms(geom.getInnerPoint().x);
							String convertLat = degree2Dms(geom.getInnerPoint().y);
							
							ListMap.put("lon1_"+(j+1), convertLon);
							ListMap.put("lat1_"+(j+1), convertLat);
							
							// degree to dms
							String lonLat = convertLat+"N"+" "+convertLon+"E";
							pattern = Pattern.compile(regex);
							Matcher matcherLonLat = pattern.matcher(lonLat);
							
							while (matcherLonLat.find()) {
								String latD = matcherLonLat.group(1);
								String latM = matcherLonLat.group(2);
								String latS = matcherLonLat.group(3);
								
								String lonD = matcherLonLat.group(5);
								String lonM = matcherLonLat.group(6);
								String lonS = matcherLonLat.group(7);
								
								if(latD != null && !latD.equals("")) {
									dmsformal.setDmsLatDeg(latD);
									ListMap.put("latD1_"+(j+1), dmsformal.getDmsLatDeg());
								}
								
								if(latM != null && !latM.equals("")) {
									dmsformal.setDmsLatMin(latM);
									ListMap.put("latM1_"+(j+1), dmsformal.getDmsLatMin());
								}
								
								if(latS != null && !latS.equals("")) {
									dmsformal.setDmsLatSec(latS);
									ListMap.put("latS1_"+(j+1), dmsformal.getDmsLatSec());
								}
								
								if(lonD != null && !lonD.equals("")) {
									dmsformal.setDmsLonDeg(lonD);
									ListMap.put("lonD1_"+(j+1), dmsformal.getDmsLonDeg());
								}
								
								if(lonM != null && !lonM.equals("")) {
									dmsformal.setDmsLonMin(lonM);
									ListMap.put("lonM1_"+(j+1), dmsformal.getDmsLonMin());
								}
								
								if(lonS != null && !lonS.equals("")) {
									dmsformal.setDmsLonSec(lonS);
									ListMap.put("lonS1_"+(j+1), dmsformal.getDmsLonSec());
								}
							}
						}
					}
					
					int contentCnt = jsonNodeMap.size();	// 항목의 개수
					
					setText(getCell(newSheet, rowNo, colNo+0), "사방시설필요");
					for(int k=0; k<=colNo+3+(contentCnt*10); k++){
						getCell(newSheet, rowNo, colNo+k).setCellStyle(HeaderStyle7);
					}
					newSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+0, colNo+3+(contentCnt*10)));
					
					setText(getCell(newSheet, rowNo+1, colNo+0), "전체(건)");
					getCell(newSheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+0).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+0).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+0, colNo+0));
					
					setText(getCell(newSheet, rowNo+1, colNo+1), dataList.get(i).get("ecnrfcltyncstycnt").toString().concat("건"));
					getCell(newSheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+1).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+1, colNo+1));
					
					setText(getCell(newSheet, rowNo+1, colNo+2), "종류");
					getCell(newSheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle2_b);
					getCell(newSheet, rowNo+4, colNo+2).setCellStyle(HeaderStyle2_b);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+2, colNo+2));
					
					setText(getCell(newSheet, rowNo+1, colNo+3), dataList.get(i).get("ecnrfcltyncstytype").toString());
					getCell(newSheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					getCell(newSheet, rowNo+4, colNo+3).setCellStyle(HeaderStyle1);
					newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+4, colNo+3, colNo+3));
					
					int initialCol = 4;  // 시작 열
					int colStep = 10;    // 컬럼 증가량
					
					for (int k=0; k < contentCnt; k++) {
						int startCol = colNo + initialCol + (colStep * k);
						int endCol = startCol + 9;
						
						setText(getCell(newSheet, rowNo+1, startCol), (k+1)+"번 사방시설설치");
						for (int m=0; m<7; m++) {
							getCell(newSheet, rowNo+1, startCol+m).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, startCol, endCol));
						
						setText(getCell(newSheet, rowNo+2, startCol), "유무");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol, startCol));
						
						setText(getCell(newSheet, rowNo+2, startCol+1), ListMap.get("ecnrFcltyNcstyAt"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+1).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+1, startCol+1));
						
						setText(getCell(newSheet, rowNo+2, startCol+2), "종류");
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+2).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+2, startCol+2));
						
						setText(getCell(newSheet, rowNo+2, startCol+3), ListMap.get("ecnrFcltyNcstyType"+(k+1)));
						for(int m=2; m<=4; m++) {
							getCell(newSheet, rowNo+m, startCol+3).setCellStyle(HeaderStyle1);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+4, startCol+3, startCol+3));
						
						setText(getCell(newSheet, rowNo+2, startCol+4), "위도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+4).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+4, startCol+6));
						
						setText(getCell(newSheet, rowNo+2, startCol+7), "경도");
						for (int m=0; m<3; m++) {
							getCell(newSheet, rowNo+2, startCol+m+7).setCellStyle(HeaderStyle2_b);
						}
						newSheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, startCol+7, startCol+9));
						
						setText(getCell(newSheet, rowNo+3, startCol+4), "도");
						getCell(newSheet, rowNo+3, startCol+4).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+5), "분");
						getCell(newSheet, rowNo+3, startCol+5).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+6), "초");
						getCell(newSheet, rowNo+3, startCol+6).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+3, startCol+7), "도");
						getCell(newSheet, rowNo+3, startCol+7).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+8), "분");
						getCell(newSheet, rowNo+3, startCol+8).setCellStyle(HeaderStyle2_b);
						setText(getCell(newSheet, rowNo+3, startCol+9), "초");
						getCell(newSheet, rowNo+3, startCol+9).setCellStyle(HeaderStyle2_b);
						
						setText(getCell(newSheet, rowNo+4, startCol+4), ListMap.get("latD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+4).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+5), ListMap.get("latM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+5).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+6), ListMap.get("latS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+6).setCellStyle(HeaderStyle1);
						
						setText(getCell(newSheet, rowNo+4, startCol+7), ListMap.get("lonD1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+7).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+8), ListMap.get("lonM1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+8).setCellStyle(HeaderStyle1);
						setText(getCell(newSheet, rowNo+4, startCol+9), ListMap.get("lonS1_"+(k+1)));
						getCell(newSheet, rowNo+4, startCol+9).setCellStyle(HeaderStyle1);
					}
				}
			}
		}
	}
}
