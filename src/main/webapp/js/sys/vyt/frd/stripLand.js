$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
	
	$('input[name=sld_nmView], input[name=sld_create_userView]').keypress(function(event) {    		
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
 * 대상지목록 상세화면
 * @param sldId
 * @returns
 */
function fnSvySldInfoDetail(id) {
	$("input[name=id]").val(id);
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/frd/sld/selectFrdSldinfo.do");
	$("#listForm").submit();
}

/**
 * 대상지 단건 상세조회
 * @param smid
 * @returns
 */
function fnSvySldInfoDetailOne(smid) {
	$(".loading-div").show();
	$("input[name=smid]").val(smid);
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/frd/sld/selectSldInfoDetail.do");
	$("#listForm").submit();
}

/**
 * 대상지 단건 수정페이지 이동
 * @param smid
 * @returns
 */
function fnSvySldInfoUpdtView() {
	$("input[name=pageIndex]").val(1);
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/vyt/frd/sld/selectSldInfoUpdtView.do");
	$("#listForm").submit();
}

/**
 * 대상지 단건 수정
 * @param smid
 * @returns
 */
function fnSvySldInfoUpdt() {
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
		
		if(epx2D != undefined && epx2S != undefined && epx2M != undefined){
			fnDms2Degree(epx2D, epx2M, epx2S, "epx2");
		}
		
		var epy2D = $("input[name=epy2D]").val();
		var epy2M = $("input[name=epy2M]").val();
		var epy2S = $("input[name=epy2S]").val();
		
		if(epy2D != undefined && epy2M != undefined && epy2S != undefined){
			fnDms2Degree(epy2D, epy2M, epy2S, "epy2");
		}
		
		$.ajax({
			url: "/sys/vyt/frd/sld/updateSldInfoUpdt.do",
			data:$("#listForm").serialize(),
	        dataType: "json",
			type:"POST",
			async:true,
	        success:function(data){
	        	if(data.status == "success"){
	        		alert(data.message);
	        		fnList("/sys/vyt/frd/sld/selectSldInfoDetail.do");
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
 * 대상지 단건 삭제
 * @param smid
 * @returns
 */
function fnSvySldInfoDelete() {
	
	if(confirm("삭제하시겠습니까?")) {
		$.ajax({
			url: "/sys/vyt/frd/sld/deleteSldDetailOne.do",
			data:$("#listForm").serialize(),
			dataType: "json",
			type:"POST",
			async:true,
			success:function(data){
				if(data.status == "success"){
					alert(data.message);
					fnList('/sys/vyt/frd/sld/selectFrdSldinfo.do');
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
 * 대상지 삭제
 * @param smid
 * @returns
 */
function fnDeleteSldSpce(id) {
	
	if(confirm("대상지를 삭제하시겠습니까?")) {
		if(confirm("대상지 삭제시 등록된 정보도 함께 삭제가 됩니다.\n그래도 삭제하시겠습니까?")){
			$.ajax({
				url:"/sys/vyt/frd/sld/deleteSldDetail.do",
				type:"POST",
		        data: {
		        	id: id
		        },
		        dataType:"json",
		        success:function(data){
		        	if(data.status == "success"){
		        		alert(data.message);
		        		fnList("/sys/vyt/frd/sld/selectFrdSldList.do");
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
 * 대상지 등록
 * @param smid
 * @returns
 */
function fnSvySldInsert() {
	
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
	        	if(data.cnt == 'fail') {
					alert("중복된 사업명이 존재합니다. \n다시 한 번 확인해주세요.");
	        		$(".loading-div").remove();
	            	$(".progress").remove();
	            	$("input[name=sld_nm]").val('');
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
 * 대상지 등록 팝업
 * @param smid
 * @returns
 */
function fncStripLandInsertView(){
	
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertStripLandView.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 500,
		width: 600,
		title: "임도 대상지 등록"
	});
}
/**
 * 대상지 추가등록 팝업
 * @param smid
 * @returns
 */
function fncAddStripLandInsertView(id){
	
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertStripLandViewAddPopup.do?id="+id);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 380,
		width: 600,
		title: "임도 대상지 추가 등록"
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
 * 대상지 분석 팝업표출
 * @param per
 * @returns
 */
function fnSldAnalPopup(smid){
	
	var sldId = $("input[name=id]").val();
	
	$('<div id="analDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("selectSldAnalPopup.do?smid="+smid+"&sldId="+sldId);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 745,
		width: 900,
		title: "임도 대상지 분석"
	});
}
/**
 * 대상지 분석 등록
 * @param per
 * @returns
 */
function fnSvySldAnalRegist(sldId, smid){
	
	var bs = $("input[name=bufferSize]").val();
	
	var analList = [];
	
	$("input[name='checkList']:checked").each(function() {
	    var checkVal = $(this).val();
	    if (analList.indexOf(checkVal) === -1) {
	        analList.push(checkVal);
	    }
	});
	
	if(analList.length == 0) {
		alert("분석종류를 선택해주세요.");
		return false;
	}
	
	if(confirm("공간분석 작업을 시작하시겠습니까?")){
		$(".loading-div").show();
		$.ajax({
			url:"/sys/vyt/frd/sld/selectSldReportImgAll.do",
			type:"POST",
			traditional : true,
	    	data: {
	    		analList:analList,
	    		smid:smid,
	    		sldId:sldId,
	    		bufferSize:bs
		    },
	    	dataType:"json",
	        success:function(data){
	        	$(".loading-div").hide();
		    	if(data.status == "success"){
	    			alert(data.message);
	    			fnList("/sys/vyt/frd/sld/selectFrdSldinfo.do");
	        	} else {
		    		alert(data.message);
	    		}
	        },
		    error: function(data){
		    	$(".loading-div").hide();
	    		alert(data.message);
	        },
	        complete: function(){
	        	$(".loading-div").hide();
	        }
		});
	}
}


/**
 * 행정구역 시군구 명칭조회 ajax
 * @returns
 */
function fncAdministCtprvnNmChange(){
	var sdNm = $("#sdView").val();
	
	if(sdNm == null || sdNm == undefined || sdNm.length == 0){
		$("#sggView").empty().append("<option value>선택하세요</option>");
		$("#emdView").empty().append("<option value>선택하세요</option>");
		$("#riView").empty().append("<option value>선택하세요</option>");
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
        			$("#sggView").empty().append("<option value>선택하세요</option>");
        			$("#emdView").empty().append("<option value>선택하세요</option>");
        			$("#riView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#sggView").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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
	var sdNm = $("#sdView").val();
	var sggNm = $("#sggView").val();
	
	if(sggNm == null || sggNm == undefined || sggNm.length == 0){
		$("#emdView").empty().append("<option value>선택하세요</option>");
		$("#riView").empty().append("<option value>선택하세요</option>");
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
        			$("#emdView").empty().append("<option value>선택하세요</option>");
        			$("#riView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#emdView").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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
	var sdNm = $("#sdView").val();
	var sggNm = $("#sggView").val();
	var emdNm = $("#emdView").val();
	
	if(emdNm == null || emdNm == undefined || emdNm.length == 0){
		$("#riView").empty().append("<option value>선택하세요</option>");
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
        			$("#riView").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#riView").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
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
 * 대상지 분석 체크박스 이벤트
 * @returns
 */
function fnCheckList(type){
	if( type == 'all'){
		if($("#checkAll").is(":checked")) $("input[name=checkList]").prop("checked", true);
		else $("input[name=checkList]").prop("checked", false);
	}else{
		var total = $("input[name=checkList]").length;
		var checked = $("input[name=checkList]:checked").length;
		
		if(total != checked) $("#checkAll").prop("checked", false);
		else $("#checkAll").prop("checked", true); 
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

function fnList(url){
	var hiddens = $("#searchForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
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

/*
 * 분석파일 다운로드
 */
function fnAnalDataDownload(analType, fileExtsn){
	var fileSn = $("input[name="+analType+"]").val();
	window.location.href = "/sys/vyt/frd/sld/selectAnalFileDown.do?fileSn="+fileSn+"&fileExtsn="+fileExtsn;
}

/**
 * 토지이용계획열람 팝업 생성
 * @returns
 * @Description
 */
function fncLadUsePlanPopup(pnuCode, addr, jibun, type){
	
	var pageTitle;
	var url;
	
	if(type == 'eum'){
		
		var addrArray = addr.split(" ");
		
		var sd = addrArray[0];
		var sgg = addrArray[1];
		var emd = addrArray[2];
		var ri = addrArray[3] !== undefined ? addrArray[3] : "";
		
		var landGbn = 1;

		if(jibun.includes("산")) landGbn = 2;
		jibun = jibun.replaceAll(/[^0-9]/g,'');
		
		pageTitle = "토지이용계획열람";
		url = "http://www.eum.go.kr/web/ar/lu/luLandDet.jsp?mode=search&selGbn=umd&isNoScr=script&pnu="+pnuCode+"&selSido="+sd+"&selSgg="+sgg+"&selUmd="+emd+"&selRi="+ri+"&landGbn="+landGbn+"&bobn="+jibun+"&bubn=0000";
	}else{
		pageTitle = "씨리얼 (SEE:REAL)";
		url = "https://seereal.lh.or.kr/main.do?menuCd=goLotdetailInfo&pnu="+pnuCode;
	}
	
	window.open(url, pageTitle,'width=750px,height=1100px,scrollbars=no');
	
}

/*
 * 필지 팝업 
 */
function fnOtherAddrPopup(){
	
	var smid = $("input[name=smid]").val(); 
	
	$('<div id="otherAddrDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("selectOtherAddrPopup.do?smid="+smid);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 150,
		width: 300,
		title: "관계지적 목록"
	});
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
		url:"/sys/vyt/frd/sld/selectCompentAuth.do",
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
		url:"/sys/vyt/frd/sld/selectCompentAuth.do",
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

/*
 * 분석파일 일괄 다운로드
 */
function fncDownloadAnalAll(smid){
	$(".loading-div").show();
	
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	formdata.append("smid",smid);
	
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
	xhr.open('POST', '/sys/vyt/frd/sld/selectDownloadAnalAll.do');
	xhr.responseType = 'blob';
	xhr.send(formdata);
}