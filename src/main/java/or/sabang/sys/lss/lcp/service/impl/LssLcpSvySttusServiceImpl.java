package or.sabang.sys.lss.lcp.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.lcp.service.LssLcpSvySttusService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;

@Service("lssLcpSvySttusService")
public class LssLcpSvySttusServiceImpl extends EgovAbstractServiceImpl implements LssLcpSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssLcpSvySttusDAO")
	private LssLcpSvySttusDAO lssLcpSvySttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectLcpSvySttusTotCnt(HashMap<String, Object> map) throws Exception {
        return lssLcpSvySttusDAO.selectLcpSvySttusTotCnt(map);
	}
	
	@Override
	public List<EgovMap> selectLcpSvySttusRegion() throws Exception {
        return lssLcpSvySttusDAO.selectLcpSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectLcpSvySttusMonth() throws Exception {
        return lssLcpSvySttusDAO.selectLcpSvySttusMonth();
	}
}
