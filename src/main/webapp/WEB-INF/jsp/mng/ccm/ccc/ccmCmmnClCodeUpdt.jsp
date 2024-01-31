<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="cmmnClCodeVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngCcmCcc.cmmnClCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 공통분류코드관리 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="cmmnClCodeVO" action="${pageContext.request.contextPath}/mng/ccm/ccc/updateCcmCmmnClCode.do" method="post">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 입력 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputYes"><spring:message code="input.yes" /></c:set>
						<c:set var="inputNo"><spring:message code="input.no" /></c:set>
						
						<!-- 분류코드 -->
						<c:set var="title"><spring:message code="mngCcmCcc.cmmnClCodeVO.clCode"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:input path="clCode" title="${title} ${inputTxt}" size="70" maxlength="70" readonly="true" />
				   				<div><form:errors path="clCode" cssClass="error" /></div>     
							</td>
						</tr>
						<!-- 분류코드명 -->
						<c:set var="title"><spring:message code="mngCcmCcc.cmmnClCodeVO.clCodeNm"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:input path="clCodeNm" title="${title} ${inputTxt}" size="70" maxlength="70" />
				   				<div><form:errors path="clCodeNm" cssClass="error" /></div>     
							</td>
						</tr>
						
						<!-- 분류코드설명 -->
						<c:set var="title"><spring:message code="mngCcmCcc.cmmnClCodeVO.clCodeDc"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
								<form:input path="clCodeDc" title="${title} ${inputTxt}" size="70" maxlength="70" />
								<div><form:errors path="clCodeDc" cssClass="error" /></div>  
							</td>
						</tr>
						
						<!-- 사용여부 -->
						<c:set var="title"><spring:message code="mngCcmCcc.cmmnClCodeVO.useAt"/> </c:set>
						<tr>
							<th>${title } <span class="pilsu">*</span></th>
							<td>
								<form:select path="useAt" title="${title} ${inputTxt }" cssClass="txt">
					  		   		<form:option value="Y"  label=" ${inputYes}"/>
									<form:option value="N" label=" ${inputNo}"/>
								</form:select>
								<div><form:errors path="useAt" cssClass="error" /></div>       
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncCmmnClCodeUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/ccm/ccc/selectCcmCmmnClCodeList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>