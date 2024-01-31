package or.sabang.sys.lss.bsc.web;

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
import or.sabang.sys.lss.bsc.service.LssBscSvySttusService;

@Controller
public class LssBscSvySttusController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "lssBscSvySttusService")
	private LssBscSvySttusService lssBscSvySttusService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LssBscSvySttusController.class);
	

	/**
	 * 기초조사 현황을 조회한다.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/lss/bsc/sts/selectBscSvySttus.do")	
    public String selectBscSvySttus (ModelMap model) throws Exception {
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		HashMap<String, Object> popMap = new HashMap<>();
		popMap.put("mberId", userInfo.getId());
		int popCnt = lssBscSvySttusService.selectBscSvyPopultnMember(popMap);
		
		if(popCnt > 0) {
			popMap.put("popNo", popCnt);
		}else {
			popMap.put("popNo", 18000);
		}

		List<EgovMap> allSvyList = lssBscSvySttusService.selectBscSvySttusTotCnt(popMap);
		List<EgovMap> regionList = lssBscSvySttusService.selectBscSvySttusRegion();
		List<EgovMap> monthList = lssBscSvySttusService.selectBscSvySttusMonth();
		
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
		
		return "sys/lss/bsc/sts/surveySttusList";		
	}
	
	/**
	 * @param model
	 * @return
	 * @throws Exception
	 *  @description 모집단 변경 팝업
	 */
	@RequestMapping(value = "/sys/lss/bsc/sts/insertBscSvyPopultnPopup.do")
	public String insertCnrsSpcePopup(
			ModelMap model) throws Exception {
		
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId", loginVO.getId());
		
		return "sys/lss/bsc/sts/insertBscSvyPopultnPopup";
	}
	
	
	@RequestMapping(value = "/sys/lss/bsc/sts/insertBscSvyPopultn.do")
	public ModelAndView insertBscSvyPopultn(
			HttpServletRequest request, Model model) throws Exception {
		ModelAndView mv = null;
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			mv = new ModelAndView("forward:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			String mberId = request.getParameter("mberId");
			String popultnCnt = request.getParameter("popultnCnt");
			HashMap<String, Object> popMap = new HashMap<>();
			popMap.put("mber_id",mberId);
			popMap.put("popultn_cnt",popultnCnt);
			try {
				lssBscSvySttusService.insertBscSvyPopultn(popMap);
				mv.addObject("status","success");
				mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
			}catch (Exception e) {
				e.printStackTrace();
				mv.addObject("status","fail");
				mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
			}
		}
		
		return mv;
	}
}
