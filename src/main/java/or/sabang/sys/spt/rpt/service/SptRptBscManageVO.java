package or.sabang.sys.spt.rpt.service;

import java.util.Date;

import egovframework.com.cmm.ComDefaultVO;

public class SptRptBscManageVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 조사ID
	 */
	private String reprt_id;
	/**
	 * 보고서구분
	 */
	private String reprt_se;
	/**
	 * 보고서작성일
	 */
	private String reprt_de;
	/**
	 * 보고서작성자
	 */
	private String reprt_wrter;
	/**
	 * 등록일자
	 */
	private Date create_de;	
	/**
	 * 보고서등록여부
	 */
	private String reprt_create_at;
	/**
	 * 보고서명
	 */
	private String reprt_orginl_nm;	
	
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
    
    /**
	 * 첨부파일 갯수
	 */
    private int cnt;
    
    /**
	 * 최신 첨부파일명
	 */
    private String orginl_nm;
	
	public String getReprt_id() {
		return reprt_id;
	}
	public void setReprt_id(String reprt_id) {
		this.reprt_id = reprt_id;
	}
	public String getReprt_se() {
		return reprt_se;
	}
	public void setReprt_se(String reprt_se) {
		this.reprt_se = reprt_se;
	}
	public String getReprt_de() {
		return reprt_de;
	}
	public void setReprt_de(String reprt_de) {
		this.reprt_de = reprt_de;
	}
	public String getReprt_wrter() {
		return reprt_wrter;
	}
	public void setReprt_wrter(String reprt_wrter) {
		this.reprt_wrter = reprt_wrter;
	}
	public Date getCreate_de() {
		return create_de;
	}
	public void setCreate_de(Date create_de) {
		this.create_de = create_de;
	}
	public String getReprt_create_at() {
		return reprt_create_at;
	}
	public void setReprt_create_at(String reprt_create_at) {
		this.reprt_create_at = reprt_create_at;
	}
	public String getReprt_orginl_nm() {
		return reprt_orginl_nm;
	}
	public void setReprt_orginl_nm(String reprt_orginl_nm) {
		this.reprt_orginl_nm = reprt_orginl_nm;
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
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getOrginl_nm() {
		return orginl_nm;
	}
	public void setOrginl_nm(String orginl_nm) {
		this.orginl_nm = orginl_nm;
	}
}
