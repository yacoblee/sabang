package or.sabang.sys.vyt.ecb.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.vyt.ecb.service.VytEcbStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbWorkVO;

@Repository("vytEcbSldManageDAO")
public class VytEcbSldManageDAO extends EgovComAbstractDAO {
	/**
	 * 사업 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectVytEcbWorkMaxYear() throws Exception{
		return selectOne("vytEcbSldManageDAO.selectVytEcbWorkMaxYear","");
	}
	
	/**
	 * 사업 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytEcbWorkYear() throws Exception{
		return list("vytEcbSldManageDAO.selectVytEcbWorkYear","");
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytEcbWorkVO> selectVytEcbWorkList(VytEcbWorkVO searchVO) throws Exception{
		return (List<VytEcbWorkVO>) list("vytEcbSldManageDAO.selectVytEcbWorkList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 목록 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectVytEcbWorkListTotCnt(VytEcbWorkVO searchVO) throws Exception{
		return (Integer) selectOne("vytEcbSldManageDAO.selectVytEcbWorkListTotCnt", searchVO);
	};
	
	/**
	 * @param vytEcbWorkVO
	 * @throws Exception
	 * @description 사업 등록
	 */
	public String insertWork(VytEcbWorkVO vytEcbWorkVO) throws Exception{
		insert("vytEcbSldManageDAO.insertWork", vytEcbWorkVO);
		return vytEcbWorkVO.getId();
	};
	
	/**
	 * 사업 대상지 등록
	 * @param vo
	 * @throws Exception
	 */
	void insertStripLandVO(VytEcbStripLandVO vo) throws Exception{
		insert("vytEcbSldManageDAO.insertStripLandVO", vo);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 삭제
	 */
	void deleteWork(HashMap<String, Object> map) throws Exception{
		delete("vytEcbSldManageDAO.deleteWork", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 사업 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public VytEcbWorkVO selectVytEcbWorkDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("vytEcbSldManageDAO.selectVytEcbWorkDetail", map);
	};
	
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 목록 조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytEcbStripLandVO> selectVytEcbWorkSldList(VytEcbWorkVO searchVO) throws Exception{
		return (List<VytEcbStripLandVO>) list("vytEcbSldManageDAO.selectVytEcbWorkSldList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 목록 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectVytEcbWorkSldListTotCnt(VytEcbWorkVO searchVO) throws Exception{
		return (Integer) selectOne("vytEcbSldManageDAO.selectVytEcbWorkSldListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 사업 대상지 목록 일괄삭제
	 */
	void deleteWorkAllSld(HashMap<String, Object> map) throws Exception{
		delete("vytEcbSldManageDAO.deleteWorkAllSld", map);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 사업 대상지 수정조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytEcbStripLandVO> selectVytEcbWorkSldListView(VytEcbWorkVO searchVO) throws Exception {
		return (List<VytEcbStripLandVO>) list("vytEcbSldManageDAO.selectVytEcbWorkSldListView", searchVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 사업 조사데이터 삭제
	 */
	void deleteWorkItem(HashMap<String, Object> map) throws Exception{
		delete("vytEcbSldManageDAO.deleteWorkItem", map);
	};
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



//	
//	/**
//	 * @param map
//	 * @return
//	 * @description 공유방 비밀번호 조회
//	 */
//	@SuppressWarnings("unchecked")
//	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
//		return (List<EgovMap>) list("fckAprFieldBookDAO.selectCnrsSpcePwd", map);
//	};
//	
//	/**
//	 * @param map
//	 * @return
//	 * @description 공유방 목록조회 테스트
//	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
//		return (List<FckAprFieldBookVO>) list("fckAprFieldBookDAO.selectCnrsSpceList", map);
//	};
//	
//	/**
//	 * @param map
//	 * @return
//	 * @description 공유방 단건조회 테스트
//	 */
//	@SuppressWarnings("unchecked")
//	public FckAprFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (FckAprFieldBookVO) selectOne("fckAprFieldBookDAO.selectCnrsSpceInfo", map);
//	};
//	
//	/**
//	 * @param mst_id
//	 * @return
//	 * @description 공유방 목록 여부
//	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
//	
//	/**
//	 * @param map
//	 * @return
//	 * @description 공유방 다운로드 테스트
//	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception{
//		return (List<FckAprFieldBookItemVO>) list("fckAprFieldBookDAO.selectCnrsSpceDownload", map);
//	};
//	
//	/**
//	 * @param map
//	 * @return
//	 * @description 공유방 조사데이터 조회 테스트
//	 */ 
//	@SuppressWarnings("unchecked")
//	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception{
//		return (List<EgovMap>) list("fckAprFieldBookDAO.selectCnrsSpceItem", map);
//	};
//	
//	/**
//	 * @param mst_id
//	 * @return
//	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
//	 */
//	public int selectCnrsSpceMvl(int mst_id) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectCnrsSpceMvl", mst_id);
//	}
//	
//	/**
//	 * @param itemVO
//	 * @return
//	 * @description 공유방 조사데이터 업데이트 테스트
//	 */	
//	void insertCnrsSpceItem(FckAprFieldBookItemVO itemVO) throws Exception{
//		insert("fckAprFieldBookDAO.insertCnrsSpceItem", itemVO);
//	};
//	
//	/**
//	 * @param itemVO
//	 * @return
//	 * @description 공유방 조사데이터 업데이트 테스트
//	 */	
//	void updateCnrsSpceItem(FckAprFieldBookItemVO itemVO) throws Exception{
//		insert("fckAprFieldBookDAO.updateCnrsSpceItem", itemVO);
//	};
//	
//	/**
//	 * @param map
//	 * @return
//	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
//	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception{
//		return (List<FckAprFieldBookItemVO>) list("fckAprFieldBookDAO.selectCnrsSpceCompItem", map);
//	};
//	
//	/**
//	 * @param stripLandVO
//	 * @return
//	 * @description 대상지 목록조회
//	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprStripLandVO> selectCnrsSpceSldList(FckAprStripLandVO stripLandVO) throws Exception{
//		return (List<FckAprStripLandVO>) list("fckAprFieldBookDAO.selectCnrsSpceSldList", stripLandVO);
//	};
//	
//	/**
//	 * @param id
//	 * @return
//	 * @description 대상지 단건조회
//	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
//		return (List<FckAprStripLandVO>) list("fckAprFieldBookDAO.selectCnrsSpceSldDetail", id);
//	};
//	
//	/**
//	 * @param itemVO
//	 * @throws Exception
//	 * @description 대상지 추가
//	 */
//	void insertCnrsSpceSld(FckAprFieldBookItemVO itemVO) throws Exception{
//		insert("fckAprFieldBookDAO.insertCnrsSpceSld", itemVO);
//	};
//	

//	

//	
//	/**
//	 * @param map
//	 * @throws Exception
//	 * @desciption 공유방 조사완료데이터 삭제
//	 */
//	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
//		delete("fckAprFieldBookDAO.deleteCnrsSpceComptItem", map);
//	};
//	
//	/**
//	 * @param map
//	 * @throws Exception
//	 * @desciption 완료테이블 조사데이터 여부
//	 */
//	public int selectUpsertAt(FckAprFieldBookItemVO searchVO) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectUpsertAt", searchVO);
//	}
//	
//	
//	
//	/**
//	 * 사업지구명 중복를 조회한다.
//	 * @throws Exception
//	 */
//	public int selectFckAprFieldBookCheckMstName(String mst_partname) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectFckAprFieldBookCheckMstName",mst_partname);
//	}
	
}
