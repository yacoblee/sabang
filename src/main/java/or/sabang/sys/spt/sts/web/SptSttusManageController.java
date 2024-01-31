package or.sabang.sys.spt.sts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.cmm.service.EgovFileMngService;
import or.sabang.cmm.service.EgovFileMngUtil;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptService;
import or.sabang.sys.fck.apr.service.FckAprComptService;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.mse.service.FckMseComptService;
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptService;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsSvySttusService;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptService;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptService;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptService;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.spt.sts.service.SptSttusManageService;
import or.sabang.sys.vyt.frd.service.VytFrdSttusService;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptService;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

@Controller
public class SptSttusManageController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	@Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	@Resource(name = "sptSttusManageService") 	
	private SptSttusManageService sptSttusManageService;
	
	@Resource(name = "fckAprComptService") 	
	private FckAprComptService fckAprComptService;
	
	@Resource(name = "lssBscSvyComptService") 	
	private LssBscSvyComptService lssBscSvyComptService;
	
	@Resource(name = "lssLcpSvyComptService") 	
	private LssLcpSvyComptService lssLcpSvyComptService;
	
	@Resource(name = "lssWkaSvyComptService") 	
	private LssWkaSvyComptService lssWkaSvyComptService;
	
	@Resource(name = "lssCnlSvyComptService") 	
	private LssCnlSvyComptService lssCnlSvyComptService;
	
	@Resource(name = "vytFrdSttusService") 	
	private VytFrdSttusService vytFrdSttusService;

	@Resource(name = "vytFrdSvyComptService") 	
	private VytFrdSvyComptService vytFrdSvyComptService;
	
	@Resource(name = "fckMseComptService")
	private FckMseComptService fckMseComptService;
	
	@Resource(name = "fckPcsSvySttusService") 	
	private FckPcsSvySttusService fckPcsSvySttusService;
	
	@Resource(name = "fckPcsComptService")
	private FckPcsComptService fckPcsComptService;
	
	private static final Logger logger = LoggerFactory.getLogger(SptSttusManageController.class);
	
	int cnt_total = 0;

	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 기초조사 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusBscDetail.do")
    public String sttusBscDetail (@ModelAttribute("searchVO") LssBscSvyComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusBscDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(lssBscSvyComptService.selectBscSvyComptMaxYear());
		}
		
		//연도코드 조회
		List<?> year_result = lssBscSvyComptService.selectBscSvyComptYear();
		model.addAttribute("yearCodeList", year_result);

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
		
		//관할1목록코드를 코드정보로부터 조회
		vo.setCodeId("FEI001");
		List<CmmnDetailCode> region1_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("region1CodeList", region1_result);//관할1목록코드목록
		
		//관할2목록코드를 코드정보로부터 조회
		vo.setCodeId(searchVO.getSvyRegion1());
		List<CmmnDetailCode> region2_result = cmmUseService.selectRegionDetail(vo);
		model.addAttribute("region2CodeList", region2_result);//관할2목록코드목록
		
		List<EgovMap> ncsstyList = sptSttusManageService.selectBscNcsstySttus(searchVO);
		List<EgovMap> yearList = sptSttusManageService.selectBscYearSttus(searchVO);
		List<EgovMap> regionList = sptSttusManageService.selectBscRegionSttus(searchVO);
		List<EgovMap> adminList = sptSttusManageService.selectBscAdministSttus(searchVO);
		List<EgovMap> factorList = sptSttusManageService.selectBscFactorSttus(searchVO);
		
		// 실태조사 필요성 현황
		JSONObject ncsstyObj = new JSONObject();
		for (EgovMap egovMap : ncsstyList) {
			ncsstyObj.put("필요", egovMap.get("ncssty").toString());
			ncsstyObj.put("불필요", egovMap.get("unncssty").toString());
		}
		model.addAttribute("ncsstyResult", ncsstyObj);

		// 관할별 현황
		JSONArray regionArr = new JSONArray();
		JSONArray regionArr1 = new JSONArray();
		JSONArray regionArr2 = new JSONArray();
		JSONArray regionArr3 = new JSONArray();
		regionArr1.put("nm");
		regionArr2.put("필요");
		regionArr3.put("불필요");
		
		for (EgovMap egovMap : regionList) {
			regionArr1.put(egovMap.get("nm").toString());
			regionArr2.put(egovMap.get("ncssty").toString());
			regionArr3.put(egovMap.get("unncssty").toString());
		}
		regionArr.put(regionArr1);
		regionArr.put(regionArr2);
		regionArr.put(regionArr3);
		model.addAttribute("regionResult", regionArr);
		
		// 연도별 현황
		JSONArray yearArr = new JSONArray();
		JSONArray yearArr1 = new JSONArray();
		JSONArray yearArr2 = new JSONArray();
		yearArr1.put("nm");
		yearArr2.put("건수");
		
		for (EgovMap egovMap : yearList) {
			yearArr1.put(egovMap.get("mon").toString());
			yearArr2.put(egovMap.get("cnt").toString());

		}
		yearArr.put(yearArr1);
		yearArr.put(yearArr2);
		model.addAttribute("yearResult", yearArr);
		
		// 행정구역별 현황
		JSONArray adminArr = new JSONArray();
		JSONArray adminArr1 = new JSONArray();
		JSONArray adminArr2 = new JSONArray();
		JSONArray adminArr3 = new JSONArray();
		adminArr1.put("nm");
		adminArr2.put("필요");
		adminArr3.put("불필요");

		for (EgovMap egovMap : adminList) {
		    adminArr1.put(egovMap.get("nm").toString());
		    adminArr2.put(egovMap.get("ncssty").toString());
		    adminArr3.put(egovMap.get("unncssty").toString());
		}
		adminArr.put(adminArr1);
		adminArr.put(adminArr2);
		adminArr.put(adminArr3);
		model.addAttribute("adminResult", adminArr);
		
		//보호대상
		JSONArray saftyscoreArr = new JSONArray();
		JSONArray saftyscoreArr0 = new JSONArray().put("nm");
		JSONArray saftyscoreArr1 = new JSONArray();
		JSONArray saftyscoreArr2 = new JSONArray();
		JSONArray saftyscoreArr3 = new JSONArray();
		JSONArray saftyscoreArr4 = new JSONArray();
		JSONArray saftyscoreArr5 = new JSONArray();
		
		//경사길이
		JSONArray slenscoreArr = new JSONArray();
		JSONArray slenscoreArr0 = new JSONArray().put("nm");
		JSONArray slenscoreArr1 = new JSONArray();
		JSONArray slenscoreArr2 = new JSONArray();
		JSONArray slenscoreArr3 = new JSONArray();
		JSONArray slenscoreArr4 = new JSONArray();
		JSONArray slenscoreArr5 = new JSONArray();

		//경사도
		JSONArray slopescoreArr = new JSONArray();
		JSONArray slopescoreArr0 = new JSONArray().put("nm");
		JSONArray slopescoreArr1 = new JSONArray();
		JSONArray slopescoreArr2 = new JSONArray();
		JSONArray slopescoreArr3 = new JSONArray();
		JSONArray slopescoreArr4 = new JSONArray();
		JSONArray slopescoreArr5 = new JSONArray();
				
		//사면형
		JSONArray sformscoreArr = new JSONArray();
		JSONArray sformscoreArr0 = new JSONArray().put("nm");
		JSONArray sformscoreArr1 = new JSONArray();
		JSONArray sformscoreArr2 = new JSONArray();
		JSONArray sformscoreArr3 = new JSONArray();
		JSONArray sformscoreArr4 = new JSONArray();		
		
		//임상
		JSONArray frstfrscoreArr = new JSONArray();
		JSONArray frstfrscoreArr0 = new JSONArray().put("nm");
		JSONArray frstfrscoreArr1 = new JSONArray();
		JSONArray frstfrscoreArr2 = new JSONArray();
		JSONArray frstfrscoreArr3 = new JSONArray();
		JSONArray frstfrscoreArr4 = new JSONArray();
		JSONArray frstfrscoreArr5 = new JSONArray();		
		
		//모암
		JSONArray prntrckscoreArr = new JSONArray();
		JSONArray prntrckscoreArr0 = new JSONArray().put("nm");
		JSONArray prntrckscoreArr1 = new JSONArray();
		JSONArray prntrckscoreArr2 = new JSONArray();
		JSONArray prntrckscoreArr3 = new JSONArray();
		JSONArray prntrckscoreArr4 = new JSONArray();
		JSONArray prntrckscoreArr5 = new JSONArray();		
		
		//황폐발생원
		JSONArray devoccausescoreArr = new JSONArray();
		JSONArray devoccausescoreArr0 = new JSONArray().put("nm");
		JSONArray devoccausescoreArr1 = new JSONArray();
		JSONArray devoccausescoreArr2= new JSONArray();
		JSONArray devoccausescoreArr3 = new JSONArray();
		JSONArray devoccausescoreArr4 = new JSONArray();
		JSONArray devoccausescoreArr5 = new JSONArray();

		//계류평균검사
		JSONArray trntavgslpscoreArr = new JSONArray();
		JSONArray trntavgslpscoreArr0 = new JSONArray().put("nm");
		JSONArray trntavgslpscoreArr1 = new JSONArray();
		JSONArray trntavgslpscoreArr2= new JSONArray();
		JSONArray trntavgslpscoreArr3 = new JSONArray();
		JSONArray trntavgslpscoreArr4 = new JSONArray();
		JSONArray trntavgslpscoreArr5 = new JSONArray();
		
		//집수면적
		JSONArray wclctareascoreArr = new JSONArray();
		JSONArray wclctareascoreArr0 = new JSONArray().put("nm");
		JSONArray wclctareascoreArr1 = new JSONArray();
		JSONArray wclctareascoreArr2= new JSONArray();
		JSONArray wclctareascoreArr3 = new JSONArray();
		JSONArray wclctareascoreArr4 = new JSONArray();
		JSONArray wclctareascoreArr5 = new JSONArray();
				
		//총계류길이
		JSONArray tltrntltscoreArr = new JSONArray();
		JSONArray tltrntltscoreArr0 = new JSONArray().put("nm");
		JSONArray tltrntltscoreArr1 = new JSONArray();
		JSONArray tltrntltscoreArr2= new JSONArray();
		JSONArray tltrntltscoreArr3 = new JSONArray();
		JSONArray tltrntltscoreArr4 = new JSONArray();
		JSONArray tltrntltscoreArr5 = new JSONArray();
				
		//전석분포비율
		JSONArray distbmdsbratescoreArr = new JSONArray();
		JSONArray distbmdsbratescoreArr0 = new JSONArray().put("nm");
		JSONArray distbmdsbratescoreArr1 = new JSONArray();
		JSONArray distbmdsbratescoreArr2= new JSONArray();
		JSONArray distbmdsbratescoreArr3 = new JSONArray();
		JSONArray distbmdsbratescoreArr4 = new JSONArray();
		JSONArray distbmdsbratescoreArr5 = new JSONArray();	
		
		saftyscoreArr0.put("보호대상");
		saftyscoreArr1.put("0점");
		saftyscoreArr2.put("5점");
		saftyscoreArr3.put("10점");
		saftyscoreArr4.put("15점");
		saftyscoreArr5.put("20점");
		
		slenscoreArr0.put("경사길이");
		slenscoreArr1.put("3점");
		slenscoreArr2.put("8점");
		slenscoreArr3.put("15점");
		slenscoreArr4.put("17점");
		slenscoreArr5.put("20점");
		
		slopescoreArr0.put("경사도");
		slopescoreArr1.put("3점");
		slopescoreArr2.put("8점");
		slopescoreArr3.put("15점");
		slopescoreArr4.put("17점");
		slopescoreArr5.put("20점");
		
		sformscoreArr0.put("사면형");
		sformscoreArr1.put("3점");
		sformscoreArr2.put("5점");
		sformscoreArr3.put("8점");
		sformscoreArr4.put("10점");
		
		frstfrscoreArr0.put("임상");
		frstfrscoreArr1.put("3점");
		frstfrscoreArr2.put("8점");
		frstfrscoreArr3.put("15점");
		frstfrscoreArr4.put("17점");
		frstfrscoreArr5.put("20점");
		
		prntrckscoreArr0.put("모암");
		prntrckscoreArr1.put("2점");
		prntrckscoreArr2.put("4점");
		prntrckscoreArr3.put("6점");
		prntrckscoreArr4.put("8점");
		prntrckscoreArr5.put("10점");
		
		devoccausescoreArr0.put("황폐발생원");
		devoccausescoreArr1.put("0점");
		devoccausescoreArr2.put("3점");
		devoccausescoreArr3.put("5점");
		devoccausescoreArr4.put("7점");
		devoccausescoreArr5.put("10점");
		
		trntavgslpscoreArr0.put("계류평균검사");
		trntavgslpscoreArr1.put("3점");
		trntavgslpscoreArr2.put("9점");
		trntavgslpscoreArr3.put("12점");
		trntavgslpscoreArr4.put("17점");
		trntavgslpscoreArr5.put("20점");
		
		wclctareascoreArr0.put("집수면적");
		wclctareascoreArr1.put("3점");
		wclctareascoreArr2.put("5점");
		wclctareascoreArr3.put("10점");
		wclctareascoreArr4.put("15점");
		wclctareascoreArr5.put("20점");
		
		tltrntltscoreArr0.put("총계류길이");
		tltrntltscoreArr1.put("3점");
		tltrntltscoreArr2.put("5점");
		tltrntltscoreArr3.put("10점");
		tltrntltscoreArr4.put("15점");
		tltrntltscoreArr5.put("20점");
		
		distbmdsbratescoreArr0.put("전석분포비율");
		distbmdsbratescoreArr1.put("2점");
		distbmdsbratescoreArr2.put("4점");
		distbmdsbratescoreArr3.put("6점");
		distbmdsbratescoreArr4.put("8점");
		distbmdsbratescoreArr5.put("10점");
		
		for (EgovMap egovMap : factorList) {
			saftyscoreArr1.put(egovMap.get("saftyscore0").toString());
			saftyscoreArr2.put(egovMap.get("saftyscore5").toString());
			saftyscoreArr3.put(egovMap.get("saftyscore10").toString());
			saftyscoreArr4.put(egovMap.get("saftyscore15").toString());
			saftyscoreArr5.put(egovMap.get("saftyscore20").toString());
			
			slenscoreArr1.put(egovMap.get("slenscore3").toString());
			slenscoreArr2.put(egovMap.get("slenscore8").toString());
			slenscoreArr3.put(egovMap.get("slenscore15").toString());
			slenscoreArr4.put(egovMap.get("slenscore17").toString());
			slenscoreArr5.put(egovMap.get("slenscore20").toString());
			
			slopescoreArr1.put(egovMap.get("slopescore3").toString());
			slopescoreArr2.put(egovMap.get("slopescore8").toString());
			slopescoreArr3.put(egovMap.get("slopescore15").toString());
			slopescoreArr4.put(egovMap.get("slopescore17").toString());
			slopescoreArr5.put(egovMap.get("slopescore20").toString());
			
			sformscoreArr1.put(egovMap.get("sformscore3").toString());
			sformscoreArr2.put(egovMap.get("sformscore5").toString());
			sformscoreArr3.put(egovMap.get("sformscore8").toString());
			sformscoreArr4.put(egovMap.get("sformscore10").toString());
			
			frstfrscoreArr1.put(egovMap.get("frstfrscore3").toString());
			frstfrscoreArr2.put(egovMap.get("frstfrscore8").toString());
			frstfrscoreArr3.put(egovMap.get("frstfrscore15").toString());
			frstfrscoreArr4.put(egovMap.get("frstfrscore17").toString());
			frstfrscoreArr5.put(egovMap.get("frstfrscore20").toString());
			
			prntrckscoreArr1.put(egovMap.get("prntrckscore2").toString());
			prntrckscoreArr2.put(egovMap.get("prntrckscore4").toString());
			prntrckscoreArr3.put(egovMap.get("prntrckscore6").toString());
			prntrckscoreArr4.put(egovMap.get("prntrckscore8").toString());
			prntrckscoreArr5.put(egovMap.get("prntrckscore10").toString());
			
			devoccausescoreArr1.put(egovMap.get("devoccausescore0").toString());
			devoccausescoreArr2.put(egovMap.get("devoccausescore3").toString());
			devoccausescoreArr3.put(egovMap.get("devoccausescore5").toString());
			devoccausescoreArr4.put(egovMap.get("devoccausescore7").toString());
			devoccausescoreArr5.put(egovMap.get("devoccausescore10").toString());
			
			trntavgslpscoreArr1.put(egovMap.get("trntavgslpscore3").toString());
			trntavgslpscoreArr2.put(egovMap.get("trntavgslpscore9").toString());
			trntavgslpscoreArr3.put(egovMap.get("trntavgslpscore12").toString());
			trntavgslpscoreArr4.put(egovMap.get("trntavgslpscore17").toString());
			trntavgslpscoreArr5.put(egovMap.get("trntavgslpscore20").toString());
			
			wclctareascoreArr1.put(egovMap.get("wclctareascore3").toString());
			wclctareascoreArr2.put(egovMap.get("wclctareascore5").toString());
			wclctareascoreArr3.put(egovMap.get("wclctareascore10").toString());
			wclctareascoreArr4.put(egovMap.get("wclctareascore15").toString());
			wclctareascoreArr5.put(egovMap.get("wclctareascore20").toString());
			
			tltrntltscoreArr1.put(egovMap.get("tltrntltscore3").toString());
			tltrntltscoreArr2.put(egovMap.get("tltrntltscore5").toString());
			tltrntltscoreArr3.put(egovMap.get("tltrntltscore10").toString());
			tltrntltscoreArr4.put(egovMap.get("tltrntltscore15").toString());
			tltrntltscoreArr5.put(egovMap.get("tltrntltscore20").toString());
			
			distbmdsbratescoreArr1.put(egovMap.get("distbmdsbratescore2").toString());
			distbmdsbratescoreArr2.put(egovMap.get("distbmdsbratescore4").toString());
			distbmdsbratescoreArr3.put(egovMap.get("distbmdsbratescore6").toString());
			distbmdsbratescoreArr4.put(egovMap.get("distbmdsbratescore8").toString());
			distbmdsbratescoreArr5.put(egovMap.get("distbmdsbratescore10").toString());
		}

		saftyscoreArr.put(saftyscoreArr0);
		saftyscoreArr.put(saftyscoreArr1);
		saftyscoreArr.put(saftyscoreArr2);
		saftyscoreArr.put(saftyscoreArr3);
		saftyscoreArr.put(saftyscoreArr4);
		saftyscoreArr.put(saftyscoreArr5);
		model.addAttribute("saftyscoreResult", saftyscoreArr);
		
		slenscoreArr.put(slenscoreArr0);
		slenscoreArr.put(slenscoreArr1);
		slenscoreArr.put(slenscoreArr2);
		slenscoreArr.put(slenscoreArr3);
		slenscoreArr.put(slenscoreArr4);
		slenscoreArr.put(slenscoreArr5);
		model.addAttribute("slenscoreResult", slenscoreArr);
		
		slopescoreArr.put(slopescoreArr0);
		slopescoreArr.put(slopescoreArr1);
		slopescoreArr.put(slopescoreArr2);
		slopescoreArr.put(slopescoreArr3);
		slopescoreArr.put(slopescoreArr4);
		slopescoreArr.put(slopescoreArr5);
		model.addAttribute("slopescoreResult", slopescoreArr);
		
		sformscoreArr.put(sformscoreArr0);
		sformscoreArr.put(sformscoreArr1);
		sformscoreArr.put(sformscoreArr2);
		sformscoreArr.put(sformscoreArr3);
		sformscoreArr.put(sformscoreArr4);
		model.addAttribute("sformscoreResult", sformscoreArr);
		
		frstfrscoreArr.put(frstfrscoreArr0);
		frstfrscoreArr.put(frstfrscoreArr1);
		frstfrscoreArr.put(frstfrscoreArr2);
		frstfrscoreArr.put(frstfrscoreArr3);
		frstfrscoreArr.put(frstfrscoreArr4);
		frstfrscoreArr.put(frstfrscoreArr5);
		model.addAttribute("frstfrscoreResult", frstfrscoreArr);
		
		prntrckscoreArr.put(prntrckscoreArr0);
		prntrckscoreArr.put(prntrckscoreArr1);
		prntrckscoreArr.put(prntrckscoreArr2);
		prntrckscoreArr.put(prntrckscoreArr3);
		prntrckscoreArr.put(prntrckscoreArr4);
		prntrckscoreArr.put(prntrckscoreArr5);
		model.addAttribute("prntrckscoreResult", prntrckscoreArr);
		
		devoccausescoreArr.put(devoccausescoreArr0);
		devoccausescoreArr.put(devoccausescoreArr1);
		devoccausescoreArr.put(devoccausescoreArr2);
		devoccausescoreArr.put(devoccausescoreArr3);
		devoccausescoreArr.put(devoccausescoreArr4);
		devoccausescoreArr.put(devoccausescoreArr5);
		model.addAttribute("devoccausescoreResult", devoccausescoreArr);
		
		trntavgslpscoreArr.put(trntavgslpscoreArr0);
		trntavgslpscoreArr.put(trntavgslpscoreArr1);
		trntavgslpscoreArr.put(trntavgslpscoreArr2);
		trntavgslpscoreArr.put(trntavgslpscoreArr3);
		trntavgslpscoreArr.put(trntavgslpscoreArr4);
		trntavgslpscoreArr.put(trntavgslpscoreArr5);
		model.addAttribute("trntavgslpscoreResult", trntavgslpscoreArr);
		
		wclctareascoreArr.put(wclctareascoreArr0);
		wclctareascoreArr.put(wclctareascoreArr1);
		wclctareascoreArr.put(wclctareascoreArr2);
		wclctareascoreArr.put(wclctareascoreArr3);
		wclctareascoreArr.put(wclctareascoreArr4);
		wclctareascoreArr.put(wclctareascoreArr5);
		model.addAttribute("wclctareascoreResult", wclctareascoreArr);
		
		tltrntltscoreArr.put(tltrntltscoreArr0);
		tltrntltscoreArr.put(tltrntltscoreArr1);
		tltrntltscoreArr.put(tltrntltscoreArr2);
		tltrntltscoreArr.put(tltrntltscoreArr3);
		tltrntltscoreArr.put(tltrntltscoreArr4);
		tltrntltscoreArr.put(tltrntltscoreArr5);
		model.addAttribute("tltrntltscoreResult", tltrntltscoreArr);
		
		distbmdsbratescoreArr.put(distbmdsbratescoreArr0);
		distbmdsbratescoreArr.put(distbmdsbratescoreArr1);
		distbmdsbratescoreArr.put(distbmdsbratescoreArr2);
		distbmdsbratescoreArr.put(distbmdsbratescoreArr3);
		distbmdsbratescoreArr.put(distbmdsbratescoreArr4);
		distbmdsbratescoreArr.put(distbmdsbratescoreArr5);
		model.addAttribute("distbmdsbratescoreResult", distbmdsbratescoreArr);
		
		return "sys/spt/sts/sttusBscDetail";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 외관점검 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusAprDetail.do")
    public String sttusAprDetail (@ModelAttribute("searchVO") FckAprComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusAprDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI016");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(fckAprComptService.selectFckAprComptMaxYear());
		}
		
		//연도코드 조회
		List<?> year_result = fckAprComptService.selectFckAprComptYear();
		model.addAttribute("yearCodeList", year_result);

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
		
//		List<EgovMap> yearList = sptSttusManageService.selectAprYearSttus(searchVO);
		List<EgovMap> adminList = sptSttusManageService.selectAprAdministSttus(searchVO);
		List<EgovMap> factorList = sptSttusManageService.selectAprFactorSttus(searchVO);
		
		
		List<EgovMap> fckrsltList = sptSttusManageService.selectAprRsltSttus(searchVO);
		
		JSONObject fckrsltObj = new JSONObject();
		JSONObject mngmtrObj = new JSONObject();
		JSONObject appnrelisObj = new JSONObject();
				
		for (EgovMap egovMap : fckrsltList) {
			// 최종점검결과
			fckrsltObj.put("양호", egovMap.get("fckrsltGood").toString());
			fckrsltObj.put("관찰필요", egovMap.get("fckrsltNeed").toString());
			fckrsltObj.put("불량", egovMap.get("fckrsltBadn").toString());
			fckrsltObj.put("없음", egovMap.get("fckrsltNone").toString());
			// 조치사항
			mngmtrObj.put("보수", egovMap.get("mngmtrMend").toString());
			mngmtrObj.put("정밀점검", egovMap.get("mngmtrChck").toString());
			mngmtrObj.put("없음", egovMap.get("mngmtrNone").toString());
			// 사방지지정해제여부
			appnrelisObj.put("해제가능", egovMap.get("appnrelisRelis").toString());
			appnrelisObj.put("유지", egovMap.get("appnrelisMntnc").toString());
		}
		model.addAttribute("fckrsltResult", fckrsltObj);
		model.addAttribute("mngmtrResult", mngmtrObj);
		model.addAttribute("appnrelisResult", appnrelisObj);
		
		// 행정구역별 현황
		JSONArray adminArr = new JSONArray();
		JSONArray adminArr1 = new JSONArray().put("nm");
		JSONArray adminArr2 = new JSONArray().put("양호");
		JSONArray adminArr3 = new JSONArray().put("관찰필요");
		JSONArray adminArr4 = new JSONArray().put("불량");
		JSONArray adminArr5 = new JSONArray().put("없음");

		for (EgovMap egovMap : adminList) {
			adminArr.put(adminArr1.put(egovMap.get("nm").toString()));
			adminArr.put(adminArr2.put(egovMap.get("fckrsltGood").toString()));
			adminArr.put(adminArr3.put(egovMap.get("fckrsltNeed").toString()));
			adminArr.put(adminArr4.put(egovMap.get("fckrsltBadn").toString()));
			adminArr.put(adminArr5.put(egovMap.get("fckrsltNone").toString()));
		}
		model.addAttribute("adminResult", adminArr);
		
		// 부대시설 - 수문
		JSONArray flugtArr = new JSONArray();
		JSONArray flugtArr0 = new JSONArray().put("nm");
		JSONArray flugtArr1 = new JSONArray().put("양호");
		JSONArray flugtArr2 = new JSONArray().put("관찰필요");
		JSONArray flugtArr3 = new JSONArray().put("불필요");
		JSONArray flugtArr4 = new JSONArray().put("없음");
		
		// 부대시설 - 식생상태
		JSONArray vtnsttusArr = new JSONArray();
		JSONArray vtnsttusArr0 = new JSONArray().put("nm");
		JSONArray vtnsttusArr1 = new JSONArray().put("양호");
		JSONArray vtnsttusArr2 = new JSONArray().put("관찰필요");
		JSONArray vtnsttusArr3 = new JSONArray().put("불필요");
		JSONArray vtnsttusArr4 = new JSONArray().put("없음");
		
		// 부대시설 - 안전시설
		JSONArray sffctArr = new JSONArray();
		JSONArray sffctArr0 = new JSONArray().put("nm");
		JSONArray sffctArr1 = new JSONArray().put("양호");
		JSONArray sffctArr2 = new JSONArray().put("관찰필요");
		JSONArray sffctArr3 = new JSONArray().put("불필요");
		JSONArray sffctArr4 = new JSONArray().put("없음");
		
		// 부대시설 - 접근도로
		JSONArray accssrdArr = new JSONArray();
		JSONArray accssrdArr0 = new JSONArray().put("nm");
		JSONArray accssrdArr1 = new JSONArray().put("양호");
		JSONArray accssrdArr2 = new JSONArray().put("관찰필요");
		JSONArray accssrdArr3 = new JSONArray().put("불필요");
		JSONArray accssrdArr4 = new JSONArray().put("없음");
		
		// 부대시설 - 기타
		JSONArray etcArr = new JSONArray();
		JSONArray etcArr0 = new JSONArray().put("nm");
		JSONArray etcArr1 = new JSONArray().put("양호");
		JSONArray etcArr2 = new JSONArray().put("관찰필요");
		JSONArray etcArr3 = new JSONArray().put("불필요");
		JSONArray etcArr4 = new JSONArray().put("없음");

		// 사방댐 - 본댐 판정값
		JSONArray orginldamArr = new JSONArray();
		JSONArray orginldamArr0 = new JSONArray().put("nm");
		JSONArray orginldamArr1 = new JSONArray().put("양호");
		JSONArray orginldamArr2 = new JSONArray().put("관찰필요");
		JSONArray orginldamArr3 = new JSONArray().put("불필요");
		JSONArray orginldamArr4 = new JSONArray().put("없음");
		
		// 사방댐 - 측벽 판정값
		JSONArray sidewallArr = new JSONArray();
		JSONArray sidewallArr0 = new JSONArray().put("nm");
		JSONArray sidewallArr1 = new JSONArray().put("양호");
		JSONArray sidewallArr2 = new JSONArray().put("관찰필요");
		JSONArray sidewallArr3 = new JSONArray().put("불필요");
		JSONArray sidewallArr4 = new JSONArray().put("없음");
		
		// 사방댐 - 물받이 판정값
		JSONArray dwnsptArr = new JSONArray();
		JSONArray dwnsptArr0 = new JSONArray().put("nm");
		JSONArray dwnsptArr1 = new JSONArray().put("양호");
		JSONArray dwnsptArr2 = new JSONArray().put("관찰필요");
		JSONArray dwnsptArr3 = new JSONArray().put("불필요");
		JSONArray dwnsptArr4 = new JSONArray().put("없음");
		
		// 사방댐 - 저사상태 판정값
		JSONArray snddpsitArr = new JSONArray();
		JSONArray snddpsitArr0 = new JSONArray().put("nm");
		JSONArray snddpsitArr1 = new JSONArray().put("저(50% 미만)");
		JSONArray snddpsitArr2 = new JSONArray().put("중(50%~80% 미만)");
		JSONArray snddpsitArr3 = new JSONArray().put("고(80% 이상)");
		
		// 계류보전 - 골막이 판정값
		JSONArray chkdamArr = new JSONArray();
		JSONArray chkdamArr0 = new JSONArray().put("nm");
		JSONArray chkdamArr1 = new JSONArray().put("양호");
		JSONArray chkdamArr2 = new JSONArray().put("관찰필요");
		JSONArray chkdamArr3 = new JSONArray().put("불필요");
		JSONArray chkdamArr4 = new JSONArray().put("없음");
		
		// 계류보전 - 기슭막이 판정값
		JSONArray rvtmntArr = new JSONArray();
		JSONArray rvtmntArr0 = new JSONArray().put("nm");
		JSONArray rvtmntArr1 = new JSONArray().put("양호");
		JSONArray rvtmntArr2 = new JSONArray().put("관찰필요");
		JSONArray rvtmntArr3 = new JSONArray().put("불필요");
		JSONArray rvtmntArr4 = new JSONArray().put("없음");
		
		// 계류보전 - 바닥막이 판정값
		JSONArray grdstablArr = new JSONArray();
		JSONArray grdstablArr0 = new JSONArray().put("nm");
		JSONArray grdstablArr1 = new JSONArray().put("양호");
		JSONArray grdstablArr2 = new JSONArray().put("관찰필요");
		JSONArray grdstablArr3 = new JSONArray().put("불필요");
		JSONArray grdstablArr4 = new JSONArray().put("없음");
		
		// 계류보전 - 계류상태 판정값
		JSONArray mooringArr = new JSONArray();
		JSONArray mooringArr0 = new JSONArray().put("nm");
		JSONArray mooringArr1 = new JSONArray().put("양호");
		JSONArray mooringArr2 = new JSONArray().put("관찰필요");
		JSONArray mooringArr3 = new JSONArray().put("불필요");
		JSONArray mooringArr4 = new JSONArray().put("없음");
		
		// 산지사방 - 보강시설 판정값
		JSONArray reinfcArr = new JSONArray();
		JSONArray reinfcArr0 = new JSONArray().put("nm");
		JSONArray reinfcArr1 = new JSONArray().put("양호");
		JSONArray reinfcArr2 = new JSONArray().put("관찰필요");
		JSONArray reinfcArr3 = new JSONArray().put("불필요");
		JSONArray reinfcArr4 = new JSONArray().put("없음");
		
		// 산지사방 - 보호시설 판정값
		JSONArray prtcArr = new JSONArray();
		JSONArray prtcArr0 = new JSONArray().put("nm");
		JSONArray prtcArr1 = new JSONArray().put("양호");
		JSONArray prtcArr2 = new JSONArray().put("관찰필요");
		JSONArray prtcArr3 = new JSONArray().put("불필요");
		JSONArray prtcArr4 = new JSONArray().put("없음");
		
		// 산지사방 - 배수시설 판정값
		JSONArray drngArr = new JSONArray();
		JSONArray drngArr0 = new JSONArray().put("nm");
		JSONArray drngArr1 = new JSONArray().put("양호");
		JSONArray drngArr2 = new JSONArray().put("관찰필요");
		JSONArray drngArr3 = new JSONArray().put("불필요");
		JSONArray drngArr4 = new JSONArray().put("없음");
		
		// 산지사방 - 사면상태 판정값
		JSONArray slopeArr = new JSONArray();
		JSONArray slopeArr0 = new JSONArray().put("nm");
		JSONArray slopeArr1 = new JSONArray().put("양호");
		JSONArray slopeArr2 = new JSONArray().put("관찰필요");
		JSONArray slopeArr3 = new JSONArray().put("불필요");
		JSONArray slopeArr4 = new JSONArray().put("없음");
		
		for (EgovMap egovMap : factorList) {
			
			// 부대시설:S
			flugtArr.put(flugtArr0.put("수문"));
			flugtArr.put(flugtArr1.put(egovMap.get("flugtjdgvalGood").toString()));
			flugtArr.put(flugtArr2.put(egovMap.get("flugtjdgvalNeed").toString()));
			flugtArr.put(flugtArr3.put(egovMap.get("flugtjdgvalNdls").toString()));
			flugtArr.put(flugtArr4.put(egovMap.get("flugtjdgvalNone").toString()));
			
			vtnsttusArr.put(vtnsttusArr0.put("식생상태"));
			vtnsttusArr.put(vtnsttusArr1.put(egovMap.get("vtnsttusjdgvalGood").toString()));
			vtnsttusArr.put(vtnsttusArr2.put(egovMap.get("vtnsttusjdgvalNeed").toString()));
			vtnsttusArr.put(vtnsttusArr3.put(egovMap.get("vtnsttusjdgvalNdls").toString()));
			vtnsttusArr.put(vtnsttusArr4.put(egovMap.get("vtnsttusjdgvalNone").toString()));
			
			sffctArr.put(sffctArr0.put("안전시설"));
			sffctArr.put(sffctArr1.put(egovMap.get("sffcjdgvalGood").toString()));
			sffctArr.put(sffctArr2.put(egovMap.get("sffcjdgvalNeed").toString()));
			sffctArr.put(sffctArr3.put(egovMap.get("sffcjdgvalNdls").toString()));
			sffctArr.put(sffctArr4.put(egovMap.get("sffcjdgvalNone").toString()));
			
			accssrdArr.put(accssrdArr0.put("접근도로"));
			accssrdArr.put(accssrdArr1.put(egovMap.get("accssrdjdgvalGood").toString()));
			accssrdArr.put(accssrdArr2.put(egovMap.get("accssrdjdgvalNeed").toString()));
			accssrdArr.put(accssrdArr3.put(egovMap.get("accssrdjdgvalNdls").toString()));
			accssrdArr.put(accssrdArr4.put(egovMap.get("accssrdjdgvalNone").toString()));
			
			etcArr.put(etcArr0.put("기타"));
			etcArr.put(etcArr1.put(egovMap.get("etcjdgvalGood").toString()));
			etcArr.put(etcArr2.put(egovMap.get("etcjdgvalNeed").toString()));
			etcArr.put(etcArr3.put(egovMap.get("etcjdgvalNdls").toString()));
			etcArr.put(etcArr4.put(egovMap.get("etcjdgvalNone").toString()));
			
			model.addAttribute("flugtResult", flugtArr);
			model.addAttribute("vtnsttusResult", vtnsttusArr);
			model.addAttribute("sffctResult", sffctArr);
			model.addAttribute("accssrdResult", accssrdArr);
			model.addAttribute("etcResult", etcArr);
			// 부대시설: E
			
			// 사방댐:S
			orginldamArr.put(orginldamArr0.put("본댐"));
			orginldamArr.put(orginldamArr1.put(egovMap.get("orginldamjdgvalGood").toString()));
			orginldamArr.put(orginldamArr2.put(egovMap.get("orginldamjdgvalNeed").toString()));
			orginldamArr.put(orginldamArr3.put(egovMap.get("orginldamjdgvalNdls").toString()));
			orginldamArr.put(orginldamArr4.put(egovMap.get("orginldamjdgvalNone").toString()));
			
			sidewallArr.put(sidewallArr0.put("측벽"));
			sidewallArr.put(sidewallArr1.put(egovMap.get("sidewalljdgvalGood").toString()));
			sidewallArr.put(sidewallArr2.put(egovMap.get("sidewalljdgvalNeed").toString()));
			sidewallArr.put(sidewallArr3.put(egovMap.get("sidewalljdgvalNdls").toString()));
			sidewallArr.put(sidewallArr4.put(egovMap.get("sidewalljdgvalNone").toString()));
			
			dwnsptArr.put(dwnsptArr0.put("물받이"));
			dwnsptArr.put(dwnsptArr1.put(egovMap.get("dwnsptjdgvalGood").toString()));
			dwnsptArr.put(dwnsptArr2.put(egovMap.get("dwnsptjdgvalNeed").toString()));
			dwnsptArr.put(dwnsptArr3.put(egovMap.get("dwnsptjdgvalNdls").toString()));
			dwnsptArr.put(dwnsptArr4.put(egovMap.get("dwnsptjdgvalNone").toString()));
			
			snddpsitArr.put(snddpsitArr0.put("저사상태"));
			snddpsitArr.put(snddpsitArr1.put(egovMap.get("snddpsitjdgvalLow").toString()));
			snddpsitArr.put(snddpsitArr2.put(egovMap.get("snddpsitjdgvalMid").toString()));
			snddpsitArr.put(snddpsitArr3.put(egovMap.get("snddpsitjdgvalHigh").toString()));
			
			model.addAttribute("orginldamResult", orginldamArr);
			model.addAttribute("sidewallResult", sidewallArr);
			model.addAttribute("dwnsptResult", dwnsptArr);
			model.addAttribute("snddpsitResult", snddpsitArr);
			// 사방댐:E
			
			// 계류보전:S
			chkdamArr.put(chkdamArr0.put("골막이"));
			chkdamArr.put(chkdamArr1.put(egovMap.get("chkdamjdgvalGood").toString()));
			chkdamArr.put(chkdamArr2.put(egovMap.get("chkdamjdgvalNeed").toString()));
			chkdamArr.put(chkdamArr3.put(egovMap.get("chkdamjdgvalNdls").toString()));
			chkdamArr.put(chkdamArr4.put(egovMap.get("chkdamjdgvalNone").toString()));
			
			rvtmntArr.put(rvtmntArr0.put("기슭막이"));
			rvtmntArr.put(rvtmntArr1.put(egovMap.get("rvtmntjdgvalGood").toString()));
			rvtmntArr.put(rvtmntArr2.put(egovMap.get("rvtmntjdgvalNeed").toString()));
			rvtmntArr.put(rvtmntArr3.put(egovMap.get("rvtmntjdgvalNdls").toString()));
			rvtmntArr.put(rvtmntArr4.put(egovMap.get("rvtmntjdgvalNone").toString()));
			
			grdstablArr.put(grdstablArr0.put("바닥막이"));
			grdstablArr.put(grdstablArr1.put(egovMap.get("grdstabljdgvalGood").toString()));
			grdstablArr.put(grdstablArr2.put(egovMap.get("grdstabljdgvalNeed").toString()));
			grdstablArr.put(grdstablArr3.put(egovMap.get("grdstabljdgvalNdls").toString()));
			grdstablArr.put(grdstablArr4.put(egovMap.get("grdstabljdgvalNone").toString()));
			
			mooringArr.put(mooringArr0.put("계류상태"));
			mooringArr.put(mooringArr1.put(egovMap.get("mooringjdgvalGood").toString()));
			mooringArr.put(mooringArr2.put(egovMap.get("mooringjdgvalNeed").toString()));
			mooringArr.put(mooringArr3.put(egovMap.get("mooringjdgvalNdls").toString()));
			mooringArr.put(mooringArr4.put(egovMap.get("mooringjdgvalNone").toString()));
			
			model.addAttribute("chkdamResult", chkdamArr);
			model.addAttribute("rvtmntResult", rvtmntArr);
			model.addAttribute("grdstablResult", grdstablArr);
			model.addAttribute("mooringResult", mooringArr);
			// 계류보전:E
			
			// 산지사방:S
			reinfcArr.put(reinfcArr0.put("보강시설"));
			reinfcArr.put(reinfcArr1.put(egovMap.get("reinfcjdgvalGood").toString()));
			reinfcArr.put(reinfcArr2.put(egovMap.get("reinfcjdgvalNeed").toString()));
			reinfcArr.put(reinfcArr3.put(egovMap.get("reinfcjdgvalNdls").toString()));
			reinfcArr.put(reinfcArr4.put(egovMap.get("reinfcjdgvalNone").toString()));
			
			prtcArr.put(prtcArr0.put("보호시설"));
			prtcArr.put(prtcArr1.put(egovMap.get("prtcjdgvalGood").toString()));
			prtcArr.put(prtcArr2.put(egovMap.get("prtcjdgvalNeed").toString()));
			prtcArr.put(prtcArr3.put(egovMap.get("prtcjdgvalNdls").toString()));
			prtcArr.put(prtcArr4.put(egovMap.get("prtcjdgvalNone").toString()));
			
			drngArr.put(drngArr0.put("배수시설"));
			drngArr.put(drngArr1.put(egovMap.get("drngjdgvalGood").toString()));
			drngArr.put(drngArr2.put(egovMap.get("drngjdgvalNeed").toString()));
			drngArr.put(drngArr3.put(egovMap.get("drngjdgvalNdls").toString()));
			drngArr.put(drngArr4.put(egovMap.get("drngjdgvalNone").toString()));
			
			slopeArr.put(slopeArr0.put("사면상태"));
			slopeArr.put(slopeArr1.put(egovMap.get("slopejdgvalGood").toString()));
			slopeArr.put(slopeArr2.put(egovMap.get("slopejdgvalNeed").toString()));
			slopeArr.put(slopeArr3.put(egovMap.get("slopejdgvalNdls").toString()));
			slopeArr.put(slopeArr4.put(egovMap.get("slopejdgvalNone").toString()));
			
			model.addAttribute("reinfcResult", reinfcArr);
			model.addAttribute("prtcResult", prtcArr);
			model.addAttribute("drngResult", drngArr);
			model.addAttribute("slopeResult", slopeArr);
			// 산지사방:E
		}
		
		return "sys/spt/sts/sttusAprDetail";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 땅밀림실태조사 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusLcpDetail.do")
    public String sttusLcpDetail (@ModelAttribute("searchVO") LssLcpSvyComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusCnlDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		if(searchVO.getSvyYear() == null || searchVO.getSvyYear().isEmpty()) {
			searchVO.setSvyYear(lssLcpSvyComptService.selectLcpSvyComptMaxYear());
		}
		
		// json convert
		ObjectMapper mapper = new ObjectMapper();
		
		//연도코드 조회
		List<?> year_result = lssLcpSvyComptService.selectLcpSvyComptYear();
		model.addAttribute("yearCodeList", year_result);
		
		AdministZoneVO adminVO = new AdministZoneVO();
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		//시군구코드 조회		
		adminVO.setSdCode(searchVO.getSvySd());		
		List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
		//읍면동코드 조회		
		adminVO.setSggCode(searchVO.getSvySgg());
		List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
		
//		model.addAttribute("sdCodeList",sdCodeList);
//		model.addAttribute("sggCodeList",sggCodeList);
//		model.addAttribute("emdCodeList",emdCodeList);
		
		//시도별 통계
		List<EgovMap> adminList = sptSttusManageService.selectLcpAdministSttus(searchVO);
		//판정등급 통계
		List<EgovMap> lastgrdList = sptSttusManageService.selectLcpLastgrdSttus(searchVO);
		// 주 구성암석별
		List<EgovMap>  cmprokvalList = sptSttusManageService.selectLcpCmprokvalSttus(searchVO);
		//  타 지층 및 관입암
		List<EgovMap>  instrokatList = sptSttusManageService.selectLcpInstrokatSttus(searchVO);
		// 암석 풍화
		List<EgovMap>  rokwthrvalList = sptSttusManageService.selectLcpRokwthrvalSttus(searchVO);
		//  지질구조
		List<EgovMap>  geologyList = sptSttusManageService.selectLcpGeologySttus(searchVO);
		//  토양형
		List<EgovMap>  soiltyList = sptSttusManageService.selectLcpSoiltySttus(searchVO);
		//  토심(cm) B층 까지 깊이
		List<EgovMap>  soildeptbvalList = sptSttusManageService.selectLcpSoildeptbvalSttus(searchVO);
		//  토성 B층 기준 (약 30cm 부근)
		List<EgovMap>  soilclassbvalList = sptSttusManageService.selectLcpSoilclassbvalSttus(searchVO);
		//  토양 구조 B층 기준
		List<EgovMap>  soilstrctList = sptSttusManageService.selectLcpSoilstrctSttus(searchVO);
		//  토양 수분 상태
		List<EgovMap>  soilwtrvalList = sptSttusManageService.selectLcpSoilwtrvalSttus(searchVO);
		//  암석 노출도
		List<EgovMap>  rokexpsrList = sptSttusManageService.selectLcpRokexpsrSttus(searchVO);
		//  너덜유무
		List<EgovMap>  talusatList = sptSttusManageService.selectLcpTalusatSttus(searchVO);
		//  지형구분
		List<EgovMap>  tpgrphvalList = sptSttusManageService.selectLcpTpgrphvalSttus(searchVO);
		//  지형특성
		List<EgovMap>  lnformList = sptSttusManageService.selectLcpLnformSttus(searchVO);
		//  수리특성
		List<EgovMap>  lcpWtrList = sptSttusManageService.selectLcpWtrSttus(searchVO);
		//  임상
		List<EgovMap>  lcpFrstfrvalList = sptSttusManageService.selectLcpFrstfrvalSttus(searchVO);
		//  징후여부
		List<EgovMap>  lcpDirsymptmList = sptSttusManageService.selectLcpDirsymptmSttus(searchVO);
		
		model.addAttribute("adminList",mapper.writeValueAsString(adminList));
		model.addAttribute("lastgrdList",mapper.writeValueAsString(lastgrdList));
		model.addAttribute("cmprokvalList",mapper.writeValueAsString(cmprokvalList));
		model.addAttribute("instrokatList",mapper.writeValueAsString(instrokatList));
		model.addAttribute("rokwthrvalList",mapper.writeValueAsString(rokwthrvalList));
		model.addAttribute("geologyList",mapper.writeValueAsString(geologyList));
		model.addAttribute("soiltyList",mapper.writeValueAsString(soiltyList));
		model.addAttribute("soildeptbvalList",mapper.writeValueAsString(soildeptbvalList));
		model.addAttribute("soilclassbvalList",mapper.writeValueAsString(soilclassbvalList));
		model.addAttribute("soilstrctList",mapper.writeValueAsString(soilstrctList));
		model.addAttribute("soilwtrvalList",mapper.writeValueAsString(soilwtrvalList));
		model.addAttribute("rokexpsrList",mapper.writeValueAsString(rokexpsrList));
		model.addAttribute("talusatList",mapper.writeValueAsString(talusatList));
		model.addAttribute("tpgrphvalList",mapper.writeValueAsString(tpgrphvalList));
		model.addAttribute("lnformList",mapper.writeValueAsString(lnformList));
		model.addAttribute("lcpWtrList",mapper.writeValueAsString(lcpWtrList));
		model.addAttribute("lcpFrstfrvalList",mapper.writeValueAsString(lcpFrstfrvalList));
		model.addAttribute("lcpDirsymptmList",mapper.writeValueAsString(lcpDirsymptmList));
		
		
		return "sys/spt/sts/sttusLcpDetail";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 취약지역실태조사 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusWkaDetail.do")
    public String sttusWkaDetail (@ModelAttribute("searchVO") LssWkaSvyComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusWkaDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(lssWkaSvyComptService.selectWkaSvyComptMaxYear());
		}
		
		// json convert
		ObjectMapper mapper = new ObjectMapper();
		
		//연도코드 조회
		List<?> year_result = lssWkaSvyComptService.selectWkaSvyComptYear();
		model.addAttribute("yearCodeList", year_result);

		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회		
		adminVO.setSdCode(searchVO.getSvySd());		
		List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
		model.addAttribute("sggCodeList",sggCodeList);

		//읍면동코드 조회		
		adminVO.setSggCode(searchVO.getSvySgg());
		List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
		model.addAttribute("emdCodeList",emdCodeList);
		
//		List<EgovMap> yearList = sptSttusManageService.selectAprYearSttus(searchVO);
		//시도별 통계
		List<EgovMap> adminList = sptSttusManageService.selectWkaAdministSttus(searchVO);
		//판정등급 통계
		List<EgovMap> jdgmntgradList = sptSttusManageService.selectWkaJdgmntgradSttus(searchVO);
		//보호시설
		List<EgovMap>  safefctList = sptSttusManageService.selectWkaSafefctSttus(searchVO);
		//인가/호수 현황
		List<EgovMap>  houseLakeList = sptSttusManageService.selectWkaHouseLakeSttus(searchVO);
		//상부주요시설
		List<EgovMap>  highmainfctList = sptSttusManageService.selectWkaHighmainfctSttus(searchVO);
		//하부주요시설
		List<EgovMap>  lowmainfctList = sptSttusManageService.selectWkaLowmainfctSttus(searchVO);
		//임상
		List<EgovMap>  frtpList = sptSttusManageService.selectWkaFrtpSttus(searchVO);
		//임분밀도
		List<EgovMap>  stddnstList = sptSttusManageService.selectWkaStddnstSttus(searchVO);
		//임분경급
		List<EgovMap>  stddmclsList = sptSttusManageService.selectWkaStddmclsSttus(searchVO);
		//사업 가능여부
		List<EgovMap>  bsposblatList = sptSttusManageService.selectWkaBsposblatSttus(searchVO);
		//대책방안
		List<EgovMap>  soilwtrvalList = sptSttusManageService.selectWkaCntrplnmethodSttus(searchVO);
		
		model.addAttribute("adminList",mapper.writeValueAsString(adminList));
		model.addAttribute("jdgmntgradList",mapper.writeValueAsString(jdgmntgradList));
		model.addAttribute("safefctList",mapper.writeValueAsString(safefctList));
		model.addAttribute("houseLakeList",mapper.writeValueAsString(houseLakeList));
		model.addAttribute("highmainfctList",mapper.writeValueAsString(highmainfctList));
		model.addAttribute("lowmainfctList",mapper.writeValueAsString(lowmainfctList));
		model.addAttribute("frtpList",mapper.writeValueAsString(frtpList));
		model.addAttribute("stddnstList",mapper.writeValueAsString(stddnstList));
		model.addAttribute("stddmclsList",mapper.writeValueAsString(stddmclsList));
		model.addAttribute("bsposblatList",mapper.writeValueAsString(bsposblatList));
		model.addAttribute("soilwtrvalList",mapper.writeValueAsString(soilwtrvalList));		
		
		return "sys/spt/sts/sttusWkaDetail";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 취약지역해제조사 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusCnlDetail.do")
    public String sttusCnlDetail (@ModelAttribute("searchVO") LssCnlSvyComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusCnlDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		vo.setCodeId("FEI003");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(lssCnlSvyComptService.selectCnlSvyComptMaxYear());
		}
		
		//json convert
		ObjectMapper mapper = new ObjectMapper();
		
		//연도코드 조회
		List<?> year_result = lssCnlSvyComptService.selectCnlSvyComptYear();
		model.addAttribute("yearCodeList", year_result);

		AdministZoneVO adminVO = new AdministZoneVO();
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);

		//시군구코드 조회		
		adminVO.setSdCode(searchVO.getSvySd());		
		List<AdministZoneVO> sggCodeList = administZoneService.selectAdministZoneSigngu(adminVO);
		model.addAttribute("sggCodeList",sggCodeList);

		//읍면동코드 조회		
		adminVO.setSggCode(searchVO.getSvySgg());
		List<AdministZoneVO> emdCodeList = administZoneService.selectAdministZoneEmd(adminVO);
		model.addAttribute("emdCodeList",emdCodeList);
		
//		List<EgovMap> yearList = sptSttusManageService.selectAprYearSttus(searchVO);
//		List<EgovMap> factorList = sptSttusManageService.selectCnlFactorSttus(searchVO);
//		List<EgovMap> fckrsltList = sptSttusManageService.selectCnlRsltSttus(searchVO);

		//시도별
		List<EgovMap> adminList = sptSttusManageService.selectCnlAdministSttus(searchVO);
		//유역현황
//		List<EgovMap> dgrsttusList = sptSttusManageService.selectCnlDgrsttusSttus(searchVO);
		//적용공법
		List<EgovMap> applcegnermhdList = sptSttusManageService.selectCnlApplcegnermhdSttus(searchVO);
		//산사태등급
		List<EgovMap> lndsldgrdeList = sptSttusManageService.selectCnlLndsldgrdeSttus(searchVO);
		//평균경사
		List<EgovMap> slopeavgList = sptSttusManageService.selectCnlSlopeavgSttus(searchVO);
		//임상도
		List<EgovMap> frtptypeList = sptSttusManageService.selectCnlFrtptypeSttus(searchVO);
		//경급도
		List<EgovMap> dmclstypeList = sptSttusManageService.selectCnlDmclstypeSttus(searchVO);
		
		model.addAttribute("adminList",mapper.writeValueAsString(adminList));
//		model.addAttribute("dgrsttusList",mapper.writeValueAsString(dgrsttusList));
		model.addAttribute("applcegnermhdList",mapper.writeValueAsString(applcegnermhdList));
		model.addAttribute("lndsldgrdeList",mapper.writeValueAsString(lndsldgrdeList));
		model.addAttribute("slopeavgList",mapper.writeValueAsString(slopeavgList));
		model.addAttribute("frtptypeList",mapper.writeValueAsString(frtptypeList));
		model.addAttribute("dmclstypeList",mapper.writeValueAsString(dmclstypeList));
		
		return "sys/spt/sts/sttusCnlDetail";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 임도 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusFrdDetail.do")
    public String sttusFrdDetail (@ModelAttribute("searchVO") VytFrdSvyComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusFrdDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		
		//json convert
		ObjectMapper mapper = new ObjectMapper();
		
		//연도코드 조회
		List<?> year_result = vytFrdSvyComptService.selectFrdSvyComptYear();
		model.addAttribute("yearCodeList", year_result);

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
		
		// 임도 종류
		vo.setCodeId("FEI171");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		// 관할구분
		vo.setCodeId("FEI167");
		List<CmmnDetailCode> region1CodeList = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("region1CodeList", region1CodeList);
		
		
		// 국유림/민유림 체크
		if(searchVO.getCompentauth() != null && !searchVO.getCompentauth().equals("")) {
			String compentauth = searchVO.getCompentauth();
			if (compentauth.equals("REG18") || compentauth.equals("REG19") || compentauth.equals("REG20") || compentauth.equals("REG21") || compentauth.equals("REG23")) {
				searchVO.setCompentAssort("국유림");
			}else {
				searchVO.setCompentAssort("민유림");
			}
		}
		
		
		// 조사유형별 현황
		List<EgovMap> frdTypeList = sptSttusManageService.selectFrdTypeSttus(searchVO);
		model.addAttribute("frdTypeList", mapper.writeValueAsString(frdTypeList));
		// 월별 현황
		List<EgovMap> monthList = vytFrdSttusService.selectFrdSvySttusMonth();
		// 관할별 현황
		List<EgovMap> regionList = sptSttusManageService.selectFrdRegionSttus(searchVO);
		// 지역별 현황
		List<EgovMap> adminList = sptSttusManageService.selectFrdAdministSttus(searchVO);
		
		JSONArray regionJson = new JSONArray();
		JSONArray regionItemX = new JSONArray();
		JSONArray regionItem1 = new JSONArray();
		JSONArray regionItem2 = new JSONArray();
		
		regionItemX.put("nm");
		regionItem1.put("조사대기");
		regionItem2.put("조사완료");
		
		for (EgovMap egovMap : regionList) {
			regionItemX.put(egovMap.get("nm").toString());
			regionItem1.put(egovMap.get("strp").toString());
			regionItem2.put(egovMap.get("comp").toString());
		}
		regionJson.put(regionItemX);
		regionJson.put(regionItem1);
		regionJson.put(regionItem2);
		
		JSONArray monthJson = new JSONArray();
		JSONArray monthItemX = new JSONArray();
		JSONArray monthItemVal = new JSONArray();
		monthItemX.put("name");
		monthItemVal.put("건수");
		for (EgovMap egovMap : monthList) {
			monthItemX.put(egovMap.get("mon").toString());
			monthItemVal.put(egovMap.get("cnt").toString());
		}
		monthJson.put(monthItemX);
		monthJson.put(monthItemVal);
		
		model.addAttribute("svyRegion", regionJson);
		model.addAttribute("svyMonth", monthJson);
		
		// 행정구역별 현황
		JSONArray adminArr = new JSONArray();
		JSONArray adminArr1 = new JSONArray();
		JSONArray adminArr2 = new JSONArray();
		JSONArray adminArr3 = new JSONArray();
		adminArr1.put("nm");
		adminArr2.put("조사대기");
		adminArr3.put("조사완료");

		for (EgovMap egovMap : adminList) {
		    adminArr1.put(egovMap.get("nm").toString());
		    adminArr2.put(egovMap.get("strp").toString());
		    adminArr3.put(egovMap.get("comp").toString());
		}
		adminArr.put(adminArr1);
		adminArr.put(adminArr2);
		adminArr.put(adminArr3);
		model.addAttribute("adminResult", adminArr);
		
		 // 조사 항목별 데이터 조회
        List<EgovMap> itemsList = sptSttusManageService.selectFrdSvyItems(searchVO);
        
        Map<String, Integer> safeFctMap = new HashMap<>();
        
		// 유무
        JSONArray atJson = new JSONArray();
        JSONArray atItemX = new JSONArray();
        JSONArray atItem1 = new JSONArray();
        JSONArray atItem2 = new JSONArray();

        atItemX.put("nm");
        atItem1.put("유");
        atItem2.put("무");
        
        // 조림지 개수
        int afrst1 = 0, afrst2 = 0; 
        // 벌채지 개수
        int cutting1 = 0, cutting2 = 0; 
        // 석력지 개수
        int stmi1 = 0, stmi2 = 0; 
        // 습지 개수
        int wetLand1 = 0, wetLand2 = 0; 
        // 묘지 개수
        int cmtry1 = 0, cmtry2 = 0; 
        // 용출수 개수
        int eltnWater1 = 0, eltnWater2 = 0; 
        // 연약지반 개수
        int sofrtGrnd1 = 0, sofrtGrnd2 = 0; 
        // 상수원오염 개수
        int wtrPltn1 = 0, wtrPltn2 = 0; 
        // 훼손우려지 개수
        int dmgCnrn1 = 0, dmgCnrn2 = 0; 
        // 산림재해 개수
        int frstDsstr1 = 0, frstDsstr2 = 0; 
        // 야생동물 개수
        int wildAnml1 = 0, wildAnml2 = 0; 
        
        // 대계류 합계
		int bigMrng1 = 0, bigMrng2 = 0, bigMrng3 = 0;
		// 중계류 합계
		int middleMrng1 = 0, middleMrng2 = 0, middleMrng3 = 0;
		// 소계류 합계
		int smallMrng1 = 0, smallMrng2 = 0, smallMrng3 = 0;
		
		// 붕괴우려 사면
		int slope1 = 0, slope2 = 0, slope3 = 0, slope4 = 0, slope5 = 0;
		// 붕괴우려 계류
		int mtTrnt1 = 0, mtTrnt2 = 0, mtTrnt3 = 0, mtTrnt4 = 0, mtTrnt5 = 0;
		
		// 사방시설 설치, 사방시설 필요
		int ecnrInst1 = 0, ecnrInst2 = 0, ecnrNscs1 = 0, ecnrNscs2 = 0;
		
		// 주요수종, 주요식생 건수
		int maintreekndCnt = 0, mainvegCnt = 0;
		
        
        for (EgovMap egovMap : itemsList) {
            try {
            	// 보호시설
            	if(egovMap.get("safefctlist") != null) {
	            	String safefctlist = egovMap.get("safefctlist").toString();
	            	JSONArray safefctlistArray = new JSONArray(safefctlist);
	                for (int i = 0; i < safefctlistArray.length(); i++) {
	                    JSONObject safefctObject = safefctlistArray.getJSONObject(i);
	                    String type = safefctObject.getString("유형");
	                    safeFctMap.put(type, safeFctMap.getOrDefault(type, 0) + 1);
	                }
            	}
                
                // 계류종류 및 현황
                if(egovMap.get("mrngkind") != null) {
                	String mrngkind = egovMap.get("mrngkind").toString();
	            	JSONArray mrngkindArray = new JSONArray(mrngkind);
	                for (int i = 0; i < mrngkindArray.length(); i++) {
	                    JSONObject mrngkindObject = mrngkindArray.getJSONObject(i);
	                    
	                    String bigMrng = mrngkindObject.getString("대분류");
	                    String smallMrng = mrngkindObject.getString("소분류");
	                    
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
	                }
                }
                
                // 붕괴우려지역
                if(egovMap.get("clpscnrnlist") != null) {
	                String clpsCnrn = egovMap.get("clpscnrnlist").toString();
	                JSONArray clpsCnrnArray = new JSONArray(clpsCnrn);
	                for (int i = 0; i < clpsCnrnArray.length(); i++) {
	                	JSONObject clpsCnrnObject = clpsCnrnArray.getJSONObject(i);
	                	
	                	String clpsCnrnBig = clpsCnrnObject.getString("붕괴우려_대분류");
	                	String clpsCnrnSmall = clpsCnrnObject.getString("붕괴우려_소분류");
	                	
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
	                }
                }
                
                // 사방시설설치
                if(egovMap.get("ecnrfcltyinstllist") != null) {
	        		String ecnrfcltyinstl = egovMap.get("ecnrfcltyinstllist").toString();
					if(Integer.parseInt(ecnrfcltyinstl) > 0) {
						ecnrInst1 += Integer.parseInt(ecnrfcltyinstl);
					}else {
						ecnrInst2 += 1;
					}
                }
                
                // 사방시설필요
                if(egovMap.get("ecnrfcltyncstylist") != null) {
                	String ecnrfcltyncsty = egovMap.get("ecnrfcltyncstylist").toString();
                	if(Integer.parseInt(ecnrfcltyncsty) > 0) {
                		ecnrNscs1 += Integer.parseInt(ecnrfcltyncsty);
                	}else {
                		ecnrNscs2 += 1;
                	}
                }
                
                // 주요수종
                if(egovMap.get("maintreekndlist") != null) {
                	String maintreeknd = egovMap.get("maintreekndlist").toString();
                	if(Integer.parseInt(maintreeknd) > 0) {
                		maintreekndCnt += Integer.parseInt(maintreeknd);
                	}
                }
                
                // 주요식생
                if(egovMap.get("mainveglist") != null) {
                	String mainveg = egovMap.get("mainveglist").toString();
                	if(Integer.parseInt(mainveg) > 0) {
                		mainvegCnt += Integer.parseInt(mainveg);
                	}
                }
                
                // 조림지
                if(egovMap.get("afrstlist") != null) {
	        		String afrst = egovMap.get("afrstlist").toString();
					if(Integer.parseInt(afrst) > 0) {
						afrst1 += Integer.parseInt(afrst);
					}else {
						afrst2 += 1;
					}
                }else {
                	afrst2 += 1;
                }
        		
                // 벌채지
                if(egovMap.get("cuttinglist") != null) {
	        		String cutting = egovMap.get("cuttinglist").toString();
	        		if(Integer.parseInt(cutting) > 0) {
	        			cutting1 += Integer.parseInt(cutting);
	        		}else {
	        			cutting2 += 1;
	        		}
                }else {
                	cutting2 += 1;
                }
                
                // 석력지
                if(egovMap.get("stmilist") != null) {
	        		String stmi = egovMap.get("stmilist").toString();
	        		if(Integer.parseInt(stmi) > 0) {
	        			stmi1 += Integer.parseInt(stmi);
	        		}else {
	        			stmi2 += 1;
	        		}
                }else {
                	stmi2 += 1;
                }
        		
                // 습지
                if(egovMap.get("wetlandlist") != null) {
	        		String wetLand = egovMap.get("wetlandlist").toString();
	        		if(Integer.parseInt(wetLand) > 0) {
	        			wetLand1 += Integer.parseInt(wetLand);
	        		}else {
	        			wetLand2 += 1;
	        		}
                }else {
                	wetLand2 += 1;
                }
        		
                // 묘지
                if(egovMap.get("cmtrylist") != null) {
	        		String cmtry = egovMap.get("cmtrylist").toString();
	        		if(Integer.parseInt(cmtry) > 0) {
	        			cmtry1 += Integer.parseInt(cmtry);
	        		}else {
	        			cmtry2 += 1;
	        		}
                }else {
        			cmtry2 += 1;
        		}
        		
        		// 용출수
                if(egovMap.get("eltnwaterlist") != null) {
	        		String eltnWater = egovMap.get("eltnwaterlist").toString();
	        		if(Integer.parseInt(eltnWater) > 0) {
	        			eltnWater1 += Integer.parseInt(eltnWater);
	        		}else {
	        			eltnWater2 += 1;
	        		}
                }else {
        			eltnWater2 += 1;
        		}
        		
        		// 연약지반
                if(egovMap.get("sofrtgrndlist") != null) {
	        		String sofrtGrnd = egovMap.get("sofrtgrndlist").toString();
	        		if(Integer.parseInt(sofrtGrnd) > 0) {
	        			sofrtGrnd1 += Integer.parseInt(sofrtGrnd);
	        		}else {
	        			sofrtGrnd2 += 1;
	        		}
                }else {
        			sofrtGrnd2 += 1;
        		}
        		
        		// 상수원 오염
                if(egovMap.get("wtrpltnlist") != null) {
	        		String wtrPltn = egovMap.get("wtrpltnlist").toString();
	        		if(Integer.parseInt(wtrPltn) > 0) {
	        			wtrPltn1 += Integer.parseInt(wtrPltn);
	        		}else {
	        			wtrPltn2 += 1;
	        		}
                }else {
        			wtrPltn2 += 1;
        		}
        		
        		// 훼손우려지
                if(egovMap.get("dmgcncrnlist") != null) {
	        		String dmgCnrn = egovMap.get("dmgcncrnlist").toString();
	        		if(Integer.parseInt(dmgCnrn) > 0) {
	        			dmgCnrn1 += Integer.parseInt(dmgCnrn);
	        		}else {
	        			dmgCnrn2 += 1;
	        		}
                }else {
        			dmgCnrn2 += 1;
        		}
        		
        		// 산림재해
                if(egovMap.get("frstdsstrlist") != null) {
	        		String frstDsstr = egovMap.get("frstdsstrlist").toString();
	        		if(Integer.parseInt(frstDsstr) > 0) {
	        			frstDsstr1 += Integer.parseInt(frstDsstr);
	        		}else {
	        			frstDsstr2 += 1;
	        		}
                }else {
        			frstDsstr2 += 1;
        		}
        		
        		// 야생동물
                if(egovMap.get("wildanmllist") != null) {
	        		String wildAnml = egovMap.get("wildanmllist").toString();
	        		if(Integer.parseInt(wildAnml) > 0) {
	        			wildAnml1 += Integer.parseInt(wildAnml);
	        		}else {
	        			wildAnml2 += 1;
	        		}
        		}else {
        			wildAnml2 += 1;
        		}
        		
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        
        // 보호시설
        JSONArray safetctJson = new JSONArray();
		JSONArray safetctItemX = new JSONArray();
		JSONArray safetctItemVal = new JSONArray();
		
		safetctItemX.put("safetcttype");
		safetctItemVal.put("개수");
        for (Map.Entry<String, Integer> entry : safeFctMap.entrySet()) {
        	safetctItemX.put(entry.getKey());
        	safetctItemVal.put(entry.getValue());
        }
        safetctJson.put(safetctItemX);
        safetctJson.put(safetctItemVal);
        model.addAttribute("safeFctList", safetctJson);
        
        // 계류종류 및 현황
        JSONArray mrngKindJson = new JSONArray();
        JSONArray mrngKindItemX = new JSONArray();
        JSONArray mrngKindItem1 = new JSONArray();
        JSONArray mrngKindItem2 = new JSONArray();
        JSONArray mrngKindItem3 = new JSONArray();
        
        mrngKindItemX.put("nm");
        mrngKindItem1.put("유형");
        mrngKindItem2.put("상시천");
        mrngKindItem3.put("건천");
        
        mrngKindItemX.put("대계류");
        mrngKindItem1.put(bigMrng1);
        mrngKindItem2.put(bigMrng2);
        mrngKindItem3.put(bigMrng3);
        
        mrngKindItemX.put("중계류");
        mrngKindItem1.put(middleMrng1);
        mrngKindItem2.put(middleMrng2);
        mrngKindItem3.put(middleMrng3);
        
        mrngKindItemX.put("소계류");
        mrngKindItem1.put(smallMrng1);
        mrngKindItem2.put(smallMrng2);
        mrngKindItem3.put(smallMrng3);
        
        mrngKindJson.put(mrngKindItemX);
        mrngKindJson.put(mrngKindItem1);
        mrngKindJson.put(mrngKindItem2);
        mrngKindJson.put(mrngKindItem3);
        
        model.addAttribute("mrngKindList", mrngKindJson);
        
        // 붕괴우려지역
        JSONArray clpsCnrnJson = new JSONArray();
        JSONArray clpsCnrnItemX = new JSONArray();
        JSONArray clpsCnrnItem1 = new JSONArray();
        JSONArray clpsCnrnItem2 = new JSONArray();
        JSONArray clpsCnrnItem3 = new JSONArray();
        JSONArray clpsCnrnItem4 = new JSONArray();
        JSONArray clpsCnrnItem5 = new JSONArray();
        
        clpsCnrnItemX.put("nm");
        clpsCnrnItem1.put("유형");
        clpsCnrnItem2.put("침식");
        clpsCnrnItem3.put("붕괴");
        clpsCnrnItem4.put("포락");
        clpsCnrnItem5.put("미선택");
        
        clpsCnrnItemX.put("사면");
        clpsCnrnItem1.put(slope1);
        clpsCnrnItem2.put(slope2);
        clpsCnrnItem3.put(slope3);
        clpsCnrnItem4.put(slope4);
        clpsCnrnItem5.put(slope5);
        
        clpsCnrnItemX.put("계류");
        clpsCnrnItem1.put(mtTrnt1);
        clpsCnrnItem2.put(mtTrnt2);
        clpsCnrnItem3.put(mtTrnt3);
        clpsCnrnItem4.put(mtTrnt4);
        clpsCnrnItem5.put(mtTrnt5);
        
        
        clpsCnrnJson.put(clpsCnrnItemX);
        clpsCnrnJson.put(clpsCnrnItem1);
        clpsCnrnJson.put(clpsCnrnItem2);
        clpsCnrnJson.put(clpsCnrnItem3);
        clpsCnrnJson.put(clpsCnrnItem4);
        clpsCnrnJson.put(clpsCnrnItem5);
        
        model.addAttribute("clpsCnrnList", clpsCnrnJson);
        
        // 주요수종 및 주요식생
        JSONArray mainJson = new JSONArray();
        JSONArray mainItemX = new JSONArray();
        JSONArray mainItem1 = new JSONArray();
        
        mainItemX.put("nm");
        mainItem1.put("건수");
        
        mainItemX.put("주요수종");
        mainItem1.put(maintreekndCnt);
        
        mainItemX.put("주요식생");
        mainItem1.put(mainvegCnt);
        
        mainJson.put(mainItemX);
        mainJson.put(mainItem1);
        
        model.addAttribute("mainList", mainJson);
        
        //유무
        atItemX.put("조림지");
        atItem1.put(afrst1);
        atItem2.put(afrst2);
        
        atItemX.put("벌채지");
        atItem1.put(cutting1);
        atItem2.put(cutting2);
        
        atItemX.put("석력지");
        atItem1.put(stmi1);
        atItem2.put(stmi2);
        
        atItemX.put("습지");
        atItem1.put(wetLand1);
        atItem2.put(wetLand2);
        
        atItemX.put("묘지");
        atItem1.put(cmtry1);
        atItem2.put(cmtry2);
        
        atItemX.put("용출수");
        atItem1.put(eltnWater1);
        atItem2.put(eltnWater2);
        
        atItemX.put("연약지반");
        atItem1.put(sofrtGrnd1);
        atItem2.put(sofrtGrnd2);
        
        atItemX.put("상수원오염");
        atItem1.put(wtrPltn1);
        atItem2.put(wtrPltn2);
        
        atItemX.put("훼손우려지");
        atItem1.put(dmgCnrn1);
        atItem2.put(dmgCnrn2);
        
        atItemX.put("산림재해");
        atItem1.put(frstDsstr1);
        atItem2.put(frstDsstr2);
        
        atItemX.put("야생동물");
        atItem1.put(wildAnml1);
        atItem2.put(wildAnml2);
        
        atJson.put(atItemX);
        atJson.put(atItem1);
        atJson.put(atItem2);
        
        model.addAttribute("atList", atJson);
        
        // 사방시설 설치 및 필요
        JSONArray ecnrJson = new JSONArray();
        JSONArray ecnrItemX = new JSONArray();
        JSONArray ecnrItem1 = new JSONArray();
        JSONArray ecnrItem2 = new JSONArray();

        ecnrItemX.put("nm");
        ecnrItem1.put("유");
        ecnrItem2.put("무");
        
        ecnrItemX.put("사방시설 설치");
        ecnrItem1.put(ecnrInst1);
        ecnrItem2.put(ecnrInst2);
        
        ecnrItemX.put("사방시설 필요");
        ecnrItem1.put(ecnrNscs1);
        ecnrItem2.put(ecnrNscs2);
        
        ecnrJson.put(ecnrItemX);
        ecnrJson.put(ecnrItem1);
        ecnrJson.put(ecnrItem2);
        
        model.addAttribute("ecnrList", ecnrJson);
        
		return "sys/spt/sts/sttusFrdDetail";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 계측장비 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusMseDetail.do")
    public String sttusMseDetail (@ModelAttribute("searchVO") FckMseComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusMseDetail controller");
		
		//장비유형코드를 코드정보로부터 조회 : 공통코드 반영 후 공통코드ID 변경후 적용
//		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
//		vo.setCodeId("FEI003");
//		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
//		model.addAttribute("typeCodeList", type_result);
		
		if(searchVO.getSvyYear() == null) {
			searchVO.setSvyYear(fckMseComptService.selectFckMseComptMaxYear());
		}
		
		//연도코드 조회
		List<?> year_result = fckMseComptService.selectFckMseComptYear();
		model.addAttribute("yearCodeList", year_result);

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
		
		List<EgovMap> yearList = sptSttusManageService.selectMseYearSttus(searchVO);
		List<EgovMap> wireList = sptSttusManageService.selectMseWireAdministSttus(searchVO);
		List<EgovMap> licmeterList = sptSttusManageService.selectMseLicmeterAdministSttus(searchVO);
		List<EgovMap> gwgaugeList = sptSttusManageService.selectMseGwGaugeAdministSttus(searchVO);
		List<EgovMap> raingaugeList = sptSttusManageService.selectMseRainGaugeAdministSttus(searchVO);
		List<EgovMap> surdpmList = sptSttusManageService.selectMseSurDpmAdministSttus(searchVO);
		List<EgovMap> gpsgaugeList = sptSttusManageService.selectMseGpsGaugeAdministSttus(searchVO);
		List<EgovMap> gatewayList = sptSttusManageService.selectMseGatewayAdministSttus(searchVO);
		List<EgovMap> nodeList = sptSttusManageService.selectMseNodeAdministSttus(searchVO);
		
		//연도별 현황
		JSONArray yearArr = new JSONArray();
		JSONArray yearArr1 = new JSONArray();
		JSONArray yearArr2 = new JSONArray();
		yearArr1.put("nm");
		yearArr2.put("건수");
		
		for (EgovMap egovMap : yearList) {
			yearArr1.put(egovMap.get("mon").toString());
			yearArr2.put(egovMap.get("cnt").toString());

		}
		yearArr.put(yearArr1);
		yearArr.put(yearArr2);
		model.addAttribute("yearResult", yearArr);
		
		//행정구역 시도별 와이어신축계 장비 수 현황
		JSONArray wireArr = new JSONArray();
		JSONArray wireArr1 = new JSONArray();
		JSONArray wireArr2 = new JSONArray();
		JSONArray wireArr3 = new JSONArray();
		JSONArray wireArr4 = new JSONArray();
		JSONArray wireArr5 = new JSONArray();
		JSONArray wireArr6 = new JSONArray();
		JSONArray wireArr7 = new JSONArray();
		wireArr1.put("nm");
		wireArr2.put("자물쇠");
		wireArr3.put("와이어");
		wireArr4.put("케이블");
		wireArr5.put("전원");
		wireArr6.put("보호함체");
		wireArr7.put("센서부");

		for (EgovMap egovMap : wireList) {
			wireArr1.put(egovMap.get("nm").toString());
			wireArr2.put(egovMap.get("rock").toString());
			wireArr3.put(egovMap.get("wire").toString());
			wireArr4.put(egovMap.get("cable").toString());
			wireArr5.put(egovMap.get("power").toString());
			wireArr6.put(egovMap.get("protect").toString());
			wireArr7.put(egovMap.get("censor").toString());
		}
		wireArr.put(wireArr1);
		wireArr.put(wireArr2);
		wireArr.put(wireArr3);
		wireArr.put(wireArr4);
		wireArr.put(wireArr5);
		wireArr.put(wireArr6);
		wireArr.put(wireArr7);
		model.addAttribute("wireResult", wireArr);
		
		//행정구역 시도별 지중경사계 장비 수 현황
		JSONArray licmeterArr = new JSONArray();
		JSONArray licmeterArr1 = new JSONArray();
		JSONArray licmeterArr2 = new JSONArray();
		JSONArray licmeterArr3 = new JSONArray();
		JSONArray licmeterArr4 = new JSONArray();
		JSONArray licmeterArr5 = new JSONArray();
		JSONArray licmeterArr6 = new JSONArray();
		licmeterArr1.put("nm");
		licmeterArr2.put("자물쇠");
		licmeterArr3.put("케이블");
		licmeterArr4.put("보호함체");
		licmeterArr5.put("전원");
		licmeterArr6.put("센서부");
		
		for (EgovMap egovMap : licmeterList) {
			licmeterArr1.put(egovMap.get("nm").toString());
			licmeterArr2.put(egovMap.get("rock").toString());
			licmeterArr3.put(egovMap.get("cable").toString());
			licmeterArr4.put(egovMap.get("protect").toString());
			licmeterArr5.put(egovMap.get("power").toString());
			licmeterArr6.put(egovMap.get("censor").toString());
		}
		licmeterArr.put(licmeterArr1);
		licmeterArr.put(licmeterArr2);
		licmeterArr.put(licmeterArr3);
		licmeterArr.put(licmeterArr4);
		licmeterArr.put(licmeterArr5);
		licmeterArr.put(licmeterArr6);
		model.addAttribute("licmeterResult", licmeterArr);
		
		//행정구역 시도별 지하수위계 장비 수 현황
		JSONArray gwgaugeArr = new JSONArray();
		JSONArray gwgaugeArr1 = new JSONArray();
		JSONArray gwgaugeArr2 = new JSONArray();
		JSONArray gwgaugeArr3 = new JSONArray();
		JSONArray gwgaugeArr4 = new JSONArray();
		JSONArray gwgaugeArr5 = new JSONArray();
		JSONArray gwgaugeArr6 = new JSONArray();
		gwgaugeArr1.put("nm");
		gwgaugeArr2.put("자물쇠");
		gwgaugeArr3.put("케이블");
		gwgaugeArr4.put("보호함체");
		gwgaugeArr5.put("전원");
		gwgaugeArr6.put("센서부");
		
		for (EgovMap egovMap : gwgaugeList) {
			gwgaugeArr1.put(egovMap.get("nm").toString());
			gwgaugeArr2.put(egovMap.get("rock").toString());
			gwgaugeArr3.put(egovMap.get("cable").toString());
			gwgaugeArr4.put(egovMap.get("protect").toString());
			gwgaugeArr5.put(egovMap.get("power").toString());
			gwgaugeArr6.put(egovMap.get("censor").toString());
		}
		gwgaugeArr.put(gwgaugeArr1);
		gwgaugeArr.put(gwgaugeArr2);
		gwgaugeArr.put(gwgaugeArr3);
		gwgaugeArr.put(gwgaugeArr4);
		gwgaugeArr.put(gwgaugeArr5);
		gwgaugeArr.put(gwgaugeArr6);
		model.addAttribute("gwgaugeResult", gwgaugeArr);
		
		//행정구역 시도별 강우계 장비 수 현황
		JSONArray raingaugeArr = new JSONArray();
		JSONArray raingaugeArr1 = new JSONArray();
		JSONArray raingaugeArr2 = new JSONArray();
		JSONArray raingaugeArr3 = new JSONArray();
		JSONArray raingaugeArr4 = new JSONArray();
		JSONArray raingaugeArr5 = new JSONArray();
		raingaugeArr1.put("nm");
		raingaugeArr2.put("케이블");
		raingaugeArr3.put("보호함체");
		raingaugeArr4.put("전원");
		raingaugeArr5.put("센서부");
		
		for (EgovMap egovMap : raingaugeList) {
			raingaugeArr1.put(egovMap.get("nm").toString());
			raingaugeArr2.put(egovMap.get("cable").toString());
			raingaugeArr3.put(egovMap.get("protect").toString());
			raingaugeArr4.put(egovMap.get("power").toString());
			raingaugeArr5.put(egovMap.get("censor").toString());
		}
		raingaugeArr.put(raingaugeArr1);
		raingaugeArr.put(raingaugeArr2);
		raingaugeArr.put(raingaugeArr3);
		raingaugeArr.put(raingaugeArr4);
		raingaugeArr.put(raingaugeArr5);
		model.addAttribute("raingaugeResult", raingaugeArr);
		
		//행정구역 시도별 지표변위계 장비 수 현황
		JSONArray surdpmArr = new JSONArray();
		JSONArray surdpmArr1 = new JSONArray();
		JSONArray surdpmArr2 = new JSONArray();
		JSONArray surdpmArr3 = new JSONArray();
		JSONArray surdpmArr4 = new JSONArray();
		surdpmArr1.put("nm");
		surdpmArr2.put("케이블");
		surdpmArr3.put("전원");
		surdpmArr4.put("센서부");
		
		for (EgovMap egovMap : surdpmList) {
			surdpmArr1.put(egovMap.get("nm").toString());
			surdpmArr2.put(egovMap.get("cable").toString());
			surdpmArr3.put(egovMap.get("power").toString());
			surdpmArr4.put(egovMap.get("censor").toString());
		}
		surdpmArr.put(surdpmArr1);
		surdpmArr.put(surdpmArr2);
		surdpmArr.put(surdpmArr3);
		surdpmArr.put(surdpmArr4);
		model.addAttribute("surdpmResult", surdpmArr);
		
		//행정구역 시도별 GPS변위계 장비 수 현황
		JSONArray gpsgaugeArr = new JSONArray();
		JSONArray gpsgaugeArr1 = new JSONArray();
		JSONArray gpsgaugeArr2 = new JSONArray();
		JSONArray gpsgaugeArr3 = new JSONArray();
		JSONArray gpsgaugeArr4 = new JSONArray();
		gpsgaugeArr1.put("nm");
		gpsgaugeArr2.put("케이블");
		gpsgaugeArr3.put("전원");
		gpsgaugeArr4.put("센서부");
		
		for (EgovMap egovMap : gpsgaugeList) {
			gpsgaugeArr1.put(egovMap.get("nm").toString());
			gpsgaugeArr2.put(egovMap.get("cable").toString());
			gpsgaugeArr3.put(egovMap.get("power").toString());
			gpsgaugeArr4.put(egovMap.get("censor").toString());
		}
		gpsgaugeArr.put(gpsgaugeArr1);
		gpsgaugeArr.put(gpsgaugeArr2);
		gpsgaugeArr.put(gpsgaugeArr3);
		gpsgaugeArr.put(gpsgaugeArr4);
		model.addAttribute("gpsgaugeResult", gpsgaugeArr);
		
		//행정구역 시도별 게이트웨이 장비 수 현황
		JSONArray gatewayArr = new JSONArray();
		JSONArray gatewayArr1 = new JSONArray();
		JSONArray gatewayArr2 = new JSONArray();
		JSONArray gatewayArr3 = new JSONArray();
		JSONArray gatewayArr4 = new JSONArray();
		JSONArray gatewayArr5 = new JSONArray();
		JSONArray gatewayArr6 = new JSONArray();
		JSONArray gatewayArr7 = new JSONArray();
		gatewayArr1.put("nm");
		gatewayArr2.put("자물쇠");
		gatewayArr3.put("태양광");
		gatewayArr4.put("함체");
		gatewayArr5.put("지선");
		gatewayArr6.put("보호휀스");
		gatewayArr7.put("안내판");

		for (EgovMap egovMap : gatewayList) {
			gatewayArr1.put(egovMap.get("nm").toString());
			gatewayArr2.put(egovMap.get("rock").toString());
			gatewayArr3.put(egovMap.get("sunlight").toString());
			gatewayArr4.put(egovMap.get("console").toString());
			gatewayArr5.put(egovMap.get("branchline").toString());
			gatewayArr6.put(egovMap.get("protect").toString());
			gatewayArr7.put(egovMap.get("infoboard").toString());
		}
		gatewayArr.put(gatewayArr1);
		gatewayArr.put(gatewayArr2);
		gatewayArr.put(gatewayArr3);
		gatewayArr.put(gatewayArr4);
		gatewayArr.put(gatewayArr5);
		gatewayArr.put(gatewayArr6);
		gatewayArr.put(gatewayArr7);
		model.addAttribute("gatewayResult", gatewayArr);
		
		//행정구역 시도별 노드 장비 수 현황
		JSONArray nodeArr = new JSONArray();
		JSONArray nodeArr1 = new JSONArray();
		JSONArray nodeArr2 = new JSONArray();
		JSONArray nodeArr3 = new JSONArray();
		JSONArray nodeArr4 = new JSONArray();
		JSONArray nodeArr5 = new JSONArray();
		JSONArray nodeArr6 = new JSONArray();
		JSONArray nodeArr7 = new JSONArray();
		nodeArr1.put("nm");
		nodeArr2.put("자물쇠");
		nodeArr3.put("태양광");
		nodeArr4.put("함체");
		nodeArr5.put("지선");
		nodeArr6.put("보호휀스");
		nodeArr7.put("안내판");

		for (EgovMap egovMap : nodeList) {
			nodeArr1.put(egovMap.get("nm").toString());
			nodeArr2.put(egovMap.get("rock").toString());
			nodeArr3.put(egovMap.get("sunlight").toString());
			nodeArr4.put(egovMap.get("console").toString());
			nodeArr5.put(egovMap.get("branchline").toString());
			nodeArr6.put(egovMap.get("protect").toString());
			nodeArr7.put(egovMap.get("infoboard").toString());
		}
		nodeArr.put(nodeArr1);
		nodeArr.put(nodeArr2);
		nodeArr.put(nodeArr3);
		nodeArr.put(nodeArr4);
		nodeArr.put(nodeArr5);
		nodeArr.put(nodeArr6);
		nodeArr.put(nodeArr7);
		model.addAttribute("nodeResult", nodeArr);
		
		return "sys/spt/sts/sttusMseDetail";
	}
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 정밀점검 통계관리
	 */
	@RequestMapping(value = "/sys/spt/sts/sttusPcsDetail.do")
	public String sttusPcsDetail (@ModelAttribute("searchVO") FckPcsComptVO searchVO, ModelMap model) throws Exception {
		
		logger.info("sttusPcsDetail controller");
		
		//조사유형코드를 코드정보로부터 조회
		ComDefaultCodeVO vo = new ComDefaultCodeVO();		
		
		//연도코드 조회
		List<?> year_result = fckPcsComptService.selectPcsSvyComptYear();
		model.addAttribute("yearCodeList", year_result);

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
		
		// 정밀점검 종류
		vo.setCodeId("FEI173");
		List<?> type_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("typeCodeList", type_result);
		
		
		List<EgovMap> monthList = fckPcsSvySttusService.selectPcsSvySttusMonth();
		JSONArray monthJson = new JSONArray();
		JSONArray monthItemX = new JSONArray();
		JSONArray monthItemVal = new JSONArray();
		monthItemX.put("name");
		monthItemVal.put("조사완료");
		for (EgovMap egovMap : monthList) {
			monthItemX.put(egovMap.get("mon").toString());
			monthItemVal.put(egovMap.get("cnt").toString());
		}
		monthJson.put(monthItemX);
		monthJson.put(monthItemVal);
		
		model.addAttribute("svyMonth", monthJson);
		
		List<EgovMap> adminList = sptSttusManageService.selectPcsAdministSttus(searchVO);
		// 행정구역별 현황
		JSONArray adminArr = new JSONArray();
		JSONArray adminArr1 = new JSONArray();
		JSONArray adminArr2 = new JSONArray();
		JSONArray adminArr3 = new JSONArray();
		adminArr1.put("nm");
		adminArr2.put("조사대기");
		adminArr3.put("조사완료");

		for (EgovMap egovMap : adminList) {
		    adminArr1.put(egovMap.get("nm").toString());
		    adminArr2.put(egovMap.get("strp").toString());
		    adminArr3.put(egovMap.get("comp").toString());
		}
		adminArr.put(adminArr1);
		adminArr.put(adminArr2);
		adminArr.put(adminArr3);
		model.addAttribute("adminResult", adminArr);
		
		if(searchVO.getSvyType() != null) {
			if(searchVO.getSvyType().contains("사방댐")) {
				searchVO.setSvyType("사방댐 정밀점검");
			}else if(searchVO.getSvyType().contains("계류보전")) {
				searchVO.setSvyType("계류보전 정밀점검");
			}else if(searchVO.getSvyType().contains("산지사방")) {
				searchVO.setSvyType("산지사방 정밀점검");
			}else if(searchVO.getSvyType().contains("해안침식방지")) {
				searchVO.setSvyType("해안침식방지 정밀점검");
			}else if(searchVO.getSvyType().contains("해안방재림")) {
				searchVO.setSvyType("해안방재림 정밀점검");
			}
		}
		
		// 조사항목별 데이터 조회
		List<EgovMap> itemsList = sptSttusManageService.selectPcsSvyItems(searchVO);
		
		// 조사유형
		int sabang = 0, gyeryu = 0, sanji = 0, haechim = 0, haebang = 0;  
		
		// 본댐.파손
		int orginlDamDmg1 = 0, orginlDamDmg2 = 0, orginlDamDmg3 = 0;
		// 본댐.균열
		int orginlDamCrk1 = 0, orginlDamCrk2 = 0, orginlDamCrk3 = 0, orginlDamCrk4 = 0, orginlDamCrk5 = 0, orginlDamCrk6 = 0, orginlDamCrk7 = 0, orginlDamCrk8 = 0, orginlDamCrk9 = 0, orginlDamCrk10 = 0, orginlDamCrk11 = 0, orginlDamCrk12 = 0;
		// 본댐.기초부세굴
		int orginlDamBasicScour1 = 0, orginlDamBasicScour2 = 0, orginlDamBasicScour3 = 0;
		// 본댐.콘크리트
		int orginlDamCncrt1 = 0, orginlDamCncrt2 = 0, orginlDamCncrt3 = 0;
		// 본댐.전석붙임
		int orginlDamPlng1 = 0, orginlDamPlng2 = 0, orginlDamPlng3 = 0;
		// 본댐.수문시설
		int orginlDamWtgate1 = 0, orginlDamWtgate2 = 0, orginlDamWtgate3 = 0;
		
		
		// 측벽.파손
		int sideWallDmg1 = 0, sideWallDmg2 = 0, sideWallDmg3 = 0;
		// 측벽.균열
		int sideWallCrk1 = 0, sideWallCrk2 = 0, sideWallCrk3 = 0, sideWallCrk4 = 0, sideWallCrk5 = 0, sideWallCrk6 = 0;
		// 측벽.기초부세굴
		int sideWallBasicScour1 = 0, sideWallBasicScour2 = 0, sideWallBasicScour3 = 0;
		
		// 물받이.파손
		int dwnSptDmg1 = 0, dwnSptDmg2 = 0, dwnSptDmg3 = 0;
		// 물받이.기초부세굴
		int dwnSptBasicScour1 = 0, dwnSptBasicScour2 = 0, dwnSptBasicScour3 = 0;
		// 물받이.균열
		int dwnSptCrk1 = 0, dwnSptCrk2 = 0, dwnSptCrk3 = 0, dwnSptCrk4 = 0, dwnSptCrk5 = 0, dwnSptCrk6 = 0;
		
		// 비파괴.압축강도
		int cncrtCmprsStrn1 = 0, cncrtCmprsStrn2 = 0, cncrtCmprsStrn3 = 0, cncrtCmprsStrn4 = 0;
		// 비파괴.균열깊이
		int cncrtCrkDpt1 = 0, cncrtCrkDpt2 = 0, cncrtCrkDpt3 = 0, cncrtCrkDpt4 = 0;
		
		// 사방댐 준설여부 판정
		int drdgnAtJdg1 = 0, drdgnAtJdg2 = 0;
		
		//완료여부
		int complt1 = 0, complt2 = 0, complt3 = 0, complt4 = 0, complt5 = 0;
		
		for (EgovMap egovMap : itemsList) {
			// 조사유형
			if(egovMap.get("svytype") != null) {
				String svytype = egovMap.get("svytype").toString();
				if(svytype.contains("사방댐")) {
					sabang += 1;
				}else if(svytype.contains("계류보전")) {
					gyeryu += 1;
				}else if(svytype.contains("산지사방")) {
					sanji += 1;
				}else if(svytype.contains("해안침식")) {
					haechim += 1;
				}else if(svytype.contains("해안방재")) {
					haebang += 1;
				}
			}
			
			// 본댐.파손
			if(egovMap.get("orginldamdmg") != null) {
				String orginlDam1 = egovMap.get("orginldamdmg").toString();
				if(orginlDam1.equals("5%미만")) {
					orginlDamDmg1 += 1;
				}else if(orginlDam1.equals("5~15%미만")) {
					orginlDamDmg2 += 1;
				}else if(orginlDam1.equals("15%이상")) {
					orginlDamDmg3 += 1;
				}
			}
			// 본댐.균열
			if(egovMap.get("orginldamcrk") != null) {
				String orginlDam2 = egovMap.get("orginldamcrk").toString();
				if(orginlDam2.equals("균열5%미만")){
					orginlDamCrk1 += 1;
				}else if(orginlDam2.equals("균열5~15%미만")){
					orginlDamCrk2 += 1;
				}else if(orginlDam2.equals("균열15%이상")){
					orginlDamCrk3 += 1;
				}else if(orginlDam2.equals("가로균열이댐길이의10%미만")){
					orginlDamCrk4 += 1;
				}else if(orginlDam2.equals("가로균열이댐길이의10~30%미만")){
					orginlDamCrk5 += 1;
				}else if(orginlDam2.equals("가로균열이댐길이의30%이상")){
					orginlDamCrk6 += 1;
				}else if(orginlDam2.equals("세로균열이유효고의10%미만")){
					orginlDamCrk7 += 1;
				}else if(orginlDam2.equals("세로균열이유효고의10~30%미만")){
					orginlDamCrk8 += 1;
				}else if(orginlDam2.equals("세로균열이유효고의30%이상")){
					orginlDamCrk9 += 1;
				}else if(orginlDam2.equals("누수없음")){
					orginlDamCrk10 += 1;
				}else if(orginlDam2.equals("누수흔적관찰")){
					orginlDamCrk11 += 1;
				}else if(orginlDam2.equals("누수관찰")){
					orginlDamCrk12 += 1;
				}
			}
			// 본댐.기초부세굴
			if(egovMap.get("orginldambasicscour") != null) {
				String orginlDam3 = egovMap.get("orginldambasicscour").toString();
				if(orginlDam3.equals("양호(보수및보강불필요)")){
					orginlDamBasicScour1 += 1;
				}else if(orginlDam3.equals("보통(경미결함보수필요)")){
					orginlDamBasicScour2 += 1;
				}else if(orginlDam3.equals("불량(중대결함보강필요)")){
					orginlDamBasicScour3 += 1;
				}
			}
			// 본댐.콘크리트
			if(egovMap.get("orginldamcncrt") != null) {
				String orginlDam4 = egovMap.get("orginldamcncrt").toString();
				if(orginlDam4.equals("5%미만")){
					orginlDamCncrt1 += 1;
				}else if(orginlDam4.equals("5~20%미만")){
					orginlDamCncrt2 += 1;
				}else if(orginlDam4.equals("20%이상")){
					orginlDamCncrt3 += 1;
				}
			}
			// 본댐.전석붙임
			if(egovMap.get("orginldamplng") != null) {
				String orginlDam5 = egovMap.get("orginldamplng").toString();
				if(orginlDam5.equals("5%미만")){
					orginlDamPlng1 += 1;
				}else if(orginlDam5.equals("5~20%미만")){
					orginlDamPlng2 += 1;
				}else if(orginlDam5.equals("20%이상")){
					orginlDamPlng3 += 1;
				}
			}
			// 본댐.수문시설
			if(egovMap.get("orginldamwtgate") != null) {
				String orginlDam6 = egovMap.get("orginldamwtgate").toString();
				if(orginlDam6.equals("양호(보수및보강불필요)")){
					orginlDamWtgate1 += 1;
				}else if(orginlDam6.equals("보통(경미결함보수필요)")){
					orginlDamWtgate2 += 1;
				}else if(orginlDam6.equals("불량(중대결함보강필요)")){
					orginlDamWtgate3 += 1;
				}
			}
		
			// 측벽.파손
			if(egovMap.get("sidewalldmg") != null) {
				String sideWall1 = egovMap.get("sidewalldmg").toString();
				if(sideWall1.equals("5%미만")){
					sideWallDmg1 += 1;
				}else if(sideWall1.equals("5~20%미만")){
					sideWallDmg2 += 1;
				}else if(sideWall1.equals("20%이상")){
					sideWallDmg3 += 1;
				}
			}
			
			// 측벽.균열
			if(egovMap.get("sidewallcrk") != null) {
				String sideWall2 = egovMap.get("sidewallcrk").toString();
				if(sideWall2.equals("5%미만")){
					sideWallCrk1 += 1;
				}else if(sideWall2.equals("5~20%미만")){
					sideWallCrk2 += 1;
				}else if(sideWall2.equals("20%이상")){
					sideWallCrk3 += 1;
				}else if(sideWall2.equals("누수없음")){
					sideWallCrk4 += 1;
				}else if(sideWall2.equals("누수흔적관찰")){
					sideWallCrk5 += 1;
				}else if(sideWall2.equals("누수관찰")){
					sideWallCrk6 += 1;
				}
			}
			
			// 측벽.기초부세굴
			if(egovMap.get("sidewallbasicscour") != null) {
				String sideWall3 = egovMap.get("sidewallbasicscour").toString();
				if(sideWall3.equals("양호(보수및보강불필요)")){
					sideWallBasicScour1 += 1;
				}else if(sideWall3.equals("보통(경미결함보수필요)")){
					sideWallBasicScour2 += 1;
				}else if(sideWall3.equals("불량(중대결함보강필요)")){
					sideWallBasicScour3 += 1;
				}	
			}
			
			//물받이.파손
			if(egovMap.get("dwnsptdmg") != null) {
				String dwnSpt1 = egovMap.get("dwnsptdmg").toString();
				if(dwnSpt1.equals("5%미만")){
					dwnSptDmg1 += 1;
				}else if(dwnSpt1.equals("5~20%미만")){
					dwnSptDmg2 += 1;
				}else if(dwnSpt1.equals("20%이상")){
					dwnSptDmg3 += 1;
				}
			}
			//물받이.기초부세굴
			if(egovMap.get("dwnsptcrk") != null) {
				String dwnSpt2 = egovMap.get("dwnsptcrk").toString();
				if(dwnSpt2.equals("균열5%미만")){
					dwnSptCrk1 += 1;
				}else if(dwnSpt2.equals("균열5~20%미만")){
					dwnSptCrk2 += 1;
				}else if(dwnSpt2.equals("균열20%이상")){
					dwnSptCrk3 += 1;
				}else if(dwnSpt2.equals("재료의상태양호")){
					dwnSptCrk4 += 1;
				}else if(dwnSpt2.equals("재료의상태보통")){
					dwnSptCrk5 += 1;
				}else if(dwnSpt2.equals("재료의상태불량")){
					dwnSptCrk6 += 1;
				}
			}
			
			//물받이.기초부세굴
			if(egovMap.get("dwnsptbasicscour") != null) {
				String dwnSpt3 = egovMap.get("dwnsptbasicscour").toString();
				
				if(dwnSpt3.equals("양호(보수및보강불필요)")){
					dwnSptBasicScour1 += 1;
				}else if(dwnSpt3.equals("보통(경미결함보수필요)")){
					dwnSptBasicScour2 += 1;
				}else if(dwnSpt3.equals("불량(중대결함보강필요)")){
					dwnSptBasicScour3 += 1;
				}
			}
			
			// 비파괴.압축강도
			if(egovMap.get("cncrtcmprsstrn") != null) {
				String cncrtCmprsStrn = egovMap.get("cncrtcmprsstrn").toString();
				if(cncrtCmprsStrn.equals("설계기준강도21MPa이상")) {
					cncrtCmprsStrn1 += 1;
				}else if(cncrtCmprsStrn.equals("설계기준강도21MPa미만")) {
					cncrtCmprsStrn2 += 1;
				}else if(cncrtCmprsStrn.equals("만족")) {
					cncrtCmprsStrn3 += 1;
				}else if(cncrtCmprsStrn.equals("미달(등급조정가능)")) {
					cncrtCmprsStrn4 += 1;
				}
			}
			
			// 비파괴.균열깊이
			if(egovMap.get("cncrtcrkdpt") != null) {
				String cncrtCrkDpt = egovMap.get("cncrtcrkdpt").toString();
				if(cncrtCrkDpt.equals("부재두께의50%미만")) {
					cncrtCrkDpt1 += 1;
				}else if(cncrtCrkDpt.equals("부재두께의50%이상")) {
					cncrtCrkDpt2 += 1;
				}else if(cncrtCrkDpt.equals("만족")) {
					cncrtCrkDpt3 += 1;
				}else if(cncrtCrkDpt.equals("미달(등급조정가능)")) {
					cncrtCrkDpt4 += 1;
				}
			}
			
			// 사방댐준설여부판정
			if(egovMap.get("drdgnatjdg") != null) {
				String drdgnAtJdg = egovMap.get("drdgnatjdg").toString();
				if(drdgnAtJdg.equals("준설시행(17점이상)")) {
					drdgnAtJdg1 += 1;
				}else if(drdgnAtJdg.equals("준설불요(17점미만)")) {
					drdgnAtJdg2 += 1;
				}
			}
			
			// 완료여부별
			if(egovMap.get("complt") != null) {
				String complt = egovMap.get("complt").toString();
				if(complt.equals("미조사")) {
					complt1 += 1;
				}else if(complt.equals("완료")) {
					complt2 += 1;
				}else if(complt.equals("미완료")) {
					complt3 += 1;
				}else if(complt.equals("보완조사")) {
					complt4 += 1;
				}else if(complt.equals("기타")) {
					complt5 += 1;
				}
			}
		}
		
		// 조사유형별
        JSONArray svyTypeJson = new JSONArray();
        JSONArray svyTypeItemX = new JSONArray();
        JSONArray svyTypeItem1 = new JSONArray();
        
        svyTypeItemX.put("nm");
        svyTypeItem1.put("건수");
        
        svyTypeItemX.put("사방댐");
        svyTypeItem1.put(sabang);
        
        svyTypeItemX.put("계류보전");
        svyTypeItem1.put(gyeryu);
        
        svyTypeItemX.put("산지사방");
        svyTypeItem1.put(sanji);
        
        svyTypeItemX.put("해안방재");
        svyTypeItem1.put(haebang);
        
        svyTypeItemX.put("해안침식");
        svyTypeItem1.put(haechim);
        
        svyTypeJson.put(svyTypeItemX);
        svyTypeJson.put(svyTypeItem1);
        
        model.addAttribute("svyTypeList", svyTypeJson);
        
        // 본댐 파손
        JSONArray odDam1Json = new JSONArray();
        JSONArray odDam1ItemX = new JSONArray();
        JSONArray odDam1Item1 = new JSONArray();
        
        odDam1ItemX.put("nm");
        odDam1Item1.put("건수");
        
        odDam1ItemX.put("5% 미만");
        odDam1Item1.put(orginlDamDmg1);
        
        odDam1ItemX.put("5~15% 미만");
        odDam1Item1.put(orginlDamDmg2);
        
        odDam1ItemX.put("15% 이상");
        odDam1Item1.put(orginlDamDmg3);
        
        odDam1Json.put(odDam1ItemX);
        odDam1Json.put(odDam1Item1);
        
        model.addAttribute("odDam1List", odDam1Json);
        
        // 본댐 균열
        JSONArray odDam2Json = new JSONArray();
        JSONArray odDam2ItemX = new JSONArray();
        JSONArray odDam2Item1 = new JSONArray();
        
        odDam2ItemX.put("nm");
        odDam2Item1.put("건수");
        
        odDam2ItemX.put("균열 5% 미만");
        odDam2Item1.put(orginlDamCrk1);
        
        odDam2ItemX.put("균열 5~15% 미만");
        odDam2Item1.put(orginlDamCrk2);
        
        odDam2ItemX.put("균열 15% 이상");
        odDam2Item1.put(orginlDamCrk3);
        
        odDam2ItemX.put("가로균열이 댐길이의 10% 미만");
        odDam2Item1.put(orginlDamCrk4);
        
        odDam2ItemX.put("가로균열이 댐길이의 10~30% 미만");
        odDam2Item1.put(orginlDamCrk5);
        
        odDam2ItemX.put("가로균열이 댐길이의 30% 이상");
        odDam2Item1.put(orginlDamCrk6);
        
        odDam2ItemX.put("세로균열이 유효고의 10% 미만");
        odDam2Item1.put(orginlDamCrk7);
        
        odDam2ItemX.put("세로균열이 유효고의 10~30% 미만");
        odDam2Item1.put(orginlDamCrk8);
        
        odDam2ItemX.put("세로균열이 유효고의 30% 이상");
        odDam2Item1.put(orginlDamCrk9);
        
        odDam2ItemX.put("누수 없음");
        odDam2Item1.put(orginlDamCrk10);
        
        odDam2ItemX.put("누수흔적 관찰");
        odDam2Item1.put(orginlDamCrk11);
        
        odDam2ItemX.put("누수 관찰");
        odDam2Item1.put(orginlDamCrk12);
        
        odDam2Json.put(odDam2ItemX);
        odDam2Json.put(odDam2Item1);
        
        model.addAttribute("odDam2List", odDam2Json);
        
        // 본댐 기초부 세굴 수문시설
        JSONArray odDam3Json = new JSONArray();
        JSONArray odDam3ItemX = new JSONArray();
        JSONArray odDam3Item1 = new JSONArray();
        JSONArray odDam3Item2 = new JSONArray();
        JSONArray odDam3Item3 = new JSONArray();
        
        odDam3ItemX.put("nm");
        odDam3Item1.put("양호");
        odDam3Item2.put("보통");
        odDam3Item3.put("불량");
        
        
        odDam3ItemX.put("기초부 세굴");
        odDam3Item1.put(orginlDamBasicScour1);
        odDam3Item2.put(orginlDamBasicScour2);
        odDam3Item3.put(orginlDamBasicScour3);
        
        odDam3ItemX.put("수문시설");
        odDam3Item1.put(orginlDamWtgate1);
        odDam3Item2.put(orginlDamWtgate2);
        odDam3Item3.put(orginlDamWtgate3);
        
        odDam3Json.put(odDam3ItemX);
        odDam3Json.put(odDam3Item1);
        odDam3Json.put(odDam3Item2);
        odDam3Json.put(odDam3Item3);
        
        model.addAttribute("odDam3List", odDam3Json);
        
        // 본댐 콘크리트 전석붙임
        JSONArray odDam4Json = new JSONArray();
        JSONArray odDam4ItemX = new JSONArray();
        JSONArray odDam4Item1 = new JSONArray();
        JSONArray odDam4Item2 = new JSONArray();
        JSONArray odDam4Item3 = new JSONArray();
        
        odDam4ItemX.put("nm");
        odDam4Item1.put("5% 미만");
        odDam4Item2.put("5~20% 미만");
        odDam4Item3.put("20% 이상");
        
        odDam4ItemX.put("콘크리트");
        odDam4Item1.put(orginlDamCncrt1);
        odDam4Item2.put(orginlDamCncrt2);
        odDam4Item3.put(orginlDamCncrt3);
        
        odDam4ItemX.put("전석붙임");
        odDam4Item1.put(orginlDamPlng1);
        odDam4Item2.put(orginlDamPlng2);
        odDam4Item3.put(orginlDamPlng3);
        
        odDam4Json.put(odDam4ItemX);
        odDam4Json.put(odDam4Item1);
        odDam4Json.put(odDam4Item2);
        odDam4Json.put(odDam4Item3);
        
        model.addAttribute("odDam4List", odDam4Json);
        
        // 측벽 파손
        JSONArray sdWl1Json = new JSONArray();
        JSONArray sdWl1ItemX = new JSONArray();
        JSONArray sdWl1Item1 = new JSONArray();
        
        sdWl1ItemX.put("nm");
        sdWl1Item1.put("건수");
        
        sdWl1ItemX.put("5% 미만");
        sdWl1Item1.put(sideWallDmg1);
        
        sdWl1ItemX.put("5~15% 미만");
        sdWl1Item1.put(sideWallDmg2);
        
        sdWl1ItemX.put("15% 이상");
        sdWl1Item1.put(sideWallDmg3);
        
        sdWl1Json.put(sdWl1ItemX);
        sdWl1Json.put(sdWl1Item1);
        
        model.addAttribute("sdWl1List", sdWl1Json);
        
        // 측벽 균열
        JSONArray sdWl2Json = new JSONArray();
        JSONArray sdWl2ItemX = new JSONArray();
        JSONArray sdWl2Item1 = new JSONArray();
        
        sdWl2ItemX.put("nm");
        sdWl2Item1.put("건수");
        
        sdWl2ItemX.put("5% 미만");
        sdWl2Item1.put(sideWallCrk1);
        
        sdWl2ItemX.put("5~20% 미만");
        sdWl2Item1.put(sideWallCrk2);
        
        sdWl2ItemX.put("20% 이상");
        sdWl2Item1.put(sideWallCrk3);
        
        sdWl2ItemX.put("누수없음");
        sdWl2Item1.put(sideWallCrk4);
        
        sdWl2ItemX.put("누수흔적 관찰");
        sdWl2Item1.put(sideWallCrk5);
        
        sdWl2ItemX.put("누수 관찰");
        sdWl2Item1.put(sideWallCrk6);
        
        sdWl2Json.put(sdWl2ItemX);
        sdWl2Json.put(sdWl2Item1);
        
        model.addAttribute("sdWl2List", sdWl2Json);
        
        // 측벽 기초부 세굴
        JSONArray sdWl3Json = new JSONArray();
        JSONArray sdWl3ItemX = new JSONArray();
        JSONArray sdWl3Item1 = new JSONArray();
        JSONArray sdWl3Item2 = new JSONArray();
        JSONArray sdWl3Item3 = new JSONArray();
        
        sdWl3ItemX.put("nm");
        sdWl3Item1.put("양호");
        sdWl3Item2.put("보통");
        sdWl3Item3.put("불량");
        
        
        sdWl3ItemX.put("기초부 세굴");
        sdWl3Item1.put(sideWallBasicScour1);
        sdWl3Item2.put(sideWallBasicScour2);
        sdWl3Item3.put(sideWallBasicScour3);
        
        sdWl3Json.put(sdWl3ItemX);
        sdWl3Json.put(sdWl3Item1);
        sdWl3Json.put(sdWl3Item2);
        sdWl3Json.put(sdWl3Item3);
        
        model.addAttribute("sdWl3List", sdWl3Json);
        
        // 물받이 파손
        JSONArray wtr1Json = new JSONArray();
        JSONArray wtr1ItemX = new JSONArray();
        JSONArray wtr1Item1 = new JSONArray();
        
        wtr1ItemX.put("nm");
        wtr1Item1.put("건수");
        
        wtr1ItemX.put("5% 미만");
        wtr1Item1.put(dwnSptDmg1);
        
        wtr1ItemX.put("5~20% 미만");
        wtr1Item1.put(dwnSptDmg2);
        
        wtr1ItemX.put("20% 이상");
        wtr1Item1.put(dwnSptDmg3);
        
        wtr1Json.put(wtr1ItemX);
        wtr1Json.put(wtr1Item1);
        
        model.addAttribute("wtr1List", wtr1Json);
        
        // 물받이 균열
        JSONArray wtr2Json = new JSONArray();
        JSONArray wtr2ItemX = new JSONArray();
        JSONArray wtr2Item1 = new JSONArray();
        
        wtr2ItemX.put("nm");
        wtr2Item1.put("건수");
        
        wtr2ItemX.put("양호");
        wtr2Item1.put(dwnSptBasicScour1);
        
        wtr2ItemX.put("보통");
        wtr2Item1.put(dwnSptBasicScour2);
        
        wtr2ItemX.put("불량");
        wtr2Item1.put(dwnSptBasicScour3);
        
        wtr2Json.put(wtr2ItemX);
        wtr2Json.put(wtr2Item1);
        
        model.addAttribute("wtr2List", wtr2Json);
        
        // 물받이 기초부세굴
        JSONArray wtr3Json = new JSONArray();
        JSONArray wtr3ItemX = new JSONArray();
        JSONArray wtr3Item1 = new JSONArray();
        
        wtr3ItemX.put("nm");
        wtr3Item1.put("건수");
        
        wtr3ItemX.put("균열5% 미만");
        wtr3Item1.put(dwnSptCrk1);
        
        wtr3ItemX.put("균열5~20% 미만");
        wtr3Item1.put(dwnSptCrk2);
        
        wtr3ItemX.put("균열5~20% 이상");
        wtr3Item1.put(dwnSptCrk3);
        
        wtr3ItemX.put("균열20% 이상");
        wtr3Item1.put(dwnSptCrk4);
        
        wtr3ItemX.put("재료의 상태 양호");
        wtr3Item1.put(dwnSptCrk5);
        
        wtr3ItemX.put("재료의 상태 양호");
        wtr3Item1.put(dwnSptCrk6);
        
        
        wtr3Json.put(wtr3ItemX);
        wtr3Json.put(wtr3Item1);
        
        model.addAttribute("wtr3List", wtr3Json);
        
        
		// 준설여부판정
        JSONArray drdgnJson = new JSONArray();
        JSONArray drdgnItemX = new JSONArray();
        JSONArray drdgnItem1 = new JSONArray();
        
        drdgnItemX.put("nm");
        drdgnItem1.put("건수");
        
        drdgnItemX.put("준설시행");
        drdgnItem1.put(drdgnAtJdg1);
        
        drdgnItemX.put("준설불요");
        drdgnItem1.put(drdgnAtJdg2);
        
        drdgnJson.put(drdgnItemX);
        drdgnJson.put(drdgnItem1);
        
        model.addAttribute("drdgnList", drdgnJson);
        
        // 완료여부별
        JSONArray cmpJson = new JSONArray();
        JSONArray cmpItemX = new JSONArray();
        JSONArray cmpItem1 = new JSONArray();
        
        cmpItemX.put("nm");
        cmpItem1.put("건수");
        
        cmpItemX.put("미조사");
        cmpItem1.put(complt1);
        
        cmpItemX.put("완료");
        cmpItem1.put(complt2);
        
        cmpItemX.put("미완료");
        cmpItem1.put(complt3);
        
        cmpItemX.put("보완조사");
        cmpItem1.put(complt4);
        
        cmpItemX.put("기타");
        cmpItem1.put(complt5);
        
        cmpJson.put(cmpItemX);
        cmpJson.put(cmpItem1);
        
        model.addAttribute("cmpList", cmpJson);
        
        
        
		return "sys/spt/sts/sttusPcsDetail";
	}
}