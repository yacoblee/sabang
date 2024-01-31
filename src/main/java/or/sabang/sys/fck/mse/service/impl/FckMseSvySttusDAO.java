package or.sabang.sys.fck.mse.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("fckMseSvySttusDAO")
public class FckMseSvySttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectMseSvySttusTotCnt() throws Exception{
		return selectList("FckMseSvySttus.selectMseSvySttusTotCnt");
	}
	
	public List<EgovMap> selectMseSvySttusRegion() throws Exception{
		return selectList("FckMseSvySttus.selectMseSvySttusRegion");
	}
	
	public List<EgovMap> selectMseSvySttusMonth() throws Exception{
		return selectList("FckMseSvySttus.selectMseSvySttusMonth");
	}
}
