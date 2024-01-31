<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="mngCcmCca.cmmnCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 공통코드관리 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/mng/ccm/cca/updateCcmCmmnCodeView.do" method="post">
			<input name="codeId" type="hidden" value="<c:out value="${result.codeId}" />">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 분류코드명 -->
						<tr>
							<th><spring:message code="mngCcmCca.cmmnCodeVO.clCodeNm" /></th>
							<td><c:out value="${result.clCodeNm}"/></td>
						</tr>
						<!-- 코드ID -->
						<tr>
							<th><spring:message code="mngCcmCca.cmmnCodeVO.codeId" /></th>
							<td><c:out value="${result.codeId}"/></td>
						</tr>
						<!-- 코드ID명 -->
						<tr>
							<th><spring:message code="mngCcmCca.cmmnCodeVO.codeIdNm" /></th>
							<td><c:out value="${result.codeIdNm}"/></td>
						</tr>
						<!-- 코드ID설명 -->
						<tr>
							<th><spring:message code="mngCcmCca.cmmnCodeVO.codeIdDc" /></th>
							<td>
								<c:out value="${fn:replace(result.codeIdDc , crlf , '<br/>')}" escapeXml="false" />
							</td>
						</tr>
						<!-- 사용여부 -->
						<tr>
							<th><spring:message code="mngCcmCca.cmmnCodeVO.useAt" /></th>
							<td><c:out value="${result.useAt}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:fncDeleteCmmnCode('<c:out value="${result.codeId}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateCmmnCodeView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/ccm/cca/selectCcmCmmnCodeList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>