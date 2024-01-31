package or.sabang.sys.lss.cnl.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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

import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookItemVO;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookService;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookVO;
import or.sabang.sys.lss.cnl.service.LssCnlStripLandVO;
import or.sabang.utl.ExcelUtils;

@Service("lssCnlFieldBookService")
public class LssCnlFieldBookServiceImpl extends EgovAbstractServiceImpl implements LssCnlFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LssCnlFieldBookServiceImpl.class);
	
	@Resource(name="lssCnlFieldBookDAO")
	private LssCnlFieldBookDAO lssCnlFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<LssCnlFieldBookVO> selectLssCnlFieldBookList(LssCnlFieldBookVO searchVO) throws Exception {		
		return lssCnlFieldBookDAO.selectLssCnlFieldBookList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectLssCnlFieldBookListTotCnt(LssCnlFieldBookVO searchVO) throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookListTotCnt(searchVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@Override
	public LssCnlFieldBookVO selectLssCnlFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookDetail(map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssCnlStripLandVO> selectLssCnlFieldBookItemList(LssCnlFieldBookItemVO searchVO) throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssCnlFieldBookItemListTotCnt(LssCnlFieldBookItemVO searchVO) throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookItemListTotCnt(searchVO);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception {
//		return lssCnlFieldBookDAO.selectCnrsSpceType(map);
//	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(LssCnlFieldBookVO fieldBookVO) throws Exception {
		lssCnlFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	public void insertCnrsSpceAuthorMgt(LssCnlFieldBookVO fieldBookVO) throws Exception {
		List<LssCnlFieldBookVO> list = new ArrayList<LssCnlFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		if(authorEsntlIdList != null) {
			for(int i =0; i<authorEsntlIdList.length;i++) {
				if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
					LssCnlFieldBookVO vo = new LssCnlFieldBookVO();
					vo.setId(fieldBookVO.getId());
					vo.setSvytype(fieldBookVO.getSvytype());
					vo.setEsntl_id(authorEsntlIdList[i].toString());
					vo.setUser_grade("USER");
					list.add(vo);				
				}
			}
		}
		
		// 생성자 추가
		LssCnlFieldBookVO vo = new LssCnlFieldBookVO();
		vo.setId(fieldBookVO.getId());
		vo.setSvytype(fieldBookVO.getSvytype());
		vo.setEsntl_id(fieldBookVO.getMst_admin_id());
		vo.setUser_grade("ADMIN");
		list.add(vo);
		
		lssCnlFieldBookDAO.insertCnrsSpceAuthorMgt(list);
	}
	
	
	/**
	 * 대상지를 일괄등록한다.
	 */
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(LssCnlFieldBookVO fieldBookVO,MultipartFile mFile) throws Exception {
		List<LssCnlFieldBookItemVO> list = new ArrayList<LssCnlFieldBookItemVO>();

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
	            
	            for(int j=2; j<rowsCnt; j++){ //row 루프
 	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		LssCnlFieldBookItemVO vo = new LssCnlFieldBookItemVO();

	            		vo.setMst_id(fieldBookVO.getId());
	            		vo.setLogin_id(user);
	            		vo = convertXSSFCellValues(row,vo);
	            		if(vo.getSvy_label() != null) {
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
	            
	            for(int j=2; j<rowsCnt; j++){ //row 루프
	            	
	            	HSSFRow row=hssfSheet.getRow(j); //row 가져오기
	                if(row!=null){
	                	LssCnlFieldBookItemVO vo = new LssCnlFieldBookItemVO();
	                	vo.setMst_id(fieldBookVO.getId());
	                	vo.setLogin_id(user);
	            		vo = convertHSSFCellValues(row,vo);
	                    
	            		if(vo.getSvy_label() != null) {
	            			list.add(vo);
	            		}
	                }
	            }
			}
		}
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;

		for (LssCnlFieldBookItemVO subVo : list) {
			try {
				lssCnlFieldBookDAO.insertStripLandVO(subVo);
				successCnt++;
			} catch (Exception e) {
				// TODO: handle exception
				failedIdsArray.put(subVo.getSvy_label());
				returnInsertLog.put("error", e.getMessage());
				LOGGER.error(e.getMessage());
			}
		}
		
		returnInsertLog.put("total", rowsCnt-2);
		returnInsertLog.put("successCnt", successCnt);
		returnInsertLog.put("failedIds", failedIdsArray);
		return returnInsertLog;
	}

	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	@Override
	public void insertCnrsSpceSld(LssCnlFieldBookItemVO itemVO) throws Exception {
		lssCnlFieldBookDAO.insertCnrsSpceSld(itemVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssCnlStripLandVO> selectLssCnlFieldBookItemView(LssCnlFieldBookItemVO searchVO) throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookItemView(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> map) throws Exception {
		lssCnlFieldBookDAO.deleteCnrsSpce(map);
	}

	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception {
		lssCnlFieldBookDAO.deleteCnrsSpceItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception {
		lssCnlFieldBookDAO.deleteCnrsSpceAllItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception {
		lssCnlFieldBookDAO.deleteCnrsSpceComptItem(map);
	}
	
	/**
	 * 취약지역 해제조사 조사정보 XSSF
	 * @return
	 * @throws Exception
	 */
	private LssCnlFieldBookItemVO convertXSSFCellValues(XSSFRow row,LssCnlFieldBookItemVO vo) throws Exception {

		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		
		String svyType = null;
		String pattern = "[^0-9.]+";
		
		Calendar calendar = Calendar.getInstance();
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		
		
		
		cell = row.getCell(1);  //기번
		if(cell!=null){
    		//memo.put("기번", ExcelUtils.getCellValue(cell));
			attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","순번");
    		attr.put(attrItem);
    	}else {
    		//memo.put("기번", "");
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","순번");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(1);  //조사ID (현재(기준일 230125)는 기번과 조사ID는 동일하게 진행)
    	if(cell!=null){
    		String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		vo.setSvy_label(svyId);
    		vo.setSvy_keyword(svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(2);  //시도
    	if(cell!=null){
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
    	
    	cell = row.getCell(3);  //시군구
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
    	
    	cell = row.getCell(4);  //읍면동
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
    	
    	cell = row.getCell(5);  //리
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
    	
    	cell = row.getCell(6);  //지번
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
    	
    	cell = row.getCell(7); //관리주체1
    	if(cell!=null) {
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
    	
    	cell = row.getCell(8);  //취약지 유형
    	if(cell!=null){
    		svyType = ExcelUtils.getCellValue(cell);
    		if(svyType.equals("")) {
    			return vo;
    		}
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "취약지역 해제조사("+ExcelUtils.getCellValue(cell)+")");
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(9); //지정년도
    	if(cell!=null) {
    		memo.put("지정년도", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정년도", "");
    	}
    	
    	cell = row.getCell(10); //지정면적
    	if(cell!=null) {
    		memo.put("지정면적", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정면적", "");
    	}
    	
    	cell = row.getCell(11); //지정번호
    	if(cell!=null) {
    		memo.put("지정번호", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정번호", "");
    	}
    	
    	XSSFCell py_d = row.getCell(12);  //위도_도
    	XSSFCell py_m = row.getCell(13);  //위도_분
    	XSSFCell py_s = row.getCell(14);  //위도_초
    	if(py_d != null && py_m != null && py_s != null) {
    		//시점_위도
    		latDd = Integer.parseInt(ExcelUtils.getCellValue(py_d)) + (Double.parseDouble(ExcelUtils.getCellValue(py_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(py_s))/3600);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", latDd);
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    		vo.setLatdd(latDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    	}
    	
    	XSSFCell px_d = row.getCell(15);  //경도_도
    	XSSFCell px_m = row.getCell(16);  //경도_분
    	XSSFCell px_s = row.getCell(17);  //경도_초
    	if(px_d != null && px_m != null && px_s != null) {
    		//시점_경도
    		lonDd = Integer.parseInt(ExcelUtils.getCellValue(px_d)) + (Double.parseDouble(ExcelUtils.getCellValue(px_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(px_s))/3600);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", lonDd);
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    		vo.setLondd(lonDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(18); //당시현황
    	if(cell!=null) {
    		memo.put("당시현황", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("당시현황", "");
    	}
    	
    	cell = row.getCell(19); //지정사유
    	if(cell!=null) {
    		memo.put("지정사유", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정사유", "");
    	}
    	
    	cell = row.getCell(20); //사업종가능
    	if(cell!=null) {
    		memo.put("사업종가능", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("사업종가능", "");
    	}
    	
    	cell = row.getCell(21); //가능여부
    	if(cell!=null) {
    		memo.put("가능여부", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("가능여부", "");
    	}
    	
    	cell = row.getCell(22); //선정사유
    	if(cell!=null) {
    		memo.put("선정사유", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("선정사유", "");
    	}
    	
    	cell = row.getCell(23); //구역설정
    	if(cell!=null) {
    		memo.put("구역설정", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("구역설정", "");
    	}
    	
    	cell = row.getCell(24); //당시종합의견
    	if(cell!=null) {
    		memo.put("당시종합의견", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("당시종합의견", "");
    	}
    	
    	
		vo.setSvy_attr(attr.toString());
		vo.setSvy_memo(memo.toString());
		
        vo.setLatdd(latDd);
        vo.setLondd(lonDd);
        
		return vo;
	}
	
	/**
	 * 취약지역 해제조사 조사정보 HSSF
	 * @return
	 * @throws Exception
	 */
	private LssCnlFieldBookItemVO convertHSSFCellValues(HSSFRow row,LssCnlFieldBookItemVO vo) throws Exception {
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		
		String svyType = null;
		String pattern = "[^0-9.]+";
		
		Calendar calendar = Calendar.getInstance();
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		HSSFCell cell = null;
		
		cell = row.getCell(1);  //기번
		if(cell!=null){
    		//memo.put("기번", ExcelUtils.getCellValue(cell));
			attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","순번");
    		attr.put(attrItem);
    	}else {
    		//memo.put("기번", "");
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","순번");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(1);  //조사ID (현재(기준일 230125)는 기번과 조사ID는 동일하게 진행)
    	if(cell!=null){
    		String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		vo.setSvy_label(svyId);
    		vo.setSvy_keyword(svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(2);  //시도
    	if(cell!=null){
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
    	
    	cell = row.getCell(3);  //시군구
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
    	
    	cell = row.getCell(4);  //읍면동
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
    	
    	cell = row.getCell(5);  //리
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
    	
    	cell = row.getCell(6);  //지번
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
    	
    	cell = row.getCell(7); //관리주체1
    	if(cell!=null) {
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
    	
    	cell = row.getCell(8);  //취약지 유형
    	if(cell!=null){
    		svyType = ExcelUtils.getCellValue(cell);
    		if(svyType.equals("")) {
    			return vo;
    		}
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "취약지역 해제조사("+ExcelUtils.getCellValue(cell)+")");
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(9); //지정년도
    	if(cell!=null) {
    		memo.put("지정년도", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정년도", "");
    	}
    	
    	cell = row.getCell(10); //지정면적
    	if(cell!=null) {
    		memo.put("지정면적", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정면적", "");
    	}
    	
    	cell = row.getCell(11); //지정번호
    	if(cell!=null) {
    		memo.put("지정번호", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정번호", "");
    	}
    	
    	HSSFCell py_d = row.getCell(12);  //위도_도
    	HSSFCell py_m = row.getCell(13);  //위도_분
    	HSSFCell py_s = row.getCell(14);  //위도_초
    	if(py_d != null && py_m != null && py_s != null) {
    		//시점_위도
    		latDd = Integer.parseInt(ExcelUtils.getCellValue(py_d)) + (Double.parseDouble(ExcelUtils.getCellValue(py_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(py_s))/3600);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", latDd);
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    		vo.setLatdd(latDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","위도");
    		attr.put(attrItem);
    	}
    	
    	HSSFCell px_d = row.getCell(15);  //경도_도
    	HSSFCell px_m = row.getCell(16);  //경도_분
    	HSSFCell px_s = row.getCell(17);  //경도_초
    	if(px_d != null && px_m != null && px_s != null) {
    		//시점_경도
    		lonDd = Integer.parseInt(ExcelUtils.getCellValue(px_d)) + (Double.parseDouble(ExcelUtils.getCellValue(px_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(px_s))/3600);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", lonDd);
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    		vo.setLondd(lonDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(18); //당시현황
    	if(cell!=null) {
    		memo.put("당시현황", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("당시현황", "");
    	}
    	
    	cell = row.getCell(19); //지정사유
    	if(cell!=null) {
    		memo.put("지정사유", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("지정사유", "");
    	}
    	
    	cell = row.getCell(20); //사업종가능
    	if(cell!=null) {
    		memo.put("사업종가능", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("사업종가능", "");
    	}
    	
    	cell = row.getCell(21); //가능여부
    	if(cell!=null) {
    		memo.put("가능여부", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("가능여부", "");
    	}
    	
    	cell = row.getCell(22); //선정사유
    	if(cell!=null) {
    		memo.put("선정사유", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("선정사유", "");
    	}
    	
    	cell = row.getCell(23); //구역설정
    	if(cell!=null) {
    		memo.put("구역설정", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("구역설정", "");
    	}
    	
    	cell = row.getCell(24); //당시종합의견
    	if(cell!=null) {
    		memo.put("당시종합의견", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("당시종합의견", "");
    	}
    	
    	
		vo.setSvy_attr(attr.toString());
		vo.setSvy_memo(memo.toString());
		
        vo.setLatdd(latDd);
        vo.setLondd(lonDd);
        
		return vo;
	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectLssCnlFieldBookMaxYear() throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookMaxYear();
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssCnlFieldBookYear() throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookYear();
	}
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssCnlFieldBookCheckMstName(String mst_partname) throws Exception {
		return lssCnlFieldBookDAO.selectLssCnlFieldBookCheckMstName(mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 */
	@Override
	public LssCnlFieldBookItemVO selectFieldBookDetailOne(LssCnlFieldBookItemVO searchVO) throws Exception {
		return lssCnlFieldBookDAO.selectFieldBookDetailOne(searchVO);
	}
	
	/**
	 * 공유방 단건 수정
	 */
	@Override
	public void updateFieldBookDetailOne(LssCnlFieldBookItemVO searchVO) throws Exception {
		lssCnlFieldBookDAO.updateFieldBookDetailOne(searchVO);
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = lssCnlFieldBookDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * 공유방 단건 삭제
	 */
	@Override
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception {
		lssCnlFieldBookDAO.deleteFieldBookDetailOne(map);
	}
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectLssCnlProjBessel(HashMap<String, Object> map) throws Exception{
		return lssCnlFieldBookDAO.selectLssCnlProjBessel(map);
	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception {
		return lssCnlFieldBookDAO.selectOrgchtList();
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssCnlFieldBookVO vo) throws Exception {
		return lssCnlFieldBookDAO.selectMberList(vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	@Override
	public List<LssCnlFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception {
		return lssCnlFieldBookDAO.selectCnrsAuthorList(map);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서목록
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return lssCnlFieldBookDAO.selectDeptInfoList();
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서별 회원목록
	 */
	@Override
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return lssCnlFieldBookDAO.selectDeptCreatList();
	}
	
	/**
	 * 공유방 사용자 권한 수정
	 * @throws Exception
	 */
	public void updateCnrsSpceAuthorMgt(LssCnlFieldBookVO fieldBookVO) throws Exception{
		
		List<LssCnlFieldBookVO> list = new ArrayList<LssCnlFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		for(int i =0; i<authorEsntlIdList.length;i++) {
			if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
				LssCnlFieldBookVO vo = new LssCnlFieldBookVO();
				vo.setId(fieldBookVO.getId());
				vo.setSvytype(fieldBookVO.getSvytype());
				vo.setEsntl_id(authorEsntlIdList[i].toString());
				vo.setUser_grade("USER");
				list.add(vo);				
			}
		}		
		lssCnlFieldBookDAO.deleteCnrsSpceAuthorMgt(fieldBookVO);
		if(list.size() != 0) {
			lssCnlFieldBookDAO.insertCnrsSpceAuthorMgt(list);
		}
	}
}
