package or.sabang.sys.bbs.ntb.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

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
public class SysNoticeController {
	
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


	int cnt_total = 0;

	/**
	 * @param noticeVO 
	 * @param fileVO
	 * @param model
	 * @throws Exception
	 * @description 공지사항 목록 조회
	 */
	@RequestMapping(value = "/sys/bbs/ntb/noticeList.do")
	public String selectArticleList(@ModelAttribute("noticeVO") NoticeVO noticeVO, FileVO fileVO, ModelMap model, HttpServletRequest request) throws Exception {
//		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		int pageUnit = noticeVO.getPageUnit();
		/** EgovPropertyService.sample */
		noticeVO.setPageUnit(propertiesService.getInt("pageUnit"));
		noticeVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(noticeVO.getPageIndex());
		paginationInfo.setPageSize(noticeVO.getPageSize());
		
		noticeVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(noticeVO.getPageUnit());

		noticeVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		noticeVO.setLastIndex(paginationInfo.getLastRecordIndex());
		noticeVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<NoticeVO> list = null;
		list = noticeService.selectNoticeList(noticeVO);
		cnt_total = noticeService.selectNoticeTotCnt(noticeVO);
		paginationInfo.setTotalRecordCount(cnt_total);

		model.addAttribute("resultList", list);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sys/bbs/ntb/noticeList";
	}

	/**
	 * @param noticeVO 
	 * @param fileVO
	 * @param commentVO
	 * @param model
	 * @throws Exception
	 * @description 공지사항 상세 조회
	 */
	@RequestMapping(value = "/sys/bbs/ntb/selectNoticeDetail.do")
	public String selectNoticeDetail(@ModelAttribute("noticeVO") NoticeVO noticeVO, FileVO fileVO, @ModelAttribute("commentVO") CommentVO commentVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		// 조회수 증가
		noticeService.IncrsNoticeRdcnt(noticeVO);

		NoticeVO result = noticeService.selectNoticeDetail(noticeVO);
		model.addAttribute("resultList", result);

		return "sys/bbs/ntb/noticeDetail";
	}
}
