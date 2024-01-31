<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<validator:javascript formName="FckMseStripLandVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="sysFckMse.sldManage.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">계측장비</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 대상지 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="FckMseStripLandVO" action="${pageContext.request.contextPath}/sys/fck/mse/sld/insertMseSld.do" method="post">
			<input name="svySd" type="hidden" />
			<input name="svySgg" type="hidden" />
			<input name="svyEmd" type="hidden" />
			<input name="svyRi" type="hidden" />
			<input name="pnucode" type="hidden" />
			<input name="svyLon" type="hidden" />
			<input name="svyLat" type="hidden" />
			<input name="svyData" type="hidden" />
			<input name="wireExt" type="hidden" />
			<input name="licMeter" type="hidden" />
			<input name="licMeterPerCheck" type="hidden" />
			<input name="gwGauge" type="hidden" />
			<input name="rainGauge" type="hidden" />
			<input name="strcDpm" type="hidden" />
			<input name="surDpm" type="hidden" />
			<input name="gpsGauge" type="hidden" />
			<input name="gateway" type="hidden" />
			<input name="node" type="hidden" />
			<input name="modemNum" type="hidden" />
			<input name="cellNum" type="hidden" />
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tbody>
						<tr>
							<th><spring:message code="sysFckMse.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="8">
								<label for="svySdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sd"/></label>
								<select id="svySdView" name="svySdView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
									<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
									<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<label for="svySggView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sgg"/></label>
								<select id="svySggView" name="svySggView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
									<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
									<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<label for="svyEmdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.emd"/></label>
								<select id="svyEmdView" name="svyEmdView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
									<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
									<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<label for="svyRiView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.ri"/></label>
								<select id="svyRiView" name="svyRiView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.ri"/>">
									<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
									<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<input type="text" name="svyJibun" id="svyJibun" class="txt wd30"/>
							</td>
							<td>
								<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fncOwnerSearch(); return false;">조회</button>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.owner" /></th>
							<td colspan="4"><input name="owner" type="text" /></td>
							<th><spring:message code="sysFckMse.stripLandVO.lglLimit" /></th>
							<td colspan="3"><input name="lglLimit" type="text" /></td>
							<td>
								<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fncLadUsePlanPopup(); return false;">토지이용계획열람</button>
							</td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckMse.stripLandVO.se" /></th> <!-- 구분 -->						
							<th colspan="7"><spring:message code="sysFckMse.stripLandVO.msSensor" /></th> <!-- 계측 센서 -->
							<th colspan="2"><spring:message code="sysFckMse.stripLandVO.colctDevice" /></th> <!-- 수집 장치 -->
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.wireExt" /></th> <!-- 와이어 신축계 -->
							<th><spring:message code="sysFckMse.stripLandVO.licMeter" /></th> <!-- 지중경사계 -->
							<th><spring:message code="sysFckMse.stripLandVO.gwGauge" /></th> <!-- 지하수위계 -->
							<th><spring:message code="sysFckMse.stripLandVO.rainGauge" /></th> <!-- 강우계 -->
							<th><spring:message code="sysFckMse.stripLandVO.strcDpm" /></th> <!-- 구조물 변위계 -->
							<th><spring:message code="sysFckMse.stripLandVO.surDpm" /></th> <!-- 지표 변위계 -->
							<th><spring:message code="sysFckMse.stripLandVO.gpsGauge" /></th> <!-- GPS 변위계 -->
							<th><spring:message code="sysFckMse.stripLandVO.gateway" /></th> <!-- 게이트웨이 -->
							<th><spring:message code="sysFckMse.stripLandVO.node" /></th> <!-- 노드 -->
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.quantity" /></th> <!-- 수량 -->
							<td><input id="TW" name="wireExtCount" type="text" /></td>
							<td><input id="INC" name="licMeterCount" type="text" onchange="javascript:licCount(this); return false;"/></td>
							<td><input id="WL" name="gwGaugeCount" type="text" /></td>
							<td><input id="RG" name="rainGaugeCount" type="text" /></td>
							<td><input id="TM" name="strcDpmCount" type="text" /></td>
							<td><input id="SD" name="surDpmCount" type="text" /></td>
							<td><input id="GPS" name="gpsGaugeCount" type="text" /></td>
							<td><input id="GATEWAY" name="gatewayCount" type="text" /></td>
							<td><input id="NODE" name="nodeCount" type="text" /></td>
						</tr>
					</tbody>
				</table>
				
				<!-- 지중경사계 -->
				<div class="mainMenu">□ <spring:message code="sysFckMse.stripLandVO.mseLicMeterXYAdd" /></div>
       			<div>
       				<table id="mseLicMeterXY">
	       				<tbody>
	       					<tr>
	       						<th><spring:message code="sysFckMse.stripLandVO.licMeterId" /></th> <!-- 지중경사계 채널명 -->
	       						<th><spring:message code="sysFckMse.stripLandVO.se" /></th> <!-- 구분 -->
	       						<th><spring:message code="sysFckMse.stripLandVO.hg" /></th> <!-- 높이 -->
	       					</tr>
	       		
	       				</tbody>
	       			</table>
	       			<div class="BtnGroup">
	       				<div class="BtnRightArea">
			   				<button type="button" class="add-btn" onclick="javascript:fncMseSttusAdd('lic'); return false;"><spring:message code="button.add" /></button>
			           		<button type="button" class="del-btn" onclick="javascript:fncMseSttusDel('lic'); return false;"><spring:message code="button.delete" /></button>
				   		</div>
	       			</div>
       			</div>
       			
       			<div class="mainMenu">□ <spring:message code="sysFckMse.stripLandVO.mseGatewayAdd" /></div>
       			<div>
       				<table id="mseGateway">
	       				<tbody>
	       					<tr>
	       						<th><spring:message code="sysFckMse.stripLandVO.gatewayId" /></th> <!-- 게이트웨이 ID  -->
	       						<th><spring:message code="sysFckMse.stripLandVO.commModemNum" /></th> <!-- 통신모뎀번호 -->
	       						<th><spring:message code="sysFckMse.stripLandVO.cellNum" /></th> <!-- 이동전화번호 -->
	       					</tr>	       					
	       				</tbody>
	       			</table>
	       			<div class="BtnGroup">
						<div class="BtnRightArea">
			   				<button type="button" class="add-btn" onclick="javascript:fncMseSttusAdd('gw'); return false;"><spring:message code="button.add" /></button>
			           		<button type="button" class="del-btn" onclick="javascript:fncMseSttusDel('gw'); return false;"><spring:message code="button.delete" /></button>
				   		</div>
					</div>
       			</div>
       			<div class="BtnGroup" style="width:100%; margin-top:20px;">
		           <div class="BtnRightArea">
		              <button type="button" class="add-btn" onclick="javascript:fncMseSldManage('insert'); return false;"><spring:message code="title.create" /></button>
		              <button type="button" class="list-btn" onclick="javascript:fncList(); return false;"><spring:message code="button.list" /></button>
		           </div>
		        </div>
			</div>
		</form:form>
	</div>
</div>
<script>
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
</script>