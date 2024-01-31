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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusCnlManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusCnlDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<%-- <input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/> --%>
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
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
					<h4>시도별 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="adminArea"></div>
				</div>		
			</div>
			<div class="chartSub1">
				<div>
					<div class="chartTitle">
						<h4>적용공법</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="applcegnermhdArea"></div>
					</div>
				</div>		
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
						<h4>산사태등급</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="lndsldgrdeArea"></div>
					</div>		
			</div>
			<!-- 
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>산사태등급</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="lndsldgrdeArea"></div>
					</div>
				</div>		
				<div>
					<div class="chartTitle">
						<h4>평균경사</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="slopeavgArea"></div>
					</div>
				</div>		
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>임상도</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="frtptypeArea"></div>
					</div>
				</div>		
				<div>
					<div class="chartTitle">
						<h4>경급도</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="dmclstypeArea"></div>
					</div>
				</div>		
			</div> -->
		</div>
	</div>
	<script>
	// 시도별 통계
	var adminCols = ["sd"];
	var adminVals = ["조사건수"];
	
	JSON.parse('${adminList}').forEach(function (el) {
		adminCols.push(el.sd);
		adminVals.push(el.cnt);
	});
	
	c3.generate({bindto:'#adminArea', data:{type:"bar", x:"sd", columns:[adminCols,adminVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
	
	// 적용공법
	var applcegnermhdCols = ["applcegnermhd"]; 
	var applcegnermhdVals = ["count"];
	
	JSON.parse('${applcegnermhdList}').forEach(function (el) {
		applcegnermhdCols.push(el.applcegnermhd);
		applcegnermhdVals.push(el.cnt);
	});

	// 유역현황
// 	var dgrsttusCols = ["dgrsttus"]; 
// 	var dgrsttusVals = ["count"];
	
// 	JSON.parse('${dgrsttusList}').forEach(function (el) {
// 		dgrsttusCols.push(el.dgrsttus);
// 		dgrsttusVals.push(el.cnt);
// 	});
	
// 	c3.generate({bindto:'#applcegnermhdArea', data:{type:"bar", x:"applcegnermhd", columns:[applcegnermhdCols,applcegnermhdVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 430,height: 250}});
// 	c3.generate({bindto:'#dgrsttusArea', data:{type:"bar", x:"dgrsttus", columns:[dgrsttusCols,dgrsttusVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 430,height: 250}});
	
	// 산사태등급
	var lndsldgrdeCols = ["lndsldgrde"]; 
	var lndsldgrdeVals = ["count"];
	
	JSON.parse('${lndsldgrdeList}').forEach(function (el) {
		lndsldgrdeCols.push(el.lndsldgrde);
		lndsldgrdeVals.push(el.cnt);
	});

	// 평균경사
	var slopeavgCols = ["slopeavg"]; 
	var slopeavgVals = ["count"];
	
	JSON.parse('${slopeavgList}').forEach(function (el) {
		slopeavgCols.push(el.slopeavg);
		slopeavgVals.push(el.cnt);
	});
	
	c3.generate({bindto:'#lndsldgrdeArea', data:{type:"bar", x:"lndsldgrde", columns:[lndsldgrdeCols,lndsldgrdeVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
	//c3.generate({bindto:'#slopeavgArea', data:{type:"bar", x:"slopeavg", columns:[slopeavgCols,slopeavgVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 430,height: 250}});
	
	/* 
	// 임상도
	var frtptypeCols = ["frtptype"]; 
	var frtptypeVals = ["count"];
	
	JSON.parse('${frtptypeList}').forEach(function (el) {
		frtptypeCols.push(el.frtptype);
		frtptypeVals.push(el.cnt);
	});

	// 경급도
	var dmclstypeCols = ["dmclstype"]; 
	var dmclstypeVals = ["count"];
	
	JSON.parse('${dmclstypeList}').forEach(function (el) {
		dmclstypeCols.push(el.dmclstype);
		dmclstypeVals.push(el.cnt);
	});
	
	c3.generate({bindto:'#frtptypeArea', data:{type:"bar", x:"frtptype", columns:[lndsldgrdeCols,lndsldgrdeVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 430,height: 250}});
	c3.generate({bindto:'#dmclstypeArea', data:{type:"bar", x:"dmclstype", columns:[dmclstypeCols,dmclstypeVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 430,height: 250}});
	 */
	</script>