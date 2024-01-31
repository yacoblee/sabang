package or.sabang.utl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.ImageUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import com.clipsoft.org.apache.poi.ss.usermodel.CreationHelper;
import com.clipsoft.org.apache.poi.ss.usermodel.Drawing;
import com.clipsoft.org.apache.poi.ss.usermodel.Picture;
import com.clipsoft.org.apache.poi.util.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.GeoPoint;
import com.supermap.data.Geometry;
import com.supermap.data.Point2D;
import com.supermap.data.PrjCoordSys;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptService;
import or.sabang.sys.vyt.frd.service.impl.VytFrdSvyComptDAO;
import or.sabang.sys.vyt.frd.web.VytFrdSvyComptController;


public class MseExcelView extends AbstractView {

	/** 첨부파일 위치 지정  => globals.properties */
	private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	
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
	public MseExcelView() {
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
		List<EgovMap> photoTagList = (List<EgovMap>) dataMap.get("photoTagList"); // 
		List<EgovMap> wireextList = (List<EgovMap>) dataMap.get("wireextList");
		List<EgovMap> licmeterList = (List<EgovMap>) dataMap.get("licmeterList");
		List<EgovMap> gwgaugeList = (List<EgovMap>) dataMap.get("gwgaugeList");
		List<EgovMap> raingaugeList = (List<EgovMap>) dataMap.get("raingaugeList");
		List<EgovMap> strcdpmList = (List<EgovMap>) dataMap.get("strcdpmList");
		List<EgovMap> surdpmList = (List<EgovMap>) dataMap.get("surdpmList");
		List<EgovMap> gpsgaugeList = (List<EgovMap>) dataMap.get("gpsgaugeList");
		List<EgovMap> gatewayList = (List<EgovMap>) dataMap.get("gatewayList");
		List<EgovMap> nodeList = (List<EgovMap>) dataMap.get("nodeList");
		
		XSSFSheet sheet = wb.createSheet(sheetNm);
		sheet.setDefaultColumnWidth(10);
		Row row = sheet.createRow(1);
		int rowNo = 0, colNo = 0;
		
		setText(getCell(sheet, rowNo, colNo), "일반사항");
		getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1_b);
		getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1_b);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+1, colNo, colNo));
		
		setText(getCell(sheet, rowNo, colNo+1), "점검 일자");
		getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle1_b);
		
		String svyDt = dataList.get(0).get("svyDt") == null ? "" : dataList.get(0).get("svyDt").toString();
		setText(getCell(sheet, rowNo, colNo+2), svyDt);
		getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+2, colNo+3));
		
		setText(getCell(sheet, rowNo, colNo+4), "조사 단계");
		getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle1_b);
		
		String svystep = dataList.get(0).get("svystep") == null ? "" : dataList.get(0).get("svystep").toString();
		setText(getCell(sheet, rowNo, colNo+5), svystep);
		getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+5, colNo+6));
		
		setText(getCell(sheet, rowNo, colNo+7), "조사자");
		getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle1_b);
		
		String svyuser = dataList.get(0).get("svyuser") == null ? "" : dataList.get(0).get("svyuser").toString();
		setText(getCell(sheet, rowNo, colNo+8), svyuser);
		getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, colNo+8, colNo+11));
		
		setText(getCell(sheet, rowNo+1, colNo+1), "점검 장소");
		getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1_b);
		
		String sd = dataList.get(0).get("sd") == null ? "" : dataList.get(0).get("sd").toString();
		String sgg = dataList.get(0).get("sgg") == null ? "" : dataList.get(0).get("sgg").toString();
		String emd = dataList.get(0).get("emd") == null ? "" : dataList.get(0).get("emd").toString();
		String ri = dataList.get(0).get("ri") == null ? "" : dataList.get(0).get("ri").toString();
		String jibun = dataList.get(0).get("jibun") == null ? "" : dataList.get(0).get("jibun").toString();
		String addr = sd+" "+sgg+" "+emd+" "+ri+" "+jibun;
		setText(getCell(sheet, rowNo+1, colNo+2), addr);
		getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+1, rowNo+1, colNo+2, colNo+11));
		
		int rowNum = 2;
		
		// 와이어신축계
		if(wireextList.size() > 0 ) {
			int msSize = wireextList.size()*2 + 4;
			setText(getCell(sheet, rowNo+rowNum, colNo), "와이어신축계");
			for(int i = 0;i<msSize;i++) {
				getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
			}
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+4), "와이어");
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+6), "케이블");
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+8), "보호함체");
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+10), "전원");
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+11), "자물쇠");
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			
			for(int i =0;i<wireextList.size();i++) {
				rowNum++;
				
				String wireextchl = wireextList.get(i).get("wireextchl") == null ? "" : wireextList.get(i).get("wireextchl").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+1), wireextchl);
				getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
				
				String twSensor = wireextList.get(i).get("twSensor") == null ? "" : wireextList.get(i).get("twSensor").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+2), twSensor);
				getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
				
				String twWire = wireextList.get(i).get("twWire") == null ? "" : wireextList.get(i).get("twWire").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+4), twWire);
				getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
				
				String twCable = wireextList.get(i).get("twCable") == null ? "" : wireextList.get(i).get("twCable").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+6), twCable);
				getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
				
				String twPrtcbox = wireextList.get(i).get("twPrtcbox") == null ? "" : wireextList.get(i).get("twPrtcbox").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+8), twPrtcbox);
				getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
				
				String twPower = wireextList.get(i).get("twPower") == null ? "" : wireextList.get(i).get("twPower").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+10), twPower);
				getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
				
				String twLock = wireextList.get(i).get("twLock") == null ? "" : wireextList.get(i).get("twLock").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+11), twLock);
				getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
				
			}
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+5));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+6), "현장 센서값");
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+8).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+6, colNo+8));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+9), "시스템 센서값");
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+9, colNo+11));
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
			
			for(int i =0;i<wireextList.size();i++) {
				rowNum++;
				
				String wireextchl = wireextList.get(i).get("wireextchl") == null ? "" : wireextList.get(i).get("wireextchl").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+1), wireextchl);
				getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
				
				String twperChkresult = wireextList.get(i).get("twperChkresult") == null ? "" : wireextList.get(i).get("twperChkresult").toString();
				twperChkresult = twperChkresult.equals("양호") ? "O" : "";
				setText(getCell(sheet, rowNo+rowNum, colNo+2), twperChkresult);
				getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
				
				twperChkresult = wireextList.get(i).get("twperChkresult") == null ? "" : wireextList.get(i).get("twperChkresult").toString();
				twperChkresult = twperChkresult.equals("불량") ? "O" : "";
				setText(getCell(sheet, rowNo+rowNum, colNo+3), twperChkresult);
				getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
				
				String twperBadsttus = wireextList.get(i).get("twperBadsttus") == null ? "" : wireextList.get(i).get("twperBadsttus").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+4), twperBadsttus);
				getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
				
				String twperSptsensor = wireextList.get(i).get("twperSptsensor") == null ? "" : wireextList.get(i).get("twperSptsensor").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+6), twperSptsensor);
				getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+8));
				
				String twperSyssensor = wireextList.get(i).get("twperSyssensor") == null ? "" : wireextList.get(i).get("twperSyssensor").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+9), twperSyssensor);
				getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+9, colNo+11));
			}
			rowNum++;
		}
		
		//지중경사계
		if(licmeterList.size() > 0 ) {
			int msSize = licmeterList.size()+4;
			for(int i = 0;i<licmeterList.size();i++) {
				msSize = msSize + Integer.parseInt(licmeterList.get(i).get("licmeterperCnt").toString());
			}
			setText(getCell(sheet, rowNo+rowNum, colNo), "지중경사계");
			for(int i = 0;i<msSize;i++) {
				getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
			}
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));

			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);

			setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));

			setText(getCell(sheet, rowNo+rowNum, colNo+4), "케이블");
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));

			setText(getCell(sheet, rowNo+rowNum, colNo+6), "보호함체");
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));

			setText(getCell(sheet, rowNo+rowNum, colNo+8), "전원");
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));

			setText(getCell(sheet, rowNo+rowNum, colNo+10), "자물쇠");
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));

			for(int i =0;i<licmeterList.size();i++) {
			    rowNum++;
			    
			    String licmeterchl = licmeterList.get(i).get("licmeterchl") == null ? "" : licmeterList.get(i).get("licmeterchl").toString();
			    setText(getCell(sheet, rowNo+rowNum, colNo+1), licmeterchl);
			    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
			    
			    String incSensor = licmeterList.get(i).get("incSensor") == null ? "" : licmeterList.get(i).get("incSensor").toString();
			    setText(getCell(sheet, rowNo+rowNum, colNo+2), incSensor);
			    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
			    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
			    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
			    
			    String incCable = licmeterList.get(i).get("incCable") == null ? "" : licmeterList.get(i).get("incCable").toString();
			    setText(getCell(sheet, rowNo+rowNum, colNo+4), incCable);
			    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
			    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
			    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
			    
			    String incPrtcbox = licmeterList.get(i).get("incPrtcbox") == null ? "" : licmeterList.get(i).get("incPrtcbox").toString();
			    setText(getCell(sheet, rowNo+rowNum, colNo+6), incPrtcbox);
			    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
			    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
			    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
			    
			    String incPower = licmeterList.get(i).get("incPower") == null ? "" : licmeterList.get(i).get("incPower").toString();
			    setText(getCell(sheet, rowNo+rowNum, colNo+8), incPower);
			    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
			    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
			    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
			    
			    String incLock = licmeterList.get(i).get("incLock") == null ? "" : licmeterList.get(i).get("incLock").toString();
			    setText(getCell(sheet, rowNo+rowNum, colNo+10), incLock);
			    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
			    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
			    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
			    
			}
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));

			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));

			setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+5));

			setText(getCell(sheet, rowNo+rowNum, colNo+6), "현장 센서값");
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+8).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+6, colNo+8));

			setText(getCell(sheet, rowNo+rowNum, colNo+9), "시스템 센서값");
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+9, colNo+11));

			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);

			setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);

			setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
			
			for(int i =0;i<licmeterList.size();i++) {
				String msSensor = licmeterList.get(i).get("licmeterper").toString();
				JSONArray eqpArr = new JSONArray(msSensor);
				for (int j = 0; j < eqpArr.length(); j++) {
					rowNum++;
					JSONObject licObj = eqpArr.getJSONObject(j);
					
					String licmeterchl = licObj.has("채널명") ? licObj.get("채널명").toString() : "";
					setText(getCell(sheet, rowNo+rowNum, colNo+1), licmeterchl);
					getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);

					String incperChkresult = licObj.has("점검결과") ? licObj.get("점검결과").toString() : "";
					incperChkresult = incperChkresult.equals("양호") ? "O" : "";
					setText(getCell(sheet, rowNo+rowNum, colNo+2), incperChkresult);
					getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);

					incperChkresult = licObj.has("점검결과") ? licObj.get("점검결과").toString() : "";
					incperChkresult = incperChkresult.equals("불량") ? "O" : "";
					setText(getCell(sheet, rowNo+rowNum, colNo+3), incperChkresult);
					getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);

					String incperBadsttus = licObj.has("불량상태") ? licObj.get("불량상태").toString() : "";
					setText(getCell(sheet, rowNo+rowNum, colNo+4), incperBadsttus);
					getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));

					String incperSptsensor = licObj.has("현장 센서값") ? licObj.get("현장 센서값").toString() : "";
					setText(getCell(sheet, rowNo+rowNum, colNo+6), incperSptsensor);
					getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+8));

					String incperSyssensor = licObj.has("시스템 센서값") ? licObj.get("시스템 센서값").toString() : "";
					setText(getCell(sheet, rowNo+rowNum, colNo+9), incperSyssensor);
					getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+9, colNo+11));
				};				
			}
			rowNum++;
		}
		
		// 지하수위계
		if(gwgaugeList.size() > 0 ) {
			int msSize = gwgaugeList.size()*2 + 4;
			setText(getCell(sheet, rowNo+rowNum, colNo), "지하수위계");
			for(int i = 0;i<msSize;i++) {
				getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
			}
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+4), "케이블");
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+6), "보호함체");
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+8), "전원");
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+10), "자물쇠");
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
			
			for(int i =0;i<gwgaugeList.size();i++) {
				rowNum++;
				
				String gwgaugechl = gwgaugeList.get(i).get("gwgaugechl") == null ? "" : gwgaugeList.get(i).get("gwgaugechl").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+1), gwgaugechl);
				getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
				
				String wlSensor = gwgaugeList.get(i).get("wlSensor") == null ? "" : gwgaugeList.get(i).get("wlSensor").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+2), wlSensor);
				getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
				
				String wlCable = gwgaugeList.get(i).get("wlCable") == null ? "" : gwgaugeList.get(i).get("wlCable").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+4), wlCable);
				getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
				
				String wlPrtcbox = gwgaugeList.get(i).get("wlPrtcbox") == null ? "" : gwgaugeList.get(i).get("wlPrtcbox").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+6), wlPrtcbox);
				getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
				
				String wlPower = gwgaugeList.get(i).get("wlPower") == null ? "" : gwgaugeList.get(i).get("wlPower").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+8), wlPower);
				getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
                sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
				
				String wlLock = gwgaugeList.get(i).get("wlLock") == null ? "" : gwgaugeList.get(i).get("wlLock").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+10), wlLock);
				getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
                sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
				
			}
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
			getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+7));
			
			setText(getCell(sheet, rowNo+rowNum, colNo+8), "현장 수위값");
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));

			setText(getCell(sheet, rowNo+rowNum, colNo+10), "시스템 센서값");
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
			
			rowNum++;
			setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
			getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
			getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
			
			setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
			getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
			getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
			sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+7));

            setText(getCell(sheet, rowNo+rowNum, colNo+8), "노드");
			getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);

            setText(getCell(sheet, rowNo+rowNum, colNo+9), "수동");
			getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);

            setText(getCell(sheet, rowNo+rowNum, colNo+10), "RAW");
			getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);

            setText(getCell(sheet, rowNo+rowNum, colNo+11), "수위");
			getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
			
			for(int i =0;i<gwgaugeList.size();i++) {
				rowNum++;
				
				String gwgaugechl = gwgaugeList.get(i).get("gwgaugechl") == null ? "" : gwgaugeList.get(i).get("gwgaugechl").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+1), gwgaugechl);
				getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
				
				String wlperChkresult = gwgaugeList.get(i).get("wlperChkresult") == null ? "" : gwgaugeList.get(i).get("wlperChkresult").toString();
				wlperChkresult = wlperChkresult.equals("양호") ? "O" : "";
				setText(getCell(sheet, rowNo+rowNum, colNo+2), wlperChkresult);
				getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
				
				wlperChkresult = gwgaugeList.get(i).get("wlperChkresult") == null ? "" : gwgaugeList.get(i).get("wlperChkresult").toString();
				wlperChkresult = wlperChkresult.equals("불량") ? "O" : "";
				setText(getCell(sheet, rowNo+rowNum, colNo+3), wlperChkresult);
				getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
				
				String wlperBadsttus = gwgaugeList.get(i).get("wlperBadsttus") == null ? "" : gwgaugeList.get(i).get("wlperBadsttus").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+4), wlperBadsttus);
				getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
				getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
				sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+7));
				
				String wlperSptwalnode = gwgaugeList.get(i).get("wlperSptwalnode") == null ? "" : gwgaugeList.get(i).get("wlperSptwalnode").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+8), wlperSptwalnode);
				getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);

				String wlperSptwalpass = gwgaugeList.get(i).get("wlperSptwalpass") == null ? "" : gwgaugeList.get(i).get("wlperSptwalpass").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+9), wlperSptwalpass);
				getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);

				String wlperSyssnsraw = gwgaugeList.get(i).get("wlperSyssnsraw") == null ? "" : gwgaugeList.get(i).get("wlperSyssnsraw").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+10), wlperSyssnsraw);
				getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);

				String wlperSyssnswal = gwgaugeList.get(i).get("wlperSyssnswal") == null ? "" : gwgaugeList.get(i).get("wlperSyssnswal").toString();
				setText(getCell(sheet, rowNo+rowNum, colNo+11), wlperSyssnswal);
				getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);	
			}
            rowNum++;
		}
		
		// 강우계
		if(raingaugeList.size() > 0 ) {
		    int msSize = raingaugeList.size()*2 + 4;
		    setText(getCell(sheet, rowNo+rowNum, colNo), "강우계");
		    for(int i = 0;i<msSize;i++) {
		        getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
		    }
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "케이블");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "보호함체");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "전원");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+10), "자물쇠");
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    
		    for(int i =0;i<raingaugeList.size();i++) {
		        rowNum++;
		        
		        String raingaugechl = raingaugeList.get(i).get("raingaugechl") == null ? "" : raingaugeList.get(i).get("raingaugechl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), raingaugechl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String rgSensor = raingaugeList.get(i).get("rgSensor") == null ? "" : raingaugeList.get(i).get("rgSensor").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), rgSensor);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
		        
		        String rgCable = raingaugeList.get(i).get("rgCable") == null ? "" : raingaugeList.get(i).get("rgCable").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), rgCable);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		        
		        String rgPrtcbox = raingaugeList.get(i).get("rgPrtcbox") == null ? "" : raingaugeList.get(i).get("rgPrtcbox").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+6), rgPrtcbox);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
		        
		        String rgPower = raingaugeList.get(i).get("rgPower") == null ? "" : raingaugeList.get(i).get("rgPower").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), rgPower);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
		        
		        String rgLock = raingaugeList.get(i).get("rgLock") == null ? "" : raingaugeList.get(i).get("rgLock").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+10), rgLock);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
		        
		    }
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+11));    

		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+11));
		    
		    for(int i =0;i<raingaugeList.size();i++) {
		        rowNum++;
		        
		        String raingaugechl = raingaugeList.get(i).get("raingaugechl") == null ? "" : raingaugeList.get(i).get("raingaugechl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), raingaugechl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String rgperChkresult = raingaugeList.get(i).get("rgperChkresult") == null ? "" : raingaugeList.get(i).get("rgperChkresult").toString();
		        rgperChkresult = rgperChkresult.equals("양호") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), rgperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        
		        rgperChkresult = raingaugeList.get(i).get("rgperChkresult") == null ? "" : raingaugeList.get(i).get("rgperChkresult").toString();
		        rgperChkresult = rgperChkresult.equals("불량") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+3), rgperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        
		        String rgperBadsttus = raingaugeList.get(i).get("rgperBadsttus") == null ? "" : raingaugeList.get(i).get("rgperBadsttus").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), rgperBadsttus);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+11));
		    }
		    rowNum++;
		}
		
		// 구조물변위계
		if(strcdpmList.size() > 0 ) {
		    int msSize = strcdpmList.size()*2 + 4;
		    setText(getCell(sheet, rowNo+rowNum, colNo), "구조물변위계");
		    for(int i = 0;i<msSize;i++) {
		        getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
		    }
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "케이블");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "보호함체");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "전원");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+10), "자물쇠");
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
		    
		    for(int i =0;i<strcdpmList.size();i++) {
		        rowNum++;
		        
		        String strcdpmchl = strcdpmList.get(i).get("strcdpmchl") == null ? "" : strcdpmList.get(i).get("strcdpmchl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), strcdpmchl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String tmSensor = strcdpmList.get(i).get("tmSensor") == null ? "" : strcdpmList.get(i).get("tmSensor").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), tmSensor);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));
		        
		        String tmCable = strcdpmList.get(i).get("tmCable") == null ? "" : strcdpmList.get(i).get("tmCable").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), tmCable);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		        
		        String tmPrtcbox = strcdpmList.get(i).get("tmPrtcbox") == null ? "" : strcdpmList.get(i).get("tmPrtcbox").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+6), tmPrtcbox);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+7));
		        
		        String tmPower = strcdpmList.get(i).get("tmPower") == null ? "" : strcdpmList.get(i).get("tmPower").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), tmPower);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+9));
		        
		        String tmLock = strcdpmList.get(i).get("tmLock") == null ? "" : strcdpmList.get(i).get("tmLock").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+10), tmLock);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+10, colNo+11));
		    }
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+5));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "현장 센서값");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+8).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+6, colNo+8));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+9), "시스템 센서값");
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+9, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		    
		    for(int i =0;i<strcdpmList.size();i++) {
		        rowNum++;
		        
		        String strcdpmchl = strcdpmList.get(i).get("strcdpmchl") == null ? "" : strcdpmList.get(i).get("strcdpmchl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), strcdpmchl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String tmperChkresult = strcdpmList.get(i).get("tmperChkresult") == null ? "" : strcdpmList.get(i).get("tmperChkresult").toString();
		        tmperChkresult = tmperChkresult.equals("양호") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), tmperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        
		        tmperChkresult = strcdpmList.get(i).get("tmperChkresult") == null ? "" : strcdpmList.get(i).get("tmperChkresult").toString();
		        tmperChkresult = tmperChkresult.equals("불량") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+3), tmperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        
		        String tmperBadsttus = strcdpmList.get(i).get("tmperBadsttus") == null ? "" : strcdpmList.get(i).get("tmperBadsttus").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), tmperBadsttus);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		        
		        String tmperSptsensor = strcdpmList.get(i).get("tmperSptsensor") == null ? "" : strcdpmList.get(i).get("tmperSptsensor").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+6), tmperSptsensor);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+8));
		        
		        String tmperSyssensor = strcdpmList.get(i).get("tmperSyssensor") == null ? "" : strcdpmList.get(i).get("tmperSyssensor").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+9), tmperSyssensor);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+9, colNo+11));
		    }
		    rowNum++;
		}
		
		// 지표변위계
		if(surdpmList.size() > 0 ) {
		    int msSize = surdpmList.size()*2 + 4;
		    setText(getCell(sheet, rowNo+rowNum, colNo), "지표변위계");
		    for(int i = 0;i<msSize;i++) {
		        getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
		    }
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+4));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+5), "케이블");
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+5, colNo+7));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "전원");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+11));
		    
		    for(int i =0;i<surdpmList.size();i++) {
		        rowNum++;
		        
		        String surdpmchl = surdpmList.get(i).get("surdpmchl") == null ? "" : surdpmList.get(i).get("surdpmchl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), surdpmchl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String sdSensor = surdpmList.get(i).get("sdSensor") == null ? "" : surdpmList.get(i).get("sdSensor").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), sdSensor);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+4));
		        
		        String sdCable = surdpmList.get(i).get("sdCable") == null ? "" : surdpmList.get(i).get("sdCable").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+5), sdCable);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+5, colNo+7));
		        
		        String sdPower = surdpmList.get(i).get("sdPower") == null ? "" : surdpmList.get(i).get("sdPower").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), sdPower);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+11));
		    }
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+5));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "현장 센서값");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+8));

		    setText(getCell(sheet, rowNo+rowNum, colNo+9), "시스템 센서값");
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+9, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));

		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "X");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);

		    setText(getCell(sheet, rowNo+rowNum, colNo+7), "Y");
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);

		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "가속도");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);

		    setText(getCell(sheet, rowNo+rowNum, colNo+9), "X");
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);

		    setText(getCell(sheet, rowNo+rowNum, colNo+10), "Y");
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);

		    setText(getCell(sheet, rowNo+rowNum, colNo+11), "가속도");
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    
		    for(int i =0;i<surdpmList.size();i++) {
		        rowNum++;
		        
		        String surdpmchl = surdpmList.get(i).get("surdpmchl") == null ? "" : surdpmList.get(i).get("surdpmchl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), surdpmchl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String sdperChkresult = surdpmList.get(i).get("sdperChkresult") == null ? "" : surdpmList.get(i).get("sdperChkresult").toString();
		        sdperChkresult = sdperChkresult.equals("양호") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), sdperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        
		        sdperChkresult = surdpmList.get(i).get("sdperChkresult") == null ? "" : surdpmList.get(i).get("sdperChkresult").toString();
		        sdperChkresult = sdperChkresult.equals("불량") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+3), sdperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        
		        String sdperBadsttus = surdpmList.get(i).get("sdperBadsttus") == null ? "" : surdpmList.get(i).get("sdperBadsttus").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), sdperBadsttus);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+5));
		        
		        String sdperSptsensorX = surdpmList.get(i).get("sdperSptsensorX") == null ? "" : surdpmList.get(i).get("sdperSptsensorX").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+6), sdperSptsensorX);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);

		        String sdperSptsensorY = surdpmList.get(i).get("sdperSptsensorY") == null ? "" : surdpmList.get(i).get("sdperSptsensorY").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+7), sdperSptsensorY);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);

		        String sdperSptsensorAc = surdpmList.get(i).get("sdperSptsensorAc") == null ? "" : surdpmList.get(i).get("sdperSptsensorAc").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), sdperSptsensorAc);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);

		        String sdperSyssensorX = surdpmList.get(i).get("sdperSyssensorX") == null ? "" : surdpmList.get(i).get("sdperSyssensorX").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+9), sdperSyssensorX);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);

		        String sdperSyssensorY = surdpmList.get(i).get("sdperSyssensorY") == null ? "" : surdpmList.get(i).get("sdperSyssensorY").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+10), sdperSyssensorY);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);

		        String sdperSyssensorAc = surdpmList.get(i).get("sdperSyssensorAc") == null ? "" : surdpmList.get(i).get("sdperSyssensorAc").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+11), sdperSyssensorAc);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);	
		    }
		    rowNum++;
		}
		
		// GPS변위계
		if(gpsgaugeList.size() > 0 ) {
		    int msSize = gpsgaugeList.size()*2 + 4;
		    setText(getCell(sheet, rowNo+rowNum, colNo), "GPS변위계");
		    for(int i = 0;i<msSize;i++) {
		        getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
		    }
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "외관 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "센서부");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+4));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+5), "케이블");
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+5, colNo+7));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "전원");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+9, colNo+11));
		    
		    for(int i =0;i<gpsgaugeList.size();i++) {
		        rowNum++;
		        
		        String gpsgaugechl = gpsgaugeList.get(i).get("gpsgaugechl") == null ? "" : gpsgaugeList.get(i).get("gpsgaugechl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), gpsgaugechl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String gpsSensor = gpsgaugeList.get(i).get("gpsSensor") == null ? "" : gpsgaugeList.get(i).get("gpsSensor").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), gpsSensor);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+4));
		        
		        String gpsCable = gpsgaugeList.get(i).get("gpsCable") == null ? "" : gpsgaugeList.get(i).get("gpsCable").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+5), gpsCable);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+5, colNo+7));
		        
		        String gpsPower = gpsgaugeList.get(i).get("gpsPower") == null ? "" : gpsgaugeList.get(i).get("gpsPower").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), gpsPower);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+8, colNo+11));
		    }
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "채널명");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+1, colNo+1));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "점검결과");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+11));    

		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+3), "불량");
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "불량 상태");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+11));
		    
		    for(int i =0;i<gpsgaugeList.size();i++) {
		        rowNum++;
		        
		        String gpsgaugechl = gpsgaugeList.get(i).get("gpsgaugechl") == null ? "" : gpsgaugeList.get(i).get("gpsgaugechl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), gpsgaugechl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String gpsperChkresult = gpsgaugeList.get(i).get("gpsperChkresult") == null ? "" : gpsgaugeList.get(i).get("gpsperChkresult").toString();
		        gpsperChkresult = gpsperChkresult.equals("양호") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+2), gpsperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		        
		        gpsperChkresult = gpsgaugeList.get(i).get("gpsperChkresult") == null ? "" : gpsgaugeList.get(i).get("gpsperChkresult").toString();
		        gpsperChkresult = gpsperChkresult.equals("불량") ? "O" : "";
		        setText(getCell(sheet, rowNo+rowNum, colNo+3), gpsperChkresult);
		        getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		        
		        String gpsperBadsttus = gpsgaugeList.get(i).get("gpsperBadsttus") == null ? "" : gpsgaugeList.get(i).get("gpsperBadsttus").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), gpsperBadsttus);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+4, colNo+11));
		    }
		    rowNum++;
		}
		
		// 게이트웨이
		if(gatewayList.size() > 0 ) {
		    int msSize = gatewayList.size() + 2;
		    setText(getCell(sheet, rowNo+rowNum, colNo), "게이트웨이");
		    for(int i = 0;i<msSize;i++) {
		        getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
		    }
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+5));

		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "외관 점검");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "연번");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "배터리 전압");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+4));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+5), "불량 상태");
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+5, colNo+5));

		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "태양광");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+6).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+6, colNo+6));

		    setText(getCell(sheet, rowNo+rowNum, colNo+7), "함체");
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+7).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+7, colNo+7));

		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "보호휀스");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+8).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+8, colNo+8));

		    setText(getCell(sheet, rowNo+rowNum, colNo+9), "지선");
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+9).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+9, colNo+9));

		    setText(getCell(sheet, rowNo+rowNum, colNo+10), "안내판");
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+10).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+10, colNo+10));

		    setText(getCell(sheet, rowNo+rowNum, colNo+11), "자물쇠");
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+11, colNo+11));

		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호 전압");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));

		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "측정");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);

		    for(int i =0;i<gatewayList.size();i++) {
		        rowNum++;
		        
		        if(i == 0) {
		    		setText(getCell(sheet, rowNo+rowNum, colNo+2), "12V ~ 10V");
		    		setText(getCell(sheet, rowNo+rowNum, colNo+3), "3.6V ~ 3.0V");
		        	for(int j = 0;j<gatewayList.size()-1;j++) {
		        		getCell(sheet, rowNo+(rowNum+j), colNo+2).setCellStyle(HeaderStyle1_b);				
		        		getCell(sheet, rowNo+(rowNum+j), colNo+3).setCellStyle(HeaderStyle1_b);						        		
		        	}
		        	sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+gatewayList.size()-1), colNo+2, colNo+2));
		        	sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+gatewayList.size()-1), colNo+3, colNo+3));
		        }
		        
		        String gatewaychl = gatewayList.get(i).get("gatewaychl") == null ? "" : gatewayList.get(i).get("gatewaychl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), gatewaychl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String gatewayMsvolt = gatewayList.get(i).get("gatewayMsvolt") == null ? "" : gatewayList.get(i).get("gatewayMsvolt").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), gatewayMsvolt);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);

		        String gatewayBadsttus = gatewayList.get(i).get("gatewayBadsttus") == null ? "" : gatewayList.get(i).get("gatewayBadsttus").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+5), gatewayBadsttus);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);

		        String gatewaySolar = gatewayList.get(i).get("gatewaySolar") == null ? "" : gatewayList.get(i).get("gatewaySolar").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+6), gatewaySolar);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);

		        String gatewayBox = gatewayList.get(i).get("gatewayBox") == null ? "" : gatewayList.get(i).get("gatewayBox").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+7), gatewayBox);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);

		        String gatewayPrtcfence = gatewayList.get(i).get("gatewayPrtcfence") == null ? "" : gatewayList.get(i).get("gatewayPrtcfence").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), gatewayPrtcfence);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);

		        String gatewayBrnchln = gatewayList.get(i).get("gatewayBrnchln") == null ? "" : gatewayList.get(i).get("gatewayBrnchln").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+9), gatewayBrnchln);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);

		        String gatewayBoard = gatewayList.get(i).get("gatewayBoard") == null ? "" : gatewayList.get(i).get("gatewayBoard").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+10), gatewayBoard);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);

		        String gatewayLock = gatewayList.get(i).get("gatewayLock") == null ? "" : gatewayList.get(i).get("gatewayLock").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+11), gatewayLock);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);   
		    }
		    rowNum++;
		}
		
		// 노드
		if(nodeList.size() > 0 ) {
		    int msSize = nodeList.size() + 2;
		    setText(getCell(sheet, rowNo+rowNum, colNo), "노드");
		    for(int i = 0;i<msSize;i++) {
		        getCell(sheet, rowNo+(rowNum+i), colNo).setCellStyle(HeaderStyle1_b);				
		    }
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+msSize), colNo, colNo));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "성능 점검");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+5));

		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "외관 점검");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+6, colNo+11));
		    
		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+1), "연번");
		    getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+1).setCellStyle(HeaderStyle1_b);
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "배터리 전압");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+4));
		    
		    setText(getCell(sheet, rowNo+rowNum, colNo+5), "불량 상태");
		    getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+5).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+5, colNo+5));

		    setText(getCell(sheet, rowNo+rowNum, colNo+6), "태양광");
		    getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+6).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+6, colNo+6));

		    setText(getCell(sheet, rowNo+rowNum, colNo+7), "함체");
		    getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+7).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+7, colNo+7));

		    setText(getCell(sheet, rowNo+rowNum, colNo+8), "보호휀스");
		    getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+8).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+8, colNo+8));

		    setText(getCell(sheet, rowNo+rowNum, colNo+9), "지선");
		    getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+9).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+9, colNo+9));

		    setText(getCell(sheet, rowNo+rowNum, colNo+10), "안내판");
		    getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+10).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+10, colNo+10));

		    setText(getCell(sheet, rowNo+rowNum, colNo+11), "자물쇠");
		    getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+(rowNum+1), colNo+11).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+1), colNo+11, colNo+11));

		    rowNum++;
		    setText(getCell(sheet, rowNo+rowNum, colNo+2), "양호 전압");
		    getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1_b);
		    getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1_b);
		    sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+2, colNo+3));

		    setText(getCell(sheet, rowNo+rowNum, colNo+4), "측정");
		    getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1_b);

		    for(int i =0;i<nodeList.size();i++) {
		        rowNum++;
		        
		        if(i == 0) {
		            setText(getCell(sheet, rowNo+rowNum, colNo+2), "12V ~ 10V");
		            setText(getCell(sheet, rowNo+rowNum, colNo+3), "3.6V ~ 3.0V");
		            for(int j = 0;j<nodeList.size()-1;j++) {
		                getCell(sheet, rowNo+(rowNum+j), colNo+2).setCellStyle(HeaderStyle1_b);				
		                getCell(sheet, rowNo+(rowNum+j), colNo+3).setCellStyle(HeaderStyle1_b);						        		
		            }
		            sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+nodeList.size()-1), colNo+2, colNo+2));
		            sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+nodeList.size()-1), colNo+3, colNo+3));
		        }
		        
		        String nodechl = nodeList.get(i).get("nodechl") == null ? "" : nodeList.get(i).get("nodechl").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+1), nodechl);
		        getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		        
		        String nodeMs = nodeList.get(i).get("nodeMs") == null ? "" : nodeList.get(i).get("nodeMs").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+4), nodeMs);
		        getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);

		        String nodeBadsttus = nodeList.get(i).get("nodeBadsttus") == null ? "" : nodeList.get(i).get("nodeBadsttus").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+5), nodeBadsttus);
		        getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);

		        String nodeSolar = nodeList.get(i).get("nodeSolar") == null ? "" : nodeList.get(i).get("nodeSolar").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+6), nodeSolar);
		        getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);

		        String nodeBox = nodeList.get(i).get("nodeBox") == null ? "" : nodeList.get(i).get("nodeBox").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+7), nodeBox);
		        getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);

		        String nodePrtcfence = nodeList.get(i).get("nodePrtcfence") == null ? "" : nodeList.get(i).get("nodePrtcfence").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+8), nodePrtcfence);
		        getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);

		        String nodeBrnchln = nodeList.get(i).get("nodeBrnchln") == null ? "" : nodeList.get(i).get("nodeBrnchln").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+9), nodeBrnchln);
		        getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);

		        String nodeBoard = nodeList.get(i).get("nodeBoard") == null ? "" : nodeList.get(i).get("nodeBoard").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+10), nodeBoard);
		        getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);

		        String nodeLock = nodeList.get(i).get("nodeLock") == null ? "" : nodeList.get(i).get("nodeLock").toString();
		        setText(getCell(sheet, rowNo+rowNum, colNo+11), nodeLock);
		        getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);   
		    }
		    rowNum++;
		}
		
		//종합의견
		setText(getCell(sheet, rowNo+rowNum, colNo), "종합의견");
		getCell(sheet, rowNo+rowNum, colNo).setCellStyle(HeaderStyle1_b);
		
		String opinion1 = dataList.get(0).get("opinion1") == null ? "" : dataList.get(0).get("opinion1").toString();
		setText(getCell(sheet, rowNo+rowNum, colNo+1), opinion1);
		getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+6).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+7).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+8).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+9).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+10).setCellStyle(HeaderStyle1);
		getCell(sheet, rowNo+rowNum, colNo+11).setCellStyle(HeaderStyle1);
		sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo+1, colNo+11));
		
		rowNum++;
		
		
		// 사진태그
		if(photoTagList.size() > 0 ) {
			int cnt = 0;
			for(int i = 0;i<photoTagList.size();i++) {
				String photoTag = photoTagList.get(i).get("tag").toString();
				String photo = fileStoreDir+photoTagList.get(i).get("img").toString().substring(1);
				File getFile = new File(photo);
				
				InputStream inputStream = new FileInputStream(getFile);
				byte[] bytes = IOUtils.toByteArray(inputStream);
				inputStream.close();
				int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
				XSSFCreationHelper helper = wb.getCreationHelper();
				XSSFDrawing drawing = sheet.createDrawingPatriarch();
				XSSFClientAnchor anchor = helper.createClientAnchor();
				if(cnt == 0) {
					anchor.setRow1(rowNum+1);
					anchor.setRow2(rowNum+18);
					anchor.setCol1(0);		
					anchor.setCol2(6);		
					
					setText(getCell(sheet, rowNo+rowNum, colNo), photoTag);
					getCell(sheet, rowNo+rowNum, colNo).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+1).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+2).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+3).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+4).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+rowNum, colNo+5).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+rowNum, colNo, colNo+5));
					
					rowNum++;
					setText(getCell(sheet, rowNo+rowNum, colNo), "");
					sheet.addMergedRegion(new CellRangeAddress(rowNo+rowNum, rowNo+(rowNum+17), colNo, colNo+5));
					rowNum+=17;
					cnt++;
				}else if(cnt == 1) {
					anchor.setRow1(rowNum-17);
					anchor.setRow2(rowNum);
					anchor.setCol1(6);		
					anchor.setCol2(12);
					
					setText(getCell(sheet, rowNo+(rowNum-18), colNo+6), photoTag);
					getCell(sheet, rowNo+(rowNum-18), colNo+6).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+(rowNum-18), colNo+7).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+(rowNum-18), colNo+8).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+(rowNum-18), colNo+9).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+(rowNum-18), colNo+10).setCellStyle(HeaderStyle1);
					getCell(sheet, rowNo+(rowNum-18), colNo+11).setCellStyle(HeaderStyle1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo+(rowNum-18), rowNo+(rowNum-18), colNo+6, colNo+11));
					
					setText(getCell(sheet, rowNo+rowNum, colNo), "");
					sheet.addMergedRegion(new CellRangeAddress(rowNo+(rowNum-17), rowNo+rowNum, colNo+6, colNo+11));
					rowNum++;
					cnt = 0;
				}
				anchor.setDx1(0);
		    	anchor.setDx2(1000); 
		    	anchor.setDy1(0);
		    	anchor.setDy2(1000);
				XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
//				pict.resize();
//				pict.resize(1,1.051);
				
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
}