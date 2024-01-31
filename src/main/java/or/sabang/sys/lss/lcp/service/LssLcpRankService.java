package or.sabang.sys.lss.lcp.service;

import java.util.List;

import org.json.JSONObject;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import or.sabang.sys.gis.service.AnalFileVO;

public interface LssLcpRankService {
	/**
	 * 랭크 shapefile 압축파일 업로드
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	public JSONObject insertLcpSvyRank(EgovFormBasedFileVo vo) throws Exception;
	
	/**
	 * 랭크 고도값 추출(zonal statistics)
	 * @return
	 * @throws Exception
	 */
//	public JSONObject createLcpSvyRankElevation() throws Exception;
	
	/**
	 * 랭크 경사도 추출(zonal statistics)
	 * @return
	 * @throws Exception
	 */
//	public JSONObject createLcpSvyRankSlope() throws Exception;
	
	/**
	 * 랭크 토심값 추출(zonal statistics)
	 * @return
	 * @throws Exception
	 */
//	public JSONObject createLcpSvyRankSld() throws Exception;
	
	/**
	 * 랭크  총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectLcpRankListTotCnt(LssLcpRankVO searchVO) throws Exception;
	
	/**
	 * 랭크 목록을 조회한다.
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<LssLcpRankVO> selectLcpRankList(LssLcpRankVO searchVO) throws Exception;
	
	/**
	 * 랭크 쉐이프파일을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	AnalFileVO downloadLcpSvyRank() throws Exception;
	/**
	 * 랭크 등록일자 업데이트
	 * @throws Exception
	 */
//	public void updateLcpRankDate() throws Exception;
}	
