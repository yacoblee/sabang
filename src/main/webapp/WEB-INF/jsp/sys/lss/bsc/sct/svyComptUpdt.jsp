<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%-- <validator:javascript formName="lssBscStripLand" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<c:set var="pageTitle"><spring:message code="sysLssBsc.svyComptList.update"/></c:set>
<c:set var="clientid"><spring:message code="lcMap.clientId" /></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">기초조사</a></li>
	</ul>
	<h3>${pageTitle}</h3><!-- 대상지 수정 -->
	<div id="contents">
		<input type="hidden" name="photolist" value="<c:out value="${orginl_photo_result}"/>">
		<input type="hidden" name="apiKey" value="${clientid}">
		<input type="hidden" name="mapParam" value="${mapParam}">
		<input type="hidden" name="orginlZoom" value="<c:out value="${orginl_zoom}"/>">
		<form:form id="listForm" commandName="lssBscSvyCompt" action="${pageContext.request.contextPath}/sys/lss/bsc/sct/updateBscSvyCompt.do" method="post" onsubmit="return false;">
			<input type="hidden" name="photo" value="<c:out value="${orginl_photo_result}"/>">
			<form:hidden path="gid"/>
<%-- 			<form:hidden path="svyType"/> --%>
			<input name="schsvyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
			<input name="schsvyRegion1" type="hidden" value="<c:out value='${searchVO.svyRegion1}'/>"/>
			<input name="schsvyRegion2" type="hidden" value="<c:out value='${searchVO.svyRegion2}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
			<input name="schncssty" type="hidden" value="<c:out value='${searchVO.ncssty}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${lssBscSvyCompt.mstId}'/>"/>
			<input name="changedZoom" type="hidden"  value="<c:out value='${searchVO.changedZoom}'/>">
			<input name="locImgIdx" type="hidden"  value="<c:out value='${searchVO.locImgIdx}'/>">
			<input name="photoTagList" type="hidden"  value="">
			<input name="mapType" type="hidden" value="SATELLITE">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<input name="svyRegion1" type="hidden" />
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssBsc.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
				<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
					<tbody>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${lssBscSvyCompt.svyYear}"/>년</td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyId" /></th><!-- 조사ID -->
							<td ><form:input style="background-color: #FFF;border:none;" path="svyId" readonly="true"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyType" /></th><!-- 조사유형 -->
							<td ><form:input style="background-color: #FFF;border:none;" path="svyType" readonly="true"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 위도 -->
							<td colspan="2"><c:out value="${lssBscSvyCompt.svyLat}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경도 -->
							<td colspan="2"><c:out value="${lssBscSvyCompt.svyLon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.region" /></th><!-- 관할 -->
							<td colspan="5">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<form:select path="region1GroupId" id="svyRegion1View" title="${title} ${inputSelect}" cssClass="txt wd30" onchange="fncRegionChange(event); return false;">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${region1CodeList}" itemValue="code" itemLabel="codeDc" />
									</form:select>
									<form:select path="svyRegion2" id="svyRegion2View" title="${title} ${inputSelect}" cssClass="txt wd30">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${region2CodeList}" itemValue="codeDc" itemLabel="codeDc" />
									</form:select>
								</sec:authorize>
								
								<sec:authorize access="!hasRole('ROLE_ADMIN')">
									<c:out value="${lssBscSvyCompt.svyRegion1} ${lssBscSvyCompt.svyRegion2}"/>
								</sec:authorize>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.address" /></th><!-- 주소 -->
							<td colspan="5">
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<form:select path="svySd" id="svySdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministCtprvnNmChange(); return false;">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
									</form:select>
									<form:select path="svySgg" id="svySggView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministSignguNmChange(); return false;">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
									</form:select>
									<form:select path="svyEmd" id="svyEmdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministEmdNmChange(); return false;">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${emdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
									</form:select>
									<form:select path="svyRi" id="svyRiView" title="${title} ${inputSelect}" cssClass="txt wd15">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${riCodeList}" itemValue="adminNm" itemLabel="adminNm" />
									</form:select>
									<form:input path="svyJibun" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="svyJibun" cssClass="error wd20" /></div>
								</sec:authorize>
								<sec:authorize access="!hasRole('ROLE_ADMIN')">
									<c:out value="${lssBscSvyCompt.svySd} ${lssBscSvyCompt.svySgg} ${lssBscSvyCompt.svyEmd}"/><c:if test="${not empty lssBscSvyCompt.svyRi}"> <c:out value="${lssBscSvyCompt.svyRi}"/></c:if><c:if test="${not empty lssBscSvyCompt.svyJibun}"> <c:out value="${lssBscSvyCompt.svyJibun}"/></c:if>									
								</sec:authorize>	
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.bp" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 시점 위도 -->
							<td><c:out value="${lssBscSvyCompt.bpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.bp" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 시점 경도 -->
							<td><c:out value="${lssBscSvyCompt.bpy}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.bp" /> <spring:message code="sysLssBsc.svyComptVO.ant" /></th><!-- 시점 고도 -->
							<td><input type="text" name="bz" value="${lssBscSvyCompt.bz}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.ep" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 끝점 위도 -->
							<td><c:out value="${lssBscSvyCompt.epx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.ep" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 끝점 경도 -->
							<td><c:out value="${lssBscSvyCompt.epy}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.ep" /> <spring:message code="sysLssBsc.svyComptVO.ant" /></th><!-- 끝점 고도 -->
							<td><input type="text" name="ez" value="${lssBscSvyCompt.ez}"/></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssBsc.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<colgroup>
						<col width=";">
						<col width=";">
						<col width=";">
						<col width=";">
						<col width=";">
						<col width=";">
					</colgroup>
					<tbody>
						<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<c:set var="inputVal"><spring:message code="sysLssBsc.svyComptVO.value" /></c:set>
						<c:set var="inputScore"><spring:message code="sysLssBsc.svyComptVO.score" /></c:set>
						
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.svyDt"/></th><!-- 조사일 -->
							<td ><c:out value="${lssBscSvyCompt.svyDt}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyUser" /></th><!-- 조사자 -->
							<td><c:out value='${lssBscSvyCompt.svyUser}'/></td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.sm"/></c:set><!-- 점수계 -->
							<th>${title}</th>
							<td class="center">
								<form:input path="svySm" title="${title} ${inputTxt}" readonly="true"/>
				   				<div><form:errors path="svySm" cssClass="error" /></div>
							</td>
							<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.accdtSvy" /> <spring:message code="sysLssBsc.svyComptVO.ncssty" /></c:set><!-- 실태조사 필요성 -->
							<th>${title}</th>
							<td class="center">
								<form:select path="ncssty" title="${title} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${ncsstyCodeList}" itemValue="codeNm" itemLabel="codeNm" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사도 시점 위도 -->
							<td><c:out value="${lssBscSvyCompt.slopeBpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사도 시점 경도 -->
							<td><c:out value="${lssBscSvyCompt.slopeBpy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사도 끝점 위도 -->
							<td><c:out value="${lssBscSvyCompt.slopeEpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사도 끝점 경도 -->
							<td><c:out value="${lssBscSvyCompt.slopeEpy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사길이 시점 위도 -->
							<td><c:out value="${lssBscSvyCompt.slenBpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사길이 시점 경도 -->
							<td><c:out value="${lssBscSvyCompt.slenBpy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사길이 끝점 위도 -->
							<td><c:out value="${lssBscSvyCompt.slenEpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사길이 끝점 경도 -->
							<td><c:out value="${lssBscSvyCompt.slenEpy}"/></td>
						</tr>
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.safty"/></c:set><!-- 보호대상 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="saftyScore" title="${title} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${saftyCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="saftyVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="saftyVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="safty" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.saftyScore}"/>' readonly="readonly">
							</td>
						</tr>
						<c:if test="${lssBscSvyCompt.svyType eq '토석류'}">
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.devOcCause"/></c:set><!-- 황폐발생원 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="devOcCauseScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${devOccCauseCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="devOcCauseVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="devOcCauseVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="devOcCause" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.devOcCauseScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.trntAvgSlp" /></c:set><!-- 계류평균경사 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="trntAvgSlpScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${trntAvgSlpCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="trntAvgSlpVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="trntAvgSlpVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="trntAvgSlp" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.trntAvgSlpScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.wclctArea" /></c:set><!-- 집수면적 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="wclctAreaScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${wclctAreaCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="wclctAreaVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="wclctAreaVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="wclctArea" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.wclctAreaScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.tlTrntLt" /></c:set><!-- 총계류길이 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="tlTrntLtScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${tlTrntLtCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="tlTrntLtVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="tlTrntLtVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="tlTrntLt" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.tlTrntLtScore}"/>' readonly="readonly">
							</td>
						</tr>
						<c:choose>
						<c:when test="${lssBscSvyCompt.svyYear eq '2021'}">
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.distBmdsbRate" /></c:set><!-- 전석분포비율 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="distBmdsbRateScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${distBmdsbRateCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="distBmdsbRateVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="distBmdsbRateVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="distBmdsbRate" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.distBmdsbRateScore}"/>' readonly="readonly">
							</td>
						</tr>
						</c:when>
						<c:otherwise>
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.rskFactor1" /></c:set><!-- 위험인자 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2" class="devRskFactor">
								<form:checkboxes items="${rskFactorList}" path="rskFactors" itemValue="code" itemLabel="codeNm"/>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="rskFactorVal" title="${title} ${inputVal} ${inputTxt}" readonly="true"/>
				   				<div><form:errors path="rskFactorVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="rskFactorScore" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.rskFactorScore}"/>' readonly="readonly">
								<input name="rskFactor" type="hidden" value='<c:out value="${lssBscSvyCompt.rskFactorScore}"/>'>
							</td>
						</tr>
						</c:otherwise>
						</c:choose>
						</c:if>
						<c:if test="${lssBscSvyCompt.svyType eq '산사태'}">
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.sLen" /></c:set><!-- 경사길이 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="sLenScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sLenCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="sLenVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="sLenVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="sLen" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.sLenScore}"/>' readonly="readonly">
							</td>
						</tr>

						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.slope" /></c:set><!-- 경사도 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="slopeScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${slopeCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="slopeVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="slopeVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="slope" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.slopeScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.sForm" /></c:set><!-- 사면형 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="sFormScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sFormCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="sFormVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="sFormVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="sForm" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.sFormScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.frstFr" /></c:set><!-- 임상 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="frstFrScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${frstFrCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="frstFrVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="frstFrVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="frstFr" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.frstFrScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.prntRck" /></c:set><!-- 모암 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2">
								<form:select path="prntRckScore" title="${title} ${inputScore} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${prntRckCodeList}" itemValue="code" itemLabel="codeNm" />
								</form:select>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="prntRckVal" title="${title} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="prntRckVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="prntRck" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.prntRckScore}"/>' readonly="readonly">
							</td>
						</tr>
						
						<c:if test="${lssBscSvyCompt.svyYear ne '2021'}">
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.rskFactor2" /></c:set><!-- 위험요인 -->
						<tr>
							<th rowspan="2">${title}</th>
							<td rowspan="2" class="landRskFactor">
								<form:checkboxes items="${rskFactorList}" path="rskFactors" itemValue="code" itemLabel="codeNm"/>
							</td>
							<th>${inputVal}</th>
							<td class="center">
								<form:input path="rskFactorVal" title="${title} ${inputVal} ${inputTxt}" readonly="true"/>
				   				<div><form:errors path="rskFactorVal" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>${inputScore}</th>
							<td class="center">
								<input name="rskFactorScore" type="text" title="${title} ${inputScore} ${inputTxt}" value='<c:out value="${lssBscSvyCompt.rskFactorScore}"/>' readonly="readonly">
								<input name="rskFactor" type="hidden" value='<c:out value="${lssBscSvyCompt.rskFactorScore}"/>'>
							</td>
						</tr>
						</c:if>
						</c:if>
						
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.mainRisk"/></c:set><!-- 주요위험성 -->
						<tr>
							<th>${title}</th>
							<td colspan="3">
								<form:input path="mainRisk" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="mainRisk" cssClass="error" /></div>
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.opinion"/></c:set><!-- 조사자의견 -->
						<tr>
							<th>${title}</th>
							<td colspan="3">
								<form:input path="opinion" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="opinion" cssClass="error" /></div>
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.lcmap"/></c:set><!-- 위치도 -->
						<tr>
							<th colspan="6">${title}</th>
						</tr>
						<tr>
							<td colspan="4">
				   				<div id="map" style="width:100%;height:400px;"></div>
							</td>
						</tr>
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.photo"/></c:set><!-- 현장사진 -->
					    <tr><th colspan="6">${title}</th></tr>
					</tbody>
				</table>
				<div class="photoWrap">
			    	<c:choose>
			    		<c:when test="${photo_result != null }">
			    			<c:forEach items="${photo_result}" var="item" varStatus="status">
						    <c:choose>
						        <c:when test="${not empty item.FILENAME}">
						            <c:set var="noImage" value="false"/>
						            <c:set var="photoUrl" value="${item.FILENAME}"/>
						        </c:when>
						        <c:otherwise>
						            <c:set var="noImage" value="true"/>
						            <c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
						        </c:otherwise>
						    </c:choose>
						    <div class="photoBox" style="width: 50%;">
						    	<div class="photoTag">
							        <select name="photoNum" class="photoNum" style="width:5%; height:35px; float:left;">
							            <option value="" >선택</option>
							            <c:forEach var="j" begin="0" end="3">
							                <option value="<c:out value="${j+1}"/>" <c:if test="${fn:contains(item.TAG,'.') && fn:split(item.TAG,'.')[0] eq j+1}">selected="selected"</c:if>><c:out value="${j+1}"/></option>
							            </c:forEach>
							        </select> 
							         <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag<c:out value="${status.count }"/>"
							            <c:choose>
							                <c:when test="${fn:contains(item,'TAG') && fn:contains(item.TAG,'.') }">placeholder="<c:out value="${fn:split(item.TAG,'.')[1]}"/>" value="<c:out value="${fn:split(item.TAG,'.')[1]}"/>"</c:when>
							                <c:when test="${fn:contains(item,'TAG') && fn:length(item.TAG) > 0 }">placeholder="<c:out value="${item.TAG}"/>" value="<c:out value="${item.TAG}"/>"</c:when>
							                <c:otherwise>placeholder="사진태그없음"</c:otherwise>
							            </c:choose> 
							        /> 
							        <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncLssSvyDeletePhoto(event); return false;">삭제</button>
						    	</div>
						    	<div class="photoUrl">
						    		<input type="hidden" <c:if test="${noImage eq false}">value="<c:url value='${photoUrl}'/>"</c:if> name="photoSrc<c:out value="${status.count}"/>" />
				                    <img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> class="photoSrc<c:out value="${status.count}"/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
				                    <span class="thumb-div noselect">변경</span>
						    	</div>
						    </div>
				    		</c:forEach>
			    		</c:when>
			    		<c:otherwise>
			    			<div class="noPhotoTagInfo">사진태그 정보 없음</div>
			    		</c:otherwise>
			    	</c:choose>
				</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="add-btn" onclick="javascript:fncLssSvyAddPhoto(); return false;">현장사진 <spring:message code="button.add" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncSvyComptUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>