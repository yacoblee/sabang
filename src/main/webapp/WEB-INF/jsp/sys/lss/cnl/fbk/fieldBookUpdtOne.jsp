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
<c:set var="pageTitle"><spring:message code="sysLssBsc.detailfieldBookItem.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysLssCnl.fieldBookItemList.title"/></c:set>
<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
<c:set var="inputSelect"><spring:message code="input.cSelect"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" commandName="result" action="/sys/lss/cnl/updateFieldBookDetailOne.do" method="post">
		<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
			<input name="smid" type="hidden" value="<c:out value='${result.smid}'/>">
			<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
			<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
			<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
			<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">	
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
			
			<input name="schsvyid" type="hidden" value="<c:out value='${searchVO.svyid}'/>"/>
			<input name="schsd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
			<input name="schsgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
			<input name="schemd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
			<input name="schri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>			
			<div class="BoardDetail">
				<table>
					<colgroup>
						<col width="25%">
						<col width="25%">
						<col width="25%">
						<col width="25%">
					</colgroup>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.sn" /></th><!-- 순번 -->
							<td><input name="sn" value="<c:out value="${result.sn}"/>"/></td>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.type" /></th><!-- 유형 -->
							<td>
								<select name="svyType">
									<option value="">선택하세요</option>
									<option value="토석류" <c:if test ="${result.svyType eq '토석류'}">selected="selected"</c:if>>토석류</option>
									<option value="산사태" <c:if test ="${result.svyType eq '산사태'}">selected="selected"</c:if>>산사태</option>
								</select>
							</td>	
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.svyComptVO.svyId" /></th><!-- 조사ID -->
							<td><input name="svyId" value="<c:out value="${result.svyId}"/>" readonly="readonly"/></td>
							<th><spring:message code="sysLssCnl.svyComptVO.region1" /></th><!-- 관할1 -->
							<td><input name="region1" value="<c:out value="${result.region1}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.stripLandVO.addr" /></th>
							<td colspan="3">
								<form:select path="svySd" id="svySdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministCtprvnNmChange(); return false;"><!-- 시도 -->
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
								</form:select>
								<form:select path="svySgg" id="svySggView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministSignguNmChange(); return false;"><!-- 시군구 -->
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${sggCodeList}" itemValue="adminNm" itemLabel="adminNm" />
								</form:select>
								<form:select path="svyEmd" id="svyEmdView" title="${title} ${inputSelect}" cssClass="txt wd15" onchange="fncAdministEmdNmChange(); return false;"><!-- 읍면동 -->
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${emdCodeList}" itemValue="adminNm" itemLabel="adminNm" />
								</form:select>
								<form:select path="svyRi" id="svyRiView" title="${title} ${inputSelect}" cssClass="txt wd15"><!-- 리 -->
									<form:option value="" label="${inputSelect}"/>
									<form:options items="${riCodeList}" itemValue="adminNm" itemLabel="adminNm" />
								</form:select>
								<form:input path="svyJibun" title="${title} ${inputTxt}"/><!-- 지번 -->
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.lonlat" /></th><!-- 좌표 -->
<%-- 							<td colspan="3"><c:out value="${result.lat}"/> <c:out value="${result.lon}"/></td> --%>
							<td colspan="3">
								<span><spring:message code="sysLssCnl.fieldBookItemVO.lat" /></span><!-- 위도 -->
								<input type="text" name="svy_latD" value="${result.svy_latD}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="svy_latM" value="${result.svy_latM}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="svy_latS" value="${result.svy_latS}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"N
								<span class="ml5"><spring:message code="sysLssCnl.fieldBookItemVO.lon" /></span><!-- 경도 -->
								<input type="text" name="svy_lonD" value="${result.svy_lonD}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">°
								<input type="text" name="svy_lonM" value="${result.svy_lonM}" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" style="width: 30px;">'
								<input type="text" name="svy_lonS" value="${result.svy_lonS}" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" style="width: 50px;">"E
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnSltnHy" /></th><!-- 선정사유 -->
							<td colspan="3"><input name="weakAppnSltnHy" class="w100p" value="<c:out value="${result.weakAppnSltnHy}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnNo" /></th><!-- 지정번호 -->
							<td><input name="appnNo" value="<c:out value="${result.appnNo}"/>"/></td>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnYear" /></th><!-- 지정년도 -->
							<td>
								<select name="appnYear">
									<option value="">선택</option>
									<c:set var="today" value="<%=new java.util.Date()%>" />
									<fmt:formatDate value="${today}" pattern="yyyy" var="now"/> 
									<c:forEach begin="1990" end="${now}" var="idx" step="1">
									<option value="<c:out value="${idx}" />" <c:if test="${result.appnYear eq idx}">selected="selected"</c:if>><c:out value="${idx}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnArea" /></th><!-- 지정면적 -->
							<td colspan="3"><input name="appnArea" value="<c:out value="${result.appnArea}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnResn" /></th><!-- 지정사유 -->
							<td colspan="3"><input name="appnResn" class="w100p" value="<c:out value="${result.appnResn}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnBsType" /></th><!-- 사업종 가능 -->
							<td colspan="3"><input name="weakAppnBsType" class="w100p" value="<c:out value="${result.weakAppnBsType}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnSttus" /></th><!-- 당시현황 -->
							<td colspan="3"><input name="weakAppnSttus" class="w100p" value="<c:out value="${result.weakAppnSttus}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnMstOpn" /></th><!-- 당시종합의견 -->
							<td colspan="3"><input name="weakAppnMstOpn" class="w100p" value="<c:out value="${result.weakAppnMstOpn}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnAreaSet" /></th><!-- 구역설정 -->
							<td colspan="3"><input name="weakAppnAreaSet" class="w100p" value="<c:out value="${result.weakAppnAreaSet}"/>"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnPosYn" /></th><!-- 가능여부 -->
							<td colspan="3">
								<select name="weakAppnPosYn">
									<option value="">선택하세요</option>
									<option value="예" <c:if test="${result.weakAppnPosYn eq '예'}">selected="selected"</c:if>>예</option>
									<option value="아니오" <c:if test="${result.weakAppnPosYn eq '아니오'}">selected="selected"</c:if>>아니오</option>
								</select>
							</td>
						</tr>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
<%-- 						<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button> --%>
						<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookDetailOne();"><spring:message code="button.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnSelectFieldBookDetailOne('<c:out value="${result.smid}"/>'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
