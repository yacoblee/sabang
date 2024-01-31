package or.sabang.sys.fck.pcs.web;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
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
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
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
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptService;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.vyt.ecb.service.VytEcbAnalVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;
import or.sabang.utl.DmsFormalization;

@Controller
public class FckPcsComptController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
 
	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "fckPcsComptService") 	
	private FckPcsComptService fckPcsComptService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
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
    
	private static final Logger LOGGER = LoggerFactory.getLogger(FckPcsComptController.class);
	
	/**
	 * 조사완료목록을 조회한다.
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/selectPcsSvyComptList.do")	
    public String selectFckAprComptList (@ModelAttribute("searchVO") FckPcsComptVO searchVO,ModelMap model, HttpServletRequest request) throws Exception {
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
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		AdministZoneVO adminVO = new AdministZoneVO();
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(fckPcsComptService.selectPcsSvyComptMaxYear());
		}
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(fckPcsComptService.selectPcsSvyComptMaxMonth());
		}
		//연도코드 조회
		List<?> year_result = fckPcsComptService.selectPcsSvyComptYear();
		model.addAttribute("yearCodeList", year_result);
		
		//월코드 조회
		vo.setCodeId("FEI045");
		vo.setCodeDc("Cldr");
		List<?> month_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("monthCodeList", month_result);
		vo.setCodeDc("");
		
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
		
		// 조사유형
		vo.setCodeId("FEI173");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//최종점검결과 조회
		vo.setCodeId("FEI039");
		List<?> fckRslt_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("fckRsltCodeList", fckRslt_result);
		
		List<FckPcsComptVO> SvyComptList = fckPcsComptService.selectPcsSvyComptList(searchVO);
		model.addAttribute("resultList", SvyComptList);
	
		int totCnt = fckPcsComptService.selectPcsSvyComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/fck/pcs/sct/pcsComptList";
	}
	
	/**
	 * 위치도 재생성 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/updateLocReCreatePopup.do")
	public String updateLocReCreatePopup(ModelMap model) throws Exception {
		
		LocReCreateVO searchMap = new LocReCreateVO();
		EgovMap dateMap = fckPcsComptService.selectLastUpdateMinMaxDate(searchMap);
		
		model.addAttribute("lastUptDate", dateMap);
		
		return "sys/cmm/pop/updateLocReCreatePopup";
	}
	
	/**
	 * 대상지 조사 기간 별 위치도 재생성
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/updateLocReCreate.do")
	public ModelAndView updateLocReCreate(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			List<?> list = fckPcsComptService.updateLocReCreate(searchVO);
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
	 * 조사완료 엑셀 재업로드 팝업
	 * @param aprComptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/updateFckPcsComptPopup.do")
	public String updateFckPcsComptPopup(
			@ModelAttribute("pcsComptVO") FckPcsComptVO pcsComptVO, 
			ModelMap model) throws Exception{
		return "sys/fck/pcs/sct/updateFckPcsComptPopup";
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
	@RequestMapping(value = "/sys/fck/pcs/sct/updateFckPcsComptExcel.do")
	public ModelAndView updateFckAprComptExcel(
			@ModelAttribute("pcsComptVO") FckPcsComptVO pcsComptVO, 
			@RequestParam(value="file") MultipartFile mFile,
			ModelMap model,
			HttpServletResponse res) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");

			String message = "Success";
			

			mv.addObject("message", message);
			mv.addObject("status","success");

		return mv;
	}


	/**
	 * 조사완료지를 상세조회한다.
	 * @param comptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/selectFckPcsComptDetail.do")
	public String selectPcsSvyComptDetail(@ModelAttribute("searchVO") 
	FckPcsComptVO searchVO,ModelMap model) throws Exception{
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userInfo", userInfo);
		
		JSONParser parser = new JSONParser();
		DmsFormalization dmsformal = new DmsFormalization();
		
		FckPcsComptVO vo = fckPcsComptService.selectFckPcsComptDetail(searchVO);
		
		String lonlat = "";
		
		if(vo.getLat() != null && !vo.getLat().equals("")) {
			dmsformal.setDmsLat(vo.getLat());
			vo.setLat(dmsformal.getDmsLat());
			lonlat += dmsformal.getDmsLat()+" ";
		}
		if(vo.getLon() != null && !vo.getLon().equals("")) {
			dmsformal.setDmsLon(vo.getLon());
			vo.setLon(dmsformal.getDmsLon());
			lonlat += dmsformal.getDmsLon();
		}
		
		vo.setSvyLatLon(lonlat);


		// 현장사진 
		if(vo.getPhoto() != null &&vo.getPhoto().length() > 2) {
			String photo = vo.getPhoto().toString();
			Object obj = parser.parse(photo);
			org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray)obj;
			model.addAttribute("orginl_photo_result", jsonArray);
		}	
		
		if(vo.getPhototag() != null && vo.getPhototag().length() > 2) { 
			Object obj = null;
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
	        String photo = vo.getPhototag().toString();
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
		
		// 균열깊이
		JSONObject crkdptArr = new JSONObject(); 
		// 균열깊이 콘크리트 1
		crkdptArr.put("crkdptavg1", vo.getCrkdptavg1()); // 평균값
		if(vo.getCrkdptcncrt1() != null && vo.getCrkdptcncrt1() != "") {
			String crkdptcncrt = vo.getCrkdptcncrt1();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_1_photo", data.has("균열깊이_콘크리트1_시험위치_사진") ? data.get("균열깊이_콘크리트1_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt1", data);
		} else { crkdptArr.append("crkdptcncrt1", ""); }
		// 균열깊이 콘크리트 2
		crkdptArr.put("crkdptavg2", vo.getCrkdptavg2()); // 평균값
		if(vo.getCrkdptcncrt2() != null && vo.getCrkdptcncrt2() != "") {
			String crkdptcncrt = vo.getCrkdptcncrt2();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_2_photo", data.has("균열깊이_콘크리트2_시험위치_사진") ? data.get("균열깊이_콘크리트2_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt2", data);
		} else { crkdptArr.append("crkdptcncrt2", ""); }
		// 균열깊이 콘크리트 3
		crkdptArr.put("crkdptavg3", vo.getCrkdptavg3()); // 평균값
		if(vo.getCrkdptcncrt3() != null && vo.getCrkdptcncrt3() != "") {
			String crkdptcncrt = vo.getCrkdptcncrt3();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_3_photo", data.has("균열깊이_콘크리트3_시험위치_사진") ? data.get("균열깊이_콘크리트3_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt3", data);
		} else { crkdptArr.append("crkdptcncrt3", ""); }
		// 균열깊이 콘크리트 4
		crkdptArr.put("crkdptavg4", vo.getCrkdptavg4()); // 평균값
		if(vo.getCrkdptcncrt4() != null && vo.getCrkdptcncrt4() != "") {
			String crkdptcncrt = vo.getCrkdptcncrt4();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_4_photo", data.has("균열깊이_콘크리트4_시험위치_사진") ? data.get("균열깊이_콘크리트4_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt4", data);
		} else { crkdptArr.append("crkdptcncrt4", ""); }

		// 압축강도
		JSONObject cmprsstrArr = new JSONObject(); 
		// 압축강도 콘크리트 1
		cmprsstrArr.put("cmprsstrnavg1", vo.getCmprsstrnavg1()); // 평균값
		if(vo.getCmprsstrncncrt1() != null && vo.getCmprsstrncncrt1() != "") {
			String cmprsstrncncrt = vo.getCmprsstrncncrt1();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_1_photo", data.has("압축강도_콘크리트1_시험위치_사진") ? data.get("압축강도_콘크리트1_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt1", data);
		} else { cmprsstrArr.append("cmprsstrncncrt1", ""); }
		// 압축강도 콘크리트 2
		cmprsstrArr.put("cmprsstrnavg2", vo.getCmprsstrnavg2()); // 평균값
		if(vo.getCmprsstrncncrt2() != null && vo.getCmprsstrncncrt2() != "") {
			String cmprsstrncncrt = vo.getCmprsstrncncrt2();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_2_photo", data.has("압축강도_콘크리트2_시험위치_사진") ? data.get("압축강도_콘크리트2_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt2", data);
		} else { cmprsstrArr.append("cmprsstrncncrt2", ""); }
		// 압축강도 콘크리트 3
		cmprsstrArr.put("cmprsstrnavg3", vo.getCmprsstrnavg3()); // 평균값
		if(vo.getCmprsstrncncrt3() != null && vo.getCmprsstrncncrt3() != "") {
			String cmprsstrncncrt = vo.getCmprsstrncncrt3();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_3_photo", data.has("압축강도_콘크리트3_시험위치_사진") ? data.get("압축강도_콘크리트3_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt3", data);
		} else { cmprsstrArr.append("cmprsstrncncrt3", ""); }
		// 압축강도 콘크리트 4
		cmprsstrArr.put("cmprsstrnavg4", vo.getCmprsstrnavg4()); // 평균값
		if(vo.getCmprsstrncncrt4() != null && vo.getCmprsstrncncrt4() != "") {
			String cmprsstrncncrt = vo.getCmprsstrncncrt4();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_4_photo", data.has("압축강도_콘크리트4_시험위치_사진") ? data.get("압축강도_콘크리트4_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt4", data);
		} else { cmprsstrArr.append("cmprsstrncncrt4", ""); }
		
		model.addAttribute("crkdptArr", crkdptArr.toMap());
		model.addAttribute("cmprsstrArr", cmprsstrArr.toMap());
		
		// 위치도
		org.json.simple.JSONArray lcmapArr = new org.json.simple.JSONArray();
		if(vo.getLcmap().length() > 2) {				
			String lcmap = vo.getLcmap().toString();
			Object obj = parser.parse(lcmap);
			lcmapArr = (org.json.simple.JSONArray)obj;
			model.addAttribute("lcmap_result", lcmapArr);
		}		
		
		// 공유방 권한 확인
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
		
		/* 공유방 권한 확인 */
		String access = fckPcsComptService.selectAuthorCheck(commandMap);
		
		model.addAttribute("result", vo);
		model.addAttribute("access", access);
//		model.addAttribute("dmgSttus_result", dmgSttusArr);
//		model.addAttribute("trglnd_result", trglndArr);
//		model.addAttribute("dmgFclt_result", dmgFcltArr);
		
		// 첨부파일 목록 조회
		List<EgovMap> pcsComptFileList = fckPcsComptService.selectPcsComptFile(vo.getGid());
		model.addAttribute("pcsComptFileList", pcsComptFileList);
		
		return "sys/fck/pcs/sct/pcsComptDetail";
	}
	
	
	
	
	/**
	 * 조사완료지 수정화면으로 이동한다.
	 * @param comptVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/updateFckPcsComptView.do")
	public String updateFckAprSvyComptView(
			@ModelAttribute("searchVO") FckPcsComptVO searchVO,
			@ModelAttribute("locImgInfoVO") LocImgInfoVO locImgInfoVO,
			ModelMap model) throws Exception{

		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		JSONParser parser = new JSONParser();
		FckPcsComptVO vo = fckPcsComptService.selectFckPcsComptDetail(searchVO);
		DmsFormalization dmsformal = new DmsFormalization();
		
		String lonlat = "";
		if(vo.getLat() != null && !vo.getLat().equals("")) {
			dmsformal.setDmsLat(vo.getLat());
			vo.setLat(dmsformal.getDmsLat());
			lonlat += dmsformal.getDmsLat()+" ";
		}
		if(vo.getLon() != null && !vo.getLon().equals("")) {
			dmsformal.setDmsLon(vo.getLon());
			vo.setLon(dmsformal.getDmsLon());
			lonlat += dmsformal.getDmsLon();
		}
		vo.setSvyLatLon(lonlat);
		
		model.addAttribute("photoTagNum", 6);
		model.addAttribute("fckPcsCompt", vo);
		
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
		
		// 현장사진 
		if(vo.getPhoto() != null &&vo.getPhoto().length() > 2) {
			String photo = vo.getPhoto().toString();
			Object obj = parser.parse(photo);
			org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray)obj;
			model.addAttribute("orginl_photo_result", jsonArray);
		}	
		
		if(vo.getPhototag() != null && vo.getPhototag().length() > 2) { 
			Object obj = null;
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			    String photo = vo.getPhototag().toString();
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
		
		// 균열깊이
		JSONObject crkdptArr = new JSONObject();
		// 원본용
		JSONObject crkdptArr2 = new JSONObject(); 
		// 균열깊이 콘크리트 1
		crkdptArr.put("crkdptavg1", vo.getCrkdptavg1()); // 평균값
		if(vo.getCrkdptcncrt1() != "" && vo.getCrkdptcncrt1() != null) {
			String crkdptcncrt = vo.getCrkdptcncrt1();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_1_photo", data.has("균열깊이_콘크리트1_시험위치_사진") ? data.get("균열깊이_콘크리트1_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt1", data);
			model.addAttribute("origin_crkdptcncrt1", new JSONObject(crkdptcncrt));
		} else { 
			crkdptArr.append("crkdptcncrt1", "");
			model.addAttribute("origin_crkdptcncrt1", "");
		}
		// 균열깊이 콘크리트 2
		crkdptArr.put("crkdptavg2", vo.getCrkdptavg2()); // 평균값
		if(vo.getCrkdptcncrt2() != ""&& vo.getCrkdptcncrt2() != null) {
			String crkdptcncrt = vo.getCrkdptcncrt2();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_2_photo", data.has("균열깊이_콘크리트2_시험위치_사진") ? data.get("균열깊이_콘크리트2_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt2", data);
			model.addAttribute("origin_crkdptcncrt2", new JSONObject(crkdptcncrt));
		} else { 
			crkdptArr.append("crkdptcncrt2", "");
			model.addAttribute("origin_crkdptcncrt2", "");
		}
		// 균열깊이 콘크리트 3
		crkdptArr.put("crkdptavg3", vo.getCrkdptavg3()); // 평균값
		if(vo.getCrkdptcncrt3() != ""&& vo.getCrkdptcncrt3() != null) {
			String crkdptcncrt = vo.getCrkdptcncrt3();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_3_photo", data.has("균열깊이_콘크리트3_시험위치_사진") ? data.get("균열깊이_콘크리트3_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt3", data);
			model.addAttribute("origin_crkdptcncrt3", new JSONObject(crkdptcncrt));
		} else { 
			crkdptArr.append("crkdptcncrt3", ""); 
			model.addAttribute("origin_crkdptcncrt3", "");
		}
		// 균열깊이 콘크리트 4
		crkdptArr.put("crkdptavg4", vo.getCrkdptavg4()); // 평균값
		if(vo.getCrkdptcncrt4() != ""&& vo.getCrkdptcncrt4() != null) {
			String crkdptcncrt = vo.getCrkdptcncrt4();
			JSONObject data = new JSONObject(crkdptcncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("crkdptcncrt_4_photo", data.has("균열깊이_콘크리트4_시험위치_사진") ? data.get("균열깊이_콘크리트4_시험위치_사진") : "");
			crkdptArr.append("crkdptcncrt4", data);
			model.addAttribute("origin_crkdptcncrt4", new JSONObject(crkdptcncrt));
		} else { 
			crkdptArr.append("crkdptcncrt4", ""); 
			model.addAttribute("origin_crkdptcncrt4", "");
		}

		// 압축강도
		JSONObject cmprsstrArr = new JSONObject(); 
		// 압축강도 콘크리트 1
		cmprsstrArr.put("cmprsstrnavg1", vo.getCmprsstrnavg1()); // 평균값
		if(vo.getCmprsstrncncrt1() != "" && vo.getCmprsstrncncrt1() != null) {
			String cmprsstrncncrt = vo.getCmprsstrncncrt1();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_1_photo", data.has("압축강도_콘크리트1_시험위치_사진") ? data.get("압축강도_콘크리트1_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt1", data);
			model.addAttribute("origin_cmprsstrncncrt1", new JSONObject(cmprsstrncncrt));
		} else { 
			cmprsstrArr.append("cmprsstrncncrt1", "");
			model.addAttribute("origin_cmprsstrncncrt2", "");
		}
		// 압축강도 콘크리트 2
		cmprsstrArr.put("cmprsstrnavg2", vo.getCmprsstrnavg2()); // 평균값
		if(vo.getCmprsstrncncrt2() != ""&& vo.getCmprsstrncncrt2() != null) {
			String cmprsstrncncrt = vo.getCmprsstrncncrt2();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_2_photo", data.has("압축강도_콘크리트2_시험위치_사진") ? data.get("압축강도_콘크리트2_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt2", data);
			model.addAttribute("origin_cmprsstrncncrt2", new JSONObject(cmprsstrncncrt));
		} else { 
			cmprsstrArr.append("cmprsstrncncrt2", "");
			model.addAttribute("origin_cmprsstrncncrt2", "");
		}
		// 압축강도 콘크리트 3
		cmprsstrArr.put("cmprsstrnavg3", vo.getCmprsstrnavg3()); // 평균값
		if(vo.getCmprsstrncncrt3() != "" && vo.getCmprsstrncncrt3() != null) {
			String cmprsstrncncrt = vo.getCmprsstrncncrt3();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_3_photo", data.has("압축강도_콘크리트3_시험위치_사진") ? data.get("압축강도_콘크리트3_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt3", data);
			model.addAttribute("origin_cmprsstrncncrt3", new JSONObject(cmprsstrncncrt));
		} else { 
			cmprsstrArr.append("cmprsstrncncrt3", "");
			model.addAttribute("origin_cmprsstrncncrt3", "");
		}
		// 압축강도 콘크리트 4
		cmprsstrArr.put("cmprsstrnavg4", vo.getCmprsstrnavg4()); // 평균값
		if(vo.getCmprsstrncncrt4() != "" && vo.getCmprsstrncncrt4() != null) {
			String cmprsstrncncrt = vo.getCmprsstrncncrt4();
			JSONObject data = new JSONObject(cmprsstrncncrt.replaceAll("시험값", "testVal").replaceAll("gimg://", ""));
			data.put("cmprsstrncncrt_4_photo", data.has("압축강도_콘크리트4_시험위치_사진") ? data.get("압축강도_콘크리트4_시험위치_사진") : "");
			cmprsstrArr.append("cmprsstrncncrt4", data);
			model.addAttribute("origin_cmprsstrncncrt4", new JSONObject(cmprsstrncncrt));
		} else { 
			cmprsstrArr.append("cmprsstrncncrt4", "");
			model.addAttribute("origin_cmprsstrncncrt4", "");
		}
		
		model.addAttribute("crkdptArr", crkdptArr.toMap());
		model.addAttribute("cmprsstrArr", cmprsstrArr.toMap());		
		
		//위치도 조회 처리		
		HashMap<String, Object> schMap = new HashMap<>();
		locImgInfoVO.setGid(Integer.parseInt(searchVO.getGid()));
		locImgInfoVO.setSvySe("PCS");
				
		schMap.put("SE",locImgInfoVO.getSvySe());
		schMap.put("type", vo.getSvyType());
		schMap.put("gid", Integer.parseInt(vo.getGid()));		
				
		List<EgovMap> locList = extFieldBookService.selectComptLcMapLonLat(schMap);
		
		if(locList.isEmpty()) {
			locList = extFieldBookService.selectPcsComptLcMapLonLat(schMap);
		}
				
		if(locList.size() > 0) {
			Object obj;
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
//						model.addAttribute("mapParam", paramArr.get(0).toString().replaceAll("\"", "\'"));
			}
			model.addAttribute("orginl_zoom", zoom);
		}		
		
		
		if(vo.getSvyType() == "사방댐 정밀점검") {}
		else if(vo.getSvyType() == "계류보전 정밀점검") {}
		else if(vo.getSvyType() == "산지사방 정밀점검") {}
		else if(vo.getSvyType() == "해안침식방지 정밀점검") {}
		else {}
		
		HashMap<String, Object> commandMap = new HashMap<>();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		String test = EgovUserDetailsHelper.getAuthorities().toString();
		commandMap.put("id", Integer.parseInt(vo.getMstId()));
		commandMap.put("esntlId", loginVO.getUniqId());
				
		/* 공유방 권한 확인 */
		String access = fckPcsComptService.selectAuthorCheck(commandMap);
		model.addAttribute("access", access);		
		
		// 첨부파일 목록 조회
		List<EgovMap> pcsComptFileList = fckPcsComptService.selectPcsComptFile(vo.getGid());
		model.addAttribute("pcsComptFileList", pcsComptFileList);
		
		return "sys/fck/pcs/sct/pcsComptUpdt";
	}
	
	
	
	/**
	 * 조사완료지의 대상지목록을 엑셀로 다운로드한다.
	 * @param gId
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/selectDownloadSldListExcel.do")
	public ModelAndView selectDownloadSldListExcel(FckPcsComptVO comptVO,HttpServletResponse res) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("excelView");
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	
    	List<EgovMap> dmgFcltList = new ArrayList<EgovMap>();
    	


    	
    	return modelAndView;
	}
	
	/**
     * 현장사진을 일괄 다운로드한다.
     * @param svyComptVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/sys/fck/pcs/sct/selectDownloadPhotoListZip.do")
	public void selectDownloadPhotoListZip(FckPcsComptVO svyComptVO, HttpServletResponse res) throws Exception{
		long beforeTime = System.currentTimeMillis();
		
		FckPcsComptVO result = fckPcsComptService.selectDownloadPhotoList(svyComptVO);
		
		// 조사ID
		String svyId = result.getSvyId();
		// 공유방 사진 폴더명
		String mstNm = result.getMstNm();
		// 공유방 경로 파일 생성
		File storeFile = new File(fileStoreDir.concat("한국치산기술협회-"+mstNm+".ncx"));
		
		File[] files = null;
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
 		String dt_str = formater.format(new Date()).toString();//현재시간 문자열 생성
 		String dir = fileStoreDir.concat(svyId+"_"+dt_str);//조사아이디_현재시간
 		Path path = Paths.get(dir);
 		Files.createDirectories(path);//조사아이디_현재시간 폴더 생성
 		
 		File directory = new File(path.toString());
 		
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
 		
 		if(files != null) {
	 		for (int i = 0; i < files.length; i++) {
	 			String nm = files[i].getName();
	 			JSONParser parser = new JSONParser();
	 			
	 			String parseType = result.getPhoto();
	 			
				org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(parseType);
				for(int j=0; j<jsonArray.size(); j++) {
					if(jsonArray.get(j).toString().contains(nm)) {
						EgovFileUtil.cp(storeFile+File.separator+nm, dir+File.separator+nm);//원본이미지 복사
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
	 				
	 				String fileName = svyId+"_"+dt_str + ".zip";
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
		
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		System.out.println("시간차이(m) : "+secDiffTime);
	}	
	
	/**
	 * 조사완료지를 수정한다.
	 * @param comptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/fck/pcs/sct/updateFckPcsCompt.do")
    public ModelAndView updateFckAprSvyCompt(@ModelAttribute("comptVO") FckPcsComptVO comptVO, BindingResult bindingResult, Model model, HttpServletRequest req, MultipartHttpServletRequest multiRequest) throws Exception {
		HashMap<String, Object> schMap = new HashMap<>();		
	
		ModelAndView mv = new ModelAndView("jsonView");
							
		String svyType = comptVO.getSvyType();
		
		LocImgInfoVO locImgInfoVO = new LocImgInfoVO();
		
		comptVO.setPhotoTagList(comptVO.getPhotoTagList().replaceAll("&quot;", "\""));
		
		try {
			fckPcsComptService.updateFckPcsCompt(comptVO);				
			schMap.put("mst_id", Integer.parseInt(comptVO.getMstId()));
			List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
			schMap.put("SE","PCS");
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
	@RequestMapping(value = "/sys/fck/pcs/sct/deleteFckPcsCompt.do")
	public ModelAndView deleteFckPcsCompt(FckPcsComptVO comptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			fckPcsComptService.deleteFckPcsCompt(comptVO);
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
	 * 조사완료지 엑셀을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/fck/pcs/sct/selectPcsSvyComptListExcel.do")
	public ModelAndView selectPcsComptListExcel(FckPcsComptVO comptVo, HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView("pcsExcelView");
		
	 	String svyType = request.getParameter("svyType");
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		HashMap<?, ?> _map = (HashMap<?, ?>) fckPcsComptService.selectFckPcsSvyComptListExcel(comptVo);
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		
		String fileNm = svyType+"_조사완료지_".concat(formater.format(new Date()).toString());
		
		dataMap.put("sheetNm", fileNm);
		dataMap.put("list", _map.get("resultList"));
		
		modelAndView.addObject("dataMap",dataMap);
		modelAndView.addObject("filename",fileNm);
		
		return modelAndView;
	}
	
	/**
	 * 대상지 기간 별 건수 조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/selectLocReCeateCnt.do")
	public ModelAndView selectLocReCeateCnt(@ModelAttribute("searchVO") LocReCreateVO searchVO,
			Model model) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			try {
				EgovMap dateMap = fckPcsComptService.selectLastUpdateMinMaxDate(searchVO);
				
				mv.addObject("status",200);
				mv.addObject("message","success");
				mv.addObject("allCnt", dateMap.get("allcnt"));
			} catch (Exception e) {
				mv.addObject("status",400);
				mv.addObject("message",e.getMessage());
			}
		}
		
		return mv;
	}	
	
	/**
	 * 파일 등록 팝업 표출
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/insertFileUploadPopup.do")	
	public String insertFileUploadPopup(HttpServletRequest req, Model model) throws Exception {
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			return "forward:/login.do";	
		}else {
			String gid = req.getParameter("gid");
			model.addAttribute("gid", gid);
			
			return "sys/fck/pcs/sct/pcsComptFileUploadPopup";		
		}
	}
	
	/**
	 * 파일 등록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/insertFileUpload.do")	
	public ModelAndView insertFileUpload(@RequestParam("gid") String gid, MultipartHttpServletRequest multiRequest, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		String file_wrter = user.getId();
		
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			try {

				List<MultipartFile> mpf = multiRequest.getFiles("fileList");
				
				Date date = new Date();
				UUID uuid = UUID.randomUUID();
				if (!mpf.isEmpty()) {
					for(int i=0; i<mpf.size(); i++){		
						String file_originl_nm = mpf.get(i).getOriginalFilename();
						String file_id = uuid+"-"+file_originl_nm;
						File file_path = new File(fieldBookUploadPath+File.separator+"pcsCompt"+File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
						long file_size = mpf.get(i).getSize();
						String file_extsn = EgovFileUploadUtil.getFileExtension(mpf.get(i).getOriginalFilename());
						
						File file;
						if(!file_path.exists()){file_path.mkdirs();}
						do {
							file = new File(file_path + File.separator + file_id);
						} while(file.exists());
						
						mpf.get(i).transferTo(file);
						HashMap<String, String> fileMap = new HashMap<String, String>();
						fileMap.put("gid", gid);
						fileMap.put("file_id", file_id);
						fileMap.put("file_originl_nm", file_originl_nm);
						fileMap.put("file_stre_nm", file_path.toString()+File.separator+file_originl_nm);
						fileMap.put("file_wrter", file_wrter);
						fileMap.put("file_path", file_path.toString());
						fileMap.put("file_size", String.valueOf(file_size));
						fileMap.put("file_extsn", file_extsn);
						
						fckPcsComptService.insertPcsComptFile(fileMap);
						
					}
			    }
				
				
				mv.addObject("status",200);
				mv.addObject("message","success");
			} catch (Exception e) {
				mv.addObject("status",400);
				mv.addObject("message",e.getMessage());
			}
		}
		
		return mv;	
	}
	
	/**
	 * 파일 다운로드
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/selectFileDownload.do")	
	public void selectFileDownload(@RequestParam("gid") String gid, @RequestParam("sn") String sn, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		
		if(isAuthenticated) {
			
			// 첨부파일 목록 조회
			List<EgovMap> pcsComptFileList = fckPcsComptService.selectPcsComptFile(gid);
			
			String filename = null;
			String orginal =  null;
			String stordFilePath = null;
			
			for(int i=0; i<pcsComptFileList.size(); i++) {
				if(Integer.parseInt(pcsComptFileList.get(i).get("sn").toString()) == Integer.parseInt(sn)) {
					filename = pcsComptFileList.get(i).get("fileId").toString();
					orginal = pcsComptFileList.get(i).get("fileOrginlNm").toString();
					stordFilePath = pcsComptFileList.get(i).get("filePath").toString();
				}
			}
			
			orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
			String file = stordFilePath + File.separator + filename;
			
			res.setContentType("application/octet-stream; charset=UTF-8");
			res.setHeader("Content-Disposition", "attachment;filename="+orginal);
			
			final File fileToDownload = new File(file);
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(fileToDownload);
				IOUtils.copy(inputStream, res.getOutputStream());
				res.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 파일 삭제
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/pcs/sct/deleteFileDownload.do")	
	public ModelAndView deleteFileDownload( @RequestParam("sn") String sn) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
			
		}else {
			try {
				
				if(sn != null  && !sn.equals("")) {
					fckPcsComptService.deletePcsComptFile(sn);
				}
				
				mv.addObject("status",200);
				mv.addObject("message","success");
			} catch (Exception e) {
				mv.addObject("status",400);
				mv.addObject("message",e.getMessage());
			}
		}
		
		return mv;
		
	}
}
	