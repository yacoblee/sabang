package or.sabang.mng.ccm.ccc.web;

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
import or.sabang.mng.ccm.ccc.service.CcmCmmnClCodeManageService;
import or.sabang.mng.ccm.ccc.service.CmmnClCode;
import or.sabang.mng.ccm.ccc.service.CmmnClCodeVO;

@Controller
public class CcmCmmnClCodeManageController {
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CcmCmmnClCodeManageController.class);

	/**
	 * 공통분류코드 목록을 조회한다.
	 * 
	 * @param loginVO
	 * @param searchVO
	 * @param model
	 * @return "mng/ccm/ccc/selectCcmCmmnClCodeList"
	 * @throws Exception
	 */
	@IncludedInfo(name = "공통분류코드", listUrl = "/mng/ccm/ccc/selectCcmCmmnClCodeList.do", order = 960, gid = 60)
	@RequestMapping(value = "/mng/ccm/ccc/selectCcmCmmnClCodeList.do")
	public String selectCmmnClCodeList(@ModelAttribute("searchVO") CmmnClCodeVO searchVO, ModelMap model) throws Exception {
		
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

		List<?> CmmnCodeList = cmmnClCodeManageService.selectCmmnClCodeList(searchVO);
		model.addAttribute("resultList", CmmnCodeList);

		int totCnt = cmmnClCodeManageService.selectCmmnClCodeListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "mng/ccm/ccc/ccmCmmnClCodeList";
	}

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 * 
	 * @param loginVO
	 * @param cmmnClCode
	 * @param model
	 * @return "mng/ccm/ccc/selectCcmCmmnClCodeDetail.do"
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/ccm/ccc/selectCcmCmmnClCodeDetail.do")
	public String selectCmmnClCodeDetail(@ModelAttribute("loginVO") LoginVO loginVO, CmmnClCodeVO cmmnClCodeVO,
			ModelMap model) throws Exception {

		CmmnClCode vo = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCodeVO);

		model.addAttribute("result", vo);

		return "mng/ccm/ccc/ccmCmmnClCodeDetail";
	}
	
	/**
	 * 공통분류코드 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param cmmnClCodeVO
	 * @param model
	 * @return "mng/ccm/ccc/EgovCcmCmmnClCodeRegist";
	 * @throws Exception
	 */
	@RequestMapping("/mng/ccm/ccc/registCcmCmmnClCodeView.do")
	public String insertCmmnClCodeView(@ModelAttribute("searchVO")CmmnClCodeVO cmmnClCodeVO, ModelMap model) throws Exception {
		model.addAttribute("cmmnClCodeVO", new CmmnClCodeVO());

		return "mng/ccm/ccc/ccmCmmnClCodeRegist";
	}
	
	 /**
     * 공통분류코드를 등록한다.
     * 
     * @param CmmnClCodeVO
     * @param CmmnClCodeVO
     * @param status
     * @param model
     * @return /mng/ccm/ccc/selectCcmCmmnClCodeList.do";
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/ccc/registCcmCmmnClCode.do")
    public ModelAndView insertCmmnClCode(@ModelAttribute("cmmnClCodeVO") CmmnClCodeVO cmmnClCodeVO,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			beanValidator.validate(cmmnClCodeVO, bindingResult);
			if (bindingResult.hasErrors()) {
		        mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
				return mv;
			}
			if(cmmnClCodeVO.getClCode() != null){
				CmmnClCode vo = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCodeVO);
				if(vo != null){
					mv.addObject("status","fail");
					mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
					return mv;
				}
			} 
			
			cmmnClCodeVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			cmmnClCodeManageService.insertCmmnClCode(cmmnClCodeVO);
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
     * 공통분류코드를 삭제한다.
     * 
     * @param cmmnClCodeVO
     * @param status
     * @param model
     * @return /mng/ccm/ccc/selectCcmCmmnClCodeList.do";
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/ccc/removeCcmCmmnClCode.do")
    public ModelAndView deleteCmmnClCode(@ModelAttribute("searchVO") CmmnClCodeVO cmmnClCode, @ModelAttribute("cmmnClCodeVO") CmmnClCodeVO cmmnClCodeVO,
	    BindingResult bindingResult, ModelMap model) throws Exception {
    	
    	ModelAndView mv = new ModelAndView("jsonView");
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			cmmnClCodeVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			cmmnClCodeManageService.deleteCmmnClCode(cmmnClCodeVO);
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
     * 공통분류코드 수정을 위한 수정페이지로 이동한다.
     * 
     * @param cmmnClCodeVO
     * @param model
     * @return "mng/ccm/ccc/ccmCmmnClCodeUpdt";  
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/ccc/updateCcmCmmnClCodeView.do")
    public String updateCmmnClCodeView(@ModelAttribute("searchVO") CmmnClCodeVO cmmnClCodeVO, ModelMap model)
	    throws Exception {
		
    	CmmnClCode result = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCodeVO);
		
		model.addAttribute("cmmnClCodeVO", result);
	
		return "mng/ccm/ccc/ccmCmmnClCodeUpdt";  
    }
    
    /**
     * 공통분류코드를 수정한다.
     * 
     * @param cmmnClCodeVO
     * @param status
     * @param model
     * @return /mng/ccm/ccc/selectCcmCmmnClCodeList.do"
     * @throws Exception
     */
    @RequestMapping("/mng/ccm/ccc/updateCcmCmmnClCode.do")
    public ModelAndView updateCmmnClCode(@ModelAttribute("searchVO") CmmnClCodeVO cmmnClCode, @ModelAttribute("cmmnClCodeVO") CmmnClCodeVO cmmnClCodeVO,
	    BindingResult bindingResult, ModelMap model) throws Exception {

    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			beanValidator.validate(cmmnClCodeVO, bindingResult);
			if (bindingResult.hasErrors()) {
				CmmnClCode result = cmmnClCodeManageService.selectCmmnClCodeDetail(cmmnClCode);
			    model.addAttribute("cmmnClCodeVO", result);
			    mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			} else {
				cmmnClCodeVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
				cmmnClCodeManageService.updateCmmnClCode(cmmnClCodeVO);
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