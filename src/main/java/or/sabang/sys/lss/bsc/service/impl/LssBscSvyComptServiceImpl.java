package or.sabang.sys.lss.bsc.service.impl;

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

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptService;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.utl.ExcelUtils;

@Service("lssBscSvyComptService")
public class LssBscSvyComptServiceImpl extends EgovAbstractServiceImpl implements LssBscSvyComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssBscSvyComptDAO")
	private LssBscSvyComptDAO lssBscSvyComptDAO;
	
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
	public int selectBscSvyComptListTotCnt(LssBscSvyComptVO searchVO) throws Exception {
        return lssBscSvyComptDAO.selectBscSvyComptListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<LssBscSvyComptVO> selectBscSvyComptList(LssBscSvyComptVO searchVO) throws Exception {
		return lssBscSvyComptDAO.selectBscSvyComptList(searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 */
	@Override
	public LssBscSvyComptVO selectBscSvyComptDetail(LssBscSvyComptVO svyComptVO) throws Exception {
		return lssBscSvyComptDAO.selectBscSvyComptDetail(svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateBscSvyCompt(LssBscSvyComptVO svyComptVO) throws Exception {
		lssBscSvyComptDAO.updateBscSvyCompt(svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteBscSvyCompt(LssBscSvyComptVO svyComptVO) throws Exception {
		lssBscSvyComptDAO.deleteBscSvyCompt(svyComptVO);
	}

	/**
	 * 대상지를 엑셀다운로드한다. 
	 */
	@Override
	public Map<String, Object> selectBscSvyComptListExcel(LssBscSvyComptVO svyComptVO) throws Exception {
		
		List<?> _result = lssBscSvyComptDAO.selectBscSvyComptListExcel(svyComptVO);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}

	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectBscSvyComptMaxYear() throws Exception {
		return lssBscSvyComptDAO.selectBscSvyComptMaxYear();
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 */
	@Override
	public String selectBscSvyComptMaxMonth() throws Exception {
		return lssBscSvyComptDAO.selectBscSvyComptMaxMonth();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 */
	public List<?> selectBscSvyComptYear() throws Exception{
		return lssBscSvyComptDAO.selectBscSvyComptYear();
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 */
	@Override
	public JSONObject updateBscSvyComptExcel(LssBscSvyComptVO svyComptVO,MultipartFile mFile) throws Exception {

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
	    	if (xssfWB != null && xssfWB.getNumberOfSheets() == 1) {
	    		XSSFSheet xssfSheet = xssfWB.getSheetAt(0);  //시트 가져오기
	            XSSFRow xssfRow = xssfSheet.getRow(4); //row 가져오기
	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=4; j<rowsCnt; j++){ //row 루프
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		LssBscSvyComptVO vo = new LssBscSvyComptVO();
	            		vo = convertXSSFCellValues(row,vo);
	            		try {
	            			HashMap<String, Object> schMap = new HashMap<>();
	            			List<EgovMap> paramList = lssBscSvyComptDAO.selectBscLocReCreateSvyId(vo);// 기존데이터 SVYCOMPT 테이블에서 값 가져오기.
	            			if(!paramList.isEmpty()) {
	            				//vo.setLoginId(paramList.get(0).get("loginid").toString());
		            			vo.setGid(paramList.get(0).get("gid").toString());
		            			vo.setAttr(paramList.get(0).get("attr").toString());
		            			vo.setSvyLon(paramList.get(0).get("lon").toString());
		            			vo.setSvyLat(paramList.get(0).get("lat").toString());
		            			if(paramList.get(0).get("fid") != null) {
		            				vo.setFid(Integer.parseInt(paramList.get(0).get("fid").toString()));
		            			}
		            			vo.setMstId(paramList.get(0).get("mstid").toString());
	            			} else {
		            			vo.setSvyLon(vo.getBpx());
		            			vo.setSvyLat(vo.getBpy());
		            			//vo.setFid(extFieldBookService.selectBscUpdtFid());
	            			}
	            			
	            			vo.setOpinion(vo.getOpinion().toString().replaceAll("\\R", " "));
	        				lssBscSvyComptDAO.updateBscSvyComptExcel(vo);
	        				
//	        				schMap.put("mst_id", Integer.parseInt(paramList.get(0).get("mstid").toString()));
//	        				List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
//	        				schMap.put("SE","BSC");
//	        				schMap.put("type",vo.getSvyType());
//	        				schMap.put("gid",Integer.parseInt(paramList.get(0).get("gid").toString()));
//	        				schMap.put("_label", vo.getSvyId());
//	        				schMap.put("path", fieldBookUploadPath.concat("/").concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/")); //저장경로
//	        				//schMap.put("zoom",Integer.parseInt(comptVO.getChangedZoom()));
//	        				LOGGER.info("====== 위치도 생성 시작 ======");			
//	        				extFieldBookService.updateComptLcMap(schMap);
//	        				LOGGER.info("====== 위치도 생성 종료 ======");
	        				
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

	private LssBscSvyComptVO convertXSSFCellValues(XSSFRow row, LssBscSvyComptVO vo) {
		
		double bpx = 0;
		double bpy = 0;
		double epx = 0;
		double epy = 0;
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;

    	cell = row.getCell(0);  //조사ID
    	if(cell!=null){vo.setSvyId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(1);  //조사자
    	if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(2);  //조사유형
    	if(cell!=null){vo.setSvyType(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(3);  //조사일자
    	String svyDtValue[] = ExcelUtils.getCellValue(cell).split(" ");
    	vo.setSvyDtValue(svyDtValue[0]);
    	if(cell!=null){vo.setSvyDt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	// 시점좌표_위도
    	if(row.getCell(4) != null && row.getCell(5) != null && row.getCell(6) != null) {
    		bpy = Double.parseDouble(row.getCell(4).toString()) + Double.parseDouble(row.getCell(5).toString())/60 + Double.parseDouble(row.getCell(6).toString())/3600;
    		vo.setBpy(String.valueOf(bpy));
    	}
    	
    	// 시점좌표_경도
    	if(row.getCell(7) != null && row.getCell(8) != null && row.getCell(9) != null) {
    		bpx = Double.parseDouble(row.getCell(7).toString()) + Double.parseDouble(row.getCell(8).toString())/60 + Double.parseDouble(row.getCell(9).toString())/3600;
    		vo.setBpx(String.valueOf(bpx));
    	}
    	
    	cell = row.getCell(10);	//고도값(조사시점)
    	if(cell != null) {
    		vo.setBz(ExcelUtils.getCellValue(cell).replace("\n", ""));
    	}
    	
    	// 끝점좌표_위도
    	if(row.getCell(11) != null && row.getCell(12) != null && row.getCell(13) != null) {
    		epy = Double.parseDouble(row.getCell(11).toString()) + Double.parseDouble(row.getCell(12).toString())/60 + Double.parseDouble(row.getCell(13).toString())/3600;
    		vo.setEpy(String.valueOf(epy));
    	}
    	
    	// 끝점좌표_경도
    	if(row.getCell(14) != null && row.getCell(15) != null && row.getCell(16) != null) {
    		epx = Double.parseDouble(row.getCell(14).toString()) + Double.parseDouble(row.getCell(15).toString())/60 + Double.parseDouble(row.getCell(16).toString())/3600;
    		vo.setEpx(String.valueOf(epx));
    	}
    	
    	cell = row.getCell(17);	//고도값(조사끝점)
    	if(cell != null) {
    		vo.setEz(ExcelUtils.getCellValue(cell).replace("\n", ""));
    	}
    	
    	cell = row.getCell(18);  //관할1
    	if(cell!=null){vo.setSvyRegion1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(19);  //관할2
    	if(cell!=null){vo.setSvyRegion2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(20);  //시도
    	if(cell!=null){vo.setSvySd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(21);  //시군구
    	if(cell!=null){vo.setSvySgg(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(22);  //읍면동
    	if(cell!=null){vo.setSvyEmd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(23);  //리
    	if(cell!=null){vo.setSvyRi(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(24);  //지번
    	if(cell!=null){vo.setSvyJibun(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	if(row.getCell(2).toString().equals("산사태")) {
    		cell = row.getCell(25);  //보호대상_측정값
        	if(cell!=null){vo.setSaftyVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(26);  //보호대상_점수
        	if(cell!=null){vo.setSaftyScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(27);  //경사길이_측정값
        	if(cell!=null){vo.setsLenVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(28);  //경사길이_점수
        	if(cell!=null){vo.setsLenScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(29);  //경사도_측정값
        	if(cell!=null){vo.setSlopeVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(30);  //경사도_점수
        	if(cell!=null){vo.setSlopeScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(31);  //사면형_측정값
        	if(cell!=null){vo.setsFormVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(32);  //사면형_점수
        	if(cell!=null){vo.setsFormScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(33);  //임상_측정값
        	if(cell!=null){vo.setFrstFrVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(34);  //임상_점수
        	if(cell!=null){vo.setFrstFrScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(35);  //모암_측정값
        	if(cell!=null){vo.setPrntRckVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(36);  //모암_점수
        	if(cell!=null){vo.setPrntRckScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(37);  //위험요인_측정값
        	if(cell!=null){vo.setRskFactorVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(38);  //위험요인_점수
        	if(cell!=null){vo.setRskFactorScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	} else {
    		cell = row.getCell(39);  //보호대상_측정값
        	if(cell!=null){vo.setSaftyVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(40);  //보호대상_점수
        	if(cell!=null){vo.setSaftyScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(41);  //황폐발생원_측정값
        	if(cell!=null){vo.setDevOcCauseVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(42);  //황폐발생원_점수
        	if(cell!=null){vo.setDevOcCauseScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(43);  //계류평균경사_측정값
        	if(cell!=null){vo.setTrntAvgSlpVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(44);  //계류평균경사_점수
        	if(cell!=null){vo.setTrntAvgSlpScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(45);  //집수면적_측정값
        	if(cell!=null){vo.setWclctAreaVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(46);  //집수면적_점수
        	if(cell!=null){vo.setWclctAreaScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(47);  //총계류길이_측정값
        	if(cell!=null){vo.setTlTrntLtVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(48);  //총계류길이_점수
        	if(cell!=null){vo.setTlTrntLtScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(49);  //위험인자_측정값
        	if(cell!=null){vo.setRskFactorVal(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        	
        	cell = row.getCell(50);  //위험인자_점수
        	if(cell!=null){vo.setRskFactorScore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	}
    	
    	cell = row.getCell(51);  //점수계
    	if(cell!=null){vo.setSvySm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(52);  //실태조사 필요성
    	if(cell!=null){vo.setNcssty(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(53);  //주요위험성
    	if(cell!=null){vo.setMainRisk(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(54);  //조사자의견
    	if(cell!=null){vo.setOpinion(ExcelUtils.getCellValue(cell).replace("\n", "").replaceAll("'","''"));}
    	//////////////////////////////////////////////////////////////
    	cell = row.getCell(55);  //공유방 ID
    	if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	cell = row.getCell(56);  //로그인 ID
    	if(cell!=null){vo.setLoginId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
        
		return vo;
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectBscSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return lssBscSvyComptDAO.selectBscSvyComptPhotoInfo(searchVO);
	}
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return lssBscSvyComptDAO.selectLastUpdateMinMaxDate(searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return lssBscSvyComptDAO.updateLocReCreate(map);
	};
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectBscLocReCreateSvyId(LssBscSvyComptVO svyComptVO) throws Exception{
		return lssBscSvyComptDAO.selectBscLocReCreateSvyId(svyComptVO);
	}
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param svyComptVO
	 * @throws Exception
	 */
	public void updateBscSvyComptPhotoList(LssBscSvyComptVO searchVO) throws Exception{
		lssBscSvyComptDAO.updateBscSvyComptPhotoList(searchVO);
	}
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	@Override
	public EgovMap selectSvyPhotoNullList(LssBscSvyComptVO searchVO) throws Exception {
		return lssBscSvyComptDAO.selectSvyPhotoNullList(searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssBscSvyComptVO map) throws Exception{
		return lssBscSvyComptDAO.updatePhotoList(map);
	};
}
