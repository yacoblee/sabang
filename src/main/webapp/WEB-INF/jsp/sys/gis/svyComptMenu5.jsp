<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<!-- 오늘일자 -->
<c:set var="nowDt"><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM"/></c:set>
<!-- 외관점검 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="aprSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="aprSvyComptSvyType"><spring:message code="sysGis.stripLandVO.type"/></label>
			<select id="aprSvyComptSvyType" name="aprSvyComptSvyType" title="<spring:message code="sysGis.stripLandVO.type"/>" class="SvyComptType w2">
				<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyTypeCodeInfo" items="${typeCodeLists}" varStatus="status">
				<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
				</c:forEach>
			</select>
			
			<label for="aprSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" id= "aprSvyComptStartDt" class="SvyComptStartDt w3" name="aprSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" id= "aprSvyComptStartDt" class="SvyComptStartDt w3" name="aprSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" id= "aprSvyComptEndDt" class="SvyComptEndDt w3" name="aprSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" id= "aprSvyComptEndDt" class="SvyComptEndDt w3" name="aprSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="aprSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="aprSvyComptSd" class="SvyComptSd w4" name="aprSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="aprSvyComptSgg" class="SvyComptSgg w4" name="aprSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="aprSvyComptEmd" class="SvyComptEmd w4" name="aprSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="aprSvyComptRi" class="SvyComptRi w4" name="aprSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
				<input type="text" id="aprSvyComptJibun" name="aprSvyComptJibun"/>
		</div>
		<div>
			<label for="aprSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="aprSvyComptId" name="aprSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="aprSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="aprSvyComptsvyUser" name="aprSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="aprSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="aprSvyComptMstNm" name="aprSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>		
		<!-- 사방댐 -->	
		<div class="SvyComptInput Hidden apr1">
			<label for="aprSvyComptorginlDamVal"><spring:message code="sysGis.svyComptVO.orginlDamVal"/></label><!-- 본댐측정값 -->
			<select id="aprSvyComptorginlDamVal" name="aprSvyComptorginlDamVal" title="<spring:message code="sysGis.svyComptVO.orginlDamVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI017'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="aprSvyComptorginlDamJdgVal"><spring:message code="sysGis.svyComptVO.orginlDamJdgVal"/></label><!-- 본댐판정값 -->
			<select id="aprSvyComptorginlDamJdgVal" name="aprSvyComptorginlDamJdgVal" title="<spring:message code="sysGis.svyComptVO.orginlDamJdgVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
			<label for="aprSvyComptsideWallVal"><spring:message code="sysGis.svyComptVO.sideWallVal"/></label><!-- 측벽측정값 -->
			<select id="aprSvyComptsideWallVal" name="aprSvyComptsideWallVal" title="<spring:message code="sysGis.svyComptVO.sideWallVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI023'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>
		<div class="SvyComptInput Hidden apr1">
			<label for="aprSvyComptsideWallJdgVal"><spring:message code="sysGis.svyComptVO.sideWallJdgVal"/></label><!-- 측벽판정값 -->
			<select id="aprSvyComptsideWallJdgVal" name="aprSvyComptsideWallJdgVal" title="<spring:message code="sysGis.svyComptVO.sideWallJdgVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="aprSvyComptdwnsptVal"><spring:message code="sysGis.svyComptVO.dwnsptVal"/></label><!-- 물받이 측정값-->
			<select id="aprSvyComptdwnsptVal" name="aprSvyComptdwnsptVal" title="<spring:message code="sysGis.svyComptVO.dwnsptVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI044'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
			<label for="aprSvyComptdwnsptJdgVal"><spring:message code="sysGis.svyComptVO.dwnsptJdgVal"/></label><!-- 물받이판정값 -->
			<select id="aprSvyComptdwnsptJdgVal" name="aprSvyComptdwnsptJdgVal" title="<spring:message code="sysGis.svyComptVO.dwnsptJdgVal"/>" class="mo_mt5 input_null w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>		
		
		
		<!-- 산지사방 -->
		<div class="SvyComptInput Hidden apr2">
			<label for="aprSvyComptreinfcVal"><spring:message code="sysGis.svyComptVO.reinfcVal"/></label><!-- 보강시설측정값 -->
			<select id="aprSvyComptreinfcVal" name="aprSvyComptreinfcVal" title="<spring:message code="sysGis.svyComptVO.reinfcVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI024'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="aprSvyComptreinfcJdgVal"><spring:message code="sysGis.svyComptVO.reinfcJdgVal"/></label><!-- 보강시설판정값 -->
			<select id="aprSvyComptreinfcJdgVal" name="aprSvyComptreinfcJdgVal" title="<spring:message code="sysGis.svyComptVO.reinfcJdgVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
			<label for="aprSvyComptprtcVal"><spring:message code="sysGis.svyComptVO.prtcVal"/></label><!-- 보호시설측정값 -->
			<select id="aprSvyComptprtcVal" name="aprSvyComptprtcVal" title="<spring:message code="sysGis.svyComptVO.prtcVal"/>" class="mo_mt5 input_null w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI025'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>
		<div class="SvyComptInput Hidden apr2">
			<label for="aprSvyComptprtcJdgVal"><spring:message code="sysGis.svyComptVO.prtcJdgVal"/></label><!-- 보호시설판정값 -->
			<select id="aprSvyComptprtcJdgVal" name="aprSvyComptprtcJdgVal" title="<spring:message code="sysGis.svyComptVO.prtcJdgVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="aprSvyComptdrngVal"><spring:message code="sysGis.svyComptVO.drngVal"/></label><!-- 배부시설측정값-->
			<select id="aprSvyComptdrngVal" name="aprSvyComptdrngVal" title="<spring:message code="sysGis.svyComptVO.drngVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI026'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
			<label for="aprSvyComptdrngJdgVal"><spring:message code="sysGis.svyComptVO.drngJdgVal"/></label><!-- 배수시설판정값 -->
			<select id="aprSvyComptdrngJdgVal" name="aprSvyComptdrngJdgVal" title="<spring:message code="sysGis.svyComptVO.drngJdgVal"/>" class="mo_mt5 input_null w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>	
		
		<!-- 계류보전 -->
		<div class="SvyComptInput Hidden apr3">
			<label for="aprSvyComptchkdamVal"><spring:message code="sysGis.svyComptVO.chkdamVal"/></label><!-- 골막이측정값 -->
			<select id="aprSvyComptchkdamVal" name="aprSvyComptchkdamVal" title="<spring:message code="sysGis.svyComptVO.chkdamVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI029'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="aprSvyComptchkdamJdgVal"><spring:message code="sysGis.svyComptVO.chkdamJdgVal"/></label><!-- 골막이판정값 -->
			<select id="aprSvyComptchkdamJdgVal" name="aprSvyComptchkdamJdgVal" title="<spring:message code="sysGis.svyComptVO.chkdamJdgVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
			<label for="aprSvyComptrvtmntVal"><spring:message code="sysGis.svyComptVO.rvtmntVal"/></label><!-- 기슭막이측정값 -->
			<select id="aprSvyComptrvtmntVal" name="aprSvyComptrvtmntVal" title="<spring:message code="sysGis.svyComptVO.rvtmntVal"/>" class="mo_mt5 input_null w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI029'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>
		<div class="SvyComptInput Hidden apr3">
			<label for="aprSvyComptrvtmntJdgVal"><spring:message code="sysGis.svyComptVO.rvtmntJdgVal"/></label><!-- 기슭막이판정값 -->
			<select id="aprSvyComptrvtmntJdgVal" name="aprSvyComptrvtmntJdgVal" title="<spring:message code="sysGis.svyComptVO.rvtmntJdgVal"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="aprSvyComptgrdstabl"><spring:message code="sysGis.svyComptVO.grdstabl"/></label><!-- 바닥막이측정값-->
			<select id="aprSvyComptgrdstabl" name="aprSvyComptgrdstabl" title="<spring:message code="sysGis.svyComptVO.grdstabl"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI029'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
			<label for="aprSvyComptgrdstablJdgVal"><spring:message code="sysGis.svyComptVO.grdstablJdgVal"/></label><!-- 바닥막이판정값 -->
			<select id="aprSvyComptgrdstablJdgVal" name="aprSvyComptgrdstablJdgVal" title="<spring:message code="sysGis.svyComptVO.grdstablJdgVal"/>" class="mo_mt5 input_null w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'apr' && codeItem.codeId eq 'FEI018'}">
				<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>	
	</li>
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="aprSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btnaprSearch" class="SvyComptBtn" name="aprSvyComptSearch">검색</button>
        	<button type="button" id="btnaprReset" class="SvyComptBtnReset" name="aprSvyComptReset">초기화</button>
		</div>
	</li>
	
	<!-- 조회 결과 S-->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
			<p class="txtsearch apr">검색결과 총 <span class="txtblue">0</span> 건</p>
			<table id="aprSchRsltLst" summary="기초조사 상세검색에 대한 결과를 리스트로 출력합니다.">
					<caption>검색결과리스트</caption>
					<colgroup>
						<col style="width:10%;">
						<col style="width:20%;">
						<col style="width:50%;">
						<col style="width:20%;">
					</colgroup>
					<thead>
	                	<tr>
	                		<th>번호</th>
	                		<th>조사유형</th>
	                		<th>조사지역</th>
	                		<th>더보기</th>
	                	</tr>
	                </thead>					
					<tbody>
					
					</tbody>
				</table>	
				<div class="map_Page">
				
				</div>			
			</div>
		</div>					
		<!-- 조회 결과 E-->	
	</li>	
	</form>
</ul>