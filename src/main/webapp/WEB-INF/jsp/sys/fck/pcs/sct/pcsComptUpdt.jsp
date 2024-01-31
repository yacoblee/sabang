<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="sysLssBsc.svyComptList.update"/></c:set>
<c:set var="clientid"><spring:message code="lcMap.clientId" /></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle}</h3><!-- 대상지 수정 -->
	<div id="contents">
		<input type="hidden" name="photolist" value="<c:out value="${orginl_photo_result}"/>">
		<input type="hidden" name="apiKey" value="${clientid}">
		<input type="hidden" name="mapParam" value="${mapParam}">
		<input type="hidden" name="orginlZoom" value="<c:out value="${orginl_zoom}"/>">
		<form:form id="listForm" commandName="fckPcsCompt" action="${pageContext.request.contextPath}/sys/fck/pcs/sct/updateFckPcsCompt.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="photo" value="<c:out value="${orginl_photo_result}"/>">
			<form:hidden path="gid"/>
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
			<input name="locImgIdx" type="hidden"  value="<c:out value='${searchVO.locImgIdx}'/>">
			<input name="changedZoom" type="hidden"  value="<c:out value='${searchVO.changedZoom}'/>">
			<input name="photoTagList" type="hidden"  value="">
			<input name="mstId" type="hidden" value="<c:out value='${fckPcsCompt.mstId}'/>"/>
			<input name="mstNm" type="hidden"  value="<c:out value='${fckPcsCompt.mstNm}'/>">
			<input name="mapType" type="hidden" value="SATELLITE">
			<input name="errorCheck" type="hidden" value="false">		
			<div class="BoardDetail">
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
				  		<!-- 입력/선택 -->
						<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
						<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
						<tr>
							<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="3"><form:input style="background-color: #FFF;border:none;" path="svyId" readonly="true"/></td>
							<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!--조사유형 -->
							<td colspan="3"><form:input style="background-color: #FFF;border:none;" path="svyType" readonly="true"/></td>
						</tr>				  	
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrKnd"/></th><!-- 사방댐종류 -->
					  		<td colspan="3"><input type="text" readonly="readonly" value='<c:out value="${fckPcsCompt.ecnrknd }"></c:out>'></td>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrForm"/></th><!-- 형식 -->
					  		<td colspan="3"><input type="text" readonly="readonly" value='<c:out value="${fckPcsCompt.svyform }"></c:out>'></td>
				  		</tr>
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					  		<td colspan="3"><input type="text" readonly="readonly" value='<c:out value="${fckPcsCompt.ecnrrnum }"></c:out>'></td>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt"/></th><!-- 점검일시 -->
					  		<td colspan="3"><input type="text" readonly="readonly" value='<c:out value="${fckPcsCompt.svyDt }"></c:out>'></td>
				  		</tr>
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					  		<td colspan="3"><input type="text" readonly="readonly" value='<c:out value="${fckPcsCompt.nationspotnum }"></c:out>'></td>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyUser"/></th><!-- 점검자 -->
					  		<td colspan="3">
					  			<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
									<input type="text" name="svyUser" value="<c:out value="${fckPcsCompt.svyUser }"/>" readonly="readonly"/>
								</sec:authorize>
								<sec:authorize access="!hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
									<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
										<c:out value='${fckPcsCompt.svyUser}'/>
									</c:if>
								</sec:authorize>
					  			
				  			</td>
				  		</tr>
				  		<tr>
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.sd"/></th><!-- 도 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.sgg"/></th><!-- 시/군 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.emd"/></th><!-- 읍/면 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.ri"/></th><!-- 동/리 -->
					  		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.jibun"/></th><!-- 지번 -->
				  		</tr>
				  		<tr>
				  			<td colspan="2">
				  			<form:select path="svySd" id="svySdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministCtprvnNmChange('ectrsTable'); return false;">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							</td>
							<td colspan="2">
							<form:select path="svySgg" id="svySggView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministSignguNmChange('ectrsTable'); return false;">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							</td>
							<td colspan="2">
							<form:select path="svyEmd" id="svyEmdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministEmdNmChange('ectrsTable'); return false;">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${emdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							</td>
							<td colspan="2">
							<form:select path="svyRi" id="svyRiView" title="${title} ${inputSelect}" cssClass="txt wd15">
								<form:option value="" label="${inputSelect}"/>
								<form:options items="${riCodeList}" itemValue="adminNm" itemLabel="adminNm" />
							</form:select>
							</td>
				  			<td colspan="2">
								<form:input path="svyJibun" title="${title} ${inputTxt}"/>
							</td>
				  		</tr>
				  		<tr>
				  			<th class="subMenu" rowspan="5" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fclt"/></th><!-- 시설제원 -->
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
				  			<td colspan="6">
								<select name="fcltyear">
									<option value="">선택</option>
									<c:set var="today" value="<%=new java.util.Date()%>" />
									<fmt:formatDate value="${today}" pattern="yyyy" var="now"/> 
									<c:forEach begin="1990" end="${now}" var="idx" step="1">
									<option value="<c:out value="${idx}" />" <c:if test="${fckPcsCompt.fcltyear eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
									</c:forEach>
								</select>
							</td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltUprHg"/></th><!-- 상장(m) -->
				  			<td colspan="2"><input type="text" name="fcltuprhg" value="<c:out value="${fckPcsCompt.fcltuprhg }" />"/></td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltLwrHg"/></th><!-- 하장(m) -->
				  			<td colspan="2"><input type="text" name="fcltlwrhg" value="<c:out value="${fckPcsCompt.fcltlwrhg }" />"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltTthgh"/></th><!-- 전고(m) -->
				  			<td colspan="2"><input type="text" name="tthghjdgval" value="<c:out value="${fckPcsCompt.tthghjdgval }" />"/></td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltStkHg"/></th><!-- 유효고(m) -->
				  			<td colspan="2"><input type="text" name="fcltstkhg" value="<c:out value="${fckPcsCompt.fcltstkhg }" />"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.fcltBt"/></th><!-- 천단폭(m) -->
				  			<td colspan="6"><input type="text" name="wotdjdgval" value="<c:out value="${fckPcsCompt.wotdjdgval }" />"/></td>
				  		</tr>				  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.cnstrctCt"/></th><!-- 시공비(천원) -->
				  			<td colspan="6"><input type="text" name="cnstrcost" value="<c:out value="${fckPcsCompt.cnstrcost }" />"/></td>
				  		</tr>
				  		<tr>
				  			<th class="subMenu" colspan="10"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo"/>(WGS84)</th><!-- 산사태 위치정보 -->
				  		</tr>		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.nthtd"/></th><!-- 북위 -->
				  			<td colspan="3">
					  			<input type="text" name="latdlndsld" value="${fckPcsCompt.latdlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="latmlndsld" value="${fckPcsCompt.latmlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="latslndsld" value="${fckPcsCompt.latslndsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
				  			</td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.estlngtd"/></th><!-- 동경 -->
				  			<td colspan="3">
					  			<input type="text" name="londlndsld" value="${fckPcsCompt.londlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="lonmlndsld" value="${fckPcsCompt.lonmlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="lonslndsld" value="${fckPcsCompt.lonslndsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
				  			</td>
				  		</tr>
				  		<tr>
				  			<th class="subMenu" colspan="10"><spring:message code="sysFckPcs.svyComptVO.acplcInfo"/>(WGS84)</th><!-- 현지계측 위치정보 -->
				  		</tr>					  		
				  		<tr>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.nthtd"/></th><!-- 북위 -->
				  			<td colspan="3">
					  			<input type="text" name="latdacplcsld" value="${fckPcsCompt.latdacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="latmacplcsld" value="${fckPcsCompt.latmacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="latsacplcsld" value="${fckPcsCompt.latsacplcsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
				  			</td>
				  			<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.estlngtd"/></th><!-- 동경 -->
				  			<td colspan="3">
					  			<input type="text" name="londacplcsld" value="${fckPcsCompt.londacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="lonmacplcsld" value="${fckPcsCompt.lonmacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="lonsacplcsld" value="${fckPcsCompt.lonsacplcsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
				  			</td>
				  		</tr>			  		
				  	</tbody>
				</table>
				<div class="mainMenu">□ 위치도</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<tbody>
					<tr>								
						<td colspan="10">
							<div class="ol-custom-control ol-basemap-control" id="toggle-btn">
								<button type="button" class="btn-map-selector" title="일반지도" value="NORMAL">일반지도</button>
								<button type="button" class="btn-map-selector" title="위성지도" value="SATELLITE">위성지도</button>
							</div>
							<div id="map" style="width:100%;height:400px;"></div>
						</td>
					</tr>
				</tbody>
				</table>
				
				<div class="mainMenu">□ 현장사진</div>
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
					        <select name="photoNum" class="photoNum" style="width:5%; height:35px; float:left;">
					            <option value="" >선택</option>
					            <c:forEach var="j" begin="0" end="${photoTagNum}">
					                <option value="<c:out value="${j+1}"/>" <c:if test="${fn:contains(item.TAG,'.') && fn:substring(item.TAG,1,2) eq '.' && fn:split(item.TAG,'.')[0] eq j+1}">selected="selected"</c:if>><c:out value="${j+1}"/></option>
					            </c:forEach>
					        </select>
					        <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag<c:out value="${status.count }"/>"
				            	<c:choose>
					                <c:when test="${fn:contains(item,'TAG') && fn:contains(item.TAG,'.') && fn:substring(item.TAG,1,2) eq '.' }">placeholder="<c:out value="${fn:substringAfter(item.TAG,'.')}"/>" value="<c:out value="${fn:substringAfter(item.TAG,'.')}"/>"</c:when>
					                <c:when test="${fn:contains(item,'TAG') && fn:length(item.TAG) > 0 }">placeholder="<c:out value="${item.TAG}"/>" value="<c:out value="${item.TAG}"/>"</c:when>
					                <c:otherwise>placeholder="사진태그없음"</c:otherwise>
					            </c:choose> 
					        /> 
					        <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncFckPcsDeletePhoto(event); return false;">삭제</button>
				    	</div>
				    	<div class="photoUrl">
				    		<input type="hidden" <c:if test="${noImage eq false}">value="<c:url value='${photoUrl}'/>"</c:if> name="photoSrc<c:out value="${status.count}"/>" />
		                    <img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> class="photoSrc<c:out value="${status.count}"/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
		                    <span class="thumb-div noselect">변경</span>
				    	</div>
				    </div>
				    </c:forEach>
			    </div>
			    <div style="margin-top: 15px;">
			    	<button type="button" class="add-btn" onclick="javascript:fncFckPcsAddPhoto(); return false;" style="float: right; margin-bottom: 10px;">현장사진 <spring:message code="button.add" /></button>
				    <hr style="border: #333 solid 0.15rem; clear: both; margin-bottom: 40px;">
			    </div>
				
				<div class="mainMenu">□ 정밀점검 평가표</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" id="evlTbl">
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
				            <th class="subMenu" colspan="2">결함사항</th>
				            <th class="subMenu" colspan="6">평가기준</th>
				            <th class="subMenu">평가점수</th>
				        </tr>
				    </thead>
					<tbody>
						<!-- 본댐 S -->
						<tr>
							<th class="subMenu" rowspan="6" ><spring:message code="sysFckPcs.svyComptVO.orginlDam.title"/></th>
				            <th colspan="2"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1"/></th>
				            <td colspan="6">
				            	<select name="orginldamdmg" onchange="fnChangeEvlTable(event)">
				            		<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1.se1" var="orginlDamDefs1Se1"/>
				            		<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1.se2" var="orginlDamDefs1Se2"/>
				            		<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1.se3" var="orginlDamDefs1Se3"/>
				            		<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1.val1"/>" value="<c:out value="${orginlDamDefs1Se1}"/>" <c:if test="${fckPcsCompt.orginldamdmg eq orginlDamDefs1Se1}">selected</c:if>><c:out value="${orginlDamDefs1Se1 }"/></option>
				            		<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1.val2"/>" value="<c:out value="${orginlDamDefs1Se2}"/>" <c:if test="${fckPcsCompt.orginldamdmg eq orginlDamDefs1Se2}">selected</c:if>><c:out value="${orginlDamDefs1Se2 }"/></option>
				            		<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects1.val3"/>" value="<c:out value="${orginlDamDefs1Se3}"/>" <c:if test="${fckPcsCompt.orginldamdmg eq orginlDamDefs1Se3}">selected</c:if>><c:out value="${orginlDamDefs1Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="orginldamdmgscore" value="<c:out value="${fckPcsCompt.orginldamdmgscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				            <th colspan="2"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se1" var="orginlDamDefs2Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se2" var="orginlDamDefs2Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se3" var="orginlDamDefs2Se3"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se4" var="orginlDamDefs2Se4"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se5" var="orginlDamDefs2Se5"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se6" var="orginlDamDefs2Se6"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se7" var="orginlDamDefs2Se7"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se8" var="orginlDamDefs2Se8"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se9" var="orginlDamDefs2Se9"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se10" var="orginlDamDefs2Se10"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se11" var="orginlDamDefs2Se11"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.se12" var="orginlDamDefs2Se12"/>
				            	
				            	<select name="orginldamcrk" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val1"/>" value="<c:out value="${orginlDamDefs2Se1}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se1}">selected</c:if>><c:out value="${orginlDamDefs2Se1}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val2"/>" value="<c:out value="${orginlDamDefs2Se2}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se2}">selected</c:if>><c:out value="${orginlDamDefs2Se2}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val3"/>" value="<c:out value="${orginlDamDefs2Se3}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se3}">selected</c:if>><c:out value="${orginlDamDefs2Se3}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val1"/>" value="<c:out value="${orginlDamDefs2Se4}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se4}">selected</c:if>><c:out value="${orginlDamDefs2Se4}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val2"/>" value="<c:out value="${orginlDamDefs2Se5}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se5}">selected</c:if>><c:out value="${orginlDamDefs2Se5}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val3"/>" value="<c:out value="${orginlDamDefs2Se6}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se6}">selected</c:if>><c:out value="${orginlDamDefs2Se6}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val1"/>" value="<c:out value="${orginlDamDefs2Se7}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se7}">selected</c:if>><c:out value="${orginlDamDefs2Se7}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val2"/>" value="<c:out value="${orginlDamDefs2Se8}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se8}">selected</c:if>><c:out value="${orginlDamDefs2Se8}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val3"/>" value="<c:out value="${orginlDamDefs2Se9}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se9}">selected</c:if>><c:out value="${orginlDamDefs2Se9}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val1"/>" value="<c:out value="${orginlDamDefs2Se10}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se10}">selected</c:if>><c:out value="${orginlDamDefs2Se10}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val2"/>" value="<c:out value="${orginlDamDefs2Se11}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se11}">selected</c:if>><c:out value="${orginlDamDefs2Se11}"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects2.val3"/>" value="<c:out value="${orginlDamDefs2Se12}"/>" <c:if test="${fckPcsCompt.orginldamcrk eq orginlDamDefs2Se12}">selected</c:if>><c:out value="${orginlDamDefs2Se12}"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="orginldamcrkscore" value="<c:out value="${fckPcsCompt.orginldamcrkscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				            <th colspan="2"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3.se1" var="orginlDamDefs3Se1"/>
								<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3.se2" var="orginlDamDefs3Se2"/>
								<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3.se3" var="orginlDamDefs3Se3"/>
				            	<select name="orginldambasicscour" onchange="fnChangeEvlTable(event)">
				            		<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3.val1"/>" value="<c:out value="${orginlDamDefs3Se1}"/>" <c:if test="${fckPcsCompt.orginldambasicscour eq orginlDamDefs3Se1}">selected</c:if>><c:out value="${orginlDamDefs3Se1}" /></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3.val2"/>" value="<c:out value="${orginlDamDefs3Se2}"/>" <c:if test="${fckPcsCompt.orginldambasicscour eq orginlDamDefs3Se2}">selected</c:if>><c:out value="${orginlDamDefs3Se2}" /></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects3.val3"/>" value="<c:out value="${orginlDamDefs3Se3}"/>" <c:if test="${fckPcsCompt.orginldambasicscour eq orginlDamDefs3Se3}">selected</c:if>><c:out value="${orginlDamDefs3Se3}" /></option>
				            	</select>
				            </td>
				            <td><input type="text" name="orginldambasicscourscore" value="<c:out value="${fckPcsCompt.orginldambasicscourscore }"/>" readonly="readonly"/></td>
				        </tr>
				        <tr>
				            <th colspan="2"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41.se1" var="orginlDamDefs41Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41.se2" var="orginlDamDefs41Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41.se3" var="orginlDamDefs41Se3"/>
				            	<select name="orginldamplng" onchange="fnChangeEvlTable(event)">
				            		<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41.val1"/>" value="<c:out value="${orginlDamDefs41Se1}"/>" <c:if test="${fckPcsCompt.orginldamplng eq orginlDamDefs41Se1}">selected</c:if>><c:out value="${orginlDamDefs41Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41.val2"/>" value="<c:out value="${orginlDamDefs41Se2}"/>" <c:if test="${fckPcsCompt.orginldamplng eq orginlDamDefs41Se2}">selected</c:if>><c:out value="${orginlDamDefs41Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects41.val3"/>" value="<c:out value="${orginlDamDefs41Se3}"/>" <c:if test="${fckPcsCompt.orginldamplng eq orginlDamDefs41Se3}">selected</c:if>><c:out value="${orginlDamDefs41Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="orginldamcncrtscore" value="<c:out value="${fckPcsCompt.orginldamcncrtscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				            <th colspan="2"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42.se1" var="orginlDamDefs42Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42.se2" var="orginlDamDefs42Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42.se3" var="orginlDamDefs42Se3"/>
				            	<select name="orginldamcncrt" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42.val1"/>" value="<c:out value="${orginlDamDefs42Se1}"/>" <c:if test="${fckPcsCompt.orginldamcncrt eq orginlDamDefs42Se1}">selected</c:if>><c:out value="${orginlDamDefs42Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42.val2"/>" value="<c:out value="${orginlDamDefs42Se2}"/>" <c:if test="${fckPcsCompt.orginldamcncrt eq orginlDamDefs42Se2}">selected</c:if>><c:out value="${orginlDamDefs42Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects42.val3"/>" value="<c:out value="${orginlDamDefs42Se3}"/>" <c:if test="${fckPcsCompt.orginldamcncrt eq orginlDamDefs42Se3}">selected</c:if>><c:out value="${orginlDamDefs42Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="orginldamplngscore" value="<c:out value="${fckPcsCompt.orginldamplngscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				            <th colspan="2"><spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5.se1" var="orginlDamDefs5Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5.se2" var="orginlDamDefs5Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5.se3" var="orginlDamDefs5Se3"/>
				            	<select name="orginldamwtgate" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5.val1"/>" value="<c:out value="${orginlDamDefs5Se1}" />" <c:if test="${fckPcsCompt.orginldamwtgate eq orginlDamDefs5Se1}">selected</c:if>><c:out value="${orginlDamDefs5Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5.val2"/>" value="<c:out value="${orginlDamDefs5Se2}" />" <c:if test="${fckPcsCompt.orginldamwtgate eq orginlDamDefs5Se2}">selected</c:if>><c:out value="${orginlDamDefs5Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.orginlDam.defects5.val3"/>" value="<c:out value="${orginlDamDefs5Se3}" />" <c:if test="${fckPcsCompt.orginldamwtgate eq orginlDamDefs5Se3}">selected</c:if>><c:out value="${orginlDamDefs5Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="orginldamwtgatescore" value="<c:out value="${fckPcsCompt.orginldamwtgatescore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <!-- 본댐 E -->
				        <!-- 측벽 S -->
				        <tr>
				        	<th rowspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWall.title"/></th>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.sideWall.defects1"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects1.se1" var="sideWallDefs1Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects1.se2" var="sideWallDefs1Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects1.se3" var="sideWallDefs1Se3"/>
				            	<select name="sidewalldmg" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects1.val1"/>" value="<c:out value="${sideWallDefs1Se1}"/>" <c:if test="${fckPcsCompt.sidewalldmg eq sideWallDefs1Se1}">selected</c:if>><c:out value="${sideWallDefs1Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects1.val2"/>" value="<c:out value="${sideWallDefs1Se2}"/>" <c:if test="${fckPcsCompt.sidewalldmg eq sideWallDefs1Se2}">selected</c:if>><c:out value="${sideWallDefs1Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects1.val3"/>" value="<c:out value="${sideWallDefs1Se3}"/>" <c:if test="${fckPcsCompt.sidewalldmg eq sideWallDefs1Se3}">selected</c:if>><c:out value="${sideWallDefs1Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="sidewalldmgscore" value="<c:out value="${fckPcsCompt.sidewalldmgscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.sideWall.defects2"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects2.se1" var="sideWallDefs2Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects2.se2" var="sideWallDefs2Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects2.se3" var="sideWallDefs2Se3"/>
				            	<select name="sidewallcrk" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects2.val1"/>" value="<c:out value="${sideWallDefs2Se1}"/>" <c:if test="${fckPcsCompt.sidewallcrk eq sideWallDefs2Se1}">selected</c:if>><c:out value="${sideWallDefs2Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects2.val2"/>" value="<c:out value="${sideWallDefs2Se2}"/>" <c:if test="${fckPcsCompt.sidewallcrk eq sideWallDefs2Se2}">selected</c:if>><c:out value="${sideWallDefs2Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects2.val3"/>" value="<c:out value="${sideWallDefs2Se3}"/>" <c:if test="${fckPcsCompt.sidewallcrk eq sideWallDefs2Se3}">selected</c:if>><c:out value="${sideWallDefs2Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="sidewallcrkscore" value="<c:out value="${fckPcsCompt.sidewallcrkscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.sideWall.defects3"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects3.se1" var="sideWallDefs3Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects3.se2" var="sideWallDefs3Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.sideWall.defects3.se3" var="sideWallDefs3Se3"/>
				            	<select name="sidewallbasicscour" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects3.val1"/>" value="<c:out value="${sideWallDefs3Se1}"/>" <c:if test="${fckPcsCompt.sidewallbasicscour eq sideWallDefs3Se1}">selected</c:if>><c:out value="${sideWallDefs3Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects3.val2"/>" value="<c:out value="${sideWallDefs3Se2}"/>" <c:if test="${fckPcsCompt.sidewallbasicscour eq sideWallDefs3Se2}">selected</c:if>><c:out value="${sideWallDefs3Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.sideWall.defects3.val3"/>" value="<c:out value="${sideWallDefs3Se3}"/>" <c:if test="${fckPcsCompt.sidewallbasicscour eq sideWallDefs3Se3}">selected</c:if>><c:out value="${sideWallDefs3Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="sidewallbasicscourscore" value="<c:out value="${fckPcsCompt.sidewallbasicscourscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <!-- 측벽 E -->		        
				        <!-- 물받이 S -->
				        <tr>
				            <th rowspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnspt.title"/></th>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1.se1" var="dwnsptDefs1Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1.se2" var="dwnsptDefs1Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1.se3" var="dwnsptDefs1Se3"/>
				            	<select name="dwnsptdmg" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1.val1"/>" value="<c:out value="${dwnsptDefs1Se1}"/>" <c:if test="${fckPcsCompt.dwnsptdmg eq dwnsptDefs1Se1}">selected</c:if>><c:out value="${dwnsptDefs1Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1.val2"/>" value="<c:out value="${dwnsptDefs1Se2}"/>" <c:if test="${fckPcsCompt.dwnsptdmg eq dwnsptDefs1Se2}">selected</c:if>><c:out value="${dwnsptDefs1Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects1.val3"/>" value="<c:out value="${dwnsptDefs1Se3}"/>" <c:if test="${fckPcsCompt.dwnsptdmg eq dwnsptDefs1Se3}">selected</c:if>><c:out value="${dwnsptDefs1Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="dwnsptdmgscore" value="<c:out value="${fckPcsCompt.dwnsptdmgscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2.se1" var="dwnsptDefs2Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2.se2" var="dwnsptDefs2Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2.se3" var="dwnsptDefs2Se3"/>
				            	<select name="dwnsptbasicscour" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2.val1"/>" value="<c:out value="${dwnsptDefs2Se1}"/>" <c:if test="${fckPcsCompt.dwnsptbasicscour eq dwnsptDefs2Se1}">selected</c:if>><c:out value="${dwnsptDefs2Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2.val2"/>" value="<c:out value="${dwnsptDefs2Se2}"/>" <c:if test="${fckPcsCompt.dwnsptbasicscour eq dwnsptDefs2Se2}">selected</c:if>><c:out value="${dwnsptDefs2Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects2.val3"/>" value="<c:out value="${dwnsptDefs2Se3}"/>" <c:if test="${fckPcsCompt.dwnsptbasicscour eq dwnsptDefs2Se3}">selected</c:if>><c:out value="${dwnsptDefs2Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="dwnsptbasicscourscore" value="<c:out value="${fckPcsCompt.dwnsptbasicscourscore }"/>"  readonly="readonly"/></td>
				        </tr>
				        <tr>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3.se1" var="dwnsptDefs3Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3.se2" var="dwnsptDefs3Se2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3.se3" var="dwnsptDefs3Se3"/>
				            	<select name="dwnsptcrk" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3.val1"/>" value="<c:out value="${dwnsptDefs3Se1}"/>" <c:if test="${fckPcsCompt.dwnsptcrk eq dwnsptDefs3Se1}">selected</c:if>><c:out value="${dwnsptDefs3Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3.val2"/>" value="<c:out value="${dwnsptDefs3Se2}"/>" <c:if test="${fckPcsCompt.dwnsptcrk eq dwnsptDefs3Se2}">selected</c:if>><c:out value="${dwnsptDefs3Se2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.dwnspt.defects3.val3"/>" value="<c:out value="${dwnsptDefs3Se3}"/>" <c:if test="${fckPcsCompt.dwnsptcrk eq dwnsptDefs3Se3}">selected</c:if>><c:out value="${dwnsptDefs3Se3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="dwnsptcrkscore" value="<c:out value="${fckPcsCompt.dwnsptcrkscore }"/>"  readonly="readonly"/></td>
				        </tr>	        
				        <!-- 물받이 E -->	
				        <!-- 조사자보정 S -->	   
				        <tr>
				        	<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.title"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.se1" var="svyUserCalibrDefsSe1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.se2" var="svyUserCalibrDefsSe2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.se3" var="svyUserCalibrDefsSe3"/>
				            	<select name="svycrctn" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.val1"/>" value="<c:out value="${svyUserCalibrDefsSe1}"/>" <c:if test="${fckPcsCompt.svycrctn eq svyUserCalibrDefsSe1}">selected</c:if>><c:out value="${svyUserCalibrDefsSe1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.val2"/>" value="<c:out value="${svyUserCalibrDefsSe2}"/>" <c:if test="${fckPcsCompt.svycrctn eq svyUserCalibrDefsSe2}">selected</c:if>><c:out value="${svyUserCalibrDefsSe2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.svyUserCalibr.val3"/>" value="<c:out value="${svyUserCalibrDefsSe3}"/>" <c:if test="${fckPcsCompt.svycrctn eq svyUserCalibrDefsSe3}">selected</c:if>><c:out value="${svyUserCalibrDefsSe3 }"/></option>
				            	</select>
				            </td>
				            <td><input type="text" name="svycrctnscore" value="<c:out value="${fckPcsCompt.svycrctnscore }"/>"  readonly="readonly"/></td>
				        </tr>
					    <tr>
					    	<th colspan="9"><spring:message code="sysFckPcs.svyComptVO.scoreSum"/></th>
					    	<td></td>
					    </tr>
				        <!-- 조사자보정 E -->
				        <!-- 비파괴 검사 S-->
				        <tr>
				        	<th rowspan="2"><spring:message code="sysFckPcs.svyComptVO.ndt.title"/></th>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ndt.defects3"/></th>
				            <td colspan="6">
					        	<spring:message code="sysFckPcs.svyComptVO.ndt.defects3.se1" var="ndtDefs3Se1"/>
					        	<spring:message code="sysFckPcs.svyComptVO.ndt.defects3.se2" var="ndtDefs3Se2"/>
				            	<select name="cncrtcmprsstrn" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.ndt.defects3.val1"/>" value="<c:out value="${ndtDefs3Se1}"/>" <c:if test="${fckPcsCompt.cncrtcmprsstrn eq ndtDefs3Se1}">selected</c:if>><c:out value="${ndtDefs3Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.ndt.defects3.val2"/>" value="<c:out value="${ndtDefs3Se2}"/>" <c:if test="${fckPcsCompt.cncrtcmprsstrn eq ndtDefs3Se2}">selected</c:if>><c:out value="${ndtDefs3Se2 }"/></option>
				            	</select>
				            </td>
				            <td>
				        	<c:choose>
				        	<c:when test="${cncrtcmprsstrn eq ndtDefs3Se1 }"><spring:message code="sysFckPcs.svyComptVO.ndt.defects3.val1" var="ndtDefs3Val"/></c:when>
				        	<c:otherwise><spring:message code="sysFckPcs.svyComptVO.ndt.defects3.val2"  var="ndtDefs3Val"/></c:otherwise>
				        	</c:choose>
				        	<input type="text" value="<c:out value="${ndtDefs3Val }"/>" readonly="readonly">
							</td>
				        </tr>	        
				        <tr>
				        	<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ndt.defects5"/></th>
				            <td colspan="6">
				            	<spring:message code="sysFckPcs.svyComptVO.ndt.defects5.se1" var="ndtDefs5Se1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.ndt.defects5.se2" var="ndtDefs5Se2"/>
				        		
				            	<select name="cncrtcrkdpt" onchange="fnChangeEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.ndt.defects5.val1"/>" value="<c:out value="${ndtDefs5Se1}"/>" <c:if test="${fckPcsCompt.cncrtcrkdpt eq ndtDefs5Se1}">selected</c:if>><c:out value="${ndtDefs5Se1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.ndt.defects5.val2"/>" value="<c:out value="${ndtDefs5Se2}"/>" <c:if test="${fckPcsCompt.cncrtcrkdpt eq ndtDefs5Se2}">selected</c:if>><c:out value="${ndtDefs5Se2 }"/></option>
				            	</select>
				            </td>
				            <td>
				        	<c:choose>
				        	<c:when test="${cncrtcrkdpt eq ndtDefs5Se1 }"><spring:message code="sysFckPcs.svyComptVO.ndt.defects5.val1" var="ndtDefs5Val"/></c:when>
				        	<c:otherwise><spring:message code="sysFckPcs.svyComptVO.ndt.defects5.val2"  var="ndtDefs5Val"/></c:otherwise>
				        	</c:choose>
				        	<input type="text" value="<c:out value="${ndtDefs5Val }"/>" readonly="readonly">
							</td>
				        </tr>	        
				        <!-- 비파괴 검사 E-->
					</tbody>
				</table>
				
				<div class="mainMenu">□ 사방댐 준설 평가표</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" id="drdgEvlTbl">
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
				            <th class="subMenu" colspan="4"><spring:message code="sysFckPcs.svyComptVO.actualFactor"/></th> <!-- 인자실측치 -->		      
				            <th class="subMenu" rowspan="2"><spring:message code="sysFckPcs.svyComptVO.wghval"/></th> <!-- 가중치 -->
				            <th class="subMenu" rowspan="2" colspan="2"><spring:message code="sysFckPcs.svyComptVO.dredgeval"/></th> <!-- 준설기준값 -->
				            <th class="subMenu" rowspan="2"><spring:message code="sysFckPcs.svyComptVO.dmgSttusNote"/></th> <!-- 비고 -->      				            
				        </tr>
				        <tr>
				            <th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.seStdr"/></th> <!-- 구분기준 -->
				            <th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.cffcnt"/></th> <!-- 계수 -->
				        </tr>
				    </thead>					
					<tbody>
						 <!-- 현재저사량 S-->
				       	<tr>					
				       		<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal"/></th>
				            <td colspan="2">
				            	<spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal.se1" var="presSnddpsitValSe1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal.se2" var="presSnddpsitValSe2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal.se3" var="presSnddpsitValSe3"/>
				            	<select name="nowsnddpsitvaldivision" onchange="fnChangeDrdgEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal.scffcnt1"/>" value="<c:out value="${presSnddpsitValSe1}"/>" <c:if test="${fckPcsCompt.nowsnddpsitvaldivision eq  presSnddpsitValSe1}">selected</c:if>><c:out value="${presSnddpsitValSe1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal.scffcnt2"/>" value="<c:out value="${presSnddpsitValSe2}"/>" <c:if test="${fckPcsCompt.nowsnddpsitvaldivision eq  presSnddpsitValSe2}">selected</c:if>><c:out value="${presSnddpsitValSe2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.presSnddpsitVal.scffcnt3"/>" value="<c:out value="${presSnddpsitValSe3}"/>" <c:if test="${fckPcsCompt.nowsnddpsitvaldivision eq  presSnddpsitValSe3}">selected</c:if>><c:out value="${presSnddpsitValSe3 }"/></option>
				            	</select>
				            </td>
							<td colspan="2"><input type="text" name="nowsnddpsitvalcoeff" value="<c:out value="${fckPcsCompt.nowsnddpsitvalcoeff }"/>" readonly="readonly"/></td>			
				            <td><input type="text" name="nowsnddpsitvalweight" value="<c:out value="${fckPcsCompt.nowsnddpsitvalweight }"/>" readonly="readonly"></td>
				            <td colspan="2"><input type="text" name="nowsnddpsitvaldrdgn" value="<c:out value="${fckPcsCompt.nowsnddpsitvaldrdgn }"/>" readonly="readonly"/></td>
							<td>--</td>
				        </tr>				        
				        <!-- 현재저사량 E-->	
				        <!-- 생활권 S-->
				        <tr>			
				        	<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.urbanErosion"/></th>
				            <td colspan="2">
				            	<spring:message code="sysFckPcs.svyComptVO.urbanErosion.se1" var="urbanErosionSe1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.urbanErosion.se2" var="urbanErosionSe2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.urbanErosion.se3" var="urbanErosionSe3"/>
				            	<select name="lifezonedivision" onchange="fnChangeDrdgEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.urbanErosion.scffcnt1"/>" value="<c:out value="${urbanErosionSe1}"/>" <c:if test="${fckPcsCompt.lifezonedivision eq  urbanErosionSe1}">selected</c:if>><c:out value="${urbanErosionSe1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.urbanErosion.scffcnt2"/>" value="<c:out value="${urbanErosionSe2}"/>" <c:if test="${fckPcsCompt.lifezonedivision eq  urbanErosionSe2}">selected</c:if>><c:out value="${urbanErosionSe2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.urbanErosion.scffcnt3"/>" value="<c:out value="${urbanErosionSe3}"/>" <c:if test="${fckPcsCompt.lifezonedivision eq  urbanErosionSe3}">selected</c:if>><c:out value="${urbanErosionSe3 }"/></option>
				            	</select>
				            </td>
							<td colspan="2"><input type="text" name="lifezonecoeff" value="<c:out value="${fckPcsCompt.lifezonecoeff }"/>" readonly="readonly"/></td>			
				            <td><input type="text" name="lifezoneweight" value="<c:out value="${fckPcsCompt.lifezoneweight }"/>" readonly="readonly"></td>
				            <td colspan="2"><input type="text" name="lifezonedrdgn" value="<c:out value="${fckPcsCompt.lifezonedrdgn }"/>" readonly="readonly"/></td>
							<td>--</td>
				        </tr>				        
				        <!-- 생활권 E-->	
				        <!-- 계상기울기 S-->
				        <tr>			
				        	<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.calculationSlope"/></th>
				            <td colspan="2" onchange="fnChangeDrdgEvlTable(event)">
				            	<spring:message code="sysFckPcs.svyComptVO.calculationSlope.se1" var="calculationSlopeSe1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.calculationSlope.se2" var="calculationSlopeSe2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.calculationSlope.se3" var="calculationSlopeSe3"/>
				            	<select name="riverbedgradientdivision">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.calculationSlope.scffcnt1"/>" value="<c:out value="${calculationSlopeSe1}"/>" <c:if test="${fckPcsCompt.riverbedgradientdivision eq  calculationSlopeSe1}">selected</c:if>><c:out value="${calculationSlopeSe1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.calculationSlope.scffcnt2"/>" value="<c:out value="${calculationSlopeSe2}"/>" <c:if test="${fckPcsCompt.riverbedgradientdivision eq  calculationSlopeSe2}">selected</c:if>><c:out value="${calculationSlopeSe2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.calculationSlope.scffcnt3"/>" value="<c:out value="${calculationSlopeSe3}"/>" <c:if test="${fckPcsCompt.riverbedgradientdivision eq  calculationSlopeSe3}">selected</c:if>><c:out value="${calculationSlopeSe3 }"/></option>
				            	</select>
				            </td>
							<td colspan="2"><input type="text" name="riverbedgradientcoeff" value="<c:out value="${fckPcsCompt.riverbedgradientcoeff }"/>" readonly="readonly"/></td>			
				            <td><input type="text" name="riverbedgradientweight" value="<c:out value="${fckPcsCompt.riverbedgradientweight }"/>" readonly="readonly"></td>
				            <td colspan="2"><input type="text" name="riverbedgradientdrdgn" value="<c:out value="${fckPcsCompt.riverbedgradientdrdgn }"/>" readonly="readonly"/></td>
							<td>--</td>
				        </tr>				        
				        <!-- 계상기울기 E-->	
				        <!-- 토석이동량 S-->
				        <tr>	
				        	<th class="subMenu" colspan="2"><spring:message code="sysFckPcs.svyComptVO.soilMovement"/></th>
				            <td colspan="2">
				            	<spring:message code="sysFckPcs.svyComptVO.soilMovement.se1" var="soilMovementSe1"/>
				            	<spring:message code="sysFckPcs.svyComptVO.soilMovement.se2" var="soilMovementSe2"/>
				            	<spring:message code="sysFckPcs.svyComptVO.soilMovement.se3" var="soilMovementSe3"/>
				            	<select name="soilmoveamntdivision" onchange="fnChangeDrdgEvlTable(event)">
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.soilMovement.scffcnt1"/>" value="<c:out value="${soilMovementSe1}"/>" <c:if test="${fckPcsCompt.soilmoveamntdivision eq  soilMovementSe1}">selected</c:if>><c:out value="${soilMovementSe1 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.soilMovement.scffcnt2"/>" value="<c:out value="${soilMovementSe2}"/>" <c:if test="${fckPcsCompt.soilmoveamntdivision eq  soilMovementSe2}">selected</c:if>><c:out value="${soilMovementSe2 }"/></option>
									<option data-sub="<spring:message code="sysFckPcs.svyComptVO.soilMovement.scffcnt3"/>" value="<c:out value="${soilMovementSe3}"/>" <c:if test="${fckPcsCompt.soilmoveamntdivision eq  soilMovementSe3}">selected</c:if>><c:out value="${soilMovementSe3 }"/></option>
				            	</select>
				            </td>
							<td colspan="2"><input type="text" name="soilmoveamntcoeff" value="<c:out value="${fckPcsCompt.soilmoveamntcoeff }"/>" readonly="readonly"/></td>			
				            <td><input type="text" name="soilmoveamntweight" value="<c:out value="${fckPcsCompt.soilmoveamntweight }"/>" readonly="readonly"></td>
				            <td colspan="2"><input type="text" name="soilmoveamntdrdgn" value="<c:out value="${fckPcsCompt.soilmoveamntdrdgn }"/>" readonly="readonly"/></td>
							<td>--</td>
				        </tr>				        
				        <!-- 토석이동량 E-->	
				         <tr>
				        	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.totalScore"/></th><!-- 총점 -->
				        	<th colspan="5"><spring:message code="sysFckPcs.svyComptVO.ecnrKndDredge"/></th><!-- 준설여부 판정 -->
				        </tr>
				        <tr>
				        	<td colspan="5"><input type="text" name="totalscore" value="<c:out value="${fckPcsCompt.totalscore }"/>" readonly="readonly"/></td>
				        	<td colspan="5"><input type="text" name="drdgnatjdg" value="<c:out value="${fckPcsCompt.drdgnatjdg }"/>" readonly="readonly"/></td>
				        </tr>
					</tbody>
				</table>
				
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
				   		<p><input type="text" name="opinion1" value="<c:out value="${fckPcsCompt.opinion1 }"/>" /></p>
				   		<p><input type="text" name="opinion2" value="<c:out value="${fckPcsCompt.opinion2 }"/>" /></p>
				   		<p><input type="text" name="opinion3" value="<c:out value="${fckPcsCompt.opinion3 }"/>" /></p>
				   		<p><input type="text" name="opinion4" value="<c:out value="${fckPcsCompt.opinion4 }"/>" /></p>
				   		<p><input type="text" name="opinion5" value="<c:out value="${fckPcsCompt.opinion5 }"/>" /></p>
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
					    	
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.val1" var="finGradeVal1"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.val2" var="finGradeVal2"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.val3" var="finGradeVal3"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.val4" var="finGradeVal4"/>
					    	<spring:message code="sysFckPcs.svyComptVO.finGrade.val5" var="finGradeVal5"/>
					    	
					    	<select>
								<option data-sub="<c:out value="${finGradeVal1 }"/>" value="<c:out value="${finGradeSe1}"/>"><c:out value="${finGradeSe1 }"/></option>
								<option data-sub="<c:out value="${finGradeVal2 }"/>" value="<c:out value="${finGradeSe2}"/>"><c:out value="${finGradeSe2 }"/></option>
								<option data-sub="<c:out value="${finGradeVal3 }"/>" value="<c:out value="${finGradeSe3}"/>"><c:out value="${finGradeSe3 }"/></option>
								<option data-sub="<c:out value="${finGradeVal4 }"/>" value="<c:out value="${finGradeSe4}"/>"><c:out value="${finGradeSe4 }"/></option>
								<option data-sub="<c:out value="${finGradeVal5 }"/>" value="<c:out value="${finGradeSe5}"/>"><c:out value="${finGradeSe5 }"/></option>
					    	</select>
				        </td>
				     </tr>
				        <!-- 점검결과 및 점수 E -->
				</table>			
				
				<!-- 압축강도 및 균열깊이 시험 S-->
				<div class="mainMenu"></div>
				<input type="hidden" name="crkdptcncrt1" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="crkdptcncrt2" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="crkdptcncrt3" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="crkdptcncrt4" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="cmprsstrncncrt1" /> <!-- 압축강도 콘크리트 -->
				<input type="hidden" name="cmprsstrncncrt2" /> <!-- 압축강도 콘크리트 -->
				<input type="hidden" name="cmprsstrncncrt3" /> <!-- 압축강도 콘크리트 -->
				<input type="hidden" name="cmprsstrncncrt4" /> <!-- 압축강도 콘크리트 -->
				
				<!-- 압축강도 및 균열깊이 원본값 -->
				<input type="hidden" name="origin_crkdptcncrt1" value="<c:out value="${origin_crkdptcncrt1}"/>" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="origin_crkdptcncrt2" value="<c:out value="${origin_crkdptcncrt2}"/>" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="origin_crkdptcncrt3" value="<c:out value="${origin_crkdptcncrt3}"/>" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="origin_crkdptcncrt4" value="<c:out value="${origin_crkdptcncrt4}"/>" /> <!-- 균열깊이_콘크리트 -->
				<input type="hidden" name="origin_cmprsstrncncrt1" value="<c:out value="${origin_cmprsstrncncrt1}"/>" /> <!-- 압축강도 콘크리트 -->
				<input type="hidden" name="origin_cmprsstrncncrt2" value="<c:out value="${origin_cmprsstrncncrt2}"/>" /> <!-- 압축강도 콘크리트 -->
				<input type="hidden" name="origin_cmprsstrncncrt3" value="<c:out value="${origin_cmprsstrncncrt3}"/>" /> <!-- 압축강도 콘크리트 -->
				<input type="hidden" name="origin_cmprsstrncncrt4" value="<c:out value="${origin_cmprsstrncncrt4}"/>" /> <!-- 압축강도 콘크리트 -->
				
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" class="testTbl" name="cmprsstrn">
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
						<td rowspan="20" class="fontFocus"><spring:message code="sysFckPcs.svyComptVO.selectMinMsg"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt1 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="cmprsstrnavg1" value="<c:out value="${cmprsstrArr.cmprsstrnavg1 }"/>" readonly="readonly"></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.cmprsstrncncrt_2_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_2_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt2 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="cmprsstrnavg2" value="<c:out value="${cmprsstrArr.cmprsstrnavg2 }"/>" readonly="readonly"></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.cmprsstrncncrt_3_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_3_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt3 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="cmprsstrnavg3" value="<c:out value="${cmprsstrArr.cmprsstrnavg3 }"/>" readonly="readonly"></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.cmprsstrncncrt_4_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.cmprsstrncncrt_4_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${cmprsstrArr.cmprsstrncncrt4 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="cmprsstrnavg4" value="<c:out value="${cmprsstrArr.cmprsstrnavg4 }"/>" readonly="readonly"></td>
					</tr>
				</tbody>
				</table>

				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" class="testTbl" name="crkdpt">
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
						<c:choose>
						<c:when test="${not empty items.crkdptcncrt_1_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_1_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
						<td rowspan="20" class="fontFocus"><spring:message code="sysFckPcs.svyComptVO.selectMaxMsg"/></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt1 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="crkdptavg1" value="<c:out value="${crkdptArr.crkdptavg1 }"/>" readonly="readonly"></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.crkdptcncrt_2_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_2_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt2 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="crkdptavg2" value="<c:out value="${crkdptArr.crkdptavg2 }"/>" readonly="readonly"></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.crkdptcncrt_3_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_3_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt3 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="crkdptavg3" value="<c:out value="${crkdptArr.crkdptavg3 }"/>" readonly="readonly"></td>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }" varStatus="status">
						<c:choose>
						<c:when test="${not empty items.crkdptcncrt_4_photo}">
							<c:set var="noImage" value="false"/>
							<c:set var="photoUrl" value="/storage/fieldBook${items.crkdptcncrt_4_photo}"/>
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
						<c:if test="${fn:contains(items, 'testVal01')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal01 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal02')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal02 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal03')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal03 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal04')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal04 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal05')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal05 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }">
						<c:if test="${fn:contains(items, 'testVal06')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal06 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal07')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal07 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal08')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal08 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal09')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal09 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal10')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal10 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }">
						<c:if test="${fn:contains(items, 'testVal11')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal11 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal12')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal12 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal13')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal13 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal14')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal14 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal15')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal15 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<c:forEach var="items" items="${crkdptArr.crkdptcncrt4 }">
						<c:if test="${fn:contains(items, 'testVal16')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal16 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal17')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal17 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal18')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal18 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal19')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal19 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						<c:if test="${fn:contains(items, 'testVal20')}"><td colspan="2"><input type="text" value="<c:out value="${items.testVal20 }"/>" onkeyup="javascript:fnChangeTestValue(event);" maxlength="3"  ></td></c:if> 
						</c:forEach>
					</tr>
					<tr>
						<th colspan="2" class="fontFocus">평균값</th>
						<td colspan="8"><input type="text" name="crkdptavg4" value="<c:out value="${crkdptArr.crkdptavg4 }"/>" readonly="readonly"></td>
					</tr>
				</tbody>
				</table>
				<!-- 압축강도 및 균열깊이 시험 E-->
				<br>
				<div class="mainMenu">□ 파일목록<button class="upload-btn btn_right" title="파일 업로드" onclick="javascript:fnFileUploadView('<c:out value='${searchVO.gid}'/>'); return false;">파일 업로드</button></div>
				<div id="svyFileDiv">
					<table id="svyFileTable">
						<colgroup>
							<col width="5%;">
							<col width="40%;">
							<col width="15%;">
							<col width="10%;">
							<col width="18%;">
							<col width="12%;">
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
								<tr class="center">
									<td colspan="6"><spring:message code="common.nodata.msg" /></td>
								</tr>
							</c:if>	
							<c:forEach items="${pcsComptFileList}" var="fileInfo" varStatus="status">
								<tr class="center">
									<td>${status.count}</td>
									<td><a href="javascript:void(0);" onclick="javascript:fnFileDownload('${fileInfo.gid }','${fileInfo.sn }'); return false;">${fileInfo.fileOrginlNm }</a></td>
									<td>${fileInfo.fileSize }</td>
									<td>${fileInfo.fileWrter }</td>
									<td>${fileInfo.createDt }</td>
									<td>
										<button class="download-btn-m" onclick="javascript:fnFileDownload('${fileInfo.gid }','${fileInfo.sn }'); return false;"></button>
										<button class="del-btn-m" onclick="javascript:fnFileDelete('${fileInfo.sn }'); return false;"></button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>						
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<%-- <button type="button" class="add-btn" onclick="javascript:fncFckPcsAddPhoto(); return false;">현장사진 <spring:message code="button.add" /></button> --%>
						<button type="button" class="modify-btn" onclick="javascript:fncFckPcsComptUpdate(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>