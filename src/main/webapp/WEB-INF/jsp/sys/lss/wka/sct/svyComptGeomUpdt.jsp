<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysLssWka.svyComptGeom.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역실태조사</a></li>
		<li><a href="#">조사완료</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3><!-- 공간정보 수정 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/lss/wka/sct/updateWkaSvyComptUpdt.do" method="post">
			<!-- 검색조건 파라메터 추가 : 저장 or 목록으로 갈때 조사완료화면에서 검색이 이루어진 경우 검색화면으로 돌아가기 위함 -->
			<input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
			<input name="schsvyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
			<input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
			<input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
			<input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
			<input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
			<input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
			<input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
			<input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
			<input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
			<input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<input name="schjdgmntgrad" type="hidden" value="<c:out value='${searchVO.jdgmntgrad}'/>">
			
			<input name="exmnnid" type="hidden" value="<c:out value="${result.exmnnid}" />">
			<input name="mstid" type="hidden" value="<c:out value="${result.mstid}" />">
			<input name="svyid" type="hidden" value="<c:out value="${result.svyid}" />">
			<input name="evadata" type="hidden" value="<c:out value="${result.evadata}" />">
			<input name="evamapnm" type="hidden" value="<c:out value="${result.evamapnm}" />">
			<input name="evamap" type="hidden" value="<c:out value="${result.evamap}" />"/><!-- 대피지점 -->
			<input name="svydata" id="svydata" type="hidden" value="<c:out value="${result.svydata}" />">
			<input name="svyType" type="hidden" value="${result.svytype}">
			
			<input name="vnaraPntWkt" id="vnaraPntWkt" type="hidden" value="<c:out value="${vnaraPntLne.vnarapntwkt}" />">
			<input name="vnaraLneWkt" type="hidden" value="<c:out value="${vnaraPntLne.vnaralnewkt}" />">
			<input name="vnaraPlgnWkt01" type="hidden" value="${plgn01}"><!-- 사방댐 -->
			<input name="vnaraPlgnWkt02" type="hidden" value="${plgn02}"><!-- 계류보전 -->
			<input name="vnaraPlgnWkt03" type="hidden" value="${plgn03}"><!-- 유역면적 -->
			<input name="vnaraPlgnWkt04" type="hidden" value="${plgn04}"><!-- 산지사방 -->
			
			<input name="lndslddgWkt" type="hidden" value="<c:out value="${result.lndslddgwkt}" />"><!-- 사방댐 -->
			<input name="mntntrntWkt" type="hidden" value="<c:out value="${result.mntntrntwkt}" />"><!-- 계류보전 -->
			<input name="dgrareaWkt" type="hidden" value="<c:out value="${result.dgrareawkt}" />"><!-- 유역면적 -->
			<input name="mtcecnrWkt" type="hidden" value="<c:out value="${result.mtcecnrwkt}" />"><!-- 산지사방 -->
			<input name="evasysWkt" type="hidden" value="<c:out value="${result.evasyswkt}" />"><!-- 대피체계 -->
			
			<input name="uploc" type="hidden" value="<c:out value="${result.uploc}" />"><!-- 최상부 -->
			<input name="mdlloc" type="hidden" value="<c:out value="${result.mdlloc}" />"><!-- 중간부 -->
			
			<input name="lcmap" type="hidden" value="<c:out value="${lcmapArray}" />">
			
			<input name="statmap" type="hidden" value="<c:out value="${statmapArray}" />">
			
			<input name="calcMntrntWkt" type="hidden" value="<c:out value="${result.mntntrntwkt}" />"><!-- 토석류, 사방댐 -->
			<input name="calcLndslddgWkt" type="hidden" value="<c:out value="${result.lndslddgwkt}" />"><!-- 토석류, 계류보전 -->
			<input name="calcMtcecnrwktWkt" type="hidden" value="<c:out value="${result.mtcecnrwkt}" />"><!-- 산사태, 산지사방 -->
			<input name="calcInArea" type="hidden" value="N" />
			
			<div class="BoardDetail">
				<div class="ol-edit-control-btns" id="edit-btn">
					<button type="button" class="land02-btn-m" title="유역면적" value="03">유역면적</button>
					<c:choose>
						<c:when test="${fn:contains(result.svytype,'토석류')}">
							<button type="button" class="land03-btn-m" title="사방댐" value="01">사방댐</button>
							<button type="button" class="land04-btn-m" title="계류보전" value="02">계류보전</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="land05-btn-m" title="산지사방" value="04">산지사방</button>
						</c:otherwise>
					</c:choose>
					<button type="button" class="land06-btn-m" title="대피로" value="05">대피로</button>
					<button type="button" class="land07-btn-m" title="중간부" value="06">중간부</button>
					<button type="button" class="land08-btn-m" title="최상부" value="07">최상부</button>
					<button type="button" class="land09-btn-m" title="대피지점" value="08">대피지점</button>
					<button type="button" class="land10-btn-m" title="현황도" value="10">현황도</button>
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
				        <button type="button" id="chk_ctrln" name="layers" value="ctrln">등고선</button>
				        <button type="button" id="chk_lgstr" name="layers" value="lgstr">지적</button>
				        <button type="button" class="btn-map-selector" title="일반지도" value="base">일반지도</button>
				        <button type="button" class="btn-map-selector" title="항공지도" value="satellite">항공지도</button>
				    </div>
				    <div class="lgstr-check" style="display: none;">
				    	<input type="checkbox" class="lgstrChk" id="chk_snap_lgstr" name="snaps">
						<label for="chk_snap_lgstr" class="lgstrSnap">지적스내핑</label>
				    </div>
				    <div class="lgstr-label-check" style="display: none;">
				    	<input type="checkbox" class="lgstrChk" id="chk_snap_lgstr_label" name="lgstrLabel">
						<label for="chk_snap_lgstr_label" class="lgstrLbl">지적라벨</label>
				    </div>
				    <div class="hybrid-check">
						<input type="checkbox" value="hybrid" id="hybrid">
						<label for="hybrid">하이브리드</label>
				    </div>
					<div id="map" class="map">
						<div class="legend-div">
							<img src='<c:url value="/images/main/legend01.png" />'>
						</div>
					</div>
				</div>
				<br>
				<div>
					<div class="geomTbTitle">
						<span>□ 상세야장</span>
						<button type="button" class="add-btn" id="statAddBtn" onclick="javascript:fnStatBtn('add'); return false;">행추가</button>
					</div>
					<table id="statTable">
						<colgroup>
							<col style="width : 10%;">
							<col style="width : 25%;">
							<col style="width : 25%;">
							<col style="width : 25%;">
							<col style="width : 15%;">
						</colgroup>
						<tr class="bg-grey">
							<th rowspan="2">연번</th>
							<th colspan="2">GPS 좌표 (Map Datum:WGS84)</th>
							<th rowspan="2">특이사항</th>
							<th rowspan="2"></th>
						</tr>
						<tr class="bg-grey">
							<th>위도</th>
							<th>경도</th>
						</tr>
					</table>
					<br>
					<div class="geomTbTitle">
						<span>□ 편입지적</span>
					</div>
					<table id="lcmapTable">
						<tr class="bg-grey">
						    <th colspan="6">소재지</th>
						    <th rowspan="2" class="line-hg40">지적면적<br>(m<sup class="ft-size1">2</sup>)</th>
						    <th rowspan="2" class="line-hg40">편입면적<br>(m<sup class="ft-size1">2</sup>)</th>
						    <th rowspan="2" class="line-hg40">소유<br>구분</th>
						</tr>
						<tr class="bg-grey">
							<th>시도</th>
							<th>시군구</th>
							<th>읍면동</th>
							<th>리</th>
							<th>지번</th>
							<th>지목</th>
						</tr>
						<tr class="scoreLine">
							<th colspan="6">계</th>
							<th id="branchAreaTotal">0</th>
							<th id="incorAreaTotal">0</th>
							<td></td>
						</tr>
					</table>
				</div>
				<div>
					<div>
						<div class="areaBtnGroup">
							<button type="button" class="write-btn" onclick="fnCalcInArea(); return false;">편입면적 계산</button>
<!-- 							<button type="button" class="reset-btn" onclick="window.location.reload()">초기화</button> -->
						</div>
					</div>
				</div>
				<hr>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="save-btn" onclick="javascript:fnGeomSave(); return false;"><spring:message code="button.save" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>