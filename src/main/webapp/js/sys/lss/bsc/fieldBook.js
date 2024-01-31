$(document).ready(function(){
	window.$app = {};
	$app.upload = {};
	$('#searchKeyword').keypress(function(event) {
    	if (event.which == 13) {
    		fnSearch();
    	}
    });
	$('#svyIdView').keypress(function(event) {
    	if (event.which == 13) {
    		fnSubSearch();
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
	
	// input enter event
	$('#mst_partname,#id,#mst_create_user').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
});

/**
 * 공유방 조사데이터 업로드
 * @returns
 */
function fnInsertFieldBookView(){

	var form = $("<form></form>");
	$(form).attr("id","listForm").attr("name","itemVO").attr("method","post").attr("action","/ext/lss/bsc/upload.do").attr("enctype","multipart/form-data");
	var btn = "<button type=\"button\" class=\"add-btn fl\" target=\"_blank\" style=\"margin:16px 8px;\" onclick=\"javascript:fnInsertFieldBookData();\">등록</button>";
	
	var html = "<input type=\"button\" value=\"파일추가\" onclick=\"javascript:fnAddFile();\">";
	html += "<div id=\"fileDiv\"></div>"
//	form.append($("<input/>",{type:"file",id:"file",name:"file",multiple:"multiple",value:""}));
	form.append($("<input/>",{type:"hidden",name:"data_for_update",value:$('input[name=getData_for_update]').val().toString().replace(/\\(.)/mg, "$1")}));
	form.append($("<input/>",{type:"hidden",name:"mst_pwd",value:$('input[name=getMst_pwd]').val()}));
	form.append($("<input/>",{type:"hidden",name:"last_update",value:$('input[name=getLast_update]').val()}));
	form.append($("<input/>",{type:"hidden",name:"MST_ID",value:$('input[name=getMst_id]').val()}));
	form.append($("<input/>",{type:"hidden",name:"LOGIN_ID",value:$('input[name=getLogin_id]').val()}));
	form.append($("<input/>",{type:"hidden",name:"FID",value:"6"}));
	form.append($("<input/>",{type:"hidden",name:"LON",value:"236254.624402"}));
	form.append($("<input/>",{type:"hidden",name:"LAT",value:"532583.036471"}));
	form.append($("<input/>",{type:"hidden",name:"DATA",value:"POINT(236254.624402 532583.036471)"})  );
	form.append($("<input/>",{type:"hidden",name:"KEYWORD",value:$('input[name=getLabel_no]').val()}));
	form.append($("<input/>",{type:"hidden",name:"LABEL",value:$('input[name=getLabel_no]').val()}));
	form.append($("<input/>",{type:"hidden",name:"STYLE",value:"BRUSH(bc:#7a55478f,fc:#7a5547ff);SYMBOL(c:#7a5547ff,o:#7a5547ff,s:8)"}));
	form.append($("<input/>",{type:"hidden",name:"MEMO",value:fnConvertJSON({"FID":13153,"FILE_NCX":"\/storage\/emulated\/0\/가온지도\/도면\/[산사태]산림조합중앙회-대구경북.ncx","조사ID":"S072128","조사유형":"산사태","관할1":"03. 대구","관할2":"달성군","시도":"대구광역시","시군구":"달성군","읍면동":"논공읍","리":"본리리","지번":"산72-5임","모암_측정값":"퇴적암","사진":["gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0001.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0002.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0003.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0004.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0005.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0006.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0007.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0008.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0009.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0010.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0011.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0012.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0013.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0014.jpg","gimg:\/\/\/[산사태]산림조합중앙회-대구경북.ncx\/S072128.0015.jpg"],"연번":"","조사일":"2021-08-30","조사자":"이문석","시점좌표_X":332695.9878746378,"시점좌표_Y":350540.63843040646,"끝점좌표_X":332593.6422363573,"끝점좌표_Y":350535.72523870406,"시점고도":"91","끝점고도":"96","경사길이_측정값":"102","경사도_측정값":"25","점수계":"66","보호대상":"재산","사면형":"복합","임상":"무입목지","모암":"퇴적암","보호대상_측정값":"재산","사면형_측정값":"복합","임상_측정값":"무입목지","보호대상_점수":"5","경사길이_점수":"17","경사도_점수":"15","사면형_점수":"10","임상_점수":"17","모암_점수":"2","필요성":"필요","주요위험성":"공장배후사면으로 붕괴의 흔적이발견되었으며 강우시 토사의 의한 하부 피해가능성 존재","조사자의견":"급경사산지와 구간 침식 붕괴발생","컬러":-638932,"완료":"완료"})}));
	form.append($("<input/>",{type:"hidden",name:"TAG1",value:"_TAG1"}));
	form.append($("<input/>",{type:"hidden",name:"TAG2",value:"_TAG2"}));
	form.append($("<input/>",{type:"hidden",name:"REG_DATE",value:"2021/08/04 18:24:40"}));
	form.append($("<input/>",{type:"hidden",name:"UPD_DATE",value: $("input[name=getLast_update]").val()}));
	form.append($("<input/>",{type:"hidden",name:"ATTR",value:"[{\"NAME\":\"no\",\"VALUE\":\"5-0444\"},{\"NAME\":\"x\",\"VALUE\":\"149190\"},{\"NAME\":\"y\",\"VALUE\":\"46228\"},{\"NAME\":\"읍면동\",\"VALUE\":\"해안동\"},{\"NAME\":\"리\",\"VALUE\":\"\"},{\"NAME\":\"지번\",\"VALUE\":\"2046-3\"},{\"NAME\":\"경급\",\"VALUE\":\"22\"}]"}));
	form.append(html);
	form.append(btn);
	form.appendTo("body");
}

/**
 * 공유방 조사데이터 업로드 ajaxSubmit
 * @returns
 */
function fnInsertFieldBookData() {
/*	var options = {
		type: 'POST',
		success:function(data){
			var item = JSON.parse(data);
			console.log(item);
			alert("업로드 되었습니다.");
		},
		error: function(data){
			console.log(data);
	    }
	}
	$("#listForm").ajaxSubmit(options);*/
	$("#listForm").submit();
}

/**
 * 파일동적추가 CNT
 * @returns
 */
var cnt = 0;
function fnAddFile(){
	$("#fileDiv").append("<br><input type='file' name='file"+cnt+"' />");
	cnt++;
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
	
	var svyYear = $("select[name=svy_yearView]").val();
	if(svyYear != null && svyYear != '' && svyYear != undefined) $("input[name=svy_year]").val(svyYear);
	
		$("#listForm").attr("action","/sys/lss/bsc/fbk/selectFieldBookList.do");
			var params = $("#listForm *[name*=View]");
		$.each(params,function(idx,elm){
			var val = $(elm).val();
			var hidden = $(elm).attr("name").replace("View","");
			$("input[name=".concat(hidden,"]")).val(val);
		});
		$("#listForm").submit();
}

/**
 * 공유방 등록팝업
 * @returns
 */
function fnInsertCnrsSpcePopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertCnrsSpcePopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 700,
		width: 600,
		title: "기초조사 공유방 등록"
	});
}

/**
 * 공유방 등록
 * @returns
 */
function fnInsertCnrsSpce() {
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
function fnFieldBookDetail(id) {
	$("input[name=mstId]").val(id);
	
	var schid = $("input[name=id]").val();
	
	if(schid == null){
		$("input[name=id]").val('');
	}
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#listForm").attr("action","/sys/lss/bsc/fbk/selectFieldBookDetail.do");
	$("#listForm").submit();
	
}

/**
 * 대상지등록
 * @returns
 */
function fnInsertCnrsSpceSldPopup(mst_id) {
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertCnrsSpceSldPopup.do?mst_id="+mst_id)
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 300,
		width: 400,
		title: "기초조사 대상지 등록"
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
function fnInsertCnrsSpceSld(mst_id){
	var returnStatus = false;
	if($app.upload == null || $app.upload == undefined || $app.upload.form == undefined){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
	$("#insertForm *[name]").each(function(idx,elm){
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
			data: {"mst_id": mst_id},
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
 * 대상지 등록 팝업 닫기
 * @returns
 * @Description
 */
function fnCloseInsertCnrsSpce(){
	window.location.reload();
}


/**
 * 조사데이터 수정 페이지
 * @returns
 * @Description
 */
function fnUpdateCnrsSpceView(id) {
	$("input[name=id]").val(id);
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/lss/bsc/fbk/updateFieldBookView.do");
	$("#listForm").submit();
}

/**
 * 공유방 조사데이터 삭제
 * @returns
 * @Description
 */
function fnUpdateCnrsSpce(label){
	var mst_id = $("input[name=mstId]").val();
	var label = [];
	
	$("input[name=fieldBook_check]:checked").each(function() {
		label.push($(this).val());
	})
	
	if(label.length == 0) {
		alert("삭제항목을 선택해주세요.");
		return;
	}
	
	if(confirm("선택항목을 삭제하시겠습니까?")) {		
		$.ajax({
			url:"/sys/lss/bsc/fbk/updateCnrsSpceItem.do",
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
 * 공유방 조사데이터 삭제
 * @returns
 * @Description
 */
function fnDeleteCnrsSpce(id){
	if(confirm("공유방을 삭제하시겠습니까?")) {
		if(confirm("공유방삭제시 조사된 정보도 함께 삭제가 됩니다.\n그래도 삭제하시겠습니까?")){
			$.ajax({
				url:"/sys/lss/bsc/fbk/deleteCnrsSpce.do",
				type:"POST",
		        data: {
		        	mstId: id
		        },
		        dataType:"json",
		        success:function(data){
		        	if(data.status == "success"){
		        		alert(data.message);
		        		location.href = "/sys/lss/bsc/fbk/selectFieldBookList.do"
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

/**
 * 공유방 상세 검색
 * @returns
 */
function fnSubSearch(){	
	$("#searchForm > input[name=pageSubIndex]").val(1);
	
	$("#searchForm").attr("action","/sys/lss/bsc/fbk/selectFieldBookDetail.do");
	
	var params = $("#searchForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	
	var hiddens = $("#searchForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#searchForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#searchForm").submit();
}


/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	
	var form = $("<form></form>").attr("action","/sys/lss/bsc/fbk/selectFieldBookList.do").attr("method","post");
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}


 