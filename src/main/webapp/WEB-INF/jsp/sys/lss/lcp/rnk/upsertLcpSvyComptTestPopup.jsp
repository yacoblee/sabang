<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="upsertForm" method="post" action="/sys/lss/lcp/rnk/upsertLcpSvyComptTest.do">
        <div class="">
			<textarea name="jsonValue" rows="10" cols="10" style="width: 376px; height: 193px;"></textarea>
		</div>
		<div style="padding-top:3px;">
			<label for="epsg">입력좌표계</label>
			<select name="epsg" style="width:80%">
				<option value="5186">5186</option>
				<option value="5179">5179</option>
			</select>
		</div>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="save-btn" onclick="javascript:fnUpsertLcpSvyComptTest();"><spring:message code="title.create" /></button>
			</div>
		</div>
		</form:form>
    </div>
</div>