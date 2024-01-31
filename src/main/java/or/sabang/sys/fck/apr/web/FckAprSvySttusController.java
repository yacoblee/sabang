package or.sabang.sys.fck.apr.web;

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
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprSvySttusService;

@Controller
public class FckAprSvySttusController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "fckAprSvySttusService")
	private FckAprSvySttusService fckAprSvySttusService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FckAprSvySttusController.class);
	

	/**
	 * 기초조사 현황을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/fck/apr/sts/selectAprSvySttus.do")	
    public String selectBscSvySttus (ModelMap model) throws Exception {
		
		List<EgovMap> allSvyList = fckAprSvySttusService.selectAprSvySttusTotCnt();
		List<EgovMap> regionList = fckAprSvySttusService.selectAprSvySttusRegion();
		List<EgovMap> monthList = fckAprSvySttusService.selectAprSvySttusMonth();
		
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
		model.addAttribute("svyMonth", monthJson);
		
		return "sys/fck/apr/sts/surveySttusList";		
	}
}
