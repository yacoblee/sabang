package or.sabang.sys.lss.wka.service;

public class LssWkaStripLand extends LssWkaStripLandVO {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 조사유형코드
	 */
	private String typeNm;
	/**
	 * 관할1코드
	 */
	private String region1Nm;
	/**
	 * 관할2코드
	 */
	private String region2Nm;
	/**
	 * 시도코드
	 */
	private String sdNm;
	/**
	 * 시군구코드
	 */
	private String sggNm;
	/**
	 * 읍면동
	 */
	private String emdNm;
	/**
	 * 리
	 */
	private String riNm;
	/**
	 * 위도 도
	 */
	private String latD;
	/**
	 * 위도 분
	 */
	private String latM;
	/**
	 * 위도 초
	 */
	private String latS;
	/**
	 * 경도 도
	 */
	private String lonD;
	/**
	 * 경도 분
	 */
	private String lonM;
	/**
	 * 경도 초
	 */
	private String lonS;
	
	/**
	 * 위도 Decimal Degree
	 */
	private Double latDd;
	/**
	 * 경도 Decimal Degree
	 */
	private Double lonDd;
	/**
	 * typeNm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getTypeNm() {
		return typeNm;
	}
	/**
	 * typeNm attribute 값을 설정한다.
	 * @param typeNm String
	 */
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	/**
	 * region1Nm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRegion1Nm() {
		return region1Nm;
	}
	/**
	 * region1Nm attribute 값을 설정한다.
	 * @param region1Nm String
	 */
	public void setRegion1Nm(String region1Nm) {
		this.region1Nm = region1Nm;
	}
	/**
	 * region2Nm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRegion2Nm() {
		return region2Nm;
	}
	/**
	 * region2Nm attribute 값을 설정한다.
	 * @param region2Nm String
	 */
	public void setRegion2Nm(String region2Nm) {
		this.region2Nm = region2Nm;
	}
	/**
	 * sdNm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSdNm() {
		return sdNm;
	}
	/**
	 * sdNm attribute 값을 설정한다.
	 * @param sdNm String
	 */
	public void setSdNm(String sdNm) {
		this.sdNm = sdNm;
	}
	/**
	 * sggNm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getSggNm() {
		return sggNm;
	}
	/**
	 * sggNm attribute 값을 설정한다.
	 * @param sggNm String
	 */
	public void setSggNm(String sggNm) {
		this.sggNm = sggNm;
	}
	/**
	 * emdNm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getEmdNm() {
		return emdNm;
	}
	/**
	 * emdNm attribute 값을 설정한다.
	 * @param emdNm String
	 */
	public void setEmdNm(String emdNm) {
		this.emdNm = emdNm;
	}
	/**
	 * riNm attribute 값을  리턴한다.
	 * @return String
	 */
	public String getRiNm() {
		return riNm;
	}
	/**
	 * riNm attribute 값을 설정한다.
	 * @param riNm String
	 */
	public void setRiNm(String riNm) {
		this.riNm = riNm;
	}
	/**
	 * latDgree attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLatD() {
		return latD;
	}
	/**
	 * latDgree attribute 값을 설정한다.
	 * @param latD String
	 */
	public void setLatD(String latD) {
		this.latD = latD;
	}
	/**
	 * latM attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLatM() {
		return latM;
	}
	/**
	 * latM attribute 값을 설정한다.
	 * @param latM String
	 */
	public void setLatM(String latM) {
		this.latM = latM;
	}
	/**
	 * latS attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLatS() {
		return latS;
	}
	/**
	 * latS attribute 값을 설정한다.
	 * @param latS String
	 */
	public void setLatS(String latS) {
		this.latS = latS;
	}
	/**
	 * lonDgree attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLonD() {
		return lonD;
	}
	/**
	 * lonDgree attribute 값을 설정한다.
	 * @param lonD String
	 */
	public void setLonD(String lonD) {
		this.lonD = lonD;
	}
	/**
	 * lonM attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLonM() {
		return lonM;
	}
	/**
	 * lonM attribute 값을 설정한다.
	 * @param lonM String
	 */
	public void setLonM(String lonM) {
		this.lonM = lonM;
	}
	/**
	 * lonS attribute 값을  리턴한다.
	 * @return String
	 */
	public String getLonS() {
		return lonS;
	}
	/**
	 * lonS attribute 값을 설정한다.
	 * @param lonS String
	 */
	public void setLonS(String lonS) {
		this.lonS = lonS;
	}
	/**
	 * latDd attribute 값을  리턴한다.
	 * @return String
	 */
	public Double getLatDd() {
		return latDd;
	}
	/**
	 * latDd attribute 값을 설정한다.
	 * @param latDd String
	 */
	public void setLatDd(Double latDd) {
		this.latDd = latDd;
	}
	/**
	 * lonDd attribute 값을  리턴한다.
	 * @return String
	 */
	public Double getLonDd() {
		return lonDd;
	}
	/**
	 * lonDd attribute 값을 설정한다.
	 * @param lonDd String
	 */
	public void setLonDd(Double lonDd) {
		this.lonDd = lonDd;
	}
}
