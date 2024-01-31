package or.sabang.sys.lss.cnl.service;

import egovframework.com.cmm.ComDefaultVO;

public class LssCnlStripLandVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	
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
	 * 관리주체
	 */
	private String mange;
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
	
	private String smid;
	
	/*순번*/
	private String sn;
	
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
	
	public String getMange() {
		return mange;
	}
	public void setMange(String mange) {
		this.mange = mange;
	}
	public String getSmid() {
		return smid;
	}
	public void setSmid(String smid) {
		this.smid = smid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
