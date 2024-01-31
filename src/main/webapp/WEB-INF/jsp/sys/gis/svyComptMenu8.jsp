<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<!-- 오늘일자 -->
<c:set var="nowDt"><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM"/></c:set>
<!-- 정밀점검 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="pcsSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="pcsSvyComptSvyType"><spring:message code="sysGis.stripLandVO.type"/></label>
			<select id="pcsSvyComptSvyType"  name="pcsSvyComptSvyType" title="<spring:message code="sysGis.stripLandVO.type"/>" class="SvyComptType w2">
				<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<option value="콘크리트 사방댐 정밀점검">콘크리트사방댐</option>
				<option value="강재 사방댐 정밀점검">강재사방댐</option>
				<option value="석재 사방댐 정밀점검">석재사방댐</option>
				<option value="계류보전 정밀점검">계류보전</option>
				<option value="산지사방 정밀점검">산지사방</option>
				<option value="해안방재림 정밀점검">해안방재림</option>
				<option value="해안침식방지 정밀점검">해안침식방지</option>
			</select>
			
			<label for="pcsSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<input type="text" id= "pcsSvyComptStartDt" class="SvyComptStartDt w3" name="pcsSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<input type="text" id= "pcsSvyComptEndDt" class="SvyComptEndDt w3" name="pcsSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="pcsSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="pcsSvyComptSd" class="SvyComptSd w4" name="pcsSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="pcsSvyComptSgg" class="SvyComptSgg w4" name="pcsSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="pcsSvyComptEmd" class="SvyComptEmd w4" name="pcsSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="pcsSvyComptRi" class="SvyComptRi w4" name="pcsSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
				<input type="text" id="pcsSvyComptJibun" name="pcsSvyComptJibun"/>
		</div>
		<div>
			<label for="pcsSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="pcsSvyComptId" name="pcsSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="pcsSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="pcsSvyComptsvyUser" name="pcsSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="pcsSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="pcsSvyComptMstNm" name="pcsSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div class="SvyComptInput">
			<label for="pcsSvyComptEcnrrNum"><spring:message code="sysGis.svyComptVO.ecnrrnum"/></label><!-- 관리번호 -->
			<input type="text" id="pcsSvyComptEcnrrNum" name="pcsSvyComptEcnrrNum" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="pcsSvyComptNationSpotNum"><spring:message code="sysGis.svyComptVO.nationspotnum"/></label><!-- 국가지점번호 -->
			<input type="text" id="pcsSvyComptNationSpotNum" name="pcsSvyComptNationSpotNum" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="pcsSvyComptFcltYear"><spring:message code="sysGis.svyComptVO.fcltyear"/></label><!-- 시설년도 -->
			<select id="pcsSvyComptFcltYear" name="pcsSvyComptFcltYear" title="<spring:message code="sysGis.svyComptVO.fcltyear"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate value="${now}" pattern="yyyy" var="startYear"/>
				<c:forEach begin="0" end="${startYear - 2000}" var="year" step="1">
				<option value="${startYear-year}">${startYear-year}</option>
				</c:forEach>
			</select>
		</div>
		
		<!-- 콘크리트사방댐 -->
		<div class="SvyComptInput Hidden pcs1 pcs3">
			<label for="pcsSvyComptOrginlDamDmg" ><spring:message code="sysGis.svyComptVO.orginldamdmg"/></label><!-- 1.본댐(파손,변위,변형) -->
			<select id="pcsSvyComptOrginlDamDmg" name="pcsSvyComptOrginlDamDmg" title="<spring:message code="sysGis.svyComptVO.orginldamdmg"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="5% 미만">5% 미만</option>
				<option value="5~15% 미만">5~15% 미만</option>
				<option value="15% 이상">15% 이상</option>
			</select>
			<label for="pcsSvyComptOrginlDamCrk" ><spring:message code="sysGis.svyComptVO.orginldamcrk"/></label><!-- 2.본댐(균열,누수) -->
			<select id="pcsSvyComptOrginlDamCrk" name="pcsSvyComptOrginlDamCrk" title="<spring:message code="sysGis.svyComptVO.orginldamcrk"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="가로균열이 댐길이의 10% 미만">가로균열이 댐길이의 10% 미만</option>
				<option value="가로균열이 댐길이의 10~30% 미만">가로균열이 댐길이의 10~30% 미만</option>
				<option value="가로균열이 댐길이의 30% 이상">가로균열이 댐길이의 30% 이상</option>
				<option value="세로균열이 유효고의 10% 미만">세로균열이 유효고의 10% 미만</option>
				<option value="세로균열이 유효고의 10~30% 미만">세로균열이 유효고의 10~30% 미만</option>
				<option value="세로균열이 유효고의 30% 이상">세로균열이 유효고의 30% 이상</option>
				<option value="누수 없음">누수 없음</option>
				<option value="누수흔적 관찰">누수흔적 관찰</option>
				<option value="누수 관찰">누수 관찰</option>
			</select>
			<label for="pcsSvyComptOrginlDamBasicScour" ><spring:message code="sysGis.svyComptVO.orginldambasicscour"/></label><!-- 3.본댐(기초부세굴,물받이유탈) -->
			<select id="pcsSvyComptOrginlDamBasicScour" name="pcsSvyComptOrginlDamBasicScour" title="<spring:message code="sysGis.svyComptVO.orginldambasicscour"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호 (보수 및 보강 불필요)">양호 (보수 및 보강 불필요)</option>
				<option value="보통 (경미결함 보수필요)">보통 (경미결함 보수필요)</option>
				<option value="불량 (중대결함 보강필요)">불량 (중대결함 보강필요)</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden pcs1 pcs3">
			<label for="pcsSvyComptOrginlDamWtgate" ><spring:message code="sysGis.svyComptVO.orginldamwtgate"/></label><!-- 5.본댐(수문시설상태) -->
			<select id="pcsSvyComptOrginlDamWtgate" name="pcsSvyComptOrginlDamWtgate" title="<spring:message code="sysGis.svyComptVO.orginldamwtgate"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호 (보수 및 보강 불필요)">양호 (보수 및 보강 불필요)</option>
				<option value="보통 (경미결함 보수필요)">보통 (경미결함 보수필요)</option>
				<option value="불량 (중대결함 보강필요)">불량 (중대결함 보강필요)</option>
			</select>
			<label for="pcsSvyComptSidewallDmg" ><spring:message code="sysGis.svyComptVO.sidewalldmg"/></label><!-- 1.측벽(파손,변위,변형) -->
			<select id="pcsSvyComptSidewallDmg" name="pcsSvyComptSidewallDmg" title="<spring:message code="sysGis.svyComptVO.sidewalldmg"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="5% 미만">5% 미만</option>
				<option value="5~20% 미만">5~20% 미만</option>
				<option value="20% 이상">20% 이상</option>
			</select>
			<label for="pcsSvyComptSidewallCrk" ><spring:message code="sysGis.svyComptVO.sidewallcrk"/></label><!-- 2.측벽(균열,누수) -->
			<select id="pcsSvyComptSidewallCrk" name="pcsSvyComptSidewallCrk" title="<spring:message code="sysGis.svyComptVO.sidewallcrk"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="균열 5% 미만">균열 5% 미만</option>
				<option value="균열 5~20% 미만">균열 5~20% 미만</option>
				<option value="균열 20% 이상">균열 20% 이상</option>
				<option value="누수 없음">누수 없음</option>
				<option value="누수흔적 관찰">누수흔적 관찰</option>
				<option value="누수 관찰">누수 관찰</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden pcs1 pcs3">
			<label for="pcsSvyComptSidewallBasicScour" ><spring:message code="sysGis.svyComptVO.sidewallbasicscour"/></label><!-- 3.측벽(기초부세굴,물받이) -->
			<select id="pcsSvyComptSidewallBasicScour" name="pcsSvyComptSidewallBasicScour" title="<spring:message code="sysGis.svyComptVO.sidewallbasicscour"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호 (보수 및 보강 불필요)">양호 (보수 및 보강 불필요)</option>
				<option value="보통 (경미결함 보수필요)">보통 (경미결함 보수필요)</option>
				<option value="불량 (중대결함 보강필요)">불량 (중대결함 보강필요)</option>
			</select>
			<label for="pcsSvyComptDwnSptDmg" ><spring:message code="sysGis.svyComptVO.dwnsptdmg"/></label><!-- 1.물받이/물방석(파손,변위,변형) -->
			<select id="pcsSvyComptDwnSptDmg" name="pcsSvyComptDwnSptDmg" title="<spring:message code="sysGis.svyComptVO.dwnsptdmg"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="5% 미만">5% 미만</option>
				<option value="5~20% 미만">5~20% 미만</option>
				<option value="20% 이상">20% 이상</option>
			</select>
			<label for="pcsSvyComptDwnSptBasicScour" ><spring:message code="sysGis.svyComptVO.dwnsptbasicscour"/></label><!-- 2.물받이/물방석(기초부세굴,수직벽유탈) -->
			<select id="pcsSvyComptDwnSptBasicScour" name="pcsSvyComptDwnSptBasicScour" title="<spring:message code="sysGis.svyComptVO.dwnsptbasicscour"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호 (보수 및 보강 불필요)">양호 (보수 및 보강 불필요)</option>
				<option value="보통 (경미결함 보수필요)">보통 (경미결함 보수필요)</option>
				<option value="불량 (중대결함 보강필요)">불량 (중대결함 보강필요)</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden pcs1 pcs3">
			<label for="pcsSvyComptDwnSptCrk" ><spring:message code="sysGis.svyComptVO.dwnsptcrk"/></label><!-- 3.물받이/물방석(균열,재료상태) -->
			<select id="pcsSvyComptDwnSptCrk" name="pcsSvyComptDwnSptCrk" title="<spring:message code="sysGis.svyComptVO.dwnsptcrk"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="균열 5% 미만">균열 5% 미만</option>
				<option value="균열 5~20% 미만">균열 5~20% 미만</option>
				<option value="균열 20% 이상">균열 20% 이상</option>
				<option value="재료의 상태 양호">재료의 상태 양호</option>
				<option value="재료의 상태 보통">재료의 상태 보통</option>
				<option value="재료의 상태 불량">재료의 상태 불량</option>
			</select>
		</div>
<!-- 		<div class="SvyComptInput Hidden pcs1"> -->
<%-- 			<label for="pcsSvyComptSvyCrctn" ><spring:message code="sysGis.svyComptVO.svycrctn"/></label><!-- 조사자보정 --> --%>
<%-- 			<select id="pcsSvyComptSvyCrctn" name="pcsSvyComptSvyCrctn" title="<spring:message code="sysGis.svyComptVO.svycrctn"/>" class="mo_mt5 input_null w1"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="경미한 결함으로서 조치 불필요">경미한 결함으로서 조치 불필요</option> -->
<!-- 				<option value="경미한 결함으로서 간단한 조치 필요">경미한 결함으로서 간단한 조치 필요</option> -->
<!-- 				<option value="중대한 결함이지만 단순한 조치 필요">중대한 결함이지만 단순한 조치 필요</option> -->
<!-- 				<option value="중대한 결함이 있거나 경미한 결함이 다수 존재하여 반드시 조치 필요">중대한 결함이 있거나 경미한 결함이 다수 존재하여 반드시 조치 필요</option> -->
<!-- 			</select> -->
<!-- 		</div> -->
		
<!-- 		<div class="SvyComptInput Hidden pcs1"> -->
<%-- 			<label for="pcsSvyComptCncrtcmprsstrn" ><spring:message code="sysGis.svyComptVO.cncrtcmprsstrn"/></label><!-- 비파괴검사(슈미트해머) --> --%>
<%-- 			<select id="pcsSvyComptCncrtcmprsstrn" name="pcsSvyComptCncrtcmprsstrn" title="<spring:message code="sysGis.svyComptVO.cncrtcmprsstrn"/>" class="mo_mt5 input_null w2"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="설계기준강도 21MPa 이상">설계기준강도 21MPa 이상</option> -->
<!-- 				<option value="설계기준강도 21MPa 미만">설계기준강도 21MPa 미만</option> -->
<!-- 				<option value="만족">만족</option> -->
<!-- 				<option value="미달 (등급 조정 가능)">미달 (등급 조정 가능)</option> -->
<!-- 			</select> -->
<%-- 			<label for="pcsSvyComptCncrtcrkdpt" ><spring:message code="sysGis.svyComptVO.cncrtcrkdpt"/></label><!-- 비파괴검사(초음파탐상) --> --%>
<%-- 			<select id="pcsSvyComptCncrtcrkdpt" name="pcsSvyComptCncrtcrkdpt" title="<spring:message code="sysGis.svyComptVO.cncrtcrkdpt"/>" class="mo_mt5 input_null w2"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="부재 두께의 50% 미만">부재 두께의 50% 미만</option> -->
<!-- 				<option value="부재 두께의 50% 이상">부재 두께의 50% 이상</option> -->
<!-- 				<option value="만족">만족</option> -->
<!-- 				<option value="미달 (등급 조정 가능)">미달 (등급 조정 가능)</option> -->
<!-- 			</select> -->
<!-- 		</div> -->
		
<!-- 		<div class="SvyComptInput Hidden pcs1"> -->
<%-- 			<label for="pcsSvyComptNowSnddpsitValDivision" ><spring:message code="sysGis.svyComptVO.nowsnddpsitvaldivision"/></label><!-- 1.현재저사량 --> --%>
<%-- 			<select id="pcsSvyComptNowSnddpsitValDivision" name="pcsSvyComptNowSnddpsitValDivision" title="<spring:message code="sysGis.svyComptVO.nowsnddpsitvaldivision"/>" class="mo_mt5 input_null w3"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="➀ 고 (80% 이상)">➀ 고 (80% 이상)</option> -->
<!-- 				<option value="➁ 중 (50%~79%)">➁ 중 (50%~79%)</option> -->
<!-- 				<option value="➂ 저 (50% 미만)">➂ 저 (50% 미만)</option> -->
<!-- 			</select> -->
<%-- 			<label for="pcsSvyComptLifeZoneDivision" ><spring:message code="sysGis.svyComptVO.lifezonedivision"/></label><!-- 2.생활권 --> --%>
<%-- 			<select id="pcsSvyComptLifeZoneDivision" name="pcsSvyComptLifeZoneDivision" title="<spring:message code="sysGis.svyComptVO.lifezonedivision"/>" class="mo_mt5 input_null w3"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="➀ 단 (500m 이하)">➀ 단 (500m 이하)</option> -->
<!-- 				<option value="➁ 중 (501m~999m)">➁ 중 (501m~999m)</option> -->
<!-- 				<option value="➂ 장 (1000m 이상)">➂ 장 (1000m 이상)</option> -->
<!-- 			</select> -->
<%-- 			<label for="pcsSvyComptRiverBedGradientDivision" ><spring:message code="sysGis.svyComptVO.riverbedgradientdivision"/></label><!-- 3.계상물매 --> --%>
<%-- 			<select id="pcsSvyComptRiverBedGradientDivision" name="pcsSvyComptRiverBedGradientDivision" title="<spring:message code="sysGis.svyComptVO.riverbedgradientdivision"/>" class="mo_mt5 input_null w3"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="➀ 급 (10% 이상)">➀ 급 (10% 이상)</option> -->
<!-- 				<option value="➁ 경 (5%~9%)">➁ 경 (5%~9%)</option> -->
<!-- 				<option value="➂ 완 (5% 미만)">➂ 완 (5% 미만)</option> -->
<!-- 			</select> -->
<!-- 		</div> -->
		
<!-- 		<div class="SvyComptInput Hidden pcs1"> -->
<%-- 			<label for="pcsSvyComptSoilMoveamntDivision" ><spring:message code="sysGis.svyComptVO.soilmoveamntdivision"/></label><!-- 4.토석이동량 --> --%>
<%-- 			<select id="pcsSvyComptSoilMoveamntDivision" name="pcsSvyComptSoilMoveamntDivision" title="<spring:message code="sysGis.svyComptVO.soilmoveamntdivision"/>" class="mo_mt5 input_null w3"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="➀ 다 (70% 이상)">➀ 다 (70% 이상)</option> -->
<!-- 				<option value="➁ 중 (30% ~ 69%)">➁ 중 (30% ~ 69%)</option> -->
<!-- 				<option value="➂ 소 (30% 미만)">➂ 소 (30% 미만)</option> -->
<!-- 			</select> -->
<%-- 			<label for="pcsSvyComptDrdgnatJdg" ><spring:message code="sysGis.svyComptVO.drdgnatjdg"/></label><!-- 사방댐준설여부판정 --> --%>
<%-- 			<select id="pcsSvyComptDrdgnatJdg" name="pcsSvyComptDrdgnatJdg" title="<spring:message code="sysGis.svyComptVO.drdgnatjdg"/>" class="mo_mt5 input_null w3"> --%>
<%-- 				<option value=""><spring:message code="title.all" /></option> --%>
<!-- 				<option value="준설시행 (17점 이상)">준설시행 (17점 이상)</option> -->
<!-- 				<option value="준설불요 (17점 미만)">준설불요 (17점 미만)</option> -->
<!-- 			</select> -->
<!-- 		</div> -->
	</li>
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="pcsSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btnaprSearch" class="SvyComptBtn" name="pcsSvyComptSearch">검색</button>
        	<button type="button" id="btnaprReset" class="SvyComptBtnReset" name="pcsSvyComptReset">초기화</button>
		</div>
	</li>
	
	<!-- 조회 결과 S-->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
			<p class="txtsearch pcs">검색결과 총 <span class="txtblue">0</span> 건</p>
			<table id="pcsSchRsltLst" summary="임도타당성평가 상세검색에 대한 결과를 리스트로 출력합니다.">
					<caption>검색결과리스트</caption>
					<colgroup>
						<col style="width:10%;">
						<col style="width:20%;">
						<col style="width:50%;">
						<col style="width:20%;">
					</colgroup>
					<thead>
	                	<tr>
	                		<th>번호</th>
	                		<th>조사종류</th>
	                		<th>조사지역</th>
	                		<th>더보기</th>
	                	</tr>
	                </thead>					
					<tbody>
					
					</tbody>
				</table>	
				<div class="map_Page">
				
				</div>			
			</div>
		</div>					
		<!-- 조회 결과 E-->	
	</li>	
	</form>
</ul>