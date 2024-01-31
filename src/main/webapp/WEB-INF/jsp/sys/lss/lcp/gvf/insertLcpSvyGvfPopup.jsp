<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="" commandName="" method="post" action="/sys/lss/lcp/gvf/insertLcpSvyGvf.do">
    	<div>
    		<label for="svysldNm" class="Hidden"><spring:message code="sysLssLcp.lcpGvfVO.sldNm"/></label>
			<select id="svysldNm" name="svysldNm" title="<spring:message code="sysLssLcp.lcpGvfVO.sldNm"/>" style="width:100%;">
	    		<c:forEach items="${resultList}" var="result" varStatus="status">
	    			<option value="<c:out value="${result.id}"/>"><c:out value="${result.svysldNm }"/></option>
	    		</c:forEach>
    		</select>
    	</div>
        <div class="drag-div drag-active">
			<p class="drag-msg noselect">파일을 드래그하세요.</p>
		</div>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnInsertLcpSvyGvf();"><spring:message code="title.create" /></button>
			</div>
		</div>
		</form:form>
    </div>
</div>