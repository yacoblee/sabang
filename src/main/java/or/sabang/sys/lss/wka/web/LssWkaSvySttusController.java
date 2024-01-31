package or.sabang.sys.lss.wka.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.lss.wka.service.LssWkaSvySttusService;

@Controller
public class LssWkaSvySttusController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "lssWkaSvySttusService")
	private LssWkaSvySttusService lssWkaSvySttusService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LssWkaSvySttusController.class);
	

	/**
	 * 기초조사 현황을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/wka/sts/selectWkaSvySttus.do")	
    public String selectWkaSvySttus (ModelMap model) throws Exception {
		
		List<EgovMap> allSvyList = lssWkaSvySttusService.selectWkaSvySttusTotCnt();
		List<EgovMap> regionList = lssWkaSvySttusService.selectWkaSvySttusRegion();
		List<EgovMap> monthList = lssWkaSvySttusService.selectWkaSvySttusMonth();
		
		JSONObject allSvyJson = new JSONObject();
		for (EgovMap egovMap : allSvyList) {
			allSvyJson.put(egovMap.get("nm").toString(), egovMap.get("cnt").toString());
		}
		
		JSONArray regionJson = new JSONArray();
		JSONArray regionNmArr = new JSONArray();
		JSONArray regionComptArr = new JSONArray();
		
		regionNmArr.put("nm");
		regionComptArr.put("조사완료");
		
		for (EgovMap egovMap : regionList) {
			regionNmArr.put(egovMap.get("nm").toString());
			regionComptArr.put(egovMap.get("comp").toString());
		}
		regionJson.put(regionNmArr);
		regionJson.put(regionComptArr);
		
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
		
		return "sys/lss/wka/sts/surveySttusList";		
	}
	
}
