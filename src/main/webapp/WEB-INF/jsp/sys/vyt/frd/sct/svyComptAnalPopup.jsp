<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="analDiv">
	<div class="BoardDetail">
		<c:set var="pageTitle"><spring:message code="sysVytFrd.stripLand.title"/></c:set>
		<form id="insertForm" action="/sys/vyt/frd/sct/selectSvyComptReportImgAll.do">
			<input name="mstId" type="hidden" value="<c:out value="${mstId}"/>">
			<input name="svyLabel" type="hidden" value="<c:out value="${svyLabel}"/>">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle}</caption>
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>

				</colgroup>
				<tbody>
					<tr class="center">
						<th colspan="4"><spring:message code="sysVytFrd.analManageDetail.frdStat"/></th><!-- 대상지분석 -->
					</tr>
					<tr class="center">
						<td><input type="checkbox" id="check1" name="checkList" value="1" checked="checked" onclick="fnCheckList('list');"><label for="check1">대상지위치</label></td>
						<td><input type="checkbox" id="check2" name="checkList" value="2" checked="checked" onclick="fnCheckList('list');"><label for="check2">행정구역</label></td>
						<td><input type="checkbox" id="check3" name="checkList" value="3" checked="checked" onclick="fnCheckList('list');"><label for="check3">사업대상지 관계지적도</label></td>
						<td><input type="checkbox" id="check4" name="checkList" value="4" checked="checked" onclick="fnCheckList('list');"><label for="check4">문화재보존관리지도</label></td>
					</tr>
				</tbody>
			</table>
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">조사완료지 조사정보 상세조회</caption>
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
				</colgroup>
				<tbody>
					<tr class="center">
						<th colspan="4"><spring:message code="sysVytFrd.analManageDetail.frdStnCnd"/></th><!-- 임황분석 -->
					</tr>
					<tr class="center">
						<td><input type="checkbox" id="check5" name="checkList" value="5" checked="checked" onclick="fnCheckList('list');"><label for="check5">임상분포도</label></td>
						<td><input type="checkbox" id="check6" name="checkList" value="6" checked="checked" onclick="fnCheckList('list');"><label for="check6">임종분포도</label></td>
						<td><input type="checkbox" id="check7" name="checkList" value="7" checked="checked" onclick="fnCheckList('list');"><label for="check7">영급분포도</label></td>
						<td><input type="checkbox" id="check8" name="checkList" value="8" checked="checked" onclick="fnCheckList('list');"><label for="check8">경급분포도</label></td>
					</tr>
					<tr class="center">
						<td><input type="checkbox" id="check9" name="checkList" value="9" checked="checked" onclick="fnCheckList('list');"><label for="check9">밀도분포도</label></td>
						<td><input type="checkbox" id="check10" name="checkList" value="10" checked="checked" onclick="fnCheckList('list');"><label for="check10">수종분포도</label></td>
					</tr>
				</tbody>
			</table>
			
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">조사완료지 조사정보 상세조회</caption>
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>

				</colgroup>
				<tbody>
					<tr class="center">
						<th colspan="4"><spring:message code="sysVytFrd.analManageDetail.frdSilCnd"/></th><!-- 지황분석 -->
					</tr>
					<tr class="center">
						<td><input type="checkbox" id="check11" name="checkList" value="11" checked="checked" onclick="fnCheckList('list');"><label for="check11">경사분포도</label></td>
						<td><input type="checkbox" id="check12" name="checkList" value="12" checked="checked" onclick="fnCheckList('list');"><label for="check12">향분포도</label></td>
						<td><input type="checkbox" id="check13" name="checkList" value="13" checked="checked" onclick="fnCheckList('list');"><label for="check13">표고분포도</label></td>
						<td><input type="checkbox" id="check14" name="checkList" value="14" checked="checked" onclick="fnCheckList('list');"><label for="check14">토성분포도</label></td>
					</tr>
					<tr class="center">
						<td><input type="checkbox" id="check15" name="checkList" value="15" checked="checked" onclick="fnCheckList('list');"><label for="check15">지질분포도</label></td>
						<td><input type="checkbox" id="check16" name="checkList" value="16" checked="checked" onclick="fnCheckList('list');"><label for="check16">모암분포도</label></td>
						<td><input type="checkbox" id="check17" name="checkList" value="17" checked="checked" onclick="fnCheckList('list');"><label for="check17">퇴적양식분포도</label></td>
						<td><input type="checkbox" id="check18" name="checkList" value="18" checked="checked" onclick="fnCheckList('list');"><label for="check18">암석노출도</label></td>
					</tr>
				</tbody>
			</table>
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<caption class="Hidden">조사완료지 조사정보 상세조회</caption>
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>

				</colgroup>
				<tbody>
					<tr>
						<th colspan="4"><spring:message code="sysVytFrd.analManageDetail.frdEtc"/></th><!-- 기타현황 -->
					</tr>
					<tr class="center">
						<td><input type="checkbox" id="check19" name="checkList" value="19" checked="checked" onclick="fnCheckList('list');"><label for="check19">생태자연도</label></td>
						<td><input type="checkbox" id="check20" name="checkList" value="20" checked="checked" onclick="fnCheckList('list');"><label for="check20">멸종위기 동·식물 분석 현황</label></td>
						<td><input type="checkbox" id="check21" name="checkList" value="21" checked="checked" onclick="fnCheckList('list');"><label for="check21">산불취약지도</label></td>
						<td><input type="checkbox" id="check22" name="checkList" value="22" checked="checked" onclick="fnCheckList('list');"><label for="check22">산사태위험등급도</label></td>
					</tr>
				</tbody>
			</table>
	       <div class="BtnGroup">
	       		<div class="BtnLeftArea">
	       			<input type="checkbox" id="checkAll" name="checkAt" value="checkAll" checked="checked" onclick="fnCheckList('all');"><label for="checkAll">전체선택</label>
	       		</div>
				<div class="BtnRightArea">
	       			버퍼크기 : <input type="number" name="bufferSize" value="200" onkeypress="if(event.keyCode=='13'){event.preventDefault();}">m
					<button type="button" class="add-btn" onclick="javascript:fnSvySldAnalRegist('<c:out value="${svyLabel}"/>','<c:out value="${mstId}"/>'); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
		</form>
   </div>
</div>