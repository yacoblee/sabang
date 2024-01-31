<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecRam.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 권한관리 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/mng/sec/ram/authorList.do" method="post">
		<input type="hidden" name="pageIndex" value="<c:out value='${authorManageVO.pageIndex}'/>"/>
		<input type="hidden" name="authorCode" value="<c:out value="${authorManage.authorCode}"/>"/>
		<input name="searchCondition" type="hidden" value="<c:out value='${authorManageVO.searchCondition}'/>"/>
		<input name="searchKeyword" type="hidden" value="<c:out value='${authorManageVO.searchKeyword}'/>"/>
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 권한코드 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.authorCode" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${authorManage.authorCode}"/></td>
				</tr>
				<!-- 권한명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.authorNm" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${authorManage.authorNm}"/></td>
				</tr>
				<!-- 설명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.authorDc" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${authorManage.authorDc}"/></td>
				</tr>
				<!-- 등록일 -->
				<c:set var="title"><spring:message code="table.regdate" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${authorManage.authorCreatDe}"/></td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="del-btn" onclick="javascript:fncDeleteAuthor('<c:out value="${authorManage.authorCode}"/>')"><spring:message code="title.delete" /></button>
				<button type="button" class="modify-btn" onclick="javascript:fncUpdateAuthorView('<c:out value="${authorManage.authorCode}"/>')"><spring:message code="title.update" /></button>
				<button type="button" class="list-btn" onclick="location.href='/mng/sec/ram/authorList.do'"><spring:message code="button.list" /></button>
			</div>
			</div>
		</div>
		</form>
	</div>
</div>