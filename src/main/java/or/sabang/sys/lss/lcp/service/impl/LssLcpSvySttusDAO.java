package or.sabang.sys.lss.lcp.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("lssLcpSvySttusDAO")
public class LssLcpSvySttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectLcpSvySttusTotCnt(HashMap<String, Object> map) throws Exception{
		return selectList("LssLcpSvySttus.selectLcpSvySttusTotCnt", map);
	}
	
	public List<EgovMap> selectLcpSvySttusRegion() throws Exception{
		return selectList("LssLcpSvySttus.selectLcpSvySttusRegion");
	}
	
	public List<EgovMap> selectLcpSvySttusMonth() throws Exception{
		return selectList("LssLcpSvySttus.selectLcpSvySttusMonth");
	}
}
