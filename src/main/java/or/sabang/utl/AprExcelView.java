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

public class AprExcelView extends AbstractView {

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
   public AprExcelView() {
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
      
      String svyType = req.getParameter("svyType");
      
      XSSFCell cell = null;
      
      String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름

      List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트

      XSSFSheet sheet = wb.createSheet(sheetNm);
      sheet.setDefaultColumnWidth(10);
      Row row = sheet.createRow(1);

      int rowNo = 0, colNo = 0;
      
      if(svyType.equals("계류보전 외관점검")) {
         
            setText(getCell(sheet, rowNo, colNo), "일련번호");
            getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 1), "조사ID");
            getCell(sheet, rowNo, colNo + 1).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 2), "시설년도");
            getCell(sheet, rowNo, colNo + 2).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 3), "시도");
            getCell(sheet, rowNo, colNo + 3).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 4), "시군구");
            getCell(sheet, rowNo, colNo + 4).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 5), "읍면동");
            getCell(sheet, rowNo, colNo + 5).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 6), "리");
            getCell(sheet, rowNo, colNo + 6).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 7), "지번");
            getCell(sheet, rowNo, colNo + 7).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 8), "속칭");
            getCell(sheet, rowNo, colNo + 8).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 9), "지정면적");
            getCell(sheet, rowNo, colNo + 9).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 10), "점검일시");
            getCell(sheet, rowNo, colNo + 10).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 11), "점검자");
            getCell(sheet, rowNo, colNo + 11).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 12), "시점위도");
            getCell(sheet, rowNo, colNo + 12).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 13), "시점위분");
            getCell(sheet, rowNo, colNo + 13).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 14), "시점위초");
            getCell(sheet, rowNo, colNo + 14).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 15), "시점경도");
            getCell(sheet, rowNo, colNo + 15).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 16), "시점경분");
            getCell(sheet, rowNo, colNo + 16).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 17), "시점경초");
            getCell(sheet, rowNo, colNo + 17).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 18), "종점위도");
            getCell(sheet, rowNo, colNo + 18).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 19), "종점위분");
            getCell(sheet, rowNo, colNo + 19).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 20), "종점위초");
            getCell(sheet, rowNo, colNo + 20).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 21), "종점경도");
            getCell(sheet, rowNo, colNo + 21).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 22), "종점경분");
            getCell(sheet, rowNo, colNo + 22).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 23), "종점경초");
            getCell(sheet, rowNo, colNo + 23).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 24), "시설물종류");
            getCell(sheet, rowNo, colNo + 24).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 25), "길이");
            getCell(sheet, rowNo, colNo + 25).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 26), "폭");
            getCell(sheet, rowNo, colNo + 26).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 27), "깊이");
            getCell(sheet, rowNo, colNo + 27).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 28), "골막이 판정");
            getCell(sheet, rowNo, colNo + 28).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 29), "기슭막이 판정");
            getCell(sheet, rowNo, colNo + 29).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 30), "바닥막이 판정");
            getCell(sheet, rowNo, colNo + 30).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 31), "골막이");
            getCell(sheet, rowNo, colNo + 31).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 32), "기슭막이");
            getCell(sheet, rowNo, colNo + 32).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 33), "바닥막이");
            getCell(sheet, rowNo, colNo + 33).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 34), "계류상태");
            getCell(sheet, rowNo, colNo + 34).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 35), "수문");
            getCell(sheet, rowNo, colNo + 35).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 36), "식생상태");
            getCell(sheet, rowNo, colNo + 36).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 37), "안전시설");
            getCell(sheet, rowNo, colNo + 37).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 38), "접근도로");
            getCell(sheet, rowNo, colNo + 38).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 39), "기타");
            getCell(sheet, rowNo, colNo + 39).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 40), "점검결과");
            getCell(sheet, rowNo, colNo + 40).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 41), "조치사항");
            getCell(sheet, rowNo, colNo + 41).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 42), "지정해제");
            getCell(sheet, rowNo, colNo + 42).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 43), "종합의견 1");
            getCell(sheet, rowNo, colNo + 43).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 44), "종합의견 2");
            getCell(sheet, rowNo, colNo + 44).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 45), "종합의견 3");
            getCell(sheet, rowNo, colNo + 45).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 46), "종합의견 4");
            getCell(sheet, rowNo, colNo + 46).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 47), "종합의견 5");
            getCell(sheet, rowNo, colNo + 47).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 48), "주요시설 특이사항");
            getCell(sheet, rowNo, colNo + 48).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 49), "부대시설 특이사항");
            getCell(sheet, rowNo, colNo + 49).setCellStyle(HeaderStyle1);
            setText(getCell(sheet, rowNo, colNo + 50), "기타 특이사항");
            getCell(sheet, rowNo, colNo + 50).setCellStyle(HeaderStyle1); 
            
     
            colNo=0;
            rowNo=1;
            
            if(dataList.size() > 0) {
            	for(int i = 0; i < dataList.size(); i++) {
            		
            			
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyfid").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltyear").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysgg").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyemd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyri").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyjibun").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 속칭
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dsgarea").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyUser").toString());	
					getCell(sheet, rowNo, colNo++);
	
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
										
					
					// 종점??
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
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("knd").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltlng").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltrng").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltdept").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("chkdamval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rvtmntval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("grdstablval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("chkdamjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("rvtmntjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("grdstabljdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mooringjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("flugtjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("vtnsttusjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sffcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("accssrdjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("etcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fckrslt").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mngmtr").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnrelis").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion4").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion5").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 주요시설 특이사항
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo),"");	// 부대시설 특이사항
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 기타 특이사항
					getCell(sheet, rowNo++, colNo);
					
					colNo = 0;
            	}
            	
            }
            
      }else if(svyType.equals("사방댐 외관점검")) {
         setText(getCell(sheet, rowNo, colNo), "일련번호");
         getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 1), "조사ID");
         getCell(sheet, rowNo, colNo + 1).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 2), "시설년도"); 
         getCell(sheet, rowNo, colNo + 2).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 3), "시도");
         getCell(sheet, rowNo, colNo + 3).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 4), "시군구");
         getCell(sheet, rowNo, colNo + 4).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 5), "읍면동");
         getCell(sheet, rowNo, colNo + 5).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 6), "리");
         getCell(sheet, rowNo, colNo + 6).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 7), "지번");
         getCell(sheet, rowNo, colNo + 7).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 8), "속칭");
         getCell(sheet, rowNo, colNo + 8).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 9), "사방댐종류");
         getCell(sheet, rowNo, colNo + 9).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 10), "형식");
         getCell(sheet, rowNo, colNo + 10).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 11), "상장");
         getCell(sheet, rowNo, colNo + 11).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 12), "하장");
         getCell(sheet, rowNo, colNo + 12).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 13), "유효");
         getCell(sheet, rowNo, colNo + 13).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 14), "점검일시");
         getCell(sheet, rowNo, colNo + 14).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 15), "조사자");
         getCell(sheet, rowNo, colNo + 15).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 16), "관리번호");
         getCell(sheet, rowNo, colNo + 16).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 17), "국가지점번호");
         getCell(sheet, rowNo, colNo + 17).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 18), "위도");
         getCell(sheet, rowNo, colNo + 18).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 19), "위분");
         getCell(sheet, rowNo, colNo + 19).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 20), "위초");
         getCell(sheet, rowNo, colNo + 20).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 21), "경도");
         getCell(sheet, rowNo, colNo + 21).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 22), "경분");
         getCell(sheet, rowNo, colNo + 22).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 23), "경초");
         getCell(sheet, rowNo, colNo + 23).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 24), "본댐 측정");
         getCell(sheet, rowNo, colNo + 24).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 25), "측벽 측정");
         getCell(sheet, rowNo, colNo + 25).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 26), "물받이 측정");
         getCell(sheet, rowNo, colNo + 26).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 27), "본댐");
         getCell(sheet, rowNo, colNo + 27).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 28), "측벽");
         getCell(sheet, rowNo, colNo + 28).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 29), "물받이");
         getCell(sheet, rowNo, colNo + 29).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 30), "수문");
         getCell(sheet, rowNo, colNo + 30).setCellStyle(HeaderStyle1);;
         setText(getCell(sheet, rowNo, colNo + 31), "식생");
         getCell(sheet, rowNo, colNo + 31).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 32), "안전시설");
         getCell(sheet, rowNo, colNo + 32).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 33), "접근도로");
         getCell(sheet, rowNo, colNo + 33).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 34), "기타");
         getCell(sheet, rowNo, colNo + 34).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 35), "저사상태");
         getCell(sheet, rowNo, colNo + 35).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 36), "저사량");
         getCell(sheet, rowNo, colNo + 36).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 37), "점검결과");
         getCell(sheet, rowNo, colNo + 37).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 38), "조치사항");
         getCell(sheet, rowNo, colNo + 38).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 39), "지정해제");
         getCell(sheet, rowNo, colNo + 39).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 40), "종합의견 1");
         getCell(sheet, rowNo, colNo + 40).setCellStyle(HeaderStyle1);;
         setText(getCell(sheet, rowNo, colNo + 41), "종합의견 2");
         getCell(sheet, rowNo, colNo + 41).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 42), "종합의견 3");
         getCell(sheet, rowNo, colNo + 42).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 43), "종합의견 4");
         getCell(sheet, rowNo, colNo + 43).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 44), "종합의견 5");
         getCell(sheet, rowNo, colNo + 44).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 45), "주요시설 특이사항");
         getCell(sheet, rowNo, colNo + 45).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 46), "부대시설 특이사항");
         getCell(sheet, rowNo, colNo + 46).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 47), "기타 특이사항");
         getCell(sheet, rowNo, colNo + 47).setCellStyle(HeaderStyle1);
         
         colNo=0;
         rowNo=1;
         
         if(dataList.size() > 0) {
         	for(int i = 0; i < dataList.size(); i++) {
         		
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyfid").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltyear").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysgg").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyemd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyri").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyjibun").toString());
					getCell(sheet, rowNo, colNo++);

					setText(getCell(sheet, rowNo, colNo), "");	// 속칭
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("knd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyform").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltuprhg").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltlwrhg").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltstkhg").toString());
					getCell(sheet, rowNo, colNo++);

					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyUser").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("ecnrrnum").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("nationspotnum").toString());	
					getCell(sheet, rowNo, colNo++);
	
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
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewallval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("orginldamjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sidewalljdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dwnsptjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("flugtjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("vtnsttusjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sffcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("accssrdjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("etcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("snddpsitjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("snddpsitval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fckrslt").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mngmtr").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnrelis").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion4").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion5").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 주요시설 특이사항
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo),"");	// 부대시설 특이사항
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 기타 특이사항
					getCell(sheet, rowNo++, colNo);
					
					colNo = 0;
	
         	}
         }

      }else if(svyType.equals("산지사방 외관점검")) {
         setText(getCell(sheet, rowNo, colNo), "일련번호");
         getCell(sheet, rowNo, colNo).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 1), "조사ID");
         getCell(sheet, rowNo, colNo + 1).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 2), "시설년도");
         getCell(sheet, rowNo, colNo + 2).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 3), "시도");
         getCell(sheet, rowNo, colNo + 3).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 4), "시군구");
         getCell(sheet, rowNo, colNo + 4).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 5), "읍면동");
         getCell(sheet, rowNo, colNo + 5).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 6), "리");
         getCell(sheet, rowNo, colNo + 6).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 7), "지번");
         getCell(sheet, rowNo, colNo + 7).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 8), "속칭");
         getCell(sheet, rowNo, colNo + 8).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 9), "지정면적");
         getCell(sheet, rowNo, colNo + 9).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 10), "점검일시");
         getCell(sheet, rowNo, colNo + 10).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 11), "점검자");
         getCell(sheet, rowNo, colNo + 11).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 12), "위도도");
         getCell(sheet, rowNo, colNo + 12).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 13), "위도분");
         getCell(sheet, rowNo, colNo + 13).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 14), "위도초");
         getCell(sheet, rowNo, colNo + 14).setCellStyle(HeaderStyle1);;
         setText(getCell(sheet, rowNo, colNo + 15), "경도도");
         getCell(sheet, rowNo, colNo + 15).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 16), "경도분");
         getCell(sheet, rowNo, colNo + 16).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 17), "경도초");
         getCell(sheet, rowNo, colNo + 17).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 18), "시설물종류");
         getCell(sheet, rowNo, colNo + 18).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 19), "보강시설 판정");
         getCell(sheet, rowNo, colNo + 19).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 20), "보호시설 판정");
         getCell(sheet, rowNo, colNo + 20).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 21), "배수시설 판정");
         getCell(sheet, rowNo, colNo + 21).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 22), "보강시설");
         getCell(sheet, rowNo, colNo + 22).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 23), "보호시설");
         getCell(sheet, rowNo, colNo + 23).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 24), "배수시설");
         getCell(sheet, rowNo, colNo + 24).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 25), "사면상태");
         getCell(sheet, rowNo, colNo + 25).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 26), "수문");
         getCell(sheet, rowNo, colNo + 26).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 27), "식생상태");
         getCell(sheet, rowNo, colNo + 27).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 28), "안전시설");
         getCell(sheet, rowNo, colNo + 28).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 29), "접근도로");
         getCell(sheet, rowNo, colNo + 29).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 30), "기타");
         getCell(sheet, rowNo, colNo + 30).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 31), "점검결과");
         getCell(sheet, rowNo, colNo + 31).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 32), "조치사항");
         getCell(sheet, rowNo, colNo + 32).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 33), "지정해제");
         getCell(sheet, rowNo, colNo + 33).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 34), "종합의견 1");
         getCell(sheet, rowNo, colNo + 34).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 35), "종합의견 2");
         getCell(sheet, rowNo, colNo + 35).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 36), "종합의견 3");
         getCell(sheet, rowNo, colNo + 36).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 37), "종합의견 4");
         getCell(sheet, rowNo, colNo + 37).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 38), "종합의견 5");
         getCell(sheet, rowNo, colNo + 38).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 39), "주요시설 특이사항");
         getCell(sheet, rowNo, colNo + 39).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 40), "부대시설 특이사항");
         getCell(sheet, rowNo, colNo + 40).setCellStyle(HeaderStyle1);
         setText(getCell(sheet, rowNo, colNo + 41), "기타 특이사항");
         getCell(sheet, rowNo, colNo + 41).setCellStyle(HeaderStyle1);
         
         colNo=0;
         rowNo=1;
         
         if(dataList.size() > 0) {
         	for(int i = 0; i < dataList.size(); i++) {
         		
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyfid").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyid").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fcltyear").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svysgg").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyemd").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyri").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyjibun").toString());
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 속칭
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("dsgarea").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svydt").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("svyUser").toString());	
					getCell(sheet, rowNo, colNo++);
	
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
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("knd").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("reinfcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prtcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("drngjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("reinfcval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("prtcval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("drngval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("slopejdgval").toString());	
					getCell(sheet, rowNo, colNo++);

					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("flugtjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("vtnsttusjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("sffcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("accssrdjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("etcjdgval").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("fckrslt").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("mngmtr").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("appnrelis").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion1").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion2").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion3").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion4").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), dataList.get(i).get("opinion5").toString());	
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 주요시설 특이사항
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo),"");	// 부대시설 특이사항
					getCell(sheet, rowNo, colNo++);
					
					setText(getCell(sheet, rowNo, colNo), "");	// 기타 특이사항
					getCell(sheet, rowNo++, colNo);
					
					colNo = 0;

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