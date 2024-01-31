<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="menuManageVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngMnuMpm.menuRegist.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3><!-- 메뉴정보 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="menuManageVO" name="menuManageVO" action="${pageContext.request.contextPath}/mng/mnu/mpm/menuRegistInsert.do" method="post">
			<input type="hidden" name="tmp_SearchElementName" value="">
			<input type="hidden" name="tmp_SearchElementVal" value="">
			<input name="cmd" type="hidden" value="<c:out value='insert'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption>${pageTitle} <spring:message code="title.create" /></caption>
					<colgroup>
						<col style="width:16%" />
						<col style="" />
						<col style="width:16%" />
						<col style="" />
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="mngMnuMpm.menuRegist.menuNo"/> <span class="pilsu">*</span></th><!-- 메뉴No -->
							<td>
							    <form:input path="menuNo" maxlength="10" title="<spring:message code='mngMnuMpm.menuRegist.menuNo'/>" cssStyle="width:50px" /><!-- 메뉴No -->
				      			<form:errors path="menuNo" />
							</td>
							<th><spring:message code="mngMnuMpm.menuRegist.menuOrder"/> <span class="pilsu">*</span></th><!-- 메뉴순서 -->
							<td>
							    <form:input path="menuOrdr" maxlength="10" title="<spring:message code='mngMnuMpm.menuRegist.menuOrder'/>" cssStyle="width:50px" /><!-- 메뉴순서 -->
				      			<form:errors path="menuOrdr" />
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuRegist.menuNm"/> <span class="pilsu">*</span></th><!-- 메뉴명 -->
							<td>
							    <form:input path="menuNm" maxlength="30" title="<spring:message code='mngMnuMpm.menuRegist.menuNm'/>" /><!-- 메뉴명 -->
				      			<form:errors path="menuNm" />
							</td>
							<th><spring:message code="mngMnuMpm.menuRegist.upperMenuId"/> <span class="pilsu">*</span></th><!-- 상위메뉴No -->
							<td>
							    <form:input path="upperMenuId" maxlength="10" title="<spring:message code='mngMnuMpm.menuRegist.upperMenuId'/>" readonly="true" class="readOnlyClass" cssStyle="width:50px" /><!-- 상위메뉴No -->
								<form:errors path="upperMenuId" />
								<button type="button" class="search-btn" onclick="javascript:fncPopupUpperMenuId(); return false;"><spring:message code="mngMnuMpm.menuRegist.selectMenuSearch"/></button>
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuRegist.progrmFileNm"/> <span class="pilsu">*</span></th><!--  -->
							<td colspan="3">
							    <form:input path="progrmFileNm" maxlength="60" onkeypress="press();" title="파일명" readonly="true" class="readOnlyClass" cssStyle="width:350px" /><!-- 파일명 -->
							    <form:errors path="progrmFileNm" />
							    <button type="button" class="search-btn" onclick="javascript:fncPopupProgrmFile(); return false;"><spring:message code="mngMnuMpm.menuRegist.programFileNameSearch"/></button>
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuRegist.relateImageNm"/> <span class="pilsu">*</span></th><!-- 관련이미지명 -->
							<td>
							    <form:input path="relateImageNm" maxlength="30" title="<spring:message code='mngMnuMpm.menuRegist.relateImageNm'/>"/><!-- 관련이미지명 -->
					      		<form:errors path="relateImageNm" />
							</td>
							<th><spring:message code="mngMnuMpm.menuRegist.relateImagePath"/> <span class="pilsu">*</span></th><!-- 관련이미지경로 -->
							<td>
							    <form:input path="relateImagePath" maxlength="30" title="<spring:message code='mngMnuMpm.menuRegist.relateImagePath'/>"/><!-- 관련이미지경로 -->
					      		<form:errors path="relateImagePath" />
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuRegist.menuDc"/> <span class="pilsu">*</span></th><!-- 메뉴설명 -->
							<td colspan="3">
							    <form:textarea path="menuDc" rows="14" cols="75" cssClass="txaClass" title="<spring:message code='mngMnuMpm.menuRegist.menuDc'/>" cssStyle="height:200px"/><!-- 메뉴설명 -->
				      			<form:errors path="menuDc"/>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="save-btn" onclick="javascript:fncInsertMenuManage(); return false;"><spring:message code="title.create" /></button>
						<button type="button" class="list-btn" onclick="location.href='/mng/mnu/mpm/menuManageSelect.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script>
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
</script>