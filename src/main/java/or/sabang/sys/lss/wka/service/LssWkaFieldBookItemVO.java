
package or.sabang.sys.lss.wka.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssWkaFieldBookItemVO extends ComDefaultVO {

	
	/**
	 * 고유 아이디 
	 */
	private int gid;
	
	/**
	 * 공유방 ID
	 */
	private String mst_id;
	
	/**
	 * 로그인 ID
	 */
	private String login_id;

	/**
	 * 키값
	 */
	private int svy_fid;
	/**
	 * x좌표
	 */
	private String svy_lon;
	
	/**
	 * y좌표
	 */
	private String svy_lat;
	
	/**
	 * Geometry
	 */
	private String svy_data;

	/**
	 * 키워드
	 */
	private String svy_keyword;
	
	/**
	 * 라벨
	 */
	private String svy_label;
	
	/**
	 * 스타일
	 */
	private String svy_style;
	
	/**
	 * 메모
	 */
	private String svy_memo;
	
	/**
	 * 태그1
	 */
	private String svy_tag1;
	
	/**
	 * 태그2
	 */
	private String svy_tag2;
	
	/**
	 * 등록일자
	 */
	private String creat_dt;
	
	/**
	 * 수정일자
	 */
	private String last_updt_pnttm;

	/**
	 * 상세속성
	 */
	private String svy_attr;
	
	/**
	 * 구분
	 */
	private String se;

	/**
	 * 위도 Decimal Degree
	 */
	private Double latdd;
	/**
	 * 경도 Decimal Degree
	 */
	private Double londd;
	
	private String uniq_id;
	
	/*
	 * 시도명
	 */
	private String sd;
	/*
	 * 시군구명
//	 */
	private String sgg;
	/*
	 * 읍면동명
	 */
	private String emd;
	/*
	 * 리명
	 */
	private String ri;
	/*
	 * 지번
	 */
	private String jibun;
	/*
	 * 조사Id
	 */
	private String svyId;
	/**
	 * 읍면동코드
	 */
	private String stdgCd;	

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getMst_id() {
		return mst_id;
	}

	public void setMst_id(String mst_id) {
		this.mst_id = mst_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public int getSvy_fid() {
		return svy_fid;
	}

	public void setSvy_fid(int svy_fid) {
		this.svy_fid = svy_fid;
	}

	public String getSvy_lon() {
		return svy_lon;
	}

	public void setSvy_lon(String svy_lon) {
		this.svy_lon = svy_lon;
	}

	public String getSvy_lat() {
		return svy_lat;
	}

	public void setSvy_lat(String svy_lat) {
		this.svy_lat = svy_lat;
	}

	public String getSvy_data() {
		return svy_data;
	}

	public void setSvy_data(String svy_data) {
		this.svy_data = svy_data;
	}

	public String getSvy_keyword() {
		return svy_keyword;
	}

	public void setSvy_keyword(String svy_keyword) {
		this.svy_keyword = svy_keyword;
	}

	public String getSvy_label() {
		return svy_label;
	}

	public void setSvy_label(String svy_label) {
		this.svy_label = svy_label;
	}

	public String getSvy_style() {
		return svy_style;
	}

	public void setSvy_style(String svy_style) {
		this.svy_style = svy_style;
	}

	public String getSvy_memo() {
		return svy_memo;
	}

	public void setSvy_memo(String svy_memo) {
		this.svy_memo = svy_memo;
	}

	public String getSvy_tag1() {
		return svy_tag1;
	}

	public void setSvy_tag1(String svy_tag1) {
		this.svy_tag1 = svy_tag1;
	}

	public String getSvy_tag2() {
		return svy_tag2;
	}

	public void setSvy_tag2(String svy_tag2) {
		this.svy_tag2 = svy_tag2;
	}

	public String getCreat_dt() {
		return creat_dt;
	}

	public void setCreat_dt(String creat_dt) {
		this.creat_dt = creat_dt;
	}

	public String getLast_updt_pnttm() {
		return last_updt_pnttm;
	}

	public void setLast_updt_pnttm(String last_updt_pnttm) {
		this.last_updt_pnttm = last_updt_pnttm;
	}

	public String getSvy_attr() {
		return svy_attr;
	}

	public void setSvy_attr(String svy_attr) {
		this.svy_attr = svy_attr;
	}

	public String getSe() {
		return se;
	}

	public void setSe(String se) {
		this.se = se;
	}

	public Double getLatdd() {
		return latdd;
	}

	public void setLatdd(Double latdd) {
		this.latdd = latdd;
	}

	public Double getLondd() {
		return londd;
	}

	public void setLondd(Double londd) {
		this.londd = londd;
	}

	public String getUniq_id() {
		return uniq_id;
	}

	public void setUniq_id(String uniq_id) {
		this.uniq_id = uniq_id;
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

	public String getSvyId() {
		return svyId;
	}

	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}

	public String getStdgCd() {
		return stdgCd;
	}

	public void setStdgCd(String stdgCd) {
		this.stdgCd = stdgCd;
	}




	
}
