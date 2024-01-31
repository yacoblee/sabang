<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" method="post" action="/sys/lss/wka/sts/insertBscSvyPopultn.do">
    	<input type="hidden" id="mberId" name="mberId" value="${userId}">
    	<table class="fieldBookTable">
         	<tr>
		      	<c:set var="title">모집단 건수</c:set>
			  	<th>${title}</th>
			  	<td>
					<input type="text" id="popultnCnt" name="popultnCnt" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
		      	</td>
	      	</tr>
    	</table>        
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnInsertBscSvyPopultn(); return false;">변경</button>				
			</div>
		</div>
		</form:form>
    </div>
</div>