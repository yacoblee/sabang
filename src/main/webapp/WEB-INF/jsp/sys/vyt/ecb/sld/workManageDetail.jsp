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
<c:set var="pageTitle"><spring:message code="sysVytEcb.work.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysVytEcb.stripLand.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">사방사업</a></li>
		<li><a href="#">사업관리</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail"/></h3>
	<div id="contents">		
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width: 14%;">
						<col style="width: 20%;">
						<col style="width: 14%;">
						<col style="width: 20%;">
						<col style="width: 12%;">
						<col style="width: 20%;">
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="sysVytEcb.workVO.workType" /></th>
							<td><c:out value="${result.workType}"/></td>
							<th><spring:message code="sysVytEcb.workVO.workName" /></th>
							<td colspan="3"><c:out value="${result.workNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysVytEcb.workVO.creatDt" /></th>
							<td><c:out value="${result.creatDt}"/></td>
							<th><spring:message code="sysVytEcb.workVO.creatUser" /></th>
							<td><c:out value="${result.creatUser}"/></td>
							<th><spring:message code="sysVytEcb.workVO.workCnt" /></th>
							<td><c:out value="${result.workCnt}"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<fieldset>
			<form id="listForm" action="/sys/vyt/ecb/sld/selectVytEcbWorkDetail.do" method="post">
				<input name="id" type="hidden" value="<c:out value='${searchVO.id}'/>">
				<input name="schworkNm" type="hidden" value="<c:out value='${searchVO.workNm}'/>">
				<input name="schworkYear" type="hidden" value="<c:out value='${searchVO.workYear}'/>">
				<input name="schworkType" type="hidden" value="<c:out value='${searchVO.workType}'/>">
				<input name="schcreatUser" type="hidden" value="<c:out value='${searchVO.creatUser}'/>">
				<input name="pageSubIndex" type="hidden" value="<c:out value='${searchVO.pageSubIndex}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${schpageUnit}'/>">
			</form>
			</fieldset>
			<div class="BoardList">
			<h3>${subTitle} <spring:message code="title.list"/></h3>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<sec:authorize access="hasAnyRole('ROLE_USER_CNRS','ROLE_ADMIN_VYTECB','ROLE_SUB_ADMIN','ROLE_ADMIN')">
				<button style="float:right;" type="button" class="save-btn" onclick="javascript:fnInsertWorkSldPopup('<c:out value="${result.id}"/>');"><spring:message code="sysVytEcb.stripLandVO.upload" /></button>
				</sec:authorize>
			</span>
			
			<table summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
				<colgroup>
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 25%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="sysVytEcb.stripLandVO.sldId" /></th>
						<th><spring:message code="sysVytEcb.analManageVO.sd" /></th><!-- 시도명 -->
						<th><spring:message code="sysVytEcb.analManageVO.sgg" /></th><!-- 시군구명 -->
						<th><spring:message code="sysVytEcb.analManageVO.emd" /></th><!-- 읍면동명 -->
						<th><spring:message code="sysVytEcb.analManageVO.ri" /></th><!-- 리명 -->
						<th><spring:message code="sysVytEcb.analManageVO.jibun" /></th><!-- 지번 -->
						<th></th>
					</tr>
				</thead>
				<tbody class="ov">
					<c:if test="${fn:length(resultList) == 0}">					
						<tr>
							<td colspan="6" style="text-align:center;"><spring:message code="info.nodata.msg" /></td>
						</tr>
					</c:if>
					<c:forEach var="resultItem" items="${resultList}" varStatus="status">
						<tr>
							<td><c:out value="${resultItem.sldId}"/></td>
							<td><c:out value="${resultItem.sdNm}"/></td>
							<td><c:out value="${resultItem.sggNm}"/></td>
							<td><c:out value="${resultItem.emdNm}"/></td>
							<td><c:out value="${resultItem.riNm}"/></td>
							<td><c:out value="${resultItem.jibun}"/></td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					<button type="button" class="del-btn" onclick="javascript:fnDeleteWork('<c:out value="${result.id}"/>');"><spring:message code="sysVytEcb.stripLandVO.delete" /></button>
					<button type="button" class="modify-btn" onclick="javascript:fnUpdateWorkView('<c:out value="${result.id}"/>');"><spring:message code="sysVytEcb.stripLand.title" /> <spring:message code="title.update" /></button>
					</sec:authorize>
					<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
				</div>
			</div>
			<div class="Page">
				<c:if test="${not empty paginationInfo}">
				<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPage"/>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script>
function linkPage(pageNo){
	$("input[name=pageSubIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>