package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class BscExcelView extends AbstractView {

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
	private static XSSFCellStyle ValueStyle = null;

	/**
	 * Default Constructor. Sets the content type of the view for excel files.
	 */
	public BscExcelView() {
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
		
		String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름

		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트

		XSSFSheet sheet = wb.createSheet(sheetNm);
		sheet.setDefaultColumnWidth(14);
		Row row = sheet.createRow(1);
		row.setHeight((short)2000);
		int rowNo = 0, colNo = 0;
		
		if(dataList.size() > 0 ) {
			if(dataList.get(0).get("svyyear") != null ) {
				if(!dataList.get(0).get("svyyear").toString().equals("2021")) {
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+12).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+13).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+14).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+15).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+16).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+17).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+18).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+19).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+20).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+21).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+22).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+23).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+24).setCellStyle(HeaderStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo, colNo+24));
	
					setText(getCell(sheet, rowNo, colNo+25), "산사태 위험인자");
					getCell(sheet, rowNo, colNo+25).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+26).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+27).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+28).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+29).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+30).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+31).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+32).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+33).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+34).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+35).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+36).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+37).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+38).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+39).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+40).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+41).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+42).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+43).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+44).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+45).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+46).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+47).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+48).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+49).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+50).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+51).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+52).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+53).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+54).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+55).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+56).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+57).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+58).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+59).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+60).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+61).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+62).setCellStyle(HeaderStyle2);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+25, colNo+62));
					
					setText(getCell(sheet, rowNo, colNo+63), "토석류 위험인자");
					getCell(sheet, rowNo, colNo+63).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+64).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+65).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+66).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+67).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+68).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+69).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+70).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+71).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+72).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+73).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+74).setCellStyle(HeaderStyle3);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+63, colNo+74));
					
					setText(getCell(sheet, rowNo, colNo+75), "점수계");
					getCell(sheet, rowNo, colNo+75).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+75).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+75).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+75).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+75, colNo+75));
	
					setText(getCell(sheet, rowNo, colNo+76), "실태조사 필요성");
					getCell(sheet, rowNo, colNo+76).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+76).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+76).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+76).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+76, colNo+76));
	
					setText(getCell(sheet, rowNo, colNo+77), "주요위험성");
					getCell(sheet, rowNo, colNo+77).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+77).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+77).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+77).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+77, colNo+77));
	
					sheet.setColumnWidth(colNo+78, (short)20000);
					setText(getCell(sheet, rowNo, colNo+78), "조사자의견");
					getCell(sheet, rowNo, colNo+78).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+78).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+78).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+78).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+78, colNo+78));
					
					colNo = 0;
	
					setText(getCell(sheet, rowNo+1, colNo), "ID");
					getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo, colNo));
	
					setText(getCell(sheet, rowNo+1, colNo+1), "조사자");
					getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+1, colNo+1));
	
					setText(getCell(sheet, rowNo+1, colNo+2), "조사유형");
					getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+2, colNo+2));
	
					setText(getCell(sheet, rowNo+1, colNo+3), "조사일자");
					getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+3, colNo+3));
	
					setText(getCell(sheet, rowNo+1, colNo+4), "GPS 좌표(조사시점)");
					getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+4, colNo+9));
					
					setText(getCell(sheet, rowNo+1, colNo+10), "고도값\r\n"+ "(조사시점)");
					getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+10).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+10, colNo+10));
	
					setText(getCell(sheet, rowNo+1, colNo+11), "GPS 좌표(조사끝점)");
					getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+12).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+13).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+14).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+15).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+16).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+11, colNo+16));
					
					setText(getCell(sheet, rowNo+1, colNo+17), "고도값\r\n"+ "(조사끝점)");
					getCell(sheet, rowNo+1, colNo+17).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+17).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+17).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+17, colNo+17));
	
					setText(getCell(sheet, rowNo+1, colNo+18), "재산관리관");
					getCell(sheet, rowNo+1, colNo+18).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+19).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+18, colNo+19));
	
					setText(getCell(sheet, rowNo+1, colNo+20), "시_도");
					getCell(sheet, rowNo+1, colNo+20).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+20).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+20).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+20, colNo+20));
	
					setText(getCell(sheet, rowNo+1, colNo+21), "시_군_구");
					getCell(sheet, rowNo+1, colNo+21).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+21).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+21).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+21, colNo+21));
	
					setText(getCell(sheet, rowNo+1, colNo+22), "읍_면_동");
					getCell(sheet, rowNo+1, colNo+22).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+22).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+22).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+22, colNo+22));
	
					setText(getCell(sheet, rowNo+1, colNo+23), "리");
					getCell(sheet, rowNo+1, colNo+23).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+23).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+23).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+23, colNo+23));
	
					setText(getCell(sheet, rowNo+1, colNo+24), "지번");
					getCell(sheet, rowNo+1, colNo+24).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+24).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+24).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+24, colNo+24));
	
					setText(getCell(sheet, rowNo+1, colNo+25),
							"보호대상\r\n"+"0=일반산지\r\n"+"5=재산피해\r\n"+"10=인가1~4\r\n"+"15=인가5~9\r\n"+"20=인가10이상 또는 공공시설");
					getCell(sheet, rowNo+1, colNo+25).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+26).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+25, colNo+26));
	
					setText(getCell(sheet, rowNo+1, colNo+27),"경사길이(m)\r\n"+"1=5~30미만\r\n"+"3=30~60미만\r\n"+"5=60~100미만\r\n"+"7=100~150미만\r\n"+"10=150이상");
					getCell(sheet, rowNo+1, colNo+27).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+28).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+27, colNo+28));
					
					setText(getCell(sheet, rowNo+1, colNo+29),"경사길이\r\n"+"GPS좌표(하단부)");
					getCell(sheet, rowNo+1, colNo+29).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+30).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+31).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+32).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+33).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+34).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+29, colNo+34));
					
					setText(getCell(sheet, rowNo+1, colNo+35),"경사길이\r\n"+"GPS좌표(상단부)");
					getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+36).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+37).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+38).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+39).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+40).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+35, colNo+40));
					
					setText(getCell(sheet, rowNo+1, colNo+41),
							"경사도(°)\r\n"+"5=10~15미만\r\n"+"8=15~20미만\r\n"+"15=20~30미만\r\n"+"17=30~40미만\r\n"+"20=40이상");
					getCell(sheet, rowNo+1, colNo+41).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+42).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+41, colNo+42));
					
					setText(getCell(sheet, rowNo+1, colNo+43),"경사도\r\n"+"GPS좌표(하단부)");
					getCell(sheet, rowNo+1, colNo+43).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+44).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+45).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+46).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+47).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+48).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+43, colNo+48));
					
					setText(getCell(sheet, rowNo+1, colNo+49),"경사도\r\n"+"GPS좌표(상단부)");
					getCell(sheet, rowNo+1, colNo+49).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+50).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+51).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+52).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+53).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+54).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+49, colNo+54));
	
					setText(getCell(sheet, rowNo+1, colNo+55),
							"사면형\r\n"+"3=상승사면\r\n"+"5=평형사면\r\n"+"8=하강사면\r\n"+"10=복합사면");
					getCell(sheet, rowNo+1, colNo+55).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+56).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+55, colNo+56));
	
					setText(getCell(sheet, rowNo+1, colNo+57),
							"임상\r\n"+"1=활,혼(소,중,대)\r\n"+"3=활,혼(치)\r\n"+"5=침(치,소)\r\n"+"7=무입목지\r\n"+"10=침(중,대)");
					getCell(sheet, rowNo+1, colNo+57).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+58).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+57, colNo+58));
	
					setText(getCell(sheet, rowNo+1, colNo+59),
							"모암\r\n"+"2=퇴적암\r\n"+"4=화강암\r\n"+"6=천매암\r\n"+"8=편암\r\n"+"10=반암");
					getCell(sheet, rowNo+1, colNo+59).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+60).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+59, colNo+60));
					
					setText(getCell(sheet, rowNo+1, colNo+61),
							"위험요인(낙석,붕괴,침식)\r\n"+"3=침식(토사) 또는 균열(암반)\r\n"+"5=유실 또는 이완암(낙석)\r\n"+"10=붕괴\r\n"+"15=침식,유실,붕괴(2개) 또는 균열,이완암,붕괴(2개)\r\n"+"20=침식,유실,붕괴(3개) 또는 균열,이완암,붕괴(3개)");
					getCell(sheet, rowNo+1, colNo+61).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+62).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+61, colNo+62));
					
					setText(getCell(sheet, rowNo+1, colNo+63),
							"보호대상\r\n"+"0=일반산지\r\n"+"5=재산피해\r\n"+"10=인가1~4\r\n"+"15=인가5~9\r\n"+"20=인가10이상 또는 공공시설");
					getCell(sheet, rowNo+1, colNo+63).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+64).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+63, colNo+64));
					
					setText(getCell(sheet, rowNo+1, colNo+65),
							"황폐발생원\r\n"+"0=산사태 위험 4등급 이하만 있는 유역\r\n"+"3=산사태 위험 3등급 이하만 있는 유역\r\n"+"5=산사태 위험 2등급50%미만인 유역\r\n"+"7=산사태 위험 2등급50%이상인 유역\r\n"+"10=산사태 위험 1등급이 있는 유역");
					getCell(sheet, rowNo+1, colNo+65).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+66).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+65, colNo+66));
	
					setText(getCell(sheet, rowNo+1, colNo+67),
							"계류평균경사(°)\r\n"+"3=5미만\r\n"+"9=5~7미만\r\n"+"12=7~11미만\r\n"+"17=11~16미만\r\n"+"20=16이상");
					getCell(sheet, rowNo+1, colNo+67).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+68).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+67, colNo+68));
	
					setText(getCell(sheet, rowNo+1, colNo+69),
							"집수면적(ha)\r\n"+"3=5미만\r\n"+"5=5~10미만\r\n"+"10=10~20미만\r\n"+"15=20~30미만\r\n"+"20=30이상");
					getCell(sheet, rowNo+1, colNo+69).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+70).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+69, colNo+70));
	
					setText(getCell(sheet, rowNo+1, colNo+71),
							"총계류길이(m)\r\n"+"1=100미만\r\n"+"3=100~200미만\r\n"+"5=200~300미만\r\n"+"7=300~500미만\r\n"+"10=500이상");
					getCell(sheet, rowNo+1, colNo+71).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+72).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+71, colNo+72));
					
					setText(getCell(sheet, rowNo+1, colNo+73),
							"위험인자(전석,침식,붕괴)\r\n"+"3=전석\r\n"+"5=침식\r\n"+"10=붕괴\r\n"+"15=전석,침식 또는 전석,붕괴 또는 침식,붕괴\r\n"+"20=전석,침식,붕괴");
					getCell(sheet, rowNo+1, colNo+73).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+74).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+73, colNo+74));
					
					setText(getCell(sheet, rowNo+2, colNo+4), "위도");
					getCell(sheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+6).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+6).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+4, colNo+6));
	
					setText(getCell(sheet, rowNo+2, colNo+7), "경도");
					getCell(sheet, rowNo+2, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+9).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+9).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+7, colNo+9));
	
					setText(getCell(sheet, rowNo+2, colNo+11), "위도");
					getCell(sheet, rowNo+2, colNo+11).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+12).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+13).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+11).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+12).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+13).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+11, colNo+13));
	
					setText(getCell(sheet, rowNo+2, colNo+14), "경도");
					getCell(sheet, rowNo+2, colNo+14).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+15).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+16).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+14).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+15).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+16).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+14, colNo+16));
	
					setText(getCell(sheet, rowNo+2, colNo+18), "관할1");
					getCell(sheet, rowNo+2, colNo+18).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+18).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+18, colNo+18));
	
					setText(getCell(sheet, rowNo+2, colNo+19), "관할2");
					getCell(sheet, rowNo+2, colNo+19).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+19).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+19, colNo+19));
	
					setText(getCell(sheet, rowNo+3, colNo+25), "재산");
					getCell(sheet, rowNo+3, colNo+25).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+26), "점수");
					getCell(sheet, rowNo+3, colNo+26).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+27), "측정값");
					getCell(sheet, rowNo+3, colNo+27).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+28), "점수");
					getCell(sheet, rowNo+3, colNo+28).setCellStyle(HeaderStyle4);
					
					setText(getCell(sheet, rowNo+3, colNo+29), "위도");
					getCell(sheet, rowNo+3, colNo+29).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+30).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+31).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+29, colNo+31));
	
					setText(getCell(sheet, rowNo+3, colNo+32), "경도");
					getCell(sheet, rowNo+3, colNo+32).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+33).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+34).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+32, colNo+34));
					
					setText(getCell(sheet, rowNo+3, colNo+35), "위도");
					getCell(sheet, rowNo+3, colNo+35).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+36).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+37).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+35, colNo+37));
	
					setText(getCell(sheet, rowNo+3, colNo+38), "경도");
					getCell(sheet, rowNo+3, colNo+38).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+39).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+40).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+38, colNo+40));
	
					setText(getCell(sheet, rowNo+3, colNo+41), "측정값");
					getCell(sheet, rowNo+3, colNo+41).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+42), "점수");
					getCell(sheet, rowNo+3, colNo+42).setCellStyle(HeaderStyle4);
					
					setText(getCell(sheet, rowNo+3, colNo+43), "위도");
					getCell(sheet, rowNo+3, colNo+43).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+44).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+45).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+43, colNo+45));
	
					setText(getCell(sheet, rowNo+3, colNo+46), "경도");
					getCell(sheet, rowNo+3, colNo+46).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+47).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+48).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+46, colNo+48));
					
					setText(getCell(sheet, rowNo+3, colNo+49), "위도");
					getCell(sheet, rowNo+3, colNo+49).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+50).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+51).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+49, colNo+51));
	
					setText(getCell(sheet, rowNo+3, colNo+52), "경도");
					getCell(sheet, rowNo+3, colNo+52).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+53).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+3, colNo+54).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+52, colNo+54));
	
					setText(getCell(sheet, rowNo+3, colNo+55), "측정값");
					getCell(sheet, rowNo+3, colNo+55).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+56), "점수");
					getCell(sheet, rowNo+3, colNo+56).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+57), "측정값");
					getCell(sheet, rowNo+3, colNo+57).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+58), "점수");
					getCell(sheet, rowNo+3, colNo+58).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+59), "측정값");
					getCell(sheet, rowNo+3, colNo+59).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+60), "점수");
					getCell(sheet, rowNo+3, colNo+60).setCellStyle(HeaderStyle4);
					
					// 위험인자 :S
					setText(getCell(sheet, rowNo+3, colNo+61), "측정값");
					getCell(sheet, rowNo+3, colNo+61).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+62), "점수");
					getCell(sheet, rowNo+3, colNo+62).setCellStyle(HeaderStyle4);
					// 위험인자 :E
	
					setText(getCell(sheet, rowNo+3, colNo+63), "측정값");
					getCell(sheet, rowNo+3, colNo+63).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+64), "점수");
					getCell(sheet, rowNo+3, colNo+64).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+65), "측정값");
					getCell(sheet, rowNo+3, colNo+65).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+66), "점수");
					getCell(sheet, rowNo+3, colNo+66).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+67), "측정값");
					getCell(sheet, rowNo+3, colNo+67).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+68), "점수");
					getCell(sheet, rowNo+3, colNo+68).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+69), "측정값");
					getCell(sheet, rowNo+3, colNo+69).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+70), "점수");
					getCell(sheet, rowNo+3, colNo+70).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+71), "측정값");
					getCell(sheet, rowNo+3, colNo+71).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+72), "점수");
					getCell(sheet, rowNo+3, colNo+72).setCellStyle(HeaderStyle5);
					
					setText(getCell(sheet, rowNo+3, colNo+73), "측정값");
					getCell(sheet, rowNo+3, colNo+73).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+74), "점수");
					getCell(sheet, rowNo+3, colNo+74).setCellStyle(HeaderStyle5);
					
					colNo = 0;
					rowNo = 4;
					
					if (dataList.size() > 0) {
						
						for (int i = 0; i < dataList.size(); i++) {
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyUser").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svytype").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							String[] bpArr = dataList.get(i).get("bp").toString().split("\\s+");
	
							setText(getCell(sheet, rowNo, colNo), bpArr[0].substring(0, bpArr[0].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[1].substring(0, bpArr[1].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[2].substring(0, bpArr[2].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[3].substring(0, bpArr[3].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[4].substring(0, bpArr[4].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[5].substring(0, bpArr[5].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("bz").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							String[] epArr = dataList.get(i).get("ep").toString().split("\\s+");
	
							setText(getCell(sheet, rowNo, colNo), epArr[0].substring(0, epArr[0].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[1].substring(0, epArr[1].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[2].substring(0, epArr[2].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[3].substring(0, epArr[3].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[4].substring(0, epArr[4].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[5].substring(0, epArr[5].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ez").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyregion1").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyregion2").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysd").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysgg").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyemd").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyri").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyjibun").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							if(dataList.get(i).get("svytype").toString().equals("산사태")) {
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyval").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
								
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyscore").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}else {
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slenval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slenscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							String[] slenBpArr = dataList.get(i).get("slenBp").toString().split("\\s+");
							
							setText(getCell(sheet, rowNo, colNo), slenBpArr.length > 1 ? slenBpArr[0].substring(0, slenBpArr[0].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenBpArr.length > 1 ? slenBpArr[1].substring(0, slenBpArr[1].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenBpArr.length > 1 ? slenBpArr[2].substring(0, slenBpArr[2].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenBpArr.length > 1 ? slenBpArr[3].substring(0, slenBpArr[3].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenBpArr.length > 1 ? slenBpArr[4].substring(0, slenBpArr[4].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenBpArr.length > 1 ? slenBpArr[5].substring(0, slenBpArr[5].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							String[] slenEpArr = dataList.get(i).get("slenEp").toString().split("\\s+");
							
							setText(getCell(sheet, rowNo, colNo), slenEpArr.length > 1 ? slenEpArr[0].substring(0, slenEpArr[0].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenEpArr.length > 1 ? slenEpArr[1].substring(0, slenEpArr[1].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenEpArr.length > 1 ? slenEpArr[2].substring(0, slenEpArr[2].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenEpArr.length > 1 ? slenEpArr[3].substring(0, slenEpArr[3].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenEpArr.length > 1 ? slenEpArr[4].substring(0, slenEpArr[4].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slenEpArr.length > 1 ? slenEpArr[5].substring(0, slenEpArr[5].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slopeval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slopescore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							String[] slopeBpArr = dataList.get(i).get("slopeBp").toString().split("\\s+");
							
							setText(getCell(sheet, rowNo, colNo), slopeBpArr.length > 1 ? slopeBpArr[0].substring(0, slopeBpArr[0].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeBpArr.length > 1 ? slopeBpArr[1].substring(0, slopeBpArr[1].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeBpArr.length > 1 ? slopeBpArr[2].substring(0, slopeBpArr[2].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeBpArr.length > 1 ? slopeBpArr[3].substring(0, slopeBpArr[3].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeBpArr.length > 1 ? slopeBpArr[4].substring(0, slopeBpArr[4].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeBpArr.length > 1 ? slopeBpArr[5].substring(0, slopeBpArr[5].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							String[] slopeEpArr = dataList.get(i).get("slopeEp").toString().split("\\s+");
							
							setText(getCell(sheet, rowNo, colNo), slopeEpArr.length > 1 ? slopeEpArr[0].substring(0, slopeEpArr[0].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeEpArr.length > 1 ? slopeEpArr[1].substring(0, slopeEpArr[1].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeEpArr.length > 1 ? slopeEpArr[2].substring(0, slopeEpArr[2].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeEpArr.length > 1 ? slopeEpArr[3].substring(0, slopeEpArr[3].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeEpArr.length > 1 ? slopeEpArr[4].substring(0, slopeEpArr[4].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), slopeEpArr.length > 1 ? slopeEpArr[5].substring(0, slopeEpArr[5].length() - 1) : "");
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sformval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sformscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frstfrval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frstfrscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prntrckval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prntrckscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							if(dataList.get(i).get("svytype").toString().equals("산사태")) {
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rskfactorval").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
								
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rskfactorscore").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							} else {
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}
	
							if(dataList.get(i).get("svytype").toString().equals("토석류")) {
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyval").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
								
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyscore").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}else {
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("devoccauseval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("devoccausescore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("trntavgslpval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("trntavgslpscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("wclctareaval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("wclctareascore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("tltrntltval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("tltrntltscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
	//						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("distbmdsbrateval").toString());
	//						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
		//
	//						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("distbmdsbratescore").toString());
	//						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							
							if(dataList.get(i).get("svytype").toString().equals("토석류")) {
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rskfactorval").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
								
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rskfactorscore").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							} else {
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysm").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ncssty").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mainrisk").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion").toString());
							getCell(sheet, rowNo++, colNo).setCellStyle(ValueStyle);
							
							colNo = 0;
						}
						
					}
				}else {
						
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+12).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+13).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+14).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+15).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+16).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+17).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+18).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+19).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+20).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+21).setCellStyle(HeaderStyle);
					getCell(sheet, rowNo, colNo+22).setCellStyle(HeaderStyle);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo, colNo+22));
	
					setText(getCell(sheet, rowNo, colNo+23), "산사태 위험인자");
					getCell(sheet, rowNo, colNo+23).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+24).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+25).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+26).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+27).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+28).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+29).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+30).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+31).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+32).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+33).setCellStyle(HeaderStyle2);
					getCell(sheet, rowNo, colNo+34).setCellStyle(HeaderStyle2);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+23, colNo+34));
	
					setText(getCell(sheet, rowNo, colNo+35), "토석류 위험인자");
					getCell(sheet, rowNo, colNo+35).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+36).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+37).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+38).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+39).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+40).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+41).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+42).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+43).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+44).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+45).setCellStyle(HeaderStyle3);
					getCell(sheet, rowNo, colNo+46).setCellStyle(HeaderStyle3);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+35, colNo+46));
	
					setText(getCell(sheet, rowNo, colNo+47), "점수계");
					getCell(sheet, rowNo, colNo+47).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+47).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+47).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+47).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+47, colNo+47));
	
					setText(getCell(sheet, rowNo, colNo+48), "실태조사 필요성");
					getCell(sheet, rowNo, colNo+48).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+48).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+48).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+48).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+48, colNo+48));
	
					setText(getCell(sheet, rowNo, colNo+49), "주요위험성");
					getCell(sheet, rowNo, colNo+49).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+49).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+49).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+49).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+49, colNo+49));
	
					setText(getCell(sheet, rowNo, colNo+50), "조사자의견");
					getCell(sheet, rowNo, colNo+50).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+1, colNo+50).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+2, colNo+50).setCellStyle(HeaderStyle6);
					getCell(sheet, rowNo+3, colNo+50).setCellStyle(HeaderStyle6);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+50, colNo+50));
	
					colNo = 0;
	
					setText(getCell(sheet, rowNo+1, colNo), "ID");
					getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo, colNo));
	
					setText(getCell(sheet, rowNo+1, colNo+1), "조사자");
					getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+1, colNo+1));
	
					setText(getCell(sheet, rowNo+1, colNo+2), "조사유형");
					getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+2, colNo+2));
	
					setText(getCell(sheet, rowNo+1, colNo+3), "조사일자");
					getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+3, colNo+3));
	
					setText(getCell(sheet, rowNo+1, colNo+4), "GPS 좌표(조사시점)");
					getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+4, colNo+9));
	
					setText(getCell(sheet, rowNo+1, colNo+10), "GPS 좌표(끝점)");
					getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+12).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+13).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+14).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+15).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+10, colNo+15));
	
					setText(getCell(sheet, rowNo+1, colNo+16), "재산관리관");
					getCell(sheet, rowNo+1, colNo+16).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+1, colNo+17).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+16, colNo+17));
	
					setText(getCell(sheet, rowNo+1, colNo+18), "시_도");
					getCell(sheet, rowNo+1, colNo+18).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+18).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+18).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+18, colNo+18));
	
					setText(getCell(sheet, rowNo+1, colNo+19), "시_군_구");
					getCell(sheet, rowNo+1, colNo+19).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+19).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+19).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+19, colNo+19));
	
					setText(getCell(sheet, rowNo+1, colNo+20), "읍_면_동");
					getCell(sheet, rowNo+1, colNo+20).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+20).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+20).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+20, colNo+20));
	
					setText(getCell(sheet, rowNo+1, colNo+21), "리");
					getCell(sheet, rowNo+1, colNo+21).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+21).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+21).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+21, colNo+21));
	
					setText(getCell(sheet, rowNo+1, colNo+22), "지번");
					getCell(sheet, rowNo+1, colNo+22).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+22).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+22).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+22, colNo+22));
	
					setText(getCell(sheet, rowNo+1, colNo+23),
							"보호대상\r\n"+"0=일반산지\r\n"+"5=재산\r\n"+"10=1~4\r\n"+"15=5~9\r\n"+"20=10↑");
					getCell(sheet, rowNo+1, colNo+23).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+24).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+23, colNo+24));
	
					setText(getCell(sheet, rowNo+1, colNo+25),
							"경사길이(m)\r\n"+"3=30↓\r\n"+"8=31~60\r\n"+"15=61~100\r\n"+"17=101~150\r\n"+"20=151↑");
					getCell(sheet, rowNo+1, colNo+25).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+26).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+25, colNo+26));
	
					setText(getCell(sheet, rowNo+1, colNo+27),
							"경사도(°)\r\n"+"5=15↓\r\n"+"8=16~20\r\n"+"15=21~30\r\n"+"17=31~40\r\n"+"20=41↑");
					getCell(sheet, rowNo+1, colNo+27).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+28).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+27, colNo+28));
	
					setText(getCell(sheet, rowNo+1, colNo+29),
							"사면형\r\n"+"3=상승사면\r\n"+"5=평형사면\r\n"+"8=하강사면\r\n"+"10=복합사면");
					getCell(sheet, rowNo+1, colNo+29).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+30).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+29, colNo+30));
	
					setText(getCell(sheet, rowNo+1, colNo+31),
							"임상\r\n"+"3=활,혼(소,중,대)\r\n"+"8=활,혼(치)\r\n"+"15=침(치,소)\r\n"+"17=무입목지\r\n"+"20=침(중,대)");
					getCell(sheet, rowNo+1, colNo+31).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+32).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+31, colNo+32));
	
					setText(getCell(sheet, rowNo+1, colNo+33),
							"모암\r\n"+"2=퇴적암\r\n"+"4=화강암\r\n"+"6=천매암\r\n"+"8=편암\r\n"+"10=반암");
					getCell(sheet, rowNo+1, colNo+33).setCellStyle(HeaderStyle4);
					getCell(sheet, rowNo+2, colNo+34).setCellStyle(HeaderStyle4);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+33, colNo+34));
	
					setText(getCell(sheet, rowNo+1, colNo+35),
							"보호대상\r\n"+"0=일반산지\r\n"+"5=재산피해\r\n"+"10=1~4\r\n"+"15=5~9\r\n"+"20=10↑");
					getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+36).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+35, colNo+36));
	
					setText(getCell(sheet, rowNo+1, colNo+37),
							"황폐발생원\r\n"+"0=4등급\r\n"+"3=3등급\r\n"+"5=2등급↓\r\n"+"7=2등급↑\r\n"+"10=1등급");
					getCell(sheet, rowNo+1, colNo+37).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+38).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+37, colNo+38));
	
					setText(getCell(sheet, rowNo+1, colNo+39),
							"계류평균경사(°)\r\n"+"3=4↓\r\n"+"9=5~7\r\n"+"12=8~10\r\n"+"17=11~16\r\n"+"20=17↑");
					getCell(sheet, rowNo+1, colNo+39).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+40).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+39, colNo+40));
	
					setText(getCell(sheet, rowNo+1, colNo+41),
							"집수면적(ha)\r\n"+"3=4↓\r\n"+"5=5~10\r\n"+"10=11~20\r\n"+"15=21~30\r\n"+"20=31↑");
					getCell(sheet, rowNo+1, colNo+41).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+42).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+41, colNo+42));
	
					setText(getCell(sheet, rowNo+1, colNo+43),
							"총계류길이(m)\r\n"+"3=99↓\r\n"+"5=100~200\r\n"+"10=201~300\r\n"+"15=301~500\r\n"+"20=501↑");
					getCell(sheet, rowNo+1, colNo+43).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+44).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+43, colNo+44));
	
					setText(getCell(sheet, rowNo+1, colNo+45),
							"전석분포비율(%)\r\n"+"2=4↓\r\n"+"4=5~10\r\n"+"6=11~20\r\n"+"8=21~30\r\n"+"10=31↑");
					getCell(sheet, rowNo+1, colNo+45).setCellStyle(HeaderStyle5);
					getCell(sheet, rowNo+2, colNo+46).setCellStyle(HeaderStyle5);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+45, colNo+46));
	
					setText(getCell(sheet, rowNo+2, colNo+4), "위도");
					getCell(sheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+6).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+6).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+4, colNo+6));
	
					setText(getCell(sheet, rowNo+2, colNo+7), "경도");
					getCell(sheet, rowNo+2, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+9).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+9).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+7, colNo+9));
	
					setText(getCell(sheet, rowNo+2, colNo+10), "위도");
					getCell(sheet, rowNo+2, colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+11).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+12).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+11).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+12).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+10, colNo+12));
	
					setText(getCell(sheet, rowNo+2, colNo+13), "경도");
					getCell(sheet, rowNo+2, colNo+13).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+14).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+2, colNo+15).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+13).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+14).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+15).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+13, colNo+15));
	
					setText(getCell(sheet, rowNo+2, colNo+16), "관할1");
					getCell(sheet, rowNo+2, colNo+16).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+16).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+16, colNo+16));
	
					setText(getCell(sheet, rowNo+2, colNo+17), "관할2");
					getCell(sheet, rowNo+2, colNo+17).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+3, colNo+17).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+17, colNo+17));
	
					setText(getCell(sheet, rowNo+3, colNo+23), "재산");
					getCell(sheet, rowNo+3, colNo+23).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+24), "점수");
					getCell(sheet, rowNo+3, colNo+24).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+25), "측정값");
					getCell(sheet, rowNo+3, colNo+25).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+26), "점수");
					getCell(sheet, rowNo+3, colNo+26).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+27), "측정값");
					getCell(sheet, rowNo+3, colNo+27).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+28), "점수");
					getCell(sheet, rowNo+3, colNo+28).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+29), "측정값");
					getCell(sheet, rowNo+3, colNo+29).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+30), "점수");
					getCell(sheet, rowNo+3, colNo+30).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+31), "측정값");
					getCell(sheet, rowNo+3, colNo+31).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+32), "점수");
					getCell(sheet, rowNo+3, colNo+32).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+33), "측정값");
					getCell(sheet, rowNo+3, colNo+33).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+34), "점수");
					getCell(sheet, rowNo+3, colNo+34).setCellStyle(HeaderStyle4);
	
					setText(getCell(sheet, rowNo+3, colNo+35), "측정값");
					getCell(sheet, rowNo+3, colNo+35).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+36), "점수");
					getCell(sheet, rowNo+3, colNo+36).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+37), "측정값");
					getCell(sheet, rowNo+3, colNo+37).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+38), "점수");
					getCell(sheet, rowNo+3, colNo+38).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+39), "측정값");
					getCell(sheet, rowNo+3, colNo+39).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+40), "점수");
					getCell(sheet, rowNo+3, colNo+40).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+41), "측정값");
					getCell(sheet, rowNo+3, colNo+41).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+42), "점수");
					getCell(sheet, rowNo+3, colNo+42).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+43), "측정값");
					getCell(sheet, rowNo+3, colNo+43).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+44), "점수");
					getCell(sheet, rowNo+3, colNo+44).setCellStyle(HeaderStyle5);
	
					setText(getCell(sheet, rowNo+3, colNo+45), "측정값");
					getCell(sheet, rowNo+3, colNo+45).setCellStyle(HeaderStyle7);
	
					setText(getCell(sheet, rowNo+3, colNo+46), "점수");
					getCell(sheet, rowNo+3, colNo+46).setCellStyle(HeaderStyle5);
	
					colNo = 0;
					rowNo = 4;
	
					if (dataList.size() > 0) {
						for (int i = 0; i < dataList.size(); i++) {
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyUser").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svytype").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							String[] bpArr = dataList.get(i).get("bp").toString().split("\\s+");
	
							setText(getCell(sheet, rowNo, colNo), bpArr[0].substring(0, bpArr[0].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[1].substring(0, bpArr[1].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[2].substring(0, bpArr[2].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[3].substring(0, bpArr[3].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[4].substring(0, bpArr[4].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), bpArr[5].substring(0, bpArr[5].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							String[] epArr = dataList.get(i).get("ep").toString().split("\\s+");
	
							setText(getCell(sheet, rowNo, colNo), epArr[0].substring(0, epArr[0].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[1].substring(0, epArr[1].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[2].substring(0, epArr[2].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[3].substring(0, epArr[3].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[4].substring(0, epArr[4].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), epArr[5].substring(0, epArr[5].length() - 1));
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyregion1").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyregion2").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysd").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysgg").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyemd").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyri").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyjibun").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							if(dataList.get(i).get("svytype").toString().equals("산사태")) {
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyval").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
								
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyscore").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}else {
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slenval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slenscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slopeval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slopescore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sformval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sformscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frstfrval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frstfrscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prntrckval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prntrckscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							if(dataList.get(i).get("svytype").toString().equals("토석류")) {
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyval").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
								
								setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyscore").toString());
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}else {
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
								setText(getCell(sheet, rowNo, colNo), "");
								getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
							}
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("devoccauseval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("devoccausescore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("trntavgslpval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("trntavgslpscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("wclctareaval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("wclctareascore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("tltrntltval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("tltrntltscore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("distbmdsbrateval").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("distbmdsbratescore").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysm").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ncssty").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mainrisk").toString());
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
	
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion").toString());
							getCell(sheet, rowNo++, colNo).setCellStyle(ValueStyle);
	
							colNo = 0;
						}
					}
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(235, 241, 222)));
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(252, 213, 180)));
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(197, 217, 241)));
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(242, 220, 219)));
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(220, 230, 241)));
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(228, 223, 236)));
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 0)));
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
