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
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">사방사업</a></li>
		<li><a href="#">사업관리</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/vyt/ecb/sld/selectVytEcbWorkList.do" method="post">
				<input name="id" type="hidden" value="<c:out value='${searchVO.id}'/>">
				<input name="workNm" type="hidden" value="<c:out value='${searchVO.workNm}'/>">
				<input name="workYear" type="hidden" value="<c:out value='${searchVO.workYear}'/>">
				<input name="workType" type="hidden" value="<c:out value='${searchVO.workType}'/>">
				<input name="creatUser" type="hidden" value="<c:out value='${searchVO.creatUser}'/>">
				<input name="creatDt" type="hidden" value="<c:out value='${searchVO.creatDt}'/>">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysVytEcb.workVO.workName"/></th><!-- 사업명 -->
								<td>
									<label for="workNmView" class="Hidden"><spring:message code="sysVytEcb.workVO.workName"/></label>
									<input type="text" id="workNmView" value="<c:out value='${searchVO.workNm}'/>" name="workNmView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysVytEcb.workVO.workType"/></th><!-- 사업종류 -->
								<td>
									<label for="workTypeView" class="Hidden"><spring:message code="sysVytEcb.workVO.workType"/></label>
									<select id="workTypeView" name="workTypeView" title="<spring:message code="sysVytEcb.workVO.workType"/>">
										<option value=""<c:if test="${empty searchVO.workType || searchVO.workType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="workTypeCodeInfo" items="${workTypeCodeList}" varStatus="status">
										<option value="<c:out value="${workTypeCodeInfo.code}"/>"<c:if test="${searchVO.workType eq workTypeCodeInfo.code}">selected="selected"</c:if>><c:out value="${workTypeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysVytEcb.workVO.workYear"/></th><!-- 등록연도 -->
								<td>
									<label for="workYearView" class="Hidden"><spring:message code="sysVytEcb.workVO.workYear"/></label>
									<select id="workYearView" name="workYearView" title="<spring:message code="sysVytEcb.workVO.workYear"/>">
										<option value=""<c:if test="${empty searchVO.workYear || searchVO.workYear == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="workYearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${workYearCodeInfo.workyear}"/>"<c:if test="${searchVO.workYear eq workYearCodeInfo.workyear}">selected="selected"</c:if>><c:out value="${workYearCodeInfo.workyear}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysVytEcb.workVO.creatUser"/></th><!-- 등록자 -->
								<td>
									<label for="creatUserView" class="Hidden"><spring:message code="sysVytEcb.workVO.creatUser"/></label>
									<input type="text" id="creatUserView" value="<c:out value='${searchVO.creatUser}'/>" name="creatUserView" class="mo_mt5 input_null" />
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
					<col style="width: 8%;">
					<col style="width: 40%;">
					<col style="width: 15%;">
					<col style="width: 12%;">
					<col style="width: 12%;">
					<col style="width: 10%;">
					<col style="width: 3%;">					
				</colgroup>
			<thead>
			<tr>				
				<th><spring:message code="table.num" /></th><!-- 번호 -->
				<th><spring:message code="sysVytEcb.workVO.workName" /></th><!-- 사업명 -->
				<th><spring:message code="sysVytEcb.workVO.workType" /></th><!-- 사업종류 -->
				<th><spring:message code="sysVytEcb.workVO.creatUser" /></th><!-- 등록자 -->
				<th><spring:message code="sysVytEcb.workVO.creatDt" /></th><!-- 등록일 -->
				<th><spring:message code="sysVytEcb.workVO.workCnt" /></th><!-- 등록건수 -->
				<th></th>
			</tr>
			</thead>
				<tbody class="ov">
					<c:if test="${fn:length(resultList) == 0}">					
						<tr>
							<td colspan="7"><spring:message code="info.nodata.msg" /></td>
						</tr>
					</c:if>
					<c:forEach var="result" items="${resultList}" varStatus="status">						
						<tr>
							<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageUnit + status.count}"/></td>
							<td><c:out value="${result.workNm}"/></td>
							<td><c:out value="${result.workType}"/></td>
							<td><c:out value="${result.creatUser}"/></td>
							<td><c:out value="${result.creatDt}"/></td>
							<td><c:out value="${result.workCnt}"/></td>
							<td>
								<button title="<spring:message code="button.searchDetail"/>" type="button" class="search-btn-m" onclick="javascript:fnWorkDetail('<c:out value="${result.id}"/>');"></button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<sec:authorize access="hasAnyRole('ROLE_USER_CNRS','ROLE_ADMIN_VYTECB','ROLE_SUB_ADMIN','ROLE_ADMIN')">
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fnInsertWorkPopup();"><spring:message code="button.create" /></button>
				</div>
			</div>
			</sec:authorize>
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
	$("#listForm").attr("action","<c:url value='/sys/vyt/ecb/sld/selectVytEcbWorkList.do'/>");
	$("#listForm").submit();
}
</script>