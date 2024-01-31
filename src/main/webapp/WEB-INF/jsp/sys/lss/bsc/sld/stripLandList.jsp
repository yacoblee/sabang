<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="sysLssBsc.stripLandList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">기초조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/lss/bsc/sld/selectStripLandList.do" method="post">
				<input name="gid" type="hidden" value=""/>
				<input name="id" type="hidden" value=""/>
				<input name="type" type="hidden" value="<c:out value='${searchVO.type}'/>"/>
				<input name="year" type="hidden" value="<c:out value='${searchVO.year}'/>"/>
				<input name="region1" type="hidden" value="<c:out value='${searchVO.region1}'/>"/>
				<input name="region2" type="hidden" value="<c:out value='${searchVO.region2}'/>"/>
				<input name="sd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
				<input name="sgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
				<input name="emd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
				<input name="ri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>
				<input name="writer" type="hidden" value="<c:out value='${searchVO.writer}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysLssBsc.stripLandVO.type"/></th><!-- 조사유형 -->
								<td>
									<label for="typeView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.type"/></label>
									<select id="typeView" name="typeView" title="<spring:message code="sysLssBsc.stripLandVO.type"/>">
										<option value=""<c:if test="${empty searchVO.type || searchVO.type == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="typeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${typeCodeInfo.codeNm}"/>"<c:if test="${searchVO.type eq typeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${typeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysLssBsc.stripLandVO.year"/></th><!-- 연도 -->
								<td>
									<label for="yearView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.year"/></label>
									<select id="yearView" name="yearView" title="<spring:message code="sysLssBsc.stripLandVO.year"/>">
										<option value=""<c:if test="${empty searchVO.year || searchVO.year == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="yearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${yearCodeInfo.year}"/>"<c:if test="${searchVO.year eq yearCodeInfo.year}">selected="selected"</c:if>><c:out value="${yearCodeInfo.year}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysLssBsc.stripLandVO.region"/></th><!-- 관할 -->
								<td>
									<label for="region1View" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.region1"/></label>
									<select id="region1View" name="region1View" title="<spring:message code="sysLssBsc.stripLandVO.region1"/>" onchange="fncRegionChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.region1 || searchVO.region1 == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="region1CodeInfo" items="${region1CodeList}" varStatus="status">
										<option value="<c:out value="${region1CodeInfo.code}"/>"<c:if test="${searchVO.region1 eq region1CodeInfo.code}">selected="selected"</c:if>><c:out value="${region1CodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
									<label for="region2View" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.region2"/></label>
									<select id="region2View" name="region2View" title="<spring:message code="sysLssBsc.stripLandVO.region2"/>" >
										<option value=""<c:if test="${empty searchVO.region2 || searchVO.region2 == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="region2CodeInfo" items="${region2CodeList}" varStatus="status">
										<option value="<c:out value="${region2CodeInfo.code}"/>"<c:if test="${searchVO.region2 eq region2CodeInfo.code}">selected="selected"</c:if>><c:out value="${region2CodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>								
							</tr>
							<tr>
								<th><spring:message code="sysLssBsc.stripLandVO.addr"/></th><!-- 주소 -->
								<td colspan="5">
									<label for="sdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sd"/></label>
									<select id="sdView" name="sdView" title="<spring:message code="sysLssBsc.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.sd || searchVO.sd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${sdCodeInfo.adminCode}"/>"<c:if test="${searchVO.sd eq sdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="sggView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sgg"/></label>
									<select id="sggView" name="sggView" title="<spring:message code="sysLssBsc.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.sgg || searchVO.sgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="sggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${sggCodeInfo.adminCode}"/>"<c:if test="${searchVO.sgg eq sggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${sggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="emdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.emd"/></label>
									<select id="emdView" name="emdView" title="<spring:message code="sysLssBsc.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event,'list'); return false;">
										<option value=""<c:if test="${empty searchVO.emd || searchVO.emd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="emdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${emdCodeInfo.adminCode}"/>"<c:if test="${searchVO.emd eq emdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${emdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="riView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.ri"/></label>
									<select id="riView" name="riView" title="<spring:message code="sysLssBsc.stripLandVO.ri"/>">
										<option value=""<c:if test="${empty searchVO.ri || searchVO.ri == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="riCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${riCodeInfo.adminCode}"/>"<c:if test="${searchVO.ri eq riCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${riCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysLssBsc.stripLandVO.id"/></th><!-- 조사ID -->
								<td>
									<label for="sysLssBsc.svyComptVO.id" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.id"/></label>
									<input type="text" id="id" value="<c:out value='${searchVO.id}'/>" name="idView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysLssBsc.stripLandVO.writer"/></th><!-- 작성자 -->
								<td>
									<label for="sysLssBsc.svyComptVO.writer" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.writer"/></label>
									<input type="text" id="writer" value="<c:out value='${searchVO.writer}'/>" name="writerView" class="mo_mt5 input_null" />
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
					<col style="width: 9%;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
				</colgroup>
				<thead>
				  <tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="sysLssBsc.stripLandVO.id" /></th><!-- 조사ID -->
					<th><spring:message code="sysLssBsc.stripLandVO.type" /></th><!-- 조사유형 -->
					<th><spring:message code="sysLssBsc.stripLandVO.year"/></th><!-- 조사연도 -->
					<th><spring:message code="sysLssBsc.stripLandVO.writer"/></th><!-- 작성자 -->
					<th><spring:message code="sysLssBsc.stripLandVO.region" /></th><!-- 관할 -->
					<th></th>
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
					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
					<td><a href="<c:url value='/sys/lss/bsc/sld/selectLssBscStripLandDetail.do'/>?id=${resultInfo.id}" onClick="javascript:fncStripLandDetail('<c:out value="${resultInfo.id}"/>');return false;"><c:out value='${resultInfo.id}'/></a></td>
					<td><c:out value='${resultInfo.type}'/></td>
					<td><c:out value='${resultInfo.year}'/></td>
					<td><c:out value='${resultInfo.writer}'/></td>
					<td><c:out value='${resultInfo.region1}'/> <c:out value='${resultInfo.region2}'/></td>
					<td>
						<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fncStripLandDetail('<c:out value="${resultInfo.id}"/>');return false;"></button>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
				<button type="button" class="upload-btn" onclick="javascript:fncUploadStripLandDialog(); return false;"><spring:message code="button.bulkUpload" /></button>
					<button type="button" class="write-btn" onclick="javascript:fncAddStripLandInsertView(); return false;"><spring:message code="button.create" /></button>
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
	<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").submit();
	}
</script>