<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="mngMnuMpm.menuDetailUpdt.title"/></c:set><!-- 메뉴수정 -->
<validator:javascript formName="menuManageVO" staticJavascript="false" xhtml="true" cdata="false"/>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" commandName="menuManageVO" name="menuManageVO" action="${pageContext.request.contextPath}/mng/mnu/mpm/menuManageListUpdate.do" method="post">
			<input type="hidden" name="tmp_SearchElementName" value=""/>
			<input type="hidden" name="tmp_SearchElementVal" value=""/>
			<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
			<input name="searchKeyword" type="hidden" value="<c:out value='${searchVO.searchKeyword}'/>"/>
			<input name="cmd"    type="hidden"   value="update"/>
			<div class="BoardDetail">
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col style="width:16%" />
						<col style="" />
						<col style="width:16%" />
						<col style="" />
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.menuNo"/> <span class="pilsu">*</span></th><!-- 메뉴No -->
							<td class="left">
							    <c:out value="${menuManageVO.menuNo}"/>
								<form:hidden path="menuNo" />
								<form:errors path="menuNo" />
							</td>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.menuOrder"/> <span class="pilsu">*</span></th><!-- 메뉴순서 -->
							<td class="left">
							    <form:input path="menuOrdr" maxlength="10" title="<spring:message code='mngMnuMpm.menuDetailUpdt.menuOrder'/>" cssStyle="width:50px"/><!-- 메뉴순서 -->
				      			<form:errors path="menuOrdr" />
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.menuNm"/> <span class="pilsu">*</span></th><!-- 메뉴명 -->
							<td class="left">
							    <form:input path="menuNm" size="30" maxlength="30" title="<spring:message code='mngMnuMpm.menuDetailUpdt.menuNm'/>"/><!-- 메뉴명 -->
				      			<form:errors path="menuNm" />
							</td>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.upperMenuId"/> <span class="pilsu">*</span></th><!-- 상위메뉴No -->
							<td class="left">
							    <form:input path="upperMenuId" maxlength="10" title="<spring:message code='mngMnuMpm.menuDetailUpdt.upperMenuId'/>" cssStyle="width:50px"/><!-- 상위메뉴No -->
								<form:errors path="upperMenuId" />
								<button type="button" class="search-btn" onclick="javascript:fncPopupUpperMenuId(); return false;"><spring:message code="mngMnuMpm.menuRegist.selectMenuSearch"/></button>
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.progrmFileNm"/> <span class="pilsu">*</span></th><!-- 파일명 -->
							<td class="left" colspan="3">
							    <form:input path="progrmFileNm" size="60" maxlength="60" title="<spring:message code='mngMnuMpm.menuDetailUpdt.progrmFileNm'/>" cssStyle="width:350px"/><!-- 파일명 -->
							    <form:errors path="progrmFileNm" />
							    <button type="button" class="search-btn" onclick="javascript:fncPopupProgrmFile(); return false;"><spring:message code="mngMnuMpm.menuRegist.programFileNameSearch"/></button>
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.relateImageNm"/> <span class="pilsu">*</span></th><!-- 관련이미지명 -->
							<td class="left">
							    <form:input path="relateImageNm" size="30" maxlength="30" title="<spring:message code='mngMnuMpm.menuDetailUpdt.relateImageNm'/>"/><!-- 관련이미지명 -->
				      			<form:errors path="relateImageNm" />
							</td>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.relateImagePath"/> <span class="pilsu">*</span></th><!-- 관련이미지경로 -->
							<td class="left">
							    <form:input path="relateImagePath" size="30" maxlength="30" title="<spring:message code='mngMnuMpm.menuDetailUpdt.relateImagePath'/>"/><!-- 관련이미지경로 -->
				      			<form:errors path="relateImagePath" />
							</td>
						</tr>
						<tr>
							<th><spring:message code="mngMnuMpm.menuDetailUpdt.menuDc"/> <span class="pilsu">*</span></th><!-- 메뉴설명 -->
							<td class="left" colspan="3">
							    <form:textarea path="menuDc" rows="14" cols="75" title="<spring:message code='mngMnuMpm.menuDetailUpdt.menuDc'/>" cssStyle="height:200px"/><!-- 메뉴설명 -->
				      			<form:errors path="menuDc"/>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<button type="button" class="modify-btn" onclick="javascript:fncUpdateMenuManage(); return false;"><spring:message code="title.update" /></button>
						<button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>


<!--
<script type="text/javascript">
    $(document).ready(function () {
    	// 파일검색 화면 호출 함수
        $('#popupProgrmFileNm').click(function (e) {
        	e.preventDefault();
            //var page = $(this).attr("href");
            var pagetitle = $(this).attr("title");
            var page = "<c:url value='/sym/prm/EgovProgramListSearchNew.do'/>";
            var $dialog = $('<div></div>')
            .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
            .dialog({
            	autoOpen: false,
                modal: true,
                width: 550,
                height: 650,
                title: pagetitle
        	});
        	$dialog.dialog('open');
    	});
        // 메뉴이동 화면 호출 함수
        $('#popupUpperMenuId').click(function (e) {
        	e.preventDefault();
            //var page = $(this).attr("href");
            var pagetitle = $(this).attr("title");
            var page = "<c:url value='/sym/mnu/mpm/EgovMenuListSelectMvmnNew.do'/>";
            var $dialog = $('<div style="overflow:hidden;padding: 0px 0px 0px 0px;"></div>')
            .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
            .dialog({
            	autoOpen: false,
                modal: true,
                width: 610,
                height: 550,
                title: pagetitle
        	});
        	$dialog.dialog('open');
    	});
	});
</script>

/* ********************************************************
* 입력값 validator 함수
******************************************************** */
function fn_validatorMenuList() {

	if(document.menuManageVO.menuNo.value == ""){alert("<spring:message code="mngMnuMpm.menuList.validate.menuNo.notNull" />"); return false;} //메뉴번호는 필수 항목입니다.
	if(!checkNumber(document.menuManageVO.menuNo.value)){alert("<spring:message code="mngMnuMpm.menuList.validate.menuNo.onlyNumber" />"); return false;} //메뉴번호는 숫자만 입력 가능합니다.

	if(document.menuManageVO.menuOrdr.value == ""){alert("<spring:message code="mngMnuMpm.menuList.validate.menuOrdr.notNull" />"); return false;} // 메뉴순서는 필수 항목입니다.
	if(!checkNumber(document.menuManageVO.menuOrdr.value)){alert("<spring:message code="mngMnuMpm.menuList.validate.menuOrdr.onlyNumber" />"); return false;} //메뉴순서는 숫자만 입력 가능합니다.

	if(document.menuManageVO.upperMenuId.value == ""){alert("<spring:message code="mngMnuMpm.menuList.validate.upperMenuId.notNull" />"); return false;} //상위메뉴번호는 필수 항목입니다.
	if(!checkNumber(document.menuManageVO.upperMenuId.value)){alert("<spring:message code="mngMnuMpm.menuList.validate.upperMenuId.onlyNumber" />"); return false;} //상위메뉴번호는 숫자만 입력 가능합니다.

	if(document.menuManageVO.progrmFileNm.value == ""){alert("<spring:message code="mngMnuMpm.menuList.validate.progrmFileNm.notNull" />"); return false;} //프로그램파일명은 필수 항목입니다.
	if(document.menuManageVO.menuNm.value == ""){alert("<spring:message code="mngMnuMpm.menuList.validate.menuNm.notNull" />"); return false;} //메뉴명은 필수 항목입니다.

   return true;
}


/* ********************************************************
* 필드값 Number 체크 함수
******************************************************** */
function checkNumber(str) {
   var flag=true;
   if (str.length > 0) {
       for (i = 0; i < str.length; i++) {
           if (str.charAt(i) < '0' || str.charAt(i) > '9') {
               flag=false;
           }
       }
   }
   return flag;
}

/* ********************************************************
 * 수정처리 함수
 ******************************************************** */
function updateMenuManage(form) {
	if(!fn_validatorMenuList()){return;}
	if(confirm("<spring:message code="common.save.msg"/>")){

		if(!validateMenuManageVO(form)){
			return;
		}else{
            form.action="<c:url value='/sym/mnu/mpm/EgovmenuDetailUpdt.do' />";
			form.submit();
		}
	}
}

/* ********************************************************
 * 삭제처리함수
 ******************************************************** */
function deleteMenuManage(form) {
	if(confirm("<spring:message code="common.delete.msg"/>")){
        form.action="<c:url value='/sym/mnu/mpm/EgovMenuManageDelete.do' />";
		form.submit();
	}
}
/* ********************************************************
 * 목록조회 함수
 ******************************************************** */
function selectList(){
	location.href = "<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do' />";
}

/* ********************************************************
 * 파일명 엔터key 목록조회  함수
 ******************************************************** */
function press() {
    if (event.keyCode==13) {
    	searchFileNm();    // 원래 검색 function 호출
    }
}


<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>
-->