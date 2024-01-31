<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecRmt.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 롤관리 상세조회 -->
	<div id="contents">
		<form id="listForm" action="/mng/sec/rmt/roleList.do" method="post">
		<input type="hidden" name="roleCode" value="<c:out value='${roleManage.roleCode}'/>"/>
		<input type="hidden" name="searchCondition" value="<c:out value='${roleManageVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${roleManageVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${roleManageVO.pageIndex}'/>"/>
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 롤아이디 -->
				<c:set var="title"><spring:message code="mngSecRam.list.rollId" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.roleCode}"/></td>
				</tr>
				<!-- 롤 명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollNm" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.roleNm}"/></td>
				</tr>
				<!-- 롤 패턴 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollPtn" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.rolePtn}"/></td>
				</tr>
				<!-- 롤 설명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollDc" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.roleDc}"/></td>
				</tr>
				<!-- 롤 타입 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollType" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.roleTyp}"/></td>
				</tr>
				<!-- 롤 Sort -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollSort" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.roleSort}"/></td>
				</tr>
				<!-- 등록일자 -->
				<c:set var="title"><spring:message code="table.regdate" /></c:set>
				<tr>
					<th>${title}</th>
					<td><c:out value="${roleManage.roleCreatDe}"/></td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="modify-btn" onclick="javascript:fncDeleteRole('<c:out value="${roleManage.roleCode}"/>')"><spring:message code="title.delete" /></button>
				<button type="button" class="modify-btn" onclick="javascript:fncUpdateRoleView('<c:out value="${roleManage.roleCode}"/>')"><spring:message code="title.update" /></button>
				<button type="button" class="list-btn" onclick="javascript:fnList('/mng/sec/rmt/roleList.do'); return false;"><spring:message code="button.list" /></button>
			</div>
			</div>
		</div>
		</form>
	</div>
</div>
