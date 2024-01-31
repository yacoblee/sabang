<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysVytFrd.fieldBookUpdtOne.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysVytFrd.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">메인화면</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
		<form id="searchForm" action="/sys/vyt/frd/fbk/selectFieldBookDetailOne.do" method="post">
			<input name="schgid" type="hidden" value="<c:out value='${result.gid}'/>">
			<input name="schmst_partname" type="hidden" value="<c:out value='${searchMap.schmst_partname}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
			<input name="schid" type="hidden" value="<c:out value='${searchMap.schid}'/>">
			<input name="schmst_create_user" type="hidden" value="<c:out value='${searchMap.schmst_create_user}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
			<input name="schmst_id" type="hidden" value="<c:out value='${result.mst_id}'/>">
		</form>
		</fieldset>
		<form id="listForm" action="/sys/vyt/frd/fbk/updateFieldBookDetailOne.do" method="post">
		<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
			<input name="gid" type="hidden" value="<c:out value='${result.gid}'/>">
			<input name="mst_partname" type="hidden" value="<c:out value='${searchMap.schmst_partname}'/>">
			<input name="svy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
			<input name="id" type="hidden" value="<c:out value='${searchMap.schid}'/>">
			<input name="mst_create_user" type="hidden" value="<c:out value='${searchMap.schmst_create_user}'/>">
			<input name="pageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
			<input name="pageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
			<input name="pageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
			<input name="mst_id" type="hidden" value="<c:out value='${result.mst_id}'/>">
			
			<input name="lon" type="hidden" value="<c:out value='${result.lon}'/>">
			<input name="lat" type="hidden" value="<c:out value='${result.lat}'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th><spring:message code="sysVytFrd.svyComptVO.svyId" /></th><!-- 조사ID -->
						<td><input type="text" name="svyId" value="<c:out value="${result.svyId}"/>" readonly="readonly"/></td>
						<th><spring:message code="sysVytFrd.svyComptVO.year" /></th><!-- 조사연도 -->
						<td><input type="text" name="svyYear" value="<c:out value="${result.svyYear}"/>" readonly="readonly"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.stripLandVO.region" /></th><!-- 관할 -->
						<td colspan="3">
							<select id="compentAssort" name="compentAssort" class="w30p" onchange="fncCompentAuthChange(); return false;">
								<option value="">${inputSelect}</option>
								<c:forEach var="result" items="${subCompentauthList}">
									<option value="<c:out value="${result.codeNm}"/>"<c:if test="${compentauthType.compentauthType eq result.codeNm}">selected="selected"</c:if>><c:out value="${result.codeNm}"/></option>
								</c:forEach>
							</select>
							<select id="compentauth" name="compentauth" class="w30p" onchange="fncSubCompentAuthChange(); return false;">
								<option value=""<c:if test="${empty result.compentauth || result.compentauth == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="regionCodeList" items="${regionCodeList}" varStatus="status">
									<c:if test="${compentauthType.compentauthType eq '국유림' }">
										<option value="<c:out value="${regionCodeList.codeNm}"/>"<c:if test="${result.compentauth eq regionCodeList.codeNm}">selected="selected"</c:if>><c:out value="${regionCodeList.codeNm}"/></option>
									</c:if>
									<c:if test="${compentauthType.compentauthType eq '민유림' }">
										<option value="<c:out value="${regionCodeList.adminNm}"/>"<c:if test="${result.compentauth eq regionCodeList.adminNm}">selected="selected"</c:if>><c:out value="${regionCodeList.adminNm}"/></option>
									</c:if>
								</c:forEach>
							</select>
							<select id="subcompentauth" name="subcompentauth" class="w30p">
								<option value=""<c:if test="${empty result.subcompentauth || result.subcompentauth == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="region2CodeList" items="${region2CodeList}" varStatus="status">
									<c:if test="${compentauthType.compentauthType eq '국유림' }">
										<option value="<c:out value="${region2CodeList.codeNm}"/>"<c:if test="${result.subcompentauth eq region2CodeList.codeNm}">selected="selected"</c:if>><c:out value="${region2CodeList.codeNm}"/></option>
									</c:if>
									<c:if test="${compentauthType.compentauthType eq '민유림' }">
										<option value="<c:out value="${region2CodeList.adminNm}"/>"<c:if test="${result.subcompentauth eq region2CodeList.adminNm}">selected="selected"</c:if>><c:out value="${region2CodeList.adminNm}"/></option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.analManageVO.addr" /></th><!-- 주소 -->
<%-- 						<td colspan="3"><c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/> <c:out value="${result.svyRi}"/> <c:out value="${result.svyJibun}"/> </td> --%>
						<td colspan="3">
							<select id="svySdView" name="svySd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministCtprvnNmChange(); return false;">
								<option value=""<c:if test="${empty result.svySd || result.svySd == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="sdCodeList" items="${sdCodeList}" varStatus="status">
								<option value="<c:out value="${sdCodeList.adminNm}"/>"<c:if test="${result.svySd eq sdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sdCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="svySggView" name="svySgg" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministSignguNmChange(); return false;">
								<option value=""<c:if test="${empty result.svySgg || result.svySgg == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="sggCodeList" items="${sggCodeList}" varStatus="status">
								<option value="<c:out value="${sggCodeList.adminNm}"/>"<c:if test="${result.svySgg eq sggCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sggCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="svyEmdView" name="svyEmd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministEmdNmChange(); return false;">
								<option value=""<c:if test="${empty result.svyEmd || result.svyEmd == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="emdCodeList" items="${emdCodeList}" varStatus="status">
								<option value="<c:out value="${emdCodeList.adminNm}"/>"<c:if test="${result.svyEmd eq emdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${emdCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="svyRiView" name="svyRi" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15">
								<option value=""<c:if test="${empty result.svyRi || result.svyRi == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="riCodeList" items="${riCodeList}" varStatus="status">
								<option value="<c:out value="${riCodeList.adminNm}"/>"<c:if test="${result.svyRi eq riCodeList.adminNm}">selected="selected"</c:if>><c:out value="${riCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<input type="text" name="svyJibun" class="wd15" value="<c:out value="${result.svyJibun}"/>" title="${title} ${inputTxt}"/>
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.routetype" /></th><!-- 노선종류 -->
						<td>
<%-- 							<select id="routetype" name="routetype" title="<spring:message code="sysVytFrd.fieldBookVO.routetype"/>"> --%>
<%-- 								<option value=""<c:if test="${empty result.routetype || result.routetype == '0'}">selected="selected"</c:if>> ${inputSelect}</option> --%>
<%-- 								<c:forEach var="routeTypeList" items="${routeTypeList}" varStatus="status"> --%>
<%-- 								<option value="<c:out value="${routeTypeList.codeNm}"/>"<c:if test="${result.routetype eq routeTypeList.codeNm}">selected="selected"</c:if>><c:out value="${routeTypeList.codeNm}"/></option> --%>
<%-- 								</c:forEach> --%>
<%--  							</select> --%>
							<input type="text" name="routetype" value="<c:out value="${result.routetype}"/>" readonly="readonly"/>
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.frd" /><spring:message code="sysVytFrd.fieldBookVO.type" /></th><!-- 임도종류 -->
						<td>
							<select id="frdtype" name="frdtype" title="<spring:message code="sysVytFrd.fieldBookVO.frd" /><spring:message code="sysVytFrd.fieldBookVO.type" />">
								<option value=""<c:if test="${empty result.frdtype || result.frdtype == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="frdTypeList" items="${frdTypeList}" varStatus="status">
								<option value="<c:out value="${frdTypeList.codeNm}"/>"<c:if test="${result.frdtype eq frdTypeList.codeNm}">selected="selected"</c:if>><c:out value="${frdTypeList.codeNm}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.schDst" /></th><!-- 예정거리 -->
						<td><input type="text" name="schdst" class="wd15" value="<c:out value="${result.schdst}"/>"/>km</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.frdRnk" /></th><!-- 조사구분 -->
						<td>
							<select id="frdRnk" name="frdRnk" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15">
								<option value=""<c:if test="${empty result.frdRnk || result.frdRnk == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="frdRnkList" items="${frdRnkList}" varStatus="status">
								<option value="<c:out value="${frdRnkList.codeNm}"/>"<c:if test="${result.frdRnk eq frdRnkList.codeNm}">selected="selected"</c:if>><c:out value="${frdRnkList.codeNm}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lat" /></th><!-- 시점위도 -->
						<td>
							<input type="text" name="bpyD" value="<c:out value="${result.bpyD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="bpyM" value="<c:out value="${result.bpyM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="bpyS" value="<c:out value="${result.bpyS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lon" /></th><!-- 시점경도 -->
						<td>
							<input type="text" name="bpxD" value="<c:out value="${result.bpxD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="bpxM" value="<c:out value="${result.bpxM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="bpxS" value="<c:out value="${result.bpxS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />1</th><!-- 종점위도1 -->
						<td>
							<input type="text" name="epy1D" value="<c:out value="${result.epy1D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="epy1M" value="<c:out value="${result.epy1M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="epy1S" value="<c:out value="${result.epy1S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />1</th><!-- 종점경도1 -->
						<td>
							<input type="text" name="epx1D" value="<c:out value="${result.epx1D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="epx1M" value="<c:out value="${result.epx1M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="epx1S" value="<c:out value="${result.epx1S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
						</td>
					</tr>
					<c:if test="${result.epy2D != null && result.epy2M != null && result.epy2S != null && result.epx2D != null && result.epx2M != null && result.epx2S != null}">
						<tr>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />2</th><!-- 종점위도2 -->
							<td>
								<input type="text" name="epy2D" value="<c:out value="${result.epy2D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="epy2M" value="<c:out value="${result.epy2M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="epy2S" value="<c:out value="${result.epy2S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
							</td>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />2</th><!-- 종점경도2 -->
							<td>
								<input type="text" name="epx2D" value="<c:out value="${result.epx2D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="epx2M" value="<c:out value="${result.epx2M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="epx2S" value="<c:out value="${result.epx2S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
							</td>
						</tr>
					</c:if>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookDetailOne('<c:out value="${result.gid}"/>'); return false;"><spring:message code="button.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList('/sys/vyt/frd/fbk/selectFieldBookDetailOne.do'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>