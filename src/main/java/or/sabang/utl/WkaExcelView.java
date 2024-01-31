package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.springframework.web.servlet.view.AbstractView;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class WkaExcelView extends AbstractView {

	/** The content type for an Excel response */
	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private final static String comptFileDir = EgovProperties.getProperty("Globals.fileStorePath.compt");
	
	private static XSSFCellStyle HeaderStyle = null;
	private static XSSFCellStyle HeaderStyleBlue = null;	
	private static XSSFCellStyle HeaderStyleBlue2 = null;	
	private static XSSFCellStyle HeaderStyleGray = null;
	private static XSSFCellStyle HeaderStyleRed = null;
	private static XSSFCellStyle HeaderStyleOrange = null;
	private static XSSFCellStyle HeaderStyleYellow = null;
	private static XSSFCellStyle HeaderStyleGreen = null;
	private static XSSFCellStyle HeaderStyleBeige = null;
	private static XSSFCellStyle HeaderStylePink = null;
	private static XSSFCellStyle HeaderStylePink2 = null;
	private static XSSFCellStyle HeaderStyleGreen2 = null;
	private static XSSFCellStyle HeaderStyleGreen3 = null;
	private static XSSFCellStyle HeaderStyleSky = null;
	private static XSSFCellStyle ValueStyle = null;
	private static XSSFCellStyle ValueStyle2 = null;
	private static XSSFCellStyle ValueStyle3 = null;
	private static XSSFCellStyle ValueStyle4 = null;
	private static XSSFCellStyle ValueStyle5 = null;
	String color = null;

	/**
	 * Default Constructor. Sets the content type of the view for excel files.
	 */
	public WkaExcelView() {
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
		
		HeaderStyle = getHeaderCellStyle(workbook,"");
		HeaderStyleRed = getHeaderCellStyle(workbook,"Red");
		HeaderStyleYellow = getHeaderCellStyle(workbook,"Yellow");
		HeaderStyleBlue = getHeaderCellStyle(workbook,"Blue");
		HeaderStyleBlue2 = getHeaderCellStyle(workbook,"Blue2");
		HeaderStyleGray = getHeaderCellStyle(workbook,"Gray");
		HeaderStyleOrange = getHeaderCellStyle(workbook,"Orange");
		HeaderStyleGreen = getHeaderCellStyle(workbook,"Green");
		HeaderStyleGreen2 = getHeaderCellStyle(workbook,"Green2");
		HeaderStyleGreen3 = getHeaderCellStyle(workbook,"Green3");
		HeaderStyleBeige = getHeaderCellStyle(workbook,"Beige");
		HeaderStylePink = getHeaderCellStyle(workbook,"Pink");
		HeaderStylePink2 = getHeaderCellStyle(workbook,"Pink2");
		HeaderStyleSky = getHeaderCellStyle(workbook,"Sky");
		ValueStyle = getValueCellStyle(workbook);
		ValueStyle2 = getValueCellStyle2(workbook);
		ValueStyle3 = getValueCellStyle3(workbook);
		ValueStyle4 = getValueCellStyle4(workbook);
		ValueStyle5 = getValueCellStyle5(workbook);
		
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
	public static void buildExcelDocument(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		HashMap<String, Object> dataMap = (HashMap<String, Object>) model.get("dataMap");
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list");
		
		String svyType = dataList.get(0).get("svytype").toString();
		
		if(svyType.equals("산사태")) {
			buildSheet11(model, wb, req, resp);
			buildSheet12(model, wb, req, resp);
			buildSheet13(model, wb, req, resp);
			buildSheet14(model, wb, req, resp);
			buildSheet15(model, wb, req, resp);
			buildSheet16(model, wb, req, resp);
		}else {
			buildSheet21(model, wb, req, resp);
			buildSheet22(model, wb, req, resp);
			buildSheet23(model, wb, req, resp);
			buildSheet24(model, wb, req, resp);
			buildSheet25(model, wb, req, resp);
			buildSheet26(model, wb, req, resp);
		}
		
	}

	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산사태 입력값 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet11(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
			
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름

		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		
		XSSFSheet sheet1 = wb.createSheet("입력값");
		sheet1.setDefaultColumnWidth(12);
		Row row = sheet1.createRow(1);
		row.setHeight((short)1000);
		
		int rowNo = 0, colNo = 0;
		XSSFFont fontRed = wb.createFont();

		getCell(sheet1, rowNo, colNo).setCellStyle(HeaderStyleYellow);
		fontRed.setColor(Font.COLOR_RED);		
		
		/* 입력값시트 */
		getCell(sheet1, rowNo, colNo).setCellValue("일반사항");
		getCell(sheet1, rowNo, colNo).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+1).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+2).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+3).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+4).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+5).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+6).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+7).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+8).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+9).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+10).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+11).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+12).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+13).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+14).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+15).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+16).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+17).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+18).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+19).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+20).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+21).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+22).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+23).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+24).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+25).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+26).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+27).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+28).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+29).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+30).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+31).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+32).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+33).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+34).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+35).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+36).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+37).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+38).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+39).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+40).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+41).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+42).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+43).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+44).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo, colNo+44));

		getCell(sheet1, rowNo, colNo+45).setCellValue("위험사면 현황");
		getCell(sheet1, rowNo, colNo+45).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+46).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+47).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+48).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+49).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+50).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+51).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+52).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+53).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+54).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+55).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+56).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+57).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+58).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+59).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+60).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+61).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+62).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+63).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+64).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+65).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+66).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+67).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+68).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+69).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+70).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+45, colNo+70));

		getCell(sheet1, rowNo, colNo+71).setCellValue("최종 판정등급");
		getCell(sheet1, rowNo, colNo+71).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+72).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+73).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+74).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+75).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+76).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+77).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+78).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+79).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+80).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+81).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+82).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+83).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+84).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+71, colNo+84));

		String text ="판정표(해당하지 않는 항목에 대해서는 -로 표시)<무조건 모든 값을 넣어줘야함 -라도> 빈칸 없이";
		XSSFRichTextString richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);		
		getCell(sheet1, rowNo, colNo+85).setCellValue(richText);		
		getCell(sheet1, rowNo, colNo+85).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+86).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+87).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+88).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+89).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+90).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+91).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+92).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+93).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+94).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+95).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+96).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+97).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+98).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+99).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+100).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+101).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+102).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+103).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+104).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+85, colNo+104));

		getCell(sheet1, rowNo, colNo+105).setCellValue("편입지적");
		getCell(sheet1, rowNo, colNo+105).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+106).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+107).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+108).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+109).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+110).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+111).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+112).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+113).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+114).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+115).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+116).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+118).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+119).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+120).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+121).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+122).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+123).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+124).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+125).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+126).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+128).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+129).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+105, colNo+129));

		getCell(sheet1, rowNo, colNo+130).setCellValue("피해 현황 및 현황도");
		getCell(sheet1, rowNo, colNo+130).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+131).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+132).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+133).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+134).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+135).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+136).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+137).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+138).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+139).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+140).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+141).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+142).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+143).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+144).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+145).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+146).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+147).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+148).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+149).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+150).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+151).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+152).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+153).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+154).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+155).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+156).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+157).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+158).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+159).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+160).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+161).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+162).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+163).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+164).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+165).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+166).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+167).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+168).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+169).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+170).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+171).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+172).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+173).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+174).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+175).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+176).setCellStyle(HeaderStyleBeige);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+130, colNo+176));

		text ="종합의견 / 해당칸 입력 또는 보고서 직접입력";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo, colNo+177).setCellValue(richText);
		getCell(sheet1, rowNo, colNo+177).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+178).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+179).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+180).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+181).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+182).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+183).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+184).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+185).setCellStyle(HeaderStyleYellow);		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+177, colNo+185));

		colNo=0;
		text ="위치(좌표 소수점 둘째자리)";
		richText = new XSSFRichTextString(text);
		richText.applyFont(2,text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+1).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+2).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+5).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+6).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+8).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+11).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+13).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+14).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo, colNo+14));

		getCell(sheet1, rowNo+1, colNo+15).setCellValue("조사자");
		getCell(sheet1, rowNo+1, colNo+15).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+15).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+16).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+16).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+17).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+17).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+18).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+18).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+19).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+19).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+15, colNo+19));

		getCell(sheet1, rowNo+1, colNo+20).setCellValue("안정해석점수");
		getCell(sheet1, rowNo+1, colNo+20).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+20).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+21).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+21).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+22).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+22).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+23).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+23).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+24).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+24).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+25).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+25).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+26).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+26).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+27).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+27).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+28).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+28).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+29).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+29).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+30).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+30).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+31).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+31).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+32).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+32).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+20, colNo+32));

		getCell(sheet1, rowNo+1, colNo+33).setCellValue("조사지 환경");
		getCell(sheet1, rowNo+1, colNo+33).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+33).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+34).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+34).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+35).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+35).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+36).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+36).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+33, colNo+36));

		getCell(sheet1, rowNo+1, colNo+37).setCellValue("보호대상");
		getCell(sheet1, rowNo+1, colNo+37).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+37).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+38).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+38).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+39).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+39).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+40).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+40).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+41).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+41).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+42).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+42).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+43).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+43).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+44).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+44).setCellStyle(HeaderStyleBeige);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+37, colNo+44));
		
		text ="모암 암종류가 많이 판정표에 있는 암그대로 사면현황 / 경사위치 산록(1~3), 산복(4~7), 산정(8~10) / 사면형 상승, 하강, 평형";
		richText = new XSSFRichTextString(text);
		richText.applyFont(0,text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+45).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+45).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+45).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+46).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+46).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+47).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+47).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+48).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+48).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+49).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+49).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+50).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+50).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+51).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+51).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+52).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+52).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+53).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+53).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+54).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+54).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+55).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+55).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+56).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+56).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+57).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+57).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+45, colNo+57));

		getCell(sheet1, rowNo+1, colNo+58).setCellValue("토질현황");
		getCell(sheet1, rowNo+1, colNo+58).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+58).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+59).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+59).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+60).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+60).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+61).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+61).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+62).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+62).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+63).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+63).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+58, colNo+63));

		getCell(sheet1, rowNo+1, colNo+64).setCellValue("임상");
		getCell(sheet1, rowNo+1, colNo+64).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+64).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+65).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+65).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+66).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+66).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+64, colNo+66));

		getCell(sheet1, rowNo+1, colNo+67).setCellValue("용수현황");
		getCell(sheet1, rowNo+1, colNo+67).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+2, colNo+67).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+1, colNo+68).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+2, colNo+68).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+1, colNo+69).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+2, colNo+69).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+1, colNo+70).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo+2, colNo+70).setCellStyle(HeaderStyleGreen2);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+67, colNo+70));

		getCell(sheet1, rowNo+1, colNo+71).setCellValue("판정 점수");
		getCell(sheet1, rowNo+1, colNo+71).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+71).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+72).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+72).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+73).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+73).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+71, colNo+73));

		text ="판정등급 / 에이 : A, 비 : B, 씨 : C";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+74).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+74).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+74).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+75).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+75).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+74, colNo+75));

		getCell(sheet1, rowNo+1, colNo+76).setCellValue("등급보정");
		getCell(sheet1, rowNo+1, colNo+76).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+76).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+77).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+77).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+76, colNo+77));

		getCell(sheet1, rowNo+1, colNo+78).setCellValue("관리방안");
		getCell(sheet1, rowNo+1, colNo+78).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+78).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+79).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+79).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+80).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+80).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+81).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+81).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+78, colNo+81));

		getCell(sheet1, rowNo+1, colNo+82).setCellValue("관리필요성");
		getCell(sheet1, rowNo+1, colNo+82).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+82).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+82, colNo+82));

		getCell(sheet1, rowNo+1, colNo+83).setCellValue("종합의견1");
		getCell(sheet1, rowNo+1, colNo+83).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+83).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+83, colNo+83));

		getCell(sheet1, rowNo+1, colNo+84).setCellValue("종합의견2");
		getCell(sheet1, rowNo+1, colNo+84).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+84).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+84, colNo+84));

		getCell(sheet1, rowNo+1, colNo+85).setCellValue("현장조사 계");
		getCell(sheet1, rowNo+1, colNo+85).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+85).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+85, colNo+85));

		text ="피해가능성 0점 주의!!";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("0"),text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+86).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+86).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+86).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+86, colNo+87));

		text ="토사사면 / 토사 암반 중 택1 미해당은 0 -";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+88).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+88).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+88).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+89).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+89).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+90).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+90).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+91).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+91).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+88, colNo+91));

		text ="암반사면 / 토사 암반 중 택1 미해당은 0 -";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+92).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+92).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+92).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+93).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+93).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+94).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+94).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+95).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+95).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+92, colNo+95));

		getCell(sheet1, rowNo+1, colNo+96).setCellValue("공통");
		getCell(sheet1, rowNo+1, colNo+96).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+96).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+97).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+97).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+96, colNo+97));

		text ="토사사면 0점 주의!!";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("0"),text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+98).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+98).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+98).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+99).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+99).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+100).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+100).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+98, colNo+100));

		text ="암반사면 0점 주의!!";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("0"),text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+101).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+101).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+101).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+102).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+102).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+103).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+103).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+101, colNo+103));

		getCell(sheet1, rowNo+1, colNo+104).setCellValue("면적계");
		getCell(sheet1, rowNo+1, colNo+104).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+104).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+104, colNo+104));

		text ="지번기입 시 전, 도, 천, 임 등 모두 붙일것 / 편입면적 / 면적 및 편입면적은 M2로 입력";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(0,text.indexOf("/")-1,fontRed);
		richText.applyFont(text.lastIndexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+105).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+105).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+105).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+106).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+106).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+107).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+107).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+108).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+108).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+109).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+109).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+110).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+110).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+111).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+111).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+112).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+112).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+113).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+113).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+114).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+114).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+115).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+115).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+116).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+116).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+117).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+117).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+118).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+118).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+119).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+119).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+120).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+120).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+121).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+121).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+122).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+122).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+123).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+123).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+124).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+124).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+125).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+125).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+126).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+126).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+127).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+127).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+128).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+128).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+129).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+129).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+105, colNo+129));

		getCell(sheet1, rowNo+1, colNo+130).setCellValue("현황도");
		getCell(sheet1, rowNo+1, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+134).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+134).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+135).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+135).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+136).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+136).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+137).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+137).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+138).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+138).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+139).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+139).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+140).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+140).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+141).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+141).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+142).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+142).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+143).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+143).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+144).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+144).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+145).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+145).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+146).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+146).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+147).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+147).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+148).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+148).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+149).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+149).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+150).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+150).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+151).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+151).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+152).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+152).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+153).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+153).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+154).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+154).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+155).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+155).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+156).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+156).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+157).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+157).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+158).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+158).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+159).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+159).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+160).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+160).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+164).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+164).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+166).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+166).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+167).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+167).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+168).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+168).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+169).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+169).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+130, colNo+169));
		
		getCell(sheet1, rowNo+1, colNo+170).setCellValue("대피로");
		getCell(sheet1, rowNo+1, colNo+170).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+170).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+171).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+171).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+172).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+172).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+173).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+173).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+174).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+174).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+175).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+175).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+176).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+176).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+170, colNo+176));
		
		getCell(sheet1, rowNo+1, colNo+177).setCellValue("대상지개요");
		getCell(sheet1, rowNo+1, colNo+177).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+177).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+177, colNo+177));

		getCell(sheet1, rowNo+1, colNo+178).setCellValue("현장결과 위험계류 현황");
		getCell(sheet1, rowNo+1, colNo+178).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+178).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+1, colNo+179).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+179).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+178, colNo+179));

		getCell(sheet1, rowNo+1, colNo+180).setCellValue("지정사유 항목");
		getCell(sheet1, rowNo+1, colNo+180).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+180).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+1, colNo+181).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+181).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+180, colNo+181));

		getCell(sheet1, rowNo+1, colNo+182).setCellValue("구역설정근거 및 사유");
		getCell(sheet1, rowNo+1, colNo+182).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+182).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+182, colNo+182));

		getCell(sheet1, rowNo+1, colNo+183).setCellValue("기타특이사항");
		getCell(sheet1, rowNo+1, colNo+183).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+183).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+183, colNo+183));

		getCell(sheet1, rowNo+1, colNo+184).setCellValue("관리방안");
		getCell(sheet1, rowNo+1, colNo+184).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+184).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+1, colNo+185).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+185).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+184, colNo+185));

		text ="행정구역(시군, 읍면, 리동 전부 붙임)";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);
		getCell(sheet1, rowNo+2, colNo).setCellValue(richText);		
		getCell(sheet1, rowNo+2, colNo).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+1).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+2).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+5).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+6).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+8).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo, colNo+8));

		getCell(sheet1, rowNo+2, colNo+9).setCellValue("위도");
		getCell(sheet1, rowNo+2, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+11).setCellStyle(HeaderStyleGray);
		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+9, colNo+11));

		getCell(sheet1, rowNo+2, colNo+12).setCellValue("경도");
		getCell(sheet1, rowNo+2, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+14).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+14).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+12, colNo+14));

		colNo=0;
		rowNo=0;

		getCell(sheet1, rowNo+3, colNo).setCellValue("기번");
		getCell(sheet1, rowNo+3, colNo).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+1).setCellValue("구분");
		getCell(sheet1, rowNo+3, colNo+1).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+2).setCellValue("기초ID");
		getCell(sheet1, rowNo+3, colNo+2).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+3).setCellValue("시도");
		getCell(sheet1, rowNo+3, colNo+3).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+4).setCellValue("시군구");
		getCell(sheet1, rowNo+3, colNo+4).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+5).setCellValue("읍면");
		getCell(sheet1, rowNo+3, colNo+5).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+6).setCellValue("리동");
		getCell(sheet1, rowNo+3, colNo+6).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+7).setCellValue("지번");
		getCell(sheet1, rowNo+3, colNo+7).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+8).setCellValue("관리주체");
		getCell(sheet1, rowNo+3, colNo+8).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+9).setCellValue("도");
		getCell(sheet1, rowNo+3, colNo+9).setCellStyle(HeaderStyleGray);		

		getCell(sheet1, rowNo+3, colNo+10).setCellValue("분");
		getCell(sheet1, rowNo+3, colNo+10).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+11).setCellValue("초");
		getCell(sheet1, rowNo+3, colNo+11).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+12).setCellValue("도");
		getCell(sheet1, rowNo+3, colNo+12).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+13).setCellValue("분");
		getCell(sheet1, rowNo+3, colNo+13).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+14).setCellValue("초");
		getCell(sheet1, rowNo+3, colNo+14).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+15).setCellValue("소속");
		getCell(sheet1, rowNo+3, colNo+15).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+16).setCellValue("직급");
		getCell(sheet1, rowNo+3, colNo+16).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+17).setCellValue("조사자");
		getCell(sheet1, rowNo+3, colNo+17).setCellStyle(HeaderStyleBlue);

		text ="조사일자\r\n" + 
				"2022. 00. 00.";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+18).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+18).setCellStyle(HeaderStyleBlue);

		text ="연락처\r\n" + 
				"043-000-0000";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+19).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+19).setCellStyle(HeaderStyleBlue);
		
		text ="건기시\r\n" + 
				"소수점 2";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+20).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+20).setCellStyle(HeaderStyleOrange);

		text ="건기방향\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+21).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+21).setCellStyle(HeaderStyleOrange);

		text ="건기판정\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+22).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+22).setCellStyle(HeaderStyleOrange);

		text ="건기점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+23).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+23).setCellStyle(HeaderStyleOrange);

		text ="우기시\r\n" + 
				"소수점2";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+24).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+24).setCellStyle(HeaderStyleOrange);

		text ="우기방향\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+25).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+25).setCellStyle(HeaderStyleOrange);

		text ="우기판정\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+26).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+26).setCellStyle(HeaderStyleOrange);

		text ="우기점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+27).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+27).setCellStyle(HeaderStyleOrange);

		text ="습윤단위중량\r\n" + 
				"소수점1";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+28).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+28).setCellStyle(HeaderStyleOrange);

		text ="점착력\r\n" + 
				"소수점1";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+29).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+29).setCellStyle(HeaderStyleOrange);

		text ="내부마찰각\r\n" + 
				"소수점1";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+30).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+30).setCellStyle(HeaderStyleOrange);

		text ="안정해석점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+31).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+31).setCellStyle(HeaderStyleOrange);

		text ="이격거리\r\n" + 
				"안정해석";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+32).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+32).setCellStyle(HeaderStyleOrange);

		text ="유역(ha)\r\n" + 
				"소수점1";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+33).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+33).setCellStyle(HeaderStylePink);

		text ="유역(㎡)\r\n" + 
				"소수점1";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+34).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+34).setCellStyle(HeaderStylePink);

		text ="취약지역 면적\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+35).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+35).setCellStyle(HeaderStylePink);

		text ="취약(㎡)\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+36).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+36).setCellStyle(HeaderStylePink);

		text ="보호시설\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+37).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+37).setCellStyle(HeaderStyleBeige);

		text ="보호 개수\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+38).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+38).setCellStyle(HeaderStyleBeige);

		text ="인가\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+39).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+39).setCellStyle(HeaderStyleBeige);

		text ="인가 개수\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+40).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+40).setCellStyle(HeaderStyleBeige);

		text ="상부주요시설\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+41).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+41).setCellStyle(HeaderStyleBeige);

		text ="상부인가\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+42).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+42).setCellStyle(HeaderStyleBeige);

		text ="하부주요시설\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+43).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+43).setCellStyle(HeaderStyleBeige);

		text ="하부인가\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+44).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+44).setCellStyle(HeaderStyleBeige);

		text ="사면상태\r\n" + 
				"토사 암반";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+45).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+45).setCellStyle(HeaderStyleBlue);

		text ="모암\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+46).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+46).setCellStyle(HeaderStyleBlue);

		text ="토성\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+47).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+47).setCellStyle(HeaderStyleBlue);

		text ="경사길이\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+48).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+48).setCellStyle(HeaderStyleBlue);

		text ="평균경사도\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+49).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+49).setCellStyle(HeaderStyleBlue);

		text ="최저경사\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+50).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+50).setCellStyle(HeaderStyleBlue);

		text ="최고경사\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+51).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+51).setCellStyle(HeaderStyleBlue);

		text ="경사범위\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+52).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+52).setCellStyle(HeaderStyleBlue);

		text ="경사위치\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+53).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+53).setCellStyle(HeaderStyleBlue);

		text ="1~10부\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+54).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+54).setCellStyle(HeaderStyleBlue);

		text ="최고지점\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+55).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+55).setCellStyle(HeaderStyleBlue);

		text ="최저지점\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+56).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+56).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+57).setCellValue("사면형");
		getCell(sheet1, rowNo+3, colNo+57).setCellStyle(HeaderStyleBlue);

		text ="토심\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+58).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+58).setCellStyle(HeaderStyleGreen);

		text ="균열\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+59).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+59).setCellStyle(HeaderStyleGreen);

		text ="함몰\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+60).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+60).setCellStyle(HeaderStyleGreen);

		text ="융기\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+61).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+61).setCellStyle(HeaderStyleGreen);

		text ="말단부압출\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+62).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+62).setCellStyle(HeaderStyleGreen);

		text ="확대예상\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+63).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+63).setCellStyle(HeaderStyleGreen);

		text ="임상\r\n" + 
				"침 활 혼 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+64).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+64).setCellStyle(HeaderStyleBlue);

		text ="임분밀도\r\n" + 
				"소 중 밀";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+65).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+65).setCellStyle(HeaderStyleBlue);

		text ="임분경급\r\n" + 
				"치 소 중 대";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+66).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+66).setCellStyle(HeaderStyleBlue);

		text ="임분경급\r\n" + 
				"치 소 중 대";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+67).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+67).setCellStyle(HeaderStyleGreen2);

		text ="강우시용수\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+68).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+68).setCellStyle(HeaderStyleGreen2);

		text ="사면이축축함\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+69).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+69).setCellStyle(HeaderStyleGreen2);

		text ="사면이건조함\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+70).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+70).setCellStyle(HeaderStyleGreen2);

		text ="현장조사점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+71).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+71).setCellStyle(HeaderStyleGray);

		text ="점수계\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+72).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+72).setCellStyle(HeaderStyleGray);

		text ="안정해석점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+73).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+73).setCellStyle(HeaderStyleGray);

		text ="판정등급\r\n" + 
				"에이 비 씨";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+74).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+74).setCellStyle(HeaderStyleBlue);

		text ="판정결과\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+75).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+75).setCellStyle(HeaderStyleBlue);

		text ="보정\r\n" + 
				"상 하 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+76).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+76).setCellStyle(HeaderStylePink);

		text ="등급보정 사유\r\n" + 
				"텍스트 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+77).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+77).setCellStyle(HeaderStylePink);

		text ="사업 가능여부\r\n" + 
				"가능 불가능";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+78).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+78).setCellStyle(HeaderStyleOrange);

		text ="대책방안\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+79).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+79).setCellStyle(HeaderStyleOrange);

		text ="필요공종\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+80).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+80).setCellStyle(HeaderStyleOrange);

		text ="대피장소\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+81).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+81).setCellStyle(HeaderStyleOrange);

		text ="관리방안\r\n" + 
				"현 비 구";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+82).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+82).setCellStyle(HeaderStyleBlue);

		text ="종합의견1\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+83).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+83).setCellStyle(HeaderStyleGreen);

		text ="종합의견2\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+84).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+84).setCellStyle(HeaderStyleGreen);

		text ="계\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+85).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+85).setCellStyle(HeaderStylePink);

		text ="피해이력\r\n" + 
				"0 3 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+86).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+86).setCellStyle(HeaderStyleYellow);

		text ="보호시설\r\n" + 
				"0 5 7 10";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+87).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+87).setCellStyle(HeaderStyleYellow);

		text ="경사도\r\n" + 
				"1 3 5 7";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+88).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+88).setCellStyle(HeaderStyleBlue);

		text ="사면높이\r\n" + 
				"1 2 3 5 7";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+89).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+89).setCellStyle(HeaderStyleBlue);

		text ="토심\r\n" + 
				"1 2 3 5 7";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+90).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+90).setCellStyle(HeaderStyleBlue);

		text ="종단형상\r\n" + 
				"1 2 3 4";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+91).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+91).setCellStyle(HeaderStyleBlue);

		text ="경사도\r\n" + 
				"1 3 5 7";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+92).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+92).setCellStyle(HeaderStyleGreen);

		text ="사면높이\r\n" + 
				"1 3 5 7";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+93).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+93).setCellStyle(HeaderStyleGreen);

		text ="암석종류\r\n" + 
				"1 2 3 5 7";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+94).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+94).setCellStyle(HeaderStyleGreen);

		text ="균열상황\r\n" + 
				"1 2 3 4";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+95).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+95).setCellStyle(HeaderStyleGreen);

		text ="산사태위험도\r\n" + 
				"1 2 3 4 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+96).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+96).setCellStyle(HeaderStyleOrange);

		text ="용수상황\r\n" + 
				"1 3 4 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+97).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+97).setCellStyle(HeaderStyleOrange);

		text ="붕괴지\r\n" + 
				"0 5 7 10";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+98).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+98).setCellStyle(HeaderStyleGreen);

		text ="뿌리특성\r\n" + 
				"1 3 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+99).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+99).setCellStyle(HeaderStyleGreen);

		text ="산림현황\r\n" + 
				"1 3 4 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+100).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+100).setCellStyle(HeaderStyleGreen);

		text ="붕괴\r\n" + 
				"0 7 10";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+101).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+101).setCellStyle(HeaderStyleBlue);

		text ="불연속면방향\r\n" + 
				"1 3 4 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+102).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+102).setCellStyle(HeaderStyleBlue);

		text ="풍화상태\r\n" + 
				"1 3 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+103).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+103).setCellStyle(HeaderStyleBlue);

		text ="면적계\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+104).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+104).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+105).setCellValue("지번1");
		getCell(sheet1, rowNo+3, colNo+105).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+106).setCellValue("지목1");
		getCell(sheet1, rowNo+3, colNo+106).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+107).setCellValue("면적1");
		getCell(sheet1, rowNo+3, colNo+107).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+108).setCellValue("편입면적1");
		getCell(sheet1, rowNo+3, colNo+108).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+109).setCellValue("소유1");
		getCell(sheet1, rowNo+3, colNo+109).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+110).setCellValue("지번2");
		getCell(sheet1, rowNo+3, colNo+110).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+111).setCellValue("지목2");
		getCell(sheet1, rowNo+3, colNo+111).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+112).setCellValue("면적2");
		getCell(sheet1, rowNo+3, colNo+112).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+113).setCellValue("편입면적2");
		getCell(sheet1, rowNo+3, colNo+113).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+114).setCellValue("소유2");
		getCell(sheet1, rowNo+3, colNo+114).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+115).setCellValue("지번3");
		getCell(sheet1, rowNo+3, colNo+115).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+116).setCellValue("지목3");
		getCell(sheet1, rowNo+3, colNo+116).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+117).setCellValue("면적3");
		getCell(sheet1, rowNo+3, colNo+117).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+118).setCellValue("편입면적3");
		getCell(sheet1, rowNo+3, colNo+118).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+119).setCellValue("소유3");
		getCell(sheet1, rowNo+3, colNo+119).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+120).setCellValue("지번4");
		getCell(sheet1, rowNo+3, colNo+120).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+121).setCellValue("지목4");
		getCell(sheet1, rowNo+3, colNo+121).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+122).setCellValue("면적4");
		getCell(sheet1, rowNo+3, colNo+122).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+123).setCellValue("편입면적4");
		getCell(sheet1, rowNo+3, colNo+123).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+124).setCellValue("소유4");
		getCell(sheet1, rowNo+3, colNo+124).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+125).setCellValue("지번5");
		getCell(sheet1, rowNo+3, colNo+125).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+126).setCellValue("지목5");
		getCell(sheet1, rowNo+3, colNo+126).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+127).setCellValue("면적5");
		getCell(sheet1, rowNo+3, colNo+127).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+128).setCellValue("편입면적5");
		getCell(sheet1, rowNo+3, colNo+128).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+129).setCellValue("소유5");
		getCell(sheet1, rowNo+3, colNo+129).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+130).setCellValue("연번1");
		getCell(sheet1, rowNo+3, colNo+130).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+131).setCellValue("위도도1");
		getCell(sheet1, rowNo+3, colNo+131).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+132).setCellValue("위도분1");
		getCell(sheet1, rowNo+3, colNo+132).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+133).setCellValue("위도초1");
		getCell(sheet1, rowNo+3, colNo+133).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+134).setCellValue("경도도1");
		getCell(sheet1, rowNo+3, colNo+134).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+135).setCellValue("경도분1");
		getCell(sheet1, rowNo+3, colNo+135).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+136).setCellValue("경도초1");
		getCell(sheet1, rowNo+3, colNo+136).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+137).setCellValue("특이1");
		getCell(sheet1, rowNo+3, colNo+137).setCellStyle(HeaderStyleOrange);

		getCell(sheet1, rowNo+3, colNo+138).setCellValue("연번2");
		getCell(sheet1, rowNo+3, colNo+138).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+139).setCellValue("위도도2");
		getCell(sheet1, rowNo+3, colNo+139).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+140).setCellValue("위도분2");
		getCell(sheet1, rowNo+3, colNo+140).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+141).setCellValue("위도초2");
		getCell(sheet1, rowNo+3, colNo+141).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+142).setCellValue("경도도2");
		getCell(sheet1, rowNo+3, colNo+142).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+143).setCellValue("경도분2");
		getCell(sheet1, rowNo+3, colNo+143).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+144).setCellValue("경도초2");
		getCell(sheet1, rowNo+3, colNo+144).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+145).setCellValue("특이2");
		getCell(sheet1, rowNo+3, colNo+145).setCellStyle(HeaderStyleYellow);

		getCell(sheet1, rowNo+3, colNo+146).setCellValue("연번3");
		getCell(sheet1, rowNo+3, colNo+146).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+147).setCellValue("위도도3");
		getCell(sheet1, rowNo+3, colNo+147).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+148).setCellValue("위도분3");
		getCell(sheet1, rowNo+3, colNo+148).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+149).setCellValue("위도초3");
		getCell(sheet1, rowNo+3, colNo+149).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+150).setCellValue("경도도3");
		getCell(sheet1, rowNo+3, colNo+150).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+151).setCellValue("경도분3");
		getCell(sheet1, rowNo+3, colNo+151).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+152).setCellValue("경도초3");
		getCell(sheet1, rowNo+3, colNo+152).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+153).setCellValue("특이3");
		getCell(sheet1, rowNo+3, colNo+153).setCellStyle(HeaderStyleGreen);

		getCell(sheet1, rowNo+3, colNo+154).setCellValue("연번4");
		getCell(sheet1, rowNo+3, colNo+154).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+155).setCellValue("위도도4");
		getCell(sheet1, rowNo+3, colNo+155).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+156).setCellValue("위도분4");
		getCell(sheet1, rowNo+3, colNo+156).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+157).setCellValue("위도초4");
		getCell(sheet1, rowNo+3, colNo+157).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+158).setCellValue("경도도4");
		getCell(sheet1, rowNo+3, colNo+158).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+159).setCellValue("경도분4");
		getCell(sheet1, rowNo+3, colNo+159).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+160).setCellValue("경도초4");
		getCell(sheet1, rowNo+3, colNo+160).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+161).setCellValue("특이4");
		getCell(sheet1, rowNo+3, colNo+161).setCellStyle(HeaderStyleGreen2);

		getCell(sheet1, rowNo+3, colNo+162).setCellValue("연번5");
		getCell(sheet1, rowNo+3, colNo+162).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+163).setCellValue("위도도5");
		getCell(sheet1, rowNo+3, colNo+163).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+164).setCellValue("위도분5");
		getCell(sheet1, rowNo+3, colNo+164).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+165).setCellValue("위도초5");
		getCell(sheet1, rowNo+3, colNo+165).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+166).setCellValue("경도도5");
		getCell(sheet1, rowNo+3, colNo+166).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+167).setCellValue("경도분5");
		getCell(sheet1, rowNo+3, colNo+167).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+168).setCellValue("경도초5");
		getCell(sheet1, rowNo+3, colNo+168).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+169).setCellValue("특이5");
		getCell(sheet1, rowNo+3, colNo+169).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+170).setCellValue("장소");
		getCell(sheet1, rowNo+3, colNo+170).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+171).setCellValue("대위도");
		getCell(sheet1, rowNo+3, colNo+171).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+172).setCellValue("대위분");
		getCell(sheet1, rowNo+3, colNo+172).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+173).setCellValue("대위초");
		getCell(sheet1, rowNo+3, colNo+173).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+174).setCellValue("대경도");
		getCell(sheet1, rowNo+3, colNo+174).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+175).setCellValue("대경분");
		getCell(sheet1, rowNo+3, colNo+175).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+176).setCellValue("대경초");
		getCell(sheet1, rowNo+3, colNo+176).setCellStyle(HeaderStylePink);

		text ="대상지개요\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+177).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+177).setCellStyle(HeaderStyleGreen3);

		text ="현장결과1 / 유역현황\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+178).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+178).setCellStyle(HeaderStyleGreen3);

		text ="현장결과2 / 위험징후 및\r\n" + 
				"분포현환 / 텍스트";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.lastIndexOf("/")+1,text.length(),fontRed);		
		getCell(sheet1, rowNo+3, colNo+179).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+179).setCellStyle(HeaderStyleGreen3);

		text ="간략한 종합의견 및 지정사유\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+180).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+180).setCellStyle(HeaderStyleGreen3);

		text ="지정사유 또는 안정해석 결과 입력 / 텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+181).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+181).setCellStyle(HeaderStyleGreen3);

		text ="구역설정\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+182).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+182).setCellStyle(HeaderStyleGreen3);

		text ="특이사항 및 진입여건\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+183).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+183).setCellStyle(HeaderStyleGreen3);

		getCell(sheet1, rowNo+3, colNo+184).setCellValue("재해예방사업종\r\n" + 
				"선정사유");
		getCell(sheet1, rowNo+3, colNo+184).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet1, rowNo+3, colNo+185).setCellValue("기타종합의견\r\n" + 
				"주민협의 및 대체대안");
		getCell(sheet1, rowNo+3, colNo+185).setCellStyle(HeaderStyleGreen3);
		
		colNo=0;
		rowNo=4;
		String formulaText = "";
		int formatM = 0;
		Double formatS = 0.0;
		
		if(dataList.size() > 0) {
			for (int i=0; i<dataList.size(); i++) {
				colNo=0;
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("fid").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svytype").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svyid").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("sd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("sgg").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("emd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("ri").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("jibun").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("mgc").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("latD").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++); 
				if(dataList.get(i).get("latM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("latM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);					
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("latS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("latS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);					
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("lonD").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("lonM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lonM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("lonS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lonS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svydept").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("syvofcps").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svyuser").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				String format = dataList.get(i).get("svydt").toString().replaceAll("-", ". ").concat(".");
				cell.setCellValue(format);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svymbtl").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*건기시*/
				if(dataList.get(i).get("drval").toString().equals("") || doubleTypeCheck(dataList.get(i).get("drval").toString()) > 1) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("drval").toString()));					
				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*건기방향*/
				formulaText = "IF(W"+(rowNo+1)+"=\"OK\",\"<\",IF(W"+(rowNo+1)+"=\"NG\",\">\"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*건기판정*/
				formulaText = "IF(U"+(rowNo+1)+"<1.5,\"NG\",IF(U"+(rowNo+1)+">=1.5,\"OK\",IF(U"+(rowNo+1)+"=\"-\",\"\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*건기점수*/
				formulaText = "IF(U"+(rowNo+1)+">=1.6,0,IF(U"+(rowNo+1)+"<1.5,30,\"15\"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*우기시*/
				if(dataList.get(i).get("wtval").toString().equals("") || doubleTypeCheck(dataList.get(i).get("wtval").toString()) > 1) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("wtval").toString()));					
				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*우기방향*/
				formulaText = "IF(AA"+(rowNo+1)+"=\"OK\",\"<\",IF(AA"+(rowNo+1)+"=\"NG\",\">\"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*우기판정*/
				formulaText = "IF(Y"+(rowNo+1)+"<1.2,\"NG\",IF(Y"+(rowNo+1)+">=1.2,\"OK\",IF(Y"+(rowNo+1)+"=\"-\",\"\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*우기점수*/
				formulaText = "IF(Y"+(rowNo+1)+">=1.3,0,IF(Y"+(rowNo)+1+"<1.2,30,\"15\"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*습윤단위중량(값없음)*/
				if(dataList.get(i).get("hmdtwwight").toString().equals("") || doubleTypeCheck(dataList.get(i).get("hmdtwwight").toString()) > 1) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("hmdtwwight").toString()));					
				}	
				cell.setCellStyle(ValueStyle4);
				
				cell = getCell(sheet1, rowNo, colNo++);/*점착력(값없음)*/
				if(dataList.get(i).get("adhnsv").toString().equals("") || doubleTypeCheck(dataList.get(i).get("adhnsv").toString()) > 1) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("adhnsv").toString()));					
				}	
				cell.setCellStyle(ValueStyle4);
				
				cell = getCell(sheet1, rowNo, colNo++);/*내부마찰각(값없음)*/
				if(dataList.get(i).get("innrfrctn").toString().equals("") || doubleTypeCheck(dataList.get(i).get("innrfrctn").toString()) > 1) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("innrfrctn").toString()));					
				}	
				cell.setCellStyle(ValueStyle4);
				
				cell = getCell(sheet1, rowNo, colNo++);/*안정해석점수*/
				formulaText = "MAX(X"+(rowNo+1)+",AB"+(rowNo+1)+")"; 
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*이격거리*/
				cell.setCellValue(dataList.get(i).get("gapdstnc").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*유역(ha)*/ /*소수점 자리 체크*/
				if(dataList.get(i).get("dgrha").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("dgrha").toString()));					
				}					
				cell.setCellStyle(ValueStyle5);
																																																																																																																																																																																																																																																																																																																																					
				cell = getCell(sheet1, rowNo, colNo++);/*유역m2*/
				if(dataList.get(i).get("dgrha").toString().equals("")) {
					cell.setCellValue("");
				}else {					
					formulaText = "AH"+(rowNo+1)+"*10000";
					cell.setCellFormula(formulaText);
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*취약지역 면적*/
				formulaText = "AK"+(rowNo+1)+"*0.0001";
				cell.setCellFormula(formulaText);
//				if(data/*List.get(i).get("wkha").toString().equals("")) {
//					cell.setCellValue("");										
//				}else {
//					cell.setCellValue(Double.parseDouble(dataList.get(i).get("wkha").toString()));					
//				}					*/
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*취약m2*/
				formulaText = "SUM(DE"+(rowNo+1)+",DJ"+(rowNo+1)+",DO"+(rowNo+1)+",DT"+(rowNo+1)+",DY"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
//				if(dataList.get(i).get("wkha").toString().equals("")) {
//					cell.setCellValue("");
//				}else {					
//					formulaText = "AJ"+(rowNo+1)+"*10000";
//					cell.setCellFormula(formulaText);					
//				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보호시설*/
				cell.setCellValue(dataList.get(i).get("saftyfclt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보호개수*/
				cell.setCellValue(dataList.get(i).get("saftycnt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*인가*/
				cell.setCellValue(dataList.get(i).get("cnfm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*인가 개수*/
				cell.setCellValue(dataList.get(i).get("cnfmcnt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*상부주요시설*/
				cell.setCellValue(dataList.get(i).get("uprmain").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*상부인가*/
				cell.setCellValue(dataList.get(i).get("uprcnfm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*하부주요시설*/
				cell.setCellValue(dataList.get(i).get("lwrmain").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*하부인가*/
				cell.setCellValue(dataList.get(i).get("lwrcnfm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사면상태*/
				cell.setCellValue(dataList.get(i).get("sformsttus").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*모암*/
				cell.setCellValue(dataList.get(i).get("prntrck").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토성*/
				cell.setCellValue(dataList.get(i).get("soilclass").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사길이*/
				if(dataList.get(i).get("slen").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slen").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*평균경사도*/
				if(dataList.get(i).get("slopeavg").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					String value = dataList.get(i).get("slopeavg").toString().replaceAll("[^0-9]", "");
					cell.setCellValue(Double.parseDouble(value));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*최저경사*/
				if(dataList.get(i).get("slopelwt").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slopelwt").toString()));					
				}				
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*최고경사*/
				if(dataList.get(i).get("slopehgt").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slopehgt").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사범위*/
				formulaText = "IF(AX"+(rowNo+1)+"<15,\"완경사지\",IF(AX"+(rowNo+1)+"<20,\"경사지\",IF(AX"+(rowNo+1)+"<25,\"급경사지\",IF(AX"+(rowNo+1)+"<30,\"험준지\",IF(AX"+(rowNo+1)+"<90,\"절험지\",IF(AX"+(rowNo+1)+"=0,\" \",IF(AX"+(rowNo+1)+"=\"\",\" \")))))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사위치*/
				cell.setCellValue(dataList.get(i).get("slopeloc").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*1~10부*/
				if(dataList.get(i).get("arealcridge").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("arealcridge").toString().replaceAll("[^0-9]", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*최고지점*/
				if(dataList.get(i).get("hgtpnt").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("hgtpnt").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*최저지점*/
				if(dataList.get(i).get("lwtpnt").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("lwtpnt").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사면형*/
				cell.setCellValue(dataList.get(i).get("sform").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토심*/
				if(dataList.get(i).get("soildept").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(dataList.get(i).get("soildept").toString());					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*균열*/
				cell.setCellValue(dataList.get(i).get("crk").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*함몰*/
				cell.setCellValue(dataList.get(i).get("rtctn").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*융기*/
				cell.setCellValue(dataList.get(i).get("uplft").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*말단부압출*/
				cell.setCellValue(dataList.get(i).get("enppres").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*확대예상*/
				cell.setCellValue(dataList.get(i).get("expnsnprd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*임상*/
				cell.setCellValue(dataList.get(i).get("frstfr").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*임분밀도*/
				cell.setCellValue(dataList.get(i).get("dnsty").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*임분경급*/
				cell.setCellValue(dataList.get(i).get("dmclschk").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*용수상시여부*/
				cell.setCellValue(dataList.get(i).get("wtrrgrat").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*강우시용수*/
				cell.setCellValue(dataList.get(i).get("rainwtrat").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사면이축축함*/
				cell.setCellValue(dataList.get(i).get("drsform").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사면이건조함*/
				cell.setCellValue(dataList.get(i).get("wtsfrom").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*현장조사점수*/
				formulaText = "CH"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*안정해석점수*/
				formulaText = "AF"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*점수계*/
				formulaText = "SUM(BT"+(rowNo+1)+":BU"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*판정등급*/
				cell.setCellValue(dataList.get(i).get("jdggrd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*판정결과*/
				formulaText = "IF(BW"+(rowNo+1)+"=\"A등급\",\"위험\",IF(BW"+(rowNo+1)+"=\"B등급\",\"잠재적 위험\",IF(BW"+(rowNo+1)+"=\"C등급\",\"위험성 낮음\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보정*/
				cell.setCellValue(dataList.get(i).get("revisn").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*등급보정사유*/
				cell.setCellValue(dataList.get(i).get("revisnrsn").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사업 가능여부*/
				cell.setCellValue(dataList.get(i).get("taskpsblat").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대책방안*/
				cell.setCellValue(dataList.get(i).get("cntrpln").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*필요공종*/
				cell.setCellValue(dataList.get(i).get("altrv").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대피장소*/
				cell.setCellValue(dataList.get(i).get("shunt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*관리방안*/
				cell.setCellValue(dataList.get(i).get("mngpln").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*종합의견1(값없음)*/
//				cell.setCellValue(dataList.get(i).get("opinion1").toString());
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*종합의견2(값없음)*/
//				cell.setCellValue(dataList.get(i).get("opinion2").toString());
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*계*/
				formulaText = "SUM(CI"+(rowNo+1)+",CJ"+(rowNo+1)+",CK"+(rowNo+1)+",CL"+(rowNo+1)+",CM"+(rowNo+1)+",CN"+(rowNo+1)+",CO"+(rowNo+1)+",CP"+(rowNo+1)+",CQ"+(rowNo+1)+",CR"+(rowNo+1)+",CS"+(rowNo+1)+",CT"+(rowNo+1)+",CU"+(rowNo+1)+",CV"+(rowNo+1)+",CW"+(rowNo+1)+",CX"+(rowNo+1)+",CY"+(rowNo+1)+",CZ"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*피해이력*/
				if(dataList.get(i).get("dmgehistscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("dmgehistscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보호시설*/
				if(dataList.get(i).get("saftyfcltscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("saftyfcltscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사도*/
				if(dataList.get(i).get("sformsttus").toString().equals("토사")) {
					if(dataList.get(i).get("soilslopescore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("soilslopescore").toString()));					
					}
				}else{
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사면높이*/
				if(dataList.get(i).get("sformsttus").toString().equals("토사")) {
					if(dataList.get(i).get("soilsformscore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("soilsformscore").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토심*/
				if(dataList.get(i).get("sformsttus").toString().equals("토사")) {
					if(dataList.get(i).get("soildeptscore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("soildeptscore").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*종단형상*/
				if(dataList.get(i).get("sformsttus").toString().equals("토사")) {
					if(dataList.get(i).get("soilepshpscroe").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("soilepshpscroe").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사도*/
				if(dataList.get(i).get("sformsttus").toString().equals("암반")) {
					if(dataList.get(i).get("bdrckscore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrckscore").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사면높이*/
				if(dataList.get(i).get("sformsttus").toString().equals("암반")) {
					if(dataList.get(i).get("bdrcksfromscore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrcksfromscore").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*암석종류*/
				if(dataList.get(i).get("sformsttus").toString().equals("암반")) {
					if(dataList.get(i).get("bdrckkndscore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrckkndscore").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*균열상황*/
				if(dataList.get(i).get("sformsttus").toString().equals("암반")) {
					if(dataList.get(i).get("bdrckcrkscore").toString().equals("")) {					
						cell.setCellValue("");					
					}else {
						cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrckcrkscore").toString()));					
					}
				}else {
					cell.setCellValue("");	
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*산사태위험도*/
				if(dataList.get(i).get("lndsldrskscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("lndsldrskscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*용수상황*/
				if(dataList.get(i).get("wtrsttusscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("wtrsttusscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*붕괴지*/
				if(dataList.get(i).get("crkareascore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("crkareascore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*뿌리특성*/
				if(dataList.get(i).get("rootscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("rootscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*산림현황*/
				if(dataList.get(i).get("mststscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mststscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*붕괴*/
				if(dataList.get(i).get("crkscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("crkscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*불연속면방향*/
				if(dataList.get(i).get("disctnuplnscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("disctnuplnscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*풍화상태*/
				if(dataList.get(i).get("wteffdgrscore").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("wteffdgrscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적계*/
				formulaText = "SUM(DD"+(rowNo+1)+",DI"+(rowNo+1)+",DN"+(rowNo+1)+",DS"+(rowNo+1)+",DX"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번1*/
				cell.setCellValue(dataList.get(i).get("jibun1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목1*/
				cell.setCellValue(dataList.get(i).get("jimok1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적1*/
				if(dataList.get(i).get("area1").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area1").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적1*/
				if(dataList.get(i).get("incldarea1").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea1").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유1*/
				cell.setCellValue(dataList.get(i).get("own1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번2*/
				cell.setCellValue(dataList.get(i).get("jibun2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목2*/
				cell.setCellValue(dataList.get(i).get("jimok2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적2*/
				if(dataList.get(i).get("area2").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area2").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적2*/
				if(dataList.get(i).get("incldarea2").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea2").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유2*/
				cell.setCellValue(dataList.get(i).get("own2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번3*/
				cell.setCellValue(dataList.get(i).get("jibun3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목3*/
				cell.setCellValue(dataList.get(i).get("jimok3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적3*/
				if(dataList.get(i).get("area3").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area3").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적3*/
				if(dataList.get(i).get("incldarea3").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea3").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유3*/
				cell.setCellValue(dataList.get(i).get("own3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번4*/
				cell.setCellValue(dataList.get(i).get("jibun4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목4*/
				cell.setCellValue(dataList.get(i).get("jimok4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적4*/
				if(dataList.get(i).get("area4").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area4").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적4*/
				if(dataList.get(i).get("incldarea4").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea4").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유4*/
				cell.setCellValue(dataList.get(i).get("own4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번5*/
				cell.setCellValue(dataList.get(i).get("jibun5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목5*/
				cell.setCellValue(dataList.get(i).get("jimok5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적5*/
				if(dataList.get(i).get("area5").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area5").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적5*/
				if(dataList.get(i).get("incldarea5").toString().equals("")) {					
					cell.setCellValue("");					
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea5").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유5*/
				cell.setCellValue(dataList.get(i).get("own5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번1*/
				if(dataList.get(i).get("lat1D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("①");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도1*/	
				cell.setCellValue(dataList.get(i).get("lat1D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분1*/
				if(dataList.get(i).get("lat1M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat1M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초1*/
				if(dataList.get(i).get("lat1S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat1S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);					
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도1*/	
				cell.setCellValue(dataList.get(i).get("lon1D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분1*/
				if(dataList.get(i).get("lon1M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon1M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);					
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초1*/
				if(dataList.get(i).get("lon1S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon1S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);							
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이1*/
				cell.setCellValue(dataList.get(i).get("partclr1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번2*/
				if(dataList.get(i).get("lat2D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("②");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도2*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat2D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분2*/
				if(dataList.get(i).get("lat2M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat2M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초2*/
				if(dataList.get(i).get("lat2S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat2S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도2*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon2D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분2*/
				if(dataList.get(i).get("lon2M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon2M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초2*/
				if(dataList.get(i).get("lon2S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon2S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이2*/
				cell.setCellValue(dataList.get(i).get("partclr2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번3*/
				if(dataList.get(i).get("lat3D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("③");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도3*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat3D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분3*/
				if(dataList.get(i).get("lat3M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat3M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초3*/
				if(dataList.get(i).get("lat3S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat3S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도3*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon3D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분3*/
				if(dataList.get(i).get("lon3M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon3M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초3*/
				if(dataList.get(i).get("lon3S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon3S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이3*/
				cell.setCellValue(dataList.get(i).get("partclr3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번4*/
				if(dataList.get(i).get("lat4D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("④");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도4*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat4D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분4*/
				if(dataList.get(i).get("lat4M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat4M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초4*/
				if(dataList.get(i).get("lat4S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat4S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도4*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon4D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분4*/
				if(dataList.get(i).get("lon4M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon4M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초4*/
				if(dataList.get(i).get("lon4S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon4S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이4*/
				cell.setCellValue(dataList.get(i).get("partclr4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번5*/
				if(dataList.get(i).get("lat5D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("⑤");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도5*/	
				if(dataList.get(i).get("lat5D").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat5D").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분5*/
				if(dataList.get(i).get("lat5M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat5M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초5*/
				if(dataList.get(i).get("lat5S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat5S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도5*/	
				if(dataList.get(i).get("lon5D").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon5D").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분5*/
				if(dataList.get(i).get("lon5M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon5M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초5*/
				if(dataList.get(i).get("lon5S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon5S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이5*/
				cell.setCellValue(dataList.get(i).get("partclr5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*장소*/
				cell.setCellValue(dataList.get(i).get("shuntplace").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대위도*/
				if(dataList.get(i).get("evamapLatD").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLatD").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대위분*/
				if(dataList.get(i).get("evamapLatM").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLatM").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대위초*/
				if(dataList.get(i).get("evamapLatS").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatS = Double.parseDouble(dataList.get(i).get("evamapLatS").toString());
				    String result = String.format("%05.02f", formatS);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);

				cell = getCell(sheet1, rowNo, colNo++);/*대경도*/
				if(dataList.get(i).get("evamapLonD").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLonD").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대경분*/
				if(dataList.get(i).get("evamapLonM").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLonM").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대경초*/
				if(dataList.get(i).get("evamapLonS").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatS = Double.parseDouble(dataList.get(i).get("evamapLonS").toString());
				    String result = String.format("%05.02f", formatS);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대상지개요*/
				cell.setCellValue(dataList.get(i).get("sldsumry").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*현장결과1(값없음)*/
//				cell.setCellValue(dataList.get(i).get("sptrslt1").toString());
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*현장결과2*/
				cell.setCellValue(dataList.get(i).get("sptrslt2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*간략한 종합의견 및 지정사유*/
				cell.setCellValue(dataList.get(i).get("smplrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지정사유 또는 안정해석 결과 입력*/
				cell.setCellValue(dataList.get(i).get("apntrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*구역설정*/
				cell.setCellValue(dataList.get(i).get("arearslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이사항 및 진입여건*/
				cell.setCellValue(dataList.get(i).get("partclrrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*재해예방사업종 선정사유*/
				cell.setCellValue(dataList.get(i).get("dsstrprvrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*기타종합의견 주민협의 및 대체대안*/
				cell.setCellValue(dataList.get(i).get("etcrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				rowNo++;
			}
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산사태 출력 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet12(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
			
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트

		XSSFSheet sheet2 = wb.createSheet("출력");
		sheet2.setDefaultColumnWidth(10);
		
		int rowNo = 0, colNo = 0;
		getCell(sheet2, rowNo, colNo).setCellValue("기번");
		getCell(sheet2, rowNo, colNo).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+1).setCellValue("시도");
		getCell(sheet2, rowNo, colNo+1).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+2).setCellValue("시군구");
		getCell(sheet2, rowNo, colNo+2).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+3).setCellValue("읍면");
		getCell(sheet2, rowNo, colNo+3).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+4).setCellValue("리동");
		getCell(sheet2, rowNo, colNo+4).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+5).setCellValue("지번");
		getCell(sheet2, rowNo, colNo+5).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+6).setCellValue("주체");
		getCell(sheet2, rowNo, colNo+6).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+7).setCellValue("소재지");
		getCell(sheet2, rowNo, colNo+7).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+8).setCellValue("위도도");
		getCell(sheet2, rowNo, colNo+8).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+9).setCellValue("위도분");
		getCell(sheet2, rowNo, colNo+9).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+10).setCellValue("위도초");
		getCell(sheet2, rowNo, colNo+10).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+11).setCellValue("경도도");
		getCell(sheet2, rowNo, colNo+11).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+12).setCellValue("경도분");
		getCell(sheet2, rowNo, colNo+12).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+13).setCellValue("경도초");
		getCell(sheet2, rowNo, colNo+13).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+14).setCellValue("위도");
		getCell(sheet2, rowNo, colNo+14).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+15).setCellValue("경도");
		getCell(sheet2, rowNo, colNo+15).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+16).setCellValue("소속");
		getCell(sheet2, rowNo, colNo+16).setCellStyle(HeaderStyleBlue);
				
		getCell(sheet2, rowNo, colNo+17).setCellValue("직급");
		getCell(sheet2, rowNo, colNo+17).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+18).setCellValue("조사자");
		getCell(sheet2, rowNo, colNo+18).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+19).setCellValue("조사일자");
		getCell(sheet2, rowNo, colNo+19).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+20).setCellValue("연락처");
		getCell(sheet2, rowNo, colNo+20).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+21).setCellValue("건기시");
		getCell(sheet2, rowNo, colNo+21).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+22).setCellValue("건기방향");
		getCell(sheet2, rowNo, colNo+22).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+23).setCellValue("건기판정");
		getCell(sheet2, rowNo, colNo+23).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+24).setCellValue("우기시");
		getCell(sheet2, rowNo, colNo+24).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+25).setCellValue("우기방향");
		getCell(sheet2, rowNo, colNo+25).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+26).setCellValue("우기판정");
		getCell(sheet2, rowNo, colNo+26).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+27).setCellValue("습윤중량");
		getCell(sheet2, rowNo, colNo+27).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+28).setCellValue("점착");
		getCell(sheet2, rowNo, colNo+28).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+29).setCellValue("내부마찰");
		getCell(sheet2, rowNo, colNo+29).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+30).setCellValue("이격거리");
		getCell(sheet2, rowNo, colNo+30).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+31).setCellValue("유역헥타");
		getCell(sheet2, rowNo, colNo+31).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+32).setCellValue("유역제곱");
		getCell(sheet2, rowNo, colNo+32).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+33).setCellValue("취약헥타");
		getCell(sheet2, rowNo, colNo+33).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+34).setCellValue("취약제곱");
		getCell(sheet2, rowNo, colNo+34).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+35).setCellValue("보호시설");
		getCell(sheet2, rowNo, colNo+35).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+36).setCellValue("보호개수");
		getCell(sheet2, rowNo, colNo+36).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+37).setCellValue("인가");
		getCell(sheet2, rowNo, colNo+37).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+38).setCellValue("인가개수");
		getCell(sheet2, rowNo, colNo+38).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+39).setCellValue("상부주요");
		getCell(sheet2, rowNo, colNo+39).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+40).setCellValue("상부인가");
		getCell(sheet2, rowNo, colNo+40).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+41).setCellValue("하부주요");
		getCell(sheet2, rowNo, colNo+41).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+42).setCellValue("하부인가");
		getCell(sheet2, rowNo, colNo+42).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+43).setCellValue("사면");
		getCell(sheet2, rowNo, colNo+43).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+44).setCellValue("모암");
		getCell(sheet2, rowNo, colNo+44).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+45).setCellValue("토성");
		getCell(sheet2, rowNo, colNo+45).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+46).setCellValue("경사길이");
		getCell(sheet2, rowNo, colNo+46).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+47).setCellValue("평균");
		getCell(sheet2, rowNo, colNo+47).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+48).setCellValue("최저");
		getCell(sheet2, rowNo, colNo+48).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+49).setCellValue("최고");
		getCell(sheet2, rowNo, colNo+49).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+50).setCellValue("범위");
		getCell(sheet2, rowNo, colNo+50).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+51).setCellValue("위치");
		getCell(sheet2, rowNo, colNo+51).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+52).setCellValue("00부");
		getCell(sheet2, rowNo, colNo+52).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+53).setCellValue("최고지점");
		getCell(sheet2, rowNo, colNo+53).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+54).setCellValue("최저지점");
		getCell(sheet2, rowNo, colNo+54).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+55).setCellValue("사면형");
		getCell(sheet2, rowNo, colNo+55).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+56).setCellValue("토심");
		getCell(sheet2, rowNo, colNo+56).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+57).setCellValue("균열");
		getCell(sheet2, rowNo, colNo+57).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+58).setCellValue("함몰");
		getCell(sheet2, rowNo, colNo+58).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+59).setCellValue("융기");
		getCell(sheet2, rowNo, colNo+59).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+60).setCellValue("말단부");
		getCell(sheet2, rowNo, colNo+60).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+61).setCellValue("확대");
		getCell(sheet2, rowNo, colNo+61).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+62).setCellValue("임상");
		getCell(sheet2, rowNo, colNo+62).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+63).setCellValue("밀도");
		getCell(sheet2, rowNo, colNo+63).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+64).setCellValue("경급");
		getCell(sheet2, rowNo, colNo+64).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+65).setCellValue("용수");
		getCell(sheet2, rowNo, colNo+65).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+66).setCellValue("강우");
		getCell(sheet2, rowNo, colNo+66).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+67).setCellValue("축축");
		getCell(sheet2, rowNo, colNo+67).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+68).setCellValue("건조");
		getCell(sheet2, rowNo, colNo+68).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+69).setCellValue("현장점수");
		getCell(sheet2, rowNo, colNo+69).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+70).setCellValue("안정점수");
		getCell(sheet2, rowNo, colNo+70).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+71).setCellValue("점수계");
		getCell(sheet2, rowNo, colNo+71).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+72).setCellValue("등급");
		getCell(sheet2, rowNo, colNo+72).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+73).setCellValue("결과");
		getCell(sheet2, rowNo, colNo+73).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+74).setCellValue("상하향");
		getCell(sheet2, rowNo, colNo+74).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+75).setCellValue("보정사유");
		getCell(sheet2, rowNo, colNo+75).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+76).setCellValue("사업여부");
		getCell(sheet2, rowNo, colNo+76).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+77).setCellValue("대책방안");
		getCell(sheet2, rowNo, colNo+77).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+78).setCellValue("필요공종");
		getCell(sheet2, rowNo, colNo+78).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+79).setCellValue("대피장소");
		getCell(sheet2, rowNo, colNo+79).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+80).setCellValue("현상태");
		getCell(sheet2, rowNo, colNo+80).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+81).setCellValue("비구조적");
		getCell(sheet2, rowNo, colNo+81).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+82).setCellValue("구조적");
		getCell(sheet2, rowNo, colNo+82).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+83).setCellValue("종합의견1");
		getCell(sheet2, rowNo, colNo+83).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+84).setCellValue("종합의견2");
		getCell(sheet2, rowNo, colNo+84).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+85).setCellValue("현장계");
		getCell(sheet2, rowNo, colNo+85).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+86).setCellValue("피해이력");
		getCell(sheet2, rowNo, colNo+86).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+87).setCellValue("판보호");
		getCell(sheet2, rowNo, colNo+87).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+88).setCellValue("토경사");
		getCell(sheet2, rowNo, colNo+88).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+89).setCellValue("토높이");
		getCell(sheet2, rowNo, colNo+89).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+90).setCellValue("토토심");
		getCell(sheet2, rowNo, colNo+90).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+91).setCellValue("토종단");
		getCell(sheet2, rowNo, colNo+91).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+92).setCellValue("암경사");
		getCell(sheet2, rowNo, colNo+92).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+93).setCellValue("암높이");
		getCell(sheet2, rowNo, colNo+93).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+94).setCellValue("암모암");
		getCell(sheet2, rowNo, colNo+94).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+95).setCellValue("암균열");
		getCell(sheet2, rowNo, colNo+95).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+96).setCellValue("공산사태");
		getCell(sheet2, rowNo, colNo+96).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+97).setCellValue("공용수");
		getCell(sheet2, rowNo, colNo+97).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+98).setCellValue("토붕괴");
		getCell(sheet2, rowNo, colNo+98).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+99).setCellValue("토뿌리");
		getCell(sheet2, rowNo, colNo+99).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+100).setCellValue("토산림");
		getCell(sheet2, rowNo, colNo+100).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+101).setCellValue("암붕괴");
		getCell(sheet2, rowNo, colNo+101).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+102).setCellValue("암방향");
		getCell(sheet2, rowNo, colNo+102).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+103).setCellValue("암풍화");
		getCell(sheet2, rowNo, colNo+103).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+104).setCellValue("면적계");
		getCell(sheet2, rowNo, colNo+104).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+105).setCellValue("지번1");
		getCell(sheet2, rowNo, colNo+105).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+106).setCellValue("지목1");
		getCell(sheet2, rowNo, colNo+106).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+107).setCellValue("면적1");
		getCell(sheet2, rowNo, colNo+107).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+108).setCellValue("편입1");
		getCell(sheet2, rowNo, colNo+108).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+109).setCellValue("소유1");
		getCell(sheet2, rowNo, colNo+109).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+110).setCellValue("지번2");
		getCell(sheet2, rowNo, colNo+110).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+111).setCellValue("지목2");
		getCell(sheet2, rowNo, colNo+111).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+112).setCellValue("면적2");
		getCell(sheet2, rowNo, colNo+112).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+113).setCellValue("편입2");
		getCell(sheet2, rowNo, colNo+113).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+114).setCellValue("소유2");
		getCell(sheet2, rowNo, colNo+114).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+115).setCellValue("지번3");
		getCell(sheet2, rowNo, colNo+115).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+116).setCellValue("지목3");
		getCell(sheet2, rowNo, colNo+116).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+117).setCellValue("면적3");
		getCell(sheet2, rowNo, colNo+117).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+118).setCellValue("편입3");
		getCell(sheet2, rowNo, colNo+118).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+119).setCellValue("소유3");
		getCell(sheet2, rowNo, colNo+119).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+120).setCellValue("지번4");
		getCell(sheet2, rowNo, colNo+120).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+121).setCellValue("지목4");
		getCell(sheet2, rowNo, colNo+121).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+122).setCellValue("면적4");
		getCell(sheet2, rowNo, colNo+122).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+123).setCellValue("편입4");
		getCell(sheet2, rowNo, colNo+123).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+124).setCellValue("소유4");
		getCell(sheet2, rowNo, colNo+124).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+125).setCellValue("지번5");
		getCell(sheet2, rowNo, colNo+125).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+126).setCellValue("지목5");
		getCell(sheet2, rowNo, colNo+126).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+127).setCellValue("면적5");
		getCell(sheet2, rowNo, colNo+127).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+128).setCellValue("편입5");
		getCell(sheet2, rowNo, colNo+128).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+129).setCellValue("소유5");
		getCell(sheet2, rowNo, colNo+129).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+130).setCellValue("연번1");
		getCell(sheet2, rowNo, colNo+130).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+131).setCellValue("위도1");
		getCell(sheet2, rowNo, colNo+131).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+132).setCellValue("경도1");
		getCell(sheet2, rowNo, colNo+132).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+133).setCellValue("특이1");
		getCell(sheet2, rowNo, colNo+133).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+134).setCellValue("연번2");
		getCell(sheet2, rowNo, colNo+134).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+135).setCellValue("위도2");
		getCell(sheet2, rowNo, colNo+135).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+136).setCellValue("경도2");
		getCell(sheet2, rowNo, colNo+136).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+137).setCellValue("특이2");
		getCell(sheet2, rowNo, colNo+137).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+138).setCellValue("연번3");
		getCell(sheet2, rowNo, colNo+138).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+139).setCellValue("위도3");
		getCell(sheet2, rowNo, colNo+139).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+140).setCellValue("경도3");
		getCell(sheet2, rowNo, colNo+140).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+141).setCellValue("특이3");
		getCell(sheet2, rowNo, colNo+141).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+142).setCellValue("연번4");
		getCell(sheet2, rowNo, colNo+142).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+143).setCellValue("위도4");
		getCell(sheet2, rowNo, colNo+143).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+144).setCellValue("경도4");
		getCell(sheet2, rowNo, colNo+144).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+145).setCellValue("특이4");
		getCell(sheet2, rowNo, colNo+145).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+146).setCellValue("연번5");
		getCell(sheet2, rowNo, colNo+146).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+147).setCellValue("위도5");
		getCell(sheet2, rowNo, colNo+147).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+148).setCellValue("경도5");
		getCell(sheet2, rowNo, colNo+148).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+149).setCellValue("특이5");
		getCell(sheet2, rowNo, colNo+149).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+150).setCellValue("대피장소");
		getCell(sheet2, rowNo, colNo+150).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+151).setCellValue("대위도");
		getCell(sheet2, rowNo, colNo+151).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+152).setCellValue("대경도");
		getCell(sheet2, rowNo, colNo+152).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+153).setCellValue("텍개요");
		getCell(sheet2, rowNo, colNo+153).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+154).setCellValue("텍결과1");
		getCell(sheet2, rowNo, colNo+154).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+155).setCellValue("텍결과2");
		getCell(sheet2, rowNo, colNo+155).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+156).setCellValue("텍사유1");
		getCell(sheet2, rowNo, colNo+156).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+157).setCellValue("텍사유2");
		getCell(sheet2, rowNo, colNo+157).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+158).setCellValue("텍구역");
		getCell(sheet2, rowNo, colNo+158).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+159).setCellValue("텍특이");
		getCell(sheet2, rowNo, colNo+159).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+160).setCellValue("텍사업");
		getCell(sheet2, rowNo, colNo+160).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+161).setCellValue("텍주민");
		getCell(sheet2, rowNo, colNo+161).setCellStyle(HeaderStyleGreen3);
		
		rowNo=1;
		String formulaText = "";
		String formulaTextM = "";
		String formulaTextS = "";
		
		if(dataList.size() > 0) {
			for (int i=0; i<dataList.size(); i++) {				
				colNo=0;
				cell = getCell(sheet2, rowNo, colNo++);/*기번*/
				formulaText = "입력값!A"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*시도*/
				formulaText = "입력값!D"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*시군구*/
				formulaText = "입력값!E"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*읍면*/
				formulaText = "입력값!F"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*리동*/
				formulaText = "입력값!G"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번*/
				formulaText = "입력값!H"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*주체*/
				formulaText = "입력값!I"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소재지*/
				formulaText = "B"+(rowNo+1)+"&\" \"&C"+(rowNo+1)+"&\" \"&D"+(rowNo+1)+"&\" \"&E"+(rowNo+1)+"&\" \"&F"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도도*/
				formulaText = "입력값!J"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도분*/
				formulaText = "입력값!K"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도초*/
				formulaText = "입력값!L"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도도*/
				formulaText = "입력값!M"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도분*/
				formulaText = "입력값!N"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도초*/
				formulaText = "입력값!O"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도*/
				
				formulaTextM = "입력값!K"+(rowNo+4);
				cell.setCellFormula(formulaTextM);
				cell.setCellStyle(ValueStyle2);
				String cellM = cell.getCellFormula();
				System.out.println("cellM : " + cellM);
				
				formulaTextS = "입력값!L"+(rowNo+4);
				cell.setCellFormula(formulaTextS);
				cell.setCellStyle(ValueStyle3);
				String cellS = cell.getCellFormula();
				System.out.println("cellS : " + cellS);
				
				
				
				formulaText = "입력값!J"+(rowNo+4)+"&\"°\"&\" \"&입력값!K"+(rowNo+4)+"&\"′\"&\" \"&입력값!L"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도*/
				formulaText = "입력값!M"+(rowNo+4)+"&\"°\"&\" \"&입력값!N"+(rowNo+4)+"&\"′\"&\" \"&입력값!O"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소속*/
				formulaText = "입력값!P"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*직급*/
				formulaText = "입력값!Q"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*조사자*/
				formulaText = "입력값!R"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*조사일자*/
				formulaText = "입력값!S"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연락처*/
				formulaText = "입력값!T"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*건기시*/
				formulaText = "입력값!U"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*건기방향*/
				formulaText = "입력값!V"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*건기판정*/
				formulaText = "입력값!W"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*우기시*/
				formulaText = "입력값!Y"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*우기방향*/
				formulaText = "입력값!Z"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*우기판정*/
				formulaText = "입력값!AA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*습윤중량*/
				formulaText = "입력값!AC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*점착*/
				formulaText = "입력값!AD"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*내부마찰*/
				formulaText = "입력값!AE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*이격거리*/
				formulaText = "입력값!AG"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*유역헥타*/
				formulaText = "입력값!AH"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*유역제곱*/
				formulaText = "입력값!AI"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*취약헥타*/
				formulaText = "입력값!AJ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*취약제곱*/
				formulaText = "입력값!AK"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*보호시설*/
				formulaText = "입력값!AL"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*보호개수*/
				formulaText = "IF(입력값!AM"+(rowNo+4)+"=0,0,IF(입력값!AM"+(rowNo+4)+"=\"-\",\"0\",입력값!AM"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*인가*/
				formulaText = "입력값!AN"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*인가개수*/
				formulaText = "IF(입력값!AO"+(rowNo+4)+"=0,0,IF(입력값!AO"+(rowNo+4)+"=\"-\",\"0\",입력값!AO"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*상부주요*/
				formulaText = "입력값!AP"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*상부인가*/
				formulaText = "IF(입력값!AQ"+(rowNo+4)+"=0,0,IF(입력값!AQ"+(rowNo+4)+"=\"-\",\"0\",입력값!AQ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*하부주요*/
				formulaText = "입력값!AR"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*하부인가*/
				formulaText = "IF(입력값!AS"+(rowNo+4)+"=0,0,IF(입력값!AS"+(rowNo+4)+"=\"-\",\"0\",입력값!AS"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*사면*/
				formulaText = "입력값!AT"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*모암*/
				formulaText = "입력값!AU"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토성*/
				formulaText = "입력값!AV"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경사길이*/
				formulaText = "입력값!AW"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*평균*/
				formulaText = "입력값!AX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*최저*/
				formulaText = "입력값!AY"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*최고*/
				formulaText = "입력값!AZ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*범위*/
				formulaText = "입력값!BA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위치*/
				formulaText = "입력값!BB"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*00부*/
				formulaText = "입력값!BC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*최고지점*/
				formulaText = "입력값!BD"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*최저지점*/
				formulaText = "입력값!BE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*사면형*/
				formulaText = "입력값!BF"+(rowNo+4)+"&\"사면\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토심*/
				formulaText = "입력값!BG"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*균열*/
				formulaText = "입력값!BH"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*함몰*/
				formulaText = "입력값!BI"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*융기*/
				formulaText = "입력값!BJ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*말단부*/
				formulaText = "입력값!BK"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*확대*/
				formulaText = "입력값!BL"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
		        cell = getCell(sheet2, rowNo, colNo++);/*임상*/
		        formulaText = "IF(입력값!BM"+(rowNo+4)+"=\"침\",\"침엽수림\",IF(입력값!BM"+(rowNo+4)+"=\"활\",\"활엽수림\",IF(입력값!BM"+(rowNo+4)+"=\"혼\",\"혼효림\",IF(입력값!BM"+(rowNo+4)+"=\"무\",\"무입목지\"))))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet2, rowNo, colNo++);/*임상밀도*/
		        formulaText = "입력값!BN"+(rowNo+4);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet2, rowNo, colNo++);/*임상경급*/
		        formulaText = "IF(입력값!BO"+(rowNo+4)+"=\"치\",\"치수림\",IF(입력값!BO"+(rowNo+4)+"=\"소\",\"소경목\",IF(입력값!BO"+(rowNo+4)+"=\"중\",\"중경목\",IF(입력값!BO"+(rowNo+4)+"=\"대\",\"대경목\"))))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*용수*/
				formulaText = "입력값!BP"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*강우*/
				formulaText = "입력값!BQ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*축축*/
				formulaText = "입력값!BR"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*건조*/
				formulaText = "입력값!BS"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*현장점수*/
				formulaText = "입력값!BT"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*안정점수*/
				formulaText = "입력값!BU"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*점수계*/
				formulaText = "입력값!BV"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*등급*/
				formulaText = "IF(입력값!BW"+(rowNo+4)+"=\"A등급\",\"A\",IF(입력값!BW"+(rowNo+4)+"=\"B등급\",\"B\",IF(입력값!BW"+(rowNo+4)+"=\"C등급\",\"C\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*결과*/
				formulaText = "입력값!BX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*상하향*/
				formulaText = "IF(입력값!BY"+(rowNo+4)+"=\"상\",\"상향\",IF(입력값!BY"+(rowNo+4)+"=\"-\",\"-\",IF(입력값!BY"+(rowNo+4)+"=0,\"-\",IF(입력값!BY"+(rowNo+4)+"=\"하\",\"하향\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*보정사유*/
				formulaText = "IF(입력값!BZ"+(rowNo+4)+"=\"-\",\"-\",IF(입력값!BZ"+(rowNo+4)+"=0,\" \",입력값!BZ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*사업여부*/
				formulaText = "입력값!CA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대책방안*/
				formulaText = "입력값!CB"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*필요공종*/
				formulaText = "입력값!CC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대피장소*/
				formulaText = "입력값!CD"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*현상태*/
				formulaText = "IF(입력값!CE"+(rowNo+4)+"=\"현 상태 유지\",\"○\",IF(입력값!CE"+(rowNo+4)+"=\"-\",\"\",IF(입력값!CE"+(rowNo+4)+"=0,\"\",IF(입력값!CE"+(rowNo+4)+"=\"비구조적(대피체계구축필요)\",\"\",IF(입력값!CE"+(rowNo+4)+"=\"구조적+비구조적(적극적인 관리필요)\",\"\")))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*비구조적*/
				formulaText = "IF(입력값!CE"+(rowNo+4)+"=\"비구조적(대피체계구축필요)\",\"○\",IF(입력값!CE"+(rowNo+4)+"=\"-\",\"\",IF(입력값!CE"+(rowNo+4)+"=0,\"\",IF(입력값!CE"+(rowNo+4)+"=\"현 상태 유지\",\"\",IF(입력값!CE"+(rowNo+4)+"=\"구조적+비구조적(적극적인 관리필요)\",\"\")))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*구조적*/
				formulaText = "IF(입력값!CE"+(rowNo+4)+"=\"구조적+비구조적(적극적인 관리필요)\",\"○\",IF(입력값!CE"+(rowNo+4)+"=\"-\",\"\",IF(입력값!CE"+(rowNo+4)+"=0,\"\",IF(입력값!CE"+(rowNo+4)+"=\"현 상태 유지\",\"\",IF(입력값!CE"+(rowNo+4)+"=\"비구조적(대피체계구축필요)\",\"\")))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*종합의견1*/
				formulaText = "IF(입력값!CF"+(rowNo+4)+"=\"-\",\" \",IF(입력값!CF"+(rowNo+4)+"=0,\" \",입력값!CF"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*종합의견2*/
				formulaText = "IF(입력값!CG"+(rowNo+4)+"=\"-\",\" \",IF(입력값!CG"+(rowNo+4)+"=0,\" \",입력값!CG"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*현장계*/
				formulaText = "입력값!CH"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*피해이력*/
				formulaText = "입력값!CI"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판보호*/
				formulaText = "입력값!CJ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토경사*/
				formulaText = "입력값!CK"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토높이*/
				formulaText = "입력값!CL"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토토심*/
				formulaText = "입력값!CM"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토종단*/
				formulaText = "입력값!CN"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암경사*/
				formulaText = "입력값!CO"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암높이*/
				formulaText = "입력값!CP"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암모암*/
				formulaText = "입력값!CQ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암균열*/
				formulaText = "입력값!CR"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*공산사태*/
				formulaText = "입력값!CS"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*공용수*/
				formulaText = "입력값!CT"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토붕괴*/
				formulaText = "입력값!CU"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토뿌리*/
				formulaText = "입력값!CV"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토산림*/
				formulaText = "입력값!CW"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암붕괴*/
				formulaText = "입력값!CX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암방향*/
				formulaText = "입력값!CY"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암풍화*/
				formulaText = "입력값!CZ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적계*/
				formulaText = "입력값!DA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번1*/
				formulaText = "IF(입력값!DB"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DB"+(rowNo+4)+"=0,\" \",입력값!DB"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목1*/
				formulaText = "IF(입력값!DC"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DC"+(rowNo+4)+"=0,\" \",입력값!DC"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적1*/
				formulaText = "IF(입력값!DD"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DD"+(rowNo+4)+"=0,\" \",입력값!DD"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입1*/
				formulaText = "IF(입력값!DE"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DE"+(rowNo+4)+"=0,\" \",입력값!DE"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유1*/
				formulaText = "IF(입력값!DF"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DF"+(rowNo+4)+"=0,\" \",입력값!DF"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번2*/
				formulaText = "IF(입력값!DG"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DG"+(rowNo+4)+"=0,\" \",입력값!DG"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목2*/
				formulaText = "IF(입력값!DH"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DH"+(rowNo+4)+"=0,\" \",입력값!DH"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적2*/
				formulaText = "IF(입력값!DI"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DI"+(rowNo+4)+"=0,\" \",입력값!DI"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입2*/
				formulaText = "IF(입력값!DJ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DJ"+(rowNo+4)+"=0,\" \",입력값!DJ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유2*/
				formulaText = "IF(입력값!DK"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DK"+(rowNo+4)+"=0,\" \",입력값!DK"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번3*/
				formulaText = "IF(입력값!DL"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DL"+(rowNo+4)+"=0,\" \",입력값!DL"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목3*/
				formulaText = "IF(입력값!DM"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DM"+(rowNo+4)+"=0,\" \",입력값!DM"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적3*/
				formulaText = "IF(입력값!DN"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DN"+(rowNo+4)+"=0,\" \",입력값!DN"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입3*/
				formulaText = "IF(입력값!DO"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DO"+(rowNo+4)+"=0,\" \",입력값!DO"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유3*/
				formulaText = "IF(입력값!DP"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DP"+(rowNo+4)+"=0,\" \",입력값!DP"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번4*/
				formulaText = "IF(입력값!DQ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DQ"+(rowNo+4)+"=0,\" \",입력값!DQ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목4*/
				formulaText = "IF(입력값!DR"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DR"+(rowNo+4)+"=0,\" \",입력값!DR"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적4*/
				formulaText = "IF(입력값!DS"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DS"+(rowNo+4)+"=0,\" \",입력값!DS"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입4*/
				formulaText = "IF(입력값!DT"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DT"+(rowNo+4)+"=0,\" \",입력값!DT"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유4*/
				formulaText = "IF(입력값!DU"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DU"+(rowNo+4)+"=0,\" \",입력값!DU"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번5*/
				formulaText = "IF(입력값!DV"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DV"+(rowNo+4)+"=0,\" \",입력값!DV"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목5*/
				formulaText = "IF(입력값!DW"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DW"+(rowNo+4)+"=0,\" \",입력값!DW"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적5*/
				formulaText = "IF(입력값!DX"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DX"+(rowNo+4)+"=0,\" \",입력값!DX"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입5*/
				formulaText = "IF(입력값!DY"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DY"+(rowNo+4)+"=0,\" \",입력값!DY"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유5*/
				formulaText = "IF(입력값!DZ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DZ"+(rowNo+4)+"=0,\" \",입력값!DZ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번1*/
				formulaText = "IF(입력값!EA"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EA"+(rowNo+4)+"=0,\" \",입력값!EA"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도1*/
				formulaText = "입력값!EB"+(rowNo+4)+"&\"°\"&\" \"&입력값!EC"+(rowNo+4)+"&\"′\"&\" \"&입력값!ED"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도1*/
				formulaText = "입력값!EE"+(rowNo+4)+"&\"°\"&\" \"&입력값!EF"+(rowNo+4)+"&\"′\"&\" \"&입력값!EG"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이1*/
				formulaText = "IF(입력값!EH"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EH"+(rowNo+4)+"=0,\" \",입력값!EH"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번2*/
				formulaText = "IF(입력값!EI"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EI"+(rowNo+4)+"=0,\" \",입력값!EI"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도2*/
				formulaText = "입력값!EJ"+(rowNo+4)+"&\"°\"&\" \"&입력값!EK"+(rowNo+4)+"&\"′\"&\" \"&입력값!EL"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도2*/
				formulaText = "입력값!EM"+(rowNo+4)+"&\"°\"&\" \"&입력값!EN"+(rowNo+4)+"&\"′\"&\" \"&입력값!EO"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이2*/
				formulaText = "IF(입력값!EP"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EP"+(rowNo+4)+"=0,\" \",입력값!EP"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번3*/
				formulaText = "IF(입력값!EQ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EQ"+(rowNo+4)+"=0,\" \",입력값!EQ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도3*/
				formulaText = "입력값!ER"+(rowNo+4)+"&\"°\"&\" \"&입력값!ES"+(rowNo+4)+"&\"′\"&\" \"&입력값!ET"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도3*/
				formulaText = "입력값!EU"+(rowNo+4)+"&\"°\"&\" \"&입력값!EV"+(rowNo+4)+"&\"′\"&\" \"&입력값!EW"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이3*/
				formulaText = "IF(입력값!EX"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EX"+(rowNo+4)+"=0,\" \",입력값!EX"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번4*/
				formulaText = "IF(입력값!EY"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EY"+(rowNo+4)+"=0,\" \",입력값!EY"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도4*/
				formulaText = "입력값!EZ"+(rowNo+4)+"&\"°\"&\" \"&입력값!FA"+(rowNo+4)+"&\"′\"&\" \"&입력값!FB"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도4*/
				formulaText = "입력값!FC"+(rowNo+4)+"&\"°\"&\" \"&입력값!FD"+(rowNo+4)+"&\"′\"&\" \"&입력값!FE"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이4*/
				formulaText = "IF(입력값!FF"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FF"+(rowNo+4)+"=0,\" \",입력값!FF"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번5*/
				formulaText = "IF(입력값!FG"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FG"+(rowNo+4)+"=0,\" \",입력값!FG"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도5*/
				formulaText = "입력값!FH"+(rowNo+4)+"&\"°\"&\" \"&입력값!FI"+(rowNo+4)+"&\"′\"&\" \"&입력값!FJ"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도5*/
				formulaText = "입력값!FK"+(rowNo+4)+"&\"°\"&\" \"&입력값!FL"+(rowNo+4)+"&\"′\"&\" \"&입력값!FM"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이5*/
				formulaText = "IF(입력값!FN"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FN"+(rowNo+4)+"=0,\" \",입력값!FN"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				//HBLEE 20230620  
				cell = getCell(sheet2, rowNo, colNo++);/*대피장소*/
				formulaText = "입력값!FO"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대위도*/
				formulaText = "입력값!FP"+(rowNo+4)+"&\"°\"&\" \"&입력값!FQ"+(rowNo+4)+"&\"′\"&\" \"&입력값!FR"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대경도*/
				formulaText = "입력값!FS"+(rowNo+4)+"&\"°\"&\" \"&입력값!FT"+(rowNo+4)+"&\"′\"&\" \"&입력값!FU"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍개요*/
				formulaText = "IF(입력값!FV"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FV"+(rowNo+4)+"=\"\",\"\",IF(입력값!FV"+(rowNo+4)+"=0,\" \",입력값!FV"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍결과1*/
				formulaText = "IF(입력값!FW"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FW"+(rowNo+4)+"=\"\",\"\",IF(입력값!FW"+(rowNo+4)+"=0,\" \",입력값!FW"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍결과2*/
				formulaText = "IF(입력값!FX"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FQ"+(rowNo+4)+"=\"\",\"\",IF(입력값!FQ"+(rowNo+4)+"=0,\" \",입력값!FQ"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍사유1*/
				formulaText = "IF(입력값!FY"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FY"+(rowNo+4)+"=\"\",\"\",IF(입력값!FY"+(rowNo+4)+"=0,\" \",입력값!FY"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍사유2*/
				formulaText = "IF(입력값!FZ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FZ"+(rowNo+4)+"=\"\",\"\",IF(입력값!FZ"+(rowNo+4)+"=0,\" \",입력값!FZ"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍구역*/
				formulaText = "IF(입력값!GA"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GA"+(rowNo+4)+"=\"\",\"\",IF(입력값!GA"+(rowNo+4)+"=0,\" \",입력값!GA"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍특이*/
				formulaText = "IF(입력값!GB"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GB"+(rowNo+4)+"=\"\",\"\",IF(입력값!GB"+(rowNo+4)+"=0,\" \",입력값!GB"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍사업*/
				formulaText = "IF(입력값!GC"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GC"+(rowNo+4)+"=\"\",\"\",IF(입력값!GC"+(rowNo+4)+"=0,\" \",입력값!GC"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍주민*/
				formulaText = "IF(입력값!GD"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GD"+(rowNo+4)+"=\"\",\"\",IF(입력값!GD"+(rowNo+4)+"=0,\" \",입력값!GD"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				rowNo++;
			}
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산사태 취약지역관리 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet13(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		String[] legaldongcdList =  (String[]) dataMap.get("legaldongcdList"); // 법정동코드가 담긴 리스트를
		String[] mnagncdList =  (String[]) dataMap.get("mnagncdList"); // 산사태관리기관코드가 담긴 리스트를
		
		XSSFSheet sheet3 = wb.createSheet("취약지역관리");
		sheet3.setDefaultColumnWidth(17);
		sheet3.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet3, rowNo, colNo).setCellValue("입력후 삭제");
		getCell(sheet3, rowNo, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo, colNo+1).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+1).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+2).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+2).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+3).setCellValue("모두 WEAK021 사용");
		getCell(sheet3, rowNo, colNo+3).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet3, rowNo, colNo+4).setCellValue("모두 FLDABS2022 사용");
		getCell(sheet3, rowNo, colNo+4).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet3, rowNo, colNo+5).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+5).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+6).setCellValue("미입력");
		getCell(sheet3, rowNo, colNo+6).setCellStyle(HeaderStyleGray);
		
		getCell(sheet3, rowNo, colNo+7).setCellValue("자동입력");
		getCell(sheet3, rowNo, colNo+7).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet3, rowNo, colNo+8).setCellValue("미입력");
		getCell(sheet3, rowNo, colNo+8).setCellStyle(HeaderStyleGray);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo, colNo+9).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+9).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+10).setCellValue("미입력");
		getCell(sheet3, rowNo, colNo+10).setCellStyle(HeaderStyleGray);
		
		getCell(sheet3, rowNo, colNo+11).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+11).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo+1, colNo).setCellValue("기번");
		getCell(sheet3, rowNo+1, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+1).setCellValue("시선IT 문의");
		getCell(sheet3, rowNo+1, colNo+1).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+2).setCellValue("법정동코드 검색 입력");
		getCell(sheet3, rowNo+1, colNo+2).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+3).setCellValue("분류코드 참조");
		getCell(sheet3, rowNo+1, colNo+3).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+4).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+4).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+5).setCellValue("협회ID");
		getCell(sheet3, rowNo+1, colNo+5).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+6).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+6).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+7).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+7).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+8).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+8).setCellStyle(HeaderStyle);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+1, colNo+9).setCellValue("분류코드 참조");
		getCell(sheet3, rowNo+1, colNo+9).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+10).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+10).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+11).setCellValue("분류코드 참조");
		getCell(sheet3, rowNo+1, colNo+11).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+2, colNo).setCellValue("");
		getCell(sheet3, rowNo+2, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+2, colNo+1).setCellValue("업체에서 ID부여");
		getCell(sheet3, rowNo+2, colNo+1).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+2).setCellValue("법정동코드 10자리");
		getCell(sheet3, rowNo+2, colNo+2).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+3).setCellValue("최초, 변경 등 <분류참조>");
		getCell(sheet3, rowNo+2, colNo+3).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+4).setCellValue("");
		getCell(sheet3, rowNo+2, colNo+4).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+5).setCellValue("협회ID");
		getCell(sheet3, rowNo+2, colNo+5).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+6).setCellValue("공백");
		getCell(sheet3, rowNo+2, colNo+6).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+7).setCellValue("날짜8자리");
		getCell(sheet3, rowNo+2, colNo+7).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+8).setCellValue("공백");
		getCell(sheet3, rowNo+2, colNo+8).setCellStyle(HeaderStyleYellow);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+2, colNo+9).setCellValue("관리기관코드 <분류참조>");
		getCell(sheet3, rowNo+2, colNo+9).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+10).setCellValue("공백");
		getCell(sheet3, rowNo+2, colNo+10).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+11).setCellValue("관리기관코드 <분류참조>");
		getCell(sheet3, rowNo+2, colNo+11).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+3, colNo).setCellValue("");
		getCell(sheet3, rowNo+3, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+3, colNo+1).setCellValue("취약지역조사ID");
		getCell(sheet3, rowNo+3, colNo+1).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+2).setCellValue("취약지역조사지역코드");
		getCell(sheet3, rowNo+3, colNo+2).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+3).setCellValue("취약지역조사상태코드");
		getCell(sheet3, rowNo+3, colNo+3).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+4).setCellValue("취약지역조사대장구분코드");
		getCell(sheet3, rowNo+3, colNo+4).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+5).setCellValue("등록자ID");
		getCell(sheet3, rowNo+3, colNo+5).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+6).setCellValue("수정자ID");
		getCell(sheet3, rowNo+3, colNo+6).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+7).setCellValue("최초등록일");
		getCell(sheet3, rowNo+3, colNo+7).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+8).setCellValue("최종수정일");
		getCell(sheet3, rowNo+3, colNo+8).setCellStyle(HeaderStylePink2);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+3, colNo+9).setCellValue("산사태시행청코드");
		getCell(sheet3, rowNo+3, colNo+9).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+10).setCellValue("취약지역야장ID");
		getCell(sheet3, rowNo+3, colNo+10).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+11).setCellValue("산사태관리주체코드");
		getCell(sheet3, rowNo+3, colNo+11).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+4, colNo).setCellValue("");
		getCell(sheet3, rowNo+4, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+1).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet3, rowNo+4, colNo+1).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+2).setCellValue("VNARA_EXMNN_ARCD");
		getCell(sheet3, rowNo+4, colNo+2).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+3).setCellValue("VNARA_EXMNN_STCD");
		getCell(sheet3, rowNo+4, colNo+3).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+4).setCellValue("VNARA_EXMNN_RGSTR_TPCD");
		getCell(sheet3, rowNo+4, colNo+4).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+5).setCellValue("RGTER_ID");
		getCell(sheet3, rowNo+4, colNo+5).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+6).setCellValue("UPUSR_ID");
		getCell(sheet3, rowNo+4, colNo+6).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+7).setCellValue("FRST_RGDT");
		getCell(sheet3, rowNo+4, colNo+7).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+8).setCellValue("LAST_UPDDT");
		getCell(sheet3, rowNo+4, colNo+8).setCellStyle(HeaderStyle);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+4, colNo+9).setCellValue("LNDSL_MNAGN_CD");
		getCell(sheet3, rowNo+4, colNo+9).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+10).setCellValue("VNARA_FLNT_ID");
		getCell(sheet3, rowNo+4, colNo+10).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+11).setCellValue("VNARA_MNGME_CD");
		getCell(sheet3, rowNo+4, colNo+11).setCellStyle(HeaderStyle); 
		
		rowNo=5;
		String formulaText = "";

		if(dataList.size() > 0) {

		    //hblee(산사태 취약지역 관리)
		    for (int i=0; i<dataList.size(); i++) {
		        colNo=0;
		        
		        cell = getCell(sheet3, rowNo, colNo++);/*기번*/
		        formulaText = "입력값!A"+(rowNo);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사ID*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사지역코드*/
		        if(legaldongcdList[i] != null && legaldongcdList[i].toString().length() > 0) {
					cell.setCellValue(legaldongcdList[i].toString());					
				}else {					
					cell.setCellValue("");					
				}
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사상태코드*/
		        cell.setCellValue("WEAK021");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사대장구분코드*/
		        formulaText = "\"FLDABS\"&LEFT(SUBSTITUTE(입력값!S"+(rowNo)+",\". \",\"\"),4)";
				cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*등록자ID*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*수정자ID*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*최초등록일*/
		        formulaText = "LEFT(SUBSTITUTE(입력값!S"+(rowNo)+",\". \",\"\"),8)";
				cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*최종수정일*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*산사태시행청코드*/
		        if(mnagncdList[i] != null && mnagncdList[i].toString().length() > 0) {
					cell.setCellValue(mnagncdList[i].toString());					
				}else {					
					cell.setCellValue("");					
				}
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet3, rowNo, colNo++);/*취약지역야장ID*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);
		        
		        cell = getCell(sheet3, rowNo, colNo++);/*산사태관리주체코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        rowNo++;
		    }
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산사태 취약지역조사야장 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet14(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		
		XSSFSheet sheet4 = wb.createSheet("취약지역조사야장");
		sheet4.setDefaultColumnWidth(17);
		sheet4.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet4, rowNo, colNo).setCellValue("이동시 삭제");
		getCell(sheet4, rowNo, colNo).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo, colNo+1).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+1).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+2).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+2).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+3).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+3).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+4).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+4).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+5).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+5).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+6).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+6).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+7).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+7).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+8).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+8).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+9).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+9).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+10).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+10).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+11).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+11).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+12).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+12).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+13).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+13).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+14).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+14).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+15).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+15).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+16).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+16).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+17).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+17).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+18).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+18).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+19).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+19).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+20).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+20).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+21).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+21).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+22).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+22).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+23).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+24).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+24).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+25).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+25).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+26).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+26).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+27).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+27).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+28).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+28).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+29).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+29).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+30).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+30).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+31).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+31).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+32).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+32).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+33).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+33).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+34).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+34).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+35).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+35).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+36).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+36).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+37).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+37).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+38).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+38).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+39).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+39).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+40).setCellValue("자동입력");;   
		getCell(sheet4, rowNo, colNo+40).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+41).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+41).setCellStyle(HeaderStyleGray);   
		getCell(sheet4, rowNo, colNo+42).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+42).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+43).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+43).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+44).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+44).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+45).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+45).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+46).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+46).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+47).setCellValue("직접입력");
		getCell(sheet4, rowNo, colNo+47).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+48).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+48).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+49).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+49).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+50).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+50).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+51).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+51).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+52).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+52).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+53).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+53).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+54).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+54).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+55).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+55).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+56).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+56).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+57).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+57).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+58).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+58).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+59).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+59).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+60).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+60).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+61).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+61).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+62).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+62).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+63).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+63).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+64).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+64).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+65).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+65).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+66).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+66).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+67).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+67).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+68).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+68).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+69).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+69).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+70).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+70).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+71).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+71).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+72).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+72).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+73).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+73).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+74).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+74).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+75).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+75).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+76).setCellValue("직접입력 / N Y");
		getCell(sheet4, rowNo, colNo+76).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+77).setCellValue("직접입력 / N Y");
		getCell(sheet4, rowNo, colNo+77).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+78).setCellValue("직접입력 / N Y");
		getCell(sheet4, rowNo, colNo+78).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+79).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+79).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+80).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+80).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+81).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+81).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+82).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+82).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+83).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+83).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+84).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+84).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+85).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+85).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+86).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+86).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+87).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+87).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+88).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+88).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+89).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+89).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+90).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+90).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+91).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+91).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+92).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+92).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+93).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+93).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+94).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+94).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+95).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+95).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+96).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+96).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+97).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+97).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+98).setCellValue("모두 N");
		getCell(sheet4, rowNo, colNo+98).setCellStyle(HeaderStyleGreen3);
		getCell(sheet4, rowNo, colNo+99).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+99).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+100).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+100).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+101).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+101).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+102).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+102).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+103).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+103).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+104).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+104).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+105).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+105).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+106).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+106).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+107).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+107).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+108).setCellValue("직접입력 / 맑음, 비, 눈");
		getCell(sheet4, rowNo, colNo+108).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+109).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+109).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+110).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+110).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+111).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+111).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+112).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+112).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+113).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+113).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+114).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+114).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+115).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+115).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+116).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+116).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+117).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+117).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+118).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+118).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+119).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+119).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+120).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+120).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+121).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+121).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+122).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+122).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+123).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+123).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+124).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+124).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+125).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+125).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+126).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+126).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+127).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+127).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+128).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+128).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+129).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+129).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+130).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+131).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+132).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+133).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+134).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+134).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+135).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+135).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+136).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+136).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+137).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+137).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+138).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+138).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+139).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+139).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+140).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+140).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+141).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+141).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+142).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+142).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+143).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+143).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+144).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+144).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+145).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+145).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+146).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+146).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+147).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+147).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+148).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+148).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+149).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+149).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+150).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+150).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+151).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+151).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+152).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+152).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+153).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+153).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+154).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+154).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+155).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+155).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+156).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+156).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+157).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+157).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+158).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+158).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+159).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+159).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+160).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+160).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+161).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+162).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+163).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+164).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+164).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+165).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+166).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+166).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+167).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+167).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+168).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+168).setCellStyle(HeaderStyleSky);

		getCell(sheet4, rowNo+1, colNo).setCellValue("");
		getCell(sheet4, rowNo+1, colNo).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+1, colNo+1).setCellValue("소속명");
		getCell(sheet4, rowNo+1, colNo+1).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+2).setCellValue("조사자");
		getCell(sheet4, rowNo+1, colNo+2).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+3).setCellValue("전화번호");
		getCell(sheet4, rowNo+1, colNo+3).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+4).setCellValue("조사일자");
		getCell(sheet4, rowNo+1, colNo+4).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+5).setCellValue("유역면적");
		getCell(sheet4, rowNo+1, colNo+5).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+6).setCellValue("보호시설유무");
		getCell(sheet4, rowNo+1, colNo+6).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+7).setCellValue("보호시설수");
		getCell(sheet4, rowNo+1, colNo+7).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+8).setCellValue("보호시설호수유무");
		getCell(sheet4, rowNo+1, colNo+8).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+9).setCellValue("보호시설수");
		getCell(sheet4, rowNo+1, colNo+9).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+10).setCellValue("상단보호시설");
		getCell(sheet4, rowNo+1, colNo+10).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+11).setCellValue("하단보호시설");
		getCell(sheet4, rowNo+1, colNo+11).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+12).setCellValue("상단보호개수");
		getCell(sheet4, rowNo+1, colNo+12).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+13).setCellValue("하단보호개수");
		getCell(sheet4, rowNo+1, colNo+13).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+14).setCellValue("황폐지발생원코드");
		getCell(sheet4, rowNo+1, colNo+14).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+15).setCellValue("취약지역계류평균경사도");
		getCell(sheet4, rowNo+1, colNo+15).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+16).setCellValue("취약지역계류상단경사도");
		getCell(sheet4, rowNo+1, colNo+16).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+17).setCellValue("취약지역계류하단경사도");
		getCell(sheet4, rowNo+1, colNo+17).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+18).setCellValue("취약지역계류명");
		getCell(sheet4, rowNo+1, colNo+18).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+19).setCellValue("취약지역변곡점거리");
		getCell(sheet4, rowNo+1, colNo+19).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+20).setCellValue("취약지역변곡점고도높이");
		getCell(sheet4, rowNo+1, colNo+20).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+21).setCellValue("취약지역기타변곡점수");
		getCell(sheet4, rowNo+1, colNo+21).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+22).setCellValue("취약지역계류평균폭");
		getCell(sheet4, rowNo+1, colNo+22).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+23).setCellValue("취약지역변곡점고도높이");
		getCell(sheet4, rowNo+1, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+24).setCellValue("계류상단위치거리 거리");
		getCell(sheet4, rowNo+1, colNo+24).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+25).setCellValue("계류상단고도높이 고도");
		getCell(sheet4, rowNo+1, colNo+25).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+26).setCellValue("계류상단토심높이 토심");
		getCell(sheet4, rowNo+1, colNo+26).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+27).setCellValue("계류전석분포상황코드");
		getCell(sheet4, rowNo+1, colNo+27).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+28).setCellValue("계류상단전석분포비율 전석");
		getCell(sheet4, rowNo+1, colNo+28).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+29).setCellValue("계류상단위치위도값 위도");
		getCell(sheet4, rowNo+1, colNo+29).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+30).setCellValue("계류상단위치경도값 경도");
		getCell(sheet4, rowNo+1, colNo+30).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+31).setCellValue("계류중간위치거리 거리");
		getCell(sheet4, rowNo+1, colNo+31).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+32).setCellValue("계류중간고도높이 고도");
		getCell(sheet4, rowNo+1, colNo+32).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+33).setCellValue("계류중간토심높이 토심");
		getCell(sheet4, rowNo+1, colNo+33).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+34).setCellValue("계류중간전석분포코드");
		getCell(sheet4, rowNo+1, colNo+34).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+35).setCellValue("계류중간전석분포비율 전석");
		getCell(sheet4, rowNo+1, colNo+35).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+36).setCellValue("계류중간위치위도값 위도");
		getCell(sheet4, rowNo+1, colNo+36).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+37).setCellValue("계류중간위치경도값 경도");
		getCell(sheet4, rowNo+1, colNo+37).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+38).setCellValue("계류하단위치거리 거리");
		getCell(sheet4, rowNo+1, colNo+38).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+39).setCellValue("계류하단고도높이 고도");
		getCell(sheet4, rowNo+1, colNo+39).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+40).setCellValue("계류하단토심높이 토심");;   
		getCell(sheet4, rowNo+1, colNo+40).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+41).setCellValue("계류하단전석분포코드");
		getCell(sheet4, rowNo+1, colNo+41).setCellStyle(HeaderStyleGray);   
		getCell(sheet4, rowNo+1, colNo+42).setCellValue("계류하단전석분포비율 전석");
		getCell(sheet4, rowNo+1, colNo+42).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+43).setCellValue("계류하단위치위도값 위도");
		getCell(sheet4, rowNo+1, colNo+43).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+44).setCellValue("계류하단위치경도값 경도");
		getCell(sheet4, rowNo+1, colNo+44).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+45).setCellValue("경사길이");
		getCell(sheet4, rowNo+1, colNo+45).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+46).setCellValue("경사도");
		getCell(sheet4, rowNo+1, colNo+46).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+47).setCellValue("경사위치");
		getCell(sheet4, rowNo+1, colNo+47).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+48).setCellValue("위험경사지유형수");
		getCell(sheet4, rowNo+1, colNo+48).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+49).setCellValue("위험사면모암수");
		getCell(sheet4, rowNo+1, colNo+49).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+50).setCellValue("토지균열");
		getCell(sheet4, rowNo+1, colNo+50).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+51).setCellValue("토지함몰");
		getCell(sheet4, rowNo+1, colNo+51).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+52).setCellValue("토지융기");
		getCell(sheet4, rowNo+1, colNo+52).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+53).setCellValue("토지압출");
		getCell(sheet4, rowNo+1, colNo+53).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+54).setCellValue("토지확대");
		getCell(sheet4, rowNo+1, colNo+54).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+55).setCellValue("상시용수");
		getCell(sheet4, rowNo+1, colNo+55).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+56).setCellValue("강우용수");
		getCell(sheet4, rowNo+1, colNo+56).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+57).setCellValue("습윤");
		getCell(sheet4, rowNo+1, colNo+57).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+58).setCellValue("건조");
		getCell(sheet4, rowNo+1, colNo+58).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+59).setCellValue("임상코드");
		getCell(sheet4, rowNo+1, colNo+59).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+60).setCellValue("임상밀도코드");
		getCell(sheet4, rowNo+1, colNo+60).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+61).setCellValue("임상경급코드");
		getCell(sheet4, rowNo+1, colNo+61).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+62).setCellValue("계류보전유무");
		getCell(sheet4, rowNo+1, colNo+62).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+63).setCellValue("계류보전폭");
		getCell(sheet4, rowNo+1, colNo+63).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+64).setCellValue("계류보전높이");
		getCell(sheet4, rowNo+1, colNo+64).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+65).setCellValue("계류보전상단위도값");
		getCell(sheet4, rowNo+1, colNo+65).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+66).setCellValue("계류보전상단경도값");
		getCell(sheet4, rowNo+1, colNo+66).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+67).setCellValue("계류보전하단위도값");
		getCell(sheet4, rowNo+1, colNo+67).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+68).setCellValue("계류보전하단경도값");
		getCell(sheet4, rowNo+1, colNo+68).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+69).setCellValue("계류보전공정보막이유무");
		getCell(sheet4, rowNo+1, colNo+69).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+70).setCellValue("계류보전공정골막이유무");
		getCell(sheet4, rowNo+1, colNo+70).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+71).setCellValue("계류보전공정바닥막이유무");
		getCell(sheet4, rowNo+1, colNo+71).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+72).setCellValue("계류보전공정돌수로유무");
		getCell(sheet4, rowNo+1, colNo+72).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+73).setCellValue("계류보전공정기슭막이유무");
		getCell(sheet4, rowNo+1, colNo+73).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+74).setCellValue("계류보전공정기타유무");
		getCell(sheet4, rowNo+1, colNo+74).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+75).setCellValue("계류보전공정기타내용");
		getCell(sheet4, rowNo+1, colNo+75).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+76).setCellValue("예방사업계류보전유무");
		getCell(sheet4, rowNo+1, colNo+76).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+77).setCellValue("예방사업사방댐유무");
		getCell(sheet4, rowNo+1, colNo+77).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+78).setCellValue("예방사업산지사방유무");
		getCell(sheet4, rowNo+1, colNo+78).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+79).setCellValue("예방사업가능여부");
		getCell(sheet4, rowNo+1, colNo+79).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+80).setCellValue("예방사업선정사유내용");
		getCell(sheet4, rowNo+1, colNo+80).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+81).setCellValue("취약지역선정사유내용");
		getCell(sheet4, rowNo+1, colNo+81).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+82).setCellValue("취약지역구역설정사유내용");
		getCell(sheet4, rowNo+1, colNo+82).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+83).setCellValue("취약지역종합의견내용");
		getCell(sheet4, rowNo+1, colNo+83).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+84).setCellValue("취약지역특이사항내용");
		getCell(sheet4, rowNo+1, colNo+84).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+85).setCellValue("분석함수율");
		getCell(sheet4, rowNo+1, colNo+85).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+86).setCellValue("분석밀도율");
		getCell(sheet4, rowNo+1, colNo+86).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+87).setCellValue("분석습윤량");
		getCell(sheet4, rowNo+1, colNo+87).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+88).setCellValue("분석포화량");
		getCell(sheet4, rowNo+1, colNo+88).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+89).setCellValue("분석건조량");
		getCell(sheet4, rowNo+1, colNo+89).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+90).setCellValue("분석액성한계비율");
		getCell(sheet4, rowNo+1, colNo+90).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+91).setCellValue("분석소성한계비율");
		getCell(sheet4, rowNo+1, colNo+91).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+92).setCellValue("분석자갈비율");
		getCell(sheet4, rowNo+1, colNo+92).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+93).setCellValue("분석모래비율");
		getCell(sheet4, rowNo+1, colNo+93).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+94).setCellValue("분석점토비율");
		getCell(sheet4, rowNo+1, colNo+94).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+95).setCellValue("분석투수성계수");
		getCell(sheet4, rowNo+1, colNo+95).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+96).setCellValue("분석전단저항각도");
		getCell(sheet4, rowNo+1, colNo+96).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+97).setCellValue("계류상황코드");
		getCell(sheet4, rowNo+1, colNo+97).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+98).setCellValue("야장삭제여부");
		getCell(sheet4, rowNo+1, colNo+98).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+99).setCellValue("위험경사지위치위도값");
		getCell(sheet4, rowNo+1, colNo+99).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+100).setCellValue("위험경사지위치경도값");
		getCell(sheet4, rowNo+1, colNo+100).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+101).setCellValue("하단시점특이사항내용");
		getCell(sheet4, rowNo+1, colNo+101).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+102).setCellValue("취약지역조사ID");
		getCell(sheet4, rowNo+1, colNo+102).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+103).setCellValue("취약지역조사상세주소");
		getCell(sheet4, rowNo+1, colNo+103).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+104).setCellValue("취약지역위치위도값");
		getCell(sheet4, rowNo+1, colNo+104).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+105).setCellValue("취약지역위치경도값");
		getCell(sheet4, rowNo+1, colNo+105).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+106).setCellValue("취약지역조사지면적");
		getCell(sheet4, rowNo+1, colNo+106).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+107).setCellValue("취약지역조사지길이");
		getCell(sheet4, rowNo+1, colNo+107).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+108).setCellValue("취약지역날씨명");
		getCell(sheet4, rowNo+1, colNo+108).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+109).setCellValue("계류상단분포암반유무");
		getCell(sheet4, rowNo+1, colNo+109).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+110).setCellValue("계류상단분포전석유무");
		getCell(sheet4, rowNo+1, colNo+110).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+111).setCellValue("계류상단분포자갈유무");
		getCell(sheet4, rowNo+1, colNo+111).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+112).setCellValue("계류상단분포모래유무");
		getCell(sheet4, rowNo+1, colNo+112).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+113).setCellValue("계류상단분포기타유무");
		getCell(sheet4, rowNo+1, colNo+113).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+114).setCellValue("계류상단분포기타내용");
		getCell(sheet4, rowNo+1, colNo+114).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+115).setCellValue("계류중간분포암반유무");
		getCell(sheet4, rowNo+1, colNo+115).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+116).setCellValue("계류중간분포전석유무");
		getCell(sheet4, rowNo+1, colNo+116).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+117).setCellValue("계류중간분포자갈유무");
		getCell(sheet4, rowNo+1, colNo+117).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+118).setCellValue("계류중간분포모래유무");
		getCell(sheet4, rowNo+1, colNo+118).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+119).setCellValue("계류중간분포기타유무");
		getCell(sheet4, rowNo+1, colNo+119).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+120).setCellValue("계류중간부분포기타내용");
		getCell(sheet4, rowNo+1, colNo+120).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+121).setCellValue("계류하단분포암반유무");
		getCell(sheet4, rowNo+1, colNo+121).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+122).setCellValue("계류하단분포전석유무");
		getCell(sheet4, rowNo+1, colNo+122).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+123).setCellValue("계류하단분포자갈유무");
		getCell(sheet4, rowNo+1, colNo+123).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+124).setCellValue("계류하단분포모래유무");
		getCell(sheet4, rowNo+1, colNo+124).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+125).setCellValue("계류하단분포기타유무");
		getCell(sheet4, rowNo+1, colNo+125).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+126).setCellValue("계류하단분포기타내용");
		getCell(sheet4, rowNo+1, colNo+126).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+127).setCellValue("취약지역조사자직급명");
		getCell(sheet4, rowNo+1, colNo+127).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+128).setCellValue("취약지역조사지면적1");
		getCell(sheet4, rowNo+1, colNo+128).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+129).setCellValue("취약지역조사지면적2");
		getCell(sheet4, rowNo+1, colNo+129).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+130).setCellValue("취약지역조사지유역면적1");
		getCell(sheet4, rowNo+1, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+131).setCellValue("취약지역조사지유역면적2");
		getCell(sheet4, rowNo+1, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+132).setCellValue("취약지역조사지계류면적");
		getCell(sheet4, rowNo+1, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+133).setCellValue("취약지역조사지사방댐면적");
		getCell(sheet4, rowNo+1, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+134).setCellValue("취약지역월류상태여부");
		getCell(sheet4, rowNo+1, colNo+134).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+135).setCellValue("계류상황코드");
		getCell(sheet4, rowNo+1, colNo+135).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+136).setCellValue("취약지역곡류상태여부");
		getCell(sheet4, rowNo+1, colNo+136).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+137).setCellValue("취약지역계류유무");
		getCell(sheet4, rowNo+1, colNo+137).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+138).setCellValue("취약지역유목유무");
		getCell(sheet4, rowNo+1, colNo+138).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+139).setCellValue("취약지역유목길이");
		getCell(sheet4, rowNo+1, colNo+139).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+140).setCellValue("취약지역계상퇴적지역유무");
		getCell(sheet4, rowNo+1, colNo+140).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+141).setCellValue("계안붕괴지좌안수");
		getCell(sheet4, rowNo+1, colNo+141).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+142).setCellValue("계안붕괴지우안수");
		getCell(sheet4, rowNo+1, colNo+142).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+143).setCellValue("계류전석평균분포비율");
		getCell(sheet4, rowNo+1, colNo+143).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+144).setCellValue("계류하단폭");
		getCell(sheet4, rowNo+1, colNo+144).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+145).setCellValue("계류중간폭");
		getCell(sheet4, rowNo+1, colNo+145).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+146).setCellValue("계류상단폭");
		getCell(sheet4, rowNo+1, colNo+146).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+147).setCellValue("경사상단높이");
		getCell(sheet4, rowNo+1, colNo+147).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+148).setCellValue("경사하단높이");
		getCell(sheet4, rowNo+1, colNo+148).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+149).setCellValue("경사상승유무");
		getCell(sheet4, rowNo+1, colNo+149).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+150).setCellValue("경사평형유무");
		getCell(sheet4, rowNo+1, colNo+150).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+151).setCellValue("경사하강유무");
		getCell(sheet4, rowNo+1, colNo+151).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+152).setCellValue("경사조합유무");
		getCell(sheet4, rowNo+1, colNo+152).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+153).setCellValue("모암코드");
		getCell(sheet4, rowNo+1, colNo+153).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+154).setCellValue("토심높이");
		getCell(sheet4, rowNo+1, colNo+154).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+155).setCellValue("토지현황표본위도값");
		getCell(sheet4, rowNo+1, colNo+155).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+156).setCellValue("토지현황표본경도값");
		getCell(sheet4, rowNo+1, colNo+156).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+157).setCellValue("취약지역유역현황내용");
		getCell(sheet4, rowNo+1, colNo+157).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+158).setCellValue("산사태발생위험지역여부");
		getCell(sheet4, rowNo+1, colNo+158).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+159).setCellValue("산사태발생위험가능성여부");
		getCell(sheet4, rowNo+1, colNo+159).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+160).setCellValue("산사태발생보호시설여부");
		getCell(sheet4, rowNo+1, colNo+160).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+161).setCellValue("산사태발생불량림여부");
		getCell(sheet4, rowNo+1, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+162).setCellValue("산사태발생재난위험여부");
		getCell(sheet4, rowNo+1, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+163).setCellValue("취약지역조사평가점수");
		getCell(sheet4, rowNo+1, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+164).setCellValue("취약지역계류총길이");
		getCell(sheet4, rowNo+1, colNo+164).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+165).setCellValue("취약지역조사기타주소");
		getCell(sheet4, rowNo+1, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+166).setCellValue("용역사업번호");
		getCell(sheet4, rowNo+1, colNo+166).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+167).setCellValue("사업년도");
		getCell(sheet4, rowNo+1, colNo+167).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+1, colNo+168).setCellValue("산사태발생위험등급코드");
		getCell(sheet4, rowNo+1, colNo+168).setCellStyle(HeaderStylePink2);

		getCell(sheet4, rowNo+2, colNo).setCellValue("");
		getCell(sheet4, rowNo+2, colNo).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+1).setCellValue("VNARA_EXMNE_PSTN_NM");
		getCell(sheet4, rowNo+2, colNo+1).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+2).setCellValue("VNARA_EXMNE_NM");
		getCell(sheet4, rowNo+2, colNo+2).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+3).setCellValue("VNARA_EXMNE_TLNO");
		getCell(sheet4, rowNo+2, colNo+3).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+4).setCellValue("VNARA_EXMNN_DT");
		getCell(sheet4, rowNo+2, colNo+4).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+5).setCellValue("VNARA_SRZN_DRAR_AREA");
		getCell(sheet4, rowNo+2, colNo+5).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+6).setCellValue("VNARA_FTSPT_FG");
		getCell(sheet4, rowNo+2, colNo+6).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+7).setCellValue("VNARA_FTSPT_CNT");
		getCell(sheet4, rowNo+2, colNo+7).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+8).setCellValue("VNARA_FTSPT_HO_CNT_FG");
		getCell(sheet4, rowNo+2, colNo+8).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+9).setCellValue("VNARA_FTSPT_HO_CNT");
		getCell(sheet4, rowNo+2, colNo+9).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+10).setCellValue("VNARA_UPEND_FTSPT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+10).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+11).setCellValue("VNARA_LWPRT_FTSPT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+11).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+12).setCellValue("VNARA_UPEND_HO_CNT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+12).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+13).setCellValue("VNARA_LWPRT_HO_CNT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+13).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+14).setCellValue("WSLND_SOURCE_CD");
		getCell(sheet4, rowNo+2, colNo+14).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+15).setCellValue("VNARA_MRNG_AVRG_GRDNT");
		getCell(sheet4, rowNo+2, colNo+15).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+16).setCellValue("VNARA_MRNG_UPEND_GRDNT");
		getCell(sheet4, rowNo+2, colNo+16).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+17).setCellValue("VNARA_MRNG_LWPRT_GRDNT");
		getCell(sheet4, rowNo+2, colNo+17).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+18).setCellValue("VNARA_MRNG_NM");
		getCell(sheet4, rowNo+2, colNo+18).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+19).setCellValue("VNARA_IFLPT_DSTNC");
		getCell(sheet4, rowNo+2, colNo+19).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+20).setCellValue("VNARA_IFLPT_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+20).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+21).setCellValue("VNARA_ETC_IFLPT_CNT");
		getCell(sheet4, rowNo+2, colNo+21).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+22).setCellValue("VNARA_MRNG_AVRG_WDT");
		getCell(sheet4, rowNo+2, colNo+22).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+23).setCellValue("UPEND_TRMNA_PCMTT_CONT");
		getCell(sheet4, rowNo+2, colNo+23).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+24).setCellValue("MRNG_UPEND_LCTN_DSTNC");
		getCell(sheet4, rowNo+2, colNo+24).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+25).setCellValue("MRNG_UPEND_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+25).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+26).setCellValue("MRNG_UPEND_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+26).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+27).setCellValue("MRNG_BDSTN_DISTR_STTN_CD");
		getCell(sheet4, rowNo+2, colNo+27).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+28).setCellValue("MRNG_UPEND_BDSTN_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+28).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+29).setCellValue("MRNG_UPEND_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+29).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+30).setCellValue("MRNG_UPEND_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+30).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+31).setCellValue("MRNG_MDDL_LCTN_DSTNC");
		getCell(sheet4, rowNo+2, colNo+31).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+32).setCellValue("MRNG_MDDL_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+32).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+33).setCellValue("MRNG_MDDL_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+33).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+34).setCellValue("MRNG_MDDL_BDSTN_DISTR_CD");
		getCell(sheet4, rowNo+2, colNo+34).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+35).setCellValue("MRNG_MDDL_BDSTN_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+35).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+36).setCellValue("MRNG_MDDL_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+36).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+37).setCellValue("MRNG_MDDL_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+37).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+38).setCellValue("MRNG_LWPRT_LCTN_DSTNC");
		getCell(sheet4, rowNo+2, colNo+38).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+39).setCellValue("MRNG_LWPRT_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+39).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+40).setCellValue("MRNG_LWPRT_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+40).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+41).setCellValue("MRNG_LWPRT_BDSTN_DISTR_CD");
		getCell(sheet4, rowNo+2, colNo+41).setCellStyle(HeaderStyle);   
		getCell(sheet4, rowNo+2, colNo+42).setCellValue("MRNG_LWPRT_BDSTN_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+42).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+43).setCellValue("MRNG_LWPRT_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+43).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+44).setCellValue("MRNG_LWPRT_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+44).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+45).setCellValue("RISK_CLLN_LNGTH");
		getCell(sheet4, rowNo+2, colNo+45).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+46).setCellValue("RISK_CLLN_GRDNT");
		getCell(sheet4, rowNo+2, colNo+46).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+47).setCellValue("RISK_CLLN_LCTN_CNT");
		getCell(sheet4, rowNo+2, colNo+47).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+48).setCellValue("RISK_CLLN_TPE_CNT");
		getCell(sheet4, rowNo+2, colNo+48).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+49).setCellValue("RISK_CLLN_PRRCK_CNT");
		getCell(sheet4, rowNo+2, colNo+49).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+50).setCellValue("LAND_STATS_CRCK_FG");
		getCell(sheet4, rowNo+2, colNo+50).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+51).setCellValue("LAND_STATS_DENT_FG");
		getCell(sheet4, rowNo+2, colNo+51).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+52).setCellValue("LAND_STATS_UPLFT_FG");
		getCell(sheet4, rowNo+2, colNo+52).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+53).setCellValue("LAND_STATS_EPTES_FG");
		getCell(sheet4, rowNo+2, colNo+53).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+54).setCellValue("LAND_STATS_ESTNS_EXPEC_FG");
		getCell(sheet4, rowNo+2, colNo+54).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+55).setCellValue("USWTR_STATS_ORDTM_EXIST_FG");
		getCell(sheet4, rowNo+2, colNo+55).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+56).setCellValue("USWTR_STATS_RAIN_USWTR_FG");
		getCell(sheet4, rowNo+2, colNo+56).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+57).setCellValue("USWTR_STATS_CLLN_HMDT_FG");
		getCell(sheet4, rowNo+2, colNo+57).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+58).setCellValue("USWTR_STATS_CLLN_CNSTR_FG");
		getCell(sheet4, rowNo+2, colNo+58).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+59).setCellValue("FRTP_STATS_FRTP_CD");
		getCell(sheet4, rowNo+2, colNo+59).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+60).setCellValue("FRTP_STATS_SGRST_DNST_CD");
		getCell(sheet4, rowNo+2, colNo+60).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+61).setCellValue("FRTP_STATS_SGRST_DMCLS_CD");
		getCell(sheet4, rowNo+2, colNo+61).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+62).setCellValue("MRNG_PRSRV_FG");
		getCell(sheet4, rowNo+2, colNo+62).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+63).setCellValue("MRNG_PRSRV_WDT");
		getCell(sheet4, rowNo+2, colNo+63).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+64).setCellValue("MRNG_PRSRV_HGHT");
		getCell(sheet4, rowNo+2, colNo+64).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+65).setCellValue("MRNG_PRSRV_UPEND_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+65).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+66).setCellValue("MRNG_PRSRV_UPEND_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+66).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+67).setCellValue("MRNG_PRSRV_LWPRT_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+67).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+68).setCellValue("MRNG_PRSRV_LWPRT_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+68).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+69).setCellValue("MRNG_PRSRV_PROCS_BADP_FG");
		getCell(sheet4, rowNo+2, colNo+69).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+70).setCellValue("MRNG_PRSRV_PROCS_PBRR_FG");
		getCell(sheet4, rowNo+2, colNo+70).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+71).setCellValue("MRNG_PRSRV_PROCS_BSLWK_FG");
		getCell(sheet4, rowNo+2, colNo+71).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+72).setCellValue("MRNG_PRSRV_PROCS_STNCH_FG");
		getCell(sheet4, rowNo+2, colNo+72).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+73).setCellValue("MRNG_PRSRV_PROCS_RVMT_FG");
		getCell(sheet4, rowNo+2, colNo+73).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+74).setCellValue("MRNG_PRSRV_PROCS_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+74).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+75).setCellValue("MRNG_PRSRV_PROCS_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+75).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+76).setCellValue("PRVNN_BSNSS_MRNG_PRSRV_FG");
		getCell(sheet4, rowNo+2, colNo+76).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+77).setCellValue("PRVNN_BSNSS_ECNDM_FG");
		getCell(sheet4, rowNo+2, colNo+77).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+78).setCellValue("PRVNN_BSNSS_MNDST_ERCNT_FG");
		getCell(sheet4, rowNo+2, colNo+78).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+79).setCellValue("PRVNN_BSNSS_PSSBL_YN");
		getCell(sheet4, rowNo+2, colNo+79).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+80).setCellValue("PRVNN_BSNSS_SLCTN_RSN_CONT");
		getCell(sheet4, rowNo+2, colNo+80).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+81).setCellValue("VNARA_SLCTN_RSN_CONT");
		getCell(sheet4, rowNo+2, colNo+81).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+82).setCellValue("VNARA_ZONE_ESTBS_RSN_CONT");
		getCell(sheet4, rowNo+2, colNo+82).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+83).setCellValue("VNARA_GNRLZ_OPNN_CONT");
		getCell(sheet4, rowNo+2, colNo+83).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+84).setCellValue("VNARA_PCMTT_CONT");
		getCell(sheet4, rowNo+2, colNo+84).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+85).setCellValue("ANLSS_MSCNT");
		getCell(sheet4, rowNo+2, colNo+85).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+86).setCellValue("ANLSS_DNST_RT");
		getCell(sheet4, rowNo+2, colNo+86).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+87).setCellValue("ANLSS_DMPNS_QNTT");
		getCell(sheet4, rowNo+2, colNo+87).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+88).setCellValue("ANLSS_STRTN_QNTT");
		getCell(sheet4, rowNo+2, colNo+88).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+89).setCellValue("ANLSS_CNSTR_QNTT");
		getCell(sheet4, rowNo+2, colNo+89).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+90).setCellValue("ANLSS_LQDLM_RATE");
		getCell(sheet4, rowNo+2, colNo+90).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+91).setCellValue("ANLSS_PLTLM_RATE");
		getCell(sheet4, rowNo+2, colNo+91).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+92).setCellValue("ANLSS_GRVL_RATE");
		getCell(sheet4, rowNo+2, colNo+92).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+93).setCellValue("ANLSS_SAND_RATE");
		getCell(sheet4, rowNo+2, colNo+93).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+94).setCellValue("ANLSS_CLAY_RATE");
		getCell(sheet4, rowNo+2, colNo+94).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+95).setCellValue("ANLSS_PV_CFFCN");
		getCell(sheet4, rowNo+2, colNo+95).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+96).setCellValue("ANLSS_SRSTC_AGL");
		getCell(sheet4, rowNo+2, colNo+96).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+97).setCellValue("ANLSS_CHSN_QNTT");
		getCell(sheet4, rowNo+2, colNo+97).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+98).setCellValue("VNARA_FLNT_DLT_YN");
		getCell(sheet4, rowNo+2, colNo+98).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+99).setCellValue("RISK_CLLN_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+99).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+100).setCellValue("RISK_CLLN_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+100).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+101).setCellValue("LWPRT_PNTM_PCMTT_CONT");
		getCell(sheet4, rowNo+2, colNo+101).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+102).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet4, rowNo+2, colNo+102).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+103).setCellValue("VNARA_EXMNN_DTADD");
		getCell(sheet4, rowNo+2, colNo+103).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+104).setCellValue("VNARA_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+104).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+105).setCellValue("VNARA_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+105).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+106).setCellValue("VNARA_SRZN_AREA");
		getCell(sheet4, rowNo+2, colNo+106).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+107).setCellValue("VNARA_SRZN_LNGTH");
		getCell(sheet4, rowNo+2, colNo+107).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+108).setCellValue("VNARA_WTHR_NM");
		getCell(sheet4, rowNo+2, colNo+108).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+109).setCellValue("MRNG_UPEND_DISTR_BSROK_FG");
		getCell(sheet4, rowNo+2, colNo+109).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+110).setCellValue("MRNG_UPEND_DISTR_BDSTN_FG");
		getCell(sheet4, rowNo+2, colNo+110).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+111).setCellValue("MRNG_UPEND_DISTR_GRVL_FG");
		getCell(sheet4, rowNo+2, colNo+111).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+112).setCellValue("MRNG_UPEND_DISTR_SAND_FG");
		getCell(sheet4, rowNo+2, colNo+112).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+113).setCellValue("MRNG_UPEND_DISTR_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+113).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+114).setCellValue("MRNG_UPEND_DISTR_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+114).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+115).setCellValue("MRNG_MDDL_DISTR_BSROK_FG");
		getCell(sheet4, rowNo+2, colNo+115).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+116).setCellValue("MRNG_MDDL_DISTR_BDSTN_FG");
		getCell(sheet4, rowNo+2, colNo+116).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+117).setCellValue("MRNG_MDDL_DISTR_GRVL_FG");
		getCell(sheet4, rowNo+2, colNo+117).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+118).setCellValue("MRNG_MDDL_DISTR_SAND_FG");
		getCell(sheet4, rowNo+2, colNo+118).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+119).setCellValue("MRNG_MDDL_DISTR_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+119).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+120).setCellValue("MRNG_MDDL_DISTR_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+120).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+121).setCellValue("MRNG_LWPRT_DISTR_BSROK_FG");
		getCell(sheet4, rowNo+2, colNo+121).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+122).setCellValue("MRNG_LWPRT_DISTR_BDSTN_FG");
		getCell(sheet4, rowNo+2, colNo+122).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+123).setCellValue("MRNG_LWPRT_DISTR_GRVL_FG");
		getCell(sheet4, rowNo+2, colNo+123).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+124).setCellValue("MRNG_LWPRT_DISTR_SAND_FG");
		getCell(sheet4, rowNo+2, colNo+124).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+125).setCellValue("MRNG_LWPRT_DISTR_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+125).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+126).setCellValue("MRNG_LWPRT_DISTR_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+126).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+127).setCellValue("VNARA_EXMNE_CLPST_NM");
		getCell(sheet4, rowNo+2, colNo+127).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+128).setCellValue("VNARA_SRZN_AREA1");
		getCell(sheet4, rowNo+2, colNo+128).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+129).setCellValue("VNARA_SRZN_AREA2");
		getCell(sheet4, rowNo+2, colNo+129).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+130).setCellValue("VNARA_SRZN_DRAR_AREA1");
		getCell(sheet4, rowNo+2, colNo+130).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+131).setCellValue("VNARA_SRZN_DRAR_AREA2");
		getCell(sheet4, rowNo+2, colNo+131).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+132).setCellValue("VNARA_SRZN_MRNG_AREA");
		getCell(sheet4, rowNo+2, colNo+132).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+133).setCellValue("VNARA_SRZN_ECNDM_AREA");
		getCell(sheet4, rowNo+2, colNo+133).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+134).setCellValue("VNARA_OVFLW_STTS_YN");
		getCell(sheet4, rowNo+2, colNo+134).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+135).setCellValue("MRNG_STTN_CD");
		getCell(sheet4, rowNo+2, colNo+135).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+136).setCellValue("VNARA_BTFLW_STTS_YN");
		getCell(sheet4, rowNo+2, colNo+136).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+137).setCellValue("VNARA_MRNG_FG");
		getCell(sheet4, rowNo+2, colNo+137).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+138).setCellValue("VNARA_FLDWD_FG");
		getCell(sheet4, rowNo+2, colNo+138).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+139).setCellValue("VNARA_FLDWD_LNGTH");
		getCell(sheet4, rowNo+2, colNo+139).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+140).setCellValue("VNARA_STMBD_ACCMA_ARA_FG");
		getCell(sheet4, rowNo+2, colNo+140).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+141).setCellValue("SLIVW_LBNK_CNT");
		getCell(sheet4, rowNo+2, colNo+141).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+142).setCellValue("SLIVW_RBNK_CNT");
		getCell(sheet4, rowNo+2, colNo+142).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+143).setCellValue("MRNG_BDSTN_AVRG_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+143).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+144).setCellValue("MRNG_LWPRT_WDT");
		getCell(sheet4, rowNo+2, colNo+144).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+145).setCellValue("MRNG_MDDL_WDT");
		getCell(sheet4, rowNo+2, colNo+145).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+146).setCellValue("MRNG_UPEND_WDT");
		getCell(sheet4, rowNo+2, colNo+146).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+147).setCellValue("RISK_CLLN_UPEND_HGHT");
		getCell(sheet4, rowNo+2, colNo+147).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+148).setCellValue("RISK_CLLN_LWPRT_HGHT");
		getCell(sheet4, rowNo+2, colNo+148).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+149).setCellValue("RISK_CLLN_RISE_FG");
		getCell(sheet4, rowNo+2, colNo+149).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+150).setCellValue("RISK_CLLN_EQLBM_FG");
		getCell(sheet4, rowNo+2, colNo+150).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+151).setCellValue("RISK_CLLN_DSWNG_FG");
		getCell(sheet4, rowNo+2, colNo+151).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+152).setCellValue("RISK_CLLN_MXTR_FG");
		getCell(sheet4, rowNo+2, colNo+152).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+153).setCellValue("PRDN_STATS_PRRCK_CD");
		getCell(sheet4, rowNo+2, colNo+153).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+154).setCellValue("LAND_STATS_SMPL_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+154).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+155).setCellValue("LAND_STATS_SMPL_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+155).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+156).setCellValue("LAND_STATS_SMPL_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+156).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+157).setCellValue("VNARA_DRAR_STATS_CONT");
		getCell(sheet4, rowNo+2, colNo+157).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+158).setCellValue("LNDSL_OCCRR_RISK_ARA_YN");
		getCell(sheet4, rowNo+2, colNo+158).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+159).setCellValue("LNDSL_OCCRR_RISK_PSSBT_YN");
		getCell(sheet4, rowNo+2, colNo+159).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+160).setCellValue("LNDSL_OCCRR_FTSPT_YN");
		getCell(sheet4, rowNo+2, colNo+160).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+161).setCellValue("LNDSL_OCCRR_BDFRS_YN");
		getCell(sheet4, rowNo+2, colNo+161).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+162).setCellValue("LNDSL_OCCRR_MSFRT_RISK_YN");
		getCell(sheet4, rowNo+2, colNo+162).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+163).setCellValue("VNARA_EXMNN_EVSCR");
		getCell(sheet4, rowNo+2, colNo+163).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+164).setCellValue("VNARA_MRNG_TOT_LNGTH");
		getCell(sheet4, rowNo+2, colNo+164).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+165).setCellValue("VNARA_EXMNN_ETC_ADDR");
		getCell(sheet4, rowNo+2, colNo+165).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+166).setCellValue("SERVC_BSNSS_NO");
		getCell(sheet4, rowNo+2, colNo+166).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+167).setCellValue("BSNSS_YR");
		getCell(sheet4, rowNo+2, colNo+167).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+168).setCellValue("LNDSL_OCCRR_RISK_GRCD");
		getCell(sheet4, rowNo+2, colNo+168).setCellStyle(HeaderStyle);
		
		rowNo=3;
		String formulaText = "";

		if(dataList.size() > 0) {

		    //hblee(산사태 취약지역조사야장)
		    for (int i=0; i<dataList.size(); i++) {
		        colNo=0;

		        cell = getCell(sheet4, rowNo, colNo++);/*기번*/
		        formulaText = "입력값!A"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*소속명*/
		        formulaText = "출력!Q"+(rowNo-1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*조사자*/
		        formulaText = "입력값!R"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*전화번호*/
		        formulaText = "입력값!T"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*조사일자*/
		        formulaText = "취약지역관리!H"+(rowNo+3);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*유역면적*/
		        formulaText = "입력값!AH"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*보호시설유무*/
		        formulaText = "IF(입력값!AL"+(rowNo+2)+"=\"유\",\"Y\",IF(입력값!AL"+(rowNo+2)+"=\"무\",\"N\",\"\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);
		        
		        cell = getCell(sheet4, rowNo, colNo++);/*보호시설수*/
		        formulaText = "입력값!AM"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*보호시설호수유무*/
		        formulaText = "IF(입력값!AN"+(rowNo+2)+"=\"유\",\"Y\",IF(입력값!AN"+(rowNo+2)+"=\"무\",\"N\",\"\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*보호시설수*/
		        formulaText = "입력값!AO"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*상단보호시설*/
		        formulaText = "입력값!AP"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*하단보호시설*/
		        formulaText = "입력값!AR"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*상단보호개수*/        
		        formulaText = "입력값!AQ"+(rowNo+2)+"&\" 개소\"";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*하단보호개수*/        
		        formulaText = "입력값!AS"+(rowNo+2)+"&\" 개소\"";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*황폐지발생원코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류평균경사도*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류상단경사도*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류하단경사도*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역변곡점거리*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역변곡점고도높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역기타변곡점수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류평균폭*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역변곡점고도높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단위치거리*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단고도높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단토심높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류전석분포상황코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단전석분포비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단위치위도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단위치경도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간위치거리*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간고도높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간토심높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간전석분포코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간전석분포비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간위치위도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간위치경도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단위치거리*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단고도높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단토심높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단전석분포코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단전석분포비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단위치위도값*/
		        formulaText = "LEFT((입력값!J"+(rowNo+2)+"&\":\"&입력값!K"+(rowNo+2)+"&\":\"&입력값!L"+(rowNo+2)+"),10)";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단위치경도값*/
		        formulaText = "LEFT((입력값!M"+(rowNo+2)+"&\":\"&입력값!N"+(rowNo+2)+"&\":\"&입력값!O"+(rowNo+2)+"),11)";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사길이*/
		        formulaText = "출력!AU"+(rowNo-1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사도*/
		        formulaText = "출력!AV"+(rowNo-1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사위치*/
		        formulaText = "출력!BA"+(rowNo-1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*위험경사지유형수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*위험사면모암수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지균열*/
		        formulaText = "IF(입력값!BH"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BH"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지함몰*/
		        formulaText = "IF(입력값!BI"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BI"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지융기*/
		        formulaText = "IF(입력값!BJ"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BJ"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지압출*/
		        formulaText = "IF(입력값!BK"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BK"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지확대*/
		        formulaText = "IF(입력값!BL"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BL"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*상시용수*/
		        formulaText = "IF(입력값!BP"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BP"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*강우용수*/
		        formulaText = "IF(입력값!BQ"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BQ"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*습윤*/
		        formulaText = "IF(입력값!BR"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BR"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*건조*/
		        formulaText = "IF(입력값!BS"+(rowNo+2)+"=\"무\",\"N\",IF(입력값!BS"+(rowNo+2)+"=\"유\",\"Y\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*임상코드*/
		        formulaText = "IF(입력값!BM"+(rowNo+2)+"=\"침\",\"FT0002\",IF(입력값!BM"+(rowNo+2)+"=\"활\",\"FT0003\",IF(입력값!BM"+(rowNo+2)+"=\"혼\",\"FT0004\",IF(입력값!BM"+(rowNo+2)+"=\"무\",\"FT0001\"))))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*임상밀도코드*/
		        formulaText = "IF(입력값!BN"+(rowNo+2)+"=\"소(少)\",\"DS0001\",IF(입력값!BN"+(rowNo+2)+"=\"중(中)\",\"DS0002\",IF(입력값!BN"+(rowNo+2)+"=\"밀(密)\",\"DS0003\")))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*임상경급코드*/
		        formulaText = "IF(입력값!BO"+(rowNo+2)+"=\"치\",\"DM0001\",IF(입력값!BO"+(rowNo+2)+"=\"소\",\"DM0002\",IF(입력값!BO"+(rowNo+2)+"=\"중\",\"DM0003\",IF(입력값!BO"+(rowNo+2)+"=\"대\",\"DM0004\"))))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전폭*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전높이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전상단위도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전상단경도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전하단위도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전하단경도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정보막이유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정골막이유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정바닥막이유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정돌수로유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정기슭막이유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정기타유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정기타내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*예방사업계류보전유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*예방사업사방댐유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*예방사업산지사방유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*예방사업가능여부*/
		        formulaText = "IF(입력값!CA"+(rowNo+2)+"=\"가능\",\"Y\",IF(입력값!CA"+(rowNo+2)+"=\"불가능\",\"N\",IF(입력값!CA"+(rowNo+2)+"=\"\",\"\",IF(입력값!CA"+(rowNo+2)+"=\"-\",\"\"))))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*예방사업선정사유내용*/
		        formulaText = "입력값!FV"+(rowNo+2)+"&입력값!FW"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역선정사유내용*/
		        formulaText = "입력값!FP"+(rowNo+2)+"&입력값!FQ"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역구역설정사유내용*/
		        formulaText = "입력값!FT"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역종합의견내용*/
		        formulaText = "입력값!FR"+(rowNo+2)+"&입력값!FS"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역특이사항내용*/
		        formulaText = "입력값!FU"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석함수율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석밀도율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석습윤량*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석포화량*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석건조량*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석액성한계비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석소성한계비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석자갈비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석모래비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석점토비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석투수성계수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*분석전단저항각도*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상황코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*야장삭제여부*/
		        cell.setCellValue("N");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*위험경사지위치위도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*위험경사지위치경도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*하단시점특이사항내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사ID*/
		        formulaText = "취약지역관리!B"+(rowNo+3);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사상세주소*/
		        formulaText = "입력값!H"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역위치위도값*/
		        formulaText = "LEFT((입력값!J"+(rowNo+2)+"&\":\"&입력값!K"+(rowNo+2)+"&\":\"&입력값!L"+(rowNo+2)+"),10)";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역위치경도값*/
		        formulaText = "LEFT((입력값!M"+(rowNo+2)+"&\":\"&입력값!N"+(rowNo+2)+"&\":\"&입력값!O"+(rowNo+2)+"),11)";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지면적*/
		        formulaText = "입력값!AK"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지길이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역날씨명*/
		        cell.setCellValue("맑음");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포암반유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포전석유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포자갈유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포모래유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포기타유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포기타내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포암반유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포전석유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포자갈유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포모래유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포기타유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간부분포기타내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포암반유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포전석유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포자갈유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포모래유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포기타유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포기타내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사자직급명*/
		        formulaText = "입력값!Q"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지면적1*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지면적2*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지유역면적1*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지유역면적2*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지계류면적*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사지사방댐면적*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역월류상태여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상황코드*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역곡류상태여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역유목유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역유목길이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계상퇴적지역유무*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계안붕괴지좌안수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계안붕괴지우안수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류전석평균분포비율*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류하단폭*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류중간폭*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*계류상단폭*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사상단높이*/
		        formulaText = "입력값!BD"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사하단높이*/
		        formulaText = "입력값!BE"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사상승유무*/
		        formulaText = "IF(입력값!BF"+(rowNo+2)+"=\"상승\",\"Y\",IF(입력값!BF"+(rowNo+2)+"=\"\",\"\",\"N\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사평형유무*/
		        formulaText = "IF(입력값!BF"+(rowNo+2)+"=\"평형\",\"Y\",IF(입력값!BF"+(rowNo+2)+"=\"\",\"\",\"N\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사하강유무*/
		        formulaText = "IF(입력값!BF"+(rowNo+2)+"=\"하강\",\"Y\",IF(입력값!BF"+(rowNo+2)+"=\"\",\"\",\"N\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*경사조합유무*/
		        formulaText = "IF(입력값!BF"+(rowNo+2)+"=\"복합\",\"Y\",IF(입력값!BF"+(rowNo+2)+"=\"\",\"\",\"N\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*모암코드*/
		        formulaText = "IF(입력값!AU"+(rowNo+2)+"=\"퇴적암\",\"PR0001\",IF(입력값!AU"+(rowNo+2)+"=\"화강암\",\"PR0002\",IF(입력값!AU"+(rowNo+2)+"=\"천매암\",\"PR0003\",IF(입력값!AU"+(rowNo+2)+"=\"편암\",\"PR0004\",IF(입력값!AU"+(rowNo+2)+"=\"편마암\",\"PR0004\",IF(입력값!AU"+(rowNo+2)+"=\"화성암\",\"PR0005\",IF(입력값!AU"+(rowNo+2)+"=\"안산암\",\"PR0005\",IF(입력값!AU"+(rowNo+2)+"=\"석회암\",\"PR0001\",IF(입력값!AU"+(rowNo+2)+"=\"\",\"\")))))))))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토심높이*/
		        formulaText = "입력값!BG"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지현황표본위도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*토지현황표본경도값*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역유역현황내용*/
		        formulaText = "입력값!FO"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*산사태발생위험지역여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*산사태발생위험가능성여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*산사태발생보호시설여부*/
		        formulaText = "IF(입력값!AL"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*산사태발생불량림여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*산사태발생재난위험여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사평가점수*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역계류총길이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*취약지역조사기타주소*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*용역사업번호*/
		        formulaText = "LEFT(입력값!S"+(rowNo+2)+",4)";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*사업년도*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet4, rowNo, colNo++);/*산사태발생위험등급코드*/
		        formulaText = "출력!BU"+(rowNo-1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);
		        
		        rowNo++;
		    }
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산사태 취약지역관리대장 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet15(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		
		XSSFSheet sheet5 = wb.createSheet("취약지역관리대장");
		sheet5.setDefaultColumnWidth(17);
		sheet5.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet5, rowNo, colNo).setCellValue("이동시 삭제");
		getCell(sheet5, rowNo, colNo).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo, colNo+1).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+1).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+2).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+2).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+3).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+4).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+5).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+5).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+6).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+6).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+7).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+8).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+8).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+9).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+10).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+11).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+11).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+12).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+13).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+13).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+14).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+14).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+15).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+15).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+16).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+16).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+17).setCellValue("모두 N");
		getCell(sheet5, rowNo, colNo+17).setCellStyle(HeaderStyleGreen3);
		getCell(sheet5, rowNo, colNo+18).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+18).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+19).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+19).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+20).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+20).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+21).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+21).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+22).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+22).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+23).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+24).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+24).setCellStyle(HeaderStyleGray);

		getCell(sheet5, rowNo+1, colNo).setCellValue("");
		getCell(sheet5, rowNo+1, colNo).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+1, colNo+1).setCellValue("조사대장구분코드");
		getCell(sheet5, rowNo+1, colNo+1).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+2).setCellValue("관리주체명");
		getCell(sheet5, rowNo+1, colNo+2).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+3).setCellValue("취약지역지구명");
		getCell(sheet5, rowNo+1, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+4).setCellValue("취약지역소유명");
		getCell(sheet5, rowNo+1, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+5).setCellValue("취약지역대피장소명");
		getCell(sheet5, rowNo+1, colNo+5).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+6).setCellValue("취약지역지정사유내용");
		getCell(sheet5, rowNo+1, colNo+6).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+7).setCellValue("취약지역지정위원회명");
		getCell(sheet5, rowNo+1, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+8).setCellValue("취약지역지정위원회심의년월일");
		getCell(sheet5, rowNo+1, colNo+8).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+9).setCellValue("취약지역지정위원회이의신청일");
		getCell(sheet5, rowNo+1, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+10).setCellValue("취약지역지정위원회이의신청사유");
		getCell(sheet5, rowNo+1, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+11).setCellValue("취약지역지정면적");
		getCell(sheet5, rowNo+1, colNo+11).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+12).setCellValue("산사태취약지역지정일");
		getCell(sheet5, rowNo+1, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+13).setCellValue("산사태취약지역해제일");
		getCell(sheet5, rowNo+1, colNo+13).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+14).setCellValue("취약지역해제사유내용");
		getCell(sheet5, rowNo+1, colNo+14).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+15).setCellValue("취약지역지정해제여부");
		getCell(sheet5, rowNo+1, colNo+15).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+16).setCellValue("취약지역현황내용");
		getCell(sheet5, rowNo+1, colNo+16).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+17).setCellValue("대장삭제여부");
		getCell(sheet5, rowNo+1, colNo+17).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+1, colNo+18).setCellValue("조사ID");
		getCell(sheet5, rowNo+1, colNo+18).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+19).setCellValue("지번");
		getCell(sheet5, rowNo+1, colNo+19).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+20).setCellValue("위도");
		getCell(sheet5, rowNo+1, colNo+20).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+21).setCellValue("경도");
		getCell(sheet5, rowNo+1, colNo+21).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+22).setCellValue("취약지면적");
		getCell(sheet5, rowNo+1, colNo+22).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+23).setCellValue("취약지역조사지길이");
		getCell(sheet5, rowNo+1, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+24).setCellValue("취약지역조사기타주소");
		getCell(sheet5, rowNo+1, colNo+24).setCellStyle(HeaderStyleGray);
		
		getCell(sheet5, rowNo+2, colNo).setCellValue("");
		getCell(sheet5, rowNo+2, colNo).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+1).setCellValue("VNARA_EXMNN_RGSTR_TPCD");
		getCell(sheet5, rowNo+2, colNo+1).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+2).setCellValue("VNARA_MNGME_MNBD_NM");
		getCell(sheet5, rowNo+2, colNo+2).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+3).setCellValue("VNARA_DSTRT_NM");
		getCell(sheet5, rowNo+2, colNo+3).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+4).setCellValue("VNARA_PSSSS_NM");
		getCell(sheet5, rowNo+2, colNo+4).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+5).setCellValue("VNARA_EVCTN_PLACE_NM");
		getCell(sheet5, rowNo+2, colNo+5).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+6).setCellValue("VNARA_APPNT_RSN_CONT");
		getCell(sheet5, rowNo+2, colNo+6).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+7).setCellValue("VNARA_APPNT_CMMTT_NM");
		getCell(sheet5, rowNo+2, colNo+7).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+8).setCellValue("VNARA_APPNT_CMMTT_DLBRT_DT");
		getCell(sheet5, rowNo+2, colNo+8).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+9).setCellValue("VNARA_APPNT_CMMTT_OBJCN_RQDT");
		getCell(sheet5, rowNo+2, colNo+9).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+10).setCellValue("VNARA_APPNT_CMMTT_FROBJ_RSN");
		getCell(sheet5, rowNo+2, colNo+10).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+11).setCellValue("VNARA_APPNT_AREA");
		getCell(sheet5, rowNo+2, colNo+11).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+12).setCellValue("LNDSL_VNARA_APPDT");
		getCell(sheet5, rowNo+2, colNo+12).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+13).setCellValue("LNDSL_VNARA_RLDT");
		getCell(sheet5, rowNo+2, colNo+13).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+14).setCellValue("VNARA_RLS_RSN_CONT");
		getCell(sheet5, rowNo+2, colNo+14).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+15).setCellValue("VNARA_APPNT_RLS_YN");
		getCell(sheet5, rowNo+2, colNo+15).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+16).setCellValue("VNARA_STATS_CONT");
		getCell(sheet5, rowNo+2, colNo+16).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+17).setCellValue("VNARA_RGSTR_DLT_YN");
		getCell(sheet5, rowNo+2, colNo+17).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+18).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet5, rowNo+2, colNo+18).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+19).setCellValue("VNARA_EXMNN_DTADD");
		getCell(sheet5, rowNo+2, colNo+19).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+20).setCellValue("VNARA_LCTN_LTTD_VAL");
		getCell(sheet5, rowNo+2, colNo+20).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+21).setCellValue("VNARA_LCTN_LNGTD_VAL");
		getCell(sheet5, rowNo+2, colNo+21).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+22).setCellValue("VNARA_SRZN_AREA");
		getCell(sheet5, rowNo+2, colNo+22).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+23).setCellValue("VNARA_SRZN_LNGTH");
		getCell(sheet5, rowNo+2, colNo+23).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+24).setCellValue("VNARA_EXMNN_ETC_ADDR");
		getCell(sheet5, rowNo+2, colNo+24).setCellStyle(HeaderStyle);
		
		rowNo=3;
		String formulaText = "";

		if(dataList.size() > 0) {

		    //hblee(산사태 취약지역관리대장)
		    for (int i=0; i<dataList.size(); i++) {
		        colNo=0;

		        cell = getCell(sheet5, rowNo, colNo++);/*기번*/
		        formulaText = "입력값!A"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*조사대장구분코드*/
		        formulaText = "IF(입력값!B"+(rowNo+2)+"=\"산사태\",\"VNA002\",IF(입력값!B"+(rowNo+2)+"=\"토석류\",\"VNA001\",\"\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*관리주체명*/
		        formulaText = "입력값!D"+(rowNo+2)+"&\" \"&입력값!E"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지구명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역소유명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역대피장소명*/
		        formulaText = "입력값!CD"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정사유내용*/
		        formulaText = "취약지역조사야장!CD"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정위원회명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정위원회심의년월일*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정위원회이의신청일*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정위원회이의신청사유*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정면적*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*산사태취약지역지정일*/        
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*산사태취약지역해제일*/        
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역해제사유내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역지정해제여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역현황내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*대장삭제여부*/
		        cell.setCellValue("N");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*조사ID*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지번*/
		        formulaText = "입력값!H"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*위도*/
		        formulaText = "취약지역조사야장!DA"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*경도*/
		        formulaText = "취약지역조사야장!DB"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지면적*/
		        formulaText = "취약지역조사야장!DC"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역조사지길이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역조사기타주소*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        rowNo++;
		    }
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 산사태 취약지역첨부파일 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet16(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		List<EgovMap> photoTagList = (List<EgovMap>) dataMap.get("photoTagList"); // 현장사진 데이터가 담긴 리스트
		
		XSSFSheet sheet6 = wb.createSheet("취약지역첨부파일");
		sheet6.setDefaultColumnWidth(10);
		sheet6.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet6, rowNo, colNo).setCellValue("동영상은 1 \r\n" + "사진은 3~5");
		getCell(sheet6, rowNo, colNo).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+1).setCellValue("저장경로는 동일하게");
		getCell(sheet6, rowNo, colNo+1).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+2).setCellValue("조사ID별 동일하게");
		getCell(sheet6, rowNo, colNo+2).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+3).setCellValue("저장파일과 동일 자동입력");
		getCell(sheet6, rowNo, colNo+3).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+4).setCellValue("동영상 MP4, 사진 JPG");
		getCell(sheet6, rowNo, colNo+4).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+5).setCellValue("직접입력");
		getCell(sheet6, rowNo, colNo+5).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+6).setCellValue("");
		getCell(sheet6, rowNo, colNo+6).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+7).setCellValue("");
		getCell(sheet6, rowNo, colNo+7).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+8).setCellValue("직접입력");
		getCell(sheet6, rowNo, colNo+8).setCellStyle(HeaderStylePink);

		getCell(sheet6, rowNo+1, colNo).setCellValue("파일순번");
		getCell(sheet6, rowNo+1, colNo).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+1).setCellValue("취약지역첨부파일저장경로");
		getCell(sheet6, rowNo+1, colNo+1).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+2).setCellValue("저장파일명");
		getCell(sheet6, rowNo+1, colNo+2).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+3).setCellValue("원본파일명");
		getCell(sheet6, rowNo+1, colNo+3).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+4).setCellValue("확장자명");
		getCell(sheet6, rowNo+1, colNo+4).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+5).setCellValue("파일설명");
		getCell(sheet6, rowNo+1, colNo+5).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+6).setCellValue("파일크기");
		getCell(sheet6, rowNo+1, colNo+6).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+7).setCellValue("파일구분코드");
		getCell(sheet6, rowNo+1, colNo+7).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+8).setCellValue("취약지역조사ID");
		getCell(sheet6, rowNo+1, colNo+8).setCellStyle(HeaderStyleRed);

		getCell(sheet6, rowNo+2, colNo).setCellValue("VNARA_ATTCH_FILE_SEQ");
		getCell(sheet6, rowNo+2, colNo).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+1).setCellValue("VNARA_ATTCH_FILE_STORE_CRSE");
		getCell(sheet6, rowNo+2, colNo+1).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+2).setCellValue("VNARA_ATTCH_STORE_FILE_NM");
		getCell(sheet6, rowNo+2, colNo+2).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+3).setCellValue("VNARA_ATTCH_ORGIL_FILE_NM");
		getCell(sheet6, rowNo+2, colNo+3).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+4).setCellValue("VNARA_ATTCH_FILE_EXT_NM");
		getCell(sheet6, rowNo+2, colNo+4).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+5).setCellValue("VNARA_ATTCH_FILE_DSCRT");
		getCell(sheet6, rowNo+2, colNo+5).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+6).setCellValue("VNARA_ATTCH_FILE_MGNTD");
		getCell(sheet6, rowNo+2, colNo+6).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+7).setCellValue("LNDSL_UPLD_FILE_TPCD");
		getCell(sheet6, rowNo+2, colNo+7).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+8).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet6, rowNo+2, colNo+8).setCellStyle(HeaderStyle);
		
		rowNo=3;

		if(photoTagList.size() > 0) {
			
		    //hblee(산사태 취약지역첨부파일)
		    for (int i=0; i<photoTagList.size(); i++) {
				String photoTag = photoTagList.get(i).get("tag").toString();
				String[] photoImg = photoTagList.get(i).get("img").toString().split("/");
				String imgext = "." + FilenameUtils.getExtension(photoImg[2]);
				int fileNum = 3;
				
				if(photoTag.contains(".")) photoTag.substring(photoTag.indexOf(".")+1);
				
				if(photoTag.equals("유출구 상부전경2")) {
					fileNum = 4;
				}else if(photoTag.equals("내부사진1")) {
					fileNum = 5;
				}else if(photoTag.equals("내부사진2")) {
					fileNum = 6;
				}
				
				colNo = 0;
				
				cell = getCell(sheet6, rowNo, colNo++);/*파일순번*/
        		cell.setCellValue(fileNum);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*취약지역첨부파일저장경로*/
        		cell.setCellValue("/webdata/upload/common/MHMS/upload/weakbook/images/");
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*저장파일명*/
        		cell.setCellValue(photoImg[2]);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*원본파일명*/
        		cell.setCellValue(photoImg[2]);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*확장자명*/
        		cell.setCellValue(imgext);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*파일설명*/
        		cell.setCellValue(photoTag);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*파일크기*/
        		cell.setCellValue("");
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*파일구분코드*/
        		cell.setCellValue("IMAGE");
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*취약지역조사ID*/
        		cell.setCellValue("");
        		cell.setCellStyle(ValueStyle);
        		
        		rowNo++;
		    }
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 토석류 입력값 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet21(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름

		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		XSSFSheet sheet1 = wb.createSheet("입력값");
		sheet1.setDefaultColumnWidth(12);
		Row row = sheet1.createRow(1);
		row.setHeight((short)1000);
		
		int rowNo = 0, colNo = 0;
		XSSFFont fontRed = wb.createFont();
		fontRed.setColor(Font.COLOR_RED);
		
		getCell(sheet1, rowNo, colNo).setCellValue("일반사항");
		getCell(sheet1, rowNo, colNo).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+1).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+2).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+3).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+4).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+5).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+6).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+7).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+8).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+9).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+1).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+11).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+12).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+13).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+14).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+15).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+16).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+17).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+18).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+19).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+20).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+21).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+22).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+23).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+24).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+25).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+26).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+27).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+28).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+29).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+30).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+31).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+32).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+33).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+34).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+35).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+36).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+37).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo, colNo+37));
		
		getCell(sheet1, rowNo, colNo+38).setCellValue("위험사면 현황");
		getCell(sheet1, rowNo, colNo+38).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+39).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+40).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+41).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+42).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+43).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+44).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+45).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+46).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+47).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+48).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+49).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+50).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+51).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+52).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+53).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+54).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+55).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+56).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+57).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+58).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+38, colNo+58));
		
		getCell(sheet1, rowNo, colNo+59).setCellValue("전석분포비율(최단부)");
		getCell(sheet1, rowNo, colNo+59).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+60).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+61).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+62).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+63).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+64).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+65).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+66).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+67).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+68).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+69).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+70).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+71).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+72).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+73).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+74).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo, colNo+75).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+59, colNo+75));
		
		getCell(sheet1, rowNo, colNo+76).setCellValue("전석분포비율(중단부)");
		getCell(sheet1, rowNo, colNo+76).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+77).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+78).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+79).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+80).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+81).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+82).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+83).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+84).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+85).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+86).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+87).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+88).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+89).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+90).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+91).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo, colNo+92).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+76, colNo+92));
		
		getCell(sheet1, rowNo, colNo+93).setCellValue("전석분포비율(최상부)");
		getCell(sheet1, rowNo, colNo+93).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+94).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+95).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+96).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+97).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+98).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+99).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+100).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+101).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+102).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+103).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+104).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+105).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+106).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+107).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+108).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+109).setCellStyle(HeaderStyleBeige);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+93, colNo+109));
		
		getCell(sheet1, rowNo, colNo+110).setCellValue("최종 판정등급");
		getCell(sheet1, rowNo, colNo+110).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+111).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+112).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+113).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+114).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+115).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+116).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+117).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+118).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+119).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+120).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+121).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+110, colNo+121));
		
		String text ="종합의견 / 해당칸 입력 또는 보고서 직접입력";
		XSSFRichTextString richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);		
		getCell(sheet1, rowNo, colNo+122).setCellValue(richText);
		getCell(sheet1, rowNo, colNo+122).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+123).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+122, colNo+123));
		
		text ="판정표(해당하지 않는 항목에 대해서는 -로 표시)<무조건 모든 값을 넣어줘야함 -라도> 빈칸 없이";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);		
		getCell(sheet1, rowNo, colNo+124).setCellValue(richText);
		getCell(sheet1, rowNo, colNo+124).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+125).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+126).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+127).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+128).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+129).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo, colNo+134).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+124, colNo+134));
		
		getCell(sheet1, rowNo, colNo+135).setCellValue("편입지적");
		getCell(sheet1, rowNo, colNo+135).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+136).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+137).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+138).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+139).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+140).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+141).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+142).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+143).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+144).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+145).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+146).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+147).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+148).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+149).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+150).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+151).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+152).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+153).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+154).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+155).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+156).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+157).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+158).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+159).setCellStyle(HeaderStyleGreen2);
		getCell(sheet1, rowNo, colNo+160).setCellStyle(HeaderStyleGreen2);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+135, colNo+160));
		
		getCell(sheet1, rowNo, colNo+161).setCellValue("피해 현황 및 현황도");
		getCell(sheet1, rowNo, colNo+161).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+162).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+163).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+164).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+165).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+166).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+167).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+168).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+169).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+170).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+171).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+172).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+173).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+174).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+175).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+176).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+177).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+178).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+179).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+180).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+181).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+182).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+183).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+184).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+185).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+186).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+187).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+188).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+189).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+190).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+191).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+192).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+193).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+194).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+195).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+196).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+197).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+198).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+199).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+200).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+201).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+202).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+203).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+204).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+205).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+206).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo, colNo+207).setCellStyle(HeaderStyleBeige);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+161, colNo+207));
		
		text ="종합의견 / 해당칸 입력 또는 보고서 직접입력";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);		
		getCell(sheet1, rowNo, colNo+208).setCellValue(richText);
		getCell(sheet1, rowNo, colNo+208).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+209).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+210).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+211).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+212).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+213).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+214).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+215).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo, colNo+216).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+208, colNo+216));
		
		text ="위치(좌표 소수점 둘째자리)";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+1).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+2).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+5).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+6).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+8).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+11).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+13).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+14).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo, colNo+14));
		
		getCell(sheet1, rowNo+1, colNo+15).setCellValue("조사자");
		getCell(sheet1, rowNo+1, colNo+15).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+15).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+16).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+16).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+17).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+17).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+18).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+18).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+19).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+19).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+15, colNo+19));
		
		getCell(sheet1, rowNo+1, colNo+20).setCellValue("안정해석");
		getCell(sheet1, rowNo+1, colNo+20).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+20).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+21).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+21).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+22).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+22).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+23).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+23).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+24).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+24).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+25).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+25).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+20, colNo+25));
		
		getCell(sheet1, rowNo+1, colNo+26).setCellValue("조사지 환경");
		getCell(sheet1, rowNo+1, colNo+26).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+26).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+27).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+27).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+28).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+28).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+29).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+29).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+26, colNo+29));
		
		getCell(sheet1, rowNo+1, colNo+30).setCellValue("보호대상");
		getCell(sheet1, rowNo+1, colNo+30).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+30).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+31).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+31).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+32).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+32).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+33).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+33).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+34).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+34).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+35).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+35).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+36).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+36).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+1, colNo+37).setCellStyle(HeaderStyleBeige);
		getCell(sheet1, rowNo+2, colNo+37).setCellStyle(HeaderStyleBeige);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+30, colNo+37));
		
		getCell(sheet1, rowNo+1, colNo+38).setCellValue("기본정보");
		getCell(sheet1, rowNo+1, colNo+38).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+38).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+39).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+39).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+40).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+40).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+41).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+41).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+42).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+42).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+43).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+43).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+44).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+44).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+45).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+45).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+46).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+46).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+47).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+47).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+48).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+48).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+49).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+49).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+38, colNo+49));
		
		getCell(sheet1, rowNo+1, colNo+50).setCellValue("상태정보");
		getCell(sheet1, rowNo+1, colNo+50).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+50).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+51).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+51).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+52).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+52).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+53).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+53).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+54).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+54).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+55).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+55).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+56).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+56).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+57).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+57).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+58).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+58).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+50, colNo+58));
		
		text ="위치(소수점 둘째자리)";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+59).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+59).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+59).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+60).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+60).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+61).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+61).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+62).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+62).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+63).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+63).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+64).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+64).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+59, colNo+64));
		
		getCell(sheet1, rowNo+1, colNo+65).setCellValue("최단부 일반현황");
		getCell(sheet1, rowNo+1, colNo+65).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+65).setCellStyle(HeaderStyleYellow);		
		getCell(sheet1, rowNo+1, colNo+66).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+66).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+67).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+67).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+68).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+68).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+69).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+69).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+65, colNo+69));
		
		getCell(sheet1, rowNo+1, colNo+70).setCellValue("최단부 토석분포 현황");
		getCell(sheet1, rowNo+1, colNo+70).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+70).setCellStyle(HeaderStyleYellow);		
		getCell(sheet1, rowNo+1, colNo+71).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+71).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+72).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+72).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+73).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+73).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+74).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+74).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+75).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+75).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+70, colNo+75));
		
		text ="위치(소수점 둘째자리)";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+76).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+76).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+76).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+77).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+77).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+78).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+78).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+79).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+79).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+80).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+80).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+81).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+81).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+76, colNo+81));
		
		getCell(sheet1, rowNo+1, colNo+82).setCellValue("중단부 일반현황");
		getCell(sheet1, rowNo+1, colNo+82).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+82).setCellStyle(HeaderStyleOrange);		
		getCell(sheet1, rowNo+1, colNo+83).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+83).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+84).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+84).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+85).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+85).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+86).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+86).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+82, colNo+86));
		
		getCell(sheet1, rowNo+1, colNo+87).setCellValue("중단부 토석분포현황");
		getCell(sheet1, rowNo+1, colNo+87).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+87).setCellStyle(HeaderStyleOrange);		
		getCell(sheet1, rowNo+1, colNo+88).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+88).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+89).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+89).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+90).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+90).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+91).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+91).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+92).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+92).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+87, colNo+92));
		
		text ="위치(소수점 둘째자리)";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+93).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+93).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+93).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+94).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+94).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+95).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+95).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+96).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+96).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+97).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+97).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+98).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+98).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+93, colNo+98));
		
		getCell(sheet1, rowNo+1, colNo+99).setCellValue("최상부 일반현황");
		getCell(sheet1, rowNo+1, colNo+99).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+99).setCellStyle(HeaderStyleGreen);		
		getCell(sheet1, rowNo+1, colNo+100).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+100).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+101).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+101).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+102).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+102).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+103).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+103).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+99, colNo+103));
		
		getCell(sheet1, rowNo+1, colNo+104).setCellValue("최상부 토석분포현황");
		getCell(sheet1, rowNo+1, colNo+104).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+104).setCellStyle(HeaderStyleGreen);		
		getCell(sheet1, rowNo+1, colNo+105).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+105).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+106).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+106).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+107).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+107).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+108).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+108).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+1, colNo+109).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+109).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+104, colNo+109));
		
		getCell(sheet1, rowNo+1, colNo+110).setCellValue("판정 점수");
		getCell(sheet1, rowNo+1, colNo+110).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+110).setCellStyle(HeaderStyleGray);		
		getCell(sheet1, rowNo+1, colNo+111).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+111).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+112).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+112).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+110, colNo+112));
		
		getCell(sheet1, rowNo+1, colNo+113).setCellValue("판정등급");
		getCell(sheet1, rowNo+1, colNo+113).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+113).setCellStyle(HeaderStyleBlue);		
		getCell(sheet1, rowNo+1, colNo+114).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+114).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+113, colNo+114));
		
		getCell(sheet1, rowNo+1, colNo+115).setCellValue("등급보정");
		getCell(sheet1, rowNo+1, colNo+115).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+115).setCellStyle(HeaderStylePink);		
		getCell(sheet1, rowNo+1, colNo+116).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+116).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+115, colNo+116));
		
		getCell(sheet1, rowNo+1, colNo+117).setCellValue("관리방안");
		getCell(sheet1, rowNo+1, colNo+117).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+117).setCellStyle(HeaderStyleOrange);		
		getCell(sheet1, rowNo+1, colNo+118).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+118).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+119).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+119).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+120).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+120).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+117, colNo+120));
		
		getCell(sheet1, rowNo+1, colNo+121).setCellValue("관리필요성");
		getCell(sheet1, rowNo+1, colNo+121).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+121).setCellStyle(HeaderStyleBlue);		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+121, colNo+121));
		
		getCell(sheet1, rowNo+1, colNo+122).setCellValue("종합의견1");
		getCell(sheet1, rowNo+1, colNo+122).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+122).setCellStyle(HeaderStyleGreen3);		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+122, colNo+122));
		
		getCell(sheet1, rowNo+1, colNo+123).setCellValue("종합의견2");
		getCell(sheet1, rowNo+1, colNo+123).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+123).setCellStyle(HeaderStyleGray);		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+123, colNo+123));
		
		getCell(sheet1, rowNo+1, colNo+124).setCellValue("현장조사 계");
		getCell(sheet1, rowNo+1, colNo+124).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+124).setCellStyle(HeaderStylePink);		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+124, colNo+124));
		
		text ="피해가능성 0점 주의!!";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("0"),text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+125).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+125).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+125).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+126).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+126).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+125, colNo+126));
		
		getCell(sheet1, rowNo+1, colNo+127).setCellValue("지형");
		getCell(sheet1, rowNo+1, colNo+127).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+127).setCellStyle(HeaderStyleBlue);		
		getCell(sheet1, rowNo+1, colNo+128).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+128).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+1, colNo+129).setCellStyle(HeaderStyleBlue);
		getCell(sheet1, rowNo+2, colNo+129).setCellStyle(HeaderStyleBlue);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+127, colNo+129));
		
		text ="주위험요소  / 아래항목 중 택1";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+130).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+130).setCellStyle(HeaderStyleGreen);
		getCell(sheet1, rowNo+2, colNo+130).setCellStyle(HeaderStyleGreen);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+130, colNo+130));
		
		text ="잠재적위험 / 0점 주의!!";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);		
		getCell(sheet1, rowNo+1, colNo+131).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+131).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+131).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+132).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+132).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+133).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+133).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+1, colNo+134).setCellStyle(HeaderStyleOrange);
		getCell(sheet1, rowNo+2, colNo+134).setCellStyle(HeaderStyleOrange);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+131, colNo+134));
		
		getCell(sheet1, rowNo+1, colNo+135).setCellValue("면적계");
		getCell(sheet1, rowNo+1, colNo+135).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+135).setCellStyle(HeaderStylePink);		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+135, colNo+135));
		
		text ="지번기입 시 전, 도, 천, 임 등 모두 붙일것 / 편입면적 / 면적 및 편입면적은 M2로 입력";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(0,text.indexOf("/")-1,fontRed);
		richText.applyFont(text.lastIndexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+1, colNo+136).setCellValue(richText);
		getCell(sheet1, rowNo+1, colNo+136).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+136).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+137).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+137).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+138).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+138).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+139).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+139).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+140).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+140).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+141).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+141).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+142).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+142).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+143).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+143).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+144).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+144).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+145).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+145).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+146).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+146).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+147).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+147).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+148).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+148).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+149).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+149).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+150).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+150).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+151).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+151).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+152).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+152).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+153).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+153).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+154).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+154).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+155).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+155).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+156).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+156).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+157).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+157).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+158).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+158).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+159).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+159).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+1, colNo+160).setCellStyle(HeaderStylePink);
		getCell(sheet1, rowNo+2, colNo+160).setCellStyle(HeaderStylePink);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+136, colNo+160));
		
		getCell(sheet1, rowNo+1, colNo+161).setCellValue("현황도");
		getCell(sheet1, rowNo+1, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+164).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+164).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+166).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+166).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+167).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+167).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+168).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+168).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+169).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+169).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+170).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+170).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+171).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+171).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+172).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+172).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+173).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+173).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+174).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+174).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+175).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+175).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+176).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+176).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+177).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+177).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+178).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+178).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+179).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+179).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+180).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+180).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+181).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+181).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+182).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+182).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+183).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+183).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+184).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+184).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+185).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+185).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+186).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+186).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+187).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+187).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+188).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+188).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+189).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+189).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+190).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+190).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+191).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+191).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+192).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+192).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+193).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+193).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+194).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+194).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+195).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+195).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+196).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+196).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+197).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+197).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+198).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+198).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+199).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+199).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+1, colNo+200).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+200).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+161, colNo+200));
		
		// 대피로
		getCell(sheet1, rowNo+1, colNo+201).setCellValue("대피로");
		getCell(sheet1, rowNo+1, colNo+201).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+201).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+202).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+202).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+203).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+203).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+204).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+204).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+205).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+205).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+206).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+206).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+1, colNo+207).setCellStyle(HeaderStyleYellow);
		getCell(sheet1, rowNo+2, colNo+207).setCellStyle(HeaderStyleYellow);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+201, colNo+207));
		
		getCell(sheet1, rowNo+1, colNo+208).setCellValue("대상지개요");
		getCell(sheet1, rowNo+1, colNo+208).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+208).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+208, colNo+208));

		getCell(sheet1, rowNo+1, colNo+209).setCellValue("현장결과 위험계류 현황");
		getCell(sheet1, rowNo+1, colNo+209).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+209).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+1, colNo+210).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+210).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+209, colNo+210));

		getCell(sheet1, rowNo+1, colNo+211).setCellValue("지정사유 항목");
		getCell(sheet1, rowNo+1, colNo+211).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+211).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+1, colNo+212).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+212).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+211, colNo+212));

		getCell(sheet1, rowNo+1, colNo+213).setCellValue("구역설정근거 및 사유");
		getCell(sheet1, rowNo+1, colNo+213).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+213).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+213, colNo+213));

		getCell(sheet1, rowNo+1, colNo+214).setCellValue("기타특이사항");
		getCell(sheet1, rowNo+1, colNo+214).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+214).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+214, colNo+214));

		getCell(sheet1, rowNo+1, colNo+215).setCellValue("관리방안");
		getCell(sheet1, rowNo+1, colNo+215).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+215).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+1, colNo+216).setCellStyle(HeaderStyleGreen3);
		getCell(sheet1, rowNo+2, colNo+216).setCellStyle(HeaderStyleGreen3);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+215, colNo+216));
		
		colNo=0;
		rowNo=0;
		
		text ="행정구역(시군, 읍면, 리동 전부 붙임)";		
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("("),text.length(),fontRed);
		getCell(sheet1, rowNo+2, colNo).setCellValue(richText);
		getCell(sheet1, rowNo+2, colNo).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+1).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+2).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+5).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+6).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+8).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo, colNo+8));
		
		getCell(sheet1, rowNo+2, colNo+9).setCellValue("위도");
		getCell(sheet1, rowNo+2, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+11).setCellStyle(HeaderStyleGray);
		
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+9, colNo+11));
		
		getCell(sheet1, rowNo+2, colNo+12).setCellValue("경도");
		getCell(sheet1, rowNo+2, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+14).setCellStyle(HeaderStyleGray);
		getCell(sheet1, rowNo+2, colNo+14).setCellStyle(HeaderStyleGray);
		sheet1.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+12, colNo+14));
		
		colNo=0;
		rowNo=0;
		
		getCell(sheet1, rowNo+3, colNo).setCellValue("기번");
		getCell(sheet1, rowNo+3, colNo).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+1).setCellValue("구분");
		getCell(sheet1, rowNo+3, colNo+1).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+2).setCellValue("기초ID");
		getCell(sheet1, rowNo+3, colNo+2).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+3).setCellValue("시도");
		getCell(sheet1, rowNo+3, colNo+3).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+4).setCellValue("시군구");
		getCell(sheet1, rowNo+3, colNo+4).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+5).setCellValue("읍면");
		getCell(sheet1, rowNo+3, colNo+5).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+6).setCellValue("리동");
		getCell(sheet1, rowNo+3, colNo+6).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+7).setCellValue("지번");
		getCell(sheet1, rowNo+3, colNo+7).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+8).setCellValue("관리주체");
		getCell(sheet1, rowNo+3, colNo+8).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+9).setCellValue("도");
		getCell(sheet1, rowNo+3, colNo+9).setCellStyle(HeaderStyleGray);		
		
		getCell(sheet1, rowNo+3, colNo+10).setCellValue("분");
		getCell(sheet1, rowNo+3, colNo+10).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+11).setCellValue("초");
		getCell(sheet1, rowNo+3, colNo+11).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+12).setCellValue("도");
		getCell(sheet1, rowNo+3, colNo+12).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+13).setCellValue("분");
		getCell(sheet1, rowNo+3, colNo+13).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+14).setCellValue("초");
		getCell(sheet1, rowNo+3, colNo+14).setCellStyle(HeaderStyleGray);
		
		getCell(sheet1, rowNo+3, colNo+15).setCellValue("소속");
		getCell(sheet1, rowNo+3, colNo+15).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+16).setCellValue("직급");
		getCell(sheet1, rowNo+3, colNo+16).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+17).setCellValue("조사자");
		getCell(sheet1, rowNo+3, colNo+17).setCellStyle(HeaderStyleBlue);
		
		text ="조사일자\r\n" + 
				"2022. 00. 00.";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+18).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+18).setCellStyle(HeaderStyleBlue);
//		setText(getCell(sheet1, rowNo+3, colNo+18), "조사일자");
		
		text ="연락처\r\n" + 
				"043-000-0000";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+19).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+19).setCellStyle(HeaderStyleBlue);
		
		text ="1회 토석류\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+20).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+20).setCellStyle(HeaderStyleYellow);
		
		text ="정지조건\r\n" + 
				"소수점 2째";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+21).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+21).setCellStyle(HeaderStyleYellow);
		
		text ="가중치\r\n" + 
				"소수점 2째";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+22).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+22).setCellStyle(HeaderStyleYellow);
		
		text ="토석류량\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+23).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+23).setCellStyle(HeaderStyleYellow);
		
		text ="안정해석점수\r\n" + 
				"0 15 30";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+24).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+24).setCellStyle(HeaderStyleYellow);
		
		text ="이격거리\r\n" + 
				"안정해석";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+25).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+25).setCellStyle(HeaderStyleYellow);
		
		text ="유역(ha)\r\n" + 
				"소수점1";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+26).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+26).setCellStyle(HeaderStylePink);
		
		text ="유역m2\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+27).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+27).setCellStyle(HeaderStylePink);
		
		text ="취약지역 면적\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+28).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+28).setCellStyle(HeaderStylePink);
		
		text ="취약m2\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+29).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+29).setCellStyle(HeaderStylePink);
		
		text ="보호시설\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+30).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+30).setCellStyle(HeaderStyleBeige);
		
		text ="보호 개수\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+31).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+31).setCellStyle(HeaderStyleBeige);
		
		text ="인가\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+32).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+32).setCellStyle(HeaderStyleBeige);
		
		text ="인가 개수\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+33).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+33).setCellStyle(HeaderStyleBeige);
		
		text ="상부주요시설\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+34).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+34).setCellStyle(HeaderStyleBeige);
		
		text ="상부인가\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+35).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+35).setCellStyle(HeaderStyleBeige);
		
		text ="하부주요시설\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+36).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+36).setCellStyle(HeaderStyleBeige);
		
		text ="하부인가\r\n" + 
				"숫자 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+37).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+37).setCellStyle(HeaderStyleBeige);
		
		text ="황폐발생\r\n" + 
				"1 2 2.5 3";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+38).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+38).setCellStyle(HeaderStyleBlue);
		
		text ="변곡점길이\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+39).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+39).setCellStyle(HeaderStyleBlue);
		
		text ="변곡점고도\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+40).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+40).setCellStyle(HeaderStyleBlue);
		
		text ="계류길이\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+41).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+41).setCellStyle(HeaderStyleBlue);
		
		text ="평균경사도\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+42).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+42).setCellStyle(HeaderStyleBlue);
		
		text ="최저경사\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+43).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+43).setCellStyle(HeaderStyleBlue);
		
		text ="최고경사\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+44).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+44).setCellStyle(HeaderStyleBlue);
		
		text ="임상\r\n" + 
				"침 활 혼 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+45).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+45).setCellStyle(HeaderStyleBlue);
		
		text ="임분밀도\r\n" + 
				"소 중 밀 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+46).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+46).setCellStyle(HeaderStyleBlue);
		
		text ="임분경급\r\n" + 
				"소 중 대 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+47).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+47).setCellStyle(HeaderStyleBlue);
		
		text ="종점특이\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+48).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+48).setCellStyle(HeaderStyleBlue);
		
		text ="시점특이\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+49).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+49).setCellStyle(HeaderStyleBlue);
		
		text ="월류상태\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+50).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+50).setCellStyle(HeaderStyleGreen);
		
		text ="계류상황\r\n" + 
				"협착 확폭 인공";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+51).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+51).setCellStyle(HeaderStyleGreen);
		
		text ="곡류상태\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+52).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+52).setCellStyle(HeaderStyleGreen);
		
		text ="계류수\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+53).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+53).setCellStyle(HeaderStyleGreen);
		
		text ="유목여부\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+54).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+54).setCellStyle(HeaderStyleGreen);
		
		text ="유목길이\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+55).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+55).setCellStyle(HeaderStyleGreen);
		
		text ="퇴적지\r\n" + 
				"유 무";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+56).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+56).setCellStyle(HeaderStyleGreen);
		
		text ="좌안붕괴지\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+57).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+57).setCellStyle(HeaderStyleGreen);

		text ="우안붕괴지\r\n" + 
				"정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+58).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+58).setCellStyle(HeaderStyleGreen);
		
		text ="위도도1\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+59).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+59).setCellStyle(HeaderStyleYellow);
		
		text ="위도분1\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+60).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+60).setCellStyle(HeaderStyleYellow);
		
		text ="위도초1\r\n" + 
				"00.00 자동";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+61).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+61).setCellStyle(HeaderStyleYellow);
		
		text ="경도도1\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+62).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+62).setCellStyle(HeaderStyleYellow);
		
		text ="경도분1\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+63).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+63).setCellStyle(HeaderStyleYellow);
		
		text ="경도초1\r\n" + 
				"00.00 자동";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+64).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+64).setCellStyle(HeaderStyleYellow);
		
		text ="위치1\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+65).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+65).setCellStyle(HeaderStyleYellow);
		
		text ="고도1\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+66).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+66).setCellStyle(HeaderStyleYellow);
		
		text ="토심1\r\n" + 
				"cm정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+67).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+67).setCellStyle(HeaderStyleYellow);
		
		text ="폭1\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+68).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+68).setCellStyle(HeaderStyleYellow);
		
		text ="경사1\r\n" + 
				"°정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+69).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+69).setCellStyle(HeaderStyleYellow);
		
		text ="총계(100확인)\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+70).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+70).setCellStyle(HeaderStyleYellow);
		
		text ="암반1\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+71).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+71).setCellStyle(HeaderStyleYellow);
		
		text ="전석1\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+72).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+72).setCellStyle(HeaderStyleYellow);
		
		text ="자갈1\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+73).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+73).setCellStyle(HeaderStyleYellow);
		
		text ="모래1\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+74).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+74).setCellStyle(HeaderStyleYellow);
		
		text ="기타1\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+75).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+75).setCellStyle(HeaderStyleYellow);
		
		text ="위도도2\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+76).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+76).setCellStyle(HeaderStyleOrange);
		
		text ="위도분2\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+77).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+77).setCellStyle(HeaderStyleOrange);
		
		text ="위도초2\r\n" + 
				"00.00 자동";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+78).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+78).setCellStyle(HeaderStyleOrange);
		
		text ="경도도2\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+79).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+79).setCellStyle(HeaderStyleOrange);
		
		text ="경도분2\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+80).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+80).setCellStyle(HeaderStyleOrange);
		
		text ="경도초2\r\n" + 
				"00.00 자동";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+81).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+81).setCellStyle(HeaderStyleOrange);
		
		text ="위치2\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+82).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+82).setCellStyle(HeaderStyleOrange);
		
		text ="고도2\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+83).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+83).setCellStyle(HeaderStyleOrange);
		
		text ="토심2\r\n" + 
				"cm정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+84).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+84).setCellStyle(HeaderStyleOrange);
		
		text ="폭2\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+85).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+85).setCellStyle(HeaderStyleOrange);
		
		text ="경사2\r\n" + 
				"°정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+86).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+86).setCellStyle(HeaderStyleOrange);
		
		text ="총계(100확인)\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+87).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+87).setCellStyle(HeaderStyleOrange);
		
		text ="암반2\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+88).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+88).setCellStyle(HeaderStyleOrange);
		
		text ="전석2\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+89).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+89).setCellStyle(HeaderStyleOrange);
		
		text ="자갈2\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+90).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+90).setCellStyle(HeaderStyleOrange);
		
		text ="모래2\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+91).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+91).setCellStyle(HeaderStyleOrange);
		
		text ="기타2\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+92).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+92).setCellStyle(HeaderStyleOrange);
		
		text ="위도도3\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+93).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+93).setCellStyle(HeaderStyleGreen);
		
		text ="위도분3\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+94).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+94).setCellStyle(HeaderStyleGreen);
		
		text ="위도초3\r\n" + 
				"00.00 자동";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+95).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+95).setCellStyle(HeaderStyleGreen);
		
		text ="경도도3\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+96).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+96).setCellStyle(HeaderStyleGreen);
		
		text ="경도분3\r\n" + 
				"00 자동입력";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+97).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+97).setCellStyle(HeaderStyleGreen);
		
		text ="경도초3\r\n" + 
				"00.00 자동";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+98).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+98).setCellStyle(HeaderStyleGreen);
		
		text ="위치3\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+99).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+99).setCellStyle(HeaderStyleGreen);
		
		text ="고도3\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+100).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+100).setCellStyle(HeaderStyleGreen);
		
		text ="토심3\r\n" + 
				"cm정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+101).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+101).setCellStyle(HeaderStyleGreen);
		
		text ="폭3\r\n" + 
				"m정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+102).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+102).setCellStyle(HeaderStyleGreen);
		
		text ="경사3\r\n" + 
				"°정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+103).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+103).setCellStyle(HeaderStyleGreen);
		
		text ="총계(100확인)\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+104).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+104).setCellStyle(HeaderStyleGreen);
		
		text ="암반3\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+105).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+105).setCellStyle(HeaderStyleGreen);
		
		text ="전석3\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+106).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+106).setCellStyle(HeaderStyleGreen);
		
		text ="자갈3\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+107).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+107).setCellStyle(HeaderStyleGreen);
		
		text ="모래3\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+108).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+108).setCellStyle(HeaderStyleGreen);
		
		text ="기타3\r\n" + 
				"%정수";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+109).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+109).setCellStyle(HeaderStyleGreen);
		
		text ="현장조사점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+110).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+110).setCellStyle(HeaderStyleGray);
		
		text ="안정해석점수\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+111).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+111).setCellStyle(HeaderStyleGray);
		
		text ="점수계\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+112).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+112).setCellStyle(HeaderStyleGray);
		
		text ="판정등급\r\n" + 
				"에이 비 씨";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+113).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+113).setCellStyle(HeaderStyleBlue);
		
		text ="판정결과\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+114).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+114).setCellStyle(HeaderStyleBlue);
		
		text ="보정\r\n" + 
				"상 하 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+115).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+115).setCellStyle(HeaderStylePink);
		
		text ="등급보정 사유\r\n" + 
				"텍스트 0 -";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+116).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+116).setCellStyle(HeaderStylePink);
		
		text ="사업 가능여부\r\n" + 
				"가능 불가능";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+117).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+117).setCellStyle(HeaderStyleOrange);
		
		text ="대책방안\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+118).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+118).setCellStyle(HeaderStyleOrange);
		
		text ="필요공종\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+119).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+119).setCellStyle(HeaderStyleOrange);
		
		text ="대피장소\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+120).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+120).setCellStyle(HeaderStyleOrange);
		
		text ="관리방안\r\n" + 
				"현 비 구";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+121).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+121).setCellStyle(HeaderStyleBlue);
		
		text ="종합의견1\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+122).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+122).setCellStyle(HeaderStyleGreen3);
		
		text ="종합의견2\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+123).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+123).setCellStyle(HeaderStyleGreen3);
		
		text ="계\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+124).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+124).setCellStyle(HeaderStylePink);
		
		text ="피해이력\r\n" + 
				"0 3 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+125).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+125).setCellStyle(HeaderStyleYellow);
		
		text ="보호시설\r\n" + 
				"0 5 7 10";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+126).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+126).setCellStyle(HeaderStyleYellow);
		
		text ="유역면적\r\n" + 
				"1 2 3 4 5";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+127).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+127).setCellStyle(HeaderStyleBlue);
		
		text ="평균경사\r\n" + 
				"3 5 8 10";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+128).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+128).setCellStyle(HeaderStyleBlue);
		
		text ="토심\r\n" + 
				"1 3 5 7 10";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+129).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+129).setCellStyle(HeaderStyleBlue);
		
		text ="붕괴 / 침식 / 전석 / 토석류 흔적\r\n" + 
				"0 10 20";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+130).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+130).setCellStyle(HeaderStyleGreen);
		
		text ="산사태위험도\r\n" + 
				"0 1 2 3";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+131).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+131).setCellStyle(HeaderStyleOrange);
		
		text ="산림현황\r\n" + 
				"0 1 2 3";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+132).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+132).setCellStyle(HeaderStyleOrange);
		
		text ="뿌리특성\r\n" + 
				"0 1 2";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+133).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+133).setCellStyle(HeaderStyleOrange);
		
		text ="기타(선택형)\r\n" + 
				"0 2";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+134).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+134).setCellStyle(HeaderStyleOrange);
		
		text ="면적계\r\n" + 
				"자동계산";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(0,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+135).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+135).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+136).setCellValue("지번1");
		getCell(sheet1, rowNo+3, colNo+136).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+137).setCellValue("지목1");
		getCell(sheet1, rowNo+3, colNo+137).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+138).setCellValue("면적1");
		getCell(sheet1, rowNo+3, colNo+138).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+139).setCellValue("편입면적1");
		getCell(sheet1, rowNo+3, colNo+139).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+140).setCellValue("소유1");
		getCell(sheet1, rowNo+3, colNo+140).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+141).setCellValue("지번2");
		getCell(sheet1, rowNo+3, colNo+141).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+142).setCellValue("지목2");
		getCell(sheet1, rowNo+3, colNo+142).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+143).setCellValue("면적2");
		getCell(sheet1, rowNo+3, colNo+143).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+144).setCellValue("면편입적2");
		getCell(sheet1, rowNo+3, colNo+144).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+145).setCellValue("소유2");
		getCell(sheet1, rowNo+3, colNo+145).setCellStyle(HeaderStyleBeige);

		getCell(sheet1, rowNo+3, colNo+146).setCellValue("지번3");
		getCell(sheet1, rowNo+3, colNo+146).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+147).setCellValue("지목3");
		getCell(sheet1, rowNo+3, colNo+147).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+148).setCellValue("면적3");
		getCell(sheet1, rowNo+3, colNo+148).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+149).setCellValue("편입면적3");
		getCell(sheet1, rowNo+3, colNo+149).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+150).setCellValue("소유3");
		getCell(sheet1, rowNo+3, colNo+150).setCellStyle(HeaderStyleBlue);

		getCell(sheet1, rowNo+3, colNo+151).setCellValue("지번4");
		getCell(sheet1, rowNo+3, colNo+151).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+152).setCellValue("지목4");
		getCell(sheet1, rowNo+3, colNo+152).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+153).setCellValue("면적4");
		getCell(sheet1, rowNo+3, colNo+153).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+154).setCellValue("편입면적4");
		getCell(sheet1, rowNo+3, colNo+154).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+155).setCellValue("소유4");
		getCell(sheet1, rowNo+3, colNo+155).setCellStyle(HeaderStyleGray);

		getCell(sheet1, rowNo+3, colNo+156).setCellValue("지번5");
		getCell(sheet1, rowNo+3, colNo+156).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+157).setCellValue("지목5");
		getCell(sheet1, rowNo+3, colNo+157).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+158).setCellValue("면적5");
		getCell(sheet1, rowNo+3, colNo+158).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+159).setCellValue("편입면적5");
		getCell(sheet1, rowNo+3, colNo+159).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+160).setCellValue("소유5");
		getCell(sheet1, rowNo+3, colNo+160).setCellStyle(HeaderStylePink);

		getCell(sheet1, rowNo+3, colNo+161).setCellValue("연번5");
		getCell(sheet1, rowNo+3, colNo+161).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+162).setCellValue("위도도5");
		getCell(sheet1, rowNo+3, colNo+162).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+163).setCellValue("위도분5");
		getCell(sheet1, rowNo+3, colNo+163).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+164).setCellValue("위도초5");
		getCell(sheet1, rowNo+3, colNo+164).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+165).setCellValue("경도도5");
		getCell(sheet1, rowNo+3, colNo+165).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+166).setCellValue("경도분5");
		getCell(sheet1, rowNo+3, colNo+166).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+167).setCellValue("경도초5");
		getCell(sheet1, rowNo+3, colNo+167).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+168).setCellValue("특이5");
		getCell(sheet1, rowNo+3, colNo+168).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet1, rowNo+3, colNo+169).setCellValue("연번6");
		getCell(sheet1, rowNo+3, colNo+169).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+170).setCellValue("위도도6");
		getCell(sheet1, rowNo+3, colNo+170).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+171).setCellValue("위도분");
		getCell(sheet1, rowNo+3, colNo+171).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+172).setCellValue("위도초");
		getCell(sheet1, rowNo+3, colNo+172).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+173).setCellValue("경도도6");
		getCell(sheet1, rowNo+3, colNo+173).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+174).setCellValue("경도분");
		getCell(sheet1, rowNo+3, colNo+174).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+175).setCellValue("경도초6");
		getCell(sheet1, rowNo+3, colNo+175).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+176).setCellValue("특이6");
		getCell(sheet1, rowNo+3, colNo+176).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet1, rowNo+3, colNo+177).setCellValue("연번7");
		getCell(sheet1, rowNo+3, colNo+177).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+178).setCellValue("위도도7");
		getCell(sheet1, rowNo+3, colNo+178).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+179).setCellValue("위도분7");
		getCell(sheet1, rowNo+3, colNo+179).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+180).setCellValue("위도초7");
		getCell(sheet1, rowNo+3, colNo+180).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+181).setCellValue("경도도7");
		getCell(sheet1, rowNo+3, colNo+181).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+182).setCellValue("경도분7");
		getCell(sheet1, rowNo+3, colNo+182).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+183).setCellValue("경도초7");
		getCell(sheet1, rowNo+3, colNo+183).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+184).setCellValue("특이7");
		getCell(sheet1, rowNo+3, colNo+184).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet1, rowNo+3, colNo+185).setCellValue("연번8");
		getCell(sheet1, rowNo+3, colNo+185).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+186).setCellValue("위도도");
		getCell(sheet1, rowNo+3, colNo+186).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+187).setCellValue("위도분8");
		getCell(sheet1, rowNo+3, colNo+187).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+188).setCellValue("위도초8");
		getCell(sheet1, rowNo+3, colNo+188).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+189).setCellValue("경도도8");
		getCell(sheet1, rowNo+3, colNo+189).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+190).setCellValue("경도분8");
		getCell(sheet1, rowNo+3, colNo+190).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+191).setCellValue("경도초8");
		getCell(sheet1, rowNo+3, colNo+191).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+192).setCellValue("특이8");
		getCell(sheet1, rowNo+3, colNo+192).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet1, rowNo+3, colNo+193).setCellValue("연번9");
		getCell(sheet1, rowNo+3, colNo+193).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+194).setCellValue("위도도9");
		getCell(sheet1, rowNo+3, colNo+194).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+195).setCellValue("위도분9");
		getCell(sheet1, rowNo+3, colNo+195).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+196).setCellValue("위도초9");
		getCell(sheet1, rowNo+3, colNo+196).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+197).setCellValue("경도도9");
		getCell(sheet1, rowNo+3, colNo+197).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+198).setCellValue("경도분9");
		getCell(sheet1, rowNo+3, colNo+198).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+199).setCellValue("경도초9");
		getCell(sheet1, rowNo+3, colNo+199).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+200).setCellValue("특이9");
		getCell(sheet1, rowNo+3, colNo+200).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet1, rowNo+3, colNo+201).setCellValue("장소");
		getCell(sheet1, rowNo+3, colNo+201).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+202).setCellValue("대위도");
		getCell(sheet1, rowNo+3, colNo+202).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+203).setCellValue("대위분");
		getCell(sheet1, rowNo+3, colNo+203).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+204).setCellValue("대위초");
		getCell(sheet1, rowNo+3, colNo+204).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+205).setCellValue("대경도");
		getCell(sheet1, rowNo+3, colNo+205).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+206).setCellValue("대경분");
		getCell(sheet1, rowNo+3, colNo+206).setCellStyle(HeaderStylePink);
		
		getCell(sheet1, rowNo+3, colNo+207).setCellValue("대경초");
		getCell(sheet1, rowNo+3, colNo+207).setCellStyle(HeaderStylePink);
		
		text ="대상지개요\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+208).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+208).setCellStyle(HeaderStyleGreen3);
		
		text ="현장결과1 / 유역현황\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+209).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+209).setCellStyle(HeaderStyleGreen3);
		
		text ="현장결과2 / 위험징후 및\r\n" + 
				"분포현환 / 텍스트";
		richText = new XSSFRichTextString(text);
		richText.applyFont(text.lastIndexOf("/")+1,text.length(),fontRed);		
		getCell(sheet1, rowNo+3, colNo+210).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+210).setCellStyle(HeaderStyleGreen3);
		
		text ="간략한 종합의견 및 지정사유\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+211).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+211).setCellStyle(HeaderStyleGreen3);
		
		text ="지정사유 또는 안정해석 결과 입력 / 텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("/")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+212).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+212).setCellStyle(HeaderStyleGreen3);
		
		text ="구역설정\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+213).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+213).setCellStyle(HeaderStyleGreen3);
		
		text ="특이사항 및 진입여건\r\n" + 
				"텍스트";
		richText = new XSSFRichTextString(text);		
		richText.applyFont(text.indexOf("\n")+1,text.length(),fontRed);
		getCell(sheet1, rowNo+3, colNo+214).setCellValue(richText);
		getCell(sheet1, rowNo+3, colNo+214).setCellStyle(HeaderStyleGreen3);
		
		
		getCell(sheet1, rowNo+3, colNo+215).setCellValue("재해예방사업종\r\n" + 
				"선정사유");
		getCell(sheet1, rowNo+3, colNo+215).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet1, rowNo+3, colNo+216).setCellValue("기타종합의견\r\n" + 
				"주민협의 및 대체대안");
		getCell(sheet1, rowNo+3, colNo+216).setCellStyle(HeaderStyleGreen3);
		
		colNo=0;
		rowNo=4;
		String formulaText = "";
		int formatM = 0;
		double formatS = 0.0;

		if(dataList.size() > 0) {
			//HBLEE (토석류)
			for (int i=0; i<dataList.size(); i++) {
				colNo=0;
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("fid").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svytype").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svyid").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("sd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("sgg").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("emd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("ri").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("jibun").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("mgc").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("latD").toString()); /*수정필요*/
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("latM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("latM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("latS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("latS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("lonD").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("lonM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lonM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("lonS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lonS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svydept").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("syvofcps").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				if(dataList.get(i).get("svyuser").toString().equals("")) {
					cell.setCellValue("");					
				}else {					
					cell.setCellValue(dataList.get(i).get("svyuser").toString());
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				String format = dataList.get(i).get("svydt").toString().replaceAll("-", ". ").concat(".");
				cell.setCellValue(format);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);
				cell.setCellValue(dataList.get(i).get("svymbtl").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*1회 토석류(값없음)*/
				if(dataList.get(i).get("frstsoil").toString().equals("")) {
					cell.setCellValue("");										
				}else {					
					cell.setCellValue(parseInt(dataList.get(i).get("frstsoil").toString()));					
				}					
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*정지조건(값없음)*/
				if(dataList.get(i).get("stopcndtn").toString().equals("") || doubleTypeCheck(dataList.get(i).get("stopcndtn").toString()) > 1) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("stopcndtn").toString()));	
				}					
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*가중치(값없음)*/
				if(dataList.get(i).get("wghtval").toString().equals("") || doubleTypeCheck(dataList.get(i).get("wghtval").toString()) > 1) {
					cell.setCellValue("");										
				}else {					
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("wghtval").toString()));					
				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토석류량(값없음)*/
				if(dataList.get(i).get("soilqy").toString().equals("")) {
					cell.setCellValue("");										
				}else {					
					cell.setCellValue(parseInt(dataList.get(i).get("soilqy").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*안정해석점수*/
				if(dataList.get(i).get("stbintrprtscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("stbintrprtscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*이격거리*/
				cell.setCellValue(dataList.get(i).get("gapdstnc").toString());					
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*유역(ha)*/
				if(dataList.get(i).get("dgrha").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("dgrha").toString()));					
				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*유역m2*/
				if(dataList.get(i).get("dgrha").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formulaText = "AA"+(rowNo+1)+"*10000";
					cell.setCellFormula(formulaText);					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*취약지역 면적*/
				formulaText = "AD"+(rowNo+1)+"*0.0001";
				cell.setCellFormula(formulaText);
//				if(dataList.get(i).get("wkha").toString().equals("")) {
//					cell.setCellValue("");										
//				}else {
//					cell.setCellValue(Double.parseDouble(dataList.get(i).get("wkha").toString()));					
//				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*취약m2*/
				formulaText = "SUM(EJ"+(rowNo+1)+",EO"+(rowNo+1)+",ET"+(rowNo+1)+",EY"+(rowNo+1)+",FD"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
//				if(dataList.get(i).get("wkha").toString().equals("")) {
//					cell.setCellValue("");
//				}else {					
//					formulaText = "AC"+(rowNo+1)+"*10000";
//					cell.setCellFormula(formulaText);					
//				}
				cell.setCellStyle(ValueStyle5);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보호시설*/
				cell.setCellValue(dataList.get(i).get("saftyfclt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보호개수*/
				cell.setCellValue(dataList.get(i).get("saftycnt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*인가*/
				cell.setCellValue(dataList.get(i).get("cnfm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*인가 개수*/
				cell.setCellValue(dataList.get(i).get("cnfmcnt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*상부주요시설*/
				cell.setCellValue(dataList.get(i).get("uprmain").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*상부인가*/
				cell.setCellValue(dataList.get(i).get("uprcnfm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*하부주요시설*/
				cell.setCellValue(dataList.get(i).get("lwrmain").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*하부인가*/
				cell.setCellValue(dataList.get(i).get("lwrcnfm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*황폐발생*/
				if(dataList.get(i).get("devoccause").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					String devoccause = dataList.get(i).get("devoccause").toString();
					if(devoccause.equals("1등급")) {
						devoccause = "1"; 
					}else if(devoccause.equals("2등급 50% 이상")) {
						devoccause = "2";
					}else if(devoccause.equals("2등급 50% 미만")) {
						devoccause = "2.5";
					}else if(devoccause.equals("3등급 이하")) {
						devoccause = "3";
					}
					cell.setCellValue(devoccause);					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*변곡점길이*/
				formulaText = "CE"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*변곡점고도*/
				formulaText = "CF"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*계류길이*/
				if(dataList.get(i).get("tltrntlt").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("tltrntlt").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*평균경사도*/
				if(dataList.get(i).get("mntntrntavg").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mntntrntavg").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*최저경사*/
				if(dataList.get(i).get("mntntrntmin").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mntntrntmin").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*최고경사*/
				if(dataList.get(i).get("mntntrntmax").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mntntrntmax").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*임상*/
				cell.setCellValue(dataList.get(i).get("frstfr").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*임분밀도*/
				cell.setCellValue(dataList.get(i).get("dnsty").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*임분경급*/
				cell.setCellValue(dataList.get(i).get("dmclschk").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*종점특이*/
				cell.setCellValue(dataList.get(i).get("enpopion").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*시점특이*/
				cell.setCellValue(dataList.get(i).get("stpopion").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*월류상태*/
				cell.setCellValue(dataList.get(i).get("ovflwsttus").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*계류상황*/
				cell.setCellValue(dataList.get(i).get("tltrntltsttus").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*곡류상태*/
				cell.setCellValue(dataList.get(i).get("mndrsttus").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*계류수*/
				cell.setCellValue(dataList.get(i).get("mntstrm").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*유목여부*/
				String nmdicat = "무";
				if(dataList.get(i).get("nmdiclng").toString().length() > 0 && (!dataList.get(i).get("nmdiclng").equals("0"))) {
					nmdicat = "유";
				}
				cell.setCellValue(nmdicat);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*유목길이*/
				cell.setCellValue(dataList.get(i).get("nmdiclng").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*퇴적지*/
				cell.setCellValue(dataList.get(i).get("destat").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*좌안붕괴지*/
				cell.setCellValue(dataList.get(i).get("lftcrkarea").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*우안붕괴지*/
				cell.setCellValue(dataList.get(i).get("rghtcrkarea").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도1*/
				formulaText = "J"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분1*/
				formulaText = "K"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초1*/
				formulaText = "L"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도1*/
				formulaText = "M"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분1*/
				formulaText = "N"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초1*/
				formulaText = "O"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle3);
				                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
				cell = getCell(sheet1, rowNo, colNo++);/*위치1*/
				if(dataList.get(i).get("loc1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("loc1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*고도1*/
				if(dataList.get(i).get("height1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("height1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토심1*/
				if(dataList.get(i).get("soildept1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("soildept1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*폭1*/
				if(dataList.get(i).get("range1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("range1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사1*/
				if(dataList.get(i).get("slope1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slope1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*총계(100확인)*/
				formulaText = "SUM(BT"+(rowNo+1)+":BX"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*암반1*/
				if(dataList.get(i).get("bdrck1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrck1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*전석1*/
				if(dataList.get(i).get("bldstn1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("bldstn1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*자갈1*/
				if(dataList.get(i).get("grvl1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("grvl1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*모래1*/
				if(dataList.get(i).get("sand1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("sand1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*기타1*/
				if(dataList.get(i).get("etc1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("etc1").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도2*/ /*수정*/
				if(dataList.get(i).get("mdllatD").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mdllatD").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분2*/ /*수정*/
				if(dataList.get(i).get("mdllatM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("mdllatM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초2*/ /*수정*/
				if(dataList.get(i).get("mdllatS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("mdllatS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도2*/ /*수정*/
				if(dataList.get(i).get("mdllonD").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mdllonD").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분2*/ /*수정*/
				if(dataList.get(i).get("mdllonM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("mdllonM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초2*/ /*수정*/
				if(dataList.get(i).get("mdllonS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("mdllonS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위치2*/
				if(dataList.get(i).get("loc2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("loc2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*고도2*/
				if(dataList.get(i).get("height2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("height2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토심2*/
				if(dataList.get(i).get("soildept2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("soildept2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*폭2*/
				if(dataList.get(i).get("range2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("range2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사2*/
				if(dataList.get(i).get("slope2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slope2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*총계(100확인)*/
				formulaText = "SUM(CK"+(rowNo+1)+":CO"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*암반2*/
				if(dataList.get(i).get("bdrck2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrck2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*전석2*/
				if(dataList.get(i).get("bldstn2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("bldstn2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*자갈2*/
				if(dataList.get(i).get("grvl2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("grvl2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*모래2*/
				if(dataList.get(i).get("sand2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("sand2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*기타2*/
				if(dataList.get(i).get("etc2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("etc2").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도3*/ /*수정*/
				if(dataList.get(i).get("uplatD").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("uplatD").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분3*/ /*수정*/
				if(dataList.get(i).get("uplatM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("uplatM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초3*/ /*수정*/
				if(dataList.get(i).get("uplatS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("uplatS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도3*/ /*수정*/
				if(dataList.get(i).get("uplonD").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("uplonD").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분3*/ /*수정*/
				if(dataList.get(i).get("uplonM").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("uplonM").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초3*/ /*수정*/
				if(dataList.get(i).get("uplonS").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("uplonS").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위치3*/
				if(dataList.get(i).get("loc3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("loc3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*고도3*/
				if(dataList.get(i).get("height3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("height3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토심3*/
				if(dataList.get(i).get("soildept3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("soildept3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*폭3*/
				if(dataList.get(i).get("range3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("range3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경사3*/
				if(dataList.get(i).get("slope3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slope3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*총계(100확인)*/
				formulaText = "SUM(DB"+(rowNo+1)+":DF"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*암반3*/
				if(dataList.get(i).get("bdrck3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("bdrck3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*전석3*/
				if(dataList.get(i).get("bldstn3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("bldstn3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*자갈3*/
				if(dataList.get(i).get("grvl3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("grvl3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*모래3*/
				if(dataList.get(i).get("sand3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("sand3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*기타3*/
				if(dataList.get(i).get("etc3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("etc3").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*현장조사점수*/
				formulaText = "DU"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*안정해석점수*/
				formulaText = "Y"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*점수계*/
				formulaText = "SUM(DG"+(rowNo+1)+":DH"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*판정등급*/
				cell.setCellValue(dataList.get(i).get("jdggrd").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*판정결과*/
				formulaText = "IF(DJ"+(rowNo+1)+"=\"A등급\",\"위험\",IF(DJ"+(rowNo+1)+"=\"B등급\",\"잠재적 위험\",IF(DJ"+(rowNo+1)+"=\"C등급\",\"위험성 낮음\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보정*/
				cell.setCellValue(dataList.get(i).get("revisn").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*등급보정사유*/
				cell.setCellValue(dataList.get(i).get("revisnrsn").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*사업 가능여부*/
				cell.setCellValue(dataList.get(i).get("taskpsblat").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대책방안*/
				cell.setCellValue(dataList.get(i).get("cntrpln").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*필요공종*/
				cell.setCellValue(dataList.get(i).get("altrv").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대피장소*/
				cell.setCellValue(dataList.get(i).get("shunt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*관리방안*/
				cell.setCellValue(dataList.get(i).get("mngpln").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*종합의견1(값없음)*/
//				cell.setCellValue(dataList.get(i).get("opinion1").toString());
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*종합의견2(값없음)*/
//				cell.setCellValue(dataList.get(i).get("opinion2").toString());
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*계*/
				formulaText = "SUM(DV"+(rowNo+1)+",DW"+(rowNo+1)+",DX"+(rowNo+1)+",DY"+(rowNo+1)+",DZ"+(rowNo+1)+",EA"+(rowNo+1)+",EB"+(rowNo+1)+",EC"+(rowNo+1)+",ED"+(rowNo+1)+",EE"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*피해이력*/
				if(dataList.get(i).get("dmgehistscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("dmgehistscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*보호시설*/
				if(dataList.get(i).get("saftyfcltscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("saftyfcltscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*유역면적*/
				if(dataList.get(i).get("dgrareascore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("dgrareascore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*평균경사*/
				if(dataList.get(i).get("slpavgscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("slpavgscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*토심*/
				if(dataList.get(i).get("soildeptscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("soildeptscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*주위험요소*/
				
				// 붕괴점수
				int clpsscore = 0;
				// 침식점수
				int corrosionscore = 0;
				// 전석점수
				int bldrstnescore = 0;
				// 토석류 흔적 점수
				int soiltracescore = 0;
				// 주 위험요소 점수
				ArrayList<Integer> mainriskelemarr = new ArrayList<Integer>();
				int mainriskelemscore = 0;
				if(dataList.get(i).get("clpsscore") != null && dataList.get(i).get("clpsscore").toString().length() > 0) clpsscore = Integer.parseInt(dataList.get(i).get("clpsscore").toString());    
				if(dataList.get(i).get("corrosionscore") != null && dataList.get(i).get("corrosionscore").toString().length() > 0) corrosionscore = Integer.parseInt(dataList.get(i).get("corrosionscore").toString());    
				if(dataList.get(i).get("bldrstnescore") != null && dataList.get(i).get("bldrstnescore").toString().length() > 0) bldrstnescore = Integer.parseInt(dataList.get(i).get("bldrstnescore").toString());    
				if(dataList.get(i).get("soiltracescore") != null && dataList.get(i).get("soiltracescore").toString().length() > 0) soiltracescore = Integer.parseInt(dataList.get(i).get("soiltracescore").toString());    
				
				
				mainriskelemarr.add(clpsscore);
				mainriskelemarr.add(corrosionscore);
				mainriskelemarr.add(bldrstnescore);
				mainriskelemarr.add(soiltracescore);
				
				mainriskelemscore = Collections.max(mainriskelemarr);
				
				cell.setCellValue(mainriskelemscore);										
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*산사태위험도*/
				if(dataList.get(i).get("lndsldrskscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("lndsldrskscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*산림현황*/
				if(dataList.get(i).get("mststscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("mststscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*뿌리특성*/
				if(dataList.get(i).get("rootscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("rootscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*기타(선택형)*/
				if(dataList.get(i).get("etcscore").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("etcscore").toString()));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적계*/
				formulaText = "SUM(EI"+(rowNo+1)+",EN"+(rowNo+1)+",ES"+(rowNo+1)+",EX"+(rowNo+1)+",FC"+(rowNo+1)+")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번1*/
				formulaText = "H"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목1*/
				cell.setCellValue(dataList.get(i).get("jimok1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적1*/
				if(dataList.get(i).get("area1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area1").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적1*/
				if(dataList.get(i).get("incldarea1").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea1").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유1*/
				cell.setCellValue(dataList.get(i).get("own1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번2*/
				cell.setCellValue(dataList.get(i).get("jibun2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목2*/
				cell.setCellValue(dataList.get(i).get("jimok2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적2*/
				if(dataList.get(i).get("area2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area2").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적2*/
				if(dataList.get(i).get("incldarea2").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea2").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유2*/
				cell.setCellValue(dataList.get(i).get("own2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번3*/
				cell.setCellValue(dataList.get(i).get("jibun3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목3*/
				cell.setCellValue(dataList.get(i).get("jimok3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적3*/
				if(dataList.get(i).get("area3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area3").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적3*/
				if(dataList.get(i).get("incldarea3").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea3").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유3*/
				cell.setCellValue(dataList.get(i).get("own3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번4*/
				cell.setCellValue(dataList.get(i).get("jibun4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목4*/
				cell.setCellValue(dataList.get(i).get("jimok4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적4*/
				if(dataList.get(i).get("area4").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area4").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적4*/
				if(dataList.get(i).get("incldarea4").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea4").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유4*/
				cell.setCellValue(dataList.get(i).get("own4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지번5*/
				cell.setCellValue(dataList.get(i).get("jibun5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지목5*/
				cell.setCellValue(dataList.get(i).get("jimok5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*면적5*/
				if(dataList.get(i).get("area5").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("area5").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*편입면적5*/
				if(dataList.get(i).get("incldarea5").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					cell.setCellValue(Double.parseDouble(dataList.get(i).get("incldarea5").toString().replaceAll(",", "")));					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*소유5*/
				cell.setCellValue(dataList.get(i).get("own5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번1*/
				if(dataList.get(i).get("lat1D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("①");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도1*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat1D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분1*/
				if(dataList.get(i).get("lat1M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat1M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초1*/
				if(dataList.get(i).get("lat1S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat1S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도1*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon1D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분1*/
				if(dataList.get(i).get("lon1M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon1M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초1*/
				if(dataList.get(i).get("lon1S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon1S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이1*/
				cell.setCellValue(dataList.get(i).get("partclr1").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번2*/
				if(dataList.get(i).get("lat2D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("②");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도2*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat2D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분2*/
				if(dataList.get(i).get("lat2M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat2M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초2*/
				if(dataList.get(i).get("lat2S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat2S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도2*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon2D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분2*/
				if(dataList.get(i).get("lon2M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon2M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초2*/
				if(dataList.get(i).get("lon2S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon2S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이2*/
				cell.setCellValue(dataList.get(i).get("partclr2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번3*/
				if(dataList.get(i).get("lat3D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("③");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도3*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat3D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분3*/
				if(dataList.get(i).get("lat3M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat3M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초3*/
				if(dataList.get(i).get("lat3S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat3S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도3*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon3D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분3*/
				if(dataList.get(i).get("lon3M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon3M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초3*/
				if(dataList.get(i).get("lon3S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon3S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이3*/
				cell.setCellValue(dataList.get(i).get("partclr3").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번4*/
				if(dataList.get(i).get("lat4D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("④");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도4*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat4D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분4*/
				if(dataList.get(i).get("lat4M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat4M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초4*/
				if(dataList.get(i).get("lat4S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat4S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도4*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon4D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분4*/
				if(dataList.get(i).get("lon4M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon4M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초4*/
				if(dataList.get(i).get("lon4S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon4S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이4*/
				cell.setCellValue(dataList.get(i).get("partclr4").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*연번5*/
				if(dataList.get(i).get("lat5D").toString().equals("")) {
					cell.setCellValue("");					
				}else {
					cell.setCellValue("⑤");					
				}
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도도5*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lat5D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도분5*/
				if(dataList.get(i).get("lat5M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lat5M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*위도초5*/
				if(dataList.get(i).get("lat5S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lat5S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도도5*/	/*수정*/
				cell.setCellValue(dataList.get(i).get("lon5D").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도분5*/
				if(dataList.get(i).get("lon5M").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatM = Integer.parseInt(dataList.get(i).get("lon5M").toString());
					String result = String.format("%02d", formatM);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet1, rowNo, colNo++);/*경도초5*/
				if(dataList.get(i).get("lon5S").toString().equals("")) {
					cell.setCellValue("");										
				}else {
					formatS = Double.parseDouble(dataList.get(i).get("lon5S").toString());
					String result = String.format("%05.02f", formatS);
					cell.setCellValue(result);										
				}					
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이5*/
				cell.setCellValue(dataList.get(i).get("partclr5").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*장소*/
				cell.setCellValue(dataList.get(i).get("shuntplace").toString());
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet1, rowNo, colNo++);/*대위도*/
				if(dataList.get(i).get("evamapLatD").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLatD").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대위분*/
				if(dataList.get(i).get("evamapLatM").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLatM").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대위초*/
				if(dataList.get(i).get("evamapLatS").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatS = Double.parseDouble(dataList.get(i).get("evamapLatS").toString());
				    String result = String.format("%05.02f", formatS);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);

				cell = getCell(sheet1, rowNo, colNo++);/*대경도*/
				if(dataList.get(i).get("evamapLonD").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLonD").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대경분*/
				if(dataList.get(i).get("evamapLonM").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatM = Integer.parseInt(dataList.get(i).get("evamapLonM").toString());
				    String result = String.format("%02d", formatM);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle2);

				cell = getCell(sheet1, rowNo, colNo++);/*대경초*/
				if(dataList.get(i).get("evamapLonS").toString().equals("")) {
				    cell.setCellValue("");										
				}else {
				    formatS = Double.parseDouble(dataList.get(i).get("evamapLonS").toString());
				    String result = String.format("%05.02f", formatS);
				    cell.setCellValue(result);										
				}
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet1, rowNo, colNo++);/*대상지개요*/
				cell.setCellValue(dataList.get(i).get("sldsumry").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*현장결과1(값없음)*/
//				cell.setCellValue(dataList.get(i).get("sptrslt1").toString());
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*현장결과2*/
				cell.setCellValue(dataList.get(i).get("sptrslt2").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*간략한 종합의견 및 지정사유*/
				cell.setCellValue(dataList.get(i).get("smplrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*지정사유 또는 안정해석 결과 입력*/
				cell.setCellValue(dataList.get(i).get("apntrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*구역설정*/
				cell.setCellValue(dataList.get(i).get("arearslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*특이사항 및 진입여건*/
				cell.setCellValue(dataList.get(i).get("partclrrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*재해예방사업종 선정사유*/
				cell.setCellValue(dataList.get(i).get("dsstrprvrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet1, rowNo, colNo++);/*기타종합의견 주민협의 및 대체대안*/
				cell.setCellValue(dataList.get(i).get("etcrslt").toString());
				cell.setCellStyle(ValueStyle);
				
				rowNo++;			
			}
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 토석류 출력 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet22(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트

		XSSFSheet sheet2 = wb.createSheet("출력");
		sheet2.setDefaultColumnWidth(10);
		
		int rowNo=0, colNo=0;
		getCell(sheet2, rowNo, colNo).setCellValue("기번");
		getCell(sheet2, rowNo, colNo).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+1).setCellValue("시도");
		getCell(sheet2, rowNo, colNo+1).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+2).setCellValue("시군구");
		getCell(sheet2, rowNo, colNo+2).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+3).setCellValue("읍면");
		getCell(sheet2, rowNo, colNo+3).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+4).setCellValue("리동");
		getCell(sheet2, rowNo, colNo+4).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+5).setCellValue("지번");
		getCell(sheet2, rowNo, colNo+5).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+6).setCellValue("주체");
		getCell(sheet2, rowNo, colNo+6).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+7).setCellValue("소재지");
		getCell(sheet2, rowNo, colNo+7).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+8).setCellValue("위도도");
		getCell(sheet2, rowNo, colNo+8).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+9).setCellValue("위도분");
		getCell(sheet2, rowNo, colNo+9).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+10).setCellValue("위도초");
		getCell(sheet2, rowNo, colNo+10).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+11).setCellValue("경도도");
		getCell(sheet2, rowNo, colNo+11).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+12).setCellValue("경도분");
		getCell(sheet2, rowNo, colNo+12).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+13).setCellValue("경도초");
		getCell(sheet2, rowNo, colNo+13).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+14).setCellValue("위도");
		getCell(sheet2, rowNo, colNo+14).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+15).setCellValue("경도");
		getCell(sheet2, rowNo, colNo+15).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+16).setCellValue("소속");
		getCell(sheet2, rowNo, colNo+16).setCellStyle(HeaderStyleBlue);
				
		getCell(sheet2, rowNo, colNo+17).setCellValue("직급");
		getCell(sheet2, rowNo, colNo+17).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+18).setCellValue("조사자");
		getCell(sheet2, rowNo, colNo+18).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+19).setCellValue("조사일자");
		getCell(sheet2, rowNo, colNo+19).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+20).setCellValue("연락처");
		getCell(sheet2, rowNo, colNo+20).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+21).setCellValue("1토석류");
		getCell(sheet2, rowNo, colNo+21).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+22).setCellValue("정지조건");
		getCell(sheet2, rowNo, colNo+22).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+23).setCellValue("가중치");
		getCell(sheet2, rowNo, colNo+23).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+24).setCellValue("토석류량");
		getCell(sheet2, rowNo, colNo+24).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+25).setCellValue("이격거리");
		getCell(sheet2, rowNo, colNo+25).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+26).setCellValue("유역헥타");
		getCell(sheet2, rowNo, colNo+26).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+27).setCellValue("유역제곱");
		getCell(sheet2, rowNo, colNo+27).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+28).setCellValue("취약헥타");
		getCell(sheet2, rowNo, colNo+28).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+29).setCellValue("취약제곱");
		getCell(sheet2, rowNo, colNo+29).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+30).setCellValue("보호시설");
		getCell(sheet2, rowNo, colNo+30).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+31).setCellValue("보호개수");
		getCell(sheet2, rowNo, colNo+31).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+32).setCellValue("인가");
		getCell(sheet2, rowNo, colNo+32).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+33).setCellValue("인가개수");
		getCell(sheet2, rowNo, colNo+33).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+34).setCellValue("상부주요");
		getCell(sheet2, rowNo, colNo+34).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+35).setCellValue("상부인가");
		getCell(sheet2, rowNo, colNo+35).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+36).setCellValue("하부주요");
		getCell(sheet2, rowNo, colNo+36).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+37).setCellValue("하부인가");
		getCell(sheet2, rowNo, colNo+37).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+38).setCellValue("황폐발생");
		getCell(sheet2, rowNo, colNo+38).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+39).setCellValue("변곡길이");
		getCell(sheet2, rowNo, colNo+39).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+40).setCellValue("변곡고도");
		getCell(sheet2, rowNo, colNo+40).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+41).setCellValue("계류길이");
		getCell(sheet2, rowNo, colNo+41).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+42).setCellValue("평균");
		getCell(sheet2, rowNo, colNo+42).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+43).setCellValue("최저");
		getCell(sheet2, rowNo, colNo+43).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+44).setCellValue("최고");
		getCell(sheet2, rowNo, colNo+44).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+45).setCellValue("임상");
		getCell(sheet2, rowNo, colNo+45).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+46).setCellValue("밀도");
		getCell(sheet2, rowNo, colNo+46).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+47).setCellValue("경급");
		getCell(sheet2, rowNo, colNo+47).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+48).setCellValue("종점특이");
		getCell(sheet2, rowNo, colNo+48).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+49).setCellValue("시점특이");
		getCell(sheet2, rowNo, colNo+49).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+50).setCellValue("월류");
		getCell(sheet2, rowNo, colNo+50).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+51).setCellValue("계류상황");
		getCell(sheet2, rowNo, colNo+51).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+52).setCellValue("곡류상태");
		getCell(sheet2, rowNo, colNo+52).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+53).setCellValue("계류수");
		getCell(sheet2, rowNo, colNo+53).setCellStyle(HeaderStyleGreen);
	
		getCell(sheet2, rowNo, colNo+54).setCellValue("유목유무");
		getCell(sheet2, rowNo, colNo+54).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+55).setCellValue("유목길이");
		getCell(sheet2, rowNo, colNo+55).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+56).setCellValue("퇴적지");
		getCell(sheet2, rowNo, colNo+56).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+57).setCellValue("좌안붕괴");
		getCell(sheet2, rowNo, colNo+57).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+58).setCellValue("우안붕괴");
		getCell(sheet2, rowNo, colNo+58).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+59).setCellValue("위도1");
		getCell(sheet2, rowNo, colNo+59).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+60).setCellValue("경도1");
		getCell(sheet2, rowNo, colNo+60).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+61).setCellValue("위치1");
		getCell(sheet2, rowNo, colNo+61).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+62).setCellValue("고도1");
		getCell(sheet2, rowNo, colNo+62).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+63).setCellValue("토심1");
		getCell(sheet2, rowNo, colNo+63).setCellStyle(HeaderStyleYellow);

		getCell(sheet2, rowNo, colNo+64).setCellValue("폭1");
		getCell(sheet2, rowNo, colNo+64).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+65).setCellValue("경사1");
		getCell(sheet2, rowNo, colNo+65).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+66).setCellValue("암반1");
		getCell(sheet2, rowNo, colNo+66).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+67).setCellValue("전석1");
		getCell(sheet2, rowNo, colNo+67).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+68).setCellValue("자갈1");
		getCell(sheet2, rowNo, colNo+68).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+69).setCellValue("모래1");
		getCell(sheet2, rowNo, colNo+69).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+70).setCellValue("기타1");
		getCell(sheet2, rowNo, colNo+70).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+71).setCellValue("위도2");
		getCell(sheet2, rowNo, colNo+71).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+72).setCellValue("경도2");
		getCell(sheet2, rowNo, colNo+72).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+73).setCellValue("위치2");
		getCell(sheet2, rowNo, colNo+73).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+74).setCellValue("고도2");
		getCell(sheet2, rowNo, colNo+74).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+75).setCellValue("토심2");
		getCell(sheet2, rowNo, colNo+75).setCellStyle(HeaderStylePink);

		getCell(sheet2, rowNo, colNo+76).setCellValue("폭2");
		getCell(sheet2, rowNo, colNo+76).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+77).setCellValue("경사2");
		getCell(sheet2, rowNo, colNo+77).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+78).setCellValue("암반2");
		getCell(sheet2, rowNo, colNo+78).setCellStyle(HeaderStylePink);
	
		getCell(sheet2, rowNo, colNo+79).setCellValue("전석2");
		getCell(sheet2, rowNo, colNo+79).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+80).setCellValue("자갈2");
		getCell(sheet2, rowNo, colNo+80).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+81).setCellValue("모래2");
		getCell(sheet2, rowNo, colNo+81).setCellStyle(HeaderStylePink);
	
		getCell(sheet2, rowNo, colNo+82).setCellValue("기타2");
		getCell(sheet2, rowNo, colNo+82).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+83).setCellValue("위도3");
		getCell(sheet2, rowNo, colNo+83).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+84).setCellValue("경도3");
		getCell(sheet2, rowNo, colNo+84).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+85).setCellValue("위치3");
		getCell(sheet2, rowNo, colNo+85).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+86).setCellValue("고도3");
		getCell(sheet2, rowNo, colNo+86).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+87).setCellValue("토심3");
		getCell(sheet2, rowNo, colNo+87).setCellStyle(HeaderStyleBlue);

		getCell(sheet2, rowNo, colNo+88).setCellValue("폭3");
		getCell(sheet2, rowNo, colNo+88).setCellStyle(HeaderStyleBlue);
	
		getCell(sheet2, rowNo, colNo+89).setCellValue("경사3");
		getCell(sheet2, rowNo, colNo+89).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+90).setCellValue("암반3");
		getCell(sheet2, rowNo, colNo+90).setCellStyle(HeaderStyleBlue);
	
		getCell(sheet2, rowNo, colNo+91).setCellValue("전석3");
		getCell(sheet2, rowNo, colNo+91).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+92).setCellValue("자갈3");
		getCell(sheet2, rowNo, colNo+92).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+93).setCellValue("모래3");
		getCell(sheet2, rowNo, colNo+93).setCellStyle(HeaderStyleBlue);
	
		getCell(sheet2, rowNo, colNo+94).setCellValue("기타3");
		getCell(sheet2, rowNo, colNo+94).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+94).setCellValue("기타3");
		getCell(sheet2, rowNo, colNo+94).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+95).setCellValue("현장점수");
		getCell(sheet2, rowNo, colNo+95).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+96).setCellValue("안정점수");
		getCell(sheet2, rowNo, colNo+96).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+97).setCellValue("점수계");
		getCell(sheet2, rowNo, colNo+97).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+98).setCellValue("등급");
		getCell(sheet2, rowNo, colNo+98).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+99).setCellValue("결과");
		getCell(sheet2, rowNo, colNo+99).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+100).setCellValue("상하향");
		getCell(sheet2, rowNo, colNo+100).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+101).setCellValue("보정사유");
		getCell(sheet2, rowNo, colNo+101).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+102).setCellValue("사업여부");
		getCell(sheet2, rowNo, colNo+102).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+103).setCellValue("대책방안");
		getCell(sheet2, rowNo, colNo+103).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+104).setCellValue("필요공종");
		getCell(sheet2, rowNo, colNo+104).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+105).setCellValue("대피장소");
		getCell(sheet2, rowNo, colNo+105).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+106).setCellValue("현상태");
		getCell(sheet2, rowNo, colNo+106).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+107).setCellValue("비구조적");
		getCell(sheet2, rowNo, colNo+107).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+108).setCellValue("구조적");
		getCell(sheet2, rowNo, colNo+108).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+109).setCellValue("종합의견1");
		getCell(sheet2, rowNo, colNo+109).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+110).setCellValue("종합의견2");
		getCell(sheet2, rowNo, colNo+110).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+111).setCellValue("현장계");
		getCell(sheet2, rowNo, colNo+111).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+112).setCellValue("피해이력");
		getCell(sheet2, rowNo, colNo+112).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+113).setCellValue("판보호");
		getCell(sheet2, rowNo, colNo+113).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+114).setCellValue("판유역");
		getCell(sheet2, rowNo, colNo+114).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+115).setCellValue("판경사");
		getCell(sheet2, rowNo, colNo+115).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+116).setCellValue("판토심");
		getCell(sheet2, rowNo, colNo+116).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+117).setCellValue("주위험");
		getCell(sheet2, rowNo, colNo+117).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+118).setCellValue("판산사태");
		getCell(sheet2, rowNo, colNo+118).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+119).setCellValue("판산림");
		getCell(sheet2, rowNo, colNo+119).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+120).setCellValue("판뿌리");
		getCell(sheet2, rowNo, colNo+120).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+121).setCellValue("판기타");
		getCell(sheet2, rowNo, colNo+121).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+122).setCellValue("면적계");
		getCell(sheet2, rowNo, colNo+122).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+123).setCellValue("지번1");
		getCell(sheet2, rowNo, colNo+123).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+124).setCellValue("지목1");
		getCell(sheet2, rowNo, colNo+124).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+125).setCellValue("면적1");
		getCell(sheet2, rowNo, colNo+125).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+126).setCellValue("편입1");
		getCell(sheet2, rowNo, colNo+126).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+127).setCellValue("소유1");
		getCell(sheet2, rowNo, colNo+127).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+128).setCellValue("지번2");
		getCell(sheet2, rowNo, colNo+128).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+129).setCellValue("지목2");
		getCell(sheet2, rowNo, colNo+129).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+130).setCellValue("면적2");
		getCell(sheet2, rowNo, colNo+130).setCellStyle(HeaderStyleBeige);
	
		getCell(sheet2, rowNo, colNo+131).setCellValue("편입2");
		getCell(sheet2, rowNo, colNo+131).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+132).setCellValue("소유2");
		getCell(sheet2, rowNo, colNo+132).setCellStyle(HeaderStyleBeige);
		
		getCell(sheet2, rowNo, colNo+133).setCellValue("지번3");
		getCell(sheet2, rowNo, colNo+133).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+134).setCellValue("지목3");
		getCell(sheet2, rowNo, colNo+134).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+135).setCellValue("면적3");
		getCell(sheet2, rowNo, colNo+135).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+136).setCellValue("편입3");
		getCell(sheet2, rowNo, colNo+136).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+137).setCellValue("소유3");
		getCell(sheet2, rowNo, colNo+137).setCellStyle(HeaderStyleSky);
		
		getCell(sheet2, rowNo, colNo+138).setCellValue("지번4");
		getCell(sheet2, rowNo, colNo+138).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+139).setCellValue("지목4");
		getCell(sheet2, rowNo, colNo+139).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+140).setCellValue("면적4");
		getCell(sheet2, rowNo, colNo+140).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+141).setCellValue("편입4");
		getCell(sheet2, rowNo, colNo+141).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+142).setCellValue("소유4");
		getCell(sheet2, rowNo, colNo+142).setCellStyle(HeaderStyleGray);
		
		getCell(sheet2, rowNo, colNo+143).setCellValue("지번5");
		getCell(sheet2, rowNo, colNo+143).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+144).setCellValue("지목5");
		getCell(sheet2, rowNo, colNo+144).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+145).setCellValue("면적5");
		getCell(sheet2, rowNo, colNo+145).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+146).setCellValue("편입5");
		getCell(sheet2, rowNo, colNo+146).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+147).setCellValue("소유5");
		getCell(sheet2, rowNo, colNo+147).setCellStyle(HeaderStylePink);
		
		getCell(sheet2, rowNo, colNo+148).setCellValue("연번5");
		getCell(sheet2, rowNo, colNo+148).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+149).setCellValue("위도5");
		getCell(sheet2, rowNo, colNo+149).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+150).setCellValue("경도5");
		getCell(sheet2, rowNo, colNo+150).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+151).setCellValue("특이5");
		getCell(sheet2, rowNo, colNo+151).setCellStyle(HeaderStyleOrange);
		
		getCell(sheet2, rowNo, colNo+152).setCellValue("연번6");
		getCell(sheet2, rowNo, colNo+152).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+153).setCellValue("위도6");
		getCell(sheet2, rowNo, colNo+153).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+154).setCellValue("경도6");
		getCell(sheet2, rowNo, colNo+154).setCellStyle(HeaderStyleYellow);
	
		getCell(sheet2, rowNo, colNo+155).setCellValue("특이6");
		getCell(sheet2, rowNo, colNo+155).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+156).setCellValue("연번7");
		getCell(sheet2, rowNo, colNo+156).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+157).setCellValue("위도7");
		getCell(sheet2, rowNo, colNo+157).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+158).setCellValue("경도7");
		getCell(sheet2, rowNo, colNo+158).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet2, rowNo, colNo+159).setCellValue("특이7");
		getCell(sheet2, rowNo, colNo+159).setCellStyle(HeaderStyleGreen);
	
		getCell(sheet2, rowNo, colNo+160).setCellValue("연번8");
		getCell(sheet2, rowNo, colNo+160).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+161).setCellValue("위도8");
		getCell(sheet2, rowNo, colNo+161).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+162).setCellValue("경도8");
		getCell(sheet2, rowNo, colNo+162).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+163).setCellValue("특이8");
		getCell(sheet2, rowNo, colNo+163).setCellStyle(HeaderStyleGreen2);
		
		getCell(sheet2, rowNo, colNo+164).setCellValue("연번9");
		getCell(sheet2, rowNo, colNo+164).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+165).setCellValue("위도9");
		getCell(sheet2, rowNo, colNo+165).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+166).setCellValue("경도9");
		getCell(sheet2, rowNo, colNo+166).setCellStyle(HeaderStyleBlue);
	
		getCell(sheet2, rowNo, colNo+167).setCellValue("특이9");
		getCell(sheet2, rowNo, colNo+167).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet2, rowNo, colNo+168).setCellValue("대피장소");
		getCell(sheet2, rowNo, colNo+168).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+169).setCellValue("대위도");
		getCell(sheet2, rowNo, colNo+169).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+170).setCellValue("대경도");
		getCell(sheet2, rowNo, colNo+170).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet2, rowNo, colNo+171).setCellValue("텍개요");
		getCell(sheet2, rowNo, colNo+171).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+172).setCellValue("텍결과1");
		getCell(sheet2, rowNo, colNo+172).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+173).setCellValue("텍결과2");
		getCell(sheet2, rowNo, colNo+173).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+174).setCellValue("텍사유1");
		getCell(sheet2, rowNo, colNo+174).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+175).setCellValue("텍사유2");
		getCell(sheet2, rowNo, colNo+175).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+176).setCellValue("텍구역");
		getCell(sheet2, rowNo, colNo+176).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+177).setCellValue("텍특이");
		getCell(sheet2, rowNo, colNo+177).setCellStyle(HeaderStyleGreen3);
	
		getCell(sheet2, rowNo, colNo+178).setCellValue("텍사업");
		getCell(sheet2, rowNo, colNo+178).setCellStyle(HeaderStyleGreen3);
		
		getCell(sheet2, rowNo, colNo+179).setCellValue("텍주민");
		getCell(sheet2, rowNo, colNo+179).setCellStyle(HeaderStyleGreen3);
		
		rowNo=1;
		String formulaText = "";
		
		if(dataList.size() > 0) {
			for (int i=0; i<dataList.size(); i++) {				
				colNo=0;
				cell = getCell(sheet2, rowNo, colNo++);/*기번*/
				formulaText = "입력값!A"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*시도*/
				formulaText = "입력값!D"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*시군구*/
				formulaText = "입력값!E"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*읍면*/
				formulaText = "입력값!F"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*리동*/
				formulaText = "입력값!G"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번*/
				formulaText = "입력값!H"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*주체*/
				formulaText = "입력값!I"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소재지*/
				formulaText = "B"+(rowNo+1)+"&\" \"&C"+(rowNo+1)+"&\" \"&D"+(rowNo+1)+"&\" \"&E"+(rowNo+1)+"&\" \"&F"+(rowNo+1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도도*/
				formulaText = "입력값!J"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도분*/
				formulaText = "입력값!K"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도초*/
				formulaText = "입력값!L"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도도*/
				formulaText = "입력값!M"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도분*/
				formulaText = "입력값!N"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle2);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도초*/
				formulaText = "입력값!O"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle3);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도*/
				formulaText = "입력값!J"+(rowNo+4)+"&\"°\"&\" \"&입력값!K"+(rowNo+4)+"&\"′\"&\" \"&입력값!L"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도*/
				formulaText = "입력값!M"+(rowNo+4)+"&\"°\"&\" \"&입력값!N"+(rowNo+4)+"&\"′\"&\" \"&입력값!O"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소속*/
				formulaText = "입력값!P"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*직급*/
				formulaText = "입력값!Q"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*조사자*/
				formulaText = "입력값!R"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*조사일자*/
				formulaText = "입력값!S"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연락처*/
				formulaText = "입력값!T"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*1토석류*/
				formulaText = "입력값!U"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*정지조건*/
				formulaText = "입력값!V"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*가중치*/
				formulaText = "입력값!W"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토석류량*/
				formulaText = "입력값!Y"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*이격거리*/
				formulaText = "입력값!Z"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*유역헥타*/
				formulaText = "입력값!AA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*유역제곱*/
				formulaText = "입력값!AB"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*취약헥타*/
				formulaText = "입력값!AC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*취약제곱*/
				formulaText = "입력값!AD"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*보호시설*/
				formulaText = "입력값!AE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*보호개수*/
				formulaText = "IF(입력값!AF"+(rowNo+4)+"=0,0,IF(입력값!AF"+(rowNo+4)+"=\"-\",\"0\",입력값!AF"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*인가*/
				formulaText = "입력값!AG"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*인가개수*/
				formulaText = "IF(입력값!AH"+(rowNo+4)+"=0,0,IF(입력값!AH"+(rowNo+4)+"=\"-\",\"0\",입력값!AH"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*상부주요*/
				formulaText = "입력값!AI"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*상부인가*/
				formulaText = "IF(입력값!AJ"+(rowNo+4)+"=0,0,IF(입력값!AJ"+(rowNo+4)+"=\"-\",\"0\",입력값!AJ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*하부주요*/
				formulaText = "입력값!AK"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*하부인가*/
				formulaText = "IF(입력값!AL"+(rowNo+4)+"=0,0,IF(입력값!AL"+(rowNo+4)+"=\"-\",\"0\",입력값!AL"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*황폐발생*/
				formulaText = "IF(입력값!AM"+(rowNo+4)+"=1,\"산사태위험지 1등급 유역\",IF(입력값!AM"+(rowNo+4)+"=2,\"산사태위험지 2등급 유역\",IF(입력값!AM"+(rowNo+4)+"=2.5,\"산사태위험지 2등급 50% 이하 유역\",IF(입력값!AM"+(rowNo+4)+"=3,\"산사태위험지 3,4등급 유역\",\"-\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*변곡길이*/
				formulaText = "입력값!AN"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*변곡고도*/
				formulaText = "입력값!AO"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*계류길이*/
				formulaText = "입력값!AP"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*평균*/
				formulaText = "입력값!AQ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*최저*/
				formulaText = "입력값!AR"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*최고*/
				formulaText = "입력값!AS"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*임상*/
				formulaText = "IF(입력값!AT"+(rowNo+4)+"=\"침\",\"침엽수림\",IF(입력값!AT"+(rowNo+4)+"=\"활\",\"활엽수림\",IF(입력값!AT"+(rowNo+4)+"=\"혼\",\"혼효림\",IF(입력값!AT"+(rowNo+4)+"=\"무\",\"무입목지\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet2, rowNo, colNo++);/*임상밀도*/
				formulaText = "입력값!AU"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet2, rowNo, colNo++);/*임상경급*/
				formulaText = "IF(입력값!AV"+(rowNo+4)+"=\"치\",\"치수림\",IF(입력값!AV"+(rowNo+4)+"=\"소\",\"소경급\",IF(입력값!AV"+(rowNo+4)+"=\"중\",\"중경급\",IF(입력값!AV"+(rowNo+4)+"=\"대\",\"대경급\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*종점특이*/
				formulaText = "입력값!AW"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*시점특이*/
				formulaText = "입력값!AX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*월류*/
				formulaText = "입력값!AY"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*계류상황*/
				formulaText = "IF(입력값!AZ"+(rowNo+4)+"=\"협착\",\"협착부\",IF(입력값!AZ"+(rowNo+4)+"=\"확폭\",\"확폭부\",IF(입력값!AZ"+(rowNo+4)+"=\"인공\",\"인공계류\",\"-\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*곡류상태*/
				formulaText = "입력값!BA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*계류수*/
				formulaText = "입력값!BB"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*유목유무*/
				formulaText = "입력값!BC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*유목길이*/
				formulaText = "입력값!BD"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*퇴적지*/
				formulaText = "입력값!BE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*좌안붕괴*/
				formulaText = "입력값!BF"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*우안붕괴*/
				formulaText = "입력값!BG"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도1*/
				formulaText = "O"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도1*/
				formulaText = "P"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위치1*/
				formulaText = "입력값!BN"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*고도1*/
				formulaText = "입력값!BO"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토심1*/
				formulaText = "입력값!BP"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*폭1*/
				formulaText = "입력값!BQ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경사1*/
				formulaText = "입력값!BR"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암반1*/
				formulaText = "입력값!BT"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*전석1*/
				formulaText = "입력값!BU"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*자갈1*/
				formulaText = "입력값!BV"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*모래1*/
				formulaText = "입력값!BW"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*기타1*/
				formulaText = "입력값!BX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도2*/
				formulaText = "입력값!BY"+(rowNo+4)+"&\"°\"&\" \"&입력값!BZ"+(rowNo+4)+"&\"′\"&\" \"&입력값!CA"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도2*/
				formulaText = "입력값!CB"+(rowNo+4)+"&\"°\"&\" \"&입력값!CC"+(rowNo+4)+"&\"′\"&\" \"&입력값!CD"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위치2*/
				formulaText = "입력값!CE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*고도2*/
				formulaText = "입력값!CF"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토심2*/
				formulaText = "입력값!CG"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*폭2*/
				formulaText = "입력값!CH"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경사2*/
				formulaText = "입력값!CI"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암반2*/
				formulaText = "입력값!CK"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*전석2*/
				formulaText = "입력값!CL"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*자갈2*/
				formulaText = "입력값!CM"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*모래2*/
				formulaText = "입력값!CN"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*기타2*/
				formulaText = "입력값!CO"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도3*/
				formulaText = "입력값!CP"+(rowNo+4)+"&\"°\"&\" \"&입력값!CQ"+(rowNo+4)+"&\"′\"&\" \"&입력값!CR"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도3*/
				formulaText = "입력값!CS"+(rowNo+4)+"&\"°\"&\" \"&입력값!CT"+(rowNo+4)+"&\"′\"&\" \"&입력값!CU"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위치3*/
				formulaText = "입력값!CV"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*고도3*/
				formulaText = "입력값!CW"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*토심3*/
				formulaText = "입력값!CX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*폭3*/
				formulaText = "입력값!CY"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경사3*/
				formulaText = "입력값!CZ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*암반3*/
				formulaText = "입력값!DB"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*전석3*/
				formulaText = "입력값!DC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*자갈3*/
				formulaText = "입력값!DD"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*모래3*/
				formulaText = "입력값!DE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*기타3*/
				formulaText = "입력값!DF"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*현장점수*/
				formulaText = "입력값!DG"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*안정점수*/
				formulaText = "입력값!DH"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*점수계*/
				formulaText = "입력값!DI"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*등급*/
				formulaText = "IF(입력값!DJ"+(rowNo+4)+"=\"A등급\",\"A\",IF(입력값!DJ"+(rowNo+4)+"=\"B등급\",\"B\",IF(입력값!DJ"+(rowNo+4)+"=\"C등급\",\"C\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*결과*/
				formulaText = "입력값!DK"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*상하향*/
				formulaText = "IF(입력값!DL"+(rowNo+4)+"=\"상\",\"상향\",IF(입력값!DL"+(rowNo+4)+"=\"-\",\"-\",IF(입력값!DL"+(rowNo+4)+"=0,\"-\",IF(입력값!DL"+(rowNo+4)+"=\"하\",\"하향\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*보정사유*/
				formulaText = "IF(입력값!DM"+(rowNo+4)+"=\"-\",\"-\",IF(입력값!DM"+(rowNo+4)+"=0,\" \",입력값!DM"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*사업여부*/
				formulaText = "입력값!DN"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대책방안*/
				formulaText = "입력값!DO"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*필요공종*/
				formulaText = "입력값!DP"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대피장소*/
				formulaText = "입력값!DQ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*현상태*/
				formulaText = "IF(입력값!DR"+(rowNo+4)+"=\"현 상태 유지\",\"○\",IF(입력값!DR"+(rowNo+4)+"=\"-\",\"\",IF(입력값!DR"+(rowNo+4)+"=0,\"\",IF(입력값!DR"+(rowNo+4)+"=\"비구조적(대피체계구축필요)\",\"\",IF(입력값!DR"+(rowNo+4)+"=\"구조적+비구조적(적극적인 관리필요)\",\"\")))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*비구조적*/
				formulaText = "IF(입력값!DR"+(rowNo+4)+"=\"비구조적(대피체계구축필요)\",\"○\",IF(입력값!DR"+(rowNo+4)+"=\"-\",\"\",IF(입력값!DR"+(rowNo+4)+"=0,\"\",IF(입력값!DR"+(rowNo+4)+"=\"현 상태 유지\",\"\",IF(입력값!DR"+(rowNo+4)+"=\"구조적+비구조적(적극적인 관리필요)\",\"\")))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*구조적*/
				formulaText = "IF(입력값!DR"+(rowNo+4)+"=\"구조적+비구조적(적극적인 관리필요)\",\"○\",IF(입력값!DR"+(rowNo+4)+"=\"-\",\"\",IF(입력값!DR"+(rowNo+4)+"=0,\"\",IF(입력값!DR"+(rowNo+4)+"=\"현 상태 유지\",\"\",IF(입력값!DR"+(rowNo+4)+"=\"비구조적(대피체계구축필요)\",\"\")))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*종합의견1*/
				formulaText = "IF(입력값!DS"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DS"+(rowNo+4)+"=0,\" \",입력값!DS"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*종합의견2*/
				formulaText = "IF(입력값!DT"+(rowNo+4)+"=\"-\",\" \",IF(입력값!DT"+(rowNo+4)+"=0,\" \",입력값!DT"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*현장계*/
				formulaText = "입력값!DU"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*피해이력*/
				formulaText = "입력값!DV"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판보호*/
				formulaText = "입력값!DW"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판유역*/
				formulaText = "입력값!DX"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판경사*/
				formulaText = "입력값!DY"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판토심*/
				formulaText = "입력값!DZ"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*주위험*/
				formulaText = "입력값!EA"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판산사태*/
				formulaText = "입력값!EB"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판산림*/
				formulaText = "입력값!EC"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판뿌리*/
				formulaText = "입력값!ED"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*판기타*/
				formulaText = "입력값!EE"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적계*/
				formulaText = "입력값!EF"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번1*/
				formulaText = "IF(입력값!EG"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EG"+(rowNo+4)+"=0,\" \",입력값!EG"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목1*/
				formulaText = "IF(입력값!EH"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EH"+(rowNo+4)+"=0,\" \",입력값!EH"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적1*/
				formulaText = "IF(입력값!EI"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EI"+(rowNo+4)+"=0,\" \",입력값!EI"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입1*/
				formulaText = "IF(입력값!EJ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EJ"+(rowNo+4)+"=0,\" \",입력값!EJ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유1*/
				formulaText = "IF(입력값!EK"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EK"+(rowNo+4)+"=0,\" \",입력값!EK"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번2*/
				formulaText = "IF(입력값!EL"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EL"+(rowNo+4)+"=0,\" \",입력값!EL"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목2*/
				formulaText = "IF(입력값!EM"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EM"+(rowNo+4)+"=0,\" \",입력값!EM"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적2*/
				formulaText = "IF(입력값!EN"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EN"+(rowNo+4)+"=0,\" \",입력값!EN"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입2*/
				formulaText = "IF(입력값!EO"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EO"+(rowNo+4)+"=0,\" \",입력값!EO"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유2*/
				formulaText = "IF(입력값!EP"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EP"+(rowNo+4)+"=0,\" \",입력값!EP"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번3*/
				formulaText = "IF(입력값!EQ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EQ"+(rowNo+4)+"=0,\" \",입력값!EQ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목3*/
				formulaText = "IF(입력값!ER"+(rowNo+4)+"=\"-\",\" \",IF(입력값!ER"+(rowNo+4)+"=0,\" \",입력값!ER"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적3*/
				formulaText = "IF(입력값!ES"+(rowNo+4)+"=\"-\",\" \",IF(입력값!ES"+(rowNo+4)+"=0,\" \",입력값!ES"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입3*/
				formulaText = "IF(입력값!ET"+(rowNo+4)+"=\"-\",\" \",IF(입력값!ET"+(rowNo+4)+"=0,\" \",입력값!ET"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유3*/
				formulaText = "IF(입력값!EU"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EU"+(rowNo+4)+"=0,\" \",입력값!EU"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번4*/
				formulaText = "IF(입력값!EV"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EV"+(rowNo+4)+"=0,\" \",입력값!EV"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목4*/
				formulaText = "IF(입력값!EW"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EW"+(rowNo+4)+"=0,\" \",입력값!EW"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적4*/
				formulaText = "IF(입력값!EX"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EX"+(rowNo+4)+"=0,\" \",입력값!EX"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입4*/
				formulaText = "IF(입력값!EY"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EY"+(rowNo+4)+"=0,\" \",입력값!EY"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유4*/
				formulaText = "IF(입력값!EZ"+(rowNo+4)+"=\"-\",\" \",IF(입력값!EZ"+(rowNo+4)+"=0,\" \",입력값!EZ"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지번5*/
				formulaText = "IF(입력값!FA"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FA"+(rowNo+4)+"=0,\" \",입력값!FA"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*지목5*/
				formulaText = "IF(입력값!FB"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FB"+(rowNo+4)+"=0,\" \",입력값!FB"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*면적5*/
				formulaText = "IF(입력값!FC"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FC"+(rowNo+4)+"=0,\" \",입력값!FC"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*편입5*/
				formulaText = "IF(입력값!FD"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FD"+(rowNo+4)+"=0,\" \",입력값!FD"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*소유5*/
				formulaText = "IF(입력값!FE"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FE"+(rowNo+4)+"=0,\" \",입력값!FE"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번5*/
				formulaText = "IF(입력값!FF"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FF"+(rowNo+4)+"=0,\" \",입력값!FF"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
			
				cell = getCell(sheet2, rowNo, colNo++);/*위도5*/
				formulaText = "입력값!FG"+(rowNo+4)+"&\"°\"&\" \"&입력값!FH"+(rowNo+4)+"&\"′\"&\" \"&입력값!FI"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도5*/
				formulaText = "입력값!FJ"+(rowNo+4)+"&\"°\"&\" \"&입력값!FK"+(rowNo+4)+"&\"′\"&\" \"&입력값!FL"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이5*/
				formulaText = "IF(입력값!FM"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FM"+(rowNo+4)+"=0,\" \",입력값!FM"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번6*/
				formulaText = "IF(입력값!FN"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FN"+(rowNo+4)+"=0,\" \",입력값!FN"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도6*/
				formulaText = "입력값!FO"+(rowNo+4)+"&\"°\"&\" \"&입력값!FP"+(rowNo+4)+"&\"′\"&\" \"&입력값!FQ"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도6*/
				formulaText = "입력값!FR"+(rowNo+4)+"&\"°\"&\" \"&입력값!FS"+(rowNo+4)+"&\"′\"&\" \"&입력값!FT"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이6*/
				formulaText = "IF(입력값!FU"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FU"+(rowNo+4)+"=0,\" \",입력값!FU"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번7*/
				formulaText = "IF(입력값!FV"+(rowNo+4)+"=\"-\",\" \",IF(입력값!FV"+(rowNo+4)+"=0,\" \",입력값!FV"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도7*/
				formulaText = "입력값!FW"+(rowNo+4)+"&\"°\"&\" \"&입력값!FX"+(rowNo+4)+"&\"′\"&\" \"&입력값!FY"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도7*/
				formulaText = "입력값!FZ"+(rowNo+4)+"&\"°\"&\" \"&입력값!GA"+(rowNo+4)+"&\"′\"&\" \"&입력값!GB"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이7*/
				formulaText = "IF(입력값!GC"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GC"+(rowNo+4)+"=0,\" \",입력값!GC"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
			
				cell = getCell(sheet2, rowNo, colNo++);/*연번8*/
				formulaText = "IF(입력값!GD"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GD"+(rowNo+4)+"=0,\" \",입력값!GD"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도8*/
				formulaText = "입력값!GE"+(rowNo+4)+"&\"°\"&\" \"&입력값!GF"+(rowNo+4)+"&\"′\"&\" \"&입력값!GG"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도8*/
				formulaText = "입력값!GH"+(rowNo+4)+"&\"°\"&\" \"&입력값!GI"+(rowNo+4)+"&\"′\"&\" \"&입력값!GJ"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이8*/
				formulaText = "IF(입력값!GK"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GK"+(rowNo+4)+"=0,\" \",입력값!GK"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*연번9*/
				formulaText = "IF(입력값!GL"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GL"+(rowNo+4)+"=0,\" \",입력값!GL"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*위도9*/
				formulaText = "입력값!GM"+(rowNo+4)+"&\"°\"&\" \"&입력값!GN"+(rowNo+4)+"&\"′\"&\" \"&입력값!GO"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*경도9*/
				formulaText = "입력값!GP"+(rowNo+4)+"&\"°\"&\" \"&입력값!GQ"+(rowNo+4)+"&\"′\"&\" \"&입력값!GR"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*특이9*/
				formulaText = "IF(입력값!GS"+(rowNo+4)+"=\"-\",\" \",IF(입력값!GS"+(rowNo+4)+"=0,\" \",입력값!GS"+(rowNo+4)+"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대피장소*/
				formulaText = "입력값!GT"+(rowNo+4);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대위도*/
				formulaText = "입력값!GU"+(rowNo+4)+"&\"°\"&\" \"&입력값!GV"+(rowNo+4)+"&\"′\"&\" \"&입력값!GW"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*대경도*/
				formulaText = "입력값!GX"+(rowNo+4)+"&\"°\"&\" \"&입력값!GY"+(rowNo+4)+"&\"′\"&\" \"&입력값!GZ"+(rowNo+4)+"&\"″\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍개요*/
				formulaText = "IF(입력값!HA"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HA"+(rowNo+4)+"=\"\",\"\",IF(입력값!HA"+(rowNo+4)+"=0,\" \",입력값!HA"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍결과1*/
				formulaText = "IF(입력값!HB"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HB"+(rowNo+4)+"=\"\",\"\",IF(입력값!HB"+(rowNo+4)+"=0,\" \",입력값!HB"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍결과2*/
				formulaText = "IF(입력값!HC"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HC"+(rowNo+4)+"=\"\",\"\",IF(입력값!HC"+(rowNo+4)+"=0,\" \",입력값!HC"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍사유1*/
				formulaText = "IF(입력값!HD"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HD"+(rowNo+4)+"=\"\",\"\",IF(입력값!HD"+(rowNo+4)+"=0,\" \",입력값!HD"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍사유2*/
				formulaText = "IF(입력값!HE"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HE"+(rowNo+4)+"=\"\",\"\",IF(입력값!HE"+(rowNo+4)+"=0,\" \",입력값!HE"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍구역*/
				formulaText = "IF(입력값!HF"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HF"+(rowNo+4)+"=\"\",\"\",IF(입력값!HF"+(rowNo+4)+"=0,\" \",입력값!HF"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍특이*/
				formulaText = "IF(입력값!HG"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HG"+(rowNo+4)+"=\"\",\"\",IF(입력값!HG"+(rowNo+4)+"=0,\" \",입력값!HG"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍사업*/
				formulaText = "IF(입력값!HH"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HH"+(rowNo+4)+"=\"\",\"\",IF(입력값!HH"+(rowNo+4)+"=0,\" \",입력값!HH"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet2, rowNo, colNo++);/*텍주민*/
				formulaText = "IF(입력값!HI"+(rowNo+4)+"=\"-\",\" \",IF(입력값!HI"+(rowNo+4)+"=\"\",\"\",IF(입력값!HI"+(rowNo+4)+"=0,\" \",입력값!HI"+(rowNo+4)+")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				rowNo++;
			}
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 토석류 취약지역관리 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet23(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		String[] legaldongcdList =  (String[]) dataMap.get("legaldongcdList"); // 법정동코드가 담긴 리스트를
		String[] mnagncdList =  (String[]) dataMap.get("mnagncdList"); // 산사태관리기관코드가 담긴 리스트를

		XSSFSheet sheet3 = wb.createSheet("취약지역관리");
		sheet3.setDefaultColumnWidth(17);
		sheet3.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet3, rowNo, colNo).setCellValue("이동시 삭제");
		getCell(sheet3, rowNo, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo, colNo+1).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+1).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+2).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+2).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+3).setCellValue("모두 WEAK021 사용");
		getCell(sheet3, rowNo, colNo+3).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet3, rowNo, colNo+4).setCellValue("모두 FLDABS2022 사용");
		getCell(sheet3, rowNo, colNo+4).setCellStyle(HeaderStyleGreen);
		
		getCell(sheet3, rowNo, colNo+5).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+5).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+6).setCellValue("미입력");
		getCell(sheet3, rowNo, colNo+6).setCellStyle(HeaderStyleGray);
		
		getCell(sheet3, rowNo, colNo+7).setCellValue("자동입력");
		getCell(sheet3, rowNo, colNo+7).setCellStyle(HeaderStyleBlue);
		
		getCell(sheet3, rowNo, colNo+8).setCellValue("미입력");
		getCell(sheet3, rowNo, colNo+8).setCellStyle(HeaderStyleGray);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo, colNo+9).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+9).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo, colNo+10).setCellValue("미입력");
		getCell(sheet3, rowNo, colNo+10).setCellStyle(HeaderStyleGray);
		
		getCell(sheet3, rowNo, colNo+11).setCellValue("직접입력");
		getCell(sheet3, rowNo, colNo+11).setCellStyle(HeaderStylePink);
		
		getCell(sheet3, rowNo+1, colNo).setCellValue("기번");
		getCell(sheet3, rowNo+1, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+1).setCellValue("시선IT 문의");
		getCell(sheet3, rowNo+1, colNo+1).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+2).setCellValue("법정동코드 검색 입력");
		getCell(sheet3, rowNo+1, colNo+2).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+3).setCellValue("분류코드 참조");
		getCell(sheet3, rowNo+1, colNo+3).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+4).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+4).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+5).setCellValue("협회ID");
		getCell(sheet3, rowNo+1, colNo+5).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+6).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+6).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+7).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+7).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+8).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+8).setCellStyle(HeaderStyle);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+1, colNo+9).setCellValue("분류코드 참조");
		getCell(sheet3, rowNo+1, colNo+9).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+10).setCellValue("");
		getCell(sheet3, rowNo+1, colNo+10).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+1, colNo+11).setCellValue("분류코드 참조");
		getCell(sheet3, rowNo+1, colNo+11).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+2, colNo).setCellValue("");
		getCell(sheet3, rowNo+2, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+2, colNo+1).setCellValue("업체에서 ID부여");
		getCell(sheet3, rowNo+2, colNo+1).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+2).setCellValue("법정동코드 10자리");
		getCell(sheet3, rowNo+2, colNo+2).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+3).setCellValue("최초, 변경 등 <분류참조>");
		getCell(sheet3, rowNo+2, colNo+3).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+4).setCellValue("");
		getCell(sheet3, rowNo+2, colNo+4).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+5).setCellValue("협회ID");
		getCell(sheet3, rowNo+2, colNo+5).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+6).setCellValue("공백");
		getCell(sheet3, rowNo+2, colNo+6).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+7).setCellValue("날짜8자리");
		getCell(sheet3, rowNo+2, colNo+7).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+8).setCellValue("공백");
		getCell(sheet3, rowNo+2, colNo+8).setCellStyle(HeaderStyleYellow);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+2, colNo+9).setCellValue("관리기관코드 <분류참조>");
		getCell(sheet3, rowNo+2, colNo+9).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+10).setCellValue("공백");
		getCell(sheet3, rowNo+2, colNo+10).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+2, colNo+11).setCellValue("관리기관코드 <분류참조>");
		getCell(sheet3, rowNo+2, colNo+11).setCellStyle(HeaderStyleYellow);
		
		getCell(sheet3, rowNo+3, colNo).setCellValue("");
		getCell(sheet3, rowNo+3, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+3, colNo+1).setCellValue("취약지역조사ID");
		getCell(sheet3, rowNo+3, colNo+1).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+2).setCellValue("취약지역조사지역코드");
		getCell(sheet3, rowNo+3, colNo+2).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+3).setCellValue("취약지역조사상태코드");
		getCell(sheet3, rowNo+3, colNo+3).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+4).setCellValue("취약지역조사대장구분코드");
		getCell(sheet3, rowNo+3, colNo+4).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+5).setCellValue("등록자ID");
		getCell(sheet3, rowNo+3, colNo+5).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+6).setCellValue("수정자ID");
		getCell(sheet3, rowNo+3, colNo+6).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+7).setCellValue("최초등록일");
		getCell(sheet3, rowNo+3, colNo+7).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+8).setCellValue("최종수정일");
		getCell(sheet3, rowNo+3, colNo+8).setCellStyle(HeaderStylePink2);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+3, colNo+9).setCellValue("산사태시행청코드");
		getCell(sheet3, rowNo+3, colNo+9).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+10).setCellValue("취약지역야장ID");
		getCell(sheet3, rowNo+3, colNo+10).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+3, colNo+11).setCellValue("산사태관리주체코드");
		getCell(sheet3, rowNo+3, colNo+11).setCellStyle(HeaderStylePink2);
		
		getCell(sheet3, rowNo+4, colNo).setCellValue("");
		getCell(sheet3, rowNo+4, colNo).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+1).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet3, rowNo+4, colNo+1).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+2).setCellValue("VNARA_EXMNN_ARCD");
		getCell(sheet3, rowNo+4, colNo+2).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+3).setCellValue("VNARA_EXMNN_STCD");
		getCell(sheet3, rowNo+4, colNo+3).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+4).setCellValue("VNARA_EXMNN_RGSTR_TPCD");
		getCell(sheet3, rowNo+4, colNo+4).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+5).setCellValue("RGTER_ID");
		getCell(sheet3, rowNo+4, colNo+5).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+6).setCellValue("UPUSR_ID");
		getCell(sheet3, rowNo+4, colNo+6).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+7).setCellValue("FRST_RGDT");
		getCell(sheet3, rowNo+4, colNo+7).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+8).setCellValue("LAST_UPDDT");
		getCell(sheet3, rowNo+4, colNo+8).setCellStyle(HeaderStyle);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		
		getCell(sheet3, rowNo+4, colNo+9).setCellValue("LNDSL_MNAGN_CD");
		getCell(sheet3, rowNo+4, colNo+9).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+10).setCellValue("VNARA_FLNT_ID");
		getCell(sheet3, rowNo+4, colNo+10).setCellStyle(HeaderStyle);
		
		getCell(sheet3, rowNo+4, colNo+11).setCellValue("VNARA_MNGME_CD");
		getCell(sheet3, rowNo+4, colNo+11).setCellStyle(HeaderStyle);
		
		rowNo=5;
		String formulaText = "";

		if(dataList.size() > 0) {

			//hblee(토석류 취약지역 관리)
			for (int i=0; i<dataList.size(); i++) {
				colNo=0;
				
				cell = getCell(sheet3, rowNo, colNo++);/*기번*/
				formulaText = "입력값!A"+(rowNo);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사ID*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사지역코드*/
				if(legaldongcdList[i] != null && legaldongcdList[i].toString().length() > 0) {
					cell.setCellValue(legaldongcdList[i].toString());					
				}else {					
					cell.setCellValue("");					
				}
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사상태코드*/
				cell.setCellValue("WEAK021");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*취약지역조사대장구분코드*/
				formulaText = "\"FLDABS\"&LEFT(SUBSTITUTE(입력값!S"+(rowNo)+",\"-\",\"\"),4)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*등록자ID*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*수정자ID*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*최초등록일*/
				formulaText = "LEFT(SUBSTITUTE(입력값!S"+(rowNo)+",\". \",\"\"),8)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*최종수정일*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*산사태시행청코드*/
				if(mnagncdList[i] != null && mnagncdList[i].toString().length() > 0) {
					cell.setCellValue(mnagncdList[i].toString());					
				}else {					
					cell.setCellValue("");					
				}
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet3, rowNo, colNo++);/*취약지역야장ID*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet3, rowNo, colNo++);/*산사태관리주체코드*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				rowNo++;
			}
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 토석류 취약지역조사야장 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet24(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		
		XSSFSheet sheet4 = wb.createSheet("취약지역조사야장");
		sheet4.setDefaultColumnWidth(17);
		sheet4.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet4, rowNo, colNo).setCellValue("이동시 삭제");
		getCell(sheet4, rowNo, colNo).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo, colNo+1).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+1).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+2).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+2).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+3).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+3).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+4).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+4).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+5).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+5).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+6).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+6).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+7).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+7).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+8).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+8).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+9).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+9).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+10).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+10).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+11).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+11).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+12).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+12).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+13).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+13).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+14).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+14).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+15).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+15).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+16).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+16).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+17).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+17).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+18).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+18).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+19).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+19).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+20).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+20).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+21).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+21).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+22).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+22).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+23).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+24).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+24).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+25).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+25).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+26).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+26).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+27).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+27).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+28).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+28).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+29).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+29).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+30).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+30).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+31).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+31).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+32).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+32).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+33).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+33).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+34).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+34).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+35).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+35).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+36).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+36).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+37).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+37).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+38).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+38).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+39).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+39).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+40).setCellValue("자동입력");   
		getCell(sheet4, rowNo, colNo+40).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+41).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+41).setCellStyle(HeaderStyleGray);   
		getCell(sheet4, rowNo, colNo+42).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+42).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+43).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+43).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+44).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+44).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+45).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+45).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+46).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+46).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+47).setCellValue("직접입력");
		getCell(sheet4, rowNo, colNo+47).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+48).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+48).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+49).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+49).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+50).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+50).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+51).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+51).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+52).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+52).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+53).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+53).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+54).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+54).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+55).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+55).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+56).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+56).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+57).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+57).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+58).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+58).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+59).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+59).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+60).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+60).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+61).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+61).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+62).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+62).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+63).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+63).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+64).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+64).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+65).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+65).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+66).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+66).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+67).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+67).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+68).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+68).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+69).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+69).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+70).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+70).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+71).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+71).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+72).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+72).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+73).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+73).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+74).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+74).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+75).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+75).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+76).setCellValue("직접입력 / N Y");
		getCell(sheet4, rowNo, colNo+76).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+77).setCellValue("직접입력 / N Y");
		getCell(sheet4, rowNo, colNo+77).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+78).setCellValue("직접입력 / N Y");
		getCell(sheet4, rowNo, colNo+78).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+79).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+79).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+80).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+80).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+81).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+81).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+82).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+82).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+83).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+83).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+84).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+84).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+85).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+85).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+86).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+86).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+87).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+87).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+88).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+88).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+89).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+89).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+90).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+90).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+91).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+91).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+92).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+92).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+93).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+93).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+94).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+94).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+95).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+95).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+96).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+96).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+97).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+97).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+98).setCellValue("모두 N");
		getCell(sheet4, rowNo, colNo+98).setCellStyle(HeaderStyleGreen3);
		getCell(sheet4, rowNo, colNo+99).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+99).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+100).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+100).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+101).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+101).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+102).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+102).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+103).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+103).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+104).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+104).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+105).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+105).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+106).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+106).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+107).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+107).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+108).setCellValue("직접입력 / 맑음, 비, 눈");
		getCell(sheet4, rowNo, colNo+108).setCellStyle(HeaderStylePink);
		getCell(sheet4, rowNo, colNo+109).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+109).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+110).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+110).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+111).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+111).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+112).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+112).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+113).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+113).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+114).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+114).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+115).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+115).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+116).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+116).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+117).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+117).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+118).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+118).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+119).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+119).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+120).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+120).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+121).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+121).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+122).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+122).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+123).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+123).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+124).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+124).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+125).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+125).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+126).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+126).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+127).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+127).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+128).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+128).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+129).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+129).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+130).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+131).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+132).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+133).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+134).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+134).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+135).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+135).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+136).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+136).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+137).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+137).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+138).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+138).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+139).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+139).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+140).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+140).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+141).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+141).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+142).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+142).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+143).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+143).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+144).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+144).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+145).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+145).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+146).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+146).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+147).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+147).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+148).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+148).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+149).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+149).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+150).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+150).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+151).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+151).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+152).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+152).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+153).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+153).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+154).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+154).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+155).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+155).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+156).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+156).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+157).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+157).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+158).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+158).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+159).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+159).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+160).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+160).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+161).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+162).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+163).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+164).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+164).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+165).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+166).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+166).setCellStyle(HeaderStyleSky);
		getCell(sheet4, rowNo, colNo+167).setCellValue("미입력, 선택사항");
		getCell(sheet4, rowNo, colNo+167).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo, colNo+168).setCellValue("자동입력");
		getCell(sheet4, rowNo, colNo+168).setCellStyle(HeaderStyleSky);

		getCell(sheet4, rowNo+1, colNo).setCellValue("");
		getCell(sheet4, rowNo+1, colNo).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+1, colNo+1).setCellValue("소속");
		getCell(sheet4, rowNo+1, colNo+1).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+2).setCellValue("조사자");
		getCell(sheet4, rowNo+1, colNo+2).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+3).setCellValue("전화번호");
		getCell(sheet4, rowNo+1, colNo+3).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+4).setCellValue("조사일자");
		getCell(sheet4, rowNo+1, colNo+4).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+5).setCellValue("유역면적");
		getCell(sheet4, rowNo+1, colNo+5).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+6).setCellValue("보호시설유무");
		getCell(sheet4, rowNo+1, colNo+6).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+7).setCellValue("보호시설수");
		getCell(sheet4, rowNo+1, colNo+7).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+8).setCellValue("인가유무");
		getCell(sheet4, rowNo+1, colNo+8).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+9).setCellValue("인가호수");
		getCell(sheet4, rowNo+1, colNo+9).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+10).setCellValue("상부주요시설");
		getCell(sheet4, rowNo+1, colNo+10).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+11).setCellValue("하부주요시설");
		getCell(sheet4, rowNo+1, colNo+11).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+12).setCellValue("상부개소");
		getCell(sheet4, rowNo+1, colNo+12).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+13).setCellValue("하부개소");
		getCell(sheet4, rowNo+1, colNo+13).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+14).setCellValue("황폐지발생원코드");
		getCell(sheet4, rowNo+1, colNo+14).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+15).setCellValue("계류평균경사도");
		getCell(sheet4, rowNo+1, colNo+15).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+16).setCellValue("계류상단경사도");
		getCell(sheet4, rowNo+1, colNo+16).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+17).setCellValue("계류하단경사도");
		getCell(sheet4, rowNo+1, colNo+17).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+18).setCellValue("계류명");
		getCell(sheet4, rowNo+1, colNo+18).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+19).setCellValue("변곡점거리");
		getCell(sheet4, rowNo+1, colNo+19).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+20).setCellValue("변곡점고도높이");
		getCell(sheet4, rowNo+1, colNo+20).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+21).setCellValue("기타변곡점수");
		getCell(sheet4, rowNo+1, colNo+21).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+22).setCellValue("계류평균폭");
		getCell(sheet4, rowNo+1, colNo+22).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+23).setCellValue("변곡점고도높이");
		getCell(sheet4, rowNo+1, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+24).setCellValue("상단 거리");
		getCell(sheet4, rowNo+1, colNo+24).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+25).setCellValue("상단 고도");
		getCell(sheet4, rowNo+1, colNo+25).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+26).setCellValue("상단 토심");
		getCell(sheet4, rowNo+1, colNo+26).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+27).setCellValue("계류전석분포상황코드");
		getCell(sheet4, rowNo+1, colNo+27).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+28).setCellValue("상단 전석");
		getCell(sheet4, rowNo+1, colNo+28).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+29).setCellValue("상단 위도");
		getCell(sheet4, rowNo+1, colNo+29).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+30).setCellValue("상단 경도");
		getCell(sheet4, rowNo+1, colNo+30).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+31).setCellValue("중간 거리");
		getCell(sheet4, rowNo+1, colNo+31).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+32).setCellValue("중간 고도");
		getCell(sheet4, rowNo+1, colNo+32).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+33).setCellValue("중간 토심");
		getCell(sheet4, rowNo+1, colNo+33).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+34).setCellValue("계류중간전석분포코드");
		getCell(sheet4, rowNo+1, colNo+34).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+35).setCellValue("중간 전석");
		getCell(sheet4, rowNo+1, colNo+35).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+36).setCellValue("중간 위도");
		getCell(sheet4, rowNo+1, colNo+36).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+37).setCellValue("중간 경도");
		getCell(sheet4, rowNo+1, colNo+37).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+38).setCellValue("하단 거리");
		getCell(sheet4, rowNo+1, colNo+38).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+39).setCellValue("하단 고도");
		getCell(sheet4, rowNo+1, colNo+39).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+40).setCellValue("하단 토심");   
		getCell(sheet4, rowNo+1, colNo+40).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+41).setCellValue("계류하단전석분포코드");
		getCell(sheet4, rowNo+1, colNo+41).setCellStyle(HeaderStyleGray);   
		getCell(sheet4, rowNo+1, colNo+42).setCellValue("하단 전석");
		getCell(sheet4, rowNo+1, colNo+42).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+43).setCellValue("하단 위도");
		getCell(sheet4, rowNo+1, colNo+43).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+44).setCellValue("하단 경도");
		getCell(sheet4, rowNo+1, colNo+44).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+45).setCellValue("위험경사지길이");
		getCell(sheet4, rowNo+1, colNo+45).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+46).setCellValue("위험경사지경사도");
		getCell(sheet4, rowNo+1, colNo+46).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+47).setCellValue("위험경사지위치수");
		getCell(sheet4, rowNo+1, colNo+47).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+48).setCellValue("위험경사지유형수");
		getCell(sheet4, rowNo+1, colNo+48).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+49).setCellValue("위험사면모암수");
		getCell(sheet4, rowNo+1, colNo+49).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+50).setCellValue("토지현황균열유무");
		getCell(sheet4, rowNo+1, colNo+50).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+51).setCellValue("토지현황함몰유무");
		getCell(sheet4, rowNo+1, colNo+51).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+52).setCellValue("토지현황융기유무");
		getCell(sheet4, rowNo+1, colNo+52).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+53).setCellValue("토지현황말단부압출유무");
		getCell(sheet4, rowNo+1, colNo+53).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+54).setCellValue("토지현황확장예상유무");
		getCell(sheet4, rowNo+1, colNo+54).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+55).setCellValue("용수현황상시존재유무");
		getCell(sheet4, rowNo+1, colNo+55).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+56).setCellValue("용수현황강우용수유무");
		getCell(sheet4, rowNo+1, colNo+56).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+57).setCellValue("용수현황경사지습도유무");
		getCell(sheet4, rowNo+1, colNo+57).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+58).setCellValue("용수현황경사지건조유무");
		getCell(sheet4, rowNo+1, colNo+58).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+59).setCellValue("임상코드");
		getCell(sheet4, rowNo+1, colNo+59).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+60).setCellValue("밀도코드");
		getCell(sheet4, rowNo+1, colNo+60).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+61).setCellValue("경급코드");
		getCell(sheet4, rowNo+1, colNo+61).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+62).setCellValue("계류보전유무");
		getCell(sheet4, rowNo+1, colNo+62).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+63).setCellValue("계류보전폭");
		getCell(sheet4, rowNo+1, colNo+63).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+64).setCellValue("계류보전높이");
		getCell(sheet4, rowNo+1, colNo+64).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+65).setCellValue("계류보전상단위도값");
		getCell(sheet4, rowNo+1, colNo+65).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+66).setCellValue("계류보전상단경도값");
		getCell(sheet4, rowNo+1, colNo+66).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+67).setCellValue("계류보전하단위도값");
		getCell(sheet4, rowNo+1, colNo+67).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+68).setCellValue("계류보전하단경도값");
		getCell(sheet4, rowNo+1, colNo+68).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+69).setCellValue("계류보전공정보막이유무");
		getCell(sheet4, rowNo+1, colNo+69).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+70).setCellValue("계류보전공정골막이유무");
		getCell(sheet4, rowNo+1, colNo+70).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+71).setCellValue("계류보전공정바닥막이유무");
		getCell(sheet4, rowNo+1, colNo+71).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+72).setCellValue("계류보전공정돌수로유무");
		getCell(sheet4, rowNo+1, colNo+72).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+73).setCellValue("계류보전공정기슭막이유무");
		getCell(sheet4, rowNo+1, colNo+73).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+74).setCellValue("계류보전공정기타유무");
		getCell(sheet4, rowNo+1, colNo+74).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+75).setCellValue("계류보전공정기타내용");
		getCell(sheet4, rowNo+1, colNo+75).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+76).setCellValue("예방사업계류보전유무");
		getCell(sheet4, rowNo+1, colNo+76).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+77).setCellValue("예방사업사방댐유무");
		getCell(sheet4, rowNo+1, colNo+77).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+78).setCellValue("예방사업산지사방유무");
		getCell(sheet4, rowNo+1, colNo+78).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+79).setCellValue("예방사업가능여부");
		getCell(sheet4, rowNo+1, colNo+79).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+80).setCellValue("예방사업선정사유내용");
		getCell(sheet4, rowNo+1, colNo+80).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+81).setCellValue("선정사유내용");
		getCell(sheet4, rowNo+1, colNo+81).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+82).setCellValue("구역설정사유내용");
		getCell(sheet4, rowNo+1, colNo+82).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+83).setCellValue("종합의견내용");
		getCell(sheet4, rowNo+1, colNo+83).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+84).setCellValue("특이사항내용");
		getCell(sheet4, rowNo+1, colNo+84).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+85).setCellValue("분석함수율");
		getCell(sheet4, rowNo+1, colNo+85).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+86).setCellValue("분석밀도율");
		getCell(sheet4, rowNo+1, colNo+86).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+87).setCellValue("분석습윤량");
		getCell(sheet4, rowNo+1, colNo+87).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+88).setCellValue("분석포화량");
		getCell(sheet4, rowNo+1, colNo+88).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+89).setCellValue("분석건조량");
		getCell(sheet4, rowNo+1, colNo+89).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+90).setCellValue("분석액성한계비율");
		getCell(sheet4, rowNo+1, colNo+90).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+91).setCellValue("분석소성한계비율");
		getCell(sheet4, rowNo+1, colNo+91).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+92).setCellValue("분석자갈비율");
		getCell(sheet4, rowNo+1, colNo+92).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+93).setCellValue("분석모래비율");
		getCell(sheet4, rowNo+1, colNo+93).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+94).setCellValue("분석점토비율");
		getCell(sheet4, rowNo+1, colNo+94).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+95).setCellValue("분석투수성계수");
		getCell(sheet4, rowNo+1, colNo+95).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+96).setCellValue("분석전단저항각도");
		getCell(sheet4, rowNo+1, colNo+96).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+97).setCellValue("계류상황코드");
		getCell(sheet4, rowNo+1, colNo+97).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+98).setCellValue("야장삭제여부");
		getCell(sheet4, rowNo+1, colNo+98).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+99).setCellValue("위험경사지위치위도값");
		getCell(sheet4, rowNo+1, colNo+99).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+100).setCellValue("위험경사지위치경도값");
		getCell(sheet4, rowNo+1, colNo+100).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+101).setCellValue("하단시점특이사항내용");
		getCell(sheet4, rowNo+1, colNo+101).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+102).setCellValue("조사ID");
		getCell(sheet4, rowNo+1, colNo+102).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+103).setCellValue("조사상세주소");
		getCell(sheet4, rowNo+1, colNo+103).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+104).setCellValue("위치위도값");
		getCell(sheet4, rowNo+1, colNo+104).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+105).setCellValue("위치경도값");
		getCell(sheet4, rowNo+1, colNo+105).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+106).setCellValue("조사지면적");
		getCell(sheet4, rowNo+1, colNo+106).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+107).setCellValue("조사지길이");
		getCell(sheet4, rowNo+1, colNo+107).setCellStyle(HeaderStyleBlue2);
		getCell(sheet4, rowNo+1, colNo+108).setCellValue("날씨명");
		getCell(sheet4, rowNo+1, colNo+108).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+109).setCellValue("계류상단분포암반유무");
		getCell(sheet4, rowNo+1, colNo+109).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+110).setCellValue("계류상단분포전석유무");
		getCell(sheet4, rowNo+1, colNo+110).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+111).setCellValue("계류상단분포자갈유무");
		getCell(sheet4, rowNo+1, colNo+111).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+112).setCellValue("계류상단분포모래유무");
		getCell(sheet4, rowNo+1, colNo+112).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+113).setCellValue("계류상단분포기타유무");
		getCell(sheet4, rowNo+1, colNo+113).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+114).setCellValue("계류상단분포기타내용");
		getCell(sheet4, rowNo+1, colNo+114).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+115).setCellValue("계류중간분포암반유무");
		getCell(sheet4, rowNo+1, colNo+115).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+116).setCellValue("계류중간분포전석유무");
		getCell(sheet4, rowNo+1, colNo+116).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+117).setCellValue("계류중간분포자갈유무");
		getCell(sheet4, rowNo+1, colNo+117).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+118).setCellValue("계류중간분포모래유무");
		getCell(sheet4, rowNo+1, colNo+118).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+119).setCellValue("계류중간분포기타유무");
		getCell(sheet4, rowNo+1, colNo+119).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+120).setCellValue("계류중간부분포기타내용");
		getCell(sheet4, rowNo+1, colNo+120).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+121).setCellValue("계류하단분포암반유무");
		getCell(sheet4, rowNo+1, colNo+121).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+122).setCellValue("계류하단분포전석유무");
		getCell(sheet4, rowNo+1, colNo+122).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+123).setCellValue("계류하단분포자갈유무");
		getCell(sheet4, rowNo+1, colNo+123).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+124).setCellValue("계류하단분포모래유무");
		getCell(sheet4, rowNo+1, colNo+124).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+125).setCellValue("계류하단분포기타유무");
		getCell(sheet4, rowNo+1, colNo+125).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+126).setCellValue("계류하단분포기타내용");
		getCell(sheet4, rowNo+1, colNo+126).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+127).setCellValue("조사자직급명");
		getCell(sheet4, rowNo+1, colNo+127).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+128).setCellValue("조사지면적1");
		getCell(sheet4, rowNo+1, colNo+128).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+129).setCellValue("조사지면적2");
		getCell(sheet4, rowNo+1, colNo+129).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+130).setCellValue("조사지유역면적1");
		getCell(sheet4, rowNo+1, colNo+130).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+131).setCellValue("조사지유역면적2");
		getCell(sheet4, rowNo+1, colNo+131).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+132).setCellValue("조사지계류면적");
		getCell(sheet4, rowNo+1, colNo+132).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+133).setCellValue("조사지사방댐면적");
		getCell(sheet4, rowNo+1, colNo+133).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+134).setCellValue("월류상태여부");
		getCell(sheet4, rowNo+1, colNo+134).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+135).setCellValue("계류상황코드");
		getCell(sheet4, rowNo+1, colNo+135).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+136).setCellValue("곡류상태여부");
		getCell(sheet4, rowNo+1, colNo+136).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+137).setCellValue("계류유무");
		getCell(sheet4, rowNo+1, colNo+137).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+138).setCellValue("유목유무");
		getCell(sheet4, rowNo+1, colNo+138).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+139).setCellValue("유목길이");
		getCell(sheet4, rowNo+1, colNo+139).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+140).setCellValue("퇴적유무");
		getCell(sheet4, rowNo+1, colNo+140).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+141).setCellValue("좌안붕괴");
		getCell(sheet4, rowNo+1, colNo+141).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+142).setCellValue("우안붕괴");
		getCell(sheet4, rowNo+1, colNo+142).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+143).setCellValue("평균전석");
		getCell(sheet4, rowNo+1, colNo+143).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+144).setCellValue("계류하단폭");
		getCell(sheet4, rowNo+1, colNo+144).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+145).setCellValue("계류중간폭");
		getCell(sheet4, rowNo+1, colNo+145).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+146).setCellValue("계류상단폭");
		getCell(sheet4, rowNo+1, colNo+146).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+147).setCellValue("위험경사지상단높이");
		getCell(sheet4, rowNo+1, colNo+147).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+148).setCellValue("위험경사지하단높이");
		getCell(sheet4, rowNo+1, colNo+148).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+149).setCellValue("위험경사지상승유무");
		getCell(sheet4, rowNo+1, colNo+149).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+150).setCellValue("위험경사지평형유무");
		getCell(sheet4, rowNo+1, colNo+150).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+151).setCellValue("위험경사지하강유무");
		getCell(sheet4, rowNo+1, colNo+151).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+152).setCellValue("위험경사지조합유무");
		getCell(sheet4, rowNo+1, colNo+152).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+153).setCellValue("사면현황모암코드");
		getCell(sheet4, rowNo+1, colNo+153).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+154).setCellValue("토지현황표본토심높이");
		getCell(sheet4, rowNo+1, colNo+154).setCellStyle(HeaderStyleGreen2);
		getCell(sheet4, rowNo+1, colNo+155).setCellValue("토지현황표본위도값");
		getCell(sheet4, rowNo+1, colNo+155).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+156).setCellValue("토지현황표본경도값");
		getCell(sheet4, rowNo+1, colNo+156).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+157).setCellValue("유역현황내용");
		getCell(sheet4, rowNo+1, colNo+157).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+158).setCellValue("산사태발생위험지역여부");
		getCell(sheet4, rowNo+1, colNo+158).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+159).setCellValue("산사태발생위험가능성여부");
		getCell(sheet4, rowNo+1, colNo+159).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+160).setCellValue("산사태발생보호시설여부");
		getCell(sheet4, rowNo+1, colNo+160).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+161).setCellValue("산사태발생불량림여부");
		getCell(sheet4, rowNo+1, colNo+161).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+162).setCellValue("산사태발생재난위험여부");
		getCell(sheet4, rowNo+1, colNo+162).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+163).setCellValue("조사평가점수");
		getCell(sheet4, rowNo+1, colNo+163).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+164).setCellValue("계류총길이");
		getCell(sheet4, rowNo+1, colNo+164).setCellStyle(HeaderStyleYellow);
		getCell(sheet4, rowNo+1, colNo+165).setCellValue("조사기타주소");
		getCell(sheet4, rowNo+1, colNo+165).setCellStyle(HeaderStyleGray);
		getCell(sheet4, rowNo+1, colNo+166).setCellValue("용역사업번호");
		getCell(sheet4, rowNo+1, colNo+166).setCellStyle(HeaderStylePink2);
		getCell(sheet4, rowNo+1, colNo+167).setCellValue("사업년도");
		getCell(sheet4, rowNo+1, colNo+167).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+1, colNo+168).setCellValue("산사태발생위험등급코드");
		getCell(sheet4, rowNo+1, colNo+168).setCellStyle(HeaderStylePink2);

		getCell(sheet4, rowNo+2, colNo).setCellValue("");
		getCell(sheet4, rowNo+2, colNo).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+1).setCellValue("VNARA_EXMNE_PSTN_NM");
		getCell(sheet4, rowNo+2, colNo+1).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+2).setCellValue("VNARA_EXMNE_NM");
		getCell(sheet4, rowNo+2, colNo+2).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+3).setCellValue("VNARA_EXMNE_TLNO");
		getCell(sheet4, rowNo+2, colNo+3).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+4).setCellValue("VNARA_EXMNN_DT");
		getCell(sheet4, rowNo+2, colNo+4).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+5).setCellValue("VNARA_SRZN_DRAR_AREA");
		getCell(sheet4, rowNo+2, colNo+5).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+6).setCellValue("VNARA_FTSPT_FG");
		getCell(sheet4, rowNo+2, colNo+6).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+7).setCellValue("VNARA_FTSPT_CNT");
		getCell(sheet4, rowNo+2, colNo+7).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+8).setCellValue("VNARA_FTSPT_HO_CNT_FG");
		getCell(sheet4, rowNo+2, colNo+8).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+9).setCellValue("VNARA_FTSPT_HO_CNT");
		getCell(sheet4, rowNo+2, colNo+9).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+10).setCellValue("VNARA_UPEND_FTSPT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+10).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+11).setCellValue("VNARA_LWPRT_FTSPT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+11).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+12).setCellValue("VNARA_UPEND_HO_CNT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+12).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+13).setCellValue("VNARA_LWPRT_HO_CNT_DTL_CONT");
		getCell(sheet4, rowNo+2, colNo+13).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+14).setCellValue("WSLND_SOURCE_CD");
		getCell(sheet4, rowNo+2, colNo+14).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+15).setCellValue("VNARA_MRNG_AVRG_GRDNT");
		getCell(sheet4, rowNo+2, colNo+15).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+16).setCellValue("VNARA_MRNG_UPEND_GRDNT");
		getCell(sheet4, rowNo+2, colNo+16).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+17).setCellValue("VNARA_MRNG_LWPRT_GRDNT");
		getCell(sheet4, rowNo+2, colNo+17).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+18).setCellValue("VNARA_MRNG_NM");
		getCell(sheet4, rowNo+2, colNo+18).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+19).setCellValue("VNARA_IFLPT_DSTNC");
		getCell(sheet4, rowNo+2, colNo+19).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+20).setCellValue("VNARA_IFLPT_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+20).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+21).setCellValue("VNARA_ETC_IFLPT_CNT");
		getCell(sheet4, rowNo+2, colNo+21).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+22).setCellValue("VNARA_MRNG_AVRG_WDT");
		getCell(sheet4, rowNo+2, colNo+22).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+23).setCellValue("UPEND_TRMNA_PCMTT_CONT");
		getCell(sheet4, rowNo+2, colNo+23).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+24).setCellValue("MRNG_UPEND_LCTN_DSTNC");
		getCell(sheet4, rowNo+2, colNo+24).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+25).setCellValue("MRNG_UPEND_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+25).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+26).setCellValue("MRNG_UPEND_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+26).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+27).setCellValue("MRNG_BDSTN_DISTR_STTN_CD");
		getCell(sheet4, rowNo+2, colNo+27).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+28).setCellValue("MRNG_UPEND_BDSTN_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+28).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+29).setCellValue("MRNG_UPEND_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+29).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+30).setCellValue("MRNG_UPEND_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+30).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+31).setCellValue("MRNG_MDDL_LCTN_DSTNC");
		getCell(sheet4, rowNo+2, colNo+31).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+32).setCellValue("MRNG_MDDL_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+32).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+33).setCellValue("MRNG_MDDL_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+33).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+34).setCellValue("MRNG_MDDL_BDSTN_DISTR_CD");
		getCell(sheet4, rowNo+2, colNo+34).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+35).setCellValue("MRNG_MDDL_BDSTN_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+35).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+36).setCellValue("MRNG_MDDL_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+36).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+37).setCellValue("MRNG_MDDL_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+37).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+38).setCellValue("MRNG_LWPRT_LCTN_DSTNC");
		getCell(sheet4, rowNo+2, colNo+38).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+39).setCellValue("MRNG_LWPRT_ANCT_HGHT");
		getCell(sheet4, rowNo+2, colNo+39).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+40).setCellValue("MRNG_LWPRT_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+40).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+41).setCellValue("MRNG_LWPRT_BDSTN_DISTR_CD");
		getCell(sheet4, rowNo+2, colNo+41).setCellStyle(HeaderStyle);   
		getCell(sheet4, rowNo+2, colNo+42).setCellValue("MRNG_LWPRT_BDSTN_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+42).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+43).setCellValue("MRNG_LWPRT_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+43).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+44).setCellValue("MRNG_LWPRT_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+44).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+45).setCellValue("RISK_CLLN_LNGTH");
		getCell(sheet4, rowNo+2, colNo+45).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+46).setCellValue("RISK_CLLN_GRDNT");
		getCell(sheet4, rowNo+2, colNo+46).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+47).setCellValue("RISK_CLLN_LCTN_CNT");
		getCell(sheet4, rowNo+2, colNo+47).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+48).setCellValue("RISK_CLLN_TPE_CNT");
		getCell(sheet4, rowNo+2, colNo+48).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+49).setCellValue("RISK_CLLN_PRRCK_CNT");
		getCell(sheet4, rowNo+2, colNo+49).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+50).setCellValue("LAND_STATS_CRCK_FG");
		getCell(sheet4, rowNo+2, colNo+50).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+51).setCellValue("LAND_STATS_DENT_FG");
		getCell(sheet4, rowNo+2, colNo+51).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+52).setCellValue("LAND_STATS_UPLFT_FG");
		getCell(sheet4, rowNo+2, colNo+52).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+53).setCellValue("LAND_STATS_EPTES_FG");
		getCell(sheet4, rowNo+2, colNo+53).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+54).setCellValue("LAND_STATS_ESTNS_EXPEC_FG");
		getCell(sheet4, rowNo+2, colNo+54).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+55).setCellValue("USWTR_STATS_ORDTM_EXIST_FG");
		getCell(sheet4, rowNo+2, colNo+55).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+56).setCellValue("USWTR_STATS_RAIN_USWTR_FG");
		getCell(sheet4, rowNo+2, colNo+56).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+57).setCellValue("USWTR_STATS_CLLN_HMDT_FG");
		getCell(sheet4, rowNo+2, colNo+57).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+58).setCellValue("USWTR_STATS_CLLN_CNSTR_FG");
		getCell(sheet4, rowNo+2, colNo+58).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+59).setCellValue("FRTP_STATS_FRTP_CD");
		getCell(sheet4, rowNo+2, colNo+59).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+60).setCellValue("FRTP_STATS_SGRST_DNST_CD");
		getCell(sheet4, rowNo+2, colNo+60).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+61).setCellValue("FRTP_STATS_SGRST_DMCLS_CD");
		getCell(sheet4, rowNo+2, colNo+61).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+62).setCellValue("MRNG_PRSRV_FG");
		getCell(sheet4, rowNo+2, colNo+62).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+63).setCellValue("MRNG_PRSRV_WDT");
		getCell(sheet4, rowNo+2, colNo+63).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+64).setCellValue("MRNG_PRSRV_HGHT");
		getCell(sheet4, rowNo+2, colNo+64).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+65).setCellValue("MRNG_PRSRV_UPEND_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+65).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+66).setCellValue("MRNG_PRSRV_UPEND_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+66).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+67).setCellValue("MRNG_PRSRV_LWPRT_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+67).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+68).setCellValue("MRNG_PRSRV_LWPRT_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+68).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+69).setCellValue("MRNG_PRSRV_PROCS_BADP_FG");
		getCell(sheet4, rowNo+2, colNo+69).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+70).setCellValue("MRNG_PRSRV_PROCS_PBRR_FG");
		getCell(sheet4, rowNo+2, colNo+70).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+71).setCellValue("MRNG_PRSRV_PROCS_BSLWK_FG");
		getCell(sheet4, rowNo+2, colNo+71).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+72).setCellValue("MRNG_PRSRV_PROCS_STNCH_FG");
		getCell(sheet4, rowNo+2, colNo+72).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+73).setCellValue("MRNG_PRSRV_PROCS_RVMT_FG");
		getCell(sheet4, rowNo+2, colNo+73).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+74).setCellValue("MRNG_PRSRV_PROCS_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+74).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+75).setCellValue("MRNG_PRSRV_PROCS_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+75).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+76).setCellValue("PRVNN_BSNSS_MRNG_PRSRV_FG");
		getCell(sheet4, rowNo+2, colNo+76).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+77).setCellValue("PRVNN_BSNSS_ECNDM_FG");
		getCell(sheet4, rowNo+2, colNo+77).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+78).setCellValue("PRVNN_BSNSS_MNDST_ERCNT_FG");
		getCell(sheet4, rowNo+2, colNo+78).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+79).setCellValue("PRVNN_BSNSS_PSSBL_YN");
		getCell(sheet4, rowNo+2, colNo+79).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+80).setCellValue("PRVNN_BSNSS_SLCTN_RSN_CONT");
		getCell(sheet4, rowNo+2, colNo+80).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+81).setCellValue("VNARA_SLCTN_RSN_CONT");
		getCell(sheet4, rowNo+2, colNo+81).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+82).setCellValue("VNARA_ZONE_ESTBS_RSN_CONT");
		getCell(sheet4, rowNo+2, colNo+82).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+83).setCellValue("VNARA_GNRLZ_OPNN_CONT");
		getCell(sheet4, rowNo+2, colNo+83).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+84).setCellValue("VNARA_PCMTT_CONT");
		getCell(sheet4, rowNo+2, colNo+84).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+85).setCellValue("ANLSS_MSCNT");
		getCell(sheet4, rowNo+2, colNo+85).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+86).setCellValue("ANLSS_DNST_RT");
		getCell(sheet4, rowNo+2, colNo+86).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+87).setCellValue("ANLSS_DMPNS_QNTT");
		getCell(sheet4, rowNo+2, colNo+87).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+88).setCellValue("ANLSS_STRTN_QNTT");
		getCell(sheet4, rowNo+2, colNo+88).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+89).setCellValue("ANLSS_CNSTR_QNTT");
		getCell(sheet4, rowNo+2, colNo+89).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+90).setCellValue("ANLSS_LQDLM_RATE");
		getCell(sheet4, rowNo+2, colNo+90).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+91).setCellValue("ANLSS_PLTLM_RATE");
		getCell(sheet4, rowNo+2, colNo+91).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+92).setCellValue("ANLSS_GRVL_RATE");
		getCell(sheet4, rowNo+2, colNo+92).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+93).setCellValue("ANLSS_SAND_RATE");
		getCell(sheet4, rowNo+2, colNo+93).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+94).setCellValue("ANLSS_CLAY_RATE");
		getCell(sheet4, rowNo+2, colNo+94).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+95).setCellValue("ANLSS_PV_CFFCN");
		getCell(sheet4, rowNo+2, colNo+95).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+96).setCellValue("ANLSS_SRSTC_AGL");
		getCell(sheet4, rowNo+2, colNo+96).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+97).setCellValue("ANLSS_CHSN_QNTT");
		getCell(sheet4, rowNo+2, colNo+97).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+98).setCellValue("VNARA_FLNT_DLT_YN");
		getCell(sheet4, rowNo+2, colNo+98).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+99).setCellValue("RISK_CLLN_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+99).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+100).setCellValue("RISK_CLLN_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+100).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+101).setCellValue("LWPRT_PNTM_PCMTT_CONT");
		getCell(sheet4, rowNo+2, colNo+101).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+102).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet4, rowNo+2, colNo+102).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+103).setCellValue("VNARA_EXMNN_DTADD");
		getCell(sheet4, rowNo+2, colNo+103).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+104).setCellValue("VNARA_LCTN_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+104).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+105).setCellValue("VNARA_LCTN_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+105).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+106).setCellValue("VNARA_SRZN_AREA");
		getCell(sheet4, rowNo+2, colNo+106).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+107).setCellValue("VNARA_SRZN_LNGTH");
		getCell(sheet4, rowNo+2, colNo+107).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+108).setCellValue("VNARA_WTHR_NM");
		getCell(sheet4, rowNo+2, colNo+108).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+109).setCellValue("MRNG_UPEND_DISTR_BSROK_FG");
		getCell(sheet4, rowNo+2, colNo+109).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+110).setCellValue("MRNG_UPEND_DISTR_BDSTN_FG");
		getCell(sheet4, rowNo+2, colNo+110).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+111).setCellValue("MRNG_UPEND_DISTR_GRVL_FG");
		getCell(sheet4, rowNo+2, colNo+111).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+112).setCellValue("MRNG_UPEND_DISTR_SAND_FG");
		getCell(sheet4, rowNo+2, colNo+112).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+113).setCellValue("MRNG_UPEND_DISTR_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+113).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+114).setCellValue("MRNG_UPEND_DISTR_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+114).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+115).setCellValue("MRNG_MDDL_DISTR_BSROK_FG");
		getCell(sheet4, rowNo+2, colNo+115).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+116).setCellValue("MRNG_MDDL_DISTR_BDSTN_FG");
		getCell(sheet4, rowNo+2, colNo+116).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+117).setCellValue("MRNG_MDDL_DISTR_GRVL_FG");
		getCell(sheet4, rowNo+2, colNo+117).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+118).setCellValue("MRNG_MDDL_DISTR_SAND_FG");
		getCell(sheet4, rowNo+2, colNo+118).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+119).setCellValue("MRNG_MDDL_DISTR_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+119).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+120).setCellValue("MRNG_MDDL_DISTR_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+120).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+121).setCellValue("MRNG_LWPRT_DISTR_BSROK_FG");
		getCell(sheet4, rowNo+2, colNo+121).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+122).setCellValue("MRNG_LWPRT_DISTR_BDSTN_FG");
		getCell(sheet4, rowNo+2, colNo+122).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+123).setCellValue("MRNG_LWPRT_DISTR_GRVL_FG");
		getCell(sheet4, rowNo+2, colNo+123).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+124).setCellValue("MRNG_LWPRT_DISTR_SAND_FG");
		getCell(sheet4, rowNo+2, colNo+124).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+125).setCellValue("MRNG_LWPRT_DISTR_ETC_FG");
		getCell(sheet4, rowNo+2, colNo+125).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+126).setCellValue("MRNG_LWPRT_DISTR_ETC_CONT");
		getCell(sheet4, rowNo+2, colNo+126).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+127).setCellValue("VNARA_EXMNE_CLPST_NM");
		getCell(sheet4, rowNo+2, colNo+127).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+128).setCellValue("VNARA_SRZN_AREA1");
		getCell(sheet4, rowNo+2, colNo+128).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+129).setCellValue("VNARA_SRZN_AREA2");
		getCell(sheet4, rowNo+2, colNo+129).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+130).setCellValue("VNARA_SRZN_DRAR_AREA1");
		getCell(sheet4, rowNo+2, colNo+130).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+131).setCellValue("VNARA_SRZN_DRAR_AREA2");
		getCell(sheet4, rowNo+2, colNo+131).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+132).setCellValue("VNARA_SRZN_MRNG_AREA");
		getCell(sheet4, rowNo+2, colNo+132).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+133).setCellValue("VNARA_SRZN_ECNDM_AREA");
		getCell(sheet4, rowNo+2, colNo+133).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+134).setCellValue("VNARA_OVFLW_STTS_YN");
		getCell(sheet4, rowNo+2, colNo+134).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+135).setCellValue("MRNG_STTN_CD");
		getCell(sheet4, rowNo+2, colNo+135).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+136).setCellValue("VNARA_BTFLW_STTS_YN");
		getCell(sheet4, rowNo+2, colNo+136).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+137).setCellValue("VNARA_MRNG_FG");
		getCell(sheet4, rowNo+2, colNo+137).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+138).setCellValue("VNARA_FLDWD_FG");
		getCell(sheet4, rowNo+2, colNo+138).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+139).setCellValue("VNARA_FLDWD_LNGTH");
		getCell(sheet4, rowNo+2, colNo+139).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+140).setCellValue("VNARA_STMBD_ACCMA_ARA_FG");
		getCell(sheet4, rowNo+2, colNo+140).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+141).setCellValue("SLIVW_LBNK_CNT");
		getCell(sheet4, rowNo+2, colNo+141).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+142).setCellValue("SLIVW_RBNK_CNT");
		getCell(sheet4, rowNo+2, colNo+142).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+143).setCellValue("MRNG_BDSTN_AVRG_DISTR_RATE");
		getCell(sheet4, rowNo+2, colNo+143).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+144).setCellValue("MRNG_LWPRT_WDT");
		getCell(sheet4, rowNo+2, colNo+144).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+145).setCellValue("MRNG_MDDL_WDT");
		getCell(sheet4, rowNo+2, colNo+145).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+146).setCellValue("MRNG_UPEND_WDT");
		getCell(sheet4, rowNo+2, colNo+146).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+147).setCellValue("RISK_CLLN_UPEND_HGHT");
		getCell(sheet4, rowNo+2, colNo+147).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+148).setCellValue("RISK_CLLN_LWPRT_HGHT");
		getCell(sheet4, rowNo+2, colNo+148).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+149).setCellValue("RISK_CLLN_RISE_FG");
		getCell(sheet4, rowNo+2, colNo+149).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+150).setCellValue("RISK_CLLN_EQLBM_FG");
		getCell(sheet4, rowNo+2, colNo+150).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+151).setCellValue("RISK_CLLN_DSWNG_FG");
		getCell(sheet4, rowNo+2, colNo+151).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+152).setCellValue("RISK_CLLN_MXTR_FG");
		getCell(sheet4, rowNo+2, colNo+152).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+153).setCellValue("PRDN_STATS_PRRCK_CD");
		getCell(sheet4, rowNo+2, colNo+153).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+154).setCellValue("LAND_STATS_SMPL_SLDPT_HGHT");
		getCell(sheet4, rowNo+2, colNo+154).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+155).setCellValue("LAND_STATS_SMPL_LTTD_VAL");
		getCell(sheet4, rowNo+2, colNo+155).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+156).setCellValue("LAND_STATS_SMPL_LNGTD_VAL");
		getCell(sheet4, rowNo+2, colNo+156).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+157).setCellValue("VNARA_DRAR_STATS_CONT");
		getCell(sheet4, rowNo+2, colNo+157).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+158).setCellValue("LNDSL_OCCRR_RISK_ARA_YN");
		getCell(sheet4, rowNo+2, colNo+158).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+159).setCellValue("LNDSL_OCCRR_RISK_PSSBT_YN");
		getCell(sheet4, rowNo+2, colNo+159).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+160).setCellValue("LNDSL_OCCRR_FTSPT_YN");
		getCell(sheet4, rowNo+2, colNo+160).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+161).setCellValue("LNDSL_OCCRR_BDFRS_YN");
		getCell(sheet4, rowNo+2, colNo+161).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+162).setCellValue("LNDSL_OCCRR_MSFRT_RISK_YN");
		getCell(sheet4, rowNo+2, colNo+162).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+163).setCellValue("VNARA_EXMNN_EVSCR");
		getCell(sheet4, rowNo+2, colNo+163).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+164).setCellValue("VNARA_MRNG_TOT_LNGTH");
		getCell(sheet4, rowNo+2, colNo+164).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+165).setCellValue("VNARA_EXMNN_ETC_ADDR");
		getCell(sheet4, rowNo+2, colNo+165).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+166).setCellValue("SERVC_BSNSS_NO");
		getCell(sheet4, rowNo+2, colNo+166).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+167).setCellValue("BSNSS_YR");
		getCell(sheet4, rowNo+2, colNo+167).setCellStyle(HeaderStyle);
		getCell(sheet4, rowNo+2, colNo+168).setCellValue("LNDSL_OCCRR_RISK_GRCD");
		getCell(sheet4, rowNo+2, colNo+168).setCellStyle(HeaderStyle);
		
		rowNo=3;
		String formulaText = "";

		if(dataList.size() > 0) {

			//hblee(토석류 취약지역조사야장)
			for (int i=0; i<dataList.size(); i++) {
				colNo=0;

				cell = getCell(sheet4, rowNo, colNo++);/*기번*/
				formulaText = "입력값!A"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*소속*/
				formulaText = "입력값!P"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사자*/
				formulaText = "입력값!R"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*전화번호*/
				formulaText = "입력값!T"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사일자*/
				formulaText = "취약지역관리!H"+(rowNo+3);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*유역면적*/
				formulaText = "입력값!AA"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*보호시설유무*/
				formulaText = "IF(입력값!AE"+(rowNo+2)+"=\"유\",\"Y\",IF(입력값!AE"+(rowNo+2)+"=\"무\",\"N\",\"\"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);
				
				cell = getCell(sheet4, rowNo, colNo++);/*보호시설수*/
				formulaText = "입력값!AF"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*인가유무*/
				formulaText = "IF(입력값!AG"+(rowNo+2)+"=\"유\",\"Y\",IF(입력값!AG"+(rowNo+2)+"=\"무\",\"N\",\"\"))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*인가호수*/
				formulaText = "입력값!AH"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상부주요시설*/
				formulaText = "입력값!AI"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하부주요시설*/
				formulaText = "입력값!AK"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상부개소*/        
				formulaText = "입력값!AJ"+(rowNo+2)+"&\" 개소\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하부개소*/        
				formulaText = "입력값!AL"+(rowNo+2)+"&\" 개소\"";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*황폐지발생원코드*/
				formulaText = "IF(입력값!AM"+(rowNo+2)+"=1,\"WO0001\",IF(입력값!AM"+(rowNo+2)+"=2,\"WO0002\",IF(입력값!AM"+(rowNo+2)+"=2.5,\"WO0003\",IF(입력값!AM"+(rowNo+2)+"=3,\"WO0004\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류평균경사도*/
				formulaText = "입력값!AQ"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단경사도*/
				formulaText = "입력값!AS"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단경사도*/
				formulaText = "입력값!AR"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류명*/
				formulaText = "입력값!C"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*변곡점거리*/
				formulaText = "입력값!AN"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*변곡점고도높이*/
				formulaText = "입력값!AO"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*기타변곡점수*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류평균폭*/
				formulaText = "SUM(입력값!BQ"+(rowNo+2)+",입력값!CH"+(rowNo+2)+",입력값!CY"+(rowNo+2)+")/3";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*변곡점고도높이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상단 거리*/
				formulaText = "입력값!CV"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상단 고도*/
				formulaText = "입력값!CW"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상단 토심*/
				formulaText = "입력값!CX"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류전석분포상황코드*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상단 전석*/
				formulaText = "입력값!DC"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상단 위도*/
				formulaText = "LEFT((입력값!CP"+(rowNo+2)+"&\":\"&입력값!CQ"+(rowNo+2)+"&\":\"&입력값!CR"+(rowNo+2)+"),10)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*상단 경도*/
				formulaText = "LEFT((입력값!CS"+(rowNo+2)+"&\":\"&입력값!CT"+(rowNo+2)+"&\":\"&입력값!CU"+(rowNo+2)+"),11)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*중간 거리*/
				formulaText = "입력값!CE"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*중간 고도*/
				formulaText = "입력값!CF"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*중간 토심*/
				formulaText = "입력값!CG"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간전석분포코드*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*중간 전석*/
				formulaText = "입력값!CL"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*중간 위도*/
				formulaText = "LEFT((입력값!BY"+(rowNo+2)+"&\":\"&입력값!BZ"+(rowNo+2)+"&\":\"&입력값!CA"+(rowNo+2)+"),10)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*중간 경도*/
				formulaText = "LEFT((입력값!CB"+(rowNo+2)+"&\":\"&입력값!CC"+(rowNo+2)+"&\":\"&입력값!CD"+(rowNo+2)+"),11)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단 거리*/
				formulaText = "입력값!BN"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단 고도*/
				formulaText = "입력값!BO"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단 토심*/
				formulaText = "입력값!BP"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단전석분포코드*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단 전석*/
				formulaText = "입력값!BU"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단 위도*/
				formulaText = "LEFT((입력값!J"+(rowNo+2)+"&\":\"&입력값!K"+(rowNo+2)+"&\":\"&입력값!L"+(rowNo+2)+"),10)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단 경도*/
				formulaText = "LEFT((입력값!M"+(rowNo+2)+"&\":\"&입력값!N"+(rowNo+2)+"&\":\"&입력값!O"+(rowNo+2)+"),11)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지길이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지경사도*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지위치수*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지유형수*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험사면모암수*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황균열유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황함몰유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황융기유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황말단부압출유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황확장예상유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*용수현황상시존재유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*용수현황강우용수유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*용수현황경사지습도유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*용수현황경사지건조유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*임상코드*/
				formulaText = "IF(입력값!AT"+(rowNo+2)+"=\"침\",\"FT0002\",IF(입력값!AT"+(rowNo+2)+"=\"활\",\"FT0003\",IF(입력값!AT"+(rowNo+2)+"=\"혼\",\"FT0004\",IF(입력값!AT"+(rowNo+2)+"=\"무\",\"FT0001\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*밀도코드*/
				formulaText = "IF(입력값!AU"+(rowNo+2)+"=\"소(少)\",\"DS0001\",IF(입력값!AU"+(rowNo+2)+"=\"중(中)\",\"DS0002\",IF(입력값!AU"+(rowNo+2)+"=\"밀(密)\",\"DS0003\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*경급코드*/
				formulaText = "IF(입력값!AV"+(rowNo+2)+"=\"치\",\"DM0001\",IF(입력값!AV"+(rowNo+2)+"=\"소\",\"DM0002\",IF(입력값!AV"+(rowNo+2)+"=\"중\",\"DM0003\",IF(입력값!AV"+(rowNo+2)+"=\"대\",\"DM0004\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전폭*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전높이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전상단위도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전상단경도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전하단위도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전하단경도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정보막이유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정골막이유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정바닥막이유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정돌수로유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정기슭막이유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정기타유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류보전공정기타내용*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*예방사업계류보전유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*예방사업사방댐유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*예방사업산지사방유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*예방사업가능여부*/
				formulaText = "IF(입력값!DN"+(rowNo+2)+"=\"가능\",\"Y\",IF(입력값!DN"+(rowNo+2)+"=\"불가능\",\"N\",IF(입력값!DN"+(rowNo+2)+"=\"\",\"\",IF(입력값!DN"+(rowNo+2)+"=\"-\",\"\"))))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*예방사업선정사유내용*/
				formulaText = "입력값!HA"+(rowNo+2)+"&입력값!HB"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*선정사유내용*/
				formulaText = "입력값!GU"+(rowNo+2)+"&입력값!GV"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*구역설정사유내용*/
				formulaText = "입력값!GY"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*종합의견내용*/
				formulaText = "입력값!GW"+(rowNo+2)+"&입력값!GX"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*특이사항내용*/
				formulaText = "입력값!GZ"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석함수율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석밀도율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석습윤량*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석포화량*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석건조량*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석액성한계비율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석소성한계비율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석자갈비율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석모래비율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석점토비율*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석투수성계수*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*분석전단저항각도*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상황코드*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*야장삭제여부*/
				cell.setCellValue("N");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지위치위도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지위치경도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*하단시점특이사항내용*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사ID*/
				formulaText = "취약지역관리!B"+(rowNo+3);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사상세주소*/
				formulaText = "입력값!H"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위치위도값*/
				formulaText = "LEFT((입력값!J"+(rowNo+2)+"&\":\"&입력값!K"+(rowNo+2)+"&\":\"&입력값!L"+(rowNo+2)+"),10)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위치경도값*/
				formulaText = "LEFT((입력값!M"+(rowNo+2)+"&\":\"&입력값!N"+(rowNo+2)+"&\":\"&입력값!O"+(rowNo+2)+"),11)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지면적*/
				formulaText = "입력값!AD"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지길이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*날씨명*/
				cell.setCellValue("맑음");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포암반유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포전석유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포자갈유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포모래유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포기타유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단분포기타내용*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포암반유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포전석유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포자갈유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포모래유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간분포기타유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간부분포기타내용*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포암반유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포전석유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포자갈유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포모래유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포기타유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단분포기타내용*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사자직급명*/
				formulaText = "입력값!Q"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지면적1*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지면적2*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지유역면적1*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지유역면적2*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지계류면적*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사지사방댐면적*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*월류상태여부*/
				formulaText = "IF(입력값!AY"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상황코드*/
				formulaText = "IF(입력값!AZ"+(rowNo+2)+"=\"협착\",\"DM0001\",IF(입력값!AZ"+(rowNo+2)+"=\"확폭\",\"DM0002\",IF(입력값!AZ"+(rowNo+2)+"=\"인공\",\"DM0003\")))";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*곡류상태여부*/
				formulaText = "IF(입력값!BA"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류유무*/
				formulaText = "IF(입력값!BB"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*유목유무*/
				formulaText = "IF(입력값!BC"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*유목길이*/
				formulaText = "입력값!BD"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*퇴적유무*/
				formulaText = "IF(입력값!BE"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*좌안붕괴*/
				formulaText = "입력값!BF"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*우안붕괴*/
				formulaText = "입력값!BG"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*평균전석*/
				formulaText = "SUM(입력값!BU"+(rowNo+2)+",입력값!CL"+(rowNo+2)+",입력값!DC"+(rowNo+2)+")/3";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류하단폭*/
				formulaText = "입력값!BQ"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류중간폭*/
				formulaText = "입력값!CH"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류상단폭*/
				formulaText = "입력값!CY"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지상단높이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지하단높이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지상승유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지평형유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지하강유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*위험경사지조합유무*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*사면현황모암코드*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황표본토심높이*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황표본위도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*토지현황표본경도값*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*유역현황내용*/
				formulaText = "입력값!GT"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*산사태발생위험지역여부*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*산사태발생위험가능성여부*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*산사태발생보호시설여부*/
				formulaText = "IF(입력값!AE"+(rowNo+2)+"=\"유\",\"Y\",\"N\")";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*산사태발생불량림여부*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*산사태발생재난위험여부*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사평가점수*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*계류총길이*/
				formulaText = "입력값!CV"+(rowNo+2);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*조사기타주소*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*용역사업번호*/
				formulaText = "LEFT(입력값!S"+(rowNo+2)+",4)";
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*사업년도*/
				cell.setCellValue("");
				cell.setCellStyle(ValueStyle);

				cell = getCell(sheet4, rowNo, colNo++);/*산사태발생위험등급코드*/
				formulaText = "출력!CU"+(rowNo-1);
				cell.setCellFormula(formulaText);
				cell.setCellStyle(ValueStyle);

				rowNo++;
			}
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 토석류 취약지역관리대장 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet25(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		
		XSSFSheet sheet5 = wb.createSheet("취약지역관리대장");
		sheet5.setDefaultColumnWidth(17);
		sheet5.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet5, rowNo, colNo).setCellValue("이동시 삭제");
		getCell(sheet5, rowNo, colNo).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo, colNo+1).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+1).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+2).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+2).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+3).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+4).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+5).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+5).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+6).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+6).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+7).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+8).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+8).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+9).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+10).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+11).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+11).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+12).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+13).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+13).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+14).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+14).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+15).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+15).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+16).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+16).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+17).setCellValue("모두 N");
		getCell(sheet5, rowNo, colNo+17).setCellStyle(HeaderStyleGreen3);
		getCell(sheet5, rowNo, colNo+18).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+18).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+19).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+19).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+20).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+20).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+21).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+21).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+22).setCellValue("자동입력");
		getCell(sheet5, rowNo, colNo+22).setCellStyle(HeaderStyleSky);
		getCell(sheet5, rowNo, colNo+23).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo, colNo+24).setCellValue("미입력, 선택사항");
		getCell(sheet5, rowNo, colNo+24).setCellStyle(HeaderStyleGray);

		getCell(sheet5, rowNo+1, colNo).setCellValue("");
		getCell(sheet5, rowNo+1, colNo).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+1, colNo+1).setCellValue("조사대장구분코드");
		getCell(sheet5, rowNo+1, colNo+1).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+2).setCellValue("관리주체명");
		getCell(sheet5, rowNo+1, colNo+2).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+3).setCellValue("취약지역지구명");
		getCell(sheet5, rowNo+1, colNo+3).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+4).setCellValue("취약지역소유명");
		getCell(sheet5, rowNo+1, colNo+4).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+5).setCellValue("취약지역대피장소명");
		getCell(sheet5, rowNo+1, colNo+5).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+6).setCellValue("취약지역지정사유내용");
		getCell(sheet5, rowNo+1, colNo+6).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+7).setCellValue("취약지역지정위원회명");
		getCell(sheet5, rowNo+1, colNo+7).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+8).setCellValue("취약지역지정위원회심의년월일");
		getCell(sheet5, rowNo+1, colNo+8).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+9).setCellValue("취약지역지정위원회이의신청일");
		getCell(sheet5, rowNo+1, colNo+9).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+10).setCellValue("취약지역지정위원회이의신청사유");
		getCell(sheet5, rowNo+1, colNo+10).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+11).setCellValue("취약지역지정면적");
		getCell(sheet5, rowNo+1, colNo+11).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+12).setCellValue("산사태취약지역지정일");
		getCell(sheet5, rowNo+1, colNo+12).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+13).setCellValue("산사태취약지역해제일");
		getCell(sheet5, rowNo+1, colNo+13).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+14).setCellValue("취약지역해제사유내용");
		getCell(sheet5, rowNo+1, colNo+14).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+15).setCellValue("취약지역지정해제여부");
		getCell(sheet5, rowNo+1, colNo+15).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+16).setCellValue("취약지역현황내용");
		getCell(sheet5, rowNo+1, colNo+16).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+17).setCellValue("대장삭제여부");
		getCell(sheet5, rowNo+1, colNo+17).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+1, colNo+18).setCellValue("조사ID");
		getCell(sheet5, rowNo+1, colNo+18).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+19).setCellValue("지번");
		getCell(sheet5, rowNo+1, colNo+19).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+20).setCellValue("위도");
		getCell(sheet5, rowNo+1, colNo+20).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+21).setCellValue("경도");
		getCell(sheet5, rowNo+1, colNo+21).setCellStyle(HeaderStylePink2);
		getCell(sheet5, rowNo+1, colNo+22).setCellValue("취약지면적");
		getCell(sheet5, rowNo+1, colNo+22).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+23).setCellValue("취약지역조사지길이");
		getCell(sheet5, rowNo+1, colNo+23).setCellStyle(HeaderStyleGray);
		getCell(sheet5, rowNo+1, colNo+24).setCellValue("취약지역조사기타주소");
		getCell(sheet5, rowNo+1, colNo+24).setCellStyle(HeaderStyleGray);
		
		getCell(sheet5, rowNo+2, colNo).setCellValue("");
		getCell(sheet5, rowNo+2, colNo).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+1).setCellValue("VNARA_EXMNN_RGSTR_TPCD");
		getCell(sheet5, rowNo+2, colNo+1).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+2).setCellValue("VNARA_MNGME_MNBD_NM");
		getCell(sheet5, rowNo+2, colNo+2).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+3).setCellValue("VNARA_DSTRT_NM");
		getCell(sheet5, rowNo+2, colNo+3).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+4).setCellValue("VNARA_PSSSS_NM");
		getCell(sheet5, rowNo+2, colNo+4).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+5).setCellValue("VNARA_EVCTN_PLACE_NM");
		getCell(sheet5, rowNo+2, colNo+5).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+6).setCellValue("VNARA_APPNT_RSN_CONT");
		getCell(sheet5, rowNo+2, colNo+6).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+7).setCellValue("VNARA_APPNT_CMMTT_NM");
		getCell(sheet5, rowNo+2, colNo+7).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+8).setCellValue("VNARA_APPNT_CMMTT_DLBRT_DT");
		getCell(sheet5, rowNo+2, colNo+8).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+9).setCellValue("VNARA_APPNT_CMMTT_OBJCN_RQDT");
		getCell(sheet5, rowNo+2, colNo+9).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+10).setCellValue("VNARA_APPNT_CMMTT_FROBJ_RSN");
		getCell(sheet5, rowNo+2, colNo+10).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+11).setCellValue("VNARA_APPNT_AREA");
		getCell(sheet5, rowNo+2, colNo+11).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+12).setCellValue("LNDSL_VNARA_APPDT");
		getCell(sheet5, rowNo+2, colNo+12).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+13).setCellValue("LNDSL_VNARA_RLDT");
		getCell(sheet5, rowNo+2, colNo+13).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+14).setCellValue("VNARA_RLS_RSN_CONT");
		getCell(sheet5, rowNo+2, colNo+14).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+15).setCellValue("VNARA_APPNT_RLS_YN");
		getCell(sheet5, rowNo+2, colNo+15).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+16).setCellValue("VNARA_STATS_CONT");
		getCell(sheet5, rowNo+2, colNo+16).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+17).setCellValue("VNARA_RGSTR_DLT_YN");
		getCell(sheet5, rowNo+2, colNo+17).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+18).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet5, rowNo+2, colNo+18).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+19).setCellValue("VNARA_EXMNN_DTADD");
		getCell(sheet5, rowNo+2, colNo+19).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+20).setCellValue("VNARA_LCTN_LTTD_VAL");
		getCell(sheet5, rowNo+2, colNo+20).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+21).setCellValue("VNARA_LCTN_LNGTD_VAL");
		getCell(sheet5, rowNo+2, colNo+21).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+22).setCellValue("VNARA_SRZN_AREA");
		getCell(sheet5, rowNo+2, colNo+22).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+23).setCellValue("VNARA_SRZN_LNGTH");
		getCell(sheet5, rowNo+2, colNo+23).setCellStyle(HeaderStyle);
		getCell(sheet5, rowNo+2, colNo+24).setCellValue("VNARA_EXMNN_ETC_ADDR");
		getCell(sheet5, rowNo+2, colNo+24).setCellStyle(HeaderStyle);
		
		rowNo=3;
		String formulaText = "";

		if(dataList.size() > 0) {

		    //hblee(토석류 취약지역관리대장)
		    for (int i=0; i<dataList.size(); i++) {
		        colNo=0;

		        cell = getCell(sheet5, rowNo, colNo++);/*기번*/
		        formulaText = "입력값!A"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*조사대장구분코드*/
		        formulaText = "IF(입력값!B"+(rowNo+2)+"=\"산사태\",\"VNA002\",IF(입력값!B"+(rowNo+2)+"=\"토석류\",\"VNA001\",\"\"))";
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*관리주체명*/
		        formulaText = "입력값!D"+(rowNo+2)+"&\" \"&입력값!E"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지구명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*소유명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*대피장소명*/
		        formulaText = "입력값!DQ"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정사유내용*/
		        formulaText = "취약지역조사야장!CD"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정위원회명*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정위원회심의년월일*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정위원회이의신청일*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정위원회이의신청사유*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정면적*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*산사태지정일*/        
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*산사태해제일*/        
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*해제사유내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*지정해제여부*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*현황내용*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*대장삭제여부*/
		        cell.setCellValue("N");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*조사ID*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*조사상세주소*/
		        formulaText = "입력값!H"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*위치위도값*/
		        formulaText = "취약지역조사야장!AR"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*위치경도값*/
		        formulaText = "취약지역조사야장!AS"+(rowNo+1);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*조사지면적*/
		        formulaText = "입력값!AD"+(rowNo+2);
		        cell.setCellFormula(formulaText);
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역조사지길이*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        cell = getCell(sheet5, rowNo, colNo++);/*취약지역조사기타주소*/
		        cell.setCellValue("");
		        cell.setCellStyle(ValueStyle);

		        rowNo++;
		    }
		}
	}
	
	/**
	 * @param model
	 * @param wb
	 * @param req
	 * @param resp
	 * @throws Exception
	 * @description 토석류 취약지역첨부파일 시트
	 */
	@SuppressWarnings("unchecked")
	public static void buildSheet26(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
		XSSFCell cell = null;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트
		List<EgovMap> photoTagList = (List<EgovMap>) dataMap.get("photoTagList"); // 현장사진 데이터가 담긴 리스트
		
		XSSFSheet sheet6 = wb.createSheet("취약지역첨부파일");
		sheet6.setDefaultColumnWidth(17);
		sheet6.setDefaultRowHeightInPoints((float) 16.5);
		
		int rowNo=0, colNo=0;
		getCell(sheet6, rowNo, colNo).setCellValue("동영상은 1 \r\n" + "사진은 3~5");
		getCell(sheet6, rowNo, colNo).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+1).setCellValue("저장경로는 동일하게");
		getCell(sheet6, rowNo, colNo+1).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+2).setCellValue("조사ID별 동일하게");
		getCell(sheet6, rowNo, colNo+2).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+3).setCellValue("저장파일과 동일 자동입력");
		getCell(sheet6, rowNo, colNo+3).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+4).setCellValue("동영상 MP4, 사진 JPG");
		getCell(sheet6, rowNo, colNo+4).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+5).setCellValue("직접입력");
		getCell(sheet6, rowNo, colNo+5).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+6).setCellValue("");
		getCell(sheet6, rowNo, colNo+6).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+7).setCellValue("");
		getCell(sheet6, rowNo, colNo+7).setCellStyle(HeaderStylePink);
		getCell(sheet6, rowNo, colNo+8).setCellValue("직접입력");
		getCell(sheet6, rowNo, colNo+8).setCellStyle(HeaderStylePink);
		
		getCell(sheet6, rowNo+1, colNo).setCellValue("파일순번");
		getCell(sheet6, rowNo+1, colNo).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+1).setCellValue("취약지역첨부파일저장경로");
		getCell(sheet6, rowNo+1, colNo+1).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+2).setCellValue("저장파일명");
		getCell(sheet6, rowNo+1, colNo+2).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+3).setCellValue("원본파일명");
		getCell(sheet6, rowNo+1, colNo+3).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+4).setCellValue("확장자명");
		getCell(sheet6, rowNo+1, colNo+4).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+5).setCellValue("파일설명");
		getCell(sheet6, rowNo+1, colNo+5).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+6).setCellValue("파일크기");
		getCell(sheet6, rowNo+1, colNo+6).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+7).setCellValue("파일구분코드");
		getCell(sheet6, rowNo+1, colNo+7).setCellStyle(HeaderStyleRed);
		getCell(sheet6, rowNo+1, colNo+8).setCellValue("취약지역조사ID");
		getCell(sheet6, rowNo+1, colNo+8).setCellStyle(HeaderStyleRed);
		
		getCell(sheet6, rowNo+2, colNo).setCellValue("VNARA_ATTCH_FILE_SEQ");
		getCell(sheet6, rowNo+2, colNo).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+1).setCellValue("VNARA_ATTCH_FILE_STORE_CRSE");
		getCell(sheet6, rowNo+2, colNo+1).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+2).setCellValue("VNARA_ATTCH_STORE_FILE_NM");
		getCell(sheet6, rowNo+2, colNo+2).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+3).setCellValue("VNARA_ATTCH_ORGIL_FILE_NM");
		getCell(sheet6, rowNo+2, colNo+3).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+4).setCellValue("VNARA_ATTCH_FILE_EXT_NM");
		getCell(sheet6, rowNo+2, colNo+4).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+5).setCellValue("VNARA_ATTCH_FILE_DSCRT");
		getCell(sheet6, rowNo+2, colNo+5).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+6).setCellValue("VNARA_ATTCH_FILE_MGNTD");
		getCell(sheet6, rowNo+2, colNo+6).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+7).setCellValue("LNDSL_UPLD_FILE_TPCD");
		getCell(sheet6, rowNo+2, colNo+7).setCellStyle(HeaderStyle);
		getCell(sheet6, rowNo+2, colNo+8).setCellValue("VNARA_EXMNN_ID");
		getCell(sheet6, rowNo+2, colNo+8).setCellStyle(HeaderStyle);
		
		rowNo=3;

		if(photoTagList.size() > 0) {
			
		    //hblee(토석류 취약지역첨부파일)
		    for (int i=0; i<photoTagList.size(); i++) {
				String photoTag = photoTagList.get(i).get("tag").toString();
				String[] photoImg = photoTagList.get(i).get("img").toString().split("/");
				String imgext = "." + FilenameUtils.getExtension(photoImg[2]);
				int fileNum = 3;
				
				if(photoTag.contains(".")) photoTag.substring(photoTag.indexOf(".")+1);
				
				if(photoTag.equals("유출구 상부전경2")) {
					fileNum = 4;
				}else if(photoTag.equals("내부사진1")) {
					fileNum = 5;
				}else if(photoTag.equals("내부사진2")) {
					fileNum = 6;
				}
				
				colNo = 0;
				
				cell = getCell(sheet6, rowNo, colNo++);/*파일순번*/
        		cell.setCellValue(fileNum);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*취약지역첨부파일저장경로*/
        		cell.setCellValue("/webdata/upload/common/MHMS/upload/weakbook/images/");
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*저장파일명*/
        		cell.setCellValue(photoImg[2]);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*원본파일명*/
        		cell.setCellValue(photoImg[2]);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*확장자명*/
        		cell.setCellValue(imgext);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*파일설명*/
        		cell.setCellValue(photoTag);
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*파일크기*/
        		cell.setCellValue("");
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*파일구분코드*/
        		cell.setCellValue("IMAGE");
        		cell.setCellStyle(ValueStyle);

        		cell = getCell(sheet6, rowNo, colNo++);/*취약지역조사ID*/
        		cell.setCellValue("");
        		cell.setCellStyle(ValueStyle);
        		
        		rowNo++;
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
		cell.setCellType(CellType.FORMULA);
		cell.setCellValue(text);
	}

	/**
	 * Title Style
	 * 
	 * @param wb/
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
	 * Header Style1
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle(XSSFWorkbook wb,String color) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일//		
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		Font font = wb.createFont();
		font.setBold(true);
		if(color == "Yellow") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 0)));
		}else if(color == "Red") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0)));
		}else if(color == "Gray") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(217, 217, 217)));
		}else if(color == "Blue") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(180, 198, 231)));
		}else if(color == "Blue2") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(0, 176, 240)));
		}else if(color == "Orange") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 192, 0)));
		}else if(color == "Pink") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(248, 203, 173)));
		}else if(color == "Pink2") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(244, 176, 132)));
		}else if(color == "Green") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(169, 208, 142)));
		}else if(color == "Beige") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 242, 204)));
		}else if(color == "Green2") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(146, 208, 80)));
		}else if(color == "Sky") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(221, 235, 247)));
		}else if(color == "Green3") {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(226, 239, 218)));
		}else {
			cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
		}
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

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
	 * Value Style (Bold)
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getValueCellStyle2(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 데이터셀의 셀스타일
		XSSFDataFormat format = wb.createDataFormat();
		// cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setDataFormat(format.getFormat("00"));
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	/**
	 * Value Style (Bold)
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getValueCellStyle3(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 데이터셀의 셀스타일
		XSSFDataFormat format = wb.createDataFormat();
		// cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setDataFormat(format.getFormat("00.00"));
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	/**
	 * Value Style (Bold)
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getValueCellStyle4(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 데이터셀의 셀스타일
		XSSFDataFormat format = wb.createDataFormat();
		// cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setDataFormat(format.getFormat("0.0"));
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	/**
	 * Value Style (Bold)
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getValueCellStyle5(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 데이터셀의 셀스타일
		XSSFDataFormat format = wb.createDataFormat();
		// cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setDataFormat(format.getFormat("0.00"));
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		return cellStyle;
	}
	
	/**
	 * Value Convert String To Int
	 * 
	 * @param wb
	 * @return
	 */
	public static int parseInt(String value) {
		double d = Double.parseDouble(value);
		int result = (int) d;
		return result;
	}
	
	/**
	 * Value Double Type Check
	 * 
	 * @param wb
	 * @return
	 */
	public static int doubleTypeCheck(String value) {
		int result = (value.length() - value.replace(".", "").length());
		return result;
	}

}
