package or.sabang.sys.spt.sct.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.spt.rpt.service.SptRptReportListVO;
import or.sabang.sys.spt.sct.service.SptComptReportFileVO;

@Repository("sptComptReportDAO")
public class SptComptReportDAO extends EgovComAbstractDAO {

	/**
	 * @param id
	 * @return
	 * @description 기초조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectBscComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectBscComptReportFileList", searchVO);
	};
	
	/**
	 * @param id
	 * @return
	 * @description 기초조사 완료보고서 파일갯수조회
	 */
	public int selectBscComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectBscComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 기초조사 완료보고서 등록
	 */
	public void insertBscComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertBscComptReportFile", fileVO);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 보고서 파일상세조회
	 */
	public SptComptReportFileVO selectBscComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectBscComptReportFileDetail", file_id);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 기초조사 완료보고서 파일삭제
	 */
	public void deleteBscComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteBscComptReportFile", fileVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 외관점검 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectAprComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectAprComptReportFileList", searchVO);
	};

	/**
	 * @param id
	 * @return
	 * @description 외관점검 완료보고서 파일갯수조회
	 */
	public int selectAprComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectAprComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 외관점검 완료보고서 등록
	 */
	public void insertAprComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertAprComptReportFile", fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 외관점검 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectAprComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectAprComptReportFileDetail", file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 외관점검 완료보고서 파일삭제
	 */
	public void deleteAprComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteAprComptReportFile", fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectLcpComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectLcpComptReportFileList", searchVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일갯수조회
	 */
	public int selectLcpComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectLcpComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 땅밀림실태조사 완료보고서 등록
	 */
	public void insertLcpComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertLcpComptReportFile", fileVO);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 땅밀림실태조사 보고서 파일상세조회
	 */
	public SptComptReportFileVO selectLcpComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectLcpComptReportFileDetail", file_id);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일삭제
	 */
	public void deleteLcpComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteLcpComptReportFile", fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectWkaComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectWkaComptReportFileList", searchVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일갯수조회
	 */
	public int selectWkaComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectWkaComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 취약지역 실태조사 완료보고서 등록
	 */
	public void insertWkaComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertWkaComptReportFile", fileVO);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 실태조사 보고서 파일상세조회
	 */
	public SptComptReportFileVO selectWkaComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectWkaComptReportFileDetail", file_id);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일삭제
	 */
	public void deleteWkaComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteWkaComptReportFile", fileVO);
	}
	
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectCnlComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectCnlComptReportFileList", searchVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일갯수조회
	 */
	public int selectCnlComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectCnlComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 취약지역 해제조사 완료보고서 등록
	 */
	public void insertCnlComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertCnlComptReportFile", fileVO);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 해제조사 보고서 파일상세조회
	 */
	public SptComptReportFileVO selectCnlComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectCnlComptReportFileDetail", file_id);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일삭제
	 */
	public void deleteCnlComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteCnlComptReportFile", fileVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectEcbComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectEcbComptReportFileList", searchVO);
	};

	/**
	 * @param id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일갯수조회
	 */
	public int selectEcbComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectEcbComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 사방사업타당성평가 완료보고서 등록
	 */
	public void insertEcbComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertEcbComptReportFile", fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectEcbComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectEcbComptReportFileDetail", file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일삭제
	 */
	public void deleteEcbComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteEcbComptReportFile", fileVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 임도타당성평가 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectFrdComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectFrdComptReportFileList", searchVO);
	};

	/**
	 * @param id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일갯수조회
	 */
	public int selectFrdComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectFrdComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 임도타당성평가 완료보고서 등록
	 */
	public void insertFrdComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertFrdComptReportFile", fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectFrdComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectFrdComptReportFileDetail", file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일삭제
	 */
	public void deleteFrdComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteFrdComptReportFile", fileVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 계측장비 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectMseComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectMseComptReportFileList", searchVO);
	};

	/**
	 * @param id
	 * @return
	 * @description 계측장비 완료보고서 파일갯수조회
	 */
	public int selectMseComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectMseComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 계측장비 완료보고서 등록
	 */
	public void insertMseComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertMseComptReportFile", fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 계측장비 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectMseComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectMseComptReportFileDetail", file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 계측장비 완료보고서 파일삭제
	 */
	public void deleteMseComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deleteMseComptReportFile", fileVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @description 정밀점검 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectPcsComptReportFileList(SptComptReportFileVO searchVO) throws Exception{
		return (List<SptComptReportFileVO>) list("sptComptReportDAO.selectPcsComptReportFileList", searchVO);
	};

	/**
	 * @param id
	 * @return
	 * @description 정밀점검 완료보고서 파일갯수조회
	 */
	public int selectPcsComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return (Integer) selectOne("sptComptReportDAO.selectPcsComptReportFileTotCnt", searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 정밀점검 완료보고서 등록
	 */
	public void insertPcsComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.insertPcsComptReportFile", fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 정밀점검 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectPcsComptReportFileDetail(String file_id) throws Exception{
		return (SptComptReportFileVO) selectOne("sptComptReportDAO.selectPcsComptReportFileDetail", file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 정밀점검 완료보고서 파일삭제
	 */
	public void deletePcsComptReportFile(SptComptReportFileVO fileVO) throws Exception{
		insert("sptComptReportDAO.deletePcsComptReportFile", fileVO);
	}
}
