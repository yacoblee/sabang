package or.sabang.mng.bbs.ntb.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface NoticeService {
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 목록조회
	 */
	public List<NoticeVO> selectNoticeList(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 갯수조회
	 */
	public int selectNoticeTotCnt(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 조회수 증가
	 */
	public void IncrsNoticeRdcnt(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 상세조회
	 */
	public NoticeVO selectNoticeDetail(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 등록
	 */
	public void insertNotice(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 수정
	 */
	public void updateNotice(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param noticeVO
	 * @return
	 * @description 공지사항 삭제
	 */
	public void deleteNotice(NoticeVO noticeVO) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 메인페이지 공지사항 조회
	 */
	List<EgovMap> selectMainNoticeList(HashMap<String, Object> map) throws Exception;
}
