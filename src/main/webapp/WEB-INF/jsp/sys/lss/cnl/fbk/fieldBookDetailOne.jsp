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
<c:set var="pageTitle"><spring:message code="sysLssCnl.fieldBookUpdtOne.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form id="listForm">
		<input name="smid" type="hidden" value="<c:out value='${result.smid}'/>">
		<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
		<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
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
							<td><c:out value="${result.sn}"/></td>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.type" /></th><!-- 유형 -->
							<td><c:out value="${result.svyType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.stripLandVO.id" /></th><!-- 조사ID -->
							<td><c:out value="${result.svyId}"/></td>
							<th><spring:message code="sysLssCnl.stripLandVO.region1" /></th><!-- 관할 -->
							<td><c:out value="${result.region1}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.stripLandVO.addr" /></th><!-- 주소 -->
							<td colspan="3"><c:out value="${result.svySd}"/> <c:out value="${result.svySgg}"/> <c:out value="${result.svyEmd}"/> <c:out value="${result.svyRi}"/> <c:out value="${result.svyJibun}"/> </td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.lonlat" /></th><!-- 좌표 -->
							<td colspan="3"><c:out value="${result.svyLat}"/> <c:out value="${result.svyLon}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnSltnHy" /></th><!-- 선정사유 -->
							<td colspan="3"><c:out value="${result.weakAppnSltnHy}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnNo" /></th><!-- 지정번호 -->
							<td><c:out value="${result.appnNo}"/></td>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnYear" /></th><!-- 지정년도 -->
							<td><c:out value="${result.appnYear}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnArea" /></th><!-- 지정면적 -->
							<td colspan="3"><c:out value="${result.appnArea}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.appnResn" /></th><!-- 지정사유 -->
							<td colspan="3"><c:out value="${result.appnResn}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnBsType" /></th><!-- 사업종 가능 -->
							<td colspan="3"><c:out value="${result.weakAppnBsType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnSttus" /></th><!-- 당시현황 -->
							<td colspan="3"><c:out value="${result.weakAppnSttus}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnMstOpn" /></th><!-- 당시종합의견 -->
							<td colspan="3"><c:out value="${result.weakAppnMstOpn}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnAreaSet" /></th><!-- 구역설정 -->
							<td colspan="3"><c:out value="${result.weakAppnAreaSet}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssCnl.fieldBookItemVO.weakAppnPosYn" /></th><!-- 가능여부 -->
							<td colspan="3"><c:out value="${result.weakAppnPosYn}"/></td>
						</tr>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<sec:authorize access="hasAnyRole('ROLE_ADMIN_CNL','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button>
							<button type="button" class="modify-btn" onclick="javascript:fnSelectFieldBookUpdtOne('<c:out value="${result.smid}"/>');"><spring:message code="button.update" /></button>
						</sec:authorize>
						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_CNL','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<c:if test="${access eq 'ADMIN' }">
								<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button>
							</c:if>
							<c:if test="${access eq 'USER' or access eq 'ADMIN' }">
								<button type="button" class="modify-btn" onclick="javascript:fnSelectFieldBookUpdtOne('<c:out value="${result.smid}"/>');"><spring:message code="button.update" /></button>
							</c:if>
						</sec:authorize>
						<button type="button" class="list-btn" onclick="javascript:fnFieldBookDetail('<c:out value='${mstId}'/>'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
