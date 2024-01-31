package or.sabang.sys.fck.pcs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptPcsReportSvyComptVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

@Repository("fckPcsComptDAO")
public class FckPcsComptDAO extends EgovComAbstractDAO {

	/**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<FckPcsComptVO> selectPcsSvyComptList(FckPcsComptVO searchVO) throws Exception{
		 return (List<FckPcsComptVO>) list("fckPcsComptDAO.selectPcsSvyComptList", searchVO);
	}
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectPcsSvyComptListTotCnt(FckPcsComptVO searchVO) throws Exception{
		return (Integer)selectOne("fckPcsComptDAO.selectPcsSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectPcsSvyComptMaxYear() throws Exception{
		return selectOne("fckPcsComptDAO.selectPcsSvyComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectPcsSvyComptMaxMonth() throws Exception{
		return selectOne("fckPcsComptDAO.selectPcsSvyComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectPcsSvyComptYear() throws Exception{
		return list("fckPcsComptDAO.selectPcsSvyComptYear","");
	}
	
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("fckPcsComptDAO.selectLastUpdateMinMaxDate",searchVO);
	}

	/**
	 * 대상지 조사 기간 별 위치도 재생성
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 12.
	 * @modified
	 */
	public List<?> updateLocReCreate(LocReCreateVO searchVO) throws Exception{
		return list("fckPcsComptDAO.updateLocReCreate",searchVO);
	}

	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 13.
	 * @modified
	 */
	public String selectAuthorCheck(HashMap<String, Object> commandMap) throws Exception{
		return (String) selectOne("fckPcsComptDAO.selectAuthorCheck",commandMap);
	}

	/**
	 * 
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 13.
	 * @modified
	 */
	public FckPcsComptVO selectFckPcsComptDetail(FckPcsComptVO searchVO) throws Exception{
		return selectOne("fckPcsComptDAO.selectFckPcsComptDetail", searchVO);
	} 
	
	/**
	 * 사진목록 조회
	 * @throws Exception
	 */
	public FckPcsComptVO selectDownloadPhotoList(FckPcsComptVO searchVO) throws Exception{
		return selectOne("fckPcsComptDAO.selectDownloadPhotoList", searchVO);
	}

	/**
	 * 조사완료지 수정
	 * @author DEVWORK
	 * @param comptVO
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 */
	public void updateFckPcsCompt(FckPcsComptVO comptVO)  throws Exception{
		update("fckPcsComptDAO.updateFckPcsCompt",comptVO);
	}

	/**
	 * 조사완료지 삭제
	 * @author DEVWORK
	 * @param comptVO
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 */
	public void deleteFckPcsCompt(FckPcsComptVO comptVO) throws Exception{
		delete("fckPcsComptDAO.deleteFckPcsCompt", comptVO);
	}

	/**
	 * 조사완료지 엑셀 다운로드
	 * @author DEVWORK
	 * @param comptVo
	 * @return
	 * @since 2023. 12. 14.
	 * @modified
	 */
	public List<?> selectFckPcsSvyComptListExcel(FckPcsComptVO comptVo) throws Exception{
		return list("fckPcsComptDAO.selectFckPcsSvyComptListExcel",comptVo);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectPcsComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return (List<EgovMap>) list("fckPcsComptDAO.selectPcsComptPhotoInfo",searchVO);
	}
	
	/**
	 * 조사완료 첨부파일 업로드
	 * @author shcho
	 * @param fileMap
	 * @return
	 * @since 2024. 01. 02.
	 * @modified
	 */
	public void insertPcsComptFile(HashMap<String, String> fileMap) throws Exception {
		insert("fckPcsComptDAO.insertPcsComptFile", fileMap);
	}
	
	/**
	 * 조사완료 첨부파일 조회
	 * @author shcho
	 * @param gid
	 * @return
	 * @since 2024. 01. 02.
	 * @modified
	 */
	public List<EgovMap> selectPcsComptFile(String gid) throws Exception {
		return (List<EgovMap>) list("fckPcsComptDAO.selectPcsComptFile", gid);
	}
	
	/**
	 * 조사완료 첨부파일 삭제
	 * @author shcho
	 * @param sn
	 * @return
	 * @since 2024. 01. 08.
	 * @modified
	 */
	public void deletePcsComptFile(String sn) throws Exception {
		delete("fckPcsComptDAO.deletePcsComptFile", sn);
	}
	
}
