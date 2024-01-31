<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="noticeVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngBbs.noticeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">공지사항관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 권한관리 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="noticeVO" action="/mng/bbs/ntb/noticeUpdate.do" method="post">
		<input type="hidden" name="nttId" value="<c:out value='${noticeVO.nttId}'/>"/>
		<input type="hidden" name="bbsId" value="<c:out value='${noticeVO.bbsId}'/>"/>
		<input type="hidden" name="searchCondition" value="<c:out value='${noticeVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${noticeVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${noticeVO.pageIndex}'/>"/> 
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 글 제목 -->
				<c:set var="title"><spring:message code="mngBbs.noticeVO.updt.nttSj" /></c:set>
				<tr>
					<th><label for="nttSj">${title}<span class="pilsu">*</span></label></th>
					<td class="left">
						<form:input path="nttSj" title="${title} ${inputTxt }" size="70" maxlength="70" />
						<div>	<form:errors path="nttSj" cssClass="error" /></div>
					</td>
				</tr>
				<!-- 작성자 -->
				<c:set var="title"><spring:message code="table.reger" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
					    <c:out value="${noticeVO.frstRegisterId}"/>
					</td>
				</tr>
				<!-- 글 내용  -->
				<c:set var="title"><spring:message code="mngBbs.noticeVO.updt.nttCn" /></c:set>
				<tr>
					<th><label for="nttCn">${title}<span class="pilsu">*</span></label></th>
					<td class="nopd" colspan="3">
						<form:textarea path="nttCn" title="${title} ${inputTxt}" cols="300" rows="20" style="resize:none; min-height: 300px; width: 100%; border: 1px solid #ddd;" />
						
						<div><form:errors path="nttCn" cssClass="error" /></div>
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fncNoticeUpdate();"><spring:message code="title.update" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/bbs/ntb/noticeList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>