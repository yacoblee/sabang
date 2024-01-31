<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle"><spring:message code="mngLogWlg.webLog.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">로그관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/log/wlg/selectWebLogList.do" method="post">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
				<input name="searchCnd" type="hidden" value="<c:out value='${searchVO.searchCnd}'/>">
				<input name="searchWrd" type="hidden" value="<c:out value='${searchVO.searchWrd}'/>">
				<input name="searchBgnDe" type="hidden" value="<c:out value='${searchVO.searchBgnDe}'/>">
				<input name="searchEndDe" type="hidden" value="<c:out value='${searchVO.searchEndDe}'/>">
				<div class="search">
					<table  class="tableWid_L" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
<%-- 						<colgroup> --%>
<%-- 							<col style="width: 10%;"> --%>
<%-- 							<col style="width: 90%;"> --%>
<%-- 						</colgroup> --%>
						<tbody>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchCndView" class="Hidden">검색구분</label>
									<select id="searchCndView" name="searchCndView" title="<spring:message code="select.searchCondition" />">
										<option value="0" <c:if test="${empty searchVO.searchCnd || searchVO.searchCnd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if>><spring:message code="mngLogWlg.webLog.requester" /></option>
										<option value="2" <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if>><spring:message code="mngLogWlg.webLog.url" /></option>
										<option value="3" <c:if test="${searchVO.searchCnd == '3'}">selected="selected"</c:if>><spring:message code="mngLogWlg.webLog.requesterIp" /></option>
									</select>
									<label for="searchWrdView" class="Hidden">검색어입력창</label>
									<input type="text" id="searchWrdView" name="searchWrdView" class="mo_mt5 input_null" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value="<c:out value="${searchVO.searchWrd}"/>" />
								</td>
								<th>날짜</th>
								<td>
									<label for="sDate" class="Hidden"><spring:message code="mngLogClg.seachWrd.searchBgnDe" /></label>
									<input type="date" id="sDate" class="dtPicker" name="searchBgnDeView" value="<c:out value='${searchVO.searchBgnDe}'/>" placeholder="YYYYMMDD"/> ~ 
									<label for="eDate" class="Hidden"><spring:message code="mngLogClg.seachWrd.searchEndDe" /></label>
									<input type="date" id="eDate" class="dtPicker" name="searchEndDeView" value="<c:out value='${searchVO.searchEndDe}'/>" placeholder="YYYYMMDD"/>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">조회</button>
						<button type="button" class="download-btn" onclick="javascript:fnExcelDown(); return false;">다운로드</button>
					</div>
				</div>
			</form>
		</fieldset>
		<div class="BoardList">
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle}<spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 5%;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: 10%;">
					<col style="width: 15%;">
				</colgroup>
				<thead>
				<tr>
					<th><spring:message code="table.num" /></th><!-- 번호 -->
					<th><spring:message code="mngLogWlg.webLog.requestId" /></th><!-- 요청ID -->
					<th><spring:message code="mngLogWlg.webLog.occrrncDe" /></th><!-- 발생일자 -->
					<th><spring:message code="mngLogWlg.webLog.url" /></th><!-- URL -->
					<th><spring:message code="mngLogWlg.webLog.requester" /></th><!-- 요청자 -->
					<th><spring:message code="mngLogWlg.webLog.requesterIp" /></th><!-- 요청자IP -->
				</tr>
				</thead>
				<tbody class="ov">
				<c:if test="${fn:length(resultList) == 0}">
				<tr>
					<td colspan="7"><spring:message code="common.nodata.msg" /></td>
				</tr>
				</c:if>
				<c:forEach items="${resultList}" var="result" varStatus="status">
				<tr>
					<td><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
					<td><c:out value='${result.requstId}'/></td>
					<td><c:out value='${fn:substring(result.occrrncDe,0,19)}'/></td>
					<td><c:out value='${result.url}'/></td>
					<td><c:out value='${result.rqsterNm}'/></td>
					<td><c:out value='${result.rqesterIp}'/></td>
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
		$("#listForm").submit();
	}
</script>

<!-- 
<script type="text/javascript">
/*********************************************************
 * 초기화
 ******************************************************** */
function fn_egov_init(){
	// 첫 입력란에 포커스..
	document.WebLogForm.searchWrd.focus();
}
/* ********************************************************
 * 달력
 ******************************************************** */
function fn_egov_init_date(){
	
	$("#searchBgnDe").datepicker(  
	        {dateFormat:'yy-mm-dd'
	         , showOn: 'button'
	         , buttonImage: '<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif'/>'
	         , buttonImageOnly: true
	         
	         , showMonthAfterYear: true
	         , showOtherMonths: true
		     , selectOtherMonths: true
				
	         , changeMonth: true // 월선택 select box 표시 (기본은 false)
	         , changeYear: true  // 년선택 selectbox 표시 (기본은 false)
	         , showButtonPanel: true // 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});


	$("#searchEndDe").datepicker( 
	        {dateFormat:'yy-mm-dd'
	         , showOn: 'button'
	         , buttonImage: '<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif'/>'  
	         , buttonImageOnly: true
	         
	         , showMonthAfterYear: true
	         , showOtherMonths: true
		     , selectOtherMonths: true
				
	         , changeMonth: true // 월선택 select box 표시 (기본은 false)
	         , changeYear: true  // 년선택 selectbox 표시 (기본은 false)
	         , showButtonPanel: true // 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
}
/*********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function fn_egov_select_linkPage(pageNo){
	document.WebLogForm.pageIndex.value = pageNo;
	document.WebLogForm.action = "<c:url value='/sym/log/wlg/SelectWebLogList.do'/>";
   	document.WebLogForm.submit();
}
/*********************************************************
 * 조회 처리 함수
 ******************************************************** */
function fn_egov_search_webLog(){
	var vFrom = document.WebLogForm;

	 if(vFrom.searchEndDe.value != ""){
	     if(vFrom.searchBgnDe.value > vFrom.searchEndDe.value){
	         alert("<spring:message code="mngLogWlg.validate.dateCheck" />"); //검색조건의 시작일자가 종료일자보다  늦습니다. 검색조건 날짜를 확인하세요!
	         return;
		  }
	 }else{
		 vFrom.searchEndDe.value = "";
	 }

	vFrom.pageIndex.value = "1";
	vFrom.action = "<c:url value='/sym/log/wlg/SelectWebLogList.do'/>";
	vFrom.submit();
}
/* ********************************************************
 * 상세회면 처리 함수
 ******************************************************** */
function fn_egov_detail_webLog(requstId) {
  	var width = 850;
  	var height = 330;
  	var top = 0;
  	var left = 0;
  	var url = "<c:url value='/sym/log/wlg/SelectWebLogDetail.do' />?requstId="+requstId;
  	var name = "WebLogDetailPopup"
  	var openWindows = window.open(url,name, "width="+width+",height="+height+",top="+top+",left="+left+",toolbar=no,status=no,location=no,scrollbars=yes,menubar=no,resizable=yes");
}
</script>
 -->