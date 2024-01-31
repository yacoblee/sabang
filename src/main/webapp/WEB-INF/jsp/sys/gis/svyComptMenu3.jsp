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
<!-- 취약지역실태조사 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="wkaSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="wkaSvyComptSvyType"><spring:message code="sysGis.stripLandVO.type"/></label>
			<select id="wkaSvyComptSvyType" name="wkaSvyComptSvyType" title="<spring:message code="sysGis.stripLandVO.type"/>" class="SvyComptType w2">
				<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
				<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
				</c:forEach>
			</select>
			<label for="wkaSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" class="SvyComptStartDt w3" id = "wkaSvyComptStartDt" name="wkaSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptStartDt w3" id = "wkaSvyComptStartDt" name="wkaSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" class="SvyComptEndDt w3" id="wkaSvyComptEndDt" name="wkaSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptEndDt w3" id="wkaSvyComptEndDt" name="wkaSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="wkaSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="wkaSvyComptSd" class="SvyComptSd w4" name="wkaSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="wkaSvyComptSgg" class="SvyComptSgg w4" name="wkaSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="wkaSvyComptEmd" class="SvyComptEmd w4" name="wkaSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="wkaSvyComptRi" class="SvyComptRi w4" name="wkaSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
			<input type="text" id="wkaSvyComptJibun" name="wkaSvyComptJibun"/>															  
		</div>
		<div>
			<label for="wkaSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="wkaSvyComptId" name="wkaSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="wkaSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="wkaSvyComptsvyUser" name="wkaSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="wkaSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="wkaSvyComptMstNm" name="wkaSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<!-- 산사태 S-->
		<div class="SvyComptInput Hidden wka1">
			<label for="wkaSvyComptdglog" ><spring:message code="sysGis.svyComptVO.dglogVal"/></label><!-- 피해이력 -->
			<select name="wkaSvyComptdglog" id="wkaSvyComptdglog" title="<spring:message code="sysGis.svyComptVO.dglogVal"/>" class="mo_mt5 w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI098' }">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="wkaSvyComptdireffcsafe" ><spring:message code="sysGis.svyComptVO.direffcsafeVal"/></label><!-- 직접영향권 내 보호시설 -->
			<select name="wkaSvyComptdireffcsafe" id="wkaSvyComptdireffcsafe" title="<spring:message code="sysGis.svyComptVO.direffcsafeVal"/>"class="mo_mt5 w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI099' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="wkaSvyComptcrcksittn" ><spring:message code="sysGis.svyComptVO.crcksittnVal"/></label><!-- 균열상황 -->
			<select name="wkaSvyComptcrcksittn" id = "wkaSvyComptcrcksittn" title="<spring:message code="sysGis.svyComptVO.crcksittnVal"/>"class="mo_mt5 w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI126' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			
		</div>		
		<div class="SvyComptInput Hidden wka1">
			<label for="wkaSvyComptclps" ><spring:message code="sysGis.svyComptVO.sttuscrk"/></label><!-- 균열 -->
			<select name="wkaSvyComptclps" id="wkaSvyComptclps" title="<spring:message code="sysGis.svyComptVO.sttuscrk"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI127' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="wkaSvyComptsurfacedir" ><spring:message code="sysGis.svyComptVO.surfacedirVal"/></label><!-- 불연속면방향 -->
			<select name="wkaSvyComptsurfacedir" id="wkaSvyComptsurfacedir" title="<spring:message code="sysGis.svyComptVO.surfacedirVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI128' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>				
			<label for="wkaSvyComptwtrsttus" ><spring:message code="sysGis.svyComptVO.wtrsttusVal"/></label><!-- 풍화상태 -->
			<select name="wkaSvyComptwtrsttus" id= "wkaSvyComptwtrsttus" title="<spring:message code="sysGis.svyComptVO.wtrsttusVal"/>" class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI129' }">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</div>			
		<div class="SvyComptInput Hidden wka1">
			<label for="wkaSvyComptlndslddgscore" ><spring:message code="sysGis.svyComptVO.lndslddgsttusVal"/></label><!-- 산사태위험등급 -->
			<select name="wkaSvyComptlndslddgscore" id= "wkaSvyComptlndslddgscore" title="<spring:message code="sysGis.svyComptVO.lndslddgsttusVal"/>" class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI131' }">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="wkaSvyComptwatersttus" ><spring:message code="sysGis.svyComptVO.watersttusVal"/></label><!-- 용수상황 -->
			<select name="wkaSvyComptwatersttus" id="wkaSvyComptwatersttus" title="<spring:message code="sysGis.svyComptVO.watersttusVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI132' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</div>		
		<!-- 산사태 E -->
		

		
		<!-- 토석류 S-->
		<div class="SvyComptInput Hidden wka2">
			<label for="wkaSvyComptdglog2" ><spring:message code="sysGis.svyComptVO.dglogVal"/></label><!-- 피해이력 -->
			<select name="wkaSvyComptdglog2" id="wkaSvyComptdglog2" title="<spring:message code="sysGis.svyComptVO.dglogVal"/>" class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI098' }">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>	
			<label for="wkaSvyComptdireffcsafe2" ><spring:message code="sysGis.svyComptVO.direffcsafeVal"/></label><!-- 직접영향권 내 보호시설 -->
			<select name="wkaSvyComptdireffcsafe2" id="wkaSvyComptdireffcsafe2" title="<spring:message code="sysGis.svyComptVO.direffcsafeVal"/>"class="mo_mt5 w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI099' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="wkaSvyComptclpsscore" ><spring:message code="sysGis.svyComptVO.slidlandVal"/></label><!-- 붕괴 -->
			<select name="wkaSvyComptclpsscore" id="wkaSvyComptclpsscore" title="<spring:message code="sysGis.svyComptVO.slidlandVal"/>"class="mo_mt5 w4">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI144' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>			
		</div>
		<div class="SvyComptInput Hidden wka2">
			<label for="wkaSvyComptcorrosionscore" ><spring:message code="sysGis.svyComptVO.corrosionVal"/></label><!-- 침식 -->
			<select name="wkaSvyComptcorrosionscore" id="wkaSvyComptcorrosionscore" title="<spring:message code="sysGis.svyComptVO.corrosionVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI145' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>		
			<label for="wkaSvyComptbldrstnescore" ><spring:message code="sysGis.svyComptVO.bldrstneVal"/></label><!-- 전석 -->
			<select name="wkaSvyComptbldrstnescore" id="wkaSvyComptbldrstnescore" title="<spring:message code="sysGis.svyComptVO.bldrstneVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI146' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>		
			<label for="wkaSvyComptsoiltracescore" ><spring:message code="sysGis.svyComptVO.soiltraceVal"/></label><!-- 토석류 흔적 -->
			<select name="wkaSvyComptsoiltracescore" id="wkaSvyComptsoiltracescore" title="<spring:message code="sysGis.svyComptVO.soiltraceVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI147' }">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>	                          		
					</c:if>
				</c:forEach>
			</select>		
		</div>
		<div class="SvyComptInput Hidden wka2">
			<label for="wkaSvyComptforeststtusscore" ><spring:message code="sysGis.svyComptVO.foreststtusVal"/></label><!-- 산림현황 -->
			<select name="wkaSvyComptforeststtusscore" id="wkaSvyComptforeststtusscore"title="<spring:message code="sysGis.svyComptVO.foreststtusVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI148' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>		
			<label for="wkaSvyComptrootcharscore" ><spring:message code="sysGis.svyComptVO.rootcharVal"/></label><!-- 뿌리특성 -->
			<select name="wkaSvyComptrootcharscore" id="wkaSvyComptrootcharscore" title="<spring:message code="sysGis.svyComptVO.rootcharVal"/>"class="mo_mt5 w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'wka' && codeItem.codeId eq 'FEI149' }">
						<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>			
		</div>
		<!-- 토석류 E -->
	</li>
	<!-- 조회 조건 E-->
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="wkaSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btnwkaSearch" class="SvyComptBtn" name="wkaSvyComptSearch">검색</button>
        	<button type="button" id="btnwkaReset" class="SvyComptBtnReset" name="wkaSvyComptReset">초기화</button>
		</div>
	</li>
	
<!-- 검색결과 탭 S -->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
				<p class="txtsearch wka">검색결과 총 <span class="txtblue">0</span> 건</p>
				<table id="wkaSchRsltLst" summary="기초조사 상세검색에 대한 결과를 리스트로 출력합니다.">
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
