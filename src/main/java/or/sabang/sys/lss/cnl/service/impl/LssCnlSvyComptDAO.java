package or.sabang.sys.lss.cnl.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.cnl.service.LssCnlSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

@Repository("lssCnlSvyComptDAO")
public class LssCnlSvyComptDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectCnlSvyComptListTotCnt(LssCnlSvyComptVO searchVO) throws Exception{
		return (Integer)selectOne("LssCnlSvyCompt.selectCnlSvyComptListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssCnlSvyComptVO> selectCnlSvyComptList(LssCnlSvyComptVO searchVO) throws Exception{
		 return (List<LssCnlSvyComptVO>) list("LssCnlSvyCompt.selectCnlSvyComptList", searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public LssCnlSvyComptVO selectCnlSvyComptDetail(LssCnlSvyComptVO svyComptVO) throws Exception{
		return selectOne("LssCnlSvyCompt.selectCnlSvyComptDetail", svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateCnlSvyCompt(LssCnlSvyComptVO svyComptVO) throws Exception {
		update("LssCnlSvyCompt.updateCnlSvyCompt",svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteCnlSvyCompt(LssCnlSvyComptVO svyComptVO) throws Exception{
		delete("LssCnlSvyCompt.deleteCnlSvyCompt", svyComptVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public List<?> selectCnlSvyComptListExcel(LssCnlSvyComptVO svyComptVO) throws Exception{
		return list("LssCnlSvyCompt.selectCnlSvyComptListExcel",svyComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectCnlSvyComptMaxYear() throws Exception{
		return selectOne("LssCnlSvyCompt.selectCnlSvyComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월 최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectCnlSvyComptMaxMonth() throws Exception{
		return selectOne("LssCnlSvyCompt.selectCnlSvyComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectCnlSvyComptYear() throws Exception{
		return list("LssCnlSvyCompt.selectCnlSvyComptYear","");
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @throws Exception
	 */
	void updateCnlSvyComptExcel(LssCnlSvyComptVO svyComptVO) throws Exception{
		update("LssCnlSvyCompt.updateCnlSvyComptExcel", svyComptVO);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectCnlSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) {
		return (List<EgovMap>) list("LssCnlSvyCompt.selectCnlSvyComptPhotoInfo", searchVO);
	};
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("LssCnlSvyCompt.selectLastUpdateMinMaxDate",searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return list("LssCnlSvyCompt.updateLocReCreate",map);
	};
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCnlLocReCreateSvyId(LssCnlSvyComptVO svyComptVO) throws Exception{
		return (List<EgovMap>) list("LssCnlSvyCompt.selectCnlLocReCreateSvyId",svyComptVO);
	};
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param svyComptVO
	 * @throws Exception
	 */
	public void updateCnlSvyComptPhotoList(LssCnlSvyComptVO searchVO) throws Exception {
		update("LssCnlSvyCompt.updateCnlSvyComptPhotoList",searchVO);
	}
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	public EgovMap selectSvyPhotoNullList(LssCnlSvyComptVO searchVO) throws Exception{
		 return selectOne("LssCnlSvyCompt.selectSvyPhotoNullList", searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssCnlSvyComptVO map) throws Exception{
		return list("LssCnlSvyCompt.updatePhotoList",map);
	};
	
	/**
	 * 대상지 공간정보 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeom(LssCnlSvyComptVO searchVO) throws Exception{
		return selectOne("LssCnlSvyCompt.selectSvyComptGeom",searchVO);
	}
	
	/**
	 * 공간정보 (유출구)
	 * @param geomMap
	 * @throws Exception
	 */
	public void insertSvyComptGeomVnarapnt(HashMap<String, Object> geomMap) throws Exception {
		insert("LssCnlSvyCompt.insertSvyComptGeomVnarapnt",geomMap);
	}
	
	/**
	 * 공간정보 (대피로)
	 * @param geomMap
	 * @throws Exception
	 */
	public void insertSvyComptGeomVnaralne(HashMap<String, Object> geomMap) throws Exception {
		insert("LssCnlSvyCompt.insertSvyComptGeomVnaralne",geomMap);
	}
	
	/**
	 * 공간정보 (폴리곤)
	 * @param geomMap
	 * @throws Exception
	 */
	public void insertSvyComptGeomVnaraPlgn(HashMap<String, Object> geomMap) throws Exception {
		insert("LssCnlSvyCompt.insertSvyComptGeomVnaraPlgn",geomMap);
	}
	
	/**
	 * 대상지 공간정보 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectSvyComptGeomPntLne(LssCnlSvyComptVO searchVO) throws Exception{
		return selectOne("LssCnlSvyCompt.selectSvyComptGeomPntLne",searchVO);
	}

   /**
	 * 대상지 공간정보 조회
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssCnlSvyComptVO> selectSvyComptGeomPlgn(LssCnlSvyComptVO searchVO) throws Exception{
		 return (List<LssCnlSvyComptVO>) list("LssCnlSvyCompt.selectSvyComptGeomPlgn", searchVO);
	}
	
	/**
	 * 현황도 이미지 경로 수정
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	public int updateComptLcMap(HashMap<String, Object> schMap) throws Exception {
		return update("LssCnlSvyCompt.updateComptLcMap", schMap);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("LssCnlSvyCompt.selectAuthorCheck",map);
	}
	
	/**
	 * 로그인한 유저의 권한있는 공유방 목록 조회
	 * @param map
	 * @throws Exception
	 */
	public List<String> selectAuthorCnrsList(HashMap<String, Object> map) throws Exception{
		return (List<String>) list("LssCnlSvyCompt.selectAuthorCnrsList",map);
	}
	
	/**
	 * 공간정보 삭제(포인트)
	 * @param geomMap
	 * @throws Exception
	 */
	public void deleteSvyComptGeomVnarapnt(HashMap<String, Object> geomMap) throws Exception {
		delete("LssCnlSvyCompt.deleteSvyComptGeomVnarapnt",geomMap);
	}
	/**
	 * 공간정보 삭제(라인)
	 * @param geomMap
	 * @throws Exception
	 */
	public void deleteSvyComptGeomVnaralne(HashMap<String, Object> geomMap) throws Exception {
		delete("LssCnlSvyCompt.deleteSvyComptGeomVnaralne",geomMap);
	}
	/**
	 * 공간정보 삭제(폴리곤)
	 * @param geomMap
	 * @throws Exception
	 */
	public void deleteSvyComptGeomVnaraPlgn(HashMap<String, Object> geomMap) throws Exception {
		delete("LssCnlSvyCompt.deleteSvyComptGeomVnaraPlgn",geomMap);
	}
}
