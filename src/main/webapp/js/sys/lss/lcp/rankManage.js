$(document).ready(function(){
	window.$app = {};
	$app.upload = {};
	$('#searchKeyword').keypress(function(event) {
    	if (event.which == 13) {
    		fnSearch();
    	}
    });
	
	$(document).on({
    	"dragenter":function(e){
        	$(this).addClass("over");
        },
        "dragleave":function(e){
        	$(this).removeClass("over");
        },
        "dragover":function(e){
        	e.preventDefault();
        },
        "drop":function(e){
        	
//        	alert("droped!");
        	var files = e.originalEvent.dataTransfer.files;
        	if(files.length > 0){
        		var fd = new FormData($("#insertForm")[0]);
        		fd.append('file', files[0]);
        		//fd.append('svyType',$("#svyType").val());
        		var nm = files[0].name.split(".");
        		nm.pop();
        		var filenm = nm.join(".");
        		var size = files[0].size;
        		var sizeMb = size ? size/1024/1024 : 0;
        		
        		$app.upload.nm = nm;
        		$app.upload.size = size;
        		$app.upload.form = fd;
        		console.log("file name is ",filenm,", file size is ",size);
        		
        		var table_html = '<table summary="업로드할 파일의 정보를 출력합니다.">';
        		table_html += '<caption class="Hidden">업로드파일 목록</caption>';
        		table_html += '<colgroup>';
        		table_html += '<col style="width: 60%;">';
        		table_html += '<col style="width: 30%;">';
        		table_html += '<col style="width: 10%;">';
        		table_html += '</colgroup>';
        		table_html += '<thead>';
        		table_html += '<tr>';
        		table_html += '<th>파일명</th>';
        		table_html += '<th>파일용량</th>';
        		table_html += '<th></th>';
        		table_html += '</tr>';
        		table_html += '</thead>';
        		table_html += '<tbody>';
        		table_html += '</tbody>';
        		table_html += '</table>';
        		
        		var $table = $(table_html);
        		
        		var $tr = $("<tr></tr>");
        		
        		$tr.append("<td>".concat(nm,"</td>"));
        		$tr.append("<td>".concat(sizeMb.toFixed(2)," Mb</td>"));
        		
        		var $td = $("<td><button></button></td>");
        		//var $del = $("<button></button>");
        		$td.find("button").attr("type","button").attr("class","del-file-btn");
        		
        		$tr.append($td);
        		$table.find("tbody").append($tr);
        		
        		$td.find("button").on("click", function(){
        			$(".drag-div").addClass("drag-active").removeClass("BoardList");
        			$(".drag-div").empty().append('<p class="drag-msg noselect">파일을 드래그하세요.</p>');
        		});
        		
        		$(".drag-div").addClass("BoardList").removeClass("drag-active");
        		$(".drag-div").empty().append($table);
        	}
        	$(this).removeClass("over");
        	e.preventDefault();
        	e.stopPropagation();
        }
    },".drag-div");
});

/**
 * 랭크등록 팝업
 * @returns
 */
function fnInsertRankPopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			console.log($(this));
			$(this).load("insertLcpSvyRankPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 310,
		width: 400,
		title: "랭크등록 팝업"
	});
}

/**
 * 랭크데이터 등록
 * @returns
 */
function fnInsertLcpSvyRank() {
	var returnStatus = false;
	if($app.upload == null || $app.upload == undefined || $app.upload.form == undefined){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
	$("#insertForm *[name]").each(function(idx,elm){
		$app.upload.form.set($(elm).attr("id"),$(elm).val());
		if($(elm).val() == null || $(elm).val() == ""){
			var th = $(elm).parent().prev("th").text();
			alert(th == "" ? "입력되지 않은 정보가 있습니다." : th.concat(" 정보가 입력되지 않았습니다."));
			returnStatus = true;
			return false;
		}
	});
	
	if(returnStatus){
		return;
	}
	
	if(confirm("등록하시겠습니까?")) {
		
		$(".drag-div").append('<div class="progress"><div></div></div>');
		
		var jqXHR=$.ajax({
			url: $("#insertForm")[0].action,
			type:"POST",
			contentType:false,
	        processData: false,
	        cache: false,
	        data: $app.upload.form,
	        dataType:"json",
	        xhr: function(){
				var xhrobj = $.ajaxSettings.xhr();
	            if (xhrobj.upload) {
	            	xhrobj.upload.addEventListener('progress', function(event) {
	            		var percent = 0;
	                    var position = event.loaded || event.position;
	                    var total = event.total;
	                    if (event.lengthComputable) {
	                        percent = Math.ceil(position / total * 100);
	                    }
	                    
	                    setProgress(percent);
	            	}, false);
	            }
	            return xhrobj;
			},
	        beforeSend: function(){
	        	$(".sub-upload").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
	        },
	        success: function(data){
	        	if(data.message){
	        		alert(data.message);
	        	}
	        	if(data.status == "success"){
	        		$app.upload = null;
	        		window.location.reload();
	        	}else{
	        		$(".loading-div").remove();
	            	$(".progress").remove();
        		}
	        },
	        error: function(request, status, error){
	        	$(".loading-div").remove();
	        	$(".progress").remove();
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}
}

/**
 * 랭크 데이터 분석 1단계 고도값 추출(Zonal Statistics)
 * @returns
 */
function fnCreateLcpSvyRankStep1(){
	var jqXHR=$.ajax({
		url: "/sys/lss/lcp/rnk/createLcpSvyRankElevation.do",
		type:"POST",
		contentType:false,
        processData: false,
        cache: false,
        dataType:"json",
        beforeSend: function(){
        	$(".loading-icon").text("고도값 생성중입니다...");
        },
        success: function(data){
        	if(data.status == "fail"){
        		alert(data.message);
        		$(".loading-div").remove();
        	}else{
//        		if(data.message){
//	        		alert(data.message);
//	        	}
        		if(data.status == "success"){
        			$(".loading-icon").text("고도값 생성이 완료되었습니다...");
        			fnCreateLcpSvyRankStep2();
	        	}else{
	        		$(".loading-div").remove();
	            	$(".progress").remove();
        		}
        	}
        },
        error: function(request, status, error){
        	$(".loading-div").remove();
        	$(".progress").remove();
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 랭크 데이터 분석 2단계 경사도값 추출(Zonal Statistics)
 * @returns
 */
function fnCreateLcpSvyRankStep2(){
	var jqXHR=$.ajax({
		url: "/sys/lss/lcp/rnk/createLcpSvyRankSlope.do",
		type:"POST",
		contentType:false,
        processData: false,
        cache: false,
        dataType:"json",
        beforeSend: function(){
        	$(".loading-icon").text("경사도값 생성중입니다...");
        },
        success: function(data){
        	if(data.status == "fail"){
        		alert(data.message);
        		$(".loading-div").remove();
        	}else{
//        		if(data.message){
//	        		alert(data.message);
//	        	}
        		if(data.status == "success"){
        			$(".loading-icon").text("경사도값 생성이 완료되었습니다...");
        			fnCreateLcpSvyRankStep3();
	        	}else{
	        		$(".loading-div").remove();
	            	$(".progress").remove();
        		}
        	}
        },
        error: function(request, status, error){
        	$(".loading-div").remove();
        	$(".progress").remove();
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 랭크 데이터 분석 3단계 토심값 추출(Zonal Statistics)
 * @returns
 */
function fnCreateLcpSvyRankStep3(){
	var jqXHR=$.ajax({
		url: "/sys/lss/lcp/rnk/createLcpSvyRankSld.do",
		type:"POST",
		contentType:false,
        processData: false,
        cache: false,
        dataType:"json",
        beforeSend: function(){
        	$(".loading-icon").text("토심값 생성중입니다...");
        },
        success: function(data){
        	if(data.status == "fail"){
        		alert(data.message);
        		$(".loading-div").remove();
        	}else{
        		if(data.message){
	        		alert(data.message);
	        	}
        		if(data.status == "success"){
        			$(".loading-icon").text("토심값 생성이 완료되었습니다...");
	        		window.location.reload();
	        	}else{
	        		$(".loading-div").remove();
	            	$(".progress").remove();
        		}
        	}
        },
        error: function(request, status, error){
        	$(".loading-div").remove();
        	$(".progress").remove();
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 업로드 progress 갱신
 * @param per
 * @returns
 */
function setProgress(per){
	var progressBarwidth = per*$(".progress").width()/100;
	$(".progress").find("div").animate({width:progressBarwidth},10).html(per+"% ");
	
	console.log("업로드 진행율 : ".concat(per,"% "));
	if(per == 100){
		$(".sub-upload").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
	}
}

/**
 * 슈퍼맵 등록 및 수정테스트 팝업
 * @returns
 */
function fnUpsertLcpSvyComptTestPopup(){
	$('<div id="upsertDiv">').dialog({
		modal:true,
		open: function(){
			console.log($(this));
			$(this).load("upsertLcpSvyComptTestPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 370,
		width: 400,
		title: "슈퍼맵 등록 및 수정테스트 팝업"
	});
}

function fnUpsertLcpSvyComptTest() {
	//$("input[name=gid]").val(gid);
	if(confirm("등록하시겠습니까?")) {
		$("#upsertForm").ajaxForm({
			type: 'POST',
	        //url: "/sys/lss/bsc/sct/removeBscSvyCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	            } else if(data.status == "forward"){
	            	alert(data.message);
	            	location.href = data.url;
	            }else {
	                alert(data.message);
	            }
	    },
	    error: function(data) {
	    	alert(data.message);
	    	if(data.status == "forward"){
	    		location.href = data.url;
	    	}
	    }
		}).submit();
	}
}

function fnDeleteLcpSvyComptTestPopup(){
	$('<div id="deleteDiv">').dialog({
		modal:true,
		open: function(){
			console.log($(this));
			$(this).load("deleteLcpSvyComptTestPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 370,
		width: 400,
		title: "슈퍼맵 삭제테스트 팝업"
	});
}

function fnDeleteLcpSvyComptTest() {
	//$("input[name=gid]").val(gid);
	if(confirm("삭제하시겠습니까?")) {
		$("#deleteForm").ajaxForm({
			type: 'POST',
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
	                alert(data.message);
	            } else if(data.status == "forward"){
	            	alert(data.message);
	            	location.href = data.url;
	            }else {
	                alert(data.message);
	            }
	    },
	    error: function(data) {
	    	alert(data.message);
	    	if(data.status == "forward"){
	    		location.href = data.url;
	    	}
	    }
		}).submit();
	}
}

/**
 * 랭크데이터 다운로드
 * @returns
 */
function fncDownloadLcpSvyRank(){
	if(confirm("랭크데이터를 다운로드하시겠습니까?")){
		$(".loading-div").show();
		var xhr = new XMLHttpRequest();
		var formdata = new FormData();
		
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
				//this.response is what you're looking for
				console.log(this.response, typeof this.response);
				var a = document.createElement("a");
				var url = URL.createObjectURL(this.response)
				a.href = url;
				a.download = filename;
				document.body.appendChild(a);
				a.click();
				window.URL.revokeObjectURL(url);
			}
		};
		xhr.onerror = function(){
			$(".loading-div").hide();
			alert("다운로드를 실패하였습니다.");
		};
		xhr.open('POST', '/sys/lss/lcp/rnk/selectDownloadLcpSvyRank.do');
		xhr.responseType = 'blob';
		xhr.send(formdata);
	}
}

/**
 * 행정구역 시군구 조회 ajax
 * @returns
 */
function fncAdministCtprvnChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#svySggView").empty().append("<option value>선택하세요</option>");
		$("#svyEmdView").empty().append("<option value>선택하세요</option>");
		$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
        			$("#svyEmdView").empty().append("<option value>선택하세요</option>");
        			$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
 * 행정구역 읍면동 조회 ajax
 * @returns
 */
function fncAdministSignguChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#svyEmdView").empty().append("<option value>선택하세요</option>");
		$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
        			$("#svyEmdView").empty().append("<option value>선택하세요</option>");
        			$("#svyRiView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svyEmdView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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
function fncAdministEmdChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
        			$("#svyRiView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svyRiView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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