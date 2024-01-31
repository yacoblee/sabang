
package or.sabang.sys.vyt.frd.service;

import egovframework.com.cmm.ComDefaultVO;

public class VytFrdFieldBookItemVO extends ComDefaultVO {
	
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
	private String lon;
	
	/**
	 * y좌표
	 */
	private String lat;
	
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
	 * 일련번호
	 */
	private String sn;
	
	/**
	 * 위도 Decimal Degree
	 */
	private Double latdd;
	/**
	 * 경도 Decimal Degree
	 */
	private Double londd;
	/*
	 * 시도명
	 */
	private String svySd;
	/*
	 * 시군구명
	 */
	private String svySgg;
	/*
	 * 읍면동명
	 */
	private String svyEmd;
	/*
	 * 리명
	 */
	private String svyRi;
	/*
	 * 지번
	 */
	private String svyJibun;
	/*
	 * 조사Id
	 */
	private String svyId;
	/*
	 * 조사유형
	 */
	private String svyType;
	/*
	 * 예정거리
	 */
	private String schdst;
	/*
	 * 시점경도
	 */
	private String bpx;
	/*
	 * 시점위도
	 */
	private String bpy;
	
	private String bpxD;
	private String bpxM;
	private String bpxS;
	
	private String bpyD;
	private String bpyM;
	private String bpyS;
	/*
	 * 종점경도1
	 */
	private String epx1;
	/*
	 * 종점위도1
	 */
	private String epy1;
	
	private String epx1D;
	private String epx1M;
	private String epx1S;
	
	private String epy1D;
	private String epy1M;
	private String epy1S;
	/*
	 * 종점경도2
	 */
	private String epx2;
	/*
	 * 종점위도2
	 */
	private String epy2;
	
	private String epx2D; 
	private String epx2M; 
	private String epx2S; 
	                     
	private String epy2D; 
	private String epy2M; 
	private String epy2S; 
	
	
	private String smid;
	private String sldId;
	
	/*
	 * 임도 차수
	 */
	private String frdRnk; 
	
	/*
	 * 관할
	 */
	private String compentauth;
	private String routetype;
	private String frdtype;
	
	private String svyYear;
	
	private String subcompentauth;
	
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

	public String getLon() {
		return lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public void setLat(String lat) {
		this.lat = lat;
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getSmid() {
		return smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
	}

	public String getSchdst() {
		return schdst;
	}

	public void setSchdst(String schdst) {
		this.schdst = schdst;
	}

	public String getBpx() {
		return bpx;
	}

	public void setBpx(String bpx) {
		this.bpx = bpx;
	}

	public String getBpy() {
		return bpy;
	}

	public void setBpy(String bpy) {
		this.bpy = bpy;
	}

	public String getEpx1() {
		return epx1;
	}

	public void setEpx1(String epx1) {
		this.epx1 = epx1;
	}

	public String getEpy1() {
		return epy1;
	}

	public void setEpy1(String epy1) {
		this.epy1 = epy1;
	}

	public String getEpx2() {
		return epx2;
	}

	public void setEpx2(String epx2) {
		this.epx2 = epx2;
	}

	public String getEpy2() {
		return epy2;
	}

	public void setEpy2(String epy2) {
		this.epy2 = epy2;
	}

	public String getBpxD() {
		return bpxD;
	}

	public String getBpxM() {
		return bpxM;
	}

	public String getBpxS() {
		return bpxS;
	}

	public String getBpyD() {
		return bpyD;
	}

	public String getBpyM() {
		return bpyM;
	}

	public String getBpyS() {
		return bpyS;
	}

	public String getEpx1D() {
		return epx1D;
	}

	public String getEpx1M() {
		return epx1M;
	}

	public String getEpx1S() {
		return epx1S;
	}

	public String getEpy1D() {
		return epy1D;
	}

	public String getEpy1M() {
		return epy1M;
	}

	public String getEpy1S() {
		return epy1S;
	}

	public String getEpx2D() {
		return epx2D;
	}

	public String getEpx2M() {
		return epx2M;
	}

	public String getEpx2S() {
		return epx2S;
	}

	public String getEpy2D() {
		return epy2D;
	}

	public String getEpy2M() {
		return epy2M;
	}

	public String getEpy2S() {
		return epy2S;
	}

	public void setBpxD(String bpxD) {
		this.bpxD = bpxD;
	}

	public void setBpxM(String bpxM) {
		this.bpxM = bpxM;
	}

	public void setBpxS(String bpxS) {
		this.bpxS = bpxS;
	}

	public void setBpyD(String bpyD) {
		this.bpyD = bpyD;
	}

	public void setBpyM(String bpyM) {
		this.bpyM = bpyM;
	}

	public void setBpyS(String bpyS) {
		this.bpyS = bpyS;
	}

	public void setEpx1D(String epx1d) {
		epx1D = epx1d;
	}

	public void setEpx1M(String epx1m) {
		epx1M = epx1m;
	}

	public void setEpx1S(String epx1s) {
		epx1S = epx1s;
	}

	public void setEpy1D(String epy1d) {
		epy1D = epy1d;
	}

	public void setEpy1M(String epy1m) {
		epy1M = epy1m;
	}

	public void setEpy1S(String epy1s) {
		epy1S = epy1s;
	}

	public void setEpx2D(String epx2d) {
		epx2D = epx2d;
	}

	public void setEpx2M(String epx2m) {
		epx2M = epx2m;
	}

	public void setEpx2S(String epx2s) {
		epx2S = epx2s;
	}

	public void setEpy2D(String epy2d) {
		epy2D = epy2d;
	}

	public void setEpy2M(String epy2m) {
		epy2M = epy2m;
	}

	public void setEpy2S(String epy2s) {
		epy2S = epy2s;
	}

	public String getSldId() {
		return sldId;
	}

	public void setSldId(String sldId) {
		this.sldId = sldId;
	}

	public String getFrdRnk() {
		return frdRnk;
	}

	public void setFrdRnk(String frdRnk) {
		this.frdRnk = frdRnk;
	}

	public String getCompentauth() {
		return compentauth;
	}

	public void setCompentauth(String compentauth) {
		this.compentauth = compentauth;
	}

	public String getRoutetype() {
		return routetype;
	}

	public void setRoutetype(String routetype) {
		this.routetype = routetype;
	}

	public String getFrdtype() {
		return frdtype;
	}

	public void setFrdtype(String frdtype) {
		this.frdtype = frdtype;
	}

	public String getSvyYear() {
		return svyYear;
	}

	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}

	public String getSubcompentauth() {
		return subcompentauth;
	}

	public void setSubcompentauth(String subcompentauth) {
		this.subcompentauth = subcompentauth;
	}
}
