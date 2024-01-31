package or.sabang.mng.bbs.art.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import or.sabang.mng.bbs.art.service.ArticleCommentService;
import or.sabang.mng.bbs.art.service.CommentVO;
import or.sabang.mng.bbs.art.service.Comment;

@Controller
public class ArticleCommentController {
	
	@Resource(name = "articleCommentService")
    protected ArticleCommentService articleCommentService;
    
    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Autowired
    private DefaultBeanValidator beanValidator;
    
    //protected Logger log = Logger.getLogger(this.getClass());
    
    /**
	 * @param commentVO 
	 * @param bindingResult 
	 * @param model
	 * @throws Exception
	 * @description 댓글 등록
	 */ 
    @RequestMapping("/mng/bbs/art/insertArticleComment.do")
    public String insertArticleComment(CommentVO commentVO, BindingResult bindingResult, ModelMap model) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	commentVO.setFrstRegisterId(user.getUniqId());
    	commentVO.setFrstRegisterNm(user.getName());
    	commentVO.setWrterId(user.getUniqId());
    	commentVO.setWrterNm(user.getName());
    	articleCommentService.insertArticleComment(commentVO);
    	return "forward:/mng/bbs/art/selectArticleDetail.do";
    }
    
    /**
	 * @param commentVO 
	 * @param bindingResult 
	 * @param model
	 * @throws Exception
	 * @description 댓글 수정
	 */ 
    @RequestMapping("/mng/bbs/art/articleCommentUpdate.do")
    public String updateArticleComment(CommentVO commentVO, BindingResult bindingResult, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commentVO.setLastUpdusrId(user.getName());
		articleCommentService.updateArticleComment(commentVO);
		return "forward:/mng/bbs/art/selectArticleDetail.do";
	}
	
    
    
    /**
	 * @param commentVO 
	 * @param bindingResult 
	 * @param model
	 * @throws Exception
	 * @description 댓글 삭제
	 */  
    @RequestMapping("/mng/bbs/art/articleCommentDelete.do")
    public String deleteArticleComment(CommentVO commentVO, BindingResult bindingResult, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		commentVO.setLastUpdusrId(user.getName());
		articleCommentService.deleteArticleComment(commentVO);
		
		return "forward:/mng/bbs/art/selectArticleDetail.do";
	}
    
	
}

