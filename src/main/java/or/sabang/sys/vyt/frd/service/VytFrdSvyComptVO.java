package or.sabang.sys.vyt.frd.service;

import egovframework.com.cmm.ComDefaultVO;

public class VytFrdSvyComptVO  extends ComDefaultVO{

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
	 * 공유방명
	 */
	private String mstNm;
	/*
	 * 조사연도
	 */
	private String svyYear;
	/*
	 * 조사일자 - 월
	 */
	private String svyMonth;
	/*
	 * 조사ID
	 */
	private String svyId;
	/*
	 * 조사유형
	 */
	private String svyType;
	/*
	 * 노선종류
	 */
	private String routeType;
	/*
	 * 노선코드
	 */
	private String routeCode;
	/*
	 * 임도유형
	 */
	private String frdType;
	/*
	 * 위도
	 */
	private String svyLat;
	/*
	 * 경도
	 */
	private String svyLon;
	/*
	 * pnu코드
	 */
	private String pnu;
	/*
	 * 시도
	 */
	private String svySd;
	/*
	 * 시군구
	 */
	private String svySgg;
	/*
	 * 읍면동
	 */
	private String svyEmd;
	/*
	 * 리
	 */
	private String svyRi;
	/*
	 * 지번
	 */
	private String svyJibun;
	/*
	 * 지목
	 */
	private String svyJimok;
	/*
	 * 조사일자
	 */
	private String svyDt;
	/*
	 * 조사자
	 */
	private String svyUser;
	/*
	 * 시점위도(위치정보_Y)
	 */
	private String py;
	/*
	 * 시점경도(위치정보_X)
	 */
	private String px;
	/*
	 * 계류 내 침식현황
	 */
	private String mrngErsnStat;
	/*
	 * 계류_대계류
	 */
	private String mrngBig;
	/*
	 * 계류종류_목록
	 */
	private String mrngKind;
	/*
	 * 국가지점번호
	 */
	private String nationspotNum;
	/*
	 * 기타(용출수, 묘지 등)
	 */
	private String etc;
	/*
	 * 대상지 계류현황
	 */
	private String destMrngStat;
	/*
	 * 대상지 내 사면침식현황
	 */
	private String destErsn;
	/*
	 * 대상지 내 암반
	 */
	private String destRock;
	/*
	 * 묘지_목록
	 */
	private String cmtryList;
	/*
	 * 묘지_유무
	 */
	private String cmtryAt;
	/*
	 * 벌채지_목록
	 */
	private String cuttingList;
	/*
	 * 벌채지_유무
	 */
	private String cuttingAt;
	/*
	 * 보호시설
	 */
	private String safeFct;
	/*
	 * 보호시설_목록
	 */
	private String safeFctList;
	/*
	 * 본번
	 */
	private String bonbun;
	/*
	 * 부번
	 */
	private String bubun;
	/*
	 * 붕괴우려_목록
	 */
	private String clpsCnrnList;
	/*
	 * 붕괴우려_사면
	 */
	private String clpsCnrn;
	/*
	 * 사면현황
	 */
	private String slopeStatus;
	/*
	 * 사방시설설치_건수
	 */
	private String ecnrFcltyInstlCnt;
	/*
	 * 사방시설설치_종류
	 */
	private String ecnrFcltyInstlType;
	/*
	 * 사방시설설치_목록
	 */
	private String ecnrFcltyInstlList;
	/*
	 * 사방시설필요_건수
	 */
	private String ecnrFcltyNcstyCnt;
	/*
	 * 사방시설필요_목록
	 */
	private String ecnrFcltyNcstyType;
	/*
	 * 사방시설필요_종류
	 */
	private String ecnrFcltyNcstyList;
	/*
	 * 산림재해_건수
	 */
	private String frstDsstrCnt;
	/*
	 * 산림재해_목록
	 */
	private String frstDsstrList;
	/*
	 * 산림재해_산불
	 */
	private String frstDsstrFire;
	/*
	 * 산지경사
	 */
	private String mtSlope;
	/*
	 * 산지경사_목록
	 */
	private String mtSlopeList;
	/*
	 * 산지경사_최대
	 */
	private String mtSlopeMax;
	/*
	 * 산지경사_최소
	 */
	private String mtSlopeMin;
	/*
	 * 산지경사_평균
	 */
	private String mtSlopeAvg;
	/*
	 * 상수원오염_목록
	 */
	private String wtrPltnList;
	/*
	 * 상수원오렴_유무
	 */
	private String wtrPltnAt;
	/*
	 * 석력지_목록
	 */
	private String stmiList;
	/*
	 * 성명
	 */
	private String username;
	/*
	 * 소속
	 */
	private String svyDept;
	/*
	 * 속칭
	 */
	private String commonly;
	/*
	 * 습지_목록
	 */
	private String wetLandList;
	/*
	 * 습지_여부
	 */
	private String wetLandAt;
	/*
	 * 시점_경도
	 */
	private String bpx;
	/*
	 * 시점_위도
	 */
	private String bpy;
	
	private String bpxD;
	private String bpxM;
	private String bpxS;
	
	private String bpyD;
	private String bpyM;
	private String bpyS;
	
	/*
	 * 종점_경도
	 */
	private String epx;
	/*
	 * 종점_위도
	 */
	private String epy;
	
	private String epxD;
	private String epxM;
	private String epxS;
	
	private String epyD;
	private String epyM;
	private String epyS;
	/*
	 * 신규추가
	 */
	private String addNew;
	/*
	 * 암석노출_목록
	 */
	private String rockExposList;
	/*
	 * 암석노출_최대
	 */
	private String rockExposMax;
	/*
	 * 암석노출_최소
	 */
	private String rockExposMin;
	/*
	 * 암석노출_평균
	 */
	private String rockExposAvg;
	/*
	 * 암석노출_합계
	 */
	private String rockExposSum;
	/*
	 * 야생동물_건수
	 */
	private String wildAnmlCnt;
	/*
	 * 야생동물_목록
	 */
	private String wildAnmlList;
	/*
	 * 야생동물_종류
	 */
	private String wildAnmlKind;
	/*
	 * 연약지반_목록
	 */
	private String sofrtGrndList;
	/*
	 * 연약지반_여부 
	 */
	private String sofrtGrndAt;
	/*
	 * 용출수_목록
	 */
	private String eltnWaterList;
	/*
	 * 용출수_유무
	 */
	private String eltnWaterAt;
	/*
	 * 일반현황
	 */
	private String gnrlStts;
	/*
	 * 임도연장(km)
	 */
	private String frdExtns;
	/*
	 * 전답
	 */
	private String field;
	/*
	 * 접근성
	 */
	private String acsbl;
	/*
	 * 조림지_목록
	 */
	private String afrstList;
	/*
	 * 조림지_유무
	 */
	private String afrstAt;
	/*
	 * 종단기울기
	 */
	private String lonSlope;
	/*
	 * 종단기울기_목록
	 */
	private String lonSlopeList;
	/*
	 * 종단기울기_최대
	 */
	private String lonSlopeMax;
	/*
	 * 종단기울기_최소
	 */
	private String lonSlopeMin;
	/*
	 * 종단기울기_평균
	 */
	private String lonSlopeAvg;
	/*
	 * 주요수종_건수
	 */
	private String maintreekndCnt;
	/*
	 * 주요수종_목록
	 */
	private String maintreekndList;
	/*
	 * 주요식생_건수
	 */
	private String mainvegCnt;
	/*
	 * 주요식생_목록
	 */
	private String mainvegList;
	/*
	 * 훼손우려지_목록
	 */
	private String dmgCncrnList;
	/*
	 * 훼손우려지_유무
	 */
	private String dmgCncrnAt;
	/*
	 * 등록일자
	 */
	private String creatDt;
	/*
	 * 수정일자
	 */
	private String lastUpdtPnttm;
	
	/*
	 * 조사ID (상세조회 및 수정페이지 전용, 검색조건 겹치지 않기 위해 따로 작성)
	 */
	private String svyLabel;
	
	/*
	 * 관할청
	 */
	private String compentauth;
	
	/*
	 * 조사구분
	 */
	private String frdRnk;
	
	/*
	 * 예정거리
	 */
	private String schdst;
	
	/*
	 * 대상지관리 고유번호 
	 */
	private String smid;
	/*
	 * 대상지관리 대상지방번호
	 */
	private String sldId;
	
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
	 * 세부관할
	 */
	private String subcompentauth;
	
	// 종단기울기 사진
	private String lonSlopePhoto;
	
	// 산지경사 사진
	private String mtSlopePhoto;
	
	// 암반노출 사진
	private String rockExposPhoto;
	
	// 조림지 사진
	private String afrstPhoto;
	
	// 벌채지 사진
	private String cuttingPhoto;
	
	// 석력지 사진
	private String stmiPhoto;
	// 계류종류 및 현황 사진
	private String mrngKindPhoto;
	// 습지 사진
	private String wetLandPhoto;
	// 묘지 사진
	private String cmtryPhoto;
	// 용출수 사진
	private String eltnWaterPhoto;
	// 연약지반 사진
	private String sofrtGrndPhoto;
	// 붕괴우려지역 사진
	private String clpsCnrnPhoto;
	// 주요수종 사진
	private String maintreekndPhoto;
	// 주요식생 사진
	private String mainvegPhoto;
	// 상수원오염 사진
	private String wtrPltnPhoto;
	// 과다한 훼손우려지 사진
	private String dmgCncrnPhoto;
	// 산불/병해충 사진
	private String frstDsstrPhoto;
	// 야생동물 사진
	private String wildAnmlPhoto;
	// 사방시설 설치여부 사진
	private String ecnrFcltyInstlPhoto;
	// 사방시설 필요여부 사진
	private String ecnrFcltyNcstyPhoto;
	// gidList
	private String gidList;
	
	// 현장사진존재유무
	private String photoResultAt;
	// 분석파일존재유무
	private String analFileResultAt1;
	private String analFileResultAt2;
	
	// 유림구분 (국/유)
	private String compentAssort;
	
	public String getGid() {
		return gid;
	}
	public String getMstNm() {
		return mstNm;
	}
	public String getSvyYear() {
		return svyYear;
	}
	public String getSvyId() {
		return svyId;
	}
	public String getSvyType() {
		return svyType;
	}
	public String getRouteType() {
		return routeType;
	}
	public String getFrdType() {
		return frdType;
	}
	public void setFrdType(String frdType) {
		this.frdType = frdType;
	}
	public String getSvyLat() {
		return svyLat;
	}
	public String getSvyLon() {
		return svyLon;
	}
	public String getPnu() {
		return pnu;
	}
	public String getSvySd() {
		return svySd;
	}
	public String getSvySgg() {
		return svySgg;
	}
	public String getSvyEmd() {
		return svyEmd;
	}
	public String getSvyRi() {
		return svyRi;
	}
	public String getSvyJibun() {
		return svyJibun;
	}
	public String getSvyJimok() {
		return svyJimok;
	}
	public String getSvyDt() {
		return svyDt;
	}
	public String getSvyUser() {
		return svyUser;
	}
	public String getPy() {
		return py;
	}
	public String getPx() {
		return px;
	}
	public String getMrngErsnStat() {
		return mrngErsnStat;
	}
	public String getMrngBig() {
		return mrngBig;
	}
	public String getMrngKind() {
		return mrngKind;
	}
	public String getNationspotNum() {
		return nationspotNum;
	}
	public String getEtc() {
		return etc;
	}
	public String getDestMrngStat() {
		return destMrngStat;
	}
	public String getDestErsn() {
		return destErsn;
	}
	public String getDestRock() {
		return destRock;
	}
	public String getCmtryList() {
		return cmtryList;
	}
	public String getCmtryAt() {
		return cmtryAt;
	}
	public String getCuttingList() {
		return cuttingList;
	}
	public String getCuttingAt() {
		return cuttingAt;
	}
	public String getSafeFct() {
		return safeFct;
	}
	public String getSafeFctList() {
		return safeFctList;
	}
	public String getBonbun() {
		return bonbun;
	}
	public String getBubun() {
		return bubun;
	}
	public String getClpsCnrnList() {
		return clpsCnrnList;
	}
	public String getClpsCnrn() {
		return clpsCnrn;
	}
	public String getSlopeStatus() {
		return slopeStatus;
	}
	public String getEcnrFcltyInstlCnt() {
		return ecnrFcltyInstlCnt;
	}
	public String getEcnrFcltyInstlType() {
		return ecnrFcltyInstlType;
	}
	public String getEcnrFcltyInstlList() {
		return ecnrFcltyInstlList;
	}
	public String getEcnrFcltyNcstyCnt() {
		return ecnrFcltyNcstyCnt;
	}
	public String getEcnrFcltyNcstyType() {
		return ecnrFcltyNcstyType;
	}
	public String getEcnrFcltyNcstyList() {
		return ecnrFcltyNcstyList;
	}
	public String getFrstDsstrCnt() {
		return frstDsstrCnt;
	}
	public String getFrstDsstrList() {
		return frstDsstrList;
	}
	public String getFrstDsstrFire() {
		return frstDsstrFire;
	}
	public String getMtSlope() {
		return mtSlope;
	}
	public String getMtSlopeList() {
		return mtSlopeList;
	}
	public String getMtSlopeMax() {
		return mtSlopeMax;
	}
	public String getMtSlopeMin() {
		return mtSlopeMin;
	}
	public String getMtSlopeAvg() {
		return mtSlopeAvg;
	}
	public String getWtrPltnList() {
		return wtrPltnList;
	}
	public String getWtrPltnAt() {
		return wtrPltnAt;
	}
	public String getStmiList() {
		return stmiList;
	}
	public String getUsername() {
		return username;
	}
	public String getSvyDept() {
		return svyDept;
	}
	public String getCommonly() {
		return commonly;
	}
	public String getWetLandList() {
		return wetLandList;
	}
	public String getWetLandAt() {
		return wetLandAt;
	}
	public String getBpx() {
		return bpx;
	}
	public String getBpy() {
		return bpy;
	}
	public String getEpx() {
		return epx;
	}
	public String getEpy() {
		return epy;
	}
	public String getAddNew() {
		return addNew;
	}
	public String getRockExposList() {
		return rockExposList;
	}
	public String getRockExposMax() {
		return rockExposMax;
	}
	public String getRockExposMin() {
		return rockExposMin;
	}
	public String getRockExposAvg() {
		return rockExposAvg;
	}
	public String getRockExposSum() {
		return rockExposSum;
	}
	public String getWildAnmlCnt() {
		return wildAnmlCnt;
	}
	public String getWildAnmlList() {
		return wildAnmlList;
	}
	public String getWildAnmlKind() {
		return wildAnmlKind;
	}
	public String getSofrtGrndList() {
		return sofrtGrndList;
	}
	public String getSofrtGrndAt() {
		return sofrtGrndAt;
	}
	public String getEltnWaterList() {
		return eltnWaterList;
	}
	public String getEltnWaterAt() {
		return eltnWaterAt;
	}
	public String getGnrlStts() {
		return gnrlStts;
	}
	public String getFrdExtns() {
		return frdExtns;
	}
	public String getField() {
		return field;
	}
	public String getAcsbl() {
		return acsbl;
	}
	public String getAfrstList() {
		return afrstList;
	}
	public String getAfrstAt() {
		return afrstAt;
	}
	public String getLonSlope() {
		return lonSlope;
	}
	public String getLonSlopeList() {
		return lonSlopeList;
	}
	public String getLonSlopeMax() {
		return lonSlopeMax;
	}
	public String getLonSlopeMin() {
		return lonSlopeMin;
	}
	public String getLonSlopeAvg() {
		return lonSlopeAvg;
	}
	public String getMaintreekndCnt() {
		return maintreekndCnt;
	}
	public String getMaintreekndList() {
		return maintreekndList;
	}
	public String getMainvegCnt() {
		return mainvegCnt;
	}
	public String getMainvegList() {
		return mainvegList;
	}
	public String getDmgCncrnList() {
		return dmgCncrnList;
	}
	public String getDmgCncrnAt() {
		return dmgCncrnAt;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getMstId() {
		return mstId;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}
	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}
	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}
	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public void setSvyLat(String svyLat) {
		this.svyLat = svyLat;
	}
	public void setSvyLon(String svyLon) {
		this.svyLon = svyLon;
	}
	public void setPnu(String pnu) {
		this.pnu = pnu;
	}
	public void setSvySd(String svySd) {
		this.svySd = svySd;
	}
	public void setSvySgg(String svySgg) {
		this.svySgg = svySgg;
	}
	public void setSvyEmd(String svyEmd) {
		this.svyEmd = svyEmd;
	}
	public void setSvyRi(String svyRi) {
		this.svyRi = svyRi;
	}
	public void setSvyJibun(String svyJibun) {
		this.svyJibun = svyJibun;
	}
	public void setSvyJimok(String svyJimok) {
		this.svyJimok = svyJimok;
	}
	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
	}
	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public void setPx(String px) {
		this.px = px;
	}
	public void setMrngErsnStat(String mrngErsnStat) {
		this.mrngErsnStat = mrngErsnStat;
	}
	public void setMrngBig(String mrngBig) {
		this.mrngBig = mrngBig;
	}
	public void setMrngKind(String mrngKind) {
		this.mrngKind = mrngKind;
	}
	public void setNationspotNum(String nationspotNum) {
		this.nationspotNum = nationspotNum;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public void setDestMrngStat(String destMrngStat) {
		this.destMrngStat = destMrngStat;
	}
	public void setDestErsn(String destErsn) {
		this.destErsn = destErsn;
	}
	public void setDestRock(String destRock) {
		this.destRock = destRock;
	}
	public void setCmtryList(String cmtryList) {
		this.cmtryList = cmtryList;
	}
	public void setCmtryAt(String cmtryAt) {
		this.cmtryAt = cmtryAt;
	}
	public void setCuttingList(String cuttingList) {
		this.cuttingList = cuttingList;
	}
	public void setCuttingAt(String cuttingAt) {
		this.cuttingAt = cuttingAt;
	}
	public void setSafeFct(String safeFct) {
		this.safeFct = safeFct;
	}
	public void setSafeFctList(String safeFctList) {
		this.safeFctList = safeFctList;
	}
	public void setBonbun(String bonbun) {
		this.bonbun = bonbun;
	}
	public void setBubun(String bubun) {
		this.bubun = bubun;
	}
	public void setClpsCnrnList(String clpsCnrnList) {
		this.clpsCnrnList = clpsCnrnList;
	}
	public void setClpsCnrn(String clpsCnrn) {
		this.clpsCnrn = clpsCnrn;
	}
	public void setSlopeStatus(String slopeStatus) {
		this.slopeStatus = slopeStatus;
	}
	public void setEcnrFcltyInstlCnt(String ecnrFcltyInstlCnt) {
		this.ecnrFcltyInstlCnt = ecnrFcltyInstlCnt;
	}
	public void setEcnrFcltyInstlType(String ecnrFcltyInstlType) {
		this.ecnrFcltyInstlType = ecnrFcltyInstlType;
	}
	public void setEcnrFcltyInstlList(String ecnrFcltyInstlList) {
		this.ecnrFcltyInstlList = ecnrFcltyInstlList;
	}
	public void setEcnrFcltyNcstyCnt(String ecnrFcltyNcstyCnt) {
		this.ecnrFcltyNcstyCnt = ecnrFcltyNcstyCnt;
	}
	public void setEcnrFcltyNcstyType(String ecnrFcltyNcstyType) {
		this.ecnrFcltyNcstyType = ecnrFcltyNcstyType;
	}
	public void setEcnrFcltyNcstyList(String ecnrFcltyNcstyList) {
		this.ecnrFcltyNcstyList = ecnrFcltyNcstyList;
	}
	public void setFrstDsstrCnt(String frstDsstrCnt) {
		this.frstDsstrCnt = frstDsstrCnt;
	}
	public void setFrstDsstrList(String frstDsstrList) {
		this.frstDsstrList = frstDsstrList;
	}
	public void setFrstDsstrFire(String frstDsstrFire) {
		this.frstDsstrFire = frstDsstrFire;
	}
	public void setMtSlope(String mtSlope) {
		this.mtSlope = mtSlope;
	}
	public void setMtSlopeList(String mtSlopeList) {
		this.mtSlopeList = mtSlopeList;
	}
	public void setMtSlopeMax(String mtSlopeMax) {
		this.mtSlopeMax = mtSlopeMax;
	}
	public void setMtSlopeMin(String mtSlopeMin) {
		this.mtSlopeMin = mtSlopeMin;
	}
	public void setMtSlopeAvg(String mtSlopeAvg) {
		this.mtSlopeAvg = mtSlopeAvg;
	}
	public void setWtrPltnList(String wtrPltnList) {
		this.wtrPltnList = wtrPltnList;
	}
	public void setWtrPltnAt(String wtrPltnAt) {
		this.wtrPltnAt = wtrPltnAt;
	}
	public void setStmiList(String stmiList) {
		this.stmiList = stmiList;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setSvyDept(String svyDept) {
		this.svyDept = svyDept;
	}
	public void setCommonly(String commonly) {
		this.commonly = commonly;
	}
	public void setWetLandList(String wetLandList) {
		this.wetLandList = wetLandList;
	}
	public void setWetLandAt(String wetLandAt) {
		this.wetLandAt = wetLandAt;
	}
	public void setBpx(String bpx) {
		this.bpx = bpx;
	}
	public void setBpy(String bpy) {
		this.bpy = bpy;
	}
	public void setEpx(String epx) {
		this.epx = epx;
	}
	public void setEpy(String epy) {
		this.epy = epy;
	}
	public void setAddNew(String addNew) {
		this.addNew = addNew;
	}
	public void setRockExposList(String rockExposList) {
		this.rockExposList = rockExposList;
	}
	public void setRockExposMax(String rockExposMax) {
		this.rockExposMax = rockExposMax;
	}
	public void setRockExposMin(String rockExposMin) {
		this.rockExposMin = rockExposMin;
	}
	public void setRockExposAvg(String rockExposAvg) {
		this.rockExposAvg = rockExposAvg;
	}
	public void setRockExposSum(String rockExposSum) {
		this.rockExposSum = rockExposSum;
	}
	public void setWildAnmlCnt(String wildAnmlCnt) {
		this.wildAnmlCnt = wildAnmlCnt;
	}
	public void setWildAnmlList(String wildAnmlList) {
		this.wildAnmlList = wildAnmlList;
	}
	public void setWildAnmlKind(String wildAnmlKind) {
		this.wildAnmlKind = wildAnmlKind;
	}
	public void setSofrtGrndList(String sofrtGrndList) {
		this.sofrtGrndList = sofrtGrndList;
	}
	public void setSofrtGrndAt(String sofrtGrndAt) {
		this.sofrtGrndAt = sofrtGrndAt;
	}
	public void setEltnWaterList(String eltnWaterList) {
		this.eltnWaterList = eltnWaterList;
	}
	public void setEltnWaterAt(String eltnWaterAt) {
		this.eltnWaterAt = eltnWaterAt;
	}
	public void setGnrlStts(String gnrlStts) {
		this.gnrlStts = gnrlStts;
	}
	public void setFrdExtns(String frdExtns) {
		this.frdExtns = frdExtns;
	}
	public void setField(String field) {
		this.field = field;
	}
	public void setAcsbl(String acsbl) {
		this.acsbl = acsbl;
	}
	public void setAfrstList(String afrstList) {
		this.afrstList = afrstList;
	}
	public void setAfrstAt(String afrstAt) {
		this.afrstAt = afrstAt;
	}
	public void setLonSlope(String lonSlope) {
		this.lonSlope = lonSlope;
	}
	public void setLonSlopeList(String lonSlopeList) {
		this.lonSlopeList = lonSlopeList;
	}
	public void setLonSlopeMax(String lonSlopeMax) {
		this.lonSlopeMax = lonSlopeMax;
	}
	public void setLonSlopeMin(String lonSlopeMin) {
		this.lonSlopeMin = lonSlopeMin;
	}
	public void setLonSlopeAvg(String lonSlopeAvg) {
		this.lonSlopeAvg = lonSlopeAvg;
	}
	public void setMaintreekndCnt(String maintreekndCnt) {
		this.maintreekndCnt = maintreekndCnt;
	}
	public void setMaintreekndList(String maintreekndList) {
		this.maintreekndList = maintreekndList;
	}
	public void setMainvegCnt(String mainvegCnt) {
		this.mainvegCnt = mainvegCnt;
	}
	public void setMainvegList(String mainvegList) {
		this.mainvegList = mainvegList;
	}
	public void setDmgCncrnList(String dmgCncrnList) {
		this.dmgCncrnList = dmgCncrnList;
	}
	public void setDmgCncrnAt(String dmgCncrnAt) {
		this.dmgCncrnAt = dmgCncrnAt;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getSvyMonth() {
		return svyMonth;
	}
	public void setSvyMonth(String svyMonth) {
		this.svyMonth = svyMonth;
	}
	public String getSvyLabel() {
		return svyLabel;
	}
	public void setSvyLabel(String svyLabel) {
		this.svyLabel = svyLabel;
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
	public String getEpxD() {
		return epxD;
	}
	public String getEpxM() {
		return epxM;
	}
	public String getEpxS() {
		return epxS;
	}
	public String getEpyD() {
		return epyD;
	}
	public String getEpyM() {
		return epyM;
	}
	public String getEpyS() {
		return epyS;
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
	public void setEpxD(String epxD) {
		this.epxD = epxD;
	}
	public void setEpxM(String epxM) {
		this.epxM = epxM;
	}
	public void setEpxS(String epxS) {
		this.epxS = epxS;
	}
	public void setEpyD(String epyD) {
		this.epyD = epyD;
	}
	public void setEpyM(String epyM) {
		this.epyM = epyM;
	}
	public void setEpyS(String epyS) {
		this.epyS = epyS;
	}
	public String getCompentauth() {
		return compentauth;
	}
	public String getFrdRnk() {
		return frdRnk;
	}
	public String getSchdst() {
		return schdst;
	}
	public void setCompentauth(String compentauth) {
		this.compentauth = compentauth;
	}
	public void setFrdRnk(String frdRnk) {
		this.frdRnk = frdRnk;
	}
	public void setSchdst(String schdst) {
		this.schdst = schdst;
	}
	public String getSmid() {
		return smid;
	}
	public String getSldId() {
		return sldId;
	}
	public void setSmid(String smid) {
		this.smid = smid;
	}
	public void setSldId(String sldId) {
		this.sldId = sldId;
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
	public String getSubcompentauth() {
		return subcompentauth;
	}
	public void setSubcompentauth(String subcompentauth) {
		this.subcompentauth = subcompentauth;
	}
	public String getLonSlopePhoto() {
		return lonSlopePhoto;
	}
	public String getMtSlopePhoto() {
		return mtSlopePhoto;
	}
	public String getRockExposPhoto() {
		return rockExposPhoto;
	}
	public String getAfrstPhoto() {
		return afrstPhoto;
	}
	public String getCuttingPhoto() {
		return cuttingPhoto;
	}
	public String getStmiPhoto() {
		return stmiPhoto;
	}
	public String getMrngKindPhoto() {
		return mrngKindPhoto;
	}
	public String getWetLandPhoto() {
		return wetLandPhoto;
	}
	public String getCmtryPhoto() {
		return cmtryPhoto;
	}
	public String getEltnWaterPhoto() {
		return eltnWaterPhoto;
	}
	public String getSofrtGrndPhoto() {
		return sofrtGrndPhoto;
	}
	public String getClpsCnrnPhoto() {
		return clpsCnrnPhoto;
	}
	public String getMaintreekndPhoto() {
		return maintreekndPhoto;
	}
	public String getMainvegPhoto() {
		return mainvegPhoto;
	}
	public String getWtrPltnPhoto() {
		return wtrPltnPhoto;
	}
	public String getDmgCncrnPhoto() {
		return dmgCncrnPhoto;
	}
	public String getFrstDsstrPhoto() {
		return frstDsstrPhoto;
	}
	public String getWildAnmlPhoto() {
		return wildAnmlPhoto;
	}
	public String getEcnrFcltyInstlPhoto() {
		return ecnrFcltyInstlPhoto;
	}
	public String getEcnrFcltyNcstyPhoto() {
		return ecnrFcltyNcstyPhoto;
	}
	public void setLonSlopePhoto(String lonSlopePhoto) {
		this.lonSlopePhoto = lonSlopePhoto;
	}
	public void setMtSlopePhoto(String mtSlopePhoto) {
		this.mtSlopePhoto = mtSlopePhoto;
	}
	public void setRockExposPhoto(String rockExposPhoto) {
		this.rockExposPhoto = rockExposPhoto;
	}
	public void setAfrstPhoto(String afrstPhoto) {
		this.afrstPhoto = afrstPhoto;
	}
	public void setCuttingPhoto(String cuttingPhoto) {
		this.cuttingPhoto = cuttingPhoto;
	}
	public void setStmiPhoto(String stmiPhoto) {
		this.stmiPhoto = stmiPhoto;
	}
	public void setMrngKindPhoto(String mrngKindPhoto) {
		this.mrngKindPhoto = mrngKindPhoto;
	}
	public void setWetLandPhoto(String wetLandPhoto) {
		this.wetLandPhoto = wetLandPhoto;
	}
	public void setCmtryPhoto(String cmtryPhoto) {
		this.cmtryPhoto = cmtryPhoto;
	}
	public void setEltnWaterPhoto(String eltnWaterPhoto) {
		this.eltnWaterPhoto = eltnWaterPhoto;
	}
	public void setSofrtGrndPhoto(String sofrtGrndPhoto) {
		this.sofrtGrndPhoto = sofrtGrndPhoto;
	}
	public void setClpsCnrnPhoto(String clpsCnrnPhoto) {
		this.clpsCnrnPhoto = clpsCnrnPhoto;
	}
	public void setMaintreekndPhoto(String maintreekndPhoto) {
		this.maintreekndPhoto = maintreekndPhoto;
	}
	public void setMainvegPhoto(String mainvegPhoto) {
		this.mainvegPhoto = mainvegPhoto;
	}
	public void setWtrPltnPhoto(String wtrPltnPhoto) {
		this.wtrPltnPhoto = wtrPltnPhoto;
	}
	public void setDmgCncrnPhoto(String dmgCncrnPhoto) {
		this.dmgCncrnPhoto = dmgCncrnPhoto;
	}
	public void setFrstDsstrPhoto(String frstDsstrPhoto) {
		this.frstDsstrPhoto = frstDsstrPhoto;
	}
	public void setWildAnmlPhoto(String wildAnmlPhoto) {
		this.wildAnmlPhoto = wildAnmlPhoto;
	}
	public void setEcnrFcltyInstlPhoto(String ecnrFcltyInstlPhoto) {
		this.ecnrFcltyInstlPhoto = ecnrFcltyInstlPhoto;
	}
	public void setEcnrFcltyNcstyPhoto(String ecnrFcltyNcstyPhoto) {
		this.ecnrFcltyNcstyPhoto = ecnrFcltyNcstyPhoto;
	}
	public String getGidList() {
		return gidList;
	}
	public void setGidList(String gidList) {
		this.gidList = gidList;
	}
	public String getPhotoResultAt() {
		return photoResultAt;
	}
	public void setPhotoResultAt(String photoResultAt) {
		this.photoResultAt = photoResultAt;
	}
	
	public String getAnalFileResultAt1() {
		return analFileResultAt1;
	}
	public String getAnalFileResultAt2() {
		return analFileResultAt2;
	}
	public void setAnalFileResultAt1(String analFileResultAt1) {
		this.analFileResultAt1 = analFileResultAt1;
	}
	public void setAnalFileResultAt2(String analFileResultAt2) {
		this.analFileResultAt2 = analFileResultAt2;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getCompentAssort() {
		return compentAssort;
	}
	public void setCompentAssort(String compentAssort) {
		this.compentAssort = compentAssort;
	}
	
}
