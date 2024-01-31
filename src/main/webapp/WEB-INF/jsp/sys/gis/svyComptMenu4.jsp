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
<!-- 취약지역해제조사 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="cnlSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="cnlSvyComptSvyType"><spring:message code="sysGis.stripLandVO.type"/></label>
			<select id="cnlSvyComptSvyType"  name="cnlSvyComptSvyType" title="<spring:message code="sysGis.stripLandVO.type"/>" class="SvyComptType w2">
				<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
				<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
				</c:forEach>
			</select>
			
			<label for="cnlSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" class="SvyComptStartDt w3" id="cnlSvyComptStartDt" name="cnlSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptStartDt w3" id="cnlSvyComptStartDt" name="cnlSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" class="SvyComptEndDt w3" id="cnlSvyComptEndDt" name="cnlSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" class="SvyComptEndDt w3" id="cnlSvyComptEndDt" name="cnlSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="cnlSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="cnlSvyComptSd" class="SvyComptSd w4" name="cnlSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="cnlSvyComptSgg" class="SvyComptSgg w4" name="cnlSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="cnlSvyComptEmd" class="SvyComptEmd w4" name="cnlSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="cnlSvyComptRi" class="SvyComptRi w4" name="cnlSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
			<input type="text" id="cnlSvyComptJibun" name="cnlSvyComptJibun"/>
		</div>
		<div>
			<label for="cnlSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="cnlSvyComptId" name="cnlSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="cnlSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="cnlSvyComptsvyUser" name="cnlSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="cnlSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="cnlSvyComptMstNm" name="cnlSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<!-- 산사태 S-->
		<div class="SvyComptInput Hidden cnl1">
			<label for="cnlSvyComptbiztype"><spring:message code="sysLssCnl.svyComptVO.bizType"/></label><!-- 사업종류(공통) -->
			<select id="cnlSvyComptbiztype" name="cnlSvyComptbiztype" title="<spring:message code="sysLssCnl.svyComptVO.bizType"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI158'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="cnlSvyComptapplcegnermhd"><spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/></label><!-- 적용공법(공통) -->
			<select id="cnlSvyComptapplcegnermhd" name="cnlSvyComptapplcegnermhd" title="<spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI159'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="cnlSvyComptdsstroccrrncat"><spring:message code="sysLssCnl.svyComptVO.dsstrOccrrncAt"/></label><!-- 재해발생여부(공통) -->
			<select id="cnlSvyComptdsstroccrrncat" name="cnlSvyComptdsstroccrrncat" title="<spring:message code="sysLssCnl.svyComptVO.dsstrOccrrncAt"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI160'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</div>

		<div class="SvyComptInput Hidden cnl1">
			<label for="cnlSvyComptssttus"><spring:message code="sysLssCnl.svyComptVO.sSttus"/></label><!-- 사면상태(산사태) -->
			<select id="cnlSvyComptssttus" name="cnlSvyComptssttus" title="<spring:message code="sysLssCnl.svyComptVO.sSttus"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI162'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>

			<label for="cnlSvyComptstableintrprtscore"><spring:message code="sysLssCnl.svyComptVO.stableIntrprtYn"/></label><!-- 안정해석결과(산사태) -->
			<select id="cnlSvyComptstableintrprtscore" name="cnlSvyComptstableintrprtscore" title="<spring:message code="sysLssCnl.svyComptVO.stableIntrprtYn"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI163'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<!-- 산사태 E-->
		<!-- 토석류 -->
		<div class="SvyComptInput Hidden cnl2">
			<label for="cnlSvyComptbiztype2"><spring:message code="sysLssCnl.svyComptVO.bizType"/></label><!-- 사업종류(공통) -->
			<select id="cnlSvyComptbiztype2" name="cnlSvyComptbiztype2" title="<spring:message code="sysLssCnl.svyComptVO.bizType"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI158'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="cnlSvyComptapplcegnermhd2"><spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/></label><!-- 적용공법(공통) -->
			<select id="cnlSvyComptapplcegnermhd2" name="cnlSvyComptapplcegnermhd2" title="<spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI159'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="cnlSvyComptdsstroccrrncat2"><spring:message code="sysLssCnl.svyComptVO.dsstrOccrrncAt"/></label><!-- 재해발생여부(공통) -->
			<select id="cnlSvyComptdsstroccrrncat2" name="cnlSvyComptdsstroccrrncat2" title="<spring:message code="sysLssCnl.svyComptVO.dsstrOccrrncAt"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI160'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</div>		
		<div class="SvyComptInput Hidden cnl2">
			<label for="cnlSvyComptmrngsttus"><spring:message code="sysLssCnl.svyComptVO.mrngSttus"/></label><!-- 계류상태(토석류) -->
			<select id="cnlSvyComptmrngsttus" name="cnlSvyComptmrngsttus" title="<spring:message code="sysLssCnl.svyComptVO.mrngSttus"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI161'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="cnlSvyComptsimlatnrsltyn"><spring:message code="sysLssCnl.svyComptVO.simlatnRsltYn"/></label><!-- 시뮬레이션결과(토석류) -->
			<select id="cnlSvyComptsimlatnrsltyn" name="cnlSvyComptsimlatnrsltyn" title="<spring:message code="sysLssCnl.svyComptVO.simlatnRsltYn"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI164'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>
			<label for="cnlSvyComptreducat"><spring:message code="sysLssCnl.svyComptVO.reducAt"/></label><!-- 저감여부(토석류) -->
			<select id="cnlSvyComptreducat" name="cnlSvyComptreducat" title="<spring:message code="sysLssCnl.svyComptVO.reducAt"/>" class="mo_mt5 input_null w3">
				<option value=""<c:if test="${not empty svyCodeLst}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach items="${svyCodeLst}" var="codeItem" varStatus="status">
					<c:if test="${codeItem.svyType eq 'cnl' && codeItem.codeId eq 'FEI166'}">
					<option value="<c:out value="${codeItem.codeNm}"/>"><c:out value="${codeItem.codeDc}"/></option>
					</c:if>
				</c:forEach>
			</select>				
		</div>
	</li>	
		
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="cnlSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btncnlSearch" class="SvyComptBtn" name="cnlSvyComptSearch">검색</button>
        	<button type="button" id="btncnlReset" class="SvyComptBtnReset" name="cnlSvyComptReset">초기화</button>
		</div>
	</li>
	<!-- 검색결과 S-->
	<li>	
		<div class="map_tabcontent">
			<div class="tab_content on">
			<p class="txtsearch cnl">검색결과 총 <span class="txtblue">0</span> 건</p>
			<table id="cnlSchRsltLst" summary="기초조사 상세검색에 대한 결과를 리스트로 출력합니다.">
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
	<!-- 검색결과 E-->			  
	</form>		
</ul>
