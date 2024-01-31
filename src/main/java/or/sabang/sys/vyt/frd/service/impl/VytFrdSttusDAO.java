package or.sabang.sys.vyt.frd.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("vytFrdSttusDAO")
public class VytFrdSttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectFrdSvySttusTotCnt() throws Exception{
		return selectList("VytFrdSvySttus.selectFrdSvySttusTotCnt");
	}
	
	public List<EgovMap> selectFrdSvySttusCompentAuth() throws Exception{
		return selectList("VytFrdSvySttus.selectFrdSvySttusCompentAuth");
	}
	
	public List<EgovMap> selectFrdSvySttusRegion() throws Exception{
		return selectList("VytFrdSvySttus.selectFrdSvySttusRegion");
	}
	
	public List<EgovMap> selectFrdSvySttusMonth() throws Exception{
		return selectList("VytFrdSvySttus.selectFrdSvySttusMonth");
	}
}
