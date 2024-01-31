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
<c:set var="pageTitle"><spring:message code="sysFckPcs.fieldBookAuthorManage.title"/></c:set>
<c:set var="subTitle"><spring:message code="sysFckPcs.fieldBookItemList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="/sys/main.do">메인화면</a></li>
		<li><a href="#">사방시설점검</a></li>
		<li><a href="#">정밀점검</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" commandName="resultList"  method="post" enctype="multipart/form-data" action="/sys/fck/pcs/fbk/updateCnrsSpceAuthorMgt.do">		
		<div class="BoardDetail">
			<input name="mstId" type="hidden" value="${searchItemVO.MST_ID}"/>
			<input name="svytype" type="hidden" value="PCS"/>
			<input name="schid" type="hidden" value="<c:out value='${fieldBookVO.id}'/>">
			<input name="schmst_corpname" type="hidden" value="<c:out value='${fieldBookVO.mst_corpname}'/>">
			<input name="schmst_partname" type="hidden" value="<c:out value='${fieldBookVO.mst_partname}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${fieldBookVO.svy_year}'/>">
			<input name="schmst_create_user" type="hidden" value="<c:out value='${fieldBookVO.mst_create_user}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${fieldBookVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${fieldBookVO.pageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${fieldBookVO.pageSubIndex}'/>">
			<input name="pageName" type="hidden" value="authorMng">		
			<table summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 20%;">
					<col style="width: 80%;">
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code="sysFckPcs.fieldBookAuthorManage.creatUser"/></th><!-- 공유방 등록자 -->
						<td>
						<c:forEach var="result" items="${resultList}" varStatus="status">
						<c:if test="${result.user_grade eq 'ADMIN' }">
							<input type="hidden" name="mst_admin_id" value="${result.esntl_id}"/>
							<c:out value="${result.mber_nm}"></c:out>
						</c:if>
						</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="BtnGroup" style="margin-bottom: 10px;">
				<div class="BtnRightArea">
					<button type="button" class="search-btn-m" onclick="javascript:fnSearchAuthorUserPopup(<c:out value="${searchItemVO.MST_ID}"/>)"></button>
				</div>			
			</div>
		</div>
		<div class="BoardList">
<%-- 			<h3>${subTitle}</h3> --%>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 45%;">
					<col style="width: 45%;">
					<col style="width: 10%;">
				</colgroup>
				<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
			<thead>
			<tr>				
				<th><spring:message code="sysFckPcs.fieldBookVO.deptname" /></th><!-- 부서명 -->
				<th><spring:message code="sysFckPcs.fieldBookVO.authory" /></th><!-- 권한자명 -->
				<th></th>
			</tr>
			</thead>
				<tbody class="ov">
					<c:if test="${fn:length(resultList) == 0}">					
						<tr>
							<td colspan="8"><spring:message code="info.nodata.msg" /></td>
						</tr>
					</c:if>
					<c:forEach var="result" items="${resultList}" varStatus="status">						
						<tr>
							<td>
								<input type="hidden" name="authorEsntlId" value="${result.esntl_id}" />
								<c:out value="${result.dept_nm}"/>
							</td>
							<td><c:out value="${result.mber_nm}"/></td>
							<td>
								<c:if test="${result.user_grade ne 'ADMIN' }">
								<button type="button" class="close-btn-m" onclick="javascript:fnDelAuthory(event); return false;"></button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnRightArea">
				<button type="button" class="modify-btn" onclick="javascript:fnUpdateCnrsSpceAuthor();"><spring:message code="title.update" /></button>
				<button type="button" class="list-btn" onclick="javascript:fnList('/sys/fck/pcs/fbk/selectPcsFieldBookList.do'); return false;"><spring:message code="button.list" /></button>
			</div>
		</div>
		</form:form>
	</div>
</div>