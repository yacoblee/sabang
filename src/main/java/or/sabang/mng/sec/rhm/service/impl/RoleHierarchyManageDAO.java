package or.sabang.mng.sec.rhm.service.impl;

import java.util.List;

import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManage;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManageVO;

import org.springframework.stereotype.Repository;

@Repository("roleHierarchyManageDAO")
public class RoleHierarchyManageDAO extends EgovComAbstractDAO {

	/**
	 * 등록된 롤상하관계 정보 목록 조회
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @return List<RoleHierarchyManage>
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	public List<RoleHierarchyManageVO> selectRoleHierarchyList(RoleHierarchyManageVO roleHierarchyManageVO) throws Exception {
		return (List<RoleHierarchyManageVO>) list("roleHierarchyManageDAO.selectRoleHierarchyList", roleHierarchyManageVO);
	}

	/**
	 * 롤 별 상하관계를 등록
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @return RoleHierarchyManage
	 * @exception Exception
	 */
	public void insertRoleHierarchy(RoleHierarchyManage roleHierarchyManage) throws Exception {
		insert("roleHierarchyManageDAO.insertRoleHierarchy", roleHierarchyManage);
	}
	
	/**
	 * 불필요한 롤 상하관계 정보를 화면에 조회하여 데이터베이스에서 삭제
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @exception Exception
	 */
	public void deleteRoleHierarchy(RoleHierarchyManage roleHierarchyManage) throws Exception {
		delete("roleHierarchyManageDAO.deleteRoleHierarchy", roleHierarchyManage);
	}
	
	/**
	 * 목록조회 카운트를 반환한다
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @return int
	 * @exception Exception
	 */
    public int selectRoleHierarchyListTotCnt(RoleHierarchyManageVO roleHierarchyManageVO) throws Exception {
        return (Integer)selectOne("roleHierarchyManageDAO.selectRoleHierarchyListTotCnt", roleHierarchyManageVO);
    }	
}