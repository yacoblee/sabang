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
		<form id="insertForm" action="/sys/vyt/frd/sld/insertStripLand.do" enctype="multipart/form-data">
			<input type="hidden" name="routetype" value="기존노선"/>
			<table>
				<tr>
					<th><spring:message code="sysVytFrd.stripLandVO.workName"/></th>
					<td><input type="text" id="sld_nm" name="sld_nm" /></td>
				</tr>
				<tr>
					<th>관할청</th>
					<td>
						<select id="compentAssort" name="compentAssort" class="w30p" onchange="fncCompentAuthChange(); return false;">
							<option value="">${inputSelect}</option>
							<c:forEach var="result" items="${compentAssort_result}">
								<option value="${result.codeNm}">${result.codeNm}</option>
							</c:forEach>
						</select>
						<select id="compentauth" name="compentauth" class="w30p" onchange="fncSubCompentAuthChange(); return false;">
							<option value="">${inputSelect}</option>
						</select>
						<select id="subcompentauth" name="subcompentauth" class="w30p">
							<option value="">${inputSelect}</option>
						</select>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<th>노선종류</th> -->
<!-- 					<td> -->
<!-- 						<select id="routetype" name="routetype"> -->
<%-- 							<option value="">${inputSelect}</option> --%>
<%-- 							<c:forEach var="result" items="${route_result}"> --%>
<%-- 								<option value="${result.codeNm}">${result.codeNm}</option> --%>
<%-- 							</c:forEach> --%>
<!-- 						</select> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>
					<th><spring:message code="sysVytFrd.workVO.creatUser"/></th>
					<td><input type="text" id="sld_create_user" name="sld_create_user" value="${userNm}" readonly="readonly"/></td>
				</tr>
			</table>
	       <div class="drag-div drag-active">
				<p class="drag-msg noselect">파일을 드래그하세요.</p>
			</div>
	       <div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnSvySldInsert(); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
		</form>
   </div>
</div>