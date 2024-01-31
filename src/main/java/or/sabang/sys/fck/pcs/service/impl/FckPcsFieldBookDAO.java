package or.sabang.sys.fck.pcs.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.pcs.service.FckPcsFieldBookItemVO;
import or.sabang.sys.fck.pcs.service.FckPcsFieldBookVO;
import or.sabang.sys.fck.pcs.service.FckPcsStripLandVO;

@Repository("fckPcsFieldBookDAO")
public class FckPcsFieldBookDAO extends EgovComAbstractDAO {

	
	

	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(FckPcsFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("fckPcsFieldBookDAO.selectMberList",vo);
	}
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception{
		return list("fckPcsFieldBookDAO.selectOrgchtList","");
	}
	
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("fckPcsFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("fckPcsFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 권한자 조회
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 
	 */
	public List<FckPcsFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<FckPcsFieldBookVO>) list("fckPcsFieldBookDAO.selectCnrsAuthorList", map);
	}
	
	/**
	 * 사업지구명 중복 조회
	 * @author DEVWORK
	 * @param mst_partname
	 * @return
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public int selectFckPcsFieldBookCheckMstName(String mst_partname) {
		return (int) selectOne("fckPcsFieldBookDAO.selectFckPcsFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 공유방 등록
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public String insertCnrsSpce(FckPcsFieldBookVO fieldBookVO) {
		insert("fckPcsFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	}
	
	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param list
	 * @since 2023. 11. 30.
	 * @modified
	 */
	void insertCnrsSpceAuthorMgt(List<FckPcsFieldBookVO> list) {
		insert("fckPcsFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	}
	
	/**
	 * 대상지 등록
	 * @author DEVWORK
	 * @param list
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	void insertStripLand(List<FckAprFieldBookItemVO> list) throws Exception{
		insert("fckAprFieldBookDAO.insertStripLand", list);
	}
	
	/**
	 * 대상지 데이터 등록
	 * @author DEVWORK
	 * @param vo
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public void insertStripLandVO(FckPcsFieldBookItemVO vo) {
		insert("fckPcsFieldBookDAO.insertStripLandVO", vo);
	}
	
	/**
	 * 공유방 삭제 
	 * @author DEVWORK
	 * @param commandMap
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public void deleteCnrsSpce(HashMap<String, Object> commandMap) {
		delete("fckPcsFieldBookDAO.deleteCnrsSpce", commandMap);
	}
	
	/**
	 * 공유방 목록 최근연도 조회
	 * @author DEVWORK
	 * @return
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public String selectFckPcsFieldBookMaxYear() {
		return selectOne("fckPcsFieldBookDAO.selectFckPcsFieldBookMaxYear","");
	}
	
	/**
	 * 공유방 연도 목록 조회
	 * @author DEVWORK
	 * @return
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public List<?> selectFckPcsFieldBookYear() {
		return list("fckPcsFieldBookDAO.selectFckPcsFieldBookYear", "");
	}
	
	/**
	 * 공유방 목록 조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public List<FckPcsFieldBookVO> selectFckPcsFieldBookList(FckPcsFieldBookVO searchVO) {
		return (List<FckPcsFieldBookVO>) list("fckPcsFieldBookDAO.selectFckPcsFieldBookList", searchVO);
	}
	
	/**
	 * 공유방 갯수 조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public int selectFckPcsFieldBookListTotCnt(FckPcsFieldBookVO searchVO) {
		return (Integer) selectOne("fckPcsFieldBookDAO.selectFckPcsFieldBookListTotCnt", searchVO);
	}
	
	/**
	 * 공유방 상세조회
	 * @author DEVWORK
	 * @param map
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookVO selectFckPcsFieldBookDetail(HashMap<String, Object> map) {
		return selectOne("fckPcsFieldBookDAO.selectFckPcsFieldBookDetail", map);
	}
	
	/**
	 * 공유방 조사데이터 리스트조회
	 * @author DEVWORK
	 * @param searchItemVO
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public List<FckPcsStripLandVO> selectFckPcsFieldBookItemList(FckPcsFieldBookItemVO searchItemVO) {
		return (List<FckPcsStripLandVO>) list("fckPcsFieldBookDAO.selectFckPcsFieldBookItemList", searchItemVO);
	}
	
	/**
	 * 공유방 조사데이터 전체 건수 조회
	 * @author DEVWORK
	 * @param vo
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public int selectFckPcsFieldBookItemListTotCnt(FckPcsFieldBookItemVO vo) {
		return (Integer) selectOne("fckPcsFieldBookDAO.selectFckPcsFieldBookItemListTotCnt", vo);
	}
	
	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) {
		return (String) selectOne("fckPcsFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * 사방댐 정밀점검 상세 정보 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookItemVO selectFieldBookDetailOne1(HashMap<String, Object> commandMap) {
		return selectOne("fckPcsFieldBookDAO.selectFieldBookDetailOne1", commandMap);
	};

	/**
	 * 계류보전 정밀점검 상세 정보 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookItemVO selectFieldBookDetailOne2(HashMap<String, Object> commandMap) {
		return selectOne("fckPcsFieldBookDAO.selectFieldBookDetailOne2", commandMap);
	};
	
	/**
	 * 산지사방 정밀점검 상세 정보 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookItemVO selectFieldBookDetailOne3(HashMap<String, Object> commandMap) {
		return selectOne("fckPcsFieldBookDAO.selectFieldBookDetailOne3", commandMap);
	};
	
	/**
	 * 해안침식방지 정밀점검 상세 정보 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookItemVO selectFieldBookDetailOne4(HashMap<String, Object> commandMap) {
		return selectOne("fckPcsFieldBookDAO.selectFieldBookDetailOne4", commandMap);
	};
	
	/**
	 * 해안방재림 정밀점검 상세 정보 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookItemVO selectFieldBookDetailOne5(HashMap<String, Object> commandMap) {
		return selectOne("fckPcsFieldBookDAO.selectFieldBookDetailOne5", commandMap);
	}
	
	/**
	 * 공유방 조사데이터 전체 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void deleteCnrsSpceAllItem(HashMap<String, Object> commandMap) {
		delete("fckPcsFieldBookDAO.deleteCnrsSpceAllItem", commandMap);
	}
	
	/**
	 * 공유방 조사완료데이터 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @since 2023. 12. 4.
	 * @modified
	 */
	
	public void deleteCnrsSpceComptItem(HashMap<String, Object> commandMap) {
		delete("fckPcsFieldBookDAO.deleteCnrsSpceComptItem", commandMap);
	}
	/**
	 * 공유방 조사데이터 단건 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> commandMap) {
		delete("fckPcsFieldBookDAO.deleteFieldBookDetailOne",commandMap);
	}
	
	/**
	 * 공유방 권한 삭제
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void deleteCnrsSpceAuthorMgt(FckPcsFieldBookVO fieldBookVO) {
		delete("fckPcsFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
	
	/**
	 * 좌표계 변환
	 * @author DEVWORK
	 * @param projMap
	 * @return
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public List<EgovMap> selectFckPcsProjBessel(HashMap<String, Object> projMap) {
		return (List<EgovMap>) list("fckPcsFieldBookDAO.selectFckPcsProjBessel",projMap);
	}
	
	/**
	 * 공유방 조사데이터 수정
	 * @author DEVWORK
	 * @param searchVO
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void updateFieldBookDetailOne(FckPcsFieldBookItemVO searchVO) {
		update("fckPcsFieldBookDAO.updateFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public String selectFckPcsSvyType(HashMap<String, Object> commandMap) {
		return selectOne("fckPcsFieldBookDAO.selectFckPcsSvyType", commandMap);
	};
	
}
