package or.sabang.sys.lss.wka.service.impl;

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
import or.sabang.sys.lss.wka.service.LssWkaFieldBookItemVO;
import or.sabang.sys.lss.wka.service.LssWkaFieldBookService;
import or.sabang.sys.lss.wka.service.LssWkaFieldBookVO;
import or.sabang.sys.lss.wka.service.LssWkaStripLandItemVO;
import or.sabang.sys.lss.wka.service.LssWkaStripLandVO;
import or.sabang.sys.service.SysComptVO;
import or.sabang.utl.ExcelUtils;

@Service("lssWkaFieldBookService")
public class LssWkaFieldBookServiceImpl extends EgovAbstractServiceImpl implements LssWkaFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LssWkaFieldBookServiceImpl.class);
	
	@Resource(name="lssWkaFieldBookDAO")
	private LssWkaFieldBookDAO lssWkaFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/** egovFileIdGnrService */
//	@Resource(name="egovWkaIdGnrService")
//	private EgovIdGnrService idgenService;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<LssWkaFieldBookVO> selectLssWkaFieldBookList(LssWkaFieldBookVO searchVO) throws Exception {		
		return lssWkaFieldBookDAO.selectLssWkaFieldBookList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectLssWkaFieldBookListTotCnt(LssWkaFieldBookVO searchVO) throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookListTotCnt(searchVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@Override
	public LssWkaFieldBookVO selectLssWkaFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookDetail(map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssWkaStripLandVO> selectLssWkaFieldBookItemList(LssWkaFieldBookItemVO searchVO) throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssWkaFieldBookItemListTotCnt(LssWkaFieldBookItemVO searchVO) throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookItemListTotCnt(searchVO);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception {
//		return lssWkaFieldBookDAO.selectCnrsSpceType(map);
//	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(LssWkaFieldBookVO fieldBookVO) throws Exception {
		lssWkaFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	public void insertCnrsSpceAuthorMgt(LssWkaFieldBookVO fieldBookVO) throws Exception {
		List<LssWkaFieldBookVO> list = new ArrayList<LssWkaFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		if(authorEsntlIdList != null) {
			for(int i =0; i<authorEsntlIdList.length;i++) {
				if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
					LssWkaFieldBookVO vo = new LssWkaFieldBookVO();
					vo.setId(fieldBookVO.getId());
					vo.setSvytype(fieldBookVO.getSvytype());
					vo.setEsntl_id(authorEsntlIdList[i].toString());
					vo.setUser_grade("USER");
					list.add(vo);				
				}
			}
		}
		
		// 생성자 추가
		LssWkaFieldBookVO vo = new LssWkaFieldBookVO();
		vo.setId(fieldBookVO.getId());
		vo.setSvytype(fieldBookVO.getSvytype());
		vo.setEsntl_id(fieldBookVO.getMst_admin_id());
		vo.setUser_grade("ADMIN");
		list.add(vo);
		
		lssWkaFieldBookDAO.insertCnrsSpceAuthorMgt(list);
	}

	/**
	 * 대상지를 일괄등록한다.
	 */
	@SuppressWarnings({ "null", "unchecked" })
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(LssWkaFieldBookVO fieldBookVO,MultipartFile mFile) throws Exception {
		List<LssWkaFieldBookItemVO> list = new ArrayList<LssWkaFieldBookItemVO>();
		String user = fieldBookVO.getMst_create_user();
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		int rowsCnt = 0;
		int epsgVal = 4326;
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
	            		LssWkaFieldBookItemVO vo = new LssWkaFieldBookItemVO();
//	            		SysFieldInfoVO sysVO = new SysFieldInfoVO(); 

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
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	
	            	HSSFRow row=hssfSheet.getRow(j); //row 가져오기
	                if(row!=null){
	                	LssWkaFieldBookItemVO vo = new LssWkaFieldBookItemVO();
//	                	SysFieldInfoVO sysVO = new SysFieldInfoVO(); 
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
		LssWkaFieldBookItemVO sysFieldVO = new LssWkaFieldBookItemVO();
		List<LssWkaFieldBookItemVO> sysFieldList = new ArrayList<LssWkaFieldBookItemVO>();
		
		for (LssWkaFieldBookItemVO subVo : list) {
			HashMap<String, Object> map = new HashMap<>();
			try {
				//고유아이디 셋팅
				//String uniqId = idgenService.getNextStringId();
				//subVo.setUniq_id(uniqId);
				//System.out.println("uniqId : "+uniqId);
				/*일반정보 테이블 등록*/
				lssWkaFieldBookDAO.insertStripLandVO(subVo);
				/*공간정보 테이블 등록*/
//				String Geometry = selectLssWkaGeomDataList(subVo);
//				String Geometry =  "POINT("+subVo.getLONDD() + " " + subVo.getLATDD()+")";
//				sysFieldVO.setMST_ID((subVo.getMST_ID()));
//				sysFieldVO.set_LABEL(subVo.get_LABEL());
//				sysFieldVO.setSvy_id(subVo.get_LABEL());
//				sysFieldVO.setUniq_id(uniqId);
//				sysFieldVO.setSmgeometry(Geometry);
//    			sysFieldList.add(sysFieldVO);
				successCnt++;
			} catch (Exception e) {
				failedIdsArray.put(subVo.getSvy_label());
				returnInsertLog.put("error", e.getMessage());
				LOGGER.error(e.getMessage());
			}
		}

//		if(successCnt == (rowsCnt-1)) {
//	         String query = "mst_id = {1} and uniq_id = '{2}'";
//	         superMapUtils.upsertBulkRecordSet(sysFieldList, "tf_feis_wka_fieldinfo",query,epsgVal);
//	      }
		
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
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
//	@Override
//	public List<LssWkaStripLandVO> selectCnrsSpceSldList(LssWkaStripLandVO stripLandVO) throws Exception {
//		return lssWkaFieldBookDAO.selectCnrsSpceSldList(stripLandVO);
//	}
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return lssWkaFieldBookDAO.selectCnrsSpceSldDetail(id);
	}

	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	@Override
	public void insertCnrsSpceSld(LssWkaFieldBookItemVO itemVO) throws Exception {
		lssWkaFieldBookDAO.insertCnrsSpceSld(itemVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssWkaStripLandVO> selectLssWkaFieldBookItemView(LssWkaFieldBookItemVO searchVO) throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookItemView(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> map) throws Exception {
		lssWkaFieldBookDAO.deleteCnrsSpce(map);
	}

	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception {
		lssWkaFieldBookDAO.deleteCnrsSpceItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception {
		lssWkaFieldBookDAO.deleteCnrsSpceAllItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception {
		lssWkaFieldBookDAO.deleteCnrsSpceComptItem(map);
	}
	
	/**
	 * 기초조사 조사정보 XSSF
	 * @return
	 * @throws Exception
	 */
	private LssWkaFieldBookItemVO convertXSSFCellValues(XSSFRow row,LssWkaFieldBookItemVO vo) throws Exception {
		//FckAprStripLand vo = new FckAprStripLand();
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		List<SysComptVO> list = new ArrayList<SysComptVO>();
		
		String pattern = "[^0-9.]+";
		
		Calendar calendar = Calendar.getInstance();
		
		double latDd = 0;
		double lonDd = 0;
		String svyType = "";
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		
		cell = row.getCell(0);  //기번
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
    	
    	cell = row.getCell(1);  //구분
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "취약지역 실태조사("+ExcelUtils.getCellValue(cell)+")");
    		attrItem.put("NAME","조사유형");
    		svyType = ExcelUtils.getCellValue(cell);
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(2);  //기초ID
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
    	
    	cell = row.getCell(3);  //기초조사 수행연도
    	if(cell!=null) {
    		memo.put("기초조사수행연도", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("기초조사수행연도", "");
    	}
    	
    	cell = row.getCell(4);  //조사연도
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
    	
    	cell = row.getCell(5);  //시도
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
    	
    	cell = row.getCell(10); //관리주체1
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
    	
    	cell = row.getCell(11); //관리주체2
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
    	
    	XSSFCell bpy_d = row.getCell(12);  //시점_위도_도
    	XSSFCell bpy_m = row.getCell(13);  //시점_위도_분
    	XSSFCell bpy_s = row.getCell(14);  //시점_위도_초
    	if(bpy_d != null && bpy_m != null && bpy_s != null) {
    		//시점_위도
    		latDd = Integer.parseInt(ExcelUtils.getCellValue(bpy_d)) + (Double.parseDouble(ExcelUtils.getCellValue(bpy_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(bpy_s))/3600);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", latDd);
    		attrItem.put("NAME","시점_위도");
    		attr.put(attrItem);
    		vo.setLatdd(latDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점_위도");
    		attr.put(attrItem);
    	}
    	
    	XSSFCell bpx_d = row.getCell(15);  //시점_경도_도
    	XSSFCell bpx_m = row.getCell(16);  //시점_경도_분
    	XSSFCell bpx_s = row.getCell(17);  //시점_경도_초
    	if(bpx_d != null && bpx_m != null && bpx_s != null) {
    		//시점_경도
    		lonDd = Integer.parseInt(ExcelUtils.getCellValue(bpx_d)) + (Double.parseDouble(ExcelUtils.getCellValue(bpx_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(bpx_s))/3600);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", lonDd);
    		attrItem.put("NAME","시점_경도");
    		attr.put(attrItem);
    		vo.setLondd(lonDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점_경도");
    		attr.put(attrItem);
    	}
    	
    	XSSFCell epy_d = row.getCell(18);  //종점_위도_도
    	XSSFCell epy_m = row.getCell(19);  //종점_위도_분
    	XSSFCell epy_s = row.getCell(20);  //종점_위도_초
    	if(epy_d != null && epy_m != null && epy_s != null) {
    		//종점_위도
    		latDd = Integer.parseInt(ExcelUtils.getCellValue(epy_d)) + (Double.parseDouble(ExcelUtils.getCellValue(epy_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(epy_s))/3600);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", latDd);
    		attrItem.put("NAME","종점_위도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점_위도");
    		attr.put(attrItem);
    	}
    	
    	XSSFCell epx_d = row.getCell(21);  //종점_경도_도
    	XSSFCell epx_m = row.getCell(22);  //종점_경도_분
    	XSSFCell epx_s = row.getCell(23);  //종점_경도_초
    	if(epx_d != null && epx_m != null && epx_s != null) {
    		//종점_경도
    		lonDd = Integer.parseInt(ExcelUtils.getCellValue(epx_d)) + (Double.parseDouble(ExcelUtils.getCellValue(epx_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(epx_s))/3600);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", lonDd);
    		attrItem.put("NAME","종점_경도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점_경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(24); //토심
    	if(cell!=null) {
    		memo.put("토심", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("토심", "");
    	}
    	
    	cell = row.getCell(25); //산사태위험도 - 산사태위험등급 현황으로 매칭
    	if(cell!=null) {
    		String sldSttus =  ExcelUtils.getCellValue(cell);
    		if(svyType.equals("산사태")) {
    			if(sldSttus.equals("1")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 1등급");
    				memo.put("산사태위험등급_점수", "5");
    			}else if(sldSttus.equals("2")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 2등급(50% 이상)");
    				memo.put("산사태위험등급_점수", "4");
    			}else if(sldSttus.equals("2.5")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 2등급(50% 미만)");
    				memo.put("산사태위험등급_점수", "3");
    			}else if(sldSttus.equals("3")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 3등급");//3,4등급을 전자야장과 통일 2023-05-10
    				memo.put("산사태위험등급_점수", "2");
    			}else if(sldSttus.equals("4")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 4등급");//3,4등급을 전자야장과 통일 2023-05-10
    				memo.put("산사태위험등급_점수", "2");
    			}else if(sldSttus.equals("5")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 5등급");
    				memo.put("산사태위험등급_점수", "1");    				
    			}else {
    				memo.put("산사태위험등급 현황", "");
    				memo.put("산사태위험등급_점수", "");
    			}
    				
    		}else if(svyType.equals("토석류")){
    			if(sldSttus.equals("1")) {
    				memo.put("황폐발생원", "1등급");    			
//    				memo.put("산사태위험등급 현황", "1등급");
    				memo.put("황폐발생원_점수", "3");    			    				
    			}else if(sldSttus.equals("2")) {
    				memo.put("황폐발생원", "2등급 50% 이상");    			
//    				memo.put("산사태위험등급 현황", "2등급 50% 이상");
    				memo.put("황폐발생원_점수", "2");
    			}else if(sldSttus.equals("2.5")) {
    				memo.put("황폐발생원", "2등급 50% 미만");    			
//    				memo.put("산사태위험등급 현황", "2등급 50% 미만");
    				memo.put("황폐발생원_점수", "1");
    			}else if(sldSttus.equals("3")) {
    				memo.put("황폐발생원", "3등급 이하");    			
//    				memo.put("산사태위험등급 현황", "3등급 이하");
    				memo.put("황폐발생원_점수", "0");
    			}else {
    				memo.put("황폐발생원", "");    			
//    				memo.put("산사태위험등급 현황", "");
    				memo.put("황폐발생원_점수", "");
    			}	
    		}else {
    			memo.put("산사태위험도", "");
    		}
    	}else {
    		memo.put("산사태위험도", "");
    	}
    	
    	cell = row.getCell(26); //지질도 - 모암으로 매칭
    	if(cell!=null) {
    		String prntRock = ExcelUtils.getCellValue(cell);
    		if(svyType.equals("산사태")) {
    			if(prntRock == "퇴적암" || prntRock == "석회암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "1");
    			}else if(prntRock == "화강암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "2");
    			}else if(prntRock == "천매암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "3");
    			}else if(prntRock == "편암" || prntRock == "편마암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "5");
    			}else if(prntRock == "화성암" || prntRock == "안산암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "7");
    			}else {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "");
    			}

    		}else if(svyType.equals("토석류")) {
    			memo.put("모암", "");    			
    		}
    	}else {
    		memo.put("모암", "");
    	}
    	
    	cell = row.getCell(27); //토성
    	if(cell!=null) {
    		memo.put("토성", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("토성", "");
    	}
    	
    	cell = row.getCell(28); //사면길이
    	if(cell!=null) {
    		memo.put("사면길이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("사면길이", "");
    	}
    	
    	cell = row.getCell(29); //최저높이
    	if(cell!=null) {
    		memo.put("최저높이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("최저높이", "");
    	}
    	
    	cell = row.getCell(30); //최고높이
    	if(cell!=null) {
    		memo.put("최고높이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("최고높이", "");
    	}
    	
    	cell = row.getCell(31); //고도차
    	if(cell!=null) {
    		String dirhg = String.valueOf((int)cell.getNumericCellValue());
    		memo.put("고도차", dirhg);
    	}else {
    		memo.put("고도차", "");
    	}
    	
    	cell = row.getCell(32); //건기
    	if(cell!=null) {
    		memo.put("건기", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("건기", "");
    	}
    	
    	cell = row.getCell(33); //우기
    	if(cell!=null) {
    		memo.put("우기", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("우기", "");
    	}
    	
    	cell = row.getCell(34); //1회 토석류량
    	if(cell!=null) {
    		memo.put("1회 토석류량", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("1회 토석류량", "");
    	}
    	
    	cell = row.getCell(35); //정지조건
    	if(cell!=null) {
    		memo.put("정지조건", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("정지조건", "");
    	}
    	
    	cell = row.getCell(36); //가중치
    	if(cell!=null) {
    		memo.put("가중치", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("가중치", "");
    	}
    	
    	cell = row.getCell(37); //전체 토석류량
    	if(cell!=null) {
    		memo.put("전체 토석류량", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("전체 토석류량", "");
    	}
    	
    	cell = row.getCell(38); //유역면적
    	if(cell!=null) {
    		memo.put("유역면적", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("유역면적", "");
    	}
    	
    	cell = row.getCell(39); //계류길이
    	if(cell!=null) {
    		memo.put("계류길이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("계류길이", "");
    	}
    	
		vo.setSvy_attr(attr.toString());
		vo.setSvy_memo(memo.toString());
        
		return vo;
	}
	
	/**
	 * 기초조사 조사정보 HSSF
	 * @return
	 * @throws Exception
	 */
	private LssWkaFieldBookItemVO convertHSSFCellValues(HSSFRow row,LssWkaFieldBookItemVO vo) throws Exception {
		//FckAprStripLand vo = new FckAprStripLand();
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		List<SysComptVO> list = new ArrayList<SysComptVO>();
		
		String pattern = "[^0-9.]+";
		
		Calendar calendar = Calendar.getInstance();
		
		double latDd = 0;
		double lonDd = 0;
		String svyType = "";
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		HSSFCell cell = null;
		
		cell = row.getCell(0);  //기번
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
    	
    	cell = row.getCell(1);  //구분
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "취약지역 실태조사("+ExcelUtils.getCellValue(cell)+")");
    		attrItem.put("NAME","조사유형");
    		svyType = ExcelUtils.getCellValue(cell);
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사유형");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(2);  //기초ID
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
    	
    	cell = row.getCell(3);  //기초조사 수행연도
    	if(cell!=null) {
    		memo.put("기초조사수행연도", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("기초조사수행연도", "");
    	}
    	
    	cell = row.getCell(4);  //조사연도
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
    	
    	cell = row.getCell(5);  //시도
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
    	
    	cell = row.getCell(10); //관리주체1
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
    	
    	cell = row.getCell(11); //관리주체2
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
    	
    	HSSFCell bpy_d = row.getCell(12);  //시점_위도_도
    	HSSFCell bpy_m = row.getCell(13);  //시점_위도_분
    	HSSFCell bpy_s = row.getCell(14);  //시점_위도_초
    	if(bpy_d != null && bpy_m != null && bpy_s != null) {
    		//시점_위도
    		latDd = Integer.parseInt(ExcelUtils.getCellValue(bpy_d)) + (Double.parseDouble(ExcelUtils.getCellValue(bpy_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(bpy_s))/3600);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", latDd);
    		attrItem.put("NAME","시점_위도");
    		attr.put(attrItem);
    		vo.setLatdd(latDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점_위도");
    		attr.put(attrItem);
    	}
    	
    	HSSFCell bpx_d = row.getCell(15);  //시점_경도_도
    	HSSFCell bpx_m = row.getCell(16);  //시점_경도_분
    	HSSFCell bpx_s = row.getCell(17);  //시점_경도_초
    	if(bpx_d != null && bpx_m != null && bpx_s != null) {
    		//시점_경도
    		lonDd = Integer.parseInt(ExcelUtils.getCellValue(bpx_d)) + (Double.parseDouble(ExcelUtils.getCellValue(bpx_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(bpx_s))/3600);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", lonDd);
    		attrItem.put("NAME","시점_경도");
    		attr.put(attrItem);
    		vo.setLondd(lonDd);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점_경도");
    		attr.put(attrItem);
    	}
    	
    	HSSFCell epy_d = row.getCell(18);  //종점_위도_도
    	HSSFCell epy_m = row.getCell(19);  //종점_위도_분
    	HSSFCell epy_s = row.getCell(20);  //종점_위도_초
    	if(epy_d != null && epy_m != null && epy_s != null) {
    		//종점_위도
    		latDd = Integer.parseInt(ExcelUtils.getCellValue(epy_d)) + (Double.parseDouble(ExcelUtils.getCellValue(epy_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(epy_s))/3600);
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", latDd);
    		attrItem.put("NAME","종점_위도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점_위도");
    		attr.put(attrItem);
    	}
    	
    	HSSFCell epx_d = row.getCell(21);  //종점_경도_도
    	HSSFCell epx_m = row.getCell(22);  //종점_경도_분
    	HSSFCell epx_s = row.getCell(23);  //종점_경도_초
    	if(epx_d != null && epx_m != null && epx_s != null) {
    		//종점_경도
    		lonDd = Integer.parseInt(ExcelUtils.getCellValue(epx_d)) + (Double.parseDouble(ExcelUtils.getCellValue(epx_m))/60) + (Double.parseDouble(ExcelUtils.getCellValue(epx_s))/3600);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", lonDd);
    		attrItem.put("NAME","종점_경도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점_경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(24); //토심
    	if(cell!=null) {
    		memo.put("토심", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("토심", "");
    	}
    	
    	cell = row.getCell(25); //산사태위험도 - 산사태위험등급 현황으로 매칭
    	if(cell!=null) {
    		String sldSttus =  ExcelUtils.getCellValue(cell);
    		if(svyType.equals("산사태")) {
    			if(sldSttus.equals("1")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 1등급");
    				memo.put("산사태위험등급_점수", "5");
    			}else if(sldSttus.equals("2")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 2등급(50% 이상)");
    				memo.put("산사태위험등급_점수", "4");
    			}else if(sldSttus.equals("2.5")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 2등급(50% 미만)");
    				memo.put("산사태위험등급_점수", "3");
    			}else if(sldSttus.equals("3")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 3,4등급");
    				memo.put("산사태위험등급_점수", "2");
    			}else if(sldSttus.equals("4")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 3,4등급");
    				memo.put("산사태위험등급_점수", "2");
    			}else if(sldSttus.equals("5")) {
    				memo.put("산사태위험등급 현황", "산사태위험등급 5등급");
    				memo.put("산사태위험등급_점수", "1");    				
    			}else {
    				memo.put("산사태위험등급 현황", "");
    				memo.put("산사태위험등급_점수", "");
    			}
    				
    		}else if(svyType.equals("토석류")){
    			if(sldSttus.equals("1")) {
    				memo.put("황폐발생원", "1등급");    			
    				memo.put("산사태위험등급 현황", "1등급");
    				memo.put("산사태위험등급_점수", "3");    			    				
    			}else if(sldSttus.equals("2")) {
    				memo.put("황폐발생원", "2등급 50% 이상");    			
    				memo.put("산사태위험등급 현황", "2등급 50% 이상");
    				memo.put("산사태위험등급_점수", "2");
    			}else if(sldSttus.equals("2.5")) {
    				memo.put("황폐발생원", "2등급 50% 미만");    			
    				memo.put("산사태위험등급 현황", "2등급 50% 미만");
    				memo.put("산사태위험등급_점수", "1");
    			}else if(sldSttus.equals("3") || sldSttus.equals("4")) {
    				memo.put("황폐발생원", "3등급 이하");    			
    				memo.put("산사태위험등급 현황", "3등급 이하");
    				memo.put("산사태위험등급_점수", "0");
    			}else {
    				memo.put("황폐발생원", "");    			
    				memo.put("산사태위험등급 현황", "");
    				memo.put("산사태위험등급_점수", "");
    			}	
    		}else {
    			memo.put("산사태위험등급 현황", "");
    			memo.put("산사태위험등급 현황", "");    			
    		}
    	}else {
    		memo.put("산사태위험등급 현황", "");
    	}
    	
    	cell = row.getCell(26); //지질도 - 모암으로 매칭
    	if(cell!=null) {
    		String prntRock = ExcelUtils.getCellValue(cell);
    		if(svyType.equals("산사태")) {
    			if(prntRock == "퇴적암" || prntRock == "석회암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "1");
    			}else if(prntRock == "화강암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "2");
    			}else if(prntRock == "천매암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "3");
    			}else if(prntRock == "편암" || prntRock == "편마암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "5");
    			}else if(prntRock == "화성암" || prntRock == "안산암" || prntRock == "반암") {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "7");
    			}else {
    				memo.put("모암", prntRock);
    				memo.put("암석종류", prntRock);
    				memo.put("암석종류_점수", "");
    			}

    		}else if(svyType.equals("토석류")) {
    			memo.put("모암", "");    			
    		}
    	}else {
    		memo.put("모암", "");
    	}
    	
    	cell = row.getCell(27); //토성
    	if(cell!=null) {
    		memo.put("토성", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("토성", "");
    	}
    	
    	cell = row.getCell(28); //사면길이
    	if(cell!=null) {
    		memo.put("사면길이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("사면길이", "");
    	}
    	
    	cell = row.getCell(29); //최저높이
    	if(cell!=null) {
    		memo.put("최저높이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("최저높이", "");
    	}
    	
    	cell = row.getCell(30); //최고높이
    	if(cell!=null) {
    		memo.put("최고높이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("최고높이", "");
    	}
    	
    	cell = row.getCell(31); //고도차
    	if(cell!=null) {
    		String dirhg = String.valueOf((int)cell.getNumericCellValue());
    		memo.put("고도차", dirhg);
    	}else {
    		memo.put("고도차", "");
    	}
    	
    	cell = row.getCell(32); //건기
    	if(cell!=null) {
    		memo.put("건기", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("건기", "");
    	}
    	
    	cell = row.getCell(33); //우기
    	if(cell!=null) {
    		memo.put("우기", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("우기", "");
    	}
    	
    	cell = row.getCell(34); //1회 토석류량
    	if(cell!=null) {
    		memo.put("1회 토석류량", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("1회 토석류량", "");
    	}
    	
    	cell = row.getCell(35); //정지조건
    	if(cell!=null) {
    		memo.put("정지조건", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("정지조건", "");
    	}
    	
    	cell = row.getCell(36); //가중치
    	if(cell!=null) {
    		memo.put("가중치", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("가중치", "");
    	}
    	
    	cell = row.getCell(37); //전체 토석류량
    	if(cell!=null) {
    		memo.put("전체 토석류량", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("전체 토석류량", "");
    	}
    	
    	cell = row.getCell(38); //유역면적
    	if(cell!=null) {
    		memo.put("유역면적", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("유역면적", "");
    	}
    	
    	cell = row.getCell(39); //계류길이
    	if(cell!=null) {
    		memo.put("계류길이", ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("계류길이", "");
    	}
    	
		vo.setSvy_attr(attr.toString());
		vo.setSvy_memo(memo.toString());
        
		return vo;
	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectLssWkaFieldBookMaxYear() throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookMaxYear();
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssWkaFieldBookYear() throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookYear();
	}
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssWkaFieldBookCheckMstName(String mst_partname) throws Exception {
		return lssWkaFieldBookDAO.selectLssWkaFieldBookCheckMstName(mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 * @throws Exception
	 */
	public LssWkaStripLandItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		return lssWkaFieldBookDAO.selectFieldBookDetailOne(map);
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = lssWkaFieldBookDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * 공유방 단건 수정
	 * @throws Exception
	 */
	public void updateFieldBookDetailOne(LssWkaStripLandItemVO vo) throws Exception{
		
		//좌표 경위도로 변경
		//svy_lat,svy_lon,smgeometry,attr 시점,종점좌표 변경
		
		String sLatDeg = vo.getSpLatDeg();
		String sLatMin = vo.getSpLatMin();
		String sLatSec = vo.getSpLatSec();
		
		String sLonDeg = vo.getSpLonDeg();
		String sLonMin = vo.getSpLonMin();
		String sLonSec = vo.getSpLonSec();
		
		String eLatDeg = vo.getEpLatDeg();
		String eLatMin = vo.getEpLatMin();
		String eLatSec = vo.getEpLatSec();
		
		String eLonDeg = vo.getEpLonDeg();
		String eLonMin = vo.getEpLonMin();
		String eLonSec = vo.getEpLonSec();
		
		String svyType = vo.getSvyType();
		String lnddgr = vo.getLndDgr();
		String prntRock = vo.getPrntRock();
		
		Double sLatDecimal = Integer.valueOf(sLatDeg) + (Double.parseDouble(sLatMin)/60) + (Double.parseDouble(sLatSec)/3600);
		Double sLonDecimal = Integer.valueOf(sLonDeg) + (Double.parseDouble(sLonMin)/60) + (Double.parseDouble(sLonSec)/3600);
		
		Double eLatDecimal = Integer.valueOf(eLatDeg) + (Double.parseDouble(eLatMin)/60) + (Double.parseDouble(eLatSec)/3600);
		Double eLonDecimal = Integer.valueOf(eLonDeg) + (Double.parseDouble(eLonMin)/60) + (Double.parseDouble(eLonSec)/3600);
		
		vo.setsLatDecimal(String.valueOf(sLatDecimal));
		vo.setsLonDecimal(String.valueOf(sLonDecimal));
		vo.seteLatDecimal(String.valueOf(eLatDecimal));
		vo.seteLonDecimal(String.valueOf(eLonDecimal));
		
		
		if(svyType.equals("산사태")) {
			if(lnddgr.equals("1")) {
				vo.setLndDgrGrd("산사태위험등급 1등급");
				vo.setLndDgrGrdScore("5");
			}else if(lnddgr.equals("2")) {
				vo.setLndDgrGrd("산사태위험등급 2등급(50% 이상)");
				vo.setLndDgrGrdScore("4");
			}else if(lnddgr.equals("2.5")) {
				vo.setLndDgrGrd("산사태위험등급 2등급(50% 미만)");
				vo.setLndDgrGrdScore("3");
			}else if(lnddgr.equals("3")) {
				vo.setLndDgrGrd("산사태위험등급 3등급");
				vo.setLndDgrGrdScore("2");
			}else if(lnddgr.equals("4")) {
				vo.setLndDgrGrd("산사태위험등급 4등급");
				vo.setLndDgrGrdScore("2");
			}else if(lnddgr.equals("5")) {
				vo.setLndDgrGrd("산사태위험등급 5등급");
				vo.setLndDgrGrdScore("1");
			}
			
			vo.setRockType(prntRock);
			
			if(prntRock.equals("퇴적암") || prntRock.equals("석회암")) {
				vo.setRockTypeScore("1");
			}else if(prntRock.equals("화강암")) {
				vo.setRockTypeScore("2");
			}else if(prntRock.equals("천매암")) {
				vo.setRockTypeScore("3");
			}else if(prntRock.equals("편암") || prntRock.equals("편마암")) {
				vo.setRockTypeScore("5");
			}else if(prntRock.equals("화성암") || prntRock.equals("안산암")) {
				vo.setRockTypeScore("7");
			}
		}else if(svyType.equals("토석류")){
			if(lnddgr.equals("1")) {
				vo.setDevSource("1등급");
//				vo.setLndDgrGrd("1등급");//빠지는게 맞고
				vo.setLndDgrGrdScore("3");//황폐발생원 점수 컬럼 --- 승현아 scodsltnscore 이컬럼이야 황폐발생원 점수
			}else if(lnddgr.equals("2")) {
				vo.setDevSource("2등급 50% 이상");
//				vo.setLndDgrGrd("2등급 50% 이상");
				vo.setLndDgrGrdScore("2");
			}else if(lnddgr.equals("2.5")) {
				vo.setDevSource("2등급 50% 미만");
//				vo.setLndDgrGrd("2등급 50% 미만");
				vo.setLndDgrGrdScore("1");
			}else if(lnddgr.equals("3")) {
				vo.setDevSource("3등급 이하");
//				vo.setLndDgrGrd("3등급 이하");
				vo.setLndDgrGrdScore("0");
			}
		}
		
		lssWkaFieldBookDAO.updateFieldBookDetailOne(vo);
	}
	
	/**
	 * 공유방 단건 삭제
	 */
	@Override
	public void deleteFieldBookDetailOne(LssWkaStripLandItemVO vo) throws Exception {
		lssWkaFieldBookDAO.deleteFieldBookDetailOne(vo);
	}
	
	/**
	 * 공간정보 테이블 위치정보 변환
	 * @throws Exception
	 */
//	public String selectLssWkaGeomDataList(SysFieldInfoVO subVo) throws Exception{
//		return lssWkaFieldBookDAO.selectLssWkaGeomDataList(subVo);
//	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception {
		return lssWkaFieldBookDAO.selectOrgchtList();
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssWkaFieldBookVO vo) throws Exception {
		return lssWkaFieldBookDAO.selectMberList(vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	@Override
	public List<LssWkaFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception {
		return lssWkaFieldBookDAO.selectCnrsAuthorList(map);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서목록
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return lssWkaFieldBookDAO.selectDeptInfoList();
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서별 회원목록
	 */
	@Override
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return lssWkaFieldBookDAO.selectDeptCreatList();
	}
	
	/**
	 * 공유방 사용자 권한 수정
	 * @throws Exception
	 */
	public void updateCnrsSpceAuthorMgt(LssWkaFieldBookVO fieldBookVO) throws Exception{
		
		List<LssWkaFieldBookVO> list = new ArrayList<LssWkaFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		for(int i =0; i<authorEsntlIdList.length;i++) {
			if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
				LssWkaFieldBookVO vo = new LssWkaFieldBookVO();
				vo.setId(fieldBookVO.getId());
				vo.setSvytype(fieldBookVO.getSvytype());
				vo.setEsntl_id(authorEsntlIdList[i].toString());
				vo.setUser_grade("USER");
				list.add(vo);				
			}
		}
		lssWkaFieldBookDAO.deleteCnrsSpceAuthorMgt(fieldBookVO);
		if(list.size() != 0) {
			lssWkaFieldBookDAO.insertCnrsSpceAuthorMgt(list);
		}
	}
}
