package or.sabang.sys.spt.rpt.service;

import java.util.Date;

import egovframework.com.cmm.ComDefaultVO;

public class SptRptLcpReportSvyComptVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/**
	 * gid
	 */
	private String gid;
	/**
	 * 조사방ID
	 */
	private String mstId;
	/**
	 * 조사ID
	 */
	private String id;
	/**
	 * 조사유형
	 */
	private String type;
	/**
	 * 조사연도
	 */
	private String year;
	
	/**
	 * 시도
	 */
	private String sd;
	/**
	 * 시군구
	 */
	private String sgg;
	/**
	 * 읍면동
	 */
	private String emd;
	/**
	 * 리
	 */
	private String ri;
	/**
	 * 지번
	 */
	private String jibun;
	
	/**
	 * 등록일자
	 */
	private String createDt;
	/**
	 * 작성자
	 */
	private String writer;
	
	/**
	 * 공유방명
	 */
	private String mstNm;
	/**
	 * 조사일자-월
	 */
	private String month;
	
	/**
	 * 최종판정등급
	 */
	private String lastGrd;
	
	public String getMstId() {
		return mstId;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getSgg() {
		return sgg;
	}
	public void setSgg(String sgg) {
		this.sgg = sgg;
	}
	public String getEmd() {
		return emd;
	}
	public void setEmd(String emd) {
		this.emd = emd;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getMstNm() {
		return mstNm;
	}
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getLastGrd() {
		return lastGrd;
	}
	public void setLastGrd(String lastGrd) {
		this.lastGrd = lastGrd;
	}
}