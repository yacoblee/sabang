<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysVytFrd.stripLand.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle}<spring:message code="title.management"/> <spring:message code="title.detail"/></h3>
	<div id="contents">
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tr>
					<th><spring:message code="sysVytFrd.stripLandVO.workName" /></th>
					<td><c:out value="${result.sld_nm}"/></td>
					<th><spring:message code="sysVytFrd.stripLandVO.writer" /></th>
					<td><c:out value="${result.sld_create_user}"/></td>
				</tr>
				<tr>
					<th><spring:message code="sysVytFrd.stripLandVO.createDt" /></th>
					<td><c:out value="${result.creat_dt}"/></td>
					<th><spring:message code="sysVytFrd.stripLandVO.sldCnt" /></th>
					<td><c:out value="${result.totcnt}"/></td>
				</tr>
			</table>
		</div>
		<fieldset>
			<form id="searchForm" action="/sys/vyt/frd/sld/selectFrdSldinfo.do" method="post">
				<input name="schid" type="hidden" value="<c:out value='${result.id}'/>">
				<input name="schsmid" type="hidden" value="">
				<input name="schsld_nm" type="hidden" value="<c:out value='${searchMap.schsldNm}'/>">
				<input name="schsld_create_user" type="hidden" value="<c:out value='${searchMap.schsld_create_user}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
				<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
			</form>
		</fieldset>
		<fieldset>
			<form id="listForm" action="/sys/vyt/frd/sld/selectFrdSldinfo.do" method="post">
				<input name="id" type="hidden" value="${result.id}">
				<input name="smid" type="hidden" value="">
				<input name="schsld_nm" type="hidden" value="<c:out value='${searchMap.schsldNm}'/>">
				<input name="schsld_create_user" type="hidden" value="<c:out value='${searchMap.schsld_create_user}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
				<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
			</form>
		</fieldset>
		<div class="BoardList">
		<h3>${pageTitle} <spring:message code="title.list"/></h3>
		<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
			<button style="float:right;" type="button" class="save-btn" onclick="javascript:fncAddStripLandInsertView('<c:out value="${result.id}"/>');"><spring:message code="sysFckApr.stripLand.upload" /></button>
		</span>
		<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
			<thead>
				<tr>
					<th>번호</th>
					<th>조사ID</th>
					<th>임도종류</th>
					<th>예정거리</th>
					<th>소재지</th>
					<th></th>
				</tr>
			</thead>
			<tbody class="ov">
				<c:if test="${fn:length(resultList) == 0}">					
					<tr>
						<td colspan="6" style="text-align:center;"><spring:message code="info.nodata.msg" /></td>
					</tr>
				</c:if>
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<tr>
						<td><c:out value="${(searchVO.pageSubIndex-1) * searchVO.pageSize + status.count}"/></td>
						<td><c:out value="${result.sldlabel}"/></td>
						<td><c:out value="${result.frdtype}" /></td>
						<td><fmt:formatNumber pattern="0.##" value="${result.schdst}" />km</td>
						<td><c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> </td>
						<td>
							<div class="BtnGroup">
								<button type="button" class="search-btn-m" onclick="fnSvySldInfoDetailOne('<c:out value="${result.smid}"/>');" title="상세보기"></button>
								<button type="button" class="process-btn-m" onclick="fnSldAnalPopup('<c:out value="${result.smid}"/>');" title="대상지분석"></button>
<%-- 								<button title="분석파일 일괄 <spring:message code="title.download"/>" class="download-btn-m" onclick="javascript:fncDownloadAnalAll('<c:out value="${result.smid}"/>'); return false;"></button> --%>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="del-btn" onclick="javascript:fnDeleteSldSpce('<c:out value="${result.id}"/>');">${pageTitle}<spring:message code="button.delete" /></button>
					<button type="button" class="list-btn" onclick="javascript:fnList('/sys/vyt/frd/sld/selectFrdSldList.do'); return false;">목록</button>
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
var clicked = false;
	function linkPage(pageNo){
		if(!clicked){
			clicked = true;
			var hiddens = $("#searchForm").find("input[name*=sch]");
			$("#searchForm input[name=schpageSubIndex]").val(pageNo);
			$.each(hiddens,function(idx,elm){
				$("#searchForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
			});
			$("#searchForm").submit();
		}
	}
</script>