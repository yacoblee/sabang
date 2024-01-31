package or.sabang.sys.lss.wka.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
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

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.com.utl.sim.service.EgovFileTool;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.impl.ExtFieldBookDAO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptService;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.utl.LssWkaSupMapUtils;
import or.sabang.utl.SupMapCommonUtils;

@Service("lssWkaSvyComptService")
public class LssWkaSvyComptServiceImpl extends EgovAbstractServiceImpl implements LssWkaSvyComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	
	@Resource(name="lssWkaSvyComptDAO")
	private LssWkaSvyComptDAO lssWkaSvyComptDAO;
	
	@Resource(name="extFieldBookDAO")
	private ExtFieldBookDAO extFieldBookDAO;
	
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
	public int selectWkaSvyComptListTotCnt(LssWkaSvyComptVO searchVO) throws Exception {
        return lssWkaSvyComptDAO.selectWkaSvyComptListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<LssWkaSvyComptVO> selectWkaSvyComptList(LssWkaSvyComptVO searchVO) throws Exception {
		return lssWkaSvyComptDAO.selectWkaSvyComptList(searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 */
	@Override
	public LssWkaSvyComptVO selectWkaSvyComptDetail(LssWkaSvyComptVO svyComptVO) throws Exception {
		return lssWkaSvyComptDAO.selectWkaSvyComptDetail(svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateWkaCompt(HashMap<String, Object> wkaCompt, HashMap<String, Object> wkaCompt2) throws Exception {
		lssWkaSvyComptDAO.updateWkaCompt(wkaCompt,wkaCompt2);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateWkaSvyCompt(LssWkaSvyComptVO svyComptVO) throws Exception {
		lssWkaSvyComptDAO.updateWkaSvyCompt(svyComptVO);
	}
	
	/**
	 * 대상지 공간정보를 수정한다.
	 */
	public void updateWkaSvyComptGeom(LssWkaSvyComptVO svyComptVO) throws Exception {
		lssWkaSvyComptDAO.updateWkaSvyComptGeom(svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteWkaSvyCompt(LssWkaSvyComptVO svyComptVO) throws Exception {
		lssWkaSvyComptDAO.deleteWkaSvyCompt(svyComptVO);
	}

	/**
	 * 대상지를 엑셀다운로드한다. 
	 */
	@Override
	public Map<String, Object> selectWkaSvyComptListExcel(LssWkaSvyComptVO svyComptVO) throws Exception {
		
		List<?> _result = lssWkaSvyComptDAO.selectWkaSvyComptListExcel(svyComptVO);
		List<?> _photoTagResult = lssWkaSvyComptDAO.selectPhotoTagList(svyComptVO);
		
		//법정동코드
		String[] legaldongcd_result = new String[_result.size()];
		//산사태시행청코드
		String[] mnagncd_result = new String[_result.size()];
		
		for(int i=0;i<_result.size();i++) {
			EgovMap map = (EgovMap) _result.get(i);
			String legaldongcd = lssWkaSvyComptDAO.selectLegaldongcd(map);
			String mnagncd = lssWkaSvyComptDAO.selectMnagncd(map);
			
			legaldongcd_result[i] = legaldongcd;
			mnagncd_result[i] = mnagncd;
		}
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		_map.put("photoTagList", _photoTagResult);
		_map.put("legaldongcdList", legaldongcd_result);
		_map.put("mnagncdList", mnagncd_result);
		return _map;
	}

	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectWkaSvyComptMaxYear() throws Exception {
		return lssWkaSvyComptDAO.selectWkaSvyComptMaxYear();
	}
	
	/**
	 * 대상지 조사월 최대값을 조회한다.
	 */
	@Override
	public String selectWkaSvyComptMaxMonth() throws Exception {
		return lssWkaSvyComptDAO.selectWkaSvyComptMaxMonth();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 */
	public List<?> selectWkaSvyComptYear() throws Exception{
		return lssWkaSvyComptDAO.selectWkaSvyComptYear();
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 */
	@Override
	public JSONObject updateWkaSvyComptExcel(LssWkaSvyComptVO svyComptVO,MultipartFile mFile) throws Exception {

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
	    		LOGGER.debug("=====>>>>> ERR : IOExceptio n "+e.getMessage());
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
                                                                                                                                                                                                                                	            		LssWkaSvyComptVO vo = new LssWkaSvyComptVO();
 	            		vo = convertXSSFCellValues(row,vo,xssfWB);
	            		try {
	            			HashMap<String, Object> schMap = new HashMap<>();
//	            			List<EgovMap> paramList = lssWkaSvyComptDAO.selectWkaLocReCreateSvyId(vo);// 기존데이터 SVYCOMPT 테이블에서 값 가져오기.
//	            			if(!paramList.isEmpty()) {
//	            				vo.setMstId(paramList.get(0).get("mstid").toString());
//	            				vo.setLoginId(paramList.get(0).get("loginid").toString());
//		            			vo.setGid(paramList.get(0).get("gid").toString());
//		            			vo.setAttr(paramList.get(0).get("attr").toString());
//		            			vo.setLon(paramList.get(0).get("lon").toString());
//		            			vo.setLat(paramList.get(0).get("lat").toString());
//		            			vo.setFid(Integer.parseInt(paramList.get(0).get("fid").toString()));
//	            			} else {
//		            			vo.setSvyLon(vo.getBpx());
//		            			vo.setSvyLat(vo.getBpx());
//		            			vo.setFid(extFieldBookService.selectBscUpdtFid());
//	            			}
	            			
//	            			vo.setOpinion(vo.getOpinion().toString().replaceAll("\\R", " "));
// 	        				lssWkaSvyComptDAO.updateWkaSvyComptExcel(vo);
	        				
//	        				schMap.put("mst_id", Integer.parseInt(paramList.get(0).get("mstid").toString()));
//	        				List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
//	        				schMap.put("SE","Wka");
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

	private LssWkaSvyComptVO convertXSSFCellValues(XSSFRow row, LssWkaSvyComptVO vo, XSSFWorkbook wb) {
		
		double lon = 0;
		double lat = 0;
		int rowNum = row.getRowNum()+1;
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		FormulaEvaluator fev = wb.getCreationHelper().createFormulaEvaluator();
		
    	cell = row.getCell(0); // 조사정보 fid
    	if(cell!=null){vo.setFid(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(1); // 조사구분
    	if(cell!=null){vo.setSvyType(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(2); // 기초ID
    	if(cell!=null){vo.setSvyId(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(3); // 시도
    	if(cell!=null){vo.setSvySd(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(4); // 시군구
    	if(cell!=null){vo.setSvySgg(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(5); // 읍면
    	if(cell!=null){vo.setSvyEmd(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(6); // 리동
    	if(cell!=null){vo.setSvyRi(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(7); // 지번
    	if(cell!=null){vo.setSvyJibun(ExcelUtils.getCellValue(cell).replace("\n", ""));}

     	cell = row.getCell(8); // 관리주체
    	if(cell!=null){vo.setMgc(ExcelUtils.getCellValue(cell).replace("\n", ""));}
    	
    	if(row.getCell(3) != null && row.getCell(4) != null && row.getCell(5) != null && row.getCell(6) != null && row.getCell(7) != null) { // 주소
    		String addr = row.getCell(3).toString() +" "+ row.getCell(4).toString() +" "+ row.getCell(5).toString() +" "+ row.getCell(6).toString() +" "+ row.getCell(7).toString();
    		vo.setAddr(addr);
    	}

    	if(!(row.getCell(9).toString().isEmpty() && row.getCell(10).toString().isEmpty() && row.getCell(11).toString().isEmpty())) { //위도 
    		lat = Double.parseDouble(row.getCell(9).toString()) + Double.parseDouble(row.getCell(10).toString())/60 + Double.parseDouble(row.getCell(11).toString())/3600;
    		vo.setLat(String.valueOf(lat));
    	}
    	
    	if(!(row.getCell(12).toString().isEmpty() && row.getCell(13).toString().isEmpty() && row.getCell(14).toString().isEmpty())) { //경도
    		lon = Double.parseDouble(row.getCell(12).toString()) + Double.parseDouble(row.getCell(13).toString())/60 + Double.parseDouble(row.getCell(14).toString())/3600;
    		vo.setLon(String.valueOf(lon));
    	}

    	cell = row.getCell(15); // 소속
    	if(cell!=null){vo.setSvydept(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(16); // 직급
    	if(cell!=null){vo.setSyvofcps(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(17); // 조사자
    	if(cell!=null){vo.setSvyUser(ExcelUtils.getCellValue(cell).replace("\n", ""));}

    	cell = row.getCell(18); // 조사일자
    	String test1 = ExcelUtils.getCellValue(cell).replaceAll("[\n-.]", "");
    	if(cell!=null){vo.setSvydt(ExcelUtils.getCellValue(cell).replaceAll("[\n-.]", ""));}

    	cell = row.getCell(19); // 연락처
    	if(cell!=null){vo.setSvymbtl(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//		VO 변경으로 인한 컬럼 재매칭 필요 (2022.12.14)
//    	if(row.getCell(1).toString().equals("토석류")) {
//	    	cell = row.getCell(20); // 1회 토석류 (토석류)
//	    	if(cell!=null){vo.setFrstsoil(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	
//	    	cell = row.getCell(21); // 정지조건 (토석류)
//	    	if(cell!=null){vo.setStopcndtn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	
//	    	cell = row.getCell(22); // 가중치 (토석류)
//	    	if(cell!=null){vo.setWghtval(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	
//	    	cell = row.getCell(23); // 토석류량 (토석류)
//	    	if(cell!=null){vo.setSoilqy(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	    	
//	    	cell = row.getCell(24); // 안정해석점수
//	    	if(cell!=null){vo.setStbintrprtscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(25); // 이격거리
//	    	if(cell!=null){vo.setGapdstnc(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(26); // 유역(ha)
//        	if(cell!=null){vo.setDgrha(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(27); // 유역m2
//        	cell.setCellFormula("AA"+rowNum+"*10000");
//        	int drgsm = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setDgrsm(String.valueOf(drgsm));}
//
//        	cell = row.getCell(28); // 취약지역 면적
//        	cell.setCellFormula("AD"+rowNum+"*0.0001");
//        	double wkha = fev.evaluateInCell(cell).getNumericCellValue();
//        	if(cell!=null){vo.setWkha(String.format("%.2f", wkha));}
//
//        	cell = row.getCell(29); // 취약m2
//        	cell.setCellFormula("SUM(EJ"+rowNum+",EO"+rowNum+",ET"+rowNum+",EY"+rowNum+",FD"+rowNum+")");
//        	double wksm = fev.evaluateInCell(cell).getNumericCellValue();
//        	if(cell!=null){vo.setWksm(String.format("%.2f", wksm));}
//
//	    	cell = row.getCell(30); // 보호시설
//	    	if(cell!=null){vo.setSaftyfclt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(31); // 보호 개수
//	    	if(cell!=null){vo.setSaftycnt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(32); // 인가
//	    	if(cell!=null){vo.setCnfm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(33); // 인가 개수
//	    	if(cell!=null){vo.setCnfmcnt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(34); // 상부주요시설
//	    	if(cell!=null){vo.setUprmain(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(35); // 상부인가
//	    	if(cell!=null){vo.setUprcnfm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(36); // 하부주요시설
//	    	if(cell!=null){vo.setLwrmain(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(37); // 하부인가
//	    	if(cell!=null){vo.setLwrcnfm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(38); // 황폐발생 (토석류)
//	    	if(cell!=null){vo.setDevoccause(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(39); // 변곡점길이 (토석류)
//	    	cell.setCellFormula("CE"+rowNum);
//	    	int inflcpntlength = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setInflcpntlength(String.valueOf(inflcpntlength));}
//
//	    	cell = row.getCell(40); // 변곡점고도 (토석류)
//	    	cell.setCellFormula("CF"+rowNum);
//	    	int inflcpntheight = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setInflcpntheight(String.valueOf(inflcpntheight));}
//
//	    	cell = row.getCell(41); // 계류길이 (토석류)
//	    	if(cell!=null){vo.setTltrntlt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(42); // 평균경사도
//	    	if(cell!=null){vo.setSlopeavg(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(43); // 최저경사
//	    	if(cell!=null){vo.setSlopelwt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(44); // 최고경사
//	    	if(cell!=null){vo.setSlopehgt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(45); // 임상
//	    	if(cell!=null){vo.setFrstfr(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(46); // 임분밀도
//	    	if(cell!=null){vo.setDnsty(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(47); // 임분경급
//	    	if(cell!=null){vo.setDmclschk(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(48); // 종점특이 (토석류)
//	    	if(cell!=null){vo.setEnpopion(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(49); // 시점특이 (토석류)
//	    	if(cell!=null){vo.setStpopion(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(50); // 월류상태
//	    	if(cell!=null){vo.setOvflwsttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(51); // 계류상황
//	    	if(cell!=null){vo.setTltrntltsttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(52); // 곡류상태
//	    	if(cell!=null){vo.setMndrsttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	    	
//	    	cell = row.getCell(53); // 계류수 (토석류)
//	    	if(cell!=null){vo.setMntstrm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(54); // 유목여부 (토석류)
//	    	if(cell!=null){vo.setNmdicat(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(55); // 유목길이 (토석류)
//	    	if(cell!=null){vo.setNmdiclng(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(56); // 퇴적지 (토석류)
//	    	if(cell!=null){vo.setDestat(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(57); // 좌안붕괴지 (토석류)
//	    	if(cell!=null){vo.setLftcrkarea(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(58); // 우안붕괴지 (토석류)
//	    	if(cell!=null){vo.setRghtcrkarea(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	    	
//	    	if(!(row.getCell(59).toString().isEmpty() && row.getCell(60).toString().isEmpty() && row.getCell(61).toString().isEmpty())) { // 위도1 (토석류)
//	    		lat = Double.parseDouble(row.getCell(9).toString()) + Double.parseDouble(row.getCell(10).toString())/60 + Double.parseDouble(row.getCell(11).toString())/3600;
//	    		vo.setDtblat1(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(62).toString().isEmpty() && row.getCell(63).toString().isEmpty() && row.getCell(64).toString().isEmpty())) { // 경도1 (토석류)
//	    		lon = Double.parseDouble(row.getCell(12).toString()) + Double.parseDouble(row.getCell(13).toString())/60 + Double.parseDouble(row.getCell(14).toString())/3600;
//	    		vo.setDtblon1(String.valueOf(lon));
//	    	}
//
//	    	cell = row.getCell(65); // 위치1 (토석류)
//	    	if(cell!=null){vo.setDtbloc1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(66); // 고도1 (토석류)
//	    	if(cell!=null){vo.setDtbheight1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(67); // 토심1 (토석류)
//	    	if(cell!=null){vo.setDtbsoildept1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(68); // 폭1 (토석류)
//	    	if(cell!=null){vo.setDtbrange1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(69); // 경사1 (토석류)
//	    	if(cell!=null){vo.setDtbslope1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(70); // 총계1 (토석류)
//	    	cell.setCellFormula("SUM(BT"+rowNum+":BX"+rowNum+")");
//	    	int dtbcnt1 = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setDtbcnt1(String.valueOf(dtbcnt1));}
//
//	    	cell = row.getCell(71); // 암반1 (토석류)
//	    	if(cell!=null){vo.setDtbbdrck1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(72); // 전석1 (토석류)
//	    	if(cell!=null){vo.setDtbbldstn1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(73); // 자갈1 (토석류)
//	    	if(cell!=null){vo.setDtbgrvl1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(74); // 모래1 (토석류)
//	    	if(cell!=null){vo.setDtbsand1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(75); // 기타1 (토석류)
//	    	if(cell!=null){vo.setDtbetc1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(76).toString().isEmpty() && row.getCell(77).toString().isEmpty() && row.getCell(78).toString().isEmpty())) { // 위도2 (토석류)
//	    		lat = Double.parseDouble(row.getCell(76).toString()) + Double.parseDouble(row.getCell(77).toString())/60 + Double.parseDouble(row.getCell(78).toString())/3600;
//	    		vo.setDtblat2(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(79).toString().isEmpty() && row.getCell(80).toString().isEmpty() && row.getCell(81).toString().isEmpty())) { // 경도2 (토석류)
//	    		lon = Double.parseDouble(row.getCell(79).toString()) + Double.parseDouble(row.getCell(80).toString())/60 + Double.parseDouble(row.getCell(81).toString())/3600;
//	    		vo.setDtblon2(String.valueOf(lon));
//	    	}
//	    	
//	    	cell = row.getCell(82); // 위치2 (토석류)
//	    	if(cell!=null){vo.setDtbloc2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(83); // 고도2 (토석류)
//	    	if(cell!=null){vo.setDtbheight2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(84); // 토심2 (토석류)
//	    	if(cell!=null){vo.setDtbsoildept2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(85); // 폭2 (토석류)
//	    	if(cell!=null){vo.setDtbrange2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(86); // 경사2 (토석류)
//	    	if(cell!=null){vo.setDtbslope2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(87); // 총계2 (토석류)
//	    	cell.setCellFormula("SUM(CK"+rowNum+":CO"+rowNum+")");
//	    	int dtbcnt2 = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setDtbcnt2(String.valueOf(dtbcnt2));}
//
//	    	cell = row.getCell(88); // 암반2 (토석류)
//	    	if(cell!=null){vo.setDtbbdrck2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(89); // 전석2 (토석류)
//	    	if(cell!=null){vo.setDtbbldstn2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(90); // 자갈2 (토석류)
//	    	if(cell!=null){vo.setDtbgrvl2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(91); // 모래2 (토석류)
//	    	if(cell!=null){vo.setDtbsand2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(92); // 기타2 (토석류)
//	    	if(cell!=null){vo.setDtbetc2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(93).toString().isEmpty() && row.getCell(94).toString().isEmpty() && row.getCell(95).toString().isEmpty())) { // 위도3 (토석류)
//	    		lat = Double.parseDouble(row.getCell(93).toString()) + Double.parseDouble(row.getCell(94).toString())/60 + Double.parseDouble(row.getCell(95).toString())/3600;
//	    		vo.setDtblat3(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(96).toString().isEmpty() && row.getCell(97).toString().isEmpty() && row.getCell(98).toString().isEmpty())) { // 경도3 (토석류)
//	    		lon = Double.parseDouble(row.getCell(96).toString()) + Double.parseDouble(row.getCell(97).toString())/60 + Double.parseDouble(row.getCell(98).toString())/3600;
//	    		vo.setDtblon3(String.valueOf(lon));
//	    	}
//	    	
//	    	cell = row.getCell(99); // 위치3 (토석류)
//	    	if(cell!=null){vo.setDtbloc3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(100); // 고도3 (토석류)
//	    	if(cell!=null){vo.setDtbheight3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(101); // 토심3 (토석류)
//	    	if(cell!=null){vo.setDtbsoildept3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(102); // 폭3 (토석류)
//	    	if(cell!=null){vo.setDtbrange3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(103); // 경사3 (토석류)
//	    	if(cell!=null){vo.setDtbslope3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(104); // 총계3 (토석류)
//	    	cell.setCellFormula("SUM(DB"+rowNum+":DF"+rowNum+")");
//	    	int dtbcnt3 = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setDtbcnt3(String.valueOf(dtbcnt3));}
//
//	    	cell = row.getCell(105); // 암반3 (토석류)
//	    	if(cell!=null){vo.setDtbbdrck3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(106); // 전석3 (토석류)
//	    	if(cell!=null){vo.setDtbbldstn3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(107); // 자갈3 (토석류)
//	    	if(cell!=null){vo.setDtbgrvl3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(108); // 모래3 (토석류)
//	    	if(cell!=null){vo.setDtbsand3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(109); // 기타3 (토석류)
//	    	if(cell!=null){vo.setDtbetc3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(110); // 현장조사점수
//        	cell.setCellFormula("DU"+rowNum);
//        	int exmnscore = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setExmnscore(String.valueOf(exmnscore));}
//
//        	cell = row.getCell(111); // 안정해석점수
//        	cell.setCellFormula("Y"+rowNum);
//        	int stbinppnt = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setStbinppnt(String.valueOf(stbinppnt));}
//
//        	cell = row.getCell(112); // 점수계
//        	cell.setCellFormula("SUM(DG"+rowNum+":DH"+rowNum+")");
//        	int scoresm = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setScoresm(String.valueOf(scoresm));}
//
//	    	cell = row.getCell(113); // 판정등급
//	    	if(cell!=null){vo.setJdggrd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(114); // 판정결과
//	    	cell.setCellFormula("IF(DJ"+rowNum+"=\"에이\",\"위험\",IF(DJ"+rowNum+"=\"비\",\"잠재적 위험\",IF(DJ"+rowNum+"=\"씨\",\"위험성 낮음\")))");
//	    	if(cell!=null){vo.setJdgrslt(fev.evaluateInCell(cell).toString());}
//
//	    	cell = row.getCell(115); // 보정
//	    	if(cell!=null){vo.setRevisn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(116); // 등급보정 사유
//	    	if(cell!=null){vo.setRevisnrsn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(117); // 사업 가능여부
//	    	if(cell!=null){vo.setTaskpsblat(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(118); // 대책방안
//	    	if(cell!=null){vo.setCntrpln(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(119); // 필요공종
//	    	if(cell!=null){vo.setAltrv(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(120); // 대피장소
//	    	if(cell!=null){vo.setShunt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(121); // 관리방안
//	    	if(cell!=null){vo.setMngpln(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(122); // 종합의견1
//	    	if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(123); // 종합의견2
//	    	if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(124); // 점수계
//	    	cell.setCellFormula("SUM(DV"+rowNum+",DW"+rowNum+",DX"+rowNum+",DY"+rowNum+",DZ"+rowNum+",EA"+rowNum+",EB"+rowNum+",EC"+rowNum+",ED"+rowNum+",EE"+rowNum+")");
//	    	int sm = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setSm(String.valueOf(sm));}
//	    	
//	    	cell = row.getCell(125); // 피해이력
//	    	if(cell!=null){vo.setDmgehistscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(126); // 보호시설
//	    	if(cell!=null){vo.setSaftyfcltscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(127); // 유역면적 (토석류)
//	    	if(cell!=null){vo.setDgrareascore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(128); // 평균경사 (토석류)
//	    	if(cell!=null){vo.setSlpavgscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(129); // 토심
//	    	if(cell!=null){vo.setSoildeptscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(130); // 붕괴/ 침식/ 전석/ 토석류 흔적 위험요인 (토석류)
//	    	if(cell!=null){vo.setRskfactorscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(131); // 산사태위험도
//	    	if(cell!=null){vo.setLndsldrskscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(132); // 산림현황
//	    	if(cell!=null){vo.setMststscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(133); // 뿌리특성
//	    	if(cell!=null){vo.setRootscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(134); // 기타(선택형) (토석류)
//	    	if(cell!=null){vo.setEtcscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(135); // 면적계
//	    	cell.setCellFormula("SUM(EI"+rowNum+",EN"+rowNum+",ES"+rowNum+",EX"+rowNum+",FC"+rowNum+")");
//	    	int smarea = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//	    	if(cell!=null){vo.setSmarea(String.valueOf(smarea));}
//
//	    	cell = row.getCell(136); // 지번1
//	    	if(cell!=null){vo.setJibun1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(137); // 지목1
//	    	if(cell!=null){vo.setJimok1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(138); // 면적1
//	    	if(cell!=null){vo.setArea1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(139); // 편입면적1
//	    	if(cell!=null){vo.setIncldarea1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(140); // 소유1
//	    	if(cell!=null){vo.setOwn1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(141); // 지번2
//	    	if(cell!=null){vo.setJibun2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(142); // 지목2
//	    	if(cell!=null){vo.setJimok2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(143); // 면적2
//	    	if(cell!=null){vo.setArea2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(144); // 편입면적2
//	    	if(cell!=null){vo.setIncldarea2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(145); // 소유2
//	    	if(cell!=null){vo.setOwn2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(146); // 지번3
//	    	if(cell!=null){vo.setJibun3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(147); // 지목3
//	    	if(cell!=null){vo.setJimok3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(148); // 면적3
//	    	if(cell!=null){vo.setArea3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(149); // 편입면적3
//	    	if(cell!=null){vo.setIncldarea3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(150); // 소유3
//	    	if(cell!=null){vo.setOwn3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(151); // 지번4
//	    	if(cell!=null){vo.setJibun4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(152); // 지목4
//	    	if(cell!=null){vo.setJimok4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(153); // 면적4
//	    	if(cell!=null){vo.setArea4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(154); // 편입면적4
//	    	if(cell!=null){vo.setIncldarea4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(155); // 소유4
//	    	if(cell!=null){vo.setOwn4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(156); // 지번5
//	    	if(cell!=null){vo.setJibun5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(157); // 지목5
//	    	if(cell!=null){vo.setJimok5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(158); // 면적5
//	    	if(cell!=null){vo.setArea5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(159); // 편입면적5
//	    	if(cell!=null){vo.setIncldarea5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(160); // 소유5
//	    	if(cell!=null){vo.setOwn5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(161); // 연번5
//	    	if(cell!=null){vo.setYn5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(162).toString().isEmpty() && row.getCell(163).toString().isEmpty() && row.getCell(164).toString().isEmpty())) { // 위도5 (토석류)
//	    		lat = Double.parseDouble(row.getCell(162).toString()) + Double.parseDouble(row.getCell(163).toString())/60 + Double.parseDouble(row.getCell(164).toString())/3600;
//	    		vo.setLat5(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(165).toString().isEmpty() && row.getCell(166).toString().isEmpty() && row.getCell(167).toString().isEmpty())) { // 경도5 (토석류)
//	    		lon = Double.parseDouble(row.getCell(165).toString()) + Double.parseDouble(row.getCell(166).toString())/60 + Double.parseDouble(row.getCell(167).toString())/3600;
//	    		vo.setLon5(String.valueOf(lon));
//	    	}
//	    	
//	    	cell = row.getCell(168); // 특이5
//	    	if(cell!=null){vo.setPartclr5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(169); // 연번6 (토석류)
//	    	if(cell!=null){vo.setYn6(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(170).toString().isEmpty() && row.getCell(171).toString().isEmpty() && row.getCell(172).toString().isEmpty())) { // 위도6 (토석류)
//	    		lat = Double.parseDouble(row.getCell(170).toString()) + Double.parseDouble(row.getCell(171).toString())/60 + Double.parseDouble(row.getCell(172).toString())/3600;
//	    		vo.setLat6(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(173).toString().isEmpty() && row.getCell(174).toString().isEmpty() && row.getCell(175).toString().isEmpty())) { // 경도6 (토석류)
//	    		lon = Double.parseDouble(row.getCell(173).toString()) + Double.parseDouble(row.getCell(174).toString())/60 + Double.parseDouble(row.getCell(175).toString())/3600;
//	    		vo.setLon6(String.valueOf(lon));
//	    	}
//
//	    	cell = row.getCell(176); // 특이6 (토석류)
//	    	if(cell!=null){vo.setPartclr6(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(177); // 연번7 (토석류)
//	    	if(cell!=null){vo.setYn7(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(178).toString().isEmpty() && row.getCell(179).toString().isEmpty() && row.getCell(180).toString().isEmpty())) { // 위도7 (토석류)
//	    		lat = Double.parseDouble(row.getCell(178).toString()) + Double.parseDouble(row.getCell(179).toString())/60 + Double.parseDouble(row.getCell(180).toString())/3600;
//	    		vo.setLat7(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(181).toString().isEmpty() && row.getCell(182).toString().isEmpty() && row.getCell(183).toString().isEmpty())) { // 경도7 (토석류)
//	    		lon = Double.parseDouble(row.getCell(181).toString()) + Double.parseDouble(row.getCell(182).toString())/60 + Double.parseDouble(row.getCell(183).toString())/3600;
//	    		vo.setLon7(String.valueOf(lon));
//	    	}
//
//	    	cell = row.getCell(184); // 특이7 (토석류)
//	    	if(cell!=null){vo.setPartclr7(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(185); // 연번8 (토석류)
//	    	if(cell!=null){vo.setYn8(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(186).toString().isEmpty() && row.getCell(187).toString().isEmpty() && row.getCell(188).toString().isEmpty())) { // 위도8 (토석류)
//	    		lat = Double.parseDouble(row.getCell(186).toString()) + Double.parseDouble(row.getCell(187).toString())/60 + Double.parseDouble(row.getCell(188).toString())/3600;
//	    		vo.setLat8(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(189).toString().isEmpty() && row.getCell(190).toString().isEmpty() && row.getCell(191).toString().isEmpty())) { // 경도8 (토석류)
//	    		lon = Double.parseDouble(row.getCell(189).toString()) + Double.parseDouble(row.getCell(190).toString())/60 + Double.parseDouble(row.getCell(191).toString())/3600;
//	    		vo.setLon8(String.valueOf(lon));
//	    	}
//
//	    	cell = row.getCell(192); // 특이8 (토석류)
//	    	if(cell!=null){vo.setPartclr8(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(193); // 연번9 (토석류)
//	    	if(cell!=null){vo.setYn9(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	if(!(row.getCell(194).toString().isEmpty() && row.getCell(195).toString().isEmpty() && row.getCell(196).toString().isEmpty())) { // 위도9 (토석류)
//	    		lat = Double.parseDouble(row.getCell(194).toString()) + Double.parseDouble(row.getCell(195).toString())/60 + Double.parseDouble(row.getCell(196).toString())/3600;
//	    		vo.setLat9(String.valueOf(lat));
//	    	}
//	    		
//	    	if(!(row.getCell(197).toString().isEmpty() && row.getCell(198).toString().isEmpty() && row.getCell(199).toString().isEmpty())) { // 경도9 (토석류)
//	    		lon = Double.parseDouble(row.getCell(197).toString()) + Double.parseDouble(row.getCell(198).toString())/60 + Double.parseDouble(row.getCell(199).toString())/3600;
//	    		vo.setLon9(String.valueOf(lon));
//	    	}
//
//	    	cell = row.getCell(200); // 특이9 (토석류)
//	    	if(cell!=null){vo.setPartclr9(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(201); // 대상지개요
//	    	if(cell!=null){vo.setSldsumry(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(202); // 현장결과1 / 유역현황
//	    	if(cell!=null){vo.setSptrslt1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(203); // 현장결과2 / 위험징후 및 분포현환
//	    	if(cell!=null){vo.setSptrslt2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(204); // 간략한 종합의견 및 지정사유
//	    	if(cell!=null){vo.setSmplrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(205); // 지정사유 또는 안정해석 결과 입력
//	    	if(cell!=null){vo.setApntrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(206); // 구역설정
//	    	if(cell!=null){vo.setArearslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(207); // 특이사항 및 진입여건
//	    	if(cell!=null){vo.setPartclrrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(208); // 재해예방사업종 선정사유
//	    	if(cell!=null){vo.setDsstrprvrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//	    	cell = row.getCell(209); // 기타종합의견 주민협의 및 대체대안
//	    	if(cell!=null){vo.setEtcrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//	    	
//	    	cell = row.getCell(210); // 공유방ID
//	    	if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//    	}
//    	if(row.getCell(1).toString().equals("산사태")) {
//    		cell = row.getCell(20); // 건기시 (산사태)
//        	if(cell!=null){vo.setDrval(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(21); // 건기방향 (산사태)
//        	cell.setCellFormula("IF(W"+rowNum+"=\"OK\",\"<\",IF(W"+rowNum+"=\"NG\",\">\"))");
//        	if(cell!=null){vo.setDrdir(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(22); // 건기판정 (산사태)
//        	cell.setCellFormula("IF(U"+rowNum+"<1.5,\"NG\",IF(U"+rowNum+">=1.5,\"OK\",IF(U"+rowNum+"=\"-\",\"\")))");
//        	if(cell!=null){vo.setDrjdg(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(23); // 건기점수 (산사태)
//        	cell.setCellFormula("IF(U"+rowNum+">=1.6,0,IF(U"+rowNum+"<1.5,30,\"15\"))");
//        	if(cell!=null){vo.setDrscore(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(24); // 우기시 (산사태)
//        	if(cell!=null){vo.setWtval(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(25); // 우기방향 (산사태)
//        	cell.setCellFormula("IF(AA"+rowNum+"=\"OK\",\"<\",IF(AA"+rowNum+"=\"NG\",\">\"))");
//        	if(cell!=null){vo.setWtdir(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(26); // 우기판정 (산사태)
//        	cell.setCellFormula("IF(Y"+rowNum+"<1.2,\"NG\",IF(Y"+rowNum+">=1.2,\"OK\",IF(Y"+rowNum+"=\"-\",\"\")))");
//        	if(cell!=null){vo.setWtjdg(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(27); // 우기점수 (산사태)
//        	cell.setCellFormula("IF(Y"+rowNum+">=1.3,0,IF(Y"+rowNum+"<1.2,30,\"15\"))");
//        	if(cell!=null){vo.setWtscore(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(28); // 습윤단위중량 (산사태)
//        	if(cell!=null){vo.setHmdtwwight(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(29); // 점착력 (산사태)
//        	if(cell!=null){vo.setAdhnsv(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(30); // 내부마찰각 (산사태)
//        	if(cell!=null){vo.setInnrfrctn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(31); // 안정해석점수
//        	cell.setCellFormula("MAX(X"+rowNum+",AB"+rowNum+")");
//        	int stbintrprtscore = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setStbintrprtscore(String.valueOf(stbintrprtscore ));}
//
//        	cell = row.getCell(32); // 이격거리
//        	if(cell!=null){vo.setGapdstnc(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(33); // 유역(ha)
//        	if(cell!=null){vo.setDgrha(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(34); // 유역m2
//        	cell.setCellFormula("AH"+rowNum+"*10000");
//        	int drgsm = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setDgrsm(String.valueOf(drgsm));}
//
//        	cell = row.getCell(35); // 취약지역 면적
//        	cell.setCellFormula("AK"+rowNum+"*0.0001");
//        	double wkha = fev.evaluateInCell(cell).getNumericCellValue();
//        	if(cell!=null){vo.setWkha(String.format("%.2f", wkha));}
//
//        	cell = row.getCell(36); // 취약m2
//        	cell.setCellFormula("SUM(DE"+rowNum+",DJ"+rowNum+",DO"+rowNum+",DT"+rowNum+",DY"+rowNum+")");
//        	double wksm = fev.evaluateInCell(cell).getNumericCellValue();
//        	if(cell!=null){vo.setWksm(String.format("%.2f", wksm));}
//
//        	cell = row.getCell(37); // 보호시설
//        	if(cell!=null){vo.setSaftyfclt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(38); // 보호 개수
//        	if(cell!=null){vo.setSaftycnt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(39); // 인가
//        	if(cell!=null){vo.setCnfm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(40); // 인가 개수
//        	if(cell!=null){vo.setCnfmcnt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(41); // 상부주요시설
//        	if(cell!=null){vo.setUprmain(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(42); // 상부인가
//        	if(cell!=null){vo.setUprcnfm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(43); // 하부주요시설
//        	if(cell!=null){vo.setLwrmain(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(44); // 하부인가
//        	if(cell!=null){vo.setLwrcnfm(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(45); // 사면상태 (산사태)
//        	if(cell!=null){vo.setSformsttus(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(46); // 모암 (산사태)
//        	if(cell!=null){vo.setPrntrck(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(47); // 토성 (산사태)
//        	if(cell!=null){vo.setSoilclass(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(48); // 경사길이 (산사태)
//        	if(cell!=null){vo.setSlen(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(49); // 평균경사도
//        	if(cell!=null){vo.setSlopeavg(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(50); // 최저경사
//        	if(cell!=null){vo.setSlopelwt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(51); // 최고경사
//        	if(cell!=null){vo.setSlopehgt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(52); // 경사범위 (산사태)
//        	cell.setCellFormula("IF(AX"+rowNum+"<15,\"완경사지\",IF(AX"+rowNum+"<20,\"경사지\",IF(AX"+rowNum+"<25,\"급경사지\",IF(AX"+rowNum+"<30,\"험준지\",IF(AX"+rowNum+"<90,\"절험지\",IF(AX"+rowNum+"=0,\" \",IF(AX"+rowNum+"=\"\",\" \")))))))");
//        	if(cell!=null){vo.setSloperng(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(53); // 경사위치 (산사태)
//        	cell.setCellFormula("IF(BC"+rowNum+"<4,\"산록\",IF(BC"+rowNum+"<8,\"산복\",\"산정\"))");
//        	if(cell!=null){vo.setSlopeloc(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(54); // 1~10부 (산사태)
//        	if(cell!=null){vo.setArealcridge(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(55); // 최고지점 (산사태)
//        	if(cell!=null){vo.setHgtpnt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(56); // 최저지점 (산사태)
//        	if(cell!=null){vo.setLwtpnt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(57); // 사면형 (산사태)
//        	if(cell!=null){vo.setSform(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(58); // 토심 (산사태)
//        	if(cell!=null){vo.setSoildept(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(59); // 균열 (산사태)
//        	if(cell!=null){vo.setSoilcrk(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(60); // 함몰 (산사태)
//        	if(cell!=null){vo.setRtctn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(61); // 융기 (산사태)
//        	if(cell!=null){vo.setUplft(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(62); // 말단부압출 (산사태)
//        	if(cell!=null){vo.setEnppres(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(63); // 확대예상 (산사태)
//        	if(cell!=null){vo.setExpnsnprd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(64); // 임상
//        	if(cell!=null){vo.setFrstfr(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(65); // 임분밀도
//        	if(cell!=null){vo.setDnsty(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(66); // 임분경급
//        	if(cell!=null){vo.setDmclschk(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(67); // 용수상시여부 (산사태)
//        	if(cell!=null){vo.setWtrrgrat(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(68); // 강우시용수 (산사태)
//        	if(cell!=null){vo.setRainwtrat(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(69); // 사면이축축함 (산사태)
//        	if(cell!=null){vo.setDrsform(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(70); // 사면이건조함 (산사태)
//        	if(cell!=null){vo.setWtsfrom(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(71); // 현장조사점수
//        	cell.setCellFormula("CH"+rowNum);
//        	int exmnscore = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setExmnscore(String.valueOf(exmnscore));}
//
//        	cell = row.getCell(72); // 안정해석점수
//        	cell.setCellFormula("AF"+rowNum);
//        	int stbinppnt = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setStbinppnt(String.valueOf(stbinppnt));}
//
//        	cell = row.getCell(73); // 점수계
//        	cell.setCellFormula("SUM(BT"+rowNum+":BU"+rowNum+")");
//        	int scoresm = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setScoresm(String.valueOf(scoresm));}
//
//        	cell = row.getCell(74); // 판정등급
//        	if(cell!=null){vo.setJdggrd(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(75); // 판정결과
//        	cell.setCellFormula("IF(BW"+rowNum+"=\"에이\",\"위험\",IF(BW"+rowNum+"=\"비\",\"잠재적 위험\",IF(BW"+rowNum+"=\"씨\",\"위험성 낮음\")))");
//        	if(cell!=null){vo.setJdgrslt(fev.evaluateInCell(cell).toString());}
//
//        	cell = row.getCell(76); // 보정
//        	if(cell!=null){vo.setRevisn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(77); // 등급보정 사유
//        	if(cell!=null){vo.setRevisnrsn(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(78); // 사업 가능여부
//        	if(cell!=null){vo.setTaskpsblat(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(79); // 대책방안
//        	if(cell!=null){vo.setCntrpln(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(80); // 필요공종
//        	if(cell!=null){vo.setAltrv(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(81); // 대피장소
//        	if(cell!=null){vo.setShunt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(82); // 관리방안
//        	if(cell!=null){vo.setMngpln(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(83); // 종합의견1
//        	if(cell!=null){vo.setOpinion1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(84); // 종합의견2
//        	if(cell!=null){vo.setOpinion2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(85); // 점수계
//        	cell.setCellFormula("SUM(CI"+rowNum+",CJ"+rowNum+",CK"+rowNum+",CL"+rowNum+",CM"+rowNum+",CN"+rowNum+",CO"+rowNum+",CP"+rowNum+",CQ"+rowNum+",CR"+rowNum+",CS"+rowNum+",CT"+rowNum+",CU"+rowNum+",CV"+rowNum+",CW"+rowNum+",CX"+rowNum+",CY"+rowNum+",CZ"+rowNum+")");
//        	int sm = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setSm(String.valueOf(sm));}
//
//        	cell = row.getCell(86); // 피해이력
//        	if(cell!=null){vo.setDmgehistscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(87); // 보호시설
//        	if(cell!=null){vo.setSaftyfcltscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(88); // 경사도 (산사태)
//        	if(cell!=null){vo.setSoilslopescore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(89); // 사면높이 (산사태)
//        	if(cell!=null){vo.setSoilsformscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(90); // 토심
//        	if(cell!=null){vo.setSoildeptscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(91); // 종단형상 (산사태)
//        	if(cell!=null){vo.setSoilepshpscroe(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(92); // 경사도 (산사태)
//        	if(cell!=null){vo.setBdrckscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(93); // 사면높이 (산사태)
//        	if(cell!=null){vo.setBdrcksfromscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(94); // 암석종류 (산사태)
//        	if(cell!=null){vo.setBdrckkndscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(95); // 군열상황 (산사태)
//        	if(cell!=null){vo.setBdrckcrkscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(96); // 산사태위험도
//        	if(cell!=null){vo.setLndsldrskscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(97); // 용수상황 (산사태)
//        	if(cell!=null){vo.setWtrsttusscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(98); // 붕괴지 (산사태)
//        	if(cell!=null){vo.setCrkareascore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(99); // 뿌리특성
//        	if(cell!=null){vo.setRootscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(100); // 산림현황
//        	if(cell!=null){vo.setMststscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(101); // 붕괴 (산사태)
//        	if(cell!=null){vo.setCrkscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(102); // 불연속면방향 (산사태)
//        	if(cell!=null){vo.setDisctnuplnscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(103); // 풍화상태 (산사태)
//        	if(cell!=null){vo.setWteffdgrscore(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(104); // 면적계
//        	cell.setCellFormula("SUM(DD"+rowNum+",DI"+rowNum+",DN"+rowNum+",DS"+rowNum+",DX"+rowNum+")");
//        	int smarea = (int) Math.round(fev.evaluateInCell(cell).getNumericCellValue());
//        	if(cell!=null){vo.setSmarea(String.valueOf(smarea));}
//
//        	cell = row.getCell(105); // 지번1
//        	if(cell!=null){vo.setJibun1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(106); // 지목1
//        	if(cell!=null){vo.setJimok1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(107); // 면적1
//        	if(cell!=null){vo.setArea1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(108); // 편입면적1
//        	if(cell!=null){vo.setIncldarea1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(109); // 소유1
//        	if(cell!=null){vo.setOwn1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(110); // 지번2
//        	if(cell!=null){vo.setJibun2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(111); // 지목2
//        	if(cell!=null){vo.setJimok2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(112); // 면적2
//        	if(cell!=null){vo.setArea2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(113); // 편입면적2
//        	if(cell!=null){vo.setIncldarea2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(114); // 소유2
//        	if(cell!=null){vo.setOwn2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(115); // 지번3
//        	if(cell!=null){vo.setJibun3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(116); // 지목3
//        	if(cell!=null){vo.setJimok3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(117); // 면적3
//        	if(cell!=null){vo.setArea3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(118); // 편입면적3
//        	if(cell!=null){vo.setIncldarea3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(119); // 소유3
//        	if(cell!=null){vo.setOwn3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(120); // 지번4
//        	if(cell!=null){vo.setJibun4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(121); // 지목4
//        	if(cell!=null){vo.setJimok4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(122); // 면적4
//        	if(cell!=null){vo.setArea4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(123); // 편입면적4
//        	if(cell!=null){vo.setIncldarea4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(124); // 소유4
//        	if(cell!=null){vo.setOwn4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(125); // 지번5
//        	if(cell!=null){vo.setJibun5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(126); // 지목5
//        	if(cell!=null){vo.setJimok5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(127); // 면적5
//        	if(cell!=null){vo.setArea5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(128); // 편입면적5
//        	if(cell!=null){vo.setIncldarea5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(129); // 소유5
//        	if(cell!=null){vo.setOwn5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(130); // 연번1 (산사태)
//        	if(cell!=null){vo.setYn1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	if(!(row.getCell(131).toString().isEmpty() && row.getCell(132).toString().isEmpty() && row.getCell(133).toString().isEmpty())) { // 위도1 (산사태)
//	    		lat = Double.parseDouble(row.getCell(131).toString()) + Double.parseDouble(row.getCell(132).toString())/60 + Double.parseDouble(row.getCell(133).toString())/3600;
//	    		vo.setLat1(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(134).toString().isEmpty() && row.getCell(135).toString().isEmpty() && row.getCell(136).toString().isEmpty())) { // 경도1 (산사태)
//	    		lon = Double.parseDouble(row.getCell(134).toString()) + Double.parseDouble(row.getCell(135).toString())/60 + Double.parseDouble(row.getCell(136).toString())/3600;
//	    		vo.setLon1(String.valueOf(lon));
//	    	}
//
//        	cell = row.getCell(137); // 특이1 (산사태)
//        	if(cell!=null){vo.setPartclr1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(138); // 연번2 (산사태)
//        	if(cell!=null){vo.setYn2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	if(!(row.getCell(139).toString().isEmpty() && row.getCell(140).toString().isEmpty() && row.getCell(141).toString().isEmpty())) { // 위도2 (산사태)
//	    		lat = Double.parseDouble(row.getCell(139).toString()) + Double.parseDouble(row.getCell(140).toString())/60 + Double.parseDouble(row.getCell(141).toString())/3600;
//	    		vo.setLat2(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(142).toString().isEmpty() && row.getCell(143).toString().isEmpty() && row.getCell(144).toString().isEmpty())) { // 경도2 (산사태)
//	    		lon = Double.parseDouble(row.getCell(142).toString()) + Double.parseDouble(row.getCell(143).toString())/60 + Double.parseDouble(row.getCell(144).toString())/3600;
//	    		vo.setLat2(String.valueOf(lon));
//	    	}
//
//        	cell = row.getCell(145); // 특이2 (산사태)
//        	if(cell!=null){vo.setPartclr2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(146); // 연번3 (산사태)
//        	if(cell!=null){vo.setYn3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	if(!(row.getCell(147).toString().isEmpty() && row.getCell(148).toString().isEmpty() && row.getCell(149).toString().isEmpty())) { // 위도3 (산사태)
//	    		lat = Double.parseDouble(row.getCell(147).toString()) + Double.parseDouble(row.getCell(148).toString())/60 + Double.parseDouble(row.getCell(149).toString())/3600;
//	    		vo.setLat3(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(150).toString().isEmpty() && row.getCell(151).toString().isEmpty() && row.getCell(152).toString().isEmpty())) { // 경도3 (산사태)
//	    		lon = Double.parseDouble(row.getCell(150).toString()) + Double.parseDouble(row.getCell(151).toString())/60 + Double.parseDouble(row.getCell(152).toString())/3600;
//	    		vo.setLon3(String.valueOf(lon));
//	    	}
//
//        	cell = row.getCell(153); // 특이3 (산사태)
//        	if(cell!=null){vo.setPartclr3(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(154); // 연번4 (산사태)
//        	if(cell!=null){vo.setYn4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	if(!(row.getCell(155).toString().isEmpty() && row.getCell(156).toString().isEmpty() && row.getCell(157).toString().isEmpty())) { // 위도4 (산사태)
//	    		lat = Double.parseDouble(row.getCell(155).toString()) + Double.parseDouble(row.getCell(156).toString())/60 + Double.parseDouble(row.getCell(157).toString())/3600;
//	    		vo.setLat4(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(158).toString().isEmpty() && row.getCell(159).toString().isEmpty() && row.getCell(160).toString().isEmpty())) { // 경도4 (산사태)
//	    		lon = Double.parseDouble(row.getCell(158).toString()) + Double.parseDouble(row.getCell(159).toString())/60 + Double.parseDouble(row.getCell(160).toString())/3600;
//	    		vo.setLon4(String.valueOf(lon));
//	    	}
//	    	
//        	cell = row.getCell(161); // 특이4 (산사태)
//        	if(cell!=null){vo.setPartclr4(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(162); // 연번5
//        	if(cell!=null){vo.setYn5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	if(!(row.getCell(163).toString().isEmpty() && row.getCell(164).toString().isEmpty() && row.getCell(165).toString().isEmpty())) { // 위도5 (산사태)
//	    		lat = Double.parseDouble(row.getCell(163).toString()) + Double.parseDouble(row.getCell(164).toString())/60 + Double.parseDouble(row.getCell(165).toString())/3600;
//	    		vo.setLat5(String.valueOf(lat));
//	    	}
//
//	    	if(!(row.getCell(166).toString().isEmpty() && row.getCell(167).toString().isEmpty() && row.getCell(168).toString().isEmpty())) { // 경도5 (산사태)
//	    		lon = Double.parseDouble(row.getCell(166).toString()) + Double.parseDouble(row.getCell(167).toString())/60 + Double.parseDouble(row.getCell(168).toString())/3600;
//	    		vo.setLon5(String.valueOf(lon));
//	    	}
//
//        	cell = row.getCell(169); // 특이5
//        	if(cell!=null){vo.setPartclr5(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(170); // 대상지개요
//        	if(cell!=null){vo.setSldsumry(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(171); // 현장결과1 / 유역현황
//        	if(cell!=null){vo.setSptrslt1(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(172); // 현장결과2 / 위험징후 및 분포현환
//        	if(cell!=null){vo.setSptrslt2(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(173); // 간략한 종합의견 및 지정사유
//        	if(cell!=null){vo.setSmplrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(174); // 지정사유 또는 안정해석 결과 입력
//        	if(cell!=null){vo.setApntrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(175); // 구역설정
//        	if(cell!=null){vo.setArearslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(176); // 특이사항 및 진입여건
//        	if(cell!=null){vo.setPartclrrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(177); // 재해예방사업종 선정사유
//        	if(cell!=null){vo.setDsstrprvrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//        	cell = row.getCell(178); // 기타종합의견 주민협의 및 대체대안
//        	if(cell!=null){vo.setEtcrslt(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//        	
//        	cell = row.getCell(179); // 공유방ID
//	    	if(cell!=null){vo.setMstId(ExcelUtils.getCellValue(cell).replace("\n", ""));}
//
//    	}

		return vo;
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectWkaSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return lssWkaSvyComptDAO.selectWkaSvyComptPhotoInfo(searchVO);
	}
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return lssWkaSvyComptDAO.selectLastUpdateMinMaxDate(searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return lssWkaSvyComptDAO.updateLocReCreate(map);
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

		LssWkaSupMapUtils utils = new LssWkaSupMapUtils();
		
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
			schMap.put("SE","WKA");
			
			imgResult = new ArrayList<String>();
			
			//위치도
			vo = utils.creatWkaLocLgstrMap(schMap);
			if(vo != null) {
				source = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
				target = mstPath.concat(".ncx")+File.separator+sldId+"_위치도.".concat(vo.getFileExtsn());
				
				File locFile1 = new File(source);
				
				if(locFile1.exists()) {
					EgovFileUtil.cp(source, fieldBookDir+target);
					imgResult.add("/"+mstPath.concat(".ncx")+"/"+sldId+"_위치도.".concat(vo.getFileExtsn()));
				}else {
					LOGGER.error(vo.getStreFileNm()+".".concat(vo.getFileExtsn())+" 파일이 존재하지 않아 "+sldId+"_위치도.".concat(vo.getFileExtsn())+"복사를 실패하였습니다.(gId:"+gid+")");
				}
				
				schMap.put("lcMap","/"+mstPath.concat(".ncx")+"/"+sldId+"_위치도.".concat(vo.getFileExtsn()));
			}
			
			String statMap = lssWkaSvyComptDAO.selectSvyComptStatMap(schMap);
			//현황도
			vo = utils.creatWkaLocStatMap(schMap,statMap);
			if(vo != null) {
				source = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
				target = mstPath.concat(".ncx")+File.separator+sldId+"_현황도.".concat(vo.getFileExtsn());
				
				File locFile2 = new File(source);
				
				if(locFile2.exists()) {
					EgovFileUtil.cp(source, fieldBookDir+target);
					imgResult.add("/"+mstPath.concat(".ncx")+"/"+sldId+"_현황도.".concat(vo.getFileExtsn()));
				}else {
					LOGGER.error(vo.getStreFileNm()+".".concat(vo.getFileExtsn())+" 파일이 존재하지 않아 "+sldId+"_현황도.".concat(vo.getFileExtsn())+"복사를 실패하였습니다.(gId:"+gid+")");
				}
			}
			
			//대피체계
			vo = utils.creatWkaLocExitMap(schMap);
			if(vo != null) {
				source = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
				target = mstPath.concat(".ncx")+File.separator+sldId+"_대피체계.".concat(vo.getFileExtsn());
				
				File locFile3 = new File(source);
				
				if(locFile3.exists()) {
					EgovFileUtil.cp(source, fieldBookDir+target);
					imgResult.add("/"+mstPath.concat(".ncx")+"/"+sldId+"_대피체계.".concat(vo.getFileExtsn()));
				}else {
					LOGGER.error(vo.getStreFileNm()+".".concat(vo.getFileExtsn())+" 파일이 존재하지 않아 "+sldId+"_대피체계.".concat(vo.getFileExtsn())+"복사를 실패하였습니다.(gId:"+gid+")");
				}
			}
			
			if(imgResult.size() > 0) {
				schMap.put("imgMap",new ObjectMapper().writeValueAsString(imgResult));
				
				lssWkaSvyComptDAO.updateComptLcMap(schMap);
			}
			LOGGER.info(sldId+" 생성 종료");
		}
		utils.closeWorkspace();
	}
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaLocReCreateSvyId(LssWkaSvyComptVO svyComptVO) throws Exception{
		return lssWkaSvyComptDAO.selectWkaLocReCreateSvyId(svyComptVO);
	}
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param svyComptVO
	 * @throws Exception
	 */
	public void updateWkaSvyComptPhotoList(LssWkaSvyComptVO searchVO) throws Exception{
		lssWkaSvyComptDAO.updateWkaSvyComptPhotoList(searchVO);
	}
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	@Override
	public EgovMap selectSvyPhotoNullList(LssWkaSvyComptVO searchVO) throws Exception {
		return lssWkaSvyComptDAO.selectSvyPhotoNullList(searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssWkaSvyComptVO map) throws Exception{
		return lssWkaSvyComptDAO.updatePhotoList(map);
	};
	
	/**
	 * 대상지 공간정보 조회 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeom(LssWkaSvyComptVO searchVO) throws Exception{
		return lssWkaSvyComptDAO.selectSvyComptGeom(searchVO);
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
			lssWkaSvyComptDAO.deleteSvyComptGeomVnarapnt(geomMap);
		}else {
			lssWkaSvyComptDAO.insertSvyComptGeomVnarapnt(geomMap);
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
			lssWkaSvyComptDAO.deleteSvyComptGeomVnaralne(geomMap);
		}else {
			lssWkaSvyComptDAO.insertSvyComptGeomVnaralne(geomMap);
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
			lssWkaSvyComptDAO.deleteSvyComptGeomVnaraPlgn(geomMap);
		}else {
			lssWkaSvyComptDAO.insertSvyComptGeomVnaraPlgn(geomMap);
		}
	}
	
	/**
	 * 대상지 공간정보 조회 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeomPntLne(LssWkaSvyComptVO searchVO) throws Exception{
		return lssWkaSvyComptDAO.selectSvyComptGeomPntLne(searchVO);
	}
	

	/**
	 * 대상지 공간정보 조회 
	 */
	@Override
	public List<LssWkaSvyComptVO> selectSvyComptGeomPlgn(LssWkaSvyComptVO searchVO) throws Exception {
		return lssWkaSvyComptDAO.selectSvyComptGeomPlgn(searchVO);
	}

	@Override
	public List<LssWkaSvyComptVO> selectCalcInArea(LssWkaSvyComptVO searchVO) throws Exception {
		return lssWkaSvyComptDAO.selectCalcInArea(searchVO);
	}
	
	/**
	 * 취약지역 쉐이프파일을 다운로드한다.
	 */
	@Override
	public AnalFileVO downloadWkaSvyShp(String gid) throws Exception{
		String where = "gid = ".concat(gid);
		
		String[] fields = new String[]{"gid"};
		
		SupMapCommonUtils utils = new SupMapCommonUtils();
		
		AnalFileVO pntVo = utils.exportDbToFile("tf_feis_wka_vnarapnt", where,fields);
		AnalFileVO lneVo = utils.exportDbToFile("tf_feis_wka_vnaralne", where,fields);
		AnalFileVO plgnVo = utils.exportDbToFile("tf_feis_wka_vnaraplgn", where,fields);
		
		//대피로 쉐이프파일을 유출구 쉐이프파일 폴더로 이동
		File lneFile = new File(lneVo.getFileStreCours()+File.separator+lneVo.getStreFileNm());
		File lneFiles[] = lneFile.listFiles();
		for (File file : lneFiles) {
			EgovFileUtil.mv(file.getPath(), pntVo.getFileStreCours()+File.separator+pntVo.getStreFileNm());
		}
		
		//취약지역 쉐이프파일을 유출구 쉐이프파일 폴더로 이동
		File plgnFile = new File(plgnVo.getFileStreCours()+File.separator+plgnVo.getStreFileNm());
		File plgnFiles[] = plgnFile.listFiles();
		for (File file : plgnFiles) {
			EgovFileUtil.mv(file.getPath(), pntVo.getFileStreCours()+File.separator+pntVo.getStreFileNm());
		}
		
		boolean isCompressed = EgovFileCmprs.cmprsFile(pntVo.getFileStreCours()+File.separator+pntVo.getStreFileNm(), pntVo.getFileStreCours()+File.separator+pntVo.getStreFileNm().concat(".zip"));
		
		//boolean isCompressed = EgovFileCmprs.cmprsFile("D:/home/tomcat/FEIStorage/analysis/2022/10/24/1666619558727/test", "test.zip");
		EgovFileUtil.rm(pntVo.getFileStreCours()+File.separator+pntVo.getStreFileNm());
		EgovFileUtil.rm(lneVo.getFileStreCours()+File.separator+lneVo.getStreFileNm());
		EgovFileUtil.rm(plgnVo.getFileStreCours()+File.separator+plgnVo.getStreFileNm());
		
		pntVo.setFileExtsn("zip");
		
		return pntVo;
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = lssWkaSvyComptDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * 로그인한 유저의 권한있는 공유방 목록 조회
	 * @throws Exception
	 */
	public List<String> selectAuthorCnrsList(HashMap<String, Object> map) throws Exception{
		return lssWkaSvyComptDAO.selectAuthorCnrsList(map);
	}
	
	/**
	 * 유역면적 점수를 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectDgrareaScore(String vnaraPlgnWkt03) throws Exception {
		String score = "0";
		Double result = lssWkaSvyComptDAO.selectDgrareaScore(vnaraPlgnWkt03);
		
		if(result < 6) {
			score = "1";
		}else if(result < 10) {
			score = "2";
		}else if(result < 20) {
			score = "3";
		}else if(result < 30) {
			score = "4";
		}else {
			score = "5";
		}
		
		return score;
	}
	
	/**
	 * 공간정보 2개의 폴리곤을 하나로 합친다. 
     * @param calcAreaMap
     * @return String
     */
	public String selectUnionGeom(HashMap<String, Object> map) throws Exception{
		return lssWkaSvyComptDAO.selectUnionGeom(map);
	}

	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectLssWkaProjDMS(HashMap<String, Object> map) throws Exception{
		return lssWkaSvyComptDAO.selectLssWkaProjDMS(map);
	}
}
