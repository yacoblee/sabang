<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecRmt.hierarchy.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 롤상하관계관리 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="roleHierarchyManage" action="${pageContext.request.contextPath}/mng/sec/rhm/roleHierarchyInsert.do" method="post">
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption>${pageTitle} <spring:message code="title.create" /></caption>
			<tbody>
				<!-- 상위 롤 -->
				<c:set var="title"><spring:message code="mngSecRmt.hierarchy.list.parntsRole" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<form:select path="parntsRoleCode">
							<form:option value="">--- 상위 롤 선택 ---</form:option>
							<form:options items="${authorManageList}" itemValue="authorCode" itemLabel="authorNm"/>
						</form:select>
						<div><form:errors path="parntsRoleCode" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 하위 롤 -->
				<c:set var="title"><spring:message code="mngSecRmt.hierarchy.list.chldrnRole" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<form:select path="chldrnRoleCode">
							<form:option value="">--- 하위 롤 선택 ---</form:option>
							<form:options items="${authorManageList}" itemValue="authorCode" itemLabel="authorNm"/>
						</form:select>
						<div><form:errors path="chldrnRoleCode" cssClass="error" /></div> 
					</td>
				</tr>
			</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="save-btn" onclick="javascript:fncRoleHierarchyInsert();"><spring:message code="title.create" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/sec/rhm/roleHierarchyList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>