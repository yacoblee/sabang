package or.sabang.mng.sec.rmt.web;

import java.util.List;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.mng.sec.ram.service.AuthorManageService;
import or.sabang.mng.sec.ram.service.AuthorManageVO;
import or.sabang.mng.sec.ram.web.AuthorManageController;
import or.sabang.mng.sec.rmt.service.RoleManage;
import or.sabang.mng.sec.rmt.service.RoleManageService;
import or.sabang.mng.sec.rmt.service.RoleManageVO;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
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
 * 롤관리에 관한 controller 클래스를 정의한다.
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
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 *
 * </pre>
 */


@Controller
@SessionAttributes(types=SessionVO.class)
public class RoleManageController {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    @Resource(name = "roleManageService")
    private RoleManageService roleManageService;

    @Resource(name = "cmmUseService")
    CmmUseService cmmUseService;

    @Resource(name = "authorManageService")
    private AuthorManageService authorManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** Message ID Generation */
    @Resource(name="egovRoleIdGnrService")
    private EgovIdGnrService egovRoleIdGnrService;

    @Autowired
	private DefaultBeanValidator beanValidator;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleManageController.class);
    
    @Resource(name="databaseSecurityMetadataSource")
	EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

    /**
	 * 롤 목록화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/mng/sec/rmt/RoleListView.do")
    public String selectRoleListView()
            throws Exception {
        return "mng/sec/rmt/RoleManage";
    }

	/**
	 * 등록된 롤 정보 목록 조회
	 * @param roleManageVO RoleManageVO
	 * @return String
	 * @exception Exception
	 */
    @IncludedInfo(name="롤관리", listUrl="/mng/sec/rmt/roleList.do", order = 90,gid = 20)
    @RequestMapping(value="/mng/sec/rmt/roleList.do")
	public String selectRoleList(@ModelAttribute("roleManageVO") RoleManageVO roleManageVO,
			                      ModelMap model) throws Exception {

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(roleManageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(roleManageVO.getPageUnit());
		paginationInfo.setPageSize(roleManageVO.getPageSize());

		roleManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		roleManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		roleManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		roleManageVO.setRoleManageList(roleManageService.selectRoleList(roleManageVO));
        model.addAttribute("roleList", roleManageVO.getRoleManageList());

        int totCnt = roleManageService.selectRoleListTotCnt(roleManageVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        //model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));

        return "mng/sec/rmt/roleManage";
	}

	/**
	 * 등록된 롤 정보 조회
	 * @param roleCode String
	 * @param roleManageVO RoleManageVO
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rmt/role.do")
	public String selectRole(@RequestParam("roleCode") String roleCode,
	                         @ModelAttribute("roleManageVO") RoleManageVO roleManageVO,
	                         @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
		                      ModelMap model) throws Exception {

    	roleManageVO.setRoleCode(roleCode);

    	authorManageVO.setAuthorManageList(authorManageService.selectAuthorAllList(authorManageVO));

    	model.addAttribute("roleManage", roleManageService.selectRole(roleManageVO));
        model.addAttribute("authorManageList", authorManageVO.getAuthorManageList());
        model.addAttribute("cmmCodeDetailList", getCmmCodeDetailList(new ComDefaultCodeVO(),"FEI031"));

        return "mng/sec/rmt/roleSelect";
	}

    /**
	 * 롤 등록화면 이동
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/mng/sec/rmt/roleInsertView.do")
    public String insertRoleView(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
    								@ModelAttribute("roleManage") RoleManage roleManage,
    									ModelMap model) throws Exception {

    	authorManageVO.setAuthorManageList(authorManageService.selectAuthorAllList(authorManageVO));
        model.addAttribute("authorManageList", authorManageVO.getAuthorManageList());
        model.addAttribute("cmmCodeDetailList", getCmmCodeDetailList(new ComDefaultCodeVO(),"FEI031"));

        return "mng/sec/rmt/roleInsert";
    }

    /**
	 * 공통코드 호출
	 * @param comDefaultCodeVO ComDefaultCodeVO
	 * @param codeId String
	 * @return List
	 * @exception Exception
	 */
    public List<?> getCmmCodeDetailList(ComDefaultCodeVO comDefaultCodeVO, String codeId)  throws Exception {
    	comDefaultCodeVO.setCodeId(codeId);
    	return cmmUseService.selectCmmCodeDetail(comDefaultCodeVO);
    }

	/**
	 * 시스템 메뉴에 따른 접근권한, 데이터 입력, 수정, 삭제의 권한 롤을 등록
	 * @param roleManage RoleManage
	 * @param roleManageVO RoleManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rmt/roleInsert.do")
	public ModelAndView insertRole(@ModelAttribute("roleManage") RoleManage roleManage,
			                 @ModelAttribute("roleManageVO") RoleManageVO roleManageVO,
			                  BindingResult bindingResult,
                              ModelMap model) throws Exception {

    	beanValidator.validate(roleManage, bindingResult); //validation 수행
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		if (bindingResult.hasErrors()) {
    			mv.addObject("status","fail");
    			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		} else {
    			String roleTyp = roleManage.getRoleTyp();
    			if("method".equals(roleTyp))//KISA 보안약점 조치 (2018-10-29, 윤창원)
    	    	roleTyp = "mtd";
    	    	else if("pointcut".equals(roleTyp))//KISA 보안약점 조치 (2018-10-29, 윤창원)
    	    		roleTyp = "pct";
    	    	else roleTyp = "web";
    
    	    	roleManage.setRoleCode(roleTyp.concat("-").concat(egovRoleIdGnrService.getNextStringId()));
    	    	roleManageVO.setRoleCode(roleManage.getRoleCode());
    
    	    	mv.addObject("cmmCodeDetailList", getCmmCodeDetailList(new ComDefaultCodeVO(),"FEI031"));
    	    	mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
    	    	mv.addObject("roleManage", roleManageService.insertRole(roleManage, roleManageVO));
    			mv.addObject("status","success");
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
	 * 등록된 롤 정보 수정화면
	 * @param roleCode String
	 * @param roleManageVO RoleManageVO
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rmt/roleUpdateView.do")
	public String updateRoleView(@RequestParam("roleCode") String roleCode,
	                         @ModelAttribute("roleManageVO") RoleManageVO roleManageVO,
	                         @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
		                      ModelMap model) throws Exception {

    	roleManageVO.setRoleCode(roleCode);

    	authorManageVO.setAuthorManageList(authorManageService.selectAuthorAllList(authorManageVO));

    	model.addAttribute("roleManage", roleManageService.selectRole(roleManageVO));
        model.addAttribute("authorManageList", authorManageVO.getAuthorManageList());
        model.addAttribute("cmmCodeDetailList", getCmmCodeDetailList(new ComDefaultCodeVO(),"FEI031"));

        return "mng/sec/rmt/roleUpdate";
	}
    
	/**
	 * 시스템 메뉴에 따른 접근권한, 데이터 입력, 수정, 삭제의 권한 롤을 수정
	 * @param roleManage RoleManage
	 * @param bindingResult BindingResult
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rmt/roleUpdate.do")
	public ModelAndView updateRole(@ModelAttribute("roleManage") RoleManage roleManage,
			BindingResult bindingResult,
            ModelMap model) throws Exception {

    	beanValidator.validate(roleManage, bindingResult); //validation 수행
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		if (bindingResult.hasErrors()) {
    			mv.addObject("status","fail");
    			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		} else {
    			roleManageService.updateRole(roleManage);
    			mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
    			mv.addObject("status","success");
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
	 * 불필요한 롤정보를 화면에 조회하여 데이터베이스에서 삭제
	 * @param roleManage RoleManage
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rmt/roleDelete.do")
	public ModelAndView deleteRole(@ModelAttribute("roleManage") RoleManage roleManage,
            ModelMap model) throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		roleManageService.deleteRole(roleManage);
    		mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
			mv.addObject("status","success");
			databaseSecurityMetadataSource.reload();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
	}

	/**
	 * 불필요한 그룹정보 목록을 화면에 조회하여 데이터베이스에서 삭제
	 * @param roleCodes String
	 * @param roleManage RoleManage
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/mng/sec/rmt/roleListDelete.do")
	public String deleteRoleList(@RequestParam("roleCodes") String roleCodes,
			                     @ModelAttribute("roleManage") RoleManage roleManage,
	                              Model model) throws Exception {
    	String [] strRoleCodes = roleCodes.split(";");
    	for(int i=0; i<strRoleCodes.length;i++) {
    		roleManage.setRoleCode(strRoleCodes[i]);
    		roleManageService.deleteRole(roleManage);
    	}

		model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		databaseSecurityMetadataSource.reload();
		return "forward:/mng/sec/rmt/roleList.do";
	}

}