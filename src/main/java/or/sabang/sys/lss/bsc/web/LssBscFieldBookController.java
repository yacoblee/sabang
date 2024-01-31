package or.sabang.sys.lss.bsc.web;

import java.io.File;
import java.io.PrintWriter;
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
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookItemVO;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookService;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookVO;
import or.sabang.sys.lss.bsc.service.LssBscStripLandVO;
import or.sabang.utl.DmsFormalization;
import oracle.net.aso.p;

@Controller
public class LssBscFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "lssBscFieldBookService") 	
	private LssBscFieldBookService lssBscFieldBookService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
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
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssBscFieldBookController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@RequestMapping(value = "/sys/lss/bsc/fbk/selectFieldBookList.do")
	public String selectFieldBookList(@ModelAttribute("searchVO") LssBscFieldBookVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectFieldBookList controller");
		

		List<LssBscFieldBookVO> list = null;
		
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
			searchVO.setSvy_year(lssBscFieldBookService.selectLssBscFieldBookMaxYear());
		}
		
		//회사명 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		//연도코드 조회
		List<?> year_result = lssBscFieldBookService.selectLssBscFieldBookYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = lssBscFieldBookService.selectLssBscFieldBookList(searchVO);
			cnt_total = lssBscFieldBookService.selectLssBscFieldBookListTotCnt(searchVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList", list);
		
		return "sys/lss/bsc/fbk/fieldBookList";
	}

	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 상세조회
	 */
	@RequestMapping(value = "/sys/lss/bsc/fbk/selectFieldBookDetail.do")
    public String selectFieldBookDetail(
    		@ModelAttribute("searchVO") LssBscFieldBookItemVO searchVO,
    		@ModelAttribute("fieldBookVO") LssBscFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, ModelMap model, HttpServletRequest request) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 상세정보조회 */
		LssBscFieldBookVO result  = lssBscFieldBookService.selectLssBscFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		
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
		List<LssBscStripLandVO> resultList = null;
		
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
		
		resultList = lssBscFieldBookService.selectLssBscFieldBookItemList(searchVO);
		
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
		
		int totCnt = lssBscFieldBookService.selectLssBscFieldBookItemListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		
		
				
		return "sys/lss/bsc/fbk/fieldBookDetail";
	}
	
	/**
	 * @param mst_id
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 조사데이터 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/bsc/fbk/rgstFieldBookItemPopup.do")
	public String uploadRptBscFilePopup(@RequestParam(value="mst_id") String mst_id,HttpServletRequest req, ModelMap model) throws Exception {
		model.addAttribute("mst_id", mst_id);
		return "sys/lss/bsc/fbk/rgstFieldBookItemPopup";
	}

	/**
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/bsc/fbk/insertCnrsSpcePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("fieldBookVO") LssBscFieldBookVO fieldBookVO,
			ModelMap model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);

		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		return "sys/lss/bsc/fbk/insertCnrsSpcePopup";
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
	@RequestMapping(value = "/sys/lss/bsc/fbk/insertCnrsSpce.do")
    public ModelAndView insertCnrsSpce(
    		@ModelAttribute("fieldBookVO") LssBscFieldBookVO fieldBookVO, 
    		@RequestParam(value = "file") MultipartFile mFile,
    		Model model, 
    		BindingResult bindingResult) throws Exception {
		// 추후 수정
		ModelAndView mv = null;
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
				String mst_adminpwd = fieldBookVO.getMst_adminpwd();
				String mst_readpwd = fieldBookVO.getMst_readpwd();
				int cnt = lssBscFieldBookService.selectLssBscFieldBookCheckMstName(mst_partname);
				if(mst_partname.isEmpty() != true && mst_password.isEmpty() != true && mst_adminpwd.isEmpty() !=true && mst_readpwd.isEmpty() !=true) {
					if(cnt > 0) {
						mv.addObject("cnt", "fail");
						return mv;
					}else {
						mv.addObject("cnt", "success");
						List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir+"bsc"+File.separator, maxFileSize, extWhiteList);
						if (list.size() > 0) {
							fieldBookVO.setMst_admin_user(loginVO.getName());
							mstId = lssBscFieldBookService.insertCnrsSpce(fieldBookVO);
							commandMap.put("id", mstId);
							fieldBookVO.setId(mstId);
							results = lssBscFieldBookService.insertStripLand(fieldBookVO,mFile);
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
				lssBscFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				lssBscFieldBookService.deleteCnrsSpce(commandMap);
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
	@RequestMapping(value = "/sys/lss/bsc/fbk/insertCnrsSpceSldPopup.do")
	public String insertCnrsSpceSldPopup(
			@RequestParam(value="mst_id") String mst_id,
			ModelMap model) throws Exception {

		model.addAttribute("mst_id",mst_id);
		
		return "sys/lss/bsc/fbk/insertCnrsSpceSldPopup";
	}

	/**
	 * @param id
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 대상지 등록
	 */
	@RequestMapping(value = "/sys/lss/bsc/fbk/insertCnrsSpceSld.do")
	public ModelAndView insertCnrsSpceSld(
			@ModelAttribute("fieldBookVO") LssBscFieldBookVO fieldBookVO,
			@RequestParam(value="mst_id") String mst_id,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			HashMap<String, Object> commandMap = new HashMap<>();
			fieldBookVO.setId(mst_id);
			fieldBookVO.setMst_admin_user(loginVO.getName());
			JSONObject results = lssBscFieldBookService.insertStripLand(fieldBookVO,mFile);
			
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
	@RequestMapping(value = "/sys/lss/bsc/fbk/updateFieldBookView.do")
    public String updateFieldBookView(
    		@ModelAttribute("searchVO") LssBscFieldBookItemVO searchVO,
    		@ModelAttribute("fieldBookVO") LssBscFieldBookVO fieldBookVO,
    		@RequestParam(value="mstId") String id, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 상세정보조회 */
		LssBscFieldBookVO result  = lssBscFieldBookService.selectLssBscFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		searchVO.setMST_ID(id);
		
		/* 공유방 조사데이터 리스트조회 */
		List<LssBscStripLandVO> resultList = lssBscFieldBookService.selectLssBscFieldBookItemView(searchVO);
		
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
				
		return "sys/lss/bsc/fbk/fieldBookUpdt";
	}

	/**
	 * @param label
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 공유방 조사데이터 수정
	 */
	@RequestMapping(value = "/sys/lss/bsc/fbk/updateCnrsSpceItem.do")
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
				lssBscFieldBookService.deleteCnrsSpceItem(commandMap);
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
	
	@RequestMapping(value = "/sys/lss/bsc/fbk/deleteCnrsSpce.do")
	public ModelAndView deleteCnrsSpce(@RequestParam(value="mstId") String id, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			lssBscFieldBookService.deleteCnrsSpce(commandMap);
			lssBscFieldBookService.deleteCnrsSpceAllItem(commandMap);
			//220314 공유방 삭제시 조사완료 데이터 삭제X  
			lssBscFieldBookService.deleteCnrsSpceComptItem(commandMap);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
}
