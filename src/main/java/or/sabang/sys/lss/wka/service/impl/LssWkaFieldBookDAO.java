package or.sabang.sys.lss.wka.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.wka.service.LssWkaFieldBookItemVO;
import or.sabang.sys.lss.wka.service.LssWkaFieldBookVO;
import or.sabang.sys.lss.wka.service.LssWkaStripLandItemVO;
import or.sabang.sys.lss.wka.service.LssWkaStripLandVO;

@Repository("lssWkaFieldBookDAO")
public class LssWkaFieldBookDAO extends EgovComAbstractDAO {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaFieldBookVO> selectLssWkaFieldBookList(LssWkaFieldBookVO searchVO) throws Exception{
		return (List<LssWkaFieldBookVO>) list("lssWkaFieldBookDAO.selectLssWkaFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssWkaFieldBookListTotCnt(LssWkaFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("lssWkaFieldBookDAO.selectLssWkaFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public LssWkaFieldBookVO selectLssWkaFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("lssWkaFieldBookDAO.selectLssWkaFieldBookDetail", map);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaStripLandVO> selectLssWkaFieldBookItemList(LssWkaFieldBookItemVO searchVO) throws Exception{
		return (List<LssWkaStripLandVO>) list("lssWkaFieldBookDAO.selectLssWkaFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssWkaFieldBookItemListTotCnt(LssWkaFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("lssWkaFieldBookDAO.selectLssWkaFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("lssWkaFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<LssWkaFieldBookVO>) list("lssWkaFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public LssWkaFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (LssWkaFieldBookVO) selectOne("lssWkaFieldBookDAO.selectCnrsSpceInfo", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("lssWkaFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사 유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception{
//		return (String) selectOne("lssWkaFieldBookDAO.selectCnrsSpceType", map);
//	};

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(LssWkaFieldBookVO fieldBookVO) throws Exception{
		insert("lssWkaFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	void insertStripLand(List<LssWkaFieldBookItemVO> list) throws Exception{
		insert("lssWkaFieldBookDAO.insertStripLand", list);
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(List<LssWkaFieldBookVO> list) throws Exception{
		insert("lssWkaFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	};
	
	void insertStripLandVO(LssWkaFieldBookItemVO subVo) throws Exception{
		insert("lssWkaFieldBookDAO.insertStripLandVO", subVo);
	}
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
//	@SuppressWarnings("unchecked")
//	public List<LssWkaStripLandVO> selectCnrsSpceSldList(LssWkaStripLandVO stripLandVO) throws Exception{
//		return (List<LssWkaStripLandVO>) list("lssWkaFieldBookDAO.selectCnrsSpceSldList", stripLandVO);
//	};
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return (List<LssWkaStripLandVO>) list("lssWkaFieldBookDAO.selectCnrsSpceSldDetail", id);
	};
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	void insertCnrsSpceSld(LssWkaFieldBookItemVO itemVO) throws Exception{
		insert("lssWkaFieldBookDAO.insertCnrsSpceSld", itemVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaStripLandVO> selectLssWkaFieldBookItemView(LssWkaFieldBookItemVO searchVO) throws Exception {
		return (List<LssWkaStripLandVO>) list("lssWkaFieldBookDAO.selectLssWkaFieldBookItemView", searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		delete("lssWkaFieldBookDAO.deleteCnrsSpce", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		delete("lssWkaFieldBookDAO.deleteCnrsSpceItem", map);
	};
	
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("lssWkaFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("lssWkaFieldBookDAO.deleteCnrsSpceComptItem", map);
	};

	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssWkaFieldBookMaxYear() throws Exception{
		return selectOne("lssWkaFieldBookDAO.selectLssWkaFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssWkaFieldBookYear() throws Exception{
		return list("lssWkaFieldBookDAO.selectLssWkaFieldBookYear","");
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectLssWkaFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("lssWkaFieldBookDAO.selectLssWkaFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 공유방 대상지 상세조회
	 * @param map
	 * @throws Exception
	 */
	public LssWkaStripLandItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		return selectOne("lssWkaFieldBookDAO.selectFieldBookDetailOne",map);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("lssWkaFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * 공유방 대상지 수정
	 * @param vo
	 * @throws Exception
	 */
	public void updateFieldBookDetailOne(LssWkaStripLandItemVO vo) throws Exception{
		update("lssWkaFieldBookDAO.updateFieldBookDetailOne",vo);
	}
	
	/**
	 * 공유방 대상지 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteFieldBookDetailOne(LssWkaStripLandItemVO vo) throws Exception{
		delete("lssWkaFieldBookDAO.deleteFieldBookDetailOne",vo);
	}
	
	/**
	 * 공간정보 테이블 위치정보 변환
	 * @throws Exception
	 */
//	public String selectLssWkaGeomDataList(SysFieldInfoVO vo) throws Exception{
//		return (String) selectOne("lssWkaFieldBookDAO.selectLssWkaGeomDataList",vo);
//	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception{
		return list("lssWkaFieldBookDAO.selectOrgchtList","");
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssWkaFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("lssWkaFieldBookDAO.selectMberList",vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssWkaFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<LssWkaFieldBookVO>) list("lssWkaFieldBookDAO.selectCnrsAuthorList", map);
	};
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("lssWkaFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("lssWkaFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 사용자 권한 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteCnrsSpceAuthorMgt(LssWkaFieldBookVO fieldBookVO) throws Exception{
		delete("lssWkaFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
}
