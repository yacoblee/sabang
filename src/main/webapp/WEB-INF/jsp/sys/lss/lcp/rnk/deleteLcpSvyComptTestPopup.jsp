<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="deleteForm" method="post" action="/sys/lss/lcp/rnk/deleteLcpSvyComptTest.do">
        <div class="">
			<input name="smid" type="text">
		</div>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="save-btn" onclick="javascript:fnDeleteLcpSvyComptTest();"><spring:message code="title.create" /></button>
			</div>
		</div>
		</form:form>
    </div>
</div>