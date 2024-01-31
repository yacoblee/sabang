<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysBbs.articleVO.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">알림마당</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail" /></h3><!-- 권한관리 상세조회 -->
	<div id="contents">	
		<form id="listForm" action="${pageContext.request.contextPath}/sys/bbs/art/selectArticleDetail.do" method="post">
			<input name="subPageIndex" type="hidden" value="<c:out value='${articleCommentVO.subPageIndex}'/>">
			<input type="hidden" name="bbsId" value="<c:out value="${result.bbsId}"/>"/>
			<input type="hidden" name="nttId" value="<c:out value="${result.nttId}"/>"/>
			<input name="schsearchCondition" type="hidden" value="<c:out value='${articleVO.searchCondition}'/>"/>
			<input name="schsearchKeyword" type="hidden" value="<c:out value='${articleVO.searchKeyword}'/>"/>
			<input name="schpageIndex" type="hidden" value="<c:out value='${articleVO.pageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${articleVO.pageUnit}'/>">
			<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
				<tbody>
					<!-- 글 제목  -->
					<c:set var="title"><spring:message code="sysBbs.articleVO.detail.nttSj" /></c:set>
					<tr>
						<th>${title}</th>
						<td colspan="5" class="left"><c:out value="${result.nttSj}"/></td>
					</tr>
					<!-- 작성자, 작성시간, 조회수 -->
					<tr>
						<th><spring:message code="table.reger"/></th>
						<td class="left"><c:out value="${result.frstRegisterId}"/></td>
						<th><spring:message code="table.regdate"/></th>
						<td class="left"><c:out value="${result.frstRegisterPnttm}"/></td>
						<th><spring:message code="sysBbs.articleVO.detail.rdcnt"/></th>
						<td class="left"><c:out value="${result.rdcnt}"/></td>
					</tr>
					<!-- 글 내용 -->
					<c:set var="title"><spring:message code="sysBbs.articleVO.detail.nttCn" /></c:set>
					<tr>
						<th class="vtop">${title}</th>
						<td colspan="5" class="cnt"><textarea style="resize:none; min-height: 300px; width: 100%; border: 1px solid #ddd;" cols="300" rows="20" readonly="readonly" disabled="disabled"><c:out value="${result.nttCn}" escapeXml="false"/></textarea></td>
					</tr>
					<!-- 첨부파일  -->
					<c:if test="${not empty result.atchFileId}">
					<tr>
						<th><spring:message code="sysBbs.articleVO.detail.atchFile" /></th>
						<td colspan="5">
							<c:import url="/sys/bbs/art/selectFileInfs.do" charEncoding="utf-8">
								<c:param name="param_atchFileId" value="${result.atchFileId}" />
							</c:import>
						</td>
					</tr>
				  	</c:if>
				</tbody>	
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<sec:authorize access="isAuthenticated()">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<c:if test="${result.frstRegisterId ne userId}">
								<button type="button" class="del-btn" onclick="javascript:fncArticleDelete('<c:out value="${result.nttId}"/>')"><spring:message code="title.delete" /></button>
								<button type="button" class="modify-btn" onclick="javascript:fncUpdateArticleView('<c:out value="${result.nttId}"/>')"><spring:message code="title.update" /></button>
							</c:if> 
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_USER')">
							<c:if test="${result.frstRegisterId eq userId}">
								<button type="button" class="del-btn" onclick="javascript:fncArticleDelete('<c:out value="${result.nttId}"/>')"><spring:message code="title.delete" /></button>
								<button type="button" class="modify-btn" onclick="javascript:fncUpdateArticleView('<c:out value="${result.nttId}"/>')"><spring:message code="title.update" /></button>
							</c:if> 
						</sec:authorize>
					</sec:authorize>
					<button type="button" class="list-btn" onclick="javascript:fnList('article'); return false;"><spring:message code="button.list" /></button>
				</div>
			</div>
		</div>
		</form>
		<!-- 댓글 작성 폼 -->
		<form:form commandName="articleCommentVO" action="${pageContext.request.contextPath}/sys/bbs/art/insertArticleComment.do" method="post">
			<input type="hidden" name="bbsId" value="<c:out value="${result.bbsId}"/>"/>
			<input type="hidden" name="nttId" value="<c:out value="${result.nttId}"/>"/>
			<input type="hidden" name="commentNo" />				
			<div class="comment_box" style="margin-top: 30px;">			
				<form:textarea path="commentCn" name="commentCn" class="commentarea" style="resize:none; min-height: 60px; width: 90%; border: 1px solid #ddd;"/>
				<button class="insert" onclick="javascript:fncArticleCommentInsert()" type="button" style="width: 10%; float: right; height:60px; background: #124428; color: snow;">등록</button>
			</div>
		</form:form>
		<!-- 댓글 리스트 -->
		<c:set var="replyTitle">댓글</c:set>
		<h3>${replyTitle} <c:out value="${cmtCnt}"/></h3>
		<div class="reply">	
		<ul class="comment-list">
			<c:if test="${fn:length(cmtList) == 0}">
			<li>
		  		<p class="txt"><spring:message code="common.nocomment.msg" /></p>
	  		</li>
			</c:if>
			<c:forEach var="cmtList" items="${cmtList}" varStatus="status">
				<li>

					<div class="top">
						<strong><c:out value="${cmtList.wrterNm}" /></strong>
						<span class="bar">|</span>
						<span class="date"><c:out value="${cmtList.frstRegisterPnttm}" /></span>
					</div>
					<p class="txt">
						<input class="comment_${cmtList.commentNo}" placeholder="${cmtList.commentCn}" style="display:none; position: absolute; width: 100%;"/>
						<c:out value="${fn:replace(cmtList.commentCn , crlf , '<br/>')}" escapeXml="false" />
					</p>
					<div class="bottom btn_${cmtList.commentNo}">
						<sec:authorize access="isAuthenticated()">
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<c:if test="${cmtList.wrterId ne sessionUniqId}">
									<span class="btn_u"><a href="javascript:fncUpdateArticleCommentView(${cmtList.commentNo})"  title="<spring:message code="button.update" /> <spring:message code="input.button" />"><spring:message code="button.update" /> </a></span>
									<span class="btn_d"><a href="javascript:fncArticleCommentDelete(${cmtList.commentNo})"  title="<spring:message code="button.delete" /> <spring:message code="input.button" />"><spring:message code="button.delete" /></a></span>
								</c:if>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_USER')">
								<c:if test="${cmtList.wrterId eq sessionUniqId}">
									<span class="btn_u"><a href="javascript:fncUpdateArticleCommentView(${cmtList.commentNo})"  title="<spring:message code="button.update" /> <spring:message code="input.button" />"><spring:message code="button.update" /> </a></span>
									<span class="btn_d"><a href="javascript:fncArticleCommentDelete(${cmtList.commentNo})"  title="<spring:message code="button.delete" /> <spring:message code="input.button" />"><spring:message code="button.delete" /></a></span>
								</c:if>
							</sec:authorize>
						</sec:authorize>
					</div>
				</li>
			</c:forEach>
		</ul>		
		</div>
		<!-- paging navigation -->
		<div class="Page">
			<c:if test="${not empty paginationInfo}">
				<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPage"/>
			</c:if>
		</div> 
	</div>
	
	<script>
		function linkPage(pageNo){
			$("input[name=subPageIndex]").val(pageNo);
			$("#listForm").submit();
		}
	</script>
</div> 