<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="aprComptVO" commandName="aprComptVO" method="post" action="/sys/fck/apr/sct/updateFckAprComptExcel.do">
        <table class="fieldBookTable ov">
	       	<!-- 조사유형 -->
       	  	<tr>
	      		<c:set var="title"><spring:message code="sysFckApr.stripLandVO.type"/></c:set>
			  	<th>${title}</th>
			  	<td>
					<select id="svy_type" name="svyType" title="${title}">
						<option value="사방댐">사방댐 외관점검</option>
						<option value="산지사방">산지사방 외관점검</option>
						<option value="계류보전">계류보전 외관점검</option>
					</select>
		      	</td>
	      	</tr>
		</table>
        <div class="drag-div drag-active">
			<p class="drag-msg noselect">파일을 드래그하세요.</p>
		</div>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnUpdateFckAprCompt(); return false;"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
</div>