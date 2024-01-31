package or.sabang.mng.log.lgm.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.annotation.IncludedInfo;
import or.sabang.mng.log.lgm.service.SysLog;
import or.sabang.mng.log.lgm.service.SysLogService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : EgovSysLogController.java
 * @Description : 시스템 로그정보를 관리하기 위한 컨트롤러 클래스
 * @Modification Information
 *
 *    수정일        수정자         수정내용
 *    -------       -------     -------------------
 *    2009. 3. 11.  이삼섭         최초생성
 *    2011. 7. 01.  이기하         패키지 분리(sym.log -> sym.log.lgm)
 *    2011.8.26	정진오			IncludedInfo annotation 추가
 *    2017.09.14	이정은			표준프레임워크 v3.7 개선
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */

@Controller
public class SysLogController {
	
	@Resource(name="sysLogService")
	private SysLogService sysLogService;
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	/**
	 * 시스템 로그 목록 조회
	 *
	 * @param sysLog
	 * @return sym/log/lgm/EgovSysLogList
	 * @throws Exception
	 */
	@IncludedInfo(name="로그관리", listUrl="/mng/log/lgm/selectSysLogList.do", order = 1030 ,gid = 60)
	@RequestMapping(value="/mng/log/lgm/selectSysLogList.do")
	public String selectSysLogInf(@ModelAttribute("searchVO") SysLog sysLog,
			ModelMap model) throws Exception{
		
    	/** EgovPropertyService.sample */
		sysLog.setPageUnit(propertiesService.getInt("pageUnit"));
		sysLog.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sysLog.getPageIndex());
		paginationInfo.setRecordCountPerPage(sysLog.getPageUnit());
		paginationInfo.setPageSize(sysLog.getPageSize());

		sysLog.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sysLog.setLastIndex(paginationInfo.getLastRecordIndex());
		sysLog.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		HashMap<?, ?> _map = (HashMap<?, ?>)sysLogService.selectSysLogInf(sysLog);
		int totCnt = Integer.parseInt((String)_map.get("resultCnt"));

		model.addAttribute("resultList", _map.get("resultList"));
		model.addAttribute("resultCnt", _map.get("resultCnt"));
		model.addAttribute("frm", sysLog);

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "mng/log/lgm/sysLogList";
		
	}
	/**
	 * 시스템 로그 상세 조회
	 *
	 * @param sysLog
	 * @param model
	 * @return sym/log/lgm/EgovSysLogInqire
	 * @throws Exception
	 */
	@RequestMapping(value="/mng/log/lgm/selectSysLogDetail.do")
	public String selectSysLog(@ModelAttribute("searchVO") SysLog sysLog,
			@RequestParam("requstId") String requstId,
			ModelMap model) throws Exception{

		sysLog.setRequstId(requstId.trim());

		SysLog vo = sysLogService.selectSysLog(sysLog);
		model.addAttribute("result", vo);
		return "mng/log/lgm/sysLogDetail";
	}
	
	/**
	 * 시스템 로그 엑셀 저장
	 * @param sysLog
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/mng/log/lgm/selectSysLogListExcel.do")
	public ModelAndView selectSysLogExcel(SysLog sysLog) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
    	HashMap<?, ?> _map = (HashMap<?, ?>)sysLogService.selectSysLogExcel(sysLog);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String fileNm = "syslog_".concat(formater.format(new Date()).toString());
    	String[] columnArray = {"요청ID","발생일자","메소드명","처리구분","처리시간","요청자"};
    	String[] columnVarArray = {"requstId","occrrncDe","methodNm","processSeCodeNm","processTime","rqesterNm"};
    	
    	dataMap.put("columnArr", columnArray);
    	dataMap.put("columnVarArr", columnVarArray);
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}
}
