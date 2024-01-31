package or.sabang.mng.log.lgm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import or.sabang.mng.log.lgm.service.SysLog;
import or.sabang.mng.log.lgm.service.SysLogService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * @Class Name : EgovSysLogServiceImpl.java
 * @Description : 로그관리(시스템)를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("sysLogService")
public class SysLogServiceImpl extends EgovAbstractServiceImpl implements SysLogService{
	@Resource(name="sysLogDAO")
	private SysLogDAO sysLogDAO;

    /** ID Generation */
	@Resource(name="egovSysLogIdGnrService")
	private EgovIdGnrService egovSysLogIdGnrService;

	/**
	 * 시스템 로그정보를 생성한다.
	 *
	 * @param SysLog
	 */
	@Override
	public void logInsertSysLog(SysLog sysLog) throws Exception {
		String requstId = egovSysLogIdGnrService.getNextStringId();
		sysLog.setRequstId(requstId);

		sysLogDAO.logInsertSysLog(sysLog);
		
	}

	/**
	 * 시스템 로그정보를 요약한다.
	 *
	 * @param
	 */
	@Override
	public void logInsertSysLogSummary() throws Exception {
		sysLogDAO.logInsertSysLogSummary();
		
	}

	/**
	 * 시스템 로그정보 목록을 조회한다.
	 *
	 * @param SysLog
	 */
	@Override
	public Map<?, ?> selectSysLogInf(SysLog sysLog) throws Exception {

		List<?> _result = sysLogDAO.selectSysLogInf(sysLog);
		int _cnt = sysLogDAO.selectSysLogInfCnt(sysLog);

		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		_map.put("resultCnt", Integer.toString(_cnt));

		return _map;
	}

	/**
	 * 시스템 로그 상세정보를 조회한다.
	 *
	 * @param sysLog
	 * @return sysLog
	 * @throws Exception
	 */
	@Override
	public SysLog selectSysLog(SysLog sysLog) throws Exception {
		return sysLogDAO.selectSysLog(sysLog);
	}
	
	/**
	 * 시스템로그정보 목록을 조회하여 엑셀로 저장한다.
	 * @param sysLog
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> selectSysLogExcel(SysLog sysLog) throws Exception {
		sysLog.setSearchBgnDe(sysLog.getSearchBgnDe().replaceAll("[^0-9]",""));
		sysLog.setSearchEndDe(sysLog.getSearchEndDe().replaceAll("[^0-9]",""));
		
		List<?> _result = sysLogDAO.selectSysLogExcel(sysLog);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}

}
