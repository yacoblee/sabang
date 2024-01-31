package or.sabang.mng.bbs.ntb.web;

import java.util.List;
import java.util.regex.Matcher;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.EgovFileMngService;
import or.sabang.cmm.service.EgovFileMngUtil;
import or.sabang.mng.bbs.art.service.CommentVO;
import or.sabang.mng.bbs.ntb.service.NoticeService;
import or.sabang.mng.bbs.ntb.service.NoticeVO;

@Controller
public class NoticeController {

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	@Resource(name = "noticeService")
	private NoticeService noticeService;

	@Autowired
	private DefaultBeanValidator beanValidator;
	
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);


	int cnt_total = 0;

	/**
	 * @param noticeVO 
	 * @param fileVO
	 * @param model
	 * @throws Exception
	 * @description 공지사항 목록 조회
	 */
	@RequestMapping(value = "/mng/bbs/ntb/noticeList.do")
	public String selectArticleList(@ModelAttribute("noticeVO") NoticeVO noticeVO, FileVO fileVO, ModelMap model) throws Exception {
//		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		noticeVO.setPageUnit(propertiesService.getInt("pageUnit"));
		noticeVO.setPageSize(propertiesService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(noticeVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(noticeVO.getPageUnit());
		paginationInfo.setPageSize(noticeVO.getPageSize());

		noticeVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		noticeVO.setLastIndex(paginationInfo.getLastRecordIndex());
		noticeVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<NoticeVO> list = null;
		list = noticeService.selectNoticeList(noticeVO);
		cnt_total = noticeService.selectNoticeTotCnt(noticeVO);
		paginationInfo.setTotalRecordCount(cnt_total);

		model.addAttribute("resultList", list);
		model.addAttribute("paginationInfo", paginationInfo);

		return "mng/bbs/ntb/noticeList";
	}

	/**
	 * @param noticeVO 
	 * @param fileVO
	 * @param commentVO
	 * @param model
	 * @throws Exception
	 * @description 공지사항 상세 조회
	 */
	@RequestMapping(value = "/mng/bbs/ntb/selectNoticeDetail.do")
	public String selectNoticeDetail(NoticeVO noticeVO, FileVO fileVO, @ModelAttribute("commentVO") CommentVO commentVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("userId",user.getName());
		
		// 조회수 증가
		noticeService.IncrsNoticeRdcnt(noticeVO);

		NoticeVO result = noticeService.selectNoticeDetail(noticeVO);
		String nttCn = result.getNttCn(); 
		
		String blankNttCn = HtmlUtils.htmlUnescape(nttCn);
		String newBlankNttCn = blankNttCn.replaceAll(Matcher.quoteReplacement("&apos;"), "'");
		
		result.setNttCn(newBlankNttCn);
		model.addAttribute("resultList", result);

		return "mng/bbs/ntb/noticeDetail";
	}

	/**
	 * @param noticeVO 
	 * @param model
	 * @throws Exception
	 * @description 공지사항 등록화면 이동
	 */ 
	@RequestMapping(value = "/mng/bbs/ntb/noticeRegistView.do")
	public String insertNoticeView(NoticeVO noticeVO, ModelMap model) throws Exception {
		return "mng/bbs/ntb/noticeRegist";
	}

	/**
	 * @param noticeVO 
	 * @param model
	 * @throws Exception
	 * @description 공지사항 등록
	 */
	@RequestMapping(value = "/mng/bbs/ntb/noticeRegist.do")
	public ModelAndView insertNotice(NoticeVO noticeVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();		
    		noticeVO.setFrstRegisterId(user.getName());
    		noticeService.insertNotice(noticeVO);
    		mv.addObject("status","success");
	    	mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
	}

	/**
	 * @param noticeVO 
	 * @param model
	 * @throws Exception
	 * @description 공지사항 수정화면 이동
	 */
	@RequestMapping("/mng/bbs/ntb/updateNoticeView.do")
	public String updateNoticeView(NoticeVO noticeVO, ModelMap model) throws Exception {
		NoticeVO result = noticeService.selectNoticeDetail(noticeVO);
		String nttCn = result.getNttCn(); 
		
		String blankNttCn = HtmlUtils.htmlUnescape(nttCn);
		String newBlankNttCn = blankNttCn.replaceAll(Matcher.quoteReplacement("&apos;"), "'");
		
		result.setNttCn(newBlankNttCn);
		
		model.addAttribute("noticeVO", result);

		return "mng/bbs/ntb/noticeUpdt";
	}

	/**
	 * @param noticeVO 
	 * @param model
	 * @throws Exception
	 * @description 공지사항 수정
	 */
	@RequestMapping("/mng/bbs/ntb/noticeUpdate.do")
	public ModelAndView updateNotice(NoticeVO noticeVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();		
    		noticeVO.setLastUpdusrId(user.getName());
    		noticeService.updateNotice(noticeVO);
    		mv.addObject("status","success");
	    	mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
	}
	
	/**
	 * @param noticeVO 
	 * @param model
	 * @throws Exception
	 * @description 공지사항 삭제
	 */
	@RequestMapping("/mng/bbs/ntb/noticeDelete.do")
	public ModelAndView deleteNotice(NoticeVO noticeVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();		
    		noticeVO.setLastUpdusrId(user.getName());
    		noticeService.deleteNotice(noticeVO);
    		mv.addObject("status","success");
	    	mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
	}
}
