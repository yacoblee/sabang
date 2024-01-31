
package or.sabang.sys.ext.service;

import egovframework.com.cmm.ComDefaultVO;

public class ExtFieldBookItemVO extends ComDefaultVO {

	
	/**
	 * 고유 아이디 
	 */
	private int gid;
	
	/**
	 * 공유방 ID
	 */
	private String MST_ID;
	
	/**
	 * 로그인 ID
	 */
	private String LOGIN_ID ;

	/**
	 * 키값
	 */
	private int _FID;
	/**
	 * x좌표
	 */
	private String _LON;
	
	/**
	 * y좌표
	 */
	private String _LAT;
	
	/**
	 * Geometry
	 */
	private String _DATA;

	/**
	 * 키워드
	 */
	private String _KEYWORD;
	
	/**
	 * 라벨
	 */
	private String _LABEL;
	
	/**
	 * 스타일
	 */
	private String _STYLE;
	
	/**
	 * 메모
	 */
	private String _MEMO;
	
	/**
	 * 태그1
	 */
	private String _TAG1;
	
	/**
	 * 태그2
	 */
	private String _TAG2;
	
	/**
	 * 등록일자
	 */
	private String _REG_DATE;
	
	/**
	 * 수정일자
	 */
	private String _UPD_DATE;
	
	/**
	 * 시스템전송일자
	 */
	private String _LAST_UPD_DATE;
	/**
	 * 상세속성
	 */
	private String ATTR;
	
	/**
	 * 구분
	 */
	private String SE;

	/**
	 * 위도 Decimal Degree
	 */
	private Double LATDD;
	/**
	 * 경도 Decimal Degree
	 */
	private Double LONDD;
	/**
	 * 공간정보테이블 구분
	 */
	private String GIS_SE;
	
	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getMST_ID() {
		return MST_ID;
	}

	public void setMST_ID(String MST_ID) {
		this.MST_ID = MST_ID;
	}

	public String getLOGIN_ID() {
		return LOGIN_ID;
	}

	public void setLOGIN_ID(String lOGIN_ID) {
		this.LOGIN_ID = lOGIN_ID;
	}

	public int getFID() {
		return _FID;
	}

	public void setFID(int _FID) {
		this._FID = _FID;
	}

	public String getLON() {
		return _LON;
	}

	public void setLON(String _LON) {
		this._LON = _LON;
	}

	public String getLAT() {
		return _LAT;
	}

	public void setLAT(String _LAT) {
		this._LAT = _LAT;
	}

	public String getDATA() {
		return _DATA;
	}

	public void setDATA(String _DATA) {
		this._DATA = _DATA;
	}

	public String getKEYWORD() {
		return _KEYWORD;
	}

	public void setKEYWORD(String _KEYWORD) {
		this._KEYWORD = _KEYWORD;
	}

	public String getLABEL() {
		return _LABEL;
	}

	public void setLABEL(String _LABEL) {
		this._LABEL = _LABEL;
	}

	public String getSTYLE() {
		return _STYLE;
	}

	public void setSTYLE(String _STYLE) {
		this._STYLE = _STYLE;
	}

	public String getMEMO() {
		return _MEMO;
	}

	public void setMEMO(String _MEMO) {
		this._MEMO = _MEMO;
	}

	public String getTAG1() {
		return _TAG1;
	}

	public void setTAG1(String _TAG1) {
		this._TAG1 = _TAG1;
	}

	public String getTAG2() {
		return _TAG2;
	}

	public void setTAG2(String _TAG2) {
		this._TAG2 = _TAG2;
	}

	public String getREG_DATE() {
		return _REG_DATE;
	}

	public void setREG_DATE(String _REG_DATE) {
		this._REG_DATE = _REG_DATE;
	}

	public String getUPD_DATE() {
		return _UPD_DATE;
	}

	public void setUPD_DATE(String _UPD_DATE) {
		this._UPD_DATE = _UPD_DATE;
	}

	public String getATTR() {
		return ATTR;
	}

	public void setATTR(String ATTR) {
		this.ATTR = ATTR;
	}

	public String getSE() {
		return SE;
	}

	public void setSE(String SE) {
		this.SE = SE;
	}

	public Double getLATDD() {
		return LATDD;
	}

	public void setLATDD(Double lATDD) {
		LATDD = lATDD;
	}

	public Double getLONDD() {
		return LONDD;
	}

	public void setLONDD(Double lONDD) {
		LONDD = lONDD;
	}

	public String getGIS_SE() {
		return GIS_SE;
	}

	public void setGIS_SE(String gIS_SE) {
		GIS_SE = gIS_SE;
	}

	public String getLAST_UPD_DATE() {
		return _LAST_UPD_DATE;
	}

	public void setLAST_UPD_DATE(String _LAST_UPD_DATE) {
		this._LAST_UPD_DATE = _LAST_UPD_DATE;
	}
	
}
