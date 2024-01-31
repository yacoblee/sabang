<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssBsc.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">기초조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/bsc/sct/updateBscSvyComptView.do" method="post">
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			<input name="schsvyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
			<input name="schsvyRegion1" type="hidden" value="<c:out value='${searchVO.svyRegion1}'/>"/>
			<input name="schsvyRegion2" type="hidden" value="<c:out value='${searchVO.svyRegion2}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
			<input name="schncssty" type="hidden" value="<c:out value='${searchVO.ncssty}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssBsc.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${result.svyYear}"/>년</td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyId" /></th><!-- 조사ID -->
							<td><c:out value='${result.svyId}'/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyType" /></th><!-- 조사유형 -->
							<td><c:out value="${result.svyType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.region" /></th><!-- 관할 -->
							<td><c:out value="${result.svyRegion1} ${result.svyRegion2}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 위도 -->
							<td><c:out value="${result.svyLat}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경도 -->
							<td><c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.address" /></th><!-- 주소 -->
							<td colspan="5"><c:out value="${result.svySd} ${result.svySgg} ${result.svyEmd}"/><c:if test="${not empty result.svyRi}"> <c:out value="${result.svyRi}"/></c:if><c:if test="${not empty result.svyJibun}"> <c:out value="${result.svyJibun}"/></c:if></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.bp" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 시점 위도 -->
							<td><c:out value="${result.bpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.bp" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 시점 경도 -->
							<td><c:out value="${result.bpy}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.bp" /> <spring:message code="sysLssBsc.svyComptVO.ant" /></th><!-- 시점 고도 -->
							<td><c:out value="${result.bz}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.ep" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 끝점 위도 -->
							<td><c:out value="${result.epx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.ep" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 끝점 경도 -->
							<td><c:out value="${result.epy}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.ep" /> <spring:message code="sysLssBsc.svyComptVO.ant" /></th><!-- 끝점 고도 -->
							<td><c:out value="${result.ez}"/></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssBsc.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.svyDt"/></th><!-- 조사일 -->
							<td colspan="2"><c:out value="${result.svyDt}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.svyUser" /></th><!-- 조사자 -->
							<td colspan="2"><c:out value='${result.svyUser}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.sm" /></th><!-- 점수계 -->
							<td colspan="2"><c:out value="${result.svySm}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.accdtSvy" /> <spring:message code="sysLssBsc.svyComptVO.ncssty" /></th><!-- 실태조사 필요성 -->
							<td colspan="2"><c:out value="${result.ncssty}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사도 시점 위도 -->
							<td colspan="2"><c:out value="${result.slopeBpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사도 시점 경도 -->
							<td colspan="2"><c:out value="${result.slopeBpy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사도 끝점 위도 -->
							<td colspan="2"><c:out value="${result.slopeEpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.slope" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사도 끝점 경도 -->
							<td colspan="2"><c:out value="${result.slopeEpy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사길이 시점 위도 -->
							<td colspan="2"><c:out value="${result.slenBpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.lpt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사길이 시점 경도 -->
							<td colspan="2"><c:out value="${result.slenBpy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lat" /></th><!-- 경사길이 끝점 위도 -->
							<td colspan="2"><c:out value="${result.slenEpx}"/></td>
							<th><spring:message code="sysLssBsc.svyComptVO.sLen" /> <spring:message code="sysLssBsc.svyComptVO.upt" /> <spring:message code="sysLssBsc.svyComptVO.lon" /></th><!-- 경사길이 끝점 경도 -->
							<td colspan="2"><c:out value="${result.slenEpy}"/></td>
						</tr>
						<c:if test="${result.svyType eq '토석류'}">
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.safty" /></th><!-- 보호대상 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.devOcCause" /></th><!-- 황폐발생원 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.saftyVal}"/></td>
							<td><c:out value="${result.saftyScore}"/></td>
							<td><c:out value="${result.devOcCauseVal}"/></td>
							<td><c:out value="${result.devOcCauseScore}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.trntAvgSlp" /></th><!-- 계류평균경사 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.wclctArea" /></th><!-- 집수면적 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.trntAvgSlpVal}"/></td>
							<td><c:out value="${result.trntAvgSlpScore}"/></td>
							<td><c:out value="${result.wclctAreaVal}"/></td>
							<td><c:out value="${result.wclctAreaScore}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.tlTrntLt" /></th><!-- 총계류길이 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							<c:choose>
							<c:when test="${result.svyYear eq '2021'}">
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.distBmdsbRate" /></th><!-- 전석분포비율 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							</c:when>
							<c:otherwise>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.rskFactor1" /></th><!-- 위험인자 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							</c:otherwise>
							</c:choose>
						</tr>
						<tr class="center">
							<td><c:out value="${result.tlTrntLtVal}"/></td>
							<td><c:out value="${result.tlTrntLtScore}"/></td>
							<c:choose>
							<c:when test="${result.svyYear eq '2021'}">
							<td><c:out value="${result.distBmdsbRateVal}"/></td>
							<td><c:out value="${result.distBmdsbRateScore}"/></td>
							</c:when>
							<c:otherwise>
							<td><c:out value="${result.rskFactorVal}"/></td>
							<td><c:out value="${result.rskFactorScore}"/></td>
							</c:otherwise>
							</c:choose>
						</tr>
						</c:if>
						<c:if test="${result.svyType eq '산사태'}">
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.safty" /></th><!-- 보호대상 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.sLen" /></th><!-- 경사길이 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.saftyVal}"/></td>
							<td><c:out value="${result.saftyScore}"/></td>
							<td><c:out value="${result.sLenVal}"/></td>
							<td><c:out value="${result.sLenScore}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.slope" /></th><!-- 경사도 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.sForm" /></th><!-- 사면형 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.slopeVal}"/></td>
							<td><c:out value="${result.slopeScore}"/></td>
							<td><c:out value="${result.sFormVal}"/></td>
							<td><c:out value="${result.sFormScore}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.frstFr" /></th><!-- 임상 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.prntRck" /></th><!-- 모암 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.frstFrVal}"/></td>
							<td><c:out value="${result.frstFrScore}"/></td>
							<td><c:out value="${result.prntRckVal}"/></td>
							<td><c:out value="${result.prntRckScore}"/></td>
						</tr>
						<c:if test="${result.svyYear ne '2021'}">
						<tr>
							<th rowspan="2"><spring:message code="sysLssBsc.svyComptVO.rskFactor2" /></th><!-- 위험요인 -->
							<th><spring:message code="sysLssBsc.svyComptVO.value" /></th><!-- 측정값 -->
							<th><spring:message code="sysLssBsc.svyComptVO.score" /></th><!-- 점수 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.rskFactorVal}"/></td>
							<td><c:out value="${result.rskFactorScore}"/></td>
						</tr>
						</c:if>
						</c:if>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.mainRisk"/></th><!-- 주요위험성 -->
							<td colspan="5"><c:out value="${result.mainRisk}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssBsc.svyComptVO.opinion"/></th><!-- 조사자의견 -->
							<td colspan="5"><c:out value="${result.opinion}"/></td>
						</tr>
						<!-- 위치도 사진 -->
						<c:if test="${lcmap_result.size() > 0 }">
							<tr><th colspan="6">위치도 사진</th></tr>
							<tr>
								<td colspan="6">
									<div class="slide_box">
										<div class="slide">
											<c:forEach items="${lcmap_result}" var="item" varStatus="status">
												<div>
													<img src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count}"/>번 위치도" onerror="this.remove ? this.remove() : this.removeNode();">
												</div>
											</c:forEach>	
										</div>								
									</div>
								</td>
							</tr>
						</c:if>			
						<!-- 현장사진 -->
						<c:if test="${result.svyYear < 2022 }">
							<c:choose>
								<c:when test="${photo_result != null || orginl_photo_result != null}">
									<tr><th colspan="6">현장사진</th></tr>
									<c:forEach items="${orginl_photo_result}" var="item" varStatus="status" begin="0" end="3">
									<c:set var="photoUrl" value="${item}"/>
									<c:if test="${status.count%2 == 1 }">
									<c:set var="prevUrl" value="${photoUrl}"/>
									<tr>
									</c:if>
									<th colspan="3">현장사진 <c:out value="${status.count}"/>번</th>
									<c:if test="${status.last}">
										<c:if test="${status.count%2 == 1 }">
											</tr>
											<tr class="photoTr">
												<td colspan="3">
												 	<img src="/storage/fieldBook<c:url value='${prevUrl}'/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
												</td>
											</tr>
										</c:if>
									</c:if>
									<c:if test="${status.count%2 == 0 }">
										</tr>
										<tr class="photoTr">
											<td colspan="3">
											 	<img src="/storage/fieldBook<c:url value='${prevUrl}'/>" alt="<c:out value="${status.count-1}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</td>
											<td colspan="3">
											 	<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</td>
										</tr>
									</c:if>
									</c:forEach>
									<c:if test="${fn:length(orginl_photo_result) > 4}">
										<tr><th colspan="6">기타 사진</th></tr>
										<tr class="photoTr">
											<td colspan="6">
												<c:forEach items="${orginl_photo_result}" var="item" varStatus="status" begin="4"><img src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count-1}"/>번 현장사진" "></c:forEach>
											</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr><th colspan="6">사진태그 정보 없음</th></tr>
								</c:otherwise>
							</c:choose>
						</c:if>
					</tbody>
				</table>
				<c:if test="${result.svyYear >= 2022 }">								
					<c:choose>
						<c:when test="${photo_result != null }">
							<div class="photoWrap">
							<c:forEach items="${photo_result}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.FILENAME}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.FILENAME}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.TAG) > 0 }"><c:out value="${item.TAG}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl"><img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">																	</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<table>
					<tbody>
						<c:if test="${orginl_photo_result != null}">
							<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
							<tr class="photoTr">
								 <td colspan="6">
								 	<div>
									 	<c:forEach items="${orginl_photo_result}" var="item" varStatus="status">
									 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</c:forEach>
									</div>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>		
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_BSC','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button" class="del-btn" onclick="javascript:fncDeleteSvyCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
							</sec:authorize>
							<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView(); return false;"><spring:message code="title.update" /></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_BSC','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasAnyRole('ROLE_USER_BSC','ROLE_USER_CNRS','ROLE_USER')">
								<c:if test="${result.loginId eq userInfo.id or result.loginId eq userInfo.name or fn:contains(result.svyUser,userInfo.name)}">
									<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView(); return false;"><spring:message code="title.update" /></button>
								</c:if>
							</sec:authorize>
						</sec:authorize>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>