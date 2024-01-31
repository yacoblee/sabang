package or.sabang.sys.fck.apr.web;

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
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookService;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;
import or.sabang.sys.fck.apr.service.FckAprStripLandVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class FckAprFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "fckAprFieldBookService") 	
	private FckAprFieldBookService fckAprFieldBookService;
	
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
    
	private static final Logger LOGGER = LoggerFactory.getLogger(FckAprFieldBookController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/selectFieldBookList.do")
	public String selectFieldBookList(@ModelAttribute("searchVO") FckAprFieldBookVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectFieldBookList controller");
		
		List<FckAprFieldBookVO> list = null;
		
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
			searchVO.setSvy_year(fckAprFieldBookService.selectFckAprFieldBookMaxYear());
		}
		
		//회사명 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		//연도코드 조회
		List<?> year_result = fckAprFieldBookService.selectFckAprFieldBookYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = fckAprFieldBookService.selectFckAprFieldBookList(searchVO);
			cnt_total = fckAprFieldBookService.selectFckAprFieldBookListTotCnt(searchVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
 		model.addAttribute("resultList", list);
		
		return "sys/fck/apr/fbk/fieldBookList";
	}
	
	/**
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/insertCnrsSpcePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
			ModelMap model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		List<?> orgcht_list = fckAprFieldBookService.selectOrgchtList();
		model.addAttribute("orgchtList", orgcht_list);
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		return "sys/fck/apr/fbk/insertCnrsSpcePopup";
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
	@RequestMapping(value = "/sys/fck/apr/fbk/insertCnrsSpce.do")
    public ModelAndView insertCnrsSpce(
    		@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
    		@RequestParam(value = "file") MultipartFile mFile,
    		HttpServletRequest req,
    		Model model, 
    		BindingResult bindingResult) throws Exception {
 		long start = System.currentTimeMillis();
		// 추후 수정
		ModelAndView mv = null;
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else { 
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>(); 
			JSONObject results  = null; 
			
			String mstId = null;  
			
			try { 
				String mst_partname = fieldBookVO.getMst_partname();
				String mst_password = fieldBookVO.getMst_password();
//				String mst_adminpwd = fieldBookVO.getMst_adminpwd();
//				String mst_readpwd = fieldBookVO.getMst_readpwd();
				
				int cnt = fckAprFieldBookService.selectFckAprFieldBookCheckMstName(mst_partname);
				
				if(mst_partname.isEmpty() != true && mst_password.isEmpty() != true) {
  					if(cnt > 0) {
						mv.addObject("cnt", "fail");
						return mv;
					}else {
						mv.addObject("cnt", "success");
						List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir+"apr"+File.separator, maxFileSize, extWhiteList);
						if (list.size() > 0) {
							fieldBookVO.setMst_admin_user(loginVO.getName());
							fieldBookVO.setMst_admin_id(loginVO.getUniqId());
							mstId = fckAprFieldBookService.insertCnrsSpce(fieldBookVO);
							commandMap.put("id", mstId);
							fieldBookVO.setId(mstId);
							fckAprFieldBookService.insertCnrsSpceAuthorMgt(fieldBookVO);
							results = fckAprFieldBookService.insertStripLand(fieldBookVO,mFile);
						}
						mv.addObject("status","success"); 
						
						if(results != null) {
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
						}else {
							mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
						}
					}
				
				}else {
					mv.addObject("nnt", "fail");
				}
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				fckAprFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				fckAprFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
		}
		long end = System.currentTimeMillis();
		LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		return mv;
	}

	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 상세조회
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/selectFieldBookDetail.do")
    public String selectFieldBookDetail(
    		@ModelAttribute("searchVO") FckAprFieldBookVO searchVO,
    		@ModelAttribute("searchItemVO") FckAprFieldBookItemVO searchItemVO,  HttpServletRequest req,
    		Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		String id = req.getParameter("mstId");
		
		model.addAttribute("mstId", id);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", user.getUniqId());
		
		/* 공유방 상세정보조회 */
		FckAprFieldBookVO result  = fckAprFieldBookService.selectFckAprFieldBookDetail(commandMap);
		
		/* 공유방 권한 확인 */
		String access = fckAprFieldBookService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result",result);
		model.addAttribute("access",access);
		
		searchItemVO.setMST_ID(id);
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList); 
		
		//시군구코드 조회
		if(searchItemVO.getSd() != null && !searchItemVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchItemVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(searchItemVO.getSgg() != null && !searchItemVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchItemVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchItemVO.getEmd() != null && !searchItemVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchItemVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}	
		/* 공유방 조사데이터 리스트조회 */
		List<FckAprStripLandVO> resultList = null;
		
		/** EgovPropertyService.sample */
		searchItemVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchItemVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchItemVO.getPageSubIndex());
		paginationInfo.setRecordCountPerPage(searchItemVO.getPageUnit());
		paginationInfo.setPageSize(searchItemVO.getPageSize());
		
		searchItemVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchItemVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchItemVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		resultList = fckAprFieldBookService.selectFckAprFieldBookItemList(searchItemVO);
		
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
		
		int totCnt = fckAprFieldBookService.selectFckAprFieldBookItemListTotCnt(searchItemVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
				
		return "sys/fck/apr/fbk/fieldBookDetail";
	}
	
	/**
	 * @param stripLandVO
	 * @param mst_id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 대상지 등록 팝업
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/insertCnrsSpceSldPopup.do")
	public String insertCnrsSpceSldPopup(
			@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
			@RequestParam(value="mst_id") String mst_id,
			ModelMap model) throws Exception {
		
		model.addAttribute("mst_id",mst_id);
		
		return "sys/fck/apr/fbk/insertCnrsSpceSldPopup";
	}

	@RequestMapping(value = "/sys/fck/apr/fbk/insertCnrsSpceSld.do")
	public ModelAndView insertCnrsSpceSld(
			@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
			@RequestParam(value="mst_id") String mst_id,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		fieldBookVO.setId(mst_id);
		fieldBookVO.setMst_create_user(loginVO.getName());
		JSONObject results = null;
		
		try {
			List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
			if(list.size() > 0) {
				results = fckAprFieldBookService.insertStripLand(fieldBookVO,mFile);
			}
			mv.addObject("status","success");
			if(results != null) {
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
			} else {
				mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
			}
		} catch (SecurityException e) {
			LOGGER.error(e.getMessage());
			fckAprFieldBookService.deleteCnrsSpce(commandMap);
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			fckAprFieldBookService.deleteCnrsSpce(commandMap);
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
	@RequestMapping(value = "/sys/fck/apr/fbk/updateFieldBookView.do")
    public String updateFieldBookView(
    		@ModelAttribute("searchVO") FckAprFieldBookItemVO searchVO,
    		@RequestParam(value="id") String id, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 상세정보조회 */
		FckAprFieldBookVO result  = fckAprFieldBookService.selectFckAprFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		searchVO.setMST_ID(id);
		
		/* 공유방 조사데이터 리스트조회 */
		List<FckAprStripLandVO> resultList = fckAprFieldBookService.selectFckAprFieldBookItemView(searchVO);
		model.addAttribute("resultList", resultList);
				
		return "sys/fck/apr/fbk/fieldBookUpdt";
	}
	
	/**
	 * @param label
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 공유방 조사데이터 수정
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/updateCnrsSpceItem.do")
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
				commandMap.put("id", mst_id);
				fckAprFieldBookService.deleteCnrsSpceItem(commandMap);
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
	 * @param id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 공유방 삭제
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/deleteCnrsSpce.do")
	public ModelAndView deleteCnrsSpce(@RequestParam(value="id") String id, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			fckAprFieldBookService.deleteCnrsSpce(commandMap);
			fckAprFieldBookService.deleteCnrsSpceAllItem(commandMap);
			fckAprFieldBookService.deleteCnrsSpceComptItem(commandMap);
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
	@RequestMapping(value = "/sys/fck/apr/fbk/selectFieldBookDetailOne.do")
    public String selectFieldBookDetailOne(
    		@RequestParam(value="smid") String smid, Model model,  HttpServletRequest req,
    		@ModelAttribute("searchItemVO") FckAprFieldBookItemVO searchItemVO,
    		@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO
    		) throws Exception {
		
		String id = req.getParameter("mstId");
		
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		commandMap.put("smid", Integer.parseInt(smid));
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		model.addAttribute("mstId", id);
		
		FckAprFieldBookItemVO result = fckAprFieldBookService.selectFieldBookDetailOne(commandMap);
		
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
		String access = fckAprFieldBookService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result",result);
		model.addAttribute("access",access);
		
		return "sys/fck/apr/fbk/fieldBookDetailOne";
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정페이지 이동
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/updateFieldBookViewOne.do")
	public String updateFieldBookViewOne(
			@ModelAttribute("searchVO") FckAprFieldBookItemVO searchVO, 
			@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
			@RequestParam(value="smid") String smid, Model model, HttpServletRequest req) throws Exception {
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("smid", Integer.parseInt(smid));
		String id = req.getParameter("mstId");
		model.addAttribute("mstId", id);
		
		FckAprFieldBookItemVO result = fckAprFieldBookService.selectFieldBookDetailOne(commandMap);
		model.addAttribute("result",result);
		
		// 도분초 자르기
		String svyLatLon = result.getSvyLat()+" "+result.getSvyLon();
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(svyLatLon);

		DmsFormalization dmsformal = new DmsFormalization();
		
		while (matcher.find()) {
		    String LatD = matcher.group(1);
		    String LatM = matcher.group(2);
		    String LatS = matcher.group(3);
		    
		    String LonD = matcher.group(5);
		    String LonM = matcher.group(6);
		    String LonS = matcher.group(7);
		    
		    if(LatD != null && !LatD.equals("")) {
		    	dmsformal.setDmsLatDeg(LatD);
		    	result.setSvyLatD(dmsformal.getDmsLatDeg());
		    }
		    if(LatM != null && !LatM.equals("")) {
		    	dmsformal.setDmsLatMin(LatM);
		    	result.setSvyLatM(dmsformal.getDmsLatMin());
		    }
		    if(LatS != null && !LatS.equals("")) {
		    	dmsformal.setDmsLatSec(LatS);
		    	result.setSvyLatS(dmsformal.getDmsLatSec());
		    }
		    if(LonD != null && !LonD.equals("")) {
		    	dmsformal.setDmsLonDeg(LonD);
		    	result.setSvyLonD(dmsformal.getDmsLonDeg());
		    }
		    if(LonM != null && !LonM.equals("")) {
		    	dmsformal.setDmsLonMin(LonM);
		    	result.setSvyLonM(dmsformal.getDmsLonMin());
		    }
		    if(LonS != null && !LonS.equals("")) {
		    	dmsformal.setDmsLonSec(LonS);
		    	result.setSvyLonS(dmsformal.getDmsLonSec());
		    }
		}
		
		model.addAttribute("result", result);
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		comVO.setCodeId("FEI016");
		List<CmmnDetailCode> aprList_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("aprList_result", aprList_result);//사방댐 외관점검 종류
		
		comVO.setCodeId("FEI060");
		List<CmmnDetailCode> knd_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("ecnrKndCodeList", knd_result);//사방댐 외관점검 조사유형
		
		comVO.setCodeId("FEI017");
		List<CmmnDetailCode> orginlDam_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("orginlDamCodeList", orginlDam_result);//본댐 측정값 코드목록
		
		comVO.setCodeId("FEI023");
		List<CmmnDetailCode> sideWall_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("sideWallCodeList", sideWall_result);//측벽 측정값 코드목록
		
		comVO.setCodeId("FEI018");
		List<CmmnDetailCode> jdgVal_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("jdgValCodeList", jdgVal_result);//본댐 판정값 코드목록
		
		comVO.setCodeId("FEI044");
		List<CmmnDetailCode> dwnspt_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("dwnSptCodeList", dwnspt_result);//물받이 측정값 코드목록
		
		comVO.setCodeId("FEI019");
		List<CmmnDetailCode> snddpsitJdgVal_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("snddpsitJdgValCodeList", snddpsitJdgVal_result);//저사상태 판정값
		
		comVO.setCodeId("FEI029");
		List<CmmnDetailCode> mooringMainfclt_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("mooringMainfcltCodeList", mooringMainfclt_result);//계류보전 주요시설  코드목록
		
		comVO.setCodeId("FEI039");
		List<?> fckRslt_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("fckRsltCodeList", fckRslt_result);//최종점검결과 조회
		
		comVO.setCodeId("FEI021");
		List<CmmnDetailCode> mngmtr_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("mngmtrCodeList", mngmtr_result);//조치사항 코드목록
		
		comVO.setCodeId("FEI022");
		List<CmmnDetailCode> appnRelis_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("appnRelisCodeList", appnRelis_result);//지정해제 코드목록
		
		comVO.setCodeId("FEI024");
		List<CmmnDetailCode> reinfc_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("reinfcCodeList", reinfc_result);//보강시설 코드목록
		
		comVO.setCodeId("FEI025");
		List<CmmnDetailCode> prtc_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("prtcCodeList", prtc_result);//보호시설 코드목록
		
		comVO.setCodeId("FEI026");
		List<CmmnDetailCode> drng_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("drngCodeList", drng_result);//배수시설 코드목록
		
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
		
		return "sys/fck/apr/fbk/fieldBookUpdtOne";
	}
	
	/**
	 * @param searchVO
	 * @param id 
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/updateFieldBookDetailOne.do")
	public ModelAndView updateFieldBookDetailOne(@ModelAttribute("searchVO") FckAprFieldBookItemVO searchVO, ModelMap model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			
			String svyYearStr = "";
			if(!searchVO.getCreateDt().isEmpty()) {
				String svyDt = searchVO.getCreateDt();
				String[] svyDtArr = svyDt.split("-");
				svyYearStr = svyDtArr[0];
			}
			String svy_attr = "["
					+ "{\"VALUE\":\""+searchVO.getSvyId()+"\",\"NAME\":\"조사ID\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyType()+"\",\"NAME\":\"조사유형\"},"
					+ "{\"VALUE\":\""+svyYearStr+"\",\"NAME\":\"조사연도\"},"
					+ "{\"VALUE\":\""+searchVO.getSvySd()+"\",\"NAME\":\"시도\"},"
					+ "{\"VALUE\":\""+searchVO.getSvySgg()+"\",\"NAME\":\"시군구\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyEmd()+"\",\"NAME\":\"읍면동\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyRi()+"\",\"NAME\":\"리\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyJibun()+"\",\"NAME\":\"JIBUN\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyLatD()+"° "+searchVO.getSvyLatM()+"'' "+searchVO.getSvyLatS()+"\\\""+"\",\"NAME\":\"위도\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyLonD()+"° "+searchVO.getSvyLonM()+"'' "+searchVO.getSvyLonS()+"\\\""+"\",\"NAME\":\"경도\"}"
					+ "]";
			
			searchVO.setATTR(svy_attr);
			
			
			String bpy = req.getParameter("bpy");
			String bpx = req.getParameter("bpx");
			
			HashMap<String, Object> projMap = new HashMap<>();
			projMap.put("bpx",bpx);
			projMap.put("bpy",bpy);
			
			// 4326 to 5186
			List<EgovMap> projList = fckAprFieldBookService.selectFckAprProjBessel(projMap);
			
			if(!projList.get(0).isEmpty()) {
				if(projList.get(0).containsKey("bpx")) {
					searchVO.setSvyLon(projList.get(0).get("bpx").toString());
				}
				if(projList.get(0).containsKey("bpy")) {
					searchVO.setSvyLat(projList.get(0).get("bpy").toString());
				}
			}
			
			fckAprFieldBookService.updateFieldBookDetailOne(searchVO);
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
	@RequestMapping(value = "/sys/fck/apr/fbk/deleteFieldBookDetailOne.do")
	public ModelAndView deleteFieldBookDetailOne(@ModelAttribute("searchVO") FckAprFieldBookItemVO searchVO, ModelMap model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("smid", searchVO.getSmid());
			
			fckAprFieldBookService.deleteFieldBookDetailOne(commandMap);
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
	@RequestMapping(value = "/sys/fck/apr/fbk/selectMberList.do")
	public ModelAndView selectMberList(
			  @RequestParam(value="searchKeyword") String searchKeyword,
			  @RequestParam(value="searchCondition") String searchCondition,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			FckAprFieldBookVO vo = new FckAprFieldBookVO();
			vo.setSearchKeyword(searchKeyword);
			vo.setSearchCondition(searchCondition);
			
			List<EgovMap> list = fckAprFieldBookService.selectMberList(vo);
			
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
	@RequestMapping(value = "/sys/fck/apr/fbk/selectFieldbookAuthoryView.do")
    public String selectFieldbookAuthoryView(
    		@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
    		@ModelAttribute("searchItemVO") FckAprFieldBookItemVO searchItemVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			return "redirect:/login.do";
		} else {
			model.addAttribute("userId",user.getName());
			
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", Integer.parseInt(id));
			
			searchItemVO.setMST_ID(id);
			/* 공유방 권한자 조회 */
			List<FckAprFieldBookVO> result = fckAprFieldBookService.selectCnrsAuthorList(commandMap);
			
			model.addAttribute("resultList", result);
			return "sys/fck/apr/fbk/fieldBookAuthorManage";
		}
	}
	
	/**
	 * 부서별 회원목록 트리 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/fbk/selectAuthorUserPopup.do")
	public String selectAuthorUserPopup(FckAprFieldBookVO vo,ModelMap model) throws Exception{
		
		List<String> authorEsntlIdList = Arrays.asList(vo.getAuthorEsntlIdList());
		
		List<EgovMap> deptList = fckAprFieldBookService.selectDeptInfoList();
		List<EgovMap> deptMberList = fckAprFieldBookService.selectDeptCreatList();
		
		model.addAttribute("dept_list",deptList);
		model.addAttribute("dept_mber_list", deptMberList);
		model.addAttribute("authorEsntlIdList", authorEsntlIdList);
		
		
		return "sys/fck/apr/fbk/searchAuthorUserPopup";
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
	@RequestMapping(value = "/sys/fck/apr/fbk/updateCnrsSpceAuthorMgt.do")
	public ModelAndView updateCnrsSpceAuthorMgt(
			@ModelAttribute("fieldBookVO") FckAprFieldBookVO fieldBookVO,
			@RequestParam(value="mstId") String id,
    		HttpServletRequest req,
    		BindingResult bindingResult,
			ModelMap model) throws Exception {
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		fieldBookVO.setId(id);
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			fckAprFieldBookService.updateCnrsSpceAuthorMgt(fieldBookVO);
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
