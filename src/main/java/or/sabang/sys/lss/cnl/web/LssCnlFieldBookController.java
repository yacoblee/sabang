package or.sabang.sys.lss.cnl.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookItemVO;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookService;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookVO;
import or.sabang.sys.lss.cnl.service.LssCnlStripLandVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class LssCnlFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "lssCnlFieldBookService")
	private LssCnlFieldBookService lssCnlFieldBookService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.stripland");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssCnlFieldBookController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/selectFieldBookList.do")
	public String selectFieldBookList(@ModelAttribute("searchVO") LssCnlFieldBookVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectFieldBookList controller");
		

		List<LssCnlFieldBookVO> list = null;
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
 		if(searchVO.getSvy_year() == null) {
			searchVO.setSvy_year(lssCnlFieldBookService.selectLssCnlFieldBookMaxYear());
		}
		
		//회사명 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		//연도코드 조회
		List<?> year_result = lssCnlFieldBookService.selectLssCnlFieldBookYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = lssCnlFieldBookService.selectLssCnlFieldBookList(searchVO);
			cnt_total = lssCnlFieldBookService.selectLssCnlFieldBookListTotCnt(searchVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList", list);
		
		return "sys/lss/cnl/fbk/fieldBookList";
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 상세조회
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/selectFieldBookDetail.do")
    public String selectFieldBookDetail(
    		@ModelAttribute("searchVO") LssCnlFieldBookItemVO searchVO, HttpServletRequest req,
    		@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO,
    		Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		String id = req.getParameter("mstId");
		
		model.addAttribute("mstId", id);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", user.getUniqId());
		
		/* 공유방 상세정보조회 */
		LssCnlFieldBookVO result  = lssCnlFieldBookService.selectLssCnlFieldBookDetail(commandMap);
		
		/* 공유방 권한 확인 */
		String access = lssCnlFieldBookService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result",result);
		model.addAttribute("access",access);
		
		searchVO.setMst_id(id);

		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList); 
		
		//시군구코드 조회
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}				
		
		
		/* 공유방 조사데이터 리스트조회 */
		List<LssCnlStripLandVO> resultList = null;
		
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageSubIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		resultList = lssCnlFieldBookService.selectLssCnlFieldBookItemList(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		for(int i=0; i<resultList.size(); i++) {
			if(resultList.get(i).getLon() != null && !resultList.get(i).getLon().equals("")) {
				dmsformal.setDmsLon(resultList.get(i).getLon());
				resultList.get(i).setLon(dmsformal.getDmsLon());
			}
			if(resultList.get(i).getLat() != null && !resultList.get(i).getLat().equals("")) {
				dmsformal.setDmsLat(resultList.get(i).getLat());
				resultList.get(i).setLat(dmsformal.getDmsLat());
			}
		}
		
		int totCnt = lssCnlFieldBookService.selectLssCnlFieldBookItemListTotCnt(searchVO);
		
		result.setTotcnt(Integer.toString(totCnt));
	
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/cnl/fbk/fieldBookDetail";
	}
	
	/**
	 * @param mst_id
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 조사데이터 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/rgstFieldBookItemPopup.do")
	public String uploadRptCnlFilePopup(@RequestParam(value="mst_id") String mst_id, HttpServletRequest req, ModelMap model) throws Exception {
		model.addAttribute("mst_id", mst_id);
		return "sys/lss/cnl/fbk/rgstFieldBookItemPopup";
	}

	/**
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/insertCnrsSpcePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO,
			ModelMap model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		List<?> orgcht_list = lssCnlFieldBookService.selectOrgchtList();
		model.addAttribute("orgchtList", orgcht_list);

		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		return "sys/lss/cnl/fbk/insertCnrsSpcePopup";
	}

	/**
	 * @param fieldBookVO
	 * @param req
	 * @param model
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 * @description 공유방 등록
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/insertCnrsSpce.do")
    public ModelAndView insertCnrsSpce(
    		@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO, 
    		@RequestParam(value = "file") MultipartFile mFile,
    		HttpServletRequest req,
    		Model model, 
    		BindingResult bindingResult) throws Exception {
		ModelAndView mv = null;
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			JSONObject results = null;
			
			String mstId = null;
			try {
				String mst_partname = fieldBookVO.getMst_partname();
				String mst_password = fieldBookVO.getMst_password();
//				String mst_adminpwd = fieldBookVO.getMst_adminpwd();
//				String mst_readpwd = fieldBookVO.getMst_readpwd();
				int cnt = lssCnlFieldBookService.selectLssCnlFieldBookCheckMstName(mst_partname);
				if(mst_partname.isEmpty() != true && mst_password.isEmpty() != true) {
					if(cnt > 0) {
						mv.addObject("cnt", "fail");
						
						return mv;
					}else {
						mv.addObject("cnt", "success");
						List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir+"cnl"+File.separator, maxFileSize, extWhiteList);
						if (list.size() > 0) {
							fieldBookVO.setMst_admin_user(loginVO.getName());
							fieldBookVO.setMst_admin_id(loginVO.getUniqId());
							mstId = lssCnlFieldBookService.insertCnrsSpce(fieldBookVO);
							commandMap.put("id", mstId);
							fieldBookVO.setId(mstId);
							lssCnlFieldBookService.insertCnrsSpceAuthorMgt(fieldBookVO);
							results = lssCnlFieldBookService.insertStripLand(fieldBookVO,mFile);
						}
						mv.addObject("status","success");
						
						if(results != null) {
							String total = results.get("total").toString();
							String successCnt = results.get("successCnt").toString();
							JSONArray failedIds = (JSONArray)results.get("failedIds");
							String failedCnt = String.valueOf(failedIds.length());
							String message = "전체 "+total+"건 중 "+successCnt+"건이 등록되었습니다.\n총 : "+
								total+" 건\n등록 : "+successCnt+" 건\n실패 : "+failedCnt+" 건\n실패목록 : "+failedIds.join(",");
							mv.addObject("message", message);
						}else {
							mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
						}
					}
				}else {
					mv.addObject("nnt", "fail");
				}
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				lssCnlFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				lssCnlFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
		}
		return mv;
	}

	/**
	 * @param stripLandVO
	 * @param mst_id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 대상지 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/insertCnrsSpceSldPopup.do")
	public String insertCnrsSpceSldPopup(
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="mst_create_user") String mst_create_user,
			ModelMap model) throws Exception {
		
		model.addAttribute("mst_id",mst_id);
		model.addAttribute("mst_create_user",mst_create_user);
		
		return "sys/lss/cnl/fbk/insertCnrsSpceSldPopup";
	}

	/**
	 * @param id
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 대상지 등록
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/insertCnrsSpceSld.do")
	public ModelAndView insertCnrsSpceSld(
			@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO,
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="mst_create_user") String mst_create_user,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		fieldBookVO.setId(mst_id);
		fieldBookVO.setMst_create_user(mst_create_user);
		JSONObject results = lssCnlFieldBookService.insertStripLand(fieldBookVO,mFile);
		
		try {
			List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
			
			String total = results.get("total").toString();
			String successCnt = results.get("successCnt").toString();
			JSONArray failedIds = (JSONArray)results.get("failedIds");
			String failedCnt = String.valueOf(failedIds.length());
			String message = "";
			
			if(failedIds.length() == 0) {
				message = "전체 "+total+"건 중 "+successCnt+"건이 등록되었습니다.";
			}else {
				message = "등록에 실패하였습니다.\n총 : "+
						total+" 건 중 "+failedCnt+" 건의 오류항목이 존재합니다.\n오류목록 : "+failedIds.join(",");
			}
			
			mv.addObject("message", message);
			mv.addObject("status","success");
		} catch (SecurityException e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}

	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/updateFieldBookView.do")
    public String updateFieldBookView(
    		@ModelAttribute("searchVO") LssCnlFieldBookItemVO searchVO,
    		@RequestParam(value="id") String id, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 상세정보조회 */
		LssCnlFieldBookVO result  = lssCnlFieldBookService.selectLssCnlFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		searchVO.setMst_id(id);
		
		/* 공유방 조사데이터 리스트조회 */
		List<LssCnlStripLandVO> resultList = lssCnlFieldBookService.selectLssCnlFieldBookItemView(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = lssCnlFieldBookService.selectLssCnlFieldBookItemListTotCnt(searchVO);
		
		result.setTotcnt(Integer.toString(totCnt));
				
		return "sys/lss/cnl/fbk/fieldBookUpdt";
	}

	/**
	 * @param label
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 공유방 조사데이터 수정
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/updateCnrsSpceItem.do")
	public void updateCnrsSpce(
			@RequestParam(value="label[]") List<String> label,
			@RequestParam(value="mst_id") String mst_id,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		
		JSONObject result = new JSONObject();
		try {
			for(int i=0; i<label.size(); i++) {
				HashMap<String, Object> commandMap = new HashMap<>();
				commandMap.put("label", label.get(i));
				commandMap.put("mst_id", mst_id);
				lssCnlFieldBookService.deleteCnrsSpceItem(commandMap);
				result.put("status", "success");
				result.put("message", egovMessageSource.getMessage("success.common.update"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			result.put("status", "failed");
			result.put("message", egovMessageSource.getMessage("fail.common.msg"));
		}		
		out.print(result);
	}
	
	/**
	 * 공유방 삭제
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/deleteCnrsSpce.do")
	public ModelAndView deleteCnrsSpce(@RequestParam(value="id") String id, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			lssCnlFieldBookService.deleteCnrsSpce(commandMap);
			lssCnlFieldBookService.deleteCnrsSpceAllItem(commandMap);
			//220314 공유방 삭제시 조사완료 데이터 삭제X  
			lssCnlFieldBookService.deleteCnrsSpceComptItem(commandMap);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 상세조회
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/selectFieldBookDetailOne.do")
    public String selectFieldBookDetailOne(@ModelAttribute("searchVO") LssCnlFieldBookItemVO searchVO,
    		@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO,
    		Model model, HttpServletRequest req) throws Exception {
		
		String id = req.getParameter("mstId");
		
		// 공유방 단건 상세조회
		LssCnlFieldBookItemVO result = lssCnlFieldBookService.selectFieldBookDetailOne(searchVO);
		result.setMst_id(id);
		model.addAttribute("result", result);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		model.addAttribute("mstId", id);
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if(result.getSvyLon() != null && !result.getSvyLon().equals("")) {
			dmsformal.setDmsLon(result.getSvyLon());
			result.setSvyLon(dmsformal.getDmsLon());
		}
		if(result.getSvyLat() != null && !result.getSvyLat().equals("")) {
			dmsformal.setDmsLat(result.getSvyLat());
			result.setSvyLat(dmsformal.getDmsLat());
		}
		
		
		/* 공유방 권한 확인 */
		String access = lssCnlFieldBookService.selectAuthorCheck(commandMap);
		model.addAttribute("access",access);
		
		return "sys/lss/cnl/fbk/fieldBookDetailOne";
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정페이지 이동
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/updateFieldBookViewOne.do")
	public String updateFieldBookViewOne(@ModelAttribute("searchVO") LssCnlFieldBookItemVO searchVO,
			@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO, HttpServletRequest req,
			Model model) throws Exception {
		
		
		String id = req.getParameter("mstId");
		model.addAttribute("mstId", id);
		LssCnlFieldBookItemVO result = lssCnlFieldBookService.selectFieldBookDetailOne(searchVO);
		result.setMst_id(id);
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		// 도분초 자르기
		String svyLatLon = result.getSvyLat()+" "+result.getSvyLon();
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(svyLatLon);

		while (matcher.find()) {
		    String LatD = matcher.group(1);
		    String LatM = matcher.group(2);
		    String LatS = matcher.group(3);
		    
		    String LonD = matcher.group(5);
		    String LonM = matcher.group(6);
		    String LonS = matcher.group(7);
		    
		    
		    if(LatD != null && !LatD.equals("")) {
		    	dmsformal.setDmsLatDeg(LatD);
		    	result.setSvy_latD(dmsformal.getDmsLatDeg());
		    }
		    if(LatM != null && !LatM.equals("")) {
		    	dmsformal.setDmsLatMin(LatM);
		    	result.setSvy_latM(dmsformal.getDmsLatMin());
		    }
		    if(LatS != null && !LatS.equals("")) {
		    	dmsformal.setDmsLatSec(LatS);
		    	result.setSvy_latS(dmsformal.getDmsLatSec());
		    }
		    if(LonD != null && !LonD.equals("")) {
		    	dmsformal.setDmsLonDeg(LonD);
		    	result.setSvy_lonD(dmsformal.getDmsLonDeg());
		    }
		    if(LonM != null && !LonM.equals("")) {
		    	dmsformal.setDmsLonMin(LonM);
		    	result.setSvy_lonM(dmsformal.getDmsLonMin());
		    }
		    if(LonS != null && !LonS.equals("")) {
		    	dmsformal.setDmsLonSec(LonS);
		    	result.setSvy_lonS(dmsformal.getDmsLonSec());
		    }
		}
		
		model.addAttribute("result", result);
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(result.getSvySd() != null) {
			adminVO.setSdNm(result.getSvySd());
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}

		//읍면동코드 조회
		if(result.getSvySgg() != null) {
			adminVO.setSdNm(result.getSvySd());
			adminVO.setSggNm(result.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}

		//리코드 조회
		if(result.getSvyRi() != null) {
			adminVO.setSdNm(result.getSvySd());
			adminVO.setSggNm(result.getSvySgg());
			adminVO.setEmdNm(result.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		return "sys/lss/cnl/fbk/fieldBookUpdtOne";
	}
	
	/**
	 * @param searchVO
	 * @param id 
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/updateFieldBookDetailOne.do")
	public ModelAndView updateFieldBookDetailOne(@ModelAttribute("searchVO") LssCnlFieldBookItemVO searchVO,
			@ModelAttribute("fieldBookVO") LssCnlFieldBookItemVO fieldBookVO,
			ModelMap model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			String bpy = req.getParameter("bpy");
			String bpx = req.getParameter("bpx");
			String svy_attr = "["
					+ "{\"VALUE\":\""+searchVO.getSn()+"\",\"NAME\":\"순번\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyId()+"\",\"NAME\":\"조사ID\"},"
					+ "{\"VALUE\":\""+searchVO.getSvySd()+"\",\"NAME\":\"시도\"},"
					+ "{\"VALUE\":\""+searchVO.getSvySgg()+"\",\"NAME\":\"시군구\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyEmd()+"\",\"NAME\":\"읍면동\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyRi()+"\",\"NAME\":\"리\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyJibun()+"\",\"NAME\":\"지번\"},"
					+ "{\"VALUE\":\""+searchVO.getRegion1()+"\",\"NAME\":\"관할1\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyType()+"\",\"NAME\":\"조사유형\"},"
					+ "{\"VALUE\":\""+bpx+"\",\"NAME\":\"위도\"},"
					+ "{\"VALUE\":\""+bpy+"\",\"NAME\":\"경도\"}"
					+ "]";
			
			searchVO.setSvy_attr(svy_attr);
			
			
			
			HashMap<String, Object> projMap = new HashMap<>();
			projMap.put("bpx",bpx);
			projMap.put("bpy",bpy);
			
			// 4326 to 5186
			List<EgovMap> projList = lssCnlFieldBookService.selectLssCnlProjBessel(projMap);
			
			if(!projList.get(0).isEmpty()) {
				if(projList.get(0).containsKey("bpx")) {
					searchVO.setSvyLon(projList.get(0).get("bpx").toString());
				}
				if(projList.get(0).containsKey("bpy")) {
					searchVO.setSvyLat(projList.get(0).get("bpy").toString());
				}
			}
			
			lssCnlFieldBookService.updateFieldBookDetailOne(searchVO);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.update"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param id 
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 삭제
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/deleteFieldBookDetailOne.do")
	public ModelAndView deleteFieldBookDetailOne(@ModelAttribute("searchVO") LssCnlFieldBookItemVO searchVO, ModelMap model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("smid", searchVO.getSmid());
			
			lssCnlFieldBookService.deleteFieldBookDetailOne(commandMap);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * 사용자 조회
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/selectMberList.do")
	public ModelAndView selectMberList(
			  @RequestParam(value="searchKeyword") String searchKeyword,
			  @RequestParam(value="searchCondition") String searchCondition,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			LssCnlFieldBookVO vo = new LssCnlFieldBookVO();
			vo.setSearchKeyword(searchKeyword);
			vo.setSearchCondition(searchCondition);
			
			List<EgovMap> list = lssCnlFieldBookService.selectMberList(vo);
			
			mv.addObject("cnt",list);
			mv.addObject("list",list);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 권한관리 페이지 이동
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/selectFieldbookAuthoryView.do")
    public String selectFieldbookAuthoryView(
    		@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 권한자 조회 */
		List<LssCnlFieldBookVO> result = lssCnlFieldBookService.selectCnrsAuthorList(commandMap);
		
		model.addAttribute("resultList", result);
		model.addAttribute("mstId", id);
		return "sys/lss/cnl/fbk/fieldBookAuthorManage";
	}
	
	/**
	 * 부서별 회원목록 트리 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/selectAuthorUserPopup.do")
	public String selectAuthorUserPopup(LssCnlFieldBookVO vo,ModelMap model) throws Exception{
		
		List<String> authorEsntlIdList = Arrays.asList(vo.getAuthorEsntlIdList());
		
		List<EgovMap> deptList = lssCnlFieldBookService.selectDeptInfoList();
		List<EgovMap> deptMberList = lssCnlFieldBookService.selectDeptCreatList();
		
		model.addAttribute("dept_list",deptList);
		model.addAttribute("dept_mber_list", deptMberList);
		model.addAttribute("authorEsntlIdList", authorEsntlIdList);
		
		
		return "sys/lss/cnl/fbk/searchAuthorUserPopup";
	}
	
	/**
	 * @param fieldBookVO
	 * @param req
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 사용자 권한 수정
	 */
	@RequestMapping(value = "/sys/lss/cnl/fbk/updateCnrsSpceAuthorMgt.do")
	public ModelAndView updateCnrsSpceAuthorMgt(
			@ModelAttribute("fieldBookVO") LssCnlFieldBookVO fieldBookVO, 
    		HttpServletRequest req,
    		BindingResult bindingResult,
			ModelMap model) throws Exception {
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			String id = req.getParameter("mstId");
			fieldBookVO.setId(id);
			
			lssCnlFieldBookService.updateCnrsSpceAuthorMgt(fieldBookVO);
			mv.addObject("status","success");
			mv.addObject("message",egovMessageSource.getMessage("success.common.update"));
		} catch(Exception e) {
			e.printStackTrace();
			mv.addObject("status","failed");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}		
		return mv;
	}
}
