<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssWka.stripLandItemVO.title"/> <spring:message code="title.update"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역실태조사</a></li>
	</ul>
	<h3>${pageTitle}</h3><!-- 공유방 대상지 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="result" action="${pageContext.request.contextPath}/sys/lss/wka/fbk/updateFieldBookDetailOne.do" method="post">
			<input name="smid" type="hidden" value="<c:out value='${result.smid}'/>">
			<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
			<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
			<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
			<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
			
			<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="schsd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
			<input name="schsgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
			<input name="schemd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
			<input name="schri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>	
			
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
						<col width="35%">
					</colgroup>
					<tbody>
						<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.cSelect"/></c:set>
						<c:set var="inputSvyType"><spring:message code="sysLssWka.stripLandItemVO.svyType"/></c:set>
						<c:set var="inputSvySd"><spring:message code="sysLssWka.stripLandItemVO.svySd"/></c:set>
						<c:set var="inputSvySgg"><spring:message code="sysLssWka.stripLandItemVO.svySgg"/></c:set>
						<c:set var="inputSvyEmd"><spring:message code="sysLssWka.stripLandItemVO.svyEmd"/></c:set>
						<c:set var="inputSvyRi"><spring:message code="sysLssWka.stripLandItemVO.svyRi"/></c:set>
						<c:set var="inputLndDgr"><spring:message code="sysLssWka.stripLandItemVO.svyType" /></c:set>
						<c:set var="inputSoilDepth"><spring:message code="sysLssWka.stripLandItemVO.soilDepth" /></c:set>
						<c:set var="inputSoilCls"><spring:message code="sysLssWka.stripLandItemVO.soilCls" /></c:set>
						<c:set var="inputPrntRock"><spring:message code="sysLssWka.stripLandItemVO.prntRock" /></c:set>
						
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.sn"/></th><!-- 순번 -->
							<td><form:input path="sn"/></td>
							<th><spring:message code="sysLssWka.stripLandItemVO.svyType" /></th><!-- 조사유형 -->
							<td>
								<form:select path="svyType" title="${inputSvyType} ${inputSelect}" cssClass="txt" onchange="fnSelectFieldBookTypeChange(this.value); return false;">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${typeCodeList}" itemValue="codeNm" itemLabel="codeNm" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.bscId" /></th><!-- 기초조사ID -->
							<td ><form:input path="bscId" readonly="true"/></td>
							<th><spring:message code="sysLssWka.stripLandItemVO.bscYear"/></th><!-- 기초조사수행연도 -->
							<td>
								<select name="bscYear">
									<option value="">선택</option>
								<c:set var="today" value="<%=new java.util.Date()%>" />
								<fmt:formatDate value="${today}" pattern="yyyy" var="now"/> 
								<c:forEach begin="1990" end="${now}" var="idx" step="1">
									<option value="<c:out value="${idx}" />" <c:if test="${result.bscYear eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.svyYear"/></th><!-- 조사연도 -->
							<td>
								<select name="svyYear">
									<option value="">선택</option>
									<c:set var="today" value="<%=new java.util.Date()%>" />
									<fmt:formatDate value="${today}" pattern="yyyy" var="now"/> 
									<c:forEach begin="1990" end="${now}" var="idx" step="1">
									<option value="<c:out value="${idx}" />" <c:if test="${result.svyYear eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
									</c:forEach>
								</select>
							</td>
							<th><spring:message code="sysLssWka.stripLandItemVO.lndDgdgr" /></th><!-- 산사태위험도 -->
							<td>
								<select name="lndDgr">
									<option value="">선택</option>
									<c:if test="${result.svyType eq '산사태'}">
									<c:forTokens var="idx" items="1,2,2.5,3,4,5" delims=",">
									<option value="<c:out value="${idx}" />" <c:if test="${result.lndDgr eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
									</c:forTokens>
									</c:if>
									<c:if test="${result.svyType eq '토석류'}">
									<c:forTokens var="idx" items="1,2,2.5,3" delims=",">
									<option value="<c:out value="${idx}" />" <c:if test="${result.lndDgr eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
									</c:forTokens>
									</c:if>
								</select>
							</td>
						</tr>
						<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.spLat" /></th><!-- 시점위도 -->
						<td>
							<form:input path="spLatDeg" cssClass="wd15" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />° 
							<form:input path="spLatMin" cssClass="wd15" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />' 
							<form:input path="spLatSec" cssClass="wd15" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" />"N
						</td>
						<th><spring:message code="sysLssWka.stripLandItemVO.spLon" /></th><!-- 시점경도 -->
						<td>
							<form:input path="spLonDeg" cssClass="wd15" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />° 
							<form:input path="spLonMin" cssClass="wd15" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />' 
							<form:input path="spLonSec" cssClass="wd15" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');"/>"E
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.epLat" /></th><!-- 종점위도 -->
						<td><form:input path="epLatDeg" cssClass="wd15" />° <form:input path="epLatMin" cssClass="wd15" />' <form:input path="epLatSec" cssClass="wd15" />"N</td>
						<th><spring:message code="sysLssWka.stripLandItemVO.epLon" /></th><!-- 종점경도 -->
						<td><form:input path="epLonDeg" cssClass="wd15" />° <form:input path="epLonMin" cssClass="wd15" />' <form:input path="epLonSec" cssClass="wd15" />"E</td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.region" /></th><!-- 관할 -->
						<td colspan="3">
							<form:input path="svyRegion1" cssClass="wd15" />
							<form:input path="svyRegion2" cssClass="wd15" />
						</td>
					<tr>
						<th><spring:message code="sysLssWka.stripLandItemVO.addr" /></th><!-- 주소 -->
						<td colspan="3">
							<form:select path="svySd" id="svySdView" title="${inputSvySd} ${inputSelect}" cssClass="txt wd15" onChange="javascript:fncAdministCtprvnNmChange(); return false;">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							<form:select path="svySgg" id="svySggView" title="${inputSvySgg} ${inputSelect}" cssClass="txt wd15" onChange="javascript:fncAdministSignguNmChange(); return false;">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							<form:select path="svyEmd" id="svyEmdView" title="${inputSvyEmd} ${inputSelect}" cssClass="txt wd15" onChange="javascript:fncAdministEmdNmChange(); return false;">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${emdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							<form:select path="svyRi" id="svyRiView" title="${inputSvyRi} ${inputSelect}" cssClass="txt wd15">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${riCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							<form:input path="svyJibun"/>
						</td>
					</tr>
					</tbody>
					<tbody id="wkaLandslide" <c:if test="${result.svyType ne '산사태'}"> style="display: none;"</c:if>>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.soilDepth" /></th><!-- 토심 -->
							<td>
							<select name="soilDepth" title="${inputSoilDepth} ${inputSelect}">
								<option value="" label="선택">
								<c:forEach items="${soilDepthCodeList}" var="soil">
								<option value="${soil.codeDc}" label="${soil.codeDc}" <c:if test="${fn:replace(soil.codeDc,' ','') eq result.soilDepth}">selected</c:if>>
								</c:forEach>
							</select>
							<th><spring:message code="sysLssWka.stripLandItemVO.soilCls" /></th><!-- 토성 -->
							<td>
								<form:select path="soilCls" title="${inputSoilCls} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${soilClsCodeList}" itemValue="codeNm" itemLabel="codeNm" />
								</form:select>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.prntRock" /></th><!-- 모암 -->
							<td>
								<form:select path="prntRock" title="${inputSoilCls} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${prntRockCodeList}" itemValue="codeNm" itemLabel="codeNm" />
								</form:select>
							</td>
							<th><spring:message code="sysLssWka.stripLandItemVO.aspectLen" /></th><!-- 사면길이 -->
							<td><form:input path="aspectLen"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.minHeight" /></th><!-- 최저높이 -->
							<td><form:input path="minHeight"/></td>
							<th><spring:message code="sysLssWka.stripLandItemVO.maxHeight" /></th><!-- 최고높이 -->
							<td><form:input path="maxHeight"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.drySeason" /></th><!-- 건기 -->
							<td><form:input path="drySeason"/></td>
							<th><spring:message code="sysLssWka.stripLandItemVO.rainSeason" /></th><!-- 우기 -->
							<td><form:input path="rainSeason"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.altdeDifnce" /></th><!-- 고도차 -->
							<td ><form:input path="altdeDifnce"/></td>
							<th></th>
							<td></td>
						</tr>
					</tbody>
					<tbody id="wkaDebrisFlow" <c:if test="${result.svyType ne '토석류'}"> style="display: none;"</c:if>>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.basinArea" /></th><!-- 유역면적 -->
							<td><form:input path="basinArea"/></td>
							<th><spring:message code="sysLssWka.stripLandItemVO.mntTorntLen" /></th><!-- 계류길이 -->
							<td><form:input path="mntTorntLen"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.stripLandItemVO.soilDepth" /></th><!-- 토심 -->
							<td>
								<select name="soilDepth" title="${inputSoilDepth} ${inputSelect}">
									<option value="" label="선택">
									<c:forEach items="${soilDepthCodeList}" var="soil">
									<option value="${soil.codeDc}" label="${soil.codeDc}" <c:if test="${fn:replace(soil.codeDc,' ','') eq result.soilDepth}">selected</c:if>>
									</c:forEach>
								</select>
							</td>
							<th></th>
							<td></td>
						</tr>
					</tbody>
				</table>
		  		
				<div class="BtnGroup">
					<div class="BtnRightArea">
              			<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookItem(); return false;"><spring:message code="title.update" /></button>
              			<button type="button" class="list-btn" onclick="javascript:fnFieldBookItemDetail('<c:out value="${result.smid}"/>'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>