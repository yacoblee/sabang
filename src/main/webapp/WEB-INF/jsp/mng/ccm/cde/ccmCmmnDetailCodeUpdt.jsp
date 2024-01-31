<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="cmmnDetailCodeVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngCcmCde.cmmnDetailCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 공통코드관리 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="cmmnDetailCodeVO" action="${pageContext.request.contextPath}/mng/ccm/cde/updateCcmCmmnDetailCode.do" method="post">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 입력 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<c:set var="inputYes"><spring:message code="input.yes" /></c:set>
						<c:set var="inputNo"><spring:message code="input.no" /></c:set>
						
						<!-- 코드ID -->
						<c:set var="title"><spring:message code="mngCcmCde.cmmnDetailCodeVO.codeId"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
							    <form:input path="codeId" title="${title} ${inputSelect}" readonly="true"/>
				   				<div><form:errors path="codeId" cssClass="error" /></div>     
							    
							</td>
						</tr>
						
						<!-- 상세코드 -->
						<c:set var="title"><spring:message code="mngCcmCde.cmmnDetailCodeVO.code"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
							    <form:input path="code" title="${title} ${inputTxt}" size="70" maxlength="70" readonly="true" />
				   				<div><form:errors path="code" cssClass="error" /></div>     
							</td>
						</tr>
						
						<!-- 상세코드명 -->
						<c:set var="title"><spring:message code="mngCcmCde.cmmnDetailCodeVO.codeNm"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
							    <form:input path="codeNm" title="${title} ${inputTxt}" size="70" maxlength="70" />
				   				<div><form:errors path="codeNm" cssClass="error" /></div>     
							</td>
						</tr>
						
						<!-- 상세코드설명 -->
						<c:set var="title"><spring:message code="mngCcmCde.cmmnDetailCodeVO.codeDc"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
								<form:input path="codeDc" title="${title} ${inputTxt}" size="70" maxlength="70" />
								<div><form:errors path="codeDc" cssClass="error" /></div>  
							</td>
						</tr>
						
						<!-- 사용여부 -->
						<c:set var="title"><spring:message code="mngCcmCde.cmmnDetailCodeVO.useAt"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
								<form:select path="useAt" title="${title} ${inputTxt }" cssClass="txt">
									<form:option value="Y"  label=" ${inputYes}"/>
									<form:option value="N"  label=" ${inputNo}"/>
								</form:select>
								<div><form:errors path="useAt" cssClass="error" /></div>       
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncCmmnDetailCodeUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/ccm/cde/selectCcmCmmnDetailCodeList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>