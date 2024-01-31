package or.sabang.sys.lss.lcp.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import egovframework.com.cmm.EgovMessageSource;
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
import or.sabang.sys.fck.apr.service.FckAprComptService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class LssLcpSvyComptController {
	
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
	
	@Resource(name = "fckAprComptService") 	
	private FckAprComptService fckAprComptService;
	
	@Resource(name = "lssLcpSvyComptService") 	
	private LssLcpSvyComptService lssLcpSvyComptService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** 첨부파일 위치 지정  => globals.properties */
//    private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.compt");
//    private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
    /** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath");
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    
	private static final Logger LOGGER = LoggerFactory.getLogger(LssLcpSvyComptController.class);
	

	/**
	 * 조사완료목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sct/selectLcpSvyComptList.do")	
    public String selectLcpSvyComptList (@ModelAttribute("searchVO") LssLcpSvyComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {
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
			searchVO.setSvyYear(lssLcpSvyComptService.selectLcpSvyComptMaxYear());
		}
		Date date = new Date();
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(lssLcpSvyComptService.selectLcpSvyComptMaxMonth());
//			searchVO.setSvyMonth(new SimpleDateFormat("MM").format(date));
		}
		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		
		//연도코드 조회
		List<?> year_result = lssLcpSvyComptService.selectLcpSvyComptYear();
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
		
		List<LssLcpSvyComptVO> SvyComptList = lssLcpSvyComptService.selectLcpSvyComptList(searchVO);
		model.addAttribute("resultList", SvyComptList);
	
		int totCnt = lssLcpSvyComptService.selectLcpSvyComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/lss/lcp/sct/svyComptList";		
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sct/selectLcpSvyComptDetail.do")
	public String selectLcpSvyComptDetail(@ModelAttribute("searchVO") LssLcpSvyComptVO searchVO,ModelMap model,HttpServletRequest request) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		LssLcpSvyComptVO vo = lssLcpSvyComptService.selectLcpSvyComptDetail(searchVO);

		DmsFormalization dmsformal = new DmsFormalization();
		
		String lonlat = "";
		
		if(vo.getSvyLat() != null && !vo.getSvyLat().equals("")) {
			dmsformal.setDmsLat(vo.getSvyLat());
			vo.setSvyLat(dmsformal.getDmsLat());
			lonlat += dmsformal.getDmsLat()+ " ";
		}
		if(vo.getSvyLon() != null && !vo.getSvyLon().equals("")) {
			dmsformal.setDmsLon(vo.getSvyLon());
			vo.setSvyLon(dmsformal.getDmsLon());
			lonlat += dmsformal.getDmsLon();
		}
		vo.setSvyLatLon(lonlat);
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		comVO.setCodeId("FEI046");
		List<CmmnDetailCode> existence_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("existenceCodeList", existence_result);//유무 코드목록
		
		comVO.setCodeId("FEI047");
		List<CmmnDetailCode> frstfrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("frstfrvalCodeList", frstfrval_result);//임상 코드목록
		
		comVO.setCodeId("FEI048");
		List<CmmnDetailCode> prrcklargrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("prrcklargrvalCodeList", prrcklargrval_result);//모암 코드목록
		
		comVO.setCodeId("FEI049");
		List<CmmnDetailCode> rokwthrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("rokwthrvalCodeList", rokwthrval_result);//암석풍화 코드목록
		
		comVO.setCodeId("FEI050");
		List<CmmnDetailCode> disctnuplnslpval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("disctnuplnslpvalCodeList", disctnuplnslpval_result);//불연속면과 사면방향 코드목록
		
		comVO.setCodeId("FEI051");
		List<CmmnDetailCode> disctnuplnintvlval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("disctnuplnintvlvalCodeList", disctnuplnintvlval_result);//불연속면간격 코드목록
		
		comVO.setCodeId("FEI052");
		List<CmmnDetailCode> soilclassbval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soilclassbvalCodeList", soilclassbval_result);//토성 코드목록
		
		comVO.setCodeId("FEI053");
		List<CmmnDetailCode> soilwtrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soilwtrvalCodeList", soilwtrval_result);//토양수분 코드목록
		
		comVO.setCodeId("FEI054");
		List<CmmnDetailCode> talusat_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("talusatCodeList", talusat_result);//너덜 코드목록
		
		comVO.setCodeId("FEI055");
		List<CmmnDetailCode> soildeptscore_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soildeptscoreCodeList", soildeptscore_result);//이동대(토심)깊이 코드목록
		
		comVO.setCodeId("FEI056");
		List<CmmnDetailCode> tpgrphval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("tpgrphvalCodeList", tpgrphval_result);//지형구분 코드목록
				
		comVO.setCodeId("FEI057");
		List<CmmnDetailCode> formval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("formvalCodeList", formval_result);//지형형태 코드목록
		
		comVO.setCodeId("FEI059");
		List<CmmnDetailCode> slprngval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("slprngvalCodeList", slprngval_result);//사면경사 코드목록

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
		
		if(vo.getSttusprnt() != null) vo.setSttusprnt(vo.getSttusprnt().replaceAll("\"", ""));
		
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
		
		Object lcpObj;
		// 땅밀림현황_균열
		org.json.simple.JSONArray lcpSttusArr_crk = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_crk() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_crk = new JSONArray(vo.getLcpsttus_crk());
			if(lcpSttus_crk.length() > 1 )  {
				for (int k=0; k<lcpSttus_crk.length(); k++) {
					JSONObject lcpsttus_crkObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_crk.getJSONObject(k).has("시점_경도") ? lcpSttus_crk.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_crk.getJSONObject(k).has("시점_위도") ? lcpSttus_crk.getJSONObject(k).get("시점_위도").toString() : null);
					projMap.put("epx",lcpSttus_crk.getJSONObject(k).has("종점_경도") ? lcpSttus_crk.getJSONObject(k).get("종점_경도").toString() : null);
					projMap.put("epy",lcpSttus_crk.getJSONObject(k).has("종점_위도") ? lcpSttus_crk.getJSONObject(k).get("종점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					lcpsttus_crkObj.put("occur", lcpSttus_crk.getJSONObject(k).has("발생방향") ? (String) lcpSttus_crk.getJSONObject(k).get("발생방향").toString() : "");
					lcpsttus_crkObj.put("bpx", lcpSttus_crk.getJSONObject(k).has("시점_경도") ? projList.get(0).get("exBpx") : "");
					lcpsttus_crkObj.put("bpy", lcpSttus_crk.getJSONObject(k).has("시점_위도") ? projList.get(0).get("exBpy") : "");
					lcpsttus_crkObj.put("bpz", lcpSttus_crk.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_crk.getJSONObject(k).get("시점_고도").toString() : "");
					lcpsttus_crkObj.put("epx", lcpSttus_crk.getJSONObject(k).has("종점_경도") ? projList.get(0).get("exEpx") : "");
					lcpsttus_crkObj.put("epy", lcpSttus_crk.getJSONObject(k).has("종점_위도") ? projList.get(0).get("exEpy") : "");
					lcpsttus_crkObj.put("epz", lcpSttus_crk.getJSONObject(k).has("종점_고도") ? (String) lcpSttus_crk.getJSONObject(k).get("종점_고도").toString() : "");					
					lcpsttus_crkObj.put("height", lcpSttus_crk.getJSONObject(k).has("높이") ? (String) lcpSttus_crk.getJSONObject(k).get("높이").toString() : "0");
					lcpsttus_crkObj.put("length", lcpSttus_crk.getJSONObject(k).has("연장") ? (String) lcpSttus_crk.getJSONObject(k).get("연장").toString() : "0");
					lcpsttus_crkObj.put("depth", lcpSttus_crk.getJSONObject(k).has("깊이") ? (String) lcpSttus_crk.getJSONObject(k).get("깊이").toString() : "0");
					obj = parser.parse(lcpsttus_crkObj.toString());
					lcpSttusArr_crk.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_crk_result", lcpSttusArr_crk);
		// 땅밀림현황_단차
		org.json.simple.JSONArray lcpSttusArr_stp = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_stp() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_stp = new JSONArray(vo.getLcpsttus_stp());
			if(lcpSttus_stp.length() > 1 )  {
				for (int k=0; k<lcpSttus_stp.length(); k++) {
					JSONObject lcpsttus_stpObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_stp.getJSONObject(k).has("시점_경도") ? lcpSttus_stp.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_stp.getJSONObject(k).has("시점_위도") ? lcpSttus_stp.getJSONObject(k).get("시점_위도").toString() : null);
					projMap.put("epx",lcpSttus_stp.getJSONObject(k).has("종점_경도") ? lcpSttus_stp.getJSONObject(k).get("종점_경도").toString() : null);
					projMap.put("epy",lcpSttus_stp.getJSONObject(k).has("종점_위도") ? lcpSttus_stp.getJSONObject(k).get("종점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					lcpsttus_stpObj.put("occur", lcpSttus_stp.getJSONObject(k).has("발생방향") ? (String) lcpSttus_stp.getJSONObject(k).get("발생방향").toString() : "");
					lcpsttus_stpObj.put("bpx", lcpSttus_stp.getJSONObject(k).has("시점_경도") ? projList.get(0).get("exBpx") : "");
					lcpsttus_stpObj.put("bpy", lcpSttus_stp.getJSONObject(k).has("시점_위도") ? projList.get(0).get("exBpy") : "");
					lcpsttus_stpObj.put("bpz", lcpSttus_stp.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_stp.getJSONObject(k).get("시점_고도").toString() : "");
					lcpsttus_stpObj.put("epx", lcpSttus_stp.getJSONObject(k).has("종점_경도") ? projList.get(0).get("exEpx") : "");
					lcpsttus_stpObj.put("epy", lcpSttus_stp.getJSONObject(k).has("종점_위도") ? projList.get(0).get("exEpy") : "");
					lcpsttus_stpObj.put("epz", lcpSttus_stp.getJSONObject(k).has("종점_고도") ? (String) lcpSttus_stp.getJSONObject(k).get("종점_고도").toString() : "");					
					lcpsttus_stpObj.put("height", lcpSttus_stp.getJSONObject(k).has("높이") ? (String) lcpSttus_stp.getJSONObject(k).get("높이").toString() : "0");
					lcpsttus_stpObj.put("length", lcpSttus_stp.getJSONObject(k).has("연장") ? (String) lcpSttus_stp.getJSONObject(k).get("연장").toString() : "0");
					lcpsttus_stpObj.put("depth", lcpSttus_stp.getJSONObject(k).has("깊이") ? (String) lcpSttus_stp.getJSONObject(k).get("깊이").toString() : "0");
					obj = parser.parse(lcpsttus_stpObj.toString());
					lcpSttusArr_stp.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_stp_result", lcpSttusArr_stp);
		// 땅밀림현황_수목이상생장
		org.json.simple.JSONArray lcpSttusArr_wdpt = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_wdpt() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_wdpt = new JSONArray(vo.getLcpsttus_wdpt());
			if(lcpSttus_wdpt.length() > 1 )  {
				for (int k=0; k<lcpSttus_wdpt.length(); k++) {
					JSONObject lcpsttus_wdptObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();
					projMap.put("bpx",lcpSttus_wdpt.getJSONObject(k).has("시점_경도") ? lcpSttus_wdpt.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_wdpt.getJSONObject(k).has("시점_위도") ? lcpSttus_wdpt.getJSONObject(k).get("시점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					lcpsttus_wdptObj.put("type", lcpSttus_wdpt.getJSONObject(k).has("유형") ? (String) lcpSttus_wdpt.getJSONObject(k).get("유형").toString() : "");	//유형
					lcpsttus_wdptObj.put("abgrow", lcpSttus_wdpt.getJSONObject(k).has("이상생장_방향") ? (String) lcpSttus_wdpt.getJSONObject(k).get("이상생장_방향").toString() : ""); //이상생장_방향
					lcpsttus_wdptObj.put("chstht", lcpSttus_wdpt.getJSONObject(k).has("흉고직경") ? (String) lcpSttus_wdpt.getJSONObject(k).get("흉고직경").toString() : ""); //흉고직경
					lcpsttus_wdptObj.put("treeht", lcpSttus_wdpt.getJSONObject(k).has("수고") ? (String) lcpSttus_wdpt.getJSONObject(k).get("수고").toString() : ""); //수고
					lcpsttus_wdptObj.put("occurx", lcpSttus_wdpt.getJSONObject(k).has("발생범위_가로") ? (String) lcpSttus_wdpt.getJSONObject(k).get("발생범위_가로").toString() : ""); //발생범위_가로
					lcpsttus_wdptObj.put("occury", lcpSttus_wdpt.getJSONObject(k).has("발생범위_세로") ? (String) lcpSttus_wdpt.getJSONObject(k).get("발생범위_세로").toString() : ""); //발생범위_세로
					lcpsttus_wdptObj.put("surfht", lcpSttus_wdpt.getJSONObject(k).has("지표높이") ? (String) lcpSttus_wdpt.getJSONObject(k).get("지표높이").toString() : ""); //지표높이
					lcpsttus_wdptObj.put("treespc", lcpSttus_wdpt.getJSONObject(k).has("수종") ? (String) lcpSttus_wdpt.getJSONObject(k).get("수종").toString() : ""); //수종
					lcpsttus_wdptObj.put("treeapr", lcpSttus_wdpt.getJSONObject(k).has("임상") ? (String) lcpSttus_wdpt.getJSONObject(k).get("임상").toString() : ""); //임상
					lcpsttus_wdptObj.put("bpx", lcpSttus_wdpt.getJSONObject(k).has("시점_경도") ? projList.get(0).get("exBpx") : "");
					lcpsttus_wdptObj.put("bpy", lcpSttus_wdpt.getJSONObject(k).has("시점_위도") ? projList.get(0).get("exBpy") : "");
					lcpsttus_wdptObj.put("bpz", lcpSttus_wdpt.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_wdpt.getJSONObject(k).get("시점_고도").toString() : "");
					obj = parser.parse(lcpsttus_wdptObj.toString());
					lcpSttusArr_wdpt.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_wdpt_result", lcpSttusArr_wdpt);
		// 땅밀림현황_구조물이상
		org.json.simple.JSONArray lcpSttusArr_strct = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_strct() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_strct = new JSONArray(vo.getLcpsttus_strct());
			if(lcpSttus_strct.length() > 1 )  {
				for (int k=0; k<lcpSttus_strct.length(); k++) {
					JSONObject lcpsttus_strctObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_strct.getJSONObject(k).has("시점_경도") ? lcpSttus_strct.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_strct.getJSONObject(k).has("시점_위도") ? lcpSttus_strct.getJSONObject(k).get("시점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					lcpsttus_strctObj.put("strctype", lcpSttus_strct.getJSONObject(k).has("구조물종류") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물종류").toString() : ""); //구조물종류
					lcpsttus_strctObj.put("strcsizeht", lcpSttus_strct.getJSONObject(k).has("구조물크기_높이") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물크기_높이").toString() : ""); //구조물크기_높이
					lcpsttus_strctObj.put("strcsizewd", lcpSttus_strct.getJSONObject(k).has("구조물크기_가로") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물크기_가로").toString() : ""); //구조물크기_가로
					lcpsttus_strctObj.put("strcsizedp", lcpSttus_strct.getJSONObject(k).has("구조물크기_세로") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물크기_세로").toString() : ""); //구조물크기_세로
					lcpsttus_strctObj.put("occurloc", lcpSttus_strct.getJSONObject(k).has("발생위치") ? (String) lcpSttus_strct.getJSONObject(k).get("발생위치").toString() : ""); //발생위치
					lcpsttus_strctObj.put("abdegre", lcpSttus_strct.getJSONObject(k).has("이상정도") ? (String) lcpSttus_strct.getJSONObject(k).get("이상정도").toString() : ""); //이상정도
					lcpsttus_strctObj.put("bpx", lcpSttus_strct.getJSONObject(k).has("시점_경도") ? projList.get(0).get("exBpx") : "");
					lcpsttus_strctObj.put("bpy", lcpSttus_strct.getJSONObject(k).has("시점_위도") ? projList.get(0).get("exBpy") : "");
					lcpsttus_strctObj.put("bpz", lcpSttus_strct.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_strct.getJSONObject(k).get("시점_고도").toString() : "");
					obj = parser.parse(lcpsttus_strctObj.toString());
					lcpSttusArr_strct.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_strct_result", lcpSttusArr_strct);
		// 땅밀림현황_지하수용출
		org.json.simple.JSONArray lcpSttusArr_ugrwtr = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_ugrwtr() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_ugrwtr = new JSONArray(vo.getLcpsttus_ugrwtr());
			if(lcpSttus_ugrwtr.length() > 1 )  {
				for (int k=0; k<lcpSttus_ugrwtr.length(); k++) {
					JSONObject lcpsttus_ugrwtrtObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_ugrwtr.getJSONObject(k).has("시점_경도") ? lcpSttus_ugrwtr.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_ugrwtr.getJSONObject(k).has("시점_위도") ? lcpSttus_ugrwtr.getJSONObject(k).get("시점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					lcpsttus_ugrwtrtObj.put("occurloc", lcpSttus_ugrwtr.getJSONObject(k).has("발생위치") ? (String) lcpSttus_ugrwtr.getJSONObject(k).get("발생위치").toString() : ""); //발생위치
					lcpsttus_ugrwtrtObj.put("occuramt", lcpSttus_ugrwtr.getJSONObject(k).has("발생정도") ? (String) lcpSttus_ugrwtr.getJSONObject(k).get("발생정도").toString() : ""); //발생정도
					lcpsttus_ugrwtrtObj.put("bpx", lcpSttus_ugrwtr.getJSONObject(k).has("시점_경도") ? projList.get(0).get("exBpx") : "");
					lcpsttus_ugrwtrtObj.put("bpy", lcpSttus_ugrwtr.getJSONObject(k).has("시점_위도") ? projList.get(0).get("exBpy") : "");
					lcpsttus_ugrwtrtObj.put("bpz", lcpSttus_ugrwtr.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_ugrwtr.getJSONObject(k).get("시점_고도").toString() : "");
					obj = parser.parse(lcpsttus_ugrwtrtObj.toString());
					lcpSttusArr_ugrwtr.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_ugrwtr_result", lcpSttusArr_ugrwtr);
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		String access = lssLcpSvyComptService.selectAuthorCheck(commandMap);
		
		model.addAttribute("access", access);			
		model.addAttribute("result", vo);
//		model.addAttribute("lcpsttus_result", lcpSttusArr);
		return "sys/lss/lcp/sct/svyComptDetail";
	}
	
	/**
	 * 조사완료지 수정화면으로 이동한다.
	 * @param stripLandVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sct/updateLcpSvyComptView.do")
	public String updateLssLcpSvyComptView(@ModelAttribute("searchVO") LssLcpSvyComptVO searchVO,
			ModelMap model, HttpServletRequest request) throws Exception{

		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
//		AdministZoneVO adminVO = new AdministZoneVO();
		LssLcpSvyComptVO vo = lssLcpSvyComptService.selectLcpSvyComptDetail(searchVO);
		
		DmsFormalization dmsformal = new DmsFormalization();
		
		String lonlat = "";
		if(vo.getSvyLat() != null && !vo.getSvyLat().equals("")) {
			dmsformal.setDmsLat(vo.getSvyLat());
			vo.setSvyLat(dmsformal.getDmsLat());
			lonlat += dmsformal.getDmsLat()+" ";
		}
		if(vo.getSvyLon() != null && !vo.getSvyLon().equals("")) {
			dmsformal.setDmsLon(vo.getSvyLon());
			vo.setSvyLon(dmsformal.getDmsLon());
			lonlat += dmsformal.getDmsLon();
		}
		vo.setSvyLatLon(lonlat);
		
		model.addAttribute("result", vo);
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		comVO.setCodeId("FEI046");
		List<CmmnDetailCode> existence_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("existenceCodeList", existence_result);//유무 코드목록
		
		comVO.setCodeId("FEI047");
		List<CmmnDetailCode> frstfrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("frstfrvalCodeList", frstfrval_result);//임상 코드목록
		
		comVO.setCodeId("FEI048");
		List<CmmnDetailCode> prrcklargrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("prrcklargrvalCodeList", prrcklargrval_result);//모암 코드목록
		
		comVO.setCodeId("FEI049");
		List<CmmnDetailCode> rokwthrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("rokwthrvalCodeList", rokwthrval_result);//암석풍화 코드목록
		
		comVO.setCodeId("FEI050");
		List<CmmnDetailCode> disctnuplnslpval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("disctnuplnslpvalCodeList", disctnuplnslpval_result);//불연속면과 사면방향 코드목록
		
		comVO.setCodeId("FEI051");
		List<CmmnDetailCode> disctnuplnintvlval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("disctnuplnintvlvalCodeList", disctnuplnintvlval_result);//불연속면간격 코드목록
		
		comVO.setCodeId("FEI052");
		List<CmmnDetailCode> soilclassbval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soilclassbvalCodeList", soilclassbval_result);//토성 코드목록
		
		comVO.setCodeId("FEI053");
		List<CmmnDetailCode> soilwtrval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soilwtrvalCodeList", soilwtrval_result);//토양수분 코드목록
		
		comVO.setCodeId("FEI054");
		List<CmmnDetailCode> talusat_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("talusatCodeList", talusat_result);//너덜 코드목록
		
		comVO.setCodeId("FEI055");
		List<CmmnDetailCode> soildeptscore_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soildeptscoreCodeList", soildeptscore_result);//이동대(토심)깊이 코드목록
		
		comVO.setCodeId("FEI056");
		List<CmmnDetailCode> tpgrphval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("tpgrphvalCodeList", tpgrphval_result);//지형구분 코드목록
				
		comVO.setCodeId("FEI057");
		List<CmmnDetailCode> formval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("formvalCodeList", formval_result);//지형형태 코드목록
		
		comVO.setCodeId("FEI058");
		List<CmmnDetailCode> lngformval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("lngformvalCodeList", lngformval_result);//임지이용상태 코드목록
		
		comVO.setCodeId("FEI059");
		List<CmmnDetailCode> slprngval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("slprngvalCodeList", slprngval_result);//사면경사 코드목록
		
		comVO.setCodeId("FEI061");
		List<CmmnDetailCode> disctnupln_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("disctnuplnCodeList", disctnupln_result);//불연속면 종류 코드목록
		
		comVO.setCodeId("FEI062");
		List<CmmnDetailCode> disctnuplndrcno_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("disctnuplndrcnoCodeList", disctnuplndrcno_result);//불연속면 방향수 코드목록
		
		comVO.setCodeId("FEI063");
		List<CmmnDetailCode> soilty_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soiltyCodeList", soilty_result);//토양형 방향수 코드목록
		
		comVO.setCodeId("FEI064");
		List<CmmnDetailCode> soilclassb_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("soilclassbCodeList", soilclassb_result);//토성 B층 기준 방향수 코드목록
		
		comVO.setCodeId("FEI065");
		List<CmmnDetailCode> rokexpsr_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("rokexpsrCodeList", rokexpsr_result);//암석노출도(%) 코드목록
		
		comVO.setCodeId("FEI066");
		List<CmmnDetailCode> arealcval_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("arealcvalCodeList", arealcval_result);//조사지역위치 코드목록

		comVO.setCodeId("FEI067");
		List<CmmnDetailCode> maintreeNeedle_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("maintreeNeedleCodeList", maintreeNeedle_result);//주요수종(침엽수) 코드목록
	
		comVO.setCodeId("FEI068");
		List<CmmnDetailCode> maintreeBroad_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("maintreeBroadCodeList", maintreeBroad_result);//주요수종(활엽수) 코드목록
	
		comVO.setCodeId("FEI069");
		List<CmmnDetailCode> maintreeGrBroad_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("maintreeGrBroadCodeList", maintreeGrBroad_result);//주요수종(상록활엽수) 코드목록
	
		comVO.setCodeId("FEI070");
		List<CmmnDetailCode> maintreeBamboo_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("maintreeBambooCodeList", maintreeBamboo_result);//주요수종(죽림) 코드목록
		
		comVO.setCodeId("FEI090");
		List<CmmnDetailCode> lcpSttus_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("lcpSttusCodeList", lcpSttus_result);//땅밀림징후 코드목록
		

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
		
		if(vo.getSttusprnt() != null) vo.setSttusprnt(vo.getSttusprnt().replaceAll("\"", ""));
//		if(vo.getSvyType().equals("사방댐 외관점검") || vo.getSvyType().equals("계류보전 외관점검")) {
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
		
		Object lcpObj;
		// 땅밀림현황_균열
		org.json.simple.JSONArray lcpSttusArr_crk = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_crk() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_crk = new JSONArray(vo.getLcpsttus_crk());
			if(lcpSttus_crk.length() > 1 )  {
				for (int k=0; k<lcpSttus_crk.length(); k++) {
					JSONObject lcpsttus_crkObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_crk.getJSONObject(k).has("시점_경도") ? lcpSttus_crk.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_crk.getJSONObject(k).has("시점_위도") ? lcpSttus_crk.getJSONObject(k).get("시점_위도").toString() : null);
					projMap.put("epx",lcpSttus_crk.getJSONObject(k).has("종점_경도") ? lcpSttus_crk.getJSONObject(k).get("종점_경도").toString() : null);
					projMap.put("epy",lcpSttus_crk.getJSONObject(k).has("종점_위도") ? lcpSttus_crk.getJSONObject(k).get("종점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					if(projList.get(0) != null) {
						double bpx = Double.parseDouble(projList.get(0).get("bpx").toString());
						double bpy = Double.parseDouble(projList.get(0).get("bpy").toString());
						double epx = Double.parseDouble(projList.get(0).get("epx").toString());
						double epy = Double.parseDouble(projList.get(0).get("epy").toString());
						
						// 시점 경도
						int bpx1 = (int) Math.floor(bpx);
						int bpx2 = (int) Math.floor((bpx - bpx1) * 60);
						double bpx3 = (((bpx - bpx1) * 60) - bpx2) * 60;
						// 시점 위도
						int bpy1 = (int) Math.floor(bpy);
						int bpy2 = (int) Math.floor((bpy - bpy1) * 60);
						double bpy3 = (((bpy - bpy1) * 60) - bpy2) * 60;
						// 종검 경도
						int epx1 = (int) Math.floor(epx);
						int epx2 = (int) Math.floor((epx - epx1) * 60);
						double epx3 = (((epx - epx1) * 60) - epx2) * 60;
						// 종점 위도
						int epy1 = (int) Math.floor(epy);
						int epy2 = (int) Math.floor((epy - epy) * 60);
						double epy3 = (((epy - epy1) * 60) - epy2) * 60;
						
						String bpxText = bpx1+" "+bpx2+" "+bpx3;
						String bpyText = bpy1+" "+bpy2+" "+bpy3;
						String epxText = epx1+" "+epx2+" "+epx3;
						String epyText = epy1+" "+epy2+" "+epy3;
						
						lcpsttus_crkObj.put("bpx", lcpSttus_crk.getJSONObject(k).has("시점_경도") ? bpxText : "");
						lcpsttus_crkObj.put("bpy", lcpSttus_crk.getJSONObject(k).has("시점_위도") ? bpyText : "");
						lcpsttus_crkObj.put("epx", lcpSttus_crk.getJSONObject(k).has("종점_경도") ? epxText : "");
						lcpsttus_crkObj.put("epy", lcpSttus_crk.getJSONObject(k).has("종점_위도") ? epyText : "");
					}
					
					lcpsttus_crkObj.put("type", lcpSttus_crk.getJSONObject(k).has("유형") ? (String) lcpSttus_crk.getJSONObject(k).get("유형").toString() : "");		// 값 들어오는 것에는 유형하고 발생방향하고 값이 값음
					lcpsttus_crkObj.put("occur", lcpSttus_crk.getJSONObject(k).has("발생방향") ? (String) lcpSttus_crk.getJSONObject(k).get("발생방향").toString() : "");
					lcpsttus_crkObj.put("bpz", lcpSttus_crk.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_crk.getJSONObject(k).get("시점_고도").toString() : "");
					lcpsttus_crkObj.put("epz", lcpSttus_crk.getJSONObject(k).has("종점_고도") ? (String) lcpSttus_crk.getJSONObject(k).get("종점_고도").toString() : "");					
					lcpsttus_crkObj.put("height", lcpSttus_crk.getJSONObject(k).has("높이") ? (String) lcpSttus_crk.getJSONObject(k).get("높이").toString() : "0");
					lcpsttus_crkObj.put("length", lcpSttus_crk.getJSONObject(k).has("연장") ? (String) lcpSttus_crk.getJSONObject(k).get("연장").toString() : "0");
					lcpsttus_crkObj.put("depth", lcpSttus_crk.getJSONObject(k).has("깊이") ? (String) lcpSttus_crk.getJSONObject(k).get("깊이").toString() : "0");
					obj = parser.parse(lcpsttus_crkObj.toString());
					lcpSttusArr_crk.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_crk_result", lcpSttusArr_crk);
		// 땅밀림현황_단차
		org.json.simple.JSONArray lcpSttusArr_stp = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_stp() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_stp = new JSONArray(vo.getLcpsttus_stp());
			if(lcpSttus_stp.length() > 1 )  {
				for (int k=0; k<lcpSttus_stp.length(); k++) {
					JSONObject lcpsttus_stpObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_stp.getJSONObject(k).has("시점_경도") ? lcpSttus_stp.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_stp.getJSONObject(k).has("시점_위도") ? lcpSttus_stp.getJSONObject(k).get("시점_위도").toString() : null);
					projMap.put("epx",lcpSttus_stp.getJSONObject(k).has("종점_경도") ? lcpSttus_stp.getJSONObject(k).get("종점_경도").toString() : null);
					projMap.put("epy",lcpSttus_stp.getJSONObject(k).has("종점_위도") ? lcpSttus_stp.getJSONObject(k).get("종점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					if(projList.get(0) != null) {
						double bpx = Double.parseDouble(projList.get(0).get("bpx").toString());
						double bpy = Double.parseDouble(projList.get(0).get("bpy").toString());
						double epx = Double.parseDouble(projList.get(0).get("epx").toString());
						double epy = Double.parseDouble(projList.get(0).get("epy").toString());
						
						// 시점 경도
						int bpx1 = (int) Math.floor(bpx);
						int bpx2 = (int) Math.floor((bpx - bpx1) * 60);
						double bpx3 = (((bpx - bpx1) * 60) - bpx2) * 60;
						// 시점 위도
						int bpy1 = (int) Math.floor(bpy);
						int bpy2 = (int) Math.floor((bpy - bpy1) * 60);
						double bpy3 = (((bpy - bpy1) * 60) - bpy2) * 60;
						// 종검 경도
						int epx1 = (int) Math.floor(epx);
						int epx2 = (int) Math.floor((epx - epx1) * 60);
						double epx3 = (((epx - epx1) * 60) - epx2) * 60;
						// 종점 위도
						int epy1 = (int) Math.floor(epy);
						int epy2 = (int) Math.floor((epy - epy) * 60);
						double epy3 = (((epy - epy1) * 60) - epy2) * 60;
						
						String bpxText = bpx1+" "+bpx2+" "+bpx3;
						String bpyText = bpy1+" "+bpy2+" "+bpy3;
						String epxText = epx1+" "+epx2+" "+epx3;
						String epyText = epy1+" "+epy2+" "+epy3;
						
						lcpsttus_stpObj.put("bpx", lcpSttus_stp.getJSONObject(k).has("시점_경도") ? bpxText : "");
						lcpsttus_stpObj.put("bpy", lcpSttus_stp.getJSONObject(k).has("시점_위도") ? bpyText : "");
						lcpsttus_stpObj.put("epx", lcpSttus_stp.getJSONObject(k).has("종점_경도") ? epxText : "");
						lcpsttus_stpObj.put("epy", lcpSttus_stp.getJSONObject(k).has("종점_위도") ? epyText : "");
					}
					
					lcpsttus_stpObj.put("occur", lcpSttus_stp.getJSONObject(k).has("발생방향") ? (String) lcpSttus_stp.getJSONObject(k).get("발생방향").toString() : "");
					lcpsttus_stpObj.put("bpz", lcpSttus_stp.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_stp.getJSONObject(k).get("시점_고도").toString() : "");
					lcpsttus_stpObj.put("epz", lcpSttus_stp.getJSONObject(k).has("종점_고도") ? (String) lcpSttus_stp.getJSONObject(k).get("종점_고도").toString() : "");					
					lcpsttus_stpObj.put("height", lcpSttus_stp.getJSONObject(k).has("높이") ? (String) lcpSttus_stp.getJSONObject(k).get("높이").toString() : "0");
					lcpsttus_stpObj.put("length", lcpSttus_stp.getJSONObject(k).has("연장") ? (String) lcpSttus_stp.getJSONObject(k).get("연장").toString() : "0");
					lcpsttus_stpObj.put("depth", lcpSttus_stp.getJSONObject(k).has("깊이") ? (String) lcpSttus_stp.getJSONObject(k).get("깊이").toString() : "0");
					obj = parser.parse(lcpsttus_stpObj.toString());
					lcpSttusArr_stp.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_stp_result", lcpSttusArr_stp);
		// 땅밀림현황_수목이상생장
		org.json.simple.JSONArray lcpSttusArr_wdpt = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_wdpt() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_wdpt = new JSONArray(vo.getLcpsttus_wdpt());
			if(lcpSttus_wdpt.length() > 1 )  {
				for (int k=0; k<lcpSttus_wdpt.length(); k++) {
					JSONObject lcpsttus_wdptObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_wdpt.getJSONObject(k).has("시점_경도") ? lcpSttus_wdpt.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_wdpt.getJSONObject(k).has("시점_위도") ? lcpSttus_wdpt.getJSONObject(k).get("시점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					if(projList.get(0) != null) {
						double bpx = Double.parseDouble(projList.get(0).get("bpx").toString());
						double bpy = Double.parseDouble(projList.get(0).get("bpy").toString());
						
						// 시점 경도
						int bpx1 = (int) Math.floor(bpx);
						int bpx2 = (int) Math.floor((bpx - bpx1) * 60);
						double bpx3 = (((bpx - bpx1) * 60) - bpx2) * 60;
						// 시점 위도
						int bpy1 = (int) Math.floor(bpy);
						int bpy2 = (int) Math.floor((bpy - bpy1) * 60);
						double bpy3 = (((bpy - bpy1) * 60) - bpy2) * 60;
						
						String bpxText = bpx1+" "+bpx2+" "+bpx3;
						String bpyText = bpy1+" "+bpy2+" "+bpy3;
						
						lcpsttus_wdptObj.put("bpx", lcpSttus_wdpt.getJSONObject(k).has("시점_경도") ? bpxText : "");
						lcpsttus_wdptObj.put("bpy", lcpSttus_wdpt.getJSONObject(k).has("시점_위도") ? bpyText : "");						
					}
					
					lcpsttus_wdptObj.put("type", lcpSttus_wdpt.getJSONObject(k).has("유형") ? (String) lcpSttus_wdpt.getJSONObject(k).get("유형").toString() : "");	//유형
					lcpsttus_wdptObj.put("abgrow", lcpSttus_wdpt.getJSONObject(k).has("이상생장_방향") ? (String) lcpSttus_wdpt.getJSONObject(k).get("이상생장_방향").toString() : ""); //이상생장_방향
					lcpsttus_wdptObj.put("chstht", lcpSttus_wdpt.getJSONObject(k).has("흉고직경") ? (String) lcpSttus_wdpt.getJSONObject(k).get("흉고직경").toString() : ""); //흉고직경
					lcpsttus_wdptObj.put("treeht", lcpSttus_wdpt.getJSONObject(k).has("수고") ? (String) lcpSttus_wdpt.getJSONObject(k).get("수고").toString() : ""); //수고
					lcpsttus_wdptObj.put("occurx", lcpSttus_wdpt.getJSONObject(k).has("발생범위_가로") ? (String) lcpSttus_wdpt.getJSONObject(k).get("발생범위_가로").toString() : ""); //발생범위_가로
					lcpsttus_wdptObj.put("occury", lcpSttus_wdpt.getJSONObject(k).has("발생범위_세로") ? (String) lcpSttus_wdpt.getJSONObject(k).get("발생범위_세로").toString() : ""); //발생범위_세로
					lcpsttus_wdptObj.put("surfht", lcpSttus_wdpt.getJSONObject(k).has("지표높이") ? (String) lcpSttus_wdpt.getJSONObject(k).get("지표높이").toString() : ""); //지표높이
					lcpsttus_wdptObj.put("treespc", lcpSttus_wdpt.getJSONObject(k).has("수종") ? (String) lcpSttus_wdpt.getJSONObject(k).get("수종").toString() : ""); //수종
					lcpsttus_wdptObj.put("treeapr", lcpSttus_wdpt.getJSONObject(k).has("임상") ? (String) lcpSttus_wdpt.getJSONObject(k).get("임상").toString() : ""); //임상
					lcpsttus_wdptObj.put("bpz", lcpSttus_wdpt.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_wdpt.getJSONObject(k).get("시점_고도").toString() : "");
					obj = parser.parse(lcpsttus_wdptObj.toString());
					lcpSttusArr_wdpt.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_wdpt_result", lcpSttusArr_wdpt);
		// 땅밀림현황_구조물이상
		org.json.simple.JSONArray lcpSttusArr_strct = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_strct() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_strct = new JSONArray(vo.getLcpsttus_strct());
			if(lcpSttus_strct.length() > 1 )  {
				for (int k=0; k<lcpSttus_strct.length(); k++) {
					JSONObject lcpsttus_strctObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_strct.getJSONObject(k).has("시점_경도") ? lcpSttus_strct.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_strct.getJSONObject(k).has("시점_위도") ? lcpSttus_strct.getJSONObject(k).get("시점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					if(projList.get(0) != null) {
						double bpx = Double.parseDouble(projList.get(0).get("bpx").toString());
						double bpy = Double.parseDouble(projList.get(0).get("bpy").toString());
						
						// 시점 경도
						int bpx1 = (int) Math.floor(bpx);
						int bpx2 = (int) Math.floor((bpx - bpx1) * 60);
						double bpx3 = (((bpx - bpx1) * 60) - bpx2) * 60;
						// 시점 위도
						int bpy1 = (int) Math.floor(bpy);
						int bpy2 = (int) Math.floor((bpy - bpy1) * 60);
						double bpy3 = (((bpy - bpy1) * 60) - bpy2) * 60;
						
						String bpxText = bpx1+" "+bpx2+" "+bpx3;
						String bpyText = bpy1+" "+bpy2+" "+bpy3;
						
						lcpsttus_strctObj.put("bpx", lcpSttus_strct.getJSONObject(k).has("시점_경도") ? bpxText : "");
						lcpsttus_strctObj.put("bpy", lcpSttus_strct.getJSONObject(k).has("시점_위도") ? bpyText : "");						
					}
					
					lcpsttus_strctObj.put("strctype", lcpSttus_strct.getJSONObject(k).has("구조물종류") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물종류").toString() : ""); //구조물종류
					lcpsttus_strctObj.put("strcsizeht", lcpSttus_strct.getJSONObject(k).has("구조물크기_높이") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물크기_높이").toString() : ""); //구조물크기_높이
					lcpsttus_strctObj.put("strcsizewd", lcpSttus_strct.getJSONObject(k).has("구조물크기_가로") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물크기_가로").toString() : ""); //구조물크기_가로
					lcpsttus_strctObj.put("strcsizedp", lcpSttus_strct.getJSONObject(k).has("구조물크기_세로") ? (String) lcpSttus_strct.getJSONObject(k).get("구조물크기_세로").toString() : ""); //구조물크기_세로
					lcpsttus_strctObj.put("occurloc", lcpSttus_strct.getJSONObject(k).has("발생위치") ? (String) lcpSttus_strct.getJSONObject(k).get("발생위치").toString() : ""); //발생위치
					lcpsttus_strctObj.put("abdegre", lcpSttus_strct.getJSONObject(k).has("이상정도") ? (String) lcpSttus_strct.getJSONObject(k).get("이상정도").toString() : ""); //이상정도
					lcpsttus_strctObj.put("bpz", lcpSttus_strct.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_strct.getJSONObject(k).get("시점_고도").toString() : "");
					obj = parser.parse(lcpsttus_strctObj.toString());
					lcpSttusArr_strct.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_strct_result", lcpSttusArr_strct);
		// 땅밀림현황_지하수용출
		org.json.simple.JSONArray lcpSttusArr_ugrwtr = new org.json.simple.JSONArray();
		if(vo.getLcpsttus_ugrwtr() != null) {
			lcpObj = new Object();
			JSONArray lcpSttus_ugrwtr = new JSONArray(vo.getLcpsttus_ugrwtr());
			if(lcpSttus_ugrwtr.length() > 1 )  {
				for (int k=0; k<lcpSttus_ugrwtr.length(); k++) {
					JSONObject lcpsttus_ugrwtrtObj = new JSONObject();
					
					HashMap<String, Object> projMap = new HashMap<>();				
					projMap.put("bpx",lcpSttus_ugrwtr.getJSONObject(k).has("시점_경도") ? lcpSttus_ugrwtr.getJSONObject(k).get("시점_경도").toString() : null);
					projMap.put("bpy",lcpSttus_ugrwtr.getJSONObject(k).has("시점_위도") ? lcpSttus_ugrwtr.getJSONObject(k).get("시점_위도").toString() : null);
					
					List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjDMS(projMap);
					
					if(projList.get(0) != null) {						
						double bpx = Double.parseDouble(projList.get(0).get("bpx").toString());
						double bpy = Double.parseDouble(projList.get(0).get("bpy").toString());
						
						// 시점 경도
						int bpx1 = (int) Math.floor(bpx);
						int bpx2 = (int) Math.floor((bpx - bpx1) * 60);
						double bpx3 = (((bpx - bpx1) * 60) - bpx2) * 60;
						// 시점 위도
						int bpy1 = (int) Math.floor(bpy);
						int bpy2 = (int) Math.floor((bpy - bpy1) * 60);
						double bpy3 = (((bpy - bpy1) * 60) - bpy2) * 60;
						
						String bpxText = bpx1+" "+bpx2+" "+bpx3;
						String bpyText = bpy1+" "+bpy2+" "+bpy3;
						
						lcpsttus_ugrwtrtObj.put("bpx", lcpSttus_ugrwtr.getJSONObject(k).has("시점_경도") ? bpxText : "");
						lcpsttus_ugrwtrtObj.put("bpy", lcpSttus_ugrwtr.getJSONObject(k).has("시점_위도") ? bpyText : "");
					}
					
					lcpsttus_ugrwtrtObj.put("occurloc", lcpSttus_ugrwtr.getJSONObject(k).has("발생위치") ? (String) lcpSttus_ugrwtr.getJSONObject(k).get("발생위치").toString() : ""); //발생위치
					lcpsttus_ugrwtrtObj.put("occuramt", lcpSttus_ugrwtr.getJSONObject(k).has("발생정도") ? (String) lcpSttus_ugrwtr.getJSONObject(k).get("발생정도").toString() : ""); //발생정도
					lcpsttus_ugrwtrtObj.put("bpz", lcpSttus_ugrwtr.getJSONObject(k).has("시점_고도") ? (String) lcpSttus_ugrwtr.getJSONObject(k).get("시점_고도").toString() : "");
					obj = parser.parse(lcpsttus_ugrwtrtObj.toString());
					lcpSttusArr_ugrwtr.add(obj);
				}
			}
		}
		model.addAttribute("lcpsttus_ugrwtr_result", lcpSttusArr_ugrwtr);
		return "sys/lss/lcp/sct/svyComptUpdt";
	}

	/**
	 * 조사완료지를 수정한다.
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/lcp/sct/updateLcpSvyCompt.do")
    public ModelAndView updateLssLcpSvyCompt(@ModelAttribute("lssLcpSvyComptVO") LssLcpSvyComptVO svyComptVO, BindingResult bindingResult, Model model, HttpServletRequest request) throws Exception {
		beanValidator.validate(svyComptVO, bindingResult);
		ModelAndView mv = new ModelAndView("jsonView");
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		//현장사진
		if(!svyComptVO.getPhotoTagList().isEmpty()) svyComptVO.setPhotoTagList(svyComptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		
		// 땅밀림 징후
		JSONArray lcpSttusCrk = null;
		JSONArray lcpSttusStp = null;
		JSONArray lcpSttusWdpt = null;
		JSONArray lcpSttusStrct = null;
		JSONArray lcpSttusUgrwtr = null;
		if(!svyComptVO.getLcpSttusCrk().isEmpty()) lcpSttusCrk = new JSONArray(svyComptVO.getLcpSttusCrk().replaceAll("&quot;", "\""));			
		if(!svyComptVO.getLcpSttusStp().isEmpty()) lcpSttusStp = new JSONArray(svyComptVO.getLcpSttusStp().replaceAll("&quot;", "\""));
		if(!svyComptVO.getLcpSttusWdpt().isEmpty()) lcpSttusWdpt = new JSONArray(svyComptVO.getLcpSttusWdpt().replaceAll("&quot;", "\""));
		if(!svyComptVO.getLcpSttusStrct().isEmpty()) lcpSttusStrct = new JSONArray(svyComptVO.getLcpSttusStrct().replaceAll("&quot;", "\""));
		if(!svyComptVO.getLcpSttusUgrwtr().isEmpty()) lcpSttusUgrwtr = new JSONArray(svyComptVO.getLcpSttusUgrwtr().replaceAll("&quot;", "\""));
 
		// 땅밀림징후_균열
		if(lcpSttusCrk != null) {
			for(int i=0; i<lcpSttusCrk.length(); i++) {
				HashMap<String, Object> projMap = new HashMap<>();				
				projMap.put("bpx",lcpSttusCrk.getJSONObject(i).has("시점_경도") ? lcpSttusCrk.getJSONObject(i).get("시점_경도").toString() : null);
				projMap.put("bpy",lcpSttusCrk.getJSONObject(i).has("시점_위도") ? lcpSttusCrk.getJSONObject(i).get("시점_위도").toString() : null);
				projMap.put("epx",lcpSttusCrk.getJSONObject(i).has("종점_경도") ? lcpSttusCrk.getJSONObject(i).get("종점_경도").toString() : null);
				projMap.put("epy",lcpSttusCrk.getJSONObject(i).has("종점_위도") ? lcpSttusCrk.getJSONObject(i).get("종점_위도").toString() : null);
				
				List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjBessel(projMap);
				
				lcpSttusCrk.getJSONObject(i).remove("시점_경도");
				lcpSttusCrk.getJSONObject(i).remove("시점_위도");
				lcpSttusCrk.getJSONObject(i).remove("종점_경도");
				lcpSttusCrk.getJSONObject(i).remove("종점_위도");
				
				if(!projList.get(0).get("bpx").toString().isEmpty()) lcpSttusCrk.getJSONObject(i).put("시점_경도",projList.get(0).get("bpx"));
				if(!projList.get(0).get("bpy").toString().isEmpty()) lcpSttusCrk.getJSONObject(i).put("시점_위도",projList.get(0).get("bpy"));
				if(!projList.get(0).get("epx").toString().isEmpty()) lcpSttusCrk.getJSONObject(i).put("종점_경도",projList.get(0).get("epx"));
				if(!projList.get(0).get("epy").toString().isEmpty()) lcpSttusCrk.getJSONObject(i).put("종점_위도",projList.get(0).get("epy"));
						
			}
			svyComptVO.setLcpsttus_crk(lcpSttusCrk.toString());
		}

		// 땅밀림징후_단차
		if(lcpSttusStp != null) {
			for(int i=0; i<lcpSttusStp.length(); i++) {
				HashMap<String, Object> projMap = new HashMap<>();				
				projMap.put("bpx",lcpSttusStp.getJSONObject(i).has("시점_경도") ? lcpSttusStp.getJSONObject(i).get("시점_경도").toString() : null);
				projMap.put("bpy",lcpSttusStp.getJSONObject(i).has("시점_위도") ? lcpSttusStp.getJSONObject(i).get("시점_위도").toString() : null);
				projMap.put("epx",lcpSttusStp.getJSONObject(i).has("종점_경도") ? lcpSttusStp.getJSONObject(i).get("종점_경도").toString() : null);
				projMap.put("epy",lcpSttusStp.getJSONObject(i).has("종점_위도") ? lcpSttusStp.getJSONObject(i).get("종점_위도").toString() : null);
				
				List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjBessel(projMap);
				
				lcpSttusStp.getJSONObject(i).remove("시점_경도");
				lcpSttusStp.getJSONObject(i).remove("시점_위도");
				lcpSttusStp.getJSONObject(i).remove("종점_경도");
				lcpSttusStp.getJSONObject(i).remove("종점_위도");
				
				if(!projList.get(0).get("bpx").toString().isEmpty()) lcpSttusStp.getJSONObject(i).put("시점_경도",projList.get(0).get("bpx"));
				if(!projList.get(0).get("bpy").toString().isEmpty()) lcpSttusStp.getJSONObject(i).put("시점_위도",projList.get(0).get("bpy"));
				if(!projList.get(0).get("epx").toString().isEmpty()) lcpSttusStp.getJSONObject(i).put("종점_경도",projList.get(0).get("epx"));
				if(!projList.get(0).get("epy").toString().isEmpty()) lcpSttusStp.getJSONObject(i).put("종점_위도",projList.get(0).get("epy"));
								
			}
			svyComptVO.setLcpsttus_stp(lcpSttusStp.toString());
		}
		
		// 땅밀림징후_수목이상생장
		if(lcpSttusWdpt != null) {
			for(int i=0; i<lcpSttusWdpt.length(); i++) {
				HashMap<String, Object> projMap = new HashMap<>();				
				projMap.put("bpx",lcpSttusWdpt.getJSONObject(i).has("시점_경도") ? lcpSttusWdpt.getJSONObject(i).get("시점_경도").toString() : null);
				projMap.put("bpy",lcpSttusWdpt.getJSONObject(i).has("시점_위도") ? lcpSttusWdpt.getJSONObject(i).get("시점_위도").toString() : null);
				
				List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjBessel(projMap);
				
				lcpSttusWdpt.getJSONObject(i).remove("시점_경도");
				lcpSttusWdpt.getJSONObject(i).remove("시점_위도");
				
				if(!projList.get(0).get("bpx").toString().isEmpty()) lcpSttusWdpt.getJSONObject(i).put("시점_경도",projList.get(0).get("bpx"));
				if(!projList.get(0).get("bpy").toString().isEmpty()) lcpSttusWdpt.getJSONObject(i).put("시점_위도",projList.get(0).get("bpy"));
						
			}
			svyComptVO.setLcpsttus_wdpt(lcpSttusWdpt.toString());
		}
		
		// 땅밀림징후_구조물이상
		if(lcpSttusStrct != null) {
			for(int i=0; i<lcpSttusStrct.length(); i++) {
				HashMap<String, Object> projMap = new HashMap<>();				
				projMap.put("bpx",lcpSttusStrct.getJSONObject(i).has("시점_경도") ? lcpSttusStrct.getJSONObject(i).get("시점_경도").toString() : null);
				projMap.put("bpy",lcpSttusStrct.getJSONObject(i).has("시점_위도") ? lcpSttusStrct.getJSONObject(i).get("시점_위도").toString() : null);
				
				List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjBessel(projMap);
				
				lcpSttusStrct.getJSONObject(i).remove("시점_경도");
				lcpSttusStrct.getJSONObject(i).remove("시점_위도");
				
				if(!projList.get(0).get("bpx").toString().isEmpty()) lcpSttusStrct.getJSONObject(i).put("시점_경도",projList.get(0).get("bpx"));
				if(!projList.get(0).get("bpy").toString().isEmpty()) lcpSttusStrct.getJSONObject(i).put("시점_위도",projList.get(0).get("bpy"));
								
			}
			svyComptVO.setLcpsttus_strct(lcpSttusStrct.toString());
		}
		
		// 땅밀림징후_지하수용출
		if(lcpSttusUgrwtr != null) {
			for(int i=0; i<lcpSttusUgrwtr.length(); i++) {
				HashMap<String, Object> projMap = new HashMap<>();				
				projMap.put("bpx",lcpSttusUgrwtr.getJSONObject(i).has("시점_경도") ? lcpSttusUgrwtr.getJSONObject(i).get("시점_경도").toString() : null);
				projMap.put("bpy",lcpSttusUgrwtr.getJSONObject(i).has("시점_위도") ? lcpSttusUgrwtr.getJSONObject(i).get("시점_위도").toString() : null);
				
				List<EgovMap> projList = lssLcpSvyComptService.selectLssLcpProjBessel(projMap);
				
				lcpSttusUgrwtr.getJSONObject(i).remove("시점_경도");
				lcpSttusUgrwtr.getJSONObject(i).remove("시점_위도");
				
				if(!projList.get(0).get("bpx").toString().isEmpty()) lcpSttusUgrwtr.getJSONObject(i).put("시점_경도",projList.get(0).get("bpx"));
				if(!projList.get(0).get("bpy").toString().isEmpty()) lcpSttusUgrwtr.getJSONObject(i).put("시점_위도",projList.get(0).get("bpy"));
								
			}
			svyComptVO.setLcpsttus_ugrwtr(lcpSttusUgrwtr.toString());
		}
		
		try {
			lssLcpSvyComptService.updateLcpSvyCompt(svyComptVO);
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
	@RequestMapping(value = "/sys/lss/lcp/sct/deleteLcpSvyCompt.do")
	public ModelAndView deleteLssLcpSvyCompt(LssLcpSvyComptVO svyComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			lssLcpSvyComptService.deleteLcpSvyCompt(svyComptVO);
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
	 * 조사완료지 엑셀 다운로드 한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/lss/lcp/sct/selectLcpSvyComptListExcel.do")
	public ModelAndView selectLcpSvyComptListExcel(LssLcpSvyComptVO svyComptVO) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("lcpExcelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	HashMap<?, ?> _map = (HashMap<?, ?>)lssLcpSvyComptService.selectLcpSvyComptListExcel(svyComptVO);
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    	
    	String fileNm = "땅밀림실태조사완료지_".concat(formater.format(new Date()).toString());
    	
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
	@RequestMapping(value = "/sys/lss/lcp/sct/updateLcpSvyComptPopup.do")
	public String insertCnrsSpceSldPopup(
			@ModelAttribute("svyComptVO") LssLcpSvyComptVO svyComptVO,
			ModelMap model) throws Exception {
		
		return "sys/lss/lcp/sct/updateLcpSvyComptPopup";
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
	@RequestMapping(value = "/sys/lss/lcp/sct/updateLcpSvyComptExcel.do")
	public ModelAndView updateBscSvyComptExcel(
			@ModelAttribute("svyComptVO") LssLcpSvyComptVO svyComptVO,
    		@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		HashMap<String, Object> commandMap = new HashMap<>();
		JSONObject results = lssLcpSvyComptService.updateLcpSvyComptExcel(svyComptVO, mFile);
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
	 * @param fieldBookVO
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 공유방 등록 팝업
	 */
	@RequestMapping(value = "/sys/lss/lcp/sct/insertShpFilePopup.do")
	public String insertCnrsSpcePopup(
			@ModelAttribute("svyComptVO") LssLcpSvyComptVO svyComptVO,
			ModelMap model) throws Exception {
		
		return "sys/lss/lcp/sct/insertShpFilePopup";
	}
	
	@RequestMapping(value = "/sys/lss/lcp/sct/creatLocationPopupTest.do")
	public String creatLocationPopupTest() throws Exception{
		return "sys/lss/lcp/sct/creatLocationPopupTest";
	}
	
	/**
	 * 위치도 재생성 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sct/updateLocReCreatePopup.do")
	public String updateLocReCreatePopup(ModelMap model) throws Exception {
		
		LocReCreateVO searchMap = new LocReCreateVO();
		EgovMap dateMap = lssLcpSvyComptService.selectLastUpdateMinMaxDate(searchMap);
		
		model.addAttribute("lastUptDate", dateMap);
		
		return "sys/lss/lcp/sct/updateLocReCreatePopup";
	}
	
	/**
	 * 대상지 기간 별 건수 조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/lcp/sct/selectLocReCeateCnt.do")
	public ModelAndView selectLocReCeateCnt(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap dateMap = lssLcpSvyComptService.selectLastUpdateMinMaxDate(searchVO);
			
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
	@RequestMapping(value = "/sys/lss/lcp/sct/updateLocReCreate.do")
	public ModelAndView updateLocReCreate(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		//["/한국치산기술협회-기초조사-경북(합).ncx/148613_위치도1.png"]
		try {
			lssLcpSvyComptService.updateComptLcMap(searchVO);
			
			
			
			
			mv.addObject("status",200);
			mv.addObject("message","success");
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
	@RequestMapping(value = "/sys/lss/lcp/sct/selectLcpFrCode.do")
	public ModelAndView selectLcpFrCode(ModelMap model, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		// 주요수종 조회
		String frstval = request.getParameter("frCode");
		if(frstval.equals("침엽수")){
			//주요수종(침엽수) 코드
			comVO.setCodeId("FEI067");
		}else if(frstval.equals("활엽수")){
			//주요수종(활엽수) 코드
			comVO.setCodeId("FEI068");
		}else if(frstval.equals("상록활엽수")){
			//주요수종(상록활엽수) 코드
			comVO.setCodeId("FEI069");			
		}else if(frstval.equals("죽림")){
			//주요수종(죽림) 코드
			comVO.setCodeId("FEI070");			
		}else if(frstval.equals("혼효림")){
			//주요수종(혼효림) 코드
			comVO.setCodeId("FEI168");			
		}
		
		//주요수종 코드목록
		List<CmmnDetailCode> list = cmmUseService.selectCmmCodeDetail(comVO);
		mv.addObject("result", list);
		
		return mv;
	}
}