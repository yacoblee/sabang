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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusBscManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusBscDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<input name="svyRegion1" type="hidden" value="<c:out value='${searchVO.svyRegion1}'/>"/>
				<input name="svyRegion2" type="hidden" value="<c:out value='${searchVO.svyRegion2}'/>"/>
				<input name="svySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
				<input name="svySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
				<input name="svyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
				<input name="svyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
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
								<th><spring:message code="sysSptSts.sttusBsc.region"/></th><!-- 관할1 -->
								<td>
									<label for="svyRegion1View" class="Hidden"><spring:message code="sysSptSts.sttusBsc.region1"/></label>
									<select id="svyRegion1View" name="svyRegion1View" title="<spring:message code="sysSptSts.sttusBsc.region1"/>" onchange="fncRegionChange(event); return false;">
										<option value=""<c:if test="${empty searchVO.svyRegion1 || searchVO.svyRegion1 == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyRegion1CodeInfo" items="${region1CodeList}" varStatus="status">
										<option value="<c:out value="${svyRegion1CodeInfo.code}"/>"<c:if test="${searchVO.svyRegion1 eq svyRegion1CodeInfo.code}">selected="selected"</c:if>><c:out value="${svyRegion1CodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
									<label for="svyRegion2View" class="Hidden"><spring:message code="sysSptSts.sttusBsc.region2"/></label>
									<select id="svyRegion2View" name="svyRegion2View" title="<spring:message code="sysSptSts.sttusBsc.region2"/>" >
										<option value=""<c:if test="${empty searchVO.svyRegion2 || searchVO.svyRegion2 == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyRegion2CodeInfo" items="${region2CodeList}" varStatus="status">
										<option value="<c:out value="${svyRegion2CodeInfo.code}"/>"<c:if test="${searchVO.svyRegion2 eq svyRegion2CodeInfo.code}">selected="selected"</c:if>><c:out value="${svyRegion2CodeInfo.codeNm}"/></option>
										</c:forEach>
									</select>
								</td>								
							</tr>
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
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSearch(); return false;">검색</button>
					</div>
				</div>
			</form>
		</fieldset>
		<div class="BoardList chartDiv">
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>실태조사 필요성</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="ncsstyArea"></div>
					</div>
				</div>
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
					<h4>관할별 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="regionArea"></div>
				</div>
			</div>
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>지역별 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="adminArea"></div>
				</div>
			</div>
			
			<c:if test="${searchVO.svyType eq '산사태'}">
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>보호대상</h4>
						<p>점수현황</p>
					</div>
					<div>
						<div id="saftyscoreArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>경사길이(m)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="slenscoreArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>경사도(°)</h4>
						<p>점수현황</p>
					</div>
					<div>
						<div id="slopescoreArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>사면형</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="sformscoreArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>임상</h4>
						<p>점수현황</p>
					</div>
					<div>
						<div id="frstfrscoreArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>모암</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="prntrckscoreArea"></div>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${searchVO.svyType eq '토석류'}">
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>보호대상</h4>
						<p>점수현황</p>
					</div>
					<div>
						<div id="saftyscoreArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>황폐발생원</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="devoccausescoreArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>계류평균경사(°)</h4>
						<p>점수현황</p>
					</div>
					<div>
						<div id="trntavgslpscoreArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>집수면적(ha)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="wclctareascoreArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>총계류길이(m)</h4>
						<p>점수현황</p>
					</div>
					<div>
						<div id="tltrntltscoreArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>전석분포비율(%)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="distbmdsbratescoreArea"></div>
					</div>
				</div>
			</div>
			</c:if>
		</div>
	</div>
</div>
<script>
c3.generate({bindto:'#ncsstyArea', data:{type:'donut', json:${ncsstyResult}}, legend: {show: true,position: 'right'}, size:{width: 450,height: 250}});
c3.generate({bindto:'#adminArea', data:{type:"bar", x:"nm", columns:${adminResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#regionArea', data:{type:'bar', x:'nm', columns:${regionResult}}, bar:{width: {ratio: 0.5}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}}, legend: {show: true, position: 'right'}, size:{width: 860,height: 280}});
c3.generate({bindto:'#yearArea',data:{type:'line',x:'nm',columns:${yearResult}},bar:{width: {ratio: 0.2}},axis: {x: {type: 'category'}},legend: {show: true,position: 'inset',inset: {anchor: 'top-right',x:undefined,y: undefined,step: undefined}},size:{width: 430,height: 250}});

/* 공통 조사인자 */
c3.generate({bindto:'#saftyscoreArea', data:{type:"bar", x:"nm", columns:${saftyscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});

/* 산사태 조사인자 */
c3.generate({bindto:'#slenscoreArea', data:{type:"bar", x:"nm", columns:${slenscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#slopescoreArea', data:{type:"bar", x:"nm", columns:${slopescoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#sformscoreArea', data:{type:"bar", x:"nm", columns:${sformscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#frstfrscoreArea', data:{type:"bar", x:"nm", columns:${frstfrscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#prntrckscoreArea', data:{type:"bar", x:"nm", columns:${prntrckscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});

/* 토석류 조사인자 */
c3.generate({bindto:'#devoccausescoreArea', data:{type:"bar", x:"nm", columns:${devoccausescoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#trntavgslpscoreArea', data:{type:"bar", x:"nm", columns:${trntavgslpscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#wclctareascoreArea', data:{type:"bar", x:"nm", columns:${wclctareascoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#tltrntltscoreArea', data:{type:"bar", x:"nm", columns:${tltrntltscoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
c3.generate({bindto:'#distbmdsbratescoreArea', data:{type:"bar", x:"nm", columns:${distbmdsbratescoreResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true},},}, legend: {show: true, position: 'top'}, size:{width: 430,height: 250}});
</script>

