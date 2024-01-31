<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
   <div class="BoardDetail">
	   <form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/lss/bsc/fbk/insertCnrsSpce.do">
	       <table class="fieldBookTable ov">
	          	  <!-- 회사명 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssBsc.fieldBookVO.corpname"/></c:set>
					  <th>${title}</th>
					  <td>
						  <form:select path="mst_corpname" title="${title}${inputTxt}" cssClass="txt">
						      <form:options items="${ogrnztCodeList}" itemValue="codeNm" itemLabel="codeNm" />
						  </form:select>
						  <div><form:errors path="mst_corpname" cssClass="error" /></div>
				      </td>
			      </tr>
			      <!-- 사업지구명 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssBsc.fieldBookVO.partname"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_partname" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_partname" cssClass="error" /></div>
				      </td>
			      </tr>
			      <!-- 생성자ID -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssBsc.fieldBookVO.create_user"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_create_user" value="${userNm}" title="${title}${inputTxt}" readonly="true"/>
				   		  <div><form:errors path="mst_create_user" cssClass="error" /></div>
				      </td>
			      </tr>
	          	   <!-- 비밀번호 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssBsc.fieldBookVO.password"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_password" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_password" cssClass="error" /></div>
				      </td>
			      </tr>
	          	   <!-- 관리자비밀번호 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssBsc.fieldBookVO.adminpwd"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_adminpwd" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_adminpwd" cssClass="error" /></div>
				      </td>
			      </tr>
	          	   <!-- 읽기전용비밀번호 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssBsc.fieldBookVO.readpwd"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_readpwd" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_readpwd" cssClass="error" /></div>
				      </td>
			      </tr>
	       </table>
	       <div class="drag-div drag-active">
				<p class="drag-msg noselect">파일을 드래그하세요.</p>
			</div>
	       <div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpce(); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
	   </form:form>
   </div>
</div>
<!-- 
<script>
function fnInsertCnrsSpce() {
	if(confirm("등록하시겠습니까?")) {
		$("#insertForm").ajaxForm({
			type: 'POST',
			url: $("#insertForm")[0].action,
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
	        		console.log(data);
                    alert(data.message);
                    window.location.reload();
                } else {
                    alert(data.message);
                }
        	},
        	error: function(data) {
        		alert(data.message);
        	}
		}).submit();
	}
}
</script> -->