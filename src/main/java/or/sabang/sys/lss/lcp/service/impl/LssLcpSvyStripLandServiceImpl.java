package or.sabang.sys.lss.lcp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.mng.log.clg.service.LoginLog;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLand;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.sys.service.ZonalStatisticVO;
import or.sabang.utl.ExcelUtils;

@Service("lssLcpSvyStripLandService")
public class LssLcpSvyStripLandServiceImpl extends EgovAbstractServiceImpl implements LssLcpSvyStripLandService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssLcpSvyStripLandDAO")
	private LssLcpSvyStripLandDAO lssLcpSvyStripLandDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectLssLcpSvySldTotCnt(LssLcpSvyStripLandVO searchVO) throws Exception {
        return lssLcpSvyStripLandDAO.selectLssLcpSvySldTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<?> selectLssLcpSvySldList(LssLcpSvyStripLandVO searchVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectLssLcpSvySldList(searchVO);
	}
	
	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<EgovMap> test() throws Exception {
		return lssLcpSvyStripLandDAO.test();
	}
	
	/**
	 * 대상지 정보 갯수를 조회한다.
	 */
	@Override
	public int selectSvySldInfoCnt(LssLcpSvyStripLandVO stripLandVO) throws Exception {
        return lssLcpSvyStripLandDAO.selectSvySldInfoCnt(stripLandVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 */
	@Override
	public List<?> selectSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldInfo(stripLandVO);
	}
	
	/**
	 * 대상지 정보를 상세조회한다.
	 */
	@Override
	public LssLcpSvyStripLandVO selectSvySldInfoDetail(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldInfoDetail(stripLandVO);
	}
	
	/**
	 * 대상지를 일괄등록한다.
	 */
	@Override
	public void insertUploadStripLand(MultipartFile mFile) throws Exception {
		List<LssLcpSvyStripLand> list = new ArrayList<LssLcpSvyStripLand>();
		LssLcpSvyStripLand lssLcpSvyStripLand = null;
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		String nowDate = sdf.format(new Date());
		
		String pattern = "[^0-9.]+";
		
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
	            XSSFRow xssfRow = xssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            int rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	lssLcpSvyStripLand = new LssLcpSvyStripLand();
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
	                    XSSFCell cell = null;
	                	cell = row.getCell(0);  //조사ID
	                	if(cell!=null){
	                		lssLcpSvyStripLand.setId(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(1); //조사유형
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setType(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(2); //조사연도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setYear(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(3); //관할1
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setRegion1(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(4); //관할2
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setRegion2(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(5); //시도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setSd(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(6); //시군구
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setSgg(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(7); //읍면동
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setEmd(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(8); //리
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setRi(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(9); //지번
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setJibun(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(10); //위도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setLat(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(11); //경도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setLon(ExcelUtils.getCellValue(cell));
	                	}
	                    
	            		//위도
	            		String lat = lssLcpSvyStripLand.getLat();
	            		String[] latArray = lat.split(pattern);
	            		//경도
	            		String lon = lssLcpSvyStripLand.getLon();
	            		String[] lonArray = lon.split(pattern);
	            		
	                    double latDd = Integer.parseInt(latArray[0]) + (Integer.parseInt(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
	        			double lonDd = Integer.parseInt(lonArray[0]) + (Integer.parseInt(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
	            		
	                    lssLcpSvyStripLand.setCreateDt(nowDate);
	                    lssLcpSvyStripLand.setLatDd(latDd);
	                    lssLcpSvyStripLand.setLonDd(lonDd);
	                    lssLcpSvyStripLand.setWriter(user.getId());
	                    list.add(lssLcpSvyStripLand);
	            	}
	            }
	    	}
		}else {
			HSSFWorkbook hssfWB = (HSSFWorkbook) excelZipService.loadWorkbook(inputStream);
			
			if (hssfWB.getNumberOfSheets() == 1) {
				HSSFSheet hssfSheet = hssfWB.getSheetAt(0);  //시트 가져오기
	            HSSFRow hssfRow = hssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = hssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            int rowsCnt=hssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	lssLcpSvyStripLand = new LssLcpSvyStripLand();
	            	HSSFRow row=hssfSheet.getRow(j); //row 가져오기
	                if(row!=null){
	                	int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
	                    HSSFCell cell = null;
	                	cell = row.getCell(0);  //조사ID
	                	if(cell!=null){
	                		lssLcpSvyStripLand.setId(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(1); //조사유형
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setType(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(2); //조사연도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setYear(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(3); //관할1
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setRegion1(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(4); //관할2
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setRegion2(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(5); //시도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setSd(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(6); //시군구
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setSgg(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(7); //읍면동
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setEmd(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(8); //리
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setRi(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(9); //지번
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setJibun(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(10); //위도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setLat(ExcelUtils.getCellValue(cell));
	                	}
	                    cell = row.getCell(11); //경도
	                    if(cell!=null){
	                    	lssLcpSvyStripLand.setLon(ExcelUtils.getCellValue(cell));
	                	}
	                    
	            		//위도
	            		String lat = lssLcpSvyStripLand.getLat();
	            		String[] latArray = lat.split(pattern);
	            		//경도
	            		String lon = lssLcpSvyStripLand.getLon();
	            		String[] lonArray = lon.split(pattern);
	            		
	            		double latDd = Integer.parseInt(latArray[0]) + (Integer.parseInt(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
	        			double lonDd = Integer.parseInt(lonArray[0]) + (Integer.parseInt(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
	            		
	                    lssLcpSvyStripLand.setCreateDt(nowDate);
	                    lssLcpSvyStripLand.setLatDd(latDd);
	                    lssLcpSvyStripLand.setLonDd(lonDd);
	                    lssLcpSvyStripLand.setWriter(user.getId());
	                    list.add(lssLcpSvyStripLand);
	                }
	            }
			}
		}
		
		List<List<LssLcpSvyStripLand>> listByFiveHundredList = ListUtils.partition(list, list.size() / 1000);
		for (List<LssLcpSvyStripLand> subList : listByFiveHundredList) {
			lssLcpSvyStripLandDAO.insertUploadStripLand(subList);
		}
//		lssLcpSvyStripLandDAO.insertUploadStripLand(list);
	}
	
	/**
	 * 대상지를 등록한다.
	 */
	@Override
	public void insertLssLcpSvyStripLand(LssLcpSvyStripLand stripLand) throws Exception {
		lssLcpSvyStripLandDAO.insertLssLcpSvyStripLand(stripLand);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	public void updateLssLcpSvyStripLand(LssLcpSvyStripLand stripLand) throws Exception {
		lssLcpSvyStripLandDAO.updateLssLcpSvyStripLand(stripLand);
	}
	
	/**
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteLssLcpSvyStripLand(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.deleteLssLcpSvyStripLand(stripLandVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectLssLcpSvyStripLandMaxYear() throws Exception {
		return lssLcpSvyStripLandDAO.selectLssLcpSvyStripLandMaxYear();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	@Override
	public List<?> selectLssLcpSvyStripLandYear() throws Exception {
		return lssLcpSvyStripLandDAO.selectLssLcpSvyStripLandYear();
	}
	
	/**
	 * 조사대상지 등록정보를 등록한다.
	 * @throws Exception
	 */
	@Override
	public String insertSvySldRegInfo(HashMap<String, Object> sldMap) throws Exception {
		// 조사대상지 등록정보 중복 체크
		int dplctCheck = lssLcpSvyStripLandDAO.dplctCheckSvySldInfo(sldMap);
		String sldId = null;
		if(dplctCheck == 1) {
			sldId = "duplication";
		}else {
			lssLcpSvyStripLandDAO.insertSvySldRegInfo(sldMap);
			sldId = sldMap.get("id").toString(); 
		}
		return sldId;
	};
	
	 /**
     * 조사대상지 정보를 조회한다.
     * @throws Exception
     */
    public List<EgovMap> selectRankInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
    	return lssLcpSvyStripLandDAO.selectRankInfo(stripLandVO);
    }
    
    /**
     * 행정구역별 미조사된 대상지 건수를 조회한다.
     * @throws Exception
     */
    public EgovMap selectRankInfoCnt() throws Exception {
    	return lssLcpSvyStripLandDAO.selectRankInfoCnt();
    }
    
    /**
     * 행정구역별 작년조사된 대상지 건수를 조회한다.
     * @throws Exception
     */
    public EgovMap selectLastRankInfoCnt() throws Exception {
    	return lssLcpSvyStripLandDAO.selectLastRankInfoCnt();
    }
    
    /**
	 * 랭크데이터를 수정한다.
	 * @throws Exception
	 */
	public void updateRankInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.updateRankInfo(stripLandVO);
	}
    
	/**
	 * 조사대상지 정보를 등록한다.
	 * @throws Exception
	 */
    @Override
	public void insertSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {    	
    	EgovMap result = lssLcpSvyStripLandDAO.selectSvySldCntInfo(stripLandVO);
    	
    	stripLandVO.setGid(result.get("gid").toString());
    	if(result.get("labelNum") != null) {
    		stripLandVO.setLabelNum(result.get("labelNum").toString());    		
    	}
    	
		lssLcpSvyStripLandDAO.insertSvySldInfo(stripLandVO);
	};
	
	/**
     * 조사대상지 등록정보 상세조회
     * @throws Exception
     */
    public EgovMap selectSvySldRegInfoDetail(LssLcpSvyStripLandVO stripLandVO) throws Exception {
    	return lssLcpSvyStripLandDAO.selectSvySldRegInfoDetail(stripLandVO);
    }
    
    /**
	 * 조사대상지 시도별 건수 조회
	 * @throws Exception
	 */
	@Override
	public List<?> selectSvySldCtrdCnt(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldCtrdCnt(stripLandVO);
	}
	
	/**
	 * 조사대상지 등록정보를 수정한다.
	 * @throws Exception
	 */
	public void updateSvySldRegInfo(String sldId) throws Exception {
		lssLcpSvyStripLandDAO.updateSvySldRegInfo(sldId);
	}
	
	/**
	 * 조사대상지 정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.deleteSvySldInfo(stripLandVO);
	}
	
	/**
	 * 랭크데이터를 수정한다.
	 * @throws Exception
	 */
	public void updateRankDelInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.updateRankDelInfo(stripLandVO);
	}
	
	/**
	 * 조사대상지 임상도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldImInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldImInfo(stripLandVO);
	}
	
	/**
	 * 조사대상지 입지도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldIjInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldIjInfo(stripLandVO);
	}
	
	/**
	 * 조사대상지 지질도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldGlInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldGlInfo(stripLandVO);
	}	
	
	/**
	 * 랭크 정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteRank(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.deleteRank(stripLandVO);
		//lssLcpSvyStripLandDAO.deleteSvySldInfo(stripLandVO);
		//lssLcpSvyStripLandDAO.deleteSvySldRegInfo(stripLandVO);
	}
	
	/**
	 * 제보 정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteGvf(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.deleteGvf(stripLandVO);
	}
	
	/**
	 * 조사대상지 등록정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteSvySldRegInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.deleteSvySldRegInfo(stripLandVO);
	}
	
	/**
	 * 조사대상지 등록정보방 목록을 조회한다.
	 */
	public List<?> selectSvySldRegInfoList() throws Exception {
		return lssLcpSvyStripLandDAO.selectSvySldRegInfoList();
	}
	
	/**
	 * 고도정보를 등록한다.
	 */
	public void updateSvySldElevInfo(List<ZonalStatisticVO> list) throws Exception {
		lssLcpSvyStripLandDAO.updateSvySldElevInfo(list);
	}
	
	/**
	 * 경사정보를 등록한다.
	 */
	public void updateSvySldSlopInfo(List<ZonalStatisticVO> list) throws Exception {
		lssLcpSvyStripLandDAO.updateSvySldSlopInfo(list);
	}
	
	/**
	 * 토심정보를 등록한다.
	 */
	public void updateSvySldSldInfo(List<ZonalStatisticVO> list) throws Exception {
		lssLcpSvyStripLandDAO.updateSvySldSldInfo(list);
	}
	
	public List<EgovMap> selectSvysldGidInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpSvyStripLandDAO.selectSvysldGidInfo(stripLandVO);
	}
	public void updateSvySldGid(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		lssLcpSvyStripLandDAO.updateSvySldGid(stripLandVO);
	}
	
	@Override
	public Map<String, Object> selectLcpSvySldListExcel(LssLcpSvyStripLandVO vo) throws Exception {
		List<?> _result = lssLcpSvyStripLandDAO.selectLcpSvySldListExcel(vo);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}
}
