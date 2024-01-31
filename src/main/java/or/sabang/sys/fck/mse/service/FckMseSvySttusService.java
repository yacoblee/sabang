package or.sabang.sys.fck.mse.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FckMseSvySttusService {

	List<EgovMap> selectMseSvySttusTotCnt() throws Exception;
	
	List<EgovMap> selectMseSvySttusRegion() throws Exception;
	
	List<EgovMap> selectMseSvySttusMonth() throws Exception;

}
