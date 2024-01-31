<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngSecRmt.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="${pageContext.request.contextPath}/mng/sec/rmt/roleList.do" method="post">
				<input type="hidden" name="roleCode"/>
				<input type="hidden" name="roleCodes"/>
				<input type="hidden" name="pageIndex" value="<c:out value='${roleManageVO.pageIndex}'/>"/>
				<input type="hidden" name="searchCondition" value="<c:out value='${roleManageVO.searchCondition}'/>"/>
				<input type="hidden" name="searchKeyword" value="<c:out value='${roleManageVO.searchKeyword}'/>"/>
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th>구분</th>
								<td>
									<label for="searchCondition" class="Hidden">구분</label>
									<select id="searchCondition" name="searchConditionView">
										<option value="0" <c:if test="${empty roleManageVO.searchCondition || roleManageVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${roleManageVO.searchCondition == '1'}">selected="selected"</c:if>>롤ID</option>
										<option value="2" <c:if test="${roleManageVO.searchCondition == '2'}">selected="selected"</c:if>>롤명</option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${roleManageVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
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
		
		<div class="BoardList">
			<span class="ListCount">Total : <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong> Page : <strong><c:out value="${paginationInfo.currentPageNo}"/>/<c:out value="${paginationInfo.totalPageCount}"/></strong></span>
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
<%-- 					<col style="width: 3%;"> --%>
					<col style="width: 12%;">
					<col style="width: 25%;">
					<col style="width: 7%;">
					<col style="width: 7%;">
					<col style="width: ;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
<%-- 						<th><input type="checkbox" name="checkAll" class="check2" onclick="javascript:fncCheckAll()" title="<spring:message code="input.selectAll.title" />"></th><!-- 번호 --> --%>
						<th class="board_th_link"><spring:message code="mngSecRam.list.rollId" /></th><!-- 롤 ID -->
						<th><spring:message code="mngSecRam.list.rollNm" /></th><!-- 롤 명 -->
						<th><spring:message code="mngSecRam.list.rollType" /></th><!-- 롤 타입 -->
						<th><spring:message code="mngSecRam.list.rollSort" /></th><!-- 롤 Sort -->
						<th><spring:message code="mngSecRam.list.rollDc" /></th><!-- 롤 설명 -->
						<th><spring:message code="table.regdate" /></th><!-- 등록일자 -->
				  	</tr>
				</thead>
				<tbody class="ov">
				<c:if test="${fn:length(roleList) == 0}">
					<tr>
						<td colspan="8"><spring:message code="common.nodata.msg" /></td>
					</tr>
					</c:if>
					<c:forEach var="role" items="${roleList}" varStatus="status">
					<tr>
<%-- 						<td><input type="checkbox" name="delYn" class="check2" title="선택"><input type="hidden" name="checkId" value="<c:out value="${role.roleCode}"/>" /></td> --%>
						<td><a href="<c:url value='/mng/sec/rmt/role.do'/>?roleCode=${role.roleCode}" onclick="javascript:fncSelectRole('<c:out value="${role.roleCode}"/>');return false;"><c:out value="${role.roleCode}"/></a></td>
						<td class="left"><c:out value="${role.roleNm}"/></td>
						<td><c:out value="${role.roleTyp}"/></td>
						<td><c:out value="${role.roleSort}"/></td>
						<td class="left"><c:out value="${role.roleDc}"/></td>
						<td><c:out value="${fn:substring(role.roleCreatDe,0,10)}"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="write-btn" onclick="javascript:fncAddRoleInsert();"><spring:message code="button.create" /></button>
				</div>
			</div>
			<div class="Page">
				<c:if test="${not empty paginationInfo}">
				<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPage"/>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script>
<c:if test="${!empty message}">alert("<c:out value='${message}'/>");</c:if>

function linkPage(pageNo){
	$("input[name=pageIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>
<script>
// function fncCheckAll() {
//     var checkField = document.listForm.delYn;
//     if(document.listForm.checkAll.checked) {
//         if(checkField) {
//             if(checkField.length > 1) {
//                 for(var i=0; i < checkField.length; i++) {
//                     checkField[i].checked = true;
//                 }
//             } else {
//                 checkField.checked = true;
//             }
//         }
//     } else {
//         if(checkField) {
//             if(checkField.length > 1) {
//                 for(var j=0; j < checkField.length; j++) {
//                     checkField[j].checked = false;
//                 }
//             } else {
//                 checkField.checked = false;
//             }
//         }
//     }
// }

// function fncManageChecked() {

//     var checkField = document.listForm.delYn;
//     var checkId = document.listForm.checkId;
//     var returnValue = "";
//     var returnBoolean = false;
//     var checkCount = 0;

//     if(checkField) {
//         if(checkField.length > 1) {
//             for(var i=0; i<checkField.length; i++) {
//                 if(checkField[i].checked) {
//                 	checkCount++;
//                     checkField[i].value = checkId[i].value;
//                     if(returnValue == "")
//                         returnValue = checkField[i].value;
//                     else
//                         returnValue = returnValue + ";" + checkField[i].value;
//                 }
//             }
//             if(checkCount > 0)
//                 returnBoolean = true;
//             else {
//                 alert("<spring:message code="mngSecRmt.validate.groupSelect"/>"); //선택된  롤이 없습니다.
//                 returnBoolean = false;
//             }
//         } else {
//             if(document.listForm.delYn.checked == false) {
//                 alert("<spring:message code="mngSecRmt.validate.groupSelect"/>"); //선택된  롤이 없습니다.
//                 returnBoolean = false;
//             }
//             else {
//                 returnValue = checkId.value;
//                 returnBoolean = true;
//             }
//         }
//     } else {
//     	alert("<spring:message code="mngSecRmt.validate.groupSelectResult"/>"); //조회된 결과가 없습니다.
//     }

//     document.listForm.roleCodes.value = returnValue;
//     return returnBoolean;
// }

// function fncSelectRoleList(pageNo){
//     document.listForm.searchCondition.value = "1";
//     document.listForm.pageIndex.value = pageNo;
//     document.listForm.action = "<c:url value='/sec/rmt/EgovRoleList.do'/>";
//     document.listForm.submit();
// }

// function fncSelectRole(roleCode) {
//     document.listForm.roleCode.value = roleCode;
//     document.listForm.action = "<c:url value='/sec/rmt/EgovRole.do'/>";
//     document.listForm.submit();
// }

// function fncAddRoleInsert() {
//     location.href = "<c:url value='/sec/rmt/EgovRoleInsertView.do'/>";
// }

// function fncRoleListDelete() {
// 	if(fncManageChecked()) {
//         if(confirm("삭제하시겠습니까?")) { //삭제하시겠습니까?
//             document.listForm.action = "<c:url value='/sec/rmt/EgovRoleListDelete.do'/>";
//             document.listForm.submit();
//         }
//     }
// }

// function fncAddRoleView() {
//     document.listForm.action = "<c:url value='/sec/rmt/EgovRoleUpdate.do'/>";
//     document.listForm.submit();
// }

// function linkPage(pageNo){
//     document.listForm.searchCondition.value = "1";
//     document.listForm.pageIndex.value = pageNo;
//     document.listForm.action = "<c:url value='/sec/rmt/EgovRoleList.do'/>";
//     document.listForm.submit();
// }

// function press() {

//     if (event.keyCode==13) {
//     	fncSelectRoleList('1');
//     }
// }
</script>