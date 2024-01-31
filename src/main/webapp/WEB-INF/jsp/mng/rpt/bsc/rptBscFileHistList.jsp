<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="pageTitle"><spring:message code="mngRptBsc.reportManage.LssBscReportHist"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">보고서관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/rpt/bsc/rptBscFileHistList.do" method="post">
				<input name="id" type="hidden" value="">
				<input name="pageIndex" type="hidden" value="<c:out value='${sptRptBscFileVO.pageIndex}'/>">
				<input name="searchCondition" type="hidden" value="<c:out value='${sptRptBscFileVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${sptRptBscFileVO.searchKeyword}'/>"/>
				<div class="search">
					<table class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
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
										<option value="0" <c:if test="${empty sptRptBscFileVO.searchCondition || sptRptBscFileVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${sptRptBscFileVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="mngRptBsc.reportManage.reprt_id"/></option>
										<option value="2" <c:if test="${sptRptBscFileVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="mngRptBsc.reportManage.file_id"/></option>
										<option value="3" <c:if test="${sptRptBscFileVO.searchCondition == '3'}">selected="selected"</c:if>><spring:message code="mngRptBsc.reportManage.file_orginl_nm"/></option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${sptRptBscFileVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
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
					<col style="width: 15%;">
					<col style="width: 20%;">
					<col style="width: 15%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
					<col style="width: 20%;">
					<col style="width: 10%;">
				</colgroup>
			<thead>
			<tr>
				<!-- <th><input type="checkbox"  id="reprt_all_check" onclick="javascript:fnSelectAllCheck();" /></th> -->
				<th><spring:message code="mngRptBsc.reportManage.reprt_id" /></th> <!--조사ID -->
				<th><spring:message code="mngRptBsc.reportManage.file_id" /></th><!-- 파일ID -->
				<th><spring:message code="mngRptBsc.reportManage.file_orginl_nm" /></th><!-- 파일명 -->
				<th><spring:message code="mngRptBsc.reportManage.file_wrter" /></th>
				<th><spring:message code="mngRptBsc.reportManage.file_size" /></th><!-- 파일용량 -->
				<th><spring:message code="mngRptBsc.reportManage.create_de" /></th><!-- 등록일자 -->
				<th><spring:message code="mngRptBsc.reportManage.download" /></th><!-- 다운로드 -->
			</tr>
			</thead>
 			<tbody class="ov">
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="8"><spring:message code="info.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<%-- <td><input type="checkbox" class="reprt_check"value="<c:out value="${result.file_id}"/>"></td> --%>
					<td><c:out value="${result.reprt_id}"/></td>
					<td><c:out value="${result.file_id}"/></td>
					<td><c:out value="${result.file_orginl_nm}"/></td>
					<td><c:out value="${result.file_wrter}"/></td>
				    <td><c:out value="${fn:substring(result.file_size / 1000000,0,4)}" />kB</td>
				    <td><fmt:formatDate value="${result.create_de}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
 					<td>
						<div class="BtnGroup">
							<button type="button" class="download-btn"onclick="location.href='/sys/spt/rpt/selectBscFileDownload.do?sttus=hist&file_id=<c:out value="${result.file_id}"/>'"><spring:message code="mngRptBsc.reportManage.download" /></button>
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
	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").attr("action","<c:url value='/sys/spt/rpt/selectRptBscManageList.do'/>");
		$("#listForm").submit();
	}
</script>