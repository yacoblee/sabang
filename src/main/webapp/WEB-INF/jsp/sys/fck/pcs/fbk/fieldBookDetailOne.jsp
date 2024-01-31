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
<c:set var="pageTitle"><spring:message code="sysFckPcs.fieldBookDetailOne.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckPcs.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">

		<form id="listForm">				
		<input name="smid" type="hidden" value="${result.smid }">
		<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">		
		<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
		<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
		<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
		<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
		<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
		<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
		<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
		<input name="schpageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
		
		<input name="schsvyid" type="hidden" value="<c:out value='${searchItemVO.svyid}'/>"/>
		<input name="schsd" type="hidden" value="<c:out value='${searchItemVO.sd}'/>"/>
		<input name="schsgg" type="hidden" value="<c:out value='${searchItemVO.sgg}'/>"/>
		<input name="schemd" type="hidden" value="<c:out value='${searchItemVO.emd}'/>"/>
		<input name="schri" type="hidden" value="<c:out value='${searchItemVO.ri}'/>"/>	
		<div class="BoardDetail">
			<c:if test="${fn:contains(result.svyType,'사방댐 정밀점검')}">
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
					<td colspan="3"><c:out value="${result.sn}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3"><c:out value="${result.svyType}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><c:out value="${result.svyId}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><c:out value="${result.commonly}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrKnd"/></th><!-- 사방댐종류 -->
					<td colspan="3"><c:out value="${result.ecnrKnd}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrForm" /></th><!-- 형식  -->
					<td colspan="3"><c:out value="${result.ecnrForm}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					<td colspan="3"><c:out value="${result.ecnrRnum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt" /></th><!-- 점검일시  -->
					<td colspan="3"><c:out value="${result.svyDt}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					<td colspan="3"><c:out value="${result.nationSpotNum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.chkUser" /></th><!-- 점검자  -->
					<td colspan="3"><c:out value="${result.chkUser}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locplc"/></th><!-- 소재지-->
					<td colspan="8"><c:out value="${result.svySd}" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
				</tr>


				<%-- <tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo" /></th><!-- 산사태정보시스템 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/> <c:out value="${result.svyLat}"/></td>
				</tr> --%>

				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locInfo" /></th><!-- 현재계측 위치정보  -->
					<td colspan="8">위도 <c:out value="${result.svyLat}"/>  경도 <c:out value="${result.svyLon}"/></td>
				</tr>
				<tr>
					<th rowspan="8" colspan="3"><spring:message code="sysFckPcs.svyComptVO.fclt" /></th><!-- 시설제원 -->
				</tr>					
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
					<td colspan="4"><c:out value="${result.fcltYear }" /></td>
				</tr>
				<%-- <tr>
					<th colspan="7"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu" /></th><!-- 주요공작물 시설명 -->
					<td colspan="3"><c:out value="${result.fcltmainstrctu }" /></td>
				</tr> --%>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltUprHg" /></th><!-- 상장 -->
					<td colspan="4"><c:out value="${result.fcltUprHg }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltLwrHg" /></th><!-- 하장 -->
					<td colspan="4"><c:out value="${result.fcltLwrHg }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltStkHg" /></th><!-- 유효고 -->
					<td colspan="4"><c:out value="${result.fcltStkHg }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltTthgh" /></th><!-- 전고 -->
					<td colspan="4"><c:out value="${result.tthghjdgval }" /></td>
				</tr>				
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltBt" /></th><!-- 천단폭 -->
					<td colspan="4"><c:out value="${result.wotdjdgval }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrctCt" /></th><!-- 시공비(천원) -->
					<td colspan="4"><c:out value="${result.cnstrcost }" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt" /></th><!-- 주요시설 -->
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.orginlDam" /></th><!-- 본댐  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.orginlDamVal" /></th><!-- 본댐 측정  -->
					<td colspan="4"><c:out value="${result.orginlDamVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.orginlDamJdgVal" /></th><!-- 본댐 판정  -->
					<td colspan="4"><c:out value="${result.orginlDamJdgVal }" /></td>
				</tr>
				<tr>
					<th rowspan="4" colspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWall" /></th><!-- 측벽  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWallVal" /></th><!-- 측벽 측정값  -->
					<td colspan="4"><c:out value="${result.sideWallVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWallJdgVal" /></th><!-- 측벽 판정값  -->
					<td colspan="4"><c:out value="${result.sideWallJdgVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sidewalllng" /></th><!-- 측벽 가로  -->
					<td colspan="4"><c:out value="${result.sidewalllng }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sidewallheg" /></th><!-- 측벽 세로  -->
					<td colspan="4"><c:out value="${result.sidewallheg }" /></td>
				</tr>
				<tr>
					<th rowspan="4" colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnspt" /></th><!-- 물받이  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnsptVal" /></th><!-- 물받이 측정값  -->
					<td colspan="4"><c:out value="${result.dwnsptVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnsptJdgVal" /></th><!-- 물받이 판정값  -->
					<td colspan="4"><c:out value="${result.dwnsptJdgVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnspthrz" /></th><!-- 물받이 가로  -->
					<td colspan="4"><c:out value="${result.dwnspthrz }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnsptvtc" /></th><!-- 물받이 세로  -->
					<td colspan="4"><c:out value="${result.dwnsptvtc }" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.subfclt" /></th><!-- 부대시설 -->
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.flugt" /></th><!-- 수문 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.sffc" /></th><!-- 안전시설 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.accssrd" /></th><!-- 접근도로 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.etc" /></th><!-- 기타 -->
				</tr>
				<tr class="center">
					<td colspan="2"><c:out value="${result.flugtJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.vtnsttusJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.sffcJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.accssrdJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.etcJdgVal}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.snddpsitVal" /></th><!-- 저사량 -->
					<td colspan="3"><c:out value="${result.snddpsitVal}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.snddpsitJdgVal" /></th><!-- 저사상태 판정값 -->
					<td colspan="3"><c:out value="${result.snddpsitJdgVal}" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.chckrslt" /></th><!-- 점검결과 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
					<td colspan="7"><c:out value="${result.fckRslt}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
					<td colspan="7"><c:out value="${result.mngmtr}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
					<td colspan="7"><c:out value="${result.appnRelis}" /></td>
				</tr>				
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.specialNote" /></th><!-- 특이사항 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
					<td colspan="7"><c:out value="${result.mainFcltSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.subfclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
					<td colspan="7"><c:out value="${result.subfcltSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
					<td colspan="7"><c:out value="${result.etcSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.opinion" /></th><!-- 종합의견 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion1" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion1 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion2" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion2 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion3" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion3 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion4" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion4 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion5" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion5 }" /></td>
				</tr>
			</table>	
			</c:if>
			<c:if test="${result.svyType eq '계류보전 정밀점검' }">
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
					<td colspan="3"><c:out value="${result.sn}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3"><c:out value="${result.svyType}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><c:out value="${result.svyId}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><c:out value="${result.commonly}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.knd"/></th><!-- 종류 -->
					<td colspan="3"><c:out value="${result.knd}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrForm" /></th><!-- 형식  -->
					<td colspan="3"><c:out value="${result.ecnrForm}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					<td colspan="3"><c:out value="${result.ecnrRnum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt" /></th><!-- 점검일시  -->
					<td colspan="3"><c:out value="${result.createDt}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					<td colspan="3"><c:out value="${result.nationSpotNum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyUser" /></th><!-- 점검자  -->
					<td colspan="3"><c:out value="${result.chkUser}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locplc"/></th><!-- 소재지-->
					<td colspan="8"><c:out value="" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
				</tr>
				<%-- <tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo" /></th><!-- 산사태정보시스템 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/> <c:out value="${result.svyLat}"/></td>
				</tr> --%>

				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locInfo" /></th><!-- 현재계측 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/><c:out value="${result.svyLat}"/></td>
				</tr>
				<tr>
				<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.fclt" /></th><!-- 시설제원 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
					<td colspan="7"><c:out value="${result.fcltYear }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu"/></th><!-- 주요공작물_시설명 -->
					<td colspan="7"><c:out value="${result.fcltmainstrctu }" /></td>
				</tr>	
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltLng" /></th><!-- 길이 -->
					<td colspan="7"><c:out value="${result.fcltLng }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltRng" /></th><!-- 폭 -->
					<td colspan="7"><c:out value="${result.fcltRng }" /></td>
				</tr>				
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltDept" /></th><!-- 깊이 -->
					<td colspan="7"><c:out value="${result.fcltDept }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltlng1" /></th><!-- 주요시설 길이 -->
					<td colspan="7"><c:out value="${result.fcltlng1 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltheg1" /></th><!-- 주요시설 높이 -->
					<td colspan="7"><c:out value="${result.fcltheg1 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fclthrz2" /></th><!-- 주요시설 가로 -->
					<td colspan="7"><c:out value="${result.fclthrz2 }" /></td>
				</tr>				
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltvtc2" /></th><!-- 주요시설 세로 -->
					<td colspan="7"><c:out value="${result.fcltvtc2 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrcost" /></th><!-- 시공비(천원) -->
					<td colspan="7"><c:out value="${result.cnstrcost }" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt" /></th><!-- 주요시설  -->
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.chkdamVal" /></th><!-- 골막이  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.chkdamVal" /></th><!-- 골막이 측정  -->
					<td colspan="4"><c:out value="${result.chkdamVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.chkdamJdgVal" /></th><!-- 골막이 판정  -->
					<td colspan="4"><c:out value="${result.chkdamJdgVal }" /></td>
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.rvtmntVal" /></th><!-- 기슭막이  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.rvtmntVal" /></th><!-- 기슭막이 측정값  -->
					<td colspan="4"><c:out value="${result.rvtmntVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.rvtmntJdgVal" /></th><!-- 기슭막이 판정값  -->
					<td colspan="4"><c:out value="${result.rvtmntJdgVal }" /></td>
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.grdstabl" /></th><!-- 바닥막이  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.grdstablVal" /></th><!-- 바닥막이 측정값  -->
					<td colspan="4"><c:out value="${result.grdstablVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.grdstablJdgVal" /></th><!-- 바닥막이 판정값  -->
					<td colspan="4"><c:out value="${result.grdstablJdgVal }" /></td>
				</tr>
				<tr>
					<th colspan="7"><spring:message code="sysFckPcs.svyComptVO.mrngsttus" /></th> <!-- 계류상태  -->
					<td colspan="3"><c:out value="${result.mrngsttus }" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.subfclt" /></th><!-- 부대시설 -->
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.flugt" /></th><!-- 수문 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.sffc" /></th><!-- 안전시설 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.accssrd" /></th><!-- 접근도로 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.etc" /></th><!-- 기타 -->
				</tr>
				<tr class="center">
					<td colspan="2"><c:out value="${result.flugtJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.vtnsttusJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.sffcJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.accssrdJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.etcJdgVal}" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.chckrslt" /></th><!-- 점검결과 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
					<td colspan="7"><c:out value="${result.fckRslt}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
					<td colspan="7"><c:out value="${result.mngmtr}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
					<td colspan="7"><c:out value="${result.appnRelis}" /></td>
				</tr>				
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.specialNote" /></th><!-- 특이사항 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
					<td colspan="7"><c:out value="${result.mainFcltSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.subfclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
					<td colspan="7"><c:out value="${result.subfcltSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
					<td colspan="7"><c:out value="${result.etcSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.opinion" /></th><!-- 종합의견 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion1" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion1 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion2" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion2 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion3" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion3 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion4" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion4 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion5" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion5 }" /></td>
				</tr>
			</table>	
			</c:if>		
			<c:if test="${result.svyType eq '산지사방 정밀점검' }">
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
					<td colspan="3"><c:out value="${result.sn}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3"><c:out value="${result.svyType}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><c:out value="${result.svyId}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><c:out value="${result.commonly}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.knd"/></th><!-- 종류 -->
					<td colspan="3"><c:out value="${result.knd}" /></td>
					<th colspan="5"></th>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					<td colspan="3"><c:out value="${result.ecnrRnum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt" /></th><!-- 점검일시  -->
					<td colspan="3"><c:out value="${result.createDt}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					<td colspan="3"><c:out value="${result.nationSpotNum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyUser" /></th><!-- 점검자  -->
					<td colspan="3"><c:out value="${result.chkUser}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locplc"/></th><!-- 소재지-->
					<td colspan="8"><c:out value="" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
				</tr>
				<%-- <tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo" /></th><!-- 산사태정보시스템 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/> <c:out value="${result.svyLat}"/></td>
				</tr> --%>

				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locInfo" /></th><!-- 현재계측 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/><c:out value="${result.svyLat}"/></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.fclt" /></th><!-- 시설제원 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
					<td colspan="7"><c:out value="${result.fcltYear }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu"/></th><!-- 주요공작물_시설명 -->
					<td colspan="7"><c:out value="${result.fcltmainstrctu }" /></td>
				</tr>	
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrcost" /></th><!-- 시공비(천원) -->
					<td colspan="7"><c:out value="${result.cnstrcost }" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt" /></th><!-- 주요시설  -->
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.reinfc" /></th><!-- 보강시설  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.reinfcVal" /></th><!-- 보강시설 측정  -->
					<td colspan="4"><c:out value="${result.reinfcVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.reinfcJdgVal" /></th><!-- 보강시설 판정  -->
					<td colspan="4"><c:out value="${result.reinfcJdgVal }" /></td>
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.prtc" /></th><!-- 보호시설  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.prtcVal" /></th><!-- 보호시설 측정값  -->
					<td colspan="4"><c:out value="${result.prtcVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.prtcJdgVal" /></th><!-- 보호시설 판정값  -->
					<td colspan="4"><c:out value="${result.prtcJdgVal }" /></td>
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.drng" /></th><!-- 배수시설  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.drngVal" /></th><!-- 배수시설 측정값  -->
					<td colspan="4"><c:out value="${result.drngVal }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.drngJdgVal" /></th><!-- 배수시설 판정값  -->
					<td colspan="4"><c:out value="${result.drngJdgVal }" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.subfclt" /></th><!-- 부대시설 -->
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.flugt" /></th><!-- 수문 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.sffc" /></th><!-- 안전시설 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.accssrd" /></th><!-- 접근도로 -->
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.etc" /></th><!-- 기타 -->
				</tr>
				<tr class="center">
					<td colspan="2"><c:out value="${result.flugtJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.vtnsttusJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.sffcJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.accssrdJdgVal}" /></td>
					<td colspan="2"><c:out value="${result.etcJdgVal}" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.chckrslt" /></th><!-- 점검결과 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
					<td colspan="7"><c:out value="${result.fckRslt}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
					<td colspan="7"><c:out value="${result.mngmtr}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
					<td colspan="7"><c:out value="${result.appnRelis}" /></td>
				</tr>				
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.specialNote" /></th><!-- 특이사항 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
					<td colspan="7"><c:out value="${result.mainFcltSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.subfclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
					<td colspan="7"><c:out value="${result.subfcltSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
					<td colspan="7"><c:out value="${result.etcSpecialNote}" /></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.opinion" /></th><!-- 종합의견 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion1" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion1 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion2" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion2 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion3" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion3 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion4" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion4 }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion5" /></th><!-- 측벽 세로  -->
					<td colspan="7"><c:out value="${result.opinion5 }" /></td>
				</tr>
			</table>	
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
					<td colspan="3"><c:out value="${result.sn}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3"><c:out value="${result.svyType}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><c:out value="${result.svyId}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><c:out value="${result.commonly}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					<td colspan="3"><c:out value="${result.ecnrRnum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt" /></th><!-- 점검일시  -->
					<td colspan="3"><c:out value="${result.createDt}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					<td colspan="3"><c:out value="${result.nationSpotNum}" /></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyUser" /></th><!-- 점검자  -->
					<td colspan="3"><c:out value="${result.chkUser}" /></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locplc"/></th><!-- 소재지-->
					<td colspan="8"><c:out value="" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
				</tr>
				<%-- <tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.fireSysInfo" /></th><!-- 산사태정보시스템 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/> <c:out value="${result.svyLat}"/></td>
				</tr> --%>

				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.locInfo" /></th><!-- 현재계측 위치정보  -->
					<td colspan="8"><c:out value="${result.svyLon}"/><c:out value="${result.svyLat}"/></td>
				</tr>
				<tr>
				<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.fclt" /></th><!-- 시설제원 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
					<td colspan="7"><c:out value="${result.fcltYear }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu" /></th><!-- 주요공작물 시설명 -->
					<td colspan="7"><c:out value="${result.fcltmainstrctu }" /></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrcost" /></th><!-- 시공비(천원) -->
					<td colspan="7"><c:out value="${result.cnstrcost }" /></td>
				</tr>
			</table>	
			</c:if>		
			<div class="BtnGroup">
				<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button>
							<button type="button" class="modify-btn" onclick="javascript:fnSelectFieldBookUpdtOne('<c:out value="${result.smid}"/>');"><spring:message code="button.update" /></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_APR','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<c:if test="${access eq 'ADMIN' }">
								<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button>
							</c:if>
							<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
								<button type="button" class="modify-btn" onclick="javascript:fnSelectFieldBookUpdtOne('<c:out value="${result.smid}"/>');"><spring:message code="title.update" /></button>
							</c:if>
						</sec:authorize>
						<button type="button" class="list-btn" onclick="javascript:fnFieldBookDetail('<c:out value='${mstId}'/>'); return false;"><spring:message code="button.list" /></button>
				</div>
			</div>
			</div>					
		</form>
	</div>
</div>