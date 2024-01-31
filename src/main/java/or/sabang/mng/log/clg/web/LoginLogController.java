package or.sabang.mng.log.clg.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import egovframework.com.cmm.annotation.IncludedInfo;
import or.sabang.mng.log.clg.service.LoginLogService;
import or.sabang.mng.log.lgm.service.SysLog;
import or.sabang.mng.log.clg.service.LoginLog;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Class Name : EgovLoginLogController.java
 * @Description : 접속로그정보를 관리하기 위한 컨트롤러 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------      -------     -------------------
 *    2009. 3. 11. 이삼섭        최초생성
 *    2011. 7. 01. 이기하        패키지 분리(sym.log -> sym.log.clg)
 *    2011.8.26	정진오			IncludedInfo annotation 추가
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */

@Controller
public class LoginLogController {

	@Resource(name="loginLogService")
	private LoginLogService loginLogService;

	@Resource(name="propertiesService")
	protected EgovPropertyService propertyService;

	/**
	 * 로그인 로그 목록 조회
	 *
	 * @param loginLog
	 * @return sym/log/clg/EgovLoginLogList
	 * @throws Exception
	 */
	@IncludedInfo(name="접속로그관리", order = 1080 ,gid = 60)
	@RequestMapping(value="/mng/log/clg/selectLoginLogList.do")
	public String selectLoginLogInf(@ModelAttribute("searchVO") LoginLog loginLog,
			ModelMap model) throws Exception{

		loginLog.setPageUnit(propertyService.getInt("pageUnit"));
		loginLog.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(loginLog.getPageIndex());
		paginationInfo.setRecordCountPerPage(loginLog.getPageUnit());
		paginationInfo.setPageSize(loginLog.getPageSize());

		loginLog.setFirstIndex(paginationInfo.getFirstRecordIndex());
		loginLog.setLastIndex(paginationInfo.getLastRecordIndex());
		loginLog.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		HashMap<?, ?> _map = (HashMap<?, ?>)loginLogService.selectLoginLogInf(loginLog);
		int totCnt = Integer.parseInt((String)_map.get("resultCnt"));

		model.addAttribute("resultList", _map.get("resultList"));
		model.addAttribute("resultCnt", _map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "mng/log/clg/loginLogList";
	}

	/**
	 * 로그인 로그 상세 조회
	 *
	 * @param loginLog
	 * @param model
	 * @return sym/log/clg/EgovLoginLogInqire
	 * @throws Exception
	 */
	@RequestMapping(value="/mng/log/clg/selectLoginLogDetail.do")
	public String selectLoginLog(@ModelAttribute("searchVO") LoginLog loginLog,
			@RequestParam("logId") String logId,
			ModelMap model) throws Exception{
		

		loginLog.setLogId(logId.trim());

		LoginLog vo = loginLogService.selectLoginLog(loginLog);
		model.addAttribute("result", vo);
		return "mng/log/clg/loginLogDetail";
	}
	
	/**
	 * 로그인 로그 엑셀 저장
	 * @param loginLog
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mng/log/clg/selectLoginLogListExcel.do")
	public ModelAndView selectSysLogExcel(LoginLog loginLog) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
    	HashMap<?, ?> _map = (HashMap<?, ?>)loginLogService.selectLoginLogExcel(loginLog);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String fileNm = "loginlog_".concat(formater.format(new Date()).toString());
    	String[] columnArray = {"로그ID","발생일자","접속방식","사용자명","접속IP"};
    	String[] columnVarArray = {"logId","creatDt","conectMthd","loginNm","conectIp"};
    	
    	dataMap.put("columnArr", columnArray);
    	dataMap.put("columnVarArr", columnVarArray);
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}

}
