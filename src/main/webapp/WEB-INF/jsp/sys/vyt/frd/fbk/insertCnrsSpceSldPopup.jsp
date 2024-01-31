<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="inputSelect">선택하세요</c:set>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/vyt/frd/fbk/insertCnrsSpceSld.do">
    	<input type="hidden" name="mst_id" value="${mst_id}" />
    	<input type="hidden" name="mst_corpname" value="한국치산기술협회" />
    	<input name="mst_create_user" type="hidden" value="${mst_create_user}" />
        <table class="fieldBookTable ov">
        	<tr>
        		<th>구분</th>
				<td>
					<select name="frdRnk">
						<option value="">${inputSelect}</option>
						<c:forEach var="result" items="${frdRnk_result}">
							<option value="${result.codeNm}">${result.codeNm}</option>
						</c:forEach>
					</select>
				</td>
        	</tr>
        	<tr>
				<th rowspan="3">사업목록</th>
				<td>
					<select name="sldId" onchange="fnCheckSldValue(this.value);">
						<option value="">${inputSelect}</option>
						<c:forEach var="result" items="${sldRegList}">
							<option value="${result.id}">${result.svysldNm}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<select id="svySdView" name="svySd" class="wd15" onchange="fncAdministCtprvnNmChange(event);">
						<option value="">선택하세요</option>
						<c:forEach var="result" items="${sdCodeList}">
							<option value="${result.adminNm}">${result.adminNm}</option>
						</c:forEach>
					</select>
					<select id="svySggView" name="svySgg" class="wd15" onchange="fncAdministSignguNmChange(event);">
						<option value="">선택하세요</option>
					</select>
					<select id="svyEmdView" name="svyEmd" class="wd15" onchange="fncAdministEmdNmChange(event);">
						<option value="">선택하세요</option>
					</select>
					<button type="button" class="search-btn" onclick="javascript:fnSelectSldCnt('detail');">검색</button>
				</td>
			</tr>
			<tr>
				<td>
					조사건수 : <span id="sldCnt">0</span>
				</td>
			</tr>
        </table>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpceSld();"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
</div>