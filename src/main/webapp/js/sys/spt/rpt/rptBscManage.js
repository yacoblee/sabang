$(document).ready(function() {
    $('#id,#writer,#mstNm').keypress(function(event) {
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
 * 보고서 조회(추후 클립리포트 연계)
 * @returns
 * @Description
 */
function fnSelectReport(){
	window.open('','보고서','width:500, height:700, toolbar=no, top:50, height:50, scrollbars=no');
}

/**
 * 보고서 파일 팝업
 * @returns
 * @Description
 */
function fnRptBscFilePopup(id){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("/sys/spt/rpt/uploadRptBscFilePopup.do?reprt_id="+id);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 350,
		width: 400,
		title: "기초조사 보고서 파일등록"
	});
}

/**
 * 보고서 등록
 * @returns
 * @Description
 */
function fnUploadRptBscFile(){
	$("form[role='insertReportFile']").submit();
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
function fnDeleteRptBscFile(file_id){
	if(confirm("선택 파일을 삭제하시겠습니까?")) {
		$.ajax({
			type: "POST",
			url: "/sys/spt/rpt/deleteBscFile.do",
			dataType: "text",
			data : {
				"file_id" : file_id					
			},
			success:function(){			
				window.location.reload();
			}
		})
	}
}

/**
 * 공유방 상세조회
 * @param mstId
 * @returns
 */
function fnMstDetail(mstId,se){	
	$("input[name=mstId]").val(mstId);
	if(se == "BSC"){
		$("#listForm").attr("action","/sys/spt/rpt/rptBscManageDetail.do");
	}else if(se == "APR"){
		$("#listForm").attr("action","/sys/spt/rpt/rptAprManageDetail.do");
	}else{
		$("#listForm").attr("action","/sys/spt/rpt/rptLcpManageDetail.do");
	}

	$("#listForm").submit();
}

/**
 * 조사완료지 전체조회
 * @returns
 */
function fncAllCheckReport(se){
	//var rptType = $("input[name=rptType]").val();
	var svyType = $("input[name=type]").val() ? $("input[name=type]").val() : $("input[name=lastGrd]").val();
	var svyYear = $("input[name=year]").val();
	var form = $("#listForm");
	
	if(svyType == null || svyType == undefined || svyType == ''){
		alert("조사유형을 반드시 선택해야 보고서를 생성할 수 있습니다.\n조회 후 다시 시도해주세요.")
		return false;
	}
	
	if(svyType.length == 0){
		alert("하나의 조사유형으로만 보고서를 생성할 수 있습니다.\n조회 후 다시 시도해주세요.");
		return;
	}
	
	if(svyYear.length == 0){
		alert("조사연도를 반드시 선택해야 보고서를 생성할 수 있습니다.\n조회 후 다시 시도해주세요.");
		return;
	}
	
	var fileName = "";
	
	if(confirm("전체보고서를 다운받으시겠습니까?")) {
		if(se == "BSC"){
			if(svyType == "산사태"){
				if(svyYear == '2021'){
					fileName = "REPORT_SYS_LSS_BSC_01";
				}else{
					fileName = "REPORT_SYS_LSS_BSC_01_01";
				}
			}else{
				if(svyYear == '2021'){
					fileName = "REPORT_SYS_LSS_BSC_02";
				}else{
					fileName = "REPORT_SYS_LSS_BSC_02_01";
				}
			}
		}else if(se == "APR"){
			if(svyType == "사방댐 외관점검"){
				fileName = "REPORT_SYS_FCK_APR_01";
			}else if(svyType == "계류보전 외관점검"){
				fileName = "REPORT_SYS_FCK_APR_02";
			}else{
				fileName = "REPORT_SYS_FCK_APR_03";
			}	
		}else if(se == "LCP"){
			if(svyType == "C"){
				fileName = "REPORT_SYS_LSS_LCP_01";
			}else{
				fileName = "REPORT_SYS_LSS_LCP_02";
			}
		}else if(se == "WKA"){
			if(svyType == "산사태"){
				fileName = "REPORT_SYS_LSS_WKA_01";
			}else{
				fileName = "REPORT_SYS_LSS_WKA_02";
			}
		}else if(se == "CNL"){
			if(svyType == "산사태"){
				fileName = "REPORT_SYS_LSS_CNL_01";
			}else{
				fileName = "REPORT_SYS_LSS_CNL_02";
			}
		}else if(se == "PCS"){
			if(svyType == "콘크리트 사방댐 정밀점검" || svyType == "석재 사방댐 정밀점검" || svyType == "강재 사방댐 정밀점검"){
				fileName = "REPORT_SYS_FCK_PCS_01";
			}else if(svyType == "계류보전 정밀점검"){
				fileName = "REPORT_SYS_FCK_PCS_02";
			}else{
				fileName = "REPORT_SYS_FCK_PCS_03";
			}
		}
		fncOpenClipReport(fileName,null,form);
	}else {
		return;
	}
}

/**
 * 조사완료지 멀티선택 
 * @returns
 */
function fncMultiCheckReport(se){
	var checkboxs = $(".BoardList tbody input[type=checkbox]:checked");
	var checkIds = "",checkTypeNm = "",checkType = true;
//	var mstId = $("input[name=mstId]").val();
	var svyType = $("input[name=type]").val();
	var svyYear = $("input[name=year]").val();
	
	if(checkboxs.length == 0){
		alert("조사정보가 선택되지 않았습니다.\n선택 후 다시 시도해주세요.");
		return;
	}
	
	if(svyYear.length == 0){
		alert("조사연도를 반드시 선택해야 보고서를 생성할 수 있습니다.\n조회 후 다시 시도해주세요.");
		return;
	}
	
//	if(svyType.length == 0){
//		alert("하나의 조사유형으로만 보고서를 생성할 수 있습니다.\n조회 후 다시 시도해주세요.");
//		return;
//	}
	
	$.each(checkboxs,function(idx,elm){
		var typeElm = $(elm).closest("tr").children().eq(3);
		var typeNm = $(typeElm).text();
		
		if(checkTypeNm.length == 0){
			checkTypeNm = typeNm;
		}else{
			if(checkTypeNm == typeNm){
				checkType = true;
			}else{
				checkType = false;
				return false;
			}
		}
		
		checkIds += $(elm).val();
		if(checkboxs.length-1 > idx){
			checkIds += ",";
		}
	});
	
	if(!checkType){
		alert("하나의 조사유형으로만 보고서를 생성할 수 있습니다.\n확인 후 다시 시도해주세요.");
		return;
	}
	
	var fileName = "";
	
	if(se == "BSC"){
		if(checkTypeNm == "산사태"){
			if(svyYear == '2021'){
				fileName = "REPORT_SYS_LSS_BSC_01";
			}else{
				fileName = "REPORT_SYS_LSS_BSC_01_01";
			}
		}else{
			if(svyYear == '2021'){
				fileName = "REPORT_SYS_LSS_BSC_02";
			}else{
				fileName = "REPORT_SYS_LSS_BSC_02_01";
			}
		}
	}else if(se == "APR"){
		if(checkTypeNm == "사방댐 외관점검"){
			fileName = "REPORT_SYS_FCK_APR_01";
		}else if(checkTypeNm == "계류보전 외관점검"){
			fileName = "REPORT_SYS_FCK_APR_02";
		}else{
			fileName = "REPORT_SYS_FCK_APR_03";
		}
	}else if(se == "LCP"){
		fileName = "REPORT_SYS_LSS_LCP_01";
	}else if(se == "WKA"){
		if(checkTypeNm == "취약지역 실태조사(산사태)"){
			fileName = "REPORT_SYS_LSS_WKA_01";		
		}else{
			fileName = "REPORT_SYS_LSS_WKA_02";
		}		
	}else if(se == "CNL"){
		if(checkTypeNm == "취약지역 해제조사(산사태)"){
			fileName = "REPORT_SYS_LSS_CNL_01";		
		}else{
			fileName = "REPORT_SYS_LSS_CNL_02";
		}
	}else if(se == "PCS"){
		if(checkTypeNm == "콘크리트 사방댐 정밀점검" || checkTypeNm == "석재 사방댐 정밀점검" || checkTypeNm == "강재 사방댐 정밀점검"){
			fileName = "REPORT_SYS_FCK_PCS_01";
		}else if(checkTypeNm == "계류보전 정밀점검"){
			fileName = "REPORT_SYS_FCK_PCS_02";
		}else{
			fileName = "REPORT_SYS_FCK_PCS_03";
		}
	}
	
	fncOpenClipReport(fileName,checkIds);
}

/**
 * 클립리포트 호출
 * @param fileNm
 * @param mstId
 * @param checkId
 * @param searchCondition
 * @param searchKeyword
 * @returns
 */
function fncOpenClipReport(fileNm,checkId,param){
	$("form[name=reportForm]").remove();
	
	var form = $("form[name=reportForm]");
	
	if(form.length == 0){
		form = $("<form></form");
		form.attr("name","reportForm");
		form.append("<input type=\"hidden\" name=\"nowPge\" value=\"1\">");
		form.append("<input type=\"hidden\" name=\"rptName\" value=\"".concat(fileNm,"\">"));
	}
	
	if(checkId){
		form.append("<input type=\"hidden\" name=\"gid\" value=\"".concat(checkId,"\">"));
	}
	if(param){
		$.each($(param).find("input[type=hidden]"),function(idx,elm){
			var nm = $(elm).attr("name").replace("View","");
			var val = $(elm).val();
			form.append("<input type=\"hidden\" name=\"".concat(nm,"\" value=\"").concat(val,"\">"));
		});
	}
	
	form.appendTo("body");
	
	window.open('',fileNm,'width=1100, height=800, scrollbars=1');
	
	form.attr("action","/cmm/openClipReport.do");
	form.attr("method","post");
	form.attr("target",fileNm);
	form.submit();
	form.remove();
}


/**
 * 관할2 조회 ajax
 * @param event
 * @returns
 */
function fncRegionChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		if(se == 'list'){
			$("#region2View").empty().append("<option value>선택하세요</option>");
			return false;
		}else{
			$("#region2").empty().append("<option value>선택하세요</option>");
			return false;
		}
		
	}
	
	$.ajax({
		url:"/cmm/selectRegionDetail.do",
		type:"POST",
        data: {codeId:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
					if(se == 'list'){
						$("#region2View").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#region2View").append("<option value=\"".concat(item.code,"\">").concat(item.codeNm,"</option>"));
	        			});	
					}else{
						$("#region2").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#region2").append("<option value=\"".concat(item.code,"\">").concat(item.codeNm,"</option>"));
	        			});
					}
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 행정구역 시군구 조회 ajax
 * @returns
 */
function fncAdministCtprvnChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		if(se == 'list'){
			$("#sggView").empty().append("<option value>선택하세요</option>");
			$("#emdView").empty().append("<option value>선택하세요</option>");
			$("#riView").empty().append("<option value>선택하세요</option>");
			return false;
		}else{
			$("#sgg").empty().append("<option value>선택하세요</option>");
			$("#emd").empty().append("<option value>선택하세요</option>");
			$("#ri").empty().append("<option value>선택하세요</option>");
			return false;
		}
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneSigngu.do",
		type:"POST",
        data: {sdCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
					if(se == 'list'){
						$("#sggView").empty().append("<option value>선택하세요</option>");
	        			$("#emdView").empty().append("<option value>선택하세요</option>");
	        			$("#riView").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#sggView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}else{
						$("#sgg").empty().append("<option value>선택하세요</option>");
	        			$("#emd").empty().append("<option value>선택하세요</option>");
	        			$("#ri").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#sgg").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 행정구역 읍면동 조회 ajax
 * @returns
 */
function fncAdministSignguChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		if(se == 'list'){
			$("#emdView").empty().append("<option value>선택하세요</option>");
			$("#riView").empty().append("<option value>선택하세요</option>");
			return false;
		}else{
			$("#emd").empty().append("<option value>선택하세요</option>");
			$("#ri").empty().append("<option value>선택하세요</option>");
			return false;
		}
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneEmd.do",
		type:"POST",
        data: {sggCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
					if(se == 'list'){
						$("#emdView").empty().append("<option value>선택하세요</option>");
	        			$("#riView").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#emdView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}else{
						$("#emd").empty().append("<option value>선택하세요</option>");
	        			$("#ri").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#emd").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}
        			
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 행정구역 리 조회 ajax
 * @returns
 */
function fncAdministEmdChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		if(se == 'list'){
			$("#riView").empty().append("<option value>선택하세요</option>");
			return false;
		}else{
			$("#ri").empty().append("<option value>선택하세요</option>");
			return false;
		}
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneRi.do",
		type:"POST",
        data: {emdCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
					if(se == 'list'){
						$("#riView").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#riView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}else{
						$("#ri").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#ri").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/*
 * 현장사진 다운로드
 */
function fncPhotoDown(){
	var checkboxs = $(".BoardList tbody input[type=checkbox]:checked");
	var rptType = $("input[name=rptType]").val();
	
	if(checkboxs.length == 0){
	 	$('.loading-div').hide();
		alert("조사정보가 선택되지 않았습니다.\n선택 후 다시 시도해주세요.");
		return;
	}else{
		if(checkboxs.length > 5) {
			alert("다운로드 개수는 최대 5개입니다.\n확인 후 다시 시도해주세요.");
			return;
		}
	}
	
	if(confirm("다운로드 하시겠습니까?")){
		$(".loading-div").show();
		var xhr = new XMLHttpRequest();
		var formdata = new FormData();
		
		formdata.append("rptType",rptType);
	
		$.each(checkboxs,function(idx,elm){
			var mstNm = $(elm).prevAll('.mstNm').val();		
			var svyId = $(elm).prevAll('.svyId').val();
			formdata.append("mstNmList",mstNm);		
			formdata.append("svyIdList",svyId);		
		});

		xhr.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
				$(".loading-div").hide();
				var filename = "";
				var disposition = xhr.getResponseHeader('Content-Disposition');
				if (disposition && disposition.indexOf('attachment') !== -1) {
					var filenameRegex = /filename[^;=\n]*=UTF-8''((['"]).*?\2|[^;\n]*)/;
					var matches = filenameRegex.exec(disposition);
					if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
				}
				
				if(filename == null || filename == ""){
					alert("현장사진이 존재하지 않습니다.");
					return;
				}
				//this.response is what you're looking for
				console.log(this.response, typeof this.response);
				var a = document.createElement("a");
				var url = URL.createObjectURL(this.response)
				a.href = url;
				a.download = decodeURIComponent(filename);
				document.body.appendChild(a);
				a.click();
				window.URL.revokeObjectURL(url);
			}
		};
		xhr.onerror = function(){
			$(".loading-div").hide();
			alert("다운로드를 실패하였습니다.");
		};
		xhr.open('POST', '/sys/spt/rpt/selectRptManagePhotoDownload.do');
		xhr.responseType = 'blob';
		xhr.send(formdata);
	}
}

function fncAllPhotoDown(){
	var svyYear = $("input[name=year]").val();
	var hiddens = $("#listForm").find("input[type=hidden]");
	var form = $("<form></form>").attr("action","/sys/spt/rpt/selectRptManagePhotoDownload.do").attr("method","post");
	
	if(svyYear.length == 0){
		alert("조사연도를 반드시 선택해야 엑셀파일을 생성할 수 있습니다.\n목록조회 후 다시 시도해주세요.");
		return;
	}
		
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	if(confirm("다운로드 하시겠습니까?")){
		$('.loading-div').show();
		form.appendTo("body").submit().remove();
		$('.loading-div').hide();		
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
	var ncsstyAt = $("input[name=ncsstyPassAt]").is(":checked");
	$("input[name=ncsstyPassAt]").val(ncsstyAt);
	$("input[name=pageIndex]").val(1);
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageVal]").val(pageUnit);
	
	var checkboxes = document.querySelectorAll('input[name=jdgmntGradChkBox]:checked');
	var values = Array.prototype.slice.call(checkboxes)
	                  .map(function(checkbox) {
	                    return checkbox.value;
	                  });
	var selectedValues = values.join(',');
	$("input[name=jdgmntGrad]").val(selectedValues);
	
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

/**
 * 조사일자 월 선택 초기화
 */
function fncSvyYearChange(){
	$("#monthView").val("").prop("selected","true");
}