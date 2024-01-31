<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="sysVytEcb.analManageList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">사방사업</a></li>
		<li><a href="#">분석관리</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/vyt/ecb/als/selectVytEcbAnalList.do" method="post">
				<input name="mstId" type="hidden" value="<c:out value='${searchVO.mstId}'/>"/>
				<input name="sldId" type="hidden" value="<c:out value='${searchVO.sldId}'/>"/>
				<input name="analId" type="hidden" value="<c:out value='${searchVO.analId}'/>"/>
				<input name="workNm" type="hidden" value="<c:out value='${searchVO.workNm}'/>">
				<input name="workType" type="hidden" value="<c:out value='${searchVO.workType}'/>"/>
				<input name="workYear" type="hidden" value="<c:out value='${searchVO.workYear}'/>"/>
				<input name="workSd" type="hidden" value="<c:out value='${searchVO.workSd}'/>"/>
				<input name="workSgg" type="hidden" value="<c:out value='${searchVO.workSgg}'/>"/>
				<input name="workEmd" type="hidden" value="<c:out value='${searchVO.workEmd}'/>"/>
				<input name="workRi" type="hidden" value="<c:out value='${searchVO.workRi}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysVytEcb.analManageVO.workName"/></th><!-- 사업명 -->
								<td>
									<label for="workNmView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.workName"/></label>
									<input type="text" id="workNmView" value="<c:out value='${searchVO.workNm}'/>" name="workNmView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysVytEcb.analManageVO.workType"/></th><!-- 사업종류 -->
								<td>
									<label for="workTypeView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.workType"/></label>
									<select id="workTypeView" name="workTypeView" title="<spring:message code="sysVytEcb.analManageVO.workType"/>">
										<option value=""<c:if test="${empty searchVO.workType || searchVO.workType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="workTypeCodeInfo" items="${workTypeCodeList}" varStatus="status">
										<option value="<c:out value="${workTypeCodeInfo.code}"/>"<c:if test="${searchVO.workType eq workTypeCodeInfo.code}">selected="selected"</c:if>><c:out value="${workTypeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysVytEcb.workVO.workYear"/></th><!-- 등록년도 -->
								<td>
									<label for="workYearView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.workYear"/></label>
									<select id="workYearView" name="workYearView" title="<spring:message code="sysVytEcb.analManageVO.workYear"/>">
										<option value=""<c:if test="${empty searchVO.workYear || searchVO.workYear == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="yearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${yearCodeInfo.workyear}"/>"<c:if test="${searchVO.workYear eq yearCodeInfo.workyear}">selected="selected"</c:if>><c:out value="${yearCodeInfo.workyear}"/>년</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysVytEcb.analManageVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="workSdView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.sd"/></label>
									<select id="workSdView" name="workSdView" title="<spring:message code="sysVytEcb.analManageVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.workSd || searchVO.workSd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${sdCodeInfo.adminCode}"/>"<c:if test="${searchVO.workSd eq sdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="workSggView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.sgg"/></label>
									<select id="workSggView" name="workSggView" title="<spring:message code="sysVytEcb.analManageVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.workSgg || searchVO.workSgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${sggCodeInfo.adminCode}"/>"<c:if test="${searchVO.workSgg eq sggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="workEmdView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.emd"/></label>
									<select id="workEmdView" name="workEmdView" title="<spring:message code="sysVytEcb.analManageVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.workEmd || searchVO.workEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="emdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${emdCodeInfo.adminCode}"/>"<c:if test="${searchVO.workEmd eq emdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${emdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="workRiView" class="Hidden"><spring:message code="sysVytEcb.analManageVO.ri"/></label>
									<select id="workRiView" name="workRiView" title="<spring:message code="sysVytEcb.analManageVO.ri"/>">
										<option value=""<c:if test="${empty searchVO.workRi || searchVO.workRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="riCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${riCodeInfo.adminCode}"/>"<c:if test="${searchVO.workRi eq riCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${riCodeInfo.adminNm}"/></option>
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
				<select name="pageSelect" onchange="javascirpt:fnPage(); return false;">
					<option value="10"<c:if test="${searchVO.pageUnit == 10}">selected='selected'</c:if>>10개씩 보기</option>
					<option value="30"<c:if test="${searchVO.pageUnit == 30}">selected='selected'</c:if>>30개씩 보기</option>
					<option value="50"<c:if test="${searchVO.pageUnit == 50}">selected='selected'</c:if>>50개씩 보기</option>
					<option value="100"<c:if test="${searchVO.pageUnit == 100}">selected='selected'</c:if>>100개씩 보기</option>
					<option value="150"<c:if test="${searchVO.pageUnit == 150}">selected='selected'</c:if>>150개씩 보기</option>
				</select>
			</span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 20%;">
					<col style="width: 15%;">
					<col style="width: 35%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
				  <tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="sysVytEcb.analManageVO.workName" /></th><!-- 사업명 -->
					<th><spring:message code="sysVytEcb.analManageVO.workType" /></th><!-- 사업종류 -->
					<th><spring:message code="sysVytEcb.analManageVO.addr" /></th><!-- 주소 -->
					<th><spring:message code="sysVytEcb.analManageVO.creatDt" /></th><!-- 등록일 -->
					<th></th>
				  </tr>
				</thead>
				<tbody>
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="10"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
				<tr>
					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
					<td><c:out value='${resultInfo.workNm}'/></td>
					<td><c:out value='${resultInfo.workType}'/></td>
					<td><c:out value='${resultInfo.sdNm}'/> <c:out value='${resultInfo.sggNm}'/> <c:out value='${resultInfo.emdNm}'/> <c:out value='${resultInfo.riNm}'/> <c:out value='${resultInfo.jibun}'/></td>
					<td><c:out value='${resultInfo.creatDt}'/></td>
					<td class="tag-left">
						<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fncAnalStripLandDetail('<c:out value="${resultInfo.mstId}"/>','<c:out value="${resultInfo.sldId}"/>','<c:out value="${resultInfo.analId}"/>');return false;"></button>
						<c:if test="${resultInfo.analCnt gt 0}">
						<button title="분석파일 <spring:message code="title.download"/>" class="download-btn-m" onclick="javascript:fncDownloadAnalAll('<c:out value="${resultInfo.mstId}"/>','<c:out value="${resultInfo.sldId}"/>','<c:out value="${resultInfo.analId}"/>');return false;"></button>
						</c:if>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
<!-- 			<div class="BtnGroup"> -->
<!-- 				<div class="BtnRightArea"> -->
<%-- 					<button type="button" class="upload-btn" onclick="javascript:fncUploadStripLandDialog(); return false;"><spring:message code="button.bulkUpload" /></button> --%>
<%-- 					<button type="button" class="write-btn" onclick="javascript:fncAddStripLandInsertView(); return false;"><spring:message code="button.create" /></button> --%>
<!-- 				</div> -->
<!-- 			</div> -->
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