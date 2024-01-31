$(document).ready(function() {
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
    
    
    
    
    
    
});

/**
 * 전체 체크박스 선택
 * @returns
 * @Description
 */
function fnSelectAllCheck(){
	if($("#reprt_all_check").prop("checked")) {
		$('.reprt_check').prop('checked', true);
	} else {
		$('.reprt_check').prop('checked', false); 
	}
}

/**
 * 보고서 파일 팝업
 * @returns
 * @Description
 */
function fnRptFilePopup(se){	
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			if(se == "BSC"){
				$(this).load("/sys/spt/sct/insertBscComptReportPopup.do");	
			}else if(se == "APR"){
				$(this).load("/sys/spt/sct/insertAprComptReportPopup.do");
			}else if(se == "LCP"){
				$(this).load("/sys/spt/sct/insertLcpComptReportPopup.do");
			}else if(se == "WKA"){
				$(this).load("/sys/spt/sct/insertWkaComptReportPopup.do");
			}else if(se == "CNL"){
				$(this).load("/sys/spt/sct/insertCnlComptReportPopup.do");
			}else if(se == "ECB"){
				$(this).load("/sys/spt/sct/insertEcbComptReportPopup.do");
			}else if(se == "FRD"){
				$(this).load("/sys/spt/sct/insertFrdComptReportPopup.do");
			}else if(se == "MSE"){
				$(this).load("/sys/spt/sct/insertMseComptReportPopup.do");
			}else if(se == "PCS"){
				$(this).load("/sys/spt/sct/insertPcsComptReportPopup.do");
			}
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 480,
		width: 500,
		title: "완료보고서 파일 업로드"
	});
}

/**
 * 기초조사 완료보고서 등록
 * @returns
 * @Description
 */
function fnUploadRptFile(){

	if($("[name='files']").val().length == 0){
		alert("파일을 업로드해주세요.")
		return;	
	}
	
	var orderSd = $("#svySdView option:checked").text();
	$("#insertForm").append("<input type='hidden' name='orderSd' value='"+orderSd+"'>");
	var orderSgg = $("#svySggView option:checked").text();
	$("#insertForm").append("<input type='hidden' name='orderSgg' value='"+orderSgg+"'>");
	
	$(".loading-div").show();
	var option = {
	    dataType : 'json',
	    success: function(res){
	        if(res.status){
	        	if(res.status == "success"){
	        		$(".loading-div").hide();
	        		alert("보고서가 등록되었습니다.");
	        		window.location.href = "/sys/spt/sct/select"+res.svyType+"ComptReportList.do";
	        	}else{
	        		$(".loading-div").hide();
	        		alert("보고서 등록이 실패하였습니다.\n관리자에게 문의하세요.");
	        	}
	        }
	    },
	    error: function(res){
	    	$(".loading-div").hide();
	        alert("에러가 발생했습니다.")
	    }
	};
	$("#insertForm").ajaxSubmit(option);
}

/**
 * 첨부파일 표출
 * @returns
 * @Description
 */
function fnHandleFile(file){
	$.ajax({
		success:function(){		
			$("#reportFileArea").empty();				
			for(var i=0;i<file.length;i++){				
				$("#reportFileArea").append("<tr><td>"+file[i].name+"</td><td>"+(file[i].size / 1000000).toFixed(2)+" MB"+"</td></tr>");
			}
		}
	})
}

/**
 * 첨부파일 삭제
 * @returns
 * @Description
 */
function fnDeleteRptFile(gid,se){
	$("input[name=gid]").val(gid);
	
	var url; //submit url
	var returnUrl; // return url
	
	if(confirm("삭제하시겠습니까?")){
		if(se == "BSC"){
			url = "/sys/spt/sct/deleteBscComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectBscComptReportList.do";
		}else if(se == "APR"){
			url = "/sys/spt/sct/deleteAprComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectAprComptReportList.do";
		}else if(se == "LCP"){
			url = "/sys/spt/sct/deleteLcpComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectLcpComptReportList.do";
		}else if(se == "WKA"){
			url = "/sys/spt/sct/deleteWkaComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectWkaComptReportList.do";
		}else if(se == "CNL"){
			url = "/sys/spt/sct/deleteCnlComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectCnlComptReportList.do";
		}else if(se == "ECB"){
			url = "/sys/spt/sct/deleteEcbComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectEcbComptReportList.do";
		}else if(se == "FRD"){
			url = "/sys/spt/sct/deleteFrdComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectFrdComptReportList.do";
		}else if(se == "MSE"){
			url = "/sys/spt/sct/deleteMseComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectMseComptReportList.do";
		}else if(se == "PCS"){
			url = "/sys/spt/sct/deletePcsComptReportFile.do";
			returnUrl = "/sys/spt/sct/selectPcsComptReportList.do";
		}
		
		$.ajax({
			type : 'POST',
			url: url,
			data : $("#listForm").serialize(),
			dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href=returnUrl;
                } else {
                    alert(data.message);
                }
	        },
	        error: function(data) {
	        	alert(data.message);
	        }
		});
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
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageUnit]").val(pageUnit);
	
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

/**
 * 행정구역 시군구 조회 ajax
 * @returns
 */
function fncAdministCtprvnChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#svySggView").empty().append("<option value>선택하세요</option>");
		return false;
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneSigngu.do",
		type:"POST",
        data: {sdCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#svySggView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svySggView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
        			});
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}
/**
 * 기관명 조회 ajax
 * @returns
 */
function fncOrgzntChange(event){
	
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#deptNm").empty().append("<option value>선택하세요</option>");
		return false;
	}
	
	$.ajax({
		url:"/sys/spt/sct/selectOrgzntList.do",
		type:"POST",
		data: {corpNm:code},
		dataType:"json",
		success:function(list){
			if(list.result){
				if(list.result.length > 0){
					$("#deptNm").empty().append("<option value>선택하세요</option>");
					$.each(list.result,function(idx,item){
						$("#deptNm").append("<option value=\"".concat(item.deptNm,"\">").concat(item.deptNm,"</option>"));
					});
				}
			}
		},
		error: function(request, status, error){
			alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
		}
	});
}