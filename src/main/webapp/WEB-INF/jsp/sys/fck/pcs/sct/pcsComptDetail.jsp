<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysFckPcs.svyComptList.title"/></c:set>
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
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/fck/Pcs/sct/updateFckPcsComptView.do" method="post">
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
			<%-- <input name="schjdgmntgrad" type="hidden" value="<c:out value='${searchVO.jdgmntgrad}'/>"> --%>
			<div class="BoardDetail WkaBoardDetail">
				<div class="mainMenu">□ 정밀점검 일반사항</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckPcs.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
					<colgroup>
				  		<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
				  	</colgroup>
				  	<tbody>
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<tr>
							<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="3"><c:out value="${result.svyId }"/></td>
							<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!--조사유형 -->
							<td colspan="3"><c:out value="${result.svyType }"/></td>
						</tr>				  	
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrKnd"/></th><!-- 사방댐종류 -->
					  		<td colspan="3"><c:out value="${result.ecnrknd }"/></td>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrForm"/></th><!-- 형식 -->
					  		<td colspan="3"><c:out value="${result.svyform }"/></td>
				  		</tr>
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					  		<td colspan="3"><c:out value="${result.ecnrrnum }"/></td>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt"/></th><!-- 점검일시 -->
					  		<td colspan="3"><c:out value="${result.svyDt }"/></td>
				  		</tr>
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					  		<td colspan="3"><c:out value="${result.nationspotnum }"/></td>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyUser"/></th><!-- 점검자 -->
					  		<td colspan="3"><c:out value="${result.svyUser }"/></td>
				  		</tr>
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.sd"/></th><!-- 도 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.sgg"/></th><!-- 시/군 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.emd"/></th><!-- 읍/면 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ri"/></th><!-- 동/리 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.jibun"/></th><!-- 지번 -->
				  		</tr>
				  		<tr>
				  			<td colspan="2"><c:out value="${result.svySd }"/></td>
				  			<td colspan="2"><c:out value="${result.svySgg }"/></td>
				  			<td colspan="2"><c:out value="${result.svyEmd }"/></td>
				  			<td colspan="2"><c:out value="${result.svyRi }"/></td>
				  			<td colspan="2"><c:out value="${result.svyJibun }"/></td>
				  		</tr>
				  		<tr>
				  			<th class="subMenu" rowspan="5" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fclt"/></th><!-- 시설제원 -->
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
				  			<td colspan="6"><c:out value="${result.fcltyear }"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltUprHg"/></th><!-- 상장(m) -->
				  			<td colspan="2"><c:out value="${result.fcltuprhg }"/></td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltLwrHg"/></th><!-- 하장(m) -->
				  			<td colspan="2"><c:out value="${result.fcltlwrhg }"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltTthgh"/></th><!-- 전고(m) -->
				  			<td colspan="2"><c:out value="${result.tthghjdgval }"/></td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltStkHg"/></th><!-- 유효고(m) -->
				  			<td colspan="2"><c:out value="${result.fcltstkhg }"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltBt"/></th><!-- 천단폭(m) -->
				  			<td colspan="6"><c:out value="${result.wotdjdgval }"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.cnstrctCt"/></th><!-- 시공비(천원) -->
				  			<td colspan="6"><c:out value="${result.cnstrcost }"/></td>
				  		</tr>
				  		<tr>
				  			<th class="subMenu" colspan="10"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo"/>(WGS84)</th><!-- 산사태 위치정보 -->
				  		</tr>		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.nthtd"/></th><!-- 북위 -->
				  			<td colspan="3"><c:out value="${result.latdlndsld }"/>&deg;<c:out value="${result.latmlndsld }"/>&#39;<c:out value="${result.latslndsld }"/>&#34;N</td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.estlngtd"/></th><!-- 동경 -->
				  			<td colspan="3"><c:out value="${result.londlndsld }"/>&deg;<c:out value="${result.lonmlndsld }"/>&#39;<c:out value="${result.lonslndsld }"/>&#34;E</td>
				  		</tr>
				  		<tr>
				  			<th class="subMenu" colspan="10"><spring:message code="sysFckPcs.svyComptVO.acplcInfo"/>(WGS84)</th><!-- 현지계측 위치정보 -->
				  		</tr>					  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.nthtd"/></th><!-- 북위 -->
				  			<td colspan="3"><c:out value="${result.latdacplcsld }"/>&deg;<c:out value="${result.latmacplcsld }"/>&#39;<c:out value="${result.latsacplcsld }"/>&#34;N</td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.estlngtd"/></th><!-- 동경 -->
				  			<td colspan="3"><c:out value="${result.londacplcsld }"/>&deg;<c:out value="${result.lonmacplcsld }"/>&#39;<c:out value="${result.lonsacplcsld }"/>&#34;E</td>
				  		</tr>			  		
				  	</tbody>
				</table>
				<div class="mainMenu">□ 위치도</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<tbody>
				<c:if test="${lcmap_result.size() > 0 }">
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
				</tbody>
				</table>
				<div class="mainMenu">□ 현장사진</div>
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
					</tbody>
				</table>
				<div class="mainMenu">□ 정밀점검 평가표</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckPcs.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
					<colgroup>
				  		<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
				  	</colgroup>
				    <thead>
				        <tr>
				            <th class="subMenu">항목</th>
				            <th class="subMenu" colspan="4">결함사항</th>
				            <th class="subMenu" colspan="4">평가기준</th>
				            <th class="subMenu">평가점수</th>
				        </tr>
				    </thead>
					<tbody>
						<!-- 본댐 S -->
						<tr>
							<th class="subMenu" rowspan="5" ><spring:message code="sysFckPcs.svyComptVO.orginlDam.title"/></th>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1"/></th>
				            <td colspan="4"><c:out value="${result.orginldamdmg }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.orginldamdmgscore }" var="orginldamdmgscore"/>
				            	<c:out value="${result.orginldamdmgscore }"/>
				            </td>
				        </tr>
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2"/></th>
				            <td colspan="4"><c:out value="${result.orginldamcrk }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.orginldamcrkscore }" var="orginldamcrkscore"/>
				            	<c:out value="${result.orginldamcrkscore }"/>
				            </td>
				        </tr>
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3"/></th>
				            <td colspan="4"><c:out value="${ result.orginldambasicscour}"/></td>
				            <td>
								<fmt:parseNumber type="number" value="${result.orginldambasicscourscore }" var="orginldambasicscourscore"/>
				            	<c:out value="${result.orginldambasicscourscore }"/>
				            </td>
				        </tr>
				        <tr>
			            	<fmt:parseNumber var="orginldamcncrtscore" type="number" value="${result.orginldamcncrtscore}"/>
			            	<fmt:parseNumber var="orginldamplngscore" type="number" value="${result.orginldamplngscore}"/>
				            <c:choose>
				            <c:when test="${orginldamplngscore gt orginldamcncrtscore }">
							<th colspan="4"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42"/></th>
							<td colspan="4"><c:out value="${result.orginldamcncrt }"/></td>
							<td>
								<fmt:parseNumber type="number" value="${result.orginldamplngscore }" var="orginldamscore42"/>
								<c:out value="${result.orginldamplngscore }"/>
							</td>
							</c:when>
				            <c:otherwise>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41"/></th>
				            <td colspan="4"><c:out value="${result.orginldamplng }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.orginldamcncrtscore }" var="orginldamscore42"/>
				            	<c:out value="${result.orginldamcncrtscore }"/>
				            </td>
				            </c:otherwise>
				            </c:choose>
				        </tr>
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5"/></th>
				            <td colspan="4"><c:out value="${ result.orginldamwtgate}"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.orginldamwtgatescore }" var="orginldamwtgatescore"/>
				            	<c:out value="${result.orginldamwtgatescore }"/>
				            </td>
				        </tr>
				        <!-- 본댐 E -->
				        <!-- 측벽 S -->
				        <tr>
				            <th rowspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWall.title"/></th>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.sideWall.defects1"/></th>
				            <td colspan="4"><c:out value="${result.sidewalldmg }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.sidewalldmgscore }" var="sidewalldmgscore"/>
				            	<c:out value="${result.sidewalldmgscore }"/>
				            </td>
				        </tr>
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.sideWall.defects2"/></th>
				            <td colspan="4"><c:out value="${result.sidewallcrk }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.sidewallcrkscore }" var="sidewallcrkscore"/>
				            	<c:out value="${result.sidewallcrkscore }"/>
				            </td>
				        </tr>
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.sideWall.defects3"/></th>
				            <td colspan="4"><c:out value="${result.sidewallbasicscour }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.sidewallbasicscourscore }" var="sidewallbasicscourscore"/>
				            	<c:out value="${result.sidewallbasicscourscore }"/>
				            </td>
				        </tr>
				        <!-- 측벽 E -->		        
				        <!-- 물받이 S -->
				        <tr>
				            <th rowspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnspt.title"/></th>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1"/>
				            <td colspan="4"><c:out value="${result.dwnsptdmg }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.dwnsptdmgscore }" var="dwnsptdmgscore"/>
				            	<c:out value="${result.dwnsptdmgscore }"/>
				            </td>
				        </tr>
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2"/>
				            <td colspan="4"><c:out value="${result.dwnsptbasicscour }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.dwnsptbasicscourscore }" var="dwnsptbasicscourscore"/>
				            	<c:out value="${result.dwnsptbasicscourscore }"/>
				            </td>
				        </tr>		        
				        <tr>
				            <th colspan="4"><spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3"/>
				            <td colspan="4"><c:out value="${result.dwnsptcrk }"/></td>
				            <td>
				            	<fmt:parseNumber type="number" value="${result.dwnsptcrkscore }" var="dwnsptcrkscore"/>
				            	<c:out value="${result.dwnsptcrkscore }"/>
				            </td>
				        </tr>		        
				        <!-- 물받이 E -->	
				        <!-- 조사자보정 S -->	   
				        <tr>
				            <th colspan="5"><spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.title"/></th>
				            <td colspan="4"><c:out value="${result.svycrctn }"/></td>
							<td>
								<fmt:formatNumber type="number" value="${result.svycrctnscore }" var="svycrctnscore"/>
								<c:out value="${result.svycrctnscore }"/>
							</td>
				        </tr>
				        <!-- 조사자보정 E -->
				        <!-- 합계 S -->
					    <tr>
					    	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.scoreSum"/></th>
					    	<td colspan="5">
					    		<c:set var="scoreSum" value="${ orginldamcrkscore + orginldamdmgscore + orginldamwtgatescore + orginldamscore42 + orginldambasicscourscore + sidewallcrkscore + sidewallbasicscourscore + sidewalldmgscore + dwnsptcrkscore + dwnsptbasicscourscore + dwnsptdmgscore + svycrctnscore}"/>
					    		<c:out value="${scoreSum }"></c:out>
					    	</td>
					    </tr>
				        <!-- 합계 E -->
				        <!-- 비파괴 검사 S-->
				        <tr>
				        	<th rowspan="2"><spring:message code="sysFckPcs.svyComptVO.ndt.title"/></th>
				        	<th colspan="4"><spring:message code="sysFckPcs.svyComptVO.ndt.defects3"/></th>
				        	<td colspan="4"><c:out value="${result.cncrtcmprsstrn }"/></td>
				        	<td>
				        	<spring:message code="sysFckPcs.svyComptVO.ndt.defects3.se1" var="ndtDefs3Se1"/>
				        	<spring:message code="sysFckPcs.svyComptVO.ndt.defects3.se2" var="ndtDefs3Se2"/>
				        	<c:choose>
				        	<c:when test="${cncrtcmprsstrn eq ndtDefs3Se1 }"><spring:message code="sysFckPcs.svyComptVO.ndt.defects3.val1"/></c:when>
				        	<c:otherwise><spring:message code="sysFckPcs.svyComptVO.ndt.defects3.val2"/></c:otherwise>
				        	</c:choose>
				        	</td>
				        </tr>	        
				        <tr>
				        	<th colspan="4"><spring:message code="sysFckPcs.svyComptVO.ndt.defects5"/></th>
				        	<td colspan="4"><c:out value="${result.cncrtcrkdpt }"/></td>
				        	<td>
				        	<spring:message code="sysFckPcs.svyComptVO.ndt.defects5.se1" var="ndtDefs5Se1"/>
				        	<spring:message code="sysFckPcs.svyComptVO.ndt.defects5.se2" var="ndtDefs5Se2"/>
				        	<c:choose>
				        	<c:when test="${cncrtcrkdpt eq ndtDefs3Se1 }"><spring:message code="sysFckPcs.svyComptVO.ndt.defects5.val1"/></c:when>
				        	<c:otherwise><spring:message code="sysFckPcs.svyComptVO.ndt.defects5.val2"/></c:otherwise>
				        	</c:choose>
				        	</td>
				        </tr>	        
				        <!-- 비파괴 검사 E-->
					</tbody>
				</table>
				<div class="mainMenu">□ 사방댐 준설 평가표</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckPcs.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
				    <colgroup>
				  		<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
				  	</colgroup>
				    <thead>
				        <tr>
				            <th class="subMenu" rowspan="2" colspan="2"><spring:message code="sysFckPcs.svyComptVO.factor"/></th> <!-- 인자 -->
				            <th class="subMenu" colspan="3"><spring:message code="sysFckPcs.svyComptVO.actualFactor"/></th> <!-- 인자실측치 -->		      
				            <th class="subMenu" rowspan="2" colspan="2"><spring:message code="sysFckPcs.svyComptVO.wghval"/></th> <!-- 가중치 -->
				            <th class="subMenu" rowspan="2" colspan="2"><spring:message code="sysFckPcs.svyComptVO.dredgeval"/></th> <!-- 준설기준값 -->
				            <th class="subMenu" rowspan="2"><spring:message code="sysFckPcs.svyComptVO.dmgSttusNote"/></th> <!-- 비고 -->      				            
				        </tr>
				        <tr>
				            <th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.seStdr"/></th> <!-- 구분기준 -->
				            <th class="subMenu"><spring:message code="sysFckPcs.svyComptVO.cffcnt"/></th> <!-- 계수 -->
				        </tr>
				    </thead>					
					<tbody>
						 <!-- 현재저사량 S-->
				       	<tr>					        
							<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal"/></th>
				            <td colspan="2"><c:out value="${result.nowsnddpsitvaldivision }"/></td>
							<td><c:out value="${result.nowsnddpsitvalcoeff }"/></td>			
				            <td colspan="2"><c:out value="${result.nowsnddpsitvalweight }"/></td>
				            <td colspan="2"><c:out value="${result.nowsnddpsitvaldrdgn }"/></td>
							<td>--</td>
				        </tr>				        
				        <!-- 현재저사량 E-->	
				        <!-- 생활권 S-->
				       	<tr>					        
							<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.urbanErosion"/></th>
				            <td colspan="2"><c:out value="${result.lifezonedivision }"/></td>
							<td><c:out value="${result.lifezonecoeff }"/></td>			
				            <td colspan="2"><c:out value="${result.lifezoneweight }"/></td>
				            <td colspan="2"><c:out value="${result.lifezonedrdgn }"/></td>
							<td>--</td>
				        </tr>						        
				        <!-- 생활권 E-->	
				        <!-- 계상기울기 S-->
				        <tr>	
				        	<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.calculationSlope"/></th>
				            <td colspan="2"><c:out value="${result.riverbedgradientdivision }"/></td>
							<td><c:out value="${result.riverbedgradientcoeff }"/></td>			
				            <td colspan="2"><c:out value="${result.riverbedgradientweight }"/></td>
				            <td colspan="2"><c:out value="${result.riverbedgradientdrdgn }"/></td>
							<td>--</td>
				        </tr>				        
				        <!-- 계상기울기 E-->	
				        <!-- 토석이동량 S-->
				        <tr>			
				        	<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.soilMovement"/></th>
				            <td colspan="2"><c:out value="${result.soilmoveamntdivision }"/></td>
							<td><c:out value="${result.soilmoveamntcoeff }"/></td>			
				            <td colspan="2"><c:out value="${result.soilmoveamntweight }"/></td>
				            <td colspan="2"><c:out value="${result.soilmoveamntdrdgn }"/></td>
							<td>--</td>		        
				        </tr>				        
				        <!-- 토석이동량 E-->	
				        <tr>
				        	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.totalScore"/></th><!-- 총점 -->
				        	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.ecnrKndDredge"/></th><!-- 준설여부 판정 -->
				        	
				        </tr>
				        <tr>
				        	<td colspan="5"><c:out value="${result.totalscore }"/><input type="hidden" value="<c:out value="${result.totalscore }"/>" readonly="readonly"/></td>
				        	<td colspan="5"><c:out value="${result.drdgnatjdg }"/><input type="hidden" value="<c:out value="${result.drdgnatjdg }"/>" readonly="readonly"/></td>
				        </tr>
					</tbody>
				</table>
				
				<!-- 
				<div class="mainMenu">□ 조치 필요 사항</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="sysFckPcs.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
				    <colgroup>
				  		<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
				  	</colgroup>
				  	<thead>
				  		<tr>
				  			<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.se"/></th> 구분
				  			<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.damageDetail"/></th> 손상내용
				  			<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.reinforceMethod"/></th> 보수보강 공법
				  			<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.Volume"/></th> 물량
				  			<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.dmgSttusNote"/></th> 비고
				  		</tr>
				  	</thead>
				  	<tbody>
				  		<tr>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  		</tr>
				  		<tr>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  		</tr>
				  		<tr>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  			<td colspan="2"></td>
				  		</tr>
				  	</tbody>
				</table>
				 -->
				 
				<div class="mainMenu">□ 정밀점검 종합결과</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="sysFckPcs.svyComptList.bscDetail" /> <spring:message code="title.detail" /></caption>
				    <thead>
				        <tr>
				            <th class="subMenu" colspan="10"  rowspan="20"><spring:message code="sysFckPcs.svyComptVO.opinion"/></th><!-- 종합의견 -->	      
				        </tr>
				    </thead>
				    <tr>
				   		<td colspan="10">
				   		<p><c:out value="${result.opinion1 }"/></p>
				   		<p><c:out value="${result.opinion2 }"/></p>
				   		<p><c:out value="${result.opinion3 }"/></p>
				   		<p><c:out value="${result.opinion4 }"/></p>
				   		<p><c:out value="${result.opinion5 }"/></p>
				   		</td>	
				    </tr>
				    <!-- 점검결과 및 점수 S -->
				    <tr>
				    	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.gnrlzScore"/></th>
				        <th colspan="5"><spring:message code="sysFckPcs.svyComptVO.lastFckRsltGrde"/></th>
				    </tr>
				    <tr>
				        <td colspan="5"></td>
				        <td colspan="5"></td>
					</tr>
				    <tr>
				    	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.finGrade.title"/></th>
				        <td colspan="5">
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.se1" var="finGradeSe1"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.se2" var="finGradeSe2"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.se3" var="finGradeSe3"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.se4" var="finGradeSe4"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.se5" var="finGradeSe5"/>
					   		<c:choose>
				           	<c:when test="${result.cncrtcmprsstrn eq finGradeSe1}"><spring:message code="sysFckPcs.svyComptVO.finGrade.val1"/></c:when>
				           	<c:when test="${result.cncrtcmprsstrn eq finGradeSe2}"><spring:message code="sysFckPcs.svyComptVO.finGrade.val2"/></c:when>
				           	<c:when test="${result.cncrtcmprsstrn eq finGradeSe3}"><spring:message code="sysFckPcs.svyComptVO.finGrade.val3"/></c:when>
				           	<c:when test="${result.cncrtcmprsstrn eq finGradeSe4}"><spring:message code="sysFckPcs.svyComptVO.finGrade.val4"/></c:when>
				        	<c:otherwise><spring:message code="sysFckPcs.svyComptVO.finGrade.val5"/></c:otherwise>
				     		</c:choose>
				        </td>
				     </tr>
				        <!-- 점검결과 및 점수 E -->
				</table>	
				
				<!-- 압축강도 및 균열깊이 시험 S-->
				<div class="mainMenu"></div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" class="testTbl">
				<colgroup>
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				</colgroup>
				<thead>
				<th colspan="14">□ <spring:message code="sysFckPcs.svyComptVO.cmprsIn"/></th>
				</thead>
				<tbody>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.cmprsstrncncrt_1_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_1_photo}"/>
						</c:when>
						<c:otherwise>
							<c:set var="noImage" value="true"/>
							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
						</c:otherwise>
						</c:choose>						
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
						<td rowspan="20" class="fontFocus"><spring:message code="sysFckPcs.svyComptVO.selectMinMsg"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${cmprsstrArr.cmprsstrnavg1 }"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.cmprsstrncncrt_2_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_2_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${cmprsstrArr.cmprsstrnavg2 }"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.cmprsstrncncrt_3_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_3_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${cmprsstrArr.cmprsstrnavg3 }"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.cmprsstrncncrt_4_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_4_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${cmprsstrArr.cmprsstrnavg4 }"/></td>
					</tr>
				</tbody>
				</table>

				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" class="testTbl">
				<colgroup>
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				<col width="7.14%">
				</colgroup>
				<thead>
				<th colspan="14">□ <spring:message code="sysFckPcs.svyComptVO.ultrsnd"/></th>
				</thead>
				<tbody>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.crkdptcncrt_1_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_1_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
						<td rowspan="20" class="fontFocus"><spring:message code="sysFckPcs.svyComptVO.selectMaxMsg"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${crkdptArr.crkdptavg1 }"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.crkdptcncrt_2_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_2_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${crkdptArr.crkdptavg2 }"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.crkdptcncrt_3_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_3_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${crkdptArr.crkdptavg3 }"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }" varStatus="status">
<%-- 						<c:choose> --%>
<%-- 						<c:when test="${not empty items.crkdptcncrt_4_photo}"> --%>
<%-- 							<c:set var="noImage" value="false"/> --%>
<%-- 							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_4_photo}"/> --%>
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<c:set var="noImage" value="true"/> --%>
<%-- 							<c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/> --%>
<%-- 						</c:otherwise> --%>
<%-- 						</c:choose>						 --%>
						<td rowspan="5" colspan="3">
						<div>
							<img width="90%" height="auto" src="<c:url value='${photoUrl}'/>" alt="<c:out value="${status.count}"/>번 시험위치 사진" onerror="this.remove ? this.remove() : this.removeNode();">
						</div>
						</td>
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2">&#9312;&nbsp;<span><c:out value="${items.testVal01 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2">&#9313;&nbsp;<span><c:out value="${items.testVal02 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2">&#9314;&nbsp;<span><c:out value="${items.testVal03 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2">&#9315;&nbsp;<span><c:out value="${items.testVal04 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2">&#9316;&nbsp;<span><c:out value="${items.testVal05 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2">&#9317;&nbsp;<span><c:out value="${items.testVal06 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2">&#9318;&nbsp;<span><c:out value="${items.testVal07 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2">&#9319;&nbsp;<span><c:out value="${items.testVal08 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2">&#9320;&nbsp;<span><c:out value="${items.testVal09 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2">&#9321;&nbsp;<span><c:out value="${items.testVal10 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2">&#9322;&nbsp;<span><c:out value="${items.testVal11 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2">&#9323;&nbsp;<span><c:out value="${items.testVal12 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2">&#9324;&nbsp;<span><c:out value="${items.testVal13 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2">&#9325;&nbsp;<span><c:out value="${items.testVal14 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2">&#9326;&nbsp;<span><c:out value="${items.testVal15 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2">&#9327;&nbsp;<span><c:out value="${items.testVal16 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2">&#9328;&nbsp;<span><c:out value="${items.testVal17 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2">&#9329;&nbsp;<span><c:out value="${items.testVal18 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2">&#9330;&nbsp;<span><c:out value="${items.testVal19 }"/></span></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2">&#9331;&nbsp;<span><c:out value="${items.testVal20 }"/></span></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><c:out value="${crkdptArr.crkdptavg4 }"/></td>
					</tr>
				</tbody>
				</table>
				<!-- 압축강도 및 균열깊이 시험 E-->
				<br>
				<div class="mainMenu">□ 파일목록</div>
				<div>
					<table id="svyFileTable">
						<colgroup>
							<col width="5%;">
							<col width="40%;">
							<col width="15%;">
							<col width="10%;">
							<col width="20%;">
							<col width="10%;">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>파일명</th>
								<th>파일크기</th>
								<th>작성자</th>
								<th>등록일자</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${fn:length(pcsComptFileList) == 0}">
								<tr>
									<td colspan="6"><spring:message code="common.nodata.msg" /></td>
								</tr>
							</c:if>	
							<c:forEach items="${pcsComptFileList}" var="fileInfo" varStatus="status">
								<tr class="center">
									<td>${status.count }</td>
									<td><a href="javascript:void(0);" onclick="javascript:fnFileDownload('${fileInfo.gid }','${fileInfo.sn }'); return false;">${fileInfo.fileOrginlNm }</a></td>
									<td>${fileInfo.fileSize }</td>
									<td>${fileInfo.fileWrter }</td>
									<td>${fileInfo.createDt }</td>
									<td><button class="download-btn-m" onclick="javascript:fnFileDownload('${fileInfo.gid }','${fileInfo.sn }'); return false;"></button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>		
				<div class="BtnGroup">
					<div class="BtnRightArea">
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_Pcs','ROLE_USER_CNRS','ROLE_USER')"> --%>
<%-- 								<c:if test="${result.loginId eq userInfo.id or result.loginId eq userInfo.name or fn:contains(result.svyUser,userInfo.name)}"> --%>
						<button type="button" class="del-btn" onclick="javascript:fncDeletePcsCompt(); return false;"><spring:message code="title.delete" /></button>
						<button type="button" class="modify-btn" onclick="javascript:fncUpdatePcsComptView(); return false;"><spring:message code="title.update" /></button>
<%-- 								</c:if> --%>
<%-- 							</sec:authorize> --%>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>