package or.sabang.sys.bbs.art.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import freemarker.template.utility.HtmlEscape;
import or.sabang.cmm.service.EgovFileMngService;
import or.sabang.cmm.service.EgovFileMngUtil;
import or.sabang.mng.bbs.art.service.ArticleCommentService;
import or.sabang.mng.bbs.art.service.ArticleService;
import or.sabang.mng.bbs.art.service.ArticleVO;
import or.sabang.mng.bbs.art.service.CommentVO;
import or.sabang.mng.bbs.ntb.web.NoticeController;

@Controller
public class SysArticleController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
	
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
	@Resource(name = "articleService")
    private ArticleService articleService;
	
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	@Resource(name = "articleUploadPath")
	String articleUploadPath;
	
	@Resource(name = "articleCommentService")
    protected ArticleCommentService articleCommentService;
	
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SysArticleController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param articleVO 
	 * @param fileVO
	 * @param model
	 * @throws Exception
	 * @description 게시물 목록 조회
	 */
	@RequestMapping(value = "/sys/bbs/art/articleList.do")
	public String selectArticleList(@ModelAttribute("articleVO") ArticleVO articleVO, FileVO fileVO, ModelMap model, HttpServletRequest request) throws Exception {
//		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//
//		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	//KISA 보안취약점 조치 (2018-12-10, 이정은)
//
//        if(!isAuthenticated) {
//            return "egovframework/com/uat/uia/EgovLoginUsr";
//        }
		
		int pageUnit = articleVO.getPageUnit();
		/** EgovPropertyService.sample */
		articleVO.setPageUnit(propertiesService.getInt("pageUnit"));
		articleVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(articleVO.getPageIndex());
		paginationInfo.setPageSize(articleVO.getPageSize());
		
		articleVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(articleVO.getPageUnit());

		articleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		articleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		articleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		List<ArticleVO> list = null;
		list = articleService.selectArticleList(articleVO);
		cnt_total = articleService.selectArticleTotCnt(articleVO);
		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "sys/bbs/art/articleList";
	}
	
	/**
	 * @param articleVO 
	 * @param fileVO
	 * @param commentVO
	 * @param model
	 * @throws Exception
	 * @description 게시물 상세 조회
	 */
	@RequestMapping(value = "/sys/bbs/art/selectArticleDetail.do")
	public String selectArticleDetail(@ModelAttribute("articleVO") ArticleVO articleVO, FileVO fileVO, @ModelAttribute("commentVO") CommentVO commentVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		model.addAttribute("userId",user.getName());
		model.addAttribute("sessionUniqId", user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		
		// 조회수 증가
		articleService.IncrsArticleRdcnt(articleVO);
		
		ArticleVO result = articleService.selectArticleDetail(articleVO);
		String atchFileId =result.getAtchFileId(); 
		if( atchFileId != null) {
			fileVO.setAtchFileId(atchFileId);
			List<FileVO> fileList = fileMngService.selectFileInfs(fileVO);
			model.addAttribute("fileList", fileList);
		}
		
		//----------------------------
		// 댓글 처리
		//----------------------------
		CommentVO articleCommentVO = new CommentVO();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = articleCommentService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
		
		//----------------------------
	    //댓글 처리 END
		//----------------------------
//		List<CommentVO> cmtList =  articleCommentService.selectArticleCommentList(commentVO);
		model.addAttribute("result", result);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("cmtList", map.get("resultList"));
		model.addAttribute("cmtCnt", map.get("resultCnt"));
		model.addAttribute("articleCommentVO", articleCommentVO);
		
		return "sys/bbs/art/articleDetail";
	}
	
	/**
	 * @param articleVO 
	 * @param model
	 * @throws Exception
	 * @description 게시물 등록화면 이동
	 */  
	@RequestMapping(value = "/sys/bbs/art/articleRegistView.do")
	public String insertArticleView(ArticleVO articleVO, ModelMap model) throws Exception {
		return "sys/bbs/art/articleRegist";
	}
	
	/**
	 * @param articleVO 
	 * @param model
	 * @param multiRequest
	 * @throws Exception
	 * @description 게시물 등록
	 */
	@RequestMapping(value = "/sys/bbs/art/articleRegist.do") 
	public ModelAndView insertArticle(ArticleVO articleVO, ModelMap model, MultipartHttpServletRequest multiRequest) throws Exception {

		ModelAndView mv = new ModelAndView("jsonView");
		try {
//			beanValidator.validate(articleVO, bindingResult); //validation 수행
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
//			if(!isAuthenticated) {
//				return
//			}
			List<FileVO> result = null;
		    String atchFileId = null;
			
		    final List<MultipartFile> files = multiRequest.getFiles("file_1");
		    if (!files.isEmpty()) {
		    	result = fileUtil.parseFileInf(files, "BBS_", 1, "", "Globals.fileStorePath.article");
		    	atchFileId = fileMngService.insertFileInfs(result);
		    }
		    articleVO.setAtchFileId(atchFileId);
		    articleVO.setFrstRegisterId(user.getName());
		    articleService.insertArticle(articleVO);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;
	}

	/**
	 * @param articleVO 
	 * @param model
	 * @throws Exception
	 * @description 게시물 수정화면 이동
	 */
	@RequestMapping("/sys/bbs/art/updateArticleView.do")
	public String updateArticleView(ArticleVO articleVO, ModelMap model) throws Exception {
		
		ArticleVO result = articleService.selectArticleDetail(articleVO);
		
		String nttCn = result.getNttCn(); 
		
		String blankNttCn = HtmlUtils.htmlUnescape(nttCn);
		String newBlankNttCn = blankNttCn.replaceAll(Matcher.quoteReplacement("&apos;"), "'");
		
		result.setNttCn(newBlankNttCn);
		
		model.addAttribute("articleVO", result);
		
		return "sys/bbs/art/articleUpdt";  
    }
	
	/**
	 * @param articleVO 
	 * @param model
	 * @param multiRequest
	 * @throws Exception
	 * @description 게시물 수정
	 */
	@RequestMapping("/sys/bbs/art/articleUpdate.do")
	public ModelAndView updateArticle(ArticleVO articleVO, ModelMap model, MultipartHttpServletRequest multiRequest) throws Exception {
		
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			String atchFileId = articleVO.getAtchFileId();
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			
			if(!files.isEmpty()){
				if("".equals(atchFileId)){
					List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");	
					atchFileId = fileMngService.insertFileInfs(result); // 기존에 첨부파일 ID가 없다.
					articleVO.setAtchFileId(atchFileId); // 관련 비즈니스 규칙에 따라서 생성된 첨부파일 ID 정보를 세팅한다.
				}else{
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(atchFileId); // 최종 파일 순번을 획득하기 위하여 VO에 현재 첨부파일 ID를 세팅한다.
					int cnt = fileMngService.getMaxFileSN(fvo); // 해당 첨부파일 ID에 속하는 최종 파일 순번을 획득한다.
					List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");	
					fileMngService.updateFileInfs(result);
				}
			}
			
			articleVO.setLastUpdusrId(user.getName());
			articleService.updateArticle(articleVO);
			mv.addObject("status","success");
			mv.addObject("message", egovMessageSource.getMessage("success.common.update"));
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
		return mv;	
	}
	
	/**
	 * @param articleVO 
	 * @param model
	 * @throws Exception
	 * @description 게시물 삭제
	 */
	@RequestMapping("/sys/bbs/art/articleDelete.do")
	public ModelAndView deleteArticle(ArticleVO articleVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
    	try {
    		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();		
    		articleVO.setLastUpdusrId(user.getName());
    		articleService.deleteArticle(articleVO);
    		mv.addObject("status","success");
	    	mv.addObject("message", egovMessageSource.getMessage("success.common.delete"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			mv.addObject("status","fail");
			mv.addObject("message", egovMessageSource.getMessage("fail.common.msg"));
		}
    	return mv;
	}
	
	/**
     * @param fileVO
     * @param commandMap
     * @param model
     * @throws Exception
     * @description 첨부파일에 대한 목록을 조회한다.
     */
    @RequestMapping("/sys/bbs/art/selectFileInfs.do")
    public String selectFileInfs(FileVO fileVO, @RequestParam Map<String, Object> commandMap, ModelMap model) throws Exception {
		String atchFileId = (String)commandMap.get("param_atchFileId");
	
		fileVO.setAtchFileId(atchFileId);
		List<FileVO> result = fileMngService.selectFileInfs(fileVO);
	
		model.addAttribute("fileList", result);
		model.addAttribute("updateFlag", "N");
		model.addAttribute("fileListCnt", result.size());
		model.addAttribute("atchFileId", atchFileId);
	
		return "/sys/bbs/art/fileList";
    }
	/**
     * @param fileVO
     * @param commandMap
     * @param model
     * @throws Exception
     * @description 첨부파일 변경을 위한 수정페이지로 이동한다.
     */
    @RequestMapping("/sys/bbs/art/selectFileInfsForUpdate.do")
    public String selectFileInfsForUpdate(FileVO fileVO, @RequestParam Map<String, Object> commandMap, ModelMap model) throws Exception {

	String atchFileId = (String)commandMap.get("param_atchFileId");

	fileVO.setAtchFileId(atchFileId);

	List<FileVO> result = fileMngService.selectFileInfs(fileVO);

	model.addAttribute("fileList", result);
	model.addAttribute("updateFlag", "Y");
	model.addAttribute("fileListCnt", result.size());
	model.addAttribute("atchFileId", atchFileId);

	return "/sys/bbs/art/fileList";
    }
	
    /**
     * @param fileVO
     * @param model
     * @throws Exception
     * @description 첨부파일에 대한 삭제를 처리한다.
     */
    @RequestMapping("/sys/bbs/art/deleteFileInfs.do")
    public String deleteFileInf(FileVO fileVO, ModelMap model) throws Exception {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (isAuthenticated) {
		    fileMngService.deleteFileInf(fileVO);
		}
		 return "blank";
    }
    
	/**
     * 첨부파일 다운로드
     *
     * @param fileVO
     * @param atchFileId
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/sys/bbs/art/articleFileDownload.do")
    public void downloadArticleFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("atchFileId") String atchFileId,@RequestParam("fileSn") String fileSn) throws Exception {
    	
    	FileVO fileVO = new FileVO();
		fileVO.setAtchFileId(atchFileId);
		fileVO.setFileSn(fileSn);
		FileVO fvo = fileMngService.selectFileInf(fileVO);

		File uFile = new File(fvo.getFileStreCours(), fvo.getStreFileNm());
		long fSize = uFile.length();
		if (fSize > 0) {
			String mimetype = "application/x-msdownload";
			
			String userAgent = request.getHeader("User-Agent");
			HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
			if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
				mimetype = "application/x-stuff";
			}

			String contentDisposition = EgovBrowserUtil.getDisposition(fvo.getOrignlFileNm(),userAgent,"UTF-8");
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition", contentDisposition);
			response.setContentLengthLong(fSize);

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
