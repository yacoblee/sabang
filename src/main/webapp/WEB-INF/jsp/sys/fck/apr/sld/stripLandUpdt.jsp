<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
2<validator:javascript formName="fckAprStripLand" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="sysFckApr.stripLandList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">외관점검</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 대상지 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="fckAprStripLand" action="${pageContext.request.contextPath}/sys/fck/apr/sld/updateFckAprStripLand.do" method="post">
			<input type="hidden" name="typeNm">
			<input type="hidden" name="sdNm">
			<input type="hidden" name="sggNm">
			<input type="hidden" name="emdNm">
			<input type="hidden" name="riNm">
			<input type="hidden" name="lat">
			<input type="hidden" name="lon">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<c:set var="inputDegree"><spring:message code="sysFckApr.stripLandVO.degree"/> </c:set>
						<c:set var="inputMinute"><spring:message code="sysFckApr.stripLandVO.minute"/> </c:set>
						<c:set var="inputSecond"><spring:message code="sysFckApr.stripLandVO.second"/> </c:set>
						
						<!-- 조사ID -->
						<c:set var="title"><spring:message code="sysFckApr.stripLandVO.id"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td colspan="3" class="cs3">
							    <form:input path="id" title="${title} ${inputTxt}" readonly="true"/>
				   				<div><form:errors path="id" cssClass="error" /></div>     
							</td>
						</tr>
								
						<tr>
							<!-- 조사유형 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.type"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="type" title="${title} ${inputTxt}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${typeCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
								<div><form:errors path="type" cssClass="error" /></div>
							</td>
						
							<!-- 조사연도 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.year"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="year" title="${title} ${inputTxt}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${yearCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
								<div><form:errors path="year" cssClass="error" /></div>
							</td>
						</tr>
				
						<tr>
							<!-- 위도 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.lat"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td class="dmsTd">
							    <form:input path="latD" title="${title} ${inputDegree} ${inputTxt}" maxlength="2"/>
							    <form:input path="latM" title="${title} ${inputMinute} ${inputTxt}" maxlength="2"/>
							    <form:input path="latS" title="${title} ${inputSecond} ${inputTxt}" />
				   				<div><form:errors path="latD" cssClass="error" /></div>     
							</td>
							<!-- 경도 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.lon"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td class="dmsTd">
							    <form:input path="lonD" title="${title} ${inputDegree} ${inputTxt}" maxlength="3" />
							    <form:input path="lonM" title="${title} ${inputMinute} ${inputTxt}" maxlength="2" />
							    <form:input path="lonS" title="${title} ${inputSecond} ${inputTxt}" />
				   				<div><form:errors path="lonD" cssClass="error" /></div>     
							</td>
						</tr>
				
						<tr>
							<!-- 시도 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.sd"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="sd" title="${title} ${inputTxt}" cssClass="txt" onchange="fncAdministCtprvnChange(event); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
								</form:select>
								<div><form:errors path="sd" cssClass="error" /></div>
							</td>

							<!-- 시군구 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.sgg"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="sgg" title="${title} ${inputTxt}" cssClass="txt" onchange="fncAdministSignguChange(event); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sggCodeList}" itemValue="adminCode" itemLabel="adminNm" />
								</form:select>
								<div><form:errors path="sgg" cssClass="error" /></div>
							</td>
						</tr>
				
						<tr>
							<!-- 읍면동 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.emd"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:select path="emd" title="${title} ${inputTxt}" cssClass="txt" onchange="fncAdministEmdChange(event); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${emdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
								</form:select>
								<div><form:errors path="emd" cssClass="error" /></div>
							</td>
							<!-- 리 -->
							<c:set var="title"><spring:message code="sysFckApr.stripLandVO.ri"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:select path="ri" title="${title} ${inputTxt}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<c:if test="${not empty riCodeList}">
									<form:options items="${riCodeList}" itemValue="adminCode" itemLabel="adminNm" />
									</c:if>
								</form:select>
								<div><form:errors path="ri" cssClass="error" /></div>
							</td>
						</tr>

						<!-- 지번 -->
						<c:set var="title"><spring:message code="sysFckApr.stripLandVO.jibun"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td colspan="3" class="cs3">
							    <form:input path="jibun" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="jibun" cssClass="error" /></div>     
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncStripLandUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/sys/fck/apr/sld/selectStripLandList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>