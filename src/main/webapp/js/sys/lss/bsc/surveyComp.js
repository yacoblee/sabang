$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
	
	if($("input[name=apiKey]").length > 0){
		var apiKey = $("input[name=apiKey]").val();
		$.getScript("https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId="+apiKey+"&submodules=geocoder",function(){
			var param = JSON.parse($("input[name=mapParam]").val().replaceAll("'","\""));
			var center = param.center.split(",");
			var label = param.label.split(";");
			var marker = param.marker.split(";");
			
			$("input[name=changedZoom]").val($("input[name=orginlZoom]").val());
			naver.maps.onJSContentLoaded = function() {
				var projCenter = naver.maps.TransCoord.fromEPSG3857ToLatLng(new naver.maps.LatLng(center[1],center[0]));
				var projMarker_s = naver.maps.TransCoord.fromEPSG3857ToLatLng(new naver.maps.LatLng(marker[0].split(" ")[1],marker[0].split(" ")[0]));
				var projMarker_e = naver.maps.TransCoord.fromEPSG3857ToLatLng(new naver.maps.LatLng(marker[1].split(" ")[1],marker[1].split(" ")[0]));
				var mapOptions = {
					center: new naver.maps.Point(projCenter),
					zoom: param.zoom+1,
					mapTypeId : naver.maps.MapTypeId.SATELLITE,
					zoomControl:true,
					zoomControlOptions: {
			    		position: naver.maps.Position.RIGHT_TOP,
	        	        style: naver.maps.ZoomControlStyle.SMALL
	        	    },
	        	    draggable: false,
	        	    scrollWheel: false,
	        	    clickable: false
				};
				$app.map = new naver.maps.Map('map', mapOptions);
				
				var markerOptions_s = {
	        		position: new naver.maps.Point(projMarker_s),
	                map:$app.map,
	                icon:{
	                   path: naver.maps.SymbolPath.CIRCLE, 
	                   radius:7,
	                   fillColor: '#029DFF',
	                   fillOpacity: 1,
	                   strokeOpacity: 0
	                }
	        	};
		        var marker_s = new naver.maps.Marker(markerOptions_s);
		        
		        var markerOptions_e = {
	        		position: new naver.maps.Point(projMarker_e),
	                map:$app.map,
	                icon:{
	                   path: naver.maps.SymbolPath.CIRCLE, 
	                   radius:7,
	                   fillColor: '#FE0000',
	                   fillOpacity: 1,
	                   strokeOpacity: 0
	                }
	        	};
			    var marker_e = new naver.maps.Marker(markerOptions_e);
//			    var infoWindowOptions_s = {
//					position: new naver.maps.Point(projMarker_s),
//					content: '<div>' + label[0]+'</div>'
//				};
//				
//				var infoWindowOptions_e = {
//					position: new naver.maps.Point(projMarker_e),
//					content: '<div>' + label[1]+'</div>'
//				};
//				
//			    var infoWindow_s = new naver.maps.InfoWindow(infoWindowOptions_s);
//			    infoWindow_s.open($app.map);
//			    var infoWindow_e = new naver.maps.InfoWindow(infoWindowOptions_e);
//			    infoWindow_e.open($app.map);
			    
			    var CustomOverlay_s = function(options) {
			    	this._imgSize = new naver.maps.Size(10,10);
			    	this._element = $('<div style="position:absolute;width:190px;background-color:#F2F0EA;text-align:center;">'+ label[0] + '</div>') 
			    	this.setPosition(options.position);
			    	this.setMap(options.map || null);
			    };

			    CustomOverlay_s.prototype = new naver.maps.OverlayView();
			    CustomOverlay_s.prototype.constructor = CustomOverlay_s;

			    CustomOverlay_s.prototype.setPosition = function(position) {
			    	this._position = position;
			    	this.draw();
			    };

			    CustomOverlay_s.prototype.getPosition = function() {
			    	return this._position;
			    };

			    CustomOverlay_s.prototype.onAdd = function() {
			    	var overlayLayer = this.getPanes().overlayLayer;

			    	this._element.appendTo(overlayLayer);
			    };

			    CustomOverlay_s.prototype.draw = function() {
			    	if (!this.getMap()) {
			    		return;
			    	}

			    	var projection = this.getProjection(),
			    		position = this.getPosition(),
			    		pixelPosition = projection.fromCoordToOffset(position);
			    	
			    	this._element.css('left', pixelPosition.x-(pixelPosition.x/6)-16);
			    	this._element.css('top', pixelPosition.y-38);
			    };

			    CustomOverlay_s.prototype.onRemove = function() {
			    	var overlayLayer = this.getPanes().overlayLayer;

			    	this._element.remove();
			    	this._element.off();
			    };

			    var overlay_s = new CustomOverlay_s({
			    	map: $app.map,
			    	position: new naver.maps.Point(projMarker_s)
			    });
			    

			    var CustomOverlay_e = function(options) {
			    	this._imgSize = new naver.maps.Size(10,10);
			    	this._element = $('<div style="position:absolute;width:190px;background-color:#F2F0EA;text-align:center;">'+ label[1] + '</div>') 
			    	this.setPosition(options.position);
			    	this.setMap(options.map || null);
			    };

			    CustomOverlay_e.prototype = new naver.maps.OverlayView();
			    CustomOverlay_e.prototype.constructor = CustomOverlay_e;

			    CustomOverlay_e.prototype.setPosition = function(position) {
			    	this._position = position;
			    	this.draw();
			    };

			    CustomOverlay_e.prototype.getPosition = function() {
			    	return this._position;
			    };

			    CustomOverlay_e.prototype.onAdd = function() {
			    	var overlayLayer = this.getPanes().overlayLayer;

			    	this._element.appendTo(overlayLayer);
			    };

			    CustomOverlay_e.prototype.draw = function() {
			    	if (!this.getMap()) {
			    		return;
			    	}

			    	var projection = this.getProjection(),
			    		position = this.getPosition(),
			    		pixelPosition = projection.fromCoordToOffset(position);
			    	
			    	this._element.css('left', pixelPosition.x-(pixelPosition.x/6)-20);			    	
			    	this._element.css('top', pixelPosition.y-38);
			    };

			    CustomOverlay_e.prototype.onRemove = function() {
			    	var overlayLayer = this.getPanes().overlayLayer;

			    	this._element.remove();
			    	this._element.off();
			    };

			    var overlay_e = new CustomOverlay_e({
			    	map: $app.map,
			    	position: new naver.maps.Point(projMarker_e)
			    });
				
				naver.maps.Event.addListener($app.map, 'zoom_changed', function(zoom) {
					$("input[name=changedZoom]").val(zoom-1);
				});
				// 루프 돌리게 되면 해당 인덱스값 세팅
				// 현재는 테스트 중이므로 1로 고정.
				$("input[name=locImgIdx]").val(1);
			};
		});
	}
	
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
		
		if(sum >= 60){
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
				//$(this).load("/sys/lss/bsc/sld/stripLandExcelPopup.do");
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
	$("#listForm").attr("action","/sys/lss/bsc/sct/selectBscSvyComptDetail.do");
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
	        url: "/sys/lss/bsc/sct/deleteBscSvyCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/lss/bsc/sct/selectBscSvyComptList.do";
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
//	$("#listForm").attr("action","/sys/lss/bsc/sct/updateBscSvyComptView.do");
//	$("#listForm").submit();
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/bsc/sct/updateBscSvyComptView.do").attr("method","post");

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
//        if(!validateLssBscStripLand(form)){
//        	return false;
//        }else{
//        	form.submit();
//        }
	if(confirm("수정하시겠습니까?")) {
		if($("input[name=photolist]").val() != null && $("input[name=photolist]").val() != ""){
			var returnChk = false;
			var photoNumArr = new Array();
			var photoTagArr = '[';
		 	$("select[name=photoNum]").each(function(i, item){
				var photoTag = $(item).next('input[name*=photoTag]').val();
				var photoSrc = $('input[name*=photoSrc]:eq('+i+')').val();
				if($(item).next('input[name*=photoTag]').val().length == 0){
					returnChk = true;
					alert('현장사진 태그명이 입력되지 않았습니다.');
					return false;
				}
				if($(item).val().length > 0){
					photoTag = $(item).val().concat('.'+photoTag);
					photoNumArr.push($(item).val()); 
				} 
				photoTagArr = photoTagArr.concat('{"TAG":"'+photoTag+'","FILENAME":"'+photoSrc+'"},');
	   		});
			if(returnChk) return false;
			photoTagArr = photoTagArr.slice(0,-1).concat(']');
			$('input[name=photoTagList]').val(photoTagArr);
				
			// 현장사진 넘버링 1~6 체크
			var isphotoNumChk = 0;		
			// 현장사진 넘버링 중복체크 	
			var isphotoNumDup = 0;
			
			for(var i= 0;i<4;i++){
				var j = String(i+1);
				if(photoNumArr.indexOf(j) == -1 && isphotoNumChk == 0) isphotoNumChk = j;
				if(photoNumArr.indexOf(j) != photoNumArr.lastIndexOf(j) && isphotoNumDup == 0) isphotoNumDup = j;
			}
			
			if(isphotoNumChk != 0){
				alert(isphotoNumChk+'번 현장사진 순번이 지정되지 않았습니다.');
				return;
			}
			if(isphotoNumDup != 0){
				alert(isphotoNumDup+'번 현장사진 순번이 중복 되었습니다.');
				return;
			}
		}
		
		if($("#svyRegion1View").val() != "") $("input[name=svyRegion1]").val($("#svyRegion1View option:checked").text());
		if($("#svyRegion2View").val() != "") $("#svyRegion2View option:checked").val($("#svyRegion2View option:checked").text());
		
		
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: $("#listForm")[0].action,
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/lss/bsc/sct/selectBscSvyComptList.do";
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

function fncOpenClipReport(gId,svyType,svyYear){
	var url = "/cmm/openClipReport.do";
	var fileName = "";
	var form = null;
	
	if(svyType == "산사태"){
		if(svyYear == '2021'){
			fileName = "REPORT_SYS_LSS_BSC_01";
		}else{
			fileName = "REPORT_SYS_LSS_BSC_01_01";
		}
		
	}else{
		if(svyYear == '2021'){
			fileName = "REPORT_SYS_LSS_BSC_02";
		}else{
			fileName = "REPORT_SYS_LSS_BSC_02_01";
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
        				$("#svyRegion2View").append("<option value=\"".concat(item.code,"\">").concat(item.codeDc,"</option>"));
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

/**
 * 행정구역 시군구 조회 ajax
 * @returns
 */
function fncAdministCtprvnNmChange(){
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
	
	var svyYear = $("input[name=svyYear]").val();
	
//	if(svyYear.length == 0){
//		alert("조사연도를 반드시 선택해야 엑셀파일을 생성할 수 있습니다.\n목록조회 후 다시 시도해주세요.");
//		return;
//	}
	
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
 * 기초조사 완료지 갱신
 */
 function fnExcelUpload(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updateBscSvyComptPopup.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 310,
		width: 400,
		title: "기초조사 완료지 갱신"
	});
	
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
		height: 310,
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
        url: "/sys/lss/bsc/sct/selectLocReCeateCnt.do",
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
 * 위치도 재생성
 * @returns
 */
function fnLocReCreate(){
	var allCnt = $("input[name=allCnt]").val();
	var sec = allCnt * 0.5;
	var times = convertSsToMm(sec);
	if(confirm(allCnt+" 건의 위치도 재생성을 실행하시겠습니까?\n" + "예상시간은 "+times+" 입니다.")) {
		$(".loading-img").show();
		$("#updateLocForm").ajaxForm({
			type: 'POST',
	        url: "/sys/lss/bsc/sct/updateLocReCreate.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == 200) {
	                alert("위치도 재생성이 완료되었습니다.");
	                $(".loading-img").hide();
	                $("#popupDiv").dialog("destroy");
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
}

//사진생성 샘플
/*
function fnCreatePhoto(){
	$.ajax({
		url:"/ext/createPhoto.do",
		type:"POST",
        dataType:"json",
        success:function(data){
        	if(data.status == "200"){
        		alert("위치도 생성이 완료되었습니다.");
        	}else{
        		alert("위치도 생성을 실패하였습니다.");
        	}
        },
        error: function(request, status, error){
        	alert("위치도 생성 중 오류가 발생하였습니다.");
        }
	});
}
*/
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
		        <select name="photoNum" class="photoNum" style="width:5%; height:35px; float:left;">
		            <option value="" >선택</option>
		        </select> 
		         <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag`+imgCnt+`"/> 
		         <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncLssSvyDeletePhoto(event); return false;">삭제</button>
	    	</div>
	    	<div class="photoUrl">
				<input type="hidden" name="photoSrc`+imgCnt+`" value=""/>
	            <img src="../../../../images/common/noimage.png" style="width:30%;" class="photoSrc`+imgCnt+`" onerror="this.remove ? this.remove() : this.removeNode();">
		 		<span class="thumb-div noselect">변경</span>
			</div>
	    </div>
	`);
	var numCnt = 4;
	for(var i = 0; i<numCnt; i++){
		$('.photoNum:last').append('<option value="'+(i+1)+'">'+(i+1)+'</option>')
	}
	$('.photoNum:last option:eq(0)').prop('selected',true);
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
 * 현장사진 동기화 팝업
 * @returns
 */
function fnUpdatePhotoPopup(){
	$('<div id="popupDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updatePhotoPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 340,
		width: 400,
		title: "현장사진 동기화 팝업"
	});
}

/**
 * 현장사진 널값 검색 건수
 * @returns
 */
function fnPhotoNullCnt(){
	$(".loading-img").show();
	var sDt = $("#sDate").val();
	var eDt = $("#eDate").val();
	
	$("input[name=startDate]").val(sDt);
	$("input[name=endDate]").val(eDt);
	
	$("#updatePhotoForm").ajaxForm({
		type: 'POST',
        url: "/sys/lss/bsc/sct/selectPhotoNullCnt.do",
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
 * 현장사진 동기화
 * @returns
 */
function fnPhotoUpdate(){
	var allCnt = $("input[name=allCnt]").val();
	var sec = allCnt * 0.5;
	var times = convertSsToMm(sec);
	if(confirm(allCnt+" 건의 현장사진 동기화를 실행하시겠습니까?\n" + "예상시간은 "+times+" 입니다.")) {
		$(".loading-img").show();
		$("#updatePhotoForm").ajaxForm({
			type: 'POST',
	        url: "/sys/lss/bsc/sct/updatePhotoList.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == 200) {
	                alert("현장사진 동기화가 완료되었습니다.");
	                $(".loading-img").hide();
	                $("#popupDiv").dialog("destroy");
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
/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/bsc/sct/selectBscSvyComptList.do").attr("method","post");
	
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
	
	$("#listForm").attr("action","/sys/lss/bsc/sct/selectBscSvyComptList.do");
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