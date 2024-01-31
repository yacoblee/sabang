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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusMseManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusMseDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="eqpmntype" type="hidden" value="<c:out value='${searchVO.eqpmntype}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<tbody>
							<tr>
<%-- 								<th><spring:message code="sysSptSts.sttus.eqpmntype"/></th><!-- 장비유형 --> --%>
<!-- 								<td> -->
<%-- 									<label for="eqpmntypeView" class="Hidden"><spring:message code="sysSptSts.sttus.eqpmntype"/></label> --%>
<%-- 									<select id="eqpmntypeView" name="eqpmntypeView" title="<spring:message code="sysSptSts.sttus.eqpmntype"/>"> --%>
<%-- 										<option value=""<c:if test="${empty searchVO.eqpmntype || searchVO.eqpmntype == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('와이어신축계')}">selected="selected"</c:if>>와이어신축계</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('지중경사계')}">selected="selected"</c:if>>지중경사계</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('지하수위계')}">selected="selected"</c:if>>지하수위계</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('강우계')}">selected="selected"</c:if>>강우계</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('지표변위계')}">selected="selected"</c:if>>지표변위계</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('GPS변위계')}">selected="selected"</c:if>>GPS변위계</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('게이트웨이')}">selected="selected"</c:if>>게이트웨이</option> --%>
<%-- 										<option value=""<c:if test="${searchVO.eqpmntype.equals('노드')}">selected="selected"</c:if>>노드</option> --%>
<!-- 									</select> -->
<!-- 								</td> -->
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
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">검색</button>
					</div>
				</div>
			</form>
		</fieldset>
		<div class="BoardList chartDiv">
			<div class="chartSub1">
				<div>
					<div class="chartTitle">
						<h4>월별 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="yearArea"></div>
					</div>
				</div>	
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 와이어신축계 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="wireArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 지중경사계 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="licmeterArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 지하수위계 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="gwgaugeArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 강우계 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="raingaugeArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 지표변위계 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="surdpmArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 GPS변위계 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="gpsgaugeArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 게이트웨이 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="gatewayArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 노드 장비 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="nodeArea"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
c3.generate({bindto:'#yearArea',data:{type:'line',x:'nm',columns:${yearResult}},bar:{width: {ratio: 0.2}},axis: {x: {type: 'category'}},legend: {show: true,position: 'right',inset: {anchor: 'top-right',x:undefined,y: undefined,step: undefined}},size:{width: 860,height: 250}});
c3.generate({bindto:'#wireArea', data:{type:"bar", x:"nm", columns:${wireResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#licmeterArea', data:{type:"bar", x:"nm", columns:${licmeterResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#gwgaugeArea', data:{type:"bar", x:"nm", columns:${gwgaugeResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#raingaugeArea', data:{type:"bar", x:"nm", columns:${raingaugeResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#surdpmArea', data:{type:"bar", x:"nm", columns:${surdpmResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#gpsgaugeArea', data:{type:"bar", x:"nm", columns:${gpsgaugeResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#gatewayArea', data:{type:"bar", x:"nm", columns:${gatewayResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#nodeArea', data:{type:"bar", x:"nm", columns:${nodeResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
</script>

