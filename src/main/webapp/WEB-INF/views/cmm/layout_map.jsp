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
	<div class="skip-div">
		<a href="#contents">본문 바로가기</a>
	</div>
	<div id="wrap" class="wrap">
		<div id="wrap-container" class="main gis">
			<tiles:insertAttribute name="left"/>
			<tiles:insertAttribute name="right"/>
			<tiles:insertAttribute name="content"/>
		</div>
	</div>
</body>
</html>