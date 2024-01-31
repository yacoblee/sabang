package or.sabang.sys.vyt.ecb.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
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
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;
import or.sabang.sys.fck.apr.service.FckAprStripLandVO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.ecb.service.VytEcbAlsManageService;
import or.sabang.sys.vyt.ecb.service.VytEcbAnalVO;
import or.sabang.sys.vyt.ecb.service.VytEcbSldManageService;
import or.sabang.sys.vyt.ecb.service.VytEcbStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbWorkVO;
import or.sabang.utl.ExcelUtils;
import or.sabang.sys.fck.apr.service.FckAprFieldBookService;

@Service("vytEcbAlsManageService")
public class VytEcbAlsManageServiceImpl extends EgovAbstractServiceImpl implements VytEcbAlsManageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VytEcbAlsManageServiceImpl.class);
	
	@Resource(name="vytEcbAlsManageDAO")
	private VytEcbAlsManageDAO vytEcbAlsManageDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * 사업 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectVytEcbWorkMaxYear() throws Exception {
		return vytEcbAlsManageDAO.selectVytEcbWorkMaxYear();
	}
	
	/**
	 * 사업 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytEcbWorkYear() throws Exception {
		return vytEcbAlsManageDAO.selectVytEcbWorkYear();
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<VytEcbAnalVO> selectVytEcbWorkList(VytEcbAnalVO searchVO) throws Exception {		
		return vytEcbAlsManageDAO.selectVytEcbWorkList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectVytEcbWorkListTotCnt(VytEcbAnalVO searchVO) throws Exception {
		return vytEcbAlsManageDAO.selectVytEcbWorkListTotCnt(searchVO);
	}
	
	@Override
	public VytEcbAnalVO selectVytEcbWorkDetail(VytEcbAnalVO searchVO) throws Exception {
		return vytEcbAlsManageDAO.selectVytEcbWorkDetail(searchVO);
	}
	
	@Override
	public List<AnalFileVO> selectVytEcbAnalDetail(VytEcbAnalVO searchVO) throws Exception {
		return vytEcbAlsManageDAO.selectVytEcbAnalDetail(searchVO);
	}
	
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 분석결과 파일 전체 다운로드
	 */
	@Override
	public AnalFileVO downloadAnalAll(VytEcbAnalVO searchVO) throws Exception{
		AnalFileVO vo = new AnalFileVO();
		List<AnalFileVO> list = vytEcbAlsManageDAO.selectDownloadAnalAll(searchVO);
		
		String accountId = searchVO.getSearchKeyword();//계정 아이디
		String zipPath = null; //압축파일 전체경로
		String copyPath = null; //복사할 경로
		
		for (int i = 0; i < list.size(); i++) {
			AnalFileVO analFileVO = (AnalFileVO) list.get(i);
			
			if(i == 0) {
				vo.setAnalId(analFileVO.getAnalId());
				vo.setFileStreCours(analFileVO.getFileStreCours());
				vo.setFileExtsn("zip");
				
				zipPath = analFileVO.getFileStreCours()+File.separator+accountId+File.separator+analFileVO.getAnalId()+".zip"; //압축파일 전체경로
				copyPath = analFileVO.getFileStreCours()+File.separator+accountId; //복사할 경로
				
//				boolean checkFile = EgovFileUtil.isExistsFile(zipPath); //기존에 압축파일이 존재하는지 확인
//				if(checkFile) {
//					EgovFileUtil.rm(zipPath); //기존 압축파일 삭제
//				}
				
				File copyFile = new File(copyPath); //copy할 폴더
				if(copyFile.exists()) {
					FileUtils.deleteDirectory(copyFile);
					//EgovFileUtil.rm(copyPath); //copy 폴더 존재할 경우 삭제
				}
				
				//copyFile.mkdirs(); //copy 폴더 생성
			}
			
			String source = analFileVO.getFileStreCours()+File.separator+analFileVO.getStreFileNm()+".".concat(analFileVO.getFileExtsn());
			String target = copyPath+File.separator+"copy"+File.separator+analFileVO.getOrignlFileNm()+".".concat(analFileVO.getFileExtsn());
			EgovFileUtil.cp(source, target);
		}
		
		if(zipPath != null && copyPath != null) {
			boolean isCompressed = EgovFileCmprs.cmprsFile(copyPath+File.separator+"copy", zipPath);
//			if(isCompressed) {
//				EgovFileUtil.rm(copyPath);
//			}
		}
		return vo;
	}
	
	/**
	 * 분석결과 통계정보유무
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectVytEcbAnalStatDataCnt(VytEcbAnalVO searchVO) throws Exception {
		return vytEcbAlsManageDAO.selectVytEcbAnalStatDataCnt(searchVO);
	}
	
	/**
	 * 분석결과 통계정보 엑셀 다운로드
	 */
	@Override
	public Map<String, Object> selectVytEcbAnalStatDataExcel(VytEcbAnalVO searchVO) throws Exception {
		List<?> _result = vytEcbAlsManageDAO.selectVytEcbAnalStatDataExcel(searchVO);
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}
	
	/**
	 * 지적조회
	 */
	@Override
	public List<EgovMap> selectCadastralDetail(String cadastralWkt) throws Exception{
		return vytEcbAlsManageDAO.selectCadastralDetail(cadastralWkt);
	}
}