<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysFckPcs.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/fck/pcs/sct/selectPcsSvyComptList.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<input name="svyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
				<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
				<input name="svyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
				<input name="svyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
				<input name="fckRslt" type="hidden" value="<c:out value='${searchVO.fckrslt}'/>"/>
				<input name="mstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysFckPcs.stripLandVO.type"/><span class="pilsu" title="<spring:message code="title.tootip.msg" arguments="엑셀다운로드" />">*</span></th><!-- 조사유형 -->
								<td>
									<label for="svyTypeView" class="Hidden"><spring:message code="sysFckPcs.stripLandVO.type"/></label>
									<select id="svyTypeView" name="svyTypeView" title="<spring:message code="sysFckPcs.stripLandVO.type"/>">
										<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysFckPcs.svyComptVO.date"/></th><!-- 조사일자 -->
								<td colspan="2">
									<label for="svyYearView" class="Hidden"><spring:message code="sysFckPcs.svyComptVO.year"/></label>
									<select id="svyYearView" name="svyYearView" title="<spring:message code="sysFckPcs.stripLandVO.year"/>" onchange="fncSvyYearChange(); return false;">
										<option value=""<c:if test="${empty searchVO.svyYear || searchVO.svyYear == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyYearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${svyYearCodeInfo.svyyear}"/>"<c:if test="${searchVO.svyYear eq svyYearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${svyYearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
									<label for="svyMonthView" class="Hidden"><spring:message code="sysFckPcs.stripLandVO.month"/></label>
									<select id="svyMonthView" name="svyMonthView" title="<spring:message code="sysFckPcs.stripLandVO.month"/>">
										<option value=""<c:if test="${empty searchVO.svyMonth || searchVO.svyMonth == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyMonthCodeInfo" items="${monthCodeList}" varStatus="status">
										<option value="<c:out value="${svyMonthCodeInfo.code}"/>"<c:if test="${searchVO.svyMonth eq svyMonthCodeInfo.code}">selected="selected"</c:if>><c:out value="${svyMonthCodeInfo.codeDc}"/></option>
										</c:forEach>
									</select>
								</td>								
							</tr>
							<tr>
								<th><spring:message code="sysFckPcs.stripLandVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="svySdView" class="Hidden"><spring:message code="sysFckPcs.stripLandVO.sd"/></label>
									<select id="svySdView" name="svySdView" title="<spring:message code="sysFckPcs.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svySggView" class="Hidden"><spring:message code="sysFckPcs.stripLandVO.sgg"/></label>
									<select id="svySggView" name="svySggView" title="<spring:message code="sysFckPcs.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyEmdView" class="Hidden"><spring:message code="sysFckPcs.stripLandVO.emd"/></label>
									<select id="svyEmdView" name="svyEmdView" title="<spring:message code="sysFckPcs.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyRiView" class="Hidden"><spring:message code="sysFckPcs.stripLandVO.ri"/></label>
									<select id="svyRiView" name="svyRiView" title="<spring:message code="sysFckPcs.stripLandVO.ri"/>">
										<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysFckPcs.svyComptVO.lastFckRslt"/></th><!-- 최종점검결과 -->
								<td>
									<label for="fckRsltView" class="Hidden"><spring:message code="sysFckPcs.svyComptVO.lastFckRslt"/></label>
									<select id="fckRsltView" name="fckRsltView" title="<spring:message code="sysFckPcs.svyComptVO.lastFckRslt"/>">
										<option value=""<c:if test="${empty searchVO.fckrslt || searchVO.fckrslt == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="fckRsltCodeInfo" items="${fckRsltCodeList}" varStatus="status">
										<option value="<c:out value="${fckRsltCodeInfo.codeNm}"/>"<c:if test="${searchVO.fckrslt eq fckRsltCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${fckRsltCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><spring:message code="sysFckPcs.stripLandVO.svyId"/></th><!-- 조사ID -->
								<td>
									<label for="sysFckPcs.svyComptVO.svyId" class="Hidden"><spring:message code="sysFckPcs.svyComptVO.svyId"/></label>
									<input type="text" id="svyId" value="" name="svyIdView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysFckPcs.svyComptVO.svyUser"/></th><!-- 조사자 -->
								<td>
									<label for="sysFckPcs.svyComptVO.svyUser" class="Hidden"><spring:message code="sysFckPcs.svyComptVO.svyUser"/></label>
									<input type="text" id="svyUser" value="" name="svyUserView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysFckPcs.svyComptVO.mstNm"/></th><!-- 공유방명 -->
								<td colspan="2">
									<label for="sysFckPcs.svyComptVO.mstNm" class="Hidden"><spring:message code="sysFckPcs.svyComptVO.mstNm"/></label>
									<input type="text" id="mstNm" value="" name="mstNmView" class="mo_mt5 input_null" />
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
			<button class="download-btn fr" onclick="javascript:fnExcelDown(); return false;"><spring:message code="button.excel" /><spring:message code="title.download" /></button>
			<button class="add-btn fr" onclick="javascript:fnUpdateFckPcsComptPopup(); return false;"><spring:message code="button.excel" /><spring:message code="button.create" /></button>
			<button class="reset-btn fr" onclick="javascript:fnUpdateLocReCreatePopup(); return false;">위치도재생성</button>
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
					<th><spring:message code="sysFckPcs.svyComptVO.svyId" /></th><!-- 조사ID -->
					<th><spring:message code="sysFckPcs.svyComptVO.svyUser" /></th><!-- 조사자 -->
					<th><spring:message code="sysFckPcs.svyComptVO.svyType"/></th><!-- 조사유형 -->
					<th><spring:message code="table.regdate" /></th><!-- 등록일 -->
					<th><spring:message code="table.updtdate" /></th><!-- 최근수정일 -->
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
							<td><a href="<c:url value='/sys/fck/pcs/sct/selectFckPcsComptDetail.do'/>?gid=${resultInfo.gid}" onclick="fncPcsComptDetail('<c:out value="${resultInfo.gid}"/>');return false;"><c:out value='${resultInfo.svyId}'/></a></td>
							<td><c:out value='${resultInfo.svyUser}'/></td>
							<td><c:out value='${resultInfo.svyType}'/></td>
							<td><c:out value='${resultInfo.creatDt}'/></td>
							<td><c:out value='${resultInfo.lastUpdtPnttm}'/></td>
							<td>
								<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fncPcsComptDetail('<c:out value="${resultInfo.gid}"/>');return false;"></button>
								<button title="<spring:message code="button.movedMap"/>" class="map-btn-m" onclick="javascript:fncOpenMap('PCS','<c:out value="${resultInfo.gid}"/>'); return false;"></button>
								<button title="<spring:message code="button.openSurvey"/>" class="download-btn-m" onclick="javascript:fncOpenClipReport('<c:out value="${resultInfo.gid}"/>','<c:out value="${resultInfo.svyType}"/>'); return false;"></button>
								<button title="현장사진 <spring:message code="title.download"/>" class="photo-btn-m" onclick="javascript:fncDownloadPhotoListZip('<c:out value="${resultInfo.gid}"/>');return false;" <c:if test="${resultInfo.photoLen eq 0}">disabled</c:if>></button>
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
		$("#listForm").attr("action","/sys/fck/pcs/sct/selectPcsSvyComptList.do");
		$("#listForm").submit();
	}
</script>