package or.sabang.mng.sec.urm.web;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.SessionVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import or.sabang.mng.sec.urm.service.UserAuthor;
import or.sabang.mng.sec.urm.service.UserAuthorService;
import or.sabang.mng.sec.urm.service.UserAuthorVO;
import or.sabang.mng.umt.service.MberManageVO;
import or.sabang.mng.umt.service.UserDefaultVO;
import or.sabang.mng.sec.ram.service.AuthorManageService;
import or.sabang.mng.sec.ram.service.AuthorManageVO;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.intercept.EgovReloadableFilterInvocationSecurityMetadataSource;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 사용자권한에 관한 controller 클래스를 정의한다.
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
public class UserAuthorController {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Resource(name = "userAuthorService")
    private UserAuthorService userAuthorService;
    
    @Resource(name = "authorManageService")
    private AuthorManageService authorManageService;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Resource(name="databaseSecurityMetadataSource")
	EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

    /**
	 * 권한 목록화면 이동
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping("/mng/sec/urm/userAuthorListView.do")
    public String selectUserAuthorListView() throws Exception {
        return "/mng/sec/urm/userAuthorManage";
    }    

	/**
	 * 사용자별 할당된 권한목록 조회
	 * 
	 * @param userAuthorVO UserAuthorVO
	 * @param authorManageVO AuthorManageVO
	 * @return String
	 * @exception Exception
	 */
    @IncludedInfo(name="권한부여관리", listUrl="/mng/sec/urm/userAuthorList.do", order = 100,gid = 20)
    @RequestMapping(value="/mng/sec/urm/userAuthorList.do")
	public String selectUserAuthorList(@ModelAttribute("userAuthorVO") UserAuthorVO userAuthorVO,
			                             ModelMap model) throws Exception {

    	/** paging */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userAuthorVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userAuthorVO.getPageUnit());
		paginationInfo.setPageSize(userAuthorVO.getPageSize());
		
		userAuthorVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userAuthorVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userAuthorVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		userAuthorVO.setUserAuthorList(userAuthorService.selectUserAuthorList(userAuthorVO));
        model.addAttribute("userAuthorList", userAuthorVO.getUserAuthorList());
        
        int totCnt = userAuthorService.selectUserAuthorListTotCnt(userAuthorVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

        //model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
        
        return "mng/sec/urm/userAuthorManage";
	}
	
    @RequestMapping("/mng/sec/urm/userAuthorSelectUpdtView.do")
    public String userAuthorSelectUpdtView(@ModelAttribute("userAuthorVO") UserAuthorVO userSearchVO,
											  @ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
											    Model model) throws Exception {
    	// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		UserAuthor userAuthor = userAuthorService.selectUserAuthor(userSearchVO.getUniqId());
		
		authorManageVO.setAuthorManageList(authorManageService.selectAuthorAllList(authorManageVO));
        model.addAttribute("authorManageList", authorManageVO.getAuthorManageList());
        
		model.addAttribute("userAuthor", userAuthor);
//		model.addAttribute("userSearchVO", userSearchVO);

		return "mng/sec/urm/userAuthorSelectUpdt";
    }
    
	/**
	 * 사용자에 권한정보를 할당하여 데이터베이스에 등록
	 * 
	 * @param userIds String
	 * @param authorCodes String
	 * @param regYns String
	 * @param userAuthor UserAuthor
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value="/mng/sec/urm/userAuthorUpdate.do")
	public ModelAndView insertUserAuthor(@ModelAttribute("userAuthorVO") UserAuthorVO userAuthorVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			userAuthorService.updateUserAuthor(userAuthorVO);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
			databaseSecurityMetadataSource.reload();
		}catch(Exception e) {
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * 사용자별 할당된 시스템 메뉴 접근권한을 삭제
	 * @param userIds String
	 * @param userAuthor UserAuthor
	 * @return String
	 * @exception Exception
	 */ 
	@RequestMapping(value="/mng/sec/urm/userAuthorDelete.do")
	public String deleteUserAuthor (@RequestParam("userIds") String userIds,
			                        @ModelAttribute("userAuthor") UserAuthor userAuthor,
                                     ModelMap model) throws Exception {
		
    	String [] strUserIds = userIds.split(";");
    	for(int i=0; i<strUserIds.length;i++) {
    		userAuthor.setUniqId(strUserIds[i]);
    		userAuthorService.deleteUserAuthor(userAuthor);
    	}
    	
		model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		databaseSecurityMetadataSource.reload();
		return "forward:/mng/sec/urm/userAuthorList.do";
	}
}