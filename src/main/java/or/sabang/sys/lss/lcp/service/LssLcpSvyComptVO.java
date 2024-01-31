package or.sabang.sys.lss.lcp.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssLcpSvyComptVO  extends ComDefaultVO{

	private static final long serialVersionUID = 1L;
	
	/**
	 * gid 
	 */
	private String gid;
	/**
	 * 공유방아이디
	 */
	private String mstId;
	/**
	 * 공유방명칭
	 */
	private String mstNm;
	/**
	 * 조사일자 - 연도
	 */
	private String svyYear;
	/**
	 * 조사일자 - 월 
	 */
	private String svyMonth;
	/**
	 * 조사ID
	 */
	private String svyId;
	/**
	 * 일련번호
	 */
	private String sn;
	/**
	 * 조사자
	 */
	private String svyUser;
	/**
	 * 부서
	 */
	private String svyDept;
	/**
	 * 작성자ID
	 */
	private String loginId;
	/**
	 * 조사유형
	 */
	private String svyType;
	/**
	 * 시도명
	 */
	private String svySd;
	/**
	 * 시군구명
	 */
	private String svySgg;
	/**
	 * 읍면동명
	 */
	private String svyEmd;
	/**
	 * 리명
	 */
	private String svyRi;
	/**
	 * 지번
	 */
	private String svyJibun;
	/**
	 * 주소
	 */
	private String svyAddr;	
	/**
	 * 위도
	 */
	private String px;
	/**
	 * 경도
	 */
	private String py;
	/**
	 * 위경도
	 */
	private String svyLatLon;
	/**
	 * 고도
	 */
	private String pz;
	/**
	 * 조사일
	 */
	private String svyDt;
	/**
	 * 판정점수
	 */
	private String geologysm;
	/**
	 * 판정등급
	 */
	private String geologygrd;
	/**
	 * 주 구성암석 측정값
	 */
	private String cmprokval;
	/**
	 * 주 구성암석 점수
	 */
	private String cmprokscore;
	/**
	 * 타 지층 및 관입암 유/무
	 */
	private String instrokat;
	/**
	 * 모암 측정값
	 */
	private String prntrckval;
	/**
	 * 모암 점수
	 */
	private String prntrckscore;
	/**
	 * 암석풍화 측정값
	 */
	private String rokwthrval;
	/**
	 * 암석풍화 점수
	 */
	private String rokwthrscore;
	/**
	 * 지질구조_단층
	 */
	private String geologyflt;
	/**
	 * 지질구조_습곡
	 */
	private String geologyfld;
	/**
	 * 불연속면_조사1_종류
	 */
	private String disctnupln1;
	/**
	 * 불연속면_조사1_주향
	 */
	private String disctnupln1_strk;
	/**
	 * 불연속면_조사1_경사
	 */
	private String disctnupln1_dip;
	/**
	 * 불연속면_조사2_종류
	 */
	private String disctnupln2;
	/**
	 * 불연속면_조사2_주향
	 */
	private String disctnupln2_strk;
	/**
	 * 불연속면_조사2_경사
	 */
	private String disctnupln2_dip;
	/**
	 * 불연속면_조사3_종류
	 */
	private String disctnupln3;
	/**
	 * 불연속면_조사3_주향
	 */
	private String disctnupln3_strk;
	/**
	 * 불연속면_조사3_경사
	 */
	private String disctnupln3_dip;
	/**
	 * 불연속면_조사4_종류
	 */
	private String disctnupln4;
	/**
	 * 불연속면_조사4_주향
	 */
	private String disctnupln4_strk;
	/**
	 * 불연속면_조사4_경사
	 */
	private String disctnupln4_dip;
	/**
	 * 불연속면_조사5_종류
	 */
	private String disctnupln5;
	/**
	 * 불연속면_조사5_주향
	 */
	private String disctnupln5_strk;
	/**
	 * 불연속면_조사5_경사
	 */
	private String disctnupln5_dip;
	/**
	 * 불연속면 방향수
	 */
	private String disctnuplndrcno;
	/**
	 * 불연속면 사면의 방향성 측정값
	 */
	private String disctnuplnslpval;
	/**
	 * 불연속면 사면의 방향성 점수
	 */
	private String disctnuplnslpscore;
	/**
	 * 불연속면 간격 측정값
	 */
	private String disctnuplnintvlval;
	/**
	 * 불연속면 간격 점수
	 */
	private String disctnuplnintvlscore;
	/**
	 * 토양형
	 */
	private String soilty;
	/**
	 * 토심 b층까지의 깊이 측정값
	 */
	private String soildeptbval;
	/**
	 * 토심 b층까지의 깊이 점수(토심판정값)
	 */
	private String soildeptbscore;
	/**
	 * 토성 b층 기준(약30cm)부근 측정값
	 */
	private String soilclassbval;
	/**
	 * 토성 b층 기준(약30cm)부근 점수
	 */
	private String soilclassbscore;
	/**
	 * 토양수분상태 측정값
	 */
	private String soilwtrval;
	/**
	 * 토양수분상태 점수
	 */
	private String soilwtscore;
	/**
	 * 토양구조
	 */
	private String soilstrct;
	/**
	 * 암석노출도
	 */
	private String rokexpsr;
	/**
	 * 너덜(talus)유무
	 */
	private String talusat;
	/**
	 * 너덜(talus)유무 점수
	 */
	private String talusatscore;
	/**
	 * 최대높이
	 */
	private String mxhg;
	/**
	 * 사면길이 측정값
	 */
	private String slngval;
	/**
	 * 사면길이 점수
	 */
	private String slngscore;
	/**
	 * 지형구분 측정값
	 */
	private String tpgrphval;
	/**
	 * 지형구분 점수
	 */
	private String tpgrphscore;
	/**
	 * 조사지역 위치
	 */
	private String arealcval;
	/**
	 * 조사지역 능선
	 */
	private String arealcridge;
	/**
	 * 평면형(수평적) 측정값
	 */
	private String plnformval;
	/**
	 * 평면형(수평적) 점수
	 */
	private String plnformscore;
	/**
	 * 종단면형(수직적) 측정값
	 */
	private String lngformval;
	/**
	 * 종단면형(수직적) 점수
	 */
	private String lngformscore;
	/**
	 * 경사도 측정값
	 */
	private String slprngval;
	/**
	 * 경사도 점수
	 */
	private String slprngscore;
	/**
	 * 경사
	 */
	private String slprngavrg;
	/**
	 * 경사도 최소
	 */
	private String slprngminval;
	/**
	 * 경사도 최대
	 */
	private String slprngmaxval;
	/**
	 * 경사도 평균
	 */
	private String slprngavgval;
	/**
	 * 상부지하수유입
	 */
	private String ugrwtr_posblty;
	/**
	 * 하부계류
	 */
	private String dwstrm_at;
	/**
	 * 샘, 소, 저수지 유무
	 */
	private String sprg_at;
	/**
	 * 임상
	 */
	private String frstfrval;
	/**
	 * 임상코드
	 */
	private String frstfrCd;
	/**
	 * 주요수종
	 */
	private String maintreeknd;
	/**
	 * 주요수종코드
	 */
	private String maintreekndCd;
	/**
	 * 임지이용상태
	 */
	private String frlndsttus;
	/**
	 * 임지이용상태코드
	 */
	private String frlndsttusCd;
	/**
	 * 조사경계하부임지이용상태
	 */
	private String lwbndlwfrlndsttus;
	/**
	 * 조사경계하부임지이용상태코드
	 */
	private String lwbndlwfrlndsttusCd;
	/**
	 * 징후발생 여부
	 */
	private String symptmoccur;
	/**
	 * 직접징후 측정값
	 */
	private String dirsymptmval;
	/**
	 * 직접징후 점수
	 */
	private String dirsymptmscore;
	/**
	 * 간접징후 측정값
	 */
	private String indirsymptmval;
	/**
	 * 간접징후 점수
	 */
	private String indirsymptmscore;
	/**
	 * 땅밀림 징후 
	 */
	private String lcpsttus;
	/**
	 * 현황도
	 */
	private String sttusprnt;	
	/**
	 * 종합의견1
	 */
	private String opinion1;
	/**
	 * 종합의견2
	 */
	private String opinion2;
	/**
	 * 종합의견3
	 */
	private String opinion3;
	/**
	 * 종합의견4
	 */
	private String opinion4;
	/**
	 * 종합의견5
	 */
	private String opinion5;
	/**
	 * 최종점검결과
	 */
	private String fckRslt;
	/**
	 * 조치사항
	 */
	private String mngmtr;
	/**
	 * 지정해제
	 */
	private String appnRelis;
	/**
	 * 등록일자
	 */
	private String creatDt;
	/**
	 * 수정일자
	 */
	private String lastUpdtPnttm;
	/**
	 * 사진
	 */
	private String photo;
	/**
	 * 사진 URL1
	 */
	private String photoSrc1;
	/**
	 * 사진 URL2
	 */
	private String photoSrc2;
	/**
	 * 사진 URL3
	 */
	private String photoSrc3;
	/**
	 * 사진 URL4
	 */
	private String photoSrc4;
	/**
	 * 사진 URL5
	 */
	private String photoSrc5;
	/**
	 * 사진 URL5
	 */
	private String photoSrc6;
	/**
	 * 사진태그
	 */
	private String photoTag;
	/**
	 * 사진태그1
	 */
	private String photoTag1;
	/**
	 * 사진태그2
	 */
	private String photoTag2;
	/**
	 * 사진태그3
	 */
	private String photoTag3;
	/**
	 * 사진태그4
	 */
	private String photoTag4;
	/**
	 * 사진태그5
	 */
	private String photoTag5;
	/**
	 * 사진태그6
	 */
	private String photoTag6;
	/**
	 * 사진태그배열
	 */
	private String photoTagList;
	/**
	 * 위치도
	 */
	private String lcmap;
	/**
	 * 땅밀림현황 균열
	 */
	private String lcpsttus_crk;
	/**
	 * 땅밀림현황 단차
	 */
	private String lcpsttus_stp;
	/**
	 * 땅밀림현황 구조물이상
	 */
	private String lcpsttus_strct;
	/**
	 * 땅밀림현황 수목이상생장
	 */
	private String lcpsttus_wdpt;
	/**
	 * 땅밀림현황 지하수용출
	 */
	private String lcpsttus_ugrwtr;
	/**
	 * 판정점수 지질특성
	 */
	private String judgScore_geo;
	/**
	 * 판정점수 지형특성
	 */
	private String judgScore_topo;
	/**
	 * 판정점수 토양특성
	 */
	private String judgScore_soil;
	/**
	 * 판정점수 구조물이상
	 */
	private String judgScore_strct;
	/**
	 * 판정점수 지하수용출
	 */
	private String judgScore_ugwtr;
	/**
	 * 판정점수 수목이상생장
	 */
	private String judgScore_wdpt;
	/**
	 * 판정점수 균열
	 */
	private String judgScore_crk;
	/**
	 * 판정점수 단차
	 */
	private String judgScore_stp;
	/**
	 * 땅밀림 패스유무
	 */
	private String passat;
	/**
	 * 땅밀림 패스사유
	 */
	private String passresn;
	/**
	 * 기타특성 민가
	 */
	private String priHouse;
	/**
	 * 완료
	 */
	private String lcpcomplet;
	/**
	 * 시설년도
	 */
	private String faciltyYear;
	/**
	 * 조사정보 ATTR
	 */
	private String attr;
	/**
	 * 조사정보 fid
	 */
	private int fid;
	/**
	 * 위도
	 */
	private String svyLat;
	/**
	 * 경도
	 */
	private String svyLon;
	/**
	 * 판정점수합계
	 */
	private String lssfrsm;
	/**
	 * 판정표등급
	 */
	private String lssfrgrd;
	/**
	 * 자문판정등급
	 */
	private String cnsutgrd;
	/**
	 * 최종판정등급
	 */
	private String lastgrd;
	/**
	 * 자문사유
	 */
	private String cnsutresn;
	/**
	 * 땅밀림징후_균열
	 */
	private String lcpSttusCrk;
	/**
	 * 땅밀림징후_단차
	 */
	private String lcpSttusStp;
	/**
	 * 땅밀림징후_수목이상생장
	 */
	private String lcpSttusWdpt;
	/**
	 * 땅밀림징후_구조물이상
	 */
	private String lcpSttusStrct;
	/**
	 * 땅밀림징후_지하수용출
	 */
	private String lcpSttusUgrwtr;
	/**
	 * 공간정보테이블 구분
	 */
	private String GIS_SE;
	
	public String getGid() {
		return gid;
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
	public String getSvyDept() {
		return svyDept;
	}
	public void setSvyDept(String svyDept) {
		this.svyDept = svyDept;
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
	public String getSvyAddr() {
		return svyAddr;
	}
	public void setSvyAddr(String svyAddr) {
		this.svyAddr = svyAddr;
	}
	public String getPx() {
		return px;
	}
	public void setPx(String px) {
		this.px = px;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public String getPz() {
		return pz;
	}
	public void setPz(String pz) {
		this.pz = pz;
	}
	public String getSvyDt() {
		return svyDt;
	}
	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
	}
	public String getGeologysm() {
		return geologysm;
	}
	public void setGeologysm(String geologysm) {
		this.geologysm = geologysm;
	}
	public String getGeologygrd() {
		return geologygrd;
	}
	public void setGeologygrd(String geologygrd) {
		this.geologygrd = geologygrd;
	}
	public String getCmprokval() {
		return cmprokval;
	}
	public void setCmprokval(String cmprokval) {
		this.cmprokval = cmprokval;
	}
	public String getCmprokscore() {
		return cmprokscore;
	}
	public void setCmprokscore(String cmprokscore) {
		this.cmprokscore = cmprokscore;
	}
	public String getInstrokat() {
		return instrokat;
	}
	public void setInstrokat(String instrokat) {
		this.instrokat = instrokat;
	}
	public String getPrntrckval() {
		return prntrckval;
	}
	public void setPrntrckval(String prntrckval) {
		this.prntrckval = prntrckval;
	}
	public String getPrntrckscore() {
		return prntrckscore;
	}
	public void setPrntrckscore(String prntrckscore) {
		this.prntrckscore = prntrckscore;
	}
	public String getRokwthrval() {
		return rokwthrval;
	}
	public void setRokwthrval(String rokwthrval) {
		this.rokwthrval = rokwthrval;
	}
	public String getRokwthrscore() {
		return rokwthrscore;
	}
	public void setRokwthrscore(String rokwthrscore) {
		this.rokwthrscore = rokwthrscore;
	}
	public String getGeologyflt() {
		return geologyflt;
	}
	public void setGeologyflt(String geologyflt) {
		this.geologyflt = geologyflt;
	}
	public String getGeologyfld() {
		return geologyfld;
	}
	public void setGeologyfld(String geologyfld) {
		this.geologyfld = geologyfld;
	}
	public String getDisctnupln1() {
		return disctnupln1;
	}
	public void setDisctnupln1(String disctnupln1) {
		this.disctnupln1 = disctnupln1;
	}
	public String getDisctnupln1_strk() {
		return disctnupln1_strk;
	}
	public void setDisctnupln1_strk(String disctnupln1_strk) {
		this.disctnupln1_strk = disctnupln1_strk;
	}
	public String getDisctnupln1_dip() {
		return disctnupln1_dip;
	}
	public void setDisctnupln1_dip(String disctnupln1_dip) {
		this.disctnupln1_dip = disctnupln1_dip;
	}
	public String getDisctnupln2() {
		return disctnupln2;
	}
	public void setDisctnupln2(String disctnupln2) {
		this.disctnupln2 = disctnupln2;
	}
	public String getDisctnupln2_strk() {
		return disctnupln2_strk;
	}
	public void setDisctnupln2_strk(String disctnupln2_strk) {
		this.disctnupln2_strk = disctnupln2_strk;
	}
	public String getDisctnupln2_dip() {
		return disctnupln2_dip;
	}
	public void setDisctnupln2_dip(String disctnupln2_dip) {
		this.disctnupln2_dip = disctnupln2_dip;
	}
	public String getDisctnupln3() {
		return disctnupln3;
	}
	public void setDisctnupln3(String disctnupln3) {
		this.disctnupln3 = disctnupln3;
	}
	public String getDisctnupln3_strk() {
		return disctnupln3_strk;
	}
	public void setDisctnupln3_strk(String disctnupln3_strk) {
		this.disctnupln3_strk = disctnupln3_strk;
	}
	public String getDisctnupln3_dip() {
		return disctnupln3_dip;
	}
	public void setDisctnupln3_dip(String disctnupln3_dip) {
		this.disctnupln3_dip = disctnupln3_dip;
	}
	public String getDisctnupln4() {
		return disctnupln4;
	}
	public void setDisctnupln4(String disctnupln4) {
		this.disctnupln4 = disctnupln4;
	}
	public String getDisctnupln4_strk() {
		return disctnupln4_strk;
	}
	public void setDisctnupln4_strk(String disctnupln4_strk) {
		this.disctnupln4_strk = disctnupln4_strk;
	}
	public String getDisctnupln4_dip() {
		return disctnupln4_dip;
	}
	public void setDisctnupln4_dip(String disctnupln4_dip) {
		this.disctnupln4_dip = disctnupln4_dip;
	}
	public String getDisctnupln5() {
		return disctnupln5;
	}
	public void setDisctnupln5(String disctnupln5) {
		this.disctnupln5 = disctnupln5;
	}
	public String getDisctnupln5_strk() {
		return disctnupln5_strk;
	}
	public void setDisctnupln5_strk(String disctnupln5_strk) {
		this.disctnupln5_strk = disctnupln5_strk;
	}
	public String getDisctnupln5_dip() {
		return disctnupln5_dip;
	}
	public void setDisctnupln5_dip(String disctnupln5_dip) {
		this.disctnupln5_dip = disctnupln5_dip;
	}
	public String getDisctnuplndrcno() {
		return disctnuplndrcno;
	}
	public void setDisctnuplndrcno(String disctnuplndrcno) {
		this.disctnuplndrcno = disctnuplndrcno;
	}
	public String getDisctnuplnslpval() {
		return disctnuplnslpval;
	}
	public void setDisctnuplnslpval(String disctnuplnslpval) {
		this.disctnuplnslpval = disctnuplnslpval;
	}
	public String getDisctnuplnslpscore() {
		return disctnuplnslpscore;
	}
	public void setDisctnuplnslpscore(String disctnuplnslpscore) {
		this.disctnuplnslpscore = disctnuplnslpscore;
	}
	public String getDisctnuplnintvlval() {
		return disctnuplnintvlval;
	}
	public void setDisctnuplnintvlval(String disctnuplnintvlval) {
		this.disctnuplnintvlval = disctnuplnintvlval;
	}
	public String getDisctnuplnintvlscore() {
		return disctnuplnintvlscore;
	}
	public void setDisctnuplnintvlscore(String disctnuplnintvlscore) {
		this.disctnuplnintvlscore = disctnuplnintvlscore;
	}
	public String getSoilty() {
		return soilty;
	}
	public void setSoilty(String soilty) {
		this.soilty = soilty;
	}
	public String getSoildeptbval() {
		return soildeptbval;
	}
	public void setSoildeptbval(String soildeptbval) {
		this.soildeptbval = soildeptbval;
	}
	public String getSoildeptbscore() {
		return soildeptbscore;
	}
	public void setSoildeptbscore(String soildeptbscore) {
		this.soildeptbscore = soildeptbscore;
	}
	public String getSoilclassbval() {
		return soilclassbval;
	}
	public void setSoilclassbval(String soilclassbval) {
		this.soilclassbval = soilclassbval;
	}
	public String getSoilclassbscore() {
		return soilclassbscore;
	}
	public String getLcpsttus_crk() {
		return lcpsttus_crk;
	}
	public void setLcpsttus_crk(String lcpsttus_crk) {
		this.lcpsttus_crk = lcpsttus_crk;
	}
	public String getLcpsttus_stp() {
		return lcpsttus_stp;
	}
	public void setLcpsttus_stp(String lcpsttus_stp) {
		this.lcpsttus_stp = lcpsttus_stp;
	}
	public String getLcpsttus_strct() {
		return lcpsttus_strct;
	}
	public void setLcpsttus_strct(String lcpsttus_strct) {
		this.lcpsttus_strct = lcpsttus_strct;
	}
	public String getLcpsttus_wdpt() {
		return lcpsttus_wdpt;
	}
	public void setLcpsttus_wdpt(String lcpsttus_wdpt) {
		this.lcpsttus_wdpt = lcpsttus_wdpt;
	}
	public String getLcpsttus_ugrwtr() {
		return lcpsttus_ugrwtr;
	}
	public void setLcpsttus_ugrwtr(String lcpsttus_ugrwtr) {
		this.lcpsttus_ugrwtr = lcpsttus_ugrwtr;
	}
	public void setSoilclassbscore(String soilclassbscore) {
		this.soilclassbscore = soilclassbscore;
	}
	public String getSoilwtrval() {
		return soilwtrval;
	}
	public void setSoilwtrval(String soilwtrval) {
		this.soilwtrval = soilwtrval;
	}
	public String getSoilwtscore() {
		return soilwtscore;
	}
	public void setSoilwtscore(String soilwtscore) {
		this.soilwtscore = soilwtscore;
	}
	public String getSoilstrct() {
		return soilstrct;
	}
	public void setSoilstrct(String soilstrct) {
		this.soilstrct = soilstrct;
	}
	public String getRokexpsr() {
		return rokexpsr;
	}
	public void setRokexpsr(String rokexpsr) {
		this.rokexpsr = rokexpsr;
	}
	public String getTalusat() {
		return talusat;
	}
	public void setTalusat(String talusat) {
		this.talusat = talusat;
	}
	public String getTalusatscore() {
		return talusatscore;
	}
	public void setTalusatscore(String talusatscore) {
		this.talusatscore = talusatscore;
	}
	public String getMxhg() {
		return mxhg;
	}
	public void setMxhg(String mxhg) {
		this.mxhg = mxhg;
	}
	public String getSlngval() {
		return slngval;
	}
	public void setSlngval(String slngval) {
		this.slngval = slngval;
	}
	public String getSlngscore() {
		return slngscore;
	}
	public void setSlngscore(String slngscore) {
		this.slngscore = slngscore;
	}
	public String getTpgrphval() {
		return tpgrphval;
	}
	public void setTpgrphval(String tpgrphval) {
		this.tpgrphval = tpgrphval;
	}
	public String getTpgrphscore() {
		return tpgrphscore;
	}
	public void setTpgrphscore(String tpgrphscore) {
		this.tpgrphscore = tpgrphscore;
	}
	public String getArealcval() {
		return arealcval;
	}
	public void setArealcval(String arealcval) {
		this.arealcval = arealcval;
	}
	public String getArealcridge() {
		return arealcridge;
	}
	public void setArealcridge(String arealcridge) {
		this.arealcridge = arealcridge;
	}
	public String getPlnformval() {
		return plnformval;
	}
	public void setPlnformval(String plnformval) {
		this.plnformval = plnformval;
	}
	public String getPlnformscore() {
		return plnformscore;
	}
	public void setPlnformscore(String plnformscore) {
		this.plnformscore = plnformscore;
	}
	public String getLngformval() {
		return lngformval;
	}
	public void setLngformval(String lngformval) {
		this.lngformval = lngformval;
	}
	public String getLngformscore() {
		return lngformscore;
	}
	public void setLngformscore(String lngformscore) {
		this.lngformscore = lngformscore;
	}
	public String getSlprngval() {
		return slprngval;
	}
	public void setSlprngval(String slprngval) {
		this.slprngval = slprngval;
	}
	public String getSlprngscore() {
		return slprngscore;
	}
	public void setSlprngscore(String slprngscore) {
		this.slprngscore = slprngscore;
	}
	public String getSlprngavrg() {
		return slprngavrg;
	}
	public void setSlprngavrg(String slprngavrg) {
		this.slprngavrg = slprngavrg;
	}
	public String getUgrwtr_posblty() {
		return ugrwtr_posblty;
	}
	public void setUgrwtr_posblty(String ugrwtr_posblty) {
		this.ugrwtr_posblty = ugrwtr_posblty;
	}
	public String getDwstrm_at() {
		return dwstrm_at;
	}
	public void setDwstrm_at(String dwstrm_at) {
		this.dwstrm_at = dwstrm_at;
	}
	public String getSprg_at() {
		return sprg_at;
	}
	public void setSprg_at(String sprg_at) {
		this.sprg_at = sprg_at;
	}
	public String getFrstfrval() {
		return frstfrval;
	}
	public void setFrstfrval(String frstfrval) {
		this.frstfrval = frstfrval;
	}
	public String getFrstfrCd() {
		return frstfrCd;
	}
	public void setFrstfrCd(String frstfrCd) {
		this.frstfrCd = frstfrCd;
	}
	public String getMaintreeknd() {
		return maintreeknd;
	}
	public void setMaintreeknd(String maintreeknd) {
		this.maintreeknd = maintreeknd;
	}
	public String getFrlndsttus() {
		return frlndsttus;
	}
	public void setFrlndsttus(String frlndsttus) {
		this.frlndsttus = frlndsttus;
	}
	public String getLwbndlwfrlndsttus() {
		return lwbndlwfrlndsttus;
	}
	public void setLwbndlwfrlndsttus(String lwbndlwfrlndsttus) {
		this.lwbndlwfrlndsttus = lwbndlwfrlndsttus;
	}
	public String getSymptmoccur() {
		return symptmoccur;
	}
	public void setSymptmoccur(String symptmoccur) {
		this.symptmoccur = symptmoccur;
	}
	public String getDirsymptmval() {
		return dirsymptmval;
	}
	public void setDirsymptmval(String dirsymptmval) {
		this.dirsymptmval = dirsymptmval;
	}
	public String getDirsymptmscore() {
		return dirsymptmscore;
	}
	public void setDirsymptmscore(String dirsymptmscore) {
		this.dirsymptmscore = dirsymptmscore;
	}
	public String getIndirsymptmval() {
		return indirsymptmval;
	}
	public void setIndirsymptmval(String indirsymptmval) {
		this.indirsymptmval = indirsymptmval;
	}
	public String getIndirsymptmscore() {
		return indirsymptmscore;
	}
	public void setIndirsymptmscore(String indirsymptmscore) {
		this.indirsymptmscore = indirsymptmscore;
	}
	public String getLcpsttus() {
		return lcpsttus;
	}
	public void setLcpsttus(String lcpsttus) {
		this.lcpsttus = lcpsttus;
	}
	public String getSttusprnt() {
		return sttusprnt;
	}
	public void setSttusprnt(String sttusprnt) {
		this.sttusprnt = sttusprnt;
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
	public String getPhotoTagList() {
		return photoTagList;
	}
	public void setPhotoTagList(String photoTagList) {
		this.photoTagList = photoTagList;
	}
	public String getLcmap() {
		return lcmap;
	}
	public void setLcmap(String lcmap) {
		this.lcmap = lcmap;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSvyLatLon() {
		return svyLatLon;
	}
	public void setSvyLatLon(String svyLatLon) {
		this.svyLatLon = svyLatLon;
	}
	public String getSlprngminval() {
		return slprngminval;
	}
	public void setSlprngminval(String slprngminval) {
		this.slprngminval = slprngminval;
	}
	public String getSlprngmaxval() {
		return slprngmaxval;
	}
	public void setSlprngmaxval(String slprngmaxval) {
		this.slprngmaxval = slprngmaxval;
	}
	public String getMaintreekndCd() {
		return maintreekndCd;
	}
	public void setMaintreekndCd(String maintreekndCd) {
		this.maintreekndCd = maintreekndCd;
	}
	public String getFrlndsttusCd() {
		return frlndsttusCd;
	}
	public void setFrlndsttusCd(String frlndsttusCd) {
		this.frlndsttusCd = frlndsttusCd;
	}
	public String getLwbndlwfrlndsttusCd() {
		return lwbndlwfrlndsttusCd;
	}
	public void setLwbndlwfrlndsttusCd(String lwbndlwfrlndsttusCd) {
		this.lwbndlwfrlndsttusCd = lwbndlwfrlndsttusCd;
	}
	public String getJudgScore_geo() {
		return judgScore_geo;
	}
	public void setJudgScore_geo(String judgScore_geo) {
		this.judgScore_geo = judgScore_geo;
	}
	public String getJudgScore_topo() {
		return judgScore_topo;
	}
	public void setJudgScore_topo(String judgScore_topo) {
		this.judgScore_topo = judgScore_topo;
	}
	public String getJudgScore_soil() {
		return judgScore_soil;
	}
	public void setJudgScore_soil(String judgScore_soil) {
		this.judgScore_soil = judgScore_soil;
	}
	public String getJudgScore_strct() {
		return judgScore_strct;
	}
	public void setJudgScore_strct(String judgScore_strct) {
		this.judgScore_strct = judgScore_strct;
	}
	public String getJudgScore_ugwtr() {
		return judgScore_ugwtr;
	}
	public void setJudgScore_ugwtr(String judgScore_ugwtr) {
		this.judgScore_ugwtr = judgScore_ugwtr;
	}
	public String getJudgScore_wdpt() {
		return judgScore_wdpt;
	}
	public void setJudgScore_wdpt(String judgScore_wdpt) {
		this.judgScore_wdpt = judgScore_wdpt;
	}
	public String getJudgScore_crk() {
		return judgScore_crk;
	}
	public void setJudgScore_crk(String judgScore_crk) {
		this.judgScore_crk = judgScore_crk;
	}
	public String getJudgScore_stp() {
		return judgScore_stp;
	}
	public void setJudgScore_stp(String judgScore_stp) {
		this.judgScore_stp = judgScore_stp;
	}
	public String getPriHouse() {
		return priHouse;
	}
	public void setPriHouse(String priHouse) {
		this.priHouse = priHouse;
	}
	public String getLcpcomplet() {
		return lcpcomplet;
	}
	public void setLcpcomplet(String lcpcomplet) {
		this.lcpcomplet = lcpcomplet;
	}
	public String getPassat() {
		return passat;
	}
	public void setPassat(String passat) {
		this.passat = passat;
	}
	public String getPassresn() {
		return passresn;
	}
	public void setPassresn(String passresn) {
		this.passresn = passresn;
	}
	public String getFaciltyYear() {
		return faciltyYear;
	}
	public void setFaciltyYear(String faciltyYear) {
		this.faciltyYear = faciltyYear;
	}
	public String getSlprngavgval() {
		return slprngavgval;
	}
	public void setSlprngavgval(String slprngavgval) {
		this.slprngavgval = slprngavgval;
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
	public String getLssfrsm() {
		return lssfrsm;
	}
	public void setLssfrsm(String lssfrsm) {
		this.lssfrsm = lssfrsm;
	}
	public String getLssfrgrd() {
		return lssfrgrd;
	}
	public void setLssfrgrd(String lssfrgrd) {
		this.lssfrgrd = lssfrgrd;
	}
	public String getCnsutgrd() {
		return cnsutgrd;
	}
	public void setCnsutgrd(String cnsutgrd) {
		this.cnsutgrd = cnsutgrd;
	}
	public String getLastgrd() {
		return lastgrd;
	}
	public void setLastgrd(String lastgrd) {
		this.lastgrd = lastgrd;
	}
	public String getCnsutresn() {
		return cnsutresn;
	}
	public void setCnsutresn(String cnsutresn) {
		this.cnsutresn = cnsutresn;
	}
	public String getLcpSttusCrk() {
		return lcpSttusCrk;
	}
	public void setLcpSttusCrk(String lcpSttusCrk) {
		this.lcpSttusCrk = lcpSttusCrk;
	}
	public String getLcpSttusStp() {
		return lcpSttusStp;
	}
	public void setLcpSttusStp(String lcpSttusStp) {
		this.lcpSttusStp = lcpSttusStp;
	}
	public String getLcpSttusWdpt() {
		return lcpSttusWdpt;
	}
	public void setLcpSttusWdpt(String lcpSttusWdpt) {
		this.lcpSttusWdpt = lcpSttusWdpt;
	}
	public String getLcpSttusStrct() {
		return lcpSttusStrct;
	}
	public void setLcpSttusStrct(String lcpSttusStrct) {
		this.lcpSttusStrct = lcpSttusStrct;
	}
	public String getLcpSttusUgrwtr() {
		return lcpSttusUgrwtr;
	}
	public void setLcpSttusUgrwtr(String lcpSttusUgrwtr) {
		this.lcpSttusUgrwtr = lcpSttusUgrwtr;
	}
	public String getGIS_SE() {
		return GIS_SE;
	}
	public void setGIS_SE(String gIS_SE) {
		GIS_SE = gIS_SE;
	}
	
}
