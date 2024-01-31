package or.sabang.sys.fck.mse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.service.LocReCreateVO;
//import or.sabang.sys.spt.rpt.service.SptRptMseReportSvyComptVO;

public interface FckMseComptService {
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectFckMseComptListTotCnt(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<FckMseComptVO> selectFckMseComptList(FckMseComptVO searchVO) throws Exception;
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	List<FckMseComptVO> selectFckMseComptDetail(FckMseComptVO ComptVO) throws Exception;
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateFckMseCompt(FckMseComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void deleteMseCompt(FckMseComptVO ComptVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckMseComptMaxYear() throws Exception;
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckMseComptMaxMonth() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckMseComptYear() throws Exception;
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckMseProjDMS(HashMap<String, Object> map) throws Exception;
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckMseProjBessel(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 명을 조회한다.
	 * @param ComptVO
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectFieldBookNm(FckMseComptVO ComptVO) throws Exception;
	
	/**
	 * 대상지를 엑셀다운로드 한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public Map<?, ?> selectMseSvyComptListExcel(FckMseComptVO mseComptVO) throws Exception;
	
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
	 * @param mseComptVO
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateFckMseComptExcel(FckMseComptVO mseComptVO,MultipartFile mFile) throws Exception;
	
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
