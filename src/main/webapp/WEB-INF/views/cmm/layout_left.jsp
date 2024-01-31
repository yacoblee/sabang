<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title><tiles:getAsString name="title" /></title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- stylesheets -->
	<tiles:importAttribute name="defaultstylesheets"/>
	<tiles:importAttribute name="stylesheets"/>
	<c:forEach var="css" items="${defaultstylesheets}">
        <link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
    </c:forEach>
    <c:forEach var="css" items="${stylesheets}">
        <link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
    </c:forEach>
    <!-- end stylesheets -->
    <!-- scripts -->
	<tiles:importAttribute name="defaultjavascripts"/>
	<tiles:importAttribute name="javascripts"/>
	<c:forEach var="script" items="${defaultjavascripts}">
        <script src="<c:url value="${script}"/>"></script>
    </c:forEach>
    <c:forEach var="script" items="${javascripts}">
        <script src="<c:url value="${script}"/>"></script>
    </c:forEach>
    <!-- end scripts -->
</head>
<body>
	<p class="SkipNav"><a href="#Cont">콘텐츠바로가기</a><a href="#GNB">메뉴바로가기</a></p>
	<tiles:insertAttribute name="header" ignore="true"/>
	<!--  컨첸츠 S -->
	<section id="wrap-container">
		<div class="loading-div" style='display:none;'>
			<img class="loading-img" src="<%=request.getContextPath() %>/images/common/progressbar_blue.gif" alt="로딩바 이미지">
		</div>
		<div class="wrap-contents"> <!-- EXTEND클래스 추가시 페이지 확장 -->
			<ul class="widthsize">
				<li class="active"><a href="#" class="basic">기본페이지</a></li><!--active 추가시 메뉴활성화-->
				<li ><a href="#" class="extend">페이지확장</a></li>
			</ul>
			<tiles:insertAttribute name="left" ignore="true"/>
			<tiles:insertAttribute name="content" ignore="true"/>
		</div>
	</section>
	<!--  컨첸츠 E -->
	<tiles:insertAttribute name="footer" ignore="true"/>
</body>
</html>