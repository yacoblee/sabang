package or.sabang.sys.vyt.ecb.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;
import or.sabang.sys.fck.apr.service.FckAprStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbSldManageService;
import or.sabang.sys.vyt.ecb.service.VytEcbStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbWorkVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.sys.fck.apr.service.FckAprFieldBookService;

@Service("vytEcbSldManageService")
public class VytEcbSldManageServiceImpl extends EgovAbstractServiceImpl implements VytEcbSldManageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VytEcbSldManageServiceImpl.class);
	
	@Resource(name="vytEcbSldManageDAO")
	private VytEcbSldManageDAO vytEcbSldManageDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * 사업 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectVytEcbWorkMaxYear() throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkMaxYear();
	}
	
	/**
	 * 사업 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytEcbWorkYear() throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkYear();
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<VytEcbWorkVO> selectVytEcbWorkList(VytEcbWorkVO searchVO) throws Exception {		
		return vytEcbSldManageDAO.selectVytEcbWorkList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectVytEcbWorkListTotCnt(VytEcbWorkVO searchVO) throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkListTotCnt(searchVO);
	}
	
	/**
	 * @param vytEcbWorkVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	@Override
	public String insertWork(VytEcbWorkVO vytEcbWorkVO) throws Exception {
		vytEcbSldManageDAO.insertWork(vytEcbWorkVO);
		return vytEcbWorkVO.getId();
	}

	/**
	 * @param vytEcbWorkVO
	 * @param mFile
	 * @throws Exception
	 * @description 공유방 대상지 등록
	 */
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(VytEcbWorkVO vytEcbWorkVO,MultipartFile mFile) throws Exception {
		
		List<VytEcbStripLandVO> list = new ArrayList<VytEcbStripLandVO>();

		String mstid = vytEcbWorkVO.getId();
		String user = vytEcbWorkVO.getCreatUser();
		String type = vytEcbWorkVO.getWorkType();
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		int rowsCnt = 0;
		
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
	            XSSFRow xssfRow = xssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		VytEcbStripLandVO vo = new VytEcbStripLandVO();

	            		vo.setMstId(mstid);
	            		vo.setCreatDt(user);
	            		vo = convertXSSFCellValues(type,row,vo);
	            		list.add(vo);
//	            		if(vo.getFID() != 0 && vo.getLABEL() != null) {
//	            			list.add(vo);
//	            		}
	            	}
	            }
	    	}
		}else {
			HSSFWorkbook hssfWB = (HSSFWorkbook) excelZipService.loadWorkbook(inputStream);
			
			if (hssfWB.getNumberOfSheets() == 1) {
				HSSFSheet hssfSheet = hssfWB.getSheetAt(0);  //시트 가져오기
	            HSSFRow hssfRow = hssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = hssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=hssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	
	            	HSSFRow row=hssfSheet.getRow(j); //row 가져오기
	                if(row!=null){
	                	VytEcbStripLandVO vo = new VytEcbStripLandVO();

	            		vo.setMstId(mstid);
	            		vo.setCreatDt(user);
	            		vo = convertHSSFCellValues(type,row,vo);
	            		list.add(vo);
	                }
	            }
			}
		}
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;

		for (VytEcbStripLandVO subVo : list) {
			try {
				vytEcbSldManageDAO.insertStripLandVO(subVo);
				successCnt++;
			} catch (Exception e) {
				// TODO: handle exception
				failedIdsArray.put(subVo.getSldId());
				returnInsertLog.put("error", e.getMessage());
				LOGGER.error(e.getMessage());
			}
		}
		
		returnInsertLog.put("total", rowsCnt-1);
		returnInsertLog.put("successCnt", successCnt);
		returnInsertLog.put("failedIds", failedIdsArray);
		return returnInsertLog;
//		if(list.size() > 1000) {
//			List<List<FckAprFieldBookItemVO>> listByFiveHundredList = ListUtils.partition(list, list.size() / 1000);
//			for (List<FckAprFieldBookItemVO> subList : listByFiveHundredList) {
//				fckAprFieldBookDAO.insertStripLand(subList);
//			}
//		}else {
//			fckAprFieldBookDAO.insertStripLand(list);
//		}
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 삭제
	 */
	@Override
	public void deleteWork(HashMap<String, Object> map) throws Exception {
		vytEcbSldManageDAO.deleteWork(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 사업 정보 상세조회
	 */
	@Override
	public VytEcbWorkVO selectVytEcbWorkDetail(HashMap<String, Object> map) throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkDetail(map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 목록 조회
	 */
	public List<VytEcbStripLandVO> selectVytEcbWorkSldList(VytEcbWorkVO searchVO) throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkSldList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 목록 갯수조회
	 */
	public int selectVytEcbWorkSldListTotCnt(VytEcbWorkVO searchVO) throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkSldListTotCnt(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 대상지 목록 일괄삭제
	 */
	@Override
	public void deleteWorkAllSld(HashMap<String, Object> map) throws Exception {
		vytEcbSldManageDAO.deleteWorkAllSld(map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 수정조회
	 */
	public List<VytEcbStripLandVO> selectVytEcbWorkSldListView(VytEcbWorkVO searchVO) throws Exception {
		return vytEcbSldManageDAO.selectVytEcbWorkSldListView(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 조사데이터 삭제
	 */
	@Override
	public void deleteWorkItem(HashMap<String, Object> map) throws Exception {
		vytEcbSldManageDAO.deleteWorkItem(map);
	}
	

	
	/**
	 * 사방사업 타당성평가 조사정보 XSSF
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private VytEcbStripLandVO convertXSSFCellValues(String mType,XSSFRow row,VytEcbStripLandVO vo) throws Exception {
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		
		String pattern = "[^0-9.]+";
		double latDd = 0;
		double lonDd = 0;
		
		cell = row.getCell(0);  //일련번호
	   	if(cell!=null){
	   		String sldId = ExcelUtils.getCellValue(cell);
	   		if(sldId.equals("")) {
	   			return vo;
	   		}
	   		vo.setSldId(sldId);
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(1);  //위도
	   	if(cell!=null){
	   		String lat = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] latArray = lat.split(pattern);
    		if(lat.equals("")) {
    			return vo;
    		}else {
    			if(latArray.length == 1) {
        			latDd = Double.parseDouble(latArray[0]);
        		}else {
        			latDd = Integer.parseInt(latArray[0]) + (Double.parseDouble(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
        		}
    			vo.setLat(latDd);
    		}
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(2);  //경도
	   	if(cell!=null){
	   		String lon = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] lonArray = lon.split(pattern);
    		if(lon.equals("")) {
    			return vo;
    		}else {
    			if(lonArray.length == 1) {
        			lonDd = Double.parseDouble(lonArray[0]);
        		}else {
        			lonDd = Integer.parseInt(lonArray[0]) + (Double.parseDouble(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
        		}
    			vo.setLon(lonDd);
    		}
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(3);  //시도명
	   	if(cell!=null){
	   		String sdNm = ExcelUtils.getCellValue(cell);
	   		if(sdNm.equals("")) {
	   			return vo;
	   		}
	   		vo.setSdNm(sdNm);
	   	}else {
	   		return vo;
	   	}
	
	   	cell = row.getCell(4);  //시군구명
	   	if(cell!=null){
	   		String sggNm = ExcelUtils.getCellValue(cell);
	   		if(sggNm.equals("")) {
	   			return vo;
	   		}
	   		vo.setSggNm(sggNm);
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(5);  //읍면동명
	   	if(cell!=null){
	   		String emdNm = ExcelUtils.getCellValue(cell);
	   		if(emdNm.equals("")) {
	   			return vo;
	   		}
	   		vo.setEmdNm(emdNm);
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(6);  //리명
	   	if(cell!=null){
	   		String riNm = ExcelUtils.getCellValue(cell);
	   		vo.setRiNm(riNm);
	   	}
	   	
	   	cell = row.getCell(7);  //지번명
	   	if(cell!=null){
	   		String jibun = ExcelUtils.getCellValue(cell);
	   		vo.setJibun(jibun);
	   	}
	   	
		return vo;
	}
	
	/**
	 * 사방사업 타당성평가 조사정보 HSSF
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private VytEcbStripLandVO convertHSSFCellValues(String mType,HSSFRow row,VytEcbStripLandVO vo) throws Exception {
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		HSSFCell cell = null;
		
		String pattern = "[^0-9.]+";
		double latDd = 0;
		double lonDd = 0;
		
		cell = row.getCell(0);  //일련번호
	   	if(cell!=null){
	   		String sldId = ExcelUtils.getCellValue(cell);
	   		if(sldId.equals("")) {
	   			return vo;
	   		}
	   		vo.setSldId(sldId);
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(1);  //위도
	   	if(cell!=null){
	   		String lat = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] latArray = lat.split(pattern);
    		if(lat.equals("")) {
    			return vo;
    		}else {
    			if(latArray.length == 1) {
        			latDd = Double.parseDouble(latArray[0]);
        		}else {
        			latDd = Integer.parseInt(latArray[0]) + (Double.parseDouble(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
        		}
    			vo.setLat(latDd);
    		}
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(2);  //경도
	   	if(cell!=null){
	   		String lon = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] lonArray = lon.split(pattern);
    		if(lon.equals("")) {
    			return vo;
    		}else {
    			if(lonArray.length == 1) {
        			lonDd = Double.parseDouble(lonArray[0]);
        		}else {
        			lonDd = Integer.parseInt(lonArray[0]) + (Double.parseDouble(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
        		}
    			vo.setLon(lonDd);
    		}
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(3);  //시도명
	   	if(cell!=null){
	   		String sdNm = ExcelUtils.getCellValue(cell);
	   		if(sdNm.equals("")) {
	   			return vo;
	   		}
	   		vo.setSdNm(sdNm);
	   	}else {
	   		return vo;
	   	}
	
	   	cell = row.getCell(4);  //시군구명
	   	if(cell!=null){
	   		String sggNm = ExcelUtils.getCellValue(cell);
	   		if(sggNm.equals("")) {
	   			return vo;
	   		}
	   		vo.setSggNm(sggNm);
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(5);  //읍면동명
	   	if(cell!=null){
	   		String emdNm = ExcelUtils.getCellValue(cell);
	   		if(emdNm.equals("")) {
	   			return vo;
	   		}
	   		vo.setEmdNm(emdNm);
	   	}else {
	   		return vo;
	   	}
	   	
	   	cell = row.getCell(6);  //리명
	   	if(cell!=null){
	   		String riNm = ExcelUtils.getCellValue(cell);
	   		vo.setRiNm(riNm);
	   	}
	   	
	   	cell = row.getCell(7);  //지번명
	   	if(cell!=null){
	   		String jibun = ExcelUtils.getCellValue(cell);
	   		vo.setJibun(jibun);
	   	}
	   	
		return vo;
	}
}
