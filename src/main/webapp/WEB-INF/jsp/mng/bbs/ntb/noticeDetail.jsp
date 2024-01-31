<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="mngBbs.noticeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">공지사항관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3>
	<div id="contents">	
		<form id="listForm" action="${pageContext.request.contextPath}/mng/bbs/ntb/selectNoticeDetail.do" method="post">
			<input type="hidden" name="bbsId" value="<c:out value="${resultList.bbsId}"/>"/>
			<input type="hidden" name="nttId" value="<c:out value="${resultList.nttId}"/>"/>
			<input name="searchCondition" type="hidden" value="<c:out value='${resultList.searchCondition}'/>"/>
			<input name="searchKeyword" type="hidden" value="<c:out value='${resultList.searchKeyword}'/>"/>
			<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
					<!-- 글 제목  -->
					<c:set var="title"><spring:message code="mngBbs.noticeVO.detail.nttSj" /></c:set>
					<tr>
						<th>${title}</th>
						<td colspan="5" class="left"><c:out value="${resultList.nttSj}"/></td>
					</tr>
					<!-- 작성자, 작성시간, 조회수 -->
					<tr>
						<th><spring:message code="table.reger"/></th>
						<td class="left"><c:out value="${resultList.frstRegisterId}"/></td>
						<th><spring:message code="table.regdate"/></th>
						<td class="left"><c:out value="${resultList.frstRegisterPnttm}"/></td>
						<th><spring:message code="mngBbs.noticeVO.detail.rdcnt"/></th>
						<td class="left"><c:out value="${resultList.rdcnt}"/></td>
					</tr>
					<!-- 글 내용 -->
					<c:set var="title"><spring:message code="mngBbs.noticeVO.detail.nttCn" /></c:set>
					<tr>
						<th class="vtop">${title}</th>
						<td colspan="5" class="cnt"><textarea style="resize:none; min-height: 300px; width: 100%; border: 1px solid #ddd;" cols="300" rows="20" readonly="readonly" disabled="disabled"><c:out value="${resultList.nttCn}"/></textarea></td>
					</tr>
				</tbody>	
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<sec:authorize access="isAuthenticated()">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<c:if test="${result.frstRegisterId ne userId}">
								<button type="button" class="del-btn" onclick="javascript:fncNoticeDelete('<c:out value="${resultList.nttId}"/>')"><spring:message code="title.delete" /></button>
								<button type="button" class="modify-btn" onclick="javascript:fncUpdateNoticeView('<c:out value="${resultList.nttId}"/>')"><spring:message code="title.update" /></button>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USER')">
							<c:if test="${result.frstRegisterId eq userId}">
								<button type="button" class="del-btn" onclick="javascript:fncNoticeDelete('<c:out value="${resultList.nttId}"/>')"><spring:message code="title.delete" /></button>
								<button type="button" class="modify-btn" onclick="javascript:fncUpdateNoticeView('<c:out value="${resultList.nttId}"/>')"><spring:message code="title.update" /></button>
							</c:if>
						</sec:authorize>
					</sec:authorize>
					<button type="button" class="list-btn" onclick="location.href='/mng/bbs/ntb/noticeList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form>
	</div>
</div> 