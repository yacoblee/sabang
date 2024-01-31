<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="mngCcmCde.cmmnDetailCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 공통분류코드관리 상세조회 -->
	<div id="contents">
		<form id="listForm" action="<c:url value='/mng/ccm/cde/updateCcmCmmnDetailCodeView.do'/>" method="post">
			<input name="codeId" type="hidden" value="<c:out value="${result.codeId}" />">
			<input name="code" type="hidden" value="<c:out value="${result.code}" />">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 코드ID명 -->
						<tr>
							<th><spring:message code="mngCcmCde.cmmnDetailCodeVO.codeIdNm" /></th>
							<td><c:out value="${result.codeIdNm}"/></td>
						</tr>
						<!-- 상세코드 -->
						<tr>
							<th><spring:message code="mngCcmCde.cmmnDetailCodeVO.code" /></th>
							<td><c:out value="${result.code}"/></td>
						</tr>
						<!-- 상세코드명 -->
						<tr>
							<th><spring:message code="mngCcmCde.cmmnDetailCodeVO.codeNm" /></th>
							<td><c:out value="${result.codeNm}"/></td>
						</tr>
						<!-- 상세코드설명 -->
						<tr>
							<th><spring:message code="mngCcmCde.cmmnDetailCodeVO.codeDc" /></th>
							<td>
								<c:out value="${fn:replace(result.codeDc , crlf , '<br/>')}" escapeXml="false" />
							</td>
						</tr>
						<!-- 사용여부 -->
						<tr>
							<th><spring:message code="mngCcmCde.cmmnDetailCodeVO.useAt" /></th>
							<td><c:out value="${result.useAt}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:fncDeleteCmmnDetailCode('<c:out value="${result.codeId}"/>','<c:out value="${result.code}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateCmmnDetailCodeView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/ccm/cde/selectCcmCmmnDetailCodeList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>