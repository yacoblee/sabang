package or.sabang.sys.lss.cnl.service.impl;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookItemVO;
import or.sabang.sys.lss.cnl.service.LssCnlFieldBookVO;
import or.sabang.sys.lss.cnl.service.LssCnlStripLandVO;

@Repository("lssCnlFieldBookDAO")
public class LssCnlFieldBookDAO extends EgovComAbstractDAO {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssCnlFieldBookVO> selectLssCnlFieldBookList(LssCnlFieldBookVO searchVO) throws Exception{
		return (List<LssCnlFieldBookVO>) list("lssCnlFieldBookDAO.selectLssCnlFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssCnlFieldBookListTotCnt(LssCnlFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("lssCnlFieldBookDAO.selectLssCnlFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public LssCnlFieldBookVO selectLssCnlFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("lssCnlFieldBookDAO.selectLssCnlFieldBookDetail", map);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssCnlStripLandVO> selectLssCnlFieldBookItemList(LssCnlFieldBookItemVO searchVO) throws Exception{
		return (List<LssCnlStripLandVO>) list("lssCnlFieldBookDAO.selectLssCnlFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssCnlFieldBookItemListTotCnt(LssCnlFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("lssCnlFieldBookDAO.selectLssCnlFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("lssCnlFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<LssCnlFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<LssCnlFieldBookVO>) list("lssCnlFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public LssCnlFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (LssCnlFieldBookVO) selectOne("lssCnlFieldBookDAO.selectCnrsSpceInfo", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("lssCnlFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사 유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception{
//		return (String) selectOne("lssCnlFieldBookDAO.selectCnrsSpceType", map);
//	};

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(LssCnlFieldBookVO fieldBookVO) throws Exception{
		insert("lssCnlFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(List<LssCnlFieldBookVO> list) throws Exception{
		insert("lssCnlFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	};
	
	void insertStripLand(List<LssCnlFieldBookItemVO> list) throws Exception{
		insert("lssCnlFieldBookDAO.insertStripLand", list);
	};
	
	void insertStripLandVO(LssCnlFieldBookItemVO vo) throws Exception{
		insert("lssCnlFieldBookDAO.insertStripLandVO", vo);
	};
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
//	@SuppressWarnings("unchecked")
//	public List<LssCnlStripLandVO> selectCnrsSpceSldList(LssCnlStripLandVO stripLandVO) throws Exception{
//		return (List<LssCnlStripLandVO>) list("lssCnlFieldBookDAO.selectCnrsSpceSldList", stripLandVO);
//	};
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssCnlStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return (List<LssCnlStripLandVO>) list("lssCnlFieldBookDAO.selectCnrsSpceSldDetail", id);
	};
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	void insertCnrsSpceSld(LssCnlFieldBookItemVO itemVO) throws Exception{
		insert("lssCnlFieldBookDAO.insertCnrsSpceSld", itemVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@SuppressWarnings("unchecked")
	public List<LssCnlStripLandVO> selectLssCnlFieldBookItemView(LssCnlFieldBookItemVO searchVO) throws Exception {
		return (List<LssCnlStripLandVO>) list("lssCnlFieldBookDAO.selectLssCnlFieldBookItemView", searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		delete("lssCnlFieldBookDAO.deleteCnrsSpce", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		delete("lssCnlFieldBookDAO.deleteCnrsSpceItem", map);
	};
	
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("lssCnlFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("lssCnlFieldBookDAO.deleteCnrsSpceComptItem", map);
	};

	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssCnlFieldBookMaxYear() throws Exception{
		return selectOne("lssCnlFieldBookDAO.selectLssCnlFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssCnlFieldBookYear() throws Exception{
		return list("lssCnlFieldBookDAO.selectLssCnlFieldBookYear","");
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectLssCnlFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("lssCnlFieldBookDAO.selectLssCnlFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 * @return
	 * @throws Exception
	 */
	public LssCnlFieldBookItemVO selectFieldBookDetailOne(LssCnlFieldBookItemVO searchVO) throws Exception{
		return selectOne("lssCnlFieldBookDAO.selectFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("lssCnlFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * 공유방 단건 수정
	 * @return
	 * @throws Exception
	 */
	public void updateFieldBookDetailOne(LssCnlFieldBookItemVO searchVO) throws Exception{
		update("lssCnlFieldBookDAO.updateFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 공유방 단건 삭제
	 * @return
	 * @throws Exception
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		delete("lssCnlFieldBookDAO.deleteFieldBookDetailOne", map);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLssCnlProjBessel(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("lssCnlFieldBookDAO.selectLssCnlProjBessel",map);
	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception{
		return list("lssCnlFieldBookDAO.selectOrgchtList","");
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssCnlFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("lssCnlFieldBookDAO.selectMberList",vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssCnlFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<LssCnlFieldBookVO>) list("lssCnlFieldBookDAO.selectCnrsAuthorList", map);
	};
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("lssCnlFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("lssCnlFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 사용자 권한 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteCnrsSpceAuthorMgt(LssCnlFieldBookVO fieldBookVO) throws Exception{
		delete("lssCnlFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
}
