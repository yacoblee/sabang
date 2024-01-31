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
<c:set var="inputSelect"><spring:message code="input.select"/></c:set>

			<table id="ectrsTable" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
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
					<td colspan="3"><input type="text" name="sn" value="<c:out value="${result.sn}" />" readonly="readonly"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3">
						<input type="text" name="svyType" value="<c:out value="${result.svyType}" />" readonly="readonly"/>
<%-- 						<input type="text" class="txt wd50" name="knd" value="<c:out value="${result.knd}" />" readonly="readonly"/> --%>
					</td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><input type="text" name="svyId" value="<c:out value="${result.svyId}" />" readonly="readonly"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><input type="text" name="commonly" value="<c:out value="${result.commonly}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.knd"/></th><!-- 사방댐 종류 -->
					<td colspan="3">
						<input type="text" name="ecnrKnd" value="<c:out value="${result.ecnrKnd}" />"/>
					</td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrForm" /></th><!-- 형식  -->
					<td colspan="3"><input type="text" name="ecnrForm" value="<c:out value="${result.ecnrForm}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
					<td colspan="3"><input type="text" name="ecnrRnum" value="<c:out value="${result.ecnrRnum}" />"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyDt" /></th><!-- 점검일시  -->
					<td colspan="3"><input type="date" name="svyDt" value="${result.svyDt}"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
					<td colspan="3"><input type="text" name="nationSpotNum" value="<c:out value="${result.nationSpotNum}" />"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.chkUser" /></th><!-- 점검자  -->
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
						<input type="text" name="svyLatD" value="${result.svyLatD}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						<input type="text" name="svyLatM" value="${result.svyLatM}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						<input type="text" name="svyLatS" value="${result.svyLatS}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
						<span class="ml5">경도</span>
						<input type="text" name="svyLonD" value="${result.svyLonD}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
						<input type="text" name="svyLonM" value="${result.svyLonM}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
						<input type="text" name="svyLonS" value="${result.svyLonS}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
					</td>
				</tr>
				<tr>
					<th rowspan="8" colspan="3"><spring:message code="sysFckPcs.svyComptVO.fclt" /></th><!-- 시설제원 -->
				</tr>					
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
					<td colspan="4">
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
				<%-- <tr>
					<th colspan="7"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu" /></th><!-- 주요공작물 시설명 -->
					<td colspan="3"><input type="text" name="fcltmainstrctu" value="<c:out value="${result.fcltmainstrctu }" />"/></td>
				</tr> --%>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltUprHg" /></th><!-- 상장 -->
					<td colspan="4"><input type="text" name="fcltUprHg" value="<c:out value="${result.fcltUprHg }" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltLwrHg" /></th><!-- 하장 -->
					<td colspan="4"><input type="text" name="fcltLwrHg" value="<c:out value="${result.fcltLwrHg }" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltStkHg" /></th><!-- 유효고 -->
					<td colspan="4"><input type="text" name="fcltStkHg" value="<c:out value="${result.fcltStkHg }" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltTthgh" /></th><!-- 전고 -->
					<td colspan="4"><input type="text" name="tthghjdgval" value="<c:out value="${result.tthghjdgval }" />"/></td>
				</tr>				
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltBt" /></th><!-- 천단폭 -->
					<td colspan="4"><input type="text" name="wotdjdgval" value="<c:out value="${result.wotdjdgval }" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrctCt" /></th><!-- 시공비(천원) -->
					<td colspan="4"><input type="text" name="cnstrcost" value="<c:out value="${result.cnstrcost }" />"/></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt" /></th><!-- 주요시설 -->
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.orginlDam" /></th><!-- 본댐  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.orginlDamVal" /></th><!-- 본댐 측정  -->
					<td colspan="4">
					<input type="text" name="orginlDamVal" value="<c:out value="${result.orginlDamVal }" />"/>
					<%-- <form:select path="orginlDamCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="orginlDamVal">
						<form:option value="${result.orginlDamVal }" label="${result.orginlDamVal }"/>
					</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.orginlDamJdgVal" /></th><!-- 본댐 판정  -->
					<td colspan="4">
					<input type="text" name="orginlDamJdgVal" value="<c:out value="${result.orginlDamJdgVal }" />"/>
					<%-- <form:select path="orginlDamCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="orginlDamJdgVal">
						<form:option value="${result.orginlDamJdgVal }" label="${result.orginlDamJdgVal }"/>
					</form:select> --%>
					</td>
				</tr>
				<tr>
					<th rowspan="4" colspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWall" /></th><!-- 측벽  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWallVal" /></th><!-- 측벽 측정값  -->
					<td colspan="4">
					<input type="text" name="sideWallVal" value="<c:out value="${result.sideWallVal }" />"/>
					<%-- <form:select path="sideWallVal" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="sideWallVals">
						<form:option value="${result.sideWallVal }" label="${result.sideWallVal }"/>
					</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sideWallJdgVal" /></th><!-- 측벽 판정값  -->
					<td colspan="4">
					<input type="text" name="sideWallJdgVal" value="<c:out value="${result.sideWallJdgVal }" />"/>
					<%-- <form:select path="sideWallJdgVal" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="sideWallJdgVal">
						<form:option value="${result.sideWallJdgVal }" label="${result.sideWallJdgVal }"/>
					</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sidewalllng" /></th><!-- 측벽 가로  -->
					<td colspan="4">
						<input type="text" name="sidewalllng" value="<c:out value="${result.sidewalllng }" />"/>
						<%-- <form:select path="sideWallVal" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="sidewalldept">
						<form:option value="${result.sidewalllng }" label="${result.sidewalllng }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.sidewallheg" /></th><!-- 측벽 세로  -->
					<td colspan="4">
						<input type="text" name="sidewallheg" value="<c:out value="${result.sidewallheg }" />"/>
						<%-- <form:select path="sidewallheg" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="sidewallheg">
						<form:option value="${result.sidewallheg }" label="${result.sidewallheg }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th rowspan="4" colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnspt" /></th><!-- 물받이  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnsptVal" /></th><!-- 물받이 측정값  -->
					<td colspan="4">
						<input type="text" name="dwnsptVal" value="<c:out value="${result.dwnsptVal }" />"/>
						<%-- <form:select path="dwnsptVal" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="dwnsptVal">
						<form:option value="${result.dwnsptVal }" label="${result.dwnsptVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnsptJdgVal" /></th><!-- 물받이 판정값  -->
					<td colspan="4">
						<input type="text" name="dwnsptJdgVal" value="<c:out value="${result.dwnsptJdgVal }" />"/>
						<%-- <form:select path="dwnsptJdgVal" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="dwnsptJdgVal">
						<form:option value="${result.dwnsptJdgVal }" label="${result.dwnsptJdgVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnspthrz" /></th><!-- 물받이 가로  -->
					<td colspan="4">
						<input type="text" name="dwnspthrz" value="<c:out value="${result.dwnspthrz }" />"/>
						<%-- <form:select path="dwnspthrz" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="dwnspthrz">
						<form:option value="${result.dwnspthrz }" label="${result.dwnspthrz }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.dwnsptvtc" /></th><!-- 물받이 세로  -->
					<td colspan="4">
						<input type="text" name="dwnsptvtc" value="<c:out value="${result.dwnsptvtc }" />"/>
						<%-- <form:select path="dwnsptvtc" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);" name="dwnsptvtc">
						<form:option value="${result.dwnsptvtc }" label="${result.dwnsptvtc }"/>
						</form:select> --%>
					</td>
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
					<td colspan="2">
						<input type="text" name="flugtJdgVal" value="<c:out value="${result.flugtJdgVal}" />"/>
						<%-- <form:select path="flugtJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="flugtJdgVal">
							<form:option value="${result.flugtJdgVal }" label="${result.flugtJdgVal }"/>
						</form:select> --%>
					</td>
					<td colspan="2">
						<input type="text" name="vtnsttusJdgVal" value="<c:out value="${result.vtnsttusJdgVal}" />"/>
						<%-- <form:select path="vtnsttusJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="vtnsttusJdgVal">
							<form:option value="${result.vtnsttusJdgVal }" label="${result.vtnsttusJdgVal }"/>
						</form:select> --%>
					</td>
					<td colspan="2">
						<input type="text" name="sffcJdgVal" value="<c:out value="${result.sffcJdgVal}" />"/>
						<%-- <form:select path="sffcJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="sffcJdgVal">
							<form:option value="${result.sffcJdgVal }" label="${result.sffcJdgVal }"/>
						</form:select> --%>
					</td>
					<td colspan="2">
						<input type="text" name="accssrdJdgVal" value="<c:out value="${result.accssrdJdgVal}" />"/>
						<%-- <form:select path="accssrdJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="accssrdJdgVal">
							<form:option value="${result.accssrdJdgVal }" label="${result.accssrdJdgVal }"/>
						</form:select> --%>
					</td>
					<td colspan="2">
						<input type="text" name="etcJdgVal" value="<c:out value="${result.etcJdgVal}" />"/>
						<%-- <form:select path="etcJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="etcJdgVal">
							<form:option value="${result.etcJdgVal }" label="${result.etcJdgVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.snddpsitVal" /></th><!-- 저사량 -->
					<td colspan="3">
						<input type="text" name="snddpsitVal" value="<c:out value="${result.snddpsitVal}" />"/>
						<%-- <form:select path="snddpsitVal" title="${title1} ${inputSelect}" cssClass="txt" name="snddpsitVal">
							<form:option value="${result.snddpsitVal }" />
						</form:select> --%>
					</td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.snddpsitJdgVal" /></th><!-- 저사상태 판정값 -->
					<td colspan="3">
						<input type="text" name="snddpsitJdgVal" value="<c:out value="${result.snddpsitJdgVal}" />"/>
						<%-- <form:select path="snddpsitJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="snddpsitJdgVal">
							<form:option value="${result.snddpsitJdgVal }" />
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.chckrslt" /></th><!-- 점검결과 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
					<td colspan="7"><input type="text" name="fckRslt" value="<c:out value="${result.fckRslt}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
					<td colspan="7"><input type="text" name="mngmtr" value="<c:out value="${result.mngmtr}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
					<td colspan="7"><input type="text" name="appnRelis" value="<c:out value="${result.appnRelis}" />"/></td>
				</tr>				
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.specialNote" /></th><!-- 특이사항 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
					<td colspan="7"><input type="text" name="mainFcltSpecialNote" value="<c:out value="${result.mainFcltSpecialNote}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.subfclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
					<td colspan="7"><input type="text" name="subfcltSpecialNote" value="<c:out value="${result.subfcltSpecialNote}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckPcs.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
					<td colspan="7"><input type="text" name="etcSpecialNote" value="<c:out value="${result.etcSpecialNote}" />"/></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.svyComptVO.opinion" /></th><!-- 종합의견 -->
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion1" /></th><!-- 종합의견1 -->
					<td colspan="7"><input type="text" name="opinion1" value="<c:out value="${result.opinion1}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion2" /></th><!-- 종합의견2 -->
					<td colspan="7"><input type="text" name="opinion2" value="<c:out value="${result.opinion2}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion3" /></th><!-- 종합의견3 -->
					<td colspan="7"><input type="text" name="opinion3" value="<c:out value="${result.opinion3}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion4" /></th><!-- 종합의견4 -->
					<td colspan="7"><input type="text" name="opinion4" value="<c:out value="${result.opinion4}" />"/></td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.opinion5" /></th><!-- 종합의견5 -->
					<td colspan="7"><input type="text" name="opinion5" value="<c:out value="${result.opinion5}" />"/></td>
				</tr>
			</table>	
			