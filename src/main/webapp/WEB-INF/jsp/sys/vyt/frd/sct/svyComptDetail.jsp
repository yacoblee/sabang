<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("crlf", "\r\n");
%>
<c:set var="pageTitle"><spring:message code="sysVytFrd.svyComptList.title" /></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3>
	<!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm"	action="${pageContext.request.contextPath}/sys/vyt/frd/sct/updateFrdSvyComptView.do" method="post">
			<input name="gid" type="hidden" value="">
			<input name="mstId" type="hidden" value="">
			<input name="svyLabel" type="hidden" value="">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>"/>
			<input name="schfrdType" type="hidden" value="<c:out value='${searchMap.schfrdType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchMap.schsvyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchMap.schsvyMonth}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchMap.schsvySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchMap.schsvySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchMap.schsvyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchMap.schsvyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchMap.schsvyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchMap.schsvyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchMap.schmstNm}'/>"/>
			<div class="BoardDetail">
				<table id="frdSvyComptTable" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tr>
						<th>조사ID</th>
						<td><c:out value="${svyComptResult.svyId}"/></td>
						<th>관할청</th>
						<td colspan="3"><c:out value="${svyComptResult.compentauth}"/> <c:out value="${svyComptResult.subcompentauth}"/></td>
					</tr>
					<tr>
						<th>소속</th>
						<td>한국치산기술협회</td>
						<th>조사자</th>
						<td><c:out value="${svyComptResult.svyUser}"/></td>
						<th>조사일</th>
						<td><c:out value="${svyComptResult.svyDt}"/></td>
					</tr>
					<tr>
						<th>소재지</th>
						<td colspan="3">
							<c:out value="${svyComptResult.svySd}"/> <c:out value="${svyComptResult.svySgg}"/> <c:out value="${svyComptResult.svyEmd}"/> <c:out value="${svyComptResult.svyRi}"/> <c:out value="${svyComptResult.svyJibun}"/>
						</td>
						<th>속칭</th>
						<td><c:out value="${svyComptResult.commonly}"/></td>
					</tr>
					<tr>
						<th>임도종류</th>
						<td colspan="2"><c:out value="${svyComptResult.frdType}"/></td>
<%-- 						<th>노선종류</th> --%>
<%-- 						<td><c:out value="${svyComptResult.routeType}"/></td> --%>
						<th>조사구분</th>
						<td colspan="2"><c:out value="${svyComptResult.frdRnk}"/></td>
					</tr>
					<tr>
						<th>예정거리(km)</th>
						<td colspan="2"><c:out value="${svyComptResult.schdst}"/>km</td>
						<th>임도연장(km)</th>
						<td colspan="2"><c:out value="${svyComptResult.frdExtns}"/>km</td>
					</tr>
					<tr>
						<th colspan="6">GPS좌표</th>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lat" /></th><!-- 시점위도 -->
						<td colspan="2"><c:out value="${svyComptResult.bpy}"/></td>
						<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lon" /></th><!-- 시점경도 -->
						<td colspan="2"><c:out value="${svyComptResult.bpx}"/></td>
					</tr>
					<tr>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />1</th><!-- 종점위도1 -->
						<td colspan="2"><c:out value="${svyComptResult.epy}"/></td>
						<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />1</th><!-- 종점경도1 -->
						<td colspan="2"><c:out value="${svyComptResult.epx}"/></td>
					</tr>
				</table>
				<div id="map-div" class="map-div on">
					<div class="ol-custom-control ol-basemap-control z-idx99" id="toggle-btn">
						<button type="button" class="btn-map-selector" title="일반지도" value="base">일반지도</button>
						<button type="button" class="btn-map-selector" title="항공지도" value="satellite">항공지도</button>
					</div>
					<div class="hybrid-check z-idx99">
						<input type="checkbox" value="hybrid" id="hybrid"><label for="hybrid">하이브리드</label>
					</div>
					<div id="map" class="map">
						<div class="frd-legend-div">
					        <div class="frdTitle">&lt;범 례&gt;</div>
					        <div class="frdLine" id="frdLine01">
					            <input type="checkbox" name="frdLne" value="editLayer01" checked="checked">
					            <img src="/images/main/frd_legend01.png">            
					        </div>
					        <div class="frdLine" id="frdLine02">
					            <input type="checkbox" name="frdLne" value="editLayer02" checked="checked">
					            <img src="/images/main/frd_legend02.png">            
					        </div>
					        <div class="frdLine" id="frdLine03">
					            <input type="checkbox" name="frdLne" value="editLayer03" checked="checked">
					            <img src="/images/main/frd_legend03.png">            
					        </div>
					        <div class="frdLine" id="frdLine04">
					            <input type="checkbox" name="frdLne" value="editLayer04" checked="checked">
					            <img src="/images/main/frd_legend04.png">            
					        </div>
					        <div class="frdLine" id="frdLine05">
					            <input type="checkbox" name="frdLne" value="editLayer05" checked="checked">
					            <img src="/images/main/frd_legend05.png">            
					        </div>
					    </div>
					</div>
				</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="process-btn" onclick="javascript:fnSldAnalPopup('<c:out value="${svyComptResult.svyId}"/>', '<c:out value="${svyComptResult.mstId}"/>'); return false;">대상지 분석</button>
					</div>
				</div>
				<br>
				<div class="mainMenu">□ 대상지 분석정보 &nbsp;<span id="sldShowTab" onclick="javascript:fnUnfoldTable('sld');">[열기]</span></div>
				<div id="sldTable" style="display: none;">
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<colgroup>
							<col style="width: 16.7%;">
							<col style="width: 8.7%;">
							<col style="width: 10.7%;">
							<col style="width: 16.7%;">
							<col style="width: 20.7%;">
							<col style="width: 26.7%;">
						</colgroup>
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<tbody>
							<tr>
								<th colspan="6">법적제한사항</th>
							</tr>
							<tr>
								<th>지목</th>
								<th>지번</th>
								<th>소유구분</th>
								<th colspan="2">소재지</th>
								<th></th>
							</tr>
							<c:forEach var="item" items="${parcelList}" varStatus="status">
								<tr class="center">
									<td><c:out value="${item.jimok}" /></td>
									<td><c:out value="${item.jibun}" /></td>
									<td><c:out value="${item.posesnSe}" /></td>
									<td colspan="2"><c:out value="${item.addr}" /> <c:out value="${item.jibun}" /></td>
									<td>
										<button type="button" class="search-btn ft-size13" onclick="fncLadUsePlanPopup('<c:out value="${item.pnuCode}"/>','<c:out value="${item.addr}"/>','<c:out value="${item.jibun}"/>','eum'); return false;">토지이음</button><button type="button" class="search-btn ft-size13" onclick="fncLadUsePlanPopup('<c:out value="${item.pnuCode}"/>'); return false;">SEE:REAL</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="mainMenu">□ 분석통계</div>
					<table>
						<tr>
							<th colspan="6">임황분석</th>
						</tr>
						<tr class="center">
							<th rowspan="2">임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;상</th>
							<th>침엽수림</th>
							<th>활엽수림</th>
							<th>혼효림</th>
							<th>죽림</th>
							<th>무립목지/비산림</th>
						</tr>
						<tr class="center">
							<c:set var="frtp" value="${statList.frtp}" scope="page" />
							<c:choose>
								<c:when test="${empty frtp['침엽수림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${frtp['침엽수림']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty frtp['활엽수림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${frtp['활엽수림']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty frtp['혼효림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${frtp['혼효림']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty frtp['죽림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${frtp['죽림']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty frtp['무립목지/비산림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${frtp['무립목지/비산림']}" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<colgroup>
							<col width="157.33px;">
							<col width="157.33px;">
							<col width="157.33px;">
							<col width="157.33px;">
							<col width="157.33px;">
							<col width="157.33px;">
						</colgroup>
						<tr class="center">
							<th rowspan="2">임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
							<th>인공림</th>
							<th>천연림</th>
							<th>무립목지/비산림</th>
							<th></th>
							<th></th>
						</tr>
						<tr class="center">
							<c:set var="fror" value="${statList.fror}" scope="page" />
							<c:choose>
								<c:when test="${empty fror['인공림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${fror['인공림']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty fror['천연림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${fror['천연림']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty fror['무립목지/비산림']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${fror['무립목지/비산림']}" /></td>
								</c:otherwise>
							</c:choose>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<colgroup>
							<col width="157.33px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
							<col width="87.4px;">
						</colgroup>
						<tr class="center">
							<th rowspan="2">영&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;급</th>
							<th>Ⅰ 영급</th>
							<th>Ⅱ 영급</th>
							<th>Ⅲ 영급</th>
							<th>Ⅳ 영급</th>
							<th>Ⅴ 영급</th>
							<th>Ⅵ 영급</th>
							<th>Ⅶ 영급</th>
							<th>Ⅷ 영급</th>
							<th>Ⅸ 영급</th>
						</tr>
						<tr class="center">
							<c:set var="agcls" value="${statList.agcls}" scope="page" />
							<c:choose>
								<c:when test="${empty agcls['1영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['1영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['2영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['2영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['3영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['3영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['4영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['4영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['5영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['5영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['6영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['6영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['7영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['7영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['8영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['8영급']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty agcls['9영급']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${agcls['9영급']}" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<tr class="center">
							<th rowspan="2">경&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;급</th>
							<th>치수</th>
							<th>소경목</th>
							<th>중경목</th>
							<th>대경목</th>
							<th></th>
						</tr>
						<tr class="center">
							<c:set var="dmcls" value="${statList.dmcls}" scope="page" />
							<c:choose>
								<c:when test="${empty dmcls['치수']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dmcls['치수']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty dmcls['소경목']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dmcls['소경목']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty dmcls['중경목']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dmcls['중경목']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty dmcls['대경목']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dmcls['대경목']}" /></td>
								</c:otherwise>
							</c:choose>
							<td></td>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<tr class="center">
							<th rowspan="2">밀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도</th>
							<th>소</th>
							<th>중</th>
							<th>밀</th>
							<th></th>
							<th></th>
						</tr>
						<tr class="center">
							<c:set var="dnst" value="${statList.dnst}" scope="page" />
							<c:choose>
								<c:when test="${empty dnst['소']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dnst['소']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty dnst['중']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dnst['중']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty dnst['밀']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${dnst['밀']}" /></td>
								</c:otherwise>
							</c:choose>
							<td></td>
							<td></td>
						</tr>
					</table>
					<div class="ovrflwx-auto">
						<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.koftr}">
									<colgroup>
										<c:forEach var="koftr" items="${statList.koftr}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
										<c:forEach var="koftr" items="${statList.koftr}">
											<c:choose>
												<c:when
													test="${koftr.key == '1' || koftr.key == '2' || koftr.key == '3' || koftr.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:otherwise>
													<th class="bt-none"><c:out value="${koftr.key}" /></th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr class="center">
								<c:choose>
									<c:when test="${not empty statList.koftr}">
										<c:forEach var="koftr" items="${statList.koftr}">
											<c:choose>
												<c:when
													test="${koftr.key == '1' || koftr.key == '2' || koftr.key == '3' || koftr.key == '4'}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td><c:out value="${koftr.value}" /></td>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<td class="bt-none">-</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</table>
					</div>
					<br>
					<table class="tb-fixed">
						<colgroup>
							<col width="157.33px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
						</colgroup>
						<tr>
							<th colspan="10">지황분석</th>
						</tr>
						<tr class="center">
							<th rowspan="2">경&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;사</th>
							<th>10° 미만</th>
							<th>10°~20°</th>
							<th>20°~30°</th>
							<th>30°~40°</th>
							<th>40°~50°</th>
							<th>50°~60°</th>
							<th>60°~70°</th>
							<th>70°~80°</th>
							<th>80°이상</th>
						</tr>
						<tr class="center">
							<c:set var="slope" value="${statList.slope}" scope="page" />
							<c:choose>
								<c:when test="${empty slope['10도 미만']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['10도 미만']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['10-20도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['10-20도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['20-30도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['20-30도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['30-40도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['30-40도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['40-50도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['40-50도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['50-60도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['50-60도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['60-70도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['60-70도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['70-80도']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['70-80도']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty slope['80도 이상']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${slope['80도 이상']}" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<colgroup>
							<col width="157.33px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
							<col width="87.407px;">
						</colgroup>
						<tr class="center">
							<th rowspan="2">향</th>
							<th>평탄지</th>
							<th>북</th>
							<th>북동</th>
							<th>동</th>
							<th>남동</th>
							<th>남</th>
							<th>남서</th>
							<th>서</th>
							<th>북서</th>
						</tr>
						<tr class="center">
							<c:set var="aspect" value="${statList.aspect}" scope="page" />
							<c:choose>
								<c:when test="${empty aspect['평지']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['평지']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['북']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['북']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['북동']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['북동']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['동']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['동']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['남동']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['남동']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['남']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['남']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['남서']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['남서']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['서']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['서']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty aspect['북서']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${aspect['북서']}" /></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					<div class="ovrflwx-auto">
						<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.dem}">
									<colgroup>
										<c:forEach var="dem" items="${statList.dem}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">표&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고</th>
										<c:forEach var="dem" items="${statList.dem}">
											<c:choose>
												<c:when
													test="${dem.key == '1' || dem.key == '2' || dem.key == '3' || dem.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:otherwise>
													<th class="bt-none"><c:out value="${dem.key}" /></th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr class="center">
								<c:choose>
									<c:when test="${not empty statList.dem}">
										<c:forEach var="dem" items="${statList.dem}">
											<c:choose>
												<c:when
													test="${dem.key == '1' || dem.key == '2' || dem.key == '3' || dem.key == '4'}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td><c:out value="${dem.value}" /></td>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<td class="bt-none">-</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</table>
					</div>
					<div class="ovrflwx-auto">
						<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.soil}">
									<colgroup>
										<c:forEach var="soil" items="${statList.soil}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
										<c:forEach var="soil" items="${statList.soil}">
											<c:choose>
												<c:when
													test="${soil.key == '1' || soil.key == '2' || soil.key == '3' || soil.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:otherwise>
													<th class="bt-none"><c:out value="${soil.key}" /></th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr class="center">
								<c:choose>
									<c:when test="${not empty statList.soil}">
										<c:forEach var="soil" items="${statList.soil}">
											<c:choose>
												<c:when
													test="${soil.key == '1' || soil.key == '2' || soil.key == '3' || soil.key == '4'}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td><c:out value="${soil.value}" /></td>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<td class="bt-none">-</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</table>
					</div>
					<div class="ovrflwx-auto">
						<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.geology}">
									<colgroup>
										<c:forEach var="geology" items="${statList.geology}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
										<c:forEach var="geology" items="${statList.geology}">
											<c:choose>
												<c:when test="${geology.key == '1' || geology.key == '2' || geology.key == '3' || geology.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:when test="${geology.key == '0'}">
													<th class="bt-none">수류지역</th>
												</c:when>
												<c:otherwise>
													<th class="bt-none"><c:out value="${geology.key}" /></th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
							<tr class="center">
								<c:choose>
									<c:when test="${not empty statList.geology}">
										<c:forEach var="geology" items="${statList.geology}">
											<c:choose>
												<c:when
													test="${geology.key == '1' || geology.key == '2' || geology.key == '3' || geology.key == '4'}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td><c:out value="${geology.value}" /></td>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<td class="bt-none">-</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</table>
					</div>
					<table class="tb-fixed statTable">
						<tr class="center">
							<th rowspan="2">모&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;암</th>
							<th>화성암</th>
							<th>퇴적암</th>
							<th>변성암</th>
							<th></th>
							<th></th>
						</tr>
						<tr class="center">
							<c:set var="prrck" value="${statList.prrck}" scope="page" />
							<c:choose>
								<c:when test="${empty prrck['화성암']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${prrck['화성암']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty prrck['퇴적암']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${prrck['퇴적암']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty prrck['변성암']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${prrck['변성암']}" /></td>
								</c:otherwise>
							</c:choose>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<tr class="center">
							<th rowspan="2">퇴&nbsp;적&nbsp;&nbsp;양&nbsp;식</th>
							<th>잔적토</th>
							<th>보행토(포행토)</th>
							<th>봉적토</th>
							<th></th>
							<th></th>
						</tr>
						<tr class="center">
							<c:set var="accma" value="${statList.accma}" scope="page" />
							<c:choose>
								<c:when test="${empty accma['잔적토']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${accma['잔적토']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty accma['보행토(포행토)']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${accma['보행토(포행토)']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty accma['봉적토']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${accma['봉적토']}" /></td>
								</c:otherwise>
							</c:choose>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table class="tb-fixed statTable">
						<tr class="center">
							<th rowspan="2">암&nbsp;석&nbsp;노&nbsp;출&nbsp;도</th>
							<th>10%이하</th>
							<th>10~30%</th>
							<th>30~50%</th>
							<th>50~75%</th>
							<th></th>
						</tr>
						<tr class="center">
							<c:set var="rock" value="${statList.rock}" scope="page" />
							<c:choose>
								<c:when test="${empty rock['10% 이하']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${rock['10% 이하']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty rock['10~30%']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${rock['10~30%']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty rock['30~50%']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${rock['30~50%']}" /></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty rock['50~75%']}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td><c:out value="${rock['50~75%']}" /></td>
								</c:otherwise>
							</c:choose>
							<td></td>
						</tr>
					</table>
					<br>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<tbody>
							<tr class="center">
								<td>
									<div class="photoWrap">
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												대상지 위치
												<c:if test="${analImg.destloc != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('01','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('01','jpg'); return false;"></button>
												</c:if>
											</div>
											<c:choose>
												<c:when test="${analImg.destloc != null}">
													<div class="photoUrl w450">
														<img src="/storage/<c:url value="${analImg.destloc }" />" alt="대상지 위치" onerror="this.remove ? this.remove() : this.removeNode();">
													</div>
													<input type="hidden" name="01" value="<c:out value="${gidList.destloc}"/>" />
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												행정구역
												<c:if test="${analImg.admin != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('02','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('02','jpg'); return false;"></button>
												</c:if>
											</div>
											<c:choose>
												<c:when test="${analImg.admin != null}">
													<div class="photoUrl w450">
														<img src="/storage/<c:url value="${analImg.admin }" />" alt="행정구역" onerror="this.remove ? this.remove() : this.removeNode();">
													</div>
													<input type="hidden" name="02" value="<c:out value="${gidList.admin}"/>" />
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												사업대상지 관계지적도
												<c:if test="${analImg.admin != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('03','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('03','jpg'); return false;"></button>
												</c:if>
											</div>
											<c:choose>
												<c:when test="${analImg.rllgstr != null}">
													<div class="photoUrl w450">
														<img src="/storage/<c:url value="${analImg.rllgstr }" />" alt="사업대상지 관계지적도" onerror="this.remove ? this.remove() : this.removeNode();">
													</div>
													<input type="hidden" name="03" value="<c:out value="${gidList.rllgstr}"/>" />
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												문화재보존관리지도
												<c:if test="${analImg.admin != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('04','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('04','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.culture != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.culture }" />" alt="문화재보존관리지도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="04" value="<c:out value="${gidList.culture}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="조사완료지의 내역에 대한 상세조회 내역을 출력합니다.">
						<caption class="Hidden">${pageTitle}
							<spring:message code="title.detail" />
						</caption>
						<colgroup>
							<col width="15%" />
							<col width=";" />
							<col width="15%" />
							<col width=";" />
							<col width="15%" />
							<col width=";" />
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">규제대상문화재</th>
							</tr>
							<tr class="center">
								<th>국가지정문화재</th>
								<td><c:choose>
										<c:when
											test="${not empty culCntMap.natlChoCul && culCntMap.natlChoCul ne '0'}">
											<c:out value="${culCntMap.natlChoCul}" />건</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose></td>
								<th>시도지정문화재</th>
								<td><c:choose>
										<c:when
											test="${not empty culCntMap.SidoChoCul && culCntMap.SidoChoCul ne '0'}">
											<c:out value="${culCntMap.SidoChoCul}" />건</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose></td>
								<th>등록문화재</th>
								<td><c:choose>
										<c:when
											test="${not empty culCntMap.natlRegCul && culCntMap.natlRegCul ne '0'}">
											<c:out value="${culCntMap.natlRegCul}" />건</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose></td>
							</tr>
							<c:forEach items="${culNmList}" var="culNmItem"
								varStatus="status">
								<tr class="center">
									<td colspan="6">${culNmItem}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="mainMenu">□ 대상지분석</div>
					<table
						summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<tbody>
							<tr class="center">
								<th colspan="6">임황분석</th>
							</tr>
							<tr class="center">
								<td>
									<div class="photoWrap">
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												임상분포도
												<c:if test="${analImg.frtp != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('05','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('05','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.frtp != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.frtp }" />" alt="임상분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="05" value="<c:out value="${gidList.frtp}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												임종분포도
												<c:if test="${analImg.fror != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('06','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('06','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.fror != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.fror }" />" alt="임종분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="06" value="<c:out value="${gidList.fror}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												영급분포도
												<c:if test="${analImg.agcls != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('07','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('07','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.agcls != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.agcls }" />" alt="영급분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="07" value="<c:out value="${gidList.agcls}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												경급분포도
												<c:if test="${analImg.dmcls != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('08','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('08','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.dmcls != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.dmcls }" />" alt="경급분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="08" value="<c:out value="${gidList.dmcls}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												밀도분포도
												<c:if test="${analImg.dnst != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('09','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('09','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.dnst != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.dnst }" />" alt="밀도분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="09" value="<c:out value="${gidList.dnst}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												수종분포도
												<c:if test="${analImg.koftr != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('10','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('10','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.koftr != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.koftr }" />" alt="수종분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="10" value="<c:out value="${gidList.koftr}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<tbody>
							<tr class="center">
								<th colspan="6">지황분석</th>
							</tr>
							<tr class="center">
								<td>
									<div class="photoWrap">
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												경사분포도
												<c:if test="${analImg.slope != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('11','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('11','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.slope != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.slope }" />" alt="경사분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="11" value="<c:out value="${gidList.slope}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												향분포도
												<c:if test="${analImg.aspect != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('12','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('12','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.aspect != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.aspect }" />" alt="향분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="12" value="<c:out value="${gidList.aspect}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												표고분포도
												<c:if test="${analImg.dem != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('13','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('13','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.dem != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.dem }" />" alt="표고분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="13" value="<c:out value="${gidList.dem}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												토성분포도
												<c:if test="${analImg.soil != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('14','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('14','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.soil != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.soil }" />" alt="토성분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="14" value="<c:out value="${gidList.soil}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												지질분포도
												<c:if test="${analImg.geology != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('15','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('15','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.geology != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.geology }" />" alt="지질분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="15" value="<c:out value="${gidList.geology}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												모암분포도
												<c:if test="${analImg.prrck != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('16','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn"title="이미지 저장" onclick="fnAnalDataDownload('16','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.prrck != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.prrck }" />" alt="모암분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="16" value="<c:out value="${gidList.prrck}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												퇴적양식분포도
												<c:if test="${analImg.accma != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('17','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('17','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.accma != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.accma }" />" alt="퇴적양식분포도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="17" value="<c:out value="${gidList.accma}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												암석노출도
												<c:if test="${analImg.rock != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('18','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('18','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.rock != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.rock }" />" alt="암석노출도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="18" value="<c:out value="${gidList.rock}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="mainMenu">□ 기타현황</div>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<tbody>
							<tr class="center">
								<td>
									<div class="photoWrap">
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												생태자연도분석
												<c:if test="${analImg.nature != null}"><button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('19','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('19','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.nature != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.nature }" />" alt="생태자연도분석" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="19" value="<c:out value="${gidList.nature}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">멸종위기 동&middot;식물 분석 현황</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.animal != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.animal }" />" alt="멸종위기 동식물 분석 현황" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">산불취약지도</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.mtfire != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.mtfire }" />" alt="산불취약지도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												산사태위험등급도
												<c:if test="${analImg.landslide != null}">
													<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('22','zip'); return false;"></button>
													<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('22','jpg'); return false;"></button>
												</c:if>
											</div>
											<div class="photoUrl w450">
												<c:choose>
													<c:when test="${analImg.landslide != null}">
														<div class="photoUrl w450">
															<img src="/storage/<c:url value="${analImg.landslide }" />" alt="산사태위험등급도" onerror="this.remove ? this.remove() : this.removeNode();">
														</div>
														<input type="hidden" name="22" value="<c:out value="${gidList.landslide}"/>" />
													</c:when>
													<c:otherwise>
														<div class="mt35pct">분석결과가 없습니다.</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="mainMenu">□ 분석통계</div>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col width="35%" />
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="4">임&nbsp;&nbsp;황&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr class="center">
								<th>임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;상</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.frorMaxNm}">
											<c:out value="${statList.frorMaxNm}" />
											<c:out value="${statList.frorMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>경&nbsp;&nbsp;&nbsp;급&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.dmclsMaxNm}">
											<c:out value="${statList.dmclsMaxNm}" />
											<c:out value="${statList.dmclsMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>밀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.dnstMaxNm}">
											<c:out value="${statList.dnstMaxNm}" />
											<c:out value="${statList.dnstMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>영&nbsp;&nbsp;&nbsp;급&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.agclsMaxNm}">
											<c:choose>
												<c:when test="${statList.agclsMaxNm eq '1영급'}">Ⅰ영급 </c:when>
												<c:when test="${statList.agclsMaxNm eq '2영급'}">Ⅱ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '3영급'}">Ⅲ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '4영급'}">Ⅳ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '5영급'}">Ⅴ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '6영급'}">Ⅵ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '7영급'}">Ⅶ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '8영급'}">Ⅷ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '9영급'}">Ⅸ영급</c:when>
											</c:choose>
											<c:out value="${statList.agclsMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%" />
							<col width="35%" />
							<col width="15%" />
							<col width="35%" />
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="4">지&nbsp;&nbsp;황&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr class="center">
								<th>평&nbsp;&nbsp;&nbsp;균&nbsp;&nbsp;&nbsp;경&nbsp;&nbsp;&nbsp;사</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.slopeMaxVal}">
											<fmt:formatNumber value="${statList.slopeMinVal}" pattern="0.00"></fmt:formatNumber>°~<fmt:formatNumber value="${statList.slopeMaxVal}" pattern="0.00"></fmt:formatNumber>° / 평균 <fmt:formatNumber value="${statList.slopeAvgVal}" pattern="0.00"></fmt:formatNumber>°
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
									</td>
								<th>토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.soilMaxNm}">
											<c:out value="${statList.soilMaxNm}" />
											<c:out value="${statList.soilMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>향&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.aspectMaxNm}">
											<c:out value="${statList.aspectMaxNm}" />
											<c:out value="${statList.aspectMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>퇴&nbsp;&nbsp;&nbsp;적&nbsp;&nbsp;&nbsp;양&nbsp;&nbsp;&nbsp;식</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.accmaMaxNm}">
											<c:out value="${statList.accmaMaxNm}" />
											<c:out value="${statList.accmaMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>표&nbsp;&nbsp;&nbsp;고&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.demMaxVal}">
											평균 <fmt:formatNumber value="${statList.demAvgVal}" pattern=".00"></fmt:formatNumber>m
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>모&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;암</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.prrckMaxNm}">
											<c:out value="${statList.prrckMaxNm}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
								<td>
									<c:choose>
										<c:when test="${statList.geologyCnt eq 1}">
											<c:out value="${statList.geologyMaxNm}" />
										</c:when>
										<c:when test="${statList.geologyCnt > 1}">
											<c:out value="${statList.geologyMaxNm}" /> 등
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>암&nbsp;석&nbsp;노&nbsp;출&nbsp;도</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.rockMaxNm}">
											<c:out value="${statList.rockMaxNm}" />
											<c:out value="${statList.rockMaxVal}" />
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%" />
							<col width=";" />
							<col width=";" />
							<col width=";" />
							<col width=";" />
							<col width=";" />
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">산사태취약지역 및 산사태위험등급도</th>
							</tr>
							<tr class="center">
								<th rowspan="2">산불취약지도</th>
								<th>A등급</th>
								<th>B등급</th>
								<th>C등급</th>
								<th>D등급</th>
								<th></th>
							</tr>
							<tr class="center">
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td></td>
							</tr>
							<tr class="center">
								<th rowspan="2">산사태위험도</th>
								<th>1등급</th>
								<th>2등급</th>
								<th>3등급</th>
								<th>4등급</th>
								<th>5등급</th>
							</tr>
							<tr class="center">
								<c:set var="landslide" value="${statList.landslide}" scope="page" />
								<c:choose>
									<c:when test="${empty landslide['1등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['1등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['2등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['2등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['3등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['3등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['4등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['4등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['5등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['5등급']}" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle}<spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%" />
							<col width=";" />
							<col width=";" />
							<col width=";" />
							<col width=";" />
							<col width=";" />
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">기&nbsp;&nbsp;타&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr>
								<th>생태자연도</th>
								<td colspan="5"><c:out value="${svyComptResult.ecoNtrMap}" /></td>
							</tr>
							<tr>
								<th>멸종위기종서식 현황</th>
								<td colspan="5"><c:out value="${svyComptResult.endSpc}" /></td>
							</tr>
							<tr>
								<th>특별산림보호종</th>
								<td colspan="5"><c:out value="${svyComptResult.spcFrsPrt}" /></td>
							</tr>
						</tbody>
					</table>
					<br>
				</div><!-- sld종료 div -->
				<div class="mainMenu">□ 대상지 조사정보 &nbsp;<span id="svyDetailShowTab" onclick="javascript:fnUnfoldTable('svyDetail');">[열기]</span></div>
				<div id="svyDetailTable" style="display: none;">
					<input type="hidden" name="frdLneCntPntWkt" value="<c:out value="${frdMap.frdLneCntPnt}"/>"/>
					<input type="hidden" name="frdExstnLneWkt" value="<c:out value="${frdMap.frdExstnLne}"/>"/>
					<input type="hidden" name="frdModLne" value="<c:out value="${frdMap.frdModLne}"/>"/>
					<input type="hidden" name="frdRvwLne1" value="<c:out value="${frdMap.frdRvwLne1}"/>"/>
					<input type="hidden" name="frdRvwLne2" value="<c:out value="${frdMap.frdRvwLne2}"/>"/>
					<input type="hidden" name="frdRvwLne3" value="<c:out value="${frdMap.frdRvwLne3}"/>"/>
						<c:if test="${safeFctList != null and safeFctList != '[]'}">
						<div class="mainMenu">1. 일반현황
							<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','20','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
						</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">보호시설</th>
								</tr>
								<tr class="center">
									<th>보호시설</th>
									<td><c:out value="${svyComptResult.safeFct}"/></td>
									<th>전답</th>
									<td><c:out value="${svyComptResult.field}"/></td>
									<th>접근성</th>
									<td><c:out value="${svyComptResult.acsbl}"/></td>
								</tr>
								<c:forEach var="item" items="${safeFctList}" varStatus="status">
									<tr>
										<th colspan="6">
											<c:out value="${status.count}"/>번 보호시설
											<c:set var="key" value="safeFct_${status.count}" />
											<c:if test="${pntGidList.containsKey(key)}">
												<input type="hidden" name="20" value="<c:out value="${pntGidList[key]}" />"/>
											</c:if>
										</th>
									</tr>
									<tr>
										<th>유형</th>
										<td class="center"><c:out value="${item.type}"/></td>
										<th>위도</th>
										<td><c:out value="${item.lat}"/></td>
										<th>경도</th>
										<td><c:out value="${item.lon}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${lonSlopeList != null and lonSlopeList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 종단기울기
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','21','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">종단기울기 전체 통계</th>
								</tr>
								<tr class="center">
									<th>최소(%)</th>
									<td><c:out value="${svyComptResult.lonSlopeMin}"/>%</td>
									<th>최대(%)</th>
									<td><c:out value="${svyComptResult.lonSlopeMax}"/>%</td>
									<th>평균(%)</th>
									<td><c:out value="${svyComptResult.lonSlopeAvg}"/>%</td>
								</tr>
								<c:forEach var="item" items="${lonSlopeList}" varStatus="status">
									<tr>
										<th colspan="6">
											<c:out value="${status.count}"/>번 종단기울기
											<c:set var="key" value="lonSlope_${status.count}" />
											<c:if test="${pntGidList.containsKey(key)}">
												<input type="hidden" name="21" value="<c:out value="${pntGidList[key]}" />"/>
											</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 종점기울기 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td><c:out value="${item.lon1}"/></td>
										<th rowspan="2">기울기(%)</th>
										<td rowspan="2" class="center"><c:out value="${item.slope}"/>%</td>
									</tr>
									<tr>
										<th>위도2</th>
										<td><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td><c:out value="${item.lon2}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${mtSlopeList != null and mtSlopeList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 산지경사
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','22','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">산지경사 전체 통계</th>
								</tr>
								<tr class="center">
									<th>최소(°)</th>
									<td><c:out value="${svyComptResult.mtSlopeMin}"/>°</td>
									<th>최대(°)</th>
									<td><c:out value="${svyComptResult.mtSlopeMax}"/>°</td>
									<th>평균(°)</th>
									<td><c:out value="${svyComptResult.mtSlopeAvg}"/>°</td>
								</tr>
								<c:forEach var="item" items="${mtSlopeList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 산지경사
										<c:set var="key" value="mtSlope_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="22" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 산지경사 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td><c:out value="${item.lon1}"/></td>
										<th rowspan="2">경사(°)</th>
										<td rowspan="2" class="center"><c:out value="${item.slope}"/>°</td>
									</tr>
									<tr>
										<th>위도2</th>
										<td><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td><c:out value="${item.lon2}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${rockExposList != null and rockExposList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 암반노출
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','23','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">암반노출 전체 통계</th>
								</tr>
								<tr class="center">
<%-- 									<th>최소(°)</th> --%>
<%-- 									<td><c:out value="${svyComptResult.rockExposMin}"/>°</td> --%>
<%-- 									<th>최대(°)</th> --%>
<%-- 									<td><c:out value="${svyComptResult.rockExposMax}"/>°</td> --%>
<%-- 									<th>평균(°)</th> --%>
<%-- 									<td><c:out value="${svyComptResult.rockExposAvg}"/>°</td> --%>
									<th colspan="3">암반 전체 노출(m)</th>
									<td colspan="3"><fmt:formatNumber value="${svyComptResult.rockExposSum}" pattern="0.00" />m</td>
								</tr>
								<c:forEach var="item" items="${rockExposList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 암반노출
											<c:set var="key" value="rockExpos_${status.count}" />
											<c:if test="${pntGidList.containsKey(key)}">
												<input type="hidden" name="23" value="<c:out value="${pntGidList[key]}" />"/>
											</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 암반노출 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td><c:out value="${item.lon1}"/></td>
										<th rowspan="2">암반노출(m)</th>
										<td rowspan="2" class="center">
											<c:set var="rockExposIsNumber">${item.rockExpos}</c:set>
											<c:out value="${item.rockExpos}"/><c:if test="${rockExposIsNumber.matches('[0-9]+')}">m</c:if>
										</td>
									</tr>
									<tr>
										<th>위도2</th>
										<td><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td><c:out value="${item.lon2}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${afrstList != null and afrstList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 조림지
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','24','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">조림지 전체 통계</th>
								</tr>
								<tr>
									<th colspan="3">유 / 무</th>
									<td colspan="3" class="center"><c:out value="${svyComptResult.afrstAt}"/></td>
								</tr>
								<c:forEach var="item" items="${afrstList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 조림지
										<c:set var="key" value="afrst_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="24" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null and item.photo == '[]'}">
										<tr>
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td colspan="2"><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td colspan="2"><c:out value="${item.lon2}"/></td>
									</tr>
									<tr>
										<th>조림지</th>
										<td colspan="5"><c:out value="${item.afrst}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${cuttingList != null and cuttingList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 벌채지
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','25','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">벌채지 전체 통계</th>
								</tr>
								<tr>
									<th colspan="3">유 / 무</th>
									<td colspan="3" class="center"><c:out value="${svyComptResult.cuttingAt}"/></td>
								</tr>
								<c:forEach var="item" items="${cuttingList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 벌채지
										<c:set var="key" value="cutting_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="25" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td colspan="2"><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td colspan="2"><c:out value="${item.lon2}"/></td>
									</tr>
									<tr>
										<th>벌채지</th>
										<td colspan="5"><c:out value="${item.cutting}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${stmiList != null and stmiList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 석력지
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','26','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">석력지 전체 통계</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3"><c:out value="${stmiTotalAt}"/></td>
								</tr>
								<c:forEach var="item" items="${stmiList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 석력지
										<c:set var="key" value="stmi_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="26" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td colspan="2"><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td colspan="2"><c:out value="${item.lon2}"/></td>
									</tr>
									<tr>
										<th>석력지</th>
										<td colspan="5"><c:out value="${item.stmi}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${mrngKindList != null and mrngKindList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 계류종류 및 현황
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','27','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">계류종류 및 현황 전체 통계</th>
								</tr>
								<tr class="center">
									<th>대계류</th>
									<td><c:out value="${mrngTotalMap.bigMrngTotal}"/></td>
									<th>중계류</th>
									<td><c:out value="${mrngTotalMap.middleMrngTotal}"/></td>
									<th>소계류</th>
									<td><c:out value="${mrngTotalMap.smallMrngTotal}"/></td>
								</tr>
								<c:forEach var="item" items="${mrngKindList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 계류종류 및 현황
										<c:set var="key" value="mrngKind_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="27" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>대분류</th>
										<td colspan="2"><c:out value="${item.bigMrng}"/></td>
										<th>소분류</th>
										<td colspan="2"><c:out value="${item.smallMrng}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${wetLandList != null and wetLandList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 습지
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','28','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">습지 전체 통계</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3"><c:out value="${svyComptResult.wetLandAt}"/></td>
								</tr>
								<c:forEach var="item" items="${wetLandList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 습지
										<c:set var="key" value="wetLand_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="28" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 조림지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td colspan="2"><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td colspan="2"><c:out value="${item.lon2}"/></td>
									</tr>
									<tr>
										<th>습지</th>
										<td colspan="5"><c:out value="${item.wetLand}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${cmtryList != null and cmtryList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 묘지
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','29','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">묘지 전체 통계</th>
								</tr>
								<tr>
									<th>묘지 개수</th>
									<td class="center"><c:out value="${cmtryTotalMap.cmtryCnt}"/></td>
									<th>묘지 위치</th>
									<td class="center"><c:out value="${cmtryTotalMap.cmtryLocTotal}"/></td>
									<th>묘지 관리</th>
									<td class="center"><c:out value="${cmtryTotalMap.cmtryMngTotal}"/></td>
								</tr>
								<c:forEach var="item" items="${cmtryList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 묘지
										<c:set var="key" value="cmtry_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="29" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 묘지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>묘지 위치</th>
										<td colspan="2"><c:out value="${item.cmtryLoc}"/></td>
										<th>묘지 관리</th>
										<td colspan="2"><c:out value="${item.cmtryMng}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${eltnWaterList != null and eltnWaterList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 용출수
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','30','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">용출수 전체 통계</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3"><c:out value="${svyComptResult.eltnWaterAt }"/></td>
								</tr>
								<c:forEach var="item" items="${eltnWaterList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 용출수
										<c:set var="key" value="eltnWater_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="30" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 용출수 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>용출수</th>
										<td colspan="5"><c:out value="${item.eltnWater}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${sofrtGrndList != null and sofrtGrndList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 연약지반
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','31','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">연약지반 전체 통계</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3"><c:out value="${svyComptResult.sofrtGrndAt }"/></td>
								</tr>
								<c:forEach var="item" items="${sofrtGrndList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 연약지반
										<c:set var="key" value="sofrtGrnd_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="31" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 연약지반 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td colspan="2"><c:out value="${item.lat1}"/></td>
										<th>경도1</th>
										<td colspan="2"><c:out value="${item.lon1}"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td colspan="2"><c:out value="${item.lat2}"/></td>
										<th>경도2</th>
										<td colspan="2"><c:out value="${item.lon2}"/></td>
									</tr>
									<tr>
										<th>연약지반</th>
										<td colspan="5"><c:out value="${item.sofrtGrnd}"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${clpsCnrnList != null and clpsCnrnList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 붕괴우려지역
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','32','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">붕괴우려지역 전체 통계</th>
								</tr>
								<tr class="center">
									<th>붕괴우려 사면</th>
									<td colspan="2"><c:out value="${clpsCnrnTotalMap.slopeTotal}"/></td>
									<th>붕괴우려 계류</th>
									<td colspan="2"><c:out value="${clpsCnrnTotalMap.mtTrntTotal}"/></td>
								</tr>
								<c:forEach var="item" items="${clpsCnrnList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 붕괴우려지역
										<c:set var="key" value="clpsCnrn_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="32" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 붕괴우려지역 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td><c:out value="${item.lat1 }"/></td>
										<th>경도1</th>
										<td><c:out value="${item.lon1 }"/></td>
										<th>붕괴우려 대분류</th>
										<td><c:out value="${item.clpsCnrnBig }"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td><c:out value="${item.lat2 }"/></td>
										<th>경도2</th>
										<td><c:out value="${item.lon2 }"/></td>
										<th>붕괴우려 소분류</th>
										<td><c:out value="${item.clpsCnrnSmall }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${maintreekndList != null and maintreekndList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 주요수종(<c:out value="${svyComptResult.maintreekndCnt }"/>건)
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','33','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<c:forEach var="item" items="${maintreekndList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 주요수종
										<c:set var="key" value="maintreeknd_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="33" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 주요수종 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1 }"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1 }"/></td>
									</tr>
									<tr>
										<th>주요수종</th>
										<td colspan="5"><c:out value="${item.maintreeknd }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${mainvegList != null and mainvegList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 주요식생(<c:out value="${svyComptResult.mainvegCnt }"/>건)
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','34','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<c:forEach var="item" items="${mainvegList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 주요식생
										<c:set var="key" value="mainveg_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="34" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 주요식생 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1 }"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1 }"/></td>
									</tr>
									<tr>
										<th>주요식생</th>
										<td colspan="5"><c:out value="${item.mainveg }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${wtrPltnList != null and wtrPltnList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 상수원 오염
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','35','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">상수원 오염 전체 통계</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3"><c:out value="${svyComptResult.wtrPltnAt }"/></td>
								</tr>
								<c:forEach var="item" items="${wtrPltnList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 상수원 오염
										<c:set var="key" value="wtrPltn_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="35" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 상수원 오염 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1 }"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1 }"/></td>
									</tr>
									<tr>
										<th>상수원 오염</th>
										<td colspan="5"><c:out value="${item.wtrPltn }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${dmgCncrnList != null and dmgCncrnList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 과다한 훼손우려지
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','36','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">과다한 훼손우려지 전체 통계</th>
								</tr>
								<tr class="center">
									<th colspan="3">유 / 무</th>
									<td colspan="3"><c:out value="${svyComptResult.dmgCncrnAt }"/></td>
								</tr>
								<c:forEach var="item" items="${dmgCncrnList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 과다한 훼손우려지
										<c:set var="key" value="dmgCncrn_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="36" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 훼손우려지 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td colspan="2"><c:out value="${item.lat1 }"/></td>
										<th>경도1</th>
										<td colspan="2"><c:out value="${item.lon1 }"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td colspan="2"><c:out value="${item.lat2 }"/></td>
										<th>경도2</th>
										<td colspan="2"><c:out value="${item.lon2 }"/></td>
									</tr>
									<tr>
										<th>훼손우려지</th>
										<td colspan="5"><c:out value="${item.dmgCncrn }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${frstDsstrList != null and frstDsstrList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 산불 / 병해충 (<c:out value="${svyComptResult.frstDsstrCnt }"/>)건
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','37','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">산불 / 병해충 전체 통계</th>
								</tr>
								<tr class="center">
									<th>산불(건)</th>
									<td><c:out value="${dmgTotalMap.frstFireCnt }"/>건</td>
									<th>병해충(건)</th>
									<td><c:out value="${dmgTotalMap.pestCnt }"/>건</td>
									<th>기타(건)</th>
									<td><c:out value="${dmgTotalMap.etcCnt }"/>건</td>
								</tr>
								<c:forEach var="item" items="${frstDsstrList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 산불 / 병해충
										<c:set var="key" value="frstDsstr_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="37" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 산불/병해충 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도1</th>
										<td><c:out value="${item.lat1 }"/></td>
										<th>경도1</th>
										<td><c:out value="${item.lon1 }"/></td>
										<th rowspan="2">재해유형</th>
										<td rowspan="2"><c:out value="${item.dmgType }"/></td>
									</tr>
									<tr>
										<th>위도2</th>
										<td><c:out value="${item.lat2 }"/></td>
										<th>경도2</th>
										<td><c:out value="${item.lon1 }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${wildAnmlList != null and wildAnmlList != '[]'}">
							<div class="mainMenu">2. 산림 현장현황 > 야생동물
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','38','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
									<tr>
										<th colspan="6">야생동물 전체 통계</th>
									</tr>
									<tr>
										<th>전체(건)</th>
										<td colspan="2" class="center"><c:out value="${svyComptResult.wildAnmlCnt }"/>건</td>
										<th>종류</th>
										<td colspan="2"><c:out value="${svyComptResult.wildAnmlKind }"/></td>
									</tr>
									<c:forEach var="item" items="${wildAnmlList}" varStatus="status">
										<tr>
											<th colspan="6"><c:out value="${status.count}"/>번 야생동물
											<c:set var="key" value="wildAnml_${status.count}" />
											<c:if test="${pntGidList.containsKey(key)}">
												<input type="hidden" name="38" value="<c:out value="${pntGidList[key]}" />"/>
											</c:if>
											</th>
										</tr>
										<c:if test="${item.photo != null and item.photo != '[]'}">
											<tr class="photoTr">
												<td colspan="6">
													<div>
													 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
													 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 야생동물 사진" onerror="this.remove ? this.remove() : this.removeNode();">
														</c:forEach>
													</div>
												</td>
											</tr>
										</c:if>
										<c:if test="${item.photo == null or item.photo == '[]'}">
											<tr class="center">
												<td colspan="6">등록된 사진이 없습니다.</td>
											</tr>
										</c:if>
										<tr>
											<th>위도</th>
											<td colspan="2"><c:out value="${item.lat1 }"/></td>
											<th>경도</th>
											<td colspan="2"><c:out value="${item.lon1 }"/></td>
										</tr>
										<tr>
											<th>유형</th>
											<td colspan="2"><c:out value="${item.anmlAt }"/></td>
											<th>종류</th>
											<td colspan="2"><c:out value="${item.anmlType }"/></td>
										</tr>
									</c:forEach>
								</table>
							</c:if>
						<c:if test="${ecnrFcltyInstlList != null and ecnrFcltyInstlList != '[]'}">
							<div class="mainMenu">5. 사방시설 사업종 > 사방시설 설치 여부
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','39','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">사방시설 설치 여부 전체 통계</th>
								</tr>
								<tr>
									<th>전체(건)</th>
									<td colspan="2" class="center"><c:out value="${svyComptResult.ecnrFcltyInstlCnt }"/>건</td>
									<th>종류</th>
									<td colspan="2"><c:out value="${svyComptResult.ecnrFcltyInstlType }"/></td>
								</tr>
								<c:forEach var="item" items="${ecnrFcltyInstlList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 사방시설 설치 여부
										<c:set var="key" value="ecnrFcltyInstl_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="39" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 사방시설 설치 여부 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1 }"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1 }"/></td>
									</tr>
									<tr>
										<th>종류</th>
										<td colspan="5"><c:out value="${item.ecnrFcltyInstlType }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${ecnrFcltyNcstyList != null and ecnrFcltyNcstyList != '[]'}">
							<div class="mainMenu">5. 사방시설 사업종 > 사방시설 필요 여부
								<button class="download-btn btn_right" title="Point SHP다운로드" onclick="fnDownloadAnalPnt('<c:out value="${svyComptResult.gid}"/>','40','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;">SHP 다운로드</button>
							</div>
							<table>
								<colgroup>
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
									<col width="16.6%;">
								</colgroup>
								<tr>
									<th colspan="6">사방시설 필요 여부 전체 통계</th>
								</tr>
								<tr>
									<th>전체(건)</th>
									<td colspan="2" class="center"><c:out value="${svyComptResult.ecnrFcltyNcstyCnt }"/>건</td>
									<th>종류</th>
									<td colspan="2"><c:out value="${svyComptResult.ecnrFcltyNcstyType }"/></td>
								</tr>
								<c:forEach var="item" items="${ecnrFcltyNcstyList}" varStatus="status">
									<tr>
										<th colspan="6"><c:out value="${status.count}"/>번 사방시설 필요 여부
										<c:set var="key" value="ecnrFcltyNcsty_${status.count}" />
										<c:if test="${pntGidList.containsKey(key)}">
											<input type="hidden" name="40" value="<c:out value="${pntGidList[key]}" />"/>
										</c:if>
										</th>
									</tr>
									<c:if test="${item.photo != null and item.photo != '[]'}">
										<tr class="photoTr">
											<td colspan="6">
												<div>
												 	<c:forEach items="${item.photo}" var="photo" varStatus="status">
												 		<img src="/storage/fieldBook/<c:url value='${photo}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 사방시설 필요 여부 사진" onerror="this.remove ? this.remove() : this.removeNode();">
													</c:forEach>
												</div>
											</td>
										</tr>
									</c:if>
									<c:if test="${item.photo == null or item.photo == '[]'}">
										<tr class="center">
											<td colspan="6">등록된 사진이 없습니다.</td>
										</tr>
									</c:if>
									<tr>
										<th>위도</th>
										<td colspan="2"><c:out value="${item.lat1 }"/></td>
										<th>경도</th>
										<td colspan="2"><c:out value="${item.lon1 }"/></td>
									</tr>
									<tr>
										<th>종류</th>
										<td colspan="5"><c:out value="${item.ecnrFcltyNcstyType }"/></td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<div class="mainMenu">6. 조사자 의견 및 특이사항</div>
						<table>
							<colgroup>
								<col width="20%;">
								<col width="80%;">
							</colgroup>
							<tr>
								<th>일반현황</th>
								<td><c:out value="${svyComptResult.gnrlStts }"/></td>
							</tr>
							<tr>
								<th>종단기울기</th>
								<td><c:out value="${svyComptResult.lonSlope }"/></td>
							</tr>
							<tr>
								<th>산지경사</th>
								<td><c:out value="${svyComptResult.mtSlope }"/></td>
							</tr>
							<tr>
								<th>사면현황</th>
								<td><c:out value="${svyComptResult.slopeStatus }"/></td>
							</tr>
							<tr>
								<th>대상지 내 암반</th>
								<td><c:out value="${svyComptResult.destRock }"/></td>
							</tr>
							<tr>
								<th>대상지 내 사면침식현황</th>
								<td><c:out value="${svyComptResult.destErsn }"/></td>
							</tr>
							<tr>
								<th>대상지 계류현황</th>
								<td><c:out value="${svyComptResult.destMrngStat }"/></td>
							</tr>
							<tr>
								<th>계류 내 침식현황</th>
								<td><c:out value="${svyComptResult.mrngErsnStat }"/></td>
							</tr>
							<tr>
								<th>기타(용출수, 묘지 등)</th>
								<td><c:out value="${svyComptResult.etc }"/></td>
							</tr>
						</table>
					</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="download-btn" onclick="javascript:fnExcelDown('<c:out value="${svyComptResult.gid}"/>'); return false;">엑셀 다운로드</button>
						<button type="button" class="del-btn" onclick="javascript:fncDeleteSvyCompt('<c:out value="${svyComptResult.gid}"/>','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView('<c:out value="${svyComptResult.gid}"/>','<c:out value="${svyComptResult.mstId}"/>','<c:out value="${svyComptResult.svyId}"/>'); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>