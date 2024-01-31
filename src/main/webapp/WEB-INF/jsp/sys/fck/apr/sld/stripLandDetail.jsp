<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysFckApr.stripLandList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">외관점검</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 기초조사 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/fck/apr/sld/selectFckAprStripLandView.do" method="post">
			<input name="id" type="hidden" value="<c:out value="${result.id}" />">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.id" /></th><!-- 조사ID -->
							<td colspan="3"><c:out value='${result.id}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.type" /></th><!-- 조사유형 -->
							<td><c:out value="${result.typeNm}"/></td>
							<th><spring:message code="sysFckApr.stripLandVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${result.year}"/>년</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.lat" /></th><!-- 위도 -->
							<td><c:out value="${result.lat}"/></td>
							<th><spring:message code="sysFckApr.stripLandVO.lon" /></th><!-- 경도 -->
							<td><c:out value="${result.lon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.sd" /></th><!-- 시도 -->
							<td><c:out value="${result.sdNm}"/></td>
							<th><spring:message code="sysFckApr.stripLandVO.sgg" /></th><!-- 시군구 -->
							<td><c:out value="${result.sggNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.emd" /></th><!-- 읍면동 -->
							<td><c:out value="${result.emdNm}"/></td>
							<th><spring:message code="sysFckApr.stripLandVO.ri" /></th><!-- 리 -->
							<td><c:out value="${result.riNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.jibun" /></th><!-- 지번 -->
							<td colspan="3"><c:out value="${result.jibun}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.stripLandVO.createDt" /></th><!-- 등록일 -->
							<td colspan="3"><c:out value="${result.createDt}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:fncDeleteStripLand('<c:out value="${result.id}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateStripLandView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/sys/fck/apr/sld/selectStripLandList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>