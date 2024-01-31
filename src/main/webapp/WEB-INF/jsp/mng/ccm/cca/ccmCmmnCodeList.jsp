<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="mngCcmCca.cmmnCodeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">공통코드관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/ccm/cca/selectCcmCmmnCodeList.do" method="post">
				<input name="codeId" type="hidden" value="">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>"/>
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
								<th>구분</th>
								<td>
									<label for="searchCondition" class="Hidden">구분</label>
									<select id="searchCondition" name="searchConditionView" title="<spring:message code="title.searchCondition" />">
										<option value="0" <c:if test="${empty searchVO.searchCondition || searchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="mngCcmCca.cmmnCodeVO.codeId"/></option>
										<option value="2" <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="mngCcmCca.cmmnCodeVO.codeIdNm"/></option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
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
					<col style="width: 40%;">
					<col style="width: 13%;">
					<col style="width: 40%;">
					<col style="width: 13%;">
				</colgroup>
				<thead>
				  <tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="mngCcmCca.cmmnCodeVO.clCodeNm" /></th><!-- 분류코드명 -->
					<th><spring:message code="mngCcmCca.cmmnCodeVO.codeId" /></th><!-- 코드ID -->
					<th><spring:message code="mngCcmCca.cmmnCodeVO.codeIdNm"/></th><!-- 코드명 -->
					<th><spring:message code="mngCcmCca.cmmnCodeVO.useAt" /></th><!-- 사용여부 -->
				  </tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="5"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
				<tr>
					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
					<td><c:out value='${resultInfo.clCodeNm}'/></td>
					<td><a href="<c:url value='/mng/ccm/cca/selectCcmCmmnCodeDetail.do'/>?codeId=${resultInfo.codeId}" onClick="fncCodeDetail('<c:out value="${resultInfo.codeId}"/>');return false;"><c:out value='${fn:substring(resultInfo.codeId, 0, 40)}'/></a></td>
					<td><a href="<c:url value='/mng/ccm/cca/selectCcmCmmnCodeDetail.do'/>?codeId=${resultInfo.codeId}" onClick="fncCodeDetail('<c:out value="${resultInfo.codeId}"/>');return false;"><c:out value='${fn:substring(resultInfo.codeIdNm, 0, 40)}'/></a></td>
					<td><c:out value='${resultInfo.useAt}'/></td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncAddCmmnCodeInsert();"><spring:message code="button.create" /></button>
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