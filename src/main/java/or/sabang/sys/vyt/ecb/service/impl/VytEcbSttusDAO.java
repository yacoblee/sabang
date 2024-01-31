package or.sabang.sys.vyt.ecb.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("vytEcbSttusDAO")
public class VytEcbSttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectEcbSttusTotCnt() throws Exception{
		return selectList("vytEcbSttusDAO.selectEcbSttusTotCnt");
	}
	
	public List<EgovMap> selectEcbSttusRegion() throws Exception{
		return selectList("vytEcbSttusDAO.selectEcbSttusRegion");
	}
	
	public List<EgovMap> selectEcbSttusMonth() throws Exception{
		return selectList("vytEcbSttusDAO.selectEcbSttusMonth");
	}
}
