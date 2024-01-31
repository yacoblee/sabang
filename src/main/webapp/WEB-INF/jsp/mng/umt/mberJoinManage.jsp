<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="mngUmt.userJoinManage.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">사용자관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/ujm/mberJoinManage.do" method="post">
				<input name="uniqId" type="hidden" value=""/>
				<input name="pageIndex" type="hidden" value="<c:out value='${userSearchVO.pageIndex}'/>"/>
				<input name="searchCondition" type="hidden" value="<c:out value='${userSearchVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${userSearchVO.searchKeyword}'/>"/>
				<div class="search">
					<table  class="tableWid_L">
						<caption class="Hidden">사용자목록</caption>
						<tbody>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchConditionView" class="Hidden">구분</label>
									<select id="searchConditionView" name="searchConditionView" title="<spring:message code="mngUmt.userManageSsearch.searchConditioTitle" />">
										<option value="0" <c:if test="${empty userSearchVO.searchCondition || userSearchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="mngUmt.userManageSsearch.searchConditionAll" /></option>
										<option value="1" <c:if test="${userSearchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="mngUmt.userManageList.id" /></option>
										<option value="2" <c:if test="${userSearchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="mngUmt.userManageList.name" /></option>
									</select>
									<label for="searchKeywordView" class="Hidden">구분</label>
									<input type="text" id="searchKeywordView" name="searchKeywordView" class="mo_mt5 input_null" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value="<c:out value="${userSearchVO.searchKeyword}"/>" />
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
			<table summary="<spring:message code="common.summary.list"/>">
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width:5%" />
						<col style="width:20%" />
						<col style="width:20%" />
						<col style="width:20%" />
						<col style="width:15%" />
						<col style="width:20%" />
				</colgroup>
				<thead>
				<tr>
				<th><spring:message code="table.num" /></th><!-- 번호 -->
				<th><spring:message code="mngUmt.userManageList.id" /></th><!--아이디 -->
				<th><spring:message code="mngUmt.userManageList.name" /></th><!-- 사용자이름 -->
				<th><spring:message code="mngUmt.userManageList.phone" /></th><!-- 전화번호 -->
				<th><spring:message code="table.regdate" /></th><!-- 등록일 -->
				<th><spring:message code="mngUmt.userManageSsearch.sbscrbSttusA" /></th><!-- 등록일 -->
				
			</tr>
				</thead>
				<tbody class="ov">
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="8"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
				    <td><c:out value="${(userSearchVO.pageIndex-1) * userSearchVO.pageSize + status.count}"/></td>
				    <td><c:out value="${result.userId}"/></td>
				    <td><c:out value="${result.userNm}"/></td>
				    <td><c:out value="${result.userTelno}"/></td>
				    <td><c:out value="${fn:substring(result.sbscrbDe,0,10)}"/></td>
				    <td>
				    	<div class="BtnGroup">
							<button type="button" class="modify-btn" onclick="javascript:fnAcceptJoin('<c:out value="${result.userId}"/>','<c:out value="${result.uniqId}"/>'); return false;"><spring:message code="mngUmt.userManageSsearch.sbscrbSttusP" /></button>
							<button type="button" class="del-btn" onclick="javascript:fnDeleteJoin('<c:out value="${result.userId}"/>','<c:out value="${result.uniqId}"/>'); return false;"><spring:message code="mngUmt.userManageSsearch.sbscrbSttusD" /></button>
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
	<script>
		<c:if test="${!empty resultMsg}">alert("<spring:message code="${resultMsg}" />");</c:if>
		function linkPage(pageNo){
			$("input[name=pageIndex]").val(pageNo);
			$("#listForm").attr("action","<c:url value='/mng/ujm/mberJoinManage.do'/>");
			$("#listForm").submit();
		}
	</script>
</div>