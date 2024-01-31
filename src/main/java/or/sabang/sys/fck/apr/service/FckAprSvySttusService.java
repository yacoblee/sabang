package or.sabang.sys.fck.apr.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FckAprSvySttusService {

	List<EgovMap> selectAprSvySttusTotCnt() throws Exception;
	
	List<EgovMap> selectAprSvySttusRegion() throws Exception;
	
	List<EgovMap> selectAprSvySttusMonth() throws Exception;
}
