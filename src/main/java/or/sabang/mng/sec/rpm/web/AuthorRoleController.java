package or.sabang.mng.sec.rpm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.intercept.EgovReloadableFilterInvocationSecurityMetadataSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.mng.sec.ram.service.AuthorManageService;
import or.sabang.mng.sec.ram.service.AuthorManageVO;
import or.sabang.mng.sec.rpm.service.AuthorRoleManage;
import or.sabang.mng.sec.rpm.service.AuthorRoleManageService;
import or.sabang.mng.sec.rpm.service.AuthorRoleManageVO;
import or.sabang.mng.sec.rmt.service.RoleManageService;
import or.sabang.mng.sec.rmt.service.RoleManageVO;

/**
 * 권한별 롤관리에 관한 controller 클래스를 정의한다.
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
 *   2011.09.07  서준식          롤 등록시 이미 등록된 경우 데이터 중복 에러 발생 문제 수정
 * </pre>
 */
@Controller
public class AuthorRoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRoleController.class);
	
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "authorManageService")
    private AuthorManageService authorManageService;
    
    @Resource(name = "roleManageService")
    private RoleManageService roleManageService;
    
    @Resource(name = "authorRoleManageService")
    private AuthorRoleManageService authorRoleManageService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Resource(name="databaseSecurityMetadataSource")
	EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

    /**
	 * 권한 롤 관계 화면 이동
	 * @return "egovframework/com/sec/rpm/EgovDeptAuthorList"
	 * @exception Exception
	 */
    @RequestMapping("/mng/sec/rpm/authorRoleListView.do")
    public String selectAuthorRoleListView() throws Exception {

        return "mng/sec/rpm/authorRoleManage";
    } 

	/**
	 * 권한별 할당된 롤 목록 조회
	 * 
	 * @param authorRoleManageVO AuthorRoleManageVO
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/mng/sec/rpm/authorRoleList.do")
	public String selectAuthorRoleList(@ModelAttribute("authorRoleManageVO") AuthorRoleManageVO authorRoleManageVO,
			                            ModelMap model) throws Exception {

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(authorRoleManageVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(authorRoleManageVO.getPageUnit());
		paginationInfo.setPageSize(authorRoleManageVO.getPageSize());
		
		authorRoleManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		authorRoleManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
		authorRoleManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		authorRoleManageVO.setAuthorRoleList(authorRoleManageService.selectAuthorRoleList(authorRoleManageVO));
        model.addAttribute("authorRoleList", authorRoleManageVO.getAuthorRoleList());
        model.addAttribute("searchVO", authorRoleManageVO);
        
        int totCnt = authorRoleManageService.selectAuthorRoleListTotCnt(authorRoleManageVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

//        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
        
        return "mng/sec/rpm/authorRoleManage";
	}
    
    /**
	 * 권한별 할당한 롤 등록화면 이동
	 * @return String
	 * @exception Exception
	 */     
    @RequestMapping("/mng/sec/rpm/authorRoleInsertView.do")
    public String insertAuthorView(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO, @ModelAttribute("roleManageVO") RoleManageVO roleManageVO, ModelMap model) throws Exception {
    	authorManageVO.setAuthorManageList(authorManageService.selectAuthorAllList(authorManageVO));
    	roleManageVO.setRoleManageList(roleManageService.selectRoleAllList(roleManageVO));
    	
    	model.addAttribute("authorList", authorManageVO.getAuthorManageList());
    	model.addAttribute("roleList", roleManageVO.getRoleManageList());
    	
        return "mng/sec/rpm/authorRoleInsert";
    }
    
	/**
	 * 권한정보에 롤을 할당하여 데이터베이스에 등록
	 * @param authorCode String
	 * @param roleCodes String
	 * @param regYns String
	 * @param authorRoleManage AuthorRoleManage
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/mng/sec/rpm/authorRoleInsert.do")
	public ModelAndView insertAuthorRole(@RequestParam Map<?, ?> commandMap,
			                       @ModelAttribute("authorRoleManage") AuthorRoleManage authorRoleManage,
			                         ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			authorRoleManageService.insertAuthorRole(authorRoleManage);
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
	 * 권한별 할당한 롤 삭제
	 * @return String
	 * @exception Exception
	 */     
	@RequestMapping(value="/mng/sec/rpm/authorRoleDelete.do")
	public ModelAndView deleteAuthorRole(@ModelAttribute("authorRoleManage") AuthorRoleManage authorRoleManage, ModelMap model) throws Exception {
		String roleCode = authorRoleManage.getRoleCode();
		String authorCode = authorRoleManage.getAuthorCode();
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			authorRoleManageService.deleteAuthorRole(authorRoleManage);
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
	
}