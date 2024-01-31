package or.sabang.sys.fck.mse.service;

import egovframework.com.cmm.ComDefaultVO;

public class FckMseStripLandVO  extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/* gid */
	private String gid;
	/* 일련번호 */
	private String sn;
	/* 조사정보 fid */
	private int fid;
	/* 공유방아이디 */
	private String id;
	/* 조사유형 */
	private String type;
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
	
	/* 위도 */
	private String svyLat;
	/* 경도 */
	private String svyLon;
	/* 위치좌표 */
	private String svyData;
	/* pnu 코드 */
	private String pnucode;
	
	
	/* 계류보전 시점좌표 경도 */
	private String bpx;
	/* 계류보전 시점좌표 위도 */
	private String bpy;
	/* 계류보전 종점좌표 경도 */
	private String epx;
	/* 계류보전 종점좌표 위도 */
	private String epy;

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
	/* 관리자 에러 체크 */
	private String errorCheck;
	
	/* 대상지ID */
	private String sldId;
	/* 소유구분 */
	private String owner;
	/* 법적제한사항 */
	private String lglLimit;
	/* 와이어신축계 */
	private String wireExt;
	/* 지중경사계 */
	private String licMeter;
	/* 지중경사계 채널 개수 */
	private String licCnt;
	/* 지중경사계 성능점검 개수 */
	private String licMeterCnt;
	/* 지중경사계 성능점검 */
	private String licMeterPerCheck;
	/* 지하수위계 */
	private String gwGauge;
	/* 강우계 */
	private String rainGauge;
	/* 구조물 변위계 */
	private String strcDpm;
	/* 지표 변위계 */
	private String surDpm;
	/* GPS 변위계 */
	private String gpsGauge;
	/* 게이트웨이 */
	private String gateway;
	/* 게이트웨이 Id*/
	private String gatewayId;
	/* 노드 */
	private String node;
	/* 지중경사계 높이 */
	private String licHg;
	/* 통신모뎀번호 */
	private String modemNum;
	/* 이동전화번호 */
	private String cellNum;
	/* 와이어신축계 유무 */
	private Boolean twEn;
	/* 지중경사계 유무 */
	private Boolean incEn;
	/* 지하수위계 유무 */
	private Boolean wlEn;
	/* 강우계 유무 */
	private Boolean rgEn;
	/* 구조물변위계 유무 */
	private Boolean tmEn;
	/* 지표변위계 유무 */
	private Boolean sdEn;
	/* GPS변위계 유무 */
	private Boolean gpsEn;
	/* 게이트웨이 유무 */
	private Boolean gatewayEn;
	/* 노드 유무 */
	private Boolean nodeEn;
	/* 대상지 계측장비 유형 */
	private String svyTag1;
	/* 대상지 주소 언어 타입 */
	private String addrLangType;
	
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
	public String getDmgFcltPhoto() {
		return dmgFcltPhoto;
	}
	public void setDmgFcltPhoto(String dmgFcltPhoto) {
		this.dmgFcltPhoto = dmgFcltPhoto;
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
	public String getErrorCheck() {
		return errorCheck;
	}
	public void setErrorCheck(String errorCheck) {
		this.errorCheck = errorCheck;
	}
	public String getSldId() {
		return sldId;
	}
	public void setSldId(String sldId) {
		this.sldId = sldId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getLglLimit() {
		return lglLimit;
	}
	public void setLglLimit(String lglLimit) {
		this.lglLimit = lglLimit;
	}
	public String getWireExt() {
		return wireExt;
	}
	public void setWireExt(String wireExt) {
		this.wireExt = wireExt;
	}
	public String getLicMeter() {
		return licMeter;
	}
	public void setLicMeter(String licMeter) {
		this.licMeter = licMeter;
	}
	public String getLicCnt() {
		return licCnt;
	}
	public void setLicCnt(String licCnt) {
		this.licCnt = licCnt;
	}
	public String getLicMeterCnt() {
		return licMeterCnt;
	}
	public void setLicMeterCnt(String licMeterCnt) {
		this.licMeterCnt = licMeterCnt;
	}
	public String getLicMeterPerCheck() {
		return licMeterPerCheck;
	}
	public void setLicMeterPerCheck(String licMeterPerCheck) {
		this.licMeterPerCheck = licMeterPerCheck;
	}
	public String getGwGauge() {
		return gwGauge;
	}
	public void setGwGauge(String gwGauge) {
		this.gwGauge = gwGauge;
	}
	public String getRainGauge() {
		return rainGauge;
	}
	public void setRainGauge(String rainGauge) {
		this.rainGauge = rainGauge;
	}
	public String getStrcDpm() {
		return strcDpm;
	}
	public void setStrcDpm(String strcDpm) {
		this.strcDpm = strcDpm;
	}
	public String getSurDpm() {
		return surDpm;
	}
	public void setSurDpm(String surDpm) {
		this.surDpm = surDpm;
	}
	public String getGpsGauge() {
		return gpsGauge;
	}
	public void setGpsGauge(String gpsGauge) {
		this.gpsGauge = gpsGauge;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getLicHg() {
		return licHg;
	}
	public void setLicHg(String licHg) {
		this.licHg = licHg;
	}
	public String getModemNum() {
		return modemNum;
	}
	public void setModemNum(String modemNum) {
		this.modemNum = modemNum;
	}
	public String getCellNum() {
		return cellNum;
	}
	public void setCellNum(String cellNum) {
		this.cellNum = cellNum;
	}
	public Boolean getTwEn() {
		return twEn;
	}
	public void setTwEn(Boolean twEn) {
		this.twEn = twEn;
	}
	public Boolean getIncEn() {
		return incEn;
	}
	public void setIncEn(Boolean incEn) {
		this.incEn = incEn;
	}
	public Boolean getWlEn() {
		return wlEn;
	}
	public void setWlEn(Boolean wlEn) {
		this.wlEn = wlEn;
	}
	public Boolean getRgEn() {
		return rgEn;
	}
	public void setRgEn(Boolean rgEn) {
		this.rgEn = rgEn;
	}
	public Boolean getTmEn() {
		return tmEn;
	}
	public void setTmEn(Boolean tmEn) {
		this.tmEn = tmEn;
	}
	public Boolean getSdEn() {
		return sdEn;
	}
	public void setSdEn(Boolean sdEn) {
		this.sdEn = sdEn;
	}
	public Boolean getGpsEn() {
		return gpsEn;
	}
	public void setGpsEn(Boolean gpsEn) {
		this.gpsEn = gpsEn;
	}
	public Boolean getGatewayEn() {
		return gatewayEn;
	}
	public void setGatewayEn(Boolean gatewayEn) {
		this.gatewayEn = gatewayEn;
	}
	public Boolean getNodeEn() {
		return nodeEn;
	}
	public void setNodeEn(Boolean nodeEn) {
		this.nodeEn = nodeEn;
	}
	public String getSvyTag1() {
		return svyTag1;
	}
	public void setSvyTag1(String svyTag1) {
		this.svyTag1 = svyTag1;
	}
	public String getAddrLangType() {
		return addrLangType;
	}
	public void setAddrLangType(String addrLangType) {
		this.addrLangType = addrLangType;
	}
	public String getSvyData() {
		return svyData;
	}
	public String getPnucode() {
		return pnucode;
	}
	public void setSvyData(String svyData) {
		this.svyData = svyData;
	}
	public void setPnucode(String pnucode) {
		this.pnucode = pnucode;
	}
}