$(document).ready(function() {
	window.$app = {};
    $('#searchKeyword, #workNmView').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
    
});

/**
 * 대상지목록 상세화면
 * @param id
 * @returns
 */
function fncAnalStripLandDetail(mstId,sldId,analId) {
	$("input[name=mstId]").val(mstId);
	$("input[name=sldId]").val(sldId);
	$("input[name=analId]").val(analId);
	$("#listForm").attr("action","/sys/vyt/ecb/als/selectAnalStripLandDetail.do");
	$("#listForm").submit();
}

/**
 * 분석결과 다운로드
 * @param mstId
 * @param sldId
 * @returns
 */
function fncDownloadAnalAll(mstId,sldId,analId){
	$(".loading-div").show();
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	formdata.append("mstId",mstId);
	formdata.append("sldId",sldId);
	formdata.append("analId",analId);
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4){
			if(this.status == 200){
				$(".loading-div").hide();
				var filename = "";
				var disposition = xhr.getResponseHeader('Content-Disposition');
				if (disposition && disposition.indexOf('attachment') !== -1) {
					var filenameRegex = /filename[^;=\n]*=UTF-8''((['"]).*?\2|[^;\n]*)/;
					var matches = filenameRegex.exec(disposition);
					if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
				}
				//this.response is what you're looking for
				console.log(this.response, typeof this.response);
				var a = document.createElement("a");
				var url = URL.createObjectURL(this.response)
				a.href = url;
				a.download = filename;
				document.body.appendChild(a);
				a.click();
				window.URL.revokeObjectURL(url);
			}else{
				$(".loading-div").hide();
				alert("다운로드를 실패하였습니다.");
			}
		}
	};
	xhr.onerror = function(){
		$(".loading-div").hide();
		alert("다운로드를 실패하였습니다.");
	};
	xhr.open('POST', '/sys/vyt/ecb/als/selectDownloadAnalAll.do');
	xhr.responseType = 'blob';
	xhr.send(formdata);
}

/**
 * 행정구역 시군구 조회 ajax
 * @returns
 */
function fncAdministCtprvnChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#workSggView").empty().append("<option value>전체</option>");
		$("#workEmdView").empty().append("<option value>전체</option>");
		$("#workRiView").empty().append("<option value>전체</option>");
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneSigngu.do",
		type:"POST",
        data: {sdCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#workSggView").empty().append("<option value>전체</option>");
        			$("#workEmdView").empty().append("<option value>전체</option>");
        			$("#workRiView").empty().append("<option value>전체</option>");
        			$.each(list.result,function(idx,item){
        				$("#workSggView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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
 * 행정구역 읍면동 조회 ajax
 * @returns
 */
function fncAdministSignguChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#workEmdView").empty().append("<option value>전체</option>");
		$("#workRiView").empty().append("<option value>전체</option>");
		return false;
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneEmd.do",
		type:"POST",
        data: {sggCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#workEmdView").empty().append("<option value>전체</option>");
        			$("#workRiView").empty().append("<option value>전체</option>");
        			$.each(list.result,function(idx,item){
        				$("#workEmdView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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
 * 행정구역 리 조회 ajax
 * @returns
 */
function fncAdministEmdChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#workRiView").empty().append("<option value>전체</option>");
		return false;
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneRi.do",
		type:"POST",
        data: {emdCode:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#workRiView").empty().append("<option value>전체</option>");
        			$.each(list.result,function(idx,item){
        				$("#workRiView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
        			});
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
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

function fnPage(){
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