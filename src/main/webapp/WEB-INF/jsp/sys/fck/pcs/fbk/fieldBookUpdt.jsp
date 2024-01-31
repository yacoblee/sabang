<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="pageTitle"><spring:message code="sysFckApr.fieldBookDetail.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckApr.updatefieldBookItem.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form id="listForm" action="/sys/fck/pcs/fbk/updateFieldBookView.do" method="post">
				<input name="pageIndex" type="hidden" value="<c:out value='${searchItemVO.pageIndex}'/>">
				<input name="pageUnit" type="hidden" value="<c:out value='${searchItemVO.pageUnit}'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
					<td></td>	
					</tr>
					<tr>
					<td></td>
					</tr>
				</table>
			</div>
			<h3>${subTitle}</h3><br>
			<div class="BoardList"style=" width:100%; height:400px; overflow:auto;">
				<table class="check_table" summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width: 5%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 18%;">
						<col style="width: 31%;">
						<col style="width: 7%;">
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox"  id="fieldBook_all_check"/></th>
							<th><spring:message code="sysFckPcs.stripLandVO.id" /></th>
							<th><spring:message code="sysFckPcs.stripLandVO.type" /></th>
							<th><spring:message code="sysFckPcs.stripLandVO.lat" /></th>
							<th><spring:message code="sysFckPcs.stripLandVO.lon" /></th>
							<th><spring:message code="sysFckPcs.svyComptVO.region" /></th>
							<th><spring:message code="sysFckPcs.stripLandVO.addr" /></th>
							<th><spring:message code="sysFckPcs.stripLandVO.compAt" /></th>
						</tr>
					</thead>
					<tbody class="ov">
						<tr>
							<td><input type="checkbox" name="fieldBook_check" class="fieldBook_check" value=""></td>
							<td><c:out value="아이디"/></td>
							<td><c:out value="타입"/></td>
							<td><c:out value="위도"/></td>
							<td><c:out value="경도"/></td>
							<td><c:out value="지역"/></td>
							<td><c:out value="주소"/></td>
							<td><c:out value="결과"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fnUpdateCnrsSpce();"><spring:message code="title.delete" /></button>
					<button type="button" class="back-btn" onclick="javascript:fnFieldBookDetail();"><spring:message code="button.reset" /></button>
				</div>
			</div>
		</form>
	</div>
</div>