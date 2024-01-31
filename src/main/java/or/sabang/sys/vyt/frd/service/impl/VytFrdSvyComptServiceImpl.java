package or.sabang.sys.vyt.frd.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptService;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;
import or.sabang.utl.VytFrdSupMapUtils;

@Service("vytFrdSvyComptService")
public class VytFrdSvyComptServiceImpl extends EgovAbstractServiceImpl implements VytFrdSvyComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdSvyComptServiceImpl.class);
	
	@Resource(name="vytFrdSvyComptDAO")
	private VytFrdSvyComptDAO vytFrdSvyComptDAO;
	
	@Resource(name="vytFrdStripLandDAO")
	private VytFrdStripLandDAO vytFrdStripLandDAO;
	
	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<VytFrdSvyComptVO> selectFrdSvyComptList(VytFrdSvyComptVO searchVO) throws Exception {
		return vytFrdSvyComptDAO.selectFrdSvyComptList(searchVO);
	}
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectFrdSvyComptListTotCnt(VytFrdSvyComptVO searchVO) throws Exception {
        return vytFrdSvyComptDAO.selectFrdSvyComptListTotCnt(searchVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectFrdSvyComptMaxYear() throws Exception {
		return vytFrdSvyComptDAO.selectFrdSvyComptMaxYear();
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 */
	@Override
	public String selectFrdSvyComptMaxMonth() throws Exception {
		return vytFrdSvyComptDAO.selectFrdSvyComptMaxMonth();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 */
	@Override
	public List<?> selectFrdSvyComptYear() throws Exception{
		return vytFrdSvyComptDAO.selectFrdSvyComptYear();
	}
	
	/**
	 * 대상지관리 고유번호(smid)를 조회한다.
	 */
	@Override
	public List<EgovMap> selectSldNumber(HashMap<String, String> numberMap) throws Exception {
        return vytFrdSvyComptDAO.selectSldNumber(numberMap);
	}
	
	/**
	 * 대상지전자야장연계 고유번호(gid)를 조회한다.
	 */
	@Override
	public String selectFieldBookNumber(HashMap<String, String> numberMap) throws Exception {
		return vytFrdSvyComptDAO.selectFieldBookNumber(numberMap);
	}
	
	/**
	 * 조사완료 상세조회
	 * @throws Exception
	 */
	@Override
	public VytFrdSvyComptVO selectFrdSvyComptDetail(VytFrdSvyComptVO searchVO) throws Exception{
		return vytFrdSvyComptDAO.selectFrdSvyComptDetail(searchVO);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectVytFrdProjDMS(HashMap<String, Object> map) throws Exception{
		return vytFrdSvyComptDAO.selectVytFrdProjDMS(map);
	}
	
	/**
	 * 타당성평가라인 중심 좌표를 조회한다.
	 */
	@Override
	public List<EgovMap> selectVytFrdLineCntPnt(HashMap<String, String> numberMap) throws Exception {
        return vytFrdSvyComptDAO.selectVytFrdLineCntPnt(numberMap);
	}
	
	/**
	 * 추가SHP파일 등록
	 * @throws Exception
	 */
	@Override
	public void updateExtraShp(HashMap<String, Object> commandMap) throws Exception {
		vytFrdSvyComptDAO.updateExtraShp(commandMap);
	}
	
	/**
	 * 대상지를 수정한다.
	 */
	@Override
	public void updateFrdSvyCompt(VytFrdSvyComptVO svyComptVO) throws Exception {
		vytFrdSvyComptDAO.updateFrdSvyCompt(svyComptVO);
	}
	
	/**
	 * 기 분석되어 저장된 분석 유형에 맞는 버퍼 사이즈를 조회한다. 
	 */
	@Override
	public List<EgovMap> selectBufferSize(HashMap<String, Object> commandMap) throws Exception {
        return vytFrdSvyComptDAO.selectBufferSize(commandMap);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 분석결과 파일 전체 다운로드
	 */
	@Override
	public AnalFileVO downloadAnalAll(VytFrdSvyComptVO searchVO) throws Exception{
		AnalFileVO vo = new AnalFileVO();
		List<AnalFileVO> list = vytFrdSvyComptDAO.selectDownloadAnalAll(searchVO);
		
		String accountId = searchVO.getSvyUser();//계정 아이디
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
	 * 대상지를 삭제한다.
	 */
	@Override
	public void deleteFrdSvyCompt(VytFrdSvyComptVO svyComptVO) throws Exception {
		vytFrdSvyComptDAO.deleteFrdSvyCompt(svyComptVO);
		
	}
	@Override
	public void deleteFrdAnalFile(VytFrdSvyComptVO svyComptVO) throws Exception {
		vytFrdSvyComptDAO.deleteFrdAnalFile(svyComptVO);
	}
	@Override
	public void deleteFrdLines(VytFrdSvyComptVO svyComptVO) throws Exception {
		vytFrdSvyComptDAO.deleteFrdLines(svyComptVO);
	}
	
	/**
	 * 사진목록 조회
	 * @throws Exception
	 */
	@Override
	public VytFrdSvyComptVO selectDownloadPhotoList(VytFrdSvyComptVO searchVO) throws Exception{
		return vytFrdSvyComptDAO.selectDownloadPhotoList(searchVO);
	}
	
	/*
	 * 대상지 포인트 SHP 조회
	 * @params smid
	 */
	@Override
	public List<AnalFileVO> selectAnalPntInfo(HashMap<String, Object> analParameterMap) throws Exception {
		return vytFrdSvyComptDAO.selectAnalPntInfo(analParameterMap);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 조사항목 포인트 파일 일괄 다운로드
	 */
	@Override
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	public List<AnalFileVO> downloadAnalPntAll(HashMap<String, String> analDataMap) throws Exception{
		List<AnalFileVO> list = null;
		
		//String analId = SuperMapBasicUtils.getDatasetUuid();	//analId
		
		String analType = analDataMap.get("anal_type").toString();
		String smid = analDataMap.get("smid").toString();
		String sldId = analDataMap.get("sldId").toString();
		
		String analId = analDataMap.get("analId").toString();
		
		VytFrdSupMapUtils utils = new VytFrdSupMapUtils(analId);
		
		LOGGER.info(analType+"Point SHP 생성");
		
		String[] key1 = {"보호시설", "종단기울기", "산지경사", "암반노출", "조림지", "벌채지", "석력지","습지", "용출수", "연약지반", "주요수종", "주요식생", "상수원오염", "훼손우려지", "산림재해", "사방시설설치", "사방시설필요"};
		String[] key2 = {"붕괴우려지역", "야생동물"};
		String[] key3 = {"계류종류및현황", "묘지"};
		
		if(analType.contains("보호시설") || analType.contains("종단기울기") || analType.contains("산지경사") || 
				analType.contains("암반노출") || analType.contains("조림지") || analType.contains("벌채지") ||
				analType.contains("석력지") || analType.contains("습지") || analType.contains("용출수") ||
				analType.contains("연약지반") || analType.contains("주요수종") || analType.contains("주요식생")||
				analType.contains("상수원오염") || analType.contains("훼손우려지") || analType.contains("산림재해")||
				analType.contains("사방시설설치") || analType.contains("사방시설필요")
			) {
			String lon1 = analDataMap.get("lon1").toString();
			String lat1 = analDataMap.get("lat1").toString();
			String lon2 = analDataMap.get("lon2").toString();
			String lat2 = analDataMap.get("lat2").toString();
			String data1_key = analDataMap.get("data1_key").toString();
			String data1_value = analDataMap.get("data1_value").toString();
			
			list = utils.createSvyComptPointShp(smid, sldId, lon1, lat1, lon2, lat2, data1_key, data1_value, "", "", "", "", analId, analType);
			insertAnalFileList(list);
			
		}else if(analType.contains("붕괴우려지역") || analType.contains("야생동물")) {
			String lon1 = analDataMap.get("lon1").toString();
			String lat1 = analDataMap.get("lat1").toString();
			String lon2 = analDataMap.get("lon2").toString();
			String lat2 = analDataMap.get("lat2").toString();
			
			String data1_key = analDataMap.get("data1_key").toString();
			String data1_value = analDataMap.get("data1_value").toString();
			String data2_key = analDataMap.get("data2_key").toString();
			String data2_value = analDataMap.get("data2_value").toString();
			
			list = utils.createSvyComptPointShp(smid, sldId, lon1, lat1, lon2, lat2, data1_key, data1_value, data2_key, data2_value, "", "", analId, analType);
			insertAnalFileList(list);
			
		}else if(analType.contains("계류종류및현황") || analType.contains("묘지")) {
			String lon1 = analDataMap.get("lon1").toString();
			String lat1 = analDataMap.get("lat1").toString();
			String lon2 = analDataMap.get("lon2").toString();
			String lat2 = analDataMap.get("lat2").toString();
		
			String data1_key = analDataMap.get("data1_key").toString();
			String data1_value = analDataMap.get("data1_value").toString();
			String data2_key = analDataMap.get("data2_key").toString();
			String data2_value = analDataMap.get("data2_value").toString();
			String data3_key = analDataMap.get("data3_key").toString();
			String data3_value = analDataMap.get("data3_value").toString();
			
			list = utils.createSvyComptPointShp(smid, sldId, lon1, lat1, lon2, lat2, data1_key, data1_value, data2_key, data2_value, data3_key, data3_value, analId, analType);
			insertAnalFileList(list);
		}
		
		utils.closeWorkspace();
		
		return list;
	}
	
	public AnalFileVO downloadAnalPntAllImpl(List<AnalFileVO> list, String accountId) throws Exception{
		AnalFileVO vo = new AnalFileVO();
		
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
				
				File copyFile = new File(copyPath); //copy할 폴더
				if(copyFile.exists()) {
					FileUtils.deleteDirectory(copyFile);
				}
				
			}
			
			String convertKorFileNm = null;
			
			if(analFileVO.getOrignlFileNm().contains("safeFct")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("safeFct", "보호시설");
			}else if(analFileVO.getOrignlFileNm().contains("lonSlope")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("lonSlope", "종단기울기");
			}else if(analFileVO.getOrignlFileNm().contains("mtSlope")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("mtSlope", "산지경사");
			}else if(analFileVO.getOrignlFileNm().contains("rockExpos_")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("rockExpos", "암반노출");
			}else if(analFileVO.getOrignlFileNm().contains("afrst")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("afrst", "조림지");
			}else if(analFileVO.getOrignlFileNm().contains("cutting")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("cutting", "벌채지");
			}else if(analFileVO.getOrignlFileNm().contains("stmi")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("stmi", "석력지");
			}else if(analFileVO.getOrignlFileNm().contains("mrngKind")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("mrngKind", "계류종류및현황");
			}else if(analFileVO.getOrignlFileNm().contains("wetLand")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("wetLand", "습지");
			}else if(analFileVO.getOrignlFileNm().contains("cmtry")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("cmtry", "묘지");
			}else if(analFileVO.getOrignlFileNm().contains("eltnWater")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("eltnWater", "용출수");
			}else if(analFileVO.getOrignlFileNm().contains("sofrtGrnd")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("sofrtGrnd", "연약지반");
			}else if(analFileVO.getOrignlFileNm().contains("clpsCnrn")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("clpsCnrn", "붕괴우려지역");
			}else if(analFileVO.getOrignlFileNm().contains("maintreeknd")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("maintreeknd", "주요수종");
			}else if(analFileVO.getOrignlFileNm().contains("mainveg")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("mainveg", "주요식생");
			}else if(analFileVO.getOrignlFileNm().contains("wtrPltn")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("wtrPltn", "상수원오염");
			}else if(analFileVO.getOrignlFileNm().contains("dmgCncrn")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("dmgCncrn", "훼손우려지");
			}else if(analFileVO.getOrignlFileNm().contains("frstDsstr")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("frstDsstr", "산림재해");
			}else if(analFileVO.getOrignlFileNm().contains("wildAnml")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("wildAnml", "야생동물");
			}else if(analFileVO.getOrignlFileNm().contains("ecnrFcltyInstl")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("ecnrFcltyInstl", "사방시설설치");
			}else if(analFileVO.getOrignlFileNm().contains("ecnrFcltyNcsty")) {
				convertKorFileNm = analFileVO.getOrignlFileNm().replace("ecnrFcltyNcsty", "사방시설필요");
			}else {
				convertKorFileNm = analFileVO.getOrignlFileNm();
			}
			
			String source = analFileVO.getFileStreCours()+File.separator+analFileVO.getStreFileNm()+".".concat(analFileVO.getFileExtsn());
			String target = copyPath+File.separator+"copy"+File.separator+convertKorFileNm+".".concat(analFileVO.getFileExtsn());
			EgovFileUtil.cp(source, target);
		}
		
		if(zipPath != null && copyPath != null) {
			boolean isCompressed = EgovFileCmprs.cmprsFile(copyPath+File.separator+"copy", zipPath);
		}
		return vo;
	}
	
	/**
	 * 조사완료 상세조회 엑셀다운로드
	 * @param gid
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> selectFrdSvyComptDetailExcel(String gid) throws Exception {
		
		List<?> _result = vytFrdSvyComptDAO.selectFrdSvyComptDetailExcel(gid);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}
	
	@Override
	public List<VytFrdSvyComptVO> selectCheckLines(VytFrdStripLandVO vo) throws Exception{
		return vytFrdSvyComptDAO.selectCheckLines(vo);
	}
	
	@Override
	public void deleteExistLines(String smid) throws Exception{
		vytFrdSvyComptDAO.deleteExistLines(smid);
	};
	
	/**
	 * 대상지 분석결과 저장
	 */
	public void insertAnalFileList(List<AnalFileVO> list) throws Exception {
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		for (AnalFileVO vo : list) {
			vo.setCreatUser(loginVO.getId());
			
			if(vo.getFileExtsn().equals("zip")) {
				boolean isCompressed = EgovFileCmprs.cmprsFile(vo.getFileStreCours()+File.separator+vo.getStreFileNm(), vo.getFileStreCours()+File.separator+vo.getStreFileNm().concat(".zip"));
				EgovFileUtil.rm(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
			}
			
			vytFrdStripLandDAO.insertAnalFile(vo);
		}
	}
	
	/*
	 * 수정노선 등록시 tf_feis_frd_svycompt에 지오메트리 최신화
	 */
	@Override
	public void updateNewGeomInfo(VytFrdStripLandVO vo) throws Exception{
		vytFrdSvyComptDAO.updateNewGeomInfo(vo);
	};
	
}
