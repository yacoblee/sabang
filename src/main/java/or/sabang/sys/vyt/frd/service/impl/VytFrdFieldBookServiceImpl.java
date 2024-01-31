package or.sabang.sys.vyt.frd.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
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

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookItemVO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookService;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.utl.SuperMapBasicUtils;

@Service("vytFrdFieldBookService")
public class VytFrdFieldBookServiceImpl extends EgovAbstractServiceImpl implements VytFrdFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdFieldBookServiceImpl.class);
	private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	
	
	@Resource(name="vytFrdFieldBookDAO")
	private VytFrdFieldBookDAO vytFrdFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
//	@Resource(name="superMapBasicUtils")
//	private SuperMapBasicUtils superMapBasicUtils;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<VytFrdFieldBookVO> selectVytFrdFieldBookList(VytFrdFieldBookVO searchVO) throws Exception {		
		return vytFrdFieldBookDAO.selectVytFrdFieldBookList(searchVO);
	}	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectVytFrdFieldBookListTotCnt(VytFrdFieldBookVO searchVO) throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookListTotCnt(searchVO);
	}
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@Override
	public VytFrdFieldBookVO selectVytFrdFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookDetail(map);
	}
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> map) throws Exception {
		vytFrdFieldBookDAO.deleteCnrsSpce(map);
	}
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception {
		vytFrdFieldBookDAO.deleteCnrsSpceComptItem(map);
	}
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception {
		vytFrdFieldBookDAO.deleteCnrsSpceAllItem(map);
	}
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	@Override
	public List<?> selectVytFrdFieldBookYear() throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookYear();
	}
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectVytFrdFieldBookMaxYear() throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookMaxYear();
	}
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@Override
	public List<VytFrdStripLandVO> selectVytFrdFieldBookItemList(VytFrdFieldBookItemVO searchVO) throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@Override
	public int selectVytFrdFieldBookItemListTotCnt(VytFrdFieldBookItemVO searchVO) throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookItemListTotCnt(searchVO);
	}
	/**
	 * 대상지를 일괄등록한다.
	 */
	@Override
	public void insertStripLand(HashMap<String, Object> commandMap) throws Exception {
		vytFrdFieldBookDAO.insertStripLand(commandMap);
	}

	@Override
	public String insertCnrsSpce(VytFrdFieldBookVO fieldBookVO) throws Exception {
		vytFrdFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}

	@Override
	public int selectVytFrdFieldBookCheckMstName(String mst_partname) throws Exception {
		return vytFrdFieldBookDAO.selectVytFrdFieldBookCheckMstName(mst_partname);
	}

	/**
	 * 임도 조사정보 XSSF
	 * @return
	 * @throws Exception
	 */
	private VytFrdFieldBookItemVO convertXSSFCellValues(XSSFRow row,VytFrdFieldBookItemVO vo) throws Exception {
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		
		String pattern = "[^0-9.]+";
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		
		cell = row.getCell(0);  //일련번호
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","일련번호");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","일련번호");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(1);  //조사ID
    	if(cell!=null){
    		attrItem = new JSONObject();
			
			String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		vo.setSvy_label(svyId);
    		vo.setSvy_keyword(svyId);
			
			attrItem.put("VALUE", svyId);
			attrItem.put("NAME","조사ID");
			attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(2);  //관할
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","관할");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","관할");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(3);  //시도
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(4);  //시군구
    	if(cell!=null){
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
    	
    	cell = row.getCell(5);  //읍면동
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
    	
    	cell = row.getCell(6);  //리
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
    	
    	cell = row.getCell(7);  //지번
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
    	
    	cell = row.getCell(8);  //임도종류
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","임도종류");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","임도종류");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(9);  //예정거리
    	if(cell!=null) {
    		memo.put("예정거리",  ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("예정거리", "");
    	}
    	
    	cell = row.getCell(10);  //시점위도
    	if(cell!=null) {
    		String lat = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] latArray = lat.split(pattern);
    		
    		if(latArray.length == 1) {
    			latDd = Double.parseDouble(latArray[0]);
    		}else {
    			latDd = Integer.parseInt(latArray[0]) + (Double.parseDouble(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시점위도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점위도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(11);  //시점경도
    	if(cell!=null) {
    		String lon = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] lonArray = lon.split(pattern);
    		
    		if(lonArray.length == 1) {
    			lonDd = Double.parseDouble(lonArray[0]);
    		}else {
    			lonDd = Integer.parseInt(lonArray[0]) + (Double.parseDouble(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시점경도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(12);  //종점위도1
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점위도1");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점위도1");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(13);  //종점경도1
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점경도1");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점경도1");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(14);  //종점위도2
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점위도2");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점위도2");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(15);  //종점경도2
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점경도2");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점경도2");
    		attr.put(attrItem);
    	}
    	
		vo.setSvy_attr(attr.toString());
		vo.setSvy_memo(memo.toString());
		
        vo.setLatdd(latDd);
        vo.setLondd(lonDd);
        
		return vo;
	}
	/**
	 * 임도 조사정보 HSSF
	 * @return
	 * @throws Exception
	 */
	private VytFrdFieldBookItemVO convertHSSFCellValues(HSSFRow row,VytFrdFieldBookItemVO vo) throws Exception {
		JSONArray attr = new JSONArray();
		JSONObject memo = new JSONObject();
		JSONObject attrItem = null;
		
		String pattern = "[^0-9.]+";
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		HSSFCell cell = null;
		
		cell = row.getCell(0);  //일련번호
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","일련번호");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","일련번호");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(1);  //조사ID
    	if(cell!=null){
    		attrItem = new JSONObject();
			
			String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		vo.setSvy_label(svyId);
    		vo.setSvy_keyword(svyId);
			
			attrItem.put("VALUE", svyId);
			attrItem.put("NAME","조사ID");
			attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}
		
    	cell = row.getCell(2);  //시도
    	if(cell!=null){
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(3);  //시군구
    	if(cell!=null){
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
    	
    	cell = row.getCell(7);  //임도종류
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","임도종류");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","임도종류");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(8);  //예정거리
    	if(cell!=null) {
    		memo.put("예정거리",  ExcelUtils.getCellValue(cell));
    	}else {
    		memo.put("예정거리", "");
    	}
    	
    	cell = row.getCell(9);  //시점위도
    	if(cell!=null) {
    		String lat = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] latArray = lat.split(pattern);
    		
    		if(latArray.length == 1) {
    			latDd = Double.parseDouble(latArray[0]);
    		}else {
    			latDd = Integer.parseInt(latArray[0]) + (Double.parseDouble(latArray[1])/60) + (Double.parseDouble(latArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시점위도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점위도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(10);  //시점경도
    	if(cell!=null) {
    		String lon = String.valueOf(ExcelUtils.getCellValue(cell));
    		String[] lonArray = lon.split(pattern);
    		
    		if(lonArray.length == 1) {
    			lonDd = Double.parseDouble(lonArray[0]);
    		}else {
    			lonDd = Integer.parseInt(lonArray[0]) + (Double.parseDouble(lonArray[1])/60) + (Double.parseDouble(lonArray[2])/3600);
    		}
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","시점경도");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","시점경도");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(11);  //종점위도1
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점위도1");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점위도1");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(12);  //종점경도1
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점경도1");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점경도1");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(13);  //종점위도2
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점위도2");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점위도2");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(14);  //종점경도2
    	if(cell!=null) {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
    		attrItem.put("NAME","종점경도2");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","종점경도2");
    		attr.put(attrItem);
    	}
    	
		vo.setSvy_attr(attr.toString());
		vo.setSvy_memo(memo.toString());
		
        vo.setLatdd(latDd);
        vo.setLondd(lonDd);
        
		return vo;
	}
	
	/**
	 * 공유방 단건 상세조회
	 */
	@Override
	public VytFrdFieldBookItemVO selectFieldBookDetailOne(VytFrdFieldBookItemVO searchVO) throws Exception {
		return vytFrdFieldBookDAO.selectFieldBookDetailOne(searchVO);
	}
	
	/**
	 * 공유방 단건 수정
	 */
	@Override
	public void updateFieldBookDetailOne(VytFrdFieldBookItemVO searchVO) throws Exception {
		vytFrdFieldBookDAO.updateFieldBookDetailOne(searchVO);
	}
	
	/**
	 * 공유방 단건 삭제
	 */
	@Override
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception {
		vytFrdFieldBookDAO.deleteFieldBookDetailOne(map);
	}
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	@Override
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = vytFrdFieldBookDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	@Override
	public List<VytFrdFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception {
		return vytFrdFieldBookDAO.selectCnrsAuthorList(map);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서목록
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return vytFrdFieldBookDAO.selectDeptInfoList();
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서별 회원목록
	 */
	@Override
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return vytFrdFieldBookDAO.selectDeptCreatList();
	}
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	@Override
	public void insertCnrsSpceAuthorMgt(VytFrdFieldBookVO fieldBookVO) throws Exception {
		List<VytFrdFieldBookVO> list = new ArrayList<VytFrdFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		if(authorEsntlIdList != null) {
			for(int i =0; i<authorEsntlIdList.length;i++) {
				if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
					VytFrdFieldBookVO vo = new VytFrdFieldBookVO();
					vo.setId(fieldBookVO.getId());
					vo.setSvytype(fieldBookVO.getSvytype());
					vo.setEsntl_id(authorEsntlIdList[i].toString());
					vo.setUser_grade("USER");
					list.add(vo);				
				}
			}
		}
		
		// 생성자 추가
		VytFrdFieldBookVO vo = new VytFrdFieldBookVO();
		vo.setId(fieldBookVO.getId());
		vo.setSvytype(fieldBookVO.getSvytype());
		vo.setEsntl_id(fieldBookVO.getMst_admin_id());
		vo.setUser_grade("ADMIN");
		list.add(vo);
		
		vytFrdFieldBookDAO.insertCnrsSpceAuthorMgt(list);
	}
	
	/**
	 * 공유방 사용자 권한 수정
	 * @throws Exception
	 */
	@Override
	public void updateCnrsSpceAuthorMgt(VytFrdFieldBookVO fieldBookVO) throws Exception{
		
		List<VytFrdFieldBookVO> list = new ArrayList<VytFrdFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		for(int i =0; i<authorEsntlIdList.length;i++) {
			if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
				VytFrdFieldBookVO vo = new VytFrdFieldBookVO();
				vo.setId(fieldBookVO.getId());
				vo.setSvytype(fieldBookVO.getSvytype());
				vo.setEsntl_id(authorEsntlIdList[i].toString());
				vo.setUser_grade("USER");
				list.add(vo);				
			}
		}		
		vytFrdFieldBookDAO.deleteCnrsSpceAuthorMgt(fieldBookVO);
		if(list.size() != 0) {
			vytFrdFieldBookDAO.insertCnrsSpceAuthorMgt(list);
		}
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectMberList(VytFrdFieldBookVO vo) throws Exception {
		return vytFrdFieldBookDAO.selectMberList(vo);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 대상지 목록
	 */
	@Override
	public List<EgovMap> selectSldRegInfo() throws Exception{
		return vytFrdFieldBookDAO.selectSldRegInfo();
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 대상지 개수 조회
	 */
	@Override
	public int selectSldInfoCnt(VytFrdFieldBookItemVO fieldBookItemVO) throws Exception {
		return vytFrdFieldBookDAO.selectSldInfoCnt(fieldBookItemVO);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 대상지 사업목록 내용조회 
	 */
	@Override
	public List<VytFrdStripLandVO> selectSldInfo(VytFrdFieldBookItemVO fieldBookItemVO) throws Exception {
		return vytFrdFieldBookDAO.selectSldInfo(fieldBookItemVO);
	}
	
	/**
	 * 공유방 번호 부여
	 * @throws Exception
	 */
	@Override
	public void updateNoMstId(HashMap<String, Object> commandMap) throws Exception {
		vytFrdFieldBookDAO.updateNoMstId(commandMap);
	}
	
	/**
	 * 대상지 삭제 gid 조회
	 * @param fieldBookItemVO 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> selectDeleteItems(String id) throws Exception{
		return vytFrdFieldBookDAO.selectDeleteItems(id);
	}
	
}
