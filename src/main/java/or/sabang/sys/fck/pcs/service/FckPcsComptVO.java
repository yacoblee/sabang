package or.sabang.sys.fck.pcs.service;

import egovframework.com.cmm.ComDefaultVO;

public class FckPcsComptVO  extends ComDefaultVO {

	private static final long serialVersionUID = 1L;

	/*gid*/
	private String gid;	
	/*svy_fid*/
	private String fid;	
	/*mst_id*/
	private String mstId;	
	/*조사ID*/
	private String svyId;	
	/*조사유형*/
	private String svyType;	
	/*시도*/
	private String svySd;	
	/*시군구*/
	private String svySgg;	
	/*읍면동*/
	private String svyEmd;	
	/*리*/
	private String svyRi;	
	/*지번*/
	private String svyJibun;	
	/*지목*/
	private String jimok;	
	/*점검일자*/
	private String svyDt;	
	/*위치정보_X*/
	private String px;	
	/*위치정보_Y*/
	private String py;	
	/*위경도*/
	private String svyLatLon;
	/*등록일자*/
	private String creatDt;	
	/*수정일자*/
	private String lastUpdtPnttm;	
	/*속칭*/
	private String commonly;	
	/*사방댐종류*/
	private String ecnrknd;	
	/*형식*/
	private String svyform;	
	/*상장*/
	private String fcltuprhg;	
	/*하장*/
	private String fcltlwrhg;	
	/*높이(m) 유효고*/
	private String fcltstkhg;	
	/*점검자*/
	private String svyUser;	
	/*작성자ID*/
	private String loginId;
	/*관리번호*/
	private String ecnrrnum;	
	/*국가지점번호*/
	private String nationspotnum;	
	/*위도도*/
	private String svylatd;	
	/*위도분*/
	private String svylatm;	
	/*위도초*/
	private String svylats;	
	/*경도도*/
	private String svylond;	
	/*경도분*/
	private String svylonm;	
	/*경도초*/
	private String svylons;	
	/*본댐 측정*/
	private String orginldamval;	
	/*측벽 측정*/
	private String sidewallval;	
	/*물받이 측정*/
	private String dwnsptval;	
	/*본댐*/
	private String orginldam;	
	/*측벽*/
	private String sidewall;	
	/*물받이*/
	private String dwnspt;	
	/*수문*/
	private String flugtjdgval;	
	/*식생*/
	private String vtnsttusjdgval;	
	/*안전시설*/
	private String sffcjdgval;	
	/*접근도로*/
	private String accssrdjdgval;	
	/*기타*/
	private String etcjdgval;	
	/*저사상태*/
	private String snddpsitjdgval;	
	/*저사량*/
	private String snddpsitval;	
	/*점검결과*/
	private String fckrslt;	
	/*조치사항*/
	private String mngmtr;	
	/*지정해제*/
	private String appnrelis;	
	/*종합의견 1*/
	private String opinion1;	
	/*종합의견 2*/
	private String opinion2;	
	/*종합의견 3*/
	private String opinion3;	
	/*종합의견 4*/
	private String opinion4;	
	/*종합의견 5*/
	private String opinion5;	
	/*주요시설_특이사항*/
	private String mainfcltspecialnote;	
	/*부대시설_특이사항*/
	private String subfcltspecialnote;	
	/*기타_특이사항*/
	private String etcspecialnote;	
	/*종류*/
	private String knd;	
	/*높이(m) 전고*/
	private String tthghjdgval;	
	/*천단폭*/
	private String wotdjdgval;	
	/*시공비(천원)*/
	private String cnstrcost;	
	/*위도_산사태*/
	private String latdlndsld;	
	/*위분_산사태*/
	private String latmlndsld;	
	/*위초_산사태*/
	private String latslndsld;	
	/*경도_산사태*/
	private String londlndsld;	
	/*경분_산사태*/
	private String lonmlndsld;	
	/*경초_산사태*/
	private String lonslndsld;	
	/*위도_현지계측*/
	private String latdacplcsld;	
	/*위분_현지계측*/
	private String latmacplcsld;	
	/*위초_현지계측*/
	private String latsacplcsld;	
	/*경도_현지계측*/
	private String londacplcsld;	
	/*경분_현지계측*/
	private String lonmacplcsld;	
	/*경초_현지계측*/
	private String lonsacplcsld;	
	/*측벽 길이*/
	private String sidewalllng;	
	/*측벽 높이*/
	private String sidewallheg;	
	/*물받이 가로*/
	private String dwnspthrz;	
	/*물받이 세로*/
	private String dwnsptvtc;	
	/*svylon*/
	private String lon;	
	/*svylat*/
	private String lat;	
	/*시설년도*/
	private String fcltyear;	
	/*사진*/
	private String photo;	
	/*사진태그*/
	private String phototag;	
	/*물받이.균열*/
	private String dwnsptcrk;	
	/*물받이.기초부세굴*/
	private String dwnsptbasicscour;	
	/*물받이.파손*/
	private String dwnsptdmg;	
	/*본댐.균열*/
	private String orginldamcrk;	
	/*본댐.기초부세굴*/
	private String orginldambasicscour;	
	/*본댐.박리*/
	private String orginldamplng;	
	/*본댐.수문시설*/
	private String orginldamwtgate;	
	/*본댐.파손*/
	private String orginldamdmg;	
	/*측벽.균열*/
	private String sidewallcrk;	
	/*측벽.기초부세굴*/
	private String sidewallbasicscour;	
	/*측벽.파손*/
	private String sidewalldmg;	
	/*조사자보정*/
	private String svycrctn;	
	/*콘크리트 압축강도(슈미트해머)*/
	private String cncrtcmprsstrn;	
	/*콘크리트 균열깊이(초음파탐상)*/
	private String cncrtcrkdpt;	
	/*1. 현재 저사량 (저사고)*/
	private String nowsnddpsitval;	
	/*2. 생활권 (주요 보호시설과의 거리)*/
	private String lifezone;	
	/*3. 계상물매*/
	private String riverbedgradient;	
	/*4. 토석이동량*/
	private String soilmoveamnt;	
	/*총점 (인자별 준설기준 값의 합)*/
	private String totalscore;	
	/*사방댐 준설여부 판정(총점 기준)*/
	private String drdgnatjdg;	
	/*압축강도_결과값*/
	private String cmprsstrnrslt;	
	/*압축강도_콘크리트1*/
	private String cmprsstrncncrt1;	
	/*압축강도_콘크리트2*/
	private String cmprsstrncncrt2;	
	/*압축강도_평균값1*/
	private String cmprsstrnavg1;	
	/*압축강도_평균값2*/
	private String cmprsstrnavg2;	
	/*압축강도_평균값3*/
	private String cmprsstrnavg3;	
	/*압축강도_평균값4*/
	private String cmprsstrnavg4;	
	/*판정점수_구조물이상*/
	private String jdgpntstrct;	
	/*판정점수_균열*/
	private String jdgpntcrk;	
	/*판정점수_단차*/
	private String jdgpntstp;	
	/*판정점수_수목생장이상*/
	private String jdgpntwdpt;	
	/*판정점수_지질특성*/
	private String jdgpntgeo;	
	/*판정점수_지하수용출*/
	private String jdgpntugrwtr;	
	/*판정점수_지형특성*/
	private String jdgpnttopo;	
	/*판정점수_토양특성*/
	private String jdgpntsoil;	
	/*고도*/
	private String pz;	
	/*반수면 상장*/
	private String hsfcltuprhg;	
	/*반수면 하장*/
	private String hsfcltlwrhg;	
	/*반수면 전고*/
	private String hstthgh;	
	/*반수면 유효고*/
	private String hsfcltstkhg;	
	/*좌안어깨 길이*/
	private String larmlen;	
	/*좌안어깨 폭*/
	private String larmwid;	
	/*방수로 윗길이*/
	private String wtrprtoplen;	
	/*방수로 아래길이*/
	private String wtrprbtmlen;	
	/*방수로 폭*/
	private String wtrprwid;	
	/*방수로 높이*/
	private String wtrprhg;	
	/*방수로 비탈길이*/
	private String wtrprslopelen;	
	/*우안어깨 길이*/
	private String rarmlen;	
	/*우안어깨 폭*/
	private String rarmwid;	
	/*좌안측벽 길이*/
	private String lsidewalllen;	
	/*좌안측벽 높이(상)*/
	private String lsidewallhghigh;	
	/*좌안측벽 높이(하)*/
	private String lsidewallhglow;	
	/*우안측벽 길이*/
	private String rsidewalllen;	
	/*우안측벽 높이(상)*/
	private String rsidewallhghigh;	
	/*우안측벽 높이(하)*/
	private String rsidewallhglow;	
	/*물받이 윗너비*/
	private String dwnsptwidhigh;	
	/*물받이 아래너비*/
	private String dwnsptwidlow;	
	/*물받이 길이*/
	private String dwnsptlen;	
	/*물받이 수직벽가로*/
	private String dwnsptverwid;	
	/*물받이 수직벽세로*/
	private String dwnsptverhg;	
	/*지정면적*/
	private String dsntarea;	
	/*사방사업명*/
	private String ecwnm;	
	/*주요공작물(시설명)*/
	private String mainstruct;	
	/*현재저사량.가중치*/
	private String nowsnddpsitvalweight;	
	/*현재저사량.계수*/
	private String nowsnddpsitvalcoeff;	
	/*현재저사량.구분기분*/
	private String nowsnddpsitvaldivision;	
	/*현재저사량.가중치*/
	private String nowsnddpsitvaldrdgn;	
	/*생활권.가중치*/
	private String lifezoneweight;	
	/*생활권.계수*/
	private String lifezonecoeff;	
	/*생활권.구분기분*/
	private String lifezonedivision;	
	/*생활권.가중치*/
	private String lifezonedrdgn;	
	/*계상물매.가중치*/
	private String riverbedgradientweight;	
	/*계상물매.계수*/
	private String riverbedgradientcoeff;	
	/*계상물매.구분기분*/
	private String riverbedgradientdivision;	
	/*계상물매.가중치*/
	private String riverbedgradientdrdgn;	
	/*토석이동량.가중치*/
	private String soilmoveamntweight;	
	/*토석이동량.계수*/
	private String soilmoveamntcoeff;	
	/*토석이동량.구분기분*/
	private String soilmoveamntdivision;	
	/*토석이동량.가중치*/
	private String soilmoveamntdrdgn;	
	/*압축강도_콘크리트3*/
	private String cmprsstrncncrt3;	
	/*압축강도_콘크리트4*/
	private String cmprsstrncncrt4;	
	/*완료*/
	private String complt;	
	/*균열깊이_결과값*/
	private String crkdptrslt;	
	/*균열깊이_콘크리트1*/
	private String crkdptcncrt1;	
	/*균열깊이_콘크리트2*/
	private String crkdptcncrt2;	
	/*균열깊이_콘크리트3*/
	private String crkdptcncrt3;	
	/*균열깊이_콘크리트4*/
	private String crkdptcncrt4;	
	/*균열깊이_평균값1*/
	private String crkdptavg1;	
	/*균열깊이_평균값2*/
	private String crkdptavg2;	
	/*균열깊이_평균값3*/
	private String crkdptavg3;	
	/*균열깊이_평균값4*/
	private String crkdptavg4;	
	/*위치도*/
	private String lcmap;	
	/* 위치도 지도 타입 */
	private String mapType;
	/* 위치도 줌 레벨 */
	private String changedZoom;
	/* 위치도 인덱스 */
	private int locImgIdx;
	/* 연도 */
	private String svyYear;
	/* 공유방 명칭 */
	private String mstNm;
	/* 조사일자 - 월 */
	private String svyMonth;
	/*사진길이*/
	private String photoLen;
	/*조사자보정점수*/
	private String svycrctnscore;	
	/*물받이.균열점수*/
	private String dwnsptcrkscore;	
	/*물받이.기초부세굴점수*/
	private String dwnsptbasicscourscore;	
	/*물받이.파손점수*/
	private String dwnsptdmgscore;	
	/*본댐.균열점수*/
	private String orginldamcrkscore;	
	/*본댐.기초부세굴점수*/
	private String orginldambasicscourscore;	
	/*본댐.수문시설점수*/
	private String orginldamwtgatescore;	
	/*본댐.전석붙임점수*/
	private String orginldamplngscore;	
	/*본댐.콘크리트점수*/
	private String orginldamcncrtscore;	
	/*본댐.파손점수*/
	private String orginldamdmgscore;
	/* 본댐.박리점수 */
	private String orginldamstrpnscore; 
	/*측벽.균열점수*/
	private String sidewallcrkscore;	
	/*측벽.기초부세굴점수*/
	private String sidewallbasicscourscore;	
	/*측벽.파손점수*/
	private String sidewalldmgscore;	
	/*본댐.콘크리트*/
	private String orginldamcncrt;	

	/* 사진 URL1 */
	private String photoSrc1;
	/* 사진 URL2 */
	private String photoSrc2;
	/* 사진 URL3 */
	private String photoSrc3;
	/* 사진 URL4 */
	private String photoSrc4;	
	/* 사진 URL5 */
	private String photoSrc5;	
	/* 사진 URL6 */
	private String photoSrc6;
	
	/* 사진태그 */
	private String photoTag;
	/* 사진태그배열 */
	private String photoTagList;
	/* 사진태그1 */
	private String photoTag1;
	/* 사진태그2 */
	private String photoTag2;
	/* 사진태그3 */
	private String photoTag3;
	/* 사진태그4 */
	private String photoTag4;
	/* 사진태그5 */
	private String photoTag5;
	/* 사진태그6 */
	private String photoTag6;	
	
	
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	/**
	 * @type String
	 * @return the gid
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * @param gid the gid to set
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * @type String
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @type String
	 * @return the svySd
	 */
	public String getSvySd() {
		return svySd;
	}

	/**
	 * @param svySd the svySd to set
	 */
	public void setSvySd(String svySd) {
		this.svySd = svySd;
	}

	/**
	 * @type String
	 * @return the svySgg
	 */
	public String getSvySgg() {
		return svySgg;
	}

	/**
	 * @param svySgg the svySgg to set
	 */
	public void setSvySgg(String svySgg) {
		this.svySgg = svySgg;
	}

	/**
	 * @type String
	 * @return the svyEmd
	 */
	public String getSvyEmd() {
		return svyEmd;
	}

	/**
	 * @param svyEmd the svyEmd to set
	 */
	public void setSvyEmd(String svyEmd) {
		this.svyEmd = svyEmd;
	}

	/**
	 * @type String
	 * @return the svyRi
	 */
	public String getSvyRi() {
		return svyRi;
	}

	/**
	 * @param svyRi the svyRi to set
	 */
	public void setSvyRi(String svyRi) {
		this.svyRi = svyRi;
	}

	/**
	 * @type String
	 * @return the svyJibun
	 */
	public String getSvyJibun() {
		return svyJibun;
	}

	/**
	 * @param svyJibun the svyJibun to set
	 */
	public void setSvyJibun(String svyJibun) {
		this.svyJibun = svyJibun;
	}

	/**
	 * @type String
	 * @return the jimok
	 */
	public String getJimok() {
		return jimok;
	}

	/**
	 * @param jimok the jimok to set
	 */
	public void setJimok(String jimok) {
		this.jimok = jimok;
	}

	/**
	 * @type String
	 * @return the svyDt
	 */
	public String getSvyDt() {
		return svyDt;
	}

	/**
	 * @param svyDt the svyDt to set
	 */
	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
	}

	/**
	 * @type String
	 * @return the px
	 */
	public String getPx() {
		return px;
	}

	/**
	 * @param px the px to set
	 */
	public void setPx(String px) {
		this.px = px;
	}

	/**
	 * @type String
	 * @return the py
	 */
	public String getPy() {
		return py;
	}

	/**
	 * @param py the py to set
	 */
	public void setPy(String py) {
		this.py = py;
	}

	/**
	 * @type String
	 * @return the mstId
	 */
	public String getMstId() {
		return mstId;
	}

	/**
	 * @param mstId the mstId to set
	 */
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}

	/**
	 * @type String
	 * @return the svyId
	 */
	public String getSvyId() {
		return svyId;
	}

	/**
	 * @param svyId the svyId to set
	 */
	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}

	/**
	 * @type String
	 * @return the svyType
	 */
	public String getSvyType() {
		return svyType;
	}

	/**
	 * @param svyType the svyType to set
	 */
	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}

	/**
	 * @type String
	 * @return the creatDt
	 */
	public String getCreatDt() {
		return creatDt;
	}

	/**
	 * @param creatDt the creatDt to set
	 */
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}

	/**
	 * @type String
	 * @return the lastUpdtPnttm
	 */
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	/**
	 * @param lastUpdtPnttm the lastUpdtPnttm to set
	 */
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}

	/**
	 * @type String
	 * @return the commonly
	 */
	public String getCommonly() {
		return commonly;
	}

	/**
	 * @param commonly the commonly to set
	 */
	public void setCommonly(String commonly) {
		this.commonly = commonly;
	}

	/**
	 * @type String
	 * @return the ecnrknd
	 */
	public String getEcnrknd() {
		return ecnrknd;
	}

	/**
	 * @param ecnrknd the ecnrknd to set
	 */
	public void setEcnrknd(String ecnrknd) {
		this.ecnrknd = ecnrknd;
	}

	/**
	 * @type String
	 * @return the svyform
	 */
	public String getSvyform() {
		return svyform;
	}

	/**
	 * @param svyform the svyform to set
	 */
	public void setSvyform(String svyform) {
		this.svyform = svyform;
	}

	/**
	 * @type String
	 * @return the fcltuprhg
	 */
	public String getFcltuprhg() {
		return fcltuprhg;
	}

	/**
	 * @param fcltuprhg the fcltuprhg to set
	 */
	public void setFcltuprhg(String fcltuprhg) {
		this.fcltuprhg = fcltuprhg;
	}

	/**
	 * @type String
	 * @return the fcltlwrhg
	 */
	public String getFcltlwrhg() {
		return fcltlwrhg;
	}

	/**
	 * @param fcltlwrhg the fcltlwrhg to set
	 */
	public void setFcltlwrhg(String fcltlwrhg) {
		this.fcltlwrhg = fcltlwrhg;
	}

	/**
	 * @type String
	 * @return the fcltstkhg
	 */
	public String getFcltstkhg() {
		return fcltstkhg;
	}

	/**
	 * @param fcltstkhg the fcltstkhg to set
	 */
	public void setFcltstkhg(String fcltstkhg) {
		this.fcltstkhg = fcltstkhg;
	}

	/**
	 * @type String
	 * @return the svyuser
	 */
	public String getSvyUser() {
		return svyUser;
	}

	/**
	 * @param svyuser the svyuser to set
	 */
	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
	}

	/**
	 * @type String
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @type String
	 * @return the ecnrrnum
	 */
	public String getEcnrrnum() {
		return ecnrrnum;
	}

	/**
	 * @param ecnrrnum the ecnrrnum to set
	 */
	public void setEcnrrnum(String ecnrrnum) {
		this.ecnrrnum = ecnrrnum;
	}

	/**
	 * @type String
	 * @return the nationspotnum
	 */
	public String getNationspotnum() {
		return nationspotnum;
	}

	/**
	 * @param nationspotnum the nationspotnum to set
	 */
	public void setNationspotnum(String nationspotnum) {
		this.nationspotnum = nationspotnum;
	}

	/**
	 * @type String
	 * @return the svylatd
	 */
	public String getSvylatd() {
		return svylatd;
	}

	/**
	 * @param svylatd the svylatd to set
	 */
	public void setSvylatd(String svylatd) {
		this.svylatd = svylatd;
	}

	/**
	 * @type String
	 * @return the svylatm
	 */
	public String getSvylatm() {
		return svylatm;
	}

	/**
	 * @param svylatm the svylatm to set
	 */
	public void setSvylatm(String svylatm) {
		this.svylatm = svylatm;
	}

	/**
	 * @type String
	 * @return the svylats
	 */
	public String getSvylats() {
		return svylats;
	}

	/**
	 * @param svylats the svylats to set
	 */
	public void setSvylats(String svylats) {
		this.svylats = svylats;
	}

	/**
	 * @type String
	 * @return the svylond
	 */
	public String getSvylond() {
		return svylond;
	}

	/**
	 * @param svylond the svylond to set
	 */
	public void setSvylond(String svylond) {
		this.svylond = svylond;
	}

	/**
	 * @type String
	 * @return the svylonm
	 */
	public String getSvylonm() {
		return svylonm;
	}

	/**
	 * @param svylonm the svylonm to set
	 */
	public void setSvylonm(String svylonm) {
		this.svylonm = svylonm;
	}

	/**
	 * @type String
	 * @return the svylons
	 */
	public String getSvylons() {
		return svylons;
	}

	/**
	 * @param svylons the svylons to set
	 */
	public void setSvylons(String svylons) {
		this.svylons = svylons;
	}

	/**
	 * @type String
	 * @return the orginldamval
	 */
	public String getOrginldamval() {
		return orginldamval;
	}

	/**
	 * @param orginldamval the orginldamval to set
	 */
	public void setOrginldamval(String orginldamval) {
		this.orginldamval = orginldamval;
	}

	/**
	 * @type String
	 * @return the sidewallval
	 */
	public String getSidewallval() {
		return sidewallval;
	}

	/**
	 * @param sidewallval the sidewallval to set
	 */
	public void setSidewallval(String sidewallval) {
		this.sidewallval = sidewallval;
	}

	/**
	 * @type String
	 * @return the dwnsptval
	 */
	public String getDwnsptval() {
		return dwnsptval;
	}

	/**
	 * @param dwnsptval the dwnsptval to set
	 */
	public void setDwnsptval(String dwnsptval) {
		this.dwnsptval = dwnsptval;
	}

	/**
	 * @type String
	 * @return the orginldam
	 */
	public String getOrginldam() {
		return orginldam;
	}

	/**
	 * @param orginldam the orginldam to set
	 */
	public void setOrginldam(String orginldam) {
		this.orginldam = orginldam;
	}

	/**
	 * @type String
	 * @return the sidewall
	 */
	public String getSidewall() {
		return sidewall;
	}

	/**
	 * @param sidewall the sidewall to set
	 */
	public void setSidewall(String sidewall) {
		this.sidewall = sidewall;
	}

	/**
	 * @type String
	 * @return the dwnspt
	 */
	public String getDwnspt() {
		return dwnspt;
	}

	/**
	 * @param dwnspt the dwnspt to set
	 */
	public void setDwnspt(String dwnspt) {
		this.dwnspt = dwnspt;
	}

	/**
	 * @type String
	 * @return the flugtjdgval
	 */
	public String getFlugtjdgval() {
		return flugtjdgval;
	}

	/**
	 * @param flugtjdgval the flugtjdgval to set
	 */
	public void setFlugtjdgval(String flugtjdgval) {
		this.flugtjdgval = flugtjdgval;
	}

	/**
	 * @type String
	 * @return the vtnsttusjdgval
	 */
	public String getVtnsttusjdgval() {
		return vtnsttusjdgval;
	}

	/**
	 * @param vtnsttusjdgval the vtnsttusjdgval to set
	 */
	public void setVtnsttusjdgval(String vtnsttusjdgval) {
		this.vtnsttusjdgval = vtnsttusjdgval;
	}

	/**
	 * @type String
	 * @return the sffcjdgval
	 */
	public String getSffcjdgval() {
		return sffcjdgval;
	}

	/**
	 * @param sffcjdgval the sffcjdgval to set
	 */
	public void setSffcjdgval(String sffcjdgval) {
		this.sffcjdgval = sffcjdgval;
	}

	/**
	 * @type String
	 * @return the accssrdjdgval
	 */
	public String getAccssrdjdgval() {
		return accssrdjdgval;
	}

	/**
	 * @param accssrdjdgval the accssrdjdgval to set
	 */
	public void setAccssrdjdgval(String accssrdjdgval) {
		this.accssrdjdgval = accssrdjdgval;
	}

	/**
	 * @type String
	 * @return the etcjdgval
	 */
	public String getEtcjdgval() {
		return etcjdgval;
	}

	/**
	 * @param etcjdgval the etcjdgval to set
	 */
	public void setEtcjdgval(String etcjdgval) {
		this.etcjdgval = etcjdgval;
	}

	/**
	 * @type String
	 * @return the snddpsitjdgval
	 */
	public String getSnddpsitjdgval() {
		return snddpsitjdgval;
	}

	/**
	 * @param snddpsitjdgval the snddpsitjdgval to set
	 */
	public void setSnddpsitjdgval(String snddpsitjdgval) {
		this.snddpsitjdgval = snddpsitjdgval;
	}

	/**
	 * @type String
	 * @return the snddpsitval
	 */
	public String getSnddpsitval() {
		return snddpsitval;
	}

	/**
	 * @param snddpsitval the snddpsitval to set
	 */
	public void setSnddpsitval(String snddpsitval) {
		this.snddpsitval = snddpsitval;
	}

	/**
	 * @type String
	 * @return the fckrslt
	 */
	public String getFckrslt() {
		return fckrslt;
	}

	/**
	 * @param fckrslt the fckrslt to set
	 */
	public void setFckrslt(String fckrslt) {
		this.fckrslt = fckrslt;
	}

	/**
	 * @type String
	 * @return the mngmtr
	 */
	public String getMngmtr() {
		return mngmtr;
	}

	/**
	 * @param mngmtr the mngmtr to set
	 */
	public void setMngmtr(String mngmtr) {
		this.mngmtr = mngmtr;
	}

	/**
	 * @type String
	 * @return the appnrelis
	 */
	public String getAppnrelis() {
		return appnrelis;
	}

	/**
	 * @param appnrelis the appnrelis to set
	 */
	public void setAppnrelis(String appnrelis) {
		this.appnrelis = appnrelis;
	}

	/**
	 * @type String
	 * @return the opinion1
	 */
	public String getOpinion1() {
		return opinion1;
	}

	/**
	 * @param opinion1 the opinion1 to set
	 */
	public void setOpinion1(String opinion1) {
		this.opinion1 = opinion1;
	}

	/**
	 * @type String
	 * @return the opinion2
	 */
	public String getOpinion2() {
		return opinion2;
	}

	/**
	 * @param opinion2 the opinion2 to set
	 */
	public void setOpinion2(String opinion2) {
		this.opinion2 = opinion2;
	}

	/**
	 * @type String
	 * @return the opinion3
	 */
	public String getOpinion3() {
		return opinion3;
	}

	/**
	 * @param opinion3 the opinion3 to set
	 */
	public void setOpinion3(String opinion3) {
		this.opinion3 = opinion3;
	}

	/**
	 * @type String
	 * @return the opinion4
	 */
	public String getOpinion4() {
		return opinion4;
	}

	/**
	 * @param opinion4 the opinion4 to set
	 */
	public void setOpinion4(String opinion4) {
		this.opinion4 = opinion4;
	}

	/**
	 * @type String
	 * @return the opinion5
	 */
	public String getOpinion5() {
		return opinion5;
	}

	/**
	 * @param opinion5 the opinion5 to set
	 */
	public void setOpinion5(String opinion5) {
		this.opinion5 = opinion5;
	}

	/**
	 * @type String
	 * @return the mainfcltspecialnote
	 */
	public String getMainfcltspecialnote() {
		return mainfcltspecialnote;
	}

	/**
	 * @param mainfcltspecialnote the mainfcltspecialnote to set
	 */
	public void setMainfcltspecialnote(String mainfcltspecialnote) {
		this.mainfcltspecialnote = mainfcltspecialnote;
	}

	/**
	 * @type String
	 * @return the subfcltspecialnote
	 */
	public String getSubfcltspecialnote() {
		return subfcltspecialnote;
	}

	/**
	 * @param subfcltspecialnote the subfcltspecialnote to set
	 */
	public void setSubfcltspecialnote(String subfcltspecialnote) {
		this.subfcltspecialnote = subfcltspecialnote;
	}

	/**
	 * @type String
	 * @return the etcspecialnote
	 */
	public String getEtcspecialnote() {
		return etcspecialnote;
	}

	/**
	 * @param etcspecialnote the etcspecialnote to set
	 */
	public void setEtcspecialnote(String etcspecialnote) {
		this.etcspecialnote = etcspecialnote;
	}

	/**
	 * @type String
	 * @return the knd
	 */
	public String getKnd() {
		return knd;
	}

	/**
	 * @param knd the knd to set
	 */
	public void setKnd(String knd) {
		this.knd = knd;
	}

	/**
	 * @type String
	 * @return the tthghjdgval
	 */
	public String getTthghjdgval() {
		return tthghjdgval;
	}

	/**
	 * @param tthghjdgval the tthghjdgval to set
	 */
	public void setTthghjdgval(String tthghjdgval) {
		this.tthghjdgval = tthghjdgval;
	}

	/**
	 * @type String
	 * @return the wotdjdgval
	 */
	public String getWotdjdgval() {
		return wotdjdgval;
	}

	/**
	 * @param wotdjdgval the wotdjdgval to set
	 */
	public void setWotdjdgval(String wotdjdgval) {
		this.wotdjdgval = wotdjdgval;
	}

	/**
	 * @type String
	 * @return the cnstrcost
	 */
	public String getCnstrcost() {
		return cnstrcost;
	}

	/**
	 * @param cnstrcost the cnstrcost to set
	 */
	public void setCnstrcost(String cnstrcost) {
		this.cnstrcost = cnstrcost;
	}

	/**
	 * @type String
	 * @return the latdlndsld
	 */
	public String getLatdlndsld() {
		return latdlndsld;
	}

	/**
	 * @param latdlndsld the latdlndsld to set
	 */
	public void setLatdlndsld(String latdlndsld) {
		this.latdlndsld = latdlndsld;
	}

	/**
	 * @type String
	 * @return the latmlndsld
	 */
	public String getLatmlndsld() {
		return latmlndsld;
	}

	/**
	 * @param latmlndsld the latmlndsld to set
	 */
	public void setLatmlndsld(String latmlndsld) {
		this.latmlndsld = latmlndsld;
	}

	/**
	 * @type String
	 * @return the latslndsld
	 */
	public String getLatslndsld() {
		return latslndsld;
	}

	/**
	 * @param latslndsld the latslndsld to set
	 */
	public void setLatslndsld(String latslndsld) {
		this.latslndsld = latslndsld;
	}

	/**
	 * @type String
	 * @return the londlndsld
	 */
	public String getLondlndsld() {
		return londlndsld;
	}

	/**
	 * @param londlndsld the londlndsld to set
	 */
	public void setLondlndsld(String londlndsld) {
		this.londlndsld = londlndsld;
	}

	/**
	 * @type String
	 * @return the lonmlndsld
	 */
	public String getLonmlndsld() {
		return lonmlndsld;
	}

	/**
	 * @param lonmlndsld the lonmlndsld to set
	 */
	public void setLonmlndsld(String lonmlndsld) {
		this.lonmlndsld = lonmlndsld;
	}

	/**
	 * @type String
	 * @return the lonslndsld
	 */
	public String getLonslndsld() {
		return lonslndsld;
	}

	/**
	 * @param lonslndsld the lonslndsld to set
	 */
	public void setLonslndsld(String lonslndsld) {
		this.lonslndsld = lonslndsld;
	}

	/**
	 * @type String
	 * @return the latdacplcsld
	 */
	public String getLatdacplcsld() {
		return latdacplcsld;
	}

	/**
	 * @param latdacplcsld the latdacplcsld to set
	 */
	public void setLatdacplcsld(String latdacplcsld) {
		this.latdacplcsld = latdacplcsld;
	}

	/**
	 * @type String
	 * @return the latmacplcsld
	 */
	public String getLatmacplcsld() {
		return latmacplcsld;
	}

	/**
	 * @param latmacplcsld the latmacplcsld to set
	 */
	public void setLatmacplcsld(String latmacplcsld) {
		this.latmacplcsld = latmacplcsld;
	}

	/**
	 * @type String
	 * @return the latsacplcsld
	 */
	public String getLatsacplcsld() {
		return latsacplcsld;
	}

	/**
	 * @param latsacplcsld the latsacplcsld to set
	 */
	public void setLatsacplcsld(String latsacplcsld) {
		this.latsacplcsld = latsacplcsld;
	}

	/**
	 * @type String
	 * @return the londacplcsld
	 */
	public String getLondacplcsld() {
		return londacplcsld;
	}

	/**
	 * @param londacplcsld the londacplcsld to set
	 */
	public void setLondacplcsld(String londacplcsld) {
		this.londacplcsld = londacplcsld;
	}

	/**
	 * @type String
	 * @return the lonmacplcsld
	 */
	public String getLonmacplcsld() {
		return lonmacplcsld;
	}

	/**
	 * @param lonmacplcsld the lonmacplcsld to set
	 */
	public void setLonmacplcsld(String lonmacplcsld) {
		this.lonmacplcsld = lonmacplcsld;
	}

	/**
	 * @type String
	 * @return the lonsacplcsld
	 */
	public String getLonsacplcsld() {
		return lonsacplcsld;
	}

	/**
	 * @param lonsacplcsld the lonsacplcsld to set
	 */
	public void setLonsacplcsld(String lonsacplcsld) {
		this.lonsacplcsld = lonsacplcsld;
	}

	/**
	 * @type String
	 * @return the sidewalllng
	 */
	public String getSidewalllng() {
		return sidewalllng;
	}

	/**
	 * @param sidewalllng the sidewalllng to set
	 */
	public void setSidewalllng(String sidewalllng) {
		this.sidewalllng = sidewalllng;
	}

	/**
	 * @type String
	 * @return the sidewallheg
	 */
	public String getSidewallheg() {
		return sidewallheg;
	}

	/**
	 * @param sidewallheg the sidewallheg to set
	 */
	public void setSidewallheg(String sidewallheg) {
		this.sidewallheg = sidewallheg;
	}

	/**
	 * @type String
	 * @return the dwnspthrz
	 */
	public String getDwnspthrz() {
		return dwnspthrz;
	}

	/**
	 * @param dwnspthrz the dwnspthrz to set
	 */
	public void setDwnspthrz(String dwnspthrz) {
		this.dwnspthrz = dwnspthrz;
	}

	/**
	 * @type String
	 * @return the dwnsptvtc
	 */
	public String getDwnsptvtc() {
		return dwnsptvtc;
	}

	/**
	 * @param dwnsptvtc the dwnsptvtc to set
	 */
	public void setDwnsptvtc(String dwnsptvtc) {
		this.dwnsptvtc = dwnsptvtc;
	}

	/**
	 * @type String
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * @param lon the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

	/**
	 * @type String
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @type String
	 * @return the fcltyear
	 */
	public String getFcltyear() {
		return fcltyear;
	}

	/**
	 * @param fcltyear the fcltyear to set
	 */
	public void setFcltyear(String fcltyear) {
		this.fcltyear = fcltyear;
	}

	/**
	 * @type String
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @type String
	 * @return the phototag
	 */
	public String getPhototag() {
		return phototag;
	}

	/**
	 * @param phototag the phototag to set
	 */
	public void setPhototag(String phototag) {
		this.phototag = phototag;
	}

	/**
	 * @type String
	 * @return the dwnsptcrk
	 */
	public String getDwnsptcrk() {
		return dwnsptcrk;
	}

	/**
	 * @param dwnsptcrk the dwnsptcrk to set
	 */
	public void setDwnsptcrk(String dwnsptcrk) {
		this.dwnsptcrk = dwnsptcrk;
	}

	/**
	 * @type String
	 * @return the dwnsptbasicscour
	 */
	public String getDwnsptbasicscour() {
		return dwnsptbasicscour;
	}

	/**
	 * @param dwnsptbasicscour the dwnsptbasicscour to set
	 */
	public void setDwnsptbasicscour(String dwnsptbasicscour) {
		this.dwnsptbasicscour = dwnsptbasicscour;
	}

	/**
	 * @type String
	 * @return the dwnsptdmg
	 */
	public String getDwnsptdmg() {
		return dwnsptdmg;
	}

	/**
	 * @param dwnsptdmg the dwnsptdmg to set
	 */
	public void setDwnsptdmg(String dwnsptdmg) {
		this.dwnsptdmg = dwnsptdmg;
	}

	/**
	 * @type String
	 * @return the orginldamcrk
	 */
	public String getOrginldamcrk() {
		return orginldamcrk;
	}

	/**
	 * @param orginldamcrk the orginldamcrk to set
	 */
	public void setOrginldamcrk(String orginldamcrk) {
		this.orginldamcrk = orginldamcrk;
	}

	/**
	 * @type String
	 * @return the orginldambasicscour
	 */
	public String getOrginldambasicscour() {
		return orginldambasicscour;
	}

	/**
	 * @param orginldambasicscour the orginldambasicscour to set
	 */
	public void setOrginldambasicscour(String orginldambasicscour) {
		this.orginldambasicscour = orginldambasicscour;
	}

	/**
	 * @type String
	 * @return the orginldamplng
	 */
	public String getOrginldamplng() {
		return orginldamplng;
	}

	/**
	 * @param orginldamplng the orginldamplng to set
	 */
	public void setOrginldamplng(String orginldamplng) {
		this.orginldamplng = orginldamplng;
	}

	/**
	 * @type String
	 * @return the orginldamwtgate
	 */
	public String getOrginldamwtgate() {
		return orginldamwtgate;
	}

	/**
	 * @param orginldamwtgate the orginldamwtgate to set
	 */
	public void setOrginldamwtgate(String orginldamwtgate) {
		this.orginldamwtgate = orginldamwtgate;
	}

	/**
	 * @type String
	 * @return the orginldamdmg
	 */
	public String getOrginldamdmg() {
		return orginldamdmg;
	}

	/**
	 * @param orginldamdmg the orginldamdmg to set
	 */
	public void setOrginldamdmg(String orginldamdmg) {
		this.orginldamdmg = orginldamdmg;
	}

	/**
	 * @type String
	 * @return the sidewallcrk
	 */
	public String getSidewallcrk() {
		return sidewallcrk;
	}

	/**
	 * @param sidewallcrk the sidewallcrk to set
	 */
	public void setSidewallcrk(String sidewallcrk) {
		this.sidewallcrk = sidewallcrk;
	}

	/**
	 * @type String
	 * @return the sidewallbasicscour
	 */
	public String getSidewallbasicscour() {
		return sidewallbasicscour;
	}

	/**
	 * @param sidewallbasicscour the sidewallbasicscour to set
	 */
	public void setSidewallbasicscour(String sidewallbasicscour) {
		this.sidewallbasicscour = sidewallbasicscour;
	}

	/**
	 * @type String
	 * @return the sidewalldmg
	 */
	public String getSidewalldmg() {
		return sidewalldmg;
	}

	/**
	 * @param sidewalldmg the sidewalldmg to set
	 */
	public void setSidewalldmg(String sidewalldmg) {
		this.sidewalldmg = sidewalldmg;
	}

	/**
	 * @type String
	 * @return the svycrctn
	 */
	public String getSvycrctn() {
		return svycrctn;
	}

	/**
	 * @param svycrctn the svycrctn to set
	 */
	public void setSvycrctn(String svycrctn) {
		this.svycrctn = svycrctn;
	}

	/**
	 * @type String
	 * @return the cncrtcmprsstrn
	 */
	public String getCncrtcmprsstrn() {
		return cncrtcmprsstrn;
	}

	/**
	 * @param cncrtcmprsstrn the cncrtcmprsstrn to set
	 */
	public void setCncrtcmprsstrn(String cncrtcmprsstrn) {
		this.cncrtcmprsstrn = cncrtcmprsstrn;
	}

	/**
	 * @type String
	 * @return the cncrtcrkdpt
	 */
	public String getCncrtcrkdpt() {
		return cncrtcrkdpt;
	}

	/**
	 * @param cncrtcrkdpt the cncrtcrkdpt to set
	 */
	public void setCncrtcrkdpt(String cncrtcrkdpt) {
		this.cncrtcrkdpt = cncrtcrkdpt;
	}

	/**
	 * @type String
	 * @return the nowsnddpsitval
	 */
	public String getNowsnddpsitval() {
		return nowsnddpsitval;
	}

	/**
	 * @param nowsnddpsitval the nowsnddpsitval to set
	 */
	public void setNowsnddpsitval(String nowsnddpsitval) {
		this.nowsnddpsitval = nowsnddpsitval;
	}

	/**
	 * @type String
	 * @return the lifezone
	 */
	public String getLifezone() {
		return lifezone;
	}

	/**
	 * @param lifezone the lifezone to set
	 */
	public void setLifezone(String lifezone) {
		this.lifezone = lifezone;
	}

	/**
	 * @type String
	 * @return the riverbedgradient
	 */
	public String getRiverbedgradient() {
		return riverbedgradient;
	}

	/**
	 * @param riverbedgradient the riverbedgradient to set
	 */
	public void setRiverbedgradient(String riverbedgradient) {
		this.riverbedgradient = riverbedgradient;
	}

	/**
	 * @type String
	 * @return the soilmoveamnt
	 */
	public String getSoilmoveamnt() {
		return soilmoveamnt;
	}

	/**
	 * @param soilmoveamnt the soilmoveamnt to set
	 */
	public void setSoilmoveamnt(String soilmoveamnt) {
		this.soilmoveamnt = soilmoveamnt;
	}

	/**
	 * @type String
	 * @return the totalscore
	 */
	public String getTotalscore() {
		return totalscore;
	}

	/**
	 * @param totalscore the totalscore to set
	 */
	public void setTotalscore(String totalscore) {
		this.totalscore = totalscore;
	}

	/**
	 * @type String
	 * @return the drdgnatjdg
	 */
	public String getDrdgnatjdg() {
		return drdgnatjdg;
	}

	/**
	 * @param drdgnatjdg the drdgnatjdg to set
	 */
	public void setDrdgnatjdg(String drdgnatjdg) {
		this.drdgnatjdg = drdgnatjdg;
	}

	/**
	 * @type String
	 * @return the cmprsstrnrslt
	 */
	public String getCmprsstrnrslt() {
		return cmprsstrnrslt;
	}

	/**
	 * @param cmprsstrnrslt the cmprsstrnrslt to set
	 */
	public void setCmprsstrnrslt(String cmprsstrnrslt) {
		this.cmprsstrnrslt = cmprsstrnrslt;
	}

	/**
	 * @type String
	 * @return the cmprsstrncncrt1
	 */
	public String getCmprsstrncncrt1() {
		return cmprsstrncncrt1;
	}

	/**
	 * @param cmprsstrncncrt1 the cmprsstrncncrt1 to set
	 */
	public void setCmprsstrncncrt1(String cmprsstrncncrt1) {
		this.cmprsstrncncrt1 = cmprsstrncncrt1;
	}

	/**
	 * @type String
	 * @return the cmprsstrncncrt2
	 */
	public String getCmprsstrncncrt2() {
		return cmprsstrncncrt2;
	}

	/**
	 * @param cmprsstrncncrt2 the cmprsstrncncrt2 to set
	 */
	public void setCmprsstrncncrt2(String cmprsstrncncrt2) {
		this.cmprsstrncncrt2 = cmprsstrncncrt2;
	}

	/**
	 * @type String
	 * @return the cmprsstrnavg1
	 */
	public String getCmprsstrnavg1() {
		return cmprsstrnavg1;
	}

	/**
	 * @param cmprsstrnavg1 the cmprsstrnavg1 to set
	 */
	public void setCmprsstrnavg1(String cmprsstrnavg1) {
		this.cmprsstrnavg1 = cmprsstrnavg1;
	}

	/**
	 * @type String
	 * @return the cmprsstrnavg2
	 */
	public String getCmprsstrnavg2() {
		return cmprsstrnavg2;
	}

	/**
	 * @param cmprsstrnavg2 the cmprsstrnavg2 to set
	 */
	public void setCmprsstrnavg2(String cmprsstrnavg2) {
		this.cmprsstrnavg2 = cmprsstrnavg2;
	}

	/**
	 * @type String
	 * @return the cmprsstrnavg3
	 */
	public String getCmprsstrnavg3() {
		return cmprsstrnavg3;
	}

	/**
	 * @param cmprsstrnavg3 the cmprsstrnavg3 to set
	 */
	public void setCmprsstrnavg3(String cmprsstrnavg3) {
		this.cmprsstrnavg3 = cmprsstrnavg3;
	}

	/**
	 * @type String
	 * @return the cmprsstrnavg4
	 */
	public String getCmprsstrnavg4() {
		return cmprsstrnavg4;
	}

	/**
	 * @param cmprsstrnavg4 the cmprsstrnavg4 to set
	 */
	public void setCmprsstrnavg4(String cmprsstrnavg4) {
		this.cmprsstrnavg4 = cmprsstrnavg4;
	}

	/**
	 * @type String
	 * @return the jdgpntstrct
	 */
	public String getJdgpntstrct() {
		return jdgpntstrct;
	}

	/**
	 * @param jdgpntstrct the jdgpntstrct to set
	 */
	public void setJdgpntstrct(String jdgpntstrct) {
		this.jdgpntstrct = jdgpntstrct;
	}

	/**
	 * @type String
	 * @return the jdgpntcrk
	 */
	public String getJdgpntcrk() {
		return jdgpntcrk;
	}

	/**
	 * @param jdgpntcrk the jdgpntcrk to set
	 */
	public void setJdgpntcrk(String jdgpntcrk) {
		this.jdgpntcrk = jdgpntcrk;
	}

	/**
	 * @type String
	 * @return the jdgpntstp
	 */
	public String getJdgpntstp() {
		return jdgpntstp;
	}

	/**
	 * @param jdgpntstp the jdgpntstp to set
	 */
	public void setJdgpntstp(String jdgpntstp) {
		this.jdgpntstp = jdgpntstp;
	}

	/**
	 * @type String
	 * @return the jdgpntwdpt
	 */
	public String getJdgpntwdpt() {
		return jdgpntwdpt;
	}

	/**
	 * @param jdgpntwdpt the jdgpntwdpt to set
	 */
	public void setJdgpntwdpt(String jdgpntwdpt) {
		this.jdgpntwdpt = jdgpntwdpt;
	}

	/**
	 * @type String
	 * @return the jdgpntgeo
	 */
	public String getJdgpntgeo() {
		return jdgpntgeo;
	}

	/**
	 * @param jdgpntgeo the jdgpntgeo to set
	 */
	public void setJdgpntgeo(String jdgpntgeo) {
		this.jdgpntgeo = jdgpntgeo;
	}

	/**
	 * @type String
	 * @return the jdgpntugrwtr
	 */
	public String getJdgpntugrwtr() {
		return jdgpntugrwtr;
	}

	/**
	 * @param jdgpntugrwtr the jdgpntugrwtr to set
	 */
	public void setJdgpntugrwtr(String jdgpntugrwtr) {
		this.jdgpntugrwtr = jdgpntugrwtr;
	}

	/**
	 * @type String
	 * @return the jdgpnttopo
	 */
	public String getJdgpnttopo() {
		return jdgpnttopo;
	}

	/**
	 * @param jdgpnttopo the jdgpnttopo to set
	 */
	public void setJdgpnttopo(String jdgpnttopo) {
		this.jdgpnttopo = jdgpnttopo;
	}

	/**
	 * @type String
	 * @return the jdgpntsoil
	 */
	public String getJdgpntsoil() {
		return jdgpntsoil;
	}

	/**
	 * @param jdgpntsoil the jdgpntsoil to set
	 */
	public void setJdgpntsoil(String jdgpntsoil) {
		this.jdgpntsoil = jdgpntsoil;
	}

	/**
	 * @type String
	 * @return the pz
	 */
	public String getPz() {
		return pz;
	}

	/**
	 * @param pz the pz to set
	 */
	public void setPz(String pz) {
		this.pz = pz;
	}

	/**
	 * @type String
	 * @return the hsfcltuprhg
	 */
	public String getHsfcltuprhg() {
		return hsfcltuprhg;
	}

	/**
	 * @param hsfcltuprhg the hsfcltuprhg to set
	 */
	public void setHsfcltuprhg(String hsfcltuprhg) {
		this.hsfcltuprhg = hsfcltuprhg;
	}

	/**
	 * @type String
	 * @return the hsfcltlwrhg
	 */
	public String getHsfcltlwrhg() {
		return hsfcltlwrhg;
	}

	/**
	 * @param hsfcltlwrhg the hsfcltlwrhg to set
	 */
	public void setHsfcltlwrhg(String hsfcltlwrhg) {
		this.hsfcltlwrhg = hsfcltlwrhg;
	}

	/**
	 * @type String
	 * @return the hstthgh
	 */
	public String getHstthgh() {
		return hstthgh;
	}

	/**
	 * @param hstthgh the hstthgh to set
	 */
	public void setHstthgh(String hstthgh) {
		this.hstthgh = hstthgh;
	}

	/**
	 * @type String
	 * @return the hsfcltstkhg
	 */
	public String getHsfcltstkhg() {
		return hsfcltstkhg;
	}

	/**
	 * @param hsfcltstkhg the hsfcltstkhg to set
	 */
	public void setHsfcltstkhg(String hsfcltstkhg) {
		this.hsfcltstkhg = hsfcltstkhg;
	}

	/**
	 * @type String
	 * @return the larmlen
	 */
	public String getLarmlen() {
		return larmlen;
	}

	/**
	 * @param larmlen the larmlen to set
	 */
	public void setLarmlen(String larmlen) {
		this.larmlen = larmlen;
	}

	/**
	 * @type String
	 * @return the larmwid
	 */
	public String getLarmwid() {
		return larmwid;
	}

	/**
	 * @param larmwid the larmwid to set
	 */
	public void setLarmwid(String larmwid) {
		this.larmwid = larmwid;
	}

	/**
	 * @type String
	 * @return the wtrprtoplen
	 */
	public String getWtrprtoplen() {
		return wtrprtoplen;
	}

	/**
	 * @param wtrprtoplen the wtrprtoplen to set
	 */
	public void setWtrprtoplen(String wtrprtoplen) {
		this.wtrprtoplen = wtrprtoplen;
	}

	/**
	 * @type String
	 * @return the wtrprbtmlen
	 */
	public String getWtrprbtmlen() {
		return wtrprbtmlen;
	}

	/**
	 * @param wtrprbtmlen the wtrprbtmlen to set
	 */
	public void setWtrprbtmlen(String wtrprbtmlen) {
		this.wtrprbtmlen = wtrprbtmlen;
	}

	/**
	 * @type String
	 * @return the wtrprwid
	 */
	public String getWtrprwid() {
		return wtrprwid;
	}

	/**
	 * @param wtrprwid the wtrprwid to set
	 */
	public void setWtrprwid(String wtrprwid) {
		this.wtrprwid = wtrprwid;
	}

	/**
	 * @type String
	 * @return the wtrprhg
	 */
	public String getWtrprhg() {
		return wtrprhg;
	}

	/**
	 * @param wtrprhg the wtrprhg to set
	 */
	public void setWtrprhg(String wtrprhg) {
		this.wtrprhg = wtrprhg;
	}

	/**
	 * @type String
	 * @return the wtrprslopelen
	 */
	public String getWtrprslopelen() {
		return wtrprslopelen;
	}

	/**
	 * @param wtrprslopelen the wtrprslopelen to set
	 */
	public void setWtrprslopelen(String wtrprslopelen) {
		this.wtrprslopelen = wtrprslopelen;
	}

	/**
	 * @type String
	 * @return the rarmlen
	 */
	public String getRarmlen() {
		return rarmlen;
	}

	/**
	 * @param rarmlen the rarmlen to set
	 */
	public void setRarmlen(String rarmlen) {
		this.rarmlen = rarmlen;
	}

	/**
	 * @type String
	 * @return the rarmwid
	 */
	public String getRarmwid() {
		return rarmwid;
	}

	/**
	 * @param rarmwid the rarmwid to set
	 */
	public void setRarmwid(String rarmwid) {
		this.rarmwid = rarmwid;
	}

	/**
	 * @type String
	 * @return the lsidewalllen
	 */
	public String getLsidewalllen() {
		return lsidewalllen;
	}

	/**
	 * @param lsidewalllen the lsidewalllen to set
	 */
	public void setLsidewalllen(String lsidewalllen) {
		this.lsidewalllen = lsidewalllen;
	}

	/**
	 * @type String
	 * @return the lsidewallhghigh
	 */
	public String getLsidewallhghigh() {
		return lsidewallhghigh;
	}

	/**
	 * @param lsidewallhghigh the lsidewallhghigh to set
	 */
	public void setLsidewallhghigh(String lsidewallhghigh) {
		this.lsidewallhghigh = lsidewallhghigh;
	}

	/**
	 * @type String
	 * @return the lsidewallhglow
	 */
	public String getLsidewallhglow() {
		return lsidewallhglow;
	}

	/**
	 * @param lsidewallhglow the lsidewallhglow to set
	 */
	public void setLsidewallhglow(String lsidewallhglow) {
		this.lsidewallhglow = lsidewallhglow;
	}

	/**
	 * @type String
	 * @return the rsidewalllen
	 */
	public String getRsidewalllen() {
		return rsidewalllen;
	}

	/**
	 * @param rsidewalllen the rsidewalllen to set
	 */
	public void setRsidewalllen(String rsidewalllen) {
		this.rsidewalllen = rsidewalllen;
	}

	/**
	 * @type String
	 * @return the rsidewallhghigh
	 */
	public String getRsidewallhghigh() {
		return rsidewallhghigh;
	}

	/**
	 * @param rsidewallhghigh the rsidewallhghigh to set
	 */
	public void setRsidewallhghigh(String rsidewallhghigh) {
		this.rsidewallhghigh = rsidewallhghigh;
	}

	/**
	 * @type String
	 * @return the rsidewallhglow
	 */
	public String getRsidewallhglow() {
		return rsidewallhglow;
	}

	/**
	 * @param rsidewallhglow the rsidewallhglow to set
	 */
	public void setRsidewallhglow(String rsidewallhglow) {
		this.rsidewallhglow = rsidewallhglow;
	}

	/**
	 * @type String
	 * @return the dwnsptwidhigh
	 */
	public String getDwnsptwidhigh() {
		return dwnsptwidhigh;
	}

	/**
	 * @param dwnsptwidhigh the dwnsptwidhigh to set
	 */
	public void setDwnsptwidhigh(String dwnsptwidhigh) {
		this.dwnsptwidhigh = dwnsptwidhigh;
	}

	/**
	 * @type String
	 * @return the dwnsptwidlow
	 */
	public String getDwnsptwidlow() {
		return dwnsptwidlow;
	}

	/**
	 * @param dwnsptwidlow the dwnsptwidlow to set
	 */
	public void setDwnsptwidlow(String dwnsptwidlow) {
		this.dwnsptwidlow = dwnsptwidlow;
	}

	/**
	 * @type String
	 * @return the dwnsptlen
	 */
	public String getDwnsptlen() {
		return dwnsptlen;
	}

	/**
	 * @param dwnsptlen the dwnsptlen to set
	 */
	public void setDwnsptlen(String dwnsptlen) {
		this.dwnsptlen = dwnsptlen;
	}

	/**
	 * @type String
	 * @return the dwnsptverwid
	 */
	public String getDwnsptverwid() {
		return dwnsptverwid;
	}

	/**
	 * @param dwnsptverwid the dwnsptverwid to set
	 */
	public void setDwnsptverwid(String dwnsptverwid) {
		this.dwnsptverwid = dwnsptverwid;
	}

	/**
	 * @type String
	 * @return the dwnsptverhg
	 */
	public String getDwnsptverhg() {
		return dwnsptverhg;
	}

	/**
	 * @param dwnsptverhg the dwnsptverhg to set
	 */
	public void setDwnsptverhg(String dwnsptverhg) {
		this.dwnsptverhg = dwnsptverhg;
	}

	/**
	 * @type String
	 * @return the dsntarea
	 */
	public String getDsntarea() {
		return dsntarea;
	}

	/**
	 * @param dsntarea the dsntarea to set
	 */
	public void setDsntarea(String dsntarea) {
		this.dsntarea = dsntarea;
	}

	/**
	 * @type String
	 * @return the ecwnm
	 */
	public String getEcwnm() {
		return ecwnm;
	}

	/**
	 * @param ecwnm the ecwnm to set
	 */
	public void setEcwnm(String ecwnm) {
		this.ecwnm = ecwnm;
	}

	/**
	 * @type String
	 * @return the mainstruct
	 */
	public String getMainstruct() {
		return mainstruct;
	}

	/**
	 * @param mainstruct the mainstruct to set
	 */
	public void setMainstruct(String mainstruct) {
		this.mainstruct = mainstruct;
	}

	/**
	 * @type String
	 * @return the nowsnddpsitvalweight
	 */
	public String getNowsnddpsitvalweight() {
		return nowsnddpsitvalweight;
	}

	/**
	 * @param nowsnddpsitvalweight the nowsnddpsitvalweight to set
	 */
	public void setNowsnddpsitvalweight(String nowsnddpsitvalweight) {
		this.nowsnddpsitvalweight = nowsnddpsitvalweight;
	}

	/**
	 * @type String
	 * @return the nowsnddpsitvalcoeff
	 */
	public String getNowsnddpsitvalcoeff() {
		return nowsnddpsitvalcoeff;
	}

	/**
	 * @param nowsnddpsitvalcoeff the nowsnddpsitvalcoeff to set
	 */
	public void setNowsnddpsitvalcoeff(String nowsnddpsitvalcoeff) {
		this.nowsnddpsitvalcoeff = nowsnddpsitvalcoeff;
	}

	/**
	 * @type String
	 * @return the nowsnddpsitvaldivision
	 */
	public String getNowsnddpsitvaldivision() {
		return nowsnddpsitvaldivision;
	}

	/**
	 * @param nowsnddpsitvaldivision the nowsnddpsitvaldivision to set
	 */
	public void setNowsnddpsitvaldivision(String nowsnddpsitvaldivision) {
		this.nowsnddpsitvaldivision = nowsnddpsitvaldivision;
	}

	/**
	 * @type String
	 * @return the nowsnddpsitvaldrdgn
	 */
	public String getNowsnddpsitvaldrdgn() {
		return nowsnddpsitvaldrdgn;
	}

	/**
	 * @param nowsnddpsitvaldrdgn the nowsnddpsitvaldrdgn to set
	 */
	public void setNowsnddpsitvaldrdgn(String nowsnddpsitvaldrdgn) {
		this.nowsnddpsitvaldrdgn = nowsnddpsitvaldrdgn;
	}

	/**
	 * @type String
	 * @return the lifezoneweight
	 */
	public String getLifezoneweight() {
		return lifezoneweight;
	}

	/**
	 * @param lifezoneweight the lifezoneweight to set
	 */
	public void setLifezoneweight(String lifezoneweight) {
		this.lifezoneweight = lifezoneweight;
	}

	/**
	 * @type String
	 * @return the lifezonecoeff
	 */
	public String getLifezonecoeff() {
		return lifezonecoeff;
	}

	/**
	 * @param lifezonecoeff the lifezonecoeff to set
	 */
	public void setLifezonecoeff(String lifezonecoeff) {
		this.lifezonecoeff = lifezonecoeff;
	}

	/**
	 * @type String
	 * @return the lifezonedivision
	 */
	public String getLifezonedivision() {
		return lifezonedivision;
	}

	/**
	 * @param lifezonedivision the lifezonedivision to set
	 */
	public void setLifezonedivision(String lifezonedivision) {
		this.lifezonedivision = lifezonedivision;
	}

	/**
	 * @type String
	 * @return the lifezonedrdgn
	 */
	public String getLifezonedrdgn() {
		return lifezonedrdgn;
	}

	/**
	 * @param lifezonedrdgn the lifezonedrdgn to set
	 */
	public void setLifezonedrdgn(String lifezonedrdgn) {
		this.lifezonedrdgn = lifezonedrdgn;
	}

	/**
	 * @type String
	 * @return the riverbedgradientweight
	 */
	public String getRiverbedgradientweight() {
		return riverbedgradientweight;
	}

	/**
	 * @param riverbedgradientweight the riverbedgradientweight to set
	 */
	public void setRiverbedgradientweight(String riverbedgradientweight) {
		this.riverbedgradientweight = riverbedgradientweight;
	}

	/**
	 * @type String
	 * @return the riverbedgradientcoeff
	 */
	public String getRiverbedgradientcoeff() {
		return riverbedgradientcoeff;
	}

	/**
	 * @param riverbedgradientcoeff the riverbedgradientcoeff to set
	 */
	public void setRiverbedgradientcoeff(String riverbedgradientcoeff) {
		this.riverbedgradientcoeff = riverbedgradientcoeff;
	}

	/**
	 * @type String
	 * @return the riverbedgradientdivision
	 */
	public String getRiverbedgradientdivision() {
		return riverbedgradientdivision;
	}

	/**
	 * @param riverbedgradientdivision the riverbedgradientdivision to set
	 */
	public void setRiverbedgradientdivision(String riverbedgradientdivision) {
		this.riverbedgradientdivision = riverbedgradientdivision;
	}

	/**
	 * @type String
	 * @return the riverbedgradientdrdgn
	 */
	public String getRiverbedgradientdrdgn() {
		return riverbedgradientdrdgn;
	}

	/**
	 * @param riverbedgradientdrdgn the riverbedgradientdrdgn to set
	 */
	public void setRiverbedgradientdrdgn(String riverbedgradientdrdgn) {
		this.riverbedgradientdrdgn = riverbedgradientdrdgn;
	}

	/**
	 * @type String
	 * @return the soilmoveamntweight
	 */
	public String getSoilmoveamntweight() {
		return soilmoveamntweight;
	}

	/**
	 * @param soilmoveamntweight the soilmoveamntweight to set
	 */
	public void setSoilmoveamntweight(String soilmoveamntweight) {
		this.soilmoveamntweight = soilmoveamntweight;
	}

	/**
	 * @type String
	 * @return the soilmoveamntcoeff
	 */
	public String getSoilmoveamntcoeff() {
		return soilmoveamntcoeff;
	}

	/**
	 * @param soilmoveamntcoeff the soilmoveamntcoeff to set
	 */
	public void setSoilmoveamntcoeff(String soilmoveamntcoeff) {
		this.soilmoveamntcoeff = soilmoveamntcoeff;
	}

	/**
	 * @type String
	 * @return the soilmoveamntdivision
	 */
	public String getSoilmoveamntdivision() {
		return soilmoveamntdivision;
	}

	/**
	 * @param soilmoveamntdivision the soilmoveamntdivision to set
	 */
	public void setSoilmoveamntdivision(String soilmoveamntdivision) {
		this.soilmoveamntdivision = soilmoveamntdivision;
	}

	/**
	 * @type String
	 * @return the soilmoveamntdrdgn
	 */
	public String getSoilmoveamntdrdgn() {
		return soilmoveamntdrdgn;
	}

	/**
	 * @param soilmoveamntdrdgn the soilmoveamntdrdgn to set
	 */
	public void setSoilmoveamntdrdgn(String soilmoveamntdrdgn) {
		this.soilmoveamntdrdgn = soilmoveamntdrdgn;
	}

	/**
	 * @type String
	 * @return the cmprsstrncncrt3
	 */
	public String getCmprsstrncncrt3() {
		return cmprsstrncncrt3;
	}

	/**
	 * @param cmprsstrncncrt3 the cmprsstrncncrt3 to set
	 */
	public void setCmprsstrncncrt3(String cmprsstrncncrt3) {
		this.cmprsstrncncrt3 = cmprsstrncncrt3;
	}

	/**
	 * @type String
	 * @return the cmprsstrncncrt4
	 */
	public String getCmprsstrncncrt4() {
		return cmprsstrncncrt4;
	}

	/**
	 * @param cmprsstrncncrt4 the cmprsstrncncrt4 to set
	 */
	public void setCmprsstrncncrt4(String cmprsstrncncrt4) {
		this.cmprsstrncncrt4 = cmprsstrncncrt4;
	}

	/**
	 * @type String
	 * @return the complt
	 */
	public String getComplt() {
		return complt;
	}

	/**
	 * @param complt the complt to set
	 */
	public void setComplt(String complt) {
		this.complt = complt;
	}

	/**
	 * @type String
	 * @return the crkdptrslt
	 */
	public String getCrkdptrslt() {
		return crkdptrslt;
	}

	/**
	 * @param crkdptrslt the crkdptrslt to set
	 */
	public void setCrkdptrslt(String crkdptrslt) {
		this.crkdptrslt = crkdptrslt;
	}

	/**
	 * @type String
	 * @return the crkdptcncrt1
	 */
	public String getCrkdptcncrt1() {
		return crkdptcncrt1;
	}

	/**
	 * @param crkdptcncrt1 the crkdptcncrt1 to set
	 */
	public void setCrkdptcncrt1(String crkdptcncrt1) {
		this.crkdptcncrt1 = crkdptcncrt1;
	}

	/**
	 * @type String
	 * @return the crkdptcncrt2
	 */
	public String getCrkdptcncrt2() {
		return crkdptcncrt2;
	}

	/**
	 * @param crkdptcncrt2 the crkdptcncrt2 to set
	 */
	public void setCrkdptcncrt2(String crkdptcncrt2) {
		this.crkdptcncrt2 = crkdptcncrt2;
	}

	/**
	 * @type String
	 * @return the crkdptcncrt3
	 */
	public String getCrkdptcncrt3() {
		return crkdptcncrt3;
	}

	/**
	 * @param crkdptcncrt3 the crkdptcncrt3 to set
	 */
	public void setCrkdptcncrt3(String crkdptcncrt3) {
		this.crkdptcncrt3 = crkdptcncrt3;
	}

	/**
	 * @type String
	 * @return the crkdptcncrt4
	 */
	public String getCrkdptcncrt4() {
		return crkdptcncrt4;
	}

	/**
	 * @param crkdptcncrt4 the crkdptcncrt4 to set
	 */
	public void setCrkdptcncrt4(String crkdptcncrt4) {
		this.crkdptcncrt4 = crkdptcncrt4;
	}

	/**
	 * @type String
	 * @return the crkdptavg1
	 */
	public String getCrkdptavg1() {
		return crkdptavg1;
	}

	/**
	 * @param crkdptavg1 the crkdptavg1 to set
	 */
	public void setCrkdptavg1(String crkdptavg1) {
		this.crkdptavg1 = crkdptavg1;
	}

	/**
	 * @type String
	 * @return the crkdptavg2
	 */
	public String getCrkdptavg2() {
		return crkdptavg2;
	}

	/**
	 * @param crkdptavg2 the crkdptavg2 to set
	 */
	public void setCrkdptavg2(String crkdptavg2) {
		this.crkdptavg2 = crkdptavg2;
	}

	/**
	 * @type String
	 * @return the crkdptavg3
	 */
	public String getCrkdptavg3() {
		return crkdptavg3;
	}

	/**
	 * @param crkdptavg3 the crkdptavg3 to set
	 */
	public void setCrkdptavg3(String crkdptavg3) {
		this.crkdptavg3 = crkdptavg3;
	}

	/**
	 * @type String
	 * @return the crkdptavg4
	 */
	public String getCrkdptavg4() {
		return crkdptavg4;
	}

	/**
	 * @param crkdptavg4 the crkdptavg4 to set
	 */
	public void setCrkdptavg4(String crkdptavg4) {
		this.crkdptavg4 = crkdptavg4;
	}

	/**
	 * @type String
	 * @return the lcmap
	 */
	public String getLcmap() {
		return lcmap;
	}

	/**
	 * @param lcmap the lcmap to set
	 */
	public void setLcmap(String lcmap) {
		this.lcmap = lcmap;
	}

	/**
	 * @type long
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @type String
	 * @return the mapType
	 */
	public String getMapType() {
		return mapType;
	}

	/**
	 * @type String
	 * @return the svyLatLon
	 */
	public String getSvyLatLon() {
		return svyLatLon;
	}

	/**
	 * @param svyLatLon the svyLatLon to set
	 */
	public void setSvyLatLon(String svyLatLon) {
		this.svyLatLon = svyLatLon;
	}

	/**
	 * @type String
	 * @return the svyYear
	 */
	public String getSvyYear() {
		return svyYear;
	}

	/**
	 * @param svyYear the svyYear to set
	 */
	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}

	/**
	 * @type String
	 * @return the mstNm
	 */
	public String getMstNm() {
		return mstNm;
	}

	/**
	 * @param mstNm the mstNm to set
	 */
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}

	/**
	 * @type String
	 * @return the svyMonth
	 */
	public String getSvyMonth() {
		return svyMonth;
	}

	/**
	 * @param svyMonth the svyMonth to set
	 */
	public void setSvyMonth(String svyMonth) {
		this.svyMonth = svyMonth;
	}

	/**
	 * @type String
	 * @return the photoLen
	 */
	public String getPhotoLen() {
		return photoLen;
	}

	/**
	 * @param photoLen the photoLen to set
	 */
	public void setPhotoLen(String photoLen) {
		this.photoLen = photoLen;
	}

	/**
	 * @type String
	 * @return the svycrctnscore
	 */
	public String getSvycrctnscore() {
		return svycrctnscore;
	}

	/**
	 * @param svycrctnscore the svycrctnscore to set
	 */
	public void setSvycrctnscore(String svycrctnscore) {
		this.svycrctnscore = svycrctnscore;
	}

	/**
	 * @type String
	 * @return the dwnsptcrkscore
	 */
	public String getDwnsptcrkscore() {
		return dwnsptcrkscore;
	}

	/**
	 * @param dwnsptcrkscore the dwnsptcrkscore to set
	 */
	public void setDwnsptcrkscore(String dwnsptcrkscore) {
		this.dwnsptcrkscore = dwnsptcrkscore;
	}

	/**
	 * @type String
	 * @return the dwnsptbasicscourscore
	 */
	public String getDwnsptbasicscourscore() {
		return dwnsptbasicscourscore;
	}

	/**
	 * @param dwnsptbasicscourscore the dwnsptbasicscourscore to set
	 */
	public void setDwnsptbasicscourscore(String dwnsptbasicscourscore) {
		this.dwnsptbasicscourscore = dwnsptbasicscourscore;
	}

	/**
	 * @type String
	 * @return the dwnsptdmgscore
	 */
	public String getDwnsptdmgscore() {
		return dwnsptdmgscore;
	}

	/**
	 * @param dwnsptdmgscore the dwnsptdmgscore to set
	 */
	public void setDwnsptdmgscore(String dwnsptdmgscore) {
		this.dwnsptdmgscore = dwnsptdmgscore;
	}

	/**
	 * @type String
	 * @return the orginldamcrkscore
	 */
	public String getOrginldamcrkscore() {
		return orginldamcrkscore;
	}

	/**
	 * @param orginldamcrkscore the orginldamcrkscore to set
	 */
	public void setOrginldamcrkscore(String orginldamcrkscore) {
		this.orginldamcrkscore = orginldamcrkscore;
	}

	/**
	 * @type String
	 * @return the orginldambasicscourscore
	 */
	public String getOrginldambasicscourscore() {
		return orginldambasicscourscore;
	}

	/**
	 * @param orginldambasicscourscore the orginldambasicscourscore to set
	 */
	public void setOrginldambasicscourscore(String orginldambasicscourscore) {
		this.orginldambasicscourscore = orginldambasicscourscore;
	}

	/**
	 * @type String
	 * @return the orginldamwtgatescore
	 */
	public String getOrginldamwtgatescore() {
		return orginldamwtgatescore;
	}

	/**
	 * @param orginldamwtgatescore the orginldamwtgatescore to set
	 */
	public void setOrginldamwtgatescore(String orginldamwtgatescore) {
		this.orginldamwtgatescore = orginldamwtgatescore;
	}

	/**
	 * @type String
	 * @return the orginldamplngscore
	 */
	public String getOrginldamplngscore() {
		return orginldamplngscore;
	}

	/**
	 * @param orginldamplngscore the orginldamplngscore to set
	 */
	public void setOrginldamplngscore(String orginldamplngscore) {
		this.orginldamplngscore = orginldamplngscore;
	}

	/**
	 * @type String
	 * @return the orginldamcncrtscore
	 */
	public String getOrginldamcncrtscore() {
		return orginldamcncrtscore;
	}

	/**
	 * @param orginldamcncrtscore the orginldamcncrtscore to set
	 */
	public void setOrginldamcncrtscore(String orginldamcncrtscore) {
		this.orginldamcncrtscore = orginldamcncrtscore;
	}

	/**
	 * @type String
	 * @return the orginldamdmgscore
	 */
	public String getOrginldamdmgscore() {
		return orginldamdmgscore;
	}

	/**
	 * @param orginldamdmgscore the orginldamdmgscore to set
	 */
	public void setOrginldamdmgscore(String orginldamdmgscore) {
		this.orginldamdmgscore = orginldamdmgscore;
	}

	
	/**
	 * @type String
	 * @return the orginldamstrpnscore
	 */
	public String getOrginldamstrpnscore() {
		return orginldamstrpnscore;
	}

	/**
	 * @param orginldamstrpnscore the orginldamstrpnscore to set
	 */
	public void setOrginldamstrpnscore(String orginldamstrpnscore) {
		this.orginldamstrpnscore = orginldamstrpnscore;
	}

	/**
	 * @type String
	 * @return the sidewallcrkscore
	 */
	public String getSidewallcrkscore() {
		return sidewallcrkscore;
	}

	/**
	 * @param sidewallcrkscore the sidewallcrkscore to set
	 */
	public void setSidewallcrkscore(String sidewallcrkscore) {
		this.sidewallcrkscore = sidewallcrkscore;
	}

	/**
	 * @type String
	 * @return the sidewallbasicscourscore
	 */
	public String getSidewallbasicscourscore() {
		return sidewallbasicscourscore;
	}

	/**
	 * @param sidewallbasicscourscore the sidewallbasicscourscore to set
	 */
	public void setSidewallbasicscourscore(String sidewallbasicscourscore) {
		this.sidewallbasicscourscore = sidewallbasicscourscore;
	}

	/**
	 * @type String
	 * @return the sidewalldmgscore
	 */
	public String getSidewalldmgscore() {
		return sidewalldmgscore;
	}

	/**
	 * @param sidewalldmgscore the sidewalldmgscore to set
	 */
	public void setSidewalldmgscore(String sidewalldmgscore) {
		this.sidewalldmgscore = sidewalldmgscore;
	}

	/**
	 * @type String
	 * @return the orginldamcncrt
	 */
	public String getOrginldamcncrt() {
		return orginldamcncrt;
	}

	/**
	 * @param orginldamcncrt the orginldamcncrt to set
	 */
	public void setOrginldamcncrt(String orginldamcncrt) {
		this.orginldamcncrt = orginldamcncrt;
	}

	/**
	 * @type String
	 * @return the changedZoom
	 */
	public String getChangedZoom() {
		return changedZoom;
	}

	/**
	 * @param changedZoom the changedZoom to set
	 */
	public void setChangedZoom(String changedZoom) {
		this.changedZoom = changedZoom;
	}

	/**
	 * @type int
	 * @return the locImgIdx
	 */
	public int getLocImgIdx() {
		return locImgIdx;
	}

	/**
	 * @param locImgIdx the locImgIdx to set
	 */
	public void setLocImgIdx(int locImgIdx) {
		this.locImgIdx = locImgIdx;
	}

	/**
	 * @type String
	 * @return the photoSrc1
	 */
	public String getPhotoSrc1() {
		return photoSrc1;
	}

	/**
	 * @param photoSrc1 the photoSrc1 to set
	 */
	public void setPhotoSrc1(String photoSrc1) {
		this.photoSrc1 = photoSrc1;
	}

	/**
	 * @type String
	 * @return the photoSrc2
	 */
	public String getPhotoSrc2() {
		return photoSrc2;
	}

	/**
	 * @param photoSrc2 the photoSrc2 to set
	 */
	public void setPhotoSrc2(String photoSrc2) {
		this.photoSrc2 = photoSrc2;
	}

	/**
	 * @type String
	 * @return the photoSrc3
	 */
	public String getPhotoSrc3() {
		return photoSrc3;
	}

	/**
	 * @param photoSrc3 the photoSrc3 to set
	 */
	public void setPhotoSrc3(String photoSrc3) {
		this.photoSrc3 = photoSrc3;
	}

	/**
	 * @type String
	 * @return the photoSrc4
	 */
	public String getPhotoSrc4() {
		return photoSrc4;
	}

	/**
	 * @param photoSrc4 the photoSrc4 to set
	 */
	public void setPhotoSrc4(String photoSrc4) {
		this.photoSrc4 = photoSrc4;
	}

	/**
	 * @type String
	 * @return the photoSrc5
	 */
	public String getPhotoSrc5() {
		return photoSrc5;
	}

	/**
	 * @param photoSrc5 the photoSrc5 to set
	 */
	public void setPhotoSrc5(String photoSrc5) {
		this.photoSrc5 = photoSrc5;
	}

	/**
	 * @type String
	 * @return the photoSrc6
	 */
	public String getPhotoSrc6() {
		return photoSrc6;
	}

	/**
	 * @param photoSrc6 the photoSrc6 to set
	 */
	public void setPhotoSrc6(String photoSrc6) {
		this.photoSrc6 = photoSrc6;
	}

	/**
	 * @type String
	 * @return the photoTag
	 */
	public String getPhotoTag() {
		return photoTag;
	}

	/**
	 * @param photoTag the photoTag to set
	 */
	public void setPhotoTag(String photoTag) {
		this.photoTag = photoTag;
	}

	/**
	 * @type String
	 * @return the photoTagList
	 */
	public String getPhotoTagList() {
		return photoTagList;
	}

	/**
	 * @param photoTagList the photoTagList to set
	 */
	public void setPhotoTagList(String photoTagList) {
		this.photoTagList = photoTagList;
	}

	/**
	 * @type String
	 * @return the photoTag1
	 */
	public String getPhotoTag1() {
		return photoTag1;
	}

	/**
	 * @param photoTag1 the photoTag1 to set
	 */
	public void setPhotoTag1(String photoTag1) {
		this.photoTag1 = photoTag1;
	}

	/**
	 * @type String
	 * @return the photoTag2
	 */
	public String getPhotoTag2() {
		return photoTag2;
	}

	/**
	 * @param photoTag2 the photoTag2 to set
	 */
	public void setPhotoTag2(String photoTag2) {
		this.photoTag2 = photoTag2;
	}

	/**
	 * @type String
	 * @return the photoTag3
	 */
	public String getPhotoTag3() {
		return photoTag3;
	}

	/**
	 * @param photoTag3 the photoTag3 to set
	 */
	public void setPhotoTag3(String photoTag3) {
		this.photoTag3 = photoTag3;
	}

	/**
	 * @type String
	 * @return the photoTag4
	 */
	public String getPhotoTag4() {
		return photoTag4;
	}

	/**
	 * @param photoTag4 the photoTag4 to set
	 */
	public void setPhotoTag4(String photoTag4) {
		this.photoTag4 = photoTag4;
	}

	/**
	 * @type String
	 * @return the photoTag5
	 */
	public String getPhotoTag5() {
		return photoTag5;
	}

	/**
	 * @param photoTag5 the photoTag5 to set
	 */
	public void setPhotoTag5(String photoTag5) {
		this.photoTag5 = photoTag5;
	}

	/**
	 * @type String
	 * @return the photoTag6
	 */
	public String getPhotoTag6() {
		return photoTag6;
	}

	/**
	 * @param photoTag6 the photoTag6 to set
	 */
	public void setPhotoTag6(String photoTag6) {
		this.photoTag6 = photoTag6;
	}
	
}