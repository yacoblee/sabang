<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="lssBscStripLand" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="sysLssBsc.stripLandList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">기초조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 대상지 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="lssBscStripLand" action="${pageContext.request.contextPath}/sys/lss/bsc/sld/updateLssBscStripLand.do" method="post">
			<input type="hidden" name="typeNm">
			<input type="hidden" name="region1Nm">
			<input type="hidden" name="region2Nm">
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
						<c:set var="inputDegree"><spring:message code="sysLssBsc.stripLandVO.degree"/> </c:set>
						<c:set var="inputMinute"><spring:message code="sysLssBsc.stripLandVO.minute"/> </c:set>
						<c:set var="inputSecond"><spring:message code="sysLssBsc.stripLandVO.second"/> </c:set>
						
						<!-- 조사ID -->
						<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.id"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td colspan="3" class="cs3">
							    <form:input path="id" title="${title} ${inputTxt}" readonly="true"/>
				   				<div><form:errors path="id" cssClass="error" /></div>     
							</td>
						</tr>
								
						<tr>
							<!-- 조사유형 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.type"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="type" title="${title} ${inputTxt}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${typeCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
								<div><form:errors path="type" cssClass="error" /></div>
							</td>
						
							<!-- 조사연도 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.year"/> </c:set>
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
							<!-- 관할1 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.region1"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="region1" title="${title} ${inputTxt}" cssClass="txt" onchange="fncRegionChange(event); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${region1CodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
								<div><form:errors path="region1" cssClass="error" /></div>
							</td>
							<!-- 관할2 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.region2"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="region2" title="${title} ${inputTxt}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${region2CodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
								<div><form:errors path="region2" cssClass="error" /></div>
							</td>
						</tr>
						
						<tr>
							<!-- 위도 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.lat"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td class="dmsTd">
							    <form:input path="latD" title="${title} ${inputDegree} ${inputTxt}" maxlength="2"/>
							    <form:input path="latM" title="${title} ${inputMinute} ${inputTxt}" maxlength="2"/>
							    <form:input path="latS" title="${title} ${inputSecond} ${inputTxt}" />
				   				<div><form:errors path="latD" cssClass="error" /></div>     
							</td>
							<!-- 경도 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.lon"/> </c:set>
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
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.sd"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:select path="sd" title="${title} ${inputTxt}" cssClass="txt" onchange="fncAdministCtprvnChange(event); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
								</form:select>
								<div><form:errors path="sd" cssClass="error" /></div>
							</td>

							<!-- 시군구 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.sgg"/> </c:set>
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
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.emd"/> </c:set>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:select path="emd" title="${title} ${inputTxt}" cssClass="txt" onchange="fncAdministEmdChange(event); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${emdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
								</form:select>
								<div><form:errors path="emd" cssClass="error" /></div>
							</td>
							<!-- 리 -->
							<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.ri"/> </c:set>
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
						<c:set var="title"><spring:message code="sysLssBsc.stripLandVO.jibun"/> </c:set>
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
						<button type="button" class="list-btn" onclick="location.href='/sys/lss/bsc/sld/selectStripLandList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>