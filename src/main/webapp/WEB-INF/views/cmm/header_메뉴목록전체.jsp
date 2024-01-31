<%@page import="egovframework.rte.psl.dataaccess.util.EgovMap"%>
<%@page import="egovframework.com.cmm.util.EgovUserDetailsHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="egovframework.com.cmm.LoginVO"%>
<%@page import="org.springframework.web.context.request.RequestContextHolder"%>
<%@page import="org.springframework.web.context.request.ServletRequestAttributes"%>
<%@page import="java.security.Principal"%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form"        uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"      uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles"       uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false" %>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<%
LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
List<EgovMap> accesMenuList = loginVO.getAccesMenu();
pageContext.setAttribute("loginVO", loginVO);
pageContext.setAttribute("accesMenuList", accesMenuList);
%>
<!--  헤더 S -->
<header class="<sec:authorize access="!hasRole('ROLE_ADMIN')">Basic</sec:authorize>">
	<div class="primary_header">
		<h1 class="title"><a href="<c:url value='http://${pageContext.request.serverName}:${pageContext.request.serverPort}'/>"><img src="/images/common/logo.png" alt="치산정보시스템"></a></h1>		
		<sec:authorize access="isAuthenticated()">
			<p class="in_usernm"><span><c:out value="${loginVO.name}"/></span> 님 반갑습니다.</p>
			<a href="/logout.do" class="BtnLogout">로그아웃</a>
			<a href="/sys/uia/myinfo.do" class="BtnMyPage">마이페이지</a>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a href="/mng/umt/mberManage.do" class="BtnAdmin">관리서비스</a>
			</sec:authorize>
		</sec:authorize>
	</div>
	<!--  메뉴 S -->
    <nav class="GNB" id="GNB"><!--  탭키이동시 탭초점이 아래 <a>태그에 갔을때 menuopen 클래스 추가해주세요 -->
		<div class="GNB_con">
			<ul class="depth01">
				<li><a href="/sys/gis/mainMap.do">지도서비스</a></li>
				<li><a href="/sys/lss/bsc/sts/selectBscSvySttus.do">산사태조사</a>
					<ul class="depth02">
						<li><a href="/sys/lss/bsc/sts/selectBscSvySttus.do">기초조사</a></li>
<!-- 						<li><a href="#">실태조사</a></li> -->
<!-- 						<li><a href="#">땅밀림</a></li> -->
					</ul>
				</li>
<!-- 				<li><a href="#">사방사업타당성평가</a> -->
<!-- 					<ul class="depth02"> -->
<!-- 						<li><a href="#">산지사방</a></li> -->
<!-- 						<li><a href="#">사방댐</a></li> -->
<!-- 						<li><a href="#">계류</a></li> -->
<!-- 						<li><a href="#">해안사방</a></li>						 -->
<!-- 					</ul> -->
<!-- 				</li> -->
				<li><a href="/sys/fck/apr/sts/selectAprSvySttus.do">사방시설점검</a>
					<ul class="depth02">
						<li><a href="/sys/fck/apr/sts/selectAprSvySttus.do">외관점검</a></li>
<!-- 						<li><a href="#">정밀점검</a></li> -->
<!-- 						<li><a href="#">안전진단</a></li>						 -->
					</ul>
				</li>
<!-- 				<li><a href="#">임도타당성평가</a>					 -->
<!-- 				</li> -->
<!-- 				<li><a href="#">산지전용</a> -->
<!-- 					<ul class="depth02"> -->
<!-- 						<li><a href="#">타당성조사</a></li> -->
<!-- 						<li><a href="#">신재생에너지점검</a></li> -->
<!-- 						<li><a href="#">재해영향성평가</a></li>					 -->
<!-- 					</ul> -->
<!-- 				</li> -->
				<li><a href="/sys/spt/rpt/selectRptBscManageList.do">업무지원</a>
					<ul class="depth02">
						<li><a href="/sys/spt/rpt/selectRptBscManageList.do">보고서관리</a></li>
						<li><a href="/sys/spt/sts/sttusBscDetail.do">통계관리</a></li>
						<li><a href="/sys/spt/sct/selectBscComptReportList.do">완료보고서</a></li>
					</ul>
				</li>
				<li><a href="/sys/bbs/nyd/selectSystemIntrcn.do">알림마당</a>
					<ul class="depth02">
						<li><a href="/sys/bbs/nyd/selectSystemIntrcn.do">시스템소개</a></li>
						<li><a href="/sys/bbs/ntb/noticeList.do">공지사항</a></li>
						<li><a href="/sys/bbs/art/articleList.do">게시판</a></li>
					</ul>
				</li>
			</ul>
<!-- 			<p class="Topmenu"> -->
<%-- 				<sec:authorize access="isAuthenticated()"> --%>
<%-- 					<sec:authorize access="hasRole('ROLE_ADMIN')"> --%>
<!-- 					<a href="#" class="admin">관리서비스</a> -->
<%-- 					</sec:authorize> --%>
<!-- 					<a href="#">마이페이지</a> -->
<%-- 				</sec:authorize> --%>
<!-- 			</p> -->
		</div>
	</nav>
	<!--  메뉴 E -->
	<!--  전체메뉴 S -->
<!-- 	<button class="btn_total">전체메뉴버튼</button> -->
<!-- 	<div class="TotalBack Hidden">  Hidden 클래스 제거시 전체메뉴보임 -->
<!-- 		<div class="TotalPop"> -->
<%-- 			<jsp:include page="menu.jsp" flush="false"></jsp:include> --%>
<!-- 		</div> -->
<!-- 	</div> -->
	<!--  전체메뉴 E -->
</header>
<!--  헤더 E -->