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
 * 공유방 조사데이터 업로드
 * @returns
 */
function fnInsertFieldBookView(){

	var form = $("<form></form>");
	$(form).attr("id","listForm").attr("name","itemVO").attr("method","post").attr("action","/ext/lss/lcp/upload.do").attr("enctype","multipart/form-data");
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
	
	$("#listForm").attr("action","/sys/lss/lcp/fbk/selectFieldBookList.do");
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

	$("#searchForm").attr("action","/sys/lss/lcp/fbk/selectFieldBookDetail.do");
	
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
		height: 750,
		width: 610,
		title: "땅밀림실태조사 공유방 등록"
	});
}

/**
 * 공유방 등록
 * @returns
 */
function fnInsertCnrsSpce() {
	if(confirm("등록하시겠습니까?")) {
		$(".loading-div").show();
		
		$("#insertForm").ajaxForm({
			type: 'POST',
	        url: $("#insertForm")[0].action,
	        dataType: "json",
	        success: function(data) {
	        	$(".loading-div").hide();
	        	if(data.status == "success") {
	        		alert(data.message);
                    location.href="/sys/lss/lcp/fbk/selectFieldBookList.do";
                }else{
                    alert(data.message);
                }
		    },
		    error: function(data) {
		    	$(".loading-div").hide();
		    	alert(data.message);
		    }
		}).submit();
	}else {
		return false;
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
	
	if(schid == null || schid == ''){
		$("input[name=id]").val('');
	}
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#listForm").attr("action","/sys/lss/lcp/fbk/selectFieldBookDetail.do");
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
		height: 400,
		width: 610,
		title: "땅밀림실태조사 대상지 등록"
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
	if(confirm("등록하시겠습니까?")) {
		$(".loading-div").show();
		var sldVal = $("input[name=sldVal]").val();
		var gvfVal = $("input[name=gvfVal]").val();
		
		$("#insertForm").ajaxForm({
			type: 'POST',
			url: $("#insertForm")[0].action,
	        dataType: "json",
	        data:{gvfVal:gvfVal,sldVal:sldVal},
	        success: function(data) {
	        	$(".loading-div").hide();
	        	if(data.status == "success"){
                    alert(data.message);
                    window.location.reload();
                }else{
                    alert(data.message);
                }
		    },
		    error: function(data) {
		    	$(".loading-div").hide();
		    	alert(data.message);
		    }
		}).submit();
	}else {
		return false;
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
	$("input[name=id]").val(id)
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#listForm").attr("action","/sys/lss/lcp/fbk/updateFieldBookView.do");
	$("#listForm").submit();
}

/**
 * 공유방 조사데이터 삭제
 * @returns
 * @Description
 */
function fnUpdateCnrsSpce(smid){
	var mst_id = $("input[name=mstId]").val();
	var smid = [];
	
	$("input[name=fieldBook_check]:checked").each(function() {
		smid.push($(this).val());
	})
	
	if(smid.length == 0) {
		alert("삭제항목을 선택해주세요.");
		return;
	}
	
	if(confirm("선택항목을 삭제하시겠습니까?")) {
		$(".loading-div").show();
		$.ajax({
			url:"/sys/lss/lcp/fbk/updateCnrsSpceItem.do",
			type:"POST",
        	data: {
        		smid: smid,
	        	mst_id: mst_id
    	    },
        	dataType:"json",
	        success:function(data){
	        	$(".loading-div").hide();
    	    	if(data.status == "success"){
        			alert(data.message);
        			window.location.reload();
	        	} else {
    	    		alert(data.message);
        		}
	        },
    	    error: function(data){
	    	    $(".loading-div").hide();
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
			$(".loading-div").show();
			$.ajax({
				url:"/sys/lss/lcp/fbk/deleteCnrsSpce.do",
				type:"POST",
		        data: {
		        	mstId: id
		        },
		        dataType:"json",
		        success:function(data){
		        	$(".loading-div").hide();
		        	if(data.status == "success"){
		        		alert(data.message);
		        		location.href = "/sys/lss/lcp/fbk/selectFieldBookList.do"
		        	} else {
		        		alert(data.message);
		        	}
		        },
		        error: function(data){
		        	$(".loading-div").hide();
		        	alert(data.message);
		        }
			});
		}
	}
}

/**
 * 행정구역 시군구 명칭조회 ajax
 * @returns
 */
function fncAdministCtprvnChange(elm){
	
	if(elm == "sld") {
		var sdCode = $("#sldSd").val();
		if(sdCode == null || sdCode == undefined || sdCode.length == 0){
			$("#sldSgg").empty().append("<option value>선택하세요</option>");
			$("#sldEmd").empty().append("<option value>선택하세요</option>");
			return false;
		}
		
		$.ajax({
			url:"/cmm/selectAdministZoneSigngu.do",
			type:"POST",
	        data: {sdCode:sdCode},
	        dataType:"json",
	        async: true,
	        success:function(list){
	        	if(list.result){
	        		if(list.result.length > 0){
	        			$("#sldSgg").empty().append("<option value>선택하세요</option>");
	        			$("#sldEmd").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#sldSgg").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
	        		}
	        	}
	        },
	        error: function(request, status, error){
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}else{
		var sdCode = $("#gvfSd").val();
		if(sdCode == null || sdCode == undefined || sdCode.length == 0){
			$("#gvfSgg").empty().append("<option value>선택하세요</option>");
			$("#gvfEmd").empty().append("<option value>선택하세요</option>");
			return false;
		}
		
		$.ajax({
			url:"/cmm/selectAdministZoneSigngu.do",
			type:"POST",
	        data: {sdCode:sdCode},
	        dataType:"json",
	        async: true,
	        success:function(list){
	        	if(list.result){
	        		if(list.result.length > 0){
	        			$("#gvfSgg").empty().append("<option value>선택하세요</option>");
	        			$("#gvfEmd").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#gvfSgg").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
	        		}
	        	}
	        },
	        error: function(request, status, error){
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}
}

/**
 * 행정구역 읍면동 명칭조회 ajax
 * @returns
 */
function fncAdministSignguChange(elm){
	if(elm == "sld") {
		var sggCode = $("#sldSgg").val();
		if(sggCode == null || sggCode == undefined || sggCode.length == 0){
			$("#sldEmd").empty().append("<option value>선택하세요</option>");
			return false;
		}
		
		$.ajax({
			url:"/cmm/selectAdministZoneEmd.do",
			type:"POST",
	        data: {sggCode:sggCode},
	        dataType:"json",
	        async: true,
	        success:function(list){
	        	if(list.result){
	        		if(list.result.length > 0){
	        			$("#sldEmd").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#sldEmd").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
	        		}
	        	}
	        },
	        error: function(request, status, error){
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}else {
		var sggCode = $("#gvfSgg").val();
		if(sggCode == null || sggCode == undefined || sggCode.length == 0){
			$("#gvfEmd").empty().append("<option value>선택하세요</option>");
			return false;
		}
		
		$.ajax({
			url:"/cmm/selectAdministZoneEmd.do",
			type:"POST",
	        data: {sggCode:sggCode},
	        dataType:"json",
	        async: true,
	        success:function(list){
	        	if(list.result){
	        		if(list.result.length > 0){
	        			$("#gvfEmd").empty().append("<option value>선택하세요</option>");
	        			$.each(list.result,function(idx,item){
	        				$("#gvfEmd").append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        			});
	        		}
	        	}
	        },
	        error: function(request, status, error){
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}
}


/**
 * 대상지 행정구역별 건수 조회
 * @returns
 */
function fnAddrSearch(elm){
	if(elm == "sld") {
		var sldId = $("#sldId").val();
		var sdCode = $("#sldSd").val();
		var sggCode = $("#sldSgg").val();
		var emdCode = $("#sldEmd").val();
		$("#sldCnt").empty();
		
		$.ajax({
			url:"/sys/lss/lcp/fbk/selectLcpSldListAddr.do",
			type:"POST",
	        data: {
			    sldId:sldId,
				sldSd:sdCode,
				sldSgg:sggCode,
				sldEmd:emdCode
			},
	        dataType:"json",
	        async: true,
	        success:function(data){
//	        	var result = data.result;
	        	var cnt = data.cnt;	        	
				var stdgCd = sdCode+sggCode+emdCode;
				
	        	$("input[name=sldCnt]").val(cnt);
	        	$("input[name=sldStdgCd]").val(stdgCd);
	        	
	        	var html = "";
	        	html += "<strong style=\"color:#ff0000; font-weight: bold;\"> ";
	        	html += cnt;
	        	html += "</strong>";
	        	$("#sldCnt").text('');
	        	$("#sldCnt").append(html);
	        },
	        error: function(request, status, error){
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}else {
		var sdCode = $("#gvfSd").val();
		var sggCode = $("#gvfSgg").val();
		var emdCode = $("#gvfEmd").val();
		$("#gvfCnt").empty();
		
		$.ajax({
			url:"/sys/lss/lcp/fbk/selectLcpGvfListAddr.do",
			type:"POST",
	        data: {
				gvfSd:sdCode,
				gvfSgg:sggCode,
				gvfEmd:emdCode
			},
	        dataType:"json",
	        async: true,
	        success:function(data){
//	        	var result = data.result;
	        	var cnt = data.cnt;
				var stdgCd = sdCode+sggCode+emdCode;
				
	        	$("input[name=gvfCnt]").val(cnt);
	        	$("input[name=gvfStdgCd]").val(stdgCd);
	        	
	        	var html = "";
	        	html += "<strong style=\"color:#ff0000; font-weight: bold;\"> ";
	        	html += cnt;
	        	html += "</strong>";
	        	
	        	$("#gvfCnt").text('');
	        	$("#gvfCnt").append(html);
	        },
	        error: function(request, status, error){
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
	}
}
/**
 * 행정구역 시군구 조회 ajax(EVENT)
 * @returns
 */
function fncAdministNmCtprvnChange(event){
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
 * 행정구역 읍면동 조회 ajax(EVENT)
 * @returns
 */
function fncAdministNmSignguChange(event){

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
 * 행정구역 리 조회 ajax(EVENT)
 * @returns
 */
function fncAdministNmEmdChange(event){
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
 * 목록 바로가기
 * @param url
 * @returns
 */
function fnList(url){
	var pageName = $("input[name=pageName]").val();

	var hiddens;
	if(pageName == 'authorMng'){
		hiddens = $("#insertForm").find("input[name*=sch]");
	}else{
		hiddens = $("#listForm").find("input[name*=sch]");
	}
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
//		url:"/sys/lss/lcp/fbk/selectLcpSldListAddr.do",
		url:"/sys/lss/lcp/fbk/selectMberList.do",
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
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	$("#listForm").attr("action","/sys/lss/lcp/fbk/selectFieldbookAuthoryView.do");
	$("#listForm").submit();
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

/**
 * 공유방 사용자 권한수정 
 * @returns
 */
function fnUpdateCnrsSpceAuthor(){
	if(confirm("수정하시겠습니까?")) {
			
		$("#insertForm").ajaxForm({
			type: 'POST',
	        url: $("#insertForm")[0].action,
	        dataType: "json",
	        processData: false,
	        contentType: false,
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/lss/lcp/fbk/selectFieldBookList.do";
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

