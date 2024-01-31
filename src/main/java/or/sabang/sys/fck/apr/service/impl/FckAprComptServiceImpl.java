package or.sabang.sys.fck.apr.service.impl;

import java.io.IOException;
import java.io.InputStream;
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

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.fck.apr.service.FckAprComptService;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.utl.ExcelUtils;

@Service("fckAprComptService")
public class FckAprComptServiceImpl extends EgovAbstractServiceImpl implements FckAprComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="fckAprComptDAO")
	private FckAprComptDAO fckAprComptDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectFckAprComptListTotCnt(FckAprComptVO searchVO) throws Exception {
        return fckAprComptDAO.selectFckAprComptListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<FckAprComptVO> selectFckAprComptList(FckAprComptVO searchVO) throws Exception {
		return fckAprComptDAO.selectFckAprComptList(searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 */
	@Override
	public FckAprComptVO selectFckAprComptDetail(FckAprComptVO ComptVO) throws Exception {
		return fckAprComptDAO.selectFckAprComptDetail(ComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateFckAprCompt(FckAprComptVO ComptVO) throws Exception {
		fckAprComptDAO.updateFckAprCompt(ComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteFckAprCompt(FckAprComptVO ComptVO) throws Exception {
		fckAprComptDAO.deleteFckAprCompt(ComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectFckAprComptMaxYear() throws Exception {
		return fckAprComptDAO.selectFckAprComptMaxYear();
	}
	/**
	 * 대상지 월최대값을 조회한다.
	 */
	@Override
	public String selectFckAprComptMaxMonth() throws Exception {
		// TODO Auto-generated method stub
		return fckAprComptDAO.selectFckAprComptMaxMonth();
	}

	/**
	 * 대상지 연도를 조회한다.
	 */
	@Override
	public List<?> selectFckAprComptYear() throws Exception {
		return fckAprComptDAO.selectFckAprComptYear();
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectFckAprProjDMS(HashMap<String, Object> map) throws Exception{
		return fckAprComptDAO.selectFckAprProjDMS(map);
	}
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectFckAprProjBessel(HashMap<String, Object> map) throws Exception{
		return fckAprComptDAO.selectFckAprProjBessel(map);
	}
	
	/**
	 * 공유방 명을 조회한다.
	 */
	@Override
	public EgovMap selectFieldBookNm(FckAprComptVO ComptVO) throws Exception {
		return fckAprComptDAO.selectFieldBookNm(ComptVO);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectFckAprComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return fckAprComptDAO.selectFckAprComptPhotoInfo(searchVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다. 
	 */
	@Override
	public Map<String, Object> selectFckAprSvyComptListExcel(FckAprComptVO aprComptVO) throws Exception {
		
		List<?> _result = fckAprComptDAO.selectAprSvyComptListExcel(aprComptVO);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return fckAprComptDAO.selectLastUpdateMinMaxDate(searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return fckAprComptDAO.updateLocReCreate(map);
	};
	
	@Override
	public JSONObject updateFckAprComptExcel(FckAprComptVO aprComptVO,MultipartFile mFile) throws Exception{
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		int rowsCnt = 4;
		
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;
		
		if(extention.matches("xlsx")) {
			XSSFWorkbook xssfWB = null;
			try {
				xssfWB = new XSSFWorkbook(inputStream);
			} catch (IOException e) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
	    		LOGGER.debug("=====>>>>> ERR : IOException "+e.getMessage());
	    	} catch (Exception e) {
	    		LOGGER.debug("=====>>>>> ERR : "+e.getMessage());
	    	}
			if (xssfWB != null && xssfWB.getNumberOfSheets() == 1) {
				XSSFSheet xssfSheet = xssfWB.getSheetAt(0);  //시트 가져오기
	            XSSFRow xssfRow = xssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		FckAprComptVO vo = new FckAprComptVO();
	            		vo = convertXSSFCellValues(row,vo);
	            		try {
	            			HashMap<String, Object> schMap = new HashMap<>();
	            			List<EgovMap> paramList = fckAprComptDAO.selectAprLocReCreateSvyId(vo);// 기존데이터 APRCOMT 테이블에서 값 가져오기.
	            			if(!paramList.isEmpty()) {
	            				vo.setGid(paramList.get(0).get("gid").toString());
		            			vo.setAttr(paramList.get(0).get("attr").toString());
		            			//vo.setSvyLon(paramList.get(0).get("lon").toString());
		            			//vo.setSvyLat(paramList.get(0).get("lat").toString());
		            			//vo.setFid(Integer.parseInt(paramList.get(0).get("fid").toString()));
		            			//vo.setMstId(paramList.get(0).get("mstid").toString());
	            			}else {
	            				vo.setSvyLon(vo.getSvyLon());
		            			vo.setSvyLat(vo.getSvyLat());
		            			//vo.setFid(extFieldBookService.selectFckUpdtFid());
	            			}
	            			
	            			//vo.setOpinion(vo.getOpinion().toString().replaceAll("\\R", " "));
	            			fckAprComptDAO.updateFckAprComptExcel(vo);
	            			
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
		returnInsertLog.put("total", rowsCnt-4);
		returnInsertLog.put("successCnt", successCnt);
		returnInsertLog.put("failedIds", failedIdsArray);
		return returnInsertLog;
	}
	
	private FckAprComptVO convertXSSFCellValues(XSSFRow row,FckAprComptVO vo) throws Exception{
		XSSFCell cell = null;
		String svyType = vo.getSvyType();
		
		if(svyType == null || svyType.length() == 0) {
			return null;
		}
		
		cell = row.getCell(0); //일련번호
		if(cell!=null){vo.setFid(Integer.valueOf(ExcelUtils.getCellValue(cell).toString()));}
		else {return null;}
		
		cell = row.getCell(1); //조사ID
		if(cell!=null){vo.setSvyId(ExcelUtils.getCellValue(cell));}
		else {return null;}
		
		cell = row.getCell(2); //시설년도
		if(cell!=null){vo.setFcltYear(ExcelUtils.getCellValue(cell));}
		
		cell = row.getCell(3); //시도
		if(cell!=null){vo.setSvySd(ExcelUtils.getCellValue(cell));}
		
		cell = row.getCell(4); //시군
		if(cell!=null){vo.setSvySgg(ExcelUtils.getCellValue(cell));}
		
		cell = row.getCell(5); //읍면
		if(cell!=null){vo.setSvyEmd(ExcelUtils.getCellValue(cell));}
		
		cell = row.getCell(6); //리동
		if(cell!=null){vo.setSvyRi(ExcelUtils.getCellValue(cell));}
		
		cell = row.getCell(7); //지번
		if(cell!=null){vo.setSvyJibun(ExcelUtils.getCellValue(cell));}
		
		//cell = row.getCell(8); //속칭
		
		if(svyType.equals("사방댐")) {
			cell = row.getCell(9); //사방댐종류
			if(cell!=null){vo.setKnd(ExcelUtils.getCellValue(cell));}
			
			cell = row.getCell(10); //형식
			if(cell!=null){vo.setSvyForm(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(11); //상장
			if(cell!=null){vo.setFcltUprHg(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(12); //하장
			if(cell!=null){vo.setFcltLwrHg(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(13); //유효
			if(cell!=null){vo.setFcltStkHg(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(14); //점검일시
			if(cell!=null){vo.setSvyDt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(15); //조사자
			if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(16); //관리번호
			if(cell!=null){vo.setEcnrRnum(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(17); //국가지점번호
			if(cell!=null){vo.setNationSpotNum(ExcelUtils.getCellValue(cell));}
			//위도 도,분,초
			if(row.getCell(18)!=null && row.getCell(19)!=null && row.getCell(20)!=null){
				double svyLat = Double.parseDouble(row.getCell(18).toString()) + Double.parseDouble(row.getCell(19).toString())/60 + Double.parseDouble(row.getCell(20).toString())/3600;
				vo.setSvyLat(String.valueOf(svyLat));
			}
			//경도 도,분,초
			if(row.getCell(21)!=null && row.getCell(22)!=null && row.getCell(23)!=null){
				double svyLon = Double.parseDouble(row.getCell(21).toString()) + Double.parseDouble(row.getCell(22).toString())/60 + Double.parseDouble(row.getCell(23).toString())/3600;
				vo.setSvyLon(String.valueOf(svyLon));
			}
			cell = row.getCell(24); //본댐측정
			if(cell!=null){vo.setOrginlDamVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(25); //측벽측정
			if(cell!=null){vo.setSideWallVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(26); //물받이측정
			if(cell!=null){vo.setDwnsptVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(27); //본댐
			if(cell!=null){vo.setOrginlDamCd(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(28); //측벽
			if(cell!=null){vo.setSideWallCd(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(29); //물받이
			if(cell!=null){vo.setDwnsptCd(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(30); //수문
			if(cell!=null){vo.setFlugtJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(31); //식생
			if(cell!=null){vo.setVtnsttusJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(32); //안전시설
			if(cell!=null){vo.setSffcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(33); //접근도로
			if(cell!=null){vo.setAccssrdJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(34); //기타
			if(cell!=null){vo.setEtcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(35); //저사상태
			if(cell!=null){vo.setSnddpsitJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(36); //저사량
			if(cell!=null){vo.setSnddpsitVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(37); //점검결과
			if(cell!=null){vo.setFckRslt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(38); //조치사항
			if(cell!=null){vo.setMngmtr(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(39); //지정해제
			if(cell!=null){vo.setAppnRelis(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(40); //종합의견1
			if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(41); //종합의견2
			if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(42); //종합의견3
			if(cell!=null){vo.setOpinion3(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(43); //종합의견4
			if(cell!=null){vo.setOpinion4(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(44); //종합의견5
			if(cell!=null){vo.setOpinion5(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(45); //주요시설특이사항
			if(cell!=null){vo.setMainFcltSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(46); //부대시설특이사항
			if(cell!=null){vo.setSbrsSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(47); //기타특이사항
			if(cell!=null){vo.setEtcSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(48); //공유방ID
			if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(49); //로그인ID
			if(cell!=null){vo.setLoginId(ExcelUtils.getCellValue(cell));}
		}else if(svyType.equals("계류보전")) {
			cell = row.getCell(9); //지정면적
			if(cell!=null){vo.setDsgArea(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(10); //점검일시
			if(cell!=null){vo.setSvyDt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(11); //점검자
			if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell));}
			//시점위도
			if(row.getCell(12)!=null && row.getCell(13)!=null && row.getCell(14)!=null) {
				double svyLat = Double.parseDouble(row.getCell(12).toString()) + Double.parseDouble(row.getCell(14).toString())/60 + Double.parseDouble(row.getCell(15).toString())/3600;
				vo.setSvyLat(String.valueOf(svyLat));
				vo.setBpy(String.valueOf(svyLat));
			}
			//시점경도
			if(row.getCell(15)!=null && row.getCell(16)!=null && row.getCell(17)!=null) {
				double svyLon = Double.parseDouble(row.getCell(15).toString()) + Double.parseDouble(row.getCell(16).toString())/60 + Double.parseDouble(row.getCell(17).toString())/3600;
				vo.setSvyLon(String.valueOf(svyLon));
				vo.setBpx(String.valueOf(svyLon));
			}
			//종점위도
			if(row.getCell(18)!=null && row.getCell(19)!=null && row.getCell(20)!=null) {
				double svyLat = Double.parseDouble(row.getCell(18).toString()) + Double.parseDouble(row.getCell(19).toString())/60 + Double.parseDouble(row.getCell(20).toString())/3600;
				vo.setEpy(String.valueOf(svyLat));
			}
			//종점경도
			if(row.getCell(21)!=null && row.getCell(22)!=null && row.getCell(23)!=null) {
				double svyLon = Double.parseDouble(row.getCell(21).toString()) + Double.parseDouble(row.getCell(22).toString())/60 + Double.parseDouble(row.getCell(23).toString())/3600;
				vo.setEpx(String.valueOf(svyLon));
			}
			cell = row.getCell(24); //시설물종류
			if(cell!=null){vo.setKnd(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(25); //길이
			if(cell!=null){vo.setFcltLng(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(26); //폭
			if(cell!=null){vo.setFcltRng(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(27); //깊이
			if(cell!=null){vo.setFcltDept(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(28); //골막이판정값
			if(cell!=null){vo.setChkdamJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(29); //기슭막이판정값
			if(cell!=null){vo.setRvtmntJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(30); //바닥막이판정값
			if(cell!=null){vo.setGrdstablJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(31); //골막이
			if(cell!=null){vo.setChkdamVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(32); //기슭막이
			if(cell!=null){vo.setRvtmntVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(33); //바닥막이
			if(cell!=null){vo.setGrdstablVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(34); //계류상태
			if(cell!=null){vo.setMooringJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(35); //수문
			if(cell!=null){vo.setFlugtJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(36); //식생상태
			if(cell!=null){vo.setVtnsttusJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(37); //안전시설
			if(cell!=null){vo.setSffcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(38); //접근도로
			if(cell!=null){vo.setAccssrdJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(39); //기타
			if(cell!=null){vo.setEtcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(40); //점검결과
			if(cell!=null){vo.setFckRslt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(41); //조치사항
			if(cell!=null){vo.setMngmtr(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(42); //지정해제
			if(cell!=null){vo.setAppnRelis(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(43); //종합의견1
			if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(44); //종합의견2
			if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(45); //종합의견3
			if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(46); //종합의견4
			if(cell!=null){vo.setOpinion4(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(47); //종합의견5
			if(cell!=null){vo.setOpinion5(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(48); //주요시설_특이사항
			if(cell!=null){vo.setMainFcltSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(49); //부대시설_특이사항
			if(cell!=null){vo.setSbrsSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(50); //기타_특이사항
			if(cell!=null){vo.setEtcSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(51); //공유방ID
			if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(52); //로그인ID
			if(cell!=null){vo.setLoginId(ExcelUtils.getCellValue(cell));}
		}else if(svyType.equals("산지사방")) {
			cell = row.getCell(9); //지정면적
			if(cell!=null){vo.setDsgArea(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(10); //점검일시
			if(cell!=null){vo.setSvyDt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(11); //점검자
			if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell));}
			//위도 도,분,초
			if(row.getCell(12)!=null && row.getCell(13)!=null && row.getCell(14)!=null){
				double svyLat = Double.parseDouble(row.getCell(12).toString()) + Double.parseDouble(row.getCell(13).toString())/60 + Double.parseDouble(row.getCell(14).toString())/3600;
				vo.setSvyLat(String.valueOf(svyLat));
			}
			//경도 도,분,초
			if(row.getCell(15)!=null && row.getCell(16)!=null && row.getCell(17)!=null){
				double svyLon = Double.parseDouble(row.getCell(15).toString()) + Double.parseDouble(row.getCell(16).toString())/60 + Double.parseDouble(row.getCell(17).toString())/3600;
				vo.setSvyLon(String.valueOf(svyLon));
			}
			cell = row.getCell(18); //시설물종류
			if(cell!=null){vo.setKnd(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(19); //보강시설판정값
			if(cell!=null){vo.setReinfcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(20); //보호시설판정값
			if(cell!=null){vo.setPrtcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(21); //배수시설판정값
			if(cell!=null){vo.setDrngJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(22); //보강시설
			if(cell!=null){vo.setReinfcVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(23); //보호시설
			if(cell!=null){vo.setPrtcVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(24); //배수시설
			if(cell!=null){vo.setDrngVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(25); //사면상태
			if(cell!=null){vo.setSlopeJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(26); //수문
			if(cell!=null){vo.setFlugtJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(27); //식생상태
			if(cell!=null){vo.setVtnsttusJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(28); //안전시설
			if(cell!=null){vo.setSffcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(29); //접근도로
			if(cell!=null){vo.setAccssrdJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(30); //기타
			if(cell!=null){vo.setEtcJdgVal(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(31); //점검결과
			if(cell!=null){vo.setFckRslt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(32); //조치사항
			if(cell!=null){vo.setMngmtr(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(33); //지정해제
			if(cell!=null){vo.setAppnRelis(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(34); //종합의견1
			if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(35); //종합의견2
			if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(36); //종합의견3
			if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(37); //종합의견4
			if(cell!=null){vo.setOpinion4(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(38); //종합의견5
			if(cell!=null){vo.setOpinion5(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(39); //주요시설_특이사항
			if(cell!=null){vo.setMainFcltSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(40); //부대시설_특이사항
			if(cell!=null){vo.setSbrsSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(41); //기타_특이사항
			if(cell!=null){vo.setEtcSlNt(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(42); //공유방ID
			if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell));}
			cell = row.getCell(43); //로그인ID
			if(cell!=null){vo.setLoginId(ExcelUtils.getCellValue(cell));}
		}
		
		return vo;
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = fckAprComptDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * 권한 코드 확인
	 * @throws Exception
	 */
	public String selectAuthorCode(HashMap<String, Object> map) throws Exception{
		String result = fckAprComptDAO.selectAuthorCode(map);
		if(result == null) result = "UNAUTHORD";
		return result;
	}
}
