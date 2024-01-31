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
<c:set var="pageTitle"><spring:message code="sysSptRpt.reportManage.FckAprReport.view"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">업무지원</a></li>
		<li><a href="#">보고서관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/spt/rpt/rptAprManageDetail.do" method="post">
				<input name="mstId" type="hidden" value="<c:out value="${reportVO.mstId}"/>">
				<input name="type" type="hidden" value="<c:out value="${searchVO.type}"/>">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="searchCondition" type="hidden" value="<c:out value='${searchVO.searchCondition}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
				<div class="search">
					<table class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th><spring:message code="sysSptRpt.reportManage.svyType"/></th>
								<td>
									<label for="typeView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyType"/></label>
									<select id="typeView" name="typeView" title="<spring:message code="sysSptRpt.reportManage.svyType"/>">
										<option value=""<c:if test="${empty searchVO.type || searchVO.type == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="typeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${typeCodeInfo.codeNm}"/>"<c:if test="${searchVO.type eq typeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${typeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchCondition" class="Hidden">구분</label>
									<select id="searchCondition" name="searchConditionView" title="<spring:message code="title.searchCondition" />">
										<option value="0"<c:if test="${empty searchVO.searchCondition || searchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1"<c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="sysSptRpt.reportManage.svyId"/></option>
										<option value="2"<c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message	code="sysSptRpt.reportManage.svyUser"/></option>
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
		<div class="BoardDetail">
			<br>
			<table summary="<spring:message code="common.summary.list"/>">
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
					<colgroup>
					<col style="width: 15%;">
					<col style="width: 25%;">
					<col style="width: 15%;">
					<col style="width: 25%;">				
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code="sysSptRpt.reportManage.mstNm"/></th>
						<td class="row"><c:out value="${reportVO.corpNm}"/>-<c:out value="${reportVO.partNm}"/></td>
						<th><spring:message code="sysSptRpt.reportManage.creatDt"/></th>
						<td class="row"><c:out value="${reportVO.createDt}"/></td>						
					</tr>
					<tr>
						<th><spring:message code="sysSptRpt.reportManage.creatUsrNm"/></th>
						<td class="row"><c:out value="${reportVO.creatNm}"/></td>
						<th><spring:message code="sysSptRpt.reportManage.cnt"/></th>
						<td class="row"><c:out value="${reportVO.cnt}"/></td>						
					</tr>
				</tbody>
			</table>
		</div>
		<div class="BoardList">
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
					<colgroup>
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
				</colgroup>
			<thead>
			<tr>
				<th><input type="checkbox"  id="reprt_all_check" onclick="javascript:fnSelectAllCheck();" /></th><!-- 보고서 병합 체크박스 -->
				<th><spring:message code="table.num" /></th><!-- 번호 -->
				<th><spring:message code="sysSptRpt.reportManage.svyId" /></th> <!--조사ID -->
				<th><spring:message code="sysSptRpt.reportManage.svyType" /></th><!-- 조사유형 -->
				<th><spring:message code="sysSptRpt.reportManage.svyYear" /></th><!-- 조사연도 -->				
				<th><spring:message code="sysSptRpt.reportManage.svyUser" /></th><!-- 조사자 -->
				<th><spring:message code="sysSptRpt.reportManage.svyCreateDt" /></th><!-- 등록일 -->
			</tr>
			</thead>
				<tbody>
					<c:if test="${fn:length(svyComptList) == 0}">
						<tr>
							<td colspan="8"><spring:message code="info.nodata.msg" /></td>
						</tr>
					</c:if>
					<c:forEach var="resultInfo" items="${svyComptList}" varStatus="status">
						<tr>
							<td>
								<input type="checkbox" class="reprt_check" value="<c:out value="${resultInfo.id}"/>">
							</td>
							<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
							<td><c:out value="${resultInfo.id}"/></td>
							<td><c:out value="${resultInfo.type}"/></td>
							<td><c:out value="${resultInfo.year}"/></td>
							<td><c:out value="${resultInfo.writer}"/></td>
							<td><c:out value="${resultInfo.createDt}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
				<button type="button" class="download-btn" onclick="javascript:fncAllCheckReport('APR'); return false;">전체보고서</button>
				<button type="button" class="download-btn" onclick="javascript:fncMultiCheckReport('APR'); return false;">보고서</button>
				<button type="button" class="list-btn" onclick="location.href='/sys/spt/rpt/selectRptAprManageList.do'"><spring:message code="button.list" /></button>
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
		$("#listForm").attr("action","<c:url value='/sys/spt/rpt/rptAprManageDetail.do'/>");
		$("#listForm").submit();
	}
</script>