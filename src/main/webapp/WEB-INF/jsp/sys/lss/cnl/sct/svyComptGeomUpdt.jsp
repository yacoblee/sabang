<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssCnl.svyComptGeom.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
		<li><a href="#">조사완료</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 공간정보 수정 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/wka/cnl/updateCnlSvyComptUpdtView.do" method="post">
			<!-- 검색조건 파라메터 추가 : 저장 or 목록으로 갈때 조사완료화면에서 검색이 이루어진 경우 검색화면으로 돌아가기 위함 -->
			<input name="svyTypesch" type="hidden" value="${searchVO.svyType}">
			<input name="svyYearsch" type="hidden" value="${searchVO.svyYear}">
			<input name="svyMonthsch" type="hidden" value="${searchVO.svyMonth}">
			<input name="svySdsch" type="hidden" value="${searchVO.svySd}">
			<input name="svySggsch" type="hidden" value="${searchVO.svySd}">
			<input name="svyEmdsch" type="hidden" value="${searchVO.svyEmd}">
			<input name="svyRisch" type="hidden" value="${searchVO.svyRi}">
			<input name="svyIdsch" type="hidden" value="${searchVO.svyId}">
			<input name="svyUsersch" type="hidden" value="${searchVO.svyUser}">
			<input name="mstNmsch" type="hidden" value="${searchVO.mstNm}">
			<input name="pageIndexsch" type="hidden" value="${searchVO.pageIndex}">
			<input name="pageValsch" type="hidden" value="${searchVO.pageUnit}">
			
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			<input name="exmnnid" type="hidden" value="<c:out value="${result.exmnnid}" />">
			<input name="mstid" type="hidden" value="<c:out value="${result.mstid}" />">
			<input name="svyid" type="hidden" value="<c:out value="${result.svyid}" />">
			<input name="statmap" type="hidden" value="<c:out value="${result.statmap}" />">
			<input name="evadata" type="hidden" value="<c:out value="${result.evadata}" />">
			<input name="evamapnm" type="hidden" value="<c:out value="${result.evamapnm}" />">
			<input name="svydata" id="svydata" type="hidden" value="<c:out value="${result.svydata}" />">
			
			<input name="vnaraPntWkt" id="vnaraPntWkt" type="hidden" value="<c:out value="${vnaraPntLne.vnarapntwkt}" />">
			<input name="vnaraLneWkt" type="hidden" value="<c:out value="${vnaraPntLne.vnaralnewkt}" />">
			<input name="vnaraPlgnWkt01" type="hidden" value="${plgn01}"><!-- 사방댐 -->
			<input name="vnaraPlgnWkt02" type="hidden" value="${plgn02}"><!-- 계류보전 -->
			<input name="vnaraPlgnWkt03" type="hidden" value="${plgn03}"><!-- 유역면적 -->
			<input name="vnaraPlgnWkt04" type="hidden" value="${plgn04}"><!-- 산지사방 -->
			
			<div class="BoardDetail">
				<div class="ol-edit-control-btns" id="edit-btn">
					<button type="button" class="land02-btn-m" title="유역면적" value="03">유역면적</button>
					<button type="button" class="land03-btn-m" title="사방댐" value="01">사방댐</button>
					<button type="button" class="land04-btn-m" title="계류보전" value="02">계류보전</button>
					<button type="button" class="land05-btn-m" title="산지사방" value="04">산지사방</button>
					<button type="button" class="land06-btn-m" title="대피로" value="05">대피로</button>
				</div>
				<div class="deletBtnArea" style="display: none;">
					<button type="button" class="landDel-btn-m" title="삭제">삭제</button>
				</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />" class="mb30">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th><spring:message code="sysLssWka.svyComptGeom.svyDt" /></th><!-- 조사일자 -->
							<td><c:out value='${result.svydt}'/></td>
							<th><spring:message code="sysLssWka.svyComptGeom.svyUser" /></th><!-- 조사자 -->
							<td><c:out value="${result.svyuser}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysLssWka.svyComptGeom.addr" /></th><!-- 주소 -->
							<td colspan="3"><c:out value="${result.sdnm}"/> <c:out value="${result.sggnm}"/> <c:out value="${result.emdnm}"/> <c:out value="${result.rinm}"/> <c:out value="${result.jibun}"/></td>
						</tr>
					</tbody>
				</table>
				<div id="map-div" class="map-div on">
					<div class="ol-custom-control ol-basemap-control" id="toggle-btn">
				        <button type="button" class="btn-map-selector" title="일반지도" value="base">일반지도</button>
				        <button type="button" class="btn-map-selector" title="항공지도" value="satellite">항공지도</button>
				    </div>
				    <div class="hybrid-check">
						<input type="checkbox" value="hybrid" id="hybrid">
						<label for="hybrid">하이브리드</label>
				    </div>
					<div id="map" class="map"></div>
				</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<c:if test="${not empty result.sttusimg}">
							<button type="button" class="save-btn" onclick="javascript:fnShowStatImg(<c:out value="${result.gid}" />); return false;">현황도 이미지</button>
						</c:if>
						<button type="button" class="save-btn" onclick="javascript:fnGeomSave(); return false;"><spring:message code="button.save" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>