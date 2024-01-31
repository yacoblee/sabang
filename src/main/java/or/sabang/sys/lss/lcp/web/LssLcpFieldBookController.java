package or.sabang.sys.lss.lcp.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookItemVO;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookService;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class LssLcpFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "lssLcpFieldBookService") 	
	private LssLcpFieldBookService lssLcpFieldBookService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssLcpFieldBookController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectFieldBookList.do")
	public String selectFieldBookList(@ModelAttribute("searchVO") LssLcpFieldBookVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectFieldBookList controller");
		
		List<LssLcpFieldBookVO> list = null;
		
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
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(lssLcpFieldBookService.selectLssLcpFieldBookMaxYear());
		}
		
		//회사명 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		//연도코드 조회
		List<?> year_result = lssLcpFieldBookService.selectLssLcpFieldBookYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = lssLcpFieldBookService.selectLssLcpFieldBookList(searchVO);
			cnt_total = lssLcpFieldBookService.selectLssLcpFieldBookListTotCnt(searchVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList", list);
		
		return "sys/lss/lcp/fbk/fieldBookList";
	}

	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 상세조회
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectFieldBookDetail.do")
    public String selectFieldBookDetail(
    		@ModelAttribute("searchVO") LssLcpFieldBookItemVO searchVO,
    		@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", user.getUniqId());
		
		/* 공유방 상세정보조회 */
		LssLcpFieldBookVO result  = lssLcpFieldBookService.selectLssLcpFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		
		/* 공유방 권한 확인 */
		String access = lssLcpFieldBookService.selectAuthorCheck(commandMap);		
		model.addAttribute("access",access);
		
		searchVO.setMST_ID(id);
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList); 
		
		//시군구코드 조회
		if(searchVO.getSvySd() != null && !searchVO.getSvySd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSvySd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(searchVO.getSvySgg() != null && !searchVO.getSvySgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getSvyEmd() != null && !searchVO.getSvyEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}	
		
		/* 공유방 조사데이터 리스트조회 */
		List<LssLcpSvyStripLandVO> resultList = null;
		
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
		
		resultList = lssLcpFieldBookService.selectLssLcpFieldBookItemList(searchVO);
		
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
		
		int totCnt = lssLcpFieldBookService.selectLssLcpFieldBookItemListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
				
		return "sys/lss/lcp/fbk/fieldBookDetail";
	}
	
	/**
	 * @param mst_id
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 조사데이터 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/rgstFieldBookItemPopup.do")
	public String uploadRptLcpFilePopup(@RequestParam(value="mst_id") String mst_id,HttpServletRequest req, ModelMap model) throws Exception {
		model.addAttribute("mst_id", mst_id);
		return "sys/lss/lcp/fbk/rgstFieldBookItemPopup";
	}

	/**
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/insertCnrsSpcePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO,
			ModelMap model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);

		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
	
		if(adminVO.getSdCode() != null && !adminVO.getSdCode().trim().isEmpty()) {
		//시군구코드 조회
		adminVO.setSdCode(adminVO.getAdminCode());
		List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
		model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(adminVO.getEmdCode() != null && !adminVO.getEmdCode().trim().isEmpty()) {
		adminVO.setSggCode(adminVO.getAdminCode());
		List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
		model.addAttribute("emdCodeList",emdCodeList);
		}
		//조사대상지등록 조회
		List<LssLcpFieldBookVO> sldnm_result = lssLcpFieldBookService.selectLcpSldRegList();
		model.addAttribute("sldNmList", sldnm_result);
		
		List<?> orgcht_list = lssLcpFieldBookService.selectOrgchtList();
		model.addAttribute("orgchtList", orgcht_list);
		
		return "sys/lss/lcp/fbk/insertCnrsSpcePopup";
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
	@RequestMapping(value = "/sys/lss/lcp/fbk/insertCnrsSpce.do")
    public ModelAndView insertCnrsSpce(@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO, HttpServletRequest req, Model model,  BindingResult bindingResult) throws Exception {
		ModelAndView mv = null;
 		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
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
				int cnt = lssLcpFieldBookService.selectLssLcpFieldBookCheckMstName(mst_partname);
				if(mst_partname.isEmpty() != true && mst_password.isEmpty() != true) {
					if(cnt > 0) {
						mv.addObject("cnt", "fail");
						return mv;
					}else {
						mv.addObject("cnt", "success");
						fieldBookVO.setMst_admin_user(loginVO.getName());
						fieldBookVO.setMst_admin_id(loginVO.getUniqId());
						mstId = lssLcpFieldBookService.insertCnrsSpce(fieldBookVO);
						commandMap.put("id", mstId);
						fieldBookVO.setId(mstId);
						lssLcpFieldBookService.insertCnrsSpceAuthorMgt(fieldBookVO);
						results = lssLcpFieldBookService.insertStripLand(fieldBookVO);
						if(results != null) {
							String total = results.get("total").toString();
							String successCnt = results.get("successCnt").toString();
							JSONArray failedIds = (JSONArray)results.get("failedIds");
							String failedCnt = String.valueOf(failedIds.length());
							String message = "전체 "+total+"건 중 "+successCnt+"건이 등록되었습니다.\n총 : "+
								total+" 건\n등록 : "+successCnt+" 건\n실패 : "+failedCnt+" 건\n실패목록 : "+failedIds.join(",");
							mv.addObject("status","success");
				    		mv.addObject("message", message);
						}else {
							mv.addObject("status","success");
							mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
						}
					}
				}else {
					mv.addObject("nnt", "fail");
				}
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				lssLcpFieldBookService.deleteCnrsSpce(commandMap);
				lssLcpFieldBookService.deleteCnrsSpceComptItem(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				lssLcpFieldBookService.deleteCnrsSpce(commandMap);
				lssLcpFieldBookService.deleteCnrsSpceComptItem(commandMap);
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
	@RequestMapping(value = "/sys/lss/lcp/fbk/insertCnrsSpceSldPopup.do")
	public String insertCnrsSpceSldPopup(
			@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO,
			@RequestParam(value="mst_id") String mst_id,
			ModelMap model) throws Exception {
		
		model.addAttribute("mst_id",mst_id);
		
		//조사대상지등록 조회
		List<LssLcpFieldBookVO> sldnm_result = lssLcpFieldBookService.selectLcpSldRegList();
		model.addAttribute("sldNmList", sldnm_result);

		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
	
		if(adminVO.getSdCode() != null && !adminVO.getSdCode().trim().isEmpty()) {
		//시군구코드 조회
		adminVO.setSdCode(adminVO.getAdminCode());
		List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
		model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(adminVO.getEmdCode() != null && !adminVO.getEmdCode().trim().isEmpty()) {
		adminVO.setSggCode(adminVO.getAdminCode());
		List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
		model.addAttribute("emdCodeList",emdCodeList);
		}
		
		return "sys/lss/lcp/fbk/insertCnrsSpceSldPopup";
	}

	/**
	 * @param id
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 대상지 등록
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/insertCnrsSpceSld.do")
	public ModelAndView insertCnrsSpceSld(
			@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO,
			@RequestParam(value = "mst_id") String mstId,
			ModelMap model,
			HttpServletResponse res,
			BindingResult bindingResult) throws Exception {
		
		ModelAndView mv = null;
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//		LssLcpFieldBookItemVO fieldBookItemVO = new LssLcpFieldBookItemVO();
		
//		String sldValCnvr = EgovStringUtil.getHtmlStrCnvr(sldVal);
//		String gvfValCnvr = EgovStringUtil.getHtmlStrCnvr(gvfVal);
//		
//		if(sldVal.equals(",") && gvfVal.equals(",")) {
//			fieldBookItemVO.setATTR("");
//		}else {
//			if(sldVal.equals(",")) {
//				fieldBookItemVO.setATTR(gvfValCnvr);
//			}else if(gvfVal.equals(",")) {
//				fieldBookItemVO.setATTR(sldValCnvr);
//			}else {
//				fieldBookItemVO.setATTR(sldValCnvr.substring(0, sldValCnvr.length()-1)+","+gvfValCnvr.substring(1));
//			}
//		}
		
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			JSONObject results = null;
			
			try {
				fieldBookVO.setMst_admin_user(loginVO.getName());
				commandMap.put("id", mstId);
				fieldBookVO.setId(mstId);
				results = lssLcpFieldBookService.insertStripLand(fieldBookVO);
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
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				lssLcpFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				lssLcpFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
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
	@RequestMapping(value = "/sys/lss/lcp/fbk/updateFieldBookView.do")
    public String updateFieldBookView(
    		@ModelAttribute("searchVO") LssLcpFieldBookItemVO searchVO,
    		@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 상세정보조회 */
		LssLcpFieldBookVO result  = lssLcpFieldBookService.selectLssLcpFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		searchVO.setMST_ID(id);
		
		/* 공유방 조사데이터 리스트조회 */
		List<LssLcpSvyStripLandVO> resultList = lssLcpFieldBookService.selectLssLcpFieldBookItemView(searchVO);
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
		model.addAttribute("resultList", resultList);
				
		return "sys/lss/lcp/fbk/fieldBookUpdt";
	}

	/**
	 * @param label
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 공유방 조사데이터 수정
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/updateCnrsSpceItem.do")
	public ModelAndView updateCnrsSpce(
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="smid[]") List<String> smid,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		//JSONObject results = null;
		try {
			for(int i=0; i<smid.size(); i++) {
				HashMap<String, Object> commandMap = new HashMap<>();
				commandMap.put("label", smid.get(i));
				commandMap.put("mst_id", mst_id);
				lssLcpFieldBookService.deleteCnrsSpceItem(commandMap);
//				String query = "smid = ".concat(smid.get(i));
//				results = superMapUtils.deleteRecordSet("tf_feis_lcp_fieldinfo", query);
				mv.addObject("status","success");
	    		mv.addObject("message",egovMessageSource.getMessage("success.common.update"));
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","failed");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}		
		return mv;
	}
	
	@RequestMapping(value = "/sys/lss/lcp/fbk/deleteCnrsSpce.do")
	public ModelAndView deleteCnrsSpce(@RequestParam(value="mstId") String id, ModelMap model) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		//JSONObject results = null;
		try {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			lssLcpFieldBookService.deleteCnrsSpce(commandMap);//공유방 삭제
			
			//String query = "mst_id = ".concat(id);
			//results = superMapUtils.deleteRecordSet("tf_feis_lcp_fieldinfo", query);//공유방정보삭제
			//220314 공유방 삭제시 조사완료 데이터 삭제X
			lssLcpFieldBookService.deleteCnrsSpceComptItem(commandMap);
			mv.addObject("status","success");
    		mv.addObject("message",egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","failed");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * @param gvfSd
	 * @param gvfSgg
	 * @param gvfEmd
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @description 조사대상지테이블 정보 조회
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectLcpSldListAddr.do")
	public ModelAndView selectLcpSldListAddr(
			  @RequestParam(value="sldId") String sldId,
			  @RequestParam(value="sldSd") String sldSd,
			  @RequestParam(value="sldSgg") String sldSgg,
			  @RequestParam(value="sldEmd") String sldEmd,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			LssLcpFieldBookVO vo = new LssLcpFieldBookVO();
			vo.setSldId(sldId);
			vo.setSldSd(sldSd);
			vo.setSldSgg(sldSgg);
			vo.setSldEmd(sldEmd);
			List<EgovMap> list = lssLcpFieldBookService.selectLcpSldListAddr(vo);
			
			mv.addObject("cnt",list.get(0).get("cnt"));
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
	 * @param gvfSd
	 * @param gvfSgg
	 * @param gvfEmd
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @description 제보테이블 정보 조회
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectLcpGvfListAddr.do")
	public ModelAndView selectLcpGvfListAddr(
			  @RequestParam(value="gvfSd") String gvfSd,
			  @RequestParam(value="gvfSgg") String gvfSgg,
			  @RequestParam(value="gvfEmd") String gvfEmd,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			LssLcpFieldBookVO vo = new LssLcpFieldBookVO();
			vo.setGvfSd(gvfSd);
			vo.setGvfSgg(gvfSgg);
			vo.setGvfEmd(gvfEmd);
			List<EgovMap> list = lssLcpFieldBookService.selectLcpGvfListAddr(vo);

			mv.addObject("cnt",list.get(0).get("cnt"));
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
	 * 사용자 조회
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectMberList.do")
	public ModelAndView selectMberList(
			  @RequestParam(value="searchKeyword") String searchKeyword,
			  @RequestParam(value="searchCondition") String searchCondition,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			LssLcpFieldBookVO vo = new LssLcpFieldBookVO();
			vo.setSearchKeyword(searchKeyword);
			vo.setSearchCondition(searchCondition);
			
			List<EgovMap> list = lssLcpFieldBookService.selectMberList(vo);
			
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
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectFieldbookAuthoryView.do")
    public String selectFieldbookAuthoryView(
    		@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 권한자 조회 */
		List<LssLcpFieldBookVO> result = lssLcpFieldBookService.selectCnrsAuthorList(commandMap);
		
		model.addAttribute("resultList", result);
		model.addAttribute("mstId", id);
		return "sys/lss/lcp/fbk/fieldBookAuthorManage";
	}
	
	/**
	 * 부서별 회원목록 트리 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/fbk/selectAuthorUserPopup.do")
	public String selectAuthorUserPopup(LssLcpFieldBookVO vo,ModelMap model) throws Exception{
		
		List<String> authorEsntlIdList = Arrays.asList(vo.getAuthorEsntlIdList());
		
		List<EgovMap> deptList = lssLcpFieldBookService.selectDeptInfoList();
		List<EgovMap> deptMberList = lssLcpFieldBookService.selectDeptCreatList();
		
		model.addAttribute("dept_list",deptList);
		model.addAttribute("dept_mber_list", deptMberList);
		model.addAttribute("authorEsntlIdList", authorEsntlIdList);
		
		
		return "sys/lss/lcp/fbk/searchAuthorUserPopup";
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
	@RequestMapping(value = "/sys/lss/lcp/fbk/updateCnrsSpceAuthorMgt.do")
	public ModelAndView updateCnrsSpceAuthorMgt(
			@ModelAttribute("fieldBookVO") LssLcpFieldBookVO fieldBookVO, 
    		HttpServletRequest req,
    		BindingResult bindingResult,
			ModelMap model) throws Exception {
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			String id = req.getParameter("mstId");
			fieldBookVO.setId(id);
			
			lssLcpFieldBookService.updateCnrsSpceAuthorMgt(fieldBookVO);
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