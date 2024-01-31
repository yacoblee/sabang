<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngPrm.fileNmSearch.title"/></c:set><!-- 프로그램파일명 검색 -->
<div id="contents">
	<fieldset>
		<legend class="Hidden">검색테이블</legend>
		<form name="progrmManageForm" action ="<c:url value='/mng/prm/programListSearchPopup.do'/>" method="post">
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
			<div class="search">
				<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} 영역</caption>
					<colgroup>
						<col style="width: 10%;">
						<col style="width: 90%;">
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="mngPrm.fileNmSearch.progrmNm" /></th><!-- 프로그램명 -->
							<td>
								<label for="searchPopupKeyword" class="Hidden">구분</label>
								<input type="text" id="searchPopupKeyword" value="<c:out value='${searchVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
							</td>
						</tr>
					</tbody>
				</table>
				<div class="searchdiv">
					<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSelectProgramListSearch(); return false;">검색</button>
				</div>
			</div>
		</form>
	</fieldset>
	<div class="BoardList">
		<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
		<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
			<colgroup>
				<col style="width:50%" />
				<col style="width:50%" />
			</colgroup>
			<thead>
				<tr>
				   <th scope="col"><spring:message code="mngPrm.fileNmSearch.progrmFileNm"/></th><!-- 프로그램파일명 -->
				   <th scope="col"><spring:message code="mngPrm.fileNmSearch.progrmNm"/></th><!-- 프로그램명 -->
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(list_progrmmanage) == 0}">
				<tr>
					<td colspan="2"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach var="result" items="${list_progrmmanage}" varStatus="status">
				<tr>
					<td>
				    	<span class="link">
				    		<a href="#LINK" onclick="choisProgramListSearch('<c:out value="${result.progrmFileNm}"/>'); return false;"><c:out value="${result.progrmFileNm}"/></a>
				    	</span>
				    </td>
				    <td><c:out value="${result.progrmKoreanNm}"/></td>
				</tr>
			 	</c:forEach>
			</tbody>
		</table>
		<div class="Page">
			<c:if test="${not empty paginationInfo}">
			<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPopupPage"/>
			</c:if>
		</div>
	</div>
</div>