<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="pageTitle"><spring:message code="sysLssLcp.fieldBookDetail.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysLssLcp.updatefieldBookItem.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">땅밀림실태조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form id="listForm" action="/sys/lss/lcp/fbk/updateFieldBookView.do" method="post">
			<input name="id" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${searchVO.MST_ID}'/>">
			<input name="mst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
			<input name="mst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
			<input name="svyYear" type="hidden" value="<c:out value='${fieldBookVO.svyYear}'/>">
			<input name="mst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
			<input name="pageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
			<input name="pageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
			<input name="pageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
			
			<input name="svyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
			<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
			<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
			<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th><spring:message code="sysLssLcp.fieldBookVO.id" /></th>
						<td><c:out value="${result.id}"/></td>
						<th><spring:message code="sysLssLcp.fieldBookVO.create_user" /></th>
						<td><c:out value="${result.mst_create_user}"/></td>
						<th><spring:message code="sysLssLcp.fieldBookVO.registered" /></th>
						<td><c:out value="${result.mst_registered}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssLcp.fieldBookVO.corpname" /></th>
						<td><c:out value="${result.mst_corpname}"/></td>
						<th><spring:message code="sysLssLcp.fieldBookVO.partname" /></th>
						<td><c:out value="${result.mst_partname}"/></td>
						<th><spring:message code="sysLssLcp.fieldBookVO.totcnt" /></th>
						<td><c:out value="${result.totcnt}"/></td>
					</tr>
				</table>
			</div>
			
			<h3>${subTitle}</h3><br>
			<div class="BoardList"style=" width:100%; height:400px; overflow:auto;">
				<table class="check_table" summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width: 5%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 18%;">
						<col style="width: 31%;">
						<col style="width: 7%;">
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox"  id="fieldBook_all_check" onclick="javascript:fnSelectAllCheck();" /></th>
							<th><spring:message code="sysLssLcp.stripLandVO.id" /></th>
							<th><spring:message code="sysLssLcp.stripLandVO.type" /></th>
							<th><spring:message code="sysLssLcp.stripLandVO.lat" /></th>
							<th><spring:message code="sysLssLcp.stripLandVO.lon" /></th>
							<th><spring:message code="sysLssLcp.svyComptVO.region" /></th>
							<th><spring:message code="sysLssLcp.stripLandVO.addr" /></th>
							<th><spring:message code="sysLssLcp.stripLandVO.compAt" /></th>
						</tr>
					</thead>
					<tbody class="ov">
						<c:if test="${fn:length(resultList) == 0}">
							<tr>
								<td colspan="7" style="text-align:center;"><spring:message code="info.nodata.msg" /></td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="fieldBook_check" class="fieldBook_check" value="<c:out value="${result.smid}"/>"></td>
								<td><c:out value="${result.id}"/></td>
								<td><c:out value="${result.type}"/></td>
								<td><c:out value="${result.lat}"/></td>
								<td><c:out value="${result.lon}"/></td>
								<td><c:out value="${result.region1}"/> <c:out value="${result.region2}"/></td>
								<td><c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> </td>
								<td><c:out value="${result.at}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fnUpdateCnrsSpce();"><spring:message code="title.delete" /></button>
					<button type="button" class="back-btn" onclick="javascript:fnFieldBookDetail('<c:out value="${result.id}"/>');"><spring:message code="button.reset" /></button>
				</div>
			</div>
		</form>
	</div>
</div>