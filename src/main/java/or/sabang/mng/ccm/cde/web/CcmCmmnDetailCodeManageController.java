package or.sabang.mng.ccm.cde.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.mng.ccm.cca.service.CcmCmmnCodeManageService;
import or.sabang.mng.ccm.cca.service.CmmnCodeVO;
import or.sabang.mng.ccm.ccc.service.CcmCmmnClCodeManageService;
import or.sabang.mng.ccm.ccc.service.CmmnClCodeVO;
import or.sabang.mng.ccm.cde.service.CcmCmmnDetailCodeManageService;
import or.sabang.mng.ccm.cde.service.CmmnDetailCodeVO;

/**
*
* 공통상세코드에 관한 요청을 받아 서비스 클래스로 요청을 전달하고 서비스클래스에서 처리한 결과를 웹 화면으로 전달을 위한 Controller를 정의한다
* @author 공통서비스 개발팀 이중호
* @since 2009.04.01
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*   수정일      수정자           수정내용
*  -------    --------    ---------------------------
*   2009.04.01  이중호       최초 생성
*   2011.08.26	정진오	IncludedInfo annotation 추가
*   2017.08.08	이정은	표준프레임워크 v3.7 개선
*
* </pre>
*/

@Controller
public class CcmCmmnDetailCodeManageController {

	@Resource(name = "cmmnDetailCodeManageService")
   private CcmCmmnDetailCodeManageService cmmnDetailCodeManageService;

	@Resource(name = "cmmnClCodeManageService")
   private CcmCmmnClCodeManageService cmmnClCodeManageService;

	@Resource(name = "cmmnCodeManageService")
   private CcmCmmnCodeManageService cmmnCodeManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Autowired
	private DefaultBeanValidator beanValidator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CcmCmmnDetailCodeManageController.class);
	
	   /**
		 * 공통상세코드 목록을 조회한다.
	     * @param loginVO
	     * @param searchVO
	     * @param model
	     * @return "mng/ccm/cde/ccmCmmnDetailCodeList"
	     * @throws Exception
	     */
		@IncludedInfo(name="공통상세코드", listUrl="/mng/ccm/cde/selectCcmCmmnDetailCodeList.do", order = 970 ,gid = 60)
	    @RequestMapping(value="/mng/ccm/cde/selectCcmCmmnDetailCodeList.do")
		public String selectCmmnDetailCodeList (@ModelAttribute("loginVO") LoginVO loginVO, @ModelAttribute("searchVO") CmmnDetailCodeVO searchVO
				, ModelMap model
				) throws Exception {
			
	    	/** EgovPropertyService.sample */
	    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
	    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

	    	/** pageing */
	    	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	        List<?> CmmnCodeList = cmmnDetailCodeManageService.selectCmmnDetailCodeList(searchVO);
	        model.addAttribute("resultList", CmmnCodeList);

	        int totCnt = cmmnDetailCodeManageService.selectCmmnDetailCodeListTotCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
	        model.addAttribute("paginationInfo", paginationInfo);

	        return "mng/ccm/cde/ccmCmmnDetailCodeList";
		}
		
		/**
		 * 공통상세코드 상세항목을 조회한다.
		 * @param loginVO
		 * @param cmmnDetailCodeVO
		 * @param model
		 * @return "mng/ccm/cde/ccmCmmnDetailCodeDetail"
		 * @throws Exception
		 */
		@RequestMapping(value="/mng/ccm/cde/selectCcmCmmnDetailCodeDetail.do")
	 	public String selectCmmnDetailCodeDetail (@ModelAttribute("loginVO") LoginVO loginVO
	 			, CmmnDetailCodeVO cmmnDetailCodeVO,	ModelMap model
	 			)	throws Exception {
	    	CmmnDetailCode vo = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
			model.addAttribute("result", vo);

			return "mng/ccm/cde/ccmCmmnDetailCodeDetail";
		}
		
		/**
		 * 공통상세코드를 삭제한다.
		 * @param loginVO
		 * @param cmmnDetailCodeVO
		 * @param model
		 * @return "forward:/mng/ccm/cde/ccmCmmnDetailCodeList.do"
		 * @throws Exception
		 */
	    @RequestMapping(value="/mng/ccm/cde/removeCcmCmmnDetailCode.do")
		public ModelAndView deleteCmmnDetailCode (@ModelAttribute("loginVO") LoginVO loginVO, CmmnDetailCodeVO cmmnDetailCodeVO, ModelMap model) throws Exception {
	    	ModelAndView mv = new ModelAndView("jsonView");
			try {
				cmmnDetailCodeManageService.deleteCmmnDetailCode(cmmnDetailCodeVO);
				mv.addObject("status","success");
				mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
			} catch(Exception e) {
				LOGGER.error(e.getMessage());
				mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
			return mv;
		}
	    
		/**
		 * 공통상세코드 등록을 위한 등록페이지로 이동한다.
		 * 
		 * @param cmmnDetailCodeVO
		 * @param model
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/mng/ccm/cde/registCcmCmmnDetailCodeView.do")
		public String insertCmmnDetailCodeView(@ModelAttribute("loginVO") LoginVO loginVO, @ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
				@ModelAttribute("cmmnDetailCodeVO") CmmnDetailCodeVO cmmnDetailCodeVO
				,ModelMap model) throws Exception {
			
			CmmnClCodeVO searchClCodeVO = new CmmnClCodeVO();
            searchClCodeVO.setFirstIndex(0);
	        List<?> CmmnClCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchClCodeVO);
	        model.addAttribute("clCodeList", CmmnClCodeList);

	    
	        CmmnCodeVO clCode = new CmmnCodeVO(); 
	        clCode.setClCode(cmmnCodeVO.getClCode());
	        
	        if (cmmnCodeVO.getClCode().equals("")) {

	        }else{
            
            	CmmnCodeVO searchCodeVO = new CmmnCodeVO();
            	searchCodeVO.setRecordCountPerPage(999999);
                searchCodeVO.setFirstIndex(0);
            	searchCodeVO.setSearchCondition("clCode");
                searchCodeVO.setSearchKeyword(cmmnCodeVO.getClCode());
                
    	        List<?> CmmnCodeList = cmmnCodeManageService.selectCmmnCodeList(searchCodeVO);
    	        model.addAttribute("codeList", CmmnCodeList);
	        } 	
	        
			return "mng/ccm/cde/ccmCmmnDetailCodeRegist";
		}
		
		/**
	     * 공통상세코드를 등록한다.
	     * 
	     * @param CmmnDetailCodeVO
	     * @param CmmnDetailCodeVO
	     * @param status
	     * @param model
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping("/mng/ccm/cde/registCcmCmmnDetailCode.do")
	    public ModelAndView insertCmmnDetailCode(@ModelAttribute("cmmnDetailCodeVO") CmmnDetailCodeVO cmmnDetailCodeVO,
	    		BindingResult bindingResult, ModelMap model) throws Exception {

	    	ModelAndView mv = new ModelAndView("jsonView");
	    	try {
	    		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		    	CmmnClCodeVO searchClCodeVO = new CmmnClCodeVO();
				beanValidator.validate(cmmnDetailCodeVO, bindingResult);
				if (bindingResult.hasErrors()) {
					List<?> CmmnClCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchClCodeVO);
					mv.addObject("clCodeList", CmmnClCodeList);
					mv.addObject("status","fail");
					mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
					return mv;
				}
				if(cmmnDetailCodeVO.getCodeId() != null){
					CmmnDetailCode vo = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
					if(vo != null){
						List<?> CmmnClCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchClCodeVO);
						model.addAttribute("clCodeList", CmmnClCodeList);
						mv.addObject("status","fail");
						mv.addObject("message", egovMessageSource.getMessage("comSymCcmCde.validate.codeCheck"));
						return mv;
					}
				} 
				
				cmmnDetailCodeVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    	cmmnDetailCodeManageService.insertCmmnDetailCode(cmmnDetailCodeVO);
		    	mv.addObject("status","success");
		    	mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
	    	} catch(Exception e) {
				LOGGER.error(e.getMessage());
				mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
			return mv;
	    }
	    
	    /**
	     * 공통상세코드 수정을 위한 수정페이지로 이동한다.
	     * 
	     * @param cmmnDetailCodeVO
	     * @param model
	     * @return "mng/ccm/cde/ccmCmmnDetailCodeUpdt"
	     * @throws Exception
	     */
	    @RequestMapping("/mng/ccm/cde/updateCcmCmmnDetailCodeView.do")
	    public String updateCmmnDetailCodeView(@ModelAttribute("loginVO") LoginVO loginVO, 
	    		@ModelAttribute("cmmnDetailCodeVO") CmmnDetailCodeVO cmmnDetailCodeVO, ModelMap model)
	    				throws Exception {
			
	    	CmmnDetailCode result = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
			model.addAttribute("cmmnDetailCodeVO", result);
		
			return "mng/ccm/cde/ccmCmmnDetailCodeUpdt";  
	    }
	    
	    /**
	     * 공통상세코드를 수정한다.
	     * 
	     * @param cmmnDetailCodeVO
	     * @param model
	     * @return "mng/ccm/cde/ccmCmmnDetailCodeUpdt", "/mng/ccm/cde/selectCcmCmmnDetailCodeList.do"
	     * @throws Exception
	     */
	    @RequestMapping("/mng/ccm/cde/updateCcmCmmnDetailCode.do")
	    public ModelAndView updateCmmnDetailCode(@ModelAttribute("cmmnDetailCodeVO") CmmnDetailCodeVO cmmnDetailCodeVO, ModelMap model, BindingResult bindingResult )
	    	throws Exception {
	        
        ModelAndView mv = new ModelAndView("jsonView");
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	    	beanValidator.validate(cmmnDetailCodeVO, bindingResult);
	    	if (bindingResult.hasErrors()){
	    		CmmnDetailCode result = cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(cmmnDetailCodeVO);
	    		model.addAttribute("cmmnDetailCodeVO", result);
	    		mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
	    	} else {
	    		cmmnDetailCodeVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		    	cmmnDetailCodeManageService.updateCmmnDetailCode(cmmnDetailCodeVO);
				mv.addObject("status","success");
				mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
	    	}
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
}	
