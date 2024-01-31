<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%-- <validator:javascript formName="lssBscStripLand" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<c:set var="pageTitle"><spring:message code="sysLssBsc.stripLandList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">기초조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 대상지 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="lssBscSvyCompt" action="${pageContext.request.contextPath}/sys/lss/bsc/sct/updateBscSvyCompt.do" method="post">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssBsc.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${lssBscSvyCompt.svyYear}"/>년</td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyId" /></th><!-- 조사ID -->
							<td><c:out value='${lssBscSvyCompt.svyId}'/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyType" /></th><!-- 조사유형 -->
							<td><c:out value="${lssBscSvyCompt.svyType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.region" /></th><!-- 관할 -->
							<td><c:out value="${lssBscSvyCompt.svyRegion1} ${lssBscSvyCompt.svyRegion2}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 위도 -->
							<td><c:out value="${lssBscSvyCompt.svyLat}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경도 -->
							<td><c:out value="${lssBscSvyCompt.svyLon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.address" /></th><!-- 주소 -->
							<td colspan="5"><c:out value="${lssBscSvyCompt.svySd} ${lssBscSvyCompt.svySgg} ${lssBscSvyCompt.svyEmd}"/><c:if test="${not empty lssBscSvyCompt.svyRi}"> <c:out value="${lssBscSvyCompt.svyRi}"/></c:if><c:if test="${not empty lssBscSvyCompt.svyJibun}"> <c:out value="${lssBscSvyCompt.svyJibun}"/></c:if></td>
							
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssBsc.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<c:set var="inputVal"><spring:message code="sysLssBsc.svyComptVO.value" /></c:set>
						<c:set var="inputScore"><spring:message code="sysLssBsc.svyComptVO.score" /></c:set>
						<!-- 보호대상 -->
						<c:set var="saftyTitle"><spring:message code="sysLssBsc.svyComptVO.safty"/></c:set>
						<!-- 황폐발생원 -->
						<c:set var="devOcCauseTitle"><spring:message code="sysLssBsc.svyComptVO.devOcCause"/></c:set>
						<!-- 계류평균경사 -->
						<c:set var="trntAvgSlpTitle"><spring:message code="sysLssBsc.svyComptVO.trntAvgSlp" /></c:set>
						<!-- 집수면적 -->
						<c:set var="wclctAreaTitle"><spring:message code="sysLssBsc.svyComptVO.wclctArea" /></c:set>
						<!-- 총계류길이 -->
						<c:set var="tlTrntLtTitle"><spring:message code="sysLssBsc.svyComptVO.tlTrntLt" /></c:set>
						<!-- 전석분포비율 -->
						<c:set var="distBmdsbRateTitle"><spring:message code="sysLssBsc.svyComptVO.distBmdsbRate" /></c:set>
						<!-- 경사길이 -->
						<c:set var="sLenTitle"><spring:message code="sysLssBsc.svyComptVO.sLen" /></c:set>
						<!-- 경사도 -->
						<c:set var="slopeTitle"><spring:message code="sysLssBsc.svyComptVO.slope" /></c:set>
						<!-- 사면형 -->
						<c:set var="sFormTitle"><spring:message code="sysLssBsc.svyComptVO.sForm" /></c:set>
						<!-- 임상 -->
						<c:set var="frstFrTitle"><spring:message code="sysLssBsc.svyComptVO.frstFr" /></c:set>
						<!-- 모암 -->
						<c:set var="prntRckTitle"><spring:message code="sysLssBsc.svyComptVO.prntRck" /></c:set>
						
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.svyDt"/></th><!-- 조사일 -->
							<td colspan="2"><c:out value="${lssBscSvyCompt.svyDt}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyUser" /></th><!-- 조사자 -->
							<td colspan="2"><c:out value='${lssBscSvyCompt.svyUser}'/></td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.sm"/></c:set><!-- 점수계 -->
							<th>${title}</th>
							<td colspan="2">
								<form:input path="svySm" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="svySm" cssClass="error" /></div>
							</td>
							<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.accdtSvy" /> <spring:message code="sysLssBsc.svyComptVO.ncssty" /></c:set><!-- 실태조사 필요성 -->
							<th>${title}</th>
							<td colspan="2">
								<form:input path="ncssty" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="ncssty" cssClass="error" /></div>
							</td>
						</tr>
						<c:if test="${lssBscSvyCompt.svyType eq '토석류'}">
						<tr>
							<th rowspan="2">${saftyTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
							<th rowspan="2">${devOcCauseTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
						</tr>
						<tr>
							<td>
								<form:input path="saftyVal" title="${saftyTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="saftyVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="saftyScore" title="${saftyTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="saftyScore" cssClass="error" /></div>
							</td>
							<td>
<%-- 								<form:select path="type" title="${title} ${inputTxt}" cssClass="txt"> --%>
<%-- 									<form:option value="" label="${inputSelect}"/> --%>
<%-- 									<form:options items="${typeCodeList}" itemValue="code" itemLabel="codeNm" /> --%>
<%-- 								</form:select> --%>
								<form:input path="devOcCauseVal" title="${devOcCauseTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="devOcCauseVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="devOcCauseScore" title="${devOcCauseTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="devOcCauseScore" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th rowspan="2">${trntAvgSlpTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
							<th rowspan="2">${wclctAreaTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
						</tr>
						<tr>
							<td>
								<form:input path="trntAvgSlpVal" title="${trntAvgSlpTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="trntAvgSlpVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="trntAvgSlpScore" title="${trntAvgSlpTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="trntAvgSlpScore" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="wclctAreaVal" title="${wclctAreaTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="wclctAreaVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="wclctAreaScore" title="${wclctAreaTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="wclctAreaScore" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th rowspan="2">${tlTrntLtTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
							<th rowspan="2">${distBmdsbRateTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
						</tr>
						<tr>
							<td>
								<form:input path="tlTrntLtVal" title="${tlTrntLtTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="tlTrntLtVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="tlTrntLtScore" title="${tlTrntLtTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="tlTrntLtScore" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="distBmdsbRateVal" title="${distBmdsbRateTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="distBmdsbRateVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="distBmdsbRateScore" title="${distBmdsbRateTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="distBmdsbRateScore" cssClass="error" /></div>
							</td>
						</tr>
						</c:if>
						<c:if test="${lssBscSvyCompt.svyType eq '산사태'}">
						<tr>
							<th rowspan="2">${saftyTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
							<th rowspan="2">${sLenTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
						</tr>
						<tr>
							<td>
								<form:input path="saftyVal" title="${saftyTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="saftyVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="saftyScore" title="${saftyTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="saftyScore" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="sLenVal" title="${sLenTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="sLenVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="sLenScore" title="${sLenTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="sLenScore" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th rowspan="2">${slopeTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
							<th rowspan="2">${sFormTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
						</tr>
						<tr>
							<td>
								<form:input path="slopeVal" title="${slopeTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="slopeVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="slopeScore" title="${slopeTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="slopeScore" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="sFormVal" title="${sFormTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="sFormVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="sFormScore" title="${sFormTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="sFormScore" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th rowspan="2">${frstFrTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
							<th rowspan="2">${prntRckTitle}</th>
							<th>${inputVal}</th>
							<th>${inputScore}</th>
						</tr>
						<tr>
							<td>
								<form:input path="frstFrVal" title="${frstFrTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="frstFrVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="frstFrScore" title="${frstFrTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="frstFrScore" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="prntRckVal" title="${prntRckTitle} ${inputVal} ${inputTxt}"/>
				   				<div><form:errors path="prntRckVal" cssClass="error" /></div>
							</td>
							<td>
								<form:input path="prntRckScore" title="${prntRckTitle} ${inputScore} ${inputTxt}"/>
				   				<div><form:errors path="prntRckScore" cssClass="error" /></div>
							</td>
						</tr>
						</c:if>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.mainRisk"/></c:set><!-- 주요위험성 -->
						<tr>
							<th>${title}</th>
							<td colspan="5">
								<form:input path="mainRisk" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="mainRisk" cssClass="error" /></div>
							</td>
						</tr>
						
						<c:set var="title"><spring:message code="sysLssBsc.svyComptVO.opinion"/></c:set><!-- 조사자의견 -->
						<tr>
							<th>${title}</th>
							<td colspan="5">
								<form:input path="opinion" title="${title} ${inputTxt}"/>
				   				<div><form:errors path="opinion" cssClass="error" /></div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncSvyComptUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="location.href='/sys/lss/bsc/sct/selectBscSvyComptList.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>