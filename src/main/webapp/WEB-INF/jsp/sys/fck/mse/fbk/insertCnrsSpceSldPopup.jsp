<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/fck/mse/fbk/insertCnrsSpceSld.do">
    	<input type="hidden" value="${mst_id}" name="id" />
    	<input type="hidden" value="${svy_step}" name="svy_step" />
    	<table class="fieldBookTable ov">
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
				<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpceSld();"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
</div>