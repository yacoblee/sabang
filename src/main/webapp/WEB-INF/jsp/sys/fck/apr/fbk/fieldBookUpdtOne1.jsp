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
<c:set var="pageTitle"><spring:message code="sysFckApr.fieldBookUpdtOne.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckApr.fieldBookItemList.title"/></c:set>
<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
	<!-- 사방댐 외관점검 테이블 -->
	<table id="ectrsTable" <c:if test="${result.svyType ne '사방댐 외관점검'}"> style="display: none;"</c:if>>
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
			<td colspan="3"><input type="text" name="sn" value="<c:out value="${result.sn}" />"/></td>
			<c:set var="title"><spring:message code="sysFckApr.svyComptVO.svyType" /></c:set>
			<th colspan="2">${title }</th><!-- 조사유형  -->
			<td colspan="3">
				<form:select path="svyType" title="${title} ${inputSelect}" cssClass="txt" onchange="fnSelectFieldBookTypeChange(this.value); return false;">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${aprList_result}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyId"/></th><!-- 조사ID -->
			<td colspan="3"><input type="text" name="svyId" value="<c:out value="${result.svyId}" />" readonly="readonly"/></td>
			<th colspan="2"><spring:message code="sysFckApr.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
			<td colspan="3"><input type="text" name="commonly" value="<c:out value="${result.commonly}" />"/></td>
		</tr>
		<tr>
			<c:set var="title"><spring:message code="sysFckApr.svyComptVO.knd"/></c:set>
			<th colspan="2">${title}</th><!-- 종류 -->
			<td colspan="3">
				<form:select path="knd" title="${title} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${ecnrKndCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.ecnrForm" /></th><!-- 형식  -->
			<td colspan="3"><input type="text" name="ecnrForm" value="<c:out value="${result.ecnrForm}" />"/></td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.ecnrRnum"/></th><!-- 사방댐관리번호 -->
			<td colspan="3"><input type="text" name="ecnrRnum" value="<c:out value="${result.ecnrRnum}" />"/></td>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시  -->
			<td colspan="3"><input type="text" name="createDt" value="<c:out value="${result.createDt}" />" readonly="readonly"/></td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.nationSpotNum"/></th><!-- 국가지점번호 -->
			<td colspan="3"><input type="text" name="nationSpotNum" value="<c:out value="${result.nationSpotNum}" />"/></td>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자  -->
			<td colspan="3"><input type="text" name="chkUser" value="<c:out value="${result.chkUser}" />" readonly="readonly"/></td>
		</tr>
		<tr>
			<c:set var="title"><spring:message code="sysFckApr.svyComptVO.locplc"/></c:set>
			<th colspan="2">${title }</th><!-- 소재지-->
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
   				<div><form:errors path="svyJibun" cssClass="error wd20" /></div>
			</td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보 -->
<%-- 							<td colspan="4"><c:out value="${result.svyLat}"/> <c:out value="${result.svyLon}"/></td> --%>
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
			<th rowspan="4" colspan="3"><spring:message code="sysFckApr.svyComptVO.fclt" /></th><!-- 시설제원 -->
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltYear"/></th><!-- 시설년도 -->
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
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltUprHg" /></th><!-- 상장 -->
			<td colspan="4"><input type="text" name="fcltUprHg" value="<c:out value="${result.fcltUprHg}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltLwrHg" /></th><!-- 하장 -->
			<td colspan="4"><input type="text" name="fcltLwrHg" value="<c:out value="${result.fcltLwrHg}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltStkHg" /></th><!-- 유효고 -->
			<td colspan="4"><input type="text" name="fcltStkHg" value="<c:out value="${result.fcltStkHg}" />"/></td>
		</tr>
		<tr>
			<th colspan="10"><spring:message code="sysFckApr.svyComptVO.mainfclt" /></th><!-- 주요시설 -->
		</tr>
		<tr>
                        <c:set var="title"><spring:message code="sysFckApr.svyComptVO.orginlDam" /></c:set><!-- 본댐-->
                        <th rowspan="2" colspan="3">${title}</th>
                        <th colspan="2"><spring:message code="sysFckApr.svyComptVO.orginlDamVal" /></th>
                        <td colspan="5">
                            <form:select path="orginlDamCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                <form:option value="" label="${inputSelect}"/>
                                <form:options items="${orginlDamCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
                            <form:input class="selectJdgVal" path="orginlDamVal" title="${title} ${inputTxt}"/>
                                <div><form:errors path="orginlDamVal" cssClass="error" /></div>
                        </td>
                    </tr>
                    <tr>
                        <c:set var="title"><spring:message code="sysFckApr.svyComptVO.orginlDamJdgVal" /></c:set>
                        <th colspan="2">${title}</th>
                        <td colspan="5">
                            <form:select path="orginlDamJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                <form:option value="" label="${inputSelect}"/>
                                <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <c:set var="title"><spring:message code="sysFckApr.svyComptVO.sideWall" /></c:set><!-- 측벽-->
                        <th rowspan="2" colspan="3">${title}</th>
                        <th colspan="2"><spring:message code="sysFckApr.svyComptVO.sideWallVal" /></th>
                        <td colspan="5">
                            <form:select path="sideWallCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                <form:option value="" label="${inputSelect}"/>
                                <form:options items="${sideWallCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
                            <form:input class="selectJdgVal" path="sideWallVal" title="${title} ${inputTxt}"/>
                                <div><form:errors path="sideWallVal" cssClass="error" /></div>
                        </td>
                    </tr>
                    <tr>
                        <c:set var="title"><spring:message code="sysFckApr.svyComptVO.sideWallJdgVal" /></c:set>
                        <th colspan="2">${title}</th>
                        <td colspan="5">
                                <form:select path="sideWallJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                <form:option value="" label="${inputSelect}"/>
                                <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <c:set var="title"><spring:message code="sysFckApr.svyComptVO.dwnspt" /></c:set><!-- 물받이-->
                        <th rowspan="2" colspan="3">${title}</th>
                        <th colspan="2"><spring:message code="sysFckApr.svyComptVO.dwnsptVal" /></th>
                        <td colspan="5">
                            <form:select path="dwnsptCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                                <form:option value="" label="${inputSelect}"/>
                                <form:options items="${dwnSptCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
                            <form:input class="selectJdgVal" path="dwnsptVal" title="${title} ${inputTxt}"/>
                                <div><form:errors path="dwnsptVal" cssClass="error" /></div>
                        </td>
                    </tr>
                    <tr>
                        <c:set var="title"><spring:message code="sysFckApr.svyComptVO.dwnsptJdgVal" /></c:set>
                        <th colspan="2">${title}</th>
                        <td colspan="5">
                            <form:select path="dwnsptJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                                <form:option value="" label="${inputSelect}"/>
                                <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
                        </td>
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
			<td colspan="2">
				<form:select path="flugtJdgVal" title="${title1} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
			<td colspan="2">
   				<form:select path="vtnsttusJdgVal" title="${title2} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
			<td colspan="2">
				<form:select path="sffcJdgVal" title="${title3} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
			<td colspan="2">
				<form:select path="accssrdJdgVal" title="${title4} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
			<td colspan="2">
				<form:select path="etcJdgVal" title="${title5} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.snddpsitVal" /></th><!-- 저사량 -->
			<td colspan="3"><input type="text" name="snddpsitVal" value="<c:out value="${result.snddpsitVal}" />"/></td>
			<th colspan="2"><spring:message code="sysFckApr.svyComptVO.snddpsitJdgVal" /></th><!-- 저사상태 판정값 -->
			<td colspan="3">
				<form:select path="snddpsitJdgVal" id="snddpsitJdgVal" title="${title2} ${inputSelect}" cssClass="txt">
                             <form:option value="" label="${inputSelect}"/>
                             <form:options items="${snddpsitJdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                            </form:select>
			</td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fckRslt" /></th><!-- 최종점검결과 -->
			<td colspan="7">
			<form:select path="fckRslt" title="${title} ${inputSelect}" cssClass="txt">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${fckRsltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
			</form:select></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.mngmtr" /></th><!-- 조치사항 -->
			<td colspan="7">
   				<form:select path="mngmtr" title="${title} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${mngmtrCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.appnRelis" /></th><!-- 지정해제 -->
			<td colspan="7">
				<form:select path="appnRelis" title="${title} ${inputSelect}" cssClass="txt">
					<form:option value="" label="${inputSelect}"/>
					<form:options items="${appnRelisCodeList}" itemValue="codeNm" itemLabel="codeNm" />
				</form:select>
			</td>
		</tr>
		<tr>
			<th colspan="10"><spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 특이사항 -->
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.mainFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 주요시설 특이사항 -->
			<td colspan="7"><input type="text" name="mainFcltSpecialNote" value="<c:out value="${result.mainFcltSpecialNote}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.subfclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 부대시설 특이사항 -->
			<td colspan="7"><input type="text" name="subfcltSpecialNote" value="<c:out value="${result.subfcltSpecialNote}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.fieldBookItemVO.ectFclt"/> <spring:message code="sysFckApr.fieldBookItemVO.specialNote"/></th><!-- 기타시설 특이사항 -->
			<td colspan="7"><input type="text" name="etcSpecialNote" value="<c:out value="${result.etcSpecialNote}" />"/></td>
		</tr>
		<tr>
			<th colspan="10"><spring:message code="sysFckApr.svyComptVO.opinion" /></th><!-- 종합의견 -->
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion1" /></th><!-- 종합의견1 -->
			<td colspan="7"><input type="text" name="opinion1" value="<c:out value="${result.opinion1}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion2" /></th><!-- 종합의견2 -->
			<td colspan="7"><input type="text" name="opinion2" value="<c:out value="${result.opinion2}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion3" /></th><!-- 종합의견3 -->
			<td colspan="7"><input type="text" name="opinion3" value="<c:out value="${result.opinion3}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion4" /></th><!-- 종합의견4 -->
			<td colspan="7"><input type="text" name="opinion4" value="<c:out value="${result.opinion4}" />"/></td>
		</tr>
		<tr>
			<th colspan="3"><spring:message code="sysFckApr.svyComptVO.opinion5" /></th><!-- 종합의견5 -->
			<td colspan="7"><input type="text" name="opinion5" value="<c:out value="${result.opinion5}" />"/></td>
		</tr>
	</table>
