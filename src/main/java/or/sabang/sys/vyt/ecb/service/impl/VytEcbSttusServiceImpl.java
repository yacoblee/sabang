package or.sabang.sys.vyt.ecb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.vyt.ecb.service.VytEcbSttusService;

@Service("vytEcbSttusService")
public class VytEcbSttusServiceImpl extends EgovAbstractServiceImpl implements VytEcbSttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(VytEcbSttusServiceImpl.class);
	
	@Resource(name="vytEcbSttusDAO")
	private VytEcbSttusDAO vytEcbSttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectAprSvySttusTotCnt() throws Exception {
        return vytEcbSttusDAO.selectEcbSttusTotCnt();
	}
	
	@Override
	public List<EgovMap> selectAprSvySttusRegion() throws Exception {
        return vytEcbSttusDAO.selectEcbSttusRegion();
	}
	
	@Override
	public List<EgovMap> selectAprSvySttusMonth() throws Exception {
        return vytEcbSttusDAO.selectEcbSttusMonth();
	}
}
