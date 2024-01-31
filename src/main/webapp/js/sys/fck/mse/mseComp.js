var $app = {};

$(document).ready(function() {

	if($("input[name=kndEtc]").val() != '' && ($("select[name=knd]").val() == '' || $("select[name=knd]").val() == 'kndEtc')){
		$("select[name=knd]").val('kndEtc');
		$("input[name=kndEtc]").attr("disabled",false);
		$("input[name=kndEtc]").css({'display':'inline-block','width':'45%'});
		$("select[name=knd]").css({'width':'50%'});
	}
	
	if($("input[name=apiKey]").length > 0){
	    var apiKey = $("input[name=apiKey]").val();
	    $('.ol-basemap-control').css('right', '60px');	    
	    $.getScript("https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId="+apiKey+"&submodules=geocoder",function(){
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

	// input enter event
	$('#svyId,#svyUser,#mstNm').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
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
				const photoArr = $("input[name=photoList]").val().slice(1, -1).split(', ');
				photoArr.forEach((val, idx) => {
					$("#photoDiv").append("<img id=photoSrc"+(idx+1)+" src=\"/storage/fieldBook".concat(val,"\">"));
				});
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
 * 현장사진 추가 
 * @returns
 */
function fncMseSvyAddPhoto(){
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
		         <input style="width:70%; float:left; margin-left:10px;" type="text" name="photoTag`+imgCnt+`"/> 
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
 * 조사완료지 상세화면
 * @param id
 * @returns
 */
function fncMseComptUpdt(sldid,svystep) {
	$("input[name=sldid]").val(sldid);
	$("input[name=svystep]").val(svystep);
	var updtType = $("input[name=updt]").val();
	if(updtType == "detail"){
		$("#listForm").attr("action","/sys/fck/mse/sct/selectFckMseComptDetail.do");	
		
	}else {
		$("#listForm").attr("action","/sys/fck/mse/sct/updateMseComptView.do");	
	}
	$("#listForm").submit();
}

/**
 * 조사완료지 삭제
 * @param id
 * @returns
 */

function fncDeleteMseCompt(sldid,svystep){
	$("input[name=sldid]").val(sldid);
	$("input[name=svystep]").val(svystep);
	if(confirm("삭제하시겠습니까?")) {
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/fck/mse/sct/deleteMseCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    fnList();
                    //location.href="/sys/fck/mse/sct/selectMseComptList.do";
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
 * 조사완료지 수정 (일반 사용자)
 * @returns
 */
function fncSvyComptUpdate(){
	if(confirm("수정하시겠습니까?")) {
		
		//와이어신축계
		var wireextArr = "["
		$("tr.tw").each(function(i){
			//채널명
			var tw_chl = $("tr.tw:eq("+i+") input[name=tw_chl]").val();
			
			//외관점검
			//센서부
			var tw_sensor = $("tr.tw:eq("+i+") select[name=tw_sensor]").val();
			//와이어
			var tw_wire = $("tr.tw:eq("+i+") select[name=tw_wire]").val();
			//케이블
			var tw_cable = $("tr.tw:eq("+i+") select[name=tw_cable]").val();
			//보호함체
			var tw_prtcbox = $("tr.tw:eq("+i+") select[name=tw_prtcbox]").val();
			//전원
			var tw_power = $("tr.tw:eq("+i+") select[name=tw_power]").val();
			
			//성능점검
			//점검결과
			var twPer_chkresult = $("tr.twPer:eq("+i+") select[name=twPer_chkresult]").val();
			//불량 상태
			var twPer_badsttus = $("tr.twPer:eq("+i+") input[name=twPer_badsttus]").val();
			//현장 센서값
			var twPer_sptsensor = $("tr.twPer:eq("+i+") input[name=twPer_sptsensor]").val();
			//시스템 센서값
			var twPer_syssensor = $("tr.twPer:eq("+i+") input[name=twPer_syssensor]").val();
			
			wireextArr = wireextArr.concat('{"외관점검":{"센서부":"'+tw_sensor+'","와이어":"'+tw_wire+'","케이블":"'+tw_cable+'","보호함체":"'+tw_prtcbox+'","전원":"'+tw_power+'"},"채널명":"'+tw_chl+'","성능점검":{"점검결과":"'+twPer_chkresult+'","불량상태":"'+twPer_badsttus+'","현장센서값":"'+twPer_sptsensor+'","시스템센서값":"'+twPer_syssensor+'"}},');
		});
		
		//지중경사계
		var licmeterArr = '[';
		var licmeterSubArr = '[';
		$("tr.inc").each(function(i){
			licmeterSubArr = '[';
			//채널명
			var inc_chl = $("tr.inc:eq("+i+") input[name=inc_chl]").val();
			
			//외관점검
			//센서부
			var inc_sensor = $("tr.inc:eq("+i+") select[name=inc_sensor]").val();
			//케이블
			var inc_cable = $("tr.inc:eq("+i+") select[name=inc_cable]").val();
			//보호함체
			var inc_prtcbox = $("tr.inc:eq("+i+") select[name=inc_prtcbox]").val();
			//전원
			var inc_power = $("tr.inc:eq("+i+") select[name=inc_power]").val();
			
			//성능점검
//			if(inc_chl.length > 0){
				$("tr.incPer[class*="+inc_chl+"]").each(function(j){
					//채널명
					var incPer_chl = $("tr.incPer:eq("+j+") input[name=incPer_chl]").val();
					//점검결과
					var incPer_chkresult = $("tr.incPer:eq("+j+") select[name=incPer_chkresult]").val();
					//불량상태
					var incPer_badsttus = $("tr.incPer:eq("+j+") input[name=incPer_badsttus]").val();
					//현장 센서값
					var incPer_sptsensor = $("tr.incPer:eq("+j+") input[name=incPer_sptsensor]").val();
					//시스템 센서값
					var incPer_syssensor = $("tr.incPer:eq("+j+") input[name=incPer_syssensor]").val();
					
					licmeterSubArr = licmeterSubArr.concat('{"채널명":"'+incPer_chl+'","점검결과":"'+incPer_chkresult+'","불량상태":"'+incPer_badsttus+'","현장 센서값":"'+incPer_sptsensor+'","시스템 센서값":"'+incPer_syssensor+'"},');
				});
				licmeterSubArr = licmeterSubArr.slice(0,-1).concat(']');
//			}
			licmeterArr = licmeterArr.concat('{"외관점검":{"센서부":"'+inc_sensor+'","케이블":"'+inc_cable+'","보호함체":"'+inc_prtcbox+'","전원":"'+inc_power+'"},"채널명":"'+inc_chl+'","성능점검":'+licmeterSubArr+'},');
			
		});
		
		//지하수위계
		var gwgaugeArr = '[';
		$("tr.wl").each(function(i){
			//채널명
			var wl_chl = $("tr.wl:eq("+i+") input[name=wl_chl]").val();
			//외관점검
			//센서부
			var wl_sensor = $("tr.wl:eq("+i+") select[name=wl_sensor]").val();
			//케이블
			var wl_cable = $("tr.wl:eq("+i+") select[name=wl_cable]").val();
			//보호함체
			var wl_prtcbox = $("tr.wl:eq("+i+") select[name=wl_prtcbox]").val();
			//전원
			var wl_power = $("tr.wl:eq("+i+") select[name=wl_power]").val();
			//자물쇠
			var wl_lock = $("tr.wl:eq("+i+") select[name=wl_lock]").val();
			
			//성능점검
			//점검결과
			var wlPer_chkresult = $("tr.wlPer:eq("+i+") select[name=wlPer_chkresult]").val();
			//불량상태
			var wlPer_badsttus = $("tr.wlPer:eq("+i+") input[name=wlPer_badsttus]").val();
			//현장수위값_노드
			var wlPer_sptwalNode = $("tr.wlPer:eq("+i+") input[name=wlPer_sptwalNode]").val();
			//현장수위값_수동
			var wlPer_sptwalPass = $("tr.wlPer:eq("+i+") input[name=wlPer_sptwalPass]").val();
			//시스템센서값_RAW
			var wlPer_syssnsRaw = $("tr.wlPer:eq("+i+") input[name=wlPer_syssnsRaw]").val();
			//시스템센서값_수위
			var wlPer_syssnsWal = $("tr.wlPer:eq("+i+") input[name=wlPer_syssnsWal]").val();
			
			gwgaugeArr = gwgaugeArr.concat('{"외관점검":{"센서부":"'+wl_sensor+'","케이블":"'+wl_cable+'","보호함체":"'+wl_prtcbox+'","전원":"'+wl_power+'","자물쇠":"'+wl_lock+'"},"채널명":"'+wl_chl+'","성능점검":{"점검결과":"'+wlPer_chkresult+'","불량상태":"'+wlPer_badsttus+'","현장수위값_노드":"'+wlPer_sptwalNode+'","현장수위값_수동":"'+wlPer_sptwalPass+'","시스템센서값_RAW":"'+wlPer_syssnsRaw+'","시스템센서값_수위":"'+wlPer_syssnsWal+'"}},');
		});
		
		//강우계
		var raingaugeArr = '[';
		$("tr.rg").each(function(i){
			//채널명
			var rg_chl = $("tr.rg:eq("+i+") input[name=rg_chl]").val();
			
			//외관점검
			//센서부
			var rg_sensor = $("tr.rg:eq("+i+") select[name=rg_sensor]").val();
			//케이블
			var rg_cable = $("tr.rg:eq("+i+") select[name=rg_cable]").val();
			//전원
			var rg_power = $("tr.rg:eq("+i+") select[name=rg_power]").val();
			
			//성능점검
			//점검결과
			var rgPer_chkresult = $("tr.rgPer:eq("+i+") select[name=rgPer_chkresult]").val();
			//불량상태
			var rgPer_bacsttus = $("tr.rgPer:eq("+i+") input[name=rgPer_bacsttus]").val();
			
			raingaugeArr = raingaugeArr.concat('{"외관점검":{"센서부":"'+rg_sensor+'","케이블":"'+rg_cable+'","전원":"'+rg_power+'"},"채널명":"'+rg_chl+'","성능점검":{"점검결과":"'+rgPer_chkresult+'","불량상태":"'+rgPer_bacsttus+'"}},');
		});
		
		//구조물변위계
		var strcdpmArr = '[';
		$("tr.tm").each(function(i){
			//채널명
			var tm_chl = $("tr.tm:eq("+i+") input[name=tm_chl]").val();
			
			//외관점검
			//센서부
			var tm_sensor = $("tr.tm:eq("+i+") select[name=tm_sensor]").val();
			//케이블
			var tm_cable = $("tr.tm:eq("+i+") select[name=tm_cable]").val();
			//보호함체
			var tm_prtcbox = $("tr.tm:eq("+i+") select[name=tm_prtcbox]").val();
			//전원
			var tm_power = $("tr.tm:eq("+i+") select[name=tm_power]").val();
			//자물쇠
			var tm_lock = $("tr.tm:eq("+i+") select[name=tm_lock]").val();
			
			//성능점검
			//점검결과
			var tmPer_chkresult = $("tr.tmPer:eq("+i+") select[name=tmPer_chkresult]").val();
			//불량상태
			var tmPer_badsttus = $("tr.tmPer:eq("+i+") input[name=tmPer_badsttus]").val();
			//현장 센서값
			var tmPer_sptsensor = $("tr.tmPer:eq("+i+") input[name=tmPer_sptsensor]").val();
			//시스템 센서값
			var tmPer_syssensor = $("tr.tmPer:eq("+i+") input[name=tmPer_syssensor]").val();
			
			strcdpmArr = strcdpmArr.concat('{"외관점검":{"센서부":"'+tm_sensor+'","케이블":"'+tm_cable+'","보호함체":"'+tm_prtcbox+'","전원":"'+tm_power+'","자물쇠":"'+tm_lock+'"},"채널명":"'+tm_chl+'","성능점검":{"점검결과":"'+tmPer_chkresult+'","불량상태":"'+tmPer_badsttus+'","현장 센서값":"'+tmPer_sptsensor+'","시스템 센서값":"'+tmPer_syssensor+'"}},');
		});
		
		//지표변위계
		var surdpmArr = '[';
		$("tr.sd").each(function(i){
			//채널명
			var sd_chl = $("tr.sd:eq("+i+") input[name=sd_chl]").val();
			
			//외관점검
			//센서부
			var sd_sensor = $("tr.sd:eq("+i+") select[name=sd_sensor]").val();
			//케이블
			var sd_cable = $("tr.sd:eq("+i+") select[name=sd_cable]").val();
			//전원
			var sd_power = $("tr.sd:eq("+i+") select[name=sd_power]").val();
			
			//성능점검
			//점검결과
			var sdPer_chkresult = $("tr.sdPer:eq("+i+") select[name=sdPer_chkresult]").val();
			//불량상태
			var sdPer_badsttus = $("tr.sdPer:eq("+i+") input[name=sdPer_badsttus]").val();
			//현장센서값_X
			var sdPer_sptsensorX = $("tr.sdPer:eq("+i+") input[name=sdPer_sptsensorX]").val();
			//현장센서값_Y
			var sdPer_sptsensorY = $("tr.sdPer:eq("+i+") input[name=sdPer_sptsensorY]").val();
			//현장센서값_가속도
			var sdPer_sptsensorAC = $("tr.sdPer:eq("+i+") input[name=sdPer_sptsensorAC]").val();
			//시스템센서값_X
			var sdPer_syssensorX = $("tr.sdPer:eq("+i+") input[name=sdPer_syssensorX]").val();
			//시스템센서값_Y
			var sdPer_syssensorY = $("tr.sdPer:eq("+i+") input[name=sdPer_syssensorY]").val();
			//시스템센서값_가속도
			var sdPer_syssensorAC = $("tr.sdPer:eq("+i+") input[name=sdPer_syssensorAC]").val();
			
			surdpmArr = surdpmArr.concat('{"외관점검":{"센서부":"'+sd_sensor+'","케이블":"'+sd_cable+'","전원":"'+sd_power+'"},"채널명":"'+sd_chl+'","성능점검":{"점검결과":"'+sdPer_chkresult+'","불량상태":"'+sdPer_badsttus+'","현장센서값_X":"'+sdPer_sptsensorX+'","현장센서값_Y":"'+sdPer_sptsensorY+'","현장센서값_가속도":"'+sdPer_sptsensorAC+'","시스템센서값_X":"'+sdPer_syssensorX+'","시스템센서값_Y":"'+sdPer_syssensorY+'","시스템센서값_가속도":"'+sdPer_syssensorAC+'"}},');
		});
		
		//GPS변위계
		var gpsgaugeArr = '[';
		$("tr.gps").each(function(i){
			//채널명
			var gps_chl = $("tr.gps:eq("+i+") input[name=gps_chl]").val();
			
			//외관점검
			//센서부
			var gps_sensor = $("tr.gps:eq("+i+") select[name=gps_sensor]").val();
			//케이블
			var gps_cable = $("tr.gps:eq("+i+") select[name=gps_cable]").val();
			//전원
			var gps_power = $("tr.gps:eq("+i+") select[name=gps_power]").val();
			
			//성능점검			
			//점검결과
			var gpsPer_chkresult = $("tr.gpsPer:eq("+i+") select[name=gpsPer_chkresult]").val();
			//불량상태
			var gpsPer_badsttus = $("tr.gpsPer:eq("+i+") input[name=gpsPer_badsttus]").val();
			
			gpsgaugeArr = gpsgaugeArr.concat('{"외관점검":{"센서부":"'+gps_sensor+'","케이블":"'+gps_cable+'","전원":"'+gps_power+'"},"채널명":"'+gps_chl+'","성능점검":{"점검결과":"'+gpsPer_chkresult+'","불량상태":"'+gpsPer_badsttus+'"}},');
		});
		
		//게이트웨이
		var gatewayArr = '[';
		$("tr.gateway").each(function(i){
			//연번
			var gateway_chl = $("tr.gateway:eq("+i+") input[name=gateway_chl]").val();
			
			//외관점검
			//태양광
			var gateway_solar = $("tr.gateway:eq("+i+") select[name=gateway_solar]").val();
			//함체
			var gateway_box = $("tr.gateway:eq("+i+") select[name=gateway_box]").val();
			//보호휀스
			var gateway_prtcfence = $("tr.gateway:eq("+i+") select[name=gateway_prtcfence]").val();
			//지선
			var gateway_brnchln = $("tr.gateway:eq("+i+") select[name=gateway_brnchln]").val();
			//안내판
			var gateway_board = $("tr.gateway:eq("+i+") select[name=gateway_board]").val();
			//자물쇠
			var gateway_lock = $("tr.gateway:eq("+i+") select[name=gateway_lock]").val();
			
			//성능점검
			//측정전압
			var gateway_msvolt = $("tr.gateway:eq("+i+") input[name=gateway_msvolt]").val();
			//불량상태
			var gateway_badsttus = $("tr.gateway:eq("+i+") input[name=gateway_badsttus]").val();
			
			gatewayArr = gatewayArr.concat('{"연번":"'+gateway_chl+'","외관점검":{"태양광":"'+gateway_solar+'","함체":"'+gateway_box+'","보호휀스":"'+gateway_prtcfence+'","지선":"'+gateway_brnchln+'","안내판":"'+gateway_board+'","자물쇠":"'+gateway_lock+'"},"성능점검":{"측정전압":"'+gateway_msvolt+'","불량상태":"'+gateway_badsttus+'"}},');
		});
		
		//노드
		var nodeArr = '[';
		$("tr.node").each(function(i){
		    //연번
		    var node_chl = $("tr.node:eq("+i+") input[name=node_chl]").val();
		    
		    //외관점검
		    //태양광
		    var node_solar = $("tr.node:eq("+i+") select[name=node_solar]").val();
		    //함체
		    var node_box = $("tr.node:eq("+i+") select[name=node_box]").val();
		    //보호휀스
		    var node_prtcfence = $("tr.node:eq("+i+") select[name=node_prtcfence]").val();
		    //지선
		    var node_brnchln = $("tr.node:eq("+i+") select[name=node_brnchln]").val();
		    //안내판
		    var node_board = $("tr.node:eq("+i+") select[name=node_board]").val();
		    //자물쇠
		    var node_lock = $("tr.node:eq("+i+") select[name=node_lock]").val();
		    
		    //성능점검
		    //측정전압
		    var node_msvolt = $("tr.node:eq("+i+") input[name=node_ms]").val();
		    //불량상태
		    var node_badsttus = $("tr.node:eq("+i+") input[name=node_badsttus]").val();
		    
		    nodeArr = nodeArr.concat('{"연번":"'+node_chl+'","외관점검":{"태양광":"'+node_solar+'","함체":"'+node_box+'","보호휀스":"'+node_prtcfence+'","지선":"'+node_brnchln+'","안내판":"'+node_board+'","자물쇠":"'+node_lock+'"},"성능점검":{"측정":"'+node_msvolt+'","불량상태":"'+node_badsttus+'"}},');
		});
		
		wireextArr = wireextArr.slice(0,-1).concat(']');
		licmeterArr = licmeterArr.slice(0,-1).concat(']');
		gwgaugeArr = gwgaugeArr.slice(0,-1).concat(']');
		raingaugeArr = raingaugeArr.slice(0,-1).concat(']');
		strcdpmArr = strcdpmArr.slice(0,-1).concat(']');
		surdpmArr = surdpmArr.slice(0,-1).concat(']');
		gpsgaugeArr = gpsgaugeArr.slice(0,-1).concat(']');
		gatewayArr = gatewayArr.slice(0,-1).concat(']');
		nodeArr = nodeArr.slice(0,-1).concat(']');
		
		if(wireextArr.length > 1) $('input[name=wireext]').val(wireextArr);
		if(licmeterArr.length > 1) $('input[name=licmeter]').val(licmeterArr);
		if(gwgaugeArr.length > 1) $('input[name=gwgauge]').val(gwgaugeArr);
		if(raingaugeArr.length > 1) $('input[name=raingauge]').val(raingaugeArr);
		if(strcdpmArr.length > 1) $('input[name=strcdpm]').val(strcdpmArr);
		if(surdpmArr.length > 1) $('input[name=surdpm]').val(surdpmArr);
		if(gpsgaugeArr.length > 1) $('input[name=gpsgauge]').val(gpsgaugeArr);
		if(gatewayArr.length > 1) $('input[name=gateway]').val(gatewayArr);
		if(nodeArr.length > 1) $('input[name=node]').val(nodeArr);

		if($("input[name=photoList]").val() != null && $("input[name=photoList]").val() != ""){
			var returnChk = false;
			var photoTagArr = '[';
		 	$(".photoBox").each(function(i){
				var photoTag = $('input[name=photoTag'+(i+1)+']').val();
				var photoSrc = $('input[name*=photoSrc]:eq('+i+')').val();
				if(photoTag.length == 0){
					returnChk = true;
					alert('현장사진 태그명이 입력되지 않았습니다.');
					return false;
				}
				photoTagArr = photoTagArr.concat('{"TAG":"'+photoTag+'","FILENAME":"'+photoSrc+'"},');
	   		});
			if(returnChk) return false;
			photoTagArr = photoTagArr.slice(0,-1).concat(']');
			$('input[name=photoTagList]').val(photoTagArr);
		}
		
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: $("#listForm")[0].action,
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    fnList();
                    //location.href="/sys/fck/mse/sct/selectMseComptList.do";
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
 * 행정구역 시군구 명칭조회 ajax
 * @returns
 */
function fncAdministCtprvnNmChange(){
	var sdNm = $("#svySdView").val();
	
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
        			$("#svySggView").empty().append("<option value>전체</option>");
        			$("#svyEmdView").empty().append("<option value>전체</option>");
        			$("#svyRiView").empty().append("<option value>전체</option>");
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
 * 행정구역 읍면동 명칭조회 ajax
 * @returns
 */
function fncAdministSignguNmChange(){
	var sdNm = $("#svySdView").val();
	var sggNm = $("#svySggView").val();
	
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
        			$("#svyEmdView").empty().append("<option value>전체</option>");
        			$("#svyRiView").empty().append("<option value>전체</option>");
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

/**
 * 행정구역 리 명칭조회 ajax
 * @returns
 */
function fncAdministEmdNmChange(){
	var sdNm = $("#svySdView").val();
	var sggNm = $("#svySggView").val();
	var emdNm = $("#svyEmdView").val();
	
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
        			$("#svyRiView").empty().append("<option value>전체</option>");
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
	var url = "/sys/fck/mse/sct/selectDownloadSldListExcel.do";
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
	if(svyType == '계류보전 외관점검' || svyType == '사방댐 외관점검'){
		formdata.append("photoType",photoType);
	}else{
		formdata.append("photoType","산지사방");
	}
	xhr.open('POST', '/sys/fck/mse/sct/selectDownloadPhotoListZip.do');
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
function fnPhotoDown(sldid,svystep){
	$("input[name=sldid]").val(sldid);
	$("input[name=svystep]").val(svystep);
	
	var url = "/sys/fck/mse/sct/selectMseDownloadSldListPhoto.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	
	form.append($('<input/>', {type: 'hidden', name: "sldid", value:sldid }));
	form.append($('<input/>', {type: 'hidden', name: "svystep", value:svystep }));
	
	form.appendTo("body").submit().remove();
	
}

/*
 * 조사완료지 엑셀 다운로드
 */
function fnExcelDown(sldid,svystep){

//	var svyType = $("input[name=svyType]").val();
	
//	if(svyType.length == 0){
//		alert("조사유형를 반드시 선택해야 엑셀파일을 생성할 수 있습니다.\n목록조회 후 다시 시도해주세요.");
//		return;
//	}
	$("input[name=sldid]").val(sldid);
	$("input[name=svystep]").val(svystep);	
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
 * 조사완료 재업로드 팝업
 * @returns
 */
function fnUpdateFckMseComptPopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updateFckMseComptPopup.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 370,
		width: 400,
		title: "외관점검 완료지 갱신"
	});
	
}

/**
 * 조사완료 재업로드
 * @returns
 */
function fnUpdateFckMseCompt(){
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
			//url:"/sys/fck/mse/sld/uploadStripLandExcel.do",
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

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/fck/mse/sct/selectMseComptList.do").attr("method","post");
	
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
	
	
	$("#listForm").attr("action","/sys/fck/mse/sct/selectMseComptList.do");
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