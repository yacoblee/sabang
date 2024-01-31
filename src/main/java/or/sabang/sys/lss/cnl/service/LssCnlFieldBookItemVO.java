
package or.sabang.sys.lss.cnl.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssCnlFieldBookItemVO extends ComDefaultVO {
	
	private static final long serialVersionUID = 1L;
	
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
	private String svyLon;
	
	/**
	 * y좌표
	 */
	private String svyLat;
	
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
	
	/* smid */
	private String smid;
	/* 조사ID */
	private String svyId;
	/* 조사TYPE */
	private String svyType;
	/* 지정사유 */
	private String appnResn;
	/* 사업종가능 */
	private String weakAppnBsType;
	/* 구역설정 */
	private String weakAppnAreaSet;
	/* 선정사유 */
	private String weakAppnSltnHy;
	/* 지정번호 */
	private String appnNo;
	/* 당시현황 */
	private String weakAppnSttus;
	/* 당시종합의견 */
	private String weakAppnMstOpn;
	/* 지정년도 */
	private String appnYear;
	/* 지정면적 */
	private String appnArea;
	/* 가능여부 */
	private String weakAppnPosYn;

	/* 위도 */
	private String svy_latD;
	private String svy_latM;
	private String svy_latS;
	/* 경도 */        
	private String svy_lonD;
	private String svy_lonM;
	private String svy_lonS;
	
	/* 시도명 */
	private String svySd;
	/* 시군구명 */
	private String svySgg;
	/* 읍면동명 */
	private String svyEmd;
	/* 리명 */
	private String svyRi;
	/* 지번 */
	private String svyJibun;
	
	/* 순번 */
	private String sn;
	/* 관할1 */
	private String region1;

	/*
	 * 조사ID(parameter용)
	 */
	private String svyid;
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

	public String getSmid() {
		return smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
	}

	public String getAppnResn() {
		return appnResn;
	}

	public void setAppnResn(String appnResn) {
		this.appnResn = appnResn;
	}

	public String getWeakAppnBsType() {
		return weakAppnBsType;
	}

	public void setWeakAppnBsType(String weakAppnBsType) {
		this.weakAppnBsType = weakAppnBsType;
	}

	public String getWeakAppnAreaSet() {
		return weakAppnAreaSet;
	}

	public void setWeakAppnAreaSet(String weakAppnAreaSet) {
		this.weakAppnAreaSet = weakAppnAreaSet;
	}

	public String getWeakAppnSltnHy() {
		return weakAppnSltnHy;
	}

	public void setWeakAppnSltnHy(String weakAppnSltnHy) {
		this.weakAppnSltnHy = weakAppnSltnHy;
	}

	public String getAppnNo() {
		return appnNo;
	}

	public void setAppnNo(String appnNo) {
		this.appnNo = appnNo;
	}

	public String getWeakAppnSttus() {
		return weakAppnSttus;
	}

	public void setWeakAppnSttus(String weakAppnSttus) {
		this.weakAppnSttus = weakAppnSttus;
	}

	public String getWeakAppnMstOpn() {
		return weakAppnMstOpn;
	}

	public void setWeakAppnMstOpn(String weakAppnMstOpn) {
		this.weakAppnMstOpn = weakAppnMstOpn;
	}

	public String getAppnYear() {
		return appnYear;
	}

	public void setAppnYear(String appnYear) {
		this.appnYear = appnYear;
	}

	public String getAppnArea() {
		return appnArea;
	}

	public void setAppnArea(String appnArea) {
		this.appnArea = appnArea;
	}

	public String getWeakAppnPosYn() {
		return weakAppnPosYn;
	}

	public void setWeakAppnPosYn(String weakAppnPosYn) {
		this.weakAppnPosYn = weakAppnPosYn;
	}

	public String getSvy_latD() {
		return svy_latD;
	}

	public void setSvy_latD(String svy_latD) {
		this.svy_latD = svy_latD;
	}

	public String getSvy_latM() {
		return svy_latM;
	}

	public void setSvy_latM(String svy_latM) {
		this.svy_latM = svy_latM;
	}

	public String getSvy_latS() {
		return svy_latS;
	}

	public void setSvy_latS(String svy_latS) {
		this.svy_latS = svy_latS;
	}

	public String getSvy_lonD() {
		return svy_lonD;
	}

	public void setSvy_lonD(String svy_lonD) {
		this.svy_lonD = svy_lonD;
	}

	public String getSvy_lonM() {
		return svy_lonM;
	}

	public void setSvy_lonM(String svy_lonM) {
		this.svy_lonM = svy_lonM;
	}

	public String getSvy_lonS() {
		return svy_lonS;
	}

	public void setSvy_lonS(String svy_lonS) {
		this.svy_lonS = svy_lonS;
	}

	public String getSvySd() {
		return svySd;
	}

	public void setSvySd(String svySd) {
		this.svySd = svySd;
	}

	public String getSvySgg() {
		return svySgg;
	}

	public void setSvySgg(String svySgg) {
		this.svySgg = svySgg;
	}

	public String getSvyEmd() {
		return svyEmd;
	}

	public void setSvyEmd(String svyEmd) {
		this.svyEmd = svyEmd;
	}

	public String getSvyRi() {
		return svyRi;
	}

	public void setSvyRi(String svyRi) {
		this.svyRi = svyRi;
	}

	public String getSvyJibun() {
		return svyJibun;
	}

	public void setSvyJibun(String svyJibun) {
		this.svyJibun = svyJibun;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRegion1() {
		return region1;
	}

	public void setRegion1(String region1) {
		this.region1 = region1;
	}

	public String getSvyLon() {
		return svyLon;
	}

	public void setSvyLon(String svyLon) {
		this.svyLon = svyLon;
	}

	public String getSvyLat() {
		return svyLat;
	}

	public void setSvyLat(String svyLat) {
		this.svyLat = svyLat;
	}

	public String getSvyId() {
		return svyId;
	}

	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}

	public String getSvyType() {
		return svyType;
	}

	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}

	public String getSvyid() {
		return svyid;
	}

	public void setSvyid(String svyid) {
		this.svyid = svyid;
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

	public String getStdgCd() {
		return stdgCd;
	}

	public void setStdgCd(String stdgCd) {
		this.stdgCd = stdgCd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
