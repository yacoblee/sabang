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
        			$app.upload = {};
        		});
        		
        		$(".drag-div").addClass("BoardList").removeClass("drag-active");
        		$(".drag-div").empty().append($table);
        	}
        	$(this).removeClass("over");
        	e.preventDefault();
        	e.stopPropagation();
        }
    },".drag-div");
	
	// input enter event
	$('#workNmView,#creatUserView').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
});

/**
 * 사업 등록팝업
 * @returns
 */
function fnInsertWorkPopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertVytEcbWorkPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 500,
		width: 600,
		title: "사방사업 타당성평가 사업 등록"
	});
}

/**
 * 사업 등록
 * @returns
 */
function fnInsertWork() {
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
			//url:"/sys/fck/apr/sld/uploadStripLandExcel.do",
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
	        	//$(".sub-upload").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
	        },
	        success: function(data){
				if(data.nnt == 'fail') {
					alert("공백으로 입력된 내용이 있습니다. \n다시 한 번 확인해주세요.");
					$(".loading-div").remove();
	            	$(".progress").remove();
				}else{
					if(data.cnt == 'fail') {
						alert("중복된 사업지구명이 존재합니다. \n다시 한 번 확인해주세요.");
		        		$(".loading-div").remove();
		            	$(".progress").remove();
		            	$("input[name='mst_partname']").text('');
						return false;
		        	}else{
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
		$(".sub-upload div.BoardDetail").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
	}
}

/**
 * 조사데이터 상세조회
 * @param id
 * @returns
 */
function fnWorkDetail(id) {
	$("input[name=id]").val(id);
	var hiddens = $("#listForm").find("input[name*=sch]");
	
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/ecb/sld/selectVytEcbWorkDetail.do");
	$("#listForm").submit();
}

/**
 * 대상지등록
 * @returns
 */
function fnInsertWorkSldPopup(mst_id) {
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertVytEcbWorkSldPopup.do?id="+mst_id)
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 320,
		width: 400,
		title: "사업 대상지 등록"
	});
}

/**
 * 전체 체크박스 선택
 * @returns
 * @Description
 */
function fnSelectAllCheck(){

	window.location.pathname.indexOf("updateFieldBookView") > -1 ? flag="U" : flag="I"
	if(flag == "U") {
		if($("#fieldBook_all_check").prop("checked")) {
			$('.fieldBook_check').prop('checked', true);
		} else {
			$('.fieldBook_check').prop('checked', false);
		}
	} else {
		if($("#sld_all_check").prop("checked")) {
			$('.sld_check').prop('checked', true);
		} else {
			$('.sld_check').prop('checked', false);
		}
	}
}

/**
 * 대상지 추가
 * @returns
 * @Description
 */
function fnInsertWorkSld(id){
	if($app.upload == null || $app.upload == undefined || $app.upload.form == undefined){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
	if(confirm("등록하시겠습니까?")) {
		$(".drag-div").append('<div class="progress"><div></div></div>');
		
		var jqXHR=$.ajax({
			url: $("#insertForm")[0].action,
			type:"POST",
			data: {"id": id},
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
 * 사업 조사데이터 수정 페이지
 * @returns
 * @Description
 */
function fnUpdateWorkView(id) {
	$("input[name=id]").val(id)
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/ecb/sld/updateWorkView.do");
	$("#listForm").submit();
}

/**
 * 사업 조사데이터 수정 페이지 조사데이터 삭제
 * @returns
 * @Description
 */
function fnUpdateWork(label){
	var mst_id = $("input[name=id]").val();
	var label = [];
	
	$("input[name=fieldBook_check]:checked").each(function() {
		label.push(parseInt($(this).val()));
	})
	
	if(label.length == 0) {
		alert("삭제항목을 선택해주세요.");
		return;
	}
	
	if(confirm("선택항목을 삭제하시겠습니까?")) {
		$.ajax({
			url:"/sys/vyt/ecb/sld/updateWorkItem.do",
			type:"POST",
        	data: {
        		label: label,
	        	mst_id: mst_id
    	    },
        	dataType:"json",
	        success:function(data){
    	    	if(data.status == "success"){
        			alert(data.message);
        			window.location.reload();
	        	} else {
    	    		alert(data.message);
        		}
	        },
    	    error: function(data){
        		alert(data.message);
	        }
		});
	}
}

/**
 * 사업정보 삭제
 * @returns
 * @Description
 */
function fnDeleteWork(id){
	if(confirm("사업을 삭제하시겠습니까?")) {
		if(confirm("사업삭제시 평가정보도 함께 삭제가 됩니다.\n그래도 삭제하시겠습니까?")){
			$.ajax({
				url:"/sys/vyt/ecb/sld/deleteVytEcbWork.do",
				type:"POST",
		        data: {
		        	id: id
		        },
		        dataType:"json",
		        success:function(data){
		        	if(data.status == "success"){
		        		alert(data.message);
		        		window.location.href = "/sys/vyt/ecb/sld/selectVytEcbWorkList.do"
		        	} else {
		        		alert(data.message);
		        	}
		        },
		        error: function(data){
		        	alert(data.message);
		        }
			});
		}
	}
}

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/vyt/ecb/sld/selectVytEcbWorkList.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}
/**
 * 검색
 * @returns
 * @Description
 */
function fnSearch(){
	$("input[name=pageIndex]").val(1);
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageUnit]").val(pageUnit);
	
	$("#listForm").attr("action","/sys/vyt/ecb/sld/selectVytEcbWorkList.do");
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}