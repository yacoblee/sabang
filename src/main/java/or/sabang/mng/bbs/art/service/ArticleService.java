package or.sabang.mng.bbs.art.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.mng.bbs.art.service.ArticleVO;

public interface ArticleService {
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 목록조회
	 */
	public List<ArticleVO> selectArticleList(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 갯수조회
	 */
	public int selectArticleTotCnt(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 조회수 증가
	 */
	public void IncrsArticleRdcnt(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 상세조회
	 */
	public ArticleVO selectArticleDetail(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 등록
	 */
	public void insertArticle(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 수정
	 */
	public void updateArticle(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 삭제
	 */
	public void deleteArticle(ArticleVO articleVO) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception 
	 * @description 메인페이지 게시물 조회
	 */
	List<EgovMap> selectMainArticleList(HashMap<String, Object> map) throws Exception;
}
