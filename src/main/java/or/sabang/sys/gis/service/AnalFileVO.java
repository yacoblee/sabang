package or.sabang.sys.gis.service;

public class AnalFileVO{
	/**
	 * 고유번호
	 */
	private int gid;
	/**
	 * 공유방ID
	 */
	private int mstId;
	/**
	 * 조사ID
	 */
	private String sldId;
	/**
	 * 분석ID
	 */
	private String analId;
	/**
	 * 이전분석ID
	 */
	private String oldAnalId;
	/**
	 * 생성일자
	 */
	private String creatDt;
	/**
	 * 파일확장자
	 */
	private String fileExtsn;
	/**
	 * 파일저장경로
	 */
	private String fileStreCours;
	/**
	 * 저장파일명
	 */
	private String streFileNm;
	/**
	 * 원파일명
	 */
	private String orignlFileNm;
	/**
	 * 분석종류
	 */
	private String analType;
	/**
	 * 파일크기
	 */
	private String fileSize;
	/**
	 * 생성자
	 */
	private String creatUser;
	/**
	 * smid
	 */
	private String smid;
	/**
	 * 공간분석통계정보
	 */
	private String statData;
	/**
	 * 공간분석통계정보
	 */
	private String statMaxData;
	/**
	 * 공간분석 통계 최대값
	 */
	private String statMaxValue;
	/**
	 * 공간분석 통계 최소값
	 */
	private String statMinValue;
	/**
	 * 공간분석 통계평균값
	 */
	private String statAvgValue;
	/**
	 * 버퍼 사이즈
	 */
	private String bufferSize; 
	/**
	 * 분석 노선코드
	 */
	private String routeCode;
	/**
	 * 유역면적WKT
	 */
	private String waterShedWkt;
	/**
	 * 유출구WKT
	 */
	private String ecrtcnlWkt;
	/**
	 * 조사유형
	 */
	private String svyType;
	
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getMstId() {
		return mstId;
	}
	public void setMstId(int mstId) {
		this.mstId = mstId;
	}
	public String getSldId() {
		return sldId;
	}
	public void setSldId(String sldId) {
		this.sldId = sldId;
	}
	public String getAnalId() {
		return analId;
	}
	public void setAnalId(String analId) {
		this.analId = analId;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	public String getFileExtsn() {
		return fileExtsn;
	}
	public void setFileExtsn(String fileExtsn) {
		this.fileExtsn = fileExtsn;
	}
	public String getFileStreCours() {
		return fileStreCours;
	}
	public void setFileStreCours(String fileStreCours) {
		this.fileStreCours = fileStreCours;
	}
	public String getStreFileNm() {
		return streFileNm;
	}
	public void setStreFileNm(String streFileNm) {
		this.streFileNm = streFileNm;
	}
	public String getOrignlFileNm() {
		return orignlFileNm;
	}
	public void setOrignlFileNm(String orignlFileNm) {
		this.orignlFileNm = orignlFileNm;
	}
	public String getAnalType() {
		return analType;
	}
	public void setAnalType(String analType) {
		this.analType = analType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getCreatUser() {
		return creatUser;
	}
	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}
	public String getSmid() {
		return smid;
	}
	public void setSmid(String smid) {
		this.smid = smid;
	}
	public String getStatData() {
		return statData;
	}
	public void setStatData(String statData) {
		this.statData = statData;
	}
	public String getStatMaxData() {
		return statMaxData;
	}
	public void setStatMaxData(String statMaxData) {
		this.statMaxData = statMaxData;
	}
	public String getStatMaxValue() {
		return statMaxValue;
	}
	public String getStatMinValue() {
		return statMinValue;
	}
	public String getStatAvgValue() {
		return statAvgValue;
	}
	public void setStatMaxValue(String statMaxValue) {
		this.statMaxValue = statMaxValue;
	}
	public void setStatMinValue(String statMinValue) {
		this.statMinValue = statMinValue;
	}
	public void setStatAvgValue(String statAvgValue) {
		this.statAvgValue = statAvgValue;
	}
	public String getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(String bufferSize) {
		this.bufferSize = bufferSize;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getWaterShedWkt() {
		return waterShedWkt;
	}
	public void setWaterShedWkt(String waterShedWkt) {
		this.waterShedWkt = waterShedWkt;
	}
	public String getSvyType() {
		return svyType;
	}
	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}
	public String getEcrtcnlWkt() {
		return ecrtcnlWkt;
	}
	public void setEcrtcnlWkt(String ecrtcnlWkt) {
		this.ecrtcnlWkt = ecrtcnlWkt;
	}
	public String getOldAnalId() {
		return oldAnalId;
	}
	public void setOldAnalId(String oldAnalId) {
		this.oldAnalId = oldAnalId;
	}
}
