package or.sabang.sys.vyt.frd.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface VytFrdSttusService {

	List<EgovMap> selectFrdSvySttusTotCnt() throws Exception;
	
	List<EgovMap> selectFrdSvySttusCompentAuth() throws Exception;
	
	List<EgovMap> selectFrdSvySttusRegion() throws Exception;
	
	List<EgovMap> selectFrdSvySttusMonth() throws Exception;
	
}
