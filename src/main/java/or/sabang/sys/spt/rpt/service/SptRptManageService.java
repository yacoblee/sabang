package or.sabang.sys.spt.rpt.service;

import java.util.List;

import or.sabang.sys.fck.apr.service.FckAprComptVO;

public interface SptRptManageService {
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 보고서 목록조회
	 */
	public List<SptRptReportListVO> selectBscReportList(SptRptReportListVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 기초조사 보고서 갯수조회
	 */
	public int selectBscReportTotCnt(SptRptReportListVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 기초조사 보고서 상세조회
	 */
	public SptRptReportListVO selectBscReportDetail(SptRptBscReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 기초조사 보고서 상세조회 조사완료지 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptBscReportSvyComptVO> selectBscReportSvyComptList(SptRptBscReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 기초조사 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectBscReportSvyComptListTotCnt(SptRptBscReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 보고서 목록조회
	 */
	public List<SptRptReportListVO> selectAprReportList(SptRptReportListVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 보고서 갯수조회
	 */
	public int selectAprReportTotCnt(SptRptReportListVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 외관점검 보고서 상세조회
	 */
	public SptRptReportListVO selectAprReportDetail(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 외관점검 보고서 상세조회 조사완료지 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectAprReportSvyComptList(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 외관점검 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectAprReportSvyComptListTotCnt(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 땅밀림실태조사 보고서 목록조회
	 */
	public List<SptRptReportListVO> selectLcpReportList(SptRptReportListVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 땅밀림실태조사 보고서 갯수조회
	 */
	public int selectLcpReportTotCnt(SptRptReportListVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 보고서 상세조회
	 */
	public SptRptReportListVO selectLcpReportDetail(SptRptLcpReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 땅밀림실태조사 보고서 상세조회 조사완료지 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptLcpReportSvyComptVO> selectLcpReportSvyComptList(SptRptLcpReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 땅밀림실태조사 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectLcpReportSvyComptListTotCnt(SptRptLcpReportSvyComptVO searchVO) throws Exception;
	/**
	 * 기초조사 조사아이디 목록
	 * @param searchVO
	 * @throws Exception
	 */
	List<SptRptAprReportSvyComptVO> selectBscSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 외관점검 조사아이디 목록
	 * @param searchVO
	 * @throws Exception
	 */
	List<SptRptAprReportSvyComptVO> selectAprSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 땅밀림실태조사 조사아이디 목록
	 * @param searchVO
	 * @throws Exception
	 */
	List<SptRptAprReportSvyComptVO> selectLcpSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception;
	/**
	 * 취약지역 실태조사 조사아이디 목록
	 * @param searchVO
	 * @throws Exception
	 */
	List<SptRptAprReportSvyComptVO> selectWkaSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception;
	/**
	 * 취약지역 해제조사 조사아이디 목록
	 * @param searchVO
	 * @throws Exception
	 */
	List<SptRptAprReportSvyComptVO> selectCnlSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 보고서 목록조회
	 */
	public List<SptRptWkaReportSvyComptVO> selectWkaReportSvyComptList(SptRptWkaReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 실태조사 보고서 갯수조회
	 */
	public int selectWkaReportSvyComptListTotCnt(SptRptWkaReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 보고서 목록조회
	 */
	public List<SptRptCnlReportSvyComptVO> selectCnlReportSvyComptList(SptRptCnlReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @description 취약지역 해제조사 보고서 갯수조회
	 */
	public int selectCnlReportSvyComptListTotCnt(SptRptCnlReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 정밀점검 보고서 상세조회 조사완료지 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptPcsReportSvyComptVO> selectPcsReportSvyComptList(SptRptPcsReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 정밀점검 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectPcsReportSvyComptListTotCnt(SptRptPcsReportSvyComptVO searchVO) throws Exception;
}
