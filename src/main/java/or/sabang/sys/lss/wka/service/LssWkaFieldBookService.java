package or.sabang.sys.lss.wka.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookVO;
import or.sabang.sys.service.SysFieldInfoVO;

public interface LssWkaFieldBookService {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	public List<LssWkaFieldBookVO> selectLssWkaFieldBookList(LssWkaFieldBookVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	public int selectLssWkaFieldBookListTotCnt(LssWkaFieldBookVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	public LssWkaFieldBookVO selectLssWkaFieldBookDetail(HashMap<String, Object> map) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssWkaStripLandVO> selectLssWkaFieldBookItemList(LssWkaFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssWkaFieldBookItemListTotCnt(LssWkaFieldBookItemVO searchVO) throws Exception;

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
	String insertCnrsSpce(LssWkaFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(LssWkaFieldBookVO fieldBookVO) throws Exception;
	
	JSONObject insertStripLand(LssWkaFieldBookVO fieldBookVO,MultipartFile mFile) throws Exception;
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
//	List<LssWkaStripLandVO> selectCnrsSpceSldList(LssWkaStripLandVO stripLandVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	List<LssWkaStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception;
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	void insertCnrsSpceSld(LssWkaFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssWkaStripLandVO> selectLssWkaFieldBookItemView(LssWkaFieldBookItemVO searchVO) throws Exception;
	
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
	public String selectLssWkaFieldBookMaxYear() throws Exception;
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssWkaFieldBookYear() throws Exception;
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssWkaFieldBookCheckMstName(String mst_partname) throws Exception;
	
	/**
	 * 공유방 대상지 상세조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public LssWkaStripLandItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방 대상지 수정
	 * @param vo
	 * @throws Exception
	 */
	void updateFieldBookDetailOne(LssWkaStripLandItemVO vo) throws Exception;
	
	/**
	 * 공유방 단건 삭제
	 */
	public void deleteFieldBookDetailOne(LssWkaStripLandItemVO vo) throws Exception;
	
	
	/**
	 * 공간정보 테이블 위치정보 변환
	 * @throws Exception
	 */
//	public String selectLssWkaGeomDataList(SysFieldInfoVO vo) throws Exception;
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception;
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssWkaFieldBookVO vo) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	public List<LssWkaFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception;
	
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
	void updateCnrsSpceAuthorMgt(LssWkaFieldBookVO fieldBookVO) throws Exception;
}
