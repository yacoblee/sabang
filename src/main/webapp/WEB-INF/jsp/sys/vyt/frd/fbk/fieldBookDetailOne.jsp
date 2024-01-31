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
<c:set var="pageTitle"><spring:message code="sysVytFrd.fieldBookDetailOne.title"/></c:set>
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
				<input name="gid" type="hidden" value="<c:out value='${result.gid}'/>">
				<input name="schmst_partname" type="hidden" value="<c:out value='${searchMap.schmst_partname}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
				<input name="schid" type="hidden" value="<c:out value='${searchMap.schid}'/>">
				<input name="schmst_create_user" type="hidden" value="<c:out value='${searchMap.schmst_create_user}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
				<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
				<input name="schmst_id" type="hidden" value="<c:out value='${mstId}'/>">
			</form>
		</fieldset>
		<fieldset>
			<form id="listForm" action="/sys/vyt/frd/fbk/updateFieldBookViewOne.do" method="post">
				<input name="gid" type="hidden" value="<c:out value='${result.gid}'/>">
				<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
				<input name="mstId" type="hidden" value="<c:out value='${searchVO.mst_id}'/>">
				<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
				<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
				<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
				<input name="pageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
				<input name="schmst_id" type="hidden" value="<c:out value='${mstId}'/>">
			</form>
		</fieldset>
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th><spring:message code="sysVytFrd.svyComptVO.svyId" /></th><!-- 조사ID -->
						<td><c:out value="${result.svyId}"/></td>
						<th><spring:message code="sysVytFrd.svyComptVO.year" /></th><!-- 조사연도 -->
						<td><c:out value="${result.svyYear}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.stripLandVO.region" /></th><!-- 관할 -->
						<td><c:out value="${result.compentauth}"/></td>
						<th>세부 <spring:message code="sysVytFrd.stripLandVO.region" /></th><!-- 관할 -->
						<td><c:out value="${result.subcompentauth}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.analManageVO.addr" /></th><!-- 주소 -->
						<td colspan="3"><c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/> <c:out value="${result.svyRi}"/> <c:out value="${result.svyJibun}"/> </td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.routetype" /></th><!-- 노선종류 -->
						<td><c:out value="${result.routetype}"/></td>
						<th><spring:message code="sysVytFrd.fieldBookVO.frd" /><spring:message code="sysVytFrd.fieldBookVO.type" /></th><!-- 임도종류 -->
						<td><c:out value="${result.frdtype}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.schDst" /></th><!-- 예정거리 -->
						<td><c:out value="${result.schdst}"/>km</td>
						<th><spring:message code="sysVytFrd.fieldBookVO.frdRnk" /></th><!-- 조사구분 -->
						<td><c:out value="${result.frdRnk}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lat" /></th><!-- 시점위도 -->
						<td><c:out value="${result.bpy}"/></td>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lon" /></th><!-- 시점경도 -->
						<td><c:out value="${result.bpx}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />1</th><!-- 종점위도1 -->
						<td><c:out value="${result.epy1}"/></td>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />1</th><!-- 종점경도1 -->
						<td><c:out value="${result.epx1}"/></td>
					</tr>
					<c:if test="${result.epy2 != null && result.epx2 != null}">
						<tr>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />2</th><!-- 종점경도 -->
							<td><c:out value="${result.epy2}"/></td>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />2</th><!-- 종점위도 -->
							<td><c:out value="${result.epx2}"/></td>
						</tr>
					</c:if>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
							<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.gid}"/>');"><spring:message code="title.delete" /></button>
							<button type="button" class="modify-btn" onclick="javascript:fnSelectFieldBookUpdtOne('<c:out value="${result.gid}"/>');"><spring:message code="button.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList('/sys/vyt/frd/fbk/selectFieldBookDetail.do'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
	</div>
</div>