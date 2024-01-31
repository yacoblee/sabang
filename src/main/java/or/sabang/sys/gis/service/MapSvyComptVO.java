package or.sabang.sys.gis.service;

import egovframework.com.cmm.ComDefaultVO;

public class MapSvyComptVO  extends ComDefaultVO{

	private static final long serialVersionUID = 1L;
	
	/*
	 * gid 
	 */
	private String gid;
	/*
	 * 공유방아이디
	 */
	private String mstId;
	/*
	 * 공유방이름
	 */
	private String mstNm;
	/*
	 * 조사연도
	 */
	private String svyYear;
	/*
	 * 조사ID
	 */
	private String svyId;
	/*
	 * 조사자
	 */
	private String svyUser;
	/*
	 * 작성자ID
	 */
	private String loginId;
	/*
	 * 조사유형
	 */
	private String svyType;
	/*
	 * 관할1
	 */
	private String svyRegion1;
	/*
	 * 관할2
	 */
	private String svyRegion2;
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
	 * 위도
	 */
	private String svyLat;
	/*
	 * 경도
	 */
	private String svyLon;
	/*
	 * 조사일
	 */
	private String svyDt;
	
	/*
	 * 조사일
	 */
	private String svyDtValue;
	/*
	 * 보호대상 측정값
	 */
	private String saftyVal;
	/*
	 * 보호대상 점수
	 */
	private String saftyScore;
	/*
	 * 경사길이 측정값
	 */
	private String sLenVal;
	/*
	 * 경사길이 점수
	 */
	private String sLenScore;
	/*
	 * 경사도 측정값
	 */
	private String slopeVal;
	/*
	 * 경사도 점수
	 */
	private String slopeScore;
	/*
	 * 사면형 측정값
	 */
	private String sFormVal;
	/*
	 * 사면형 점수
	 */
	private String sFormScore;
	/*
	 * 임상 측정값
	 */
	private String frstFrVal;
	/*
	 * 임상 점수
	 */
	private String frstFrScore;
	/*
	 * 모암 측정값
	 */
	private String prntRckVal;
	/*
	 * 모암 점수
	 */
	private String prntRckScore;
	/*
	 * 총계류길이 측정값(total torrent length)
	 */
	private String tlTrntLtVal;
	/*
	 * 총계류길이 점수
	 */
	private String tlTrntLtScore;
	/*
	 * 계류평균경사 측정값(torrent Average slope)
	 */
	private String trntAvgSlpVal;
	/*
	 * 계류평균경사 점수
	 */
	private String trntAvgSlpScore;
	/*
	 * 황폐발생원 측정값(devastation occurrence cause)
	 */
	private String devOcCauseVal;
	/*
	 * 황폐발생원 점수
	 */
	private String devOcCauseScore;
	/*
	 * 집수면적 측정값(water collecting area)
	 */
	private String wclctAreaVal;
	/*
	 * 집수면적 점수
	 */
	private String wclctAreaScore;
	/*
	 * 전석분포비율 측정값(boulder distribution rate)
	 */
	private String distBmdsbRateVal;
	/*
	 * 전석분포비율 점수
	 */
	private String distBmdsbRateScore;
	/*
	 * 점수계
	 */
	private String svySm;
	/*
	 * 주요위험성
	 */
	private String mainRisk;
	/*
	 * 조사자 의견
	 */
	private String opinion;
	/*
	 * 필요성
	 */
	private String ncssty;
	/*
	 * 등록일자
	 */
	private String creatDt;
	/*
	 * 수정일자
	 */
	private String lastUpdtPnttm;
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
	 * 시점좌표 
	 */
	private String bp;
	/*
	 * 종점좌표 
	 */
	private String ep;
	/*
	 * 위험인자 측정값
	 */
	private String rskFactorVal;
	/*
	 * 위험인자 점수
	 */
	private String rskFactorScore;
	private String[] rskFactors;
	/*
	 * 위치도
	 */
	private String lcmap;
	/*
	 * 조사일자 - 월
	 */
	private String svyMonth;
	
	/*
	 * 시점좌표_경도
	 */
	private String bpx;
	/*
	 * 시점좌표_위도
	 */
	private String bpy;
	
	/*
	 * 시점좌표_경도
	 */
	private String epx;
	/*
	 * 시점좌표_위도
	 */
	private String epy;
	
	/*
	 * 위치도 줌 레벨
	 */
	private String changedZoom;
	
	/*
	 * 위치도 인덱스
	 */
	private int locImgIdx;
	
	private String minDt;
	private String maxDt;
	private String startDate;
	private String endDate;
	/*
	 * 조사정보 ATTR
	 */
	private String attr;
	
	/*
	 * 조사정보 fid
	 */
	private int fid;
	/*
	 * 위치도 지도 타입
	 */
	private String mapType;
	
	/**
	 * 최종판정등급
	 */
	private String lastgrd;
	/**
	 * 
	 * 계류상태
	 */
	private String mrngsttus;
	
	public String getSvyYear() {
		return svyYear;
	}
	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}
	public String getMstId() {
		return mstId;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}
	public String getMstNm() {
		return mstNm;
	}
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}
	public String getSvyId() {
		return svyId;
	}
	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}
	public String getSvyUser() {
		return svyUser;
	}
	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
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
	public String getSvyRegion1() {
		return svyRegion1;
	}
	public void setSvyRegion1(String svyRegion1) {
		this.svyRegion1 = svyRegion1;
	}
	public String getSvyRegion2() {
		return svyRegion2;
	}
	public void setSvyRegion2(String svyRegion2) {
		this.svyRegion2 = svyRegion2;
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
	public String getSvyDt() {
		return svyDt;
	}
	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
	}
	public String getSaftyVal() {
		return saftyVal;
	}
	public void setSaftyVal(String saftyVal) {
		this.saftyVal = saftyVal;
	}
	public String getSaftyScore() {
		return saftyScore;
	}
	public void setSaftyScore(String saftyScore) {
		this.saftyScore = saftyScore;
	}
	public String getsLenVal() {
		return sLenVal;
	}
	public void setsLenVal(String sLenVal) {
		this.sLenVal = sLenVal;
	}
	public String getsLenScore() {
		return sLenScore;
	}
	public void setsLenScore(String sLenScore) {
		this.sLenScore = sLenScore;
	}
	public String getSlopeVal() {
		return slopeVal;
	}
	public void setSlopeVal(String slopeVal) {
		this.slopeVal = slopeVal;
	}
	public String getSlopeScore() {
		return slopeScore;
	}
	public void setSlopeScore(String slopeScore) {
		this.slopeScore = slopeScore;
	}
	public String getsFormVal() {
		return sFormVal;
	}
	public void setsFormVal(String sFormVal) {
		this.sFormVal = sFormVal;
	}
	public String getsFormScore() {
		return sFormScore;
	}
	public void setsFormScore(String sFormScore) {
		this.sFormScore = sFormScore;
	}
	public String getFrstFrVal() {
		return frstFrVal;
	}
	public void setFrstFrVal(String frstFrVal) {
		this.frstFrVal = frstFrVal;
	}
	public String getFrstFrScore() {
		return frstFrScore;
	}
	public void setFrstFrScore(String frstFrScore) {
		this.frstFrScore = frstFrScore;
	}
	public String getPrntRckVal() {
		return prntRckVal;
	}
	public void setPrntRckVal(String prntRckVal) {
		this.prntRckVal = prntRckVal;
	}
	public String getPrntRckScore() {
		return prntRckScore;
	}
	public void setPrntRckScore(String prntRckScore) {
		this.prntRckScore = prntRckScore;
	}
	public String getTlTrntLtVal() {
		return tlTrntLtVal;
	}
	public void setTlTrntLtVal(String tlTrntLtVal) {
		this.tlTrntLtVal = tlTrntLtVal;
	}
	public String getTlTrntLtScore() {
		return tlTrntLtScore;
	}
	public void setTlTrntLtScore(String tlTrntLtScore) {
		this.tlTrntLtScore = tlTrntLtScore;
	}
	public String getTrntAvgSlpVal() {
		return trntAvgSlpVal;
	}
	public void setTrntAvgSlpVal(String trntAvgSlpVal) {
		this.trntAvgSlpVal = trntAvgSlpVal;
	}
	public String getTrntAvgSlpScore() {
		return trntAvgSlpScore;
	}
	public void setTrntAvgSlpScore(String trntAvgSlpScore) {
		this.trntAvgSlpScore = trntAvgSlpScore;
	}
	public String getDevOcCauseVal() {
		return devOcCauseVal;
	}
	public void setDevOcCauseVal(String devOcCauseVal) {
		this.devOcCauseVal = devOcCauseVal;
	}
	public String getDevOcCauseScore() {
		return devOcCauseScore;
	}
	public void setDevOcCauseScore(String devOcCauseScore) {
		this.devOcCauseScore = devOcCauseScore;
	}
	public String getWclctAreaVal() {
		return wclctAreaVal;
	}
	public void setWclctAreaVal(String wclctAreaVal) {
		this.wclctAreaVal = wclctAreaVal;
	}
	public String getWclctAreaScore() {
		return wclctAreaScore;
	}
	public void setWclctAreaScore(String wclctAreaScore) {
		this.wclctAreaScore = wclctAreaScore;
	}
	public String getDistBmdsbRateVal() {
		return distBmdsbRateVal;
	}
	public void setDistBmdsbRateVal(String distBmdsbRateVal) {
		this.distBmdsbRateVal = distBmdsbRateVal;
	}
	public String getDistBmdsbRateScore() {
		return distBmdsbRateScore;
	}
	public void setDistBmdsbRateScore(String distBmdsbRateScore) {
		this.distBmdsbRateScore = distBmdsbRateScore;
	}
	public String getSvySm() {
		return svySm;
	}
	public void setSvySm(String svySm) {
		this.svySm = svySm;
	}
	public String getMainRisk() {
		return mainRisk;
	}
	public void setMainRisk(String mainRisk) {
		this.mainRisk = mainRisk;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getNcssty() {
		return ncssty;
	}
	public void setNcssty(String ncssty) {
		this.ncssty = ncssty;
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
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getBp() {
		return bp;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
	public String getEp() {
		return ep;
	}
	public void setEp(String ep) {
		this.ep = ep;
	}
	public String getRskFactorVal() {
		return rskFactorVal;
	}
	public void setRskFactorVal(String rskFactorVal) {
		this.rskFactorVal = rskFactorVal;
	}
	public String getRskFactorScore() {
		return rskFactorScore;
	}
	public void setRskFactorScore(String rskFactorScore) {
		this.rskFactorScore = rskFactorScore;
	}
	public String[] getRskFactors() {
		return rskFactors;
	}
	public void setRskFactors(String[] rskFactors) {
		this.rskFactors = rskFactors;
	}
	public String getLcmap() {
		return lcmap;
	}
	public void setLcmap(String lcmap) {
		this.lcmap = lcmap;
	}
	public String getSvyMonth() {
		return svyMonth;
	}
	public void setSvyMonth(String svyMonth) {
		this.svyMonth = svyMonth;
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
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getSvyDtValue() {
		return svyDtValue;
	}
	public void setSvyDtValue(String svyDtValue) {
		this.svyDtValue = svyDtValue;
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
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public String getLastgrd() {
		return lastgrd;
	}
	public void setLastgrd(String lastgrd) {
		this.lastgrd = lastgrd;
	}
	public String getMrngsttus() {
		return mrngsttus;
	}
	public void setMrngsttus(String mrngsttus) {
		this.mrngsttus = mrngsttus;
	}
}
