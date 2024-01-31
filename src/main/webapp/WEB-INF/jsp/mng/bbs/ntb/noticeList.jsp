<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="mngBbs.noticeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">공지사항관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/bbs/ntb/noticeList.do" method="post">
				<input name="pageIndex" type="hidden" value="<c:out value='${noticeVO.pageIndex}'/>"/>
				<input name="searchCondition" type="hidden" value="<c:out value='${noticeVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${noticeVO.searchKeyword}'/>"/>
				<div class="search">
					<table  class="tableWid_L">
						<caption class="Hidden">게시물목록</caption>
						<tbody>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchCondition" class="Hidden">구분</label>
									<select id="searchCondition" name="searchConditionView" title="<spring:message code="mngUmt.userManageSsearch.searchConditioTitle" />">
										<option value="0" <c:if test="${empty noticeVO.searchCondition || noticeVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="mngBbs.noticeSearch.searchConditionAll" /></option>
										<option value="1" <c:if test="${noticeVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="mngBbs.noticeVO.list.nttSj" /></option>
										<option value="2" <c:if test="${noticeVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="mngBbs.noticeVO.list.nttCn" /></option>
										<option value="3" <c:if test="${noticeVO.searchCondition == '3'}">selected="selected"</c:if>><spring:message code="table.reger" /></option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" name="searchKeywordView" class="mo_mt5 input_null" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value="<c:out value="${noticeVO.searchKeyword}"/>" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">검색</button>
					</div>
				</div> 
				<input name="nttId" type="hidden" value="<c:out value='${noticeVO.nttId}'/>">			
			</form>
		</fieldset>
	
		<div class="BoardList">
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
				<col style="width: 10%" />
				<col style="width: 40%" />
				<col style="width: 15%" />
				<col style="width: 20%" />
				<col style="width: 15%" />
			</colgroup>
			<thead>
			<tr>
				<th><spring:message code="table.num" /></th><!-- 번호 -->
				<th><spring:message code="mngBbs.noticeVO.list.nttSj" /></th><!-- 제목 -->
				<th><spring:message code="table.reger" /></th><!-- 작성자명 -->
				<th><spring:message code="table.regdate" /></th><!-- 작성일자 -->
				<th><spring:message code="mngBbs.noticeVO.list.rdcnt" /></th><!-- 조회수 -->
			</tr>
			</thead>
			<tbody class="ov">
			<c:if test="${fn:length(resultList) == 0}">
			<tr>
				<td colspan="5"><spring:message code="common.nodata.msg" /></td>
			</tr>
			</c:if>
			<c:forEach var="result" items="${resultList}" varStatus="status">
			<tr>
			    <td><c:out value="${(noticeVO.pageIndex-1) * noticeVO.pageSize + status.count}"/></td>
			    <td><a href="<c:url value='/mng/bbs/ntb/selectNoticeDetail.do'/>?nttId=${result.nttId}" onClick="fncNoticeDetail('<c:out value="${result.nttId}"/>');return false;"><c:out value="${result.nttSj}"/></a></td>
			    <td><c:out value="${result.frstRegisterId}"/></td>
			    <td><c:out value="${result.frstRegisterPnttm}"/></td>
			    <td><c:out value="${result.rdcnt}"/></td>
			</tr>
			</c:forEach>
			</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncAddNoticeInsert();"><spring:message code="button.create" /></button>
				</div>
			</div>
			<div class="Page">
				<c:if test="${not empty paginationInfo}">
				<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPage"/>
				</c:if>
			</div>
		</div>
	</div>
	<script>
		/* <c:if test="${!empty resultMsg}">alert("<spring:message code="${resultMsg}" />");</c:if> */
		function linkPage(pageNo){
			$("input[name=pageIndex]").val(pageNo);
			$("#listForm").submit();
		}
	</script>
</div>