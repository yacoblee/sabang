<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<div class="map-tools">
	<ul>
		<li class="home" title="전체보기"><span>전체보기</span></li>
		<li class="measure" title="거리재기"><span>거리</span></li>
		<li class="area" title="면적재기"><span>면적</span></li>
		<li class="save" title="지도저장"><span>저장</span></li>
		<li class="print" title="지도인쇄"><span>인쇄</span></li>
		<li class="clear" title="초기화"><span>초기화</span></li>
		<li class="del hidden" title="삭제"><span>삭제</span></li>
	</ul>
</div>