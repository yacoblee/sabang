<%@page import="egovframework.com.cmm.util.EgovUserDetailsHelper"%>
<%@page import="egovframework.com.cmm.LoginVO"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false" %>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<%
LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
pageContext.setAttribute("loginVO", loginVO);
%>    
	<div class="homemenu">
			<P>홈페이지</P>
			<ul class="Tdepth01"> 
				<li>
					<a href="#none">지도서비스</a>
				</li>
				<li class="active">
					<a href="#none">산사태조사</a>
					<ul class="Tdepth02">
						<li class="smenuOn"><a href="#none">기초조사</a>
							<ul class="Tdepth03">
								<li><a href="/sys/lss/bsc/sts/selectBscSvySttus.do">조사현황</a></li>
								<li><a href="/sys/lss/bsc/fbk/selectFieldBookList.do">전자야장연계</a></li>
								<li><a href="/sys/lss/bsc/sct/selectBscSvyComptList.do">조사완료</a></li>
							</ul>
						</li>
						<li class="smenuOn"><a href="#none">땅밀림실태조사</a>
							<ul  class="Tdepth03">
								<li><a href="/sys/lss/lcp/sts/selectLcpSvySttus.do">조사현황</a></li>
								<li><a href="/sys/lss/lcp/sld/selectLcpSvySldList.do">조사대상지관리</a></li>
								<li><a href="/sys/lss/lcp/rnk/selectLcpSvyRankList.do">랭크관리</a></li>
								<li><a href="/system/stat/report/frmerReportPage.do">제보관리</a></li>
								<li><a href="/system/stat/report/frmerReportPage.do">전자야장연계</a></li>
								<li><a href="/system/stat/report/frmerReportPage.do">조사완료</a></li>
							</ul>
						</li>
						<li class="smenuOn"><a href="#none">취약지역실태조사</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">조사현황</a></li>
								<li><a href="/system/integration/monthList.do">전자야장연계</a></li>
								<li><a href="/system/integration/yearList.do">조사완료</a></li>
							</ul>
						</li>
						<li class="smenuOn"><a href="#none">취약지역해제조사</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">조사현황</a></li>
								<li><a href="/system/integration/monthList.do">전자야장연계</a></li>
								<li><a href="/system/integration/yearList.do">조사완료</a></li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					<a href="#none">사방시설점검</a>
					<ul class="Tdepth02">
						<li class="smenuOn"><a href="#none">외관점검</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">조사현황</a></li>
								<li><a href="/system/integration/monthList.do">전자야장연계</a></li>
								<li><a href="/system/integration/yearList.do">조사완료</a></li>
							</ul>
						</li>
						<li class="smenuOn"><a href="#none">정밀점검</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">조사현황</a></li>
								<li><a href="/system/integration/monthList.do">전자야장연계</a></li>
								<li><a href="/system/integration/yearList.do">조사완료</a></li>
							</ul>
						</li>						
					</ul>
				</li>
				<li>
					<a href="#none">타당성평가</a>
					<ul class="Tdepth02">
						<li class="smenuOn"><a href="#none">사방사업</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">평가현황</a></li>
								<li><a href="/system/integration/monthList.do">사업관리</a></li>
								<li><a href="/system/integration/yearList.do">대상지분석</a></li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					<a href="#none">업무지원</a>
					<ul class="Tdepth02">
						<li class="smenuOn"><a href="#none">보고서관리</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">기초조사</a></li>
								<li><a href="/system/integration/monthList.do">외관점검</a></li>
								<li><a href="/system/integration/yearList.do">땅밀림실태조사</a></li>
								<li><a href="/system/integration/yearList.do">취약지역실태조사</a></li>
								<li><a href="/system/integration/yearList.do">취약지역해제조사</a></li>
							</ul>
						</li>
						<li class="smenuOn"><a href="#none">통계관리</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">기초조사</a></li>
								<li><a href="/system/integration/monthList.do">외관점검</a></li>
								<li><a href="/system/integration/yearList.do">땅밀림실태조사</a></li>
								<li><a href="/system/integration/yearList.do">취약지역실태조사</a></li>
								<li><a href="/system/integration/yearList.do">취약지역해제조사</a></li>
							</ul>
						</li>
						<li class="smenuOn"><a href="#none">완료보고서</a>
							<ul class="Tdepth03">
								<li><a href="/system/stat/search/aggregation/listPage.do">기초조사</a></li>
								<li><a href="/system/integration/monthList.do">외관점검</a></li>
								<li><a href="/system/integration/yearList.do">땅밀림실태조사</a></li>
								<li><a href="/system/integration/yearList.do">취약지역실태조사</a></li>
								<li><a href="/system/integration/yearList.do">취약지역해제조사</a></li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					<a href="#none">알림마당</a>
					<ul class="Tdepth02">
						<li class="smenuOn"><a href="#none">시스템소개</a>
						<li class="smenuOn"><a href="#none">공지사항</a>
						<li class="smenuOn"><a href="#none">게시판</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<div class="adminmenu">
		    <p>관리자서비스</p>
		    <div class="admincon">
		        <ul class="Tdepth01">
		            <li><a href="#none">사용자관리</a>
		                <ul class="Tdepth02">
		                    <li><a href="/manage/member/memberList.do">회원관리</a></li>
		                    <li><a href="/manage/member/memberJoinList.do">가입승인관리</a></li>
		                </ul>
		            </li>
		            <li><a href="#none">로그관리</a>
		                <ul class="Tdepth02">
		                    <li><a href="/manage/log/syslog/selectSysLogList.do">시스템로그관리</a></li>
							<li><a href="/manage/log/weblog/selectWebLogList.do">웹로그관리</a></li>
							<li><a href="/manage/log/userlog/selectUserLogList.do">사용자로그관리</a></li>
							<li><a href="/manage/log/loginlog/selectLoginLogList.do">접속로그관리</a></li>
		                </ul>
		            </li>
		            <li><a href="#none">권한관리</a>
		                <ul class="Tdepth02">
		                    <li><a href="/manage/author/authorList.do">권한관리</a></li>
							<li><a href="/manage/author/authorRoleList.do">권한 롤 관리</a></li>
							<li><a href="/manage/author/roleList.do">롤 관리</a></li>
							<li><a href="/manage/author/roleHierarchyList.do">롤 상하관계관리</a></li>
							<li><a href="/manage/author/authorUserList.do">권한부여관리</a></li>
		                </ul>
		            </li>
		            <li><a href="#none">게시물관리</a>
		                <ul class="Tdepth02">
		                    <li><a href="/manage/statistic/systemStatList.do">공지사항관리</a></li>
		                    <li><a href="/manage/statistic/systemStatList.do">게시판관리</a></li>
		                </ul>
		            </li>
		            <li><a href="#none">공통코드관리</a>
		                <ul class="Tdepth02">
		                    <li><a href="/manage/statistic/systemStatList.do">공통코드관리</a></li>
		                    <li><a href="/manage/statistic/systemStatList.do">공통분류코드관리</a></li>
		                    <li><a href="/manage/statistic/systemStatList.do">공통상세코드관리</a></li>
		                </ul>
		            </li>
		            <li><a href="#none">메뉴관리</a>
		                <ul class="Tdepth02">
		                    <li><a href="/manage/statistic/systemStatList.do">프로그램목록관리</a></li>
		                    <li><a href="/manage/statistic/systemStatList.do">메뉴목록관리</a></li>
		                    <li><a href="/manage/statistic/systemStatList.do">메뉴생성관리</a></li>
		                </ul>
		            </li>
		        </ul>
		    </div>
		</div>
		<div class="mapage">
			<p>마이페이지</p>
			<a href="/system/account/myinfo.do"><img src="/images/common/icon_mypage.png" alt="마이페이지"> <span>마이페이지</span></a>
		</div>
		<button class="TPopcolose">닫기</button>
		<div class="ben_mbl">
			<a href="/logout.do" class="popbtnlogout_mbl">로그아웃</a>
			<a href="/system/main.do" class="goHome_mbl">메인으로</a>
		</div>