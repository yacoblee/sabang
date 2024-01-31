package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import com.clipsoft.org.apache.poi.ss.usermodel.Cell;
import com.clipsoft.org.apache.poi.ss.usermodel.DataFormatter;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class LcpExcelView extends AbstractView {

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
	private static XSSFCellStyle ValueStyle = null;
	private static XSSFCellStyle ValueStyle2 = null;

	/**
	 * Default Constructor. Sets the content type of the view for excel files.
	 */
	public LcpExcelView() {
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

		HeaderStyle = getHeaderCellStyle(workbook);
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
		ValueStyle = getValueCellStyle(workbook);
		ValueStyle2 = getValueCellStyle2(workbook);

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
			response.setHeader("Content-Disposition", "attachment;filename=" + sFilename + ".xlsx;");
		} else {
			sFilename = new String(sFilename.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + sFilename + ".xlsx\"");
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
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		evaluator.evaluate(cell);
		
		String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름

		List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트

		XSSFSheet sheet = wb.createSheet(sheetNm);
		sheet.setDefaultColumnWidth(14);
		Row row = sheet.createRow(1);
		//row.setHeight((short)1000);
		
		int rowNo = 0, colNo = 0;
		

		setText(getCell(sheet, rowNo, colNo), "22년\r\n"+"고유\r\n"+"번호");
		getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(0, 2800);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo, colNo));
		
		setText(getCell(sheet, rowNo, colNo+1), "번호\r\n"+"(PNU)");
		getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+1).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+1).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(1, 1700);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+1, colNo+1));
		
		setText(getCell(sheet, rowNo, colNo+2), "조사\r\n"+"번호");
		getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+2).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+2).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+2, colNo+2));
		
		setText(getCell(sheet, rowNo, colNo+3), "중복\r\n"+"여부\r\n"+"(진입불가)");
		getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+3).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+3).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(3, 2500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+3, colNo+3));
		
		setText(getCell(sheet, rowNo, colNo+4), "물리탐사");
		getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+4).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+4).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(4, 5000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+4, colNo+4));
		
		setText(getCell(sheet, rowNo, colNo+5), "시도");
		getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+5).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+5).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(5, 4000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+5, colNo+5));
		
		setText(getCell(sheet, rowNo, colNo+6), "시군구");
		getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+6).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+6).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(6, 3500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+6, colNo+6));
		
		setText(getCell(sheet, rowNo, colNo+7), "읍면동");
		getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+7).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+7).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(7, 3500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+7, colNo+7));
		
		setText(getCell(sheet, rowNo, colNo+8), "리");
		getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+8).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+8).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(8, 3500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+8, colNo+8));
		
		setText(getCell(sheet, rowNo, colNo+9), "지번");
		getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+9).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+9).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(9, 3500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+9, colNo+9));
		
		setText(getCell(sheet, rowNo, colNo+10), "주소");
		getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+10).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+10).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(10, 14000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+10, colNo+10));
		
		setText(getCell(sheet, rowNo, colNo+11), "위치정보");
		getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+12).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+13).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+14).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+15).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+16).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+17).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+11, colNo+17));
		
		setText(getCell(sheet, rowNo+1, colNo+11), "북위");
		getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+12).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+13).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+11).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+12).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+13).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+11, colNo+13));
		
		setText(getCell(sheet, rowNo+1, colNo+14), "동경");
		getCell(sheet, rowNo+1, colNo+14).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+15).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+16).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+14).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+15).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+16).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+14, colNo+16));
		
		setText(getCell(sheet, rowNo+1, colNo+17), "좌표");
		getCell(sheet, rowNo+1, colNo+17).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+17).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+17).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(17, 10000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+17, colNo+17));
		
		setText(getCell(sheet, rowNo+3, colNo+11), "도");
		sheet.setColumnWidth(11, 2000);
		getCell(sheet, rowNo+3, colNo+11).setCellStyle(HeaderStyle1);		
		
		setText(getCell(sheet, rowNo+3, colNo+12), "분");
		sheet.setColumnWidth(12, 2000);
		getCell(sheet, rowNo+3, colNo+12).setCellStyle(HeaderStyle1);		
		
		setText(getCell(sheet, rowNo+3, colNo+13), "초");
		sheet.setColumnWidth(13, 2500);
		getCell(sheet, rowNo+3, colNo+13).setCellStyle(HeaderStyle1);		
		
		setText(getCell(sheet, rowNo+3, colNo+14), "도");
		sheet.setColumnWidth(14, 2000);
		getCell(sheet, rowNo+3, colNo+14).setCellStyle(HeaderStyle1);		
		
		setText(getCell(sheet, rowNo+3, colNo+15), "분");
		sheet.setColumnWidth(15, 2000);
		getCell(sheet, rowNo+3, colNo+15).setCellStyle(HeaderStyle1);		
		
		setText(getCell(sheet, rowNo+3, colNo+16), "초");
		sheet.setColumnWidth(16, 2500);
		getCell(sheet, rowNo+3, colNo+16).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo, colNo+18), "조사일자");
		getCell(sheet, rowNo, colNo+18).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+18).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+18).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+18).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(18, 8000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+18, colNo+18));
		
		setText(getCell(sheet, rowNo, colNo+19), "조사자");
		getCell(sheet, rowNo, colNo+19).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+20).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+19, colNo+20));
		
		setText(getCell(sheet, rowNo+1, colNo+19), "부서명");
		getCell(sheet, rowNo+1, colNo+19).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+19).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+19).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(19, 4000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+19, colNo+19));
		
		setText(getCell(sheet, rowNo+1, colNo+20), "조사자");
		getCell(sheet, rowNo+1, colNo+20).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+20).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+20).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(20, 10000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+20, colNo+20));
		
		setText(getCell(sheet, rowNo, colNo+21), "고도\r\n" + "(m)");
		getCell(sheet, rowNo, colNo+21).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+21).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+21).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+21).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(19, 6500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+21, colNo+21));
		
		setText(getCell(sheet, rowNo, colNo+22), "");
		getCell(sheet, rowNo, colNo+22).setCellStyle(HeaderStyle3);
		getCell(sheet, rowNo, colNo+23).setCellStyle(HeaderStyle3);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+22, colNo+23));
		
		setText(getCell(sheet, rowNo+1, colNo+22), "");
		getCell(sheet, rowNo+1, colNo+22).setCellStyle(HeaderStyle8);
		getCell(sheet, rowNo+2, colNo+22).setCellStyle(HeaderStyle8);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+22, colNo+22));
		
		setText(getCell(sheet, rowNo+3, colNo+22), "판정점수");
		sheet.setColumnWidth(22, 4875);
		getCell(sheet, rowNo+3, colNo+22).setCellStyle(HeaderStyle3);
		
		setText(getCell(sheet, rowNo+1, colNo+23), "판정표등급");
		getCell(sheet, rowNo+1, colNo+23).setCellStyle(HeaderStyle9);
		getCell(sheet, rowNo+2, colNo+23).setCellStyle(HeaderStyle9);
		getCell(sheet, rowNo+3, colNo+23).setCellStyle(HeaderStyle3);
		sheet.setColumnWidth(23, 4875);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+23, colNo+23));
		
		setText(getCell(sheet, rowNo, colNo+24), "1. 지질 특성");
		getCell(sheet, rowNo, colNo+24).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+25).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+26).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+27).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+28).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+29).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+30).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+31).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+32).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+33).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+34).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+35).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+36).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+37).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+38).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+39).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+40).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+41).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+42).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+43).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+44).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+45).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+46).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+47).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+48).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+49).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+50).setCellStyle(HeaderStyle4);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+24, colNo+50));
		
		setText(getCell(sheet, rowNo+1, colNo+24), "");
		getCell(sheet, rowNo+1, colNo+24).setCellStyle(HeaderStyle10);
		getCell(sheet, rowNo+2, colNo+24).setCellStyle(HeaderStyle10);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+24, colNo+24));
		
		setText(getCell(sheet, rowNo+3, colNo+24), "판정점수");
		sheet.setColumnWidth(24, 4875);
		getCell(sheet, rowNo+3, colNo+24).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+1, colNo+25), "주 구성암석");
		getCell(sheet, rowNo+1, colNo+25).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+2, colNo+25).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+3, colNo+25).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(25, 4875);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+25, colNo+25));
		
		setText(getCell(sheet, rowNo+1, colNo+26), "타 지층\r\n" + "및 관입암\r\n" +	"유/무");
		getCell(sheet, rowNo+1, colNo+26).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+26).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+26).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(26, 4700);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+26, colNo+26));
		
		setText(getCell(sheet, rowNo+1, colNo+27), "");
		getCell(sheet, rowNo+1, colNo+27).setCellStyle(HeaderStyle10);
		getCell(sheet, rowNo+2, colNo+27).setCellStyle(HeaderStyle10);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+27, colNo+27));
		
		setText(getCell(sheet, rowNo+3, colNo+27), "판정점수");
		sheet.setColumnWidth(27, 4875);
		getCell(sheet, rowNo+3, colNo+27).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+1, colNo+28), "암석풍화");
		getCell(sheet, rowNo+1, colNo+28).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+2, colNo+28).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+3, colNo+28).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(28, 4000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+28, colNo+28));
		
		setText(getCell(sheet, rowNo+1, colNo+29), "지질구조");
		getCell(sheet, rowNo+1, colNo+29).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+30).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+29, colNo+30));
		
		setText(getCell(sheet, rowNo+2, colNo+29), "단층");
		getCell(sheet, rowNo+2, colNo+29).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+29).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(29, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+29, colNo+29));
		
		setText(getCell(sheet, rowNo+2, colNo+30), "습곡형");
		getCell(sheet, rowNo+2, colNo+30).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+30).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(30, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+30, colNo+30));
		
		setText(getCell(sheet, rowNo+1, colNo+31), "불연속면 조사  [(불연속면의 종류) & 주향 / 경사]");
		getCell(sheet, rowNo+1, colNo+31).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+32).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+33).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+34).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+36).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+37).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+38).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+39).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+40).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+41).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+42).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+43).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+44).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+45).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+31, colNo+45));
		
		setText(getCell(sheet, rowNo+2, colNo+31), "1 (     )");
		getCell(sheet, rowNo+2, colNo+31).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+32).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+33).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+31, colNo+33));
		
		setText(getCell(sheet, rowNo+2, colNo+34), "2 (     )");
		getCell(sheet, rowNo+2, colNo+34).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+35).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+36).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+34, colNo+36));
		
		setText(getCell(sheet, rowNo+2, colNo+37), "3 (     )");
		getCell(sheet, rowNo+2, colNo+37).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+38).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+39).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+37, colNo+39));
		
		setText(getCell(sheet, rowNo+2, colNo+40), "4 (     )");
		getCell(sheet, rowNo+2, colNo+40).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+41).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+42).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+40, colNo+42));
		
		setText(getCell(sheet, rowNo+2, colNo+43), "5 (     )");
		getCell(sheet, rowNo+2, colNo+43).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+44).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+45).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+43, colNo+45));
		
		setText(getCell(sheet, rowNo+3, colNo+31), "종류");
		sheet.setColumnWidth(31, 3000);
		getCell(sheet, rowNo+3, colNo+31).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+32), "주향");
		sheet.setColumnWidth(32, 3000);
		getCell(sheet, rowNo+3, colNo+32).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+33), "경사");
		sheet.setColumnWidth(33, 3000);
		getCell(sheet, rowNo+3, colNo+33).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+34), "종류");
		sheet.setColumnWidth(34, 3000);
		getCell(sheet, rowNo+3, colNo+34).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+35), "주향");
		sheet.setColumnWidth(35, 3000);
		getCell(sheet, rowNo+3, colNo+35).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+36), "경사");
		sheet.setColumnWidth(36, 3000);
		getCell(sheet, rowNo+3, colNo+36).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+37), "종류");
		sheet.setColumnWidth(37, 3000);
		getCell(sheet, rowNo+3, colNo+37).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+38), "주향");
		sheet.setColumnWidth(38, 3000);
		getCell(sheet, rowNo+3, colNo+38).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+39), "경사");
		sheet.setColumnWidth(39, 3000);
		getCell(sheet, rowNo+3, colNo+39).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+40), "종류");
		sheet.setColumnWidth(40, 3000);
		getCell(sheet, rowNo+3, colNo+40).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+41), "주향");
		sheet.setColumnWidth(41, 3000);
		getCell(sheet, rowNo+3, colNo+41).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+42), "경사");
		sheet.setColumnWidth(42, 3000);
		getCell(sheet, rowNo+3, colNo+42).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+43), "종류");
		sheet.setColumnWidth(43, 3000);
		getCell(sheet, rowNo+3, colNo+43).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+44), "주향");
		sheet.setColumnWidth(44, 3000);
		getCell(sheet, rowNo+3, colNo+44).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+3, colNo+45), "경사");
		sheet.setColumnWidth(45, 3000);
		getCell(sheet, rowNo+3, colNo+45).setCellStyle(HeaderStyle5);
		
		setText(getCell(sheet, rowNo+1, colNo+46), "불연속면\r\n" + "방향수");
		getCell(sheet, rowNo+1, colNo+46).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+46).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+46).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(46, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+46, colNo+46));
		
		setText(getCell(sheet, rowNo+1, colNo+47), "");
		getCell(sheet, rowNo+1, colNo+47).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+2, colNo+47), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+47).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+47).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(47, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+47, colNo+47));
		
		setText(getCell(sheet, rowNo+1, colNo+48), "불연속면과\r\n"+"사면의 방향성");
		getCell(sheet, rowNo+1, colNo+48).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+2, colNo+48).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+48).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(48, 3300);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+48, colNo+48));
		
		setText(getCell(sheet, rowNo+1, colNo+49), "");
		getCell(sheet, rowNo+1, colNo+49).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+2, colNo+49), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+49).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+49).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(49, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+49, colNo+49));
		
		setText(getCell(sheet, rowNo+1, colNo+50), "불연속면\r\n"+"간격(cm)");
		getCell(sheet, rowNo+1, colNo+50).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+2, colNo+50).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+50).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(50, 3100);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+50, colNo+50));
		
		setText(getCell(sheet, rowNo, colNo+51), "2. 토양 특성");
		getCell(sheet, rowNo, colNo+51).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+52).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+53).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+54).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+55).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+56).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+57).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+58).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+59).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+60).setCellStyle(HeaderStyle1);		
		getCell(sheet, rowNo, colNo+61).setCellStyle(HeaderStyle1);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+51, colNo+61));
		
		setText(getCell(sheet, rowNo+1, colNo+51), "토양형");
		getCell(sheet, rowNo+1, colNo+51).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+51).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+51).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(51, 5000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+51, colNo+51));
		
		setText(getCell(sheet, rowNo+1, colNo+52), "");
		getCell(sheet, rowNo+1, colNo+52).setCellStyle(HeaderStyle12);
		
		setText(getCell(sheet, rowNo+2, colNo+52), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+52).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+52).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(52, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+52, colNo+52));
		
		setText(getCell(sheet, rowNo+1, colNo+53), "토심(cm)\r\n"+"B층까지\r\n"+"깊이");
		getCell(sheet, rowNo+1, colNo+53).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+2, colNo+53).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+53).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(53, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+53, colNo+53));
		
		setText(getCell(sheet, rowNo+1, colNo+54), "");
		getCell(sheet, rowNo+1, colNo+54).setCellStyle(HeaderStyle12);
		
		setText(getCell(sheet, rowNo+2, colNo+54), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+54).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+54).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(54, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+54, colNo+54));
		
		setText(getCell(sheet, rowNo+1, colNo+55), "토성B층\r\n"+"기준\r\n"+"(약 30cm부근)");
		getCell(sheet, rowNo+1, colNo+55).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+2, colNo+55).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+55).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(55, 3700);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+55, colNo+55));
		
		setText(getCell(sheet, rowNo+1, colNo+56), "토양구조\r\n"+"B층 기준");
		getCell(sheet, rowNo+1, colNo+56).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+56).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+56).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(56, 2400);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+56, colNo+56));
		
		setText(getCell(sheet, rowNo+1, colNo+57), "");
		getCell(sheet, rowNo+1, colNo+57).setCellStyle(HeaderStyle12);
		
		setText(getCell(sheet, rowNo+2, colNo+57), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+57).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+57).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(57, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+57, colNo+57));
		
		setText(getCell(sheet, rowNo+1, colNo+58), "토양수분\r\n"+"상태");
		getCell(sheet, rowNo+1, colNo+58).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+2, colNo+58).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+58).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(58, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+58, colNo+58));
		
		setText(getCell(sheet, rowNo+1, colNo+59), "암석\r\n"+"노출도(%)");
		getCell(sheet, rowNo+1, colNo+59).setCellStyle(HeaderStyle);
		getCell(sheet, rowNo+2, colNo+59).setCellStyle(HeaderStyle);
		getCell(sheet, rowNo+3, colNo+59).setCellStyle(HeaderStyle);
		sheet.setColumnWidth(58, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+59, colNo+59));
		
		setText(getCell(sheet, rowNo+1, colNo+60), "");
		getCell(sheet, rowNo+1, colNo+60).setCellStyle(HeaderStyle12);
		
		setText(getCell(sheet, rowNo+2, colNo+60), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+60).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+60).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(60, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+60, colNo+60));
		
		setText(getCell(sheet, rowNo+1, colNo+61), "너덜(talus)\r\n"+"유/무");
		getCell(sheet, rowNo+1, colNo+61).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+2, colNo+61).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+61).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(61, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+61, colNo+61));
		
		setText(getCell(sheet, rowNo, colNo+62), "지형특성");
		getCell(sheet, rowNo, colNo+62).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+63).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+64).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+65).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+66).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+67).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+68).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+69).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+70).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+71).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+72).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+73).setCellStyle(HeaderStyle4);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+62, colNo+73));
		
		setText(getCell(sheet, rowNo+1, colNo+62), "지형구분");
		getCell(sheet, rowNo+1, colNo+62).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+63).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+64).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+62, colNo+64));
		
		setText(getCell(sheet, rowNo+2, colNo+62), "최대\r\n"+"높이\r\n"+"(m)");
		getCell(sheet, rowNo+2, colNo+62).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+62).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(62, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+62, colNo+62));
		
		setText(getCell(sheet, rowNo+2, colNo+63), "");
		getCell(sheet, rowNo+2, colNo+63).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+3, colNo+63), "판정\r\n"+"점수");
		sheet.setColumnWidth(63, 2350);
		getCell(sheet, rowNo+3, colNo+63).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+2, colNo+64), "위치");
		getCell(sheet, rowNo+2, colNo+64).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+3, colNo+64).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(63, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+64, colNo+64));
		
		setText(getCell(sheet, rowNo+1, colNo+65), "조사지역의 위치");
		getCell(sheet, rowNo+1, colNo+65).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+66).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+65, colNo+66));
		
		setText(getCell(sheet, rowNo+2, colNo+65), "( )부\r\n" + "능선 / 10");
		getCell(sheet, rowNo+2, colNo+65).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+65).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(65, 2500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+65, colNo+65));
		
		setText(getCell(sheet, rowNo+2, colNo+66), "위치");
		getCell(sheet, rowNo+2, colNo+66).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+66).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(66, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+66, colNo+66));
		
		setText(getCell(sheet, rowNo+1, colNo+67), "");
		getCell(sheet, rowNo+1, colNo+67).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+2, colNo+67), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+67).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+67).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(67, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+67, colNo+67));
		
		setText(getCell(sheet, rowNo+1, colNo+68), "평면형\r\n"+"(수평적)");
		getCell(sheet, rowNo+1, colNo+68).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+2, colNo+68).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+68).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(68, 2500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+68, colNo+68));
		
		setText(getCell(sheet, rowNo+1, colNo+69), "");
		getCell(sheet, rowNo+1, colNo+69).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+2, colNo+69), "판정\r\n"+"점수");
		getCell(sheet, rowNo+2, colNo+69).setCellStyle(HeaderStyle2);
		getCell(sheet, rowNo+3, colNo+69).setCellStyle(HeaderStyle2);
		sheet.setColumnWidth(69, 2350);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+69, colNo+69));
		
		setText(getCell(sheet, rowNo+1, colNo+70), "종단면형\r\n"+"(수직적)");
		getCell(sheet, rowNo+1, colNo+70).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+2, colNo+70).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+70).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(69, 2500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+70, colNo+70));
		
		setText(getCell(sheet, rowNo+1, colNo+71), "경사");
		getCell(sheet, rowNo+1, colNo+71).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+72).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+73).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+71, colNo+73));
		
		setText(getCell(sheet, rowNo+2, colNo+71), "");
		getCell(sheet, rowNo+2, colNo+71).setCellStyle(HeaderStyle10);
		
		setText(getCell(sheet, rowNo+3, colNo+71), "판정\r\n"+"점수");
		sheet.setColumnWidth(71, 2350);
		getCell(sheet, rowNo+3, colNo+71).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+2, colNo+72), "경사\r\n"+"범위");
		getCell(sheet, rowNo+2, colNo+72).setCellStyle(HeaderStyle11);
		getCell(sheet, rowNo+3, colNo+72).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(72, 2500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+72, colNo+72));
		
		setText(getCell(sheet, rowNo+2, colNo+73), "평균\r\n"+"경사");
		getCell(sheet, rowNo+2, colNo+73).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+73).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(73, 2500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+73, colNo+73));
		
		setText(getCell(sheet, rowNo, colNo+74), "수리특성");
		getCell(sheet, rowNo, colNo+74).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+75).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+76).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+74, colNo+76));
		
		setText(getCell(sheet, rowNo+1, colNo+74), "상류로부터 지하수 유입 가능성");
		getCell(sheet, rowNo+1, colNo+74).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+1, colNo+75), "하류계류의 유무");
		getCell(sheet, rowNo+1, colNo+75).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+1, colNo+76), "샘, 소, 저수지 유무");
		getCell(sheet, rowNo+1, colNo+76).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+2, colNo+74), "유/무");
		getCell(sheet, rowNo+2, colNo+74).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+74).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(74, 7500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+74, colNo+74));
		
		setText(getCell(sheet, rowNo+2, colNo+75), "유/무");
		getCell(sheet, rowNo+2, colNo+75).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+75).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(75, 4300);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+75, colNo+75));
		
		setText(getCell(sheet, rowNo+2, colNo+76), "유/무");
		getCell(sheet, rowNo+2, colNo+76).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+76).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(76, 4700);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+3, colNo+76, colNo+76));
		
		setText(getCell(sheet, rowNo, colNo+77), "산림특성");
		getCell(sheet, rowNo, colNo+77).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+78).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+77, colNo+78));
		
		setText(getCell(sheet, rowNo+1, colNo+77), "임상");
		getCell(sheet, rowNo+1, colNo+77).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+77).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+77).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(77, 4000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+77, colNo+77));
		
		setText(getCell(sheet, rowNo+1, colNo+78), "주요수종");
		getCell(sheet, rowNo+1, colNo+78).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+78).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+3, colNo+78).setCellStyle(HeaderStyle4);
		sheet.setColumnWidth(78, 12500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+78, colNo+78));
		
		setText(getCell(sheet, rowNo, colNo+79), "6. 기타특성");
		getCell(sheet, rowNo, colNo+79).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+80).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+79, colNo+80));
		
		setText(getCell(sheet, rowNo+1, colNo+79), "임지\r\n"+"이용상태");
		getCell(sheet, rowNo+1, colNo+79).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+79).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+79).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(79, 4100);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+79, colNo+79));
		
		setText(getCell(sheet, rowNo+1, colNo+80), "조사 경계\r\n"+"하부 임지\r\n"+"이용상태");
		getCell(sheet, rowNo+1, colNo+80).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+80).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+80).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(80, 4300);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+80, colNo+80));
		
		setText(getCell(sheet, rowNo, colNo+81), "7. 땅밀림 징후");
		getCell(sheet, rowNo, colNo+81).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+82).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+83).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+84).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+85).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+86).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+87).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+88).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+89).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+90).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+91).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+92).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+93).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+94).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+95).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+96).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+97).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+98).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+99).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+100).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+101).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+102).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+103).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+104).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+105).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+106).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+107).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+108).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+109).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+110).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+111).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+112).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+113).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+114).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+115).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo, colNo+116).setCellStyle(HeaderStyle4);		
		getCell(sheet, rowNo, colNo+117).setCellStyle(HeaderStyle4);		
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+81, colNo+117));
	
		setText(getCell(sheet, rowNo+1, colNo+81), "징후발생\r\n"+"여부");
		getCell(sheet, rowNo+1, colNo+81).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+81).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+81).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(81, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+81, colNo+81));
		
		setText(getCell(sheet, rowNo+1, colNo+82), "");
		getCell(sheet, rowNo+1, colNo+82).setCellStyle(HeaderStyle12);
		getCell(sheet, rowNo+2, colNo+82).setCellStyle(HeaderStyle12);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+82, colNo+82));
		
		setText(getCell(sheet, rowNo+3, colNo+82), "판정\r\n"+"점수");
		sheet.setColumnWidth(82, 2000);
		getCell(sheet, rowNo+3, colNo+82).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+1, colNo+83), "직접\r\n"+"징후");
		getCell(sheet, rowNo+1, colNo+83).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+2, colNo+83).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+3, colNo+83).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(83, 2000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+83, colNo+83));
		
		setText(getCell(sheet, rowNo+1, colNo+84), "");
		getCell(sheet, rowNo+1, colNo+84).setCellStyle(HeaderStyle12);
		getCell(sheet, rowNo+2, colNo+84).setCellStyle(HeaderStyle12);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+84, colNo+84));
		
		setText(getCell(sheet, rowNo+3, colNo+84), "판정\r\n"+"점수");
		sheet.setColumnWidth(84, 2000);
		getCell(sheet, rowNo+3, colNo+84).setCellStyle(HeaderStyle2);
		
		setText(getCell(sheet, rowNo+1, colNo+85), "간접\r\n"+"징후");
		getCell(sheet, rowNo+1, colNo+85).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+2, colNo+85).setCellStyle(HeaderStyle13);
		getCell(sheet, rowNo+3, colNo+85).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(85, 2000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+85, colNo+85));
		
		setText(getCell(sheet, rowNo+1, colNo+86), "직접징후");
		getCell(sheet, rowNo+1, colNo+86).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+87).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+88).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+89).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+91).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+92).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+93).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+94).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+95).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+96).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+97).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+98).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+1, colNo+99).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+86, colNo+99));
		
		setText(getCell(sheet, rowNo+2, colNo+86), "균열(유/무)");
		getCell(sheet, rowNo+2, colNo+86).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+87).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+88).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+89).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+90).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+91).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+92).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+86, colNo+92));
		
		setText(getCell(sheet, rowNo+3, colNo+86), "유무");
		sheet.setColumnWidth(86, 2000);
		getCell(sheet, rowNo+3, colNo+86).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+87), "위도");
		sheet.setColumnWidth(87, 4000);
		getCell(sheet, rowNo+3, colNo+87).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+88), "경도");
		sheet.setColumnWidth(88, 4000);
		getCell(sheet, rowNo+3, colNo+88).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+89), "고도");
		sheet.setColumnWidth(89, 3000);
		getCell(sheet, rowNo+3, colNo+89).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+90), "규모\r\n"+"(연장x높이x깊이)");
		getCell(sheet, rowNo+3, colNo+90).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+91).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+92).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(90, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+90, colNo+92));
		
		setText(getCell(sheet, rowNo+2, colNo+93), "단차(유/무)");
		getCell(sheet, rowNo+2, colNo+93).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+94).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+95).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+96).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+97).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+98).setCellStyle(HeaderStyle4);
		getCell(sheet, rowNo+2, colNo+99).setCellStyle(HeaderStyle4);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+93, colNo+99));
		
		setText(getCell(sheet, rowNo+3, colNo+93), "유무");
		sheet.setColumnWidth(93, 2000);
		getCell(sheet, rowNo+3, colNo+93).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+94), "위도");
		sheet.setColumnWidth(94, 4000);
		getCell(sheet, rowNo+3, colNo+94).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+95), "경도");
		sheet.setColumnWidth(95, 4000);
		getCell(sheet, rowNo+3, colNo+95).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+96), "고도");
		sheet.setColumnWidth(96, 3000);
		getCell(sheet, rowNo+3, colNo+96).setCellStyle(HeaderStyle4);
		
		setText(getCell(sheet, rowNo+3, colNo+97), "규모\r\n"+"(연장x높이x깊이)");
		getCell(sheet, rowNo+3, colNo+97).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+98).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+99).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(97, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+97, colNo+99));
		
		setText(getCell(sheet, rowNo+1, colNo+100), "간접징후");
		getCell(sheet, rowNo+1, colNo+100).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+101).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+102).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+103).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+104).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+105).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+106).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+107).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+108).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+109).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+110).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+111).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+112).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+113).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+114).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+115).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+116).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+117).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+100, colNo+117));
		
		setText(getCell(sheet, rowNo+2, colNo+100), "구조물 이상 (유/무)");
		getCell(sheet, rowNo+2, colNo+100).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+101).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+102).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+103).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+104).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+105).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+106).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+100, colNo+106));
		
		setText(getCell(sheet, rowNo+3, colNo+100), "유무");
		sheet.setColumnWidth(100, 2000);
		getCell(sheet, rowNo+3, colNo+100).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+101), "위도");
		sheet.setColumnWidth(101, 4000);
		getCell(sheet, rowNo+3, colNo+101).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+102), "경도");
		sheet.setColumnWidth(102, 4000);
		getCell(sheet, rowNo+3, colNo+102).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+103), "고도");
		sheet.setColumnWidth(103, 3000);
		getCell(sheet, rowNo+3, colNo+103).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+104), "규모\r\n"+"(연장x높이x깊이)");
		getCell(sheet, rowNo+3, colNo+104).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+105).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+106).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(104, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+104, colNo+106));
		
		setText(getCell(sheet, rowNo+2, colNo+107), "수목이상생장 (유/무)");
		getCell(sheet, rowNo+2, colNo+107).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+108).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+109).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+110).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+111).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+112).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+113).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+107, colNo+113));
		
		setText(getCell(sheet, rowNo+3, colNo+107), "유무");
		sheet.setColumnWidth(107, 2000);
		getCell(sheet, rowNo+3, colNo+107).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+108), "위도");
		sheet.setColumnWidth(108, 4000);
		getCell(sheet, rowNo+3, colNo+108).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+109), "경도");
		sheet.setColumnWidth(109, 4000);
		getCell(sheet, rowNo+3, colNo+109).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+110), "고도");
		sheet.setColumnWidth(110, 3000);
		getCell(sheet, rowNo+3, colNo+110).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+111), "규모\r\n"+"(연장x높이x깊이)");
		getCell(sheet, rowNo+3, colNo+111).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+112).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+113).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(111, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+111, colNo+113));
		
		setText(getCell(sheet, rowNo+2, colNo+114), "지하수 용출 (유/무)");
		getCell(sheet, rowNo+2, colNo+114).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+115).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+116).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+117).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+2, rowNo+2, colNo+114, colNo+117));
		
		setText(getCell(sheet, rowNo+3, colNo+114), "유무");
		sheet.setColumnWidth(114, 2000);
		getCell(sheet, rowNo+3, colNo+114).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+115), "위도");
		sheet.setColumnWidth(115, 4000);
		getCell(sheet, rowNo+3, colNo+115).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+116), "경도");
		sheet.setColumnWidth(116, 4000);
		getCell(sheet, rowNo+3, colNo+116).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo+3, colNo+117), "고도");
		sheet.setColumnWidth(117, 3000);
		getCell(sheet, rowNo+3, colNo+117).setCellStyle(HeaderStyle1);
		
		setText(getCell(sheet, rowNo, colNo+118), "산사태위험지판정표");
		getCell(sheet, rowNo, colNo+118).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+119).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+120).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+121).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+122).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+123).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+124).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+125).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+126).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+127).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+128).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+129).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+130).setCellStyle(HeaderStyle6);
		getCell(sheet, rowNo, colNo+131).setCellStyle(HeaderStyle6);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+118, colNo+131));
		
		setText(getCell(sheet, rowNo+1, colNo+118), "보호대상");
		getCell(sheet, rowNo+1, colNo+118).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+119).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+118).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+119).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+118, colNo+119));
		
		setText(getCell(sheet, rowNo+3, colNo+118), "일반산지/재산피해/인가1~5미만/인가5~9/인가10이상/공공시설");
		getCell(sheet, rowNo+3, colNo+118).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+119).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(118, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+118, colNo+119));
		
		setText(getCell(sheet, rowNo+1, colNo+120), "경사길이");
		getCell(sheet, rowNo+1, colNo+120).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+121).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+120).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+121).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+2, colNo+120, colNo+121));
		
		setText(getCell(sheet, rowNo+3, colNo+120), "50m이하/51~100m/101~200m/201m이상");
		getCell(sheet, rowNo+3, colNo+120).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+121).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(120, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+3, rowNo+3, colNo+120, colNo+121));
		
		setText(getCell(sheet, rowNo+1, colNo+122), "모암");
		getCell(sheet, rowNo+1, colNo+122).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+123).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+122).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+123).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+122).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+123).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(122, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+122, colNo+123));
		
		setText(getCell(sheet, rowNo+1, colNo+124), "임상");
		getCell(sheet, rowNo+1, colNo+124).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+125).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+124).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+125).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+124).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+125).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(124, 6000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+124, colNo+125));
		
		setText(getCell(sheet, rowNo+1, colNo+126), "경사위치");
		getCell(sheet, rowNo+1, colNo+126).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+126).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+126).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(126, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+126, colNo+126));
		
		setText(getCell(sheet, rowNo+1, colNo+127), "사면형");
		getCell(sheet, rowNo+1, colNo+127).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+127).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+127).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(127, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+127, colNo+127));
		
		setText(getCell(sheet, rowNo+1, colNo+128), "토심");
		getCell(sheet, rowNo+1, colNo+128).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+128).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+128).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(128, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+128, colNo+128));
		
		setText(getCell(sheet, rowNo+1, colNo+129), "경사도");
		getCell(sheet, rowNo+1, colNo+129).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+2, colNo+129).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+3, colNo+129).setCellStyle(HeaderStyle1);
		sheet.setColumnWidth(129, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+129, colNo+129));
		
		setText(getCell(sheet, rowNo+1, colNo+130), "판정점수");
		getCell(sheet, rowNo+1, colNo+130).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo+2, colNo+130).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo+3, colNo+130).setCellStyle(HeaderStyle7);
		sheet.setColumnWidth(130, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+130, colNo+130));
		
		setText(getCell(sheet, rowNo+1, colNo+131), "판정등급");
		getCell(sheet, rowNo+1, colNo+131).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo+2, colNo+131).setCellStyle(HeaderStyle7);
		getCell(sheet, rowNo+3, colNo+131).setCellStyle(HeaderStyle7);
		sheet.setColumnWidth(131, 3000);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+3, colNo+131, colNo+131));
		
		setText(getCell(sheet, rowNo, colNo+132), "비고(종합의견1)");
		getCell(sheet, rowNo, colNo+132).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+1, colNo+132).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+2, colNo+132).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+132).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(132, 4500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+132, colNo+132));
		
		setText(getCell(sheet, rowNo, colNo+133), "종합의견2");
		getCell(sheet, rowNo, colNo+133).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+1, colNo+133).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+2, colNo+133).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+133).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(133, 4500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+133, colNo+133));
		
		setText(getCell(sheet, rowNo, colNo+134), "종합의견3");
		getCell(sheet, rowNo, colNo+134).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+1, colNo+134).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+2, colNo+134).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+134).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(134, 4500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+134, colNo+134));
		
		setText(getCell(sheet, rowNo, colNo+135), "종합의견4");
		getCell(sheet, rowNo, colNo+135).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+1, colNo+135).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+2, colNo+135).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+135).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(135, 4500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+135, colNo+135));
		
		setText(getCell(sheet, rowNo, colNo+136), "종합의견5");
		getCell(sheet, rowNo, colNo+136).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+1, colNo+136).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+2, colNo+136).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+136).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(136, 4500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+136, colNo+136));
		
		setText(getCell(sheet, rowNo, colNo+137), "자문결과");
		getCell(sheet, rowNo, colNo+137).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+1, colNo+137).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+2, colNo+137).setCellStyle(HeaderStyle5);
		getCell(sheet, rowNo+3, colNo+137).setCellStyle(HeaderStyle5);
		sheet.setColumnWidth(137, 4500);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+3, colNo+137, colNo+137));
		
		colNo = 0;
		rowNo = 4;
		
		String formulaText = "";
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				
				if(dataList.get(i).get("passresn").toString().equals("진입불가")) {
					//22년 고유번호
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sn").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//번호
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fid").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//조사번호
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//중복여부(진입불가)
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("passresn").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//물리탐사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//시도
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sd").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//시군구
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sgg").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//읍면동
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("emd").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//리
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ri").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지번
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("jibun").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//주소
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sd").toString() +" "+ dataList.get(i).get("sgg").toString() +" "+ dataList.get(i).get("emd").toString() +" "+ dataList.get(i).get("ri").toString() +" "+ dataList.get(i).get("jibun").toString() +dataList.get(i).get("jibun").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					String[] pointArr = dataList.get(i).get("point").toString().split("\\s+");
					
					//북위 - 도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//북위 - 분
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
	
					//북위 - 초
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
	
					//동경 - 도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
	
					//동경 - 분
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
	
					//동경 - 초
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
	
					//좌표
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "\"N \"&L"+(rowNo+1)+"&\"°\"&M"+(rowNo+1)+"&\"′\"&N"+(rowNo+1)+"&\"″, \"&\"E \"&O"+(rowNo+1)+"&\"°\"&P"+(rowNo+1)+"&\"′\"&Q"+(rowNo+1)+"&\"″\"";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle15);
					
					//조사일자
					if(dataList.get(i).get("svydt").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					}
					
					//부서명
					if(dataList.get(i).get("svydept") == null || dataList.get(i).get("svydept").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydept").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					}
					
					//조사자
					if(dataList.get(i).get("svyuser").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyuser").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					}
					
					//고도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					
					//판정표등급
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//주 구성암석 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//주 구성암석
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//타 지층 및 관입암 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//암석풍화 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//암석풍화
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지질구조 단층
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지질구조 습곡형
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면1 종류
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면1 주향
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면1 경사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면2 종류
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면2 주향
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면2 경사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면3 종류
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면3 주향
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면3 경사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면4 종류
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면4 주향
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면4 경사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면5 종류
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면5 주향
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면5 경사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면 방향수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면 사면의 방향성 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면 사면과 방향성
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면 간격 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//불연속면 간격
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토양형
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토심 B층까지 깊이 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토심 B층까지 깊이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토성B층기준(약30cm) 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토성B층기준(약30cm)
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토양구조 B층 기준
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토양수분상태 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토양수분상태 
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//암석노출도(%)
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//너덜 유무 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//너덜 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지형구분 최대높이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지형구분 위치 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지형구분 위치
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//조사지역 위치 능선
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//조사지역 위치
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//평면형 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//평면형
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//종단면형 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//종단면형
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//경사범위 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//경사범위
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//평균경사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//상류로부터 지하수 유입 가능성
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//하류계류의 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//샘, 소, 저수지 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//임상
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//주요수종
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//임지이용상태
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//조사경계 하부 임지 이용상태
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//징후발생여부
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//직접징후 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//직접징후
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//간접징후 판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//간접징후
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열 위도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열 경도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열 고도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열 연장
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열 높이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//균열 깊이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 위도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 경도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 고도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 연장
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 높이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//단차 깊이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
	
					//구조물이상 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//구조물이상 위도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//구조물이상 경도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//구조물이상 고도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//구조물이상 연장
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//구조물이상 높이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//구조물이상 깊이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 위도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 경도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 고도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 연장
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 높이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//수목이상생장 깊이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지하수용출 유무
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지하수용출 위도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지하수용출 경도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//지하수용출 고도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//보호대상 점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//보호대상
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//경사길이 점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//경사길이
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					
					//모암 점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//모암
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//임상 점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//임상
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//경사위치
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					
					//사면형
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//토심
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					
	 				//경사도
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					
					//판정점수
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//판정등급
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					
					//종합의견1
					if(dataList.get(i).get("opinion1").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle15);
					}
					
					//종합의견2
					if(dataList.get(i).get("opinion2").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견3 
					if(dataList.get(i).get("opinion3").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견4 
					if(dataList.get(i).get("opinion4").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion4").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견5 
					if(dataList.get(i).get("opinion5").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion5").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//자문결과 
					if(dataList.get(i).get("cnsutresn").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo++, colNo).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnsutresn").toString());
						getCell(sheet, rowNo++, colNo).setCellStyle(ValueStyle);
					}
				}else {
					//22년 고유번호
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sn").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//번호
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fid").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//조사번호
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//중복여부(진입불가)
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("passresn").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//물리탐사
					setText(getCell(sheet, rowNo, colNo), "");
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//시도
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sd").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//시군구
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sgg").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//읍면동
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("emd").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//리
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ri").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//지번
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("jibun").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					//주소
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sd").toString() +" "+ dataList.get(i).get("sgg").toString() +" "+ dataList.get(i).get("emd").toString() +" "+ dataList.get(i).get("ri").toString() +" "+ dataList.get(i).get("jibun").toString());
					getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					
					String[] pointArr = dataList.get(i).get("point").toString().split("\\s+");
					
					//북위 - 도
					if(dataList.get(i).get("point").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), pointArr[0].substring(0, pointArr[0].length() - 1));
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//북위 - 분
					if(dataList.get(i).get("point").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), pointArr[1].substring(0, pointArr[1].length() - 1));
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
	
					//북위 - 초
					if(dataList.get(i).get("point").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), pointArr[2].substring(0, pointArr[2].length() - 1));
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
	
					//동경 - 도
					if(dataList.get(i).get("point").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), pointArr[3].substring(0, pointArr[3].length() - 1));
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
	
					//동경 - 분
					if(dataList.get(i).get("point").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), pointArr[4].substring(0, pointArr[4].length() - 1));
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
	
					//동경 - 초
					if(dataList.get(i).get("point").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), pointArr[5].substring(0, pointArr[5].length() - 1));
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
	
					//좌표
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "\"N \"&L"+(rowNo+1)+"&\"°\"&M"+(rowNo+1)+"&\"′\"&N"+(rowNo+1)+"&\"″, \"&\"E \"&O"+(rowNo+1)+"&\"°\"&P"+(rowNo+1)+"&\"′\"&Q"+(rowNo+1)+"&\"″\"";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(ValueStyle);
					
					//조사일자
					if(dataList.get(i).get("svydt").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//부서명
					if(dataList.get(i).get("svydept") == null || dataList.get(i).get("svydept").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydept").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//조사자
					if(dataList.get(i).get("svyuser").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyuser").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//고도
					if(dataList.get(i).get("pz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						
						if(dataList.get(i).get("pz").toString().contains("m")) {
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("pz").toString());	
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
						}else{
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("pz").toString()+"m");	
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
						}
						
					}
					
					//판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "Y"+(rowNo+1)+"+"+"AB"+(rowNo+1)+"+"+"AV"+(rowNo+1)+"+"+"AX"+(rowNo+1)+"+"+"BA"+(rowNo+1)+"+"+"BC"+(rowNo+1)+"+"+"BF"+(rowNo+1)+"+"+"BI"+(rowNo+1)+"+"+"BL"+(rowNo+1)+"+"+"BP"+(rowNo+1)+"+"+"BR"+(rowNo+1)+"+"+"BT"+(rowNo+1)+"+"+"CE"+(rowNo+1)+"+"+"CG"+(rowNo+1);
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					
					//판정표등급
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(W"+(rowNo+1)+">=64,\"A\",IF(AND(W"+(rowNo+1)+">=45,W"+(rowNo+1)+"<64),\"B\",IF(AND(W"+(rowNo+1)+">0,W"+(rowNo+1)+"<45),\"C\",\"-\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(ValueStyle);
					
					//주 구성암석 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(OR(Z"+(rowNo+1)+"=\"화성암(심성암)\",Z"+(rowNo+1)+"=\"화성암(화산암)\",Z"+(rowNo+1)+"=\"화성암\",Z"+(rowNo+1)+"=\"심성암\""+",Z"+(rowNo+1)+"=\"화산암\"),\"2\",IF(Z"+(rowNo+1)+"=\"변성암\",\"5\",IF(Z"+(rowNo+1)+"=\"퇴적암\",\"8\",\"\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//주 구성암석
					if(dataList.get(i).get("cmprokval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cmprokval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//타 지층 및 관입암 유무
					if(dataList.get(i).get("instrokat").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("instrokat").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//암석풍화 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(AC"+(rowNo+1)+"=\"경암\",\"2\",IF(AC"+(rowNo+1)+"=\"보통암\",\"3\",IF(AC"+(rowNo+1)+"=\"풍화암\",\"7\",IF(AC"+(rowNo+1)+"=\"연암\",\"4\","+"IF(AC"+(rowNo+1)+"=\"기타\",\"0\",\"\")))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//암석풍화
					if(dataList.get(i).get("rokwthrval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rokwthrval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지질구조 단층
					if(dataList.get(i).get("geologyflt").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("geologyflt").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지질구조 습곡형
					if(dataList.get(i).get("geologyfld").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("geologyfld").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면1 종류
					if(dataList.get(i).get("disctnupln1").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln1").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면1 주향
					if(dataList.get(i).get("disctnupln1Strk").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln1Strk").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면1 경사
					if(dataList.get(i).get("disctnupln1Dip").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln1Dip").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면2 종류
					if(dataList.get(i).get("disctnupln2").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln2").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면2 주향
					if(dataList.get(i).get("disctnupln2Strk").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln2Strk").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면2 경사
					if(dataList.get(i).get("disctnupln2Dip").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln2Dip").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면3 종류
					if(dataList.get(i).get("disctnupln3").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln3").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면3 주향
					if(dataList.get(i).get("disctnupln3Strk").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln3Strk").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면3 경사
					if(dataList.get(i).get("disctnupln3Dip").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln3Dip").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면4 종류
					if(dataList.get(i).get("disctnupln4").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln4").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면4 주향
					if(dataList.get(i).get("disctnupln4Strk").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln4Strk").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면4 경사
					if(dataList.get(i).get("disctnupln4Dip").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln4Dip").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면5 종류
					if(dataList.get(i).get("disctnupln5").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln5").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면5 주향
					if(dataList.get(i).get("disctnupln5Strk").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln5Strk").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면5 경사
					if(dataList.get(i).get("disctnupln5Dip").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnupln5Dip").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면 방향수
					if(dataList.get(i).get("disctnuplndrcno").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnuplndrcno").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면 사면과 방향성 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(AW"+(rowNo+1)+"=\"없음\",\"0\",IF(AW"+(rowNo+1)+"=\"역방향\",\"3\",IF(AW"+(rowNo+1)+"=\"수직방향\",\"5\",IF(AW"+(rowNo+1)+"=\"수평방향\",\"4\","+"IF(AW"+(rowNo+1)+"=\"비탈면방향\",\"9\",\"-\")))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//불연속면 사면과 방향성
					if(dataList.get(i).get("disctnuplnslpval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");				
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnuplnslpval").toString());				
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//불연속면 간격 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(AY"+(rowNo+1)+"=\"없음\",\"0\",IF(AY"+(rowNo+1)+"=\"넓음\",\"1\",IF(AY"+(rowNo+1)+"=\"보통\",\"2\",IF(AY"+(rowNo+1)+"=\"매우조밀\",\"5\","+"IF(AY"+(rowNo+1)+"=\"조밀\",\"4\",\"-\")))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//불연속면 간격
					if(dataList.get(i).get("disctnuplnintvlval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("disctnuplnintvlval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//토양형
					if(dataList.get(i).get("soilty").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilty").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//토심 B층까지 깊이 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BB"+(rowNo+1)+"=\"30이하\",\"2\", IF(BB"+(rowNo+1)+"=\"30~60\",\"3\",IF(BB"+(rowNo+1)+"=\"60~90\",\"4\",IF(BB"+(rowNo+1)+"=\"90이상\",\"5\",\"오타\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//토심 B층까지 깊이
					if(dataList.get(i).get("soildeptbval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soildeptbval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//토성B층기준(약30cm) 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BD"+(rowNo+1)+"=\"사질토\",\"2\",IF(BD"+(rowNo+1)+"=\"사질양토\",\"3\",IF(BD"+(rowNo+1)+"=\"점질토\",\"5\",\"오타\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//토성B층기준(약30cm)
					if(dataList.get(i).get("soilclassbval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilclassbval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//토양구조 B층 기준
					if(dataList.get(i).get("soilstrct").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilstrct").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//토양수분상태 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BG"+(rowNo+1)+"=\"습\",\"5\",IF(BG"+(rowNo+1)+"=\"적윤\",\"3\",IF(BG"+(rowNo+1)+"=\"약건\",\"2\",IF(BG"+(rowNo+1)+"=\"약습\",\"4\",IF(BG"+(rowNo+1)+"=\"건조\",\"1\",\"오타\")))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//토양수분상태 
					if(dataList.get(i).get("soilwtrval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilwtrval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//암석노출도(%)
					if(dataList.get(i).get("rokexpsr").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rokexpsr").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//너덜 유무 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BJ"+(rowNo+1)+"=\"무\",\"0\",IF(BJ"+(rowNo+1)+"=\"유\",\"4\",\"오타\"))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//너덜 유무
					if(dataList.get(i).get("talusat").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("talusat").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지형구분 최대높이
					if(dataList.get(i).get("pz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						
						if(dataList.get(i).get("pz").toString().contains("m")) {
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("pz").toString());	
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
						}else{
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("pz").toString()+"m");	
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
						}
						
					}
					
					//지형구분 위치 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BM"+(rowNo+1)+"=\"산악지\",\"2\",IF(BM"+(rowNo+1)+"=\"구릉지\",\"3\",IF(BM"+(rowNo+1)+"=\"완구릉지\",\"4\",\"오타\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//지형구분 위치
					if(dataList.get(i).get("tpgrphval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("tpgrphval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//조사지역 위치 능선
					if(dataList.get(i).get("arealcridge").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle2);
					} else {
//						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("arealcridge").toString());	
//						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle2);
						
						cell = getCell(sheet, rowNo, colNo++);
						cell.setCellValue(Integer.parseInt((dataList.get(i).get("arealcridge").toString())));
						cell.setCellType(CellType.NUMERIC);
						cell.setCellStyle(ValueStyle);
						
					}
					
					//조사지역 위치
					if(dataList.get(i).get("arealcval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("arealcval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//평면형 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BQ"+(rowNo+1)+"=\"혼합형\",\"4\",IF(BQ"+(rowNo+1)+"=\"직선형\",\"1\",IF(BQ"+(rowNo+1)+"=\"곡형\",\"3\",IF(BQ"+(rowNo+1)+"=\"미근형\",\"2\",\"오타\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//평면형
					if(dataList.get(i).get("plnformval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("plnformval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종단면형 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BS"+(rowNo+1)+"=\"혼합형\",\"4\",IF(BS"+(rowNo+1)+"=\"직선형\",\"1\",IF(BS"+(rowNo+1)+"=\"곡형\",\"3\",IF(BS"+(rowNo+1)+"=\"미근형\",\"2\",\"오타\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//종단면형
					if(dataList.get(i).get("lngformval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lngformval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//경사범위 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BU"+(rowNo+1)+"=\"10이하\",\"1\",IF(BU"+(rowNo+1)+"=\"30초과\",\"2\",IF(BU"+(rowNo+1)+"=\"10~20\",\"3\",IF(BU"+(rowNo+1)+"=\"20~30\",\"4\",\"오타\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//경사범위
					if(dataList.get(i).get("slprngval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slprngval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//평균경사
					if(dataList.get(i).get("slprngavgval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle2);
					} else {
						if(dataList.get(i).get("slprngavgval").toString().contains("이상")||dataList.get(i).get("slprngavgval").toString().contains("이하")||dataList.get(i).get("slprngavgval").toString().contains("초과")||dataList.get(i).get("slprngavgval").toString().contains("미만")||dataList.get(i).get("slprngavgval").toString().contains("~")) {
							setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slprngavgval").toString());	
							getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle2);
						}else {
							cell = getCell(sheet, rowNo, colNo++);
							cell.setCellValue(Math.round(Float.parseFloat((dataList.get(i).get("slprngavgval").toString()))));
							cell.setCellType(CellType.NUMERIC);
							cell.setCellStyle(ValueStyle);
						}
					}
					
					//상류로부터 지하수 유입 가능성
					if(dataList.get(i).get("ugrwtrposblty").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ugrwtrposblty").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//하류계류의 유무
					if(dataList.get(i).get("dwstrmat").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwstrmat").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//샘, 소, 저수지 유무
					if(dataList.get(i).get("sprgat").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sprgat").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//임상
					if(dataList.get(i).get("frstfrval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frstfrval").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//주요수종
					if(dataList.get(i).get("maintreeknd").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("maintreeknd").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//임지이용상태
					if(dataList.get(i).get("frlndsttus").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frlndsttus").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//조사경계 하부 임지 이용상태
					if(dataList.get(i).get("lwbndlwfrlndsttus").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					} else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lwbndlwfrlndsttus").toString());	
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//징후발생여부
					if(dataList.get(i).get("symptmoccur").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("symptmoccur").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//직접징후 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(CF"+(rowNo+1)+"=\"유\",\"22\",IF(CF"+(rowNo+1)+"=\"무\",\"0\",\"0\"))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//직접징후
					if(dataList.get(i).get("dirsymptmval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dirsymptmval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//간접징후 판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(CH"+(rowNo+1)+"=\"유\",\"22\",IF(CH"+(rowNo+1)+"=\"무\",\"0\",\"0\"))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//간접징후
					if(dataList.get(i).get("indirsymptmval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("indirsymptmval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열유무
					if(!dataList.get(i).get("lcpsttusCrkPx").toString().equals("")
						||!dataList.get(i).get("lcpsttusCrkPy").toString().equals("")
						||!dataList.get(i).get("lcpsttusCrkPz").toString().equals("")
						||!dataList.get(i).get("lcpsttusCrkLength").toString().equals("")
						||!dataList.get(i).get("lcpsttusCrkHeight").toString().equals("")
						||!dataList.get(i).get("lcpsttusCrkDepth").toString().equals("")
						) {
						setText(getCell(sheet, rowNo, colNo), "유");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), "무");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열 위도
					if(dataList.get(i).get("lcpsttusCrkPx").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusCrkPx").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열 경도
					if(dataList.get(i).get("lcpsttusCrkPy").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusCrkPy").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열 고도
					if(dataList.get(i).get("lcpsttusCrkPz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusCrkPz").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열 연장
					if(dataList.get(i).get("lcpsttusCrkLength").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusCrkLength").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열 높이
					if(dataList.get(i).get("lcpsttusCrkHeight").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusCrkHeight").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//균열 깊이
					if(dataList.get(i).get("lcpsttusCrkDepth").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusCrkDepth").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 유무
					if(!dataList.get(i).get("lcpsttusStrctPx").toString().equals("")
							||!dataList.get(i).get("lcpsttusStpPy").toString().equals("")
							||!dataList.get(i).get("lcpsttusStpPz").toString().equals("")
							||!dataList.get(i).get("lcpsttusStpLength").toString().equals("")
							||!dataList.get(i).get("lcpsttusStpHeight").toString().equals("")
							||!dataList.get(i).get("lcpsttusStpDepth").toString().equals("")
							) {
						setText(getCell(sheet, rowNo, colNo), "유");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), "무");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 위도
					if(dataList.get(i).get("lcpsttusStrctPx").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctPx").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 경도
					if(dataList.get(i).get("lcpsttusStrctPy").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctPy").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 고도
					if(dataList.get(i).get("lcpsttusStrctPz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctPz").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 연장
					if(dataList.get(i).get("lcpsttusStpLength").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStpLength").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 높이
					if(dataList.get(i).get("lcpsttusStpHeight").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStpHeight").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//단차 깊이
					if(dataList.get(i).get("lcpsttusStpDepth").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStpDepth").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
	
					//구조물이상 유무
					if(!dataList.get(i).get("lcpsttusStrctPx").toString().equals("")
							||!dataList.get(i).get("lcpsttusStrctPy").toString().equals("")
							||!dataList.get(i).get("lcpsttusStrctPz").toString().equals("")
							||!dataList.get(i).get("lcpsttusStrctLength").toString().equals("")
							||!dataList.get(i).get("lcpsttusStrctHeight").toString().equals("")
							||!dataList.get(i).get("lcpsttusStrctDepth").toString().equals("")
							) {
						setText(getCell(sheet, rowNo, colNo), "유");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), "무");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//구조물이상 위도
					if(dataList.get(i).get("lcpsttusStrctPx").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctPx").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//구조물이상 경도
					if(dataList.get(i).get("lcpsttusStrctPy").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctPy").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//구조물이상 고도
					if(dataList.get(i).get("lcpsttusStrctPz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctPz").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//구조물이상 연장
					if(dataList.get(i).get("lcpsttusStrctLength").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctLength").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//구조물이상 높이
					if(dataList.get(i).get("lcpsttusStrctHeight").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctHeight").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//구조물이상 깊이
					if(dataList.get(i).get("lcpsttusStrctDepth").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusStrctDepth").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 유무
					if(!dataList.get(i).get("lcpsttusWdptPx").toString().equals("")
							||!dataList.get(i).get("lcpsttusWdptPy").toString().equals("")
							||!dataList.get(i).get("lcpsttusWdptPz").toString().equals("")
							||!dataList.get(i).get("lcpsttusWdptLength").toString().equals("")
							||!dataList.get(i).get("lcpsttusWdptHeight").toString().equals("")
							||!dataList.get(i).get("lcpsttusWdptDepth").toString().equals("")
							) {
						setText(getCell(sheet, rowNo, colNo), "유");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), "무");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 위도
					if(dataList.get(i).get("lcpsttusWdptPx").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusWdptPx").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 경도
					if(dataList.get(i).get("lcpsttusWdptPy").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusWdptPy").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 고도
					if(dataList.get(i).get("lcpsttusWdptPz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusWdptPz").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 연장
					if(dataList.get(i).get("lcpsttusWdptLength").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusWdptLength").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 높이
					if(dataList.get(i).get("lcpsttusWdptHeight").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusWdptHeight").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//수목이상생장 깊이
					if(dataList.get(i).get("lcpsttusWdptDepth").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusWdptDepth").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지하수용출 유무
					if(!dataList.get(i).get("lcpsttusUgrwtrPx").toString().equals("")
							||!dataList.get(i).get("lcpsttusUgrwtrPy").toString().equals("")
							||!dataList.get(i).get("lcpsttusUgrwtrPz").toString().equals("")
							) {
						setText(getCell(sheet, rowNo, colNo), "유");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), "무");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지하수용출 위도
					if(dataList.get(i).get("lcpsttusUgrwtrPx").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusUgrwtrPx").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지하수용출 경도
					if(dataList.get(i).get("lcpsttusUgrwtrPy").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusUgrwtrPy").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//지하수용출 고도
					if(dataList.get(i).get("lcpsttusUgrwtrPz").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lcpsttusUgrwtrPz").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//보호대상 점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(DP"+(rowNo+1)+"=\"일반산지\",\"0\",IF(DP"+(rowNo+1)+"=\"재산피해\",\"100\",IF(DP"+(rowNo+1)+"=\"인가1~5미만\",\"200\",IF(DP"+(rowNo+1)+"=\"인가5~9\",\"220\",IF(DP"+(rowNo+1)+"=\"인가10이상\",\"240\",IF(DP"+(rowNo+1)+"=\"공공시설\",\"240\",\"오타\"))))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//보호대상
					if(dataList.get(i).get("saftyval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("saftyval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//경사길이 점수
					
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(DR"+(rowNo+1)+"<=50,\"0\",IF((DR"+(rowNo+1)+"<=100)*AND(DR"+(rowNo+1)+">=51),\"19\",IF((DR"+(rowNo+1)+">=101)*AND(DR"+(rowNo+1)+"<=200),\"36\",IF(DR"+(rowNo+1)+">=201,\"74\",\"오타\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//경사길이
					if(dataList.get(i).get("slenval").toString().equals("")) {
						cell = getCell(sheet, rowNo, colNo++);
						cell.setCellType(CellType.NUMERIC);
						cell.setCellStyle(ValueStyle);
					}else {
//						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slenval").toString());
//						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle1);
						
						cell = getCell(sheet, rowNo, colNo++);
						cell.setCellValue(Integer.parseInt((dataList.get(i).get("slenval").toString())));
						cell.setCellType(CellType.NUMERIC);
						cell.setCellStyle(ValueStyle);
					}
					
					//모암 점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(OR(DT"+(rowNo+1)+"=\"이암류\",DT"+(rowNo+1)+"=\"혈암류\",DT"+(rowNo+1)+"=\"석회암류\",DT"+(rowNo+1)+"=\"사암류\",DT"+(rowNo+1)+"=\"역암류\",DT"+(rowNo+1)+"=\"응회암류\"),\"0\",IF(OR(DT"+(rowNo+1)+"=\"현무암류\",DT"+(rowNo+1)+"=\"규장암류\",DT"+(rowNo+1)+"=\"섬록암류\",DT"+(rowNo+1)+"=\"화강암류\"),\"5\",IF(OR(DT"+(rowNo+1)+"=\"천매암류\",DT"+(rowNo+1)+"=\"점판암류\"),\"12\",IF(OR(DT"+(rowNo+1)+"=\"편마암류\",DT"+(rowNo+1)+"=\"편암류\"),\"19\",IF(OR(DT"+(rowNo+1)+"=\"반암류\",DT"+(rowNo+1)+"=\"안산암류\"),\"56\",\"오타\")))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//모암
					if(dataList.get(i).get("prntrckval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prntrckval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					
					//임상 점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(OR(DV"+(rowNo+1)+"=\"침엽수림 치수\",DV"+(rowNo+1)+"=\"침엽수림 소경목\",DV"+(rowNo+1)+"=\"무입목지\",DV"+(rowNo+1)+"=\"죽림\"),\"18\",IF(OR(DV"+(rowNo+1)+"=\"침엽수림 중경목\",DV"+(rowNo+1)+"=\"침엽수림 대경목\",DV"+(rowNo+1)+"=\"활엽수림 치수\",DV"+(rowNo+1)+"=\"혼효림 치수\"),\"26\",IF(OR(DV"+(rowNo+1)+"=\"활엽수림 소경목\",DV"+(rowNo+1)+"=\"활엽수림 중경목\",DV"+(rowNo+1)+"=\"활엽수림 대경목\",DV"+(rowNo+1)+"=\"혼효림 소경목\",DV"+(rowNo+1)+"=\"혼효림 중경목\",DV"+(rowNo+1)+"=\"혼효림 대경목\"),\"0\",\"오타\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//임상
					if(dataList.get(i).get("frstfrval").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("frstfrval").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					
					//경사위치
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BN"+(rowNo+1)+"<=1,\"0\",IF((BN"+(rowNo+1)+"<=6)*AND(BN"+(rowNo+1)+">=2),\"9\",IF(BN"+(rowNo+1)+"<=10,\"26\",\"오타\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					
					//사면형
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BS"+(rowNo+1)+"=\"곡형\",\"0\",IF(BS"+(rowNo+1)+"=\"직선형\",\"5\",IF(BS"+(rowNo+1)+"=\"미근형\",\"12\",\"23\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//토심
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BB"+(rowNo+1)+"=\"30이하\",\"0\",IF(BB"+(rowNo+1)+"=\"30~60\",\"7\",IF(BB"+(rowNo+1)+"=\"60~90\",\"7\",IF(BB"+(rowNo+1)+"=\"90이상\",\"21\",\"오타\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					
	 				//경사도
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(BV"+(rowNo+1)+"<=25.99,\"16\",IF((BV"+(rowNo+1)+"<=40.9)*AND(BV"+(rowNo+1)+">=26),\"9\",IF(BV"+(rowNo+1)+">=41,\"0\",\"오타\")))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					
					
					//판정점수
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "DO"+(rowNo+1)+"+"+"DQ"+(rowNo+1)+"+"+"DS"+(rowNo+1)+"+"+"DU"+(rowNo+1)+"+"+"DW"+(rowNo+1)+"+"+"DX"+(rowNo+1)+"+"+"DY"+(rowNo+1)+"+"+"DZ"+(rowNo+1);
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//판정등급
					cell = getCell(sheet, rowNo, colNo++);
					formulaText = "IF(EA"+(rowNo+1)+">=360,\"1등급\",IF(AND(EA"+(rowNo+1)+">=240,EA"+(rowNo+1)+"<360),\"2등급\",IF(AND(EA"+(rowNo+1)+">=81,EA"+(rowNo+1)+"<240),\"3등급\",IF(AND(EA"+(rowNo+1)+">0,EA"+(rowNo+1)+"<=80),\"4등급\",\"-\"))))";
					cell.setCellFormula(formulaText);
					cell.setCellStyle(HeaderStyle14);
					
					//종합의견1
					if(dataList.get(i).get("opinion1").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견2
					if(dataList.get(i).get("opinion2").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견3 
					if(dataList.get(i).get("opinion3").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견4 
					if(dataList.get(i).get("opinion4").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion4").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//종합의견5 
					if(dataList.get(i).get("opinion5").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion5").toString());
						getCell(sheet, rowNo, colNo++).setCellStyle(ValueStyle);
					}
					
					//자문결과 
					if(dataList.get(i).get("cnsutresn").toString().equals("")) {
						setText(getCell(sheet, rowNo, colNo), "");
						getCell(sheet, rowNo++, colNo).setCellStyle(ValueStyle);
					}else {
						setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnsutresn").toString());
						getCell(sheet, rowNo++, colNo).setCellStyle(ValueStyle);
					}
				
				}
				
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
	public static XSSFCellStyle getHeaderCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
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
	public static XSSFCellStyle getHeaderCellStyle1(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(242, 242, 242)));
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
		cellStyle.setWrapText(true); // 줄 바꿈
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
	/**
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle3(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(221, 217, 195)));
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
	public static XSSFCellStyle getHeaderCellStyle4(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(191, 191, 191)));
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
	public static XSSFCellStyle getHeaderCellStyle5(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(192, 0, 0)));
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
	public static XSSFCellStyle getHeaderCellStyle6(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(150, 179, 215)));
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
	public static XSSFCellStyle getHeaderCellStyle7(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(140, 179, 228)));
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
	public static XSSFCellStyle getHeaderCellStyle8(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(221, 217, 195)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getHeaderCellStyle9(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(221, 217, 195)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getHeaderCellStyle10(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(191, 191, 191)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getHeaderCellStyle11(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(191, 191, 191)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getHeaderCellStyle12(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(242, 242, 242)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getHeaderCellStyle13(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(242, 242, 242)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getHeaderCellStyle14(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(217, 217, 217)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		XSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("General"));
		
		
		
		return cellStyle;
	}
	
	/**
	 * Header Style
	 * 
	 * @param wb
	 * @return
	 */
	public static XSSFCellStyle getHeaderCellStyle15(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 제목셀의 셀스타일
		//cellStyle.setWrapText(true); // 줄 바꿈
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 0)));
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 셀 색상 패턴
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		XSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("General"));
		
		
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
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
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
	public static XSSFCellStyle getValueCellStyle2(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = wb.createCellStyle(); // 데이터셀의 셀스타일
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 셀 가로 정렬
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 셀 세로 정렬
		cellStyle.setDataFormat((short) 0x31); // 셀 데이터 형식
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		XSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("0.00"));
		return cellStyle;
	}

}
