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
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="sysFckApr.fieldBookUpdtOne.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckApr.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">외관점검</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" commandName="result" action="/sys/fck/apr/fbk/updateFieldBookDetailOne.do" method="post">
		<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
			<input name="smid" type="hidden" value="<c:out value='${searchVO.smid}'/>">		
			<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
			<input name="mstId" type="hidden" value="<c:out value='${mstId}'/>">
			<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
			<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
			<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
			
			<input name="schsvyid" type="hidden" value="<c:out value='${searchVO.svyid}'/>"/>
			<input name="schsd" type="hidden" value="<c:out value='${searchVO.sd}'/>"/>
			<input name="schsgg" type="hidden" value="<c:out value='${searchVO.sgg}'/>"/>
			<input name="schemd" type="hidden" value="<c:out value='${searchVO.emd}'/>"/>
			<input name="schri" type="hidden" value="<c:out value='${searchVO.ri}'/>"/>
			<div class="BoardDetail">
				<jsp:include page="fieldBookUpdtOne1.jsp"/>
				<jsp:include page="fieldBookUpdtOne2.jsp"/>
				<jsp:include page="fieldBookUpdtOne3.jsp"/>
				<div class="BtnGroup">
					<div class="BtnRightArea">
<%-- 						<button type="button" class="del-btn" onclick="javascript:fnDeleteFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="title.delete" /></button> --%>
						<button type="button" class="modify-btn" onclick="javascript:fnUpdateFieldBookDetailOne('<c:out value="${result.smid}"/>');"><spring:message code="button.update" /></button>
						<button type="button" class="list-btn"  onclick="javascript:fnSelectFieldBookDetailOne('<c:out value="${result.smid}"/>'); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
