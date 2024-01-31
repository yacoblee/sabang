<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysLssLcp.stripLandList.title"/></c:set>
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
			<form id="searchForm" action="/sys/lss/lcp/sld/selectLcpSvySldList.do" method="post">
				<input name="id" type="hidden" value=""/>
				<input name="svysldNm" type="hidden" value="<c:out value='${searchVO.svysldNm}'/>"/>
				<input name="writer" type="hidden" value="<c:out value='${searchVO.writer}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageCheckValue" type="hidden" value="listView">
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysLssLcp.stripLandVO.svysldNm"/></th><!-- 명칭 -->
								<td>
									<label for="sysLssLcp.svyComptVO.svysldNm" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.svysldNm"/></label>
									<input type="text" id="svysldNmView" value="<c:out value='${searchVO.svysldNm}'/>" name="svysldNmView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysLssLcp.stripLandVO.writer"/></th><!-- 생성자 -->
								<td>
									<label for="sysLssLcp.svyComptVO.writer" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.writer"/></label>
									<input type="text" id="writerView" value="<c:out value='${searchVO.writer}'/>" name="writerView" class="mo_mt5 input_null" />
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
			<fieldset>
				<form id="listForm" action="/sys/lss/lcp/sld/selectLcpSvySldList.do" method="post">
					<input name="id" type="hidden" value=""/>
					<input name="sldId" type="hidden" value=""/>
					<input name="svysldNm" type="hidden" value=""/>
					<input name="writer" type="hidden" value=""/>
				</form>			
			</fieldset>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
				</colgroup>
				<thead>
				  <tr>
					<th><spring:message code="sysLssLcp.stripLandVO.svysldNm" /></th><!-- 명칭 -->
					<th><spring:message code="sysLssLcp.stripLandVO.writer" /></th><!-- 조사연도 -->
					<th><spring:message code="sysLssLcp.stripLandVO.createDt" /></th><!-- 작성자 -->
					<th><spring:message code="sysLssLcp.stripLandVO.sldCnt" /></th><!-- 관할 -->
					<th></th><!-- 관할 -->
<!-- 					<th></th> -->
				  </tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="7"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
				<tr>
					<td><c:out value='${resultInfo.svysldNm}'/></td>
					<td><c:out value='${resultInfo.svysldCreateUser}'/></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${resultInfo.creatDt}"/></td>
					<td><c:out value='${resultInfo.svysldRegcnt}'/></td>
					<td>
						<button title="<spring:message code="button.searchDetail"/>" type="button" class="search-btn-m" onclick="javascript:fncSvySldInfo('<c:out value="${resultInfo.id}"/>');"></button>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN_LCP','ROLE_SUB_ADMIN','ROLE_ADMIN')">
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncAddStripLandInsertView(); return false;"><spring:message code="button.create" /></button>
				</div>
			</div>
			</sec:authorize>
			
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