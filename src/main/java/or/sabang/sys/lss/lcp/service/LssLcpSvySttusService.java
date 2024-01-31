package or.sabang.sys.lss.lcp.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssLcpSvySttusService {

	List<EgovMap> selectLcpSvySttusTotCnt(HashMap<String, Object> map) throws Exception;
	
	List<EgovMap> selectLcpSvySttusRegion() throws Exception;
	
	List<EgovMap> selectLcpSvySttusMonth() throws Exception;
}
