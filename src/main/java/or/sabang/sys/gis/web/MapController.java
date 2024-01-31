package or.sabang.sys.gis.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.string.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.gis.service.MapService;
import or.sabang.sys.gis.service.MapSvyComptVO;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;
import or.sabang.utl.AprExcelView;
import or.sabang.utl.SupMapCommonUtils;

@Controller
public class MapController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "mapService")
	private MapService mapService;
	
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MapController.class);
	
	/**
	 * @author ipodo
	 * @name mainMap
	 * @param {HttpServletRequest} request - http servlet request
	 * @param {HttpServletResponse} response - http servlet response
	 * @param {Model} model - model map
	 * @return {String} 
	 * @throws Exception
	 * @description 지도서비스 메인화면 조회
	 */
	@RequestMapping(value = "/sys/gis/mainMap.do")	
	public String mainMap(
			@RequestParam(value="type", required=false) String type, 
			@RequestParam(value="gid", required=false) String gid,
			@RequestParam(value="layer", required=false) String layer, 
			@ModelAttribute("searchVO") MapSvyComptVO searchVO,
			HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			String feature = null;
			JSONObject features = null;
			
			List<EgovMap> apiClsfyList = mapService.selectApiClsfyList();
			
			map.put("clsfy_nm", apiClsfyList.get(0).get("clsfyNm"));
			List<EgovMap> apiServiceList = mapService.selectApiServiceList(map);
			
			if(gid != null && type != null) {
				features = new JSONObject();
				
				Map<String, Object> mapParam = new HashMap<String, Object>();
				mapParam.put("type", type);
				mapParam.put("gid", Integer.parseInt(gid));
				
				feature = mapService.selectFeatureInfo(mapParam);
				features.put("type", type);
				features.put("features", new JSONObject(new JSONTokener(feature)));
				
			}
			
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

			// 기초조사 조사유형코드를 코드정보로부터 조회
			ComDefaultCodeVO vo = new ComDefaultCodeVO();

			vo.setCodeId("FEI003");
			List<CmmnDetailCode> type_result = cmmUseService.selectCmmCodeDetail(vo);
			
			vo.setCodeId("FEI016");
			List<CmmnDetailCode> type_results = cmmUseService.selectCmmCodeDetail(vo);
			
			model.addAttribute("typeCodeList", type_result);
			model.addAttribute("typeCodeLists", type_results);
			
			vo.setCodeId("FEI045");
			vo.setCodeDc("Cldr");
			List<?> month_result = cmmUseService.selectCmmCodeDetail(vo);
			model.addAttribute("monthCodeList", month_result);
			vo.setCodeDc("");

			AdministZoneVO adminVO = new AdministZoneVO();
			// 시도코드 조회
			List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
			model.addAttribute("sdCodeList", sdCodeList);
			
			// 시군구코드 조회
			if (searchVO.getSvySd() != null && !searchVO.getSvySd().trim().isEmpty()) {
				adminVO.setSdCode(searchVO.getSvySd());
				List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
				model.addAttribute("sggCodeList", sggCodeList);
			}

			// 읍면동코드 조회
			if (searchVO.getSvySgg() != null && !searchVO.getSvySgg().trim().isEmpty()) {
				adminVO.setSggCode(searchVO.getSvySgg());
				List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
				model.addAttribute("emdCodeList", emdCodeList);
			}

			// 리코드 조회
			if (searchVO.getSvyEmd() != null && !searchVO.getSvyEmd().trim().isEmpty()) {
				adminVO.setEmdCode(searchVO.getSvyEmd());
				List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
				model.addAttribute("riCodeList", riCodeList);
			}

			// 실태조사 필요성 코드조회
			vo.setCodeId("FEI030");
			List<?> ncssty_result = cmmUseService.selectCmmCodeDetail(vo);
			
			/*2023.08.18 추가*/
			// 조사 코드 목록 조회
			List<EgovMap> svyCodeLst = cmmUseService.selectSvyCodeLst();
			model.addAttribute("svyCodeLst", svyCodeLst);
			
			model.addAttribute("ncsstyCodeList", ncssty_result);
			model.addAttribute("paginationInfo", paginationInfo);			
			
			model.addAttribute("apiClsfyList", apiClsfyList);
			model.addAttribute("apiServiceList", apiServiceList);
			model.addAttribute("featureSet",features);
			model.addAttribute("goToLayer",layer);
		}
		catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("apiClsfyList", null);
			model.addAttribute("apiServiceList", null);
			model.addAttribute("featureSet", null);
			
			model.addAttribute("svyCodeLst", null);
			model.addAttribute("ncsstyCodeList", null);
			model.addAttribute("paginationInfo", null);
		}
		
		
		return "sys/gis/mainMap";
	}
	
	/**
	 * @author ipodo
	 * @name getApiServiceList
	 * @param {HttpServletRequest} request - http servlet request
	 * @param {HttpServletResponse} response - http servlet response
	 * @returns 
	 * @throws Exception
	 * @description 주제도 서비스 상세 목록 조회
	 */
	@RequestMapping(value = "/sys/gis/selectApiServiceList.do")
	public void getApiServiceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			map.put("clsfy_nm", request.getParameter("clsfyNm"));
			List<EgovMap> apiServiceList = mapService.selectApiServiceList(map);
			
			result = mapper.writeValueAsString(apiServiceList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
	
	/**
	 * 조사완료 결과 목록 조회
	 * @author DEVWORK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 21.
	 * @modified
	 */
	@RequestMapping(value = "/sys/gis/selectSvyComptLst.do")
	public ModelAndView getSvyComptLst(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Enumeration<String> enu = request.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		
		// 요청변수 저장
		mv.addObject("request", map);
		
		try {
			// 조회 목록 건수
			int totalCnt = mapService.selectSvyComptLstCnt(map);
			// 조회 목록
			List<EgovMap> result = mapService.selectSvyComptLst(map);
			// 벡터 처리
			List<String> features = mapService.selectSvyComptWKT(map);
			
			/* 클러스터용 포인트 */ 
			EgovMap cluster = new EgovMap();
			// 시도
			List<String> clusterSido = mapService.selectClusterSido(map);
			// 시군구
			List<String> clusterSgg = mapService.selectClusterSgg(map);
			// 읍면동
			//List<String> clusterEmd = mapService.selectClusterEmd(map);
			
			cluster.put("sido", clusterSido);
			cluster.put("sgg", clusterSgg);
			//cluster.put("emd", clusterEmd);
			
			mv.addObject("totalCnt", totalCnt);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.select"));
			mv.addObject("response", result);
			mv.addObject("features", features);
			mv.addObject("cluster", cluster);
		} catch (Exception e) {
			mv.addObject("totalCnt", 0);
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			mv.addObject("response", "[]");
			mv.addObject("features", "[]");
			mv.addObject("cluster", "[]");
		}
		
		return mv;
	}	
	
	/**
	 * 조사완료 결과 벡터 조회
	 * @author DEVWORK
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 21.
	 * @modified
	 */
	@RequestMapping(value = "/sys/gis/selectSvyComptWKT.do")
	public ModelAndView getSvyComptWKT(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Enumeration<String> enu = request.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		
		// 요청변수 저장
		mv.addObject("request", map);
		
		try {
			// 조회 목록 건수
			int totalCnt = mapService.selectSvyComptLstCnt(map);
			// 벡터 처리
			List<String> features = mapService.selectSvyComptWKT(map);
			
			/* 클러스터용 포인트 */ 
			EgovMap cluster = new EgovMap();
			// 시도
			List<String> clusterSido = mapService.selectClusterSido(map);
			// 시군구
			List<String> clusterSgg = mapService.selectClusterSgg(map);
			// 읍면동
			//List<String> clusterEmd = mapService.selectClusterEmd(map);
			
			cluster.put("sido", clusterSido);
			cluster.put("sgg", clusterSgg);
			//cluster.put("emd", clusterEmd);
			
			mv.addObject("totalCnt", totalCnt);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.select"));
			mv.addObject("response", features);
			mv.addObject("cluster", cluster);
		} catch (Exception e) {
			mv.addObject("totalCnt", 0);
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			mv.addObject("response", "[]");
			mv.addObject("cluster", "[]");
		}
		
		return mv;
	}
	
	/**
	 * 조사완료 상세페이지 리다이렉트 처리용
	 * @author DEVWORK
	 * @param request
	 * @param response
	 * @param attr
	 * @param model
	 * @param type
	 * @param gid
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 23.
	 * @modified
	 */
	@RequestMapping(value = "/sys/gis/moveSvyComptDetail.do", method = RequestMethod.POST)
	public String moveSvyComptDetail(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr, Model model,
			@RequestParam(value = "type", required = true) String type, @RequestParam(value = "gid", required = true) String gid) throws Exception {
		
		String redirectUrl = "redirect:";
		
		if(type.equals("bsc")) {
			LssBscSvyComptVO searchVO = new LssBscSvyComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/lss/bsc/sct/selectBscSvyComptDetail.do";
		}
		if(type.equals("lcp")) {
			LssLcpSvyComptVO searchVO = new LssLcpSvyComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/lss/lcp/sct/selectLcpSvyComptDetail.do";
		}
		if(type.equals("wka")) {
			LssWkaSvyComptVO searchVO = new LssWkaSvyComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/lss/wka/sct/selectWkaSvyComptDetail.do";
		}
		if(type.equals("cnl")) {
			LssCnlSvyComptVO searchVO = new LssCnlSvyComptVO(); 
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/lss/cnl/sct/selectCnlSvyComptDetail.do";
		}
		if(type.equals("apr")) {
			FckAprComptVO searchVO = new FckAprComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/fck/apr/sct/selectFckAprComptDetail.do";
		}
		if(type.equals("frd")) {
			VytFrdSvyComptVO searchVO = new VytFrdSvyComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/vyt/frd/sct/selectFrdSvyComptDetail.do";
		}
		if(type.equals("mse")) {
			FckMseComptVO searchVO = new FckMseComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/fck/apr/sct/selectFckAprComptDetail.do";
		}
		if(type.equals("pcs")) {
			FckPcsComptVO searchVO = new FckPcsComptVO();
			searchVO.setGid(gid);
			attr.addFlashAttribute("searchVO", searchVO);
			redirectUrl +="/sys/fck/pcs/sct/selectFckPcsComptDetail.do";
		}
		return redirectUrl;
	}
	
	/**
	 * 공간정보 쉐이프파일 다운로드
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/gis/selectSvyComptShpDown.do", method = RequestMethod.POST)
	public void selectSvyComptShpDown(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> enu = request.getParameterNames();
		while(enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		
		String svyType = map.get("type").toString();
		String outFileType = "";
		String smidList = "1=1";
		
		if(svyType.equals("bsc")) {
			outFileType = "기초조사";
			smidList = mapService.selectBscSvyComptLstSmid(map);
		}else if(svyType.equals("apr")) {
			outFileType = "외관점검";
			smidList = mapService.selectAprSvyComptLstSmid(map);
		}else if(svyType.equals("lcp")) {
			outFileType = "땅밀림실태조사";
			smidList = mapService.selectLcpSvyComptLstSmid(map);
		}else if(svyType.equals("wka")) {
			outFileType = "취약지역실태조사";
			smidList = mapService.selectWkaSvyComptLstSmid(map);
		}else if(svyType.equals("cnl")) {
			outFileType = "취약지역해제조사";
			smidList = mapService.selectCnlSvyComptLstSmid(map);
		}else if(svyType.equals("frd")) {
			outFileType = "임도타당성평가";
			smidList = mapService.selectFrdSvyComptLstSmid(map);
		}else if(svyType.equals("pcs")) {
			outFileType = "정밀점검";
			smidList = mapService.selectPcsSvyComptLstSmid(map);
		}else if(svyType.equals("mse")) {
			outFileType = "계측기";
			smidList = mapService.selectMseSvyComptLstSmid(map);
		}
		
		SupMapCommonUtils utils = new SupMapCommonUtils();
		AnalFileVO vo = utils.exportSvyComptShp(svyType,smidList);
		
		String outFileNm = outFileType.concat("_").concat(EgovDateUtil.toString(new Date(), "yyyyMMddHHmmss", null));
		String saveFileNm = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".zip";
		
		boolean isCompressed = EgovFileCmprs.cmprsFile(vo.getFileStreCours()+File.separator+vo.getStreFileNm(), vo.getFileStreCours()+File.separator+vo.getStreFileNm().concat(".zip"));

		EgovFileUtil.rm(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
		
		File uFile = new File(saveFileNm);
		long fSize = uFile.length();
		if (fSize > 0) {
			String mimetype = "application/x-msdownload";

			String userAgent = request.getHeader("User-Agent");
			HashMap<String, String> result = EgovBrowserUtil.getBrowser(userAgent);
			if (!EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY))) {
				mimetype = "application/x-stuff";
			}

			String contentDisposition = EgovBrowserUtil.getDisposition(outFileNm.concat(".zip"), userAgent, "UTF-8");
			// response.setBufferSize(fSize); // OutOfMemeory 발생
			response.setContentType(mimetype);
			// response.setHeader("Content-Disposition", "attachment; filename=\"" +
			// contentDisposition + "\"");
			response.setHeader("Content-Disposition", contentDisposition);
			response.setContentLength(Long.valueOf(fSize).intValue());

			/*
			 * FileCopyUtils.copy(in, response.getOutputStream()); in.close();
			 * response.getOutputStream().flush(); response.getOutputStream().close();
			 */
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
