package or.sabang.sys.vyt.ecb.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.json.JSONArray;
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
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookService;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;
import or.sabang.sys.fck.apr.service.FckAprStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbSldManageService;
import or.sabang.sys.vyt.ecb.service.VytEcbStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbWorkVO;

@Controller
public class VytEcbSldManageController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "vytEcbSldManageService") 	
	private VytEcbSldManageService vytEcbSldManageService;
	
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
    
	private static final Logger LOGGER = LoggerFactory.getLogger(VytEcbSldManageController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 사업 목록조회
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/selectVytEcbWorkList.do")
	public String selectVytEcbWorkList(@ModelAttribute("searchVO") VytEcbWorkVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectVytEcbWorkList controller");
		
		List<VytEcbWorkVO> list = null;
		
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
		
		if(searchVO.getWorkYear() == null) {
			searchVO.setWorkYear(vytEcbSldManageService.selectVytEcbWorkMaxYear());
		}
		
		//사업종류 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI089");
		List<?> worktype_result = cmmUseService.selectCmmCodeDetail(vo);//cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("workTypeCodeList", worktype_result);
		
		//연도코드 조회
		List<?> year_result = vytEcbSldManageService.selectVytEcbWorkYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = vytEcbSldManageService.selectVytEcbWorkList(searchVO);
			cnt_total = vytEcbSldManageService.selectVytEcbWorkListTotCnt(searchVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
 		model.addAttribute("resultList", list);
		
		return "sys/vyt/ecb/sld/workManageList";
	}
	
	/**
	 * 사업 등록 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/insertVytEcbWorkPopup.do")
	public String insertVytEcbWorkPopup(
			@ModelAttribute("vyEcbWorkVO") VytEcbWorkVO vyEcbWorkVO,
			ModelMap model) throws Exception {
		
		//사업종류 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI089");
		List<?> worktype_result = cmmUseService.selectCmmCodeDetail(vo);//cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("workTypeCodeList", worktype_result);
				
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		return "sys/vyt/ecb/sld/insertWorkManagePopup";
	}
	
	/**
	 * @param fieldBookVO
	 * @param req
	 * @param model
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 * @description 사업 등록
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/insertVytEcbWork.do")
    public ModelAndView insertVytEcbWork(
    		@ModelAttribute("vyEcbWorkVO") VytEcbWorkVO vyEcbWorkVO,
    		@RequestParam(value = "file") MultipartFile mFile,
    		Model model, 
    		BindingResult bindingResult) throws Exception {
 		long start = System.currentTimeMillis();
		// 추후 수정
		ModelAndView mv = null;
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
				List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir+"ecb"+File.separator, maxFileSize, extWhiteList);
				if (list.size() > 0) {
					//fieldBookVO.setMst_admin_user(loginVO.getName());
					mstId = vytEcbSldManageService.insertWork(vyEcbWorkVO);
					commandMap.put("id", mstId);
					vyEcbWorkVO.setId(mstId);
					results = vytEcbSldManageService.insertStripLand(vyEcbWorkVO,mFile);
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
						vytEcbSldManageService.deleteWork(commandMap);
					}
					mv.addObject("message", message);
				}else {
					mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
				}
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				vytEcbSldManageService.deleteWork(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				vytEcbSldManageService.deleteWork(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
		}
		long end = System.currentTimeMillis();
		LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		return mv;
	}
	
	/**
	 * 사업 상세조회
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/selectVytEcbWorkDetail.do")
	public String selectFieldBookDetail(
  		@ModelAttribute("searchVO") VytEcbWorkVO searchVO,
  		@RequestParam(value="id") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 사업 상세정보조회 */
		VytEcbWorkVO result  = vytEcbSldManageService.selectVytEcbWorkDetail(commandMap);
		model.addAttribute("result",result);
		
		searchVO.setId(id);
		
		String schpageUnit = Integer.toString((searchVO.getPageUnit()));
		model.addAttribute("schpageUnit",schpageUnit);
		
		/* 사업 조사대상지 리스트조회 */
		List<VytEcbStripLandVO> resultList = null;
		
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
		
		resultList = vytEcbSldManageService.selectVytEcbWorkSldList(searchVO);
		int totCnt = vytEcbSldManageService.selectVytEcbWorkSldListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
				
		return "sys/vyt/ecb/sld/workManageDetail";
	}
	
	/**
	 * 사업 삭제
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/deleteVytEcbWork.do")
	public ModelAndView deleteVytEcbWork(@RequestParam(value="id") String id, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			vytEcbSldManageService.deleteWork(commandMap);
			vytEcbSldManageService.deleteWorkAllSld(commandMap);
			//vytEcbSldManageService.deleteWorkComptSld(commandMap);
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
	 * 사업 대상지 추가등록 팝업
	 * @param stripLandVO
	 * @param mst_id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/insertVytEcbWorkSldPopup.do")
	public String insertVytEcbWorkSldPopup(
			@ModelAttribute("vyEcbWorkVO") VytEcbWorkVO vyEcbWorkVO,
			@RequestParam(value="id") String id,
			ModelMap model) throws Exception {
		
		model.addAttribute("id",id);
		
		return "sys/vyt/ecb/sld/insertWorkSldManagePopup";
	}
	
	/**
	 * 사업 대상지 추가등록
	 * @param vyEcbWorkVO
	 * @param id
	 * @param mFile
	 * @param model
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/insertVytEcbWorkSld.do")
	public ModelAndView insertVytEcbWorkSld(
			@ModelAttribute("vyEcbWorkVO") VytEcbWorkVO vyEcbWorkVO,
			@RequestParam(value="id") String id,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		System.out.println("loginVO" + loginVO);
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		vyEcbWorkVO.setId(id);
		vyEcbWorkVO.setCreatUser(loginVO.getName());
		JSONObject results = null;
		
		try {
			List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir+"ecb"+File.separator, maxFileSize, extWhiteList);
			if(list.size() > 0) {
				results = vytEcbSldManageService.insertStripLand(vyEcbWorkVO,mFile);
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
			vytEcbSldManageService.deleteWork(commandMap);
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			vytEcbSldManageService.deleteWork(commandMap);
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
	 * @description 사업 대상지 수정조회
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/updateWorkView.do")
    public String updateWorkView(
    		@ModelAttribute("searchVO") VytEcbWorkVO searchVO,
    		@RequestParam(value="id") String id, Model model) throws Exception {

		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 사업방 상세정보조회 */
		VytEcbWorkVO result  = vytEcbSldManageService.selectVytEcbWorkDetail(commandMap);
		model.addAttribute("result",result);
		searchVO.setId(id);
		
		/* 사업방 조사데이터 리스트조회 */
		List<VytEcbStripLandVO> resultList = vytEcbSldManageService.selectVytEcbWorkSldListView(searchVO);
		model.addAttribute("resultList", resultList);
				
		return "sys/vyt/ecb/sld/workManageUpdt";
	}

	/**
	 * @param label
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 공유방 조사데이터 수정
	 */
	@RequestMapping(value = "/sys/vyt/ecb/sld/updateWorkItem.do")
	public void updateWorkItem(
			@RequestParam(value="label[]") List<Integer> label,
			@RequestParam(value="mst_id") String mst_id,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		
		JSONObject result = new JSONObject();
		HashMap<String, Object> commandMap = new HashMap<String, Object>();
		try {
			
			if(label.size() > 0) {
				commandMap.put("label", label);
				commandMap.put("id", mst_id);
				vytEcbSldManageService.deleteWorkItem(commandMap);
				result.put("status", "success");
				result.put("message", egovMessageSource.getMessage("success.common.update"));
			}
//			for(int i=0; i<label.size(); i++) {
//				
//				commandMap.put("label", label.get(i));
//				commandMap.put("id", mst_id);
//				vytEcbSldManageService.deleteWorkItem(commandMap);
//				result.put("status", "success");
//				result.put("message", egovMessageSource.getMessage("success.common.update"));
//			}
		} catch(Exception e) {
			e.printStackTrace();
			result.put("status", "failed");
			result.put("message", egovMessageSource.getMessage("fail.common.msg"));
		}		
		out.print(result);
	}
}
