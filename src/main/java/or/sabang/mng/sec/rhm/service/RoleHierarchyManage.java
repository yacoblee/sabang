package or.sabang.mng.sec.rhm.service;

import egovframework.com.cmm.ComDefaultVO;

public class RoleHierarchyManage extends ComDefaultVO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 롤 관리
	 */
	private RoleHierarchyManage roleHierarchyManage;
	/**
	 * 상위 롤코드
	 */
	private String parntsRoleCode;
	/**
	 * 하위 롤코드
	 */
	private String chldrnRoleCode;	
	
	/**
	 * roleHierarchyManage attribute 를 리턴한다.
	 * @return RoleHierarchyManage
	 */
	public RoleHierarchyManage getRoleHierarchyManage() {
		return roleHierarchyManage;
	}
	/**
	 * roleHierarchyManage attribute 값을 설정한다.
	 * @param roleHierarchyManage RoleHierarchyManage 
	 */
	public void setRoleHierarchyManage(RoleHierarchyManage roleHierarchyManage) {
		this.roleHierarchyManage = roleHierarchyManage;
	}
	/**
	 * parntsRoleCode attribute 를 리턴한다.
	 * @return String
	 */
	public String getParntsRoleCode() {
		return parntsRoleCode;
	}
	/**
	 * parntsRoleCode attribute 값을 설정한다.
	 * @param parntsRoleCode String 
	 */
	public void setParntsRoleCode(String parntsRoleCode) {
		this.parntsRoleCode = parntsRoleCode;
	}
	/**
	 * chldrnRoleCode attribute 를 리턴한다.
	 * @return String
	 */
	public String getChldrnRoleCode() {
		return chldrnRoleCode;
	}
	/**
	 * chldrnRoleCode attribute 값을 설정한다.
	 * @param chldrnRoleCode String 
	 */
	public void setChldrnRoleCode(String chldrnRoleCode) {
		this.chldrnRoleCode = chldrnRoleCode;
	}
}