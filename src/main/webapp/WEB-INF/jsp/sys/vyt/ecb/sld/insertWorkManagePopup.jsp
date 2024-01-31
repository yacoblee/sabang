<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
	<div class="BoardDetail">
		<form:form id="insertForm" modelAttribute="vyEcbWorkVO" commandName="vyEcbWorkVO" method="post" action="/sys/vyt/ecb/sld/insertVytEcbWork.do">
			<table class="fieldBookTable ov">
          	  	<!-- 사업종류 -->
          	  	<tr>
			      	<c:set var="title"><spring:message code="sysVytEcb.workVO.workType"/></c:set>
				  	<th>${title}</th>
				  	<td>
						<form:select path="workType" title="${title}${inputTxt}" cssClass="txt">
					      	<form:options items="${workTypeCodeList}" itemValue="code" itemLabel="codeNm" />
					  	</form:select>
						<div><form:errors path="workType" cssClass="error" /></div>
			      	</td>
		      	</tr>
          	  	<!-- 사업명 -->
				<tr>
			      	<c:set var="title"><spring:message code="sysVytEcb.workVO.workName"/></c:set>
				  	<th>${title}</th>
				  	<td>
				  		<form:input path="workNm" value="" title="${title}${inputTxt}"/>
				  		<div><form:errors path="workNm" cssClass="error" /></div>
		      		</td>
      			</tr>
		      	<!-- 생성자명 -->
          	  	<tr>
			      	<c:set var="title"><spring:message code="sysVytEcb.workVO.creatUser"/></c:set>
				  	<th>${title}</th>
				  	<td>
				      	<form:input path="creatUser" value="${userNm}" title="${title}${inputTxt}" readonly="true"/>
			   		  	<div><form:errors path="creatUser" cssClass="error" /></div>
			      	</td>
		      	</tr>
          	   	
		       	
			</table>
			<div class="drag-div drag-active">
				<p class="drag-msg noselect">파일을 드래그하세요.</p>
			</div>
	       	<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnInsertWork(); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
		</form:form>
	</div>
</div>