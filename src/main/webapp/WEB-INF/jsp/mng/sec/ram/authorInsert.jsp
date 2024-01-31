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
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 권한관리 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="authorManage" action="${pageContext.request.contextPath}/mng/sec/ram/authorInsert.do" method="post">
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption>${pageTitle} <spring:message code="title.create" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 권한코드 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.authorCode" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td class="left">
					    <form:input path="authorCode" title="${title} ${inputTxt}" size="40" maxlength="30" />
		   				<div><form:errors path="authorCode" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 권한명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.authorNm" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td class="left">
					    <form:input path="authorNm" title="${title} ${inputTxt}" size="40" maxlength="60" />
		   				<div><form:errors path="authorNm" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 설명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.authorDc" /></c:set>
				<tr>
					<th>${title}</th>
					<td class="left">
					    <form:input path="authorDc" title="${title} ${inputTxt}" size="40" maxlength="60" />
						<div><form:errors path="authorDc" cssClass="error" /></div> 
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="save-btn" onclick="javascript:fncAuthorInsert();"><spring:message code="title.create" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/sec/ram/authorList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>
<!-- 
<script type="text/javaScript" language="javascript">
function fncSelectAuthorList() {
	var varFrom = document.getElementById("authorManage");
	varFrom.action = "<c:url value='/sec/ram/EgovAuthorList.do'/>";
	varFrom.submit();
}

function fncAuthorInsert(form) {
	if(confirm("<spring:message code="common.regist.msg" />")){	//등록하시겠습니까?
        if(!validateAuthorManage(form)){
        	return false;
        }else{
        	form.submit();
        }
    }
}

function fncAuthorUpdate() {
	var varFrom = document.getElementById("authorManage");
	varFrom.action = "<c:url value='/sec/ram/EgovAuthorUpdate.do'/>";

	if(confirm("<spring:message code="common.regist.msg" />")){	//등록하시겠습니까?
        if(!validateAuthorManage(varFrom)){
            return;
        }else{
            varFrom.submit();
        }
    }
}

function fncAuthorDelete() {
	var varFrom = document.getElementById("authorManage");
	varFrom.action = "<c:url value='/sec/ram/EgovAuthorDelete.do'/>";
	if(confirm("<spring:message code="common.delete.msg" />")){ //삭제하시겠습니까?
	    varFrom.submit();
	}
}
</script> -->