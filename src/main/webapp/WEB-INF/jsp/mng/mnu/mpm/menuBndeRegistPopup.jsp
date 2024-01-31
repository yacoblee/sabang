<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="wTableFrm">
	<!-- 타이틀 -->
	<h2><spring:message code="comSymMnuMpm.menuRegist.pageTop.title"/></h2>

	<form name="menuManageRegistForm" action ="<c:url value='/sym/mnu/mpm/EgovMenuBndeRegist.do'/>" method="post" enctype="multipart/form-data">

	<!-- 등록폼 -->
	<table class="wTable">
		<colgroup>
			<col style="width:16%" />
			<col style="" />
		</colgroup>
		<tr>
			<th><spring:message code="comSymMnuMpm.menuBndeRegist.menuNo"/> <span class="pilsu">*</span></th>
			<td class="left">
				<input type = "file" name="file" size="40" title="<spring:message code='title.attachedFileSelect'/>">
			</td>
		</tr>
	</table>

	<!-- 하단 버튼 -->
	<div class="btn">
		<input class="s_submit" type="submit" value="<spring:message code="button.bulkUpload" />" onclick="insertMenuManage(); return false;" /><!-- 일괄등록 -->
		<span class="btn_s"><a href="<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do'/>" onclick="selectList(); return false;"><spring:message code="button.list"/></a></span><!-- 목록 -->
	</div>
	<div style="clear:both;"></div>

	<input name="cmd" type="hidden" value="<c:out value='bndeInsert'/>"/>
	</form>
	
</div>




<!-- 
<script  language="javascript1.2" type="text/javaScript">
/* ********************************************************
 * 메뉴일괄생성처리 함수
 ******************************************************** */
function insertMenuManage() {
	if(confirm("<spring:message code="comSymMnuMpm.menuBndeRegist.validate.confirm.insert"/>")){ //메뉴 일괄등록을 하시겠습니까?. \n 메뉴정보와  프로그램목록, 프로그램 변경내역 존재시 삭제 하실 수 없습니다.
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
	if(confirm("<spring:message code="comSymMnuMpm.menuBndeRegist.validate.confirm.delete"/>")){ //메뉴일괄삭제를 하시겠습니까?. \n 메뉴정보와  프로그램목록, 프로그램 변경내역 데이타 모두 삭제 됩니다.
		document.menuManageRegistForm.action ="<c:url value='/sym/mnu/mpm/EgovMenuBndeAllDelete.do' />";
		document.menuManageRegistForm.submit();
	}
}
/* ********************************************************
 * 메뉴일괄등록시 등록파일 체크 함수
 ******************************************************** */
function checkFile(){
	if(document.menuManageRegistForm.file.value==""){
	   alert("<spring:message code="comSymMnuMpm.menuBndeRegist.validate.alert.checkFile"/>"); //업로드 할 파일을 지정해 주세요
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
	     alert("<spring:message code="comSymMnuMpm.menuBndeRegist.validate.checkFile"/>"); //파일 형식이 맞지 않습니다.\n xls,xlsx만 업로드가 가능합니다!
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