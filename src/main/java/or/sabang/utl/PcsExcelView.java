package or.sabang.utl;

import java.awt.Color;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 정밀점검 엑셀 처리
 * @author DEVWORK
 *
 */
public class PcsExcelView extends AbstractView{

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
	
	public PcsExcelView() {

	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}



	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
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
	}

	   @SuppressWarnings("unchecked")
	   protected void buildExcelDocument(Map model, XSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp)
	         throws Exception {
	      Map<String, Object> dataMap = (Map<String, Object>) model.get("dataMap");
	      
	      String svyType = req.getParameter("svyType");
	      
	      XSSFCell cell = null;
	      
	      String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름

	      List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트

	      XSSFSheet sheet = wb.createSheet(sheetNm);
	      sheet.setDefaultColumnWidth(10);
	      Row row = sheet.createRow(1);

	      int rowNo = 0, colNo = 0;
	      
	      setText(getCell(sheet, rowNo, colNo), "일련번호"); //0
          getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+1), "조사ID"); //1
          getCell(sheet, rowNo, colNo+1).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+2), "사방댐종류");//2
          getCell(sheet, rowNo, colNo+2).setCellStyle(HeaderStyle1); 
          setText(getCell(sheet, rowNo, colNo+3), "형식");//3
          getCell(sheet, rowNo, colNo+3).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+4), "사방댐관리번호");//4
          getCell(sheet, rowNo, colNo+4).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+5), "점검일시");//5
          getCell(sheet, rowNo, colNo+5).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+6), "국가지점번호");//6
          getCell(sheet, rowNo, colNo+6).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+7), "점검자");//7
          getCell(sheet, rowNo, colNo+7).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+8), "시도");//8
          getCell(sheet, rowNo, colNo+8).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+9), "시군구");//9
          getCell(sheet, rowNo, colNo+9).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+10), "읍면동");//10
          getCell(sheet, rowNo, colNo+10).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+11), "리");//11
          getCell(sheet, rowNo, colNo+11).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+12), "지번");//12
          getCell(sheet, rowNo, colNo+12).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+13), "시설년도");//13
          getCell(sheet, rowNo, colNo+13).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+14), "상장(m)");//14
          getCell(sheet, rowNo, colNo+14).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+15), "하장(m)");//15
          getCell(sheet, rowNo, colNo+15).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+16), "높이(m) 전고");//16
          getCell(sheet, rowNo, colNo+16).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+17), "높이(m) 유효고");//17
          getCell(sheet, rowNo, colNo+17).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+18), "천단폭(m)");//18
          getCell(sheet, rowNo, colNo+18).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+19), "시공비(천원)");//19
          getCell(sheet, rowNo, colNo+19).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+20), "위도(산사태)");//20
          getCell(sheet, rowNo, colNo+20).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+21), "위분(산사태)");//21
          getCell(sheet, rowNo, colNo+21).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+22), "위초(산사태)");//22
          getCell(sheet, rowNo, colNo+22).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+23), "경도(산사태)");//23
          getCell(sheet, rowNo, colNo+23).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+24), "경분(산사태)");//24
          getCell(sheet, rowNo, colNo+24).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+25), "경초(산사태)");//25
          getCell(sheet, rowNo, colNo+25).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+26), "위도(현지계측)");//26
          getCell(sheet, rowNo, colNo+26).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+27), "위분(현지계측)");//27
          getCell(sheet, rowNo, colNo+27).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+28), "위초(현지계측)");//28
          getCell(sheet, rowNo, colNo+28).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+29), "경도(현지계측)");//29
          getCell(sheet, rowNo, colNo+29).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+30), "경분(현지계측)");//30
          getCell(sheet, rowNo, colNo+30).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+31), "경초(현지계측)");//31
          getCell(sheet, rowNo, colNo+31).setCellStyle(HeaderStyle1);
          //가로병합6(총 열 18 - 결함사항,평가,평가점수)
          setText(getCell(sheet, rowNo, colNo+32), "본댐");//32
          getCell(sheet, rowNo, colNo+32).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+33), "");//33
          getCell(sheet, rowNo, colNo+33).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+34), "");//34
          getCell(sheet, rowNo, colNo+34).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+35), "");//35
          getCell(sheet, rowNo, colNo+35).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+36), "");//36
          getCell(sheet, rowNo, colNo+36).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+37), "");//37
          getCell(sheet, rowNo, colNo+37).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+38), "");//38
          getCell(sheet, rowNo, colNo+38).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+39), "");//39
          getCell(sheet, rowNo, colNo+39).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+40), "");//40
          getCell(sheet, rowNo, colNo+40).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+41), "");//41
          getCell(sheet, rowNo, colNo+41).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+42), "");//42
          getCell(sheet, rowNo, colNo+42).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+43), "");//43
          getCell(sheet, rowNo, colNo+43).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+44), "");//44
          getCell(sheet, rowNo, colNo+44).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+45), "");//45
          getCell(sheet, rowNo, colNo+45).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+46), "");//46
          getCell(sheet, rowNo, colNo+46).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+47), "");//47
          getCell(sheet, rowNo, colNo+47).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+48), "");//48
          getCell(sheet, rowNo, colNo+48).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+49), "");//49
          getCell(sheet, rowNo, colNo+49).setCellStyle(HeaderStyle1);
          //가로병합3(총 열 6 - 결함사항,평가,평가점수)
          setText(getCell(sheet, rowNo, colNo+50), "측벽");//50
          getCell(sheet, rowNo, colNo+50).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+51), "");//51
          getCell(sheet, rowNo, colNo+51).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+52), "");//52
          getCell(sheet, rowNo, colNo+52).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+53), "");//53
          getCell(sheet, rowNo, colNo+53).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+54), "");//54
          getCell(sheet, rowNo, colNo+54).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+55), "");//55
          getCell(sheet, rowNo, colNo+55).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+56), "");//56
          getCell(sheet, rowNo, colNo+56).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+57), "");//57
          getCell(sheet, rowNo, colNo+57).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+58), "");//58
          getCell(sheet, rowNo, colNo+58).setCellStyle(HeaderStyle1);
          //가로병합3(총 열 6 - 결함사항,평가,평가점수)
          setText(getCell(sheet, rowNo, colNo+59), "물받이");//59
          getCell(sheet, rowNo, colNo+59).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+60), "");//60
          getCell(sheet, rowNo, colNo+60).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+61), "");//61
          getCell(sheet, rowNo, colNo+61).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+62), "");//62
          getCell(sheet, rowNo, colNo+62).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+63), "");//63
          getCell(sheet, rowNo, colNo+63).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+64), "");//64
          getCell(sheet, rowNo, colNo+64).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+65), "");//65
          getCell(sheet, rowNo, colNo+65).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+66), "");//66
          getCell(sheet, rowNo, colNo+66).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+67), "");//67
          getCell(sheet, rowNo, colNo+67).setCellStyle(HeaderStyle1);
          //가로병합2
          setText(getCell(sheet, rowNo, colNo+68), "조사자보정");//68
          getCell(sheet, rowNo, colNo+68).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+69), "");//69
          getCell(sheet, rowNo, colNo+69).setCellStyle(HeaderStyle1);
        //가로병합6
          setText(getCell(sheet, rowNo, colNo+70), "비파괴검사");//70
          getCell(sheet, rowNo, colNo+70).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+71), "");//71
          getCell(sheet, rowNo, colNo+71).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+72), "");//72
          getCell(sheet, rowNo, colNo+72).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+73), "");//73
          getCell(sheet, rowNo, colNo+73).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+74), "");//74
          getCell(sheet, rowNo, colNo+74).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+75), "");//75
          getCell(sheet, rowNo, colNo+75).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo, colNo+76), "현재저사량");//76
          getCell(sheet, rowNo, colNo+76).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+77), "");//77
          getCell(sheet, rowNo, colNo+77).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+78), "");//78
          getCell(sheet, rowNo, colNo+78).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+79), "");//79
          getCell(sheet, rowNo, colNo+79).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+80), "");//80
          getCell(sheet, rowNo, colNo+80).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo, colNo+81), "생활권");//81
          getCell(sheet, rowNo, colNo+81).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+82), "");//82
          getCell(sheet, rowNo, colNo+82).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+83), "");//83
          getCell(sheet, rowNo, colNo+83).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+84), "");//84
          getCell(sheet, rowNo, colNo+84).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+85), "");//85
          getCell(sheet, rowNo, colNo+85).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo, colNo+86), "계상기울기");//86
          getCell(sheet, rowNo, colNo+86).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+87), "");//87
          getCell(sheet, rowNo, colNo+87).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+88), "");//88
          getCell(sheet, rowNo, colNo+88).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+89), "");//89
          getCell(sheet, rowNo, colNo+89).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+90), "");//90
          getCell(sheet, rowNo, colNo+90).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo, colNo+91), "토석이동량");//91
          getCell(sheet, rowNo, colNo+91).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+92), "");//92
          getCell(sheet, rowNo, colNo+92).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+93), "");//93
          getCell(sheet, rowNo, colNo+93).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+94), "");//94
          getCell(sheet, rowNo, colNo+94).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+95), "");//95
          getCell(sheet, rowNo, colNo+95).setCellStyle(HeaderStyle1);
          
          setText(getCell(sheet, rowNo, colNo+96), "총점");//96
          getCell(sheet, rowNo, colNo+96).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+97), "사방댐준설여부판정");//97
          getCell(sheet, rowNo, colNo+97).setCellStyle(HeaderStyle1);
          //가로병합5
          setText(getCell(sheet, rowNo, colNo+98), "종합의견");//98
          getCell(sheet, rowNo, colNo+98).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+99), "");//99
          getCell(sheet, rowNo, colNo+99).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+100), "");//100
          getCell(sheet, rowNo, colNo+100).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+101), "");//101
          getCell(sheet, rowNo, colNo+101).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo, colNo+102), "");//102
          getCell(sheet, rowNo, colNo+102).setCellStyle(HeaderStyle1);
          
          // 두번째 줄
          setText(getCell(sheet, rowNo+1, colNo), ""); //0
          getCell(sheet, rowNo+1, colNo).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+1), ""); //1
          getCell(sheet, rowNo+1, colNo+1).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+2), "");//2
          getCell(sheet, rowNo+1, colNo+2).setCellStyle(HeaderStyle1); 
          setText(getCell(sheet, rowNo+1, colNo+3), "");//3
          getCell(sheet, rowNo+1, colNo+3).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+4), "");//4
          getCell(sheet, rowNo+1, colNo+4).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+5), "");//5
          getCell(sheet, rowNo+1, colNo+5).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+6), "");//6
          getCell(sheet, rowNo+1, colNo+6).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+7), "");//7
          getCell(sheet, rowNo+1, colNo+7).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+8), "");//8
          getCell(sheet, rowNo+1, colNo+8).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+9), "");//9
          getCell(sheet, rowNo+1, colNo+9).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+10), "");//10
          getCell(sheet, rowNo+1, colNo+10).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+11), "");//11
          getCell(sheet, rowNo+1, colNo+11).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+12), "");//12
          getCell(sheet, rowNo+1, colNo+12).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+13), "");//13
          getCell(sheet, rowNo+1, colNo+13).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+14), "");//14
          getCell(sheet, rowNo+1, colNo+14).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+15), "");//15
          getCell(sheet, rowNo+1, colNo+15).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+16), "");//16
          getCell(sheet, rowNo+1, colNo+16).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+17), "");//17
          getCell(sheet, rowNo+1, colNo+17).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+18), "");//18
          getCell(sheet, rowNo+1, colNo+18).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+19), "");//19
          getCell(sheet, rowNo+1, colNo+19).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+20), "");//20
          getCell(sheet, rowNo+1, colNo+20).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+21), "");//21
          getCell(sheet, rowNo+1, colNo+21).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+22), "");//22
          getCell(sheet, rowNo+1, colNo+22).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+23), "");//23
          getCell(sheet, rowNo+1, colNo+23).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+24), "");//24
          getCell(sheet, rowNo+1, colNo+24).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+25), "");//25
          getCell(sheet, rowNo+1, colNo+25).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+26), "");//26
          getCell(sheet, rowNo+1, colNo+26).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+27), "");//27
          getCell(sheet, rowNo+1, colNo+27).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+28), "");//28
          getCell(sheet, rowNo+1, colNo+28).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+29), "");//29
          getCell(sheet, rowNo+1, colNo+29).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+30), "");//30
          getCell(sheet, rowNo+1, colNo+30).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+31), "");//31
          getCell(sheet, rowNo+1, colNo+31).setCellStyle(HeaderStyle1);
          //가로병합6(총 열 18 - 결함사항,평가,평가점수)
          setText(getCell(sheet, rowNo+1, colNo+32), "결함사항");//32
          getCell(sheet, rowNo+1, colNo+32).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+33), "평가기준");//33
          getCell(sheet, rowNo+1, colNo+33).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+34), "평가점수");//34
          getCell(sheet, rowNo+1, colNo+34).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+35), "결함사항");//35
          getCell(sheet, rowNo+1, colNo+35).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+36), "평가기준");//36
          getCell(sheet, rowNo+1, colNo+36).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+37), "평가점수");//37
          getCell(sheet, rowNo+1, colNo+37).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+38), "결함사항");//38
          getCell(sheet, rowNo+1, colNo+38).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+39), "평가기준");//39
          getCell(sheet, rowNo+1, colNo+39).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+40), "평가점수");//40
          getCell(sheet, rowNo+1, colNo+40).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+41), "결함사항");//41
          getCell(sheet, rowNo+1, colNo+41).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+42), "평가기준");//42
          getCell(sheet, rowNo+1, colNo+42).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+43), "평가점수");//43
          getCell(sheet, rowNo+1, colNo+43).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+44), "결함사항");//44
          getCell(sheet, rowNo+1, colNo+44).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+45), "평가기준");//45
          getCell(sheet, rowNo+1, colNo+45).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+46), "평가점수");//46
          getCell(sheet, rowNo+1, colNo+46).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+47), "결함사항");//47
          getCell(sheet, rowNo+1, colNo+47).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+48), "평가기준");//48
          getCell(sheet, rowNo+1, colNo+48).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+49), "평가점수");//49
          getCell(sheet, rowNo+1, colNo+49).setCellStyle(HeaderStyle1);
          //가로병합3(총 열 6 - 결함사항,평가,평가점수)
          setText(getCell(sheet, rowNo+1, colNo+50), "결함사항");//50
          getCell(sheet, rowNo+1, colNo+50).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+51), "평가기준");//51
          getCell(sheet, rowNo+1, colNo+51).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+52), "평가점수");//52
          getCell(sheet, rowNo+1, colNo+52).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+53), "결함사항");//53
          getCell(sheet, rowNo+1, colNo+53).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+54), "평가기준");//54
          getCell(sheet, rowNo+1, colNo+54).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+55), "평가점수");//55
          getCell(sheet, rowNo+1, colNo+55).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+56), "결함사항");//56
          getCell(sheet, rowNo+1, colNo+56).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+57), "평가기준");//57
          getCell(sheet, rowNo+1, colNo+57).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+58), "평가점수");//58
          getCell(sheet, rowNo+1, colNo+58).setCellStyle(HeaderStyle1);
          //가로병합3(총 열 6 - 결함사항,평가,평가점수)
          setText(getCell(sheet, rowNo+1, colNo+59), "결함사항");//59
          getCell(sheet, rowNo+1, colNo+59).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+60), "평가기준");//60
          getCell(sheet, rowNo+1, colNo+60).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+61), "평가점수");//61
          getCell(sheet, rowNo+1, colNo+61).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+62), "결함사항");//62
          getCell(sheet, rowNo+1, colNo+62).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+63), "평가기준");//63
          getCell(sheet, rowNo+1, colNo+63).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+64), "평가점수");//64
          getCell(sheet, rowNo+1, colNo+64).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+65), "결함사항");//65
          getCell(sheet, rowNo+1, colNo+65).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+66), "평가기준");//66
          getCell(sheet, rowNo+1, colNo+66).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+67), "평가점수");//67
          getCell(sheet, rowNo+1, colNo+67).setCellStyle(HeaderStyle1);
          //가로병합2
          setText(getCell(sheet, rowNo+1, colNo+68), "평가사항");//68
          getCell(sheet, rowNo+1, colNo+68).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+69), "평가점수");//69
          getCell(sheet, rowNo+1, colNo+69).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo+1, colNo+70), "결함사항");//70
          getCell(sheet, rowNo+1, colNo+70).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+71), "평가기준");//71
          getCell(sheet, rowNo+1, colNo+71).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+72), "평가점수");//72
          getCell(sheet, rowNo+1, colNo+72).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+73), "결함사항");//73
          getCell(sheet, rowNo+1, colNo+73).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+74), "평가기준");//74
          getCell(sheet, rowNo+1, colNo+74).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+75), "평가점수");//75
          getCell(sheet, rowNo+1, colNo+75).setCellStyle(HeaderStyle1);
          //가로병합5
          setText(getCell(sheet, rowNo+1, colNo+76), "구분기준");//76
          getCell(sheet, rowNo+1, colNo+76).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+77), "계수");//77
          getCell(sheet, rowNo+1, colNo+77).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+78), "가중치");//78
          getCell(sheet, rowNo+1, colNo+78).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+79), "준설기준값");//79
          getCell(sheet, rowNo+1, colNo+79).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+80), "비고");//80
          getCell(sheet, rowNo+1, colNo+80).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo+1, colNo+81), "구분기준");//81
          getCell(sheet, rowNo+1, colNo+81).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+82), "계수");//82
          getCell(sheet, rowNo+1, colNo+82).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+83), "가중치");//83
          getCell(sheet, rowNo+1, colNo+83).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+84), "준설기준값");//84
          getCell(sheet, rowNo+1, colNo+84).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+85), "비고");//85
          getCell(sheet, rowNo+1, colNo+85).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo+1, colNo+86), "구분기준");//86
          getCell(sheet, rowNo+1, colNo+86).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+87), "계수");//87
          getCell(sheet, rowNo+1, colNo+87).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+88), "가중치");//88
          getCell(sheet, rowNo+1, colNo+88).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+89), "준설기준값");//89
          getCell(sheet, rowNo+1, colNo+89).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+90), "비고");//90
          getCell(sheet, rowNo+1, colNo+90).setCellStyle(HeaderStyle1);
          //가로병합6
          setText(getCell(sheet, rowNo+1, colNo+91), "구분기준");//91
          getCell(sheet, rowNo+1, colNo+91).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+92), "계수");//92
          getCell(sheet, rowNo+1, colNo+92).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+93), "가중치");//93
          getCell(sheet, rowNo+1, colNo+93).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+94), "준설기준값");//94
          getCell(sheet, rowNo+1, colNo+94).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+95), "비고");//95
          getCell(sheet, rowNo+1, colNo+95).setCellStyle(HeaderStyle1);
          
          setText(getCell(sheet, rowNo+1, colNo+96), "");//96
          getCell(sheet, rowNo+1, colNo+96).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+97), "");//97
          getCell(sheet, rowNo+1, colNo+97).setCellStyle(HeaderStyle1);
          //가로병합5
          setText(getCell(sheet, rowNo+1, colNo+98), "종합의견1");//98
          getCell(sheet, rowNo+1, colNo+98).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+99), "종합의견2");//99
          getCell(sheet, rowNo+1, colNo+99).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+100), "종합의견3");//100
          getCell(sheet, rowNo+1, colNo+100).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+101), "종합의견4");//101
          getCell(sheet, rowNo+1, colNo+101).setCellStyle(HeaderStyle1);
          setText(getCell(sheet, rowNo+1, colNo+102), "종합의견5");//102
          getCell(sheet, rowNo+1, colNo+102).setCellStyle(HeaderStyle1);
          
          /*
           * CellRangeAddress params 
           * firstrow, lastrow, firstcol, lastcol
           * */
          for(int i=0;i<103;i++) {

        	  //본댐
        	  if(i==32) sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+17));
        	  //측벽 & 물받이
        	  if(i==50 || i==59) sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+8));
        	  // 조사자보정
        	  if(i==68) sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+1));
        	  // 비파괴 & 종합의견
        	  if(i==70) sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+5));
        	  // 준설 평가표
        	  if(i==76 || i==81 || i==86 || i==91 || i==98) sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+4));
        	  // 기타 항목 세로 병합
        	  if(i < 32 || i == 96 || i == 97) sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
          }
          
          colNo=0;
          rowNo=2;
          
          if(dataList.size() > 0) {
           	for(int i = 0; i < dataList.size(); i++) {
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sn").toString());//일련번호
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());//조사아이디
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ecnrknd").toString());//사방댐종류
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyform").toString());//형식
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ecnrrnum").toString());//관리번호
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());//점검일시
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("nationspotnum").toString());//국가지점번호
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyuser").toString());//점검자
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sd").toString());//시도
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sgg").toString());//시군구
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("emd").toString());//읍면동
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ri").toString());//리
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("jibun").toString());//지번
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltyear").toString());//시설년도
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltuprhg").toString());//상장
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltlwrhg").toString());//하장
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("tthghjdgval").toString());//높이(m) 전고
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltstkhg").toString());//높이(m) 유효고
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("wotdjdgval").toString());//천단폭
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cnstrcost").toString());//시공비
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("latdlndsld").toString());//위도_산사태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("latmlndsld").toString());//위분_산사태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("latslndsld").toString());//위초_산사태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("londlndsld").toString());//경도_산사태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lonmlndsld").toString());//경분_산사태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lonslndsld").toString());//경초_산사태
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("latdacplcsld").toString());//위도_현지계측
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("latmacplcsld").toString());//위분_현지계측
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("latsacplcsld").toString());//위초_현지계측
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("londacplcsld").toString());//경도_현지계측
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lonmacplcsld").toString());//경분_현지계측
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lonsacplcsld").toString());//경초_현지계측
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "파손, 변위, 변형");//본댐파손
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamdmg").toString());//본댐파손기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamdmgscore").toString());//본댐파손점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "균열, 누수");//본댐균열
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamcrk").toString());//본댐균열기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamcrkscore").toString());//본댐균열점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "기초부 세굴, 물받이와의 유탈");//본댐기초부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldambasicscour").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldambasicscourscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "콘크리트: 박리, 박락, 백화, 마모");//본댐박리
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamstrpn").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamstrpnscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "전석붙임: 이격,유실,풍화 및 채움콘크리트 열화");//본댐전석
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamplng").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamplngscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "수문시설 상태 등 기타 사항");//본댐전석
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamwtgate").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamwtgatescore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "파손, 변위, 변형");//측벽파손
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewalldmg").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewalldmgscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "균열, 누수");//측벽균열
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewallcrk").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewallcrkscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "기초부 세굴, 물받이 및 수직벽(앞댐)과의 유탈, 재료의 상태 등 기타사항");//측벽기초부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewallbasicscour").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewallbasicscourscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "파손, 변위, 변형");//물받이파손
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptdmg").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptdmgscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "기초부 세굴, 수직벽(앞댐)과의 유탈");//물받이기초부
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptbasicscour").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptbasicscourscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "균열, 재료의 상태 등 기타 사항");//물받이균열
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptcrk").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptcrkscore").toString());//점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svycrctn").toString());//조사자보정
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svycrctnscore").toString());//조사자보정점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "콘크리트 압축강도(슈미트해머)");//비파괴압축강도
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cncrtcmprsstrn").toString());//비파괴압축강도
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "");//비파괴압축강도점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "콘크리트 균열깊이(초음파탐상)");//비파괴균열깊이
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("cncrtcrkdpt").toString());//비파괴균열깊이
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "");//비파괴균열깊이점수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("nowsnddpsitvaldivision").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("nowsnddpsitvalcoeff").toString());//계수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("nowsnddpsitvalweight").toString());//가중치
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("nowsnddpsitvaldrdgn").toString());//기준값
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "");//비고
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lifezonedivision").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lifezonecoeff").toString());//계수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lifezoneweight").toString());//가중치
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("lifezonedrdgn").toString());//기준값
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "");//비고
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("riverbedgradientdivision").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("riverbedgradientcoeff").toString());//계수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("riverbedgradientweight").toString());//가중치
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("riverbedgradientdrdgn").toString());//기준값
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "");//비고
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilmoveamntdivision").toString());//기준
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilmoveamntcoeff").toString());//계수
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilmoveamntweight").toString());//가중치
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("soilmoveamntdrdgn").toString());//기준값
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), "");//비고
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("totalscore").toString());//총점
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("drdgnatjdg").toString());//사방댐 준설여부 판정
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString());//종합의견1
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString());//종합의견2
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString());//종합의견3
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion4").toString());//종합의견4
				getCell(sheet, rowNo, colNo++).setCellStyle(HeaderStyle1);
				setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion5").toString());//종합의견5
				getCell(sheet, rowNo++, colNo).setCellStyle(HeaderStyle1);
				
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
