package or.sabang.sys.fck.pcs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.fck.apr.service.FckAprComptVO;
import or.sabang.sys.fck.pcs.service.FckPcsComptService;
import or.sabang.sys.fck.pcs.service.FckPcsComptVO;
import or.sabang.sys.service.LocReCreateVO;
import or.sabang.sys.spt.rpt.service.SptRptAprReportSvyComptVO;
import or.sabang.sys.spt.rpt.service.SptRptPcsReportSvyComptVO;

@Service("fckPcsComptService")
public class FckPcsComptServiceImpl extends EgovAbstractServiceImpl implements FckPcsComptService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FckPcsComptServiceImpl.class);
	
	@Resource(name="fckPcsComptDAO")
	private FckPcsComptDAO fckPcsComptDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<FckPcsComptVO> selectPcsSvyComptList(FckPcsComptVO searchVO) throws Exception {
		return fckPcsComptDAO.selectPcsSvyComptList(searchVO);
	}
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectPcsSvyComptListTotCnt(FckPcsComptVO searchVO) throws Exception {
        return fckPcsComptDAO.selectPcsSvyComptListTotCnt(searchVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 */
	@Override
	public String selectPcsSvyComptMaxYear() throws Exception {
		return fckPcsComptDAO.selectPcsSvyComptMaxYear();
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 */
	@Override
	public String selectPcsSvyComptMaxMonth() throws Exception {
		return fckPcsComptDAO.selectPcsSvyComptMaxMonth();
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 */
	@Override
	public List<?> selectPcsSvyComptYear() throws Exception{
		return fckPcsComptDAO.selectPcsSvyComptYear();
	}
	
	
	/**
	 * 마지막 업데이트 최소,최대 날짜
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public EgovMap selectLastUpdateMinMaxDate(LocReCreateVO searchVO) throws Exception{
		return fckPcsComptDAO.selectLastUpdateMinMaxDate(searchVO);
	}
	
	/**
	 * 대상지 조사 기간 별 위치도 재생성
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 12.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsComptService#updateLocReCreate(or.sabang.sys.service.LocReCreateVO)
	 */
	@Override
	public List<?> updateLocReCreate(LocReCreateVO searchVO) throws Exception {
		return fckPcsComptDAO.updateLocReCreate(searchVO);
	}
	
	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 13.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsComptService#selectAuthorCheck(java.util.HashMap)
	 */
	@Override
	public String selectAuthorCheck(HashMap<String, Object> commandMap) throws Exception {
		String result = fckPcsComptDAO.selectAuthorCheck(commandMap);
		if(result == null) result = "UNAUTHORD";
		return result;
	}
	
	/**
	 * 
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 13.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsComptService#selectFckPcsComptDetail(or.sabang.sys.fck.pcs.service.FckPcsComptVO)
	 */
	@Override
	public FckPcsComptVO selectFckPcsComptDetail(FckPcsComptVO searchVO) throws Exception {
		return fckPcsComptDAO.selectFckPcsComptDetail(searchVO);
	}
	
	/**
	 * 사진목록 조회
	 * @throws Exception
	 */
	@Override
	public FckPcsComptVO selectDownloadPhotoList(FckPcsComptVO searchVO) throws Exception{
		return fckPcsComptDAO.selectDownloadPhotoList(searchVO);
	}
	
	/**
	 * 조사완료지 수정
	 * @author DEVWORK
	 * @param comptVO
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsComptService#updateFckPcsCompt(or.sabang.sys.fck.pcs.service.FckPcsComptVO)
	 */
	@Override
	public void updateFckPcsCompt(FckPcsComptVO comptVO) throws Exception {
		
		// 특수기호 변경
		comptVO.setCmprsstrncncrt1(comptVO.getCmprsstrncncrt1().replaceAll("&quot;", "\""));
		comptVO.setCmprsstrncncrt2(comptVO.getCmprsstrncncrt2().replaceAll("&quot;", "\""));
		comptVO.setCmprsstrncncrt3(comptVO.getCmprsstrncncrt3().replaceAll("&quot;", "\""));
		comptVO.setCmprsstrncncrt4(comptVO.getCmprsstrncncrt4().replaceAll("&quot;", "\""));
		
		comptVO.setCrkdptcncrt1(comptVO.getCrkdptcncrt1().replaceAll("&quot;", "\""));
		comptVO.setCrkdptcncrt2(comptVO.getCrkdptcncrt2().replaceAll("&quot;", "\""));
		comptVO.setCrkdptcncrt3(comptVO.getCrkdptcncrt3().replaceAll("&quot;", "\""));
		comptVO.setCrkdptcncrt4(comptVO.getCrkdptcncrt4().replaceAll("&quot;", "\""));
		
		fckPcsComptDAO.updateFckPcsCompt(comptVO);
	}
	
	/**
	 * 조사완료지 삭제 
	 * @author DEVWORK
	 * @param comptVO
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsComptService#deleteFckPcsCompt(or.sabang.sys.fck.apr.service.FckAprComptVO)
	 */
	@Override
	public void deleteFckPcsCompt(FckPcsComptVO comptVO) throws Exception {
		fckPcsComptDAO.deleteFckPcsCompt(comptVO);
	}
	
	/**
	 * 조사완료지 엑셀 다운로드
	 * @author DEVWORK
	 * @param comptVo
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 * @see or.sabang.sys.fck.pcs.service.FckPcsComptService#selectFckPcsSvyComptListExcel(or.sabang.sys.fck.pcs.service.FckPcsComptVO)
	 */
	@Override
	public Map<?, ?> selectFckPcsSvyComptListExcel(FckPcsComptVO comptVo) throws Exception {
		List<?> _result = fckPcsComptDAO.selectFckPcsSvyComptListExcel(comptVo);
		
		Map<String, Object> _map = new HashMap<String, Object>();
		_map.put("resultList", _result);
		return _map;
	}
	
	/**
	 * 대상지를 엑셀다운로드한다. 
	 */
	@Override
	public List<EgovMap> selectPcsComptPhotoInfo(SptRptAprReportSvyComptVO searchVO) throws Exception {
		return fckPcsComptDAO.selectPcsComptPhotoInfo(searchVO);
	}
	
	/**
	 * 조사완료 첨부파일 업로드
	 * @author shcho
	 * @param fileMap
	 * @return
	 * @since 2024. 01. 02.
	 * @modified
	 */
	@Override
	public void insertPcsComptFile(HashMap<String, String> fileMap) throws Exception {
		fckPcsComptDAO.insertPcsComptFile(fileMap);
	}
	
	/**
	 * 조사완료 첨부파일 조회
	 * @author shcho
	 * @param fileMap
	 * @return
	 * @since 2024. 01. 02.
	 * @modified
	 */
	@Override
	public List<EgovMap> selectPcsComptFile(String gid) throws Exception {
		return fckPcsComptDAO.selectPcsComptFile(gid);
	}
	
	/**
	 * 조사완료 첨부파일 삭제
	 * @author shcho
	 * @param sn
	 * @return
	 * @since 2024. 01. 08.
	 * @modified
	 */
	@Override
	public void deletePcsComptFile(String sn) throws Exception {
		fckPcsComptDAO.deletePcsComptFile(sn);
	}
	
}
