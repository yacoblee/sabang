package or.sabang.sys.fck.apr.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

@Repository("fckAprComptDAO")
public class FckAprComptDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectFckAprComptListTotCnt(FckAprComptVO searchVO) throws Exception{
		return (Integer)selectOne("fckAprComptDAO.selectFckAprComptListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<FckAprComptVO> selectFckAprComptList(FckAprComptVO searchVO) throws Exception{
		 return (List<FckAprComptVO>) list("fckAprComptDAO.selectFckAprComptList", searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public FckAprComptVO selectFckAprComptDetail(FckAprComptVO ComptVO) throws Exception{
		return selectOne("fckAprComptDAO.selectFckAprComptDetail", ComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateFckAprCompt(FckAprComptVO ComptVO) throws Exception {
		update("fckAprComptDAO.updateFckAprCompt",ComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteFckAprCompt(FckAprComptVO ComptVO) throws Exception{
		delete("fckAprComptDAO.deleteFckAprCompt", ComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckAprComptMaxYear() throws Exception{
		return selectOne("fckAprComptDAO.selectFckAprComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckAprComptMaxMonth() throws Exception{
		return selectOne("fckAprComptDAO.selectFckAprComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckAprComptYear() throws Exception{
		return list("fckAprComptDAO.selectFckAprComptYear","");
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprProjDMS(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckAprComptDAO.selectFckAprProjDMS",map);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprProjBessel(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckAprComptDAO.selectFckAprProjBessel",map);
	}
	
	/**
	 * 공유방 명을 조회한다.
	 * @param ComptVO
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectFieldBookNm(FckAprComptVO ComptVO) throws Exception{
		return selectOne("fckAprComptDAO.selectFieldBookNm",ComptVO);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) {
		return (List<EgovMap>) list("fckAprComptDAO.selectFckAprComptPhotoInfo", searchVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다.
	 * @param 
	 * @throws Exception
	 */
	public List<?> selectAprSvyComptListExcel(FckAprComptVO aprComptVO) throws Exception{
		return list("fckAprComptDAO.selectAprSvyComptListExcel",aprComptVO);
	}

	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("fckAprComptDAO.selectLastUpdateMinMaxDate",searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return list("fckAprComptDAO.updateLocReCreate",map);
	};
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @param aprComtVO
	 * @throws Exception
	 */
	void updateFckAprComptExcel(FckAprComptVO aprComtVO) throws Exception{
		update("fckAprComptDAO.updateFckAprComptExcel",aprComtVO);
	}
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param aprComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectAprLocReCreateSvyId(FckAprComptVO aprComptVO) throws Exception{
		return (List<EgovMap>) list("fckAprComptDAO.selectAprLocReCeateSvyId",aprComptVO);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("fckAprComptDAO.selectAuthorCheck",map);
	}
	
	/**
	 * 권한 코드 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCode(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("fckAprComptDAO.selectAuthorCode",map);
	}
}
