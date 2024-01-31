<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysLssBsc.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역실태조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.list"/></h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/lss/wka/sct/selectWkaSvyComptList.do" method="post">
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
				<input name="mstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
				<input name="jdgmntgrad" type="hidden" value="<c:out value='${searchVO.jdgmntgrad}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<th><spring:message code="sysLssBsc.stripLandVO.type"/><span class="pilsu" title="<spring:message code="title.tootip.msg" arguments="엑셀다운로드" />">*</span></th><!-- 조사유형 -->
								<td>
									<label for="svyTypeView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.type"/></label>
									<select id="svyTypeView" name="svyTypeView" title="<spring:message code="sysLssBsc.stripLandVO.type"/>">
										<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><spring:message code="sysLssBsc.stripLandVO.date"/></th><!-- 조사일자 -->
								<td colspan="2">
									<label for="svyYearView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.year"/></label>
									<select id="svyYearView" name="svyYearView" title="<spring:message code="sysLssBsc.stripLandVO.year"/>" onchange="fncSvyYearChange(); return false;">
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
								<th><spring:message code="sysLssBsc.stripLandVO.addr"/></th><!-- 주소 -->
								<td colspan="4">
									<label for="svySdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sd"/></label>
									<select id="svySdView" name="svySdView" title="<spring:message code="sysLssBsc.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svySggView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sgg"/></label>
									<select id="svySggView" name="svySggView" title="<spring:message code="sysLssBsc.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyEmdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.emd"/></label>
									<select id="svyEmdView" name="svyEmdView" title="<spring:message code="sysLssBsc.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyRiView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.ri"/></label>
									<select id="svyRiView" name="svyRiView" title="<spring:message code="sysLssBsc.stripLandVO.ri"/>">
										<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
										<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th>등급</th>
								<td>
							        <label for="jdgmntgradA">A</label><input type="checkbox" class="rptWkaChk" id="jdgmntgradA" name="jdgmntgradChkBox" <c:if test="${fn:contains(searchVO.jdgmntgrad, 'A') }">checked</c:if> value="A등급"/>
							        <label for="jdgmntgradB">B</label><input type="checkbox" class="rptWkaChk" id="jdgmntgradB" name="jdgmntgradChkBox" <c:if test="${fn:contains(searchVO.jdgmntgrad, 'B') }">checked</c:if> value="B등급"/>
							        <label for="jdgmntgradC">C</label><input type="checkbox" class="rptWkaChk" id="jdgmntgradC" name="jdgmntgradChkBox" <c:if test="${fn:contains(searchVO.jdgmntgrad, 'C') }">checked</c:if> value="C등급"/>
								</td>
<%-- 								<th><spring:message code="sysLssBsc.svyComptVO.ncsstyAt"/></th><!-- 실태조사 필요여부 --> --%>
<!-- 								<td> -->
<%-- 									<label for="sysLssBsc.svyComptVO.ncsstyAt" class="Hidden"><spring:message code="sysLssBsc.svyComptVO.ncsstyAt"/></label> --%>
<%-- 									<select id="ncsstyView" name="ncsstyView" title="<spring:message code="sysLssBsc.svyComptVO.ncsstyAt"/>"> --%>
<%-- 										<option value=""<c:if test="${empty searchVO.ncssty || searchVO.ncssty == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option> --%>
<%-- 										<c:forEach var="ncsstyCodeInfo" items="${ncsstyCodeList}" varStatus="status"> --%>
<%-- 										<option value="<c:out value="${ncsstyCodeInfo.codeNm}"/>"<c:if test="${searchVO.ncssty eq ncsstyCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${ncsstyCodeInfo.codeNm}"/></option> --%>
<%-- 										</c:forEach> --%>
<!-- 									</select> -->
<!-- 								</td> -->
							</tr>
							<tr>
								<th><spring:message code="sysLssBsc.stripLandVO.id"/></th><!-- 조사ID -->
								<td>
									<label for="sysLssBsc.svyComptVO.svyId" class="Hidden"><spring:message code="sysLssBsc.svyComptVO.svyId"/></label>
									<input type="text" id="svyId" value="<c:out value='${searchVO.svyId}'/>" name="svyIdView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysLssBsc.svyComptVO.svyUser"/></th><!-- 조사자 -->
								<td>
									<label for="sysLssBsc.svyComptVO.svyUser" class="Hidden"><spring:message code="sysLssBsc.svyComptVO.svyUser"/></label>
									<input type="text" id="svyUser" value="<c:out value='${searchVO.svyUser}'/>" name="svyUserView" class="mo_mt5 input_null" />
								</td>
								<th><spring:message code="sysLssBsc.svyComptVO.mstNm"/></th><!-- 공유방명 -->
								<td colspan="2">
									<label for="sysLssBsc.svyComptVO.mstNm" class="Hidden"><spring:message code="sysLssBsc.svyComptVO.mstNm"/></label>
									<input type="text" id="mstNm" value="<c:out value='${searchVO.mstNm}'/>" name="mstNmView" class="mo_mt5 input_null" />
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
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong>
				<select name="pageSelect" onchange="fnSearch();">
					<option value="10"<c:if test="${searchVO.pageUnit == 10}">selected='selected'</c:if>>10개씩 보기</option>
					<option value="30"<c:if test="${searchVO.pageUnit == 30}">selected='selected'</c:if>>30개씩 보기</option>
					<option value="50"<c:if test="${searchVO.pageUnit == 50}">selected='selected'</c:if>>50개씩 보기</option>
					<option value="100"<c:if test="${searchVO.pageUnit == 100}">selected='selected'</c:if>>100개씩 보기</option>
					<option value="150"<c:if test="${searchVO.pageUnit == 150}">selected='selected'</c:if>>150개씩 보기</option>
				</select>
			<button class="download-btn fr" onclick="javascript:fnExcelDown(); return false;"><spring:message code="button.excel" /><spring:message code="title.download" /></button>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
			<button class="add-btn fr" onclick="javascript:fnExcelUpload(); return false;"><spring:message code="button.excel" /><spring:message code="button.create" /></button>
			<button class="reset-btn fr" onclick="javascript:fnUpdateLocReCreatePopup(); return false;">위치도재생성</button>
			<!-- <button class="add-btn fr" onclick="javascript:fnUpdatePhotoPopup(); return false;">현장사진 동기화</button> -->
			</sec:authorize>
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
					<th><spring:message code="sysLssWka.svyComptVO.svyId" /></th><!-- 조사ID -->
					<th><spring:message code="sysLssWka.svyComptVO.svyUser" /></th><!-- 조사자 -->
					<th><spring:message code="sysLssWka.svyComptVO.svyType" /></th><!-- 조사유형 -->
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
					<td><a href="<c:url value='/sys/lss/wka/sct/selectWkaSvyComptDetail.do'/>?gid=${resultInfo.gid}" onClick="javascript:fncSvyComptDetail('<c:out value="${resultInfo.gid}"/>');return false;"><c:out value='${resultInfo.svyId}'/></a></td>
					<td><c:out value='${resultInfo.svyUser}'/></td>
					<td><c:out value='${resultInfo.svyType}'/></td>
					<td><c:out value='${resultInfo.creatDt}'/></td>
					<td><c:out value='${resultInfo.lastUpdtPnttm}'/></td>
					<td>
						<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fncSvyComptDetail('<c:out value="${resultInfo.gid}"/>');return false;"></button>
						<button title="<spring:message code="button.movedMap"/>" class="map-btn-m" onclick="javascript:fncOpenMap('WKA','<c:out value="${resultInfo.gid}"/>'); return false;"></button>
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
						<button title="공간정보수정" class="modify-btn-m" onclick="javascript:fncSvyComptGeomView('<c:out value="${resultInfo.gid}"/>');return false;"></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasAnyRole('ROLE_USER_WKA','ROLE_USER_CNRS','ROLE_USER')">
								<c:if test="${resultInfo.svyUser ne userInfo.id or resultInfo.svyUser ne userInfo.name or fn:contains(resultInfo.svyUser,userInfo.name)}">
									<c:if test="${fn:contains(accessList, resultInfo.mstId)}">
										<button title="공간정보수정" class="modify-btn-m" onclick="javascript:fncSvyComptGeomView('<c:out value="${resultInfo.gid}"/>');return false;"></button>
									</c:if>
								</c:if>
								<c:if test="${resultInfo.svyUser eq userInfo.id or resultInfo.svyUser eq userInfo.name or fn:contains(resultInfo.svyUser,userInfo.name)}">
									<button title="공간정보수정" class="modify-btn-m" onclick="javascript:fncSvyComptGeomView('<c:out value="${resultInfo.gid}"/>');return false;"></button>
								</c:if>
							</sec:authorize>
						</sec:authorize>
						<button title="공간정보다운로드" class="save-btn-m" onclick="javascript:fncDownloadWkaSvyShp('<c:out value="${resultInfo.gid}"/>','<c:out value="${resultInfo.svyId}"/>'); return false;"></button>
						<button title="<spring:message code="button.openSurvey"/>" class="download-btn-m" onclick="javascript:fncOpenClipReport('<c:out value="${resultInfo.gid}"/>','<c:out value="${resultInfo.svyType}"/>','<c:out value='${fn:substring(resultInfo.creatDt,0,4)}'/>'); return false;"></button>
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
		$("#listForm").attr("action","/sys/lss/wka/sct/selectWkaSvyComptList.do");
		$("#listForm").submit();
	}
</script>