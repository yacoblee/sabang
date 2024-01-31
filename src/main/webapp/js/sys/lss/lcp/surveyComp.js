$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
	
	$('#svyId,#svyUser,#mstNm').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
	
	//보호대상
	//saftyVal
	$("#listForm tbody *[name*=Score]").on("change",function(){
		var name = $(this).attr("name");
		var elmId = name.replace("Score","");
		$("input[name=".concat(elmId,"]")).val($(this).val());
		
		var sum = 0;
		$("#listForm tbody *[name*=Score]").each(function(idx,elm){
			var num = $(elm).val();
			sum = sum+parseInt(num ? num : 0);
		});
		
		$("#svySm").val(sum);
		
		if(sum > 60){
			$("#ncssty").val("필요");
		}else{
			$("#ncssty").val("불필요");
		}
	});
	
	$(document).on("click",".photoUrl > span.thumb-div",function(){
		var photoClass = $(this).prevAll("input").attr("name");
		$('<div id="photoDiv">').dialog({
			modal:true,
			open: function(){
				// 모달 오버레이 설정
				$(".ui-widget-overlay").css({
					opacity: 0.5,
					filter: "Alpha(Opacity=50)",
					backgroundColor: "black"
				});
				var $photo = JSON.parse($("input[name=photolist]").val());
				$.each($photo,function(idx,elm){
					var cnt= idx+1;
					$("#photoDiv").append("<img id=photoSrc"+cnt+" src=\"/storage/fieldBook".concat(elm,"\">"));
				});
				//$(this).load("/sys/lss/lcp/sld/stripLandExcelPopup.do");
			},
			close: function(e){
				$(this).empty();
				$(this).dialog("destroy");
			},
			height: 550,
			width: 600,
			buttons:{
				"변경" : function(){
					var src = $(".selected").attr("src");
					$('.'+photoClass).attr("src",src);
					$('.'+photoClass).css('width','');
					$('input[name='+photoClass+']').val(src.substr(18));
					$(this).dialog("close");
				},
				"취소" : function(){
					$(this).dialog("close");
				}
				
			},
			title: "현장사진목록"
		});
	});
	
	
	$(document).on("click","#photoDiv img",function(){
		var selected = $(this).hasClass("selected");
		if(selected){
			$(this).removeClass("selected");
		}else{
			$("#photoDiv img").removeClass("selected");
			$(this).addClass("selected");
		}
	});
	
	$(document).on("propertychange change keyup paste input","#listForm tbody input[name*=Val]",function(){
		
	});
	
	$(document).on("propertychange change keyup paste input","#listForm tbody input[name*=Score]",function(){
		
	});
	
	$(document).on("change","input[name=rskFactors]",function(){
		var rskFactors = [];
		var rskFactorScore = 0;
		var prntTd = $(this).closest("td");
		
		$("input[name=rskFactors]").each(function(idx,elm){
			if($(elm).is(":checked")){
				rskFactors.push($(elm).val());
			}
		});
		
		if($(prntTd).hasClass("devRskFactor")){
			if(/^(?=.*전석)(?=.*침식)(?=.*붕괴).*$/.test(rskFactors)){
				rskFactorScore = 20;
			}else if(/^(?=.*전석)(?=.*침식).*$|^(?=.*전석)(?=.*붕괴).*$|^(?=.*침식)(?=.*붕괴).*$/.test(rskFactors)){
				rskFactorScore = 15;
			}else if(/전석/.test(rskFactors)){
				rskFactorScore = 3;
			}else if(/침식/.test(rskFactors)){
				rskFactorScore = 5;
			}else if(/붕괴/.test(rskFactors)){
				rskFactorScore = 7;
			}
		}else{
			if(/^(?=.*침식\(토사\))(?=.*유실\(토사\))(?=.*붕괴\(토사\)).*$|^(?=.*균열\(암반\))(?=.*이완암\(암반\))(?=.*붕괴\(암반\)).*$/.test(rskFactors)){
				rskFactorScore = 20;
			}else if(/^(?=.*침식\(토사\))(?=.*유실\(토사\)).*$|^(?=.*침식\(토사\))(?=.*붕괴\(토사\)).*$|^(?=.*유실\(토사\))(?=.*붕괴\(토사\)).*$|^(?=.*균열\(암반\))(?=.*이완암\(암반\)).*$|^(?=.*균열\(암반\))(?=.*붕괴\(암반\)).*$|^(?=.*이완암\(암반\))(?=.*붕괴\(암반\)).*$/.test(rskFactors)){
				rskFactorScore = 15;
			}else if(/붕괴\(토사\)|붕괴\(암반\)/.test(rskFactors)){
				rskFactorScore = 10;
			}else if(/^(?=.*유실\(토사\))|(?=.*이완암\(암반\)).*$/.test(rskFactors)){
				rskFactorScore = 5;
			}else if(/^(?=.*침식\(토사\))|(?=.*균열\(암반\)).*$/.test(rskFactors)){
				rskFactorScore = 3;
			}
		}
		
		$("#rskFactorVal").val(rskFactors.join(","));
		$("input[name=rskFactorScore]").val(rskFactorScore).trigger("change");
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
 * 조사완료지 상세화면
 * @param id
 * @returns
 */
function fncSvyComptDetail(gId) {
	$("input[name=gid]").val(gId);
	$("#listForm").attr("action","/sys/lss/lcp/sct/selectLcpSvyComptDetail.do");
	$("#listForm").submit();
}

/**
 * 조사완료지 삭제
 * @param id
 * @returns
 */
function fncDeleteSvyCompt(gid){
	if(confirm("삭제하시겠습니까?")) {
		$("input[name=gid]").val(gid);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/lss/lcp/sct/deleteLcpSvyCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/lss/lcp/sct/selectLcpSvyComptList.do";
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
 * 조사완료지 수정화면
 * @returns
 */
function fncUpdateSvyComptView(){
//	$("#listForm").attr("action","/sys/lss/lcp/sct/updateLcpSvyComptView.do");
//	$("#listForm").submit();
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/lcp/sct/updateLcpSvyComptView.do").attr("method","post");

	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.append($('<input/>', {type: 'hidden', name: "gid", value:$("input[name=gid]").val() }));
	
	form.appendTo("body").submit().remove();
}

/**
 * 조사완료지 수정
 * @returns
 */
function fncSvyComptUpdate(){
	//        if(!validateLssLcpStripLand(form)){
	//        	return false;
	//        }else{
	//        	form.submit();
	//        }
	
	if(confirm("수정하시겠습니까?")) {
		if($("input[name=photolist]").val() != null && $("input[name=photolist]").val() != ""){
			var returnChk = false;
			var photoTagArr = '[';
				$(".photoBox").each(function(i){
				var photoTag = $('input[name*=photoTag]:eq('+(i+1)+')').val();
				var photoSrc = $('input[name*=photoSrc]:eq('+i+')').val();
				photoTagArr = photoTagArr.concat('{"TAG":"'+photoTag+'","FILENAME":"'+photoSrc+'"},');
			});
			if(returnChk) return false;
			if(photoTagArr.length > 2){
				photoTagArr = photoTagArr.slice(0,-1).concat(']');
			}else{
				photoTagArr = null;
			}
			$('input[name=photoTagList]').val(photoTagArr);
		}
		
		$(".lcpSttus").each(function(){
			var tableId = $(this).attr("id");
			var lcpSttusArr = '[';
			$("."+tableId).each(function(e){
				if(tableId == "lcpSttusCrk" || tableId == "lcpSttusStp"){
					var typeLen = $("."+tableId).length/2;
					
					//시점
					var bpx1 = parseInt($("."+tableId+" input[name=bpx1]").eq(e).val());
					var bpx2 = parseInt($("."+tableId+" input[name=bpx2]").eq(e).val());
					var bpx3 = parseFloat($("."+tableId+" input[name=bpx3]").eq(e).val());
					var bpx = bpx1+ (bpx2/60) + (bpx3/3600);
					var bpy1 = parseInt($("."+tableId+" input[name=bpy1]").eq(e).val());
					var bpy2 = parseInt($("."+tableId+" input[name=bpy2]").eq(e).val());
					var bpy3 = parseFloat($("."+tableId+" input[name=bpy3]").eq(e).val());
					var bpy = bpy1+ (bpy2/60) + (bpy3/3600);
					//종점
					var epx1 = parseInt($("."+tableId+" input[name=epx1]").eq(e).val());
					var epx2 = parseInt($("."+tableId+" input[name=epx2]").eq(e).val());
					var epx3 = parseFloat($("."+tableId+" input[name=epx3]").eq(e).val());
					var epx = epx1+ (epx2/60) + (epx3/3600);
					var epy1 = parseInt($("."+tableId+" input[name=epy1]").eq(e).val());
					var epy2 = parseInt($("."+tableId+" input[name=epy2]").eq(e).val());
					var epy3 = parseFloat($("."+tableId+" input[name=epy3]").eq(e).val());
					var epy = epy1+ (epy2/60) + (epy3/3600);
					
					// 시점 고도
					var bpz = $("."+tableId+" input[name=bpz]").eq(e).val();
					// 종점 고도
					var epz = $("."+tableId+" input[name=epz]").eq(e).val();
					// 연장
					var length = $("."+tableId+" input[name=l]").eq(e).val();
					// 높이
					var height = $("."+tableId+" input[name=h]").eq(e).val();
					// 깊이
					var depth = $("."+tableId+" input[name=d]").eq(e).val();
					if (e < typeLen){
						lcpSttusArr = lcpSttusArr.concat('{"시점_경도":"'+bpx+'","시점_위도":"'+bpy+'","시점_고도":"'+bpz+'","종점_경도":"'+epx+'","종점_위도":"'+epy+'","종점_고도":"'+epz+'","연장":"'+length+'","높이":"'+height+'","깊이":"'+depth+'"},');							
					}
				}else{
					var bpx1 = parseInt($("."+tableId+" input[name=bpx1]").eq(e).val());
					var bpx2 = parseInt($("."+tableId+" input[name=bpx2]").eq(e).val());
					var bpx3 = parseFloat($("."+tableId+" input[name=bpx3]").eq(e).val());
					var bpx = bpx1+ (bpx2/60) + (bpx3/3600);
					var bpy1 = parseInt($("."+tableId+" input[name=bpy1]").eq(e).val());
					var bpy2 = parseInt($("."+tableId+" input[name=bpy2]").eq(e).val());
					var bpy3 = parseFloat($("."+tableId+" input[name=bpy3]").eq(e).val());
					var bpy = bpy1+ (bpy2/60) + (bpy3/3600);
					// 시점 고도
					var bpz = $("."+tableId+" input[name=bpz]").eq(e).val();
					// 연장
					var length = $("."+tableId+" input[name=l]").eq(e).val();
					// 높이
					var height = $("."+tableId+" input[name=h]").eq(e).val();
					// 깊이
					var depth = $("."+tableId+" input[name=d]").eq(e).val();
					
					lcpSttusArr = lcpSttusArr.concat('{"시점_경도":"'+bpx+'","시점_위도":"'+bpy+'","시점_고도":"'+bpz+'","연장":"'+length+'","높이":"'+height+'","깊이":"'+depth+'"},');
				}
			});
			lcpSttusArr = lcpSttusArr.slice(0,-1).concat(']');
			if(lcpSttusArr.length > 2) $("input[name="+tableId+"]").val(lcpSttusArr);
		});
		
		var params = $("#listForm *select");
		$.each(params,function(idx,elm){
			var val = $(elm).val();
			if(val.includes("Etc")){
				var etcText = $("input[name=".concat(val,"]")).val();				
				$(elm).append($("<option></option>").attr("value",etcText).text("기타").attr("selected","selected"));
			}
		});
		
		$("#listForm").ajaxForm({
			type: 'POST',
			url: $("#listForm")[0].action,
			dataType: "json",
			success: function(data) {
				if (data.status == "success") {
					alert(data.message);
					location.href="/sys/lss/lcp/sct/selectLcpSvyComptList.do";
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

function fncOpenClipReport(gId,svyType,svyYear,lastgrd){
	var url = "/cmm/openClipReport.do";
	var fileName = "";
	var form = null;
	
	if(svyType.indexOf("땅밀림") > -1){
		if(lastgrd == "C"){
			fileName = "REPORT_SYS_LSS_LCP_01";
		}else{
			fileName = "REPORT_SYS_LSS_LCP_02";
		}
	}
	
	$("form[name=reportForm]").remove();
	
	form = $("form[name=reportForm]");
	
	if(form.length == 0){
		form = $("<form></form");
		form.attr("name","reportForm");
		form.append("<input type=\"hidden\" name=\"nowPge\" value=\"1\">");
		form.append("<input type=\"hidden\" name=\"rptName\" value=\"".concat(fileName,"\">"));
		form.append("<input type=\"hidden\" name=\"gid\" value=\"\">");
		form.appendTo("body");
	}
	
	form.find("input[name=gid]").val(gId);
	
	window.open('',fileName,'width=1100, height=800, scrollbars=1');
	
	form.attr("action",url);
	form.attr("method","post");
	form.attr("target",fileName);
	form.submit();
	form.remove();
	
	return false;
}

/**
 * 최종판정등급 변경
 * @returns
 */
 function changeLastGrd(obj){
	var changeGrd = $(obj).val();
	$(".lastgrd span").text(changeGrd);
	$("input[name=lastgrd]").val(changeGrd);	
}

/**
 * 조사완료 및 수정페이지 판정표 점수
 * @returns
 */
/**
 * 조사완료 및 수정페이지 판정표 점수
 * @returns
 */
function select(obj){
	var selectVal = $(obj).val();
  	var plnform = $(".plnform option:selected").val();
  	var lngform = $(".lngform option:selected").val();
	
	//암석풍화_4
	if(selectVal == '풍화암' || selectVal == '연암' || selectVal == '보통암' || selectVal == '경암'){
		$('.rokwthrval').text(selectVal);
 		if(selectVal == '풍화암'){
        	$('.rokwthrscore').text('7');
			$('input[name=rokwthrscore]').val('7');
     	}else if(selectVal == '연암'){
	        $('.rokwthrscore').text('4');
			$('input[name=rokwthrscore]').val('4');		
		}else if (selectVal == '보통암'){
        	$('.rokwthrscore').text('3');
			$('input[name=rokwthrscore]').val('3');
     	}else{
			$('.rokwthrscore').text('2');
			$('input[name=rokwthrscore]').val('2');
 		}
	}
	
	// 토양수분상태_8
  	if(selectVal == '습' || selectVal == '약습' || selectVal == '적윤' || selectVal == '약건' || selectVal == '건조'){
    	$('.soilwtrval').text(selectVal);
     	if(selectVal == '습'){ 
        	$('.soilwtscore').text('5');
			$('input[name=soilwtscore]').val('5');
 		}else if(selectVal == '약습'){
        	$('.soilwtscore').text('4');
			$('input[name=soilwtscore]').val('4');
     	}else if(selectVal == '적윤'){
        	$('.soilwtscore').text('3');
			$('input[name=soilwtscore]').val('3');
     	}else if(selectVal == '약건'){
        	$('.soilwtscore').text('2');
			$('input[name=soilwtscore]').val('2');
     	}else{
        	$('.soilwtscore').text('1');
			$('input[name=soilwtscore]').val('1');
		}
	}
	
	//모암_3
  	if(selectVal == '퇴적암' || selectVal == '변성암' || selectVal == '화성암(심성암류)' || selectVal == '화성암(화산암류)'){
    	$('.cmprokval').text(selectVal);
     	if(selectVal == '퇴적암'){ 
        	$('.cmprokscore').text('8');
			$('input[name=cmprokscore]').val('8');
     	}else if(selectVal == '변성암'){
        	$('.cmprokscore').text('5');
			$('input[name=cmprokscore]').val('5');
     	}else if(selectVal == '화성암(심성암류)' || selectVal == '화성암(화산암)'){
	        $('.cmprokscore').text('2');
			$('input[name=cmprokscore]').val('2');
     	}else{
	        $('.cmprokscore').text('0');
			$('input[name=cmprokscore]').val('0');
     	}
  	}

    //불연속면 사면의 방향성_5  
  	if(selectVal == '비탈면방향' || selectVal == '수직방향' || selectVal == '수평방향' || selectVal == '역방향' || selectVal == '없음'){
    	$('.disctnuplnslpval').text(selectVal);
     	if(selectVal == '비탈면방향'){
        	$('.disctnuplnslpscore').text('9');
			$('input[name=disctnuplnslpscore]').val('9');
		}else if(selectVal == '수직방향'){
			$('.disctnuplnslpscore').text('5');
			$('input[name=disctnuplnslpscore]').val('5');
     	}else if(selectVal == '수평방향'){
			$('.disctnuplnslpscore').text('4');
			$('input[name=disctnuplnslpscore]').val('4');
     	}else if(selectVal == '역방향'){
			$('.disctnuplnslpscore').text('3');
			$('input[name=disctnuplnslpscore]').val('3');
     	}else{
        	$('.disctnuplnslpscore').text('0');
			$('input[name=disctnuplnslpscore]').val('0');
     	}
  	}
    
	//불연속면 간격_6  
  	if(selectVal == '매우조밀(6이하)' || selectVal == '조밀(6~20)' || selectVal == '보통(20~60)' || selectVal == '넓음(60이상)' || selectVal == '없음'){
		$('.disctnuplnintvlval').text(selectVal);
     	if(selectVal == '매우조밀(6이하)'){
	        $('.disctnuplnintvlscore').text('5');
			$('input[name=disctnuplnintvlscore]').val('5');
		}else if(selectVal == '조밀(6~20)'){
        	$('.disctnuplnintvlscore').text('4');
			$('input[name=disctnuplnintvlscore]').val('4');
     	}else if(selectVal == '보통(20~60)'){
        	$('.disctnuplnintvlscore').text('2');
			$('input[name=disctnuplnintvlscore]').val('2');
     	}else if(selectVal == '넓음(60이상)'){
        	$('.disctnuplnintvlscore').text('1');
			$('input[name=disctnuplnintvlscore]').val('1');
     	}else if(selectVal == '없음'){
        	$('.disctnuplnintvlscore').text('0');
			$('input[name=disctnuplnintvlscore]').val('0');
     	}
  	}
	
	//너덜유무_9
  	if(selectVal == '유(상류)' || selectVal == '유(측면)' || selectVal == '무'){
 		$('.talusat').text(selectVal);
     	if(selectVal == '유(상류)' || selectVal == '유(측면)'){
        	$('.talusatscore').text('4');
			$('input[name=talusatscore]').val('0');
     	}else{
        	$('.talusatscore').text('0');
			$('input[name=talusatscore]').val('0');
     	}
  	}
	
	//토성_7
  	if(selectVal == '점질토(SC SCL CL)' || selectVal == '사질양토(SL SIL)' || selectVal == '사질토(S LS)' ){
		$('.soilclassbval').text(selectVal);
     	if(selectVal == '점질토 (SC SCL CL)'){
        	$('.soilclassbscore').text('5');
			$('input[name=soilclassbscore]').val('5');
     	}else if(selectVal == '사질양토 (SL SIL)'){
	        $('.soilclassbscore').text('3');
			$('input[name=soilclassbscore]').val('3');
     	}else if(selectVal == '사질토 (S LS)'){
        	$('.soilclassbscore').text('2');
			$('input[name=soilclassbscore]').val('2');
     	}else if(selectVal == 'soilclassbEtc'){
			$('.soilclassbval').text('');
			$('.soilclassbscore').text('');
			$('input[name=soilclassbscore]').val('0');
	 	}
  	}
    
	//이동대(토심) 깊이_10  
  	if(selectVal == '90cm이상' || selectVal == '60∼90cm' || selectVal == '30∼60cm' || selectVal == '30cm이하'){
    	$('.soildeptbval').text(selectVal);
     	if(selectVal == '90cm이상'){
        	$('.soildeptbscore').text('5');
			$('input[name=soildeptbscore]').val('5');
     	}else if(selectVal == '60∼90cm'){
	        $('.soildeptbscore').text('4');
			$('input[name=soildeptbscore]').val('4');
		}else if(selectVal == '30∼60cm'){
        	$('.soildeptbscore').text('3');
			$('input[name=soildeptbscore]').val('3');
     	}else if(selectVal == '30cm이하'){
        	$('.soildeptbscore').text('2');
			$('input[name=soildeptbscore]').val('2');
     	}
  	}
    
	//지형구분_11  
  	if(selectVal == '완구릉지' || selectVal == '구릉지' || selectVal == '산악지' || selectVal == 'tpgrphvalEtc'){
 		$('.tpgrphval').text(selectVal);
     	if(selectVal == '완구릉지'){
        	$('.tpgrphscore').text('4');
			$('input[name=tpgrphscore]').val('4');
     	}else if(selectVal == '구릉지'){
        	$('.tpgrphscore').text('3');
			$('input[name=tpgrphscore]').val('3');
     	}else if(selectVal == '산악지'){
        	$('.tpgrphscore').text('2');
			$('input[name=tpgrphscore]').val('2');
     	}else if(selectVal == 'tpgrphvalEtc'){
			$('.tpgrphval').text('');
			$('.tpgrphscore').text('');
			$('input[name=tpgrphscore]').val('0');
	 	}
  	}
    
	//사면경사_14
  	if(selectVal =='20∼30'|| selectVal =='20~30' || selectVal =='10∼20' || selectVal =='10~20' || selectVal =='30이상' || selectVal =='10이하'){
    	$('.slprngval').text(selectVal);
     	if(selectVal == '20∼30' || selectVal =='20~30' ){
        	$('.slprngscore').text('4');
			$('input[name=slprngscore]').val('4');
     	}else if(selectVal == '10∼20' || selectVal =='10~20' ){
        	$('.slprngscore').text('3');
			$('input[name=slprngscore]').val('3');
     	}else if(selectVal == '30이상'){
        	$('.slprngscore').text('2');
			$('input[name=slprngscore]').val('2');
     	}else if(selectVal == '10이하'){
        	$('.slprngscore').text('1');
			$('input[name=slprngscore]').val('1');
     	}
  	}
    
	//지형형태(수평면형)_12  
  	if(plnform == '혼합형' || plnform == '凹형(곡형)' || plnform == '凸형(미근형)' || plnform == '口형(직선형)'){
    	$('.plnformval').text(plnform);
     	if(plnform == '혼합형'){
        	$('.plnformscore').text('4');
			$('input[name=plnformscore]').val('4');
     	}else if(plnform == '凹형(곡형)'){
        	$('.plnformscore').text('3');
			$('input[name=plnformscore]').val('3');
     	}else if(plnform == '凸형(미근형)'){
        	$('.plnformscore').text('2');
			$('input[name=plnformscore]').val('2');
     	}else if(plnform == '口형(직선형)'){
        	$('.plnformscore').text('1');
			$('input[name=plnformscore]').val('1');
     	}
  	}
	
	//지형형태(종단면형)_13
  	if(lngform == '혼합형' || lngform == '凹형(곡형)' || lngform == '凸형(미근형)' || lngform == '口형(직선형)'){
    	$('.lngformval').text(lngform);
     	if(lngform == '혼합형'){
        	$('.lngformscore').text('4');
			$('input[name=lngformscore]').val('4');
     	}else if(lngform == '凹형(곡형)'){
        	$('.lngformscore').text('3');
			$('input[name=lngformscore]').val('3');
     	}else if(lngform == '凸형(미근형)'){
        	$('.lngformscore').text('2');
			$('input[name=lngformscore]').val('2');
     	}else if(lngform == '口형(직선형)') {
        	$('.lngformscore').text('1');
			$('input[name=lngformscore]').val('1');
     	}
  	}

	var sum = 0;
	$("tr[class*=point] > th").each(function(){
	   sum += Number($(this).text()); 
	});
	$("#sum").text(sum+"점");
	$("input[name=lssfrsm]").val(sum);
	if(sum >= 64){
	    $("#grade").text("A");   
		$("input[name=lssfrgrd]").val("A");   
	}else if(sum >= 45){
    	$("#grade").text("B");
		$("input[name=lssfrgrd]").val("B");
	}else{
	    $("#grade").text("C");
		$("input[name=lssfrgrd]").val("C");
	}
	if($("select[name=cnsutgrd]").val() == ""){
		$(".lastgrd span").text($("input[name=lssfrgrd]").val());
		$("input[name=lastgrd]").val($("input[name=lssfrgrd]").val());
	} 
	
	/* 기타 선택시 표출 */
	if(selectVal == "soiltyEtc") {
		$("input[name=soiltyEtc]").attr("disabled",false);
		$("input[name=soiltyEtc]").css({'display':'inline-block'});
    }else if(selectVal == "건조 산림 침식 압쇄토양" || selectVal == "약건성 산림토양" || selectVal == "적윤성 산림토양" || selectVal == "약습성 산림토양" || selectVal == "미숙토양" || selectVal == "사방지 토양"){
		$("input[name=soiltyEtc]").val("");
		$("input[name=soiltyEtc]").attr("disabled",true);
		$("input[name=soiltyEtc]").css({'display':'none'});
    }
	
	if(selectVal == "soilclassbEtc") {
		$("input[name=soilclassbEtc]").attr("disabled",false);
		$("input[name=soilclassbEtc]").css({'display':'inline-block'});
    }else if(selectVal == "점질토(SC SCL CL)" || selectVal == "사질양토(SL SIL)" || selectVal == "사질토(S LS)"){
		$("input[name=soilclassbEtc]").val("");
	$("input[name=soilclassbEtc]").attr("disabled",true);
	$("input[name=soilclassbEtc]").css({'display':'none'});
    }

	if(selectVal == "soilstrctEtc") {
		$("input[name=soilstrctEtc]").attr("disabled",false);
		$("input[name=soilstrctEtc]").css({'display':'inline-block'});
    }else if(selectVal == "무구조(퇴적층)" || selectVal == "괴상(약괴 견과)" || selectVal == "단립(모래)" || selectVal == "벽상" || selectVal == "기타(판상 주상)"){
		$("input[name=soilstrctEtc]").val("");
		$("input[name=soilstrctEtc]").attr("disabled",true);
		$("input[name=soilstrctEtc]").css({'display':'none'});
    }

	if(selectVal == "soilwtrvalEtc") {
		$("input[name=soilwtrvalEtc]").attr("disabled",false);
		$("input[name=soilwtrvalEtc]").css({'display':'inline-block'});
    }else if(selectVal == "습" || selectVal == "약습" || selectVal == "적윤" || selectVal == "약건" || selectVal == "건조"){
		$("input[name=soilwtrvalEtc]").val("");
		$("input[name=soilwtrvalEtc]").attr("disabled",true);
		$("input[name=soilwtrvalEtc]").css({'display':'none'});
    }

	if(selectVal == "tpgrphvalEtc") {
		$("input[name=tpgrphvalEtc]").attr("disabled",false);
		$("input[name=tpgrphvalEtc]").css({'display':'inline-block'});
    }else if(selectVal == "완구릉지" || selectVal == "구릉지" || selectVal == "산악지"){
		$("input[name=tpgrphvalEtc]").val("");
		$("input[name=tpgrphvalEtc]").attr("disabled",true);
		$("input[name=tpgrphvalEtc]").css({'display':'none'});
    }

	if(selectVal == "arealcvalEtc") {
		$("input[name=arealcvalEtc]").attr("disabled",false);
		$("input[name=arealcvalEtc]").css({'display':'inline-block'});
    }else if(selectVal == "산록" || selectVal == "산복" || selectVal == "산정"){
		$("input[name=arealcvalEtc]").val("");
		$("input[name=arealcvalEtc]").attr("disabled",true);
		$("input[name=arealcvalEtc]").css({'display':'none'});
    }

	if(selectVal == "plnformvalEtc") {
		$("input[name=plnformvalEtc]").attr("disabled",false);
		$("input[name=plnformvalEtc]").css({'display':'inline-block'});
    }else if(plnform == "혼합형" || plnform == "口형(직선형)" || plnform == "凸형(미근형)" || plnform == "凹형(곡형)"){
		$("input[name=plnformvalEtc]").val("");
		$("input[name=plnformvalEtc]").attr("disabled",true);
		$("input[name=plnformvalEtc]").css({'display':'none'});
    }
	
	if(selectVal == "lngformvalEtc") {
		$("input[name=lngformvalEtc]").attr("disabled",false);
		$("input[name=lngformvalEtc]").css({'display':'inline-block'});
    }else if(lngform == "혼합형" || lngform == "口형(직선형)" || lngform == "凸형(미근형)" || lngform == "凹형(곡형)"){
		$("input[name=lngformvalEtc]").val("");
		$("input[name=lngformvalEtc]").attr("disabled",true);
		$("input[name=lngformvalEtc]").css({'display':'none'});
    }
		
}




/**
 * 임상 및 주요수종 조회 ajax
 * @returns
 */
function fnGetFrstfrVal(event) {
	
	var frCode = event.target.value;
	var frName = event.target.name.replace("Cd","").concat("val");
	
	$("input[name="+frName+"]").val(frCode);
	
	$.ajax({
		url:"/sys/lss/lcp/sct/selectLcpFrCode.do",
		type:"POST",
		data:{"frCode":frCode},
        dataType:"json",
        success:function(list){
			if(list.result){
        		if(list.result.length > 0){
        			$(".maintreeVal").val(null);
					$("#maintrknd").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#maintrknd").append("<option value=\"".concat(item.codeNm,"\">").concat(item.codeNm,"</option>"));
        			});
					$("#maintrknd").append("<option value>기타</option>");
        		}else{
					$(".maintreeVal").val(null);
					$("#maintrknd").empty().append("<option value>선택하세요</option>");
					$("#maintrknd").append("<option value>기타</option>");
				}		
        	}	
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/**
 * 측정값 선택
 * @returns
 */
function fncGetSelectVal(e) {
	var elmVal = "#"+e.target.name.replaceAll("Cd","");		
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

/**
 * 관할2 조회 ajax
 * @param event
 * @returns
 */
function fncRegionChange(event){
	var code = event.target.value;
	
	if(code == null || code == undefined || code.length == 0){
		$("#svyRegion2View").empty().append("<option value>선택하세요</option>");
		return false;
	}
	
	$.ajax({
		url:"/cmm/selectRegionDetail.do",
		type:"POST",
        data: {codeId:code},
        dataType:"json",
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#svyRegion2View").empty().append("<option value>선택하세요</option>");
        			$.each(list.result,function(idx,item){
        				$("#svyRegion2View").append("<option value=\"".concat(item.code,"\">").concat(item.codeNm,"</option>"));
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

/*
 * 조사완료지 엑셀 다운로드
 */
function fnExcelDown(){
	
	var url = $("#listForm").attr("action");
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	url = url.replace(".do","Excel.do");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
	
}

/**
 * 지도이동
 * @param gid
 * @returns
 */
function fncOpenMap(type, gid){
	var url = "/sys/gis/mainMap.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	form.append($('<input/>', {type: 'hidden', name: "gid", value:gid })).append($('<input/>', {type: 'hidden', name: "type", value:type }));
	form.appendTo("body").submit().remove();
}

/*
 * 조사완료지 현장사진 다운로드
 */
function fnPhotoDown(){
	
	var svyYear = $("input[name=svyYear]").val();
	
	if(svyYear.length == 0){
		alert("조사연도를 반드시 선택해야 엑셀파일을 생성할 수 있습니다.\n목록조회 후 다시 시도해주세요.");
		return;
	}
	
	var url = $("#listForm").attr("action");
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	url = url.replace(".do","SvyId.do");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
	
}

function detailSymp(){
	alert("click");
	
}

/*
 * 조사완료지 땅밀림징후 추가
 */
function fnLcpSttusAdd(type){
	var lcpSttusLen = 1;
	if($("#lcpSttus"+type+" tr").is("[class*=center]")) lcpSttusLen = parseInt($("#lcpSttus"+type+" tr:last()").attr("class").slice(-1))+1;
	if(type == "Crk" || type == "Stp"){
		var contents = "";
		contents+= "<tr class=\"dirsymptm lcpSttus"+type+" center"+lcpSttusLen+"\">";
		contents+= "<th>시점</th>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"bpx1\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpx2\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpx3\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\" style=\"width:120px;\"/>";
		contents+= "</td>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"bpy1\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpy2\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpy3\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\" style=\"width:120px;\"/>";
		contents+= "</td>";
		contents+= "<td><input type=\"text\" name=\"bpz\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/></td>";
		contents+= "<td rowspan=\"2\">";
		contents+= "<input type=\"text\" name=\"h\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "X <input type=\"text\" name=\"l\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "X <input type=\"text\" name=\"d\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "</td>";
		contents+= "</tr>";
		contents+= "<tr class=\"dirsymptm lcpSttus"+type+" center"+lcpSttusLen+"\">";
		contents+= "<th>종점</th>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"epx1\" value=\"\"/>";
		contents+= "<input type=\"text\" name=\"epx2\" value=\"\"/>";
		contents+= "<input type=\"text\" name=\"epx3\" value=\"\" style=\"width:120px;\"/>";
		contents+= "</td>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"epy1\" value=\"\"/>";
		contents+= "<input type=\"text\" name=\"epy2\" value=\"\"/>";
		contents+= "<input type=\"text\" name=\"epy3\" value=\"\" style=\"width:120px;\"/>";
		contents+= "</td>";
		contents+= "<td><input type=\"text\" name=\"epz\" value=\"\"/></td>";
		contents+= "</tr>";
		$("#lcpSttus"+type+" tbody").append(contents);
		
	}else{
		var contents = "";
		contents+= "<tr class=\"indirsymptm lcpSttus"+type+" center"+lcpSttusLen+"\">";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"bpx1\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpx2\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpx3\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\" style=\"width:120px;\"/>";
		contents+= "</td>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"bpy1\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpy2\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "<input type=\"text\" name=\"bpy3\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\" style=\"width:120px;\"/>";
		contents+= "</td>";
		contents+= "<td><input type=\"text\" name=\"bpz\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/></td>";
		contents+= "<td>";
		contents+= "<input type=\"text\" name=\"h\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "X <input type=\"text\" name=\"l\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "X <input type=\"text\" name=\"d\" value=\"\" onchange=\"fnLcpSttusChange('"+type+"')\"/>";
		contents+= "</td>";
		contents+= "</tr>";
		$("#lcpSttus"+type+" tbody").append(contents);
	}
}

/*
 * 조사완료지 땅밀림징후 삭제
 */
function fnLcpSttusDel(type){
	if(type == "Crk" || type == "Stp"){
		if($("#lcpSttus"+type+" tr").is("[class*=center]")){
			var lcpSttusLen = $("#lcpSttus"+type+" tr:last()").attr("class").split(' ').pop();
			$("#lcpSttus"+type+" ."+lcpSttusLen).remove();		
		}
	}else{
		if($("#lcpSttus"+type+" tr").is("[class*=center]")){
			var lcpSttusLen = $("#lcpSttus"+type+" tr:last()").attr("class").split(' ').pop();
			$("#lcpSttus"+type+" ."+lcpSttusLen).remove();		
		}		
	}
	
	fnLcpSttusChange(type);
}

/*
 * 조사완료지 땅밀림징후값 변경 
 */
function fnLcpSttusChange(type){
	var sttusSize = 0;
	if(type == "Crk" || type == "Stp"){
		$(".dirsymptm input").each(function(){
		   sttusSize += $(this).val().length; 
		});		
	}else{
		$(".indirsymptm input").each(function(){
		   sttusSize += $(this).val().length; 
		});				
	}
	if(sttusSize > 0){
		if(type == "Crk" || type == "Stp"){
			$(".dirsymptmval").text("유");
			$(".dirsymptmscore").text("22");
			$("input[name=dirsymptmval]").val("유");
			$("input[name=dirsymptmscore]").val("22");
		}else if(type == "Wdpt" || type == "Strct" || type == "Ugrwtr"){
			$(".indirsymptmval").text("유");
			$(".indirsymptmscore").text("14");
			$("input[name=indirsymptmval]").val("유");
			$("input[name=indirsymptmscore]").val("14");
		}
	}else{
		if(type == "Crk" || type == "Stp"){
			$(".dirsymptmval").text("무");
			$(".dirsymptmscore").text("0");
			$("input[name=dirsymptmval]").val("무");
			$("input[name=dirsymptmscore]").val("0");
		}else if(type == "Wdpt" || type == "Strct" || type == "Ugrwtr"){
			$(".indirsymptmval").text("무");
			$(".indirsymptmscore").text("0");
			$("input[name=indirsymptmval]").val("무");
			$("input[name=indirsymptmscore]").val("0");
		}
	}
	
	var sum = 0;
	$("tr[class*=point] > th").each(function(){
	   sum += Number($(this).text()); 
	});
	$("#sum").text(sum+"점");
	$("input[name=lssfrsm]").val(sum);
	if(sum >= 64){
	    $("#grade").text("A");   
		$("input[name=lssfrgrd]").val("A");   
	}else if(sum >= 45){
    	$("#grade").text("B");
		$("input[name=lssfrgrd]").val("B");
	}else{
	    $("#grade").text("C");
		$("input[name=lssfrgrd]").val("C");
	}
	if($("select[name=cnsutgrd]").val() == ""){
		$(".lastgrd span").text($("input[name=lssfrgrd]").val());
		$("input[name=lastgrd]").val($("input[name=lssfrgrd]").val());
	} 
}

/*
 * 땅밀림실태조사 위치도생성 테스트
 */
 function fnCreatLocationPopupTest(){
	$('<div id="popupDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("creatLocationPopupTest.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 300,
		width: 300,
		title: "위치도생성테스트"
	});
	
}

function fnCreatLocation(){
	///sys/gis/als/creatLocationTest.do
	
//	var allCnt = $("input[name=allCnt]").val();
//	var sec = allCnt * 0.5;
//	var times = convertSsToMm(sec);
	if(confirm("위치도를 생성하시겠습니까?")) {
		$(".loading-img").show();
		$("#insertForm").ajaxForm({
			type: 'POST',
	        url: "/sys/gis/als/creatLocationTest.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == 200) {
	                alert("위치도 생성이 완료되었습니다.");
	                $(".loading-img").hide();
	                $("#popupDiv").dialog("destroy");
	            } else {
	            	alert("위치도 생성을 실패하였습니다.\n실패메세지 : "+data.message);
	            }
	    },
	    error: function(data) {
	    	alert("에러가 발생하였습니다.\n에러메세지 : "+data.message);
	    },
	    complete: function(){
	    	$(".loading-img").hide();
	    }
		}).submit();
	}
}

/*
 * 땅밀림실태조사 조사완료 엑셀등록 팝업
 */
 function fnExcelUploadPopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updateLcpSvyComptPopup.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 320,
		width: 400,
		title: "땅밀림실태조사 완료지 갱신"
	});
	
}
 
/*
 * 땅밀림실태조사 조사완료 엑셀등록
 */
function fnUpdateLcpSvyCompt(){
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
		$(".loading-img").show();
		
		var jqXHR=$.ajax({
			url: $("#insertForm")[0].action,
			type:"POST",
			contentType:false,
	        processData: false,
	        cache: false,
	        data: $app.upload.form,
	        dataType:"json",
	        beforeSend: function(){
	        },
	        success: function(data){
	        	$(".loading-img").hide();
	        	if(data.message){
	        		alert(data.message);
	        	}
	        	if(data.status == "success"){
	        		$app.upload = null;
	        		window.location.reload();
	        	}
	        },
	        error: function(request, status, error){
	        	$(".loading-img").hide();
	        }
		});
	}
}

/**
 * 조사완료 shp파일 업로드
 * @returns
 */
//function fncShpFileUpload(gid){
//	$('<div id="uploadDiv">').dialog({
//		modal:true,
//		open: function(){
//			$(this).load("insertShpFilePopup.do");
//		},
//		close: function(e){
//			$(this).empty();
//			$(this).dialog("destroy");
//		},
//		height: 300,
//		width: 400,
//		title: "땅밀림실태조사 Shp파일 등록"
//	});
//}

/**
 * 위치도 재생성 팝업
 * @returns
 */
function fnUpdateLocReCreatePopup(){
	$('<div id="popupDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updateLocReCreatePopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 370,
		width: 400,
		title: "위치도 재생성 팝업"
	});
}

/**
 * 위치도 재생성 검색 건수
 * @returns
 */
function fnLocReCreateCnt(){
	$(".loading-img").show();
	var sDt = $("#sDate").val();
	var eDt = $("#eDate").val();
	
	$("input[name=startDate]").val(sDt);
	$("input[name=endDate]").val(eDt);
	
	$("#updateLocForm").ajaxForm({
		type: 'POST',
        url: "/sys/lss/lcp/sct/selectLocReCeateCnt.do",
        dataType: "json",
        success: function(data) {
        	if (data.status == 200) {
        		$("input[name=allCnt]").val(data.allCnt);
                $("#allCnt").html(data.allCnt+" 건");
                
            } else {
                alert(data.message);
            }
    },
    error: function(data) {
    	alert(data.message);
    },
    complete: function(){
    	$(".loading-img").hide();
    }
	}).submit();
}

/**
 * 위치도 생성
 * @returns
 */
function fnLocReCreate(){
	var allCnt = $("input[name=allCnt]").val();
	var sec = allCnt * 5;
	var times = convertSsToMm(sec);
	if(confirm(allCnt+" 건의 위치도 생성을 실행하시겠습니까?\n" + "예상시간은 "+times+" 입니다.")) {
		$(".loading-img").show();
		$("#updateLocForm").ajaxForm({
			type: 'POST',
	        url: "/sys/lss/lcp/sct/updateLocReCreate.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == 200) {
	                alert("위치도 생성이 완료되었습니다.");
	                $(".loading-img").hide();
	                $("#popupDiv").dialog("destroy");
	            } else {
	                alert("위치도 생성을 실패하였습니다.\n실패내용 : "+data.message);
	            }
	    },
	    error: function(data) {
	    	alert("에러가 발생하였습니다.\n에러내용 : "+data.message);
	    },
	    complete: function(){
	    	$(".loading-img").hide();
	    }
		}).submit();
	}
}

/**
 * 현장사진 추가 
 * @returns
 */
function fncLssSvyAddPhoto(){
	if($("input[name=photolist]").val() == ''){
		alert("현장사진 정보가 없습니다.");
		return false;
	}
	$('.noPhotoTagInfo').remove();
	var imgCnt = 1;
	if($(".photoBox").length > 0 ) imgCnt = Number($('input[name*=photoTag]:last').attr("name").substr(8)) + 1;
	$('.photoWrap').append(`
		<div class="photoBox" style="width: 50%;">
	    	<div class="photoTag">

		         <input style="width:55% !important; float:left; margin-left:10px;" type="text" name="photoTag`+imgCnt+`"/> 
		         <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncLssSvyDeletePhoto(event); return false;">삭제</button>
	    	</div>
	    	<div class="photoUrl">
				<input type="hidden" name="photoSrc`+imgCnt+`" value=""/>
	            <img src="../../../../images/common/noimage.png" style="width:30%;" class="photoSrc`+imgCnt+`" onerror="this.remove ? this.remove() : this.removeNode();">
		 		<span class="thumb-div noselect">변경</span>
			</div>
	    </div>
	`);
	window.scrollTo(0,document.body.scrollHeight);
}

/**
 * 현장사진 삭제
 * @returns
 */
function fncLssSvyDeletePhoto(e){
	$(e.target).closest('.photoBox').remove();
}

/**
 * 지도이동
 * @param gid
 * @returns
 */
function fncOpenMap(type, gid){
	var url = "/sys/gis/mainMap.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	form.append($('<input/>', {type: 'hidden', name: "gid", value:gid })).append($('<input/>', {type: 'hidden', name: "type", value:type }));
	form.appendTo("body").submit().remove();
}

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/lcp/sct/selectLcpSvyComptList.do").attr("method","post");
	
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
	
	$("#listForm").attr("action","/sys/lss/lcp/sct/selectLcpSvyComptList.do");
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

/**
 * 조사일자 월 선택 초기화
 */
function fncSvyYearChange(){
	$("#svyMonthView").val("").prop("selected","true");
}