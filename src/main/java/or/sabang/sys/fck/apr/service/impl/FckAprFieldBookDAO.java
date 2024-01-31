package or.sabang.sys.fck.apr.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;
import or.sabang.sys.fck.apr.service.FckAprStripLandVO;

@Repository("fckAprFieldBookDAO")
public class FckAprFieldBookDAO extends EgovComAbstractDAO {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprFieldBookVO> selectFckAprFieldBookList(FckAprFieldBookVO searchVO) throws Exception{
		return (List<FckAprFieldBookVO>) list("fckAprFieldBookDAO.selectFckAprFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectFckAprFieldBookListTotCnt(FckAprFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("fckAprFieldBookDAO.selectFckAprFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public FckAprFieldBookVO selectFckAprFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("fckAprFieldBookDAO.selectFckAprFieldBookDetail", map);
	};
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("fckAprFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprStripLandVO> selectFckAprFieldBookItemList(FckAprFieldBookItemVO searchVO) throws Exception{
		return (List<FckAprStripLandVO>) list("fckAprFieldBookDAO.selectFckAprFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectFckAprFieldBookItemListTotCnt(FckAprFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("fckAprFieldBookDAO.selectFckAprFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckAprFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<FckAprFieldBookVO>) list("fckAprFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public FckAprFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (FckAprFieldBookVO) selectOne("fckAprFieldBookDAO.selectCnrsSpceInfo", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception{
//		return (List<FckAprFieldBookItemVO>) list("fckAprFieldBookDAO.selectCnrsSpceDownload", map);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 조회 테스트
	 */ 
//	@SuppressWarnings("unchecked")
//	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception{
//		return (List<EgovMap>) list("fckAprFieldBookDAO.selectCnrsSpceItem", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
	 */
//	public int selectCnrsSpceMvl(int mst_id) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectCnrsSpceMvl", mst_id);
//	}
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */	
//	void insertCnrsSpceItem(FckAprFieldBookItemVO itemVO) throws Exception{
//		insert("fckAprFieldBookDAO.insertCnrsSpceItem", itemVO);
//	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */	
//	void updateCnrsSpceItem(FckAprFieldBookItemVO itemVO) throws Exception{
//		insert("fckAprFieldBookDAO.updateCnrsSpceItem", itemVO);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public List<FckAprFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception{
//		return (List<FckAprFieldBookItemVO>) list("fckAprFieldBookDAO.selectCnrsSpceCompItem", map);
//	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(FckAprFieldBookVO fieldBookVO) throws Exception{
		insert("fckAprFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(List<FckAprFieldBookVO> list) throws Exception{
		insert("fckAprFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	};
	
	void insertStripLand(List<FckAprFieldBookItemVO> list) throws Exception{
		insert("fckAprFieldBookDAO.insertStripLand", list);
	};
	
	void insertStripLandVO(FckAprFieldBookItemVO vo) throws Exception{
		insert("fckAprFieldBookDAO.insertStripLandVO", vo);
	}
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprStripLandVO> selectCnrsSpceSldList(FckAprStripLandVO stripLandVO) throws Exception{
		return (List<FckAprStripLandVO>) list("fckAprFieldBookDAO.selectCnrsSpceSldList", stripLandVO);
	};
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return (List<FckAprStripLandVO>) list("fckAprFieldBookDAO.selectCnrsSpceSldDetail", id);
	};
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	void insertCnrsSpceSld(FckAprFieldBookItemVO itemVO) throws Exception{
//		insert("fckAprFieldBookDAO.insertCnrsSpceSld", itemVO);
//	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprStripLandVO> selectFckAprFieldBookItemView(FckAprFieldBookItemVO searchVO) throws Exception {
		return (List<FckAprStripLandVO>) list("fckAprFieldBookDAO.selectFckAprFieldBookItemView", searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		delete("fckAprFieldBookDAO.deleteCnrsSpce", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		delete("fckAprFieldBookDAO.deleteCnrsSpceItem", map);
	};
		
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("fckAprFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("fckAprFieldBookDAO.deleteCnrsSpceComptItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	public int selectUpsertAt(FckAprFieldBookItemVO searchVO) throws Exception{
//		return (int) selectOne("fckAprFieldBookDAO.selectUpsertAt", searchVO);
//	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckAprFieldBookMaxYear() throws Exception{
		return selectOne("fckAprFieldBookDAO.selectFckAprFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckAprFieldBookYear() throws Exception{
		return list("fckAprFieldBookDAO.selectFckAprFieldBookYear","");
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectFckAprFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("fckAprFieldBookDAO.selectFckAprFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public FckAprFieldBookItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		return selectOne("fckAprFieldBookDAO.selectFieldBookDetailOne", map);
	}
	
	/**
	 * 공유방 단건 수정
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public void updateFieldBookDetailOne(FckAprFieldBookItemVO searchVO) throws Exception{
		update("fckAprFieldBookDAO.updateFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 공유방 단건 삭제
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		delete("fckAprFieldBookDAO.deleteFieldBookDetailOne", map);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckAprProjBessel(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckAprFieldBookDAO.selectFckAprProjBessel",map);
	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception{
		return list("fckAprFieldBookDAO.selectOrgchtList","");
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(FckAprFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("fckAprFieldBookDAO.selectMberList",vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckAprFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<FckAprFieldBookVO>) list("fckAprFieldBookDAO.selectCnrsAuthorList", map);
	};
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("fckAprFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("fckAprFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 사용자 권한 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteCnrsSpceAuthorMgt(FckAprFieldBookVO fieldBookVO) throws Exception{
		delete("fckAprFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
}
