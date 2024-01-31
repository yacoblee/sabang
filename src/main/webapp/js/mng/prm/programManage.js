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

/**
 * 프로그램 모두선택 처리
 * @returns
 */
function fCheckAll() {
    var checkField = $("input[name=checkField]");
    if($("input[name=checkAll]").is(':checked')) {
        if(checkField) {
            if(checkField.length > 1) {
            	checkField.each(function() {
            		this.checked = true;
            	});
            } else {
            	checkField.prop("checked",true);
            }
        }
    } else {
        if(checkField) {
            if(checkField.length > 1) {
            	checkField.each(function() {
            		this.checked = false;
            	});
            } else {
            	checkField.prop("checked",false);
            }
        }
    }
}

/**
 * 프로그램 멀티삭제
 * @returns
 */
function fDeleteProgrmManageList() {
    var checkField = $("input[name=checkField]");
    var ProgrmFileNm = $("input[name=checkProgrmFileNm]");
    var checkProgrmFileNms = "";
    var checkedCount = 0;
    if(checkField) {
    	if(checkField.length > 1) {
    		checkField.each(function(idx) {
        		if(this.checked){
        			checkProgrmFileNms += ((checkedCount==0? "" : ",") + ProgrmFileNm.eq(idx).value);
                    checkedCount++;
        		}
        	});
        } else {
            if(checkField.is(":checked")) {
            	checkProgrmFileNms = ProgrmFileNm.val();
            	checkedCount++;
            }
        }
    }

    if(checkedCount ==0){
		alert("선택된 메뉴가 없습니다.");
		return false;
    }

    if(confirm("삭제하시겠습니까?")){
    	$("input[name=checkedProgrmFileNmForDel]").val(checkProgrmFileNms);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/prm/progrmManageListDelete.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    window.location.reload();
                    //location.href="/mng/sec/ram/authorList.do";
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
 * 프로그램 등록화면
 * @returns
 */
function insertProgramListManageView() {
	$("#listForm").attr("action","/mng/prm/programListRegist.do");
	$("#listForm").submit();
	//location.href="/mng/prm/programListRegist.do";
}

/**
 * 프로그램 상세화면
 * @param progrmFileNm
 * @returns
 */
function selectUpdtProgramListDetail(progrmFileNm) {
	$("input[name=tmp_progrmNm]").val(progrmFileNm);
	$("#listForm").attr("action","/mng/prm/programListDetail.do");
	$("#listForm").submit();
}

/**
 * 프로그램 등록
 * @returns
 */
function insertProgramListManage(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateProgrmManageVO(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
    			type: 'POST',
    	        url: form.action,
    	        dataType: "json",
    	        success: function(data) {
    	        	if (data.status == "success") {
                        alert(data.message);
                        location.href = "/mng/prm/programListManageSelect.do";
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
}

/**
 * 프로그램 삭제
 * @param form
 * @returns
 */
function deleteProgramListManage() {
	if(confirm("삭제하시겠습니까?")){
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/prm/progrmManageListDelete.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    //window.location.reload();
                    location.href="/mng/prm/programListManageSelect.do";
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
 * 프로그램 수정화면
 * @returns
 */
function updateProgramListManageView(){
	$("#listForm").attr("action","/mng/prm/programListUpdtSelect.do");
	$("#listForm").submit();
}

/**
 * 프로그램 수정
 * @returns
 */
function updateProgramListManage(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateProgrmManageVO(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
    			type: 'POST',
    	        url: form.action,
    	        dataType: "json",
    	        success: function(data) {
    	        	if (data.status == "success") {
                        alert(data.message);
                        location.href = "/mng/prm/programListManageSelect.do";
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
}

/**
 * 프로그램 목록 일괄등록 모달창
 * @returns
 */
function insertMultiProgramListManageView(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("/mng/prm/uploadProgramListPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 350,
		width: 400,
		title: "프로그램목록 업로드"
	});
}

/**
 * 프로그램 목록 일괄등록
 * @returns
 */
function fnUploadProgramExcel(){
	var prmfile = $("#prmFile").val();
	if(prmfile == null){
		alert("업로드할 파일이 선택되지 않았습니다.");
		return false;
	}
	if(confirm("등록하시겠습니까?")) {
		$("#uploadForm").ajaxForm({
			type: "POST",
			dataType: "json",
			success: function(data){
				if(data.status == "success"){
					alert(data.message);
					window.location.reload();
				}
			},
			error: function(data){
				alert(data.message);
			}
		}).submit();
	}
}
/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	$("#listForm").attr("action","/mng/prm/programListManageSelect.do");
	$("#listForm").submit();
}
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