<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngMnuMpm.menuMvmn.title"/></c:set><!-- 메뉴이동 -->
<div id="contents">
	<fieldset>
		<legend class="Hidden">검색테이블</legend>
		<form:form name="searchUpperMenuIdForm" action ="<c:url value='/sym/mnu/mpm/EgovMenuListSelectTmp.do'/>" method="post">
			<input type="hidden" name="req_RetrunPath" value="/mng/mnu/mpm/menuMvmnPopup">
			<c:forEach var="result" items="${list_menulist}" varStatus="status" >
			<input type="hidden" name="tmp_menuNmVal" value="${result.menuNo}|${result.upperMenuId}|${result.menuNm}|${result.progrmFileNm}|${result.menuNo}|${result.menuOrdr}|${result.menuNm}|${result.upperMenuId}|${result.menuDc}|${result.relateImagePath}|${result.relateImageNm}|${result.progrmFileNm}|">
			</c:forEach>
			<div class="search">
				<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} 영역</caption>
					<colgroup>
						<col style="width:20%" />
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="mngMnuMpm.menuMvmn.menuNo"/></th><!-- 이동할메뉴명 -->
							<td>
								<label for="searchPopupKeyword" class="Hidden">구분</label>
								<input name="progrmFileNm" type="text" size="30" value=""  maxlength="60" title="<spring:message code="mngMnuMpm.menuMvmn.menuNo"/>" readonly="readonly"/>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="searchdiv">
					<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fnSelectProgramListSearch(); return false;">검색</button>
				</div>
			</div>
		</form:form>
	</fieldset>
	<div class="BoardList">
		<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
			<tbody>
				<tr>
					<td>
						<div class="tree" style="width:400px;">
							<script>
				
							var Tree = new Array;
							
							if ( typeof document.searchUpperMenuIdForm.req_RetrunPath == "object"
									&& typeof document.searchUpperMenuIdForm.tmp_menuNmVal == "object"
									&& document.searchUpperMenuIdForm.tmp_menuNmVal.length > 0 ) {
								for (var j = 0; j < document.searchUpperMenuIdForm.tmp_menuNmVal.length; j++) {
									Tree[j] = document.searchUpperMenuIdForm.tmp_menuNmVal[j].value;
							    }
								createTree(Tree, true);
				            }else{
				            	alert("<spring:message code="mngMnuMpm.menuMvmn.validate.alert.menu"/>");
				            	window.close();
				            }
				           </script>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

