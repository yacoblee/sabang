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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<!--  컨첸츠 S -->
<section id="wrap-container" class="main">
	<div class="main_wrap">
		<div class="main-visual" id="Cont">
			<div class="visual_left">
				<!-- 2021-11-17 메인화면 차트-->
				<div class="main_slidcontainer" style="width:100%; height:100%;">
				  <div class="main_slids fade">
				  	<div class="main_slidschart"></div>
				  	<div class="main_slidslabel">기초조사 월별현황</div>
				  </div>
				  <div class="main_slids fade">
				  	<div class="main_slidschart"></div>
					<div class="main_slidslabel">기초조사 지역별 현황</div>
				  </div>
				  <div class="main_slids fade">
				  	<div class="main_slidschart"></div>
				  	<div class="main_slidslabel">기초조사 전체현황</div>
				  </div>
				  <div class="main_slids fade">
				  	<div class="main_slidschart"></div>
				  	<div class="main_slidslabel">외관점검 월별현황</div>
				  </div>
				  <div class="main_slids fade">
				  	<div class="main_slidschart"></div>
				  	<div class="main_slidslabel">외관점검 지역별 현황</div>
				  </div>
				  <div class="main_slids fade">
				  	<div class="main_slidschart"></div>
				  	<div class="main_slidslabel">외관점검 전체현황</div>
				  </div>
				  <!-- 다음&이전 버튼 -->
				  <a class="main_slidsprev" onclick="fnChangeSlides(-1)">&#10094;</a>
				  <a class="main_slidsnext" onclick="fnChangeSlides(1)">&#10095;</a>
				  <!-- 다음&이전 버튼 -->
				</div>
				<!-- 현재페이지 표시 -->
				<div style="text-align:center">
				  <span class="main_slidsdot" onclick="fnMoveSlides(1)"></span>
				  <span class="main_slidsdot" onclick="fnMoveSlides(2)"></span>
				  <span class="main_slidsdot" onclick="fnMoveSlides(3)"></span>
				  <span class="main_slidsdot" onclick="fnMoveSlides(4)"></span>
				  <span class="main_slidsdot" onclick="fnMoveSlides(5)"></span>
				  <span class="main_slidsdot" onclick="fnMoveSlides(6)"></span>
				</div>
				<!-- 현재페이지 표시 -->
				<!-- 2021-11-17 메인화면 차트-->
			</div>
			<ul class="visual_right">
				<li><a href="#" onclick="javascript:fncGoToLayer('bscSvycompt'); return false;">기초조사 주제도</a></li>
				<li><a href="/sys/lss/bsc/sct/selectBscSvyComptList.do">기초조사 완료정보</a></li>
				<li><a href="/sys/spt/sct/selectBscComptReportList.do">완료보고서</a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
				<li>
					<a href="/file/치산정보시스템_사용자메뉴얼_내부용.pdf">도움말</a>
				</li>
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_USER')">
				<li>
					<a href="/file/치산정보시스템_사용자메뉴얼.pdf">도움말</a>
				</li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	<div class="bottom_content">
		<div class="notice_con">
			<div class="notice">
				<h3 class="title">공지사항</h3>
				<ul>
					<c:if test="${fn:length(noticeList) == 0}">
						<li><span class="noContents">조회된 공지사항이 없습니다.</span></li>
					</c:if>
					<c:forEach var="result" items="${noticeList}" varStatus="status">
						<li><a href="<c:url value='/sys/bbs/ntb/selectNoticeDetail.do'/>?nttId=${result.nttId}"><c:out value="${result.nttSj}"/></a></li>
					</c:forEach>
				</ul>
				<a href="/sys/bbs/ntb/noticeList.do" class="more">더보기</a>
			</div>
			<div class="notice">
				<h3 class="title">게시판</h3>
				<ul>
					<c:if test="${fn:length(articleList) == 0}">
						<li><span class="noContents">조회된 게시물이 없습니다.</span></li>
					</c:if>
					<c:forEach var="result" items="${articleList}" varStatus="status">
						<li><a href="<c:url value='/sys/bbs/art/selectArticleDetail.do'/>?nttId=${result.nttId}"><c:out value="${result.nttSj}"/></a></li>
					</c:forEach>
				</ul>
				<a href="/sys/bbs/art/articleList.do" class="more">더보기</a>
			</div>
		</div>
		 <div class="apk_qrcode">
			<a href="/file/가온-release.apk"> <span class="title"><span>전자야장 설치파일 </span>QRcode</span>
			</a>
		</div>
	</div>
	</div>
	<script type="text/javascript">
	var slideIndex = 1;
	fnShowSlides(slideIndex);
	fnLoadChart();
	
	/**
	 *@author ipodo
	 *@name fnChangeSlides
	 *@param {Number} n - number
	 *@returns 
	 *@description 슬라이드 페이지 변경 
	**/
	function fnChangeSlides(n) { fnShowSlides(slideIndex += n); }

	/**
	 *@author ipodo
	 *@name fnMoveSlide
	 *@param {Number} n - number
	 *@returns 
	 *@description 슬라이드 페이지 이동
	**/ 
	function fnMoveSlide(n) { fnShowSlides(slideIndex = n); }

	/**
	*@author ipodo
	*@name fnShowSlides
	*@param {Number} n - number
	*@returns 
	*@description 슬라이드 페이지 보이기
	**/
	function fnShowSlides(n) {
	  var i;
	  var slides = document.getElementsByClassName("main_slids");
	  
	  var dots = document.getElementsByClassName("main_slidsdot");
	  if (n > slides.length) {slideIndex = 1}
	  if (n < 1) {slideIndex = slides.length}
	  for (i = 0; i < slides.length; i++) {
		  slides[i].style.display = "none";
	      
	  }
	  for (i = 0; i < dots.length; i++) {
	      dots[i].className = dots[i].className.replace(" active", "");
	  }
	  slides[slideIndex-1].style.display = "block";
	  dots[slideIndex-1].className += " active";
	}
	
	/**
	*@author ipodo
	*@name fnOnChart
	*@param {Number} i - index
	*@returns 
	*@description 슬라이드 페이지 보이기
	**/
	function fnLoadChart() {
		var el1 = document.getElementsByClassName('main_slidschart')[0];
		var type1 = 'bar';
		var data1 = JSON.parse('${svyMonth}');
		var categories1 = ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'];
		
		var el2 = document.getElementsByClassName('main_slidschart')[1];
		var type2 = 'bar';
		var data2 = JSON.parse('${svyRegion}');
		var categories2 = ["서울","부산","대구","인천","광주","대전","울산","세종","경기","강원","충북","충남","전북","전남","경북","경남","북부청","동부청","남부청","중부청","서부청"];

		data2 = data2.filter(function (d, i) { return i != 0 });
		
		var el3 = document.getElementsByClassName('main_slidschart')[2];
		var type3 = 'pie';
		var data = JSON.parse('${svySum}');
		var data3 = [];
		
		var el4 = document.getElementsByClassName('main_slidschart')[3];
		var type4 = 'bar';
		var data4 = JSON.parse('${svyMonth2}');
		var categories4 = ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'];
		
		var el5 = document.getElementsByClassName('main_slidschart')[4];
		var type5 = 'bar';
		var data5 = JSON.parse('${svyRegion2}');
		var categories5 = ["강원","경기","경남","경북","광주","대구","대전","부산","서울","세종","울산","인천","전남","전북","제주","충남","충북"];

		data5 = data5.filter(function (d, i) { return i != 0 });
		
		var el6 = document.getElementsByClassName('main_slidschart')[5];
		var type6 = 'pie';
		var _data = JSON.parse('${svySum2}');
		var data6 = [];
		
		for(var key in data) { data3.push([key, data[key]]); }
		for(var key in _data) { data6.push([key, _data[key]]); }
		
		var chart1 = fnCreateChart(el1, type1, [data1[1]], categories1, '기초조사 월별현황', true, false);
		var chart2 = fnCreateChart(el2, type2, data2, categories2, '기초조사 지역별 현황', true, true, {width: {ratio: 0.2}});
		var chart3 = fnCreateChart(el3, type3, data3, null, '기초조사 전체현황', false, false);
		var chart4 = fnCreateChart(el4, type4, [data4[1]], categories4, '외관점검 월별현황', true, false);
		var chart5 = fnCreateChart(el5, type5, data5, categories5, '외관점검 지역별 현황', true, true, {width: {ratio: 0.2}});
		var chart6 = fnCreateChart(el6, type6, data6, null, '외관점검 전체현황', false, false);
	}
	
	/**
	*@author ipodo
	*@name fnCreateChart
	*@param {Element} element - bind element
	*@param {String} type - chart type
	*@param {Array} data - data
	*@param {Array} categories - data categories
	*@param {String} label - chart label
	*@param {boolean} visible - chart label visible
	*@returns {c3}
	*@description 슬라이드 페이지 보이기
	**/
	function fnCreateChart(element, type, data, categories, label, visible, tick, options) {
		return c3.generate({
			bindto: element,
		    data: {
		        columns: data,
		        type: type
		    },
		    axis: {
		    	x: {
		    		type: 'category',
		    		show: visible,
		    		 tick: tick ? {
		    		      centered: true,
		    		      rotate: 75,
		    		      multiline: true
		    		    } : {},
		    	    categories: categories ? categories : []
		   		}, 
		    },
		    bar: options ? options : {}
		});
	}
	</script>
</section>
<!--  컨첸츠 E -->