package or.sabang.sys.lss.wka.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.util.FileCopyUtils;
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
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.fcc.service.EgovNumberFormat;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.string.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.LocImgInfoVO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptService;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.utl.CommonUtil;
import or.sabang.utl.DmsFormalization;

@Controller
public class LssWkaSvyComptController {
	
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
	
	@Resource(name = "lssWkaSvyComptService") 	
	private LssWkaSvyComptService lssWkaSvyComptService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.reUpload");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    //private final long maxFileSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.temp");
    private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
    
//    private static final String VWORLD_API_URL = EgovProperties.getProperty("vworld.landUrl");
//	private static final String VWORLD_API_KEY = EgovProperties.getProperty("vworld.apiKey");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LssWkaSvyComptController.class);
	

	/**
	 * 조사완료목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/selectWkaSvyComptList.do")	
    public String selectWkaSvyComptList (@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {
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

		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(lssWkaSvyComptService.selectWkaSvyComptMaxYear());
		}
//		Date date = new Date();
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(lssWkaSvyComptService.selectWkaSvyComptMaxMonth());
//			searchVO.setSvyMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = lssWkaSvyComptService.selectWkaSvyComptYear();
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
		vo.setCodeId("FEI001");
		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
		if(searchVO.getSvyRegion1() != null) {
			vo.setCodeId(searchVO.getSvyRegion1());
			List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
			model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
		}
		
		//실태조사 필요성 코드조회
		vo.setCodeId("FEI030");
		List<?> ncssty_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("ncsstyCodeList", ncssty_result);
		
		List<LssWkaSvyComptVO> SvyComptList = lssWkaSvyComptService.selectWkaSvyComptList(searchVO);
		model.addAttribute("resultList", SvyComptList);
	
		int totCnt = lssWkaSvyComptService.selectWkaSvyComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		List<String> accessList = lssWkaSvyComptService.selectAuthorCnrsList(commandMap);	
		model.addAttribute("accessList", accessList);
		
		return "sys/lss/wka/sct/svyComptList";		
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/selectWkaSvyComptDetail.do")
	public String selectWkaSvyComptDetail(@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		LssWkaSvyComptVO vo = lssWkaSvyComptService.selectWkaSvyComptDetail(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		if(vo.getLon() != null && !vo.getLon().equals("")) {
			dmsformal.setDmsLon(vo.getLon());
			vo.setLon("E ".concat(dmsformal.getDmsLon().replaceAll("E", "")));
		}
		if(vo.getLat() != null && !vo.getLat().equals("")) {
			dmsformal.setDmsLat(vo.getLat());
			vo.setLat("N ".concat(dmsformal.getDmsLat().replaceAll("N", "")));
		}
		if(vo.getPx() != null && !vo.getPx().equals("")) {
			dmsformal.setDmsLat(vo.getPx());
			vo.setPx("N ".concat(dmsformal.getDmsLat().replaceAll("N", "")));
		}
		if(vo.getPy() != null && !vo.getPy().equals("")) {
			dmsformal.setDmsLon(vo.getPy());
			vo.setPy("E ".concat(dmsformal.getDmsLon().replaceAll("E", "")));
		}
		
		if(vo.getSvydt() != null && !vo.getSvydt().equals("")) { // 조사일자 YYYY-MM-DD -> YYYY. MM. DD. 양식변경
			vo.setSvydt(vo.getSvydt().toString().replaceAll("-", ". ").concat("."));
		}
		
		if(vo.getSvyType().equals("취약지역 실태조사(토석류)")){
			// 붕괴점수
			int clpsscore = 0;
			// 침식점수
			int corrosionscore = 0;
			// 전석점수
			int bldrstnescore = 0;
			// 토석류 흔적 점수
			int soiltracescore = 0;
			// 주 위험요소 점수
			ArrayList<Integer> mainriskelemarr = new ArrayList<Integer>();
			int mainriskelemscore = 0;
			
			if(vo.getClpsscore() != null && vo.getClpsscore().length() > 0) clpsscore = Integer.parseInt(vo.getClpsscore());    
			if(vo.getCorrosionscore() != null && vo.getCorrosionscore().length() > 0) corrosionscore = Integer.parseInt(vo.getCorrosionscore());    
			if(vo.getBldrstnescore() != null && vo.getBldrstnescore().length() > 0) bldrstnescore = Integer.parseInt(vo.getBldrstnescore());    
			if(vo.getSoiltracescore() != null && vo.getSoiltracescore().length() > 0) soiltracescore = Integer.parseInt(vo.getSoiltracescore());
			
			mainriskelemarr.add(clpsscore);
			mainriskelemarr.add(corrosionscore);
			mainriskelemarr.add(bldrstnescore);
			mainriskelemarr.add(soiltracescore);
			
			mainriskelemscore = Collections.max(mainriskelemarr);
			
			vo.setMainriskelemscore(Integer.toString(mainriskelemscore));
			
		}
		
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
		
		// 안정해석점수 사진
		if(vo.getStabanalImg() != null && vo.getStabanalImg().length() > 2) {
			photo = vo.getStabanalImg().toString();
			obj = parser.parse(photo);
			jsonArray = (org.json.simple.JSONArray)obj;
			model.addAttribute("stabanal_img_result", jsonArray);
		}
		
		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
			jsonArray = new org.json.simple.JSONArray();
	        photo = vo.getPhotoTag().toString();
	        JSONArray photoArr = new JSONArray(new JSONTokener(photo));
	        JSONArray sortedJsonArray = new JSONArray();
//		        Pattern numChk = Pattern.compile("^[0-9\\\\s]+");
	        
	        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	        
	        for (int i = 0; i < photoArr.length(); i++) {
//		        	Matcher matcher = numChk.matcher(photoArr.getJSONObject(i).get("TAG").toString());
//		        	if(matcher.find()) {
	        		jsonValues.add(photoArr.getJSONObject(i));	        		
//		        	};
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
	        				
	        				String[] valA_arr = valA.split("[.]");
	        				String[] valB_arr = valB.split("[.]");
	        				
	        				if(valA_arr.length == 2) {
	        					valA = Integer.valueOf(valA_arr[0]) < 10 ? "0"+valA : valA;
	        				}
	        				
	        				if(valB_arr.length == 2) {
	        					valB = Integer.valueOf(valB_arr[0]) < 10 ? "0"+valB : valB;
	        				}
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
//		org.json.simple.JSONArray lcmapArr = new org.json.simple.JSONArray();
//		if(vo.getLcmap().length() > 2) {				
//			String lcmap = vo.getLcmap().toString();
//			obj = parser.parse(lcmap);
//			lcmapArr = (org.json.simple.JSONArray)obj;
//			model.addAttribute("lcmap_result", lcmapArr);
//		}		
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		String access = lssWkaSvyComptService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result", vo);		
		model.addAttribute("access", access);			
		return "sys/lss/wka/sct/svyComptDetail";
	}
	
	/**
	 * 조사완료지 수정화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/updateWkaSvyComptView.do")
	public String updateLssWkaSvyComptView(
			@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO,
			@ModelAttribute("locImgInfoVO") LocImgInfoVO locImgInfoVO,
			ModelMap model) throws Exception{

		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		comVO.setCodeId("FEI091");
		comVO.setDetailCondition("FEI157");
		List<CmmnDetailCode> wkaResult = cmmUseService.selectWkaCodeDetail(comVO);
		model.addAttribute("wkaCodeList", wkaResult);//취약지역 실태조사 코드목록
				
		LssWkaSvyComptVO vo = lssWkaSvyComptService.selectWkaSvyComptDetail(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		if(vo.getLon() != null && !vo.getLon().equals("")) {
			dmsformal.setDmsLon(vo.getLon());
			vo.setLon("E ".concat(dmsformal.getDmsLon().replaceAll("E", "")));
		}
		if(vo.getLat() != null && !vo.getLat().equals("")) {
			dmsformal.setDmsLat(vo.getLat());
			vo.setLat("N ".concat(dmsformal.getDmsLat().replaceAll("N", "")));
		}
		if(vo.getPx() != null && !vo.getPx().equals("")) {
			dmsformal.setDmsLat(vo.getPx());
			vo.setPx("N ".concat(dmsformal.getDmsLat().replaceAll("N", "")));
		}
		if(vo.getPy() != null && !vo.getPy().equals("")) {
			dmsformal.setDmsLon(vo.getPy());
			vo.setPy("E ".concat(dmsformal.getDmsLon().replaceAll("E", "")));
		}
		
		if(vo.getSvydt() != null && !vo.getSvydt().equals("")) { // 조사일자 YYYY-MM-DD -> YYYY. MM. DD. 양식변경
			vo.setSvydt(vo.getSvydt().toString().replaceAll("-", ". ").concat("."));
		}
			
		if(vo.getSvyType().equals("취약지역 실태조사(토석류)")){
			// 붕괴점수
			int clpsscore = 0;
			// 침식점수
			int corrosionscore = 0;
			// 전석점수
			int bldrstnescore = 0;
			// 토석류 흔적 점수
			int soiltracescore = 0;
			// 주 위험요소 점수
			ArrayList<Integer> mainriskelemarr = new ArrayList<Integer>();
			int mainriskelemscore = 0;
			
			if(vo.getClpsscore() != null && ! vo.getClpsscore().equals("")) clpsscore = Integer.parseInt(vo.getClpsscore());    
			if(vo.getCorrosionscore() != null && !vo.getCorrosionscore().equals("")) corrosionscore = Integer.parseInt(vo.getCorrosionscore());    
			if(vo.getBldrstnescore() != null && !vo.getBldrstnescore().equals("")) bldrstnescore = Integer.parseInt(vo.getBldrstnescore());    
			if(vo.getSoiltracescore() != null && !vo.getSoiltracescore().equals("")) soiltracescore = Integer.parseInt(vo.getSoiltracescore());
			
			mainriskelemarr.add(clpsscore);
			mainriskelemarr.add(corrosionscore);
			mainriskelemarr.add(bldrstnescore);
			mainriskelemarr.add(soiltracescore);
			
			mainriskelemscore = Collections.max(mainriskelemarr);
			
			vo.setMainriskelemscore(Integer.toString(mainriskelemscore));
			
		}
		
//		model.addAttribute("lssWkaSvyCompt", vo);
//		
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
		
		// 안정해석점수 사진
		if(vo.getStabanalImg() != null && vo.getStabanalImg().length() > 2) {
			photo = vo.getStabanalImg().toString();
			obj = parser.parse(photo);
			jsonArray = (org.json.simple.JSONArray)obj;
			model.addAttribute("stabanal_img_result", jsonArray);
		}
		
		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
			jsonArray = new org.json.simple.JSONArray();
	        photo = vo.getPhotoTag().toString();
	        JSONArray photoArr = new JSONArray(new JSONTokener(photo));
	        JSONArray sortedJsonArray = new JSONArray();
	//	        Pattern numChk = Pattern.compile("^[0-9\\\\s]+");
	        
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
	        				
	        				String[] valA_arr = valA.split("[.]");
	        				String[] valB_arr = valB.split("[.]");
	        				
	        				if(valA_arr.length == 2) {
	        					valA = Integer.valueOf(valA_arr[0]) < 10 ? "0"+valA : valA;
	        				}
	        				
	        				if(valB_arr.length == 2) {
	        					valB = Integer.valueOf(valB_arr[0]) < 10 ? "0"+valB : valB;
	        				}
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
//		
//		//위치도 조회 처리		
//		HashMap<String, Object> schMap = new HashMap<>();
//		locImgInfoVO.setGid(Integer.parseInt(searchVO.getGid()));
//		locImgInfoVO.setSvySe("BSC");
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
//				}else if(se.equals("BSC")) {			
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
		
		model.addAttribute("result", vo);
		return "sys/lss/wka/sct/svyComptUpdt";
	}
	
	/**
	 * 조사완료지를 수정한다.
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/wka/sct/updateWkaSvyCompt.do")
    public ModelAndView updateLssWkaSvyCompt(@ModelAttribute("lssWkaSvyComptVO") LssWkaSvyComptVO svyComptVO, BindingResult bindingResult, Model model, MultipartHttpServletRequest multiRequest) throws Exception {
		beanValidator.validate(svyComptVO, bindingResult);
		ModelAndView mv = new ModelAndView("jsonView");
		
		//현장사진
		if(!svyComptVO.getPhotoTagList().isEmpty()) svyComptVO.setPhotoTagList(svyComptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		
		//현황도
		if(svyComptVO.getStatmap() != null) svyComptVO.setStatmap(svyComptVO.getStatmap().replaceAll("&quot;", "\""));
		
		//안정해석 사진 업로드 
		List<MultipartFile> files = null;
		List<MultipartFile> files2 = null;
		
		if(svyComptVO.getSvyType().equals("취약지역 실태조사(토석류)")){
			files = multiRequest.getFiles("simul");
		}else {
			files = multiRequest.getFiles("dry");
			files2 = multiRequest.getFiles("rain");
		}
		
		// 공유방명 
		String mstNm = svyComptVO.getMstNm().toString();
		// 조사아이디명
		String svyId = svyComptVO.getSvyId().toString();
		
		mstNm = mstNm.concat(".ncx");
		File stabanalImgFile = new File(fieldBookDir.concat(mstNm));
		
		ArrayList<String> stabanalImgList = new ArrayList<String>();
		
		for (MultipartFile file : files ) {
			String tag = file.getName().toString(); //태그명
			String fileNm = file.getOriginalFilename().toString(); //원본파일명
			String newFileNm = svyId.concat("_"+tag)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			String filePath = stabanalImgFile + File.separator + newFileNm;
			
			stabanalImgList.add("\"/"+mstNm + "/" + newFileNm+"\"");
			if(!stabanalImgFile.exists()) stabanalImgFile.mkdirs();
			EgovFileUtil.rm(filePath);
			file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath))); // 파일 생성
		}
		
		if(svyComptVO.getSvyType().equals("취약지역 실태조사(산사태)")) {
			for (MultipartFile file : files2 ) {
				String tag = file.getName().toString(); //태그명
				String fileNm = file.getOriginalFilename().toString(); //원본파일명
				String newFileNm = svyId.concat("_"+tag)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
				String filePath = stabanalImgFile + File.separator + newFileNm;
				
				stabanalImgList.add("\"/"+mstNm + "/" + newFileNm+"\"");
				if(!stabanalImgFile.exists()) stabanalImgFile.mkdirs();
				EgovFileUtil.rm(filePath);
				file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath))); // 파일 생성
			}
			if(stabanalImgList.size() == 1) {
				String[] stabImgArr = svyComptVO.getStabanalImg().toString().split(",");
				String stabImg = null;
				if(files.size() == 0){
					for(int i=0; i<stabImgArr.length; i++) {
						if(stabImgArr[i].indexOf("dry") != -1) stabImg = "\""+stabImgArr[i].toString()+"\"";
					}
				}
				if(files2.size() == 0) {
					for(int i=0; i<stabImgArr.length; i++) {
						if(stabImgArr[i].indexOf("rain") != -1) stabImg = "\""+stabImgArr[i].toString()+"\"";
					}
				}
				if(stabImg != null) stabanalImgList.add(stabImg);
			}
		}

		if(stabanalImgList.size() == 0) {
			svyComptVO.setStabanalImg(null);
		}else {			
			svyComptVO.setStabanalImg(stabanalImgList.toString());
		}
		
		Map<String, String> wkaMap = BeanUtils.describe(svyComptVO);
		HashMap<String, Object> wkaCompt = new HashMap<>();
		HashMap<String, Object> wkaCompt2 = new HashMap<>();
		while(wkaMap.values().remove(null));
//		while(wkaMap.values().remove(""));
		
		Object[] keyArr = wkaMap.keySet().toArray();
		Object[] valArr = wkaMap.values().toArray();
		
		for(int i =0;i<keyArr.length;i++) {
			String key = keyArr[i].toString();
			String value = valArr[i].toString();
			
			if(key.equals("svyId") || key.equals("mstId") || key.equals("dirsttus") || key.equals("svyType")) {
				wkaCompt.put(key, value);
				wkaCompt2.put(key, value);
			}else if(i < 60) {
				wkaCompt.put(key, value);
			}else {
				wkaCompt2.put(key, value);
			}
		}

		try {
			lssWkaSvyComptService.updateWkaCompt(wkaCompt,wkaCompt2);
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
	@RequestMapping(value = "/sys/lss/wka/sct/deleteWkaSvyCompt.do")
	public ModelAndView deleteLssWkaSvyCompt(LssWkaSvyComptVO svyComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			lssWkaSvyComptService.deleteWkaSvyCompt(svyComptVO);
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
	 * 조사 완료지 엑셀 다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/wka/sct/selectWkaSvyComptListExcel.do")
	public ModelAndView selectWkaSvyComptListExcel(LssWkaSvyComptVO svyComptVO) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("wkaExcelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	HashMap<?, ?> _map = (HashMap<?, ?>)lssWkaSvyComptService.selectWkaSvyComptListExcel(svyComptVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    	
    	String fileNm = "취약지역실태조사완료지_".concat(formater.format(new Date()).toString());
    	
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	dataMap.put("photoTagList", _map.get("photoTagList"));
    	dataMap.put("legaldongcdList", _map.get("legaldongcdList"));
    	dataMap.put("mnagncdList", _map.get("mnagncdList"));
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드 팝업
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/updateWkaSvyComptPopup.do")
	public String insertCnrsSpceSldPopup(
			@ModelAttribute("svyComptVO") LssWkaSvyComptVO svyComptVO,
			ModelMap model) throws Exception {
		
		return "sys/lss/wka/sct/updateWkaSvyComptPopup";
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
	@RequestMapping(value = "/sys/lss/wka/sct/updateWkaSvyComptExcel.do")
	public ModelAndView updateWkaSvyComptExcel(
			@ModelAttribute("svyComptVO") LssWkaSvyComptVO svyComptVO,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		JSONObject results = lssWkaSvyComptService.updateWkaSvyComptExcel(svyComptVO, mFile);
		
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
	@RequestMapping(value = "/sys/lss/wka/sct/updateLocReCreatePopup.do")
	public String updateLocReCreatePopup(ModelMap model) throws Exception {
		
		LocReCreateVO searchMap = new LocReCreateVO();
		EgovMap dateMap = lssWkaSvyComptService.selectLastUpdateMinMaxDate(searchMap);
		
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
	@RequestMapping(value = "/sys/lss/wka/sct/selectLocReCeateCnt.do")
	public ModelAndView selectLocReCeateCnt(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap dateMap = lssWkaSvyComptService.selectLastUpdateMinMaxDate(searchVO);
			
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
	@RequestMapping(value = "/sys/lss/wka/sct/updateLocReCreate.do")
	public ModelAndView updateLocReCreate(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			lssWkaSvyComptService.updateComptLcMap(searchVO);
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
	@RequestMapping(value = "/sys/lss/wka/sct/updatePhotoPopup.do")
	public String updatePhotoPopup(ModelMap model, LssWkaSvyComptVO searchVO) throws Exception {
		
		EgovMap photoMap = lssWkaSvyComptService.selectSvyPhotoNullList(searchVO);
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
	@RequestMapping(value = "/sys/lss/wka/sct/selectPhotoNullCnt.do")
	public ModelAndView selectPhotoNullCnt(@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap photoMap = lssWkaSvyComptService.selectSvyPhotoNullList(searchVO);
			
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
	@RequestMapping(value = "/sys/lss/wka/sct/updatePhotoList.do")
	public ModelAndView updatePhotoList(@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		String[] photoTagNmArr= {"1.전경사진","2.유출구사진","3.현황사진1","4.현황사진2"};
		String svyId = "";
		try {
			List<?> list = lssWkaSvyComptService.updatePhotoList(searchVO);
			for (int i = 0; i < list.size(); i++) {
				LOGGER.info("동기화 시작");
				int photoTagCnt = 1, photoCnt = 1;
				EgovMap map = (EgovMap)list.get(i);
				svyId = map.get("svyid").toString();
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
					lssWkaSvyComptService.updateWkaSvyComptPhotoList(searchVO);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/updateWkaSvyComptUpdtView.do")
	public String updateWkaSvyComptUpdtView(@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO, Model model) throws Exception{
		//gid,exmnnid,mstid,svyid
		//svyDt,svyUser,sdNm,sggNm,emdNm,riNm,jibun
		EgovMap map = lssWkaSvyComptService.selectSvyComptGeom(searchVO);
		model.addAttribute("result", map);
		
		String lcmap = map.get("lcmap").toString();
		ObjectMapper mapper = new ObjectMapper();
		Object[] objects = mapper.readValue(lcmap, Object[].class);
		
		JSONArray jsonArray = new JSONArray();
		
		for (Object object : objects) {
			
			JSONObject jsonObject = new JSONObject();
			
			if(mapper.convertValue(object, LinkedHashMap.class).get("WKT") != null && !mapper.convertValue(object, LinkedHashMap.class).get("WKT").equals("")) {
				String wkt = mapper.convertValue(object, LinkedHashMap.class).get("WKT").toString();
				jsonObject.put("WKT", wkt);
			}else {
				jsonObject.put("WKT", "");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("시도") != null && !mapper.convertValue(object, LinkedHashMap.class).get("시도").equals("")) {
				String sd = mapper.convertValue(object, LinkedHashMap.class).get("시도").toString();
				jsonObject.put("시도",sd);
			}else {
				jsonObject.put("시도","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("시군구") != null && !mapper.convertValue(object, LinkedHashMap.class).get("시군구").equals("")) {
				String sgg = mapper.convertValue(object, LinkedHashMap.class).get("시군구").toString();
				jsonObject.put("시군구",sgg);
			}else {
				jsonObject.put("시군구","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("읍면동") != null && !mapper.convertValue(object, LinkedHashMap.class).get("읍면동").equals("")) {
				String emd = mapper.convertValue(object, LinkedHashMap.class).get("읍면동").toString();
				jsonObject.put("읍면동",emd);
			}else {
				jsonObject.put("읍면동","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("리") != null && !mapper.convertValue(object, LinkedHashMap.class).get("리").equals("")) {
				String li = mapper.convertValue(object, LinkedHashMap.class).get("리").toString();
				jsonObject.put("리",li);
			}else {
				jsonObject.put("리","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("지번") != null && !mapper.convertValue(object, LinkedHashMap.class).get("지번").equals("")) {
				String jibun = mapper.convertValue(object, LinkedHashMap.class).get("지번").toString();
				jsonObject.put("지번",jibun);
			}else {
				jsonObject.put("지번","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("지목") != null && !mapper.convertValue(object, LinkedHashMap.class).get("지목").equals("")) {
				String jimok = mapper.convertValue(object, LinkedHashMap.class).get("지목").toString();
				jsonObject.put("지목",jimok);
			}else {
				jsonObject.put("지목","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("소유구분") != null && !mapper.convertValue(object, LinkedHashMap.class).get("소유구분").equals("")) {
				String posesnSe = mapper.convertValue(object, LinkedHashMap.class).get("소유구분").toString();
				jsonObject.put("소유구분",posesnSe);
			}else {
				jsonObject.put("소유구분","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("편입면적") != null && !mapper.convertValue(object, LinkedHashMap.class).get("편입면적").equals("")) {
				String incorArea = EgovNumberFormat.formatNumber(Float.parseFloat(mapper.convertValue(object, LinkedHashMap.class).get("편입면적").toString().replace(",", "")), 2);
				jsonObject.put("편입면적",incorArea);
			}else {
				jsonObject.put("편입면적","");
			}
			if(mapper.convertValue(object, LinkedHashMap.class).get("지적면적") != null && !mapper.convertValue(object, LinkedHashMap.class).get("지적면적").equals("")) {
				String branchArea = EgovNumberFormat.formatNumber(Float.parseFloat(mapper.convertValue(object, LinkedHashMap.class).get("지적면적").toString().replace(",", "")), 2);
				jsonObject.put("지적면적",branchArea);
			}else {
				jsonObject.put("지적면적","");
			}
            
            jsonArray.put(jsonObject);
        }
		String lcmapArray = jsonArray.toString();
		model.addAttribute("lcmapArray", lcmapArray);
		
		
		// 빈 {}값 처리
		String statmap = "";
		String rawStatmap = map.get("statmap").toString();
		
		statmap = rawStatmap.replaceAll("(\\{\\},\\s*)+", ",");
		statmap = statmap.replaceAll("(,\\{\\})+", "");
		statmap = statmap.replaceAll("\\{\\}", "");
		statmap = statmap.replaceAll("^,|,$", "");
		statmap = statmap.replaceAll(", ,", ",");
		
		if (statmap.startsWith("[,") || statmap.startsWith("[ ,") || statmap.startsWith("[, ")) {
			statmap = "["+statmap.substring(2);
		}
		
		if (statmap.endsWith(", ]")||statmap.endsWith(",]")) {
			statmap = statmap.substring(0, statmap.length() - 3) + "]";
		}

		if (statmap.isEmpty()) {
		     statmap = "[]";
		}
		
		ObjectMapper statmapMapper = new ObjectMapper();
		Object[] statmapObjects = statmapMapper.readValue(statmap, Object[].class);
		
		JSONArray statmapJsonArray = new JSONArray();
		
		HashMap<String, Object> projMap = new HashMap<>();
		
		for (Object object : statmapObjects) {
			
			JSONObject jsonObject = new JSONObject();
			
			if(statmapMapper.convertValue(object, LinkedHashMap.class).get("경도") != null && !statmapMapper.convertValue(object, LinkedHashMap.class).get("경도").equals("")) {
				String lon = statmapMapper.convertValue(object, LinkedHashMap.class).get("경도").toString();
				jsonObject.put("lon",lon);
				projMap.put("lon1", lon);
			}else {
				jsonObject.put("lon","");
			}
			
			if(statmapMapper.convertValue(object, LinkedHashMap.class).get("위도") != null && !statmapMapper.convertValue(object, LinkedHashMap.class).get("위도").equals("")) {
				String lat = statmapMapper.convertValue(object, LinkedHashMap.class).get("위도").toString();
				jsonObject.put("lat",lat);
				projMap.put("lat1", lat);
			}else {
				jsonObject.put("lat","");
			}
			
			List<EgovMap> projList = lssWkaSvyComptService.selectLssWkaProjDMS(projMap);
			
			jsonObject.put("convertLat",projList.get(0).get("lat1").toString());
			jsonObject.put("convertLon",projList.get(0).get("lon1").toString());
			
			if(statmapMapper.convertValue(object, LinkedHashMap.class).get("길이") != null && !statmapMapper.convertValue(object, LinkedHashMap.class).get("길이").equals("")) {
				String length = statmapMapper.convertValue(object, LinkedHashMap.class).get("길이").toString();
				jsonObject.put("length",length);
			}else {
				jsonObject.put("length","");
			}
			
			if(statmapMapper.convertValue(object, LinkedHashMap.class).get("깊이") != null && !statmapMapper.convertValue(object, LinkedHashMap.class).get("깊이").equals("")) {
				String dept = statmapMapper.convertValue(object, LinkedHashMap.class).get("깊이").toString();
				jsonObject.put("dept",dept);
			}else {
				jsonObject.put("dept","");
			}
			
			if(statmapMapper.convertValue(object, LinkedHashMap.class).get("높이") != null && !statmapMapper.convertValue(object, LinkedHashMap.class).get("높이").equals("")) {
				String heigth = statmapMapper.convertValue(object, LinkedHashMap.class).get("높이").toString();
				jsonObject.put("heigth",heigth);
			}else {
				jsonObject.put("heigth","");
			}
			
			if(statmapMapper.convertValue(object, LinkedHashMap.class).get("특이사항") != null && !statmapMapper.convertValue(object, LinkedHashMap.class).get("특이사항").equals("")) {
				String spclNote = statmapMapper.convertValue(object, LinkedHashMap.class).get("특이사항").toString();
				jsonObject.put("spclNote",spclNote);
			}else {
				jsonObject.put("spclNote","");
			}
			
			statmapJsonArray.put(jsonObject);
		}
		
		String statmapArray = statmapJsonArray.toString();
		model.addAttribute("statmapArray", statmapArray);
		
		
		EgovMap vnaraPntLne = lssWkaSvyComptService.selectSvyComptGeomPntLne(searchVO);
		model.addAttribute("vnaraPntLne", vnaraPntLne);
		
		List<LssWkaSvyComptVO> vnaraPlgn = lssWkaSvyComptService.selectSvyComptGeomPlgn(searchVO);
		
		if(!vnaraPlgn.isEmpty()) {
			for(int i=0; i<vnaraPlgn.size(); i++) {
				String plgnTy = vnaraPlgn.get(i).getVnaraPlgn();
				String plgn = vnaraPlgn.get(i).getVnaraPlgnWkt();
				model.addAttribute("plgnTy"+plgnTy, plgnTy);
				model.addAttribute("plgn"+plgnTy, plgn);
			}
		}
		
		model.addAttribute("vnaraPlgn", vnaraPlgn);
		
		return "sys/lss/wka/sct/svyComptGeomUpdt";	
	}
	
	/**
	 * 공간정보 수정입력
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/updateWkaSvyComptUpdt.do")
	public ModelAndView updateWkaSvyComptUpdt(@ModelAttribute("lssWkaSvyComptVO") LssWkaSvyComptVO svyComptVO, BindingResult bindingResult, Model model, HttpServletRequest req) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			String mstId = req.getParameter("mstid");
			String svyLabel = req.getParameter("svyid");
			
			svyComptVO.setMstId(mstId);
			svyComptVO.setSvyId(svyLabel);
			
			String calcInArea = req.getParameter("calcInArea"); // 편입면적 계산 버튼 클릭 여부
			String svyType = req.getParameter("svyType");
			String vnaraPntWkt = req.getParameter("vnaraPntWkt");	//유츌구
			String vnaraLneWkt = req.getParameter("vnaraLneWkt");	//대피로
			// 공간정보 저장
			String vnaraPlgnWkt01 = req.getParameter("vnaraPlgnWkt01");	//사방댐
			String vnaraPlgnWkt02 = req.getParameter("vnaraPlgnWkt02");	//계류보전
			String vnaraPlgnWkt03 = req.getParameter("vnaraPlgnWkt03");	//유역면적
			String vnaraPlgnWkt04 = req.getParameter("vnaraPlgnWkt04");	//산지사방
			//면적 계산
			String calcLndslddgWkt = req.getParameter("calcLndslddgWkt");	//계류보전
			String calcMntrntWkt = req.getParameter("calcMntrntWkt");	//사방댐
 			String calcMtcecnrwktWkt = req.getParameter("calcMtcecnrwktWkt");	//산지사방
			String lcmap = req.getParameter("lcmap");
			String uploc = req.getParameter("uploc"); //최상부
			String mdlloc = req.getParameter("mdlloc"); //중간부
			String evamap = req.getParameter("evamap");	// 대피장소
			String statmap = req.getParameter("statmap");	// 현황도
			
			
			Float branchAreaTotal = 0.0F;
			Float incorAreaTotal = 0.0F;
			
			if(calcInArea.equals("Y")) {
				if((!calcMtcecnrwktWkt.equals("") && calcMtcecnrwktWkt != null) || (!calcLndslddgWkt.equals("") && calcLndslddgWkt != null) || (!calcMntrntWkt.equals("") && calcMntrntWkt != null)) {
					if(svyType.contains("산사태")) {
						svyComptVO.setVnaraPlgnWkt(calcMtcecnrwktWkt);
					}else { // 토석류일때
						HashMap<String, Object> calcAreaMap = new HashMap<String, Object>();
						 
						if(!calcLndslddgWkt.equals("") && calcLndslddgWkt != null) {
							calcAreaMap.put("vnaraPlgnWkt01", calcLndslddgWkt);	//계류보전
						}
						if(!calcMntrntWkt.equals("") && calcMntrntWkt != null) {
							calcAreaMap.put("vnaraPlgnWkt02", calcMntrntWkt);	//사방댐
						}
						
						if(calcAreaMap.size() == 1) {	// 폴리곤이 1개일때
							String vnaraPlgnWkt = (calcLndslddgWkt.length() > calcMntrntWkt.length()) ? calcLndslddgWkt : calcMntrntWkt;
							svyComptVO.setVnaraPlgnWkt(vnaraPlgnWkt);
						}else {	// 폴리곤이 2개일때
							String vnaraPlgnWkt = lssWkaSvyComptService.selectUnionGeom(calcAreaMap);
							svyComptVO.setVnaraPlgnWkt(vnaraPlgnWkt);
							
						}
					}
					
					List<LssWkaSvyComptVO> calcDataMap =  lssWkaSvyComptService.selectCalcInArea(svyComptVO);
					
					String path1 = req.getRequestURL().toString();
					String path2 = req.getRequestURI(); 
					
					String domain = path1.replaceAll(path2, "");
					
					for(int j=0; j<calcDataMap.size(); j++) {
						String returnValue = CommonUtil.searchPosesnSe(calcDataMap.get(j).getPnuCode(), domain);
						
						// ,을 기준으로 소유구분,ㅇㅇ대장으로 나눠준다.
						String[] returnSplit = returnValue.split(",");
						// 소유구분
						calcDataMap.get(j).setPosesnSe(returnSplit[0]);
						//ㅇㅇ대장
						String jibun = calcDataMap.get(j).getSvyJibun();
						
						if(!returnValue.equals("-")) {
							if(returnSplit[1].equals("임야대장")) {
								calcDataMap.get(j).setSvyJibun("산"+jibun);
							}
						}
						
						String branchArea = EgovNumberFormat.formatNumber(Float.parseFloat(calcDataMap.get(j).getBranchArea()),2);
						String incorArea = EgovNumberFormat.formatNumber(Float.parseFloat(calcDataMap.get(j).getIncorArea()),2);
						
						branchAreaTotal += Float.parseFloat(calcDataMap.get(j).getBranchArea());
						incorAreaTotal += Float.parseFloat(calcDataMap.get(j).getIncorArea());
						
						calcDataMap.get(j).setBranchArea(branchArea);
						calcDataMap.get(j).setIncorArea(incorArea);
						
					}
					mv.addObject("calcData", calcDataMap);
					mv.addObject("branchAreaTotal", EgovNumberFormat.formatNumber(branchAreaTotal, 2));
					mv.addObject("incorAreaTotal", EgovNumberFormat.formatNumber(incorAreaTotal, 2));
				} 
			}else {
				HashMap<String, Object> geomMap = new HashMap<String, Object>();
				geomMap.put("gid", svyComptVO.getGid());
				
				if(vnaraPntWkt != null && !vnaraPntWkt.equals("")) {
					geomMap.put("vnaraPntWkt", vnaraPntWkt);
					lssWkaSvyComptService.insertSvyComptGeomVnarapnt(geomMap);
				}else {
					geomMap.put("vnaraPntWkt", "");
					geomMap.put("gid", svyComptVO.getGid());
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}
				
				if(vnaraLneWkt != null && !vnaraLneWkt.equals("")) {
					geomMap.put("vnaraLneWkt", vnaraLneWkt);
					svyComptVO.setEvasysWkt(vnaraLneWkt);
					lssWkaSvyComptService.insertSvyComptGeomVnaralne(geomMap);	//대피로WKT
				}else {
					geomMap.put("vnaraLneWkt", "");
					geomMap.put("gid", svyComptVO.getGid());
					svyComptVO.setEvasysWkt(""); 
					lssWkaSvyComptService.insertSvyComptGeomVnaralne(geomMap);
				}
				
				if(vnaraPlgnWkt01 != null && !vnaraPlgnWkt01.equals("")) {		// 사방댐(토석류)
					geomMap.put("vnaraPlgn", "01");
					geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt01);
					svyComptVO.setLndslddgWkt(vnaraPlgnWkt01);
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}else {
					geomMap.put("vnaraPlgn", "01");
					geomMap.put("vnaraPlgnWkt", "");
					svyComptVO.setLndslddgWkt(""); 
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}
				
				if(vnaraPlgnWkt02 != null && !vnaraPlgnWkt02.equals("")) {		// 계류보전(토석류)
					geomMap.put("vnaraPlgn", "02"); 
					geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt02);
					svyComptVO.setMntntrntWkt(vnaraPlgnWkt02);
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}else {
					geomMap.put("vnaraPlgn", "02");
					geomMap.put("vnaraPlgnWkt", "");
					svyComptVO.setMntntrntWkt(""); 
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}
				
				if(vnaraPlgnWkt03 != null && !vnaraPlgnWkt03.equals("")) {		// 유역면적
					geomMap.put("vnaraPlgn", "03");
					geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt03);
					svyComptVO.setDgrareaWkt(vnaraPlgnWkt03);
					String dgrareaScore = lssWkaSvyComptService.selectDgrareaScore(vnaraPlgnWkt03);
					svyComptVO.setDgrareascore(dgrareaScore);
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}else {
					geomMap.put("vnaraPlgn", "03");
					geomMap.put("vnaraPlgnWkt", "");
					svyComptVO.setDgrareaWkt(""); 
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}
				 
				if(vnaraPlgnWkt04 != null && !vnaraPlgnWkt04.equals("")) {		// 산지사방(산사태)
					geomMap.put("vnaraPlgn", "04");
					geomMap.put("vnaraPlgnWkt", vnaraPlgnWkt04);
					svyComptVO.setMtcecnrWkt(vnaraPlgnWkt04); 
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}else {
					geomMap.put("vnaraPlgn", "04");
					geomMap.put("vnaraPlgnWkt", "");
					svyComptVO.setMtcecnrWkt(""); 
					lssWkaSvyComptService.insertSvyComptGeomVnaraPlgn(geomMap);
				}
				
				svyComptVO.setLcmap(EgovStringUtil.getHtmlStrCnvr(lcmap));
				
				svyComptVO.setUploc(uploc);
				svyComptVO.setMdlloc(mdlloc);
				
				LocReCreateVO creatVO = new LocReCreateVO();
				//svyComptVO.getGid()
				creatVO.setGid(svyComptVO.getGid());
				
				// 토석류, 유역면적 점수에 대한 총 합계 수정
				LssWkaSvyComptVO vo = lssWkaSvyComptService.selectWkaSvyComptDetail(svyComptVO);
				
				if(svyType.contains("토석류")) {
					// 피해이력점수
					int dglogscore = 0; 
					if(vo.getDglogscore() != null && vo.getDglogscore().length()>0) dglogscore = Integer.parseInt(vo.getDglogscore());
					// 직접영향권 내 보호시설 점수
					int direffcscore = 0;
					if(vo.getDireffcscore() != null && vo.getDireffcscore().length()>0) direffcscore = Integer.parseInt(vo.getDireffcscore());
					// 유역면적 점수
					int dgrareaScore = 0;
					if(svyComptVO.getDgrareascore() != null && svyComptVO.getDgrareascore().length()>0) dgrareaScore = Integer.parseInt(svyComptVO.getDgrareascore());
					// 계류 평균 경사도 점수
					int mntntrntscore = 0;
					if(vo.getMntntrntscore() != null && vo.getMntntrntscore().length()>0) mntntrntscore = Integer.parseInt(vo.getMntntrntscore());
					// 토심 점수
					int soildepscore = 0;
					if(vo.getSoildepscore() != null && vo.getSoildepscore().length()>0) soildepscore = Integer.parseInt(vo.getSoildepscore());
					
					/* 주 위험요소 (붕괴/침식/전석/토석류 흔적 중 택 1) */
					// 붕괴점수
					int clpsscore = 0;
					if(vo.getClpsscore() != null && vo.getClpsscore().length() > 0) clpsscore = Integer.parseInt(vo.getClpsscore());    
					// 침식점수
					int corrosionscore = 0;
					if(vo.getCorrosionscore() != null && vo.getCorrosionscore().length() > 0) corrosionscore = Integer.parseInt(vo.getCorrosionscore());    
					// 전석점수
					int bldrstnescore = 0;
					if(vo.getBldrstnescore() != null && vo.getBldrstnescore().length() > 0) bldrstnescore = Integer.parseInt(vo.getBldrstnescore());    
					// 토석류 흔적 점수
					int soiltracescore = 0;
					if(vo.getSoiltracescore() != null && vo.getSoiltracescore().length() > 0) soiltracescore = Integer.parseInt(vo.getSoiltracescore());
					// 주 위험요소 점수
					int mainriskelemscore = 0;
					ArrayList<Integer> mainriskelemarr = new ArrayList<Integer>();
					
					mainriskelemarr.add(clpsscore);
					mainriskelemarr.add(corrosionscore);
					mainriskelemarr.add(bldrstnescore);
					mainriskelemarr.add(soiltracescore);
					
					mainriskelemscore = Collections.max(mainriskelemarr);
					vo.setMainriskelemscore(Integer.toString(mainriskelemscore));
					// 산사태위험등급현황 (토석류는 황폐발생원 점수를 가지고 계산)
					int lndslddgscore = 0;
					if(vo.getScodsltnscore() != null && vo.getScodsltnscore().length() > 0) {
						String lnddgr = vo.getScodsltnscore();// 황폐발생원 점수
						if(lnddgr.equals("1")) {
							svyComptVO.setLndslddgsttus("1등급");
							svyComptVO.setLndslddgsttusscore("3");
						}else if(lnddgr.equals("2")) {
							svyComptVO.setLndslddgsttus("2등급 50% 이상");
							svyComptVO.setLndslddgsttusscore("2");
						}else if(lnddgr.equals("2.5")) {
							svyComptVO.setLndslddgsttus("2등급 50% 미만");
							svyComptVO.setLndslddgsttusscore("1");
						}else if(lnddgr.equals("3")) {
							svyComptVO.setLndslddgsttus("3등급 이하");
							svyComptVO.setLndslddgsttusscore("0");
						}else {
							svyComptVO.setLndslddgsttus("3등급 이하");
							svyComptVO.setLndslddgsttusscore("0");
						}
					}else {
						svyComptVO.setLndslddgsttus("3등급 이하");
						svyComptVO.setLndslddgsttusscore("0");
					}
					lndslddgscore = Integer.parseInt(svyComptVO.getLndslddgsttusscore());
					
					// 산림현황
					int foreststtusscore = 0;
					if(vo.getForeststtusscore() != null && vo.getForeststtusscore().length() > 0) foreststtusscore = Integer.parseInt(vo.getForeststtusscore());
					// 뿌리특성
					int rootcharscore = 0;
					if(vo.getRootcharscore() != null && vo.getRootcharscore().length() > 0) rootcharscore = Integer.parseInt(vo.getRootcharscore());
					// 기타위험요소(선택형)
					int etcdgscore = 0;
					if(vo.getEtcdgscore() != null && vo.getEtcdgscore().length() > 0) etcdgscore = Integer.parseInt(vo.getEtcdgscore());
					
					// 안정해석 점수
					int stabanalscore = 0;
					if(vo.getStabanalscore() != null && vo.getStabanalscore().length() > 0) stabanalscore = Integer.parseInt(vo.getStabanalscore());
					
					
					// 산사태 취약지역 판정표 접수 합계
					int fieldsurveyscore = dglogscore+direffcscore+dgrareaScore+mntntrntscore+soildepscore+mainriskelemscore+lndslddgscore+foreststtusscore+rootcharscore+etcdgscore;
					svyComptVO.setFieldsurveyscore(Integer.toString(fieldsurveyscore));
					
					// 최종판정등급 점수계
					int scoresum = (fieldsurveyscore+stabanalscore);
					svyComptVO.setScoresum(Integer.toString(scoresum));
				}
				
			    svyComptVO.setEvamap("["+EgovStringUtil.getHtmlStrCnvr(evamap)+"]");
			    
			    if(statmap.equals("")) {
			    	svyComptVO.setStatmap("[]");
			    }else {
			    	svyComptVO.setStatmap(EgovStringUtil.getHtmlStrCnvr(statmap));
			    }
			    
			    
		    	lssWkaSvyComptService.updateWkaSvyComptGeom(svyComptVO);
				lssWkaSvyComptService.updateComptLcMap(creatVO);
			}
			
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
	 * 공간정보 데이터 다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sct/selectDownloadWkaSvyShp.do")
	public void selectDownloadWkaSvyShp(
			@RequestParam(value="gid", required=false) String gid, 
			@RequestParam(value="svyId", required=false, defaultValue = "0") String svyId, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		AnalFileVO vo = lssWkaSvyComptService.downloadWkaSvyShp(gid);
		
		
		String outFileNm = svyId.concat("_").concat(EgovDateUtil.toString(new Date(), "yyyyMMddHHmmss", null));
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

			String contentDisposition = EgovBrowserUtil.getDisposition(outFileNm.concat(".").concat(vo.getFileExtsn()),userAgent,"UTF-8");
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