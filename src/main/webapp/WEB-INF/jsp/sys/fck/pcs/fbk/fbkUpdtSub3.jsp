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
					<td colspan="3"><input type="text" name="sn" value="<c:out value="${result.sn}" />" readonly="readonly"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyType" /></th><!-- 조사유형  -->
					<td colspan="3"><input type="text" name="svyType" value="<c:out value="${result.svyType}"/>" readonly="readonly"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.svyId"/></th><!-- 조사ID -->
					<td colspan="3"><input type="text" name="svyId" value="<c:out value="${result.svyId}" />" readonly="readonly"/></td>
					<th colspan="2"><spring:message code="sysFckPcs.fieldBookItemVO.commonly" /></th><!-- 속칭 -->
					<td colspan="3"><input type="text" name="commonly" value="<c:out value="${result.commonly}" />"/></td>
				</tr>
				<tr>
					<th colspan="2"><spring:message code="sysFckPcs.svyComptVO.knd"/></th><!-- 종류 -->
					<td colspan="3"><input type="text" name="knd" value="<c:out value="${result.knd}" />"/></td>
					<th colspan="5"></th>
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
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.fcltmainstrctu"/></th><!-- 주요공작물_시설명 -->
					<td colspan="7"><input type="text" name="fcltmainstrctu" value="<c:out value="${result.fcltmainstrctu }" />"/></td>
				</tr>	
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.cnstrcost" /></th><!-- 시공비(천원) -->
					<td colspan="7"><input type="text" name="cnstrcost" value="<c:out value="${result.cnstrcost }" />"/></td>
				</tr>
				<tr>
					<th colspan="10"><spring:message code="sysFckPcs.fieldBookItemVO.mainFclt" /></th><!-- 주요시설  -->
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.reinfc" /></th><!-- 보강시설  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.reinfcVal" /></th><!-- 보강시설 측정  -->
					<td colspan="4">
						<input type="text" name="reinfcVal" value="<c:out value="${result.reinfcVal }" />"/>
						<%-- <form:select path="reinfcVal" title="${title1} ${inputSelect}" cssClass="txt" name="reinfcVal">
							<form:option value="${result.reinfcVal }" label="${result.reinfcVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.reinfcJdgVal" /></th><!-- 보강시설 판정  -->
					<td colspan="4">
						<input type="text" name="reinfcJdgVal" value="<c:out value="${result.reinfcJdgVal }" />"/>
						<%-- <form:select path="reinfcJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="reinfcJdgVal">
							<form:option value="${result.reinfcJdgVal }" label="${result.reinfcJdgVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.prtc" /></th><!-- 보호시설  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.prtcVal" /></th><!-- 보호시설 측정값  -->
					<td colspan="4">
						<input type="text" name="prtcVal" value="<c:out value="${result.prtcVal }" />"/>
						<%-- <form:select path="prtcVal" title="${title1} ${inputSelect}" cssClass="txt" name="prtcVal">
							<form:option value="${result.prtcVal }" label="${result.prtcVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.prtcJdgVal" /></th><!-- 보호시설 판정값  -->
					<td colspan="4">
						<input type="text" name="prtcJdgVal" value="<c:out value="${result.prtcJdgVal }" />"/>
						<%-- <form:select path="prtcJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="prtcJdgVal">
							<form:option value="${result.prtcJdgVal }" label="${result.prtcJdgVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th rowspan="2" colspan="3"><spring:message code="sysFckPcs.svyComptVO.drng" /></th><!-- 배수시설  -->
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.drngVal" /></th><!-- 배수시설 측정값  -->
					<td colspan="4">
						<input type="text" name="drngVal" value="<c:out value="${result.drngVal }" />"/>
						<%-- <form:select path="drngVal" title="${title1} ${inputSelect}" cssClass="txt" name="drngVal">
							<form:option value="${result.drngVal }" label="${result.drngVal }"/>
						</form:select> --%>
					</td>
				</tr>
				<tr>
					<th colspan="3"><spring:message code="sysFckPcs.svyComptVO.drngJdgVal" /></th><!-- 배수시설 판정값  -->
					<td colspan="4">
						<input type="text" name="drngJdgVal" value="<c:out value="${result.drngJdgVal }" />"/>
						<%-- <form:select path="drngJdgVal" title="${title1} ${inputSelect}" cssClass="txt" name="drngJdgVal">
							<form:option value="${result.drngJdgVal }" label="${result.drngJdgVal }"/>
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
			