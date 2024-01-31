package or.sabang.sys.vyt.frd.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookItemVO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookService;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class VytFrdFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "vytFrdFieldBookService")
	private VytFrdFieldBookService vytFrdFieldBookService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdFieldBookController.class);

	int cnt_total = 0;
	
	/**
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 임도타당성평가 공유방 목록조회
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectFrdFieldBookList.do")	
	public String selectVytFrdFieldBookList(@ModelAttribute("searchVO") VytFrdFieldBookVO searchVO, ModelMap model) throws Exception {
		
		LOGGER.info("VytFrdSelectFieldBookList controller");
		
		List<VytFrdFieldBookVO> list = null;
		
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
			searchVO.setSvy_year(vytFrdFieldBookService.selectVytFrdFieldBookMaxYear());
		}
		
		//회사명 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);
		
		//연도코드 조회
		List<?> year_result = vytFrdFieldBookService.selectVytFrdFieldBookYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = vytFrdFieldBookService.selectVytFrdFieldBookList(searchVO);
			cnt_total = vytFrdFieldBookService.selectVytFrdFieldBookListTotCnt(searchVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList", list);
		return "sys/vyt/frd/fbk/fieldBookList";		
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 상세조회
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectFieldBookDetail.do")
    public String selectFieldBookDetail(
    		@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO, HttpServletRequest req,
    		@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO,
    		@RequestParam("mst_id") String id,
    		Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", fieldBookVO.getPageUnit());
		searchMap.put("schpageIndex", fieldBookVO.getPageIndex());
		searchMap.put("schmst_partname", fieldBookVO.getMst_partname());
		searchMap.put("schsvy_year", fieldBookVO.getSvy_year());
		searchMap.put("schid", fieldBookVO.getId());
		searchMap.put("schmst_create_user", fieldBookVO.getMst_create_user());
		searchMap.put("schpageSubIndex", fieldBookVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		
		/* 공유방 상세정보조회*/
		VytFrdFieldBookVO result  = vytFrdFieldBookService.selectVytFrdFieldBookDetail(commandMap);
		model.addAttribute("result",result);
		
		searchVO.setMst_id(id);
		
		/* 공유방 조사데이터 리스트조회 */
		List<VytFrdStripLandVO> resultList = null;
		
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
		
		resultList = vytFrdFieldBookService.selectVytFrdFieldBookItemList(searchVO);
		
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
		
		int totCnt = vytFrdFieldBookService.selectVytFrdFieldBookItemListTotCnt(searchVO);
		
		result.setTotcnt(Integer.toString(totCnt));
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/vyt/frd/fbk/fieldBookDetail";
	}
	
	/**
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/insertCnrsSpcePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO,
			@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO, ModelMap model) throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM025");
		List<?> ogrnzt_result = cmmUseService.selectOgrnztIdDetail(vo);
		model.addAttribute("ogrnztCodeList", ogrnzt_result);

		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		// 구분
		comVO.setCodeId("FEI170");
		List<CmmnDetailCode>  frdRnk_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("frdRnk_result", frdRnk_result);
		
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
		
		
		List<EgovMap> sldRegList = vytFrdFieldBookService.selectSldRegInfo();
		model.addAttribute("sldRegList",sldRegList);
		
		
		return "sys/vyt/frd/fbk/insertCnrsSpcePopup";
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
	@RequestMapping(value = "/sys/vyt/frd/fbk/insertCnrsSpce.do")
    public ModelAndView insertCnrsSpce(
    		@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO,
    		@ModelAttribute("fieldBookItemVO") VytFrdFieldBookItemVO fieldBookItemVO,
    		HttpServletRequest req, Model model, BindingResult bindingResult) throws Exception {
		ModelAndView mv = null;
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		int regCnt = 0;
		
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			
			String mstId = null;
			try {
				String mst_partname = fieldBookVO.getMst_partname();
				String mst_password = fieldBookVO.getMst_password();
				int cnt = vytFrdFieldBookService.selectVytFrdFieldBookCheckMstName(mst_partname);
				if(mst_partname.isEmpty() != true && mst_password.isEmpty() != true) {
					if(cnt > 0) {
						mv.addObject("cnt", "fail");
						return mv;
					}else {
						mv.addObject("cnt", "success");
						fieldBookVO.setMst_admin_user(loginVO.getName());
						fieldBookVO.setMst_admin_id(loginVO.getUniqId());
						mstId = vytFrdFieldBookService.insertCnrsSpce(fieldBookVO);		//공유방 생성
						commandMap.put("id", mstId);
						fieldBookVO.setSvyId(mstId);
						vytFrdFieldBookService.insertCnrsSpceAuthorMgt(fieldBookVO);		// 생성된 공유방에 권한 부여
						commandMap.put("loginId", loginVO.getId());
						// 사업목록에 조회된 목록 불러오기 (smid)
						
						List<VytFrdStripLandVO> sldListDetail = vytFrdFieldBookService.selectSldInfo(fieldBookItemVO);
						List<Integer> smidList = new ArrayList<Integer>();
						for(int i=0; i<sldListDetail.size(); i++) {
							String smid = sldListDetail.get(i).getSmid();
							smidList.add(Integer.parseInt(smid));
							
							// fieldinfo 테이블에 memo랑 attr에 값 가공해서 넣어주기 (commandMap으로 넣어야지)
							
							String svy_attr = "["
									+ "{\"VALUE\":\""+sldListDetail.get(i).getSldlabel()+"\",\"NAME\":\"조사ID\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getFrdtype()+"\",\"NAME\":\"임도종류\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getYear()+"\",\"NAME\":\"조사연도\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getCompentauth()+"\",\"NAME\":\"관할청\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getSubcompentauth()+"\",\"NAME\":\"세부관할\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getSd()+"\",\"NAME\":\"시도\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getSgg()+"\",\"NAME\":\"시군구\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getEmd()+"\",\"NAME\":\"읍면동\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getRi()+"\",\"NAME\":\"리\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getJibun()+"\",\"NAME\":\"지번\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getLat()+"\",\"NAME\":\"위도\"},"
									+ "{\"VALUE\":\""+sldListDetail.get(i).getLon()+"\",\"NAME\":\"경도\"},"
									+ "{\"VALUE\":\""+"임도"+"\",\"NAME\":\"조사유형\"}"
									+ "]";
							
							String svy_memo = "{\"조사구분\":\""+fieldBookItemVO.getFrdRnk()+"\","
									+ "\"노선종류\":\""+sldListDetail.get(i).getRoutetype()+"\","
									+ "\"임도종류\":\""+sldListDetail.get(i).getFrdtype()+"\","
									+ "\"조사유형\":\""+"임도"+"\","
									+ "\"관할청\":\""+sldListDetail.get(i).getCompentauth()+"\","
									+ "\"예정거리\":\""+sldListDetail.get(i).getSchdst()+"\","
									+ "\"생태자연도\":\""+sldListDetail.get(i).getEcoNtrMap()+"\","
									+ "\"멸종위기종서식_현황\":\""+sldListDetail.get(i).getEndSpc()+"\","
									+ "\"특별산림보호종\":\""+sldListDetail.get(i).getSpcFrsPrt()+"\","
									+ "\"세부관할\":\""+sldListDetail.get(i).getSubcompentauth()+"\","
									+ "\"대상지관리_번호\":\""+sldListDetail.get(i).getSmid()+"\","
									+ "\"시점 경도\":\""+sldListDetail.get(i).getBpx()+"\","
									+ "\"시점 위도\":\""+sldListDetail.get(i).getBpy()+"\","
									+ "\"종점 경도\":\""+sldListDetail.get(i).getEpx1()+"\","
									+ "\"종점 위도\":\""+sldListDetail.get(i).getEpy1()+"\","
									+ "\"종점경도2\":\""+sldListDetail.get(i).getEpx2()+"\","
									+ "\"종점위도2\":\""+sldListDetail.get(i).getEpy2()+"\"}";
							
							commandMap.put("smid", smid);
							commandMap.put("svy_attr", svy_attr.replaceAll("null", ""));
							commandMap.put("svy_memo", svy_memo.replaceAll("null", ""));
							commandMap.put("svy_lon", sldListDetail.get(i).getLon());
							commandMap.put("svy_lat", sldListDetail.get(i).getLat());
							commandMap.put("svy_label", sldListDetail.get(i).getSldlabel());
							commandMap.put("svy_data", sldListDetail.get(i).getFrdLneWkt());
							
							vytFrdFieldBookService.insertStripLand(commandMap);
							
							regCnt++;
						}
						// 여기에 대상지 테이블에 있는 mst_id에 mst_id update쳐주기
						commandMap.put("smidList", smidList);
						vytFrdFieldBookService.updateNoMstId(commandMap);
						
						
						// svymemo : 노선종류, 예정거리, 시점경도, 시점위도, 종점경도1, 종점위도1, 종점경도2, 종점위도2
						// svyattr : 조사ID, frdtype(임도종류), 조사연도(creat_dt에서 YYYY), 관할청, 시도, 시군구, 읍면동, 리, 지번, 위도, 경도
						// svydata : line데이터에서 wkt좌표 point 중간좌표넣기  
						
						
						mv.addObject("status","success");
						String message = "전체 "+sldListDetail.size()+"건 중 "+regCnt+"건이 등록되었습니다.\n총 : "+
								sldListDetail.size()+" 건\n등록 : "+regCnt+" 건\n실패 : "+(sldListDetail.size()-regCnt)+" 건\n";
						mv.addObject("message", message);
					}
				}else {
					mv.addObject("nnt", "fail");
				}
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				vytFrdFieldBookService.deleteCnrsSpce(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				vytFrdFieldBookService.deleteCnrsSpce(commandMap);
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
	 * @description 공유방 삭제
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/deleteCnrsSpce.do")
	public ModelAndView deleteCnrsSpce(@RequestParam(value="id") String id,
			ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			
			
			List<Object> smidList = vytFrdFieldBookService.selectDeleteItems(id);
			commandMap.put("smidList", smidList);
			vytFrdFieldBookService.deleteCnrsSpce(commandMap);
			vytFrdFieldBookService.deleteCnrsSpceAllItem(commandMap);
			//220314 공유방 삭제시 조사완료 데이터 삭제X  
//			vytFrdFieldBookService.deleteCnrsSpceComptItem(commandMap);
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
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 대상지 등록 팝업
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/insertCnrsSpceSldPopup.do")
	public String insertCnrsSpceSldPopup(@RequestParam(value="mst_id") String mst_id, 
			@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO,
			@RequestParam(value="mst_create_user") String mst_create_user, ModelMap model) throws Exception {

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
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		comVO.setCodeId("FEI170");
		List<CmmnDetailCode>  frdRnk_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("frdRnk_result", frdRnk_result);
		
		List<EgovMap> sldRegList = vytFrdFieldBookService.selectSldRegInfo();
		model.addAttribute("sldRegList",sldRegList);
		
		model.addAttribute("mst_id",mst_id);
		model.addAttribute("mst_create_user",mst_create_user);
		
		return "sys/vyt/frd/fbk/insertCnrsSpceSldPopup";
	}
	
	/**
	 * @param id
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 대상지 등록
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/insertCnrsSpceSld.do")
	public ModelAndView insertCnrsSpceSld(
			@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO,
    		@ModelAttribute("fieldBookItemVO") VytFrdFieldBookItemVO fieldBookItemVO,
    		HttpServletRequest req, Model model, BindingResult bindingResult) throws Exception {
		ModelAndView mv = null;
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		int regCnt = 0;
		
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			try {
				String mstId = fieldBookItemVO.getMst_id();
				commandMap.put("id", mstId);
				commandMap.put("loginId", loginVO.getId());
				
				List<VytFrdStripLandVO> sldListDetail = vytFrdFieldBookService.selectSldInfo(fieldBookItemVO);
				
				List<Integer> smidList = new ArrayList<Integer>();
				for(int i=0; i<sldListDetail.size(); i++) {
					String smid = sldListDetail.get(i).getSmid();
					smidList.add(Integer.parseInt(smid));
					
					// fieldinfo 테이블에 memo랑 attr에 값 가공해서 넣어주기 (commandMap으로 넣어야지)
					
					String svy_attr = "["
							+ "{\"VALUE\":\""+sldListDetail.get(i).getSldlabel()+"\",\"NAME\":\"조사ID\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getFrdtype()+"\",\"NAME\":\"임도종류\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getYear()+"\",\"NAME\":\"조사연도\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getCompentauth()+"\",\"NAME\":\"관할청\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getSubcompentauth()+"\",\"NAME\":\"세부관할\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getSd()+"\",\"NAME\":\"시도\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getSgg()+"\",\"NAME\":\"시군구\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getEmd()+"\",\"NAME\":\"읍면동\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getRi()+"\",\"NAME\":\"리\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getJibun()+"\",\"NAME\":\"지번\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getLat()+"\",\"NAME\":\"위도\"},"
							+ "{\"VALUE\":\""+sldListDetail.get(i).getLon()+"\",\"NAME\":\"경도\"},"
							+ "{\"VALUE\":\""+"임도"+"\",\"NAME\":\"조사유형\"}"
							+ "]";
					
					String svy_memo = "{\"조사구분\":\""+fieldBookItemVO.getFrdRnk()+"\","
							+ "\"노선종류\":\""+sldListDetail.get(i).getRoutetype()+"\","
							+ "\"임도종류\":\""+sldListDetail.get(i).getFrdtype()+"\","
							+ "\"조사유형\":\""+"임도"+"\","
							+ "\"관할청\":\""+sldListDetail.get(i).getCompentauth()+"\","
							+ "\"예정거리\":\""+sldListDetail.get(i).getSchdst()+"\","
							+ "\"생태자연도\":\""+sldListDetail.get(i).getEcoNtrMap()+"\","
							+ "\"멸종위기종서식_현황\":\""+sldListDetail.get(i).getEndSpc()+"\","
							+ "\"특별산림보호종\":\""+sldListDetail.get(i).getSpcFrsPrt()+"\","
							+ "\"세부관할\":\""+sldListDetail.get(i).getSubcompentauth()+"\","
							+ "\"대상지관리_번호\":\""+sldListDetail.get(i).getSmid()+"\","
							+ "\"시점 경도\":\""+sldListDetail.get(i).getBpx()+"\","
							+ "\"시점 위도\":\""+sldListDetail.get(i).getBpy()+"\","
							+ "\"종점 경도\":\""+sldListDetail.get(i).getEpx1()+"\","
							+ "\"종점 위도\":\""+sldListDetail.get(i).getEpy1()+"\","
							+ "\"종점경도2\":\""+sldListDetail.get(i).getEpx2()+"\","
							+ "\"종점위도2\":\""+sldListDetail.get(i).getEpy2()+"\"}";
					
					commandMap.put("smid", smid);
					commandMap.put("svy_attr", svy_attr.replaceAll("null", ""));
					commandMap.put("svy_memo", svy_memo.replaceAll("null", ""));
					commandMap.put("svy_lon", sldListDetail.get(i).getLon());
					commandMap.put("svy_lat", sldListDetail.get(i).getLat());
					commandMap.put("svy_label", sldListDetail.get(i).getSldlabel());
					commandMap.put("svy_data", sldListDetail.get(i).getFrdLneWkt());
					
					vytFrdFieldBookService.insertStripLand(commandMap);
					
					regCnt++;
				}
				// 여기에 대상지 테이블에 있는 mst_id에 mst_id update쳐주기
				commandMap.put("smidList", smidList);
				vytFrdFieldBookService.updateNoMstId(commandMap);
				
				
				// svymemo : 노선종류, 예정거리, 시점경도, 시점위도, 종점경도1, 종점위도1, 종점경도2, 종점위도2
				// svyattr : 조사ID, frdtype(임도종류), 조사연도(creat_dt에서 YYYY), 관할청, 시도, 시군구, 읍면동, 리, 지번, 위도, 경도
				// svydata : line데이터에서 wkt좌표 point 중간좌표넣기  
				
				
				mv.addObject("status","success");
				String message = "전체 "+sldListDetail.size()+"건 중 "+regCnt+"건이 등록되었습니다.\n총 : "+
						sldListDetail.size()+" 건\n등록 : "+regCnt+" 건\n실패 : "+(sldListDetail.size()-regCnt)+" 건\n";
				mv.addObject("message", message);
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
	 * @description 공유방 단건 상세조회
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectFieldBookDetailOne.do")
    public String selectFieldBookDetailOne(@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO,
    		@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO,
    		Model model, HttpServletRequest req) throws Exception {
		
		String id = searchVO.getMst_id();
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", fieldBookVO.getPageUnit());
		searchMap.put("schpageIndex", fieldBookVO.getPageIndex());
		searchMap.put("schmst_partname", fieldBookVO.getMst_partname());
		searchMap.put("schsvy_year", fieldBookVO.getSvy_year());
		searchMap.put("schid", fieldBookVO.getId());
		searchMap.put("schmst_create_user", fieldBookVO.getMst_create_user());
		searchMap.put("schpageSubIndex", fieldBookVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		// 공유방 단건 상세조회
		VytFrdFieldBookItemVO result = vytFrdFieldBookService.selectFieldBookDetailOne(searchVO);
		result.setMst_id(id);
		
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if(result.getBpx() != null && !result.getBpx().equals("")) {
			dmsformal.setDmsLon(result.getBpx());
			result.setBpx(dmsformal.getDmsLon());
		}
		if(result.getBpy() != null && !result.getBpy().equals("")) {
			dmsformal.setDmsLat(result.getBpy());
			result.setBpy(dmsformal.getDmsLat());
		}
		if(result.getEpx1() != null && !result.getEpx1().equals("")) {
			dmsformal.setDmsLon(result.getEpx1());
			result.setEpx1(dmsformal.getDmsLon());
		}
		if(result.getEpy1() != null && !result.getEpy1().equals("")) {
			dmsformal.setDmsLat(result.getEpy1());
			result.setEpy1(dmsformal.getDmsLat());
		}
		if(result.getEpx2() != null && !result.getEpx2().equals("")) {
			dmsformal.setDmsLon(result.getEpx2());
			result.setEpx2(dmsformal.getDmsLon());
		}
		if(result.getEpy2() != null && !result.getEpy2().equals("")) {
			dmsformal.setDmsLat(result.getEpy2());
			result.setEpy2(dmsformal.getDmsLat());
		}
		
		model.addAttribute("result", result);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		commandMap.put("id", Integer.parseInt(id));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		model.addAttribute("mstId", id);
		
		/* 공유방 권한 확인 */
		String access = vytFrdFieldBookService.selectAuthorCheck(commandMap);
		model.addAttribute("access",access);
		
		return "sys/vyt/frd/fbk/fieldBookDetailOne";
	}
	
	/**
	 * @param searchVO
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정페이지 이동
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/updateFieldBookViewOne.do")
	public String updateFieldBookViewOne(@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO,
			@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO, HttpServletRequest req,
			Model model) throws Exception {
		
		
		String id = searchVO.getMst_id();
		model.addAttribute("mstId", id);
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", fieldBookVO.getPageUnit());
		searchMap.put("schpageIndex", fieldBookVO.getPageIndex());
		searchMap.put("schmst_partname", fieldBookVO.getMst_partname());
		searchMap.put("schsvy_year", fieldBookVO.getSvy_year());
		searchMap.put("schid", fieldBookVO.getId());
		searchMap.put("schmst_create_user", fieldBookVO.getMst_create_user());
		searchMap.put("schpageSubIndex", fieldBookVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		
		VytFrdFieldBookItemVO result = vytFrdFieldBookService.selectFieldBookDetailOne(searchVO);
		result.setMst_id(id);
		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		AdministZoneVO adminVO = new AdministZoneVO();
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		// degree to dms
		String bp = result.getBpy()+" "+result.getBpx();
		String ep1 = result.getEpy1()+" "+result.getEpx1();
		String ep2 = result.getEpy2()+" "+result.getEpx2();
		
		String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcherBp = pattern.matcher(bp);
		Matcher matcherEp1 = pattern.matcher(ep1);
		Matcher matcherEp2 = pattern.matcher(ep2);
		
		while (matcherBp.find()) {
		    String bpyD = matcherBp.group(1);
		    String bpyM = matcherBp.group(2);
		    String bpyS = matcherBp.group(3);
		    
		    String bpxD = matcherBp.group(5);
		    String bpxM = matcherBp.group(6);
		    String bpxS = matcherBp.group(7);
		    
		    if(bpyD != null && !bpyD.equals("")) {
		    	dmsformal.setDmsLatDeg(bpyD);
		    	result.setBpyD(dmsformal.getDmsLatDeg());
		    }
		    if(bpyM != null && !bpyM.equals("")) {
		    	dmsformal.setDmsLatMin(bpyM);
		    	result.setBpyM(dmsformal.getDmsLatMin());
		    }
		    if(bpyS != null && !bpyS.equals("")) {
		    	dmsformal.setDmsLatSec(bpyS);
		    	result.setBpyS(dmsformal.getDmsLatSec());
		    }
		    if(bpxD != null && !bpxD.equals("")) {
		    	dmsformal.setDmsLonDeg(bpxD);
		    	result.setBpxD(dmsformal.getDmsLonDeg());
		    }
		    if(bpxM != null && !bpxM.equals("")) {
		    	dmsformal.setDmsLonMin(bpxM);
		    	result.setBpxM(dmsformal.getDmsLonMin());
		    }
		    if(bpxS != null && !bpxS.equals("")) {
		    	dmsformal.setDmsLonSec(bpxS);
		    	result.setBpxS(dmsformal.getDmsLonSec());
		    }
		}
		
		while (matcherEp1.find()) {
			String epy1D = matcherEp1.group(1);
			String epy1M = matcherEp1.group(2);
			String epy1S = matcherEp1.group(3);
			
			String epx1D = matcherEp1.group(5);
			String epx1M = matcherEp1.group(6);
			String epx1S = matcherEp1.group(7);
			
			if(epy1D != null && !epy1D.equals("")) {
				dmsformal.setDmsLatDeg(epy1D);
				result.setEpy1D(dmsformal.getDmsLatDeg());
			}
			if(epy1M != null && !epy1M.equals("")) {
				dmsformal.setDmsLatMin(epy1M);
				result.setEpy1M(dmsformal.getDmsLatMin());
			}
			if(epy1S != null && !epy1S.equals("")) {
				dmsformal.setDmsLatSec(epy1S);
				result.setEpy1S(dmsformal.getDmsLatSec());
			}
			if(epx1D != null && !epx1D.equals("")) {
				dmsformal.setDmsLonDeg(epx1D);
				result.setEpx1D(dmsformal.getDmsLonDeg());
			}
			if(epx1M != null && !epx1M.equals("")) {
				dmsformal.setDmsLonMin(epx1M);
				result.setEpx1M(dmsformal.getDmsLonMin());
			}
			if(epx1S != null && !epx1S.equals("")) {
				dmsformal.setDmsLonSec(epx1S);
				result.setEpx1S(dmsformal.getDmsLonSec());
			}
		}
		
		while (matcherEp2.find()) {
			String epy2D = matcherEp2.group(1);
			String epy2M = matcherEp2.group(2);
			String epy2S = matcherEp2.group(3);
			
			String epx2D = matcherEp2.group(5);
			String epx2M = matcherEp2.group(6);
			String epx2S = matcherEp2.group(7);
			
			if(epy2D != null && !epy2D.equals("")) {
				dmsformal.setDmsLatDeg(epy2D);
				result.setEpy2D(dmsformal.getDmsLatDeg());
			}
			if(epy2M != null && !epy2M.equals("")) {
				dmsformal.setDmsLatMin(epy2M);
				result.setEpy2M(dmsformal.getDmsLatMin());
			}
			if(epy2S != null && !epy2S.equals("")) {
				dmsformal.setDmsLatSec(epy2S);
				result.setEpy2S(dmsformal.getDmsLatSec());
			}
			if(epx2D != null && !epx2D.equals("")) {
				dmsformal.setDmsLonDeg(epx2D);
				result.setEpx2D(dmsformal.getDmsLonDeg());
			}
			if(epx2M != null && !epx2M.equals("")) {
				dmsformal.setDmsLonMin(epx2M);
				result.setEpx2M(dmsformal.getDmsLonMin());
			}
			if(epx2S != null && !epx2S.equals("")) {
				dmsformal.setDmsLonSec(epx2S);
				result.setEpx2S(dmsformal.getDmsLonSec());
			}
		}
		model.addAttribute("result", result);
		
		// 노선종류 조회
		vo.setCodeId("FEI169");
		List<?> routeTypeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("routeTypeList", routeTypeList);
		// 조사구분 조회
		vo.setCodeId("FEI170");
		List<?> frdRnkList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frdRnkList", frdRnkList);
		// 임도종류 조회
		vo.setCodeId("FEI171");
		List<?> frdTypeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frdTypeList", frdTypeList);
		//국유림/민유림
		vo.setCodeId("FEI172");
		List<CmmnDetailCode> subCompentauthList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("subCompentauthList",subCompentauthList);
		
		HashMap<String, String> compentauthType = new HashMap<String, String>();
		//관할2목록코드를 코드정보로부터 조회
		String compentauth = null;
		if(result.getCompentauth() != null) {
			
			if(result.getCompentauth().contains("부청")) {
				if(result.getCompentauth().equals("북부청")) {
					compentauth = "REG18";
				}else if(result.getCompentauth().equals("동부청")) {
					compentauth = "REG19";
				}else if(result.getCompentauth().equals("남부청")) {
					compentauth = "REG20";
				}else if(result.getCompentauth().equals("중부청")) {
					compentauth = "REG21";
				}else if(result.getCompentauth().equals("서부청")) {
					compentauth = "REG22";
				}else {
					compentauth = result.getCompentauth().toString();
				}
				
				// 관할 조회
				vo.setCodeId("FEI167");
				List<?> regionCodeList = cmmUseService.selectCmmCodeDetail(vo);
				model.addAttribute("regionCodeList", regionCodeList);
				
				vo.setCodeId(compentauth);
				List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
				model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
				
				compentauthType.put("compentauthType", "국유림");
			}else {
				//시도코드 조회		
				List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
				model.addAttribute("regionCodeList",sdCodeList);
				
				//시군구코드 조회
				if(result.getCompentauth() != null && !result.getCompentauth().trim().isEmpty()) {
					adminVO.setSdNm(result.getCompentauth());		
					List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
					model.addAttribute("region2CodeList",sggCodeList);
				}
				
				compentauthType.put("compentauthType", "민유림");
			}
		}
		model.addAttribute("compentauthType",compentauthType);
		
		
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
			adminVO.setSggNm(result.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(result.getSvyEmd() != null && !result.getSvyEmd().trim().isEmpty()) {
			adminVO.setEmdNm(result.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		return "sys/vyt/frd/fbk/fieldBookUpdtOne";
	}
	
	
	/**
	 * @param searchVO
	 * @param id 
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 공유방 단건 수정
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/updateFieldBookDetailOne.do")
	public ModelAndView updateFieldBookDetailOne(@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO,
			@ModelAttribute("stripLandVO") VytFrdStripLandVO stripLandVO,
			ModelMap model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			String compentauth = null;
			if(searchVO.getCompentauth() != null) {
				if(searchVO.getCompentauth().equals("REG18")) {
					compentauth = "북부청";
				}else if(searchVO.getCompentauth().equals("REG19")) {
					compentauth = "동부청";
				}else if(searchVO.getCompentauth().equals("REG20")) {
					compentauth = "남부청";
				}else if(searchVO.getCompentauth().equals("REG21")) {
					compentauth = "중부청";
				}else if(searchVO.getCompentauth().equals("REG22")) {
					compentauth = "서부청";
				}else compentauth = searchVO.getCompentauth().toString();
			}
			
			String svy_attr = "["
					+ "{\"VALUE\":\""+searchVO.getSvyId()+"\",\"NAME\":\"조사ID\"},"
					+ "{\"VALUE\":\""+searchVO.getFrdtype()+"\",\"NAME\":\"임도종류\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyYear()+"\",\"NAME\":\"조사연도\"},"
					+ "{\"VALUE\":\""+compentauth+"\",\"NAME\":\"관할청\"},"
					+ "{\"VALUE\":\""+searchVO.getSubcompentauth()+"\",\"NAME\":\"세부관할\"},"
					+ "{\"VALUE\":\""+searchVO.getSvySd()+"\",\"NAME\":\"시도\"},"
					+ "{\"VALUE\":\""+searchVO.getSvySgg()+"\",\"NAME\":\"시군구\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyEmd()+"\",\"NAME\":\"읍면동\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyRi()+"\",\"NAME\":\"리\"},"
					+ "{\"VALUE\":\""+searchVO.getSvyJibun()+"\",\"NAME\":\"지번\"},"
					+ "{\"VALUE\":\""+searchVO.getLat()+"\",\"NAME\":\"위도\"},"
					+ "{\"VALUE\":\""+searchVO.getLon()+"\",\"NAME\":\"경도\"},"
					+ "{\"VALUE\":\""+"임도"+"\",\"NAME\":\"조사유형\"}"
					+ "]";
			searchVO.setSvy_attr(svy_attr);
			
			HashMap<String, Object> projMap = new HashMap<>();
			projMap.put("bpx",searchVO.getBpx());
			projMap.put("bpy",searchVO.getBpy());
			
			projMap.put("epx1",searchVO.getEpx1());
			projMap.put("epy1",searchVO.getEpy1());
			
			projMap.put("epx2",searchVO.getEpx2());
			projMap.put("epy2",searchVO.getEpy2());
			
			
			
			// 4326 to 5186
//			List<EgovMap> projList = vytFrdFieldBookService.selectVytFrdProjBessel(projMap);
//			
//			if(!projList.get(0).isEmpty()) {
//				if(projList.get(0).containsKey("bpx")) {
//					searchVO.setSvyLon(projList.get(0).get("bpx").toString());
//				}
//				if(projList.get(0).containsKey("bpy")) {
//					searchVO.setSvyLat(projList.get(0).get("bpy").toString());
//				}
//			}
			
			vytFrdFieldBookService.updateFieldBookDetailOne(searchVO);
			
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
	@RequestMapping(value = "/sys/vyt/frd/fbk/deleteFieldBookDetailOne.do")
	public ModelAndView deleteFieldBookDetailOne(@ModelAttribute("searchVO") VytFrdFieldBookItemVO searchVO, ModelMap model, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("gid", searchVO.getGid());
			
			vytFrdFieldBookService.deleteFieldBookDetailOne(commandMap);
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
	 * @description 공유방 권한관리 페이지 이동
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectFieldbookAuthoryView.do")
    public String selectFieldbookAuthoryView(
    		@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO,
    		@RequestParam(value="mst_id") String id, Model model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", fieldBookVO.getPageUnit());
		searchMap.put("schpageIndex", fieldBookVO.getPageIndex());
		searchMap.put("schmst_partname", fieldBookVO.getMst_partname());
		searchMap.put("schsvy_year", fieldBookVO.getSvy_year());
		searchMap.put("schid", fieldBookVO.getId());
		searchMap.put("schmst_create_user", fieldBookVO.getMst_create_user());
		searchMap.put("schpageSubIndex", fieldBookVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		/* 공유방 권한자 조회 */
		List<VytFrdFieldBookVO> result = vytFrdFieldBookService.selectCnrsAuthorList(commandMap);
		
		model.addAttribute("resultList", result);
		model.addAttribute("mstId", id);
		return "sys/vyt/frd/fbk/fieldBookAuthorManage";
	}
	
	
	/**
	 * 부서별 회원목록 트리 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectAuthorUserPopup.do")
	public String selectAuthorUserPopup(VytFrdFieldBookVO vo,ModelMap model) throws Exception{
		
		List<String> authorEsntlIdList = Arrays.asList(vo.getAuthorEsntlIdList());
		
		List<EgovMap> deptList = vytFrdFieldBookService.selectDeptInfoList();
		List<EgovMap> deptMberList = vytFrdFieldBookService.selectDeptCreatList();
		
		model.addAttribute("dept_list",deptList);
		model.addAttribute("dept_mber_list", deptMberList);
		model.addAttribute("authorEsntlIdList", authorEsntlIdList);
		
		
		return "sys/vyt/frd/fbk/searchAuthorUserPopup";
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
	@RequestMapping(value = "/sys/vyt/frd/fbk/updateCnrsSpceAuthorMgt.do")
	public ModelAndView updateCnrsSpceAuthorMgt(
			@ModelAttribute("fieldBookVO") VytFrdFieldBookVO fieldBookVO, 
    		HttpServletRequest req,
    		BindingResult bindingResult,
			ModelMap model) throws Exception {
		
		String authorEsntlIdList[] = req.getParameterValues("authorEsntlId");
		fieldBookVO.setAuthorEsntlIdList(authorEsntlIdList);
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			String id = req.getParameter("schmst_id");
			fieldBookVO.setId(id);
			
			vytFrdFieldBookService.updateCnrsSpceAuthorMgt(fieldBookVO);
			mv.addObject("status","success");
			mv.addObject("message",egovMessageSource.getMessage("success.common.update"));
		} catch(Exception e) {
			e.printStackTrace();
			mv.addObject("status","failed");
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
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectMberList.do")
	public ModelAndView selectMberList(
			  @RequestParam(value="searchKeyword") String searchKeyword,
			  @RequestParam(value="searchCondition") String searchCondition,
			ModelMap model, HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			VytFrdFieldBookVO vo = new VytFrdFieldBookVO();
			vo.setSearchKeyword(searchKeyword);
			vo.setSearchCondition(searchCondition);
			
			List<EgovMap> list = vytFrdFieldBookService.selectMberList(vo);
			
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
	 * 대상지 개수 조회
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectSldInfoCnt.do")
	public ModelAndView selectSldInfoCnt(
			ModelMap model,
			@ModelAttribute("fieldBookItemVO") VytFrdFieldBookItemVO fieldBookItemVO) {
		
			ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			int cnt = 0;
			cnt = vytFrdFieldBookService.selectSldInfoCnt(fieldBookItemVO);
			
			mv.addObject("cnt",cnt);
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
     * 관할청을 보여준다.
     * @param adminVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/sys/vyt/frd/fbk/selectCompentAuth.do")
	public ModelAndView selectAdministZoneSigngu(@RequestParam("compentType") String compentType, @RequestParam("compentValue") String compentValue) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		AdministZoneVO adminVO = new AdministZoneVO();
		
		if(compentType.equals("compentAuth1")) {
			if(compentValue.equals("국유림")) {
				// 관할구분
				comVO.setCodeId("FEI167");
				List<CmmnDetailCode> list = cmmUseService.selectCmmCodeDetail(comVO);
				mv.addObject("result",list);
			}else if(compentValue.equals("민유림")) {
				//시도코드 조회		
				List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
				mv.addObject("result",sdCodeList);
			}
		}else if(compentType.equals("compentAuth2")) {
			if(compentValue.startsWith("REG") || compentValue.contains("부청")) {
				
				if(compentValue.equals("북부청")) {
					compentValue = "REG18";
				}else if(compentValue.equals("동부청")) {
					compentValue = "REG19";
				}else if(compentValue.equals("남부청")) {
					compentValue = "REG20";
				}else if(compentValue.equals("중부청")) {
					compentValue = "REG21";
				}else if(compentValue.equals("서부청")) {
					compentValue = "REG22";
				}
				
				comVO.setCodeId(compentValue);
				List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(comVO);
				mv.addObject("result", region2_result);//관할2목록코드목록
			}else {
				adminVO.setSdNm(compentValue);		
				List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
				mv.addObject("result",sggCodeList);
			}
			
		}
		
		return mv;
	}

}
