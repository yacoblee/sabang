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
		<li><a href="#">취약지역실태조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/bsc/sct/updateBscSvyComptView.do" method="post">
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			<input name="test" type="hidden" value="<c:out value="${photo_result}" />">
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
			<input name="schjdgmntgrad" type="hidden" value="<c:out value='${searchVO.jdgmntgrad}'/>">
			<div class="BoardDetail WkaBoardDetail">
				<div class="mainMenu">□ 일반현황</div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tbody>
					    <tr>
					        <th class="subMenu" rowspan="2">조사자</th>
					        <th>소속</th>
					        <td colspan="2"><c:out value="${result.svydept}"/></td>
					        <th>직급</th>
					        <td><c:out value="${result.syvofcps}"/></td>
					        <th>조사자</th>
					        <td colspan="2"><c:out value="${result.svyUser}"/></td>
					    </tr>
					    <tr>
					        <th>조사일자</th>
					        <td colspan="2"><c:out value="${result.svydt}"/></td>
					        <th>연락처</th>
					        <td colspan="4"><c:out value="${result.svymbtl}"/></td>
					    </tr>
					    <tr>
					        <th class="subMenu" rowspan="4">위치 및 환경</th>
					        <th>행정구역</th>
					        <td colspan="7">
					        	<c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/> <c:out value="${result.svyRi}"/> <c:out value="${result.svyJibun}"/>
				        	</td>
					    </tr>
					    <tr>
					        <th>관리주체</th>
					        <td colspan="7"><c:out value="${result.svyRegion1}"/> <c:out value="${result.svyRegion2}"/></td>
					    </tr>
					    <tr>
					        <th>GPS좌표 (유출구)</th>
					        <th>위도</th>
					        <td colspan="2"><c:out value="${result.lat}"/></td>
					        <th>경도</th>
					        <td colspan="3"><c:out value="${result.lon}"/></td>
					    </tr>
					    <tr>
					        <th>전자야장 좌표</th>
					        <th>위도</th>
					        <td colspan="2" <c:if test="${result.lat ne result.px }">class="tempA"</c:if>><c:out value="${result.px}"/></td>
					        <th>경도</th>
					        <td colspan="3" <c:if test="${result.lon ne result.py }">class="tempA"</c:if>><c:out value="${result.py}"/></td>
					    </tr>
					    <tr>
					        <th class="subMenu">조사지 환경</th>
					        <th>유역면적</th>
					        <td colspan="2"><c:out value="${result.dgrarea}"/>ha</td>
					        <th colspan="2">취약지역 면적</th>
					        <td colspan="3"><c:out value="${result.frgltyrenarea}"/>㎡</td>
					    </tr>
					    <tr>
					        <th class="subMenu" rowspan="2">보호 대상</th>
					        <th>보호시설</th>
					        <td colspan="2"><c:out value="${result.safefct}"/>(<c:out value="${result.placelen}"/>개소)</td>
					        <th colspan="2">인가 또는 호수</th>
					        <td colspan="3"><c:out value="${result.house}"/>(<c:out value="${result.lake}"/>개소)</td>
					    </tr>
					    <tr>
					        <th>상부 주요시설</th>
					        <td><c:out value="${result.highmainfct}"/></td>
					        <th style="line-height: 25px;">상부 인가현황</th>
					        <td><c:out value="${result.highhousesttus}"/>개소</td>
					        <th style="line-height: 25px;">하부 주요시설</th>
					        <td><c:out value="${result.lowmainfct}"/></td>
					        <th style="line-height: 25px;">하부 인가현황</th>
					        <td><c:out value="${result.lowhousesttus}"/>개소</td>
					    </tr>
				  	</tbody>
		  		</table>
		  		<c:if test="${result.svyType eq '취약지역 실태조사(토석류)' }">
		  			<div class="mainMenu">□ 우려지역 계류현황</div>
					<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					    <tbody>
					        <tr>
					            <th class="subMenu" rowspan="5" style="writing-mode: vertical-lr;">기본정보</th>
					            <th colspan="2">황폐발생원</th>
					            <td colspan="4">산사태위험지 <c:out value="${result.scodsltn}"/> 유역</td>
					            <th colspan="2">계류경사</th>
					            <td colspan="4"><c:out value="${result.mntntrntavg}"/>° / <c:out value="${result.mntntrntmin}"/>~<c:out value="${result.mntntrntmax}"/>°</td>
					        </tr>
					        <tr>
					            <th colspan="2">계류길이</th>
					            <td colspan="2"><c:out value="${result.mntntrntlen}"/>m</td>
					            <th colspan="2">변곡점 길이</th>
					            <td colspan="2"><c:out value="${result.iftnpntlen}"/>m</td>
					            <th colspan="2">변곡점 고도</th>
					            <td colspan="2"><c:out value="${result.iftnpntevtn}"/>m</td>
					        </tr>
					        <tr>
					           <th colspan="2">임상</th>
						        <td colspan="2"><c:out value="${result.frtp}"/></td>
						        <th colspan="2">임분밀도</th>
						        <td colspan="2"><c:out value="${result.stddnst}"/></td>
						        <th colspan="2">임분경급</th>
						        <td colspan="2"><c:out value="${result.stddmcls}"/></td>
					        </tr>
					        <tr>
					            <th colspan="2">종점부 특이사항</th>
					            <td colspan="10"><c:if test="${result.epntpartclr != null}">∘ <c:out value="${result.epntpartclr}"/></c:if></td>
					        </tr>
					        <tr>
					            <th colspan="2">시점부 특이사항</th>
					            <td colspan="10"><c:if test="${result.spntpartclr != null}">∘ <c:out value="${result.spntpartclr}"/></c:if></td>
					        </tr>
					        <tr>
					            <th class="subMenu" rowspan="2" style="writing-mode: vertical-lr;">상태정보</th>
					            <th colspan="2">월류상태</th>
					            <td><c:out value="${result.ovrflwsttus}"/></td>
					            <th colspan="2">계류상황</th>
					            <td><c:out value="${result.mntntrntsittn}"/></td>
					            <th colspan="2">곡류상태</th>
					            <td><c:out value="${result.mdgfwsttus}"/></td>
					            <th colspan="2">계류수</th>
					            <td><c:out value="${result.mntntrntcnt}"/></td>
					        </tr>
					        <tr>
					            <th colspan="2">유목길이</th>
					            <td><c:out value="${result.lwdlen}"/>m</td>
					            <th colspan="2">퇴적지</th>
					            <td><c:out value="${result.sdtytopo}"/></td>
					            <th colspan="2">좌안붕괴지</th>
					            <td><c:out value="${result.lslmplnd}"/>개</td>
					            <th colspan="2">우안붕괴지</th>
					            <td><c:out value="${result.rslmplnd}"/>개</td>
					        </tr>
					        <tr>
					            <th class="subMenu" rowspan="9" style="writing-mode: vertical-lr;">전석분포비율</th>
					            <th rowspan="3">최단부</th>
					            <th>위도</th>
					            <td colspan="4"><c:out value="${result.shrtlat}"/></td>
					            <th>경도</th>
					            <td colspan="5"><c:out value="${result.shrtlon}"/></td>
					        </tr>
					        <tr>
					            <th>위치</th>
					            <td><c:out value="${result.shrtlc}"/>m</td>
					            <th>고도</th>
					            <td><c:out value="${result.shrtelev}"/>m</td>
					            <th>토심</t>
					            <td><c:out value="${result.shrtsoildep}"/>cm</td>
					            <th>폭</th>
					            <td><c:out value="${result.shrtdm}"/>m</td>
					            <th>경사</th>
					            <td colspan="2"><c:out value="${result.shrtslp}"/>°</td>
					        </tr>
					        <tr>
					            <th>암반</th>
					            <td><c:out value="${result.shrtrock}"/>%</td>
					            <th>전석</th>
					            <td><c:out value="${result.shrtbldrstne}"/>%</td>
					            <th>자갈</th>
					            <td><c:out value="${result.shrtgravel}"/>%</td>
					            <th>모래</th>
					            <td><c:out value="${result.shrtsand}"/>%</td>
					            <th>기타</th>
					            <td colspan="2"><c:out value="${result.shrtetc}"/>%</td>
					        </tr>
					        <tr>
					            <th rowspan="3">중간부</th>
					            <th>위도</th>
					            <td colspan="4"><c:out value="${result.mdllat}"/></td>
					            <th>경도</th>
					            <td colspan="5"><c:out value="${result.mdllon}"/></td>
					        </tr>
					        <tr>
					            <th>위치</th>
					            <td><c:out value="${result.mdllc}"/>m</td>
					            <th>고도</th>
					            <td><c:out value="${result.mdlelev}"/>m</td>
					            <th>토심</th>
					            <td><c:out value="${result.mdlsoildep}"/>cm</td>
					            <th>폭</th>
					            <td><c:out value="${result.mdldm}"/>m</td>
					            <th>경사</th>
					            <td colspan="2"><c:out value="${result.mdlslp}"/>°</td>
					        </tr>
					        <tr>
					            <th>암반</th>
					            <td><c:out value="${result.mdlrock}"/>%</td>
					            <th>전석</th>
					            <td><c:out value="${result.mdlbldrstne}"/>%</td>
					            <th>자갈</th>
					            <td><c:out value="${result.mdlgravel}"/>%</td>
					            <th>모래</th>
					            <td><c:out value="${result.mdlsand}"/>%</td>
					            <th>기타</th>
					            <td colspan="2"><c:out value="${result.mdletc}"/>%</td>
					        </tr>
					        <tr>
					            <th rowspan="3">최상부</th>
					            <th>위도</th>
					            <td colspan="4"><c:out value="${result.uplat}"/></td>
					            <th>경도</th>
					            <td colspan="5"><c:out value="${result.uplon}"/></td>
					        </tr>
					        <tr>
					            <th>위치</th>
					            <td><c:out value="${result.uplc}"/>m</td>
					            <th>고도</th>
					            <td><c:out value="${result.upelev}"/>m</td>
					            <th>토심</th>
					            <td><c:out value="${result.upsoildep}"/>cm</td>
					            <th>폭</th>
					            <td><c:out value="${result.updm}"/>m</td>
					            <th>경사</th>
					            <td colspan="2"><c:out value="${result.upslp}"/>°</td>
					        </tr>
					        <tr>
					            <th>암반</th>
					            <td><c:out value="${result.uprock}"/>%</td>
					            <th>전석</th>
					            <td><c:out value="${result.upbldrstne}"/>%</td>
					            <th>자갈</th>
					            <td><c:out value="${result.upgravel}"/>%</td>
					            <th>모래</th>
					            <td><c:out value="${result.upsand}"/>%</td>
					            <th>기타</th>
					            <td colspan="2"><c:out value="${result.upetc}"/>%</td>
					        </tr>
					    </tbody>
					</table>
					<div class="mainMenu">□ 토석류 시뮬레이션 평가표</div>
			    	<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			    		<tbody>
			    			 <tr>
					            <th colspan="2">토석류 확산 영향범위</th>
					            <td colspan="5">
		                          	<c:if test="${result.stabanalscore eq '0'}">토석류 확산 영향범위 내 대상 없음</c:if>
		                          	<c:if test="${result.stabanalscore eq '15'}">토석류 확산 영향범위 내 피해우려 대상 존재</c:if>
		                          	<c:if test="${result.stabanalscore eq '30'}">토석류 확산 영향범위 내 보호시설 등 인명피해 우려대상 존재</c:if>
					            </td>
					            <td class="stabanalscore">
					            	<c:out value="${result.stabanalscore}"/>
					            </td>
					        </tr>
					        <tr>
			    				<th colspan="2">피해대상 개소</th>
			    				<td colspan="2"><c:out value="${result.placelen}"/></td>
			    				<th colspan="2">이격거리</th>
			    				<td colspan="2"><c:out value="${result.sepdist}"/></td>
			    			</tr>
			    			<tr>
			    				<th colspan="2">1회 토석류량</th>
			    				<th colspan="2">정지조건</th>
			    				<th colspan="2">가중치</th>
			    				<th colspan="2">전체 토석류량</th>			    				
			    			</tr>
			    			<tr>
			    				<td colspan="2"><c:out value="${result.onedebriswt}"/></td>
			    				<td colspan="2"><c:out value="${result.stopcnd}"/></td>
			    				<td colspan="2"><c:out value="${result.wt}"/></td>
			    				<td colspan="2"><c:out value="${result.totdebriswt}"/></td>			    				
			    			</tr>
			    		</tbody>
			    	</table>
			    	<!-- 해석 결과 도면 -->			
					<div class="mainMenu">□ 토석류 시뮬레이션 해석 결과 도면</div>					
					<c:choose>
						<c:when test="${stabanal_img_result != null }">
							<div class="photoWrap">
								<c:forEach items="${stabanal_img_result}" var="item" varStatus="status">
									<div class="photoBox" style="width: 50%;">
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${item}'/>" alt="토석류 시뮬레이션 해석결과 도면사진" onerror="this.remove ? this.remove() : this.removeNode();">	
										</div>
									</div>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">시뮬레이션 도면 정보 없음</div>
						</c:otherwise>
					</c:choose>
		  		</c:if>
		  		<c:if test="${result.svyType eq '취약지역 실태조사(산사태)' }">
				  	<div class="mainMenu">□ 위험사면 현황</div>
					<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<tbody>
						    <tr>
						        <th class="subMenu" rowspan="3">사면 현황</th>
						        <th colspan="2">사면상태</th>
						        <td colspan="2"><c:out value="${result.dirsttus}"/></td>
						        <th colspan="2">모암</th>
						        <td colspan="2"><c:out value="${result.prntrock}"/></td>
						        <th colspan="2">토성</th>
						        <td colspan="2"><c:out value="${result.soilchar}"/></td>
						    </tr>
						    <tr>
						        <th colspan="2">경사길이</th>
						        <td colspan="2"><c:out value="${result.slopelen}"/>m</td>
						        <th colspan="2">경사도</th>
						        <td class="lndsld" colspan="2"><c:out value="${result.slope}"/>° / <c:out value="${result.slopemin}"/>~<c:out value="${result.slopemax}"/>°</td>
						        <th colspan="2">경사위치</th>
						        <td class="lndsld" colspan="2"><c:out value="${result.ridge}"/>(<c:out value="${result.slopelc}"/>/10)</td>
						    </tr>
						    <tr>
						        <th colspan="2">최고지점</th>
						        <td colspan="2"><c:out value="${result.pntmax}"/>m</td>
						        <th colspan="2">최저지점</th>
						        <td colspan="2"><c:out value="${result.pntmin}"/>m</td>
						        <th colspan="2">사면형</th>
						        <td colspan="2"><c:out value="${result.dirfrm}"/></td>
						    </tr>
						    <tr>
						        <th class="subMenu" rowspan="2">토질 현황</th>
						        <th colspan="2">토심</th>
						        <td colspan="2"><c:out value="${result.soildep}"/>m</td>
						        <th colspan="2">균열</th>
						        <td colspan="2"><c:out value="${result.crck}"/></td>
						        <th colspan="2">함몰</th>
						        <td colspan="2"><c:out value="${result.sink}"/></td>
						    </tr>
						    <tr>
						        <th colspan="2">융기</th>
						        <td colspan="2"><c:out value="${result.uplift}"/></td>
						        <th colspan="2">말단부압출</th>
						        <td colspan="2"><c:out value="${result.extdistalend}"/></td>
						        <th colspan="2">확대예상</th>
						        <td colspan="2"><c:out value="${result.expandpredic}"/></td>
						    </tr>
						    <tr>
						        <th class="subMenu">임상 현황</th>
						        <th colspan="2">임상</th>
						        <td colspan="2"><c:out value="${result.frtp}"/></td>						        
						        <th colspan="2">임분밀도</th>
						        <td colspan="2"><c:out value="${result.stddnst}"/></td>
						        <th colspan="2">임분경급</th>
						        <td colspan="2"><c:out value="${result.stddmcls}"/></td>						        
						    </tr>
						    <tr>
						        <th class="subMenu">용수 현황</th>
						        <th colspan="2">용수상시 여부</th>
						        <td><c:out value="${result.uswtrordtmat}"/></td>
						        <th colspan="2">강우시 용수</th>
						        <td><c:out value="${result.rainfallwater}"/></td>
						        <th colspan="2">사면이 축축함</th>
						        <td><c:out value="${result.dirwet}"/></td>
						        <th colspan="2">사면이 건조함</th>
						        <td><c:out value="${result.dirdry}"/></td>
						    </tr>
					    </tbody>
			    	</table>
			    	<div class="mainMenu">□ 사면 안정해석 평가표</div>
			    	<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			    		<tbody>
			    			<tr>
			    				<th colspan="2">건기</th>
			    				<td colspan="2"><c:out value="${result.dryseason}"/></td>
			    				<th colspan="2">우기</th>
			    				<td colspan="2"><c:out value="${result.rainseason}"/></td>
			    			</tr>
			    			<tr>
					            <th colspan="2">기준안전율</th>
					            <td colspan="5">
		                          	<c:if test="${result.stabanalscore eq '0'}">기준안전율 충족 건기시(1.6이상) 우기시 (1.3이상)</c:if>
		                          	<c:if test="${result.stabanalscore eq '15'}">기준안전율 수준 건기시(1.5~1.6미만) 우기시 (1.2~1.3미만)</c:if>
		                          	<c:if test="${result.stabanalscore eq '30'}">기준안전율 불충족 건기시(1.5미만) 우기시 (1.2미만)</c:if>
					            </td>
					            <td class="stabanalscore">
					            	<c:out value="${result.stabanalscore}"/>
					            </td>
					        </tr>
					        <tr>
			    				<th colspan="2">피해대상 개소</th>
			    				<td colspan="2"><c:out value="${result.placelen}"/></td>
			    				<th colspan="2">이격거리</th>
			    				<td colspan="2"><c:out value="${result.sepdist}"/></td>
			    			</tr>
			    			<tr>
			    				<th colspan="3">습윤단위중량(kN/㎥)</th>
			    				<th colspan="2">점착력(kPa)</th>
			    				<th colspan="3">내부마찰각(˚)</th>
			    			</tr>
			    			<tr>
			    				<td colspan="3"><c:out value="${result.wetunitwt}"/></td>
			    				<td colspan="2"><c:out value="${result.adheforce}"/></td>
			    				<td colspan="3"><c:out value="${result.infricangle}"/></td>
			    			</tr>
			    		</tbody>
			    	</table>
			    	<!-- 산사태 안정성 검토 결과 -->			
					<div class="mainMenu">□ 산사태 안정성 검토 결과</div>					
					<c:choose>
						<c:when test="${stabanal_img_result != null }">
							<div class="photoWrap" <c:if test="${fn:contains(stabanal_img_result[0],'rain') }">style="display: flex; flex-direction: row-reverse;"</c:if>>
								<c:forEach items="${stabanal_img_result}" var="item" varStatus="status" begin="0" end="1">
									<div class="photoBox" style="width: 50%;">
										<c:if test="${fn:contains(item,'dry') }">
											<div class="photoUrl">
												<img src="/storage/fieldBook<c:url value='${item}'/>" alt="건기 시 안전율 사진" onerror="this.remove ? this.remove() : this.removeNode();">	
											</div>
										</c:if>
										<c:if test="${fn:contains(item,'rain') }">
											<div class="photoUrl">
												<img src="/storage/fieldBook<c:url value='${item}'/>" alt="우기 시 안전율 사진" onerror="this.remove ? this.remove() : this.removeNode();">	
											</div>
										</c:if>
									</div>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">안정성 검토 결과 정보 없음</div>
						</c:otherwise>
					</c:choose>
			    </c:if>
			    <div class="mainMenu">□ 최종 판정등급</div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tbody>	
					    <tr>
					        <th class="subMenu" rowspan="3">판정 결과</th>
					        <th>현장조사점수</th>
					        <td colspan="3"><c:out value="${result.fieldsurveyscore}"/></td>
					        <th colspan="6" rowspan="2">점수 계</th>
					        <td colspan="8" rowspan="2"><c:out value="${result.scoresum}"/></td>
					        <th colspan="7" rowspan="2">판정등급</th>
					        <td colspan="5" rowspan="2">
					        	<c:if test="${result.jdgmntgrad != null}">					        
						        	<c:choose>
						        		<c:when test="${result.jdgmntgrad eq 'A등급' }"> <c:out value="${result.jdgmntgrad}"/><br/> (위험)</c:when>
						        		<c:when test="${result.jdgmntgrad eq 'B등급' }"> <c:out value="${result.jdgmntgrad}"/><br/> (잠재적 위험)</c:when>
						        		<c:when test="${result.jdgmntgrad eq 'C등급' }"> <c:out value="${result.jdgmntgrad}"/><br/> (위험성 낮음)</c:when>
						        	</c:choose>
					        	</c:if>
					        </td>
					    </tr>
					    <tr>
					        <th>안정해석 점수</th>
					        <td colspan="3"><c:out value="${result.stabanalscore}"/></td>
					    </tr>
					    <tr>
					        <th>등급보정</th>
					        <td colspan="3"><c:out value="${result.gradcoreton}"/></td>
					        <th colspan="6">보정사유</th>
					        <td colspan="20"><c:out value="${result.revisioncause}"/></td>
					    </tr>
					    <tr>
					        <th class="subMenu" rowspan="5">종합 결론</th>
					        <th>사업가능여부</th>
					        <td colspan="3"><c:out value="${result.bsposblat}"/></td>
					        <th colspan="6">대책방안</th>
					        <td colspan="20"><c:out value="${result.cntrplnmethod}"/></td>
					    </tr>
					    <tr>
					        <th rowspan="2">관리 필요성</th>
					        <td colspan="8">현 상태 유지</td>
					        <td colspan="13">비구조적<br/>(대피체계구축필요)</td>
					        <td colspan="8">구조적+비구조적<br/>(적극적인 관리필요)</td>
					    </tr>
					    <tr>
					        <td colspan="8"><c:if test="${result.mngncssty eq '현 상태 유지'}">O</c:if></td>
					        <td colspan="13"><c:if test="${result.mngncssty eq '비구조적(대피체계구축필요)'}">O</c:if></td>
					        <td colspan="8"><c:if test="${result.mngncssty eq '구조적+비구조적(적극적인 관리필요)'}">O</c:if></td>
					    </tr>
					    <tr>
					        <th rowspan="2">종합의견</th>
					        <td colspan="29">
					        	<c:if test="${not empty result.opinion9}"> ∘ <c:out value="${result.opinion9}"/></c:if>
					        </td>
					    </tr>
					    <tr>
					    	<td colspan="29">
					        	<c:if test="${not empty result.opinion10}"> ∘ <c:out value="${result.opinion10}"/></c:if>
					        </td>
					    </tr>
					</tbody>
				</table>
				<c:if test="${result.svyType eq '취약지역 실태조사(산사태)' }">
					<div class="mainMenu">□ 산사태 취약지역 판정표(산사태)</div>
				</c:if>
				<c:if test="${result.svyType eq '취약지역 실태조사(토석류)' }">
					<div class="mainMenu">□ 산사태 취약지역 판정표(토석류)</div>
				</c:if>
				<table class="svyComptGrade brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
				  		<col width="10%;">
						<col width="10%;">
						<col width="10%;">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
						<col width="">
				  	</colgroup>
				    <thead>
				        <tr>
				            <th class="subMenu" colspan="2" rowspan="2">항목</th>
				            <th class="subMenu" rowspan="2">판정인자</th>
				            <th class="subMenu" colspan="6">항목 및 점수</th>
				        </tr>
				        <tr>
				            <c:forEach var="j" begin="1" end="5">
				                <th class="subMenu"><c:out value="${j}"/></th>
				            </c:forEach>
				            <th class="subMenu">점수</th>
				        </tr>
				    </thead>
				    <tbody>
				    	<c:if test="${result.svyType eq '취약지역 실태조사(산사태)' }">
				        <tr>
				            <th class="subMenu" colspan="2" rowspan="4">피해<br>가능성<br>(15)</th>
				            <th rowspan="2">피해이력</th>
				            <td>무</td>
				            <td>간접피해</td>
				            <td>직접피해</td>
				            <td rowspan="2"></td>
				            <td rowspan="2"></td>
				            <th rowspan="2"><c:out value="${result.dglogscore}"/></th>
				        </tr>
				        <tr>
				            <td>0</td>
				            <td>3</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th rowspan="2">직접영향권<br> 내 보호시설</th>
				            <td>없음</td>
				            <td>경작지 등<br> 재산피해</td>
				            <td>인가 1~4</td>
				            <td>인가 5 이상,<br> SOC시설</td>
				            <td rowspan="2"></td>
				            <th rowspan="2"><c:out value="${result.direffcscore}"/></th>
				        </tr>
				        <tr>
				            <td>0</td>
				            <td>5</td>
				            <td>7</td>
				            <td>10</td>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="16">지형<br>(25)</th>
				            <th class="subMenu" rowspan="8">토사<br>사면</th>
				            <th rowspan="2">경사도(°)</th>
				            <td>25 미만</td>
				            <td>25~35 미만</td>
				            <td>35~41 미만</td>
				            <td>41 이상</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.slopescore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>5</td>
				            <td>7</td>
				        </tr>
				        <tr>
				            <th rowspan="2">사면높이<br>(m)</th>
				            <td>5 미만</td>
				            <td>5~20 미만</td>
				            <td>20~30 미만</td>
				            <td>30~40 미만</td>
				            <td>40 이상</td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.dirhgscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>2</td>
				            <td>3</td>
				            <td>5</td>
				            <td>7</td>
				        </tr>
				        <tr>
				            <th rowspan="2">토심(cm)</th>
				            <td>30 미만</td>
				            <td>30~50 미만</td>
				            <td>50~80 미만</td>
				            <td>80~100 미만</td>
				            <td>100 이상</td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.soildepscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>2</td>
				            <td>3</td>
				            <td>5</td>
				            <td>7</td>
				        </tr>
				        <tr>
				            <th rowspan="2">종단형상</th>
				            <td>상승사면</td>
				            <td>평형사면</td>
				            <td>하강사면</td>
				            <td>복합사면</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.crossfrmscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>2</td>
				            <td>3</td>
				            <td>4</td>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="8">암반<br>사면</th>
				            <th rowspan="2">경사도(°)</th>
				            <td>30 미만</td>
				            <td>30~40 미만</td>
				            <td>40~50 미만</td>
				            <td>50 이상</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.slopescore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>5</td>
				            <td>7</td>
				        </tr>
				        <tr>
				            <th rowspan="2">사면높이(m)</th>
				            <td>10 미만</td>
				            <td>10~20 미만</td>
				            <td>20~30 미만</td>
				            <td>30 이상</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.dirhgscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>5</td>
				            <td>7</td>
				        </tr>
				        <tr>
				            <th rowspan="2">암석종류</th>
				            <td>퇴적암<br>(이암, 혈암, 석회암, 사암 등)</td>
				            <td>화성암<br>(화강암류 기타)</td>
				            <td>변성암<br>(천매암, 점판암 기타)</td>
				            <td>변성암<br>(편마암류 및 편암류)</td>
				            <td>화성암<br>(반암류와 안산암류)</td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.rockkndscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>2</td>
				            <td>3</td>
				            <td>5</td>
				            <td>7</td>
				        </tr>
				        <tr>
				            <th rowspan="2">균열상황<br>(이완암상태)</th>
				            <td>적음</td>
				            <td>조금 적음</td>
				            <td>조금 많음</td>
				            <td>많음</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.crcksittnscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>2</td>
				            <td>3</td>
				            <td>4</td>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="16">주요위험인자<br>(30)</th>
				            <th class="subMenu" rowspan="4">공통</th>
				            <th rowspan="2">산사태위험<br>등급 현황</th>
				            <td>산사태위험등급<br>5등급</td>
				            <td>산사태위험등급 3,4등급</td>
				            <td>산사태위험등급<br>2등급 50% 미만</td>
				            <td>산사태위험등급 2등급 50% 이상</td>
				            <td>산사태위험등급 1등급</td>
				            <th rowspan="2"><c:out value="${result.lndslddgscore}"/></th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>2</td>
				            <td>3</td>
				            <td>4</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th rowspan="2">용수상황</th>
				            <td>건조</td>
				            <td>습윤</td>
				            <td>표면수</td>
				            <td>용수</td>
				            <td rowspan="2"></td>
				            <th rowspan="2"><c:out value="${result.watersttusscore}"/></th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>4</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="6">토사<br>사면</th>
				            <th rowspan="2">붕괴지</th>
				            <td>없음</td>
				            <td>표층유실, 세굴</td>
				            <td>낙석, 포행</td>
				            <td>붕괴(붕락, 포락)</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.sliplandscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>0</td>
				            <td>5</td>
				            <td>7</td>
				            <td>10</td>
				        </tr>
				        <tr>
				            <th rowspan="2">뿌리특성</th>
				            <td>천근성+심근성</td>
				            <td>심근성</td>
				            <td>천근성 또는 무입목지</td>
				            <td rowspan="2"></td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.rootcharscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th rowspan="2">산림현황</th>
				            <td>울폐도(밀)</td>
				            <td>울폐도<br>(소, 중)</td>
				            <td>황폐지, 무입목지, 치수림</td>
				            <td>산림훼손지</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.foreststtusscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>4</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="6">암반<br>사면</th>
				            <th rowspan="2">붕괴</th>
				            <td>없음</td>
				            <td>균열 및 절리</td>
				            <td>낙석 또는 붕괴</td>
				            <td rowspan="2"></td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.clpsscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>0</td>
				            <td>7</td>
				            <td>10</td>
				        </tr>
				        <tr>
				            <th rowspan="2">불연속면 방향</th>
				            <td>유리</td>
				            <td>양호</td>
				            <td>불리</td>
				            <td>매우 불리</td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.surfacescore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>4</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th rowspan="2">풍화상태</th>
				            <td>약간 풍화</td>
				            <td>보통 풍화</td>
				            <td>심한 풍화<br>완전 풍화</td>
				            <td rowspan="2"></td>
				            <td rowspan="2"></td>
				            <th rowspan="2">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.wtrsttusscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <td>1</td>
				            <td>3</td>
				            <td>5</td>
				        </tr>
				        <tr>
				            <th class="subMenu" colspan="2">점수</th>
				            <th>계</th>
				            <th colspan="6"><c:out value="${result.fieldsurveyscore}"/></th>
				        </tr>
				        <tr>
				            <th class="subMenu" colspan="3" rowspan="2">작성시 주의사항</th>
				            <td colspan="6">※ 토사사면일 경우 토사사면 인자, 암반사면일 경우 암반사면 인자의 점수만 적용</td>
				        </tr>
				        <tr>
				            <td colspan="6">※ 사면유형이 혼재된 복합사면의 경우 토사사면 및 암반사면 중 점수가 더 높거나 위험 가능성이 큰 사면의 배점만 적용</td>
				        </tr>
						</c:if>		
						<c:if test="${result.svyType eq '취약지역 실태조사(토석류)' }">
							<tr>
					            <th class="subMenu" colspan="2" rowspan="4">피해<br>가능성<br>(15)</th>
					            <th rowspan="2">피해이력</th>
					            <td>없음</td>
					            <td>간접피해</td>
					            <td>직접피해</td>
					            <td rowspan="2"></td>
					            <td rowspan="2"></td>
					            <th rowspan="2"><c:out value="${result.dglogscore}"/></th>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>3</td>
					            <td>5</td>
					        </tr>
					        <tr>
					            <th rowspan="2">직접영향권<br> 내 보호시설</th>
					            <td>없음</td>
					            <td>경작지 등<br> 재산피해</td>
					            <td>인가 1~4</td>
					            <td>인가 5 이상,<br> SOC시설</td>
					            <td rowspan="2"></td>
					            <th rowspan="2"><c:out value="${result.direffcscore}"/></th>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>5</td>
					            <td>7</td>
					            <td>10</td>
					        </tr>
					        <tr>
					            <th class="subMenu" colspan="2" rowspan="6">지형<br>(25)</th>
					            <th rowspan="2">유역면적<br>(ha)</th>
					            <td>5 미만</td>
					            <td>5~10 미만</td>
					            <td>10~20 미만</td>
					            <td>20~30 미만</td>
					            <td>30 이상</td>
					            <th rowspan="2"><c:out value="${result.dgrareascore}"/></th>
					        </tr>
					        <tr>
					            <td>1</td>
					            <td>2</td>
					            <td>3</td>
					            <td>4</td>
					            <td>5</td>
					        </tr>
					        <tr>
					            <th rowspan="2">계류<br>평균경사도(°)</th>
					            <td>5 미만</td>
					            <td>5~15 미만</td>
					            <td>15~20 미만</td>
					            <td>20 이상</td>
					            <td rowspan="2"></td>
					            <th rowspan="2"><c:out value="${result.mntntrntscore}"/></th>
					        </tr>
					        <tr>
					            <td>3</td>
					            <td>5</td>
					            <td>8</td>
					            <td>10</td>
					        </tr>
					        <tr>
					            <th rowspan="2">토심<br>(cm)</th>
					            <td>30 미만</td>
					            <td>30~50 미만</td>
					            <td>50~80 미만</td>
					            <td>80~100 미만</td>
					            <td>100 이상</td>
					            <th rowspan="2"><c:out value="${result.soildepscore}"/></th>
					        </tr>
					        <tr>
					            <td>1</td>
					            <td>3</td>
					            <td>5</td>
					            <td>7</td>
					            <td>10</td>
					        </tr>
					        <tr>
					        	<th class="subMenu" rowspan="18">위험인자<br>(30점)</th>
					        	<th class="subMenu" rowspan="8">주 위험요소<br>(20점)</th>
					            <th rowspan="2">붕괴</th>
					            <td>없음</td>
					            <td>높이 5m 미만</td>
					            <td>높이 5m 이상</td>
					            <td rowspan="2"></td>
					            <td rowspan="8">주 위험요소<br>항목 중 높은 점수<br>택 1</td>
					            <th rowspan="8"><c:out value="${result.mainriskelemscore}"/></th>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>10</td>
					            <td>20</td>
					        </tr>
					        <tr>
					            <th rowspan="2">침식</th>
					            <td>5% 미만</td>
					            <td>5~20% 미만</td>
					            <td>20% 이상</td>
					            <td rowspan="2"></td>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>10</td>
					            <td>20</td>
					        </tr>
					        <tr>
					            <th rowspan="2">전석</th>
					            <td>0%</td>
					            <td>1~10% 미만 <br> 또는 30% 초과</td>
					            <td>10~30%</td>
					            <td rowspan="2"></td>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>10</td>
					            <td>20</td>
					        </tr>
					        <tr>
					            <th rowspan="2">토석류 흔적</th>
					            <td>무</td>
					            <td rowspan="2"></td>					            
					            <td>유</td>
					            <td rowspan="2"></td>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>10</td>
					        </tr>
					        <tr>
					            <th class="subMenu" rowspan="10">잠재적 위험<br>요소<br>(10점)</th>
					            <th rowspan="2">산사태위험<br>등급현황</th>
					            <td>산사태위험등급<br>3등급 이하</td>
					            <td>산사태위험등급<br>2등급 50% 미만</td>
					            <td>산사태위험등급<br>2등급 50% 이상</td>
					            <td>산사태위험등급 1등급</td>
					            <td rowspan="2"></td>
					            <th rowspan="2"><c:out value="${result.scodsltnscore}"/></th>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>1</td>
					            <td>2</td>
					            <td>3</td>
					        </tr>
					        <tr>
					            <th rowspan="2">산림현황</th>
					            <td>울폐도(밀)</td>
					            <td>울폐도(소, 중)</td>
					            <td>수목전도 및<br>고사목</td>
					            <td>산림훼손지<br>황폐지</td>
					            <td rowspan="2"></td>
					            <th rowspan="2"><c:out value="${result.foreststtusscore}"/></th>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>1</td>
					            <td>2</td>
					            <td>3</td>
					        </tr>
					        <tr>
					            <th rowspan="2">뿌리특성</th>
					            <td>심근성 + 천근성</td>
					            <td>심근성<br>(70% 이상)</td>
					            <td>천근성<br>(70% 이상)</td>
					            <td rowspan="2"></td>
					            <td rowspan="2"></td>					            
					            <th rowspan="2"><c:out value="${result.rootcharscore}"/></th>
					        </tr>
					        <tr>
					            <td>0</td>
					            <td>1</td>
					            <td>2</td>
					        </tr>
					        <tr>
					            <th rowspan="4">기타위험요소<br>(선택형)</th>
					            <td>유송잡물, 퇴적지</td>
					            <td>용출수</td>
					            <td>유실</td>
					            <td>배수상태</td>
					            <td rowspan="4">기타위험요소<br>항목 중 택 1</td>
					            <th rowspan="4"><c:out value="${result.etcdgscore}"/></th>
					        </tr>
					        <tr>
					            <td>2</td>
					            <td>2</td>
					            <td>2</td>
					            <td>2</td>
					        </tr>
					        <tr>
					            <td>단층대, 지진대</td>
					            <td>복합적 지질구조</td>
					           	<td colspan="2">기타<br>(위험요소 기재)</td>
					        </tr>
					        <tr>
					            <td>2</td>
					            <td>2</td>
					            <td colspan="2">2</td>
					        </tr>
					        <tr>
					            <th class="subMenu" colspan="2">점수</th>
					            <th>계</th>
					            <th colspan="6"><c:out value="${result.fieldsurveyscore}"/></th>
					        </tr>
					        <tr>
					            <th class="subMenu" colspan="3" rowspan="2">작성시 주의사항</th>
					            <td colspan="6">※ 토사사면일 경우 토사사면 인자, 암반사면일 경우 암반사면 인자의 점수만 적용</td>
					        </tr>
					        <tr>
					            <td colspan="6">※ 사면유형이 혼재된 복합사면의 경우 토사사면 및 암반사면 중 점수가 더 높거나 위험 가능성이 큰 사면의 배점만 적용</td>
					        </tr>
						</c:if>
				    </tbody>
				</table>
				<!-- 종합의견 -->			
				<div class="mainMenu">□ 종합의견</div>		
				<table class="svyCompt mainOpinion brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
		    		<tbody>
	    				<tr>
					        <th class="subMenu" rowspan="8">종합 의견</th>
					        <th>대상지 개요</th>
					        <td colspan="5"><c:if test="${not empty result.opinion1}"> ∘ <c:out value="${result.opinion1}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>위험징후 및 분포현황</th>
					        <td colspan="5"><c:if test="${not empty result.opinion2}"> ∘ <c:out value="${result.opinion2}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>간략한 종합의견 및 지정사유1</th>
					        <td colspan="5"><c:if test="${not empty result.opinion3}"> ∘ <c:out value="${result.opinion3}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>간략한 종합의견 및 지정사유2</th>
					        <td colspan="5"><c:if test="${not empty result.opinion4}"> ∘ <c:out value="${result.opinion4}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>구역설정 근거 및 사유</th>
					        <td colspan="5"><c:if test="${not empty result.opinion5}"> ∘ <c:out value="${result.opinion5}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>특이사항 및 진입여건</th>
					        <td colspan="5"><c:if test="${not empty result.opinion6}"> ∘ <c:out value="${result.opinion6}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>재해예방 사업종 선정사유</th>
					        <td colspan="5"><c:if test="${not empty result.opinion7}"> ∘ <c:out value="${result.opinion7}"/></c:if></td>
					    </tr>
					    <tr>
					        <th>기타종합의견 주민협의 및 대체대안</th>
					        <td colspan="5"><c:if test="${not empty result.opinion8}"> ∘ <c:out value="${result.opinion8}"/></c:if></td>
					    </tr>
		    		</tbody>
		    	</table>
			    	
				<!-- 현장사진 -->			
				<div class="mainMenu">□ 현장사진</div>					
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
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button" class="del-btn" onclick="javascript:fncDeleteSvyCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
							</sec:authorize>
							<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView(); return false;"><spring:message code="title.update" /></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_WKA','ROLE_SUB_ADMIN','ROLE_ADMIN')">
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_WKA','ROLE_USER_CNRS','ROLE_USER')"> --%>
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