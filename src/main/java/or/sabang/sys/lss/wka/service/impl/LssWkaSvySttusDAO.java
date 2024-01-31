package or.sabang.sys.lss.wka.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("lssWkaSvySttusDAO")
public class LssWkaSvySttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectWkaSvySttusTotCnt() throws Exception{
		return selectList("LssWkaSvySttus.selectWkaSvySttusTotCnt");
	}
	
	public List<EgovMap> selectWkaSvySttusRegion() throws Exception{
		return selectList("LssWkaSvySttus.selectWkaSvySttusRegion");
	}
	
	public List<EgovMap> selectWkaSvySttusMonth() throws Exception{
		return selectList("LssWkaSvySttus.selectWkaSvySttusMonth");
	}
}
