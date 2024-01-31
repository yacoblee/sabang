<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="mngCcmCcc.cmmnClCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 공통분류코드관리 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/mng/ccm/ccc/updateCcmCmmnClCodeView.do" method="post">
			<input name="clCode" type="hidden" value="<c:out value="${result.clCode}" />">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 분류코드 -->
						<tr>
							<th><spring:message code="mngCcmCcc.cmmnClCodeVO.clCode" /></th>
							<td><c:out value="${result.clCode}"/></td>
						</tr>
						<!-- 분류코드명 -->
						<tr>
							<th><spring:message code="mngCcmCcc.cmmnClCodeVO.clCodeNm" /></th>
							<td><c:out value="${result.clCodeNm}"/></td>
						</tr>
						<!-- 분류코드설명 -->
						<tr>
							<th><spring:message code="mngCcmCcc.cmmnClCodeVO.clCodeDc" /></th>
							<td>
								<c:out value="${fn:replace(result.clCodeDc , crlf , '<br/>')}" escapeXml="false" />
							</td>
						</tr>
						<!-- 사용여부 -->
						<tr>
							<th><spring:message code="mngCcmCcc.cmmnClCodeVO.useAt" /></th>
							<td><c:out value="${result.useAt}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:fncDeleteCmmnClCode('<c:out value="${result.clCode}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateCmmnClCodeView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/ccm/ccc/selectCcmCmmnClCodeList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>