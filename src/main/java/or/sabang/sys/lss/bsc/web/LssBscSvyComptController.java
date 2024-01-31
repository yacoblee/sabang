package or.sabang.sys.lss.bsc.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;


import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.com.utl.sim.service.EgovFileTool;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.mng.log.wlg.service.WebLog;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.LocImgInfoVO;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptService;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class LssBscSvyComptController {
	
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
	
	@Resource(name = "lssBscSvyComptService") 	
	private LssBscSvyComptService lssBscSvyComptService;
	
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
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssBscSvyComptController.class);
	

	/**
	 * 조사완료목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/bsc/sct/selectBscSvyComptList.do")	
    public String selectBscSvyComptList (@ModelAttribute("searchVO") LssBscSvyComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {

		
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
			searchVO.setSvyYear(lssBscSvyComptService.selectBscSvyComptMaxYear());
		}
		Date date = new Date();
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(lssBscSvyComptService.selectBscSvyComptMaxMonth());
//			searchVO.setSvyMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = lssBscSvyComptService.selectBscSvyComptYear();
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
		
		List<LssBscSvyComptVO> SvyComptList = lssBscSvyComptService.selectBscSvyComptList(searchVO);
		model.addAttribute("resultList", SvyComptList);
	
		int totCnt = lssBscSvyComptService.selectBscSvyComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/bsc/sct/svyComptList";		
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/bsc/sct/selectBscSvyComptDetail.do")
	public String selectBscSvyComptDetail(@ModelAttribute("searchVO") LssBscSvyComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		LssBscSvyComptVO vo = lssBscSvyComptService.selectBscSvyComptDetail(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if(vo.getSvyLon() != null && !vo.getSvyLon().equals("")) {
			dmsformal.setDmsLon(vo.getSvyLon());
			vo.setSvyLon(dmsformal.getDmsLon());
		}
		if(vo.getSvyLat() != null && !vo.getSvyLat().equals("")) {
			dmsformal.setDmsLat(vo.getSvyLat());
			vo.setSvyLat(dmsformal.getDmsLat());
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
//		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
//			photo = vo.getPhotoTag().toString();
//			obj = parser.parse(photo);
//			jsonArray = (org.json.simple.JSONArray)obj;
//			model.addAttribute("photo_result", jsonArray);
//		}
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
		if(vo.getLcmap().length() > 2) {				
			String lcmap = vo.getLcmap().toString();
			obj = parser.parse(lcmap);
			lcmapArr = (org.json.simple.JSONArray)obj;
			model.addAttribute("lcmap_result", lcmapArr);
		}		
				
		model.addAttribute("result", vo);			
		return "sys/lss/bsc/sct/svyComptDetail";
	}
	
	/**
	 * 조사완료지 수정화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/bsc/sct/updateBscSvyComptView.do")
	public String updateLssBscSvyComptView(
			@ModelAttribute("searchVO") LssBscSvyComptVO searchVO,
			@ModelAttribute("locImgInfoVO") LocImgInfoVO locImgInfoVO,
			ModelMap model) throws Exception{

		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
//		AdministZoneVO adminVO = new AdministZoneVO();
		
		LssBscSvyComptVO vo = lssBscSvyComptService.selectBscSvyComptDetail(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		if(vo.getSvyLon() != null && !vo.getSvyLon().equals("")) {
			dmsformal.setDmsLon(vo.getSvyLon());
			vo.setSvyLon(dmsformal.getDmsLon());
		}
		if(vo.getSvyLat() != null && !vo.getSvyLat().equals("")) {
			dmsformal.setDmsLat(vo.getSvyLat());
			vo.setSvyLat(dmsformal.getDmsLat());
		}
		
		if(vo.getRskFactorVal() != null) {
			String[] rskFactors = vo.getRskFactorVal().split(",");
			vo.setRskFactors(rskFactors);
		}
		
		model.addAttribute("lssBscSvyCompt", vo);
		
		org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
		JSONParser parser = new JSONParser();
		Object obj;
		String photo = null;
		if(vo.getPhoto() != null && vo.getPhoto().length() > 2) {
			photo = vo.getPhoto().toString();
			obj = parser.parse(photo);
			jsonArray = (org.json.simple.JSONArray)obj;
//			model.addAttribute("photo_result", jsonArray);
			model.addAttribute("orginl_photo_result", jsonArray);
		}
//		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
//			photo = vo.getPhotoTag().toString();
//			obj = parser.parse(photo);
//			jsonArray = (org.json.simple.JSONArray)obj;
//			model.addAttribute("photo_result", jsonArray);
//		}
		if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
			jsonArray = new org.json.simple.JSONArray();
	        photo = vo.getPhotoTag().toString();
	        JSONArray photoArr = new JSONArray(new JSONTokener(photo));
	        JSONArray sortedJsonArray = new JSONArray();
	        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	        for (int i = 0; i < photoArr.length(); i++) {
	           jsonValues.add(photoArr.getJSONObject(i));
	        }
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
		
		comVO.setCodeId("FEI005");
		List<CmmnDetailCode> safty_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("saftyCodeList", safty_result);//보호대상코드목록
		
		comVO.setCodeId("FEI030");
		List<CmmnDetailCode> ncssty_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("ncsstyCodeList", ncssty_result);//실태조사 필요성코드목록
		
		if(vo.getSvyType().equals("토석류")) {
			comVO.setCodeId("FEI006");
			List<CmmnDetailCode> devoccause_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("devOccCauseCodeList", devoccause_result);//황폐발생원코드목록
			
			comVO.setCodeId("FEI007");
			List<CmmnDetailCode> trntAvgSlp_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("trntAvgSlpCodeList", trntAvgSlp_result);//계류평균경사코드목록
			
			comVO.setCodeId("FEI008");
			List<CmmnDetailCode> wclctArea_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("wclctAreaCodeList", wclctArea_result);//집수면적코드목록
			
			comVO.setCodeId("FEI009");
			List<CmmnDetailCode> tlTrntLt_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("tlTrntLtCodeList", tlTrntLt_result);//총계류길이코드목록
			if(vo.getSvyYear().equals("2021")) {
				comVO.setCodeId("FEI010");
				List<CmmnDetailCode> distBmdsbRate_result = cmmUseService.selectCmmCodeDetail(comVO);
				model.addAttribute("distBmdsbRateCodeList", distBmdsbRate_result);//전석분포비율코드목록
			}else {
				comVO.setCodeId("FEI042");
				List<CmmnDetailCode> rskFactor_result = cmmUseService.selectCmmCodeDetail(comVO);
				model.addAttribute("rskFactorList", rskFactor_result);//위험인자요소목록
			}
			
		}else {
			comVO.setCodeId("FEI011");
			List<CmmnDetailCode> sLen_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("sLenCodeList", sLen_result);//경사길이코드목록
			
			comVO.setCodeId("FEI012");
			List<CmmnDetailCode> slope_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("slopeCodeList", slope_result);//경사도코드목록
			
			comVO.setCodeId("FEI013");
			List<CmmnDetailCode> sForm_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("sFormCodeList", sForm_result);//사면형코드목록
			
			comVO.setCodeId("FEI014");
			List<CmmnDetailCode> frstFr_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("frstFrCodeList", frstFr_result);//임상코드목록
			
			comVO.setCodeId("FEI015");
			List<CmmnDetailCode> prntRck_result = cmmUseService.selectCmmCodeDetail(comVO);
			model.addAttribute("prntRckCodeList", prntRck_result);//모암코드목록
			if(!vo.getSvyYear().equals("2021")) {
				comVO.setCodeId("FEI043");
				List<CmmnDetailCode> rskFactor_result = cmmUseService.selectCmmCodeDetail(comVO);
				model.addAttribute("rskFactorList", rskFactor_result);//위험요인요소목록
			}
		}
		
		//관할1목록코드를 코드정보로부터 조회
		
		comVO.setCodeId("FEI001");
		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
		if(vo.getSvyRegion1() != null) {
			comVO.setCodeId(vo.getRegion1GroupId());
			List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(comVO);
			model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
		}
				
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(searchVO.getSvySd() != null) {
//			adminVO.setSdCode(searchVO.getSvySd());
			adminVO.setSdNm(vo.getSvySd());
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}

		//읍면동코드 조회
		if(searchVO.getSvySgg() != null) {
//			adminVO.setSggCode(searchVO.getSvySgg());
			adminVO.setSdNm(vo.getSvySd());
			adminVO.setSggNm(vo.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}

		//리코드 조회
		if(searchVO.getSvyRi() != null) {
//			adminVO.setEmdCode(searchVO.getSvyEmd());
			adminVO.setSdNm(vo.getSvySd());
			adminVO.setSggNm(vo.getSvySgg());
			adminVO.setEmdNm(vo.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		//위치도 조회 처리		
		HashMap<String, Object> schMap = new HashMap<>();
		locImgInfoVO.setGid(Integer.parseInt(searchVO.getGid()));
		locImgInfoVO.setSvySe("BSC");
		
		schMap.put("SE",locImgInfoVO.getSvySe());
		schMap.put("type", vo.getSvyType());
		schMap.put("gid", Integer.parseInt(vo.getGid()));		
		List<EgovMap> locList = extFieldBookService.selectComptLcMapLonLat(schMap);
				
		if(locList.size() > 0) {
			if(locList.get(0).containsKey("lon") && locList.get(0).containsKey("lat") ) {
				String center = String.join(",", (String[]) new String[] {locList.get(0).get("lon").toString(),locList.get(0).get("lat").toString()});
				String svyid = vo.getSvyId();
				String se = locImgInfoVO.getSvySe();
				String marker = "";
				String label = "";
				if(locList.get(0).get("marker").toString().split(";").length > 1) {
					String[] markers = locList.get(0).get("marker").toString().split(";");
					String[] labels = locList.get(0).get("label").toString().split(";");
					if(markers[0].toString().equals(markers[1].toString())) {
						marker += labels[0].toString();
					}else if(se.equals("BSC")) {			
						marker += markers[0].toString().concat(";");
						label += svyid + "_시점".concat(";");
						marker += markers[1].toString();
						label += svyid + "_종점";
					}else {
						marker += markers[0].toString().concat(";");
						label += labels[0].toString().concat(";");
						marker += markers[1].toString();
						label += labels[1].toString();
					}
				} else {
					marker += locList.get(0).get("marker").toString();
					label += locList.get(0).get("label").toString();
				}
				
				int w = 586;
				int h = 516;
				int zoom = extFieldBookService.selectLocImgInfo(locImgInfoVO);
				
				org.json.simple.JSONArray paramArr = new org.json.simple.JSONArray();
				for(int i=0; i<locList.size(); i++) {
					JSONObject param = new JSONObject();
					param.put("center",center);
					param.put("svyid",svyid);
					param.put("se",se);
					param.put("marker",marker);
					param.put("label",label.toString());
					param.put("zoom", zoom);
					param.put("w",w);
					param.put("h",h);
					
					obj = parser.parse(param.toString());
					paramArr.add(obj);
					model.addAttribute("mapParam", paramArr.get(0).toString().replaceAll("\"", "'"));
				}
				
				model.addAttribute("orginl_zoom", zoom);
			}
		}
		
		return "sys/lss/bsc/sct/svyComptUpdt";
	}
	
	/**
	 * 조사완료지를 수정한다.
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/bsc/sct/updateBscSvyCompt.do")
    public ModelAndView updateLssBscSvyCompt(@ModelAttribute("lssBscSvyComptVO") LssBscSvyComptVO svyComptVO, BindingResult bindingResult, Model model) throws Exception {
		beanValidator.validate(svyComptVO, bindingResult);
		ModelAndView mv = new ModelAndView("jsonView");
		LocImgInfoVO locImgInfoVO = new LocImgInfoVO();
		HashMap<String, Object> schMap = new HashMap<>();
		svyComptVO.setPhotoTagList(svyComptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		try {
			lssBscSvyComptService.updateBscSvyCompt(svyComptVO);
			
			svyComptVO.setMapType("SATELLITE");
			
			schMap.put("mst_id", Integer.parseInt(svyComptVO.getMstId()));
			List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
			schMap.put("SE","BSC");
			schMap.put("type",svyComptVO.getSvyType());
			schMap.put("gid",Integer.parseInt(svyComptVO.getGid()));
			schMap.put("_label", svyComptVO.getSvyId());
			schMap.put("path", fieldBookUploadPath.concat("/").concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/")); //저장경로
			schMap.put("midPath","/".concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/"));
			schMap.put("zoom",Integer.parseInt(svyComptVO.getChangedZoom()));
			schMap.put("mapType",svyComptVO.getMapType());
			LOGGER.info("====== 위치도 생성 시작 ======");			
			extFieldBookService.updateComptLcMap(schMap);
			LOGGER.info("====== 위치도 생성 종료 ======");
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
	@RequestMapping(value = "/sys/lss/bsc/sct/deleteBscSvyCompt.do")
	public ModelAndView deleteLssBscSvyCompt(LssBscSvyComptVO svyComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			lssBscSvyComptService.deleteBscSvyCompt(svyComptVO);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/bsc/sct/selectBscSvyComptListExcel.do")
	public ModelAndView selectBscSvyComptListExcel(LssBscSvyComptVO svyComptVO) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("bscExcelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	HashMap<?, ?> _map = (HashMap<?, ?>)lssBscSvyComptService.selectBscSvyComptListExcel(svyComptVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    	
    	String fileNm = "기초조사완료지_".concat(formater.format(new Date()).toString());
    	
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
	@RequestMapping(value = "/sys/lss/bsc/sct/updateBscSvyComptPopup.do")
	public String insertCnrsSpceSldPopup(
			@ModelAttribute("svyComptVO") LssBscSvyComptVO svyComptVO,
			ModelMap model) throws Exception {
		
		return "sys/lss/bsc/sct/updateBscSvyComptPopup";
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
	@RequestMapping(value = "/sys/lss/bsc/sct/updateBscSvyComptExcel.do")
	public ModelAndView updateBscSvyComptExcel(
			@ModelAttribute("svyComptVO") LssBscSvyComptVO svyComptVO,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		JSONObject results = lssBscSvyComptService.updateBscSvyComptExcel(svyComptVO, mFile);
		
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
	@RequestMapping(value = "/sys/lss/bsc/sct/updateLocReCreatePopup.do")
	public String updateLocReCreatePopup(ModelMap model) throws Exception {
		
		LocReCreateVO searchMap = new LocReCreateVO();
		EgovMap dateMap = lssBscSvyComptService.selectLastUpdateMinMaxDate(searchMap);
		
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
	@RequestMapping(value = "/sys/lss/bsc/sct/selectLocReCeateCnt.do")
	public ModelAndView selectLocReCeateCnt(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap dateMap = lssBscSvyComptService.selectLastUpdateMinMaxDate(searchVO);
			
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
	@RequestMapping(value = "/sys/lss/bsc/sct/updateLocReCreate.do")
	public ModelAndView updateLocReCreate(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			List<?> list = lssBscSvyComptService.updateLocReCreate(searchVO);
			for (int i = 0; i < list.size(); i++) {
				LOGGER.info("재생성 시작");
				HashMap<String, Object> schMap = new HashMap<String, Object>();
				EgovMap map = (EgovMap)list.get(i);
				
				schMap.put("gid", map.get("gid"));
				schMap.put("type", map.get("svytype"));
				schMap.put("path", fieldBookUploadPath.concat("/").concat(map.get("mstpath").toString()).concat(".ncx/"));
				schMap.put("midPath","/".concat(map.get("mstpath").toString()).concat(".ncx/"));
				schMap.put("_label", map.get("svylabel"));
				schMap.put("SE", map.get("svyse"));
				schMap.put("mapType", "SATELLITE");
				
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
	@RequestMapping(value = "/sys/lss/bsc/sct/updatePhotoPopup.do")
	public String updatePhotoPopup(ModelMap model, LssBscSvyComptVO searchVO) throws Exception {
		
		EgovMap photoMap = lssBscSvyComptService.selectSvyPhotoNullList(searchVO);
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
	@RequestMapping(value = "/sys/lss/bsc/sct/selectPhotoNullCnt.do")
	public ModelAndView selectPhotoNullCnt(@ModelAttribute("searchVO") LssBscSvyComptVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap photoMap = lssBscSvyComptService.selectSvyPhotoNullList(searchVO);
			
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
	@RequestMapping(value = "/sys/lss/bsc/sct/updatePhotoList.do")
	public ModelAndView updatePhotoList(@ModelAttribute("searchVO") LssBscSvyComptVO searchVO, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		String[] photoTagNmArr= {"1.전경사진","2.유출구사진","3.현황사진1","4.현황사진2"};
		String svyId = "";
		try {
			List<?> list = lssBscSvyComptService.updatePhotoList(searchVO);
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
					lssBscSvyComptService.updateBscSvyComptPhotoList(searchVO);
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
}