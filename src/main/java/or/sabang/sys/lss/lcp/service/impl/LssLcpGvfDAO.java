package or.sabang.sys.lss.lcp.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.lcp.service.LssLcpGvfVO;
import or.sabang.sys.lss.lcp.service.LssLcpRankVO;

@Repository("lssLcpGvfDAO")
public class LssLcpGvfDAO extends EgovComAbstractDAO {
	
	/**
	 * 제보 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectLcpGvfListTotCnt(LssLcpRankVO searchVO) throws Exception{
		return (Integer)selectOne("LssLcpGvf.selectLcpGvfListTotCnt", searchVO);
	}

   /**
	 * 제보 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<?> selectLcpGvfList(LssLcpRankVO searchVO) throws Exception{
		 return (List<?>) list("LssLcpGvf.selectLcpGvfList", searchVO);
	}
	
	/**
	 * 제보 등록 목록을 조회한다.
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpGvfAddList() throws Exception{
		return (List<EgovMap>) list("LssLcpGvf.selectLcpGvfAddList", "");
	}
	
	/**
	 * 제보데이터 임상도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpGvfVO selectGvfImInfo(LssLcpGvfVO gvfVO) throws Exception {
		return selectOne("LssLcpGvf.selectGvfImInfo",gvfVO);
	}
	
	/**
	 * 제보데이터 입지도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpGvfVO selectGvfIjInfo(LssLcpGvfVO gvfVO) throws Exception {
		return selectOne("LssLcpGvf.selectGvfIjInfo",gvfVO);
	}
	
	/**
	 * 제보데이터 지질도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpGvfVO selectGvfGlInfo(LssLcpGvfVO gvfVO) throws Exception {
		return selectOne("LssLcpGvf.selectGvfGlInfo",gvfVO);
	}
	
	/**
	 * 제보 데이터를 수정한다.
	 * @param gvfVO
	 * @throws Exception
	 */
	public void updateLcpGvfData(LssLcpGvfVO gvfVO) throws Exception{
		update("LssLcpGvf.updateLcpGvfData", gvfVO);
	}
	
	/**
	 * 조사대상지 정보를 등록한다.
	 * @throws Exception
	 */ 
    public void insertSvySldInfo(LssLcpGvfVO gvfVO) throws Exception{
		insert("LssLcpGvf.insertSvySldInfo", gvfVO);
	};
	
	/**
	 * 제보 데이터를 삭제한다.
	 * @param gvfVO
	 * @throws Exception
	 */
	public void deleteLcpGvfData(LssLcpGvfVO gvfVO) throws Exception{
		delete("LssLcpGvf.deleteLcpGvfData", gvfVO);
	}
	
	/**
	 * 조사대상지 정보를 삭제한다.
	 * @throws Exception
	 */ 
    public void deleteSvySldInfo(LssLcpGvfVO gvfVO) throws Exception{
		delete("LssLcpGvf.deleteSvySldInfo", gvfVO);
	};
	
	/**
	 * 조사대상지 정보를 수정한다.
	 * @throws Exception
	 */ 
	public void updateSvySldRegInfo(LssLcpGvfVO gvfVO) throws Exception{
		update("LssLcpGvf.updateSvySldRegInfo", gvfVO);
	};
	
}
