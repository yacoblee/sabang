package or.sabang.sys.fck.apr.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("fckAprSvySttusDAO")
public class FckAprSvySttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectAprSvySttusTotCnt() throws Exception{
		return selectList("FckAprSvySttus.selectAprSvySttusTotCnt");
	}
	
	public List<EgovMap> selectAprSvySttusRegion() throws Exception{
		return selectList("FckAprSvySttus.selectAprSvySttusRegion");
	}
	
	public List<EgovMap> selectAprSvySttusMonth() throws Exception{
		return selectList("FckAprSvySttus.selectAprSvySttusMonth");
	}
}
