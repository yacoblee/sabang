package or.sabang.mng.bbs.art.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.mng.bbs.art.service.ArticleVO;

@Repository("articleManageDAO")
public class ArticleManageDAO extends EgovComAbstractDAO {
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 목록조회
	 */
	public List<ArticleVO> selectArticleList(ArticleVO articleVO) throws Exception{
		return (List<ArticleVO>) list("articleManageDAO.selectArticleList", articleVO);
	};
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 갯수조회
	 */
	public int selectArticleTotCnt(ArticleVO articleVO) throws Exception{
		return (Integer) selectOne("articleManageDAO.selectArticleTotCnt", articleVO);
	}
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 조회수 증가
	 */
	public void IncrsArticleRdcnt(ArticleVO articleVO) throws Exception {
		update("articleManageDAO.IncrsArticleRdcnt", articleVO);
	}
	
    
    /**
	 * @param articleVO
	 * @return
	 * @description 게시물 상세조회
	 */
    public ArticleVO selectArticleDetail(ArticleVO articleVO) throws Exception {
    	return selectOne("articleManageDAO.selectArticleDetail", articleVO);
    }

    /**
     * @param articleVO
     * @return
     * @description 게시물 등록
     */
    public void insertArticle(ArticleVO articleVO) throws Exception {
    	insert("articleManageDAO.insertArticle", articleVO);
    }
    
    /**
	 * @param articleVO
	 * @return
	 * @description 게시물 수정
	 */
    public void updateArticle(ArticleVO articleVO) throws Exception {
    	update("articleManageDAO.updateArticle", articleVO);
    }
    
    /**
	 * @param articleVO
	 * @return
	 * @description 게시물 삭제
	 */
    public void deleteArticle(ArticleVO articleVO) throws Exception {
    	update("articleManageDAO.deleteArticle", articleVO);
    }
    
    public List<EgovMap> selectMainArticleList(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("articleManageDAO.selectMainArticleList", map);
	};
    
}
