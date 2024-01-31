<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysFckMse.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">계측장비</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/fck/mse/sct/selectMseComptList.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="sldid" type="hidden" value="">
				<input name="svystep" type="hidden" value="">
				<input name="updt" type="hidden" value="detail">
				<input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<input name="svyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
				<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
				<input name="svyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
				<input name="svyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
				<input name="mstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th>조사단계</th><!-- 조사유형 -->
								<td>
									<label for="svystepView" class="Hidden">조사단계</label>
									<select id="svystepView" name="svystepView" title="조사단계">
										<option value="">전체</option>
										<option value="1차">1차</option> 
										<option value="2차">2차</option>
										<option value="긴급">긴급</option>
									</select>
								</td>
								<th><spring:message code="sysFckMse.svyComptVO.date"/></th><!-- 조사일자 -->
								<td colspan="2">
									<label for="svyYearView" class="Hidden"><spring:message code="sysFckMse.svyComptVO.year"/></label>
									<select id="svyYearView" name="svyYearView" title="<spring:message code="sysFckMse.stripLandVO.year"/>" onchange="fncSvyYearChange(); return false;">
										<option value=""<c:if test="${empty searchVO.svyYear || searchVO.svyYear == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyYearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${svyYearCodeInfo.svyyear}"/>"<c:if test="${searchVO.svyYear eq svyYearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${svyYearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
									<label for="svyMonthView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.month"/></label>
									<select id="svyMonthView" name="svyMonthView" title="<spring:message code="sysLssBsc.stripLandVO.month"/>">
										<option value=""<c:if test="${empty searchVO.svyMonth || searchVO.svyMonth == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyMonthCodeInfo" items="${monthCodeList}" varStatus="status">
										<option value="<c:out value="${svyMonthCodeInfo.code}"/>"<c:if test="${searchVO.svyMonth eq svyMonthCodeInfo.code}">selected="selected"</c:if>><c:out value="${svyMonthCodeInfo.codeDc}"/></option>
										</c:forEach>
									</select>
								</td>								
							</tr>
							<tr>
								<th><spring:message code="sysFckMse.stripLandVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="svySdView" class="Hidden"><spring:message code="sysFckMse.stripLandVO.sd"/></label>
									<select id="svySdView" name="svySdView" title="<spring:message code="sysFckMse.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event,'compt'); return false;">
										<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svySggView" class="Hidden"><spring:message code="sysFckMse.stripLandVO.sgg"/></label>
									<select id="svySggView" name="svySggView" title="<spring:message code="sysFckMse.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event,'compt'); return false;">
										<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyEmdView" class="Hidden"><spring:message code="sysFckMse.stripLandVO.emd"/></label>
									<select id="svyEmdView" name="svyEmdView" title="<spring:message code="sysFckMse.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event,'compt'); return false;">
										<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyRiView" class="Hidden"><spring:message code="sysFckMse.stripLandVO.ri"/></label>
									<select id="svyRiView" name="svyRiView" title="<spring:message code="sysFckMse.stripLandVO.ri"/>">
										<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysFckMse.stripLandVO.id"/></th><!-- 조사ID -->
								<td>
									<label for="sysFckMse.svyComptVO.svyId" class="Hidden"><spring:message code="sysFckMse.svyComptVO.svyId"/></label>
									<input type="text" id="svyId" value="<c:out value='${searchVO.svyId}'/>" name="svyIdView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysFckMse.svyComptVO.svyUser"/></th><!-- 조사자 -->
								<td>
									<label for="sysFckMse.svyComptVO.svyUser" class="Hidden"><spring:message code="sysFckMse.svyComptVO.svyUser"/></label>
									<input type="text" id="svyUser" value="<c:out value='${searchVO.svyUser}'/>" name="svyUserView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysFckMse.svyComptVO.mstNm"/></th><!-- 공유방명 -->
								<td colspan="2">
									<label for="sysFckMse.svyComptVO.mstNm" class="Hidden"><spring:message code="sysFckMse.svyComptVO.mstNm"/></label>
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
			</span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 15%;">
					<col style="width: 10%;">
					<col style="width: 15%;">
					<col style="width: 15%;">
					<col style="width: 17%;">
					<col style="width: 18%;">
				</colgroup>
				<thead>
				  <tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="sysFckMse.svyComptVO.svyId" /></th><!-- 조사ID -->
					<th><spring:message code="sysFckMse.svyComptVO.svyUser" /></th><!-- 조사자 -->
					<th><spring:message code="sysFckMse.svyComptVO.svyType"/></th><!-- 조사유형 -->
					<th><spring:message code="table.regdate" /></th><!-- 등록일 -->
					<th><spring:message code="table.updtdate" /></th><!-- 최근수정일 -->
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
					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageUnit + status.count}"/></td>
					<td><a href="<c:url value='/sys/fck/mse/sct/selectFckMseComptDetail.do'/>?gid=${resultInfo.gid}" onClick="fncMseComptUpdt('<c:out value="${resultInfo.sldid}"/>','<c:out value="${resultInfo.sldid}"/>');return false;"><c:out value='${resultInfo.sldid}'/></a></td>
					<td><c:out value='${resultInfo.svyUser}'/></td>
					<td><c:out value='${resultInfo.svystep}'/></td>
					<td><c:out value='${resultInfo.creatDt}'/></td>
					<td><c:out value='${resultInfo.lastUpdtPnttm}'/></td>
					<td class="tag-center pl5">
						<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fncMseComptUpdt('<c:out value="${resultInfo.sldid}"/>','<c:out value="${resultInfo.svystep}"/>');return false;"></button>
						<button title="<spring:message code="button.excel" /><spring:message code="title.download" />" class="download-btn-m" onclick="javascript:fnExcelDown('<c:out value="${resultInfo.sldid}"/>','<c:out value="${resultInfo.svystep}"/>'); return false;"></button>
						<button title="현장사진 다운로드" class="save-btn-m" onclick="javascript:fnPhotoDown('<c:out value="${resultInfo.sldid}"/>','<c:out value="${resultInfo.svystep}"/>');return false;"></button>
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
		$("#listForm").attr("action","/sys/fck/mse/sct/selectMseComptList.do");
		$("#listForm").submit();
	}
</script>