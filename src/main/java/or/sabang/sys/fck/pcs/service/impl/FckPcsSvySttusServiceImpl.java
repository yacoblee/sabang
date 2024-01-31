package or.sabang.sys.fck.pcs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.pcs.service.FckPcsSvySttusService;

@Service("fckPcsSvySttusService")
public class FckPcsSvySttusServiceImpl extends EgovAbstractServiceImpl implements FckPcsSvySttusService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FckPcsSvySttusServiceImpl.class);
	
	@Resource(name="fckPcsSvySttusDAO")
	private FckPcsSvySttusDAO fckPcsSvySttusDAO;
	
	/**
	 * 정밀점검 전체 건수 
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 9. 20.
	 * @modified
	 */
	@Override
	public List<EgovMap> selectPcsSvySttusTotCnt() throws Exception{
		return fckPcsSvySttusDAO.selectPcsSvySttusTotCnt();
	};

	/**
	 * 정밀점검 조사지역
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 9. 20.
	 * @modified
	 */
	@Override
	public List<EgovMap> selectPcsSvySttusRegion() throws Exception{
		return fckPcsSvySttusDAO.selectPcsSvySttusRegion();
	};

	/**
	 * 정밀점검 월별 현황
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 9. 20.
	 * @modified
	 */
	@Override
	public List<EgovMap> selectPcsSvySttusMonth() throws Exception{
		return fckPcsSvySttusDAO.selectPcsSvySttusMonth();
	};
	 
}
