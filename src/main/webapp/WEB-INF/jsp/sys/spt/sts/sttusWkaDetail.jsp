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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusWkaManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusWkaDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<%-- <input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/> --%>
				<%-- <input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/> --%>
				<%-- <input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/> --%>
				<%-- <input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/> --%>
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
					<h4>판정등급 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="jdgmntgradArea"></div>
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
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>보호시설</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="safefctArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>인가</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="houseArea"></div>
					</div>				
				</div>
				<div>
					<div class="chartTitle">
						<h4>호수</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="lakeArea"></div>
					</div>				
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>상부 주요시설</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="highmainfctArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>하부 주요시설</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="lowmainfctArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>임상</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="frtpArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>임분밀도</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="stddnstArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>임분경급</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="stddmclsArea"></div>
					</div>
				</div>
			</div>
		</div>
		<script>
			// 판정등급별
			var jdgmntgradCols = ["jdgmntgrad"]; 
			var jdgmntgradVals = ["등급건수"];
			
			JSON.parse('${jdgmntgradList}').forEach(function (el) {
				jdgmntgradCols.push(el.jdgmntgrad);
				jdgmntgradVals.push(el.cnt);
			});
			
			c3.generate({bindto:'#jdgmntgradArea', data:{type:"bar", x:"jdgmntgrad", columns:[jdgmntgradCols,jdgmntgradVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
			
			
			// 시도별 통계
			var adminCols = ["sd"];
			var adminVals = ["조사건수"];
			
			JSON.parse('${adminList}').forEach(function (el) {
				adminCols.push(el.sd);
				adminVals.push(el.cnt);
			});
			
			c3.generate({bindto:'#adminArea', data:{type:"bar", x:"sd", columns:[adminCols,adminVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
			
			
			// 보호시설
			var safefctList = {}; 
			// 인가
			var houseList = {};
			// 호수
			var lakeList = {}; 
			
			JSON.parse('${safefctList}').forEach(function (el) {
				safefctList[el.safefct] = el.cnt;
			});
			JSON.parse('${houseLakeList}').forEach(function (el) {
				if(el.title.indexOf('인가')>-1) {
					houseList[el.val] = el.cnt;
				}
				else {
					lakeList[el.val] = el.cnt;
				}
			});

			c3.generate({bindto:'#safefctArea', data:{type:'donut', json: safefctList}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
			c3.generate({bindto:'#houseArea', data:{type:'donut', json: houseList}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
			c3.generate({bindto:'#lakeArea', data:{type:'donut', json: lakeList}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
			
			
			// 상부 주요시설
			var highmainfctList = {}; 
			// 하부 주요시설
			var lowmainfctList = {};
			
			JSON.parse('${highmainfctList}').forEach(function (el) {
				highmainfctList[el.highmainfct] = el.cnt;
			});
			
			JSON.parse('${lowmainfctList}').forEach(function (el) {
				lowmainfctList[el.lowmainfct] = el.cnt;
			});
			
			c3.generate({bindto:'#highmainfctArea', data:{type:'donut', json: highmainfctList}, legend: {show: true,position: 'right'}, size:{width: 430,height: 250}});
			c3.generate({bindto:'#lowmainfctArea', data:{type:'donut', json: lowmainfctList}, legend: {show: true,position: 'right'}, size:{width: 430,height: 250}});
			
			
			// 임도
			var frtpCols = ["frtp"];
			var frtpVals = ["count"];
			//임분밀도
			var stddnstCols = ["stddnst"];
			var stddnstVals = ["count"];
			//임분경급
			var stddmclsCols = ["stddmcls"];
			var stddmclsVals = ["count"];
			
			JSON.parse('${frtpList}').forEach(function (el) {
				frtpCols.push(el.frtp);
				frtpVals.push(el.cnt);
			});
			JSON.parse('${stddnstList}').forEach(function (el) {
				stddnstCols.push(el.stddnst);
				stddnstVals.push(el.cnt);
			});
			JSON.parse('${stddmclsList}').forEach(function (el) {
				stddmclsCols.push(el.stddmcls);
				stddmclsVals.push(el.cnt);
			});
			
			c3.generate({bindto:'#frtpArea', data:{type:"bar", x:"frtp", columns:[frtpCols,frtpVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: false, position: 'right'}, size:{width: 340,height: 180}}); 
			c3.generate({bindto:'#stddnstArea', data:{type:"bar", x:"stddnst", columns:[stddnstCols,stddnstVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: false, position: 'right'}, size:{width: 340,height: 180}}); 
			c3.generate({bindto:'#stddmclsArea', data:{type:"bar", x:"stddmcls", columns:[stddmclsCols,stddmclsVals]}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}},  legend: {show: false, position: 'right'}, size:{width: 340,height: 180}}); 
		</script>
	</div>
