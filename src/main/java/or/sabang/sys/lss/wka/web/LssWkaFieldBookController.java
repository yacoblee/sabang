package or.sabang.sys.lss.wka.web;

import java.io.File;
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
import or.sabang.sys.lss.wka.service.LssWkaFieldBookItemVO;
import or.sabang.sys.lss.wka.service.LssWkaFieldBookService;
import or.sabang.sys.lss.wka.service.LssWkaFieldBookVO;
import or.sabang.sys.lss.wka.service.LssWkaStripLandItemVO;
import or.sabang.sys.lss.wka.service.LssWkaStripLandVO;
import or.sabang.utl.DmsFormalization;
import oracle.sql.ARRAY;

@Controller
public class LssWkaFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "lssWkaFieldBookService") 	
	private LssWkaFieldBookService lssWkaFieldBookService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.stripland");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssWkaFieldBookController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/selectFieldBookList.do")
	public String selectFieldBookList(@ModelAttribute("searchVO") LssWkaFieldBookVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectFieldBookList controller");
		

		List<LssWkaFieldBookVO> list = null;
		
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
			searchVO.setSvy_year(lssWkaFieldBookService.selectLssWkaFieldBookMaxYear());
		}
		
		//회사명 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		//연도코드 조회
		List<?> year_result = lssWkaFieldBookService.selectLssWkaFieldBookYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = lssWkaFieldBookService.selectLssWkaFieldBookList(searchVO);
			cnt_total = lssWkaFieldBookService.selectLssWkaFieldBookListTotCnt(searchVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList", list);
		
		return "sys/lss/wka/fbk/fieldBookList";
	}

	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 상세조회
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/selectFieldBookDetail.do")
    public String selectFieldBookDetail(
    		@ModelAttribute("searchVO") LssWkaFieldBookItemVO searchVO, HttpServletRequest req,
    		@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO,
    		Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		String id = req.getParameter("mstId");
		
		model.addAttribute("mstId", id);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", user.getUniqId());
		
		/* 공유방 상세정보조회 */
		LssWkaFieldBookVO result  = lssWkaFieldBookService.selectLssWkaFieldBookDetail(commandMap);

		/* 공유방 권한 확인 */
		String access = lssWkaFieldBookService.selectAuthorCheck(commandMap);
		
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
		List<LssWkaStripLandVO> resultList = null;
		
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
		
		resultList = lssWkaFieldBookService.selectLssWkaFieldBookItemList(searchVO);
		
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
		int totCnt = lssWkaFieldBookService.selectLssWkaFieldBookItemListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
				
		return "sys/lss/wka/fbk/fieldBookDetail";
	}
	
	/**
	 * @param mst_id
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 조사데이터 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/rgstFieldBookItemPopup.do")
	public String uploadRptWkaFilePopup(@RequestParam(value="mst_id") String mst_id,HttpServletRequest req, ModelMap model) throws Exception {
		model.addAttribute("mst_id", mst_id);
		return "sys/lss/wka/fbk/rgstFieldBookItemPopup";
	}

	/**
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/insertCnrsSpcePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO,
			ModelMap model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		 
		List<?> orgcht_list = lssWkaFieldBookService.selectOrgchtList();
		model.addAttribute("orgchtList", orgcht_list);
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		return "sys/lss/wka/fbk/insertCnrsSpcePopup";
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
	@RequestMapping(value = "/sys/lss/wka/fbk/insertCnrsSpce.do")
    public ModelAndView insertCnrsSpce(
    		@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO, 
    		@RequestParam(value = "file") MultipartFile mFile,
    		HttpServletRequest req,
    		Model model, 
    		BindingResult bindingResult) throws Exception {
		// 추후 수정
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
				int cnt = lssWkaFieldBookService.selectLssWkaFieldBookCheckMstName(mst_partname);
				if(mst_partname.isEmpty() != true && mst_password.isEmpty() != true) {
					if(cnt > 0) {
						mv.addObject("cnt", "fail");
						return mv;
					}else {
						mv.addObject("cnt", "success");
						List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir+"wka"+File.separator, maxFileSize, extWhiteList);
						if (list.size() > 0) {
							fieldBookVO.setMst_admin_user(loginVO.getName());
							fieldBookVO.setMst_admin_id(loginVO.getUniqId());
							mstId = lssWkaFieldBookService.insertCnrsSpce(fieldBookVO);
							commandMap.put("id", mstId);
							fieldBookVO.setId(mstId);
							lssWkaFieldBookService.insertCnrsSpceAuthorMgt(fieldBookVO);
							results = lssWkaFieldBookService.insertStripLand(fieldBookVO,mFile);
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
				lssWkaFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				lssWkaFieldBookService.deleteCnrsSpce(commandMap);
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
	@RequestMapping(value = "/sys/lss/wka/fbk/insertCnrsSpceSldPopup.do")
	public String insertCnrsSpceSldPopup(
			@RequestParam(value="mst_id") String mst_id,
			ModelMap model) throws Exception {
		
		//List<LssWkaStripLandVO> list = null;
		
		//list = lssWkaFieldBookService.selectCnrsSpceSldList(stripLandVO);
		//model.addAttribute("resultList", list);
		model.addAttribute("mst_id",mst_id);
		
		return "sys/lss/wka/fbk/insertCnrsSpceSldPopup";
	}

	/**
	 * @param id
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 대상지 등록
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/insertCnrsSpceSld.do")
	public ModelAndView insertCnrsSpceSld(
			@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO,
			@RequestParam(value="mst_id") String mst_id,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		fieldBookVO.setId(mst_id);
		JSONObject results = lssWkaFieldBookService.insertStripLand(fieldBookVO,mFile);
		
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
	@RequestMapping(value = "/sys/lss/wka/fbk/updateFieldBookView.do")
    public String updateFieldBookView(
    		@ModelAttribute("searchVO") LssWkaFieldBookItemVO searchVO,
    		@RequestParam(value="id") String id, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 상세정보조회 */
		LssWkaFieldBookVO result  = lssWkaFieldBookService.selectLssWkaFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		searchVO.setMst_id(id);
		
		/* 공유방 조사데이터 리스트조회 */
		List<LssWkaStripLandVO> resultList = lssWkaFieldBookService.selectLssWkaFieldBookItemView(searchVO);
		model.addAttribute("resultList", resultList);
				
		return "sys/lss/wka/fbk/fieldBookUpdt";
	}

	/**
	 * @param mst_id
	 * @param smid
	 * @param model
	 * @param res
	 * @return
	 * @throws Exception
	 * @description 공유방 대상지 등록데이터 수정
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/updateCnrsSpceItem.do")
	public ModelAndView updateCnrsSpce(
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="smid[]") List<String> smid,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		//JSONObject results = new JSONObject();
		try {
			for(int i=0; i<smid.size(); i++) {
				HashMap<String, Object> commandMap = new HashMap<>();
				commandMap.put("label", smid.get(i));
				commandMap.put("mst_id", mst_id);
				lssWkaFieldBookService.deleteCnrsSpceItem(commandMap);
//				String query = "mst_id = ".concat(mst_id).concat(" and svy_id = '").concat(smid.get(i).toString()+"'");
//				String query = "svy_id = ".concat(smid.get(i).toString());
//				results = superMapUtils.deleteRecordSet("tf_feis_wka_svy_pt", query);
				mv.addObject("status","success");
	    		mv.addObject("message",egovMessageSource.getMessage("success.common.update"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			mv.addObject("status","failed");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}		
		return mv;
	}
	
	/**
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 삭제
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/deleteCnrsSpce.do")
	public ModelAndView deleteCnrsSpce(@RequestParam(value="id") String id, ModelMap model) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		JSONObject results = new JSONObject();
		try {
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			lssWkaFieldBookService.deleteCnrsSpce(commandMap);//공유방 삭제
			lssWkaFieldBookService.deleteCnrsSpceAllItem(commandMap);
//			String query = "mst_id = ".concat(id);
//			results = superMapUtils.deleteRecordSet("tf_feis_wka_svy_pt", query);//공유방정보삭제
			mv.addObject("status","success");
			mv.addObject("message",egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param smid
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 상세조회
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/selectFieldBookDetailOne.do")
    public String selectFieldBookItemDetail(
    		@ModelAttribute("searchVO") LssWkaFieldBookItemVO searchVO, HttpServletRequest req,
    		@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO,
    		@RequestParam(value="smid") String smid, Model model) throws Exception {

		
		String id = req.getParameter("mstId");
		model.addAttribute("mstId", id);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		commandMap.put("smid", Integer.parseInt(smid));
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 대상지 상세정보조회 */
		LssWkaStripLandItemVO result  = lssWkaFieldBookService.selectFieldBookDetailOne(commandMap);	
		
		DmsFormalization dmsformal = new DmsFormalization();
		if(result.getSpLonDeg() != null && !result.getSpLonDeg().equals("")) {
			dmsformal.setDmsLonDeg(result.getSpLonDeg());
			result.setSpLonDeg(dmsformal.getDmsLonDeg());
		}
		if(result.getSpLonMin() != null && !result.getSpLonMin().equals("")) {
			dmsformal.setDmsLonMin(result.getSpLonMin());
			result.setSpLonMin(dmsformal.getDmsLonMin());
		}
		if(result.getSpLonSec() != null && !result.getSpLonSec().equals("")) {
			dmsformal.setDmsLonSec(result.getSpLonSec());
			result.setSpLonSec(dmsformal.getDmsLonSec());
		}
		if(result.getSpLatDeg() != null && !result.getSpLatDeg().equals("")) {
			dmsformal.setDmsLatDeg(result.getSpLatDeg());
			result.setSpLatDeg(dmsformal.getDmsLatDeg());
		}
		if(result.getSpLatMin() != null && !result.getSpLatMin().equals("")) {
			dmsformal.setDmsLatMin(result.getSpLatMin());
			result.setSpLatMin(dmsformal.getDmsLatMin());
		}
		if(result.getSpLatSec() != null && !result.getSpLatSec().equals("")) {
			dmsformal.setDmsLatSec(result.getSpLatSec());
			result.setSpLatSec(dmsformal.getDmsLatSec());
		}
		
		if(result.getEpLonDeg() != null && !result.getEpLonDeg().equals("")) {
			dmsformal.setDmsLonDeg(result.getEpLonDeg());
			result.setEpLonDeg(dmsformal.getDmsLonDeg());
		}
		if(result.getEpLonMin() != null && !result.getEpLonMin().equals("")) {
			dmsformal.setDmsLonMin(result.getEpLonMin());
			result.setEpLonMin(dmsformal.getDmsLonMin());
		}
		if(result.getEpLonSec() != null && !result.getEpLonSec().equals("")) {
			dmsformal.setDmsLonSec(result.getEpLonSec());
			result.setEpLonSec(dmsformal.getDmsLonSec());
		}
		if(result.getEpLatDeg() != null && !result.getEpLatDeg().equals("")) {
			dmsformal.setDmsLatDeg(result.getEpLatDeg());
			result.setEpLatDeg(dmsformal.getDmsLatDeg());
		}
		if(result.getEpLatMin() != null && !result.getEpLatMin().equals("")) {
			dmsformal.setDmsLatMin(result.getEpLatMin());
			result.setEpLatMin(dmsformal.getDmsLatMin());
		}
		if(result.getEpLatSec() != null && !result.getEpLatSec().equals("")) {
			dmsformal.setDmsLatSec(result.getEpLatSec());
			result.setEpLatSec(dmsformal.getDmsLatSec());
		}
		
		/* 공유방 권한 확인 */
		String access = lssWkaFieldBookService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result",result);
		model.addAttribute("access",access);
				
		return "sys/lss/wka/fbk/fieldBookDetailOne";
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정페이지 이동
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/updateFieldBookViewOne.do")
    public String selectFieldBookItemUpdtView(
    		@ModelAttribute("searchVO") LssWkaFieldBookItemVO searchVO, HttpServletRequest req,
    		@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO, 
    		@RequestParam(value="smid") String smid, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("smid", Integer.parseInt(smid));
		
		String id = req.getParameter("mstId");
		model.addAttribute("mstId", id);
		
		/* 공유방 대상지 상세정보조회 */
		LssWkaStripLandItemVO result  = lssWkaFieldBookService.selectFieldBookDetailOne(commandMap);
		DmsFormalization dmsformal = new DmsFormalization();
		if(result.getSpLonDeg() != null && !result.getSpLonDeg().equals("")) {
			dmsformal.setDmsLonDeg(result.getSpLonDeg());
			result.setSpLonDeg(dmsformal.getDmsLonDeg());
		}
		if(result.getSpLonMin() != null && !result.getSpLonMin().equals("")) {
			dmsformal.setDmsLonMin(result.getSpLonMin());
			result.setSpLonMin(dmsformal.getDmsLonMin());
		}
		if(result.getSpLonSec() != null && !result.getSpLonSec().equals("")) {
			dmsformal.setDmsLonSec(result.getSpLonSec());
			result.setSpLonSec(dmsformal.getDmsLonSec());
		}
		if(result.getSpLatDeg() != null && !result.getSpLatDeg().equals("")) {
			dmsformal.setDmsLatDeg(result.getSpLatDeg());
			result.setSpLatDeg(dmsformal.getDmsLatDeg());
		}
		if(result.getSpLatMin() != null && !result.getSpLatMin().equals("")) {
			dmsformal.setDmsLatMin(result.getSpLatMin());
			result.setSpLatMin(dmsformal.getDmsLatMin());
		}
		if(result.getSpLatSec() != null && !result.getSpLatSec().equals("")) {
			dmsformal.setDmsLatSec(result.getSpLatSec());
			result.setSpLatSec(dmsformal.getDmsLatSec());
		}
		
		if(result.getEpLonDeg() != null && !result.getEpLonDeg().equals("")) {
			dmsformal.setDmsLonDeg(result.getEpLonDeg());
			result.setEpLonDeg(dmsformal.getDmsLonDeg());
		}
		if(result.getEpLonMin() != null && !result.getEpLonMin().equals("")) {
			dmsformal.setDmsLonMin(result.getEpLonMin());
			result.setEpLonMin(dmsformal.getDmsLonMin());
		}
		if(result.getEpLonSec() != null && !result.getEpLonSec().equals("")) {
			dmsformal.setDmsLonSec(result.getEpLonSec());
			result.setEpLonSec(dmsformal.getDmsLonSec());
		}
		if(result.getEpLatDeg() != null && !result.getEpLatDeg().equals("")) {
			dmsformal.setDmsLatDeg(result.getEpLatDeg());
			result.setEpLatDeg(dmsformal.getDmsLatDeg());
		}
		if(result.getEpLatMin() != null && !result.getEpLatMin().equals("")) {
			dmsformal.setDmsLatMin(result.getEpLatMin());
			result.setEpLatMin(dmsformal.getDmsLatMin());
		}
		if(result.getEpLatSec() != null && !result.getEpLatSec().equals("")) {
			dmsformal.setDmsLatSec(result.getEpLatSec());
			result.setEpLatSec(dmsformal.getDmsLatSec());
		}
		
		model.addAttribute("result",result);
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(result.getSvySd() != null && !result.getSvySd().trim().isEmpty()) {
			adminVO.setSdNm(result.getSvySd());
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}

		//읍면동코드 조회
		if(result.getSvySgg() != null && !result.getSvySgg().trim().isEmpty()) {
			adminVO.setSdNm(result.getSvySd());
			adminVO.setSggNm(result.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}

		//리코드 조회
		if(result.getSvyEmd() != null && !result.getSvyEmd().trim().isEmpty()) {
			adminVO.setSdNm(result.getSvySd());
			adminVO.setSggNm(result.getSvySgg());
			adminVO.setEmdNm(result.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		//산사태위험등급코드를 코드정보로부터 조회
		vo.setCodeId("FEI131");
		List<?> lnddgr_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("lndDgrCodeList", lnddgr_result);
		
		//토심코드를 코드정보로부터 조회
		vo.setCodeId("FEI118");
		List<?> soilDepth_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("soilDepthCodeList", soilDepth_result);
		
		//토성코드를 코드정보로부터 조회
		vo.setCodeId("FEI102");
		List<?> soilCls_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("soilClsCodeList", soilCls_result);
		
		//모암코드를 코드정보로부터 조회
		vo.setCodeId("FEI101");
		List<?> prntRock_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("prntRockCodeList", prntRock_result);
		
		return "sys/lss/wka/fbk/fieldBookUpdtOne";
	}
	
	/**
	 * @param searchVO
	 * @param id 
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/updateFieldBookDetailOne.do")
    public ModelAndView updateFieldBookItem(
    		@ModelAttribute("stripLandItemVO") LssWkaStripLandItemVO vo, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			lssWkaFieldBookService.updateFieldBookDetailOne(vo);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.update"));
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg").concat("\n"+e.getMessage()));
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
	@RequestMapping(value = "/sys/lss/wka/fbk/deleteFieldBookDetailOne.do")
	public ModelAndView deleteFieldBookItem(LssWkaStripLandItemVO vo,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			lssWkaFieldBookService.deleteFieldBookDetailOne(vo);
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
	@RequestMapping(value = "/sys/lss/wka/fbk/selectMberList.do")
	public ModelAndView selectMberList(
			  @RequestParam(value="searchKeyword") String searchKeyword,
			  @RequestParam(value="searchCondition") String searchCondition,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			LssWkaFieldBookVO vo = new LssWkaFieldBookVO();
			vo.setSearchKeyword(searchKeyword);
			vo.setSearchCondition(searchCondition);
			
			List<EgovMap> list = lssWkaFieldBookService.selectMberList(vo);
			
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
	@RequestMapping(value = "/sys/lss/wka/fbk/selectFieldbookAuthoryView.do")
    public String selectFieldbookAuthoryView(
    		@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 권한자 조회 */
		List<LssWkaFieldBookVO> result = lssWkaFieldBookService.selectCnrsAuthorList(commandMap);
		
		model.addAttribute("resultList", result);
		model.addAttribute("mstId", id);
		return "sys/lss/wka/fbk/fieldBookAuthorManage";
	}
	
	/**
	 * 부서별 회원목록 트리 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/fbk/selectAuthorUserPopup.do")
	public String selectAuthorUserPopup(LssWkaFieldBookVO vo,ModelMap model) throws Exception{
		
		List<String> authorEsntlIdList = Arrays.asList(vo.getAuthorEsntlIdList());
		
		List<EgovMap> deptList = lssWkaFieldBookService.selectDeptInfoList();
		List<EgovMap> deptMberList = lssWkaFieldBookService.selectDeptCreatList();
		
		model.addAttribute("dept_list",deptList);
		model.addAttribute("dept_mber_list", deptMberList);
		model.addAttribute("authorEsntlIdList", authorEsntlIdList);
		
		
		return "sys/lss/wka/fbk/searchAuthorUserPopup";
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
	@RequestMapping(value = "/sys/lss/wka/fbk/updateCnrsSpceAuthorMgt.do")
	public ModelAndView updateCnrsSpceAuthorMgt(
			@ModelAttribute("fieldBookVO") LssWkaFieldBookVO fieldBookVO, 
    		HttpServletRequest req,
    		BindingResult bindingResult,
			ModelMap model) throws Exception {
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			String id = req.getParameter("mstId");
			fieldBookVO.setId(id);
			
			lssWkaFieldBookService.updateCnrsSpceAuthorMgt(fieldBookVO);
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
