package or.sabang.sys.fck.mse.service;

import egovframework.com.cmm.ComDefaultVO;

public class FckMseComptVO  extends ComDefaultVO {

	private static final long serialVersionUID = 1L;
	
	/* gid */
	private String gid;
	/* 일련번호 */
	private String sn;
	/* 조사정보 fid */
	private int fid;
	/* 공유방아이디 */
	private String mstId;
	/* 계측기아이디*/
	private String sldid;
	/* 조사단계*/
	private String svystep;
	/* 수정관리*/
	private String updt;
	/* 장비유형*/
	private String eqpmntype;
	/* 조사ID */
	private String svyId;
	/* 작성자ID */
	private String loginId;
	/* 조사유형 */
	private String svyType;
	/* 형식 */
	private String svyForm;	
	/* 점검일시 */
	private String svyDt;
	/* 조사자 */
	private String svyUser;
	/* 점검자 */
	private String chkUser;
	/* 시도명 */
	private String svySd;
	/* 시군구명 */
	private String svySgg;
	/* 읍면동명 */
	private String svyEmd;
	/* 리명 */
	private String svyRi;
	/* 지번 */
	private String svyJibun;
	/* 지번 */
	private String commonly;
	
	/* 와이어신축계 채널명 */
	private String wireextchl;
	/* 와이어신축계 외관점검 */
	private String wireext;
	/* 와이어신축계 성능점검 */
	private String wireextper;
	/* 지중경사계 채널명 */
	private String licmeterchl;
	/* 지중경사계 외관점검 */
	private String licmeter;
	/* 지중경사계 성능점검 */
	private String licmeterper;
	/* 지하수위계 채널명 */
	private String gwgaugechl;
	/* 지하수위계 외관점검 */
	private String gwgauge;
	/* 지하수위계 성능점검 */
	private String gwgaugeper;
	/* 강우계 채널명 */
	private String raingaugechl;
	/* 강우계 외관점검 */
	private String raingauge;
	/* 강우계 성능점검 */
	private String raingaugeper;
	/* 구조물변위계 채널명 */
	private String strcdpmchl;
	/* 구조물변위계 외관점검 */
	private String strcdpm;
	/* 구조물변위계 성능점검 */
	private String strcdpmper;
	/* 지표변위계 채널명 */
	private String surdpmchl;
	/* 지표변위계 외관점검 */
	private String surdpm;
	/* 지표변위계 성능점검 */
	private String surdpmper;
	/* GPS변위계 채널명 */
	private String gpsgaugechl;
	/* GPS변위계 외관점검 */
	private String gpsgauge;
	/* GPS변위계 성능점검 */
	private String gpsgaugeper;
	/* 게이트웨이 채널명 */
	private String gatewaychl;
	/* 게이트웨이 외관점검 */
	private String gateway;
	/* 게이트웨이 성능점검 */
	private String gatewayper;
	/* 노드 채널명 */
	private String nodechl;
	/* 노드 외관점검 */
	private String node;
	/* 노드 성능점검 */
	private String nodeper;
	
	/* 위도 */
	private String svyLat;
	/* 경도 */
	private String svyLon;
	/* 계류보전 시점좌표 경도 */
	private String bpx;
	/* 계류보전 시점좌표 위도 */
	private String bpy;
	/* 위도 */
	private String lat;
	/* 경도 */
	private String lon;
	/* 위치좌표 경도 */
	private String px;
	/* 위치좌표 위도 */
	private String py;
	/* 계류보전 종점좌표 경도 */
	private String epx;
	/* 계류보전 종점좌표 위도 */
	private String epy;
	
	/* 종합의견1 */
	private String opinion1;
	/* 종합의견2 */
	private String opinion2;
	/* 종합의견3 */
	private String opinion3;
	/* 종합의견4 */
	private String opinion4;
	/* 종합의견5 */
	private String opinion5;
	
	/* 위치도 */
	private String lcmap;
	
	/* 사진 */
	private String photo;
	/* 사진태그 */
	private String photoTag;
	/* 사진태그배열 */
	private String photoTagList;
	/* 등록일자 */
	private String creatDt;
	/* 수정일자 */
	private String lastUpdtPnttm;
	
	/* 위경도 */
	private String svyLatLon;
	
	/* 연도 */
	private String svyYear;
	/* 공유방 명칭 */
	private String mstNm;
	/* 조사일자 - 월 */
	private String svyMonth;
	/* 위치도 줌 레벨 */
	private String changedZoom;
	/* 위치도 인덱스 */
	private int locImgIdx;
	/* 조사정보 ATTR */
	private String attr;
	/* 현장사진 개수 */
	private int photoLen;
	/* 소유구분 */
	private String owner;
	/* 법적제한사항 */
	private String lgllimit;
	/* 통신모뎀번호 */
	private String commmodemnum;
	/* 이동전화번호 */
	private String cellnum;
	
	/* 와이어신축계*/
	private String[] wireextIdList;
	/* 지중경사계*/
	private String[] licmeterIdList;
	/* 지하수위계*/
	private String[] gwgaugeIdList;
	/* 강우계*/
	private String[] raingaugeIdList;
	/* 구조물변위계*/
	private String[] strcdpmIdList;
	/* 지표변위계*/
	private String[] surdpmIdList;
	/* GPS변위계*/
	private String[] gpsgaugeIdList;
	/* 게이트웨이*/
	private String[] gatewayIdList;
	/* 노드*/
	private String[] nodeIdList;
	
	public String getGid() {
		return gid;
	}
	public String getSn() {
		return sn;
	}
	public int getFid() {
		return fid;
	}
	public String getMstId() {
		return mstId;
	}
	public String getSldid() {
		return sldid;
	}
	public String getSvystep() {
		return svystep;
	}
	public String getUpdt() {
		return updt;
	}
	public String getEqpmntype() {
		return eqpmntype;
	}
	public String getSvyId() {
		return svyId;
	}
	public String getLoginId() {
		return loginId;
	}
	public String getSvyType() {
		return svyType;
	}
	public String getSvyForm() {
		return svyForm;
	}
	public String getSvyDt() {
		return svyDt;
	}
	public String getSvyUser() {
		return svyUser;
	}
	public String getChkUser() {
		return chkUser;
	}
	public String getSvySd() {
		return svySd;
	}
	public String getSvySgg() {
		return svySgg;
	}
	public String getSvyEmd() {
		return svyEmd;
	}
	public String getSvyRi() {
		return svyRi;
	}
	public String getSvyJibun() {
		return svyJibun;
	}
	public String getCommonly() {
		return commonly;
	}
	public String getWireextchl() {
		return wireextchl;
	}
	public String getWireext() {
		return wireext;
	}
	public String getWireextper() {
		return wireextper;
	}
	public String getLicmeterchl() {
		return licmeterchl;
	}
	public String getLicmeter() {
		return licmeter;
	}
	public String getLicmeterper() {
		return licmeterper;
	}
	public String getGwgaugechl() {
		return gwgaugechl;
	}
	public String getGwgauge() {
		return gwgauge;
	}
	public String getGwgaugeper() {
		return gwgaugeper;
	}
	public String getRaingaugechl() {
		return raingaugechl;
	}
	public String getRaingauge() {
		return raingauge;
	}
	public String getRaingaugeper() {
		return raingaugeper;
	}
	public String getStrcdpmchl() {
		return strcdpmchl;
	}
	public String getStrcdpm() {
		return strcdpm;
	}
	public String getStrcdpmper() {
		return strcdpmper;
	}
	public String getSurdpmchl() {
		return surdpmchl;
	}
	public String getSurdpm() {
		return surdpm;
	}
	public String getSurdpmper() {
		return surdpmper;
	}
	public String getGpsgaugechl() {
		return gpsgaugechl;
	}
	public String getGpsgauge() {
		return gpsgauge;
	}
	public String getGpsgaugeper() {
		return gpsgaugeper;
	}
	public String getGatewaychl() {
		return gatewaychl;
	}
	public String getGateway() {
		return gateway;
	}
	public String getGatewayper() {
		return gatewayper;
	}
	public String getNodechl() {
		return nodechl;
	}
	public String getNode() {
		return node;
	}
	public String getNodeper() {
		return nodeper;
	}
	public String getSvyLat() {
		return svyLat;
	}
	public String getSvyLon() {
		return svyLon;
	}
	public String getBpx() {
		return bpx;
	}
	public String getBpy() {
		return bpy;
	}
	public String getLat() {
		return lat;
	}
	public String getLon() {
		return lon;
	}
	public String getPx() {
		return px;
	}
	public String getPy() {
		return py;
	}
	public String getEpx() {
		return epx;
	}
	public String getEpy() {
		return epy;
	}
	public String getOpinion1() {
		return opinion1;
	}
	public String getOpinion2() {
		return opinion2;
	}
	public String getOpinion3() {
		return opinion3;
	}
	public String getOpinion4() {
		return opinion4;
	}
	public String getOpinion5() {
		return opinion5;
	}
	public String getLcmap() {
		return lcmap;
	}
	public String getPhoto() {
		return photo;
	}
	public String getPhotoTag() {
		return photoTag;
	}
	public String getPhotoTagList() {
		return photoTagList;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public String getSvyLatLon() {
		return svyLatLon;
	}
	public String getSvyYear() {
		return svyYear;
	}
	public String getMstNm() {
		return mstNm;
	}
	public String getSvyMonth() {
		return svyMonth;
	}
	public String getChangedZoom() {
		return changedZoom;
	}
	public int getLocImgIdx() {
		return locImgIdx;
	}
	public String getAttr() {
		return attr;
	}
	public int getPhotoLen() {
		return photoLen;
	}
	public String getOwner() {
		return owner;
	}
	public String getLgllimit() {
		return lgllimit;
	}
	public String getCommmodemnum() {
		return commmodemnum;
	}
	public String getCellnum() {
		return cellnum;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public void setMstId(String mstId) {
		this.mstId = mstId;
	}
	public void setSldid(String sldid) {
		this.sldid = sldid;
	}
	public void setSvystep(String svystep) {
		this.svystep = svystep;
	}
	public void setUpdt(String updt) {
		this.updt = updt;
	}
	public void setEqpmntype(String eqpmntype) {
		this.eqpmntype = eqpmntype;
	}
	public void setSvyId(String svyId) {
		this.svyId = svyId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public void setSvyType(String svyType) {
		this.svyType = svyType;
	}
	public void setSvyForm(String svyForm) {
		this.svyForm = svyForm;
	}
	public void setSvyDt(String svyDt) {
		this.svyDt = svyDt;
	}
	public void setSvyUser(String svyUser) {
		this.svyUser = svyUser;
	}
	public void setChkUser(String chkUser) {
		this.chkUser = chkUser;
	}
	public void setSvySd(String svySd) {
		this.svySd = svySd;
	}
	public void setSvySgg(String svySgg) {
		this.svySgg = svySgg;
	}
	public void setSvyEmd(String svyEmd) {
		this.svyEmd = svyEmd;
	}
	public void setSvyRi(String svyRi) {
		this.svyRi = svyRi;
	}
	public void setSvyJibun(String svyJibun) {
		this.svyJibun = svyJibun;
	}
	public void setCommonly(String commonly) {
		this.commonly = commonly;
	}
	public void setWireextchl(String wireextchl) {
		this.wireextchl = wireextchl;
	}
	public void setWireext(String wireext) {
		this.wireext = wireext;
	}
	public void setWireextper(String wireextper) {
		this.wireextper = wireextper;
	}
	public void setLicmeterchl(String licmeterchl) {
		this.licmeterchl = licmeterchl;
	}
	public void setLicmeter(String licmeter) {
		this.licmeter = licmeter;
	}
	public void setLicmeterper(String licmeterper) {
		this.licmeterper = licmeterper;
	}
	public void setGwgaugechl(String gwgaugechl) {
		this.gwgaugechl = gwgaugechl;
	}
	public void setGwgauge(String gwgauge) {
		this.gwgauge = gwgauge;
	}
	public void setGwgaugeper(String gwgaugeper) {
		this.gwgaugeper = gwgaugeper;
	}
	public void setRaingaugechl(String raingaugechl) {
		this.raingaugechl = raingaugechl;
	}
	public void setRaingauge(String raingauge) {
		this.raingauge = raingauge;
	}
	public void setRaingaugeper(String raingaugeper) {
		this.raingaugeper = raingaugeper;
	}
	public void setStrcdpmchl(String strcdpmchl) {
		this.strcdpmchl = strcdpmchl;
	}
	public void setStrcdpm(String strcdpm) {
		this.strcdpm = strcdpm;
	}
	public void setStrcdpmper(String strcdpmper) {
		this.strcdpmper = strcdpmper;
	}
	public void setSurdpmchl(String surdpmchl) {
		this.surdpmchl = surdpmchl;
	}
	public void setSurdpm(String surdpm) {
		this.surdpm = surdpm;
	}
	public void setSurdpmper(String surdpmper) {
		this.surdpmper = surdpmper;
	}
	public void setGpsgaugechl(String gpsgaugechl) {
		this.gpsgaugechl = gpsgaugechl;
	}
	public void setGpsgauge(String gpsgauge) {
		this.gpsgauge = gpsgauge;
	}
	public void setGpsgaugeper(String gpsgaugeper) {
		this.gpsgaugeper = gpsgaugeper;
	}
	public void setGatewaychl(String gatewaychl) {
		this.gatewaychl = gatewaychl;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public void setGatewayper(String gatewayper) {
		this.gatewayper = gatewayper;
	}
	public void setNodechl(String nodechl) {
		this.nodechl = nodechl;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public void setNodeper(String nodeper) {
		this.nodeper = nodeper;
	}
	public void setSvyLat(String svyLat) {
		this.svyLat = svyLat;
	}
	public void setSvyLon(String svyLon) {
		this.svyLon = svyLon;
	}
	public void setBpx(String bpx) {
		this.bpx = bpx;
	}
	public void setBpy(String bpy) {
		this.bpy = bpy;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public void setPx(String px) {
		this.px = px;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public void setEpx(String epx) {
		this.epx = epx;
	}
	public void setEpy(String epy) {
		this.epy = epy;
	}
	public void setOpinion1(String opinion1) {
		this.opinion1 = opinion1;
	}
	public void setOpinion2(String opinion2) {
		this.opinion2 = opinion2;
	}
	public void setOpinion3(String opinion3) {
		this.opinion3 = opinion3;
	}
	public void setOpinion4(String opinion4) {
		this.opinion4 = opinion4;
	}
	public void setOpinion5(String opinion5) {
		this.opinion5 = opinion5;
	}
	public void setLcmap(String lcmap) {
		this.lcmap = lcmap;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setPhotoTag(String photoTag) {
		this.photoTag = photoTag;
	}
	public void setPhotoTagList(String photoTagList) {
		this.photoTagList = photoTagList;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public void setSvyLatLon(String svyLatLon) {
		this.svyLatLon = svyLatLon;
	}
	public void setSvyYear(String svyYear) {
		this.svyYear = svyYear;
	}
	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}
	public void setSvyMonth(String svyMonth) {
		this.svyMonth = svyMonth;
	}
	public void setChangedZoom(String changedZoom) {
		this.changedZoom = changedZoom;
	}
	public void setLocImgIdx(int locImgIdx) {
		this.locImgIdx = locImgIdx;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public void setPhotoLen(int photoLen) {
		this.photoLen = photoLen;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setLgllimit(String lgllimit) {
		this.lgllimit = lgllimit;
	}
	public void setCommmodemnum(String commmodemnum) {
		this.commmodemnum = commmodemnum;
	}
	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}
	public String[] getWireextIdList() {
		return wireextIdList;
	}
	public String[] getLicmeterIdList() {
		return licmeterIdList;
	}
	public String[] getGwgaugeIdList() {
		return gwgaugeIdList;
	}
	public String[] getRaingaugeIdList() {
		return raingaugeIdList;
	}
	public String[] getStrcdpmIdList() {
		return strcdpmIdList;
	}
	public String[] getSurdpmIdList() {
		return surdpmIdList;
	}
	public String[] getGpsgaugeIdList() {
		return gpsgaugeIdList;
	}
	public String[] getGatewayIdList() {
		return gatewayIdList;
	}
	public String[] getNodeIdList() {
		return nodeIdList;
	}
	public void setWireextIdList(String[] wireextIdList) {
		this.wireextIdList = wireextIdList;
	}
	public void setLicmeterIdList(String[] licmeterIdList) {
		this.licmeterIdList = licmeterIdList;
	}
	public void setGwgaugeIdList(String[] gwgaugeIdList) {
		this.gwgaugeIdList = gwgaugeIdList;
	}
	public void setRaingaugeIdList(String[] raingaugeIdList) {
		this.raingaugeIdList = raingaugeIdList;
	}
	public void setStrcdpmIdList(String[] strcdpmIdList) {
		this.strcdpmIdList = strcdpmIdList;
	}
	public void setSurdpmIdList(String[] surdpmIdList) {
		this.surdpmIdList = surdpmIdList;
	}
	public void setGpsgaugeIdList(String[] gpsgaugeIdList) {
		this.gpsgaugeIdList = gpsgaugeIdList;
	}
	public void setGatewayIdList(String[] gatewayIdList) {
		this.gatewayIdList = gatewayIdList;
	}
	public void setNodeIdList(String[] nodeIdList) {
		this.nodeIdList = nodeIdList;
	}
}