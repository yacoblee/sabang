<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysFckApr.svyComptList.title"/></c:set>

<!-- 사진태그 -->
<%-- <c:forEach items="${trglnd_result}" var="item" varStatus="status">
	<c:forEach items="${item.photoTag}" var="photo" varStatus="status">
		<c:out value="${photo.TAG}"/>
		<c:out value="${photo.FILENAME}"/>
	</c:forEach>
</c:forEach> --%>

<!-- 사진 -->
<%-- <c:forEach items="${trglnd_result}" var="item" varStatus="status">
	<c:forEach items="${item.photo}" var="photo" varStatus="status">
		<c:out value="${photo}"/>									
	</c:forEach>
</c:forEach> --%>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">외관점검</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/fck/apr/sct/updateFckAprComptView.do" method="post">
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
			<input name="schfckRslt" type="hidden" value="<c:out value='${searchVO.fckRslt}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<div class="BoardDetail">
				<c:if test="${result.svyType eq '사방댐 외관점검'}">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
				</colgroup>
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="2"><c:out value="${result.svyId}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!--조사유형 -->
							<td colspan="2"><c:out value='${result.svyType}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 사방댐종류 -->
							<td colspan="2"><c:out value="${result.knd}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.ecnrForm" /></th><!-- 형식 -->
							<td colspan="2"><c:out value='${result.svyForm}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
							<td colspan="2"><c:out value="${result.ecnrRnum}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
							<td colspan="2"><c:out value='${result.svyDt}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호-->
							<td colspan="2"><c:out value="${result.nationSpotNum}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
							<td colspan="2"><c:out value='${result.svyUser}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="5"><c:out value='${result.svySd}'/> <c:out value='${result.svySgg}'/> <c:out value='${result.svyEmd}'/> <c:out value='${result.svyRi}'/> <c:out value='${result.svyJibun}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보(WGS84)-->
							<td colspan="5"><c:out value='${result.svyLatLon}'/></td>
						</tr>
						<tr>
							<th rowspan="4"><spring:message code="sysFckApr.svyComptVO.fclt" /></th><!-- 시설제원 -->
							<th><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
							<td colspan="4"><c:out value="${result.fcltYear}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltUprHg"/></th><!-- 상장 -->
							<td colspan="4"><c:out value="${result.fcltUprHg}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltLwrHg"/></th><!-- 하장 -->
							<td colspan="4"><c:out value="${result.fcltLwrHg}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltStkHg"/></th><!-- 유효고 -->
							<td colspan="4"><c:out value="${result.fcltStkHg}"/></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr class="center">
							<th colspan="6"><spring:message code="sysFckApr.svyComptVO.mainfclt" /></th><!-- 주요시설 -->
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.orginlDam" /></th><!-- 본댐 -->
							<th><spring:message code="sysFckApr.svyComptVO.orginlDamVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.orginlDamVal}"/></td>
						</tr>
						<tr>							
							<th><spring:message code="sysFckApr.svyComptVO.orginlDamJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.orginlDamJdgVal}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.sideWall" /></th><!-- 측벽 -->
							<th><spring:message code="sysFckApr.svyComptVO.sideWallVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.sideWallVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.sideWallJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.sideWallJdgVal}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dwnspt" /></th><!-- 물받이 -->
							<th><spring:message code="sysFckApr.svyComptVO.dwnsptVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.dwnsptVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.dwnsptJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.dwnsptJdgVal}"/></td>
						</tr>
						<tr>
							<th colspan="5"><spring:message code="sysFckApr.svyComptVO.subfclt" /></th>
						</tr>
						<tr class="center">
							<th><spring:message code="sysFckApr.svyComptVO.flugt" /></th><!-- 수문 -->
							<th><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
							<th><spring:message code="sysFckApr.svyComptVO.sffc" /></th><!-- 안전시설 -->
							<th><spring:message code="sysFckApr.svyComptVO.accssrd" /></th><!-- 접근도로 -->
							<th><spring:message code="sysFckApr.svyComptVO.etc" /></th><!-- 기타 -->
						</tr>
						<tr class="center">
							<td><c:out value="${result.flugtJdgVal}"/></td>
							<td><c:out value="${result.vtnsttusJdgVal}"/></td>
							<td><c:out value="${result.sffcJdgVal}"/></td>
							<td><c:out value="${result.accssrdJdgVal}"/></td>
							<td><c:out value="${result.etcJdgVal}"/></td>
						</tr>
						<tr class="center">
							<th><spring:message code="sysFckApr.svyComptVO.snddpsitVal" /></th><!-- 저사량 -->
							<td colspan="2"><c:out value="${result.snddpsitVal}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.snddpsitJdgVal" /></th><!-- 저사상태 판정값-->
							<td colspan="1"><c:out value="${result.snddpsitJdgVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th>
							<td colspan="4"><c:out value="${result.fckRslt}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th>
							<td colspan="4"><c:out value="${result.mngmtr}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th>
							<td colspan="4"><c:out value="${result.appnRelis}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion1" /></th>
							<td colspan="4"><c:out value="${result.opinion1}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion2" /></th>
							<td colspan="4"><c:out value="${result.opinion2}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion3" /></th>
							<td colspan="4"><c:out value="${result.opinion3}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion4" /></th>
							<td colspan="4"><c:out value="${result.opinion4}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion5" /></th>
							<td colspan="4"><c:out value="${result.opinion5}"/></td>
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
										<div class="slide_btn">
											<div class="main_slidsprev" onclick="javascript:prev();">&#10094;</div>
											<div class="main_slidsnext" onclick="javascript:next();">&#10095;</div>
										</div>								
									</div>
								</td>
							</tr>
						</c:if>
							<tr><th colspan="6">현장사진</th></tr>
						</tbody>
					</table>
					<!-- 현장사진 -->
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
							<!-- 피해발생 항목 추가 -->						
							<tr>
								<th colspan="6"><spring:message code="sysFckApr.svyComptVO.sttusPrnt" /></th><!-- 피해발생현황도 -->							
							</tr>
							<tr>
								<th colspan="3"><spring:message code="sysFckApr.svyComptVO.sttusPrnt1" /></th><!-- 반수면 -->
								<th colspan="3"><spring:message code="sysFckApr.svyComptVO.sttusPrnt2" /></th><!-- 대수면 -->
							</tr>
							<tr class="photoTr">							
								<td colspan="3"><img src="<c:url value='${result.sttusPrnt1}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
								<td colspan="3"><img src="<c:url value='${result.sttusPrnt2}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
							</tr>
							<tr>
								<th colspan="6"><spring:message code="sysFckApr.svyComptVO.sttusPrnt3" /></th><!-- 본체 천단부·천단부·방수로·물받이 -->							
							</tr>
							<tr class="photoTr">
								<td colspan="6"><img style="width:70%;" src="<c:url value='${result.sttusPrnt3}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>							
							</tr>
							<tr>
								<th colspan="3"><spring:message code="sysFckApr.svyComptVO.sttusPrnt4" /></th><!-- 좌안측벽 -->
								<th colspan="3"><spring:message code="sysFckApr.svyComptVO.sttusPrnt5" /></th><!-- 우안측벽 -->
							</tr>
							<tr class="photoTr">
								<td colspan="3"><img src="<c:url value='${result.sttusPrnt4}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
								<td colspan="3"><img src="<c:url value='${result.sttusPrnt5}'/>" onerror="this.remove ? this.remove() : this.removeNode();"></td>
							</tr>
							<c:choose>
								<c:when test="${dmgSttus_result.size() > 0}">
									<tr>
										<th colspan="6"><spring:message code="sysFckApr.svyComptVO.dmgSttus" /></th><!-- 피해발생 현황 -->
									</tr>
									<tr>
										<th>번호</th>
										<th><spring:message code="sysFckApr.svyComptVO.dmgSttusType" /></th><!-- 피해발생 현황 - 유형 -->
										<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dmgSttusLoc" /></th><!-- 피해발생 현황 - 위치 -->
										<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dmgSttusNote" /></th><!-- 피해발생 현황 - 비고 -->
									</tr>
									<c:forEach items="${dmgSttus_result}" var="item" varStatus="status">
									<tr class="center">
										<td>${status.count}</td>
										<td><c:out value="${item.type}"/></td>
										<td colspan="2"><c:out value="${item.loc}"/></td>
										<td colspan="2"><c:out value="${item.note}"/></td>
									</tr>
									</c:forEach>
									<tr>
										<th colspan="6"><spring:message code="sysFckApr.svyComptVO.dmgSttus" /> 사진</th>
									</tr>
									<c:forEach items="${dmgSttus_result}" var="item" varStatus="status">
		 							<c:if test="${!empty item.photo}">
										<tr>
											<th colspan="6">${status.count}번 피해발생 현황 사진</th>
										</tr>
										<tr>
											<td colspan="6">
											<div class="dmgSttusPhotoList">
												<c:forEach items="${item.photo}" var="item" varStatus="status">
									 				<img src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count}"/>번 피해발생 현황 사진" style="width:400px; height:300px; margin-right: 20px;" onerror="this.remove ? this.remove() : this.removeNode();">
												</c:forEach>
											</div>
											</td>
										</tr>
									</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<th colspan="6">피해발생 현황이 없습니다.</th>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</c:if>
				
				<c:if test="${result.svyType eq '산지사방 외관점검'}">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
				</colgroup>
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="2"><c:out value="${result.svyId}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!--조사유형 -->
							<td colspan="2"><c:out value='${result.svyType}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
							<td colspan="2"><c:out value="${result.knd}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
							<td colspan="2"><c:out value='${result.svyDt}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.dsgArea" /></th><!-- 지정면적 -->
							<td colspan="2"><c:out value='${result.dsgArea}'/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
							<td colspan="2"><c:out value='${result.svyUser}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="5"><c:out value='${result.svySd}'/> <c:out value='${result.svySgg}'/> <c:out value='${result.svyEmd}'/> <c:out value='${result.svyRi}'/> <c:out value='${result.svyJibun}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보(WGS84)-->
							<td colspan="5"><c:out value='${result.svyLatLon}'/></td>
						</tr>
						<tr>
							<th rowspan="4"><spring:message code="sysFckApr.svyComptVO.fclt" /></th><!-- 시설제원 -->
							<th><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
							<td colspan="4"><c:out value="${result.fcltYear}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltHg"/></th><!-- 높이 -->
							<td colspan="4"><c:out value="${result.fcltHg}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltRng"/></th><!-- 폭 -->
							<td colspan="4"><c:out value="${result.fcltRng}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltSlp"/></th><!-- 평균경사 -->
							<td colspan="4"><c:out value="${result.fcltSlp}"/></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr class="center">
							<th colspan="6"><spring:message code="sysFckApr.svyComptVO.mainfclt" /></th><!-- 주요시설 -->
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.reinfc" /></th><!-- 보강시설 -->
							<th><spring:message code="sysFckApr.svyComptVO.reinfcVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.reinfcVal}"/></td>
						</tr>
						<tr>							
							<th><spring:message code="sysFckApr.svyComptVO.reinfcJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.reinfcJdgVal}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.prtc" /></th><!-- 보호시설 -->
							<th><spring:message code="sysFckApr.svyComptVO.prtcVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.prtcVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.prtcJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.prtcJdgVal}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.drng" /></th><!-- 배수시설 -->
							<th><spring:message code="sysFckApr.svyComptVO.drngVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.drngVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.drngJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.drngJdgVal}"/></td>
						</tr>						
						<tr>
							<th colspan="6"><spring:message code="sysFckApr.svyComptVO.subfclt" /></th>
						</tr>
						<tr class="center">
							<th><spring:message code="sysFckApr.svyComptVO.flugt" /></th><!-- 수문 -->
							<th><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
							<th><spring:message code="sysFckApr.svyComptVO.sffc" /></th><!-- 안전시설 -->
							<th><spring:message code="sysFckApr.svyComptVO.accssrd" /></th><!-- 접근도로 -->
							<th><spring:message code="sysFckApr.svyComptVO.etc" /></th><!-- 기타 -->
							<th></th>
						</tr>
						<tr class="center">
							<td><c:out value="${result.flugtJdgVal}"/></td>
							<td><c:out value="${result.vtnsttusJdgVal}"/></td>
							<td><c:out value="${result.sffcJdgVal}"/></td>
							<td><c:out value="${result.accssrdJdgVal}"/></td>
							<td><c:out value="${result.etcJdgVal}"/></td>
							<td></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.slpJdgVal" /></th><!-- 사면상태 -->
							<td colspan="5"><c:out value="${result.slopeJdgVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th>
							<td colspan="5"><c:out value="${result.fckRslt}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th>
							<td colspan="5"><c:out value="${result.mngmtr}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th>
							<td colspan="5"><c:out value="${result.appnRelis}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion1" /></th>
							<td colspan="5"><c:out value="${result.opinion1}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion2" /></th>
							<td colspan="5"><c:out value="${result.opinion2}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion3" /></th>
							<td colspan="5"><c:out value="${result.opinion3}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion4" /></th>
							<td colspan="5"><c:out value="${result.opinion4}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion5" /></th>
							<td colspan="5"><c:out value="${result.opinion5}"/></td>
						</tr>
						<!-- 위치도 사진 -->
						<c:if test="${lcmap_result.size() > 0 && trglnd_result.size() eq 0}">
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
										<div class="slide_btn">
											<div class="main_slidsprev" onclick="javascript:prev();">&#10094;</div>
											<div class="main_slidsnext" onclick="javascript:next();">&#10095;</div>
										</div>								
									</div>
								</td>
							</tr>
						</c:if>
						<tr><th colspan="6">현장사진</th></tr>
					</tbody>
				</table>
				<!-- 현장사진 -->
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
						<c:choose>
							<c:when test="${trglnd_result.size() > 0 }">
								<tr>						
									<th colspan="6"><spring:message code="sysFckApr.svyComptVO.trglnd" /></th><!-- 대상지 위치 및 주요시설 -->							
								</tr>
								<tr>						
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNo" /></th><!-- 번호 -->
									<th colspan="2"><spring:message code="sysFckApr.svyComptVO.trglndPnt" /></th><!-- 좌표 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndStrctu" /></th><!-- 주요시설물 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndSttus" /></th><!-- 현황 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNote" /></th><!-- 비고 -->
								</tr>
								<tr>
									<th><spring:message code="sysFckApr.svyComptVO.trglndPy" /></th><!-- 위도 -->
									<th><spring:message code="sysFckApr.svyComptVO.trglndPx" /></th><!-- 경도 -->
								</tr>
		 						<c:forEach items="${trglnd_result}" var="item" varStatus="status">
								<tr class="center">
									<td><c:out value="${status.count}"/></td>							
									<td><c:out value="${item.py}"/></td>
									<td><c:out value="${item.px}"/></td>
									<td><c:out value="${item.strctu}"/></td>
									<td><c:out value="${item.sttus}"/></td>
									<td><c:out value="${item.note}"/></td>
								</tr>
								</c:forEach>
							<c:if test="${lcmap_result.size() > 0 }">
								<tr><th colspan="6">대상지 위치도 사진</th></tr>
								<tr>
									<td colspan="6">
										<div class="slide_box">
											<div class="slide">
												<c:forEach items="${lcmap_result}" var="item" varStatus="status">
													<div>
														<p><c:out value="${status.count}"/>호지</p>
														<img src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count}"/>번 위치도" onerror="this.remove ? this.remove() : this.removeNode();">
													</div>
												</c:forEach>	
											</div>								
											<div class="slide_btn">
												<div class="main_slidsprev" onclick="javascript:prev();">&#10094;</div>
												<div class="main_slidsnext" onclick="javascript:next();">&#10095;</div>
											</div>								
										</div>
									</td>
								</tr>
							</c:if>
								<c:forEach items="${trglnd_result}" var="item" varStatus="status">
								<c:set var="photoTag" value="${item.photoTag }"/>
	 							<c:set var="photo" value="${item.photo }"/>
	 							<c:if test="${!empty photo}">
									<tr><th colspan="6"><c:out value="${status.count }"/>호 대상지</th></tr>
									<tr class="photoTr">
										 <td colspan="6">
										 	<div>
												<c:choose>
												 	<c:when test="${!empty photoTag}">
												 		<c:forEach items="${photoTag}" var="item" varStatus="status">
												 			<div class="photoBox">
																<img src="/storage/fieldBook<c:url value='${item.FILENAME}'/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">	
												 				<span><c:out value="${item.TAG}"/></span>
												 			</div>
														</c:forEach>
												 	</c:when>
											 		<c:when test="${empty photoTag and !empty photo}">
											 			<c:forEach items="${photo}" var="item" varStatus="status">
											 				<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 200px; height: 200px; margin-right: 30px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();"> 
														</c:forEach>
											 		</c:when>
											 	</c:choose>
											</div>
										</td>
									</tr>
								</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<th colspan="6">대상지 위치 및 주요시설이 없습니다.</th>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				</c:if>
				
				<c:if test="${result.svyType eq '계류보전 외관점검'}">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
					<col style="width: 16.7%;">
				</colgroup>
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="2"><c:out value="${result.svyId}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!--조사유형 -->
							<td colspan="2"><c:out value='${result.svyType}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
							<td colspan="2"><c:out value="${result.knd}"/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
							<td colspan="2"><c:out value='${result.svyDt}'/></td>							
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.dsgArea" /></th><!-- 지정면적 -->
							<td colspan="2"><c:out value='${result.dsgArea}'/></td>
							<th><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
							<td colspan="2"><c:out value='${result.svyUser}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="5"><c:out value='${result.svySd}'/> <c:out value='${result.svySgg}'/> <c:out value='${result.svyEmd}'/> <c:out value='${result.svyRi}'/> <c:out value='${result.svyJibun}'/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보(WGS84)-->
							<td colspan="5"><c:out value='${result.svyLatLon}'/></td>
						</tr>
						<tr>
							<th rowspan="4"><spring:message code="sysFckApr.svyComptVO.fclt" /></th><!-- 시설제원 -->
							<th><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
							<td colspan="4"><c:out value="${result.fcltYear}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltLng"/></th><!-- 길이 -->
							<td colspan="4"><c:out value="${result.fcltLng}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltRng"/></th><!-- 폭 -->
							<td colspan="4"><c:out value="${result.fcltRng}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fcltDept"/></th><!-- 깊이 -->
							<td colspan="4"><c:out value="${result.fcltDept}"/></td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckApr.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<tbody>
						<tr class="center">
							<th colspan="6"><spring:message code="sysFckApr.svyComptVO.mainfclt" /></th>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.chkdam" /></th><!-- 골막이-->
							<th><spring:message code="sysFckApr.svyComptVO.chkdamVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.chkdamVal}"/></td>
						</tr>
						<tr>							
							<th><spring:message code="sysFckApr.svyComptVO.chkdamJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.chkdamJdgVal}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.rvtmnt" /></th><!-- 기슭막이 -->
							<th><spring:message code="sysFckApr.svyComptVO.rvtmntVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.rvtmntVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.rvtmntJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.rvtmntJdgVal}"/></td>
						</tr>
						<tr>
							<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.grdstabl" /></th><!-- 바닥막이 -->
							<th><spring:message code="sysFckApr.svyComptVO.grdstablVal" /></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.grdstablVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.grdstablJdgVal" /></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.grdstablJdgVal}"/></td>
						</tr>
						<tr>
							<th colspan="6"><spring:message code="sysFckApr.svyComptVO.subfclt" /></th>
						</tr>
						<tr class="center">
							<th><spring:message code="sysFckApr.svyComptVO.flugt" /></th><!-- 수문 -->
							<th><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
							<th><spring:message code="sysFckApr.svyComptVO.sffc" /></th><!-- 안전시설 -->
							<th><spring:message code="sysFckApr.svyComptVO.accssrd" /></th><!-- 접근도로 -->
							<th><spring:message code="sysFckApr.svyComptVO.etc" /></th><!-- 기타 -->
							<th></th>
						</tr>
						<tr class="center">
							<td><c:out value="${result.flugtJdgVal}"/></td>
							<td><c:out value="${result.vtnsttusJdgVal}"/></td>
							<td><c:out value="${result.sffcJdgVal}"/></td>
							<td><c:out value="${result.accssrdJdgVal}"/></td>
							<td><c:out value="${result.etcJdgVal}"/></td>
							<td></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.mooringJdgVal" /></th><!-- 계류상태 판정값 -->
							<td colspan="5"><c:out value="${result.mooringJdgVal}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th>
							<td colspan="5"><c:out value="${result.fckRslt}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th>
							<td colspan="5"><c:out value="${result.mngmtr}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th>
							<td colspan="5"><c:out value="${result.appnRelis}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion1" /></th>
							<td colspan="5"><c:out value="${result.opinion1}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion2" /></th>
							<td colspan="5"><c:out value="${result.opinion2}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion3" /></th>
							<td colspan="5"><c:out value="${result.opinion3}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion4" /></th>
							<td colspan="5"><c:out value="${result.opinion4}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysFckApr.svyComptVO.opinion5" /></th>
							<td colspan="5"><c:out value="${result.opinion5}"/></td>
						</tr>
						<!-- 위치도 사진 -->
						<c:if test="${lcmap_result.size() > 0 }">
							<tr><th colspan="6">위치도 사진</th></tr>
							<tr>
								<td colspan="6">
									<div class="slide_box">
										<div class="slide">
											<c:forEach items="${lcmap_result}" var="item" varStatus="status" begin="0" end="0">
												<div>
													<img src="/storage/fieldBook<c:url value='${item}'/>" alt="<c:out value="${status.count}"/>번 위치도" onerror="this.remove ? this.remove() : this.removeNode();">
												</div>
											</c:forEach>	
										</div>								
										<div class="slide_btn">
											<div class="main_slidsprev" onclick="javascript:prev();">&#10094;</div>
											<div class="main_slidsnext" onclick="javascript:next();">&#10095;</div>
										</div>								
									</div>
								</td>
							</tr>
						</c:if>				
						<tr><th colspan="6">현장사진</th></tr>
					</tbody>
				</table>
				<!-- 현장사진 -->
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
						<c:choose>
							<c:when test="${trglnd_result.size() > 0}">
								<tr>						
									<th colspan="6"><spring:message code="sysFckApr.svyComptVO.trglnd" /></th><!-- 대상지 위치 및 주요시설 -->							
								</tr>
								<tr>						
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNo" /></th><!-- 번호 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndLc"/></th><!-- 위치 -->
									<th colspan="2"><spring:message code="sysFckApr.svyComptVO.trglndPnt" /></th><!-- 좌표 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndStrctu" /></th><!-- 주요시설물 -->
									<%-- <th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndSttus" /></th><!-- 현황 --> --%>
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.trglndNote" /></th><!-- 비고 -->
								</tr>
								<tr>
									<th><spring:message code="sysFckApr.svyComptVO.trglndPy" /></th><!-- 위도 -->
									<th><spring:message code="sysFckApr.svyComptVO.trglndPx" /></th><!-- 경도 -->
								</tr>
		 						<c:forEach items="${trglnd_result}" var="item" varStatus="status">
								<tr class="center">
									<td rowspan="2"><c:out value="${status.count}"/></td>
									<td><spring:message code="sysFckApr.svyComptVO.trglndLcPnttm"/></td>															
									<td><c:out value="${item.py1}"/></td>
									<td><c:out value="${item.px1}"/></td>
									<td rowspan="2"><c:out value="${item.strctu}"/></td>
									<%-- <td rowspan="2"><c:out value="${item.sttus}"/></td> --%>
									<td rowspan="2"><c:out value="${item.note}"/></td>
								</tr>
								<tr>
									<td style="text-align:center;"><spring:message code="sysFckApr.svyComptVO.trglndLcTmnl"/></td>
									<td style="text-align:center;"><c:out value="${item.py2}"/></td>
									<td style="text-align:center;"><c:out value="${item.px2}"/></td>							
								</tr>
								</c:forEach>
								<c:forEach items="${trglnd_result}" var="item" varStatus="status">
								<c:set var="photoTag" value="${item.photoTag }"/>
	 							<c:set var="photo" value="${item.photo }"/>
	 							
									<tr><th colspan="6"><c:out value="${status.count }"/>호 대상지</th></tr>
									<tr class="photoTr">
										 <td colspan="6">
										 	<div>
											 	<c:choose>
												 	<c:when test="${!empty photoTag}">
												 		<c:forEach items="${photoTag}" var="item" varStatus="status">
												 			<div class="photoBox">
																<img src="/storage/fieldBook<c:url value='${item.FILENAME}'/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">	
												 				<span><c:out value="${item.TAG}"/></span>
												 			</div>
														</c:forEach>
												 	</c:when>
											 		<c:when test="${empty photoTag and !empty photo}">
											 			<c:forEach items="${photo}" var="item" varStatus="status">
											 				<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 200px; height: 200px; margin-right: 30px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();"> 
														</c:forEach>
											 		</c:when>
											 	</c:choose>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<th colspan="6">대상지 위치 및 주요시설이 없습니다.</th>
								</tr>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${dmgFclt_result.size() > 0}">
								<tr>						
									<th colspan="6"><spring:message code="sysFckApr.svyComptVO.dmgFclt" /></th><!-- 피해시설 위치 및 현황 -->							
								</tr>
								<tr>						
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltNo" /></th><!-- 번호 -->
									<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltPnt" /></th><!-- 좌표 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltStrctu" /></th><!-- 주요시설물 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltSttus" /></th><!-- 현황 -->
									<th rowspan="2"><spring:message code="sysFckApr.svyComptVO.dmgFcltNote" /></th><!-- 비고 -->
								</tr>
								<tr>
									<th><spring:message code="sysFckApr.svyComptVO.dmgFcltPy" /></th><!-- 위도 -->
									<th><spring:message code="sysFckApr.svyComptVO.dmgFcltPx" /></th><!-- 경도 -->
								</tr>
		 						<c:forEach items="${dmgFclt_result}" var="item" varStatus="status">
								<tr class="center">
									<td><c:out value="${status.count}"/></td>							
									<td><c:out value="${item.py}"/></td>
									<td><c:out value="${item.px}"/></td>
									<td><c:out value="${item.strctu}"/></td>
									<td><c:out value="${item.sttus}"/></td>
									<td><c:out value="${item.note}"/></td>
								</tr>
								</c:forEach>
								<c:forEach items="${dmgFclt_result}" var="item" varStatus="status">
								<c:set var="photoTag" value="${item.photoTag }"/>
	 							<c:set var="photo" value="${item.photo }"/>
	 							
	 							<c:if test="${!empty photo}">
									<tr><th colspan="6"><c:out value="${status.count }"/>번 피해시설</th></tr>
									<tr class="photoTr">
										 <td colspan="6">
										 	<div>
												<c:choose>
												 	<c:when test="${!empty photoTag}">
												 		<c:forEach items="${photoTag}" var="item" varStatus="status">
												 			<div class="photoBox">
																<img src="/storage/fieldBook<c:url value='${item.FILENAME}'/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">	
												 				<span><c:out value="${item.TAG}"/></span>
												 			</div>
														</c:forEach>
												 	</c:when>
											 		<c:when test="${empty photoTag and !empty photo}">
											 			<c:forEach items="${photo}" var="item" varStatus="status">
											 				<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 200px; height: 200px; margin-right: 30px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();"> 
														</c:forEach>
											 		</c:when>
											 	</c:choose>
											</div>
										</td>
									</tr>
								</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<th colspan="6">피해시설 위치 및 현황이 없습니다.</th>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				</c:if>
				
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button" class="del-btn" onclick="javascript:fncDeleteAprCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
							</sec:authorize>
							<button type="button" class="modify-btn" onclick="javascript:fncUpdateAprComptView(); return false;"><spring:message code="title.update" /></button>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_APR','ROLE_USER_CNRS','ROLE_USER')"> --%>
<%-- 								<c:if test="${result.loginId eq userInfo.id or result.loginId eq userInfo.name or fn:contains(result.svyUser,userInfo.name)}"> --%>
									<c:if test="${access eq 'ADMIN' }">
										<button type="button" class="del-btn" onclick="javascript:fncDeleteAprCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
									</c:if>
									<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
										<button type="button" class="modify-btn" onclick="javascript:fncUpdateAprComptView(); return false;"><spring:message code="title.update" /></button>
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