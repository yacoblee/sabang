<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="mngPrm.programListManage.title"/></c:set><!-- 프로그램목록리스트 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/mng/prm/programListManageSelect.do" method="post">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
				<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
				<input name="checkedProgrmFileNmForDel" type="hidden" />
				<input name="cmd" type="hidden" >
				<input name="tmp_progrmNm" type="hidden" >
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th><spring:message code="mngPrm.programListManage.programName" /></th><!-- 프로그램명 -->
								<td>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
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
					<col style="width:20px" />
					<col style="" />
					<col style="width:137px" />
					<col style="width:260px" />
					<col style="width:150px" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" name="checkAll" class="check2" onclick="fCheckAll();" title="전체선택" /></th>
					   	<th scope="col"><spring:message code="mngPrm.programListManage.programFileName" /></th><!-- 프로그램파일명 -->
					   	<th scope="col"><spring:message code="mngPrm.programListManage.programName" /></th><!-- 프로그램명 -->
					   	<th scope="col">URL</th>
					   	<th scope="col"><spring:message code="mngPrm.programListManage.ProgramDescription" /></th><!-- 프로그램설명 -->
					</tr>
				</thead>
				<tbody>
					<%-- 데이터를 없을때 화면에 메세지를 출력해준다 --%>
					<c:if test="${fn:length(list_progrmmanage) == 0}">
					<tr>
						<td colspan="5">
							<spring:message code="common.nodata.msg" />
					 	</td>
					</tr>
					</c:if>
					 
					<c:forEach var="result" items="${list_progrmmanage}" varStatus="status">
					<tr>
						<td>
					    	<input type="checkbox" name="checkField" class="check2" title="선택">
					       	<input name="checkProgrmFileNm" type="hidden" value="<c:out value='${result.progrmFileNm}'/>"/>
					    </td>
					    <td>
				            <span class="link"><a href="<c:url value='/mng/prm/programListDetailSelect.do'/>?tmp_progrmNm=<c:out value="${result.progrmFileNm}"/>"  onclick="selectUpdtProgramListDetail('<c:out value="${result.progrmFileNm}"/>'); return false;">
				
				            <c:if test="${fn:length(result.progrmFileNm)> 22}">
					    	<c:out value="${fn:substring(result.progrmFileNm,0, 22)}"/>...
						    </c:if>
						    <c:if test="${fn:length(result.progrmFileNm)<= 22}">
					    	<c:out value="${result.progrmFileNm}"/>
						    </c:if>
				
				            </a></span>
					    </td>
					    <td>
					    <c:if test="${fn:length(result.progrmKoreanNm)> 12}">
					    	<c:out value="${fn:substring(result.progrmKoreanNm,0, 12)}"/>...
					    </c:if>
					    <c:if test="${fn:length(result.progrmKoreanNm)<= 12}">
					    	<c:out value="${result.progrmKoreanNm}"/>
					    </c:if>
					    </td>
					    <td>
					    <c:if test="${fn:length(result.URL)> 35}">
					    	<c:out value="${fn:substring(result.URL,0, 35)}"/>...
					    </c:if>
					    <c:if test="${fn:length(result.URL)<= 35}">
					    	<c:out value="${result.URL}"/>
					    </c:if>
					
					    </td>
					    <td><c:out value="${result.progrmDc}"/></td>
					  </tr>
					 </c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
<!-- 					<button type="button" class="write-btn" onclick="javascript:insertMultiProgramListManageView(); return false;">일괄등록</button> -->
					<button type="button" class="write-btn" onclick="javascript:insertProgramListManageView(); return false;"><spring:message code="button.create" /></button>
					<button type="button" class="del-btn" onclick="javascript:fDeleteProgrmManageList(); return false;"><spring:message code="button.delete" /></button>
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
<c:if test="${!empty resultMsg}">alert("<c:out value='${resultMsg}'/>");</c:if>

function linkPage(pageNo){
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>