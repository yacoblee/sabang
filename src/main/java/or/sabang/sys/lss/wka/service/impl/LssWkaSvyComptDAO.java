package or.sabang.sys.lss.wka.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.wka.service.LssWkaSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

@Repository("lssWkaSvyComptDAO")
public class LssWkaSvyComptDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectWkaSvyComptListTotCnt(LssWkaSvyComptVO searchVO) throws Exception{
		return (Integer)selectOne("LssWkaSvyCompt.selectWkaSvyComptListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssWkaSvyComptVO> selectWkaSvyComptList(LssWkaSvyComptVO searchVO) throws Exception{
		 return (List<LssWkaSvyComptVO>) list("LssWkaSvyCompt.selectWkaSvyComptList", searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public LssWkaSvyComptVO selectWkaSvyComptDetail(LssWkaSvyComptVO svyComptVO) throws Exception{
		return selectOne("LssWkaSvyCompt.selectWkaSvyComptDetail", svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateWkaCompt(HashMap<String, Object> wkaCompt, HashMap<String, Object> wkaCompt2) throws Exception {
		update("LssWkaSvyCompt.updateWkaSvyCompt",wkaCompt);
		update("LssWkaSvyCompt.updateWkaSvyCompt",wkaCompt2);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateWkaSvyCompt(LssWkaSvyComptVO svyComptVO) throws Exception {
		update("LssWkaSvyCompt.updateWkaSvyCompt",svyComptVO);
	}
	/**
	 * 대상지 공간정보를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateWkaSvyComptGeom(LssWkaSvyComptVO svyComptVO) throws Exception {
		update("LssWkaSvyCompt.updateWkaSvyComptGeom",svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteWkaSvyCompt(LssWkaSvyComptVO svyComptVO) throws Exception{
		delete("LssWkaSvyCompt.deleteWkaSvyCompt", svyComptVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public List<?> selectWkaSvyComptListExcel(LssWkaSvyComptVO svyComptVO) throws Exception{
		return list("LssWkaSvyCompt.selectWkaSvyComptListExcel",svyComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectWkaSvyComptMaxYear() throws Exception{
		return selectOne("LssWkaSvyCompt.selectWkaSvyComptMaxYear","");
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectWkaSvyComptMaxMonth() throws Exception{
		return selectOne("LssWkaSvyCompt.selectWkaSvyComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectWkaSvyComptYear() throws Exception{
		return list("LssWkaSvyCompt.selectWkaSvyComptYear","");
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @throws Exception
	 */
	void updateWkaSvyComptExcel(LssWkaSvyComptVO svyComptVO) throws Exception{
		update("LssWkaSvyCompt.updateWkaSvyComptExcel", svyComptVO);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) {
		return (List<EgovMap>) list("LssWkaSvyCompt.selectWkaSvyComptPhotoInfo", searchVO);
	};
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("LssWkaSvyCompt.selectLastUpdateMinMaxDate",searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return list("LssWkaSvyCompt.updateLocReCreate",map);
	};
	
	/**
	 * 위치도,현황도,대피체계 이미지 경로 수정
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	public int updateComptLcMap(HashMap<String, Object> schMap) throws Exception {
		return update("LssWkaSvyCompt.updateComptLcMap", schMap);
	}
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectWkaLocReCreateSvyId(LssWkaSvyComptVO svyComptVO) throws Exception{
		return (List<EgovMap>) list("LssWkaSvyCompt.selectWkaLocReCreateSvyId",svyComptVO);
	};
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param svyComptVO
	 * @throws Exception
	 */
	public void updateWkaSvyComptPhotoList(LssWkaSvyComptVO searchVO) throws Exception {
		update("LssWkaSvyCompt.updateWkaSvyComptPhotoList",searchVO);
	}
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	public EgovMap selectSvyPhotoNullList(LssWkaSvyComptVO searchVO) throws Exception{
		 return selectOne("LssWkaSvyCompt.selectSvyPhotoNullList", searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssWkaSvyComptVO map) throws Exception{
		return list("LssWkaSvyCompt.updatePhotoList",map);
	};
	
	/**
	 * 대상지 공간정보 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeom(LssWkaSvyComptVO searchVO) throws Exception{
		return selectOne("LssWkaSvyCompt.selectSvyComptGeom",searchVO);
	}
	
	/**
	 * 공간정보 (유출구)
	 * @param geomMap
	 * @throws Exception
	 */
	public void insertSvyComptGeomVnarapnt(HashMap<String, Object> geomMap) throws Exception {
		insert("LssWkaSvyCompt.insertSvyComptGeomVnarapnt",geomMap);
	}
	
	/**
	 * 공간정보 (대피로)
	 * @param geomMap
	 * @throws Exception
	 */
	public void insertSvyComptGeomVnaralne(HashMap<String, Object> geomMap) throws Exception {
		insert("LssWkaSvyCompt.insertSvyComptGeomVnaralne",geomMap);
	}
	
	/**
	 * 공간정보 (폴리곤)
	 * @param geomMap
	 * @throws Exception
	 */
	public void insertSvyComptGeomVnaraPlgn(HashMap<String, Object> geomMap) throws Exception {
		insert("LssWkaSvyCompt.insertSvyComptGeomVnaraPlgn",geomMap);
	}
	
	/**
	 * 대상지 공간정보 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeomPntLne(LssWkaSvyComptVO searchVO) throws Exception{
		return selectOne("LssWkaSvyCompt.selectSvyComptGeomPntLne",searchVO);
	}

   /**
	 * 대상지 공간정보 조회
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssWkaSvyComptVO> selectSvyComptGeomPlgn(LssWkaSvyComptVO searchVO) throws Exception{
		 return (List<LssWkaSvyComptVO>) list("LssWkaSvyCompt.selectSvyComptGeomPlgn", searchVO);
	}
	
	/**
	 * 대상지 법정동 코드 조회
	 */
	public String selectLegaldongcd(EgovMap map) throws Exception{
		return selectOne("LssWkaSvyCompt.selectLegaldongcd",map);
	}
	
	/**
	 * 대상지 산사태관리기관 코드 조회
	 */
	public String selectMnagncd(EgovMap map) throws Exception{
		return selectOne("LssWkaSvyCompt.selectMnagncd",map);
	}
	
	/**
	 * 대상지 현장사진 목록 조회
	 * @param stripLandVO
	 * @throws Exception
	 */
	public List<?> selectPhotoTagList(LssWkaSvyComptVO svyComptVO) throws Exception{
		return list("LssWkaSvyCompt.selectPhotoTagList",svyComptVO);
	}
	
	/**
	 * 현황도 정보 조회
	 * @param searchMap
	 * @return
	 * @throws Exception
	 */
	public String selectSvyComptStatMap(HashMap<String, Object> searchMap) throws Exception{
		return selectOne("LssWkaSvyCompt.selectSvyComptStatMap",searchMap);
	}
	
	/**
	 * 편입, 지적면적 계산
	 * @param hashMap
	 * @return
	 * @throws Exception
	 */
	public List<LssWkaSvyComptVO> selectCalcInArea(LssWkaSvyComptVO searchVO) throws Exception{
		return (List<LssWkaSvyComptVO>)list("LssWkaSvyCompt.selectCalcInArea",searchVO);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("LssWkaSvyCompt.selectAuthorCheck",map);
	}
	
	/**
	 * 로그인한 유저의 권한있는 공유방 목록 조회
	 * @param map
	 * @throws Exception
	 */
	public List<String> selectAuthorCnrsList(HashMap<String, Object> map) throws Exception{
		return (List<String>) list("LssWkaSvyCompt.selectAuthorCnrsList",map);
	}
	
	/**
	 * 유역면적 점수를 조회한다.
     * @param vnaraPlgnWkt03
     * @return String
     */
	public double selectDgrareaScore(String vnaraPlgnWkt03) throws Exception{
		return (Double)selectOne("LssWkaSvyCompt.selectDgrareaScore", vnaraPlgnWkt03);
	}
	
	/**
	 * 공간정보 2개의 폴리곤을 하나로 합친다. 
     * @param calcAreaMap
     * @return String
     */
	public String selectUnionGeom(HashMap<String, Object> map) throws Exception{
		return selectOne("LssWkaSvyCompt.selectUnionGeom", map);
	}
	
	/**
	 * 공간정보 삭제(포인트)
	 * @param geomMap
	 * @throws Exception
	 */
	public void deleteSvyComptGeomVnarapnt(HashMap<String, Object> geomMap) throws Exception {
		delete("LssWkaSvyCompt.deleteSvyComptGeomVnarapnt",geomMap);
	}
	/**
	 * 공간정보 삭제(라인)
	 * @param geomMap
	 * @throws Exception
	 */
	public void deleteSvyComptGeomVnaralne(HashMap<String, Object> geomMap) throws Exception {
		delete("LssWkaSvyCompt.deleteSvyComptGeomVnaralne",geomMap);
	}
	/**
	 * 공간정보 삭제(폴리곤)
	 * @param geomMap
	 * @throws Exception
	 */
	public void deleteSvyComptGeomVnaraPlgn(HashMap<String, Object> geomMap) throws Exception {
		delete("LssWkaSvyCompt.deleteSvyComptGeomVnaraPlgn",geomMap);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLssWkaProjDMS(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("LssWkaSvyCompt.selectLssWkaProjDMS",map);
	}
}
