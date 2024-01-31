<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="mngMnuMcm.MenuCreat.title"/></c:set><!-- 메뉴생성 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<fieldset>
			<legend class="Hidden">검색테이블</legend>
			<form id="listForm" name="menuCreatManageForm" action="/mng/mnu/mcm/menuCreatInsert.do" method="post">
				<input name="checkedMenuNoForInsert" type="hidden" >
				<input name="checkedAuthorForInsert" type="hidden" >
				<input name="req_menuNo" type="hidden" >
				<div class="search">
					<table  class="tableWid_M" summary="<spring:message code="common.search.summary.list" arguments="${pageTitle}" />">
						<caption class="Hidden">${pageTitle} 검색영역</caption>
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 90%;">
						</colgroup>
						<tbody>
							<tr>
								<th><spring:message code="mngMnuMcm.MenuCreat.authCode" /></th><!-- 권한코드 -->
								<td>
									<label for="authorCode" class="Hidden">구분</label>
									<input type="text" id="authorCode" value="<c:out value='${resultVO.authorCode}'/>" name="authorCode" class="mo_mt5 input_null" maxlength="30" title="<spring:message code="mngMnuMcm.MenuCreat.authCode" />" readonly="readonly"/>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="searchdiv">
						<button type="button" id="searchBtn" class="search-btn btn_click" onclick="javascript:fInsertMenuCreat(); return false;" title="<spring:message code="mngMnuMcm.MenuCreat.createMenu" />"><spring:message code="mngMnuMcm.MenuCreat.createMenu" /></button><!-- 메뉴생성 -->
						<button type="button" class="list-btn" onclick="location.href='/mng/mnu/mcm/menuCreatManageSelect.do'"><spring:message code="button.list" /></button>
					</div>
				</div>
				<c:forEach var="result1" items="${list_menulist}" varStatus="status" >
				<input type="hidden" name="tmp_menuNmVal" value="${result1.menuNo}|${result1.upperMenuId}|${result1.menuNm}|${result1.progrmFileNm}|${result1.chkYeoBu}|">
				</c:forEach>
			</form>
		</fieldset>
		<div class="BoardList">
			<div class="tree">
				<script type="text/javaScript">
				    var chk_Object = true;
		
					var Tree = new Array;

					if( document.menuCreatManageForm.tmp_menuNmVal != null || document.menuCreatManageForm.tmp_menuNmVal != undefined ){
						for (var j = 0; j < document.menuCreatManageForm.tmp_menuNmVal.length; j++) {
							Tree[j] = document.menuCreatManageForm.tmp_menuNmVal[j].value;
					    }
					    createTree(Tree);
		            }else{
		                alert("<spring:message code="mngMnuMcm.MenuCreat.validate.menuNmVal.none2" />"); //메뉴가 존재하지 않습니다. 메뉴 등록 후 사용하세요.
		                //window.close();
		            }
				</script>
			</div>
		</div>
	</div>
</div>

<!-- <style type="text/css">
.tree {margin-bottom:30px; padding:10px; border-top:2px solid #1a90d8; border-bottom:2px solid #1a90d8; background:#f7f7f7; }
.tree input[type=checkbox] {margin-right:2px; vertical-align:-2px; }
.tree img {vertical-align:-4px; }
</style> -->
<!--<script language="javascript1.2" type="text/javaScript">

/* ********************************************************
 * 조회 함수
 ******************************************************** */
function selectMenuCreatTmp() {
    document.menuCreatManageForm.action = "<c:url value='/sym/mnu/mcm/EgovMenuCreatSelect.do'/>";
    document.menuCreatManageForm.submit();
}

/* ********************************************************
 * 멀티입력 처리 함수
 ******************************************************** */
function fInsertMenuCreat() {
    var checkField = document.menuCreatManageForm.checkField;
    var checkMenuNos = "";
    var checkedCount = 0;
    if(checkField) {
    	if(checkField.length > 1) {
            for(var i=0; i < checkField.length; i++) {
                if(checkField[i].checked) {
                    checkMenuNos += ((checkedCount==0? "" : ",") + checkField[i].value);
                    checkedCount++;
                    console.log("checkMenuNos = "+checkMenuNos);
                    console.log("checkedCount = "+checkedCount);
                }
            }
        } else {
            if(checkField.checked) {
                checkMenuNos = checkField.value;
            }
        }
    }
    if(checkedCount == 0){
        alert("선택된 메뉴가 없습니다.");
        return false;
    }
    document.menuCreatManageForm.checkedMenuNoForInsert.value=checkMenuNos;
    document.menuCreatManageForm.checkedAuthorForInsert.value=document.menuCreatManageForm.authorCode.value;
    document.menuCreatManageForm.action = "<c:url value='/mng/mnu/mcm/EgovMenuCreatInsert.do'/>";
    document.menuCreatManageForm.submit();
}
/* ********************************************************
 * 메뉴사이트맵 생성 화면 호출
 ******************************************************** */
function fMenuCreatSiteMap() {
	id = document.menuCreatManageForm.authorCode.value;
	window.open("<c:url value='/sym/mnu/mcm/EgovMenuCreatSiteMapSelect.do'/>?authorCode="+id,'Pop_SiteMap','scrollbars=yes, width=550, height=700');
}
<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>

</script>-->