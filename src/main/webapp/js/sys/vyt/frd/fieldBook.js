$(document).ready(function(){
	window.$app = {};
	$app.upload = {};
	
	$('#searchKeyword, #svyIdView, #mst_partnameView').keypress(function(event) {
    	if (event.which == 13) {
    		fnSearch();
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
 * 조사데이터 상세조회
 * @param id
 * @returns
 */
function fnFieldBookDetail(id) {
	$("input[name=mst_id]").val(id);
	var schid = $("input[name=id]").val();
	
	if(schid == null || schid == undefined){
		$("input[name=id]").val('');
	}else{
		$("input[name=id]").val(schid);
	}
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#listForm").attr("action","/sys/vyt/frd/fbk/selectFieldBookDetail.do").attr("method","post");
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
		width: 620,
		title: "임도 공유방 등록"
	});
}

/**
 * 대상지등록
 * @returns
 */
function fnInsertCnrsSpceSldPopup(mst_id) {
	var mst_create_user = $("input[name=mst_create_user]").val();
	
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertCnrsSpceSldPopup.do?mst_id="+mst_id+"&mst_create_user="+mst_create_user)
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 350,
		width: 620,
		title: "임도 대상지 등록"
	});
}

/**
 * 대상지 추가
 * @returns
 * @Description
 */
function fnInsertCnrsSpceSld(){
	var sldId = $("select[name=sldId]").val();
	var frdRnk = $("select[name=frdRnk]").val();
	var sldCnt = $("#sldCnt").text();
	
	if(frdRnk == null || frdRnk == ""){
		alert("구분을 선택해주세요.");
		return false;
	}else if(sldId == null || sldId == ""){
		alert("사업목록을 선택해주세요.");
		return false;
	}else if(sldCnt == "0"){
		alert("등록 대상지가 없습니다. \n다시 한 번 확인해주세요.");
		return false;
	}else{
		if(confirm("등록하시겠습니까?")) {
			$.ajax({
				url: $("#insertForm")[0].action,
				type:"POST",
				data : $("#insertForm").serialize(),
		        dataType:"json",
		        success:function(data){
					if(data.message){
		        		alert(data.message);
		        	}
		        	if(data.status == "success"){
		        		window.location.reload();
		        	}else{
		        		$(".loading-div").remove();
	        		}
		        },
		        error: function(request, status, error){
		        	$(".loading-div").remove();
		        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
		        }
			});
		}
	}
}

/**
 * 공유방 등록
 * @returns
 */
function fnInsertCnrsSpce() {
	var mst_partname = $("#insertForm input[name=mst_partname]").val();
	var mst_password = $("#insertForm input[name=mst_password]").val();
	var frdRnk = $("#insertForm select[name=frdRnk]").val();
	var sldId = $("#insertForm select[name=sldId]").val();
	var authorLength = $("#insertForm .authoryList tbody tr").length;
	
	if(mst_partname == null || mst_partname == ""){
		alert("사업지구명 정보가 입력되지 않았습니다.");
		return false;
	}
	if(mst_password == null || mst_password ==""){
		alert("비밀번호 정보가 입력되지 않았습니다.");
		return false;
	}
	if(frdRnk == null || frdRnk ==""){
		alert("구분 정보가 입력되지 않았습니다.");
		return false;
	}
	if(sldId == null || sldId ==""){
		alert("사업목록 정보가 입력되지 않았습니다.");
		return false;
	}
	if(authorLength < 1){
		alert("권한 정보가 입력되지 않았습니다.");
		return false;
	}
	
	if(confirm("등록하시겠습니까?")) {
		$(".drag-div").append('<div class="progress"><div></div></div>');
		$.ajax({
			url: $("#insertForm")[0].action,
			type:"POST",
			data : $("#insertForm").serialize(),
	        dataType:"json",
	        success:function(data){
	        	if(data.nnt == 'fail') {
					alert("공백으로 입력된 내용이 있습니다. \n다시 한 번 확인해주세요.");
					$(".loading-div").remove();
	            	$(".progress").remove();
				}else{
					if(data.cnt == 'fail') {
						alert("중복된 사업지구명이 존재합니다. \n다시 한 번 확인해주세요.");
		        		$(".loading-div").remove();
		            	$(".progress").remove();
		            	$("input[name=mst_partname]").val('');
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
 * 공유방 조사데이터 삭제
 * @returns
 * @Description
 */
function fnDeleteCnrsSpce(id){
	if(confirm("공유방을 삭제하시겠습니까?")) {
		if(confirm("공유방삭제시 조사된 정보도 함께 삭제가 됩니다.\n그래도 삭제하시겠습니까?")){
			$.ajax({
				url:"/sys/vyt/frd/fbk/deleteCnrsSpce.do",
				type:"POST",
		        data: {
		        	id: id
		        },
		        dataType:"json",
		        success:function(data){
		        	if(data.status == "success"){
		        		alert(data.message);
		        		fnList("/sys/vyt/frd/fbk/selectFrdFieldBookList.do");
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
	
	$("#listForm").attr("action","/sys/vyt/frd/fbk/updateFieldBookView.do");
	$("#listForm").submit();
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
 * 공유방 단건 상세조회 
 * @returns
 * @Description
 */
function fnSelectFieldBookDetailOne(gid){
	$("input[name=gid]").val(gid);
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/frd/fbk/selectFieldBookDetailOne.do").attr("method", "post");
	$("#listForm").submit();
}

/**
 * 공유방 단건 수정 페이지 이동
 * @returns
 * @Description
 */
function fnSelectFieldBookUpdtOne(gid){
	$("input[name=gid]").val(gid);
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/frd/fbk/updateFieldBookViewOne.do").attr("method", "post");
	$("#listForm").submit();
}

/**
 * 공유방 단건 수정
 * @returns
 * @Description
 */
function fnUpdateFieldBookDetailOne(){
	
	var mstId = $('input[name=mst_id]').val();
	
	if(confirm("수정하시겠습니까?")) {
		
		var bpxD = $("input[name=bpxD]").val();
		var bpxM = $("input[name=bpxM]").val();
		var bpxS = $("input[name=bpxS]").val();
		fnDms2Degree(bpxD, bpxM, bpxS, "bpx");
		
		var bpyD = $("input[name=bpyD]").val();
		var bpyM = $("input[name=bpyM]").val();
		var bpyS = $("input[name=bpyS]").val();
		fnDms2Degree(bpyD, bpyM, bpyS, "bpy");
		
		var epx1D = $("input[name=epx1D]").val();
		var epx1M = $("input[name=epx1M]").val();
		var epx1S = $("input[name=epx1S]").val();
		fnDms2Degree(epx1D, epx1M, epx1S, "epx1");
		
		var epy1D = $("input[name=epy1D]").val();
		var epy1M = $("input[name=epy1M]").val();
		var epy1S = $("input[name=epy1S]").val();
		fnDms2Degree(epy1D, epy1M, epy1S, "epy1");
		
		var epx2D = $("input[name=epx2D]").val();
		var epx2M = $("input[name=epx2M]").val();
		var epx2S = $("input[name=epx2S]").val();
		fnDms2Degree(epx2D, epx2M, epx2S, "epx2");
		
		var epy2D = $("input[name=epy2D]").val();
		var epy2M = $("input[name=epy2M]").val();
		var epy2S = $("input[name=epy2S]").val();
		fnDms2Degree(epy2D, epy2M, epy2S, "epy2");
		
		$.ajax({
			url: "/sys/vyt/frd/fbk/updateFieldBookDetailOne.do",
			data:$("#listForm").serialize(),
	        dataType: "json",
			type:"POST",
			async:true,
	        success:function(data){
	        	if(data.status == "success"){
	        		alert(data.message);
	        		fnList("/sys/vyt/frd/fbk/selectFrdFieldBookList.do");
	        	} else {
	        		alert(data.message);
	        	}
	        },
	        error:function(request,status,error){
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
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
			url: "/sys/vyt/frd/fbk/deleteFieldBookDetailOne.do",
			data:$("#listForm").serialize(),
			dataType: "json",
			type:"POST",
			async:true,
			success:function(data){
				if(data.status == "success"){
					alert(data.message);
					window.location.href = "/sys/vyt/frd/fbk/selectFieldBookDetail.do?mst_id="+mstId;
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
 * 행정구역 시군구 명칭조회 ajax
 * @returns
 */
function fncAdministCtprvnNmChange(){
	if($("#sldCnt").text() != '0'){
		$("#sldCnt").css("color", "black");
		$("#sldCnt").css("font-weight", "normal");
		$("#sldCnt").text("0");
	}
	
	var sdNm = $("#svySdView").val();
	
	if(sdNm == null || sdNm == undefined || sdNm.length == 0){
		$("#svySggView").empty().append("<option value>선택하세요</option>");
		$("#svyEmdView").empty().append("<option value>선택하세요</option>");
		$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
        			$("#svySggView").empty().append("<option value>선택하세요</option>");
        			$("#svyEmdView").empty().append("<option value>선택하세요</option>");
        			$("#svyRiView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svySggView").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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
function fncAdministSignguNmChange(){
	
	var sdNm = $("#svySdView").val();
	var sggNm = $("#svySggView").val();
	
	if(sggNm == null || sggNm == undefined || sggNm.length == 0){
		$("#svyEmdView").empty().append("<option value>선택하세요</option>");
		$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
        			$("#svyEmdView").empty().append("<option value>선택하세요</option>");
        			$("#svyRiView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svyEmdView").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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
function fncAdministEmdNmChange(){
	var sdNm = $("#svySdView").val();
	var sggNm = $("#svySggView").val();
	var emdNm = $("#svyEmdView").val();
	
	if(emdNm == null || emdNm == undefined || emdNm.length == 0){
		$("#svyRiView").empty().append("<option value>선택하세요</option>");
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
        			$("#svyRiView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svyRiView").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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
 * 공유방 권한관리 페이지 이동
 * @param id
 * @returns
 */
function fnManageCnrsSpceAuthory(id) {
	
	var hiddens = $("#searchForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#searchForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#searchForm").attr("action","/sys/vyt/frd/fbk/selectFieldbookAuthoryView.do");
	$("#searchForm").submit();
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
                    location.href="/sys/vyt/frd/fbk/selectFrdFieldBookList.do";
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
		var creatNm = $("#insertForm div table:eq(0) input[type=hidden] ").val();
		
		var $tr = $("<tr></tr>");
		var $td = "<td><input type=\"hidden\" name=\"authorEsntlId\" value=\"".concat(esntlId).concat("\"/>").concat(deptNm).concat("</td>");
		var $td1 = "<td>".concat(mberNm).concat("</td>");
		var $td2 = "<td><button type=\"button\" class=\"close-btn-m\" onclick=\"javascript:fnDelAuthory(event); return false;\"></button></td>";
		
		if(creatNm != esntlId){
			$td2+= "<button type=\"button\" class=\"close-btn-m\" onclick=\"javascript:fnDelAuthory(event); return false;\"></button>";
		}
		$td2+= "</td>";
		
		$tr.append($td).append($td1).append($td2);
		
		$("#contents .BoardList tbody").append($tr);
	});
	
	$("#deptListDiv").dialog("destroy");
}
/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
/**
 * 목록 바로가기
 * @param url
 * @returns
 */
function fnList(url){
	var pageName = $("input[name=pageName]").val();

	var hiddens = $("#searchForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}

function fnSearchMberList(){
	var searchKeyword = $("input[name=searchKeyword]").val();
	var searchCondition = $("select[name=searchCondition]").val();
	
	$('#reprt_all_check').prop('checked', false);
	$(".mberList tbody").empty();
	
	$.ajax({
		url:"/sys/vyt/frd/fbk/selectMberList.do",
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
		appendHtml += 	"<td>".concat("<input type=\"hidden\" name=\"authorEsntlId\" value=\"").concat(esntlId).concat("\">").concat(deptNm).concat("</td>");
		appendHtml += 	"<td>".concat(mberNm).concat("</td>");
		appendHtml += 	"<td>".concat("<button type=\"button\" class=\"close-btn-m\" onclick=\"javascript:fnDelAuthory(event); return false;\">");
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
 * 검색
 * @returns
 * @Description
 */
function fnSearch(){
	$("input[name=pageIndex]").val(1);
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageUnit]").val(pageUnit);
	
	$("#searchForm").attr("action","/sys/vyt/frd/fbk/selectFrdFieldBookList.do");
	var params = $("#searchForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#searchForm").submit();
}

/*
 * 위경도 변환기
 * DMS to Degree
 */
function fnDms2Degree(d, m, s, type) {
	if(d != "" && m != "" && s != ""){
		if(!isNaN(d) && !isNaN(m) && !isNaN(s)){
			d = Number(d);
			m = Number(m);
			s = Number(s);
            var coordinate = d + m/60 + s/3600;
            
            if(coordinate != null && !isNaN(coordinate)){
            	$('#listForm').append('<input type="hidden" name="'+type+'" value="'+coordinate+'"/>');
            }
		}
	}
}

/**
 * 대상지 사업목록 개수 ajax
 * @returns
 */
function fnSelectSldCnt(type){
	if( $("select[name=sldId]").val() == null || $("select[name=sldId]").val() == ''){
		alert("사업목록을 선택해주세요.");
		return false;
	}
	
	$.ajax({
		url:"/sys/vyt/frd/fbk/selectSldInfoCnt.do",
		type:"POST",
		data : $("#insertForm").serialize(),
        dataType:"json",
        success:function(data){
        	$("#sldCnt").css("color", "red");
        	$("#sldCnt").css("font-weight", "bold");
        	$("#sldCnt").text(data.cnt);
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 대상지 사업목록 선택 초기화
 * @returns
 */
function fnCheckSldValue(value){
	$("#svySdView").val('');
	fncAdministCtprvnNmChange();
	
	$("#sldCnt").css("color", "black");
	$("#sldCnt").css("font-weight", "normal");
	$("#sldCnt").text("0");
}

/*
 * 대상지 등록 관할청 
 */
function fncCompentAuthChange(){
	var compentAssort = $("#compentAssort").val();
	
	if(compentAssort == null || compentAssort == undefined || compentAssort.length == 0){
		$("#compentauth").empty().append("<option value>선택</option>");
		$("#subcompentauth").empty().append("<option value>선택</option>");
		return false;
	}
	
	$.ajax({
		url:"/sys/vyt/frd/fbk/selectCompentAuth.do",
		type:"POST",
        data: {compentType:"compentAuth1",compentValue:compentAssort},
        dataType:"json",
        async: true,
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#compentauth").empty().append("<option value>선택</option>");
        			$("#subcompentauth").empty().append("<option value>선택</option>");
        			$.each(list.result,function(idx,item){
        				if(compentAssort == '국유림'){
        					$("#compentauth").append("<option value=\"".concat(item.code,"\">").concat(item.codeNm,"</option>"));
        				}else if(compentAssort == '민유림'){
        					$("#compentauth").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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

/*
 * 대상지 등록 세부관할 
 */
function fncSubCompentAuthChange(){
	var compentauth = $("#compentauth").val();
	var compentAssort = $("#compentAssort").val();
	
	if(compentauth == null || compentauth == undefined || compentauth.length == 0){
		$("#subcompentauth").empty().append("<option value>선택</option>");
		return false;
	}
	
	$.ajax({
		url:"/sys/vyt/frd/fbk/selectCompentAuth.do",
		type:"POST",
		data: {compentType:"compentAuth2",compentValue:compentauth},
		dataType:"json",
		async: true,
		success:function(list){
			if(list.result){
				if(list.result.length > 0){
					$("#subcompentauth").empty().append("<option value>선택</option>");
					$.each(list.result,function(idx,item){
						if(compentAssort == '국유림'){
							$("#subcompentauth").append("<option value=\"".concat(item.codeNm,"\">").concat(item.codeNm,"</option>"));
						}else if(compentAssort == '민유림'){
							$("#subcompentauth").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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