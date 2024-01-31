<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="roleManage" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngSecRmt.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 권한관리 상세조회 -->
	<div id="contents">
		<form:form id="listForm" commandName="roleManage" action="/mng/sec/rmt/roleUpdate.do" method="post">
		<input type="hidden" name="roleCode" value="<c:out value='${roleManage.roleCode}'/>"/>
		<input type="hidden" name="searchCondition" value="<c:out value='${roleManageVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${roleManageVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${roleManageVO.pageIndex}'/>"/>
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 롤아이디 -->
				<c:set var="title"><spring:message code="mngSecRam.list.rollId" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<c:out value="${roleManage.roleCode}"/>
					</td>
				</tr>
				<!-- 롤 명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollNm" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<form:input path="roleNm" title="${title} ${inputTxt}" size="40" maxlength="50" />
						<div><form:errors path="roleNm" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 롤 패턴 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollPtn" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<form:input path="rolePtn" title="${title} ${inputTxt}" size="40" maxlength="200" />
						<div><form:errors path="rolePtn" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 롤 설명 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollDc" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
					    <form:input path="roleDc" title="${title} ${inputTxt}" size="40" maxlength="200" />
						<div><form:errors path="roleDc" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 롤 타입 -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollType" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<form:select path="roleTyp">
							<form:options items="${cmmCodeDetailList}" itemValue="code" itemLabel="codeNm"/>
						</form:select>
						<div><form:errors path="roleTyp" cssClass="error" /></div> 
					</td>
				</tr>
				<!-- 롤 Sort -->
				<c:set var="title"><spring:message code="mngSecRam.regist.rollSort" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
						<form:input path="roleSort" title="${title} ${inputTxt}" size="40" maxlength="10" />
						<div><form:errors path="roleSort" cssClass="error" /></div> 
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fncRoleUpdate();"><spring:message code="title.update" /></button>
					<button type="button" class="list-btn" onclick="javascript:fnList('/mng/sec/rmt/roleList.do'); return false;"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>