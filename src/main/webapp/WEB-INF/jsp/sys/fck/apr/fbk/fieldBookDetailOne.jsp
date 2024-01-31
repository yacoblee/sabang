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
<c:set var="pageTitle"><spring:message code="sysFckApr.fieldBookDetailOne.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckApr.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">외관점검</a></li>
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
				<c:if test="${result.svyType eq '사방댐 외관점검'}">
					<table>
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
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.sn" /></th><!-- 일련번호 -->
							<td colspan="3"><c:out value="${result.sn}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!-- 조사유형  -->
							<td colspan="3"><c:out value="${result.svyType}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="3"><c:out value="${result.svyId}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
							<td colspan="3"><c:out value="${result.commonly}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
							<td colspan="3"><c:out value="${result.knd}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.ecnrForm" /></th><!-- 형식  -->
							<td colspan="3"><c:out value="${result.ecnrForm}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
							<td colspan="3"><c:out value="${result.ecnrRnum}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시  -->
							<td colspan="3"><c:out value="${result.createDt}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
							<td colspan="3"><c:out value="${result.nationSpotNum}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자  -->
							<td colspan="3"><c:out value="${result.chkUser}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지-->
							<td colspan="8"><c:out value="${result.svySd}" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보 -->
							<td colspan="8"><c:out value="${result.svyLat}"/> <c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th rowspan="4" colspan="3"><spring:message code="sysFckApr.svyComptVO.fclt" /></th><!-- 시설제원 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
							<td colspan="4"><c:out value="${result.fcltYear}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltUprHg" /></th><!-- 상장 -->
							<td colspan="4"><c:out value="${result.fcltUprHg}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltLwrHg" /></th><!-- 하장 -->
							<td colspan="4"><c:out value="${result.fcltLwrHg}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltStkHg" /></th><!-- 유효고 -->
							<td colspan="4"><c:out value="${result.fcltStkHg}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.mainfclt" /></th><!-- 주요시설 -->
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.orginlDam" /></th><!-- 본댐  -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.orginlDamVal" /></th><!-- 본댐 측정값  -->
							<td colspan="4"><c:out value="${result.orginlDamVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.orginlDamJdgVal" /></th><!-- 본댐 판정값 -->
							<td colspan="4"><c:out value="${result.orginlDamJdgVal}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.sideWall" /></th><!-- 측벽  -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.sideWallVal" /></th><!-- 측벽 측정값  -->
							<td colspan="4"><c:out value="${result.sideWallVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.sideWallJdgVal" /></th><!-- 측벽 판정값  -->
							<td colspan="4"><c:out value="${result.sideWallJdgVal}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.dwnspt" /></th><!-- 물받이  -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.dwnsptVal" /></th><!-- 물받이 측정값  -->
							<td colspan="4"><c:out value="${result.dwnsptVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.dwnsptJdgVal" /></th><!-- 물받이 판정값  -->
							<td colspan="4"><c:out value="${result.dwnsptJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.subfclt"/></th><!-- 부대시설 -->
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.flugt" /></th><!-- 수문 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sffc" /></th><!-- 안전시설 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.accssrd" /></th><!-- 접근도로 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.etc" /></th><!-- 기타 -->
						</tr>
						<tr class="center">
							<td colspan="2"><c:out value="${result.flugtJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.vtnsttusJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.sffcJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.accssrdJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.etcJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.snddpsitVal" /></th><!-- 저사량 -->
							<td colspan="3"><c:out value="${result.snddpsitVal}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.snddpsitJdgVal" /></th><!-- 저사상태 판정값 -->
							<td colspan="3"><c:out value="${result.snddpsitJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
							<td colspan="7"><c:out value="${result.fckRslt}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
							<td colspan="7"><c:out value="${result.mngmtr}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
							<td colspan="7"><c:out value="${result.appnRelis}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 특이사항 -->
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
							<td colspan="7"><c:out value="${result.mainFcltSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.subfclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
							<td colspan="7"><c:out value="${result.subfcltSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
							<td colspan="7"><c:out value="${result.etcSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.opinion" /></th><!-- 종합의견 -->
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion1" /></th><!-- 종합의견1 -->
							<td colspan="7"><c:out value="${result.opinion1}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion2" /></th><!-- 종합의견2 -->
							<td colspan="7"><c:out value="${result.opinion2}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion3" /></th><!-- 종합의견3 -->
							<td colspan="7"><c:out value="${result.opinion3}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion4" /></th><!-- 종합의견4 -->
							<td colspan="7"><c:out value="${result.opinion4}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion5" /></th><!-- 종합의견5 -->
							<td colspan="7"><c:out value="${result.opinion5}" /></td>
						</tr>
					</table>
				</c:if>
				<c:if test="${result.svyType eq '계류보전 외관점검'}">
					<table>
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
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.sn" /></th><!-- 일련번호 -->
							<td colspan="3"><c:out value="${result.sn}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!-- 조사유형  -->
							<td colspan="3"><c:out value="${result.svyType}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="3"><c:out value="${result.svyId}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
							<td colspan="3"><c:out value="${result.commonly}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
							<td colspan="3"><c:out value="${result.knd}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
							<td colspan="3"><c:out value="${result.createDt}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dsgArea"/></th><!-- 지정면적 -->
							<td colspan="3"><c:out value="${result.dsgArea}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
							<td colspan="3"><c:out value="${result.chkUser}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지 -->
							<td colspan="8"><c:out value="${result.svySd}" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보 -->
							<th>시점</th>
							<td colspan="6"><c:out value="${result.svyLat}"/> <c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th>종점</th>
							<td colspan="6"><c:out value="${result.eLatD}"/>°<c:out value="${result.eLatM}"/>'<c:out value="${result.eLatS}"/>"N <c:out value="${result.eLonD}"/>°<c:out value="${result.eLonM}"/>'<c:out value="${result.eLonS}"/>"E</td>
						</tr>
						<tr>
							<th rowspan="4" colspan="3"><spring:message code="sysFckApr.svyComptVO.fclt"/></th><!-- 시설제원 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
							<td colspan="4"><c:out value="${result.fcltYear}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltLng"/></th><!-- 길이 -->
							<td colspan="4"><c:out value="${result.fcltLng}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltRng"/></th><!-- 폭 -->
							<td colspan="4"><c:out value="${result.fcltRng}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltDept"/></th><!-- 깊이 -->
							<td colspan="4"><c:out value="${result.fcltDept}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.mainfclt"/></th><!-- 주요시설 -->
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.chkdam"/></th><!-- 골막이 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.chkdamVal"/></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.chkdamVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.chkdamJdgVal"/></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.chkdamJdgVal}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.rvtmnt"/></th><!-- 기슭막이 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.rvtmntVal"/></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.rvtmntVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.rvtmntJdgVal"/></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.rvtmntJdgVal}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.grdstabl"/></th><!-- 바닥막이 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.grdstablVal"/></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.grdstablVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.grdstablJdgVal"/></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.grdstablJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.subfclt"/></th><!-- 부대시설 -->
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.flugt" /></th><!-- 수문 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sffc" /></th><!-- 안전시설 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.accssrd" /></th><!-- 접근도로 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.etc" /></th><!-- 기타 -->
						</tr>
						<tr class="center">
							<td colspan="2"><c:out value="${result.flugtJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.vtnsttusJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.sffcJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.accssrdJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.etcJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.mrngsttus" /></th><!-- 계류상태 -->
							<td colspan="7"><c:out value="${result.mooringJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
							<td colspan="7"><c:out value="${result.fckRslt}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
							<td colspan="7"><c:out value="${result.mngmtr}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
							<td colspan="7"><c:out value="${result.appnRelis}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 특이사항 -->
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
							<td colspan="7"><c:out value="${result.mainFcltSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.subfclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
							<td colspan="7"><c:out value="${result.subfcltSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
							<td colspan="7"><c:out value="${result.etcSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.opinion" /></th><!-- 종합의견 -->
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion1" /></th><!-- 종합의견1 -->
							<td colspan="7"><c:out value="${result.opinion1}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion2" /></th><!-- 종합의견2 -->
							<td colspan="7"><c:out value="${result.opinion2}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion3" /></th><!-- 종합의견3 -->
							<td colspan="7"><c:out value="${result.opinion3}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion4" /></th><!-- 종합의견4 -->
							<td colspan="7"><c:out value="${result.opinion4}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion5" /></th><!-- 종합의견5 -->
							<td colspan="7"><c:out value="${result.opinion5}" /></td>
						</tr>
					</table>
				</c:if>
				<c:if test="${result.svyType eq '산지사방 외관점검'}">
					<table>
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
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.sn" /></th><!-- 일련번호 -->
							<td colspan="3"><c:out value="${result.sn}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyType" /></th><!-- 조사유형  -->
							<td colspan="3"><c:out value="${result.svyType}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
							<td colspan="3"><c:out value="${result.svyId}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
							<td colspan="3"><c:out value="${result.commonly}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
							<td colspan="3"><c:out value="${result.knd}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
							<td colspan="3"><c:out value="${result.createDt}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dsgArea"/></th><!-- 지정면적 -->
							<td colspan="3"><c:out value="${result.dsgArea}" /></td>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
							<td colspan="3"><c:out value="${result.chkUser}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지 -->
							<td colspan="8"><c:out value="${result.svySd}" /> <c:out value="${result.svySgg}" /> <c:out value="${result.svyEmd}" /> <c:out value="${result.svyRi}" /> <c:out value="${result.svyJibun}" /></td>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보 -->
							<td colspan="8"><c:out value="${result.svyLat}"/> <c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th rowspan="4" colspan="3"><spring:message code="sysFckApr.svyComptVO.fclt"/></th><!-- 시설제원 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
							<td colspan="4"><c:out value="${result.fcltYear}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltLng"/></th><!-- 길이 -->
							<td colspan="4"><c:out value="${result.fcltLng}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltRng"/></th><!-- 폭 -->
							<td colspan="4"><c:out value="${result.fcltRng}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltDept"/></th><!-- 깊이 -->
							<td colspan="4"><c:out value="${result.fcltDept}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.mainfclt"/></th><!-- 주요시설 -->
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.reinfc"/></th><!-- 보강시설 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.reinfcVal"/></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.reinfcVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.reinfcJdgVal"/></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.reinfcJdgVal}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.prtc"/></th><!-- 보호시설 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.prtcVal"/></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.prtcVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.prtcJdgVal"/></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.prtcJdgVal}" /></td>
						</tr>
						<tr>
							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.drng"/></th><!-- 배수시설 -->
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.drngVal"/></th><!-- 측정값 -->
							<td colspan="4"><c:out value="${result.drngVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.drngJdgVal"/></th><!-- 판정값 -->
							<td colspan="4"><c:out value="${result.drngJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.subfclt" /></th><!-- 부대시설 -->
						</tr>
						<tr>
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.flugt" /></th><!-- 수문 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.vtnsttus" /></th><!-- 식생상태 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.sffc" /></th><!-- 안전시설 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.accssrd" /></th><!-- 접근도로 -->
							<th colspan="2"><spring:message code="sysFckApr.svyComptVO.etc" /></th><!-- 기타 -->
						</tr>
						<tr class="center">
							<td colspan="2"><c:out value="${result.flugtJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.vtnsttusJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.sffcJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.accssrdJdgVal}" /></td>
							<td colspan="2"><c:out value="${result.etcJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.slpJdgVal" /></th><!-- 사면상태 -->
							<td colspan="7"><c:out value="${result.slopeJdgVal}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
							<td colspan="7"><c:out value="${result.fckRslt}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
							<td colspan="7"><c:out value="${result.mngmtr}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
							<td colspan="7"><c:out value="${result.appnRelis}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 특이사항 -->
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
							<td colspan="7"><c:out value="${result.mainFcltSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.subfclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
							<td colspan="7"><c:out value="${result.subfcltSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
							<td colspan="7"><c:out value="${result.etcSpecialNote}" /></td>
						</tr>
						<tr>
							<th colspan="10"><spring:message code="sysFckApr.svyComptVO.opinion" /></th><!-- 종합의견 -->
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion1" /></th><!-- 종합의견1 -->
							<td colspan="7"><c:out value="${result.opinion1}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion2" /></th><!-- 종합의견2 -->
							<td colspan="7"><c:out value="${result.opinion2}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion3" /></th><!-- 종합의견3 -->
							<td colspan="7"><c:out value="${result.opinion3}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion4" /></th><!-- 종합의견4 -->
							<td colspan="7"><c:out value="${result.opinion4}" /></td>
						</tr>
						<tr>
							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion5" /></th><!-- 종합의견5 -->
							<td colspan="7"><c:out value="${result.opinion5}" /></td>
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