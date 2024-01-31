package or.sabang.sys.fck.mse.web;

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
import java.util.Collection;
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
import org.apache.commons.beanutils.BeanUtils;
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
import egovframework.com.cmm.EgovBrowserUtil;
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
import egovframework.com.utl.sim.service.EgovFileTool;
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
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.fck.mse.service.FckMseComptService;
//import or.sabang.sys.fck.mse.service.FckMseComptService;
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class FckMseComptController {
	
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
	
	@Resource(name = "fckMseComptService") 	
	private FckMseComptService fckMseComptService;
	
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
    
	private static final Logger LOGGER = LoggerFactory.getLogger(FckMseComptController.class);
	

	/**
	 * 조사완료목록을 조회한다.
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sct/selectMseComptList.do")	
    public String selectFckMseComptList (@ModelAttribute("searchVO") FckMseComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {
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
			searchVO.setSvyYear(fckMseComptService.selectFckMseComptMaxYear());
		}
		Date date = new Date();
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(fckMseComptService.selectFckMseComptMaxMonth());
			searchVO.setSvyMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI016");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = fckMseComptService.selectFckMseComptYear();
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
		
		List<FckMseComptVO> comptList = fckMseComptService.selectFckMseComptList(searchVO);
		model.addAttribute("resultList", comptList);

		int totCnt = fckMseComptService.selectFckMseComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/fck/mse/sct/mseComptList";
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param comptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sct/selectFckMseComptDetail.do")
 	public String selectFckMseComptDetail(@ModelAttribute("searchVO") FckMseComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		// 계측기 목록 조회
		searchVO.setSvyType("계측장비");
		List<FckMseComptVO> mseComptList = fckMseComptService.selectFckMseComptDetail(searchVO);
		// 계측기 대상지 일반사항 조회
		searchVO.setSvyType("계측장비 일반사항");
		List<FckMseComptVO> mseComptDetail = fckMseComptService.selectFckMseComptDetail(searchVO);
		
		// 계측기 점검 불량 상태
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI174");
		List<?> mseSttus = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("mseSttus", mseSttus);
		
		//와이어신축계
		List<JSONObject> wireExtList = new ArrayList<JSONObject>();
		List<JSONObject> wireExtPerList = new ArrayList<JSONObject>();
		List<String> wireExtPhotoList = new ArrayList<String>();
		List<JSONObject> wireExtPhotoTagList = new ArrayList<JSONObject>();
		//지중경사계
		List<JSONObject> licMeterList = new ArrayList<JSONObject>();		
		List<JSONObject> licMeterPerList = new ArrayList<JSONObject>();		
		List<String> licMeterPhotoList = new ArrayList<String>();		
		List<JSONObject> licMeterPhotoTagList = new ArrayList<JSONObject>();		
		//지하수위계
		List<JSONObject> gwGaugeList = new ArrayList<JSONObject>();		
		List<JSONObject> gwGaugePerList = new ArrayList<JSONObject>();
		List<String> gwGaugePhotoList = new ArrayList<String>();		
		List<JSONObject> gwGaugePhotoTagList = new ArrayList<JSONObject>();		
		//강우계
		List<JSONObject> rainGaugeList = new ArrayList<JSONObject>();		
		List<JSONObject> rainGaugePerList = new ArrayList<JSONObject>();
		List<String> rainGaugePhotoList = new ArrayList<String>();		
		List<JSONObject> rainGaugePhotoTagList = new ArrayList<JSONObject>();	
		//구조물변위계
		List<JSONObject> strcDpmList = new ArrayList<JSONObject>();				
		List<JSONObject> strcDpmPerList = new ArrayList<JSONObject>();
		List<String> strcDpmPhotoList = new ArrayList<String>();				
		List<JSONObject> strcDpmPhotoTagList = new ArrayList<JSONObject>();				
		//지표변위계
		List<JSONObject> surDpmList = new ArrayList<JSONObject>();						
		List<JSONObject> surDpmPerList = new ArrayList<JSONObject>();	
		List<String> surDpmPhotoList = new ArrayList<String>();						
		List<JSONObject> surDpmPhotoTagList = new ArrayList<JSONObject>();					
		//GPS변위계
		List<JSONObject> gpsGaugeList = new ArrayList<JSONObject>();						
		List<JSONObject> gpsGaugePerList = new ArrayList<JSONObject>();
		List<String> gpsGaugePhotoList = new ArrayList<String>();						
		List<JSONObject> gpsGaugePhotoTagList = new ArrayList<JSONObject>();					
		//게이트웨이
		List<JSONObject> gatewayList = new ArrayList<JSONObject>();						
		List<JSONObject> gatewayPerList = new ArrayList<JSONObject>();	
		List<String> gatewayPhotoList = new ArrayList<String>();						
		List<JSONObject> gatewayPhotoTagList = new ArrayList<JSONObject>();						
		//노드
		List<JSONObject> nodeList = new ArrayList<JSONObject>();						
		List<JSONObject> nodePerList = new ArrayList<JSONObject>();	
		List<String> nodePhotoList = new ArrayList<String>();						
		List<JSONObject> nodePhotoTagList = new ArrayList<JSONObject>();
		//종합
		List<String> photoList = new ArrayList<String>();						
		List<JSONObject> photoTagList = new ArrayList<JSONObject>();					
		
		JSONArray eqpArr = new JSONArray();
		JSONObject msChl = new JSONObject();
		String msSenser = null;
		String msSenserPer = null;
		
		String[] photo = null;
		for(int i=0;i<mseComptList.size(); i++) {
			String eqpmn_type = mseComptList.get(i).getEqpmntype().toString();

			
			//와이어신축계
			if(eqpmn_type.equals("와이어신축계") && mseComptList.get(i).getWireext() != null) {
				msSenser = "["+mseComptList.get(i).getWireext()+"]";
				msSenserPer = "["+mseComptList.get(i).getWireextper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getWireextchl());
				eqpArr.put(0, msChl);
				wireExtList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getWireextchl());
				eqpArr.put(0, msChl);
				wireExtPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						wireExtPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					wireExtPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) wireExtPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(wireExtPhotoTagList.size() > 0) { 
			        	Collections.sort( wireExtPhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = wireExtPhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(wireExtPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				wireExtPhotoTagList.add(wireExtPhotoTagList.get(j));
			        				wireExtPhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(wireExtPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				wireExtPhotoTagList.add(wireExtPhotoTagList.get(x));
			        				wireExtPhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(wireExtPhotoTagList);
			        }
				}
			}
			//지중경사계
			if(eqpmn_type.equals("지중경사계") && mseComptList.get(i).getLicmeter() != null) {
				msSenser = "["+mseComptList.get(i).getLicmeter()+"]";
				msSenserPer = mseComptList.get(i).getLicmeterper();
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getLicmeterchl());
				eqpArr.put(0, msChl);
				licMeterList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				
				for(Object licObj : eqpArr) licMeterPerList.add((JSONObject) licObj);
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						licMeterPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					licMeterPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) licMeterPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(licMeterPhotoTagList.size() > 0) { 
			        	Collections.sort( licMeterPhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = licMeterPhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(licMeterPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				licMeterPhotoTagList.add(licMeterPhotoTagList.get(j));
			        				licMeterPhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(licMeterPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				licMeterPhotoTagList.add(licMeterPhotoTagList.get(x));
			        				licMeterPhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(licMeterPhotoTagList);
			        }
				}
			}
			//지하수위계
			if(eqpmn_type.equals("지하수위계") && mseComptList.get(i).getGwgauge() != null) {
				msSenser = "["+mseComptList.get(i).getGwgauge()+"]";
				msSenserPer = "["+mseComptList.get(i).getGwgaugeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGwgaugechl());
				eqpArr.put(0, msChl);
				gwGaugeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGwgaugechl());
				eqpArr.put(0, msChl);
				gwGaugePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						gwGaugePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					gwGaugePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) gwGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(gwGaugePhotoTagList.size() > 0) { 
			        	Collections.sort( gwGaugePhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = gwGaugePhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(gwGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				gwGaugePhotoTagList.add(gwGaugePhotoTagList.get(j));
			        				gwGaugePhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(gwGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				gwGaugePhotoTagList.add(gwGaugePhotoTagList.get(x));
			        				gwGaugePhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(gwGaugePhotoTagList);
			        }
				}
			}
			//강우계
			if(eqpmn_type.equals("강우계") && mseComptList.get(i).getRaingauge() != null) {
				msSenser = "["+mseComptList.get(i).getRaingauge()+"]";
				msSenserPer = "["+mseComptList.get(i).getRaingaugeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getRaingaugechl());
				eqpArr.put(0, msChl);
				rainGaugeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getRaingaugechl());
				eqpArr.put(0, msChl);
				rainGaugePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						rainGaugePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					rainGaugePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) rainGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(rainGaugePhotoTagList.size() > 0) { 
			        	Collections.sort( rainGaugePhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = rainGaugePhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(rainGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				rainGaugePhotoTagList.add(rainGaugePhotoTagList.get(j));
			        				rainGaugePhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(rainGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				rainGaugePhotoTagList.add(rainGaugePhotoTagList.get(x));
			        				rainGaugePhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}
			        	photoTagList.addAll(rainGaugePhotoTagList);
			        }
				}
			}
			//구조물변위계
			if(eqpmn_type.equals("구조물변위계") && mseComptList.get(i).getStrcdpm() != null) {
				msSenser = "["+mseComptList.get(i).getStrcdpm()+"]";
				msSenserPer = "["+mseComptList.get(i).getStrcdpmper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getStrcdpmchl());
				eqpArr.put(0, msChl);
				strcDpmList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getStrcdpmchl());
				eqpArr.put(0, msChl);
				strcDpmPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						strcDpmPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					strcDpmPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) strcDpmPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(strcDpmPhotoTagList.size() > 0) { 
			        	Collections.sort( strcDpmPhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = strcDpmPhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(strcDpmPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				strcDpmPhotoTagList.add(strcDpmPhotoTagList.get(j));
			        				strcDpmPhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(strcDpmPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				strcDpmPhotoTagList.add(strcDpmPhotoTagList.get(x));
			        				strcDpmPhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(strcDpmPhotoTagList);
			        }
				}
			}
			//지표변위계
			if(eqpmn_type.equals("지표변위계") && mseComptList.get(i).getSurdpm() != null) {
				msSenser = "["+mseComptList.get(i).getSurdpm()+"]";
				msSenserPer = "["+mseComptList.get(i).getSurdpmper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getSurdpmchl());
				eqpArr.put(0, msChl);
				surDpmList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getSurdpmchl());
				eqpArr.put(0, msChl);
				surDpmPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						surDpmPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					surDpmPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) surDpmPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(surDpmPhotoTagList.size() > 0) { 
			        	Collections.sort( surDpmPhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = surDpmPhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(surDpmPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				surDpmPhotoTagList.add(surDpmPhotoTagList.get(j));
			        				surDpmPhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(surDpmPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				surDpmPhotoTagList.add(surDpmPhotoTagList.get(x));
			        				surDpmPhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(surDpmPhotoTagList);
			        }
				}
			}
			//GPS변위계
			if(eqpmn_type.equals("GPS변위계") && mseComptList.get(i).getGpsgauge() != null) {
				msSenser = "["+mseComptList.get(i).getGpsgauge()+"]";
				msSenserPer = "["+mseComptList.get(i).getGpsgaugeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGpsgaugechl());
				eqpArr.put(0, msChl);
				gpsGaugeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGpsgaugechl());
				eqpArr.put(0, msChl);
				gpsGaugePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						gpsGaugePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					gpsGaugePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) gpsGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(gpsGaugePhotoTagList.size() > 0) { 
			        	Collections.sort( gpsGaugePhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = gpsGaugePhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(gpsGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				gpsGaugePhotoTagList.add(gpsGaugePhotoTagList.get(j));
			        				gpsGaugePhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(gpsGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				gpsGaugePhotoTagList.add(gpsGaugePhotoTagList.get(x));
			        				gpsGaugePhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(gpsGaugePhotoTagList);
			        }
				}
			}
			//게이트웨이
			if(eqpmn_type.equals("게이트웨이") && mseComptList.get(i).getGateway() != null) {
				msSenser = "["+mseComptList.get(i).getGateway()+"]";
				msSenserPer = "["+mseComptList.get(i).getGatewayper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getGatewaychl());
				eqpArr.put(0, msChl);
				gatewayList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getGatewaychl());
				eqpArr.put(0, msChl);
				gatewayPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						gatewayPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					gatewayPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) gatewayPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(gatewayPhotoTagList.size() > 0) { 
			        	Collections.sort( gatewayPhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = gatewayPhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(gatewayPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				gatewayPhotoTagList.add(gatewayPhotoTagList.get(j));
			        				gatewayPhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(gatewayPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				gatewayPhotoTagList.add(gatewayPhotoTagList.get(x));
			        				gatewayPhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(gatewayPhotoTagList);
			        }
				}
			}
			//노드
			if(eqpmn_type.equals("노드") && mseComptList.get(i).getNode() != null) {
				msSenser = "["+mseComptList.get(i).getNode()+"]";
				msSenserPer = "["+mseComptList.get(i).getNodeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getNodechl());
				eqpArr.put(0, msChl);
				nodeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getNodechl());
				eqpArr.put(0, msChl);
				nodePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						nodePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					nodePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) nodePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(nodePhotoTagList.size() > 0) { 
			        	Collections.sort( nodePhotoTagList, new Comparator<JSONObject>() {
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
			        	int photoSize = nodePhotoTagList.size();
			        	for (int x = 0; x < photoSize; x++) {
			        		if(j != -1) {
			        			if(nodePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
			        				nodePhotoTagList.add(nodePhotoTagList.get(j));
			        				nodePhotoTagList.remove(j);
			        			}
			        		}else {
			        			if(nodePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
			        				j = x;
			        				nodePhotoTagList.add(nodePhotoTagList.get(x));
			        				nodePhotoTagList.remove(x);
			        			}	        			
			        		}
			        	}	
			        	photoTagList.addAll(nodePhotoTagList);
			        }
				}
			}
		}
		
		// 공유방 권한 확인
//		HashMap<String, Object> commandMap = new HashMap<>();
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		commandMap.put("id", Integer.parseInt(vo.getMstId()));
//		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
//		String access = fckMseComptService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result", mseComptDetail);
		model.addAttribute("resultList", mseComptList);
		
		model.addAttribute("wireExtList", wireExtList);
		model.addAttribute("wireExtPhotoList", wireExtPhotoList);
		model.addAttribute("wireExtPhotoTagList", wireExtPhotoTagList);
		model.addAttribute("wireExtPerList", wireExtPerList);
		model.addAttribute("licMeterList", licMeterList);
		model.addAttribute("licMeterPhotoList", licMeterPhotoList);
		model.addAttribute("licMeterPhotoTagList", licMeterPhotoTagList);
		model.addAttribute("licMeterPerList", licMeterPerList);
		model.addAttribute("gwGaugeList", gwGaugeList);
		model.addAttribute("gwGaugePhotoList", gwGaugePhotoList);
		model.addAttribute("gwGaugePhotoTagList", gwGaugePhotoTagList);
		model.addAttribute("gwGaugePerList", gwGaugePerList);
		model.addAttribute("rainGaugeList", rainGaugeList);
		model.addAttribute("rainGaugePhotoList", rainGaugePhotoList);
		model.addAttribute("rainGaugePhotoTagList", rainGaugePhotoTagList);
		model.addAttribute("rainGaugePerList", rainGaugePerList);
		model.addAttribute("strcDpmList", strcDpmList);
		model.addAttribute("strcDpmPhotoList", strcDpmPhotoList);
		model.addAttribute("strcDpmPhotoTagList", strcDpmPhotoTagList);
		model.addAttribute("strcDpmPerList", strcDpmPerList);
		model.addAttribute("surDpmList", surDpmList);
		model.addAttribute("surDpmPhotoList", surDpmPhotoList);
		model.addAttribute("surDpmPhotoTagList", surDpmPhotoTagList);
		model.addAttribute("surDpmPerList", surDpmPerList);
		model.addAttribute("gpsGaugeList", gpsGaugeList);
		model.addAttribute("gpsGaugePhotoList", gpsGaugePhotoList);
		model.addAttribute("gpsGaugePhotoTagList", gpsGaugePhotoTagList);
		model.addAttribute("gpsGaugePerList", gpsGaugePerList);
		model.addAttribute("gatewayList", gatewayList);
		model.addAttribute("gatewayPhotoList", gatewayPhotoList);
		model.addAttribute("gatewayPhotoTagList", gatewayPhotoTagList);
		model.addAttribute("gatewayPerList", gatewayPerList);
		model.addAttribute("nodeList", nodeList);
		model.addAttribute("nodePhotoList", nodePhotoList);
		model.addAttribute("nodePhotoTagList", nodePhotoTagList);
		model.addAttribute("nodePerList", nodePerList);
		model.addAttribute("photoList", photoList);
		model.addAttribute("photoTagList", photoTagList);
//		model.addAttribute("access", access);

		return "sys/fck/mse/sct/mseComptDetail";
	}
	
	/**
	 * 조사완료지 수정페이지로 이동한다.
	 * @param comptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sct/updateMseComptView.do")
	public String updateMseComptView(@ModelAttribute("searchVO") FckMseComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		// 계측기 목록 조회
		searchVO.setSvyType("계측장비");
		List<FckMseComptVO> mseComptList = fckMseComptService.selectFckMseComptDetail(searchVO);
		// 계측기 대상지 일반사항 조회
		searchVO.setSvyType("계측장비 일반사항");
		List<FckMseComptVO> mseComptDetail = fckMseComptService.selectFckMseComptDetail(searchVO);
		
		// 계측기 점검 불량 상태
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI174");
		List<?> mseSttus = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("mseSttus", mseSttus);
		
		//와이어신축계
		List<JSONObject> wireExtList = new ArrayList<JSONObject>();
		List<JSONObject> wireExtPerList = new ArrayList<JSONObject>();
		List<String> wireExtPhotoList = new ArrayList<String>();
		List<JSONObject> wireExtPhotoTagList = new ArrayList<JSONObject>();
		//지중경사계
		List<JSONObject> licMeterList = new ArrayList<JSONObject>();		
		List<JSONObject> licMeterPerList = new ArrayList<JSONObject>();		
		List<String> licMeterPhotoList = new ArrayList<String>();		
		List<JSONObject> licMeterPhotoTagList = new ArrayList<JSONObject>();		
		//지하수위계
		List<JSONObject> gwGaugeList = new ArrayList<JSONObject>();		
		List<JSONObject> gwGaugePerList = new ArrayList<JSONObject>();
		List<String> gwGaugePhotoList = new ArrayList<String>();		
		List<JSONObject> gwGaugePhotoTagList = new ArrayList<JSONObject>();		
		//강우계
		List<JSONObject> rainGaugeList = new ArrayList<JSONObject>();		
		List<JSONObject> rainGaugePerList = new ArrayList<JSONObject>();
		List<String> rainGaugePhotoList = new ArrayList<String>();		
		List<JSONObject> rainGaugePhotoTagList = new ArrayList<JSONObject>();	
		//구조물변위계
		List<JSONObject> strcDpmList = new ArrayList<JSONObject>();				
		List<JSONObject> strcDpmPerList = new ArrayList<JSONObject>();
		List<String> strcDpmPhotoList = new ArrayList<String>();				
		List<JSONObject> strcDpmPhotoTagList = new ArrayList<JSONObject>();				
		//지표변위계
		List<JSONObject> surDpmList = new ArrayList<JSONObject>();						
		List<JSONObject> surDpmPerList = new ArrayList<JSONObject>();	
		List<String> surDpmPhotoList = new ArrayList<String>();						
		List<JSONObject> surDpmPhotoTagList = new ArrayList<JSONObject>();					
		//GPS변위계
		List<JSONObject> gpsGaugeList = new ArrayList<JSONObject>();						
		List<JSONObject> gpsGaugePerList = new ArrayList<JSONObject>();
		List<String> gpsGaugePhotoList = new ArrayList<String>();						
		List<JSONObject> gpsGaugePhotoTagList = new ArrayList<JSONObject>();					
		//게이트웨이
		List<JSONObject> gatewayList = new ArrayList<JSONObject>();						
		List<JSONObject> gatewayPerList = new ArrayList<JSONObject>();	
		List<String> gatewayPhotoList = new ArrayList<String>();						
		List<JSONObject> gatewayPhotoTagList = new ArrayList<JSONObject>();						
		//노드
		List<JSONObject> nodeList = new ArrayList<JSONObject>();						
		List<JSONObject> nodePerList = new ArrayList<JSONObject>();	
		List<String> nodePhotoList = new ArrayList<String>();						
		List<JSONObject> nodePhotoTagList = new ArrayList<JSONObject>();
		//종합
		List<String> photoList = new ArrayList<String>();						
		List<JSONObject> photoTagList = new ArrayList<JSONObject>();					
		
		JSONArray eqpArr = new JSONArray();
		JSONObject msChl = new JSONObject();
		String msSenser = null;
		String msSenserPer = null;
		
		String[] photo = null;
		for(int i=0;i<mseComptList.size(); i++) {
			String eqpmn_type = mseComptList.get(i).getEqpmntype().toString();
			
			
			//와이어신축계
			if(eqpmn_type.equals("와이어신축계") && mseComptList.get(i).getWireext() != null) {
				msSenser = "["+mseComptList.get(i).getWireext()+"]";
				msSenserPer = "["+mseComptList.get(i).getWireextper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getWireextchl());
				eqpArr.put(0, msChl);
				wireExtList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getWireextchl());
				eqpArr.put(0, msChl);
				wireExtPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						wireExtPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					wireExtPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) wireExtPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(wireExtPhotoTagList.size() > 0) { 
						Collections.sort( wireExtPhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = wireExtPhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(wireExtPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									wireExtPhotoTagList.add(wireExtPhotoTagList.get(j));
									wireExtPhotoTagList.remove(j);
								}
							}else {
								if(wireExtPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									wireExtPhotoTagList.add(wireExtPhotoTagList.get(x));
									wireExtPhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(wireExtPhotoTagList);
					}
				}
			}
			//지중경사계
			if(eqpmn_type.equals("지중경사계") && mseComptList.get(i).getLicmeter() != null) {
				msSenser = "["+mseComptList.get(i).getLicmeter()+"]";
				msSenserPer = mseComptList.get(i).getLicmeterper();
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getLicmeterchl());
				eqpArr.put(0, msChl);
				licMeterList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				
				for(Object licObj : eqpArr) licMeterPerList.add((JSONObject) licObj);
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						licMeterPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					licMeterPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) licMeterPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(licMeterPhotoTagList.size() > 0) { 
						Collections.sort( licMeterPhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = licMeterPhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(licMeterPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									licMeterPhotoTagList.add(licMeterPhotoTagList.get(j));
									licMeterPhotoTagList.remove(j);
								}
							}else {
								if(licMeterPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									licMeterPhotoTagList.add(licMeterPhotoTagList.get(x));
									licMeterPhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(licMeterPhotoTagList);
					}
				}
			}
			//지하수위계
			if(eqpmn_type.equals("지하수위계") && mseComptList.get(i).getGwgauge() != null) {
				msSenser = "["+mseComptList.get(i).getGwgauge()+"]";
				msSenserPer = "["+mseComptList.get(i).getGwgaugeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGwgaugechl());
				eqpArr.put(0, msChl);
				gwGaugeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGwgaugechl());
				eqpArr.put(0, msChl);
				gwGaugePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						gwGaugePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					gwGaugePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) gwGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(gwGaugePhotoTagList.size() > 0) { 
						Collections.sort( gwGaugePhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = gwGaugePhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(gwGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									gwGaugePhotoTagList.add(gwGaugePhotoTagList.get(j));
									gwGaugePhotoTagList.remove(j);
								}
							}else {
								if(gwGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									gwGaugePhotoTagList.add(gwGaugePhotoTagList.get(x));
									gwGaugePhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(gwGaugePhotoTagList);
					}
				}
			}
			//강우계
			if(eqpmn_type.equals("강우계") && mseComptList.get(i).getRaingauge() != null) {
				msSenser = "["+mseComptList.get(i).getRaingauge()+"]";
				msSenserPer = "["+mseComptList.get(i).getRaingaugeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getRaingaugechl());
				eqpArr.put(0, msChl);
				rainGaugeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getRaingaugechl());
				eqpArr.put(0, msChl);
				rainGaugePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						rainGaugePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					rainGaugePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) rainGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(rainGaugePhotoTagList.size() > 0) { 
						Collections.sort( rainGaugePhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = rainGaugePhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(rainGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									rainGaugePhotoTagList.add(rainGaugePhotoTagList.get(j));
									rainGaugePhotoTagList.remove(j);
								}
							}else {
								if(rainGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									rainGaugePhotoTagList.add(rainGaugePhotoTagList.get(x));
									rainGaugePhotoTagList.remove(x);
								}	        			
							}
						}
						photoTagList.addAll(rainGaugePhotoTagList);
					}
				}
			}
			//구조물변위계
			if(eqpmn_type.equals("구조물변위계") && mseComptList.get(i).getStrcdpm() != null) {
				msSenser = "["+mseComptList.get(i).getStrcdpm()+"]";
				msSenserPer = "["+mseComptList.get(i).getStrcdpmper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getStrcdpmchl());
				eqpArr.put(0, msChl);
				strcDpmList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getStrcdpmchl());
				eqpArr.put(0, msChl);
				strcDpmPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						strcDpmPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					strcDpmPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) strcDpmPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(strcDpmPhotoTagList.size() > 0) { 
						Collections.sort( strcDpmPhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = strcDpmPhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(strcDpmPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									strcDpmPhotoTagList.add(strcDpmPhotoTagList.get(j));
									strcDpmPhotoTagList.remove(j);
								}
							}else {
								if(strcDpmPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									strcDpmPhotoTagList.add(strcDpmPhotoTagList.get(x));
									strcDpmPhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(strcDpmPhotoTagList);
					}
				}
			}
			//지표변위계
			if(eqpmn_type.equals("지표변위계") && mseComptList.get(i).getSurdpm() != null) {
				msSenser = "["+mseComptList.get(i).getSurdpm()+"]";
				msSenserPer = "["+mseComptList.get(i).getSurdpmper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getSurdpmchl());
				eqpArr.put(0, msChl);
				surDpmList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getSurdpmchl());
				eqpArr.put(0, msChl);
				surDpmPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						surDpmPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					surDpmPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) surDpmPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(surDpmPhotoTagList.size() > 0) { 
						Collections.sort( surDpmPhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = surDpmPhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(surDpmPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									surDpmPhotoTagList.add(surDpmPhotoTagList.get(j));
									surDpmPhotoTagList.remove(j);
								}
							}else {
								if(surDpmPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									surDpmPhotoTagList.add(surDpmPhotoTagList.get(x));
									surDpmPhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(surDpmPhotoTagList);
					}
				}
			}
			//GPS변위계
			if(eqpmn_type.equals("GPS변위계") && mseComptList.get(i).getGpsgauge() != null) {
				msSenser = "["+mseComptList.get(i).getGpsgauge()+"]";
				msSenserPer = "["+mseComptList.get(i).getGpsgaugeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGpsgaugechl());
				eqpArr.put(0, msChl);
				gpsGaugeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("채널명", mseComptList.get(i).getGpsgaugechl());
				eqpArr.put(0, msChl);
				gpsGaugePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						gpsGaugePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					gpsGaugePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) gpsGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(gpsGaugePhotoTagList.size() > 0) { 
						Collections.sort( gpsGaugePhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = gpsGaugePhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(gpsGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									gpsGaugePhotoTagList.add(gpsGaugePhotoTagList.get(j));
									gpsGaugePhotoTagList.remove(j);
								}
							}else {
								if(gpsGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									gpsGaugePhotoTagList.add(gpsGaugePhotoTagList.get(x));
									gpsGaugePhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(gpsGaugePhotoTagList);
					}
				}
			}
			//게이트웨이
			if(eqpmn_type.equals("게이트웨이") && mseComptList.get(i).getGateway() != null) {
				msSenser = "["+mseComptList.get(i).getGateway()+"]";
				msSenserPer = "["+mseComptList.get(i).getGatewayper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getGatewaychl());
				eqpArr.put(0, msChl);
				gatewayList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getGatewaychl());
				eqpArr.put(0, msChl);
				gatewayPerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						gatewayPhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					gatewayPhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) gatewayPhotoTagList.add((JSONObject) eqpPhotoTag);
					if(gatewayPhotoTagList.size() > 0) { 
						Collections.sort( gatewayPhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = gatewayPhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(gatewayPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									gatewayPhotoTagList.add(gatewayPhotoTagList.get(j));
									gatewayPhotoTagList.remove(j);
								}
							}else {
								if(gatewayPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									gatewayPhotoTagList.add(gatewayPhotoTagList.get(x));
									gatewayPhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(gatewayPhotoTagList);
					}
				}
			}
			//노드
			if(eqpmn_type.equals("노드") && mseComptList.get(i).getNode() != null) {
				msSenser = "["+mseComptList.get(i).getNode()+"]";
				msSenserPer = "["+mseComptList.get(i).getNodeper()+"]";
				eqpArr = new JSONArray(new JSONTokener(msSenser));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getNodechl());
				eqpArr.put(0, msChl);
				nodeList.add(eqpArr.getJSONObject(0));
				eqpArr = new JSONArray(new JSONTokener(msSenserPer));
				msChl = eqpArr.getJSONObject(0);
				msChl.put("연번", mseComptList.get(i).getNodechl());
				eqpArr.put(0, msChl);
				nodePerList.add(eqpArr.getJSONObject(0));
				
				//현장사진
				photo = mseComptList.get(i).getPhoto().toString().split(",");
				if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
					for(String msImg : photo) {
						msImg = msImg.trim();
						nodePhotoList.add(msImg);
						photoList.add(msImg);
					}
				}
				
				eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
				
				if(eqpArr.length() > 0) {
					nodePhotoTagList = new ArrayList<JSONObject>();
					for(Object eqpPhotoTag : eqpArr) nodePhotoTagList.add((JSONObject) eqpPhotoTag);
					if(nodePhotoTagList.size() > 0) { 
						Collections.sort( nodePhotoTagList, new Comparator<JSONObject>() {
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
						int photoSize = nodePhotoTagList.size();
						for (int x = 0; x < photoSize; x++) {
							if(j != -1) {
								if(nodePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
									nodePhotoTagList.add(nodePhotoTagList.get(j));
									nodePhotoTagList.remove(j);
								}
							}else {
								if(nodePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
									j = x;
									nodePhotoTagList.add(nodePhotoTagList.get(x));
									nodePhotoTagList.remove(x);
								}	        			
							}
						}	
						photoTagList.addAll(nodePhotoTagList);
					}
				}
			}
		}
		
		// 공유방 권한 확인
//		HashMap<String, Object> commandMap = new HashMap<>();
//		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//		commandMap.put("id", Integer.parseInt(vo.getMstId()));
//		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
//		String access = fckMseComptService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result", mseComptDetail);
		model.addAttribute("resultList", mseComptList);
		
		model.addAttribute("wireExtList", wireExtList);
		model.addAttribute("wireExtPhotoList", wireExtPhotoList);
		model.addAttribute("wireExtPhotoTagList", wireExtPhotoTagList);
		model.addAttribute("wireExtPerList", wireExtPerList);
		model.addAttribute("licMeterList", licMeterList);
		model.addAttribute("licMeterPhotoList", licMeterPhotoList);
		model.addAttribute("licMeterPhotoTagList", licMeterPhotoTagList);
		model.addAttribute("licMeterPerList", licMeterPerList);
		model.addAttribute("gwGaugeList", gwGaugeList);
		model.addAttribute("gwGaugePhotoList", gwGaugePhotoList);
		model.addAttribute("gwGaugePhotoTagList", gwGaugePhotoTagList);
		model.addAttribute("gwGaugePerList", gwGaugePerList);
		model.addAttribute("rainGaugeList", rainGaugeList);
		model.addAttribute("rainGaugePhotoList", rainGaugePhotoList);
		model.addAttribute("rainGaugePhotoTagList", rainGaugePhotoTagList);
		model.addAttribute("rainGaugePerList", rainGaugePerList);
		model.addAttribute("strcDpmList", strcDpmList);
		model.addAttribute("strcDpmPhotoList", strcDpmPhotoList);
		model.addAttribute("strcDpmPhotoTagList", strcDpmPhotoTagList);
		model.addAttribute("strcDpmPerList", strcDpmPerList);
		model.addAttribute("surDpmList", surDpmList);
		model.addAttribute("surDpmPhotoList", surDpmPhotoList);
		model.addAttribute("surDpmPhotoTagList", surDpmPhotoTagList);
		model.addAttribute("surDpmPerList", surDpmPerList);
		model.addAttribute("gpsGaugeList", gpsGaugeList);
		model.addAttribute("gpsGaugePhotoList", gpsGaugePhotoList);
		model.addAttribute("gpsGaugePhotoTagList", gpsGaugePhotoTagList);
		model.addAttribute("gpsGaugePerList", gpsGaugePerList);
		model.addAttribute("gatewayList", gatewayList);
		model.addAttribute("gatewayPhotoList", gatewayPhotoList);
		model.addAttribute("gatewayPhotoTagList", gatewayPhotoTagList);
		model.addAttribute("gatewayPerList", gatewayPerList);
		model.addAttribute("nodeList", nodeList);
		model.addAttribute("nodePhotoList", nodePhotoList);
		model.addAttribute("nodePhotoTagList", nodePhotoTagList);
		model.addAttribute("nodePerList", nodePerList);
		model.addAttribute("photoList", photoList);
		model.addAttribute("photoTagList", photoTagList);
//		model.addAttribute("access", access);
		
		return "sys/fck/mse/sct/mseComptUpdt";
	}
	
	/**
	 * 조사완료지를 수정한다.
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/fck/mse/sct/updateMseCompt.do")
    public ModelAndView updateMseCompt(@ModelAttribute("fckMseComptVO") FckMseComptVO svyComptVO, BindingResult bindingResult,HttpServletRequest req, Model model, MultipartHttpServletRequest multiRequest) throws Exception {
		beanValidator.validate(svyComptVO, bindingResult);
		ModelAndView mv = new ModelAndView("jsonView");
		
		//와이어신축계
		String wireextIdList[] = req.getParameterValues("wireextchl");
		svyComptVO.setWireextIdList(wireextIdList);
		if(!svyComptVO.getWireext().isEmpty()) svyComptVO.setWireext(svyComptVO.getWireext().replaceAll("&quot;", "\""));
		
		//지중경사계
		String licmeterIdList[] = req.getParameterValues("licmeterchl");
		svyComptVO.setLicmeterIdList(licmeterIdList);
		if(!svyComptVO.getLicmeter().isEmpty()) svyComptVO.setLicmeter(svyComptVO.getLicmeter().replaceAll("&quot;", "\""));
		
		//지하수위계
		String gwgaugeIdList[] = req.getParameterValues("gwgaugechl");
		svyComptVO.setGwgaugeIdList(gwgaugeIdList);
		if(!svyComptVO.getGwgauge().isEmpty()) svyComptVO.setGwgauge(svyComptVO.getGwgauge().replaceAll("&quot;", "\""));
		
		//강우계
		String raingaugeIdList[] = req.getParameterValues("raingaugechl");
		svyComptVO.setRaingaugeIdList(raingaugeIdList);
		if(!svyComptVO.getRaingauge().isEmpty()) svyComptVO.setRaingauge(svyComptVO.getRaingauge().replaceAll("&quot;", "\""));
		
		//구조물변위계
		String strcdpmIdList[] = req.getParameterValues("strcdpmchl");
		svyComptVO.setStrcdpmIdList(strcdpmIdList);
		if(!svyComptVO.getStrcdpm().isEmpty()) svyComptVO.setStrcdpm(svyComptVO.getStrcdpm().replaceAll("&quot;", "\""));
		
		//지표변위계
		String surdpmIdList[] = req.getParameterValues("surdpmchl");
		svyComptVO.setSurdpmIdList(surdpmIdList);
		if(!svyComptVO.getSurdpm().isEmpty()) svyComptVO.setSurdpm(svyComptVO.getSurdpm().replaceAll("&quot;", "\""));
		
		//GPS변위계
		String gpsgaugeIdList[] = req.getParameterValues("gpsgaugechl");
		svyComptVO.setGpsgaugeIdList(gpsgaugeIdList);
		if(!svyComptVO.getGpsgauge().isEmpty()) svyComptVO.setGpsgauge(svyComptVO.getGpsgauge().replaceAll("&quot;", "\""));
		
		//게이트웨이
		String gatewayIdList[] = req.getParameterValues("gatewaychl");
		svyComptVO.setGatewayIdList(gatewayIdList);
		if(!svyComptVO.getGateway().isEmpty()) svyComptVO.setGateway(svyComptVO.getGateway().replaceAll("&quot;", "\""));
		
		//노드
		String nodeIdList[] = req.getParameterValues("nodechl");
		svyComptVO.setNodeIdList(nodeIdList);
		if(!svyComptVO.getNode().isEmpty()) svyComptVO.setNode(svyComptVO.getNode().replaceAll("&quot;", "\""));
		//현장사진
		if(!svyComptVO.getPhotoTagList().isEmpty()) svyComptVO.setPhotoTagList(svyComptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		
		
		
		try {
			fckMseComptService.updateFckMseCompt(svyComptVO);		
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
	 * 조사 완료지 엑셀 다운로드
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/sys/fck/mse/sct/selectMseComptListExcel.do")
	public ModelAndView selectMseComptListExcel(FckMseComptVO svyComptVO) throws Exception {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("mseExcelView");
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HashMap<?, ?> _map = (HashMap<?, ?>)fckMseComptService.selectMseSvyComptListExcel(svyComptVO);
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		
		String fileNm = "계측장비조사완료지_".concat(formater.format(new Date()).toString());
//		
		dataMap.put("sheetNm", fileNm);
		dataMap.put("list", _map.get("resultList"));
		dataMap.put("wireextList", _map.get("wireextList"));
		dataMap.put("licmeterList", _map.get("licmeterList"));
		dataMap.put("gwgaugeList", _map.get("gwgaugeList"));
		dataMap.put("raingaugeList", _map.get("raingaugeList"));
		dataMap.put("strcdpmList", _map.get("strcdpmList"));
		dataMap.put("surdpmList", _map.get("surdpmList"));
		dataMap.put("gpsgaugeList", _map.get("gpsgaugeList"));
		dataMap.put("gatewayList", _map.get("gatewayList"));
		dataMap.put("nodeList", _map.get("nodeList"));
		dataMap.put("photoTagList", _map.get("photoTagList"));
//		dataMap.put("legaldongcdList", _map.get("legaldongcdList"));
//		dataMap.put("mnagncdList", _map.get("mnagncdList"));
		
		modelAndView.addObject("dataMap",dataMap);
		modelAndView.addObject("filename",fileNm);
		
		return modelAndView;
	}
	
	/**
	 * 조사완료지를 삭제한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sct/deleteMseCompt.do")
	public ModelAndView deleteMseCompt(FckMseComptVO svyComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			fckMseComptService.deleteMseCompt(svyComptVO);
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
	 * 대상지 현장사진을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/mse/sct/selectMseDownloadSldListPhoto.do")
	public void selectMseDownloadSldListPhoto(FckMseComptVO svyComptVO,HttpServletRequest req,HttpServletResponse res) throws Exception{
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		String dt_str = formater.format(new Date()).toString();//현재시간 문자열 생성 
		String timestamp_str = EgovStringUtil.getTimeStamp();//파일 저장 폴더가 동일할 경우 여러명이 기능을 사용할때 삭제가 될 우려가 발생하여 타임스템프 폴더를 생성
		
//		if(EgovFileTool.getExistDirectory(comptFileDir.concat(timestamp_str))) {
//			EgovFileTool.deleteDirectory(comptFileDir.concat(timestamp_str));
//		}
		
		String zipNm = "계측장비현장사진_"+dt_str+".zip"; //zip 압축 파일명
		
		// 계측기 대상지 조회
		svyComptVO.setSvyType("계측장비");
		List<FckMseComptVO> mseComptList = fckMseComptService.selectFckMseComptDetail(svyComptVO);

		//와이어신축계
		List<String> wireExtPhotoList = new ArrayList<String>();
		List<JSONObject> wireExtPhotoTagList = new ArrayList<JSONObject>();
		//지중경사계				
		List<String> licMeterPhotoList = new ArrayList<String>();		
		List<JSONObject> licMeterPhotoTagList = new ArrayList<JSONObject>();		
		//지하수위계		
		List<String> gwGaugePhotoList = new ArrayList<String>();		
		List<JSONObject> gwGaugePhotoTagList = new ArrayList<JSONObject>();		
		//강우계		
		List<String> rainGaugePhotoList = new ArrayList<String>();		
		List<JSONObject> rainGaugePhotoTagList = new ArrayList<JSONObject>();	
		//구조물변위계				
		List<String> strcDpmPhotoList = new ArrayList<String>();				
		List<JSONObject> strcDpmPhotoTagList = new ArrayList<JSONObject>();				
		//지표변위계							
		List<String> surDpmPhotoList = new ArrayList<String>();						
		List<JSONObject> surDpmPhotoTagList = new ArrayList<JSONObject>();					
		//GPS변위계			
		List<String> gpsGaugePhotoList = new ArrayList<String>();						
		List<JSONObject> gpsGaugePhotoTagList = new ArrayList<JSONObject>();					
		//게이트웨이				
		List<String> gatewayPhotoList = new ArrayList<String>();						
		List<JSONObject> gatewayPhotoTagList = new ArrayList<JSONObject>();						
		//노드					
		List<String> nodePhotoList = new ArrayList<String>();						
		List<JSONObject> nodePhotoTagList = new ArrayList<JSONObject>();
		//종합
		List<String> photoList = new ArrayList<String>();						
		List<JSONObject> photoTagList = new ArrayList<JSONObject>();					

		JSONArray eqpArr = new JSONArray();
		String[] photo = null;
		for(int i=0;i<mseComptList.size(); i++) {
		    String eqpmn_type = mseComptList.get(i).getEqpmntype().toString();

		    //와이어신축계
		    if(eqpmn_type.equals("와이어신축계") && mseComptList.get(i).getWireext() != null) {
		    	//현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                wireExtPhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            wireExtPhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) wireExtPhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(wireExtPhotoTagList.size() > 0) { 
		                Collections.sort( wireExtPhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = wireExtPhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(wireExtPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            wireExtPhotoTagList.add(wireExtPhotoTagList.get(j));
		                            wireExtPhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(wireExtPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            wireExtPhotoTagList.add(wireExtPhotoTagList.get(x));
		                            wireExtPhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(wireExtPhotoTagList);
		            }
		        }
		    }
		    //지중경사계
		    if(eqpmn_type.equals("지중경사계") && mseComptList.get(i).getLicmeter() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                licMeterPhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            licMeterPhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) licMeterPhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(licMeterPhotoTagList.size() > 0) { 
		                Collections.sort( licMeterPhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = licMeterPhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(licMeterPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            licMeterPhotoTagList.add(licMeterPhotoTagList.get(j));
		                            licMeterPhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(licMeterPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            licMeterPhotoTagList.add(licMeterPhotoTagList.get(x));
		                            licMeterPhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(licMeterPhotoTagList);
		            }
		        }
		    }
		    //지하수위계
		    if(eqpmn_type.equals("지하수위계") && mseComptList.get(i).getGwgauge() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                gwGaugePhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            gwGaugePhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) gwGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(gwGaugePhotoTagList.size() > 0) { 
		                Collections.sort( gwGaugePhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = gwGaugePhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(gwGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            gwGaugePhotoTagList.add(gwGaugePhotoTagList.get(j));
		                            gwGaugePhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(gwGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            gwGaugePhotoTagList.add(gwGaugePhotoTagList.get(x));
		                            gwGaugePhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(gwGaugePhotoTagList);
		            }
		        }
		    }
		    //강우계
		    if(eqpmn_type.equals("강우계") && mseComptList.get(i).getRaingauge() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                rainGaugePhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            rainGaugePhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) rainGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(rainGaugePhotoTagList.size() > 0) { 
		                Collections.sort( rainGaugePhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = rainGaugePhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(rainGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            rainGaugePhotoTagList.add(rainGaugePhotoTagList.get(j));
		                            rainGaugePhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(rainGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            rainGaugePhotoTagList.add(rainGaugePhotoTagList.get(x));
		                            rainGaugePhotoTagList.remove(x);
		                        }	        			
		                    }
		                }
		                photoTagList.addAll(rainGaugePhotoTagList);
		            }
		        }
		    }
		    //구조물변위계
		    if(eqpmn_type.equals("구조물변위계") && mseComptList.get(i).getStrcdpm() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                strcDpmPhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            strcDpmPhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) strcDpmPhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(strcDpmPhotoTagList.size() > 0) { 
		                Collections.sort( strcDpmPhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = strcDpmPhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(strcDpmPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            strcDpmPhotoTagList.add(strcDpmPhotoTagList.get(j));
		                            strcDpmPhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(strcDpmPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            strcDpmPhotoTagList.add(strcDpmPhotoTagList.get(x));
		                            strcDpmPhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(strcDpmPhotoTagList);
		            }
		        }
		    }
		    //지표변위계
		    if(eqpmn_type.equals("지표변위계") && mseComptList.get(i).getSurdpm() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                surDpmPhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            surDpmPhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) surDpmPhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(surDpmPhotoTagList.size() > 0) { 
		                Collections.sort( surDpmPhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = surDpmPhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(surDpmPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            surDpmPhotoTagList.add(surDpmPhotoTagList.get(j));
		                            surDpmPhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(surDpmPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            surDpmPhotoTagList.add(surDpmPhotoTagList.get(x));
		                            surDpmPhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(surDpmPhotoTagList);
		            }
		        }
		    }
		    //GPS변위계
		    if(eqpmn_type.equals("GPS변위계") && mseComptList.get(i).getGpsgauge() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                gpsGaugePhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            gpsGaugePhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) gpsGaugePhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(gpsGaugePhotoTagList.size() > 0) { 
		                Collections.sort( gpsGaugePhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = gpsGaugePhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(gpsGaugePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            gpsGaugePhotoTagList.add(gpsGaugePhotoTagList.get(j));
		                            gpsGaugePhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(gpsGaugePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            gpsGaugePhotoTagList.add(gpsGaugePhotoTagList.get(x));
		                            gpsGaugePhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(gpsGaugePhotoTagList);
		            }
		        }
		    }
		    //게이트웨이
		    if(eqpmn_type.equals("게이트웨이") && mseComptList.get(i).getGateway() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                gatewayPhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            gatewayPhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) gatewayPhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(gatewayPhotoTagList.size() > 0) { 
		                Collections.sort( gatewayPhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = gatewayPhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(gatewayPhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            gatewayPhotoTagList.add(gatewayPhotoTagList.get(j));
		                            gatewayPhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(gatewayPhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            gatewayPhotoTagList.add(gatewayPhotoTagList.get(x));
		                            gatewayPhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(gatewayPhotoTagList);
		            }
		        }
		    }
		    //노드
		    if(eqpmn_type.equals("노드") && mseComptList.get(i).getNode() != null) {
		        //현장사진
		    	String svyId = mseComptList.get(i).getSvyId().toString();
		        photo = mseComptList.get(i).getPhoto().toString().split(",");
		        if(photo.length > 0 && mseComptList.get(i).getPhoto().length() > 0) {
		            for(String msImg : photo) {
		            	msImg = msImg.trim();
		                nodePhotoList.add(msImg);
		                photoList.add(msImg);
		            }
		        }
		        
		        eqpArr = new JSONArray(new JSONTokener(mseComptList.get(i).getPhotoTag()));
		        
		        if(eqpArr.length() > 0) {
		            nodePhotoTagList = new ArrayList<JSONObject>();
		            for(Object eqpPhotoTag : eqpArr) nodePhotoTagList.add((JSONObject) eqpPhotoTag);
		            if(nodePhotoTagList.size() > 0) { 
		                Collections.sort( nodePhotoTagList, new Comparator<JSONObject>() {
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
		                int photoSize = nodePhotoTagList.size();
		                for (int x = 0; x < photoSize; x++) {
		                    if(j != -1) {
		                        if(nodePhotoTagList.get(j).get("TAG").toString().isEmpty()) {
		                            nodePhotoTagList.add(nodePhotoTagList.get(j));
		                            nodePhotoTagList.remove(j);
		                        }
		                    }else {
		                        if(nodePhotoTagList.get(x).get("TAG").toString().isEmpty()) {
		                            j = x;
		                            nodePhotoTagList.add(nodePhotoTagList.get(x));
		                            nodePhotoTagList.remove(x);
		                        }	        			
		                    }
		                }	
		                photoTagList.addAll(nodePhotoTagList);
		            }
		        }
		    }
		}
		
		// 와이어 신축계 디렉토리 생성
		if(wireExtPhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/와이어신축계/1.현장사진"));
		    
		    for(int j=0;j < wireExtPhotoTagList.size();j++ ) {
		        JSONObject wireArr = new JSONObject(wireExtPhotoTagList.get(j).toString());
		        String msImg = wireArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(wireExtPhotoList.indexOf(msImg) != -1) wireExtPhotoList.remove(wireExtPhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }						
		        
		    }
		}
		if(wireExtPhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/와이어신축계/2.기타사진"));
		    
		    for(int j=0; j< wireExtPhotoList.size();j++) {
		        if(wireExtPhotoList.get(j).toString().length() > 0) {
		            String svyId =wireExtPhotoList.get(j).substring(wireExtPhotoList.get(j).lastIndexOf("/")+1,wireExtPhotoList.get(j).length()-9);

		            int photoCnt = wireExtPhotoTagList.size()+(j+1); 
		            String fileNm = wireExtPhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }				
		}

		// 지중경사계 디렉토리 생성
		if(licMeterPhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/지중경사계/1.현장사진"));
		    
		    for(int j=0;j < licMeterPhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(licMeterPhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(licMeterPhotoList.indexOf(msImg) != -1) licMeterPhotoList.remove(licMeterPhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }						
		        
		    }
		}
		if(licMeterPhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/지중경사계/2.기타사진"));
		    
		    for(int j=0; j< licMeterPhotoList.size();j++) {
		        if(licMeterPhotoList.get(j).toString().length() > 0) {
		            String svyId = licMeterPhotoList.get(j).substring(licMeterPhotoList.get(j).lastIndexOf("/")+1,licMeterPhotoList.get(j).length()-9);
		            int photoCnt = licMeterPhotoTagList.size()+(j+1); 
		            String fileNm = licMeterPhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }				
		}

		// 지하수위계 디렉토리 생성
		if(gwGaugePhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/지하수위계/1.현장사진"));
		    
		    for(int j=0;j < gwGaugePhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(gwGaugePhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(gwGaugePhotoList.indexOf(msImg) != -1) gwGaugePhotoList.remove(gwGaugePhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(gwGaugePhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/지하수위계/2.기타사진"));
		    
		    for(int j=0; j< gwGaugePhotoList.size();j++) {
		        if(gwGaugePhotoList.get(j).toString().length() > 0) {
		            String svyId =gwGaugePhotoList.get(j).substring(gwGaugePhotoList.get(j).lastIndexOf("/")+1,gwGaugePhotoList.get(j).length()-9);
		            int photoCnt = gwGaugePhotoTagList.size()+(j+1); 
		            String fileNm = gwGaugePhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}

		// 강우계 디렉토리 생성
		if(rainGaugePhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/강우계/1.현장사진"));
		    
		    for(int j=0;j < rainGaugePhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(rainGaugePhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(rainGaugePhotoList.indexOf(msImg) != -1) rainGaugePhotoList.remove(rainGaugePhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(rainGaugePhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/강우계/2.기타사진"));
		    
		    for(int j=0; j< rainGaugePhotoList.size();j++) {
		        if(rainGaugePhotoList.get(j).toString().length() > 0) {
		            String svyId = rainGaugePhotoList.get(j).substring(rainGaugePhotoList.get(j).lastIndexOf("/")+1,rainGaugePhotoList.get(j).length()-9);
		            int photoCnt = rainGaugePhotoTagList.size()+(j+1); 
		            String fileNm = rainGaugePhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}

		// 구조물변위계 디렉토리 생성
		if(strcDpmPhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/구조물변위계/1.현장사진"));
		    
		    for(int j=0;j < strcDpmPhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(strcDpmPhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(strcDpmPhotoList.indexOf(msImg) != -1) strcDpmPhotoList.remove(strcDpmPhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(strcDpmPhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/구조물변위계/2.기타사진"));
		    
		    for(int j=0; j< strcDpmPhotoList.size();j++) {
		        if(strcDpmPhotoList.get(j).toString().length() > 0) {
		            String svyId =strcDpmPhotoList.get(j).substring(strcDpmPhotoList.get(j).lastIndexOf("/")+1,strcDpmPhotoList.get(j).length()-9);
		            int photoCnt = strcDpmPhotoTagList.size()+(j+1); 
		            String fileNm = strcDpmPhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}

		// 지표변위계 디렉토리 생성
		if(surDpmPhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/지표변위계/1.현장사진"));
		    
		    for(int j=0;j < surDpmPhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(surDpmPhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(surDpmPhotoList.indexOf(msImg) != -1) surDpmPhotoList.remove(surDpmPhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(surDpmPhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/지표변위계/2.기타사진"));
		    
		    for(int j=0; j< surDpmPhotoList.size();j++) {
		        if(surDpmPhotoList.get(j).toString().length() > 0) {
		            String svyId =  surDpmPhotoList.get(j).substring(surDpmPhotoList.get(j).lastIndexOf("/")+1,surDpmPhotoList.get(j).length()-9);
		            int photoCnt = surDpmPhotoTagList.size()+(j+1); 
		            String fileNm = surDpmPhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}

		// GPS변위계 디렉토리 생성
		if(gpsGaugePhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/GPS변위계/1.현장사진"));
		    
		    for(int j=0;j < gpsGaugePhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(gpsGaugePhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(gpsGaugePhotoList.indexOf(msImg) != -1) gpsGaugePhotoList.remove(gpsGaugePhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(gpsGaugePhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/GPS변위계/2.기타사진"));
		    
		    for(int j=0; j< gpsGaugePhotoList.size();j++) {
		        if(gpsGaugePhotoList.get(j).toString().length() > 0) {
		            String svyId = gpsGaugePhotoList.get(j).substring(gpsGaugePhotoList.get(j).lastIndexOf("/")+1,gpsGaugePhotoList.get(j).length()-9);
		            int photoCnt = gpsGaugePhotoTagList.size()+(j+1); 
		            String fileNm = gpsGaugePhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}

		// 게이트웨이 디렉토리 생성
		if(gatewayPhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/게이트웨이/1.현장사진"));
		    
		    for(int j=0;j < gatewayPhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(gatewayPhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(gatewayPhotoList.indexOf(msImg) != -1) gatewayPhotoList.remove(gatewayPhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(gatewayPhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/게이트웨이/2.기타사진"));
		    
		    for(int j=0; j< gatewayPhotoList.size();j++) {
		        if(gatewayPhotoList.get(j).toString().length() > 0) {
		            String svyId =gatewayPhotoList.get(j).substring(gatewayPhotoList.get(j).lastIndexOf("/")+1,gatewayPhotoList.get(j).length()-9);
		            int photoCnt = gatewayPhotoTagList.size()+(j+1); 
		            String fileNm = gatewayPhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}

		// 노드 디렉토리 생성
		if(nodePhotoTagList.size() > 0) {			
		    File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/노드/1.현장사진"));
		    
		    for(int j=0;j < nodePhotoTagList.size();j++ ) {
		        JSONObject msArr = new JSONObject(nodePhotoTagList.get(j).toString());
		        String msImg = msArr.getString("FILENAME");
		        String svyId = msImg.substring(msImg.lastIndexOf("/")+1,msImg.length()-9);
		        String fileNm = msImg.substring(msImg.indexOf("/")+1);
		        String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm);
		        String orginlPhoto = fileStoreDir+fileNm; 
		        File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
		        
		        if(EgovFileUtil.isExistsFile(orginlPhoto)) {
		            if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
		            if(nodePhotoList.indexOf(msImg) != -1) nodePhotoList.remove(nodePhotoList.indexOf(msImg));
		            EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
		            photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
		        }
		    }
		}
		if(nodePhotoList.size() > 0) {
		    File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+svyComptVO.getSldid()+"/노드/2.기타사진"));
		    
		    for(int j=0; j< nodePhotoList.size();j++) {
		        if(nodePhotoList.get(j).toString().length() > 0) {
		            String svyId = nodePhotoList.get(j).substring(nodePhotoList.get(j).lastIndexOf("/")+1,nodePhotoList.get(j).length()-9);
		            int photoCnt = nodePhotoTagList.size()+(j+1); 
		            String fileNm = nodePhotoList.get(j).toString().substring(1); //원본파일명
		            String newFileNm = svyId.concat("-"+photoCnt)+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
		            String orginlPhoto = fileStoreDir+fileNm; 
		            File photoTempFileNm = new File(photoTempFile+File.separator+svyId);
		            
		            if(EgovFileUtil.isExistsFile(orginlPhoto)) {			
		                if(!photoTempFile.exists()) photoTempFile.mkdirs();			
		                EgovFileUtil.cp(orginlPhoto, photoTempFile+File.separator+svyId);//원본이미지 복사
		                photoTempFileNm.renameTo(new File(photoTempFile+File.separator+newFileNm));						
		            }
		        }
		    }
		}
		
		//폴더 압축
		String source = comptFileDir.concat(timestamp_str).concat("/temp");
		String target = comptFileDir.concat(timestamp_str).concat("/계측장비현장사진_"+dt_str+".zip");
		boolean isCompressed = EgovFileCmprs.cmprsFile(source, target);
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ServletOutputStream so = null;
		BufferedOutputStream bos = null;
		
		so = res.getOutputStream();
		bos = new BufferedOutputStream(so);
		
		if(isCompressed) {
			try {
				byte[] buffer = new byte[1024];
				
				String userAgent = req.getHeader("User-Agent");
				String contentDisposition = EgovBrowserUtil.getDisposition(zipNm,userAgent,"UTF-8");
				
				res.setContentType("application/zip; UTF-8");
//						    res.addHeader("Content-Disposition", "attachment; filename="+contentDisposition);
			    res.setHeader("Content-Disposition", contentDisposition);
				fis = new FileInputStream(target);
				bis = new BufferedInputStream(fis);
				

				int n = 0;
				while((n = bis.read(buffer)) > 0){ 
					bos.write(buffer, 0, n); 
					bos.flush(); 
				}
				
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
				throw new Exception("압축파일 생성이 실패하였습니다.");
			}
			finally{
				if(bos != null) bos.close(); 
				if(bis != null) bis.close(); 
				if(so != null) so.close(); 
				if(fis != null) fis.close();
				EgovFileUtil.rm(target);
				EgovFileTool.deleteDirectory(comptFileDir.concat(timestamp_str));
				LOGGER.info("폴더삭제 : "+comptFileDir.concat(timestamp_str));
			}
		}else {
			EgovFileUtil.rm(target);
			if(EgovFileTool.getExistDirectory(comptFileDir.concat(timestamp_str))) {
				EgovFileTool.deleteDirectory(comptFileDir.concat(timestamp_str));
				LOGGER.info("폴더 삭제 : "+comptFileDir.concat(timestamp_str));
			}
			bos.write(0);
			bos.flush();
			bos.close();
			throw new Exception("압축파일 생성이 실패하였습니다.");
		}
		
	}
}


	