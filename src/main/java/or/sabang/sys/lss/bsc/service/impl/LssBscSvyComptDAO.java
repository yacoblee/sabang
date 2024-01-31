package or.sabang.sys.lss.bsc.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.bsc.service.LssBscSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

@Repository("lssBscSvyComptDAO")
public class LssBscSvyComptDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectBscSvyComptListTotCnt(LssBscSvyComptVO searchVO) throws Exception{
		return (Integer)selectOne("LssBscSvyCompt.selectBscSvyComptListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssBscSvyComptVO> selectBscSvyComptList(LssBscSvyComptVO searchVO) throws Exception{
		 return (List<LssBscSvyComptVO>) list("LssBscSvyCompt.selectBscSvyComptList", searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public LssBscSvyComptVO selectBscSvyComptDetail(LssBscSvyComptVO svyComptVO) throws Exception{
		return selectOne("LssBscSvyCompt.selectBscSvyComptDetail", svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateBscSvyCompt(LssBscSvyComptVO svyComptVO) throws Exception {
		update("LssBscSvyCompt.updateBscSvyCompt",svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteBscSvyCompt(LssBscSvyComptVO svyComptVO) throws Exception{
		delete("LssBscSvyCompt.deleteBscSvyCompt", svyComptVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public List<?> selectBscSvyComptListExcel(LssBscSvyComptVO svyComptVO) throws Exception{
		return list("LssBscSvyCompt.selectBscSvyComptListExcel",svyComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectBscSvyComptMaxYear() throws Exception{
		return selectOne("LssBscSvyCompt.selectBscSvyComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectBscSvyComptMaxMonth() throws Exception{
		return selectOne("LssBscSvyCompt.selectBscSvyComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectBscSvyComptYear() throws Exception{
		return list("LssBscSvyCompt.selectBscSvyComptYear","");
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @throws Exception
	 */
	void updateBscSvyComptExcel(LssBscSvyComptVO svyComptVO) throws Exception{
		update("LssBscSvyCompt.updateBscSvyComptExcel", svyComptVO);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectBscSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) {
		return (List<EgovMap>) list("LssBscSvyCompt.selectBscSvyComptPhotoInfo", searchVO);
	};
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("LssBscSvyCompt.selectLastUpdateMinMaxDate",searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return list("LssBscSvyCompt.updateLocReCreate",map);
	};
	
	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectBscLocReCreateSvyId(LssBscSvyComptVO svyComptVO) throws Exception{
		return (List<EgovMap>) list("LssBscSvyCompt.selectBscLocReCreateSvyId",svyComptVO);
	};
	
	/**
	 * 조사완료 현장사진 일괄수정
	 * @param svyComptVO
	 * @throws Exception
	 */
	public void updateBscSvyComptPhotoList(LssBscSvyComptVO searchVO) throws Exception {
		update("LssBscSvyCompt.updateBscSvyComptPhotoList",searchVO);
	}
	
	/**
	 * 현장사진 널값을 조회한다.
	 * @param searchVO
	 * @return 
	 * @throws Exception
	 */
	public EgovMap selectSvyPhotoNullList(LssBscSvyComptVO searchVO) throws Exception{
		 return selectOne("LssBscSvyCompt.selectSvyPhotoNullList", searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 현장사진 동기화
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updatePhotoList(LssBscSvyComptVO map) throws Exception{
		return list("LssBscSvyCompt.updatePhotoList",map);
	};
}
