package or.sabang.sys.lss.cnl.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssCnlSvySttusService {
	
	List<EgovMap> selectCnlSvySttusTotCnt(HashMap<String, Object> map) throws Exception;
	
	List<EgovMap> selectCnlSvySttusRegion() throws Exception;
	
	List<EgovMap> selectCnlSvySttusMonth() throws Exception;

	/**
	 * 사용자ID별 취약지역 해제조사 통계정보 모집단 갯수를 조회한다.
     * @param map
     * @return int
     */
	int selectCnlSvyPopultnMember(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 사용자별 모집단 건수를 변경한다.
     * @param map
     */
	void insertCnlSvyPopultn(HashMap<String, Object> map) throws Exception;
	
}
