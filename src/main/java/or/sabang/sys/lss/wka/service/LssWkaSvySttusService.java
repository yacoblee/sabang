package or.sabang.sys.lss.wka.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssWkaSvySttusService {

	List<EgovMap> selectWkaSvySttusTotCnt() throws Exception;
	
	List<EgovMap> selectWkaSvySttusRegion() throws Exception;
	
	List<EgovMap> selectWkaSvySttusMonth() throws Exception;
}
