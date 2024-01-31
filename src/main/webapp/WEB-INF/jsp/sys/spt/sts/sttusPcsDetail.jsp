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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusPcsManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusPcsDetail.do" method="post">
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
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>조사유형별 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="pcsTypeAll"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>월별 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="lineSurveyMonth"></div>
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
<!-- 			<div><h4>본댐 현황</h4></div> -->
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>본댐 (파손, 변위, 변형)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="orginlDam1Area"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>본댐 (기초부 세굴 및 수문시설)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="orginlDam3Area"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>본댐 (콘크리트 및 전석붙임)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="orginlDam4Area"></div>
					</div>
				</div>
			</div>
			<div class="chartSub1">
				<div>
					<div class="chartTitle">
						<h4>본댐 (균열, 누수)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="orginlDam2Area"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>측벽 (파손, 변위, 변형)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="sideWall1Area"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>물받이 (파손, 변위, 변형)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="wtr1Area"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>측벽 (균열, 누수)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="sideWall2Area"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>물받이 (균열)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="wtr3Area"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>측벽 (기초부 세굴)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="sideWall3Area"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>물받이 (기초부 세굴)</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="wtr2Area"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>사방댐 준설여부 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="drdgnArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>완료여부 현황</h4>
						<p>전체현황</p>
					</div>
					<div>
						<div id="cmpArea"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
//조사유형별
c3.generate({
	bindto:"#pcsTypeAll",
	data:{
		type:"bar",
		x:"nm",
		columns:${svyTypeList}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});
//지역별
c3.generate({
	bindto:"#adminArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${adminResult}
	},
 bar: {
     width: {
         ratio: 0.5
     }
 },
 axis: {
     x: {
         type: 'category',
         tick: {
             center: true,
             rotate: 65,
             multiline: true
         }
     },
 },
 legend: {
     show: true,
     position: 'right'
 },
 size: {
     width: 860,
     height: 280
 }
});

//월별
c3.generate({
	bindto:"#lineSurveyMonth",
	data:{
		type:"line",
		x:"name",
		columns:${svyMonth}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'inset',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined}
	},
	size:{
		width: 430,
 		height: 270
	}
});

//본댐 파손
c3.generate({
	bindto:"#orginlDam1Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${odDam1List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width:290,
		height: 180
	}
});

//본댐 균열
c3.generate({
	bindto:"#orginlDam2Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${odDam2List}
	},
	bar:{
		width: {ratio: 0.2}
	},
	axis: {
		x: {
			type: "category",
			tick: {
	             center: true,
	             rotate: 65,
	             multiline: true
	         }
		}
	},
	legend: {
		show: true,
		position: 'bottom',
		inset: {
			anchor: 'top-right',
			x:undefined,
			y: undefined,
			step: undefined
		}
	},
	size:{
	   width: 860,
	   height: 400
	}
});

//본댐 콘크리트 전석붙임
c3.generate({
	bindto:"#orginlDam3Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${odDam3List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width:290,
		height: 180
	}
});
//본댐 기초부세굴 및 수문
c3.generate({
	bindto:"#orginlDam4Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${odDam4List}
	},
	bar:{
		width: {ratio: 0.2}
	},
	axis: {
		x: {
			type: "category"
		}
	
	},
	legend: {
		show: true,
		position: 'bottom',
		inset: {
			anchor: 'top-right',
			x:undefined,
			y: undefined,
			step: undefined
		}
	},
	size:{
		width:290,
		height: 180
	}
});

//측벽 파손
c3.generate({
	bindto:"#sideWall1Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${sdWl1List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

//측벽 균열
c3.generate({
	bindto:"#sideWall2Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${sdWl2List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'inset',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

//측벽 기초부세굴
c3.generate({
	bindto:"#sideWall3Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${sdWl3List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

// 물받이 파손
c3.generate({
	bindto:"#wtr1Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${wtr1List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

// 물받이 균열
c3.generate({
	bindto:"#wtr2Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${wtr2List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

// 물받이 기초부세굴
c3.generate({
	bindto:"#wtr3Area",
	data:{
		type:"bar",
		x:"nm",
		columns:${wtr3List}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

//준설여부
c3.generate({
	bindto:"#drdgnArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${drdgnList}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});

//완료여부
c3.generate({
	bindto:"#cmpArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${cmpList}
	},
	bar:{
		width:{
			ratio:0.2
		}
	},
	axis:{
		x:{
			type:"category"
		}
	},
	legend:{
		show:true,
		position:'bottom',
		inset:{
			anchor:'top-right',
			x:undefined,
			y:undefined,
			step:undefined
		}
	},
	size:{
		width: 430,
 		height: 270
	}
});
</script>