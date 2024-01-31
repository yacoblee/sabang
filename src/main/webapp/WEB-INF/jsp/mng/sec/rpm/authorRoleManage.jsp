<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="comCopSecRam.authorRoleList.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">권한관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" action="${pageContext.request.contextPath}/mng/sec/rpm/authorRoleList.do" method="post">
				<input type="hidden" name="roleCode"/>
				<input type="hidden" name="authorCode"/>
				<input type="hidden" name="pageIndex" value="<c:out value='${authorRoleManageVO.pageIndex}'/>"/>
				<input type="hidden" name="searchCondition" value="<c:out value='${authorRoleManageVO.searchCondition}'/>">
				<input name="searchKeyword" type="hidden" value="<c:out value='${authorRoleManageVO.searchKeyword}'/>"/>
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
										<option value="0" <c:if test="${empty authorRoleManageVO.searchCondition || authorRoleManageVO.searchCondition == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
										<option value="1" <c:if test="${authorRoleManageVO.searchCondition == '1'}">selected="selected"</c:if>>권한코드</option>
										<option value="2" <c:if test="${authorRoleManageVO.searchCondition == '2'}">selected="selected"</c:if>>권한명</option>
										<option value="3" <c:if test="${authorRoleManageVO.searchCondition == '3'}">selected="selected"</c:if>>롤ID</option>
										<option value="4" <c:if test="${authorRoleManageVO.searchCondition == '4'}">selected="selected"</c:if>>롤명</option>
									</select>
									<label for="searchKeyword" class="Hidden">구분</label>
									<input type="text" id="searchKeyword" value="<c:out value='${authorRoleManageVO.searchKeyword}'/>" name="searchKeywordView" class="mo_mt5 input_null" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
<%-- 						<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" /> --%>
<%-- 						<input type="button" class="s_btn" value="<spring:message code="button.list" />" onClick="fncSelectAuthorList()" title="<spring:message code="button.list" /> <spring:message code="input.button" />" /> --%>
<%-- 						<input type="button" class="s_btn" value="<spring:message code="button.create" />" onClick="fncAddAuthorRoleInsert()" title="<spring:message code="button.create" /> <spring:message code="input.button" />" /> --%>
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
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 20%;">
					<col style="width: 10%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
<%-- 						<th><input type="checkbox" name="checkAll" class="check2" onclick="javascript:fncCheckAll()" title="<spring:message code="input.selectAll.title" />"></th> --%>
						<th class="board_th_link"><spring:message code="comCopSecRam.authorRoleList.authorId" /></th><!-- 권한 ID -->
						<th><spring:message code="comCopSecRam.authorRoleList.authorNm" /></th><!-- 권한 명 -->
						<th><spring:message code="comCopSecRam.authorRoleList.rollId" /></th><!-- 롤 ID -->
						<th><spring:message code="comCopSecRam.authorRoleList.rollNm" /></th><!-- 롤 명 -->
						<th><spring:message code="table.regdate" /></th><!--등록일 -->
						<th><spring:message code="comCopSecRam.authorRoleList.manage" /></th><!-- 관리 -->
					</tr>
				</thead>
				<tbody class="ov">
					<c:if test="${fn:length(authorRoleList) == 0}">
					<tr>
						<td colspan="7"><spring:message code="common.nodata.msg" /></td>
					</tr>
					</c:if>
					<c:forEach var="authorRole" items="${authorRoleList}" varStatus="status">
					<tr>
<%-- 						<td><input type="checkbox" name="delYn" class="check2" title="선택"><input type="hidden" name="checkId" value="<c:out value="${authorRole.roleCode}"/>" /></td> --%>
						<td><c:out value="${authorRole.authorCode}"/></td>
						<td><c:out value="${authorRole.authorNm}"/></td>
						<td><c:out value="${authorRole.roleCode}"/></td>
						<td><c:out value="${authorRole.roleNm}"/></td>
						<td><c:out value="${fn:substring(authorRole.creatDt,0,10)}"/></td>
						<td>
							<div class="BtnGroup">
								<button type="button" class="modify-btn" onclick="javascript:fncDeleteAuthorRole('${authorRole.authorCode}','${authorRole.roleCode }');">삭제</button>
							</div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
<%-- 			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption class="Hidden">${pageTitle} <spring:message code="title.list" /></caption>
				<colgroup>
					<col style="width: 3%;">
					<col style="width: 12%;">
					<col style="width: 20%;">
					<col style="width: 12%;">
					<col style="width: 10%;">
					<col style="width: ;">
					<col style="width: 10%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" name="checkAll" class="check2" onclick="javascript:fncCheckAll()" title="<spring:message code="input.selectAll.title" />"></th>
						<th class="board_th_link"><spring:message code="mngSecRam.authorRoleList.rollId" /></th><!-- 롤 ID -->
						<th><spring:message code="mngSecRam.authorRoleList.rollNm" /></th><!-- 롤 명 -->
						<th><spring:message code="mngSecRam.authorRoleList.rollType" /></th><!-- 롤 타입 -->
						<th><spring:message code="mngSecRam.authorRoleList.rollSort" /></th><!-- 롤 Sort -->
						<th><spring:message code="mngSecRam.authorRoleList.rollDc" /></th><!-- 롤 설명 -->
						<th><spring:message code="table.regdate" /></th><!--등록일 -->
						<th><spring:message code="mngSecRam.authorRoleList.regYn" /></th><!-- 등록여부 -->
					</tr>
				</thead>
				<tbody class="ov">
					<c:if test="${fn:length(authorRoleList) == 0}">
					<tr>
						<td colspan="7"><spring:message code="common.nodata.msg" /></td>
					</tr>
					</c:if>
					<c:forEach var="authorRole" items="${authorRoleList}" varStatus="status">
					<tr>
						<td><input type="checkbox" name="delYn" class="check2" title="선택"><input type="hidden" name="checkId" value="<c:out value="${authorRole.roleCode}"/>" /></td>
						<td><c:out value="${authorRole.roleCode}"/></td>
						<td><c:out value="${authorRole.roleNm}"/></td>
						<td><c:out value="${authorRole.roleTyp}"/></td>
						<td><c:out value="${authorRole.roleSort}"/></td>
						<td><c:out value="${authorRole.roleDc}"/></td>
						<td><c:out value="${fn:substring(authorRole.creatDt,0,10)}"/></td>
						<td>
							<select name="regYn" title="<spring:message code="mngSecRam.authorRoleList.regYn" />">
							<option value="Y" <c:if test="${authorRole.regYn == 'Y'}">selected</c:if> ><spring:message code="mngSecRam.authorRoleList.regY" /></option><!-- 등록 -->
							<option value="N" <c:if test="${authorRole.regYn == 'N'}">selected</c:if> ><spring:message code="mngSecRam.authorRoleList.regN" /></option><!-- 미등록 -->
							</select>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table> --%>
			<sec:authorize access="isAuthenticated()">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<div class="BtnGroup">
						<div class="BtnRightArea">
							<button type="button" class="write-btn" onclick="javascript:fncAddAuthorRoleInsert();"><spring:message code="button.create" /></button>
						</div>
					</div>
				</sec:authorize>
			</sec:authorize>
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
<!-- <script>
function fncCheckAll() {
    var checkField = document.listForm.delYn;
    if(document.listForm.checkAll.checked) {
        if(checkField) {
            if(checkField.length > 1) {
                for(var i=0; i < checkField.length; i++) {
                    checkField[i].checked = true;
                }
            } else {
                checkField.checked = true;
            }
        }
    } else {
        if(checkField) {
            if(checkField.length > 1) {
                for(var j=0; j < checkField.length; j++) {
                    checkField[j].checked = false;
                }
            } else {
                checkField.checked = false;
            }
        }
    }
}

function fncManageChecked() {

    var checkField = document.listForm.delYn;
    var checkId = document.listForm.checkId;
    var checkRegYn = document.listForm.regYn;
    var returnValue = "";
    var returnRegYns = "";
    var checkedCount = 0;
    var returnBoolean = false;

    if(checkField) {
        if(checkField.length > 1) {
            for(var i=0; i<checkField.length; i++) {
                if(checkField[i].checked) {
                	checkedCount++;
                    checkField[i].value = checkId[i].value;

	                if(returnValue == "") {
	                    returnValue = checkField[i].value;
	                    returnRegYns = checkRegYn[i].value;
	                }
	                else {
	                    returnValue = returnValue + ";" + checkField[i].value;
	                    returnRegYns = returnRegYns + ";" + checkRegYn[i].value;
	                }
                }
            }

            if(checkedCount > 0)
            	returnBoolean = true;
            else {
                alert("<spring:message code="mngSecRam.authorRoleList.validate.alert.noSelect" />");//선택된  롤이 없습니다.
                returnBoolean = false;
            }
        } else {
        	 if(document.listForm.delYn.checked == false) {
                alert("<spring:message code="mngSecRam.authorRoleList.validate.alert.noSelect" />");//선택된 롤이 없습니다.
            	returnBoolean = false;
            }
            else {
            	returnValue = checkId.value;
                returnRegYns = checkRegYn.value;

                returnBoolean = true;
            }
        }
    } else {
        alert("<spring:message code="mngSecRam.authorRoleList.validate.alert.noResult" />");//조회된 결과가 없습니다.
    }

    document.listForm.roleCodes.value = returnValue;
    document.listForm.regYns.value = returnRegYns;

    return returnBoolean;

}

function fncSelectAuthorRoleList() {
    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = "1";
    document.listForm.action = "<c:url value='/sec/ram/EgovAuthorRoleList.do'/>";
    document.listForm.submit();
}

function fncSelectAuthorList(){
    // document.listForm.searchCondition.value = "1";
    // document.listForm.pageIndex.value = "1";
    
    //document.listForm.searchKeyword.value = "";
    //document.listForm.action = "<c:url value='/sec/ram/EgovAuthorList.do'/>";
    //document.listForm.submit();
    location.href = "<c:url value='/sec/ram/EgovAuthorList.do'/>";
}

function fncSelectAuthorRole(roleCode) {
    document.listForm.roleCode.value = roleCode;
    document.listForm.action = "<c:url value='/sec/ram/EgovRole.do'/>";
    document.listForm.submit();
}

function fncAddAuthorRoleInsert() {
	if(fncManageChecked()) {
	    if(confirm("<spring:message code="mngSecRam.authorRoleList.validate.confirm.regist" />")) {//등록하시겠습니까?
            document.listForm.action = "<c:url value='/sec/ram/EgovAuthorRoleInsert.do'/>";
            document.listForm.submit();
	    }
	} else return;
}

function linkPage(pageNo){
    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/sec/ram/EgovAuthorRoleList.do'/>";
    document.listForm.submit();
}


function press() {

    if (event.keyCode==13) {
    	fncSelectAuthorRoleList();
    }
}
</script> -->