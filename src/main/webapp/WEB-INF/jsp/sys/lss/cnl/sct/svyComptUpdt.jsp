<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <validator:javascript formName="lssCnlStripLand" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<c:set var="pageTitle"><spring:message code="sysLssCnl.svyComptList.update"/></c:set>
<c:set var="clientid"><spring:message code="lcMap.clientId" /></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
	</ul>
	<h3>${pageTitle}</h3><!-- 대상지 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="result" action="${pageContext.request.contextPath}/sys/lss/cnl/sct/updateCnlSvyCompt.do" method="post" onsubmit="return false;" enctype="multipart/form-data">
			<input type="hidden" name="photolist" value="<c:out value="${orginl_photo_result}"/>">
			<input type="hidden" name="orginlZoom" value="<c:out value="${orginl_zoom}"/>">
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			
			<input name="schsvyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			
			<input name="mstId" type="hidden" value="<c:out value='${searchVO.mstId}'/>"/>
			<input name="locImgIdx" type="hidden"  value="<c:out value='${searchVO.locImgIdx}'/>">
			<input name="mstNm" type="hidden"  value="<c:out value='${result.mstNm}'/>">
			
			<input name="svyType" type="hidden"  value="<c:out value='${result.svyType}'/>">
			<input name="photoTagList" type="hidden" value='${searchVO.photoTag}'>
			<div class="BoardDetail">
				<c:set var="inputTxt">입력</c:set>
				<c:set var="inputSelect">선택</c:set>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssCnl.svyComptList.cnlDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${result.svyYear}"/>년</td>
							<th><spring:message code="sysLssCnl.svyComptVO.svyId" /></th><!-- 조사ID -->
							<td><input type="text" name="svyId" value="<c:out value='${result.svyId}'/>"  readonly="readonly"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.svyType" /></th><!-- 조사유형 -->
							<td><c:out value="${result.svyType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.region" /></th><!-- 관리주체 -->
							<td><c:out value="${result.region1} ${result.region2}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.lat" /></th><!-- 위도 -->
							<td><c:out value="${result.svyLat}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.lon" /></th><!-- 경도 -->
							<td><c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.address" /></th><!-- 소재지 -->
							<td colspan="5"><c:out value="${result.svySd} ${result.svySgg} ${result.svyEmd}"/><c:if test="${not empty result.svyRi}"> <c:out value="${result.svyRi}"/></c:if><c:if test="${not empty result.svyJibun}"> <c:out value="${result.svyJibun}"/></c:if></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssCnl.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
				<tbody>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.svyDt"/></th><!-- 조사일 -->
							<td colspan="2"><c:out value="${result.svyDt}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.svyUser" /></th><!-- 조사자 -->
							<td colspan="2"><c:out value="${result.svyUser}"/></td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.appnYear"/></c:set>
							<th>${title}</th><!-- 지정년도 -->
							<td>
								<input name="appnYear" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.appnYear}"/>' oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" style="width: 100px;"><c:if test="${not empty result.appnYear && result.appnYear ne '-'}">년</c:if>
								<div><form:errors path="appnYear" cssClass="error" /></div>
							</td>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.appnNo"/></c:set>
							<th>${title}</th><!-- 지정번호 -->
							<td>
								<input name="appnNo" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.appnNo}"/>' style="width: 100px;">
								<div><form:errors path="appnNo" cssClass="error" /></div>
							</td>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.appnArea"/></c:set>
							<th>${title}</th><!-- 지정면적 -->
							<td>
								<input name="appnArea" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.appnArea}"/>' oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" style="width: 100px;"><c:if test="${not empty searchVO.appnArea && searchVO.appnArea ne '-'}">m<sup>2</sup></c:if>
								<div><form:errors path="appnArea" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th rowspan="7"><spring:message code="sysLssCnl.svyComptVO.landOverview" /></th><!-- 대상지개황 -->
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.weakappnsttus"/></c:set>
							<th>${title }</th><!-- 당시현황 -->
							<td colspan="4">
								<textarea rows="5" cols="80" name="weakAppnSttus" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.weakAppnSttus}"/></textarea>
								<div><form:errors path="weakAppnSttus" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.appnResn"/></c:set><!-- 지정사유 -->
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="appnResn" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.appnResn}"/></textarea>
								<div><form:errors path="appnResn" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.weakappnbstype" /></c:set><!-- 사업종가능 -->
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="weakAppnBsType" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.weakAppnBsType}"/></textarea>
								<div><form:errors path="weakAppnBsType" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.weakappnposyn" /></c:set><!-- 가능여부 -->
							<th>${title }</th>
							<td colspan="4">
								<select name="weakAppnPosYn">
									<option value="">${inputSelect}</option>
									<option value="예" <c:if test="${result.weakAppnPosYn eq '예'}">selected="selected"</c:if>>예</option>
									<option value="아니오" <c:if test="${result.weakAppnPosYn eq '아니오'}">selected="selected"</c:if>>아니오</option>
									<option value="산사태취약지역 지정·심의 자료 없음" <c:if test="${result.weakAppnPosYn eq '산사태취약지역 지정·심의 자료 없음'}">selected="selected"</c:if>>산사태취약지역 지정·심의 자료 없음</option>
								</select>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.weakappnsltnhy" /></c:set><!-- 선정사유 -->
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="weakAppnSltnHy" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.weakAppnSltnHy}"/></textarea>
								<div><form:errors path="weakAppnSltnHy" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.weakappnareaset" /></c:set><!-- 구역설정 -->
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="weakAppnAreaSet" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.weakAppnAreaSet}"/></textarea>
								<div><form:errors path="weakAppnAreaSet" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.weakappnmstopn" /></c:set><!-- 당시종합의견 -->
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="weakAppnMstOpn" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.weakAppnMstOpn}"/></textarea>
								<div><form:errors path="weakAppnMstOpn" cssClass="error" /></div>
							</td>
						</tr>
						<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.bizType"/></c:set>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.bizType"/></th><!-- 사업종류 -->
							<td colspan="2">
								<form:select path="bizType" title="${title} ${inputSelect}" cssClass="txt">
									<form:option value="" label="${inputSelect}"></form:option>
									<form:options items="${bizTypeCodeList}" itemValue="codeDc" itemLabel="codeDc" />
								</form:select>
							</td>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.cnstrYear"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.cnstrYear" /></th><!-- 시공연도 -->
<%-- 							<td colspan="2"><c:out value='${searchVO.cnstrYear}'/><c:if test="${not empty result.cnstrYear && result.cnstrYear ne '-'}">년</c:if></td> --%>
							<td colspan="2">
								<input name="cnstrYear" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.cnstrYear}"/>' oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" style="width: 100px;">년
								<div><form:errors path="cnstrYear" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/></th><!-- 적용공법 -->
							<td colspan="5">
								<form:select path="applcEgnerMhdCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${applcEgnerMhdCodeList}" itemValue="codeDc" itemLabel="codeDc" />
								</form:select>
								<form:input class="selectJdgVal" path="applcEgnerMhd" title="${title} ${inputTxt}"/>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.fctSttus"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.fctSttus" /></th><!-- 시설물 상태 -->
							<td colspan="5">
								<input name="fctSttus" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.fctSttus}"/>'>
								<div><form:errors path="fctSttus" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.fctSttus"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.dgrSttus" /></th><!-- 유역현황 -->
							<td colspan="5">
								<input name="dgrSttus" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.dgrSttus}"/>'>
								<div><form:errors path="dgrSttus" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.dmgeHist"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.dmgeHist" /></th><!-- 피해이력 -->
							<td colspan="5">
								<input name="dmgeHist" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.dmgeHist}"/>'>
								<div><form:errors path="dmgeHist" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.dmgeHist"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.speclNote" /></th><!-- 특이사항 -->
							<td colspan="5">
								<input name="speclNote" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.speclNote}"/>'>
								<div><form:errors path="speclNote" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.basis" /></th><!-- 근거 -->
							<td colspan="5">
								<input type="text" id="cnlBasis" value="<c:out value='${cnlBasis}' />" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.detailMatter"/></c:set>
							<th>${title}</th><!-- 세부사항 -->
							<td colspan="5">
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${not empty searchVO.detailMatter}"><c:out value='${searchVO.detailMatter}'/></c:when> --%>
<%-- 									<c:when test="${searchVO.detailMatter ne '' }"><c:out value='${searchVO.detailMatter}'/></c:when> --%>
<%-- 									<c:otherwise>-</c:otherwise> --%>
<%-- 								</c:choose> --%>
								<input name="detailMatter" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.detailMatter}"/>'>
								<div><form:errors path="detailMatter" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.dsstrOccrrncAt"/></c:set>
							<th>${title }</th><!-- 재해발생여부 -->
							<td colspan="2">
								<form:select path="cnlevl1" title="${title} ${inputSelect}" cssClass="txt"><!-- 재해발생여부 -->
			                          <form:option value="" label="${inputSelect}"/>
			                          <form:options items="${dsstrOccrrncAtCodeList}" itemValue="codeDc" itemLabel="codeDc" />
			                    </form:select>
							</td>
							<c:if test="${result.svyType eq '산사태'}">
								<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.sSttus"/></c:set>
								<th>${title }</th><!-- 사면상태 -->
								<td colspan="2">
									<form:select path="cnlevl2" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"></form:option>
										<form:options items="${sSttusCodeList}" itemValue="codeDc" itemLabel="codeDc" />
									</form:select>
								</td>
							</c:if>
							<c:if test="${result.svyType eq '토석류'}">
								<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.mrngSttus"/></c:set>
								<th>${title }</th><!-- 계류상태 -->
								<td colspan="2">
									<form:select path="cnlevl2" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${mrngSttusCodeList}" itemValue="codeDc" itemLabel="codeDc" />
									</form:select>
								</td>
							</c:if>
						</tr>
						<tr>
							<c:if test="${result.svyType eq '토석류'}">
								<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.simlatnRsltYn"/></c:set>
								<th><spring:message code="sysLssCnl.svyComptVO.simlatnRsltYn" /></th><!-- 시뮬레이션 결과 -->
								<td colspan="2">
									<form:select path="cnlevl3" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${simlatnRsltYnCodeList}" itemValue="codeDc" itemLabel="codeNm" />
									</form:select>
								</td>
							</c:if>
							<c:if test="${result.svyType eq '산사태'}">
								<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.stableIntrprtYn"/></c:set>
								<th><spring:message code="sysLssCnl.svyComptVO.stableIntrprtYn" /></th><!-- 안정해석 결과 -->
								<td colspan="2">
									<form:select path="cnlevl3" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${stableIntrprtYnCodeList}" itemValue="codeDc" itemLabel="codeNm" />
									</form:select>
								</td>
								<td colspan="3"></td>
							</c:if>
							<c:if test="${result.svyType eq '토석류'}">
								<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.reducAt"/></c:set>
								<th><spring:message code="sysLssCnl.svyComptVO.reducAt"/></th>
								<td colspan="2">
									<form:select path="reducAt" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${reducAtCodeList}" itemValue="codeDc" itemLabel="codeNm" />
									</form:select>
								</td>
							</c:if>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.mstOpinion"/></c:set>
							<th rowspan="2"><spring:message code="sysLssCnl.svyComptVO.mstOpinion"/></th><!-- 종합의견 -->
							<td colspan="5">
								<textarea rows="5" cols="104" name="mstOpinion1" title="${title}1 ${inputTxt}" style="resize: none;"><c:out value="${result.mstOpinion1}"/></textarea>
								<div><form:errors path="mstOpinion1" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<td colspan="5">
								<textarea rows="5" cols="104" name="mstOpinion2" title="${title}2 ${inputTxt}" style="resize: none;"><c:out value="${result.mstOpinion2}"/></textarea>
								<div><form:errors path="mstOpinion2" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.finalMstOpinion"/></c:set>
							<th rowspan="3">${title}1</th><!-- 최종종합의견1 -->
							<th>${title}1</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="finalMstOpinion1" title="${title}1 ${inputTxt}" style="resize: none;"><c:out value="${result.finalMstOpinion1}"/></textarea>
								<div><form:errors path="finalMstOpinion1" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.finalMstOpinion"/></c:set>
							<th>${title}2</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="finalMstOpinion2" title="${title}2 ${inputTxt}" style="resize: none;"><c:out value="${result.finalMstOpinion2}"/></textarea>
								<div><form:errors path="finalMstOpinion2" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.finalMstOpinion"/></c:set>
							<th>${title}3</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="finalMstOpinion3" title="${title}3 ${inputTxt}" style="resize: none;"><c:out value="${result.finalMstOpinion3}"/></textarea>
								<div><form:errors path="finalMstOpinion3" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.cnlYn"/></c:set>
							<th><spring:message code="sysLssCnl.svyComptVO.cnlYn"/></th><!-- 최종 검토결과 -->
							<td colspan="5">
								<select name="cnlYn">
									<option value="">선택하세요</option>
									<option value="가" <c:if test ="${result.cnlYn eq '가'}">selected="selected"</c:if>>해제 심의 대상지 적합</option>
									<option value="부" <c:if test ="${result.cnlYn eq '부'}">selected="selected"</c:if>>해제 심의 대상지 부적합</option>
								</select>
							</td>
						</tr>
						<tr>
							<th rowspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn" /></th>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn1" /></th>
							<td>
								<form:checkbox path="cnlSlctRn1" name="cnlSlctRn" value="1" onclick="getCheckboxValue(this.value)"/>
							</td>
						</tr>
						<tr>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn2" /></th>
							<td>
								<form:checkbox path="cnlSlctRn2" name="cnlSlctRn" value="2" onclick="getCheckboxValue(this.value)"/>
							</td>
						</tr>
						<tr>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn3" /></th>
							<td>
								<form:checkbox path="cnlSlctRn3" name="cnlSlctRn" value="3" onclick="getCheckboxValue(this.value)"/>
							</td>
						</tr>
						<tr>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn4" /></th>
							<td>
								<form:checkbox path="cnlSlctRn4" name="cnlSlctRn" value="4" onclick="getCheckboxValue(this.value)"/>
							</td>
						</tr>
						 <c:if test="${result.svyType eq '토석류'}">
						 	<tr>
						 		<th>1회 토석류량</th><!-- 1회 토석류량 -->
						 		<td colspan="2">
							 		<input name="oneDebrisFlow" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.oneDebrisFlow}"/>'>
									<div><form:errors path="oneDebrisFlow" cssClass="error" /></div>
						 		</td>
						 		<th>정지조건</th><!-- 정지조건 -->
						 		<td colspan="2">
						 			<input name="stopCnd" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.stopCnd}"/>'>
									<div><form:errors path="stopCnd" cssClass="error" /></div>
						 		</td>
						 	</tr>
						 	<tr>
						 		<th>가중치</th><!-- 가중치 -->
						 		<td colspan="2">
							 		<input name="wghtVal" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.wghtVal}"/>'>
									<div><form:errors path="wghtVal" cssClass="error" /></div>
						 		</td>
						 		<th>전체 토석류량</th><!-- 전체토석류량 -->
						 		<td colspan="2">
						 		<input name="allDebrisFlow" type="text" title="${title} ${inputTxt}" value='<c:out value="${result.allDebrisFlow}"/>'>
								<div><form:errors path="allDebrisFlow" cssClass="error" /></div>
						 	</tr>
						 </c:if>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.bizNdDgrSttus"/></c:set>
							<th rowspan="3"><spring:message code="sysLssCnl.svyComptVO.manageStat" /></th>
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="bizNdDgrSttus" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.bizNdDgrSttus}"/></textarea>
								<div><form:errors path="bizNdDgrSttus" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.dmgHistNdDgrChag"/></c:set>
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="dmgHistNdDgrChag" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.dmgHistNdDgrChag}"/></textarea>
								<div><form:errors path="dmgHistNdDgrChag" cssClass="error" /></div>
						</tr>
						<tr>
							<c:set var="title"><spring:message code="sysLssCnl.svyComptVO.hbtOpnNdEtcMatter"/></c:set>
							<th>${title }</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="hbtOpnNdEtcMatter" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.hbtOpnNdEtcMatter}"/></textarea>
								<div><form:errors path="hbtOpnNdEtcMatter" cssClass="error" /></div>
						</tr>
						<tr>
							<th rowspan="2">해제평가</th>
							<th>재해발생 여부</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="dsstrOccrrncAt" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.dsstrOccrrncAt}"/></textarea>
								<div><form:errors path="dsstrOccrrncAt" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>사면 계류 상태2</th>
							<td colspan="4">
								<textarea rows="5" cols="80" name="relisEvlsMrngSttus2" title="${title} ${inputTxt}" style="resize: none;"><c:out value="${result.relisEvlsMrngSttus2}"/></textarea>
								<div><form:errors path="relisEvlsMrngSttus2" cssClass="error" /></div>
							</td>
						</tr>
						<!-- 항공사진을 통한 주변 환경변화 -->
						<tr>
							<td colspan="6"></td>
						</tr>
						<tr>
							<th colspan="6">항공사진을 통한 주변 환경변화</th>
						</tr>
						<tr>
							<td colspan="3" class="center">
								<input type="file" name="arlphoto1" accept=".jpg, .png" onchange="arlphotoPrevImg(event);">
								<button class="del-btn" style="height:35px;" onclick="fnDeleteLndPrevImg('1'); return false;">삭제</button>
				    			<input type="hidden" name="rawArlphoto1" value="<c:out value='${result.arlphoto1}'/>">
							</td>
							<td colspan="3" class="center">
								<input type="file" name="arlphoto2" accept=".jpg, .png" onchange="arlphotoPrevImg(event);">
								<button class="del-btn" style="height:35px;" onclick="fnDeleteLndPrevImg('2'); return false;">삭제</button>
				    			<input type="hidden" name="rawArlphoto2" value="<c:out value='${result.arlphoto2}'/>">
							</td>
						</tr>
						<tr class="photoTr">
				    		<td colspan="3" id="arlphotoImg1" class="w470 h350">
		 						<c:choose>
									<c:when test="${not empty result.arlphoto1}">
						    			<img alt="산사태취약지역 지정 이전(2013) 사진" style="max-width: 100%" src="<c:url value='${result.arlphoto1}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
									</c:when>
									<c:otherwise>
										<img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;">
									</c:otherwise>
								</c:choose>
				    		</td>
				    		<td colspan="3" id="arlphotoImg2" class="w470 h350">
				    			<c:choose>
									<c:when test="${not empty result.arlphoto2}">
						    			<img alt="산사태취약지역 지정 이후(2018) 사진" style="max-width: 100%" src="<c:url value='${result.arlphoto2}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
									</c:when>
									<c:otherwise>
										<img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;">
									</c:otherwise>
								</c:choose>
				    		</td>
				    	</tr>
						<tr>
							<th colspan="3">산사태취약지역 지정 이전(2013)</th>
							<th colspan="3">산사태취약지역 지정 이후(2018)</th>
						</tr>
						<tr>
							<td colspan="6"></td>
						</tr>
						<!-- 위치도 사진 -->
						<c:if test="${lcmap_result.size() > 0 }">
							<tr><th colspan="6">위치도 사진</th></tr>
							<tr>
								<td colspan="6">
									<div class="slide_box">
										<div class="slide">
											<c:forEach items="${lcmap_result}" var="item" varStatus="status">
												<div>
													<img src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count}"/>번 위치도" onerror="this.remove ? this.remove() : this.removeNode();">
												</div>
											</c:forEach>	
										</div>								
									</div>
								</td>
							</tr>
						</c:if>			
					</tbody>
				</table>
				<c:if test="${result.svyType eq '토석류'}">
					<div class="mainMenu">□ 시뮬레이션 해석 결과 도면</div>					
					<table class="mb30">
						<tr class="center">
							<td>
								<input type="file" name="rsltphoto1" accept=".jpg, .png" onchange="rsltphotoPrevImg(event);">
								<button class="del-btn" style="height:35px;" onclick="fnDeleteRsltPrevImg('1'); return false;">삭제</button>
				    			<input type="hidden" name="rawRsltphoto1" value="<c:out value='${result.rsltphoto1}'/>">
							</td>
						</tr>
						<tr class="photoTr">
				    		<td id="rsltphotoImg1" class="w470 h350">
				    			<c:choose>
									<c:when test="${not empty result.rsltphoto1}">
						    			<img alt="토석류 해석 결과 도면" style="max-width: 100%" src="<c:url value='${result.rsltphoto1}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
									</c:when>
									<c:otherwise>
										<img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 15%;">
									</c:otherwise>
								</c:choose>
				    		</td>
				    	</tr>
					</table>
				</c:if>
				<c:if test="${result.svyType eq '산사태'}">
					<div class="mainMenu">□ 산사태 안정해석 해석 결과 도면</div>					
					<table class="mb30">
						<tr class="center">
							<td>
								<input type="file" name="rsltphoto1" accept=".jpg, .png" onchange="rsltphotoPrevImg(event);">
								<button class="del-btn" style="height:35px;" onclick="fnDeleteRsltPrevImg('1'); return false;">삭제</button>
				    			<input type="hidden" name="rawRsltphoto1" value="<c:out value='${result.rsltphoto2}'/>">
							</td>
							<td>
								<input type="file" name="rsltphoto2" accept=".jpg, .png" onchange="rsltphotoPrevImg(event);">
								<button class="del-btn" style="height:35px;" onclick="fnDeleteRsltPrevImg('2'); return false;">삭제</button>
								<input type="hidden" name="rawRsltphoto2" value="<c:out value='${result.rsltphoto3}'/>">
							</td>
						</tr>
						<tr class="photoTr">
				    		<td id="rsltphotoImg1" class="w470 h350">
					    		<c:choose>
									<c:when test="${not empty result.rsltphoto2}">
						    			<img alt="산사태 해석 결과 도면(건기)" style="max-width: 100%" src="<c:url value='${result.rsltphoto2}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
									</c:when>
									<c:otherwise>
										<img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;">
									</c:otherwise>
								</c:choose>
				    		</td>
				    		<td id="rsltphotoImg2" class="w470 h350">
				    			<c:choose>
									<c:when test="${not empty result.rsltphoto3}">
						    			<img alt="산사태 해석 결과 도면(우기)" style="max-width: 100%" src="<c:url value='${result.rsltphoto3}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
									</c:when>
									<c:otherwise>
										<img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;">
									</c:otherwise>
								</c:choose>
				    		</td>
				    	</tr>
				    	<tr>
							<th>건기 시</th>
							<th>우기 시</th>
						</tr>
					</table>
				</c:if>
				<hr>
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
							            <c:forEach var="j" begin="0" end="5">
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