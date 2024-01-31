package or.sabang.sys.lss.lcp.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssLcpFieldBookService {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	public List<LssLcpFieldBookVO> selectLssLcpFieldBookList(LssLcpFieldBookVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	public int selectLssLcpFieldBookListTotCnt(LssLcpFieldBookVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	public LssLcpFieldBookVO selectLssLcpFieldBookDetail(HashMap<String, Object> map) throws Exception;

	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssLcpSvyStripLandVO> selectLssLcpFieldBookItemList(LssLcpFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssLcpFieldBookItemListTotCnt(LssLcpFieldBookItemVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	List<LssLcpFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	LssLcpFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception;

	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	int selectCnrsSpceAt(int mst_id) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사유형
	 */
//	String selectCnrsSpceType(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
	List<LssLcpFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 조회 테스트
	 */
//	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception;

	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
	 */
//	int selectCnrsSpceMvl(int mst_id) throws Exception;
	int selectLcpCnrsSpceMvl(int mst_id) throws Exception;
	int selectAprCnrsSpceFieldMvl(int mst_id) throws Exception;
	int selectAprCnrsSpceComptMvl(int mst_id) throws Exception;
	
	/**
	 * @return
	 * @description 기초조사 FID 갱신
	 */
	int selectLcpUpdtFid() throws Exception;

	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
//	void insertCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception;
//	int insertLcpCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception;
//	void insertAprCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
	void updateCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception;
//	int updateLcpCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception;
//	void updateAprCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	List<LssLcpFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception;
//	List<LssLcpFieldBookItemVO> selectLcpCnrsSpceCompItem(HashMap<String, Object> map) throws Exception;
//	List<LssLcpFieldBookItemVO> selectAprCnrsSpceCompItem(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(LssLcpFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(LssLcpFieldBookVO fieldBookVO) throws Exception;
	
	JSONObject insertStripLand(LssLcpFieldBookVO fieldBookVO) throws Exception;
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
	List<LssLcpSvyStripLandVO> selectCnrsSpceSldList(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	List<LssLcpSvyStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception;
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	void insertCnrsSpceSld(LssLcpFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssLcpSvyStripLandVO> selectLssLcpFieldBookItemView(LssLcpFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce (HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem (HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem (HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem (HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	int selectUpsertAt(LssLcpFieldBookItemVO searchVO) throws Exception;

	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssLcpFieldBookMaxYear() throws Exception;
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssLcpFieldBookYear() throws Exception;
	
	/**
	 * 시업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectLssLcpFieldBookCheckMstName(String mst_partname) throws Exception;
	
	/**
	 * 제보테이블 정보를 조회한다.
	 * @param vo
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpGvfListAddr(LssLcpFieldBookVO vo) throws Exception;
	
	/**
	 * 조사대상지등록 테이블 정보를 조회한다. 
	 * @throws Exception
	 */
	public List<LssLcpFieldBookVO> selectLcpSldRegList() throws Exception;
	
	/**
	 * 조사대상지정보 테이블 정보를 조회한다.
	 * @param vo
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSldListAddr(LssLcpFieldBookVO vo) throws Exception;
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception;
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssLcpFieldBookVO vo) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	public List<LssLcpFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception;
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception;
	
	/**
	 * 공유방 사용자 권한 수정
	 * @param vo
	 * @throws Exception
	 */
	void updateCnrsSpceAuthorMgt(LssLcpFieldBookVO fieldBookVO) throws Exception;
}
