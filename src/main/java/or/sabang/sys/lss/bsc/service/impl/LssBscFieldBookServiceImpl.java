package or.sabang.sys.lss.bsc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.impl.FckAprFieldBookServiceImpl;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookItemVO;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookVO;
import or.sabang.sys.lss.bsc.service.LssBscStripLandVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.utl.StaticMapImageUtils;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookService;

@Service("lssBscFieldBookService")
public class LssBscFieldBookServiceImpl extends EgovAbstractServiceImpl implements LssBscFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LssBscFieldBookServiceImpl.class);
	
	@Resource(name="lssBscFieldBookDAO")
	private LssBscFieldBookDAO lssBscFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<LssBscFieldBookVO> selectLssBscFieldBookList(LssBscFieldBookVO searchVO) throws Exception {		
		return lssBscFieldBookDAO.selectLssBscFieldBookList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectLssBscFieldBookListTotCnt(LssBscFieldBookVO searchVO) throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookListTotCnt(searchVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@Override
	public LssBscFieldBookVO selectLssBscFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookDetail(map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssBscStripLandVO> selectLssBscFieldBookItemList(LssBscFieldBookItemVO searchVO) throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssBscFieldBookItemListTotCnt(LssBscFieldBookItemVO searchVO) throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookItemListTotCnt(searchVO);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception {
//		return lssBscFieldBookDAO.selectCnrsSpceType(map);
//	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(LssBscFieldBookVO fieldBookVO) throws Exception {
		lssBscFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}

	/**
	 * 대상지를 일괄등록한다.
	 */
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(LssBscFieldBookVO fieldBookVO,MultipartFile mFile) throws Exception {
		List<LssBscFieldBookItemVO> list = new ArrayList<LssBscFieldBookItemVO>();

		String user = fieldBookVO.getMst_create_user();
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
	            		LssBscFieldBookItemVO vo = new LssBscFieldBookItemVO();

	            		vo.setMST_ID(fieldBookVO.getId());
	            		vo.setLOGIN_ID(user);
	            		vo = convertXSSFCellValues(row,vo);
	            		if(vo.getLABEL() != null) {
	            			list.add(vo);
	            		}
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
	                	LssBscFieldBookItemVO vo = new LssBscFieldBookItemVO();
	                	vo.setMST_ID(fieldBookVO.getId());
	                	vo.setLOGIN_ID(user);
	            		vo = convertHSSFCellValues(row,vo);
	                    
	            		if(vo.getLABEL() != null) {
	            			list.add(vo);
	            		}
	                }
	            }
			}
		}
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;

		for (LssBscFieldBookItemVO subVo : list) {
			try {
				lssBscFieldBookDAO.insertStripLandVO(subVo);
				successCnt++;
			} catch (Exception e) {
				// TODO: handle exception
				failedIdsArray.put(subVo.getLABEL());
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
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	@Override
//	public void insertCnrsSpceSld(LssBscFieldBookItemVO itemVO) throws Exception {
//		lssBscFieldBookDAO.insertCnrsSpceSld(itemVO);
//	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssBscStripLandVO> selectLssBscFieldBookItemView(LssBscFieldBookItemVO searchVO) throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookItemView(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> map) throws Exception {
		lssBscFieldBookDAO.deleteCnrsSpce(map);
	}

	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception {
		lssBscFieldBookDAO.deleteCnrsSpceItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception {
		lssBscFieldBookDAO.deleteCnrsSpceAllItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception {
		lssBscFieldBookDAO.deleteCnrsSpceComptItem(map);
	}
	
	/**
	 * 기초조사 조사정보 XSSF
	 * @return
	 * @throws Exception
	 */
	private LssBscFieldBookItemVO convertXSSFCellValues(XSSFRow row,LssBscFieldBookItemVO vo) throws Exception {
		//FckAprStripLand vo = new FckAprStripLand();
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		
		String pattern = "[^0-9.]+";
		
		Calendar calendar = Calendar.getInstance();
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		
		cell = row.getCell(0);  //조사ID
    	if(cell!=null){
    		String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		vo.setLABEL(svyId);
    		vo.setKEYWORD(svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(1);  //조사유형
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(2);  //조사연도
    	if(cell!=null){
    		String year = ExcelUtils.getCellValue(cell);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", year.equals("") ? String.valueOf(calendar.get(Calendar.YEAR)) : year);
    		attrItem.put("NAME","조사연도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
    		attrItem.put("NAME","조사연도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(3);  //관할1
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","관할1");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","관할1");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(4);  //관할2
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","관할2");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","관할2");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(5);  //시도
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(6);  //시군구
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시군구");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시군구");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(7);  //읍면동
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","읍면동");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","읍면동");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(8);  //리
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","리");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","리");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(9);  //지번
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","지번");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","지번");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(10);  //위도
    	if(cell!=null) {
    		//위도
    		String lat = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] latArray = lat.split(pattern);
    		
    		if(latArray.length == 1) {
    			latDd = Double.parseDouble(latArray[0]);
    		}else {
    			latDd = Integer.parseInt(latArray[0]) + (Double.parseDouble(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(11);  //경도
    	if(cell!=null) {
    		//경도
    		String lon = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] lonArray = lon.split(pattern);
    		
    		if(lonArray.length == 1) {
    			lonDd = Double.parseDouble(lonArray[0]);
    		}else {
    			lonDd = Integer.parseInt(lonArray[0]) + (Double.parseDouble(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(12);  //모암
    	if(cell!=null) {
    		String moam = ExcelUtils.getCellValue(cell);
    		memo.put("모암_측정값", moam);
    		if(moam.indexOf("퇴적") > -1) {
    			memo.put("모암_점수", "2");
    		}else if(moam.indexOf("화강") > -1) {
    			memo.put("모암_점수", "4");
    		}else if(moam.indexOf("천매") > -1) {
    			memo.put("모암_점수", "6");
    		}else if(moam.indexOf("편암") > -1) {
    			memo.put("모암_점수", "8");
    		}else if(moam.indexOf("반암") > -1) {
    			memo.put("모암_점수", "10");
    		}
    	}else {
    		memo.put("모암_측정값", "");
    		memo.put("모암_점수", "");
    	}
    	
		vo.setATTR(attr.toString());
		vo.setMEMO(memo.toString());
		
        vo.setLATDD(latDd);
        vo.setLONDD(lonDd);
        
		return vo;
	}
	
	/**
	 * 기초조사 조사정보 HSSF
	 * @return
	 * @throws Exception
	 */
	private LssBscFieldBookItemVO convertHSSFCellValues(HSSFRow row,LssBscFieldBookItemVO vo) throws Exception {
		//FckAprStripLand vo = new FckAprStripLand();
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		
		String pattern = "[^0-9.]+";
		
		Calendar calendar = Calendar.getInstance();
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		HSSFCell cell = null;
		
		cell = row.getCell(0);  //조사ID
    	if(cell!=null){
    		String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		vo.setLABEL(svyId);
    		vo.setKEYWORD(svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(1);  //조사유형
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(2);  //조사연도
    	if(cell!=null){
    		String year = ExcelUtils.getCellValue(cell);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", year.equals("") ? String.valueOf(calendar.get(Calendar.YEAR)) : year);
    		attrItem.put("NAME","조사연도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
    		attrItem.put("NAME","조사연도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(3);  //관할1
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","관할1");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","관할1");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(4);  //관할2
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","관할2");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","관할2");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(5);  //시도
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(6);  //시군구
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시군구");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시군구");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(7);  //읍면동
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","읍면동");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","읍면동");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(8);  //리
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","리");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","리");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(9);  //지번
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","지번");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","지번");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(10);  //위도
    	if(cell!=null) {
    		//위도
    		String lat = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] latArray = lat.split(pattern);
    		
    		if(latArray.length == 1) {
    			latDd = Double.parseDouble(latArray[0]);
    		}else {
    			latDd = Integer.parseInt(latArray[0]) + (Integer.parseInt(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(11);  //경도
    	if(cell!=null) {
    		//경도
    		String lon = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] lonArray = lon.split(pattern);
    		
    		if(lonArray.length == 1) {
    			lonDd = Double.parseDouble(lonArray[0]);
    		}else {
    			lonDd = Integer.parseInt(lonArray[0]) + (Integer.parseInt(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(12);  //모암
    	if(cell!=null) {
    		String moam = ExcelUtils.getCellValue(cell);
    		memo.put("모암_측정값", moam);
    		if(moam.indexOf("퇴적") > -1) {
    			memo.put("모암_점수", "2");
    		}else if(moam.indexOf("화강") > -1) {
    			memo.put("모암_점수", "4");
    		}else if(moam.indexOf("천매") > -1) {
    			memo.put("모암_점수", "6");
    		}else if(moam.indexOf("편암") > -1) {
    			memo.put("모암_점수", "8");
    		}else if(moam.indexOf("반암") > -1) {
    			memo.put("모암_점수", "10");
    		}
    	}else {
    		memo.put("모암_측정값", "");
    		memo.put("모암_점수", "");
    	}
    	
		vo.setATTR(attr.toString());
		vo.setMEMO(memo.toString());
		
        vo.setLATDD(latDd);
        vo.setLONDD(lonDd);
        
		return vo;
	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectLssBscFieldBookMaxYear() throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookMaxYear();
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssBscFieldBookYear() throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookYear();
	}
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssBscFieldBookCheckMstName(String mst_partname) throws Exception {
		return lssBscFieldBookDAO.selectLssBscFieldBookCheckMstName(mst_partname);
	}
}
