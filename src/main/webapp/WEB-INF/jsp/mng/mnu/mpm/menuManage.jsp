<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="mngMnuMpm.menuManage.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="<c:url value='/mng/mnu/mpm/menuManageSelect.do'/>" method="post">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<input name="checkedMenuNoForDel" type="hidden" />
				<input name="req_menuNo" type="hidden"  />
				<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th><spring:message code="mngMnuMpm.menuManage.menuNm"/></th><!-- 메뉴명 -->
								<td>
									<label for="searchKeyword" class="Hidden"><spring:message code="mngMnuMpm.menuManage.menuNm"/></label>
									<input type="text" id="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" title="<spring:message code="title.searchCondition"/>"/><!-- 검색조건 -->
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
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width:30px" />
					<col style="width:100px" />
					<col style="width:120px" />
					<col style="width:200px" />
					<col style="width:167px" />
					<col style="width:100px" />
					<col style="width:30px">
				</colgroup>
				<thead>
					<tr>
<!-- 					   <th scope="col"><input type="checkbox" name="checkAll" class="check2" onclick="fCheckAll();" title="전체선택"/></th>전체선택 -->
						<th><spring:message code="table.num" /></th><!-- 번호 -->
						<th scope="col"><spring:message code="mngMnuMpm.menuManage.menuNo"/></th><!-- 메뉴ID -->
						<th scope="col"><spring:message code="mngMnuMpm.menuManage.menuNmHn"/></th><!-- 메뉴한글명 -->
						<th scope="col"><spring:message code="mngMnuMpm.menuManage.progrmFileNm"/></th><!-- 프로그램파일명 -->
						<th scope="col"><spring:message code="mngMnuMpm.menuManage.menuDc"/></th><!-- 메뉴설명 -->
						<th scope="col"><spring:message code="mngMnuMpm.menuManage.upperMenuId"/></th><!-- 상위메뉴ID -->
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="result" items="${list_menumanage}" varStatus="status">
					<tr>
<!-- 						<td> -->
<!-- 							<input type="checkbox" name="checkField" class="check2" title="선택"/> -->
<%-- 							<input name="checkMenuNo" type="hidden" value="<c:out value='${result.menuNo}'/>"/> --%>
<!-- 					    </td> -->
						<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
					    <td><c:out value="${result.menuNo}"/></td>
					    <td><c:out value="${result.menuNm}"/></td>
					    <td><c:out value="${result.progrmFileNm}"/></td>
					    <td><c:out value="${result.menuDc}"/></td>
					    <td><c:out value="${result.upperMenuId}"/></td>
					    <td><button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="fncMenuManageDetail('<c:out value="${result.menuNo}"/>'); return false;"></button></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncmenuBndeRegistView(); return false;"><spring:message code="button.bulkUpload" /></button>
					<button type="button" class="write-btn" onclick="javascript:fncInsertMenuManageView(); return false;"><spring:message code="button.create" /></button>
<%-- 					<button type="button" class="del-btn" onclick="javascript:fDeleteMenuList(); return false;"><spring:message code="button.delete" /></button> --%>
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
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
function linkPage(pageNo){
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>