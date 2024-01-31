package or.sabang.sys.gis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @author ipodo
 * @name MapService
 * @created 2021. 11. 18
 * @modified 2021. 11. 18
 * @description 
 *
 *
 */
public interface MapService {

	/**
	 * @author ipodo
	 * @name selectApiClsfyList
	 * @param
	 * @return {List<EgovMap>} 
	 * @throws Exception
	 * @description API 서비스 분류 목록 조회
	 */
	public List<EgovMap> selectApiClsfyList() throws Exception;

	/**
	 * @author ipodo
	 * @name selectApiServiceList
	 * @param {HashMap} map - parameter map
	 * @return {List<EgovMap>}
	 * @throws Exception
	 * @description API 서비스 상세 목록 조회
	 */
	public List<EgovMap> selectApiServiceList(HashMap<String, Object> map) throws Exception;
	
	/**
	 * Feature 조회
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	public String selectFeatureInfo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 조사결과 목록 건수 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectSvyComptLstCnt(HashMap<String, Object> map)  throws Exception;
	
	/**
	 * 조사결과 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectSvyComptLst(HashMap<String, Object> map) throws Exception;

	/**
	 * 조사결과 벡터 처리
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectSvyComptWKT(HashMap<String, Object> map) throws Exception;

	/**
	 * 시도 클러스터 
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 29.
	 * @modified
	 */
	public List<String> selectClusterSido(HashMap<String, Object> map) throws Exception;

	/**
	 * 시군구 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 29.
	 * @modified
	 */
	public List<String> selectClusterSgg(HashMap<String, Object> map) throws Exception;

	/**
	 * 읍면동 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 29.
	 * @modified
	 */
	public List<String> selectClusterEmd(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 공유방명 검색결과
	 * @param mstNm
	 * @return
	 * @throws Exception
	 */
	public String selectSvyComptMstIdLst(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 기초조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectBscSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 외관점검 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectAprSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 땅밀림실태조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectLcpSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 취약지역실태조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectWkaSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 취약지역해제조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectCnlSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 임도타당성평가 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectFrdSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 정밀점검 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectPcsSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 계측기 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String selectMseSvyComptLstSmid(HashMap<String, Object> map) throws Exception;
}
