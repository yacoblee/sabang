package or.sabang.sys.fck.pcs.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 정밀점검 조사현황 차트 
 * @author DEVWORK
 *
 */
public interface FckPcsSvySttusService {

	/**
	 * 정밀점검 전체 건수 
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 9. 20.
	 * @modified
	 */
	List<EgovMap> selectPcsSvySttusTotCnt() throws Exception;

	/**
	 * 정밀점검 조사지역
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 9. 20.
	 * @modified
	 */
	List<EgovMap> selectPcsSvySttusRegion() throws Exception;

	/**
	 * 정밀점검 월별 현황
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 9. 20.
	 * @modified
	 */
	List<EgovMap> selectPcsSvySttusMonth() throws Exception;

	

	
	
}
