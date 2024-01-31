package or.sabang.sys.spt.rpt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import or.sabang.sys.spt.rpt.service.SptRptManageService;
import or.sabang.sys.spt.rpt.service.SptRptPcsReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptReportListVO;
import or.sabang.sys.spt.rpt.service.SptRptWkaReportSvyComptVO;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptBscReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptCnlReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptLcpReportSvyComptVO;

@Service("sptRptManageService")
public class SptRptManageServiceImpl extends EgovAbstractServiceImpl implements SptRptManageService {

	@Resource(name="sptRptManageDAO")
	private SptRptManageDAO sptRptManageDAO;

	/**
	 * 기초조사 보고서 목록조회
	 */
	@Override
	public List<SptRptReportListVO> selectBscReportList(SptRptReportListVO searchVO) throws Exception {		
		return sptRptManageDAO.selectBscReportList(searchVO);
	}

	/**
	 * 기초조사 보고서 갯수조회
	 */
	@Override
	public int selectBscReportTotCnt(SptRptReportListVO searchVO) throws Exception {
		return sptRptManageDAO.selectBscReportTotCnt(searchVO);
	}

	/**
	 * 기초조사 보고서 상세조회
	 */
	@Override
	public SptRptReportListVO selectBscReportDetail(SptRptBscReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectBscReportDetail(searchVO);
	}
	
	/**
	 * 기초조사 보고서 상세조회 조사완료 목록
	 */
	@Override
	public List<SptRptBscReportSvyComptVO> selectBscReportSvyComptList(SptRptBscReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectBscReportSvyComptList(searchVO);
	}
	
	/**
	 * 기초조사 보고서 상세조회 조사완료지 총 갯수
	 */
	public int selectBscReportSvyComptListTotCnt(SptRptBscReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectBscReportSvyComptListTotCnt(searchVO);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 외관점검 보고서 목록조회
	 */
	@Override
	public List<SptRptReportListVO> selectAprReportList(SptRptReportListVO searchVO) throws Exception {		
		return sptRptManageDAO.selectAprReportList(searchVO);
	}

	/**
	 * 외관점검 보고서 갯수조회
	 */
	@Override
	public int selectAprReportTotCnt(SptRptReportListVO searchVO) throws Exception {
		return sptRptManageDAO.selectAprReportTotCnt(searchVO);
	}

	/**
	 * 외관점검 보고서 상세조회
	 */
	@Override
	public SptRptReportListVO selectAprReportDetail(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectAprReportDetail(searchVO);
	}
	
	/**
	 * 외관점검 보고서 상세조회 조사완료 목록
	 */
	@Override
	public List<SptRptAprReportSvyComptVO> selectAprReportSvyComptList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectAprReportSvyComptList(searchVO);
	}
	
	/**
	 * 외관점검 보고서 상세조회 조사완료지 총 갯수
	 */
	public int selectAprReportSvyComptListTotCnt(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectAprReportSvyComptListTotCnt(searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 목록조회
	 */
	@Override
	public List<SptRptReportListVO> selectLcpReportList(SptRptReportListVO searchVO) throws Exception {		
		return sptRptManageDAO.selectLcpReportList(searchVO);
	}

	/**
	 * 땅밀림실태조사 보고서 갯수조회
	 */
	@Override
	public int selectLcpReportTotCnt(SptRptReportListVO searchVO) throws Exception {
		return sptRptManageDAO.selectLcpReportTotCnt(searchVO);
	}

	/**
	 * 땅밀림실태조사 보고서 상세조회
	 */
	@Override
	public SptRptReportListVO selectLcpReportDetail(SptRptLcpReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectLcpReportDetail(searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 상세조회 조사완료 목록
	 */
	@Override
	public List<SptRptLcpReportSvyComptVO> selectLcpReportSvyComptList(SptRptLcpReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectLcpReportSvyComptList(searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 상세조회 조사완료지 총 갯수
	 */
	public int selectLcpReportSvyComptListTotCnt(SptRptLcpReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectLcpReportSvyComptListTotCnt(searchVO);
	}
	
	/**
	 * 기초조사 조사아이디 목록
	 */
	@Override
	public List<SptRptAprReportSvyComptVO> selectBscSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectBscSvyIdList(searchVO);
	}
	
	/**
	 * 외관점검 조사아이디 목록
	 */
	@Override
	public List<SptRptAprReportSvyComptVO> selectAprSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectAprSvyIdList(searchVO);
	}
	
	/**
	 * 땅밀림실태조사 조사아이디 목록
	 */
	@Override
	public List<SptRptAprReportSvyComptVO> selectLcpSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectLcpSvyIdList(searchVO);
	}
	
	/**
	 * 취약지역 실태조사 조사아이디 목록
	 */
	@Override
	public List<SptRptAprReportSvyComptVO> selectWkaSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectWkaSvyIdList(searchVO);
	}
	/**
	 * 취약지역 해제조사 조사아이디 목록
	 */
	@Override
	public List<SptRptAprReportSvyComptVO> selectCnlSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectCnlSvyIdList(searchVO);
	}
	
	/**
	 * 취약지역 실태조사 상세조회 조사완료 목록
	 */
	@Override
	public List<SptRptWkaReportSvyComptVO> selectWkaReportSvyComptList(SptRptWkaReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectWkaReportSvyComptList(searchVO);
	}
	
	/**
	 * 취약지역 실태조사 상세조회 조사완료지 총 갯수
	 */
	@Override
	public int selectWkaReportSvyComptListTotCnt(SptRptWkaReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectWkaReportSvyComptListTotCnt(searchVO);
	}
	
	/**
	 * 취약지역 해제조사 상세조회 조사완료 목록
	 */
	@Override
	public List<SptRptCnlReportSvyComptVO> selectCnlReportSvyComptList(SptRptCnlReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectCnlReportSvyComptList(searchVO);
	}
	
	/**
	 * 취약지역 해제조사 상세조회 조사완료지 총 갯수
	 */
	@Override
	public int selectCnlReportSvyComptListTotCnt(SptRptCnlReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectCnlReportSvyComptListTotCnt(searchVO);
	}
	
	/**
	 * 정밀점검 상세조회 조사완료 목록
	 */
	@Override
	public List<SptRptPcsReportSvyComptVO> selectPcsReportSvyComptList(SptRptPcsReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectPcsReportSvyComptList(searchVO);
	}
	
	/**
	 * 정밀점검 상세조회 조사완료지 총 갯수
	 */
	@Override
	public int selectPcsReportSvyComptListTotCnt(SptRptPcsReportSvyComptVO searchVO) throws Exception {
		return sptRptManageDAO.selectPcsReportSvyComptListTotCnt(searchVO);
	}
}
