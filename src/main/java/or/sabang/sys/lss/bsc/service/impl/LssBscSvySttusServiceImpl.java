package or.sabang.sys.lss.bsc.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.bsc.service.LssBscSvySttusService;

@Service("lssBscSvySttusService")
public class LssBscSvySttusServiceImpl extends EgovAbstractServiceImpl implements LssBscSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssBscSvySttusDAO")
	private LssBscSvySttusDAO lssBscSvySttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectBscSvySttusTotCnt(HashMap<String, Object> map) throws Exception {
        return lssBscSvySttusDAO.selectBscSvySttusTotCnt(map);
	}
	
	@Override
	public List<EgovMap> selectBscSvySttusRegion() throws Exception {
        return lssBscSvySttusDAO.selectBscSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectBscSvySttusMonth() throws Exception {
        return lssBscSvySttusDAO.selectBscSvySttusMonth();
	}
	
	/**
	 * 사용자ID별 기초조사 통계정보 모집단 갯수를 조회한다.
     * @param searchVO
     * @return int
     */
	@Override
	public int selectBscSvyPopultnMember(HashMap<String, Object> map) throws Exception{
		return lssBscSvySttusDAO.selectBscSvyPopultnMember(map);
	}
	
	/**
	 * 사용자별 모집단 건수를 변경한다.
     * @param map
     */
	public void insertBscSvyPopultn(HashMap<String, Object> map) throws Exception{
		lssBscSvySttusDAO.insertBscSvyPopultn(map);
	};
}
