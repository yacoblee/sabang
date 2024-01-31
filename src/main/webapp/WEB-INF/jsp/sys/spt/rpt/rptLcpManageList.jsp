<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="pageTitle"><spring:message code="sysSptRpt.reportManage.LssLcpReport"/></c:set>
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
			<form id="listForm" action="/sys/spt/rpt/selectRptLcpManageList.do" method="post">
				<input name="gid" type="hidden" value=""/>
				<input name="id" type="hidden" value="${searchVO.id}"/>
				<input name="type" type="hidden" value="<c:out value='${searchVO.type}'/>"/>
				<input name="year" type="hidden" value="<c:out value='${searchVO.year}'/>"/>
				<input name="month" type="hidden" value="<c:out value='${searchVO.month}'/>"/>
				<input name="sd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
				<input name="sgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
				<input name="emd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
				<input name="ri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>
				<input name="writer" type="hidden" value="<c:out value='${searchVO.writer}'/>"/>
				<input name="mstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">			
				<input name="rptType" type="hidden" value="땅밀림실태조사"/>
				<input name="mstNmList" type="hidden"/>
				<input name="svyIdList" type="hidden"/>
				<input name="pageVal" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<input name="lastGrd" type="hidden" value="<c:out value='${searchVO.lastGrd}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysLssLcp.svyComptVO.svyGrade"/><span class="pilsu">*</span></th><!-- 조사유형 -->
								<td>
									<label for="lastGrdView" class="Hidden"><spring:message code="sysLssLcp.svyComptVO.svyGrade"/></label>
									<select id="lastGrdView" name="lastGrdView" title="<spring:message code="sysLssLcp.svyComptVO.svyGrade"/>">
										<option value="">전체</option>
										<option value="A" <c:if test="${searchVO.lastGrd eq 'A' }">selected="selected"</c:if>>A</option>
										<option value="B" <c:if test="${searchVO.lastGrd eq 'B' }">selected="selected"</c:if>>B</option>
										<option value="C" <c:if test="${searchVO.lastGrd eq 'C' }">selected="selected"</c:if>>C</option>
<%-- 										<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option> --%>
<%-- 										<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status"> --%>
<%-- 										<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option> --%>
<%-- 										</c:forEach> --%>
									</select>
								</td>
								<th><spring:message code="sysSptRpt.reportManage.svyDt"/></th><!-- 조사일자 -->
								<td colspan="2">
									<label for="yearView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyYear"/></label>
									<select id="yearView" name="yearView" title="<spring:message code="sysSptRpt.reportManage.svyYear"/>" onchange="fncSvyYearChange(); return false;">
										<option value=""<c:if test="${empty searchVO.year || searchVO.year == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="yearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${yearCodeInfo.svyyear}"/>"<c:if test="${searchVO.year eq yearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${yearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
									<label for="monthView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyMonth"/></label>
									<select id="monthView" name="monthView" title="<spring:message code="sysSptRpt.reportManage.svyMonth"/>">
										<option value=""<c:if test="${empty searchVO.month || searchVO.month == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyMonthCodeInfo" items="${monthCodeList}" varStatus="status">
										<option value="<c:out value="${svyMonthCodeInfo.code}"/>"<c:if test="${searchVO.month eq svyMonthCodeInfo.code}">selected="selected"</c:if>><c:out value="${svyMonthCodeInfo.codeDc}"/></option>
										</c:forEach>
									</select>
								</td>								
							</tr>
							<tr>
								<th><spring:message code="sysLssLcp.stripLandVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="sdView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svySd"/></label>
									<select id="sdView" name="sdView" title="<spring:message code="sysSptRpt.reportManage.svySd"/>" onchange="fncAdministCtprvnChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.sd || searchVO.sd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${sdCodeInfo.adminCode}"/>"<c:if test="${searchVO.sd eq sdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="sggView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svySgg"/></label>
									<select id="sggView" name="sggView" title="<spring:message code="sysSptRpt.reportManage.svySgg"/>" onchange="fncAdministSignguChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.sgg || searchVO.sgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${sggCodeInfo.adminCode}"/>"<c:if test="${searchVO.sgg eq sggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="emdView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyEmd"/></label>
									<select id="emdView" name="emdView" title="<spring:message code="sysSptRpt.reportManage.svyEmd"/>" onchange="fncAdministEmdChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.emd || searchVO.emd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="emdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${emdCodeInfo.adminCode}"/>"<c:if test="${searchVO.emd eq emdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${emdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="riView" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyRi"/></label>
									<select id="riView" name="riView" title="<spring:message code="sysSptRpt.reportManage.svyRi"/>">
										<option value=""<c:if test="${empty searchVO.ri || searchVO.ri == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="riCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${riCodeInfo.adminCode}"/>"<c:if test="${searchVO.ri eq riCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${riCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysSptRpt.reportManage.svyId"/></th><!-- 조사ID -->
								<td>
									<label for="sysSptRpt.reportManage.svyId" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyId"/></label>
									<input type="text" id="id" value="<c:out value='${searchVO.id}'/>" name="idView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysSptRpt.reportManage.svyUser"/></th><!-- 작성자 -->
								<td>
									<label for="sysSptRpt.reportManage.svyUser" class="Hidden"><spring:message code="sysSptRpt.reportManage.svyUser"/></label>
									<input type="text" id="writer" value="<c:out value='${searchVO.writer}'/>" name="writerView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysLssLcp.svyComptVO.mstNm"/></th><!-- 공유방명 -->
								<td colspan="2">
									<label for="sysLssLcp.svyComptVO.mstNm" class="Hidden"><spring:message code="sysLssLcp.svyComptVO.mstNm"/></label>
									<input type="text" id="mstNm" value="<c:out value='${searchVO.mstNm}'/>" name="mstNmView" class="mo_mt5 input_null" />
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
				<button type="button" class="download-btn fr" onclick="javascript:fncAllCheckReport('LCP'); return false;">전체보고서</button>
				<button type="button" class="download-btn fr" onclick="javascript:fncMultiCheckReport('LCP'); return false;">보고서</button>
<!-- 				<button type="button" class="download-btn fr" onclick="javascript:fncAllPhotoDown(); return false;">전체현장사진</button> -->
				<button type="button" class="download-btn fr" onclick="javascript:fncPhotoDown(); return false;">현장사진</button>
			</span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
					<colgroup>
					<col style="width: 9%;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
				</colgroup>
			<thead>
			<tr>
				<th><input type="checkbox"  id="reprt_all_check" onclick="javascript:fnSelectAllCheck();" /></th><!-- 보고서 병합 체크박스 -->
				<th><spring:message code="table.num" /></th><!-- 번호 -->
				<th><spring:message code="sysSptRpt.reportManage.svyId" /></th> <!--조사ID -->
				<th><spring:message code="sysSptRpt.reportManage.grade" /></th><!-- 조사등급 -->
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
								<input type="hidden" class="mstNm" value="<c:out value="${resultInfo.mstNm}"/>" />
								<input type="hidden" class="svyId" value="<c:out value="${resultInfo.id}"/>" />
								<input type="checkbox" class="reprt_check" value="<c:out value="${resultInfo.gid}"/>">
							</td>
							<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageUnit + status.count}"/></td>
							<td><c:out value="${resultInfo.id}"/></td>
							<td><c:out value="${resultInfo.lastGrd}"/></td>
							<td><c:out value="${resultInfo.year}"/></td>
							<td><c:out value="${resultInfo.writer}"/></td>
							<td><c:out value="${resultInfo.createDt}"/></td>
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
		$("#listForm").attr("action","<c:url value='/sys/spt/rpt/selectRptLcpManageList.do'/>");
		$("#listForm").submit();
	};
</script>