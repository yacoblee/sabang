var $app = {};

$(document).ready(function() {
	
	if($("input[name=apiKey]").length > 0){
	    var apiKey = $("input[name=apiKey]").val();
	    $('.ol-basemap-control').css('right', '60px');	    
	    $.getScript("https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId="+apiKey+"&submodules=geocoder",function(){
	    	if($("input[name=mapParam]").val() == '') return;
			var param = JSON.parse($("input[name=mapParam]").val().replaceAll("'","\""));
		    var center = param.center.split(",");
		    var label = param.label;
		    var mapType = param.mapType;
		    
		    $("input[name=changedZoom]").val($("input[name=orginlZoom]").val());
		    naver.maps.onJSContentLoaded = function() {
			    var projCenter = naver.maps.TransCoord.fromEPSG3857ToLatLng(new naver.maps.LatLng(center[1],center[0]));
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
			    
		        var markerOptions = {
	        		position: new naver.maps.LatLng(projCenter),
	                map:$app.map,
	                icon:{
	                   path: naver.maps.SymbolPath.CIRCLE, 
	                   radius:7,
	                   fillColor: '#DE695D',
	                   fillOpacity: 1,
	                   strokeOpacity: 0
	                }
	        	};
		        var marker = new naver.maps.Marker(markerOptions);
		        
		        var infoWindowOptions = {
		            position: $app.map.getCenter(),
			        content: '<div>' + label+'</div>'
	        	};
		        var infoWindow = new naver.maps.InfoWindow(infoWindowOptions);
		        infoWindow.open($app.map,marker);
		        
		        naver.maps.Event.addListener($app.map, 'zoom_changed', function(zoom) {
		        	$("input[name=changedZoom]").val(zoom-1);
		        });
		        // 루프 돌리게 되면 해당 인덱스값 세팅
		        // 현재는 테스트 중이므로 1로 고정.
		        $("input[name=locImgIdx]").val(1);
		        
		        // 지도타입 변경 이벤트
		        $('input[name=mapType]').val(mapType);
		        if(mapType != null && mapType != ''){	// mapType이 있는 경우만 변경, 없으면 기본 위성지도으로출력
		        	$app.map.setMapTypeId(naver.maps.MapTypeId[mapType]);
		        	
		        	var normalBtn = $("#toggle-btn > button:eq(0)");
		        	var satelliteBtn = $("#toggle-btn > button:eq(1)");
		        	
		        	if(mapType == 'NORMAL'){
		        		normalBtn.addClass("selected");
		        		satelliteBtn.removeClass("selected");
		        	}else if(mapType == 'SATELLITE'){
		        		satelliteBtn.addClass("selected");
		        		normalBtn.removeClass("selected");
		        	}
		        }else{
		        	$('input[name=mapType]').val('SATELLITE');
		        	var normalBtn = $("#toggle-btn > button:eq(0)");
		        	var satelliteBtn = $("#toggle-btn > button:eq(1)");
		        	satelliteBtn.addClass("selected");
	        		normalBtn.removeClass("selected");
		        }
		    };
		    var mapTypeBtns = $("#toggle-btn > button");
		    mapTypeBtns.on("click", function(e) {
		        e.preventDefault();
		        
		        var mapTypeId = this.value;
		        $app.map.setMapTypeId(naver.maps.MapTypeId[mapTypeId]);
		        
		        if($(this).hasClass("selected")) return false;
		        
				$(".btn-map-selector").removeClass("selected");
				$(this).addClass("selected");
				
				$('input[name=mapType]').val(mapTypeId);
		    });
	    });
	}
	
	if($('.slide>div').length == 1) $('.slide_btn div').css({'display':'none'}); 
	$('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}
    });
	
	$(document).on("click",".photoUrl > span.thumb-div",function(){
		var photoClass = $(this).prevAll("input").attr("name");
		if($(this).hasClass("trglnd")){
			var $photo = JSON.parse($(this).closest(".photoTr").prevAll("input[name=trglndPhotoList]").val());
		}else if($(this).hasClass("dmgFclt")){
			var $photo = JSON.parse($(this).closest(".photoTr").prevAll("input[name=dmgFcltPhotoList]").val());
		}else{
			var $photo = JSON.parse($("input[name=photolist]").val());					
		}
		
		$('<div id="photoDiv">').dialog({
			modal:true,
			open: function(){
				// 모달 오버레이 설정
				$(".ui-widget-overlay").css({
					opacity: 0.5,
					filter: "Alpha(Opacity=50)",
					backgroundColor: "black"
				});
				$.each($photo,function(idx,elm){
					var cnt = idx+1;
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
					var src = $("img.selected").attr("src");
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
	
	// input enter event
	$('#svyId,#svyUser,#mstNm').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
});


/**
 * @author ipodo
 * @param
 * @returns
 * @description 파일업로드 이벤트
 */
// 파일을 담아둘 배열
var fileList = [];
$(document).ready(function() {
	
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
        	
        	if(fileList.length == 0){
        		var table_html = '<table id="fileList" summary="업로드할 파일의 정보를 출력합니다.">';
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
        		$(".drag-div").empty().append($table);
        	}
        	
        	if(files.length > 0){
        		
        		for(var i=0; i<files.length; i++){
	        		
        			var f = files[i];
        			fileList.push(f);
        			
	        		var nm = files[i].name.split(".");
	        		nm.pop();
	        		var filenm = nm.join(".");
	        		var size = files[i].size;
	        		var sizeMb = size ? size/1024/1024 : 0;
	        		
	        		console.log("file name is ",filenm,", file size is ",size);
	        		
	        		var $tr = $("<tr></tr>");
	        		
	        		$tr.append("<td>".concat(nm,"</td>"));
	        		$tr.append("<td>".concat(sizeMb.toFixed(2)," Mb</td>"));
	        		
	        		var $td = $("<td><button></button></td>");
	        		$td.find("button").attr("type","button").attr("class","del-file-btn");
	        		
	        		$tr.append($td);
	        		$("#fileList").find("tbody").append($tr);
	        		
	        		$td.find("button").on("click", function(){
	        			var trNum = $(this).closest("tr").index();
	        			fileList.splice(trNum, 1);
	        			
	        			var del_tr = $(this).parent().parent();           
	        			del_tr.remove();
	        			
	        			var trLen = $("#fileList tr").length;
	        			if(trLen == 1){
		        			$(".drag-div").addClass("drag-active").removeClass("BoardList");
		        			$(".drag-div").empty().append('<p class="drag-msg noselect">파일을 드래그하세요.</p>');
		        			fileList.length = 0;
		        			$(".drag-div").css("overflow-y","hidden");
	        			}
	        		});
	        		
	        		$(".drag-div").addClass("BoardList").removeClass("drag-active");
	        		$(".drag-div").css("overflow-y","auto");
	        	}
        		
	        	$(this).removeClass("over");
	        	e.preventDefault();
	        	e.stopPropagation();
        	}
        }
    },".drag-div");
	
});

/**
 * 조사완료지 상세화면
 * @param id
 * @returns
 */
function fncPcsComptDetail(gId) {
	$("input[name=gid]").val(gId);
	$("#listForm").attr("action","/sys/fck/pcs/sct/selectFckPcsComptDetail.do");
	$("#listForm").submit();
}

/**
 * 대상지 위치도 다음 사진 
 * @param id
 * @returns
 */
var cnt=0;
function next(){
	var img=$('.slide>div');
	var max=img.length-1;	
	if(img.is(':animated')) return false;
	$(img[cnt]).animate({'left':'-100%'}).siblings().css({'left':'100%'});
	cnt++;
	if(cnt>max) cnt=0;
	$(img[cnt]).animate({'left':0});
}

/**
 * 대상지 위치도 이전 사진 
 * @param id
 * @returns
 */
function prev(){
	var img=$('.slide>div');
	var max=img.length-1;
	if(img.is(':animated')) return false;
	$(img[cnt]).animate({'left':'100%'}).siblings().css({'left':'-100%'});
	cnt--;
	if(cnt<0) cnt=max;
	$(img[cnt]).animate({'left':0});
}
/**
 * 조사완료지 삭제
 * @param id
 * @returns
 */

function fncDeletePcsCompt(gid){
	$("input[name=gid]").val(gid);
	if(confirm("삭제하시겠습니까?")) {
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/fck/pcs/sct/deleteFckPcsCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    fnList();
                    //location.href="/sys/fck/pcs/sct/selectPcsSvyComptList.do";
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
function fncUpdatePcsComptView(){
//	$("#listForm").attr("action","/sys/fck/pcs/sct/updateFckPcsComptView.do");
//	$("#listForm").submit();
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/fck/pcs/sct/updateFckPcsComptView.do").attr("method","post");
	
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
function fncFckPcsComptUpdate(){
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
			
			for(var i= 0;i<6;i++){
				var j = String(i+1);
				if(photoNumArr.indexOf(j) == -1 && isphotoNumChk == 0) isphotoNumChk = j;
				if(photoNumArr.indexOf(j) != photoNumArr.lastIndexOf(j) && isphotoNumDup == 0) isphotoNumDup = j;
			}
			
			if(isphotoNumChk != 0){
				alert(isphotoNumChk+'번 현장사진 순번이 지정되지 않았습니다.');
				return false;
			}
			if(isphotoNumDup != 0){
				alert(isphotoNumDup+'번 현장사진 순번이 중복 되었습니다.');
				return false;
			}
		}
		
		/* 압축강도 및 균열깊이 값 설정*/
		fnSetChckTableVal();
		
		/*var svyType = $("#svyType").val();
		if(svyType.indexOf('사방댐') == 0){
			
//			var idx = $(".dmgSttusTable  tr").length-2;
//			var dmgSttusArray = [];
//			for(var j=0; j<idx; j++){
//				
//				var dmgSttusPhoto = $("input[name=dmgsttusPhoto"+(j+1)+"]").val();
//				var dmgSttusType = $("input[name=dmgsttusType"+(j+1)+"]").val();
//				var dmgSttusLoc = $("input[name=dmgsttusLoc"+(j+1)+"]").val();
//				var dmgSttusNote = $("input[name=dmgsttusNote"+(j+1)+"]").val();
//				var dmgSttusPhotoTag = $("input[name=dmgsttusPhotoTag"+(j+1)+"]").val();
//				
//				if( dmgSttusPhotoTag != "" && dmgSttusPhotoTag !=[] && dmgSttusPhotoTag != null && dmgSttusPhotoTag != undefined ){
//					dmgSttusPhotoTag.replace(/\\\//g, "/");
//				}else{
//					dmgSttusPhotoTag = [];
//				}
//				
//				if(dmgSttusType == '' && dmgSttusLoc == '' && dmgSttusNote == ''){
//					alert("입력되지 않은 행이 있습니다.");
//					return false;
//				}
//				
//				if( dmgSttusPhoto != "" &&dmgSttusPhoto !=[] && dmgSttusPhoto != null && dmgSttusPhoto != undefined ){
//					dmgSttusPhoto.replace(/\\\//g, "/")
//				}else{
//					dmgSttusPhoto=[];
//				}
//				
//				var param ={
//					"사진" : dmgSttusPhoto,
//					"사진태그" : dmgSttusPhotoTag,
//					"비고" : dmgSttusNote,
//					"위치" : dmgSttusLoc,
//					"유형" : dmgSttusType
//				};
//				dmgSttusArray.push(param);
//			}
//			$("#listForm").append($('<input/>', {type: 'hidden', name: "dmgSttus", value:JSON.stringify(dmgSttusArray) }));
			
		}else if(svyType.indexOf('계류보전') == 0){
			
			// 대상지 위치 및 주요시설 테이블
			var idx1 = $(".trglndTr").length;
			var dmgTrglndArr = [];
			for(var j=0; j<idx1; j++){
				// 시점 위도 37
				var dmgTrglndBgPyD = parseInt($("input[name=dmgTrglndBgPyD"+(j+1)+"]").val());
				var dmgTrglndBgPyM = parseInt($("input[name=dmgTrglndBgPyM"+(j+1)+"]").val());
				var dmgTrglndBgPyS = parseFloat($("input[name=dmgTrglndBgPyS"+(j+1)+"]").val());
				var bpy = dmgTrglndBgPyD+(dmgTrglndBgPyM/60)+(dmgTrglndBgPyS/3600);
				if(!isNaN(bpy)){
					bpy = dmgTrglndBgPyD+(dmgTrglndBgPyM/60)+(dmgTrglndBgPyS/3600);
				}else{
					bpy = "";
				}
				
				// 시점 경도 127
				var dmgTrglndBgPxD = parseInt($("input[name=dmgTrglndBgPxD"+(j+1)+"]").val());
				var dmgTrglndBgPxM = parseInt($("input[name=dmgTrglndBgPxM"+(j+1)+"]").val());
				var dmgTrglndBgPxS = parseFloat($("input[name=dmgTrglndBgPxS"+(j+1)+"]").val());
				var bpx = dmgTrglndBgPxD+(dmgTrglndBgPxM/60)+(dmgTrglndBgPxS/3600);
				if(!isNaN(bpx)){
					bpx = dmgTrglndBgPxD+(dmgTrglndBgPxM/60)+(dmgTrglndBgPxS/3600);
				}else{
					bpx = "";
				}
				
				// 종점 위도 				
				var dmgTrglndEdPyD = parseInt($("input[name=dmgTrglndEdPyD"+(j+1)+"]").val());
				var dmgTrglndEdPyM = parseInt($("input[name=dmgTrglndEdPyM"+(j+1)+"]").val());
				var dmgTrglndEdPyS = parseFloat($("input[name=dmgTrglndEdPyS"+(j+1)+"]").val());
				var epy = dmgTrglndEdPyD+(dmgTrglndEdPyM/60)+(dmgTrglndEdPyS/3600);
				if(!isNaN(epy)){
					epy = dmgTrglndEdPyD+(dmgTrglndEdPyM/60)+(dmgTrglndEdPyS/3600);
				}else{
					epy = "";
				}
				
				// 종점 경도
				var dmgTrglndEdPxD = parseInt($("input[name=dmgTrglndEdPxD"+(j+1)+"]").val());
				var dmgTrglndEdPxM = parseInt($("input[name=dmgTrglndEdPxM"+(j+1)+"]").val());
				var dmgTrglndEdPxS = parseFloat($("input[name=dmgTrglndEdPxS"+(j+1)+"]").val());
				var epx = dmgTrglndEdPxD+(dmgTrglndEdPxM/60)+(dmgTrglndEdPxS/3600);
				
				if(!isNaN(epx)){
					epx = dmgTrglndEdPxD+(dmgTrglndEdPxM/60)+(dmgTrglndEdPxS/3600);
				}else{
					epx = "";
				}
				
				
				if( bpx == '' || bpy == '' ){
					if( epx == '' || epy == '' ){
						alert("좌표가 입력되지 않았습니다.");
						return false;
					}	
				}else if( isNaN(bpx) || isNaN(bpy) ){
					if( isNaN(epx) || isNaN(epy) ){
						alert("좌표가 입력되지 않았습니다.");
						return false;
					}	
				}
				
				
				// 주요시설물
				var dmgTrglndStrctu = $("input[name=dmgTrglndStrctu"+(j+1)+"]").val();
				// 비고
				var dmgTrglndNote = $("input[name=dmgTrglndNote"+(j+1)+"]").val();
				// 사진
				var dmgTrglndPhoto = $("input[name=dmgTrglndPhoto"+(j+1)+"]").val();
				// 사진태그
				var dmgTrglndPhotoTag = $("input[name=dmgTrglndPhotoTag"+(j+1)+"]").val();
				
				if( dmgTrglndPhoto != "" && dmgTrglndPhoto !=[] && dmgTrglndPhoto != null && dmgTrglndPhoto != undefined ){
					dmgTrglndPhoto.replace(/\\\//g, "/");
				}else{
					dmgTrglndPhoto=[];
				}
				if( dmgTrglndPhotoTag != "" && dmgTrglndPhotoTag !=[] && dmgTrglndPhotoTag != null && dmgTrglndPhotoTag != undefined ){
					dmgTrglndPhotoTag.replace(/\\\//g, "/")
				}else{
					dmgTrglndPhotoTag = [];
				}
				
				var param1 ={
						"X좌표1" : bpx,
						"Y좌표1" : bpy,
						"X좌표2" : epx,
						"Y좌표2" : epy,
						"사진" : dmgTrglndPhoto,
						"주요시설물" : dmgTrglndStrctu,
						"비고" : dmgTrglndNote
					};
				
				dmgTrglndArr.push(param1);
				
				$("#listForm").append($('<input/>', {type: 'hidden', name: "dmgTrglndPhotoTag"+(j+1), value:dmgTrglndPhotoTag }));
			}
			$("#listForm").append($('<input/>', {type: 'hidden', name: "trglnd", value:JSON.stringify(dmgTrglndArr) }));
			
			
			// 피해시설 위치 및 피해현황 테이블
			var idx2 = $(".dmgFcltTable tr").length-3;
			var dmgFcltArr = [];
				for(var k=0; k<idx2; k++){
					
				// 시점 위도 37
				var dmgFcltPyD = parseInt($("input[name=dmgFcltPyD"+(k+1)+"]").val());
				var dmgFcltPyM = parseInt($("input[name=dmgFcltPyM"+(k+1)+"]").val());
				var dmgFcltPyS = parseFloat($("input[name=dmgFcltPyS"+(k+1)+"]").val());
				
				if(!isNaN(bpy)){
					bpy = dmgFcltPyD+(dmgFcltPyM/60)+(dmgFcltPyS/3600);
				}else{
					bpy = "";
				}
				// 시점 경도 127
				var dmgFcltPxD = parseInt($("input[name=dmgFcltPxD"+(k+1)+"]").val());
				var dmgFcltPxM = parseInt($("input[name=dmgFcltPxM"+(k+1)+"]").val());
				var dmgFcltPxS = parseFloat($("input[name=dmgFcltPxS"+(k+1)+"]").val());
				if(!isNaN(bpx)){
					bpx = dmgFcltPxD+(dmgFcltPxM/60)+(dmgFcltPxS/3600);
				}else{
					bpx = "";
				}
				

				if(bpx == '' || bpy == '' || isNaN(bpx) || isNaN(bpy)){
					alert("좌표가 입력되지 않았습니다.");
					return false;
				}
					
				// 주요시설물
				var dmgFcltStrctu = $("input[name=dmgFcltStrctu"+(k+1)+"]").val();
				// 비고
				var dmgFcltNote = $("input[name=dmgFcltNote"+(k+1)+"]").val();
				// 현황
				var dmgFcltSttus = $("input[name=dmgFcltSttus"+(k+1)+"]").val();
				// 사진
				var dmgFcltPhoto = $("input[name=dmgFcltPhoto"+(k+1)+"]").val();
				if( dmgFcltPhoto != "" &&dmgFcltPhoto !=[] && dmgFcltPhoto != null && dmgFcltPhoto != undefined ){
					dmgFcltPhoto.replace(/\\\//g, "/")
				}else{
					dmgFcltPhoto=[];
				}
				// 사진태그
				var dmgFcltPhotoTag = $("input[name=dmgFcltPhotoTag"+(k+1)+"]").val();
				
				if( dmgFcltPhotoTag != "" && dmgFcltPhotoTag !=[] && dmgFcltPhotoTag != null && dmgFcltPhotoTag != undefined ){
					dmgFcltPhotoTag.replace(/\\\//g, "/");
				}else{
					dmgFcltPhotoTag = [];
				}
				
				var param2 ={
						"X좌표" : bpx,
						"Y좌표" : bpy,
						"사진" : dmgFcltPhoto,
						"주요시설물" : dmgFcltStrctu,
						"현황" : dmgFcltSttus,
						"비고" : dmgFcltNote
					};
				
				dmgFcltArr.push(param2);
				
				$("#listForm").append($('<input/>', {type: 'hidden', name: "dmgFcltPhotoTag"+(k+1), value:dmgFcltPhotoTag }));
			}
			$("#listForm").append($('<input/>', {type: 'hidden', name: "dmgFclt", value:JSON.stringify(dmgFcltArr) }));
		}else if(svyType.indexOf('산지사방') == 0){
			// 대상지 위치 및 주요시설 테이블
			var idx1 = $(".dmgSttusTable tr").length-3;
			var dmgTrglndArr = [];
			for(var j=0; j<idx1; j++){
				// 시점 위도 37
				var dmgTrglndPyD = parseInt($("input[name=dmgTrglndPyD"+(j+1)+"]").val());
				var dmgTrglndPyM = parseInt($("input[name=dmgTrglndPyM"+(j+1)+"]").val());
				var dmgTrglndPyS = parseFloat($("input[name=dmgTrglndPyS"+(j+1)+"]").val());
				var py = dmgTrglndPyD+(dmgTrglndPyM/60)+(dmgTrglndPyS/3600);
				if(!isNaN(py)){
					py = dmgTrglndPyD+(dmgTrglndPyM/60)+(dmgTrglndPyS/3600);
				}else{
					py = "";
				}
				
				// 시점 경도 127
				var dmgTrglndPxD = parseInt($("input[name=dmgTrglndPxD"+(j+1)+"]").val());
				var dmgTrglndPxM = parseInt($("input[name=dmgTrglndPxM"+(j+1)+"]").val());
				var dmgTrglndPxS = parseFloat($("input[name=dmgTrglndPxS"+(j+1)+"]").val());
				var px = dmgTrglndPxD+(dmgTrglndPxM/60)+(dmgTrglndPxS/3600);
				if(!isNaN(px)){
					px = dmgTrglndPxD+(dmgTrglndPxM/60)+(dmgTrglndPxS/3600);
				}else{
					px = "";
				}
				
				// 주요시설물
				var dmgTrglndStrctu = $("input[name=dmgTrglndStrctu"+(j+1)+"]").val();
				// 비고
				var dmgTrglndNote = $("input[name=dmgTrglndNote"+(j+1)+"]").val();
				// 사진
				var dmgTrglndPhoto = $("input[name=dmgTrglndPhoto"+(j+1)+"]").val();
				// 사진태그
				var dmgTrglndPhotoTag = $("input[name=dmgTrglndPhotoTag"+(j+1)+"]").val();
				// 현황
				var dmgTrglndSttus = $("input[name=dmgTrglndSttus"+(j+1)+"]").val();
				
				if( dmgTrglndPhoto != "" &&dmgTrglndPhoto !=[] && dmgTrglndPhoto != null && dmgTrglndPhoto != undefined ){
					dmgTrglndPhoto.replace(/\\\//g, "/")
				}else{
					dmgTrglndPhoto=[];
				}
				if( dmgTrglndPhotoTag != "" &&dmgTrglndPhotoTag !=[] && dmgTrglndPhotoTag != null && dmgTrglndPhotoTag != undefined ){
					dmgTrglndPhotoTag.replace(/\\\//g, "/")
				}else{
					dmgTrglndPhotoTag=[];
				}
				
				if(px == '' || py == '' || isNaN(px) || isNaN(py)){
					alert("좌표가 입력되지 않았습니다.");
					return false;
				}
				
				var param1 ={
						"X좌표" : px,
						"Y좌표" : py,
						"사진" : dmgTrglndPhoto,
						"주요시설물" : dmgTrglndStrctu,
						"비고" : dmgTrglndNote,
						"현황" : dmgTrglndSttus
					};
				
				dmgTrglndArr.push(param1);
				
				$("#listForm").append($('<input/>', {type: 'hidden', name: "dmgTrglndPhotoTag"+(j+1), value:dmgTrglndPhotoTag }));
			}
			$("#listForm").append($('<input/>', {type: 'hidden', name: "trglnd", value:JSON.stringify(dmgTrglndArr) }));
		}
		*/
			
		
		
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: $("#listForm")[0].action,
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    fnList("/sys/fck/pcs/sct/selectPcsSvyComptList.do");
                    //location.href="/sys/fck/pcs/sct/selectPcsSvyComptList.do";
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
	
	if(svyType.indexOf("사방댐 정밀점검") !== -1) {// 추후 사방댐 콘크리트, 석재, 강재로 나뉜거 확정되면 보고서 추가
		fileName = "REPORT_SYS_FCK_PCS_01";
	}else if(svyType == "계류보전 정밀점검"){
		fileName = "REPORT_SYS_FCK_PCS_02";
	}else {
		fileName = "REPORT_SYS_FCK_PCS_03";
	}
	
	form = $("form[name=reportForm]");
	
	console.log("gid : "+ gid);
	console.log("svyType : "+ svyType);
	console.log("fileName : "+ fileName);
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
 * 측정값 선택
 * @returns
 */
function fncGetSelectVal(e) {
	var elmVal = "#"+e.target.name.replaceAll("Cd","Val");		
	var selectVal = $("select[name='"+e.target.name+"']").val();
	
	if($(elmVal).val().indexOf(selectVal) > -1 && selectVal.length > 0 ) {
		selectVal = "";
		alert("이미 선택한 값입니다.");
	}
	
	if(selectVal != "기타"){
		$(elmVal).val($(elmVal).val() +","+ selectVal);
	}
	
	if($(elmVal).val().charAt(0) == ","){
		$(elmVal).val($(elmVal).val().substr(1));
	}
	if($(elmVal).val().charAt($(elmVal).val().length-1) == ","){
		$(elmVal).val($(elmVal).val().substr(0,$(elmVal).val().length-1));
	}
}

/**
 * 저사상태 측정값 선택
 * @returns
 */
function fncChange(obj) {
	if(obj != null){
		var val = obj.value;
		val = val.replace(/[^0-9]/,"");
		if(Number(val) < 50){
			$("#snddpsitJdgVal option:eq(1)").prop("selected","true");
		}else if(Number(val) < 80){
			$("#snddpsitJdgVal option:eq(2)").prop("selected","true");
		}else if(Number(val) <= 100){
			$("#snddpsitJdgVal option:eq(3)").prop("selected","true");
		}else{
			$("#snddpsitJdgVal option:eq(0)").prop("selected","true");
		}
	}
	
//	$("#snddpsitJdgVal option:eq(0)").prop("selected", true);		
		
//		if(Number(elmVal.value) < 50) {
//			$("#snddpsitJdgVal option:eq(1)").prop("selected","true");
//		} else if(Number(elmVal.value) >= 50 && Number(elmVal.value) < 80) {
//			$("#snddpsitJdgVal option:eq(2)").prop("selected","true");
//		} else if(Number(elmVal.value) >= 80 && Number(elmVal.value) <= 100){
//			$("#snddpsitJdgVal option:eq(3)").prop("selected","true");
//		}
	
}

function fncGetInputBlur() {
	var elmVal = document.getElementById("snddpsitVal");

	$("#snddpsitJdgVal option:eq(0)").prop("selected", true);
	
	if(Number(elmVal.value) < 50) {
		$("#snddpsitJdgVal option:eq(1)").prop("selected","true");
	} else if(Number(elmVal.value) >= 50 && Number(elmVal.value) < 80) {
		$("#snddpsitJdgVal option:eq(2)").prop("selected","true");
	} else if(Number(elmVal.value) >= 80 && Number(elmVal.value) <= 100){
		$("#snddpsitJdgVal option:eq(3)").prop("selected","true");
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
 * 행정구역 시군구 명칭조회 ajax
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
 * 지도이동
 * @param gid
 * @returns
 */
function fncOpenMap(type,gid){
	var url = "/sys/gis/mainMap.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	form.append($('<input/>', {type: 'hidden', name: "gid", value:gid })).append($('<input/>', {type: 'hidden', name: "type", value:type }));
	form.appendTo("body").submit().remove();
}

/**
 * 대상지목록 엑셀 다운로드
 * @param gid
 * @returns
 */
function fncDownloadSldListExcel(gid){
	var url = "/sys/fck/pcs/sct/selectDownloadSldListExcel.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	form.append($('<input/>', {type: 'hidden', name: "gid", value:gid}));
	
	form.appendTo("body").submit().remove();
}

/**
 * 현장사진 압축파일 다운로드
 * @param gid
 * @returns
 */
function fncDownloadPhotoListZip(gid, svyType, photoType){
	$(".loading-div").show();
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	
	formdata.append("gid",gid);
	formdata.append("svyType",svyType);
	if(svyType == '계류보전 정밀점검' || svyType == '사방댐 정밀점검'){
		formdata.append("photoType",photoType);
	}else{
		formdata.append("photoType","산지사방");
	}
	xhr.open('POST', '/sys/fck/pcs/sct/selectDownloadPhotoListZip.do');
	xhr.responseType = 'blob';
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4){
			if(this.status == 200){
				$(".loading-div").hide();
				var filename = "";
				var disposition = xhr.getResponseHeader('Content-Disposition');
				if (disposition && disposition.indexOf('attachment') !== -1) {
					var filenameRegex =  /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
					var matches = filenameRegex.exec(disposition);
					if (matches != null && matches[1]){
						var rawfilename = matches[1].replace(/['"]/g, '');
						filename = decodeURIComponent(rawfilename);
					}
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
	xhr.send(formdata);
	
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

/*
 * 조사완료지 엑셀 다운로드
 */
function fnExcelDown(){
	
	var svyType = $("input[name=svyType]").val();
	
	if(svyType.length == 0){
		alert("조사유형를 반드시 선택해야 엑셀파일을 생성할 수 있습니다.\n목록조회 후 다시 시도해주세요.");
		return;
	}
	
	var url = $("#listForm").attr("action");
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	url = url.replace(".do","Excel.do");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), svyType: $(elm).attr("svyType"), value:$(elm).val() }));
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
        url: "/sys/fck/pcs/sct/selectLocReCeateCnt.do",
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
	        url: "/sys/fck/pcs/sct/updateLocReCreate.do",
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

/**
 * 현장사진 추가 
 * @returns
 */
function fncFckPcsAddPhoto(){
	if($("input[name=photolist]").val() == ''){
		alert("현장사진 정보가 없습니다.");
		return false;
	}
	var svyType = $('input[name=svyType]').val();
	var imgCnt = 1;
	if($(".photoBox").length > 0 ) imgCnt = Number($('input[name*=photoTag]:last').attr("name").substr(8)) + 1;
	$('.photoWrap').append(`
		<div class="photoBox" style="width: 50%;">
	    	<div class="photoTag">
		        <select name="photoNum" class="photoNum" style="width:5%; height:35px; float:left;">
		            <option value="" >선택</option>
		        </select> 
		         <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag`+imgCnt+`"/> 
		         <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncFckPcsDeletePhoto(event); return false;">삭제</button>
	    	</div>
	    	<div class="photoUrl">
	            <input type="hidden" name="photoSrc`+imgCnt+`" value=""/>
	            <img src="../../../../images/common/noimage.png" style="width:30%;" class="photoSrc`+imgCnt+`" onerror="this.remove ? this.remove() : this.removeNode();">
		 		<span class="thumb-div noselect">변경</span>
			</div>
	    </div>
	`);
	var numCnt = 6;
	if(svyType == '산지사방 정밀점검') numCnt = 30;
	for(var i = 0; i<numCnt; i++){
		$('.photoNum:last').append('<option value="'+(i+1)+'">'+(i+1)+'</option>')
	}
	$('.photoNum:last option:eq(0)').prop('selected',true);
	//window.scrollTo(0,document.body.scrollHeight);
}

/**
 * 현장사진 삭제
 * @returns
 */
function fncFckPcsDeletePhoto(e){
	$(e.target).closest('.photoBox').remove();
}

/**
 * 조사완료 재업로드 팝업
 * @returns
 */
function fnUpdateFckPcsComptPopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updateFckPcsComptPopup.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 310,
		width: 400,
		title: "정밀점검 완료지 갱신"
	});
	
}

/**
 * 조사완료 재업로드
 * @returns
 */
function fnUpdateFckPcsCompt(){
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
			//url:"/sys/fck/pcs/sld/uploadStripLandExcel.do",
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

/*
 * 사방댐 피해발생현황 행 추가 및 삭제 
 */
function fncFckPcsAddDelDmgSttusRow(e) {
	var svyType = $("#svyType").val();
	if(svyType.indexOf('사방댐') == 0){
		var idx = $(".dmgSttusTable tr").length-1;
		
		if(e == "add"){
			var appendHtml = "";
			appendHtml += "<tr class='center'>";
			appendHtml += 	"<td>";
			appendHtml += 		idx;
			appendHtml += 	"</td>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='dmgsttusType"+idx+"'>";
			appendHtml += 	"</td>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='dmgsttusLoc"+idx+"'>";
			appendHtml += 	"</td>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='dmgsttusNote"+idx+"'>";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$(".dmgSttusTable").append(appendHtml);
		}else{
			if(idx < 2){
				return false;
			}else{
				$(".dmgSttusTable tr:last").remove();
			}
		}
	}else if(svyType.indexOf('계류보전') == 0){
		
		var idx1 = $(".trglndTr").length+1;
		var idx2 = $(".dmgFcltTable tr").length-2;
		
		if(e == "add1"){
			var appendHtml = "";
			appendHtml += "<tr class='center trglndTr'>";
			appendHtml += 	"<td rowspan='2'>"+idx1+"</td>";
			appendHtml += 	"<td>시점</td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndBgPyD"+idx1+"' onKeyup=this.value=this.value.replace(/[^0-9]/g,''); style='width: 30px;'/>° <input type='text' name='dmgTrglndBgPyM"+idx1+"' onKeyup=this.value=this.value.replace(/[^0-9]/g,''); style='width: 30px;'/>' <input type='text' name='dmgTrglndBgPyS"+idx1+"' onKeyup=this.value=this.value.replace(/[^-\.0-9]/g,''); style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndBgPxD"+idx1+"' onKeyup=this.value=this.value.replace(/[^0-9]/g,''); style='width: 30px;'/>° <input type='text' name='dmgTrglndBgPxM"+idx1+"' onKeyup=this.value=this.value.replace(/[^0-9]/g,''); style='width: 30px;'/>' <input type='text' name='dmgTrglndBgPxS"+idx1+"' onKeyup=this.value=this.value.replace(/[^-\.0-9]/g,''); style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td rowspan='2'><input type='text' name='dmgTrglndStrctu"+idx1+"'/></td>";
			appendHtml += 	"<td rowspan='2'><input type='text' name='dmgTrglndNote"+idx1+"'/></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='center'>";
			appendHtml += 	"<td>종점</td>"; 
			appendHtml += 	"<td><input type='text' name='dmgTrglndEdPyD"+idx1+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>° <input type='text' name='dmgTrglndEdPyM"+idx1+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>' <input type='text' name='dmgTrglndEdPyS"+idx1+"' onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndEdPxD"+idx1+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>° <input type='text' name='dmgTrglndEdPxM"+idx1+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>' <input type='text' name='dmgTrglndEdPxS"+idx1+"' onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'/>\" </td>";
			appendHtml += "</tr>";
			
			$(".dmgSttusTable").append(appendHtml);
		}else if(e == "del1"){
			if(idx1 < 2){
				return false;
			}else{
				$(".dmgSttusTable tr:last").remove();
				$(".trglndTr:last").remove();
			}
		}else if(e == "add2"){
			var appendHtml = "";
			appendHtml += "<tr class='center'>";
			appendHtml += 	"<td>"+idx2+"</td>";
			appendHtml += 	"<td><input type='text' name='dmgFcltPyD"+idx2+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>° <input type='text' name='dmgFcltPyM"+idx2+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>' <input type='text' name='dmgFcltPyS"+idx2+"' onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td><input type='text' name='dmgFcltPxD"+idx2+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>° <input type='text' name='dmgFcltPxM"+idx2+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>' <input type='text' name='dmgFcltPxS"+idx2+"' onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td><input type='text' name='dmgFcltStrctu"+idx2+"' /></td>";
			appendHtml += 	"<td><input type='text' name='dmgFcltSttus"+idx2+"' /></td>";
			appendHtml += 	"<td><input type='text' name='dmgFcltNote"+idx2+"' /></td>";
			appendHtml += "</tr>";
			$(".dmgFcltTable").append(appendHtml);
		}else if(e == "del2"){
			if(idx2 < 2){
				return false;
			}else{
				$(".dmgFcltTable tr:last").remove();
			}
		}
	}else if(svyType.indexOf('산지사방') == 0){
		var idx = $(".dmgSttusTable tr").length-2;
		if(e == "add1"){
			var appendHtml = "";
			appendHtml += "<tr class='center'>";
			appendHtml += 	"<td>"+idx+"</td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndPyD"+idx+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>° <input type='text' name='dmgTrglndPyM"+idx+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>' <input type='text' name='dmgTrglndPyS"+idx+"' onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndPxD"+idx+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>° <input type='text' name='dmgTrglndPxM"+idx+"' onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'/>' <input type='text' name='dmgTrglndPxS"+idx+"' onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'/>\" </td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndStrctu"+idx+"'/></td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndSttus"+idx+"'/></td>";
			appendHtml += 	"<td><input type='text' name='dmgTrglndNote"+idx+"'/></td>";
			appendHtml += "</tr>";
			
			$(".dmgSttusTable").append(appendHtml);
		}else if(e == "del1"){
			if(idx < 2){
				return false;
			}else{
				$(".dmgSttusTable tr:last").remove();
			}
		}
	}
	
}


/*
 * 피해발생현황도 사진 미리보기
 */
function SttusPrntImg(event){
	var fileToImg = event.target;
	if (fileToImg.files && fileToImg.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e){
			
			var tagNm = fileToImg.name;
			var tagId = tagNm.replace(/(\d+)/, "Img$1");
			
			var imgBox = document.getElementById(tagId);
			var img = new Image();
			img.src = e.target.result;
			
			if(tagNm == 'sttusPrnt3'){
				img.style = 'max-width:70%';
			}else{
				img.style = 'max-width:100%';
			}
				
			imgBox.innerHTML = "";
			imgBox.appendChild(img);
		}
	}
    reader.readAsDataURL(fileToImg.files[0]);
	
}

/*
 * 피해발생현황도 사진 삭제
 */
function fnDeleteSttusPrntImg(val){
	if( val != null ){
		$("input[name=sttusPrnt"+val+"]").val("");
		$("#sttusPrntImg"+val).children().attr("src","../../../../../../images/common/noimage.png");
		
		$("input[name=rawSttusPrnt"+val+"]").val("{}");
	}
}


/**
 * 현장사진 압축파일 다운로드
 * @param gid
 * @returns
 */
function fncDownloadPhotoListZip(gid){
	$(".loading-div").show();
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	
	formdata.append("gid",gid);
	xhr.open('POST', '/sys/fck/pcs/sct/selectDownloadPhotoListZip.do');
	xhr.responseType = 'blob';
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4){
			if(this.status == 200){
				$(".loading-div").hide();
				var filename = "";
				var disposition = xhr.getResponseHeader('Content-Disposition');
				if (disposition && disposition.indexOf('attachment') !== -1) {
					var filenameRegex =  /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
					var matches = filenameRegex.exec(disposition);
					if (matches != null && matches[1]){
						var rawfilename = matches[1].replace(/['"]/g, '');
						filename = decodeURIComponent(rawfilename);
					}
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
	xhr.send(formdata);
}


/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){

	var form = $("<form></form>").attr("action","/sys/fck/pcs/sct/selectPcsSvyComptList.do").attr("method","post");
	

	
	form.appendTo("body").submit().remove();
}
/**
 * 검색
 * @returns
 * @Description
 */
function fnSearch(){
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageUnit]").val(pageUnit);
	
	
	$("#listForm").attr("action","/sys/fck/pcs/sct/selectPcsSvyComptList.do");
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

/*
 * @author ipodo
 * @param {Event} e - change event
 * @returns 
 * @description 평가표 체인지 이벤트
*/
function fnChangeEvlTable(e) {
	var val = $(e.target).find("option:selected").data("sub");
	$(e.target).parent().next().children().val(val);
	
	var total = 0;
	
	// 총점 재산정
	$('#evlTbl tbody tr').each(function (i, el) { 
		if(i < 13) {
			total += Number($(el).find('td:eq(1)').children().val());	
		} 
	});
	
	// 준설평가표 총점 변경
	$('#evlTbl tbody tr:eq(13)').find('td').text(total);
}

/*
 * @author ipodo
 * @param {Event} e - change event
 * @returns 
 * @description 준설 평가표 체인지 이벤트
*/
function fnChangeDrdgEvlTable(e) {
	var val = $(e.target).find("option:selected").data("sub");
	$(e.target).parent().next().children().val(val);
	// 계수 
	var cfEl = $(e.target).parent().next().children();
	var cfVal = Number($(cfEl).val());
	var cf = cfVal.toFixed(2);
	// 가중치
	var wgEl = $(cfEl).parent().next().children();
	var wgVal = Number($(wgEl).val());
	var wg = wgVal.toFixed(2);
	
	var stdEl = $(wgEl).parent().next().children();
	var stdVal = Number(cf * wg).toFixed(2); 
	$(stdEl).val(stdVal);
	
	var total = 0;
	
	// 총점 및 준설여부 판정 값 재산정
	$('#drdgEvlTbl tbody tr').each(function (i, el) { 
		if(i < 4) {
			total += Number($(el).find('td:eq(3)').children().val());	
		} 
	});

	// 준설평가표 총점 변경
	$('#drdgEvlTbl tbody tr:eq(5) td:eq(0)').children().val(total);
	// 준설평가표 판정 변경	
	$('#drdgEvlTbl tbody tr:eq(5) td:eq(1)').children().val(total < 17 ? '준설불요 (17점 미만)' : '준설시행 (17점 이상)');	
}

/**
 * @author ipodo
 * @param {Event} e - keypress
 * @returns
 * @description 시험값  입력
 */
function fnChangeTestValue(e) {
	e.target.value = e.target.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	e.preventDefault();
	
	fnChangeTextAvg(e.target);
}

/**
 * @author ipodo
 * @param {Element} e - element
 * @returns
 * @description 시험 평균값  입력
 */
function fnChangeTextAvg(e) {
	
	var idx = $(e).parent().parent().index(); // 현재 tr 인덱스 확인
	var size = [];
	var total = 0; // 전체값
	
	if(idx < 4) size = [0,3]; //첫번째 로우
	else if(idx > 4 && idx < 9) size = [5,8]; //두번째 로우
	else if(idx > 9 && idx < 14) size = [10,13]; //세번째 로우
	else size = [15,18]; //마지막 로우
	
	// 시험값 합산
	for(var i=size[0]; i<=size[1]; i++) {
		$(e).parents('tbody').find('tr:eq('+i+') td').children('input').toArray().forEach(function(j) {
			if($(j).attr('name') == undefined) {
				total += Number(j.value);	
			}
		});
	}
	var avg = (total / 20); //평균값
	
	$(e).parents('tbody').find('tr:eq('+(size[1]+1)+') td:eq(0)').children().val(avg.toFixed(1));
}

/**
 * @author ipodo
 * @param
 * @returns
 * @description 압축강도 및 균열깊이 테스트표 
 */
function fnSetChckTableVal() {
	
	var arr = [];
	
	/* 압축강도 및 균열깊이 */
	$('.testTbl tbody').each(function (i, el) {
		var type = $(this).parent().attr("name");
		var list = $(this).find('input:not([name^="'+type+'"])');
		var idx = 0;
		var str = ''; 
		var photoName = type == 'cmprsstrn' ? '압축강도' : '균열깊이';
		
		list.each(function (i,f) {
			
			var numf = idx < 9 ? '0'.concat(idx+1) : ''.concat(idx+1);
			
			// 1번 시험항목들
			if(i < 20) {
				
				str = (i == 0) ? str.concat('{') : str.concat(','); 
				str = str.concat('"시험값',numf,'":"',$(f).val(),'"');
				
				idx++;
				if(i == 19) { 
					
					var imgLen = $(f).parents('tbody').find('div:eq(0)').children('img').length;
					
					if(imgLen > 0) {
						var url = $(f).parents('tbody').find('div:eq(0)').children('img').attr('src');
						str = url.indexOf('noimage.png') < 0 ? str.concat(',"',photoName,'_콘크리트1_시험위치_사진"',':"', url.replace('/storage/fieldBook', 'gimg://'),'"') : str.concat(',"',photoName,'_콘크리트1_시험위치_사진"',':""'); 
						
					} else { str = str.concat(',"',photoName,'_콘크리트1_시험위치_사진"',':""') }
					
					//str = str.concat($('input[name='+type+'cncrt11ChckOthVal]').val());
					
					str = str.concat('}');
					$('input[name="'+type+'cncrt1"]').val(str);
					
					arr.push(str);
					
					str = '{'
					idx = 0;
				}
			}
			if(i >=20 && i < 40) {
				str = str.concat(i != 20 ? ',' : '');
				str = str.concat('"시험값',numf,'":"',$(f).val(),'"');
				
				idx++;
				if(i == 39) {
					
					var imgLen = $(f).parents('tbody').find('div:eq(1)').children('img').length;
					
					if(imgLen > 0) {
						var url = $(f).parents('tbody').find('div:eq(1)').children('img').attr('src');
						str = url.indexOf('noimage.png') < 0 ? str.concat(',"',photoName,'_콘크리트2_시험위치_사진"',':"', url.replace('/storage/fieldBook', 'gimg://'),'"') : str.concat(',"',photoName,'_콘크리트2_시험위치_사진"',':""');
						
					} else { str = str.concat(',"',photoName,'_콘크리트2_시험위치_사진"',':""') }
					 
					str = str.concat('}');
					$('input[name="'+type+'cncrt2"]').val(str);
					
					arr.push(str);
					
					str = '{';
					idx = 0;
				}
			}				
			if(i >=40 && i < 60) {
				str = str.concat(i != 40 ? ',' : '');
				str = str.concat('"시험값',numf,'":"',$(f).val(),'"');
				
				idx++;
				if(i == 59) { 
					
					var imgLen = $(f).parents('tbody').find('div:eq(2)').children('img').length;
					
					if(imgLen > 0) {
						var url = $(f).parents('tbody').find('div:eq(2)').children('img').attr('src');
						str = url.indexOf('noimage.png') < 0 ? str.concat(',"',photoName,'_콘크리트3_시험위치_사진"',':"', url.replace('/storage/fieldBook', 'gimg://'),'"') : str.concat(',"',photoName,'_콘크리트3_시험위치_사진"',':""');
												
					} else { str = str.concat(',"',photoName,'_콘크리트3_시험위치_사진"',':""') }
										
					str = str.concat('}');
					$('input[name="'+type+'cncrt3"]').val(str);
					
					arr.push(str);
					
					str = '{';
					idx = 0;
				}
			}
			if(i >=60 && i < 80) {
				str = str.concat(i != 60 ? ',' : '');
				str = str.concat('"시험값',numf,'":"',$(f).val(),'"');
				
				idx++;
				if(i == 79) { 
					
					var imgLen = $(f).parents('tbody').find('div:eq(3)').children('img').length;
					
					if(imgLen > 0) {
						var url = $(f).parents('tbody').find('div:eq(3)').children('img').attr('src');
						str = url.indexOf('noimage.png') < 0 ? str.concat(',"',photoName,'_콘크리트4_시험위치_사진"',':"', url.replace('/storage/fieldBook', 'gimg://'),'"') : str.concat(',"',photoName,'_콘크리트4_시험위치_사진"',':""');
						
					} else { str = str.concat(',"',photoName,'_콘크리트4_시험위치_사진"',':""') }
										
					str = str.concat('}');
					$('input[name="'+type+'cncrt4"]').val(str);
					
					arr.push(str);
					
					str = '{';
					idx = 0;
				}
			}
		});
	});

	var len = 4;
	
	for(var i=0; i < len; i++) {
		$('input[name=cmprsstrncncrt'+(i+1)+']').val(arr[i]);
		$('input[name=crkdptcncrt'+(i+1)+']').val(arr[i+4]);
		
		// 변경 json
		var cmprsstrncncrt = JSON.parse($('input[name=cmprsstrncncrt'+(i+1)+']').val());
		var crkdptcncrt = JSON.parse($('input[name=crkdptcncrt'+(i+1)+']').val());
		
		// 원본 json 
		var origin_cmprsstrncncrt = JSON.parse($('input[name=origin_cmprsstrncncrt'+(i+1)+']').val());
		var origin_crkdptcncrt = JSON.parse($('input[name=origin_crkdptcncrt'+(i+1)+']').val());
		
		//원본 <-- 변경값 
		$.extend(origin_cmprsstrncncrt,cmprsstrncncrt);
		$.extend(origin_crkdptcncrt,crkdptcncrt);
		
		$('input[name=cmprsstrncncrt'+(i+1)+']').val(JSON.stringify(origin_cmprsstrncncrt));
		$('input[name=crkdptcncrt'+(i+1)+']').val(JSON.stringify(origin_crkdptcncrt));
	}		
}

/**
 * @author ipodo
 * @param
 * @returns
 * @description 수정페이지 파일 업로드 팝업
 */
function fnFileUploadView(gid) {
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("/sys/fck/pcs/sct/insertFileUploadPopup.do?gid="+gid);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 320,
		width: 500,
		title: "정밀점검 파일등록"
	});
}

/**
 * @author ipodo
 * @param
 * @returns
 * @description 수정페이지 파일 업로드
 */
function fnFileUpload(gid) {
	
	var formData = new FormData($("#insertForm")[0]);
	
	formData.append("gid", gid);
	
	if(fileList.length > 0){
		fileList.forEach(function(f){
			formData.append("fileList", f);
		});
	}         
	
	var returnStatus = false;
	
	if(fileList == null || fileList == undefined || fileList.length == 0){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
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
	        data: formData,
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
	        	if(data.status == "200"){
	        		alert("업로드를 완료하였습니다.");
	        		window.location.reload();
	        	}else{
	        		alert("업로드를 실패하였습니다. 관리자에게 문의하세요.");
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
 * @author ipodo
 * @param
 * @returns
 * @description 파일 다운로드
 */
function fnFileDownload(gid, sn) {
    window.location.href = "/sys/fck/pcs/sct/selectFileDownload.do?gid="+gid+"&sn="+sn;
}

/**
 * @author ipodo
 * @param
 * @returns
 * @description 파일 삭제
 */
function fnFileDelete(sn) {
	if(confirm("해당 파일을 삭제하시겠습니까?")) {
		$.ajax({
			type: "POST",
	        url: "/sys/fck/pcs/sct/deleteFileDownload.do",
			data : {"sn":sn},
			dataType: "json",
			success: function(data) {
	        	if (data.message == "success") {
	                alert("파일이 삭제되었습니다.");
	                window.location.reload();
	            } else {
	            	alert("에러가 발생했습니다.");
	            }
		    },error: function(data) {
		    	alert(data.message);
		    }
			
		});
	}
}
/**
 * 조사일자 월 선택 초기화
 */
function fncSvyYearChange(){
	$("#svyMonthView").val("").prop("selected","true");
}