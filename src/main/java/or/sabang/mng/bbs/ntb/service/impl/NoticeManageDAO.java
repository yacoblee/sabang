package or.sabang.mng.bbs.ntb.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.mng.bbs.ntb.service.NoticeVO;

@Repository("noticeManageDAO")
public class NoticeManageDAO extends EgovComAbstractDAO {
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 목록조회
	 */
	public List<NoticeVO> selectNoticeList(NoticeVO noticeVO) throws Exception{
		return (List<NoticeVO>) list("noticeManageDAO.selectNoticeList", noticeVO);
	};
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 갯수조회
	 */
	public int selectNoticeTotCnt(NoticeVO noticeVO) throws Exception{
		return (Integer) selectOne("noticeManageDAO.selectNoticeTotCnt", noticeVO);
	}
	
    /**
  	 * @param noticeVO
  	 * @return
  	 * @description 공지사항 조회수 증가
  	 */
    public void IncrsNoticeRdcnt(NoticeVO noticeVO) throws Exception {
    	update("noticeManageDAO.IncrsNoticeRdcnt", noticeVO);
    }	
      
    /**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 상세조회
	 */
    public NoticeVO selectNoticeDetail(NoticeVO noticeVO) throws Exception {
    	return selectOne("noticeManageDAO.selectNoticeDetail", noticeVO);
    }
    
    /**
     * @param noticeVO
     * @return
     * @description 공지사항 등록
     */
    public void insertNotice(NoticeVO noticeVO) throws Exception {
    	insert("noticeManageDAO.insertNotice", noticeVO);
    }	

    /**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 수정
	 */
    public void updateNotice(NoticeVO noticeVO) throws Exception {
    	update("noticeManageDAO.updateNotice", noticeVO);
    }
    
    /**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 삭제
	 */
    public void deleteNotice(NoticeVO noticeVO) throws Exception {
    	update("noticeManageDAO.deleteNotice", noticeVO);
    }
    
    /**
	 * @param map
	 * @return
	 * @description 메인페이지 공지사항 조회
	 */
    public List<EgovMap> selectMainNoticeList(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("noticeManageDAO.selectMainNoticeList", map);
	};
}
