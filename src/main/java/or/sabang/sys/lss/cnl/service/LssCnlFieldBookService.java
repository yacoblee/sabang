package or.sabang.sys.lss.cnl.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssCnlFieldBookService {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	public List<LssCnlFieldBookVO> selectLssCnlFieldBookList(LssCnlFieldBookVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	public int selectLssCnlFieldBookListTotCnt(LssCnlFieldBookVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	public LssCnlFieldBookVO selectLssCnlFieldBookDetail(HashMap<String, Object> map) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssCnlStripLandVO> selectLssCnlFieldBookItemList(LssCnlFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssCnlFieldBookItemListTotCnt(LssCnlFieldBookItemVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 공유방 조사유형
	 */
//	String selectCnrsSpceType(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(LssCnlFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(LssCnlFieldBookVO fieldBookVO) throws Exception;
	
	JSONObject insertStripLand(LssCnlFieldBookVO fieldBookVO,MultipartFile mFile) throws Exception;
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	void insertCnrsSpceSld(LssCnlFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssCnlStripLandVO> selectLssCnlFieldBookItemView(LssCnlFieldBookItemVO searchVO) throws Exception;
	
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
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssCnlFieldBookMaxYear() throws Exception;
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssCnlFieldBookYear() throws Exception;
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssCnlFieldBookCheckMstName(String mst_partname) throws Exception;
	
	/**
	 * 공유방 단건 상세조회
	 */
	public LssCnlFieldBookItemVO selectFieldBookDetailOne(LssCnlFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 단건 수정
	 */
	public void updateFieldBookDetailOne(LssCnlFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * 공유방 단건 삭제
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception;
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 */
	public List<EgovMap> selectLssCnlProjBessel(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception;
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssCnlFieldBookVO vo) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	public List<LssCnlFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception;
	
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
	void updateCnrsSpceAuthorMgt(LssCnlFieldBookVO fieldBookVO) throws Exception;
}
