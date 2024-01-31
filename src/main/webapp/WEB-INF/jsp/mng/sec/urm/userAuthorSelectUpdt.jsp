<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecUrm.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 권한부여관리 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="userAuthor" action="/mng/sec/urm/userAuthorUpdate.do" method="post">
		<input type="hidden" name="uniqId" value="<c:out value='${userAuthorVO.uniqId}'/>"/>
		<input type="hidden" name="searchCondition" value="<c:out value='${userAuthorVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${userAuthorVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${userAuthorVO.pageIndex}'/>"/>
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 아이디 -->
				<c:set var="title"><spring:message code="mngSecUrm.list.id" /></c:set>
				<tr>
					<th>${title}</th>
					<td>
					    <c:out value="${userAuthor.userId}"/>
					</td>
				</tr>
				<!-- 사용자명 -->
				<c:set var="title"><spring:message code="mngSecUrm.list.name" /></c:set>
				<tr>
					<th>${title}</th>
					<td>
					    <c:out value="${userAuthor.userNm}"/>
					</td>
				</tr>
				<!-- 권한명 -->
				<c:set var="title"><spring:message code="mngSecUrm.list.authorNm" /></c:set>
				<tr>
					<th>${title}</th>
					<td>
						<form:select path="authorCode">
							<form:options items="${authorManageList}" itemValue="authorCode" itemLabel="authorNm"/>
						</form:select>
						<div><form:errors path="authorCode" cssClass="error" /></div> 
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="modify-btn" onclick="javascript:fncUserAuthorUpdate(); return false;"><spring:message code="title.update" /></button>
				<button type="button" class="list-btn" onclick="javascript:fnList('/mng/sec/urm/userAuthorList.do'); return false;"><spring:message code="button.list" /></button>
			</div>
			</div>
		</div>
		</form:form>
	</div>
</div>