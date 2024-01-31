<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="sysSptRpt.reportFile.insert"/></c:set>
<div class="sub-upload">
	<div class="BoardDetail">
	<form id="insertForm" method="post" action="/sys/spt/sct/insertAprComptReportFile.do" enctype="multipart/form-data">
       <input type="hidden" name="reprt_id" value="<c:out value='${reprt_id}'/>">
       <table class="fieldBookTable ov">
<!--    			<tr> -->
<!-- 	   			<td>기관명</td> -->
<!-- 	   			<td> -->
<!-- 					<label for="corpNm" class="Hidden">기관명</label> -->
<!-- 					<select id="corpNm" name="corpNm" title="기관명" onchange="fncOrgzntChange(event); return false;"> -->
<!-- 						<option value="">선택</option> -->
<!-- 						<option value="ORGNZT_0000000000000">한국치산기술협회</option> -->
<!-- 						<option value="ORGNZT_0000000000001">산림조합중앙회</option> -->
<!-- 					</select> -->
<!-- 	   			</td> -->
<!-- 	   		</tr> -->
	   		<tr>
	   			<td>부서명</td>
	   			<td>
					<label for="deptNm" class="Hidden">부서명</label>
					<select id="deptNm" name="deptNm" title="부서명">
					<option value="">선택</option>
						<c:forEach var="deptCodeInfo" items="${deptNmList}" varStatus="status">
							<option value="<c:out value="${deptCodeInfo.deptNm}"/>"><c:out value="${deptCodeInfo.deptNm}"/></option>
						</c:forEach>
					</select>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>발주처</td>
	   			<td>
	   				<label for="svySdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sd"/></label>
					<select id="svySdView" name="svySdView" title="<spring:message code="sysLssBsc.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;" style="width: 155px;">
					<option value="">선택</option>
					<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
					<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"><c:out value="${svySdCodeInfo.adminNm}"/></option>
					</c:forEach>
					</select>
					<label for="svySggView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sgg"/></label>
					<select id="svySggView" name="svySggView" title="<spring:message code="sysLssBsc.stripLandVO.sgg"/>" style="width: 155px;">
						<option value="">선택</option>
						<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
						<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"><c:out value="${svySggCodeInfo.adminNm}"/></option>
						</c:forEach>
					</select>
	   			</td>
			</tr>
	   		<tr>
	   			<td>사업명</td>
	   			<td>
	   				<input type="text" name="cnstrName" id="cnstrName"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>사업년도</td>
	   			<td>
	   				<select id="cnstrYear" name="cnstrYear">
	   					<option value="">선택</option>
	   				</select>
	   			</td>
	   		</tr>
	  			<tr>
	   			<td>첨부파일</td>
	   			<td>
	   				<input style="float: left;" type="file" id="files" name="files" accept=".hwp" multiple="multiple">
	   			</td>
	   		</tr>
       </table>
   </form>
	   <div class="BtnGroup">
		   <div class="BtnRightArea">
		      <button type="button" class="upload-btn" onclick="javascript:fnUploadRptFile(); return false;"><spring:message code="title.create"/></button>
		   </div>
	   </div>
	</div>
</div>
<script>
	var yearSelect = document.getElementById("cnstrYear");
	var currentYear = new Date().getFullYear();
	
	var currentYearOption = document.createElement("option");
	currentYearOption.value = currentYear;
	currentYearOption.text = currentYear;
	currentYearOption.selected = true;
	yearSelect.appendChild(currentYearOption);
	
	for (var i = currentYear - 1; i >= currentYear - 5; i--) {
	  var option = document.createElement("option");
	  option.value = i;
	  option.text = i;
	  yearSelect.appendChild(option);
	}
</script>