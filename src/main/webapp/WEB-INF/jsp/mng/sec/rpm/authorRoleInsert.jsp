<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="authorRoleManage" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="comCopSecRam.authorRoleList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 권한관리 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="authorRoleManage" action="${pageContext.request.contextPath}/mng/sec/rpm/authorRoleInsert.do" method="post">
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption>${pageTitle} <spring:message code="title.create" /></caption>
				<tbody>
					<!-- 권한 ID -->
					<c:set var="title"><spring:message code="comCopSecRam.authorRoleList.authorId"/></c:set>
					<tr>
						<th scope="row">${title} <span class="pilsu">*</span></th>
						<td>
							<select id="authorCode" name="authorCode">
								<option value="" selected>--- 권한 선택 ---</option>
								<c:forEach var="result" items="${authorList}" varStatus="status">
									<option value="${result.authorCode}"><c:out value="${result.authorNm}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<!-- 롤 ID -->
					<c:set var="title"><spring:message code="comCopSecRam.authorRoleList.rollId"/></c:set>
					<tr>
						<th scope="row">${title} <span class="pilsu">*</span></th>
						<td>
							<select id="roleCode" name="roleCode">
								<option value="" selected>--- 롤 선택 ---</option>
								<c:forEach var="result" items="${roleList}" varStatus="status">
									<option value="${result.roleCode}"><c:out value="${result.roleNm}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="save-btn" onclick="javascript:fncAuthorRoleInsert();"><spring:message code="title.create" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/sec/rpm/authorRoleList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div> 