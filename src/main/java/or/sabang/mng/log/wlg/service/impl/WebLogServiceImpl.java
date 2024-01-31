package or.sabang.mng.log.wlg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import or.sabang.mng.log.wlg.service.WebLog;
import or.sabang.mng.log.wlg.service.WebLogService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovWebLogServiceImpl.java
 * @Description : 웹로그 관리를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.wlg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("webLogService")
public class WebLogServiceImpl extends EgovAbstractServiceImpl implements
	WebLogService {

	@Resource(name="webLogDAO")
	private WebLogDAO webLogDAO;

    /** ID Generation */
	@Resource(name="egovWebLogIdGnrService")
	private EgovIdGnrService egovWebLogIdGnrService;

	/**
	 * 웹 로그를 기록한다.
	 *
	 * @param WebLog
	 */
	@Override
	public void logInsertWebLog(WebLog webLog) throws Exception {
		String requstId = egovWebLogIdGnrService.getNextStringId();
		webLog.setRequstId(requstId);

		webLogDAO.logInsertWebLog(webLog);
	}

	/**
	 * 웹 로그정보를 요약한다.
	 *
	 * @param
	 */
	@Override
	public void logInsertWebLogSummary() throws Exception {

		webLogDAO.logInsertWebLogSummary();
	}

	/**
	 * 웹 로그정보 상제정보를 조회한다.
	 *
	 * @param webLog
	 * @return webLog
	 * @throws Exception
	 */
	@Override
	public WebLog selectWebLog(WebLog webLog) throws Exception{

		return webLogDAO.selectWebLog(webLog);
	}

	/**
	 * 웹 로그정보 목록을 조회한다.
	 *
	 * @param WebLog
	 */
	@Override
	public Map<String, Object> selectWebLogInf(WebLog webLog) throws Exception {
		List<?> _result = webLogDAO.selectWebLogInf(webLog);
		int _cnt = webLogDAO.selectWebLogInfCnt(webLog);

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));

		return _map;
	}
	
	/**
	 * 웹 로그정보 목록을 조회하여 엑셀로 저장한다.
	 * @param webLog
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> selectWebLogExcel(WebLog webLog) throws Exception {
		webLog.setSearchBgnDe(webLog.getSearchBgnDe().replaceAll("[^0-9]",""));
		webLog.setSearchEndDe(webLog.getSearchEndDe().replaceAll("[^0-9]",""));
		
		List<?> _result = webLogDAO.selectWebLogExcel(webLog);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}

}
