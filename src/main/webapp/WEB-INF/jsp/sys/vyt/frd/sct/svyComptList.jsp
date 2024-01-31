<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysVytFrd.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="searchForm" action="/sys/vyt/frd/sct/selectFrdSvyComptList.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="mstId" type="hidden" value="">
				<input name="svyLabel" type="hidden" value="">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysVytFrd.svyComptVO.type"/><span class="pilsu" title="<spring:message code="title.tootip.msg" arguments="엑셀다운로드" />">*</span></th><!-- 조사유형 -->
								<td>
									<label for="frdType" class="Hidden"><spring:message code="sysVytFrd.svyComptVO.type"/></label>
									<select id="frdType" name="frdType" title="<spring:message code="sysVytFrd.svyComptVO.type"/>" style="min-width: 162px !important;">
										<option value=""<c:if test="${empty searchVO.frdType || searchVO.frdType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="frdTypeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${frdTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.frdType eq frdTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${frdTypeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysVytFrd.svyComptVO.date"/></th><!-- 조사일자 -->
								<td colspan="2">
									<label for="svyYear" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.year"/></label>
									<select id="svyYear" name="svyYear" title="<spring:message code="sysLssBsc.stripLandVO.year"/>" onchange="fncSvyYearChange(); return false;">
										<option value=""<c:if test="${empty searchVO.svyYear || searchVO.svyYear == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyYearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${svyYearCodeInfo.svyyear}"/>"<c:if test="${searchVO.svyYear eq svyYearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${svyYearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
									<label for="svyMonth" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.month"/></label>
									<select id="svyMonth" name="svyMonth" title="<spring:message code="sysLssBsc.stripLandVO.month"/>">
										<option value=""<c:if test="${empty searchVO.svyMonth || searchVO.svyMonth == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyMonthCodeInfo" items="${monthCodeList}" varStatus="status">
										<option value="<c:out value="${svyMonthCodeInfo.code}"/>"<c:if test="${searchVO.svyMonth eq svyMonthCodeInfo.code}">selected="selected"</c:if>><c:out value="${svyMonthCodeInfo.codeDc}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysVytFrd.svyComptVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="svySd" class="Hidden"><spring:message code="sysVytFrd.analManageVO.sd"/></label>
									<select id="svySdView" name="svySd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministCtprvnNmChange(); return false;">
										<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sdCodeList" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${sdCodeList.adminNm}"/>"<c:if test="${searchVO.svySd eq sdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${sdCodeList.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svySgg" class="Hidden"><spring:message code="sysVytFrd.analManageVO.sgg"/></label>
									<select id="svySggView" name="svySgg" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministSignguNmChange(); return false;">
										<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${sggCodeInfo.adminNm}"/>"<c:if test="${searchVO.svySgg eq sggCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyEmd" class="Hidden"><spring:message code="sysVytFrd.analManageVO.emd"/></label>
									<select id="svyEmdView" name="svyEmd" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15" onchange="fncAdministEmdNmChange(); return false;">
										<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="emdCodeList" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${emdCodeList.adminNm}"/>"<c:if test="${searchVO.svyEmd eq emdCodeList.adminNm}">selected="selected"</c:if>><c:out value="${emdCodeList.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyRi" class="Hidden"><spring:message code="sysVytFrd.analManageVO.ri"/></label>
									<select id="svyRiView" name="svyRi" title="<spring:message code="sysVytFrd.analManageVO.addr"/>" class="wd15">
										<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="riCodeList" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${riCodeList.adminNm}"/>"<c:if test="${searchVO.svyRi eq riCodeList.adminNm}">selected="selected"</c:if>><c:out value="${riCodeList.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysVytFrd.svyComptVO.svyId"/></th><!-- 조사ID -->
								<td>
									<label for="sysVytFrd.svyComptVO.svyId" class="Hidden"><spring:message code="sysVytFrd.svyComptVO.svyId"/></label>
									<input type="text" id="svyId" name="svyId" value="<c:out value='${searchVO.svyId}'/>" name="svyIdView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysVytFrd.svyComptVO.svyUser"/></th><!-- 점검자 -->
								<td>
									<label for="sysVytFrd.svyComptVO.svyUser" class="Hidden"><spring:message code="sysVytFrd.svyComptVO.svyUser"/></label>
									<input type="text" id="svyUser" name="svyUser" value="<c:out value='${searchVO.svyUser}'/>" name="svyUserView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysVytFrd.svyComptVO.mstNm"/></th><!-- 공유방명 -->
								<td colspan="2">
									<label for="sysVytFrd.svyComptVO.mstNm" class="Hidden"><spring:message code="sysVytFrd.svyComptVO.mstNm"/></label>
									<input type="text" id="mstNm" name="mstNm" value="<c:out value='${searchVO.mstNm}'/>" name="mstNmView" class="mo_mt5 input_null" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<!--<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnCreatePhoto(); return false;">사진생성</button>-->
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">검색</button>
					</div>
				</div>
			</form>
		</fieldset>
		<div class="BoardList">
			<form id="listForm" action="/sys/vyt/frd/sct/selectFrdSvyComptList.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="mstId" type="hidden" value="">
				<input name="svyLabel" type="hidden" value="">
				<input name="schfrdType" type="hidden" value="<c:out value='${searchVO.frdType}'/>"/>
				<input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
				<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
				<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
				<input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
				<input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
				<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			</form>
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<select name="pageSelect" onchange="fnSearch();">
					<option value="10"<c:if test="${searchVO.pageUnit == 10}">selected='selected'</c:if>>10개씩 보기</option>
					<option value="30"<c:if test="${searchVO.pageUnit == 30}">selected='selected'</c:if>>30개씩 보기</option>
					<option value="50"<c:if test="${searchVO.pageUnit == 50}">selected='selected'</c:if>>50개씩 보기</option>
					<option value="100"<c:if test="${searchVO.pageUnit == 100}">selected='selected'</c:if>>100개씩 보기</option>
					<option value="150"<c:if test="${searchVO.pageUnit == 150}">selected='selected'</c:if>>150개씩 보기</option>
				</select>
<%-- 			<button class="download-btn fr" onclick=""><spring:message code="button.excel" /><spring:message code="title.download" /></button> --%>
<%-- 			<button class="add-btn fr" onclick="javascript:fnExcelUpload(); return false;"><spring:message code="button.excel" /><spring:message code="button.create" /></button> --%>
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
					<col style="width: ;">
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="table.num" /></th><!-- 번호 -->
						<th>조사ID</th><!-- 조사ID -->
						<th>점검자</th><!-- 점검자 -->
						<th>임도종류</th><!-- 임도종류 -->
						<th>등록일</th><!-- 등록일 -->
						<th>최근수정일</th><!-- 최근수정일 -->
						<th></th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="9"><spring:message code="common.nodata.msg" /></td>
					</tr>
				</c:if>
				<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
					<tr>
						<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageUnit + status.count}"/></td>
						<td><a href="<c:url value='/sys/vyt/frd/sct/selectFrdSvyComptDetail.do'/>?gid=${resultInfo.gid}" onclick="javascript:fncSvyComptDetail('<c:out value="${resultInfo.gid}"/>','<c:out value="${resultInfo.mstId}"/>','<c:out value="${resultInfo.svyId}"/>');return false;"><c:out value='${resultInfo.svyId}'/></a></td>
						<%-- <td><c:out value='${fn:substring(resultInfo.svyDt,0,4)}'/></td> --%>
						<td><c:out value='${resultInfo.svyUser}'/></td>
						<td><c:out value='${resultInfo.frdType}'/></td>
						<td><c:out value='${resultInfo.creatDt}'/></td>
						<td><c:out value='${resultInfo.lastUpdtPnttm}'/></td>
						<td>
							<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fncSvyComptDetail('<c:out value="${resultInfo.gid}"/>','<c:out value="${resultInfo.mstId}"/>','<c:out value="${resultInfo.svyId}"/>');return false;"></button>
							<button title="<spring:message code="button.movedMap"/>" class="map-btn-m" onclick="javascript:fncOpenMap('FRD','<c:out value="${resultInfo.gid}"/>'); return false;"></button>
							<button title="분석파일 일괄 <spring:message code="title.download"/>" class="download-btn-m" onclick="javascript:fncDownloadAnalAll('<c:out value="${resultInfo.svyId}"/>','<c:out value="${resultInfo.mstId}"/>','<c:out value='${fn:substring(resultInfo.creatDt,0,4)}'/>'); return false;" <c:choose><c:when test="${resultInfo.analFileResultAt1 eq 'N' && resultInfo.analFileResultAt2 eq 'N'}">disabled</c:when><c:when test="${resultInfo.analFileResultAt1 eq 'N' && resultInfo.analFileResultAt2 eq 'Y'}"></c:when><c:when test="${resultInfo.analFileResultAt1 eq 'Y' && resultInfo.analFileResultAt2 eq 'N'}">disabled</c:when><c:when test="${resultInfo.analFileResultAt1 eq 'Y' && resultInfo.analFileResultAt2 eq 'Y'}"></c:when></c:choose>></button>
							<button title="현장사진 <spring:message code="title.download"/>" class="photo-btn-m" onclick="javascript:fncDownloadPhotoListZip('<c:out value="${resultInfo.gid}"/>');return false;" <c:if test="${resultInfo.photoResultAt eq 'N'}">disabled</c:if>></button>
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
	<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
 	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").attr("action","/sys/vyt/frd/sct/selectFrdSvyComptList.do");
		$("#listForm").submit();
	}
</script>