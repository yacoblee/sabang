package or.sabang.sys.fck.apr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprSvySttusService;
import or.sabang.sys.fck.apr.service.FckAprComptVO;

@Service("fckAprSvySttusService")
public class FckAprSvySttusServiceImpl extends EgovAbstractServiceImpl implements FckAprSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="fckAprSvySttusDAO")
	private FckAprSvySttusDAO fckAprSvySttusDAO;
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public List<EgovMap> selectAprSvySttusTotCnt() throws Exception {
        return fckAprSvySttusDAO.selectAprSvySttusTotCnt();
	}
	
	@Override
	public List<EgovMap> selectAprSvySttusRegion() throws Exception {
        return fckAprSvySttusDAO.selectAprSvySttusRegion();
	}
	
	@Override
	public List<EgovMap> selectAprSvySttusMonth() throws Exception {
        return fckAprSvySttusDAO.selectAprSvySttusMonth();
	}
}
