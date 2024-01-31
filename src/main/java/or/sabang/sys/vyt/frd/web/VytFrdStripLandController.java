package or.sabang.sys.vyt.frd.web;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandService;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;
import or.sabang.utl.CommonUtil;
import or.sabang.utl.DmsFormalization;

@Controller
public class VytFrdStripLandController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "vytFrdStripLandService")
	private VytFrdStripLandService vytFrdStripLandService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
    /** 첨부파일 위치 지정  => globals.properties */
    private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.frdSld");
	
//    private static final String VWORLD_API_URL = EgovProperties.getProperty("vworld.landUrl");
//	private static final String VWORLD_API_KEY = EgovProperties.getProperty("vworld.apiKey");
    
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdStripLandController.class);
	
	int cnt_total = 0;
	
	/**
	 * 대상지관리 목록을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectFrdSldList.do")	
	public String selectVytFrdSldList(@ModelAttribute("searchVO") VytFrdStripLandVO searchVO, ModelMap model) throws Exception {
		LOGGER.info("selectVytFrdSldList controller");
		
		List<VytFrdStripLandVO> list = null;
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
			searchVO.setSvy_year(vytFrdStripLandService.selectVytFrdSldMaxYear());
		}
		
		//연도코드 조회
		List<?> year_result = vytFrdStripLandService.selectVytFrdSldYear();
		model.addAttribute("yearCodeList", year_result);
		
		try {
			list = vytFrdStripLandService.selectVytFrdSldList(searchVO);
			cnt_total = vytFrdStripLandService.selectVytFrdSldListTotCnt(searchVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paginationInfo.setTotalRecordCount(cnt_total);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList", list);
		return "sys/vyt/frd/sld/svySldList";		
	}
	
	/**
	 * 대상지관리 리스트를 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectFrdSldinfo.do")	
	public String selectVytFrdSldInfo(
			@ModelAttribute("searchVO") VytFrdStripLandVO searchVO,
			@RequestParam("id") String id,
			ModelMap model) throws Exception {
		
		HashMap<String, Object> commandMap = new HashMap<>();
		commandMap.put("id", Integer.parseInt(id));
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", searchVO.getPageUnit());
		searchMap.put("schpageIndex", searchVO.getPageIndex());
		searchMap.put("schsldNm", searchVO.getSld_nm());
		searchMap.put("schsld_create_user", searchVO.getSld_create_user());
		searchMap.put("schsvy_year", searchVO.getSvy_year());
		searchMap.put("schpageSubIndex", searchVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		/* 대상지 관리 상세정보 조회 */
		VytFrdStripLandVO result = vytFrdStripLandService.selectVytFrdSldDetail(commandMap);
		model.addAttribute("result",result);
		
		/* 대상지 관리데이터 리스트조회 */
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
		
		resultList = vytFrdStripLandService.selectVytFrdSldItemList(searchVO);
		int totCnt = vytFrdStripLandService.selectVytFrdSldItemListTotCnt(searchVO);
	
		result.setTotcnt(Integer.toString(totCnt));
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		return "sys/vyt/frd/sld/svySldInfo";		
	}
	
	/**
	 * 대상지를 상세조회한다
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectSldInfoDetail.do")	
	public String selectVytFrdSldInfoDetail(
			@ModelAttribute("searchVO") VytFrdStripLandVO searchVO,
			ModelMap model, HttpServletRequest req) throws Exception {
		
		String smid = req.getParameter("smid");
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", searchVO.getPageUnit());
		searchMap.put("schpageIndex", searchVO.getPageIndex());
		searchMap.put("schsldNm", searchVO.getSld_nm());
		searchMap.put("schsld_create_user", searchVO.getSld_create_user());
		searchMap.put("schsvy_year", searchVO.getSvy_year());
		searchMap.put("schpageSubIndex", searchVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		VytFrdStripLandVO result = vytFrdStripLandService.selectSldDetailOne(smid);
		
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
		
		// 필지 조회
		List<VytFrdStripLandVO> parcelList = vytFrdStripLandService.selectParcelList(smid);

		String path1 = req.getRequestURL().toString();
		String path2 = req.getRequestURI(); 
		
		String domain = path1.replaceAll(path2, "");
		
		if(parcelList != null) {
			result.setParcelCnt(Integer.toString(parcelList.size()-1));
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
		
		model.addAttribute("result", result);
		model.addAttribute("parcelList", parcelList);
		
		HashMap<String, Object> analParameterMap = new HashMap<String, Object>();
		analParameterMap.put("smid", smid);
		analParameterMap.put("routeCode", "01");
		
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
		if(result.getCulture() != null) {
			String[] smidArray = result.getCulture().split(",");
	        
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
		
		
		return "sys/vyt/frd/sld/svySldDetail";		
	}
	
	/**
	 * 대상지를 수정페이지로 이동한다
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectSldInfoUpdtView.do")	
	public String selectVytFrdSldInfoUpdtView(
			@ModelAttribute("searchVO") VytFrdStripLandVO searchVO,
			ModelMap model, HttpServletRequest req) throws Exception {
		
		String smid = req.getParameter("smid");
		
		// 이전 검색 페이지 map
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("schpageUnit", searchVO.getPageUnit());
		searchMap.put("schpageIndex", searchVO.getPageIndex());
		searchMap.put("schsldNm", searchVO.getSld_nm());
		searchMap.put("schsld_create_user", searchVO.getSld_create_user());
		searchMap.put("schsvy_year", searchVO.getSvy_year());
		searchMap.put("schpageSubIndex", searchVO.getPageSubIndex());
		model.addAttribute("searchMap", searchMap);
		
		VytFrdStripLandVO result = vytFrdStripLandService.selectSldDetailOne(smid);
		
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
		
		HashMap<String, Object> analParameterMap = new HashMap<String, Object>();
		analParameterMap.put("smid", smid);
		analParameterMap.put("routeCode", "01");
		
		List<AnalFileVO> analList = vytFrdStripLandService.selectAnalImg(analParameterMap);
		
		// 통계가공
		HashMap<String, Object> statList = new HashMap<String, Object>();

		for(int i=0; i<analList.size(); i++){
			
			// 분석 이미지 종류
			String analType = analList.get(i).getOrignlFileNm();
			
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
				
				if(analType.equals("slope") || analType.equals("aspect") || analType.equals("dem")) {
					statList.put(analType+"MaxVal",statMaxRasterValue);
					statList.put(analType+"MinVal",statMinRasterValue);
					statList.put(analType+"AvgVal",statAvgRasterValue);
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
		model.addAttribute("statList", statList);
		
		// 노선종류 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI169");
		List<?> routeTypeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("routeTypeList", routeTypeList);
		// 임도종류 조회
		vo.setCodeId("FEI171");
		List<?> frdTypeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("frdTypeList", frdTypeList);
		
		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회
		if(result.getSd() != null && !result.getSd().trim().isEmpty()) {
			adminVO.setSdNm(result.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		
		//읍면동코드 조회
		if(result.getSgg() != null && !result.getSgg().trim().isEmpty()) {
			adminVO.setSggNm(result.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(result.getEmd() != null && !result.getEmd().trim().isEmpty()) {
			adminVO.setEmdNm(result.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		
		model.addAttribute("result", result);
		
		return "sys/vyt/frd/sld/svySldUpdt";		
	}
	/**
	 * 대상지를 수정한다
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/updateSldInfoUpdt.do")	
	public ModelAndView selectVytFrdSldInfoUpdt(
			@ModelAttribute("stripLandVO") VytFrdStripLandVO stripLandVO,
			ModelMap model, HttpServletRequest req) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		try {
			vytFrdStripLandService.updateSldDetailOne(stripLandVO);
			
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.update"));
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;		
	}
	
	/**
	 * 대상지 등록 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/insertStripLandView.do")	
	public String insertStripLandView(ModelMap model) throws Exception {
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userNm", loginVO.getName());
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		// 관할청
		comVO.setCodeId("FEI167");
		List<CmmnDetailCode> compentauth_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("compentauth_result", compentauth_result);
		
		// 노선종류
		comVO.setCodeId("FEI169");
		List<CmmnDetailCode> route_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("route_result", route_result);
		
		// 관할구분
		comVO.setCodeId("FEI172");
		List<CmmnDetailCode> compentAssort_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("compentAssort_result", compentAssort_result);
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		return "sys/vyt/frd/sld/stripLandRegist";		
	}
	
	/**
	 * 대상지 등록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/insertStripLand.do")	
	public ModelAndView insertStripLand(
			@ModelAttribute("stripLandVO") VytFrdStripLandVO stripLandVO,
			ModelMap model, @RequestParam(value = "file") MultipartFile mFile
			) throws Exception {
		ModelAndView mv = null;
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			JSONObject results = null;
			
			String id = null;
			
			String sldNm = stripLandVO.getSld_nm();
			int cnt = vytFrdStripLandService.selectVytFrdCheckSldName(sldNm);
			try {
				if(cnt > 0) {
					mv.addObject("cnt", "fail");
					return mv;
				}else {
					mv.addObject("cnt", "success");
					id = vytFrdStripLandService.insertSldSpce(stripLandVO);
					List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
					if (list.size() > 0) {
						EgovFormBasedFileVo fileVO = list.get(0);
						results = vytFrdStripLandService.insertFrdSvySld(fileVO);
						
						List<VytFrdStripLandVO> noSldIdList = vytFrdStripLandService.selectNoSldId();
						List<Integer> smidList = new ArrayList<Integer>();
						for(int i=0; i<noSldIdList.size(); i++) {
							String smid = noSldIdList.get(i).getSmid();
							smidList.add(Integer.parseInt(smid));
						}
						
						commandMap.put("id", id);
						commandMap.put("smidList", smidList);
//						commandMap.put("routetype", stripLandVO.getRoutetype().toString());
						commandMap.put("routetype", "기존노선");
						
						String compentauth = null;
						if(stripLandVO.getCompentauth() != null) {
							if(stripLandVO.getCompentauth().equals("REG18")) {
								compentauth = "북부청";
							}else if(stripLandVO.getCompentauth().equals("REG19")) {
								compentauth = "동부청";
							}else if(stripLandVO.getCompentauth().equals("REG20")) {
								compentauth = "남부청";
							}else if(stripLandVO.getCompentauth().equals("REG21")) {
								compentauth = "중부청";
							}else if(stripLandVO.getCompentauth().equals("REG22")) {
								compentauth = "서부청";
							}else compentauth = stripLandVO.getCompentauth().toString();
						}
						
						commandMap.put("compentauth", compentauth);
						commandMap.put("subcompentauth", stripLandVO.getSubcompentauth().toString());
						
						if(smidList.size() > 0) {
							vytFrdStripLandService.updateNoSldId(commandMap);
							mv.addObject("status",results.get("status"));
							mv.addObject("message", results.get("message"));
						}else {
							mv.addObject("status","fail");
							mv.addObject("message", "등록파일을 찾을 수  없습니다.\n관리자에게 문의하세요.");
						}
						
					}else {
						mv.addObject("status","fail");
			    		mv.addObject("message", "등록파일을 찾을 수 없습니다.\n관리자에게 문의하세요.");
					}
				}
			} catch (SecurityException e) {
				LOGGER.error(e.getMessage());
				vytFrdStripLandService.deleteSldDetail(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("errors.file.extension"));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				vytFrdStripLandService.deleteSldDetail(commandMap);
				mv.addObject("status","fail");
	    		mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
		}
		
		return mv;	
	}
	
	/**
	 * 대상지 추가등록 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/insertStripLandViewAddPopup.do")	
	public String insertStripLandViewAddPopup(
			@RequestParam(value="id") String id,
			ModelMap model) throws Exception {
		
		model.addAttribute("id",id);
		
		ComDefaultCodeVO comVO = new ComDefaultCodeVO();
		
		// 관할청
		comVO.setCodeId("FEI167");
		List<CmmnDetailCode>  compentauth_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("compentauth_result", compentauth_result);
		
		// 노선종류
		comVO.setCodeId("FEI169");
		List<CmmnDetailCode>  route_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("route_result", route_result);
		
		// 관할구분
		comVO.setCodeId("FEI172");
		List<CmmnDetailCode> compentAssort_result = cmmUseService.selectCmmCodeDetail(comVO);
		model.addAttribute("compentAssort_result", compentAssort_result);
		
//		if(searchVO.getSvyRegion1() != null) {
//			vo.setCodeId(searchVO.getSvyRegion1());
//			List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
//			model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
//		}
		
		return "sys/vyt/frd/sld/stripLandSldRegist";		
	}
	
	/**
	 * 대상지 추가등록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/insertStripLandViewAdd.do")	
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
			
			try {
				List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFilesExt(mFile, uploadDir, maxFileSize, extWhiteList);
				if (list.size() > 0) {
					EgovFormBasedFileVo fileVO = list.get(0);
					results = vytFrdStripLandService.insertFrdSvySld(fileVO);
					
					mv.addObject("status",results.get("status"));
		    		mv.addObject("message", results.get("message"));
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
				
				commandMap.put("id", id);
				commandMap.put("smidList", smidList);
//				commandMap.put("routetype", stripLandVO.getRoutetype().toString());
				commandMap.put("routetype", "기존노선");
				
				String compentauth = null;
				if(stripLandVO.getCompentauth() != null) {
					if(stripLandVO.getCompentauth().equals("REG18")) {
						compentauth = "북부청";
					}else if(stripLandVO.getCompentauth().equals("REG19")) {
						compentauth = "동부청";
					}else if(stripLandVO.getCompentauth().equals("REG20")) {
						compentauth = "남부청";
					}else if(stripLandVO.getCompentauth().equals("REG21")) {
						compentauth = "중부청";
					}else if(stripLandVO.getCompentauth().equals("REG22")) {
						compentauth = "서부청";
					}else compentauth = stripLandVO.getCompentauth().toString();
				}
				
				commandMap.put("compentauth", compentauth);
				commandMap.put("subcompentauth", stripLandVO.getSubcompentauth().toString());
				
				if(smidList.size() > 0) {
					vytFrdStripLandService.updateNoSldId(commandMap);
				}else {
					mv.addObject("status","fail");
					mv.addObject("message", "등록파일을 찾을 수  없습니다.\n관리자에게 문의하세요.");
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
	 * 대상지 삭제
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/deleteSldDetail.do")	
	public ModelAndView deleteSldDetail(@RequestParam(value="id") String id, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("id", id);
			
			vytFrdStripLandService.deleteSldDetail(commandMap);
			
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
	 * 대상지 단건 삭제
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/deleteSldDetailOne.do")	
	public ModelAndView deleteSldDetailOne(@ModelAttribute("stripLandVO") VytFrdStripLandVO stripLandVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			
			HashMap<String, Object> commandMap = new HashMap<>();
			commandMap.put("smid", stripLandVO.getSmid());
			
			vytFrdStripLandService.deleteSldDetailOne(commandMap);
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
	 * 대상지 분석 팝업 표출
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectSldAnalPopup.do")	
	public String selectSldAnalPopup(ModelMap model, HttpServletRequest req) throws Exception {
		
		String smid = req.getParameter("smid");
		model.addAttribute("smid", smid);
		String sldId = req.getParameter("sldId");
		model.addAttribute("sldId", sldId);
		
		return "sys/vyt/frd/sld/stripLandAnalPopup";		
	}
	/**
	 * 대상지 분석
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectSldReportImgAll.do")	
	public ModelAndView selectSldReportImgAll(ModelMap model, HttpServletRequest req) throws Exception {
		long start = System.currentTimeMillis();
		ModelAndView mv = null;
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		}else {
			mv = new ModelAndView("jsonView");
			HashMap<String, Object> commandMap = new HashMap<>();
			
			String[] analList = req.getParameterValues("analList");
			String smid = req.getParameter("smid");
			String sldId = req.getParameter("sldId");
			
			String bufferSize = req.getParameter("bufferSize");
			
			commandMap.put("analList", Arrays.toString(analList));
			commandMap.put("smid", smid);
			commandMap.put("smid1", smid);
			commandMap.put("sldId", sldId);
			commandMap.put("routeCode", "01");
			commandMap.put("pageType", "sld");
			if(bufferSize == null) {
				commandMap.put("bufferSize", "200");
			}else {
				commandMap.put("bufferSize", bufferSize);
			}
			
			vytFrdStripLandService.createVytFrdAnalData(commandMap);
			
			mv.addObject("status","success");
			model.addAttribute("message", "분석이 완료되었습니다.");
		}
		long end = System.currentTimeMillis();
		LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		return mv;
	}
	
	/**
	 * 분석파일 및 이미지 다운로드
	 * @param fileSn
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectAnalFileDown.do")
	public void selectAnalFileDown(@RequestParam("fileSn") String fileSn,
			@RequestParam("fileExtsn") String fileExtsn,
			ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		AnalFileVO vo = vytFrdStripLandService.selectAnalFileDownDetail(fileSn);
		vo.setFileExtsn(fileExtsn);
		
		saveAnalFileImg(vo, request, response);
	}
	
	/**
	 * 이미지 다운로드
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void saveAnalFileImg(AnalFileVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
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

			String contentDisposition = EgovBrowserUtil.getDisposition(vo.getSldId().concat("_").concat(vo.getOrignlFileNm().concat(".").concat(vo.getFileExtsn())),userAgent,"UTF-8");
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
	
	/**
	 * 관계지적 목록 팝업
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectOtherAddrPopup.do")	
	public String selectOtherAddrPopup(ModelMap model, HttpServletRequest req) throws Exception {
		
		String smid = req.getParameter("smid");
		
		List<VytFrdStripLandVO> parcelList = vytFrdStripLandService.selectParcelList(smid);
		model.addAttribute("parcelList", parcelList);
		
		return "sys/vyt/frd/sld/stripLandOtherAddrPopup";		
	}
	
    /**
     * 대상지 등록 팝업 관할청을 보여준다.
     * @param adminVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectCompentAuth.do")
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
	 * 대상지 분석 파일 일괄 다운로드
	 * @param svyComptVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sld/selectDownloadAnalAll.do")
	public void selectDownloadAnalAll(
			@ModelAttribute("searchVO") VytFrdStripLandVO searchVO,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		
		
		if(isAuthenticated) {
			searchVO.setSld_create_user(loginVO.getId());
			
			AnalFileVO vo = vytFrdStripLandService.downloadAnalAll(searchVO);

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
	
	
}
