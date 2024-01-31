package or.sabang.mng.bbs.art.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.mng.bbs.art.service.ArticleService;
import or.sabang.mng.bbs.art.service.ArticleVO;

@Service("articleService")
public class ArticleServiceImpl extends EgovAbstractServiceImpl implements ArticleService {
	
	@Resource(name = "articleManageDAO")
    private ArticleManageDAO articleManageDAO;
//	@Resource(name = "articleDAO")
//	private ArticleDAO articleDAO;
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 목록조회
	 */
	@Override
	public List<ArticleVO> selectArticleList(ArticleVO articleVO) throws Exception {
		return articleManageDAO.selectArticleList(articleVO);
	}

	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 갯수조회
	 */
	@Override
	public int selectArticleTotCnt(ArticleVO articleVO) throws Exception {
		return articleManageDAO.selectArticleTotCnt(articleVO);
	}
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 조회수 증가
	 */
	public void IncrsArticleRdcnt(ArticleVO articleVO) throws Exception {
		articleManageDAO.IncrsArticleRdcnt(articleVO);
	}
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 상세조회
	 */
	public ArticleVO selectArticleDetail(ArticleVO articleVO) throws Exception {
		return articleManageDAO.selectArticleDetail(articleVO);
    }
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 등록
	 */
	public void insertArticle(ArticleVO articleVO) throws Exception {
		articleManageDAO.insertArticle(articleVO);
    }
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 수정
	 */
	public void updateArticle(ArticleVO articleVO) throws Exception {
		articleManageDAO.updateArticle(articleVO);
    }
	
	/**
	 * @param articleVO
	 * @return
	 * @description 게시물 삭제
	 */
	public void deleteArticle(ArticleVO articleVO) throws Exception {
		articleManageDAO.deleteArticle(articleVO);
	}
	
	
	public List<EgovMap> selectMainArticleList(HashMap<String, Object> map) throws Exception {
		return articleManageDAO.selectMainArticleList(map);
	}
	
	

}
