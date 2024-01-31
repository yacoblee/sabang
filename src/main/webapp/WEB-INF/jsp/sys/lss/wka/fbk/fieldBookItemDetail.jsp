<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysLssWka.stripLandItemVO.title"/> <spring:message code="title.detail"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역실태조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">		
			<div class="BoardDetail">
				<fieldset>
				<form id="listForm" action="/sys/lss/wka/fbk/selectFieldBookItemUpdtView.do" method="post">
					<input name="smid" type="hidden" value="<c:out value='${result.smid}'/>">
					<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
				</form>
				</fieldset>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.sn" /></th><!-- 순번 -->
						<td><c:out value="${result.sn}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.svyType" /></th><!-- 조사유형 -->
						<td><c:out value="${result.svyType}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.bscId" /></th><!-- 기초조사ID -->
						<td><c:out value="${result.bscId}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.bscYear" /></th><!-- 기초조사수행연도 -->
						<td><c:out value="${result.bscYear}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.svyYear" /></th><!-- 조사연도 -->
						<td><c:out value="${result.svyYear}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.lndDgr" /></th><!-- 산사태위험등급 -->
						<td><c:out value="${result.lndDgr}"/></td>
					</tr>
					<tr>
						
						<th><spring:message code="sysLssWka.stripLandItemVO.spLat" /></th><!-- 시점위도 -->
						<td><c:out value="${result.spLatDeg}"/>°<fmt:formatNumber minIntegerDigits="2" type="number" value="${result.spLatMin}" />'<fmt:formatNumber pattern="#00.##" value="${result.spLatSec}" />"N</td>
						<th><spring:message code="sysLssWka.stripLandItemVO.spLon" /></th><!-- 시점경도 -->
						<td><c:out value="${result.spLonDeg}"/>°<fmt:formatNumber minIntegerDigits="2" type="number" value="${result.spLonMin}" />'<fmt:formatNumber pattern="#00.##" value="${result.spLonSec}" />"E</td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.epLat" /></th><!-- 종점위도 -->
						<td><c:out value="${result.epLatDeg}"/>°<fmt:formatNumber minIntegerDigits="2" type="number" value="${result.epLatMin}" />'<fmt:formatNumber pattern="#00.##" value="${result.epLatSec}" />"N</td>
						<th><spring:message code="sysLssWka.stripLandItemVO.epLon" /></th><!-- 종점경도 -->
						<td><c:out value="${result.epLonDeg}"/>°<fmt:formatNumber minIntegerDigits="2" type="number" value="${result.epLonMin}" />'<fmt:formatNumber pattern="#00.##" value="${result.epLonSec}" />"E</td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.region" /></th>
						<td><c:out value="${result.svyRegion1}"/><c:if test="${not empty result.svyRegion2}"> <c:out value="${result.svyRegion2}"/></c:if></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.addr" /></th>
						<td><c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/><c:if test="${not empty result.svyRi}"> <c:out value="${result.svyRi}"/></c:if> <c:out value="${result.svyJibun}"/></td>
					</tr>
					<c:if test="${result.svyType eq '산사태'}">
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.soilDepth" /></th><!-- 토심 -->
						<td><c:out value="${result.soilDepth}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.soilCls" /></th><!-- 토성 -->
						<td><c:out value="${result.soilCls}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.prntRock" /></th><!-- 모암 -->
						<td><c:out value="${result.prntRock}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.aspectLen" /></th><!-- 사면길이 -->
						<td><c:out value="${result.aspectLen}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.minHeight" /></th><!-- 최저높이 -->
						<td><c:out value="${result.minHeight}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.maxHeight" /></th><!-- 최고높이 -->
						<td><c:out value="${result.maxHeight}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.drySeason" /></th><!-- 건기 -->
						<td><c:out value="${result.drySeason}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.rainSeason" /></th><!-- 우기 -->
						<td><c:out value="${result.rainSeason}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.altdeDifnce" /></th><!-- 고도차 -->
						<td colspan="3"><c:out value="${result.altdeDifnce}"/></td>
					</tr>
					</c:if>
					<c:if test="${result.svyType eq '토석류'}">
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.basinArea" /></th><!-- 유역면적 -->
						<td><c:out value="${result.basinArea}"/></td>
						<th><spring:message code="sysLssWka.stripLandItemVO.mntTorntLen" /></th><!-- 계류길이 -->
						<td><c:out value="${result.mntTorntLen}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.soilDepth" /></th><!-- 토심 -->
						<td colspan="3"><c:out value="${result.soilDepth}"/></td>
					</tr>
					</c:if>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookItem('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button>
							<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookItemView('<c:out value="${result.smid}"/>');"><spring:message code="title.update" /></button>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<c:if test="${access eq 'ADMIN' }">
								<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookItem('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button>
							</c:if>
							<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
								<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookItemView('<c:out value="${result.smid}"/>');"><spring:message code="title.update" /></button>
							</c:if>
						</sec:authorize>
						<button type="button" class="list-btn" onclick="location.href='/sys/lss/wka/fbk/selectFieldBookList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
	</div>
</div>
<script>
function linkPage(pageNo){
	$("input[name=pageSubIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>