package or.sabang.sys.uia.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.clipsoft.org.json.simple.JSONObject;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.config.EgovLoginConfig;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.mng.sec.drm.service.DeptAuthorService;
import or.sabang.mng.sec.drm.service.DeptAuthorVO;
import or.sabang.mng.umt.service.MberManageService;
import or.sabang.mng.umt.service.MberManageVO;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import or.sabang.sys.uia.service.LoginService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovClntInfo;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.security.intercept.EgovReloadableFilterInvocationSecurityMetadataSource;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 컨트롤러 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일               수정자            수정내용
 *  ----------   --------   ---------------------------
 *  2009.03.06   박지욱           최초 생성
 *  2011.08.26   정진오           IncludedInfo annotation 추가
 *  2011.09.07   서준식           스프링 시큐리티 로그인 및 SSO 인증 로직을 필터로 분리
 *  2011.09.25   서준식           사용자 관리 컴포넌트 미포함에 대한 점검 로직 추가
 *  2011.09.27   서준식           인증서 로그인시 스프링 시큐리티 사용에 대한 체크 로직 추가
 *  2011.10.27   서준식           아이디 찾기 기능에서 사용자 리름 공백 제거 기능 추가
 *  2017.07.21   장동한           로그인인증제한 작업
 *  2018.10.26   신용호           로그인 화면에 message 파라미터 전달 수정
 *  2019.10.01   정진호           로그인 인증세션 추가
 *  2020.06.25   신용호           로그인 메시지 처리 수정
 *  2021.01.15   신용호           로그아웃시 권한 초기화 추가 : session 모드 actionLogout()
 *  
 *  </pre>
 */
@Controller
public class LoginController {

	/** EgovLoginService */
	@Resource(name = "loginService")
	private LoginService loginService;

	/** mberManageService */
	@Resource(name = "mberManageService")
	private MberManageService mberManageService;
	
	/** EgovCmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** deptAuthorService */
	@Resource(name = "deptAuthorService")
    private DeptAuthorService deptAuthorService;
	
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Resource(name = "egovLoginConfig")
	EgovLoginConfig egovLoginConfig;
	
	@Resource(name="databaseSecurityMetadataSource")
	EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 로그인 화면으로 들어간다
	 * @param vo - 로그인후 이동할 URL이 담긴 LoginVO
	 * @return 로그인 페이지
	 * @exception Exception
	 */
	@IncludedInfo(name = "로그인", listUrl = "/login.do", order = 10, gid = 10)
	@RequestMapping(value = "/login.do")
	public String loginUsrView(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//		if (EgovComponentChecker.hasComponent("mberManageService")) {
//			model.addAttribute("useMemberManage", "true");
//		}

		//인증된사용자 여부
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(isAuthenticated) {
			return "redirect:/sys/main.do";
		}
		
		
		//권한체크시 에러 페이지 이동
		String auth_error =  request.getParameter("auth_error") == null ? "" : (String)request.getParameter("auth_error");
		if(auth_error != null && auth_error.equals("1")){
			return "cmm/error/accessDenied";
		}

		String message = (String)request.getParameter("loginMessage");
		if (message!=null) model.addAttribute("loginMessage", message);
		
		return "login";
	}

	/**
	 * 일반(세션) 로그인을 처리한다
	 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */
	@RequestMapping(value = "/actionLogin.do")
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {
		// 1. 로그인인증제한 활성화시 
		if( egovLoginConfig.isLock()){
		    Map<?,?> mapLockUserInfo = (EgovMap)loginService.selectLoginIncorrect(loginVO);
		    if(mapLockUserInfo != null){			
				//2.1 로그인인증제한 처리
				String sLoginIncorrectCode = loginService.processLoginIncorrect(loginVO, mapLockUserInfo);
				if(!sLoginIncorrectCode.equals("E")){
					if(sLoginIncorrectCode.equals("L")){
						model.addAttribute("loginMessage", egovMessageSource.getMessageArgs("fail.common.loginIncorrect", new Object[] {egovLoginConfig.getLockCount(),request.getLocale()}));
					}else if(sLoginIncorrectCode.equals("C")){
						model.addAttribute("loginMessage", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
					}
					return "sys/uia/loginUsr";
				}
		    }else{
		    	model.addAttribute("loginMessage", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
		    	return "sys/uia/loginUsr";
		    }
		}
		
		// 2. 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		
		// 3. 일반 로그인 처리
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

			// 3-1. 로그인 정보를 세션에 저장
			request.getSession().setAttribute("loginVO", resultVO);
			// 2019.10.01 로그인 인증세션 추가
			request.getSession().setAttribute("accessUser", resultVO.getId());

			return "redirect:/actionMain.do";

		} else {
			model.addAttribute("loginMessage", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
			return "sys/uia/loginUsr";
		}
	}

	/**
	 * 로그인 후 메인화면으로 들어간다
	 * @param
	 * @return 로그인 페이지
	 * @exception Exception
	 */
	@RequestMapping(value = "/actionMain.do")
	public String actionMain(ModelMap model) throws Exception {

		// 1. Spring Security 사용자권한 처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "sys/uia/loginUsr";
		}
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		LOGGER.debug("User Id : {}", user == null ? "" : EgovStringUtil.isNullToString(user.getId()));

		/*
		// 2. 메뉴조회
		MenuManageVO menuManageVO = new MenuManageVO();
		menuManageVO.setTmp_Id(user.getId());
		menuManageVO.setTmp_UserSe(user.getUserSe());
		menuManageVO.setTmp_Name(user.getName());
		menuManageVO.setTmp_Email(user.getEmail());
		menuManageVO.setTmp_OrgnztId(user.getOrgnztId());
		menuManageVO.setTmp_UniqId(user.getUniqId());
		List list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
		model.addAttribute("list_headmenu", list_headmenu);
		*/

		// 3. 메인 페이지 이동
		String main_page = Globals.MAIN_PAGE;

		LOGGER.debug("Globals.MAIN_PAGE > " + Globals.MAIN_PAGE);
		LOGGER.debug("main_page > {}", main_page);

		if (main_page.startsWith("/")) {
			return "forward:" + main_page;
		} else {
			return main_page;
		}

		/*
		if (main_page != null && !main_page.equals("")) {

			// 3-1. 설정된 메인화면이 있는 경우
			return main_page;

		} else {

			// 3-2. 설정된 메인화면이 없는 경우
			if (user.getUserSe().equals("USR")) {
				return "egovframework/com/EgovMainView";
			} else {
				return "egovframework/com/EgovMainViewG";
			}
		}
		*/
	}

	/**
	 * 로그아웃한다.
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/logout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {

		String userIp = EgovClntInfo.getClntIP(request);

		// 1. Security 연동
		return "redirect:/j_spring_security_logout";

//		request.getSession().setAttribute("loginVO", null);
//		// 세션모드인경우 Authority 초기화
//		// List<String> authList = (List<String>)EgovUserDetailsHelper.getAuthorities();
//		request.getSession().setAttribute("accessUser", null);
//
//		//return "redirect:/egovDevIndex.jsp";
//		return "redirect:/EgovContent.do";
	}
	
	/**
	 * 회원가입 화면에서 가입정보를 입력하여 db에 저장한다.
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/signup.do")
	public ModelAndView signup(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
    	
    	String userPswd = request.getParameter("mberPswd");
    	
    	int count = 0;
    	
    	mberManageVO.setPassword(userPswd);
		mberManageVO.setGroupId(null);
		mberManageVO.setMberSttus("A");
		
		count = mberManageService.insertMber(mberManageVO);
    	
		modelAndView.addObject("status",count < 1 ? 400 : 200);
    	modelAndView.addObject("message",count < 1 ? "failed" : "success");

		return modelAndView;
	}
	
	/**
	 * 회원가입화면에서 아이디 중복여부를 체크한다.
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/idCheck.do")
	public ModelAndView idCheckController(String mberId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
		
		int idCheck = loginService.idCheck(mberId);
		
		modelAndView.addObject("usedCnt", idCheck == 1 ? "true" : "false");
		
		return modelAndView;
	}
	
	/**
	 * 마이페이지 화면을 호출한다.
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/uia/myinfo.do")
	public String myinfo( HttpServletRequest req, HttpServletResponse res, Model model) throws Exception {
		LOGGER.info("myinfo controller");
		
		//인증된사용자 여부
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			return "redirect:/sys/main.do";
		}
				
		MberManageVO vo = new MberManageVO();
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String uniqId = user.getUniqId();
		
		try {
			vo = mberManageService.selectMber(uniqId);
		} catch(Exception e) {
			LOGGER.debug(e.getStackTrace().toString());
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orgnztId", vo.getInsttId());
		List<?> deptList = deptAuthorService.selectDeptNmList(map);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("mberManageVO", vo);

		return "sys/uia/myinfo";
	}
	
	/**
	 * 마이페이지에서 회원정보를 수정한다.
	 * @param mberManageVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/uia/updateUserInfo.do", method = RequestMethod.POST)
	public ModelAndView updateUserInfo(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, Model model) throws Exception {		
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
    	
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			modelAndView.addObject("status",400);
	    	modelAndView.addObject("message","failed");
		}else{
			mberManageVO.setMberSttus("P");
			int count = mberManageService.updateMber(mberManageVO);
			
			modelAndView.addObject("status",count < 1 ? 400 : 200);
	    	modelAndView.addObject("message",count < 1 ? "failed" : "success");
		}
		
		//Exception 없이 진행시 수정성공메시지
//		model.addAttribute("resultMsg", "success.common.update");
//		return "forward:/sys/uia/myinfo.do";
		return modelAndView;
	}
	
	/**
	 * 마이페이지에서 회원 비밀번호를 수정한다.
	 * @param commandMap
	 * @param mberManageVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/uia/updateUserPswd.do")
	public ModelAndView updateUserPswd(@RequestParam Map<String, Object> commandMap, @ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
    	
    	// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if (!isAuthenticated) {
			modelAndView.addObject("status",400);
	    	modelAndView.addObject("message","failed");
		}else {
			String oldPassword = (String) commandMap.get("oldPassword");
			String newPassword = (String) commandMap.get("newPassword");
			String newPassword2 = (String) commandMap.get("newPasswordConfirm");
			String uniqId = (String) commandMap.get("uniqId");
			
//			String uniqId = req.getParameter("uniqId");
//			String oldPassword = req.getParameter("oldPassword");
//			String password = req.getParameter("newPassword");
//			String passwordConfirm = req.getParameter("newPasswordConfirm");
			
			boolean isCorrectPassword = false;
			MberManageVO resultVO = new MberManageVO();
			mberManageVO.setPassword(newPassword);
			mberManageVO.setOldPassword(oldPassword);
			mberManageVO.setUniqId(uniqId);
			
			String resultMsg = "";
			resultVO = mberManageService.selectPassword(mberManageVO);
			
			//패스워드 암호화
			String encryptPass = EgovFileScrty.encryptPassword(oldPassword, resultVO.getMberId());
//			String encryptPass = oldPassword;
			if (encryptPass.equals(resultVO.getPassword())) {
				if (newPassword.equals(newPassword2)) {
					isCorrectPassword = true;
				} else {
					isCorrectPassword = false;
					resultMsg = "fail.user.passwordUpdate2";
				}
			} else {
				isCorrectPassword = false;
				resultMsg = "fail.user.passwordUpdate1";
			}
			
			int count = 0;
			
			if (isCorrectPassword) {
				mberManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword, resultVO.getMberId()));
//				mberManageVO.setPassword(newPassword);
				count = mberManageService.updatePassword(mberManageVO);
			}
			
			modelAndView.addObject("status",count < 1 ? 400 : 200);
	    	modelAndView.addObject("message",count < 1 ? "failed" : "success");
		}
		return modelAndView;
	}
	
	/**
	 * 부서 목록 조회
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/system/appraisal/selectDeptNmList.do")
	public void selectDeptNmList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LOGGER.debug("Select Dept Nm List");
		
		JSONObject result = new JSONObject();

		res.setCharacterEncoding("UTF-8");
	    res.setContentType("application/json");
		
	    PrintWriter out = res.getWriter();
		
		String insttId = req.getParameter("insttId");
		String callback = req.getParameter("callback");
		
		try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("orgnztId", insttId);
			
			// 부서명 목록
			List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
			
			result.put("items", deptNmList);
			result.put("status", "success");
			
		} catch (Exception e) {
			result.put("items", "[]");
			result.put("status", "failed");
			
		} finally {
			out.print(callback + "(" + result.toString() + ")");
		}
		
	}
}