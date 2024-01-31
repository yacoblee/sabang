<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="pageTitle"><spring:message code="sysLssBsc.fieldBookDetail.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysLssBsc.detailfieldBookItem.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form id="listForm" action="/sys/lss/cnl/fbk/updateFieldBookView.do" method="post">
			<input name="id" type="hidden" value="<c:out value='${result.id}'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th><spring:message code="sysLssBsc.fieldBookVO.id" /></th>
						<td><c:out value="${result.id}"/></td>
						<th><spring:message code="sysLssBsc.fieldBookVO.create_user" /></th>
						<td><c:out value="${result.mst_create_user}"/></td>
						<th><spring:message code="sysLssBsc.fieldBookVO.registered" /></th>
						<td><c:out value="${result.mst_registered}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssBsc.fieldBookVO.corpname" /></th>
						<td><c:out value="${result.mst_corpname}"/></td>
						<th><spring:message code="sysLssBsc.fieldBookVO.partname" /></th>
						<td><c:out value="${result.mst_partname}"/></td>
						<th><spring:message code="sysLssBsc.fieldBookVO.totcnt" /></th>
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
						<col style="width: 14%;">
						<col style="width: 14%;">
						<col style="width: 14%;">
						<col style="width: 14%;">
						<col style="width: 32%;">
						<col style="width: 7%;">
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox"  id="fieldBook_all_check" onclick="javascript:fnSelectAllCheck();" /></th>
							<th><spring:message code="sysLssBsc.stripLandVO.id" /></th>
							<th><spring:message code="sysLssBsc.stripLandVO.type" /></th>
							<th><spring:message code="sysLssBsc.stripLandVO.lat" /></th>
							<th><spring:message code="sysLssBsc.stripLandVO.lon" /></th>
							<th><spring:message code="sysLssBsc.stripLandVO.addr" /></th>
							<th><spring:message code="sysLssBsc.stripLandVO.compAt" /></th>
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
								<td><input type="checkbox" name="fieldBook_check" class="fieldBook_check" value="<c:out value="${result.id}"/>"></td>
								<td><c:out value="${result.id}"/></td>
								<td><c:out value="${result.type}"/></td>
								<td><c:out value="${result.lat}"/></td>
								<td><c:out value="${result.lon}"/></td>
								<td><c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> </td>
								<td><c:out value="${result.at}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fnUpdateCnrsSpce('<c:out value="${result.id}"/>');"><spring:message code="title.delete" /></button>
					<button type="button" class="back-btn" onclick="javascript:fnFieldBookDetail('<c:out value="${result.id}"/>');"><spring:message code="button.reset" /></button>
				</div>
			</div>
		</form>
	</div>
</div>