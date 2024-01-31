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


<!-- 기초조사 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="bscSvyComptSchForm" action="">
	<!-- 조회 공통 조건 S-->
	<li>
		<div>
			<label for="bscSvyComptSvyType"><spring:message code="sysGis.stripLandVO.type"/></label>
			<select id="bscSvyComptSvyType" name="bscSvyComptSvyType" title="<spring:message code="sysGis.stripLandVO.type"/>" class="SvyComptType w2">
				<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
				<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
				</c:forEach>
			</select>
			<label for="bscSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" class="SvyComptStartDt w3" id="bscSvyComptStartDt" name="bscSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptStartDt w3" id="bscSvyComptStartDt" name="bscSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" class="SvyComptEndDt w3" id="bscSvyComptEndDt" name="bscSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%>			
			<input type="text" class="SvyComptEndDt w3" id="bscSvyComptEndDt" name="bscSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자">			
		</div>
		<div>
			<label for="bscSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="bscSvyComptSd" class="SvyComptSd w4" name="bscSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="bscSvyComptSgg" class="SvyComptSgg w4" name="bscSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="bscSvyComptEmd" class="SvyComptEmd w4" name="bscSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="bscSvyComptRi" class="SvyComptRi w4" name="bscSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<input type="text" id="bscSvyComptJibun" name="bscSvyComptJibun" class="mo_mt5 input_null w3" />		
		</div>
		<div>
			<label for="bscSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="bscSvyComptId" name="bscSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="bscSvyComptUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="bscSvyComptUser" name="bscSvyComptUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="bscSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="bscSvyComptMstNm" name="bscSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />			
		</div>
		<!-- 산사태 S-->
		<div class="SvyComptInput Hidden bsc1">
			<label for="bscSvyComptSafty"><spring:message code="sysGis.svyComptVO.safty"/></label>
			<select id="bscSvyComptSafty" name="bscSvyComptSafty" title="<spring:message code="sysGis.svyComptVO.safty"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI005'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
			<label for="bscSvyComptsLen"><spring:message code="sysGis.svyComptVO.sLen"/></label>
			<select id="bscSvyComptsLen" name="bscSvyComptsLen" title="<spring:message code="sysGis.svyComptVO.sLen"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI011'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
			<label for="bscSvyComptslope"><spring:message code="sysGis.svyComptVO.slope"/></label>
			<select id="bscSvyComptslope" name="bscSvyComptslope" title="<spring:message code="sysGis.svyComptVO.slope"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI012'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
		</div>
		<div class="SvyComptInput Hidden bsc1">
			<label for="bscSvyComptsForm"><spring:message code="sysGis.svyComptVO.sForm"/></label>
			<select id="bscSvyComptsForm" name="bscSvyComptsForm" title="<spring:message code="sysGis.svyComptVO.sForm"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI013'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
			<label for="bscSvyComptfrstFr"><spring:message code="sysGis.svyComptVO.frstFr"/></label>
			<select id="bscSvyComptfrstFr" name="bscSvyComptfrstFr" title="<spring:message code="sysGis.svyComptVO.frstFr"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI014'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
			<label for="bscSvyComptprntRck"><spring:message code="sysGis.svyComptVO.prntRck"/></label>
			<select id="bscSvyComptprntRck" name="bscSvyComptprntRck" title="<spring:message code="sysGis.svyComptVO.prntRck"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI015'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>
		</div>
		<!-- 산사태 E -->
		
		<!-- 토석류 S -->
		<div class="SvyComptInput Hidden bsc2">
			<label for="bscSvyComptsafty2"><spring:message code="sysGis.svyComptVO.safty"/></label>
			<select id="bscSvyComptsafty2" name="bscSvyComptsafty2" title="<spring:message code="sysGis.svyComptVO.safty"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI005'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="bscSvyComptDevOcCause"><spring:message code="sysGis.svyComptVO.devOcCause"/></label><!-- 황폐발생원 -->
			<select id="bscSvyComptDevOcCausey" name="bscSvyComptDevOcCause" title="<spring:message code="sysGis.svyComptVO.devOcCause"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI006'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>	
			<label for="bscSvyComptTrntAvgSlp"><spring:message code="sysGis.svyComptVO.trntAvgSlp"/></label><!-- 계류평균경사 -->
			<select id="bscSvyComptTrntAvgSlp" name="bscSvyComptTrntAvgSlp" title="<spring:message code="sysGis.svyComptVO.trntAvgSlp"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI007'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>			
		</div>
		<div class="SvyComptInput Hidden bsc2">
			<label for="bscSvyComptWclctArea"><spring:message code="sysGis.svyComptVO.wclctArea"/></label><!-- 집수면적 -->
			<select id="bscSvyComptWclctArea" name="bscSvyComptWclctArea" title="<spring:message code="sysGis.svyComptVO.wclctArea"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI008'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>		
			<label for="bscSvyComptTlTrntLt"><spring:message code="sysGis.svyComptVO.tlTrntLt"/></label><!-- 총계류길이 -->
			<select id="bscSvyComptTlTrntLt" name="bscSvyComptTlTrntLt" title="<spring:message code="sysGis.svyComptVO.tlTrntLt"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'bsc' && codeItem.codeId eq 'FEI009'}">
				<option value="<c:out value="${codeItem.code}"/>"><c:out value="${codeItem.codeNm}"/></option>
				</c:if>
				</c:forEach>
			</select>	
		</div>
		<!-- 토석류 E -->
	</li>
	<!-- 조회 조건 E-->
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="bscSvyComptShpDown">공간정보 다운로드</button>
         	<button type="button" id="btnBscSearch" class="SvyComptBtn" name="bscSvyComptSearch">검색</button>
        	<button type="button" id="btnBscReset" class="SvyComptBtnReset" name="bscSvyComptReset">초기화</button>
		</div>
	</li>
	
	
	<!-- 검색결과 탭 S -->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
				<p class="txtsearch bsc">검색결과 총 <span class="txtblue">0</span> 건</p>
				<table id="bscSchRsltLst" summary="기초조사 상세검색에 대한 결과를 리스트로 출력합니다.">
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
	</li>
	<!-- 검색결과 탭 E -->
	</form>
</ul>