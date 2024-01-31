package or.sabang.sys.lss.lcp.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.lcp.service.LssLcpGvfService;
import or.sabang.sys.lss.lcp.service.LssLcpGvfVO;
import or.sabang.sys.lss.lcp.service.LssLcpRankVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.sys.service.SysComptVO;
import or.sabang.sys.service.ZonalStatisticVO;
import or.sabang.utl.LssLcpSupMapUtils;
import or.sabang.utl.SuperMapBasicUtils;

@Controller
public class LssLcpGvfController {
	
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
	
	@Resource(name = "lssLcpGvfService")
	private LssLcpGvfService lssLcpGvfService;
	
	@Resource(name = "lssLcpSvyStripLandService") 	
	private LssLcpSvyStripLandService lssLcpSvyStripLandService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.gvf");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssLcpGvfController.class);
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 제보관리 목록조회
	 */
	@RequestMapping(value = "/sys/lss/lcp/gvf/selectLcpSvyGvfList.do")
	public String selectLcpSvyGvfList(@ModelAttribute("searchVO") LssLcpRankVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {

		LOGGER.info("selectLcpSvyGvfList controller");
		
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
		if(searchVO.getSvyEmd() != null && !searchVO.getSvyEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		List<?> list = lssLcpGvfService.selectLcpGvfList(searchVO);
		
		model.addAttribute("resultList", list);
	
		int totCnt = lssLcpGvfService.selectLcpGvfListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/lcp/gvf/gvfManageList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 제보등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/lcp/gvf/insertLcpSvyGvfPopup.do")
	public String insertLcpSvyGvfPopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		/* 조사대상지 등록정보방 목록 조회 */
		List<?> resultList = lssLcpSvyStripLandService.selectSvySldRegInfoList();
		model.addAttribute("resultList", resultList);
		
		return "sys/lss/lcp/gvf/insertLcpSvyGvfPopup";
	}
	
	/**
	 * 제보 쉐이프파일 등록
	 * @param mFile
	 * @param model
	 * @return
	 * @throws SecurityException
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/gvf/insertLcpSvyGvf.do")
    public ModelAndView insertLcpSvyGvf(@ModelAttribute("gvfVO") LssLcpGvfVO gvfVO, HttpServletRequest req, @RequestParam(value = "file") MultipartFile mFile, Model model) throws SecurityException,Exception {
		// 추후 수정
		
		ModelAndView mv = null;
		ArrayList<String> smidList = new ArrayList<>();
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
					// 제보 데이터 등록 (tf_feis_lcp_gvf)
					results = lssLcpGvfService.insertLcpSvyGvf(fileVO);
					
					//File deFile = new File(uploadDir+fileVO.getServerSubPath()+File.separator+fileVO.getPhysicalName().replace(".zip", ""));
					//FileUtils.deleteDirectory(deFile);
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
		
		// 제보 데이터 대상지 아이디, 유니크 아이디 부여
		String sldId = req.getParameter("svysldNm");
		List<EgovMap> gvfList =  lssLcpGvfService.selectLcpGvfAddList();
		int gvfListCnt = gvfList.size();
		if(gvfListCnt > 0) {
			for(int i = 0; i < gvfList.size(); i++) {
				String gid = Integer.toString(i+1);
				smidList.add(gvfList.get(i).get("smid").toString());
				gvfVO.setGid(gid);
				gvfVO.setSmid(gvfList.get(i).get("smid").toString());
				gvfVO.setSvyRank(0);
				gvfVO.setSvyLabel(gvfList.get(i).get("svyLabel").toString());
				gvfVO.setSldId(sldId);
				gvfVO.setCtrdCd(gvfList.get(i).get("ctrdCd").toString());
				gvfVO.setStdgCd(gvfList.get(i).get("stdgCd").toString());
				gvfVO.setSvyAddr(gvfList.get(i).get("stdgNm").toString());
				
				// 제보데이터 임상도 정보조회
				LssLcpGvfVO gvfVO1 = lssLcpGvfService.selectGvfImInfo(gvfVO);
				// 제보데이터 입지도 정보조회
				LssLcpGvfVO gvfVO2 = lssLcpGvfService.selectGvfIjInfo(gvfVO);
				// 제보데이터 지질도 정보조회(
				LssLcpGvfVO gvfVO3 = lssLcpGvfService.selectGvfGlInfo(gvfVO);
				if(gvfVO1 != null) {
					gvfVO.setFrtpCd(gvfVO1.getFrtpCd());
					gvfVO.setMapLabel(gvfVO1.getMapLabel());
				}
				if(gvfVO2 != null) {
					gvfVO.setPrrckLarg(gvfVO2.getPrrckLarg());
					gvfVO.setSltpCd(gvfVO2.getSltpCd());
					gvfVO.setSibflrScs(gvfVO2.getSibflrScs());
					gvfVO.setSibflrStr(gvfVO2.getSibflrStr());
					gvfVO.setRockExdgr(gvfVO2.getRockExdgr());
					gvfVO.setSibflrCbs(gvfVO2.getSibflrCbs());
					gvfVO.setWteffDgr(gvfVO2.getWteffDgr());
				}
				if(gvfVO3 != null) {
					gvfVO.setPrrckLarg(gvfVO3.getPrrckLarg());
					gvfVO.setPrrckMddl(gvfVO3.getPrrckMddl());
				}
				lssLcpGvfService.updateLcpGvfData(gvfVO);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			
			Calendar cal = Calendar.getInstance();
			String yearCal = String.valueOf(cal.get(Calendar.YEAR));
			
			List<ZonalStatisticVO> resultList = null;
			
			String processId = SuperMapBasicUtils.getDatasetUuid();
			
			LssLcpSupMapUtils utils = new LssLcpSupMapUtils(processId);
			
			String creatYearGvfTbl = "ta_gvf".concat(yearCal+"_").concat(processId);
			String smid = smidList.toString();
			smid = smid.substring(1, smid.length()-1);
			String creatYearRankWhere = "smid in(".concat(smid).concat(")");
			
			//고도값 생성
			resultList = utils.createLssLcpGvfElevation(processId,creatYearGvfTbl,creatYearRankWhere);
			lssLcpSvyStripLandService.updateSvySldElevInfo(resultList);
			
			//경사도값 생성
			resultList = utils.createLssLcpGvfSlope(processId,creatYearGvfTbl,creatYearRankWhere);
			lssLcpSvyStripLandService.updateSvySldSlopInfo(resultList);
			
			//토심값 생성
			resultList = utils.createLssLcpGvfSld(processId,creatYearGvfTbl,creatYearRankWhere);
			lssLcpSvyStripLandService.updateSvySldSldInfo(resultList);
			
			lssLcpSvyStripLandService.updateSvySldRegInfo(sldId);
			
			utils.closeWorkspace();
			Date finished = new Date();
			
			LOGGER.info("등록시작시간 : ".concat(sdf.format(now)));
			LOGGER.info("등록완료시간 : ".concat(sdf.format(finished)));
		}
		return mv;
	}
	
	/**
	 * 제보 데이터를 삭제한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/gvf/deleteLcpGvfData.do")
    public ModelAndView deleteLcpGvfData(@ModelAttribute("gvfVO") LssLcpGvfVO gvfVO, Model model) throws SecurityException,Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try{
			lssLcpGvfService.deleteLcpGvfData(gvfVO);
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * 제보 데이터 다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/gvf/selectDownloadLcpSvyGvf.do")
	public void selectDownloadLcpSvyGvf(@RequestParam(value="pnu", required=false) String pnu, HttpServletRequest request, HttpServletResponse response) throws Exception{
		AnalFileVO vo = lssLcpGvfService.downloadLcpSvyGvf(pnu);
		
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
}
