package or.sabang.sys.vyt.ecb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;

public interface VytEcbAlsManageService {
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
	public List<VytEcbAnalVO> selectVytEcbWorkList(VytEcbAnalVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 갯수조회
	 */
	public int selectVytEcbWorkListTotCnt(VytEcbAnalVO searchVO) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 사업 정보 상세조회
	 */
	public VytEcbAnalVO selectVytEcbWorkDetail(VytEcbAnalVO searchVO) throws Exception;
	
	public List<AnalFileVO> selectVytEcbAnalDetail(VytEcbAnalVO searchVO) throws Exception;
	
	/**
	 * 분석결과 파일 전체 다운로드
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO downloadAnalAll(VytEcbAnalVO searchVO) throws Exception;
	
	/**
	 * 분석결과 통계정보유무
	 * @param searchVO
	 * @return
	 * @throws Exception 
	 */
	public int selectVytEcbAnalStatDataCnt(VytEcbAnalVO searchVO) throws Exception;
	
	/**
	 * 분석결과 통계정보 엑셀 다운로드
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public Map<?,?> selectVytEcbAnalStatDataExcel(VytEcbAnalVO searchVO) throws Exception;
	
	/**
	 * 지적조회
	 * @param cadastralWkt
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCadastralDetail(String cadastralWkt) throws Exception;
}
