package or.sabang.sys.lss.lcp.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
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
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.sys.service.ZonalStatisticVO;
import or.sabang.utl.LssLcpSupMapUtils;
import or.sabang.utl.SuperMapBasicUtils;

@Controller
public class LssLcpSvyStripLandController {
	
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
	
	@Resource(name = "lssLcpSvyStripLandService") 	
	private LssLcpSvyStripLandService lssLcpSvyStripLandService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssLcpSvyStripLandController.class);
	

	/**
	 * 대상지목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/selectLcpSvySldList.do")	
    public String selectLcpSvySldList (@ModelAttribute("searchVO") LssLcpSvyStripLandVO searchVO,ModelMap model) throws Exception {
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<?> sldList = lssLcpSvyStripLandService.selectLssLcpSvySldList(searchVO);
		model.addAttribute("resultList", sldList);

		int totCnt = lssLcpSvyStripLandService.selectLssLcpSvySldTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/lcp/sld/svySldList";		
	}
	
	/**
	 * 대상지 목록 엑셀파일을 저장한다.
	 * @param model
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/uploadStripLandExcel.do", method = RequestMethod.POST)	
	public ModelAndView uploadLssLcpSvyStripLand(ModelMap model,
			@RequestParam(value = "file") MultipartFile mFile) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    		//model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
    	}else {
    		try {
    			List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
    			if (list.size() > 0) {
//    				EgovFormBasedFileVo vo = list.get(0);	// 첫번째 이미지
    				
//    				lssLcpSvyStripLandService.insertUploadStripLand(mFile);
    				mv.addObject("status","success");
    				mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
    			}
    		} catch (SecurityException e) {
    			LOGGER.error(e.getMessage());
    			mv.addObject("status","fail");
        		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
    		} catch (Exception e) {
    			LOGGER.error(e.getMessage());
    			mv.addObject("status","fail");
        		mv.addObject("message", egovMessageSource.getMessage("errors.file.transfer"));
    		}
    	}
    	
		return mv;
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/selectSvySldInfo.do")
	public String selectSvySldInfo(@ModelAttribute("stripLandVO") LssLcpSvyStripLandVO stripLandVO, ModelMap model, HttpServletRequest request) throws Exception{

		/* 조사대상지 등록정보 상세조회 */
		EgovMap result = lssLcpSvyStripLandService.selectSvySldRegInfoDetail(stripLandVO);
 		model.addAttribute("result",result);
		
		             
		//임상코드를 코드정보로부터 조회
 		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI071");
		List<?> frtp_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frtpCdResult", frtp_result);//임상코드목록
		
		//지질(대)코드를 코드정보로부터 조회
		vo.setCodeId("FEI072");
		List<?> prrck_larg_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("prrckLargResult", prrck_larg_result);//지질(대)코드목록
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(stripLandVO.getSd() != null && !stripLandVO.getSd().trim().isEmpty()) {
			stripLandVO.setStdgCd(stripLandVO.getSd());
			adminVO.setSdCode(stripLandVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(stripLandVO.getSgg() != null && !stripLandVO.getSgg().trim().isEmpty()) {
			stripLandVO.setStdgCd(stripLandVO.getSgg());
			adminVO.setSggCode(stripLandVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(stripLandVO.getEmd() != null && !stripLandVO.getEmd().trim().isEmpty()) {
			stripLandVO.setStdgCd(stripLandVO.getEmd());
			adminVO.setEmdCode(stripLandVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		int pageUnit = stripLandVO.getPageUnit();
		/** EgovPropertyService.sample */
		stripLandVO.setPageUnit(propertiesService.getInt("pageUnit"));
		stripLandVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(stripLandVO.getPageIndex());
		paginationInfo.setPageSize(stripLandVO.getPageSize());
		
		stripLandVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(stripLandVO.getPageUnit());

		stripLandVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		stripLandVO.setLastIndex(paginationInfo.getLastRecordIndex());
		stripLandVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		/* 조사대상지 정보조회*/
		List<?> resultList = lssLcpSvyStripLandService.selectSvySldInfo(stripLandVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = lssLcpSvyStripLandService.selectSvySldInfoCnt(stripLandVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/lcp/sld/svySldInfo";
	}
	
	/**
	 * 대상지 정보를 상세조회한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/selectSvySldInfoDetail.do")
	public String selectSvySldInfoDetail(@ModelAttribute("stripLandVO") LssLcpSvyStripLandVO stripLandVO, ModelMap model) throws Exception{
		
		//임상코드를 코드정보로부터 조회		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI071");
		List<?> frtp_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frtpCdResult", frtp_result);//임상코드목록
		
		//지질(대)코드를 코드정보로부터 조회
		vo.setCodeId("FEI072");
		List<?> prrck_larg_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("prrckLargResult", prrck_larg_result);//지질(대)코드목록
		
		//지질(중)코드를 코드정보로부터 조회
		vo.setCodeId("FEI073");
		List<?> prrck_mddl_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("prrckMddlResult", prrck_mddl_result);//지질(중)코드목록
		
		//토양형코드를 코드정보로부터 조회
		vo.setCodeId("FEI074");
		List<?> sltp_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sltpResult", sltp_result);//지질(대)코드목록

		//토성코드를 코드정보로부터 조회
		vo.setCodeId("FEI075");
		List<?> sibscs_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sibscsResult", sibscs_result);//지질(대)코드목록
		
		//토양구조코드를 코드정보로부터 조회
		vo.setCodeId("FEI076");
		List<?> sidstrct_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sidstrctResult", sidstrct_result);//지질(대)코드목록
		
		//암노출도코드를 코드정보로부터 조회
		vo.setCodeId("FEI077");
		List<?> rckexd_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("rckexdResult", rckexd_result);//지질(대)코드목록
		
		//토양석력함량코드를 코드정보로부터 조회
		vo.setCodeId("FEI078");
		List<?> sibcbs_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sibcbsResult", sibcbs_result);//지질(대)코드목록
		
		//풍화정도코드를 코드정보로부터 조회
		vo.setCodeId("FEI079");
		List<?> wteff_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("wteffResult", wteff_result);//지질(대)코드목록
	
		/* 조사대상지 정보조회*/
		LssLcpSvyStripLandVO svysldInfoDetailVO = lssLcpSvyStripLandService.selectSvySldInfoDetail(stripLandVO);
		model.addAttribute("result", svysldInfoDetailVO);
		
		return "sys/lss/lcp/sld/svySldInfoDetail";
	}
	
	/**
	 * 대상지 등록화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/insertStripLandView.do")
	public String insertStripLandView(@ModelAttribute("stripLandVO") LssLcpSvyStripLandVO stripLandVO,ModelMap model) throws Exception{
		
//		//조사유형코드를 코드정보로부터 조회
//		ComDefaultCodeVO vo = new ComDefaultCodeVO();
//		vo.setCodeId("FEI003");
//		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("typeCodeList", type_result);//조사유형코드목록
//		
//		//조사연도를 코드정보로부터 조회
//		vo.setCodeId("FEI004");
//		List<?> year_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("yearCodeList", year_result);//조사연도코드목록
//		
//		//관할목록코드를 코드정보로부터 조회
//		vo.setCodeId("FEI001");
//		List<?> region_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("regionCodeList", region_result);//관할목록코드목록
//		
		//시도목록코드를 행정구역 시도정보로부터 조회
		List<?> list = administZoneService.selectLcpAdministZoneCtprvn();
		model.addAttribute("sdCodeList",list);
		
		//행정구역별 미조사된 대상지 건수 조회
		EgovMap rankInfoCnt = lssLcpSvyStripLandService.selectRankInfoCnt();
		List<?> rankInfoCntList  = rankInfoCnt.valueList();
		model.addAttribute("rankInfoCntList",rankInfoCntList);
		
		//행정구역별 작년조사된 대상지 건수 조회
		EgovMap LastRankInfoCnt = lssLcpSvyStripLandService.selectLastRankInfoCnt();
		List<?> LastRankInfoCntList  = LastRankInfoCnt.valueList();
		model.addAttribute("lastRankInfoCntList",LastRankInfoCntList);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1); //작년
		String format = "yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String lastYear = sdf.format(cal.getTime());
		
		model.addAttribute("lastYear", lastYear);
		
		/* 조사대상지 정보조회*/
		if(stripLandVO.getSldId() != null && (!stripLandVO.getSldId().equals("0"))) {
			List<?> resultList = lssLcpSvyStripLandService.selectSvySldCtrdCnt(stripLandVO);
			model.addAttribute("resultList", resultList);			
			model.addAttribute("year", stripLandVO.getYear());
			model.addAttribute("sldId", stripLandVO.getSldId());
		}else {
			model.addAttribute("sldId", null);
		}
		
		
		return "sys/lss/lcp/sld/stripLandRegist";
	}
	
	/**
	 * 대상지를 등록한다.
	 * @param stripLandVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/insertStripLand.do")
	public ModelAndView insertStripLand(@ModelAttribute("stripLandVO") LssLcpSvyStripLandVO stripLandVO, BindingResult bindingResult, ModelMap model) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		
		ModelAndView mv = new ModelAndView("jsonView");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               		
    	try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			String[] sdCdList = stripLandVO.getSdCdList().split(",");
			String[] sdCntList = stripLandVO.getSdCntList().split(",");
			String[] sdNmList = new String[]{"경기-","충북-","충남-","전북-","전남-","경북-","경남-","강원-"};
			ArrayList<String> rankList = new ArrayList<>();
			HashMap<String, Object> sldMap = new HashMap<>();
			Calendar cal = Calendar.getInstance();
			String yearCal = stripLandVO.getYear() != null ? stripLandVO.getYear() : String.valueOf(cal.get(Calendar.YEAR));
			
			String svysldNm = yearCal+"년 땅밀림 우려 조사대상지";

			sldMap.put("svysldYear",stripLandVO.getYear());
			sldMap.put("svysldNm",svysldNm);
			sldMap.put("svysldCreateUser",user.getName());
			sldMap.put("svysldAdminUser",user.getName());
			sldMap.put("svysldRegcnt", stripLandVO.getSdTotalCnt());
			// 땅밀림 조사대상지등록정보 등록
			String sldId = null;
			if(stripLandVO.getSldId() == null || stripLandVO.getSldId().equals("")) {
				sldId = lssLcpSvyStripLandService.insertSvySldRegInfo(sldMap);
				if(sldId == "duplication") {
					mv.addObject("status","fail");
					mv.addObject("message", "이미 해당연도에 등록한 조사대상지가 있습니다.");
					return mv;
				}
			}else {
				sdNmList = new String[]{"추가-경기-","추가-충북-","추가-충남-","추가-전북-","추가-전남-","추가-경북-","추가-경남-","추가-강원-"};
				sldId = stripLandVO.getSldId(); 
			}
			
 			for(int i = 0 ;i<sdCdList.length; i++) {
 				boolean delChk = false;
 				if(Integer.parseInt(sdCntList[i]) != 0) {
	 				if(Integer.parseInt(sdCntList[i]) < 0) {
	 					delChk = true;
	 					sdCntList[i] = sdCntList[i].replace("-", "");
	 				}else if(sdCntList[i].contains("+")) {
	 					sdCntList[i] = sdCntList[i].replace("+", ""); 
	 				}
					stripLandVO.setSdCd(sdCdList[i]);
					stripLandVO.setSdCnt(sdCntList[i]);
					stripLandVO.setLabel(sdNmList[i]);
					stripLandVO.setLabelNum("1");
					stripLandVO.setYear(yearCal);
					stripLandVO.setSldId(sldId);
					// 땅밀림 조사대상지 삭제
					if(delChk) {
						lssLcpSvyStripLandService.deleteSvySldInfo(stripLandVO);
//						lssLcpSvyStripLandService.updateRankDelInfo(stripLandVO);
					}else {
						// Gid 재세팅
//						stripLandVO.setCtrdCd(sdCdList[i]);
//						List<EgovMap> svysldGidInfo = lssLcpSvyStripLandService.selectSvysldGidInfo(stripLandVO);
//						for(int k=0;k<svysldGidInfo.size();k++) {
//							String gid = Integer.toString(k+1);
//							String uniqId = svysldGidInfo.get(k).get("uniqId").toString();
//							stripLandVO.setUniqId(uniqId);
//							stripLandVO.setGid(gid);
//							lssLcpSvyStripLandService.updateSvySldGid(stripLandVO);
//						}
						
						// 랭크 테이블 조사대상지정보 조회
						List<EgovMap> svySldInfoList = lssLcpSvyStripLandService.selectRankInfo(stripLandVO);
						lssLcpSvyStripLandService.updateRankInfo(stripLandVO);
						for(int j = 0; j < svySldInfoList.size(); j++) {
							String rank = svySldInfoList.get(j).get("rank").toString();
							rankList.add(rank);
							//stripLandVO.setGid(svySldInfoList.get(j).get("smid").toString());
							stripLandVO.setRank(rank);
							stripLandVO.setCtrdCd(sdCdList[i]);
							stripLandVO.setUniqId(svySldInfoList.get(j).get("uniqId").toString());
							stripLandVO.setStdgCd(svySldInfoList.get(j).get("stdgCd").toString());
							stripLandVO.setAddr(svySldInfoList.get(j).get("addr").toString());
							// 땅밀림 조사대상지 임상도 정보조회
							LssLcpSvyStripLandVO stripLandVO1 = lssLcpSvyStripLandService.selectSvySldImInfo(stripLandVO);
							// 땅밀림 조사대상지 입지도 정보조회
							LssLcpSvyStripLandVO stripLandVO2 = lssLcpSvyStripLandService.selectSvySldIjInfo(stripLandVO);
							// 땅밀림 조사대상지 지질도 정보조회
							LssLcpSvyStripLandVO stripLandVO3 = lssLcpSvyStripLandService.selectSvySldGlInfo(stripLandVO);
							if(stripLandVO1 != null) {
								stripLandVO.setFrtpCd(stripLandVO1.getFrtpCd());
							}
							if(stripLandVO2 != null) {
								stripLandVO.setSltpCd(stripLandVO2.getSltpCd());
								stripLandVO.setSibflrScs(stripLandVO2.getSibflrScs());
								stripLandVO.setSibflrStr(stripLandVO2.getSibflrStr());
								stripLandVO.setRockExdgr(stripLandVO2.getRockExdgr());
								stripLandVO.setSibflrCbs(stripLandVO2.getSibflrCbs());
								stripLandVO.setWteffDgr(stripLandVO2.getWteffDgr());
							}
							if(stripLandVO3 != null) {
								stripLandVO.setPrrckLarg(stripLandVO3.getPrrckLarg());
								stripLandVO.setPrrckMddl(stripLandVO3.getPrrckMddl());
							}
							// 땅밀림 조사대상지정보 등록
							lssLcpSvyStripLandService.insertSvySldInfo(stripLandVO);
							mv.addObject("status","success");
							mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
						}
					}
 				}
 			}
 			
 			lssLcpSvyStripLandService.updateSvySldRegInfo(sldId);
			stripLandVO.setWriter(user.getName());
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
			
			List<ZonalStatisticVO> resultList = null;
			
			String processId = SuperMapBasicUtils.getDatasetUuid();
			
			LssLcpSupMapUtils utils = new LssLcpSupMapUtils(processId);
			
			String creatYearRankTbl = "ta_rank".concat(yearCal+"_").concat(processId);//"tf_feis_lcp_rank".concat(Integer.toString(yearCal));
 			String rank = rankList.toString();
 			rank = rank.substring(1, rank.length()-1);
 			String creatYearRankWhere = "rank in(".concat(rank).concat(")");
 			
 			//고도값 생성
 			resultList = utils.createLssLcpRankElevation(processId,creatYearRankTbl,creatYearRankWhere);
 			lssLcpSvyStripLandService.updateSvySldElevInfo(resultList);
 			
 			//경사도값 생성
 			resultList = utils.createLssLcpRankSlope(processId,creatYearRankTbl,creatYearRankWhere);
 			lssLcpSvyStripLandService.updateSvySldSlopInfo(resultList);
 			
 			//토심값 생성
 			resultList = utils.createLssLcpRankSld(processId,creatYearRankTbl,creatYearRankWhere);
 			lssLcpSvyStripLandService.updateSvySldSldInfo(resultList);
 			
 			utils.closeWorkspace();
 			
 			Date finished = new Date();
 			
 			LOGGER.info("등록시작시간 : ".concat(sdf.format(now)));
 			LOGGER.info("등록완료시간 : ".concat(sdf.format(finished)));
 			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
	}
	
	/**
	 * 조사대상지 등록정보를 삭제한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/deleteSvySldRegInfo.do")
	public ModelAndView deleteSvySldRegInfo(LssLcpSvyStripLandVO stripLandVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			// 랭크 테이블 데이터 수정
			lssLcpSvyStripLandService.deleteSvySldInfo(stripLandVO);
			lssLcpSvyStripLandService.deleteSvySldRegInfo(stripLandVO);
			lssLcpSvyStripLandService.deleteRank(stripLandVO);
			lssLcpSvyStripLandService.deleteGvf(stripLandVO);
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
	 * 대상지 정보 수정화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sld/updateLssLcpSvySldInfoView.do")
	public String updateLssLcpSvySldInfoView(@ModelAttribute("stripLandVO") LssLcpSvyStripLandVO stripLandVO, ModelMap model) throws Exception{
		
		//임상코드를 코드정보로부터 조회		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI071");
		List<?> frtp_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frtpCdResult", frtp_result);//임상코드목록
		
		//지질(대)코드를 코드정보로부터 조회
		vo.setCodeId("FEI072");
		List<?> prrck_larg_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("prrckLargResult", prrck_larg_result);//지질(대)코드목록
		
		//지질(중)코드를 코드정보로부터 조회
		vo.setCodeId("FEI073");
		List<?> prrck_mddl_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("prrckMddlResult", prrck_mddl_result);//지질(대)코드목록
		
		//토양형코드를 코드정보로부터 조회
		vo.setCodeId("FEI074");
		List<?> sltp_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sltpResult", sltp_result);//지질(대)코드목록

		//토성코드를 코드정보로부터 조회
		vo.setCodeId("FEI075");
		List<?> sibscs_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sibscsResult", sibscs_result);//지질(대)코드목록
		
		//토양구조코드를 코드정보로부터 조회
		vo.setCodeId("FEI076");
		List<?> sidstrct_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sidstrctResult", sidstrct_result);//지질(대)코드목록
		
		//암노출도코드를 코드정보로부터 조회
		vo.setCodeId("FEI077");
		List<?> rckexd_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("rckexdResult", rckexd_result);//지질(대)코드목록
		
		//토양석력함량코드를 코드정보로부터 조회
		vo.setCodeId("FEI078");
		List<?> sibcbs_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("sibcbsResult", sibcbs_result);//지질(대)코드목록
		
		//풍화정도코드를 코드정보로부터 조회
		vo.setCodeId("FEI079");
		List<?> wteff_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("wteffResult", wteff_result);//지질(대)코드목록
	
		/* 조사대상지 정보조회*/
		LssLcpSvyStripLandVO svysldInfoDetailVO = lssLcpSvyStripLandService.selectSvySldInfoDetail(stripLandVO);
		model.addAttribute("result", svysldInfoDetailVO);
		
		return "sys/lss/lcp/sld/svySldInfoUpdt";
	}
	
	@RequestMapping(value = "/sys/lss/lcp/sld/selectLcpSvySldListExcel.do")	
    public ModelAndView selectLcpSvySldListExcel (@ModelAttribute("searchVO") LssLcpSvyStripLandVO searchVO,ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
    	HashMap<?, ?> _map = (HashMap<?, ?>)lssLcpSvyStripLandService.selectLcpSvySldListExcel(searchVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String fileNm = "조사대상지_".concat(formater.format(new Date()).toString());
    	String[] columnArray = {"rank","라벨","행정구역코드","주소","임상","지질(대)","지질(중)","토양형","토성","토양구조","암노출도","토양석력함량","풍화정도","평균고도(m)","평균토심(cm)","최소토심(cm)","최대토심(cm)","최저고도(m)","최대고도(m)","평균경사(º)","최소경사(º)","최대경사(º)"};
    	String[] columnVarArray = {"rank","label","stdgCd","addr","frtpCd","prrckLarg","prrckMddl","sltpCd","sibflrScs","sibflrStr","rockExdgr","sibflrCbs","wteffDgr","sldpAvg","sldpMin","sldpMax","evtAvg","evtMin","evtMax","slpAvg","slpMin","slpMax"};
    	
    	dataMap.put("columnArr", columnArray);
    	dataMap.put("columnVarArr", columnVarArray);
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}
//	
//	/**
//	 * 대상지 엑셀등록 모달팝업
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/sys/lss/lcp/sld/stripLandExcelPopup.do")
//	public String uploadStripLandExcelPopup() throws Exception {
//		return "sys/lss/lcp/sld/stripLandExcelPopup";
//	}
}
