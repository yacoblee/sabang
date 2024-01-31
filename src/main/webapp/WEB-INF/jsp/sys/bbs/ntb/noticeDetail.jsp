<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysBbs.noticeVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">알림마당</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3>
	<div id="contents">	
		<form id="listForm" action="${pageContext.request.contextPath}/sys/bbs/ntb/selectNoticeDetail.do" method="post">
			<input type="hidden" name="bbsId" value="<c:out value="${resultList.bbsId}"/>"/>
			<input type="hidden" name="nttId" value="<c:out value="${resultList.nttId}"/>"/>
			<input name="schsearchCondition" type="hidden" value="<c:out value='${noticeVO.searchCondition}'/>"/>
			<input name="schsearchKeyword" type="hidden" value="<c:out value='${noticeVO.searchKeyword}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${noticeVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${noticeVO.pageUnit}'/>">
			<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
					<!-- 글 제목  -->
					<c:set var="title"><spring:message code="sysBbs.noticeVO.detail.nttSj" /></c:set>
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
						<th><spring:message code="sysBbs.noticeVO.detail.rdcnt"/></th>
						<td class="left"><c:out value="${resultList.rdcnt}"/></td>
					</tr>
					<!-- 글 내용 -->
					<c:set var="title"><spring:message code="sysBbs.noticeVO.detail.nttCn" /></c:set>
					<tr>
						<th class="vtop">${title}</th>
						<td colspan="5" class="cnt"><textarea style="resize:none; min-height: 300px; width: 100%; border: 1px solid #ddd;" cols="300" rows="20" readonly="readonly" disabled="disabled"><c:out value="${resultList.nttCn}" escapeXml="false"/></textarea></td>
					</tr>
				</tbody>	
			</table>
			
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="list-btn" onclick="javascript:fnList('notice'); return false;"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form>
	</div>
</div> 