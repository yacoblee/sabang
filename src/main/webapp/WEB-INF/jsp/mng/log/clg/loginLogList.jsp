<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle"><spring:message code="mngLogClg.loginLog.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">로그관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/log/clg/selectLoginLogList.do" method="post">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="searchCnd" type="hidden" value="<c:out value='${searchVO.searchCnd}'/>">
				<input name="searchWrd" type="hidden" value="<c:out value='${searchVO.searchWrd}'/>">
				<input name="searchBgnDe" type="hidden" value="<c:out value='${searchVO.searchBgnDe}'/>">
				<input name="searchEndDe" type="hidden" value="<c:out value='${searchVO.searchEndDe}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchCndView" class="Hidden">검색구분</label>
									<select id="searchCndView" name="searchCndView" title="<spring:message code="select.searchCondition" />">
										<option value="0" <c:if test="${empty searchVO.searchCnd || searchVO.searchCnd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if>><spring:message code="mngLogClg.loginLog.loginNm" /></option>
										<option value="2" <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if>><spring:message code="mngLogClg.loginLog.loginIp" /></option>
									</select>
									<label for="searchWrdView" class="Hidden">검색어입력창</label>
									<input type="text" id="searchWrdView" name="searchWrdView" class="mo_mt5 input_null" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value="<c:out value="${searchVO.searchWrd}"/>" />
								</td>
								<th>날짜</th>
								<td>
									<label for="sDate" class="Hidden"><spring:message code="mngLogClg.seachWrd.searchBgnDe" /></label>
									<input type="date" id="sDate" class="dtPicker" name="searchBgnDeView" value="<c:out value='${searchVO.searchBgnDe}'/>" placeholder="YYYYMMDD"/> ~ 
									<label for="eDate" class="Hidden"><spring:message code="mngLogClg.seachWrd.searchEndDe" /></label>
									<input type="date" id="eDate" class="dtPicker" name="searchEndDeView" value="<c:out value='${searchVO.searchEndDe}'/>" placeholder="YYYYMMDD"/>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;"><spring:message code="button.inquire"/></button>
						<button type="button" class="download-btn" onclick="javascript:fnExcelDown(); return false;">다운로드</button>
					</div>
				</div>
			</form>
		</fieldset>
		<div class="BoardList">
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle}<spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 5%;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
				</colgroup>
				<thead>
				<tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="mngLogClg.loginLog.logId" /></th><!-- 로그ID -->
					<th><spring:message code="mngLogClg.loginLog.occrrncDe" /></th><!-- 발생일자 -->
					<th><spring:message code="mngLogClg.loginLog.loginMthd" /></th><!-- 로그유형 -->
					<th><spring:message code="mngLogClg.loginLog.loginNm" /></th><!-- 사용자 -->
				</tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="6"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach items="${resultList}" var="result" varStatus="status">
				<tr>
					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
					<td><c:out value='${result.logId}'/></td>
					<td><c:out value='${fn:substring(result.creatDt,0,10)}'/></td>
					<td><c:out value='${result.loginMthd}'/></td>
					<td><c:out value='${result.loginNm}'/></td>
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
function linkPage(pageNo){
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>					