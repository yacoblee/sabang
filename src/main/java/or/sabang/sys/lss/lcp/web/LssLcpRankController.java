package or.sabang.sys.lss.lcp.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.lcp.service.LssLcpRankService;
import or.sabang.sys.lss.lcp.service.LssLcpRankVO;
import or.sabang.sys.service.SysComptVO;

@Controller
public class LssLcpRankController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	@Resource(name = "lssLcpRankService")
	private LssLcpRankService lssLcpRankService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.rank");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssLcpRankController.class);
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 랭크관리 목록조회
	 */
	@RequestMapping(value = "/sys/lss/lcp/rnk/selectLcpSvyRankList.do")
	public String selectLcpSvyRankList(@ModelAttribute("searchVO") LssLcpRankVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectLcpSvyRankList controller");
		
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
		if(searchVO.getSvyEmd() != null && !searchVO.getSvyEmd().trim().isEmpty() ) {
			adminVO.setEmdCode(searchVO.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		List<LssLcpRankVO> list = lssLcpRankService.selectLcpRankList(searchVO);
		
		model.addAttribute("resultList", list);
	
		int totCnt = lssLcpRankService.selectLcpRankListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/lcp/rnk/rankManageList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 랭크등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/lcp/rnk/insertLcpSvyRankPopup.do")
	public String insertLcpSvyRankPopup(HttpServletRequest req, ModelMap model) throws Exception {
		return "sys/lss/lcp/rnk/insertLcpSvyRankPopup";
	}
	
	/**
	 * 랭크 쉐이프파일 등록
	 * @param mFile
	 * @param model
	 * @return
	 * @throws SecurityException
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/rnk/insertLcpSvyRank.do")
    public ModelAndView insertLcpSvyRank(@RequestParam(value = "file") MultipartFile mFile, Model model) throws SecurityException,Exception {
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
			
			try {
				List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
				if (list.size() > 0) {
					EgovFormBasedFileVo fileVO = list.get(0);
					results = lssLcpRankService.insertLcpSvyRank(fileVO);
					mv.addObject("status",results.get("status"));
		    		mv.addObject("message", results.get("message"));
				}else {
					mv.addObject("status","fail");
		    		mv.addObject("message", "등록파일을 찾을 수 없습니다.\n관리자에게 문의하세요.");
				}
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
	 * 랭크 데이터 다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/rnk/selectDownloadLcpSvyRank.do")
	public void selectDownloadLcpSvyRank(HttpServletRequest request, HttpServletResponse response) throws Exception{
		AnalFileVO vo = lssLcpRankService.downloadLcpSvyRank();
		
		String saveFileNm = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
		
		File uFile = new File(saveFileNm);
		long fSize = uFile.length();
		if (fSize > 0) {
			String mimetype = "application/x-msdownload";
			
			String userAgent = request.getHeader("User-Agent");
			HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
			if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
				mimetype = "application/x-stuff";
			}

			String contentDisposition = EgovBrowserUtil.getDisposition(vo.getOrignlFileNm().concat(".").concat(vo.getFileExtsn()),userAgent,"UTF-8");
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
				EgovResourceCloseHelper.close(in, out);
			}
		}
	}
	
	/**
	 * 수퍼맵 신규등록 및 수정 테스트 팝업
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/sys/lss/lcp/rnk/upsertLcpSvyComptTestPopup.do")
//	public String upsertLcpSvyComptTestPopup(HttpServletRequest req, ModelMap model) throws Exception {
//		return "sys/lss/lcp/rnk/upsertLcpSvyComptTestPopup";
//	}
	
	/**
	 * 수퍼맵 신규등록 및 수정 테스트(전자야장 -> 시스템)
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/sys/lss/lcp/rnk/upsertLcpSvyComptTest.do")
//	public ModelAndView upsertLcpSvyComptTest(@RequestParam(value = "jsonValue") String jsonVal,
//			@RequestParam(value = "epsg") int epsgVal) throws Exception{
//		ModelAndView mv = null;
//		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//		if(!isAuthenticated) {
//			//mv = new ModelAndView("forward:/login.do");
//			mv = new ModelAndView("jsonView");
//			mv.addObject("status","forward");
//			mv.addObject("url","/login.do");
//    		mv.addObject("message", egovMessageSource.getMessage("fail.session.timeout"));
//			throw new ModelAndViewDefiningException(mv);
//		} else {
//			mv = new ModelAndView("jsonView");
//			HashMap<String, Object> commandMap = new HashMap<>();
//			
//			String htmlStrCnvr = EgovStringUtil.getHtmlStrCnvr(jsonVal);
//			
//			JSONParser parser = new JSONParser();
//			JSONObject results = null;
//			org.json.simple.JSONObject params = (org.json.simple.JSONObject) parser.parse(htmlStrCnvr);
//			
//			try {
//				SysComptVO itemVo = new SysComptVO();
//				
//				itemVo.setMst_id(params.get("MST_ID") != null ? Integer.valueOf(params.get("MST_ID").toString()) : null);
//				itemVo.setLogin_id(params.get("LOGIN_ID") != null ? params.get("LOGIN_ID").toString() : null);
//				itemVo.setSvy_fid(params.get("_FID") != null ? Integer.valueOf(params.get("_FID").toString()) : null);
//				itemVo.setSvy_lon(params.get("_LON") != null ? Double.valueOf(params.get("_LON").toString()) : null);
//				itemVo.setSvy_lat(params.get("_LAT") != null ? Double.valueOf(params.get("_LAT").toString()) : null);
//				itemVo.setSvy_keyword(params.get("_KEYWORD") != null ? params.get("_KEYWORD").toString() : null);
//				itemVo.setSvy_label(params.get("_LABEL") != null ? params.get("_LABEL").toString() : null);
//				itemVo.setSvy_style(params.get("_STYLE") != null ? params.get("_STYLE").toString() : null);
//				itemVo.setSvy_memo(params.get("_MEMO") != null ? params.get("_MEMO").toString() : null);
//				itemVo.setSvy_tag1(params.get("_TAG1") != null ? params.get("_TAG1").toString() : null);
//				itemVo.setSvy_tag2(params.get("_TAG2") != null ? params.get("_TAG2").toString() : null);
//				itemVo.setSvy_attr(params.get("ATTR") != null ? params.get("ATTR").toString() : null);
//				itemVo.setSmgeometry(params.get("_DATA") != null ? params.get("_DATA").toString() : null);
//				itemVo.setCreat_dt(params.get("_REG_DATE") != null ? params.get("_REG_DATE").toString() : null);
//				itemVo.setLast_updt_pnttm(params.get("_UPD_DATE") != null ? params.get("_UPD_DATE").toString() : null);
//				
////				if(params.get("_REG_DATE") != null) {
////					String regDate = params.get("_REG_DATE").toString();
////					regDate = regDate.trim().replaceAll("/", "-");
////					Timestamp creatDt = Timestamp.valueOf(regDate);
////					itemVo.setCreat_dt(creatDt);
////				}
////				if(params.get("_UPD_DATE") != null) {
////					String updDate = params.get("_UPD_DATE").toString();
////					updDate = updDate.trim().replaceAll("/", "-");
////					Timestamp lastUpdtPnttm = Timestamp.valueOf(updDate);
////					itemVo.setLast_updt_pnttm(lastUpdtPnttm);
////				}
//				
//				String query = "mst_id = ".concat(params.get("MST_ID").toString()).concat(" and svy_label = '").concat(params.get("_LABEL").toString()).concat("'");
//				
//				results = superMapUtils.upsertRecordSet(itemVo, "tf_feis_lcp_svycompt", query,epsgVal);
//				
//				mv.addObject("status",results.get("status"));
//	    		mv.addObject("message", results.get("message"));
//			} catch (Exception e) {
//				LOGGER.error(e.getMessage());
//				mv.addObject("status","fail");
//	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
//			}
//		}
//		return mv;
//	}
	
	/**
	 * 수퍼맵 삭제 테스트 팝업
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/sys/lss/lcp/rnk/deleteLcpSvyComptTestPopup.do")
//	public String deleteLcpSvyComptTestPopup(HttpServletRequest req, ModelMap model) throws Exception {
//		return "sys/lss/lcp/rnk/deleteLcpSvyComptTestPopup";
//	}
	
	/**
	 * 수퍼맵 신규등록 및 수정 테스트(전자야장 -> 시스템)
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/sys/lss/lcp/rnk/deleteLcpSvyComptTest.do")
//	public ModelAndView deleteLcpSvyComptTest(@RequestParam(value = "smid") String smid) throws Exception{
//		ModelAndView mv = null;
//		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//		if(!isAuthenticated) {
//			//mv = new ModelAndView("forward:/login.do");
//			mv = new ModelAndView("jsonView");
//			mv.addObject("status","forward");
//			mv.addObject("url","/login.do");
//    		mv.addObject("message", egovMessageSource.getMessage("fail.session.timeout"));
//			throw new ModelAndViewDefiningException(mv);
//		} else {
//			mv = new ModelAndView("jsonView");
//			JSONObject results = null;
//			try {
//				String query = "smid = ".concat(smid);
//				results = superMapUtils.deleteRecordSet("tf_feis_lcp_svycompt", query);
//				
//				mv.addObject("status",results.get("status"));
//	    		mv.addObject("message", results.get("message"));
//			} catch (Exception e) {
//				LOGGER.error(e.getMessage());
//				mv.addObject("status","fail");
//	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
//			}
//		}
//		return mv;
//	}
}
