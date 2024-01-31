<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssLcp.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">땅밀림실태조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail"/></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/lcp/sct/updateLcpSvyComptView.do" method="post">
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
			<input name="schlastgrd" type="hidden" value="<c:out value='${searchVO.lastgrd}'/>">
			<div class="LcpBoardDetail">

			<!-- 조사ID -->
			<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<colgroup>
					<col width="15%">
					<col width="17%">
					<col width="15%">
					<col width="17%">
					<col width="15%">
					<col width="17%">
				</colgroup>
				<caption class="Hidden">${pageTitle} <spring:message code="sysLssLcp.svyComptList.svyDetail" /> <spring:message code="title.detail" /></caption>
				<tr>
					<th><spring:message code="sysLssLcp.stripLandVO.svyId" /></th>
					<td><c:out value="${result.svyId}"/></td>
					<th><spring:message code="sysLssLcp.svyComptVO.svyDt" /></th>
					<td><c:out value="${result.svyDt}"/></td>
					<th><spring:message code="sysLssLcp.svyComptVO.svyUser" /></th>
					<td><c:out value="${result.svyUser}"/></td>
				</tr>
			    </table>
				<br>
				<!-- 일반사항 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptList.gnrlMatter" /></p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
						<col width="15%">
						<col width="50%">
						<col width="15%">
						<col width="20%">
					</colgroup>
				   <tr>
					   <th><spring:message code="sysLssLcp.svyComptList.svyPlace" /></th>
					   <td colspan="3"><c:out value="${result.svyAddr }"/></td>
				   </tr>
				   <tr>
					   <th>GPS 좌표</th>
					   <td><c:out value="${result.svyLatLon}"/></td>
					   <th><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
					   <td><c:out value="${result.pz}"/>m</td>
				   </tr>
				</table>
				<br>
				<!-- 지질특성 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.geologyChartr" /></p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
					<col width="10%">         	
					<col width="10%">         	
					<col width="10%">         	
					<col width="10%">         	
					<col width="10%">         	
					<col width="10%">         	
					<col width="10%">         	
					</colgroup>
					<tr class="txtAlg-ct">
						<th><spring:message code="sysLssLcp.svyComptVO.cmprok" /></th>
						<td colspan="2"><c:out value="${result.cmprokval}"/></td> <!-- 주 구성암석 -->
						<th colspan="2"><spring:message code="sysLssLcp.svyComptVO.instrokat" /></th>
						<td colspan="2"><c:out value="${result.instrokat}"/></td>
					</tr>
					<tr class="txtAlg-ct">
						<th><spring:message code="sysLssLcp.svyComptVO.rokwthr" /></th>
						<td><c:out value="${result.rokwthrval}"/></td>
						<th><spring:message code="sysLssLcp.svyComptVO.geology" /></th>
						<th><spring:message code="sysLssLcp.svyComptVO.geologyflt" /></th>
						<td><c:out value="${result.geologyflt}"/></td>
						<th><spring:message code="sysLssLcp.svyComptVO.geologyfld" /></th>
						<td><c:out value="${result.geologyfld}"/></td>
					</tr>
					</table>
				<!-- 지질특성 추가 -->
				<table class="svyCompt" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<colgroup>
							<col width="18%">
							<col width="18%">
							<col width="18%">
							<col width="12%">
							<col width="17%">
							<col width="17%">
						</colgroup>
					<tr>
						<th colspan="3"><spring:message code="sysLssLcp.svyComptVO.disctnupln" /></th>
						<th><spring:message code="sysLssLcp.svyComptVO.disctnuplndrcno" /></th>
						<th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.disctnuplnslpval" /></th>
						<th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.disctnuplnintvlval" /></th>
					</tr>
					<tr class="txtAlg-ct">
						<th>1.(<c:out value="${result.disctnupln1}"/>)</th>
						<th>2.(<c:out value="${result.disctnupln2}"/>)</th>
						<th>3.(<c:out value="${result.disctnupln3}"/>)</th>
						<td rowspan="2"><c:out value="${result.disctnuplndrcno}"/></td>
						<td rowspan="2"><c:out value="${result.disctnuplnslpval}"/></td>
						<td rowspan="2"><c:out value="${result.disctnuplnintvlval}"/></td>
					</tr>
					<tr class="txtAlg-ct">
						<td><c:out value="${result.disctnupln1_strk}"/> / <c:out value="${result.disctnupln1_dip}"/></td>
						<td><c:out value="${result.disctnupln2_strk}"/> / <c:out value="${result.disctnupln2_dip}"/></td>
						<td><c:out value="${result.disctnupln3_strk}"/> / <c:out value="${result.disctnupln3_dip}"/></td>
					</tr>
				</table>
				<br>
         <!-- 토양특성 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.soilChartr" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
         		<col width="15%">
         		<col width="15%">
         		<col width="15%">
         		<col width="10%">
         		<col width="15%">
         		<col width="10%">
         		<col width="10%">
         		<col width="10%">
         	</colgroup>
			<tr class="txtAlg-ct">
                <th><spring:message code="sysLssLcp.svyComptVO.soilty" /></th>
				<td><c:out value="${result.soilty}"/></td>
				<th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.soildeptb" /></th>
			    <td><c:out value="${result.soildeptbval}"/></td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.soilclassb30" /></c:set>
                <th colspan="2"><spring:message code="sysLssLcp.svyComptVO.soilclassb30" /></th><!-- 30cm부근 -->
                <td colspan="2"><c:out value="${result.soilclassbval}"/></td>
			</tr>
			<tr class="txtAlg-ct">
                 <th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.soilclassb" /></th>
                 <td><c:out value="${result.soilstrct}"/></td>
                 <th><spring:message code="sysLssLcp.svyComptVO.soilwtr" /></th>
                 <td><c:out value="${result.soilwtrval}"/></td>
                 <th><spring:message code="sysLssLcp.svyComptVO.rokexpsr" /></th>
                 <td><spring:message code="sysLssLcp.svyComptVO.talusat" /></td>
                 <th><spring:message code="sysLssLcp.svyComptVO.talusat" /></th>
                 <td><c:out value="${result.talusat}"/></td>
             </tr>
         </table>
         <br>
		          <!-- 지형특성 -->
				  <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartr" /></p></div>
				  <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					  <tr class="txtAlg-ct">
						 <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartrSe" /></th>
						 <th><spring:message code="sysLssLcp.svyComptVO.mxhg" /></th>
						 <td colspan="2"><c:out value="${result.mxhg}"/>m</td>
						 <th colspan="2" rowspan="2"><spring:message code="sysLssLcp.svyComptVO.arealcval" /></th><!-- 조사지역 위치 -->
						 <th colspan="2"><c:out value="${result.arealcridge}"/><spring:message code="sysLssLcp.svyComptVO.arealcridge" /></th>
					  </tr>
					  <tr class="txtAlg-ct">
						 <td colspan="3"><c:out value="${result.tpgrphval}"/></td>
						 <td colspan="2"><c:out value="${result.arealcval}"/></td>
					  </tr>
					  </table>
					  <table class="svyCompt" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						  <colgroup>
							  <col width="16%">
							  <col width="18%">
							  <col width="15%">
							  <col width="18%">
							  <col width="15%">
							  <col width="17%">
						  </colgroup>
					  <tr class="txtAlg-ct">
						 <th><spring:message code="sysLssLcp.svyComptVO.plnformval" /></th>
						 <td class="plnform"><c:out value="${result.plnformval}"/></td>
						 <th><spring:message code="sysLssLcp.svyComptVO.lngformval" /></th>
						 <td class="lngform"><c:out value="${result.lngformval}"/></td>
						 <th><spring:message code="sysLssLcp.svyComptVO.slprngavrg" /></th>
						 <td><c:out value="${result.slprngval}"/></td>
					  </tr>
				  </table>
				  <br>
				<!-- 수리특성 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.hydrlChartr" /></p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tr>
						<th><spring:message code="sysLssLcp.svyComptVO.ugrwtrPosblty" /></th>
						<th><spring:message code="sysLssLcp.svyComptVO.dwstrmAt" /></th>
						<th><spring:message code="sysLssLcp.svyComptVO.sprgAt" /></th>
					</tr>
					<tr class="txtAlg-ct">
						<td><c:out value="${result.ugrwtr_posblty}"/></td>
						<td><c:out value="${result.dwstrm_at}"/></td>
						<td><c:out value="${result.sprg_at}"/></td>
					</tr>
				</table>
				<br>
				<!-- 산림특성 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.mtstChartr" /></p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
						<col width="15%">
						<col width="20%">
					</colgroup>
				<tr>
					<th><spring:message code="sysLssLcp.svyComptVO.frstfrval" /></th>
					<td class="txtAlg-ct"><c:out value="${result.frstfrval}"/></td>
					<th><spring:message code="sysLssLcp.svyComptVO.maintreeknd" /></th>
					<td><c:out value="${result.maintreeknd}"/></td>
				</tr>
				</table>
				<br>
				<!-- 기타 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.svyEtc" /></p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
						<col width="35%">
					</colgroup>
					<tr>
						<th><spring:message code="sysLssLcp.svyComptVO.frlndsttus" /></th>
						<td><c:out value="${result.frlndsttus}"/></td>
						<th><spring:message code="sysLssLcp.svyComptVO.lwbndlwfrlndsttus" /></th>
						<td><c:out value="${result.lwbndlwfrlndsttus}"/></td>
					</tr>
				</table>
				<br>
				<!-- 땅밀림 징후 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.lcpSttus" /></p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tr> 
						<th><spring:message code="sysLssLcp.svyComptVO.symptmAt" /></th>
						<td class="txtAlg-ct"><c:out value="${result.symptmoccur}"/></td>
						<th colspan="4" class="line-ht20">GPS 좌표 <br>(MAP DATUM : WGS84)</th>
						<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
						<th rowspan="2" style="word-spacing: -10px;"><spring:message code="sysLssLcp.svyComptVO.scale" /></th>
					</tr>
					<tr>
						<th>유형</th>
						<th>유/무</th>
						<th colspan="2"><spring:message code="sysLssLcp.svyComptVO.lat" /></th>
						<th colspan="2"><spring:message code="sysLssLcp.svyComptVO.lon" /></th>
					</tr>
					<c:if test="${lcpsttus_crk_result.size() > 0 }">
						<c:forEach items="${lcpsttus_crk_result}" var="item" varStatus="status"> 
						<tr class="center">
							<td rowspan="2"><spring:message code="sysLssLcp.svyComptVO.lcpsttus_crk"></spring:message></td>
							<td rowspan="2">유</td>
							<td colspan="2"><c:out value="${item.bpx}"/></td>
							<td colspan="2"><c:out value="${item.bpy}"/></td>
							<td><c:out value="${item.bpz}"/></td>
							<td rowspan="2">
								<c:if test="${not empty item.height}"><c:out value="${item.height }"/></c:if>
								<c:if test="${not empty item.length}"> X <c:out value="${item.length }"/></c:if>
								<c:if test="${not empty item.depth}"> X <c:out value="${item.depth }"/></c:if>
							</td>
						</tr>
						<tr class="center">
				         	<td colspan="2"><c:out value="${item.epx}"/></td>
							<td colspan="2"><c:out value="${item.epy}"/></td>
			            	<td><c:out value="${item.epz}"/></td>
				        </tr>
						</c:forEach>
					</c:if>
					<c:if test="${lcpsttus_stp_result.size() > 0 }">
						<c:forEach items="${lcpsttus_stp_result}" var="item" varStatus="status"> 
						<tr class="center">
							<td rowspan="2"><spring:message code="sysLssLcp.svyComptVO.lcpsttus_stp"></spring:message></td>
							<td rowspan="2">유</td>
							<td colspan="2"><c:out value="${item.bpx}"/></td>
							<td colspan="2"><c:out value="${item.bpy}"/></td>
							<td><c:out value="${item.bpz}"/></td>
							<td rowspan="2">
								<c:if test="${not empty item.height}"><c:out value="${item.height }"/></c:if>
								<c:if test="${not empty item.length}"> X <c:out value="${item.length }"/></c:if>
								<c:if test="${not empty item.depth}"> X <c:out value="${item.depth }"/></c:if>
							</td>
						</tr>
						<tr class="center">
				         	<td colspan="2"><c:out value="${item.epx}"/></td>
							<td colspan="2"><c:out value="${item.epy}"/></td>
			            	<td><c:out value="${item.epz}"/></td>
				        </tr>
						</c:forEach>
					</c:if>
					<c:if test="${lcpsttus_wdpt_result.size() > 0 }">
						<c:forEach items="${lcpsttus_wdpt_result}" var="item" varStatus="status"> 
						<tr class="center">
							<td><spring:message code="sysLssLcp.svyComptVO.lcpsttus_wdpt"></spring:message></td>
							<td>유</td>
							<td colspan="2"><c:out value="${item.bpx}"/></td>
							<td colspan="2"><c:out value="${item.bpy}"/></td>
							<td><c:out value="${item.bpz}"/></td>
							<td>
								<c:if test="${not empty item.height}"><c:out value="${item.height }"/></c:if>
								<c:if test="${not empty item.length}"> X <c:out value="${item.length }"/></c:if>
								<c:if test="${not empty item.depth}"> X <c:out value="${item.depth }"/></c:if>
							</td>
						</tr>
						</c:forEach>
					</c:if>
					<c:if test="${lcpsttus_strct_result.size() > 0 }">
						<c:forEach items="${lcpsttus_strct_result}" var="item" varStatus="status"> 
						<tr class="center">
							<td><spring:message code="sysLssLcp.svyComptVO.lcpsttus_strct"></spring:message></td>
							<td>유</td>
							<td colspan="2"><c:out value="${item.bpx}"/></td>
							<td colspan="2"><c:out value="${item.bpy}"/></td>
							<td><c:out value="${item.bpz}"/></td>
							<td>
								<c:if test="${not empty item.height}"><c:out value="${item.height }"/></c:if>
								<c:if test="${not empty item.length}"> X <c:out value="${item.length }"/></c:if>
								<c:if test="${not empty item.depth}"> X <c:out value="${item.depth }"/></c:if>
							</td>
						</tr>
						</c:forEach>
					</c:if>
					<c:if test="${lcpsttus_ugrwtr_result.size() > 0 }">
						<c:forEach items="${lcpsttus_ugrwtr_result}" var="item" varStatus="status"> 
						<tr class="center">
							<td><spring:message code="sysLssLcp.svyComptVO.lcpsttus_ugrwtr"></spring:message></td>
							<td>유</td>
							<td colspan="2"><c:out value="${item.bpx}"/></td>
							<td colspan="2"><c:out value="${item.bpy}"/></td>
							<td><c:out value="${item.bpz}"/></td>
							<td>
								<c:if test="${not empty item.height}"><c:out value="${item.height }"/></c:if>
								<c:if test="${not empty item.length}"> X <c:out value="${item.length }"/></c:if>
								<c:if test="${not empty item.depth}"> X <c:out value="${item.depth }"/></c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				</table>
				<br>
				<!-- 종합의견 -->
				<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p">종합의견</p></div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<tr>
						<th class="opinion"><spring:message code="sysLssLcp.svyComptVO.opinion1" /></th>
						<td colspan="8"><c:out value="${result.opinion1}" escapeXml="false"/></td>
					</tr>
					<tr>
						<th class="opinion"><spring:message code="sysLssLcp.svyComptVO.opinion2" /></th>
						<td colspan="8"><c:out value="${result.opinion2}" escapeXml="false"/></td>
					</tr>
					<tr>
						<th class="opinion"><spring:message code="sysLssLcp.svyComptVO.opinion3" /></th>
						<td colspan="8"><c:out value="${result.opinion3}" escapeXml="false"/></td>
					</tr>
					<tr>
						<th class="opinion"><spring:message code="sysLssLcp.svyComptVO.opinion4" /></th>
						<td colspan="8"><c:out value="${result.opinion4}" escapeXml="false"/></td>
					</tr>
					<tr>
						<th class="opinion"><spring:message code="sysLssLcp.svyComptVO.opinion5" /></th>
						<td colspan="8"><c:out value="${result.opinion5}" escapeXml="false"/></td>
					</tr>
				</tbody>
				</table>
				<br>
				<table class="svyComptGrade brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				    <thead>
				      <tr>
				        <th colspan="8"><spring:message code="sysLssLcp.svyComptVO.lcpJudge"/></th>        
				      </tr>
				      <tr>
	                    <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.indexNo"/></th>
	                    <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.cause"/></th>
	                    <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.point"/></th>
	                    <th colspan="5"><spring:message code="sysLssLcp.svyComptVO.category"/></th>
				      </tr>
	                  <tr>
	                   <c:forEach var="j" begin="1" end="5">
	                         <th><c:out value="${j}"/></th>
	                     </c:forEach>
	                  </tr>
				    </thead>
				    <tbody>
				      <tr>
				        <th rowspan="2">1</th> 
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.dirsymptmval"/></th>
						<th><c:out value="${result.dirsymptmval}"/></th>
		                <c:forEach var="existence" items="${existenceCodeList}" varStatus="status">
		                    <td><c:out value="${existence.codeDc }"/></td>   
		               	</c:forEach>
						<td colspan="3"></td>
				      </tr>
				      <tr class="point">
				     	<th><c:out value="${result.dirsymptmscore}"/></th>
						<td>22</td>
						<td>0</td>
				        <td colspan="3"></td>
				      </tr>
				      <tr>
				        <th rowspan="2">2</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.indirsymptmval"/></th>
   				     	<th><c:out value="${result.indirsymptmval}"/></th>
		                <c:forEach var="existence" items="${existenceCodeList}" varStatus="status">
		                    <td><c:out value="${existence.codeDc }"/></td>   
		               	</c:forEach>
				        <td colspan="3"></td>
				      </tr>
				      <tr class="point">
				        <th><c:out value="${result.indirsymptmscore}"/></th>
						<td>14</td>
						<td>0</td>
				        <td colspan="3"></td>
				      </tr>
				      <tr>
				        <th rowspan="2">3</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.prntRck"/></th>
				        <th><c:out value="${result.cmprokval}"/></th>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpSedimentary"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpMetamorphic"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpVolcanic"/></td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.cmprokscore}"/></th>
				        <td>8</td>
				        <td>5</td>
				        <td>2</td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">4</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.rokwthr" /></th>
				        <th><c:out value="${result.rokwthrval}"/></th>
				        <c:forEach var="rokwthrval" items="${rokwthrvalCodeList}" varStatus="status">
    						<td><c:out value="${rokwthrval.codeDc }"/></td>	
						</c:forEach>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.rokwthrscore}"/></th>
				        <td>7</td>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">5</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.disctnuplnslpval"/></th>
				        <th><c:out value="${result.disctnuplnslpval}"/></th>
				        <c:forEach var="disctnuplnslpval" items="${disctnuplnslpvalCodeList}" varStatus="status">
    						<td><c:out value="${disctnuplnslpval.codeDc }"/></td>	
						</c:forEach>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.disctnuplnslpscore}"/></th>
				        <td>9</td>
				        <td>5</td>
				        <td>4</td>
				        <td>3</td>
				        <td>0</td>
				      </tr>
				      <tr>
				        <th rowspan="2">6</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.disctnuplnintvlval"/></th>
				        <th><c:out value="${result.disctnuplnintvlval}"/></th>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpBigDense"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpDense"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpNormal"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpAbroad"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpNone"/></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.disctnuplnintvlscore}"/></th>
				        <td>5</td>
				        <td>4</td>
				        <td>2</td>
				        <td>1</td>
				        <td>0</td>
				      </tr>
				      <tr>
				        <th rowspan="2">7</th>
				        <th rowspan="2">토성</th>
				        <th><c:out value="${result.soilclassbval}"/></th>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpClaysoil"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpSandyloam"/></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpLoam"/></td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.soilclassbscore}"/></th>
				        <td>5</td>
				        <td>3</td>
				        <td>2</td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">8</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.soilwtr" /></th>
				        <th><c:out value="${result.soilwtrval}"/></th>
				        <c:forEach var="soilwtrval" items="${soilwtrvalCodeList}" varStatus="status">
    						<td><c:out value="${soilwtrval.codeDc }"/></td>	
						</c:forEach>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.soilwtscore}"/></th>
				        <td>5</td>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td>1</td>
				      </tr>
				      <tr>
				        <th rowspan="2">9</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.talusat" /></th>
				        <th><c:out value="${result.talusat}"/></th>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpYes" /></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpNo" /></td>
				        <td></td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.talusatscore}"/></th>
				        <td>4</td>
				        <td>0</td>
				        <td></td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">10</th>
				        <th rowspan="2">이동대<br>(토심)깊이</th>
				        <th><c:out value="${result.soildeptbval}"/></th>
				        <c:forEach var="soildeptscore" items="${soildeptscoreCodeList}" varStatus="status">
    						<td><c:out value="${soildeptscore.codeDc }"/></td>	
						</c:forEach>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.soildeptbscore}"/></th>
				        <td>5</td>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">11</th>
				        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartrSe" /></th>
				        <th><c:out value="${result.tpgrphval}"/></th>
				        <c:forEach var="tpgrphval" items="${tpgrphvalCodeList}" varStatus="status">
    						<td><c:out value="${tpgrphval.codeDc }"/></td>	
						</c:forEach>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.tpgrphscore}"/></th>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td></td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">12</th>
				        <th rowspan="2">지형형태<br>(수평면형)</th>
				        <th><c:out value="${result.plnformval}"/></th>
				        <c:forEach var="formval" items="${formvalCodeList}" varStatus="status">
    						<td><c:out value="${formval.codeDc }"/></td>	
						</c:forEach>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.plnformscore}"/></th>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td>1</td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">13</th>
				        <th rowspan="2">지형형태<br>(종단면형)</th>
				        <th><c:out value ="${result.lngformval}"/></th>
				        <c:forEach var="formval" items="${formvalCodeList}" varStatus="status">
    						<td><c:out value="${formval.codeDc }"/></td>	
						</c:forEach>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value ="${result.lngformscore}"/></th>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td>1</td>
				        <td></td>
				      </tr>
				      <tr>
				        <th rowspan="2">14</th>
				        <th rowspan="2">사면경사</th>
				        <th><c:out value="${result.slprngval}"/></th>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegree2030" /></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegree1020" /></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegreeOver30" /></td>
				        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegreeUnder10" /></td>
				        <td></td>
				      </tr>
				      <tr class="point">
                        <th><c:out value="${result.slprngscore}"/></th>
				        <td>4</td>
				        <td>3</td>
				        <td>2</td>
				        <td>1</td>
				        <td></td>
				      </tr>
	                  <tr>
	                     <th colspan="2"><spring:message code="sysLssLcp.svyComptVO.judgePoint"/></th>
	                     <th colspan="2" id="sum"><c:out value="${result.lssfrsm}"/></th>
	                     <th colspan="2"><spring:message code="sysLssLcp.svyComptVO.judgeGrade"/></th>
	                     <th colspan="2" id="grade"><c:out value="${result.lssfrgrd}"/></th>
	                  </tr>
	                  <tr>
	                  	<th colspan="2">자문판정등급</th>
	                  	<th colspan="2"><c:out value="${result.cnsutgrd}"/></th>
	                  	<th colspan="2">최종판정등급</th>
	                  	<th colspan="2"><c:out value="${result.lastgrd}"/></th>
	                  </tr>
	                  <tr>
	                  	<th colspan="2">자문사유</th>
	                  	<td colspan="7"><c:out value="${result.cnsutresn}"/></td>
	                  </tr>
	                  <tr><th colspan="9">현장사진</th></tr>
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
									<div class="photoUrl"><img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">	</div>
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
						<c:if test="${result.sttusprnt != null}">
							<tr><th colspan="6">현황도</th></tr>
							<tr class="photoTr">
								 <td colspan="6"><img src="/storage/fieldBook<c:url value='${result.sttusprnt}'/>"  alt="현황도" onerror="this.remove ? this.remove() : this.removeNode();"></td>
							 </tr>
						</c:if>
					</tbody>
				</table>		
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_LCP','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<button type="button" class="del-btn" onclick="javascript:fncDeleteSvyCompt('<c:out value="${result.gid}"/>'); return false;"><spring:message code="title.delete" /></button>
							</sec:authorize>
							<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvyComptView(); return false;"><spring:message code="title.update" /></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_LCP','ROLE_SUB_ADMIN','ROLE_ADMIN')">
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_BSC','ROLE_USER_CNRS','ROLE_USER')"> --%>
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
