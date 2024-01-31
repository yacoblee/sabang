$(document).ready(function(){
	window.$app = {};
	$app.upload = {};
	$('#searchKeyword').keypress(function(event) {
    	if (event.which == 13) {
    		fnSearch();
    	}
    });
	$('#svyidView').keypress(function(event) {
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
	
	//공유방 권한수정 부서목록 팝업 부서 체크박스 체인지 이벤트
	$(document).on("change",".check1",function(){
		var chk1 = $(this).attr("id");
		var mberDiv = "."+chk1.replace("chk","deptMber").concat(" input[type=checkbox]");
		if($(this).is(":checked")){
			$(mberDiv).prop("checked", true);
		}else{
			$(mberDiv).prop("checked", false);
		}
	});
	
	//공유방 권한수정 부서목록 팝업 회원 체크박스 체인지 이벤트
	$(document).on("change",".check2",function(){
		var mberDiv = $(this).parent("div").parent("div");//회원 부모 div
		var deptChkId = "#"+$(mberDiv).attr("class").replace("deptMber","chk");//부서체크박스아이디
		var checkNum = $(mberDiv).find("input[type=checkbox]").length;//해당부서 회원수
		var checkedNum = $(mberDiv).find("input[type=checkbox]:checked").length;//해당부서 체크된 회원수
		if($(this).is(":checked")){
			if(checkNum === checkedNum){
				$(deptChkId).prop("checked",true);
			}
		}else{
			if(checkedNum < checkNum){
				$(deptChkId).prop("checked",false);
			}
		}
	});
	
	// input enter event
	$('#mst_partname,#id,#mst_create_user').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
});

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
		height: 800,
		width: 600,
		title: "외관점검 공유방 등록"
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
	
//	$("#insertForm *[name]").each(function(idx,elm){
//		$app.upload.form.set($(elm).attr("id"),$(elm).val());
//		if($(elm).val() == null || $(elm).val() == ""){
//			var th = $(elm).parent().prev("th").text();
//			alert(th == "" ? "입력되지 않은 정보가 있습니다." : th.concat(" 정보가 입력되지 않았습니다."));
//			returnStatus = true;
//			return false;
//		}
//	});
	
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
function fnFieldBookDetail(id) {
	$("input[name=mstId]").val(id);
	$("input[name=smid]").val('');
	
	var schid = $("input[name=id]").val();
	
	if(schid == null){
		$("input[name=id]").val('');
	}
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/fck/apr/fbk/selectFieldBookDetail.do");
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
		height: 500,
		width: 400,
		title: "외관점검 대상지 등록"
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
	
//	if(confirm("대상지항목을 등록하시겠습니까?")) {
//		$.ajax({
//			url:"/sys/fck/apr/fbk/insertCnrsSpceSld.do",
//			type:"POST",
//	        data: {	        	
//	        	mst_id: mst_id
//	        },
//	        dataType:"json",
//	        success:function(data){
//	        	if(data.status == "success"){
//	        		alert(data.message);
//	        		window.location.reload();
//	        	} else {
//	        		alert(data.message);
//	        	}
//	        },
//	        error: function(data){
//	        	alert(data.message);
//	        }
//		});
//	}
	
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
		
//		$app.upload.form.set("mst_corpname",$("#mst_corpname").val());
//		$app.upload.form.set("mst_partname",$("#mst_partname").val());
//		$app.upload.form.set("mst_create_user",$("#mst_create_user").val());
//		$app.upload.form.set("mst_password",$("#mst_password").val());
//		$app.upload.form.set("mst_adminpwd",$("#mst_adminpwd").val());
//		$app.upload.form.set("mst_readpwd",$("#mst_readpwd").val());
//
//		if($app.upload.form.get("svyType")){
//			$app.upload.form.set('svyType',$("#svyType").val());
//		}else{
//			$app.upload.form.append('svyType',$("#svyType").val());
//		}
		
		
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
 * 조사데이터 수정 페이지
 * @returns
 * @Description
 */
function fnUpdateCnrsSpceView(id) {
	$("input[name=id]").val(id);
	$("#listForm").attr("action","/sys/fck/apr/fbk/updateFieldBookView.do");
	$("#listForm").submit();
}

/**
 * 공유방 조사데이터 삭제
 * @returns
 * @Description
 */
function fnUpdateCnrsSpce(label){
	var mst_id = $("input[name=id]").val();
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
			url:"/sys/fck/apr/fbk/updateCnrsSpceItem.do",
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
				url:"/sys/fck/apr/fbk/deleteCnrsSpce.do",
				type:"POST",
		        data: {
		        	id: id
		        },
		        dataType:"json",
		        success:function(data){
		        	if(data.status == "success"){
		        		alert(data.message);
		        		window.location.href = "/sys/fck/apr/fbk/selectFieldBookList.do"
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
 * 공유방 단건 상세조회 
 * @returns
 * @Description
 */
function fnSelectFieldBookDetailOne(smid){
	$("input[name=smid]").val(smid);
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/fck/apr/fbk/selectFieldBookDetailOne.do").attr("method", "post");
	$("#listForm").submit();
}

/**
 * 공유방 단건 수정 페이지 이동
 * @returns
 * @Description
 */
function fnSelectFieldBookUpdtOne(smid){
//	var hiddens = $("#listForm").find("input[type=hidden]");
//	$.each(hiddens,function(idx,elm){
//		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
//	});
//	
////	$("input[name=smid]").val(smid);
//	$("#listForm").attr("action","/sys/fck/apr/fbk/updateFieldBookViewOne.do").attr("method", "post");
//	$("#listForm").submit();
	
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/fck/apr/fbk/updateFieldBookViewOne.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($("<input/>", {type: "hidden", name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.append($("<input/>", {type: "hidden", name: "smid", value:smid }));
	form.append($("<input/>", {type: "hidden", name: "mstId", value:$("input[name=mstId]").val() }));
	
	form.appendTo("body").submit().remove();
}

/**
 * 공유방 단건 수정
 * @returns
 * @Description
 */
function fnUpdateFieldBookDetailOne(smid){
	var smid = $('input[name=smid]').val(smid);
	
	var e = $('#svyType').val();
	
	if(e == '사방댐 외관점검'){
		$('#mrngTable').remove();
		$('#mtcTable').remove();
		
		$("#ectrsTable #svyType").val(e).prop("selected", true);
	}else if(e == '계류보전 외관점검'){
		$('#ectrsTable').remove();
		$('#mtcTable').remove();
		
		$("#mrngTable #svyType").val(e).prop("selected", true);
	}else if(e == '산지사방 외관점검'){
		$('#ectrsTable').remove();
		$('#mrngTable').remove();
	}
	
	var svyLatD = parseInt($('input[name=svyLatD]').val());
	var svyLatM = parseInt($('input[name=svyLatM]').val());
	var svyLatS = parseFloat($('input[name=svyLatS]').val());
	
	var svyLonD = parseInt($('input[name=svyLonD]').val());
	var svyLonM = parseInt($('input[name=svyLonM]').val());
	var svyLonS = parseFloat($('input[name=svyLonS]').val());
	
	var bpx = svyLonD+(svyLonM/60)+(svyLonS/3600);
	if(!isNaN(bpx)){
		bpx = svyLonD+(svyLonM/60)+(svyLonS/3600);
	}else{
		bpx = "";
	}
	$("#listForm").append($('<input/>', {type: 'hidden', name: "bpx", value:bpx }));	
	
	var bpy = svyLatD+(svyLatM/60)+(svyLatS/3600);
	if(!isNaN(bpy)){
		bpy = svyLatD+(svyLatM/60)+(svyLatS/3600);
	}else{
		bpy = "";
	}
	$("#listForm").append($('<input/>', {type: 'hidden', name: "bpy", value:bpy }));
	
	
	if(bpx == '' || bpy == '' || isNaN(bpx) || isNaN(bpy)){
		alert("좌표가 입력되지 않았습니다.");
		return false;
	}
	
	
	if(confirm("수정하시겠습니까?")) {
		$.ajax({
			url: "/sys/fck/apr/fbk/updateFieldBookDetailOne.do",
			data:$("#listForm").serialize(),
	        dataType: "json",
			type:"POST",
			async:true,
	        success:function(data){
	        	if(data.status == "success"){
	        		alert(data.message);
	        		fnList("/sys/fck/apr/fbk/selectFieldBookList.do");
	        		//window.location.href = "/sys/fck/apr/fbk/selectFieldBookDetail.do?id="+mstId;
	        	} else {
	        		alert(data.message);
	        	}
	        },
	        error: function(data){
	        	alert(data.message);
	        }
		})
	}
}

/**
 * 공유방 단건 삭제
 * @returns
 * @Description
 */
function fnDeleteFieldBookDetailOne(){
	var mstId = $("input[name=mstId]").val();
	if(confirm("삭제하시겠습니까?")) {
		$.ajax({
			url: "/sys/fck/apr/fbk/deleteFieldBookDetailOne.do",
			data:$("#listForm").serialize(),
			dataType: "json",
			type:"POST",
			async:true,
			success:function(data){
				if(data.status == "success"){
					alert(data.message);
					window.location.href = "/sys/fck/apr/fbk/selectFieldBookDetail.do?mstId="+mstId;
				} else {
					alert(data.message);
				}
			},
			error: function(data){
				alert(data.message);
			}
		})
	}
}

/**
 * 공유방 단건 수정 조사유형 변경 이벤트 
 * @returns
 * @Description
 */
function fnSelectFieldBookTypeChange(e){

	if(e == '사방댐 외관점검'){
		$('#ectrsTable').css('display', '');
		$('#mrngTable').css('display', 'none');
		$('#mtcTable').css('display', 'none');
		
		$("#ectrsTable #svyType").val(e).prop("selected", true);
	}else if(e == '계류보전 외관점검'){
		$('#ectrsTable').css('display', 'none');
		$('#mrngTable').css('display', '');
		$('#mtcTable').css('display', 'none');
		
		$("#mrngTable #svyType").val(e).prop("selected", true);
	}else if(e == '산지사방 외관점검'){
		$('#ectrsTable').css('display', 'none');
		$('#mrngTable').css('display', 'none');
		$('#mtcTable').css('display', '');
		
		$("#mtcTable #svyType").val(e).prop("selected", true);
	}
	
}

/**
 * 행정구역 시군구 명칭조회 ajax
 * @returns
 */
function fncAdministCtprvnNmChange(e){
	
	var sdNm ; 
	if(e == 'ectrsTable'){
		sdNm = $("#ectrsTable select[name=svySd]").val();
	}else if(e == 'mrngTable'){
		sdNm = $("#mrngTable select[name=svySd]").val();
	}else if(e == 'mtcTable'){
		sdNm = $("#mtcTable select[name=svySd]").val();
	}
	
	if(sdNm == null || sdNm == undefined || sdNm.length == 0){
		$("#svySggView").empty().append("<option value>전체</option>");
		$("#svyEmdView").empty().append("<option value>전체</option>");
		$("#svyRiView").empty().append("<option value>전체</option>");
		return false;
	}
	
	
	$.ajax({
		url:"/cmm/selectAdministZoneSigngu.do",
		type:"POST",
        data: {sdNm:sdNm},
        dataType:"json",
        async: true,
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			if(e == 'ectrsTable'){
	        			$("#ectrsTable select[name=svySgg]").empty().append("<option value>전체</option>");
	        			$("#ectrsTable select[name=svyEmd]").empty().append("<option value>전체</option>");
	        			$("#ectrsTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}else if(e == 'mrngTable'){
        				$("#mrngTable select[name=svySgg]").empty().append("<option value>전체</option>");
        				$("#mrngTable select[name=svyEmd]").empty().append("<option value>전체</option>");
        				$("#mrngTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}else if(e == 'mtcTable'){
        				$("#mtcTable select[name=svySgg]").empty().append("<option value>전체</option>");
        				$("#mtcTable select[name=svyEmd]").empty().append("<option value>전체</option>");
        				$("#mtcTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}
        			
        			$.each(list.result,function(idx,item){
        				if(e == 'ectrsTable'){
            				$("#ectrsTable select[name=svySgg]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}else if(e == 'mrngTable'){
            				$("#mrngTable select[name=svySgg]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}else if(e == 'mtcTable'){
            				$("#mtcTable select[name=svySgg]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}
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
 * 행정구역 시군구 조회 ajax(EVENT)
 * @returns
 */
function fncAdministCtprvnChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#sggView").empty().append("<option value>전체</option>");
		$("#emdView").empty().append("<option value>전체</option>");
		$("#riView").empty().append("<option value>전체</option>");
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
        			$("#sggView").empty().append("<option value>전체</option>");
        			$("#emdView").empty().append("<option value>전체</option>");
        			$("#riView").empty().append("<option value>전체</option>");
        			$.each(list.result,function(idx,item){
        				$("#sggView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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
		$("#emdView").empty().append("<option value>전체</option>");
		$("#riView").empty().append("<option value>전체</option>");
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
        			$("#emdView").empty().append("<option value>전체</option>");
        			$("#riView").empty().append("<option value>전체</option>");
        			$.each(list.result,function(idx,item){
        				$("#emdView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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
 * 행정구역 읍면동 명칭조회 ajax
 * @returns
 */
function fncAdministSignguNmChange(e){
	var sdNm;
	var sggNm;
	
	if(e == 'ectrsTable'){
		sdNm = $("#ectrsTable select[name=svySd]").val();
		sggNm = $("#ectrsTable select[name=svySgg]").val();
	}else if(e == 'mrngTable'){
		sdNm = $("#mrngTable select[name=svySd]").val();
		sggNm = $("#mrngTable select[name=svySgg]").val();
	}else if(e == 'mtcTable'){
		sdNm = $("#mtcTable select[name=svySd]").val();
		sggNm = $("#mtcTable select[name=svySgg]").val();
	}
	
	if(sggNm == null || sggNm == undefined || sggNm.length == 0){
		$("#svyEmdView").empty().append("<option value>전체</option>");
		$("#svyRiView").empty().append("<option value>전체</option>");
		return false;
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneEmd.do",
		type:"POST",
        data: {sdNm:sdNm,sggNm:sggNm},
        dataType:"json",
        async: true,
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			if(e == 'ectrsTable'){
	        			$("#ectrsTable select[name=svyEmd]").empty().append("<option value>전체</option>");
	        			$("#ectrsTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}else if(e == 'mrngTable'){
        				$("#mrngTable select[name=svyEmd]").empty().append("<option value>전체</option>");
        				$("#mrngTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}else if(e == 'mtcTable'){
        				$("#mtcTable select[name=svyEmd]").empty().append("<option value>전체</option>");
        				$("#mtcTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}
        			
        			$.each(list.result,function(idx,item){
        				if(e == 'ectrsTable'){
            				$("#ectrsTable select[name=svyEmd]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}else if(e == 'mrngTable'){
            				$("#mrngTable select[name=svyEmd]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}else if(e == 'mtcTable'){
            				$("#mtcTable select[name=svyEmd]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}
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
		$("#riView").empty().append("<option value>전체</option>");
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
        			$("#riView").empty().append("<option value>전체</option>");
        			$.each(list.result,function(idx,item){
        				$("#riView").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
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
 * 행정구역 리 명칭조회 ajax
 * @returns
 */
function fncAdministEmdNmChange(e){
	
	var sdNm;
	var sggNm;
	var emdNm;
	
	if(e == 'ectrsTable'){
		sdNm = $("#ectrsTable select[name=svySd]").val();
		sggNm = $("#ectrsTable select[name=svySgg]").val();
		emdNm = $("#ectrsTable select[name=svyEmd]").val();
	}else if(e == 'mrngTable'){
		sdNm = $("#mrngTable select[name=svySd]").val();
		sggNm = $("#mrngTable select[name=svySgg]").val();
		emdNm = $("#mrngTable select[name=svyEmd]").val();
	}else if(e == 'mtcTable'){
		sdNm = $("#mtcTable select[name=svySd]").val();
		sggNm = $("#mtcTable select[name=svySgg]").val();
		emdNm = $("#mtcTable select[name=svyEmd]").val();
	}
	if(emdNm == null || emdNm == undefined || emdNm.length == 0){
		$("#svyRiView").empty().append("<option value>전체</option>");
		return false;
	}
	
	$.ajax({
		url:"/cmm/selectAdministZoneRi.do",
		type:"POST",
        data: {sdNm:sdNm,sggNm:sggNm,emdNm:emdNm},
        dataType:"json",
        async: true,
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			
        			if(e == 'ectrsTable'){
	        			$("#ectrsTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}else if(e == 'mrngTable'){
        				$("#mrngTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}else if(e == 'mtcTable'){
        				$("#mtcTable select[name=svyRi]").empty().append("<option value>전체</option>");
        			}
        			
        			$.each(list.result,function(idx,item){
        				if(e == 'ectrsTable'){
            				$("#ectrsTable select[name=svyRi]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}else if(e == 'mrngTable'){
            				$("#mrngTable select[name=svyRi]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}else if(e == 'mtcTable'){
            				$("#mtcTable select[name=svyRi]").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
            			}
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
	$("#listForm").attr("action","selectFieldBookList.do");
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}
/**
 * 공유방 상세 검색
 * @returns
 */
function fnSubSearch(){	
	$("#searchForm > input[name=pageSubIndex]").val(1);
 
	$("#searchForm").attr("action","/sys/fck/apr/fbk/selectFieldBookDetail.do");
	
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
/**
 * 목록 바로가기
 * @param url
 * @returns
 */
function fnList(url){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}

/**
 * 페이징 변경
 * @returns
 */
function fnPagging(){
	$("input[name=pageIndex]").val(1);
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageUnit]").val(pageUnit);
	
	$("#listForm").attr("action","/sys/fck/apr/fbk/selectFieldBookList.do");
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

function fnSearchMberList(){
	var searchKeyword = $("input[name=searchKeyword]").val();
	var searchCondition = $("select[name=searchCondition]").val();
	
	$('#reprt_all_check').prop('checked', false);
	$(".mberList tbody").empty();
	
	$.ajax({
//		url:"/sys/lss/lcp/fbk/selectLcpSldListAddr.do",
		url:"/sys/fck/apr/fbk/selectMberList.do",
		type:"POST",
        data: {
		    searchKeyword:searchKeyword,
			searchCondition:searchCondition
		},
        dataType:"json",
        async: true,
        success:function(data){
        	var result = data.list;
			for(var i =0;i<result.length;i++){
				var deptNm = result[i].deptNm
				var mberNm = result[i].mberNm
				var esntlId = result[i].esntlId
				
				var appendHtml = "<tr>";
				appendHtml += "<td>";
				appendHtml += "<input type=\"hidden\" class=\"esntlId\" name=\"esntlId\" value=\"".concat(esntlId).concat("\">");
				appendHtml += "<input type=\"hidden\" class=\"deptNm\" name=\"deptNm\" value=\"".concat(deptNm).concat("\">");
				appendHtml += "<input type=\"hidden\" class=\"mberNm\" name=\"mberNm\" value=\"".concat(mberNm).concat("\">");
				appendHtml += "<input type=\"checkbox\" class=\"reprt_check\" value=\"".concat(esntlId).concat("\">");
				appendHtml += "</td>";
				appendHtml += "<td>".concat(deptNm).concat("</td>");
				appendHtml += "<td>".concat(mberNm).concat("</td>");
				appendHtml += "</tr>";
				
				$(".mberList tbody").append(appendHtml);
			}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
//	$("#listForm").submit();
}

/**
 * 권한자 목록 추가
 * @returns
 * @Description
 */
function fnAddAuthory(){
	var checkboxs = $(".mberList tbody input[type=checkbox]:checked");
	
	if(checkboxs.length == 0){
		alert("사용자가 선택되지 않았습니다.\n선택 후 다시 시도해주세요.");
		return;
	}
	$.each(checkboxs,function(idx,elm){
		var esntlId = $(elm).prevAll('.esntlId').val();
		var deptNm = $(elm).prevAll('.deptNm').val();
		var mberNm = $(elm).prevAll('.mberNm').val();
		var appendHtml = "";
		var esntlIdList = $(".authoryList input[name=authorEsntlId]");
		
		for(var i=0; i<esntlIdList.length;i++){
			if(esntlId == esntlIdList.eq(i).val()){
				alert(mberNm+"사용자는 이미 권한이 등록된 사용자 입니다.\n 해제 후 다시 등록해주세요.");
				return;	
			}
		}
		
		appendHtml += "<tr>";
		appendHtml += "<td>".concat("<input type=\"hidden\" name=\"authorEsntlId\" value=\"").concat(esntlId).concat("\">").concat(deptNm).concat("</td>");
		appendHtml += "<td>".concat(mberNm).concat("</td>");
		appendHtml += "<td>".concat("<button type=\"button\" class=\"close-btn-m\" onclick=\"javascript:fnDelAuthory(event); return false;\">");
		appendHtml += "<spring:message code=\"title.create\" /></button></td>";
		appendHtml += "</tr>";
		$(".authoryList tbody").append(appendHtml);
	}); 
	
}

/**
 * 권한자 목록 삭제
 * @returns
 * @Description
 */
function fnDelAuthory(e){
	$(e.target).closest('tr').remove();
}

/**
 * 전체 체크박스 선택
 * @returns
 * @Description
 */
function fnSelectMberAllCheck(){
	if($("#reprt_all_check").prop("checked")) {
		$('.reprt_check').prop('checked', true);
	} else {
		$('.reprt_check').prop('checked', false); 
	}
}

/**
 * 공유방 권한관리 페이지 이동
 * @param id
 * @returns
 */
function fnManageCnrsSpceAuthory(id) {
	var hiddens = $("#listForm").find("input[type=hidden]");
	var form = $("<form></form>").attr("action","/sys/fck/apr/fbk/selectFieldbookAuthoryView.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}

/**
 * 사용자목록팝업
 * @returns
 */
function fnSearchAuthorUserPopup(id){
	var authorEsntlIdList = new Array();
 	$("input[name=authorEsntlId]").each(function(idx, item){
	   authorEsntlIdList.push($(item).val());
   	});
	$('<div id="deptListDiv">').dialog({
		modal:true,
		traditional : true,
		open: function(){
			$(this).load("selectAuthorUserPopup.do",{authorEsntlIdList : authorEsntlIdList.toString()});
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 700,
		width: 600,
		title: "사용자목록"
	});
}

/**
 * 사용자목록팝업 확인 이벤트
 * @returns
 */
function fnAddAuthorUser(){
	if($("input[name*=mber]:checked").length === 0){
		alert("선택된 사용자가 없습니다.");
		return;
	}
	$("#contents .BoardList tbody").empty();
	$.each($("input[name*=mber]:checked"),function(){
		var deptDiv = $(this).parent("div").parent("div");
		var chkForStr = deptDiv.attr("class").replace("deptMber","chk");
		var esntlId = $(this).val();
		var deptNm = $("label[for=".concat(chkForStr).concat("]")).text();
		var mberNm = $(this).next("label").text();
		var creatNm = $("#listForm div table:eq(0) input[type=hidden] ").val();
		
		var $tr = $("<tr></tr>");
		var $td = "<td><input type=\"hidden\" name=\"authorEsntlId\" value=\"".concat(esntlId).concat("\"/>").concat(deptNm).concat("</td>");
		var $td1 = "<td>".concat(mberNm).concat("</td>");
		var $td2 = "<td>";
		
		if(creatNm != esntlId){
			$td2+= "<button type=\"button\" class=\"close-btn-m\" onclick=\"javascript:fnDelAuthory(event); return false;\"></button>";
		}
		$td2+= "</td>";
		
		$tr.append($td).append($td1).append($td2);
		
		$("#contents .BoardList tbody").append($tr);
	});
	
	$("#deptListDiv").dialog("destroy");
}

/**
 * 공유방 사용자 권한수정 
 * @returns
 */
function fnUpdateCnrsSpceAuthor(){
	if(confirm("수정하시겠습니까?")) {
			
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: $("#listForm")[0].action,
	        dataType: "json",
	        processData: false,
	        contentType: false,
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    //location.href="/sys/fck/apr/fbk/selectFieldBookList.do";
                    fnList("/sys/fck/apr/fbk/selectFieldBookList.do");
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
 * 공유방 사용자 목록 트리 버튼 이벤트
 * @param idx
 * @param status
 * @returns
 */
function openCloseEx(idx,status){
	var deptMberDiv = ".deptMber_".concat(idx);
	var treeMenuImgSrc = $("#a_".concat(idx).concat(" > img")).attr("src");
	if(status === 0){
		$(deptMberDiv).show();
		$("#a_".concat(idx).concat(" img")).attr("src",treeMenuImgSrc.replace("plus","minus"));
		$("#a_".concat(idx)).attr("href","javascript:openCloseEx(".concat(idx).concat(",1)"));
	}else{
		$(deptMberDiv).hide();
		$("#a_".concat(idx).concat(" img")).attr("src",treeMenuImgSrc.replace("minus","plus"));
		$("#a_".concat(idx)).attr("href","javascript:openCloseEx(".concat(idx).concat(",0)"));
	}
}

/**
 * 측정값 선택
 * @returns
 */
function fncGetSelectVal(e) {
	
	var elmVal = "#"+e.target.name.replaceAll("Cd","Val");		
	var selectVal = $("select[name='"+e.target.name+"']").val();
	
	if($(elmVal).val().indexOf(selectVal) > -1 && selectVal.size > 0 ) {
		selectVal = "";
		alert("이미 선택한 값입니다.");
	}
	
	$(elmVal).val($(elmVal).val() +","+ selectVal);
	
	if($(elmVal).val().charAt(0) == ","){
		$(elmVal).val($(elmVal).val().substr(1));
	}
	if($(elmVal).val().charAt($(elmVal).val().length-1) == ","){
		$(elmVal).val($(elmVal).val().substr(0,$(elmVal).val().length-1));
	}
}