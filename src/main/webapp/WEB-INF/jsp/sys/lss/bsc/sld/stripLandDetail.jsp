<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssBsc.stripLandList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">기초조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 기초조사 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/bsc/sld/selectLssBscStripLandView.do" method="post">
			<input name="id" type="hidden" value="<c:out value="${result.id}" />">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.id" /></th><!-- 조사ID -->
							<td colspan="3"><c:out value='${result.id}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.type" /></th><!-- 조사유형 -->
							<td><c:out value="${result.typeNm}"/></td>
							<th><spring:message code="sysLssBsc.stripLandVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${result.year}"/>년</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.region1" /></th><!-- 관할1 -->
							<td><c:out value="${result.region1Nm}"/></td>
							<th><spring:message code="sysLssBsc.stripLandVO.region2" /></th><!-- 관할2 -->
							<td><c:out value="${result.region2Nm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.lat" /></th><!-- 위도 -->
							<td><c:out value="${result.lat}"/></td>
							<th><spring:message code="sysLssBsc.stripLandVO.lon" /></th><!-- 경도 -->
							<td><c:out value="${result.lon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.sd" /></th><!-- 시도 -->
							<td><c:out value="${result.sdNm}"/></td>
							<th><spring:message code="sysLssBsc.stripLandVO.sgg" /></th><!-- 시군구 -->
							<td><c:out value="${result.sggNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.emd" /></th><!-- 읍면동 -->
							<td><c:out value="${result.emdNm}"/></td>
							<th><spring:message code="sysLssBsc.stripLandVO.ri" /></th><!-- 리 -->
							<td><c:out value="${result.riNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.jibun" /></th><!-- 지번 -->
							<td colspan="3"><c:out value="${result.jibun}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.stripLandVO.createDt" /></th><!-- 등록일 -->
							<td colspan="3"><c:out value="${result.createDt}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:fncDeleteStripLand('<c:out value="${result.id}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateStripLandView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/sys/lss/bsc/sld/selectStripLandList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>