package or.sabang.sys.vyt.frd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookItemVO;
import or.sabang.sys.vyt.frd.service.VytFrdFieldBookVO;
import or.sabang.sys.vyt.frd.service.VytFrdStripLandVO;

@Repository("vytFrdFieldBookDAO")
public class VytFrdFieldBookDAO extends EgovComAbstractDAO {
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytFrdFieldBookVO> selectVytFrdFieldBookList(VytFrdFieldBookVO searchVO) throws Exception{
		return (List<VytFrdFieldBookVO>) list("vytFrdFieldBookDAO.selectVytFrdFieldBookList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectVytFrdFieldBookListTotCnt(VytFrdFieldBookVO searchVO) throws Exception{
		return (Integer) selectOne("vytFrdFieldBookDAO.selectVytFrdFieldBookListTotCnt", searchVO);
	};
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public VytFrdFieldBookVO selectVytFrdFieldBookDetail(HashMap<String, Object> map) throws Exception{
		return selectOne("vytFrdFieldBookDAO.selectVytFrdFieldBookDetail", map);
	};
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	void deleteCnrsSpce(HashMap<String, Object> map) throws Exception{
		if (map.containsKey("smidList")) {
			Object value = map.get("smidList");
			if (value instanceof ArrayList) {
				ArrayList<?> list = (ArrayList<?>) value;
				if (!list.isEmpty()) {
					update("vytFrdFieldBookDAO.updateRmMstId", map);
				}
			}
		}
		delete("vytFrdFieldBookDAO.deleteCnrsSpce", map);
		
	};
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception{
		delete("vytFrdFieldBookDAO.deleteCnrsSpceComptItem", map);
	};
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception{
		delete("vytFrdFieldBookDAO.deleteCnrsSpceAllItem", map);
	};
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytFrdFieldBookYear() throws Exception{
		return list("vytFrdFieldBookDAO.selectVytFrdFieldBookYear","");
	};
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectVytFrdFieldBookMaxYear() throws Exception{
		return selectOne("vytFrdFieldBookDAO.selectVytFrdFieldBookMaxYear","");
	}
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytFrdStripLandVO> selectVytFrdFieldBookItemList(VytFrdFieldBookItemVO searchVO) throws Exception{
		return (List<VytFrdStripLandVO>) list("vytFrdFieldBookDAO.selectVytFrdFieldBookItemList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	@SuppressWarnings("unchecked")
	public int selectVytFrdFieldBookItemListTotCnt(VytFrdFieldBookItemVO searchVO) throws Exception{
		return (Integer) selectOne("vytFrdFieldBookDAO.selectVytFrdFieldBookItemListTotCnt", searchVO);
	};
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	String insertCnrsSpce(VytFrdFieldBookVO fieldBookVO) throws Exception{
		insert("vytFrdFieldBookDAO.insertCnrsSpce", fieldBookVO);
		return fieldBookVO.getId();
	}
	void insertStripLand(HashMap<String, Object> commandMap) throws Exception{
		insert("vytFrdFieldBookDAO.insertStripLand", commandMap);
	}
	void insertStripLandVO(VytFrdFieldBookItemVO vo) throws Exception{
		insert("vytFrdFieldBookDAO.insertStripLandVO", vo);
	}
	
	/**
	 * 사업지구명 중복를 조회한다.
	 * @throws Exception
	 */
	public int selectVytFrdFieldBookCheckMstName(String mst_partname) throws Exception{
		return (int) selectOne("vytFrdFieldBookDAO.selectVytFrdFieldBookCheckMstName",mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 * @return
	 * @throws Exception
	 */
	public VytFrdFieldBookItemVO selectFieldBookDetailOne(VytFrdFieldBookItemVO searchVO) throws Exception{
		return selectOne("vytFrdFieldBookDAO.selectFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 공유방 단건 수정
	 * @return
	 * @throws Exception
	 */
	public void updateFieldBookDetailOne(VytFrdFieldBookItemVO searchVO) throws Exception{
		update("vytFrdFieldBookDAO.updateFieldBookDetailOne", searchVO);
	}
	
	/**
	 * 공유방 단건 삭제
	 * @return
	 * @throws Exception
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception{
		
		String[] smidList = new String[1];
		smidList[0] = map.get("gid").toString(); 
		map.put("smidList", smidList);
		
		update("vytFrdFieldBookDAO.updateRmMstId", map);
		delete("vytFrdFieldBookDAO.deleteFieldBookDetailOne", map);
	}
	
	/**
	 * 공유방 권한 확인
	 * @param map
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		return (String) selectOne("vytFrdFieldBookDAO.selectAuthorCheck",map);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytFrdFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception{
		return (List<VytFrdFieldBookVO>) list("vytFrdFieldBookDAO.selectCnrsAuthorList", map);
	};
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return selectList("vytFrdFieldBookDAO.selectDeptInfoList");
	}
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return selectList("vytFrdFieldBookDAO.selectDeptCreatList");
	}
	
	/**
	 * 공유방 사용자 권한 삭제
	 * @param vo
	 * @throws Exception
	 */
	public void deleteCnrsSpceAuthorMgt(VytFrdFieldBookVO fieldBookVO) throws Exception{
		delete("vytFrdFieldBookDAO.deleteCnrsSpceAuthorMgt",fieldBookVO);
	}
	
	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	void insertCnrsSpceAuthorMgt(List<VytFrdFieldBookVO> list) throws Exception{
		insert("vytFrdFieldBookDAO.insertCnrsSpceAuthorMgt", list);
	};
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(VytFrdFieldBookVO vo) throws Exception{
		return (List<EgovMap>) list("vytFrdFieldBookDAO.selectMberList",vo);
	}
	/**
	 * importfromshp
	 * @param vo
	 * @throws Exception
	 */
	public void insertStripLandFromShp(HashMap<String, Object> commandMap) throws Exception{
		insert("vytFrdFieldBookDAO.insertStripLandFromShp",commandMap);
	}
	
	/**
	 * 대상지 조회
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectSldRegInfo() throws Exception{
		return selectList("vytFrdFieldBookDAO.selectSldRegInfo");
	}
	
	/**
	 * 대상지 개수 조회
	 * @throws Exception
	 */
	public int selectSldInfoCnt(VytFrdFieldBookItemVO fieldBookItemVO) throws Exception{
		return (int) selectOne("vytFrdFieldBookDAO.selectSldInfoCnt",fieldBookItemVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytFrdStripLandVO> selectSldInfo(VytFrdFieldBookItemVO fieldBookItemVO) throws Exception{
		return (List<VytFrdStripLandVO>) list("vytFrdFieldBookDAO.selectSldInfo", fieldBookItemVO);
	};
	
	/**
	 * 공유방 번호 부여
	 * @param vo
	 * @throws Exception
	 */
	public void updateNoMstId(HashMap<String, Object> commandMap) throws Exception{
		if (commandMap.containsKey("smidList")) {
			Object value = commandMap.get("smidList");
			if (value instanceof ArrayList) {
				ArrayList<?> list = (ArrayList<?>) value;
				if (!list.isEmpty()) {
					update("vytFrdFieldBookDAO.updateNoMstId",commandMap);
				}
			}
		}
	}
	
	/**
	 * 대상지 삭제 gid 조회
	 * @param fieldBookItemVO 
	 * @return
	 * @throws Exception
	 */
	public List<Object> selectDeleteItems(String id) throws Exception{
		return selectList("vytFrdFieldBookDAO.selectDeleteItems", id);
	}
}
