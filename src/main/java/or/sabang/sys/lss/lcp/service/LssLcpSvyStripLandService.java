package or.sabang.sys.lss.lcp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.service.ZonalStatisticVO;

public interface LssLcpSvyStripLandService  {
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectLssLcpSvySldTotCnt(LssLcpSvyStripLandVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<?> selectLssLcpSvySldList(LssLcpSvyStripLandVO searchVO) throws Exception;
	
	/**
	 * 대상지 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<EgovMap> test() throws Exception;
	
	/**
	 * 대상지 정보 갯수를 조회한다.
	 * 
	 * @param searchVO
	 * @return int(대상지 총 갯수)
	 * @throws Exception 
	 */
	int selectSvySldInfoCnt(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 대상지 정보를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	LssLcpSvyStripLandVO selectSvySldInfoDetail(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 대상지를 일괄등록한다.
	 * @param mFile
	 * @throws Exception
	 */
	void insertUploadStripLand(MultipartFile mFile) throws Exception;
	
	/**
	 * 대상지를 등록한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void insertLssLcpSvyStripLand(LssLcpSvyStripLand stripLand) throws Exception;
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void updateLssLcpSvyStripLand(LssLcpSvyStripLand stripLand) throws Exception;
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	void deleteLssLcpSvyStripLand(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	String selectLssLcpSvyStripLandMaxYear() throws Exception;
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	List<?> selectLssLcpSvyStripLandYear() throws Exception;
	
	/**
	 * 조사대상지 등록정보를 등록한다.
	 * @throws Exception
	 */
	String insertSvySldRegInfo(HashMap<String, Object> sldMap) throws Exception;
	
	/**
     * 조사대상지 정보를 조회한다.
     * @throws Exception
     */
    public List<EgovMap> selectRankInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
     * 행정구역별 미조사된 대상지 건수를 조회한다.
     * @throws Exception
     */
    public EgovMap selectRankInfoCnt() throws Exception;
    
    /**
     * 행정구역별 작년조사된 대상지 건수를 조회한다.
     * @throws Exception
     */
    public EgovMap selectLastRankInfoCnt() throws Exception;
    
    /**
	 * 랭크데이터를 수정한다.
	 * @throws Exception
	 */
	void updateRankInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 조사대상지 정보를 등록한다.
	 * @throws Exception
	 */
    void insertSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
     * 조사대상지 등록정보 상세조회
     * @throws Exception
     */
    public EgovMap selectSvySldRegInfoDetail(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
	 * 조사대상지 시도별 건수 조회.
	 * @throws Exception
	 */
    public List<?> selectSvySldCtrdCnt(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 조사대상지 등록정보를 수정한다.
	 * @throws Exception
	 */
    void updateSvySldRegInfo(String sldId) throws Exception;
    
    /**
     * 조사대상지 정보를 삭제한다.
     * @throws Exception
     */
    void deleteSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
	 * 랭크데이터를 수정한다.
	 * @throws Exception
	 */
	void updateRankDelInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 조사대상지 임상도 정보를 조회한다.
	 * @throws Exception
	 */
	LssLcpSvyStripLandVO selectSvySldImInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 조사대상지 입지도 정보를 조회한다.
	 * @throws Exception
	 */
	LssLcpSvyStripLandVO selectSvySldIjInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
	 * 조사대상지 지질도 정보를 조회한다.
	 * @throws Exception
	 */
	LssLcpSvyStripLandVO selectSvySldGlInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
	
	/**
     * 랭크 정보를 삭제한다.
     * @throws Exception
     */
    void deleteRank(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
     * 제보 정보를 삭제한다.
     * @throws Exception
     */
    void deleteGvf(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
     * 조사대상지 등록정보를 삭제한다.
     * @throws Exception
     */
    void deleteSvySldRegInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
	 * 조사대상지 등록정보방 목록을 조회한다.
	 * 
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	List<?> selectSvySldRegInfoList() throws Exception;
	
    /**
     * 고도정보를 등록한다.
     * @param list
     * @throws Exception
     */
    void updateSvySldElevInfo(List<ZonalStatisticVO> list) throws Exception;
    
    /**
     * 경사정보를 등록한다.
     * @param list
     * @throws Exception
     */
    void updateSvySldSlopInfo(List<ZonalStatisticVO> list) throws Exception;
    
    /**
     * 토심정보를 등록한다.
     * @param list
     * @throws Exception
     */
    void updateSvySldSldInfo(List<ZonalStatisticVO> list) throws Exception;
    
    List<EgovMap> selectSvysldGidInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    void updateSvySldGid(LssLcpSvyStripLandVO stripLandVO) throws Exception;
    
    /**
     * 조사대상지 엑셀다운로드
     * @param vo
     * @return
     * @throws Exception
     */
    public Map<?, ?> selectLcpSvySldListExcel(LssLcpSvyStripLandVO vo) throws Exception;
}
