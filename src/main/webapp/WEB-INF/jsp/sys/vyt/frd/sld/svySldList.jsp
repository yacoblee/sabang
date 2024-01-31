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
	<h3>${pageTitle}<spring:message code="title.management"/></h3>
	<div id="contents">
		<fieldset>
			<legend	class="Hidden">검색테이블</legend>
			<form id="searchForm" action="/sys/vyt/frd/sld/selectFrdSldList.do" method="post">
				<input name="sld_nm" type="hidden" value="<c:out value='${searchVO.sld_nm}'/>">
				<input name="sld_create_user" type="hidden" value="<c:out value='${searchVO.sld_create_user}'/>">
				<input name="svy_year" type="hidden" value="<c:out value='${searchVO.svy_year}'/>">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysVytFrd.workVO.workName"/></th><!-- 사업명 -->
								<td>
									<label for="sld_nm" class="Hidden"><spring:message code="sysVytFrd.workVO.workName"/></label>
									<input type="text" name="sld_nmView" class="mo_mt5 input_null" value="<c:out value='${searchVO.sld_nm}'/>" />
								</td>
								<th><spring:message code="sysVytFrd.workVO.creatUser"/></th><!-- 등록자 -->
								<td>
									<label for="" class="Hidden"><spring:message code="sysVytFrd.workVO.creatUser"/></label>
									<input type="text" name="sld_create_userView" class="mo_mt5 input_null" value="<c:out value='${searchVO.sld_create_user}'/>" />
								</td>
								<th><spring:message code="sysVytFrd.workVO.workYear"/></th><!-- 등록연도 -->
								<td>
									<label for="svy_yearView" class="Hidden"><spring:message code="sysVytFrd.workVO.workYear"/></label>
									<select id="svy_yearView" name="svy_yearView" title="<spring:message code="sysVytFrd.workVO.workYear"/>">
										<option value=""<c:if test="${empty searchVO.svy_year || searchVO.svy_year == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyYearCodeInfo" items="${yearCodeList}" varStatus="status">
											<option value="<c:out value="${svyYearCodeInfo.svyyear}"/>"<c:if test="${searchVO.svy_year eq svyYearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${svyYearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">검색</button>
					</div>
				</div>
			</form>
		</fieldset>
		<div class="BoardList">
			<fieldset>
				<form id="listForm" action="/sys/vyt/frd/sld/selectFrdSldList.do" method="post">
					<input name="id" type="hidden" value=""/>
					<input name="sld_nm" type="hidden" value="<c:out value='${searchVO.sld_nm}'/>">
					<input name="sld_create_user" type="hidden" value="<c:out value='${searchVO.sld_create_user}'/>">
					<input name="svy_year" type="hidden" value="<c:out value='${searchVO.svy_year}'/>">
					<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
					<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				</form>
			</fieldset>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<select name="pageSelect" onchange="fnSearch();">
					<option value="10"<c:if test="${searchVO.pageUnit == 10}">selected='selected'</c:if>>10개씩 보기</option>
					<option value="30"<c:if test="${searchVO.pageUnit == 30}">selected='selected'</c:if>>30개씩 보기</option>
					<option value="50"<c:if test="${searchVO.pageUnit == 50}">selected='selected'</c:if>>50개씩 보기</option>
					<option value="100"<c:if test="${searchVO.pageUnit == 100}">selected='selected'</c:if>>100개씩 보기</option>
					<option value="150"<c:if test="${searchVO.pageUnit == 150}">selected='selected'</c:if>>150개씩 보기</option>
				</select>
			</span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 40%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
				<tr>
					<th><spring:message code="sysVytFrd.workVO.number"/></th><!-- 번호 -->
					<th><spring:message code="sysVytFrd.workVO.workName"/></th><!-- 사업명 -->
					<th><spring:message code="sysVytFrd.workVO.creatUser"/></th><!-- 등록자 -->
					<th><spring:message code="sysVytFrd.workVO.creatDt"/></th><!-- 등록일 -->
					<th><spring:message code="sysVytFrd.workVO.workCnt"/></th><!-- 등록건수 -->
					<th></th><!-- 조회버튼 -->
				</tr>
				</thead>
				<tbody class="ov">
					<c:if test="${fn:length(resultList) == 0}">					
							<tr>
								<td colspan="8"><spring:message code="info.nodata.msg" /></td>
							</tr>
					</c:if>
					<c:forEach var="result" items="${resultList}" varStatus="status">						
						<tr>
							<td><c:out value="${result.id}"/></td>
							<td><c:out value="${result.sld_nm}"/></td>
							<td><c:out value="${result.sld_create_user}"/></td>
							<td><c:out value="${result.creat_dt}"/></td>
							<td><c:out value="${result.totcnt}"/></td>
							<td>
								<div class="BtnGroup">
									<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fnSvySldInfoDetail('<c:out value="${result.id}"/>');"></button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncStripLandInsertView(); return false;"><spring:message code="button.create" /></button>
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
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>