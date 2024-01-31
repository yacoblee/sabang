package or.sabang.sys.spt.rpt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.spt.rpt.service.SptRptReportListVO;
import or.sabang.sys.spt.rpt.service.SptRptWkaReportSvyComptVO;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptBscReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptCnlReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptLcpReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptPcsReportSvyComptVO;

@Repository("sptRptManageDAO")
public class SptRptManageDAO extends EgovComAbstractDAO {

	/**
	 * 기초조사 보고서 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SptRptReportListVO> selectBscReportList(SptRptReportListVO searchVO) throws Exception{
		return (List<SptRptReportListVO>) list("sptRptManageDAO.selectBscReportList", searchVO);
	};
	
	/**
	 * 기초조사 보고서 갯수조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectBscReportTotCnt(SptRptReportListVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectBscReportTotCnt", searchVO);
	}
	
	/**
	 * 기초조사 보고서 상세조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public SptRptReportListVO selectBscReportDetail(SptRptBscReportSvyComptVO searchVO) throws Exception{
		return (SptRptReportListVO) selectOne("sptRptManageDAO.selectBscReportDetail", searchVO);
	}
	
	/**
	 * 기초조사 보고서 상세조회 조사완료 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptBscReportSvyComptVO> selectBscReportSvyComptList(SptRptBscReportSvyComptVO searchVO) throws Exception{
		return (List<SptRptBscReportSvyComptVO>) list("sptRptManageDAO.selectBscReportSvyComptList",searchVO);
	}
	
	/**
	 * 기초조사 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectBscReportSvyComptListTotCnt(SptRptBscReportSvyComptVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectBscReportSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 외관점검 보고서 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SptRptReportListVO> selectAprReportList(SptRptReportListVO searchVO) throws Exception{
		return (List<SptRptReportListVO>) list("sptRptManageDAO.selectAprReportList", searchVO);
	};
	
	/**
	 * 외관점검 보고서 갯수조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectAprReportTotCnt(SptRptReportListVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectAprReportTotCnt", searchVO);
	}
	
	/**
	 * 외관점검 보고서 상세조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public SptRptReportListVO selectAprReportDetail(SptRptAprReportSvyComptVO searchVO) throws Exception{
		return (SptRptReportListVO) selectOne("sptRptManageDAO.selectAprReportDetail", searchVO);
	}
	
	/**
	 * 외관점검 보고서 상세조회 조사완료 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectAprReportSvyComptList(SptRptAprReportSvyComptVO searchVO) throws Exception{
		return (List<SptRptAprReportSvyComptVO>) list("sptRptManageDAO.selectAprReportSvyComptList",searchVO);
	}
	
	/**
	 * 외관점검 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectAprReportSvyComptListTotCnt(SptRptAprReportSvyComptVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectAprReportSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SptRptReportListVO> selectLcpReportList(SptRptReportListVO searchVO) throws Exception{
		return (List<SptRptReportListVO>) list("sptRptManageDAO.selectLcpReportList", searchVO);
	};
	
	/**
	 * 땅밀림실태조사 보고서 갯수조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectLcpReportTotCnt(SptRptReportListVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectLcpReportTotCnt", searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 상세조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public SptRptReportListVO selectLcpReportDetail(SptRptLcpReportSvyComptVO searchVO) throws Exception{
		return (SptRptReportListVO) selectOne("sptRptManageDAO.selectLcpReportDetail", searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 상세조회 조사완료 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptLcpReportSvyComptVO> selectLcpReportSvyComptList(SptRptLcpReportSvyComptVO searchVO) throws Exception{
		return (List<SptRptLcpReportSvyComptVO>) list("sptRptManageDAO.selectLcpReportSvyComptList",searchVO);
	}
	
	/**
	 * 땅밀림실태조사 보고서 상세조회 조사완료지 총 갯수
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectLcpReportSvyComptListTotCnt(SptRptLcpReportSvyComptVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectLcpReportSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 기초조사 조사아이디 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectBscSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return (List<SptRptAprReportSvyComptVO>) list("sptRptManageDAO.selectBscSvyIdList", searchVO);
	}
	
	/**
	 * 외관점검 조사아이디 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectAprSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return (List<SptRptAprReportSvyComptVO>) list("sptRptManageDAO.selectAprSvyIdList", searchVO);
	}
	
	/**
	 * 땅밀림실태조사 조사아이디 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectLcpSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return (List<SptRptAprReportSvyComptVO>) list("sptRptManageDAO.selectLcpSvyIdList", searchVO);
	}
	
	/**
	 * 취약지역 실태조사 조사아이디 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectWkaSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return (List<SptRptAprReportSvyComptVO>) list("sptRptManageDAO.selectWkaSvyIdList", searchVO);
	}
	
	/**
	 * 취약지역 해제조사 조사아이디 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<SptRptAprReportSvyComptVO> selectCnlSvyIdList(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return (List<SptRptAprReportSvyComptVO>) list("sptRptManageDAO.selectCnlSvyIdList", searchVO);
	}
	
	/**
	 * 취약지역 실태조사 보고서 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SptRptWkaReportSvyComptVO> selectWkaReportSvyComptList(SptRptWkaReportSvyComptVO searchVO) throws Exception{
		return (List<SptRptWkaReportSvyComptVO>) list("sptRptManageDAO.selectWkaReportSvyComptList", searchVO);
	};
	
	/**
	 * 취약지역 실태조사 보고서 갯수조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectWkaReportSvyComptListTotCnt(SptRptWkaReportSvyComptVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectWkaReportSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 취약지역 해제조사 보고서 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SptRptCnlReportSvyComptVO> selectCnlReportSvyComptList(SptRptCnlReportSvyComptVO searchVO) throws Exception{
		return (List<SptRptCnlReportSvyComptVO>) list("sptRptManageDAO.selectCnlReportSvyComptList", searchVO);
	};
	
	/**
	 * 취약지역 해제조사 보고서 갯수조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectCnlReportSvyComptListTotCnt(SptRptCnlReportSvyComptVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectCnlReportSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 취약지역 해제조사 보고서 목록조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SptRptPcsReportSvyComptVO> selectPcsReportSvyComptList(SptRptPcsReportSvyComptVO searchVO) throws Exception{
		return (List<SptRptPcsReportSvyComptVO>) list("sptRptManageDAO.selectPcsReportSvyComptList", searchVO);
	};
	
	/**
	 * 취약지역 해제조사 보고서 갯수조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectPcsReportSvyComptListTotCnt(SptRptPcsReportSvyComptVO searchVO) throws Exception{
		return (Integer) selectOne("sptRptManageDAO.selectPcsReportSvyComptListTotCnt", searchVO);
	}
}
