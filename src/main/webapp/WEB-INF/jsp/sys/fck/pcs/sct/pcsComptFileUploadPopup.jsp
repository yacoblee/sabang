<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
	<c:set var="inputTxt">입력</c:set>
	<c:set var="inputSelect">선택</c:set>
	<div class="BoardDetail">
		<form id="insertForm" action="/sys/fck/pcs/sct/insertFileUpload.do" enctype="multipart/form-data">
	       <div class="drag-div drag-active pcs-drag-area">
				<p class="drag-msg noselect">파일을 드래그하세요.</p>
			</div>
	       <div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnFileUpload('<c:out value="${gid}"/>'); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
		</form>
   </div>
</div>
<script>

</script>