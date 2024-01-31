package or.sabang.cmm.web;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;

/**
 * @Class Name : EgovComUtlController.java
 * @Description : 공통유틸리티성 작업을 위한 Controller
 * @Modification Information
 * @
 * @ 수정일              수정자          수정내용
 * @ ----------  --------  ---------------------------
 *   2009.03.02  조재영          최초 생성
 *   2011.10.07  이기하          .action -> .do로 변경하면서 동일 매핑이 되어 삭제처리
 *   2015.11.12  김연호          한국인터넷진흥원 웹 취약점 개선
 *   2019.04.25  신용호          moveToPage() 화이트리스트 처리
 *
 *  @author 공통서비스 개발팀 조재영
 *  @since 2009.03.02
 *  @version 1.0
 *  @see
 *
 */
@Controller
public class ComUtlController {

    //@Resource(name = "egovUserManageService")
    //private EgovUserManageService egovUserManageService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComUtlController.class);
	
//	@Resource(name = "egovPageLinkWhitelist")
//    protected List<String> egovWhitelist;

    /** AdministZoneService */
    @Resource(name = "administZoneService")
    protected AdministZoneService administZoneService;
	
    /** cmmUseService */
    @Resource(name = "cmmUseService")
    protected CmmUseService cmmUseService;
    
    /**
     * 행정구역 시도리스트를 조회한다.
     * @param adminVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cmm/selectAdministZoneCtprvn.do")
	public ModelAndView selectAdministZoneCtprvn(@ModelAttribute("adminVO") AdministZoneVO adminVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<?> list = administZoneService.selectAdministZoneCtprvn();
		mv.addObject("result",list);
		
		return mv;
	}
	
    /**
     * 행정구역 시군구리스트를 조회한다.
     * @param adminVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping("/cmm/selectAdministZoneSigngu.do")
	public ModelAndView selectAdministZoneSigngu(@ModelAttribute("adminVO") AdministZoneVO adminVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<AdministZoneVO> list = administZoneService.selectAdministZoneSigngu(adminVO);
		mv.addObject("result",list);
		
		return mv;
	}
	
	/**
	 * 행정구역 읍면동리스트를 조회한다.
	 * @param adminVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cmm/selectAdministZoneEmd.do")
	public ModelAndView selectAdministZoneEmd(@ModelAttribute("adminVO") AdministZoneVO adminVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<AdministZoneVO> list = administZoneService.selectAdministZoneEmd(adminVO);
		mv.addObject("result",list);
		
		return mv;
	}
	
	/**
	 * 행정구역 리 리스트를 조회한다.
	 * @param adminVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cmm/selectAdministZoneRi.do")
	public ModelAndView selectAdministZoneRi(@ModelAttribute("adminVO") AdministZoneVO adminVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<AdministZoneVO> list = administZoneService.selectAdministZoneRi(adminVO);
		mv.addObject("result",list);
		
		return mv;
	}
	
	
	@RequestMapping("/cmm/selectRegionDetail.do")
	public ModelAndView selectRegionDetail(@ModelAttribute("cmmnCodeVO") ComDefaultCodeVO cmmnCodeVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<CmmnDetailCode> list = cmmUseService.selectRegionDetail(cmmnCodeVO);
		mv.addObject("result",list);
		
		return mv;
	}
	
    /**
	 * validato rule dynamic Javascript
	 */
	@RequestMapping("/cmm/validator.do")
	public String validate(){
		return "cmm/validator";
	}
	
	@RequestMapping("/cmm/openClipReport.do")
	public String openClipReport(){
		return "cmm/clipReport";
	}
	
	/**
	 * API KEY 목록 조회
	 * @author ipodo
	 * @name selectAPIKeyController
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cmm/selectAPIKey.do")
	public ModelAndView selectAPIKeyController(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelView = new ModelAndView("jsonView");

		modelView.addObject("request", request.getParameterMap());
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orgNm", request.getParameter("org_nm"));
		
		try {
			List<EgovMap> list = cmmUseService.selectAPIKeyList(map);
			
			modelView.addObject("status", 200);
			modelView.addObject("result",list);
		}
		catch (Exception e){
			modelView.addObject("status", 500);
			modelView.addObject("result","[]");			
		}

		return modelView;
	}

	
	/**
	 * @author jooth
	 * @name proxyRequest
	 * @created 2020. 11. 24. 오후 2:33:39
	 * @modifed 2020. 11. 24. 오후 2:33:39
	 * @param request
	 * @param response
	 * @throws Exception
	 * @Description proxy 
	 */
	@RequestMapping(value = "/cmm/proxy.do")
	public void proxyRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			cmmUseService.proxy(request,response);
		} catch (Exception e) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			response.setContentType( "text/plain" );
		}
		
	}
}