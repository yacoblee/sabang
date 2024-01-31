<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysFckMse.sldList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">계측장비</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update"/></h3>
	<div id="contents">
		<form:form id="listForm" commandName="FckMseStripLandVO" action="${pageContext.request.contextPath}/sys/fck/mse/sld/updateMseSldView.do" method="post">
			<input name="sldId" type="hidden" value="${result.sldId}" />
			<input name="svySd" type="hidden" />
			<input name="svySgg" type="hidden" />
			<input name="svyEmd" type="hidden" />
			<input name="svyRi" type="hidden" />
			<input name="pnuCode" type="hidden" value="${result.pnucode}"/>
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
				<colgroup>
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
				</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="2"><c:out value="${result.sldId}"/></td>
							<th><spring:message code="sysFckMse.stripLandVO.owner" /></th>
							<td colspan="2"><input type="text" name="owner" value="${result.owner}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="4">
								<label for="svySdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sd"/></label>
								<select id="svySdView" name="svySdView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
									<option value=""<c:if test="${empty result.svySd || result.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
									<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${result.svySd eq svySdCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<label for="svySggView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.sgg"/></label>
								<select id="svySggView" name="svySggView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
									<option value=""<c:if test="${empty result.svySgg || result.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
									<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${result.svySgg eq svySggCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<label for="svyEmdView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.emd"/></label>
								<select id="svyEmdView" name="svyEmdView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.emd"/>" onchange="fncAdministEmdChange(event); return false;">
									<option value=""<c:if test="${empty result.svyEmd || result.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
									<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${result.svyEmd eq svyEmdCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<label for="svyRiView" class="Hidden"><spring:message code="sysLssBsc.stripLandVO.ri"/></label>
								<select id="svyRiView" name="svyRiView" class="txt wd15" title="<spring:message code="sysLssBsc.stripLandVO.ri"/>">
									<option value=""<c:if test="${empty result.svyRi || result.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
									<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${result.svyRi eq svyRiCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
									</c:forEach>
								</select>
								<input type="text" name="svyJibun" id="svyJibun" class="txt wd15" value="${result.svyJibun}"/>
							</td>
							<td>
								<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fncOwnerSearch(); return false;">조회</button>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.lglLimit" /></th>
							<td colspan="4"><input name="lglLimit" type="text" value="${result.lglLimit}"/></td>
							<td>
								<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fncLadUsePlanPopup(); return false;">토지이용계획열람</button>
							</td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckMse.stripLandVO.se" /></th> <!-- 구분 -->		
							<th colspan="4"><spring:message code="sysFckMse.stripLandVO.quantity" /></th> <!-- 수량 -->
						</tr>
						<tr>
							<th rowspan="7"><spring:message code="sysFckMse.stripLandVO.msSensor" /></th> <!-- 계측 센서 -->
							<th><spring:message code="sysFckMse.stripLandVO.wireExt" /></th> <!-- 와이어 신축계 -->
							<td colspan="4">
								<input type="text" name="wireExtCount" value="${result.wireExt}"/>
							</td>						
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.licMeter" /></th> <!-- 지중경사계 -->
							<td colspan="4">
								<input type="text" name="licMeterCount" value="${result.licCnt}" onchange="javascript:licCount(this); return false;"/>								
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.gwGauge" /></th> <!-- 지하수위계 -->
							<td colspan="4">
								<input type="text" name="gwGaugeCount" value="${result.gwGauge}"/>								
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.rainGauge" /></th> <!-- 강우계 -->
							<td colspan="4">
								<input type="text" name="rainGaugeCount" value="${result.rainGauge}"/>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.strcDpm" /></th> <!-- 구조물 변위계 -->
							<td colspan="4">
								<input type="text" name="strcDpmCount" value="${result.strcDpm}"/>
							</td>							
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.surDpm" /></th> <!-- 지표 변위계 -->
							<td colspan="4">
								<input type="text" name="surDpmCount" value="${result.surDpm}"/>
							</td>						
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.gpsGauge" /></th> <!-- GPS 변위계 -->
							<td colspan="4">
								<input type="text" name="gpsGaugeCount" value="${result.gpsGauge}"/>
							</td>						
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckMse.stripLandVO.colctDevice" /></th> <!-- 수집 장치 -->
							<th><spring:message code="sysFckMse.stripLandVO.gateway" /></th> <!-- 게이트웨이 -->
							<td colspan="4">
								<input type="text" name="gatewayCount" value="${result.gateway}"/>
							</td>					
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.node" /></th> <!-- 노드 -->
							<td colspan="4">
								<input type="text" name="nodeCount" value="${result.node}"/>
							</td>						
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
	       					<c:if test="${not empty licMeterList}">
	       						<c:forEach items="${licMeterList}" var="list" varStatus="status">
	       							<tr class="channel${status.count}">
	       								<th class="licMeterId${status.count}"><c:out value="${list.licId}"/></th>
	       								<th>X/Y축</th>
	       								<td>
	       									<c:forTokens items="${list.licHgList}" delims="," var="licMeterHg">
	       										<input type="text" name="licHg" value="${licMeterHg}"/>
	       									</c:forTokens>
	       								</td>
	       							</tr>
	       						</c:forEach>
	       					</c:if>
	       				</tbody>
	       			</table>
	       			<div class="BtnGroup">
	       				<div class="BtnRightArea">
			   				<button type="button" class="add-btn" onclick="javascript:fncMseSttusAdd('lic'); return false;"><spring:message code="button.add" /></button>
			           		<button type="button" class="del-btn" onclick="javascript:fncMseSttusDel('lic'); return false;"><spring:message code="button.delete" /></button>
				   		</div>
	       			</div>
       			</div>
       			
       			<!-- 통신모뎀번호 및 이동전화번호 -->
       			<div class="mainMenu">□ <spring:message code="sysFckMse.stripLandVO.mseGatewayAdd" /></div>
       			<div>
       				<table id="mseGateway">
	       				<tbody>
	       					<tr>
	       						<th><spring:message code="sysFckMse.stripLandVO.gatewayId" /></th> <!-- 게이트웨이 ID  -->
	       						<th><spring:message code="sysFckMse.stripLandVO.commModemNum" /></th> <!-- 통신모뎀번호 -->
	       						<th><spring:message code="sysFckMse.stripLandVO.cellNum" /></th> <!-- 이동전화번호 -->
	       					</tr>	    
	       					<c:if test="${not empty modemCellNumList}">
								<c:forEach items="${modemCellNumList}" var="list" varStatus="status">
									<tr class="channel${status.count}">
										<td><input type="text" name="gateWayIdView${status.count}" value="${list.gatewayId}"/></td>
										<td><input type="text" name="modemNumView${status.count}" value="${list.modemNum}"/></td>
										<td><input type="text" name="cellNumView${status.count}" value="${list.cellNum}"/></td>
									</tr>
								</c:forEach>
							</c:if>   					
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
		              <button type="button" class="modify-btn" onclick="javascript:fncMseSldManage('update'); return false;"><spring:message code="title.update" /></button>
		              <button type="button" class="list-btn" onclick="javascript:fncList(); return false;"><spring:message code="button.list" /></button>
		           </div>
		        </div>
			</div>
		</form:form>
	</div>
</div>