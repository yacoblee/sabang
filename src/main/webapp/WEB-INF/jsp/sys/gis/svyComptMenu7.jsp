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
<!-- 계측기 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="mseSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="mseSvyComptEqpmnType"><spring:message code="sysGis.svyComptVO.eqpmntype"/></label>
			<select id="mseSvyComptEqpmnType" name="mseSvyComptEqpmnType" title="<spring:message code="sysGis.svyComptVO.eqpmntype"/>" class="w2">
				<option value=""><spring:message code="title.all" /></option>
				<option value="<spring:message code="sysGis.svyComptVO.wireext"/>"><spring:message code="sysGis.svyComptVO.wireext"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.licmeter"/>"><spring:message code="sysGis.svyComptVO.licmeter"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.gwgauge"/>"><spring:message code="sysGis.svyComptVO.gwgauge"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.raingauge"/>"><spring:message code="sysGis.svyComptVO.raingauge"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.strcdpm"/>"><spring:message code="sysGis.svyComptVO.strcdpm"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.surdpm"/>"><spring:message code="sysGis.svyComptVO.surdpm"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.gpsgauge"/>"><spring:message code="sysGis.svyComptVO.gpsgauge"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.gateway"/>"><spring:message code="sysGis.svyComptVO.gateway"/></option>
				<option value="<spring:message code="sysGis.svyComptVO.node"/>"><spring:message code="sysGis.svyComptVO.node"/></option>
			</select>
			
			<label for="mseSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" id= "mseSvyComptStartDt" class="SvyComptStartDt w3" name="mseSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" id= "mseSvyComptStartDt" class="SvyComptStartDt w3" name="mseSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" id= "mseSvyComptEndDt" class="SvyComptEndDt w3" name="mseSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" id= "mseSvyComptEndDt" class="SvyComptEndDt w3" name="mseSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="mseSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="mseSvyComptSd" class="SvyComptSd w4" name="mseSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="mseSvyComptSgg" class="SvyComptSgg w4" name="mseSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="mseSvyComptEmd" class="SvyComptEmd w4" name="mseSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="mseSvyComptRi" class="SvyComptRi w4" name="mseSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
			<input type="text" id="mseSvyComptJibun" name="mseSvyComptJibun"/>
		</div>
		<div>
			<label for="mseSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="mseSvyComptId" name="mseSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="mseSvyComptsvyUser" name="mseSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="mseSvyComptMstNm" name="mseSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>	
		<!-- 와이어신축계 -->
		<div class="SvyComptInput Hidden mse1">
			<label for="mseSvyComptWireExtLock"><spring:message code="sysGis.svyComptVO.lock"/></label><!-- 자물쇠 -->
			<select id="mseSvyComptWireExtLock" name="mseSvyComptWireExtLock" title="<spring:message code="sysGis.svyComptVO.lock"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptwireExtWire"><spring:message code="sysGis.svyComptVO.wire"/></label><!-- 와이어 -->
			<select id="mseSvyComptwireExtWire" name="mseSvyComptwireExtWire" title="<spring:message code="sysGis.svyComptVO.wire"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptwireExtCable"><spring:message code="sysGis.svyComptVO.cable"/></label><!-- 케이블 -->
			<select id="mseSvyComptwireExtCable" name="mseSvyComptwireExtCable" title="<spring:message code="sysGis.svyComptVO.cable"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse1">
			<label for="mseSvyComptWireExtPower"><spring:message code="sysGis.svyComptVO.power"/></label><!-- 전원 -->
			<select id="mseSvyComptWireExtPower" name="mseSvyComptWireExtPower" title="<spring:message code="sysGis.svyComptVO.power"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptWireExtProtect"><spring:message code="sysGis.svyComptVO.protectconsole"/></label><!-- 보호함체 -->
			<select id="mseSvyComptWireExtProtect" name="mseSvyComptWireExtProtect" title="<spring:message code="sysGis.svyComptVO.protectconsole"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptWireExtCensor"><spring:message code="sysGis.svyComptVO.censor"/></label><!-- 센서부 -->
			<select id="mseSvyComptWireExtCensor" name="mseSvyComptWireExtCensor" title="<spring:message code="sysGis.svyComptVO.censor"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse1">
			<label for="mseSvyComptWireExtSysCsrVal"><spring:message code="sysGis.svyComptVO.syscsrval"/></label><!-- 시스템센서값 -->
			<input type="text" id="mseSvyComptWireExtSysCsrVal" name="mseSvyComptWireExtSysCsrVal" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptWireExtSiteCsrVal"><spring:message code="sysGis.svyComptVO.sitecsrval"/></label><!-- 현장센서값 -->
			<input type="text" id="mseSvyComptWireExtSiteCsrVal" name="mseSvyComptWireExtSiteCsrVal" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptWireExtBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptWireExtBadStat" name="mseSvyComptWireExtBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse1">
			<label for="mseSvyComptWireExtChkRst"><spring:message code="sysGis.svyComptVO.checkresult"/></label><!-- 점검결과 -->
			<select id="mseSvyComptWireExtChkRst" name="mseSvyComptWireExtChkRst" title="<spring:message code="sysGis.svyComptVO.checkresult"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- 지중경사계 -->
		<div class="SvyComptInput Hidden mse2">
			<label for="mseSvyComptLicMeterLock"><spring:message code="sysGis.svyComptVO.lock"/></label><!-- 자물쇠 -->
			<select id="mseSvyComptLicMeterLock" name="mseSvyComptLicMeterLock" title="<spring:message code="sysGis.svyComptVO.lock"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptLicMeterCable"><spring:message code="sysGis.svyComptVO.cable"/></label><!-- 케이블 -->
			<select id="mseSvyComptLicMeterCable" name="mseSvyComptLicMeterCable" title="<spring:message code="sysGis.svyComptVO.cable"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptLicMeterProtect"><spring:message code="sysGis.svyComptVO.protectconsole"/></label><!-- 보호함체 -->
			<select id="mseSvyComptLicMeterProtect" name="mseSvyComptLicMeterProtect" title="<spring:message code="sysGis.svyComptVO.protectconsole"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse2">
			<label for="mseSvyComptLicMeterPower"><spring:message code="sysGis.svyComptVO.power"/></label><!-- 전원 -->
			<select id="mseSvyComptLicMeterPower" name="mseSvyComptLicMeterPower" title="<spring:message code="sysGis.svyComptVO.power"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptLicMeterCensor"><spring:message code="sysGis.svyComptVO.censor"/></label><!-- 센서부 -->
			<select id="mseSvyComptLicMeterCensor" name="mseSvyComptLicMeterCensor" title="<spring:message code="sysGis.svyComptVO.censor"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- 지하수위계 -->
		<div class="SvyComptInput Hidden mse3">
			<label for="mseSvyComptGwGaugeLock"><spring:message code="sysGis.svyComptVO.lock"/></label><!-- 자물쇠 -->
			<select id="mseSvyComptGwGaugeLock" name="mseSvyComptGwGaugeLock" title="<spring:message code="sysGis.svyComptVO.lock"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGwGaugeCable"><spring:message code="sysGis.svyComptVO.cable"/></label><!-- 케이블 -->
			<select id="mseSvyComptGwGaugeCable" name="mseSvyComptGwGaugeCable" title="<spring:message code="sysGis.svyComptVO.cable"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGwGaugeProtect"><spring:message code="sysGis.svyComptVO.protectconsole"/></label><!-- 보호함체 -->
			<select id="mseSvyComptGwGaugeProtect" name="mseSvyComptGwGaugeProtect" title="<spring:message code="sysGis.svyComptVO.protectconsole"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse3">
			<label for="mseSvyComptGwGaugePower"><spring:message code="sysGis.svyComptVO.power"/></label><!-- 전원 -->
			<select id="mseSvyComptGwGaugePower" name="mseSvyComptGwGaugePower" title="<spring:message code="sysGis.svyComptVO.power"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGwGaugeCensor"><spring:message code="sysGis.svyComptVO.censor"/></label><!-- 센서부 -->
			<select id="mseSvyComptGwGaugeCensor" name="mseSvyComptGwGaugeCensor" title="<spring:message code="sysGis.svyComptVO.censor"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGwGaugeBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptGwGaugeBadStat" name="mseSvyComptGwGaugeBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse3">
			<label for="mseSvyComptGwGaugeSiteCsrVal1"><spring:message code="sysGis.svyComptVO.sitecsrval1"/></label><!-- 현장센서값1 -->
			<input type="text" id="mseSvyComptGwGaugeSiteCsrVal1" name="mseSvyComptGwGaugeSiteCsrVal1" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptGwGaugeSiteCsrVal2"><spring:message code="sysGis.svyComptVO.sitecsrval2"/></label><!-- 현장센서값2 -->
			<input type="text" id="mseSvyComptGwGaugeSiteCsrVal2" name="mseSvyComptGwGaugeSiteCsrVal2" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptGwGaugeSysCsrVal1"><spring:message code="sysGis.svyComptVO.syscsrval1"/></label><!-- 시스템센서값1 -->
			<input type="text" id="mseSvyComptGwGaugeSysCsrVal1" name="mseSvyComptGwGaugeSysCsrVal1" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div class="SvyComptInput Hidden mse3">
			<label for="mseSvyComptGwGaugeSysCsrVal2"><spring:message code="sysGis.svyComptVO.syscsrval2"/></label><!-- 시스템센서값2 -->
			<input type="text" id="mseSvyComptGwGaugeSysCsrVal2" name="mseSvyComptGwGaugeSysCsrVal2" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptGwGaugeChkRst"><spring:message code="sysGis.svyComptVO.checkresult"/></label><!-- 점검결과 -->
			<select id="mseSvyComptGwGaugeChkRst" name="mseSvyComptGwGaugeChkRst" title="<spring:message code="sysGis.svyComptVO.checkresult"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- 강우계 -->
		<div class="SvyComptInput Hidden mse4">
			<label for="mseSvyComptRainGaugeCable"><spring:message code="sysGis.svyComptVO.cable"/></label><!-- 케이블 -->
			<select id="mseSvyComptRainGaugeCable" name="mseSvyComptRainGaugeCable" title="<spring:message code="sysGis.svyComptVO.cable"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptRainGaugeProtect"><spring:message code="sysGis.svyComptVO.protectconsole"/></label><!-- 보호함체 -->
			<select id="mseSvyComptRainGaugeProtect" name="mseSvyComptRainGaugeProtect" title="<spring:message code="sysGis.svyComptVO.protectconsole"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptRainGaugePower"><spring:message code="sysGis.svyComptVO.power"/></label><!-- 전원 -->
			<select id="mseSvyComptRainGaugePower" name="mseSvyComptRainGaugePower" title="<spring:message code="sysGis.svyComptVO.power"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse4">
			<label for="mseSvyComptRainGaugeCensor"><spring:message code="sysGis.svyComptVO.censor"/></label><!-- 센서부 -->
			<select id="mseSvyComptRainGaugeCensor" name="mseSvyComptRainGaugeCensor" title="<spring:message code="sysGis.svyComptVO.censor"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptRainGaugeVoltage"><spring:message code="sysGis.svyComptVO.voltage"/></label><!-- 전압 -->
			<select id="mseSvyComptRainGaugeVoltage" name="mseSvyComptRainGaugeVoltage" title="<spring:message code="sysGis.svyComptVO.voltage"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptRainGaugeBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptRainGaugeBadStat" name="mseSvyComptRainGaugeBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse4">
			<label for="mseSvyComptRainGaugeChkRst"><spring:message code="sysGis.svyComptVO.checkresult"/></label><!-- 점검결과 -->
			<select id="mseSvyComptRainGaugeChkRst" name="mseSvyComptRainGaugeChkRst" title="<spring:message code="sysGis.svyComptVO.checkresult"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- 구조물변위계 -->
		<!-- 지표변위계 -->
		<div class="SvyComptInput Hidden mse6">
			<label for="mseSvyComptSurDpmCable"><spring:message code="sysGis.svyComptVO.cable"/></label><!-- 케이블 -->
			<select id="mseSvyComptSurDpmCable" name="mseSvyComptSurDpmCable" title="<spring:message code="sysGis.svyComptVO.cable"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptSurDpmPower"><spring:message code="sysGis.svyComptVO.power"/></label><!-- 전원 -->
			<select id="mseSvyComptSurDpmPower" name="mseSvyComptSurDpmPower" title="<spring:message code="sysGis.svyComptVO.power"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptSurDpmCensor"><spring:message code="sysGis.svyComptVO.censor"/></label><!-- 센서부 -->
			<select id="mseSvyComptSurDpmCensor" name="mseSvyComptSurDpmCensor" title="<spring:message code="sysGis.svyComptVO.censor"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse6">
			<label for="mseSvyComptSurDpmSiteCsrValx"><spring:message code="sysGis.svyComptVO.sitecsrvalx"/></label><!-- 현장센서값X -->
			<input type="text" id="mseSvyComptSurDpmSiteCsrValx" name="mseSvyComptSurDpmSiteCsrValx" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptSurDpmSiteCsrValy"><spring:message code="sysGis.svyComptVO.sitecsrvaly"/></label><!-- 현장센서값Y -->
			<input type="text" id="mseSvyComptSurDpmSiteCsrValy" name="mseSvyComptSurDpmSiteCsrValy" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptSurDpmSiteCsrValAcc"><spring:message code="sysGis.svyComptVO.sitecsrvalacc"/></label><!-- 현장센서값 가속도 -->
			<input type="text" id="mseSvyComptSurDpmSiteCsrValAcc" name="mseSvyComptSurDpmSiteCsrValAcc" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div class="SvyComptInput Hidden mse6">
			<label for="mseSvyComptSurDpmSysCsrValx"><spring:message code="sysGis.svyComptVO.syscsrvalx"/></label><!-- 시스템센서값X -->
			<input type="text" id="mseSvyComptSurDpmSysCsrValx" name="mseSvyComptSurDpmSysCsrValx" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptSurDpmSysCsrValy"><spring:message code="sysGis.svyComptVO.syscsrvaly"/></label><!-- 시스템센서값Y -->
			<input type="text" id="mseSvyComptSurDpmSysCsrValy" name="mseSvyComptSurDpmSysCsrValy" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptSurDpmSysCsrValAcc"><spring:message code="sysGis.svyComptVO.syscsrvalacc"/></label><!-- 시스템센서값 가속도 -->
			<input type="text" id="mseSvyComptSurDpmSysCsrValAcc" name="mseSvyComptSurDpmSysCsrValAcc" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div class="SvyComptInput Hidden mse6">
			<label for="mseSvyComptSurDpmBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptSurDpmBadStat" name="mseSvyComptSurDpmBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptSurDpmChkRst"><spring:message code="sysGis.svyComptVO.checkresult"/></label><!-- 점검결과 -->
			<select id="mseSvyComptSurDpmChkRst" name="mseSvyComptSurDpmChkRst" title="<spring:message code="sysGis.svyComptVO.checkresult"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- GPS변위계 -->
		<div class="SvyComptInput Hidden mse7">
			<label for="mseSvyComptGpsGaugeCable"><spring:message code="sysGis.svyComptVO.cable"/></label><!-- 케이블 -->
			<select id="mseSvyComptGpsGaugeCable" name="mseSvyComptGpsGaugeCable" title="<spring:message code="sysGis.svyComptVO.cable"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGpsGaugePower"><spring:message code="sysGis.svyComptVO.power"/></label><!-- 전원 -->
			<select id="mseSvyComptGpsGaugePower" name="mseSvyComptGpsGaugePower" title="<spring:message code="sysGis.svyComptVO.power"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGpsGaugeCensor"><spring:message code="sysGis.svyComptVO.censor"/></label><!-- 센서부 -->
			<select id="mseSvyComptGpsGaugeCensor" name="mseSvyComptGpsGaugeCensor" title="<spring:message code="sysGis.svyComptVO.censor"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse7">
			<label for="mseSvyComptGpsGaugeBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptGpsGaugeBadStat" name="mseSvyComptGpsGaugeBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGpsGaugeChkRst"><spring:message code="sysGis.svyComptVO.checkresult"/></label><!-- 점검결과 -->
			<select id="mseSvyComptGpsGaugeChkRst" name="mseSvyComptGpsGaugeChkRst" title="<spring:message code="sysGis.svyComptVO.checkresult"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- 게이트웨이 -->
		<div class="SvyComptInput Hidden mse8">
			<label for="mseSvyComptGatewayLock"><spring:message code="sysGis.svyComptVO.lock"/></label><!-- 자물쇠 -->
			<select id="mseSvyComptGatewayLock" name="mseSvyComptGatewayLock" title="<spring:message code="sysGis.svyComptVO.lock"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGatewaySunLight"><spring:message code="sysGis.svyComptVO.sunlight"/></label><!-- 태양광 -->
			<select id="mseSvyComptGatewaySunLight" name="mseSvyComptGatewaySunLight" title="<spring:message code="sysGis.svyComptVO.sunlight"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGatewayConsole"><spring:message code="sysGis.svyComptVO.console"/></label><!-- 함체 -->
			<select id="mseSvyComptGatewayConsole" name="mseSvyComptGatewayConsole" title="<spring:message code="sysGis.svyComptVO.console"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse8">
			<label for="mseSvyComptGatewayBranchLine"><spring:message code="sysGis.svyComptVO.branchline"/></label><!-- 지선 -->
			<select id="mseSvyComptGatewayBranchLine" name="mseSvyComptGatewayBranchLine" title="<spring:message code="sysGis.svyComptVO.branchline"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGatewayProtect"><spring:message code="sysGis.svyComptVO.protectfens"/></label><!-- 보호휀스 -->
			<select id="mseSvyComptGatewayProtect" name="mseSvyComptGatewayProtect" title="<spring:message code="sysGis.svyComptVO.protectfens"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptGatewayInfoBoard"><spring:message code="sysGis.svyComptVO.infoboard"/></label><!-- 안내판 -->
			<select id="mseSvyComptGatewayInfoBoard" name="mseSvyComptGatewayInfoBoard" title="<spring:message code="sysGis.svyComptVO.infoboard"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse8">
			<label for="mseSvyComptGatewayGoodVoltage"><spring:message code="sysGis.svyComptVO.goodvoltage"/></label><!-- 양호전압 -->
			<input type="text" id="mseSvyComptGatewayGoodVoltage" name="mseSvyComptGatewayGoodVoltage" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptGatewayChkVoltage"><spring:message code="sysGis.svyComptVO.chkvoltage"/></label><!-- 측정전압 -->
			<input type="text" id="mseSvyComptGatewayChkVoltage" name="mseSvyComptGatewayChkVoltage" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptGatewayBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptGatewayBadStat" name="mseSvyComptGatewayBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse8">
			<label for="mseSvyComptGatewayChkRst"><spring:message code="sysGis.svyComptVO.checkresult"/></label><!-- 점검결과 -->
			<select id="mseSvyComptGatewayChkRst" name="mseSvyComptGatewayChkRst" title="<spring:message code="sysGis.svyComptVO.checkresult"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<!-- 노드 -->
		<div class="SvyComptInput Hidden mse9">
			<label for="mseSvyComptNodeLock"><spring:message code="sysGis.svyComptVO.lock"/></label><!-- 자물쇠 -->
			<select id="mseSvyComptNodeLock" name="mseSvyComptNodeLock" title="<spring:message code="sysGis.svyComptVO.lock"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptNodeSunLight"><spring:message code="sysGis.svyComptVO.sunlight"/></label><!-- 태양광 -->
			<select id="mseSvyComptNodeSunLight" name="mseSvyComptNodeSunLight" title="<spring:message code="sysGis.svyComptVO.sunlight"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptNodeConsole"><spring:message code="sysGis.svyComptVO.console"/></label><!-- 함체 -->
			<select id="mseSvyComptNodeConsole" name="mseSvyComptNodeConsole" title="<spring:message code="sysGis.svyComptVO.console"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse9">
			<label for="mseSvyComptNodeBranchLine"><spring:message code="sysGis.svyComptVO.branchline"/></label><!-- 지선 -->
			<select id="mseSvyComptNodeBranchLine" name="mseSvyComptNodeBranchLine" title="<spring:message code="sysGis.svyComptVO.branchline"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptNodeProtect"><spring:message code="sysGis.svyComptVO.protectfens"/></label><!-- 보호휀스 -->
			<select id="mseSvyComptNodeProtect" name="mseSvyComptNodeProtect" title="<spring:message code="sysGis.svyComptVO.protectfens"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
			<label for="mseSvyComptNodeInfoBoard"><spring:message code="sysGis.svyComptVO.infoboard"/></label><!-- 안내판 -->
			<select id="mseSvyComptNodeInfoBoard" name="mseSvyComptNodeInfoBoard" title="<spring:message code="sysGis.svyComptVO.infoboard"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
		<div class="SvyComptInput Hidden mse9">
			<label for="mseSvyComptNodeGoodVoltage1"><spring:message code="sysGis.svyComptVO.goodvoltage1"/></label><!-- 양호전압1 -->
			<input type="text" id="mseSvyComptNodeGoodVoltage1" name="mseSvyComptNodeGoodVoltage1" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptNodeGoodVoltage2"><spring:message code="sysGis.svyComptVO.goodvoltage2"/></label><!-- 양호전압2 -->
			<input type="text" id="mseSvyComptNodeGoodVoltage2" name="mseSvyComptNodeGoodVoltage2" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="mseSvyComptNodeChk"><spring:message code="sysGis.svyComptVO.chk"/></label><!-- 측정 -->
			<input type="text" id="mseSvyComptNodeChk" name="mseSvyComptNodeChk" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div class="SvyComptInput Hidden mse9">
			<label for="mseSvyComptNodeBadStat"><spring:message code="sysGis.svyComptVO.badstatus"/></label><!-- 불량상태 -->
			<select id="mseSvyComptNodeBadStat" name="mseSvyComptNodeBadStat" title="<spring:message code="sysGis.svyComptVO.badstatus"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="양호">양호</option>
				<option value="불량">불량</option>
			</select>
		</div>
	</li>
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="mseSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btnaprSearch" class="SvyComptBtn" name="mseSvyComptSearch">검색</button>
        	<button type="button" id="btnaprReset" class="SvyComptBtnReset" name="mseSvyComptReset">초기화</button>
		</div>
	</li>
	
	<!-- 조회 결과 S-->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
			<p class="txtsearch mse">검색결과 총 <span class="txtblue">0</span> 건</p>
			<table id="mseSchRsltLst" summary="계측기 상세검색에 대한 결과를 리스트로 출력합니다.">
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
	                		<th>조사종류</th>
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