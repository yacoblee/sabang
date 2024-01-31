package or.sabang.sys.vyt.frd.service;

import egovframework.com.cmm.ComDefaultVO;

public class VytFrdStripLandVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
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
	 * 지목
	 */
	private String jimok;
	/**
	 * 위도
	 */
	private String lat;
	/**
	 * 경도
	 */
	private String lon;
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
	 * 조사자 등급
	 */
	private String user_grade;
	
	private String smid;
	private String smgeometry;
	/*
	 * 대상지명칭
	 */
	private String sld_nm;
	/*
	 * 생성자ID
	 */
	private String sld_create_user;
	/*
	 * 관리자ID
	 */
	private String sld_admin_user;
	/*
	 * 대상지ID
	 */
	private String sldlabel;
	/*
	 * 노선종류
	 */
	private String routetype;
	/*
	 * 임도종류
	 */
	private String frdtype;
	/*
	 * 시설예정거리
	 */
	private String schdst;
	/*
	 * 기타
	 */
	private String etc;
	/*
	 * 등록일
	 */
	private String creat_dt;
	/*
	 * 수정일
	 */
	private String last_updt_pnttm;
	
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
	
	/*
	 * 관할청
	 */
	private String compentauth;
	
	/*
	 * 전자야장에 쓰이는 smid == gid
	 */
	private String gid;
	
	/*
	 * 문화재목록
	 */
	private String culture;
	
	private String addr;
	
	private String pnuCode;
	
	/*
	 * 필지갯수
	 */
	private String parcelCnt;
	
	/*
	 * 소유구분
	 */
	private String posesnSe;
	
	/*
	 * 생태자연도
	 */
	private String ecoNtrMap;
	
	/*
	 * 멸종위기종서식 현황
	 */
	private String endSpc;
	
	/*
	 * 특별산림보호종
	 */
	private String spcFrsPrt;
	
	/*
	 * 공유방ID
	 */
	private String mstId;
	
	/*
	 * 버퍼 사이즈
	 */
	private String bufferSize;
	
	/*
	 * 세부 관할
	 */
	private String subcompentauth;
	
	/*
	 * 임도라인 WKT
	 */
	private String frdLneWkt;
	
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

	public String getMst_deptname() {
		return mst_deptname;
	}

	public void setMst_deptname(String mst_deptname) {
		this.mst_deptname = mst_deptname;
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

	public String getMst_access() {
		return mst_access;
	}

	public void setMst_access(String mst_access) {
		this.mst_access = mst_access;
	}

	public String getMst_readpwd() {
		return mst_readpwd;
	}

	public void setMst_readpwd(String mst_readpwd) {
		this.mst_readpwd = mst_readpwd;
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

	public String getUser_grade() {
		return user_grade;
	}

	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}

	public String getSmid() {
		return smid;
	}
	
	public void setSmid(String smid) {
		this.smid = smid;
	}

	public String getSmgeometry() {
		return smgeometry;
	}

	public String getSld_nm() {
		return sld_nm;
	}

	public String getSld_create_user() {
		return sld_create_user;
	}

	public String getSld_admin_user() {
		return sld_admin_user;
	}

	public String getRoutetype() {
		return routetype;
	}

	public String getFrdtype() {
		return frdtype;
	}

	public String getSchdst() {
		return schdst;
	}

	public String getEtc() {
		return etc;
	}

	public String getCreat_dt() {
		return creat_dt;
	}

	public String getLast_updt_pnttm() {
		return last_updt_pnttm;
	}

	public void setSmgeometry(String smgeometry) {
		this.smgeometry = smgeometry;
	}

	public void setSld_nm(String sld_nm) {
		this.sld_nm = sld_nm;
	}

	public void setSld_create_user(String sld_create_user) {
		this.sld_create_user = sld_create_user;
	}

	public void setSld_admin_user(String sld_admin_user) {
		this.sld_admin_user = sld_admin_user;
	}

	public void setRoutetype(String routetype) {
		this.routetype = routetype;
	}

	public void setFrdtype(String frdtype) {
		this.frdtype = frdtype;
	}

	public void setSchdst(String schdst) {
		this.schdst = schdst;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public void setCreat_dt(String creat_dt) {
		this.creat_dt = creat_dt;
	}

	public void setLast_updt_pnttm(String last_updt_pnttm) {
		this.last_updt_pnttm = last_updt_pnttm;
	}

	public String getSldlabel() {
		return sldlabel;
	}

	public void setSldlabel(String sldlabel) {
		this.sldlabel = sldlabel;
	}

	public String getBpx() {
		return bpx;
	}

	public String getBpy() {
		return bpy;
	}

	public String getEpx1() {
		return epx1;
	}

	public String getEpy1() {
		return epy1;
	}

	public String getEpx2() {
		return epx2;
	}

	public String getEpy2() {
		return epy2;
	}

	public void setBpx(String bpx) {
		this.bpx = bpx;
	}

	public void setBpy(String bpy) {
		this.bpy = bpy;
	}

	public void setEpx1(String epx1) {
		this.epx1 = epx1;
	}

	public void setEpy1(String epy1) {
		this.epy1 = epy1;
	}

	public void setEpx2(String epx2) {
		this.epx2 = epx2;
	}

	public void setEpy2(String epy2) {
		this.epy2 = epy2;
	}

	public String getCompentauth() {
		return compentauth;
	}
	public void setCompentauth(String compentauth) {
		this.compentauth = compentauth;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPnuCode() {
		return pnuCode;
	}

	public void setPnuCode(String pnuCode) {
		this.pnuCode = pnuCode;
	}

	public String getPosesnSe() {
		return posesnSe;
	}

	public void setPosesnSe(String posesnSe) {
		this.posesnSe = posesnSe;
	}

	public String getJimok() {
		return jimok;
	}

	public void setJimok(String jimok) {
		this.jimok = jimok;
	}

	public String getParcelCnt() {
		return parcelCnt;
	}

	public void setParcelCnt(String parcelCnt) {
		this.parcelCnt = parcelCnt;
	}

	public String getEcoNtrMap() {
		return ecoNtrMap;
	}

	public String getEndSpc() {
		return endSpc;
	}

	public String getSpcFrsPrt() {
		return spcFrsPrt;
	}

	public void setEcoNtrMap(String ecoNtrMap) {
		this.ecoNtrMap = ecoNtrMap;
	}

	public void setEndSpc(String endSpc) {
		this.endSpc = endSpc;
	}

	public void setSpcFrsPrt(String spcFrsPrt) {
		this.spcFrsPrt = spcFrsPrt;
	}

	public String getMstId() {
		return mstId;
	}

	public void setMstId(String mstId) {
		this.mstId = mstId;
	}

	public String getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(String bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getSubcompentauth() {
		return subcompentauth;
	}

	public void setSubcompentauth(String subcompentauth) {
		this.subcompentauth = subcompentauth;
	}

	public String getFrdLneWkt() {
		return frdLneWkt;
	}

	public void setFrdLneWkt(String frdLneWkt) {
		this.frdLneWkt = frdLneWkt;
	}
}
