<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
   <div class="BoardDetail">
	   <form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/lss/lcp/fbk/insertCnrsSpce.do">
	   	   <input name="sldCnt" type="hidden" value="">
	   	   <input name="sldStdgCd" type="hidden" value="">
	   	   <input name="gvfCnt" type="hidden" value="">
	   	   <input name="gvfStdgCd" type="hidden" value="">
	   	   <input type="hidden" name="mst_corpname" value="한국치산기술협회"/>
	   	   <input type="hidden" name="svytype" value="LCP" />
	       <table class="fieldBookTable ov">
	       <c:set var="inputSelect">선택하세요</c:set>
			      <!-- 사업지구명 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.partname"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_partname" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_partname" cssClass="error" /></div>
				      </td>
			      </tr>
			      <!-- 생성자ID -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.create_user"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_create_user" value="${userNm}" title="${title}${inputTxt}" readonly="true"/>
				   		  <div><form:errors path="mst_create_user" cssClass="error" /></div>
				      </td>
			      </tr>
			      <!-- 부서명 -->
					<tr>
						<c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.deptname"/></c:set>
						<th>${title}</th>
						<td>
							<form:select path="mst_deptname" title="${title}${inputTxt}" cssClass="txt">
								<form:options items="${orgchtList}" itemValue="dept_id" itemLabel="dept_nm" />
							</form:select>
							<div><form:errors path="mst_deptname" cssClass="error" /></div>
						</td>
					</tr>
	          	   <!-- 비밀번호 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.password"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_password" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_password" cssClass="error" /></div>
				      </td>
			      </tr>
			      <!-- 권한자 -->
				<tr class="searchList">
					<c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.author_setup"/></c:set>
					<th>${title}</th>
					<td>
						<select id="searchCondition" name="searchCondition" title="<spring:message code="sysLssLcp.fieldBookVO.searchConditioTitle" />" style="width:30%;">
							<option value="0" <c:if test="${empty userSearchVO.searchCondition || userSearchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="mngUmt.userManageSsearch.searchConditionAll" /></option>
							<option value="1" <c:if test="${userSearchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="sysLssLcp.fieldBookVO.deptname" /></option>
							<option value="2" <c:if test="${userSearchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="sysLssLcp.fieldBookVO.name" /></option>
						</select>
						<input type="text" id="searchKeyword" name="searchKeyword" class="mo_mt5 input_null" value="<c:out value="${userSearchVO.searchKeyword}"/>" style="width:50%;"/>
						<button title="<spring:message code="button.searchDetail"/>" class="search-btn-m" onclick="javascript:fnSearchMberList(); return false;"></button>
					</td>
				</tr>
				<tr>
					<th>검색 목록</th>
					<td>
						<div class="mberListBox">
							<table class="mberList">
								<thead>
									<tr>
										<th><input type="checkbox" id="reprt_all_check" onclick="javascript:fnSelectMberAllCheck();"/></th>
										<th>부서명</th>
										<th>사용자명</th>
									</tr>	
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
						<div class="BtnRightArea">
							<button type="button" class="add-btn" onclick="javascript:fnAddAuthory(); return false;"><spring:message code="title.create" /></button>
						</div>
					</td>
				</tr>
				<tr>
					<c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.authory"/></c:set>
					<th>${title}</th>
					<td>
						<div class="authoryListBox">
							<table class="authoryList">
								<thead>
									<tr>
										<th>부서명</th>
										<th>사용자명</th>
										<th></th>									
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			      <!-- 조사대상지 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.sldNm"/></c:set>
					  <th rowspan="3">${title}</th>
					  <td class="addrArea">
					      <form:select path="sldId" title="${title}${inputSelect}" cssClass="txt">
						      <form:options items="${sldNmList}" itemValue="sldId" itemLabel="sldNm" />
						  </form:select>
						  <div><form:errors path="sldNm" cssClass="error" /></div>
				      </td>
			      </tr>
			      <tr>
			      	  <td class="addrArea addrWd13">
			      	  	  <form:select path="sldSd" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministCtprvnChange('sld'); return false;">
						      <form:option value="" label="${inputSelect}"/>
						      <form:options items="${sdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
						  </form:select>
						  <form:select path="sldSgg" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministSignguChange('sld'); return false;">
						      <form:option value="" label="${inputSelect}"/>
						      <form:options items="${sggCodeList}" itemValue="adminCode" itemLabel="adminNm" />
						  </form:select>
						  <form:select path="sldEmd" title="${title}${inputTxt}" cssClass="txt">
						      <form:option value="" label="${inputSelect}"/>
						      <form:options items="${emdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
						  </form:select>
					      <div class="AddrBtnRightArea">
						       <button type="button" class="search-btn" onclick="javascript:fnAddrSearch('sld'); return false;"><spring:message code="title.inquire" /></button>
					      </div>
			      	  </td>
			      </tr>
			      <tr>
			      	  <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.sldCnt"/></c:set>
					  <td>${title} : <span id="sldCnt"><strong style="color: #ff0000; font-weight: bold;">0</strong></span></td>
			      </tr>
			      <!-- 제보대상지 -->
			      <tr>
				      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.gvfNm"/></c:set>
					  <th rowspan="2">${title}</th>
					  <td class="addrArea addrWd13">
			      	  	  <form:select path="gvfSd" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministCtprvnChange('gvf'); return false;">
						      <form:option value="" label="${inputSelect}"/>
						      <form:options items="${sdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
						  </form:select>
						  <form:select path="gvfSgg" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministSignguChange('gvf'); return false;">
						      <form:option value="" label="${inputSelect}"/>
						      <form:options items="${sggCodeList}" itemValue="adminCode" itemLabel="adminNm" />
						  </form:select>
						  <form:select path="gvfEmd" title="${title}${inputTxt}" cssClass="txt">
						      <form:option value="" label="${inputSelect}"/>
						      <form:options items="${emdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
						  </form:select>
					      <div class="AddrBtnRightArea">
						       <button type="button" class="search-btn" onclick="javascript:fnAddrSearch('gvf'); return false;"><spring:message code="title.inquire" /></button>
					      </div>
			      	  </td>
			      </tr>
			      <tr>
			      	  <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.gvfCnt"/></c:set>
					  <td>${title} : <span id="gvfCnt"><strong style="color: #ff0000; font-weight: bold;">0</strong></span></td>
			      </tr>
	       </table>
	       <div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpce(); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
	   </form:form>
   </div>
</div>
<script>
$(document).ready(function(){
	var sdCode = $("#sldSd").val();
	if(sdCode == null || sdCode == undefined || sdCode.length == 0){
		$("#sldSgg").empty().append("<option value>선택하세요</option>");
		$("#sldEmd").empty().append("<option value>선택하세요</option>");
		$("#gvfSgg").empty().append("<option value>선택하세요</option>");
		$("#gvfEmd").empty().append("<option value>선택하세요</option>");
		return false;
	}
});
</script>