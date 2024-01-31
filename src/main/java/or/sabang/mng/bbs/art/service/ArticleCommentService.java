package or.sabang.mng.bbs.art.service;

import java.util.Map;

public interface ArticleCommentService {
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 목록조회
	 */
	public Map<String, Object> selectArticleCommentList(CommentVO commentVO) throws Exception;
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 등록
	 */
	public void insertArticleComment(CommentVO commentVO) throws Exception;
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 수정
	 */
	public void updateArticleComment(CommentVO commentVO) throws Exception;
	
	/**
	 * @param commentVO
	 * @return
	 * @description 댓글 삭제
	 */
	public void deleteArticleComment(CommentVO commentVO) throws Exception;
	
}
