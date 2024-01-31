package or.sabang.sys.fck.mse.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FckMseFieldBookService {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	public List<FckMseFieldBookVO> selectFckMseFieldBookList(FckMseFieldBookVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	public int selectFckMseFieldBookListTotCnt(FckMseFieldBookVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	public FckMseFieldBookVO selectFckMseFieldBookDetail(HashMap<String, Object> map) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<FckMseStripLandVO> selectFckMseFieldBookItemList(FckMseFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectFckMseFieldBookItemListTotCnt(FckMseFieldBookItemVO searchVO) throws Exception;

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
	List<FckMseFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	FckMseFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception;

	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	int selectCnrsSpceAt(int mst_id) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
//	List<FckMseFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception;

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

	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
//	void insertCnrsSpceItem(FckMseFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
//	void updateCnrsSpceItem(FckMseFieldBookItemVO itemVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	List<FckMseFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(FckMseFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(FckMseFieldBookVO fieldBookVO) throws Exception;
	
	JSONObject insertStripLand(FckMseFieldBookVO fieldBookVO) throws Exception;
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
	List<FckMseStripLandVO> selectCnrsSpceSldList(FckMseStripLandVO stripLandVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	List<FckMseStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception;
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	void insertCnrsSpceSld(FckMseFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<FckMseStripLandVO> selectFckMseFieldBookItemView(FckMseFieldBookItemVO searchVO) throws Exception;
	
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
	 * @param map
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	int selectUpsertAt(FckMseFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckMseFieldBookMaxYear() throws Exception;
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckMseFieldBookYear() throws Exception;
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectFckMseFieldBookCheckMstName(String mst_partname) throws Exception;
	
	/**
	 * 공유방 단건 상세조회
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	FckMseFieldBookItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 단건 수정
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateFieldBookDetailOne(FckMseFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * 공유방 단건 삭제
	 * @param stripLandVO
	 * @throws Exception
	 */
	void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception;
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckMseProjBessel(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception;
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(FckMseFieldBookVO vo) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	public List<FckMseFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception;
	
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
	void updateCnrsSpceAuthorMgt(FckMseFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * 계측장비 대상지 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectMseSldListAddr(FckMseFieldBookVO fieldBookVO) throws Exception;
}