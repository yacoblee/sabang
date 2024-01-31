package or.sabang.sys.lss.wka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

public interface LssWkaSvyComptService {
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectWkaSvyComptListTotCnt(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<LssWkaSvyComptVO> selectWkaSvyComptList(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	LssWkaSvyComptVO selectWkaSvyComptDetail(LssWkaSvyComptVO svyComptVO) throws Exception;
		
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateWkaCompt(HashMap<String, Object> wkaCompt, HashMap<String, Object> wkaCompt2) throws Exception;
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateWkaSvyCompt(LssWkaSvyComptVO svyComptVO) throws Exception;
	/**
	 * 대상지 공간정보를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateWkaSvyComptGeom(LssWkaSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void deleteWkaSvyCompt(LssWkaSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지를 엑셀다운로드 한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public Map<?, ?> selectWkaSvyComptListExcel(LssWkaSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectWkaSvyComptMaxYear() throws Exception;
	
	/**
	 * 대상지 조사월 최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectWkaSvyComptMaxMonth() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectWkaSvyComptYear() throws Exception;
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @throws Exception
	 */
	JSONObject updateWkaSvyComptExcel(LssWkaSvyComptVO svyComptVO,MultipartFile mFile) throws Exception;

	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception;
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception;
	
	/**
	 * 위치도 생성 업데이트
	 * @param searchVO
	 * @throws Exception
	 */
	public void updateComptLcMap(LocReCreateVO searchVO) throws Exception;
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaLocReCreateSvyId(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateWkaSvyComptPhotoList(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	public EgovMap selectSvyPhotoNullList(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssWkaSvyComptVO map) throws Exception;
	
	/**
	 * 대상지 공간정보 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeom(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 공간정보 (유출구)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	void insertSvyComptGeomVnarapnt(HashMap<String, Object> geomMap) throws Exception;
	
	/**
	 * 공간정보 (대피로)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	void insertSvyComptGeomVnaralne(HashMap<String, Object> geomMap) throws Exception;
	
	/**
	 * 공간정보 (폴리곤)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	void insertSvyComptGeomVnaraPlgn(HashMap<String, Object> geomMap) throws Exception;
	
	/**
	 * 대상지 공간정보 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeomPntLne(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 공간정보 조회
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<LssWkaSvyComptVO> selectSvyComptGeomPlgn(LssWkaSvyComptVO searchVO) throws Exception;
	
//	/**
//	 * 현황도 정보 조회
//	 * @param searchMap
//	 * @return
//	 * @throws Exception
//	 */
//	public String selectSvyComptStatMap(HashMap<String, Object> searchMap) throws Exception;
	
	/**
	 * 편입면적 계산
	 * 
	 * @param hashMap
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	public List<LssWkaSvyComptVO> selectCalcInArea(LssWkaSvyComptVO searchVO) throws Exception;
	
	/**
	 * 취약지역 쉐이프파일을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	AnalFileVO downloadWkaSvyShp(String gid) throws Exception;
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 로그인한 유저의 권한있는 공유방 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<String> selectAuthorCnrsList(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 유역면적 점수를 조회한다.
	 * @param vnaraPlgnWkt03
	 * @return String
	 * @throws Exception 
	 */
	String selectDgrareaScore(String vnaraPlgnWkt03) throws Exception;
	
	/**
	 * 공간정보 2개의 폴리곤을 하나로 합친다. 
     * @param calcAreaMap
     * @return String
     */
	public String selectUnionGeom(HashMap<String, Object> map) throws Exception;
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLssWkaProjDMS(HashMap<String, Object> map) throws Exception;
}