<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysFckApr.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">계측장비</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update"/></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<input type="hidden" name="wireExtPhotoList" value="<c:out value="${wireExtPhotoList}"/>">
		<input type="hidden" name="licMeterPhotoList" value="<c:out value="${licMeterPhotoList}"/>">
		<input type="hidden" name="gwGaugePhotoList" value="<c:out value="${gwGaugePhotoList}"/>">
		<input type="hidden" name="rainGaugePhotoList" value="<c:out value="${rainGaugePhotoList}"/>">
		<input type="hidden" name="strcDpmPhotoList" value="<c:out value="${strcDpmPhotoList}"/>">
		<input type="hidden" name="surDpmPhotoList" value="<c:out value="${surDpmPhotoList}"/>">
		<input type="hidden" name="gpsGaugePhotoList" value="<c:out value="${gpsGaugePhotoList}"/>">
		<input type="hidden" name="gatewayPhotoList" value="<c:out value="${gatewayPhotoList}"/>">
		<input type="hidden" name="nodePhotoList" value="<c:out value="${nodePhotoList}"/>">
		<input type="hidden" name="photoList" value="<c:out value="${photoList}"/>">
		<form:form id="listForm" commandName="result" action="${pageContext.request.contextPath}/sys/fck/mse/sct/updateMseCompt.do" method="post" enctype="multipart/form-data">
			<input name="wireext" type="hidden"  value="">
			<input name="licmeter" type="hidden"  value="">
			<input name="gwgauge" type="hidden"  value="">
			<input name="raingauge" type="hidden"  value="">
			<input name="strcdpm" type="hidden"  value="">
			<input name="surdpm" type="hidden"  value="">
			<input name="gpsgauge" type="hidden"  value="">
			<input name="gateway" type="hidden"  value="">
			<input name="node" type="hidden"  value="">
			<input name="photoTagList" type="hidden"  value="">							
			<input name="gid" type="hidden" value="<c:out value="${result[0].gid}"/>">
			<input name="sldid" type="hidden" value="<c:out value="${result[0].sldid}"/>">
			<input name="mstId" type="hidden" value="<c:out value="${result[0].mstId}"/>">
			<input name="svystep" type="hidden" value="<c:out value="${result[0].svystep}"/>">
			<input name="updt" type="hidden" value="update">
			<div class="BoardDetail">
				<table id="mseSvyComptTable" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}"/>">
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
						<td colspan="5"><input type="text" name="opinion1" value="<c:out value="${result[0].opinion1}"/>" /></td>
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
									<tr class="tw">
										<th>
											<input type="hidden" name="wireextchl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
											<input type="hidden" name="tw_chl" value="<c:out value="${item.get('채널명')}"/>"/>
											<c:out value="${item.get('채널명')}"/>
										</th>
										<td>
                                            <select name="tw_sensor">
                                                <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                    <option value="${sttus.codeNm}" <c:if test="${item.get('센서부')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
										<td>
                                            <select name="tw_wire">
                                                <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                    <option value="${sttus.codeNm}" <c:if test="${item.get('와이어')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
										<td>
                                            <select name="tw_cable">
                                                <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                    <option value="${sttus.codeNm}" <c:if test="${item.get('케이블')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
										<td>
                                            <select name="tw_prtcbox">
                                                <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                    <option value="${sttus.codeNm}" <c:if test="${item.get('보호함체')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
										<td>
                                            <select name="tw_power">
                                                <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                    <option value="${sttus.codeNm}" <c:if test="${item.get('전원')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
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
									<tr class="twPer">
										<th>
											<input type="hidden" name="tw_chl" value="<c:out value="${item.get('채널명')}"/>"/>
											<c:out value="${item.get('채널명')}"/>
										</th>
										<td>
                                            <select name="twPer_chkresult">
                                                <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                    <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
										<td><input type="text" name="twPer_badsttus" value="${item.get('불량상태')}"/></td>
										<td><input type="text" name="twPer_sptsensor" value="${item.get('현장센서값')}"/></td>
										<td><input type="text" name="twPer_syssensor" value="${item.get('시스템센서값')}"/></td>
									</tr>
								</c:forEach>
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
								<tr class="inc">
									<th>
										<input type="hidden" name="licmeterchl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
										<input type="hidden" name="inc_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="inc_sensor">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('센서부')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="inc_cable">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('케이블')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="inc_prtcbox">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('보호함체')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="inc_power">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('전원')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
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
								<tr class="incPer <c:out value="${item.get('채널명')}"/>">
									<th>
										<input type="hidden" name="incPer_chl" value="<c:out value="${item.get('채널명')}"/>" />
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="incPer_chkresult">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td><input type="text" name="incPer_badsttus" value="${item.get('불량상태')}"/></td>
									<td><input type="text" name="incPer_sptsensor" value="${item.get('현장 센서값')}"/></td>
									<td><input type="text" name="incPer_syssensor" value="${item.get('시스템 센서값')}"/></td>
								</tr>
							</c:forEach>
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
								<tr class="wl">
									<th>
										<input type="hidden" name="gwgaugechl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
										<input type="hidden" name="wl_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="wl_sensor">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('센서부') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="wl_cable">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('케이블') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="wl_prtcbox">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('보호함체') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="wl_power">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('전원') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="wl_lock">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('자물쇠') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
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
								<tr class="wlPer">
									<th>
										<input type="hidden" name="wl_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>										
									<td>
                                        <select name="wlPer_chkresult">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td><input type="text" name="wlPer_badsttus" value="${item.get('불량상태')}"/></td>
									<td><input type="text" name="wlPer_sptwalNode" value="${item.get('현장수위값_노드')}"/></td>
									<td><input type="text" name="wlPer_sptwalPass" value="${item.get('현장수위값_수동')}"/></td>
									<td><input type="text" name="wlPer_syssnsRaw" value="${item.get('시스템센서값_RAW')}"/></td>
									<td><input type="text" name="wlPer_syssnsWal" value="${item.get('시스템센서값_수위')}"/></td>
								</tr>
							</c:forEach>
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
								<tr class="rg">
									<th>
										<input type="hidden" name="raingaugechl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
										<input type="hidden" name="rg_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="rg_sensor">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('센서부') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="rg_cable">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('케이블') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="rg_power">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('전원') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
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
								<tr class="rgPer">
									<th>
										<input type="hidden" name="rg_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>										
									<td>
                                        <select name="rgPer_chkresult">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td><input type="text" name="rgPer_bacsttus" value="${item.get('불량상태')}"/></td>
								</tr>
							</c:forEach>
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
								<tr class="tm">
									<th>
										<input type="hidden" name="strcdpmchl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
										<input type="hidden" name="tm_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="tm_sensor">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('센서부') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="tm_cable">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('케이블') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="tm_prtcbox">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('보호함체') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="tm_power">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('전원') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="tm_lock">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('자물쇠') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
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
								<tr class="tmPer">
									<th>
										<input type="hidden" name="tm_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>									
									<td>
                                        <select name="tmPer_chkresult">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td><input type="text" name="tmPer_badsttus" value="${item.get('불량상태')}"/></td>
									<td><input type="text" name="tmPer_sptsensor" value="${item.get('현장 센서값')}"/></td>
									<td><input type="text" name="tmPer_syssensor" value="${item.get('시스템 센서값')}"/></td>
								</tr>
							</c:forEach>
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
								<tr class="sd">
									<th>
										<input type="hidden" name="surdpmchl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
										<input type="hidden" name="sd_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="sd_sensor">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('센서부') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="sd_cable">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('케이블') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="sd_power">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('전원') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
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
								<tr class="sdPer">
									<th>
										<input type="hidden" name="sd_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>									
									<td>
                                        <select name="sdPer_chkresult">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td><input type="text" name="sdPer_badsttus" value="${item.get('불량상태')}"/></td>
									<td><input type="text" name="sdPer_sptsensorX" value="${item.get('현장센서값_X')}"/></td>
									<td><input type="text" name="sdPer_sptsensorY" value="${item.get('현장센서값_Y')}"/></td>
									<td><input type="text" name="sdPer_sptsensorAC" value="${item.get('현장센서값_가속도')}"/></td>
									<td><input type="text" name="sdPer_syssensorX" value="${item.get('시스템센서값_X')}"/></td>
									<td><input type="text" name="sdPer_syssensorY" value="${item.get('시스템센서값_Y')}"/></td>
									<td><input type="text" name="sdPer_syssensorAC" value="${item.get('시스템센서값_가속도')}"/></td>
								</tr>
							</c:forEach>
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
								<tr class="gps">
									<th>
										<input type="hidden" name="gpsgaugechl" value="<c:out value="${result[0].sldid}"/>_<c:out value="${item.get('채널명')}"/>"/>
										<input type="hidden" name="gps_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>
									<td>
                                        <select name="gps_sensor">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('센서부') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gps_cable">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('케이블') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gps_power">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('전원') eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
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
								<tr class="gpsPer">
									<th>
										<input type="hidden" name="gps_chl" value="<c:out value="${item.get('채널명')}"/>"/>
										<c:out value="${item.get('채널명')}"/>
									</th>										
									<td>
                                        <select name="gpsPer_chkresult">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('점검결과')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td><input type="text" name="gpsPer_badsttus" value="${item.get('불량상태')}"/></td>
								</tr>
							</c:forEach>
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
								<tr class="gateway">
									<th>
										<input type="hidden" name="gatewaychl" value="<c:out value="${result[0].sldid}"/>_gateway<c:out value="${item.get('연번')}"/>"/>
										<input type="hidden" name="gateway_chl" value="<c:out value="${item.get('연번')}"/>"/>
										<c:out value="${item.get('연번')}"/>
									</th>
									<c:if test="${status.count eq 1 }">
										<th rowspan="${listSize}">12V<br/>~<br/>10V</th>
										<th rowspan="${listSize}">3.6V<br/>~<br/>3.0V</th>
									</c:if>
									<td><input type="text" name="gateway_msvolt" value="${itemPer.get('측정전압')}"/></td>
									<td><input type="text" name="gateway_badsttus" value="${itemPer.get('불량상태')}"/></td>
									<td>
                                        <select name="gateway_solar">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('태양광')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gateway_box">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('함체')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gateway_prtcfence">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('보호휀스')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gateway_brnchln">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('지선')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gateway_board">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('안내판')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="gateway_lock">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('자물쇠')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
							    </tr>
							</c:forEach>
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
								<tr class="node">
									<th>
										<input type="hidden" name="nodechl" value="<c:out value="${result[0].sldid}"/>_node<c:out value="${item.get('연번')}"/>"/>
										<input type="hidden" name="node_chl" value="<c:out value="${item.get('연번')}"/>"/>
										<c:out value="${item.get('연번')}"/>
									</th>
									<c:if test="${status.count eq 1 }">
										<th rowspan="${listSize}">12V<br/>~<br/>10V</th>
										<th rowspan="${listSize}">3.6V<br/>~<br/>3.0V</th>
									</c:if>
									<td><input type="text" name="node_ms" value="${itemPer.get('측정')}"/></td>
									<td><input type="text" name="node_badsttus" value="${itemPer.get('불량상태')}"/></td>
									<td>
                                        <select name="node_solar">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('태양광')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="node_box">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('함체')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="node_prtcfence">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('보호휀스')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="node_brnchln">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('지선')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="node_board">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('안내판')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
									<td>
                                        <select name="node_lock">
                                            <c:forEach var="sttus" items="${mseSttus}" varStatus="status">
                                                <option value="${sttus.codeNm}" <c:if test="${item.get('자물쇠')  eq sttus.codeNm}"> selected</c:if>><c:out value="${sttus.codeNm}"/></option>
                                            </c:forEach>
                                        </select>
                                    </td>
							    </tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				</c:if>
				<!-- 현장사진 -->		
				<div class="mainMenu">□ 현장사진</div>	
				<div class="photoWrap">				
					<c:choose>
						<c:when test="${fn:length(photoTagList) > 0 }">
							<c:forEach items="${photoTagList}" var="item" varStatus="status">
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
											<input style="width:70%; float:left; margin-left:10px;" type="text" name="photoTag<c:out value="${status.count }"/>"
											<c:choose>
												<c:when test="${fn:contains(item,'TAG') && fn:length(item.get('TAG')) > 0 }">value="<c:out value="${item.get('TAG')}"/>"</c:when>
												<c:otherwise>placheholder="사진 태그 없음"</c:otherwise>
											</c:choose>
											/>
											<button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncLssSvyDeletePhoto(event); return false;">삭제</button>
										</div>
										<div class="photoUrl">
											<input type="hidden" <c:if test="${noImage eq false}">value="<c:url value='${photoUrl}'/>"</c:if> name="photoSrc<c:out value="${status.count}"/>" />
						                    <img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> class="photoSrc<c:out value="${status.count}"/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
						                    <span class="thumb-div noselect">변경</span>
										</div>
									</div>
							</c:forEach>				
						</c:when>
						<c:otherwise>
							<div class="noPhotoTagInfo">사진태그 정보 없음</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<c:if test="${photoList != null }">
							<button type="button" class="add-btn" onclick="javascript:fncMseSvyAddPhoto(); return false;">현장사진 <spring:message code="button.add" /></button>
						</c:if>
              			<button type="button" class="modify-btn" onclick="javascript:fncSvyComptUpdate(); return false;"><spring:message code="title.update" /></button>
              			<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>