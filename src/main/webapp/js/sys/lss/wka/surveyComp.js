$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
//	
	if($("input[name=svyType]").val() == "취약지역 실태조사(산사태)"){
		select();
	}
	
	if($('input[name=jdgmntgrad]').val() == null || $('input[name=jdgmntgrad]').val() == ""){
		var checkboxes = document.querySelectorAll('input[name="jdgmntgradChkBox"]');
		for (var i = 0; i < checkboxes.length; i++) {
			checkboxes[i].checked = true;
		}
	}
		
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
		
		if(sum > 60){
			$("#ncssty").val("필요");
		}else{
			$("#ncssty").val("불필요");
		}
	});
	
//	$(document).on("click",".photoUrl > span.thumb-div",function(){
//		var photoClass = $(this).prevAll("input").attr("name");
//		$('<div id="photoDiv">').dialog({
//			modal:true,
//			open: function(){
//				// 모달 오버레이 설정
//				$(".ui-widget-overlay").css({
//					opacity: 0.5,
//					filter: "Alpha(Opacity=50)",
//					backgroundColor: "black"
//				});
//				var $photo = JSON.parse($("input[name=photolist]").val());
//				$.each($photo,function(idx,elm){
//					var cnt= idx+1;
//					$("#photoDiv").append("<img id=photoSrc"+cnt+" src=\"/storage/fieldBook".concat(elm,"\">"));
//				});
//				//$(this).load("/sys/lss/wka/sld/stripLandExcelPopup.do");
//			},
//			close: function(e){
//				$(this).empty();
//				$(this).dialog("destroy");
//			},
//			height: 550,
//			width: 600,
//			buttons:{
//				"변경" : function(){
//					var src = $(".selected").attr("src");
//					$('.'+photoClass).attr("src",src);
//					$('.'+photoClass).css('width','');
//					$('input[name='+photoClass+']').val(src.substr(18));
//					$(this).dialog("close");
//				},
//				"취소" : function(){
//					$(this).dialog("close");
//				}
//				
//			},
//			title: "현장사진목록"
//		});
//	});
	
//	
//	$(document).on("click","#photoDiv img",function(){
//		var selected = $(this).hasClass("selected");
//		if(selected){
//			$(this).removeClass("selected");
//		}else{
//			$("#photoDiv img").removeClass("selected");
//			$(this).addClass("selected");
//		}
//	});
	
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
function fncSvyComptDetail(gid) {
	$("input[name=gid]").val(gid);
	$("#listForm").attr("action","/sys/lss/wka/sct/selectWkaSvyComptDetail.do");
	$("#listForm").submit();
}

/**
 * 조사완료지 공간정보 수정화면
 * @param gId
 * @returns
 */
function fncSvyComptGeomView(gId) {
	$("input[name=gid]").val(gId);
	$("#listForm").attr("action","/sys/lss/wka/sct/updateWkaSvyComptUpdtView.do");
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
	        url: "/sys/lss/wka/sct/deleteWkaSvyCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/lss/wka/sct/selectWkaSvyComptList.do";
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
//	$("#listForm").attr("action","/sys/lss/wka/sct/updateWkaSvyComptView.do");
//	$("#listForm").submit();
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/wka/sct/updateWkaSvyComptView.do").attr("method","post");

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
			if(photoTagArr.length > 2){
				photoTagArr = photoTagArr.slice(0,-1).concat(']');
			}else{
				photoTagArr = null;
			}
			
			
			$('input[name=photoTagList]').val(photoTagArr);
			
			// 현장사진 넘버링 1~6 체크
			var isphotoNumChk = 0;		
			// 현장사진 넘버링 중복체크 	
			var isphotoNumDup = 0;
			// 현장사진 넘버링 최대값 설정
			var photoNumMax = 0;
			var grade = $("select[name=jdgmntgrad]").val();
			
			if(grade.indexOf("C") == 0){
				photoNumMax = 6;
			}else{
				photoNumMax = 14;
			}
			
			for(var i= 0;i<photoNumMax;i++){
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
		
		if($("input[name=svyType]").val() == "취약지역 실태조사(토석류)"){
			var shrtSum = 0;
			var mdlSum = 0;
			var upSum = 0;
			
			// 최단부 분포비율
			$("input[class=shrtper]").each(function(){
				shrtSum += Number($(this).val());
			});
			
			// 중간부 분포비율
			$("input[class=mdlper]").each(function(){
				mdlSum += Number($(this).val());
			});
			
			// 최상부 분포비율
			$("input[class=upper]").each(function(){
				upSum += Number($(this).val());
			});
			
			if(shrtSum != 100){
				alert("최단부 암반, 전석, 자갈, 모래, 기타 비율의 합이 100%가 아닙니다.");
				return false;
			}
			
			if(mdlSum != 100){
				alert("중간부 암반, 전석, 자갈, 모래, 기타 비율의 합이 100%가 아닙니다.");
				return false;
			}
			
			if(upSum != 100){
				alert("최상부 암반, 전석, 자갈, 모래, 기타 비율의 합이 100%가 아닙니다.");
				return false;
			}
		}
		
		$("select[name*=Val]").each(function(){
			var dirsttus = $("select[name=dirsttus]").val();
			var objNm = $(this).attr("name").replace("Val","");
			var objScoreNm = $(this).attr("name").replace("Val","score");
			var objVal = $(this).find(":selected").text();
			var objScore = $(this).val();
			if(dirsttus == "토사"){
				if(!$(this).hasClass("dirBr")){	 
					if(objScore != ""){
						if(objNm != "slope" && objNm != "soildep"){
							$("input[name="+objNm+"]").val(objVal);
						};
						$("input[name="+objScoreNm+"]").val(objScore);				
					}
				};	
			}else if(dirsttus == "암반"){
				if(!$(this).hasClass("dirEs")){
					if(objScore != ""){
						if(objNm != "slope"){
							$("input[name="+objNm+"]").val(objVal);
						};
						$("input[name="+objScoreNm+"]").val(objScore);				
					}
				};	
			}else{
				if(objScore != ""){
					$("input[name="+objNm+"]").val(objVal);
					$("input[name="+objScoreNm+"]").val(objScore);				
				}
			}
		});
		
		if($("input[name=svyType]").val() == "취약지역 실태조사(토석류)"){
			// 황폐발생원
			var scodsltn = $("select[name=scodsltn]").val();
						
			if(scodsltn == "0"){
				$("input[name=lndslddgsttus]").val("3등급 이하");
			}else if(scodsltn == "1"){
				$("input[name=lndslddgsttus]").val("2등급 50% 미만");
			}else if(scodsltn == "2"){
				$("input[name=lndslddgsttus]").val("2등급 50% 이상");
			}else if(scodsltn == "3"){
				$("input[name=lndslddgsttus]").val("1등급");
			}			
		}	
		
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: $("#listForm")[0].action,
	        dataType: "json",
	        processData: false,
	        contentType: false,
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/lss/wka/sct/selectWkaSvyComptList.do";
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

function fncOpenClipReport(gid,svyType){
	var url = "/cmm/openClipReport.do";
	var fileName = "";
	var form = null;
	
	if(svyType == "취약지역 실태조사(산사태)"){
		fileName = "REPORT_SYS_LSS_WKA_01";
	}else{
		fileName = "REPORT_SYS_LSS_WKA_02";
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
	
	form.find("input[name=gid]").val(gid);
	
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

	var svyType = $("input[name=svyType]").val();
	
	if(svyType.length == 0){
		alert("조사유형을 반드시 선택해야 엑셀파일을 생성할 수 있습니다.\n목록조회 후 다시 시도해주세요.");
		return;
	}
	
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
			$(this).load("updateWkaSvyComptPopup.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 310,
		width: 400,
		title: "취약지역 실태조사 완료지 갱신"
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
        url: "/sys/lss/wka/sct/selectLocReCeateCnt.do",
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
	        url: "/sys/lss/wka/sct/updateLocReCreate.do",
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
        url: "/sys/lss/wka/sct/selectPhotoNullCnt.do",
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
	        url: "/sys/lss/wka/sct/updatePhotoList.do",
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
 * 조사완료 및 수정페이지 판정표 점수(select)
 * @returns
 */
function select(obj){
	if(obj != null){
		var val = obj.value;
		var objNm = obj.name;
	}
	var dirSttus = $("select[name=dirsttus]").val();
	
	// 취약지역 실태조사(산사태)
	if(dirSttus == "토사"){
		$("th.dirBr[class*=score]").each(function(){
			$(this).text("0");
		});
		$("select.dirEs").not(".disabled").attr("disabled",false);	
		$("select.dirEs").each(function(){
			var scoreNm = "." + $(this).attr("name").replace("Val","score");
			$(".dirEs"+scoreNm).text($(this).val());
		});	
		
		// 사면높이
		var dirgh = Number($("input[name=pntmax]").val()) - Number($("input[name=pntmin]").val());
		if(dirgh < 5){
			$(".dirEs select[name=dirhgVal]").val("1").prop("selected", true);
			$(".dirEs.dirhgscore").text("1");
		}else if(dirgh < 20){
			$(".dirEs select[name=dirhgVal]").val("2").prop("selected", true);
			$(".dirEs.dirhgscore").text("2");
		}else if(dirgh < 30){
			$(".dirEs select[name=dirhgVal]").val("3").prop("selected", true);
			$(".dirEs.dirhgscore").text("3");
		}else if(dirgh < 40){
			$(".dirEs select[name=dirhgVal]").val("5").prop("selected", true);
			$(".dirEs.dirhgscore").text("5");
		}else{
			$(".dirEs select[name=dirhgVal]").val("7").prop("selected", true);
			$(".dirEs.dirhgscore").text("7");
		}
		
		// 경사도
		var slopeAvg = (Number($("input[name=slopemin]").val()) + Number($("input[name=slopemax]").val()))/2;
		if(slopeAvg < 25){
			$(".dirEs select[name=slopeVal]").val("1").prop("selected", true);
			$(".dirEs.slopescore").text("1");
		}else if(slopeAvg < 35){
			$(".dirEs select[name=slopeVal]").val("3").prop("selected", true);
			$(".dirEs.slopescore").text("3");
		}else if(slopeAvg < 41){
			$(".dirEs select[name=slopeVal]").val("5").prop("selected", true);
			$(".dirEs.slopescore").text("5");
		}else {
			$(".dirEs select[name=slopeVal]").val("7").prop("selected", true);
			$(".dirEs.slopescore").text("7");
		}	
		
	}else if(dirSttus == "암반"){
		$("th.dirEs[class*=score]").each(function(){
			$(this).text("0");
		});
		$("select.dirBr").not(".disabled").attr("disabled",false);
		$("select.dirBr").each(function(){
			var scoreNm = "." + $(this).attr("name").replace("Val","score");
			$(".dirBr"+scoreNm).text($(this).val());
		});
		
		// 사면높이
		var dirgh = Number($("input[name=pntmax]").val()) - Number($("input[name=pntmin]").val());
		if(dirgh < 10){
			$(".dirBr select[name=dirhgVal]").val("1").prop("selected", true);
			$(".dirBr.dirhgscore").text("1");
		}else if(dirgh < 20){
			$(".dirBr select[name=dirhgVal]").val("3").prop("selected", true);
			$(".dirBr.dirhgscore").text("3");
		}else if(dirgh < 30){
			$(".dirBr select[name=dirhgVal]").val("5").prop("selected", true);
			$(".dirBr.dirhgscore").text("5");
		}else{
			$(".dirBr select[name=dirhgVal]").val("7").prop("selected", true);
			$(".dirBr.dirhgscore").text("7");
		}
		
		// 경사도
		var slopeAvg = (Number($("input[name=slopemin]").val()) + Number($("input[name=slopemax]").val()))/2;
		if(slopeAvg < 30){
			$(".dirBr select[name=slopeVal]").val("1").prop("selected", true);
			;$(".dirBr.slopescore").text("1");
		}else if(slopeAvg < 40){
			$(".dirBr select[name=slopeVal]").val("3").prop("selected", true);
			$(".dirBr.slopescore").text("3");				
		}else if(slopeAvg < 50){
			$(".dirBr select[name=slopeVal]").val("5").prop("selected", true);
			$(".dirBr.lopescore").text("5");
		}else {
			$(".dirBr select[name=slopeVal]").val("7").prop("selected", true);
			$(".dirBr.slopescore").text("7");
		}	
	}
	
	// 종단형상
	if(objNm == "dirfrm" && dirSttus == "토사"){
		if(val =="상승"){
			$("select[name=dirfrmVal]").val("1").prop("selected", true);
			$("input[name=crossfrmscore]").val("1");
			$(".crossfrmscore").text("1");
		}else if(val == "평형"){
			$("select[name=dirfrmVal]").val("2").prop("selected", true);
			$("input[name=crossfrmscore]").val("2");
			$(".crossfrmscore").text("2");
		}else if(val == "하강"){
			$("select[name=dirfrmVal]").val("3").prop("selected", true);
			$("input[name=crossfrmscore]").val("3");
			$(".crossfrmscore").text("3");
		}else if(val == "복합"){
			$("select[name=dirfrmVal]").val("4").prop("selected", true);
			$("input[name=crossfrmscore]").val("4");
			$(".crossfrmscore").text("4");
		}
	}
	
	//모암
	if(objNm == "prntrock" && dirSttus == "암반"){
		if(val == "퇴적암" || val == "석회암"){
			$("select[name=prntrockVal]").val("1").prop("selected", true);
			$("input[name=rockkndscore]").val("1");
			$(".rockkndscore").text("1");
		}else if(val == "화성암" || val == "화강암"){
			$("select[name=prntrockVal]").val("2").prop("selected", true);
			$("input[name=rockkndscore]").val("2");
			$(".rockkndscore").text("2");
		}else if(val == "천매암"){
			$("select[name=prntrockVal]").val("3").prop("selected", true);
			$("input[name=rockkndscore]").val("3");
			$(".rockkndscore").text("3");
		}else if(val == "편암" || val == "편마암"){
			$("select[name=prntrockVal]").val("5").prop("selected", true);
			$("input[name=rockkndscore]").val("5");
			$(".rockkndscore").text("5");
		}else if(val == "안산암"){
			$("select[name=prntrockVal]").val("7").prop("selected", true);
			$("input[name=rockkndscore]").val("7");
			$(".rockkndscore").text("7");
		}
	}
	
	if(objNm != null && objNm.includes("Val")){
		var scoreNm = "."+objNm.replace("Val","score");
		$(scoreNm).text(val);
	}
	
	//취약지역 실태조사(토석류)
	var svyType = $("input[name=svyType]").val();
	if(svyType == "취약지역 실태조사(토석류)"){
		var objClassNm = obj.className;
		
		// 주 위험요소
		if(objClassNm == "mainrisk"){
			var mainriskArr = [0];
			$("select[class=mainrisk]").each(function(){
				var objNm = $(this).attr("name");
				var objVal = $(this).find(":selected").text();
				var objScore = $(this).val();
				
				if(objScore == "") objVal = "";
				$("input[name="+objNm+"]").val(objVal);
				$("input[name="+objNm+"score]").val(objScore);
				mainriskArr.push(Number(objScore));
			});
			var mainriskMax = Math.max.apply(null, mainriskArr);
			$(".mainriskscore").text(mainriskMax);
		}
	}
	
	// 산사태위험등급현황
	if(objNm == "scodsltn"){
		$("select[name=lndslddgsttusVal]").val(val).prop("selected",true);
		$(".lndslddgsttusscore").text(val);
	}
	
	// 토석류 확산 영향범위
	if(objNm =="stabanal"){
		objNm = objNm + "score";
		$("input[name="+objNm+"]").val(val);
		$("."+objNm).text(val);
	}
	
	changeTotScore();
	
}

/**
 * 조사완료 및 수정페이지 판정표 점수(input)
 * @returns
 */
function inputText(obj){
	var val = obj.value;
	var objNm = obj.name;
	var dirSttus = $("select[name=dirsttus]").val();
	
	// 취약지역 실태조사 (산사태)
	// 경사도
	if(objNm.includes("slope")){
		var slopeAvg = 0;
		slopeAvg = (Number($("input[name=slopemin]").val()) + Number($("input[name=slopemax]").val()))/2
		$("input[name=slope]").val(slopeAvg);
		if(dirSttus == "토사"){
			if(slopeAvg < 25){
				$(".dirEs select[name=slopeVal]").val("1").prop("selected", true);
				$(".dirEs.slopescore").text("1");
			}else if(slopeAvg < 35){
				$(".dirEs select[name=slopeVal]").val("3").prop("selected", true);
				$(".dirEs.slopescore").text("3");
			}else if(slopeAvg < 41){
				$(".dirEs select[name=slopeVal]").val("5").prop("selected", true);
				$(".dirEs.slopescore").text("5");
			}else {
				$(".dirEs select[name=slopeVal]").val("7").prop("selected", true);
				$(".dirEs.slopescore").text("7");
			}			
		}else if(dirSttus == "암반"){
			if(slopeAvg < 30){
				$(".dirBr select[name=slopeVal]").val("1").prop("selected", true);
				;$(".dirBr.slopescore").text("1");
			}else if(slopeAvg < 40){
				$(".dirBr select[name=slopeVal]").val("3").prop("selected", true);
				$(".dirBr.slopescore").text("3");				
			}else if(slopeAvg < 50){
				$(".dirBr select[name=slopeVal]").val("5").prop("selected", true);
				$(".dirBr.lopescore").text("5");
			}else {
				$(".dirBr select[name=slopeVal]").val("7").prop("selected", true);
				$(".dirBr.slopescore").text("7");
			}
		}
	}
	
	// 사면높이
	if(objNm.includes("pnt")){
		var dirgh = 0;
		dirgh = Number($("input[name=pntmax]").val()) - Number($("input[name=pntmin]").val());
		if(dirSttus == "토사"){
			if(dirgh < 5){
				$(".dirEs select[name=dirhgVal]").val("1").prop("selected", true);
				$(".dirEs.dirhgscore").text("1");
			}else if(dirgh < 20){
				$(".dirEs select[name=dirhgVal]").val("2").prop("selected", true);
				$(".dirEs.dirhgscore").text("2");
			}else if(dirgh < 30){
				$(".dirEs select[name=dirhgVal]").val("3").prop("selected", true);
				$(".dirEs.dirhgscore").text("3");
			}else if(dirgh < 40){
				$(".dirEs select[name=dirhgVal]").val("5").prop("selected", true);
				$(".dirEs.dirhgscore").text("5");
			}else{
				$(".dirEs select[name=dirhgVal]").val("7").prop("selected", true);
				$(".dirEs.dirhgscore").text("7");
			}
		}else if(dirSttus == "암반"){
			if(dirgh < 10){
				$(".dirBr select[name=dirhgVal]").val("1").prop("selected", true);
				$(".dirBr.dirhgscore").text("1");
			}else if(dirgh < 20){
				$(".dirBr select[name=dirhgVal]").val("3").prop("selected", true);
				$(".dirBr.dirhgscore").text("3");
			}else if(dirgh < 30){
				$(".dirBr select[name=dirhgVal]").val("5").prop("selected", true);
				$(".dirBr.dirhgscore").text("5");
			}else{
				$(".dirBr select[name=dirhgVal]").val("7").prop("selected", true);
				$(".dirBr.dirhgscore").text("7");
			}
		}
	}
	
	// 토심
	if(objNm == "soildep" && dirSttus == "토사"){
		if(Number(val) < 30){
			$("select[name=soildepVal]").val("1").prop("selected", true);
			$("input[name=soildepscore]").val("1");
			$(".soildepscore").text("1");	
		}else if(Number(val) < 50){
			$("select[name=soildepVal]").val("2").prop("selected", true);
			$("input[name=soildepscore]").val("2");
			$(".soildepscore").text("2");	
		}else if(Number(val) < 80){
			$("select[name=soildepVal]").val("3").prop("selected", true);
			$("input[name=soildepscore]").val("3");
			$(".soildepscore").text("3");	
		}else if(Number(val) < 100){
			$("select[name=soildepVal]").val("5").prop("selected", true);
			$("input[name=soildepscore]").val("5");
			$(".soildepscore").text("5");	
		}else{
			$("select[name=soildepVal]").val("7").prop("selected", true);
			$("input[name=soildepscore]").val("7");
			$(".soildepscore").text("7");	
		}
	}
	
	// 건기,우기 (안정해석 점수)
	if(objNm.includes("season")){
		var drySn = parseFloat($("input[name=dryseason]").val());
		var rainSn = parseFloat($("input[name=rainseason]").val());
		
		if(isNaN(drySn) == false && isNaN(rainSn) == false){
			if(drySn < 1.5 || rainSn < 1.2){
				$(".stabanalscore").text("30");
				$("input[name=stabanalscore]").val("30");
			}else if(drySn < 1.6 || rainSn < 1.3){
				$(".stabanalscore").text("15");
				$("input[name=stabanalscore]").val("15");
			}else{
				$(".stabanalscore").text("0");
				$("input[name=stabanalscore]").val("0");
			}			
		}	
	}
	//취약지역 실태조사(토석류)
	var objClassNm = obj.className;
	
	//토심
	if(objClassNm == "soildep"){
		var sum = 0;
		$("input[class=soildep]").each(function(){
			sum += Number($(this).val());
		});
		sum = parseInt(sum/3);
		if(sum < 30){
			$("select[name=soildepVal]").val("1").prop("selected", true);
			$("input[name=soildepscore]").val("1");
			$(".soildepscore").text("1");	
		}else if(sum < 50){
			$("select[name=soildepVal]").val("3").prop("selected", true);
			$("input[name=soildepscore]").val("3");
			$(".soildepscore").text("3");	
		}else if(sum < 80){
			$("select[name=soildepVal]").val("5").prop("selected", true);
			$("input[name=soildepscore]").val("5");
			$(".soildepscore").text("5");	
		}else if(sum < 100){
			$("select[name=soildepVal]").val("7").prop("selected", true);
			$("input[name=soildepscore]").val("7");
			$(".soildepscore").text("7");	
		}else{
			$("select[name=soildepVal]").val("10").prop("selected", true);
			$("input[name=soildepscore]").val("10");
			$(".soildepscore").text("10");	
		}
	}
	
	//경사
	if(objClassNm == "slp"){
		var slpArr = [];
		var sum = 0;
		$("input[class=slp]").each(function(){
			sum+= Number($(this).val());
			slpArr.push(Number($(this).val()));
		});
		var slpAvg = parseInt(sum/3);
		var slpMin = Math.min.apply(null, slpArr);
		var slpMax = Math.max.apply(null, slpArr);
		
		$(".mntntrnt").text(slpAvg+"° / "+slpMin+"~"+slpMax+"°");
		$("input[name=mntntrntavg]").val(slpAvg);
		$("input[name=mntntrntmin]").val(slpMin);
		$("input[name=mntntrntmax]").val(slpMax);
		
		if(slpAvg < 5){
			$("select[name=mntntrntVal]").val("3").prop("selected", true);
			$("input[name=mntntrntscore]").val("3");
			$(".mntntrntscore").text("3");	
		}else if(slpAvg < 15){
			$("select[name=mntntrntVal]").val("5").prop("selected", true);
			$("input[name=mntntrntscore]").val("5");
			$(".mntntrntscore").text("5");	
		}else if(slpAvg < 20){
			$("select[name=mntntrntVal]").val("8").prop("selected", true);
			$("input[name=mntntrntscore]").val("8");
			$(".mntntrntscore").text("8");	
		}else{
			$("select[name=mntntrntVal]").val("10").prop("selected", true);
			$("input[name=mntntrntscore]").val("10");
			$(".mntntrntscore").text("10");	
		}
	}
	
	
	changeTotScore();
	
}

/**
 * 판정표 점수 수정
 * @returns
 */
function changeTotScore(){
	var sum = 0;
	$("th[class*=score]").each(function(){
		sum += Number($(this).text());
	});
	// 현장조사점수
	$("input[name=fieldsurveyscore]").val(sum);
	$(".fieldsurveyscore").text(sum);
	$(".totScore").text(sum);
	
	// 총 합계 점수
	sum += Number($("input[name=stabanalscore]").val());
	$("input[name=scoresum]").val(sum);
	$(".scoresum").text(sum);
	
	if(sum < 34){
		$("select[name=jdgmntgrad]").val("C등급").prop("selected",true);	
	}else if(sum < 67){
		$("select[name=jdgmntgrad]").val("B등급").prop("selected",true);
	}else{
		$("select[name=jdgmntgrad]").val("A등급").prop("selected",true);
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
				 <select name="photoNum" class="photoNum" style="min-width:15%; width:15%; height:35px; float:left;">
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
	
	var numCnt = 14;
	var grade = $("select[name=jdgmntgrad]").val();
	if(grade.indexOf("C") == 0) numCnt = 6;
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

/**
 * 공간정보 다운로드
 * @param gid
 * @returns
 */
function fncDownloadWkaSvyShp(gid,svyid){
	if(confirm("공간정보를 다운로드하시겠습니까?")){
		$(".loading-div").show();
		
		var xhr = new XMLHttpRequest();
		var formdata = new FormData();
		formdata.set("gid",gid);
		formdata.set("svyId",svyid);
		
		xhr.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
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
			}
		};
		xhr.onerror = function(){
			$(".loading-div").hide();
			alert("다운로드를 실패하였습니다.");
		};
		xhr.open('POST', '/sys/lss/wka/sct/selectDownloadWkaSvyShp.do');
		xhr.responseType = 'blob';
		xhr.send(formdata);
	}
}

/**
 * 판정등급 변경
 * @returns
 */
function changeGrade(){
	var grade = $("select[name=jdgmntgrad]").val();
	if(grade.indexOf("C") == 0){
		$("select[name=photoNum]").each(function(e){
			var tagSize = $(this).children().length;
			if(tagSize > 7){
				while(tagSize != 7){
					$(this).children().last().remove();
					tagSize = $(this).children().length;
				}
			}
		})		
	}else{
		$("select[name=photoNum]").each(function(e){
			var tagSize = $(this).children().length;
			if(tagSize < 14){
				while(tagSize != 14){
					$(this).append("<option value='"+(tagSize+1)+"'>"+(tagSize+1)+"</option>");
					tagSize = $(this).children().length;
				}
			}
		})
	}
}

/**
 * 산사태, 토석류 안정해석 이미지 미리보기
 * @returns
 * @Description
 */
function readImg(input){ 
	var photoView = $(input).attr('name')+"ImgView";
	if (input.files && input.files[0]) {
		var reader = new FileReader();
    	reader.onload = function(e) {
			$('#'+photoView).attr('src',e.target.result);
    	};
    	reader.readAsDataURL(input.files[0]);
		$('#'+photoView).css('width','');
  	} else {
    	$('#'+photoView).attr('src',"");
  	}
}

/**
 * 산사태, 토석류 안정해석 이미지 삭제
 * @returns
 * @Description
 */
function fncDeleteStabanal(val){
	if( val != null ){
		var photoView = val.name+"ImgView";
		$("input[name="+val.name+"]").val("");
		$("#"+photoView).attr("src","../../../../../../images/common/noimage.png");
	}
}

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/wka/sct/selectWkaSvyComptList.do").attr("method","post");
	
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
	
	var checkboxes = document.querySelectorAll('input[name=jdgmntgradChkBox]:checked');
	var values = Array.prototype.slice.call(checkboxes)
	                  .map(function(checkbox) {
	                    return checkbox.value;
	                  });
	if(checkboxes.length == 0){
		$("input[name=jdgmntgrad]").val("empty");
	}else{
		var selectedValues = values.join(',');
		$("input[name=jdgmntgrad]").val(selectedValues);
	}
	
	$("#listForm").attr("action","/sys/lss/wka/sct/selectWkaSvyComptList.do");
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