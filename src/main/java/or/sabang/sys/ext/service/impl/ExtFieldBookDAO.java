package or.sabang.sys.ext.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.ext.service.ExtFieldBookItemVO;
import or.sabang.sys.ext.service.ExtFieldBookVO;
import or.sabang.sys.ext.service.LocImgInfoVO;

@Repository("extFieldBookDAO")
public class ExtFieldBookDAO extends EgovComAbstractDAO {
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 기초조사 공유방 체크
	 */
//	public int selectBscCnrsSpceCheck(HashMap<String, Object> map) throws Exception{
//		return (int) selectOne("extFieldBookDAO.selectBscCnrsSpceCheck", map);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 외관점검 공유방 체크
	 */
//	public int selectAprCnrsSpceCheck(HashMap<String, Object> map) throws Exception{
//		return (int) selectOne("extFieldBookDAO.selectAprCnrsSpceCheck", map);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 땅밀림실태조사 공유방 체크
	 */
//	public int selectLcpCnrsSpceCheck(HashMap<String, Object> map) throws Exception{
//		return (int) selectOne("extFieldBookDAO.selectLcpCnrsSpceCheck", map);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 취약지역실태조사 공유방 체크
	 */
//	public int selectWkaCnrsSpceCheck(HashMap<String, Object> map) throws Exception{
//		return (int) selectOne("extFieldBookDAO.selectWkaCnrsSpceCheck", map);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 취약지역해제조사 공유방 체크
	 */
//	public int selectCnlCnrsSpceCheck(HashMap<String, Object> map) throws Exception{
//		return (int) selectOne("extFieldBookDAO.selectCnlCnrsSpceCheck", map);
//	}

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("extFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 목록조회
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ExtFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<ExtFieldBookVO>) list("extFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 단건조회
	 */
	public ExtFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
		return (ExtFieldBookVO) selectOne("extFieldBookDAO.selectCnrsSpceInfo", map);
	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 목록 여부
	 */
	public int selectCnrsSpceAt(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectCnrsSpceAt", mst_id);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 다운로드
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ExtFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception{
		return (List<ExtFieldBookItemVO>) list("extFieldBookDAO.selectCnrsSpceDownload", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사데이터 조회
	 */ 
	@SuppressWarnings("unchecked")
	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("extFieldBookDAO.selectCnrsSpceItem", map);
	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장  공유방 기초조사 조사데이터 FID 최대값 조회
	 */
	public int selectBscCnrsSpceMvl(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectBscCnrsSpceMvl", mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 땅밀림실태조사 조사데이터 FID 최대값 조회
	 */
	public int selectLcpCnrsSpceMvl(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectLcpCnrsSpceMvl", mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 외관점검 조사데이터 FID 최대값 조회
	 */
	public int selectAprCnrsSpceFieldMvl(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectAprCnrsSpceFieldMvl", mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 조사완료 외관점검 조사데이터 FID 최대값 조회
	 */
	public int selectAprCnrsSpceComptMvl(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectAprCnrsSpceComptMvl", mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장  공유방 취약지역 실태조사 조사데이터 FID 최대값 조회
	 */
	public int selectWkaCnrsSpceMvl(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectWkaCnrsSpceMvl", mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장  공유방 취약지역 해제조사 조사데이터 FID 최대값 조회
	 */
	public int selectCnlCnrsSpceMvl(int mst_id) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectCnlCnrsSpceMvl", mst_id);
	}
	
	/**
	 * @return
	 * @description 전자야장 기초조사 FID 갱신
	 */	
	public int selectBscUpdtFid() throws Exception{
		return (int) selectOne("extFieldBookDAO.selectBscUpdtFid");
	}
	
	/**
	 * @return
	 * @description 전자야장 땅밀림실태조사 FID 갱신
	 */	
	public int selectLcpUpdtFid() throws Exception{
		return (int) selectOne("extFieldBookDAO.selectLcpUpdtFid");
	}
	
	/**
	 * @return
	 * @description 전자야장 취약지역 실태조사 FID 갱신
	 */	
	public int selectWkaUpdtFid() throws Exception{
		return (int) selectOne("extFieldBookDAO.selectWkaUpdtFid");
	}
	
	/**
	 * @return
	 * @description 전자야장 취약지역 해제조사 FID 갱신
	 */	
	public int selectCnlUpdtFid() throws Exception{
		return (int) selectOne("extFieldBookDAO.selectCnlUpdtFid");
	}
		
	/**
	 * @param itemVO
	 * @return
	 * @description 전자야장 공유방 조사데이터 생성
	 */	
	void insertCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception{
		insert("extFieldBookDAO.insertCnrsSpceItem", itemVO);
	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 전자야장 공유방 조사데이터 업데이트
	 */	
	void updateCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception{
		update("extFieldBookDAO.updateCnrsSpceItem", itemVO);
	};
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 전자야장 공유방 조사데이터 upsert
	 */
	void upsertCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception{
		update("extFieldBookDAO.upsertCnrsSpceItem", itemVO);
	};
	
	/**
	 * 
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사 완료데이터 단건 조회
	 */
	public ExtFieldBookItemVO selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception{
		return (ExtFieldBookItemVO) selectOne("extFieldBookDAO.selectCnrsSpceCompItem", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사데이터 업로드 결과 목록
	 */
	@SuppressWarnings("unchecked")
	public List<ExtFieldBookItemVO> selectCnrsSpceCompList(HashMap<String, Object> map) throws Exception{
		return (List<ExtFieldBookItemVO>) list("extFieldBookDAO.selectCnrsSpceCompList", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 전자야장 완료테이블 조사데이터 여부
	 */
	public int selectUpsertAt(ExtFieldBookItemVO searchVO) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectUpsertAt", searchVO);
	}

	/**
	 * @param updDate
	 * @throws Exception
	 * @desciption 전자야장 전송 데이터 업데이트 날짜와 완료테이블 데이터 날짜 비교
	 */
	public int selectUpdDateAt(ExtFieldBookItemVO searchVO) throws Exception {
		return (int) selectOne("extFieldBookDAO.selectUpdDateAt", searchVO);
	}
	
	/**
	 * 전자야장 위치도 포인트 및 라벨 조회
	 * @author ipodo
	 * @name updateComptLcMap
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception {
		return (List<EgovMap>) list("extFieldBookDAO.selectComptLcMapLonLat", schMap);
	}
	
	/**
	 * 전자야장 위치도 포인트 및 라벨 조회
	 * @author ipodo
	 * @name updateComptLcMap
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectAprComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception {
		return (List<EgovMap>) list("extFieldBookDAO.selectAprComptLcMapLonLat", schMap);
	}
	

	/**
	 * 전자야장 위치도 포인트 및 라벨 조회
	 * @author DEVWORK
	 * @param schMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 */
	public List<EgovMap> selectPcsComptLcMapLonLat(HashMap<String, Object> schMap)  throws Exception{
		return (List<EgovMap>) list("extFieldBookDAO.selectPcsComptLcMapLonLat", schMap);
	};	
	
	/**
	 * 전자야장 위치도 업데이트
	 * @author ipodo
	 * @name updateComptLcMap
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	public int updateComptLcMap(HashMap<String, Object> schMap) throws Exception {
		return update("extFieldBookDAO.updateComptLcMap", schMap);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 동기화
	 */
	@SuppressWarnings("unchecked")
	public List<ExtFieldBookItemVO> selectCnrsSpceSyncItem(HashMap<String, Object> map) throws Exception{
		return (List<ExtFieldBookItemVO>) list("extFieldBookDAO.selectCnrsSpceSyncItem", map);
	};
	
	/**
	 * 위치도 줌레벨 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int selectLocImgInfo(LocImgInfoVO map) throws Exception{
		return (int) selectOne("extFieldBookDAO.selectLocImgInfo", map);
	};
	
	/**
	 * 위치도 줌레벨 추가 및 수정
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void insertUpdtLocImgInfo(LocImgInfoVO map) throws Exception{
		insert("extFieldBookDAO.insertUpdtLocImgInfo", map);
	}

}
