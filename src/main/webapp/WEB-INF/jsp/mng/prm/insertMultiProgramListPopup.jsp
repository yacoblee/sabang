<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
	<div class="BoardDetail">
		<form:form id="uploadForm" method="post" action="/mng/prm/uploadProgramExcel.do">
			<table class="ov">
				<tr>
			      	<c:set var="title">초기화여부</c:set>
				  	<th>${title}</th>
				  	<td>
				  		<label for="checkCls" class="Hidden">${title}</label>
				  		<input type="checkbox" id="checkCls" name="checkCls" class="check2" title="초기화 체크 여부">
		      		</td>
      			</tr>
          	  	<tr>
			      	<c:set var="title">파일선택</c:set>
				  	<th>${title}</th>
				  	<td>
				      	<input type="file" id="prmFile" name="prmFile" accept=".xls,.xlsx">
			      	</td>
		      	</tr>
		      	
			</table>
	       	<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnUploadProgramExcel(); return false;"><spring:message code="title.save" /></button>
				</div>
			</div>
		</form:form>
	</div>
</div>