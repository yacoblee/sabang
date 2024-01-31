package or.sabang.sys.fck.pcs.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("fckPcsSvySttusDAO")
public class FckPcsSvySttusDAO extends EgovComAbstractDAO {

	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 */
	public List<EgovMap> selectPcsSvySttusTotCnt() throws Exception {
		return selectList("FckPcsSvySttus.selectPcsSvySttusTotCnt");
	}

	public List<EgovMap> selectPcsSvySttusRegion() throws Exception {
		return selectList("FckPcsSvySttus.selectPcsSvySttusRegion");
	}

	public List<EgovMap> selectPcsSvySttusMonth() throws Exception {
		return selectList("FckPcsSvySttus.selectPcsSvySttusMonth");
	}

}
