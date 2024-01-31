package or.sabang.sys.lss.cnl.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptService;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.utl.LssCnlSupMapUtils;
import or.sabang.utl.SuperMapBasicUtils;

@Service("lssCnlSvyComptService")
public class LssCnlSvyComptServiceImpl extends EgovAbstractServiceImpl implements LssCnlSvyComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	
	@Resource(name="lssCnlSvyComptDAO")
	private LssCnlSvyComptDAO lssCnlSvyComptDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectCnlSvyComptListTotCnt(LssCnlSvyComptVO searchVO) throws Exception {
        return lssCnlSvyComptDAO.selectCnlSvyComptListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<LssCnlSvyComptVO> selectCnlSvyComptList(LssCnlSvyComptVO searchVO) throws Exception {
		return lssCnlSvyComptDAO.selectCnlSvyComptList(searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 */
	@Override
	public LssCnlSvyComptVO selectCnlSvyComptDetail(LssCnlSvyComptVO svyComptVO) throws Exception {
		return lssCnlSvyComptDAO.selectCnlSvyComptDetail(svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateCnlSvyCompt(LssCnlSvyComptVO svyComptVO) throws Exception {
		lssCnlSvyComptDAO.updateCnlSvyCompt(svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteCnlSvyCompt(LssCnlSvyComptVO svyComptVO) throws Exception {
		lssCnlSvyComptDAO.deleteCnlSvyCompt(svyComptVO);
	}

	/**
	 * 대상지를 엑셀다운로드한다. 
	 */
	@Override
	public Map<String, Object> selectCnlSvyComptListExcel(LssCnlSvyComptVO svyComptVO) throws Exception {
		
		List<?> _result = lssCnlSvyComptDAO.selectCnlSvyComptListExcel(svyComptVO);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}

	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectCnlSvyComptMaxYear() throws Exception {
		return lssCnlSvyComptDAO.selectCnlSvyComptMaxYear();
	}
	
	/**
	 * 대상지 조사월 최대값을 조회한다.
	 */
	@Override
	public String selectCnlSvyComptMaxMonth() throws Exception {
		return lssCnlSvyComptDAO.selectCnlSvyComptMaxMonth();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 */
	public List<?> selectCnlSvyComptYear() throws Exception{
		return lssCnlSvyComptDAO.selectCnlSvyComptYear();
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 */
	@Override
	public JSONObject updateCnlSvyComptExcel(LssCnlSvyComptVO svyComptVO,MultipartFile mFile) throws Exception {
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		int rowsCnt = 4;
		
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
	    	if (xssfWB != null) {
	    		XSSFSheet xssfSheet = xssfWB.getSheetAt(0);  //시트 가져오기
	            XSSFRow xssfRow = xssfSheet.getRow(4); //row 가져오기
	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=2; j<rowsCnt; j++){ //row 루프
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		LssCnlSvyComptVO vo = new LssCnlSvyComptVO();
	            		vo = convertXSSFCellValues(row,vo);
	            		try {
	            			lssCnlSvyComptDAO.updateCnlSvyComptExcel(vo);	        				
	        				successCnt++;
	        			} catch (Exception e) {
	        				// TODO: handle exception
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
		
		returnInsertLog.put("total", rowsCnt-2);
		returnInsertLog.put("successCnt", successCnt);
		returnInsertLog.put("failedIds", failedIdsArray);
		return returnInsertLog;
	}

	private LssCnlSvyComptVO convertXSSFCellValues(XSSFRow row, LssCnlSvyComptVO vo) {
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;

    	cell = row.getCell(1);  // 기번
    	if(cell!=null){vo.setSn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(2);  // 조사ID
    	if(cell!=null){vo.setSvyId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(3);  // 시도
    	if(cell!=null){vo.setSvySd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(4);  // 시군구
    	if(cell!=null){vo.setSvySgg(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(5);  // 읍면동
    	if(cell!=null){vo.setSvyEmd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(6);  // 리
    	if(cell!=null){vo.setSvyRi(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(7);  // 지번(관리번호있을경우 관리번호)
    	if(cell!=null){vo.setSvyJibun(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(8);  // 관리주체
    	if(cell!=null){
	    	String mange = ExcelUtils.getCellValue(cell).replace("\n", "");
	    	String[] manges = mange.split(" ");
    		if(manges.length == 2) {
    			vo.setRegion1(manges[0]);
    			vo.setRegion2(manges[1]);
    		}else {
    			vo.setRegion1(mange);
    		}
    	}
    	
    	String svyType = null;
    	cell = row.getCell(9);  // 취약지유형
    	if(cell!=null){
    		svyType = ExcelUtils.getCellValue(cell).replace("\n", "");
    		vo.setSvyType("취약지역 해제조사(".concat(svyType).concat(")"));
    	}
    	
    	cell = row.getCell(10);  // 지정년도
    	if(cell!=null){vo.setAppnYear(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(11);  // 지정면적
    	if(cell!=null){vo.setAppnArea(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(12);  // 지정번호
    	if(cell!=null){vo.setAppnNo(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(13);  // 소속
    	if(cell!=null){}
//    	if(cell!=null){vo.(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	/*
		여기 소속은 조사자 이름 기준으로 검색 
		ORGNZT_0000000000000	한국치산기술협회
		ORGNZT_0000000000001	산림조합중앙회
    	 */
    	
    	cell = row.getCell(14);  // 성명
    	if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(15);  // 조사일자 
    	if(cell!=null){
    		String svyDt = ExcelUtils.getCellValue(cell).replace("\n", "");
    		
        	svyDt = svyDt.replace(".", "").replace(" ", "-");
    		vo.setSvyDt(svyDt);
    	}
    	
    	cell = row.getCell(16);  // 연락처
    	if(cell!=null){}	//vo추가해줘야함
    	
    	String latD = null;
    	cell = row.getCell(17);  // 위도도
    	if(cell!=null){
    		latD =  ExcelUtils.getCellValue(cell).replace("\n", "");
    	}
    	
    	String latM = null;
    	cell = row.getCell(18);  // 위도분
    	if(cell!=null){
    		latM =  ExcelUtils.getCellValue(cell).replace("\n", "");
    	}
    	
    	String latS = null;
    	cell = row.getCell(19);  // 위도초
    	if(cell!=null){
    		latS =  ExcelUtils.getCellValue(cell).replace("\n", "");
    	}
    	
    	String lonD = null;
    	cell = row.getCell(20);  // 경도도
    	if(cell!=null){
    		lonD =  ExcelUtils.getCellValue(cell).replace("\n", "");
    	}
    	
    	String lonM = null;
    	cell = row.getCell(21);  // 경도분
    	if(cell!=null){
    		lonM =  ExcelUtils.getCellValue(cell).replace("\n", "");
    	}
    	
    	String lonS = null;
    	cell = row.getCell(22);  // 경도초
    	if(cell!=null){
    		lonS =  ExcelUtils.getCellValue(cell).replace("\n", "");
    	}
    	
    	cell = row.getCell(23);  // 산사태위험도
    	if(cell!=null){vo.setLndsldGrde(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(24);  // 평균경사 
    	if(cell!=null){vo.setSlopeAvg(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(25);  // 임상 
    	if(cell!=null){vo.setFrtpType(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(26);  // 경급 
    	if(cell!=null){vo.setDmclsType(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(27);  // 사업시행여부 
    	if(cell!=null){vo.setBizOpertnAT(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(28);  // 사업종류 
    	if(cell!=null){vo.setBizType(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(29);  // 적용공법 
    	if(cell!=null){vo.setApplcEgnerMhd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(30);  // 시공년도 
    	if(cell!=null){vo.setCnstrYear(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(31);  // 시설물상태 
    	if(cell!=null){vo.setFctSttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(32);  // 유역현황 
    	if(cell!=null){vo.setDgrSttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(33);  // 피해이력
    	if(cell!=null){vo.setDmgeHist(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(34);  // 특이사항
    	if(cell!=null){vo.setSpeclNote(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(35);  // 당시현황
    	if(cell!=null){vo.setWeakAppnSttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(36);  // 지정사유
    	if(cell!=null){vo.setAppnResn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(37);  // 사업종가능
    	if(cell!=null){vo.setWeakAppnBsType(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(38);  // 가능여부
    	if(cell!=null){vo.setWeakAppnPosYn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(39);  // 선정사유
    	if(cell!=null){vo.setWeakAppnSltnHy(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(40);  // 구역설정 
    	if(cell!=null){vo.setWeakAppnAreaSet(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(41);  // 당시종합의견 
    	if(cell!=null){vo.setWeakAppnMstOpn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(42);  // 근거 
    	if(cell!=null){}
    	
    	cell = row.getCell(43);  // 세부사항 
    	if(cell!=null){vo.setDetailMatter(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(44);  // 재해발생여부 
    	if(cell!=null){vo.setCnlevl1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(45);  // 사면계류상태
    	if(cell!=null){
    		if(svyType.equals("산사태")) {
    			//사면상태
    			vo.setCnlevl2(ExcelUtils.getCellValue(cell).replace("\n", ""));
    		}else if(svyType.equals("토석류")) {
    			//계류상태
    			vo.setCnlevl2(ExcelUtils.getCellValue(cell).replace("\n", ""));
    		}
    	}
    	
    	cell = row.getCell(46);  // 안정해석결과
    	if(cell!=null){
    		if(svyType.equals("산사태")) {
    			//사면안정해석결과
    			vo.setCnlevl3(ExcelUtils.getCellValue(cell).replace("\n", ""));
    		}else if(svyType.equals("토석류")) {
    			//시뮬레이션결과
    			vo.setCnlevl3(ExcelUtils.getCellValue(cell).replace("\n", ""));
    		}
    	}
    	
    	cell = row.getCell(47);  // 해제선정사유 1
    	if(cell!=null){vo.setCnlSlctRn1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(48);  // 해제선정사유 2
    	if(cell!=null){vo.setCnlSlctRn2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(49);  // 해제선정사유 3
    	if(cell!=null){vo.setCnlSlctRn3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(50);  // 해제선정사유 4
    	if(cell!=null){vo.setCnlSlctRn4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(51);  // 종합의견1
    	if(cell!=null){vo.setMstOpinion1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(52);  // 종합의견2
    	if(cell!=null){vo.setMstOpinion2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(53);  // 해제여부
    	if(cell!=null){vo.setCnlYn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(54);  // 사업 및 유역현황
    	if(cell!=null){vo.setBizNdDgrSttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(55);  // 피해이력 및 유역 변화
    	if(cell!=null){vo.setDmgHistNdDgrChag(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(56);  // 주민의견 및 기타 특이사항
    	if(cell!=null){vo.setHbtOpnNdEtcMatter(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(57);  // 사면계류상태1
    	if(cell!=null){vo.setRelisEvlsMrngSttus1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(58);  // 해제평가 재해발생여부
    	if(cell!=null){vo.setDsstrOccrrncAt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(59);  // 사면계류상태2
    	if(cell!=null){vo.setRelisEvlsMrngSttus2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(60);  // 최종의견1
    	if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(61);  // 최종의견2
    	if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(62);  // 최종의견3
    	if(cell!=null){vo.setOpinion3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(63);  // 1회(시뮬레이션) 
    	if(cell!=null){vo.setOneDebrisFlow(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(64);  // 정지조건(시뮬레이션)
    	if(cell!=null){vo.setStopCnd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(65);  // 가중치 
    	if(cell!=null){vo.setWghtVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(66);  // 토석류량(시뮬레이션) 
    	if(cell!=null){vo.setAllDebrisFlow(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(67);  // 저감여부 
    	if(cell!=null){vo.setReducAt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(69);  // 점수(사면안정) 
    	if(cell!=null){vo.setStableIntrprtScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(70);  // 공유방ID
    	if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(71);  // 로그인ID
    	if(cell!=null){vo.setLoginId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    			
 		String svyLat = Float.toString(Float.parseFloat(latD)+Float.parseFloat(latM)/60+Float.parseFloat(latS)/3600);
		vo.setSvyLat(svyLat);
    	
		String svyLon = Float.toString(Float.parseFloat(lonD)+Float.parseFloat(lonM)/60+Float.parseFloat(lonS)/3600);
		vo.setSvyLon(svyLon);
		
		
		return vo;
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectCnlSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return lssCnlSvyComptDAO.selectCnlSvyComptPhotoInfo(searchVO);
	}
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return lssCnlSvyComptDAO.selectLastUpdateMinMaxDate(searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return lssCnlSvyComptDAO.updateLocReCreate(map);
	};
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCnlLocReCreateSvyId(LssCnlSvyComptVO svyComptVO) throws Exception{
		return lssCnlSvyComptDAO.selectCnlLocReCreateSvyId(svyComptVO);
	}
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param svyComptVO
	 * @throws Exception
	 */
	public void updateCnlSvyComptPhotoList(LssCnlSvyComptVO searchVO) throws Exception{
		lssCnlSvyComptDAO.updateCnlSvyComptPhotoList(searchVO);
	}
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	@Override
	public EgovMap selectSvyPhotoNullList(LssCnlSvyComptVO searchVO) throws Exception {
		return lssCnlSvyComptDAO.selectSvyPhotoNullList(searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssCnlSvyComptVO map) throws Exception{
		return lssCnlSvyComptDAO.updatePhotoList(map);
	};
	
	/**
	 * 대상지 공간정보 조회 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeom(LssCnlSvyComptVO searchVO) throws Exception{
		return lssCnlSvyComptDAO.selectSvyComptGeom(searchVO);
	}

	/**
	 *  공간정보(유츌구)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertSvyComptGeomVnarapnt(HashMap<String, Object> geomMap) throws Exception {
		if(geomMap.get("vnaraPntWkt").toString().equals("")) {
			lssCnlSvyComptDAO.deleteSvyComptGeomVnarapnt(geomMap);
		}else {
			lssCnlSvyComptDAO.insertSvyComptGeomVnarapnt(geomMap);
		}
	}
	
	/**
	 *  공간정보(대피로)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertSvyComptGeomVnaralne(HashMap<String, Object> geomMap) throws Exception {
		if(geomMap.get("vnaraLneWkt").toString().equals("")) {
			lssCnlSvyComptDAO.deleteSvyComptGeomVnaralne(geomMap);
		}else {
			lssCnlSvyComptDAO.insertSvyComptGeomVnaralne(geomMap);
		}
		
		
	}
	
	/**
	 *  공간정보(폴리곤)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertSvyComptGeomVnaraPlgn(HashMap<String, Object> geomMap) throws Exception {
		if(geomMap.get("vnaraPlgnWkt").toString().equals("")) {
			lssCnlSvyComptDAO.deleteSvyComptGeomVnaraPlgn(geomMap);
		}else {
			lssCnlSvyComptDAO.insertSvyComptGeomVnaraPlgn(geomMap);
		}
		
	}
	
	/**
	 * 대상지 공간정보 조회 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeomPntLne(LssCnlSvyComptVO searchVO) throws Exception{
		return lssCnlSvyComptDAO.selectSvyComptGeomPntLne(searchVO);
	}
	

	/**
	 * 대상지 공간정보 조회 
	 */
	@Override
	public List<LssCnlSvyComptVO> selectSvyComptGeomPlgn(LssCnlSvyComptVO searchVO) throws Exception {
		return lssCnlSvyComptDAO.selectSvyComptGeomPlgn(searchVO);
	}
	
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
		
		LssCnlSupMapUtils utils = new LssCnlSupMapUtils();
		
		for (int i = 0; i < list.size(); i++) {
			LOGGER.info(i+"번째 생성 시작");

			EgovMap map = (EgovMap)list.get(i);
			
			String gid = map.get("gid").toString();
			//String exmnnId = map.get("exmnnid").toString();
			String mstId = map.get("mstid").toString();
			String sldId = map.get("svylabel").toString();
			String mstPath = map.get("mstpath").toString();
			
			schMap.put("mstId", mstId);
			schMap.put("sldId", sldId);
			//schMap.put("exmnnId", exmnnId);
			schMap.put("gid", gid);
			schMap.put("SE","CNL");
			
			imgResult = new ArrayList<String>();
			
			//현황도
			vo = utils.creatCnlLocExitMap(schMap);
			if(vo != null) {
				source = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
				target = mstPath.concat(".ncx")+File.separator+sldId+"_현황도.".concat(vo.getFileExtsn());
				
				File locFile3 = new File(source);
				
				if(locFile3.exists()) {
					EgovFileUtil.cp(source, fieldBookDir+target);
					imgResult.add("/"+mstPath.concat(".ncx")+"/"+sldId+"_현황도.".concat(vo.getFileExtsn()));
				}else {
					LOGGER.error(vo.getStreFileNm()+".".concat(vo.getFileExtsn())+" 파일이 존재하지 않아 "+sldId+"_현황도.".concat(vo.getFileExtsn())+"복사를 실패하였습니다.(gId:"+gid+")");
				}
			}
			
			if(imgResult.size() > 0) {
				schMap.put("lcMap",new ObjectMapper().writeValueAsString(imgResult));
				
				lssCnlSvyComptDAO.updateComptLcMap(schMap);
			}
			LOGGER.info(sldId+" 생성 종료");
		}
		utils.closeWorkspace();
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = lssCnlSvyComptDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * 로그인한 유저의 권한있는 공유방 목록 조회
	 * @throws Exception
	 */
	public List<String> selectAuthorCnrsList(HashMap<String, Object> map) throws Exception{
		return lssCnlSvyComptDAO.selectAuthorCnrsList(map);
	}
}
