<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysVytFrd.stripLand.title"/></c:set>
<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle}<spring:message code="title.management"/> <spring:message code="title.update"/></h3>
	<div id="contents">
		<form id="searchForm" action="/sys/vyt/frd/sld/selectSldInfoDetail.do">
			<input name="schsmid" type="hidden" value="${result.smid}"/>
			<input name="schid" type="hidden" value="${result.id}"/>
			<input name="schsld_nm" type="hidden" value="<c:out value='${searchMap.schsldNm}'/>">
			<input name="schsld_create_user" type="hidden" value="<c:out value='${searchMap.schsld_create_user}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
		</form>
		<form id="listForm" action="/sys/vyt/frd/sld/updateSldInfoUpdt.do" method="post">
			<input name="smid" type="hidden" value="${result.smid}"/>
			<input name="id" type="hidden" value="${result.id}"/>
			<input name="schsld_nm" type="hidden" value="<c:out value='${searchMap.schsldNm}'/>">
			<input name="schsld_create_user" type="hidden" value="<c:out value='${searchMap.schsld_create_user}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
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
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th>조사ID</th><!-- 조사ID -->
							<td colspan="2"><input name="sldlabel" type="text" value="<c:out value="${result.sldlabel}"/>"  readonly="readonly"/></td>
							<th>임도종류</th>
							<td colspan="2">
								<select id="frdtype" name="frdtype" title="<spring:message code="sysVytFrd.fieldBookVO.frd" /><spring:message code="sysVytFrd.fieldBookVO.type" />">
									<option value=""<c:if test="${empty result.frdtype || result.frdtype == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
									<c:forEach var="frdTypeList" items="${frdTypeList}" varStatus="status">
									<option value="<c:out value="${frdTypeList.codeNm}"/>"<c:if test="${result.frdtype eq frdTypeList.codeNm}">selected="selected"</c:if>><c:out value="${frdTypeList.codeNm}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>예정거리</th>
							<td colspan="2"><input name="schdst" class="w85p" type="text" value="<c:out value="${result.schdst}"/>"/>km</td>						
							<th>노선종류</th>
							<td colspan="2">
								<input name="routetype" type="text" value="<c:out value="${result.routetype}"/>"  readonly="readonly"/>
<%-- 								<select id="routetype" name="routetype" title="<spring:message code="sysVytFrd.fieldBookVO.routetype"/>"> --%>
<%-- 									<option value=""<c:if test="${empty result.routetype || result.routetype == '0'}">selected="selected"</c:if>> ${inputSelect}</option> --%>
<%-- 									<c:forEach var="routeTypeList" items="${routeTypeList}" varStatus="status"> --%>
<%-- 									<option value="<c:out value="${routeTypeList.codeNm}"/>"<c:if test="${result.routetype eq routeTypeList.codeNm}">selected="selected"</c:if>><c:out value="${routeTypeList.codeNm}"/></option> --%>
<%-- 									</c:forEach> --%>
<!-- 								</select> -->
							</td>						
						</tr>
						<tr>
						<th><spring:message code="sysVytFrd.analManageVO.addr" /></th><!-- 주소 -->
<%-- 						<td colspan="3"><c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/> <c:out value="${result.svyRi}"/> <c:out value="${result.svyJibun}"/> </td> --%>
						<td colspan="5">
							<select id="sdView" name="sd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministCtprvnNmChange(); return false;">
								<option value=""<c:if test="${empty result.sd || result.sd == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="sdCodeList" items="${sdCodeList}" varStatus="status">
								<option value="<c:out value="${sdCodeList.adminNm}"/>"<c:if test="${result.sd eq sdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sdCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="sggView" name="sgg" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministSignguNmChange(); return false;">
								<option value=""<c:if test="${empty result.sgg || result.sgg == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="sggCodeList" items="${sggCodeList}" varStatus="status">
								<option value="<c:out value="${sggCodeList.adminNm}"/>"<c:if test="${result.sgg eq sggCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sggCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="emdView" name="emd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministEmdNmChange(); return false;">
								<option value=""<c:if test="${empty result.emd || result.emd == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="emdCodeList" items="${emdCodeList}" varStatus="status">
								<option value="<c:out value="${emdCodeList.adminNm}"/>"<c:if test="${result.emd eq emdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${emdCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<select id="riView" name="ri" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15">
								<option value=""<c:if test="${empty result.ri || result.ri == '0'}">selected="selected"</c:if>> ${inputSelect}</option>
								<c:forEach var="riCodeList" items="${riCodeList}" varStatus="status">
								<option value="<c:out value="${riCodeList.adminNm}"/>"<c:if test="${result.ri eq riCodeList.adminNm}">selected="selected"</c:if>><c:out value="${riCodeList.adminNm}"/></option>
								</c:forEach>
							</select>
							<input type="text" name="jibun" class="wd15" value="<c:out value="${result.jibun}"/>" title="${title} ${inputTxt}"/>
						</td>
					</tr>
					<tr>
						<th colspan="6">GPS좌표</th>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lat" /></th><!-- 시점위도 -->
						<td colspan="2">
							<input type="text" name="bpyD" value="<c:out value="${result.bpyD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="bpyM" value="<c:out value="${result.bpyM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="bpyS" value="<c:out value="${result.bpyS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 100px;">"N
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lon" /></th><!-- 시점경도 -->
						<td colspan="2">
							<input type="text" name="bpxD" value="<c:out value="${result.bpxD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="bpxM" value="<c:out value="${result.bpxM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="bpxS" value="<c:out value="${result.bpxS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 100px;">"E
						</td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />1</th><!-- 종점위도1 -->
						<td colspan="2">
							<input type="text" name="epy1D" value="<c:out value="${result.epy1D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="epy1M" value="<c:out value="${result.epy1M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="epy1S" value="<c:out value="${result.epy1S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 100px;">"N
						</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />1</th><!-- 종점경도1 -->
						<td colspan="2">
							<input type="text" name="epx1D" value="<c:out value="${result.epx1D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
							<input type="text" name="epx1M" value="<c:out value="${result.epx1M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
							<input type="text" name="epx1S" value="<c:out value="${result.epx1S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 100px;">"E
						</td>
					</tr>
					<c:if test="${result.epy2D != null && result.epy2M != null && result.epy2S != null && result.epx2D != null && result.epx2M != null && result.epx2S != null}">
						<tr>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />2</th><!-- 종점위도2 -->
							<td colspan="2">
								<input type="text" name="epy2D" value="<c:out value="${result.epy2D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="epy2M" value="<c:out value="${result.epy2M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="epy2S" value="<c:out value="${result.epy2S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 100px;">"N
							</td>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />2</th><!-- 종점경도2 -->
							<td colspan="2">
								<input type="text" name="epx2D" value="<c:out value="${result.epx2D}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="epx2M" value="<c:out value="${result.epx2M}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="epx2S" value="<c:out value="${result.epx2S}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 100px;">"E
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty result.etc}">
						<tr>
							<th>기타</th>
							<td colspan="5"><input name="etc" type="text" value="<c:out value="${result.etc}"/>"/></td>
						</tr>
					</c:if>
<!-- 					<tr> -->
<!-- 						<th>기타</th> -->
<%-- 						<td colspan="5"><input name="etc" type="text" value="<c:out value="${result.etc}"/>"/></td> --%>
<!-- 					</tr> -->
					</tbody>
				</table>
				<br>
				<div class="mainMenu">□ 분석통계</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tbody>
						<tr class="center">
							<th colspan="4">임&nbsp;&nbsp;황&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
						</tr>
						<tr class="center">
							<th>임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;상</th>
							<td>
								<c:choose>
									<c:when test="${not empty statList.frorMaxNm}"><c:out value="${statList.frorMaxNm}"/> <c:out value="${statList.frorMaxVal}"/></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<th>경&nbsp;&nbsp;&nbsp;급&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
							<td>
								<c:choose>
									<c:when test="${not empty statList.dmclsMaxNm}"><c:out value="${statList.dmclsMaxNm}"/> <c:out value="${statList.dmclsMaxVal}"/></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr class="center">
							<th>밀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도</th>
							<td>
								<c:choose>
									<c:when test="${not empty statList.dnstMaxNm}"><c:out value="${statList.dnstMaxNm}"/> <c:out value="${statList.dnstMaxVal}"/></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<th>영&nbsp;&nbsp;&nbsp;급&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
							<td>
								<c:choose>
									<c:when test="${not empty statList.agclsMaxNm}">
										<c:choose>
											<c:when test="${statList.agclsMaxNm eq '1영급'}">Ⅰ영급 </c:when>
											<c:when test="${statList.agclsMaxNm eq '2영급'}">Ⅱ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '3영급'}">Ⅲ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '4영급'}">Ⅳ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '5영급'}">Ⅴ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '6영급'}">Ⅵ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '7영급'}">Ⅶ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '8영급'}">Ⅷ영급</c:when>
											<c:when test="${statList.agclsMaxNm eq '9영급'}">Ⅸ영급</c:when>
										</c:choose>
										<c:out value="${statList.agclsMaxVal}"/>
									</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width="35%"/>
							<col width="15%"/>
							<col width="35%"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="4">지&nbsp;&nbsp;황&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr class="center">
								<th>평&nbsp;&nbsp;&nbsp;균&nbsp;&nbsp;&nbsp;경&nbsp;&nbsp;&nbsp;사</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.slopeMaxVal}">
											<fmt:formatNumber value="${statList.slopeMinVal}" pattern="0.00"></fmt:formatNumber>°~<fmt:formatNumber value="${statList.slopeMaxVal}" pattern="0.00"></fmt:formatNumber>° / 평균 <fmt:formatNumber value="${statList.slopeAvgVal}" pattern="0.00"></fmt:formatNumber>°
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.soilMaxNm}"><c:out value="${statList.soilMaxNm}"/> <c:out value="${statList.soilMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>향&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.aspectMaxNm}"><c:out value="${statList.aspectMaxNm}"/> <c:out value="${statList.aspectMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>퇴&nbsp;&nbsp;&nbsp;적&nbsp;&nbsp;&nbsp;양&nbsp;&nbsp;&nbsp;식</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.accmaMaxNm}"><c:out value="${statList.accmaMaxNm}"/> <c:out value="${statList.accmaMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>표&nbsp;&nbsp;&nbsp;고&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.demMaxVal}">
											평균 <fmt:formatNumber value="${statList.demAvgVal}" pattern=".00"></fmt:formatNumber>m
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>모&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;암</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.prrckMaxNm}"><c:out value="${statList.prrckMaxNm}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
								<td>
									<c:choose>
										<c:when test="${statList.geologyCnt eq 1}">
											<c:out value="${statList.geologyMaxNm}"/>
										</c:when>
										<c:when test="${statList.geologyCnt > 1}">
											<c:out value="${statList.geologyMaxNm}"/> 등
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>암&nbsp;석&nbsp;노&nbsp;출&nbsp;도</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.rockMaxNm}"><c:out value="${statList.rockMaxNm}"/> <c:out value="${statList.rockMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">산사태취약지역 및 산사태위험등급도</th>
							</tr>
							<tr class="center">
								<th rowspan="2">산불취약지도</th>
								<th>A등급</th>
								<th>B등급</th>
								<th>C등급</th>
								<th>D등급</th>
								<th></th>
							</tr>
							<tr class="center">
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td></td>
							</tr>
							<tr class="center">
								<th rowspan="2">산사태위험도</th>
								<th>1등급</th>
								<th>2등급</th>
								<th>3등급</th>
								<th>4등급</th>
								<th>5등급</th>
							</tr>
							<tr class="center">
								<c:set var="landslide" value="${statList.landslide}" scope="page" />
								<c:choose>
									<c:when test="${empty landslide['1등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['1등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['2등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['2등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['3등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['3등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['4등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['4등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['5등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['5등급']}" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">기&nbsp;&nbsp;타&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr>
								<th>생태자연도</th>
								<td colspan="5">
									<input type="text" name="ecoNtrMap" value="<c:out value="${result.ecoNtrMap}"/>"/>
								</td>
							</tr>
							<tr>
								<th>멸종위기종서식 현황</th>
								<td colspan="5">
									<input type="text" name="endSpc" value="<c:out value="${result.endSpc}"/>"/>
								</td>
							</tr>
							<tr>
								<th>특별산림보호종</th>
								<td colspan="5">
									<input type="text" name="spcFrsPrt" value="<c:out value="${result.spcFrsPrt}"/>"/>
								</td>
							</tr>
						</tbody>
					</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">	
						<button type="button" class="modify-btn" onclick="javascript:fnSvySldInfoUpdt(); return false;">수정</button>
						<button type="button" class="list-btn" onclick="javascript:fnList('/sys/vyt/frd/sld/selectFrdSldinfo.do'); return false;">목록</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").submit();
	}
</script>