<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="articleManage" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngBbs.noticeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">공지사항관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 권한관리 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="noticeVO" action="${pageContext.request.contextPath}/mng/bbs/ntb/noticeRegist.do" method="post">
		<input type="hidden" name="bbsId" value="BBS_002" />
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption>${pageTitle} <spring:message code="title.create" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 제목 -->
				<c:set var="title"><spring:message code="mngBbs.noticeVO.regist.nttSj" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td class="left">
					    <form:input path="nttSj" title="${title} ${inputTxt}" size="40" maxlength="30" />
		   				<div><form:errors path="nttSj" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 내용 -->
				<c:set var="title"><spring:message code="mngBbs.noticeVO.regist.nttCn" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td class="left textarea">
					    <form:textarea path="nttCn" title="${title} ${inputTxt}" style="resize:none; min-height: 300px; width: 100%; border: 1px solid #ddd;"/>
		   				<div><form:errors path="nttCn" cssClass="error" /></div> 
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="save-btn" onclick="javascript:fncNoticeInsert();"><spring:message code="title.create" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/bbs/ntb/noticeList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>