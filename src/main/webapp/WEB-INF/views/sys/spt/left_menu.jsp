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
<%@ taglib prefix="sec"      uri="http://www.springframework.org/security/tags"%>
<%@ page session="false" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%
LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
pageContext.setAttribute("loginVO", loginVO);
%>
<!-- support -->
<div class="wrap-left">
<h2 class="menu05">업무지원</h2> <!--메뉴별로 클래스이름 바꿔주세요 menu01, menu03, menu04 , 관리자페이지menu05 ,마이페이지 menu06-->
 	<ul class="lnb">
<!-- 		<li class="submenuOn"> -->
<!-- 			<a href="#none">보고서관리</a> -->
<!-- 			<ul class="left_Msub"> -->
<!-- 				<li><a href="/sys/spt/rpt/selectRptBscManageList.do">기초조사</a></li> -->
<!-- 				<li><a href="/sys/spt/rpt/selectRptAprManageList.do">외관점검</a></li> -->
<!-- 			</ul> -->
<!-- 		</li> -->
<!-- 		<li class="submenuOn"> -->
<!-- 			<a href="#none">통계관리</a> -->
<!-- 			<ul class="left_Msub"> -->
<!-- 				<li><a href="/sys/spt/sts/sttusBscDetail.do">기초조사</a></li> -->
<!-- 				<li><a href="#">외관점검</a></li> -->
<!-- 			</ul> -->
<!-- 		</li> -->
<!-- 		<li class="submenuOn"> -->
<!-- 			<a href="#none">완료보고서</a> -->
<!-- 			<ul class="left_Msub"> -->
<!-- 				<li><a href="/sys/spt/sct/selectBscComptReportList.do">기초조사</a></li> -->
<!-- 				<li><a href="/sys/spt/sct/selectAprComptReportList.do">외관점검</a></li> -->
<!-- 			</ul> -->
<!-- 		</li> -->
	</ul>
</div>
<script lang="javascript">
var menuList = '<c:out value="${loginVO.accesLeftMenu}"/>';
createLeftMenu(menuList);
</script>