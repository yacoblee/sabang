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
<c:set var="pageTitle"><spring:message code="sysLssWka.fieldBookDetail.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysLssWka.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역실태조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
			<fieldset>
				<legend class="Hidden">검색테이블</legend>
				<form id="searchForm" action="/sys/lss/wka/fbk/selectFieldBookDetail.do" method="post">
					<input name="smid" type="hidden" value="">
					<input name="id" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
					<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
					<input name="mst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
					<input name="mst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
					<input name="svy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
					<input name="mst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
					<input name="pageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
					<input name="pageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
					<input name="pageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">

					<input name="svyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
					<input name="sd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
					<input name="sgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
					<input name="emd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
					<input name="ri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>
				
					<div class="search">
						<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
							<caption class="Hidden">${pageTitle} 검색영역</caption>
							<tbody>
								<tr>
									<th><spring:message code="sysLssWka.fieldBookItemVO.id"/></th><!-- 조사 ID -->
										<td>
											<label for="sysLssWka.fieldBookItemVO.id" class="Hidden"><spring:message code="sysLssWka.fieldBookItemVO.id"/></label>
											<input type="text" id="svyIdView" value="<c:out value='${searchVO.svyId}'/>" name="svyIdView" class="mo_mt5 input_null"/>
										</td>
									<th><spring:message code="sysLssWka.fieldBookItemVO.addr"/></th><!-- 주소 -->
										<td colspan="5">
											<label for="sdView" class="Hidden"><spring:message code="sysLssWka.fieldBookItemVO.svySd"/></label>
											<select id="sdView" name="sdView" title="<spring:message code="sysLssWka.fieldBookItemVO.svySd"/>" onchange="fncAdministCtprvnChange(event); return false;">
												<option value=""<c:if test="${empty searchVO.sd || searchVO.sd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
												<c:forEach var="sdCodeInfo" items="${sdCodeList}" varStatus="status">
												<option value="<c:out value="${sdCodeInfo.adminCode}"/>"<c:if test="${searchVO.sd eq sdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sdCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
											<label for="sggView" class="Hidden"><spring:message code="sysLssWka.fieldBookItemVO.svySgg"/></label>
											<select id="sggView" name="sggView" title="<spring:message code="sysLssWka.fieldBookItemVO.svySgg"/>" onchange="fncAdministSignguChange(event); return false;">
												<option value=""<c:if test="${empty searchVO.sgg || searchVO.sgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
												<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
												<option value="<c:out value="${sggCodeInfo.adminCode}"/>"<c:if test="${searchVO.sgg eq sggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
											<label for="emdView" class="Hidden"><spring:message code="sysLssWka.fieldBookItemVO.svyEmd"/></label>
											<select id="emdView" name="emdView" title="<spring:message code="sysLssWka.fieldBookItemVO.svyEmd"/>" onchange="fncAdministEmdChange(event); return false;">
												<option value=""<c:if test="${empty searchVO.emd || searchVO.emd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>				
												<c:forEach var="emdCodeInfo" items="${emdCodeList}" varStatus="status">
												<option value="<c:out value="${emdCodeInfo.adminCode}"/>"<c:if test="${searchVO.emd eq emdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${emdCodeInfo.adminNm}"/></option>
												</c:forEach>
 											</select>
											<label for="riView" class="Hidden"><spring:message code="sysLssWka.fieldBookItemVO.svyRi"/></label>
											<select id="riView" name="riView" title="<spring:message code="sysLssWka.fieldBookItemVO.svyRi"/>">
												<option value=""<c:if test="${empty searchVO.ri || searchVO.ri == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
												<c:forEach var="riCodeInfo" items="${riCodeList}" varStatus="status">
												<option value="<c:out value="${riCodeInfo.adminCode}"/>"<c:if test="${searchVO.ri eq riCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${riCodeInfo.adminNm}"/></option>
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
						<th><spring:message code="sysLssWka.fieldBookVO.id" /></th>
						<td><c:out value="${result.id}"/></td>
						<th><spring:message code="sysLssWka.fieldBookVO.create_user" /></th>
						<td><c:out value="${result.mst_create_user}"/></td>
						<th><spring:message code="sysLssWka.fieldBookVO.registered" /></th>
						<td><c:out value="${result.mst_registered}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssWka.fieldBookVO.corpname" /></th>
						<td><c:out value="${result.mst_corpname}"/></td>
						<th><spring:message code="sysLssWka.fieldBookVO.partname" /></th>
						<td><c:out value="${result.mst_partname}"/></td>
						<th><spring:message code="sysLssWka.fieldBookVO.totcnt" /></th>
						<td><c:out value="${result.totcnt}"/></td>
					</tr>
				</table>
			</div>
			<fieldset>
			<form id="listForm" action="/sys/lss/wka/fbk/selectFieldBookDetail.do" method="post">
				<input name="smid" type="hidden" value="">
				<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
				<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
				<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
				<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
				<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
				<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
				<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
				<input name="pageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">

				<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
				<input name="schsd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
				<input name="schsgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
				<input name="schemd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
				<input name="schri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>
								
			</form>
			</fieldset>
			<div class="BoardList">
			<h3>${subTitle}</h3>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					<button style="float:right;" type="button" class="add-btn" onclick="javascript:fnManageCnrsSpceAuthory('<c:out value="${result.id}"/>');"><spring:message code="sysLssWka.stripLand.authorManage" /></button>
					<button style="float:right;" type="button" class="save-btn" onclick="javascript:fnInsertCnrsSpceSldPopup('<c:out value="${result.id}"/>');"><spring:message code="sysLssWka.stripLand.upload" /></button>
				</sec:authorize>
				<sec:authorize access="!hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					<c:if test="${access eq 'ADMIN' }">
						<button style="float:right;" type="button" class="add-btn" onclick="javascript:fnManageCnrsSpceAuthory('<c:out value="${result.id}"/>');"><spring:message code="sysLssWka.stripLand.authorManage" /></button>
					</c:if>
					<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
						<button style="float:right;" type="button" class="save-btn" onclick="javascript:fnInsertCnrsSpceSldPopup('<c:out value="${result.id}"/>');"><spring:message code="sysLssWka.stripLand.upload" /></button>
					</c:if>
				</sec:authorize>
			</span>
			
			<table summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
				<thead>
					<tr>
						<th><spring:message code="sysLssWka.fieldBookItemVO.cnt" /></th>
						<th><spring:message code="sysLssWka.stripLandVO.id" /></th>
						<th><spring:message code="sysLssWka.stripLandVO.type" /></th>
						<th><spring:message code="sysLssWka.stripLandVO.lat" /></th>
						<th><spring:message code="sysLssWka.stripLandVO.lon" /></th>
						<th><spring:message code="sysLssWka.stripLandVO.addr" /></th>
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
							<td><c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> </td>
							<td>
								<div class="BtnGroup">
									<button title="<spring:message code="button.searchDetail"/>" type="button" class="search-btn-m" onclick="javascript:fnFieldBookItemDetail('<c:out value="${result.smid}"/>');"></button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					<button type="button" class="del-btn" onclick="javascript:fnDeleteCnrsSpce('<c:out value="${result.id}"/>');"><spring:message code="title.delete" /></button>
<%-- 					<button type="button" class="modify-btn" onclick="javascript:fnUpdateCnrsSpceView('<c:out value="${result.id}"/>');"><spring:message code="title.update" /></button> --%>
					</sec:authorize>
					<button type="button" class="list-btn" onclick="javascript:fnList('/sys/lss/wka/fbk/selectFieldBookList.do'); return false;"><spring:message code="button.list" /></button>
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