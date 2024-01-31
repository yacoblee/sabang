package or.sabang.sys.lss.cnl.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.LocImgInfoVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptService;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class LssCnlSvyComptController {
	
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
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
	@Resource(name = "lssCnlSvyComptService") 	
	private LssCnlSvyComptService lssCnlSvyComptService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.temp");
    private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssCnlSvyComptController.class);
	

	/**
	 * 조사완료목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/selectCnlSvyComptList.do")	
    public String selectCnlSvyComptList (@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {
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
			searchVO.setSvyYear(lssCnlSvyComptService.selectCnlSvyComptMaxYear());
		}
		Date date = new Date();
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(lssCnlSvyComptService.selectCnlSvyComptMaxMonth());
//			searchVO.setSvyMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = lssCnlSvyComptService.selectCnlSvyComptYear();
		model.addAttribute("yearCodeList", year_result);
		
		//월코드 조회
		vo.setCodeId("FEI045");
		vo.setCodeDc("Cldr");
		List<?> month_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("monthCodeList", month_result);
		vo.setCodeDc("");

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
		
		//관할1목록코드를 코드정보로부터 조회
//		vo.setCodeId("FEI001");
//		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
//		if(searchVO.getSvyRegion1() != null) {
//			vo.setCodeId(searchVO.getSvyRegion1());
//			List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
//			model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
//		}
		
		//실태조사 필요성 코드조회
//		vo.setCodeId("FEI030");
//		List<?> ncssty_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("ncsstyCodeList", ncssty_result);
//		
		List<LssCnlSvyComptVO> SvyComptList = lssCnlSvyComptService.selectCnlSvyComptList(searchVO);
		model.addAttribute("resultList", SvyComptList);
	
		int totCnt = lssCnlSvyComptService.selectCnlSvyComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		List<String> accessList = lssCnlSvyComptService.selectAuthorCnrsList(commandMap);	
		model.addAttribute("accessList", accessList);
				
		return "sys/lss/cnl/sct/svyComptList";		
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/selectCnlSvyComptDetail.do")
	public String selectCnlSvyComptDetail(@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		LssCnlSvyComptVO vo = lssCnlSvyComptService.selectCnlSvyComptDetail(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		if(vo.getSvyLon() != null && !vo.getSvyLon().equals("")) {
			dmsformal.setDmsLon(vo.getSvyLon());
			vo.setSvyLon(dmsformal.getDmsLon());
		}
		if(vo.getSvyLat() != null && !vo.getSvyLat().equals("")) {
			dmsformal.setDmsLat(vo.getSvyLat());
			vo.setSvyLat(dmsformal.getDmsLat());
		}
		
		// 항공사진 따옴표 제거
		if(!vo.getArlphoto1().equals("{}")) {
			vo.setArlphoto1("/storage/fieldBook"+vo.getArlphoto1());
		}else {
			vo.setArlphoto1("");
		}
		if(!vo.getArlphoto2().equals("{}")) {
			vo.setArlphoto2("/storage/fieldBook"+vo.getArlphoto2());
		}else {
			vo.setArlphoto2("");
		}
		vo.setArlphoto1(vo.getArlphoto1().replaceAll("\"", ""));
		vo.setArlphoto2(vo.getArlphoto2().replaceAll("\"", ""));
		
		// 도면해석 따옴표 제거
		if(!vo.getRsltphoto1().equals("{}")) {
			vo.setRsltphoto1("/storage/fieldBook"+vo.getRsltphoto1());
		}else {
			vo.setRsltphoto1("");
		}
		if(!vo.getRsltphoto2().equals("{}")) {
			vo.setRsltphoto2("/storage/fieldBook"+vo.getRsltphoto2());
		}else {
			vo.setRsltphoto2("");
		}
		if(!vo.getRsltphoto3().equals("{}")) {
			vo.setRsltphoto3("/storage/fieldBook"+vo.getRsltphoto3());
		}else {
			vo.setRsltphoto3("");
		}
		vo.setRsltphoto1(vo.getRsltphoto1().replaceAll("\"", ""));
		vo.setRsltphoto2(vo.getRsltphoto2().replaceAll("\"", ""));
		vo.setRsltphoto3(vo.getRsltphoto3().replaceAll("\"", ""));
		
		List<String> cnlBasisList = new ArrayList<String>();
		
		if(vo.getCnlSlctRn1() != null) {
			String cnlSlctRn1 = vo.getCnlSlctRn1().toString();
			if(cnlSlctRn1.equals("1")) cnlBasisList.add("제13조제2항제1호");
		}
		
		if(vo.getCnlSlctRn2() != null) {
			String cnlSlctRn2 = vo.getCnlSlctRn2().toString();
			if(cnlSlctRn2.equals("2")) cnlBasisList.add("제13조제2항제2호");
		}
		
		if(vo.getCnlSlctRn3() != null) {
			String cnlSlctRn3 = vo.getCnlSlctRn3().toString();
			if(cnlSlctRn3.equals("3")) cnlBasisList.add("제13조제2항제3호");
		}
		
		if(vo.getCnlSlctRn4() != null) {
			String cnlSlctRn4 = vo.getCnlSlctRn4().toString();
			if(cnlSlctRn4.equals("4")) cnlBasisList.add("제13조제2항제4호");
		}
		String cnlBasis = StringUtils.join(cnlBasisList, ", ");
		model.addAttribute("cnlBasis",cnlBasis);
		
		org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
		JSONParser parser = new JSONParser();
		Object obj;
		String photo = null;
		if(vo.getPhoto() != null && vo.getPhoto().length() > 2) {
			photo = vo.getPhoto().toString();
			obj = parser.parse(photo);
			jsonArray = (org.json.simple.JSONArray)obj;
			model.addAttribute("orginl_photo_result", jsonArray);
		}	
		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
			jsonArray = new org.json.simple.JSONArray();
	        photo = vo.getPhotoTag().toString();
	        JSONArray photoArr = new JSONArray(new JSONTokener(photo));
	        JSONArray sortedJsonArray = new JSONArray();
//	        Pattern numChk = Pattern.compile("^[\s]");
	        
	        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	        
	        for (int i = 0; i < photoArr.length(); i++) {
//	        	Matcher matcher = numChk.matcher(photoArr.getJSONObject(i).get("TAG").toString());
//	        	if(matcher.find()) {
	        		jsonValues.add(photoArr.getJSONObject(i));	        		
//	        	};
	        }
	        if(jsonValues.size() > 0) {
	        	Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        		private static final String KEY_NUM = "TAG";
	        		@Override
	        		public int compare(JSONObject a, JSONObject b) {
	        			String valA = "";
	        			String valB = "";
	        			try {
	        				valA = (String) a.get(KEY_NUM);
	        				valB = (String) b.get(KEY_NUM);
	        			}
	        			catch (JSONException e) {
	        				e.printStackTrace();
	        			}
	        	
        				return valA.compareTo(valB);
	        		}
	        	});
	        	
	        	int j = -1;
	        	int photoSize = jsonValues.size();
	        	for (int i = 0; i < photoSize; i++) {
	        		if(j != -1) {
	        			if(jsonValues.get(j).get("TAG").toString().isEmpty()) {
	        				jsonValues.add(jsonValues.get(j));
	        				jsonValues.remove(j);
	        			}
	        		}else {
	        			if(jsonValues.get(i).get("TAG").toString().isEmpty()) {
	        				j = i;
	        				jsonValues.add(jsonValues.get(i));
	        				jsonValues.remove(i);
	        			}	        			
	        		}
	        	}	        	
	        	for (int i = 0; i < jsonValues.size(); i++) {
	        		sortedJsonArray.put(jsonValues.get(i));
	        	}	        	

	        	obj = parser.parse(sortedJsonArray.toString());
	        	jsonArray = (org.json.simple.JSONArray)obj;
	        	model.addAttribute("photo_result", jsonArray);
	        }
	        
		} 
		
		// 위치도
		org.json.simple.JSONArray lcmapArr = new org.json.simple.JSONArray();
		if(vo.getLcMap().length() > 2) {				
			String lcmap = vo.getLcMap().toString();
			obj = parser.parse(lcmap);
			lcmapArr = (org.json.simple.JSONArray)obj;
			model.addAttribute("lcmap_result", lcmapArr);
		}		
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		String access = lssCnlSvyComptService.selectAuthorCheck(commandMap);
				
		model.addAttribute("access", access);			
		model.addAttribute("result", vo);			
		return "sys/lss/cnl/sct/svyComptDetail";
	}
	
	/**
	 * 조사완료지 수정화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateCnlSvyComptView.do")
	public String updateLssCnlSvyComptView(
			@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO,
			@ModelAttribute("locImgInfoVO") LocImgInfoVO locImgInfoVO,
			ModelMap model) throws Exception{
		
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		LssCnlSvyComptVO vo = lssCnlSvyComptService.selectCnlSvyComptDetail(searchVO);
		
		// 항공사진 따옴표 제거
		if(!vo.getArlphoto1().equals("{}")) {
			vo.setArlphoto1("/storage/fieldBook"+vo.getArlphoto1());
		}else {
			vo.setArlphoto1("");
		}
		if(!vo.getArlphoto2().equals("{}")) {
			vo.setArlphoto2("/storage/fieldBook"+vo.getArlphoto2());
		}else {
			vo.setArlphoto2("");
		}
		vo.setArlphoto1(vo.getArlphoto1().replaceAll("\"", ""));
		vo.setArlphoto2(vo.getArlphoto2().replaceAll("\"", ""));
		
		// 도면해석 따옴표 제거
		if(!vo.getRsltphoto1().equals("{}")) {
			vo.setRsltphoto1("/storage/fieldBook"+vo.getRsltphoto1());
		}else {
			vo.setRsltphoto1("");
		}
		if(!vo.getRsltphoto2().equals("{}")) {
			vo.setRsltphoto2("/storage/fieldBook"+vo.getRsltphoto2());
		}else {
			vo.setRsltphoto2("");
		}
		if(!vo.getRsltphoto3().equals("{}")) {
			vo.setRsltphoto3("/storage/fieldBook"+vo.getRsltphoto3());
		}else {
			vo.setRsltphoto3("");
		}
		vo.setRsltphoto1(vo.getRsltphoto1().replaceAll("\"", ""));
		vo.setRsltphoto2(vo.getRsltphoto2().replaceAll("\"", ""));
		vo.setRsltphoto3(vo.getRsltphoto3().replaceAll("\"", ""));
		
		DmsFormalization dmsformal = new DmsFormalization();
		if(vo.getSvyLon() != null && !vo.getSvyLon().equals("")) {
			dmsformal.setDmsLon(vo.getSvyLon());
			vo.setSvyLon(dmsformal.getDmsLon());
		}
		if(vo.getSvyLat() != null && !vo.getSvyLat().equals("")) {
			dmsformal.setDmsLat(vo.getSvyLat());
			vo.setSvyLat(dmsformal.getDmsLat());
		}
		
		// 사업종류
		if(vo.getBizType().equals("사방댐") || vo.getBizType().equals("사방댐설치사업")) {
			vo.setBizType("사방댐설치사업");
		}else if(vo.getBizType().equals("계류보전") || vo.getBizType().equals("계류보전사업")) {
			vo.setBizType("계류보전사업");
		}
		
		model.addAttribute("result", vo);
		
		
		List<String> cnlBasisList = new ArrayList<String>();
		
		if(vo.getCnlSlctRn1() != null) {
			String cnlSlctRn1 = vo.getCnlSlctRn1().toString();
			if(cnlSlctRn1.equals("1")) cnlBasisList.add("제13조제2항제1호");
		}
		
		if(vo.getCnlSlctRn2() != null) {
			String cnlSlctRn2 = vo.getCnlSlctRn2().toString();
			if(cnlSlctRn2.equals("2")) cnlBasisList.add("제13조제2항제2호");
		}
		
		if(vo.getCnlSlctRn3() != null) {
			String cnlSlctRn3 = vo.getCnlSlctRn3().toString();
			if(cnlSlctRn3.equals("3")) cnlBasisList.add("제13조제2항제3호");
		}
		
		if(vo.getCnlSlctRn4() != null) {
			String cnlSlctRn4 = vo.getCnlSlctRn4().toString();
			if(cnlSlctRn4.equals("4")) cnlBasisList.add("제13조제2항제4호");
		}
		String cnlBasis = StringUtils.join(cnlBasisList, ", ");
		model.addAttribute("cnlBasis",cnlBasis);
		
		
		comVO.setCodeId("FEI158");
		List<CmmnDetailCode> bizTypeCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("bizTypeCodeList", bizTypeCodeList); // 사업종류 코드목록
		
		comVO.setCodeId("FEI159");
		List<CmmnDetailCode> applcEgnerMhdCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("applcEgnerMhdCodeList", applcEgnerMhdCodeList); // 적용공법 코드목록
		
		comVO.setCodeId("FEI160");
		List<CmmnDetailCode> dsstrOccrrncAtCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("dsstrOccrrncAtCodeList", dsstrOccrrncAtCodeList); // 재해발생여부 코드목록
		
		comVO.setCodeId("FEI161");
		List<CmmnDetailCode> mrngSttusCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("mrngSttusCodeList", mrngSttusCodeList); // 계류상태(토석류) 코드목록
		
		comVO.setCodeId("FEI162");
		List<CmmnDetailCode> sSttusCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("sSttusCodeList", sSttusCodeList); // 사면상태(산사태) 코드목록
		
		comVO.setCodeId("FEI163");
		List<CmmnDetailCode> stableIntrprtYnCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("stableIntrprtYnCodeList", stableIntrprtYnCodeList); // 안정해석결과(산사태) 코드목록
		
		comVO.setCodeId("FEI164");
		List<CmmnDetailCode> simlatnRsltYnCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("simlatnRsltYnCodeList", simlatnRsltYnCodeList); // 시뮬레이션결과(토석류) 코드목록
		
		comVO.setCodeId("FEI165");
		List<CmmnDetailCode> hbtOpnListnYnCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("hbtOpnListnYnCodeList", hbtOpnListnYnCodeList ); // 주민의견청취 코드목록
		
		comVO.setCodeId("FEI166");
		List<CmmnDetailCode>  reducAtCodeList = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("reducAtCodeList", reducAtCodeList ); // 주민의견청취 코드목록
		
		
		org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
		JSONParser parser = new JSONParser();
		Object obj;
		String photo = null;
		if(vo.getPhoto() != null && vo.getPhoto().length() > 2) {
			photo = vo.getPhoto().toString(); 
			obj = parser.parse(photo);
			jsonArray = (org.json.simple.JSONArray)obj;
			model.addAttribute("orginl_photo_result", jsonArray);
		}
		
		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
			jsonArray = new org.json.simple.JSONArray();
	        photo = vo.getPhotoTag().toString();
	        JSONArray photoArr = new JSONArray(new JSONTokener(photo));
	        JSONArray sortedJsonArray = new JSONArray();
	        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	        
	        for (int i = 0; i < photoArr.length(); i++) {
	        		jsonValues.add(photoArr.getJSONObject(i));	        		
	        }
	        if(jsonValues.size() > 0) {
	        	Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        		private static final String KEY_NUM = "TAG";
	        		@Override
	        		public int compare(JSONObject a, JSONObject b) {
	        			String valA = "";
	        			String valB = "";
	        			try {
	        				valA = (String) a.get(KEY_NUM);
	        				valB = (String) b.get(KEY_NUM);
	        			}
	        			catch (JSONException e) {
	        				e.printStackTrace();
	        			}
	        	
        				return valA.compareTo(valB);
	        		}
	        	});
	        	
	        	int j = -1;
	        	int photoSize = jsonValues.size();
	        	for (int i = 0; i < photoSize; i++) {
	        		if(j != -1) {
	        			if(jsonValues.get(j).get("TAG").toString().isEmpty()) {
	        				jsonValues.add(jsonValues.get(j));
	        				jsonValues.remove(j);
	        			}
	        		}else {
	        			if(jsonValues.get(i).get("TAG").toString().isEmpty()) {
	        				j = i;
	        				jsonValues.add(jsonValues.get(i));
	        				jsonValues.remove(i);
	        			}	        			
	        		}
	        	}	        	
	        	for (int i = 0; i < jsonValues.size(); i++) {
	        		sortedJsonArray.put(jsonValues.get(i));
	        	}	        	

	        	obj = parser.parse(sortedJsonArray.toString());
	        	jsonArray = (org.json.simple.JSONArray)obj;
	        	model.addAttribute("photo_result", jsonArray);
	        }
	        
		}
		
		//위치도 조회 처리		
//		HashMap<String, Object> schMap = new HashMap<>();
//		locImgInfoVO.setGid(Integer.parseInt(searchVO.getGid()));
//		locImgInfoVO.setSvySe("CNL");
//		
//		schMap.put("SE",locImgInfoVO.getSvySe());
//		schMap.put("type", vo.getSvyType());
//		schMap.put("gid", Integer.parseInt(vo.getGid()));		
//		List<EgovMap> locList = extFieldBookService.selectComptLcMapLonLat(schMap);
//				
//		if(locList.size() > 0) {
//			String center = String.join(",", (String[]) new String[] {locList.get(0).get("lon").toString(),locList.get(0).get("lat").toString()});
//			String svyid = vo.getSvyId();
//			String se = locImgInfoVO.getSvySe();
//			String marker = "";
//			String label = "";
//			if(locList.get(0).get("marker").toString().split(";").length > 1) {
//				String[] markers = locList.get(0).get("marker").toString().split(";");
//				String[] labels = locList.get(0).get("label").toString().split(";");
//				if(markers[0].toString().equals(markers[1].toString())) {
//					marker += labels[0].toString();
//				}else if(se.equals("CNL")) {			
//					marker += markers[0].toString().concat(";");
//					label += svyid + "_시점".concat(";");
//					marker += markers[1].toString();
//					label += svyid + "_종점";
//				}else {
//					marker += markers[0].toString().concat(";");
//					label += labels[0].toString().concat(";");
//					marker += markers[1].toString();
//					label += labels[1].toString();
//				}
//			} else {
//				marker += locList.get(0).get("marker").toString();
//				label += locList.get(0).get("label").toString();
//			}
//			
//			int w = 586;
//			int h = 516;
//			int zoom = extFieldBookService.selectLocImgInfo(locImgInfoVO);
//			
//			org.json.simple.JSONArray paramArr = new org.json.simple.JSONArray();
//			for(int i=0; i<locList.size(); i++) {
//				JSONObject param = new JSONObject();
//				param.put("center",center);
//				param.put("svyid",svyid);
//				param.put("se",se);
//				param.put("marker",marker);
//				param.put("label",label.toString());
//				param.put("zoom", zoom);
//				param.put("w",w);
//				param.put("h",h);
//				
//				obj = parser.parse(param.toString());
//				paramArr.add(obj);
//				model.addAttribute("mapParam", paramArr.get(0).toString().replaceAll("\"", "'"));
//			}
//			
//			model.addAttribute("orginl_zoom", zoom);
//		}
				
		
		return "sys/lss/cnl/sct/svyComptUpdt";
	}
	
	/**
	 * 조사완료지를 수정한다.
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/cnl/sct/updateCnlSvyCompt.do")
    public ModelAndView updateLssCnlSvyCompt(@ModelAttribute("lssCnlSvyComptVO") LssCnlSvyComptVO svyComptVO, BindingResult bindingResult, Model model, HttpServletRequest req, MultipartHttpServletRequest multiRequest) throws Exception {
		beanValidator.validate(svyComptVO, bindingResult);
		ModelAndView mv = new ModelAndView("jsonView");
//		HashMap<String, Object> schMap = new HashMap<>();
		svyComptVO.setPhotoTagList(svyComptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		try {
			if(svyComptVO.getCnlSlctRn1() == null) svyComptVO.setCnlSlctRn1("");
			if(svyComptVO.getCnlSlctRn2() == null) svyComptVO.setCnlSlctRn2("");
			if(svyComptVO.getCnlSlctRn3() == null) svyComptVO.setCnlSlctRn3("");
			if(svyComptVO.getCnlSlctRn4() == null) svyComptVO.setCnlSlctRn4("");
			
			if(svyComptVO.getSvyType() != null && !svyComptVO.getSvyType().equals("")) {
				svyComptVO.setSvyType("취약지역 해제조사("+svyComptVO.getSvyType()+")");
			}
			
			
 			// 공유방명
 			String mstNm = svyComptVO.getMstNm().toString();
 			mstNm = mstNm.concat(".ncx");
 			// 조사ID명
 			String svyId = svyComptVO.getSvyId().toString();
 			
 			// 항공사진 
 			String arlphoto1 = req.getParameter("rawArlphoto1").replaceAll("&quot;", "\"");
 			String[] arlphoto1FileName = arlphoto1.replaceAll("\"", "").split("/");
 			String fileName1 = arlphoto1FileName[arlphoto1FileName.length - 1];
 			
			if(fileName1.equals("{}") || fileName1.isEmpty()) {
				arlphoto1 = "";
			}else {
				arlphoto1 = "\"지정이전\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName1+"\"]},";
			}
 			
			String arlphoto2 = req.getParameter("rawArlphoto2").replaceAll("&quot;", "\"");
 			String[] arlphoto2FileName = arlphoto2.replaceAll("\"", "").split("/");
 			String fileName2 = arlphoto2FileName[arlphoto2FileName.length - 1];
 			
			if(fileName2.equals("{}") || fileName2.isEmpty()) {
				arlphoto2 = "";
			}else {
				arlphoto2 = "\"지정이후\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName2+"\"]},";
			}
			
			for(int i=1; i<=2; i++) {
				List<MultipartFile> arlphotoFile = multiRequest.getFiles("arlphoto"+i);
				if(!arlphotoFile.isEmpty() && !arlphotoFile.equals("[]")) {
					arlphotoFile.get(0).getName();
					String checkNm = arlphotoFile.get(0).getName();
					String uploadNm = cnlComptFileUpload(arlphotoFile, mstNm, svyId, checkNm, "항공");
								 
					if(i == 1 && !uploadNm.equals("")) {
						svyComptVO.setArlphoto1(uploadNm);
						arlphoto1 = "\"지정이전\": {\"사진\": [\""+svyComptVO.getArlphoto1()+"\"]},";
					}
					if(i == 2 && !uploadNm.equals("")) {
						svyComptVO.setArlphoto2(uploadNm);
						arlphoto2 = "\"지정이후\": {\"사진\": [\""+svyComptVO.getArlphoto2()+"\"]},";
					}
				}
			}
			String arlphoto = "{"+ arlphoto1 + arlphoto2 + "}";
			int arlphotoLastIndex = arlphoto.lastIndexOf(",");
 			if(arlphotoLastIndex >= 0) {
 				arlphoto = arlphoto.substring(0, arlphotoLastIndex) + arlphoto.substring(arlphotoLastIndex + 1);
 			}
 			svyComptVO.setArlphoto(arlphoto);
 			
 			
 			// 결과도면
 			if(svyComptVO.getSvyType().equals("취약지역 해제조사(토석류)")) {
	 			String rsltphoto1 = req.getParameter("rawRsltphoto1").replaceAll("&quot;", "\"");
	 			String[] rsltphoto1FileName = rsltphoto1.replaceAll("\"", "").split("/");
	 			String rsltFileName1 = rsltphoto1FileName[rsltphoto1FileName.length - 1];
			
	 			if(rsltFileName1.equals("{}") || rsltFileName1.isEmpty()) {
	 				rsltphoto1 = "";
				}else {
					rsltphoto1 = "\"시뮬레이션 결과도면\": {\"사진\": [\"gimg:///"+mstNm+"/"+rsltFileName1+"\"]},";
				}
	 			
				List<MultipartFile> rsltphotoFile = multiRequest.getFiles("rsltphoto1");
				if(!rsltphotoFile.isEmpty() && !rsltphotoFile.equals("[]")) {
					rsltphotoFile.get(0).getName();
					String checkNm = rsltphotoFile.get(0).getName();
					String uploadNm = cnlComptFileUpload(rsltphotoFile, mstNm, svyId, checkNm, "토석류");
								 
					if(!uploadNm.equals("")) {
						svyComptVO.setRsltphoto(uploadNm);
						rsltphoto1 = "\"시뮬레이션 결과도면\": {\"사진\": [\""+svyComptVO.getRsltphoto()+"\"]},";
					}
				}
				String rsltphoto = "{" + rsltphoto1 + "}";
				int rsltphotoLastIndex = rsltphoto.lastIndexOf(",");
	 			if(rsltphotoLastIndex >= 0) {
	 				rsltphoto = rsltphoto.substring(0, rsltphotoLastIndex) + rsltphoto.substring(rsltphotoLastIndex + 1);
	 			}
	 			svyComptVO.setRsltphoto(rsltphoto);
 			}else {
 				String rsltphoto1 = req.getParameter("rawRsltphoto1").replaceAll("&quot;", "\"");
	 			String[] rsltphoto1FileName = rsltphoto1.replaceAll("\"", "").split("/");
	 			String rsltFileName1 = rsltphoto1FileName[rsltphoto1FileName.length - 1];
			
	 			if(rsltFileName1.equals("{}") || rsltFileName1.isEmpty()) {
	 				rsltphoto1 = "";
				}else {
					rsltphoto1 = "\"건기\": {\"사진\": [\"gimg:///"+mstNm+"/"+rsltFileName1+"\"]},";
				}

	 			String rsltphoto2 = req.getParameter("rawRsltphoto2").replaceAll("&quot;", "\"");
	 			String[] rsltphoto2FileName = rsltphoto2.replaceAll("\"", "").split("/");
	 			String rsltFileName2 = rsltphoto2FileName[rsltphoto2FileName.length - 1];
	 			
	 			if(rsltFileName2.equals("{}") || rsltFileName2.isEmpty()) {
	 				rsltphoto2 = "";
	 			}else {
	 				rsltphoto2 = "\"우기\": {\"사진\": [\"gimg:///"+mstNm+"/"+rsltFileName2+"\"]},";
	 			}
	 			
	 			for(int i=1; i<=2; i++) {
					List<MultipartFile> rsltphotoFile = multiRequest.getFiles("rsltphoto"+i);
					if(!rsltphotoFile.isEmpty() && !rsltphotoFile.equals("[]")) {
						rsltphotoFile.get(0).getName();
						String checkNm = rsltphotoFile.get(0).getName();
						String uploadNm = cnlComptFileUpload(rsltphotoFile, mstNm, svyId, checkNm ,"산사태");
									 
						if(i == 1 && !uploadNm.equals("")) {
							svyComptVO.setRsltphoto(uploadNm);
							rsltphoto1 = "\"건기\": {\"사진\": [\""+svyComptVO.getRsltphoto()+"\"]},";
						}
						if(i == 2 && !uploadNm.equals("")) {
							svyComptVO.setRsltphoto(uploadNm);
							rsltphoto2 = "\"우기\": {\"사진\": [\""+svyComptVO.getRsltphoto()+"\"]},";
						}
					}
				}
				String rsltphoto = "{" + rsltphoto1 + rsltphoto2 + "}";
				int rsltphotoLastIndex = rsltphoto.lastIndexOf(",");
	 			if(rsltphotoLastIndex >= 0) {
	 				rsltphoto = rsltphoto.substring(0, rsltphotoLastIndex) + rsltphoto.substring(rsltphotoLastIndex + 1);
	 			}
	 			svyComptVO.setRsltphoto(rsltphoto);
 			}
//			schMap.put("mst_id", Integer.parseInt(svyComptVO.getMstId()));
//			List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
//		
//			schMap.put("SE","CNL");
//			schMap.put("type",svyComptVO.getSvyType()); 
//			schMap.put("gid",Integer.parseInt(svyComptVO.getGid()));
//			schMap.put("_label", svyComptVO.getSvyId());
//			schMap.put("path", fieldBookUploadPath.concat("/").concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/")); //저장경로
//			schMap.put("zoom",Integer.parseInt(svyComptVO.getChangedZoom()));
//		
//			LOGGER.info("====== 위치도 생성 시작 ======");			
//			extFieldBookService.updateComptLcMap(schMap);
//			LOGGER.info("====== 위치도 생성 종료 ======");
 			
 			lssCnlSvyComptService.updateCnlSvyCompt(svyComptVO);
 			
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
	 * 조사완료지를 삭제한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/deleteCnlSvyCompt.do")
	public ModelAndView deleteLssCnlSvyCompt(LssCnlSvyComptVO svyComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			lssCnlSvyComptService.deleteCnlSvyCompt(svyComptVO);
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
	 * 조사완료정보 엑셀다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/cnl/sct/selectCnlSvyComptListExcel.do")
	public ModelAndView selectCnlSvyComptListExcel(LssCnlSvyComptVO svyComptVO) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("cnlExcelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	HashMap<?, ?> _map = (HashMap<?, ?>)lssCnlSvyComptService.selectCnlSvyComptListExcel(svyComptVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    	
    	String fileNm = "취약지역해제조사_".concat(formater.format(new Date()).toString());
    	
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}
	
	/**
	 * 조사완료지 엑셀 재업로드 팝업
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateCnlSvyComptPopup.do")
	public String insertCnrsSpceSldPopup(
			@ModelAttribute("svyComptVO") LssCnlSvyComptVO svyComptVO,
			ModelMap model) throws Exception {
		
		return "sys/lss/cnl/sct/updateCnlSvyComptPopup";
	}

	/**
	 * @param svyComptVO
	 * @param mFile
	 * @param model
	 * @param res
	 * @return
	 * @throws Exception
	 * @desciption 조사완료 데이터를 재업로드 한다.
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateCnlSvyComptExcel.do")
	public ModelAndView updateCnlSvyComptExcel(
			@ModelAttribute("svyComptVO") LssCnlSvyComptVO svyComptVO,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		JSONObject results = lssCnlSvyComptService.updateCnlSvyComptExcel(svyComptVO, mFile);
		
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
	 * 위치도 재생성 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateLocReCreatePopup.do")
	public String updateLocReCreatePopup(ModelMap model) throws Exception {
		
		LocReCreateVO searchMap = new LocReCreateVO();
		EgovMap dateMap = lssCnlSvyComptService.selectLastUpdateMinMaxDate(searchMap);
		
		model.addAttribute("lastUptDate", dateMap);
		
		return "sys/cmm/pop/updateLocReCreatePopup";
	}
	
	/**
	 * 대상지 기간 별 건수 조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/selectLocReCeateCnt.do")
	public ModelAndView selectLocReCeateCnt(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap dateMap = lssCnlSvyComptService.selectLastUpdateMinMaxDate(searchVO);
			
			mv.addObject("status",200);
			mv.addObject("message","success");
			mv.addObject("allCnt", dateMap.get("allcnt"));
		} catch (Exception e) {
			mv.addObject("status",400);
			mv.addObject("message",e.getMessage());
		}
		
		return mv;
	}
	
	/**
	 * 대상지 조사 기간 별 위치도 재생성
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateLocReCreate.do")
	public ModelAndView updateLocReCreate(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			List<?> list = lssCnlSvyComptService.updateLocReCreate(searchVO);
			for (int i = 0; i < list.size(); i++) {
				LOGGER.info("재생성 시작");
				HashMap<String, Object> schMap = new HashMap<String, Object>();
				EgovMap map = (EgovMap)list.get(i);
				
				schMap.put("gid", map.get("gid"));
				schMap.put("type", map.get("svytype"));
				schMap.put("path", fieldBookUploadPath.concat("/").concat(map.get("mstpath").toString()).concat(".ncx/"));
				schMap.put("_label", map.get("svylabel"));
				schMap.put("SE", map.get("svyse"));
				
				extFieldBookService.updateComptLcMap(schMap);
				LOGGER.info("재생성 종료");
				Thread.sleep(100);
			}
			mv.addObject("status",200);
			mv.addObject("message","success");
		} catch (Exception e) {
			mv.addObject("status",400);
			mv.addObject("message",e.getMessage());
		}
		
		return mv;
	}


	/**
	 * 현장사진 동기화 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updatePhotoPopup.do")
	public String updatePhotoPopup(ModelMap model, LssCnlSvyComptVO searchVO) throws Exception {
		
		EgovMap photoMap = lssCnlSvyComptService.selectSvyPhotoNullList(searchVO);
		model.addAttribute("result", photoMap);
		
		return "sys/cmm/pop/updatePhotoPopup";
	}
	
	/**
	 * 대상지 기간 별 건수 조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/selectPhotoNullCnt.do")
	public ModelAndView selectPhotoNullCnt(@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap photoMap = lssCnlSvyComptService.selectSvyPhotoNullList(searchVO);
			
			mv.addObject("status",200);
			mv.addObject("message","success");
			mv.addObject("allCnt", photoMap.get("allcnt"));
		} catch (Exception e) {
			mv.addObject("status",400);
			mv.addObject("message",e.getMessage());
		}
		
		return mv;
	}
	
	File[] photoList = null;
	public int COMPARETYPE_NAME = 0;
	public int COMPARETYPE_DATE = 1;
	public File[] sortFileList(File[] photoList, final int compareType) {
		Arrays.sort(photoList,new Comparator<Object>(){
			@Override
			public int compare(Object o1, Object o2) {
				String s1 = "";
				String s2 = "";
				if(compareType == COMPARETYPE_NAME) {
					s1 = ((File) o1).getName();
					s2 = ((File) o2).getName();
				}
				return s1.compareTo(s2);
			}
		});
		return photoList;
	}
		
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updatePhotoList.do")
	public ModelAndView updatePhotoList(@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		String[] photoTagNmArr= {"1.전경사진","2.유출구사진","3.현황사진1","4.현황사진2"};
		String svyId = "";
		try {
			List<?> list = lssCnlSvyComptService.updatePhotoList(searchVO);
			for (int i = 0; i < list.size(); i++) {
				LOGGER.info("동기화 시작");
				int photoTagCnt = 1, photoCnt = 1;
				EgovMap map = (EgovMap)list.get(i);
				svyId = map.get("svyid").toString();
				System.out.println("svyID : "+svyId);
				File photoTagDir = new File(fileStoreDir.concat(map.get("svyid").toString()+File.separator+"1.현장사진"+File.separator));
				File photoDir = new File(fileStoreDir.concat(map.get("svyid").toString()+File.separator+"2.기타사진"+File.separator));
				if(EgovFileUtil.isExistsFile(photoTagDir.toString()) && EgovFileUtil.isExistsFile(photoDir.toString()) ) {
					String photoTagArr = "[";
					String photoArr = "[";
					File[] photoTagList = photoTagDir.listFiles(); 
					photoList = photoDir.listFiles();
//					photoList = sortFileList(photoList,COMPARETYPE_NAME);
					// 사진태그 작업
					for(File j : photoTagList) {
						String newFileNm = map.get("mstnm").toString().concat(".ncx/"+map.get("svyid").toString().concat(".000"+photoTagCnt+"."+FilenameUtils.getExtension(j.getName())));
						String dbFileNm = "gimg:///"+newFileNm;
						
						// 파일 이동
						File photoPath = new File(fieldBookDir+map.get("mstnm").toString()+".ncx");
						if(!photoPath.exists()) photoPath.mkdirs();
						Path filePath = Paths.get(j.getPath());
						Path newFilePath = Paths.get(fieldBookDir+newFileNm);
						
						Files.move(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
						
						// DB JSON 재조립
						photoTagArr = photoTagArr.concat("{\"TAG\":\""+photoTagNmArr[photoTagCnt-1]+"\",\"FILENAME\":\""+dbFileNm+"\"},");
						photoArr = photoArr.concat("\""+dbFileNm+"\",");
						photoTagCnt++;
					}
					photoCnt = photoTagCnt;
					photoTagArr = photoTagArr.substring(0, photoTagArr.length()-1).concat("]");
					
					// 사진 작업
					for(File x : photoList) {
						String photoNm = ".000"+ photoCnt;
						if(photoCnt>= 10) photoNm = ".00"+photoCnt;
						String newFileNm = map.get("mstnm").toString().concat(".ncx/"+map.get("svyid").toString().concat(photoNm+"."+FilenameUtils.getExtension(x.getName())));
						String dbFileNm = "gimg:///"+newFileNm;
						
						// 파일 이동
						File photoPath = new File(fieldBookDir+map.get("mstnm").toString()+".ncx");
						if(!photoPath.exists()) photoPath.mkdirs();
						Path filePath = Paths.get(x.getPath());
						Path newFilePath = Paths.get(fieldBookDir+newFileNm);
						Files.move(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
						
						// DB JSON 재조립
						photoArr = photoArr.concat("\""+dbFileNm+"\",");
						photoCnt++;
					}
					photoArr = photoArr.substring(0, photoArr.length()-1).concat("]");
					
					// 대상지 DB 업데이트
					if(photoTagArr.length() <= 2) photoTagArr = null;
					if(photoArr.length() <= 2) photoArr = null;
					searchVO.setSvyId(map.get("svyid").toString());
					searchVO.setPhoto(photoArr);
					searchVO.setPhotoTagList(photoTagArr);
					lssCnlSvyComptService.updateCnlSvyComptPhotoList(searchVO);
				}
				
				LOGGER.info("동기화 종료");
				Thread.sleep(100);
			}
			mv.addObject("status",200);
			mv.addObject("message","success");
		} catch (Exception e) {
			mv.addObject("status",400);
			mv.addObject("message", svyId+e.getMessage());
		}
		
		return mv;
	}
	
	/**
	 * 공간정보 수정화면
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateCnlSvyComptUpdtView.do")
	public String updateCnlSvyComptUpdtView(@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO, Model model) throws Exception {
		
		EgovMap map = lssCnlSvyComptService.selectSvyComptGeom(searchVO);
		
		String sttusimg = map.get("sttusimg").toString();
		
		// 현황도 이미지가 없는 경우
		if(sttusimg.equals("{}")) {
			map.put("sttusimg", "");
		}
		
		model.addAttribute("result", map);
		
		EgovMap vnaraPntLne = lssCnlSvyComptService.selectSvyComptGeomPntLne(searchVO);
		model.addAttribute("vnaraPntLne", vnaraPntLne);
		
		List<LssCnlSvyComptVO> vnaraPlgn = lssCnlSvyComptService.selectSvyComptGeomPlgn(searchVO);
		
		if(!vnaraPlgn.isEmpty()) {
			for(int i=0; i<vnaraPlgn.size(); i++) {
				String plgnTy = vnaraPlgn.get(i).getVnaraPlgn();
				String plgn = vnaraPlgn.get(i).getVnaraPlgnWkt();
				model.addAttribute("plgnTy"+plgnTy, plgnTy);
				model.addAttribute("plgn"+plgnTy, plgn);
			}
		}
		
		model.addAttribute("vnaraPlgn", vnaraPlgn);
		
		return "sys/lss/cnl/sct/svyComptGeomUpdt";
	}
	
	/**
	 * 공간정보 수정입력
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/updateCnlSvyComptUpdt.do")
	public ModelAndView updateCnlSvyComptUpdt(@ModelAttribute("LssCnlSvyComptVO") LssCnlSvyComptVO svyComptVO, BindingResult bindingResult, Model model, HttpServletRequest req) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			String vnaraPntWkt = req.getParameter("vnaraPntWkt");	//유츌구
			String vnaraLneWkt = req.getParameter("vnaraLneWkt");	//대피로
			String vnaraPlgnWkt01 = req.getParameter("vnaraPlgnWkt01");	//사방댐
			String vnaraPlgnWkt02 = req.getParameter("vnaraPlgnWkt02");	//계류보전
			String vnaraPlgnWkt03 = req.getParameter("vnaraPlgnWkt03");	//유역면적
			String vnaraPlgnWkt04 = req.getParameter("vnaraPlgnWkt04");	//산지사방

			
			HashMap<String, Object> geomMap = new HashMap<String, Object>();
			geomMap.put("gid", svyComptVO.getGid());
			
			if(vnaraPntWkt != null && !vnaraPntWkt.equals("")) {
				geomMap.put("vnaraPntWkt", vnaraPntWkt);
				lssCnlSvyComptService.insertSvyComptGeomVnarapnt(geomMap);
			}else {
				geomMap.put("vnaraPntWkt", "");
				lssCnlSvyComptService.insertSvyComptGeomVnarapnt(geomMap);
			}
			
			if(vnaraLneWkt != null && !vnaraLneWkt.equals("")) {
				geomMap.put("vnaraLneWkt", vnaraLneWkt);
				lssCnlSvyComptService.insertSvyComptGeomVnaralne(geomMap);
			}else {
				geomMap.put("vnaraLneWkt", "");
				lssCnlSvyComptService.insertSvyComptGeomVnaralne(geomMap);
			}
			
			if(vnaraPlgnWkt01 != null && !vnaraPlgnWkt01.equals("")) {
				geomMap.put("vnaraPlgn", "01");
				geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt01);
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}else {
				geomMap.put("vnaraPlgn", "01");
				geomMap.put("vnaraPlgnWkt", "");
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}
			
			if(vnaraPlgnWkt02 != null && !vnaraPlgnWkt02.equals("")) {
				geomMap.put("vnaraPlgn", "02"); 
				geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt02);
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}else {
				geomMap.put("vnaraPlgn", "02"); 
				geomMap.put("vnaraPlgnWkt", "");
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}
			
			if(vnaraPlgnWkt03 != null && !vnaraPlgnWkt03.equals("")) {
				geomMap.put("vnaraPlgn", "03");
				geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt03);
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}else {
				geomMap.put("vnaraPlgn", "03");
				geomMap.put("vnaraPlgnWkt", "");
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}
			
			if(vnaraPlgnWkt04 != null && !vnaraPlgnWkt04.equals("")) {
				geomMap.put("vnaraPlgn", "04");
				geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt04);
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}else {
				geomMap.put("vnaraPlgn", "04");
				geomMap.put("vnaraPlgnWkt", "");
				lssCnlSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
			}
			
			LocReCreateVO creatVO = new LocReCreateVO();
			//svyComptVO.getGid()
			creatVO.setGid(svyComptVO.getGid());
			
			lssCnlSvyComptService.updateComptLcMap(creatVO);
			
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
	 * 항공사진 이미지파일업로드
	 * @param sttusPrntFile
	 * @param mstNm
	 * @param svyId
	 * @param checkNm
	 * @return saveFileNm
	 * @throws Exception
	 */
	private String cnlComptFileUpload(List<MultipartFile> fileList, String mstNm, String svyId, String checkNm, String checkKey) throws Exception{
		File imgFile = new File(fieldBookDir.concat(mstNm));
		ArrayList<String> imgList = new ArrayList<String>();
		
		String saveFileNm = "";
		String newFileNm  = "";
		
		for (MultipartFile file : fileList ) {
			String fileNm = file.getOriginalFilename().toString(); //원본파일명
			
			if(checkNm.equals("arlphoto1") && checkKey.equals("항공")) {
				newFileNm = svyId.concat("_지정이전")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("arlphoto2") && checkKey.equals("항공")) {
				newFileNm = svyId.concat("_지정이후")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("rsltphoto1") && checkKey.equals("토석류")) {
				newFileNm = svyId.concat("_시뮬레이션해석결과도면")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("rsltphoto1") && checkKey.equals("산사태")) {
				newFileNm = svyId.concat("_건기")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("rsltphoto2") && checkKey.equals("산사태")) {
				newFileNm = svyId.concat("_우기")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}
			String filePath = imgFile + File.separator + newFileNm;
			
			imgList.add("\"/"+mstNm + "/" + newFileNm+"\"");
			if(!imgFile.exists()) imgFile.mkdirs();
			EgovFileUtil.rm(filePath);
			file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath))); // 파일 생성
			
			saveFileNm = "gimg:///"+mstNm+"/"+newFileNm;
		}
		return saveFileNm;
	}
	
	/**
	 * 공간정보 수정화면 > 현황도팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/cnl/sct/selectSvyComptStatPopup.do")
	public String selectSvyComptStatPopup(@RequestParam(value="gid") String gid, Model model) throws Exception {
		
		LssCnlSvyComptVO searchVO = new LssCnlSvyComptVO();
		searchVO.setGid(gid);
		
		EgovMap map = lssCnlSvyComptService.selectSvyComptGeom(searchVO);
		
		String sttusimg = map.get("sttusimg").toString();
		
		// 현황도 이미지가 없는 경우
		if(sttusimg.equals("{}")) {
			map.put("sttusimg", "");
		}else {
			String convertSttusImg = sttusimg.replace("\"", "");
			model.addAttribute("sttusimg", convertSttusImg);
		}
		
		return "sys/lss/cnl/sct/svySvyComptStatPopup";
	}
}