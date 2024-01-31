package or.sabang.sys.spt.sct.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import or.sabang.sys.spt.rpt.service.SptRptReportListVO;
import or.sabang.sys.spt.sct.service.SptComptReportFileVO;
import or.sabang.sys.spt.sct.service.SptComptReportService;

@Service("sptComptReportService")
public class SptComptReportServiceImpl  extends EgovAbstractServiceImpl implements  SptComptReportService  {
	
	@Resource(name="sptComptReportDAO")
	private SptComptReportDAO sptComptReportDAO;
	
	/**
	 * @param id
	 * @return
	 * @description 기초조사 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectBscComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectBscComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 기초조사 완료보고서 파일갯수조회
	 */
	@Override
	public int selectBscComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectBscComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 기초조사 완료보고서 등록
	 */
	@Override
	public void insertBscComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertBscComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 기초조사 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectBscComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectBscComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 기초조사 완료보고서 파일삭제
	 */
	@Override
	public void deleteBscComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteBscComptReportFile(fileVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 외관점검 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectAprComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectAprComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 외관점검 완료보고서 파일갯수조회
	 */
	@Override
	public int selectAprComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectAprComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 외관점검 완료보고서 등록
	 */
	@Override
	public void insertAprComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertAprComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 외관점검 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectAprComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectAprComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 외관점검 완료보고서 파일삭제
	 */
	@Override
	public void deleteAprComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteAprComptReportFile(fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectLcpComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectLcpComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일갯수조회
	 */
	@Override
	public int selectLcpComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectLcpComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 땅밀림실태조사 완료보고서 등록
	 */
	@Override
	public void insertLcpComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertLcpComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectLcpComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectLcpComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일삭제
	 */
	@Override
	public void deleteLcpComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteLcpComptReportFile(fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectWkaComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectWkaComptReportFileList(searchVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일갯수조회
	 */
	@Override
	public int selectWkaComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectWkaComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 취약지역 실태조사 완료보고서 등록
	 */
	@Override
	public void insertWkaComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertWkaComptReportFile(fileVO);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectWkaComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectWkaComptReportFileDetail(file_id);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일삭제
	 */
	@Override
	public void deleteWkaComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteWkaComptReportFile(fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectCnlComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectCnlComptReportFileList(searchVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일갯수조회
	 */
	@Override
	public int selectCnlComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectCnlComptReportFileTotCnt(searchVO);
	}
	
	/**
	 * @param fileVO
	 * @return
	 * @description 취약지역 해제조사 완료보고서 등록
	 */
	@Override
	public void insertCnlComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertCnlComptReportFile(fileVO);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectCnlComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectCnlComptReportFileDetail(file_id);
	}
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일삭제
	 */
	@Override
	public void deleteCnlComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteCnlComptReportFile(fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectEcbComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectEcbComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일갯수조회
	 */
	@Override
	public int selectEcbComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectEcbComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 사방사업타당성평가 완료보고서 등록
	 */
	@Override
	public void insertEcbComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertEcbComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectEcbComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectEcbComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일삭제
	 */
	@Override
	public void deleteEcbComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteEcbComptReportFile(fileVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectFrdComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectFrdComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일갯수조회
	 */
	@Override
	public int selectFrdComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectFrdComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 임도타당성평가 완료보고서 등록
	 */
	@Override
	public void insertFrdComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertFrdComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 임도타당성평가 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectFrdComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectFrdComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일삭제
	 */
	@Override
	public void deleteFrdComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteFrdComptReportFile(fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 계측장비 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectMseComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectMseComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 계측장비 완료보고서 파일갯수조회
	 */
	@Override
	public int selectMseComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectMseComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 계측장비 완료보고서 등록
	 */
	@Override
	public void insertMseComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertMseComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 계측장비 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectMseComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectMseComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 계측장비 완료보고서 파일삭제
	 */
	@Override
	public void deleteMseComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deleteMseComptReportFile(fileVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 정밀점검 완료보고서 파일목록조회
	 */
	@Override
	public List<SptComptReportFileVO> selectPcsComptReportFileList(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectPcsComptReportFileList(searchVO);
	}

	/**
	 * @param id
	 * @return
	 * @description 정밀점검 완료보고서 파일갯수조회
	 */
	@Override
	public int selectPcsComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception {
		return sptComptReportDAO.selectPcsComptReportFileTotCnt(searchVO);
	}

	/**
	 * @param fileVO
	 * @return
	 * @description 정밀점검 완료보고서 등록
	 */
	@Override
	public void insertPcsComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.insertPcsComptReportFile(fileVO);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 정밀점검 완료보고서 상세조회
	 */
	@Override
	public SptComptReportFileVO selectPcsComptReportFileDetail(String file_id) throws Exception {
		return sptComptReportDAO.selectPcsComptReportFileDetail(file_id);
	}

	/**
	 * @param file_id
	 * @return
	 * @description 정밀점검 완료보고서 파일삭제
	 */
	@Override
	public void deletePcsComptReportFile(SptComptReportFileVO fileVO) throws Exception {
		sptComptReportDAO.deletePcsComptReportFile(fileVO);
	}
}
