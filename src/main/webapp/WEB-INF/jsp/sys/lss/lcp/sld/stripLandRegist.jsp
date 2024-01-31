<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<validator:javascript formName="lssLcpStripLand" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="sysLssLcp.stripLandList.title"/></c:set>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sldYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set> 
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">땅밀림실태조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.create" /></h3><!-- 대상지 등록 -->
	<div id="contents">
		<form:form id="listForm" commandName="lssLcpStripLand" action="${pageContext.request.contextPath}/sys/lss/lcp/sld/insertStripLand.do" method="post">
			<input type="hidden" name="sldId" value="${sldId}" />
			<input type="hidden" name="id" value="${sldId}" />
			<input type="hidden" name="sdTotalCnt" />
			<input type="hidden" name="sdCdList" />
			<input type="hidden" name="sdCntList" />
			<input name="schsvysldNm" type="hidden" value="<c:out value='${stripLandVO.svysldNm}'/>">
			<input name="schwriter" type="hidden" value="<c:out value='${stripLandVO.writer}'/>">
			<div class="BoardDetail">
				<!-- 행정구역 별 미조사 건수 및 비율 조회 -->
				<div>행정구역별 미조사 건수 조회 </div>
				<div class="sdListWrap" style="margin-bottom: 30px;"> 
					<c:forEach var="sdCodeListInfo" items="${sdCodeList}" varStatus="status">
						<input type="hidden" name="<c:out value="${sdCodeListInfo.adminCode}"/>_rankInfoCnt" value="<c:out value="${rankInfoCntList[status.count-1]}"/>"/>
						<input type="hidden" name="<c:out value="${sdCodeListInfo.adminCode }"/>_nm" value="<c:out value="${sdCodeListInfo.adminNm }"/>" />
						<div class="sdListBox">
							<div class="sdNm"><c:out value="${sdCodeListInfo.adminNm }"/></div>
							<div class="sdCnt"><c:out value="${rankInfoCntList[status.count-1]}"/></div>
						</div>								
					</c:forEach>
				</div>
				<!-- 작년도 행정구역 별 A,B등급 건수 및 비율 조회 -->
				<div><c:out value="${lastYear}"/>년 행정구역별 조사 건수 조회 </div>
				<div class="sdListWrap" style="margin-bottom: 30px;"> 
					<c:forEach var="sdCodeListInfo" items="${sdCodeList}" varStatus="status">
						<input type="hidden" name="<c:out value="${sdCodeListInfo.adminCode}"/>_rankInfoCnt" value="<c:out value="${lastRankInfoCntList[status.count-1]}"/>"/>
						<input type="hidden" name="<c:out value="${sdCodeListInfo.adminCode }"/>_nm" value="<c:out value="${sdCodeListInfo.adminNm }"/>" />
						<div class="sdListBox">
							<div class="sdNm"><c:out value="${sdCodeListInfo.adminNm }"/></div>
							<div class="sdCnt"><c:out value="${lastRankInfoCntList[status.count-1]}"/></div>
						</div>								
					</c:forEach>
				</div>
				<c:if test="${resultList != null }">
				<!-- 행정구역 시도 개수 정하기 -->
				<div><c:out value="${sldYear}"/>년 행정구역별 대상지 건수 조회 </div>
				<div class="sdListWrap" style="margin-bottom: 30px;">
					<c:forEach var="sdCodeListInfo" items="${sdCodeList}" varStatus="status">
						<input type="hidden" name="<c:out value="${sdCodeListInfo.adminCode}"/>_sldInfoCnt" value="<c:out value="${resultList[status.count-1].ctrdCnt }"/>"/>
						<div class="sdListBox">
							<div class="sdNm"><c:out value="${sdCodeListInfo.adminNm }"/></div>
							<div class="sdCnt">
								<c:forEach var="resultListInfo" items="${resultList}" varStatus="status">
									<c:if test="${sdCodeListInfo.adminCode eq resultListInfo.ctrdCd }"> <c:out value="${resultListInfo.ctrdCnt }"/></c:if>
								</c:forEach>			
							</div>
						</div>								
					</c:forEach>
				</div>
				</c:if>
				
				<!-- 조사대상지 연도선택 -->
				<div class="sldYearBox">
					<span>조사연도</span>
					<div class="sldYear">
						<c:if test="${resultList ne null }">
							<input type="text" name="year" id="year" value="${year}" readonly="readonly" />	
						</c:if>
						<c:if test="${resultList eq null }">
							<select name="year" id="year" style="width: 150px;">
								<option value="">--연도 선택--</option>
								<option value="2022">2022</option>
								<option value="2023">2023</option>
								<option value="2024">2024</option>
								<option value="2025">2025</option>
								<option value="2026">2026</option>
							</select>						
						</c:if>
					</div>
					<span>총 건수</span>
					<div class="sdTotalCnt" style="width:50px; height: 50px; line-height:50px;">0 건</div>				
				</div>
				<!-- 행정구역 시도 개수 정하기 -->
				<div class="sdListWrap">
					<c:forEach var="sdCodeListInfo" items="${sdCodeList}" varStatus="status">
						<input type="hidden" name="<c:out value="${sdCodeListInfo.adminCode}"/>_cnt" />
						<div class="sdListBox">
							<div class="sdNm"><c:out value="${sdCodeListInfo.adminNm }"/></div>
							<div class="sdCnt">
								<c:if test="${resultList != null }">
									<button class="cntPlus" onclick="javascript:fncSdCntPlus(<c:out value="${sdCodeListInfo.adminCode}"/>); return false;">+</button>
									<button class="cntMinus" onclick="javascript:fncSdCntMinus(<c:out value="${sdCodeListInfo.adminCode}"/>); return false;">-</button>
								</c:if>
								<input type="number" class="<c:out value="${sdCodeListInfo.adminCode}"/>" name="sdCnt" onchange="fncSdCntChange(event,<c:out value="${sdCodeListInfo.adminCode}"/>); return false;"/>
							</div>
						</div>								
					</c:forEach>
				</div>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="save-btn" onclick="javascript:fncStripLandInsert(); return false;"><spring:message code="title.create" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList();"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<script>
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>
</script>