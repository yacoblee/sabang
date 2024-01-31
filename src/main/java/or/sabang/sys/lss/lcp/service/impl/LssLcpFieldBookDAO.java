package or.sabang.sys.lss.lcp.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookItemVO;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.sys.service.SysComptVO;

@Repository("lssLcpFieldBookDAO")
public class LssLcpFieldBookDAO extends EgovComAbstractDAO {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpFieldBookVO> selectLssLcpFieldBookList(LssLcpFieldBookVO searchVO) throws Exception{
		return (List<LssLcpFieldBookVO>) list("lssLcpFieldBookDAO.selectLssLcpFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssLcpFieldBookListTotCnt(LssLcpFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("lssLcpFieldBookDAO.selectLssLcpFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public LssLcpFieldBookVO selectLssLcpFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("lssLcpFieldBookDAO.selectLssLcpFieldBookDetail", map);
	};
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("lssLcpFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpSvyStripLandVO> selectLssLcpFieldBookItemList(LssLcpFieldBookItemVO searchVO) throws Exception{
		return (List<LssLcpSvyStripLandVO>) list("lssLcpFieldBookDAO.selectLssLcpFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssLcpFieldBookItemListTotCnt(LssLcpFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("lssLcpFieldBookDAO.selectLssLcpFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<LssLcpFieldBookVO>) list("lssLcpFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public LssLcpFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (LssLcpFieldBookVO) selectOne("lssLcpFieldBookDAO.selectCnrsSpceInfo", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("lssLcpFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사 유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception{
//		return (String) selectOne("lssLcpFieldBookDAO.selectCnrsSpceType", map);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception{
		return (List<LssLcpFieldBookItemVO>) list("lssLcpFieldBookDAO.selectCnrsSpceDownload", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 조회 테스트
	 */ 
//	@SuppressWarnings("unchecked")
//	List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception{
//		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectCnrsSpceItem", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
	 */
//	public int selectCnrsSpceMvl(int mst_id) throws Exception{
//		return (int) selectOne("lssLcpFieldBookDAO.selectCnrsSpceMvl", mst_id);
//	}
	public int selectLcpCnrsSpceMvl(int mst_id) throws Exception{
		return (int) selectOne("lssLcpFieldBookDAO.selectLcpCnrsSpceMvl", mst_id);
	}
	public int selectAprCnrsSpceFieldMvl(int mst_id) throws Exception{
		return (int) selectOne("lssLcpFieldBookDAO.selectAprCnrsSpceFieldMvl", mst_id);
	}
	public int selectAprCnrsSpceComptMvl(int mst_id) throws Exception{
		return (int) selectOne("lssLcpFieldBookDAO.selectAprCnrsSpceComptMvl", mst_id);
	}
	
	/**
	 * @return
	 * @description 기초조사 FID 갱신
	 */	
	public int selectLcpUpdtFid() throws Exception{
		return (int) selectOne("lssLcpFieldBookDAO.selectLcpUpdtFid");
	}
	
		
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */	
//	void insertCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
//		insert("lssLcpFieldBookDAO.insertCnrsSpceItem", itemVO);
//	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */	
	void updateCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
		update("lssLcpFieldBookDAO.updateCnrsSpceItem", itemVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public List<LssLcpFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception{
//		return (List<LssLcpFieldBookItemVO>) list("lssLcpFieldBookDAO.selectCnrsSpceCompItem", map);
//	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(LssLcpFieldBookVO fieldBookVO) throws Exception{
		insert("lssLcpFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(List<LssLcpFieldBookVO> list) throws Exception{
		insert("lssLcpFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	};
	
	void insertStripLand(List<LssLcpFieldBookItemVO> list) throws Exception{
		insert("lssLcpFieldBookDAO.insertStripLand", list);
	};
	
	void insertStripLandVO(SysComptVO vo) throws Exception{
		insert("lssLcpFieldBookDAO.insertStripLandVO", vo);
	}
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpSvyStripLandVO> selectCnrsSpceSldList(LssLcpSvyStripLandVO stripLandVO) throws Exception{
		return (List<LssLcpSvyStripLandVO>) list("lssLcpFieldBookDAO.selectCnrsSpceSldList", stripLandVO);
	};
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpSvyStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return (List<LssLcpSvyStripLandVO>) list("lssLcpFieldBookDAO.selectCnrsSpceSldDetail", id);
	};
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	void insertCnrsSpceSld(LssLcpFieldBookItemVO itemVO) throws Exception{
		insert("lssLcpFieldBookDAO.insertCnrsSpceSld", itemVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpSvyStripLandVO> selectLssLcpFieldBookItemView(LssLcpFieldBookItemVO searchVO) throws Exception {
		return (List<LssLcpSvyStripLandVO>) list("lssLcpFieldBookDAO.selectLssLcpFieldBookItemView", searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		delete("lssLcpFieldBookDAO.deleteCnrsSpce", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		delete("lssLcpFieldBookDAO.deleteCnrsSpceItem", map);
	};
	
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("lssLcpFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("lssLcpFieldBookDAO.deleteCnrsSpceComptItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	public int selectUpsertAt(LssLcpFieldBookItemVO searchVO) throws Exception{
//		return (int) selectOne("lssLcpFieldBookDAO.selectUpsertAt", searchVO);
//	}
//	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssLcpFieldBookMaxYear() throws Exception{
		return selectOne("lssLcpFieldBookDAO.selectLssLcpFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssLcpFieldBookYear() throws Exception{
		return list("lssLcpFieldBookDAO.selectLssLcpFieldBookYear","");
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectLssLcpFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("lssLcpFieldBookDAO.selectLssLcpFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 제보테이블 정보를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpGvfListAddr(LssLcpFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectLcpGvfListAddr",vo);
	}
	
	/**
	 * 제보테이블 정보 개수를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpGvfListAddrCnt(LssLcpFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectLcpGvfListAddrCnt",vo);
	}
	
	/**
	 * 조사대상지등록 테이블 정보를 조회한다. 
	 * @throws Exception
	 */
	public List<LssLcpFieldBookVO> selectLcpSldRegList() throws Exception {
		return (List<LssLcpFieldBookVO>) list("lssLcpFieldBookDAO.selectLcpSldRegList","");
	}
	
	/**
	 * 조사대상지정보 테이블 정보를 조회한다.
	 * @param vo
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSldListAddr(LssLcpFieldBookVO vo) throws Exception {
		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectLcpSldListAddr",vo);
	}
	
	/**
	 * 조사대상지정보 테이블 정보 개수를 조회한다.
	 * @param vo
	 * @throws Exception
	 */
	public List<EgovMap> selectLcpSldListAddrCnt(LssLcpFieldBookVO vo) throws Exception {
		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectLcpSldListAddrCnt",vo);
	}

	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception{
		return list("lssLcpFieldBookDAO.selectOrgchtList","");
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssLcpFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("lssLcpFieldBookDAO.selectMberList",vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<LssLcpFieldBookVO>) list("lssLcpFieldBookDAO.selectCnrsAuthorList", map);
	};
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("lssLcpFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("lssLcpFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 사용자 권한 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteCnrsSpceAuthorMgt(LssLcpFieldBookVO fieldBookVO) throws Exception{
		delete("lssLcpFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
}
