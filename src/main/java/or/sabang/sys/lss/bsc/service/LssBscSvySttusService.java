package or.sabang.sys.lss.bsc.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface LssBscSvySttusService {

	List<EgovMap> selectBscSvySttusTotCnt(HashMap<String, Object> map) throws Exception;
	
	List<EgovMap> selectBscSvySttusRegion() throws Exception;
	
	List<EgovMap> selectBscSvySttusMonth() throws Exception;
	
	/**
	 * 사용자ID별 기초조사 통계정보 모집단 갯수를 조회한다.
     * @param map
     * @return int
     */
	int selectBscSvyPopultnMember(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 사용자별 모집단 건수를 변경한다.
     * @param map
     */
	void insertBscSvyPopultn(HashMap<String, Object> map) throws Exception;
}
