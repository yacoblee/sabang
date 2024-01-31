package or.sabang.sys.vyt.frd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;

public interface VytFrdSvyComptService {
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectFrdSvyComptListTotCnt(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<VytFrdSvyComptVO> selectFrdSvyComptList(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFrdSvyComptMaxYear() throws Exception;
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFrdSvyComptMaxMonth() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFrdSvyComptYear() throws Exception;
	
	/**
	 * 대상지관리 고유번호(smid)를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectSldNumber(HashMap<String, String> numberMap) throws Exception;
	
	/**
	 * 대상지전자야장연계 고유번호(gid)를 조회한다.
	 * @throws Exception
	 */
	public String selectFieldBookNumber(HashMap<String, String> numberMap) throws Exception;
	
	/**
	 * 조사완료 상세조회
	 * @throws Exception
	 */
	public VytFrdSvyComptVO selectFrdSvyComptDetail(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectVytFrdProjDMS(HashMap<String, Object> map) throws Exception;
	
	
	/**
	 * 타당성평가라인 중심 좌표를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectVytFrdLineCntPnt(HashMap<String, String> numberMap) throws Exception;
	
	/**
	 * 추가SHP파일 등록
	 * @throws Exception
	 */
	public void updateExtraShp(HashMap<String, Object> commandMap) throws Exception;
	
	/**
	 * 대상지를 수정한다.
	 * @param svyComptVO
	 * @throws Exception
	 */
	void updateFrdSvyCompt(VytFrdSvyComptVO svyComptVO) throws Exception;
	/**
	 * 기 분석되어 저장된 분석 유형에 맞는 버퍼 사이즈를 조회한다. 
	 * @throws Exception
	 */
	public List<EgovMap> selectBufferSize(HashMap<String, Object> commandMap) throws Exception;
	
	/**
	 * 분석결과 파일 전체 다운로드
	 * @param searchVO
	 * @implNote 수정노선이 있는경우 수정 노선에 대해서만 다운로드
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO downloadAnalAll(VytFrdSvyComptVO searchVO) throws Exception;
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteFrdSvyCompt(VytFrdSvyComptVO svyComptVO) throws Exception;
	public void deleteFrdAnalFile(VytFrdSvyComptVO svyComptVO) throws Exception;
	public void deleteFrdLines(VytFrdSvyComptVO svyComptVO) throws Exception;
	
	/**
	 * 사진목록 조회
	 * @throws Exception
	 */
	public VytFrdSvyComptVO selectDownloadPhotoList(VytFrdSvyComptVO searchVO) throws Exception;
	
	public List<AnalFileVO> selectAnalPntInfo(HashMap<String, Object> analParameterMap) throws Exception;
	
	/**
	 * 조사항목 포인트 파일 일괄 다운로드
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> downloadAnalPntAll(HashMap<String, String> analDataMap) throws Exception;
	AnalFileVO downloadAnalPntAllImpl(List<AnalFileVO> fileVO, String accountId) throws Exception;
	
	/**
	 * 조사완료 상세조회 엑셀다운로드
	 * @param gid
	 * @throws Exception
	 */
	public Map<?, ?> selectFrdSvyComptDetailExcel(String gid) throws Exception;
	
	/*
	 * 기존노선조회
	 */
	public List<VytFrdSvyComptVO> selectCheckLines(VytFrdStripLandVO vo) throws Exception;
	
	public void deleteExistLines(String smid) throws Exception;
	
	/*
	 * 수정노선 등록시 tf_feis_frd_svycompt에 지오메트리 최신화
	 */
	public void updateNewGeomInfo(VytFrdStripLandVO vo) throws Exception;
	

}
