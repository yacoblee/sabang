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
<c:set var="pageTitle"><spring:message code="sysSptSct.reportManage.LssCnlComptReport"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">업무지원</a></li>
		<li><a href="#">완료보고서</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/spt/sct/selectCnlComptReportList.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="file_id" type="hidden" value="">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}검색영역</caption>
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
										<option value="0"<c:if test="${empty searchVO.searchCondition || searchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1"<c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="sysSptSct.reportFile.file_orginl_nm"/></option>
										<option value="2"<c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message	code="sysSptSct.reportFile.file_wrter"/></option>
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
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<select name="pageSelect" onchange="fnSearch();">
					<option value="10"<c:if test="${searchVO.pageUnit == 10}">selected='selected'</c:if>>10개씩 보기</option>
					<option value="30"<c:if test="${searchVO.pageUnit == 30}">selected='selected'</c:if>>30개씩 보기</option>
					<option value="50"<c:if test="${searchVO.pageUnit == 50}">selected='selected'</c:if>>50개씩 보기</option>
					<option value="100"<c:if test="${searchVO.pageUnit == 100}">selected='selected'</c:if>>100개씩 보기</option>
					<option value="150"<c:if test="${searchVO.pageUnit == 150}">selected='selected'</c:if>>150개씩 보기</option>
				</select>
			</span>
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 5%;">
					<col style="width: 10%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 5%;">
					<col style="width: 5%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="sysSptSct.reportFile.no" /></th>
						<th><spring:message code="sysSptSct.reportFile.deptNm" /></th>
						<th><spring:message code="sysSptSct.reportFile.orderAddr" /></th>
						<th><spring:message code="sysSptSct.reportFile.cnstrNm" /></th>
						<th><spring:message code="sysSptSct.reportFile.cnstrYear" /></th>
<%-- 						<th><spring:message code="sysSptSct.reportFile.file_orginl_nm" /></th> --%>
						<th><spring:message code="sysSptSct.reportFile.file_wrter" /></th>
						<th><spring:message code="sysSptSct.reportFile.create_de" /></th>
						<th><spring:message code="sysSptSct.reportFile.manage" /></th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(resultFileList) == 0}">
				<tr>
					<td colspan="8" class="center"><spring:message code="info.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach var="result" items="${resultFileList}" varStatus="status">
 				<tr class="reportArea">
 					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageUnit + status.count}"/></td>
 					<td><c:out value="${result.deptNm}"/></td>
 					<td><c:out value="${result.orderSd}"/> <c:out value="${result.orderSgg}"/></td>
 					<td><c:out value="${result.cnstrName}"/></td>
 					<td><c:out value="${result.cnstrYear}"/></td> 		
<%-- 					<td class="row"><c:out value="${result.file_orginl_nm}"/></td>					 --%>
					<td class="row"><c:out value="${result.file_wrter}"/></td>
					<%-- <td class="row"><fmt:formatNumber value="${result.file_size / 1000000}" pattern="0.00"/> [MB]</td> --%>
					<td class="row"><fmt:formatDate value="${result.create_de}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
					<td class="row">
						<div class="BtnGroup">
				    		<button type="button" class="download-btn-m" onclick="location.href='/sys/spt/sct/selectCnlComptReportDownload.do?gid=<c:out value="${result.gid}"/>'"></button>
				    		<sec:authorize access="isAuthenticated()">
					    		<sec:authorize access="hasAnyRole('ROLE_ADMIN_CNL','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					    			<button type="button" class="del-btn-m" onclick="javascript:fnDeleteRptFile('<c:out value="${result.gid}"/>','CNL')"></button>
					    			<sec:authorize access="!hasAnyRole('ROLE_ADMIN_CNL','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					    				<sec:authorize access="hasAnyRole('ROLE_USER_CNL','ROLE_USER_CNRS','ROLE_USER')">
											<c:set var="name" value="${result.file_wrter}" />
											<c:if test="${name eq userNm}">
												<button type="button" class="del-btn-m" onclick="javascript:fnDeleteRptFile('<c:out value="${result.gid}"/>','CNL')"></button>
											</c:if>
					    				</sec:authorize>
					    			</sec:authorize>				    			
								</sec:authorize>
<%-- 								<sec:authorize access="hasAnyRole('ROLE_USER_CNL','ROLE_USER')"> --%>
<%-- 									<c:set var="name" value="${result.file_wrter}" /> --%>
<%-- 									<c:if test="${name eq userNm}"> --%>
<%-- 										<button type="button" class="del-btn" onclick="javascript:fnDeleteRptFile('<c:out value="${result.gid}"/>','CNL')"><spring:message code="button.delete" /></button> --%>
<%-- 									</c:if> --%>
<%-- 								</sec:authorize> --%>
							</sec:authorize>
				    	</div>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fnRptFilePopup('CNL');"><spring:message code="title.create"/></button>
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
	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").attr("action","<c:url value='/sys/spt/sct/selectCnlComptReportList.do'/>");
		$("#listForm").submit();
	}
</script>