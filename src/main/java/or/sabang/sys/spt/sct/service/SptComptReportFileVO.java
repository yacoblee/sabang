package or.sabang.sys.spt.sct.service;

import java.util.Date;

import egovframework.com.cmm.ComDefaultVO;

public class SptComptReportFileVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 조사ID
	 */
	private String gid;
	/**
	 * 파일ID
	 */
	private String file_id;
	/**
	 * 파일원본명
	 */
	private String file_orginl_nm;
	/**
	 * 파일저장명
	 */
	private String file_stre_nm;
	/**
	 * 파일경로
	 */
	private String file_path;
	/**
	 * 파일용량
	 */
	private long file_size;
	/**
	 * 등록일자
	 */
	private Date create_de;
	/**
	 * 작성자
	 */
	private String file_wrter;
	/**
	 * 파일구분
	 */
	private String se;
	/** 검색조건 */
    private String searchCondition = "";

    /** 검색Keyword */
    private String searchKeyword = "";

    /** 검색사용여부 */
    private String searchUseYn = "";
    
	/** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = 10;

    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
    /** 부서명  */
    private String deptNm;
    /** 발주처 시도  */
    private String orderSd;
    /** 발주처 시군구  */
    private String orderSgg;
    /** 사업명  */
    private String cnstrName;
    /** 사업년도  */
    private String cnstrYear;
    /** 기관명  */
    private String corpNm;
    
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFile_orginl_nm() {
		return file_orginl_nm;
	}
	public void setFile_orginl_nm(String file_orginl_nm) {
		this.file_orginl_nm = file_orginl_nm;
	}
	public String getFile_stre_nm() {
		return file_stre_nm;
	}
	public void setFile_stre_nm(String file_stre_nm) {
		this.file_stre_nm = file_stre_nm;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public Date getCreate_de() {
		return create_de;
	}
	public void setCreate_de(Date create_de) {
		this.create_de = create_de;
	}
	public String getFile_wrter() {
		return file_wrter;
	}
	public void setFile_wrter(String file_wrter) {
		this.file_wrter = file_wrter;
	}
	public String getSe() {
		return se;
	}
	public void setSe(String se) {
		this.se = se;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchUseYn() {
		return searchUseYn;
	}
	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getOrderSd() {
		return orderSd;
	}
	public void setOrderSd(String orderSd) {
		this.orderSd = orderSd;
	}
	public String getOrderSgg() {
		return orderSgg;
	}
	public void setOrderSgg(String orderSgg) {
		this.orderSgg = orderSgg;
	}
	public String getCnstrName() {
		return cnstrName;
	}
	public void setCnstrName(String cnstrName) {
		this.cnstrName = cnstrName;
	}
	public String getCnstrYear() {
		return cnstrYear;
	}
	public void setCnstrYear(String cnstrYear) {
		this.cnstrYear = cnstrYear;
	}
	public String getCorpNm() {
		return corpNm;
	}
	public void setCorpNm(String corpNm) {
		this.corpNm = corpNm;
	}
}
