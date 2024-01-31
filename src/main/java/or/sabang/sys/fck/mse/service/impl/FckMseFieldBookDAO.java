package or.sabang.sys.fck.mse.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.mse.service.FckMseFieldBookItemVO;
import or.sabang.sys.fck.mse.service.FckMseFieldBookVO;
import or.sabang.sys.fck.mse.service.FckMseStripLandVO;
import or.sabang.sys.service.SysComptVO;

@Repository("fckMseFieldBookDAO")
public class FckMseFieldBookDAO extends EgovComAbstractDAO {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseFieldBookVO> selectFckMseFieldBookList(FckMseFieldBookVO searchVO) throws Exception{
		return (List<FckMseFieldBookVO>) list("fckMseFieldBookDAO.selectFckMseFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectFckMseFieldBookListTotCnt(FckMseFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("fckMseFieldBookDAO.selectFckMseFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public FckMseFieldBookVO selectFckMseFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("fckMseFieldBookDAO.selectFckMseFieldBookDetail", map);
	};
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("fckMseFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseStripLandVO> selectFckMseFieldBookItemList(FckMseFieldBookItemVO searchVO) throws Exception{
		return (List<FckMseStripLandVO>) list("fckMseFieldBookDAO.selectFckMseFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectFckMseFieldBookItemListTotCnt(FckMseFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("fckMseFieldBookDAO.selectFckMseFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckMseFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<FckMseFieldBookVO>) list("fckMseFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public FckMseFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (FckMseFieldBookVO) selectOne("fckMseFieldBookDAO.selectCnrsSpceInfo", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("fckMseFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public List<FckMseFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception{
//		return (List<FckMseFieldBookItemVO>) list("fckMseFieldBookDAO.selectCnrsSpceDownload", map);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 조회 테스트
	 */ 
//	@SuppressWarnings("unchecked")
//	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception{
//		return (List<EgovMap>) list("fckMseFieldBookDAO.selectCnrsSpceItem", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
	 */
//	public int selectCnrsSpceMvl(int mst_id) throws Exception{
//		return (int) selectOne("fckMseFieldBookDAO.selectCnrsSpceMvl", mst_id);
//	}
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */	
//	void insertCnrsSpceItem(FckMseFieldBookItemVO itemVO) throws Exception{
//		insert("fckMseFieldBookDAO.insertCnrsSpceItem", itemVO);
//	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */	
//	void updateCnrsSpceItem(FckMseFieldBookItemVO itemVO) throws Exception{
//		insert("fckMseFieldBookDAO.updateCnrsSpceItem", itemVO);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public List<FckMseFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception{
//		return (List<FckMseFieldBookItemVO>) list("fckMseFieldBookDAO.selectCnrsSpceCompItem", map);
//	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(FckMseFieldBookVO fieldBookVO) throws Exception{
		insert("fckMseFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(List<FckMseFieldBookVO> list) throws Exception{
		insert("fckMseFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	};
	
	void insertStripLand(List<FckMseFieldBookItemVO> list) throws Exception{
		insert("fckMseFieldBookDAO.insertStripLand", list);
	};
	
	void insertStripLandVO(SysComptVO vo) throws Exception{
		insert("fckMseFieldBookDAO.insertStripLandVO", vo);
	}
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseStripLandVO> selectCnrsSpceSldList(FckMseStripLandVO stripLandVO) throws Exception{
		return (List<FckMseStripLandVO>) list("fckMseFieldBookDAO.selectCnrsSpceSldList", stripLandVO);
	};
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return (List<FckMseStripLandVO>) list("fckMseFieldBookDAO.selectCnrsSpceSldDetail", id);
	};
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	void insertCnrsSpceSld(FckMseFieldBookItemVO itemVO) throws Exception{
//		insert("fckMseFieldBookDAO.insertCnrsSpceSld", itemVO);
//	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseStripLandVO> selectFckMseFieldBookItemView(FckMseFieldBookItemVO searchVO) throws Exception {
		return (List<FckMseStripLandVO>) list("fckMseFieldBookDAO.selectFckMseFieldBookItemView", searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		delete("fckMseFieldBookDAO.deleteCnrsSpce", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		delete("fckMseFieldBookDAO.deleteCnrsSpceItem", map);
	};
		
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("fckMseFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("fckMseFieldBookDAO.deleteCnrsSpceComptItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	public int selectUpsertAt(FckMseFieldBookItemVO searchVO) throws Exception{
//		return (int) selectOne("fckMseFieldBookDAO.selectUpsertAt", searchVO);
//	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectFckMseFieldBookMaxYear() throws Exception{
		return selectOne("fckMseFieldBookDAO.selectFckMseFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckMseFieldBookYear() throws Exception{
		return list("fckMseFieldBookDAO.selectFckMseFieldBookYear","");
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectFckMseFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("fckMseFieldBookDAO.selectFckMseFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public FckMseFieldBookItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		return selectOne("fckMseFieldBookDAO.selectFieldBookDetailOne", map);
	}
	
	/**
	 * 공유방 단건 수정
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public void updateFieldBookDetailOne(FckMseFieldBookItemVO searchVO) throws Exception{
		update("fckMseFieldBookDAO.updateFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 공유방 단건 삭제
	 * @param stripLandVO
	 * @return
	 * @throws Exception
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		delete("fckMseFieldBookDAO.deleteFieldBookDetailOne", map);
	}
	
	/**
	 * EPSG:5186 좌표를 도분초 형식의 좌표로 변환한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectFckMseProjBessel(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("fckMseFieldBookDAO.selectFckMseProjBessel",map);
	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception{
		return list("fckMseFieldBookDAO.selectOrgchtList","");
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(FckMseFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("fckMseFieldBookDAO.selectMberList",vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<FckMseFieldBookVO>) list("fckMseFieldBookDAO.selectCnrsAuthorList", map);
	};
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("fckMseFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("fckMseFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 사용자 권한 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteCnrsSpceAuthorMgt(FckMseFieldBookVO fieldBookVO) throws Exception{
		delete("fckMseFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
	
	/**
	 * 계측장비 대상지 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectMseSldListAddr(FckMseFieldBookVO fieldBookVO) throws Exception{
		return (List<EgovMap>) list("fckMseFieldBookDAO.selectMseSldListAddr",fieldBookVO);
	}
}
