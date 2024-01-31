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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusAprManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusAprDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="svyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
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
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>최종점검결과</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="fckrsltArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>조치사항</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="mngmtrArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>사방지지정해제여부</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="appnrelisArea"></div>
					</div>
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
			
			
			<c:if test="${searchVO.svyType eq '사방댐 외관점검'}">
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>본댐 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="orginldamArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>측벽 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="sidewallArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>물받이 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="dwnsptArea"></div>
					</div>
				</div>
			</div>
			</c:if>
			
			<c:if test="${searchVO.svyType eq '계류보전 외관점검'}">
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>골막이 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="chkdamArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>기슭막이 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="rvtmntArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>바닥막이 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="grdstablArea"></div>
					</div>
				</div>
			</div>
			</c:if>
			
			<c:if test="${searchVO.svyType eq '산지사방 외관점검'}">
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>보강시설 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="reinfcArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>보호시설 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="prtcArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>배수시설 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="drngArea"></div>
					</div>
				</div>
			</div>
			</c:if>
			
			
			<div class="chartSub3">
				<c:if test="${searchVO.svyType eq '사방댐 외관점검'}">
				<div>
					<div class="chartTitle">
						<h4>저사상태 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="snddpsitArea"></div>
					</div>
				</div>
				</c:if>
				<c:if test="${searchVO.svyType eq '계류보전 외관점검'}">
				<div>
					<div class="chartTitle">
						<h4>계류상태 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="mooringArea"></div>
					</div>
				</div>
				</c:if>
				<c:if test="${searchVO.svyType eq '산지사방 외관점검'}">
				<div>
					<div class="chartTitle">
						<h4>사면상태 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="slopeArea"></div>
					</div>
				</div>
				</c:if>
				<div>
					<div class="chartTitle">
						<h4>수문 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="flugtArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>식생상태 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="vtnsttusArea"></div>
					</div>
				</div>				
			</div>
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>안전시설 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="sffctArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>접근도로 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="accssrdArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>기타 판정값</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="etcArea"></div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
<script>
c3.generate({bindto:'#fckrsltArea', data:{type:'donut', json:${fckrsltResult}}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
c3.generate({bindto:'#mngmtrArea', data:{type:'donut', json:${mngmtrResult}}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});
c3.generate({bindto:'#appnrelisArea', data:{type:'donut', json:${appnrelisResult}}, legend: {show: true,position: 'right'}, size:{width: 340,height: 180}});

c3.generate({bindto:'#adminArea', data:{type:"bar", x:"nm", columns:${adminResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, rotate: 65, multiline: true}}},  legend: {show: true, position: 'right',}, size:{width: 860,height: 280}});

/* 부대시설 - 수문*/
c3.generate({bindto:'#flugtArea', data:{type:"bar", x:"nm", columns:${flugtResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 부대시설 - 식생상태*/
c3.generate({bindto:'#vtnsttusArea', data:{type:"bar", x:"nm", columns:${vtnsttusResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 부대시설 - 안전시설*/
c3.generate({bindto:'#sffctArea', data:{type:"bar", x:"nm", columns:${sffctResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 부대시설 - 접근도로*/
c3.generate({bindto:'#accssrdArea', data:{type:"bar", x:"nm", columns:${accssrdResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 부대시설 - 기타*/
c3.generate({bindto:'#etcArea', data:{type:"bar", x:"nm", columns:${etcResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});

/* 사방댐 - 본댐  */
c3.generate({bindto:'#orginldamArea', data:{type:"bar", x:"nm", columns:${orginldamResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 사방댐 - 측벽 */
c3.generate({bindto:'#sidewallArea', data:{type:"bar", x:"nm", columns:${sidewallResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 사방댐 - 물받이 */
c3.generate({bindto:'#dwnsptArea', data:{type:"bar", x:"nm", columns:${dwnsptResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 사방댐 - 저사상태 */
c3.generate({bindto:'#snddpsitArea', data:{type:"bar", x:"nm", columns:${snddpsitResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});

/* 계류보전 - 골막이  */
c3.generate({bindto:'#chkdamArea', data:{type:"bar", x:"nm", columns:${chkdamResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 계류보전 - 기슭막이 */
c3.generate({bindto:'#rvtmntArea', data:{type:"bar", x:"nm", columns:${rvtmntResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 계류보전 - 바닥막이 */
c3.generate({bindto:'#grdstablArea', data:{type:"bar", x:"nm", columns:${grdstablResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 계류보전 - 계류상태 */
c3.generate({bindto:'#mooringArea', data:{type:"bar", x:"nm", columns:${mooringResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});

/* 산지사방 - 보강시설  */
c3.generate({bindto:'#reinfcArea', data:{type:"bar", x:"nm", columns:${reinfcResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 산지사방 - 보호시설 */
c3.generate({bindto:'#prtcArea', data:{type:"bar", x:"nm", columns:${prtcResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 산지사방 - 배수시설 */
c3.generate({bindto:'#drngArea', data:{type:"bar", x:"nm", columns:${drngResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
/* 산지사방 - 사면상태 */
c3.generate({bindto:'#slopeArea', data:{type:"bar", x:"nm", columns:${slopeResult}}, bar:{width: {ratio: 0.3}}, axis: {x: {type: 'category', tick: {center:true, multiline: true}}}, legend: {show: true, position: 'bottom'}, size:{width: 280,height: 150}});
 
</script>