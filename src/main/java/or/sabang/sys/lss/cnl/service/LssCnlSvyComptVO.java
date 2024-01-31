package or.sabang.sys.lss.cnl.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssCnlSvyComptVO  extends ComDefaultVO{

	private static final long serialVersionUID = 1L;
	
	private String mstNm;
	private String svyYear;
	private String svyMonth;
	private String loginId;
	/*
	 * gid 
	 */
	private String gid;
	/*
	 * 전자야장 고유번호
	 */
	private int fid;
	/*
	 * 공유방아이디
	 */
	private String mstId;
	/*
	 * 조사ID  
	 */
	private String svyId;
	/*
	 * 조사유형  
	 */
	private String svyType;
	/*
	 * 유형  
	 */
	private String type;
	/*
	 * 조사일자  
	 */
	private String svyDt;
	/*
	 * 관할1  
	 */
	private String region1;
	/*
	 * 관할2  
	 */
	private String region2;
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
	 * 순번
	 */
	private String sn;
	/*
	 * 국가지점번호
	 */
	private String nlIdxNo;
	/*
	 * 조사자
	 */
	private String svyUser;
	/*
	 * 점검자
	 */
	private String checkUser;
	/*
	 * 경도
	 */
	private String svyLat;
	/*
	 * 위도
	 */
	private String svyLon;
	/*
	 * 지정년도
	 */
	private String appnYear;
	/*
	 * 지정번호
	 */
	private String appnNo;
	/*
	 * 지정면적
	 */
	private String appnArea;
	/*
	 * 지정사유
	 */
	private String appnResn;
	/*
	 * 사업종류
	 */
	private String bizType;
	/*
	 * 시공년도
	 */
	private String cnstrYear;
	/*
	 * 적용공법코드
	 */
	private String applcEgnerMhdCd;
	/*
	 * 적용공법
	 */
	private String applcEgnerMhd;
	/*
	 * 시설물 상태
	 */
	private String fctSttus;
	/*
	 * 유역현황
	 */
	private String dgrSttus;
	/*
	 * 피해이력
	 */
	private String dmgeHist;
	/*
	 * 특이사항
	 */
	private String speclNote;
	/*
	 * 안정해석결과 해제평가
	 */
	private String stableIntrprtYn;
	/*
	 * 시뮬레이션결과 해제평가
	 */
	private String simlatnRsltYn;
	/*
	 * 종합의견1
	 */
	private String mstOpinion1;
	/*
	 * 종합의견2
	 */
	private String mstOpinion2;
	/*
	 * 해제여부
	 */
	private String cnlYn;
	/*
	 * 해제선정사유1
	 */
	private String cnlSlctRn1;
	/*
	 * 해제선정사유2
	 */
	private String cnlSlctRn2;
	/*
	 * 해제선정사유3
	 */
	private String cnlSlctRn3;
	/*
	 * 취약지역지정 당시현황
	 */
	private String weakAppnSttus;
	/*
	 * 취약지역지정 사업종
	 */
	private String weakAppnBsType;
	/*
	 * 취약지역지정 가능여부
	 */
	private String weakAppnPosYn;
	/*
	 * 취약지역지정 선정사유
	 */
	private String weakAppnSltnHy;
	/*
	 * 취약지역지정 구역설정
	 */
	private String weakAppnAreaSet;
	/*
	 * 취약지정지정 종합의견
	 */
	private String weakAppnMstOpn;
	/*
	 * 사업 및 유역현황
	 */
	private String bizNdDgrSttus;
	/*
	 * 피해이력 및 유역변화
	 */	
	private String dmgHistNdDgrChag;
	/*
	 * 산사태등급
	 */
	private String lndsldGrde;
	/*
	 * 평균경사
	 */
	private String slopeAvg;
	/*
	 * 임상도
	 */
	private String frtpType;
	/*
	 * 경급도
	 */
	private String dmclsType;
	/*
	 * 건기
	 */
	private String drySeason;
	/*
	 * 우기
	 */
	private String rainSeason;
	/*
	 * 의견1
	 */
	private String opinion1;
	/*
	 * 의견2
	 */
	private String opinion2;
	/*
	 * 의견3
	 */
	private String opinion3;
	/*
	 * 현황도
	 */
	private String sttusImg;
	/*
	 * 위치도
	 */
	private String lcMap;
	/*
	 * 등록일
	 */
	private String creatDt;
	/*
	 * 마지막 수정일
	 */
	private String lastUpdtPnttm;
	/*
	 * 1회유출토석류량
	 */
	private String oneDebrisFlow;
	/*
	 * 전체토석류량
	 */
	private String allDebrisFlow;
	/*
	 * 정지조건
	 */
	private String stopCnd;
	/*
	 * 가중치
	 */
	private String wghtVal;
	/*
	 * 안정해석점수
	 */
	private String stableIntrprtScore;
	/*
	 * 저감여부
	 */
	private String reducAt;
	
	private String minDt;
	private String maxDt;
	private String startDate;
	private String endDate;
	
	private String attr;
	
	/*
	 * 사진
	 */
	private String photo;
	/*
	 * 사진 URL1
	 */
	private String photoSrc1;
	/*
	 * 사진 URL2
	 */
	private String photoSrc2;
	/*
	 * 사진 URL3
	 */
	private String photoSrc3;
	/*
	 * 사진 URL4
	 */
	private String photoSrc4;	
	/*
	 * 사진
	 */
	private String photoTag;
	/*
	 * 사진태그배열
	 */
	private String photoTagList;
	/*
	 * 사진태그1
	 */
	private String photoTag1;
	/*
	 * 사진태그2
	 */
	private String photoTag2;
	/*
	 * 사진태그3
	 */
	private String photoTag3;
	/*
	 * 사진태그4
	 */
	private String photoTag4;
	/*
	 * 위치도 줌 레벨
	 */
	private String changedZoom;
	/*
	 * 위치도 인덱스
	 */
	private int locImgIdx;
	
	
	/* 2023-01-19 추가*/
	
	/*
	 * 사업시행 여부
	 */
	private String bizOpertnAT;	
	/*
	 * 재해발생여부(공통)
	 */
	private String dsstrOccrrncAt;
	/*
	 * 계류상태(토석류)
	 */
	private String mrngSttus;
	/*
	 * 사면상태(산사태)
	 */
	private String sSttus;
	/*
	 * 해제선정사유4
	 */
	private String cnlSlctRn4;
	/*
	 * 주민의견 및 기타 특이사항
	 */
	private String hbtOpnNdEtcMatter;
	/*
	 * 해제평가 사면계류상태1
	 */
	private String relisEvlsMrngSttus1;
	/*
	 * 해제평가 재해발생여부
	 */
	private String relisEvlDsstrOccrrncAt;
	/*
	 * 해제평가 사면계류상태2
	 */
	private String relisEvlsMrngSttus2;
	/*
	 * 해제요건 세부사항
	 */
	private String detailMatter;
	
	/*
	 * 공간정보(유출구)
	 */
	private String vnaraPntWkt;
	
	/*
	 * 공간정보(대피로)
	 */
	private String vnaraLneWkt;
	
	/*
	 * 공간정보(폴리곤)
	 */
	private String vnaraPlgnWkt;
	/*
	 * 공간정보(폴리곤타입)
	 */
	private String vnaraPlgn;
	/*
	 * 항공사진을 통한 주변 환경변화
	 */
	private String arlphoto;
	/*
	 * 항공사진을 통한 주변 환경변화 지정이전
	 */
	private String arlphoto1;
	/*
	 * 항공사진을 통한 주변 환경변화 지정이후
	 */
	private String arlphoto2;
	
	/*해제평가 : 산사태(재해발생여부, 사면상태, 안정해석결과), 토석류(재해발생여부, 계류상태, 시뮬레이션결과)*/
	/**
	 * 해제평가1
	 */
	private String cnlevl1;
	/**
	 * 해제평가2
	 */
	private String cnlevl2;
	/**
	 * 해제평가3
	 */
	private String cnlevl3;
	/*
	 * 결과도면
	 */
	private String rsltphoto;
	/*
	 * 결과도면(토석류, 시뮬레이션)
	 */
	private String rsltphoto1;
	/*
	 * 결과도면(산사태, 건기)
	 */
	private String rsltphoto2;
	/*
	 * 결과도면(산사태, 우기)
	 */
	private String rsltphoto3;
	
	/*
	 * 최종종합의견1
	 */
	private String finalMstOpinion1; 
	/*
	 * 최종종합의견2
	 */
	private String finalMstOpinion2; 
	/*
	 * 최종종합의견3
	 */
	private String finalMstOpinion3; 
	
	public String getMstNm() {
		return mstNm;
	}
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}
	public String getSvyYear() {
		return svyYear;
	}
	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}
	public String getSvyMonth() {
		return svyMonth;
	}
	public void setSvyMonth(String svyMonth) {
		this.svyMonth = svyMonth;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
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
	public String getSvyType() {
		return svyType;
	}
	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSvyDt() {
		return svyDt;
	}
	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
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
	public String getNlIdxNo() {
		return nlIdxNo;
	}
	public void setNlIdxNo(String nlIdxNo) {
		this.nlIdxNo = nlIdxNo;
	}
	public String getSvyUser() {
		return svyUser;
	}
	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
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
	public String getAppnYear() {
		return appnYear;
	}
	public void setAppnYear(String appnYear) {
		this.appnYear = appnYear;
	}
	public String getAppnNo() {
		return appnNo;
	}
	public void setAppnNo(String appnNo) {
		this.appnNo = appnNo;
	}
	public String getAppnArea() {
		return appnArea;
	}
	public void setAppnArea(String appnArea) {
		this.appnArea = appnArea;
	}
	public String getAppnResn() {
		return appnResn;
	}
	public void setAppnResn(String appnResn) {
		this.appnResn = appnResn;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getCnstrYear() {
		return cnstrYear;
	}
	public void setCnstrYear(String cnstrYear) {
		this.cnstrYear = cnstrYear;
	}
	public String getApplcEgnerMhd() {
		return applcEgnerMhd;
	}
	public void setApplcEgnerMhd(String applcEgnerMhd) {
		this.applcEgnerMhd = applcEgnerMhd;
	}
	public String getFctSttus() {
		return fctSttus;
	}
	public void setFctSttus(String fctSttus) {
		this.fctSttus = fctSttus;
	}
	public String getDgrSttus() {
		return dgrSttus;
	}
	public void setDgrSttus(String dgrSttus) {
		this.dgrSttus = dgrSttus;
	}
	public String getDmgeHist() {
		return dmgeHist;
	}
	public void setDmgeHist(String dmgeHist) {
		this.dmgeHist = dmgeHist;
	}
	public String getSpeclNote() {
		return speclNote;
	}
	public void setSpeclNote(String speclNote) {
		this.speclNote = speclNote;
	}
	public String getStableIntrprtYn() {
		return stableIntrprtYn;
	}
	public void setStableIntrprtYn(String stableIntrprtYn) {
		this.stableIntrprtYn = stableIntrprtYn;
	}
	public String getSimlatnRsltYn() {
		return simlatnRsltYn;
	}
	public void setSimlatnRsltYn(String simlatnRsltYn) {
		this.simlatnRsltYn = simlatnRsltYn;
	}
	public String getMstOpinion1() {
		return mstOpinion1;
	}
	public void setMstOpinion1(String mstOpinion1) {
		this.mstOpinion1 = mstOpinion1;
	}
	public String getMstOpinion2() {
		return mstOpinion2;
	}
	public void setMstOpinion2(String mstOpinion2) {
		this.mstOpinion2 = mstOpinion2;
	}
	public String getCnlYn() {
		return cnlYn;
	}
	public void setCnlYn(String cnlYn) {
		this.cnlYn = cnlYn;
	}
	public String getCnlSlctRn1() {
		return cnlSlctRn1;
	}
	public void setCnlSlctRn1(String cnlSlctRn1) {
		this.cnlSlctRn1 = cnlSlctRn1;
	}
	public String getCnlSlctRn2() {
		return cnlSlctRn2;
	}
	public void setCnlSlctRn2(String cnlSlctRn2) {
		this.cnlSlctRn2 = cnlSlctRn2;
	}
	public String getCnlSlctRn3() {
		return cnlSlctRn3;
	}
	public void setCnlSlctRn3(String cnlSlctRn3) {
		this.cnlSlctRn3 = cnlSlctRn3;
	}
	public String getWeakAppnSttus() {
		return weakAppnSttus;
	}
	public void setWeakAppnSttus(String weakAppnSttus) {
		this.weakAppnSttus = weakAppnSttus;
	}
	public String getWeakAppnBsType() {
		return weakAppnBsType;
	}
	public void setWeakAppnBsType(String weakAppnBsType) {
		this.weakAppnBsType = weakAppnBsType;
	}
	public String getWeakAppnPosYn() {
		return weakAppnPosYn;
	}
	public void setWeakAppnPosYn(String weakAppnPosYn) {
		this.weakAppnPosYn = weakAppnPosYn;
	}
	public String getWeakAppnSltnHy() {
		return weakAppnSltnHy;
	}
	public void setWeakAppnSltnHy(String weakAppnSltnHy) {
		this.weakAppnSltnHy = weakAppnSltnHy;
	}
	public String getWeakAppnAreaSet() {
		return weakAppnAreaSet;
	}
	public void setWeakAppnAreaSet(String weakAppnAreaSet) {
		this.weakAppnAreaSet = weakAppnAreaSet;
	}
	public String getWeakAppnMstOpn() {
		return weakAppnMstOpn;
	}
	public void setWeakAppnMstOpn(String weakAppnMstOpn) {
		this.weakAppnMstOpn = weakAppnMstOpn;
	}
	public String getBizNdDgrSttus() {
		return bizNdDgrSttus;
	}
	public void setBizNdDgrSttus(String bizNdDgrSttus) {
		this.bizNdDgrSttus = bizNdDgrSttus;
	}
	public String getDmgHistNdDgrChag() {
		return dmgHistNdDgrChag;
	}
	public void setDmgHistNdDgrChag(String dmgHistNdDgrChag) {
		this.dmgHistNdDgrChag = dmgHistNdDgrChag;
	}
	public String getLndsldGrde() {
		return lndsldGrde;
	}
	public void setLndsldGrde(String lndsldGrde) {
		this.lndsldGrde = lndsldGrde;
	}
	public String getSlopeAvg() {
		return slopeAvg;
	}
	public void setSlopeAvg(String slopeAvg) {
		this.slopeAvg = slopeAvg;
	}
	public String getFrtpType() {
		return frtpType;
	}
	public void setFrtpType(String frtpType) {
		this.frtpType = frtpType;
	}
	public String getDmclsType() {
		return dmclsType;
	}
	public void setDmclsType(String dmclsType) {
		this.dmclsType = dmclsType;
	}
	public String getDrySeason() {
		return drySeason;
	}
	public void setDrySeason(String drySeason) {
		this.drySeason = drySeason;
	}
	public String getRainSeason() {
		return rainSeason;
	}
	public void setRainSeason(String rainSeason) {
		this.rainSeason = rainSeason;
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
	public String getSttusImg() {
		return sttusImg;
	}
	public void setSttusImg(String sttusImg) {
		this.sttusImg = sttusImg;
	}
	public String getLcMap() {
		return lcMap;
	}
	public void setLcMap(String lcMap) {
		this.lcMap = lcMap;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhotoTag() {
		return photoTag;
	}
	public void setPhotoTag(String photoTag) {
		this.photoTag = photoTag;
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
	public String getOneDebrisFlow() {
		return oneDebrisFlow;
	}
	public void setOneDebrisFlow(String oneDebrisFlow) {
		this.oneDebrisFlow = oneDebrisFlow;
	}
	public String getAllDebrisFlow() {
		return allDebrisFlow;
	}
	public void setAllDebrisFlow(String allDebrisFlow) {
		this.allDebrisFlow = allDebrisFlow;
	}
	public String getStopCnd() {
		return stopCnd;
	}
	public void setStopCnd(String stopCnd) {
		this.stopCnd = stopCnd;
	}
	public String getWghtVal() {
		return wghtVal;
	}
	public void setWghtVal(String wghtVal) {
		this.wghtVal = wghtVal;
	}
	public String getStableIntrprtScore() {
		return stableIntrprtScore;
	}
	public void setStableIntrprtScore(String stableIntrprtScore) {
		this.stableIntrprtScore = stableIntrprtScore;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
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
	public String getPhotoTagList() {
		return photoTagList;
	}
	public void setPhotoTagList(String photoTagList) {
		this.photoTagList = photoTagList;
	}
	public String getReducAt() {
		return reducAt;
	}
	public void setReducAt(String reducAt) {
		this.reducAt = reducAt;
	}
	public String getMinDt() {
		return minDt;
	}
	public void setMinDt(String minDt) {
		this.minDt = minDt;
	}
	public String getMaxDt() {
		return maxDt;
	}
	public void setMaxDt(String maxDt) {
		this.maxDt = maxDt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getBizOpertnAT() {
		return bizOpertnAT;
	}
	public void setBizOpertnAT(String bizOpertnAT) {
		this.bizOpertnAT = bizOpertnAT;
	}
	public String getDsstrOccrrncAt() {
		return dsstrOccrrncAt;
	}
	public void setDsstrOccrrncAt(String dsstrOccrrncAt) {
		this.dsstrOccrrncAt = dsstrOccrrncAt;
	}
	public String getMrngSttus() {
		return mrngSttus;
	}
	public void setMrngSttus(String mrngSttus) {
		this.mrngSttus = mrngSttus;
	}
	public String getsSttus() {
		return sSttus;
	}
	public void setsSttus(String sSttus) {
		this.sSttus = sSttus;
	}
	public String getCnlSlctRn4() {
		return cnlSlctRn4;
	}
	public void setCnlSlctRn4(String cnlSlctRn4) {
		this.cnlSlctRn4 = cnlSlctRn4;
	}
	public String getHbtOpnNdEtcMatter() {
		return hbtOpnNdEtcMatter;
	}
	public void setHbtOpnNdEtcMatter(String hbtOpnNdEtcMatter) {
		this.hbtOpnNdEtcMatter = hbtOpnNdEtcMatter;
	}
	public String getRelisEvlsMrngSttus1() {
		return relisEvlsMrngSttus1;
	}
	public void setRelisEvlsMrngSttus1(String relisEvlsMrngSttus1) {
		this.relisEvlsMrngSttus1 = relisEvlsMrngSttus1;
	}
	public String getRelisEvlsMrngSttus2() {
		return relisEvlsMrngSttus2;
	}
	public void setRelisEvlsMrngSttus2(String relisEvlsMrngSttus2) {
		this.relisEvlsMrngSttus2 = relisEvlsMrngSttus2;
	}
	public String getDetailMatter() {
		return detailMatter;
	}
	public void setDetailMatter(String detailMatter) {
		this.detailMatter = detailMatter;
	}
	public String getRelisEvlDsstrOccrrncAt() {
		return relisEvlDsstrOccrrncAt;
	}
	public void setRelisEvlDsstrOccrrncAt(String relisEvlDsstrOccrrncAt) {
		this.relisEvlDsstrOccrrncAt = relisEvlDsstrOccrrncAt;
	}
	public String getVnaraPntWkt() {
		return vnaraPntWkt;
	}
	public void setVnaraPntWkt(String vnaraPntWkt) {
		this.vnaraPntWkt = vnaraPntWkt;
	}
	public String getVnaraLneWkt() {
		return vnaraLneWkt;
	}
	public void setVnaraLneWkt(String vnaraLneWkt) {
		this.vnaraLneWkt = vnaraLneWkt;
	}
	public String getVnaraPlgnWkt() {
		return vnaraPlgnWkt;
	}
	public void setVnaraPlgnWkt(String vnaraPlgnWkt) {
		this.vnaraPlgnWkt = vnaraPlgnWkt;
	}
	public String getVnaraPlgn() {
		return vnaraPlgn;
	}
	public void setVnaraPlgn(String vnaraPlgn) {
		this.vnaraPlgn = vnaraPlgn;
	}
	public String getArlphoto() {
		return arlphoto;
	}
	public void setArlphoto(String arlphoto) {
		this.arlphoto = arlphoto;
	}
	public String getArlphoto1() {
		return arlphoto1;
	}
	public void setArlphoto1(String arlphoto1) {
		this.arlphoto1 = arlphoto1;
	}
	public String getArlphoto2() {
		return arlphoto2;
	}
	public void setArlphoto2(String arlphoto2) {
		this.arlphoto2 = arlphoto2;
	}
	public String getCnlevl1() {
		return cnlevl1;
	}
	public String getCnlevl2() {
		return cnlevl2;
	}
	public String getCnlevl3() {
		return cnlevl3;
	}
	public void setCnlevl1(String cnlevl1) {
		this.cnlevl1 = cnlevl1;
	}
	public void setCnlevl2(String cnlevl2) {
		this.cnlevl2 = cnlevl2;
	}
	public void setCnlevl3(String cnlevl3) {
		this.cnlevl3 = cnlevl3;
	}
	public String getRsltphoto() {
		return rsltphoto;
	}
	public void setRsltphoto(String rsltphoto) {
		this.rsltphoto = rsltphoto;
	}
	public String getRsltphoto1() {
		return rsltphoto1;
	}
	public String getRsltphoto2() {
		return rsltphoto2;
	}
	public String getRsltphoto3() {
		return rsltphoto3;
	}
	public void setRsltphoto1(String rsltphoto1) {
		this.rsltphoto1 = rsltphoto1;
	}
	public void setRsltphoto2(String rsltphoto2) {
		this.rsltphoto2 = rsltphoto2;
	}
	public void setRsltphoto3(String rsltphoto3) {
		this.rsltphoto3 = rsltphoto3;
	}
	public String getApplcEgnerMhdCd() {
		return applcEgnerMhdCd;
	}
	public void setApplcEgnerMhdCd(String applcEgnerMhdCd) {
		this.applcEgnerMhdCd = applcEgnerMhdCd;
	}
	public String getFinalMstOpinion1() {
		return finalMstOpinion1;
	}
	public String getFinalMstOpinion2() {
		return finalMstOpinion2;
	}
	public String getFinalMstOpinion3() {
		return finalMstOpinion3;
	}
	public void setFinalMstOpinion1(String finalMstOpinion1) {
		this.finalMstOpinion1 = finalMstOpinion1;
	}
	public void setFinalMstOpinion2(String finalMstOpinion2) {
		this.finalMstOpinion2 = finalMstOpinion2;
	}
	public void setFinalMstOpinion3(String finalMstOpinion3) {
		this.finalMstOpinion3 = finalMstOpinion3;
	}
}