<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="mngMnuMcm.menuCreatManage.title"/></c:set><!-- 메뉴생성관리 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/mnu/mcm/menuCreatManageSelect.do" method="post">
				<input name="checkedMenuNoForDel" type="hidden" />
				<input name="authorCode" type="hidden" />
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<input type="hidden" name="req_menuNo">
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th><spring:message code="mngMnuMcm.menuCreatManage.authCode" /></th><!-- 보안설정대상ID -->
								<td>
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
					<col style="width:20%" />
					<col style="width:20%" />
					<col style="width:20%" />
					<col style="width:20%" />
					<col style="width:20%" />
				</colgroup>
				<thead>
					<tr>
					   <th scope="col"><spring:message code="mngMnuMcm.menuCreatManage.authCode" /></th><!-- 권한코드 -->
					   <th scope="col"><spring:message code="mngMnuMcm.menuCreatManage.authName" /></th><!-- 권한명 -->
					   <th scope="col"><spring:message code="mngMnuMcm.menuCreatManage.authDesc" /></th><!-- 권한 설명 -->
					   <th scope="col"><spring:message code="mngMnuMcm.menuCreatManage.creationStatus" /></th><!-- 메뉴생성여부 -->
					   <th scope="col"><spring:message code="mngMnuMcm.menuCreatManage.createMenu" /></th><!-- 메뉴생성 -->
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${list_menumanage}" varStatus="status">
					<tr>
					    <td><c:out value="${result.authorCode}"/></td>
					    <td><c:out value="${result.authorNm}"/></td>
					    <td><c:out value="${result.authorDc}"/></td>
					    <td>
						<c:if test="${result.chkYeoBu > 0}">Y</c:if>
			        	<c:if test="${result.chkYeoBu == 0}">N</c:if>
					    </td>
					    <td>
					    	<button type="button" class="write-btn" onclick="javascript:selectMenuCreat('<c:out value="${result.authorCode}"/>'); return false;">메뉴생성</button>
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
<c:if test="${!empty resultMsg}">alert("<c:out value='${resultMsg}'/>");</c:if>

function linkPage(pageNo){
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>