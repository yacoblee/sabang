package or.sabang.sys.lss.bsc.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

public interface LssBscSvyComptService {
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectBscSvyComptListTotCnt(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<LssBscSvyComptVO> selectBscSvyComptList(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	LssBscSvyComptVO selectBscSvyComptDetail(LssBscSvyComptVO svyComptVO) throws Exception;
		
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateBscSvyCompt(LssBscSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void deleteBscSvyCompt(LssBscSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지를 엑셀다운로드 한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public Map<?, ?> selectBscSvyComptListExcel(LssBscSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectBscSvyComptMaxYear() throws Exception;
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectBscSvyComptMaxMonth() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectBscSvyComptYear() throws Exception;
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @throws Exception
	 */
	JSONObject updateBscSvyComptExcel(LssBscSvyComptVO svyComptVO,MultipartFile mFile) throws Exception;

	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectBscSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception;
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception;
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectBscLocReCreateSvyId(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateBscSvyComptPhotoList(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	public EgovMap selectSvyPhotoNullList(LssBscSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssBscSvyComptVO map) throws Exception;
}