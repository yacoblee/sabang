<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="sysFckPcs.fieldBookUpdtOne.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckPcs.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" commandName="result" action="/sys/fck/pcs/fbk/updateFieldBookDetailOne.do" method="post">
			<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
			<input name="smid" type="hidden" value="<c:out value='${searchItemVO.smid}'/>">		
			<input name="schid" type="hidden" value="<c:out value='${searchVO.id}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
			<input name="schmstId" type="hidden" value="">
			<input name="schmst_corpname" type="hidden" value="<c:out value='${searchVO.mst_corpname}'/>">
			<input name="schmst_partname" type="hidden" value="<c:out value='${searchVO.mst_partname}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${searchVO.svy_year}'/>">
			<input name="schmst_create_user" type="hidden" value="<c:out value='${searchVO.mst_create_user}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchVO.pageSubIndex}'/>">
			
			<input name="schsvyid" type="hidden" value="<c:out value='${searchItemVO.svyid}'/>"/>
			<input name="schsd" type="hidden" value="<c:out value='${searchItemVO.sd}'/>"/>
			<input name="schsgg" type="hidden" value="<c:out value='${searchItemVO.sgg}'/>"/>
			<input name="schemd" type="hidden" value="<c:out value='${searchItemVO.emd}'/>"/>
			<input name="schri" type="hidden" value="<c:out value='${searchItemVO.ri}'/>"/>
			
			<div class="BoardDetail">
			<c:if test="${fn:contains(result.svyType,'사방댐 정밀점검')}">
			<jsp:include page="fbkUpdtSub1.jsp"/>	
			</c:if>
			<c:if test="${result.svyType eq '계류보전 정밀점검' }">
			<jsp:include page="fbkUpdtSub2.jsp"/>			
			</c:if>		
			<c:if test="${result.svyType eq '산지사방 정밀점검' }">
			<jsp:include page="fbkUpdtSub3.jsp"/>	
			</c:if>		
			<c:if test="${result.svyType eq '해안침식방지 정밀점검' || result.svyType eq '해안방재림 정밀점검' }">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
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
				<!-- 공통 -->
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.sn" /></th><!-- 일련번호 -->
					<td colspan="3"><input type="text" name="sn" value="<c:out value="${result.sn}" />"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3"><input type="text" name="svyType" value="<c:out value="${result.svyType}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><input type="text" name="svyId" value="<c:out value="${result.svyId}" />"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><input type="text" name="commonly" value="<c:out value="${result.commonly}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					<td colspan="3"><input type="text" name="ecnrForm" value="<c:out value="${result.ecnrRnum}" />"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt" /></th><!-- 점검일시  -->
					<td colspan="3"><input type="date" name="createDt" value="<c:out value="${result.createDt}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					<td colspan="3"><input type="text" name="nationSpotNum" value="<c:out value="${result.nationSpotNum}" />"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyUser" /></th><!-- 점검자  -->
					<td colspan="3"><input type="text" name="chkUser" value="<c:out value="${result.chkUser}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locplc"/></th><!-- 소재지-->
					<td colspan="8">
						<form:select path="svySd" id="svySdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministCtprvnNmChange('ectrsTable'); return false;">
							<form:option value="" label="${inputSelect}"/>
							<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
						</form:select>
						<form:select path="svySgg" id="svySggView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministSignguNmChange('ectrsTable'); return false;">
							<form:option value="" label="${inputSelect}"/>
							<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
						</form:select>
						<form:select path="svyEmd" id="svyEmdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministEmdNmChange('ectrsTable'); return false;">
							<form:option value="" label="${inputSelect}"/>
							<form:options items="${emdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
						</form:select>
						<form:select path="svyRi" id="svyRiView" title="${title} ${inputSelect}" cssClass="txt wd15">
							<form:option value="" label="${inputSelect}"/>
							<form:options items="${riCodeList}" itemValue="adminNm" itemLabel="adminNm" />
						</form:select>
						<form:input path="svyJibun" title="${title} ${inputTxt}"/>
					</td>
				</tr>
				<%-- <tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo" /></th><!-- 산사태정보시스템 위치정보  -->
					<td colspan="8">
						<span>위도</span>
						<input type="text" name="latdlndsld" value="${result.latdlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						<input type="text" name="latmlndsld" value="${result.latmlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						<input type="text" name="latslndsld" value="${result.latslndsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						<span class="ml5">경도</span>
						<input type="text" name="londlndsld" value="${result.londlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						<input type="text" name="lonmlndsld" value="${result.lonmlndsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						<input type="text" name="lonslndsld" value="${result.lonslndsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
					</td>
				</tr> --%>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locInfo" /></th><!-- 현재계측 위치정보  -->
					<td colspan="8">
						<span>위도</span>
						<input type="text" name="svyLatD" value="${result.latdacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						<input type="text" name="svyLatM" value="${result.latmacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						<input type="text" name="svyLatS" value="${result.latsacplcsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						<span class="ml5">경도</span>
						<input type="text" name="svyLonD" value="${result.londacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						<input type="text" name="svyLonM" value="${result.lonmacplcsld}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						<input type="text" name="svyLonS" value="${result.lonsacplcsld}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
					</td>
				</tr>
				<tr>
				<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.fclt" /></th><!-- 시설제원 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
					<td colspan="7">
						<select name="fcltYear">
							<option value="">선택</option>
							<c:set var="today" value="<%=new java.util.Date()%>" />
							<fmt:formatDate value="${today}" pattern="yyyy" var="now"/> 
							<c:forEach begin="1990" end="${now}" var="idx" step="1">
							<option value="<c:out value="${idx}" />" <c:if test="${result.fcltYear eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu" /></th><!-- 주요공작물 시설명 -->
					<td colspan="7"><input type="text" name="fcltmainstrctu" value="<c:out value="${result.fcltmainstrctu }" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrcost" /></th><!-- 시공비(천원) -->
					<td colspan="7"><input type="text" name="cnstrcost" value="<c:out value="${result.cnstrcost }" />"/></td>
				</tr>
			</table>	
			</c:if>		
				<div class="BtnGroup">
					<div class="BtnRightArea">
<%-- 						<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button> --%>
						<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="button.update" /></button>
					 <button type="button" class="list-btn"  onclick="javascript:fnSelectFieldBookDetailOne('<c:out value="${result.smid}"/>','<c:out value="${result.svyType}"/>'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
