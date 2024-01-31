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

<!-- 땅밀림실태조사 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="lcpSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="lcpSvycomptlastgrd"><spring:message code="sysGis.svyComptVO.svyGrade"/></label>
			<select id="lcpSvycomptlastgrd" name="lcpSvycomptlastgrd" title="<spring:message code="sysGis.svyComptVO.svyGrade"/>" class="SvyComptType w2" >
				<option value="">전체</option>
				<option value="A" <c:if test="${searchVO.lastgrd eq 'A' }">selected="selected"</c:if>>A</option>
				<option value="B" <c:if test="${searchVO.lastgrd eq 'B' }">selected="selected"</c:if>>B</option>
				<option value="C" <c:if test="${searchVO.lastgrd eq 'C' }">selected="selected"</c:if>>C</option>
			</select>
			<label for="lcpSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" class="SvyComptStartDt w3" id="lcpSvyComptStartDt" name="lcpSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptStartDt w3" id="lcpSvyComptStartDt" name="lcpSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" class="SvyComptEndDt w3" id="lcpSvyComptEndDt" name="lcpSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptEndDt w3" id="lcpSvyComptEndDt" name="lcpSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="lcpSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="lcpSvyComptSd" class="SvyComptSd w4" name="lcpSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="lcpSvyComptSgg" class="SvyComptSgg w4" name="lcpSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="lcpSvyComptEmd" class="SvyComptEmd w4" name="lcpSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="lcpSvyComptRi" class="SvyComptRi w4" name="lcpSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
			<input type="text" id="lcpSvyComptJibun" name="lcpSvyComptJibun"/>
		</div>
		<div>
			<label for="lcpSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="lcpSvyComptId" name="lcpSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="lcpSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="lcpSvyComptsvyUser" name="lcpSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="lcpSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="lcpSvyComptMstNm" name="lcpSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div>
			<label for="lcpSvycomptcmprokscore"><spring:message code="sysGis.svyComptVO.cmprok" /></label><!-- 주구성암석 -->
			<select id="lcpSvycomptcmprokscore" name="lcpSvycomptcmprokscore" title="<spring:message code="sysGis.svyComptVO.cmprok"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${codeItem.codeId eq 'FEI048'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>		
			<label for="lcpSvycomptrokwthrscore"><spring:message code="sysGis.svyComptVO.rokwthr" /></label>	<!-- 암석풍화 -->
			<select id="lcpSvycomptrokwthrscore" name="lcpSvycomptrokwthrscore" title="<spring:message code="sysGis.svyComptVO.rokwthr"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${ codeItem.codeId eq 'FEI049'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
 			<label for="lcpSvycomptsoilty"><spring:message code="sysGis.svyComptVO.soilty" /></label><!-- 토양형 -->
			<select id="lcpSvycomptsoilty" name="lcpSvycomptsoilty" title="<spring:message code="sysGis.svyComptVO.soilty"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${codeItem.svyType eq 'lcp' &&  codeItem.codeId eq 'FEI063'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<div>
			<label for="lcpSvycompttalusatscore"><spring:message code="sysGis.svyComptVO.talusat" /></label><!-- 너덜유무 -->
			<select id="lcpSvycompttalusatscore" name="lcpSvycompttalusatscore" title="<spring:message code="sysGis.svyComptVO.talusat"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${codeItem.svyType eq 'lcp' &&  codeItem.codeId eq 'FEI054'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="lcpSvycompttpgrphscore"><spring:message code="sysGis.svyComptVO.tpgrphChartrSe" /></label><!-- 지형구분/조사지역위치 -->
			<select id="lcpSvycompttpgrphscore" name="lcpSvycompttpgrphscore" title="<spring:message code="sysGis.svyComptVO.tpgrphChartrSe"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${codeItem.svyType eq 'lcp' &&  codeItem.codeId eq 'FEI056'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for = "lcpSvycomptplnformscore"><spring:message code="sysGis.svyComptVO.plnformval" /></label><!-- 평면형(수평적) -->
			<select id="lcpSvycomptplnformscore" name="lcpSvycomptplnformscore" title="<spring:message code="sysGis.svyComptVO.plnformval"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${codeItem.codeId eq 'FEI057'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>		   
		</div>
		<div>
			<label for ="lcpSvycomptlngformscore"><spring:message code="sysGis.svyComptVO.lngformval" /></label><!-- 종단면형(수직적) -->
			<select id="lcpSvycomptlngformscore" name="lcpSvycomptlngformscore" title="<spring:message code="sysGis.svyComptVO.lngformval"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${ codeItem.codeId eq 'FEI057'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>	
			<label for="lcpSvycomptslprngscore"><spring:message code="sysGis.svyComptVO.slprngavrg" /></label><!-- 평균경사도 -->
			<select id="lcpSvycomptslprngscore" name="lcpSvycomptslprngscore" title="<spring:message code="sysGis.svyComptVO.slprngavrg"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
					<c:if test="${codeItem.svyType eq 'lcp' &&  codeItem.codeId eq 'FEI059'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for ="lcpSvycomptfrstfrval"><spring:message code="sysGis.svyComptVO.frstfrval" /></label>	<!-- 임상 -->
			<select id="lcpSvycomptfrstfrval" name="lcpSvycomptfrstfrval" title="<spring:message code="sysGis.svyComptVO.frstfrval"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="codeItem" items="${svyCodeLst}" varStatus="status">
				<c:if test="${codeItem.svyType eq 'lcp' &&  codeItem.codeId eq 'FEI047'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>						
		</div>

	</li>
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="lcpSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btnlcpSearch" class="SvyComptBtn" name="lcpSvyComptSearch">검색</button>
        	<button type="button" id="btnlcpReset" class="SvyComptBtnReset" name="lcpSvyComptReset">초기화</button>
		</div>
	</li>
		<!-- 검색결과 S-->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
			<p class="txtsearch lcp">검색결과 총 <span class="txtblue">0</span> 건</p>
			<table id="lcpSchRsltLst" summary="기초조사 상세검색에 대한 결과를 리스트로 출력합니다.">
					<colgroup>
						<col style="width:10%;">
						<col style="width:20%;">
						<col style="width:50%;">
						<col style="width:20%;">
					</colgroup>
					<thead>
	                	<tr>
	                		<th>번호</th>
	                		<th>판정등급</th>
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
	<!-- 검색결과 E -->
	</form>
</ul>
