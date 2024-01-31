$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
    $('#svysldNmView, #writerView, #rankView, #labelView').keypress(function(event) {    		
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
});

/**
 * 대상지목록 상세화면
 * @param id
 * @returns
 */
function fncSvySldInfo(id) {
	$("input[name=id]").val(id);
	
	if($("input[name=pageCheckValue]").val() == 'listView'){
		var svysldNm = $("input[name=svysldNmView]").val();
		$("input[name=svysldNm]").val(svysldNm);
		var writer = $("input[name=writerView]").val();
		$("input[name=writer]").val(writer);
	}
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("#listForm").attr("action","/sys/lss/lcp/sld/selectSvySldInfo.do");
	$("#listForm").submit();
	if($("input[name=pageCheckValue]").val() == 'detailView'){
		$("#searchForm").submit();
	}
	
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
			$(".ui-widget-overlay").css({
				opacity: 0.5,
				filter: "Alpha(Opacity=50)",
				backgroundColor: "black"
			});
			$(this).load("/sys/lss/lcp/sld/stripLandExcelPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 350,
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
	
	$(".drag-div").append('<div class="progress"><div></div></div>');
	
	var jqXHR=$.ajax({
		url:"/sys/lss/lcp/sld/uploadStripLandExcel.do",
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
function fncAddStripLandInsertView(sldId,year) {
	if(sldId == null || sldId == undefined) sldId =0;
	$('input[name=year]').val(year);
	$('input[name=sldId]').val(sldId);
	
	$("input[name=svysldNm]").remove();//중복제거
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	
	$("#listForm").attr("action","/sys/lss/lcp/sld/insertStripLandView.do");
	$("#listForm").submit();
}

/**
 * 대상지 등록
 * @returns
 */
function fncStripLandInsert() {
//	alert("아직 지원되지 않는 기능입니다.");
//	return false;
	var totalCnt = parseInt($('input[name=sdTotalCnt]').val());
	var message = "총 "+totalCnt+"건의 조사대상지를 등록하시겠습니까?";
	if(totalCnt < 0) message = "총 "+totalCnt+"건의 조사대상지를 삭제하시겠습니까?";
	var svySldYear = $('#year').val();
	if(svySldYear == null || svySldYear == ""){
		alert("조사연도를 선택해주세요."); 
		return false;
	}
	
	
	if(confirm(message)){
		var errChk = false;
		var sdNmArr = [];
		var sdCntArr = [];
		var sdNm = "";
		var errMsg = "";
		$('input[name*=_cnt]').each(function(){
			var sdCnt = $(this).val();
			var sdCd = $(this).attr("name").split("_",1);
			var finalCnt = 0;
			if(sdCnt < 0){
				finalCnt = parseInt(sdCnt) + parseInt($('input[name='+sdCd+'_sldInfoCnt]').val());
				if(finalCnt < 0){
					sdNm = $('input[name='+sdCd+'_nm]').val();
					errMsg = sdNm+"의 등록된 대상지 건수 보다 더 많은 대상지 건수를 삭제할 수 없습니다.";
					errChk = true;
					return false;
				}
			}else{
				finalCnt = parseInt($('input[name='+sdCd+'_rankInfoCnt]').val()) - parseInt(sdCnt);
				if(finalCnt < 0){
					sdNm = $('input[name='+sdCd+'_nm]').val();
					errMsg = sdNm+"의 미조사된 대상지 건수 보다 더 많은 대상지 건수를 등록할 수 없습니다.";
					errChk = true;
					return false;
				}
			}
			if(sdCnt == null || sdCnt == "") sdCnt = 0;
			sdNmArr.push($(this).attr('name').split('_',1));
			sdCntArr.push(sdCnt);
		});
		if(errChk){
			alert(errMsg);
			return false;
		} 
		$('input[name=sdCdList]').val(sdNmArr);
		$('input[name=sdCntList]').val(sdCntArr);

    	$("#listForm").ajaxForm({
			type: 'POST',
        	url: $("#listForm")[0].action,
        	dataType: "json",
        	beforeSubmit: function(data,form,option){
        		$('.loading-div').show();
        	},
        	success: function(data) {
        		$('.loading-div').hide();
        		if (data.status == "success") {
                	alert(data.message);
                	location.href="/sys/lss/lcp/sld/selectLcpSvySldList.do";
            	} else {
                    alert(data.message);
					location.href="/sys/lss/lcp/sld/selectLcpSvySldList.do";
                }
	    	},
    		error: function(data) {
    			$('.loading-div').hide();
    			alert(data.message);
    		}
		}).submit();
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
        	url: "/sys/lss/lcp/sld/removeLssLcpStripLand.do",
        	dataType: "json",
        	success: function(data) {
        		if (data.status == "success") {
                	alert(data.message);
                	location.href="/sys/lss/lcp/sld/selectStripLandList.do";
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
function fncUpdateSvySldInfoView(){
	$("#listForm").attr("action","/sys/lss/lcp/sld/updateLssLcpSvySldInfoView.do");
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
		
        if(!validateLssLcpStripLand(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
	        	url: $("#listForm")[0].action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/sys/lss/lcp/sld/selectStripLandList.do";
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

/**
 * 조사대상지 지역별 등록 조회 
 * @param event
 * @returns
 */
function fncSdCntChange(event,adminCode){
	var totalCnt = 0;
	$('input[name="sdCnt"]').each(function(){
		if(!isNaN(parseInt($(this).val()))) totalCnt += parseInt($(this).val()); 
	});
	var test  = event.target.value;
	$("input[name=sdTotalCnt]").val(totalCnt);
	$("input[name="+adminCode+"_cnt]").val(event.target.value);
	$('.sdTotalCnt').text(totalCnt+" 건");
	
//	if(code == null || code == undefined || code.length == 0){
//		$("#svyRegion2View").empty().append("<option value>선택하세요</option>");
//		return false;
//	}
}

/**
 * 조사대상지 시도별 추가 건수 설정
 * @returns
 */
function fncSdCntPlus(adminCode){
	var plusSdCnt = $('input[name*='+adminCode+'_cnt]').val();
	plusSdCnt = "+" + plusSdCnt.replace(/[^0-9]/g,"");
	$('input[name*='+adminCode+'_cnt]').val(plusSdCnt);
	$('.'+adminCode).attr("type","text");
	$('.'+adminCode).val(plusSdCnt);
	
	var totalCnt = 0;
	$('input[name="sdCnt"]').each(function(){
		if(!isNaN(parseInt($(this).val()))) totalCnt += parseInt($(this).val()); 
	}); 
	$("input[name=sdTotalCnt]").val(totalCnt);
	$('.sdTotalCnt').text(totalCnt+" 건");
}

/**
 * 조사대상지 시도별 추가 건수 설정
 * @returns
 */
function fncSdCntMinus(adminCode){
	var minusSdCnt = $('input[name*='+adminCode+'_cnt]').val();
	minusSdCnt = "-" + minusSdCnt.replace(/[^0-9]/g,"");
	$('input[name*='+adminCode+'_cnt]').val(minusSdCnt);
	$('.'+adminCode).attr("type","text");
	$('.'+adminCode).val(minusSdCnt);
	
	var totalCnt = 0;
	$('input[name="sdCnt"]').each(function(){
		if(!isNaN(parseInt($(this).val()))) totalCnt += parseInt($(this).val()); 
	});
	$("input[name=sdTotalCnt]").val(totalCnt);
	$('.sdTotalCnt').text(totalCnt+" 건");
}

/**
 * 대상지정보 상세화면
 * @param id
 * @returns
 */
function fnSvySldInfoDetail(sldId, uniqId) {

	$("input[name=sldId]").val(sldId);
	$("input[name=uniqId]").val(uniqId);
	
	var detailPageIndex = $("#searchForm > input[name=pageIndex]").val();
	var detailPageUnit = $("#searchForm > input[name=pageUnit]").val();

	 $("#listForm > input[name=pageIndex]").val(detailPageIndex);
	 $("#listForm > input[name=pageUnit]").val(detailPageUnit);
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	
	$("#listForm").attr("action","/sys/lss/lcp/sld/selectSvySldInfoDetail.do");
	$("#listForm").submit();
}

/**
 * 대상지등록 정보 삭제
 * @param id
 * @returns
 */
function fnDeleteSvySldRegInfo (id) {
	$("input[name=id]").val(id);
	var svysldNm = $("input[name=svySldNm]").val();
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	if(confirm(svysldNm+"를 삭제하시겠습니까?")) {
		if(confirm("삭제시 조사된 정보도 함께 삭제가 됩니다.\n그래도 삭제하시겠습니까?")) {
			$("#listForm").ajaxForm({
				type: 'POST',
		        url: "/sys/lss/lcp/sld/deleteSvySldRegInfo.do",
		        dataType: "json",
		        success: function(data) {
		        	if (data.status == "success") {
	                    alert(data.message);
	                    location.href="/sys/lss/lcp/sld/selectLcpSvySldList.do";
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
 * 조사대상지 엑셀다운로드
 * @param id
 * @returns
 */
function fncStripLandExcel(id){
//	$(".loading-div").show();
//	var url = "/sys/lss/lcp/sld/selectLcpSvySldListExcel.do";
//	var form = $("<form></form>").attr("action",url).attr("method","post");
//	form.append($('<input/>', {type: 'hidden', name: "sldId", value:id }));
//	form.appendTo("body").submit().remove();
//	$(".loading-div").hide();
	$(".loading-div").show();
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
//	formdata.append("sldId",id);
	var hiddens = $("#searchForm").find("input[type=hidden]");
	$.each(hiddens,function(idx,elm){
		formdata.append($(elm).attr("name"),$(elm).val())
	});
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4 && this.status == 200){
			$(".loading-div").hide();
			var filename = "";
			var disposition = xhr.getResponseHeader('Content-Disposition');
			if (disposition && disposition.indexOf('attachment') !== -1) {
				var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
				var matches = filenameRegex.exec(disposition);
				if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
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
	xhr.open('POST', '/sys/lss/lcp/sld/selectLcpSvySldListExcel.do');
	xhr.responseType = 'blob';
	xhr.send(formdata);
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
	
	var svysldNm = $("input[name=schsvysldNm]").val();
	if(svysldNm == ''){
		 $("input[name=svysldNm]").val('');
	}
	
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
/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var svysldNm = $("input[name=schsvysldNm]").val();
	if(svysldNm == ''){
		 $("input[name=svysldNm]").val('');
	}
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/lcp/sld/selectLcpSvySldList.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}