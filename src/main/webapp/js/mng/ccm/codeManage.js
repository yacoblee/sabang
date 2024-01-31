/**
 * 
 */

$(document).ready(function() {
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
})

/* ******************************************************************************
 * 공통코드 관련 스크립트
 * ******************************************************************************/

/**
 * 공통코드 상세화면
 * @param codeId
 * @returns
 */
function fncCodeDetail(codeId){
	$("input[name=codeId]").val(codeId);
	$("#listForm").attr("action","/mng/ccm/cca/selectCcmCmmnCodeDetail.do");
	$("#listForm").submit();
}

/**
 * 공통코드 등록화면
 * @returns
 */
function fncAddCmmnCodeInsert(){
	location.href="/mng/ccm/cca/registCcmCmmnCodeView.do";
}

/**
 * 공통코드 등록
 * @returns
 */
function fncCmmnCodeInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateCmmnCodeVO(form)){
        	return false;
        } else {
			$("#listForm").ajaxForm({
				type: 'POST',
		        url: form.action,
		        dataType: "json",
		        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	                location.href="/mng/ccm/cca/selectCcmCmmnCodeList.do";
	            } else {
	                alert(data.message);
					location.href="/mng/ccm/cca/registCcmCmmnCodeView.do";
	            }
	        },
	        error: function(data) {
	        	alert(data.message);
				location.href="/mng/ccm/cca/registCcmCmmnCodeView.do";
	        }
			}).submit();
        }
    }
}
/**
 * 공통코드 삭제
 * @param codeId
 * @returns
 */
function fncDeleteCmmnCode(codeId){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=codeId]").val(codeId);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/ccm/cca/removeCcmCmmnCode.do",
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/mng/ccm/cca/selectCcmCmmnCodeList.do";
            } else {
                alert(data.message);
            }
        },
        error: function(data) {
        	alert(data.message);
        }
		}).submit();
	}
}

/**
 * 공통코드 수정화면
 * @returns
 */
function fncUpdateCmmnCodeView(){
	$("#listForm").attr("action","/mng/ccm/cca/updateCcmCmmnCodeView.do");
	$("#listForm").submit();
}

/**
 * 공통코드 수정
 * @returns
 */
function fncCmmnCodeUpdate(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateCmmnCodeVO(form)){
        	return false;
        }else{
			$("#listForm").ajaxForm({
				type: 'POST',
		        url: form.action,
		        dataType: "json",
		        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	                location.href="/mng/ccm/cca/selectCcmCmmnCodeList.do";
	            } else {
	                alert(data.message);
					location.href="/mng/ccm/cca/selectCcmCmmnCodeDetail.do";
	            }
	        },
	        error: function(data) {
	        	alert(data.message);
				location.href="/mng/ccm/cca/selectCcmCmmnCodeDetail.do";
	        }
			}).submit();
        }
    }
}

/* ******************************************************************************
 * 공통분류코드 관련 스크립트
 * ******************************************************************************/

/**
 * 공통분류코드 상세화면
 * @param clCode
 * @returns
 */
function fnClCodedetail(clCode){
	$("input[name=clCode]").val(clCode);
	$("#listForm").attr("action","/mng/ccm/ccc/selectCcmCmmnClCodeDetail.do");
	$("#listForm").submit();
}

/**
 * 공통분류코드 등록화면
 * @returns
 */
function fncAddCmmnClCodeInsert(){
	location.href="/mng/ccm/ccc/registCcmCmmnClCodeView.do";
}

/**
 * 공통분류코드 등록
 * @returns
 */
function fncCmmnClCodeInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateCmmnClCodeVO(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
		        url: form.action,
		        dataType: "json",
		        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	                location.href="/mng/ccm/ccc/selectCcmCmmnClCodeList.do";
	            } else {
	                alert(data.message);
					location.href="/mng/ccm/ccc/registCcmCmmnClCodeView.do";
	            }
	        },
	        error: function(data) {
	        	alert(data.message);
				location.href="/mng/ccm/ccc/registCcmCmmnClCodeView.do";
	        }
			}).submit();
        }
    }
}

/**
 * 공통분류코드 삭제
 * @param clCode
 * @returns
 */
function fncDeleteCmmnClCode(clCode){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=clCode]").val(clCode);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/ccm/ccc/removeCcmCmmnClCode.do",
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/mng/ccm/ccc/selectCcmCmmnClCodeList.do";
            } else {
                alert(data.message);
            }
        },
        error: function(data) {
        	alert(data.message);
        }
		}).submit();
	}
}

/**
 * 공통분류코드 수정화면
 * @returns
 */
function fncUpdateCmmnClCodeView(){
	$("#listForm").attr("action","/mng/ccm/ccc/updateCcmCmmnClCodeView.do");
	$("#listForm").submit();
}

/**
 * 공통분류코드 수정
 * @returns
 */
function fncCmmnClCodeUpdate(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateCmmnClCodeVO(form)){
        	return false;
        }else{
			$("#listForm").ajaxForm({
				type: 'POST',
		        url: form.action,
		        dataType: "json",
		        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	                location.href="/mng/ccm/ccc/selectCcmCmmnClCodeList.do";
	            } else {
	                alert(data.message);
					location.href="/mng/ccm/ccc/selectCcmCmmnClCodeDetail.do";
	            }
	        },
	        error: function(data) {
	        	alert(data.message);
				location.href="/mng/ccm/ccc/selectCcmCmmnClCodeDetail.do";
	        }
			}).submit();
        }
    }
}

/* ******************************************************************************
 * 공통상세코드 관련 스크립트
 * ******************************************************************************/

/**
 * 공통상세코드 상세화면
 * @param codeId
 * @param code
 * @returns
 */
function fnDetailCodedetail(codeId, code){
	$("input[name=codeId]").val(codeId);
	$("input[name=code]").val(code);
	$("#listForm").attr("action","/mng/ccm/cde/selectCcmCmmnDetailCodeDetail.do");
	$("#listForm").submit();
}

/**
 * 공통상세코드 등록화면
 * @returns
 */
function fncAddCmmnDetailCodeInsert(){
	location.href="/mng/ccm/cde/registCcmCmmnDetailCodeView.do";
}

/**
 * CodeId 가져오기
 * @param form
 * @returns
 */
function fnGetCodeId(){
	$("#listForm").attr("action","/mng/ccm/cde/registCcmCmmnDetailCodeView.do");
	$("#listForm").submit();
}

/**
 * 공통상세코드 등록
 * @returns
 */
function fncCmmnDetailCodeInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateCmmnDetailCodeVO(form)){
        	return false;
        }else{
			$("#listForm").ajaxForm({
				type: 'POST',
		        url: "/mng/ccm/cde/registCcmCmmnDetailCode.do",
		        dataType: "json",
		        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	                location.href="/mng/ccm/cde/selectCcmCmmnDetailCodeList.do";
	            } else {
	                alert(data.message);
					location.href="/mng/ccm/cde/registCcmCmmnDetailCodeView.do";
	            }
	        },
	        error: function(data) {
	        	alert(data.message);
				location.href="/mng/ccm/cde/registCcmCmmnDetailCodeView.do";
	        }
			}).submit();
        }
    }
}

/**
 * 공통상세코드 삭제
 * @param codeId
 * @param code
 * @returns
 */
function fncDeleteCmmnDetailCode(codeId, code){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=codeId]").val(codeId);
		$("input[name=code]").val(code);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/ccm/cde/removeCcmCmmnDetailCode.do",
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/mng/ccm/cde/selectCcmCmmnDetailCodeList.do";
            } else {
                alert(data.message);
            }
        },
        error: function(data) {
        	alert(data.message);
        }
		}).submit();
		
	}
}

/**
 * 공통상세코드 수정화면
 * @returns
 */
function fncUpdateCmmnDetailCodeView(){
	$("#listForm").attr("action","/mng/ccm/cde/updateCcmCmmnDetailCodeView.do");
	$("#listForm").submit();
}

/**
 * 공통상세코드 수정
 * @returns
 */
function fncCmmnDetailCodeUpdate(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateCmmnDetailCodeVO(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
		        url: form.action,
		        dataType: "json",
		        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	                location.href="/mng/ccm/cde/selectCcmCmmnDetailCodeList.do";
	            } else {
	                alert(data.message);
					location.href="/mng/ccm/cde/selectCcmCmmnDetailCodeDetail.do";
	            }
	        },
	        error: function(data) {
	        	alert(data.message);
				location.href="/mng/ccm/cde/selectCcmCmmnDetailCodeDetail.do";
	        }
			}).submit();
        }
    }
}

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/

/**
 * 검색
 * @returns
 * @Description
 */
function fnSearch(){
	$("input[name=pageIndex]").val(1);
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}