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
	<h3>${pageTitle} <spring:message code="title.detail"/></h3>
	<div id="contents">
		<form:form id="listForm" commandName="FckMseStripLandVO" action="${pageContext.request.contextPath}/sys/fck/mse/sld/updateMseSldView.do" method="post">
			<input name="sldId" type="hidden" value="${result.sldId}" />
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
							<td colspan="2"><c:out value="${result.owner}"></c:out></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="5"><c:out value='${result.svySd}'/> <c:out value='${result.svySgg}'/> <c:out value='${result.svyEmd}'/> <c:out value='${result.svyRi}'/> <c:out value='${result.svyJibun}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.lglLimit" /></th>
							<td colspan="5"><c:out value="${result.lglLimit}"/></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckMse.stripLandVO.se" /></th> <!-- 구분 -->		
							<th colspan="4"><spring:message code="sysFckMse.stripLandVO.quantity" /></th> <!-- 수량 -->
						</tr>
						<tr>
							<c:set var="msSensorCnt" value="${(result.licMeterCnt/2)+7 }"/>
							<th rowspan="${msSensorCnt }" class="rowChange"><spring:message code="sysFckMse.stripLandVO.msSensor" /></th> <!-- 계측 센서 -->
							<th><spring:message code="sysFckMse.stripLandVO.wireExt" /></th> <!-- 와이어 신축계 -->
							<td colspan="4">
								<c:if test="${not empty result.wireExt}"><c:out value="${result.wireExt}"/></c:if>
							</td>						
						</tr>
						<tr>
							<c:set var="licMeterCnt" value="${(result.licMeterCnt/2)+1}"/>
							<th rowspan="${licMeterCnt}" class="rowChange"><spring:message code="sysFckMse.stripLandVO.licMeter" /></th> <!-- 지중경사계 -->
							<th colspan="2"><spring:message code="sysFckMse.stripLandVO.mseLicMeterX" /></th> <!-- X축 -->
							<th colspan="2"><spring:message code="sysFckMse.stripLandVO.mseLicMeterY" /></th> <!-- Y축 -->
						</tr>
							<c:choose>
								<c:when test="${not empty licMeterList}">
									<c:forEach items="${licMeterList}" var="list">
										<c:forTokens items="${list.licHgList}" delims="," var="licMeterId" varStatus="status">
										<tr>
											<td colspan="2"><c:out value="${list.licId}"/>(<c:out value="${licMeterId}"/> m X축)</td>
											<td colspan="2"><c:out value="${list.licId}"/>(<c:out value="${licMeterId}"/> m Y축)</td>
										</tr>
										</c:forTokens>									
									</c:forEach>
									
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="2"></td>
										<td colspan="2"></td>
									</tr>
								</c:otherwise>
							</c:choose>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.gwGauge" /></th> <!-- 지하수위계 -->
							<td colspan="4">
								<c:if test="${not empty result.gwGauge}"><c:out value="${result.gwGauge}"/></c:if>								
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.rainGauge" /></th> <!-- 강우계 -->
							<td colspan="4">
								<c:if test="${not empty result.rainGauge}"><c:out value="${result.rainGauge}"/></c:if>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.strcDpm" /></th> <!-- 구조물 변위계 -->
							<td colspan="4">
								<c:if test="${not empty result.strcDpm}"><c:out value="${result.strcDpm}"/></c:if>
							</td>							
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.surDpm" /></th> <!-- 지표 변위계 -->
							<td colspan="4">
								<c:if test="${not empty result.surDpm}"><c:out value="${result.surDpm}"/></c:if>
							</td>						
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.gpsGauge" /></th> <!-- GPS 변위계 -->
							<td colspan="4">
								<c:if test="${not empty result.gpsGauge}"><c:out value="${result.gpsGauge}"/></c:if>
							</td>						
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckMse.stripLandVO.colctDevice" /></th> <!-- 수집 장치 -->
							<th><spring:message code="sysFckMse.stripLandVO.gateway" /></th> <!-- 게이트웨이 -->
							<td colspan="4">
								<c:if test="${not empty result.gateway}"><c:out value="${result.gateway}"/></c:if>
							</td>					
						</tr>
						<tr>
							<th><spring:message code="sysFckMse.stripLandVO.node" /></th> <!-- 노드 -->
							<td colspan="4">
								<c:if test="${not empty result.node}"><c:out value="${result.node}"/></c:if>
							</td>						
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckMse.stripLandVO.gatewayId" /></th> <!-- 게이트웨이 ID  -->
       						<th colspan="2"><spring:message code="sysFckMse.stripLandVO.commModemNum" /></th> <!-- 통신모뎀번호 -->
       						<th colspan="2"><spring:message code="sysFckMse.stripLandVO.cellNum" /></th> <!-- 이동전화번호 -->
						</tr>
						<c:if test="${not empty modemCellNumList}">
							<c:forEach items="${modemCellNumList}" var="list">
								<tr>
									<td colspan="2"><c:out value="${list.gatewayId}"/></td>
									<td colspan="2"><c:out value="${list.modemNum}"/></td>
									<td colspan="2"><c:out value="${list.cellNum}"/></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				
       			<div class="BtnGroup" style="width:100%; margin-top:20px;">
		           <div class="BtnRightArea">
		              <button type="button" class="modify-btn" onclick="javascript:fncUpdateMseSldView(); return false;"><spring:message code="title.update" /></button>
		              <button type="button" class="del-btn" onclick="javascript:fncDeleteMseSld(); return false;"><spring:message code="title.delete" /></button>
		              <button type="button" class="list-btn" onclick="javascript:fncList(); return false;"><spring:message code="button.list" /></button>
		           </div>
		        </div>
			</div>
		</form:form>
	</div>
</div>