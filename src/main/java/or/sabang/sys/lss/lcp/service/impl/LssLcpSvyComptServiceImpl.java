package or.sabang.sys.lss.lcp.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.impl.ExtFieldBookDAO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.utl.LssLcpSupMapUtils;
import or.sabang.utl.SuperMapBasicUtils;

@Service("lssLcpSvyComptService")
public class LssLcpSvyComptServiceImpl extends EgovAbstractServiceImpl implements LssLcpSvyComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	
	@Resource(name="lssLcpSvyComptDAO")
	private LssLcpSvyComptDAO lssLcpSvyComptDAO;
	
	@Resource(name="extFieldBookDAO")
	private ExtFieldBookDAO extFieldBookDAO;
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectLcpSvyComptListTotCnt(LssLcpSvyComptVO searchVO) throws Exception {
        return lssLcpSvyComptDAO.selectLcpSvyComptListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<LssLcpSvyComptVO> selectLcpSvyComptList(LssLcpSvyComptVO searchVO) throws Exception {
		return lssLcpSvyComptDAO.selectLcpSvyComptList(searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 */
	@Override
	public LssLcpSvyComptVO selectLcpSvyComptDetail(LssLcpSvyComptVO svyComptVO) throws Exception {
		return lssLcpSvyComptDAO.selectLcpSvyComptDetail(svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateLcpSvyCompt(LssLcpSvyComptVO svyComptVO) throws Exception {
		lssLcpSvyComptDAO.updateLcpSvyCompt(svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteLcpSvyCompt(LssLcpSvyComptVO svyComptVO) throws Exception {
		lssLcpSvyComptDAO.deleteLcpSvyCompt(svyComptVO);
	}

	/**
	 * 대상지를 엑셀다운로드한다. 
	 */
	@Override
	public Map<String, Object> selectLcpSvyComptListExcel(LssLcpSvyComptVO svyComptVO) throws Exception {
		
		List<?> _result = lssLcpSvyComptDAO.selectLcpSvyComptListExcel(svyComptVO);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}

	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectLcpSvyComptMaxYear() throws Exception {
		return lssLcpSvyComptDAO.selectLcpSvyComptMaxYear();
	}
	
	/**
	 * 대상지 조사월 최대값을 조회한다.
	 */
	@Override
	public String selectLcpSvyComptMaxMonth() throws Exception {
		return lssLcpSvyComptDAO.selectLcpSvyComptMaxMonth();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 */
	public List<?> selectLcpSvyComptYear() throws Exception{
		return lssLcpSvyComptDAO.selectLcpSvyComptYear();
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 */
	@Override
	public JSONObject updateLcpSvyComptExcel(LssLcpSvyComptVO svyComptVO,MultipartFile mFile) throws Exception {

		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		int colNum = 0;
		int rowsCnt = 7;
		
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;
		
		//String pattern = "[^0-9.]+";
		
		if(extention.matches("xlsx")) {
			XSSFWorkbook xssfWB = null;// = (XSSFWorkbook) excelZipService.loadWorkbook(inputStream,null);
	    	try {
	    		xssfWB = new XSSFWorkbook(inputStream);

	    	} catch (IOException e) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
	    		LOGGER.debug("=====>>>>> ERR : IOException "+e.getMessage());
	    	} catch (Exception e) {
	    		LOGGER.debug("=====>>>>> ERR : "+e.getMessage());
	    	}
	    	if (xssfWB != null && xssfWB.getNumberOfSheets() == 1) {
	    		XSSFSheet xssfSheet = xssfWB.getSheetAt(0);  //시트 가져오기
	            XSSFRow xssfRow = xssfSheet.getRow(6); //row 가져오기
 	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            for (int rowNum = xssfSheet.getLastRowNum(); rowNum>=0; rowNum--) {
	            	XSSFRow row = xssfSheet.getRow(rowNum);
	            	if(row != null && row.getCell(colNum) != null) {
	            		rowsCnt = rowNum;
	            		break;
	            	}
	            }
	            
	            for(int j=7; j<=rowsCnt; j++){ //row 루프
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		LssLcpSvyComptVO vo = new LssLcpSvyComptVO();
	            		vo = convertXSSFCellValues(row,vo);
	            		try {
	            			List<EgovMap> paramList = lssLcpSvyComptDAO.selectLcpLocReCreateSvyId(vo);
	            			if(!paramList.isEmpty()) {
	            				vo.setLoginId(paramList.get(0).containsValue("loginid") ? paramList.get(0).get("loginid").toString() : "");
		            			vo.setAttr(paramList.get(0).get("attr").toString());
		            			vo.setSvyLon(paramList.get(0).containsValue("lon") ? paramList.get(0).get("lon").toString() : "");
		            			vo.setSvyLat(paramList.get(0).containsValue("lat") ? paramList.get(0).get("lat").toString() : "");
//		            			vo.setFid(Integer.parseInt(paramList.get(0).get("fid").toString()));
//		            			vo.setMstId(paramList.get(0).get("mstid").toString());
	            			} else {
		            			vo.setSvyLon(vo.getPx());
		            			vo.setSvyLat(vo.getPy());
//		            			vo.setFid(extFieldBookService.selectLcpUpdtFid());
	            			}
	        				lssLcpSvyComptDAO.updateLcpSvyComptExcel(vo);
	        				successCnt++;
	        			} catch (Exception e) {
	        				failedIdsArray.put(vo.getSvyId());
	        				returnInsertLog.put("error", e.getMessage());
	        				LOGGER.error(e.getMessage());
	        			}
	            	}else {
	            		rowsCnt = j;
	                    break;
	            	}
	            }
	    	}
		}
		
		returnInsertLog.put("total", rowsCnt-6);
		returnInsertLog.put("successCnt", successCnt);
		returnInsertLog.put("failedIds", failedIdsArray);
		return returnInsertLog;
	}

	private LssLcpSvyComptVO convertXSSFCellValues(XSSFRow row, LssLcpSvyComptVO vo) throws Exception {
		
		double px = 0;
		double py = 0;
		double bpx = 0;
		double bpy = 0;
		double epx = 0;
		double epy = 0;
		
		XSSFCell cell = null;

		cell = row.getCell(0);  //고유번호
		if(cell!=null){vo.setSn(ExcelUtils.getCellValue(cell));}
		
		cell = row.getCell(1);  //번호(PNU)
		if(cell!=null){vo.setFid(Integer.parseInt(ExcelUtils.getCellValue(cell)));}
		
    	cell = row.getCell(2);  //조사ID
    	if(cell!=null){vo.setSvyId(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(3);  //중복여부 진입불가
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		vo.setPassat("유");
    		vo.setPassresn(ExcelUtils.getCellValue(cell));
    	}else {
    		vo.setPassat("무");
    	}
    	//----------------------------------물리탐사 추가
    	cell = row.getCell(5);  //시도
    	if(cell!=null){vo.setSvySd(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(6);  //시군구
    	if(cell!=null){vo.setSvySgg(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(7);  //읍면동
    	if(cell!=null){vo.setSvyEmd(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(8);  //리
    	if(cell!=null){vo.setSvyRi(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(9);  //지번
    	if(cell!=null){vo.setSvyJibun(ExcelUtils.getCellValue(cell));}
    	
    	// 북위_도분초
   		if(!ExcelUtils.getCellValue(row.getCell(11)).toString().equals("") && !ExcelUtils.getCellValue(row.getCell(12)).toString().equals("") && !ExcelUtils.getCellValue(row.getCell(13)).toString().equals("")){
    		py = Double.parseDouble(row.getCell(11).toString()) + Double.parseDouble(row.getCell(12).toString())/60 + Double.parseDouble(row.getCell(13).toString())/3600;
    		vo.setPy(String.valueOf(py));
    	}
    	
    	// 동경_도분초
   		if(!ExcelUtils.getCellValue(row.getCell(14)).toString().equals("") && !ExcelUtils.getCellValue(row.getCell(15)).toString().equals("") && !ExcelUtils.getCellValue(row.getCell(16)).toString().equals("")){
    		px = Double.parseDouble(row.getCell(14).toString()) + Double.parseDouble(row.getCell(15).toString())/60 + Double.parseDouble(row.getCell(16).toString())/3600;
    		vo.setPx(String.valueOf(px));
    	}
    	
    	cell = row.getCell(18);  //조사일자_점검일시
    	if(cell!=null){vo.setSvyDt(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(19);  //부서명
    	
    	cell = row.getCell(20);  //조사자_점검자
    	if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(21); //고도(m)
    	if(cell!=null){vo.setPz(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(22);	//판정점수
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setLssfrsm(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(23);	//판정등급
    	if(cell!=null){
    		vo.setLssfrgrd(cell.getStringCellValue());
    		vo.setLastgrd(cell.getStringCellValue());
    	}
    	
    	cell = row.getCell(24);	//주 구성암석 판정점수
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setCmprokscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(25);	//주 구성암석 측정갑
    	if(cell!=null){vo.setCmprokval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(26);	//타 지층 및 관입암 유/무 측정값
    	if(cell!=null){vo.setInstrokat(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(27);	//암석풍화 점수
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setRokwthrscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(28);	//암석풍화 측정값
    	if(cell!=null){vo.setRokwthrval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(29);	//지질구조_단층
    	if(cell!=null){vo.setGeologyflt(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(30);	//지질구조_습곡형
    	if(cell!=null){vo.setGeologyfld(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(31);	//지질특성_불연속면 조사1_종류
    	if(cell!=null){vo.setDisctnupln1(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(32);	//지질특성_불연속면 조사1_주향
    	if(cell!=null){vo.setDisctnupln1_strk(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(33);	//지질특성_불연속면 조사1_경사
    	if(cell!=null){vo.setDisctnupln1_dip(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(34);	//지질특성_불연속면 조사2_종류
    	if(cell!=null){vo.setDisctnupln2(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(35);	//지질특성_불연속면 조사2_주향
    	if(cell!=null){vo.setDisctnupln2_strk(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(36);	//지질특성_불연속면 조사2_경사
    	if(cell!=null){vo.setDisctnupln2_dip(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(37);	//지질특성_불연속면 조사3_종류
    	if(cell!=null){vo.setDisctnupln3(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(38);	//지질특성_불연속면 조사3_주향
    	if(cell!=null){vo.setDisctnupln3_strk(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(39);	//지질특성_불연속면 조사3_경사
    	if(cell!=null){vo.setDisctnupln3_dip(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(40);	//지질특성_불연속면 조사4_종류
    	if(cell!=null){vo.setDisctnupln4(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(41);	//지질특성_불연속면 조사4_주향
    	if(cell!=null){vo.setDisctnupln4_strk(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(42);	//지질특성_불연속면 조사4_경사
    	if(cell!=null){vo.setDisctnupln4_dip(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(43);	//지질특성_불연속면 조사5_종류
    	if(cell!=null){vo.setDisctnupln5(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(44);	//지질특성_불연속면 조사5_주향
    	if(cell!=null){vo.setDisctnupln5_strk(ExcelUtils.getCellValue(cell));}
    	cell = row.getCell(45);	//지질특성_불연속면 조사5_경사
    	if(cell!=null){vo.setDisctnupln5_dip(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(46);	//불연속면 방향수
    	if(cell!=null){vo.setDisctnuplndrcno(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(47);	//불연속면과 사면의 방향성 판정점수
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setDisctnuplnslpscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(48);	//불연속면과 사면의 방향성 측정값
    	if(cell!=null){vo.setDisctnuplnslpval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(49);	//불연속면 간격 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setDisctnuplnintvlscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(50);	//불연속면 간격 측정값
    	if(cell!=null){vo.setDisctnuplnintvlval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(51);	//토양형
    	if(cell!=null){vo.setSoilty(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(52);	//토심판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setSoildeptbscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(53);	//토심측정값
    	if(cell!=null){vo.setSoildeptbval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(54);	//토성판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setSoilclassbscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(55);	//토성측정값
    	if(cell!=null){vo.setSoilclassbval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(56);	//토양구조 B층 기준
    	if(cell!=null){vo.setSoilstrct(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(57);	//토양수분상태 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setSoilwtscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(58);	//토양수분상태 측정값
    	if(cell!=null){vo.setSoilwtrval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(59);	//암석노출도
    	if(cell!=null){vo.setRokexpsr(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(60);	//너덜(talus) 유무 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setTalusatscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(61);	//너덜(talus) 유무 측정값
    	if(cell!=null){vo.setTalusat(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(62);	//지형구분 최대높이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setMxhg(ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));}
    	
    	cell = row.getCell(63);	//지형구분 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setTpgrphscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(64);	//지형구분 측정값
    	if(cell!=null){vo.setTpgrphval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(65);	//조사지역 능선
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setArealcridge(ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));}
    	
    	cell = row.getCell(66);	//조사지역 위치
    	if(cell!=null){vo.setArealcval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(67);	//평면형(수평적) 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setPlnformscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(68);	//평면형(수평적) 측정값
    	if(cell!=null){vo.setPlnformval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(69);	//종단면형(수직적) 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setLngformscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(70);	//종단면형(수직적) 측정값
    	if(cell!=null){vo.setLngformval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(71);	//경사범위 판정값
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setSlprngscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(72);	//경사범위 측정값
    	if(cell!=null){vo.setSlprngval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(73);	//평균경사 측정값
    	if(cell!=null){vo.setSlprngavgval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(74);	//상부지하수유입
    	if(cell!=null){vo.setUgrwtr_posblty(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(75);	//하류계류의 유무
    	if(cell!=null){vo.setDwstrm_at(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(76);	//샘,소,저수지 유무
    	if(cell!=null){vo.setSprg_at(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(77);	//임상
    	if(cell!=null){vo.setFrstfrval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(78);	//주요수종
    	if(cell!=null){vo.setMaintreeknd(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(79);	//임지이용상태
    	if(cell!=null){vo.setFrlndsttus(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(80);	//조사경계하부임지이용상태
    	if(cell!=null){vo.setLwbndlwfrlndsttus(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(81);	//징후발생 여부
    	if(cell!=null){vo.setSymptmoccur(cell.getStringCellValue());}
    	
    	cell = row.getCell(82);	//직접징후 판정점수
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){vo.setDirsymptmscore(String.valueOf((int)cell.getNumericCellValue()));}
    	
    	cell = row.getCell(83);	//직접징후 유무
    	if(cell!=null){vo.setDirsymptmval(cell.getStringCellValue());}
    	
    	cell = row.getCell(84);	//간접징후 판정점수    	
    	if(cell!=null){vo.setIndirsymptmscore(cell.getStringCellValue());}
    	
    	cell = row.getCell(85);	//간접징후 유무
    	if(cell!=null){vo.setIndirsymptmval(cell.getStringCellValue());}
    	
    	//균열
    	JSONObject lcpsttusCrkObj = new JSONObject();
    	HashMap<String, Object> projMap = new HashMap<>();
    	
    	cell = row.getCell(87);	//균열 위도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		if(ExcelUtils.getCellValue(cell).toString().contains("/")) {
    			String[] cellValue = ExcelUtils.getCellValue(cell).toString().split("/");
    			String[] bpxCellValue = cellValue[0].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			String[] epxCellValue = cellValue[1].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			
    			if(bpxCellValue.length == 3) {
    				bpx = Double.parseDouble(bpxCellValue[0].toString()) + Double.parseDouble(bpxCellValue[1].toString())/60 + Double.parseDouble(bpxCellValue[2].toString())/3600;
    				projMap.put("bpx", bpx);    				
    			}
    			if(epxCellValue.length == 3) {
    				epx = Double.parseDouble(epxCellValue[0].toString()) + Double.parseDouble(epxCellValue[1].toString())/60 + Double.parseDouble(epxCellValue[2].toString())/3600;
    				projMap.put("epx", epx);    				
    			}
    			
    		}else{
    			String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
    			String[] cellValue = cellVal.split(" ");
    			if(cellValue.length == 3) {
    				bpx = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
    				projMap.put("bpx", bpx);    				
    			}
    		}
    	}
    	
    	cell = row.getCell(88);	//균열 경도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		if(ExcelUtils.getCellValue(cell).toString().contains("/")) {
    			String[] cellValue = ExcelUtils.getCellValue(cell).toString().split("/");
    			String[] bpyCellValue = cellValue[0].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			String[] epyCellValue = cellValue[1].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			
    			if(bpyCellValue.length == 3) {
    				bpy = Double.parseDouble(bpyCellValue[0].toString()) + Double.parseDouble(bpyCellValue[1].toString())/60 + Double.parseDouble(bpyCellValue[2].toString())/3600;
    				projMap.put("bpy", bpy);    				
    			}
    			if(epyCellValue.length == 3) {
    				epy = Double.parseDouble(epyCellValue[0].toString()) + Double.parseDouble(epyCellValue[1].toString())/60 + Double.parseDouble(epyCellValue[2].toString())/3600;
    				projMap.put("epy", epy);    				
    			}
    		}else{
    			String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
    			String[] cellValue = cellVal.split(" ");
    			if(cellValue.length == 3) {
    				bpy = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;    				
    				projMap.put("bpy", bpy);    			
    			}
    		}
    	}
    	
    	List<EgovMap> projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    	
    	if(projMap.containsKey("bpx") && projMap.containsKey("bpy")) {
    		projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    		lcpsttusCrkObj.put("시점_경도", projList.get(0).get("bpx"));
    		lcpsttusCrkObj.put("시점_위도", projList.get(0).get("bpy"));
    	}
    	
    	if(projMap.containsKey("epx") && projMap.containsKey("epy")) {
    		lcpsttusCrkObj.put("종점_경도", projList.get(0).get("epx"));
        	lcpsttusCrkObj.put("종점_위도", projList.get(0).get("epy"));
    	}
    	
    	cell = row.getCell(89);	//균열 고도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		if(ExcelUtils.getCellValue(cell).toString().contains("/")) {
    			String[] cellValue = ExcelUtils.getCellValue(cell).toString().split("/");
    			lcpsttusCrkObj.put("시점_고도",cellValue[0].trim().substring(0, cellValue[0].trim().length()-1));
    			lcpsttusCrkObj.put("종점_고도",cellValue[1].trim().substring(0, cellValue[1].trim().length()-1));
    		}else{
    			lcpsttusCrkObj.put("시점_고도",ExcelUtils.getCellValue(cell).trim().substring(0, ExcelUtils.getCellValue(cell).trim().length()-1));
    		}
    	}
    	
    	cell = row.getCell(90);	//균열_연장
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusCrkObj.put("연장", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));

    	cell = row.getCell(91);	//균열_높이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusCrkObj.put("높이", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	cell = row.getCell(92);	//균열_깊이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusCrkObj.put("깊이", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));

    	org.json.simple.JSONArray lcpsttusCrkArr = new org.json.simple.JSONArray();
    	lcpsttusCrkArr.add(lcpsttusCrkObj);
    	vo.setLcpsttus_crk(lcpsttusCrkArr.toString());
    	
    	//단차
    	JSONObject lcpsttusStpObj = new JSONObject();
    	projMap = new HashMap<>();
    	
    	cell = row.getCell(94);	//단차 위도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		if(ExcelUtils.getCellValue(cell).toString().contains("/")) {
    			String[] cellValue = ExcelUtils.getCellValue(cell).toString().split("/");
    			String[] bpxCellValue = cellValue[0].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			String[] epxCellValue = cellValue[1].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			
    			if(bpxCellValue.length == 3) {
    				bpx = Double.parseDouble(bpxCellValue[0].toString()) + Double.parseDouble(bpxCellValue[1].toString())/60 + Double.parseDouble(bpxCellValue[2].toString())/3600;
    				projMap.put("bpx", bpx);    				
    			}
    			if(epxCellValue.length == 3) {
    				epx = Double.parseDouble(epxCellValue[0].toString()) + Double.parseDouble(epxCellValue[1].toString())/60 + Double.parseDouble(epxCellValue[2].toString())/3600;
    				projMap.put("epx", epx);    				
    			}
    		}else{
    			String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
    			String[] cellValue = cellVal.split(" ");
    			if(cellValue.length == 3) {
    				bpx = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;    				
    				projMap.put("bpx", bpx);
    			}
    		}
    	}
    	
    	cell = row.getCell(95);	//단차 경도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		if(ExcelUtils.getCellValue(cell).toString().contains("/")) {
    			String[] cellValue = ExcelUtils.getCellValue(cell).toString().split("/");
    			String[] bpyCellValue = cellValue[0].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			String[] epyCellValue = cellValue[1].trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "").split(" ");
    			
    			if(bpyCellValue.length == 3) {
    				bpy = Double.parseDouble(bpyCellValue[0].toString()) + Double.parseDouble(bpyCellValue[1].toString())/60 + Double.parseDouble(bpyCellValue[2].toString())/3600;
    				projMap.put("bpy", bpy);    				
    			}
    			if(epyCellValue.length == 3) {
    				epy = Double.parseDouble(epyCellValue[0].toString()) + Double.parseDouble(epyCellValue[1].toString())/60 + Double.parseDouble(epyCellValue[2].toString())/3600;
    				projMap.put("epy", epy);    				
    			}
    		}else{
    			String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
    			String[] cellValue = cellVal.split(" ");
    			if(cellValue.length == 3) {
    				bpy = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
    				projMap.put("bpy", bpy);    			    				
    			}
    		}
    	}
    	
    	projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    	
    	if(projMap.containsKey("bpx") && projMap.containsKey("bpy")) {
    		projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    		lcpsttusStpObj.put("시점_경도", projList.get(0).get("bpx"));
    		lcpsttusStpObj.put("시점_위도", projList.get(0).get("bpy"));
    	}
    	
    	if(projMap.containsKey("epx") && projMap.containsKey("epy")) {
    		lcpsttusStpObj.put("종점_경도", projList.get(0).get("epx"));
    		lcpsttusStpObj.put("종점_위도", projList.get(0).get("epy"));
    	}
    	
    	cell = row.getCell(96);	//단차 고도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		if(ExcelUtils.getCellValue(cell).toString().contains("/")) {
    			String[] cellValue = ExcelUtils.getCellValue(cell).toString().split("/");
    			lcpsttusStpObj.put("시점_고도",cellValue[0].trim().substring(0, cellValue[0].trim().length()-1));
    			lcpsttusStpObj.put("종점_고도",cellValue[1].trim().substring(0, cellValue[1].trim().length()-1));
    		}else{
    			lcpsttusStpObj.put("시점_고도",ExcelUtils.getCellValue(cell).trim().substring(0, ExcelUtils.getCellValue(cell).trim().length()-1));
    		}
    	}
    	
    	cell = row.getCell(97);	//단차_연장
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusStpObj.put("연장", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	cell = row.getCell(98);	//단차_높이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusStpObj.put("높이", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	cell = row.getCell(99);	//단차_깊이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusStpObj.put("깊이", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	org.json.simple.JSONArray lcpsttusStpArr = new org.json.simple.JSONArray();
    	lcpsttusStpArr.add(lcpsttusStpObj);
      	vo.setLcpsttus_stp(lcpsttusStpArr.toString());
    	
    	//구조물이상
    	JSONObject lcpsttusStrctObj = new JSONObject();
    	projMap = new HashMap<>();
    	
    	cell = row.getCell(101); //구조물이상 위도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
			String[] cellValue = cellVal.split(" ");
			if(cellValue.length == 3) {
				bpx = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
				projMap.put("bpx", bpx);				
			}
    		
    	}
    	
    	cell = row.getCell(102); //구조물이상 경도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
			String[] cellValue = cellVal.split(" ");
			if(cellValue.length == 3) {
				bpy = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
				projMap.put("bpy", bpy);    							
			}
    	}
    	
    	if(projMap.containsKey("bpx") && projMap.containsKey("bpy")) {
    		projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    		lcpsttusStrctObj.put("시점_경도", projList.get(0).get("bpx"));
    		lcpsttusStrctObj.put("시점_위도", projList.get(0).get("bpy"));
    	}
    	
    	cell = row.getCell(103); //구조물이상 고도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){lcpsttusStrctObj.put("시점_고도",ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));}
    	
    	cell = row.getCell(104); //구조물이상_가로
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusStrctObj.put("가로", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	cell = row.getCell(105); //구조물이상_세로
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusStrctObj.put("세로", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));

    	
    	cell = row.getCell(106); //구조물이상_높이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusStrctObj.put("높이", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	org.json.simple.JSONArray lcpsttusStrctArr = new org.json.simple.JSONArray();
    	lcpsttusStrctArr.add(lcpsttusStrctObj);
    	vo.setLcpsttus_strct(lcpsttusStrctArr.toString());
    	
    	//수목이상생장
    	JSONObject lcpsttusWdptObj = new JSONObject();
    	projMap = new HashMap<>();
    	
    	cell = row.getCell(108); //수목이상생장 위도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
			String[] cellValue = cellVal.split(" ");
			if(cellValue.length == 3) {
				bpx = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
				projMap.put("bpx", bpx);				
			}
    	}
    	
    	cell = row.getCell(109); //수목이상생장 경도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
			String[] cellValue = cellVal.split(" ");
			if(cellValue.length == 3) {
				bpy = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
				projMap.put("bpy", bpy);    							
			}
    	}
    	
    	if(projMap.containsKey("bpx") && projMap.containsKey("bpy")) {
    		projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    		lcpsttusWdptObj.put("시점_경도", projList.get(0).get("bpx"));
    		lcpsttusWdptObj.put("시점_위도", projList.get(0).get("bpy"));
    	}
    	
    	cell = row.getCell(110); //수목이상생장 고도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){lcpsttusWdptObj.put("시점_고도",ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));}
    	
    	cell = row.getCell(111); //수목이상생장_흉고직경
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusWdptObj.put("흉고직경", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	cell = row.getCell(112); //수목이상생장_수고
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusWdptObj.put("수고", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));
    	
    	cell = row.getCell(113); //수목이상생장_지표높이
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")) lcpsttusWdptObj.put("지표높이", ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));

    	org.json.simple.JSONArray lcpsttusWdptArr = new org.json.simple.JSONArray();
    	lcpsttusWdptArr.add(lcpsttusWdptObj);
    	vo.setLcpsttus_wdpt(lcpsttusWdptArr.toString());
    	
    	//지하수용출
    	JSONObject lcpsttusUgrwtrObj = new JSONObject();
    	projMap = new HashMap<>();
    	
    	cell = row.getCell(115); //지하수용출 위도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
			String[] cellValue = cellVal.split(" ");
			if(cellValue.length == 3) {
				bpx = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
				projMap.put("bpx", bpx);				
			}
    	}
    	
    	cell = row.getCell(116); //지하수용출 경도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){
    		String cellVal = ExcelUtils.getCellValue(cell).trim().replaceAll("°", " ").replaceAll("′", " ").replaceAll("″", "");
			String[] cellValue = cellVal.split(" ");
			if(cellValue.length == 3) {
				bpy = Double.parseDouble(cellValue[0].toString()) + Double.parseDouble(cellValue[1].toString())/60 + Double.parseDouble(cellValue[2].toString())/3600;
				projMap.put("bpy", bpy);    							
			}
    	}
    	
    	projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    	
    	if(projMap.containsKey("bpx") && projMap.containsKey("bpy")) {
    		projList = lssLcpSvyComptDAO.selectLssLcpProjBessel(projMap);
    		lcpsttusUgrwtrObj.put("시점_경도", projList.get(0).get("bpx"));
    		lcpsttusUgrwtrObj.put("시점_위도", projList.get(0).get("bpy"));
    	}
    	
    	cell = row.getCell(117); //지하수용출 고도
    	if(!ExcelUtils.getCellValue(cell).toString().equals("")){lcpsttusUgrwtrObj.put("시점_고도",ExcelUtils.getCellValue(cell).substring(0, ExcelUtils.getCellValue(cell).length()-1));}

    	org.json.simple.JSONArray lcpsttusUgrwtrArr = new org.json.simple.JSONArray();
    	lcpsttusUgrwtrArr.add(lcpsttusUgrwtrObj);
    	vo.setLcpsttus_ugrwtr(lcpsttusUgrwtrArr.toString());
    	
    	cell = row.getCell(118); //보호대상 점수
    	
    	cell = row.getCell(119); //보호대상 측정값
    	
    	cell = row.getCell(120); //경사길이 점수
    	
    	cell = row.getCell(121); //경사길이 측정값
    	
    	cell = row.getCell(122); //모암 점수

    	cell = row.getCell(123); //모암 측정값
    	if(cell!=null){vo.setPrntrckval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(124); //임상 점수
    	
    	cell = row.getCell(125); //임상 측정값
    	if(cell!=null){vo.setFrstfrval(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(126); //경사위치
    	
    	cell = row.getCell(127); //사면형
    	
    	cell = row.getCell(128); //토심
    	
    	cell = row.getCell(129); //경사도
    	
    	cell = row.getCell(130); //판정점수
    	
    	cell = row.getCell(131); //판정등급
    	
    	cell = row.getCell(132); //종합의견1
    	if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell).trim());}
    	
    	cell = row.getCell(133); //종합의견2
    	if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell).trim());}
    
    	cell = row.getCell(134); //종합의견3
    	if(cell!=null){vo.setOpinion3(ExcelUtils.getCellValue(cell).trim());}
    	
    	cell = row.getCell(135); //종합의견4
    	if(cell!=null){vo.setOpinion4(ExcelUtils.getCellValue(cell).trim());}
    	
    	cell = row.getCell(136); //종합의견5
    	if(cell!=null){vo.setOpinion5(ExcelUtils.getCellValue(cell).trim());}
    	
    	cell = row.getCell(137); //자문결과 (자문사유)
    	if(cell!=null){vo.setCnsutresn(ExcelUtils.getCellValue(cell).trim());}
    	
    	cell = row.getCell(138); //공유방ID
    	if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(139); //로그인ID
    	if(cell!=null){vo.setLoginId(ExcelUtils.getCellValue(cell));}
    	
		return vo;
	}

	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return lssLcpSvyComptDAO.selectLcpSvyComptPhotoInfo(searchVO);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectLssLcpProjDMS(HashMap<String, Object> map) throws Exception{
		return lssLcpSvyComptDAO.selectLssLcpProjDMS(map);
	}
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLssLcpProjBessel(HashMap<String, Object> map) throws Exception{
		return lssLcpSvyComptDAO.selectLssLcpProjBessel(map);
	}
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpLocReCreateSvyId(LssLcpSvyComptVO svyComptVO) throws Exception{
		return lssLcpSvyComptDAO.selectLcpLocReCreateSvyId(svyComptVO);
	}
	
	/**
	 * 땅밀림 징후 값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSttusList(LssLcpSvyComptVO svyComptVO) throws Exception{
		return lssLcpSvyComptDAO.selectLcpSttusList(svyComptVO);
	}
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return lssLcpSvyComptDAO.selectLastUpdateMinMaxDate(searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return lssLcpSvyComptDAO.updateLocReCreate(map);
	};
	
	/**
	 * 위치도 생성 업데이트
 	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateComptLcMap(LocReCreateVO searchVO) throws Exception{
		AnalFileVO vo = null;
		String source = null;
		String target = null;
		HashMap<String, Object> schMap = new HashMap<String, Object>();
		List<String> imgResult = null;
		
		List<?> list = updateLocReCreate(searchVO);
		
		LssLcpSupMapUtils utils = new LssLcpSupMapUtils();
		
		for (int i = 0; i < list.size(); i++) {
			LOGGER.info(i+"번째 생성 시작");

			EgovMap map = (EgovMap)list.get(i);
			
			String gid = map.get("gid").toString();
			String mstId = map.get("mstid").toString();
			String sldId = map.get("svylabel").toString();
			String mstPath = map.get("mstpath").toString();
			
			schMap.put("mstId", mstId);
			schMap.put("sldId", sldId);
			schMap.put("gid", gid);
			schMap.put("SE","LCP");
			
			imgResult = new ArrayList<String>();
			
			vo = utils.creatLcpLocLgstrMap(schMap);
			source = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
			target = mstPath.concat(".ncx")+File.separator+sldId+"_위치도1.".concat(vo.getFileExtsn());
			
			File locFile1 = new File(source);
			
			if(locFile1.exists()) {
				EgovFileUtil.cp(source, fieldBookDir+target);
				imgResult.add("/"+mstPath.concat(".ncx")+"/"+sldId+"_위치도1.".concat(vo.getFileExtsn()));
			}else {
				LOGGER.error(vo.getStreFileNm()+".".concat(vo.getFileExtsn())+" 파일이 존재하지 않아 "+sldId+"_위치도1.".concat(vo.getFileExtsn())+"복사를 실패하였습니다.(gid:"+gid+")");
			}
			
			vo = utils.creatLcpLocSatMap(schMap);
			source = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
			target = mstPath.concat(".ncx")+File.separator+sldId+"_위치도2.".concat(vo.getFileExtsn());
			
			File locFile2 = new File(source);
			
			if(locFile2.exists()) {
				EgovFileUtil.cp(source, fieldBookDir+target);
				imgResult.add("/"+mstPath.concat(".ncx")+"/"+sldId+"_위치도2.".concat(vo.getFileExtsn()));
			}else {
				LOGGER.error(vo.getStreFileNm()+".".concat(vo.getFileExtsn())+" 파일이 존재하지 않아 "+sldId+"_위치도2.".concat(vo.getFileExtsn())+"복사를 실패하였습니다.(gid:"+gid+")");
			}
			
			schMap.put("lcMap",new ObjectMapper().writeValueAsString(imgResult));
			
			extFieldBookDAO.updateComptLcMap(schMap);
			LOGGER.info(sldId+" 생성 종료");
		}
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = lssLcpSvyComptDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
}