package or.sabang.sys.vyt.frd.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandService;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.utl.SuperMapBasicUtils;
import or.sabang.utl.VytFrdSupMapUtils;

@Service("vytFrdStripLandService")
public class VytFrdStripLandServiceImpl extends EgovAbstractServiceImpl implements VytFrdStripLandService {
	private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.frdSld");
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdStripLandServiceImpl.class);
	
	@Resource(name="vytFrdStripLandDAO")
	private VytFrdStripLandDAO vytFrdStripLandDAO;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 대상지 목록조회
	 */
	@Override
	public List<VytFrdStripLandVO> selectVytFrdSldList(VytFrdStripLandVO searchVO) throws Exception {		
		return vytFrdStripLandDAO.selectVytFrdSldList(searchVO);
	}	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytFrdSldYear() throws Exception {
		return vytFrdStripLandDAO.selectVytFrdSldYear();
	}
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectVytFrdSldMaxYear() throws Exception {
		return vytFrdStripLandDAO.selectVytFrdSldMaxYear();
	}
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 대상지 갯수조회
	 */
	@Override
	public int selectVytFrdSldListTotCnt(VytFrdStripLandVO searchVO) throws Exception {
		return vytFrdStripLandDAO.selectVytFrdSldListTotCnt(searchVO);
	}
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 대상지 정보 상세조회
	 */
	@Override
	public VytFrdStripLandVO selectVytFrdSldDetail(HashMap<String, Object> map) throws Exception {
		return vytFrdStripLandDAO.selectVytFrdSldDetail(map);
	}
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<VytFrdStripLandVO> selectVytFrdSldItemList(VytFrdStripLandVO searchVO) throws Exception {
		return vytFrdStripLandDAO.selectVytFrdSldItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectVytFrdSldItemListTotCnt(VytFrdStripLandVO searchVO) throws Exception {
		return vytFrdStripLandDAO.selectVytFrdSldItemListTotCnt(searchVO);
	}
	@Override
	public String insertSldSpce(VytFrdStripLandVO stripLandVO) throws Exception {
		vytFrdStripLandDAO.insertSldSpce(stripLandVO);
		return stripLandVO.getId();
	}
	
	@Override
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	public JSONObject insertFrdSvySld(EgovFormBasedFileVo vo) throws Exception {
		JSONObject returnLog = new JSONObject();
		
		String midPath = vo.getServerSubPath();
		String originFileName = vo.getFileName();
		String zipFileName = vo.getPhysicalName();
		String uploadFilePath = uploadDir+midPath+File.separator+zipFileName;
		String deComFilePath = uploadFilePath.replace(".zip", "");

		boolean deCompressed = EgovFileCmprs.decmprsFile(uploadFilePath, deComFilePath);
		File shpDir = null;
		
		if(deCompressed) {
			shpDir = new File(deComFilePath);
			
			if(shpDir.isDirectory()) {
				
				File[] shpFiles = shpDir.listFiles(new FileFilter() {
					@Override 
					public boolean accept(File pathname) {
						if(pathname.isDirectory()) {
							return false;
						}
						return pathname.getName().toLowerCase().endsWith(".shp");
					}
				});
				
				int cnt = 1;
				if(shpFiles != null) {
					for(File shp : shpFiles) {
						Map<String, Object> map = new HashMap<>();
				        map.put("url", shp.toURI().toURL());
				        
				        boolean delete_all = (cnt == 1 ? true : false);
				        cnt++;
				        VytFrdSupMapUtils utils = new VytFrdSupMapUtils();
				        returnLog = utils.uploadInsertShape(shp.getAbsolutePath(), "tf_feis_frd_svysldinfo", false);
					}
				}else {
					LOGGER.error("shp 파일이 존재하지 않습니다.");
					returnLog.put("status", "fail");
					returnLog.put("message", "shp 파일이 존재하지 않습니다.");
				}
			}else {
				LOGGER.error("압축 해제 결과 폴더가 없습니다.");
				returnLog.put("status", "fail");
				returnLog.put("message", "압축 해제 결과 폴더가 없습니다.");
			}
		}else {
			LOGGER.error("압축 해제 중에 오류가 발생하였습니다.");
			returnLog.put("status", "fail");
			returnLog.put("message", "압축 해제 중에 오류가 발생하였습니다.");
		}
		return returnLog;
	}
	
	/**
	 * 대상지번호 없는 조회
	 * @throws Exception
	 */
	public List<VytFrdStripLandVO> selectNoSldId() throws Exception {
		return vytFrdStripLandDAO.selectNoSldId();
	}
	
	/**
	 * 대상지 번호 부여
	 * @throws Exception
	 */
	@Override
	public void updateNoSldId(HashMap<String, Object> commandMap) throws Exception {
		vytFrdStripLandDAO.updateNoSldId(commandMap);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 대상지 단건 삭제
	 */
	@Override
	public void deleteSldDetailOne(HashMap<String, Object> map) throws Exception {
		vytFrdStripLandDAO.deleteSldDetailOne(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 대상지 삭제
	 */
	@Override
	public void deleteSldDetail(HashMap<String, Object> map) throws Exception {
		vytFrdStripLandDAO.deleteSldDetail(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 대상지 단건 조회
	 */
	@Override
	public VytFrdStripLandVO selectSldDetailOne(String smid) throws Exception {
		return vytFrdStripLandDAO.selectSldDetailOne(smid);
	}
	/**
	 * 대상지 단건 수정
	 */
	@Override
	public void updateSldDetailOne(VytFrdStripLandVO searchVO) throws Exception {
		vytFrdStripLandDAO.updateSldDetailOne(searchVO);
	}
	
	/**
	 * 대상지 분석
	 */
	@Override
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	public void createVytFrdAnalData(HashMap<String, Object> commandMap) throws Exception {
		List<AnalFileVO> fileVO = null;
		
		String analList = commandMap.get("analList").toString().replaceAll("[\\[\\] ]", "");
		String smid = commandMap.get("smid").toString();
		String smid1 = commandMap.get("smid1").toString();	// 기존노선과 분리하기위한
		String sldId = commandMap.get("sldId").toString();
		String bufferSize = commandMap.get("bufferSize").toString();
		
		String pageType = commandMap.get("pageType").toString();
		
		String[] analElements = analList.split(",");
		
		String analId = SuperMapBasicUtils.getDatasetUuid();	//analId
		
		int[] analArray = new int[analElements.length];
        for (int i=0; i<analElements.length; i++) {
        	analArray[i] = Integer.parseInt(analElements[i]);
        }
        
        // 센터 조회
		List<VytFrdStripLandVO> cntPnt = vytFrdStripLandDAO.selectCenterPnt(commandMap);
		
		double x = Double.parseDouble(cntPnt.get(0).getLon());
		double y = Double.parseDouble(cntPnt.get(0).getLat());
		
		String sldLabel = cntPnt.get(0).getSldlabel(); 
		
		Map<String, Object> coordMap = new HashMap<String, Object>();
		coordMap.put("lon", x);
		coordMap.put("lat", y);
		
		
		// 기존 노선 말고 다른 노선 smid 조회
		List<EgovMap> anotherSmidList = vytFrdStripLandDAO.selectAnotherSmid(sldLabel);
		
		String smid2 = null;
		
		// 02번 수정노선 smid
		for(int i=0; i<anotherSmidList.size(); i++) {
			if(anotherSmidList.get(i).get("routecode").equals("02")) {
				smid2 = anotherSmidList.get(i).get("smid").toString();
			}
		}
		
		VytFrdSupMapUtils utils = new VytFrdSupMapUtils(analId);
		
		for (int arNum : analArray) {
			// 1. 대상지위치
			if(arNum == 1) {
				LOGGER.info("1. 대상지 위치 생성");
				fileVO = utils.createFrdClipSatImg(sldId, smid, analId, "forestroad", smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 2. 행정구역
			if(arNum == 2) {
				LOGGER.info("2. 행정구역 생성");
				
				coordMap.put("tbType", "emd");
				// 시군구 코드조회
				List<EgovMap> sggCode = vytFrdStripLandDAO.selectSggOne(coordMap);
				
				String emdCodeOne = sggCode.get(0).get("emdCode").toString();		//해당 읍면동
				String sggCodeOne = sggCode.get(0).get("signguCod").toString();		// 전체 읍면동
				
				// 해당 읍면동 전체 조회 
				List<EgovMap> emdList = vytFrdStripLandDAO.selectEmdList(sggCodeOne);
				
				fileVO = utils.createFrdClipAdmImg(sldId, smid, analId, "forestroad", x, y, sggCodeOne, emdCodeOne, emdList, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 3. 사업대상지 관계지적도
			if(arNum == 3) {
				LOGGER.info("3. 관계지적도 생성");
				
				coordMap.put("tbType", "emd");
				// 시군구 코드조회
				List<EgovMap> sggCode = vytFrdStripLandDAO.selectSggOne(coordMap);
				
				String sggCodeOne = sggCode.get(0).get("signguCod").toString();		// 전체 읍면동
				
				fileVO = utils.createFrdClipRelImg(sldId, smid, analId, "forestroad", sggCodeOne, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 4. 문화재보존관리지도
			if(arNum == 4) {
				LOGGER.info("4. 문화재보존관리지도 생성");
				
				String culSmidList = vytFrdStripLandDAO.selectCultureSmidList(smid);
				
				if(culSmidList != null) {
					String[] smidArray = culSmidList.split(",");
				        
			        List<Integer> list = new ArrayList<>();
			        for (String smidOne : smidArray) {
			        	list.add(Integer.parseInt(smidOne));
			        }	
					List<EgovMap> culCenterPnt = vytFrdStripLandDAO.selectCultureCenterPnt(list);
					
					Double culX = Double.parseDouble(culCenterPnt.get(0).get("x").toString());
					Double culY = Double.parseDouble(culCenterPnt.get(0).get("y").toString());
					
					
					// 문화재 조회 결과 db 저장
					HashMap<String, String> culMap = new HashMap<String, String>();
					
					culMap.put("smid", smid);
					culMap.put("culList", culSmidList);
					
					vytFrdStripLandDAO.updateCultureList(culMap);
					
					
					fileVO = utils.createFrdClipCulImg(sldId, smid, analId, "forestroad", culX, culY, culSmidList, smid2, pageType);
				}else {
					fileVO = utils.createFrdClipCulImg(sldId, smid, analId, "forestroad", null, null, "", smid2, pageType);
				}
				insertAnalFileList(fileVO);
			}
			// 5. 임상분포도
			if(arNum == 5) {
				LOGGER.info("5. 임상분포도 생성");
				
				String analType = "frtp";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 6. 임종분포도
			if(arNum == 6) {
				LOGGER.info("6. 임종분포도 생성");
				
				String analType = "fror";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 7. 영급분포도
			if(arNum == 7) {
				LOGGER.info("7. 영급분포도 생성");
				
				String analType = "agcls";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 8. 경급분포도
			if(arNum == 8) {
				LOGGER.info("8. 경급분포도 생성");
				
				String analType = "dmcls";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 9. 밀도분포도
			if(arNum == 9) {
				LOGGER.info("9. 밀도분포도 생성");
				
				String analType = "dnst";
				fileVO = utils.createFrdClipDestImg(sldId, smid, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 10. 수종분포도
			if(arNum == 10) {
				LOGGER.info("10. 수종분포도 생성");
				
				String analType = "koftr";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 11. 경사분포도
			if(arNum == 11) {
				LOGGER.info("11. 경사분포도 생성");
				
				String analType = "slope";
				fileVO = utils.createFrdClipRstrImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 12. 향분포도
			if(arNum == 12) {
				LOGGER.info("12. 향분포도 생성");
				
				String analType = "aspect";
				fileVO = utils.createFrdClipRstrImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 13. 표고분포도
			if(arNum == 13) {
				LOGGER.info("13. 표고분포도 생성");
				
				String analType = "dem";
				fileVO = utils.createFrdClipRstrImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 14. 토성분포도
			if(arNum == 14) {
				LOGGER.info("14. 토성분포도 생성!");
				
				String analType = "soil";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 15. 지질분포도
			if(arNum == 15) {
				LOGGER.info("15. 지질분포도 생성!");
				
				String analType = "geology";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 16. 모암분포도
			if(arNum == 16) {
				LOGGER.info("16. 모암분포도 생성!");
				
				String analType = "prrck";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 17. 퇴적양식분포도
			if(arNum == 17) {
				LOGGER.info("17. 퇴적양식분포도 생성!");
				
				String analType = "accma";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 18. 암석노출도
			if(arNum == 18) {
				LOGGER.info("18. 암석노출도 생성!");
				
				String analType = "rock";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 19. 생태자연도
			if(arNum == 19) {
				LOGGER.info("19. 생태자연도 생성");
				// 일단은 keep
				String analType = "nature";
				fileVO = utils.createFrdClipDestImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
			// 20. 멸종위기 동식물 분석현황
			if(arNum == 20) {
				LOGGER.info("20. 멸종위기 동식물 분석현황 생성");
				// 일단은 keep
				String analType = "animal";
			}
			// 21. 산불취약지도
			if(arNum == 21) {
				LOGGER.info("21. 산불취약지도 생성");
				// 현재 산불취약테이블없음
				String analType = "forestFire";
			}
			// 22. 산사태위험등급도
			if(arNum == 22) {
				LOGGER.info("22. 산사태위험등급도 생성");
				// tf_feis_landslide
				String analType = "landslide";
				fileVO = utils.createFrdClipRstrImg(sldId, smid1, analId, "forestroad", analType, "uniqe", bufferSize, smid2, pageType);
				insertAnalFileList(fileVO);
			}
		}
		utils.closeWorkspace();
	}
	
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
	 * 대상지 분석 이미지 조회
	 * @params smid
	 */
	@Override
	public List<AnalFileVO> selectAnalImg(HashMap<String, Object> analParameterMap) throws Exception {
		return vytFrdStripLandDAO.selectAnalImg(analParameterMap);
	}
	
	/**
	 * 문화재 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCultureName(List<Integer> list) throws Exception{
		return vytFrdStripLandDAO.selectCultureName(list);
	}
	
	@Override
	public AnalFileVO selectAnalFileDownDetail(String sn) throws Exception{
		return vytFrdStripLandDAO.selectAnalFileDownDetail(sn);
	}
	/*
	 * 필지조회
	 */
	@Override
	public List<VytFrdStripLandVO> selectParcelList(String smid) throws Exception{
		return vytFrdStripLandDAO.selectParcelList(smid);
	}
	
	/**
	 * 대상지명 중복조회
	 */
	@Override
	public int selectVytFrdCheckSldName(String sldNm) throws Exception {
		return vytFrdStripLandDAO.selectVytFrdCheckSldName(sldNm);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 분석결과 파일 전체 다운로드
	 */
	@Override
	public AnalFileVO downloadAnalAll(VytFrdStripLandVO searchVO) throws Exception{
		AnalFileVO vo = new AnalFileVO();
		List<AnalFileVO> list = vytFrdStripLandDAO.selectDownloadAnalAll(searchVO);
		
		String accountId = searchVO.getSld_create_user();//계정 아이디
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
}
