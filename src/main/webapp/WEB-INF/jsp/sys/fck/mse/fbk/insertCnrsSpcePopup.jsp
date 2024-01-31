<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
	<div class="BoardDetail">
		<form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/fck/mse/fbk/insertCnrsSpce.do">
			<input type="hidden" name="mst_corpname" value="한국치산기술협회"/>
			<input type="hidden" name="svytype" value="MSE" />
			
			<table class="fieldBookTable ov">
				<colgroup>
			  		<col width="20%;">
					<col width="80%;">
			  	</colgroup>
		      	<!-- 사업지구명 -->
          	  	<tr>
			      	<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.partname"/></c:set>
				  	<th>${title}</th>
				  	<td>
				      	<form:input path="mst_partname" title="${title} ${inputTxt}"/>
			   		  	<div><form:errors path="mst_partname" cssClass="error" /></div>
			      	</td>
		      	</tr>
		      	<!-- 부서명 -->
				<tr>
					<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.deptname"/></c:set>
					<th>${title}</th>
					<td>
						<form:select path="mst_deptname" title="${title}${inputTxt}" cssClass="txt">
							<form:options items="${orgchtList}" itemValue="dept_id" itemLabel="dept_nm" />
						</form:select>
						<div><form:errors path="mst_deptname" cssClass="error" /></div>
					</td>
				</tr>
		      	<!-- 생성자명 -->
          	  	<tr>
			      	<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.create_user"/></c:set>
				  	<th>${title}</th>
				  	<td>
				      	<form:input path="mst_create_user" value="${userNm}" title="${title}${inputTxt}" readonly="true"/>
			   		  	<div><form:errors path="mst_create_user" cssClass="error" /></div>
			      	</td>
		      	</tr>
          	   	<!-- 비밀번호 -->
          	  	<tr>
			      	<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.password"/></c:set>
				  	<th>${title}</th>
				  	<td>
				      	<form:input path="mst_password" title="${title} ${inputTxt}"/>
			   		  	<div><form:errors path="mst_password" cssClass="error" /></div>
			      	</td>
		      	</tr>
				<!-- 권한자 -->
				<tr class="searchList">
					<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.author_setup"/></c:set>
					<th>${title}</th>
					<td>
						<select id="searchCondition" name="searchCondition" title="<spring:message code="sysFckMse.fieldBookVO.searchConditioTitle" />" style="width:30%;">
							<option value="0" <c:if test="${empty userSearchVO.searchCondition || userSearchVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="mngUmt.userManageSsearch.searchConditionAll" /></option>
							<option value="1" <c:if test="${userSearchVO.searchCondition == '1'}">selected="selected"</c:if>><spring:message code="sysFckMse.fieldBookVO.deptname" /></option>
							<option value="2" <c:if test="${userSearchVO.searchCondition == '2'}">selected="selected"</c:if>><spring:message code="sysFckMse.fieldBookVO.name" /></option>
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
					<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.authory"/></c:set>
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
		       	<!-- 조사유형 -->
          	  	<tr>
			      	<c:set var="title"><spring:message code="sysFckMse.stripLandVO.svyStep"/></c:set>
				  	<th>${title}</th>
				  	<td>
						<select id="svy_step" name="svy_step" title="${title} ${inputTxt}">
							<option value="1차">1차</option>
							<option value="2차">2차</option>
							<option value="긴급">긴급</option>
						</select>
			      	</td>
		      	</tr>
		      	<!-- 조사대상지 -->
				<tr>
				<%-- 				      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.sldNm"/></c:set> --%>
					<th rowspan="3">대상지</th>
					<td class="addrArea">
				    </td>
				</tr>
				<tr>
					<td class="addrArea addrWd13">
						<label for="svySd" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sd"/></label>
						<select id="svySd" name="svySd" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
							<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
							<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
							<option value="<c:out value="${svySdCodeInfo.adminNm}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
							</c:forEach>
						</select>
						<label for="svySgg" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sgg"/></label>
						<select id="svySgg" name="svySgg" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
							<option value="" selected="selected"><spring:message code="title.all" /></option>
							<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
							<option value="<c:out value="${svySggCodeInfo.adminNm}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
							</c:forEach>
						</select>
						<label for="svyEmd" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.emd"/></label>
						<select id="svyEmd" name="svyEmd" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
							<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
							<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
							<option value="<c:out value="${svyEmdCodeInfo.adminNm}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
							</c:forEach>
						</select>
						<label for="svyRi" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.ri"/></label>
						<select id="svyRi" name="svyRi" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.ri"/>">
							<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
							<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
							<option value="<c:out value="${svyRiCodeInfo.adminNm}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
							</c:forEach>
						</select>
						<c:set var="title"><spring:message code="sysFckMse.fieldBookVO.svyJibun"/></c:set>
						<form:input path="svyJibun" value="${svyJibun}" title="${title}${inputTxt}" style="width: 20%;"/>
					
						<div class="AddrBtnRightArea">
							<button type="button" class="search-btn" onclick="javascript:fnAddrSearch(); return false;"><spring:message code="title.inquire" /></button>
						</div>
					</td>
				</tr>
				<tr>
<%-- 					<c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.gvfCnt"/></c:set> --%>
					<td>건수 : <span id="mseSldCnt"><strong style="color: #ff0000; font-weight: bold;">0</strong></span></td>
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