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
<!-- 산지사방 외관점검 테이블 -->
<table id="mtcTable" <c:if test="${result.svyType ne '산지사방 외관점검'}"> style="display: none;"</c:if>>
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
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.knd"/></th><!-- 종류 -->
		<td colspan="3">
			<form:select path="knd" title="${title} ${inputSelect}" cssClass="txt">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${ecnrKndCodeList}" itemValue="codeNm" itemLabel="codeNm" />
			</form:select>
		</td>
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyDt" /></th><!-- 점검일시 -->
		<td colspan="3"><input type="text" name= "createDt" value="<c:out value="${result.createDt}" />" readonly="readonly"/></td>
	</tr>
	<tr>
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dsgArea"/></th><!-- 지정면적 -->
		<td colspan="3"><input type="text" name="dsgArea" value="<c:out value="${result.dsgArea}" />"/></td>
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
		<td colspan="3"><input type="text" name= "chkUser" value="<c:out value="${result.chkUser}" />" readonly="readonly"/></td>
	</tr>
	<tr>
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.locplc"/></th><!-- 소재지 -->
		<td colspan="8" class="locplc3">
			<form:select path="svySd" id="svySdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministCtprvnNmChange('mtcTable'); return false;">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
			</form:select>
			<form:select path="svySgg" id="svySggView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministSignguNmChange('mtcTable'); return false;">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
			</form:select>
			<form:select path="svyEmd" id="svyEmdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministEmdNmChange('mtcTable'); return false;">
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
		<td colspan="8">
			<span>위도</span>
			<input type="text" name="svyLatD" value="${result.svyLatD}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
			<input type="text" name="svyLatM" value="${result.svyLatM}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
			<input type="text" name="svyLatS" value="${result.svyLatS}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
			<span class="ml5">경도</span>
			<input type="text" name="svyLonD" value="${result.svyLonD}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
			<input type="text" name="svyLonM" value="${result.svyLonM}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
			<input type="text" name="svyLonS" value="${result.svyLonS}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
		</td>
	</tr>
	<tr>
		<th rowspan="4" colspan="3"><spring:message code="sysFckApr.svyComptVO.fclt"/></th><!-- 시설제원 -->
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
		<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltLng"/></th><!-- 길이 -->
		<td colspan="4"><input type="text" name="fcltLng" value="<c:out value="${result.fcltLng}" />"/></td>
	</tr>
	<tr>
		<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltRng"/></th><!-- 폭 -->
		<td colspan="4"><input type="text" name="fcltRng" value="<c:out value="${result.fcltRng}" />"/></td>
	</tr>
	<tr>
		<th colspan="3"><spring:message code="sysFckApr.svyComptVO.fcltDept"/></th><!-- 깊이 -->
		<td colspan="4"><input type="text" name="fcltDept" value="<c:out value="${result.fcltDept}" />"/></td>
	</tr>
	<tr>
		<th colspan="10"><spring:message code="sysFckApr.svyComptVO.mainfclt"/></th><!-- 주요시설 -->
	</tr>
<!-- 						<tr> -->
<%-- 							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.reinfc"/></th><!-- 보강시설 --> --%>
<%-- 							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.reinfcVal"/></th><!-- 측정값 --> --%>
<%-- 							<td colspan="4"><input type="text" name="reinfcVal" value="<c:out value="${result.reinfcVal}" />"/></td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<%-- 							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.reinfcJdgVal"/></th><!-- 판정값 --> --%>
<%-- 							<td colspan="4"><input type="text" name="reinfcJdgVal" value="<c:out value="${result.reinfcJdgVal}" />"/></td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<%-- 							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.prtc"/></th><!-- 보호시설 --> --%>
<%-- 							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.prtcVal"/></th><!-- 측정값 --> --%>
<%-- 							<td colspan="4"><input type="text" name="prtcVal" value="<c:out value="${result.prtcVal}" />"/></td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<%-- 							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.prtcJdgVal"/></th><!-- 판정값 --> --%>
<%-- 							<td colspan="4"><input type="text" name="prtcJdgVal" value="<c:out value="${result.prtcJdgVal}" />"/></td> --%>
<!-- 						</tr> -->
<!-- 						<tr> -->
<%-- 							<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.drng"/></th><!-- 배수시설 --> --%>
<%-- 							<th colspan="3"><spring:message code="sysFckApr.svyComptVO.drngVal"/></th><!-- 측정값 --> --%>
<%-- 							<td colspan="4"><input type="text" name="drngVal" value="<c:out value="${result.drngVal}" />"/></td> --%>
<!-- 						</tr> -->
	<tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.reinfc" /></c:set><!-- 보강시설-->
                       <th rowspan="2" colspan="3">${title}</th>
                       <th colspan="2"><spring:message code="sysFckApr.svyComptVO.reinfcVal" /></th>
                       <td colspan="5">
                        <form:select path="reinfcCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                            <form:option value="" label="${inputSelect}"/>
                            <form:options items="${reinfcCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                        </form:select>
                           <form:input class="selectJdgVal" path="reinfcVal" title="${title} ${inputTxt}"/>
                           <div><form:errors path="reinfcVal" cssClass="error" /></div>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.reinfcJdgVal" /></c:set>
                       <th colspan="2">${title}</th>
                       <td colspan="5">
                           <form:select path="reinfcJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.prtc" /></c:set><!-- 보호시설-->
                       <th rowspan="2" colspan="3">${title}</th>
                       <th colspan="2"><spring:message code="sysFckApr.svyComptVO.prtcVal" /></th>
                       <td colspan="5">
                        <form:select path="prtcCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                            <form:option value="" label="${inputSelect}"/>
                            <form:options items="${prtcCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                        </form:select>
                        <form:input class="selectJdgVal" path="prtcVal" title="${title} ${inputTxt}"/>
                        <div><form:errors path="prtcVal" cssClass="error" /></div>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.prtcJdgVal" /></c:set>
                       <th colspan="2">${title}</th>
                       <td colspan="5">
                           <form:select path="prtcJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.drng" /></c:set><!-- 배수시설-->
                       <th rowspan="2" colspan="3">${title}</th>
                       <th colspan="2"><spring:message code="sysFckApr.svyComptVO.drngVal" /></th>
                       <td colspan="5">
                        <form:select path="drngCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                            <form:option value="" label="${inputSelect}"/>
                            <form:options items="${drngCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                        </form:select>
                        <form:input class="selectJdgVal" path="drngVal" title="${title} ${inputTxt}"/>
                        <div><form:errors path="drngVal" cssClass="error" /></div>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.drngJdgVal" /></c:set>
                       <th colspan="2">${title}</th>
                       <td colspan="5">
                        <form:select path="drngJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                            <form:option value="" label="${inputSelect}"/>
                            <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                        </form:select>
                       </td>
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
		<th colspan="3"><spring:message code="sysFckApr.svyComptVO.slpJdgVal" /></th><!-- 사면상태 -->
		<td colspan="7">
			<form:select path="slopeJdgVal" title="${title} ${inputSelect}" cssClass="txt">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
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
