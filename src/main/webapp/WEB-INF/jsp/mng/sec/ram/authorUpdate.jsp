<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="authorManage" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngSecRam.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 권한관리 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="authorManage" action="/mng/sec/ram/authorUpdate.do" method="post">
		<input type="hidden" name="authorCode" value="<c:out value='${authorManageVO.authorCode}'/>"/>
		<input type="hidden" name="searchCondition" value="<c:out value='${authorManageVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${authorManageVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${authorManageVO.pageIndex}'/>"/>
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 권한코드 -->
				<c:set var="title"><spring:message code="comCopSecRam.regist.authorCode" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
					    <c:out value="${authorManageVO.authorCode}"/>
					</td>
				</tr>
				<!-- 권한명 -->
				<c:set var="title"><spring:message code="comCopSecRam.regist.authorNm" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
					    <form:input path="authorNm" title="${title} ${inputTxt}" size="40" maxlength="60" />
		   				<div><form:errors path="authorNm" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 설명 -->
				<c:set var="title"><spring:message code="comCopSecRam.regist.authorDc" /></c:set>
				<tr>
					<th>${title}</th>
					<td>
					    <form:input path="authorDc" title="${title} ${inputTxt}" size="40" maxlength="60" />   
						<div><form:errors path="authorDc" cssClass="error" /></div> 
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fncAuthorUpdate();"><spring:message code="title.update" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/sec/ram/authorList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>