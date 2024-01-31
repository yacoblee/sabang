package or.sabang.sys.fck.pcs.service;

import egovframework.com.cmm.ComDefaultVO;

public class FckPcsComptVOBak  extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/* gid */
	private String gid;
	/* 일련번호 */
	private String sn;
	/* 조사정보 fid */
	private int fid;
	/* 공유방아이디 */
	private String mstId;
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
	/* 조사자 */
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
	/* 사방댐 종류,시설물 종류 */
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
	 
	/* 산지사방 시설제원 높이 */
	private String fcltHg;
	/* 산지사방 시설제원 평균경사 */
	private String fcltSlp;
	
	/* 해안사방 시설제원 종류 */
	private String fcltKnd;
	/* 해안사방 시설제원 주요구조물 */
	private String fcltMainStrctu;
	/* 해안사방 시설점검 가로 */
	private String fcltHrz;
	/* 해안사방 시설점검 세로 */
	private String fcltVtcl;
	/* 해안사방 시설점검 해안거리 */
	private String fcltCstDstnc;
	
	/* 위도 */
	private String svyLat;
	/* 경도 */
	private String svyLon;
	/* 계류보전 시점좌표 경도 */
	private String bpx;
	/* 계류보전 시점좌표 위도 */
	private String bpy;
	/* 계류보전 종점좌표 경도 */
	private String epx;
	/* 계류보전 종점좌표 위도 */
	private String epy;
	
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
	
	/* 해안방재림 주요구조물 측정값  */
	private String cstPrvnfrstStrctuVal;
	/* 해안방재림 주요구조물 판정값 */
	private String cstPrvnfrstStrctuJdgVal;
	/* 해안방재림 판정값 */
	private String cstPrvnfrstJdgVal;
	/* 해안침식방지 주요구조물 측정값 */
	private String cstRdcPrvnStrctuVal;
	/* 해안침식방지 주요구조물 판정값 */
	private String cstRdcPrvnStrctuJdgVal;
	/* 해안침식방지 판정값 */
	private String cstRdcPrvnJdgVal;
	
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
	
	/* 사방댐 현황도 */
	private String sttusPrnt;
	/* 사방댐 현황도 반수면 */
	private String sttusPrnt1;
	/* 사방댐 현황도 대수면 */
	private String sttusPrnt2;
	/* 사방댐 현황도 본체 */
	private String sttusPrnt3;
	/* 사방댐 현황도 좌안측벽 */
	private String sttusPrnt4;
	/* 사방댐 현황도 우안측벽 */
	private String sttusPrnt5;
	/* 사방댐 피해발생현황 */
	private String dmgSttus;
	/* 사방댐 피해발생현황사진 */
	private String dmgSttusPhoto;
	
	/* 계류보전,산지사방 대상지 */
	private String trglnd;
	/* 계류보전 피해시설 */
	private String dmgFclt;
	/* 계류보전, 대상지 위치 및 주요시설 */
	private String trglndPhoto;
	/* 계류보전, 피해시설 위치 및 피해현황 */
	private String dmgFcltPhoto;
	
	/* 위치도 */
	private String lcmap;
	
	/* 사진 */
	private String photo;
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
	
	
	/* 등록일자 */
	private String creatDt;
	/* 수정일자 */
	private String lastUpdtPnttm;
	
	/* 위경도 */
	private String svyLatLon;
	
	/* 사방댐 본댐코드 */
	private String orginlDamCd;
	/* 사방댐 측벽코드 */
	private String sideWallCd;
	/* 사방댐 물받이코드 */
	private String dwnsptCd;
	
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
	
	/* 해안사방 해안방재림 코드 */
	private String cstPrvnfrstStrctuCd;
	/* 해안사방 해안침식 코드 */
	private String cstRdcPrvnStrctuCd;
	
	/* 본번  */
	private String svyBonbun;
	/* 부번 */
	private String svyBunbun;
	
	/* 주요시설_특이사항 */
	private String mainFcltSlNt;
	/* 부대시설_특이사항 */
	private String sbrsSlNt;
	/* 기타_특이사항 */
	private String etcSlNt;
	
	/* 연도 */
	private String svyYear;
	/* 공유방 명칭 */
	private String mstNm;
	/* 조사일자 - 월 */
	private String svyMonth;
	/* 위치도 줌 레벨 */
	private String changedZoom;
	/* 위치도 인덱스 */
	private int locImgIdx;
	/* 조사정보 ATTR */
	private String attr;
	/* 현장사진 개수 */
	private int photoLen;
	/* 대상지 개수 */
	private int trgLndLen;
	/* 피해발생현황 사진개수 */
	private int dmgSttusPhotoLen;
	/* 피해시설 위치 및 피해현황 사진개수 */
	private int dmgFcltPhotoLen;
	
	/* 위치도 지도 타입 */
	private String mapType;
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getMstId() {
		return mstId;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
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
	public String getFcltHg() {
		return fcltHg;
	}
	public void setFcltHg(String fcltHg) {
		this.fcltHg = fcltHg;
	}
	public String getFcltSlp() {
		return fcltSlp;
	}
	public void setFcltSlp(String fcltSlp) {
		this.fcltSlp = fcltSlp;
	}
	public String getFcltKnd() {
		return fcltKnd;
	}
	public void setFcltKnd(String fcltKnd) {
		this.fcltKnd = fcltKnd;
	}
	public String getFcltMainStrctu() {
		return fcltMainStrctu;
	}
	public void setFcltMainStrctu(String fcltMainStrctu) {
		this.fcltMainStrctu = fcltMainStrctu;
	}
	public String getFcltHrz() {
		return fcltHrz;
	}
	public void setFcltHrz(String fcltHrz) {
		this.fcltHrz = fcltHrz;
	}
	public String getFcltVtcl() {
		return fcltVtcl;
	}
	public void setFcltVtcl(String fcltVtcl) {
		this.fcltVtcl = fcltVtcl;
	}
	public String getFcltCstDstnc() {
		return fcltCstDstnc;
	}
	public void setFcltCstDstnc(String fcltCstDstnc) {
		this.fcltCstDstnc = fcltCstDstnc;
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
	public String getEpx() {
		return epx;
	}
	public void setEpx(String epx) {
		this.epx = epx;
	}
	public String getEpy() {
		return epy;
	}
	public void setEpy(String epy) {
		this.epy = epy;
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
	public String getCstPrvnfrstStrctuVal() {
		return cstPrvnfrstStrctuVal;
	}
	public void setCstPrvnfrstStrctuVal(String cstPrvnfrstStrctuVal) {
		this.cstPrvnfrstStrctuVal = cstPrvnfrstStrctuVal;
	}
	public String getCstPrvnfrstStrctuJdgVal() {
		return cstPrvnfrstStrctuJdgVal;
	}
	public void setCstPrvnfrstStrctuJdgVal(String cstPrvnfrstStrctuJdgVal) {
		this.cstPrvnfrstStrctuJdgVal = cstPrvnfrstStrctuJdgVal;
	}
	public String getCstPrvnfrstJdgVal() {
		return cstPrvnfrstJdgVal;
	}
	public void setCstPrvnfrstJdgVal(String cstPrvnfrstJdgVal) {
		this.cstPrvnfrstJdgVal = cstPrvnfrstJdgVal;
	}
	public String getCstRdcPrvnStrctuVal() {
		return cstRdcPrvnStrctuVal;
	}
	public void setCstRdcPrvnStrctuVal(String cstRdcPrvnStrctuVal) {
		this.cstRdcPrvnStrctuVal = cstRdcPrvnStrctuVal;
	}
	public String getCstRdcPrvnStrctuJdgVal() {
		return cstRdcPrvnStrctuJdgVal;
	}
	public void setCstRdcPrvnStrctuJdgVal(String cstRdcPrvnStrctuJdgVal) {
		this.cstRdcPrvnStrctuJdgVal = cstRdcPrvnStrctuJdgVal;
	}
	public String getCstRdcPrvnJdgVal() {
		return cstRdcPrvnJdgVal;
	}
	public void setCstRdcPrvnJdgVal(String cstRdcPrvnJdgVal) {
		this.cstRdcPrvnJdgVal = cstRdcPrvnJdgVal;
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
	public String getSttusPrnt() {
		return sttusPrnt;
	}
	public void setSttusPrnt(String sttusPrnt) {
		this.sttusPrnt = sttusPrnt;
	}
	public String getSttusPrnt1() {
		return sttusPrnt1;
	}
	public void setSttusPrnt1(String sttusPrnt1) {
		this.sttusPrnt1 = sttusPrnt1;
	}
	public String getSttusPrnt2() {
		return sttusPrnt2;
	}
	public void setSttusPrnt2(String sttusPrnt2) {
		this.sttusPrnt2 = sttusPrnt2;
	}
	public String getSttusPrnt3() {
		return sttusPrnt3;
	}
	public void setSttusPrnt3(String sttusPrnt3) {
		this.sttusPrnt3 = sttusPrnt3;
	}
	public String getSttusPrnt4() {
		return sttusPrnt4;
	}
	public void setSttusPrnt4(String sttusPrnt4) {
		this.sttusPrnt4 = sttusPrnt4;
	}
	public String getSttusPrnt5() {
		return sttusPrnt5;
	}
	public void setSttusPrnt5(String sttusPrnt5) {
		this.sttusPrnt5 = sttusPrnt5;
	}
	public String getDmgSttus() {
		return dmgSttus;
	}
	public void setDmgSttus(String dmgSttus) {
		this.dmgSttus = dmgSttus;
	}
	public String getDmgSttusPhoto() {
		return dmgSttusPhoto;
	}
	public void setDmgSttusPhoto(String dmgSttusPhoto) {
		this.dmgSttusPhoto = dmgSttusPhoto;
	}
	public String getTrglnd() {
		return trglnd;
	}
	public void setTrglnd(String trglnd) {
		this.trglnd = trglnd;
	}
	public String getDmgFcltPhoto() {
		return dmgFcltPhoto;
	}
	public void setDmgFcltPhoto(String dmgFcltPhoto) {
		this.dmgFcltPhoto = dmgFcltPhoto;
	}
	public String getDmgFclt() {
		return dmgFclt;
	}
	public void setDmgFclt(String dmgFclt) {
		this.dmgFclt = dmgFclt;
	}
	public String getTrglndPhoto() {
		return trglndPhoto;
	}
	public void setTrglndPhoto(String trglndPhoto) {
		this.trglndPhoto = trglndPhoto;
	}
	public String getLcmap() {
		return lcmap;
	}
	public void setLcmap(String lcmap) {
		this.lcmap = lcmap;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhotoSrc1() {
		return photoSrc1;
	}
	public void setPhotoSrc1(String photoSrc1) {
		this.photoSrc1 = photoSrc1;
	}
	public String getPhotoSrc2() {
		return photoSrc2;
	}
	public void setPhotoSrc2(String photoSrc2) {
		this.photoSrc2 = photoSrc2;
	}
	public String getPhotoSrc3() {
		return photoSrc3;
	}
	public void setPhotoSrc3(String photoSrc3) {
		this.photoSrc3 = photoSrc3;
	}
	public String getPhotoSrc4() {
		return photoSrc4;
	}
	public void setPhotoSrc4(String photoSrc4) {
		this.photoSrc4 = photoSrc4;
	}
	public String getPhotoSrc5() {
		return photoSrc5;
	}
	public void setPhotoSrc5(String photoSrc5) {
		this.photoSrc5 = photoSrc5;
	}
	public String getPhotoSrc6() {
		return photoSrc6;
	}
	public void setPhotoSrc6(String photoSrc6) {
		this.photoSrc6 = photoSrc6;
	}
	public String getPhotoTag() {
		return photoTag;
	}
	public void setPhotoTag(String photoTag) {
		this.photoTag = photoTag;
	}
	public String getPhotoTagList() {
		return photoTagList;
	}
	public void setPhotoTagList(String photoTagList) {
		this.photoTagList = photoTagList;
	}
	public String getPhotoTag1() {
		return photoTag1;
	}
	public void setPhotoTag1(String photoTag1) {
		this.photoTag1 = photoTag1;
	}
	public String getPhotoTag2() {
		return photoTag2;
	}
	public void setPhotoTag2(String photoTag2) {
		this.photoTag2 = photoTag2;
	}
	public String getPhotoTag3() {
		return photoTag3;
	}
	public void setPhotoTag3(String photoTag3) {
		this.photoTag3 = photoTag3;
	}
	public String getPhotoTag4() {
		return photoTag4;
	}
	public void setPhotoTag4(String photoTag4) {
		this.photoTag4 = photoTag4;
	}
	public String getPhotoTag5() {
		return photoTag5;
	}
	public void setPhotoTag5(String photoTag5) {
		this.photoTag5 = photoTag5;
	}
	public String getPhotoTag6() {
		return photoTag6;
	}
	public void setPhotoTag6(String photoTag6) {
		this.photoTag6 = photoTag6;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getSvyLatLon() {
		return svyLatLon;
	}
	public void setSvyLatLon(String svyLatLon) {
		this.svyLatLon = svyLatLon;
	}
	public String getOrginlDamCd() {
		return orginlDamCd;
	}
	public void setOrginlDamCd(String orginlDamCd) {
		this.orginlDamCd = orginlDamCd;
	}
	public String getSideWallCd() {
		return sideWallCd;
	}
	public void setSideWallCd(String sideWallCd) {
		this.sideWallCd = sideWallCd;
	}
	public String getDwnsptCd() {
		return dwnsptCd;
	}
	public void setDwnsptCd(String dwnsptCd) {
		this.dwnsptCd = dwnsptCd;
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
	public String getCstPrvnfrstStrctuCd() {
		return cstPrvnfrstStrctuCd;
	}
	public void setCstPrvnfrstStrctuCd(String cstPrvnfrstStrctuCd) {
		this.cstPrvnfrstStrctuCd = cstPrvnfrstStrctuCd;
	}
	public String getCstRdcPrvnStrctuCd() {
		return cstRdcPrvnStrctuCd;
	}
	public void setCstRdcPrvnStrctuCd(String cstRdcPrvnStrctuCd) {
		this.cstRdcPrvnStrctuCd = cstRdcPrvnStrctuCd;
	}
	public String getSvyBonbun() {
		return svyBonbun;
	}
	public void setSvyBonbun(String svyBonbun) {
		this.svyBonbun = svyBonbun;
	}
	public String getSvyBunbun() {
		return svyBunbun;
	}
	public void setSvyBunbun(String svyBunbun) {
		this.svyBunbun = svyBunbun;
	}
	public String getMainFcltSlNt() {
		return mainFcltSlNt;
	}
	public void setMainFcltSlNt(String mainFcltSlNt) {
		this.mainFcltSlNt = mainFcltSlNt;
	}
	public String getSbrsSlNt() {
		return sbrsSlNt;
	}
	public void setSbrsSlNt(String sbrsSlNt) {
		this.sbrsSlNt = sbrsSlNt;
	}
	public String getEtcSlNt() {
		return etcSlNt;
	}
	public void setEtcSlNt(String etcSlNt) {
		this.etcSlNt = etcSlNt;
	}
	public String getSvyYear() {
		return svyYear;
	}
	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}
	public String getMstNm() {
		return mstNm;
	}
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}
	public String getSvyMonth() {
		return svyMonth;
	}
	public void setSvyMonth(String svyMonth) {
		this.svyMonth = svyMonth;
	}
	public String getChangedZoom() {
		return changedZoom;
	}
	public void setChangedZoom(String changedZoom) {
		this.changedZoom = changedZoom;
	}
	public int getLocImgIdx() {
		return locImgIdx;
	}
	public void setLocImgIdx(int locImgIdx) {
		this.locImgIdx = locImgIdx;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public int getPhotoLen() {
		return photoLen;
	}
	public void setPhotoLen(int photoLen) {
		this.photoLen = photoLen;
	}
	public int getTrgLndLen() {
		return trgLndLen;
	}
	public void setTrgLndLen(int trgLndLen) {
		this.trgLndLen = trgLndLen;
	}
	public int getDmgSttusPhotoLen() {
		return dmgSttusPhotoLen;
	}
	public void setDmgSttusPhotoLen(int dmgSttusPhotoLen) {
		this.dmgSttusPhotoLen = dmgSttusPhotoLen;
	}
	public int getDmgFcltPhotoLen() {
		return dmgFcltPhotoLen;
	}
	public void setDmgFcltPhotoLen(int dmgFcltPhotoLen) {
		this.dmgFcltPhotoLen = dmgFcltPhotoLen;
	}
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	
}