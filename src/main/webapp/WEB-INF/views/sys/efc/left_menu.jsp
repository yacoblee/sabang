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
<% response.setCharacterEncoding("UTF-8"); %>
<!-- erosin control facility check-->
<div class="wrap-left">
<h2 class="menu05">사방시설점검</h2> <!--메뉴별로 클래스이름 바꿔주세요 menu01, menu03, menu04 , 관리자페이지menu05 ,마이페이지 menu06-->
 	<ul class="lnb">
		<li class="submenuOn">
			<a href="#none" >사용자관리</a>
			<ul class="left_Msub">			
				<li><a href="/mng/umt/mberManage.do">회원관리</a></li>
				<li><a href="/mng/umt/mberJoinManage.do">가입승인관리</a></li>
			</ul>
		</li>
		<li class="submenuOn">
			<a href="#none" >로그관리</a>
			<ul class="left_Msub">			
				<li><a href="/mng/log/lgm/selectSysLogList.do">시스템로그관리</a></li>
				<li><a href="/mng/log/wlg/selectWebLogList.do">웹로그관리</a></li>
				<li><a href="/mng/log/ulg/selectUserLogList.do">사용자로그관리</a></li>
				<li><a href="/mng/log/clg/selectLoginLogList.do">접속로그관리</a></li>
			</ul>
		</li>
		<li class="submenuOn">
			<a href="#none" >권한관리</a>
			<ul class="left_Msub">			
				<li><a href="/mng/sec/ram/authorList.do">권한관리</a></li>
				<li><a href="/mng/sec/ram/authorRoleList.do">권한 롤 관리</a></li>
				<li><a href="/mng/sec/rmt/roleList.do">롤 관리</a></li>
				<li><a href="/mng/sec/rmt/roleHierarchyList.do">롤 상하관계관리</a></li>
				<li><a href="/mng/sec/urm/userAuthorList.do">권한부여관리</a></li>
			</ul>
		</li>
		<li class="submenuOn">
			<a href="#none" >통계관리</a>
			<ul class="left_Msub">
				<li><a href="/mng/sts/systemStatList.do">시스템사용통계</a></li>
			</ul>
		</li>
		<li class="submenuOn">
			<a href="#none">게시물관리</a>
			<ul class="left_Msub">
				<li><a href="/mng/board/notice.do">공지사항관리</a></li>
				<li><a href="/mng/board/bulletin.do">게시판관리</a></li>
			</ul>
		</li>
		<li class="submenuOn">
			<a href="#none">공통코드관리</a>
			<ul class="left_Msub">
				<li><a href="/mng/ccm/cca/selectCcmCmmnCodeList.do">공통코드관리</a></li>
				<li><a href="/mng/ccm/ccc/selectCcmCmmnClCodeList.do">공통분류코드관리</a></li>
				<li><a href="/mng/ccm/cde/selectCcmCmmnDetailCodeList.do">공통상세코드관리</a></li>
			</ul>
		</li>
		<li class="submenuOn">
			<a href="#none">메뉴관리</a>
			<ul class="left_Msub">
				<li><a href="/mng/mnu/mpm/menuManageSelect.do">메뉴관리</a></li>
			</ul>
		</li>
	</ul>
</div>