<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecUrm.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="${pageContext.request.contextPath}/mng/sec/urm/userAuthorList.do" method="post">
				<input type="hidden" name="uniqId"/>
				<input type="hidden" name="pageIndex" value="<c:out value='${userAuthorVO.pageIndex}'/>"/>
				<input type="hidden" name="searchCondition" value="<c:out value='${userAuthorVO.searchCondition}'/>"/>
				<input type="hidden" name="searchKeyword" value="<c:out value='${userAuthorVO.searchKeyword}'/>"/>
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchCondition" class="Hidden">구분</label>
									<select id="searchCondition" name="searchConditionView">
										<option value="0" <c:if test="${empty userAuthorVO.searchCondition || userAuthorVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${userAuthorVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="mngSecUrm.list.id" /></option>
										<option value="2" <c:if test="${userAuthorVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="mngSecUrm.list.name" /></option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${userAuthorVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;"><spring:message code="button.inquire"/></button>
					</div>
					
				</div>
			</form>
		</fieldset>
		
		<div class="BoardList">
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 5%;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="table.num" /></th><!-- 번호 -->
						<th><spring:message code="mngSecUrm.list.id" /></th><!-- 아이디 -->
						<th><spring:message code="mngSecUrm.list.name" /></th><!-- 사용자명 -->
						<th><spring:message code="mngSecUrm.list.authorNm" /></th><!-- 권한명 -->
						<th><spring:message code="mngSecUrm.list.manage" /></th><!-- 관리 -->
				  	</tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(userAuthorList) == 0}">
					<tr>
						<td colspan="5"><spring:message code="common.nodata.msg" /></td>
					</tr>
					</c:if>
					<c:forEach var="userAuthor" items="${userAuthorList}" varStatus="status">
					<tr>
						<td><c:out value="${(userAuthorVO.pageIndex-1) * userAuthorVO.pageSize + status.count}"/></td>
						<td><c:out value="${userAuthor.userId}"/></td>
						<td><c:out value="${userAuthor.userNm}"/></td>
						<td><c:out value="${userAuthor.authorNm}"/></td>
						<td>
						    <div class="BtnGroup">
								<button type="button" class="modify-btn" onclick="javascript:fncUpdateUserAuthorView('<c:out value="${userAuthor.uniqId}"/>'); return false;"><spring:message code="title.update" /></button>
						    </div>
					    </td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
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