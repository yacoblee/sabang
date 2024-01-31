<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysLssLcp.rank.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">땅밀림실태조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/lss/lcp/rnk/selectLcpSvyRankList.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="svyRank" type="hidden" value="<c:out value='${searchVO.svyRank}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageVal" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysLssLcp.lcpRankVO.rank"/></th><!-- 랭크 -->
								<td>
									<label for="svyRankView" class="Hidden"><spring:message code="sysLssLcp.lcpRankVO.rank"/></label>
									<input type="text" id="svyRankView" value="<c:if test="${searchVO.svyRank ne 0 }"><c:out value='${searchVO.svyRank}'/></c:if>" name="svyRankView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysLssLcp.lcpRankVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="svySdView" class="Hidden"><spring:message code="sysLssLcp.lcpRankVO.sd"/></label>
									<select id="svySdView" name="svySdView" title="<spring:message code="sysLssLcp.lcpRankVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
										<option value=""><spring:message code="title.all" /></option>
										<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svySggView" class="Hidden"><spring:message code="sysLssLcp.lcpRankVO.sgg"/></label>
									<select id="svySggView" name="svySggView" title="<spring:message code="sysLssLcp.lcpRankVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
										<option value=""><spring:message code="title.all" /></option>
										<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyEmdView" class="Hidden"><spring:message code="sysLssLcp.lcpRankVO.emd"/></label>
									<select id="svyEmdView" name="svyEmdView" title="<spring:message code="sysLssLcp.lcpRankVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
										<option value=""><spring:message code="title.all" /></option>
										<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyRiView" class="Hidden"><spring:message code="sysLssLcp.lcpRankVO.ri"/></label>
									<select id="svyRiView" name="svyRiView" title="<spring:message code="sysLssLcp.lcpRankVO.ri"/>">
										<option value=""><spring:message code="title.all" /></option>
										<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
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
<!-- 				<button class="save-btn fr" onclick="javascript:fnCreateLcpSvyRankSld(); return false;">토심 생성</button> -->
<!-- 				<button class="save-btn fr" onclick="javascript:fnCreateLcpSvyRankSlope(); return false;">경사도 생성</button> -->
<!-- 				<button class="save-btn fr" onclick="javascript:fnCreateLcpSvyRankElevation(); return false;">고도 생성</button> -->
				<button class="download-btn fr" onclick="javascript:fncDownloadLcpSvyRank(); return false;"><spring:message code="sysLssLcp.rank.download" /></button>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN_LCP','ROLE_SUB_ADMIN','ROLE_ADMIN')">
				<button class="upload-btn fr" onclick="javascript:fnInsertRankPopup(); return false;"><spring:message code="sysLssLcp.rank.upload" /></button>
				</sec:authorize>
				
<!-- 				<button class="del-btn fr" onclick="javascript:fnDeleteLcpSvyComptTestPopup(); return false;">슈퍼맵 삭제 테스트</button> -->
<!-- 				<button class="save-btn fr" onclick="javascript:fnUpsertLcpSvyComptTestPopup(); return false;">슈퍼맵 저장 테스트</button> -->
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
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="sysLssLcp.lcpRankVO.rank" /></th>		<!-- 랭크 -->
						<th><spring:message code="sysLssLcp.lcpRankVO.year" /></th>		<!-- 조사연도 -->
						<th><spring:message code="sysLssLcp.lcpRankVO.addr" /></th>		<!-- 주소 -->
						<th><spring:message code="sysLssLcp.lcpRankVO.createDt" /></th>	<!-- 등록일 -->
						<th><spring:message code="sysLssLcp.lcpRankVO.manage" /></th>	<!-- 관리 -->
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
					<td><c:out value='${resultInfo.svyRank}'/></td>
					<td><c:out value='${resultInfo.svyYear}'/></td>
					<td><c:out value='${resultInfo.svyAddr}'/></td>
					<td><c:out value='${resultInfo.svyDate}'/></td>
					<td></td>
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
	<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
 	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").submit();
	}
</script>