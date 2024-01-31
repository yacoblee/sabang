$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
    
    //dialog drag event
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
        		var fd = new FormData();
        		fd.append('file', files[0]);
        		//fd.append('svyType',$("#svyType").val());
        		var nm = files[0].name.split(".")[0];
        		var size = files[0].size;
        		var sizeMb = size ? size/1024/1024 : 0;
        		
        		$app.upload.nm = nm;
        		$app.upload.size = size;
        		$app.upload.form = fd;
        		console.log("file name is ",nm,", file size is ",size);
        		
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
    
    $(document).on("change","#svyType",function(){
    	
    });
	//fncGetYear();
});

/**
 * 대상지목록 상세화면
 * @param id
 * @returns
 */
function fncStripLandDetail(id) {
	$("input[name=id]").val(id);
	$("#listForm").attr("action","/sys/fck/apr/sld/selectStripLandDetail.do");
	$("#listForm").submit();
}

/**
 * 대상지 엑셀 업로드 팝업
 * @returns
 */
function fncUploadStripLandDialog() {
	
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			// 모달 오버레이 설정
//			$(".ui-widget-overlay").css({
//				opacity: 0.5,
//				filter: "Alpha(Opacity=50)",
//				backgroundColor: "black"
//			});
			$(this).load("/sys/fck/apr/sld/stripLandExcelPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 330,
		width: 400,
		title: "대상지 엑셀등록"
	});
}

/**
 * 대상지 엑셀 업로드 저장
 * @returns
 */
function fncUploadStripLandInsert(){
	if($app.upload == null || $app.upload == undefined){
		alert("파일이 선택되지 않았습니다.");
		return null;
	}
	
	if($app.upload.form.get("svyType")){
		$app.upload.form.set('svyType',$("#svyType").val());
	}else{
		$app.upload.form.append('svyType',$("#svyType").val());
	}
	
	$(".drag-div").append('<div class="progress"><div></div></div>');
	
	var jqXHR=$.ajax({
		url:"/sys/fck/apr/sld/uploadStripLandExcel.do",
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
        	//$(".sub-upload").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
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
 * 대상지 등록화면
 * @returns
 */
function fncAddStripLandInsertView() {
	location.href="/sys/fck/apr/sld/registStripLandView.do";
}

/**
 * 대상지 등록
 * @returns
 */
function fncStripLandInsert() {
//	alert("아직 지원되지 않는 기능입니다.");
//	return false;
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
		
		var type = $("#type option:checked");
		var region1 = $("#region1 option:checked");
		var region2 = $("#region2 option:checked");
		var sd = $("#sd option:checked");
		var sgg = $("#sgg option:checked");
		var emd = $("#emd option:checked");
		var ri = $("#ri option:checked");
//		var lat = $("#latDegree").val()+"° "+$("#latMinute").val()+"' "+$("#latSecond").val()+"\"";
//		var lon = $("#lonDegree").val()+"° "+$("#lonMinute").val()+"' "+$("#lonSecond").val()+"\"";
		
		$("input[name=typeNm]").val(type.val()==''?"":type.text());
		$("input[name=region1Nm]").val(region1.val()==''?"":region1.text());
		$("input[name=region2Nm]").val(region2.val()==''?"":region2.text());
		$("input[name=sdNm]").val(sd.val()==''?"":sd.text());
		$("input[name=sggNm]").val(sgg.val()==''?"":sgg.text());
		$("input[name=emdNm]").val(emd.val()==''?"":emd.text());
		$("input[name=riNm]").val(ri.val()==''?"":ri.text());
//		$("input[name=lat]").val(lat);
//		$("input[name=lon]").val(lon);
//		
        if(!validateFckAprStripLand(form)){
        	return false;
        }else{
			$("#listForm").ajaxForm({
				type: 'POST',
	        	url: $("#listForm")[0].action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/sys/fck/apr/sld/selectStripLandList.do";
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
 * 대상지 삭제
 * @param id
 * @returns
 */
function fncDeleteStripLand(id){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=id]").val(id);
		$("#listForm").ajaxForm({
			type: 'POST',
        	url: "/sys/fck/apr/sld/removeFckAprStripLand.do",
        	dataType: "json",
        	success: function(data) {
        		if (data.status == "success") {
                	alert(data.message);
                	location.href="/sys/fck/apr/sld/selectStripLandList.do";
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
 * 대상지 수정화면
 * @returns
 */
function fncUpdateStripLandView(){
	$("#listForm").attr("action","/sys/fck/apr/sld/updateFckAprStripLandView.do");
	$("#listForm").submit();
}

/**
 * 대상지 수정
 * @returns
 */
function fncStripLandUpdate(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
		
		var type = $("#type option:checked");
		var region1 = $("#region1 option:checked");
		var region2 = $("#region2 option:checked");
		var sd = $("#sd option:checked");
		var sgg = $("#sgg option:checked");
		var emd = $("#emd option:checked");
		var ri = $("#ri option:checked");
		
		$("input[name=typeNm]").val(type.val()==''?"":type.text());
		$("input[name=region1Nm]").val(region1.val()==''?"":region1.text());
		$("input[name=region2Nm]").val(region2.val()==''?"":region2.text());
		$("input[name=sdNm]").val(sd.val()==''?"":sd.text());
		$("input[name=sggNm]").val(sgg.val()==''?"":sgg.text());
		$("input[name=emdNm]").val(emd.val()==''?"":emd.text());
		$("input[name=riNm]").val(ri.val()==''?"":ri.text());
		
        if(!validateFckAprStripLand(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
	        	url: $("#listForm")[0].action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/sys/fck/apr/sld/selectStripLandList.do";
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
 * 관할2 조회 ajax
 * @param event
 * @returns
 */
function fncRegionChange(event,se){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		if(se == 'list'){
			$("#region2View").empty().append("<option value>전체</option>");
			return false;
		}else{
			$("#region2").empty().append("<option value>전체</option>");
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
						$("#region2View").empty().append("<option value>전체</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#region2View").append("<option value=\"".concat(item.code,"\">").concat(item.codeNm,"</option>"));
	        			});	
					}else{
						$("#region2").empty().append("<option value>전체</option>");
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
			$("#sggView").empty().append("<option value>전체</option>");
			$("#emdView").empty().append("<option value>전체</option>");
			$("#riView").empty().append("<option value>전체</option>");
			return false;
		}else{
			$("#sgg").empty().append("<option value>전체</option>");
			$("#emd").empty().append("<option value>전체</option>");
			$("#ri").empty().append("<option value>전체</option>");
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
						$("#sggView").empty().append("<option value>전체</option>");
	        			$("#emdView").empty().append("<option value>전체</option>");
	        			$("#riView").empty().append("<option value>전체</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#sggView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}else{
						$("#sgg").empty().append("<option value>전체</option>");
	        			$("#emd").empty().append("<option value>전체</option>");
	        			$("#ri").empty().append("<option value>전체</option>");
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
			$("#emdView").empty().append("<option value>전체</option>");
			$("#riView").empty().append("<option value>전체</option>");
			return false;
		}else{
			$("#emd").empty().append("<option value>전체</option>");
			$("#ri").empty().append("<option value>전체</option>");
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
						$("#emdView").empty().append("<option value>전체</option>");
	        			$("#riView").empty().append("<option value>전체</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#emdView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}else{
						$("#emd").empty().append("<option value>전체</option>");
	        			$("#ri").empty().append("<option value>전체</option>");
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
			$("#riView").empty().append("<option value>전체</option>");
			return false;
		}else{
			$("#ri").empty().append("<option value>전체</option>");
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
						$("#riView").empty().append("<option value>전체</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#riView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
					}else{
						$("#ri").empty().append("<option value>전체</option>");
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