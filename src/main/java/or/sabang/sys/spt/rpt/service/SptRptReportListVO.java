package or.sabang.sys.spt.rpt.service;

import egovframework.com.cmm.ComDefaultVO;

public class SptRptReportListVO extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 조사방ID
	 */
	private String mstId;
	/**
	 * 기관명
	 */
	private String corpNm;
	/**
	 * 파트명
	 */
	private String partNm;
	/**
	 * 등록자명
	 */
	private String creatNm;
	/**
	 * 조사완료수
	 */
	private int cnt;
	/**
	 * 등록인
	 */
	private String createDt;
	
	public String getMstId() {
		return mstId;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}
	public String getCorpNm() {
		return corpNm;
	}
	public void setCorpNm(String corpNm) {
		this.corpNm = corpNm;
	}
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public String getCreatNm() {
		return creatNm;
	}
	public void setCreatNm(String creatNm) {
		this.creatNm = creatNm;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
}
