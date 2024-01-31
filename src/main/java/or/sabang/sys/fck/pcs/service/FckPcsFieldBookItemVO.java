
package or.sabang.sys.fck.pcs.service;

import egovframework.com.cmm.ComDefaultVO;

public class FckPcsFieldBookItemVO extends ComDefaultVO {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 공유방 ID
	 */
	private String MST_ID;
	
	/**
	 * 로그인 ID
	 */
	private String LOGIN_ID ;

	/**
	 * 키값
	 */
	private int _FID;
	/**
	 * x좌표
	 */
	private String _LON;
	
	/**
	 * y좌표
	 */
	private String _LAT;
	
	/**
	 * Geometry
	 */
	private String _DATA;

	/**
	 * 키워드
	 */
	private String _KEYWORD;
	
	/**
	 * 라벨
	 */
	private String _LABEL;
	
	/**
	 * 스타일
	 */
	private String _STYLE;
	
	/**
	 * 메모
	 */
	private String _MEMO;
	
	/**
	 * 태그1
	 */
	private String _TAG1;
	
	/**
	 * 태그2
	 */
	private String _TAG2;
	
	/**
	 * 등록일자
	 */
	private String _REG_DATE;
	
	/**
	 * 수정일자
	 */
	private String _UPD_DATE;

	/**
	 * 상세속성
	 */
	private String ATTR;

	/**
	 * 위도 Decimal Degree
	 */
	private Double LATDD;
	/**
	 * 경도 Decimal Degree
	 */
	private Double LONDD;
	
	/*
	 *  공유방 단건
	 */
	
	/* smid */
	private String smid;
	/* 조사ID */
	private String svyId;
	/* 작성자ID */
	private String loginId;
	/* 조사유형 */
	private String svyType;
	/* 형식 */
	private String svyForm;	
	/* 점검일시 */
	private String svyDt;
	/* 점검자 */
	private String svyUser;
	/* 점검자 */
	private String chkUser;
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
	/* 사방댐 형식 */
	private String ecnrForm;
	/* 사방댐 관리번호 */
	private String ecnrRnum;
	/* 사방댐, 해안사방 국가지점번호 */
	private String nationSpotNum;
	/* 사방댐 종류 */
	private String ecnrKnd;
	/*종류, 시설물 종류*/
	private String knd;
	/* 지정면적 */
	private String dsgArea;
	/* 시설제원 시설년도 */
	private String fcltYear;
	/* 계류보전,산지사방 시설제원 폭 */
	private String fcltRng;
	/* 사방댐 시설제원 상장(m) */
	private String fcltUprHg;
	/* 사방댐 시설제원 하장(m) */
	private String fcltLwrHg;
	/* 사방댐 시설제원 유효고(m) */
	private String fcltStkHg;
	/* 계류보전 시설제원 길이 */
	private String fcltLng;
	/* 계류보전 시설제원 깊이 */
	private String fcltDept;
	/* 계류보전 골막이 측정값 */
	private String chkdamVal;
	/* 계류보전 골막이 판정값 */
	private String chkdamJdgVal;
	/* 계류보전 기슭막이 측정값 */
	private String rvtmntVal;
	/* 계류보전 기슭막이 판정값 */
	private String rvtmntJdgVal;
	/* 계류보전 바닥막이 측정값 */
	private String grdstablVal;
	/* 계류보전 바닥막이 판정값 */
	private String grdstablJdgVal;
	/* 계류보전 계류상태 판정값 */
	private String mooringJdgVal;
	/*계류보전 계류상태 */
	private String mrngsttus;
	
	/* 사방댐 본댐 측정값 */
	private String orginlDamVal;
	/* 사방댐 본댐 판정값 */
	private String orginlDamJdgVal;
	/* 사방댐 측벽 측정값 */
	private String sideWallVal;
	/* 사방댐 측벽 판정값 */
	private String sideWallJdgVal;
	/* 사방댐 물받이 측정값 */
	private String dwnsptVal;
	/* 사방댐 물받이 판정값 */
	private String dwnsptJdgVal;
	/* 사방댐 저사상태 판정값 */
	private String snddpsitJdgVal;
	/* 사방댐 저사량, 저사상태 측정값 */
	private String snddpsitVal;
	
	/* 산지사방 보강시설 측정값 */
	private String reinfcVal;
	/* 산지사방 보강시설 판정값 */
	private String reinfcJdgVal;
	/* 산지사방 보호시설 측정값 */
	private String prtcVal;
	/* 산지사방 보호시설 판정값  */
	private String prtcJdgVal;
	/* 산지사방 배수시설 측정값 */
	private String drngVal;
	/* 산지사방 배수시설 판정값 */
	private String drngJdgVal;
	/* 산지사방 사면상태 측정값 */
	private String slopeJdgVal;
	/* 산지사방 사면상태 판정값 */
	private String slopeVal;
	
	/* 수문 판정값 */
	private String flugtJdgVal;
	/* 식생상태 판정값 */
	private String vtnsttusJdgVal;
	/* 안전시설 판정값 */
	private String sffcJdgVal;
	/* 접근도로 판정값 */
	private String accssrdJdgVal;
	/* 기타 판정값 */
	private String etcJdgVal;
	
	/* 최종점검결과 */
	private String fckRslt;
	/* 조치사항 */
	private String mngmtr;
	/* 지정해제 */
	private String appnRelis;
	
	/* 종합의견1 */
	private String opinion1;
	/* 종합의견2 */
	private String opinion2;
	/* 종합의견3 */
	private String opinion3;
	/* 종합의견4 */
	private String opinion4;
	/* 종합의견5 */
	private String opinion5;
	
	/* 위도 */
	private String svyLat;
	private String svyLatD;
	private String svyLatM;
	private String svyLatS;
	/* 경도 */
	private String svyLon;
	private String svyLonD;
	private String svyLonM;
	private String svyLonS;
	
	/* 시점 */
	/* 위도 */
	private String bLatD;
	private String bLatM;
	private String bLatS;
	/* 경도 */
	private String bLonD;
	private String bLonM;
	private String bLonS;
	
	/* 종점 */
	/* 위도 */
	private String eLatD;
	private String eLatM;
	private String eLatS;
	
	/* 경도 */
	private String eLonD;
	private String eLonM;
	private String eLonS;
	
	
	private String createDt;
	
	
	/* 일련번호 */
	private String sn;
	/* 속칭 */
	private String commonly;
	/* 기타_특이사항 */
	private String etcSpecialNote;
	/* 주요시설_특이사항 */
	private String mainFcltSpecialNote;
	/* 부대시설_특이사항 */
	private String subfcltSpecialNote;
	
	/* 사방댐 본댐코드 */
	private String orginlDam;
	/* 사방댐 측벽코드 */
	private String sideWall;
	/* 사방댐 물받이코드 */
	private String dwnspt;
	
	/* 계류보전 골막이 코드 */
	private String chkdamCd;
	/* 계류보전 기슭막이 코드 */
	private String rvtmntCd;
	/* 계류보전 바닥막이 코드 */
	private String grdstablCd;
	
	/* 산지사방 보강시설코드 */
	private String reinfcCd;
	/* 산지사방 보호시설코드 */
	private String prtcCd;
	/* 산지사방 배수시설코드 */
	private String drngCd;
	
	/*조사유형 - 사방댐종류(강재사방댐 등)*/
	private String svyType2;
	/* 전고 */
	private String tthghjdgval;
	/* 천단폭 */
	private String wotdjdgval;
	/* 시공비 */
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
	/*측벽길이*/	
	private String sidewalllng;
	/*측벽높이*/	
	private String sidewallheg;
	/*물받이가로*/	
	private String dwnspthrz;
	/*물받이세로*/	
	private String dwnsptvtc;
	/*사방사업명*/	
	private String cnstrname;
	/*주요공작물_시설명*/	
	private String fcltmainstrctu;
	/*길이_주요시설1*/	
	private String fcltlng1;
	/*높이_주요시설1*/	
	private String fcltheg1;
	/*가로_주요시설2*/	
	private String fclthrz2;
	/*세로_주요시설2*/	
	private String fcltvtc2;
	
	/* 조사ID(전자야장 상세검색) */
	private String svyid;
	/* 시도명(전자야장 상세검색)*/
	private String sd;
	/* 시군구명 */
	private String sgg;
	/* 읍면동명 */
	private String emd;
	/* 리명 */
	private String ri;	
	
	public String getMST_ID() {
		return MST_ID;
	}

	public void setMST_ID(String MST_ID) {
		this.MST_ID = MST_ID;
	}

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		this.LOGIN_ID = lOGIN_ID;
	}

	public int getFID() {
		return _FID;
	}

	public void setFID(int _FID) {
		this._FID = _FID;
	}

	public String getLON() {
		return _LON;
	}

	public void setLON(String _LON) {
		this._LON = _LON;
	}

	public String getLAT() {
		return _LAT;
	}

	public void setLAT(String _LAT) {
		this._LAT = _LAT;
	}

	public String getDATA() {
		return _DATA;
	}

	public void setDATA(String _DATA) {
		this._DATA = _DATA;
	}

	public String getKEYWORD() {
		return _KEYWORD;
	}

	public void setKEYWORD(String _KEYWORD) {
		this._KEYWORD = _KEYWORD;
	}

	public String getLABEL() {
		return _LABEL;
	}

	public void setLABEL(String _LABEL) {
		this._LABEL = _LABEL;
	}

	public String getSTYLE() {
		return _STYLE;
	}

	public void setSTYLE(String _STYLE) {
		this._STYLE = _STYLE;
	}

	public String getMEMO() {
		return _MEMO;
	}

	public void setMEMO(String _MEMO) {
		this._MEMO = _MEMO;
	}

	public String getTAG1() {
		return _TAG1;
	}

	public void setTAG1(String _TAG1) {
		this._TAG1 = _TAG1;
	}

	public String getTAG2() {
		return _TAG2;
	}

	public void setTAG2(String _TAG2) {
		this._TAG2 = _TAG2;
	}

	public String getREG_DATE() {
		return _REG_DATE;
	}

	public void setREG_DATE(String _REG_DATE) {
		this._REG_DATE = _REG_DATE;
	}

	public String getUPD_DATE() {
		return _UPD_DATE;
	}

	public void setUPD_DATE(String _UPD_DATE) {
		this._UPD_DATE = _UPD_DATE;
	}

	public String getATTR() {
		return ATTR;
	}

	public void setATTR(String ATTR) {
		this.ATTR = ATTR;
	}

	public Double getLATDD() {
		return LATDD;
	}

	public void setLATDD(Double lATDD) {
		LATDD = lATDD;
	}

	public Double getLONDD() {
		return LONDD;
	}

	public void setLONDD(Double lONDD) {
		LONDD = lONDD;
	}
	
	public String getbLatD() {
		return bLatD;
	}

	public void setbLatD(String bLatD) {
		this.bLatD = bLatD;
	}

	public String getbLatM() {
		return bLatM;
	}

	public void setbLatM(String bLatM) {
		this.bLatM = bLatM;
	}

	public String getbLatS() {
		return bLatS;
	}

	public void setbLatS(String bLatS) {
		this.bLatS = bLatS;
	}

	public String getbLonD() {
		return bLonD;
	}

	public void setbLonD(String bLonD) {
		this.bLonD = bLonD;
	}

	public String getbLonM() {
		return bLonM;
	}

	public void setbLonM(String bLonM) {
		this.bLonM = bLonM;
	}

	public String getbLonS() {
		return bLonS;
	}

	public void setbLonS(String bLonS) {
		this.bLonS = bLonS;
	}

	public String getSmid() {
		return smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
	}

	public String getSvyId() {
		return svyId;
	}

	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getSvyType() {
		return svyType;
	}

	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}

	public String getSvyForm() {
		return svyForm;
	}

	public void setSvyForm(String svyForm) {
		this.svyForm = svyForm;
	}

	public String getSvyDt() {
		return svyDt;
	}

	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
	}

	public String getSvyUser() {
		return svyUser;
	}

	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
	}

	public String getChkUser() {
		return chkUser;
	}

	public void setChkUser(String chkUser) {
		this.chkUser = chkUser;
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

	public String getEcnrForm() {
		return ecnrForm;
	}

	public void setEcnrForm(String ecnrForm) {
		this.ecnrForm = ecnrForm;
	}

	public String getEcnrRnum() {
		return ecnrRnum;
	}

	public void setEcnrRnum(String ecnrRnum) {
		this.ecnrRnum = ecnrRnum;
	}

	public String getNationSpotNum() {
		return nationSpotNum;
	}

	public void setNationSpotNum(String nationSpotNum) {
		this.nationSpotNum = nationSpotNum;
	}

	public String getEcnrKnd() {
		return ecnrKnd;
	}

	public void setEcnrKnd(String ecnrKnd) {
		this.ecnrKnd = ecnrKnd;
	}

	public String getKnd() {
		return knd;
	}

	public void setKnd(String knd) {
		this.knd = knd;
	}

	public String getDsgArea() {
		return dsgArea;
	}

	public void setDsgArea(String dsgArea) {
		this.dsgArea = dsgArea;
	}

	public String getFcltYear() {
		return fcltYear;
	}

	public void setFcltYear(String fcltYear) {
		this.fcltYear = fcltYear;
	}

	public String getFcltRng() {
		return fcltRng;
	}

	public void setFcltRng(String fcltRng) {
		this.fcltRng = fcltRng;
	}

	public String getFcltUprHg() {
		return fcltUprHg;
	}

	public void setFcltUprHg(String fcltUprHg) {
		this.fcltUprHg = fcltUprHg;
	}

	public String getFcltLwrHg() {
		return fcltLwrHg;
	}

	public void setFcltLwrHg(String fcltLwrHg) {
		this.fcltLwrHg = fcltLwrHg;
	}

	public String getFcltStkHg() {
		return fcltStkHg;
	}

	public void setFcltStkHg(String fcltStkHg) {
		this.fcltStkHg = fcltStkHg;
	}

	public String getFcltLng() {
		return fcltLng;
	}

	public void setFcltLng(String fcltLng) {
		this.fcltLng = fcltLng;
	}

	public String getFcltDept() {
		return fcltDept;
	}

	public void setFcltDept(String fcltDept) {
		this.fcltDept = fcltDept;
	}

	public String getChkdamVal() {
		return chkdamVal;
	}

	public void setChkdamVal(String chkdamVal) {
		this.chkdamVal = chkdamVal;
	}

	public String getChkdamJdgVal() {
		return chkdamJdgVal;
	}

	public void setChkdamJdgVal(String chkdamJdgVal) {
		this.chkdamJdgVal = chkdamJdgVal;
	}

	public String getRvtmntVal() {
		return rvtmntVal;
	}

	public void setRvtmntVal(String rvtmntVal) {
		this.rvtmntVal = rvtmntVal;
	}

	public String getRvtmntJdgVal() {
		return rvtmntJdgVal;
	}

	public void setRvtmntJdgVal(String rvtmntJdgVal) {
		this.rvtmntJdgVal = rvtmntJdgVal;
	}

	public String getGrdstablVal() {
		return grdstablVal;
	}

	public void setGrdstablVal(String grdstablVal) {
		this.grdstablVal = grdstablVal;
	}

	public String getGrdstablJdgVal() {
		return grdstablJdgVal;
	}

	public void setGrdstablJdgVal(String grdstablJdgVal) {
		this.grdstablJdgVal = grdstablJdgVal;
	}

	public String getMooringJdgVal() {
		return mooringJdgVal;
	}

	public void setMooringJdgVal(String mooringJdgVal) {
		this.mooringJdgVal = mooringJdgVal;
	}

	public String getReinfcVal() {
		return reinfcVal;
	}

	public void setReinfcVal(String reinfcVal) {
		this.reinfcVal = reinfcVal;
	}

	public String getReinfcJdgVal() {
		return reinfcJdgVal;
	}

	public void setReinfcJdgVal(String reinfcJdgVal) {
		this.reinfcJdgVal = reinfcJdgVal;
	}

	public String getPrtcVal() {
		return prtcVal;
	}

	public void setPrtcVal(String prtcVal) {
		this.prtcVal = prtcVal;
	}

	public String getPrtcJdgVal() {
		return prtcJdgVal;
	}

	public void setPrtcJdgVal(String prtcJdgVal) {
		this.prtcJdgVal = prtcJdgVal;
	}

	public String getDrngVal() {
		return drngVal;
	}

	public void setDrngVal(String drngVal) {
		this.drngVal = drngVal;
	}

	public String getDrngJdgVal() {
		return drngJdgVal;
	}

	public void setDrngJdgVal(String drngJdgVal) {
		this.drngJdgVal = drngJdgVal;
	}

	public String getSlopeJdgVal() {
		return slopeJdgVal;
	}

	public void setSlopeJdgVal(String slopeJdgVal) {
		this.slopeJdgVal = slopeJdgVal;
	}

	public String getSlopeVal() {
		return slopeVal;
	}

	public void setSlopeVal(String slopeVal) {
		this.slopeVal = slopeVal;
	}

	public String getFlugtJdgVal() {
		return flugtJdgVal;
	}

	public void setFlugtJdgVal(String flugtJdgVal) {
		this.flugtJdgVal = flugtJdgVal;
	}

	public String getVtnsttusJdgVal() {
		return vtnsttusJdgVal;
	}

	public void setVtnsttusJdgVal(String vtnsttusJdgVal) {
		this.vtnsttusJdgVal = vtnsttusJdgVal;
	}

	public String getSffcJdgVal() {
		return sffcJdgVal;
	}

	public void setSffcJdgVal(String sffcJdgVal) {
		this.sffcJdgVal = sffcJdgVal;
	}

	public String getAccssrdJdgVal() {
		return accssrdJdgVal;
	}

	public void setAccssrdJdgVal(String accssrdJdgVal) {
		this.accssrdJdgVal = accssrdJdgVal;
	}

	public String getEtcJdgVal() {
		return etcJdgVal;
	}

	public void setEtcJdgVal(String etcJdgVal) {
		this.etcJdgVal = etcJdgVal;
	}

	public String getFckRslt() {
		return fckRslt;
	}

	public void setFckRslt(String fckRslt) {
		this.fckRslt = fckRslt;
	}

	public String getMngmtr() {
		return mngmtr;
	}

	public void setMngmtr(String mngmtr) {
		this.mngmtr = mngmtr;
	}

	public String getAppnRelis() {
		return appnRelis;
	}

	public void setAppnRelis(String appnRelis) {
		this.appnRelis = appnRelis;
	}

	public String getOpinion1() {
		return opinion1;
	}

	public void setOpinion1(String opinion1) {
		this.opinion1 = opinion1;
	}

	public String getOpinion2() {
		return opinion2;
	}

	public void setOpinion2(String opinion2) {
		this.opinion2 = opinion2;
	}

	public String getOpinion3() {
		return opinion3;
	}

	public void setOpinion3(String opinion3) {
		this.opinion3 = opinion3;
	}

	public String getOpinion4() {
		return opinion4;
	}

	public void setOpinion4(String opinion4) {
		this.opinion4 = opinion4;
	}

	public String getOpinion5() {
		return opinion5;
	}

	public void setOpinion5(String opinion5) {
		this.opinion5 = opinion5;
	}

	public String getOrginlDamVal() {
		return orginlDamVal;
	}

	public void setOrginlDamVal(String orginlDamVal) {
		this.orginlDamVal = orginlDamVal;
	}

	public String getOrginlDamJdgVal() {
		return orginlDamJdgVal;
	}

	public void setOrginlDamJdgVal(String orginlDamJdgVal) {
		this.orginlDamJdgVal = orginlDamJdgVal;
	}

	public String getSideWallVal() {
		return sideWallVal;
	}

	public void setSideWallVal(String sideWallVal) {
		this.sideWallVal = sideWallVal;
	}

	public String getSideWallJdgVal() {
		return sideWallJdgVal;
	}

	public void setSideWallJdgVal(String sideWallJdgVal) {
		this.sideWallJdgVal = sideWallJdgVal;
	}

	public String getDwnsptVal() {
		return dwnsptVal;
	}

	public void setDwnsptVal(String dwnsptVal) {
		this.dwnsptVal = dwnsptVal;
	}

	public String getDwnsptJdgVal() {
		return dwnsptJdgVal;
	}

	public void setDwnsptJdgVal(String dwnsptJdgVal) {
		this.dwnsptJdgVal = dwnsptJdgVal;
	}

	public String getSnddpsitJdgVal() {
		return snddpsitJdgVal;
	}

	public void setSnddpsitJdgVal(String snddpsitJdgVal) {
		this.snddpsitJdgVal = snddpsitJdgVal;
	}

	public String getSnddpsitVal() {
		return snddpsitVal;
	}

	public void setSnddpsitVal(String snddpsitVal) {
		this.snddpsitVal = snddpsitVal;
	}

	public String getSvyLat() {
		return svyLat;
	}

	public void setSvyLat(String svyLat) {
		this.svyLat = svyLat;
	}

	public String getSvyLon() {
		return svyLon;
	}

	public void setSvyLon(String svyLon) {
		this.svyLon = svyLon;
	}

	public String getSvyLatD() {
		return svyLatD;
	}

	public void setSvyLatD(String svyLatD) {
		this.svyLatD = svyLatD;
	}

	public String getSvyLatM() {
		return svyLatM;
	}

	public void setSvyLatM(String svyLatM) {
		this.svyLatM = svyLatM;
	}

	public String getSvyLatS() {
		return svyLatS;
	}

	public void setSvyLatS(String svyLatS) {
		this.svyLatS = svyLatS;
	}

	public String getSvyLonD() {
		return svyLonD;
	}

	public void setSvyLonD(String svyLonD) {
		this.svyLonD = svyLonD;
	}

	public String getSvyLonM() {
		return svyLonM;
	}

	public void setSvyLonM(String svyLonM) {
		this.svyLonM = svyLonM;
	}

	public String getSvyLonS() {
		return svyLonS;
	}

	public void setSvyLonS(String svyLonS) {
		this.svyLonS = svyLonS;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCommonly() {
		return commonly;
	}

	public void setCommonly(String commonly) {
		this.commonly = commonly;
	}

	public String getEtcSpecialNote() {
		return etcSpecialNote;
	}

	public void setEtcSpecialNote(String etcSpecialNote) {
		this.etcSpecialNote = etcSpecialNote;
	}

	public String getMainFcltSpecialNote() {
		return mainFcltSpecialNote;
	}

	public void setMainFcltSpecialNote(String mainFcltSpecialNote) {
		this.mainFcltSpecialNote = mainFcltSpecialNote;
	}

	public String getSubfcltSpecialNote() {
		return subfcltSpecialNote;
	}

	public void setSubfcltSpecialNote(String subfcltSpecialNote) {
		this.subfcltSpecialNote = subfcltSpecialNote;
	}

	public String getChkdamCd() {
		return chkdamCd;
	}

	public void setChkdamCd(String chkdamCd) {
		this.chkdamCd = chkdamCd;
	}

	public String getRvtmntCd() {
		return rvtmntCd;
	}

	public void setRvtmntCd(String rvtmntCd) {
		this.rvtmntCd = rvtmntCd;
	}

	public String getGrdstablCd() {
		return grdstablCd;
	}

	public void setGrdstablCd(String grdstablCd) {
		this.grdstablCd = grdstablCd;
	}

	public String getReinfcCd() {
		return reinfcCd;
	}

	public void setReinfcCd(String reinfcCd) {
		this.reinfcCd = reinfcCd;
	}

	public String getPrtcCd() {
		return prtcCd;
	}

	public void setPrtcCd(String prtcCd) {
		this.prtcCd = prtcCd;
	}

	public String getDrngCd() {
		return drngCd;
	}

	public void setDrngCd(String drngCd) {
		this.drngCd = drngCd;
	}

	public String geteLatD() {
		return eLatD;
	}

	public void seteLatD(String eLatD) {
		this.eLatD = eLatD;
	}

	public String geteLatM() {
		return eLatM;
	}

	public void seteLatM(String eLatM) {
		this.eLatM = eLatM;
	}

	public String geteLatS() {
		return eLatS;
	}

	public void seteLatS(String eLatS) {
		this.eLatS = eLatS;
	}

	public String geteLonD() {
		return eLonD;
	}

	public void seteLonD(String eLonD) {
		this.eLonD = eLonD;
	}

	public String geteLonM() {
		return eLonM;
	}

	public void seteLonM(String eLonM) {
		this.eLonM = eLonM;
	}

	public String geteLonS() {
		return eLonS;
	}

	public void seteLonS(String eLonS) {
		this.eLonS = eLonS;
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

	public String getTthghjdgval() {
		return tthghjdgval;
	}

	public void setTthghjdgval(String tthghjdgval) {
		this.tthghjdgval = tthghjdgval;
	}

	public String getWotdjdgval() {
		return wotdjdgval;
	}

	public void setWotdjdgval(String wotdjdgval) {
		this.wotdjdgval = wotdjdgval;
	}

	public String getCnstrcost() {
		return cnstrcost;
	}

	public void setCnstrcost(String cnstrcost) {
		this.cnstrcost = cnstrcost;
	}

	public String getLatdlndsld() {
		return latdlndsld;
	}

	public void setLatdlndsld(String latdlndsld) {
		this.latdlndsld = latdlndsld;
	}

	public String getLatmlndsld() {
		return latmlndsld;
	}

	public void setLatmlndsld(String latmlndsld) {
		this.latmlndsld = latmlndsld;
	}

	public String getLatslndsld() {
		return latslndsld;
	}

	public void setLatslndsld(String latslndsld) {
		this.latslndsld = latslndsld;
	}

	public String getLondlndsld() {
		return londlndsld;
	}

	public void setLondlndsld(String londlndsld) {
		this.londlndsld = londlndsld;
	}

	public String getLonmlndsld() {
		return lonmlndsld;
	}

	public void setLonmlndsld(String lonmlndsld) {
		this.lonmlndsld = lonmlndsld;
	}

	public String getLonslndsld() {
		return lonslndsld;
	}

	public void setLonslndsld(String lonslndsld) {
		this.lonslndsld = lonslndsld;
	}

	public String getLatdacplcsld() {
		return latdacplcsld;
	}

	public void setLatdacplcsld(String latdacplcsld) {
		this.latdacplcsld = latdacplcsld;
	}

	public String getLatmacplcsld() {
		return latmacplcsld;
	}

	public void setLatmacplcsld(String latmacplcsld) {
		this.latmacplcsld = latmacplcsld;
	}

	public String getLatsacplcsld() {
		return latsacplcsld;
	}

	public void setLatsacplcsld(String latsacplcsld) {
		this.latsacplcsld = latsacplcsld;
	}

	public String getLondacplcsld() {
		return londacplcsld;
	}

	public void setLondacplcsld(String londacplcsld) {
		this.londacplcsld = londacplcsld;
	}

	public String getLonmacplcsld() {
		return lonmacplcsld;
	}

	public void setLonmacplcsld(String lonmacplcsld) {
		this.lonmacplcsld = lonmacplcsld;
	}

	public String getLonsacplcsld() {
		return lonsacplcsld;
	}

	public void setLonsacplcsld(String lonsacplcsld) {
		this.lonsacplcsld = lonsacplcsld;
	}

	public String getSidewalllng() {
		return sidewalllng;
	}

	public void setSidewalllng(String sidewalllng) {
		this.sidewalllng = sidewalllng;
	}

	public String getSidewallheg() {
		return sidewallheg;
	}

	public void setSidewallheg(String sidewallheg) {
		this.sidewallheg = sidewallheg;
	}

	public String getDwnspthrz() {
		return dwnspthrz;
	}

	public void setDwnspthrz(String dwnspthrz) {
		this.dwnspthrz = dwnspthrz;
	}

	public String getDwnsptvtc() {
		return dwnsptvtc;
	}

	public void setDwnsptvtc(String dwnsptvtc) {
		this.dwnsptvtc = dwnsptvtc;
	}

	public String getCnstrname() {
		return cnstrname;
	}

	public void setCnstrname(String cnstrname) {
		this.cnstrname = cnstrname;
	}

	public String getFcltmainstrctu() {
		return fcltmainstrctu;
	}

	public void setFcltmainstrctu(String fcltmainstrctu) {
		this.fcltmainstrctu = fcltmainstrctu;
	}

	public String getFcltlng1() {
		return fcltlng1;
	}

	public void setFcltlng1(String fcltlng1) {
		this.fcltlng1 = fcltlng1;
	}

	public String getFcltheg1() {
		return fcltheg1;
	}

	public void setFcltheg1(String fcltheg1) {
		this.fcltheg1 = fcltheg1;
	}

	public String getFclthrz2() {
		return fclthrz2;
	}

	public void setFclthrz2(String fclthrz2) {
		this.fclthrz2 = fclthrz2;
	}

	public String getFcltvtc2() {
		return fcltvtc2;
	}

	public void setFcltvtc2(String fcltvtc2) {
		this.fcltvtc2 = fcltvtc2;
	}
	

	public String getMrngsttus() {
		return mrngsttus;
	}

	public void setMrngsttus(String mrngsttus) {
		this.mrngsttus = mrngsttus;
	}

	public String getSvyType2() {
		return svyType2;
	}

	public void setSvyType2(String svyType2) {
		this.svyType2 = svyType2;
	}

	public String getOrginlDam() {
		return orginlDam;
	}

	public String getSideWall() {
		return sideWall;
	}

	public String getDwnspt() {
		return dwnspt;
	}

	public void setOrginlDam(String orginlDam) {
		this.orginlDam = orginlDam;
	}

	public void setSideWall(String sideWall) {
		this.sideWall = sideWall;
	}

	public void setDwnspt(String dwnspt) {
		this.dwnspt = dwnspt;
	}
}
