package or.sabang.mng.bbs.art.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.mng.bbs.art.service.CommentVO;

@Repository("articleCommentDAO")
public class ArticleCommentDAO extends EgovComAbstractDAO {
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 목록조회
	 */
	public List<?> selectArticleCommentList(CommentVO commentVO) {
		return list("ArticleComment.selectArticleCommentList", commentVO);
	}
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 갯수조회
	 */
	public int selectArticleCommentListCnt(CommentVO commentVO) {
		return (Integer)selectOne("ArticleComment.selectArticleCommentListCnt", commentVO);
	}
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 등록
	 */
	public void insertArticleComment(CommentVO commentVO) {
		insert("ArticleComment.insertArticleComment", commentVO);
	}
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 수정
	 */
	public void updateArticleComment(CommentVO commentVO) {
		update("ArticleComment.updateArticleComment", commentVO);
	}
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 삭제
	 */
	public void deleteArticleComment(CommentVO commentVO) {
		update("ArticleComment.deleteArticleComment", commentVO);
	}

}
