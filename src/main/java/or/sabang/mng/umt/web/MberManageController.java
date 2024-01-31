package or.sabang.mng.umt.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import or.sabang.cmm.service.CmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import or.sabang.mng.log.wlg.service.WebLog;
import or.sabang.mng.sec.drm.service.DeptAuthorService;
import or.sabang.mng.sec.urm.service.UserAuthor;
import or.sabang.mng.sec.urm.service.UserAuthorService;
import or.sabang.mng.umt.service.MberManageService;
import or.sabang.mng.umt.service.MberManageVO;
import or.sabang.mng.umt.service.UserDefaultVO;
import or.sabang.sys.uia.web.LoginController;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 일반회원관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당   웹 화면으로 전달하는  Controller를 정의한다
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2011.08.26	 정진오			IncludedInfo annotation 추가
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *   2015.06.16	 조정국			수정시 유효성체크 후 에러발생 시 목록으로 이동하여 에러메시지 표시
 *   2015.06.19	 조정국			미인증 사용자에 대한 보안처리 기준 수정 (!isAuthenticated)
 *   2017.07.21  장동한 			로그인인증제한 작업
 *
 * </pre>
 */

@Controller
public class MberManageController {

	/** mberManageService */
	@Resource(name = "mberManageService")
	private MberManageService mberManageService;
	
	@Resource(name = "userAuthorService")
	private UserAuthorService userAuthorService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "deptAuthorService")
    private DeptAuthorService deptAuthorService;

	/** DefaultBeanValidator beanValidator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(MberManageController.class);
	
	/**
	 * 회원목록을 조회한다. (pageing)
	 * @param userSearchVO 검색조건정보
	 * @param model 화면모델
	 * @return mng/umt/mberManage
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/umt/mberManage.do")
	public String selectMberList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		paginationInfo.setPageSize(userSearchVO.getPageSize());

		userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		userSearchVO.setSbscrbSttus("P");
		
		List<?> mberList = mberManageService.selectMberList(userSearchVO);
		model.addAttribute("resultList", mberList);

		int totCnt = mberManageService.selectMberListTotCnt(userSearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		//일반회원 상태코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI032");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("mberSttus_result", mberSttus_result);//기업회원상태코드목록

		return "mng/umt/mberManage";
	}
	
	/**
	 * 가입승인 대기중인 회원목록을 조회한다. (pageing)
	 * @param userSearchVO 검색조건정보
	 * @param model 화면모델
	 * @return mng/umt/mberJoinManage
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/ujm/mberJoinManage.do")
	public String selectMberJoinList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		paginationInfo.setPageSize(userSearchVO.getPageSize());

		userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		userSearchVO.setSbscrbSttus("A");
		
		List<?> mberList = mberManageService.selectMberList(userSearchVO);
		model.addAttribute("resultList", mberList);

		int totCnt = mberManageService.selectMberListTotCnt(userSearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		//일반회원 상태코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI032");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("mberSttus_result", mberSttus_result);//기업회원상태코드목록

		return "mng/umt/mberJoinManage";
	}
	
	/**
	 * 회원정보 수정을 위해 회원정보를 상세조회한다.
	 * @param mberId 상세조회대상 회원아이디
	 * @param userSearchVO 검색조건
	 * @param model 화면모델
	 * @return mng/umt/mberSelectUpdt
	 * @throws Exception
	 */
	@RequestMapping("/mng/umt/mberSelectUpdtView.do")
	public String updateMberView(@RequestParam("selectedId") String mberId, @ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		//사용자상태코드를 코드정보로부터 조회
//		vo.setCodeId("FEI032");
		List<?> mberSttus_result = cmmUseService.selectCmmCodeDetail(vo);

		model.addAttribute("mberSttus_result", mberSttus_result); //사용자상태코드목록

		MberManageVO mberManageVO = mberManageService.selectMber(mberId);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orgnztId", mberManageVO.getInsttId());
		List<?> deptList = deptAuthorService.selectDeptNmList(map);
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("mberManageVO", mberManageVO);
		model.addAttribute("userSearchVO", userSearchVO);

		return "mng/umt/mberSelectUpdt";
	}
	
	/**
	 * 일반회원정보 수정후 목록조회 화면으로 이동한다.
	 * @param mberManageVO 일반회원수정정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovMberManage.do
	 * @throws Exception
	 */
	@RequestMapping("/mng/umt/mberSelectUpdt.do")
	public ModelAndView updateMber(@ModelAttribute("mberManageVO") MberManageVO mberManageVO, Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
    	
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			modelAndView.addObject("status",400);
	    	modelAndView.addObject("message","failed");
		}

		int count = mberManageService.updateMber(mberManageVO);
		
		modelAndView.addObject("status",count < 1 ? 400 : 200);
    	modelAndView.addObject("message",count < 1 ? "failed" : "success");
    	
		//Exception 없이 진행시 수정성공메시지
//		model.addAttribute("resultMsg", "success.common.update");
//		return "forward:/mng/umt/mberManage.do";
		
		return modelAndView;
	}
	
	/**
	 * 회원정보 상세조회 후 비밀번호를 수정한다.
	 * @param req
	 * @param res
	 * @param mberManageVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/umt/mberPasswordUpdt.do")
	public ModelAndView updateUserPswd(@RequestParam Map<String, Object> commandMap,
			@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {
		
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
			String uniqId = (String) commandMap.get("uId");
			
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
				resultMsg = "success.common.update";
			}
			
			modelAndView.addObject("status",count < 1 ? 400 : 200);
	    	modelAndView.addObject("message",resultMsg);
		}
    	
    	return modelAndView;
	}
	
	/**
	 * 회원 비밀번호를 초기화한다.
	 * @param userId 회원아이디
	 * @return modelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/umt/mberPasswordReset.do")
	public ModelAndView resetUserPassword(@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			mv.addObject("status", 400);
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			return mv;
		}
		
    	int count = 0;
    	String resetPassword = "sabang1234!";
    	
    	MberManageVO resultVO = mberManageService.selectMber(mberManageVO.getUniqId());
    	
		mberManageVO.setPassword(EgovFileScrty.encryptPassword(resetPassword, resultVO.getMberId()));
		count = mberManageService.updatePassword(mberManageVO);
		
//    	int count = mberManageService.resetMberPassword(mberManageVO);
    	
		mv.addObject("status",count < 1 ? 400 : 200);
    	mv.addObject("message",count < 1 ? "failed" : "success");
    	
    	return mv;
	}
	
	/**
	 * 회원정보를 삭제한다.
	 * @param user_id
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/umt/deleteMber.do")
	public ModelAndView userDelete( @ModelAttribute("mberManageVO") MberManageVO mberManageVO ) throws Exception {
		ModelAndView mv = new ModelAndView();
    	mv.setViewName("jsonView");
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			mv.addObject("status", 400);
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			return mv;
		}
		
    	String uniqId = mberManageVO.getUniqId();
		LOGGER.info(uniqId + " delete process");
		
		int count = mberManageService.deleteMber(uniqId);
		UserAuthor userAuthor = new UserAuthor();
		userAuthor.setUniqId(uniqId);
		userAuthorService.deleteUserAuthor(userAuthor);
		
		mv.addObject("status",count < 1 ? 400 : 200);
		mv.addObject("message",count < 1 ? "failed" : "success");
    	
		return mv;
	}
	
	@RequestMapping(value = "/mng/umt/updateAcceptYMber.do")
	public ModelAndView acceptYmber( @ModelAttribute("mberManageVO") MberManageVO mberManageVO, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			mv.addObject("status", 400);
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			return mv;
		}
    	
		UserAuthor userAuthor = new UserAuthor();
		userAuthor.setUniqId(mberManageVO.getUniqId());
		userAuthor.setAuthorCode("ROLE_USER");
		
		mberManageService.updateAccept(mberManageVO.getUniqId());
		userAuthorService.insertUserAuthor(userAuthor);
//    	String uniqId = mberManageVO.getUniqId();
//    	AuthorVO author = new AuthorVO();
//    	
//    	author.setScrty_dtrmn_trget_id(uniqId);
//    	author.setAuthor_code("ROLE_USER");
//    	
//    	mberManageService.updateAcceptMember(uniqId);
//    	authorService.insertAuthorUser(author);
//    	authorService.insertMnuAccesAuthorUser(author);
		mv.addObject("status", 200);
		mv.addObject("message", egovMessageSource.getMessage("success.common.acceptY"));
		//return "forward:/mng/ujm/mberJoinManage.do";
		return mv;
	}
	
	@RequestMapping(value = "/mng/umt/updateAcceptNMber.do")
	public ModelAndView acceptNMember( @ModelAttribute("mberManageVO") MberManageVO mberManageVO, Model model) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			mv.addObject("status", 400);
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			return mv;
		}
    	
    	String uniqId = mberManageVO.getUniqId();
    	
    	mberManageService.deleteMber(uniqId);
    	
    	mv.addObject("status", 200);
		mv.addObject("message", egovMessageSource.getMessage("success.common.acceptN"));
		
		return mv;
	}
	
	/**
	 * 로그인인증제한 해제 
	 * @param mberManageVO 일반회원등록정보
	 * @param model 화면모델
	 * @return uss/umt/EgovMberSelectUpdtView.do
	 * @throws Exception
	 */
	@RequestMapping("/mng/umt/mberLockIncorrect.do")
	public ModelAndView updateLockIncorrect(MberManageVO mberManageVO, Model model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
	    // 미인증 사용자에 대한 보안처리
	    Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	    if (!isAuthenticated) {
	    	modelAndView.addObject("status",400);
	    	modelAndView.addObject("message","failed");
	    }else {
	    	//String uniqId = MberManageVO.getUniqId();
	    	int count = mberManageService.updateLockIncorrect(mberManageVO);
		    
		    modelAndView.addObject("status",count < 1 ? 400 : 200);
	    	modelAndView.addObject("message",count < 1 ? "failed" : "success");
	    }
    	
		return modelAndView;
	}
	
	/**
	 * 회원 비밀번호를 암호화한다.
	 * @param mberManageVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mng/umt/mberPasswordEncrypt.do")
	public ModelAndView mberPasswordEncrypt(@ModelAttribute("mberManageVO") MberManageVO mberManageVO) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("jsonView");
    	
    	String uniqId = mberManageVO.getUniqId();
    	int count = 0;
    	
    	try {
    		MberManageVO resultVO = mberManageService.selectMber(uniqId);
        	
        	//패스워드 암호화
        	mberManageVO.setPassword(EgovFileScrty.encryptPassword(resultVO.getPassword(), resultVO.getMberId()));
        			
        	count = mberManageService.updatePassword(mberManageVO);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
    	
    	modelAndView.addObject("status",count < 1 ? 400 : 200);
    	modelAndView.addObject("message",count < 1 ? "failed" : "success");
    	
    	return modelAndView;
	}
	
	@RequestMapping(value="/mng/umt/mberManageExcel.do")
	public ModelAndView selectMberListExcel(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			modelAndView.setViewName("jsonView");
			modelAndView.addObject("status",400);
	    	modelAndView.addObject("message","failed");
	    	
	    	return modelAndView;
		}
				
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
//    	List<EgovMap> list = webLogService.selectWebLogExcel(webLog);
    	HashMap<?, ?> _map = (HashMap<?, ?>)mberManageService.selectMberListExcel(userSearchVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String fileNm = "회원목록_".concat(formater.format(new Date()).toString());
    	String[] columnArray = {"아이디","이름","전화번호","기관명","부서명","직책","가입일","사용권한"};
    	String[] columnVarArray = {"mberId","mberNm","mbtlnum","orgnztNm","deptNm","ofcpsNm","sbscrbDe","authorNm"};
    	
    	dataMap.put("columnArr", columnArray);
    	dataMap.put("columnVarArr", columnVarArray);
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}

}