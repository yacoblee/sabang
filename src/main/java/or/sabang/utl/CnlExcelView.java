package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class CnlExcelView extends AbstractView {

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
	public CnlExcelView() {
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
		
		setText(getCell(sheet, rowNo, colNo), "구분");
		getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo, colNo+1));
		
		setText(getCell(sheet, rowNo, colNo+2), "행정구역");
		getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle2);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+2, colNo+7));
		
		setText(getCell(sheet, rowNo, colNo+8), "취약지현황");
		getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle3);
		getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle3);
		getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle3);
		getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle3);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+8, colNo+11));
		
		setText(getCell(sheet, rowNo, colNo+12), "조사자");
		getCell(sheet, rowNo, colNo+12).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+13).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+14).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+15).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+12, colNo+15));
		
		setText(getCell(sheet, rowNo, colNo+16), "위도");
		getCell(sheet, rowNo, colNo+16).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo, colNo+17).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo, colNo+18).setCellStyle(HeaderStyle5);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+16, colNo+18));
		
		setText(getCell(sheet, rowNo, colNo+19), "경도");
		getCell(sheet, rowNo, colNo+19).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo, colNo+20).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo, colNo+21).setCellStyle(HeaderStyle5);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+19, colNo+21));
		
		setText(getCell(sheet, rowNo, colNo+22), "조사항목");
		getCell(sheet, rowNo, colNo+22).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+23).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+24).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+25).setCellStyle(HeaderStyle6);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+22, colNo+25));
		
		setText(getCell(sheet, rowNo, colNo+26), "관리현황 요약");
		getCell(sheet, rowNo, colNo+26).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+27).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+28).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+29).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+30).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+31).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+32).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo, colNo+33).setCellStyle(HeaderStyle7);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+26, colNo+33));
		
		setText(getCell(sheet, rowNo, colNo+34), "취약지역 지정 당시 현황");
		getCell(sheet, rowNo, colNo+34).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo, colNo+35).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo, colNo+36).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo, colNo+37).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo, colNo+38).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo, colNo+39).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo, colNo+40).setCellStyle(HeaderStyle8);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+34, colNo+40));
		
		setText(getCell(sheet, rowNo, colNo+41), "해제요건");
		getCell(sheet, rowNo, colNo+41).setCellStyle(HeaderStyle14);
		getCell(sheet, rowNo, colNo+42).setCellStyle(HeaderStyle14);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+41, colNo+42));
		
		setText(getCell(sheet, rowNo, colNo+43), "해제평가");
		getCell(sheet, rowNo, colNo+43).setCellStyle(HeaderStyle9);
		getCell(sheet, rowNo, colNo+44).setCellStyle(HeaderStyle9);
		getCell(sheet, rowNo, colNo+45).setCellStyle(HeaderStyle9);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+43, colNo+45));
		
		setText(getCell(sheet, rowNo, colNo+46), "해제선정사유");
		getCell(sheet, rowNo, colNo+46).setCellStyle(HeaderStyle10);
		getCell(sheet, rowNo, colNo+47).setCellStyle(HeaderStyle10);
		getCell(sheet, rowNo, colNo+48).setCellStyle(HeaderStyle10);
		getCell(sheet, rowNo, colNo+49).setCellStyle(HeaderStyle10);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+46, colNo+49));
		
		setText(getCell(sheet, rowNo, colNo+50), "종합의견");
		getCell(sheet, rowNo, colNo+50).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo, colNo+51).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo, colNo+52).setCellStyle(HeaderStyle11);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+50, colNo+52));
		
		setText(getCell(sheet, rowNo, colNo+53), "2. 관리현황");
		getCell(sheet, rowNo, colNo+53).setCellStyle(HeaderStyle12);
		getCell(sheet, rowNo, colNo+54).setCellStyle(HeaderStyle12);
		getCell(sheet, rowNo, colNo+55).setCellStyle(HeaderStyle12);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+53, colNo+55));
		
		setText(getCell(sheet, rowNo, colNo+56), "3. 해제평가");
		getCell(sheet, rowNo, colNo+56).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo, colNo+57).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo, colNo+58).setCellStyle(HeaderStyle13);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+56, colNo+58));
		
		setText(getCell(sheet, rowNo, colNo+59), "4. 최종의견");
		getCell(sheet, rowNo, colNo+59).setCellStyle(HeaderStyle15);
		getCell(sheet, rowNo, colNo+60).setCellStyle(HeaderStyle15);
		getCell(sheet, rowNo, colNo+61).setCellStyle(HeaderStyle15);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+59, colNo+61));
		
		setText(getCell(sheet, rowNo, colNo+62), "시뮬레이션");
		getCell(sheet, rowNo, colNo+62).setCellStyle(HeaderStyle16);
		getCell(sheet, rowNo, colNo+63).setCellStyle(HeaderStyle16);
		getCell(sheet, rowNo, colNo+64).setCellStyle(HeaderStyle16);
		getCell(sheet, rowNo, colNo+65).setCellStyle(HeaderStyle16);
		getCell(sheet, rowNo, colNo+66).setCellStyle(HeaderStyle16);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+62, colNo+66));
		
		setText(getCell(sheet, rowNo, colNo+67), "사면안정");
		getCell(sheet, rowNo, colNo+67).setCellStyle(HeaderStyle17);
		getCell(sheet, rowNo, colNo+68).setCellStyle(HeaderStyle17);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+67, colNo+68));
		
//		setText(getCell(sheet, rowNo, colNo+69), "시스템정보");
//		getCell(sheet, rowNo, colNo+69).setCellStyle(HeaderStyle);
//		getCell(sheet, rowNo, colNo+70).setCellStyle(HeaderStyle);
//		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+68, colNo+69));
		
		colNo = 0;

		setText(getCell(sheet, rowNo+1, colNo+0), "기번");
		getCell(sheet, rowNo+1, colNo+0).setCellStyle(HeaderStyle1);
		setText(getCell(sheet, rowNo+1, colNo+1), "조사ID");
		getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+1, colNo+2), "시도");
		getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle2);
		setText(getCell(sheet, rowNo+1, colNo+3), "시군구");
		getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle2);
		setText(getCell(sheet, rowNo+1, colNo+4), "읍면동");
		getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle2);
		setText(getCell(sheet, rowNo+1, colNo+5), "리");
		getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle2);
		setText(getCell(sheet, rowNo+1, colNo+6), "지번");
		getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle2);
		setText(getCell(sheet, rowNo+1, colNo+7), "관리주체");
		getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+1, colNo+8), "취약지유형");
		getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle3);
		setText(getCell(sheet, rowNo+1, colNo+9), "지정년도");
		getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle3);
		setText(getCell(sheet, rowNo+1, colNo+10), "지정면적");
		getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle3);
		setText(getCell(sheet, rowNo+1, colNo+11), "지정번호");
		getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle3);
		
		setText(getCell(sheet, rowNo+1, colNo+12), "소속");
		getCell(sheet, rowNo+1, colNo+12).setCellStyle(HeaderStyle4);
		setText(getCell(sheet, rowNo+1, colNo+13), "성명");
		getCell(sheet, rowNo+1, colNo+13).setCellStyle(HeaderStyle4);
		setText(getCell(sheet, rowNo+1, colNo+14), "조사일자");
		getCell(sheet, rowNo+1, colNo+14).setCellStyle(HeaderStyle4);
		setText(getCell(sheet, rowNo+1, colNo+15), "연락처");
		getCell(sheet, rowNo+1, colNo+15).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+1, colNo+16), "도");
		getCell(sheet, rowNo+1, colNo+16).setCellStyle(HeaderStyle5);
		setText(getCell(sheet, rowNo+1, colNo+17), "분");
		getCell(sheet, rowNo+1, colNo+17).setCellStyle(HeaderStyle5);
		setText(getCell(sheet, rowNo+1, colNo+18), "초");
		getCell(sheet, rowNo+1, colNo+18).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+1, colNo+19), "도");
		getCell(sheet, rowNo+1, colNo+19).setCellStyle(HeaderStyle5);
		setText(getCell(sheet, rowNo+1, colNo+20), "분");
		getCell(sheet, rowNo+1, colNo+20).setCellStyle(HeaderStyle5);
		setText(getCell(sheet, rowNo+1, colNo+21), "초");
		getCell(sheet, rowNo+1, colNo+21).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+1, colNo+22), "산사태위험도");
		getCell(sheet, rowNo+1, colNo+22).setCellStyle(HeaderStyle6);
		setText(getCell(sheet, rowNo+1, colNo+23), "평균경사");
		getCell(sheet, rowNo+1, colNo+23).setCellStyle(HeaderStyle6);
		setText(getCell(sheet, rowNo+1, colNo+24), "임상");
		getCell(sheet, rowNo+1, colNo+24).setCellStyle(HeaderStyle6);
		setText(getCell(sheet, rowNo+1, colNo+25), "경급");
		getCell(sheet, rowNo+1, colNo+25).setCellStyle(HeaderStyle6);
		
		setText(getCell(sheet, rowNo+1, colNo+26), "사업시행 여부");
		getCell(sheet, rowNo+1, colNo+26).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+27), "사업종류");
		getCell(sheet, rowNo+1, colNo+27).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+28), "적용공법");
		getCell(sheet, rowNo+1, colNo+28).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+29), "시공년도");
		getCell(sheet, rowNo+1, colNo+29).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+30), "시설물상태");
		getCell(sheet, rowNo+1, colNo+30).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+31), "유역현황");
		getCell(sheet, rowNo+1, colNo+31).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+32), "피해이력");
		getCell(sheet, rowNo+1, colNo+32).setCellStyle(HeaderStyle7);
		setText(getCell(sheet, rowNo+1, colNo+33), "특이사항(주민의견)");
		getCell(sheet, rowNo+1, colNo+33).setCellStyle(HeaderStyle7);
		
		setText(getCell(sheet, rowNo+1, colNo+34), "당시현황");
		getCell(sheet, rowNo+1, colNo+34).setCellStyle(HeaderStyle8);
		setText(getCell(sheet, rowNo+1, colNo+35), "지정사유");
		getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle8);
		setText(getCell(sheet, rowNo+1, colNo+36), "사업종가능");
		getCell(sheet, rowNo+1, colNo+36).setCellStyle(HeaderStyle8);
		setText(getCell(sheet, rowNo+1, colNo+37), "가능여부");
		getCell(sheet, rowNo+1, colNo+37).setCellStyle(HeaderStyle8);
		setText(getCell(sheet, rowNo+1, colNo+38), "선정사유");
		getCell(sheet, rowNo+1, colNo+38).setCellStyle(HeaderStyle8);
		setText(getCell(sheet, rowNo+1, colNo+39), "구역설정");
		getCell(sheet, rowNo+1, colNo+39).setCellStyle(HeaderStyle8);
		setText(getCell(sheet, rowNo+1, colNo+40), "당시종합의견");
		getCell(sheet, rowNo+1, colNo+40).setCellStyle(HeaderStyle8);
		
		setText(getCell(sheet, rowNo+1, colNo+41), "근거");
		getCell(sheet, rowNo+1, colNo+41).setCellStyle(HeaderStyle14);
		setText(getCell(sheet, rowNo+1, colNo+42), "세부사항");
		getCell(sheet, rowNo+1, colNo+42).setCellStyle(HeaderStyle14);
		
		setText(getCell(sheet, rowNo+1, colNo+43), "재해발생 여부");
		getCell(sheet, rowNo+1, colNo+43).setCellStyle(HeaderStyle9);
		setText(getCell(sheet, rowNo+1, colNo+44), "사면 계류 상태");
		getCell(sheet, rowNo+1, colNo+44).setCellStyle(HeaderStyle9);
		setText(getCell(sheet, rowNo+1, colNo+45), "안정해석 결과");
		getCell(sheet, rowNo+1, colNo+45).setCellStyle(HeaderStyle9);
		
		setText(getCell(sheet, rowNo+1, colNo+46), "1 사업시행등으로\r\n"+"지정목적 달성\r\n");
		getCell(sheet, rowNo+1, colNo+46).setCellStyle(HeaderStyle10);
		setText(getCell(sheet, rowNo+1, colNo+47), "2 개발, 지형변화로\r\n"+"위험요인 제거\r\n");
		getCell(sheet, rowNo+1, colNo+47).setCellStyle(HeaderStyle10);
		setText(getCell(sheet, rowNo+1, colNo+48), "3 보호시설 이주로\r\n"+"인명피해 우려 해소\r\n");
		getCell(sheet, rowNo+1, colNo+48).setCellStyle(HeaderStyle10);
		setText(getCell(sheet, rowNo+1, colNo+49), "4 그밖에 발주처에서\r\n"+"인정하는 경우\r\n");
		getCell(sheet, rowNo+1, colNo+49).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+1, colNo+50), "종합의견1");
		getCell(sheet, rowNo+1, colNo+50).setCellStyle(HeaderStyle11);
		setText(getCell(sheet, rowNo+1, colNo+51), "종합의견2");
		getCell(sheet, rowNo+1, colNo+51).setCellStyle(HeaderStyle11);
		setText(getCell(sheet, rowNo+1, colNo+52), "해제여부");
		getCell(sheet, rowNo+1, colNo+52).setCellStyle(HeaderStyle11);
		
		setText(getCell(sheet, rowNo+1, colNo+53), "사업 및 유역현황");
		getCell(sheet, rowNo+1, colNo+53).setCellStyle(HeaderStyle12);
		setText(getCell(sheet, rowNo+1, colNo+54), "피해 이력 및 유역변화");
		getCell(sheet, rowNo+1, colNo+54).setCellStyle(HeaderStyle12);
		setText(getCell(sheet, rowNo+1, colNo+55), "주민의견 및\r\n"+"기타 특이사항\r\n");
		getCell(sheet, rowNo+1, colNo+55).setCellStyle(HeaderStyle12);
		
		setText(getCell(sheet, rowNo+1, colNo+56), "나. 사면\r\n"+"계류상태");
		getCell(sheet, rowNo+1, colNo+56).setCellStyle(HeaderStyle13);
		setText(getCell(sheet, rowNo+1, colNo+57), "가. 재해발생\r\n"+"여부");
		getCell(sheet, rowNo+1, colNo+57).setCellStyle(HeaderStyle13);
		setText(getCell(sheet, rowNo+1, colNo+58), "나. 사면\r\n"+"계류상태2");
		getCell(sheet, rowNo+1, colNo+58).setCellStyle(HeaderStyle13);
		
		setText(getCell(sheet, rowNo+1, colNo+59), "의견1");
		getCell(sheet, rowNo+1, colNo+59).setCellStyle(HeaderStyle15);
		setText(getCell(sheet, rowNo+1, colNo+60), "의견2");
		getCell(sheet, rowNo+1, colNo+60).setCellStyle(HeaderStyle15);
		setText(getCell(sheet, rowNo+1, colNo+61), "의견3");
		getCell(sheet, rowNo+1, colNo+61).setCellStyle(HeaderStyle15);
		
		setText(getCell(sheet, rowNo+1, colNo+62), "1회");
		getCell(sheet, rowNo+1, colNo+62).setCellStyle(HeaderStyle16);
		setText(getCell(sheet, rowNo+1, colNo+63), "정지조건");
		getCell(sheet, rowNo+1, colNo+63).setCellStyle(HeaderStyle16);
		setText(getCell(sheet, rowNo+1, colNo+64), "가중치");
		getCell(sheet, rowNo+1, colNo+64).setCellStyle(HeaderStyle16);
		setText(getCell(sheet, rowNo+1, colNo+65), "토석류량");
		getCell(sheet, rowNo+1, colNo+65).setCellStyle(HeaderStyle16);
		setText(getCell(sheet, rowNo+1, colNo+66), "저감여부");
		getCell(sheet, rowNo+1, colNo+66).setCellStyle(HeaderStyle16);
		
		setText(getCell(sheet, rowNo+1, colNo+67), "충족여부");
		getCell(sheet, rowNo+1, colNo+67).setCellStyle(HeaderStyle17);
		setText(getCell(sheet, rowNo+1, colNo+68), "점수");
		getCell(sheet, rowNo+1, colNo+68).setCellStyle(HeaderStyle17);
		
//		setText(getCell(sheet, rowNo+1, colNo+68), "공유방ID");
//		setText(getCell(sheet, rowNo+1, colNo+69), "시스템계정");
		
		colNo = 0;
		rowNo = 2;
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {

				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sn").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);

				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyId").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);

				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svySd").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);

				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svySgg").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyEmd").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyRi").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyJibun").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);

				String region1 = dataList.get(i).get("region1").toString();
				String region2 = dataList.get(i).get("region2").toString();
				
				if(region2.isEmpty()) {
					setText(getCell(sheet, rowNo, colNo), region1);
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				}else if(!region1.isEmpty()){
					setText(getCell(sheet, rowNo, colNo), region1+", "+region2);
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				}else {
					setText(getCell(sheet, rowNo, colNo), region2);
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle2);
				}
				
				
				String svyType = dataList.get(i).get("svyType").toString();
				
				setText(getCell(sheet, rowNo, colNo), svyType);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnYear").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnArea").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnNo").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle3);
				
				setText(getCell(sheet, rowNo, colNo), "한국치산기술협회"); // 일단은 한국치산협회로 고정
				sheet.setColumnWidth((short)12, (short)5500);
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyUser").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyDt").toString());
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				setText(getCell(sheet, rowNo, colNo), "043-279-5320");	//일단고정
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle4);
				
				String dms = dataList.get(i).get("dms").toString();
				String[] regDmg = dms.split("\\s");
				
				setText(getCell(sheet, rowNo, colNo), regDmg[0].replaceAll("[^0-9]", ""));
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				setText(getCell(sheet, rowNo, colNo), regDmg[1].replaceAll("[^0-9]", ""));
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				setText(getCell(sheet, rowNo, colNo), regDmg[2].replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9,. ]", ""));
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				
				setText(getCell(sheet, rowNo, colNo), regDmg[3].replaceAll("[^0-9]", ""));
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				setText(getCell(sheet, rowNo, colNo), regDmg[4].replaceAll("[^0-9]", ""));
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);
				setText(getCell(sheet, rowNo, colNo), regDmg[5].replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9,. ]", ""));
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle5);

				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lndsldGrde").toString());	//산사태위험도
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slopeAvg").toString());	//평균경사
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frtpType").toString());	//임상
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dmclsType").toString());	//경급
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle6);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("bizOpertnAt").toString());	//사업시행여부
//				setText(getCell(sheet, rowNo, colNo), "");	//사업시행여부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("bizType").toString());	//사업종류
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("applcEgnerMhd").toString());	//적용공법
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnstrYear").toString());	//시공년도
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fctSttus").toString());	//시설물상태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dgrSttus").toString()); //유역현황
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dmgeHist").toString());	// 피해이력
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("speclNote").toString());	// 특이사항(주민의견)
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle7);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("weakAppnSttus").toString()); // 당시현황
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnreSn").toString()); // 지정사유
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("weakAppnBsType").toString());	// 사업종가능
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("weakAppnPosYn").toString());		//가능여부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("weakAppnSltnHy").toString());	//선정사유 
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("weakAppnAreaSet").toString());	// 구역설정
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("weakAppnMstOpn").toString());	// 당시종합의견
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle8);
				
				
				List<String> cnlBasisList = new ArrayList<String>();
				
				if(dataList.get(i).get("cnlSlctRn1") != null) {
					String cnlSlctRn1 = dataList.get(i).get("cnlSlctRn1").toString();
					if(cnlSlctRn1.equals("1")) cnlBasisList.add("제13조제2항제1호");
				}
				if(dataList.get(i).get("cnlSlctRn2") != null) {
					String cnlSlctRn2 = dataList.get(i).get("cnlSlctRn2").toString();
					if(cnlSlctRn2.equals("2")) cnlBasisList.add("제13조제2항제2호");
				}
				if(dataList.get(i).get("cnlSlctRn3") != null) {
					String cnlSlctRn3 = dataList.get(i).get("cnlSlctRn3").toString();
					if(cnlSlctRn3.equals("3")) cnlBasisList.add("제13조제2항제3호");
				}
				if(dataList.get(i).get("cnlSlctRn4") != null) {
					String cnlSlctRn4 = dataList.get(i).get("cnlSlctRn4").toString();
					if(cnlSlctRn4.equals("4")) cnlBasisList.add("제13조제2항제4호");
				}
				
				String cnlBasis = StringUtils.join(cnlBasisList, ", ");
				
				setText(getCell(sheet, rowNo, colNo), cnlBasis);	// 해제요건 근거
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle14);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("detailMatter").toString());	// 해제요건 세부사항
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle14);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlevl1").toString());	// 재해발생여부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle9);
				
				if(svyType.equals("산사태")) {
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlevl2").toString());	// 사면상태
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle9);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlevl3").toString());	// 안정해석결과
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle9);
				}else {
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlevl2").toString());	// 계류상태
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle9);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlevl3").toString());	// 시뮬레이션결과
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle9);
				}
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlSlctRn1").toString());	// 해제선정사유1
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle10);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlSlctRn2").toString());	// 해제선정사유2
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle10);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlSlctRn3").toString());	// 해제선정사유3
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle10);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlSlctRn4").toString());	// 해제선정사유4
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle10);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mstOpinion1").toString());	// 종합의견1
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle11);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mstOpinion2").toString());	// 종합의견2
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle11);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnlYn").toString()); // 해제여부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle11);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("bizNdDgrSttus").toString());	// 사업 및 유역현황
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle12);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dmgHistNdDgrChag").toString());	// 피해이력 및 유역변화
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle12);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("hbtOpnNdEtcMatter").toString());	// 주민의견 및 기타 특이사항
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle12);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("relisEvlsMrngSttus1").toString()); // 사면계류상태(자동입력)
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle13);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dsstrOccrrncAt").toString()); // 가. 재해발생여부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle13);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("relisEvlsMrngSttus2").toString()); // 나. 사면계류상태2
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle13);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString()); // 의견1
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString()); // 의견2
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString()); // 의견3
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("onedebrisflow").toString()); // 1회
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle16);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("stopCnd").toString()); // 정지조건
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle16);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("wghtVal").toString()); // 가중치
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle16);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("alldebrisflow").toString()); // 토석류량
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle16);
				
				if(svyType.equals("산사태")) {
					setText(getCell(sheet, rowNo, colNo), ""); // 저감여부	
				}else {
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("reducAt").toString()); // 저감여부
				}
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle16);
				
				String stableIntrprtScore = dataList.get(i).get("stableIntrprtScore").toString();
				if(stableIntrprtScore.equals("30")) {
					setText(getCell(sheet, rowNo, colNo), "부"); // 사면안정 충적여부
				}else if(stableIntrprtScore.equals("0")){
					setText(getCell(sheet, rowNo, colNo), "가"); // 사면안정 충적여부
				}else {
					setText(getCell(sheet, rowNo, colNo), ""); // 사면안정 충적여부
				}
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle17);
				
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("stableIntrprtScore").toString()); // 사면안정 점수
				getCell(sheet, rowNo++, colNo).setCellStyle(HeaderStyle17);
				
				colNo = 0;
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
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(198, 224, 180)));
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

}
