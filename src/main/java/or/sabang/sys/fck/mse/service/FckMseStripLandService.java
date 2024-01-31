package or.sabang.sys.fck.mse.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface FckMseStripLandService {
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectFckMseSldTotCnt(FckMseStripLandVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<?> selectFckMseSldList(FckMseStripLandVO searchVO) throws Exception;
	
	/**
	 * 대상지 정보를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	FckMseStripLandVO selectMseSldDetail(FckMseStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 대상지 통신모뎀번호 및 이동전화번호 목록을 조회한다.
	 * @param stripLandVO
	 * @return 
	 * @throws Exception
	 */
	List<?> selectMseSldModemCellNumList(FckMseStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 대상지 지중경사계 목록을 조회한다.
	 * @param stripLandVO
	 * @return 
	 * @throws Exception
	 */
	List<?> selectMseSldLicMeterList(FckMseStripLandVO stripLandVO) throws Exception;

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 대상지등록
	 */
	void insertMseSld(FckMseStripLandVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description pnu_code 조회 
	 */
	public List<EgovMap> selectPnuCode(FckMseStripLandVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 대상지수정
	 */
	void updateMseSld(FckMseStripLandVO stripLandVO) throws Exception;
	
	/**
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 * @description 대상지삭제
	 */
	void deleteMseSld(FckMseStripLandVO stripLandVO) throws Exception;
	
}