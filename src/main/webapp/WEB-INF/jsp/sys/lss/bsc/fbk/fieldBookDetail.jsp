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
<c:set var="pageTitle"><spring:message code="sysLssBsc.fieldBookDetail.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysLssBsc.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
<!-- 		<li><a href="#">기초조사</a></li> -->
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
			<fieldset>
				<legend class="Hidden">검색테이블</legend>
				<form id="searchForm" action="/sys/lss/bsc/fbk/selectFieldBookDetail.do" method="post">
					<input name="id" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
					<input name="mstId" type="hidden" value="<c:out value='${searchVO.MST_ID}'/>">
					<input name="mst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
					<input name="mst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
					<input name="svy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
					<input name="mst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">

					<input name="svyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
					<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
					<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
					<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
					<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
					
					<input name="pageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
					<input name="pageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
					<input name="pageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
						
					<div class="search">
						<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
							<caption class="Hidden">${pageTitle} 검색영역</caption>
							<tbody>
								<tr>
									<th><spring:message code="sysLssBsc.fieldBookItemVO.id"/></th><!-- 조사 ID -->
										<td>
											<label for="sysLssBsc.fieldBookItemVO.id" class="Hidden"><spring:message code="sysLssBsc.fieldBookItemVO.id"/></label>
											<input type="text" id="svyIdView" value="<c:out value='${searchVO.svyId}'/>" name="svyIdView" class="mo_mt5 input_null"/>
										</td>
									<th><spring:message code="sysLssBsc.fieldBookItemVO.addr"/></th><!-- 주소 -->
										<td colspan="5">
											<label for="svySdView" class="Hidden"><spring:message code="sysLssBsc.fieldBookItemVO.sd"/></label>
											<select id="svySdView" name="svySdView" title="<spring:message code="sysLssBsc.fieldBookItemVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
												<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
												<c:forEach var="sdCodeInfo" items="${sdCodeList}" varStatus="status">
												<option value="<c:out value="${sdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq sdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sdCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
											<label for="svySggView" class="Hidden"><spring:message code="sysLssBsc.fieldBookItemVO.sgg"/></label>
											<select id="svySggView" name="svySggView" title="<spring:message code="sysLssBsc.fieldBookItemVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
												<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
												<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
												<option value="<c:out value="${sggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq sggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
											<label for="svyEmdView" class="Hidden"><spring:message code="sysLssBsc.fieldBookItemVO.emd"/></label>
											<select id="svyEmdView" name="svyEmdView" title="<spring:message code="sysLssBsc.fieldBookItemVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
												<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>				
												<c:forEach var="emdCodeInfo" items="${emdCodeList}" varStatus="status">
												<option value="<c:out value="${emdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq emdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${emdCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
											<label for="svyRiView" class="Hidden"><spring:message code="sysLssBsc.fieldBookItemVO.ri"/></label>
											<select id="svyRiView" name="svyRiView" title="<spring:message code="sysLssBsc.fieldBookItemVO.ri"/>">
												<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
												<c:forEach var="riCodeInfo" items="${riCodeList}" varStatus="status">
												<option value="<c:out value="${riCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq riCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${riCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
 										</td>
								</tr>
							</tbody>
						</table>
						<div class="searchdiv">
							<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSubSearch(); return false;">검색</button>
						</div>
					</div>
				</form>
			</fieldset>	
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
			<fieldset>
			<form id="listForm" action="/sys/lss/bsc/fbk/selectFieldBookDetail.do" method="post">
				<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
				<input name="mstId" type="hidden" value="<c:out value='${searchVO.MST_ID}'/>">
				<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
				<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
				<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
				<input name="schpageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
				
				<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
				<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>							
			</form>
			</fieldset>
			<div class="BoardList">
			<h3>${subTitle}</h3>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong><sec:authorize access="hasAnyRole('ROLE_USER_CNRS','ROLE_ADMIN_BSC','ROLE_SUB_ADMIN','ROLE_ADMIN')"><button style="float:right;" type="button" class="save-btn" onclick="javascript:fnInsertCnrsSpceSldPopup('<c:out value="${result.id}"/>');"><spring:message code="sysLssBsc.stripLand.upload" /></button></sec:authorize></span>
			
			<table summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
				<thead>
					<tr>
						<th><spring:message code="sysLssBsc.fieldBookItemVO.cnt" /></th>
						<th><spring:message code="sysLssBsc.stripLandVO.id" /></th>
						<th><spring:message code="sysLssBsc.stripLandVO.type" /></th>
						<th><spring:message code="sysLssBsc.stripLandVO.lat" /></th>
						<th><spring:message code="sysLssBsc.stripLandVO.lon" /></th>
						<th><spring:message code="sysLssBsc.svyComptVO.region" /></th>
						<th><spring:message code="sysLssBsc.stripLandVO.addr" /></th>
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
							<td><c:out value="${result.region1}"/> <c:out value="${result.region2}"/></td>							
							<td><c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN_BSC','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					<button type="button" class="del-btn" onclick="javascript:fnDeleteCnrsSpce('<c:out value="${result.id}"/>');"><spring:message code="title.delete" /></button>
					<button type="button" class="modify-btn" onclick="javascript:fnUpdateCnrsSpceView('<c:out value="${result.id}"/>');"><spring:message code="title.update" /></button>
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
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("input[name=pageSubIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>