package or.sabang.mng.ccm.cca.web;

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
import or.sabang.mng.ccm.cca.service.CcmCmmnCodeManageService;
import or.sabang.mng.ccm.cca.service.CmmnCode;
import or.sabang.mng.ccm.cca.service.CmmnCodeVO;
import or.sabang.mng.ccm.ccc.service.CcmCmmnClCodeManageService;
import or.sabang.mng.ccm.ccc.service.CmmnClCodeVO;

@Controller
public class CcmCmmnCodeManageController {
	
	@Resource(name = "cmmnCodeManageService")
    private CcmCmmnCodeManageService cmmnCodeManageService;

	@Resource(name = "cmmnClCodeManageService")
    private CcmCmmnClCodeManageService cmmnClCodeManageService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CcmCmmnCodeManageController.class);
	
	/**
	 * 공통분류코드 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @param model
	 * @return "mng/ccm/cca/ccmCmmnCodeList"
	 * @throws Exception
	 */
	@IncludedInfo(name = "공통코드", listUrl = "/mng/ccm/cca/selectCcmCmmnCodeList.do", order = 980, gid = 60)
	@RequestMapping(value = "/mng/ccm/cca/selectCcmCmmnCodeList.do")
	public String selectCmmnCodeList(@ModelAttribute("searchVO") CmmnCodeVO searchVO, ModelMap model)
			throws Exception {
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

		List<?> CmmnCodeList = cmmnCodeManageService.selectCmmnCodeList(searchVO);
		model.addAttribute("resultList", CmmnCodeList);

		int totCnt = cmmnCodeManageService.selectCmmnCodeListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "mng/ccm/cca/ccmCmmnCodeList";
	}
	
	/**
	 * 공통코드 상세항목을 조회한다.
	 * 
	 * @param loginVO
	 * @param cmmnCodeVO
	 * @param model
	 * @return "mng/ccm/cca/ccmCmmnCodeDetail"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/ccm/cca/selectCcmCmmnCodeDetail.do")
	public String selectCmmnCodeDetail(@ModelAttribute("loginVO") LoginVO loginVO, CmmnCodeVO cmmnCodeVO, ModelMap model) throws Exception {
		
		CmmnCodeVO vo = cmmnCodeManageService.selectCmmnCodeDetail(cmmnCodeVO);
		
		model.addAttribute("result", vo);

		return "mng/ccm/cca/ccmCmmnCodeDetail";
	}
	
	/**
	 * 공통코드 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param cmmnCodeVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mng/ccm/cca/registCcmCmmnCodeView.do")
	public String insertCmmnCodeView(@ModelAttribute("cmmnCodeVO")CmmnCodeVO cmmnCodeVO, ModelMap model) throws Exception {
		
		CmmnClCodeVO searchVO = new CmmnClCodeVO();
		searchVO.setFirstIndex(0);
        List<?> CmmnCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
        
        model.addAttribute("clCodeList", CmmnCodeList);

		return "mng/ccm/cca/ccmCmmnCodeRegist";
	}
	
	/**
     * 공통코드를 등록한다.
     * 
     * @param CmmnCodeVO
     * @param CmmnCodeVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/cca/registCcmCmmnCode.do")
    public ModelAndView insertCmmnCode(@ModelAttribute("searchVO") CmmnCodeVO cmmnCode, @ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
    	ModelAndView mv = new ModelAndView("jsonView");
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			CmmnClCodeVO searchVO = new CmmnClCodeVO();
			beanValidator.validate(cmmnCodeVO, bindingResult);
			if (bindingResult.hasErrors()) {
		        List<?> CmmnCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
		        model.addAttribute("clCodeList", CmmnCodeList);
		        mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
				return mv;
			}
			if(cmmnCode.getCodeId() != null){
				CmmnCode vo = cmmnCodeManageService.selectCmmnCodeDetail(cmmnCode);
				if(vo != null){
					searchVO.setFirstIndex(0);
			        List<?> CmmnCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
			        model.addAttribute("clCodeList", CmmnCodeList);
			        mv.addObject("status","fail");
					mv.addObject("message", egovMessageSource.getMessage("mngCcmCca.validate.codeCheck"));
					return mv;
				}
			} 
			
			cmmnCodeVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			cmmnCodeManageService.insertCmmnCode(cmmnCodeVO);
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
     * 공통코드를 삭제한다.
     * 
     * @param cmmnCodeVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/cca/removeCcmCmmnCode.do")
    public ModelAndView deleteCmmnCode(@ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
    	ModelAndView mv = new ModelAndView("jsonView");
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			cmmnCodeVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			cmmnCodeManageService.deleteCmmnCode(cmmnCodeVO);
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
     * 공통코드 수정을 위한 수정페이지로 이동한다.
     * 
     * @param cmmnCodeVO
     * @param model
     * @return "mng/ccm/cca/ccmCmmnCodeUpdt"
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/cca/updateCcmCmmnCodeView.do")
    public String updateCmmnCodeView(@ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO, ModelMap model)
	    throws Exception {
		
    	CmmnCode result = cmmnCodeManageService.selectCmmnCodeDetail(cmmnCodeVO);
		
		model.addAttribute("cmmnCodeVO", result);
	
		return "mng/ccm/cca/ccmCmmnCodeUpdt";  
    }
    
    /**
     * 공통코드를 수정한다.
     * 
     * @param cmmnCodeVO
     * @param status
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/cca/updateCcmCmmnCode.do")
    public ModelAndView updateCmmnCode(@ModelAttribute("searchVO") CmmnCodeVO cmmnCode, @ModelAttribute("cmmnCodeVO") CmmnCodeVO cmmnCodeVO,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
    	ModelAndView mv = new ModelAndView("jsonView");
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			beanValidator.validate(cmmnCodeVO, bindingResult);
			if (bindingResult.hasErrors()) {
				CmmnCode result = cmmnCodeManageService.selectCmmnCodeDetail(cmmnCode);
				model.addAttribute("cmmnCodeVO", result);
		        mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			} else {
				cmmnCodeVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
				cmmnCodeManageService.updateCmmnCode(cmmnCodeVO);
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