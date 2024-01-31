package or.sabang.sys.vyt.ecb.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.ecb.service.VytEcbAlsManageService;
import or.sabang.sys.vyt.ecb.service.VytEcbAnalVO;
import or.sabang.utl.CommonUtil;

@Controller
public class VytEcbAlsManageController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "vytEcbAlsManageService") 	
	private VytEcbAlsManageService vytEcbAlsManageService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** 첨부파일 위치 지정  => globals.properties */
//    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.stripland");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
//    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
//    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
//	private static final String VWORLD_API_URL = EgovProperties.getProperty("vworld.landUrl");
//	private static final String VWORLD_API_KEY = EgovProperties.getProperty("vworld.apiKey");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VytEcbAlsManageController.class);
	
	int cnt_total = 0;
	
	//EROSION CONTROL BUSINESS(사방사업)
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 사업 목록조회
	 */
	@RequestMapping(value = "/sys/vyt/ecb/als/selectVytEcbAnalList.do")
	public String selectVytEcbAnalList(@ModelAttribute("searchVO") VytEcbAnalVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectVytEcbAnalList controller");
		
		List<VytEcbAnalVO> list = null;
		
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
			searchVO.setWorkYear(vytEcbAlsManageService.selectVytEcbWorkMaxYear());
		}
		
		//사업종류 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI089");
		List<?> worktype_result = cmmUseService.selectCmmCodeDetail(vo);//cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("workTypeCodeList", worktype_result);
		
		//연도코드 조회
		List<?> year_result = vytEcbAlsManageService.selectVytEcbWorkYear();
		model.addAttribute("yearCodeList", year_result);
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(searchVO.getWorkSd() != null && !searchVO.getWorkSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getWorkSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(searchVO.getWorkSgg() != null && !searchVO.getWorkSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getWorkSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getWorkEmd() != null && !searchVO.getWorkEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getWorkEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		try {
			list = vytEcbAlsManageService.selectVytEcbWorkList(searchVO);
			cnt_total = vytEcbAlsManageService.selectVytEcbWorkListTotCnt(searchVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
 		model.addAttribute("resultList", list);
		
		return "sys/vyt/ecb/als/analManageList";
	}
	
	/**
	 * 분석대상지 상세조회
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/als/selectAnalStripLandDetail.do")
	public String selectAnalStripLandDetail(
  		@ModelAttribute("searchVO") VytEcbAnalVO searchVO, Model model) throws Exception {
		
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		VytEcbAnalVO vo = vytEcbAlsManageService.selectVytEcbWorkDetail(searchVO);
		List<AnalFileVO> list = vytEcbAlsManageService.selectVytEcbAnalDetail(searchVO);
		
		int statDataCnt = vytEcbAlsManageService.selectVytEcbAnalStatDataCnt(searchVO);
		
		model.addAttribute("result", vo);
		model.addAttribute("resultList", list);
		model.addAttribute("statDataCnt",statDataCnt);
		
		return "sys/vyt/ecb/als/analManageDetail";
	}
	
	/**
	 * 분석통계엑셀다운로드
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/als/selectVytEcbAnalStatDataExcel.do")
	public ModelAndView selectVytEcbAnalStatDataExcel(VytEcbAnalVO searchVO) throws Exception{
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("ecbStatExcelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	HashMap<?, ?> _map = (HashMap<?, ?>)vytEcbAlsManageService.selectVytEcbAnalStatDataExcel(searchVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    	
    	String fileNm = "사방사업타당성평가분석통계_".concat(formater.format(new Date()).toString());
    	
    	//dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}
	
	/**
	 * 분석결과 전체다운로드
	 * @param searchVO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/als/selectDownloadAnalAll.do")
	public void selectDownloadAnalAll(
			@ModelAttribute("searchVO") VytEcbAnalVO searchVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		
		if(isAuthenticated) {
			searchVO.setSearchKeyword(loginVO.getId());
			AnalFileVO vo = vytEcbAlsManageService.downloadAnalAll(searchVO);
			
			if(vo.getFileStreCours().length() > 0) {
				String saveFileNm = vo.getFileStreCours()+File.separator+loginVO.getId()+File.separator+vo.getAnalId()+".".concat(vo.getFileExtsn());
				
				File uFile = new File(saveFileNm);
				long fSize = uFile.length();
				if (fSize > 0) {
					String mimetype = "application/x-msdownload";
					
					String userAgent = request.getHeader("User-Agent");
					HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
					if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
						mimetype = "application/x-stuff";
					}

					String contentDisposition = EgovBrowserUtil.getDisposition(vo.getAnalId()+".".concat(vo.getFileExtsn()),userAgent,"UTF-8");
					response.setContentType(mimetype);
					response.setHeader("Content-Disposition", contentDisposition);
					response.setContentLengthLong(fSize);

					BufferedInputStream in = null;
					BufferedOutputStream out = null;

					try {
						in = new BufferedInputStream(new FileInputStream(uFile));
						out = new BufferedOutputStream(response.getOutputStream());

						FileCopyUtils.copy(in, out);
						out.flush();
					} catch (IOException ex) {
						// 다음 Exception 무시 처리
						// Connection reset by peer: socket write error
						EgovBasicLogger.ignore("IO Exception", ex);
					} finally {
						//EgovFileUtil.rm(vo.getFileStreCours()+File.separator+loginVO.getId());
						EgovResourceCloseHelper.close(in, out);
						FileUtils.deleteDirectory(new File(vo.getFileStreCours()+File.separator+loginVO.getId()));
					}
				}
			}
		}
	}
	
	/**
	 * 지적 조회
	 * @param cadastralWkt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/ecb/als/selectCadastralDetail.do")
	public ModelAndView selectCadastralDetail(@RequestParam("cadastralWkt") String cadastralWkt, HttpServletRequest req) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jsonView");
    	
		List<EgovMap> cadastralList = vytEcbAlsManageService.selectCadastralDetail(cadastralWkt);
		
		String path1 = req.getRequestURL().toString();
		String path2 = req.getRequestURI(); 
		
		String domain = path1.replaceAll(path2, "");
		
		for(int i=0; i< cadastralList.size(); i++) {
			String pnuCode = cadastralList.get(i).get("pnucode").toString();
			String returnValue = CommonUtil.searchPosesnSe(pnuCode, domain);
			String[] returnSplit = returnValue.split(",");
			cadastralList.get(i).setValue(3, returnSplit[0]);
			String jibun = cadastralList.get(i).get("jibun").toString();
			
			if(!returnValue.equals("-")) {
				if(returnSplit[1].equals("임야대장")) {
					cadastralList.get(i).setValue(2,"산"+jibun);
				}
				
				if(returnSplit[1].contains("대장")) {
					String jimok = returnSplit[1].replaceAll("대장", "");
					cadastralList.get(i).setValue(4,jimok);
				}else {
					cadastralList.get(i).setValue(4,returnSplit[1]);
				}
			}
		}
    	
    	modelAndView.addObject("status",200);
    	modelAndView.addObject("result",cadastralList);
    	return modelAndView;
	}
}