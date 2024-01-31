package or.sabang.sys.spt.sct.service;

import java.util.List;

public interface SptComptReportService {
	
	/**
	 * @param id
	 * @return
	 * @description 기초조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectBscComptReportFileList(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 기초조사 완료보고서 파일갯수조회
	 */
	public int selectBscComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param fileVO
	 * @return
	 * @description 기초조사 완료보고서 등록
	 */
	public void insertBscComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 기초조사 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectBscComptReportFileDetail(String file_id) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 기초조사 완료보고서 파일삭제
	 */
	public void deleteBscComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 외관점검 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectAprComptReportFileList(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 외관점검 완료보고서 파일갯수조회
	 */
	public int selectAprComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param fileVO
	 * @return
	 * @description 외관점검 완료보고서 등록
	 */
	public void insertAprComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 외관점검 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectAprComptReportFileDetail(String file_id) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 외관점검 완료보고서 파일삭제
	 */
	public void deleteAprComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectLcpComptReportFileList(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일갯수조회
	 */
	public int selectLcpComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param fileVO
	 * @return
	 * @description 땅밀림실태조사 완료보고서 등록
	 */
	public void insertLcpComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectLcpComptReportFileDetail(String file_id) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 땅밀림실태조사 완료보고서 파일삭제
	 */
	public void deleteLcpComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectWkaComptReportFileList(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일갯수조회
	 */
	public int selectWkaComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param fileVO
	 * @return
	 * @description 취약지역 실태조사 완료보고서 등록
	 */
	public void insertWkaComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectWkaComptReportFileDetail(String file_id) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 실태조사 완료보고서 파일삭제
	 */
	public void deleteWkaComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectCnlComptReportFileList(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일갯수조회
	 */
	public int selectCnlComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;
	
	/**
	 * @param fileVO
	 * @return
	 * @description 취약지역 해제조사 완료보고서 등록
	 */
	public void insertCnlComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectCnlComptReportFileDetail(String file_id) throws Exception;
	
	/**
	 * @param file_id
	 * @return
	 * @description 취약지역 해제조사 완료보고서 파일삭제
	 */
	public void deleteCnlComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectEcbComptReportFileList(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일갯수조회
	 */
	public int selectEcbComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param fileVO
	 * @return
	 * @description 사방사업타당성평가 완료보고서 등록
	 */
	public void insertEcbComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectEcbComptReportFileDetail(String file_id) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 사방사업타당성평가 완료보고서 파일삭제
	 */
	public void deleteEcbComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectFrdComptReportFileList(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일갯수조회
	 */
	public int selectFrdComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param fileVO
	 * @return
	 * @description 임도타당성평가 완료보고서 등록
	 */
	public void insertFrdComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectFrdComptReportFileDetail(String file_id) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 임도타당성평가 완료보고서 파일삭제
	 */
	public void deleteFrdComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 계측장비 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectMseComptReportFileList(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 계측장비 완료보고서 파일갯수조회
	 */
	public int selectMseComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param fileVO
	 * @return
	 * @description 계측장비 완료보고서 등록
	 */
	public void insertMseComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 계측장비 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectMseComptReportFileDetail(String file_id) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 계측장비 완료보고서 파일삭제
	 */
	public void deleteMseComptReportFile(SptComptReportFileVO fileVO) throws Exception;
	
	/**
	 * @param id
	 * @return
	 * @description 정밀점검 완료보고서 파일목록조회
	 */
	public List<SptComptReportFileVO> selectPcsComptReportFileList(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param id
	 * @return
	 * @description 정밀점검 완료보고서 파일갯수조회
	 */
	public int selectPcsComptReportFileTotCnt(SptComptReportFileVO searchVO) throws Exception;

	/**
	 * @param fileVO
	 * @return
	 * @description 정밀점검 완료보고서 등록
	 */
	public void insertPcsComptReportFile(SptComptReportFileVO fileVO) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 정밀점검 완료보고서 파일상세조회
	 */
	public SptComptReportFileVO selectPcsComptReportFileDetail(String file_id) throws Exception;

	/**
	 * @param file_id
	 * @return
	 * @description 정밀점검 완료보고서 파일삭제
	 */
	public void deletePcsComptReportFile(SptComptReportFileVO fileVO) throws Exception;
}
