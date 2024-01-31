package or.sabang.sys.vyt.frd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookItemVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;
import or.sabang.sys.vyt.frd.service.VytFrdSvyComptVO;

@Repository("vytFrdStripLandDAO")
public class VytFrdStripLandDAO extends EgovComAbstractDAO {
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytFrdStripLandVO> selectVytFrdSldList(VytFrdStripLandVO searchVO) throws Exception{
		return (List<VytFrdStripLandVO>) list("vytFrdStripLandDAO.selectVytFrdSldList", searchVO);
	}
	
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectVytFrdSldMaxYear() throws Exception{
		return selectOne("vytFrdStripLandDAO.selectVytFrdSldMaxYear","");
	}
	
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytFrdSldYear() throws Exception{
		return list("vytFrdStripLandDAO.selectVytFrdSldYear","");
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectVytFrdSldListTotCnt(VytFrdStripLandVO searchVO) throws Exception{
		return (Integer) selectOne("vytFrdStripLandDAO.selectVytFrdSldListTotCnt", searchVO);
	}
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 대상지 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public VytFrdStripLandVO selectVytFrdSldDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("vytFrdStripLandDAO.selectVytFrdSldDetail", map);
	};
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytFrdStripLandVO> selectVytFrdSldItemList(VytFrdStripLandVO searchVO) throws Exception{
		return (List<VytFrdStripLandVO>) list("vytFrdStripLandDAO.selectVytFrdSldItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectVytFrdSldItemListTotCnt(VytFrdStripLandVO searchVO) throws Exception{
		return (Integer) selectOne("vytFrdStripLandDAO.selectVytFrdSldItemListTotCnt", searchVO);
	};
	
	/**
	 * 대상지 사업 업로드
	 */
	public String insertSldSpce(VytFrdStripLandVO stripLandVO) throws Exception{
		insert("vytFrdStripLandDAO.insertSldSpce", stripLandVO);
		return stripLandVO.getId();
	}
	
	/*
	 * 대상지번호 없는 조회
	 */
	public List<VytFrdStripLandVO> selectNoSldId() throws Exception{
		return (List<VytFrdStripLandVO>) list("vytFrdStripLandDAO.selectNoSldId","");
	};
	
	/**
	 * 대상지 번호 부여
	 * @return
	 * @throws Exception
	 */
	public void updateNoSldId(HashMap<String, Object> commandMap) throws Exception{
		update("vytFrdStripLandDAO.updateNoSldId", commandMap);
	}
	
	/**
	 * 대상지 삭제
	 * @return
	 * @throws Exception
	 */
	public void deleteSldDetail(HashMap<String, Object> map) throws Exception{
		delete("vytFrdStripLandDAO.deleteSldDetail", map);
		delete("vytFrdStripLandDAO.deleteSldAll", map);
	}
	
	/**
	 * 대상지 단건 조회
	 * @return
	 * @throws Exception
	 */
	public VytFrdStripLandVO selectSldDetailOne(String smid) throws Exception{
		return selectOne("vytFrdStripLandDAO.selectSldDetailOne",smid);
	}
	
	/**
	 * 대상지 단건 수정
	 * @return
	 * @throws Exception
	 */
	public void updateSldDetailOne(VytFrdStripLandVO searchVO) throws Exception{
		update("vytFrdStripLandDAO.updateSldDetailOne", searchVO);
	}
	
	/**
	 * 대상지 단건 삭제
	 * @return
	 * @throws Exception
	 */
	public void deleteSldDetailOne(HashMap<String, Object> map) throws Exception{
		delete("vytFrdStripLandDAO.deleteSldDetailOne", map);
		delete("vytFrdStripLandDAO.deleteFrdAnalFile", map);
	}
	
	/**
	 * 대상지 분석 센터포인트 조회
	 * @return
	 * @throws Exception
	 */
	public List<VytFrdStripLandVO> selectCenterPnt(HashMap<String, Object> commandMap) throws Exception{
		return selectList("vytFrdStripLandDAO.selectCenterPnt", commandMap);
	}
	
	/**
	 * 대상지 분석 시군구 코드 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectSggOne(Map<String, Object> coordMap) throws Exception{
		return selectList("vytFrdStripLandDAO.selectSggOne", coordMap);
	}
	
	/**
	 * 대상지 분석 해당 읍면동 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectEmdList(String sggCode) throws Exception{
		return selectList("vytFrdStripLandDAO.selectEmdList", sggCode);
	}
	/**
	 * 대상지 분석 문화재 센터 id조회
	 * @return
	 * @throws Exception
	 */
	public String selectCultureSmidList(String smid) throws Exception{
		return selectOne("vytFrdStripLandDAO.selectCultureSmidList", smid);
	}
	/**
	 * 대상지 분석 문화재 센터 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCultureCenterPnt(List<Integer> list) throws Exception{
		return selectList("vytFrdStripLandDAO.selectCultureCenterPnt", list);
	}
	
	/**
	 * 대상지 분석결과 저장
	 * @param vo
	 * @throws Exception
	 */
	public void insertAnalFile(AnalFileVO vo) throws Exception {
		insert("vytFrdStripLandDAO.insertAnalFile", vo);
	}
	
	/*
	 * 대상지 분석이미지 조회
	 */
	public List<AnalFileVO> selectAnalImg(HashMap<String, Object> analParameterMap) throws Exception{
		return selectList("vytFrdStripLandDAO.selectAnalImg", analParameterMap);
	}
	
	/*
	 * 문화재 목록 저장
	 */
	public void updateCultureList(HashMap<String, String> culList) throws Exception {
		update("vytFrdStripLandDAO.updateCultureList", culList);
	}
	
	/**
	 * 문화재 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCultureName(List<Integer> list) throws Exception{
		return selectList("vytFrdStripLandDAO.selectCultureName", list);
	}
	
	public AnalFileVO selectAnalFileDownDetail(String sn) throws Exception{
		return (AnalFileVO) selectOne("vytFrdStripLandDAO.selectAnalFileDownDetail", sn);
	}
	/*
	 * 필지조회
	 */
	public List<VytFrdStripLandVO> selectParcelList(String smid) throws Exception{
		return (List<VytFrdStripLandVO>) list("vytFrdStripLandDAO.selectParcelList", smid);
	}
	
	/**
	 * 대상지명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectVytFrdCheckSldName(String sldNm) throws Exception{
		return (int) selectOne("vytFrdStripLandDAO.selectVytFrdCheckSldName",sldNm);
	}
	
	/**
	 * 다른 노선 smid 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectAnotherSmid(String sldId) throws Exception{
		return selectList("vytFrdStripLandDAO.selectAnotherSmid", sldId);
	}
	
	/**
	 * 분석결과 파일 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> selectDownloadAnalAll(VytFrdStripLandVO searchVO) throws Exception{
		return selectList("vytFrdStripLandDAO.selectDownloadAnalAll", searchVO);
	}
}
