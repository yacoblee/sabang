package or.sabang.sys.vyt.ecb.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface VytEcbSttusService {

	List<EgovMap> selectAprSvySttusTotCnt() throws Exception;
	
	List<EgovMap> selectAprSvySttusRegion() throws Exception;
	
	List<EgovMap> selectAprSvySttusMonth() throws Exception;
}
