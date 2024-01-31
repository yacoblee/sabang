package or.sabang.sys.lss.lcp.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssLcpSvyStripLandVO extends ComDefaultVO {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 공간정보 고유ID
	 **/
	private String smid;
	/**
	 * 고유ID
	 */
	private String uniqId;
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
	 * 관할2
	 */
	private String region2;
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
	 * 위도
	 */
	private String lat;
	/**
	 * 경도
	 */
	private String lon;
	/**
	 * 등록일자
	 */
	private String createDt;
	/**
	 * 작성자
	 */
	private String writer;
	/**
	 * 첨부파일 갯수
	 */
	private String cnt;
	/**
	 * GEOM
	 */
	private String geom;
	/**
	 * GEOM
	 */
	private String at;
	/**
	 * 시도코드 리스트
	 */
	private String sdCdList;
	/**
	 * 시도별 건수 리스트
	 */
	private String  sdCntList;
	/**
	 * 시도별 총 건수
	 */
	private String sdTotalCnt;
	/**
	 * 시도코드
	 */
	private String sdCd;	
	/**
	 * 시도별 건수 리스트
	 */
	private String sdCnt;	
	/**
	 * 라벨
	 */
	private String label;
	/**
	 * 라벨번호
	 */
	private String labelNum;	
	/**
	 * 고유 아이디
	 */
	private String gid;
	/**
	 * 조사대상지방 아이디
	 */
	private String sldId;
	/**
	 * 조사대상지방 명칭
	 */
	private String svysldNm;
	/**
	 * 랭크번호
	 */
	private String rank;
	/**
	 * 주소
	 */
	private String addr;
	/**
	 * 시도코드
	 */
	private String ctrdCd;
	/**
	 * 읍면동코드
	 */
	private String stdgCd;	
	/**
	 * 임상
	 */
	private String frtpCd;
	/**
	 * 맵 라벨
	 */
	private String mapLabel;
	/**
	 * 면적
	 */
	private String lcpArea;
	/**
	 * 편입면적
	 */
	private String frtpArea;
	/**
	 * 지질(대)
	 */
	private String prrckLarg;
	/**
	 * 지질(중)
	 */
	private String prrckMddl;
	/**
	 * 평균토심
	 */
	private String sldpAvg;
	/**
	 * 최소토심
	 */
	private String sldpMin;
	/**
	 * 최대토심
	 */
	private String sldpMax;
	/**
	 * 평균고도
	 */
	private String evtAvg;
	/**
	 * 최소고도
	 */
	private String evtMin;
	/**
	 * 최대고도
	 */
	private String evtMax;
	/**
	 * 평균토심
	 */
	private String slpAvg;
	/**
	 * 최소토심
	 */
	private String slpMin;
	/**
	 * 최대토심
	 */
	private String slpMax;
	/**
	 * 토양형
	 */
	private String sltpCd;
	/**
	 * 토성
	 */
	private String sibflrScs;
	/**
	 * 토양구조
	 */
	private String sibflrStr;
	/**
	 * 암노출도
	 */
	private String rockExdgr;
	/**
	 * 토양석력함량
	 */
	private String sibflrCbs;
	/**
	 * 풍화정도
	 */
	private String wteffDgr;
	/**
	 * id attribute 값을  리턴한다.
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * id attribute 값을 설정한다.
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * type attribute 값을  리턴한다.
	 * @return String
	 */
	public String getType() {
		return type;
	}
	/**
	 * type attribute 값을 설정한다.
	 * @param type String
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * year attribute 값을  리턴한다.
	 * @return String
	 */
	public String getYear() {
		return year;
	}
	/**
	 * year attribute 값을 설정한다.
	 * @param year String
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * region1 attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRegion1() {
		return region1;
	}
	/**
	 * region1 attribute 값을 설정한다.
	 * @param region1 String
	 */
	public void setRegion1(String region1) {
		this.region1 = region1;
	}
	/**
	 * region2 attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRegion2() {
		return region2;
	}
	/**
	 * region2 attribute 값을 설정한다.
	 * @param region2 String
	 */
	public void setRegion2(String region2) {
		this.region2 = region2;
	}
	/**
	 * sd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSd() {
		return sd;
	}
	/**
	 * sd attribute 값을 설정한다.
	 * @param sd String
	 */
	public void setSd(String sd) {
		this.sd = sd;
	}
	/**
	 * sgg attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSgg() {
		return sgg;
	}
	/**
	 * sgg attribute 값을 설정한다.
	 * @param sgg String
	 */
	public void setSgg(String sgg) {
		this.sgg = sgg;
	}
	/**
	 * emd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getEmd() {
		return emd;
	}
	/**
	 * emd attribute 값을 설정한다.
	 * @param emd String
	 */
	public void setEmd(String emd) {
		this.emd = emd;
	}
	/**
	 * ri attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRi() {
		return ri;
	}
	/**
	 * ri attribute 값을 설정한다.
	 * @param ri String
	 */
	public void setRi(String ri) {
		this.ri = ri;
	}
	/**
	 * jibun attribute 값을  리턴한다.
	 * @return String
	 */
	public String getJibun() {
		return jibun;
	}
	/**
	 * jibun attribute 값을 설정한다.
	 * @param jibun String
	 */
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	/**
	 * lat attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * lat attribute 값을 설정한다.
	 * @param lat String
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * lon attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLon() {
		return lon;
	}
	/**
	 * lon attribute 값을 설정한다.
	 * @param lon String
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}
	/**
	 * createDt attribute 값을 리턴한다.
	 * @return
	 */
	public String getCreateDt() {
		return createDt;
	}
	/**
	 * createDt attribut 값을 설정한다.
	 * @param createDt
	 */
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	/**
	 * writer attribute 값을 리턴한다.
	 * @return
	 */
	public String getWriter() {
		return writer;
	}
	/**
	 * writer attribut 값을 설정한다.
	 * @param createDt
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	/**
	 * cnt attribute 값을 리턴한다.
	 * @return
	 */
	public String getCnt() {
		return cnt;
	}
	/**
	 * cnt attribute 값을 설정한다.
	 * @return
	 */
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	/**
	 * geom attribute 값을 리턴한다.
	 * @return
	 */
	public String getGeom() {
		return geom;
	}
	/**
	 * geom attribute 값을 설정한다.
	 * @return
	 */
	public void setGeom(String geom) {
		this.geom = geom;
	}
	/**
	 * at attribute 값을 리턴한다.
	 * @return
	 */
	public String getAt() {
		return at;
	}
	/**
	 * at attribute 값을 설정한다.
	 * @return
	 */
	public void setAt(String at) {
		this.at = at;
	}
	/**
	 * sdCdList attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSdCdList() {
		return sdCdList;
	}
	/**
	 * sdCdList attribute 값을 설정한다.
	 * @param sdCdList String
	 */
	public void setSdCdList(String sdCdList) {
		this.sdCdList = sdCdList;
	}
	/**
	 * sdCntList attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSdCntList() {
		return sdCntList;
	}
	/**
	 * sdCntList attribute 값을 설정한다.
	 * @param sdCntList String
	 */
	public void setSdCntList(String sdCntList) {
		this.sdCntList = sdCntList;
	}
	/**
	 * sdTotalCnt attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSdTotalCnt() {
		return sdTotalCnt;
	}
	/**
	 * sdTotalCnt attribute 값을 설정한다.
	 * @param sdTotalCnt String
	 */
	public void setSdTotalCnt(String sdTotalCnt) {
		this.sdTotalCnt = sdTotalCnt;
	}
	/**
	 * sdCd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSdCd() {
		return sdCd;
	}
	/**
	 * sdCd attribute 값을 설정한다.
	 * @param sdCd String
	 */
	public void setSdCd(String sdCd) {
		this.sdCd = sdCd;
	}
	/**
	 * sdCnt attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSdCnt() {
		return sdCnt;
	}
	/**
	 * sdCnt attribute 값을 설정한다.
	 * @param sdCntList String
	 */
	public void setSdCnt(String sdCnt) {
		this.sdCnt = sdCnt;
	}
	/**
	 * label attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * label attribute 값을 설정한다.
	 * @param label String
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabelNum() {
		return labelNum;
	}
	public void setLabelNum(String labelNum) {
		this.labelNum = labelNum;
	}
	/**
	 * gid attribute 값을  리턴한다.
	 * @return String
	 */
	public String getGid() {
		return gid;
	}
	/**
	 * gid attribute 값을 설정한다.
	 * @param gid String
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}
	/**
	 * sldId attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSldId() {
		return sldId;
	}
	/**
	 * sldId attribute 값을 설정한다.
	 * @param sldId String
	 */
	public void setSldId(String sldId) {
		this.sldId = sldId;
	}
	/**
	 * svysldNm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSvysldNm() {
		return svysldNm;
	}
	/**
	 * svysldNm attribute 값을 설정한다.
	 * @param svysldNm String
	 */
	public void setSvysldNm(String svysldNm) {
		this.svysldNm = svysldNm;
	}
	/**
	 * rank attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRank() {
		return rank;
	}
	/**
	 * rank attribute 값을 설정한다.
	 * @param rank String
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	/**
	 * addr attribute 값을  리턴한다.
	 * @return String
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * addr attribute 값을 설정한다.
	 * @param addr String
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * stdgCd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getCtrdCd() {
		return ctrdCd;
	}
	/**
	 * ctrdCd attribute 값을 설정한다.
	 * @param ctrdCd String
	 */
	public void setCtrdCd(String ctrdCd) {
		this.ctrdCd = ctrdCd;
	}
	/**
	 * stdgCd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getStdgCd() {
		return stdgCd;
	}
	/**
	 * stdgCd attribute 값을 설정한다.
	 * @param stdgCd String
	 */
	public void setStdgCd(String stdgCd) {
		this.stdgCd = stdgCd;
	}
	/**
	 * frtpCd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getFrtpCd() {
		return frtpCd;
	}
	/**
	 * frtpCd attribute 값을 설정한다.
	 * @param frtpCd String
	 */
	public void setFrtpCd(String frtpCd) {
		this.frtpCd = frtpCd;
	}
	/**
	 * mapLabel attribute 값을  리턴한다.
	 * @return String
	 */
	public String getMapLabel() {
		return mapLabel;
	}
	/**
	 * mapLabel attribute 값을 설정한다.
	 * @param mapLabel String
	 */
	public void setMapLabel(String mapLabel) {
		this.mapLabel = mapLabel;
	}
	/**
	 * lcpArea attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLcpArea() {
		return lcpArea;
	}
	/**
	 * lcpArea attribute 값을 설정한다.
	 * @param lcpArea String
	 */
	public void setLcpArea(String lcpArea) {
		this.lcpArea = lcpArea;
	}
	/**
	 * frtpArea attribute 값을  리턴한다.
	 * @return String
	 */
	public String getFrtpArea() {
		return frtpArea;
	}
	/**
	 * frtpArea attribute 값을 설정한다.
	 * @param frtpArea String
	 */
	public void setFrtpArea(String frtpArea) {
		this.frtpArea = frtpArea;
	}
	/**
	 * prrckLarg attribute 값을  리턴한다.
	 * @return String
	 */
	public String getPrrckLarg() {
		return prrckLarg;
	}
	/**
	 * prrckLarg attribute 값을 설정한다.
	 * @param praprrckLargckLarg String
	 */
	public void setPrrckLarg(String prrckLarg) {
		this.prrckLarg = prrckLarg;
	}
	public String getPrrckMddl() {
		return prrckMddl;
	}
	public void setPrrckMddl(String prrckMddl) {
		this.prrckMddl = prrckMddl;
	}
	public String getEvtAvg() {
		return evtAvg;
	}
	public void setEvtAvg(String evtAvg) {
		this.evtAvg = evtAvg;
	}
	public String getEvtMin() {
		return evtMin;
	}
	public void setEvtMin(String evtMin) {
		this.evtMin = evtMin;
	}
	public String getEvtMax() {
		return evtMax;
	}
	public void setEvtMax(String evtMax) {
		this.evtMax = evtMax;
	}
	public String getSlpAvg() {
		return slpAvg;
	}
	public void setSlpAvg(String slpAvg) {
		this.slpAvg = slpAvg;
	}
	public String getSlpMin() {
		return slpMin;
	}
	public void setSlpMin(String slpMin) {
		this.slpMin = slpMin;
	}
	public String getSlpMax() {
		return slpMax;
	}
	public void setSlpMax(String slpMax) {
		this.slpMax = slpMax;
	}
	/**
	 * sldpAvg attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSldpAvg() {
		return sldpAvg;
	}
	/**
	 * sldpAvg attribute 값을 설정한다.
	 * @param avgflrSld String
	 */
	public void setSldpAvg(String sldpAvg) {
		this.sldpAvg = sldpAvg;
	}
	/**
	 * sldpMin attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSldpMin() {
		return sldpMin;
	}
	/**
	 * sldpMin attribute 값을 설정한다.
	 * @param vldtySldp String
	 */
	public void setSldpMin(String sldpMin) {
		this.sldpMin = sldpMin;
	}
	/**
	 * sldpMax attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSldpMax() {
		return sldpMax;
	}
	/**
	 * sldpMax attribute 값을 설정한다.
	 * @param sibflrSld String
	 */
	public void setSldpMax(String sldpMax) {
		this.sldpMax = sldpMax;
	}
	/**
	 * sltpCd attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSltpCd() {
		return sltpCd;
	}
	/**
	 * sltpCd attribute 값을 설정한다.
	 * @param sltpCd String
	 */
	public void setSltpCd(String sltpCd) {
		this.sltpCd = sltpCd;
	}
	/**
	 * sibflrScs attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSibflrScs() {
		return sibflrScs;
	}
	/**
	 * sibflrScs attribute 값을 설정한다.
	 * @param sibflrScs String
	 */
	public void setSibflrScs(String sibflrScs) {
		this.sibflrScs = sibflrScs;
	}
	/**
	 * sibflrStr attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSibflrStr() {
		return sibflrStr;
	}
	/**
	 * sibflrStr attribute 값을 설정한다.
	 * @param sibflrStr String
	 */
	public void setSibflrStr(String sibflrStr) {
		this.sibflrStr = sibflrStr;
	}
	/**
	 * rockExdgr attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRockExdgr() {
		return rockExdgr;
	}
	/**
	 * rockExdgr attribute 값을 설정한다.
	 * @param rockExdgr String
	 */
	public void setRockExdgr(String rockExdgr) {
		this.rockExdgr = rockExdgr;
	}
	/**
	 * sibflrCbs attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSibflrCbs() {
		return sibflrCbs;
	}
	/**
	 * sibflrCbs attribute 값을 설정한다.
	 * @param sibflrCbs String
	 */
	public void setSibflrCbs(String sibflrCbs) {
		this.sibflrCbs = sibflrCbs;
	}
	/**
	 * wteffDgr attribute 값을  리턴한다.
	 * @return String
	 */
	public String getWteffDgr() {
		return wteffDgr;
	}
	/**
	 * wteffDgr attribute 값을 설정한다.
	 * @param wteffDgr String
	 */
	public void setWteffDgr(String wteffDgr) {
		this.wteffDgr = wteffDgr;
	}
	public String getSmid() {
		return smid;
	}
	public void setSmid(String smid) {
		this.smid = smid;
	}
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
}
