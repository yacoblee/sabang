package or.sabang.sys.fck.pcs.service.impl;

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
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;
import or.sabang.sys.fck.pcs.service.FckPcsFieldBookItemVO;
import or.sabang.sys.fck.pcs.service.FckPcsFieldBookService;
import or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO;
import or.sabang.sys.fck.pcs.service.FckPcsStripLandVO;
import or.sabang.sys.fck.pcs.service.FckPcsWorkBookConvt;
import or.sabang.utl.ExcelUtils;

@Service("fckPcsFieldBookService")
public class FckPcsFieldBookServiceImpl extends EgovAbstractServiceImpl implements FckPcsFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FckPcsFieldBookServiceImpl.class);
	
	@Resource(name="fckPcsFieldBookDAO")
	private FckPcsFieldBookDAO fckPcsFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;

	

	/**
	 * 회원 목록조회
	 */
	@Override
	public List<EgovMap> selectMberList(FckPcsFieldBookVO vo) throws Exception {
		// TODO Auto-generated method stub
		return fckPcsFieldBookDAO.selectMberList(vo);
	}
	
	/** 
	 *  조직도 조회
	 */
	@Override
	public List<?> selectOrgchtList() throws Exception {
		return fckPcsFieldBookDAO.selectOrgchtList();
	}
	/**
	 * 부서목록
	 */
	@Override
	public List<EgovMap> selectDeptInfoList() throws Exception {
		// TODO Auto-generated method stub
		return fckPcsFieldBookDAO.selectDeptInfoList();
	}
	/**
	 * 부서별 회원목록
	 */
	@Override
	public List<EgovMap> selectDeptCreatList() throws Exception {
		// TODO Auto-generated method stub
		return fckPcsFieldBookDAO.selectDeptCreatList();
	}

	@Override
	public List<FckPcsFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return fckPcsFieldBookDAO.selectCnrsAuthorList(map);
	}

	/**
	 * 사업지구명 중복 조회
	 * @author DEVWORK
	 * @param mst_partname
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 29.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookCheckMstName(java.lang.String)
	 */
	@Override
	public int selectFckPcsFieldBookCheckMstName(String mst_partname) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookCheckMstName(mst_partname);
	}

	/**
	 * 공유방 등록
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @return
	 * @since 2023. 11. 29.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#insertCnrsSpce(or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO)
	 */
	public String insertCnrsSpce(FckPcsFieldBookVO fieldBookVO) {
		fckPcsFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}

	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @since 2023. 11. 29.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#insertCnrsSpceAuthorMgt(or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO)
	 */
	@Override
	public void insertCnrsSpceAuthorMgt(FckPcsFieldBookVO fieldBookVO) {
		List<FckPcsFieldBookVO> list = new ArrayList<FckPcsFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();

		if(authorEsntlIdList != null) {
			for(int i =0; i<authorEsntlIdList.length;i++) {
				if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
					FckPcsFieldBookVO vo = new FckPcsFieldBookVO();
					vo.setId(fieldBookVO.getId());
					vo.setSvytype(fieldBookVO.getSvytype());
					vo.setEsntl_id(authorEsntlIdList[i].toString());
					vo.setUser_grade("USER");
					list.add(vo);				
				}
			}			
		}
		
		// 생성자 추가
		FckPcsFieldBookVO vo = new FckPcsFieldBookVO();
		vo.setId(fieldBookVO.getId());
		vo.setSvytype(fieldBookVO.getSvytype());
		vo.setEsntl_id(fieldBookVO.getMst_admin_id());
		vo.setUser_grade("ADMIN");
		list.add(vo);
		
		fckPcsFieldBookDAO.insertCnrsSpceAuthorMgt(list);		
	}

	/**
	 * 대상지 등록
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @param mFile
	 * @return
	 * @since 2023. 11. 29.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#insertStripLand(or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO, org.springframework.web.multipart.MultipartFile)
	 */
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(FckPcsFieldBookVO fieldBookVO, MultipartFile mFile) throws Exception {
		List<FckPcsFieldBookItemVO> list = new ArrayList<FckPcsFieldBookItemVO>();

		String user = fieldBookVO.getMst_create_user();
		String type = fieldBookVO.getSvy_type();
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		
		InputStream inputStream = mFile.getInputStream();
		
		int sheetCnt = 0;
		int sheetRowCnt = 0;
		int rowsCnt = 0;
		
		if(extention.matches("xlsx")) {
			XSSFWorkbook xssfWB = null;// = (XSSFWorkbook) excelZipService.loadWorkbook(inputStream,null);
	    	try {
	    		xssfWB = new XSSFWorkbook(inputStream);

	    	} catch (IOException e) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
	    		LOGGER.debug("=====>>>>> ERR : IOException "+e.getMessage());
	    	} catch (Exception e) {
	    		LOGGER.debug("=====>>>>> ERR : "+e.getMessage());
	    	}
	    	if (xssfWB != null && xssfWB.getNumberOfSheets() > 0) {
	    		
	    		// 시트 갯수 가져오기
	    		sheetCnt = xssfWB.getNumberOfSheets();
	    		sheetRowCnt = 0;
	    		rowsCnt = 0;
	    		
	    		for(int i=0; i < sheetCnt; i++) {
	    			String sheetNm = xssfWB.getSheetName(i).toString(); //시트명
		    		XSSFSheet xssfSheet = xssfWB.getSheetAt(i);  //시트 가져오기
		            XSSFRow xssfRow = xssfSheet.getRow(1); //row 가져오기
		            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
		            
		            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
		            
		            for(int j=1; j<rowsCnt; j++){ //row 루프
		            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
		            	if(row!=null){
		            		FckPcsFieldBookItemVO vo = new FckPcsFieldBookItemVO();

		            		vo.setMST_ID(fieldBookVO.getId());
		            		vo.setLOGIN_ID(user);
		            		vo = FckPcsWorkBookConvt.convertXSSFCellValues(sheetNm,row,vo);
		            		
		            		if(vo.getFID() != 0 && vo.getLABEL() != null) {
		            			list.add(vo);
		            		}
		            	}
		            }
	    		}
	    	}
		}else {
			HSSFWorkbook hssfWB = (HSSFWorkbook) excelZipService.loadWorkbook(inputStream);
			
			if (hssfWB != null && hssfWB.getNumberOfSheets() > 0) {
				
				sheetCnt = hssfWB.getNumberOfSheets();
				sheetRowCnt = 0;
				rowsCnt = 0;
				
				for(int i=0; i < sheetCnt; i++) {
					String sheetNm = hssfWB.getSheetName(i).toString(); // 시트명
					HSSFSheet hssfSheet = hssfWB.getSheetAt(i);  //시트 가져오기
		            HSSFRow hssfRow = hssfSheet.getRow(1); //row 가져오기
		            sheetRowCnt = hssfRow.getPhysicalNumberOfCells(); //cell Cnt
		            
		            rowsCnt=hssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
		            
		            for(int j=1; j<rowsCnt; j++){ //row 루프
		            	
		            	HSSFRow row=hssfSheet.getRow(j); //row 가져오기
		                if(row!=null){
		                	FckPcsFieldBookItemVO vo = new FckPcsFieldBookItemVO();
		                	vo.setMST_ID(fieldBookVO.getId());
		                	vo.setLOGIN_ID(user);
		            		vo = FckPcsWorkBookConvt.convertHSSFCellValues(sheetNm,row,vo);
		                    
		            		if(vo.getFID() != 0 && vo.getLABEL() != null) {
		            			list.add(vo);
		            		}
		                }
		            }
				}
			}
		}
		
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;

		for (FckPcsFieldBookItemVO subVo : list) {
			try {
				fckPcsFieldBookDAO.insertStripLandVO(subVo);
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
	}

	/**
	 * 공유방 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @since 2023. 11. 29.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#deleteCnrsSpce(java.util.HashMap)
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> commandMap) {
		fckPcsFieldBookDAO.deleteCnrsSpce(commandMap);		
	}

	/**
	 * 
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookMaxYear()
	 */
	@Override
	public String selectFckPcsFieldBookMaxYear() throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookMaxYear();
	}

	/**
	 * 공유방 연도목록 조회
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookYear()
	 */
	@Override
	public List<?> selectFckPcsFieldBookYear() throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookYear();
	}

	/**
	 * 공유방 목록 조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookList(or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO)
	 */
	@Override
	public List<FckPcsFieldBookVO> selectFckPcsFieldBookList(FckPcsFieldBookVO searchVO) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookList(searchVO);
	}

	/**
	 * 공유방 갯수 조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookListTotCnt(or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO)
	 */
	@Override
	public int selectFckPcsFieldBookListTotCnt(FckPcsFieldBookVO searchVO) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookListTotCnt(searchVO);
	}

	/**
	 * 공유방 상세조회
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookDetail(java.util.HashMap)
	 */
	@Override
	public FckPcsFieldBookVO selectFckPcsFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookDetail(map);
	}

	/**
	 * 공유방 조사데이터 리스트조회
	 * @author DEVWORK
	 * @param searchItemVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookItemList(or.sabang.sys.fck.pcs.service.FckPcsFieldBookItemVO)
	 */
	@Override
	public List<FckPcsStripLandVO> selectFckPcsFieldBookItemList(FckPcsFieldBookItemVO searchItemVO) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookItemList(searchItemVO);
	}

	/**
	 * 공유방 조사데이터 전체 건수 조회
	 * @author DEVWORK
	 * @param vo
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsFieldBookItemListTotCnt(or.sabang.sys.fck.pcs.service.FckPcsFieldBookItemVO)
	 */
	@Override
	public int selectFckPcsFieldBookItemListTotCnt(FckPcsFieldBookItemVO vo) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsFieldBookItemListTotCnt(vo);
	}

	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectAuthorCheck(java.util.HashMap)
	 */
	@Override
	public String selectAuthorCheck(HashMap<String, Object> commandMap) throws Exception {
		return fckPcsFieldBookDAO.selectAuthorCheck(commandMap);
	}

	/**
	 * 공유방 단건 조회
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFieldBookDetailOne(java.util.HashMap)
	 */
	@Override
	public FckPcsFieldBookItemVO selectFieldBookDetailOne(HashMap<String, Object> commandMap) throws Exception {
		
		FckPcsFieldBookItemVO result = null;
		String type = commandMap.get("type").toString();
		
		if(type.contains("사방댐")) result = fckPcsFieldBookDAO.selectFieldBookDetailOne1(commandMap);
		if(type.contains("계류보전")) result = fckPcsFieldBookDAO.selectFieldBookDetailOne2(commandMap);
		if(type.contains("산지사방")) result = fckPcsFieldBookDAO.selectFieldBookDetailOne3(commandMap);
		if(type.contains("해안침식방지")) result = fckPcsFieldBookDAO.selectFieldBookDetailOne4(commandMap);
		if(type.contains("해안방재림")) result = fckPcsFieldBookDAO.selectFieldBookDetailOne5(commandMap);
		
		return result;
	}

	/**
	 * 공유방 조사데이터 일괄 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#deleteCnrsSpceAllItem(java.util.HashMap)
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> commandMap) throws Exception {
		fckPcsFieldBookDAO.deleteCnrsSpceAllItem(commandMap);
	}

	/**
	 * 공유방 조사완료데이터 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#deleteCnrsSpceComptItem(java.util.HashMap)
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> commandMap) throws Exception {
		fckPcsFieldBookDAO.deleteCnrsSpceComptItem(commandMap);
	}

	/**
	 * 공유방 조사데이터 단건 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#deleteFieldBookDetailOne(java.util.HashMap)
	 */
	@Override
	public void deleteFieldBookDetailOne(HashMap<String, Object> commandMap) throws Exception {
		fckPcsFieldBookDAO.deleteFieldBookDetailOne(commandMap);
	}

	/**
	 * 공유방 권한 수정
	 * @author DEVWORK
	 * @param vo
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#updateCnrsSpceAuthorMgt(or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO)
	 */
	@Override
	public void updateCnrsSpceAuthorMgt(FckPcsFieldBookVO fieldBookVO) throws Exception {
		
		List<FckPcsFieldBookVO> list = new ArrayList<FckPcsFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		for(int i =0; i<authorEsntlIdList.length;i++) {
			if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
				FckPcsFieldBookVO vo = new FckPcsFieldBookVO();
				
				vo.setId(fieldBookVO.getId());
				vo.setSvytype(fieldBookVO.getSvytype());
				vo.setEsntl_id(authorEsntlIdList[i].toString());
				vo.setUser_grade("USER");
				
				list.add(vo);				
			}
		}		
		
		fckPcsFieldBookDAO.deleteCnrsSpceAuthorMgt(fieldBookVO);
		
		if(list.size() != 0) {
			fckPcsFieldBookDAO.insertCnrsSpceAuthorMgt(list);
		}

	}

	/**
	 * 좌표계 변환
	 * @author DEVWORK
	 * @param projMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsProjBessel(java.util.HashMap)
	 */
	@Override
	public List<EgovMap> selectFckPcsProjBessel(HashMap<String, Object> projMap) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsProjBessel(projMap);
	}

	/**
	 * 공유방 조사데이터 수정
	 * @author DEVWORK
	 * @param searchVO
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#updateFieldBookDetailOne(or.sabang.sys.fck.pcs.service.FckPcsFieldBookItemVO)
	 */
	@Override
	public void updateFieldBookDetailOne(FckPcsFieldBookItemVO searchVO) throws Exception {
 		fckPcsFieldBookDAO.updateFieldBookDetailOne(searchVO);
	}

	/**
	 * 정밀점검 조사종류 조회
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsFieldBookService#selectFckPcsSvyType(java.util.HashMap)
	 */
	@Override
	public String selectFckPcsSvyType(HashMap<String, Object> commandMap) throws Exception {
		return fckPcsFieldBookDAO.selectFckPcsSvyType(commandMap);
	}
	
}
