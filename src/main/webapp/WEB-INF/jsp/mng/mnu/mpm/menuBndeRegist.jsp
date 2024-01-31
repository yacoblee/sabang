<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle"><spring:message code="mngMnuMpm.menuBndeRegist.title" /></c:set><!-- 프로그램목록등록 -->
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">메뉴관리</a></li>
	</ul>
	<h3>${pageTitle}</h3>
	<div id="contents">
		<form:form id="listForm" name="menuManageRegistForm" action ="/mng/mnu/mpm/menuBndeRegist.do" method="post" enctype="multipart/form-data">
		<input name="cmd" type="hidden" value="<c:out value='bndeInsert'/>"/>
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
			<caption>${pageTitle} <spring:message code="title.create" /></caption>
				<tbody>
					<!-- 입력/선택 -->
					<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
					<!-- 일괄파일 -->
					<c:set var="title"><spring:message code="mngMnuMpm.menuBndeRegist.menuNo"/></c:set>
					<tr>
						<th>${title} <span class="pilsu">*</span></th>
						<td>
						    <input type = "file" name="file" size="40" title="<spring:message code='title.attachedFileSelect'/>">   
						</td>
					</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="save-btn" onclick="javascript:fncBndeInsertMenuManage(); return false;"><spring:message code="button.bulkUpload" /></button><!-- 일괄등록 -->
					<button type="button" class="list-btn" onclick="location.href='/mng/mnu/mpm/menuManageSelect.do'"><spring:message code="button.list" /></button><!-- 목록 -->
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>



<!-- 
<script  language="javascript1.2" type="text/javaScript">
/* ********************************************************
 * 메뉴일괄생성처리 함수
 ******************************************************** */
function insertMenuManage() {
	if(confirm("<spring:message code="mngMnuMpm.menuBndeRegist.validate.confirm.insert"/>")){ //메뉴 일괄등록을 하시겠습니까?. \n 메뉴정보와  프로그램목록, 프로그램 변경내역 존재시 삭제 하실 수 없습니다.
	   if(checkFile()){
		   document.menuManageRegistForm.action ="<c:url value='/sym/mnu/mpm/EgovMenuBndeRegist.do' />";
	      document.menuManageRegistForm.submit();
	   }
	}
}
/* ********************************************************
 * 메뉴일괄삭제처리 함수
 ******************************************************** */
function deleteMenuList() {
	if(confirm("<spring:message code="mngMnuMpm.menuBndeRegist.validate.confirm.delete"/>")){ //메뉴일괄삭제를 하시겠습니까?. \n 메뉴정보와  프로그램목록, 프로그램 변경내역 데이타 모두 삭제 됩니다.
		document.menuManageRegistForm.action ="<c:url value='/sym/mnu/mpm/EgovMenuBndeAllDelete.do' />";
		document.menuManageRegistForm.submit();
	}
}
/* ********************************************************
 * 메뉴일괄등록시 등록파일 체크 함수
 ******************************************************** */
function checkFile(){
	if(document.menuManageRegistForm.file.value==""){
	   alert("<spring:message code="mngMnuMpm.menuBndeRegist.validate.alert.checkFile"/>"); //업로드 할 파일을 지정해 주세요
	   return false;
	}

	var  str_dotlocation,str_ext,str_low;
	str_value  = document.menuManageRegistForm.file.value;
	str_low   = str_value.toLowerCase(str_value);
	str_dotlocation = str_low.lastIndexOf(".");
	str_ext   = str_low.substring(str_dotlocation+1);

	switch (str_ext) {
	  case "xls" :
	  case "xlsx" :
		 return true;
	     break;
	  default:
	     alert("<spring:message code="mngMnuMpm.menuBndeRegist.validate.checkFile"/>"); //파일 형식이 맞지 않습니다.\n xls,xlsx만 업로드가 가능합니다!
	     return false;
	}
}

/* ********************************************************
 * 목록조회  함수
 ******************************************************** */
function selectList(){
	location.href = "<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do' />";
}

<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>
</script>
 -->