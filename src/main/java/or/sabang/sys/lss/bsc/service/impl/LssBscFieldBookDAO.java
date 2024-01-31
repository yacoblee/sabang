package or.sabang.sys.lss.bsc.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookItemVO;
import or.sabang.sys.lss.bsc.service.LssBscFieldBookVO;
import or.sabang.sys.lss.bsc.service.LssBscStripLandVO;

@Repository("lssBscFieldBookDAO")
public class LssBscFieldBookDAO extends EgovComAbstractDAO {

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssBscFieldBookVO> selectLssBscFieldBookList(LssBscFieldBookVO searchVO) throws Exception{
		return (List<LssBscFieldBookVO>) list("lssBscFieldBookDAO.selectLssBscFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssBscFieldBookListTotCnt(LssBscFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("lssBscFieldBookDAO.selectLssBscFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public LssBscFieldBookVO selectLssBscFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("lssBscFieldBookDAO.selectLssBscFieldBookDetail", map);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssBscStripLandVO> selectLssBscFieldBookItemList(LssBscFieldBookItemVO searchVO) throws Exception{
		return (List<LssBscStripLandVO>) list("lssBscFieldBookDAO.selectLssBscFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectLssBscFieldBookItemListTotCnt(LssBscFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("lssBscFieldBookDAO.selectLssBscFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@SuppressWarnings("unchecked")
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception{
		return (List<EgovMap>) list("lssBscFieldBookDAO.selectCnrsSpcePwd", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@SuppressWarnings("unchecked")
	public List<LssBscFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception{
		return (List<LssBscFieldBookVO>) list("lssBscFieldBookDAO.selectCnrsSpceList", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@SuppressWarnings("unchecked")
//	public LssBscFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception{
//		return (LssBscFieldBookVO) selectOne("lssBscFieldBookDAO.selectCnrsSpceInfo", map);
//	};
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	public int selectCnrsSpceAt(int mst_id) throws Exception{
//		return (int) selectOne("lssBscFieldBookDAO.selectCnrsSpceAt", mst_id);
//	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사 유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception{
//		return (String) selectOne("lssBscFieldBookDAO.selectCnrsSpceType", map);
//	};

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(LssBscFieldBookVO fieldBookVO) throws Exception{
		insert("lssBscFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	};
	
	void insertStripLand(List<LssBscFieldBookItemVO> list) throws Exception{
		insert("lssBscFieldBookDAO.insertStripLand", list);
	};
	
	void insertStripLandVO(LssBscFieldBookItemVO vo) throws Exception{
		insert("lssBscFieldBookDAO.insertStripLandVO", vo);
	}
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	void insertCnrsSpceSld(LssBscFieldBookItemVO itemVO) throws Exception{
//		insert("lssBscFieldBookDAO.insertCnrsSpceSld", itemVO);
//	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	@SuppressWarnings("unchecked")
	public List<LssBscStripLandVO> selectLssBscFieldBookItemView(LssBscFieldBookItemVO searchVO) throws Exception {
		return (List<LssBscStripLandVO>) list("lssBscFieldBookDAO.selectLssBscFieldBookItemView", searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		delete("lssBscFieldBookDAO.deleteCnrsSpce", map);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception{
		delete("lssBscFieldBookDAO.deleteCnrsSpceItem", map);
	};
	
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("lssBscFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("lssBscFieldBookDAO.deleteCnrsSpceComptItem", map);
	};

	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectLssBscFieldBookMaxYear() throws Exception{
		return selectOne("lssBscFieldBookDAO.selectLssBscFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssBscFieldBookYear() throws Exception{
		return list("lssBscFieldBookDAO.selectLssBscFieldBookYear","");
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectLssBscFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("lssBscFieldBookDAO.selectLssBscFieldBookCheckMstName",mst_partname);
	}
}
