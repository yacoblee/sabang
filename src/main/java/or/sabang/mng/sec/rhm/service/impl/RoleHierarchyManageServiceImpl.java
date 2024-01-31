package or.sabang.mng.sec.rhm.service.impl;

import java.util.List;

import or.sabang.mng.sec.rhm.service.RoleHierarchyManage;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManageService;
import or.sabang.mng.sec.rhm.service.RoleHierarchyManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("roleHierarchyManageService")
public class RoleHierarchyManageServiceImpl extends EgovAbstractServiceImpl implements RoleHierarchyManageService {

	@Resource(name="roleHierarchyManageDAO")
	public RoleHierarchyManageDAO roleHierarchyManageDAO;

	/**
	 * 등록된 롤상하관계 정보 목록 조회
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @return List<RoleHierarchyManage>
	 * @exception Exception
	 */
	public List<RoleHierarchyManageVO> selectRoleHierarchyList(RoleHierarchyManageVO roleHierarchyManageVO) throws Exception {
		return roleHierarchyManageDAO.selectRoleHierarchyList(roleHierarchyManageVO);
	}

	/**
	 * 불필요한 롤 상하관계 정보를 화면에 조회하여 데이터베이스에서 삭제
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @exception Exception
	 */
	public void deleteRoleHierarchy(RoleHierarchyManage roleHierarchyManage) throws Exception {
		roleHierarchyManageDAO.deleteRoleHierarchy(roleHierarchyManage);
	}
	
	/**
	 * 롤 별 상하관계를 등록
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @return RoleHierarchyManage
	 * @exception Exception
	 */
	public void insertRoleHierarchy(RoleHierarchyManage roleHierarchyManage) throws Exception {
		roleHierarchyManageDAO.insertRoleHierarchy(roleHierarchyManage);	
	}
	
    /**
	 * 목록조회 카운트를 반환한다
	 * @param roleHierarchyManage RoleHierarchyManage
	 * @return int
	 * @exception Exception
	 */
	public int selectRoleHierarchyListTotCnt(RoleHierarchyManageVO roleHierarchyManageVO) throws Exception {
		return roleHierarchyManageDAO.selectRoleHierarchyListTotCnt(roleHierarchyManageVO);
	}
}