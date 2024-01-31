package or.sabang.sys.lss.wka.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssWkaSvyComptVO  extends ComDefaultVO{

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
	 * 조사정보 fid
	 */
	private String fid;
	/*
	 * 조사구분
	 */
	private String svyType;
	/*
	 * 기초ID
	 */
	private String svyId;
	/*
	 * 시도
	 */
	private String svySd;
	/*
	 * 시군구
	 */
	private String svySgg;
	/*
	 * 읍면
	 */
	private String svyEmd;
	/*
	 * 리동
	 */
	private String svyRi;
	/*
	 * 지번
	 */
	private String svyJibun;
	/*
	 * 관할1
	 */
	private String svyRegion1;
	/*
	 * 관할2
	 */
	private String svyRegion2;
	/*
	 * 필요성
	 */
	private String ncssty;
	/*
	 * 관리주체
	 */
	private String mgc;
	/*
	 * 주소
	 */
	private String addr;
	/*
	 * 위도
	 */
	private String lat;
	/*
	 * 경도
	 */
	private String lon;
	/*
	 * 소속
	 */
	private String svydept;
	/*
	 * 직급
	 */
	private String syvofcps;
	/*
	 * 조사자
	 */
	private String svyUser;
	/*
	 * 연락처
	 */
	private String svymbtl;
	/*
	 * 순번
	 */
	private String sn;
	/*
	 * 기초조사수행연도
	 */
	private String bscdt;
	/*
	 * 점검일시
	 */
	private String svydt;
	/*
	 * 조사자
	 */
	private String svyuser;
	/*
	 * 점검자
	 */
	private String chkuser;
	/*
	 * 유역면적
	 */
	private String dgrarea;
	/*
	 * 취약지역면적
	 */
	private String frgltyrenarea;
	/*
	 * 보호시설
	 */
	private String safefct;
	/*
	 * 인가
	 */
	private String house;
	/*
	 * 호수
	 */
	private String lake;
	/*
	 * 상부주요시설
	 */
	private String highmainfct;
	/*
	 * 상부인가현황
	 */
	private String highhousesttus;
	/*
	 * 하부주요시설
	 */
	private String lowmainfct;
	/*
	 * 하부인가현황
	 */
	private String lowhousesttus;
	/*
	 * 필요공종
	 */
	private String ncsstyworktype;
	/*
	 * 고도
	 */
	private String elevation;
	/*
	 * 현장조사점수
	 */
	private String fieldsurveyscore;
	/*
	 * 안정해석점수
	 */
	private String stabanalscore;
	/*
	 * 점수합계
	 */
	private String scoresum;
	/*
	 * 판정등급
	 */
	private String jdgmntgrad;
	/*
	 * 등급보정
	 */
	private String gradcoreton;
	/*
	 * 보정사유
	 */
	private String revisioncause;
	/*
	 * 사업가능여부
	 */
	private String bsposblat;
	/*
	 * 대책방안
	 */
	private String cntrplnmethod;
	/*
	 * 관리필요성
	 */
	private String mngncssty;
	/*
	 * 종합의견1
	 */
	private String opinion1;
	/*
	 * 종합의견2
	 */
	private String opinion2;
	/*
	 * 종합의견3
	 */
	private String opinion3;
	/*
	 * 종합의견4
	 */
	private String opinion4;
	/*
	 * 종합의견5
	 */
	private String opinion5;
	/*
	 * 종합의견6
	 */
	private String opinion6;
	/*
	 * 종합의견7
	 */
	private String opinion7;
	/*
	 * 종합의견8
	 */
	private String opinion8;
	/*
	 * 종합의견9
	 */
	private String opinion9;
	/*
	 * 종합의견10
	 */
	private String opinion10;
	/*
	 * 기준안전율
	 */
	private String stdsafefactor;
	/*
	 * 건기
	 */
	private String dryseason;
	/*
	 * 우기
	 */
	private String rainseason;
	/*
	 * 개소
	 */
	private String placelen;
	/*
	 * 이격거리
	 */
	private String sepdist;
	/*
	 * 토석류확산영향범위
	 */
	private String debfsetr;
	/*
	 * 위치도
	 */
	private String lcmap;
	/*
	 * 안정성검토결과이미지경로
	 */
	private String stablimg;
	/*
	 * 현황도
	 */
	private String statmap;
	/*
	 * 대피장소
	 */
	private String evamap;
	/*
	 * 피해이력
	 */
	private String dglog;
	/*
	 * 직접영향권내보호시설
	 */
	private String direffcsafe;
	/*
	 * 산사태위험등급현황
	 */
	private String lndslddgsttus;
	/*
	 * 뿌리특성
	 */
	private String rootchar;
	/*
	 * 산림현황
	 */
	private String foreststtus;
	/*
	 * 붕괴
	 */
	private String clps;
	/*
	 * 피해이력점수
	 */
	private String dglogscore;
	/*
	 * 직접영향권점수
	 */
	private String direffcscore;
	/*
	 * 토심점수
	 */
	private String soildepscore;
	/*
	 * 산사태위험등급점수
	 */
	private String lndslddgscore;
	/*
	 * 뿌리특성점수
	 */
	private String rootcharscore;
	/*
	 * 산림현황점수
	 */
	private String foreststtusscore;
	/*
	 * 붕괴점수
	 */
	private String clpsscore;
	/*
	 * 사면높이
	 */
	private String dirhg;
	/*
	 * 종단형상
	 */
	private String crossfrm;
	/*
	 * 암석종류
	 */
	private String rockknd;
	/*
	 * 균열상황(이완암상태)
	 */
	private String crcksittn;
	/*
	 * 용수상황
	 */
	private String watersttus;
	/*
	 * 붕괴지
	 */
	private String slidland;
	/*
	 * 불연속면방향
	 */
	private String surfacedir;
	/*
	 * 풍화상태
	 */
	private String wtrsttus;
	/*
	 * 경사도점수
	 */
	private String slopescore;
	/*
	 * 사면높이점수
	 */
	private String dirhgscore;
	/*
	 * 종단형상점수
	 */
	private String crossfrmscore;
	/*
	 * 암석종류점수
	 */
	private String rockkndscore;
	/*
	 * 균열상황점수
	 */
	private String crcksittnscore;
	/*
	 * 용수상황점수
	 */
	private String watersttusscore;
	/*
	 * 붕괴지점수
	 */
	private String sliplandscore;
	/*
	 * 불연속면점수
	 */
	private String surfacescore;
	/*
	 * 풍화상태점수
	 */
	private String wtrsttusscore;
	/*
	 * 침식
	 */
	private String corrosion;
	/*
	 * 전석
	 */
	private String bldrstne;
	/*
	 * 토석류흔적
	 */
	private String soiltrace;
	/*
	 * 기타위험요소
	 */
	private String etcdg;
	/*
	 * 유역면적점수
	 */
	private String dgrareascore;
	/*
	 * 계류경사점수
	 */
	private String mntntrntscore;
	/*
	 * 침식점수
	 */
	private String corrosionscore;
	/*
	 * 전석점수
	 */
	private String bldrstnescore;
	/*
	 * 토석류흔적점수
	 */
	private String soiltracescore;
	/*
	 * 기타위험요소점수
	 */
	private String etcdgscore;
	/*
	 * 사면상태
	 */
	private String dirsttus;
	/*
	 * 모암
	 */
	private String prntrock;
	/*
	 * 토성
	 */
	private String soilchar;
	/*
	 * 경사길이
	 */
	private String slopelen;
	/*
	 * 경사도
	 */
	private String slope;
	/*
	 * 경사도(최저)
	 */
	private String slopemin;
	/*
	 * 경사도(최대)
	 */
	private String slopemax;
	/*
	 * 능선
	 */
	private String ridge;
	/*
	 * 경사위치
	 */
	private String slopelc;
	/*
	 * 최고지점
	 */
	private String pntmax;
	/*
	 * 최저지점
	 */
	private String pntmin;
	/*
	 * 사면형
	 */
	private String dirfrm;
	/*
	 * 토심
	 */
	private String soildep;
	/*
	 * 균열
	 */
	private String crck;
	/*
	 * 함몰
	 */
	private String sink;
	/*
	 * 융기
	 */
	private String uplift;
	/*
	 * 말단부압출
	 */
	private String extdistalend;
	/*
	 * 확대예상
	 */
	private String expandpredic;
	/*
	 * 용수상시여부
	 */
	private String uswtrordtmat;
	/*
	 * 강우시용수
	 */
	private String rainfallwater;
	/*
	 * 사면이축축함
	 */
	private String dirwet;
	/*
	 * 사면이건조함
	 */
	private String dirdry;
	/*
	 * 황폐발생원
	 */
	private String scodsltn;
	/*
	 * 황폐발생원_점수
	 */
	private String scodsltnscore;
	/*
	 * 계류경사(최저)
	 */
	private String mntntrntmin;
	/*
	 * 계류경사(최고)
	 */
	private String mntntrntmax;
	/*
	 * 계류경사(평균)
	 */
	private String mntntrntavg;
	/*
	 * 계류길이
	 */
	private String mntntrntlen;
	/*
	 * 변곡점길이
	 */
	private String iftnpntlen;
	/*
	 * 변곡점고도
	 */
	private String iftnpntevtn;
	/*
	 * 종점부특이사항
	 */
	private String epntpartclr;
	/*
	 * 시점부특이사항
	 */
	private String spntpartclr;
	/*
	 * 월류상태
	 */
	private String ovrflwsttus;
	/*
	 * 계류상황
	 */
	private String mntntrntsittn;
	/*
	 * 곡류상태
	 */
	private String mdgfwsttus;
	/*
	 * 계류수
	 */
	private String mntntrntcnt;
	/*
	 * 유목길이
	 */
	private String lwdlen;
	/*
	 * 퇴적지
	 */
	private String sdtytopo;
	/*
	 * 좌안붕괴지
	 */
	private String lslmplnd;
	/*
	 * 우안붕괴지
	 */
	private String rslmplnd;
	/*
	 * 최단부위도
	 */
	private String shrtlat;
	/*
	 * 최단부경도
	 */
	private String shrtlon;
	/*
	 * 최단부위치
	 */
	private String shrtlc;
	/*
	 * 최단부고도
	 */
	private String shrtelev;
	/*
	 * 최단부토심
	 */
	private String shrtsoildep;
	/*
	 * 최단부폭
	 */
	private String shrtdm;
	/*
	 * 최단부경사
	 */
	private String shrtslp;
	/*
	 * 최단부암반
	 */
	private String shrtrock;
	/*
	 * 최단부전석
	 */
	private String shrtbldrstne;
	/*
	 * 최단부자갈
	 */
	private String shrtgravel;
	/*
	 * 최단부모래
	 */
	private String shrtsand;
	/*
	 * 최단부기타
	 */
	private String shrtetc;
	/*
	 * 중간부위도
	 */
	private String mdllat;
	/*
	 * 중간부경도
	 */
	private String mdllon;
	/*
	 * 중간부위치
	 */
	private String mdllc;
	/*
	 * 중간부고도
	 */
	private String mdlelev;
	/*
	 * 중간부토심
	 */
	private String mdlsoildep;
	/*
	 * 중간부폭
	 */
	private String mdldm;
	/*
	 * 중간부경사
	 */
	private String mdlslp;
	/*
	 * 중간부암반
	 */
	private String mdlrock;
	/*
	 * 중간부전석
	 */
	private String mdlbldrstne;
	/*
	 * 중간부자갈
	 */
	private String mdlgravel;
	/*
	 * 중간부모래
	 */
	private String mdlsand;
	/*
	 * 중간부기타
	 */
	private String mdletc;
	/*
	 * 최상부위도
	 */
	private String uplat;
	/*
	 * 최상부경도
	 */
	private String uplon;
	/*
	 * 최상부위치
	 */
	private String uplc;
	/*
	 * 최상부고도
	 */
	private String upelev;
	/*
	 * 최상부토심
	 */
	private String upsoildep;
	/*
	 * 최상부폭
	 */
	private String updm;
	/*
	 * 최상부경사
	 */
	private String upslp;
	/*
	 * 최상부암반
	 */
	private String uprock;
	/*
	 * 최상부전석
	 */
	private String upbldrstne;
	/*
	 * 최상부자갈
	 */
	private String upgravel;
	/*
	 * 최상부모래
	 */
	private String upsand;
	/*
	 * 최상부기타
	 */
	private String upetc;
	/*
	 * 임상
	 */
	private String frtp;
	/*
	 * 임분밀도
	 */
	private String stddnst;
	/*
	 * 임분경급
	 */
	private String stddmcls;
	/*
	 * 생성일
	 */
	private String creatDt;
	/*
	 * 수정일
	 */
	private String lastUpdtPnttm;
	/*
	 * 작성자ID
	 */
	private String loginId;
	
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
	 * 주위험요소 점수
	 */
	private String mainriskelemscore;
	
	/*
	 * 직적영향권 내 보호시설 점수
	 */
	private String direffcsafescore;
	
	/*
	 * 산사태위험등급 현황 점수
	 */
	private String lndslddgsttusscore;
	
	/*
	 * 붕괴지 점수
	 */
	private String slidlandscore;
	
	/*
	 * 불연속면 방향 점수
	 */
	private String surfacedirscore;
	
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
	 * 유역면적WKT
	 */
	private String dgrareaWkt;
	
	/*
	 * 사방댐WKT
	 */
	private String lndslddgWkt;
	
	/*
	 * 계류보전WKT
	 */
	private String mntntrntWkt;
	
	/*
	 * 산지사방WKT
	 */
	private String mtcecnrWkt;
	
	/*
	 * 대피쳬계WKT
	 */
	private String evasysWkt;
	
	/*
	 * 지적면적
	 */
	private String branchArea;
	
	/*
	 * 편입면적
	 */
	private String incorArea; 
	
	/*
	 * 지목
	 */
	private String svyJimok; 
	
	/*
	 * pnu코드
	 */
	private String pnuCode; 
	
	/*
	 * 계산WKT좌표
	 */
	private String smGeometry; 
	
	/*
	 * 소유구분
	 */
	private String posesnSe;
	
	/*
	 * 최상부 좌표
	 */
	private String uploc;
	
	/*
	 * 중간부 좌표
	 */
	private String mdlloc;
	
	/*
	 * 습윤단위중량
	 */
	private String wetunitwt;
	
	/*
	 * 점착력
	 */
	private String adheforce;
	
	/*
	 * 내부마찰각
	 */
	private String infricangle;
	
	/*
	 * 1회 토석류량
	 */
	private String onedebriswt;
	
	/*
	 * 정지조건
	 */
	private String stopcnd;
	
	/*
	 * 가중치
	 */
	private String wt;
	
	/*
	 * 전체 토석류량
	 */
	private String totdebriswt;
	
	/*
	 * 안정해석 이미지
	 */
	private String stabanalImg;
	
	/*
	 * 위치정보_X
	 */
	private String px;
	
	/*
	 * 위치정보_Y
	 */
	private String py;
	
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
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getSvyType() {
		return svyType;
	}
	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}
	public String getSvyId() {
		return svyId;
	}
	public void setSvyId(String svyId) {
		this.svyId = svyId;
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
	public String getNcssty() {
		return ncssty;
	}
	public void setNcssty(String ncssty) {
		this.ncssty = ncssty;
	}
	public String getMgc() {
		return mgc;
	}
	public void setMgc(String mgc) {
		this.mgc = mgc;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
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
	public String getSvydept() {
		return svydept;
	}
	public void setSvydept(String svydept) {
		this.svydept = svydept;
	}
	public String getSyvofcps() {
		return syvofcps;
	}
	public void setSyvofcps(String syvofcps) {
		this.syvofcps = syvofcps;
	}
	public String getSvyUser() {
		return svyUser;
	}
	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
	}
	public String getSvymbtl() {
		return svymbtl;
	}
	public void setSvymbtl(String svymbtl) {
		this.svymbtl = svymbtl;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getBscdt() {
		return bscdt;
	}
	public void setBscdt(String bscdt) {
		this.bscdt = bscdt;
	}
	public String getSvydt() {
		return svydt;
	}
	public void setSvydt(String svydt) {
		this.svydt = svydt;
	}
	public String getSvyuser() {
		return svyuser;
	}
	public void setSvyuser(String svyuser) {
		this.svyuser = svyuser;
	}
	public String getChkuser() {
		return chkuser;
	}
	public void setChkuser(String chkuser) {
		this.chkuser = chkuser;
	}
	public String getDgrarea() {
		return dgrarea;
	}
	public void setDgrarea(String dgrarea) {
		this.dgrarea = dgrarea;
	}
	public String getFrgltyrenarea() {
		return frgltyrenarea;
	}
	public void setFrgltyrenarea(String frgltyrenarea) {
		this.frgltyrenarea = frgltyrenarea;
	}
	public String getSafefct() {
		return safefct;
	}
	public void setSafefct(String safefct) {
		this.safefct = safefct;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getLake() {
		return lake;
	}
	public void setLake(String lake) {
		this.lake = lake;
	}
	public String getHighmainfct() {
		return highmainfct;
	}
	public void setHighmainfct(String highmainfct) {
		this.highmainfct = highmainfct;
	}
	public String getHighhousesttus() {
		return highhousesttus;
	}
	public void setHighhousesttus(String highhousesttus) {
		this.highhousesttus = highhousesttus;
	}
	public String getLowmainfct() {
		return lowmainfct;
	}
	public void setLowmainfct(String lowmainfct) {
		this.lowmainfct = lowmainfct;
	}
	public String getLowhousesttus() {
		return lowhousesttus;
	}
	public void setLowhousesttus(String lowhousesttus) {
		this.lowhousesttus = lowhousesttus;
	}
	public String getNcsstyworktype() {
		return ncsstyworktype;
	}
	public void setNcsstyworktype(String ncsstyworktype) {
		this.ncsstyworktype = ncsstyworktype;
	}
	public String getElevation() {
		return elevation;
	}
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}
	public String getFieldsurveyscore() {
		return fieldsurveyscore;
	}
	public void setFieldsurveyscore(String fieldsurveyscore) {
		this.fieldsurveyscore = fieldsurveyscore;
	}
	public String getStabanalscore() {
		return stabanalscore;
	}
	public void setStabanalscore(String stabanalscore) {
		this.stabanalscore = stabanalscore;
	}
	public String getScoresum() {
		return scoresum;
	}
	public void setScoresum(String scoresum) {
		this.scoresum = scoresum;
	}
	public String getJdgmntgrad() {
		return jdgmntgrad;
	}
	public void setJdgmntgrad(String jdgmntgrad) {
		this.jdgmntgrad = jdgmntgrad;
	}
	public String getGradcoreton() {
		return gradcoreton;
	}
	public void setGradcoreton(String gradcoreton) {
		this.gradcoreton = gradcoreton;
	}
	public String getRevisioncause() {
		return revisioncause;
	}
	public void setRevisioncause(String revisioncause) {
		this.revisioncause = revisioncause;
	}
	public String getBsposblat() {
		return bsposblat;
	}
	public void setBsposblat(String bsposblat) {
		this.bsposblat = bsposblat;
	}
	public String getCntrplnmethod() {
		return cntrplnmethod;
	}
	public void setCntrplnmethod(String cntrplnmethod) {
		this.cntrplnmethod = cntrplnmethod;
	}
	public String getMngncssty() {
		return mngncssty;
	}
	public void setMngncssty(String mngncssty) {
		this.mngncssty = mngncssty;
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
	public String getOpinion6() {
		return opinion6;
	}
	public void setOpinion6(String opinion6) {
		this.opinion6 = opinion6;
	}
	public String getOpinion7() {
		return opinion7;
	}
	public void setOpinion7(String opinion7) {
		this.opinion7 = opinion7;
	}
	public String getOpinion8() {
		return opinion8;
	}
	public void setOpinion8(String opinion8) {
		this.opinion8 = opinion8;
	}
	public String getOpinion9() {
		return opinion9;
	}
	public void setOpinion9(String opinion9) {
		this.opinion9 = opinion9;
	}
	public String getOpinion10() {
		return opinion10;
	}
	public void setOpinion10(String opinion10) {
		this.opinion10 = opinion10;
	}
	public String getStdsafefactor() {
		return stdsafefactor;
	}
	public void setStdsafefactor(String stdsafefactor) {
		this.stdsafefactor = stdsafefactor;
	}
	public String getDryseason() {
		return dryseason;
	}
	public void setDryseason(String dryseason) {
		this.dryseason = dryseason;
	}
	public String getRainseason() {
		return rainseason;
	}
	public void setRainseason(String rainseason) {
		this.rainseason = rainseason;
	}
	public String getPlacelen() {
		return placelen;
	}
	public void setPlacelen(String placelen) {
		this.placelen = placelen;
	}
	public String getSepdist() {
		return sepdist;
	}
	public void setSepdist(String sepdist) {
		this.sepdist = sepdist;
	}
	public String getDebfsetr() {
		return debfsetr;
	}
	public void setDebfsetr(String debfsetr) {
		this.debfsetr = debfsetr;
	}
	public String getLcmap() {
		return lcmap;
	}
	public void setLcmap(String lcmap) {
		this.lcmap = lcmap;
	}
	public String getStablimg() {
		return stablimg;
	}
	public void setStablimg(String stablimg) {
		this.stablimg = stablimg;
	}
	public String getStatmap() {
		return statmap;
	}
	public void setStatmap(String statmap) {
		this.statmap = statmap;
	}
	public String getEvamap() {
		return evamap;
	}
	public void setEvamap(String evamap) {
		this.evamap = evamap;
	}
	public String getDglog() {
		return dglog;
	}
	public void setDglog(String dglog) {
		this.dglog = dglog;
	}
	public String getDireffcsafe() {
		return direffcsafe;
	}
	public void setDireffcsafe(String direffcsafe) {
		this.direffcsafe = direffcsafe;
	}
	public String getLndslddgsttus() {
		return lndslddgsttus;
	}
	public void setLndslddgsttus(String lndslddgsttus) {
		this.lndslddgsttus = lndslddgsttus;
	}
	public String getRootchar() {
		return rootchar;
	}
	public void setRootchar(String rootchar) {
		this.rootchar = rootchar;
	}
	public String getForeststtus() {
		return foreststtus;
	}
	public void setForeststtus(String foreststtus) {
		this.foreststtus = foreststtus;
	}
	public String getClps() {
		return clps;
	}
	public void setClps(String clps) {
		this.clps = clps;
	}
	public String getDglogscore() {
		return dglogscore;
	}
	public void setDglogscore(String dglogscore) {
		this.dglogscore = dglogscore;
	}
	public String getDireffcscore() {
		return direffcscore;
	}
	public void setDireffcscore(String direffcscore) {
		this.direffcscore = direffcscore;
	}
	public String getSoildepscore() {
		return soildepscore;
	}
	public void setSoildepscore(String soildepscore) {
		this.soildepscore = soildepscore;
	}
	public String getLndslddgscore() {
		return lndslddgscore;
	}
	public void setLndslddgscore(String lndslddgscore) {
		this.lndslddgscore = lndslddgscore;
	}
	public String getRootcharscore() {
		return rootcharscore;
	}
	public void setRootcharscore(String rootcharscore) {
		this.rootcharscore = rootcharscore;
	}
	public String getForeststtusscore() {
		return foreststtusscore;
	}
	public void setForeststtusscore(String foreststtusscore) {
		this.foreststtusscore = foreststtusscore;
	}
	public String getClpsscore() {
		return clpsscore;
	}
	public void setClpsscore(String clpsscore) {
		this.clpsscore = clpsscore;
	}
	public String getDirhg() {
		return dirhg;
	}
	public void setDirhg(String dirhg) {
		this.dirhg = dirhg;
	}
	public String getCrossfrm() {
		return crossfrm;
	}
	public void setCrossfrm(String crossfrm) {
		this.crossfrm = crossfrm;
	}
	public String getRockknd() {
		return rockknd;
	}
	public void setRockknd(String rockknd) {
		this.rockknd = rockknd;
	}
	public String getCrcksittn() {
		return crcksittn;
	}
	public void setCrcksittn(String crcksittn) {
		this.crcksittn = crcksittn;
	}
	public String getWatersttus() {
		return watersttus;
	}
	public void setWatersttus(String watersttus) {
		this.watersttus = watersttus;
	}
	public String getSlidland() {
		return slidland;
	}
	public void setSlidland(String slidland) {
		this.slidland = slidland;
	}
	public String getSurfacedir() {
		return surfacedir;
	}
	public void setSurfacedir(String surfacedir) {
		this.surfacedir = surfacedir;
	}
	public String getWtrsttus() {
		return wtrsttus;
	}
	public void setWtrsttus(String wtrsttus) {
		this.wtrsttus = wtrsttus;
	}
	public String getSlopescore() {
		return slopescore;
	}
	public void setSlopescore(String slopescore) {
		this.slopescore = slopescore;
	}
	public String getDirhgscore() {
		return dirhgscore;
	}
	public void setDirhgscore(String dirhgscore) {
		this.dirhgscore = dirhgscore;
	}
	public String getCrossfrmscore() {
		return crossfrmscore;
	}
	public void setCrossfrmscore(String crossfrmscore) {
		this.crossfrmscore = crossfrmscore;
	}
	public String getRockkndscore() {
		return rockkndscore;
	}
	public void setRockkndscore(String rockkndscore) {
		this.rockkndscore = rockkndscore;
	}
	public String getCrcksittnscore() {
		return crcksittnscore;
	}
	public void setCrcksittnscore(String crcksittnscore) {
		this.crcksittnscore = crcksittnscore;
	}
	public String getWatersttusscore() {
		return watersttusscore;
	}
	public void setWatersttusscore(String watersttusscore) {
		this.watersttusscore = watersttusscore;
	}
	public String getSliplandscore() {
		return sliplandscore;
	}
	public void setSliplandscore(String sliplandscore) {
		this.sliplandscore = sliplandscore;
	}
	public String getSurfacescore() {
		return surfacescore;
	}
	public void setSurfacescore(String surfacescore) {
		this.surfacescore = surfacescore;
	}
	public String getWtrsttusscore() {
		return wtrsttusscore;
	}
	public void setWtrsttusscore(String wtrsttusscore) {
		this.wtrsttusscore = wtrsttusscore;
	}
	public String getCorrosion() {
		return corrosion;
	}
	public void setCorrosion(String corrosion) {
		this.corrosion = corrosion;
	}
	public String getBldrstne() {
		return bldrstne;
	}
	public void setBldrstne(String bldrstne) {
		this.bldrstne = bldrstne;
	}
	public String getSoiltrace() {
		return soiltrace;
	}
	public void setSoiltrace(String soiltrace) {
		this.soiltrace = soiltrace;
	}
	public String getEtcdg() {
		return etcdg;
	}
	public void setEtcdg(String etcdg) {
		this.etcdg = etcdg;
	}
	public String getDgrareascore() {
		return dgrareascore;
	}
	public void setDgrareascore(String dgrareascore) {
		this.dgrareascore = dgrareascore;
	}
	public String getMntntrntscore() {
		return mntntrntscore;
	}
	public void setMntntrntscore(String mntntrntscore) {
		this.mntntrntscore = mntntrntscore;
	}
	public String getCorrosionscore() {
		return corrosionscore;
	}
	public void setCorrosionscore(String corrosionscore) {
		this.corrosionscore = corrosionscore;
	}
	public String getBldrstnescore() {
		return bldrstnescore;
	}
	public void setBldrstnescore(String bldrstnescore) {
		this.bldrstnescore = bldrstnescore;
	}
	public String getSoiltracescore() {
		return soiltracescore;
	}
	public void setSoiltracescore(String soiltracescore) {
		this.soiltracescore = soiltracescore;
	}
	public String getEtcdgscore() {
		return etcdgscore;
	}
	public void setEtcdgscore(String etcdgscore) {
		this.etcdgscore = etcdgscore;
	}
	public String getDirsttus() {
		return dirsttus;
	}
	public void setDirsttus(String dirsttus) {
		this.dirsttus = dirsttus;
	}
	public String getPrntrock() {
		return prntrock;
	}
	public void setPrntrock(String prntrock) {
		this.prntrock = prntrock;
	}
	public String getSoilchar() {
		return soilchar;
	}
	public void setSoilchar(String soilchar) {
		this.soilchar = soilchar;
	}
	public String getSlopelen() {
		return slopelen;
	}
	public void setSlopelen(String slopelen) {
		this.slopelen = slopelen;
	}
	public String getSlope() {
		return slope;
	}
	public void setSlope(String slope) {
		this.slope = slope;
	}
	public String getSlopemin() {
		return slopemin;
	}
	public void setSlopemin(String slopemin) {
		this.slopemin = slopemin;
	}
	public String getSlopemax() {
		return slopemax;
	}
	public void setSlopemax(String slopemax) {
		this.slopemax = slopemax;
	}
	public String getRidge() {
		return ridge;
	}
	public void setRidge(String ridge) {
		this.ridge = ridge;
	}
	public String getSlopelc() {
		return slopelc;
	}
	public void setSlopelc(String slopelc) {
		this.slopelc = slopelc;
	}
	public String getPntmax() {
		return pntmax;
	}
	public void setPntmax(String pntmax) {
		this.pntmax = pntmax;
	}
	public String getPntmin() {
		return pntmin;
	}
	public void setPntmin(String pntmin) {
		this.pntmin = pntmin;
	}
	public String getDirfrm() {
		return dirfrm;
	}
	public void setDirfrm(String dirfrm) {
		this.dirfrm = dirfrm;
	}
	public String getSoildep() {
		return soildep;
	}
	public void setSoildep(String soildep) {
		this.soildep = soildep;
	}
	public String getCrck() {
		return crck;
	}
	public void setCrck(String crck) {
		this.crck = crck;
	}
	public String getSink() {
		return sink;
	}
	public void setSink(String sink) {
		this.sink = sink;
	}
	public String getUplift() {
		return uplift;
	}
	public void setUplift(String uplift) {
		this.uplift = uplift;
	}
	public String getExtdistalend() {
		return extdistalend;
	}
	public void setExtdistalend(String extdistalend) {
		this.extdistalend = extdistalend;
	}
	public String getExpandpredic() {
		return expandpredic;
	}
	public void setExpandpredic(String expandpredic) {
		this.expandpredic = expandpredic;
	}
	public String getUswtrordtmat() {
		return uswtrordtmat;
	}
	public void setUswtrordtmat(String uswtrordtmat) {
		this.uswtrordtmat = uswtrordtmat;
	}
	public String getRainfallwater() {
		return rainfallwater;
	}
	public void setRainfallwater(String rainfallwater) {
		this.rainfallwater = rainfallwater;
	}
	public String getDirwet() {
		return dirwet;
	}
	public void setDirwet(String dirwet) {
		this.dirwet = dirwet;
	}
	public String getDirdry() {
		return dirdry;
	}
	public void setDirdry(String dirdry) {
		this.dirdry = dirdry;
	}
	public String getScodsltn() {
		return scodsltn;
	}
	public void setScodsltn(String scodsltn) {
		this.scodsltn = scodsltn;
	}
	public String getScodsltnscore() {
		return scodsltnscore;
	}
	public void setScodsltnscore(String scodsltnscore) {
		this.scodsltnscore = scodsltnscore;
	}
	public String getMntntrntmin() {
		return mntntrntmin;
	}
	public void setMntntrntmin(String mntntrntmin) {
		this.mntntrntmin = mntntrntmin;
	}
	public String getMntntrntmax() {
		return mntntrntmax;
	}
	public void setMntntrntmax(String mntntrntmax) {
		this.mntntrntmax = mntntrntmax;
	}
	public String getMntntrntavg() {
		return mntntrntavg;
	}
	public void setMntntrntavg(String mntntrntavg) {
		this.mntntrntavg = mntntrntavg;
	}
	public String getMntntrntlen() {
		return mntntrntlen;
	}
	public void setMntntrntlen(String mntntrntlen) {
		this.mntntrntlen = mntntrntlen;
	}
	public String getIftnpntlen() {
		return iftnpntlen;
	}
	public void setIftnpntlen(String iftnpntlen) {
		this.iftnpntlen = iftnpntlen;
	}
	public String getIftnpntevtn() {
		return iftnpntevtn;
	}
	public void setIftnpntevtn(String iftnpntevtn) {
		this.iftnpntevtn = iftnpntevtn;
	}
	public String getEpntpartclr() {
		return epntpartclr;
	}
	public void setEpntpartclr(String epntpartclr) {
		this.epntpartclr = epntpartclr;
	}
	public String getSpntpartclr() {
		return spntpartclr;
	}
	public void setSpntpartclr(String spntpartclr) {
		this.spntpartclr = spntpartclr;
	}
	public String getOvrflwsttus() {
		return ovrflwsttus;
	}
	public void setOvrflwsttus(String ovrflwsttus) {
		this.ovrflwsttus = ovrflwsttus;
	}
	public String getMntntrntsittn() {
		return mntntrntsittn;
	}
	public void setMntntrntsittn(String mntntrntsittn) {
		this.mntntrntsittn = mntntrntsittn;
	}
	public String getMdgfwsttus() {
		return mdgfwsttus;
	}
	public void setMdgfwsttus(String mdgfwsttus) {
		this.mdgfwsttus = mdgfwsttus;
	}
	public String getMntntrntcnt() {
		return mntntrntcnt;
	}
	public void setMntntrntcnt(String mntntrntcnt) {
		this.mntntrntcnt = mntntrntcnt;
	}
	public String getLwdlen() {
		return lwdlen;
	}
	public void setLwdlen(String lwdlen) {
		this.lwdlen = lwdlen;
	}
	public String getSdtytopo() {
		return sdtytopo;
	}
	public void setSdtytopo(String sdtytopo) {
		this.sdtytopo = sdtytopo;
	}
	public String getLslmplnd() {
		return lslmplnd;
	}
	public void setLslmplnd(String lslmplnd) {
		this.lslmplnd = lslmplnd;
	}
	public String getRslmplnd() {
		return rslmplnd;
	}
	public void setRslmplnd(String rslmplnd) {
		this.rslmplnd = rslmplnd;
	}
	public String getShrtlat() {
		return shrtlat;
	}
	public void setShrtlat(String shrtlat) {
		this.shrtlat = shrtlat;
	}
	public String getShrtlon() {
		return shrtlon;
	}
	public void setShrtlon(String shrtlon) {
		this.shrtlon = shrtlon;
	}
	public String getShrtlc() {
		return shrtlc;
	}
	public void setShrtlc(String shrtlc) {
		this.shrtlc = shrtlc;
	}
	public String getShrtelev() {
		return shrtelev;
	}
	public void setShrtelev(String shrtelev) {
		this.shrtelev = shrtelev;
	}
	public String getShrtsoildep() {
		return shrtsoildep;
	}
	public void setShrtsoildep(String shrtsoildep) {
		this.shrtsoildep = shrtsoildep;
	}
	public String getShrtdm() {
		return shrtdm;
	}
	public void setShrtdm(String shrtdm) {
		this.shrtdm = shrtdm;
	}
	public String getShrtslp() {
		return shrtslp;
	}
	public void setShrtslp(String shrtslp) {
		this.shrtslp = shrtslp;
	}
	public String getShrtrock() {
		return shrtrock;
	}
	public void setShrtrock(String shrtrock) {
		this.shrtrock = shrtrock;
	}
	public String getShrtbldrstne() {
		return shrtbldrstne;
	}
	public void setShrtbldrstne(String shrtbldrstne) {
		this.shrtbldrstne = shrtbldrstne;
	}
	public String getShrtgravel() {
		return shrtgravel;
	}
	public void setShrtgravel(String shrtgravel) {
		this.shrtgravel = shrtgravel;
	}
	public String getShrtsand() {
		return shrtsand;
	}
	public void setShrtsand(String shrtsand) {
		this.shrtsand = shrtsand;
	}
	public String getShrtetc() {
		return shrtetc;
	}
	public void setShrtetc(String shrtetc) {
		this.shrtetc = shrtetc;
	}
	public String getMdllat() {
		return mdllat;
	}
	public void setMdllat(String mdllat) {
		this.mdllat = mdllat;
	}
	public String getMdllon() {
		return mdllon;
	}
	public void setMdllon(String mdllon) {
		this.mdllon = mdllon;
	}
	public String getMdllc() {
		return mdllc;
	}
	public void setMdllc(String mdllc) {
		this.mdllc = mdllc;
	}
	public String getMdlelev() {
		return mdlelev;
	}
	public void setMdlelev(String mdlelev) {
		this.mdlelev = mdlelev;
	}
	public String getMdlsoildep() {
		return mdlsoildep;
	}
	public void setMdlsoildep(String mdlsoildep) {
		this.mdlsoildep = mdlsoildep;
	}
	public String getMdldm() {
		return mdldm;
	}
	public void setMdldm(String mdldm) {
		this.mdldm = mdldm;
	}
	public String getMdlslp() {
		return mdlslp;
	}
	public void setMdlslp(String mdlslp) {
		this.mdlslp = mdlslp;
	}
	public String getMdlrock() {
		return mdlrock;
	}
	public void setMdlrock(String mdlrock) {
		this.mdlrock = mdlrock;
	}
	public String getMdlbldrstne() {
		return mdlbldrstne;
	}
	public void setMdlbldrstne(String mdlbldrstne) {
		this.mdlbldrstne = mdlbldrstne;
	}
	public String getMdlgravel() {
		return mdlgravel;
	}
	public void setMdlgravel(String mdlgravel) {
		this.mdlgravel = mdlgravel;
	}
	public String getMdlsand() {
		return mdlsand;
	}
	public void setMdlsand(String mdlsand) {
		this.mdlsand = mdlsand;
	}
	public String getMdletc() {
		return mdletc;
	}
	public void setMdletc(String mdletc) {
		this.mdletc = mdletc;
	}
	public String getUplat() {
		return uplat;
	}
	public void setUplat(String uplat) {
		this.uplat = uplat;
	}
	public String getUplon() {
		return uplon;
	}
	public void setUplon(String uplon) {
		this.uplon = uplon;
	}
	public String getUplc() {
		return uplc;
	}
	public void setUplc(String uplc) {
		this.uplc = uplc;
	}
	public String getUpelev() {
		return upelev;
	}
	public void setUpelev(String upelev) {
		this.upelev = upelev;
	}
	public String getUpsoildep() {
		return upsoildep;
	}
	public void setUpsoildep(String upsoildep) {
		this.upsoildep = upsoildep;
	}
	public String getUpdm() {
		return updm;
	}
	public void setUpdm(String updm) {
		this.updm = updm;
	}
	public String getUpslp() {
		return upslp;
	}
	public void setUpslp(String upslp) {
		this.upslp = upslp;
	}
	public String getUprock() {
		return uprock;
	}
	public void setUprock(String uprock) {
		this.uprock = uprock;
	}
	public String getUpbldrstne() {
		return upbldrstne;
	}
	public void setUpbldrstne(String upbldrstne) {
		this.upbldrstne = upbldrstne;
	}
	public String getUpgravel() {
		return upgravel;
	}
	public void setUpgravel(String upgravel) {
		this.upgravel = upgravel;
	}
	public String getUpsand() {
		return upsand;
	}
	public void setUpsand(String upsand) {
		this.upsand = upsand;
	}
	public String getUpetc() {
		return upetc;
	}
	public void setUpetc(String upetc) {
		this.upetc = upetc;
	}
	public String getFrtp() {
		return frtp;
	}
	public void setFrtp(String frtp) {
		this.frtp = frtp;
	}
	public String getStddnst() {
		return stddnst;
	}
	public void setStddnst(String stddnst) {
		this.stddnst = stddnst;
	}
	public String getStddmcls() {
		return stddmcls;
	}
	public void setStddmcls(String stddmcls) {
		this.stddmcls = stddmcls;
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
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getMainriskelemscore() {
		return mainriskelemscore;
	}
	public void setMainriskelemscore(String mainriskelemscore) {
		this.mainriskelemscore = mainriskelemscore;
	}
	public String getDireffcsafescore() {
		return direffcsafescore;
	}
	public void setDireffcsafescore(String direffcsafescore) {
		this.direffcsafescore = direffcsafescore;
	}
	public String getLndslddgsttusscore() {
		return lndslddgsttusscore;
	}
	public void setLndslddgsttusscore(String lndslddgsttusscore) {
		this.lndslddgsttusscore = lndslddgsttusscore;
	}
	public String getSlidlandscore() {
		return slidlandscore;
	}
	public void setSlidlandscore(String slidlandscore) {
		this.slidlandscore = slidlandscore;
	}
	public String getSurfacedirscore() {
		return surfacedirscore;
	}
	public void setSurfacedirscore(String surfacedirscore) {
		this.surfacedirscore = surfacedirscore;
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
	public String getDgrareaWkt() {
		return dgrareaWkt;
	}
	public void setDgrareaWkt(String dgrareaWkt) {
		this.dgrareaWkt = dgrareaWkt;
	}
	public String getLndslddgWkt() {
		return lndslddgWkt;
	}
	public void setLndslddgWkt(String lndslddgWkt) {
		this.lndslddgWkt = lndslddgWkt;
	}
	public String getMntntrntWkt() {
		return mntntrntWkt;
	}
	public void setMntntrntWkt(String mntntrntWkt) {
		this.mntntrntWkt = mntntrntWkt;
	}
	public String getMtcecnrWkt() {
		return mtcecnrWkt;
	}
	public void setMtcecnrWkt(String mtcecnrWkt) {
		this.mtcecnrWkt = mtcecnrWkt;
	}
	public String getEvasysWkt() {
		return evasysWkt;
	}
	public void setEvasysWkt(String evasysWkt) {
		this.evasysWkt = evasysWkt;
	}
	public String getBranchArea() {
		return branchArea;
	}
	public void setBranchArea(String branchArea) {
		this.branchArea = branchArea;
	}
	public String getIncorArea() {
		return incorArea;
	}
	public void setIncorArea(String incorArea) {
		this.incorArea = incorArea;
	}
	public String getSvyJimok() {
		return svyJimok;
	}
	public void setSvyJimok(String svyJimok) {
		this.svyJimok = svyJimok;
	}
	public String getPnuCode() {
		return pnuCode;
	}
	public void setPnuCode(String pnuCode) {
		this.pnuCode = pnuCode;
	}
	public String getSmGeometry() {
		return smGeometry;
	}
	public void setSmGeometry(String smGeometry) {
		this.smGeometry = smGeometry;
	}
	public String getPosesnSe() {
		return posesnSe;
	}
	public void setPosesnSe(String posesnSe) {
		this.posesnSe = posesnSe;
	}
	public String getUploc() {
		return uploc;
	}
	public void setUploc(String uploc) {
		this.uploc = uploc;
	}
	public String getMdlloc() {
		return mdlloc;
	}
	public void setMdlloc(String mdlloc) {
		this.mdlloc = mdlloc;
	}
	public String getWetunitwt() {
		return wetunitwt;
	}
	public void setWetunitwt(String wetunitwt) {
		this.wetunitwt = wetunitwt;
	}
	public String getAdheforce() {
		return adheforce;
	}
	public void setAdheforce(String adheforce) {
		this.adheforce = adheforce;
	}
	public String getInfricangle() {
		return infricangle;
	}
	public void setInfricangle(String infricangle) {
		this.infricangle = infricangle;
	}
	public String getOnedebriswt() {
		return onedebriswt;
	}
	public void setOnedebriswt(String onedebriswt) {
		this.onedebriswt = onedebriswt;
	}
	public String getStopcnd() {
		return stopcnd;
	}
	public void setStopcnd(String stopcnd) {
		this.stopcnd = stopcnd;
	}
	public String getWt() {
		return wt;
	}
	public void setWt(String wt) {
		this.wt = wt;
	}
	public String getTotdebriswt() {
		return totdebriswt;
	}
	public void setTotdebriswt(String totdebriswt) {
		this.totdebriswt = totdebriswt;
	}
	public String getStabanalImg() {
		return stabanalImg;
	}
	public void setStabanalImg(String stabanalImg) {
		this.stabanalImg = stabanalImg;
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
}
