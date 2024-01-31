package or.sabang.sys.vyt.ecb.service;

import egovframework.com.cmm.ComDefaultVO;

public class VytEcbStripLandVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	private String mstId;
	private String sldId;
	private String lgstrCd;
	private String sdNm;
	private String sggNm;
	private String emdNm;
	private String riNm;
	private String jibun;
	private String creatDt;
	private String lastUpdtDt;
	private double lat;
	private double lon;
	public String getMstId() {
		return mstId;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}
	public String getSldId() {
		return sldId;
	}
	public void setSldId(String sldId) {
		this.sldId = sldId;
	}
	public String getLgstrCd() {
		return lgstrCd;
	}
	public void setLgstrCd(String lgstrCd) {
		this.lgstrCd = lgstrCd;
	}
	public String getSdNm() {
		return sdNm;
	}
	public void setSdNm(String sdNm) {
		this.sdNm = sdNm;
	}
	public String getSggNm() {
		return sggNm;
	}
	public void setSggNm(String sggNm) {
		this.sggNm = sggNm;
	}
	public String getEmdNm() {
		return emdNm;
	}
	public void setEmdNm(String emdNm) {
		this.emdNm = emdNm;
	}
	public String getRiNm() {
		return riNm;
	}
	public void setRiNm(String riNm) {
		this.riNm = riNm;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	public String getLastUpdtDt() {
		return lastUpdtDt;
	}
	public void setLastUpdtDt(String lastUpdtDt) {
		this.lastUpdtDt = lastUpdtDt;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
}
