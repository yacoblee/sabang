package or.sabang.sys.fck.mse.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.mse.service.FckMseSvySttusService;

@Service("fckMseSvySttusService")
public class FckMseSvySttusServiceImpl extends EgovAbstractServiceImpl implements FckMseSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="fckMseSvySttusDAO")
	private FckMseSvySttusDAO fckMseSvySttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectMseSvySttusTotCnt() throws Exception {
        return fckMseSvySttusDAO.selectMseSvySttusTotCnt();
	}
	
	@Override
	public List<EgovMap> selectMseSvySttusRegion() throws Exception {
        return fckMseSvySttusDAO.selectMseSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectMseSvySttusMonth() throws Exception {
        return fckMseSvySttusDAO.selectMseSvySttusMonth();
	}
}
