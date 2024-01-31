package or.sabang.mng.mnu.mpm.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import or.sabang.mng.mnu.mpm.service.MenuManageService;
import or.sabang.mng.mnu.mpm.service.MenuManageVO;
import or.sabang.mng.prm.service.ProgrmManageService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 메뉴목록 관리및 메뉴생성, 사이트맵 생성을 처리하는 비즈니스 구현 클래스
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일               수정자           수정내용
 *  ----------   --------   ---------------------------
 *  2009.03.20   이  용            최초 생성
 *  2011.07.01   서준식            메뉴정보 삭제시 참조되고 있는 하위 메뉴가 있는지 체크하는 로직 추가
 *  2011.07.27   서준식            deleteMenuManageList() 메서드에서 메뉴 멀티 삭제 버그 수정
 *  2011.08.26   정진오            IncludedInfo annotation 추가
 *  2011.10.07   이기하            보안취약점 수정(파일 업로드시 엑셀파일만 가능하도록 추가)
 *  2015.05.28   조정국            메뉴리스트관리 선택시 "정상적으로 조회되었습니다"라는 alert창이 제일 먼저 뜨는것 수정 : 출력메시지 주석처리
 *  2020.11.02   신용호            KISA 보안약점 조치 - 자원해제
 *  2021.02.16   신용호            WebUtils.getNativeRequest(request,MultipartHttpServletRequest.class);
 * </pre>
 */

@Controller
public class MenuManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuManageController.class);

	/* Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** MenuManageService */
	@Resource(name = "meunManageService")
    private MenuManageService menuManageService;

    /** EgovMenuManageService */
	@Resource(name = "progrmManageService")
	private ProgrmManageService progrmManageService;

    /** EgovFileMngService */
//	@Resource(name="EgovFileMngService")
//	private EgovFileMngService fileMngService;

    /** EgovFileMngUtil */
//	@Resource(name="EgovFileMngUtil")
//	private EgovFileMngUtil fileUtil;

//	@Resource(name = "excelZipService")
//    private EgovExcelService excelZipService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;


    @RequestMapping(value="/mng/mnu/mpm/menuManageListDetail.do")
    public String selectMenuManageDetail(
    		@RequestParam("req_menuNo") String req_menuNo ,
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
    	ComDefaultVO vo = new ComDefaultVO();
    	
    	vo.setSearchKeyword(req_menuNo);

    	MenuManageVO resultVO = menuManageService.selectMenuManage(vo);
        model.addAttribute("menuManageVO", resultVO);

        return "mng/mnu/mpm/menuDetail";
    }
    
    /**
     * 메뉴정보목록을 상세화면 호출 및 상세조회한다.
     * @param req_menuNo  String
     * @return 출력페이지정보 "sym/mnu/mpm/menuDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuManageListUpdateView.do")
    public String selectMenuManage(
    		@RequestParam("menuNo") String menuNo ,
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
    	
    	ComDefaultVO vo = new ComDefaultVO();
    	
    	vo.setSearchKeyword(menuNo);

    	MenuManageVO resultVO = menuManageService.selectMenuManage(vo);
        model.addAttribute("menuManageVO", resultVO);

        return "mng/mnu/mpm/menuDetailUpdtView";
    }

    /**
     * 메뉴목록 리스트조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/menuManage"
     * @exception Exception
     */
    @IncludedInfo(name="메뉴관리리스트", order = 1091 ,gid = 60)
    @RequestMapping(value="/mng/mnu/mpm/menuManageSelect.do")
    public String selectMenuManageList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
    	// 내역 조회
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

		List<?> list_menumanage = menuManageService.selectMenuManageList(searchVO);
		model.addAttribute("list_menumanage", list_menumanage);

        int totCnt = menuManageService.selectMenuManageListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "mng/mnu/mpm/menuManage";
    }

    /**
     * 메뉴목록 멀티 삭제한다.
     * @param checkedMenuNoForDel  String
     * @return 출력페이지정보 "forward:/mng/mnu/mpm/menuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping("/mng/mnu/mpm/menuManageListDelete.do")
    public String deleteMenuManageList(
            @RequestParam("checkedMenuNoForDel") String checkedMenuNoForDel ,
            @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
            ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
		String sLocationUrl = null;
    	String message    = "";

		String [] delMenuNo = checkedMenuNoForDel.split(",");
		menuManageVO.setMenuNo(Integer.parseInt(delMenuNo[0]));

		if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0){
    		message = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
    		sLocationUrl = "forward:/mng/mnu/mpm/menuManageSelect.do";
		}else if (delMenuNo == null || (delMenuNo.length ==0)){
    		message = egovMessageSource.getMessage("fail.common.delete");
    		sLocationUrl = "forward:/mng/mnu/mpm/menuManageSelect.do";
		}else{
			menuManageService.deleteMenuManageList(checkedMenuNoForDel);
			message = egovMessageSource.getMessage("success.common.delete");
			sLocationUrl ="forward:/mng/mnu/mpm/menuManageSelect.do";
		}
		model.addAttribute("message", message);
        return sLocationUrl;
    }

    /**
     * 메뉴정보를 등록화면을 이동한다.
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuRegistInsertView.do")
    public String menuRegistInsertView(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model) throws Exception {
    	return "mng/mnu/mpm/menuRegist";
    }
    
    /**
     * 메뉴정보를 등록 한다.
     * @param menuManageVO    MenuManageVO
     * @param commandMap      Map
     * @return 출력페이지정보 등록화면 호출시 "mng/mnu/mpm/menuRegist",
     *         출력페이지정보 등록처리시 "forward:/mng/mnu/mpm/menuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuRegistInsert.do")
    public ModelAndView insertMenuManage(@RequestParam Map<?, ?> commandMap, @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult, ModelMap model) throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");

        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
        	mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    		return mv;
    	}
        
    	beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		return mv;
		}
		if(menuManageService.selectMenuNoByPk(menuManageVO) == 0){
			ComDefaultVO searchVO = new ComDefaultVO();
			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
			if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
		        mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.insert"));
			}else{
	        	menuManageService.insertMenuManage(menuManageVO);
		        mv.addObject("status","success");
	    		mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
			}
		}else{
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("common.isExist.msg"));
		}
		
		return mv;
    }

    /**
     * 메뉴정보를 수정 한다.
     * @param menuManageVO  MenuManageVO
     * @return 출력페이지정보 "forward:/mng/mnu/mpm/menuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuManageListUpdate.do")
    public ModelAndView updateMenuManage(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    		return mv;
    	}
        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		return mv;
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.update"));
		}else{
			menuManageService.updateMenuManage(menuManageVO);
			mv.addObject("status","success");
    		mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
		}
		return mv;
    }

    /**
     * 메뉴정보를 삭제 한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "forward:/mng/mnu/mpm/menuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuManageDelete.do")
    public ModelAndView deleteMenuManage(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	String message    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    		return mv;
    	}
    	if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0){
    		mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.delete.upperMenuExist"));
    		return mv;
		}

    	menuManageService.deleteMenuManage(menuManageVO);
    	mv.addObject("status","success");
		model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
    	//String _MenuNm = "%";
    	//menuManageVO.setMenuNm(_MenuNm);
      	return mv;
    }

    /**
     * 메뉴리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuList"
     * @exception Exception
     */
    @IncludedInfo(name="메뉴리스트관리", order = 1090 ,gid = 60)
    @RequestMapping(value="/mng/mnu/mpm/menuListSelect.do")
    public String selectMenuList(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	String message    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "mng/uia/loginUsr";
    	}
    	List<?> list_menulist = menuManageService.selectMenuList();
    	message = egovMessageSource.getMessage("success.common.select");
        model.addAttribute("list_menulist", list_menulist);
//        model.addAttribute("message", message);
      	return  "mng/mnu/mpm/menuList";
    }

    /**
     * 메뉴리스트의 메뉴정보를 등록한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuList"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuListInsert.do")
    public String insertMenuList(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String message    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "mng/mnu/mpm/menuList";
			return sLocationUrl;
		}

		if(menuManageService.selectMenuNoByPk(menuManageVO) == 0){
			ComDefaultVO searchVO = new ComDefaultVO();
			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
			if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
	    		message = egovMessageSource.getMessage("fail.common.insert");
		        sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
			}else{
	        	menuManageService.insertMenuManage(menuManageVO);
	    		message = egovMessageSource.getMessage("success.common.insert");
		        sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
			}
		}else{
    		message = egovMessageSource.getMessage("common.isExist.msg");
    		sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
		}
		model.addAttribute("message", message);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 수정한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuList"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuListUpdt.do")
    public String updateMenuList(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String message    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
			return sLocationUrl;
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    		message = egovMessageSource.getMessage("fail.common.update");
	        sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
		}else{
			menuManageService.updateMenuManage(menuManageVO);
			message = egovMessageSource.getMessage("success.common.update");
	        sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
		}
		model.addAttribute("message", message);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 삭제한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuList"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuListDelete.do")
    public String deleteMenuList(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
        String sLocationUrl = null;
    	String message    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "mng/mnu/mpm/menuList";
			return sLocationUrl;
		}
		menuManageService.deleteMenuManage(menuManageVO);
		message = egovMessageSource.getMessage("success.common.delete");
        sLocationUrl = "forward:/mng/mnu/mpm/menuListSelect.do";
		model.addAttribute("message", message);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 이동 메뉴목록을 조회한다.
     * @param searchVO  ComDefaultVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuMvmn"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuListSelectMvmn.do")
    public String selectMenuListMvmn(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}

    	List<?> list_menulist = menuManageService.selectMenuList();
        model.addAttribute("list_menulist", list_menulist);
      	return  "mng/mnu/mpm/menuMvmn";
    }

    /**
     * 메뉴리스트의 메뉴정보를 이동 메뉴목록을 조회한다. (New)
     * @param searchVO  ComDefaultVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuMvmn"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuListSelectMvmnPopup.do")
    public String selectMenuListMvmnPopup(
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}

    	List<?> list_menulist = menuManageService.selectMenuList();
        model.addAttribute("list_menulist", list_menulist);
      	return  "mng/mnu/mpm/menuMvmnPopup";
    }


    /*### 일괄처리 프로세스 ###*/

    /**
     * 메뉴생성 일괄삭제프로세스
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "mng/mnu/mpm/menuBndeRegist"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuBndeAllDelete.do")
    public ModelAndView menuBndeAllDelete(
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    	}
    	
    	menuManageService.menuBndeAllDelete();
    	mv.addObject("status","success");
		mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
    	
		return mv;
    }
    
    /**
     * 메뉴일괄등록화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuBndeRegistView.do")
    public String fncBndeInsertMenuManageView(ModelMap model) throws Exception{
    	return "mng/mnu/mpm/menuBndeRegist";
    }
    
    /**
     * 메뉴일괄등록화면 호출 및  메뉴일괄등록처리 프로세스
     * @param commandMap    Map
     * @param menuManageVO  MenuManageVO
     * @param request       HttpServletRequest
     * @return 출력페이지정보 "mng/mnu/mpm/menuBndeRegist"
     * @exception Exception
     */
    @RequestMapping(value="/mng/mnu/mpm/menuBndeRegist.do")
    public ModelAndView menuBndeRegist(
    		@RequestParam Map<?, ?> commandMap,
    		final HttpServletRequest request,
    		@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model)
            throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
			return mv;
    	}
    	
    	String sMessage  = "";
    	
    	final MultipartHttpServletRequest multiRequest = WebUtils.getNativeRequest(request,MultipartHttpServletRequest.class);
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();
			file = entry.getValue();
			if (!"".equals(file.getOriginalFilename())) {
				// 2011.10.07 업로드 파일에 대한 확장자를 체크
				if (file.getOriginalFilename().endsWith(".xls")
						|| file.getOriginalFilename().endsWith(".xlsx")
						|| file.getOriginalFilename().endsWith(".XLS")
						|| file.getOriginalFilename().endsWith(".XLSX")) {

					if(menuManageService.menuBndeAllDelete()){
						// KISA 보안약점 조치 - 자원해제
						InputStream is = null;
						try {
							is = file.getInputStream();
							sMessage = menuManageService.menuBndeRegist(menuManageVO, is);
							
							mv.addObject("status","success");
							mv.addObject("message", sMessage);
						} catch (IOException e) {
							throw new IOException(e);
						} finally {
							is.close();
						}
					}else{
						menuManageVO.setTmpCmd("MenuBndeRegist Error!!");
				        
				        mv.addObject("status","fail");
						mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
						mv.addObject("resultVO", menuManageVO);
						
					}
				}else{
					LOGGER.info("xls, xlsx 파일 타입만 등록이 가능합니다.");
					mv.addObject("status","fail");
					mv.addObject("message", egovMessageSource.getMessage("fail.common.msg")+"\nxls, xlsx 파일 타입만 등록이 가능합니다.");
					
					return mv;
				}
				// *********** 끝 ***********

			}else{
				mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg")+"\nxls, xlsx 파일 타입만 등록이 가능합니다.");
			}
			file = null;
		}
		return mv;
    }
}