package or.sabang.sys.spt.rpt.service;

import java.util.Date;

import egovframework.com.cmm.ComDefaultVO;

public class SptRptCnlReportSvyComptVO extends ComDefaultVO {

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
	 * 관할1
	 */
	private String region1;
	
	/**
	 * 관할2
	 */
	private String region2;
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
	 * 위도
	 */
	private String lat;
	/**
	 * 경도
	 */
	private String lon;
	/**
	 * 등록일자
	 */
	private String createDt;
	/**
	 * 작성자
	 */
	private String writer;
	/**
	 * 첨부파일 갯수
	 */
	private String cnt;
	/**
	 * GEOM
	 */
	private String geom;
	/**
	 * at
	 */
	private String at;
	/**
	 * 공유방명
	 */
	private String mstNm;
	
	/**
	 * 실태조사 필요성
	 */
	private String ncssty;
	
	/**
	 * 실태조사 필요성 패스여부
	 */
	private String ncsstyPassAt;
	
	/**
	 * 조사일자-월
	 */
	private String month;
	
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
	public String getRegion1() {
		return region1;
	}
	public void setRegion1(String region1) {
		this.region1 = region1;
	}
	public String getRegion2() {
		return region2;
	}
	public void setRegion2(String region2) {
		this.region2 = region2;
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
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
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
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getGeom() {
		return geom;
	}
	public void setGeom(String geom) {
		this.geom = geom;
	}
	public String getAt() {
		return at;
	}
	public void setAt(String at) {
		this.at = at;
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
	public String getNcssty() {
		return ncssty;
	}
	public void setNcssty(String ncssty) {
		this.ncssty = ncssty;
	}
	public String getNcsstyPassAt() {
		return ncsstyPassAt;
	}
	public void setNcsstyPassAt(String ncsstyPassAt) {
		this.ncsstyPassAt = ncsstyPassAt;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
}
