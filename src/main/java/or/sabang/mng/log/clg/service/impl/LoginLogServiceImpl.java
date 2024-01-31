package or.sabang.mng.log.clg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import or.sabang.mng.log.clg.service.LoginLog;
import or.sabang.mng.log.clg.service.LoginLogService;
import or.sabang.mng.log.lgm.service.SysLog;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovLoginLogServiceImpl.java
 * @Description : 접속로그 관리를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *       수정일       수정자         수정내용
 *      -------        -------     -------------------
 *    2009. 3. 11.     이삼섭        최초생성
 *    2011. 7. 01.     이기하        패키지 분리(stm.log -> sym.log.clg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("loginLogService")
public class LoginLogServiceImpl extends EgovAbstractServiceImpl implements
	LoginLogService {

	@Resource(name="loginLogDAO")
	private LoginLogDAO loginLogDAO;

    /** ID Generation */
	@Resource(name="egovLoginLogIdGnrService")
	private EgovIdGnrService egovLoginLogIdGnrService;

	/**
	 * 접속로그를 기록한다.
	 *
	 * @param LoginLog
	 */
	@Override
	public void logInsertLoginLog(LoginLog loginLog) throws Exception {
		String logId = egovLoginLogIdGnrService.getNextStringId();
		loginLog.setLogId(logId);

		loginLogDAO.logInsertLoginLog(loginLog);
	}

	/**
	 * 접속로그를 조회한다.
	 *
	 * @param loginLog
	 * @return loginLog
	 * @throws Exception
	 */
	@Override
	public LoginLog selectLoginLog(LoginLog loginLog) throws Exception{

		return loginLogDAO.selectLoginLog(loginLog);
	}

	/**
	 * 접속로그 목록을 조회한다.
	 *
	 * @param LoginLog
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<?, ?> selectLoginLogInf(LoginLog loginLog) throws Exception {
		List<?> _result = loginLogDAO.selectLoginLogInf(loginLog);
		int _cnt = loginLogDAO.selectLoginLogInfCnt(loginLog);

		Map<String, Object> _map = new HashMap();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));

		return _map;
	}

	/**
	 * 접속로그정보 목록을 조회하여 엑셀로 저장한다.
	 * @param loginLog
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> selectLoginLogExcel(LoginLog loginLog) throws Exception {
		loginLog.setSearchBgnDe(loginLog.getSearchBgnDe().replaceAll("[^0-9]",""));
		loginLog.setSearchEndDe(loginLog.getSearchEndDe().replaceAll("[^0-9]",""));
		
		List<?> _result = loginLogDAO.selectLoginLogExcel(loginLog);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}
}
