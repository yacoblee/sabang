package or.sabang.sys.lss.lcp.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;

@Repository("lssLcpSvyComptDAO")
public class LssLcpSvyComptDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectLcpSvyComptListTotCnt(LssLcpSvyComptVO searchVO) throws Exception{
		return (Integer)selectOne("LssLcpSvyCompt.selectLcpSvyComptListTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<LssLcpSvyComptVO> selectLcpSvyComptList(LssLcpSvyComptVO searchVO) throws Exception{
		 return (List<LssLcpSvyComptVO>) list("LssLcpSvyCompt.selectLcpSvyComptList", searchVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public LssLcpSvyComptVO selectLcpSvyComptDetail(LssLcpSvyComptVO svyComptVO) throws Exception{
		return selectOne("LssLcpSvyCompt.selectLcpSvyComptDetail", svyComptVO);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateLcpSvyCompt(LssLcpSvyComptVO svyComptVO) throws Exception {
		update("LssLcpSvyCompt.updateLcpSvyCompt",svyComptVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteLcpSvyCompt(LssLcpSvyComptVO svyComptVO) throws Exception{
		delete("LssLcpSvyCompt.deleteLcpSvyCompt", svyComptVO);
	}
	
	/**
	 * 대상지를 엑셀다운로드한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public List<?> selectLcpSvyComptListExcel(LssLcpSvyComptVO svyComptVO) throws Exception{
		return list("LssLcpSvyCompt.selectLcpSvyComptListExcel",svyComptVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLcpSvyComptMaxYear() throws Exception{
		return selectOne("LssLcpSvyCompt.selectLcpSvyComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월 최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLcpSvyComptMaxMonth() throws Exception{
		return selectOne("LssLcpSvyCompt.selectLcpSvyComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLcpSvyComptYear() throws Exception{
		return list("LssLcpSvyCompt.selectLcpSvyComptYear","");
	}
	
	/**
	 * 조사완료지 엑셀을 재업로드하여 데이터를 갱신한다.
	 * @throws Exception
	 */
	void updateLcpSvyComptExcel(LssLcpSvyComptVO svyComptVO) throws Exception{
		update("LssLcpSvyCompt.updateLcpSvyComptExcel", svyComptVO);
	}
	
	/**
	 * 대상지 현장사진정보를 불러온다.
	 * @param searchVO
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSvyComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) {
		return (List<EgovMap>) list("LssLcpSvyCompt.selectLcpSvyComptPhotoInfo", searchVO);
	};

	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLssLcpProjDMS(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("LssLcpSvyCompt.selectLssLcpProjDMS",map);
	}
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLssLcpProjBessel(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("LssLcpSvyCompt.selectLssLcpProjBessel",map);
	}

	/**
	 * 엑셀 재업로드 파라메터 전송값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpLocReCreateSvyId(LssLcpSvyComptVO svyComptVO) throws Exception{
		return (List<EgovMap>) list("LssLcpSvyCompt.selectLcpLocReCreateSvyId",svyComptVO);
	};
	
	/**
	 * 땅밀림 징후 값 조회
	 * @param svyComptVO
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSttusList(LssLcpSvyComptVO svyComptVO) throws Exception{
		return (List<EgovMap>) list("LssLcpSvyCompt.selectLcpSttusList",svyComptVO);
	};
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return selectOne("LssLcpSvyCompt.selectLastUpdateMinMaxDate",searchVO);
	}
	
	/**
	 * 기간 별 위치도 파라메터 전송값 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<?> updateLocReCreate(LocReCreateVO map) throws Exception{
		return list("LssLcpSvyCompt.updateLocReCreate",map);
	};
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("LssLcpSvyCompt.selectAuthorCheck",map);
	}
}
