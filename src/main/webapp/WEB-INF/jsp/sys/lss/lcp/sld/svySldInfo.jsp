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
<c:set var="pageTitle"><spring:message code="sysLssLcp.sldRegInfo.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysLssLcp.sldInfo.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">땅밀림실태조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">		
			<fieldset>
				<legend class="Hidden">검색테이블</legend>
				<form id="searchForm" action="/sys/lss/lcp/sld/selectSvySldInfo.do" method="post">
					<input name="id" type="hidden" value="<c:out value='${result.id}'/>"/>
					<input name="rank" type="hidden" value="<c:out value='${stripLandVO.rank}'/>"/>
					<input name="label" type="hidden" value="<c:out value='${stripLandVO.label}'/>"/>
					<input name="sd" type="hidden" value="<c:out value='${stripLandVO.sd}'/>"/>
					<input name="sgg" type="hidden" value="<c:out value='${stripLandVO.sgg}'/>"/>
					<input name="emd" type="hidden" value="<c:out value='${stripLandVO.emd}'/>"/>
					<input name="ri" type="hidden" value="<c:out value='${stripLandVO.ri}'/>"/>
					<input name="stdgCd" type="hidden" value="<c:out value='${stripLandVO.stdgCd}'/>"/>
					<input name="frtpCd" type="hidden" value="<c:out value='${stripLandVO.frtpCd}'/>"/>
					<input name="prrckLarg" type="hidden" value="<c:out value='${stripLandVO.prrckLarg}'/>"/>
					<input name="pageSubIndex" type="hidden" value="<c:out value='${stripLandVO.pageSubIndex}'/>">
					<input name="pageUnit" type="hidden" value="<c:out value='${stripLandVO.pageUnit}'/>">
					
					<input name="svysldNm" type="hidden" value="<c:out value='${stripLandVO.svysldNm}'/>">
					<input name="writer" type="hidden" value="<c:out value='${stripLandVO.writer}'/>">
					<input name="pageIndex" type="hidden" value="<c:out value='${stripLandVO.pageIndex}'/>">
					
					<div class="search">
						<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
							<caption class="Hidden">${pageTitle} 검색영역</caption>
							<tbody>
								<tr>
									<th><spring:message code="sysLssLcp.stripLandVO.rank"/></th><!-- 랭크 -->
									<td>
										<label for="sysLssLcp.svyComptVO.id" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.rank"/></label>
										<input type="text" id="rankView" value="<c:out value='${stripLandVO.rank}'/>" name="rankView" class="mo_mt5 input_null" />
									</td>
									<th><spring:message code="sysLssLcp.stripLandVO.label"/></th><!-- 라벨 -->
									<td>
										<label for="sysLssLcp.svyComptVO.label" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.label"/></label>
										<input type="text" id="labelView" value="<c:out value='${stripLandVO.label}'/>" name="labelView" class="mo_mt5 input_null" />
									</td>
									<th><spring:message code="sysLssLcp.stripLandVO.frtpCd"/></th><!-- 임상 -->
									<td>
										<label for="sysLssLcp.svyComptVO.writer" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.frtpCd"/></label>
										<select id="frtpCdView" name="frtpCdView" title="<spring:message code="sysLssLcp.stripLandVO.frtpCd"/>">
											<option value=""<c:if test="${empty stripLandVO.frtpCd || stripLandVO.frtpCd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:forEach var="frtpCd" items="${frtpCdResult}" varStatus="status">
											<option value="<c:out value="${frtpCd.code}"/>"<c:if test="${stripLandVO.frtpCd eq frtpCd.code}">selected="selected"</c:if>><c:out value="${frtpCd.codeNm}"/></option>
											</c:forEach>
										</select>
									</td>
									<th><spring:message code="sysLssLcp.stripLandVO.prrckLarg"/></th><!-- 지질(대) -->
									<td>
										<label for="sysLssLcp.svyComptVO.writer" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.prrckLarg"/></label>
										<select id="prrckLargView" name="prrckLargView" title="<spring:message code="sysLssLcp.stripLandVO.prrckLarg"/>">
											<option value=""<c:if test="${empty stripLandVO.prrckLarg || stripLandVO.prrckLarg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:forEach var="prrckLarg" items="${prrckLargResult}" varStatus="status">
											<option value="<c:out value="${prrckLarg.code}"/>"<c:if test="${stripLandVO.prrckLarg eq prrckLarg.code}">selected="selected"</c:if>><c:out value="${prrckLarg.codeNm}"/></option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th><spring:message code="sysLssLcp.stripLandVO.addr"/></th><!-- 주소 -->
									<td colspan="5">
										<label for="sdView" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.sd"/></label>
										<select id="sdView" name="sdView" title="<spring:message code="sysLssLcp.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event,'list'); return false;">
											<option value=""<c:if test="${empty stripLandVO.stdgCd || stripLandVO.stdgCd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:forEach var="sdCodeInfo" items="${sdCodeList}" varStatus="status">
											<option value="<c:out value="${sdCodeInfo.adminCode}"/>"<c:if test="${stripLandVO.sd eq sdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sdCodeInfo.adminNm}"/></option>
											</c:forEach>
										</select>
										<label for="sggView" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.sgg"/></label>
										<select id="sggView" name="sggView" title="<spring:message code="sysLssLcp.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event,'list'); return false;">
											<option value=""<c:if test="${empty stripLandVO.stdgCd || stripLandVO.stdgCd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
											<option value="<c:out value="${sggCodeInfo.adminCode}"/>"<c:if test="${stripLandVO.sgg eq sggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
											</c:forEach>
										</select>
										<label for="emdView" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.emd"/></label>
										<select id="emdView" name="emdView" title="<spring:message code="sysLssLcp.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event,'list'); return false;">
											<option value=""<c:if test="${empty stripLandVO.stdgCd || stripLandVO.stdgCd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:forEach var="emdCodeInfo" items="${emdCodeList}" varStatus="status">
											<option value="<c:out value="${emdCodeInfo.adminCode}"/>"<c:if test="${stripLandVO.emd eq emdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${emdCodeInfo.adminNm}"/></option>
											</c:forEach>
										</select>
										<label for="riView" class="Hidden"><spring:message code="sysLssLcp.stripLandVO.ri"/></label>
										<select id="riView" name="riView" title="<spring:message code="sysLssLcp.stripLandVO.ri"/>">
											<option value=""<c:if test="${empty stripLandVO.stdgCd || stripLandVO.stdgCd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:forEach var="riCodeInfo" items="${riCodeList}" varStatus="status">
											<option value="<c:out value="${riCodeInfo.adminCode}"/>"<c:if test="${stripLandVO.ri eq riCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${riCodeInfo.adminNm}"/></option>
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
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th><spring:message code="sysLssLcp.stripLandVO.svysldNm" /></th>
						<td><c:out value="${result.svysldNm}"/></td>
						<th><spring:message code="sysLssLcp.stripLandVO.writer" /></th>
						<td><c:out value="${result.svysldCreateUser}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysLssLcp.stripLandVO.createDt" /></th>
						<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${result.creatDt}"/></td>
						<th><spring:message code="sysLssLcp.stripLandVO.sldCnt" /></th>
						<td><c:out value="${result.svysldRegcnt}"/></td>
					</tr>
				</table>
			</div>
			<fieldset>
			<form id="listForm" action="/sys/lss/lcp/sld/selectSvySldInfo.do" method="post">				
				<input name="sldId" type="hidden" value="<c:out value='${result.sldId}'/>">
				<input name="svySldNm" type="hidden" value="<c:out value='${result.svysldNm}'/>">
				<input name="uniqId" type="hidden" value="<c:out value='${result.uniqId}'/>">
				<input name="year" type="hidden">
				<input name="rank" type="hidden" value="<c:out value='${stripLandVO.rank}'/>"/>
				<input name="label" type="hidden" value="<c:out value='${stripLandVO.label}'/>"/>
				<input name="sd" type="hidden" value="<c:out value='${stripLandVO.sd}'/>"/>
				<input name="sgg" type="hidden" value="<c:out value='${stripLandVO.sgg}'/>"/>
				<input name="emd" type="hidden" value="<c:out value='${stripLandVO.emd}'/>"/>
				<input name="ri" type="hidden" value="<c:out value='${stripLandVO.ri}'/>"/>
				<input name="stdgCd" type="hidden" value="<c:out value='${stripLandVO.stdgCd}'/>"/>
				<input name="frtpCd" type="hidden" value="<c:out value='${stripLandVO.frtpCd}'/>"/>
				<input name="prrckLarg" type="hidden" value="<c:out value='${stripLandVO.prrckLarg}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${stripLandVO.pageSubIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${stripLandVO.pageUnit}'/>">
				
				<input name="schid" type="hidden" value="<c:out value='${result.id}'/>">
				<input name="schsvysldNm" type="hidden" value="<c:out value='${stripLandVO.svysldNm}'/>">
				<input name="schwriter" type="hidden" value="<c:out value='${stripLandVO.writer}'/>">
			</form>
			</fieldset>
			<div class="BoardList">
			<h3>${subTitle}</h3>
			<input name="id" type="hidden" value="<c:out value='${result.id}'/>"/>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<select name="pageSelect" onchange="fnSearch();">
					<option value="10"<c:if test="${stripLandVO.pageUnit == 10}">selected='selected'</c:if>>10개씩 보기</option>
					<option value="30"<c:if test="${stripLandVO.pageUnit == 30}">selected='selected'</c:if>>30개씩 보기</option>
					<option value="50"<c:if test="${stripLandVO.pageUnit == 50}">selected='selected'</c:if>>50개씩 보기</option>
					<option value="100"<c:if test="${stripLandVO.pageUnit == 100}">selected='selected'</c:if>>100개씩 보기</option>
					<option value="150"<c:if test="${stripLandVO.pageUnit == 150}">selected='selected'</c:if>>150개씩 보기</option>
				</select>
				<sec:authorize access="hasAnyRole('ROLE_USER_CNRS','ROLE_ADMIN_LCP','ROLE_SUB_ADMIN','ROLE_ADMIN')">
				<button style="float:right;" type="button" class="save-btn" onclick="javascript:fncAddStripLandInsertView('<c:out value="${result.id }"/>','<fmt:formatDate pattern="yyyy" value="${result.creatDt}"/>'); return false;"><spring:message code="sysFckApr.stripLand.upload" /></button>
				</sec:authorize>
				<button style="float:right;" type="button" class="download-btn" onclick="javascript:fncStripLandExcel('<c:out value="${result.id }"/>'); return false;"><spring:message code="button.excel" /><spring:message code="title.download" /></button>
			</span>
			
			<table summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
				<thead>
					<tr>
						<th><spring:message code="sysLssLcp.stripLandVO.smid" /></th>
						<th><spring:message code="sysLssLcp.stripLandVO.rank" /></th>
						<th><spring:message code="sysLssLcp.stripLandVO.label" /></th>
						<th><spring:message code="sysLssLcp.stripLandVO.addr" /></th>
						<th><spring:message code="sysLssLcp.stripLandVO.frtpCd" /></th>
						<th><spring:message code="sysLssLcp.stripLandVO.prrckLarg" /></th>
						<th></th>
					</tr>
				</thead>
				<tbody class="ov">
					<c:if test="${fn:length(resultList) == 0}">					
						<tr>
							<td colspan="7" style="text-align:center;"><spring:message code="info.nodata.msg" /></td>
						</tr>
					</c:if>
					<c:forEach var="result" items="${resultList}" varStatus="status">
						<tr>
							<td><c:out value="${result.uniqId}" /></td>
							<td><c:out value="${result.rank}"/></td>
							<td><c:out value="${result.label}"/></td>
							<td><c:out value="${result.addr}"/></td>					
							<td>
								<c:forEach var="frtpCd" items="${frtpCdResult}" varStatus="status">
									<c:if test="${frtpCd.code eq result.frtpCd }"><c:out value="${frtpCd.codeNm }"/> </c:if>
								</c:forEach>	
							</td>					
							<td>
								<c:forEach var="prrckLarg" items="${prrckLargResult}" varStatus="status">
									<c:if test="${prrckLarg.code eq result.prrckLarg}"><c:out value="${prrckLarg.codeNm }"/> </c:if>
								</c:forEach>	
							</td>
							<td>
								<button title="<spring:message code="button.searchDetail"/>" type="button" class="search-btn-m" onclick="javascript:fnSvySldInfoDetail('<c:out value="${result.sldId}"/>','<c:out value="${result.uniqId}"/>');"></button>
							</td>					
						</tr>
					</c:forEach>
				</tbody>	
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
					<button type="button" class="del-btn" onclick="javascript:fnDeleteSvySldRegInfo('<c:out value="${result.id}"/>');"><spring:message code="sysLssLcp.deleteSldInfo.title" /></button>
					</sec:authorize>
					<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
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
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>