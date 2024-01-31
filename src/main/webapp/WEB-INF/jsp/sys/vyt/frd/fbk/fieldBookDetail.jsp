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
<c:set var="pageTitle"><spring:message code="sysVytFrd.fieldBookDetail.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysVytFrd.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">	
		<div class="BoardDetail">
			<input type="hidden" name="mst_create_user" value="<c:out value='${result.mst_create_user}'/>">
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
		<fieldset>
			<form id="searchForm" action="/sys/vyt/frd/fbk/selectFieldBookDetail.do" method="post">
				<input name="schmst_partname" type="hidden" value="<c:out value='${searchMap.schmst_partname}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
				<input name="schid" type="hidden" value="<c:out value='${searchMap.schid}'/>">
				<input name="schmst_create_user" type="hidden" value="<c:out value='${searchMap.schmst_create_user}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
				<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
				
				<input name="schmst_id" type="hidden" value="<c:out value='${searchVO.mst_id}'/>">
			</form>
		</fieldset>
		<fieldset>
			<form id="listForm" action="/sys/vyt/frd/fbk/selectFieldBookDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
				<input name="schmst_id" type="hidden" value="<c:out value='${searchVO.mst_id}'/>">
				<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
				<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
				<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
				<input name="pageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
			</form>
		</fieldset>
		<div class="BoardList">
		<h3>${subTitle}</h3>
		<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
			<button style="float:right;" type="button" class="add-btn" onclick="javascript:fnManageCnrsSpceAuthory('<c:out value="${searchVO.mst_id}"/>');"><spring:message code="sysVytFrd.stripLand.authorManage" /></button>
			<button style="float:right;" type="button" class="save-btn" onclick="javascript:fnInsertCnrsSpceSldPopup('<c:out value="${searchVO.mst_id}"/>');"><spring:message code="sysVytFrd.stripLand.title"/><spring:message code="title.create"/></button></span>
		<table summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
			<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
			<thead>
				<tr>
					<th><spring:message code="sysVytFrd.workVO.number" /></th>
					<th><spring:message code="sysVytFrd.svyComptVO.svyId" /></th>
					<th><spring:message code="sysVytFrd.svyComptVO.type" /></th>
					<th><spring:message code="sysVytFrd.stripLandVO.lat" /></th>
					<th><spring:message code="sysVytFrd.stripLandVO.lon" /></th>
					<th><spring:message code="sysVytFrd.stripLandVO.region" /></th>
					<th><spring:message code="sysVytFrd.stripLandVO.addr" /></th>
					<th></th>
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
							<td><c:out value="${(searchVO.pageSubIndex-1) * searchVO.pageSize + status.count}"/></td>
							<td><c:out value="${result.id}"/></td>
							<td><c:out value="${result.type}"/></td>
							<td><c:out value="${result.lat}" /></td>
							<td><c:out value="${result.lon}" /></td>
							<td><c:out value="${result.compentauth}"/></td>							
							<td><c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> </td>
							<td>
								<div class="BtnGroup">
									<button type="button" class="search-btn-m" onclick="fnSelectFieldBookDetailOne('<c:out value="${result.gid}"/>');"></button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="del-btn" onclick="javascript:fnDeleteCnrsSpce('<c:out value="${result.id}"/>');"><spring:message code="sysVytFrd.fieldBookList.title" /> <spring:message code="title.delete" /></button>
					<button type="button" class="list-btn" onclick="javascript:fnList('/sys/vyt/frd/fbk/selectFrdFieldBookList.do'); return false;"><spring:message code="button.list" /></button>
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
	var hiddens = $("#searchForm").find("input[name*=sch]");
	$("#searchForm input[name=schpageSubIndex]").val(pageNo);
	$.each(hiddens,function(idx,elm){
		$("#searchForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#searchForm").submit();
}
</script>