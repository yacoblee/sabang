<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="cmmnCodeVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngCcmCca.cmmnCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">공통코드관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 공통코드관리 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="cmmnCodeVO" action="${pageContext.request.contextPath}/mng/ccm/cca/registCcmCmmnCode.do" method="post">
			<input name="cmd" type="hidden" value="<c:out value='save'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption>${pageTitle} <spring:message code="title.create" /></caption>
					<tbody>
						<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<c:set var="inputYes"><spring:message code="input.yes" /></c:set>
						<c:set var="inputNo"><spring:message code="input.no" /></c:set>
						<!-- 분류코드명 -->
						<c:set var="title"><spring:message code="mngCcmCca.cmmnCodeVO.clCodeNm"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:select path="clCode" title="${title} ${inputSelect }" >
					    			<form:option value="" label="${inputSelect}"/>
		 							<form:options items="${clCodeList}"  itemValue="clCode" itemLabel="clCodeNm"/>
								</form:select>
				   				<div><form:errors path="clCode" cssClass="error" /></div>     
							</td>
						</tr>
								
						<!-- 코드ID -->
						<c:set var="title"><spring:message code="mngCcmCca.cmmnCodeVO.codeId"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:input path="codeId" title="${title} ${inputTxt}" size="70" maxlength="70" />
				   				<div><form:errors path="codeId" cssClass="error" /></div>     
							</td>
						</tr>
				
						<!-- 코드ID명 -->
						<c:set var="title"><spring:message code="mngCcmCca.cmmnCodeVO.codeIdNm"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:input path="codeIdNm" title="${title} ${inputTxt}" size="70" maxlength="70" />
				   				<div><form:errors path="codeIdNm" cssClass="error" /></div>     
							</td>
						</tr>
						
						<!-- 코드ID설명 -->
						<c:set var="title"><spring:message code="mngCcmCca.cmmnCodeVO.codeIdDc"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:input path="codeIdDc" title="${title} ${inputTxt}" size="70" maxlength="70" />
	<%-- 							<form:textarea path="codeIdDc" title="${title} ${inputTxt}" cols="300" rows="20" />    --%>
								<div><form:errors path="codeIdDc" cssClass="error" /></div>  
							</td>
						</tr>
						
						<!-- 사용여부 -->
						<c:set var="title"><spring:message code="mngCcmCca.cmmnCodeVO.useAt"/> </c:set>
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
						<button type="button" class="save-btn" onclick="javascript:fncCmmnCodeInsert();"><spring:message code="title.create" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/ccm/cca/selectCcmCmmnCodeList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script>
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
</script>