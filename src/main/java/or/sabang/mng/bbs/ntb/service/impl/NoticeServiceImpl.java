package or.sabang.mng.bbs.ntb.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.mng.bbs.ntb.service.NoticeService;
import or.sabang.mng.bbs.ntb.service.NoticeVO;

@Service("noticeService")
public class NoticeServiceImpl extends EgovAbstractServiceImpl implements NoticeService {
	
	@Resource(name = "noticeManageDAO")
    private NoticeManageDAO noticeManageDAO;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 목록조회
	 */
	@Override
	public List<NoticeVO> selectNoticeList(NoticeVO noticeVO) throws Exception {
		return noticeManageDAO.selectNoticeList(noticeVO);
	}

	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 갯수조회
	 */
	@Override
	public int selectNoticeTotCnt(NoticeVO noticeVO) throws Exception {
		return noticeManageDAO.selectNoticeTotCnt(noticeVO);
	}
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 조회수 증가
	 */
	public void IncrsNoticeRdcnt(NoticeVO noticeVO) throws Exception {
		noticeManageDAO.IncrsNoticeRdcnt(noticeVO);
	}
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 상세조회
	 */
	public NoticeVO selectNoticeDetail(NoticeVO noticeVO) throws Exception {
		return noticeManageDAO.selectNoticeDetail(noticeVO);
    }

	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 등록
	 */
	public void insertNotice(NoticeVO noticeVO) throws Exception {
		noticeManageDAO.insertNotice(noticeVO);
    }
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 수정
	 */
	public void updateNotice(NoticeVO noticeVO) throws Exception {
		noticeManageDAO.updateNotice(noticeVO);
    }
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 삭제
	 */
	public void deleteNotice(NoticeVO noticeVO) throws Exception {
		noticeManageDAO.deleteNotice(noticeVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 메인페이지 공지사항 조회
	 */
	public List<EgovMap> selectMainNoticeList(HashMap<String, Object> map) throws Exception {
		return noticeManageDAO.selectMainNoticeList(map);
	}
}
