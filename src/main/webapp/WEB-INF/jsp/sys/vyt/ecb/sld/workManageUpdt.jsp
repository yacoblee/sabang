<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="pageTitle"><spring:message code="sysVytEcb.work.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysVytEcb.stripLand.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">사방사업</a></li>
		<li><a href="#">사업관리</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update"/></h3>
	<div id="contents">
		<form id="listForm" action="/sys/vyt/ecb/sld/updateWorkView.do" method="post">
			<input name="id" type="hidden" value="<c:out value='${result.id}'/>">
			<input name="schworkNm" type="hidden" value="<c:out value='${searchVO.workNm}'/>">
			<input name="schworkYear" type="hidden" value="<c:out value='${searchVO.workYear}'/>">
			<input name="schworkType" type="hidden" value="<c:out value='${searchVO.workType}'/>">
			<input name="schcreatUser" type="hidden" value="<c:out value='${searchVO.creatUser}'/>">
			<input name="pageSubIndex" type="hidden" value="<c:out value='${searchVO.pageSubIndex}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width: 14%;">
						<col style="width: 20%;">
						<col style="width: 14%;">
						<col style="width: 20%;">
						<col style="width: 12%;">
						<col style="width: 20%;">
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="sysVytEcb.workVO.workType" /></th>
							<td><c:out value="${result.workType}"/></td>
							<th><spring:message code="sysVytEcb.workVO.workName" /></th>
							<td colspan="3"><c:out value="${result.workNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysVytEcb.workVO.creatDt" /></th>
							<td><c:out value="${result.creatDt}"/></td>
							<th><spring:message code="sysVytEcb.workVO.creatUser" /></th>
							<td><c:out value="${result.creatUser}"/></td>
							<th><spring:message code="sysVytEcb.workVO.workCnt" /></th>
							<td><c:out value="${result.workCnt}"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<h3>${subTitle}</h3><br>
			<div class="BoardList"style=" width:100%; height:400px; overflow:auto;">
				<table class="check_table" summary="<spring:message code="common.summary.inqire" arguments="${subTitle}" />">
				<caption class="Hidden">${subTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width: 5%;">
						<col style="width: 10%;">
						<col style="width: 15%;">
						<col style="width: 25%;">
						<col style="width: 15%;">
						<col style="width: 15%;">
						<col style="width: 15%;">
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox"  id="fieldBook_all_check" onclick="javascript:fnSelectAllCheck();" /></th>
							<th><spring:message code="sysVytEcb.stripLandVO.sldId" /></th>
							<th><spring:message code="sysVytEcb.analManageVO.sd" /></th><!-- 시도명 -->
							<th><spring:message code="sysVytEcb.analManageVO.sgg" /></th><!-- 시군구명 -->
							<th><spring:message code="sysVytEcb.analManageVO.emd" /></th><!-- 읍면동명 -->
							<th><spring:message code="sysVytEcb.analManageVO.ri" /></th><!-- 리명 -->
							<th><spring:message code="sysVytEcb.analManageVO.jibun" /></th><!-- 지번 -->
						</tr>
					</thead>
					<tbody class="ov">
						<c:if test="${fn:length(resultList) == 0}">
							<tr>
								<td colspan="7" style="text-align:center;"><spring:message code="info.nodata.msg" /></td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><input type="checkbox" name="fieldBook_check" class="fieldBook_check" value="<c:out value="${result.sldId}"/>"></td>
								<td><c:out value="${result.sldId}"/></td>
								<td><c:out value="${result.sdNm}"/></td>
								<td><c:out value="${result.sggNm}"/></td>
								<td><c:out value="${result.emdNm}"/></td>
								<td><c:out value="${result.riNm}"/></td>
								<td><c:out value="${result.jibun}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fnUpdateWork('<c:out value="${result.id}"/>');"><spring:message code="title.delete" /></button>
					<button type="button" class="back-btn" onclick="javascript:fnWorkDetail('<c:out value="${result.id}"/>');"><spring:message code="button.reset" /></button>
				</div>
			</div>
		</form>
	</div>
</div>