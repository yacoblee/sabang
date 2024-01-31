package or.sabang.sys.vyt.frd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookVO;


public interface VytFrdFieldBookService {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	public List<VytFrdFieldBookVO> selectVytFrdFieldBookList(VytFrdFieldBookVO searchVO) throws Exception;
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	public int selectVytFrdFieldBookListTotCnt(VytFrdFieldBookVO searchVO) throws Exception;
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	public VytFrdFieldBookVO selectVytFrdFieldBookDetail(HashMap<String, Object> map) throws Exception;
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce (HashMap<String, Object> map) throws Exception;
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
	public String selectVytFrdFieldBookMaxYear() throws Exception;
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytFrdFieldBookYear() throws Exception;
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<VytFrdStripLandVO> selectVytFrdFieldBookItemList(VytFrdFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectVytFrdFieldBookItemListTotCnt(VytFrdFieldBookItemVO searchVO) throws Exception;
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public void  insertStripLand(HashMap<String, Object> commandMap) throws Exception;
	String insertCnrsSpce(VytFrdFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectVytFrdFieldBookCheckMstName(String mst_partname) throws Exception;
	
	/**
	 * 공유방 단건 상세조회
	 */
	public VytFrdFieldBookItemVO selectFieldBookDetailOne(VytFrdFieldBookItemVO searchVO) throws Exception;
	/**
	 * 공유방 단건 수정
	 */
	public void updateFieldBookDetailOne(VytFrdFieldBookItemVO searchVO) throws Exception;
	/**
	 * 공유방 단건 삭제
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception;
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception;
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	public List<VytFrdFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception;
	
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
	void updateCnrsSpceAuthorMgt(VytFrdFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(VytFrdFieldBookVO fieldBookVO) throws Exception;
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(VytFrdFieldBookVO vo) throws Exception;
	
	/**
	 * 대상지 목록조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectSldRegInfo() throws Exception;
	
	/**
	 * 대상지 개수조회
	 * @param fieldBookItemVO 
	 * @return
	 * @throws Exception
	 */
	public int selectSldInfoCnt(VytFrdFieldBookItemVO fieldBookItemVO) throws Exception;
	
	/**
	 * 대상지 사업목록 내용조회 
	 * @return
	 * @throws Exception
	 */
	public List<VytFrdStripLandVO> selectSldInfo(VytFrdFieldBookItemVO fieldBookItemVO) throws Exception;
	
	/**
	 * 공유방 번호 부여
	 * @throws Exception
	 */
	public void updateNoMstId(HashMap<String, Object> commandMap) throws Exception;
	
	/**
	 * 대상지 삭제 gid 조회
	 * @param fieldBookItemVO 
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectDeleteItems(String id) throws Exception;
	
	
	
}
