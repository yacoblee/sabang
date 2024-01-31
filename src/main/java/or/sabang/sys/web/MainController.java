package or.sabang.sys.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.mng.bbs.art.service.ArticleService;
import or.sabang.mng.bbs.art.service.ArticleVO;
import or.sabang.mng.bbs.ntb.service.NoticeService;
import or.sabang.mng.bbs.ntb.service.NoticeVO;
import or.sabang.sys.fck.apr.service.FckAprSvySttusService;
import or.sabang.sys.lss.bsc.service.LssBscSvySttusService;



@Controller
public class MainController {

	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	@Resource(name = "articleService")
    private ArticleService articleService;
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;

	@Resource(name = "lssBscSvySttusService")
	private LssBscSvySttusService lssBscSvySttusService;
	
	@Resource(name = "fckAprSvySttusService")
	private FckAprSvySttusService fckAprSvySttusService;
	
	@RequestMapping(value = "/sys/main.do")
	public String systemMain(ModelMap model) throws Exception {
		LOGGER.info("system Main Controller Called..");
		LoginVO userInfo = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		HashMap<String, Object> popMap = new HashMap<>();
		
		popMap.put("mberId", userInfo.getId());
		int popCnt = lssBscSvySttusService.selectBscSvyPopultnMember(popMap);
		
		if(popCnt > 0) {
			popMap.put("popNo", popCnt);
		}else {
			popMap.put("popNo", 18000);
		}
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("limit",3);
		
		List<?> articleList = articleService.selectMainArticleList(map);
		List<?> noticeList = noticeService.selectMainNoticeList(map);
		
		/*기초조사 조사현황 조회*/
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
		
		regionNmArr.put("name");
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
		
		
		model.addAttribute("articleList", articleList);
		model.addAttribute("noticeList", noticeList);
		
		model.addAttribute("svySum", allSvyJson);
		model.addAttribute("svyRegion", regionJson);
		model.addAttribute("svyMonth", monthJson);		
		/*기초조사 조사현황 조회*/
		
		/*외관점검 조사현황 조회*/
		List<EgovMap> allSvyList2 = fckAprSvySttusService.selectAprSvySttusTotCnt();
		List<EgovMap> regionList2 = fckAprSvySttusService.selectAprSvySttusRegion();
		List<EgovMap> monthList2= fckAprSvySttusService.selectAprSvySttusMonth();
		
		JSONObject allSvyJson2 = new JSONObject();
		for (EgovMap egovMap : allSvyList2) {
			allSvyJson2.put(egovMap.get("nm").toString(), egovMap.get("cnt").toString());
		}
		
		JSONArray regionJson2 = new JSONArray();
		JSONArray regionItemX2 = new JSONArray();
		JSONArray regionItem3 = new JSONArray();
		JSONArray regionItem4 = new JSONArray();
		
		regionItemX2.put("nm");
		regionItem3.put("조사대기");
		regionItem4.put("조사완료");
		
		for (EgovMap egovMap : regionList2) {
			regionItemX2.put(egovMap.get("nm").toString());
			regionItem3.put(egovMap.get("strp").toString());
			regionItem4.put(egovMap.get("comp").toString());
		}
		regionJson2.put(regionItemX2);
		regionJson2.put(regionItem3);
		regionJson2.put(regionItem4);
		
		JSONArray monthJson2 = new JSONArray();
		JSONArray monthItemX2 = new JSONArray();
		JSONArray monthItemVal2 = new JSONArray();
		monthItemX2.put("name");
		monthItemVal2.put("조사완료");
		for (EgovMap egovMap : monthList2) {
			monthItemX2.put(egovMap.get("mon").toString());
			monthItemVal2.put(egovMap.get("cnt").toString());
		}
		monthJson2.put(monthItemX2);
		monthJson2.put(monthItemVal2);
		
		model.addAttribute("svySum2", allSvyJson2);
		model.addAttribute("svyRegion2", regionJson2);
		model.addAttribute("svyMonth2", monthJson2);
		/*외관점검 조사현황 조회*/
		
		
		return "sys/main";
	}
	
	// context-security.xml 설정
	// csrf="true"인 경우 csrf Token이 없는경우 이동하는 페이지
	// csrfAccessDeniedUrl="/egovCSRFAccessDenied.do"
	@RequestMapping("/egovCSRFAccessDenied.do")
	public String egovCSRFAccessDenied() {
		return "cmm/error/csrfAccessDenied";
	}
	
	/**
	 * 개인정보처리방침 화면
	 * @return
	 */
//	@RequestMapping("/sys/cms/privacyPolicy.do")
//	public String privacyPolicy() {
//		return "sys/cms/privPolc";
//	}
	
}