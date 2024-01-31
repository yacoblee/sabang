package or.sabang.sys.lss.lcp.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.mng.log.clg.service.LoginLog;
import or.sabang.sys.ext.service.LocImgInfoVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLand;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.sys.service.ZonalStatisticVO;

@Repository("lssLcpSvyStripLandDAO")
public class LssLcpSvyStripLandDAO extends EgovComAbstractDAO {
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectLssLcpSvySldTotCnt(LssLcpSvyStripLandVO searchVO) throws Exception{
		return (Integer)selectOne("lssLcpSvyStripLandDAO.selectLssLcpSvySldTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<?> selectLssLcpSvySldList(LssLcpSvyStripLandVO searchVO) throws Exception{
		 return list("lssLcpSvyStripLandDAO.selectLssLcpSvySldList", searchVO);
	}
	
	/**
	 * 대상지 목록을 조회한다.
	 * @param searchVO
	 * @return List(대상지 목록)
	 * @throws Exception
	 */
	public List<EgovMap> test() throws Exception{
		return (List<EgovMap>) list("lssLcpSvyStripLandDAO.test", "");
	}
	
	/**
	 * 대상지 정보 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectSvySldInfoCnt(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		return (Integer)selectOne("lssLcpSvyStripLandDAO.selectSvySldInfoCnt", stripLandVO);
	}
	
	/**
	 * 대상지를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public List<?> selectSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		return list("lssLcpSvyStripLandDAO.selectSvySldInfo", stripLandVO);
	}
	
	/**
	 * 대상지 정보를 상세조회한다.
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldInfoDetail(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		return selectOne("lssLcpSvyStripLandDAO.selectSvySldInfoDetail", stripLandVO);
	}
	
	/**
	 * 대상지를 일괄등록한다.
	 * @param list
	 * @throws Exception
	 */
	public void insertUploadStripLand(List<LssLcpSvyStripLand> list) throws Exception {
		insert("lssLcpSvyStripLandDAO.insertUploadStripLand", list);
	}
	
	/**
	 * 대상지를 등록한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void insertLssLcpSvyStripLand(LssLcpSvyStripLand stripLand) throws Exception {
		insert("lssLcpSvyStripLandDAO.insertLssLcpSvyStripLand",stripLand);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateLssLcpSvyStripLand(LssLcpSvyStripLand stripLand) throws Exception {
		update("lssLcpSvyStripLandDAO.updateLssLcpSvyStripLand",stripLand);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteLssLcpSvyStripLand(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		delete("lssLcpSvyStripLandDAO.deleteLssLcpSvyStripLand", stripLandVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssLcpSvyStripLandMaxYear() throws Exception{
		return selectOne("lssLcpSvyStripLandDAO.selectLssLcpSvyStripLandMaxYear", "");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssLcpSvyStripLandYear() throws Exception {
		return list("lssLcpSvyStripLandDAO.selectLssLcpSvyStripLandYear", "");
	}
	
	/**
	 * 조사대상지 등록정보 중복체크
	 * @throws Exception
	 */
	public int dplctCheckSvySldInfo(HashMap<String, Object> sldMap) throws Exception{
		return (Integer)selectOne("lssLcpSvyStripLandDAO.dplctCheckSvySldInfo", sldMap);
	};
	
	/**
	 * 조사대상지 등록정보를 등록한다.
	 * @throws Exception
	 */
	public void insertSvySldRegInfo(HashMap<String, Object> sldMap) throws Exception{
		insert("lssLcpSvyStripLandDAO.insertSvySldRegInfo", sldMap);
	};
	
	/**
     * 조사대상지 정보를 조회한다.
     * @throws Exception
     */
    public List<EgovMap> selectRankInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
	return (List<EgovMap>) list("lssLcpSvyStripLandDAO.selectRankInfo", stripLandVO);
    }
    
    /**
     * 행정구역별 미조사된 대상지 건수를 조회한다.
     * @throws Exception
     */
    public EgovMap selectRankInfoCnt() throws Exception {
    	return selectOne("lssLcpSvyStripLandDAO.selectRankInfoCnt","");
    }
    
    /**
     * 행정구역별 작년조사된 대상지 건수를 조회한다.
     * @throws Exception
     */
    public EgovMap selectLastRankInfoCnt() throws Exception {
    	return selectOne("lssLcpSvyStripLandDAO.selectLastRankInfoCnt","");
    }
    
    /**
	 * 랭크데이터를 수정한다.
	 * @throws Exception
	 */
	public void updateRankInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		update("lssLcpSvyStripLandDAO.updateRankInfo",stripLandVO);
	}
	
	/**
	 * 조사대상지 정보를 등록한다.
	 * @throws Exception
	 */
    public void insertSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		update("lssLcpSvyStripLandDAO.insertSvySldInfo", stripLandVO);
	};
	
	/**
	 * 조사대상지 고도정보를 등록한다.
	 * @param list
	 * @throws Exception
	 */
    public void updateSvySldElevInfo(List<ZonalStatisticVO> list) throws Exception{
    	update("lssLcpSvyStripLandDAO.updateSvySldElevInfo", list);
	};
	
	 /**
	 * 조사대상지 조사지 개수 정보 조회 
	 * @throws Exception
	 */
	public EgovMap selectSvySldCntInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		return selectOne("lssLcpSvyStripLandDAO.selectSvySldCntInfo", stripLandVO);
	}
		
	/**
	 * 조사대상지 경사정보를 등록한다.
	 * @param list
	 * @throws Exception
	 */
    public void updateSvySldSlopInfo(List<ZonalStatisticVO> list) throws Exception{
    	update("lssLcpSvyStripLandDAO.updateSvySldSlopInfo", list);
	};
	
	/**
	 * 조사대상지 토심정보를 등록한다.
	 * @param list
	 * @throws Exception
	 */
    public void updateSvySldSldInfo(List<ZonalStatisticVO> list) throws Exception{
    	update("lssLcpSvyStripLandDAO.updateSvySldSldInfo", list);
	};
	
	/**
     * 조사대상지 등록정보 상세조회
     * @throws Exception
     */
    public EgovMap selectSvySldRegInfoDetail(LssLcpSvyStripLandVO stripLandVO) throws Exception {
	return selectOne("lssLcpSvyStripLandDAO.selectSvySldRegInfoDetail", stripLandVO);
    }
    
    /**
	 * 조사대상지 시도별 건수 조회
	 * @throws Exception
	 */
	public List<?> selectSvySldCtrdCnt(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		return list("lssLcpSvyStripLandDAO.selectSvySldCtrdCnt", stripLandVO);
	}
	
	/**
	 * 조사대상지 등록정보를 수정한다.
	 * @throws Exception
	 */
	public void updateSvySldRegInfo(String sldId) throws Exception {
		update("lssLcpSvyStripLandDAO.updateSvySldRegInfo", sldId);
	}
	
	/**
	 * 조사대상지 정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteSvySldInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		delete("lssLcpSvyStripLandDAO.deleteSvySldInfo", stripLandVO);
	}
	
	/**
	 * 랭크데이터를 수정한다.
	 * @throws Exception
	 */
	public void updateRankDelInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		update("lssLcpSvyStripLandDAO.updateRankDelInfo",stripLandVO);
	}
	
	/**
	 * 조사대상지 임상도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldImInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return selectOne("lssLcpSvyStripLandDAO.selectSvySldImInfo",stripLandVO);
	}

	
	/**
	 * 조사대상지 입지도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldIjInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return selectOne("lssLcpSvyStripLandDAO.selectSvySldIjInfo",stripLandVO);
	}
	
	/**
	 * 조사대상지 지질도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpSvyStripLandVO selectSvySldGlInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return selectOne("lssLcpSvyStripLandDAO.selectSvySldGlInfo",stripLandVO);
	}
	
	/**
	 * 랭크 정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteRank(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		update("lssLcpSvyStripLandDAO.deleteRank", stripLandVO);
	}
	
	/**
	 * 제보 정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteGvf(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		update("lssLcpSvyStripLandDAO.deleteGvf", stripLandVO);
	}
	
	/**
	 * 조사대상지 등록정보를 삭제한다.
	 * @throws Exception
	 */
	public void deleteSvySldRegInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		delete("lssLcpSvyStripLandDAO.deleteSvySldRegInfo", stripLandVO);
	}
	
	/**
	 * 조사대상지 등록정보방 목록을 조회한다.
     * @param 
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<?> selectSvySldRegInfoList() throws Exception{
		 return list("lssLcpSvyStripLandDAO.selectSvySldRegInfoList","");
	}
	
	public List<EgovMap> selectSvysldGidInfo(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		 return (List<EgovMap>) list("lssLcpSvyStripLandDAO.selectSvysldGidInfo",stripLandVO);
	}
	public void updateSvySldGid(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		update("lssLcpSvyStripLandDAO.updateSvySldGid",stripLandVO);
	}
	
	public List<?> selectLcpSvySldListExcel(LssLcpSvyStripLandVO vo) throws Exception{
		return list("lssLcpSvyStripLandDAO.selectLcpSvySldListExcel",vo);
	}
}
