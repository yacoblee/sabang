package or.sabang.sys.lss.lcp.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.lcp.service.LssLcpRankVO;

@Repository("lssLcpRankDAO")
public class LssLcpRankDAO extends EgovComAbstractDAO {
	
//	public JSONObject insertLcpSvyRank(List<LssBscFieldBookItemVO> list) throws Exception{
//		insert("lssBscFieldBookDAO.insertStripLand", list);
//	};
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectLcpRankListTotCnt(LssLcpRankVO searchVO) throws Exception{
		return (Integer)selectOne("LssLcpRank.selectLcpRankListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssLcpRankVO> selectLcpRankList(LssLcpRankVO searchVO) throws Exception{
		 return (List<LssLcpRankVO>) list("LssLcpRank.selectLcpRankList", searchVO);
	}
	
	/**
	 * 랭크 등록일자 업데이트
	 * @throws Exception
	 */
	public void updateLcpRankDate() throws Exception{
		update("LssLcpRank.updateLcpRankDate");
	}
}
