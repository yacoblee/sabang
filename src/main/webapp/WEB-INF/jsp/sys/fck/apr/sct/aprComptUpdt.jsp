<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="sysLssBsc.svyComptList.update"/></c:set>
<c:set var="clientid"><spring:message code="lcMap.clientId" /></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">외관점검</a></li>
	</ul>
	<h3>${pageTitle}</h3><!-- 대상지 수정 -->
	<div id="contents">
		<input type="hidden" name="photolist" value="<c:out value="${orginl_photo_result}"/>">
		<input type="hidden" name="apiKey" value="${clientid}">
		<input type="hidden" name="mapParam" value="${mapParam}">
		<input type="hidden" name="orginlZoom" value="<c:out value="${orginl_zoom}"/>">
		<form:form id="listForm" commandName="fckAprCompt" action="${pageContext.request.contextPath}/sys/fck/apr/sct/updateFckAprCompt.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="photo" value="<c:out value="${orginl_photo_result}"/>">
			<form:hidden path="gid"/>
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
			<input name="schfckRslt" type="hidden" value="<c:out value='${searchVO.fckRslt}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${fckAprCompt.mstId}'/>"/>
			<input name="changedZoom" type="hidden"  value="<c:out value='${searchVO.changedZoom}'/>">
			<input name="locImgIdx" type="hidden"  value="<c:out value='${searchVO.locImgIdx}'/>">
			<input name="photoTagList" type="hidden"  value="">
			<input name="svySdHidden" type="hidden"  value="<c:out value='${fckAprCompt.svySd}'/>">
			<input name="svySggHidden" type="hidden"  value="<c:out value='${fckAprCompt.svySgg}'/>">
			<input name="svyEmdHidden" type="hidden"  value="<c:out value='${fckAprCompt.svyEmd}'/>">
			<input name="svyRiHidden" type="hidden"  value="<c:out value='${fckAprCompt.svyRi}'/>">
			<input name="svyJibunHidden" type="hidden"  value="<c:out value='${fckAprCompt.svyJibun}'/>">
			<input name="mstNm" type="hidden"  value="<c:out value='${fckAprCompt.mstNm}'/>">
			<input name="mapType" type="hidden" value="SATELLITE">
			<input name="dmgSttus" type="hidden" value="<c:out value='${fckAprCompt.dmgSttus}'/>">
			<input name="errorCheck" type="hidden" value="false">
			<div class="BoardDetail">
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
						<col style="width: 16.7%;">
						<col style="width: 16.7%;">
						<col style="width: 16.7%;">
						<col style="width: 16.7%;">
						<col style="width: 16.7%;">
						<col style="width: 16.7%;">
					</colgroup>
					<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
						<tbody>
							<!-- 입력/선택 -->
							<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
							<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
								<td colspan="2"><form:input style="background-color: #FFF;border:none;" path="svyId" readonly="true"/></td>
								<th><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!--조사유형 -->
								<td colspan="2"><form:input style="background-color: #FFF;border:none;" path="svyType" readonly="true"/></td>
							</tr>
                            <c:if test="${fckAprCompt.svyType eq '사방댐 외관점검'}">
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 사방댐종류 -->
								<td colspan="2">
									<form:select path="knd" title="${title} ${inputSelect}" cssClass="txt" onchange="selectKnd(this);">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${ecnrKndCodeList}" itemValue="codeNm" itemLabel="codeNm" />
										<form:option value="kndEtc" class="kndEtc">직접입력</form:option>
									</form:select>
									<input type="text" name="kndEtc" value="${fckAprCompt.knd}" style="display:none;" disabled/>
							    </td>
								<th><spring:message code="sysFckApr.svyComptVO.ecnrForm" /></th><!-- 형식 -->
								<td colspan="2">
									<form:input path="svyForm" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="svyForm" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
								<td colspan="2">
									<form:input path="ecnrRnum" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="ecnrRnum" cssClass="error" /></div>
								</td>
								<th><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
								<td colspan="2"><c:out value='${fckAprCompt.svyDt}'/></td>
							</tr>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호-->
								<td colspan="2">
									<form:input path="nationSpotNum" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="nationSpotNum" cssClass="error" /></div>
								</td>
								<th><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
								<td colspan="2">
									<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
										<form:input path="svyUser" title="${title} ${inputTxt}"/>
									</sec:authorize>
									<sec:authorize access="!hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
										<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
											<c:out value='${fckAprCompt.svyUser}'/>
										</c:if>
									</sec:authorize>
								</td>
							</tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '산지사방 외관점검' or fckAprCompt.svyType eq '계류보전 외관점검'}">
                            <tr>
                                <th><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
								<td colspan="2">
									<form:select path="knd" title="${title} ${inputSelect}" cssClass="txt" onchange="selectKnd(this);">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${ecnrKndCodeList}" itemValue="codeNm" itemLabel="codeNm" />
										<form:option value="kndEtc" class="kndEtc">직접입력</form:option>
									</form:select>
									<input type="text" name="kndEtc" value="${fckAprCompt.knd}" style="display:none;" disabled/>
							    </td>
                                <th><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
                                <td colspan="2"><c:out value='${fckAprCompt.svyDt}'/></td>
                            </tr>
                            <tr>
                                <th><spring:message code="sysFckApr.svyComptVO.dsgArea"/></th><!-- 지정면적-->
                                <td colspan="2">
                                    <form:input path="dsgArea" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="dsgArea" cssClass="error" /></div>
                                </td>
                                <th><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
                                <td colspan="2"><c:out value='${fckAprCompt.svyUser}'/></td>
                            </tr>
                            </c:if>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지-->
								<td colspan="5">
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
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보(WGS84)-->
								<td colspan="5"><c:out value='${fckAprCompt.svyLatLon}'/></td>
							</tr>
                            <c:if test="${fckAprCompt.svyType eq '사방댐 외관점검'}">
                            <c:set var="title"><spring:message code="sysFckApr.svyComptVO.fclt" /></c:set><!-- 시설제원-->
                            <c:set var="title1"><spring:message code="sysFckApr.svyComptVO.fcltYear" /></c:set><!-- 시설년도 -->
                            <c:set var="title2"><spring:message code="sysFckApr.svyComptVO.fcltUprHg" /></c:set><!-- 상장 -->
                            <c:set var="title3"><spring:message code="sysFckApr.svyComptVO.fcltLwrHg" /></c:set><!-- 하장 -->
                            <c:set var="title4"><spring:message code="sysFckApr.svyComptVO.fcltStkHg" /></c:set><!-- 유효고 -->
                            <tr>
                                <th rowspan="4">${title}</th>
                                <th>${title1}</th>
                                <td colspan="4">
                                    <form:input path="fcltYear" title="${title1} ${inputTxt}"/>
                                        <div><form:errors path="fcltYear" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title2}</th>
                                <td colspan="4">
                                    <form:input path="fcltUprHg" title="${title2} ${inputTxt}"/>
                                        <div><form:errors path="fcltUprHg" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title3}</th>
                                <td colspan="4">
                                    <form:input path="fcltLwrHg" title="${title3} ${inputTxt}"/>
                                        <div><form:errors path="fcltLwrHg" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title4}</th>
                                <td colspan="4">
                                    <form:input path="fcltStkHg" title="${title4} ${inputTxt}"/>
                                        <div><form:errors path="fcltStkHg" cssClass="error" /></div>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '산지사방 외관점검'}">
                            <c:set var="title"><spring:message code="sysFckApr.svyComptVO.fclt" /></c:set><!-- 시설제원-->
                            <c:set var="title1"><spring:message code="sysFckApr.svyComptVO.fcltYear" /></c:set><!-- 시설년도 -->
                            <c:set var="title2"><spring:message code="sysFckApr.svyComptVO.fcltHg" /></c:set><!-- 높이 -->
                            <c:set var="title3"><spring:message code="sysFckApr.svyComptVO.fcltRng" /></c:set><!-- 폭 -->
                            <c:set var="title4"><spring:message code="sysFckApr.svyComptVO.fcltSlp" /></c:set><!-- 평균경사 -->
                            <tr>
                                <th rowspan="4">${title}</th>
                                <th>${title1}</th>
                                <td colspan="4">
                                    <form:input path="fcltYear" title="${title1} ${inputTxt}"/>
                                        <div><form:errors path="fcltYear" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title2}</th>
                                <td colspan="4">
                                    <form:input path="fcltHg" title="${title2} ${inputTxt}"/>
                                        <div><form:errors path="fcltHg" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title3}</th>
                                <td colspan="4">
                                    <form:input path="fcltRng" title="${title3} ${inputTxt}"/>
                                        <div><form:errors path="fcltRng" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title4}</th>
                                <td colspan="4">
                                    <form:input path="fcltSlp" title="${title4} ${inputTxt}"/>
                                        <div><form:errors path="fcltSlp" cssClass="error" /></div>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '계류보전 외관점검'}">
                            <c:set var="title"><spring:message code="sysFckApr.svyComptVO.fclt" /></c:set><!-- 시설제원-->
                            <c:set var="title1"><spring:message code="sysFckApr.svyComptVO.fcltYear" /></c:set><!-- 시설년도 -->
                            <c:set var="title2"><spring:message code="sysFckApr.svyComptVO.fcltLng" /></c:set><!-- 길이 -->
                            <c:set var="title3"><spring:message code="sysFckApr.svyComptVO.fcltRng" /></c:set><!-- 폭 -->
                            <c:set var="title4"><spring:message code="sysFckApr.svyComptVO.fcltDept" /></c:set><!-- 깊이 -->
                            <tr>
                                <th rowspan="4">${title}</th>
                                <th>${title1}</th>
                                <td colspan="4">
                                    <form:input path="fcltYear" title="${title1} ${inputTxt}"/>
                                        <div><form:errors path="fcltYear" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title2}</th>
                                <td colspan="4">
                                    <form:input path="fcltLng" title="${title2} ${inputTxt}"/>
                                        <div><form:errors path="fcltLng" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title3}</th>
                                <td colspan="4">
                                    <form:input path="fcltRng" title="${title3} ${inputTxt}"/>
                                        <div><form:errors path="fcltRng" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <th>${title4}</th>
                                <td colspan="4">
                                    <form:input path="fcltDept" title="${title4} ${inputTxt}"/>
                                        <div><form:errors path="fcltDept" cssClass="error" /></div>
                                </td>
                            </tr>
                        </c:if>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
						<tbody class="svycomptDetail">
							<tr class="center">
								<th colspan="6"><spring:message code="sysFckApr.svyComptVO.mainfclt" /></th>
							</tr>
                            <c:if test="${fckAprCompt.svyType eq '사방댐 외관점검'}">
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.orginlDam" /></c:set><!-- 본댐-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.orginlDamVal" />								
                                </th>
                                <td colspan="4">
                                    <form:select path="orginlDamCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${orginlDamCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="orginlDamVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="orginlDamVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.orginlDamJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="orginlDamJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.sideWall" /></c:set><!-- 측벽-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.sideWallVal" />								
                                </th>
                                <td colspan="4">
                                    <form:select path="sideWallCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${sideWallCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="sideWallVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="sideWallVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.sideWallJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                        <form:select path="sideWallJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.dwnspt" /></c:set><!-- 물받이-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.dwnsptVal" />								
                                </th>
                                <td colspan="4">
                                    <form:select path="dwnsptCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${dwnSptCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="dwnsptVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="dwnsptVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.dwnsptJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="dwnsptJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '산지사방 외관점검'}">
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.reinfc" /></c:set><!-- 보강시설-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.reinfcVal" />								
                                </th>
                                <td colspan="4">
                                        <form:select path="reinfcCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${reinfcCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="reinfcVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="reinfcVal" cssClass="error" /></div>
                                        
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.reinfcJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="reinfcJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.prtc" /></c:set><!-- 보호시설-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.prtcVal" />								
                                </th>
                                <td colspan="4">
                                        <form:select path="prtcCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${prtcCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="prtcVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="prtcVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.prtcJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="prtcJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.drng" /></c:set><!-- 배수시설-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.drngVal" />								
                                </th>
                                <td colspan="4">
                                        <form:select path="drngCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${drngCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="drngVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="drngVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.drngJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="drngJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '계류보전 외관점검'}">
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.chkdam" /></c:set><!-- 골막이-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.chkdamVal" />								
                                </th>
                                <td colspan="4">
                                        <form:select path="chkdamCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${mooringMainfcltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="chkdamVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="chkdamVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.chkdamJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="chkdamJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>                            
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.rvtmnt" /></c:set><!-- 기슭막이-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.rvtmntVal" />								
                                </th>
                                <td colspan="4">
                                        <form:select path="rvtmntCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${mooringMainfcltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="rvtmntVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="rvtmntVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.rvtmntJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="rvtmntJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.grdstabl" /></c:set><!-- 바닥막이-->
                                <th rowspan="2">${title}</th>
                                <th>
                                    <spring:message code="sysFckApr.svyComptVO.grdstablVal" />								
                                </th>
                                <td colspan="4">
                                        <form:select path="grdstablCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${mooringMainfcltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                    <form:input class="selectJdgVal" path="grdstablVal" title="${title} ${inputTxt}"/>
                                        <div><form:errors path="grdstablVal" cssClass="error" /></div>
                                </td>
                            </tr>
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.grdstablJdgVal" /></c:set>
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="grdstablJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            </c:if>
                            <tr>
								<th colspan="6"><spring:message code="sysFckApr.svyComptVO.subfclt" /></th>
							</tr>
							<c:set var="title1"><spring:message code="sysFckApr.svyComptVO.flugt" /></c:set><!-- 수문 -->
							<c:set var="title2"><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></c:set><!-- 식생상태 -->
							<c:set var="title3"><spring:message code="sysFckApr.svyComptVO.sffc" /></c:set><!-- 안전시설 -->
							<c:set var="title4"><spring:message code="sysFckApr.svyComptVO.accssrd" /></c:set><!-- 접근도로 -->
							<c:set var="title5"><spring:message code="sysFckApr.svyComptVO.etc" /></c:set><!-- 기타 -->
							<tr class="center">
								<th>${title1}</th>
								<th>${title2}</th>
								<th>${title3}</th>
								<th>${title4}</th>
								<th>${title5}</th>
							</tr>
							<tr class="center">
								<td>
									<form:select path="flugtJdgVal" title="${title1} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
								<td>
					   				<form:select path="vtnsttusJdgVal" title="${title2} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
								<td>
									<form:select path="sffcJdgVal" title="${title3} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
								<td>
									<form:select path="accssrdJdgVal" title="${title4} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
								<td>
									<form:select path="etcJdgVal" title="${title5} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>								
							</tr>
                            <c:if test="${fckAprCompt.svyType eq '사방댐 외관점검'}">
                            <tr>
                                <c:set var="title1"><spring:message code="sysFckApr.svyComptVO.snddpsitVal" /></c:set><!-- 저사량 -->
                                <c:set var="title2"><spring:message code="sysFckApr.svyComptVO.snddpsitJdgVal" /></c:set><!-- 저사상태 판정값 -->
                                <th>${title1}</th>
                                <td colspan="2">
                                        <form:input path="snddpsitVal" id="snddpsitVal" title="${title1} ${inputTxt}" onchange="fncChange(this);" />
                                        <div><form:errors path="snddpsitVal" cssClass="error" /></div>
                                </td>
                                <th>${title2}</th>
                                <td colspan="1">
                                     <select name="snddpsitJdgVal" id="snddpsitJdgVal">
                                        <option value="" label="${inputSelect}"/>
                                        <c:forEach var="item" items="${snddpsitJdgValCodeList}">
                                        	<option value="${item.codeNm}" label="${item.codeNm}" <c:if test="${fn:replace(item.codeNm,' ','') eq fn:replace(fckAprCompt.snddpsitJdgVal,' ','')}">selected</c:if>/>	
                                        </c:forEach>
                                     </select>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '산지사방 외관점검'}">
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.slpJdgVal" /></c:set><!-- 사면상태 판정값 -->
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="slopeJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${fckAprCompt.svyType eq '계류보전 외관점검'}">
                            <tr>
                                <c:set var="title"><spring:message code="sysFckApr.svyComptVO.mooringJdgVal" /></c:set><!-- 계류상태 판정값 -->
                                <th>${title}</th>
                                <td colspan="4">
                                    <form:select path="mooringJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                        <form:option value="" label="${inputSelect}"/>
                                        <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                                    </form:select>
                                </td>
                            </tr>
                            </c:if>
                            <tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.fckRslt" /></c:set><!-- 최종점검 결과 -->
								<th>${title}</th>
								<td colspan="5">
					   				<form:select path="fckRslt" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${fckRsltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.mngmtr" /></c:set><!-- 조치사항 -->
								<th>${title}</th>
								<td colspan="5">
					   				<form:select path="mngmtr" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${mngmtrCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.appnRelis" /></c:set><!-- 사방지 지정해제 가능여부 -->
								<th>${title}</th>
								<td colspan="5">
									<form:select path="appnRelis" title="${title} ${inputSelect}" cssClass="txt">
										<form:option value="" label="${inputSelect}"/>
										<form:options items="${appnRelisCodeList}" itemValue="codeNm" itemLabel="codeNm" />
									</form:select>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.opinion1" /></c:set><!-- 종합의견1 -->
								<th>${title}</th>
								<td colspan="5">
									<form:input path="opinion1" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="opinion1" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.opinion2" /></c:set><!-- 종합의견2 -->
								<th>${title}</th>
								<td colspan="5">
									<form:input path="opinion2" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="opinion2" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.opinion3" /></c:set><!-- 종합의견3 -->
								<th>${title}</th>
								<td colspan="5">
									<form:input path="opinion3" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="opinion3" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.opinion4" /></c:set><!-- 종합의견4 -->
								<th>${title}</th>
								<td colspan="5">
									<form:input path="opinion4" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="opinion4" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<c:set var="title"><spring:message code="sysFckApr.svyComptVO.opinion5" /></c:set><!-- 종합의견5 -->
								<th>${title}</th>
								<td colspan="5">
									<form:input path="opinion5" title="${title} ${inputTxt}"/>
					   				<div><form:errors path="opinion5" cssClass="error" /></div>
								</td>
							</tr>
							
							<c:set var="title"><spring:message code="sysFckApr.svyComptVO.lcmap"/></c:set><!-- 위치도 -->
							<tr>
								<th colspan="6">${title}</th>
							</tr>
							<tr>								
								<td colspan="6">
							        <div class="ol-custom-control ol-basemap-control" id="toggle-btn">
								        <button type="button" class="btn-map-selector" title="일반지도" value="NORMAL">일반지도</button>
								        <button type="button" class="btn-map-selector" title="위성지도" value="SATELLITE">위성지도</button>
								    </div>
									<div id="map" style="width:100%;height:400px;"></div>
								</td>
							</tr>
							
							<c:set var="title"><spring:message code="sysFckApr.svyComptVO.photo"/></c:set><!-- 현장사진 -->
						    <tr><th colspan="6">${title}</th></tr>
                        </tbody>
                    </table>
				<div class="photoWrap">
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
					            <c:forEach var="j" begin="0" end="${photoTagNum}">
					                <option value="<c:out value="${j+1}"/>" <c:if test="${fn:contains(item.TAG,'.') && fn:substring(item.TAG,1,2) eq '.' && fn:split(item.TAG,'.')[0] eq j+1}">selected="selected"</c:if>><c:out value="${j+1}"/></option>
					            </c:forEach>
					        </select>
					        <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag<c:out value="${status.count }"/>"
				            	<c:choose>
					                <c:when test="${fn:contains(item,'TAG') && fn:contains(item.TAG,'.') && fn:substring(item.TAG,1,2) eq '.' }">placeholder="<c:out value="${fn:substringAfter(item.TAG,'.')}"/>" value="<c:out value="${fn:substringAfter(item.TAG,'.')}"/>"</c:when>
					                <c:when test="${fn:contains(item,'TAG') && fn:length(item.TAG) > 0 }">placeholder="<c:out value="${item.TAG}"/>" value="<c:out value="${item.TAG}"/>"</c:when>
					                <c:otherwise>placeholder="사진태그없음"</c:otherwise>
					            </c:choose> 
					        /> 
					        <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncFckAprDeletePhoto(event); return false;">삭제</button>
				    	</div>
				    	<div class="photoUrl">
				    		<input type="hidden" <c:if test="${noImage eq false}">value="<c:url value='${photoUrl}'/>"</c:if> name="photoSrc<c:out value="${status.count}"/>" />
		                    <img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> class="photoSrc<c:out value="${status.count}"/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
		                    <span class="thumb-div noselect">변경</span>
				    	</div>
				    </div>
				    </c:forEach>
			    </div>
			    <div style="margin-top: 15px;">
			    	<button type="button" class="add-btn" onclick="javascript:fncFckAprAddPhoto(); return false;" style="float: right; margin-bottom: 10px;">현장사진 <spring:message code="button.add" /></button>
				    <hr style="border: #333 solid 0.15rem; clear: both; margin-bottom: 40px;">
			    </div>
			    <c:if test="${fckAprCompt.svyType eq '사방댐 외관점검'}">
				    <table style="text-align: center;">
				    	<colgroup>
				    		<col width="25%;">
				    		<col width="25%;">
				    		<col width="25%;">
				    		<col width="25%;">
				    	</colgroup>
				    	<tr>
				    		<th colspan="4"><spring:message code="sysFckApr.svyComptVO.sttusPrnt" /></th><!-- 피해발생 현황도 -->
				    	</tr>
				    	<tr>
				    		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sttusPrnt1" /></th><!-- 반수면 -->
				    		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sttusPrnt2" /></th><!-- 대수면 -->
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    			<input type="file" name="sttusPrnt1" accept=".jpg, .png" onchange="SttusPrntImg(event);">
				    			<button class="del-btn" style="height:35px;" onclick="fnDeleteSttusPrntImg(1); return false;">삭제</button>
				    			<input type="hidden" name="rawSttusPrnt1" value="<c:out value='${fckAprCompt.sttusPrnt1}'/>">
				    		</td>
				    		<td colspan="2">
				    			<input type="file" name="sttusPrnt2" accept=".jpg, .png" onchange="SttusPrntImg(event);">
				    			<button class="del-btn" style="height:35px;" onclick="fnDeleteSttusPrntImg(2); return false;">삭제</button>
				    			<input type="hidden" name="rawSttusPrnt2" value="<c:out value='${fckAprCompt.sttusPrnt2}'/>">
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2" id="sttusPrntImg1" class="w470 h350">
				    			<img alt="반수면" style="max-width: 100%" src="<c:url value='${fckAprCompt.sttusPrnt1}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
				    		</td>
				    		<td colspan="2" id="sttusPrntImg2" class="w470 h350">
				    			<img alt="대수면" style="max-width: 100%" src="<c:url value='${fckAprCompt.sttusPrnt2}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
				    		</td>
				    	</tr>
				    	<tr>
				    		<th colspan="4"><spring:message code="sysFckApr.svyComptVO.sttusPrnt3" /></th><!-- 본체 천단부·천단부·방수로·물받이 -->
				    	</tr>
				    	<tr>
				    		<td colspan="4">
				    			<input type="file" name="sttusPrnt3" accept=".jpg, .png" onchange="SttusPrntImg(event);">
				    			<button class="del-btn" style="height:35px;" onclick="fnDeleteSttusPrntImg(3); return false;">삭제</button>
				    			<input type="hidden" name="rawSttusPrnt3" value="<c:out value='${fckAprCompt.sttusPrnt3}'/>">
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="4" id="sttusPrntImg3" class="w470 h350">
				    			<img alt="본체 천단부·천단부·방수로·물받이" style="max-width: 70%" src="<c:url value='${fckAprCompt.sttusPrnt3}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
				    		</td>
				    	</tr>
				    	<tr>
				    		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sttusPrnt4" /></th><!-- 좌안측벽 -->
				    		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sttusPrnt5" /></th><!-- 우안측벽 -->
				    	</tr>
				    	<tr>
				    		<td colspan="2">
				    			<input type="file" name="sttusPrnt4" accept=".jpg, .png" onchange="SttusPrntImg(event);">
				    			<button class="del-btn" style="height:35px;" onclick="fnDeleteSttusPrntImg(4); return false;">삭제</button>
				    			<input type="hidden" name="rawSttusPrnt4" value="<c:out value='${fckAprCompt.sttusPrnt4}'/>">
				    		</td>
				    		<td colspan="2"> 
				    			<input type="file" name="sttusPrnt5" accept=".jpg, .png" onchange="SttusPrntImg(event);">
				    			<button class="del-btn" style="height:35px;" onclick="fnDeleteSttusPrntImg(5); return false;">삭제</button>
				    			<input type="hidden" name="rawSttusPrnt5" value="<c:out value='${fckAprCompt.sttusPrnt5}'/>">
				    		</td>
				    	</tr>
				    	<tr>
				    		<td colspan="2" id="sttusPrntImg4" class="w470 h350">
				    			<img alt="좌안측벽" style="max-width: 100%" src="<c:url value='${fckAprCompt.sttusPrnt4}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
				    		</td>
				    		<td colspan="2" id="sttusPrntImg5" class="w470 h350">
				    			<img alt="우안측벽" style="max-width: 100%" src="<c:url value='${fckAprCompt.sttusPrnt5}'/>" onerror="this.remove ? this.remove() : this.removeNode();">
				    		</td>
				    	</tr>
				    </table>
			    </c:if>
		      	<c:if test="${fckAprCompt.svyType eq '계류보전 외관점검'}">
			      	 <div class="dmgSttusBtnArea" style="margin-top: 10px;margin-bottom: 10px; float: right;">
				    	<button type="button" class="add-btn" onclick="javascript:fncFckAprAddDelDmgSttusRow('add1'); return false;">대상지위치추가</button><button type="button" class="del-btn" onclick="javascript:fncFckAprAddDelDmgSttusRow('del1'); return false;">대상지위치삭제</button>
				    </div>
			   			<table class="dmgSttusTable">
			   				<colgroup>
					   			<col width="10%;">
					   			<col width="10%;">
					   			<col width="20%;">
					   			<col width="20%;">
					   			<col width="30%;">
					   			<col width="10%;">
				   			</colgroup>
							<tr>						
								<th colspan="6"><spring:message code="sysFckApr.svyComptVO.trglnd" /></th><!-- 대상지 위치 및 주요시설 -->							
							</tr>
							<tr>						
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNo" /></th><!-- 번호 -->
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndLc"/></th><!-- 위치 -->
								<th colspan="2"><spring:message code="sysFckApr.svyComptVO.trglndPnt" /></th><!-- 좌표 -->
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndStrctu" /></th><!-- 주요시설물 -->
								<%-- <th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndSttus" /></th><!-- 현황 --> --%>
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNote" /></th><!-- 비고 -->
							</tr>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.trglndPy" /></th><!-- 위도 -->
								<th><spring:message code="sysFckApr.svyComptVO.trglndPx" /></th><!-- 경도 -->
							</tr>
					   		<c:if test="${trglnd_result.size() > 0}">
	 						<c:forEach items="${trglnd_result}" var="item" varStatus="status">
							<tr class="center trglndTr">
								<td rowspan="2"><c:out value="${status.count}"/></td>
								<td><spring:message code="sysFckApr.svyComptVO.trglndLcPnttm"/></td>					
								<td>
									<input type="text" name="dmgTrglndBgPyD${status.count}" value="<c:out value="${item.pyD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
									<input type="text" name="dmgTrglndBgPyM${status.count}" value="<c:out value="${item.pyM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
									<input type="text" name="dmgTrglndBgPyS${status.count}" value="<c:out value="${item.pyS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
								</td>
								<td>
									<input type="text" name="dmgTrglndBgPxD${status.count}" value="<c:out value="${item.pxD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
									<input type="text" name="dmgTrglndBgPxM${status.count}" value="<c:out value="${item.pxM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
									<input type="text" name="dmgTrglndBgPxS${status.count}" value="<c:out value="${item.pxS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
								</td>
								<td rowspan="2"><input name="dmgTrglndStrctu${status.count}" type="text" value="<c:out value="${item.strctu}"/>"></td>
								<td rowspan="2"><input type="text" name="dmgTrglndNote${status.count}" value="<c:out value="${item.note}"/>"></td>
								<c:forEach items="${trglnd_photo_result}" var="photoItem" varStatus="photoStatus">
									<td style="display: none;"><input type="text" name="dmgTrglndPhoto${photoStatus.count}" value="<c:out value="${photoItem.photo}"/>"></td>
									<td style="display: none;"><input type="text" name="dmgTrglndPhotoTag${photoStatus.count}" value="<c:out value="${photoItem.photoTag}"/>"></td>
								</c:forEach>
							</tr>
							<tr class="center">
								<td style="text-align:center;"><spring:message code="sysFckApr.svyComptVO.trglndLcTmnl"/></td>
								<td>
									<input type="text" name="dmgTrglndEdPyD${status.count}" value="<c:out value="${item.pyD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
									<input type="text" name="dmgTrglndEdPyM${status.count}" value="<c:out value="${item.pyM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
									<input type="text" name="dmgTrglndEdPyS${status.count}" value="<c:out value="${item.pyS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
								</td>
								<td>
									<input type="text" name="dmgTrglndEdPxD${status.count}" value="<c:out value="${item.pxD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
									<input type="text" name="dmgTrglndEdPxM${status.count}" value="<c:out value="${item.pxM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
									<input type="text" name="dmgTrglndEdPxS${status.count}" value="<c:out value="${item.pxS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
								</td>							
							</tr>
							</c:forEach>
							</c:if>
						</table>
					
					<!-- 피해시설 위치 및 피해현황 -->
					<div class="dmgSttusBtnArea" style="margin-top: 10px;margin-bottom: 10px; float: right;">
			    		<button type="button" class="add-btn" onclick="javascript:fncFckAprAddDelDmgSttusRow('add2'); return false;">피해시설위치추가</button><button type="button" class="del-btn" onclick="javascript:fncFckAprAddDelDmgSttusRow('del2'); return false;">피해시설위치삭제</button>
			   		</div>
					<table class="dmgFcltTable">
						<colgroup>
				   			<col width="10%;">
				   			<col width="20%;">
				   			<col width="20%;">
				   			<col width="20%;">
				   			<col width="20%;">
				   			<col width="10%;">
			   			</colgroup>
						<tr>						
							<th colspan="6"><spring:message code="sysFckApr.svyComptVO.dmgFclt" /></th><!-- 피해시설 위치 및 현황 -->							
						</tr>
						<tr>						
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltNo" /></th><!-- 번호 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltPnt" /></th><!-- 좌표 -->
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltStrctu" /></th><!-- 주요시설물 -->
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltSttus" /></th><!-- 현황 -->
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltNote" /></th><!-- 비고 -->
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.dmgFcltPy" /></th><!-- 위도 -->
							<th><spring:message code="sysFckApr.svyComptVO.dmgFcltPx" /></th><!-- 경도 -->
						</tr>
					<c:if test="${dmgFclt_result.size() > 0}">
					<c:forEach items="${dmgFclt_result}" var="item" varStatus="status">
						<tr class="center">
							<td><c:out value="${status.count}"/></td>							
							<td>
								<input type="text" name="dmgFcltPyD${status.count}" value="<c:out value="${item.pyD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="dmgFcltPyM${status.count}" value="<c:out value="${item.pyM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="dmgFcltPyS${status.count}" value="<c:out value="${item.pyS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
							</td>
							<td>
								<input type="text" name="dmgFcltPxD${status.count}" value="<c:out value="${item.pxD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="dmgFcltPxM${status.count}" value="<c:out value="${item.pxM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="dmgFcltPxS${status.count}" value="<c:out value="${item.pxS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
							</td>
							<td><input type="text" name="dmgFcltStrctu${status.count}" value="<c:out value="${item.strctu}"/>"/></td>
							<td><input type="text" name="dmgFcltSttus${status.count}" value="<c:out value="${item.sttus}"/>"/></td>
							<td><input type="text" name="dmgFcltNote${status.count}" value="<c:out value="${item.note}"/>"/></td>
							<c:forEach items="${dmgFclt_photo_result}" var="photoItem" varStatus="photoStatus">
								<td style="display: none;"><input type="text" name="dmgFcltPhoto${photoStatus.count}" value="<c:out value="${photoItem.photo}"/>"></td>
								<td style="display: none;"><input type="text" name="dmgFcltPhotoTag${photoStatus.count}" value="<c:out value="${photoItem.photoTag}"/>"></td>
							</c:forEach>
						</tr>
					</c:forEach>
					</c:if>
<%-- 					<c:forEach items="${dmgFclt_result}" var="item" varStatus="status"> --%>
<%-- 					<c:set var="photoTag" value="${item.photoTag }"/> --%>
<%-- 						<c:set var="photo" value="${item.photo }"/> --%>
<%-- 						<c:if test="${!empty photo}"> --%>
<%-- 						<tr><th colspan="6"><c:out value="${status.count }"/>번 피해시설</th></tr> --%>
<!-- 						<tr class="photoTr"> -->
<!-- 							 <td colspan="6"> -->
<!-- 							 	<div> -->
<%-- 									<c:choose> --%>
<%-- 									 	<c:when test="${!empty photoTag}"> --%>
<%-- 									 		<c:forEach items="${photoTag}" var="item" varStatus="status"> --%>
<!-- 									 			<div class="photoBox"> -->
<%-- 													<img src="/storage/fieldBook<c:url value='${item.FILENAME}'/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">	 --%>
<%-- 									 				<span><c:out value="${item.TAG}"/></span> --%>
<!-- 									 			</div> -->
<%-- 											</c:forEach> --%>
<%-- 									 	</c:when> --%>
<%-- 								 		<c:when test="${empty photoTag and !empty photo}"> --%>
<%-- 								 			<c:forEach items="${photo}" var="item" varStatus="status"> --%>
<%-- 								 				<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 200px; height: 200px; margin-right: 30px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">  --%>
<%-- 											</c:forEach> --%>
<%-- 								 		</c:when> --%>
<%-- 								 	</c:choose> --%>
<!-- 								</div> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<%-- 						</c:if> --%>
<%-- 						</c:forEach> --%>
					</tbody>
				</table>
				</c:if>
				<c:if test="${fckAprCompt.svyType eq '산지사방 외관점검'}">
			      	 <div class="dmgSttusBtnArea" style="margin-top: 10px;margin-bottom: 10px; float: right;">
				    	<button type="button" class="add-btn" onclick="javascript:fncFckAprAddDelDmgSttusRow('add1'); return false;">대상지위치추가</button><button type="button" class="del-btn" onclick="javascript:fncFckAprAddDelDmgSttusRow('del1'); return false;">대상지위치삭제</button>
				    </div>
						<table class="dmgSttusTable">
							<colgroup>
					   			<col width="10%;">
					   			<col width="20%;">
					   			<col width="20%;">
					   			<col width="20%;">
					   			<col width="20%;">
					   			<col width="10%;">
				   			</colgroup>
							<tr>						
								<th colspan="6"><spring:message code="sysFckApr.svyComptVO.trglnd" /></th><!-- 대상지 위치 및 주요시설 -->							
							</tr>
							<tr>						
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNo" /></th><!-- 번호 -->
								<th colspan="2"><spring:message code="sysFckApr.svyComptVO.trglndPnt" /></th><!-- 좌표 -->
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndStrctu" /></th><!-- 주요시설물 -->
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndSttus" /></th><!-- 현황 -->
								<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNote" /></th><!-- 비고 -->
							</tr>
							<tr>
								<th><spring:message code="sysFckApr.svyComptVO.trglndPy" /></th><!-- 위도 -->
								<th><spring:message code="sysFckApr.svyComptVO.trglndPx" /></th><!-- 경도 -->
							</tr>
							<c:if test="${trglnd_result.size() > 0 }">
							<c:forEach items="${trglnd_result}" var="item" varStatus="status">
							<tr class="center">
								<td><c:out value="${status.count}"/></td>							
								<td>
									<input type="text" name="dmgTrglndPyD${status.count}" value="<c:out value="${item.pyD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
									<input type="text" name="dmgTrglndPyM${status.count}" value="<c:out value="${item.pyM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
									<input type="text" name="dmgTrglndPyS${status.count}" value="<c:out value="${item.pyS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
								</td>
								<td>
									<input type="text" name="dmgTrglndPxD${status.count}" value="<c:out value="${item.pxD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
									<input type="text" name="dmgTrglndPxM${status.count}" value="<c:out value="${item.pxM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
									<input type="text" name="dmgTrglndPxS${status.count}" value="<c:out value="${item.pxS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
								</td>
								<td><input type="text" name="dmgTrglndStrctu${status.count}" value="<c:out value="${item.strctu}"/>"/></td>
								<td><input type="text" name="dmgTrglndSttus${status.count}" value="<c:out value="${item.sttus}"/>"/></td>
								<td><input type="text" name="dmgTrglndNote${status.count}" value="<c:out value="${item.note}"/>"/></td>
								<c:forEach items="${trglnd_photo_result}" var="photoItem" varStatus="photoStatus">
									<td style="display: none;"><input type="text" name="dmgTrglndPhoto${photoStatus.count}" value="<c:out value="${photoItem.photo}"/>"></td>
									<td style="display: none;"><input type="text" name="dmgTrglndPhotoTag${photoStatus.count}" value="<c:out value="${photoItem.photoTag}"/>"></td>
								</c:forEach>
							</tr>
							</c:forEach>
							</c:if>
						</table>
					</c:if>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncFckAprComptUserUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
		
	</div>
</div>