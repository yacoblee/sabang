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
<div class="wrap-left">
<h2 class="menu05">사방시설점검</h2> <!--메뉴별로 클래스이름 바꿔주세요 menu01, menu03, menu04 , 관리자페이지menu05 ,마이페이지 menu06-->
  	<ul class="lnb">
 		<li class="submenuOn">
			<a href="#none" >외관점검</a>
			<ul class="left_Msub">			
				<li><a href="/sys/fck/apr/sts/selectAprSvySttus.do">조사현황</a></li>
				<li><a href="/sys/fck/apr/fbk/selectFieldBookList.do">전자야장연계</a></li>
				<li><a href="/sys/fck/apr/sct/selectAprComptList.do">조사완료</a></li>
			</ul>
		</li>
 		<li class="submenuOn">
			<a href="#none" >정밀점검</a>
			<ul class="left_Msub">			
				<li><a href="/sys/fck/pcs/sts/selectPcsSttus.do">조사현황</a></li>
				<li><a href="/sys/fck/pcs/fbk/selectPcsFieldBookList.do">전자야장연계</a></li>
				<li><a href="/sys/fck/pcs/sct/selectPcsSvyComptList.do">조사완료</a></li>
			</ul>
		</li> 
	</ul>
</div>
<script lang="javascript">
var menuList = '<c:out value="${loginVO.accesLeftMenu}"/>';
createLeftMenu(menuList);
</script>