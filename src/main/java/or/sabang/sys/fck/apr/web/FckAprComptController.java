package or.sabang.sys.fck.apr.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
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
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileCmprs;
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
import or.sabang.sys.fck.apr.service.FckAprComptService;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class FckAprComptController {
	
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
	
	@Resource(name = "fckAprComptService") 	
	private FckAprComptService fckAprComptService;
	
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
    
    /** 첨부파일 위치 지정  => globals.properties */
    private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
    private final String comptFileDir = EgovProperties.getProperty("Globals.fileStorePath.compt");
    
	private static final Logger LOGGER = LoggerFactory.getLogger(FckAprComptController.class);
	

	/**
	 * 조사완료목록을 조회한다.
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/selectAprComptList.do")	
    public String selectFckAprComptList (@ModelAttribute("searchVO") FckAprComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {
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
			searchVO.setSvyYear(fckAprComptService.selectFckAprComptMaxYear());
		}
		Date date = new Date();
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(fckAprComptService.selectFckAprComptMaxMonth());
			//searchVO.setSvyMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI016");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = fckAprComptService.selectFckAprComptYear();
		model.addAttribute("yearCodeList", year_result);
		
		//월 코드 조회
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
		
		//최종점검결과 조회
		vo.setCodeId("FEI039");
		List<?> fckRslt_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("fckRsltCodeList", fckRslt_result);
		
		List<FckAprComptVO> comptList = fckAprComptService.selectFckAprComptList(searchVO);
		model.addAttribute("resultList", comptList);
		
		
		// 현장사진 개수
		JSONParser parser = new JSONParser();
		Object obj;
		for(int i=0; i<comptList.size(); i++) {
			
			// 피해발생현황 사진 갯수
			org.json.simple.JSONArray dmgSttusArr = new org.json.simple.JSONArray();
			if(!comptList.get(i).getDmgSttus().equals("{}")) {
				obj = new Object();
				JSONArray dmgSttus = new JSONArray(comptList.get(i).getDmgSttus());
				org.json.simple.JSONArray dmgSttusPhotoArr = new org.json.simple.JSONArray();
				if(dmgSttus.length() > 0 )  {
					for (int j=0; j<dmgSttus.length(); j++) {
						JSONObject dmgSttusObj = new JSONObject();
						String dmgSttusPhoto = dmgSttus.getJSONObject(j).has("사진") ? dmgSttus.getJSONObject(j).get("사진").toString() : "[]";
						if(!dmgSttusPhoto.equals("[]")) {
							obj = parser.parse(dmgSttusPhoto);
							dmgSttusPhotoArr = (org.json.simple.JSONArray) obj;
							dmgSttusObj.put("photo", dmgSttusPhotoArr);
				            obj = parser.parse(dmgSttusObj.toString());
				            dmgSttusArr.add(obj);
						}
					}
				}
			}
			comptList.get(i).setDmgSttusPhotoLen(dmgSttusArr.size());
			
			// 대상지
			if(comptList.get(i).getSvyType().equals("계류보전 외관점검")) {
				org.json.simple.JSONArray trglndArr = new org.json.simple.JSONArray();
				if(!comptList.get(i).getTrglnd().equals("{}")) {
					obj = new Object();
					JSONArray trglnd = new JSONArray(comptList.get(i).getTrglnd());
					org.json.simple.JSONArray trglndPhotoArr = new org.json.simple.JSONArray();
					if(trglnd.length() > 0 ){
						for (int k=0; k<trglnd.length(); k++) {
							JSONObject trglndObj = new JSONObject();
							String trglndPhoto = trglnd.getJSONObject(k).has("사진") ? (String) trglnd.getJSONObject(k).get("사진").toString() : "[]";
							if(!trglndPhoto.equals("[]")) {
								obj = parser.parse(trglndPhoto);
								trglndPhotoArr = (org.json.simple.JSONArray)  obj;
								trglndObj.put("photo", trglndPhotoArr);
								obj = parser.parse(trglndObj.toString());
					            trglndArr.add(obj);
							}
						}
					}
				}
				comptList.get(i).setTrgLndLen(trglndArr.size());
				
				
				org.json.simple.JSONArray dmgFcltArr = new org.json.simple.JSONArray();
				if(!comptList.get(i).equals("{}")) {
					obj = new Object();
					JSONArray dmgFclt = new JSONArray(comptList.get(i).getDmgFclt());
					org.json.simple.JSONArray dmgFcltPhotoArr = new org.json.simple.JSONArray();
					if(dmgFclt.length() > 0 ){
						for (int l=0; l<dmgFclt.length(); l++) {
							JSONObject dmgFcltObj = new JSONObject();
							String dmgFcltPhoto = dmgFclt.getJSONObject(l).has("사진") ? (String) dmgFclt.getJSONObject(l).get("사진").toString() : "[]";
							if(!dmgFcltPhoto.equals("[]")) {
								obj = parser.parse(dmgFcltPhoto);
								dmgFcltPhotoArr = (org.json.simple.JSONArray) obj;
								dmgFcltObj.put("photo", dmgFcltPhotoArr);
								obj = parser.parse(dmgFcltObj.toString());
								dmgFcltArr.add(obj);
							}
						}
					}
				}
				comptList.get(i).setDmgFcltPhotoLen(dmgFcltArr.size());
				
			}
		}
		int totCnt = fckAprComptService.selectFckAprComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/fck/apr/sct/aprComptList";
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param comptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/selectFckAprComptDetail.do")
	public String selectAprSvyComptDetail(@ModelAttribute("searchVO") FckAprComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		FckAprComptVO vo = fckAprComptService.selectFckAprComptDetail(searchVO);
		
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

		// 사방댐 피해발생 현황도 사진 따옴표 제거
		String encrKnd = vo.getKnd();
		String encrForm = "("+vo.getSvyForm()+")";
		String kndUrl = "../../../../../../images/sttusprnt/";
		if(vo.getSttusPrnt1().equals("{}")) vo.setSttusPrnt1(kndUrl+encrKnd+encrForm+"/"+encrKnd+"_반수면"+encrForm+".png");
			else vo.setSttusPrnt1("/storage/fieldBook"+vo.getSttusPrnt1());
		if(vo.getSttusPrnt2().equals("{}")) vo.setSttusPrnt2(kndUrl+encrKnd+encrForm+"/"+encrKnd+"_대수면"+encrForm+".png");
			else vo.setSttusPrnt2("/storage/fieldBook"+vo.getSttusPrnt2());
		if(vo.getSttusPrnt3().equals("{}")) vo.setSttusPrnt3(kndUrl+encrKnd+encrForm+"/"+encrKnd+"_물받이"+encrForm+".png");
			else vo.setSttusPrnt3("/storage/fieldBook"+vo.getSttusPrnt3());
		if(vo.getSttusPrnt4().equals("{}")) vo.setSttusPrnt4(kndUrl+"측벽(좌안, 우안)/좌안측벽.png");
			else vo.setSttusPrnt4("/storage/fieldBook"+vo.getSttusPrnt4());
		if(vo.getSttusPrnt5().equals("{}")) vo.setSttusPrnt5(kndUrl+"측벽(좌안, 우안)/우안측벽.png");
			else vo.setSttusPrnt5("/storage/fieldBook"+vo.getSttusPrnt5());
		
		vo.setSttusPrnt1(vo.getSttusPrnt1().replaceAll("\"", ""));
		vo.setSttusPrnt2(vo.getSttusPrnt2().replaceAll("\"", ""));
		vo.setSttusPrnt3(vo.getSttusPrnt3().replaceAll("\"", ""));
		vo.setSttusPrnt4(vo.getSttusPrnt4().replaceAll("\"", ""));
		vo.setSttusPrnt5(vo.getSttusPrnt5().replaceAll("\"", ""));
	
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
//		}else {
//			if(vo.getPhotoTag() != null && vo.getPhotoTag().length() > 2) { 
//				photo = vo.getPhotoTag().toString();
//				obj = parser.parse(photo);
//				jsonArray = (org.json.simple.JSONArray)obj;
//				model.addAttribute("photo_result", jsonArray);
//			}
//		}
		
		// 피해발생현황
		org.json.simple.JSONArray dmgSttusArr = new org.json.simple.JSONArray();
		if(!vo.getDmgSttus().equals("{}")) {
			obj = new Object();
			JSONArray dmgSttus = new JSONArray(vo.getDmgSttus());
			org.json.simple.JSONArray dmgSttusPhotoArr = new org.json.simple.JSONArray();
			org.json.simple.JSONArray dmgSttusPhotoTagArr = new org.json.simple.JSONArray();
			if(dmgSttus.length() > 0 )  {
				for (int j=0; j<dmgSttus.length(); j++) {
					JSONObject dmgSttusObj = new JSONObject();
					String dmgSttusPhoto = dmgSttus.getJSONObject(j).has("사진") ? dmgSttus.getJSONObject(j).get("사진").toString() : "[]";
					String dmgSttusPhotoTag = dmgSttus.getJSONObject(j).has("사진태그") ? (String) dmgSttus.getJSONObject(j).get("사진태그").toString() : "[]";
					
					
					obj = parser.parse(dmgSttusPhoto);
					dmgSttusPhotoArr = (org.json.simple.JSONArray) obj;
					obj = parser.parse(dmgSttusPhotoTag);
					dmgSttusPhotoTagArr = (org.json.simple.JSONArray) obj;
					
					
					dmgSttusObj.put("photo", dmgSttusPhotoArr);
					dmgSttusObj.put("photoTag", dmgSttusPhotoTagArr);
					dmgSttusObj.put("note", dmgSttus.getJSONObject(j).has("비고") ? dmgSttus.getJSONObject(j).get("비고").toString() : "");
					dmgSttusObj.put("loc", dmgSttus.getJSONObject(j).has("위치") ? dmgSttus.getJSONObject(j).get("위치").toString() : "");
					dmgSttusObj.put("type", dmgSttus.getJSONObject(j).has("유형") ? dmgSttus.getJSONObject(j).get("유형").toString() : "");
//					dmgSttusObj.put("photo", dmgSttus.getJSONObject(j).has("사진") ? dmgSttus.getJSONObject(j).get("사진").toString() : "");
					
					
		            obj = parser.parse(dmgSttusObj.toString());
		            dmgSttusArr.add(obj);
		            
		            
				}
			}
		}
		
		// 대상지
		org.json.simple.JSONArray trglndArr = new org.json.simple.JSONArray();
		if(!vo.getTrglnd().equals("{}")) {
			String svyType = vo.getSvyType();
			obj = new Object();
			JSONArray trglnd = new JSONArray(vo.getTrglnd());
			org.json.simple.JSONArray trglndPhotoArr = new org.json.simple.JSONArray();
			org.json.simple.JSONArray trglndPhotoTagArr = new org.json.simple.JSONArray();
			
			if(trglnd.length() > 0 ){
				for (int k=0; k<trglnd.length(); k++) {
					JSONObject trglndObj = new JSONObject();
					String trglndPhoto = trglnd.getJSONObject(k).has("사진") ? (String) trglnd.getJSONObject(k).get("사진").toString() : "[]";
					String trglndPhotoTag = trglnd.getJSONObject(k).has("사진태그") ? (String) trglnd.getJSONObject(k).get("사진태그").toString() : "[]";
					obj = parser.parse(trglndPhoto);
					trglndPhotoArr = (org.json.simple.JSONArray)  obj;
					obj = parser.parse(trglndPhotoTag);
					trglndPhotoTagArr = (org.json.simple.JSONArray) obj;
					
					HashMap<String, Object> projMap = new HashMap<>();
					if(svyType.equals("계류보전 외관점검")) {
						projMap.put("lon1",trglnd.getJSONObject(k).has("X좌표1") ? trglnd.getJSONObject(k).get("X좌표1").toString() : null);
						projMap.put("lat1",trglnd.getJSONObject(k).has("Y좌표1") ? trglnd.getJSONObject(k).get("Y좌표1").toString() : null);
						projMap.put("lon2",trglnd.getJSONObject(k).has("X좌표2") ? trglnd.getJSONObject(k).get("X좌표2").toString() : null);
						projMap.put("lat2",trglnd.getJSONObject(k).has("Y좌표2") ? trglnd.getJSONObject(k).get("Y좌표2").toString() : null);
					}else {
						projMap.put("lon1",trglnd.getJSONObject(k).has("X좌표") ? trglnd.getJSONObject(k).get("X좌표").toString() : null);
						projMap.put("lat1",trglnd.getJSONObject(k).has("Y좌표") ? trglnd.getJSONObject(k).get("Y좌표").toString() : null);
						projMap.put("lon2",null);
						projMap.put("lat2",null);						
					}
					
					List<EgovMap> projList = fckAprComptService.selectFckAprProjDMS(projMap);
					
					trglndObj.put("photo", trglndPhotoArr);
					trglndObj.put("photoTag", trglndPhotoTagArr);
					if(svyType.equals("계류보전 외관점검")) {
						trglndObj.put("px1", trglnd.getJSONObject(k).has("X좌표1") ? projList.get(0).get("lon1") : "");
						trglndObj.put("py1", trglnd.getJSONObject(k).has("Y좌표1") ? projList.get(0).get("lat1") : "");
						trglndObj.put("px2", trglnd.getJSONObject(k).has("X좌표2") ? projList.get(0).get("lon2") : "");
						trglndObj.put("py2", trglnd.getJSONObject(k).has("Y좌표2") ? projList.get(0).get("lat2") : "");
						
						if(trglndObj.get("px1") != null && !trglndObj.get("px1").equals("")) {
							dmsformal.setDmsLon(trglndObj.get("px1").toString());
							trglndObj.put("px1",dmsformal.getDmsLon());
						}
						if(trglndObj.get("py1") != null && !trglndObj.get("py1").equals("")) {
							dmsformal.setDmsLat(trglndObj.get("py1").toString());
							trglndObj.put("py1",dmsformal.getDmsLat());
						}
						if(trglndObj.get("px2") != null && !trglndObj.get("px2").equals("")) {
							dmsformal.setDmsLon(trglndObj.get("px2").toString());
							trglndObj.put("px2",dmsformal.getDmsLon());
						}
						if(trglndObj.get("py2") != null && !trglndObj.get("py2").equals("")) {
							dmsformal.setDmsLat(trglndObj.get("py2").toString());
							trglndObj.put("py2",dmsformal.getDmsLat());
						}
						
					}else {
						trglndObj.put("px", trglnd.getJSONObject(k).has("X좌표") ? projList.get(0).get("lon1") : "");
						trglndObj.put("py", trglnd.getJSONObject(k).has("Y좌표") ? projList.get(0).get("lat1") : "");
						
						if(trglndObj.get("px") != null && !trglndObj.get("px").equals("")) {
							dmsformal.setDmsLon(trglndObj.get("px").toString());
							trglndObj.put("px",dmsformal.getDmsLon());
						}
						if(trglndObj.get("py") != null && !trglndObj.get("py").equals("")) {
							dmsformal.setDmsLat(trglndObj.get("py").toString());
							trglndObj.put("py",dmsformal.getDmsLat());
						}
					}
					
//					if(projList.get(0) != null) {
//						if(projList.get(0).containsKey("lat1") && projList.get(0).containsKey("lon1")) {
//							if(k == 0) vo.setSvyLatLon("(N) "+projList.get(0).get("lat1").toString()+" (E) "+projList.get(0).get("lon1").toString());
//						}else if(projList.get(0).containsKey("lat2") && projList.get(0).containsKey("lon2")) {
//							if(k == 0) vo.setSvyLatLon("(N) "+projList.get(0).get("lat2").toString()+" (E) "+projList.get(0).get("lon2").toString());
//						}else {
//							vo.setSvyLatLon("");
//						}
//					}
					
					trglndObj.put("strctu", trglnd.getJSONObject(k).has("주요시설물") ? (String) trglnd.getJSONObject(k).get("주요시설물").toString() : "");
					trglndObj.put("sttus", trglnd.getJSONObject(k).has("현황") ? (String) trglnd.getJSONObject(k).get("현황").toString() : "");
					trglndObj.put("note", trglnd.getJSONObject(k).has("비고") ? (String) trglnd.getJSONObject(k).get("비고").toString() : "");
					obj = parser.parse(trglndObj.toString());
		            trglndArr.add(obj);
		            
				}
			}
		}
		
		// 피해시설
		org.json.simple.JSONArray dmgFcltArr = new org.json.simple.JSONArray();
		if(!vo.getDmgFclt().equals("{}")) {
			obj = new Object();
			JSONArray dmgFclt = new JSONArray(vo.getDmgFclt());
			org.json.simple.JSONArray dmgFcltPhotoArr = new org.json.simple.JSONArray();
			org.json.simple.JSONArray dmgFcltPhotoTagArr = new org.json.simple.JSONArray();
			if(dmgFclt.length() > 0 ){
				for (int l=0; l<dmgFclt.length(); l++) {
					JSONObject dmgFcltObj = new JSONObject();
					String dmgFcltPhoto = dmgFclt.getJSONObject(l).has("사진") ? (String) dmgFclt.getJSONObject(l).get("사진").toString() : "[]";
					String dmgFcltPhotoTag = dmgFclt.getJSONObject(l).has("사진태그") ? (String) dmgFclt.getJSONObject(l).get("사진태그").toString() : "[]";
					obj = parser.parse(dmgFcltPhoto);
					dmgFcltPhotoArr = (org.json.simple.JSONArray) obj;
					obj = parser.parse(dmgFcltPhotoTag);
					dmgFcltPhotoTagArr = (org.json.simple.JSONArray) obj;

					HashMap<String, Object> projMap = new HashMap<>();
					projMap.put("lon1",dmgFclt.getJSONObject(l).has("X좌표") ? dmgFclt.getJSONObject(l).get("X좌표").toString() : null);
					projMap.put("lat1",dmgFclt.getJSONObject(l).has("Y좌표") ? dmgFclt.getJSONObject(l).get("Y좌표").toString() : null);
					
					List<EgovMap> projList = fckAprComptService.selectFckAprProjDMS(projMap);
					
					dmgFcltObj.put("photo", dmgFcltPhotoArr);
					dmgFcltObj.put("photoTag", dmgFcltPhotoTagArr);
					dmgFcltObj.put("px", dmgFclt.getJSONObject(l).has("X좌표") ? projList.get(0).get("lon1") : "");
					dmgFcltObj.put("py", dmgFclt.getJSONObject(l).has("Y좌표") ? projList.get(0).get("lat1") : "");
					
					if(dmgFcltObj.get("px") != null && !dmgFcltObj.get("px").equals("")) {
						dmsformal.setDmsLon(dmgFcltObj.get("px").toString());
						dmgFcltObj.put("px",dmsformal.getDmsLon());
					}
					if(dmgFcltObj.get("py") != null && !dmgFcltObj.get("py").equals("")) {
						dmsformal.setDmsLat(dmgFcltObj.get("py").toString());
						dmgFcltObj.put("py",dmsformal.getDmsLat());
					}
					
					
					
					dmgFcltObj.put("strctu", dmgFclt.getJSONObject(l).has("주요시설물") ?  (String) dmgFclt.getJSONObject(l).get("주요시설물").toString() : "");
					dmgFcltObj.put("sttus", dmgFclt.getJSONObject(l).has("현황") ? (String) dmgFclt.getJSONObject(l).get("현황").toString() : "");
					dmgFcltObj.put("note", dmgFclt.getJSONObject(l).has("비고") ? (String) dmgFclt.getJSONObject(l).get("비고").toString() : "");
					obj = parser.parse(dmgFcltObj.toString());
					dmgFcltArr.add(obj);
				}
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
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		String access = fckAprComptService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result", vo);
		model.addAttribute("access", access);
		model.addAttribute("dmgSttus_result", dmgSttusArr);
		model.addAttribute("trglnd_result", trglndArr);
		model.addAttribute("dmgFclt_result", dmgFcltArr);
		
		return "sys/fck/apr/sct/aprComptDetail";
	}
	
	/**
	 * 조사완료지 수정화면으로 이동한다.
	 * @param comptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/updateFckAprComptView.do")
	public String updateFckAprSvyComptView(
			@ModelAttribute("searchVO") FckAprComptVO searchVO,
			@ModelAttribute("locImgInfoVO") LocImgInfoVO locImgInfoVO,
			ModelMap model) throws Exception{

		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		FckAprComptVO vo = fckAprComptService.selectFckAprComptDetail(searchVO);
		
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
		
		if(vo.getSvyType().equals("사방댐 외관점검")) {
			// 사방댐 피해발생 현황도 사진 따옴표 제거
			if(vo.getSttusPrnt1().equals("{}")) vo.setSttusPrnt1("");
				else vo.setSttusPrnt1("/storage/fieldBook"+vo.getSttusPrnt1());
			if(vo.getSttusPrnt2().equals("{}")) vo.setSttusPrnt2("");
				else vo.setSttusPrnt2("/storage/fieldBook"+vo.getSttusPrnt2());
			if(vo.getSttusPrnt3().equals("{}")) vo.setSttusPrnt3("");
				else vo.setSttusPrnt3("/storage/fieldBook"+vo.getSttusPrnt3());
			if(vo.getSttusPrnt4().equals("{}")) vo.setSttusPrnt4("");
				else vo.setSttusPrnt4("/storage/fieldBook"+vo.getSttusPrnt4());
			if(vo.getSttusPrnt5().equals("{}")) vo.setSttusPrnt5("");
				else vo.setSttusPrnt5("/storage/fieldBook"+vo.getSttusPrnt5());
			
			vo.setSttusPrnt1(vo.getSttusPrnt1().replaceAll("\"", ""));
			vo.setSttusPrnt2(vo.getSttusPrnt2().replaceAll("\"", ""));
			vo.setSttusPrnt3(vo.getSttusPrnt3().replaceAll("\"", ""));
			vo.setSttusPrnt4(vo.getSttusPrnt4().replaceAll("\"", ""));
			vo.setSttusPrnt5(vo.getSttusPrnt5().replaceAll("\"", ""));
			if(vo.getDmgSttusPhoto() != null && !vo.getDmgSttusPhoto().equals("[]")) {
				String dmgsttutsPhoto = vo.getDmgSttusPhoto().toString();
				JSONParser parser = new JSONParser();
				Object obj;
				org.json.simple.JSONArray dmgttusPhotoArr = new org.json.simple.JSONArray();
				obj = new Object();
				JSONArray dmgSttusPhotoJSON = new JSONArray(dmgsttutsPhoto);
				org.json.simple.JSONArray dmgSttusPhotoArr = new org.json.simple.JSONArray();
				
				if(dmgSttusPhotoJSON.length() > 0 )  {
					for (int j=0; j<dmgSttusPhotoJSON.length(); j++) {
						JSONObject dmgSttusObj = new JSONObject();
						String dmgSttusPhoto = dmgSttusPhotoJSON.getJSONObject(j).has("사진") ? dmgSttusPhotoJSON.getJSONObject(j).get("사진").toString() : "[]";
						
						obj = parser.parse(dmgSttusPhoto.replaceAll("\\\\/\\\\/\\\\/", "///"));
						dmgSttusPhotoArr = (org.json.simple.JSONArray) obj;
						
						dmgSttusObj.put("photo", dmgSttusPhotoArr);
						
						String dmgSttusPhotoTag = dmgSttusPhotoJSON.getJSONObject(j).has("사진태그") ? dmgSttusPhotoJSON.getJSONObject(j).get("사진태그").toString() : "[]";
						
						obj = parser.parse(dmgSttusPhotoTag.replaceAll("\\\\/\\\\/\\\\/", "///"));
						dmgSttusPhotoArr = (org.json.simple.JSONArray) obj;
						
						dmgSttusObj.put("photoTag", dmgSttusPhotoArr);
						
			            obj = parser.parse(dmgSttusObj.toString());
			            dmgttusPhotoArr.add(obj);
			            
					}
				}
				
				model.addAttribute("dmgSttus_photo_result", dmgttusPhotoArr);
			}
		}
		if(vo.getSvyType().equals("계류보전 외관점검") || vo.getSvyType().equals("산지사방 외관점검")) {
			if(vo.getTrglndPhoto() != null && !vo.getTrglndPhoto().equals("[]")) {
				String trglndsPhoto = vo.getTrglndPhoto().toString();
				JSONParser parser = new JSONParser();
				Object obj;
				org.json.simple.JSONArray trglndPhotoArr = new org.json.simple.JSONArray();
				obj = new Object();
				JSONArray trglndPhotoJSON = new JSONArray(trglndsPhoto);
				org.json.simple.JSONArray trglndsPhotoArr = new org.json.simple.JSONArray();
				
				if(trglndPhotoJSON.length() > 0 )  {
					for (int j=0; j<trglndPhotoJSON.length(); j++) {
						JSONObject trglndObj = new JSONObject();
						String trglndPhoto = trglndPhotoJSON.getJSONObject(j).has("사진") ? trglndPhotoJSON.getJSONObject(j).get("사진").toString() : "[]";
						
						obj = parser.parse(trglndPhoto.replaceAll("\\\\/\\\\/\\\\/", "///"));
						trglndsPhotoArr = (org.json.simple.JSONArray) obj;
						
						trglndObj.put("photo", trglndsPhotoArr);
						
						String trglndPhotoTag = trglndPhotoJSON.getJSONObject(j).has("사진태그") ? trglndPhotoJSON.getJSONObject(j).get("사진태그").toString() : "[]";
						
						obj = parser.parse(trglndPhotoTag.replaceAll("\\\\/\\\\/\\\\/", "///"));
						trglndsPhotoArr = (org.json.simple.JSONArray) obj;
						
						trglndObj.put("photoTag", trglndsPhotoArr);
						
			            obj = parser.parse(trglndObj.toString());
			            trglndPhotoArr.add(obj);
			            
					}
				}
				
				model.addAttribute("trglnd_photo_result", trglndPhotoArr);
			}
			
			if(vo.getDmgFcltPhoto() != null && !vo.getDmgFcltPhoto().equals("[]")) {
				String dmgFcltsPhoto = vo.getDmgFcltPhoto().toString();
				JSONParser parser = new JSONParser();
				Object obj;
				org.json.simple.JSONArray dmgFcltPhotoArr = new org.json.simple.JSONArray();
				obj = new Object();
				JSONArray dmgFcltPhotoJSON = new JSONArray(dmgFcltsPhoto);
				org.json.simple.JSONArray dmgFcltsPhotoArr = new org.json.simple.JSONArray();
				
				if(dmgFcltPhotoJSON.length() > 0 )  {
					for (int j=0; j<dmgFcltPhotoJSON.length(); j++) {
						JSONObject dmgFcltObj = new JSONObject();
						String dmgFcltPhoto = dmgFcltPhotoJSON.getJSONObject(j).has("사진") ? dmgFcltPhotoJSON.getJSONObject(j).get("사진").toString() : "[]";
						
						obj = parser.parse(dmgFcltPhoto.replaceAll("\\\\/\\\\/\\\\/", "///"));
						dmgFcltsPhotoArr = (org.json.simple.JSONArray) obj;
						
						dmgFcltObj.put("photo", dmgFcltsPhotoArr);
						
						String dmgFcltPhotoTag = dmgFcltPhotoJSON.getJSONObject(j).has("사진태그") ? dmgFcltPhotoJSON.getJSONObject(j).get("사진태그").toString() : "[]";
						
						obj = parser.parse(dmgFcltPhotoTag.replaceAll("\\\\/\\\\/\\\\/", "///"));
						dmgFcltsPhotoArr = (org.json.simple.JSONArray) obj;
						
						dmgFcltObj.put("photoTag", dmgFcltsPhotoArr);
						
			            obj = parser.parse(dmgFcltObj.toString());
			            dmgFcltPhotoArr.add(obj);
			            
					}
				}
				model.addAttribute("dmgFclt_photo_result", dmgFcltPhotoArr);
			}
		}
		
		int photoTagNum = 5;
		if(vo.getSvyType().equals("산지사방 외관점검")) photoTagNum = 29;		
		model.addAttribute("photoTagNum", photoTagNum);
		model.addAttribute("fckAprCompt", vo);
		
		org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
		JSONParser parser = new JSONParser();
		Object obj;
		String photo = null;
		if(vo.getPhoto() != null &&vo.getPhoto().length() > 2) {
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
		
		comVO.setCodeId("FEI017");
		List<CmmnDetailCode> orginlDam_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("orginlDamCodeList", orginlDam_result);//본댐 측정값 코드목록
		
		comVO.setCodeId("FEI023");
		List<CmmnDetailCode> sideWall_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("sideWallCodeList", sideWall_result);//측벽 측정값 코드목록
		
		comVO.setCodeId("FEI018");
		List<CmmnDetailCode> jdgVal_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("jdgValCodeList", jdgVal_result);//본댐 판정값 코드목록

		comVO.setCodeId("FEI019");
		List<CmmnDetailCode> snddpsitJdgVal_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("snddpsitJdgValCodeList", snddpsitJdgVal_result);//저사상태 판정값
		
		comVO.setCodeId("FEI021");
		List<CmmnDetailCode> mngmtr_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("mngmtrCodeList", mngmtr_result);//조치사항 코드목록
		
		comVO.setCodeId("FEI022");
		List<CmmnDetailCode> appnRelis_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("appnRelisCodeList", appnRelis_result);//지정해제 코드목록
		
		comVO.setCodeId("FEI024");
		List<CmmnDetailCode> reinfc_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("reinfcCodeList", reinfc_result);//보강시설 코드목록
		
		comVO.setCodeId("FEI025");
		List<CmmnDetailCode> prtc_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("prtcCodeList", prtc_result);//보호시설 코드목록
		
		comVO.setCodeId("FEI026");
		List<CmmnDetailCode> drng_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("drngCodeList", drng_result);//배수시설 코드목록
		
		comVO.setCodeId("FEI027");
		List<CmmnDetailCode> cstPrvnfrst_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("cstPrvnfrstCodeList", cstPrvnfrst_result);//해안방재림 코드목록
		
		comVO.setCodeId("FEI028");
		List<CmmnDetailCode> cstRdcPrvn_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("cstRdcPrvnCodeList", cstRdcPrvn_result);//해안침식 코드목록
		
		comVO.setCodeId("FEI029");
		List<CmmnDetailCode> mooringMainfclt_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("mooringMainfcltCodeList", mooringMainfclt_result);//계류보전 주요시설  코드목록

		comVO.setCodeId("FEI039");
		List<CmmnDetailCode> fckrslt_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("fckRsltCodeList", fckrslt_result);//최종점검결과 코드목록		
		
		comVO.setCodeId("FEI044");
		List<CmmnDetailCode> dwnspt_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("dwnSptCodeList", dwnspt_result);//물받이 측정값 코드목록
		
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
		
		comVO.setCodeId("FEI060");
		List<CmmnDetailCode> knd_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("ecnrKndCodeList", knd_result);//사방댐 외관점검 조사유형

		//위치도 조회 처리		
		HashMap<String, Object> schMap = new HashMap<>();
		locImgInfoVO.setGid(Integer.parseInt(searchVO.getGid()));
		locImgInfoVO.setSvySe("APR");
		
		schMap.put("SE",locImgInfoVO.getSvySe());
		schMap.put("type", vo.getSvyType());
		schMap.put("gid", Integer.parseInt(vo.getGid()));		
		
		List<EgovMap> locList = extFieldBookService.selectComptLcMapLonLat(schMap);
		if(locList.isEmpty()) {
			locList = extFieldBookService.selectAprComptLcMapLonLat(schMap);
		}
		
		if(locList.size() > 0) {
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
			
			
			String mapType = "";
			if(locList.get(0).get("maptype") != null) {
				mapType += locList.get(0).get("maptype").toString();
			}
			
			org.json.simple.JSONArray paramArr = new org.json.simple.JSONArray();
			for(int i=0; i<locList.size(); i++) {
				JSONObject param = new JSONObject();
				param.put("center",center);
				param.put("svyid",svyid);
				param.put("se",se);
				param.put("marker",marker);
//				param.put("label",label.toString());
				param.put("label",label.toString().replaceAll("°", " ").replaceAll("'", " ").replaceAll("\"", " "));
				param.put("zoom", zoom);
				param.put("w",w);
				param.put("h",h);
				param.put("mapType", mapType);
				
				obj = parser.parse(param.toString());
				paramArr.add(obj);
				model.addAttribute("mapParam", paramArr.get(0).toString().replaceAll("\"", "'"));
//				model.addAttribute("mapParam", paramArr.get(0).toString().replaceAll("\"", "\'"));
			}
			
			model.addAttribute("orginl_zoom", zoom);
			
			
		}
		
		// 피해발생현황
		org.json.simple.JSONArray dmgSttusArr = new org.json.simple.JSONArray();
		if(!vo.getDmgSttus().equals("{}")) {
			obj = new Object();
			JSONArray dmgSttus = new JSONArray(vo.getDmgSttus());
			org.json.simple.JSONArray dmgSttusPhotoArr = new org.json.simple.JSONArray();
			org.json.simple.JSONArray dmgSttusPhotoTagArr = new org.json.simple.JSONArray();
			if(dmgSttus.length() > 0 )  {
				for (int j=0; j<dmgSttus.length(); j++) {
					JSONObject dmgSttusObj = new JSONObject();
					String dmgSttusPhoto = dmgSttus.getJSONObject(j).has("사진") ? dmgSttus.getJSONObject(j).get("사진").toString() : "[]";
					String dmgSttusPhotoTag = dmgSttus.getJSONObject(j).has("사진태그") ? (String) dmgSttus.getJSONObject(j).get("사진태그").toString() : "[]";
					
					
					obj = parser.parse(dmgSttusPhoto);
					dmgSttusPhotoArr = (org.json.simple.JSONArray) obj;
					obj = parser.parse(dmgSttusPhotoTag);
					dmgSttusPhotoTagArr = (org.json.simple.JSONArray) obj;
					
					
					dmgSttusObj.put("photo", dmgSttusPhotoArr);
					dmgSttusObj.put("photoTag", dmgSttusPhotoTagArr);
					dmgSttusObj.put("note", dmgSttus.getJSONObject(j).has("비고") ? dmgSttus.getJSONObject(j).get("비고").toString() : "");
					dmgSttusObj.put("loc", dmgSttus.getJSONObject(j).has("위치") ? dmgSttus.getJSONObject(j).get("위치").toString() : "");
					dmgSttusObj.put("type", dmgSttus.getJSONObject(j).has("유형") ? dmgSttus.getJSONObject(j).get("유형").toString() : "");
//					dmgSttusObj.put("photo", dmgSttus.getJSONObject(j).has("사진") ? dmgSttus.getJSONObject(j).get("사진").toString() : "");
					
					
		            obj = parser.parse(dmgSttusObj.toString());
		            dmgSttusArr.add(obj);
		            
				}
			}
		}
		model.addAttribute("dmgSttus_result", dmgSttusArr);
		
		// 대상지
		org.json.simple.JSONArray trglndArr = new org.json.simple.JSONArray();
		if(!vo.getTrglnd().equals("{}")) {
			String svyType = vo.getSvyType();
			obj = new Object();
			JSONArray trglnd = new JSONArray(vo.getTrglnd());
			org.json.simple.JSONArray trglndPhotoArr = new org.json.simple.JSONArray();
			org.json.simple.JSONArray trglndPhotoTagArr = new org.json.simple.JSONArray();
			
			if(trglnd.length() > 0 ){
				for (int k=0; k<trglnd.length(); k++) {
					JSONObject trglndObj = new JSONObject();
					String trglndPhoto = trglnd.getJSONObject(k).has("사진") ? (String) trglnd.getJSONObject(k).get("사진").toString() : "[]";
					String trglndPhotoTag = trglnd.getJSONObject(k).has("사진태그") ? (String) trglnd.getJSONObject(k).get("사진태그").toString() : "[]";
					obj = parser.parse(trglndPhoto);
					trglndPhotoArr = (org.json.simple.JSONArray)  obj;
					obj = parser.parse(trglndPhotoTag);
					trglndPhotoTagArr = (org.json.simple.JSONArray) obj;
					
					HashMap<String, Object> projMap = new HashMap<>();
					if(svyType.equals("계류보전 외관점검")) {
						projMap.put("lon1",trglnd.getJSONObject(k).has("X좌표1") ? trglnd.getJSONObject(k).get("X좌표1").toString() : null);
						projMap.put("lat1",trglnd.getJSONObject(k).has("Y좌표1") ? trglnd.getJSONObject(k).get("Y좌표1").toString() : null);
						projMap.put("lon2",trglnd.getJSONObject(k).has("X좌표2") ? trglnd.getJSONObject(k).get("X좌표2").toString() : null);
						projMap.put("lat2",trglnd.getJSONObject(k).has("Y좌표2") ? trglnd.getJSONObject(k).get("Y좌표2").toString() : null);
					}else {
						projMap.put("lon1",trglnd.getJSONObject(k).has("X좌표") ? trglnd.getJSONObject(k).get("X좌표").toString() : null);
						projMap.put("lat1",trglnd.getJSONObject(k).has("Y좌표") ? trglnd.getJSONObject(k).get("Y좌표").toString() : null);
						projMap.put("lon2",null);
						projMap.put("lat2",null);						
					}
					
					List<EgovMap> projList = fckAprComptService.selectFckAprProjDMS(projMap);
					
					trglndObj.put("photo", trglndPhotoArr);
					trglndObj.put("photoTag", trglndPhotoTagArr);
					if(svyType.equals("계류보전 외관점검")) {
						trglndObj.put("px1", trglnd.getJSONObject(k).has("X좌표1") ? projList.get(0).get("lon1") : "");
						trglndObj.put("py1", trglnd.getJSONObject(k).has("Y좌표1") ? projList.get(0).get("lat1") : "");
						
						if(projList.get(0) != null && projList.get(0).get("lon1") != null && !projList.get(0).get("lon1").equals("")) {
							String pxLon1 = projList.get(0).get("lon1").toString();
							String pxLonD1 = pxLon1.substring(0, pxLon1.indexOf("°"));
							String pxLonM1 = pxLon1.substring(pxLon1.indexOf("°")+1, pxLon1.indexOf("'"));
							String pxLonS1 = pxLon1.substring(pxLon1.indexOf("'")+1, pxLon1.indexOf("\""));
							
							trglndObj.put("pxD1", trglnd.getJSONObject(k).has("X좌표1") ? pxLonD1 : "");
							trglndObj.put("pxM1", trglnd.getJSONObject(k).has("X좌표1") ? pxLonM1 : "");
							trglndObj.put("pxS1", trglnd.getJSONObject(k).has("X좌표1") ? pxLonS1 : "");
						}
						
						if(projList.get(0) != null && projList.get(0).get("lat1") != null && !projList.get(0).get("lat1").equals("")) {
							String pxLat1 = projList.get(0).get("lat1").toString();
							String pxLatD1 = pxLat1.substring(0, pxLat1.indexOf("°"));
							String pxLatM1 = pxLat1.substring(pxLat1.indexOf("°")+1, pxLat1.indexOf("'"));
							String pxLatS1 = pxLat1.substring(pxLat1.indexOf("'")+1, pxLat1.indexOf("\""));
							
							trglndObj.put("pyD1", trglnd.getJSONObject(k).has("Y좌표1") ? pxLatD1 : "");
							trglndObj.put("pyM1", trglnd.getJSONObject(k).has("Y좌표1") ? pxLatM1 : "");
							trglndObj.put("pyS1", trglnd.getJSONObject(k).has("Y좌표1") ? pxLatS1 : "");
						}
						
						if(projList.get(0) != null && projList.get(0).get("lon2") != null && !projList.get(0).get("lon2").equals("")) {
							
							String pxLon2 = projList.get(0).get("lon2").toString();
							String pxLonD2 = pxLon2.substring(0, pxLon2.indexOf("°"));
							String pxLonM2 = pxLon2.substring(pxLon2.indexOf("°")+1, pxLon2.indexOf("'"));
							String pxLonS2 = pxLon2.substring(pxLon2.indexOf("'")+1, pxLon2.indexOf("\""));
							
							trglndObj.put("pxD2", trglnd.getJSONObject(k).has("X좌표2") ? pxLonD2 : "");
							trglndObj.put("pxM2", trglnd.getJSONObject(k).has("X좌표2") ? pxLonM2 : "");
							trglndObj.put("pxS2", trglnd.getJSONObject(k).has("X좌표2") ? pxLonS2 : "");
						}
						
						trglndObj.put("px2", trglnd.getJSONObject(k).has("X좌표2") ? projList.get(0).get("lon2") : "");
						trglndObj.put("py2", trglnd.getJSONObject(k).has("Y좌표2") ? projList.get(0).get("lat2") : "");
						
						if(projList.get(0) != null && projList.get(0).get("lat2") != null && !projList.get(0).get("lat2").equals("")) {
							String pxLat2 = projList.get(0).get("lat2").toString();
							String pxLatD2 = pxLat2.substring(0, pxLat2.indexOf("°"));
							String pxLatM2 = pxLat2.substring(pxLat2.indexOf("°")+1, pxLat2.indexOf("'"));
							String pxLatS2 = pxLat2.substring(pxLat2.indexOf("'")+1, pxLat2.indexOf("\""));
							
							trglndObj.put("pyD2", trglnd.getJSONObject(k).has("Y좌표2") ? pxLatD2 : "");
							trglndObj.put("pyM2", trglnd.getJSONObject(k).has("Y좌표2") ? pxLatM2 : "");
							trglndObj.put("pyS2", trglnd.getJSONObject(k).has("Y좌표2") ? pxLatS2 : "");
						}
						
					}else if(svyType.equals("산지사방 외관점검")){
						trglndObj.put("px", trglnd.getJSONObject(k).has("X좌표") ? projList.get(0).get("lon1") : "");
						trglndObj.put("py", trglnd.getJSONObject(k).has("Y좌표") ? projList.get(0).get("lat1") : "");
						
						if(projList.get(0) != null && projList.get(0).get("lon1") != null && !projList.get(0).get("lon1").equals("")) {
							String pxLon = projList.get(0).get("lon1").toString();
							String pxLonD = pxLon.substring(0, pxLon.indexOf("°"));
							String pxLonM = pxLon.substring(pxLon.indexOf("°")+1, pxLon.indexOf("'"));
							String pxLonS = pxLon.substring(pxLon.indexOf("'")+1, pxLon.indexOf("\""));
							
							trglndObj.put("pxD", trglnd.getJSONObject(k).has("X좌표") ? pxLonD : "");
							trglndObj.put("pxM", trglnd.getJSONObject(k).has("X좌표") ? pxLonM : "");
							trglndObj.put("pxS", trglnd.getJSONObject(k).has("X좌표") ? pxLonS : "");
						}
						
						
						if(projList.get(0) != null && projList.get(0).get("lat1") != null && !projList.get(0).get("lat1").equals("")) {
							String pxLat = projList.get(0).get("lat1").toString();
							String pxLatD = pxLat.substring(0, pxLat.indexOf("°"));
							String pxLatM = pxLat.substring(pxLat.indexOf("°")+1, pxLat.indexOf("'"));
							String pxLatS = pxLat.substring(pxLat.indexOf("'")+1, pxLat.indexOf("\""));
							
							trglndObj.put("pyD", trglnd.getJSONObject(k).has("Y좌표") ? pxLatD : "");
							trglndObj.put("pyM", trglnd.getJSONObject(k).has("Y좌표") ? pxLatM : "");
							trglndObj.put("pyS", trglnd.getJSONObject(k).has("Y좌표") ? pxLatS : "");
						}
						
						
					}else {
						trglndObj.put("px", trglnd.getJSONObject(k).has("X좌표") ? projList.get(0).get("lon1") : "");
						trglndObj.put("py", trglnd.getJSONObject(k).has("Y좌표") ? projList.get(0).get("lat1") : "");
					}
					
//					if(projList.get(0) != null) {
//						if(projList.get(0).containsKey("lat1") && projList.get(0).containsKey("lon1")) {
//							if(k == 0) vo.setSvyLatLon("(N) "+projList.get(0).get("lat1").toString()+" (E) "+projList.get(0).get("lon1").toString());
//						}else if(projList.get(0).containsKey("lat2") && projList.get(0).containsKey("lon2")) {
//							if(k == 0) vo.setSvyLatLon("(N) "+projList.get(0).get("lat2").toString()+" (E) "+projList.get(0).get("lon2").toString());
//						}else {
//							vo.setSvyLatLon("");
//						}
//					}
					trglndObj.put("strctu", trglnd.getJSONObject(k).has("주요시설물") ? (String) trglnd.getJSONObject(k).get("주요시설물").toString() : "");
					trglndObj.put("sttus", trglnd.getJSONObject(k).has("현황") ? (String) trglnd.getJSONObject(k).get("현황").toString() : "");
					trglndObj.put("note", trglnd.getJSONObject(k).has("비고") ? (String) trglnd.getJSONObject(k).get("비고").toString() : "");
					obj = parser.parse(trglndObj.toString());
		            trglndArr.add(obj);
		            
				}
			}
		}
		model.addAttribute("trglnd_result", trglndArr);
		
		org.json.simple.JSONArray dmgFcltArr = new org.json.simple.JSONArray();
		if(!vo.getDmgFclt().equals("{}")) {
			obj = new Object();
			JSONArray dmgFclt = new JSONArray(vo.getDmgFclt());
			org.json.simple.JSONArray dmgFcltPhotoArr = new org.json.simple.JSONArray();
			org.json.simple.JSONArray dmgFcltPhotoTagArr = new org.json.simple.JSONArray();
			if(dmgFclt.length() > 0 ){
				for (int l=0; l<dmgFclt.length(); l++) {
					JSONObject dmgFcltObj = new JSONObject();
					String dmgFcltPhoto = dmgFclt.getJSONObject(l).has("사진") ? (String) dmgFclt.getJSONObject(l).get("사진").toString() : "[]";
					String dmgFcltPhotoTag = dmgFclt.getJSONObject(l).has("사진태그") ? (String) dmgFclt.getJSONObject(l).get("사진태그").toString() : "[]";
					obj = parser.parse(dmgFcltPhoto);
					dmgFcltPhotoArr = (org.json.simple.JSONArray) obj;
					obj = parser.parse(dmgFcltPhotoTag);
					dmgFcltPhotoTagArr = (org.json.simple.JSONArray) obj;

					HashMap<String, Object> projMap = new HashMap<>();
					projMap.put("lon1",dmgFclt.getJSONObject(l).has("X좌표") ? dmgFclt.getJSONObject(l).get("X좌표").toString() : null);
					projMap.put("lat1",dmgFclt.getJSONObject(l).has("Y좌표") ? dmgFclt.getJSONObject(l).get("Y좌표").toString() : null);
					
					List<EgovMap> projList = fckAprComptService.selectFckAprProjDMS(projMap);
					
					dmgFcltObj.put("photo", dmgFcltPhotoArr);
					dmgFcltObj.put("photoTag", dmgFcltPhotoTagArr);
					dmgFcltObj.put("px", dmgFclt.getJSONObject(l).has("X좌표") ? projList.get(0).get("lon1") : "");
					
					if(dmgFcltObj.get("px") != null && !dmgFcltObj.get("px").equals("")) {
						String pxLon1 = projList.get(0).get("lon1").toString();
						String pxLonD1= pxLon1.substring(0, pxLon1.indexOf("°"));
						String pxLonM1= pxLon1.substring(pxLon1.indexOf("°")+1, pxLon1.indexOf("'"));
						String pxLonS1= pxLon1.substring(pxLon1.indexOf("'")+1, pxLon1.indexOf("\""));
						
						dmgFcltObj.put("pxD1", dmgFclt.getJSONObject(l).has("X좌표") ? pxLonD1 : "");
						dmgFcltObj.put("pxM1", dmgFclt.getJSONObject(l).has("X좌표") ? pxLonM1 : "");
						dmgFcltObj.put("pxS1", dmgFclt.getJSONObject(l).has("X좌표") ? pxLonS1 : "");
					}
					
					dmgFcltObj.put("py", dmgFclt.getJSONObject(l).has("Y좌표") ? projList.get(0).get("lat1") : "");

					if(dmgFcltObj.get("py") != null && !dmgFcltObj.get("py") .equals("")) {
						String pxLat1 = projList.get(0).get("lat1").toString();
						String pxLatD1= pxLat1.substring(0, pxLat1.indexOf("°"));
						String pxLatM1= pxLat1.substring(pxLat1.indexOf("°")+1, pxLat1.indexOf("'"));
						String pxLatS1= pxLat1.substring(pxLat1.indexOf("'")+1, pxLat1.indexOf("\""));
						
						dmgFcltObj.put("pyD1", dmgFclt.getJSONObject(l).has("Y좌표") ? pxLatD1 : "");
						dmgFcltObj.put("pyM1", dmgFclt.getJSONObject(l).has("Y좌표") ? pxLatM1 : "");
						dmgFcltObj.put("pyS1", dmgFclt.getJSONObject(l).has("Y좌표") ? pxLatS1 : "");
					}
					
					dmgFcltObj.put("strctu", dmgFclt.getJSONObject(l).has("주요시설물") ?  (String) dmgFclt.getJSONObject(l).get("주요시설물").toString() : "");
					dmgFcltObj.put("sttus", dmgFclt.getJSONObject(l).has("현황") ? (String) dmgFclt.getJSONObject(l).get("현황").toString() : "");
					dmgFcltObj.put("note", dmgFclt.getJSONObject(l).has("비고") ? (String) dmgFclt.getJSONObject(l).get("비고").toString() : "");
					obj = parser.parse(dmgFcltObj.toString());
					dmgFcltArr.add(obj);
				}
			}
		}
		model.addAttribute("dmgFclt_result", dmgFcltArr);
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		String test = EgovUserDetailsHelper.getAuthorities().toString();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
				
		/* 공유방 권한 확인 */
		String access = fckAprComptService.selectAuthorCheck(commandMap);
		model.addAttribute("access", access);
				
		return "sys/fck/apr/sct/aprComptUpdt";
	}
	
	/**
	 * 조사완료지를 수정한다.
	 * @param comptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/fck/apr/sct/updateFckAprCompt.do")
    public ModelAndView updateFckAprSvyCompt(@ModelAttribute("comptVO") FckAprComptVO comptVO, BindingResult bindingResult, Model model, HttpServletRequest req, MultipartHttpServletRequest multiRequest) throws Exception {
		HashMap<String, Object> schMap = new HashMap<>();		
	
		ModelAndView mv = new ModelAndView("jsonView");
							
		String svyType = comptVO.getSvyType();
		
		LocImgInfoVO locImgInfoVO = new LocImgInfoVO();
		
		comptVO.setPhotoTagList(comptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		
		if(svyType.equals("사방댐 외관점검")) {
			if(comptVO.getDmgSttus() != null) {
				comptVO.setDmgSttus(comptVO.getDmgSttus().toString().replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1").replaceAll("\"\\[", "\\[").replaceAll("\\]\"", "\\]"));
			}
			// 공유방명
			String mstNm = comptVO.getMstNm().toString();
			mstNm = mstNm.concat(".ncx");
			// 조사ID명
			String svyId = comptVO.getSvyId().toString();
			
			String sttusPrnt1 = req.getParameter("rawSttusPrnt1").replaceAll("&quot;", "\"");
			String sttusPrnt2 = req.getParameter("rawSttusPrnt2").replaceAll("&quot;", "\"");
			String sttusPrnt3 = req.getParameter("rawSttusPrnt3").replaceAll("&quot;", "\"");
			String sttusPrnt4 = req.getParameter("rawSttusPrnt4").replaceAll("&quot;", "\"");
			String sttusPrnt5 = req.getParameter("rawSttusPrnt5").replaceAll("&quot;", "\"");
			
			 String[] sttusPrnt1FileName1 = sttusPrnt1.replaceAll("\"", "").split("/");
			 String fileName1 = sttusPrnt1FileName1[sttusPrnt1FileName1.length - 1];
			 if(fileName1.equals("")) {
				 sttusPrnt1 = "";
			 }else {
				 sttusPrnt1 = "\"반수면\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName1+"\"]},";
			 }
			String[] sttusPrnt1FileName2 = sttusPrnt2.replaceAll("\"", "").split("/");
			String fileName2 = sttusPrnt1FileName2[sttusPrnt1FileName2.length - 1];
			if(fileName2.equals("")) {
				 sttusPrnt2 = "";
			 }else {
				 sttusPrnt2 = "\"대수면\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName2+"\"]},";
			 }
			String[] sttusPrnt1FileName3 = sttusPrnt3.replaceAll("\"", "").split("/");
			String fileName3 = sttusPrnt1FileName3[sttusPrnt1FileName3.length - 1];
			if(fileName3.equals("")) {
				sttusPrnt3 = "";
			}else {
				sttusPrnt3 = "\"본체\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName3+"\"]},";
			}
			String[] sttusPrnt1FileName4 = sttusPrnt4.replaceAll("\"", "").split("/");
			String fileName4 = sttusPrnt1FileName4[sttusPrnt1FileName4.length - 1];
			if(fileName4.equals("")) {
				sttusPrnt4 = "";
			}else {
				sttusPrnt4 = "\"좌안측벽\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName4+"\"]},";
			}
			String[] sttusPrnt1FileName5 = sttusPrnt5.replaceAll("\"", "").split("/");
			String fileName5 = sttusPrnt1FileName5[sttusPrnt1FileName5.length - 1];
			if(fileName5.equals("")) {
				sttusPrnt5 = "";
			}else {
				sttusPrnt5 = "\"우안측벽\": {\"사진\": [\"gimg:///"+mstNm+"/"+fileName5+"\"]},";
			}
			
 			for(int i=1; i<=5; i++) {
				List<MultipartFile> sttusPrntFile = multiRequest.getFiles("sttusPrnt"+i);
				if(!sttusPrntFile.isEmpty() && !sttusPrntFile.equals("[]")) {
					sttusPrntFile.get(0).getName();
					String checkNm = sttusPrntFile.get(0).getName();
					String uploadNm = aprComptFileUpload(sttusPrntFile, mstNm, svyId, checkNm);
					
					if(i == 1 && !uploadNm.equals("")) { //반수면
						comptVO.setSttusPrnt1(uploadNm);
						sttusPrnt1 = "\"반수면\": {\"사진\": [\""+comptVO.getSttusPrnt1()+"\"]},";
					}
					if(i == 2 && !uploadNm.equals("")) { //대수면
						comptVO.setSttusPrnt2(uploadNm);
						sttusPrnt2 = "\"대수면\": {\"사진\": [\""+comptVO.getSttusPrnt2()+"\"]},";
					}
					if(i == 3 && !uploadNm.equals("")) { //본체
						comptVO.setSttusPrnt3(uploadNm);
						sttusPrnt3 = "\"본체\": {\"사진\": [\""+comptVO.getSttusPrnt3()+"\"]},";
					}
					if(i == 4 && !uploadNm.equals("")) { //좌안측벽
						comptVO.setSttusPrnt4(uploadNm);
						sttusPrnt4 = "\"좌안측벽\": {\"사진\": [\""+comptVO.getSttusPrnt4()+"\"]},";
					}
					if(i == 5 && !uploadNm.equals("")) { //우안측벽
						comptVO.setSttusPrnt5(uploadNm);
						sttusPrnt5 = "\"우안측벽\": {\"사진\": [\""+comptVO.getSttusPrnt5()+"\"]},";
					}
				}
 			}
				
 			String sttusPrnt = "{"+ sttusPrnt1 + sttusPrnt2 + sttusPrnt3 + sttusPrnt4 + sttusPrnt5 + "}";

 			int sttusPrntLastIndex = sttusPrnt.lastIndexOf(",");
 			if(sttusPrntLastIndex >= 0) {
 				sttusPrnt = sttusPrnt.substring(0, sttusPrntLastIndex) + sttusPrnt.substring(sttusPrntLastIndex + 1);
 			}else{
 				sttusPrnt = "[]";
 			}
 			
 			comptVO.setSttusPrnt(sttusPrnt);
		}else {
			// 대상지 위치 및 주요시설
			JSONArray aprTrglnd = null;
			if(comptVO.getTrglnd() != null) {
				comptVO.setTrglnd(comptVO.getTrglnd().replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1"));
				if(!comptVO.getTrglnd().isEmpty()) aprTrglnd = new JSONArray(comptVO.getTrglnd());
				for(int i=0; i<aprTrglnd.length(); i++) {
	 				HashMap<String, Object> projMap = new HashMap<>();
	 				if(svyType.equals("계류보전 외관점검")) {
						projMap.put("bpy",aprTrglnd.getJSONObject(i).has("Y좌표1") ? aprTrglnd.getJSONObject(i).get("Y좌표1").toString() : null);
						projMap.put("bpx",aprTrglnd.getJSONObject(i).has("X좌표1") ? aprTrglnd.getJSONObject(i).get("X좌표1").toString() : null);
						projMap.put("epy",aprTrglnd.getJSONObject(i).has("Y좌표2") ? aprTrglnd.getJSONObject(i).get("Y좌표2").toString() : null);
						projMap.put("epx",aprTrglnd.getJSONObject(i).has("X좌표2") ? aprTrglnd.getJSONObject(i).get("X좌표2").toString() : null);
						
						List<EgovMap> projList = fckAprComptService.selectFckAprProjBessel(projMap);
						
						aprTrglnd.getJSONObject(i).remove("X좌표1");
						aprTrglnd.getJSONObject(i).remove("Y좌표1");
						aprTrglnd.getJSONObject(i).remove("X좌표2");
						aprTrglnd.getJSONObject(i).remove("Y좌표2");
						
						if(projList.get(0).containsKey("bpx")) {
							if(!projList.get(0).get("bpx").toString().isEmpty()) aprTrglnd.getJSONObject(i).put("X좌표1",projList.get(0).get("bpx"));
						}
						if(projList.get(0).containsKey("bpy")) {
							if(!projList.get(0).get("bpy").toString().isEmpty()) aprTrglnd.getJSONObject(i).put("Y좌표1",projList.get(0).get("bpy"));
						}
						if(projList.get(0).containsKey("epx")) {
							if(!projList.get(0).get("epx").toString().isEmpty()) aprTrglnd.getJSONObject(i).put("X좌표2",projList.get(0).get("epx"));
						}
						if(projList.get(0).containsKey("epy")) {
							if(!projList.get(0).get("epy").toString().isEmpty()) aprTrglnd.getJSONObject(i).put("Y좌표2",projList.get(0).get("epy"));
						}
						
	 				}else if(svyType.equals("산지사방 외관점검")) {
	 					projMap.put("bpy",aprTrglnd.getJSONObject(i).has("Y좌표") ? aprTrglnd.getJSONObject(i).get("Y좌표").toString() : null);
						projMap.put("bpx",aprTrglnd.getJSONObject(i).has("X좌표") ? aprTrglnd.getJSONObject(i).get("X좌표").toString() : null);
						
						List<EgovMap> projList = fckAprComptService.selectFckAprProjBessel(projMap);
						
						aprTrglnd.getJSONObject(i).remove("X좌표");
						aprTrglnd.getJSONObject(i).remove("Y좌표");
						
						if(projList.get(0).containsKey("bpx")) {
							if(!projList.get(0).get("bpx").toString().isEmpty()) aprTrglnd.getJSONObject(i).put("X좌표",projList.get(0).get("bpx"));
						}
						if(projList.get(0).containsKey("bpy")) {
							if(!projList.get(0).get("bpy").toString().isEmpty()) aprTrglnd.getJSONObject(i).put("Y좌표",projList.get(0).get("bpy"));
						}

	 				}
	 				String dmgTrglndPhotoTag = req.getParameter("dmgTrglndPhotoTag"+(i+1)).replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1");

	 				if(!dmgTrglndPhotoTag.isEmpty() && dmgTrglndPhotoTag != null) {
						aprTrglnd.getJSONObject(i).put("사진태그",dmgTrglndPhotoTag);
					}else {
						aprTrglnd.getJSONObject(i).put("사진태그","[]");
					}
	 				
				}
				
				if(aprTrglnd.length() > 0) {
					comptVO.setTrglnd(aprTrglnd.toString().replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1").replaceAll("\"\\[", "\\[").replaceAll("\\]\"", "\\]"));
				}
			}
			
			// 피해시설 위치 및 피해현황
			JSONArray aprDmgFclt = null;
	 		if(comptVO.getDmgFclt() != null) {
	 			if(!comptVO.getDmgFclt().isEmpty()) aprDmgFclt = new JSONArray(comptVO.getDmgFclt().replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1"));
	 			for(int i=0; i<aprDmgFclt.length(); i++) {
	 				HashMap<String, Object> projMap = new HashMap<>();
					projMap.put("bpy",aprDmgFclt.getJSONObject(i).has("Y좌표") ? aprDmgFclt.getJSONObject(i).get("Y좌표").toString() : null);
					projMap.put("bpx",aprDmgFclt.getJSONObject(i).has("X좌표") ? aprDmgFclt.getJSONObject(i).get("X좌표").toString() : null);
					
					if(projMap.get("bpy") != null && projMap.get("bpx") != null && !projMap.get("bpy").equals("") && !projMap.get("bpx").equals("") && !projMap.get("bpy").equals("null") && !projMap.get("bpx").equals("null")) {
						List<EgovMap> projList = fckAprComptService.selectFckAprProjBessel(projMap);
						
						aprDmgFclt.getJSONObject(i).remove("X좌표");
						aprDmgFclt.getJSONObject(i).remove("Y좌표");
						
						if(projList.get(0).containsKey("bpx")) {
							if(!projList.get(0).get("bpx").toString().isEmpty()) aprDmgFclt.getJSONObject(i).put("X좌표",projList.get(0).get("bpx"));
						}
						if(projList.get(0).containsKey("bpy")) {
							if(!projList.get(0).get("bpy").toString().isEmpty()) aprDmgFclt.getJSONObject(i).put("Y좌표",projList.get(0).get("bpy"));
						}
						
					}else {
						aprDmgFclt.getJSONObject(i).remove("X좌표");
						aprDmgFclt.getJSONObject(i).remove("Y좌표");
					}
					String dmgFcltPhotoTag = req.getParameter("dmgFcltPhotoTag"+(i+1)).replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1");
					
					if(!dmgFcltPhotoTag.isEmpty() && dmgFcltPhotoTag != null) {
						aprDmgFclt.getJSONObject(i).put("사진태그",dmgFcltPhotoTag);
					}else {
						aprDmgFclt.getJSONObject(i).put("사진태그", "[]");
					}
					
				}
	 			
				if(aprDmgFclt.length() > 0) {
					comptVO.setDmgFclt(aprDmgFclt.toString().replaceAll("&quot;", "\"").replaceAll("\\\\","").replaceAll("\"\\[\"", "[\"").replaceAll("\"(\\])\"", "\"$1").replaceAll("\"\\[", "\\[").replaceAll("\\]\"", "\\]"));
				}
			}
		}
		try {
			fckAprComptService.updateFckAprCompt(comptVO);				
			schMap.put("mst_id", Integer.parseInt(comptVO.getMstId()));
			List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
			schMap.put("SE","APR");
			schMap.put("type",comptVO.getSvyType());
			schMap.put("gid",Integer.parseInt(comptVO.getGid()));
			schMap.put("_label", comptVO.getSvyId());
			schMap.put("path", fieldBookUploadPath.concat("/").concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/")); //저장경로
			schMap.put("midPath","/".concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/"));
			schMap.put("zoom",Integer.parseInt(comptVO.getChangedZoom()));
			schMap.put("mapType",comptVO.getMapType());
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
	 * @param comptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/deleteFckAprCompt.do")
	public ModelAndView deleteFckAprCompt(FckAprComptVO ComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			fckAprComptService.deleteFckAprCompt(ComptVO);
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
	 * 조사완료지의 대상지목록을 엑셀로 다운로드한다.
	 * @param gId
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/selectDownloadSldListExcel.do")
	public ModelAndView selectDownloadSldListExcel(FckAprComptVO comptVO,HttpServletResponse res) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
    	List<EgovMap> dmgFcltList = new ArrayList<EgovMap>();
    	
    	FckAprComptVO vo = fckAprComptService.selectFckAprComptDetail(comptVO);
    	String trglnd = vo.getTrglnd();
    	if(trglnd != null) {
    		JSONArray dmgArr = new JSONArray(new JSONTokener(trglnd));
    		for (int i = 0; i < dmgArr.length(); i++) {
    			JSONObject dmgItem = (JSONObject)dmgArr.get(i);
    			//EgovMap map = new ObjectMapper().readValue(dmgItem.toString(), EgovMap.class);
    			EgovMap map = new EgovMap();
    			map.put("x좌표", dmgItem.has("X좌표") ? dmgItem.get("X좌표").toString() : "");
    			map.put("y좌표", dmgItem.has("Y좌표") ? dmgItem.get("Y좌표").toString() : "");
    			map.put("주요시설물", dmgItem.has("주요시설물") ? dmgItem.get("주요시설물") : "");
    			map.put("비고", dmgItem.has("비고") ? dmgItem.get("비고").toString() : "");
    			map.put("현황", dmgItem.has("현황") ? dmgItem.get("현황").toString() : "");
    			dmgFcltList.add(map);
			}
    		
    	}
    	
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String fileFrontNm = vo.getSvyId() != null ? vo.getSvyId()+"_대상지_" : "대상지_";
    	String fileNm = fileFrontNm.concat(formater.format(new Date()).toString());
    	String[] columnArray = {"x좌표","y좌표","주요시설물","비고","현황"};
    	String[] columnVarArray = {"x좌표","y좌표","주요시설물","비고","현황"};
    	
    	dataMap.put("columnArr", columnArray);
    	dataMap.put("columnVarArr", columnVarArray);
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", dmgFcltList);
    	
    	modelAndView.addObject("dataMap",dataMap);
    	modelAndView.addObject("filename",fileNm);
    	
    	return modelAndView;
	}
	
	/**
	 * 조사지 사진목록을 압축하여 다운로드한다.
	 * @param comptVO
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/selectDownloadPhotoListZip.do")
	public void selectDownloadPhotoListZip(FckAprComptVO comptVO,HttpServletRequest req,HttpServletResponse res) throws Exception{
		EgovMap fieldBookNmMap = fckAprComptService.selectFieldBookNm(comptVO);
		
		String photoType = req.getParameter("photoType");
		
		String svyId = (String)fieldBookNmMap.get("svyid");//조사아이디
		String fieldBookNm = (String)fieldBookNmMap.get("fieldbooknm");//공유방사진폴더명
		
		File storeFile = new File(fileStoreDir.concat(fieldBookNm+".ncx"));//공유방 경로 파일 생성
		
		//현장사진 디렉토리의 공유방 폴더에서 조사아이디로 시작하는 이미지 검색 사방댐
		FckAprComptVO vo = fckAprComptService.selectFckAprComptDetail(comptVO);
		
		File[] files = null;
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
 		String dt_str = formater.format(new Date()).toString();//현재시간 문자열 생성
 		String dir = fileStoreDir.concat(svyId+"_"+dt_str);//조사아이디_현재시간
 		Path path = Paths.get(dir);
 		Files.createDirectories(path);//조사아이디_현재시간 폴더 생성
 		
 		File directory = new File(path.toString());
 		
		if(photoType.equals("산지사방")) {
			String pattern = "(^"+svyId+"\\.[0-9][0-9][0-9][0-9]\\.(?i)(jpg|png))$";
			//현장사진 디렉토리의 공유방 폴더에서 조사아이디로 시작하는 이미지 검색
			files = storeFile.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.matches(pattern)) {
						File f = new File(dir, name);
						return f.isFile();
					}
					return false;
				}
			});
		}else { 
			String pattern = null;
			if(photoType.equals("대상지")) {
				pattern = vo.getTrglndPhoto();
			}else if(photoType.equals("피해시설")) {
				pattern = vo.getDmgFcltPhoto();
			}else if(photoType.equals("피해발생현황")) {
				pattern = vo.getDmgSttusPhoto();
			}
			
			// 해당 사진 타입에 맞는 사진 찾기
			List<String> jpgList = new ArrayList<>();
			JSONParser parser = new JSONParser();
			org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(pattern);
			
			for(int i=0; i<jsonArray.size(); i++) {
				String subDir = path.toString().concat(File.separator+(i+1)+"번");
				Path subPath = Paths.get(subDir);
				Files.createDirectories(subPath);
			}
			
			for (Object obj : jsonArray) {
				org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
				if(!jsonObject.isEmpty()) {
					org.json.simple.JSONArray photoArray = (org.json.simple.JSONArray) jsonObject.get("사진");
					for (Object photoObj : photoArray) {
						String photo = (String) photoObj;
						String fileName = photo.substring(photo.lastIndexOf("/") + 1);
						if (fileName.endsWith(".jpg")) {
							jpgList.add(fileName);
						}
					}
				}
			}
			
			files = storeFile.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					for(int i=0; i<jpgList.size(); i++) {
						if(name.matches(jpgList.get(i))) {
							File f = new File(dir, name);
							return f.isFile();
						}
					}
					return false;
				}
			});   
		}
 		
 		if(files != null) {
	 		for (int i = 0; i < files.length; i++) {
	 			String nm = files[i].getName();
	 			JSONParser parser = new JSONParser();
	 			
	 			String parseType = null;
	 			
	 			if(photoType.equals("대상지")) {
	 				parseType = vo.getTrglndPhoto();
				}else if(photoType.equals("피해시설")) {
					parseType = vo.getDmgFcltPhoto();
				}else if(photoType.equals("피해발생현황")) {
					parseType = vo.getDmgSttusPhoto();
				}
	 			
				org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(parseType);
				for(int j=0; j<jsonArray.size(); j++) {
					if(jsonArray.get(j).toString().contains(nm)) {
						System.out.println(j+"번");
						EgovFileUtil.cp(storeFile+File.separator+nm, dir.concat(File.separator+(j+1)+"번")+File.separator+nm);//원본이미지 복사
					}
				}
	 		}
	 		
	 		String source = dir;
	 		String target = dir.concat(".zip");
	 		
	 		//폴더 압축
	 		boolean isCompressed = EgovFileCmprs.cmprsFile(source, target);
	 		
	 		FileInputStream fis = null;
	 		BufferedInputStream bis = null;
	 		ServletOutputStream so = null;
	 		BufferedOutputStream bos = null;
	 		
	 		if(isCompressed) {
	 			try {
	
	 				byte[] buffer = new byte[1024];
	 				
	 				String fileName = photoType+"_"+svyId+"_"+dt_str + ".zip";
	 				URLEncoder.encode(fileName, "UTF-8");
	 				
	 				res.setContentType("application/zip");
	 			    res.addHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileName, "UTF-8"));
	 				fis = new FileInputStream(target);
	 				bis = new BufferedInputStream(fis);
	 				so = res.getOutputStream();
	 				bos = new BufferedOutputStream(so);
	
	 				int n = 0;
	 				while((n = bis.read(buffer)) > 0){ 
	 					bos.write(buffer, 0, n); 
	 					bos.flush(); 
	 				}
	 				
	 			} catch (IOException e) {
	 				LOGGER.error(e.getMessage());
	 			}finally{
	 				if(bos != null) bos.close(); 
	 				if(bis != null) bis.close(); 
	 				if(so != null) so.close(); 
	 				if(fis != null) fis.close();
	 				
	 				EgovFileUtil.rm(target);
//	 				EgovFileUtil.rm(source); 하위 폴더가 있는 경우 삭제가 되지 않기에 deleteDirectory()사용
	 				FileUtils.deleteDirectory(directory);
	 			}
	 		}else {
 				FileUtils.deleteDirectory(directory);
 				EgovFileUtil.rm(target);
	 			throw new Exception("압축파일 생성이 실패하였습니다.");
	 		}
 		}else {
			FileUtils.deleteDirectory(directory);
 			throw new Exception("압축파일 생성이 실패하였습니다.");
 		}
	}
	
	/**
	 * 조사완료지 엑셀을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/fck/apr/sct/selectAprComptListExcel.do")
	public ModelAndView selectAprComptListExcel(FckAprComptVO aprComptVo, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		
		
	 	modelAndView.setViewName("aprExcelView");

	 	String svyType = request.getParameter("svyType");
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HashMap<?, ?> _map = (HashMap<?, ?>)fckAprComptService.selectFckAprSvyComptListExcel(aprComptVo);
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		
		String fileNm = svyType+"_조사완료지_".concat(formater.format(new Date()).toString());
		
		dataMap.put("sheetNm", fileNm);
		dataMap.put("list", _map.get("resultList"));
		
		modelAndView.addObject("dataMap",dataMap);
		modelAndView.addObject("filename",fileNm);
		
		return modelAndView;
	}
	
	/**
	 * 조사완료 엑셀 재업로드 팝업
	 * @param aprComptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/updateFckAprComptPopup.do")
	public String updateFckAprComptPopup(
			@ModelAttribute("aprComptVO") FckAprComptVO aprComptVO, 
			ModelMap model) throws Exception{
		return "sys/fck/apr/sct/updateFckAprComptPopup";
	}
	
	/**
	 * 조사완료 엑셀 재업로드
	 * @param aprComptVO
	 * @param mFile
	 * @param model
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sct/updateFckAprComptExcel.do")
	public ModelAndView updateFckAprComptExcel(
			@ModelAttribute("aprComptVO") FckAprComptVO aprComptVO, 
			@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		JSONObject results = fckAprComptService.updateFckAprComptExcel(aprComptVO,mFile);
		
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
	@RequestMapping(value = "/sys/fck/apr/sct/updateLocReCreatePopup.do")
	public String updateLocReCreatePopup(ModelMap model) throws Exception {
		
		LocReCreateVO searchMap = new LocReCreateVO();
		EgovMap dateMap = fckAprComptService.selectLastUpdateMinMaxDate(searchMap);
		
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
	@RequestMapping(value = "/sys/fck/apr/sct/selectLocReCeateCnt.do")
	public ModelAndView selectLocReCeateCnt(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			EgovMap dateMap = fckAprComptService.selectLastUpdateMinMaxDate(searchVO);
			
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
	@RequestMapping(value = "/sys/fck/apr/sct/updateLocReCreate.do")
	public ModelAndView updateLocReCreate(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			List<?> list = fckAprComptService.updateLocReCreate(searchVO);
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
				schMap.put("mapType", map.get("maptype"));
				
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
	 * 피해발생 현황도 이미지파일업로드(사방댐)
	 * @param sttusPrntFile
	 * @param mstNm
	 * @param svyId
	 * @param checkNm
	 * @return saveFileNm
	 * @throws Exception
	 */
	private String aprComptFileUpload(List<MultipartFile> sttusPrntFile, String mstNm, String svyId, String checkNm) throws Exception{
		File imgFile = new File(fileStoreDir.concat(mstNm));
		ArrayList<String> imgList = new ArrayList<String>();
		
		String saveFileNm = "";
		String newFileNm  = "";
		
		for (MultipartFile file : sttusPrntFile ) {
			String fileNm = file.getOriginalFilename().toString(); //원본파일명
			
			if(checkNm.equals("sttusPrnt1")) {
				newFileNm = svyId.concat("_반수면")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("sttusPrnt2")) {
				newFileNm = svyId.concat("_대수면")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("sttusPrnt3")) {
				newFileNm = svyId.concat("_본체")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("sttusPrnt4")) {
				newFileNm = svyId.concat("_좌안측벽")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
			}else if(checkNm.equals("sttusPrnt5")) {
				newFileNm = svyId.concat("_우안측벽")+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
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
	
}
	