package or.sabang.sys.vyt.ecb.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface VytEcbSldManageService {
	/**
	 * 사업 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectVytEcbWorkMaxYear() throws Exception;
	
	/**
	 * 사업 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytEcbWorkYear() throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 목록조회
	 */
	public List<VytEcbWorkVO> selectVytEcbWorkList(VytEcbWorkVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 갯수조회
	 */
	public int selectVytEcbWorkListTotCnt(VytEcbWorkVO searchVO) throws Exception;
	
	/**
	 * 사업 등록
	 * @param fieldBookVO
	 * @return
	 * @throws Exception
	 */
	String insertWork(VytEcbWorkVO fieldBookVO) throws Exception;
	
	/**
	 * 사업 대상지 엑셀 등록
	 * @param fieldBookVO
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	JSONObject insertStripLand(VytEcbWorkVO fieldBookVO,MultipartFile mFile) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 삭제
	 */
	void deleteWork(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 사업 정보 상세조회
	 */
	public VytEcbWorkVO selectVytEcbWorkDetail(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 목록 조회
	 */
	public List<VytEcbStripLandVO> selectVytEcbWorkSldList(VytEcbWorkVO searchVO) throws Exception;
	
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 목록 갯수조회
	 */
	public int selectVytEcbWorkSldListTotCnt(VytEcbWorkVO searchVO) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 대상지 목록 일괄삭제
	 */
	void deleteWorkAllSld(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 수정조회
	 */
	public List<VytEcbStripLandVO> selectVytEcbWorkSldListView(VytEcbWorkVO searchVO) throws Exception;
	
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 조사데이터 삭제
	 */
	void deleteWorkItem (HashMap<String, Object> map) throws Exception;
}
