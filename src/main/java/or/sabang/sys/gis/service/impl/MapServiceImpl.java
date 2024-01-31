package or.sabang.sys.gis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.MapService;

/**
 * @author ipodo
 * @name MapServiceImpl
 * @created 2021. 11. 18
 * @modified 2021. 11. 18
 * @description
 *
 *
 */
@Service("mapService")
public class MapServiceImpl extends EgovAbstractServiceImpl implements MapService{

	@Resource(name = "mapServiceDAO")
	private MapServiceDAO mapServiceDAO;
	
	/**
	 * @author ipodo
	 * @name selectApiClsfyList
	 * @param
	 * @return {List<EgovMap>} 
	 * @throws Exception
	 * @description API 서비스 분류 목록 조회
	 */
	@Override
	public List<EgovMap> selectApiClsfyList() throws Exception {
		return mapServiceDAO.selectApiClsfyList();
	}

	/**
	 * @author ipodo
	 * @name selectApiServiceList
	 * @param {HashMap} map - parameter map
	 * @return {List<EgovMap>}
	 * @throws Exception
	 * @description API 서비스 상세 목록 조회
	 */
	@Override
	public List<EgovMap> selectApiServiceList(HashMap<String, Object> map) throws Exception {
		return mapServiceDAO.selectApiServiceList(map);
	}
	
	/**
	 * Feature 조회
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectFeatureInfo(Map<String, Object> mapParam) throws Exception{
		return mapServiceDAO.selectFeatureInfo(mapParam);
	}

	/**
	 * 조사결과 목록 건수 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 * @see or.sabang.sys.gis.service.MapService#selectSvyComptLst(java.util.HashMap)
	 */
	@Override
	public int selectSvyComptLstCnt(HashMap<String, Object> map) {
		
		int totalCnt = 0;
		
		//기초조사
		if(map.get("type").toString().equals("bsc")) {
			totalCnt = mapServiceDAO.selectBscSvyComptLstCnt(map);
		}
		//땅밀림 
		else if(map.get("type").toString().equals("lcp")) {
			totalCnt = mapServiceDAO.selectLcpSvyComptLstCnt(map);
		}
		//취약지역 실태
		else if(map.get("type").toString().equals("wka")) {
			totalCnt = mapServiceDAO.selectWkaSvyComptLstCnt(map);
		}
		//취약지역 해제
		else if(map.get("type").toString().equals("cnl")) {
			totalCnt = mapServiceDAO.selectCnlSvyComptLstCnt(map);
		}
		//외관점검
		else if(map.get("type").toString().equals("apr")) {
			totalCnt = mapServiceDAO.selectAprSvyComptLstCnt(map);
		}
		//임도
		else if(map.get("type").toString().equals("frd")) {
			totalCnt = mapServiceDAO.selectFrdSvyComptLstCnt(map);
		}
		//계측기
		else if(map.get("type").toString().equals("mse")) {
			totalCnt = mapServiceDAO.selectMseSvyComptLstCnt(map);
		}
		//정밀점검
		else if(map.get("type").toString().equals("pcs")) {
			totalCnt = mapServiceDAO.selectPcsSvyComptLstCnt(map);
		}
		return totalCnt;
	}
	
	/**
	 * 조사결과 목록 조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 8. 21.
	 * @modified
	 * @see or.sabang.sys.gis.service.MapService#selectSvyComptLst(java.util.HashMap)
	 */
	@Override
	public List<EgovMap> selectSvyComptLst(HashMap<String, Object> map) {
		
		List<EgovMap> rsltLst = new ArrayList<EgovMap>();
		
		//기초조사
		if(map.get("type").toString().equals("bsc")) {
			rsltLst = mapServiceDAO.selectBscSvyComptLst(map);
		}
		//땅밀림 
		else if(map.get("type").toString().equals("lcp")) {
			rsltLst = mapServiceDAO.selectLcpSvyComptLst(map);
		}
		//취약지역 실태
		else if(map.get("type").toString().equals("wka")) {
			rsltLst = mapServiceDAO.selectWkaSvyComptLst(map);
		}
		//취약지역 해제
		else if(map.get("type").toString().equals("cnl")) {
			rsltLst = mapServiceDAO.selectCnlSvyComptLst(map);
		}
		//외관점검
		else if(map.get("type").toString().equals("apr")) {
			rsltLst = mapServiceDAO.selectAprSvyComptLst(map);
		}
		//임도
		else if(map.get("type").toString().equals("frd")) {
			rsltLst = mapServiceDAO.selectFrdSvyComptLst(map);
		}
		//계측기
		else if(map.get("type").toString().equals("mse")) {
			rsltLst = mapServiceDAO.selectMseSvyComptLst(map);
		}
		//정밀점검
		else if(map.get("type").toString().equals("pcs")) {
			rsltLst = mapServiceDAO.selectPcsSvyComptLst(map);
		}

		return rsltLst;
	}

	/**
	 * 조사완료 벡터 조회용
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 24.
	 * @modified
	 * @see or.sabang.sys.gis.service.MapService#selectSvyComptWKT(java.util.HashMap)
	 */
	@Override
	public List<String> selectSvyComptWKT(HashMap<String, Object> map) throws Exception {
		
		List<String> rsltLst = new ArrayList<String>();
		
		//기초조사
		if(map.get("type").toString().equals("bsc")) {
			rsltLst = mapServiceDAO.selectBscSvyComptWKT(map);
		}
		//땅밀림 
		else if(map.get("type").toString().equals("lcp")) {
			rsltLst = mapServiceDAO.selectLcpSvyComptWKT(map);
		}
//		//취약지역 실태
		else if(map.get("type").toString().equals("wka")) {
			rsltLst = mapServiceDAO.selectWkaSvyComptWKT(map);
		}
//		//취약지역 해제
		else if(map.get("type").toString().equals("cnl")) {
			rsltLst = mapServiceDAO.selectCnlSvyComptWKT(map);
		}
//		//외관점검
		else if(map.get("type").toString().equals("apr")) {
			rsltLst = mapServiceDAO.selectAprSvyComptWKT(map);
		}
		//임도
		else if(map.get("type").toString().equals("frd")) {
			rsltLst = mapServiceDAO.selectFrdSvyComptWKT(map);
		}
		//계측기
		else if(map.get("type").toString().equals("mse")) {
			rsltLst = mapServiceDAO.selectMseSvyComptWKT(map);
		}
		//정밀점검
		else if(map.get("type").toString().equals("pcs")) {
			rsltLst = mapServiceDAO.selectPcsSvyComptWKT(map);
		}
		return rsltLst;
	}

	/**
	 * 시도 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 29.
	 * @modified
	 * @see or.sabang.sys.gis.service.MapService#selectClusterSido(java.util.HashMap)
	 */
	@Override
	public List<String> selectClusterSido(HashMap<String, Object> map) throws Exception {
		return mapServiceDAO.selectClusterSido(map);
	}

	/**
	 * 시군구 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 29.
	 * @modified
	 * @see or.sabang.sys.gis.service.MapService#selectClusterSgg(java.util.HashMap)
	 */
	@Override
	public List<String> selectClusterSgg(HashMap<String, Object> map) throws Exception {
		return mapServiceDAO.selectClusterSgg(map);
	}

	/**
	 * 읍면동 클러스터
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @throws Exception
	 * @since 2023. 8. 29.
	 * @modified
	 * @see or.sabang.sys.gis.service.MapService#selectClusterEmd(java.util.HashMap)
	 */
	@Override
	public List<String> selectClusterEmd(HashMap<String, Object> map) throws Exception {
		return mapServiceDAO.selectClusterEmd(map);
	}
	
	
	/**
	 * 공유방명 검색
	 * @param mstNm
	 * @return
	 */
	@Override
	public String selectSvyComptMstIdLst(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectSvyComptMstIdLst(map);
	}
	
	/**
	 * 기초조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectBscSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectBscSvyComptLstSmid(map);
	}
	
	/**
	 * 외관점검 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectAprSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectAprSvyComptLstSmid(map);
	}
	
	/**
	 * 땅밀림실태조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectLcpSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectLcpSvyComptLstSmid(map);
	}
	
	/**
	 * 취약지역실태조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectWkaSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectWkaSvyComptLstSmid(map);
	}
	
	/**
	 * 취약지역해제조사 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectCnlSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectCnlSvyComptLstSmid(map);
	}
	
	/**
	 * 임도타당성평가 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectFrdSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectFrdSvyComptLstSmid(map);
	}
	
	/**
	 * 정밀점검 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectPcsSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectPcsSvyComptLstSmid(map);
	}
	
	/**
	 * 계측기 검색결과 smid 목록
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public String selectMseSvyComptLstSmid(HashMap<String, Object> map) throws Exception{
		return mapServiceDAO.selectMseSvyComptLstSmid(map);
	}
}
