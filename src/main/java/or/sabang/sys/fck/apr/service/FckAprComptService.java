package or.sabang.sys.fck.apr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

public interface FckAprComptService {
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectFckAprComptListTotCnt(FckAprComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<FckAprComptVO> selectFckAprComptList(FckAprComptVO searchVO) throws Exception;
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	FckAprComptVO selectFckAprComptDetail(FckAprComptVO ComptVO) throws Exception;
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateFckAprCompt(FckAprComptVO ComptVO) throws Exception;
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void deleteFckAprCompt(FckAprComptVO ComptVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckAprComptMaxYear() throws Exception;
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckAprComptMaxMonth() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckAprComptYear() throws Exception;
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprProjDMS(HashMap<String, Object> map) throws Exception;
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprProjBessel(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 명을 조회한다.
	 * @param ComptVO
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectFieldBookNm(FckAprComptVO ComptVO) throws Exception;
		
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지를 엑셀다운로드 한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public Map<?, ?> selectFckAprSvyComptListExcel(FckAprComptVO aprComptVO) throws Exception;
	
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
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @param aprComptVO
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateFckAprComptExcel(FckAprComptVO aprComptVO,MultipartFile mFile) throws Exception;
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 권한 코드 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCode(HashMap<String, Object> map) throws Exception;
}
