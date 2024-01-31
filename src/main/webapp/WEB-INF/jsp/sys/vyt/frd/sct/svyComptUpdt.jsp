<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysVytFrd.svyComptList.title"/></c:set>
<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 조사완료 수정 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/vyt/frd/sct/updateFrdSvyCompt.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="gid" value="<c:out value="${svyComptResult.gid}"/>"/>
			<input type="hidden" name="mstId" value="<c:out value="${svyComptResult.mstId}"/>"/>
			<input type="hidden" name="analAt" value=""/>
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>"/>
			<input name="schfrdType" type="hidden" value="<c:out value='${searchMap.schfrdType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchMap.schsvyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchMap.schsvyMonth}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchMap.schsvySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchMap.schsvySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchMap.schsvyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchMap.schsvyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchMap.schsvyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchMap.schsvyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchMap.schmstNm}'/>"/>
			<div class="BoardDetail">
				<table id="frdSvyComptTable" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tr>
						<th>조사ID</th>
						<td>
							<input type="text" name="svyId" value="<c:out value="${svyComptResult.svyId}"/>" readonly="readonly"/>	
						</td>
						<th>관할청</th>
						<td colspan="3">
							<select id="compentAssort" name="compentAssort" class="w30p" onchange="fncCompentAuthChange(); return false;">
								<option value="">${inputSelect}</option>
								<c:forEach var="result" items="${subCompentauthList}">
									<option value="<c:out value="${result.codeNm}"/>"<c:if test="${compentauthType.compentauthType eq result.codeNm}">selected="selected"</c:if>><c:out value="${result.codeNm}"/></option>
								</c:forEach>
							</select>
							<select id="compentauth" name="compentauth" class="w30p" onchange="fncSubCompentAuthChange(); return false;">
								<option value=""<c:if test="${empty svyComptResult.compentauth || svyComptResult.compentauth == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="regionCodeList" items="${regionCodeList}" varStatus="status">
									<c:if test="${compentauthType.compentauthType eq '국유림' }">
										<option value="<c:out value="${regionCodeList.codeNm}"/>"<c:if test="${svyComptResult.compentauth eq regionCodeList.codeNm}">selected="selected"</c:if>><c:out value="${regionCodeList.codeNm}"/></option>
									</c:if>
									<c:if test="${compentauthType.compentauthType eq '민유림' }">
										<option value="<c:out value="${regionCodeList.adminNm}"/>"<c:if test="${svyComptResult.compentauth eq regionCodeList.adminNm}">selected="selected"</c:if>><c:out value="${regionCodeList.adminNm}"/></option>
									</c:if>
								</c:forEach>
							</select>
							<select id="subcompentauth" name="subcompentauth" class="w30p">
								<option value=""<c:if test="${empty svyComptResult.subcompentauth || svyComptResult.subcompentauth == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="region2CodeList" items="${region2CodeList}" varStatus="status">
									<c:if test="${compentauthType.compentauthType eq '국유림' }">
										<option value="<c:out value="${region2CodeList.codeNm}"/>"<c:if test="${svyComptResult.subcompentauth eq region2CodeList.codeNm}">selected="selected"</c:if>><c:out value="${region2CodeList.codeNm}"/></option>
									</c:if>
									<c:if test="${compentauthType.compentauthType eq '민유림' }">
										<option value="<c:out value="${region2CodeList.adminNm}"/>"<c:if test="${svyComptResult.subcompentauth eq region2CodeList.adminNm}">selected="selected"</c:if>><c:out value="${region2CodeList.adminNm}"/></option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>소속</th>
						<td>한국치산기술협회</td>
						<th>조사자</th>
						<td><c:out value="${svyComptResult.svyUser}"/></td>
						<th>조사일</th>
						<td><c:out value="${svyComptResult.svyDt}"/></td>
					</tr>
					<tr>
						<th>소재지</th>
						<td colspan="5">
							<select id="svySdView" name="svySd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministCtprvnNmChange(); return false;">
								<option value=""<c:if test="${empty svyComptResult.svySd || svyComptResult.svySd == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="sdCodeList" items="${sdCodeList}" varStatus="status">
								<option value="<c:out value="${sdCodeList.adminNm}"/>"<c:if test="${svyComptResult.svySd eq sdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sdCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="svySggView" name="svySgg" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministSignguNmChange(); return false;">
								<option value=""<c:if test="${empty svyComptResult.svySgg || svyComptResult.svySgg == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="sggCodeList" items="${sggCodeList}" varStatus="status">
								<option value="<c:out value="${sggCodeList.adminNm}"/>"<c:if test="${svyComptResult.svySgg eq sggCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sggCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="svyEmdView" name="svyEmd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministEmdNmChange(); return false;">
								<option value=""<c:if test="${empty svyComptResult.svyEmd || svyComptResult.svyEmd == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="emdCodeList" items="${emdCodeList}" varStatus="status">
								<option value="<c:out value="${emdCodeList.adminNm}"/>"<c:if test="${svyComptResult.svyEmd eq emdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${emdCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="svyRiView" name="svyRi" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15">
								<option value=""<c:if test="${empty svyComptResult.svyRi || svyComptResult.svyRi == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="riCodeList" items="${riCodeList}" varStatus="status">
								<option value="<c:out value="${riCodeList.adminNm}"/>"<c:if test="${svyComptResult.svyRi eq riCodeList.adminNm}">selected="selected"</c:if>><c:out value="${riCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<input type="text" name="svyJibun" class="wd15" value="<c:out value="${svyComptResult.svyJibun}"/>" title="${title} ${inputTxt}"/>
						</td>
					</tr>
					<tr>
						<th>임도종류</th>
						<td colspan="2`">
							<select id="frdType" name="frdType" title="<spring:message code="sysVytFrd.fieldBookVO.frd" /><spring:message code="sysVytFrd.fieldBookVO.type" />">
								<option value=""<c:if test="${empty svyComptResult.frdType || svyComptResult.frdType == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="frdTypeList" items="${frdTypeList}" varStatus="status">
								<option value="<c:out value="${frdTypeList.codeNm}"/>"<c:if test="${svyComptResult.frdType eq frdTypeList.codeNm}">selected="selected"</c:if>><c:out value="${frdTypeList.codeNm}"/></option>
								</c:forEach>
							</select>
						</td>
<%-- 						<th>노선종류</th> --%>
<%-- 						<td> --%>
<%-- 							<select id="routeType" name="routeType" title="<spring:message code="sysVytFrd.fieldBookVO.routetype"/>"> --%>
<%-- 								<option value=""<c:if test="${empty svyComptResult.routeType || svyComptResult.routeType == '0'}">selected="selected"</c:if>> ${inputSelect}</option> --%>
<%-- 								<c:forEach var="routeTypeList" items="${routeTypeList}" varStatus="status"> --%>
<%-- 								<option value="<c:out value="${routeTypeList.codeNm}"/>"<c:if test="${svyComptResult.routeType eq routeTypeList.codeNm}">selected="selected"</c:if>><c:out value="${routeTypeList.codeNm}"/></option> --%>
<%-- 								</c:forEach> --%>
<%--							</select> --%>
<%-- 						</td> --%>
						<th>조사구분</th>
						<td colspan="2">
							<select id="frdRnk" name="frdRnk" title="조사구분">
								<option value=""<c:if test="${empty svyComptResult.frdRnk || svyComptResult.frdRnk == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="frdRnkList" items="${frdRnkList}" varStatus="status">
								<option value="<c:out value="${frdRnkList.codeNm}"/>"<c:if test="${svyComptResult.frdRnk eq frdRnkList.codeNm}">selected="selected"</c:if>><c:out value="${frdRnkList.codeNm}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>속칭</th>
						<td>
							<input type="text" name="commonly" value="<c:out value="${svyComptResult.commonly}"/>"/>
						</td>
						<th>예정거리(km)</th>
						<td>
							<input type="text" name="schdst" class="wd30" value="<c:out value="${svyComptResult.schdst}"/>" />km
						</td>
						<th>임도연장(km)</th>
						<td>
							<input type="text" name="frdExtns" class="wd30" value="<c:out value="${svyComptResult.frdExtns}"/>" />km
						</td>
					</tr>
					<tr>
						<th colspan="6">GPS좌표</th>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lat" /></th><!-- 시점위도 -->
						<td colspan="2">
							<input type="text" name="bpyD" value="<c:out value="${svyComptResult.bpyD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="bpyM" value="<c:out value="${svyComptResult.bpyM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="bpyS" value="<c:out value="${svyComptResult.bpyS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
							<input type="hidden" name="bpy" value=""/>
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lon" /></th><!-- 시점경도 -->
						<td colspan="2">
							<input type="text" name="bpxD" value="<c:out value="${svyComptResult.bpxD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="bpxM" value="<c:out value="${svyComptResult.bpxM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="bpxS" value="<c:out value="${svyComptResult.bpxS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
							<input type="hidden" name="bpx" value=""/>
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />1</th><!-- 종점위도1 -->
						<td colspan="2">
							<input type="text" name="epy1D" value="<c:out value="${svyComptResult.epyD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="epy1M" value="<c:out value="${svyComptResult.epyM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="epy1S" value="<c:out value="${svyComptResult.epyS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
							<input type="hidden" name="epy" value=""/>
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />1</th><!-- 종점경도1 -->
						<td colspan="2">
							<input type="text" name="epx1D" value="<c:out value="${svyComptResult.epxD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="epx1M" value="<c:out value="${svyComptResult.epxM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="epx1S" value="<c:out value="${svyComptResult.epxS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
							<input type="hidden" name="epx" value=""/>
						</td>
					</tr>
				</table>
				<div id="map-div" class="map-div on">
					<div class="ol-custom-control ol-basemap-control z-idx99" id="toggle-btn">
						<button type="button" class="btn-map-selector" title="일반지도" value="base">일반지도</button>
						<button type="button" class="btn-map-selector" title="항공지도" value="satellite">항공지도</button>
					</div>
					<div class="hybrid-check z-idx99">
						<input type="checkbox" value="hybrid" id="hybrid"> <label for="hybrid">하이브리드</label>
					</div>
					<div id="map" class="map">
						<div class="frd-legend-div">
					        <div class="frdTitle">&lt;범 례&gt;</div>
					        <div class="frdLine" id="frdLine01">
					            <input type="checkbox" name="frdLne" value="editLayer01" checked="checked">
					            <img src="/images/main/frd_legend01.png">            
					        </div>
					        <div class="frdLine" id="frdLine02">
					            <input type="checkbox" name="frdLne" value="editLayer02" checked="checked">
					            <img src="/images/main/frd_legend02.png">            
					        </div>
					        <div class="frdLine" id="frdLine03">
					            <input type="checkbox" name="frdLne" value="editLayer03" checked="checked">
					            <img src="/images/main/frd_legend03.png">            
					        </div>
					        <div class="frdLine" id="frdLine04">
					            <input type="checkbox" name="frdLne" value="editLayer04" checked="checked">
					            <img src="/images/main/frd_legend04.png">            
					        </div>
					        <div class="frdLine" id="frdLine05">
					            <input type="checkbox" name="frdLne" value="editLayer05" checked="checked">
					            <img src="/images/main/frd_legend05.png">            
					        </div>
					    </div>
					</div>
				</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="add-btn" onclick="javascript:fncAddStripLandInsertView('<c:out value="${svyComptResult.sldId}"/>', '<c:out value="${svyComptResult.svyId}"/>', '<c:out value="${svyComptResult.mstId}"/>', '<c:out value="${svyComptResult.frdType}"/>', '<c:out value="${svyComptResult.compentauth}"/>'); return false;">SHP파일 추가등록</button>
					</div>
				</div>
				<br>
				<div class="mainMenu">□ 대상지 조사정보 &nbsp;</div>
				<div id="svyDetailTable">
						<input type="hidden" name="frdLneCntPntWkt" value="<c:out value="${frdMap.frdLneCntPnt}"/>"/>
						<input type="hidden" name="frdExstnLneWkt" value="<c:out value="${frdMap.frdExstnLne}"/>"/>
						<input type="hidden" name="frdModLne" value="<c:out value="${frdMap.frdModLne}"/>"/>
						<input type="hidden" name="frdRvwLne1" value="<c:out value="${frdMap.frdRvwLne1}"/>"/>
						<input type="hidden" name="frdRvwLne2" value="<c:out value="${frdMap.frdRvwLne2}"/>"/>
						<input type="hidden" name="frdRvwLne3" value="<c:out value="${frdMap.frdRvwLne3}"/>"/>
						<div class="mainMenu">1. 일반현황 
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('safeFct','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('safeFct','add'); return false;">추가</button>
						</div>
						<table id="safeFctTable">
							<colgroup>
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									보호시설
									<input type="hidden" name="safeFctList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th>보호시설</th>
								<td class="center">
									<input type="text" name="safeFct" value="<c:out value="${svyComptResult.safeFct}"/>" readonly="readonly"/>
								</td>
								<th>전답</th>
								<td>
									<select name="field">
										<option value="">${inputSelect }</option>
										<option value="유" <c:if test="${svyComptResult.field eq '유'}">selected=selected</c:if>>유</option>
										<option value="무" <c:if test="${svyComptResult.field eq '무'}">selected=selected</c:if>>무</option>
									</select>
								</td>
								<th>접근성</th>
								<td>
									<select name="acsbl">
										<option value="">${inputSelect }</option>
										<option value="상" <c:if test="${svyComptResult.acsbl eq '상'}">selected=selected</c:if>>상</option>
										<option value="중" <c:if test="${svyComptResult.acsbl eq '중'}">selected=selected</c:if>>중</option>
										<option value="하" <c:if test="${svyComptResult.acsbl eq '하'}">selected=selected</c:if>>하</option>
									</select>
								</td>
							</tr>
							<c:forEach var="item" items="${safeFctList}" varStatus="status">
								<tr class="safeFctList${status.count} checkPart">
									<th colspan="6"><c:out value="${status.count}"/>번 보호시설</th>
								</tr>
								<tr class="safeFctList${status.count}">
									<th>유형</th>
									<td class="center">
										<select name="safeFctType${status.count}">
											<option value="">${inputSelect }</option>
											<option value="민가" <c:if test="${item.type eq '민가'}">selected=selected</c:if>>민가</option>
											<option value="종교시설" <c:if test="${item.type eq '종교시설'}">selected=selected</c:if>>종교시설</option>
											<option value="농경지" <c:if test="${item.type eq '농경지'}">selected=selected</c:if>>농경지</option>
										</select>
									</td>
									<th>위도</th>
									<td>
										<input type="text" name="safeFctLatD${status.count}" value="<c:out value="${item.latD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
										<input type="text" name="safeFctLatM${status.count}" value="<c:out value="${item.latM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
										<input type="text" name="safeFctLatS${status.count}" value="<c:out value="${item.latS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도</th>
									<td>
										<input type="text" name="safeFctLonD${status.count}" value="<c:out value="${item.lonD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
										<input type="text" name="safeFctLonM${status.count}" value="<c:out value="${item.lonM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
										<input type="text" name="safeFctLonS${status.count}" value="<c:out value="${item.lonS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 종단기울기
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('lonSlope','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('lonSlope','add'); return false;">추가</button>
						</div>
						<table id="lonSlopeTable">
							<colgroup>
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									종단기울기 전체 통계
									<input type="hidden" name="lonSlopeList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th>최소(%)</th>
								<td><input type="text" name="lonSlopeMin" class="wd30 center" value="<c:out value="${svyComptResult.lonSlopeMin}"/>" readonly="readonly"/>%</td>
								<th>최대(%)</th>
								<td><input type="text" name="lonSlopeMax" class="wd30 center" value="<c:out value="${svyComptResult.lonSlopeMax}"/>" readonly="readonly"/>%</td>
								<th>평균(%)</th>
								<td><input type="text" name="lonSlopeAvg" class="wd30 center" value="<c:out value="${svyComptResult.lonSlopeAvg}"/>" readonly="readonly"/>%</td>
							</tr>
							<c:forEach var="item" items="${lonSlopeList}" varStatus="slopeStatus">
								<tr class="lonSlopeList${slopeStatus.count} checkPart">
									<th colspan="6"><c:out value="${slopeStatus.count}"/>번 종단기울기</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr lonSlopeList${slopeStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 종점기울기 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													<input type="hidden" name="lonSlopePhoto${slopeStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center lonSlopeList${slopeStatus.count}">
										<td colspan="6">
											등록된 사진이 없습니다.
										</td>
									</tr>
								</c:if>
								<tr class="lonSlopeList${slopeStatus.count}">
									<th>위도1</th>
									<td>
										<input type="text" name="lonSlopeLatD1_${slopeStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
										<input type="text" name="lonSlopeLatM1_${slopeStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
										<input type="text" name="lonSlopeLatS1_${slopeStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도1</th>
									<td>
										<input type="text" name="lonSlopeLonD1_${slopeStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
										<input type="text" name="lonSlopeLonM1_${slopeStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
										<input type="text" name="lonSlopeLonS1_${slopeStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
									<th rowspan="2">기울기(%)</th>
									<td rowspan="2" class="lonSlopeCalcArea center">
										<input type="number" name="lonSlope${slopeStatus.count}" class="wd30" value="<c:out value="${item.slope}"/>" onchange="fnCalcValue('lonSlope')" />%
									</td>
								</tr>
								<tr class="lonSlopeList${slopeStatus.count}">
									<th>위도2</th>
									<td>
										<input type="text" name="lonSlopeLatD2_${slopeStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
										<input type="text" name="lonSlopeLatM2_${slopeStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
										<input type="text" name="lonSlopeLatS2_${slopeStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도2</th>
									<td>
										<input type="text" name="lonSlopeLonD2_${slopeStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
										<input type="text" name="lonSlopeLonM2_${slopeStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
										<input type="text" name="lonSlopeLonS2_${slopeStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 산지경사
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('mtSlope','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('mtSlope','add'); return false;">추가</button>
						</div>
						<table id="mtSlopeTable">
							<colgroup>
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									산지경사 전체 통계
									<input type="hidden" name="mtSlopeList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th>최소(°)</th>
								<td><input type="text" name="mtSlopeMin" class="wd30 center" value="<c:out value="${svyComptResult.mtSlopeMin}"/>" readonly="readonly"/>°</td>
								<th>최대(°)</th>
								<td><input type="text" name="mtSlopeMax" class="wd30 center" value="<c:out value="${svyComptResult.mtSlopeMax}"/>" readonly="readonly"/>°</td>
								<th>평균(°)</th>
								<td><input type="text" name="mtSlopeAvg" class="wd30 center" value="<c:out value="${svyComptResult.mtSlopeAvg}"/>" readonly="readonly"/>°</td>
							</tr>
							<c:forEach var="item" items="${mtSlopeList}" varStatus="mtSlopeStatus">
								<tr class="mtSlopeList${mtSlopeStatus.count} checkPart">
									<th colspan="6"><c:out value="${mtSlopeStatus.count}"/>번 산지경사</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr mtSlopeList${mtSlopeStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 산지경사 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="mtSlopePhoto${mtSlopeStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center mtSlopeList${mtSlopeStatus.count}">
										<td colspan="6">
											등록된 사진이 없습니다.
										</td>
									</tr>
								</c:if>
								<tr class="mtSlopeList${mtSlopeStatus.count}">
									<th>위도1</th>
									<td>
										<input type="text" name="mtSlopeLatD1_${mtSlopeStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="mtSlopeLatM1_${mtSlopeStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="mtSlopeLatS1_${mtSlopeStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도1</th>
									<td>
										<input type="text" name="mtSlopeLonD1_${mtSlopeStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="mtSlopeLonM1_${mtSlopeStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="mtSlopeLonS1_${mtSlopeStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
									<th rowspan="2">경사(°)</th>
									<td rowspan="2" class="mtSlopeCalcArea center">
										<input type="number" name="mtSlope${mtSlopeStatus.count}" class="wd30" value="<c:out value="${item.slope}"/>" onchange="fnCalcValue('mtSlope')" />%
									</td>
								</tr>
								<tr class="mtSlopeList${mtSlopeStatus.count}">
									<th>위도2</th>
									<td>
										<input type="text" name="mtSlopeLatD2_${mtSlopeStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="mtSlopeLatM2_${mtSlopeStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="mtSlopeLatS2_${mtSlopeStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도2</th>
									<td>
										<input type="text" name="mtSlopeLonD2_${mtSlopeStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="mtSlopeLonM2_${mtSlopeStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="mtSlopeLonS2_${mtSlopeStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 암반노출
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('rockExpos','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('rockExpos','add'); return false;">추가</button>
						</div>
						<table id="rockExposTable">
							<colgroup>
					            <col width="13.6%;">
					            <col width="19.6%;">
					            <col width="13.6%;">
					            <col width="19.6%;">
					            <col width="13.6%;">
					            <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									암반노출 전체 통계
									<input type="hidden" name="rockExposList" value=""/>
								</th>
							</tr>
							<tr class="center">
<%--								<th>최소(°)</th> --%>
<%--								<td><input type="text" name="rockExposMin" class="wd30 center" value="<c:out value="${svyComptResult.rockExposMin}"/>" readonly="readonly"/>°</td> --%>
<%--								<th>최대(°)</th> --%>
<%--								<td><input type="text" name="rockExposMax" class="wd30 center" value="<c:out value="${svyComptResult.rockExposMax}"/>" readonly="readonly"/>°</td> --%>
<%--								<th>평균(°)</th> --%>
<%--								<td><input type="text" name="rockExposAvg" class="wd30 center" value="<c:out value="${svyComptResult.rockExposAvg}"/>" readonly="readonly"/>°</td> --%>
								<th colspan="3">암반 전체 노출(m)</th>
								<td colspan="3"><input type="text" name="rockExposSum" class="wd30 center" value="<fmt:formatNumber value="${svyComptResult.rockExposSum}" pattern="0.00" />" readonly="readonly"/>m</td>
								
							</tr>
							<c:forEach var="item" items="${rockExposList}" varStatus="rockExposStatus">
								<tr class="rockExposList${rockExposStatus.count} checkPart">
									<th colspan="6"><c:out value="${rockExposStatus.count}"/>번 암반노출</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr rockExposList${rockExposStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 암반노출 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="rockExposPhoto${rockExposStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center rockExposList${rockExposStatus.count}">
										<td colspan="6">
					                        등록된 사진이 없습니다.
					                    </td>
									</tr>
								</c:if>
								<tr class="rockExposList${rockExposStatus.count}">
									<th>위도1</th>
									<td>
										<input type="text" name="rockExposLatD1_${rockExposStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="rockExposLatM1_${rockExposStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="rockExposLatS1_${rockExposStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도1</th>
									<td><c:out value="${item.lon1}"/>
										<input type="text" name="rockExposLonD1_${rockExposStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
                   						<input type="text" name="rockExposLonM1_${rockExposStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
                   						<input type="text" name="rockExposLonS1_${rockExposStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
									<th rowspan="2">암반노출(m)</th>
									<td rowspan="2" class="rockExposCalcArea center">
										<c:set var="rockExposIsNumber">${item.rockExpos}</c:set>
										<input type="number" name="rockExpos${rockExposStatus.count}" class="wd30" value="<c:out value="${item.rockExpos}"/>" onchange="fnCalcValue('rockExpos')" />m
									</td>
								</tr>
								<tr class="rockExposList${rockExposStatus.count}">
									<th>위도2</th>
									<td>
										<input type="text" name="rockExposLatD2_${rockExposStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
                   						<input type="text" name="rockExposLatM2_${rockExposStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
                   						<input type="text" name="rockExposLatS2_${rockExposStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도2</th>
									<td>
										<input type="text" name="rockExposLonD2_${rockExposStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="rockExposLonM2_${rockExposStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="rockExposLonS2_${rockExposStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 조림지
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('afrst','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('afrst','add'); return false;">추가</button>
						</div>
						<table id="afrstTable">
							<colgroup>
								<col width="13.6%;">
					            <col width="19.6%;">
					            <col width="13.6%;">
					            <col width="19.6%;">
					            <col width="13.6%;">
					            <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									조림지 전체 통계
									<input type="hidden" name="afrstList" value=""/>
								</th>
							</tr>
							<tr>
								<th colspan="3">유 / 무</th>
								<td colspan="3" class="center">
									<input type="text" name="afrstAt" class="wd30" value="<c:out value="${svyComptResult.afrstAt}"/>" readonly="readonly"/>
								</td>
							</tr>
							<c:forEach var="item" items="${afrstList}" varStatus="afrstStatus">
								<tr class="afrstList${afrstStatus.count} checkPart">
									<th colspan="6"><c:out value="${afrstStatus.count}"/>번 조림지</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr afrstList${afrstStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="afrstPhoto${afrstStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center afrstList${afrstStatus.count}">
										<td colspan="6">
											등록된 사진이 없습니다.
										</td>
									</tr>
								</c:if>
								<tr class="afrstList${afrstStatus.count} afrstCalcArea">
									<th>위도1</th>
									<td colspan="2">
										<input type="text" name="afrstLatD1_${afrstStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="afrstLatM1_${afrstStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="afrstLatS1_${afrstStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도1</th>
									<td colspan="2">
										<input type="text" name="afrstLonD1_${afrstStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="afrstLonM1_${afrstStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="afrstLonS1_${afrstStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="afrstList${afrstStatus.count}">
									<th>위도2</th>
									<td colspan="2">
										<input type="text" name="afrstLatD2_${afrstStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="afrstLatM2_${afrstStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="afrstLatS2_${afrstStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도2</th>
									<td colspan="2">
										<input type="text" name="afrstLonD2_${afrstStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
					                    <input type="text" name="afrstLonM2_${afrstStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
					                    <input type="text" name="afrstLonS2_${afrstStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="afrstList${afrstStatus.count}">
									<th>조림지</th>
									<td colspan="5">
										<input type="text" name="afrst_${afrstStatus.count}" value="<c:out value="${item.afrst}"/>">
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 벌채지
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('cutting','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('cutting','add'); return false;">추가</button>
						</div>
						<table id="cuttingTable">
						    <colgroup>
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						    </colgroup>
						    <tr>
						        <th colspan="6">
						        	벌채지 전체 통계
						        	<input type="hidden" name="cuttingList" value=""/>
						        </th>
						    </tr>
						    <tr>
						        <th colspan="3">유 / 무</th>
						        <td colspan="3" class="center">
						            <input type="text" name="cuttingAt" class="wd30" value="<c:out value="${svyComptResult.cuttingAt }"/>" readonly="readonly"/>
						        </td>
						    </tr>
						    <c:forEach var="item" items="${cuttingList}" varStatus="cuttingStatus">
						        <tr class="cuttingList${cuttingStatus.count} checkPart">
						            <th colspan="6"><c:out value="${cuttingStatus.count}"/>번 벌채지</th>
						        </tr>
						        <c:if test="${item.photo != null and item.photo != '[]'}">
						            <tr class="photoTr cuttingList${cuttingStatus.count}">
						                <td colspan="6">
						                    <div>
						                         <c:forEach items="${item.photo}" var="photo" varStatus="status">
						                             <img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 벌채지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						                             <input type="hidden" name="cuttingPhoto${cuttingStatus.count}_${status.count}" value="${photo}"/>
						                        </c:forEach>
						                    </div>
						                </td>
						            </tr>
						        </c:if>
						        <c:if test="${item.photo == null or item.photo == '[]'}">
						            <tr class="center cuttingList${cuttingStatus.count}">
						                <td colspan="6">
						                    	등록된 사진이 없습니다.
						                </td>
						            </tr>
						        </c:if>
						        <tr class="cuttingList${cuttingStatus.count}">
						            <th>위도1</th>
						            <td colspan="2">
						                <input type="text" name="cuttingLatD1_${cuttingStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="cuttingLatM1_${cuttingStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="cuttingLatS1_${cuttingStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도1</th>
						            <td colspan="2">
						                <input type="text" name="cuttingLonD1_${cuttingStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="cuttingLonM1_${cuttingStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="cuttingLonS1_${cuttingStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
						        </tr>
						        <tr class="cuttingList${cuttingStatus.count}">
						            <th>위도2</th>
						            <td colspan="2">
						                <input type="text" name="cuttingLatD2_${cuttingStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="cuttingLatM2_${cuttingStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="cuttingLatS2_${cuttingStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도2</th>
						            <td colspan="2">
						                <input type="text" name="cuttingLonD2_${cuttingStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="cuttingLonM2_${cuttingStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="cuttingLonS2_${cuttingStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
						        </tr>
						        <tr class="cuttingList${cuttingStatus.count}">
									<th>벌채지</th>
									<td colspan="5">
										<input type="text" name="cutting_${cuttingStatus.count}" value="<c:out value="${item.cutting}"/>">
									</td>
								</tr>
						    </c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 석력지
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('stmi','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('stmi','add'); return false;">추가</button>
						</div>
						<table id="stmiTable">
						    <colgroup>
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						    </colgroup>
						    <tr>
						        <th colspan="6">
						        	석력지 전체 통계
						        	<input type="hidden" name="stmiList" value=""/>
						        </th>
						    </tr>
						    <tr>
						        <th colspan="3">유 / 무</th>
						        <td colspan="3" class="center">
						            <input type="text" name="stmiAt" class="wd30" value="<c:out value="${stmiTotalAt}"/>" readonly="readonly"/>
						        </td>
						    </tr>
						    <c:forEach var="item" items="${stmiList}" varStatus="stmiStatus">
						        <tr class="stmiList${stmiStatus.count} checkPart">
						            <th colspan="6"><c:out value="${stmiStatus.count}"/>번 석력지</th>
						        </tr>
						        <c:if test="${item.photo != null and item.photo != '[]'}">
						            <tr class="photoTr stmiList${stmiStatus.count}">
						                <td colspan="6">
						                    <div>
						                         <c:forEach items="${item.photo}" var="photo" varStatus="status">
						                             <img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 석력지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						                             <input type="hidden" name="stmiPhoto${stmiStatus.count}_${status.count}" value="${photo}"/>
						                        </c:forEach>
						                    </div>
						                </td>
						            </tr>
						        </c:if>
						        <c:if test="${item.photo == null or item.photo == '[]'}">
						            <tr class="center stmiList${stmiStatus.count}">
						                <td colspan="6">
						                    	등록된 사진이 없습니다.
						                </td>
						            </tr>
						        </c:if>
						        <tr class="stmiList${stmiStatus.count}">
						            <th>위도1</th>
						            <td colspan="2">
						                <input type="text" name="stmiLatD1_${stmiStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="stmiLatM1_${stmiStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="stmiLatS1_${stmiStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도1</th>
						            <td colspan="2">
						                <input type="text" name="stmiLonD1_${stmiStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="stmiLonM1_${stmiStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="stmiLonS1_${stmiStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
						        </tr>
						        <tr class="stmiList${stmiStatus.count}">
						            <th>위도2</th>
						            <td colspan="2">
						                <input type="text" name="stmiLatD2_${stmiStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="stmiLatM2_${stmiStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="stmiLatS2_${stmiStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도2</th>
						            <td colspan="2">
						                <input type="text" name="stmiLonD2_${stmiStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="stmiLonM2_${stmiStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="stmiLonS2_${stmiStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
						        </tr>
						        <tr class="stmiList${stmiStatus.count}">
									<th>석력지</th>
									<td colspan="5">
										<input type="text" name="stmi_${stmiStatus.count}" value="<c:out value="${item.stmi}"/>">
									</td>
								</tr>
						    </c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 계류종류 및 현황
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('mrng','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('mrng','add'); return false;">추가</button>
						</div>
						<table id="mrngTable">
							<colgroup>
								<col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									계류종류 및 현황 전체 통계
									<input type="hidden" name="mrngKind" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th>대계류</th>
								<td>
									<input type="text" name="bigMrngTotal1" class="wd15" value="${mrngTotalMap.bigMrng1}" readonly="readonly" />/<input type="text" name="bigMrngTotal2" class="wd15" value="${mrngTotalMap.bigMrng2}" readonly="readonly" />/<input type="text" name="bigMrngTotal3" class="wd15" value="${mrngTotalMap.bigMrng3}" readonly="readonly" />
									<input type="hidden" name="mrngBig" value=""/>
								</td>
								<th>중계류</th>
								<td>
									<input type="text" name="middleMrngTotal1" class="wd15" value="${mrngTotalMap.middleMrng1}" readonly="readonly" />/<input type="text" name="middleMrngTotal2" class="wd15" value="${mrngTotalMap.middleMrng2}" readonly="readonly" />/<input type="text" name="middleMrngTotal3" class="wd15" value="${mrngTotalMap.middleMrng3}" readonly="readonly" />
								</td>
								<th>소계류</th>
								<td>
									<input type="text" name="smallMrngTotal1" class="wd15" value="${mrngTotalMap.smallMrng1}" readonly="readonly" />/<input type="text" name="smallMrngTotal2" class="wd15" value="${mrngTotalMap.smallMrng2}" readonly="readonly" />/<input type="text" name="smallMrngTotal3" class="wd15" value="${mrngTotalMap.smallMrng3}" readonly="readonly" />
								</td>
							</tr>
							<c:forEach var="item" items="${mrngKindList}" varStatus="mrngStatus">
								<tr class="mrngList${mrngStatus.count} checkPart">
									<th colspan="6"><c:out value="${mrngStatus.count}"/>번 계류종류 및 현황</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr mrngList${mrngStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="mrngPhoto${mrngStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center mrngList${mrngStatus.count}">
										<td colspan="6">
						                  	  등록된 사진이 없습니다.
						                </td>
									</tr>
								</c:if>
								<tr class="mrngList${mrngStatus.count}">
									<th>위도</th>
						            <td colspan="2">
						                <input type="text" name="mrngLatD1_${mrngStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="mrngLatM1_${mrngStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="mrngLatS1_${mrngStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도</th>
						            <td colspan="2">
						                <input type="text" name="mrngLonD1_${mrngStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="mrngLonM1_${mrngStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="mrngLonS1_${mrngStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
								</tr>
								<tr class="mrngList${mrngStatus.count}">
									<th>대분류</th>
									<td colspan="2" class="mrngCalcArea">
										<select name="bigMrng${mrngStatus.count}" onchange="fnCalcValue('mrng')">
											<option value="">${inputSelect }</option>	
											<option value="대계류" <c:if test="${item.bigMrng eq '대계류'}">selected=selected</c:if>>대계류</option>
											<option value="중계류" <c:if test="${item.bigMrng eq '중계류'}">selected=selected</c:if>>중계류</option>
											<option value="소계류" <c:if test="${item.bigMrng eq '소계류'}">selected=selected</c:if>>소계류</option>
										</select>
									</td>
									<th>소분류</th>
									<td colspan="2" class="mrngCalcArea">
										<select name="smallMrng${mrngStatus.count}" onchange="fnCalcValue('mrng')">
											<option value="">${inputSelect }</option>	
											<option value="상시천" <c:if test="${item.smallMrng eq '상시천'}">selected=selected</c:if>>상시천</option>
											<option value="건천" <c:if test="${item.smallMrng eq '건천'}">selected=selected</c:if>>건천</option>
										</select>
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 습지
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('wetLand','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('wetLand','add'); return false;">추가</button>
						</div>
						<table id="wetLandTable">
							<colgroup>
								<col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									습지 전체 통계
									<input type="hidden" name="wetLandList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th colspan="3">유 / 무</th>
								<td colspan="3" class="center">
									<input type="text" name="wetLandAt" class="wd30" value="<c:out value="${svyComptResult.wetLandAt}"/>" readonly="readonly"/>
								</td>
							</tr>
							<c:forEach var="item" items="${wetLandList}" varStatus="wetLandStatus">
								<tr class="wetLandList${wetLandStatus.count} checkPart">
									<th colspan="6"><c:out value="${wetLandStatus.count}"/>번 습지</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr wetLandList${wetLandStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="wetLandPhoto${wetLandStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center wetLandList${wetLandStatus.count}">
										<td colspan="6">
						                   	 등록된 사진이 없습니다.
						                </td>
									</tr>
								</c:if>
								<tr class="wetLandList${wetLandStatus.count}">
									<th>위도1</th>
									<td colspan="2">
										<input type="text" name="wetLandLatD1_${wetLandStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="wetLandLatM1_${wetLandStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="wetLandLatS1_${wetLandStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도1</th>
									<td colspan="2">
										<input type="text" name="wetLandLonD1_${wetLandStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="wetLandLonM1_${wetLandStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="wetLandLonS1_${wetLandStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="wetLandList${wetLandStatus.count}">
									<th>위도2</th>
									<td colspan="2">
										<input type="text" name="wetLandLatD2_${wetLandStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="wetLandLatM2_${wetLandStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="wetLandLatS2_${wetLandStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도2</th>
									<td colspan="2">
										<input type="text" name="wetLandLonD2_${wetLandStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="wetLandLonM2_${wetLandStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="wetLandLonS2_${wetLandStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="wetLandList${wetLandStatus.count}">
									<th>습지</th>
									<td colspan="5">
										<input type="text" name="wetLand_${wetLandStatus.count}" value="<c:out value="${item.wetLand}"/>">
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 묘지
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('cmtry','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('cmtry','add'); return false;">추가</button>
						</div>
						<table id="cmtryTable">
							<colgroup>
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
								<col width="13.6%;">
								<col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									묘지 전체 통계
									<input type="hidden" name="cmtryList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th>묘지 개수</th>
								<td class="center">
									<input type="text" name="cmtryCnt" value="<c:out value="${cmtryTotalMap.cmtryCnt}"/>" readonly="readonly"/>
								</td>
								<th>묘지 위치</th>
								<td>
									<input type="text" name="cmtryLoc1" class="wd15" value="<c:out value="${cmtryTotalMap.cmtryLoc1}"/>" readonly="readonly"/>/<input type="text" name="cmtryLoc2" class="wd15" value="<c:out value="${cmtryTotalMap.cmtryLoc2}"/>" readonly="readonly"/>
								</td>
								<th>묘지 관리</th>
								<td>
									<input type="text" name="cmtryMng1" class="wd15" value="<c:out value="${cmtryTotalMap.cmtryMng1}"/>" readonly="readonly"/>/<input type="text" name="cmtryMng2" class="wd15" value="<c:out value="${cmtryTotalMap.cmtryMng2}"/>" readonly="readonly"/>
								</td>
							</tr>
							<c:forEach var="item" items="${cmtryList}" varStatus="cmtryStatus">
								<tr class="cmtryList${cmtryStatus.count} checkPart">
									<th colspan="6" class="cmtryList${cmtryStatus.count}"><c:out value="${cmtryStatus.count}"/>번 묘지</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr cmtryList${cmtryStatus.count}">
										<td colspan="6" class="cmtryList${cmtryStatus.count}">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 묘지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="cmtryPhoto${cmtryStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center cmtryList${cmtryStatus.count}">
										<td colspan="6">
					                    	등록된 사진이 없습니다.
					                	</td>
									</tr>
								</c:if>
								<tr class="cmtryList${cmtryStatus.count}">
									<th>위도</th>
									<td colspan="2">
										<input type="text" name="cmtryLatD1_${cmtryStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="cmtryLatM1_${cmtryStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="cmtryLatS1_${cmtryStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도</th>
									<td colspan="2">
										<input type="text" name="cmtryLonD1_${cmtryStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="cmtryLonM1_${cmtryStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="cmtryLonS1_${cmtryStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="cmtryList${cmtryStatus.count} center">
									<th>묘지 위치</th>
									<td colspan="2" class="cmtryCalcArea">
										<select name="cmtryLoc${cmtryStatus.count}" onchange="fnCalcValue('cmtry')">
						                    <option value="">${inputSelect }</option>
						                    <option value="노선 상부" <c:if test="${item.cmtryLoc eq '노선 상부'}">selected=selected</c:if>>노선 상부</option>
						                    <option value="노선 하부" <c:if test="${item.cmtryLoc eq '노선 하부'}">selected=selected</c:if>>노선 하부</option>
						                </select>
									</td>
									<th>묘지 관리</th>
									<td colspan="2" class="cmtryCalcArea">
										<select name="cmtryMng${cmtryStatus.count}" onchange="fnCalcValue('cmtry')">
						                    <option value="">${inputSelect }</option>
						                    <option value="관리O" <c:if test="${item.cmtryMng eq '관리O'}">selected=selected</c:if>>관리O</option>
						                    <option value="관리X" <c:if test="${item.cmtryMng eq '관리X'}">selected=selected</c:if>>관리X</option>
						                </select>
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 용출수
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('eltnWater','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('eltnWater','add'); return false;">추가</button>
						</div>
						<table id="eltnWaterTable">
							<colgroup>
								<col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									용출수 전체 통계
									<input type="hidden" name="eltnWaterList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th colspan="3">유 / 무</th>
								<td colspan="3" class="center">
									<input type="text" name="eltnWaterAt" class="wd30" value="<c:out value="${svyComptResult.eltnWaterAt }"/>" readonly="readonly"/>
								</td>
							</tr>
							<c:forEach var="item" items="${eltnWaterList}" varStatus="eltnWaterStatus">
								<tr class="eltnWaterList${eltnWaterStatus.count} checkPart">
									<th colspan="6"><c:out value="${eltnWaterStatus.count}"/>번 용출수</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr eltnWaterList${eltnWaterStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 용출수 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="eltnWaterPhoto${eltnWaterStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center eltnWaterList${eltnWaterStatus.count}">
						                <td colspan="6">
						                    	등록된 사진이 없습니다.
						                </td>
						            </tr>
								</c:if>
								<tr class="eltnWaterList${eltnWaterStatus.count}">
									<th>위도</th>
									<td colspan="2">
										<input type="text" name="eltnWaterLatD1_${eltnWaterStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="eltnWaterLatM1_${eltnWaterStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="eltnWaterLatS1_${eltnWaterStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도</th>
									<td colspan="2">
										<input type="text" name="eltnWaterLonD1_${eltnWaterStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="eltnWaterLonM1_${eltnWaterStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="eltnWaterLonS1_${eltnWaterStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="eltnWaterList${eltnWaterStatus.count}">
									<th>용출수</th>
									<td colspan="5">
										<input type="text" name="eltnWater_${eltnWaterStatus.count}" value="<c:out value="${item.eltnWater}"/>">
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 연약지반
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('sofrtGrnd','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('sofrtGrnd','add'); return false;">추가</button>
						</div>
						<table id="sofrtGrndTable">
							<colgroup>
								<col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									연약지반 전체 통계
									<input type="hidden" name="sofrtGrndList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th colspan="3">유 / 무</th>
								<td colspan="3" class="center">
									<input type="text" name="sofrtGrndAt" class="wd30" value="<c:out value="${svyComptResult.sofrtGrndAt}"/>" readonly="readonly"/>
								</td>
							</tr>
							<c:forEach var="item" items="${sofrtGrndList}" varStatus="sofrtGrndStatus">
								<tr class="sofrtGrndList${sofrtGrndStatus.count} checkPart">
									<th colspan="6"><c:out value="${sofrtGrndStatus.count}"/>번 연약지반</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr sofrtGrndList${sofrtGrndStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 연약지반 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="sofrtGrndPhoto${sofrtGrndStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="sofrtGrndList${sofrtGrndStatus.count} center">
										<td colspan="6">
						                    	등록된 사진이 없습니다.
						                </td>
									</tr>
								</c:if>
								<tr class="sofrtGrndList${sofrtGrndStatus.count}">
									<th>위도1</th>
									<td colspan="2">
										<input type="text" name="sofrtGrndLatD1_${sofrtGrndStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="sofrtGrndLatM1_${sofrtGrndStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="sofrtGrndLatS1_${sofrtGrndStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도1</th>
									<td colspan="2">
						                <input type="text" name="sofrtGrndLonD1_${sofrtGrndStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="sofrtGrndLonM1_${sofrtGrndStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="sofrtGrndLonS1_${sofrtGrndStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="sofrtGrndList${sofrtGrndStatus.count}">
									<th>위도2</th>
									<td colspan="2">
						                <input type="text" name="sofrtGrndLatD2_${sofrtGrndStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="sofrtGrndLatM2_${sofrtGrndStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="sofrtGrndLatS2_${sofrtGrndStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도2</th>
									<td colspan="2">
						                <input type="text" name="sofrtGrndLonD2_${sofrtGrndStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="sofrtGrndLonM2_${sofrtGrndStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="sofrtGrndLonS2_${sofrtGrndStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="sofrtGrndList${sofrtGrndStatus.count}">
									<th>연약지반</th>
									<td colspan="5">
										<input type="text" name="sofrtGrnd_${sofrtGrndStatus.count}" value="<c:out value="${item.sofrtGrnd}"/>">
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 붕괴우려지역
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('clpsCnrn','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('clpsCnrn','add'); return false;">추가</button>
						</div>
						<table id="clpsCnrnTable">
							<colgroup>
								<col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
						        <col width="13.6%;">
						        <col width="19.6%;">
							</colgroup>
							<tr>
								<th colspan="6">
									붕괴우려지역 전체 통계
									<input type="hidden" name="clpsCnrnList" value=""/>
								</th>
							</tr>
							<tr class="center">
								<th>붕괴우려 사면</th>
								<td colspan="2">
									<input type="text" name="slope1" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.slope1}"/>" readonly="readonly"/>/
									<input type="text" name="slope2" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.slope2}"/>" readonly="readonly"/>/
									<input type="text" name="slope3" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.slope3}"/>" readonly="readonly"/>/
									<input type="text" name="slope4" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.slope4}"/>" readonly="readonly"/>/
									<input type="text" name="slope5" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.slope5}"/>" readonly="readonly"/>
									<input type="hidden" name="clpsCnrn" value=""/>
								</td>
								<th>붕괴우려 계류</th>
								<td colspan="2">
									<input type="text" name="mtTrnt1" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.mtTrnt1}"/>" readonly="readonly"/>/
									<input type="text" name="mtTrnt2" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.mtTrnt2}"/>" readonly="readonly"/>/
									<input type="text" name="mtTrnt3" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.mtTrnt3}"/>" readonly="readonly"/>/
									<input type="text" name="mtTrnt4" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.mtTrnt4}"/>" readonly="readonly"/>/
									<input type="text" name="mtTrnt5" class="wd15 center" value="<c:out value="${clpsCnrnTotalMap.mtTrnt5}"/>" readonly="readonly"/>
								</td>
							</tr>
							<c:forEach var="item" items="${clpsCnrnList}" varStatus="clpsCnrnStatus">
								<tr class="clpsCnrnList${clpsCnrnStatus.count} checkPart">
									<th colspan="6"><c:out value="${clpsCnrnStatus.count}"/>번 붕괴우려지역</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr clpsCnrnList${clpsCnrnStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 붕괴우려지역 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="clpsCnrnPhoto${clpsCnrnStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center clpsCnrnList${clpsCnrnStatus.count}">
										<td colspan="6">
						                    	등록된 사진이 없습니다.
						                </td>
									</tr>
								</c:if>
								<tr class="clpsCnrnList${clpsCnrnStatus.count}">
									<th>위도1</th>
						            <td>
						                <input type="text" name="clpsCnrnLatD1_${clpsCnrnStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="clpsCnrnLatM1_${clpsCnrnStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="clpsCnrnLatS1_${clpsCnrnStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도1</th>
						            <td>
						                <input type="text" name="clpsCnrnLonD1_${clpsCnrnStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="clpsCnrnLonM1_${clpsCnrnStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="clpsCnrnLonS1_${clpsCnrnStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
									<th>붕괴우려 대분류</th>
									<td class="clpsCnrnCalcArea">
										<select name="clpsCnrnBig${clpsCnrnStatus.count}" onchange="fnCalcValue('clpsCnrn')">
											<option value="">${inputSelect }</option>
						                    <option value="사면" <c:if test="${item.clpsCnrnBig eq '사면'}">selected=selected</c:if>>사면</option>
						                    <option value="계류" <c:if test="${item.clpsCnrnBig eq '계류'}">selected=selected</c:if>>계류</option>
										</select>
									</td>
								</tr>
								<tr class="clpsCnrnList${clpsCnrnStatus.count} clpsCnrnCalcArea">
									<th>위도2</th>
						            <td>
						                <input type="text" name="clpsCnrnLatD2_${clpsCnrnStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="clpsCnrnLatM2_${clpsCnrnStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="clpsCnrnLatS2_${clpsCnrnStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						            </td>
						            <th>경도2</th>
						            <td>
						                <input type="text" name="clpsCnrnLonD2_${clpsCnrnStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="clpsCnrnLonM2_${clpsCnrnStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="clpsCnrnLonS2_${clpsCnrnStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						            </td>
									<th>붕괴우려 소분류</th>
									<td class="clpsCnrnCalcArea">
										<select name="clpsCnrnSmall${clpsCnrnStatus.count}" onchange="fnCalcValue('clpsCnrn')">
											<option value="">${inputSelect }</option>
						                    <option value="침식" <c:if test="${item.clpsCnrnSmall eq '침식'}">selected=selected</c:if>>침식</option>
						                    <option value="붕괴" <c:if test="${item.clpsCnrnSmall eq '붕괴'}">selected=selected</c:if>>붕괴</option>
						                    <option value="포락" <c:if test="${item.clpsCnrnSmall eq '포락'}">selected=selected</c:if>>포락</option>
										</select>
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
						<div class="mainMenu">2. 산림 현장현황 > 주요수종(<span id="maintreekndCnt" style="font-weight: bold;"><c:out value="${svyComptResult.maintreekndCnt }"/></span>건)
							<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('maintreeknd','del'); return false;">삭제</button>
							<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('maintreeknd','add'); return false;">추가</button>
							<input type="hidden" name="maintreekndList" value=""/>
							<input type="hidden" name="maintreekndCnt" value=""/>
						</div>
						<table id="maintreekndTable">
							<colgroup>
								<col width="16.6%;">
						        <col width="16.6%;">
						        <col width="16.6%;">
						        <col width="16.6%;">
						        <col width="16.6%;">
						        <col width="16.6%;">
							</colgroup>
							<c:forEach var="item" items="${maintreekndList}" varStatus="maintreekndStatus">
								<tr class="maintreekndList${maintreekndStatus.count} checkPart">
									<th colspan="6"><c:out value="${maintreekndStatus.count}"/>번 주요수종</th>
								</tr>
								<c:if test="${item.photo != null and item.photo != '[]'}">
									<tr class="photoTr maintreekndList${maintreekndStatus.count}">
										<td colspan="6">
											<div>
											 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
											 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 주요수종 사진" onerror="this.remove ? this.remove() : this.removeNode();">
											 		<input type="hidden" name="maintreekndPhoto${maintreekndStatus.count}_${status.count}" value="${photo}"/>
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
								<c:if test="${item.photo == null or item.photo == '[]'}">
									<tr class="center maintreekndList${maintreekndStatus.count}">
										<td colspan="6">
						                    	등록된 사진이 없습니다.
						                </td>
									</tr>
								</c:if>
								<tr class="maintreekndList${maintreekndStatus.count}">
									<th>위도</th>
									<td colspan="2">
										<input type="text" name="maintreekndLatD1_${maintreekndStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="maintreekndLatM1_${maintreekndStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="maintreekndLatS1_${maintreekndStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
									</td>
									<th>경도</th>
									<td colspan="2">
						                <input type="text" name="maintreekndLonD1_${maintreekndStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						                <input type="text" name="maintreekndLonM1_${maintreekndStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						                <input type="text" name="maintreekndLonS1_${maintreekndStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
									</td>
								</tr>
								<tr class="maintreekndList${maintreekndStatus.count}">
									<th>주요수종</th>
									<td colspan="5" class="maintreekndCalcArea">
										<input type="text" name="maintreeknd${maintreekndStatus.count}" value="<c:out value="${item.maintreeknd }"/>">
									</td>
								</tr>
							</c:forEach>
							</table>
							<br>
							<div class="mainMenu">2. 산림 현장현황 > 주요식생(<span id="mainvegCnt" style="font-weight: bold;"><c:out value="${svyComptResult.mainvegCnt }"/></span>건)
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('mainveg','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('mainveg','add'); return false;">추가</button>
								<input type="hidden" name="mainvegList" value=""/>
								<input type="hidden" name="mainvegCnt" value=""/>
							</div>
							<table id="mainvegTable">
								<colgroup>
									<col width="16.6%;">
							        <col width="16.6%;">
							        <col width="16.6%;">
							        <col width="16.6%;">
							        <col width="16.6%;">
							        <col width="16.6%;">
								</colgroup>
								<c:forEach var="item" items="${mainvegList}" varStatus="mainvegStatus">
									<tr class="mainvegList${mainvegStatus.count} checkPart">
										<th colspan="6"><c:out value="${mainvegStatus.count}"/>번 주요식생</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr mainvegList${mainvegStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 주요식생 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="mainvegPhoto${mainvegStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<td colspan="6" class="center mainvegList${mainvegStatus.count}">
											등록된 사진이 없습니다.
						                </td>
									</c:if>
									<tr class="mainvegList${mainvegStatus.count}">
										<th>위도</th>
										<td colspan="2">
											<input type="text" name="mainvegLatD1_${mainvegStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="mainvegLatM1_${mainvegStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="mainvegLatS1_${mainvegStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도</th>
										<td colspan="2">
							                <input type="text" name="mainvegLonD1_${mainvegStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="mainvegLonM1_${mainvegStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="mainvegLonS1_${mainvegStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
									</tr>
									<tr class="mainvegList${mainvegStatus.count}">
										<th>주요식생</th>
										<td colspan="5" class="mainvegCalcArea">
											<input type="text" name="mainveg${mainvegStatus.count}" value="<c:out value="${item.mainveg }"/>">
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="mainMenu">2. 산림 현장현황 > 상수원 오염
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('wtrPltn','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('wtrPltn','add'); return false;">추가</button>
							</div>
							<table id="wtrPltnTable">
								<colgroup>
									<col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
								</colgroup>
								<tr>
									<th colspan="6">
										상수원 오염 전체 통계
										<input type="hidden" name="wtrPltnList" value=""/>
									</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3" class="center">
										<input type="text" name="wtrPltnAt" class="wd30" value="<c:out value="${svyComptResult.wtrPltnAt}"/>" readonly="readonly"/>
									</td>
								</tr>
								<c:forEach var="item" items="${wtrPltnList}" varStatus="wtrPltnStatus">
									<tr class="wtrPltnList${wtrPltnStatus.count} checkPart">
										<th colspan="6"><c:out value="${wtrPltnStatus.count}"/>번 상수원 오염</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr wtrPltnList${wtrPltnStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 상수원 오염 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="wtrPltnPhoto${wtrPltnStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center wtrPltnList${wtrPltnStatus.count}">
											<td colspan="6">
							                    등록된 사진이 없습니다.
							                </td>
										</tr>
									</c:if>
									<tr class="wtrPltnList${wtrPltnStatus.count}">
										<th>위도</th>
										<td colspan="2">
											<input type="text" name="wtrPltnLatD1_${wtrPltnStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="wtrPltnLatM1_${wtrPltnStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="wtrPltnLatS1_${wtrPltnStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도</th>
										<td colspan="2">
											<input type="text" name="wtrPltnLonD1_${wtrPltnStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="wtrPltnLonM1_${wtrPltnStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="wtrPltnLonS1_${wtrPltnStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
									</tr>
									<tr class="wtrPltnList${wtrPltnStatus.count}">
										<th>상수원 오염</th>
										<td colspan="5">
											<input type="text" name="wtrPltn_${wtrPltnStatus.count}" value="<c:out value="${item.wtrPltn}"/>">
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="mainMenu">2. 산림 현장현황 > 과다한 훼손우려지
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('dmgCncrn','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('dmgCncrn','add'); return false;">추가</button>
							</div>
							<table id="dmgCncrnTable">
								<colgroup>
									<col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
								</colgroup>
								<tr>
									<th colspan="6">
										과다한 훼손우려지 전체 통계
										<input type="hidden" name="dmgCncrnList" value=""/>
									</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3" class="center">
										<input type="text" name="dmgCncrnAt" class="wd30" value="<c:out value="${svyComptResult.dmgCncrnAt}"/>" readonly="readonly"/>
									</td>
								</tr>
								<c:forEach var="item" items="${dmgCncrnList}" varStatus="dmgCncrnStatus">
									<tr class="dmgCncrnList${dmgCncrnStatus.count} checkPart">
										<th colspan="6"><c:out value="${dmgCncrnStatus.count}"/>번 과다한 훼손우려지</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr dmgCncrnList${dmgCncrnStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 훼손우려지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="dmgCncrnPhoto${dmgCncrnStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center dmgCncrnList${dmgCncrnStatus.count}">
											<td colspan="6">
							                    등록된 사진이 없습니다.
							                </td>
										</tr>
									</c:if>
									<tr class="dmgCncrnList${dmgCncrnStatus.count}">
										<th>위도1</th>
							            <td colspan="2">
							                <input type="text" name="dmgCncrnLatD1_${dmgCncrnStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="dmgCncrnLatM1_${dmgCncrnStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="dmgCncrnLatS1_${dmgCncrnStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
							            </td>
							            <th>경도1</th>
							            <td colspan="2">
							                <input type="text" name="dmgCncrnLonD1_${dmgCncrnStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="dmgCncrnLonM1_${dmgCncrnStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="dmgCncrnLonS1_${dmgCncrnStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
							            </td>
									</tr>
									<tr class="dmgCncrnList${dmgCncrnStatus.count}">
										<th>위도2</th>
							            <td colspan="2">
							                <input type="text" name="dmgCncrnLatD2_${dmgCncrnStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="dmgCncrnLatM2_${dmgCncrnStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="dmgCncrnLatS2_${dmgCncrnStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
							            </td>
							            <th>경도2</th>
							            <td colspan="2">
							                <input type="text" name="dmgCncrnLonD2_${dmgCncrnStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="dmgCncrnLonM2_${dmgCncrnStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="dmgCncrnLonS2_${dmgCncrnStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
							            </td>
									</tr>
									<tr class="dmgCncrnList${dmgCncrnStatus.count}">
										<th>훼손우려지</th>
										<td colspan="5">
											<input type="text" name="dmgCncrn_${dmgCncrnStatus.count}" value="<c:out value="${item.dmgCncrn}"/>">
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="mainMenu">2. 산림 현장현황 > 산불 / 병해충(<span id="frstDsstrCnt" style="font-weight: bold;"><c:out value="${svyComptResult.frstDsstrCnt }"/></span>건)
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('frstDsstr','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('frstDsstr','add'); return false;">추가</button>
							</div>
							<table id="frstDsstrTable">
								<colgroup>
									<col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
								</colgroup>
								<tr>
									<th colspan="6">
										산불 / 병해충 전체 통계
										<input type="hidden" name="frstDsstrList" value=""/>
									</th>
								</tr>
								<tr>
									<th>산불(건)</th>
									<td class="center">
										<input type="text" name="frstDsstrFire" class="wd30" value="<c:out value="${dmgTotalMap.frstFireCnt}"/>" readonly="readonly"/>건
									</td>
									<th>병해충(건)</th>
									<td class="center">
										<input type="text" name="pestCnt" class="wd30" value="<c:out value="${dmgTotalMap.pestCnt}"/>" readonly="readonly"/>건
									</td>
									<th>기타(건)</th>
									<td class="center">
										<input type="text" name="etcCnt" class="wd30" value="<c:out value="${dmgTotalMap.etcCnt}"/>" readonly="readonly"/>건
									</td>
								</tr>
								<c:forEach var="item" items="${frstDsstrList}" varStatus="frstDsstrStatus">
									<tr class="center frstDsstrList${frstDsstrStatus.count} checkPart">
										<th colspan="6"><c:out value="${frstDsstrStatus.count}"/>번 산불 / 병해충</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr frstDsstrList${frstDsstrStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 산불/병해충 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="frstDsstrPhoto${frstDsstrStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center frstDsstrList${frstDsstrStatus.count}">
											<td colspan="6">
							                    등록된 사진이 없습니다.
							                </td>
										</tr>
									</c:if>
									<tr class="frstDsstrList${frstDsstrStatus.count}">
										<th>위도1</th>
										<td>
											<input type="text" name="frstDsstrLatD1_${frstDsstrStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="frstDsstrLatM1_${frstDsstrStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="frstDsstrLatS1_${frstDsstrStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도1</th>
										<td>
											<input type="text" name="frstDsstrLonD1_${frstDsstrStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="frstDsstrLonM1_${frstDsstrStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="frstDsstrLonS1_${frstDsstrStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
										<th rowspan="2">재해유형</th>
										<td rowspan="2" class="frstDsstrCalcArea frstDsstrInputArea">
											<select name="frstDsstrDmgType${frstDsstrStatus.count}" onchange="fnCalcValue('frstDsstr'); fnCheckOptionVal(this.value, ${frstDsstrStatus.count});">
							                    <option value="">${inputSelect }</option>
							                    <option value="산불" <c:if test="${item.dmgType eq '산불'}">selected=selected</c:if>>산불</option>
							                    <option value="병해충" <c:if test="${item.dmgType eq '병해충'}">selected=selected</c:if>>병해충</option>
							                    <option value="기타" <c:if test="${item.dmgType ne '산불' and item.dmgType ne '병해충'}">selected=selected</c:if>>기타</option>
							                </select>
							                <c:if test="${item.dmgType ne '산불' and item.dmgType ne '병해충'}">
												<input type="text" name="frstDsstrDmgEtc_${frstDsstrStatus.count}" value="${item.dmgType}"/>
											</c:if>
										</td>
									</tr>
									<tr class="frstDsstrList${frstDsstrStatus.count}">
										<th>위도2</th>
										<td>
											<input type="text" name="frstDsstrLatD2_${frstDsstrStatus.count}" value="<c:out value="${item.latD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="frstDsstrLatM2_${frstDsstrStatus.count}" value="<c:out value="${item.latM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="frstDsstrLatS2_${frstDsstrStatus.count}" value="<c:out value="${item.latS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도2</th>
										<td>
											<input type="text" name="frstDsstrLonD2_${frstDsstrStatus.count}" value="<c:out value="${item.lonD2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="frstDsstrLonM2_${frstDsstrStatus.count}" value="<c:out value="${item.lonM2}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="frstDsstrLonS2_${frstDsstrStatus.count}" value="<c:out value="${item.lonS2}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="mainMenu">2. 산림 현장현황 > 야생동물
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('wildAnml','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('wildAnml','add'); return false;">추가</button>
							</div>
							<table id="wildAnmlTable">
								<colgroup>
									<col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
								</colgroup>
								<tr>
									<th colspan="6">
										야생동물 전체 통계
										<input type="hidden" name="wildAnmlList" value=""/>
									</th>
								</tr>
								<tr>
									<th>전체(건)</th>
									<td colspan="2" class="center">
										<input type="text" name="wildAnmlCnt" class="wd30" value="<c:out value="${svyComptResult.wildAnmlCnt }"/>" readonly="readonly"/>건
									</td>
									<th>종류</th>
									<td colspan="2">
										<input type="text" name="wildAnmlKind" value="<c:out value="${svyComptResult.wildAnmlKind }"/>" readonly="readonly"/>
									</td>
								</tr>
								<c:forEach var="item" items="${wildAnmlList}" varStatus="wildAnmlStatus">
									<tr class="wildAnmlList${wildAnmlStatus.count} checkPart">
										<th colspan="6"><c:out value="${wildAnmlStatus.count}"/>번 야생동물</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr wildAnmlList${wildAnmlStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 야생동물 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="wildAnmlPhoto${wildAnmlStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center wildAnmlList${wildAnmlStatus.count}">
											<td colspan="6">
							                    등록된 사진이 없습니다.
							                </td>
										</tr>
									</c:if>
									<tr class="wildAnmlList${wildAnmlStatus.count}">
										<th>위도</th>
										<td colspan="2">
							                <input type="text" name="wildAnmlLatD1_${wildAnmlStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="wildAnmlLatM1_${wildAnmlStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="wildAnmlLatS1_${wildAnmlStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도</th>
										<td colspan="2">
							                <input type="text" name="wildAnmlLonD1_${wildAnmlStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							                <input type="text" name="wildAnmlLonM1_${wildAnmlStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							                <input type="text" name="wildAnmlLonS1_${wildAnmlStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
									</tr>
									<tr class="wildAnmlList${wildAnmlStatus.count}">
										<th>유형</th>
										<td colspan="2" class="center wildAnmlCalcArea">
											<input type="text" name="wildAnmlAt${wildAnmlStatus.count}" value="<c:out value="${item.anmlAt}"/>" onchange="fnCalcValue('wildAnml')" />
										</td>
										<th>종류</th>
										<td colspan="2" class="wildAnmlCalcArea">
											<input type="text" name="wildAnmlKind${wildAnmlStatus.count}" value="<c:out value="${item.anmlType}"/>" oninput="fnCalcValue('wildAnml')">
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="mainMenu">5. 사방시설 사업종 > 사방시설 설치 여부
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('ecnrFcltyInstl','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('ecnrFcltyInstl','add'); return false;">추가</button>
							</div>
							<table id="ecnrFcltyInstlTable">
								<colgroup>
									<col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
							        <col width="13.6%;">
							        <col width="19.6%;">
								</colgroup>
								<tr>
									<th colspan="6">
										사방시설 설치 여부 전체 통계
										<input type="hidden" name="ecnrFcltyInstlList" value=""/>
									</th>
								</tr>
								<tr>
									<th>전체(건)</th>
									<td colspan="2" class="center">
										<input type="text" name="ecnrFcltyInstlCnt" class="wd30" value="<c:out value="${svyComptResult.ecnrFcltyInstlCnt }"/>" readonly="readonly"/>건
									</td>
									<th>종류</th>
									<td colspan="2">
										<input type="text" name="ecnrFcltyInstlType" value="<c:out value="${svyComptResult.ecnrFcltyInstlType }"/>" readonly="readonly"/>
									</td>
								</tr>
								<c:forEach var="item" items="${ecnrFcltyInstlList}" varStatus="ecnrFcltyInstlStatus">
									<tr class="ecnrFcltyInstlList${ecnrFcltyInstlStatus.count} checkPart">
										<th colspan="6"><c:out value="${ecnrFcltyInstlStatus.count}"/>번 사방시설 설치 여부</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr ecnrFcltyInstlList${ecnrFcltyInstlStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 사방시설 설치 여부 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="ecnrFcltyInstlPhoto${ecnrFcltyInstlStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center ecnrFcltyInstlList${ecnrFcltyInstlStatus.count}">
											<td colspan="6">
												등록된 사진이 없습니다.
											</td>
										</tr>
									</c:if>
									<tr class="ecnrFcltyInstlList${ecnrFcltyInstlStatus.count}">
										<th>위도</th>
										<td colspan="2">
											<input type="text" name="ecnrFcltyInstlLatD1_${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
											<input type="text" name="ecnrFcltyInstlLatM1_${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
											<input type="text" name="ecnrFcltyInstlLatS1_${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도</th>
										<td colspan="2">
											<input type="text" name="ecnrFcltyInstlLonD1_${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
											<input type="text" name="ecnrFcltyInstlLonM1_${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
											<input type="text" name="ecnrFcltyInstlLonS1_${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
									</tr>
									<tr class="center ecnrFcltyInstlList${ecnrFcltyInstlStatus.count}">
										<th>종류</th>
										<td colspan="5" class="ecnrFcltyInstlCalcArea">
											<input type="text" name="ecnrFcltyInstlType${ecnrFcltyInstlStatus.count}" value="<c:out value="${item.ecnrFcltyInstlType}"/>" oninput="fnCalcValue('ecnrFcltyInstl')">
										</td>
									</tr>
								</c:forEach>
							</table>
							<br>
							<div class="mainMenu">5. 사방시설 사업종 > 사방시설 필요 여부
								<button class="del-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('ecnrFcltyNcsty','del'); return false;">삭제</button>
								<button class="add-btn btn_right" onclick="javascript:fnSvyItemAddDelBtn('ecnrFcltyNcsty','add'); return false;">추가</button>
							</div>
							<table id="ecnrFcltyNcstyTable">
								<colgroup>
									<col width="13.6%;">
									<col width="19.6%;">
									<col width="13.6%;">
									<col width="19.6%;">
									<col width="13.6%;">
									<col width="19.6%;">
								</colgroup>
								<tr>
									<th colspan="6">
										사방시설 필요 여부 전체 통계
										<input type="hidden" name="ecnrFcltyNcstyList" value=""/>
									</th>
								</tr>
								<tr>
									<th>전체(건)</th>
									<td colspan="2" class="center">
										<input type="text" name="ecnrFcltyNcstyCnt" class="wd30" value="<c:out value="${svyComptResult.ecnrFcltyNcstyCnt }"/>" readonly="readonly"/>건
									</td>
									<th>종류</th>
									<td colspan="2">
										<input type="text" name="ecnrFcltyNcstyType" value="<c:out value="${svyComptResult.ecnrFcltyNcstyType }"/>" readonly="readonly"/>
									</td>
								</tr>
								<c:forEach var="item" items="${ecnrFcltyNcstyList}" varStatus="ecnrFcltyNcstyStatus">
									<tr class="ecnrFcltyNcstyList${ecnrFcltyNcstyStatus.count} checkPart">
										<th colspan="6"><c:out value="${ecnrFcltyNcstyStatus.count}"/>번 사방시설 필요 여부</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr ecnrFcltyNcstyList${ecnrFcltyNcstyStatus.count}">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 사방시설 필요 여부 사진" onerror="this.remove ? this.remove() : this.removeNode();">
												 		<input type="hidden" name="ecnrFcltyNcstyPhoto${ecnrFcltyNcstyStatus.count}_${status.count}" value="${photo}"/>
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center ecnrFcltyNcstyList${ecnrFcltyNcstyStatus.count}">
											<td colspan="6">
												등록된 사진이 없습니다.
											</td>
										</tr>
									</c:if>
									<tr class="ecnrFcltyNcstyList${ecnrFcltyNcstyStatus.count}">
										<th>위도</th>
										<td colspan="2">
											<input type="text" name="ecnrFcltyNcstyLatD1_${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.latD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
											<input type="text" name="ecnrFcltyNcstyLatM1_${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.latM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
											<input type="text" name="ecnrFcltyNcstyLatS1_${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.latS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
										</td>
										<th>경도</th>
										<td colspan="2">
											<input type="text" name="ecnrFcltyNcstyLonD1_${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.lonD1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
											<input type="text" name="ecnrFcltyNcstyLonM1_${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.lonM1}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
											<input type="text" name="ecnrFcltyNcstyLonS1_${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.lonS1}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
										</td>
									</tr>
									<tr class="ecnrFcltyNcstyList${ecnrFcltyNcstyStatus.count}">
										<th>종류</th>
										<td colspan="5" class="ecnrFcltyNcstyCalcArea">
											<input type="text" name="ecnrFcltyNcstyType${ecnrFcltyNcstyStatus.count}" value="<c:out value="${item.ecnrFcltyNcstyType}"/>" oninput="fnCalcValue('ecnrFcltyNcsty')">
										</td>
									</tr>
								</c:forEach>
							</table>
						<br>
						<div class="mainMenu">6. 조사자 의견 및 특이사항</div>
						<table>
							<colgroup>
								<col width="20%;">
								<col width="80%;">
							</colgroup>
							<tr>
								<th>일반현황</th>
								<td>
									<input type="text" name="gnrlStts" value="<c:out value="${svyComptResult.gnrlStts }"/>"/>
								</td>
							</tr>
							<tr>
								<th>종단기울기</th>
								<td>
									<input type="text" name="lonSlope" value="<c:out value="${svyComptResult.lonSlope }"/>"/>
								</td>
							</tr>
							<tr>
								<th>산지경사</th>
								<td>
									<input type="text" name="mtSlope" value="<c:out value="${svyComptResult.mtSlope }"/>"/>
								</td>
							</tr>
							<tr>
								<th>사면현황</th>
								<td>
									<input type="text" name="slopeStatus" value="<c:out value="${svyComptResult.slopeStatus }"/>"/>
								</td>
							</tr>
							<tr>
								<th>대상지 내 암반</th>
								<td>
									<input type="text" name="destRock" value="<c:out value="${svyComptResult.destRock }"/>"/>
								</td>
							</tr>
							<tr>
								<th>대상지 내 사면침식현황</th>
								<td>
									<input type="text" name="destErsn" value="<c:out value="${svyComptResult.destErsn }"/>"/>
								</td>
							</tr>
							<tr>
								<th>대상지 계류현황</th>
								<td>
									<input type="text" name="destMrngStat" value="<c:out value="${svyComptResult.destMrngStat }"/>"/>
								</td>
							</tr>
							<tr>
								<th>계류 내 침식현황</th>
								<td>
									<input type="text" name="mrngErsnStat" value="<c:out value="${svyComptResult.mrngErsnStat }"/>"/>
								</td>
							</tr>
							<tr>
								<th>기타(용출수, 묘지 등)</th>
								<td>
									<input type="text" name="etc" value="<c:out value="${svyComptResult.etc }"/>"/>
								</td>
							</tr>
						</table>
					</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncSvyComptUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>