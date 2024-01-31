package or.sabang.sys.spt.rpt.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
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
import or.sabang.sys.fck.apr.service.FckAprComptService;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.apr.web.FckAprComptController;
import or.sabang.sys.fck.pcs.service.FckPcsComptService;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptService;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptService;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptService;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptBscReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptCnlReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptLcpReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptManageService;
import or.sabang.sys.spt.rpt.service.SptRptPcsReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptReportListVO;
import or.sabang.sys.spt.rpt.service.SptRptWkaReportSvyComptVO;

@Controller
public class SptRptManageController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "sptRptManageService") 	
	private SptRptManageService sptRptManageService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "lssBscSvyComptService") 	
	private LssBscSvyComptService lssBscSvyComptService;
	
	@Resource(name = "lssLcpSvyComptService") 	
	private LssLcpSvyComptService lssLcpSvyComptService;
	
	@Resource(name = "lssWkaSvyComptService") 	
	private LssWkaSvyComptService lssWkaSvyComptService;
	
	@Resource(name = "lssCnlSvyComptService") 	
	private LssCnlSvyComptService lssCnlSvyComptService;
	
	@Resource(name = "fckAprComptService") 	
	private FckAprComptService fckAprComptService;
	
	@Resource(name = "fckPcsComptService") 	
	private FckPcsComptService fckPcsComptService;
	
	@Resource(name = "reportUploadPath")
	String reportUploadPath;
	
	/** 첨부파일 위치 지정  => globals.properties */
    private final String fileStoreDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
    private final String comptFileDir = EgovProperties.getProperty("Globals.fileStorePath.compt");
    
	private static final Logger logger = LoggerFactory.getLogger(SptRptManageController.class);
	
	int cnt_total = 0;

	/**
	 * 기초조사 보고서 목록조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/spt/rpt/selectRptBscManageList.do")
    public String selectRptBscManageList (@ModelAttribute("searchVO") SptRptBscReportSvyComptVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		String pageVal = request.getParameter("pageVal");
		
		/** EgovPropertyService */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		//searchVO.setPageUnit(150);
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** paging */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		if(pageVal == null) {
			searchVO.setPageUnit(150);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}else { 
			int page = Integer.parseInt(pageVal);
			searchVO.setPageUnit(page);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Date date = new Date();
		if(searchVO.getYear() == null) {
			searchVO.setYear(lssBscSvyComptService.selectBscSvyComptMaxYear());
		}
		
		if(searchVO.getMonth() == null) {
			searchVO.setMonth(lssBscSvyComptService.selectBscSvyComptMaxMonth());
			//searchVO.setMonth(new SimpleDateFormat("MM").format(date));
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
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if( searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
			}
		
		//싵태조사 필요성 패스여부
		if(searchVO.getNcsstyPassAt() != null && searchVO.getNcsstyPassAt().equals("true")) {
			searchVO.setNcsstyPassAt("패스");
		}
		
		//관할1목록코드를 코드정보로부터 조회
		vo.setCodeId("FEI001");
		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
		vo.setCodeId(searchVO.getRegion1());
		List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
		model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
		
		//실태조사 필요성 코드조회
		vo.setCodeId("FEI030");
		List<?> ncssty_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("ncsstyCodeList", ncssty_result);
		
		List<SptRptBscReportSvyComptVO> svyComptList = sptRptManageService.selectBscReportSvyComptList(searchVO);
		int total = sptRptManageService.selectBscReportSvyComptListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(total);
		
		model.addAttribute("svyComptList", svyComptList);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sys/spt/rpt/rptBscManageList";
		
	}
	
	/**
	 * 외관점검 보고서 목록조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/spt/rpt/selectRptAprManageList.do")
    public String selectRptAprManageList (@ModelAttribute("searchVO") SptRptAprReportSvyComptVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectRptAprManageList controller");
		
		String pageVal = request.getParameter("pageVal");
		
		/** EgovPropertyService */
		//searchVO.setPageUnit(150);
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		if(pageVal == null) {
			searchVO.setPageUnit(150);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}else { 
			int page = Integer.parseInt(pageVal);
			searchVO.setPageUnit(page);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Date date = new Date();
		if(searchVO.getYear() == null) {
			searchVO.setYear(fckAprComptService.selectFckAprComptMaxYear());
		}
		
		if(searchVO.getMonth() == null) {
			searchVO.setMonth(fckAprComptService.selectFckAprComptMaxMonth());
			//searchVO.setMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("FEI016");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = fckAprComptService.selectFckAprComptYear();
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
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		//최종점검결과 조회
		vo.setCodeId("FEI039");
		List<?> fckRslt_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("fckRsltCodeList", fckRslt_result);
				
		List<SptRptAprReportSvyComptVO> svyComptList = sptRptManageService.selectAprReportSvyComptList(searchVO);
		int total = sptRptManageService.selectAprReportSvyComptListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(total);
		
		model.addAttribute("svyComptList", svyComptList);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sys/spt/rpt/rptAprManageList";
		
	}
	/**
	 * 땅밀림실태조사 보고서 목록조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/spt/rpt/selectRptLcpManageList.do")
    public String selectRptLcpManageList (@ModelAttribute("searchVO") SptRptLcpReportSvyComptVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		String pageVal = request.getParameter("pageVal");
		
		/** EgovPropertyService */
		//searchVO.setPageUnit(150);
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		if(pageVal == null) {
			searchVO.setPageUnit(150);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}else { 
			int page = Integer.parseInt(pageVal);
			searchVO.setPageUnit(page);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Date date = new Date();
		if(searchVO.getYear() == null) {
			searchVO.setYear(lssLcpSvyComptService.selectLcpSvyComptMaxYear());
		}
		
		if(searchVO.getMonth() == null) {
			searchVO.setMonth(lssLcpSvyComptService.selectLcpSvyComptMaxMonth());
			//searchVO.setMonth(new SimpleDateFormat("MM").format(date));
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
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		List<SptRptLcpReportSvyComptVO> svyComptList = sptRptManageService.selectLcpReportSvyComptList(searchVO);
		int total = sptRptManageService.selectLcpReportSvyComptListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(total);
		
		model.addAttribute("svyComptList", svyComptList);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sys/spt/rpt/rptLcpManageList";
		
	}
	
	@RequestMapping(value = "/sys/spt/rpt/selectRptPcsManageList.do")
    public String selectRptPcsManageList (@ModelAttribute("searchVO") SptRptPcsReportSvyComptVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectRptPcsManageList controller");
		
		String pageVal = request.getParameter("pageVal");
		
		/** EgovPropertyService */
		//searchVO.setPageUnit(150);
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		if(pageVal == null) {
			searchVO.setPageUnit(150);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}else { 
			int page = Integer.parseInt(pageVal);
			searchVO.setPageUnit(page);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Date date = new Date();
		if(searchVO.getYear() == null) {
			searchVO.setYear(fckPcsComptService.selectPcsSvyComptMaxYear());
		}
		
		if(searchVO.getMonth() == null) {
			searchVO.setMonth(fckPcsComptService.selectPcsSvyComptMaxMonth());
			//searchVO.setMonth(new SimpleDateFormat("MM").format(date));
		}
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
//		vo.setCodeId("FEI016");
//		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("typeCodeList", type_result);
		
		//연도코드 조회
		List<?> year_result = fckPcsComptService.selectPcsSvyComptYear();
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
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		//최종점검결과 조회
//		vo.setCodeId("FEI039");
//		List<?> fckRslt_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("fckRsltCodeList", fckRslt_result);
				
		List<SptRptPcsReportSvyComptVO> svyComptList = sptRptManageService.selectPcsReportSvyComptList(searchVO);
		int total = sptRptManageService.selectPcsReportSvyComptListTotCnt(searchVO);
		
		paginationInfo.setTotalRecordCount(total);
		
		model.addAttribute("svyComptList", svyComptList);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sys/spt/rpt/rptPcsManageList";
		
	}
	
	/**
	 * 조사완료지 현장사진을 다운로드한다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sys/spt/rpt/selectRptManagePhotoDownload.do")
	public void selectAprComptListPhoto(@RequestParam String rptType, @RequestParam List<String> mstNmList, @RequestParam List<String> svyIdList , HttpServletRequest req,HttpServletResponse res)throws Exception {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		String dt_str = formater.format(new Date()).toString();//현재시간 문자열 생성 
		String timestamp_str = EgovStringUtil.getTimeStamp();//파일 저장 폴더가 동일할 경우 여러명이 기능을 사용할때 삭제가 될 우려가 발생하여 타임스템프 폴더를 생성
		
		if(EgovFileTool.getExistDirectory(comptFileDir.concat(timestamp_str))) {
			EgovFileTool.deleteDirectory(comptFileDir.concat(timestamp_str));
		}
		int comptListSize = mstNmList.size();
		
		String zipNm = rptType+"현장사진_"+dt_str+".zip"; //zip 압축 파일명
		
		SptRptAprReportSvyComptVO searchVO = new SptRptAprReportSvyComptVO();
		
		for(int i=0;i<comptListSize;i++) {
			List<EgovMap> photoInfo = null;
			String svyId,mstNm = null;
			svyId = svyIdList.get(i).toString();
			mstNm = mstNmList.get(i).toString(); //공유방사진폴더명
			System.out.println("svyId : "+ svyId);
			searchVO.setMstNm(mstNm);
			searchVO.setId(svyId);
			
			if(rptType.equals("기초조사")) {
				 photoInfo = lssBscSvyComptService.selectBscSvyComptPhotoInfo(searchVO);
			}else if(rptType.equals("외관점검")) {
				 photoInfo = fckAprComptService.selectFckAprComptPhotoInfo(searchVO);
			}else if(rptType.equals("취약지역 실태조사")) {
				photoInfo = lssWkaSvyComptService.selectWkaSvyComptPhotoInfo(searchVO);
			}else if(rptType.equals("취약지역 해제조사")) {
				photoInfo = lssCnlSvyComptService.selectCnlSvyComptPhotoInfo(searchVO);
			}else if(rptType.equals("땅밀림 실태조사")) {
				 photoInfo = lssLcpSvyComptService.selectLcpSvyComptPhotoInfo(searchVO);
			}else if(rptType.equals("정밀점검")) {
				 photoInfo = fckPcsComptService.selectPcsComptPhotoInfo(searchVO);
			}
			if(!photoInfo.isEmpty() && photoInfo.get(0) != null) {
				if((photoInfo.get(0).containsKey("photo") && photoInfo.get(0).get("photo").toString().length() > 2) || (photoInfo.get(0).containsKey("phototag") && photoInfo.get(0).get("phototag").toString().length() > 2)) {
					
					JSONArray photoTagList = new JSONArray();
					
					List<Object> photoList = new JSONArray(photoInfo.get(0).get("photo").toString()).toList();
			        
				        
					if(photoInfo.get(0).get("phototag") == null) {
						photoTagList = new JSONArray(photoInfo.get(0).get("photo").toString());
					}else {
						JSONArray photoTagArr = new JSONArray(new JSONTokener(photoInfo.get(0).get("phototag").toString()));
						
						Pattern numChk = Pattern.compile("^[0-9\\\\s]+");
				        
						List<JSONObject> jsonValues = new ArrayList<JSONObject>();
						
				        for (int j = 0; j < photoTagArr.length(); j++) {
				        	Matcher matcher = numChk.matcher(photoTagArr.getJSONObject(j).get("TAG").toString());
				        	if(matcher.find()) {
				        		jsonValues.add(photoTagArr.getJSONObject(j));	        		
				        	};	        		
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
				        	for (int i1 = 0; i1 < jsonValues.size(); i1++) {
				        		photoTagList.put(jsonValues.get(i1).get("FILENAME").toString());
				        	}	        	
				        }
					}
			        
					// 디렉토리 생성
					mstNm = mstNm.concat(".ncx");
					if(photoTagList.length() > 0) {			
						File photoTagTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+mstNm+"/"+svyId+"/1.현장사진"));
						EgovFileUtil.rm(photoTagTempFile.toString());
						
						for(int j=0;j < photoTagList.length();j++ ) {
							if(photoTagList.get(j).toString().length() > 0) {
								String fileNm = photoTagList.get(j).toString(); //원본파일명
								String newFileNm = svyId.concat("-"+(j+1))+'.'+FilenameUtils.getExtension(fileNm); //변경된 파일명
								String orginlPhoto = fileStoreDir+fileNm; 
								File photoTagTempFileNm = new File(photoTagTempFile+File.separator+svyId);
								
								if(EgovFileUtil.isExistsFile(orginlPhoto)) {
									if(!photoTagTempFile.exists()) photoTagTempFile.mkdirs();
									if(photoList.indexOf(fileNm) != -1) photoList.remove(photoList.indexOf(fileNm));
									EgovFileUtil.cp(orginlPhoto, photoTagTempFile+File.separator+svyId);//원본이미지 복사
									photoTagTempFileNm.renameTo(new File(photoTagTempFile+File.separator+newFileNm));
								}						
							}
						}
					}
					if(photoList.size() > 0) {
						File photoTempFile = new File(comptFileDir.concat(timestamp_str).concat("/temp/"+mstNm+"/"+svyId+"/2.기타사진"));
						EgovFileUtil.rm(photoTempFile.toString());
						
						for(int j=0; j< photoList.size();j++) {
							if(photoList.get(j).toString().length() > 0) {
								int photoCnt = photoTagList.length()+(j+1); 
								String fileNm = photoList.get(j).toString(); //원본파일명
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
				}
			}else {
				return;
			}
		}
		
		//폴더 압축
		String source = comptFileDir.concat(timestamp_str).concat("/temp");
		String target = comptFileDir.concat(timestamp_str).concat("/"+rptType+"현장사진_"+dt_str+".zip");
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
//				    res.addHeader("Content-Disposition", "attachment; filename="+contentDisposition);
			    res.setHeader("Content-Disposition", contentDisposition);
				fis = new FileInputStream(target);
				bis = new BufferedInputStream(fis);
				

				int n = 0;
				while((n = bis.read(buffer)) > 0){ 
					bos.write(buffer, 0, n); 
					bos.flush(); 
				}
				
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			finally{
				if(bos != null) bos.close(); 
				if(bis != null) bis.close(); 
				if(so != null) so.close(); 
				if(fis != null) fis.close();
				EgovFileTool.deleteDirectory(comptFileDir.concat(timestamp_str));
				logger.info("폴더삭제 : "+comptFileDir.concat(timestamp_str));
			}
		}else {
			if(EgovFileTool.getExistDirectory(comptFileDir.concat(timestamp_str))) {
				EgovFileTool.deleteDirectory(comptFileDir.concat(timestamp_str));
				logger.info("폴더 삭제 : "+comptFileDir.concat(timestamp_str));
			}
			bos.write(0);
			bos.flush();
			bos.close();
		}
	}
	
	/**
	 * 취약지역실태조사 보고서 목록조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/spt/rpt/selectRptWkaManageList.do")
    public String selectRptWkaManageList (@ModelAttribute("searchVO") SptRptWkaReportSvyComptVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		String pageVal = request.getParameter("pageVal");
		
		/** EgovPropertyService */
		//searchVO.setPageUnit(150);
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		if(pageVal == null) {
			searchVO.setPageUnit(150);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}else { 
			int page = Integer.parseInt(pageVal);
			searchVO.setPageUnit(page);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Date date = new Date();
		if(searchVO.getYear() == null) {
			searchVO.setYear(lssWkaSvyComptService.selectWkaSvyComptMaxYear());
		}
		
		if(searchVO.getMonth() == null) {
			searchVO.setMonth(lssWkaSvyComptService.selectWkaSvyComptMaxMonth());
			//searchVO.setMonth(new SimpleDateFormat("MM").format(date));
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
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		//싵태조사 필요성 패스여부
		if(searchVO.getNcsstyPassAt() != null && searchVO.getNcsstyPassAt().equals("true")) {
			searchVO.setNcsstyPassAt("패스");
		}
		
		//관할1목록코드를 코드정보로부터 조회
		vo.setCodeId("FEI001");
		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
		vo.setCodeId(searchVO.getRegion1());
		List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
		model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
		
		//실태조사 필요성 코드조회
		vo.setCodeId("FEI030");
		List<?> ncssty_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("ncsstyCodeList", ncssty_result);
		
		List<SptRptWkaReportSvyComptVO> svyComptList = sptRptManageService.selectWkaReportSvyComptList(searchVO);
		int total = sptRptManageService.selectWkaReportSvyComptListTotCnt(searchVO);
		
		
		paginationInfo.setTotalRecordCount(total);
		
		model.addAttribute("svyComptList", svyComptList);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sys/spt/rpt/rptWkaManageList";
		
	}
	
	/**
	 * 취약지역해제조사 보고서 목록조회
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/spt/rpt/selectRptCnlManageList.do")
	public String selectRptCnlManageList (@ModelAttribute("searchVO") SptRptCnlReportSvyComptVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		String pageVal = request.getParameter("pageVal");
		
		/** EgovPropertyService */
		//searchVO.setPageUnit(150);
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
		
		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		if(pageVal == null) {
			searchVO.setPageUnit(150);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}else { 
			int page = Integer.parseInt(pageVal);
			searchVO.setPageUnit(page);
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		}
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		Date date = new Date();
		if(searchVO.getYear() == null) {
			searchVO.setYear(lssCnlSvyComptService.selectCnlSvyComptMaxYear());
		}
		
		if(searchVO.getMonth() == null) {
			searchVO.setMonth(lssCnlSvyComptService.selectCnlSvyComptMaxMonth());
			//searchVO.setMonth(new SimpleDateFormat("MM").format(date));
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
		if(searchVO.getSd() != null && !searchVO.getSd().trim().isEmpty()) {
			adminVO.setSdCode(searchVO.getSd());		
			List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
			model.addAttribute("sggCodeList",sggCodeList);
		}
		//읍면동코드 조회
		if(searchVO.getSgg() != null && !searchVO.getSgg().trim().isEmpty()) {
			adminVO.setSggCode(searchVO.getSgg());
			List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
			model.addAttribute("emdCodeList",emdCodeList);
		}
		
		//리코드 조회
		if(searchVO.getEmd() != null && !searchVO.getEmd().trim().isEmpty()) {
			adminVO.setEmdCode(searchVO.getEmd());
			List<AdministZoneVO> riCodeList = administZoneService.selectAdministZoneRi(adminVO);
			model.addAttribute("riCodeList",riCodeList);
		}
		
		//싵태조사 필요성 패스여부
		if(searchVO.getNcsstyPassAt() != null && searchVO.getNcsstyPassAt().equals("true")) {
			searchVO.setNcsstyPassAt("패스");
		}
		
		//관할1목록코드를 코드정보로부터 조회
		vo.setCodeId("FEI001");
		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
		vo.setCodeId(searchVO.getRegion1());
		List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
		model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
		
		//실태조사 필요성 코드조회
		vo.setCodeId("FEI030");
		List<?> ncssty_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("ncsstyCodeList", ncssty_result);
		
		List<SptRptCnlReportSvyComptVO> svyComptList = sptRptManageService.selectCnlReportSvyComptList(searchVO);
		int total = sptRptManageService.selectCnlReportSvyComptListTotCnt(searchVO);
		
		
		paginationInfo.setTotalRecordCount(total);

		model.addAttribute("svyComptList", svyComptList);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/spt/rpt/rptCnlManageList";
		
	}
	
	
}