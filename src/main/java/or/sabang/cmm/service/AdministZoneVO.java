package or.sabang.cmm.service;

import java.io.Serializable;

public class AdministZoneVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * 시도코드
	 */
	private String sdCode;
	
	/*
	 * 시도명
	 */
	private String sdNm;
	
	/*
	 * 시군구코드
	 */
	private String sggCode;
	
	/*
	 * 시군구명
	 */
	private String sggNm;
	
	/*
	 * 읍면동코드
	 */
	private String emdCode;
	
	/*
	 * 읍면동명
	 */
	private String emdNm;
	
	/*
	 * 리코드
	 */
	private String riCode;
	
	/*
	 * 리명
	 */
	private String riNm;
	
	/*
	 * 행정구역코드
	 */
	private String adminCode;
	
	/*
	 * 행정구역명
	 */
	private String adminNm;
	
	/**
	 * sdCode attribute를 리턴한다.
	 * @return String
	 */
	public String getSdCode() {
		return sdCode;
	}
	
	/**
     * sdCode attribute 값을 설정한다.
     * @param sdCode String
     */
	public void setSdCode(String sdCode) {
		this.sdCode = sdCode;
	}
	
	/**
	 * sdNm attribute를 리턴한다.
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
	 * sggCode attribute를 리턴한다.
	 * @return String
	 */
	public String getSggCode() {
		return sggCode;
	}
	
	/**
	 * sggCode attribute 값을 설정한다.
	 * @param sggCode String
	 */
	public void setSggCode(String sggCode) {
		this.sggCode = sggCode;
	}
	
	/**
	 * sggNm attribute를 리턴한다.
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
	 * emdCode attribute를 리턴한다.
	 * @return String
	 */
	public String getEmdCode() {
		return emdCode;
	}
	
	/**
	 * emdCode attribute 값을 설정한다.
	 * @param emdCode String
	 */
	public void setEmdCode(String emdCode) {
		this.emdCode = emdCode;
	}
	
	/**
	 * emdNm attribute를 리턴한다.
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
	 * riCode attribute를 리턴한다.
	 * @return String
	 */
	public String getRiCode() {
		return riCode;
	}
	
	/**
	 * riCode attribute 값을 설정한다.
	 * @param riCode String
	 */
	public void setRiCode(String riCode) {
		this.riCode = riCode;
	}
	
	/**
	 * riNm attribute를 리턴한다.
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
	 * adminCode attribute를 리턴한다.
	 * @return String
	 */
	public String getAdminCode() {
		return adminCode;
	}
	
	/**
	 * adminCode attribute 값을 설정한다.
	 * @param adminCode String
	 */
	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}
	
	/**
	 * adminNm attribute를 리턴한다.
	 * @return String
	 */
	public String getAdminNm() {
		return adminNm;
	}
	
	/**
	 * adminNm attribute 값을 설정한다.
	 * @param adminNm String
	 */
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}
}
