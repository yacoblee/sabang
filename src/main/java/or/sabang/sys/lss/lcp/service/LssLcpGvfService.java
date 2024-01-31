package or.sabang.sys.lss.lcp.service;

import java.util.List;

import org.json.JSONObject;

import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;

public interface LssLcpGvfService {
	/**
	 * 제보 shapefile 압축파일 업로드
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	public JSONObject insertLcpSvyGvf(EgovFormBasedFileVo vo) throws Exception;
	
	/**
	 * 제보  총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectLcpGvfListTotCnt(LssLcpRankVO searchVO) throws Exception;
	
	/**
	 * 제보 목록을 조회한다.
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectLcpGvfList(LssLcpRankVO searchVO) throws Exception;
	
	/**
	 * 제보 등록 목록을 조회한다.
	 * @return
	 * @throws Exception
	 */
	List<EgovMap> selectLcpGvfAddList() throws Exception;
	
	/**
	 * 제보데이터 임상도 정보를 조회한다.
	 * @throws Exception
	 */
	LssLcpGvfVO selectGvfImInfo(LssLcpGvfVO gvfVO) throws Exception;
	
	/**
	 * 제보데이터 입지도 정보를 조회한다.
	 * @throws Exception
	 */
	LssLcpGvfVO selectGvfIjInfo(LssLcpGvfVO gvfVO) throws Exception;
	
	/**
	 * 제보데이터 지질도 정보를 조회한다.
	 * @throws Exception
	 */
	LssLcpGvfVO selectGvfGlInfo(LssLcpGvfVO gvfVO) throws Exception;
	
	/**
	 * 제보 데이터을 수정한다.
	 * @param gvfVO
	 * @return
	 * @throws Exception
	 */
	void updateLcpGvfData(LssLcpGvfVO gvfVO) throws Exception;
	
	/**
	 * 제보 데이터을 삭제한다.
	 * @param gvfVO
	 * @return
	 * @throws Exception
	 */
	void deleteLcpGvfData(LssLcpGvfVO gvfVO) throws Exception;
	
	/**
	 * 제보 쉐이프파일을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	AnalFileVO downloadLcpSvyGvf(String pnu) throws Exception;
}	
