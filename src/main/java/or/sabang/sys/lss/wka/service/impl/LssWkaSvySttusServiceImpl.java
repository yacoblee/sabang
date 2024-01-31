package or.sabang.sys.lss.wka.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.wka.service.LssWkaSvySttusService;

@Service("lssWkaSvySttusService")
public class LssWkaSvySttusServiceImpl extends EgovAbstractServiceImpl implements LssWkaSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssWkaSvySttusDAO")
	private LssWkaSvySttusDAO lssWkaSvySttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectWkaSvySttusTotCnt() throws Exception {
        return lssWkaSvySttusDAO.selectWkaSvySttusTotCnt();
	}
	
	@Override
	public List<EgovMap> selectWkaSvySttusRegion() throws Exception {
        return lssWkaSvySttusDAO.selectWkaSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectWkaSvySttusMonth() throws Exception {
        return lssWkaSvySttusDAO.selectWkaSvySttusMonth();
	}
}
