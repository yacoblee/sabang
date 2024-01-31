<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="updatePhotoForm" method="post" action="/sys/fck/apr/sct/updatePhotoList.do">
    	<input type="hidden" value="${result.mindt}" name="minDt" />
    	<input type="hidden" value="${result.maxdt}" name="maxDt" />
    	<input type="hidden" value="${result.mindt}" name="startDate" />
    	<input type="hidden" value="${result.maxdt}" name="endDate" />
    	<input type="hidden" value="${result.allcnt}" name="allCnt" />
    	<table class="fieldBookTable">
    		<colgroup>
    			<col width="25%">
    			<col width="50%">
    			<col width="25%">
    		</colgroup>
    		
         	<tr>
		      	<c:set var="title">날짜</c:set>
			  	<th>${title}</th>
			  	<td colspan="2">
					<input type="text" id="sDate" value="<c:out value="${result.mindt}"/>">
					~
					<input type="text" id="eDate" value="<c:out value="${result.maxdt}"/>">
		      	</td>
	      	</tr>
	      	<tr>
	      		<c:set var="title">대상지 수</c:set>
			  	<th>${title}</th>
			  	<td>
			  		<span id="allCnt"><c:out value="${result.allcnt}" /> 건</span>
		      	</td>
	      		<td>
	      			<button type="button" class="search-btn" onclick="javascript:fnPhotoNullCnt(); return false;"><spring:message code="title.inquire" /></button>
	      		</td>
	      	</tr>
    	</table>
        <img class="loading-img" style='display:none;' src="<%=request.getContextPath() %>/images/common/progressbar_blue.gif" alt="로딩바 이미지">
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnPhotoUpdate(); return false;">실행</button>				
			</div>
		</div>
		</form:form>
    </div>
</div>
<script>
$.datepicker.regional['kr'] = {
    closeText: '닫기', // 닫기 버튼 텍스트 변경
    currentText: '오늘', // 오늘 텍스트 변경
    monthNames: ['1 월','2 월','3 월','4 월','5 월','6 월','7 월','8 월','9 월','10 월','11 월','12 월'], // 개월 텍스트 설정
    monthNamesShort: ['1 월','2 월','3 월','4 월','5 월','6 월','7 월','8 월','9 월','10 월','11 월','12 월'], // 개월 텍스트 설정
    dayNames: ['월요일','화요일','수요일','목요일','금요일','토요일','일요일'], // 요일 텍스트 설정
    dayNamesShort: ['월','화','수','목','금','토','일'], // 요일 텍스트 축약 설정&nbsp;   
    dayNamesMin: ['월','화','수','목','금','토','일'], // 요일 최소 축약 텍스트 설정
    dateFormat: 'yy-mm-dd', // 날짜 포맷 설정
    showButtonPanel: true,
    maxDate: "today",
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    yearRange: 'c-99:c+99',
    showOtherMonths: true,
    selectOtherMonths: true 
};

// Seeting up default language, Korean
$.datepicker.setDefaults($.datepicker.regional['kr']);

$("#sDate").datepicker({
	onSelect: function(date){
		var eDate = $("#eDate");
		var sDate = $(this).datepicker("getDate");
		var min = $(this).datepicker("getDate");
		//eDate.datepicker("setDate",min);
		//sDate.setDate(sDate.getDate() + 30);
		eDate.datepicker('option', 'minDate', min);
	}
});
$("#eDate").datepicker({
	onSelect: function(date){
		var eDate = $(this).datepicker("getDate");
		var sDate = $("#sDate");
		sDate.datepicker('option', 'maxDate', eDate);
	}
});

$("#sDate").datepicker("setDate",$("input[name=startDate]").val());
$("#eDate").datepicker("setDate",$("input[name=endDate]").val());

$("#sDate").datepicker("option","maxDate",$("#eDate").datepicker("getDate"));
$("#eDate").datepicker("option","minDate",$("#sDate").datepicker("getDate"));

</script>