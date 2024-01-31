package or.sabang.mng.prm.web;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import or.sabang.mng.prm.service.ProgrmManageService;
import or.sabang.mng.prm.service.ProgrmManageVO;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;

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
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 프로그램목록 관리및 변경을 처리하는 비즈니스 구현 클래스
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *   2011.08.22  서준식          selectProgrmChangRequstProcess() 메서드 처리일자 trim 처리
 *   2011.8.26	정진오			IncludedInfo annotation 추가
 * </pre>
 */

@Controller
public class ProgrmManageController {

	/** Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** EgovProgrmManageService */
	@Resource(name = "progrmManageService")
    private ProgrmManageService progrmManageService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

    /** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.program");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgrmManageController.class);
    
    /**
     * 프로그램목록을 상세화면 호출 및 상세조회한다.
     * @param tmp_progrmNm  String
     * @return 출력페이지정보 "sym/prm/programListDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/mng/prm/programListDetail.do")
    public String selectProgrm(
    		@RequestParam("tmp_progrmNm") String tmp_progrmNm ,
   		    @ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
    	
    	ProgrmManageVO vo = new ProgrmManageVO();
    	vo.setProgrmFileNm(tmp_progrmNm);
    	ProgrmManageVO progrmManageVO = progrmManageService.selectProgrm(vo);
        model.addAttribute("progrmManageVO", progrmManageVO);
        return "mng/prm/programListDetail";
    }

    /**
     * 프로그램목록 리스트조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/programListManage"
     * @exception Exception
     */
    @IncludedInfo(name="프로그램관리",order = 1111 ,gid = 60)
    @RequestMapping(value="/mng/prm/programListManageSelect.do")
    public String selectProgrmList(
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

        List<?> list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);
        model.addAttribute("searchVO", searchVO);

        int totCnt = progrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "mng/prm/programListManage";

    }

    /**
     * 프로그램목록 멀티 삭제한다.
     * @param checkedProgrmFileNmForDel String
     * @return 출력페이지정보 "forward:/mng/prm/programListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping("/mng/prm/progrmManageListDelete.do")
    public ModelAndView deleteProgrmManageList(
            @RequestParam("checkedProgrmFileNmForDel") String checkedProgrmFileNmForDel ,
            @ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO,
            ModelMap model)
            throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");

        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    	}
    	String [] delProgrmFileNm = checkedProgrmFileNmForDel.split(",");
		if (delProgrmFileNm == null || (delProgrmFileNm.length ==0)){
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.delete"));
		}else{
			progrmManageService.deleteProgrmManageList(checkedProgrmFileNmForDel);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
		}
		
        return mv ;
    }

    /**
     * 프로그램목록을 등록화면으로 이동한다.
     * @param progrmManageVO ProgrmManageVO
     * @param commandMap     Map
     * @return 출력페이지정보 등록화면 호출시 "sym/prm/programListRegist",
     *         출력페이지정보 등록처리시 "forward:/mng/prm/programListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/mng/prm/programListRegist.do")
    public String programListRegist(
    		@RequestParam Map<?, ?> commandMap,
    		@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO,
			ModelMap model)
            throws Exception {

    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}

		return "mng/prm/programListRegist";
    }

    /**
     * 프로그램목록을 등록 한다.
     * @param progrmManageVO ProgrmManageVO
     * @param commandMap     Map
     * @return 출력페이지정보 등록화면 호출시 "sym/prm/programListRegist",
     *         출력페이지정보 등록처리시 "forward:/mng/prm/programListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/mng/prm/insertProgrmListRegist.do")
    public ModelAndView insertProgrmListRegist(
    		@RequestParam Map<?, ?> commandMap,
    		@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO,
			BindingResult bindingResult,
			ModelMap model)
            throws Exception {
    	ModelAndView mv = new ModelAndView("jsonView");
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    	}else {
    		beanValidator.validate(progrmManageVO, bindingResult);
    		if (bindingResult.hasErrors()){
    			mv.addObject("status","fail");
    			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
    		}else {
    			if(progrmManageVO.getProgrmDc()==null || progrmManageVO.getProgrmDc().equals("")){progrmManageVO.setProgrmDc(" ");}
    	    	progrmManageService.insertProgrm(progrmManageVO);
    	    	
    	    	mv.addObject("status","success");
    			mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
    		}
    	}
        
		return mv;
    }
    
    /**
     * 프로그램목록을 수정화면으로 이동한다.
     * @param progrmManageVO ProgrmManageVO
     * @return 출력페이지정보 "forward:/mng/prm/programListManageSelect.do"
     * @exception Exception
     */
    /*프로그램목록수정*/
    @RequestMapping(value="/mng/prm/programListUpdtSelect.do")
    public String selectUpdateProgrmList(
    		@RequestParam("checkProgrmFileNm") String checkProgrmFileNm ,
    		@ModelAttribute("progrmManageVO") ProgrmManageVO programVO,
    		@ModelAttribute("searchVO") ComDefaultVO searchVO,
    		ModelMap model)
            throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
    	
    	ProgrmManageVO vo = new ProgrmManageVO();
    	vo.setProgrmFileNm(checkProgrmFileNm);
    	ProgrmManageVO progrmManageVO = progrmManageService.selectProgrm(vo);
        model.addAttribute("progrmManageVO", progrmManageVO);
        return "mng/prm/programListUpdt";
    }
    
    /**
     * 프로그램목록을 수정 한다.
     * @param progrmManageVO ProgrmManageVO
     * @return 출력페이지정보 "forward:/mng/prm/programListManageSelect.do"
     * @exception Exception
     */
    /*프로그램목록수정*/
    @RequestMapping(value="/mng/prm/programListUpdt.do")
    public ModelAndView updateProgrmList(
    		@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO,
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

        beanValidator.validate(progrmManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			return mv;
		}
		
		if(progrmManageVO.getProgrmDc()==null || progrmManageVO.getProgrmDc().equals("")){progrmManageVO.setProgrmDc(" ");}
		progrmManageService.updateProgrm(progrmManageVO);
		
		mv.addObject("status","success");
		mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
		
		return mv;
    }

    /**
     * 프로그램목록을 삭제 한다.
     * @param progrmManageVO ProgrmManageVO
     * @return 출력페이지정보 "forward:/mng/prm/programListManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/mng/prm/programListManageDelete.do")
    public String deleteProgrmList(
    		@ModelAttribute("progrmManageVO")
    		ProgrmManageVO progrmManageVO,
    		ModelMap model)
            throws Exception {
    	String resultMsg = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "sys/uia/loginUsr";
    	}
        progrmManageService.deleteProgrm(progrmManageVO);
        resultMsg = egovMessageSource.getMessage("success.common.delete");
    	model.addAttribute("resultMsg", resultMsg);
        return "forward:/mng/prm/programListManageSelect.do";
    }

    /**
     * 프로그램목록 업로드 팝업
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/mng/prm/uploadProgramListPopup.do")
    public String insertMultiProgramListPopup() throws Exception{
    	return "mng/prm/insertMultiProgramListPopup";
    }
    
    /**
     * 프로그램 목록 엑셀저장
     * @param checkCls
     * @param prmFile
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/mng/prm/uploadProgramExcel.do")
    public ModelAndView uploadProgramExcel(
    		@ModelAttribute("checkCls") String checkCls,
    		@ModelAttribute("prmFile") MultipartFile prmFile,
    		ModelMap model
    		) throws Exception{
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    	}else {
    		try {
    			List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(prmFile, uploadDir, maxFileSize, extWhiteList);
    			if(list.size() > 0) {
    				progrmManageService.uploadProgramExcel(prmFile);
    				mv.addObject("status","success");
    				mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
    			}
    		} catch (SecurityException e) {
    			LOGGER.error(e.getMessage());
    			mv.addObject("status","fail");
        		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
    		} catch (Exception e) {
    			LOGGER.error(e.getMessage());
    			mv.addObject("status","fail");
        		mv.addObject("message", egovMessageSource.getMessage("errors.file.transfer"));
    		}
    	}
    	
    	return mv;
    }
    /**
     * 프로그램파일명을 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovFileNmSearch"
     * @exception Exception
     */
    @RequestMapping(value="/mng/prm/programListSearch.do")
    public String selectProgrmListSearch(
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

        List<?> list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);

        int totCnt = progrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "mng/prm/fileNmSearch";

    }

    /**
     * 프로그램파일명을 조회한다. (New)
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/prm/EgovFileNmSearch"
     * @exception Exception
     */
    @RequestMapping(value="/mng/prm/programListSearchPopup.do")
    public String programListSearchPopup(
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

        List<?> list_progrmmanage = progrmManageService.selectProgrmList(searchVO);
        model.addAttribute("list_progrmmanage", list_progrmmanage);
        model.addAttribute("searchVO",searchVO);
        
        int totCnt = progrmManageService.selectProgrmListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "mng/prm/fileNmSearchPopup";

    }
}