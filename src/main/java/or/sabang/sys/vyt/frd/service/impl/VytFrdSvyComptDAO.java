package or.sabang.sys.vyt.frd.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

@Repository("vytFrdSvyComptDAO")
public class VytFrdSvyComptDAO extends EgovComAbstractDAO {
   /**
	 * 대상지 목록을 조회한다.
     * @param searchVO
     * @return List(대상지 목록)
     * @throws Exception
     */
	public List<VytFrdSvyComptVO> selectFrdSvyComptList(VytFrdSvyComptVO searchVO) throws Exception{
		 return (List<VytFrdSvyComptVO>) list("VytFrdSvyCompt.selectFrdSvyComptList", searchVO);
	}
	
	/**
	 * 대상지 총 갯수를 조회한다.
     * @param searchVO
     * @return int(대상지 총 갯수)
     */
	public int selectFrdSvyComptListTotCnt(VytFrdSvyComptVO searchVO) throws Exception{
		return (Integer)selectOne("VytFrdSvyCompt.selectFrdSvyComptListTotCnt", searchVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFrdSvyComptMaxYear() throws Exception{
		return selectOne("VytFrdSvyCompt.selectFrdSvyComptMaxYear","");
	}
	
	/**
	 * 대상지 조사월최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFrdSvyComptMaxMonth() throws Exception{
		return selectOne("VytFrdSvyCompt.selectFrdSvyComptMaxMonth","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFrdSvyComptYear() throws Exception{
		return list("VytFrdSvyCompt.selectFrdSvyComptYear","");
	}
	
	/**
	 * 대상지관리 고유번호(smid)를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectSldNumber(HashMap<String, String> numberMap) throws Exception{
		return (List<EgovMap>) list("VytFrdSvyCompt.selectSldNumber",numberMap);
	}
	
	/**
	 * 대상지전자야장연계 고유번호(gid)를 조회한다.
	 * @throws Exception
	 */
	public String selectFieldBookNumber(HashMap<String, String> numberMap) throws Exception{
		return selectOne("VytFrdSvyCompt.selectFieldBookNumber",numberMap);
	}
	
	/**
	 * 조사완료 상세조회
	 * @throws Exception
	 */
	public VytFrdSvyComptVO selectFrdSvyComptDetail(VytFrdSvyComptVO searchVO) throws Exception{
		return selectOne("VytFrdSvyCompt.selectFrdSvyComptDetail",searchVO);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectVytFrdProjDMS(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("VytFrdSvyCompt.selectVytFrdProjDMS",map);
	}
	
	/**
	 * 타당성평가라인 중심 좌표를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectVytFrdLineCntPnt(HashMap<String, String> numberMap) throws Exception{
		return(List<EgovMap>) list("VytFrdSvyCompt.selectVytFrdLineCntPnt",numberMap);
	}
	
	/**
	 * 추가SHP파일 등록
	 * @throws Exception
	 */
	public void updateExtraShp(HashMap<String, Object> commandMap) throws Exception{
		update("VytFrdSvyCompt.updateExtraShp", commandMap);
	}
	
	/**
	 * 대상지를 수정한다.
	 * @param stripLand
	 * @throws Exception
	 */
	public void updateFrdSvyCompt(VytFrdSvyComptVO svyComptVO) throws Exception {
		update("VytFrdSvyCompt.updateFrdSvyCompt",svyComptVO);
	}
	
	/**
	 * 기 분석되어 저장된 분석 유형에 맞는 버퍼 사이즈를 조회한다. 
	 * @throws Exception
	 */
	public List<EgovMap> selectBufferSize(HashMap<String, Object> commandMap) throws Exception{
		return (List<EgovMap>) list("VytFrdSvyCompt.selectBufferSize",commandMap);
	}
	
	/**
	 * 분석결과 파일 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> selectDownloadAnalAll(VytFrdSvyComptVO searchVO) throws Exception{
		return selectList("VytFrdSvyCompt.selectDownloadAnalAll", searchVO);
	}
	
	/**
	 * 대상지를 삭제한다.
	 * @param stripLandVO
	 * @throws Exception
	 */
	public void deleteFrdSvyCompt(VytFrdSvyComptVO svyComptVO) throws Exception{
		delete("VytFrdSvyCompt.deleteFrdSvyCompt", svyComptVO);
	}
	public void deleteFrdAnalFile(VytFrdSvyComptVO svyComptVO) throws Exception{
		delete("VytFrdSvyCompt.deleteFrdAnalFile", svyComptVO);
	}
	public void deleteFrdLines(VytFrdSvyComptVO svyComptVO) throws Exception{
		delete("VytFrdSvyCompt.deleteFrdLines", svyComptVO);
	}
	 
	/**
	 * 사진목록 조회
	 * @throws Exception
	 */
	public VytFrdSvyComptVO selectDownloadPhotoList(VytFrdSvyComptVO searchVO) throws Exception{
		return selectOne("VytFrdSvyCompt.selectDownloadPhotoList",searchVO);
	}
	
	/*
	 * 대상지 포인트 SHP 조회
	 */
	public List<AnalFileVO> selectAnalPntInfo(HashMap<String, Object> analParameterMap) throws Exception{
		return selectList("VytFrdSvyCompt.selectAnalPntInfo", analParameterMap);
	}
	
	/**
	 * 조사항목 포인트 파일 일괄 다운로드
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> selectDownloadAnalPntAll(List<Integer> smidList) throws Exception{
		return selectList("VytFrdSvyCompt.selectDownloadAnalPntAll", smidList);
	}
	
	/*
	 * 조사완료 상세조회 엑셀다운로드
	 */
	public List<?> selectFrdSvyComptDetailExcel(String gid) throws Exception{
		return list("VytFrdSvyCompt.selectFrdSvyComptDetailExcel", gid);
	}
	
	/*
	 * 기존노선 조회
	 */
	public List<VytFrdSvyComptVO> selectCheckLines(VytFrdStripLandVO vo) throws Exception{
		return selectList("VytFrdSvyCompt.selectCheckLines", vo);
	}
	
	public void deleteExistLines(String smid) throws Exception{
		delete("VytFrdSvyCompt.deleteExistLines", smid);
	}
	
	/*
	 * 수정노선 등록시 tf_feis_frd_svycompt에 지오메트리 최신화
	 */
	public void updateNewGeomInfo(VytFrdStripLandVO vo) throws Exception{
		update("VytFrdSvyCompt.updateNewGeomInfo", vo);
	};
}
