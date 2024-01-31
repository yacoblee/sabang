package or.sabang.sys.gis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;

/**
 * @author ipodo
 * @name MapServiceDAO
 * @created 2021. 11. 18
 * @modified 2021. 11. 18
 * @description
 *
 *
 */
@Repository("mapServiceDAO")
public class MapServiceDAO extends EgovComAbstractDAO{

	/**
	 * @author ipodo
	 * @name selectApiClsfyList
	 * @param
	 * @return {List<EgovMap>} 
	 * @throws Exception
	 * @description API 서비스 분류 목록 조회
	 */
	public List<EgovMap> selectApiClsfyList() throws Exception {
		return selectList("mapServiceDAO.selectApiClsfyList");
	}
	
	/**
	 * @author ipodo
	 * @name selectApiServiceList
	 * @param {HashMap} map - parameter map
	 * @return {List<EgovMap>}
	 * @throws Exception
	 * @description API 서비스 상세 목록 조회
	 */
	public List<EgovMap> selectApiServiceList(HashMap<String, Object> map) throws Exception {
		return selectList("mapServiceDAO.selectApiServiceList", map);
	}
	
	/**
	 * Feature 조회
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	public String selectFeatureInfo(Map<String, Object> mapParam) throws Exception {
		return selectOne("mapServiceDAO.selectFeatureInfo", mapParam);
	}

	/**
	 * 기초조사 조사완료 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectBscSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectBscSvyComptLstCnt", map);
	}
	
	/**
	 * 기초조사 조사완료 목록조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectBscSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectBscSvyComptLst", map);
	}

	/**
	 * 땅밀림 실태조사 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectLcpSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectLcpSvyComptLstCnt", map);
	}	
	
	/**
	 * 땅밀림 실태조사 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectLcpSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectLcpSvyComptLst", map);
	}

	/**
	 * 취약지역 실태조사 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectWkaSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectWkaSvyComptLstCnt", map);
	}		
	
	/**
	 * 취약지역 실태조사 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectWkaSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectWkaSvyComptLst", map);
	}
	
	/**
	 * 취약지역 해제조사 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectCnlSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectCnlSvyComptLstCnt", map);
	}		
	
	/**
	 * 취약지역 해제조사 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectCnlSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectCnlSvyComptLst", map);
	}

	/**
	 * 외관점검 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectAprSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectAprSvyComptLstCnt", map);
	}
	
	/**
	 * 외관점검 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectAprSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectAprSvyComptLst", map);
	}

	/**
	 * 임도 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectFrdSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectFrdSvyComptLstCnt", map);
	}
	
	/**
	 * 임도 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectFrdSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectFrdSvyComptLst", map);
	}
	
	/**
	 * 계측기 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectMseSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectMseSvyComptLstCnt", map);
	}
	
	/**
	 * 계측기 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectMseSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectMseSvyComptLst", map);
	}
	
	/**
	 * 정밀점검 목록 건수조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public int selectPcsSvyComptLstCnt(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectPcsSvyComptLstCnt", map);
	}
	
	/**
	 * 정밀점검 조사완료 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 */
	public List<EgovMap> selectPcsSvyComptLst(HashMap<String, Object> map) {
		return (List<EgovMap>) list("mapServiceDAO.selectPcsSvyComptLst", map);
	}
	
	/**
	 * 기초조사 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectBscSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectBscSvyComptWKT", map);
	}

	/**
	 * 땅밀림 실태조사 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectLcpSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectLcpSvyComptWKT", map);
	}

	/**
	 * 취약지역 실태조사 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectWkaSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectWkaSvyComptWKT", map);
	}

	/**
	 * 취약지역 해제조사 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectCnlSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectCnlSvyComptWKT", map);
	}

	/**
	 * 외관점검 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectAprSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectAprSvyComptWKT", map);
	}

	/**
	 * 임도 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectFrdSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectFrdSvyComptWKT", map);
	}
	
	/**
	 * 계측기 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectMseSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectMseSvyComptWKT", map);
	}
	
	/**
	 * 정밀점검 조사완료 벡터 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 24.
	 * @modified
	 */
	public List<String> selectPcsSvyComptWKT(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectPcsSvyComptWKT", map);
	}
	
	/**
	 * 시도 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 29.
	 * @modified
	 */
	public List<String> selectClusterSido(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectClusterSido", map);
	}

	/**
	 * 시군구 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 29.
	 * @modified
	 */
	public List<String> selectClusterSgg(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectClusterSgg", map);
	}

	/**
	 * 읍면동 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 29.
	 * @modified
	 */
	public List<String> selectClusterEmd(HashMap<String, Object> map) {
		return (List<String>) list("mapServiceDAO.selectClusterEmd", map);
	}
	
	/**
	 * 공유방명 검색
	 * @param mstNm
	 * @return
	 */
	public String selectSvyComptMstIdLst(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectSvyComptMstIdLst", map);
	}
	
	/**
	 * 기초조사 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectBscSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectBscSvyComptLstSmid", map);
	}
	
	/**
	 * 외관점검 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectAprSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectAprSvyComptLstSmid", map);
	}
	
	/**
	 * 땅밀림실태조사 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectLcpSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectLcpSvyComptLstSmid", map);
	}
	
	/**
	 * 취약지역실태조사 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectWkaSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectWkaSvyComptLstSmid", map);
	}
	
	/**
	 * 취약지역해제조사 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectCnlSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectCnlSvyComptLstSmid", map);
	}
	
	/**
	 * 임도타당성평가 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectFrdSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectFrdSvyComptLstSmid", map);
	}
	
	/**
	 * 정밀점검 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectPcsSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectPcsSvyComptLstSmid", map);
	}
	
	/**
	 * 계측기 검색결과 smid 목록
	 * @param map
	 * @return
	 */
	public String selectMseSvyComptLstSmid(HashMap<String, Object> map) {
		return selectOne("mapServiceDAO.selectMseSvyComptLstSmid", map);
	}
}
