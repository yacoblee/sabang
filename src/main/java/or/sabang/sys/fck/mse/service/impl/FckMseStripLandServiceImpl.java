package or.sabang.sys.fck.mse.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.mse.service.FckMseStripLandService;
import or.sabang.sys.fck.mse.service.FckMseStripLandVO;

@Service("fckMseStripLandService")
public class FckMseStripLandServiceImpl extends EgovAbstractServiceImpl implements FckMseStripLandService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="fckMseStripLandDAO")
	private FckMseStripLandDAO fckMseStripLandDAO;
	
	/** egovFileIdGnrService */
	@Resource(name="egovMseIdGnrService")
	private EgovIdGnrService egovMseIdGnrService;
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectFckMseSldTotCnt(FckMseStripLandVO searchVO) throws Exception {
        return fckMseStripLandDAO.selectFckMseSldTotCnt(searchVO);
	}

	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 목록을 조회한다.
	 */
	@Override
	public List<?> selectFckMseSldList(FckMseStripLandVO searchVO) throws Exception {
		return fckMseStripLandDAO.selectFckMseSldList(searchVO);
	}
	
	/**
	 * @param stripLandVO
	 * @throws Exception
	 * @description 대상지 정보를 상세조회한다.
	 */
	@Override
	public FckMseStripLandVO selectMseSldDetail(FckMseStripLandVO stripLandVO) throws Exception {
		return fckMseStripLandDAO.selectMseSldDetail(stripLandVO);
	}
	
	/**
	 * @param stripLandVO
	 * @throws Exception
	 * @description 대상지 통신모뎀번호 및 이동전화번호 목록을 조회한다.
	 */
	@Override
	public List<?> selectMseSldModemCellNumList(FckMseStripLandVO stripLandVO) throws Exception {
		return fckMseStripLandDAO.selectMseSldModemCellNumList(stripLandVO);
	}
	
	/**
	 * @param stripLandVO
	 * @throws Exception
	 * @description 대상지 지중경사계 목록을 조회한다.
	 */
	@Override
	public List<?> selectMseSldLicMeterList(FckMseStripLandVO stripLandVO) throws Exception {
		return fckMseStripLandDAO.selectMseSldLicMeterList(stripLandVO);
	}
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 등록
	 */
	public void insertMseSld(FckMseStripLandVO searchVO) throws Exception {
		
		//고유아이디 셋팅
		String sldId = egovMseIdGnrService.getNextStringId();
		searchVO.setSldId(sldId);
				
		fckMseStripLandDAO.insertMseSld(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description pnu_code 조회
	 */
	public List<EgovMap> selectPnuCode(FckMseStripLandVO searchVO) throws Exception {
		return fckMseStripLandDAO.selectPnuCode(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 수정
	 */
	public void updateMseSld(FckMseStripLandVO stripLandVO) throws Exception {		
		fckMseStripLandDAO.updateMseSld(stripLandVO);
	}
	
	/**
	 * @param searchVO
	 * @throws Exception
	 * @description 대상지 수정
	 */
	public void deleteMseSld(FckMseStripLandVO stripLandVO) throws Exception {		
		fckMseStripLandDAO.deleteMseSld(stripLandVO);
	}
}
