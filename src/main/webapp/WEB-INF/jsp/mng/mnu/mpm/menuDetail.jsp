<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="mngMnuMpm.menuDetail.title"/></c:set><!-- 메뉴상세조회 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 공통코드관리 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/mng/mnu/mpm/menuManageListUpdateView.do" method="post">
			<input type="hidden" name="menuNo" value="<c:out value="${menuManageVO.menuNo}"/>"/>
			<input type="hidden" name="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>"/>
			<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
			<input name="cmd"    type="hidden"   value="select"/>
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetail.menuNo"/> <span class="pilsu">*</span></th><!-- 메뉴No -->
							<td><c:out value="${menuManageVO.menuNo}"/></td>
							<th><spring:message code="mngMnuMpm.menuDetail.menuOrder"/> <span class="pilsu">*</span></th><!-- 메뉴순서 -->
							<td><c:out value="${menuManageVO.menuOrdr}"/></td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetail.menuNm"/> <span class="pilsu">*</span></th><!-- 메뉴명 -->
							<td><c:out value="${menuManageVO.menuNm}"/></td>
							<th><spring:message code="mngMnuMpm.menuDetail.upperMenuId"/> <span class="pilsu">*</span></th><!-- 상위메뉴No -->
							<td><c:out value="${menuManageVO.upperMenuId}"/></td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetail.progrmFileNm"/> <span class="pilsu">*</span></th><!-- 파일명 -->
							<td colspan="3"><c:out value="${menuManageVO.progrmFileNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetail.relateImageNm"/> <span class="pilsu">*</span></th><!-- 관련이미지명 -->
							<td><c:out value="${menuManageVO.relateImageNm}"/></td>
							<th><spring:message code="mngMnuMpm.menuDetail.relateImagePath"/> <span class="pilsu">*</span></th><!-- 관련이미지경로 -->
							<td><c:out value="${menuManageVO.relateImagePath}"/></td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetail.menuDc"/> <span class="pilsu">*</span></th><!-- 메뉴설명 -->
							<td colspan="3"><c:out value="${menuManageVO.menuDc}"/></td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="del-btn" onclick="javascript:fncDeleteMenuManage('<c:out value="${result.codeId}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateMenuManageView(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>