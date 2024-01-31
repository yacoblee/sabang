package or.sabang.mng.sec.rhm.web;

import java.util.List;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import or.sabang.mng.sec.ram.service.AuthorManageService;
import or.sabang.mng.sec.ram.service.AuthorManageVO;
import or.sabang.mng.sec.ram.web.AuthorManageController;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManage;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManageService;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManageVO;

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
public class RoleHierarchyController {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    @Resource(name = "authorManageService")
    private AuthorManageService authorManageService;
    
    @Resource(name = "roleHierarchyManageService")
    private RoleHierarchyManageService roleHierarchyManageService;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    @Resource(name="databaseSecurityMetadataSource")
	EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;
    
    @Autowired
	private DefaultBeanValidator beanValidator;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleHierarchyController.class);

	/**
	 * 등록된 롤 정보 목록 조회
	 * @param roleManageVO RoleManageVO
	 * @return String
	 * @exception Exception
	 */
    @IncludedInfo(name="롤상하관계관리", listUrl="/mng/sec/rhm/roleHierarchyList.do", order = 90,gid = 20)
    @RequestMapping(value="/mng/sec/rhm/roleHierarchyList.do")
	public String selectRoleHierarchyList(@ModelAttribute("roleHierarchyManageVO") RoleHierarchyManageVO roleHierarchyManageVO,
			                      ModelMap model) throws Exception {

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(roleHierarchyManageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(roleHierarchyManageVO.getPageUnit());
		paginationInfo.setPageSize(roleHierarchyManageVO.getPageSize());

		roleHierarchyManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		roleHierarchyManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		roleHierarchyManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		roleHierarchyManageVO.setRoleHierarchyManageList(roleHierarchyManageService.selectRoleHierarchyList(roleHierarchyManageVO));
        model.addAttribute("roleHierarchyList", roleHierarchyManageVO.getRoleHierarchyManageList());

        int totCnt = roleHierarchyManageService.selectRoleHierarchyListTotCnt(roleHierarchyManageVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
//        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));

        return "mng/sec/rhm/roleHierarchyManage";
	}

    /**
	 * 롤 등록화면 이동
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/mng/sec/rhm/roleHierarchyInsertView.do")
    public String insertRoleView(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
    								@ModelAttribute("roleHierarchyManage") RoleHierarchyManage roleHierarchyManage,
    									ModelMap model) throws Exception {

    	authorManageVO.setAuthorManageList(authorManageService.selectAuthorAllList(authorManageVO));
        model.addAttribute("authorManageList", authorManageVO.getAuthorManageList());

        return "mng/sec/rhm/roleHierarchyInsert";
    }

	/**
	 * 시스템 메뉴에 따른 접근권한, 데이터 입력, 수정, 삭제의 권한 롤을 등록
	 * @param roleManage RoleManage
	 * @param roleManageVO RoleManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rhm/roleHierarchyInsert.do")
	public ModelAndView insertRole(@ModelAttribute("roleHierarchyManage") RoleHierarchyManage roleHierarchyManage,
                              ModelMap model) throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		roleHierarchyManageService.insertRoleHierarchy(roleHierarchyManage);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
			databaseSecurityMetadataSource.reload();
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
    @RequestMapping(value="/mng/sec/rhm/roleHierarchyDelete.do")
	public String deleteRole(@ModelAttribute("roleManage") RoleHierarchyManage roleHierarchyManage,
            ModelMap model) throws Exception {
    	roleHierarchyManageService.deleteRoleHierarchy(roleHierarchyManage);
    	databaseSecurityMetadataSource.reload();
    	model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
    	return "forward:/mng/sec/rhm/roleHierarchyList.do";

	}
}