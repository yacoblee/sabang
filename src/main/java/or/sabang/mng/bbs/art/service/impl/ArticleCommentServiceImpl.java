package or.sabang.mng.bbs.art.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import or.sabang.mng.bbs.art.service.ArticleCommentService;
import or.sabang.mng.bbs.art.service.CommentVO;

@Service("articleCommentService")
public class ArticleCommentServiceImpl extends EgovAbstractServiceImpl implements ArticleCommentService {
	
	@Resource(name = "articleCommentDAO")
    private ArticleCommentDAO articleCommentDAO;
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 목록조회
	 */
	@Override
	public Map<String, Object> selectArticleCommentList(CommentVO commentVO) throws Exception {
		List<?> result = articleCommentDAO.selectArticleCommentList(commentVO);
		int cnt = articleCommentDAO.selectArticleCommentListCnt(commentVO);
				
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 등록
	 */
	@Override
	public void insertArticleComment(CommentVO commentVO) throws Exception {
		articleCommentDAO.insertArticleComment(commentVO);
	}
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 수정
	 */
	@Override
	public void updateArticleComment(CommentVO commentVO) throws Exception {
		articleCommentDAO.updateArticleComment(commentVO);
	}
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 삭제
	 */
	@Override
	public void deleteArticleComment(CommentVO commentVO) throws Exception {
		articleCommentDAO.deleteArticleComment(commentVO);
	}
}
