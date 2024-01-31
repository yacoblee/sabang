<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssBsc.svyComptList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역실태조사</a></li>
	</ul>
	<h3><spring:message code="sysLssCnl.svyComptList.update"/></h3><!-- 조사완료 상세조회 -->
	<div id="contents">
		<input type="hidden" name="photolist" value="<c:out value="${orginl_photo_result}"/>">
		<form:form id="listForm" commandName="result" action="${pageContext.request.contextPath}/sys/lss/wka/sct/updateWkaSvyCompt.do" method="post" enctype="multipart/form-data">
			<input name="photoTagList" type="hidden"  value="">			
			<input name="stabanalImg" type="hidden"  value="${stabanal_img_result[0]}">
			<input name="stabanalImg" type="hidden"  value="${stabanal_img_result[1]}">
			<input name="sn" type="hidden"  value="${result.sn}">
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			<input name="mstId" type="hidden" value="<c:out value="${result.mstId}" />">
			<input name="mstNm" type="hidden" value="<c:out value="${result.mstNm}" />">
			<input name="svyId" type="hidden" value="<c:out value="${result.svyId}" />">
			<input name="svyType" type="hidden" value="<c:out value="${result.svyType}"/>" />
			<input name="dglog" type="hidden" value="<c:out value="${result.dglog}"/>"/>
			<input name="dglogscore" type="hidden" value="<c:out value="${result.dglogscore}"/>"/>
			<input name="direffcsafe" type="hidden" value="${result.direffcsafe}" />
			<input name="direffcsafescore" type="hidden" value="${result.direffcscore}" />
			<input name="slopescore" type="hidden" value="<c:out value="${result.slopescore}"/>"/>
			<input name="dirhgscore" type="hidden" value="<c:out value="${result.dirhgscore}"/>"/>
			<input name="soildepscore" type="hidden" value="<c:out value="${result.soildepscore}"/>" />
			<input name="crossfrmscore" type="hidden" value="<c:out value="${result.crossfrmscore}"/>" />
			<input name="rockkndscore" type="hidden" value="<c:out value="${result.rockkndscore}"/>"/>
			<input name="crcksittn" type="hidden" value="<c:out value="${result.crcksittn}"/>"/>
			<input name="crcksittnscore" type="hidden" value="<c:out value="${result.crcksittnscore}"/>"/>
			<input name="lndslddgsttus" type="hidden" value="<c:out value="${result.lndslddgsttus}"/>"/>
			<input name="lndslddgsttusscore" type="hidden" value="<c:out value="${result.lndslddgscore}"/>"/>
			<input name="watersttus" type="hidden" value="<c:out value="${result.watersttus}"/>"/>
			<input name="watersttusscore" type="hidden" value="<c:out value="${result.watersttusscore}"/>"/>
			<input name="slidland" type="hidden" value="<c:out value="${result.slidland}"/>"/>
			<input name="slidlandscore" type="hidden" value="<c:out value="${result.sliplandscore}"/>"/>
			<input name="rootchar" type="hidden" value="<c:out value="${result.rootchar}"/>"/>
			<input name="rootcharscore" type="hidden" value="<c:out value="${result.rootcharscore}"/>"/>
			<input name="foreststtus" type="hidden" value="<c:out value="${result.foreststtus}"/>"/>
			<input name="foreststtusscore" type="hidden" value="<c:out value="${result.foreststtusscore}"/>"/>
			<input name="clps" type="hidden" value="<c:out value="${result.clps}"/>"/>
			<input name="clpsscore" type="hidden" value="<c:out value="${result.clpsscore}"/>"/>
			<input name="surfacedir" type="hidden" value="<c:out value="${result.surfacedir}"/>"/>
			<input name="surfacedirscore" type="hidden" value="<c:out value="${result.surfacescore}"/>"/>
			<input name="wtrsttus" type="hidden" value="<c:out value="${result.wtrsttus}"/>"/>
			<input name="wtrsttusscore" type="hidden" value="<c:out value="${result.wtrsttusscore}"/>"/>
			<input name="fieldsurveyscore" type="hidden" value="<c:out value="${result.fieldsurveyscore}"/>"/>			
			<input name="stabanalscore" type="hidden" value="<c:out value="${result.stabanalscore}"/>"/>			
			<input name="scoresum" type="hidden" value="<c:out value="${result.scoresum}"/>"/>
						
			<input name="mntntrntavg" type="hidden" value="<c:out value="${result.mntntrntavg}"/>"/>			
			<input name="mntntrntmin" type="hidden" value="<c:out value="${result.mntntrntmin}"/>"/>			
			<input name="mntntrntmax" type="hidden" value="<c:out value="${result.mntntrntmax}"/>"/>		
			<input name="mntntrntscore" type="hidden" value="<c:out value="${result.mntntrntscore}"/>"/>
			<input name="dgrareascore" type="hidden" value="<c:out value="${result.dgrareascore}"/>"/>
			<input name="corrosion" type="hidden" value="<c:out value="${result.corrosion}"/>"/>
			<input name="corrosionscore" type="hidden" value="<c:out value="${result.corrosionscore}"/>"/>
			<input name="bldrstne" type="hidden" value="<c:out value="${result.bldrstne}"/>"/>
			<input name="bldrstnescore" type="hidden" value="<c:out value="${result.bldrstnescore}"/>"/>
			<input name="soiltrace" type="hidden" value="<c:out value="${result.soiltrace}"/>"/>
			<input name="soiltracescore" type="hidden" value="<c:out value="${result.soiltracescore}"/>"/>
			
			<input name="etcdg" type="hidden" value="<c:out value="${result.etcdg}"/>"/>
			<input name="etcdgscore" type="hidden" value="<c:out value="${result.etcdgscore}"/>"/>
			
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
					<colgroup>
				  		<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
				  		<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
				  	</colgroup>
					<tbody>
					    <tr>
					        <th class="subMenu" rowspan="2">조사자</th>
					        <th>소속</th>
					        <td colspan="2"><c:out value="${result.svydept}"/></td>
					        <th>직급</th>
					        <td><c:out value="${result.syvofcps}"/></td>
					        <th>조사자</th>
					        <td><c:out value="${result.svyUser}"/></td>
					    </tr>
					    <tr>
					        <th>조사일자</th>
					        <td colspan="2"><c:out value="${result.svydt}"/></td>
					        <th>연락처</th>
					        <td colspan="3"><c:out value="${result.svymbtl}"/></td>
					    </tr>
					    <tr>
					        <th class="subMenu" rowspan="4">위치 및 환경</th>
					        <th>행정구역</th>
					        <td colspan="6">					        
								<c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/> <c:out value="${result.svyRi}"/> <c:out value="${result.svyJibun}"/>
				        	</td>
					    </tr>
					    <tr>
					        <th>관리주체</th>
					        <td colspan="6"><c:out value="${result.svyRegion1}"/> <c:out value="${result.svyRegion2}"/></td>
					    </tr>
					    <tr>
					        <th>GPS좌표 (유출구)</th>
					        <th>위도</th>
					        <td colspan="2"><c:out value="${result.lat}"/></td>
					        <th>경도</th>
					        <td colspan="2"><c:out value="${result.lon}"/></td>
					    </tr>
					    <tr>
					        <th>전자야장 좌표</th>
					        <th>위도</th>
					        <td colspan="2" <c:if test="${result.lat ne result.px }">class="tempA"</c:if>><c:out value="${result.px}"/></td>
					        <th>경도</th>
					        <td colspan="2" <c:if test="${result.lon ne result.py }">class="tempA"</c:if>><c:out value="${result.py}"/></td>
					    </tr>
					    <tr>
					        <th class="subMenu">조사지 환경</th>
					        <th>유역면적</th>
					        <td colspan="2"><c:out value="${result.dgrarea}"/>ha</td>
					        <th colspan="2">취약지역 면적</th>
					        <td colspan="2"><c:out value="${result.frgltyrenarea}"/>㎡</td>
					    </tr>
					    <tr class="gnrlSttus">
					        <th class="subMenu" rowspan="3">보호 대상</th>
					        <th>보호시설</th>
					        <td colspan="2">
					        	<select name="safefct">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI091' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.safefct}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
	                    		</select>
					        	( <input type="text" name="placelen" value="${result.placelen}"/> 개소 )</td>
					        <th colspan="2">인가 또는 호수</th>
					        <td colspan="2">
					        	<select name="house">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI092' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.house}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
	                    		</select>
					        	( <input type="text" name="lake" value="${result.lake}"/> 개소 )</td>
					    </tr>
					    <tr>
					        <th>상부 주요시설</th>
					        <td colspan="2">
			        			<select name="highmainfct">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI093' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.highmainfct}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
	                    		</select>
					        </td>
					        <th colspan="2" style="line-height: 25px;">상부 인가현황</th>
					        <td colspan="2">
					        	<select name="highhousesttus">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI094' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.highhousesttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
	                    		</select>
					        </td>
					    </tr>
					    <tr>
					    	<th style="line-height: 25px;">하부 주요시설</th>
					        <td colspan="2">
								<select name="lowmainfct">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI095' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.lowmainfct}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
	                    		</select>
					        </td>
					        <th colspan="2" style="line-height: 25px;">하부 인가현황</th>
					        <td colspan="2">
								<select name="lowhousesttus">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI097' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.lowhousesttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
	                    		</select>
					        </td>
					    </tr>
				  	</tbody>
		  		</table>
		  		<c:if test="${result.svyType eq '취약지역 실태조사(토석류)' }">
		  			<div class="mainMenu">□ 우려지역 계류현황</div>
					<table class="svyCompt debflow brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<colgroup>
					  		<col width="4%;">
					  		<col width="8%">
					  		<col width="8%">
					  		<col width="8%;">
					  		<col width="8%">
					  		<col width="8%">
					  		<col width="8%">
					  		<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
					  		<col width="8%">
							<col width="8%">
					  	</colgroup>
					    <tbody>
					        <tr>
					            <th class="subMenu" rowspan="5" style="writing-mode: vertical-lr;">기본정보</th>
					            <th colspan="2">황폐발생원</th>
					            <td colspan="4">
					            	<select name="scodsltn" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI133' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.scodsltn}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th colspan="2">계류경사</th>
					            <td colspan="4" class="mntntrnt"><c:out value="${result.mntntrntavg}"/>° / <c:out value="${result.mntntrntmin}"/>~<c:out value="${result.mntntrntmax}"/>°</td>
					        </tr>
					        <tr>
					            <th colspan="2">계류길이</th>
					            <td colspan="2"><input type="text" name="mntntrntlen" value="${result.mntntrntlen}"/>m</td>
					            <th colspan="2">변곡점 길이</th>
					            <td colspan="2"><input type="text" name="iftnpntlen" value="${result.iftnpntlen}"/>m</td>
					            <th colspan="2">변곡점 고도</th>
					            <td colspan="2"><input type="text" name="iftnpntevtn" value="${result.iftnpntevtn}"/>m</td>
					        </tr>
					        <tr>
					           <th colspan="2">임상</th>
						        <td colspan="2">
<%-- 						        <input type="text" name="frtp" value="${result.frtp}"/> --%>
						        	<select name="frtp">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI109' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.frtp}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th colspan="2">임분밀도</th>
						        <td colspan="2">
<%-- 						        <input type="text" name="stddnst" value="${result.stddnst}"/> --%>
						        	<select name="stddnst">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI110' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.stddnst}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th colspan="2">임분경급</th>
						        <td colspan="2">
<%-- 						        <input type="text" name="stddmcls" value="${result.stddmcls}"/> --%>
						        	<select name="stddmcls">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI111' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.stddmcls}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
					        </tr>
					        <tr>
					            <th colspan="2">종점부 특이사항</th>
					            <td colspan="10"><c:if test="${result.epntpartclr != null}">∘ <input type="text" name="epntpartclr" value="${result.epntpartclr}"/></c:if></td>
					        </tr>
					        <tr>
					            <th colspan="2">시점부 특이사항</th>
					            <td colspan="10"><c:if test="${result.spntpartclr != null}">∘ <input type="text" name="spntpartclr" value="${result.spntpartclr}"/></c:if></td>
					        </tr>
					        <tr>
					            <th class="subMenu" rowspan="2" style="writing-mode: vertical-lr;">상태정보</th>
					            <th>월류상태</th>
					            <td colspan="2">
<%-- 				            	<input type="text" name="ovrflwsttus" value="${result.ovrflwsttus}"/> --%>
					            	<select name="ovrflwsttus">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI134' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.ovrflwsttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>	
					            </td>
					            <th>계류상황</th>
					            <td colspan="2">
<%-- 					            <input type="text" name="mntntrntsittn" value="${result.mntntrntsittn}"/> --%>
					            	<select name="mntntrntsittn">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI135' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.mntntrntsittn}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th>곡류상태</th>
					            <td colspan="2">
<%-- 					            <input type="text" name="mdgfwsttus" value="${result.mdgfwsttus}"/> --%>
					            	<select name="mdgfwsttus">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI136' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.mdgfwsttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th>계류수</th>
					            <td colspan="2">
<%-- 					            <input type="text" name="mntntrntcnt" value="${result.mntntrntcnt}"/> --%>
					            	<select name="mntntrntcnt">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI137' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.mntntrntcnt}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					        </tr>
					        <tr>
					            <th>유목길이</th>
					            <td colspan="2"><input type="text" name="lwdlen" value="${result.lwdlen}"/>m</td>
					            <th>퇴적지</th>
					            <td colspan="2">
<%-- 					            <input type="text" name="sdtytopo" value="${result.sdtytopo}"/> --%>
					            	<select name="sdtytopo">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI138' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.sdtytopo}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th>좌안붕괴지</th>
					            <td colspan="2">
<%-- 					            <input type="text" name="lslmplnd" value="${result.lslmplnd}"/> --%>
					            	<select name="lslmplnd">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI139' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.lslmplnd}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
				            		개
					            </td>
					            <th>우안붕괴지</th>
					            <td colspan="2">
<%-- 					            <input type="text" name="rslmplnd" value="${result.rslmplnd}"/> --%>
					            	<select name="rslmplnd">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI140' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.rslmplnd}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            	개
					            </td>
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
					            <td><input type="text" name="shrtlc" value="${result.shrtlc}"/>m</td>
					            <th>고도</th>
					            <td><input type="text" name="shrtelev" value="${result.shrtelev}"/>m</td>
					            <th>토심</t>
					            <td><input type="text" class="soildep" name="shrtsoildep" value="${result.shrtsoildep}" onchange="inputText(this)"/>cm</td>
					            <th>폭</th>
					            <td><input type="text" name="shrtdm" value="${result.shrtdm}"/>m</td>
					            <th>경사</th>
					            <td colspan="2"><input class="slp" type="text" name="shrtslp" value="${result.shrtslp}" onchange="inputText(this)"/>°</td>
					        </tr>
					        <tr>
					            <th>암반</th>
					            <td><input type="text" class="shrtper" name="shrtrock" value="${result.shrtrock}"/>%</td>
					            <th>전석</th>
					            <td><input type="text" class="shrtper" name="shrtbldrstne" value="${result.shrtbldrstne}"/>%</td>
					            <th>자갈</th>
					            <td><input type="text" class="shrtper" name="shrtgravel" value="${result.shrtgravel}"/>%</td>
					            <th>모래</th>
					            <td><input type="text" class="shrtper" name="shrtsand" value="${result.shrtsand}"/>%</td>
					            <th>기타</th>
					            <td colspan="2"><input type="text" class="shrtper" name="shrtetc" value="${result.shrtetc}"/>%</td>
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
					            <td><input type="text" name="mdllc" value="${result.mdllc}"/>m</td>
					            <th>고도</th>
					            <td><input type="text" name="mdlelev" value="${result.mdlelev}"/>m</td>
					            <th>토심</th>
					            <td><input type="text" class="soildep" name="mdlsoildep" value="${result.mdlsoildep}" onchange="inputText(this)"/>cm</td>
					            <th>폭</th>
					            <td><input type="text" name="mdldm" value="${result.mdldm}"/>m</td>
					            <th>경사</th>
					            <td colspan="2"><input type="text" class="slp" name="mdlslp" value="${result.mdlslp}" onchange="inputText(this)"/>°</td>
					        </tr>
					        <tr>
					            <th>암반</th>
					            <td><input type="text" class="mdlper" name="mdlrock" value="${result.mdlrock}"/>%</td>
					            <th>전석</th>
					            <td><input type="text" class="mdlper" name="mdlbldrstne" value="${result.mdlbldrstne}"/>%</td>
					            <th>자갈</th>
					            <td><input type="text" class="mdlper" name="mdlgravel" value="${result.mdlgravel}"/>%</td>
					            <th>모래</th>
					            <td><input type="text" class="mdlper" name="mdlsand" value="${result.mdlsand}"/>%</td>
					            <th>기타</th>
					            <td colspan="2"><input type="text" class="mdlper" name="mdletc" value="${result.mdletc}"/>%</td>
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
					            <td><input type="text" name="uplc" value="${result.uplc}"/>m</td>
					            <th>고도</th>
					            <td><input type="text" name="upelev" value="${result.upelev}"/>m</td>
					            <th>토심</th>
					            <td><input type="text" class="soildep" name="upsoildep" value="${result.upsoildep}" onchange="inputText(this)"/>cm</td>
					            <th>폭</th>
					            <td><input type="text" name="updm" value="${result.updm}"/>m</td>
					            <th>경사</th>
					            <td colspan="2"><input type="text" class="slp" name="upslp" value="${result.upslp}" onchange="inputText(this)"/>°</td>
					        </tr>
					        <tr>
					            <th>암반</th>
					            <td><input type="text" class="upper" name="uprock" value="${result.uprock}"/>%</td>
					            <th>전석</th>
					            <td><input type="text" class="upper" name="upbldrstne" value="${result.upbldrstne}"/>%</td>
					            <th>자갈</th>
					            <td><input type="text" class="upper" name="upgravel" value="${result.upgravel}"/>%</td>
					            <th>모래</th>
					            <td><input type="text" class="upper" name="upsand" value="${result.upsand}"/>%</td>
					            <th>기타</th>
					            <td colspan="2"><input type="text" class="upper" name="upetc" value="${result.upetc}"/>%</td>
					        </tr>
					    </tbody>
					</table>
					<div class="mainMenu">□ 토석류 시뮬레이션 평가표</div>
			    	<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			    		<tbody>
			    			 <tr>
					            <th colspan="2">토석류 확산 영향범위</th>
					            <td colspan="5">
					            	<select name="stabanal" onchange="select(this)">
			                          	<option value="0" <c:if test="${result.stabanalscore eq '0'}">selected="selected"</c:if>>토석류 확산 영향범위 내 대상 없음</option>
			                          	<option value="15" <c:if test="${result.stabanalscore eq '15'}">selected="selected"</c:if>>토석류 확산 영향범위 내 피해우려 대상 존재</option>
			                          	<option value="30" <c:if test="${result.stabanalscore eq '30'}">selected="selected"</c:if>>토석류 확산 영향범위 내 보호시설 등 인명피해 우려대상 존재</option>
	                    			</select>
					            </td>
					            <td class="stabanalscore">
					            	<c:out value="${result.stabanalscore}"/>
					            </td>
					        </tr>
					        <tr>
			    				<th colspan="2">피해대상 개소</th>
			    				<td colspan="2"><c:out value="${result.placelen}"/></td>
			    				<th colspan="2">이격거리</th>
			    				<td colspan="2"><input type="text" name="sepdist" value="${result.sepdist}"/></td>
			    			</tr>
			    			<tr>
			    				<th colspan="2">1회 토석류량</th>
			    				<th colspan="2">정지조건</th>
			    				<th colspan="2">가중치</th>
			    				<th colspan="2">전체 토석류량</th>			    				
			    			</tr>
			    			<tr>
			    				<td colspan="2"><input type="text" name="onedebriswt" value="${result.onedebriswt}"/></td>
			    				<td colspan="2"><input type="text" name="stopcnd" value="${result.stopcnd}"/></td>
			    				<td colspan="2"><input type="text" name="wt" value="${result.wt}"/></td>
			    				<td colspan="2"><input type="text" name="totdebriswt" value="${result.totdebriswt}"/></td>			    				
			    			</tr>
			    		</tbody>
			    	</table>
			    	<!-- 해석결과 도면 -->			
					<div class="mainMenu">□ 토석류 시뮬레이션 해석 결과 도면</div>					
					<div class="simulPhotoWrap">
		    			<c:set var="item" value="${stabanal_img_result[0]}"/>
					    <c:choose>
					        <c:when test="${not empty item}">
					            <c:set var="noImage" value="false"/>
					            <c:set var="photoUrl" value="${item}"/>
					        </c:when>
					        <c:otherwise>
					            <c:set var="noImage" value="true"/>
					            <c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
					        </c:otherwise>
					    </c:choose>
					    <div class="simulPhotoBox" style="width: 100%;">
					    	<div class="simulPhotoTag">
						    	<input type="file" name="simul" accept=".jpg, .png" onchange="readImg(this);"/>
					    		<button type="button" class="del-btn" style="float:right; height:35px;" onclick="fncDeleteStabanal(simul); return false;">삭제</button>
					    	</div>
					    	<div class="simulPhotoUrl" style="margin: 0 auto;">
			                    <img id="simulImgView" src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> class="photoSrc<c:out value="${status.count}"/>" alt="<c:out value="${status.count}"/>번 현장사진">
					    	</div>
					    </div>
					</div>             
		  		</c:if>
		  		<c:if test="${result.svyType eq '취약지역 실태조사(산사태)' }">
				  	<div class="mainMenu">□ 위험사면 현황</div>
					<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
						<colgroup>
					  		<col width="14.2%;">
							<col width="14.2%;">
							<col width="14.2%;">
							<col width="14.2%;">
					  		<col width="14.2%;">
							<col width="14.2%;">
							<col width="14.2%;">
					  	</colgroup>
						<tbody>
						    <tr>
						        <th class="subMenu" rowspan="4">사면 현황</th>
						        <th>사면상태</th>
						        <td>
						        	<select name="dirsttus" onchange="select(this);">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI100' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.dirsttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th>모암</th>
						        <td>
						        	<select name="prntrock" onchange="select(this);">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI101' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.prntrock}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th>토성</th>
						        <td>
						        	<select name="soilchar">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI102' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.soilchar}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						    </tr>
						    <tr>
						        <th>경사길이</th>
						        <td><input type="text" name="slopelen" value="${result.slopelen}"/>m</td>
						        <th>경사도</th>
						        <td class="lndsld" colspan="3"><input type="text" name="slope" value="${result.slope}" onchange="inputText(this);"/>° / <input type="text" name="slopemin" value="${result.slopemin}" onchange="inputText(this);"/>~<input type="text" name="slopemax" value="${result.slopemax}" onchange="inputText(this);"/>°</td>
						    </tr>
						    <tr>
						        <th>사면형</th>
						        <td>
						        	<select name="dirfrm" onchange="select(this);">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI103' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.dirfrm}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						    	<th>경사위치</th>
						        <td class="lndsld" colspan="3"><input type="text" name="ridge" value="${result.ridge}"/>(<input type="text" name="slopelc" value="${result.slopelc}"/>/10)</td>
						    </tr>
						    <tr>
						        <th>최고지점</th>
						        <td colspan="2"><input type="text" name="pntmax" value="${result.pntmax}" onchange="inputText(this);"/>m</td>
						        <th>최저지점</th>
						        <td colspan="2"><input type="text" name="pntmin" value="${result.pntmin}" onchange="inputText(this);"/>m</td>
						    </tr>
						    <tr>
						        <th class="subMenu" rowspan="2">토질 현황</th>
						        <th>토심</th>
						        <td><input type="text" class="soildep" name="soildep" value="${result.soildep}" onchange="inputText(this)"/>m</td>
						        <th>균열</th>
						        <td>
						        	<select name="crck">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI104' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.crck}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th>함몰</th>
						        <td>
						        	<select name="sink">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI105' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.sink}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						    </tr>
						    <tr>
						        <th>융기</th>
						        <td>
						        	<select name="uplift">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI106' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.uplift}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th>말단부압출</th>
						        <td>
						        	<select name="extdistalend">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI107' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.extdistalend}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th>확대예상</th>
						        <td>
						        	<select name="expandpredic">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI108' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.expandpredic}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						    </tr>
						    <tr>
						        <th class="subMenu">임상 현황</th>
						        <th>임상</th>
						        <td>
						        	<select name="frtp">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI109' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.frtp}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>						        
						        <th>임분밀도</th>
						        <td>
						        	<select name="stddnst">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI110' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.stddnst}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th>임분경급</th>
						        <td>
									<select name="stddmcls">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI111' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.stddmcls}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>						        
						    </tr>
						    <tr>
						        <th class="subMenu" rowspan="2">용수 현황</th>
						        <th colspan="2">용수상시 여부</th>
						        <td>
						        	<select name="uswtrordtmat">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI112' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.uswtrordtmat}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th colspan="2">강우시 용수</th>
						        <td>
						        	<select name="rainfallwater">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI113' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.rainfallwater}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						    </tr>
						    <tr>
						    	<th colspan="2">사면이 축축함</th>
						        <td>
						        	<select name="dirwet">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI114' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.dirwet}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						        <th colspan="2">사면이 건조함</th>
						        <td>
						        	<select name="dirdry">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI115' }">
		           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.dirdry}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
						        </td>
						    </tr>
					    </tbody>
			    	</table>
			    	<div class="mainMenu">□ 사면 안정해석 평가표</div>
			    	<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			    		<tbody>
			    			<tr>
			    				<th colspan="2">건기</th>
			    				<td colspan="2"><input type="text" name="dryseason" value="${result.dryseason}" onchange="inputText(this)"/></td>
			    				<th colspan="2">우기</th>
			    				<td colspan="2"><input type="text" name="rainseason" value="${result.rainseason}" onchange="inputText(this)"/></td>
			    			</tr>
			    			<tr>
					            <th colspan="2">기준안전율</th>
					            <td colspan="5">
					            	<select name="stabanal" onchange="select(this)">
			                          	<option value="0" <c:if test="${result.stabanalscore eq '0'}">selected="selected"</c:if>>기준안전율 충족 건기시(1.6이상) 우기시 (1.3이상)</option>
			                          	<option value="15" <c:if test="${result.stabanalscore eq '15'}">selected="selected"</c:if>>기준안전율 수준 건기시(1.5~1.6미만) 우기시 (1.2~1.3미만)</option>
			                          	<option value="30" <c:if test="${result.stabanalscore eq '30'}">selected="selected"</c:if>>기준안전율 불충족 건기시(1.5미만) 우기시 (1.2미만)</option>
	                    			</select>
					            </td>
					            <td class="stabanalscore">
					            	<c:out value="${result.stabanalscore}"/>
					            </td>
					        </tr>
			    			<tr>
			    				<th colspan="2">피해대상 개소</th>
			    				<td colspan="2"><c:out value="${result.placelen}"/></td>
			    				<th colspan="2">이격거리</th>
			    				<td colspan="2"><input type="text" name="sepdist" value="${result.sepdist}"/></td>
			    			</tr>
			    			<tr>
			    				<th colspan="3">습윤단위중량(kN/㎥)</th>
			    				<th colspan="2">점착력(kPa)</th>
			    				<th colspan="3">내부마찰각(˚)</th>
			    			</tr>
			    			<tr>
			    				<td colspan="3"><input type="text" name="wetunitwt" value="${result.wetunitwt}"/></td>
			    				<td colspan="2"><input type="text" name="adheforce" value="${result.adheforce}"/></td>
			    				<td colspan="3"><input type="text" name="infricangle" value="${result.infricangle}"/></td>
			    			</tr>
			    		</tbody>
			    	</table>
			    	<!-- 산사태 안정성 검토 결과 -->			
					<div class="mainMenu">□ 산사태 안정성 검토 결과</div>					
					<div class="simulPhotoWrap" <c:if test="${fn:contains(stabanal_img_result[0],'rain') }">style="display: flex; flex-direction: row-reverse;"</c:if>>
    				<c:set var="stop" value="false"/>
	    			<c:forEach var="i" varStatus="status" begin="0" end="1">
	    				<c:set var="item" value="${stabanal_img_result[i]}"/>
					    <c:choose>
					        <c:when test="${not empty item}">
					            <c:set var="noImage" value="false"/>
					            <c:set var="photoUrl" value="${item}"/>
					        </c:when>
					        <c:otherwise>
					            <c:set var="noImage" value="true"/>
					            <c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
					        </c:otherwise>
					    </c:choose>
				    	<c:if test="${empty item && status.count eq 1 }">
				    		<div class="simulPhotoBox" style="width: 50%;">
						    	<div class="simulPhotoTag">
						    		<input type="file" name="dry" accept=".jpg, .png" onchange="readImg(this);"/>
						    		<button type="button" class="del-btn" style="float:right; height:35px;" onclick="fncDeleteStabanal(dry); return false;">삭제</button>
						    	</div>
						    	<div class="simulPhotoUrl">
				                     <img id="dryImgView" src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if>>
						    	</div>									
					    	</div>
				    	</c:if>			    		
				    	<c:if test="${empty item && status.count eq 2 }">
				    		<div class="simulPhotoBox" style="width: 50%;">
				            	<div class="simulPhotoTag">
						    		<input type="file" name="rain" accept=".jpg, .png" <c:if test="${noImage eq false }">value="${photoUrl}"</c:if> onchange="readImg(this);"/>	
						    		<button type="button" class="del-btn" style="float:right; height:35px;" onclick="fncDeleteStabanal(rain); return false;">삭제</button>
						    	</div>
						    	<div class="simulPhotoUrl">
				                    <img id="rainImgView" src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if>>
						    	</div>
					    	</div>		
				    	</c:if>			    		
					    <c:if test="${fn:contains(item,'dry') }">
							<div class="simulPhotoBox" style="width: 50%;">
						    	<div class="simulPhotoTag">
						    		<input type="file" name="dry" accept=".jpg, .png" onchange="readImg(this);"/>
						    		<button type="button" class="del-btn" style="float:right; height:35px;" onclick="fncDeleteStabanal(dry); return false;">삭제</button>
						    	</div>
						    	<div class="simulPhotoUrl">
				                     <img id="dryImgView" src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if>>
						    	</div>									
					    	</div>
				    	</c:if>
				    	<c:if test="${fn:contains(item,'rain') }">
					    	<div class="simulPhotoBox" style="width: 50%;">
<%-- 						    	<c:set var="noImage" value="false"/> --%>
<%-- 				            	<c:set var="photoUrl" value="${item}"/> --%>
				            	<div class="simulPhotoTag">
						    		<input type="file" name="rain" accept=".jpg, .png" <c:if test="${noImage eq false }">value="${photoUrl}"</c:if> onchange="readImg(this);"/>	
						    		<button type="button" class="del-btn" style="float:right; height:35px;" onclick="fncDeleteStabanal(rain); return false;">삭제</button>
						    	</div>
						    	<div class="simulPhotoUrl">
				                    <img id="rainImgView" src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if>>
						    	</div>
					    	</div>					    				
				    	</c:if>		
			    	</c:forEach>
				    </div>
			    </c:if>
			    <div class="mainMenu">□ 최종 판정등급</div>
				<table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<colgroup>
				  		<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
				  		<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
						<col width="12.5%;">
				  	</colgroup>
					<tbody>	
					    <tr>
					        <th class="subMenu" rowspan="3">판정 결과</th>
					        <th>현장조사점수</th>
					        <td class="fieldsurveyscore"><c:out value="${result.fieldsurveyscore}"/></td>
					        <th rowspan="2">점수 계</th>
					        <td rowspan="2" class="scoresum"><c:out value="${result.scoresum}"/></td>
					        <th rowspan="2">판정등급</th>
					        <td colspan="2" rowspan="2">
					        	<select name="jdgmntgrad" onchange="changeGrade();">
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI152' }">
	           								<option value="${item.codeNm}" <c:if test="${fn:contains(item.codeDc, result.jdgmntgrad)}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
					        </td>
					    </tr>
					    <tr>
					        <th>안정해석 점수</th>
					        <td class="stabanalscore"><c:out value="${result.stabanalscore}"/></td>
					    </tr>
					    <tr>
					        <th>등급보정</th>
					        <td>
<%-- 					        <c:out value="${result.gradcoreton}"/> --%>
					        	<select name="gradcoreton">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI153' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.gradcoreton}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
					        </td>
					        <th>보정사유</th>
					        <td colspan="4"><input type="text" name="revisioncause" value="${result.revisioncause}"/></td>
					    </tr>
					    <tr>
					        <th class="subMenu" rowspan="5">종합 결론</th>
					        <th>사업가능여부</th>
					        <td>
					        	<select name="bsposblat">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI154' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.bsposblat}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
					        </td>
					        <th>대책방안</th>
					        <td colspan="4">
					        	<select name="cntrplnmethod">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI155' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.cntrplnmethod}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
					        </td>
					    </tr>
					    <tr>
					        <th>관리 필요성</th>
					        <td colspan="6">
					        	<select name="mngncssty">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI157' }">
	           								<option value="${item.codeDc}" <c:if test="${item.codeDc eq result.mngncssty}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
					        </td>
					    </tr>
					    <tr>
					        <th rowspan="2">종합의견</th>
					        <td colspan="6"><input type="text" name="opinion9" value="${result.opinion9}"/></td>
					    </tr>
					    <tr>
					    	<td colspan="6"><input type="text" name="opinion10" value="${result.opinion10}"/></td>
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
						<col width="10%;">
				  	</colgroup>
				    <thead>
				        <tr>
				            <th class="subMenu" colspan="2" >항목</th>
				            <th class="subMenu">판정인자</th>
				            <th class="subMenu" colspan="5" >항목 및 점수</th>
				            <th class="subMenu">점수</th>
				        </tr>
				    </thead>
				    <tbody>
				    	<c:if test="${result.svyType eq '취약지역 실태조사(산사태)' }">
				        <tr>
				            <th class="subMenu" colspan="2" rowspan="2">피해<br>가능성<br>(15)</th>
				            <th>피해이력</th>
				            <td colspan="5">
				            	<select name="dglogVal" onchange="select(this)">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI098' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.dglog}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dglogscore">
				            	<c:out value="${result.dglogscore}"/>
				            </th>
				        </tr>
				        <tr>
				            <th>직접영향권<br> 내 보호시설</th>
				            <td colspan="5">
				            	<select name="direffcsafeVal" onchange="select(this)">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI099' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.direffcsafe}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="direffcsafescore">
				            	<c:out value="${result.direffcscore}"/>
				            </th>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="8">지형<br>(25)</th>
				            <th class="subMenu" rowspan="4">토사<br>사면</th>
				            <th>경사도(°)</th>
				            <td colspan="5" class="dirEs">
				            	<select name="slopeVal" class="dirEs disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI116' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.slopescore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirEs slopescore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.slopescore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>사면높이<br>(m)</th>
				            <td colspan="5" class="dirEs">
				            	<select name="dirhgVal" class="dirEs disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI117'}">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.dirhgscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirEs dirhgscore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.dirhgscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>토심(cm)</th>
				            <td colspan="5">
				            	<select name="soildepVal" class="dirEs disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI118' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.soildepscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach> 
                    			</select>
				            </td>
				            <th class="dirEs soildepscore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.soildepscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>종단형상</th>
				            <td colspan="5">
				            	<select name="crossfrmVal" class="dirEs disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI119' }">
	           								<option value="${item.codeNm}" <c:if test="${fn:contains(item.codeDc, result.dirfrm)}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirEs crossfrmscore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.crossfrmscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="4">암반<br>사면</th>
				            <th>경사도(°)</th>
				            <td colspan="5" class="dirBr">
				            	<select name="slopeVal" class="dirBr disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI123' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.slopescore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr slopescore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.slopescore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>사면높이(m)</th>
				            <td colspan="5" class="dirBr">
				            	<select name="dirhgVal" class="dirBr disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI124' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.dirhgscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr dirhgscore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.dirhgscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>암석종류</th>
				            <td colspan="5">
				            	<select name="rockkndVal" class="dirBr disabled" disabled="disabled">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI125' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.rockkndscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr rockkndscore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.rockkndscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>균열상황<br>(이완암상태)</th>
				            <td colspan="5">
				            	<select name="crcksittnVal" class="dirBr" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI126' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.crcksittn}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr crcksittnscore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.crcksittnscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="8">주요위험인자<br>(30)</th>
				            <th class="subMenu" rowspan="2">공통</th>
				            <th>산사태위험<br>등급 현황</th>
				            <td colspan="5">
				            	<select name="lndslddgsttusVal" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI131' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.lndslddgscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="lndslddgsttusscore">
				            	<c:out value="${result.lndslddgscore}"/>
				            </th>
				        </tr>
				        <tr>
				            <th>용수상황</th>
				            <td colspan="5">
				            	<select name="watersttusVal" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI132' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.watersttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="watersttusscore">
				            	<c:out value="${result.watersttusscore}"/>
				            </th>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="3">토사<br>사면</th>
				            <th>붕괴지</th>
				            <td colspan="5">
				            	<select name="slidlandVal" class="dirEs" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI120' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.slidland}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirEs slidlandscore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.sliplandscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>뿌리특성</th>
				            <td colspan="5">
				            	<select name="rootcharVal" class="dirEs" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI121' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.rootchar}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirEs rootcharscore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.rootcharscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>산림현황</th>
				            <td colspan="5">
				            	<select name="foreststtusVal" class="dirEs" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI122' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.foreststtusscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirEs foreststtusscore">
				            	<c:if test="${result.dirsttus eq '토사' }">
						            <c:out value="${result.foreststtusscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th class="subMenu" rowspan="3">암반<br>사면</th>
				            <th>붕괴</th>
				            <td colspan="5">
				            	<select name="clpsVal" class="dirBr" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI127' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.clps}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr clpsscore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.clpsscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>불연속면 방향</th>
				            <td colspan="5">
				            	<select name="surfacedirVal" class="dirBr" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI128' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.surfacedir}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr surfacedirscore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.surfacescore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th>풍화상태</th>
				            <td colspan="5">
				            	<select name="wtrsttusVal" class="dirBr" disabled="disabled" onchange="select(this);">
			        				<option value="">선택</option>
		                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
		                          		<c:if test="${item.codeId eq 'FEI129' }">
	           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.wtrsttus}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
		                          		</c:if>
           	   						</c:forEach>
                    			</select>
				            </td>
				            <th class="dirBr wtrsttusscore">
				            	<c:if test="${result.dirsttus eq '암반' }">
						            <c:out value="${result.wtrsttusscore}"/>
				            	</c:if>
			            	</th>
				        </tr>
				        <tr>
				            <th class="subMenu" colspan="2">점수</th>
				            <th>계</th>
				            <th colspan="6" class="totScore"><c:out value="${result.fieldsurveyscore}"/></th>
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
					            <th class="subMenu" colspan="2" rowspan="2">피해<br>가능성<br>(15)</th>
					            <th>피해이력</th>
					            <td colspan="5">
					            	<select name="dglogVal" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI098' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.dglog}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="dglogscore">
					            	<c:out value="${result.dglogscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>직접영향권<br> 내 보호시설</th>
					            <td colspan="5">
					            	<select name="direffcsafeVal" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI099' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.direffcsafe}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="direffcsafescore">
					            	<c:out value="${result.direffcscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th class="subMenu" colspan="2" rowspan="3">지형<br>(25)</th>
					            <th>유역면적<br>(ha)</th>
					            <td colspan="5">
					            	<select name="dgrareaVal" disabled="disabled">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI141' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.dgrareascore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="dgrareascore">
					            	<c:out value="${result.dgrareascore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>계류<br>평균경사도(°)</th>
					            <td colspan="5">
					            	<select name="mntntrntVal" disabled="disabled">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI142' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.mntntrntscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="mntntrntscore">
					            	<c:out value="${result.mntntrntscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>토심<br>(cm)</th>
					            <td colspan="5">
					            	<select name="soildepVal" disabled="disabled">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI143' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.soildepscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="soildepscore">
					            	<c:out value="${result.soildepscore}"/>
					            </th>
					        </tr>
					        <tr>
					        	<th class="subMenu" rowspan="8">위험인자<br>(30점)</th>
					        	<th class="subMenu" rowspan="4">주 위험요소<br>(20점)</th>
					            <th>붕괴</th>
					            <td colspan="4">
					            	<select name="clpsVal" class="mainrisk" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI144' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.clpsscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <td rowspan="4">주 위험요소<br>항목 중 <br> 높은 점수 택1</td>
					            <th rowspan="4" class="mainriskscore">
					            	<c:out value="${result.mainriskelemscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>침식</th>
					            <td colspan="4">
					            	<select name="corrosionVal" class="mainrisk" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI145' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.corrosionscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					        </tr>
					        <tr>
					            <th>전석</th>
					            <td colspan="4">
					            	<select name="bldrstneVal" class="mainrisk" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI146' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.bldrstnescore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					        </tr>
					        <tr>
					            <th>토석류 흔적</th>
					            <td colspan="4">
					            	<select name="soiltraceVal" class="mainrisk" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI147' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.soiltracescore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					        </tr>
					        <tr>
					            <th class="subMenu" rowspan="4">잠재적 위험<br>요소<br>(10점)</th>
					            <th>산사태위험<br>등급현황</th>
					            <td colspan="5">
					            	<select name="lndslddgsttusVal" disabled="disabled">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI133' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.scodsltn}">selected="selected"</c:if>>산사태위험등급 ${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="lndslddgsttusscore">
					            	<c:out value="${result.scodsltnscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>산림현황</th>
					            <td colspan="5">
					            	<select name="foreststtusVal" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI148' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.foreststtusscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <th class="foreststtusscore">
					            	<c:out value="${result.foreststtusscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>뿌리특성</th>
					            <td colspan="5">
					            	<select name="rootcharVal" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI149' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeNm eq result.rootcharscore}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>					            
					            <th class="rootcharscore">
					            	<c:out value="${result.rootcharscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th>기타위험요소<br>(선택형)</th>
					            <td colspan="4">
					            	<select name="etcdgVal" onchange="select(this)">
				        				<option value="">선택</option>
			                          	<c:forEach items="${wkaCodeList}" var="item" varStatus="status">
			                          		<c:if test="${item.codeId eq 'FEI150' }">
		           								<option value="${item.codeNm}" <c:if test="${item.codeDc eq result.etcdg}">selected="selected"</c:if>>${item.codeDc}</option>		                          		
			                          		</c:if>
	           	   						</c:forEach>
	                    			</select>
					            </td>
					            <td>기타위험요소<br>항목 중 택 1</td>
					            <th class="etcdgscore">
					            	<c:out value="${result.etcdgscore}"/>
					            </th>
					        </tr>
					        <tr>
					            <th class="subMenu" colspan="2">점수</th>
					            <th>계</th>
					            <th colspan="6" class="totScore">
					            	<c:out value="${result.fieldsurveyscore}"/>
					            </th>
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
					        <td colspan="5"><input type="text" name="opinion1" value="${result.opinion1}"/></td>
					    </tr>
					    <tr>
					        <th>위험징후 및 분포현황</th>
					        <td colspan="5"><input type="text" name="opinion2" value="${result.opinion2}"/></td>
					    </tr>
					    <tr>
					        <th>간략한 종합의견 및 지정사유1</th>
					        <td colspan="5"><input type="text" name="opinion3" value="${result.opinion3}"/></td>
					    </tr>
					    <tr>
					        <th>간략한 종합의견 및 지정사유2</th>
					        <td colspan="5"><input type="text" name="opinion4" value="${result.opinion4}"/></td>
					    </tr>
					    <tr>
					        <th>구역설정 근거 및 사유</th>
					        <td colspan="5"><input type="text" name="opinion5" value="${result.opinion5}"/></td>
					    </tr>
					    <tr>
					        <th>특이사항 및 진입여건</th>
					        <td colspan="5"><input type="text" name="opinion6" value="${result.opinion6}"/></td>
					    </tr>
					    <tr>
					        <th>재해예방 사업종 선정사유</th>
					        <td colspan="5"><input type="text" name="opinion7" value="${result.opinion7}"/></td>
					    </tr>
					    <tr>
					        <th>기타종합의견 주민협의 및 대체대안</th>
					        <td colspan="5"><input type="text" name="opinion8" value="${result.opinion8}"/></td>
					    </tr>
		    		</tbody>
		    	</table>
		    	
				<!-- 현장사진 -->			
				<div class="mainMenu">□ 현장사진</div>					
				<div class="photoWrap">
			    	<c:choose>
			    		<c:when test="${photo_result != null }">
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
						    		<select name="photoNum" class="photoNum" style="min-width:15%; width:15%; height:35px; float:left;">
							            <option value="" >선택</option>
							            <c:forEach var="j" begin="0" end="13">
							                <option value="<c:out value="${j+1}"/>" <c:if test="${fn:contains(item.TAG,'.') && fn:split(item.TAG,'.')[0] eq j+1}">selected="selected"</c:if>><c:out value="${j+1}"/></option>
							            </c:forEach>
							        </select> 
							        <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag<c:out value="${status.count }"/>"
						            	<c:choose>
							                <c:when test="${fn:contains(item,'TAG') && fn:contains(item.TAG,'.') }">placeholder="<c:out value="${fn:split(item.TAG,'.')[1]}"/>" value="<c:out value="${fn:split(item.TAG,'.')[1]}"/>"</c:when>
							                <c:when test="${fn:contains(item,'TAG') && fn:length(item.TAG) > 0 }">placeholder="<c:out value="${item.TAG}"/>" value="<c:out value="${item.TAG}"/>"</c:when>
							                <c:otherwise>placeholder="사진태그없음"</c:otherwise>
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
						<c:if test="${orginl_photo_result != null }">
							<button type="button" class="add-btn" onclick="javascript:fncLssSvyAddPhoto(); return false;">현장사진 <spring:message code="button.add" /></button>
						</c:if>
              			<button type="button" class="modify-btn" onclick="javascript:fncSvyComptUpdate(); return false;"><spring:message code="title.update" /></button>
              			<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>