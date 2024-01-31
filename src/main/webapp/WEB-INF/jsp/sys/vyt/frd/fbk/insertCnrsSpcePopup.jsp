<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
<c:set var="inputTxt">입력</c:set>
<c:set var="inputSelect">선택하세요</c:set>
   <div class="BoardDetail">
	   <form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/vyt/frd/fbk/insertCnrsSpce.do">
		   <input type="hidden" name="svytype" value="FRD" />
		   <input type="hidden" name="mst_corpname" value="한국치산기술협회" />
	       <table class="fieldBookTable ov">
	          	  <!-- 회사명 -->
<!-- 	          	  <tr> -->
<%-- 				      <c:set var="title"><spring:message code="sysVytFrd.fieldBookVO.corpname"/></c:set> --%>
<%-- 					  <th>${title}</th> --%>
<!-- 					  <td> -->
<%-- 						  <form:select path="mst_corpname" title="${title}${inputTxt}" cssClass="txt"> --%>
<%-- 						      <form:options items="${ogrnztCodeList}" itemValue="codeNm" itemLabel="codeNm" /> --%>
<%-- 						  </form:select> --%>
<%-- 						  <div><form:errors path="mst_corpname" cssClass="error" /></div> --%>
<!-- 				      </td> -->
<!-- 			      </tr> -->
			      <!-- 사업지구명 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysVytFrd.fieldBookVO.partname"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_partname" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_partname" cssClass="error" /></div>
				      </td>
			      </tr>
			      <!-- 생성자ID -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysVytFrd.fieldBookVO.create_user"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_create_user" value="${userNm}" title="${title}${inputTxt}" readonly="true"/>
				   		  <div><form:errors path="mst_create_user" cssClass="error" /></div>
				      </td>
			      </tr>
	          	   <!-- 비밀번호 -->
	          	  <tr>
				      <c:set var="title"><spring:message code="sysVytFrd.fieldBookVO.password"/></c:set>
					  <th>${title}</th>
					  <td>
					      <form:input path="mst_password" title="${title} ${inputTxt}"/>
				   		  <div><form:errors path="mst_password" cssClass="error" /></div>
				      </td>
			      </tr>
	          	<!-- 권한자 -->
				<tr class="searchList">
					<c:set var="title"><spring:message code="sysVytFrd.fieldBookVO.author_setup"/></c:set>
					<th>${title}</th>
					<td>
						<select id="searchCondition" name="searchCondition" title="<spring:message code="sysVytFrd.fieldBookVO.searchConditioTitle" />" style="width:30%;">
							<option value="0" <c:if test="${empty userSearchVO.searchCondition || userSearchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="mngUmt.userManageSsearch.searchConditionAll" /></option>
							<option value="1" <c:if test="${userSearchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="sysVytFrd.fieldBookVO.deptname" /></option>
							<option value="2" <c:if test="${userSearchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="sysVytFrd.fieldBookVO.name" /></option>
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
					<c:set var="title"><spring:message code="sysVytFrd.fieldBookVO.authory"/></c:set>
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
						<button type="button" class="search-btn" onclick="javascript:fnSelectSldCnt('list');">검색</button>
					</td>
				</tr>
				<tr>
					<td>
						조사건수 : <span id="sldCnt">0</span>
					</td>
				</tr>
	       </table>
<!-- 	       <div class="drag-div drag-active"> -->
<!-- 				<p class="drag-msg noselect">파일을 드래그하세요.</p> -->
<!-- 			</div> -->
	       <div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpce(); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
	   </form:form>
   </div>
</div>
