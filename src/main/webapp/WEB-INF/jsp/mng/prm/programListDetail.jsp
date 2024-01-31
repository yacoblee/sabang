<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngPrm.programListDetail.title"/></c:set><!-- 프로그램목록 상세조회 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/mng/prm/programListUpdtSelect.do" method="post">
			<!-- 검색조건 유지 -->
		    <input name="checkProgrmFileNm" type="hidden" value="<c:out value='${progrmManageVO.progrmFileNm}'/>"/>
		    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
		    <div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="mngPrm.programListDetail.progrmFileNm"/></th><!-- 프로그램파일명 -->
							<td><c:out value="${progrmManageVO.progrmFileNm}"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="mngPrm.programListDetail.progrmStrePath"/></th><!-- 저장경로 -->
							<td><c:out value="${progrmManageVO.progrmStrePath}"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="mngPrm.programListDetail.progrmKoreanNm"/></th><!-- 한글명 -->
							<td><c:out value="${progrmManageVO.progrmKoreanNm}"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="mngPrm.programListDetail.url"/></th>
							<td><c:out value="${progrmManageVO.URL}"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="mngPrm.programListDetail.progrmDc"/></th><!-- 프로그램설명 -->
							<td><c:out value="${progrmManageVO.progrmDc}"></c:out></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:deleteProgramListManage(); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:updateProgramListManageView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>