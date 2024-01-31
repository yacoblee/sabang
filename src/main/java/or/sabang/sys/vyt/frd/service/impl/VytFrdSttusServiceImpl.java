package or.sabang.sys.vyt.frd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.vyt.frd.service.VytFrdSttusService;

@Service("vytFrdSttusService")
public class VytFrdSttusServiceImpl extends EgovAbstractServiceImpl implements VytFrdSttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdSttusServiceImpl.class);
	
	@Resource(name="vytFrdSttusDAO")
	private VytFrdSttusDAO vytFrdSttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectFrdSvySttusTotCnt() throws Exception {
        return vytFrdSttusDAO.selectFrdSvySttusTotCnt();
	}
	
	@Override
	public List<EgovMap> selectFrdSvySttusCompentAuth() throws Exception {
        return vytFrdSttusDAO.selectFrdSvySttusCompentAuth();
	}
	
	@Override
	public List<EgovMap> selectFrdSvySttusRegion() throws Exception {
		return vytFrdSttusDAO.selectFrdSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectFrdSvySttusMonth() throws Exception {
        return vytFrdSttusDAO.selectFrdSvySttusMonth();
	}
	
}
