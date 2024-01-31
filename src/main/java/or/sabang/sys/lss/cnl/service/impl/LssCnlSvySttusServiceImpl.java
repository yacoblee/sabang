package or.sabang.sys.lss.cnl.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.bsc.service.impl.LssBscSvySttusDAO;
import or.sabang.sys.lss.cnl.service.LssCnlSvySttusService;

@Service("lssCnlSvySttusService")
public class LssCnlSvySttusServiceImpl extends EgovAbstractServiceImpl implements LssCnlSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	
	@Resource(name="lssCnlSvySttusDAO")
	private LssCnlSvySttusDAO lssCnlSvySttusDAO;

	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectCnlSvySttusTotCnt(HashMap<String, Object> map) throws Exception {
        return lssCnlSvySttusDAO.selectCnlSvySttusTotCnt(map);
	}
	
	@Override
	public List<EgovMap> selectCnlSvySttusRegion() throws Exception {
        return lssCnlSvySttusDAO.selectCnlSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectCnlSvySttusMonth() throws Exception {
        return lssCnlSvySttusDAO.selectCnlSvySttusMonth();
	}
	
	/**
	 * 사용자ID별 취약지역 해제조사 통계정보 모집단 갯수를 조회한다.
     * @param searchVO
     * @return int
     */
	@Override
	public int selectCnlSvyPopultnMember(HashMap<String, Object> map) throws Exception {
		return lssCnlSvySttusDAO.selectCnlSvyPopultnMember(map);
	}
	
	/**
	 * 사용자별 모집단 건수를 변경한다.
     * @param map
     */
	public void insertCnlSvyPopultn(HashMap<String, Object> map) throws Exception{
		lssCnlSvySttusDAO.insertCnlSvyPopultn(map);
	}
}
