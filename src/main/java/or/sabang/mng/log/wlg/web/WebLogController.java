package or.sabang.mng.log.wlg.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.annotation.IncludedInfo;
import or.sabang.mng.log.wlg.service.WebLogService;
import or.sabang.mng.log.wlg.service.WebLog;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;

/**
 * @Class Name : EgovWebLogController.java
 * @Description : 시스템 로그정보를 관리하기 위한 컨트롤러 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.wlg)
 *    2011.8.26	정진오			IncludedInfo annotation 추가
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */

@Controller
public class WebLogController {

	@Resource(name="webLogService")
	private WebLogService webLogService;

	@Resource(name="propertiesService")
	protected EgovPropertyService propertyService;

	/**
	 * 웹 로그 목록 조회
	 *
	 * @param webLog
	 * @return sym/log/wlg/EgovWebLogList
	 * @throws Exception
	 */
	@IncludedInfo(name="웹로그관리", listUrl="/mng/log/wlg/selectWebLogList.do", order = 1070 ,gid = 60)
	@RequestMapping(value="/mng/log/wlg/selectWebLogList.do")
	public String selectWebLogInf(@ModelAttribute("searchVO") WebLog webLog,
			ModelMap model) throws Exception{

		webLog.setPageUnit(propertyService.getInt("pageUnit"));
		webLog.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(webLog.getPageIndex());
		paginationInfo.setRecordCountPerPage(webLog.getPageUnit());
		paginationInfo.setPageSize(webLog.getPageSize());

		webLog.setFirstIndex(paginationInfo.getFirstRecordIndex());
		webLog.setLastIndex(paginationInfo.getLastRecordIndex());
		webLog.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		HashMap<?, ?> _map = (HashMap<?, ?>)webLogService.selectWebLogInf(webLog);
		int totCnt = Integer.parseInt((String)_map.get("resultCnt"));

		model.addAttribute("resultList", _map.get("resultList"));
		model.addAttribute("resultCnt", _map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "mng/log/wlg/webLogList";
	}

	/**
	 * 웹 로그 상세 조회
	 *
	 * @param webLog
	 * @param model
	 * @return mng/log/wlg/EgovWebLogInqire
	 * @throws Exception
	 */
	@RequestMapping(value="/mng/log/wlg/selectWebLogDetail.do")
	public String selectWebLog(@ModelAttribute("searchVO") WebLog webLog,
			@RequestParam("requstId") String requstId,
			ModelMap model) throws Exception{

		webLog.setRequstId(requstId.trim());

		WebLog vo = webLogService.selectWebLog(webLog);
		model.addAttribute("result", vo);
		return "mng/log/wlg/webLogDetail";
	}
	
	/**
	 * 웹 로그 엑셀 저장
	 * @param webLog
	 * @return modelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/mng/log/wlg/selectWebLogListExcel.do")
	public ModelAndView webLogListExcel(WebLog webLog) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
//    	List<EgovMap> list = webLogService.selectWebLogExcel(webLog);
    	HashMap<?, ?> _map = (HashMap<?, ?>)webLogService.selectWebLogExcel(webLog);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String fileNm = "weblog_".concat(formater.format(new Date()).toString());
    	String[] columnArray = {"요청ID","발생일자","URL","요청자IP","요청자"};
    	String[] columnVarArray = {"requstId","occrrncDe","url","rqesterIp","rqesterNm"};
    	
    	dataMap.put("columnArr", columnArray);
    	dataMap.put("columnVarArr", columnVarArray);
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}

}
