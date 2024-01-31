package or.sabang.sys.service;

public class SysFieldInfoVO {
	
	private String svy_id;
	private String smgeometry;
	/**
	 * 고유 아이디 
	 */
	private int smid;
	
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
	private String uniq_id;
	public int getSmid() {
		return smid;
	}
	public void setSmid(int smid) {
		this.smid = smid;
	}
	public String getMST_ID() {
		return MST_ID;
	}
	public void setMST_ID(String mST_ID) {
		MST_ID = mST_ID;
	}
	public String getLOGIN_ID() {
		return LOGIN_ID;
	}
	public void setLOGIN_ID(String lOGIN_ID) {
		LOGIN_ID = lOGIN_ID;
	}
	public int get_FID() {
		return _FID;
	}
	public void set_FID(int _FID) {
		this._FID = _FID;
	}
	public String get_LON() {
		return _LON;
	}
	public void set_LON(String _LON) {
		this._LON = _LON;
	}
	public String get_LAT() {
		return _LAT;
	}
	public void set_LAT(String _LAT) {
		this._LAT = _LAT;
	}
	public String get_DATA() {
		return _DATA;
	}
	public void set_DATA(String _DATA) {
		this._DATA = _DATA;
	}
	public String get_KEYWORD() {
		return _KEYWORD;
	}
	public void set_KEYWORD(String _KEYWORD) {
		this._KEYWORD = _KEYWORD;
	}
	public String get_LABEL() {
		return _LABEL;
	}
	public void set_LABEL(String _LABEL) {
		this._LABEL = _LABEL;
	}
	public String get_STYLE() {
		return _STYLE;
	}
	public void set_STYLE(String _STYLE) {
		this._STYLE = _STYLE;
	}
	public String get_MEMO() {
		return _MEMO;
	}
	public void set_MEMO(String _MEMO) {
		this._MEMO = _MEMO;
	}
	public String get_TAG1() {
		return _TAG1;
	}
	public void set_TAG1(String _TAG1) {
		this._TAG1 = _TAG1;
	}
	public String get_TAG2() {
		return _TAG2;
	}
	public void set_TAG2(String _TAG2) {
		this._TAG2 = _TAG2;
	}
	public String get_REG_DATE() {
		return _REG_DATE;
	}
	public void set_REG_DATE(String _REG_DATE) {
		this._REG_DATE = _REG_DATE;
	}
	public String get_UPD_DATE() {
		return _UPD_DATE;
	}
	public void set_UPD_DATE(String _UPD_DATE) {
		this._UPD_DATE = _UPD_DATE;
	}
	public String getATTR() {
		return ATTR;
	}
	public void setATTR(String aTTR) {
		ATTR = aTTR;
	}
	public String getSE() {
		return SE;
	}
	public void setSE(String sE) {
		SE = sE;
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
	public String getSvy_id() {
		return svy_id;
	}
	public void setSvy_id(String svy_id) {
		this.svy_id = svy_id;
	}
	public String getSmgeometry() {
		return smgeometry;
	}
	public void setSmgeometry(String smgeometry) {
		this.smgeometry = smgeometry;
	}
	public String getUniq_id() {
		return uniq_id;
	}
	public void setUniq_id(String uniq_id) {
		this.uniq_id = uniq_id;
	}
}
