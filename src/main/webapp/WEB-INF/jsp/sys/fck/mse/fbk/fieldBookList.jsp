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
<c:set var="pageTitle"><spring:message code="sysFckMse.fieldBookList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">계측장비</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/fck/mse/fbk/selectFieldBookList.do" method="post">
				<input name="id" type="hidden" value="<c:out value='${searchVO.id}'/>">
				<input name="mstId" type="hidden" value="">
				<input name="mst_corpname" type="hidden" value="<c:out value='${searchVO.mst_corpname}'/>">
				<input name="mst_partname" type="hidden" value="<c:out value='${searchVO.mst_partname}'/>">
				<input name="svy_year" type="hidden" value="<c:out value='${searchVO.svy_year}'/>">
				<input name="mst_create_user" type="hidden" value="<c:out value='${searchVO.mst_create_user}'/>">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysFckMse.fieldBookVO.corpname"/></th><!-- 회사명 -->
								<td>
									<label for="mst_corpnameView" class="Hidden"><spring:message code="sysFckMse.fieldBookVO.corpname"/></label>
									<select id="mst_corpnameView" name="mst_corpnameView" title="<spring:message code="sysFckMse.fieldBookVO.corpname"/>" class="mo_mt5">
										<option value=""<c:if test="${empty searchVO.mst_corpname || searchVO.mst_corpname == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="ogrnztCodeInfo" items="${ogrnztCodeList}" varStatus="status">
										<option value="<c:out value="${ogrnztCodeInfo.codeNm}"/>"<c:if test="${searchVO.mst_corpname eq ogrnztCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${ogrnztCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysFckMse.fieldBookVO.partname"/></th><!-- 사업지구명 -->
								<td>
									<label for="mst_partnameView" class="Hidden"><spring:message code="sysFckMse.fieldBookVO.partname"/></label>
									<input type="text" id="mst_partname" value="<c:out value='${searchVO.mst_partname}'/>" name="mst_partnameView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysFckMse.fieldBookVO.year"/></th><!-- 등록연도 -->
								<td>
									<label for="svy_yearView" class="Hidden"><spring:message code="sysFckMse.fieldBookVO.year"/></label>
									<select id="svy_yearView" name="svy_yearView" title="<spring:message code="sysFckMse.fieldBookVO.year"/>">
										<option value=""<c:if test="${empty searchVO.svy_year || searchVO.svy_year == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyYearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${svyYearCodeInfo.svyyear}"/>"<c:if test="${searchVO.svy_year eq svyYearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${svyYearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysFckMse.fieldBookVO.id"/></th><!-- 공유방ID -->
								<td>
									<label for="idView" class="Hidden"><spring:message code="sysFckMse.fieldBookVO.id"/></label>
									<input type="text" id="id" value="<c:out value='${searchVO.id}'/>" name="idView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysFckMse.fieldBookVO.create_user"/></th><!-- 등록자 -->
								<td>
									<label for="mst_create_userView" class="Hidden"><spring:message code="sysFckMse.fieldBookVO.create_user"/></label>
									<input type="text" id="mst_create_user" value="<c:out value='${searchVO.mst_create_user}'/>" name="mst_create_userView" class="mo_mt5 input_null" />
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
				<select name="pageSelect" onchange="javascript:fnPagging(); return false;">
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
					<col style="width: 20%;">
					<col style="width: 30%;">
					<col style="width: 10%;">
					<col style="width: 20%;">
					<col style="width: 5%;">					
				</colgroup>
			<thead>
			<tr>				
				<th><spring:message code="sysFckMse.fieldBookVO.id" /></th> <!--공유방ID -->
				<th><spring:message code="sysFckMse.fieldBookVO.corpname" /></th><!-- 회사명 -->
				<th><spring:message code="sysFckMse.fieldBookVO.partname" /></th><!-- 사업지구명 -->
				<th><spring:message code="sysFckMse.fieldBookVO.create_user" /></th><!-- 생성자 -->
				<th><spring:message code="sysFckMse.fieldBookVO.registered" /></th><!-- 생성일 -->
				<th><spring:message code="sysFckMse.fieldBookVO.totcnt" /></th><!-- 조사데이터갯수 -->
				<th></th>
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
							<td><c:out value="${result.mst_corpname}"/></td>
							<td><c:out value="${result.mst_partname}"/></td>
							<td><c:out value="${result.mst_create_user}"/></td>
							<td><c:out value="${result.mst_registered}"/></td>
							<td><c:out value="${result.totcnt}"/></td>
							<td>
								<button title="<spring:message code="button.searchDetail"/>" type="button" class="search-btn-m" onclick="javascript:fnFieldBookDetail('<c:out value="${result.id}"/>');"></button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
<%-- 			<sec:authorize access="hasAnyRole('ROLE_USER_CNRS','ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')"> --%>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fnInsertCnrsSpcePopup();"><spring:message code="button.create" /></button>
				</div>
			</div>
<%-- 			</sec:authorize> --%>
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
	$("#listForm").attr("action","/sys/fck/mse/fbk/selectFieldBookList.do");
	$("#listForm").submit();
}
</script>