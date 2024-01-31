package or.sabang.sys.fck.mse.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.mse.service.FckMseComptVO;
import or.sabang.sys.service.LocReCreateVO;

@Repository("fckMseComptDAO")
public class FckMseComptDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectFckMseComptListTotCnt(FckMseComptVO searchVO) throws Exception{
		return (Integer)selectOne("fckMseComptDAO.selectFckMseComptListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<FckMseComptVO> selectFckMseComptList(FckMseComptVO searchVO) throws Exception{
		 return (List<FckMseComptVO>) list("fckMseComptDAO.selectFckMseComptList", searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public List<FckMseComptVO> selectFckMseComptDetail(FckMseComptVO ComptVO) throws Exception{
		return (List<FckMseComptVO>) list("fckMseComptDAO.selectFckMseComptDetail", ComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateFckMseCompt(List<FckMseComptVO> list) throws Exception {
		update("fckMseComptDAO.updateFckMseCompt",list);
	}
	
	/**
	 * 대상지 사진태그를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateFckMseComptPhotoTag(FckMseComptVO svyComptVO) throws Exception {
		update("fckMseComptDAO.updateFckMseComptPhotoTag",svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteMseCompt(FckMseComptVO ComptVO) throws Exception{
		delete("fckMseComptDAO.deleteMseCompt", ComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckMseComptMaxYear() throws Exception{
		return selectOne("fckMseComptDAO.selectFckMseComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckMseComptMaxMonth() throws Exception{
		return selectOne("fckMseComptDAO.selectFckMseComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckMseComptYear() throws Exception{
		return list("fckMseComptDAO.selectFckMseComptYear","");
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckMseProjDMS(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckMseComptDAO.selectFckMseProjDMS",map);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckMseProjBessel(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckMseComptDAO.selectFckMseProjBessel",map);
	}
	
	/**
	 * 공유방 명을 조회한다.
	 * @param ComptVO
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectFieldBookNm(FckMseComptVO ComptVO) throws Exception{
		return selectOne("fckMseComptDAO.selectFieldBookNm",ComptVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다.
	 * @param 
	 * @throws Exception
	 */
	public List<?> selectMseSvyComptListExcel(FckMseComptVO mseComptVO) throws Exception{
		return list("fckMseComptDAO.selectMseSvyComptListExcel",mseComptVO);
	}
	
	/**
	 * 대상지의 사진태그정볼를 조회한다.
	 * @param 
	 * @throws Exception
	 */
	public List<?> selectPhotoTagList(FckMseComptVO mseComptVO) throws Exception{
		return list("fckMseComptDAO.selectPhotoTagList",mseComptVO);
	}

	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("fckMseComptDAO.selectLastUpdateMinMaxDate",searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return list("fckMseComptDAO.updateLocReCreate",map);
	};
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @param mseComtVO
	 * @throws Exception
	 */
	void updateFckMseComptExcel(FckMseComptVO mseComtVO) throws Exception{
		update("fckMseComptDAO.updateFckMseComptExcel",mseComtVO);
	}
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param mseComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectMseLocReCreateSvyId(FckMseComptVO mseComptVO) throws Exception{
		return (List<EgovMap>) list("fckMseComptDAO.selectMseLocReCeateSvyId",mseComptVO);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("fckMseComptDAO.selectAuthorCheck",map);
	}
	
	/**
	 * 권한 코드 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCode(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("fckMseComptDAO.selectAuthorCode",map);
	}
}
