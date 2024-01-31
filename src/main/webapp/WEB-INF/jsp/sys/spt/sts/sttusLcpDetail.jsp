<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusLcpManage.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">업무지원</a></li>
		<li><a href="#">통계관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="/sys/spt/sts/sttusLcpDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<%-- <input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/> --%>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<%-- 
				<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
				 --%>
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
								<%-- 
								<th><spring:message code="sysSptSts.sttusBsc.type"/></th><!-- 조사유형 -->
								<td>
									<label for="svyTypeView" class="Hidden"><spring:message code="sysSptSts.sttusBsc.type"/></label>
									<select id="svyTypeView" name="svyTypeView" title="<spring:message code="sysSptSts.sttusBsc.type"/>">
										<option value=""<c:if test="${empty searchVO.svyType || searchVO.svyType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.svyType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>
								 --%>
								<th><spring:message code="sysSptSts.sttusBsc.year"/></th><!-- 연도 -->
								<td>
									<label for="svyYearView" class="Hidden"><spring:message code="sysSptSts.sttusBsc.type"/></label>
									<select id="svyYearView" name="svyYearView" title="<spring:message code="sysSptSts.sttusBsc.type"/>">
										<option value=""<c:if test="${empty searchVO.svyYear || searchVO.svyYear == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyYearCodeInfo" items="${yearCodeList}" varStatus="status">
										<option value="<c:out value="${svyYearCodeInfo.svyyear}"/>"<c:if test="${searchVO.svyYear eq svyYearCodeInfo.svyyear}">selected="selected"</c:if>><c:out value="${svyYearCodeInfo.svyyear}"/></option>
										</c:forEach>
									</select>
								</td>							
							</tr>
							<%-- 
							<tr>
								<th><spring:message code="sysSptSts.sttusBsc.addr"/></th><!-- 주소 -->
								<td colspan="5">
									<label for="svySdView" class="Hidden"><spring:message code="sysSptSts.sttusBsc.sd"/></label>
									<select id="svySdView" name="svySdView" title="<spring:message code="sysSptSts.sttusBsc.sd"/>" onchange="fncAdministCtprvnChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
										<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svySggView" class="Hidden"><spring:message code="sysSptSts.sttusBsc.sgg"/></label>
									<select id="svySggView" name="svySggView" title="<spring:message code="sysSptSts.sttusBsc.sgg"/>" onchange="fncAdministSignguChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
										<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyEmdView" class="Hidden"><spring:message code="sysSptSts.sttusBsc.emd"/></label>
									<select id="svyEmdView" name="svyEmdView" title="<spring:message code="sysSptSts.sttusBsc.emd"/>" onchange="fncAdministEmdChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
										<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
										</c:forEach>
									</select>
								</td>
							</tr>
							 --%>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">검색</button>
					</div>
				</div>
			</form>
		</fieldset>
		</div>
		<div class="BoardList chartDiv">
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>판정등급 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="lastgrdArea"></div>
				</div>		
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>시도별 조사 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="adminArea"></div>
				</div>			
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>주 구성암석 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="cmprokvalArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>암석 풍화 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="rokwthrvalArea"></div>
					</div>				
				</div>
			</div>
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>타 지층 및 관입암 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="instrokatArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>지질구조 단층 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="geologyfltArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>지질구조 습곡 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="geologyfldArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>직접징후 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="dirsymptmvalArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>간접징후 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="indirsymptmvalArea"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		// 판정등급별
		var lastgrdCols = ["lastgrd"]; 
		var lastgrdVals = ["등급건수"];
		
		JSON.parse('${lastgrdList}').forEach(function (el) {
			lastgrdCols.push(el.lastgrd);
			lastgrdVals.push(el.cnt);
		});
		
		c3.generate({bindto:'#lastgrdArea', data:{type:"bar", x:"lastgrd", columns:[lastgrdCols,lastgrdVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
		
		
		// 시도별 통계
		var adminCols = ["sd"];
		var adminVals = ["조사건수"];
		
		JSON.parse('${adminList}').forEach(function (el) {
			adminCols.push(el.sd);
			adminVals.push(el.cnt);
		});
		
		c3.generate({bindto:'#adminArea', data:{type:"bar", x:"sd", columns:[adminCols,adminVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
		
		// 주 구성암석
		var cmprokvalList = {}; 
		JSON.parse('${cmprokvalList}').forEach(function (el) {
			cmprokvalList[el.cmprokval] = el.cnt;
		});
		
		// 암석 풍화
		var rokwthrvalList = {}; 
		JSON.parse('${rokwthrvalList}').forEach(function (el) {
			rokwthrvalList[el.rokwthrval] = el.cnt;
		});
		
		
		c3.generate({bindto:'#cmprokvalArea', data:{type:'donut', json: cmprokvalList}, legend: {show: true,position: 'right'}, size:{width: 430,height: 250}});
		c3.generate({bindto:'#rokwthrvalArea', data:{type:'donut', json: rokwthrvalList}, legend: {show: true,position: 'right'}, size:{width: 430,height: 250}});
		
		// 타 지층 및 관입암
		var instrokatList = {}; 
		JSON.parse('${instrokatList}').forEach(function (el) {
			instrokatList[el.instrokat] = el.cnt;
		});
		
		// 지질구조 단층
		var geologyfltList = {};
		// 지질구조 습곡
		var geologyfldList = {}; 
		
		JSON.parse('${geologyList}').forEach(function (el) {
			if(el.title.indexOf('단층')>-1) {
				geologyfltList[el.val] = el.cnt;
			}
			else {
				geologyfldList[el.val] = el.cnt;
			}
		});

		c3.generate({bindto:'#instrokatArea', data:{type:'donut', json: instrokatList}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
		c3.generate({bindto:'#geologyfltArea', data:{type:'donut', json: geologyfltList}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
		c3.generate({bindto:'#geologyfldArea', data:{type:'donut', json: geologyfldList}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
		
		
		// 지질구조 단층
		var dirsymptmvalList = {};
		// 지질구조 습곡
		var indirsymptmvalList = {}; 
		
		JSON.parse('${lcpDirsymptmList}').forEach(function (el) {
			if(el.title.indexOf('직접')>-1) {
				dirsymptmvalList[el.val] = el.cnt;
			}
			else {
				indirsymptmvalList[el.val] = el.cnt;
			}
		});
		
		c3.generate({bindto:'#dirsymptmvalArea', data:{type:'donut', json: dirsymptmvalList}, legend: {show: true,position: 'right'}, size:{width: 430,height: 250}});
		c3.generate({bindto:'#indirsymptmvalArea', data:{type:'donut', json: indirsymptmvalList}, legend: {show: true,position: 'right'}, size:{width: 430,height: 250}});
	</script>
