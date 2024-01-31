package or.sabang.sys.ext.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.service.SysComptVO;

public interface ExtFieldBookService {
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 기초조사 공유방 체크
	 */
//	int selectBscCnrsSpceCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 외관점검 공유방 체크
	 */
//	int selectAprCnrsSpceCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 땅밀림실태조사 공유방 체크
	 */
//	int selectLcpCnrsSpceCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 취약지역실태조사 공유방 체크
	 */
//	int selectWkaCnrsSpceCheck(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 취약지역해제조사 공유방 체크
	 */
//	int selectCnlCnrsSpceCheck(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 비밀번호 조회
	 */
	List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 목록조회
	 */
	List<ExtFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 단건조회
	 */
	ExtFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception;

	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 목록 여부
	 */
	int selectCnrsSpceAt(int mst_id) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 다운로드
	 */
	List<ExtFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception;

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사데이터 조회
	 */
	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception;

	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 기초조사 조사데이터 FID 최대값 조회
	 */
	int selectBscCnrsSpceMvl(int mst_id) throws Exception;
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 땅밀림실태조사 조사데이터 FID 최대값 조회
	 */
	int selectLcpCnrsSpceMvl(int mst_id) throws Exception;
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 외관점검 조사데이터 FID 최대값 조회
	 */
	int selectAprCnrsSpceFieldMvl(int mst_id) throws Exception;
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 조사완료 외관점검 조사데이터 FID 최대값 조회
	 */
	int selectAprCnrsSpceComptMvl(int mst_id) throws Exception;
	
	/**
	 * @param mst_id
	 * @return
	 * @throws Exception 전자야장 공유방 취약지역실태조사 조사데이터 FID 최대값 조회
	 */
	int selectWkaCnrsSpceMvl(int mst_id) throws Exception;
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 취약지역해제조사 조사데이터 FID 최대값 조회
	 */
	int selectCnlCnrsSpceMvl(int mst_id) throws Exception;
	
	/**
	 * @return
	 * @description 전자야장 기초조사 FID 갱신
	 */
	int selectBscUpdtFid() throws Exception;
	
	/**
	 * @return
	 * @description 전자야장 땅밀림 실태조사 FID 갱신
	 */
	int selectLcpUpdtFid() throws Exception;

	/**
	 * @return
	 * @throws Exception 전자야장 취약지역 실태조사 FID 갱신
	 */
	int selectWkaUpdtFid() throws Exception;
	/**
	 * @return
	 * @description 전자야장 취약지역 해제조사 FID 갱신
	 */
	int selectCnlUpdtFid() throws Exception;
	
	/**
	 * @param itemVO
	 * @return
	 * @description 전자야장 공유방 조사데이터 생성
	 */
	void insertCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * @param itemVO
	 * @return
	 * @description 전자야장 공유방 조사데이터 업데이트
	 */
	void updateCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception;

	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 전자야장 공유방 조사데이터 upsert 
	 */
	void upsertCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception;
	
	/**
	 * 전자야장 공유방 조사 완료데이터 단건 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	ExtFieldBookItemVO selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사데이터 업로드 결과 목록
	 */
	List<ExtFieldBookItemVO> selectCnrsSpceCompList(HashMap<String, Object> map) throws Exception;
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 전자야장 완료테이블 조사데이터 여부
	 */
	int selectUpsertAt(ExtFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * @param updDate
	 * @throws Exception
	 * @desciption 전자야장 전송 데이터 업데이트 날짜와 완료테이블 데이터 날짜 비교
	 */
	int selectUpdDateAt(ExtFieldBookItemVO searchVO) throws Exception;
	
	/**
	 * 전자야장 위치도 업데이트
	 * @author ipodo
	 * @name updateComptLcMap
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	int updateComptLcMap(HashMap<String, Object> schMap) throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 동기화
	 */
	List<ExtFieldBookItemVO> selectCnrsSpceSyncItem(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 위치도 줌레벨 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int selectLocImgInfo(LocImgInfoVO map) throws Exception;
	
	/**
	 * 위치도 줌레벨 추가 및 수정
	 * @param map
	 * @return
	 * @throws Exception
	 */
	void insertUpdtLocImgInfo(LocImgInfoVO map) throws Exception;

	/**
	 * 위치도 정보 조회
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	List<EgovMap> selectComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception;
	
	/**
	 * 위치도 정보 조회
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	List<EgovMap> selectAprComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception;

	/**
	 * 위치도 정보 조회
	 * @author DEVWORK
	 * @param schMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 */
	List<EgovMap> selectPcsComptLcMapLonLat(HashMap<String, Object> schMap)throws Exception; 
	
}
