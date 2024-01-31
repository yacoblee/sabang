<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle"><spring:message code="sysLssCnl.surveySttus.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">취약지역 해제조사</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<div class="BoardList chartDiv">
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>취약지역 해제조사</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="pieSurveyAll"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>취약지역 해제조사</h4>
						<p>관할별 현황</p>
					</div>
					<div>
						<div id="barSurveyRegion"></div>
					</div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>취약지역 해제조사</h4>
					<p>월별 현황</p>
				</div>
				<div>
					<div id="lineSurveyMonth"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
c3.generate({bindto:"#pieSurveyAll",data:{type:"donut",json:JSON.parse('${svySum}')},legend: {show: true,position: 'right'},size:{width: 450,height: 250}});
c3.generate({bindto:"#barSurveyRegion",data:{type:"bar",x:"nm",columns:${svyRegion}},bar:{width: {ratio: 0.2}},axis: {x: {type: "category",},},legend: {show: true,position: 'inset',inset: {anchor: 'top-right',x:undefined,y: undefined,step: undefined}},size:{width: 430,height: 270},color:{pattern: ['#ff7f0e']}});
c3.generate({
	bindto:"#lineSurveyMonth",
	data:{
		type:"line",
		x:"name",
		columns:${svyMonth}
	},bar:{
		width: {
  			ratio: 0.2,            
		}
	},axis: {
		x: {
			type: "category",
		},
	},legend: {
        show: true,
        position: 'inset',
        inset: {
            anchor: 'top-right',
            x:undefined,
            y: undefined,
            step: undefined
        }
    },size:{
    	width: 880,
    	height: 270
    },color:{
    	pattern: ['#ff7f0e']
    }
});
</script>