<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="articleVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngBbs.articleVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">게시물관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 권한관리 수정 -->
	<div id="contents">
		<form:form id="listForm" commandName="articleVO" action="/mng/bbs/art/articleUpdate.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="nttId" value="<c:out value='${articleVO.nttId}'/>"/>
		<input type="hidden" name="bbsId" value="<c:out value='${articleVO.bbsId}'/>"/>
		<input type="hidden" name="searchCondition" value="<c:out value='${articleVO.searchCondition}'/>"/>
		<input type="hidden" name="searchKeyword" value="<c:out value='${articleVO.searchKeyword}'/>"/>
		<input type="hidden" name="pageIndex" value="<c:out value='${articleVO.pageIndex}'/>"/> 
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
				<!-- 입력 -->
				<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
				<!-- 글 제목 -->
				<c:set var="title"><spring:message code="mngBbs.articleVO.updt.nttSj" /></c:set>
				<tr>
					<th><label for="nttSj">${title}<span class="pilsu">*</span></label></th>
					<td class="left">
						<form:input path="nttSj" title="${title} ${inputTxt }" size="70" maxlength="70" />
						<div>	<form:errors path="nttSj" cssClass="error" /></div>
					</td>
				</tr>
				<!-- 작성자 -->
				<c:set var="title"><spring:message code="table.reger" /></c:set>
				<tr>
					<th>${title} <span class="pilsu">*</span></th>
					<td>
					    <c:out value="${articleVO.frstRegisterId}"/>
					</td>
				</tr>
				<!-- 글 내용  -->
				<c:set var="title"><spring:message code="mngBbs.articleVO.updt.nttCn" /></c:set>
				<tr>
					<th><label for="nttCn">${title}<span class="pilsu">*</span></label></th>
					<td class="nopd" colspan="3">
						<form:textarea path="nttCn" title="${title} ${inputTxt}" cols="300" rows="20" style="resize:none; min-height: 300px; width: 100%; border: 1px solid #ddd;" />
						
						<div>	<form:errors path="nttCn" cssClass="error" /></div>
					</td>
				</tr>
				<c:if test="${not empty articleVO.atchFileId}">
					<!-- 첨부파일 시작 -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.atchFile"/></c:set>
					<tr>
						<th>${title}</th>
						<td class="nopd" colspan="3">
							<c:import url="/mng/bbs/art/selectFileInfsForUpdate.do" charEncoding="utf-8">
								<c:param name="param_atchFileId" value="${articleVO.atchFileId}" />
							</c:import>
						</td>
					</tr>
					<!-- 첨부파일 끝 -->
				</c:if>
					<!-- 첨부파일 추가 시작 -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.atchFileAdd"/></c:set>
					<tr>
						<th><label for="file_1">${title}</label> </th>
						<td class="nopd" colspan="3">
							<input name="file_1" id="egovComFileUploader" type="file" title="<spring:message code="comCopBbs.articleVO.updt.atchFile"/>" multiple/><!-- 첨부파일 -->
						    <div id="egovComFileList"></div>
						</td>
					</tr>
					<!-- 첨부파일 추가 끝 -->
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fncArticleUpdate();"><spring:message code="title.update" /></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/bbs/art/articleList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>