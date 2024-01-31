<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/fck/apr/fbk/insertCnrsSpceSld.do">
    	<input type="hidden" value="${mst_id}" name="mst_id" />
    	<table class="fieldBookTable ov">
    		<!-- 조사유형 -->
         	<tr>
		      	<c:set var="title"><spring:message code="sysFckApr.stripLandVO.type"/></c:set>
			  	<th>${title}</th>
			  	<td>
					<select id="svy_type" name="svy_type" title="${title} ${inputTxt}">
						<option value="사방댐" selected="selected">사방댐 외관점검</option>
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
				<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpceSld('<c:out value='${mst_id}'/>');"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
</div>