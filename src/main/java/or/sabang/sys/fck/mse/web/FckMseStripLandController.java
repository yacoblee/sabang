package or.sabang.sys.fck.mse.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.fck.mse.service.FckMseStripLandService;
import or.sabang.sys.fck.mse.service.FckMseStripLandVO;
import or.sabang.utl.CommonUtil;

@Controller
public class FckMseStripLandController {
	
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
	
	@Resource(name = "fckMseStripLandService") 	
	private FckMseStripLandService fckMseStripLandService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
//	@Resource(name="superMapUtils")
//	private SuperMapUtils superMapUtils;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
    private static final String NSDI_API_URL = EgovProperties.getProperty("nsdi.apiUrl");
	private static final String NSDI_API_KEY = EgovProperties.getProperty("nsdi.apiKey");
    
	private static final Logger LOGGER = LoggerFactory.getLogger(FckMseStripLandController.class);
	
	
	/**
	 * 대상지목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/selectMseSldList.do")	
    public String selectLcpSvySldList (@ModelAttribute("searchVO") FckMseStripLandVO searchVO,ModelMap model) throws Exception {
		
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
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
			adminVO.setSdNm(searchVO.getSvySd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(searchVO.getSvySgg() != null && !searchVO.getSvySgg().trim().isEmpty()) {
			adminVO.setSggNm(searchVO.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getSvyEmd() != null && !searchVO.getSvyEmd().trim().isEmpty()) {
			adminVO.setEmdNm(searchVO.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}	
		
		List<?> sldList = fckMseStripLandService.selectFckMseSldList(searchVO);
		model.addAttribute("resultList", sldList);

		int totCnt = fckMseStripLandService.selectFckMseSldTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/fck/mse/sld/mseSldList";		
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/selectMseSldDetail.do")	
	public String selectMseSldDetail (@ModelAttribute("searchVO") FckMseStripLandVO stripLandVO,ModelMap model) throws Exception {
		
		/* 대상지 등록정보 상세조회 */
		FckMseStripLandVO vo = fckMseStripLandService.selectMseSldDetail(stripLandVO);
 		model.addAttribute("result",vo);

		/* 대상지 통신모뎀번호 리스트 */
 		List<?> modemCellNumList = fckMseStripLandService.selectMseSldModemCellNumList(stripLandVO);
 		model.addAttribute("modemCellNumList",modemCellNumList);
 		
 		/* 대상지 지중경사계 리스트 */
 		List<?> licMeterList = fckMseStripLandService.selectMseSldLicMeterList(stripLandVO);
 		model.addAttribute("licMeterList", licMeterList);
 		
		return "sys/fck/mse/sld/mseSldDetail";
	}
	
	/**
	 * 대상지 등록화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/insertMseSldView.do")
	public String registStripLandView(@ModelAttribute("searchVO") FckMseStripLandVO searchVO, ModelMap model) throws Exception{
		
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
		
		return "sys/fck/mse/sld/insertMseSld";
	}
	
	/**
	 * 대상지를 등록한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/insertMseSld.do")
	public ModelAndView insertMseSld(@ModelAttribute("searchVO") FckMseStripLandVO searchVO, HttpServletRequest req, Model model, BindingResult bindingResult) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		//통신모뎀번호
		if(!searchVO.getModemNum().isEmpty()) searchVO.setModemNum(searchVO.getModemNum().replaceAll("&quot;", "\""));
		//이동전화번호
		if(!searchVO.getCellNum().isEmpty()) searchVO.setCellNum(searchVO.getCellNum().replaceAll("&quot;", "\""));
		//계측장비
		if(!searchVO.getWireExt().isEmpty()) searchVO.setWireExt(searchVO.getWireExt().replaceAll("&quot;", "\""));
		if(!searchVO.getLicMeter().isEmpty()) searchVO.setLicMeter(searchVO.getLicMeter().replaceAll("&quot;", "\""));
		if(!searchVO.getLicMeterPerCheck().isEmpty()) searchVO.setLicMeterPerCheck(searchVO.getLicMeterPerCheck().replaceAll("&quot;", "\""));
		if(!searchVO.getGwGauge().isEmpty()) searchVO.setGwGauge(searchVO.getGwGauge().replaceAll("&quot;", "\""));
		if(!searchVO.getRainGauge().isEmpty()) searchVO.setRainGauge(searchVO.getRainGauge().replaceAll("&quot;", "\""));
		if(!searchVO.getStrcDpm().isEmpty()) searchVO.setStrcDpm(searchVO.getStrcDpm().replaceAll("&quot;", "\""));
		if(!searchVO.getSurDpm().isEmpty()) searchVO.setSurDpm(searchVO.getSurDpm().replaceAll("&quot;", "\""));
		if(!searchVO.getGpsGauge().isEmpty()) searchVO.setGpsGauge(searchVO.getGpsGauge().replaceAll("&quot;", "\""));
		if(!searchVO.getGateway().isEmpty()) searchVO.setGateway(searchVO.getGateway().replaceAll("&quot;", "\""));
		if(!searchVO.getNode().isEmpty()) searchVO.setNode(searchVO.getNode().replaceAll("&quot;", "\""));
		
		try {
			fckMseStripLandService.insertMseSld(searchVO);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * 소재지 소유구분을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/selectMseSldAddrOwner.do")	
	public ModelAndView selectMseSldAddrOwner (ModelMap model, @ModelAttribute("searchVO") FckMseStripLandVO searchVO, HttpServletRequest req) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			
			String path1 = req.getRequestURL().toString();
			String path2 = req.getRequestURI(); 
			
			String domain = path1.replaceAll(path2, "");
			List<EgovMap> pnuCodeList = new ArrayList<EgovMap>();
			pnuCodeList.addAll(fckMseStripLandService.selectPnuCode(searchVO));
			String pnuCode = pnuCodeList.get(0).get("pnucode").toString();
			if(pnuCode == null) {
				mv.addObject("message", "");
			}else {
				String returnValue = CommonUtil.searchPosesnSe(pnuCode, domain);
				mv.addObject("returnValue", returnValue);
			}
			mv.addObject("status","success");
			mv.addObject("svyLon", pnuCodeList.get(0).get("svyLon").toString());
			mv.addObject("svyLat", pnuCodeList.get(0).get("svyLat").toString());
			mv.addObject("svyData", pnuCodeList.get(0).get("svyData").toString());
			mv.addObject("pnuCode", pnuCode);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}		
		return mv;		
	}
	
	/**
	 * 대상지 수정화면으로 이동한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/updateMseSldView.do")	
	public String updateMseSldView (FckMseStripLandVO stripLandVO,ModelMap model) throws Exception {
		
		/* 대상지 등록정보 상세조회 */
		FckMseStripLandVO vo = fckMseStripLandService.selectMseSldDetail(stripLandVO);
		model.addAttribute("result",vo);
		vo.setAddrLangType("ko");
		
//		String pnuCode = fckMseStripLandService.selectPnuCode(vo);		
// 		model.addAttribute("pnuCode",pnuCode);
 		
 		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(vo.getSvySd() != null && !vo.getSvySd().trim().isEmpty()) {
			adminVO.setSdNm(vo.getSvySd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(vo.getSvySgg() != null && !vo.getSvySgg().trim().isEmpty()) {
			adminVO.setSggNm(vo.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(vo.getSvyEmd() != null && !vo.getSvyEmd().trim().isEmpty()) {
			adminVO.setEmdNm(vo.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}	
		
		/* 대상지 통신모뎀번호 리스트 */
 		List<?> modemCellNumList = fckMseStripLandService.selectMseSldModemCellNumList(stripLandVO);
 		model.addAttribute("modemCellNumList", modemCellNumList);
 		
 		/* 대상지 지중경사계 리스트 */
 		List<?> licMeterList = fckMseStripLandService.selectMseSldLicMeterList(stripLandVO);
 		model.addAttribute("licMeterList", licMeterList);
 		
 		
		return "sys/fck/mse/sld/mseSldUpdt";
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/updateMseSld.do")
	public ModelAndView updateMseSld(FckMseStripLandVO stripLandVO, HttpServletRequest req, Model model, BindingResult bindingResult) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		//통신모뎀번호
		if(!stripLandVO.getModemNum().isEmpty()) stripLandVO.setModemNum(stripLandVO.getModemNum().replaceAll("&quot;", "\""));
		//이동전화번호
		if(!stripLandVO.getCellNum().isEmpty()) stripLandVO.setCellNum(stripLandVO.getCellNum().replaceAll("&quot;", "\""));
		//계측장비
		if(!stripLandVO.getWireExt().isEmpty()) stripLandVO.setWireExt(stripLandVO.getWireExt().replaceAll("&quot;", "\""));
		if(!stripLandVO.getLicMeter().isEmpty()) stripLandVO.setLicMeter(stripLandVO.getLicMeter().replaceAll("&quot;", "\""));
		if(!stripLandVO.getLicMeterPerCheck().isEmpty()) stripLandVO.setLicMeterPerCheck(stripLandVO.getLicMeterPerCheck().replaceAll("&quot;", "\""));
		if(!stripLandVO.getGwGauge().isEmpty()) stripLandVO.setGwGauge(stripLandVO.getGwGauge().replaceAll("&quot;", "\""));
		if(!stripLandVO.getRainGauge().isEmpty()) stripLandVO.setRainGauge(stripLandVO.getRainGauge().replaceAll("&quot;", "\""));
		if(!stripLandVO.getStrcDpm().isEmpty()) stripLandVO.setStrcDpm(stripLandVO.getStrcDpm().replaceAll("&quot;", "\""));
		if(!stripLandVO.getSurDpm().isEmpty()) stripLandVO.setSurDpm(stripLandVO.getSurDpm().replaceAll("&quot;", "\""));
		if(!stripLandVO.getGpsGauge().isEmpty()) stripLandVO.setGpsGauge(stripLandVO.getGpsGauge().replaceAll("&quot;", "\""));
		if(!stripLandVO.getGateway().isEmpty()) stripLandVO.setGateway(stripLandVO.getGateway().replaceAll("&quot;", "\""));
		if(!stripLandVO.getNode().isEmpty()) stripLandVO.setNode(stripLandVO.getNode().replaceAll("&quot;", "\""));
		
		try {
			fckMseStripLandService.updateMseSld(stripLandVO);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sld/deleteMseSld.do")
	public ModelAndView deleteMseSld(FckMseStripLandVO stripLandVO, Model model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			fckMseStripLandService.deleteMseSld(stripLandVO);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}
} 
