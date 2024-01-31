package or.sabang.mng.sec.ram.web;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import or.sabang.mng.sec.ram.service.AuthorManage;
import or.sabang.mng.sec.ram.service.AuthorManageService;
import or.sabang.mng.sec.ram.service.AuthorManageVO;
import or.sabang.sys.lss.bsc.web.LssBscFieldBookController;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.intercept.EgovReloadableFilterInvocationSecurityMetadataSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 권한관리에 관한 controller 클래스를 정의한다.
 * @author 공통서비스 개발팀 이문준
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.11  이문준          최초 생성
 *   2011.8.26	정진오			IncludedInfo annotation 추가s
 *
 * </pre>
 */
 

@Controller
@SessionAttributes(types=SessionVO.class)
public class AuthorManageController {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "authorManageService")
    private AuthorManageService authorManageService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Autowired
	private DefaultBeanValidator beanValidator;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorManageController.class);
    
    @Resource(name="databaseSecurityMetadataSource")
	EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;
    
    /**
	 * 권한 목록화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/mng/sec/ram/authorListView.do")
    public String selectAuthorListView()
            throws Exception {
        return "mng/sec/ram/authorManage";
    }    
    
    /**
	 * 권한 목록을 조회한다
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/ram/authorList.do")
    public String selectAuthorList(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, 
    		                        ModelMap model)
            throws Exception {
    	
    	/** EgovPropertyService.sample */
    	//authorManageVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	//authorManageVO.setPageSize(propertiesService.getInt("pageSize"));
    	
    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(authorManageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(authorManageVO.getPageUnit());
		paginationInfo.setPageSize(authorManageVO.getPageSize());
		
		authorManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		authorManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		authorManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		authorManageVO.setAuthorManageList(authorManageService.selectAuthorList(authorManageVO));
        model.addAttribute("authorList", authorManageVO.getAuthorManageList());
        
        int totCnt = authorManageService.selectAuthorListTotCnt(authorManageVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
//        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));

        return "mng/sec/ram/authorManage";
    } 
    
    /**
	 * 권한 세부정보를 조회한다.
	 * @param authorCode String
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */   
    @RequestMapping(value="/mng/sec/ram/author.do")
    public String selectAuthor(@RequestParam("authorCode") String authorCode,
    	                       @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, 
    		                    ModelMap model) throws Exception {
    	
    	authorManageVO.setAuthorCode(authorCode);

    	model.addAttribute("authorManage", authorManageService.selectAuthor(authorManageVO));
//    	model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
    	return "mng/sec/ram/authorSelect";
    }     

    /**
	 * 권한 등록화면 이동
	 * @return String
	 * @exception Exception
	 */     
    @RequestMapping("/mng/sec/ram/authorInsertView.do")
    public String insertAuthorView(@ModelAttribute("authorManage") AuthorManage authorManage)
            throws Exception {
        return "mng/sec/ram/authorInsert";
    }
    
    /**
	 * 권한 세부정보를 등록한다.
	 * @param authorManage AuthorManage
	 * @param bindingResult BindingResult
	 * @return String
	 * @exception Exception
	 */ 
    @RequestMapping(value="/mng/sec/ram/authorInsert.do")
    public ModelAndView insertAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, 
    		                    BindingResult bindingResult,
    		                    ModelMap model) throws Exception {
    	
    	beanValidator.validate(authorManage, bindingResult); //validation 수행
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		if (bindingResult.hasErrors()) {
    			mv.addObject("status","fail");
    			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		} else {
    			authorManageService.insertAuthor(authorManage);
    			mv.addObject("status","success");
    			mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
    			databaseSecurityMetadataSource.reload();
    		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
    }
    
    /**
	 * 권한 수정화면 이동
	 * @param authorCode String
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */   
    @RequestMapping(value="/mng/sec/ram/authorUpdateView.do")
    public String updateAuthorView(@RequestParam("authorCode") String authorCode,
    	                       @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, 
    		                    ModelMap model) throws Exception {
    	
    	authorManageVO.setAuthorCode(authorCode);

    	model.addAttribute("authorManage", authorManageService.selectAuthor(authorManageVO));
//    	model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
    	return "mng/sec/ram/authorUpdate";
    } 
    
    /**
	 * 권한 세부정보를 수정한다.
	 * @param authorManage AuthorManage
	 * @param bindingResult BindingResult
	 * @return String
	 * @exception Exception
	 */   
    @RequestMapping(value="/mng/sec/ram/authorUpdate.do")
    public ModelAndView updateAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, 
    		                    BindingResult bindingResult,
    		                    Model model) throws Exception {

    	beanValidator.validate(authorManage, bindingResult); //validation 수행
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		if (bindingResult.hasErrors()) {
    			mv.addObject("status","fail");
    			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		} else {
    			authorManageService.updateAuthor(authorManage);
    			mv.addObject("status","success");
    			mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
    			databaseSecurityMetadataSource.reload();
    		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
    }    

    /**
	 * 권한 세부정보를 삭제한다.
	 * @param authorManage AuthorManage
	 * @return String
	 * @exception Exception
	 */  
    @RequestMapping(value="/mng/sec/ram/authorDelete.do")
    public ModelAndView deleteAuthor(@ModelAttribute("authorManage") AuthorManage authorManage, 
    		                    Model model) throws Exception {
    	
//    	authorManageService.deleteAuthor(authorManage);
//    	model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
//    	databaseSecurityMetadataSource.reload();
//        return "forward:/mng/sec/ram/authorList.do";
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		authorManageService.deleteAuthor(authorManage);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
			databaseSecurityMetadataSource.reload();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
    }   
    
    /**
	 * 권한목록을 삭제한다.
	 * @param authorCodes String
	 * @param authorManage AuthorManage
	 * @return String
	 * @exception Exception
	 */  
    @RequestMapping(value="/mng/sec/ram/authorListDelete.do")
    public String deleteAuthorList(@RequestParam("authorCodes") String authorCodes,
    		                       @ModelAttribute("authorManage") AuthorManage authorManage, 
    		                        Model model) throws Exception {

    	String [] strAuthorCodes = authorCodes.split(";");
    	for(int i=0; i<strAuthorCodes.length;i++) {
    		authorManage.setAuthorCode(strAuthorCodes[i]);
    		authorManageService.deleteAuthor(authorManage);
    	}
    	model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
    	databaseSecurityMetadataSource.reload();
        return "forward:/mng/sec/ram/authorList.do";
    }    
    
    /**
	 * 권한제한 화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/accessDenied.do")
    public String accessDenied()
            throws Exception {
        return "cmm/error/accessDenied";
    } 
}
