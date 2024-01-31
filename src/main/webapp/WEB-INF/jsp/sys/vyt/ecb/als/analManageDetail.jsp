<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<c:set var="pageTitle"><spring:message code="sysVytEcb.analManageDetail.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">사방사업</a></li>
		<li><a href="#">대상지분석</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 기초조사 상세조회 -->
	<div id="contents">
		<form id="listForm" action="${pageContext.request.contextPath}/sys/vyt/ecb/als/analManageDetail.do" method="post">
			<input name="mstId" type="hidden" value="<c:out value="${result.mstId}" />">
			<input name="sldId" type="hidden" value="<c:out value="${result.sldId}" />">
			<input name="svyType" type="hidden" value="vytecb">
			<input name="analId" type="hidden" value="<c:out value="${result.analId}" />">
			<input name="lgstrCd" type="hidden" value="<c:out value="${result.lgstrCd}" />">
			<input name="schworkType" type="hidden" value="<c:out value='${searchVO.workType}'/>">
			<input name="schworkYear" type="hidden" value="<c:out value='${searchVO.workYear}'/>">
			<input name="schworkSd" type="hidden" value="<c:out value='${searchVO.workSd}'/>">
			<input name="schworkSgg" type="hidden" value="<c:out value='${searchVO.workSgg}'/>">
			<input name="schworkEmd" type="hidden" value="<c:out value='${searchVO.workEmd}'/>">
			<input name="schworkRi" type="hidden" value="<c:out value='${searchVO.workRi}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
			<div class="BoardDetail">
				<table id="alsTb" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width: 15%;">
						<col style="width: 18.33%;">
						<col style="width: 15%;">
						<col style="width: 18.33%;">
						<col style="width: 15%;">
						<col style="width: 18.33%;">
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="sysVytEcb.analManageVO.workType" /></th><!-- 사업종류 -->
							<td colspan="2"><c:out value='${result.workType}'/></td>
							<th><spring:message code="sysVytEcb.analManageVO.workName" /></th><!-- 사업명 -->
							<td colspan="2"><c:out value="${result.workNm}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysVytEcb.analManageVO.addr" /></th><!-- 주소 -->
							<td colspan="2"><c:out value="${result.sdNm}"/> <c:out value="${result.sggNm}"/> <c:out value="${result.emdNm}"/> <c:out value="${result.riNm}"/> <c:out value="${result.jibun}"/></td>
							<th><spring:message code="sysVytEcb.analManageVO.creatDt"/></th><!-- 등록일 -->
							<td colspan="2"><c:out value="${result.creatDt}"/></td>
						</tr>
						<tr>
							<th rowspan="2">유역분석</th>
							<td colspan="2">
								<button type="button" class="red-point-btn" onclick="javascript:fncAddPoint(); return false;">유출구</button>
								<button type="button" class="del-btn" onclick="javascript:fncAddPointClear(); return false;">삭제</button>
								<button type="button" class="modify-btn" onclick="javascript:fncAnalWaterShed(); return false;">유역분석</button>
							</td>
							<th>계류경사분석</th>
							<td colspan="2">
								<button type="button" class="red-line-btn" onclick="javascript:fncAddLine(); return false;">종단계류경사</button>
								<button type="button" class="del-btn" onclick="javascript:fncAddLineClear(); return false;">삭제</button>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="button" class="blue-rec-btn" onclick="javascript:fncAddWaterShedPolygon(); return false;">유역그리기</button>
								<button type="button" class="del-btn" onclick="javascript:fncAddWaterShedPolygonClear(); return false;">삭제</button>
							</td>
							<th>관계지적분석</th>
							<td colspan="2">
								<div style="display: inline-block;">
									<input type="checkbox" name="cadastralPntChk" id="cadastralPntChk" checked>
									<label for="cadastralPntChk">유출구 포함여부</label>
								</div>
							</td>
						</tr>
						<tr>
							<th>지적정보</th>
							<td colspan="5">
								<button type="button" class="red-rec-btn" onclick="javascript:fncAddPolygon(); return false;">범위설정</button>
								<button type="button" class="del-btn" onclick="javascript:fncAddPolygonClear(); return false;">삭제</button>
								<button type="button" class="search-btn" onclick="javascript:fncSearchCadastral(); return false;">조회</button>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="anal-btns">
					<button type="button" class="modify-btn" onclick="javascript:fncCreateVytEcbReportImgAll(); return false;">공간분석</button>
				</div>
				<div class="anal-btns" style="display: none;">
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Loc'); return false;">위치도</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Sat'); return false;">영상위치도</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Slope'); return false;">경사</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Koftr'); return false;">임상</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Agcls'); return false;">영급</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Dnst'); return false;">소밀도</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Dmcls'); return false;">경급</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Dem'); return false;">고도</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('River'); return false;">수계</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Aspect'); return false;">향</button>
					<button type="button" class="add-btn" onclick="javascript:fncSaveAnalImg('Nature'); return false;">생태</button>
				</div>
				<div class="anal-btns" style="display: none;">
					<button type="button" class="add-btn" onclick="javascript:fncCreatStreamOrder(); return false;">Stream Order</button>
				</div>
				<div id="map-div" class="map-div on">
					<div class="ol-custom-control ol-basemap-control" id="toggle-btn">
				        <button type="button" class="btn-map-selector" title="일반지도" value="base">일반지도</button>
				        <button type="button" class="btn-map-selector" title="항공지도" value="satellite">항공지도</button>
				    </div>
				    <div class="hybrid-check">
						<input type="checkbox" value="hybrid" id="hybrid">
						<label for="hybrid">하이브리드</label>
				    </div>
					<div id="map" class="map">
						<div id="layer-div" style="position: absolute;z-index: 1;padding: 5px;">
							<div class="layers" style="background-color: #fff;padding:0 5px;">
								
							</div>
						</div>
					</div>
				</div>
				
				<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
					<colgroup>
						<col style="width: 9%;">
						<col style="width: ;">
						<col style="width: ;">
						<col style="width: ;">
					</colgroup>
					<thead>
					  <tr>
						<th><spring:message code="table.num" /></th><!-- 번호 -->
						<th>분석종류</th><!-- 분석종류 -->
						<th>분석정보</th><!-- 분석정보 다운로드 -->
						<th>분석이미지</th><!-- 분석이미지 다운로드 -->
					  </tr>
					</thead>
					<tbody>
					<c:choose>
					<c:when test="${fn:length(resultList) == 0}">
					<tr class="center">
						<td colspan="4"><spring:message code="common.nodata.msg" /></td>
					</tr>
					</c:when>
					<c:otherwise>
					<c:set var="resultFile01" />
					<c:set var="resultFile02" />
					<c:set var="resultImg02" />
					<c:set var="resultImgExt02" />
					<c:set var="resultFile03" />
					<c:set var="resultImg03" />
					<c:set var="resultImgExt03" />
					<c:set var="resultFile04" />
					<c:set var="resultImg04" />
					<c:set var="resultImgExt04" />
					<c:set var="resultFile05" />
					<c:set var="resultImg05" />
					<c:set var="resultImgExt05" />
					<c:set var="resultFile06" />
					<c:set var="resultImg06" />
					<c:set var="resultImgExt06" />
					<c:set var="resultFile07" />
					<c:set var="resultImg07" />
					<c:set var="resultImgExt07" />
					<c:set var="resultFile08" />
					<c:set var="resultImg08" />
					<c:set var="resultImgExt08" />
					<c:set var="resultFile09" />
					<c:set var="resultImg09" />
					<c:set var="resultImgExt09" />
					<c:set var="resultFile10" />
					<c:set var="resultImg10" />
					<c:set var="resultImgExt10" />
					<c:set var="resultFile11" />
					<c:set var="resultImg11" />
					<c:set var="resultImgExt11" />
					<c:set var="resultFile12" />
					<c:set var="resultImg12" />
					<c:set var="resultImgExt12" />
					<c:set var="resultFile13" />
					<c:set var="resultImg13" />
					<c:set var="resultImgExt13" />
					<c:set var="resultFile14" />
					<c:set var="resultImg14" />
					<c:set var="resultImgExt14" />
					<c:set var="resultFile15" />
					<c:set var="resultImg15" />
					<c:set var="resultImgExt15" />
					<c:set var="resultFile16" />
					<c:set var="resultImg16" />
					<c:set var="resultImgExt16" />
					<c:set var="resultFile17" />
					<c:set var="resultImg17" />
					<c:set var="resultImgExt17" />
					<c:set var="resultFile18" />
					<c:set var="resultImg18" />
					<c:set var="resultImgExt18" />
					<c:set var="resultFile19" />
					<c:set var="resultImg19" />
					<c:set var="resultImgExt19" />
					<c:set var="resultFile20" />
					<c:set var="resultImg20" />
					<c:set var="resultImgExt20" />
					<c:set var="resultImg21" />
					<c:set var="resultImgExt21" />
					<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
					<c:if test="${resultInfo.analType eq '유역분석' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile01" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '위치도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile02" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '위치도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg02" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt02" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq '위치도(영상)' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile03" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '위치도(영상)' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg03" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt03" value="${resultInfo.fileExtsn}"/>
					</c:if>
<%-- 					<c:if test="${resultInfo.analType eq '수계망도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile04" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '수계망도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg04" value="${resultInfo.gid}"/></c:if> --%>
					<c:if test="${resultInfo.analType eq 'stream3ha' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile04" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'stream3ha' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg04" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt04" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'slope' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile05" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'slope' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg05" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt05" value="${resultInfo.fileExtsn}"/>
					</c:if>
<%-- 					<c:if test="${resultInfo.analType eq '경사분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile05" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '경사분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg05" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '향분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile06" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '향분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg06" value="${resultInfo.gid}"/></c:if> --%>
					<c:if test="${resultInfo.analType eq 'aspect' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile06" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'aspect' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg06" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt06" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'dem' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile07" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'dem' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg07" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt07" value="${resultInfo.fileExtsn}"/>
					</c:if>
<%-- 					<c:if test="${resultInfo.analType eq '표고분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile07" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '표고분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg07" value="${resultInfo.gid}"/></c:if> --%>
					<c:if test="${resultInfo.analType eq 'geological' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile08" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'geological' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg08" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt08" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'koftr' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile09" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'koftr' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg09" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt09" value="${resultInfo.fileExtsn}"/>
					</c:if>
<%-- 					<c:if test="${resultInfo.analType eq '임상분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile09" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '임상분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg09" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '영급분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile10" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '영급분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg10" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '소밀도분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile11" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '소밀도분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg11" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '경급분포도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile12" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '경급분포도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg12" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '생태자연도' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile13" value="${resultInfo.gid}"/></c:if> --%>
<%-- 					<c:if test="${resultInfo.analType eq '생태자연도' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}"><c:set var="resultImg13" value="${resultInfo.gid}"/></c:if> --%>
					<c:if test="${resultInfo.analType eq 'agcls' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile10" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'agcls' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg10" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt10" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'dnst' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile11" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'dnst' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg11" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt11" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'dmcls' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile12" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'dmcls' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg12" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt12" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'nature' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile13" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'nature' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg13" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt13" value="${resultInfo.fileExtsn}"/>
					</c:if>
					<c:if test="${resultInfo.analType eq 'landslide' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile14" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'landslide' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg14" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt14" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq '대상지(시도)' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile15" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '대상지(시도)' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg15" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt15" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq '대상지(시군구)' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile16" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '대상지(시군구)' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg16" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt16" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq '관계지적도(1/1200)' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile17" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '관계지적도(1/1200)' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg17" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt17" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq '관계지적도(1/2400)' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile21" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '관계지적도(1/2400)' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg21" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt21" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq '대상지(수치지형도)' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile18" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '대상지(수치지형도)' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg18" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt18" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq 'stream5ha' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile19" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq 'stream5ha' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg19" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt19" value="${resultInfo.fileExtsn}"/>
					</c:if>
					
					<c:if test="${resultInfo.analType eq '계류경사분석' and resultInfo.fileExtsn eq 'zip'}"><c:set var="resultFile20" value="${resultInfo.gid}"/></c:if>
					<c:if test="${resultInfo.analType eq '계류경사분석' and  (resultInfo.fileExtsn eq 'png' or resultInfo.fileExtsn eq 'jpg')}">
					<c:set var="resultImg20" value="${resultInfo.gid}"/>
					<c:set var="resultImgExt20" value="${resultInfo.fileExtsn}"/>
					</c:if>
					</c:forEach>
					<tr>
						<td>1</td>
						<td>유역분석</td>
						<td>
							<c:choose>
							<c:when test="${not empty resultFile01}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile01}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 유역분석.zip
							</a>
							</c:when>
							<c:otherwise>
								-
							</c:otherwise>
							</c:choose>
						</td>
						<td>
							-
						</td>
					</tr>
					<tr>
						<td>2</td>
						<td><c:out value="대상지 위치도(지적)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile02}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile02}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지 위치도(지적).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg02}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg02}'/>&amp;ext=<c:out value='${resultImgExt02}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지 위치도(지적).<c:out value='${resultImgExt02}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>3</td>
						<td><c:out value="대상지 위치도(영상)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile03}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile03}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지 위치도(영상).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg03}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg03}'/>&amp;ext=<c:out value='${resultImgExt03}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지 위치도(영상).<c:out value='${resultImgExt03}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>4</td>
						<td><c:out value="수계망(3ha)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile04}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile04}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 수계망(3ha).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg04}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg04}'/>&amp;ext=<c:out value='${resultImgExt04}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 수계망(3ha).<c:out value='${resultImgExt04}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>5</td>
						<td><c:out value="수계망(5ha)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile19}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile19}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 수계망(5ha).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg19}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg19}'/>&amp;ext=<c:out value='${resultImgExt19}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 수계망(5ha).<c:out value='${resultImgExt19}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>6</td>
						<td><c:out value="경사분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile05}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile05}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 경사분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg05}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg05}'/>&amp;ext=<c:out value='${resultImgExt05}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 경사분포도.<c:out value='${resultImgExt05}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>7</td>
						<td><c:out value="향분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile06}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile06}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 향분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg06}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg06}'/>&amp;ext=<c:out value='${resultImgExt06}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 향분포도.<c:out value='${resultImgExt06}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>8</td>
						<td><c:out value="표고분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile07}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile07}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 표고분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg07}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg07}'/>&amp;ext=<c:out value='${resultImgExt07}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 표고분포도.<c:out value='${resultImgExt07}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>9</td>
						<td><c:out value="지질분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile08}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile08}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 지질분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg08}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg08}'/>&amp;ext=<c:out value='${resultImgExt08}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 지질분포도.<c:out value='${resultImgExt08}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>10</td>
						<td><c:out value="임상분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile09}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile09}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 임상분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg09}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg09}'/>&amp;ext=<c:out value='${resultImgExt09}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 임상분포도.<c:out value='${resultImgExt09}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>11</td>
						<td><c:out value="영급분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile10}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile10}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 영급분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg10}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg10}'/>&amp;ext=<c:out value='${resultImgExt10}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 영급분포도.<c:out value='${resultImgExt10}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>12</td>
						<td><c:out value="소밀도분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile11}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile11}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 소밀도분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg11}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg11}'/>&amp;ext=<c:out value='${resultImgExt11}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 소밀도분포도.<c:out value='${resultImgExt11}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>13</td>
						<td><c:out value="경급분포도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile12}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile12}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 경급분포도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg12}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg12}'/>&amp;ext=<c:out value='${resultImgExt12}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 경급분포도.<c:out value='${resultImgExt12}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>14</td>
						<td><c:out value="생태자연도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile13}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile13}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 생태자연도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg13}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg13}'/>&amp;ext=<c:out value='${resultImgExt13}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 생태자연도.<c:out value='${resultImgExt13}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<td>15</td>
						<td><c:out value="산사태위험등급도"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile14}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile14}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 산사태위험등급도.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg14}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg14}'/>&amp;ext=<c:out value='${resultImgExt14}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 산사태위험등급도.<c:out value='${resultImgExt14}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>16</td>
						<td><c:out value="대상지(시도)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile15}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile15}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지(시도).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg15}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg15}'/>&amp;ext=<c:out value='${resultImgExt14}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지(시도).<c:out value='${resultImgExt15}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>17</td>
						<td><c:out value="대상지(시군구)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile16}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile16}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지(시군구).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg16}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg16}'/>&amp;ext=<c:out value='${resultImgExt16}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지(시군구).<c:out value='${resultImgExt16}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>18</td>
						<td><c:out value="관계지적도(1/1200)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile17}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile17}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 관계지적도(1/1200).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg17}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg17}'/>&amp;ext=<c:out value='${resultImgExt17}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 관계지적도(1/1200).<c:out value='${resultImgExt17}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>19</td>
						<td><c:out value="관계지적도(1/2400)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile21}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile21}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 관계지적도(1/2400).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg21}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg21}'/>&amp;ext=<c:out value='${resultImgExt21}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 관계지적도(1/2400).<c:out value='${resultImgExt21}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>20</td>
						<td><c:out value="대상지(수치지형도)"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile18}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile18}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지(수치지형도).zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg18}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg18}'/>&amp;ext=<c:out value='${resultImgExt18}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 대상지(수치지형도).<c:out value='${resultImgExt18}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>21</td>
						<td><c:out value="계류경사분석"/></td>
						<td>
						<c:choose>
						<c:when test="${not empty resultFile20}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultFile20}'/>&amp;ext=zip">
								<img src="/images/sub/discat.png" alt="첨부파일"> 계류경사분석.zip
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
						<td>
						<c:choose>
						<c:when test="${not empty resultImg20}">
							<a href="/sys/gis/als/selectAnalFileDown.do?fileSn=<c:out value='${resultImg20}'/>&amp;ext=<c:out value='${resultImgExt20}'/>">
								<img src="/images/sub/discat.png" alt="첨부파일"> 계류경사분석.<c:out value='${resultImgExt20}'/>
							</a>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
						</c:choose>
						</td>
					</tr>
					</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
					<sec:authorize access="hasAnyRole('ROLE_ADMIN_VYTECB','ROLE_SUB_ADMIN','ROLE_ADMIN')">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
<%-- 								<button type="button" class="del-btn" onclick="javascript:fnUpdateWork('<c:out value="${result.sldId}"/>');"><spring:message code="title.delete" /></button> --%>
							</sec:authorize>
						</sec:authorize>
<%-- 						<sec:authorize access="!hasAnyRole('ROLE_ADMIN_VYTECB','ROLE_SUB_ADMIN','ROLE_ADMIN')"> --%>
<%-- 							<sec:authorize access="hasAnyRole('ROLE_USER_VYTECB','ROLE_USER_CNRS','ROLE_USER')"> --%>
<%-- 								<c:if test="${result.loginId eq userInfo.id or result.loginId eq userInfo.name or fn:contains(result.svyUser,userInfo.name)}"> --%>
<%-- 								</c:if> --%>
<%-- 							</sec:authorize> --%>
<%-- 						</sec:authorize> --%>
<%-- 						<button type="button" class="modify-btn" onclick="javascript:fncUpdateStripLandView(); return false;"><spring:message code="title.update" /></button> --%>
						<c:if test="${not empty statDataCnt}">
						<button type="button" class="download-btn" onclick="javascript:fnStatDataExcel(); return false;"><spring:message code="button.excel" /> <spring:message code="title.download" /></button>
						</c:if>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>