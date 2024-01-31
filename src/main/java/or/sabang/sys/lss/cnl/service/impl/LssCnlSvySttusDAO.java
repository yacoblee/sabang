package or.sabang.sys.lss.cnl.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("lssCnlSvySttusDAO")
public class LssCnlSvySttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectCnlSvySttusTotCnt(HashMap<String, Object> map) throws Exception{
		return selectList("LssCnlSvySttus.selectCnlSvySttusTotCnt",map);
	}
	
	public List<EgovMap> selectCnlSvySttusRegion() throws Exception{
		return selectList("LssCnlSvySttus.selectCnlSvySttusRegion");
	}
	
	public List<EgovMap> selectCnlSvySttusMonth() throws Exception{
		return selectList("LssCnlSvySttus.selectCnlSvySttusMonth");
	}
	
	/**
	 * 사용자ID별 기초조사 통계정보 모집단 갯수를 조회한다.
     * @param searchVO
     * @return int
     */
	public int selectCnlSvyPopultnMember(HashMap<String, Object> map) throws Exception{
		return (int) selectOne("LssCnlSvySttus.selectCnlSvyPopultnMember", map);
	}
	
	/**
	 * 사용자별 모집단 건수를 변경한다.
     * @param map
     */
	void insertCnlSvyPopultn(HashMap<String, Object> map) throws Exception{
		insert("LssCnlSvySttus.insertCnlSvyPopultn", map);
	};
	
	
	
}
