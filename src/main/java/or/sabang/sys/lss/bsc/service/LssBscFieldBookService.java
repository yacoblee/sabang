package or.sabang.sys.lss.bsc.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssBscFieldBookService {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	public List<LssBscFieldBookVO> selectLssBscFieldBookList(LssBscFieldBookVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	public int selectLssBscFieldBookListTotCnt(LssBscFieldBookVO searchVO) throws Exception;

	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	public LssBscFieldBookVO selectLssBscFieldBookDetail(HashMap<String, Object> map) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssBscStripLandVO> selectLssBscFieldBookItemList(LssBscFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssBscFieldBookItemListTotCnt(LssBscFieldBookItemVO searchVO) throws Exception;

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
	String insertCnrsSpce(LssBscFieldBookVO fieldBookVO) throws Exception;
	
	JSONObject insertStripLand(LssBscFieldBookVO fieldBookVO,MultipartFile mFile) throws Exception;
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	void insertCnrsSpceSld(LssBscFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssBscStripLandVO> selectLssBscFieldBookItemView(LssBscFieldBookItemVO searchVO) throws Exception;
	
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
	public String selectLssBscFieldBookMaxYear() throws Exception;
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssBscFieldBookYear() throws Exception;
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssBscFieldBookCheckMstName(String mst_partname) throws Exception;
}
