<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
	<div class="BoardDetail">
		<form id="insertForm" action="/sys/vyt/frd/sct/insertStripLandViewAdd.do">
			<c:set var="inputSelect">선택</c:set>
			<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="sldlabel" value="${sldlabel}"/>
			<input type="hidden" name="mstId" value="${mstId}"/>
			<input type="hidden" name="frdtype" value="${frdtype}"/>
			<input type="hidden" name="compentauth" value="${compentauth}"/>
			<table>
<!-- 				<tr> -->
<!-- 					<th>관할청</th> -->
<!-- 					<td> -->
<!-- 						<select name="compentauth" style="width: 48%;"> -->
<%-- 							<option value="">${inputSelect}</option> --%>
<%-- 							<c:forEach var="result" items="${compentauth_result}"> --%>
<%-- 								<option value="${result.codeNm}">${result.codeNm}</option> --%>
<%-- 							</c:forEach> --%>
<!-- 						</select> -->
<!-- 						<select style="width: 48%;">세부관할 추가 -->
<%-- 							<option value="">${inputSelect}</option> --%>
<!-- 						</select> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<tr>
					<th>노선종류</th>
					<td>
						<select name="routetype">
							<option value="">${inputSelect}</option>
<%-- 							<c:forEach var="result" items="${route_result}"> --%>
<%-- 								<option value="${result.codeNm}">${result.codeNm}</option> --%>
<%-- 							</c:forEach> --%>
							<option value="수정노선">수정노선</option>
							<option value="검토제안노선(1)">검토제안노선(1)</option>
							<option value="검토제안노선(2)">검토제안노선(2)</option>
							<option value="검토제안노선(3)">검토제안노선(3)</option>
						</select>
					</td>
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