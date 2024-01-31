package or.sabang.sys.vyt.frd.web;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.vyt.frd.service.VytFrdSttusService;

@Controller
public class VytFrdSttusController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "vytFrdSttusService")
	private VytFrdSttusService vytFrdSttusService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
    /** 허용할 확장자를 .확장자 형태로 연달아 기술한다. ex) .gif.jpg.jpeg.png => globals.properties */
    private final String extWhiteList = EgovProperties.getProperty("Globals.fileUpload.Extensions");
    /** 첨부 최대 파일 크기 지정 */
    private final long maxFileSize = Long.parseLong(EgovProperties.getProperty("Globals.fileUpload.maxSize"));
	/** 첨부파일 위치 지정  => globals.properties */
	private final String fieldBookDir = EgovProperties.getProperty("Globals.fileStorePath.fieldBook");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VytFrdSttusController.class);
	
	/**
	 * 임도타당성평가 현황을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/vyt/frd/sts/selectFrdSvySttus.do")
    public String selectVytFrdSttus(ModelMap model) throws Exception {
		
		List<EgovMap> allSvyList = vytFrdSttusService.selectFrdSvySttusTotCnt();
		List<EgovMap> regionList = vytFrdSttusService.selectFrdSvySttusRegion();
		List<EgovMap> monthList = vytFrdSttusService.selectFrdSvySttusMonth();
		
		List<EgovMap> compentList = vytFrdSttusService.selectFrdSvySttusCompentAuth();
		
		JSONObject allSvyJson = new JSONObject();
		for (EgovMap egovMap : allSvyList) {
			allSvyJson.put(egovMap.get("nm").toString(), egovMap.get("cnt").toString());
		}
		
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
		
		JSONArray compentJson = new JSONArray();
		JSONArray compentItemX = new JSONArray();
		JSONArray compentItem1 = new JSONArray();
		JSONArray compentItem2 = new JSONArray();
		
		compentItemX.put("nm");
		compentItem1.put("조사대기");
		compentItem2.put("조사완료");
		
		for (EgovMap egovMap : compentList) {
			compentItemX.put(egovMap.get("nm").toString());
			compentItem1.put(egovMap.get("strp").toString());
			compentItem2.put(egovMap.get("comp").toString());
		}
		compentJson.put(compentItemX);
		compentJson.put(compentItem1);
		compentJson.put(compentItem2);
		
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
		
		model.addAttribute("svySum", allSvyJson);
		model.addAttribute("svyRegion", regionJson);
		model.addAttribute("svyCompent", compentJson);
		model.addAttribute("svyMonth", monthJson);
		
		
		return "sys/vyt/frd/sts/surveySttusList";		
	}

}
