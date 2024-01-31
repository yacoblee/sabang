<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="mngSecRam.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/sec/ram/authorList.do" method="post">
				<input type="hidden" name="pageIndex" value="<c:out value='${authorManageVO.pageIndex}'/>"/>
				<input type="hidden" name="authorCode"/>
				<input name="searchCondition" type="hidden" value="<c:out value='${authorManageVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${authorManageVO.searchKeyword}'/>"/>
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
										<option value="0" <c:if test="${empty authorManageVO.searchCondition || authorManageVO.searchCondition == '0'}">selected="selected"</c:if>>전체보기</option>
										<option value="1" <c:if test="${authorManageVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="mngSecRam.list.authorRollId"/></option>
										<option value="2" <c:if test="${authorManageVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="mngSecRam.list.authorNm"/></option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${authorManageVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
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
					<col style="width: 9%;">
					<col style="width: 33%;">
					<col style="width: 30%;">
					<col style="width: ;">
					<col style="width: 10%;">
<%-- 					<col style="width: 7%;"> --%>
				</colgroup>
				<thead>
				  <tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="mngSecRam.list.authorRollId" /></th><!-- 권한 ID -->
					<th><spring:message code="mngSecRam.list.authorNm" /></th><!-- 권한 명 -->
					<th><spring:message code="mngSecRam.list.authorDc" /></th><!-- 설명 -->
					<th><spring:message code="table.regdate" /></th><!-- 등록일자 -->
					<!--<th><spring:message code="mngSecRam.list.authorRoll" /></th> 롤 정보 -->
				  </tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(authorList) == 0}">
				<tr>
					<td colspan="5"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach var="author" items="${authorList}" varStatus="status">
				<tr>
					<td><c:out value="${(authorManageVO.pageIndex-1) * authorManageVO.pageSize + status.count}"/></td>
					<td><a href="#LINK" onclick="javascript:fncSelectAuthor('<c:out value="${author.authorCode}"/>')"><c:out value="${author.authorCode}"/></a></td>
					<td><c:out value="${author.authorNm}"/></td>
					<td><c:out value="${author.authorDc}"/></td>
					<fmt:parseDate value="${author.authorCreatDe}" var="createDt" pattern="yyyymmdd"/>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${createDt}"/></td>
<%-- 					<td><a href="<c:url value='/mng/sec/rpm/authorRoleList.do'/>?searchCondition=1&searchKeyword=<c:out value="${author.authorCode}"/>" onclick="javascript:fncSelectAuthorRole('<c:out value="${author.authorCode}"/>')"><img src="<c:url value='/images/egovframework/com/cmm/btn/btn_search.gif'/>" width="15" height="15" align="middle" alt="롤 정보"></a></td> --%>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncAddAuthorInsert();"><spring:message code="button.create" /></button>
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