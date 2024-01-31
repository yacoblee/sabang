package or.sabang.sys.fck.mse.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.mse.service.FckMseStripLandVO;

@Repository("fckMseStripLandDAO")
public class FckMseStripLandDAO extends EgovComAbstractDAO {
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectFckMseSldTotCnt(FckMseStripLandVO searchVO) throws Exception{
		return (Integer)selectOne("fckMseStripLandDAO.selectFckMseSldTotCnt", searchVO);
	}

   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<?> selectFckMseSldList(FckMseStripLandVO searchVO) throws Exception{
		 return list("fckMseStripLandDAO.selectFckMseSldList", searchVO);
	}
	
	/**
	 * 대상지 계측장비 유무를 조회한다.
	 * @param stripLandVO
	 * @return 
	 * @throws Exception
	 */
	public FckMseStripLandVO selectMseSensorEnnc(FckMseStripLandVO stripLandVO) throws Exception{
		return selectOne("fckMseStripLandDAO.selectMseSensorEnnc", stripLandVO);
	}
	
	/**
	 * 대상지 정보를 상세조회한다.
	 * @param stripLandVO
	 * @return 
	 * @throws Exception
	 */
	public FckMseStripLandVO selectMseSldDetail(FckMseStripLandVO stripLandVO) throws Exception{
		return selectOne("fckMseStripLandDAO.selectMseSldDetail", stripLandVO);
	}
	
	/**
	 * 대상지 통신모뎀번호 및 이동전화번호 목록을 조회한다.
     * @param stripLandVO
     * @return 
     * @throws Exception
     */
	public List<?> selectMseSldModemCellNumList(FckMseStripLandVO stripLandVO) throws Exception{
		 return list("fckMseStripLandDAO.selectMseSldModemCellNumList", stripLandVO);
	}
	
	/**
	 * 대상지 지중경사계 목록을 조회한다.
	 * @param stripLandVO
	 * @return 
	 * @throws Exception
	 */
	public List<?> selectMseSldLicMeterList(FckMseStripLandVO stripLandVO) throws Exception{
		return list("fckMseStripLandDAO.selectMseSldLicMeterList", stripLandVO);
	}
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 등록
	 */
	void insertMseSld(FckMseStripLandVO searchVO) throws Exception{
		insert("fckMseStripLandDAO.insertMseSld", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description pnu_code 조회
	 */
	public List<EgovMap> selectPnuCode(FckMseStripLandVO searchVO) throws Exception {
		return (List<EgovMap>) list("fckMseStripLandDAO.selectPnuCode", searchVO);
	}
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 수정
	 */
	void updateMseSld(FckMseStripLandVO stripLandVO) throws Exception{
		update("fckMseStripLandDAO.updateMseSld", stripLandVO);
	};
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 수정
	 */
	void deleteMseSld(FckMseStripLandVO stripLandVO) throws Exception{
		delete("fckMseStripLandDAO.deleteMseSld", stripLandVO);
	};
}
