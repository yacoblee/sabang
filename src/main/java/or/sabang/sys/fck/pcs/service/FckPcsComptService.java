package or.sabang.sys.fck.pcs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptPcsReportSvyComptVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

public interface FckPcsComptService {
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectPcsSvyComptListTotCnt(FckPcsComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<FckPcsComptVO> selectPcsSvyComptList(FckPcsComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectPcsSvyComptMaxYear() throws Exception;
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectPcsSvyComptMaxMonth() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectPcsSvyComptYear() throws Exception;
	 
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception;

	/**
	 * 대상지 조사 기간 별 위치도 재생성
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 12.
	 * @modified
	 */
	List<?> updateLocReCreate(LocReCreateVO searchVO) throws Exception;

	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 13.
	 * @modified
	 */
	String selectAuthorCheck(HashMap<String, Object> commandMap) throws Exception;

	/**
	 * 대상지 상세조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 13.
	 * @modified
	 */
	FckPcsComptVO selectFckPcsComptDetail(FckPcsComptVO searchVO) throws Exception;
	
	/**
	 * 사진목록 조회
	 * @throws Exception
	 */
	public FckPcsComptVO selectDownloadPhotoList(FckPcsComptVO searchVO) throws Exception;

	/**
	 * 조사완료지를 수정한다.
	 * @throws Exception
	 */
	void updateFckPcsCompt(FckPcsComptVO comptVO) throws Exception;

	/**
	 * 조사완료지 삭제
	 * @author DEVWORK
	 * @param comptVO
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 */
	void deleteFckPcsCompt(FckPcsComptVO comptVO) throws Exception;

	/**
	 * 조사완료지 엑셀 다운로드
	 * @author DEVWORK
	 * @param comptVo
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 */
	Map<?, ?> selectFckPcsSvyComptListExcel(FckPcsComptVO comptVo) throws Exception;
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectPcsComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception;
	
	/**
	 * 조사완료 첨부파일 업로드
	 * @author shcho
	 * @param fileMap
	 * @return
	 * @since 2024. 01. 02.
	 * @modified
	 */
	public void insertPcsComptFile(HashMap<String, String> fileMap) throws Exception;
	
	/**
	 * 조사완료 첨부파일 조회
	 * @author shcho
	 * @param fileMap
	 * @return
	 * @since 2024. 01. 02.
	 * @modified
	 */
	public List<EgovMap> selectPcsComptFile(String gid) throws Exception;
	
	/**
	 * 조사완료 첨부파일 삭제
	 * @author shcho
	 * @param sn
	 * @return
	 * @since 2024. 01. 08.
	 * @modified
	 */
	public void deletePcsComptFile(String sn) throws Exception;
}
