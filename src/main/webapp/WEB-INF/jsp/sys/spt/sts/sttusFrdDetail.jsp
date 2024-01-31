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
<c:set var="pageTitle"><spring:message code="sysSptSts.sttusFrdManage.title"/></c:set>
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
			<form id="listForm" action="/sys/spt/sts/sttusFrdDetail.do" method="post">
				<input name="gid" type="hidden" value="">
				<input name="frdType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
				<input name="svyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
				<input name="compentauth" type="hidden" value="<c:out value='${searchVO.compentauth}'/>"/>
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
									<label for="frdTypeView" class="Hidden"><spring:message code="sysSptSts.sttusBsc.type"/></label>
									<select id="frdTypeView" name="frdTypeView" title="<spring:message code="sysSptSts.sttusBsc.type"/>">
										<option value=""<c:if test="${empty searchVO.frdType || searchVO.frdType == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<c:forEach var="svyTypeCodeInfo" items="${typeCodeList}" varStatus="status">
										<option value="<c:out value="${svyTypeCodeInfo.codeNm}"/>"<c:if test="${searchVO.frdType eq svyTypeCodeInfo.codeNm}">selected="selected"</c:if>><c:out value="${svyTypeCodeInfo.codeNm}"/></option>
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
								<th>관할별</th>
								<td>
									<select id="compentAssort" name="compentAssort" onchange="fncCompentAuthChange(); return false;">
									    <option value="" <c:if test="${searchVO.compentAssort eq ''}">selected="selected"</c:if>><spring:message code="title.all" /></option>
									    <option value="국유림" <c:if test="${searchVO.compentAssort eq '국유림'}">selected="selected"</c:if>>국유림</option>
									    <option value="민유림" <c:if test="${searchVO.compentAssort eq '민유림'}">selected="selected"</c:if>>민유림</option>
									</select>
										<select id="compentauthView" name="compentauthView" onchange="fncSubCompentAuthChange(this.value); return false;">
											<option value=""<c:if test="${empty searchVO.compentauth || searchVO.compentauth == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
											<c:if test="${searchVO.compentAssort eq '국유림'}">
												<c:forEach var="svyRegion1CodeInfo" items="${region1CodeList}" varStatus="status">
												<option value="<c:out value="${svyRegion1CodeInfo.code}"/>"<c:if test="${searchVO.compentauth eq svyRegion1CodeInfo.code}">selected="selected"</c:if>><c:out value="${svyRegion1CodeInfo.codeNm}"/></option>
												</c:forEach>
											</c:if>
											<c:if test="${searchVO.compentAssort eq '민유림'}">
												<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
												<option value="<c:out value="${svySdCodeInfo.adminNm}"/>"<c:if test="${searchVO.compentauth eq svySdCodeInfo.adminNm}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
												</c:forEach>
											</c:if>
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
						<div id="frdTypeAll"></div>
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
					<h4>관할별 현황</h4>
					<p>전체현황</p>
				</div>
				<div>
					<div id="barSurveyRegion"></div>
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
			<div class="chartSub1">
				<div class="chartTitle">
					<h4>항목별 현황</h4>
					<p>전체 유/무 현황</p>
				</div>
				<div>
					<div id="barSurveyAt"></div>
				</div>
			</div>
			<div class="chartSub3">
				<div>
					<div class="chartTitle">
						<h4>보호시설 현황</h4>
						<p>유형별 전체현황</p>
					</div>
					<div>
						<div id="safeFctArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>사방시설 현황</h4>
						<p>항목별 전체현황</p>
					</div>
					<div>
						<div id="ecnrFcltyArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>주요수종 및 주요식생 현황</h4>
						<p>건수별 전체현황</p>
					</div>
					<div>
						<div id="mainArea"></div>
					</div>
				</div>
			</div>
			<div class="chartSub2">
				<div>
					<div class="chartTitle">
						<h4>계류종류 및 현황</h4>
						<p>분류별 전체현황</p>
					</div>
					<div>
						<div id="mrngKindArea"></div>
					</div>
				</div>
				<div>
					<div class="chartTitle">
						<h4>붕괴우려지역 현황</h4>
						<p>분류별 전체현황</p>
					</div>
					<div>
						<div id="clpsCnrnArea"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
// 유형별
var frdtypeCols = ["frdtype"]; 
var frdtypeVals = ["개수"];

JSON.parse('${frdTypeList}').forEach(function (el) {
	frdtypeCols.push(el.frdtype);
	frdtypeVals.push(el.cnt);
});
c3.generate({
	bindto:'#frdTypeAll',
	data:{
		type:"bar",
		x:"frdtype",
		columns:[frdtypeCols,frdtypeVals]
	},
	bar:{
		width:{
			ratio: 0.3
		}
	},
	axis:{
		x:{
			type: 'category',
			tick: {
					center:true, 
					multiline: true
				}
			}
	},
	legend: {
		show: true,
		position: 'right'
	},
	size:{
		width: 430,
		height: 270
	}
});

// 관할별
c3.generate({
	bindto:"#barSurveyRegion",
	data:{
		type:"bar",
		x:"nm",
		columns:${svyRegion}
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
            },
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

// 지역별
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

// 월별
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

// 보호시설
c3.generate({
	bindto:"#safeFctArea",
	data:{
		type:"bar",
		x:"safetcttype",
		columns:${safeFctList}
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

// 유무현황
c3.generate({
	bindto:"#barSurveyAt",
	data:{
		type:"bar",
		x:"nm",
		columns:${atList}
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
		width:880,
		height:270
	}
});

//계류종류 및 현황
c3.generate({
	bindto:"#mrngKindArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${mrngKindList}
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
		width: 430,
		height: 270
	}
});

//붕괴우려지역
c3.generate({
	bindto:"#clpsCnrnArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${clpsCnrnList}
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
		width: 430,
		height: 270
	}
});

//사방시설 현황
c3.generate({
	bindto:"#ecnrFcltyArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${ecnrList}
	},
	bar:{
		width: {ratio: 0.2}
	},
	axis: {
		x: {
			type: "category"
		},
		y: {
	        tick: {
	        	format: d3.format("d")
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
		width:290,
		height: 180
	}
});

//주요수종 및 주요식생
c3.generate({
	bindto:"#mainArea",
	data:{
		type:"bar",
		x:"nm",
		columns:${mainList}
	},
	bar:{
		width: {ratio: 0.2}
	},
	axis: {
		x: {
			type: "category"
		},
		y: {
	        tick: {
	        	format: d3.format("d")
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
		width:290,
		height: 180
	}
});
</script>