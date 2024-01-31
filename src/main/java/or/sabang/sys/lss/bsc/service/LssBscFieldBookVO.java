package or.sabang.sys.lss.bsc.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssBscFieldBookVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	/**
	 * 조사ID
	 */
	private String id;
	/**
	 * 회사명
	 */
	private String mst_corpname;
	/**
	 * 사업지구명
	 */
	private String mst_partname;
	/**
	 * 생성자ID
	 */
	private String mst_create_user;
	
	/**
	 * 관리자ID
	 */
	private String mst_admin_user;
	
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
