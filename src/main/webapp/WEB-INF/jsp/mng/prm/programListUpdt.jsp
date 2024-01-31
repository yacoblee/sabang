<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="progrmManageVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngPrm.programListUpdt.title" /></c:set><!-- 프로그램목록 수정 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">프로그램관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" commandName="progrmManageVO" action="${pageContext.request.contextPath}/mng/prm/programListUpdt.do" method="post">
			<input name="cmd" type="hidden" value="<c:out value='update'/>"/>
			<input name="checkProgrmFileNm" type="hidden" value="<c:out value='${progrmManageVO.progrmFileNm}'/>"/>
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption>${pageTitle} <spring:message code="title.create" /></caption>
					<tbody>
						<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<!-- 프로그램파일명 -->
						<c:set var="title"><spring:message code="mngPrm.programListDetail.progrmFileNm"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:input path="progrmFileNm" size="50"  maxlength="50" id="F1" title="${title} ${inputTxt}" readonly="true"/>
      							<form:errors path="progrmFileNm" />    
							</td>
						</tr>
						
						<!-- 저장경로 -->
						<c:set var="title"><spring:message code="mngPrm.programListDetail.progrmStrePath"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
							    <form:input path="progrmStrePath"  size="60"   maxlength="60" title="${title} ${inputTxt}"/>
				      			<form:errors path="progrmStrePath" />
							</td>
						</tr>
						
						<!-- 한글명 -->
						<c:set var="title"><spring:message code="mngPrm.programListDetail.progrmKoreanNm"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:input path="progrmKoreanNm" size="60"  maxlength="60" title="${title} ${inputTxt}"/>
      							<form:errors path="progrmKoreanNm"/>
							</td>
						</tr>
						
						<!-- 한글명 -->
						<c:set var="title"><spring:message code="mngPrm.programListDetail.url"/> </c:set>
						<tr>
							<th>${title} <span class="pilsu">*</span></th>
							<td>
								<form:input path="URL" size="60" maxlength="60" title="${title} ${inputTxt}"/>
      							<form:errors path="URL"/>
							</td>
						</tr>
						
						<!-- 프로그램설명 -->
						<c:set var="title"><spring:message code="mngPrm.programListDetail.progrmDc"/> </c:set>
						<tr>
							<th>${title}</th>
							<td>
								<form:textarea path="progrmDc" rows="14" cols="103" cssClass="txaClass" title="${title} ${inputTxt}"/>
      							<form:errors path="progrmDc"/>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="save-btn" onclick="javascript:updateProgramListManage(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script>
<c:if test="${!empty resultMsg}">alert("<c:out value='${resultMsg}'/>");</c:if>
</script>