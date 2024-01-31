$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
	
	// 대상지관리 -> 상세조회 rowspan 동적 처리
 

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
function fncMseSldDetail(sldId) {
	$("input[name=sldId]").val(sldId);
	$("#listForm").attr("action","/sys/fck/mse/sld/selectMseSldDetail.do");
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
function fncAddSldInsertView() {
	location.href="/sys/fck/mse/sld/insertMseSldView.do";
}

/**
 * 대상지 등록
 * @returns
 */
function fncMseSldManage(manageSe) {
	var message = "등록하시겠습니까?";
	var url = "/sys/fck/mse/sld/insertMseSld.do";
	
	if(manageSe == 'update'){
		message = "수정하시겠습니까?";
		url = "/sys/fck/mse/sld/updateMseSld.do"
	} 
	
	if(confirm(message)){
		var params = $("#listForm *[name*=View]");
		$.each(params,function(idx,elm){
			var val = $(elm).val();
			var text = $(elm.selectedOptions).text();
			if(val != ""){
				var hidden = $(elm).attr("name").replace("View","");
				$("input[name=".concat(hidden,"]")).val(text);
			}
		});
		
		// 통신모뎀번호 및 이동전화번호 JSON 조립
		var modemNumArr = '[';
		var cellNumArr = '[';
		$("#mseGateway tr[class*=channel]").each(function(i){
			var numId = $("input[name*=gateWayId]").eq(i).val();
			var modemVal = $("input[name*=modemNumView]").eq(i).val();
			var cellval = $("input[name*=cellNumView]").eq(i).val();
			
			if(modemVal != null && modemVal != "") modemNumArr = modemNumArr.concat('{"'+numId+'":"'+modemVal+'"},');
			if(cellval != null && cellval != "") cellNumArr = cellNumArr.concat('{"'+numId+'":"'+cellval+'"},');
		});
		
		if(modemNumArr.length > 2){
			modemNumArr = modemNumArr.slice(0,-1).concat(']');
		}else{
			modemNumArr = null;
		}
		
		if(cellNumArr.length > 2){
			cellNumArr = cellNumArr.slice(0,-1).concat(']');
		}else{
			cellNumArr = null;
		}
		$("input[name=modemNum]").val(modemNumArr);
		$("input[name=cellNum]").val(cellNumArr);
		
		// 계측장비 JSON 조립
		$("input[name*=Count]").each(function(){
			var msSensorArr = '[';
			var msSensorSubArr = '[';
			var type = $(this).attr("name");
			var id = $(this).attr("id");
			var val = $(this).val();
			console.log("type : "+ type);
			console.log("id : "+ id);
			console.log("val : "+ val);
			if(val != "" && val != "0"){
				if(type.includes("licMeter")){
					$("#mseLicMeterXY tr:gt(0)").each(function(i){
						var id = $("#mseLicMeterXY th[class*=licMeterId]").eq(i).text();
						msSensorArr = msSensorArr.concat('{"채널명":"'+id+'"},');
						$(this).find("input[name=licHg]").each(function(){
							msSensorSubArr = msSensorSubArr.concat('{"채널명":"'+id+'('+$(this).val()+' m X축)"},');
							msSensorSubArr = msSensorSubArr.concat('{"채널명":"'+id+'('+$(this).val()+' m Y축)"},');
						});
					});
				}else{
					for(var i = 0;i<val;i++){
						var cnt = i+1;
						if(type.includes("node") || type.includes("gateway")){
							msSensorArr = msSensorArr.concat('{"연번":"'+cnt+'"},');
						}else{
							if(cnt >= 10){
								msSensorArr = msSensorArr.concat('{"채널명":"'+id+'-0'+cnt+'"},');
							}else{
								msSensorArr = msSensorArr.concat('{"채널명":"'+id+'-00'+cnt+'"},');
							}
						}
					};
				}
				
				if(msSensorArr.length > 2){
					msSensorArr = msSensorArr.slice(0,-1).concat(']');
				}else{
					msSensorArr = null;
				}
				
				$("input[name="+type.replace("Count","")+"]").val(msSensorArr);
				
				if(type.includes("licMeter")){
					if(msSensorSubArr.length > 2){
						msSensorSubArr = msSensorSubArr.slice(0,-1).concat(']');
					}else{
						msSensorSubArr = null;
					}					
					$("input[name="+type.replace("Count","PerCheck")+"]").val(msSensorSubArr);
				}
			}
		});
		
		$("#listForm").attr("action",url);
		$("#listForm").ajaxForm({
			type: 'POST',
        	url: $("#listForm")[0].action,
        	dataType: "json",
        	success: function(data) {
        		if (data.status == "success") {
                	alert(data.message);
                	location.href="/sys/fck/mse/sld/selectMseSldList.do";
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
function fncUpdateMseSldView(){
	$("#listForm").attr("action","/sys/fck/mse/sld/updateMseSldView.do");
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
 * 행정구역 시군구 조회 ajax
 * @returns
 */
function fncAdministCtprvnChange(event){
	
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#svySggView").empty().append("<option value>전체</option>");
		$("#svyEmdView").empty().append("<option value>전체</option>");
		$("#svyRiView").empty().append("<option value>전체</option>");
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
					$("#svySggView").empty().append("<option value>전체</option>");
        			$("#svyEmdView").empty().append("<option value>전체</option>");
        			$("#svyRiView").empty().append("<option value>전체</option>");
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
		$("#svyEmdView").empty().append("<option value>전체</option>");
		$("#svyRiView").empty().append("<option value>전체</option>");
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
					$("#svyEmdView").empty().append("<option value>전체</option>");
        			$("#svyRiView").empty().append("<option value>전체</option>");
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
		$("#svyRiView").empty().append("<option value>전체</option>");
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
					$("#svyRiView").empty().append("<option value>전체</option>");
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

/*
 * 대상지관리 채널 추가
 */
function fncMseSttusAdd(type){
	var mseSttusLen = 1;
	
	if(type == "lic"){
		if($("#mseLicMeterXY tr").is("[class*=channel]")) mseSttusLen = parseInt($("#mseLicMeterXY tr:last()").attr("class").slice(-1))+1;
		$("#mseLicMeterXY tbody tr:gt(0) td").append("<input type=\"text\" name=\"licHg\" value=\"\" />");
		
	}else{
		if($("#mseGateway tr").is("[class*=channel]")) mseSttusLen = parseInt($("#mseGateway tr:last()").attr("class").slice(-1))+1;
		var contents = "";
		contents+= "<tr class=\"channel"+mseSttusLen+"\">";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"gateWayIdView"+mseSttusLen+"\" value=\"\" />";
		contents+= "</td>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"modemNumView"+mseSttusLen+"\" value=\"\" />";
		contents+= "</td>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"cellNumView"+mseSttusLen+"\" value=\"\" />";
		contents+= "</td>";
		contents+= "</tr>";
		$("#mseGateway tbody").append(contents);
	}
}

/*
 * 대상지관리 채널 삭제
 */
function fncMseSttusDel(type){
	
	if(type == "lic"){
		$("#mseLicMeterXY tr td").each(function(i){
			$("#mseLicMeterXY tr td:eq("+i+") input:last()").remove();
		});		
		if($("#mseLicMeterXY tr").is("[class*=channel]")){
			var lcpSttusLen = $("#mseLicMeterXY tr:last()").attr("class").split(' ').pop();
		}
	}else{
		if($("#mseGateway tr").is("[class*=channel]")){
			var lcpSttusLen = $("#mseGateway tr:last()").attr("class").split(' ').pop();
			$("#mseGateway ."+lcpSttusLen).remove();		
		}		
	}
}

/**
 * 대상지 관리 목록 화면
 * @returns
 */
function fncList() {
	location.href="/sys/fck/mse/sld/selectMseSldList.do";
}

/**
 * 소재지 소유구분 검색
 * @returns
 * @Description
 */
function fncOwnerSearch(){
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	
	var sd = $("input[name=svySd]").val();
	var sgg = $("input[name=svySgg]").val();
	var emd = $("input[name=svyEmd]").val();
	var jibun = $("input[name=svyJibun]").val();
	
	if(sd == ""){
		alert("시도 입력이 되지않았습니다.");
		return false;
	}else if(sgg == ""){
		alert("시군구 입력이 되지않았습니다.");
		return false;
	}else if(emd == ""){
		alert("읍면동 입력이 되지않았습니다.");
		return false;
	}else if(jibun == ""){
		alert("지번 입력이 되지않았습니다.");
		return false;
	}
	
	$("#listForm").attr("action","/sys/fck/mse/sld/selectMseSldAddrOwner.do");
	$("#listForm").ajaxForm({
		type: 'POST',
		url: $("#listForm")[0].action,
		dataType: "json",
		success: function(data) {
			if (data.status == "success") {
				$("input[name=pnucode]").val(data.pnuCode);
				$("input[name=svyLon]").val(data.svyLon);
				$("input[name=svyLat]").val(data.svyLat);
				$("input[name=svyData]").val(data.svyData);
				$("input[name=owner]").val(data.returnValue);
//	        	alert(data.message);
	        	
	    	} else {
	            alert(data.message);
	        }
		},
		error: function(data) {
			alert(data.message);
		}
	}).submit();
}

/**
 * 지중경사계 개수 체크
 * @returns
 * @Description
 */
function licCount(event){
	var codeVal = event.value;
	
	if(codeVal != ""){
		var licCnt = $("#mseLicMeterXY tr[class*=channel]").length;
		var licHgCnt = $("input[name=licHg]").length;
//		if(licCnt == 0) licCnt = 1;
		if(licCnt > codeVal) $("#mseLicMeterXY tr:gt("+codeVal+")").remove();			
		
		for(var i = licCnt;i<codeVal;i++){
			var j = i+1;
			var id = "INC-00"+j;
			if(i >= 10) id = "INC-0"+j;
			var contents = "";
			contents+= "<tr class=\"channel"+j+"\">";
			contents+= "<th class=\"licMeterId"+j+"\">"+id+"</th>";
			contents+= "<th>X/Y축</th>";
			contents+= "<td>";
			if(licCnt != 0 && licHgCnt != 0){
				var licHgCnt = licHgCnt / licCnt;
				var k = 0;
				while(k<licHgCnt){
					contents+= "<input type=\"text\" name=\"licHg\" value=\"\" />";
					k++;
				}
			}
			contents+= "</td>";
			contents+= "</tr>";
			$("#mseLicMeterXY tbody").append(contents);
			
		}
	}else{
		$("#mseLicMeterXY tr:gt(0)").remove();
	}
}

/**
 * 토지이용계획열람 팝업 생성
 * @returns
 * @Description
 */
function fncLadUsePlanPopup(){
	var pnuCode = $("input[name=pnuCode]").val();
	if(pnuCode == ""){
		fncOwnerSearch();
		pnuCode = $("input[name=pnuCode]").val();
	}
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	var sd = $("input[name=svySd]").val();
	var sgg = $("input[name=svySgg]").val();
	var emd = $("input[name=svyEmd]").val();
	var ri = $("input[name=svyRi]").val();
	var jibun = $("input[name=svyJibun]").val();
	var landGbn = 1;
	
	if(sd == ""){
		alert("시도 입력이 되지않았습니다.");
		return false;
	}else if(sgg == ""){
		alert("시군구 입력이 되지않았습니다.");
		return false;
	}else if(emd == ""){
		alert("읍면동 입력이 되지않았습니다.");
		return false;
	}else if(jibun == ""){
		alert("지번 입력이 되지않았습니다.");
		return false;
	}
	
	if(jibun.includes("산")) landGbn = 2;
	jibun = jibun.replaceAll(/[^0-9]/g,'');
	var url = "http://www.eum.go.kr/web/ar/lu/luLandDet.jsp?mode=search&selGbn=umd&isNoScr=script&pnu="+pnuCode+"&selSido="+sd+"&selSgg="+sgg+"&selUmd="+emd+"&selRi="+ri+"&landGbn="+landGbn+"&bobn="+jibun+"&bubn=0000";
	
	window.open(url,'토지이용계획열람','width=750px,height=1100px,scrollbars=yes');
}

/**
 * 대상지 삭제
 * @param id
 * @returns
 */
function fncDeleteMseSld(){
	if(confirm("삭제하시겠습니까?")) {
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/fck/mse/sld/deleteMseSld.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/fck/mse/sld/selectMseSldList.do";
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
		var text = $(elm.selectedOptions).text();
		if(val != ""){
			var hidden = $(elm).attr("name").replace("View","");
			$("input[name=".concat(hidden,"]")).val(text);
		}
	});
	$("#listForm").submit();
}