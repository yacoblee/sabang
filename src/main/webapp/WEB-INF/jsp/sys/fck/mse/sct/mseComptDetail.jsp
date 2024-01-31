<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysFckApr.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">계측장비</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/fck/mse/sct/selectFckMseComptUpdt.do" method="post">				
			<input name="gid" type="hidden" value="<c:out value="${result[0].gid}" />">
			<input name="sldid" type="hidden" value="<c:out value="${result[0].sldid}" />">
			<input name="svystep" type="hidden" value="<c:out value="${result[0].svystep}" />">
			<input name="updt" type="hidden" value="update">
			<div class="BoardDetail">
				<table id="mseSvyComptTable" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tr>
						<th>조사ID</th>
						<td><c:out value="${result[0].sldid}"/></td>
						<th>조사자</th>
						<td><c:out value="${result[0].svyUser}"/></td>
						<th>조사일자</th>
						<td><c:out value="${result[0].svyDt}"/></td>
					</tr>
					<tr>
						<th>소재지</th>
						<td colspan="3"><c:out value="${result[0].svySd }"/> <c:out value="${result[0].svySgg }"/> <c:out value="${result[0].svyEmd }"/> <c:out value="${result[0].svyRi }"/> <c:out value="${result[0].svyJibun }"/></td>
						<th>속칭</th>
						<td><c:out value="${result[0].commonly }"/></td>
					</tr>
					<tr>
						<th>위도</th>
						<td colspan="2"><c:out value="${result[0].lat}"/></td>
						<th>경도</th>
						<td colspan="2"><c:out value="${result[0].lon}"/></td>
					</tr>
					<tr>
						<th>소유구분</th>
						<td colspan="2"><c:out value="${result[0].owner}"/></td>
						<th>법적제한사항</th>
						<td colspan="2"><c:out value="${result[0].lgllimit}"/></td>
					</tr>
					<tr>
						<th>종합의견</th>
						<td colspan="5"><c:out value="${result[0].opinion1}"/></td>
					</tr>
				</table>
				
				<!-- 와이어신축계 -->
				<c:if test="${fn:length(wireExtList) > 0}">
					<div class="mainMenu">□ 와이어신축계</div>
					<div id="wireExtTable">
						<table>
							<tbody>
								<tr><th colspan="7">외관 점검</th></tr>
								<tr>
									<th>채널명</th>
									<th>센서부</th>										
									<th>와이어</th>										
									<th>케이블</th>										
									<th>보호 함체</th>										
									<th>전원</th>										
								</tr>
								<c:forEach var="item" items="${wireExtList}" varStatus="status">
									<tr>
										<th><c:out value="${item.get('채널명')}"/></th>
										<td><c:out value="${item.get('센서부')}"/></td>
										<td><c:out value="${item.get('와이어')}"/></td>
										<td><c:out value="${item.get('케이블')}"/></td>
										<td><c:out value="${item.get('보호함체')}"/></td>
										<td><c:out value="${item.get('전원')}"/></td>
								    </tr>
								</c:forEach>
							</tbody>
						</table>
						<table>
							<tbody>
								<tr><th colspan="5">성능 점검</th></tr>
								<tr>
									<th>채널명</th>
									<th>점검결과</th>
									<th>불량 상태</th>
									<th>현장 센서값</th>
									<th>시스템 센서값</th>
								</tr>
								<c:forEach var="item" items="${wireExtPerList}" varStatus="status">
									<tr>
										<th><c:out value="${item.get('채널명')}"/></th>
										<td><c:out value="${item.get('점검결과')}"/></td>										
										<td><c:out value="${item.get('불량상태')}"/></td>										
										<td><c:out value="${item.get('현장센서값')}"/></td>										
										<td><c:out value="${item.get('시스템센서값')}"/></td>																				
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<!-- 현장사진 -->		
						<div class="mainMenu">□ 현장사진</div>					
						<c:choose>
							<c:when test="${fn:length(wireExtPhotoTagList) > 0 }">
								<div class="photoWrap">
								<c:forEach items="${wireExtPhotoTagList}" var="item" varStatus="status">
									<c:choose>
										<c:when test="${not empty item.get('FILENAME')}">
											<c:set var="noImage" value="false"/>
											<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
										</c:when>
										<c:otherwise>
											<c:set var="noImage" value="true"/>
											<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
										</c:otherwise>
									</c:choose>
										<div class="photoBox" style="width: 50%;">
											<div class="photoTag">
												<c:choose>
													<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
													<c:otherwise>사진 태그 없음</c:otherwise>
												</c:choose>
											</div>
											<div class="photoUrl">
												<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</div>
										</div>
								</c:forEach>
								</div>						
							</c:when>
							<c:otherwise>
								<div class="noPhotoTagInfo">사진태그 정보 없음</div>
							</c:otherwise>
						</c:choose>
						<!-- 기타사진 -->
						<table>
							<tbody>
								<c:if test="${fn:length(wireExtPhotoList) > 0 }">
									<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
									<tr class="photoTr">
										 <td colspan="6">
										 	<div>
											 	<c:forEach items="${wireExtPhotoList}" var="item" varStatus="status">
											 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
												</c:forEach>
											</div>
										</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</c:if>
				
				<!-- 지중경사계 -->
				<c:if test="${fn:length(licMeterList) > 0}">
				<div class="mainMenu">□ 지중경사계</div>
				<div id="licMeterTable">
					<table>
						<tbody>
							<tr><th colspan="7">외관 점검</th></tr>
							<tr>
								<th>채널명</th>										
								<th>센서부</th>										
								<th>케이블</th>										
								<th>보호 함체</th>										
								<th>전원</th>																				
							</tr>
							<c:forEach var="item" items="${licMeterList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('센서부')}"/></td>
									<td><c:out value="${item.get('케이블')}"/></td>
									<td><c:out value="${item.get('보호함체')}"/></td>
									<td><c:out value="${item.get('전원')}"/></td>
							    </tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tbody>
							<tr><th colspan="5">성능 점검</th></tr>
							<tr>
								<th>채널명</th>
								<th>점검결과</th>
								<th>불량상태</th>
								<th>현장 센서값</th>
								<th>시스템 센서값</th>
							</tr>
							<c:forEach var="item" items="${licMeterPerList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명') }"/></th>										
									<td><c:out value="${item.get('점검결과') }"/></td>										
									<td><c:out value="${item.get('불량상태') }"/></td>										
									<td><c:out value="${item.get('현장 센서값') }"/></td>										
									<td><c:out value="${item.get('시스템 센서값') }"/></td>										
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(licMeterPhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${licMeterPhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(licMeterPhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${licMeterPhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- 지하수위계 -->
				<c:if test="${fn:length(gwGaugeList) > 0}">
				<div class="mainMenu">□ 지하수위계</div>
				<div id="gwGaugeTable">
					<table>
						<tbody>
							<tr><th colspan="7">외관 점검</th></tr>
							<tr>
								<th>채널명</th>										
								<th>센서부</th>										
								<th>케이블</th>										
								<th>보호함체</th>										
								<th>전원</th>										
								<th>자물쇠</th>										
							</tr>
							<c:forEach var="item" items="${gwGaugeList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('센서부')}" /></td>
									<td><c:out value="${item.get('케이블')}" /></td>
									<td><c:out value="${item.get('보호함체')}" /></td>
									<td><c:out value="${item.get('전원')}" /></td>
									<td><c:out value="${item.get('자물쇠')}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tbody>
							<tr><th colspan="7">성능 점검</th></tr>
							<tr>
								<th rowspan="2">채널명</th>
								<th rowspan="2">점검결과</th>
								<th rowspan="2">불량상태</th>
								<th colspan="2">현장 수위값</th>
								<th colspan="2">시스템 센서값</th>
							</tr>
							<tr>
								<th>노드</th>
								<th>수동</th>
								<th>RAW</th>
								<th>수위</th>
							</tr>
							<c:forEach var="item" items="${gwGaugePerList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('점검결과')}"/></td>										
									<td><c:out value="${item.get('불량상태')}"/></td>										
									<td><c:out value="${item.get('현장수위값_노드')}"/></td>										
									<td><c:out value="${item.get('현장수위값_수동')}"/></td>										
									<td><c:out value="${item.get('시스템센서값_RAW')}"/></td>										
									<td><c:out value="${item.get('시스템센서값_수위')}"/></td>										
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(gwGaugePhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${gwGaugePhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(gwGaugePhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${gwGaugePhotoList}" var="item" varStatus="status">
											 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- 강우계 -->
				<c:if test="${fn:length(rainGaugeList) > 0}">
				<div class="mainMenu">□ 강우계</div>
				<div id="rainGaugeTable">
					<table>
						<tbody>
							<tr><th colspan="4">외관 점검</th></tr>
							<tr>
								<th>채널명</th>										
								<th>센서부</th>										
								<th>케이블</th>															
								<th>전원</th>										
							</tr>
							<c:forEach var="item" items="${rainGaugeList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('센서부')}" /></td>
									<td><c:out value="${item.get('케이블')}" /></td>
									<td><c:out value="${item.get('전원')}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tbody>
							<tr><th colspan="4">성능 점검</th></tr>
							<tr>
								<th>채널명</th>
								<th>점검결과</th>
								<th colspan="2">불량상태</th>
							</tr>
							<c:forEach var="item" items="${rainGaugePerList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>	
									<td><c:out value="${item.get('점검결과')}"/></td>										
									<td colspan="2"><c:out value="${item.get('불량상태')}"/></td>																				
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(rainGaugePhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${rainGaugePhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(rainGaugePhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${rainGaugePhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- 구조물변위계 -->
				<c:if test="${fn:length(strcDpmList) > 0}">
				<div class="mainMenu">□ 구조물변위계</div>
				<div id="strcDpmTable">
					<table>
						<tbody>
							<tr><th colspan="7">외관 점검</th></tr>
							<tr>
								<th>채널명</th>										
								<th>센서부</th>										
								<th>케이블</th>										
								<th>보호함체</th>										
								<th>전원</th>										
								<th>자물쇠</th>										
							</tr>
							<c:forEach var="item" items="${strcDpmList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('센서부')}" /></td>
									<td><c:out value="${item.get('케이블')}" /></td>
									<td><c:out value="${item.get('보호함체')}" /></td>
									<td><c:out value="${item.get('전원')}" /></td>
									<td><c:out value="${item.get('자물쇠')}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tbody>
							<tr><th colspan="5">성능 점검</th></tr>
							<tr>
								<th>채널명</th>
								<th>점검결과</th>
								<th>불량상태</th>
								<th>현장 센서값</th>
								<th>시스템 센서값</th>
							</tr>
							<c:forEach var="item" items="${strcDpmPerList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>				
									<td><c:out value="${item.get('점검결과')}"/></td>										
									<td><c:out value="${item.get('불량상태')}"/></td>										
									<td><c:out value="${item.get('현장 센서값')}"/></td>										
									<td><c:out value="${item.get('시스템 센서값')}"/></td>										
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(strcDpmPhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${strcDpmPhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(strcDpmPhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${strcDpmPhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- 지표변위계 -->
				<c:if test="${fn:length(surDpmList) > 0}">
				<div class="mainMenu">□ 지표변위계</div>
				<div id="surDpmTable">
					<table>
						<tbody>
							<tr><th colspan="7">외관 점검</th></tr>
							<tr>
								<th>채널명</th>										
								<th>센서부</th>										
								<th>케이블</th>										
								<th>전원</th>										
							</tr>
							<c:forEach var="item" items="${surDpmList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('센서부')}" /></td>
									<td><c:out value="${item.get('케이블')}" /></td>
									<td><c:out value="${item.get('전원')}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tbody>
							<tr><th colspan="9">성능 점검</th></tr>
							<tr>
								<th rowspan="2">채널명</th>
								<th rowspan="2">점검결과</th>
								<th rowspan="2">불량상태</th>
								<th colspan="3">현장 센서값</th>
								<th colspan="3">시스템 센서값</th>
							</tr>
							<tr>
								<th>X</th>
								<th>Y</th>
								<th>가속도</th>
								<th>X</th>
								<th>Y</th>
								<th>가속도</th>
							</tr>
							<c:forEach var="item" items="${surDpmPerList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>			
									<td><c:out value="${item.get('점검결과')}"/></td>										
									<td><c:out value="${item.get('불량상태')}"/></td>										
									<td><c:out value="${item.get('현장센서값_X')}"/></td>										
									<td><c:out value="${item.get('현장센서값_Y')}"/></td>										
									<td><c:out value="${item.get('현장센서값_가속도')}"/></td>										
									<td><c:out value="${item.get('시스템센서값_X')}"/></td>										
									<td><c:out value="${item.get('시스템센서값_Y')}"/></td>										
									<td><c:out value="${item.get('시스템센서값_가속도')}"/></td>										
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(surDpmPhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${surDpmPhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(surDpmPhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${surDpmPhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- GPS변위계 -->
				<c:if test="${fn:length(gpsGaugeList) > 0}">
				<div class="mainMenu">□ GPS변위계</div>
				<div id="gpsGaugeTable">
					<table>
						<tbody>
							<tr><th colspan="4">외관 점검</th></tr>
							<tr>
								<th>채널명</th>										
								<th>센서부</th>										
								<th>케이블</th>															
								<th>전원</th>										
							</tr>
							<c:forEach var="item" items="${gpsGaugeList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>
									<td><c:out value="${item.get('센서부')}" /></td>
									<td><c:out value="${item.get('케이블')}" /></td>
									<td><c:out value="${item.get('전원')}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tbody>
							<tr><th colspan="4">성능 점검</th></tr>
							<tr>
								<th>채널명</th>
								<th>점검결과</th>
								<th colspan="2">불량상태</th>
							</tr>
							<c:forEach var="item" items="${gpsGaugePerList}" varStatus="status">
								<tr>
									<th><c:out value="${item.get('채널명')}"/></th>				
									<td><c:out value="${item.get('점검결과')}"/></td>										
									<td colspan="2"><c:out value="${item.get('불량상태')}"/></td>																				
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(gpsGaugePhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${gpsGaugePhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(gpsGaugePhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${gpsGaugePhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- 게이트웨이 -->
				<c:if test="${fn:length(gatewayList) > 0}">
				<div class="mainMenu">□ 게이트웨이</div>
				<div id="gatewayTable">
					<table>
						<tbody>
							<tr>
								<th colspan="5">성능 점검</th>
								<th colspan="6">외관 점검</th>
							</tr>
							<tr>
								<th rowspan="2">연번</th>
								<th colspan="3">배터리 전압</th>
								<!-- <th rowspan="2">점검결과</th> -->										
								<th rowspan="2">불량상태</th>										
								<th rowspan="2">태양광</th>										
								<th rowspan="2">함체</th>										
								<th rowspan="2">보호휀스</th>										
								<th rowspan="2">지선</th>										
								<th rowspan="2">안내판</th>										
								<th rowspan="2">자물쇠</th>																				
							</tr>
							<tr>
								<th colspan="2">양호 전압</th>										
								<th>측정</th>
							</tr>
							<c:forEach var="item" items="${gatewayList}" varStatus="status">
							<c:set var="listSize" value="${fn:length(gatewayList)}"/>
							<c:set var="itemPer" value="${gatewayPerList[status.index]}"/>
								<tr>
									<th><c:out value="${item.get('연번')}"/></th>
									<c:if test="${status.count eq 1 }">
										<th rowspan="${listSize}">12V<br/>~<br/>10V</th>
										<th rowspan="${listSize}">3.6V<br/>~<br/>3.0V</th>
									</c:if>
									<%-- <td><c:out value="${itemPer.get('양호전압') }"/></td> --%>
									<td><c:out value="${itemPer.get('측정전압')}"/></td>
									<%-- <td><c:out value="${itemPer.get('검검결과') }"/></td> --%>
									<td><c:out value="${itemPer.get('불량상태')}"/></td>
									<td><c:out value="${item.get('태양광')}"/></td>
									<td><c:out value="${item.get('함체')}"/></td>
									<td><c:out value="${item.get('보호휀스')}"/></td>
									<td><c:out value="${item.get('지선')}"/></td>
									<td><c:out value="${item.get('안내판')}"/></td>
									<td><c:out value="${item.get('자물쇠')}"/></td>
							    </tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(gatewayPhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${gatewayPhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(gatewayPhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${gatewayPhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
				
				<!-- 노드 -->
				<c:if test="${fn:length(nodeList) > 0}">
				<div class="mainMenu">□ 노드</div>
				<div id="nodeTable">
					<table>
						<tbody>
							<tr>
								<th colspan="5">성능 점검</th>
								<th colspan="6">외관 점검</th>
							</tr>
							<tr>
								<th rowspan="2">연번</th>
								<th colspan="3">배터리 전압</th>										
								<th rowspan="2">불량상태</th>										
								<th rowspan="2">태양광</th>										
								<th rowspan="2">함체</th>										
								<th rowspan="2">보호휀스</th>										
								<th rowspan="2">지선</th>										
								<th rowspan="2">안내판</th>										
								<th rowspan="2">자물쇠</th>																				
							</tr>
							<tr>
								<th colspan="2">양호 전압</th>																		
								<th>측정</th>
							</tr>
							<tr>
								
							</tr>
							<c:forEach var="item" items="${nodeList}" varStatus="status">
							<c:set var="listSize" value="${fn:length(nodeList)}"/>
							<c:set var="itemPer" value="${nodePerList[status.index]}"/>
								<tr>
									<th><c:out value="${item.get('연번')}"/></th>
									<c:if test="${status.count eq 1 }">
										<th rowspan="${listSize}">12V<br/>~<br/>10V</th>
										<th rowspan="${listSize}">3.6V<br/>~<br/>3.0V</th>
									</c:if>
									<td><c:out value="${itemPer.get('측정')}"/></td>
									<td><c:out value="${itemPer.get('불량상태')}"/></td>
									<td><c:out value="${item.get('태양광')}"/></td>
									<td><c:out value="${item.get('함체')}"/></td>
									<td><c:out value="${item.get('보호휀스')}"/></td>
									<td><c:out value="${item.get('지선')}"/></td>
									<td><c:out value="${item.get('안내판')}"/></td>
									<td><c:out value="${item.get('자물쇠')}"/></td>
							    </tr>
							</c:forEach>
						</tbody>
					</table>
					
					<!-- 현장사진 -->		
					<div class="mainMenu">□ 현장사진</div>					
					<c:choose>
						<c:when test="${fn:length(nodePhotoTagList) > 0 }">
							<div class="photoWrap">
							<c:forEach items="${nodePhotoTagList}" var="item" varStatus="status">
								<c:choose>
									<c:when test="${not empty item.get('FILENAME')}">
										<c:set var="noImage" value="false"/>
										<c:set var="photoUrl" value="${item.get('FILENAME')}"/>
									</c:when>
									<c:otherwise>
										<c:set var="noImage" value="true"/>
										<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
									</c:otherwise>
								</c:choose>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }"><c:out value="${item.get('TAG')}"/></c:when>
												<c:otherwise>사진 태그 없음</c:otherwise>
											</c:choose>
										</div>
										<div class="photoUrl">
											<img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
										</div>
									</div>
							</c:forEach>
							</div>						
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
					<!-- 기타사진 -->
					<table>
						<tbody>
							<c:if test="${fn:length(nodePhotoList) > 0 }">
								<tr><th style="border-top : 1px solid #d4d4de;" colspan="6">기타 사진</th></tr>
								<tr class="photoTr">
									 <td colspan="6">
									 	<div>
										 	<c:forEach items="${nodePhotoList}" var="item" varStatus="status">
										 		<img src="/storage/fieldBook<c:url value='${item}'/>" style="width: 400px; height: 300px; margin-right: 20px;" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
				</c:if>
					
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button" class="del-btn" onclick="javascript:fncDeleteMseCompt('<c:out value="${result[0].sldid}" />','<c:out value="${result[0].svystep}" />'); return false;"><spring:message code="title.delete" /></button>
							</sec:authorize>
							<button type="button" class="modify-btn" onclick="javascript:fncMseComptUpdt('<c:out value="${result[0].sldid}" />','<c:out value="${result[0].svystep}" />'); return false;"><spring:message code="title.update" /></button>
<%-- 							<button type="button" class="modify-btn" onclick="javascript:fncMseComptUpdt('MSE_0000000006','1차'); return false;"><spring:message code="title.update" /></button> --%>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_APR','ROLE_USER_CNRS','ROLE_USER')"> --%>
<%-- 								<c:if test="${result.loginId eq userInfo.id or result.loginId eq userInfo.name or fn:contains(result.svyUser,userInfo.name)}"> --%>
									<c:if test="${access eq 'ADMIN' }">
										<button type="button" class="del-btn" onclick="javascript:fncDeleteMseCompt('<c:out value="${result[0].sldid}" />','<c:out value="${result[0].svystep}" />'); return false;"><spring:message code="title.delete" /></button>
									</c:if>
									<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
										<button type="button" class="modify-btn" onclick="javascript:fncMseComptUpdt('<c:out value="${result[0].sldid}" />','<c:out value="${result[0].svystep}" />'); return false;"><spring:message code="title.update" /></button>
<%-- 										<button type="button" class="modify-btn" onclick="javascript:fncMseComptUpdt('MSE_0000000006','1차'); return false;"><spring:message code="title.update" /></button> --%>
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