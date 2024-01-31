<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecRmt.hierarchy.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form id="listForm" action="/mng/sec/rhm/roleHierarchyList.do" method="post">
			<input type="hidden" name="pageIndex" value="<c:out value='${role.pageIndex}'/>"/>
			<div class="BoardList">
				<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
					<colgroup>
						<col style="width: 50%;">
						<col style="width: 50%;">
					</colgroup>
					<thead>
						<tr>
							<th><spring:message code="mngSecRmt.hierarchy.list.parntsRole" /></th><!-- 상위 롤 -->
							<th><spring:message code="mngSecRmt.hierarchy.list.chldrnRole" /></th><!-- 하위 롤 -->
					  	</tr>
					</thead>
					<tbody class="ov">
					<c:if test="${fn:length(roleHierarchyList) == 0}">
						<tr>
							<td colspan="2"><spring:message code="common.nodata.msg" /></td>
						</tr>
						</c:if>
						<c:forEach var="role" items="${roleHierarchyList}" varStatus="status">
						<tr>
							<td><c:out value="${role.parntsRoleCode}"/></td>
							<td><c:out value="${role.chldrnRoleCode}"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="write-btn" onclick="javascript:fncAddRoleHierarchyInsert();"><spring:message code="button.create" /></button>
					</div>
				</div>
				<div class="Page">
					<c:if test="${not empty paginationInfo}">
					<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPage"/>
					</c:if>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>

function linkPage(pageNo){
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>