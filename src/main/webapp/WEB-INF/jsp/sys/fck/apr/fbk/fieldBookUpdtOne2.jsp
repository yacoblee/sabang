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
<!-- 계류보전 외관점검 테이블 -->
<table id="mrngTable" <c:if test="${result.svyType ne '계류보전 외관점검'}"> style="display: none;"</c:if>>
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
		<td colspan="3"><input name="createDt" type="text" value="<c:out value="${result.createDt}" />" readonly="readonly"/></td>
	</tr>
	<tr>
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.dsgArea"/></th><!-- 지정면적 -->
		<td colspan="3"><input type="text" name="dsgArea" value="<c:out value="${result.dsgArea}" />"/></td>
		<th colspan="2"><spring:message code="sysFckApr.svyComptVO.svyUser" /></th><!-- 점검자 -->
		<td colspan="3"><input name="chkUser" type="text" value="<c:out value="${result.chkUser}" />" readonly="readonly"/></td>
	</tr>
	<tr>
		<c:set var="title"><spring:message code="sysFckApr.svyComptVO.locplc"/></c:set>
		<th colspan="2">${title }</th><!-- 소재지 -->
		<td colspan="8">
			<form:select path="svySd" id="svySdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministCtprvnNmChange('mrngTable'); return false;">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
			</form:select>
			<form:select path="svySgg" id="svySggView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministSignguNmChange('mrngTable'); return false;">
				<form:option value="" label="${inputSelect}"/>
				<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
			</form:select>
			<form:select path="svyEmd" id="svyEmdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministEmdNmChange('mrngTable'); return false;">
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
		<th rowspan="2" colspan="3"><spring:message code="sysFckApr.svyComptVO.locInfo"/></th><!-- 현지계측 위치정보 -->
		<th>시점</th>
		<td colspan="6">
			<span>위도</span>
			<input type="text" name="svyLatD" value="<c:out value="${result.svyLatD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
			<input type="text" name="svyLatM" value="<c:out value="${result.svyLatM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
			<input type="text" name="svyLatS" value="<c:out value="${result.svyLatS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
			<span class="ml5">경도</span>
			<input type="text" name="svyLonD" value="<c:out value="${result.svyLonD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
			<input type="text" name="svyLonM" value="<c:out value="${result.svyLonM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
			<input type="text" name="svyLonS" value="<c:out value="${result.svyLonS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
		</td>
	</tr>
	<tr>
		<th>종점</th>
		<td colspan="6">
		<span>위도</span>
			<input type="text" name="eLatD" value="<c:out value="${result.eLatD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
			<input type="text" name="eLatM" value="<c:out value="${result.eLatM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
			<input type="text" name="eLatS" value="<c:out value="${result.eLatS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
			<span class="ml5">경도</span>                                           
			<input type="text" name="eLonD" value="<c:out value="${result.eLonD}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
			<input type="text" name="eLonM" value="<c:out value="${result.eLonM}"/>" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
			<input type="text" name="eLonS" value="<c:out value="${result.eLonS}"/>" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"
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
	<tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.chkdam" /></c:set><!-- 골막이-->
                       <th rowspan="2" colspan="3">${title}</th>
                       <th colspan="2"><spring:message code="sysFckApr.svyComptVO.chkdamVal" /></th>
                       <td colspan="5">
                               <form:select path="chkdamCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${mooringMainfcltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                           <form:input class="selectJdgVal" path="chkdamVal" title="${title} ${inputTxt}"/>
                               <div><form:errors path="chkdamVal" cssClass="error" /></div>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.chkdamJdgVal" /></c:set>
                       <th colspan="2">${title}</th>
                       <td colspan="5">
                           <form:select path="chkdamJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                       </td>
                   </tr>                            
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.rvtmnt" /></c:set><!-- 기슭막이-->
                       <th rowspan="2" colspan="3">${title}</th>
                       <th colspan="2"><spring:message code="sysFckApr.svyComptVO.rvtmntVal" /></th>
                       <td colspan="5">
                               <form:select path="rvtmntCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${mooringMainfcltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                           <form:input class="selectJdgVal" path="rvtmntVal" title="${title} ${inputTxt}"/>
                               <div><form:errors path="rvtmntVal" cssClass="error" /></div>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.rvtmntJdgVal" /></c:set>
                       <th colspan="2">${title}</th>
                       <td colspan="5">
                           <form:select path="rvtmntJdgVal" title="${title} ${inputSelect}" cssClass="txt">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${jdgValCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                       </td>
                   </tr>
                   
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.grdstabl" /></c:set><!-- 바닥막이-->
                       <th rowspan="2" colspan="3">${title}</th>
                       <th colspan="2"><spring:message code="sysFckApr.svyComptVO.grdstablVal" /></th>
                       <td colspan="5">
                               <form:select path="grdstablCd" title="${title} ${inputSelect}" cssClass="txt selectVal" onChange="fncGetSelectVal(event);">
                               <form:option value="" label="${inputSelect}"/>
                               <form:options items="${mooringMainfcltCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                           </form:select>
                           <form:input class="selectJdgVal" path="grdstablVal" title="${title} ${inputTxt}"/>
                               <div><form:errors path="grdstablVal" cssClass="error" /></div>
                       </td>
                   </tr>
                   <tr>
                       <c:set var="title"><spring:message code="sysFckApr.svyComptVO.grdstablJdgVal" /></c:set>
                       <th colspan="2">${title}</th>
                       <td colspan="5">
                           <form:select path="grdstablJdgVal" title="${title} ${inputSelect}" cssClass="txt">
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
                    <c:set var="title"><spring:message code="sysFckApr.svyComptVO.mooringJdgVal" /></c:set><!-- 계류상태 판정값 -->
                    <th colspan="3">${title}</th>
                    <td colspan="7">
                        <form:select path="mooringJdgVal" title="${title} ${inputSelect}" cssClass="txt">
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
