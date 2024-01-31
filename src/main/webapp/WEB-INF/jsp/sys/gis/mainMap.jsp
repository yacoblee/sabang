<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<input type="hidden" name="gid" value="<c:out value="${gid}"/>">
<input type="hidden" name="featureSet" value="<c:out value="${featureSet}"/>">
<input type="hidden" name="goToLayer" value="<c:out value="${goToLayer}"/>">

<div class="loading-div" style='display:none;'>
	<img class="loading-img" src="<%=request.getContextPath() %>/images/common/progressbar_blue.gif" alt="로딩바 이미지">
</div>
<div class="map-div on">
	<div class="ol-custom-control ol-basemap-control" id="toggle-btn">
		<button type="button" class="btn-layer-selector juje" title="주제도" value="jujedo">주제도</button>
		<button type="button" class="btn-layer-selector jijuk" title="지적도" value="jijuk">지적도</button>
        <button type="button" class="btn-map-selector selected" title="일반지도" value="base">일반지도</button>
        <button type="button" class="btn-map-selector" title="항공지도" value="satellite">항공지도</button>
    </div>
    <div class="radiostyle check juje-check"></div>
    <div class="hybrid-check">
		<input type="checkbox" value="hybrid" id="hybrid"><label for="hybrid">하이브리드</label>
    </div>
    <!-- 지도 프레임 -->
	<div id="map" class="map" tabindex="0"></div>

	<!-- 주제도 범례  -->
	<div class="legend" style="display:none;">
        <span class="legend-title legend-toggle">범 례</span>
        <div class="legend-area"></div>
    </div>
</div>