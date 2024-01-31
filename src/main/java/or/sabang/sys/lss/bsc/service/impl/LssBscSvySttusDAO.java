package or.sabang.sys.lss.bsc.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

@Repository("lssBscSvySttusDAO")
public class LssBscSvySttusDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public List<EgovMap> selectBscSvySttusTotCnt(HashMap<String, Object> map) throws Exception{
		return selectList("LssBscSvySttus.selectBscSvySttusTotCnt",map);
	}
	
	public List<EgovMap> selectBscSvySttusRegion() throws Exception{
		return selectList("LssBscSvySttus.selectBscSvySttusRegion");
	}
	
	public List<EgovMap> selectBscSvySttusMonth() throws Exception{
		return selectList("LssBscSvySttus.selectBscSvySttusMonth");
	}
	
	/**
	 * 사용자ID별 기초조사 통계정보 모집단 갯수를 조회한다.
     * @param searchVO
     * @return int
     */
	public int selectBscSvyPopultnMember(HashMap<String, Object> map) throws Exception{
		return (int) selectOne("LssBscSvySttus.selectBscSvyPopultnMember", map);
	}
	
	/**
	 * 사용자별 모집단 건수를 변경한다.
     * @param map
     */
	void insertBscSvyPopultn(HashMap<String, Object> map) throws Exception{
		insert("LssBscSvySttus.insertBscSvyPopultn", map);
	};
}
