package or.sabang.sys.vyt.frd.service;

import egovframework.com.cmm.ComDefaultVO;

public class VytFrdFieldBookVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 공유방ID
	 */
	private String id;
	/**
	 * 조사ID
	 */
	private String svyId;
	/**
	 * 회사명
	 */
	private String mst_corpname;
	/**
	 * 사업지구명
	 */
	private String mst_partname;
	/**
	 * 부서명
	 */
	private String mst_deptname;	
	/**
	 * 생성자ID
	 */
	private String mst_create_user;
	
	/**
	 * 관리자ID
	 */
	private String mst_admin_user;
	
	/**
	 * 관리자 고유ID
	 */
	private String mst_admin_id;
	
	/**
	 * 등록일자
	 */
	private String mst_registered;
	
	/**
	 * 상태코드
	 */
	private String mst_status;
	
	/**
	 * 비밀번호
	 */
	private String mst_password;
	
	/**
	 * 관리자비밀번호
	 */
	private String mst_adminpwd;
	
	/**
	 * 접근권한
	 */
	private String mst_access;
	
	/**
	 * 읽기전용비밀번호
	 */
	private String mst_readpwd;
	
	/**
	 * 조사데이터 갯수
	 */
	private String totcnt;
	
	/**
	 * 등록연도
	 */
	private String svy_year;
	
	/**
	 * 부서ID
	 */
	private String dept_id;

	/**
	 * 부서명
	 */
	private String dept_nm;
	
	/**
	 * 조사자명
	 */
	private String mber_nm;
	
	/**
	 * 조사자 고유ID
	 */
	private String esntl_id;
	
	/**
	 * 검색 Keyword
	 */
	private String searchKeyword;
	
	/**
	 * 검색조건
	 */
	private String searchCondition;
	
	/**
	 * 권한 조사자 고유 ID 리스트
	 */
	private String[] authorEsntlIdList;
	
	/**
	 * 조사유형
	 */
	private String svytype;
	
	/**
	 * 조사자 등급
	 */
	private String user_grade;
	
	/*
	 * 임도 조사구분(=차수)
	 */
	private String frdRnk;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSvyId() {
		return svyId;
	}
	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}

	public String getMst_corpname() {
		return mst_corpname;
	}

	public void setMst_corpname(String mst_corpname) {
		this.mst_corpname = mst_corpname;
	}

	public String getMst_partname() {
		return mst_partname;
	}

	public void setMst_partname(String mst_partname) {
		this.mst_partname = mst_partname;
	}

	public String getMst_create_user() {
		return mst_create_user;
	}

	public void setMst_create_user(String mst_create_user) {
		this.mst_create_user = mst_create_user;
	}

	public String getMst_admin_user() {
		return mst_admin_user;
	}

	public void setMst_admin_user(String mst_admin_user) {
		this.mst_admin_user = mst_admin_user;
	}

	public String getMst_admin_id() {
		return mst_admin_id;
	}

	public void setMst_admin_id(String mst_admin_id) {
		this.mst_admin_id = mst_admin_id;
	}

	public String getMst_registered() {
		return mst_registered;
	}

	public void setMst_registered(String mst_registered) {
		this.mst_registered = mst_registered;
	}

	public String getMst_status() {
		return mst_status;
	}

	public void setMst_status(String mst_status) {
		this.mst_status = mst_status;
	}

	public String getMst_password() {
		return mst_password;
	}

	public void setMst_password(String mst_password) {
		this.mst_password = mst_password;
	}

	public String getMst_adminpwd() {
		return mst_adminpwd;
	}

	public void setMst_adminpwd(String mst_adminpwd) {
		this.mst_adminpwd = mst_adminpwd;
	}

	public String getMst_readpwd() {
		return mst_readpwd;
	}

	public void setMst_readpwd(String mst_readpwd) {
		this.mst_readpwd = mst_readpwd;
	}

	public String getMst_access() {
		return mst_access;
	}

	public void setMst_access(String mst_access) {
		this.mst_access = mst_access;
	}

	public String getTotcnt() {
		return totcnt;
	}

	public void setTotcnt(String totcnt) {
		this.totcnt = totcnt;
	}

	public String getSvy_year() {
		return svy_year;
	}

	public void setSvy_year(String svy_year) {
		this.svy_year = svy_year;
	}

	public String getMst_deptname() {
		return mst_deptname;
	}

	public void setMst_deptname(String mst_deptname) {
		this.mst_deptname = mst_deptname;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}

	public String getMber_nm() {
		return mber_nm;
	}

	public void setMber_nm(String mber_nm) {
		this.mber_nm = mber_nm;
	}

	public String getEsntl_id() {
		return esntl_id;
	}

	public void setEsntl_id(String esntl_id) {
		this.esntl_id = esntl_id;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String[] getAuthorEsntlIdList() {
		return authorEsntlIdList;
	}

	public void setAuthorEsntlIdList(String[] authorEsntlIdList) {
		this.authorEsntlIdList = authorEsntlIdList;
	}

	public String getSvytype() {
		return svytype;
	}

	public void setSvytype(String svytype) {
		this.svytype = svytype;
	}

	public String getUser_grade() {
		return user_grade;
	}

	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}

	public String getFrdRnk() {
		return frdRnk;
	}

	public void setFrdRnk(String frdRnk) {
		this.frdRnk = frdRnk;
	}
	
}
