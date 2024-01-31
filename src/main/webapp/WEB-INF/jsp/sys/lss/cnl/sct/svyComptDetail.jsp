<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssCnl.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/cnl/sct/updateCnlSvyComptView.do" method="post">
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			<input name="schsvyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssCnl.svyComptList.cnlDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.year"/></th><!-- 조사연도 -->
							<td><c:out value="${result.svyYear}"/>년</td>
							<th><spring:message code="sysLssCnl.svyComptVO.svyId" /></th><!-- 조사ID -->
							<td><c:out value='${result.svyId}'/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.svyType" /></th><!-- 조사유형 -->
							<td><c:out value="${result.svyType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.region" /></th><!-- 관리주체 -->
							<td><c:out value="${result.region1} ${result.region2}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.lat" /></th><!-- 위도 -->
							<td><c:out value="${result.svyLat}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.lon" /></th><!-- 경도 -->
							<td><c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.address" /></th><!-- 소재지 -->
							<td colspan="5"><c:out value="${result.svySd} ${result.svySgg} ${result.svyEmd}"/><c:if test="${not empty result.svyRi}"> <c:out value="${result.svyRi}"/></c:if><c:if test="${not empty result.svyJibun}"> <c:out value="${result.svyJibun}"/></c:if></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssCnl.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.svyDt"/></th><!-- 조사일 -->
							<td colspan="2"><c:out value="${result.svyDt}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.svyUser" /></th><!-- 조사자 -->
							<td colspan="2"><c:out value="${result.svyUser}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.appnYear" /></th><!-- 지정년도 -->
							<td><c:out value="${result.appnYear}"/><c:if test="${not empty result.appnYear && result.appnYear ne '-'}">년</c:if></td>
							<th><spring:message code="sysLssCnl.svyComptVO.appnNo" /></th><!-- 지정번호 -->
							<td><c:out value="${result.appnNo}"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.appnArea" /></th><!-- 지정면적 -->
							<td><c:out value="${result.appnArea}"/><c:if test="${not empty result.appnArea && result.appnArea ne '-'}">m<sup>2</sup></c:if></td>
						</tr>
						<tr>
							<th rowspan="7"><spring:message code="sysLssCnl.svyComptVO.landOverview" /></th><!-- 대상지개황 -->
							<th><spring:message code="sysLssCnl.svyComptVO.weakappnsttus" /></th><!-- 당시현황 -->
							<td colspan="4"><c:out value="${result.weakAppnSttus}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.appnResn" /></th><!-- 지정사유 -->
							<td colspan="4"><c:out value="${result.appnResn}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.weakappnbstype" /></th><!-- 사업종가능 -->
							<td colspan="4"><c:out value="${result.weakAppnBsType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.weakappnposyn" /></th><!-- 가능여부 -->
							<td colspan="4"><c:out value="${result.weakAppnPosYn}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.weakappnsltnhy" /></th><!-- 선정사유 -->
							<td colspan="4"><c:out value="${result.weakAppnSltnHy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.weakappnareaset" /></th><!-- 구역설정 -->
							<td colspan="4"><c:out value="${result.weakAppnAreaSet}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.weakappnmstopn" /></th><!-- 당시종합의견 -->
							<td colspan="4"><c:out value="${result.weakAppnMstOpn}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.bizType"/></th><!-- 사업종류 -->
							<td colspan="2">
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${result.bizType eq '사방댐'}">사방댐설치사업</c:when> --%>
<%-- 									<c:when test="${result.bizType eq '계류보전'}">계류보전사업</c:when> --%>
<%-- 									<c:when test="${result.bizType eq '사방댐설치사업'}">사방댐설치사업</c:when> --%>
<%-- 									<c:when test="${result.bizType eq '계류보전사업'}">계류보전사업</c:when> --%>
<%-- 									<c:when test="${result.bizType eq '산지사방'}">산지사방</c:when> --%>
<%-- 									<c:otherwise>-</c:otherwise> --%>
<%-- 								</c:choose> --%>
								<c:out value="${result.bizType}"/>
							</td>
							<th rowspan="2"><spring:message code="sysLssCnl.svyComptVO.applcEgnerMhd"/></th><!-- 적용공법 -->
							<td rowspan="2" colspan="2">
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${result.applcEgnerMhd eq '불투과'}">불투과형사방댐</c:when> --%>
<%-- 									<c:when test="${result.applcEgnerMhd eq '투과'}">투과형사방댐</c:when> --%>
<%-- 									<c:when test="${result.applcEgnerMhd eq '일부'}">일부투과형사방댐</c:when> --%>
<%-- 									<c:when test="${result.applcEgnerMhd eq '불투과형사방댐'}">불투과형사방댐</c:when> --%>
<%-- 									<c:when test="${result.applcEgnerMhd eq '투과형사방댐'}">투과형사방댐</c:when> --%>
<%-- 									<c:when test="${result.applcEgnerMhd eq '일부투과형사방댐'}">일부투과형사방댐</c:when> --%>
<%-- 									<c:otherwise>-</c:otherwise> --%>
<%-- 								</c:choose> --%>
								<c:out value='${result.applcEgnerMhd}'/>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.cnstrYear" /></th><!-- 시공연도 -->
							<td colspan="2"><c:out value='${result.cnstrYear}'/><c:if test="${not empty result.cnstrYear && result.cnstrYear ne '-'}">년</c:if></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.fctSttus" /></th><!-- 시설물 상태 -->
							<td colspan="5"><c:out value='${result.fctSttus}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.dgrSttus" /></th><!-- 유역현황 -->
							<td colspan="5"><c:out value='${result.dgrSttus}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.dmgeHist" /></th><!-- 피해이력 -->
							<td colspan="5"><c:out value='${result.dmgeHist}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.speclNote" /></th><!-- 특이사항 -->
							<td colspan="5"><c:out value='${result.speclNote}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.basis" /></th><!-- 근거 -->
							<td colspan="5"><c:out value='${cnlBasis}' /></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.detailMatter" /></th><!-- 세부사항 -->
							<td colspan="5">
								<c:choose>
									<c:when test="${not empty result.detailMatter}"><c:out value='${result.detailMatter}'/></c:when>
									<c:when test="${result.detailMatter ne '' }"><c:out value='${result.detailMatter}'/></c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.dsstrOccrrncAt" /><!-- 재해발생여부 -->
							<td colspan="2"><c:out value='${result.cnlevl1}'/></td>
							<c:if test="${result.svyType eq '산사태'}">
								<th><spring:message code="sysLssCnl.svyComptVO.sSttus" /></th><!-- 사면상태 -->
								<td colspan="2"><c:out value='${result.cnlevl2}'/></td>
							</c:if>
							<c:if test="${result.svyType eq '토석류'}">
								<th><spring:message code="sysLssCnl.svyComptVO.mrngSttus" /></th><!-- 계류상태 -->
								<td colspan="2"><c:out value='${result.cnlevl2}'/></td>
							</c:if>
						</tr>
						<tr>
							<c:if test="${result.svyType eq '토석류'}">
								<th><spring:message code="sysLssCnl.svyComptVO.simlatnRsltYn" /></th><!-- 시뮬레이션 결과 -->
								<td colspan="2"><c:out value='${result.cnlevl3}'/></td>
							</c:if>
							<c:if test="${result.svyType eq '산사태'}">
								<th><spring:message code="sysLssCnl.svyComptVO.stableIntrprtYn" /></th><!-- 안정해석 결과 -->
								<td colspan="5"><c:out value='${result.cnlevl3}'/></td>
							</c:if>
							<c:if test="${result.svyType eq '토석류'}">
								<th><spring:message code="sysLssCnl.svyComptVO.reducAt"/></th><!-- 저감여부 -->
								<td colspan="2"><c:out value="${result.reducAt}"/></td>
							</c:if>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysLssCnl.svyComptVO.mstOpinion"/></th><!-- 종합의견 -->
							<td colspan="5">
								<c:out value="${result.mstOpinion1}"/>
							</td>
						</tr>
						<tr>
							<td colspan="5"><c:out value="${result.mstOpinion2}"/></td>
						</tr>
						<tr>
							<th rowspan="3">최종의견</th><!-- 최종종합의견1 -->
							<th>최종의견1</th>
							<td colspan="4">
								<c:out value="${result.finalMstOpinion1}"/>
							</td>
						</tr>
						<tr>
							<th>최종의견2</th>
							<td colspan="4">
								<c:out value="${result.finalMstOpinion2}"/>
							</td>
						</tr>
						<tr>
							<th>최종의견3</th>
							<td colspan="4">
								<c:out value="${result.finalMstOpinion3}"/>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.cnlYn"/></th><!-- 최종 검토결과 -->
							<td colspan="5">
							<c:choose>
							<c:when test="${result.cnlYn eq '가'}">해제 심의 대상지 적합</c:when>
							<c:when test="${result.cnlYn eq '부'}">해제 심의 대상지 부적합</c:when>
							<c:otherwise></c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
							<th rowspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn" /></th>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn1" /></th>
							<td><c:choose><c:when test="${result.cnlSlctRn1 eq '1'}">O</c:when><c:otherwise>-</c:otherwise></c:choose></td>
						</tr>
						<tr>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn2" /></th>
							<td><c:choose><c:when test="${result.cnlSlctRn2 eq '2'}">O</c:when><c:otherwise>-</c:otherwise></c:choose></td>
						</tr>
						<tr>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn3" /></th>
							<td><c:choose><c:when test="${result.cnlSlctRn3 eq '3'}">O</c:when><c:otherwise>-</c:otherwise></c:choose></td>
						</tr>
						<tr>
							<th colspan="4"><spring:message code="sysLssCnl.svyComptVO.cnlSlctRn4" /></th>
							<td><c:choose><c:when test="${result.cnlSlctRn4 eq '4'}">O</c:when><c:otherwise>-</c:otherwise></c:choose></td>
						</tr>
						 <c:if test="${result.svyType eq '토석류'}">
						 	<tr>
						 		<th>1회 토석류량</th><!-- 1회 토석류량 -->
						 		<td colspan="2"><c:out value='${result.oneDebrisFlow}'/></td>
						 		<th>정지조건</th><!-- 정지조건 -->
						 		<td colspan="2"><c:out value='${result.stopCnd}'/></td>
						 	</tr>
						 	<tr>
						 		<th>가중치</th><!-- 가중치 -->
						 		<td colspan="2"><c:out value='${result.wghtVal}'/></td>
						 		<th>전체 토석류량</th><!-- 전체토석류량 -->
						 		<td colspan="2"><c:out value='${result.allDebrisFlow}'/></td>
						 	</tr>
						 </c:if>
						<tr>
							<th rowspan="3"><spring:message code="sysLssCnl.svyComptVO.manageStat" /></th>
							<th><spring:message code="sysLssCnl.svyComptVO.bizNdDgrSttus" /></th><!-- 사업및 유역현황 -->
							<td colspan="4"><c:out value='${result.bizNdDgrSttus}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.dmgHistNdDgrChag" /></th><!-- 피해이력 및 유역변화 -->
							<td colspan="4"><c:out value='${result.dmgHistNdDgrChag}'/></td>
						</tr>
						<tr>
							<th style="line-height: 20px;"><spring:message code="sysLssCnl.svyComptVO.hbtOpnNdEtcMatter" /></th><!-- 주민의견 및 기타특이사항 -->
							<td colspan="4"><c:out value='${result.hbtOpnNdEtcMatter}'/></td>
						</tr>
						<tr>
							<th rowspan="2">해제평가</th>
							<th>재해발생 여부</th>
							<td colspan="4"><c:out value='${result.dsstrOccrrncAt}'/></td>
						</tr>
						<tr>
							<th>사면 계류 상태2</th>
							<td colspan="4"><c:out value='${result.relisEvlsMrngSttus2}'/></td>
						</tr>
						<!-- 항공사진을 통한 주변 환경변화 -->
						<tr>
							<td colspan="6"></td>
						</tr>
						<tr>
							<th colspan="6">항공사진을 통한 주변 환경변화</th>
						</tr>
						<tr class="photoTr">
						<c:choose>
							<c:when test="${not empty result.arlphoto1}">
								<td colspan="3"><img src="<c:url value='${result.arlphoto1}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
							</c:when>
							<c:otherwise>
								<td colspan="3"><img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;"></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${not empty result.arlphoto2}">
								<td colspan="3"><img src="<c:url value='${result.arlphoto2}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
							</c:when>
							<c:otherwise>
								<td colspan="3"><img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;"></td>
							</c:otherwise>
						</c:choose>
						</tr>
						<tr>
							<th colspan="3">산사태취약지역 지정 이전(2013)</th>
							<th colspan="3">산사태취약지역 지정 이후(2018)</th>
						</tr>
						<tr>
							<td colspan="6"></td>
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
													<img class="w100p" src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count}"/>번 위치도" onerror="this.remove ? this.remove() : this.removeNode();">
												</div>
											</c:forEach>	
										</div>								
									</div>
								</td>
							</tr>
						</c:if>			
					</tbody>
				</table>
				<c:if test="${result.svyType eq '토석류'}">
					<div class="mainMenu">□ 시뮬레이션 해석 결과 도면</div>
					<table class="mb30">
						<tr class="photoTr">
							<c:choose>
								<c:when test="${not empty result.rsltphoto1}">
									<td><img alt="토석류 해석 결과 도면" src="<c:url value='${result.rsltphoto1}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
								</c:when>
								<c:otherwise>
									<td><img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 15%;"></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<th>시뮬레이션 해석 결과 도면</th>
						</tr>
					</table>
				</c:if>
				<c:if test="${result.svyType eq '산사태'}">
					<div class="mainMenu">□ 산사태 안정해석 해석 결과 도면</div>
					<table class="mb30">
						<tr class="photoTr">
							<c:choose>
								<c:when test="${not empty result.rsltphoto2}">
									<td><img alt="산사태 해석 결과 도면(건기)" src="<c:url value='${result.rsltphoto2}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
								</c:when>
								<c:otherwise>
									<td><img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;"></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${not empty result.rsltphoto3}">
									<td><img alt="산사태 해석 결과 도면(우기)" src="<c:url value='${result.rsltphoto3}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
								</c:when>
								<c:otherwise>
									<td><img src="../../../../../../images/common/noimage.png" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 30%;"></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<th>건기 시</th>
							<th>우기 시</th>
						</tr>
					</table>
				</c:if>
				<hr>
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
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_CNL','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button" class="del-btn" onclick="javascript:fncDeleteSvyCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
							</sec:authorize>
							<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView(); return false;"><spring:message code="title.update" /></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_CNL','ROLE_SUB_ADMIN','ROLE_ADMIN')">
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_CNL','ROLE_USER_CNRS','ROLE_USER')"> --%>
<%-- 								<c:if test="${result.loginId eq userInfo.id or result.loginId eq userInfo.name or fn:contains(result.svyUser,userInfo.name)}"> --%>
									<c:if test="${access eq 'ADMIN' }">
										<button type="button" class="del-btn" onclick="javascript:fncDeleteSvyCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
									</c:if>
									<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
										<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView(); return false;"><spring:message code="title.update" /></button>
									</c:if>
<%-- 								</c:if> --%>
<%-- 							</sec:authorize> --%>
						</sec:authorize>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>