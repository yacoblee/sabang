package or.sabang.sys.vyt.frd.web;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
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
import org.stringtemplate.v4.ModelAdaptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
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
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;
import or.sabang.utl.CommonUtil;
import or.sabang.utl.DmsFormalization;
import or.sabang.utl.SuperMapBasicUtils;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookItemVO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookService;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandService;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptService;

@Controller
public class VytFrdSvyComptController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "vytFrdSvyComptService")
	private VytFrdSvyComptService vytFrdSvyComptService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "vytFrdStripLandService")
	private VytFrdStripLandService vytFrdStripLandService;
	
	@Resource(name = "vytFrdFieldBookService")
	private VytFrdFieldBookService vytFrdFieldBookService;
	
	
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
	/** 첨부파일 위치 지정  => globals.properties */
	private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	/** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.frdSld");
	
//    private static final String VWORLD_API_URL = EgovProperties.getProperty("vworld.landUrl");
//	private static final String VWORLD_API_KEY = EgovProperties.getProperty("vworld.apiKey");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdSvyComptController.class);
	
	/**
	 * 임도타당성평가 조사완료를 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectFrdSvyComptList.do")	
	public String selectFrdSvyComptList(@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO, ModelMap model) throws Exception {
		
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
		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		AdministZoneVO adminVO = new AdministZoneVO();
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(vytFrdSvyComptService.selectFrdSvyComptMaxYear());
		}
		if(searchVO.getSvyMonth() == null) {
			searchVO.setSvyMonth(vytFrdSvyComptService.selectFrdSvyComptMaxMonth());
		}
		//연도코드 조회
		List<?> year_result = vytFrdSvyComptService.selectFrdSvyComptYear();
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
		
		// 임도 종류
		vo.setCodeId("FEI171");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		
		List<VytFrdSvyComptVO> SvyComptList = vytFrdSvyComptService.selectFrdSvyComptList(searchVO);
		model.addAttribute("resultList", SvyComptList);
	
		int totCnt = vytFrdSvyComptService.selectFrdSvyComptListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/vyt/frd/sct/svyComptList";		
	}
	
	/**
	 * 조사완료지 엑셀 재업로드 팝업
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/updateFrdSvyComptPopup.do")
	public String insertCnrsSpceSldPopup(ModelMap model) throws Exception {
		
		return "sys/vyt/frd/sct/updateFrdSvyComptPopup";
	}
	
	/**
	 * 조사완료지를 상세조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectFrdSvyComptDetail.do")
	public String selectFrdSvyComptDetail(@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO, ModelMap model, HttpServletRequest req) throws Exception{
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", searchVO.getPageUnit());
		searchMap.put("schpageIndex", searchVO.getPageIndex());
		searchMap.put("schfrdType", searchVO.getFrdType());
		searchMap.put("schsvyYear", searchVO.getSvyYear());
		searchMap.put("schsvyMonth", searchVO.getSvyMonth());
		searchMap.put("schsvySd", searchVO.getSvySd());
		searchMap.put("schsvySgg", searchVO.getSvySgg());
		searchMap.put("schsvyEmd", searchVO.getSvyEmd());
		searchMap.put("schsvyRi", searchVO.getSvyRi());
		searchMap.put("schsvyId", searchVO.getSvyId());
		searchMap.put("schsvyUser", searchVO.getSvyUser());
		searchMap.put("schmstNm", searchVO.getMstNm());
		model.addAttribute("searchMap", searchMap);
		
		
		HashMap<String, String> numberMap = new HashMap<String, String>();
		numberMap.put("svyLabel", searchVO.getSvyLabel());
		numberMap.put("mstId", searchVO.getMstId());
		
		DmsFormalization dmsformal = new DmsFormalization();
		List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
		String sldNumber1 = null, sldNumber2 = null;
		for(int i=0; i<sldNumberList.size(); i++) {
			if(sldNumberList.get(i).get("routeCode").equals("02")) {
				sldNumber2 = sldNumberList.get(i).get("smid").toString();
			}else if(sldNumberList.get(i).get("routeCode").equals("01")) {
				sldNumber1 = sldNumberList.get(i).get("smid").toString();
			}
		}
		
		String sldNumber = sldNumber2 != null ? sldNumber2 : sldNumber1; 
		
		// 대상지 관리  start
		VytFrdStripLandVO sldResult = vytFrdStripLandService.selectSldDetailOne(sldNumber1);
		
		if(sldResult != null) {
			sldResult.setSmid(sldNumber1);
			sldResult.setMstId(searchVO.getMstId());
			
			if(sldResult.getBpx() != null && !sldResult.getBpx().equals("")) {
				dmsformal.setDmsLon(sldResult.getBpx());
				sldResult.setBpx(dmsformal.getDmsLon());
			}
			if(sldResult.getBpy() != null && !sldResult.getBpy().equals("")) {
				dmsformal.setDmsLat(sldResult.getBpy());
				sldResult.setBpy(dmsformal.getDmsLat());
			}
			if(sldResult.getEpx1() != null && !sldResult.getEpx1().equals("")) {
				dmsformal.setDmsLon(sldResult.getEpx1());
				sldResult.setEpx1(dmsformal.getDmsLon());
			}
			if(sldResult.getEpy1() != null && !sldResult.getEpy1().equals("")) {
				dmsformal.setDmsLat(sldResult.getEpy1());
				sldResult.setEpy1(dmsformal.getDmsLat());
			}
			if(sldResult.getEpx2() != null && !sldResult.getEpx2().equals("")) {
				dmsformal.setDmsLon(sldResult.getEpx2());
				sldResult.setEpx2(dmsformal.getDmsLon());
			}
			if(sldResult.getEpy2() != null && !sldResult.getEpy2().equals("")) {
				dmsformal.setDmsLat(sldResult.getEpy2());
				sldResult.setEpy2(dmsformal.getDmsLat());
			}
			
			// 필지 조회
			List<VytFrdStripLandVO> parcelList = vytFrdStripLandService.selectParcelList(sldNumber1);
			
			String path1 = req.getRequestURL().toString();
			String path2 = req.getRequestURI(); 
			
			String domain = path1.replaceAll(path2, "");
			
			if(parcelList != null) {
				sldResult.setParcelCnt(Integer.toString(parcelList.size()-1));
				// 소유구분 조회
				for (int i=0; i<parcelList.size(); i++) {
					String pnuCode = parcelList.get(i).getPnuCode();
					String returnValue = CommonUtil.searchPosesnSe(pnuCode, domain);
					String[] returnSplit = returnValue.split(",");
					parcelList.get(i).setPosesnSe(returnSplit[0]);
					String jibun = parcelList.get(i).getJibun();
					
					if(!returnValue.equals("-")) {
						if(returnSplit[1].equals("임야대장")) {
							parcelList.get(i).setJibun("산"+jibun);
						}
						
						if(returnSplit[1].contains("대장")) {
							String jimok = returnSplit[1].replaceAll("대장", "");
							parcelList.get(i).setJimok(jimok);
						}else {
							parcelList.get(i).setJimok(returnSplit[1]);
						}
					}
				}
			}
			
			model.addAttribute("sldResult", sldResult);
			model.addAttribute("parcelList", parcelList);
			
			
			HashMap<String, Object> analParameterMap = new HashMap<String, Object>();
			analParameterMap.put("smid", sldNumber);
			
			if(sldNumber2 != null) {
				analParameterMap.put("routeCode", "02");
			}else {
				analParameterMap.put("routeCode", "01");
			}
			
			List<AnalFileVO> analList = vytFrdStripLandService.selectAnalImg(analParameterMap);
			
			// 이미지 가공
			HashMap<String, Object> analImg = new HashMap<String, Object>();
			HashMap<String, Object> gidList = new HashMap<String, Object>();
			
			// 통계가공
			HashMap<String, Object> statList = new HashMap<String, Object>();
			
			if(analList.size() > 0) {
				for(int i=0; i<analList.size(); i++){
					// 분석 이미지 종류
					String analType = analList.get(i).getOrignlFileNm();
					// 파일 경로
					String analStream = analList.get(i).getFileStreCours().replaceAll("\\\\", "/").replaceAll("/home/tomcat/FEIStorage/", "");
					// 파일명
					String analNm = analList.get(i).getStreFileNm();
					// 파일경로/파일명
					String analFileFullStream = analStream.concat("/").concat(analNm).concat(".jpg");
					analImg.put(analType, analFileFullStream);
					
					int gid = analList.get(i).getGid();
					
					gidList.put(analType, gid);
					
					String fileExten = analList.get(i).getFileExtsn();
					// frtp : 임상, fror : 임종, agcls : 영급, dnst : 밀도, dmcls : 경급, koftr : 수종 , slope:경사, aspect:향, dem:표고, soil:토성, geology:지질, prrck:모암, accma:퇴적양식, rock:암석, landslide:산사태위험등급
					String[] allowType = {"frtp", "fror", "agcls", "dnst", "dmcls", "koftr", "slope", "aspect", "dem", "soil", "geology", "prrck", "accma", "rock", "landslide"};
					
					if(!fileExten.equals("zip") && Arrays.asList(allowType).contains(analType)) {
						// 통계자료
						String statData = analList.get(i).getStatData();
						String statMaxData = analList.get(i).getStatMaxData();
						
						// 경사도,향,표고 Raster 통계
						String statMaxRasterValue = analList.get(i).getStatMaxValue();
						String statMinRasterValue = analList.get(i).getStatMinValue();
						String statAvgRasterValue = analList.get(i).getStatAvgValue();
						
						if(analType.equals("slope") || analType.equals("dem")) {
							statList.put(analType+"MaxVal",statMaxRasterValue);
							statList.put(analType+"MinVal",statMinRasterValue);
							statList.put(analType+"AvgVal",statAvgRasterValue);
						}
						if(analType.equals("aspect")) {
					        JSONObject jsonObject = new JSONObject(statData);
					        
					        String maxKey = null;
					        String maxValue = "0%"; 
					        
					        for (String key : jsonObject.keySet()) {
					            String value = jsonObject.getString(key);
					            if (value.endsWith("%")) {
					                value = value.replace("%", "");
					                if (Float.parseFloat(value) > Float.parseFloat(maxValue.replace("%", ""))) {
					                    maxKey = key;
					                    maxValue = jsonObject.getString(key);
					                }
					            }
					        }
					        statList.put(analType+"MaxNm",maxKey);
							statList.put(analType+"MaxVal",maxValue);
						}
						
						if(statMaxData != null) {
							String[] parts = statMaxData.split(",");
							if (parts.length == 2) {
								String statMaxName = parts[0]; 
								String statMaxValue = parts[1];
								statList.put(analType+"MaxNm",statMaxName);
								statList.put(analType+"MaxVal",statMaxValue);
							}
						}
						if(statData != null) {
							ObjectMapper ojm = new ObjectMapper();
							Map<String, String> dataMap = ojm.readValue(statData, java.util.Map.class);
							
							if(analType.equals("koftr") || analType.equals("soil") || analType.equals("geology") || analType.equals("dem")) {
								
								statList.put(analType+"Cnt", dataMap.size());
								
					        	// 값이 5개미만인 경우
					        	if(dataMap.size() == 4) {
					        		dataMap.put("1", "");
					        	}else if(dataMap.size() == 3) {
					        		dataMap.put("1", "");
					        		dataMap.put("2", "");
					        	}else if(dataMap.size() == 2) {
					        		dataMap.put("1", "");
					        		dataMap.put("2", "");
					        		dataMap.put("3", "");
					        	}else if(dataMap.size() == 1) {
					        		dataMap.put("1", "");
					        		dataMap.put("2", "");
					        		dataMap.put("3", "");
					        		dataMap.put("4", "");
					        	}
							}
				        	statList.put(analType, dataMap);
						}
					}
				}
			}
			model.addAttribute("gidList", gidList);
			model.addAttribute("analList", analList);
			model.addAttribute("analImg", analImg);
			model.addAttribute("statList", statList);
			
			//01 : 국가등록문화재, 02 : 국가지정문화재, 03 : 국가지정문화재보호구역, 04 : 시도지정문화재, 05 : 시도지정문화재보호구역
			if(sldResult.getCulture() != null) {
				String[] smidArray = sldResult.getCulture().split(",");
		        
		        List<Integer> list = new ArrayList<>();
		        for (String smidOne : smidArray) {
		        	list.add(Integer.parseInt(smidOne));
		        }	
				
				List<EgovMap> culList = vytFrdStripLandService.selectCultureName(list);
				
				HashMap<String, Integer> culCntMap = new HashMap<String, Integer>();
				List<String> culNmList = new ArrayList<>();
				if(culList.size() > 0 && culList != null) {
					
					int natlRegCul=0, natlChoCul=0, SidoChoCul=0;
				
					for(int i=0; i<culList.size(); i++) {
						String cultNm = culList.get(i).get("cultNm").toString();
						String cultType =  culList.get(i).get("cultType").toString();
						
						if(cultType.equals("01")) {	// 국가등록문화재
							natlRegCul++;
						}else if(cultType.equals("02")) {	// 국가지정문화재
							natlChoCul++;
						}else if(cultType.equals("04")) {	// 시도지정문화재
							SidoChoCul++;
						}
						culNmList.add(cultNm);
					}
					
					culCntMap.put("natlRegCul", natlRegCul);
					culCntMap.put("natlChoCul", natlChoCul);
					culCntMap.put("SidoChoCul", SidoChoCul);
				}
				
				model.addAttribute("culNmList",culNmList);
				model.addAttribute("culCntMap",culCntMap);
			}
		}
		// 대상지관리 end
		
		// 전자야장연계 start
		String fieldBookNumber = vytFrdSvyComptService.selectFieldBookNumber(numberMap);
		VytFrdFieldBookItemVO fieldBookVO = new VytFrdFieldBookItemVO();
		fieldBookVO.setGid(Integer.parseInt(fieldBookNumber));
		
		VytFrdFieldBookItemVO fieldBookResult = vytFrdFieldBookService.selectFieldBookDetailOne(fieldBookVO);
		model.addAttribute("fieldBookResult", fieldBookResult);
		// 전자야장연계 end
		
		
		
		// 조사완료 start
		VytFrdSvyComptVO svyComptResult = vytFrdSvyComptService.selectFrdSvyComptDetail(searchVO);

		if(svyComptResult.getBpx() != null && !svyComptResult.getBpx().equals("")) {
			dmsformal.setDmsLon(svyComptResult.getBpx());
			svyComptResult.setBpx(dmsformal.getDmsLon());
		}
		if(svyComptResult.getBpy() != null && !svyComptResult.getBpy().equals("")) {
			dmsformal.setDmsLat(svyComptResult.getBpy());
			svyComptResult.setBpy(dmsformal.getDmsLat());
		}
		if(svyComptResult.getEpx() != null && !svyComptResult.getEpx().equals("")) {
			dmsformal.setDmsLon(svyComptResult.getEpx());
			svyComptResult.setEpx(dmsformal.getDmsLon());
		}
		if(svyComptResult.getEpy() != null && !svyComptResult.getEpy().equals("")) {
			dmsformal.setDmsLat(svyComptResult.getEpy());
			svyComptResult.setEpy(dmsformal.getDmsLat());
		}
		
		HashMap<String, Object> projMap = new HashMap<>();
		
		// 보호시설
		String safeFctList = svyComptResult.getSafeFctList();
		if (safeFctList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
		    JsonNode dataMap = ojm.readTree(safeFctList.toString());
		    for (int i = 0; i < dataMap.size(); i++) {
		    	// 보호시설 key -> 경도, 위도, 유형
		        String lon = dataMap.get(i).get("경도").asText();
		        String lat = dataMap.get(i).get("위도").asText();
		        String type = dataMap.get(i).get("유형").asText();

		        JSONObject job = new JSONObject();
		        
		        if(lon != null) projMap.put("lon1", lon);
		        if(lat != null) projMap.put("lat1", lat);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon = projList.get(0).get("lon1").toString();
		        String convertLat = projList.get(0).get("lat1").toString();
		        
		        if(convertLon != null && !convertLon.equals("")) {
					dmsformal.setDmsLon(convertLon);
					job.put("lon", dmsformal.getDmsLon());
				}
		        if(convertLat != null && !convertLat.equals("")) {
		        	dmsformal.setDmsLat(convertLat);
		        	job.put("lat", dmsformal.getDmsLat());
		        }
		        job.put("type", type);
		        
		        jsonArray.add(job);
		        obj = parser.parse(jsonArray.toString());
	        	jsonArray = (org.json.simple.JSONArray)obj;
		    }
		    model.addAttribute("safeFctList", jsonArray);
		}
		
		// 종단기울기
		String lonSlopeList = svyComptResult.getLonSlopeList();
		if (lonSlopeList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(lonSlopeList.toString());
			
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 종단기울기 key -> 위도1, 경도1, 위도2, 경도2, 기울기, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String slope = dataMap.get(i).get("기울기").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
		        
		        if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
		        if(convertLon2 != null && !convertLon2.equals("")) {
		        	dmsformal.setDmsLon(convertLon2);
		        	job.put("lon2", dmsformal.getDmsLon());
		        }
		        if(convertLat1 != null && !convertLat1.equals("")) {
		        	dmsformal.setDmsLat(convertLat1);
		        	job.put("lat1", dmsformal.getDmsLat());
		        }
		        if(convertLat2 != null && !convertLat2.equals("")) {
		        	dmsformal.setDmsLat(convertLat2);
		        	job.put("lat2", dmsformal.getDmsLat());
		        }
				job.put("slope", slope);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
		        }
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
				
			}
			model.addAttribute("lonSlopeList", jsonArray);
		}
		
		// 산지경사
		String mtSlopeList = svyComptResult.getMtSlopeList();
		if (mtSlopeList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(mtSlopeList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 산지경사 key -> 위도1, 경도1, 위도2, 경도2, 경사도, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String slope = dataMap.get(i).get("경사도").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
		        
		        if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
		        if(convertLon2 != null && !convertLon2.equals("")) {
		        	dmsformal.setDmsLon(convertLon2);
		        	job.put("lon2", dmsformal.getDmsLon());
		        }
		        if(convertLat1 != null && !convertLat1.equals("")) {
		        	dmsformal.setDmsLat(convertLat1);
		        	job.put("lat1", dmsformal.getDmsLat());
		        }
		        if(convertLat2 != null && !convertLat2.equals("")) {
		        	dmsformal.setDmsLat(convertLat2);
		        	job.put("lat2", dmsformal.getDmsLat());
		        }
				job.put("slope", slope);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
		        }
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
				
			}
			model.addAttribute("mtSlopeList", jsonArray);
		}
		
		// 암반노출
		String rockExposList = svyComptResult.getRockExposList();
		if (rockExposList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			double totalRockExpos = 0;
			
			JsonNode dataMap = ojm.readTree(rockExposList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 암반노출 key -> 위도1, 경도1, 위도2, 경도2, 암반노출, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String rockExpos = dataMap.get(i).get("암반노출").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("rockExpos", rockExpos);
				
				if(rockExpos != null && !rockExpos.equals("") && StringUtils.isNumeric(rockExpos)) {
					totalRockExpos += Double.parseDouble(rockExpos);
				}
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
				
			}
			svyComptResult.setRockExposSum(Double.toString(totalRockExpos));
			
			model.addAttribute("rockExposList", jsonArray);
		}
		
		// 조림지
		String afrstList = svyComptResult.getAfrstList();
		if (afrstList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(afrstList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 조림지 key -> 위도1, 경도1, 위도2, 경도2, 조림지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String afrst = dataMap.get(i).get("조림지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("afrst", afrst);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("afrstList", jsonArray);
		}
		
		// 벌채지
		String cuttingList = svyComptResult.getCuttingList();
		if (cuttingList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			JsonNode dataMap = ojm.readTree(cuttingList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 벌채지 key -> 위도1, 경도1, 위도2, 경도2, 벌채지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String cutting = dataMap.get(i).get("벌채지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("cutting", cutting);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("cuttingList", jsonArray);
		}
		
		// 석력지
		String stmiList = svyComptResult.getStmiList();
		if (stmiList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			String stmiTotalAt = "무"; // 석력지 유무는 야장에서 따로 넘어오지 않기때문에 체크해서 넘겨줌.
			
			JsonNode dataMap = ojm.readTree(stmiList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 석력지 key -> 위도1, 경도1, 위도2, 경도2, 석력지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String stmi = dataMap.get(i).get("석력지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("stmi", stmi);
				
				if(stmi != null && !stmi.equals("")) {	stmiTotalAt = "유";	}
				model.addAttribute("stmiTotalAt", stmiTotalAt);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("stmiList", jsonArray);
		}
		
		// 계류종류 및 현황
		String mrngKindList = svyComptResult.getMrngKind();
		if (mrngKindList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 대계류 합계
			int bigMrng1 = 0, bigMrng2 = 0, bigMrng3 = 0;
			
			// 중계류 합계
			int middleMrng1 = 0, middleMrng2 = 0, middleMrng3 = 0;
			
			// 소계류 합계
			int smallMrng1 = 0, smallMrng2 = 0, smallMrng3 = 0;
			
			JsonNode dataMap = ojm.readTree(mrngKindList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 계류종류 및 현황 key -> 위도1, 경도1, 위도2, 경도2, 대분류, 소분류, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String bigMrng = dataMap.get(i).get("대분류").asText();
				String smallMrng = dataMap.get(i).get("소분류").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("bigMrng", bigMrng);
				job.put("smallMrng", smallMrng);
				
				if(bigMrng.equals("대계류")) {
					bigMrng1 += 1;
					
					if(smallMrng.equals("상시천")) {
						bigMrng2 += 1;
					}else if(smallMrng.equals("건천")) {
						bigMrng3 += 1;
					}
					
				}else if(bigMrng.equals("중계류")) {
					middleMrng1 += 1;
					
					if(smallMrng.equals("상시천")) {
						middleMrng2 += 1;
					}else if(smallMrng.equals("건천")) {
						middleMrng3 += 1;
					}
				}else if(bigMrng.equals("소계류")) {
					smallMrng1 += 1;
					
					if(smallMrng.equals("상시천")) {
						smallMrng2 += 1;
					}else if(smallMrng.equals("건천")) {
						smallMrng3 += 1;
					}
				}
				
				String bigMrngTotal = Integer.toString(bigMrng1)+"/"+Integer.toString(bigMrng2)+"/"+Integer.toString(bigMrng3);
				String middleMrngTotal = Integer.toString(middleMrng1)+"/"+Integer.toString(middleMrng2)+"/"+Integer.toString(middleMrng3);
				String smallMrngTotal = Integer.toString(smallMrng1)+"/"+Integer.toString(smallMrng2)+"/"+Integer.toString(smallMrng3);
				
				HashMap<String, String> mrngTotalMap = new HashMap<String, String>();
				mrngTotalMap.put("bigMrngTotal", bigMrngTotal);
				mrngTotalMap.put("middleMrngTotal", middleMrngTotal);
				mrngTotalMap.put("smallMrngTotal", smallMrngTotal);
				
				model.addAttribute("mrngTotalMap", mrngTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			
			model.addAttribute("mrngKindList", jsonArray);
		}
		
		// 습지
		String wetLandList = svyComptResult.getWetLandList();
		if (wetLandList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(wetLandList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 습지 key -> 위도1, 경도1, 위도2, 경도2, 습지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String wetLand = dataMap.get(i).get("습지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("wetLand", wetLand);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("wetLandList", jsonArray);
		}
		
		
		// 묘지
		String cmtryList = svyComptResult.getCmtryList();
		if (cmtryList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 묘지 위치
			int cmtryLoc1 = 0, cmtryLoc2 = 0;
			
			// 묘지 관리
			int cmtryMng1 = 0, cmtryMng2 = 0;
			
			JsonNode dataMap = ojm.readTree(cmtryList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 묘지 key -> 위도1, 경도1, 위도2, 경도2, 묘지_유무, 묘지_위치, 묘지_관리, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String cmtryAt = dataMap.get(i).get("묘지_유무").asText();
				String cmtryLoc = dataMap.get(i).get("묘지_위치").asText();
				String cmtryMng = dataMap.get(i).get("묘지_관리").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("cmtryAt", cmtryAt);
				job.put("cmtryLoc", cmtryLoc);
				job.put("cmtryMng", cmtryMng);
				
				
				if(cmtryLoc.equals("노선 상부")) {
					cmtryLoc1 += 1;
				}else if(cmtryLoc.equals("노선 하부")) {
					cmtryLoc2 += 1;
				}
				
				if(cmtryMng.equals("관리O")) {
					cmtryMng1 += 1;
				}else if(cmtryMng.equals("관리X")) {
					cmtryMng2 += 1;
				}
				
				
				String cmtryLocTotal = Integer.toString(cmtryLoc1)+"/"+Integer.toString(cmtryLoc2);
				String cmtryMngTotal = Integer.toString(cmtryMng1)+"/"+Integer.toString(cmtryMng2);
				
				HashMap<String, String> cmtryTotalMap = new HashMap<String, String>();
				cmtryTotalMap.put("cmtryCnt", Integer.toString(dataMap.size()));
				cmtryTotalMap.put("cmtryLocTotal", cmtryLocTotal);
				cmtryTotalMap.put("cmtryMngTotal", cmtryMngTotal);
				
				model.addAttribute("cmtryTotalMap", cmtryTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			
			model.addAttribute("cmtryList", jsonArray);
		}

		// 용출수
		String eltnWaterList = svyComptResult.getEltnWaterList();
		if (eltnWaterList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(eltnWaterList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 용출수 key -> 위도1, 경도1, 위도2, 경도2, 용출수, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String eltnWater = dataMap.get(i).get("용출수").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("eltnWater", eltnWater);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("eltnWaterList", jsonArray);
		}
		
		// 연약지반
		String sofrtGrndList = svyComptResult.getSofrtGrndList();
		if (sofrtGrndList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(sofrtGrndList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 연약지반 key -> 위도1, 경도1, 위도2, 경도2, 연약지반, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String sofrtGrnd = dataMap.get(i).get("연약지반").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("sofrtGrnd", sofrtGrnd);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("sofrtGrndList", jsonArray);
		}
		
		
		// 붕괴우려지역
		String clpsCnrnList = svyComptResult.getClpsCnrnList();
		if (clpsCnrnList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 붕괴우려 사면
			int slope1 = 0, slope2 = 0, slope3 = 0, slope4 = 0, slope5 = 0;
			
			// 붕괴우려 계류
			int mtTrnt1 = 0, mtTrnt2 = 0, mtTrnt3 = 0, mtTrnt4 = 0, mtTrnt5 = 0;
			
			JsonNode dataMap = ojm.readTree(clpsCnrnList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 붕괴우려지역 key -> 위도1, 경도1, 위도2, 경도2, 붕괴우려_대분류, 붕괴우려_소분류, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String clpsCnrnBig = dataMap.get(i).get("붕괴우려_대분류").asText();
				String clpsCnrnSmall = dataMap.get(i).get("붕괴우려_소분류").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("clpsCnrnBig", clpsCnrnBig);
				job.put("clpsCnrnSmall", clpsCnrnSmall);
				
				if(clpsCnrnBig.equals("사면")) {
					slope1 += 1;
					
					if(clpsCnrnSmall.equals("침식")) {
						slope2 += 1;
					}else if(clpsCnrnSmall.equals("붕괴")) {
						slope3 += 1;
					}else if(clpsCnrnSmall.equals("포락")) {
						slope4 += 1;
					}else{
						slope5 += 1;
					}
					
				}else if(clpsCnrnBig.equals("계류")) {
					mtTrnt1 += 1;
					
					if(clpsCnrnSmall.equals("침식")) {
						mtTrnt2 += 1;
					}else if(clpsCnrnSmall.equals("붕괴")) {
						mtTrnt3 += 1;
					}else if(clpsCnrnSmall.equals("포락")) {
						mtTrnt4 += 1;
					}else{
						mtTrnt5 += 1;
					}
				}
				
				String slopeTotal = Integer.toString(slope1)+"/"+Integer.toString(slope2)+"/"+Integer.toString(slope3)+"/"+Integer.toString(slope4)+"/"+Integer.toString(slope5);
				String mtTrntTotal = Integer.toString(mtTrnt1)+"/"+Integer.toString(mtTrnt2)+"/"+Integer.toString(mtTrnt3)+"/"+Integer.toString(mtTrnt4)+"/"+Integer.toString(mtTrnt5);
				
				HashMap<String, String> clpsCnrnTotalMap = new HashMap<String, String>();
				clpsCnrnTotalMap.put("slopeTotal", slopeTotal);
				clpsCnrnTotalMap.put("mtTrntTotal", mtTrntTotal);
				
				model.addAttribute("clpsCnrnTotalMap", clpsCnrnTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			
			model.addAttribute("clpsCnrnList", jsonArray);
		}
		
		// 주요수종
		String maintreekndList = svyComptResult.getMaintreekndList();
		if (maintreekndList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(maintreekndList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 주요수종 key -> 위도1, 경도1, 위도2, 경도2, 주요수종, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String maintreeknd = dataMap.get(i).get("주요수종").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("maintreeknd", maintreeknd);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("maintreekndList", jsonArray);
		}
		
		// 주요식생
		String mainvegList = svyComptResult.getMainvegList();
		if (mainvegList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(mainvegList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 주요식생 key -> 위도1, 경도1, 위도2, 경도2, 주요식생, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String mainveg = dataMap.get(i).get("주요식생").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("mainveg", mainveg);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("mainvegList", jsonArray);
		}
		
		// 상수원 오염
		String wtrPltnList = svyComptResult.getWtrPltnList();
		if (wtrPltnList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(wtrPltnList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 상수원 오염 key -> 위도1, 경도1, 위도2, 경도2, 상수원오염, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String wtrPltn = dataMap.get(i).get("상수원오염").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("wtrPltn", wtrPltn);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("wtrPltnList", jsonArray);
		}
		
		// 훼손우려지
		String dmgCncrnList = svyComptResult.getDmgCncrnList();
		if (dmgCncrnList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(dmgCncrnList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 훼손우려지 key -> 위도1, 경도1, 위도2, 경도2, 훼손우려지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String dmgCncrn = dataMap.get(i).get("훼손우려지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("dmgCncrn", dmgCncrn);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("dmgCncrnList", jsonArray);
		}
		
		//산림재해(산불/병해충)
		String frstDsstrList = svyComptResult.getFrstDsstrList();
		if (frstDsstrList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 산불(건)
			int frstFireCnt = 0;
			
			// 병해충(건)
			int pestCnt = 0;
			
			// 기타(건)
			int etcCnt = 0;
			
			JsonNode dataMap = ojm.readTree(frstDsstrList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 산림재해 key -> 위도1, 경도1, 위도2, 경도2, 재해유형, 산림재해, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String dmgType = dataMap.get(i).get("재해유형").asText();
				String dmgAt = dataMap.get(i).get("산림재해").asText();//유무
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("dmgType", dmgType);
				job.put("dmgAt", dmgAt);
				
				if(dmgType.equals("산불")) {
					frstFireCnt += 1;
				}else if(dmgType.equals("병해충")) {
					pestCnt += 1;
				}else {
					etcCnt += 1;
				}
				
				HashMap<String, String> dmgTotalMap = new HashMap<String, String>();
				dmgTotalMap.put("frstFireCnt", Integer.toString(frstFireCnt));
				dmgTotalMap.put("pestCnt", Integer.toString(pestCnt));
				dmgTotalMap.put("etcCnt", Integer.toString(etcCnt));
				
				model.addAttribute("dmgTotalMap", dmgTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("frstDsstrList", jsonArray);
		}
		
		// 야생동물
		String wildAnmlList = svyComptResult.getWildAnmlList();
		if (wildAnmlList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(wildAnmlList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 야생동물 key -> 위도1, 경도1, 위도2, 경도2, 야생동물_유형, 야생동물_종류, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String anmlAt = dataMap.get(i).get("야생동물_유형").asText();
				String anmlType = dataMap.get(i).get("야생동물_종류").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("anmlAt", anmlAt);
				job.put("anmlType", anmlType);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("wildAnmlList", jsonArray);
		}
		
		// 사방시설 설치 여부
		String ecnrFcltyInstlList = svyComptResult.getEcnrFcltyInstlList();
		if (ecnrFcltyInstlList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(ecnrFcltyInstlList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 사방시설 설치 여부 key -> 위도1, 경도1, 위도2, 경도2, 사방시설설치_유무, 사방시설설치_유형, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String ecnrFcltyInstlAt = dataMap.get(i).get("사방시설설치_유무").asText();
				String ecnrFcltyInstlType = dataMap.get(i).get("사방시설설치_유형").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("ecnrFcltyInstlAt", ecnrFcltyInstlAt);
				job.put("ecnrFcltyInstlType", ecnrFcltyInstlType);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("ecnrFcltyInstlList", jsonArray);
		}
		
		// 사방시설 필요 여부
		String ecnrFcltyNcstyList = svyComptResult.getEcnrFcltyNcstyList();
		if (ecnrFcltyNcstyList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(ecnrFcltyNcstyList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 사방시설 필요 여부 key -> 위도1, 경도1, 위도2, 경도2, 사방시설필요_유무, 사방시설필요_유형, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String ecnrFcltyNcstyAt = dataMap.get(i).get("사방시설필요_유무").asText();
				String ecnrFcltyNcstyType = dataMap.get(i).get("사방시설필요_유형").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
				if(convertLon2 != null && !convertLon2.equals("")) {
					dmsformal.setDmsLon(convertLon2);
					job.put("lon2", dmsformal.getDmsLon());
				}
				if(convertLat1 != null && !convertLat1.equals("")) {
					dmsformal.setDmsLat(convertLat1);
					job.put("lat1", dmsformal.getDmsLat());
				}
				if(convertLat2 != null && !convertLat2.equals("")) {
					dmsformal.setDmsLat(convertLat2);
					job.put("lat2", dmsformal.getDmsLat());
				}
				job.put("ecnrFcltyNcstyAt", ecnrFcltyNcstyAt);
				job.put("ecnrFcltyNcstyType", ecnrFcltyNcstyType);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("ecnrFcltyNcstyList", jsonArray);
		}
		model.addAttribute("svyComptResult", svyComptResult);
		
		// 지도 start
		List<EgovMap> frdGeomList = vytFrdSvyComptService.selectVytFrdLineCntPnt(numberMap);
		
		
		if(!frdGeomList.isEmpty()) {
			
		HashMap<String, String> frdMap = new HashMap<String, String>();
			for(int i=0; i<frdGeomList.size(); i++) {
				String routetype = frdGeomList.get(i).get("routetype").toString();
				
				if(routetype.equals("기존노선")) {
					frdMap.put("frdExstnLne",frdGeomList.get(i).get("frdLne").toString());
					frdMap.put("frdLneCntPnt",frdGeomList.get(i).get("frdLneCntPnt").toString());
				}else if(routetype.equals("수정노선")) {
					frdMap.put("frdModLne",frdGeomList.get(i).get("frdLne").toString());
				}else if(routetype.equals("검토제안노선(1)")) {
					frdMap.put("frdRvwLne1",frdGeomList.get(i).get("frdLne").toString());
				}else if(routetype.equals("검토제안노선(2)")) {
					frdMap.put("frdRvwLne2",frdGeomList.get(i).get("frdLne").toString());
				}else if(routetype.equals("검토제안노선(3)")) {
					frdMap.put("frdRvwLne3",frdGeomList.get(i).get("frdLne").toString());
				}
			}
			
			model.addAttribute("frdMap", frdMap);
				
		}
		// 지도 end
		
		// point shp파일 다운로드 조회 start
		
		HashMap<String, Object> analParameterMap = new HashMap<String, Object>();
		analParameterMap.put("smid", sldNumber);
		
		List<AnalFileVO> analPntInfoList = vytFrdSvyComptService.selectAnalPntInfo(analParameterMap);
		// analPntList 
		HashMap<String, Object> pntGidList = new HashMap<String, Object>();
		
		if(analPntInfoList.size() > 0) {
			for(int i=0; i<analPntInfoList.size(); i++){
				String analType = analPntInfoList.get(i).getOrignlFileNm();
				int gid = analPntInfoList.get(i).getGid();
				
				pntGidList.put(analType, gid);
				
			}
		}
		
		model.addAttribute("pntGidList", pntGidList);
		
		// point shp파일 다운로드 조회 end
		
		// 조사완료 end
		
		return "sys/vyt/frd/sct/svyComptDetail";
	}
	
	/**
	 * 조사완료지를 수정화면로 이동한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/updateFrdSvyComptView.do")
	public String updateVytFrdSvyComptView(@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO, ModelMap model) throws Exception{
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", searchVO.getPageUnit());
		searchMap.put("schpageIndex", searchVO.getPageIndex());
		searchMap.put("schfrdType", searchVO.getFrdType());
		searchMap.put("schsvyYear", searchVO.getSvyYear());
		searchMap.put("schsvyMonth", searchVO.getSvyMonth());
		searchMap.put("schsvySd", searchVO.getSvySd());
		searchMap.put("schsvySgg", searchVO.getSvySgg());
		searchMap.put("schsvyEmd", searchVO.getSvyEmd());
		searchMap.put("schsvyRi", searchVO.getSvyRi());
		searchMap.put("schsvyId", searchVO.getSvyId());
		searchMap.put("schsvyUser", searchVO.getSvyUser());
		searchMap.put("schmstNm", searchVO.getMstNm());
		model.addAttribute("searchMap", searchMap);
		
		
		
		HashMap<String, String> numberMap = new HashMap<String, String>();
		
		numberMap.put("svyLabel", searchVO.getSvyLabel());
		numberMap.put("mstId", searchVO.getMstId());
		
		DmsFormalization dmsformal = new DmsFormalization();
		List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
		
		String sldId1 = null, sldId2 = null;
		for(int i=0; i<sldNumberList.size(); i++) {
			if(sldNumberList.get(i).get("routeCode").toString().equals("02")) {
				sldId2 = sldNumberList.get(i).get("sldId").toString();
			}else {
				sldId1 = sldNumberList.get(i).get("sldId").toString();
			}
		}
		
		String sldId = sldId2 != null ? sldId2 : sldId1; 
		
		
		// 조사완료 start
		VytFrdSvyComptVO svyComptResult = vytFrdSvyComptService.selectFrdSvyComptDetail(searchVO);
		svyComptResult.setSldId(sldId);
		
		if(svyComptResult != null) {
			
			// degree to dms
			String bp = svyComptResult.getBpy()+" "+svyComptResult.getBpx();
			String ep = svyComptResult.getEpy()+" "+svyComptResult.getEpx();
			
			String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
			Pattern pattern = Pattern.compile(regex);
			
			Matcher matcherBp = pattern.matcher(bp);
			Matcher matcherEp1 = pattern.matcher(ep);
			
			while (matcherBp.find()) {
			    String bpyD = matcherBp.group(1);
			    String bpyM = matcherBp.group(2);
			    String bpyS = matcherBp.group(3);
			    
			    String bpxD = matcherBp.group(5);
			    String bpxM = matcherBp.group(6);
			    String bpxS = matcherBp.group(7);
			    
			    if(bpyD != null && !bpyD.equals("")) {
			    	dmsformal.setDmsLatDeg(bpyD);
			    	svyComptResult.setBpyD(dmsformal.getDmsLatDeg());
			    }
			    if(bpyM != null && !bpyM.equals("")) {
			    	dmsformal.setDmsLatMin(bpyM);
			    	svyComptResult.setBpyM(dmsformal.getDmsLatMin());
			    }
			    if(bpyS != null && !bpyS.equals("")) {
			    	dmsformal.setDmsLatSec(bpyS);
			    	svyComptResult.setBpyS(dmsformal.getDmsLatSec());
			    }
			    if(bpxD != null && !bpxD.equals("")) {
			    	dmsformal.setDmsLonDeg(bpxD);
			    	svyComptResult.setBpxD(dmsformal.getDmsLonDeg());
			    }
			    if(bpxM != null && !bpxM.equals("")) {
			    	dmsformal.setDmsLonMin(bpxM);
			    	svyComptResult.setBpxM(dmsformal.getDmsLonMin());
			    }
			    if(bpxS != null && !bpxS.equals("")) {
			    	dmsformal.setDmsLonSec(bpxS);
			    	svyComptResult.setBpxS(dmsformal.getDmsLonSec());
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
					svyComptResult.setEpyD(dmsformal.getDmsLatDeg());
				}
				if(epy1M != null && !epy1M.equals("")) {
					dmsformal.setDmsLatMin(epy1M);
					svyComptResult.setEpyM(dmsformal.getDmsLatMin());
				}
				if(epy1S != null && !epy1S.equals("")) {
					dmsformal.setDmsLatSec(epy1S);
					svyComptResult.setEpyS(dmsformal.getDmsLatSec());
				}
				if(epx1D != null && !epx1D.equals("")) {
					dmsformal.setDmsLonDeg(epx1D);
					svyComptResult.setEpxD(dmsformal.getDmsLonDeg());
				}
				if(epx1M != null && !epx1M.equals("")) {
					dmsformal.setDmsLonMin(epx1M);
					svyComptResult.setEpxM(dmsformal.getDmsLonMin());
				}
				if(epx1S != null && !epx1S.equals("")) {
					dmsformal.setDmsLonSec(epx1S);
					svyComptResult.setEpxS(dmsformal.getDmsLonSec());
				}
			}
		}
		
		HashMap<String, Object> projMap = new HashMap<>();
		
		// 보호시설
		String safeFctList = svyComptResult.getSafeFctList();
		if (safeFctList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
		    JsonNode dataMap = ojm.readTree(safeFctList.toString());
		    for (int i = 0; i < dataMap.size(); i++) {
		    	// 보호시설 key -> 경도, 위도, 유형
		        String lon = dataMap.get(i).get("경도").asText();
		        String lat = dataMap.get(i).get("위도").asText();
		        String type = dataMap.get(i).get("유형").asText();

		        JSONObject job = new JSONObject();
		        
		        if(lon != null) projMap.put("lon1", lon);
		        if(lat != null) projMap.put("lat1", lat);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon = projList.get(0).get("lon1").toString();
		        String convertLat = projList.get(0).get("lat1").toString();
		        
		        // degree to dms
				String lonLat = convertLat+" "+convertLon;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat = pattern.matcher(lonLat);
				
				while (matcherLonLat.find()) {
				    String latD = matcherLonLat.group(1);
				    String latM = matcherLonLat.group(2);
				    String latS = matcherLonLat.group(3);
				    
				    String lonD = matcherLonLat.group(5);
				    String lonM = matcherLonLat.group(6);
				    String lonS = matcherLonLat.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS", dmsformal.getDmsLonSec());
				    }
				}
		        job.put("type", type);
		        
		        jsonArray.add(job);
		        obj = parser.parse(jsonArray.toString());
	        	jsonArray = (org.json.simple.JSONArray)obj;
		    }
		    model.addAttribute("safeFctList", jsonArray);
		}
		
		// 종단기울기
		String lonSlopeList = svyComptResult.getLonSlopeList();
		if (lonSlopeList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(lonSlopeList.toString());
			
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 종단기울기 key -> 위도1, 경도1, 위도2, 경도2, 기울기, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String slope = dataMap.get(i).get("기울기").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
		        
		        // degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
		        
				job.put("slope", slope);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
		        }
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
				
			}
			model.addAttribute("lonSlopeList", jsonArray);
		}
		
		// 산지경사
		String mtSlopeList = svyComptResult.getMtSlopeList();
		if (mtSlopeList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(mtSlopeList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 산지경사 key -> 위도1, 경도1, 위도2, 경도2, 경사도, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String slope = dataMap.get(i).get("경사도").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
		        
		        if(convertLon1 != null && !convertLon1.equals("")) {
					dmsformal.setDmsLon(convertLon1);
					job.put("lon1", dmsformal.getDmsLon());
				}
		        if(convertLon2 != null && !convertLon2.equals("")) {
		        	dmsformal.setDmsLon(convertLon2);
		        	job.put("lon2", dmsformal.getDmsLon());
		        }
		        if(convertLat1 != null && !convertLat1.equals("")) {
		        	dmsformal.setDmsLat(convertLat1);
		        	job.put("lat1", dmsformal.getDmsLat());
		        }
		        if(convertLat2 != null && !convertLat2.equals("")) {
		        	dmsformal.setDmsLat(convertLat2);
		        	job.put("lat2", dmsformal.getDmsLat());
		        }
		        // degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
		        
				job.put("slope", slope);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
		        }
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
				
			}
			model.addAttribute("mtSlopeList", jsonArray);
		}
		
		// 암반노출
		String rockExposList = svyComptResult.getRockExposList();
		if (rockExposList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			double totalRockExpos = 0;
			
			JsonNode dataMap = ojm.readTree(rockExposList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 암반노출 key -> 위도1, 경도1, 위도2, 경도2, 암반노출, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String rockExpos = dataMap.get(i).get("암반노출").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
		        // degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("rockExpos", rockExpos);
				
				if(rockExpos != null && !rockExpos.equals("") && StringUtils.isNumeric(rockExpos)) {
					totalRockExpos += Double.parseDouble(rockExpos);
				}
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
				
			}
			svyComptResult.setRockExposSum(Double.toString(totalRockExpos));
			
			model.addAttribute("rockExposList", jsonArray);
		}
		
		// 조림지
		String afrstList = svyComptResult.getAfrstList();
		if (afrstList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(afrstList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 조림지 key -> 위도1, 경도1, 위도2, 경도2, 조림지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String afrst = dataMap.get(i).get("조림지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("afrst", afrst);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList<>();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("afrstList", jsonArray);
		}
		
		// 벌채지
		String cuttingList = svyComptResult.getCuttingList();
		if (cuttingList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			String cuttingTotalAt = "무"; 
			
			JsonNode dataMap = ojm.readTree(cuttingList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 벌채지 key -> 위도1, 경도1, 위도2, 경도2, 벌채지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String cutting = dataMap.get(i).get("벌채지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("cutting", cutting);
				if(cutting.equals("유")) {	cuttingTotalAt = "유";	}
				model.addAttribute("cuttingTotalAt", cuttingTotalAt);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("cuttingList", jsonArray);
		}
		
		// 석력지
		String stmiList = svyComptResult.getStmiList();
		if (stmiList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			String stmiTotalAt = "무"; // 석력지 유무는 야장에서 따로 넘어오지 않기때문에 체크해서 넘겨줌.
			
			JsonNode dataMap = ojm.readTree(stmiList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 석력지 key -> 위도1, 경도1, 위도2, 경도2, 석력지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String stmi = dataMap.get(i).get("석력지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) { 
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("stmi", stmi);
				
				if(stmi != null && !stmi.equals("")) {	stmiTotalAt = "유";	}
				model.addAttribute("stmiTotalAt", stmiTotalAt);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("stmiList", jsonArray);
		}
		
		// 계류종류 및 현황
		String mrngKindList = svyComptResult.getMrngKind();
		if (mrngKindList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 대계류 합계
			int bigMrng1 = 0, bigMrng2 = 0, bigMrng3 = 0;
			
			// 중계류 합계
			int middleMrng1 = 0, middleMrng2 = 0, middleMrng3 = 0;
			
			// 소계류 합계
			int smallMrng1 = 0, smallMrng2 = 0, smallMrng3 = 0;
			
			JsonNode dataMap = ojm.readTree(mrngKindList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 계류종류 및 현황 key -> 위도1, 경도1, 위도2, 경도2, 대분류, 소분류, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String bigMrng = dataMap.get(i).get("대분류").asText();
				String smallMrng = dataMap.get(i).get("소분류").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("bigMrng", bigMrng);
				job.put("smallMrng", smallMrng);
				
				if(bigMrng.equals("대계류")) {
					bigMrng1 += 1;
					
					if(smallMrng.equals("상시천")) {
						bigMrng2 += 1;
					}else if(smallMrng.equals("건천")) {
						bigMrng3 += 1;
					}
					
				}else if(bigMrng.equals("중계류")) {
					middleMrng1 += 1;
					
					if(smallMrng.equals("상시천")) {
						middleMrng2 += 1;
					}else if(smallMrng.equals("건천")) {
						middleMrng3 += 1;
					}
				}else if(bigMrng.equals("소계류")) {
					smallMrng1 += 1;
					
					if(smallMrng.equals("상시천")) {
						smallMrng2 += 1;
					}else if(smallMrng.equals("건천")) {
						smallMrng3 += 1;
					}
				}
				
				String bigMrngTotal = Integer.toString(bigMrng1)+"/"+Integer.toString(bigMrng2)+"/"+Integer.toString(bigMrng3);
				String middleMrngTotal = Integer.toString(middleMrng1)+"/"+Integer.toString(middleMrng2)+"/"+Integer.toString(middleMrng3);
				String smallMrngTotal = Integer.toString(smallMrng1)+"/"+Integer.toString(smallMrng2)+"/"+Integer.toString(smallMrng3);
				
				HashMap<String, String> mrngTotalMap = new HashMap<String, String>();
				mrngTotalMap.put("bigMrngTotal", bigMrngTotal);
				mrngTotalMap.put("middleMrngTotal", middleMrngTotal);
				mrngTotalMap.put("smallMrngTotal", smallMrngTotal);
				
				mrngTotalMap.put("bigMrng1",Integer.toString(bigMrng1));
				mrngTotalMap.put("bigMrng2",Integer.toString(bigMrng2));
				mrngTotalMap.put("bigMrng3",Integer.toString(bigMrng3));
				
				mrngTotalMap.put("middleMrng1",Integer.toString(middleMrng1));
				mrngTotalMap.put("middleMrng2",Integer.toString(middleMrng2));
				mrngTotalMap.put("middleMrng3",Integer.toString(middleMrng3));
				
				mrngTotalMap.put("smallMrng1",Integer.toString(smallMrng1));
				mrngTotalMap.put("smallMrng2",Integer.toString(smallMrng2));
				mrngTotalMap.put("smallMrng3",Integer.toString(smallMrng3));
				
				
				model.addAttribute("mrngTotalMap", mrngTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			
			model.addAttribute("mrngKindList", jsonArray);
		}
		
		// 습지
		String wetLandList = svyComptResult.getWetLandList();
		if (wetLandList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(wetLandList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 습지 key -> 위도1, 경도1, 위도2, 경도2, 습지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String wetLand = dataMap.get(i).get("습지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("wetLand", wetLand);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("wetLandList", jsonArray);
		}
		
		
		// 묘지
		String cmtryList = svyComptResult.getCmtryList();
		if (cmtryList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 묘지 위치
			int cmtryLoc1 = 0, cmtryLoc2 = 0;
			
			// 묘지 관리
			int cmtryMng1 = 0, cmtryMng2 = 0;
			
			JsonNode dataMap = ojm.readTree(cmtryList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 묘지 key -> 위도1, 경도1, 위도2, 경도2, 묘지_유무, 묘지_위치, 묘지_관리, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String cmtryAt = dataMap.get(i).get("묘지_유무").asText();
				String cmtryLoc = dataMap.get(i).get("묘지_위치").asText();
				String cmtryMng = dataMap.get(i).get("묘지_관리").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("cmtryAt", cmtryAt);
				job.put("cmtryLoc", cmtryLoc);
				job.put("cmtryMng", cmtryMng);
				
				if(cmtryLoc.equals("노선 상부")) {
					cmtryLoc1 += 1;
				}else if(cmtryLoc.equals("노선 하부")) {
					cmtryLoc2 += 1;
				}
				
				if(cmtryMng.equals("관리O")) {
					cmtryMng1 += 1;
				}else if(cmtryMng.equals("관리X")) {
					cmtryMng2 += 1;
				}
				
				String cmtryLocTotal = Integer.toString(cmtryLoc1)+"/"+Integer.toString(cmtryLoc2);
				String cmtryMngTotal = Integer.toString(cmtryMng1)+"/"+Integer.toString(cmtryMng2);
				
				HashMap<String, String> cmtryTotalMap = new HashMap<String, String>();
				
				cmtryTotalMap.put("cmtryLoc1", Integer.toString(cmtryLoc1));
				cmtryTotalMap.put("cmtryLoc2", Integer.toString(cmtryLoc2));
				cmtryTotalMap.put("cmtryMng1", Integer.toString(cmtryMng1));
				cmtryTotalMap.put("cmtryMng2", Integer.toString(cmtryMng2));
				
				cmtryTotalMap.put("cmtryCnt", Integer.toString(dataMap.size()));
				cmtryTotalMap.put("cmtryLocTotal", cmtryLocTotal);
				cmtryTotalMap.put("cmtryMngTotal", cmtryMngTotal);
				
				model.addAttribute("cmtryTotalMap", cmtryTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			
			model.addAttribute("cmtryList", jsonArray);
		}

		// 용출수
		String eltnWaterList = svyComptResult.getEltnWaterList();
		if (eltnWaterList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(eltnWaterList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 용출수 key -> 위도1, 경도1, 위도2, 경도2, 용출수, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String eltnWater = dataMap.get(i).get("용출수").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("eltnWater", eltnWater);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("eltnWaterList", jsonArray);
		}
		
		// 연약지반
		String sofrtGrndList = svyComptResult.getSofrtGrndList();
		if (sofrtGrndList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(sofrtGrndList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 연약지반 key -> 위도1, 경도1, 위도2, 경도2, 연약지반, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String sofrtGrnd = dataMap.get(i).get("연약지반").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("sofrtGrnd", sofrtGrnd);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("sofrtGrndList", jsonArray);
		}
		
		
		// 붕괴우려지역
		String clpsCnrnList = svyComptResult.getClpsCnrnList();
		if (clpsCnrnList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 붕괴우려 사면
			int slope1 = 0, slope2 = 0, slope3 = 0, slope4 = 0, slope5 = 0;
			
			// 붕괴우려 계류
			int mtTrnt1 = 0, mtTrnt2 = 0, mtTrnt3 = 0, mtTrnt4 = 0, mtTrnt5 = 0;
			
			JsonNode dataMap = ojm.readTree(clpsCnrnList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 붕괴우려지역 key -> 위도1, 경도1, 위도2, 경도2, 붕괴우려_대분류, 붕괴우려_소분류, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String clpsCnrnBig = dataMap.get(i).get("붕괴우려_대분류").asText();
				String clpsCnrnSmall = dataMap.get(i).get("붕괴우려_소분류").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("clpsCnrnBig", clpsCnrnBig);
				job.put("clpsCnrnSmall", clpsCnrnSmall);
				
				if(clpsCnrnBig.equals("사면")) {
					slope1 += 1;
					
					if(clpsCnrnSmall.equals("침식")) {
						slope2 += 1;
					}else if(clpsCnrnSmall.equals("붕괴")) {
						slope3 += 1;
					}else if(clpsCnrnSmall.equals("포락")) {
						slope4 += 1;
					}else{
						slope5 += 1;
					}
					
				}else if(clpsCnrnBig.equals("계류")) {
					mtTrnt1 += 1;
					
					if(clpsCnrnSmall.equals("침식")) {
						mtTrnt2 += 1;
					}else if(clpsCnrnSmall.equals("붕괴")) {
						mtTrnt3 += 1;
					}else if(clpsCnrnSmall.equals("포락")) {
						mtTrnt4 += 1;
					}else{
						mtTrnt5 += 1;
					}
				}
				
				String slopeTotal = Integer.toString(slope1)+"/"+Integer.toString(slope2)+"/"+Integer.toString(slope3)+"/"+Integer.toString(slope4)+"/"+Integer.toString(slope5);
				String mtTrntTotal = Integer.toString(mtTrnt1)+"/"+Integer.toString(mtTrnt2)+"/"+Integer.toString(mtTrnt3)+"/"+Integer.toString(mtTrnt4)+"/"+Integer.toString(mtTrnt5);
				
				HashMap<String, String> clpsCnrnTotalMap = new HashMap<String, String>();
				
				clpsCnrnTotalMap.put("slope1", Integer.toString(slope1)) ;
				clpsCnrnTotalMap.put("slope2", Integer.toString(slope2)) ;
				clpsCnrnTotalMap.put("slope3", Integer.toString(slope3)) ;
				clpsCnrnTotalMap.put("slope4", Integer.toString(slope4)) ;
				clpsCnrnTotalMap.put("slope5", Integer.toString(slope5)) ;
				
				clpsCnrnTotalMap.put("mtTrnt1", Integer.toString(mtTrnt1)) ;
				clpsCnrnTotalMap.put("mtTrnt2", Integer.toString(mtTrnt2)) ;
				clpsCnrnTotalMap.put("mtTrnt3", Integer.toString(mtTrnt3)) ;
				clpsCnrnTotalMap.put("mtTrnt4", Integer.toString(mtTrnt4)) ;
				clpsCnrnTotalMap.put("mtTrnt5", Integer.toString(mtTrnt5)) ;
				
				clpsCnrnTotalMap.put("slopeTotal", slopeTotal);
				clpsCnrnTotalMap.put("mtTrntTotal", mtTrntTotal);
				
				model.addAttribute("clpsCnrnTotalMap", clpsCnrnTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			
			model.addAttribute("clpsCnrnList", jsonArray);
		}
		
		// 주요수종
		String maintreekndList = svyComptResult.getMaintreekndList();
		if (maintreekndList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(maintreekndList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 주요수종 key -> 위도1, 경도1, 위도2, 경도2, 주요수종, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String maintreeknd = dataMap.get(i).get("주요수종").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("maintreeknd", maintreeknd);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("maintreekndList", jsonArray);
		}
		
		// 주요식생
		String mainvegList = svyComptResult.getMainvegList();
		if (mainvegList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(mainvegList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 주요식생 key -> 위도1, 경도1, 위도2, 경도2, 주요식생, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String mainveg = dataMap.get(i).get("주요식생").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("mainveg", mainveg);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("mainvegList", jsonArray);
		}
		
		// 상수원 오염
		String wtrPltnList = svyComptResult.getWtrPltnList();
		if (wtrPltnList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(wtrPltnList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 상수원 오염 key -> 위도1, 경도1, 위도2, 경도2, 상수원오염, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String wtrPltn = dataMap.get(i).get("상수원오염").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("wtrPltn", wtrPltn);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("wtrPltnList", jsonArray);
		}
		
		// 훼손우려지
		String dmgCncrnList = svyComptResult.getDmgCncrnList();
		if (dmgCncrnList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(dmgCncrnList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 훼손우려지 key -> 위도1, 경도1, 위도2, 경도2, 훼손우려지, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String dmgCncrn = dataMap.get(i).get("훼손우려지").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("dmgCncrn", dmgCncrn);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("dmgCncrnList", jsonArray);
		}
		
		//산림재해(산불/병해충)
		String frstDsstrList = svyComptResult.getFrstDsstrList();
		if (frstDsstrList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			// 산불(건)
			int frstFireCnt = 0;

			// 병해충(건)
			int pestCnt = 0;

			// 기타(건)
			int etcCnt = 0;
			
			JsonNode dataMap = ojm.readTree(frstDsstrList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 산림재해 key -> 위도1, 경도1, 위도2, 경도2, 재해유형, 산림재해, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String dmgType = dataMap.get(i).get("재해유형").asText();
				String dmgAt = dataMap.get(i).get("산림재해").asText();//유무
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("dmgType", dmgType);
				job.put("dmgAt", dmgAt);
				
				if(dmgType.equals("산불")) {
					frstFireCnt += 1;
				}else if(dmgType.equals("병해충")) {
					pestCnt += 1;
				}else {
					etcCnt += 1;
				}
				
				HashMap<String, String> dmgTotalMap = new HashMap<String, String>();
				dmgTotalMap.put("frstFireCnt", Integer.toString(frstFireCnt));
				dmgTotalMap.put("pestCnt", Integer.toString(pestCnt));
				dmgTotalMap.put("etcCnt", Integer.toString(etcCnt));
				
				model.addAttribute("dmgTotalMap", dmgTotalMap);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("frstDsstrList", jsonArray);
		}
		
		// 야생동물
		String wildAnmlList = svyComptResult.getWildAnmlList();
		if (wildAnmlList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(wildAnmlList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 야생동물 key -> 위도1, 경도1, 위도2, 경도2, 야생동물_유형, 야생동물_종류, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String anmlAt = dataMap.get(i).get("야생동물_유형").asText();
				String anmlType = dataMap.get(i).get("야생동물_종류").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("anmlAt", anmlAt);
				job.put("anmlType", anmlType);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				List<String> photoRawList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
					
					String[] photoRawParts = convertPhoto.split("/");
					if (photoRawParts.length > 0) {
			            String result = photoRawParts[photoRawParts.length - 1];
			            photoRawList.add(result);
			        }
				}
				job.put("photo", photoList);
				job.put("photoRawList", photoRawList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("wildAnmlList", jsonArray);
		}
		
		// 사방시설 설치 여부
		String ecnrFcltyInstlList = svyComptResult.getEcnrFcltyInstlList();
		if (ecnrFcltyInstlList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(ecnrFcltyInstlList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 사방시설 설치 여부 key -> 위도1, 경도1, 위도2, 경도2, 사방시설설치_유무, 사방시설설치_유형, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String ecnrFcltyInstlAt = dataMap.get(i).get("사방시설설치_유무").asText();
				String ecnrFcltyInstlType = dataMap.get(i).get("사방시설설치_유형").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("ecnrFcltyInstlAt", ecnrFcltyInstlAt);
				job.put("ecnrFcltyInstlType", ecnrFcltyInstlType);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("ecnrFcltyInstlList", jsonArray);
		}
		
		// 사방시설 필요 여부
		String ecnrFcltyNcstyList = svyComptResult.getEcnrFcltyNcstyList();
		if (ecnrFcltyNcstyList != null) {
			
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			ObjectMapper ojm = new ObjectMapper();
			JSONParser parser = new JSONParser();
			Object obj;
			
			JsonNode dataMap = ojm.readTree(ecnrFcltyNcstyList.toString());
			for (int i = 0; i < dataMap.size(); i++) {
				JSONObject job = new JSONObject();
				
				// 사방시설 필요 여부 key -> 위도1, 경도1, 위도2, 경도2, 사방시설필요_유무, 사방시설필요_유형, 사진, 사진태그
				String lon1 = dataMap.get(i).get("경도1").asText();
				String lat1 = dataMap.get(i).get("위도1").asText();
				String lon2 = dataMap.get(i).get("경도2").asText();
				String lat2 = dataMap.get(i).get("위도2").asText();
				String ecnrFcltyNcstyAt = dataMap.get(i).get("사방시설필요_유무").asText();
				String ecnrFcltyNcstyType = dataMap.get(i).get("사방시설필요_유형").asText();
				
				if(lon1 != null && !lon1.equals("")) projMap.put("lon1", lon1);
		        if(lat1 != null && !lat1.equals("")) projMap.put("lat1", lat1);
		        if(lon2 != null && !lon2.equals("")) projMap.put("lon2", lon2);
		        if(lat2 != null && !lat2.equals("")) projMap.put("lat2", lat2);
		        
		        List<EgovMap> projList = vytFrdSvyComptService.selectVytFrdProjDMS(projMap);
		        
		        String convertLon1 = projList.get(0).get("lon1") != null ? projList.get(0).get("lon1").toString() : "";     
		        String convertLat1 = projList.get(0).get("lat1") != null ? projList.get(0).get("lat1").toString() : "";     
		        String convertLon2 = projList.get(0).get("lon2") != null ? projList.get(0).get("lon2").toString() : "";
		        String convertLat2 = projList.get(0).get("lat2") != null ? projList.get(0).get("lat2").toString() : "";
				
				// degree to dms
				String lonLat1 = convertLat1+" "+convertLon1;
				String lonLat2 = convertLat2+" "+convertLon2;
				
				String regex = "(\\d+)°(\\d+)'([\\d.]+)\"([NS])\\s+(\\d+)°(\\d+)'([\\d.]+)\"([EW])";
				Pattern pattern = Pattern.compile(regex);
				
				Matcher matcherLonLat1 = pattern.matcher(lonLat1);
				Matcher matcherLonLat2 = pattern.matcher(lonLat2);
				
				while (matcherLonLat1.find()) {
				    String latD = matcherLonLat1.group(1);
				    String latM = matcherLonLat1.group(2);
				    String latS = matcherLonLat1.group(3);
				    
				    String lonD = matcherLonLat1.group(5);
				    String lonM = matcherLonLat1.group(6);
				    String lonS = matcherLonLat1.group(7);
				    
				    if(latD != null && !latD.equals("")) {
				    	dmsformal.setDmsLatDeg(latD);
				    	job.put("latD1", dmsformal.getDmsLatDeg());
				    }
				    
				    if(latM != null && !latM.equals("")) {
				    	dmsformal.setDmsLatMin(latM);
				    	job.put("latM1", dmsformal.getDmsLatMin());
				    }
				    
				    if(latS != null && !latS.equals("")) {
				    	dmsformal.setDmsLatSec(latS);
				    	job.put("latS1", dmsformal.getDmsLatSec());
				    }
				    
				    if(lonD != null && !lonD.equals("")) {
				    	dmsformal.setDmsLonDeg(lonD);
				    	job.put("lonD1", dmsformal.getDmsLonDeg());
				    }
				    
				    if(lonM != null && !lonM.equals("")) {
				    	dmsformal.setDmsLonMin(lonM);
				    	job.put("lonM1", dmsformal.getDmsLonMin());
				    }
				    
				    if(lonS != null && !lonS.equals("")) {
				    	dmsformal.setDmsLonSec(lonS);
				    	job.put("lonS1", dmsformal.getDmsLonSec());
				    }
				}
				
				while (matcherLonLat2.find()) {
					String latD = matcherLonLat2.group(1);
					String latM = matcherLonLat2.group(2);
					String latS = matcherLonLat2.group(3);
					
					String lonD = matcherLonLat2.group(5);
					String lonM = matcherLonLat2.group(6);
					String lonS = matcherLonLat2.group(7);
					
					if(latD != null && !latD.equals("")) {
						dmsformal.setDmsLatDeg(latD);
						job.put("latD2", dmsformal.getDmsLatDeg());
					}
					
					if(latM != null && !latM.equals("")) {
						dmsformal.setDmsLatMin(latM);
						job.put("latM2", dmsformal.getDmsLatMin());
					}
					
					if(latS != null && !latS.equals("")) {
						dmsformal.setDmsLatSec(latS);
						job.put("latS2", dmsformal.getDmsLatSec());
					}
					
					if(lonD != null && !lonD.equals("")) {
						dmsformal.setDmsLonDeg(lonD);
						job.put("lonD2", dmsformal.getDmsLonDeg());
					}
					
					if(lonM != null && !lonM.equals("")) {
						dmsformal.setDmsLonMin(lonM);
						job.put("lonM2", dmsformal.getDmsLonMin());
					}
					
					if(lonS != null && !lonS.equals("")) {
						dmsformal.setDmsLonSec(lonS);
						job.put("lonS2", dmsformal.getDmsLonSec());
					}
				}
				job.put("ecnrFcltyNcstyAt", ecnrFcltyNcstyAt);
				job.put("ecnrFcltyNcstyType", ecnrFcltyNcstyType);
				
				String[] photoArray = ojm.treeToValue(dataMap.get(i).get("사진"), String[].class);
				List<String> photoList = new ArrayList();
				for (String photo : photoArray) {
					String convertPhoto = photo.substring("gimg:///".length()).replaceAll("\"", "");
					photoList.add(convertPhoto);
				}
				job.put("photo", photoList);
				jsonArray.add(job);
				obj = parser.parse(jsonArray.toString());
				jsonArray = (org.json.simple.JSONArray)obj;
			}
			model.addAttribute("ecnrFcltyNcstyList", jsonArray);
		}
		model.addAttribute("svyComptResult", svyComptResult);
		
		// 지도 start
		List<EgovMap> frdGeomList = vytFrdSvyComptService.selectVytFrdLineCntPnt(numberMap);
		
		
		if(!frdGeomList.isEmpty()) {
			
		HashMap<String, String> frdMap = new HashMap<String, String>();
			for(int i=0; i<frdGeomList.size(); i++) {
				String routetype = frdGeomList.get(i).get("routetype").toString();
				
				if(routetype.equals("기존노선")) {
					frdMap.put("frdExstnLne",frdGeomList.get(i).get("frdLne").toString());
					frdMap.put("frdLneCntPnt",frdGeomList.get(i).get("frdLneCntPnt").toString());
				}else if(routetype.equals("수정노선")) {
					frdMap.put("frdModLne",frdGeomList.get(i).get("frdLne").toString());
				}else if(routetype.equals("검토제안노선(1)")) {
					frdMap.put("frdRvwLne1",frdGeomList.get(i).get("frdLne").toString());
				}else if(routetype.equals("검토제안노선(2)")) {
					frdMap.put("frdRvwLne2",frdGeomList.get(i).get("frdLne").toString());
				}else if(routetype.equals("검토제안노선(3)")) {
					frdMap.put("frdRvwLne3",frdGeomList.get(i).get("frdLne").toString());
				}
			}
			
			model.addAttribute("frdMap", frdMap);
				
		}
		
		// 지도 end
		// 조사완료 end
		
		
		// 수정 start
		// 관할 조회
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(svyComptResult.getSvySd() != null && !svyComptResult.getSvySd().trim().isEmpty()) {
			adminVO.setSdNm(svyComptResult.getSvySd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(svyComptResult.getSvySgg() != null && !svyComptResult.getSvySgg().trim().isEmpty()) {
			adminVO.setSggNm(svyComptResult.getSvySgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(svyComptResult.getSvyEmd() != null && !svyComptResult.getSvyEmd().trim().isEmpty()) {
			adminVO.setEmdNm(svyComptResult.getSvyEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		
		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		
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
		if(svyComptResult.getCompentauth() != null) {
			
			if(svyComptResult.getCompentauth().contains("부청")) {
				if(svyComptResult.getCompentauth().equals("북부청")) {
					compentauth = "REG18";
				}else if(svyComptResult.getCompentauth().equals("동부청")) {
					compentauth = "REG19";
				}else if(svyComptResult.getCompentauth().equals("남부청")) {
					compentauth = "REG20";
				}else if(svyComptResult.getCompentauth().equals("중부청")) {
					compentauth = "REG21";
				}else if(svyComptResult.getCompentauth().equals("서부청")) {
					compentauth = "REG22";
				}else {
					compentauth = svyComptResult.getCompentauth().toString();
				}
				
				// 관할 조회
				vo.setCodeId("FEI167");
				List<?> regionCodeList = cmmUseService.selectCmmCodeDetail(vo);
				model.addAttribute("regionCodeList", regionCodeList);
				
				vo.setCodeId(compentauth);
				List<CmmnDetailCode> region2_svyComptResult = cmmUseService.selectRegionDetail(vo);
				model.addAttribute("region2CodeList", region2_svyComptResult);//관할2목록코드목록
				
				compentauthType.put("compentauthType", "국유림");
			}else {
				//시도코드 조회		
				List<AdministZoneVO> regionCodeList = administZoneService.selectAdministZoneCtprvn();
				model.addAttribute("regionCodeList",regionCodeList);
				
				//시군구코드 조회
				if(svyComptResult.getCompentauth() != null && !svyComptResult.getCompentauth().trim().isEmpty()) {
					adminVO.setSdNm(svyComptResult.getCompentauth());		
					List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
					model.addAttribute("region2CodeList",sggCodeList);
				}
				
				compentauthType.put("compentauthType", "민유림");
			}
		}
		model.addAttribute("compentauthType",compentauthType);
		
		// 수정 end
		
		
		return "sys/vyt/frd/sct/svyComptUpdt";
	}
	
	/**
	 * 관계지적 목록 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectOtherAddrPopup.do")	
	public String selectOtherAddrPopup(ModelMap model, HttpServletRequest req) throws Exception {
		
		String smid = req.getParameter("smid");
		
		List<VytFrdStripLandVO> parcelList = vytFrdStripLandService.selectParcelList(smid);
		model.addAttribute("parcelList", parcelList);
		
		return "sys/vyt/frd/sld/stripLandOtherAddrPopup";		
	}
	
	/**
	 * 대상지 추가등록 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/insertStripLandViewAddPopup.do")	
	public String insertStripLandViewAddPopup(
			@RequestParam(value="id") String id,
			@RequestParam(value="sldlabel") String sldlabel,
			@RequestParam(value="mstId") String mstId,
			@RequestParam(value="frdtype") String frdtype,
			@RequestParam(value="compentauth") String compentauth,
			ModelMap model) throws Exception {
		
		model.addAttribute("id",id);
		model.addAttribute("sldlabel",sldlabel);
		model.addAttribute("mstId",mstId);
		model.addAttribute("frdtype",frdtype);
		model.addAttribute("compentauth",compentauth);
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		// 관할청
		comVO.setCodeId("FEI167");
		List<CmmnDetailCode>  compentauth_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("compentauth_result", compentauth_result);
		
		// 노선종류
		comVO.setCodeId("FEI169");
		List<CmmnDetailCode>  route_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("route_result", route_result);
		
		return "sys/vyt/frd/sct/svyComptSldRegist";		
	}
	
	/**
	 * 대상지 추가등록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/insertStripLandViewAdd.do")	
	public ModelAndView insertStripLandViewAdd(
			@ModelAttribute("stripLandVO") VytFrdStripLandVO stripLandVO,
			ModelMap model, @RequestParam(value = "file") MultipartFile mFile
			) throws Exception {
		ModelAndView mv = null;
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			JSONObject results = null;
			
			String id = stripLandVO.getId();

			//등록하는 노선 타입 받아오기
			String routeType = stripLandVO.getRoutetype().toString();
			String routeCode = null;
			
			if(routeType.equals("기존노선")) {
				routeCode = "01";
			}else if(routeType.equals("수정노선")) {
				routeCode = "02";
			}else if(routeType.equals("검토제안노선(1)")) {
				routeCode = "03";
			}else if(routeType.equals("검토제안노선(2)")) {
				routeCode = "04";
			}else if(routeType.equals("검토제안노선(3)")) {
				routeCode = "05";
			}
			
			List<VytFrdSvyComptVO> vo = vytFrdSvyComptService.selectCheckLines(stripLandVO);
			String existLineNb = null;
			for(int i=0; i<vo.size(); i++) {
				if(vo.get(i).getSmid() != null && !vo.get(i).getSmid().equals("")) {
					existLineNb = vo.get(i).getSmid();
				}
			}
			
			try {
				List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
				if (list.size() > 0) {
					EgovFormBasedFileVo fileVO = list.get(0);
					results = vytFrdStripLandService.insertFrdSvySld(fileVO);
					mv.addObject("status",results.get("status"));
		    		mv.addObject("message", results.get("message"));
		    		
		    		if(existLineNb != null) {
		    			vytFrdSvyComptService.deleteExistLines(existLineNb);
		    		}
		    		
				}else {
					mv.addObject("status","fail");
		    		mv.addObject("message", "등록파일을 찾을 수  없습니다.\n관리자에게 문의하세요.");
				}
				
				List<VytFrdStripLandVO> noSldIdList = vytFrdStripLandService.selectNoSldId();
				List<Integer> smidList = new ArrayList<Integer>();
				for(int i=0; i<noSldIdList.size(); i++) {
					String smid = noSldIdList.get(i).getSmid();
					smidList.add(Integer.parseInt(smid));
				}
				
				commandMap.put("id", id); //SLD_ID
				commandMap.put("smidList", smidList);
				commandMap.put("routetype", stripLandVO.getRoutetype().toString());
				commandMap.put("compentauth", stripLandVO.getCompentauth().toString());
 				commandMap.put("sldlabel", stripLandVO.getSldlabel().toString());
				commandMap.put("mstId", stripLandVO.getMstId().toString());
				commandMap.put("frdtype", stripLandVO.getFrdtype().toString());
				
				commandMap.put("routeCode", routeCode);
				
				if(smidList.size() > 0) {
					vytFrdSvyComptService.updateExtraShp(commandMap);
					
					//수정노선 등록시 tf_feis_frd_svycompt에 지오메트리 최신화 
					if(routeType.equals("수정노선")) {
		    			vytFrdSvyComptService.updateNewGeomInfo(stripLandVO);
		    		}
				}else {
					mv.addObject("status","fail");
					mv.addObject("message", "에러가 발생했습니다.\n관리자에게 문의하세요.");
				}
				
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
	 * 조사완료지를 수정한다.
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/vyt/frd/sct/updateFrdSvyCompt.do")
    public ModelAndView updateVytFrdSvyCompt(@ModelAttribute("vytFrdSvyComptVO") VytFrdSvyComptVO svyComptVO
    		, BindingResult bindingResult
    		, Model model, HttpServletRequest req
    		, MultipartHttpServletRequest multiRequest
    		, @RequestParam("analAt") String analAt
    	) throws Exception {
		beanValidator.validate(svyComptVO, bindingResult);
		long start = System.currentTimeMillis();
		ModelAndView mv = null;
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			 mv = new ModelAndView("jsonView");
			try {
				// 보호시설
				svyComptVO.setSafeFctList(svyComptVO.getSafeFctList().replaceAll("&quot;", "\""));
				// 종단기울기
				svyComptVO.setLonSlopeList(svyComptVO.getLonSlopeList().replaceAll("&quot;", "\""));
	 			// 산지경사
				svyComptVO.setMtSlopeList(svyComptVO.getMtSlopeList().replaceAll("&quot;", "\""));
				// 암반노출
				svyComptVO.setRockExposList(svyComptVO.getRockExposList().replaceAll("&quot;", "\""));
				// 조림지
				svyComptVO.setAfrstList(svyComptVO.getAfrstList().replaceAll("&quot;", "\""));
				// 벌채지
				svyComptVO.setCuttingList(svyComptVO.getCuttingList().replaceAll("&quot;", "\""));
				// 석력지
				svyComptVO.setStmiList(svyComptVO.getStmiList().replaceAll("&quot;", "\""));
				// 계류종류 및 현황
				svyComptVO.setMrngKind(svyComptVO.getMrngKind().replaceAll("&quot;", "\""));
				// 습지
				svyComptVO.setWetLandList(svyComptVO.getWetLandList().replaceAll("&quot;", "\""));
				// 묘지
				svyComptVO.setCmtryList(svyComptVO.getCmtryList().replaceAll("&quot;", "\""));
				// 용출수
				svyComptVO.setEltnWaterList(svyComptVO.getEltnWaterList().replaceAll("&quot;", "\""));
				// 연약지반
				svyComptVO.setSofrtGrndList(svyComptVO.getSofrtGrndList().replaceAll("&quot;", "\""));
				// 붕괴우려지역
				svyComptVO.setClpsCnrnList(svyComptVO.getClpsCnrnList().replaceAll("&quot;", "\""));
				// 주요수종
				svyComptVO.setMaintreekndList(svyComptVO.getMaintreekndList().replaceAll("&quot;", "\""));
				// 주요식생
				svyComptVO.setMainvegList(svyComptVO.getMainvegList().replaceAll("&quot;", "\""));
				// 상수원 오염
				svyComptVO.setWtrPltnList(svyComptVO.getWtrPltnList().replaceAll("&quot;", "\""));
				// 과다한 훼손우려지
				svyComptVO.setDmgCncrnList(svyComptVO.getDmgCncrnList().replaceAll("&quot;", "\""));
				// 산불 / 병해충 (=산림재해)
				svyComptVO.setFrstDsstrList(svyComptVO.getFrstDsstrList().replaceAll("&quot;", "\""));
				// 야생동물
				svyComptVO.setWildAnmlList(svyComptVO.getWildAnmlList().replaceAll("&quot;", "\""));
				// 사방시설 설치 여부
				svyComptVO.setEcnrFcltyInstlList(svyComptVO.getEcnrFcltyInstlList().replaceAll("&quot;", "\""));
				// 사방시설 필요 여부
				svyComptVO.setEcnrFcltyNcstyList(svyComptVO.getEcnrFcltyNcstyList().replaceAll("&quot;", "\""));
				
				
				String compentauth = null;
				if(svyComptVO.getCompentauth() != null) {
					if(svyComptVO.getCompentauth().equals("REG18")) {
						compentauth = "북부청";
					}else if(svyComptVO.getCompentauth().equals("REG19")) {
						compentauth = "동부청";
					}else if(svyComptVO.getCompentauth().equals("REG20")) {
						compentauth = "남부청";
					}else if(svyComptVO.getCompentauth().equals("REG21")) {
						compentauth = "중부청";
					}else if(svyComptVO.getCompentauth().equals("REG22")) {
						compentauth = "서부청";
					}else compentauth = svyComptVO.getCompentauth().toString();
				}
				svyComptVO.setCompentauth(compentauth);
				
				vytFrdSvyComptService.updateFrdSvyCompt(svyComptVO);
				
				// 저장 후 슈퍼맵 분석 진행
				
				if(analAt.equals("Y")) {
					HashMap<String, String> numberMap = new HashMap<String, String>();
					
					numberMap.put("svyLabel", svyComptVO.getSvyId());
					numberMap.put("mstId", svyComptVO.getMstId());
					
					List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
					String smid1 = null, sldId1 = null;
					String smid2 = null, sldId2 = null;
					for(int i=0; i<sldNumberList.size(); i++) {
						if(sldNumberList.get(i).get("routeCode").toString().equals("02")) {
							smid2 = sldNumberList.get(i).get("smid").toString();
							sldId2 = sldNumberList.get(i).get("sldId").toString();
						}else if(sldNumberList.get(i).get("routeCode").toString().equals("01")){
							smid1 = sldNumberList.get(i).get("smid").toString();
							sldId1 = sldNumberList.get(i).get("sldId").toString();
						}
						
					}
					String smid = smid2 != null ? smid2 : smid1; 
					String sldId = sldId2 != null ? sldId2 : sldId1; 
					
					HashMap<String, Object> commandMap = new HashMap<>();
					commandMap.put("smid", smid);
					commandMap.put("smid1", smid1);
					commandMap.put("sldId", sldId);
					commandMap.put("fileExtsn", "jpg");
					commandMap.put("pageType", "svyCompt");
					
					if(smid2 != null) {
						commandMap.put("routeCode", "02");
					}else {
						commandMap.put("routeCode", "01");
					}
					
					List<EgovMap> bufferSizeList = vytFrdSvyComptService.selectBufferSize(commandMap);
					
					HashMap<String, String> analMap = new HashMap<String, String>();
					String bufferSize = null;
					
					if(bufferSizeList.size() == 0) {
						bufferSize = "200";
						String[] analList = {"[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]"};
						commandMap.put("analList", Arrays.toString(analList));
						commandMap.put("bufferSize", bufferSize);
						vytFrdStripLandService.createVytFrdAnalData(commandMap);
					}else {
						String[] analList = new String[1];
						
						for(int i = 0; i < bufferSizeList.size(); i++) {
						    String analType = bufferSizeList.get(i).get("analType").toString();
						    
						    String analCode = null;
						    
					    	if(analType.equals("destloc")) {
					    		bufferSize = "";
					    		analCode = "1";
					    	}else if(analType.equals("admin")) {
					    		bufferSize = "";
					    		analCode = "2";
					    	}else if(analType.equals("rllgstr")) {
					    		bufferSize = "";
					    		analCode = "3";
					    	}else if(analType.equals("culture")) {
					    		bufferSize = "";
					    		analCode = "4";
					    	}else if(analType.equals("frtp")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "5";
					    	}else if(analType.equals("fror")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "6";
					    	}else if(analType.equals("agcls")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "7";
					    	}else if(analType.equals("dmcls")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "8";
					    	}else if(analType.equals("dnst")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "9";
					    	}else if(analType.equals("koftr")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "10";
					    	}else if(analType.equals("slope")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "11";
					    	}else if(analType.equals("aspect")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "12";
					    	}else if(analType.equals("dem")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "13";
					    	}else if(analType.equals("soil")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "14";
					    	}else if(analType.equals("geology")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "15";
					    	}else if(analType.equals("prrck")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "16";
					    	}else if(analType.equals("accma")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "17";
					    	}else if(analType.equals("rock")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "18";
					    	}else if(analType.equals("nature")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "19";
					    	}else if(analType.equals("animal")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "20";
					    	}else if(analType.equals("forestFire")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "21";
					    	}else if(analType.equals("landslide")) {
					    		if(bufferSizeList.get(i).get("bufferSize") != null) {
					    			bufferSize = bufferSizeList.get(i).get("bufferSize").toString();
					    		}else {
					    			bufferSize = "200";
					    		}
					    		analCode = "22";
					    	}
						    analMap.put(analCode, bufferSize);
						    
						}
						
						for(int i=0; i<22; i++) {
							String originBufferSize = "200";
							
							if(!analMap.containsKey("1")) {
								analMap.put("1", "");
							}else if(!analMap.containsKey("2")) {
								analMap.put("2", "");
							}else if(!analMap.containsKey("3")) {
								analMap.put("3", "");
							}else if(!analMap.containsKey("4")) {
								analMap.put("4", "");
							}else if(!analMap.containsKey("5")) {
								analMap.put("5", originBufferSize);
							}else if(!analMap.containsKey("6")) {
								analMap.put("6", originBufferSize);
							}else if(!analMap.containsKey("7")) {
								analMap.put("7", originBufferSize);
							}else if(!analMap.containsKey("8")) {
								analMap.put("8", originBufferSize);
							}else if(!analMap.containsKey("9")) {
								analMap.put("9", originBufferSize);
							}else if(!analMap.containsKey("10")) {
								analMap.put("10", originBufferSize);
							}else if(!analMap.containsKey("11")) {
								analMap.put("11", originBufferSize);
							}else if(!analMap.containsKey("12")) {
								analMap.put("12", originBufferSize);
							}else if(!analMap.containsKey("13")) {
								analMap.put("13", originBufferSize);
							}else if(!analMap.containsKey("14")) {
								analMap.put("14", originBufferSize);
							}else if(!analMap.containsKey("15")) {
								analMap.put("15", originBufferSize);
							}else if(!analMap.containsKey("16")) {
								analMap.put("16", originBufferSize);
							}else if(!analMap.containsKey("17")) {
								analMap.put("17", originBufferSize);
							}else if(!analMap.containsKey("18")) {
								analMap.put("18", originBufferSize);
							}else if(!analMap.containsKey("19")) {
								analMap.put("19", originBufferSize);
							}else if(!analMap.containsKey("20")) {
								analMap.put("20", originBufferSize);
							}else if(!analMap.containsKey("21")) {
								analMap.put("21", originBufferSize);
							}else if(!analMap.containsKey("22")) {
								analMap.put("22", originBufferSize);
							}
						}
						
						for (String key : analMap.keySet()) {
							analList[0] = key;
							bufferSize = analMap.get(key);
							
							commandMap.put("analList", Arrays.toString(analList));
							commandMap.put("bufferSize", bufferSize);
							vytFrdStripLandService.createVytFrdAnalData(commandMap);
						}
						long end = System.currentTimeMillis();
						LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
					}
				}
				mv.addObject("status","success");
				model.addAttribute("message", egovMessageSource.getMessage("success.common.update"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
		}
		return mv;
    }
	
	/**
	 * 대상지 분석 파일 일괄 다운로드
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @implNote 수정노선있으면 수정노선으로 파일 다운로드
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectDownloadAnalAll.do")
	public void selectDownloadAnalAll(
			@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		
		HashMap<String, String> numberMap = new HashMap<String, String>();
		
		numberMap.put("svyLabel", searchVO.getSvyId());
		numberMap.put("mstId", searchVO.getMstId());
		
		List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
		String smid1 = null, sldId1 = null;
		String smid2 = null, sldId2 = null;
		for(int i=0; i<sldNumberList.size(); i++) {
			if(sldNumberList.get(i).get("routeCode").toString().equals("02")) {
				smid2 = sldNumberList.get(i).get("smid").toString();
				sldId2 = sldNumberList.get(i).get("sldId").toString();
			}else if(sldNumberList.get(i).get("routeCode").toString().equals("01")) {
				smid1 = sldNumberList.get(i).get("smid").toString();
				sldId1 = sldNumberList.get(i).get("sldId").toString();
			}
			
		}
		String smid = smid2 != null ? smid2 : smid1; 
		String sldId = sldId2 != null ? sldId2 : sldId1; 
		
		
		searchVO.setSmid(smid);
		searchVO.setSldId(sldId);
		
		if(isAuthenticated) {
			searchVO.setSvyUser(loginVO.getId());
			AnalFileVO vo = vytFrdSvyComptService.downloadAnalAll(searchVO);

			if(vo.getFileStreCours().length() > 0) {
				String saveFileNm = vo.getFileStreCours()+File.separator+loginVO.getId()+File.separator+vo.getAnalId()+".".concat(vo.getFileExtsn());
				
				File uFile = new File(saveFileNm);
				long fSize = uFile.length();
				if (fSize > 0) {
					String mimetype = "application/x-msdownload";
					
					String userAgent = request.getHeader("User-Agent");
					HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
					if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
						mimetype = "application/x-stuff";
					}

					String contentDisposition = EgovBrowserUtil.getDisposition(vo.getAnalId()+".".concat(vo.getFileExtsn()),userAgent,"UTF-8");
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
						//EgovFileUtil.rm(vo.getFileStreCours()+File.separator+loginVO.getId());
						EgovResourceCloseHelper.close(in, out);
						FileUtils.deleteDirectory(new File(vo.getFileStreCours()+File.separator+loginVO.getId()));
					}
				}
			}
		}
	}
	
	/**
	 * 조사완료지를 삭제한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/deleteFrdSvyCompt.do")
	public ModelAndView deleteFrdSvyCompt(VytFrdSvyComptVO svyComptVO,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			HashMap<String, String> numberMap = new HashMap<String, String>();
			numberMap.put("svyLabel", svyComptVO.getSvyLabel());
			numberMap.put("mstId", svyComptVO.getMstId());
			List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
			
			String sldNumber1 = null, sldNumber2 = null, sldNumber3 = null, sldNumber4 = null, sldNumber5 = null;
			for(int i=0; i<sldNumberList.size(); i++) {
				if(sldNumberList.get(i).get("routeCode").equals("02")) {
					sldNumber2 = sldNumberList.get(i).get("smid").toString();
					svyComptVO.setSmid(sldNumber2);
					// 분석 파일 저장은 02번만 되기 때문에 
					vytFrdSvyComptService.deleteFrdAnalFile(svyComptVO);
				}else if(sldNumberList.get(i).get("routeCode").equals("03")) {
					sldNumber3 = sldNumberList.get(i).get("smid").toString();
					svyComptVO.setSmid(sldNumber3);
				}else if(sldNumberList.get(i).get("routeCode").equals("04")) {
					sldNumber4 = sldNumberList.get(i).get("smid").toString();
					svyComptVO.setSmid(sldNumber4);
				}else if(sldNumberList.get(i).get("routeCode").equals("05")) {
					sldNumber5 = sldNumberList.get(i).get("smid").toString();
					svyComptVO.setSmid(sldNumber5);
				}
				
				if(!sldNumberList.get(i).get("routeCode").equals("01")) {
					// 아래 코드로 조사완료 데이터 삭제 시 기존노선 제외 한 나머지 노선 테이블에서 제거
					vytFrdSvyComptService.deleteFrdLines(svyComptVO);
				}
			}
			
			vytFrdSvyComptService.deleteFrdSvyCompt(svyComptVO);
			
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
     * 관할청을 보여준다.
     * @param adminVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectCompentAuth.do")
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
	
	/**
     * 현장사진을 일괄 다운로드한다.
     * @param svyComptVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectDownloadPhotoListZip.do")
	public void selectDownloadPhotoListZip(VytFrdSvyComptVO svyComptVO, HttpServletResponse res) throws Exception{
		long beforeTime = System.currentTimeMillis();
		
		
		VytFrdSvyComptVO result = vytFrdSvyComptService.selectDownloadPhotoList(svyComptVO);
		
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
 		
 		String pattern = null;
 		String typeNm = null;
 		
 		
 		List<String> fileList = new ArrayList<String>();
 		
 		// 종단기울기
 		if(!result.getLonSlopePhoto().equals("[]")) {
 			typeNm = "종단기울기";
 			fileList.add(typeNm);
 		}
 		// 산지경사
 		if(!result.getMtSlopePhoto().equals("[]")) {
 			typeNm = "산지경사";
 			fileList.add(typeNm);
 		}
 		// 암반노출
 		if(!result.getRockExposPhoto().equals("[]")) {
 			typeNm = "암반노출";
 			fileList.add(typeNm);
 		}
 		// 조림지
 		if(!result.getAfrstPhoto().equals("[]")) {
 			typeNm = "조림지";
 			fileList.add(typeNm);
 		}
 		// 벌채지
 		if(!result.getCuttingPhoto().equals("[]")) {
 			typeNm = "벌채지";
 			fileList.add(typeNm);
 		}
 		// 석력지
 		if(!result.getStmiPhoto().equals("[]")) {
 			typeNm = "석력지";
 			fileList.add(typeNm);
 		}
 		// 계류종류및현황
 		if(!result.getMrngKindPhoto().equals("[]")) {
 			typeNm = "계류종류및현황";
 			fileList.add(typeNm);
 		}
 		// 습지
 		if(!result.getWetLandPhoto().equals("[]")) {
 			typeNm = "습지";
 			fileList.add(typeNm);
 		}
 		// 묘지
 		if(!result.getCmtryPhoto().equals("[]")) {
 			typeNm = "묘지";
 			fileList.add(typeNm);
 		}
 		// 용출수
 		if(!result.getEltnWaterPhoto().equals("[]")) {
 			typeNm = "용출수";
 			fileList.add(typeNm);
 		}
 		// 연약지반
 		if(!result.getSofrtGrndPhoto().equals("[]")) {
 			typeNm = "연약지반";
 			fileList.add(typeNm);
 		}
 		// 붕괴우려지역
 		if(!result.getClpsCnrnPhoto().equals("[]")) {
 			typeNm = "붕괴우려지역";
 			fileList.add(typeNm);
 		}
 		// 주요수종
 		if(!result.getMaintreekndPhoto().equals("[]")) {
 			typeNm = "주요수종";
 			fileList.add(typeNm);
 		}
 		// 주요식생
 		if(!result.getMainvegPhoto().equals("[]")) {
 			typeNm = "주요식생";
 			fileList.add(typeNm);
 		}
 		// 상수원오염
 		if(!result.getWtrPltnPhoto().equals("[]")) {
 			typeNm = "상수원오염";
 			fileList.add(typeNm);
 		}
 		// 과다한 훼손우려지
 		if(!result.getDmgCncrnPhoto().equals("[]")) {
 			typeNm = "훼손우려지";
 			fileList.add(typeNm);
 		}
 		// 산불/병해충
 		if(!result.getFrstDsstrPhoto().equals("[]")) {
 			typeNm = "산불/병해충";
 			fileList.add(typeNm);
 		}
 		// 야생동물
 		if(!result.getWildAnmlPhoto().equals("[]")) {
 			typeNm = "야생동물";
 			fileList.add(typeNm);
 		}
 		// 사방시설 설치 여부
 		if(!result.getEcnrFcltyInstlPhoto().equals("[]")) {
 			typeNm = "사방시설설치";
 			fileList.add(typeNm);
 		}
 		// 사방시설 필요 여부
 		if(!result.getEcnrFcltyNcstyPhoto().equals("[]")) {
 			typeNm = "사방시설필요";
 			fileList.add(typeNm);
 		}
 		
 		for(int k=0; k<fileList.size(); k++) {
 			
 			typeNm = fileList.get(k).toString();
 			
 			if(typeNm.equals("종단기울기")) {
 				pattern = result.getLonSlopePhoto();
 			}else if(typeNm.equals("산지경사")) {
 				pattern = result.getMtSlopePhoto();
 			}else if(typeNm.equals("암반노출")) {
 				pattern = result.getRockExposPhoto();
 			}else if(typeNm.equals("조림지")) {
 				pattern = result.getAfrstPhoto();
 			}else if(typeNm.equals("벌채지")) {
 				pattern = result.getCuttingPhoto();
 			}else if(typeNm.equals("석력지")) {
 				pattern = result.getStmiPhoto();
 			}else if(typeNm.equals("계류종류및현황")) {
 				pattern = result.getMrngKindPhoto();
 			}else if(typeNm.equals("습지")) {
 				pattern = result.getWetLandPhoto();
 			}else if(typeNm.equals("묘지")) {
 				pattern = result.getCmtryPhoto();
 			}else if(typeNm.equals("용출수")) {
 				pattern = result.getEltnWaterPhoto();
 			}else if(typeNm.equals("연약지반")) {
 				pattern = result.getSofrtGrndPhoto();
 			}else if(typeNm.equals("붕괴우려지역")) {
 				pattern = result.getClpsCnrnPhoto();
 			}else if(typeNm.equals("주요수종")) {
 				pattern = result.getMaintreekndPhoto();
 			}else if(typeNm.equals("주요식생")) {
 				pattern = result.getMainvegPhoto();
 			}else if(typeNm.equals("상수원오염")) {
 				pattern = result.getWtrPltnPhoto();
 			}else if(typeNm.equals("훼손우려지")) {
 				pattern = result.getDmgCncrnPhoto();
 			}else if(typeNm.equals("산불/병해충")) {
 				pattern = result.getFrstDsstrPhoto();
 			}else if(typeNm.equals("야생동물")) {
 				pattern = result.getWildAnmlPhoto();
 			}else if(typeNm.equals("사방시설설치")) {
 				pattern = result.getEcnrFcltyInstlPhoto();
 			}else if(typeNm.equals("사방시설필요")) {
 				pattern = result.getEcnrFcltyNcstyPhoto();
 			}
 			
 		
	 		// 중간 폴더
	 		String middleDir = path.toString().concat(File.separator+svyId+"_"+dt_str+File.separator+typeNm);
	 		Path middlePath = Paths.get(middleDir);
	 		Files.createDirectories(middlePath);
	 		
	 		// 해당 사진 타입에 맞는 사진 찾기
			List<String> jpgList = new ArrayList<>();
			JSONParser parser = new JSONParser();
			org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(pattern);
			
			for(int i=0; i<jsonArray.size(); i++) {
				String subDir = middlePath.toString().concat(File.separator+(i+1)+"번");
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
	 		
			if(files != null) {
				for (int i = 0; i < files.length; i++) {
		 			String nm = files[i].getName();
		 			JSONParser parser1 = new JSONParser();
		 			
					org.json.simple.JSONArray jsonArray1 = (org.json.simple.JSONArray) parser1.parse(pattern);
					for(int j=0; j<jsonArray1.size(); j++) {
						if(jsonArray1.get(j).toString().contains(nm)) {
							EgovFileUtil.cp(storeFile+File.separator+nm, dir.concat(File.separator+svyId+"_"+dt_str+File.separator+typeNm+File.separator+(j+1)+"번")+File.separator+nm);//원본이미지 복사
						}
					}
		 		}
				
			}
 		}
		
		if(files != null) {
	 		
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
	 				
	 				String fileName = "임도_"+svyId+"_"+dt_str + ".zip";
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
		
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
		System.out.println("시간차이(m) : "+secDiffTime);
	}
	
	/**
	 * 조사완료 상세조회 엑셀다운로드
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectFrdSvyComptDetailExcel.do")
	public ModelAndView selectFrdSvyComptDetailExcel(
			@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("frdExcelView");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		HashMap<?, ?> _map = (HashMap<?, ?>)vytFrdSvyComptService.selectFrdSvyComptDetailExcel(searchVO.getGid());

		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
    	
    	String fileNm = "임도_".concat(formater.format(new Date()).toString());
    	
    	dataMap.put("sheetNm", fileNm);
    	dataMap.put("list", _map.get("resultList"));
    	
    	mv.addObject("dataMap",dataMap);
    	mv.addObject("filename",fileNm);
		
		return mv;
		
	}
	
	/**
	 * 상세조회 대상지 분석 팝업
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectSvyComptAnalPopup.do")
	public String selectSvyAnalPopup(ModelMap model, HttpServletRequest req) throws Exception {
		
		String mstId = req.getParameter("mstId");
		model.addAttribute("mstId", mstId);
		String svyLabel = req.getParameter("svyLabel");
		model.addAttribute("svyLabel", svyLabel);
		
		return "sys/vyt/frd/sct/svyComptAnalPopup";
	}
	
	/**
	 * 대상지 분석
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectSvyComptReportImgAll.do")	
	public ModelAndView selectSvyComptReportImgAll(ModelMap model, HttpServletRequest req) throws Exception {
		long start = System.currentTimeMillis();
		ModelAndView mv = null;
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			mv = new ModelAndView("jsonView");
			
			String[] analList = req.getParameterValues("analList");
			String svyLabel = req.getParameter("svyLabel");
			String mstId = req.getParameter("mstId");
			String bufferSize = req.getParameter("bufferSize");
			
			HashMap<String, String> numberMap = new HashMap<String, String>();
			
			numberMap.put("svyLabel", svyLabel);
			numberMap.put("mstId", mstId);
			
			List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
			String smid1 = null, sldId1 = null;
			String smid2 = null, sldId2 = null;
			for(int i=0; i<sldNumberList.size(); i++) {
				if(sldNumberList.get(i).get("routeCode").toString().equals("02")) {
					smid2 = sldNumberList.get(i).get("smid").toString();
					sldId2 = sldNumberList.get(i).get("sldId").toString();
				}else if(sldNumberList.get(i).get("routeCode").toString().equals("01")){
					smid1 = sldNumberList.get(i).get("smid").toString();
					sldId1 = sldNumberList.get(i).get("sldId").toString();
				}
				
			}
			String smid = smid2 != null ? smid2 : smid1; 
			String sldId = sldId2 != null ? sldId2 : sldId1; 
			
			HashMap<String, Object> commandMap = new HashMap<>();
			
			commandMap.put("analList", Arrays.toString(analList));
			commandMap.put("smid", smid);
			commandMap.put("smid1", smid1);
			commandMap.put("sldId", sldId);
			commandMap.put("fileExtsn", "jpg");
			commandMap.put("pageType", "svyCompt");
			
			if(smid2 != null) {
				commandMap.put("routeCode", "02");
			}else {
				commandMap.put("routeCode", "01");
			}
			
			if(bufferSize == null) {
				commandMap.put("bufferSize", "200");
			}else {
				commandMap.put("bufferSize", bufferSize);
			}
			
			vytFrdStripLandService.createVytFrdAnalData(commandMap);
			
			mv.addObject("status","success");
			model.addAttribute("message", "분석이 완료되었습니다.");
			
			long end = System.currentTimeMillis();
			LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		}
		long end = System.currentTimeMillis();
		LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		return mv;
	}
	
	/**
	 * 조사완료 point shp 생성 및 다운로드
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sct/selectDownloadPointShp.do")
	public void downloadSvyComptPointShp(
			@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long start = System.currentTimeMillis();
		ModelAndView mv = null;
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			mv = new ModelAndView("jsonView");
			
			String gid = request.getParameter("gid");
			searchVO.setGid(gid);
			
			String analType = request.getParameter("analType");
			String svyLabel = request.getParameter("svyLabel");
			String mstId = request.getParameter("mstId");
			
			
			HashMap<String, String> numberMap = new HashMap<String, String>();
			
			numberMap.put("svyLabel", svyLabel);
			numberMap.put("mstId", mstId);
			
			List<EgovMap> sldNumberList = vytFrdSvyComptService.selectSldNumber(numberMap);
			String smid1 = null, sldId1 = null;
			String smid2 = null, sldId2 = null;
			for(int i=0; i<sldNumberList.size(); i++) {
				if(sldNumberList.get(i).get("routeCode").toString().equals("02")) {
					smid2 = sldNumberList.get(i).get("smid").toString();
					sldId2 = sldNumberList.get(i).get("sldId").toString();
				}else if(sldNumberList.get(i).get("routeCode").toString().equals("01")){
					smid1 = sldNumberList.get(i).get("smid").toString();
					sldId1 = sldNumberList.get(i).get("sldId").toString();
				}
				
			}
			String smid = smid2 != null ? smid2 : smid1; 
			String sldId = sldId2 != null ? sldId2 : sldId1; 
			
			
			String analId = SuperMapBasicUtils.getDatasetUuid();
			String accountId = loginVO.getId();
			HashMap<String, String> analDataMap = new HashMap<String, String>();
			analDataMap.put("smid", smid);
			analDataMap.put("sldId", sldId);
			analDataMap.put("analId", analId);
			
			VytFrdSvyComptVO svyComptVO = vytFrdSvyComptService.selectFrdSvyComptDetail(searchVO);
			
			HashMap<String, String> fileMap = new HashMap<String, String>();
			
			String zipPath = null;
			String copyPath = null;
			
			List<AnalFileVO> fileVO = null;
			
			AnalFileVO vo = null;
			 
			// 보호시설 
			if(analType.equals("20")) {
				String safeFctList = svyComptVO.getSafeFctList();
				if(!safeFctList.equals("[]")) {
					
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
				    JsonNode dataMap = ojm.readTree(safeFctList.toString());
				    for (int i = 0; i < dataMap.size(); i++) {
				    	// 보호시설 key -> 경도, 위도, 유형
				        String lon1 = dataMap.get(i).get("경도").asText();
				        String lat1 = dataMap.get(i).get("위도").asText();
				        String type = dataMap.get(i).get("유형").asText();
				        
				        analDataMap.put("anal_type", (i+1)+"번 보호시설");   
				        analDataMap.put("lon1", lon1);                    
				        analDataMap.put("lat1", lat1);                    
				        analDataMap.put("lon2", "");                    
				        analDataMap.put("lat2", "");                    
				        analDataMap.put("data1_key", "유형");                  
				        analDataMap.put("data1_value", type);                  
			        	
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 종단기울기
			if(analType.equals("21")) {
				String lonSlopeList = svyComptVO.getLonSlopeList();
				if(!lonSlopeList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
				    JsonNode dataMap = ojm.readTree(lonSlopeList.toString());
				    for (int i = 0; i < dataMap.size(); i++) {
				    	// 종단기울기 key -> 경도1, 위도1,경도2, 위도2,기울기
				        String lon1 = dataMap.get(i).get("경도1").asText();
				        String lat1 = dataMap.get(i).get("위도1").asText();
				        String lon2 = dataMap.get(i).get("경도2").asText();
				        String lat2 = dataMap.get(i).get("위도2").asText();
				        String type = dataMap.get(i).get("기울기").asText();
				        
				        
				        analDataMap.put("anal_type", (i+1)+"번 종단기울기");   
				        analDataMap.put("lon1", lon1);                    
				        analDataMap.put("lat1", lat1);                    
				        analDataMap.put("lon2", lon2);                    
				        analDataMap.put("lat2", lat2);                    
				        analDataMap.put("data1_key", "기울기");                  
				        analDataMap.put("data1_value", type);                  
			        	
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			
			// 산지경사
			if(analType.equals("22")) {
				String mtSlopeList = svyComptVO.getMtSlopeList();
				if(!mtSlopeList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(mtSlopeList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 산지경사 key -> 경도1, 위도1,경도2, 위도2, 경사도
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type = dataMap.get(i).get("경사도").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 산지경사");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "경사도");                  
						analDataMap.put("data1_value", type);                  
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 암반노출
			if(analType.equals("23")) {
				String rockExposList = svyComptVO.getRockExposList();
				if(!rockExposList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(rockExposList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 암반노출 key -> 경도1, 위도1,경도2, 위도2, 암반노출
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type = dataMap.get(i).get("암반노출").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 암반노출");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "암반노출");                  
						analDataMap.put("data1_value", type);      
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 조림지
			if(analType.equals("24")) {
				String afrstList = svyComptVO.getAfrstList();
				if(!afrstList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(afrstList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 조림지 key -> 경도1, 위도1,경도2, 위도2, 조림지
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 조림지");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "조림지");                  
						analDataMap.put("data1_value", "유");                  
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 벌채지
			if(analType.equals("25")) {
				String cuttingList = svyComptVO.getCuttingList();
				if(!cuttingList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(cuttingList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 벌채지 key -> 경도1, 위도1,경도2, 위도2, 벌채지
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 벌채지");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "벌채지");                  
						analDataMap.put("data1_value", "유");                  
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 석력지
			if(analType.equals("26")) {
				String stmiList = svyComptVO.getStmiList();
				if(!stmiList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(stmiList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 석력지 key -> 경도1, 위도1,경도2, 위도2, 석력지
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 석력지");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "석력지");                  
						analDataMap.put("data1_value", "유");          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 계류종류 및 현황
			if(analType.equals("27")) {
				String mrngKindList = svyComptVO.getMrngKind();
				if(!mrngKindList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(mrngKindList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 계류종류 및 현황 key -> 위도1, 경도1, 위도2, 경도2, 대분류, 중분류, 소분류
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type1 = dataMap.get(i).get("대분류").asText();
	//					String type2 = dataMap.get(i).get("중분류").asText(); // 현재 중분류로 넘어오는거 없으나 추후 넘어오면 주석 제거
						String type3 = dataMap.get(i).get("소분류").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 계류종류및현황");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);    
						analDataMap.put("data1_key", "대분류");                  
						analDataMap.put("data1_value", type1);          
						analDataMap.put("data2_key", "중분류");                  
						analDataMap.put("data2_value", "");          
						analDataMap.put("data3_key", "소분류");                  
						analDataMap.put("data3_value", type3);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 습지
			if(analType.equals("28")) {
				String wetLandList = svyComptVO.getWetLandList();
				if(!wetLandList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(wetLandList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 습지 key -> 위도1, 경도1, 위도2, 경도2, 습지
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 습지");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "습지");                  
						analDataMap.put("data1_value", "유");          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 묘지
			if(analType.equals("29")) {
				String cmtryList = svyComptVO.getCmtryList();
				if(!cmtryList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(cmtryList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 묘지 key -> 위도1, 경도1, 위도2, 경도2, 묘지
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type2 = dataMap.get(i).get("묘지_위치").asText();
						String type3 = dataMap.get(i).get("묘지_관리").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 묘지");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "묘지_유무");                  
						analDataMap.put("data1_value", "유");          
						analDataMap.put("data2_key", "묘지_위치");                  
						analDataMap.put("data2_value", type2);          
						analDataMap.put("data3_key", "묘지_관리");                  
						analDataMap.put("data3_value", type3);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 용출수
			if(analType.equals("30")) {
				String eltnWaterList = svyComptVO.getEltnWaterList();
				if(!eltnWaterList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(eltnWaterList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 용출수 key -> 위도1, 경도1, 위도2, 경도2, 용출수
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 용출수");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "용출수");                  
						analDataMap.put("data1_value", "유");          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 연약지반
			if(analType.equals("31")) {
				String sofrtGrndList = svyComptVO.getSofrtGrndList();
				if(!sofrtGrndList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(sofrtGrndList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 연약지반 key -> 위도1, 경도1, 위도2, 경도2, 연약지반
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 연약지반");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "연약지반");                  
						analDataMap.put("data1_value", "유");          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 붕괴우려지역
			if(analType.equals("32")) {
				String clpsCnrnList = svyComptVO.getClpsCnrnList();
				if(!clpsCnrnList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(clpsCnrnList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 붕괴우려지역 key -> 위도1, 경도1, 위도2, 경도2, 붕괴우려_대분류, 붕괴우려_소분류
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type1 = dataMap.get(i).get("붕괴우려_대분류").asText();
						String type2 = dataMap.get(i).get("붕괴우려_소분류").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 붕괴우려지역");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);             
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);             
						analDataMap.put("data1_key", "붕괴우려_대분류");                  
						analDataMap.put("data1_value", type1);          
						analDataMap.put("data2_key", "붕괴우려_소분류");                  
						analDataMap.put("data2_value", type2);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 주요수종
			if(analType.equals("33")) {
				String maintreekndList = svyComptVO.getMaintreekndList();
				if(!maintreekndList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(maintreekndList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 주요수종 key -> 위도1, 경도1, 위도2, 경도2, 주요수종
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type = dataMap.get(i).get("주요수종").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 주요수종");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "주요수종");                  
						analDataMap.put("data1_value", type);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 주요식생
			if(analType.equals("34")) {
				String mainvegList = svyComptVO.getMainvegList();
				if(!mainvegList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(mainvegList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 주요식생 key -> 위도1, 경도1, 위도2, 경도2, 주요식생
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type = dataMap.get(i).get("주요식생").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 주요식생");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "주요식생");                  
						analDataMap.put("data1_value", type);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 상수원오염
			if(analType.equals("35")) {
				String wtrPltnList = svyComptVO.getWtrPltnList();
				if(!wtrPltnList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(wtrPltnList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 상수원오염 key -> 위도1, 경도1, 위도2, 경도2, 상수원오염
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 상수원오염");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);          
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);          
						analDataMap.put("data1_key", "상수원오염");                  
						analDataMap.put("data1_value", "유");          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 훼손우려지
			if(analType.equals("36")) {
				String dmgCncrnList = svyComptVO.getDmgCncrnList();
				if(!dmgCncrnList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(dmgCncrnList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 훼손우려지 key -> 위도1, 경도1, 위도2, 경도2, 훼손우려지
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 훼손우려지");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "훼손우려지");                  
						analDataMap.put("data1_value", "유");          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 산림재해(산불/병해충)
			if(analType.equals("37")) {
				String frstDsstrList = svyComptVO.getFrstDsstrList();
				if(!frstDsstrList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(frstDsstrList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 산림재해 key -> 위도1, 경도1, 위도2, 경도2, 재해유형, 산림재해
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type1 = dataMap.get(i).get("재해유형").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 산림재해");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "재해유형");                  
						analDataMap.put("data1_value", type1);          
	
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 야생동물
			if(analType.equals("38")) {
				String wildAnmlList = svyComptVO.getWildAnmlList();
				if(!wildAnmlList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(wildAnmlList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 야생동물 key -> 위도1, 경도1, 위도2, 경도2, 야생동물_유무, 야생동물_유형
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type1 = dataMap.get(i).get("야생동물_유형").asText();
						String type2 = dataMap.get(i).get("야생동물_종류").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 야생동물");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "야생동물_형태");                  
						analDataMap.put("data1_value", type1);          
						analDataMap.put("data2_key", "야생동물_유형");                  
						analDataMap.put("data2_value", type2);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 사방시설 설치 여부
			if(analType.equals("39")) {
				String ecnrFcltyInstlList = svyComptVO.getEcnrFcltyInstlList();
				if(!ecnrFcltyInstlList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(ecnrFcltyInstlList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 사방시설설치 key -> 위도1, 경도1, 위도2, 경도2, 사방시설설치_유무, 사방시설설치_유형
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type1 = dataMap.get(i).get("사방시설설치_유형").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 사방시설설치");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);       
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);       
						analDataMap.put("data1_key", "사방시설설치_유형");                  
						analDataMap.put("data1_value", type1);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			// 사방시설 필요 여부
			if(analType.equals("40")) {
				String ecnrFcltyNcstyList = svyComptVO.getEcnrFcltyNcstyList();
				if(!ecnrFcltyNcstyList.equals("[]")) {
					org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
					ObjectMapper ojm = new ObjectMapper();
					JSONParser parser = new JSONParser();
					Object obj;
					
					JsonNode dataMap = ojm.readTree(ecnrFcltyNcstyList.toString());
					for (int i = 0; i < dataMap.size(); i++) {
						// 사방시설필요 key -> 위도1, 경도1, 위도2, 경도2, 사방시설필요_유무, 사방시설필요_유형
						String lon1 = dataMap.get(i).get("경도1").asText();
						String lat1 = dataMap.get(i).get("위도1").asText();
						String lon2 = dataMap.get(i).get("경도2").asText();
						String lat2 = dataMap.get(i).get("위도2").asText();
						String type1 = dataMap.get(i).get("사방시설필요_유형").asText();
						
						analDataMap.put("anal_type", (i+1)+"번 사방시설필요");   
						analDataMap.put("lon1", lon1);                    
						analDataMap.put("lat1", lat1);                    
						analDataMap.put("lon2", lon2);                    
						analDataMap.put("lat2", lat2);                    
						analDataMap.put("data1_key", "사방시설필요_유형");                  
						analDataMap.put("data1_value", type1);          
						
				        List<AnalFileVO> afv = vytFrdSvyComptService.downloadAnalPntAll(analDataMap);
				        if (afv != null) {
			                // fileVO 초기화 또는 생성 후 추가
			                if (fileVO == null) {
			                    fileVO = new ArrayList<>();
			                }
			                fileVO.addAll(afv);
			            }
				    }
				    vo = vytFrdSvyComptService.downloadAnalPntAllImpl(fileVO, accountId);
				}
			}
			
			if(vo.getFileStreCours().length() > 0) {
				String saveFileNm = vo.getFileStreCours()+File.separator+loginVO.getId()+File.separator+vo.getAnalId()+".".concat(vo.getFileExtsn());
				
				File uFile = new File(saveFileNm);
				long fSize = uFile.length();
				if (fSize > 0) {
					String mimetype = "application/x-msdownload";
					
					String userAgent = request.getHeader("User-Agent");
					HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
					if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
						mimetype = "application/x-stuff";
					}

					String contentDisposition = EgovBrowserUtil.getDisposition(vo.getAnalId()+".".concat(vo.getFileExtsn()),userAgent,"UTF-8");
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
						//EgovFileUtil.rm(vo.getFileStreCours()+File.separator+loginVO.getId());
						EgovResourceCloseHelper.close(in, out);
						FileUtils.deleteDirectory(new File(vo.getFileStreCours()+File.separator+loginVO.getId()));
					}
				}
			}
		}
		
		long end = System.currentTimeMillis();
		LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		
	}
}
