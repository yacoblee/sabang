$(document).ready(function(){
	//주제도 목록 생성
	//$("div.juje-check")
	//<div><input type="checkbox" id="juje1" value="tf_feis_ctprvn"><label for="juje1">시도행정구역</label></div>
	$.each(CONFIG.LAYERS.SUPERMAP,function(idx,elm){
		var id = elm.get("id");
		var title = elm.get("title");
		
		var $div = $("<div></div>");
		var $input = $("<input></input>");
		var $label = $("<label></label>");
		
		$input.attr("type","checkbox");
		$input.attr("id",id.concat("chk"));
		$input.val(id);
		
		$label.attr("for",id.concat("chk"));
		$label.text(title);
		
		$div.append($input).append($label);
		
		$("div.juje-check").append($div);
	});
	
	//배경지도 선택
	$(".btn-map-selector").on("click",function(){
		if($(this).hasClass("selected")){
			return false;
		}
		
		$(".btn-map-selector").removeClass("selected");
		$(this).addClass("selected");
		
		var current_position = $(".hybrid-check").offset().left; 
		var position = $(this).offset().left;
		var dffrnc = current_position - position;
		
		if($(this).val() == "base"){
			SM.getLayer(SM.map.getLayerGroup(),"satellite").setVisible(false);
			SM.getLayer(SM.map.getLayerGroup(),"hybrid").setVisible(false);
			$(".hybrid-check").hide();
		}else if($(this).val() == "satellite"){
			SM.getLayer(SM.map.getLayerGroup(),"base").setVisible(false);
			$("#hybrid").prop("checked",true).change();
			$(".hybrid-check").css("left", position + "px");
			$(".hybrid-check").show();
		}
		
		SM.getLayer(SM.map.getLayerGroup(),$(this).val()).setVisible(true);
	});
	
	//주제도 선택
	$(".btn-layer-selector.juje").on("click",function(){
		var selected = $(this).hasClass("selected");
		if(selected){
			$(".juje-check").removeClass("on");
			$(this).removeClass("selected");
		}else{
			$(".juje-check").addClass("on");
			$(this).addClass("selected");
		}
	});
	
	//주제도목록 체크 이벤트
	$(".juje-check input[type=checkbox]").on("change",function(){
		var  layerId = $(this).val();
		var checked = $(this).is(":checked");
		
		layerId = layerId.replace("chk","");
		if(checked){
			SM.addLayer(layerId);
			
			if(layerId.indexOf('bsc') == 0) {
				// 팝업 엘리먼트 추가
//				SM.createOverlay('popup-'+layerId, null, null, null);

				if(!SM.map.getListeners('singleclick') && SM.eventListener.singleclick.length == 0) {
					// 싱글클릭 이벤트 등록
					SM.onSingleClick(fnSingleclickMap);
				}
				else if(SM.eventListener.singleclick.length > 0) {
					SM.reSingleClick();
				}
			}
		}else{
			SM.removeLayer(layerId);
			
			if(layerId.indexOf('bsc') == 0) {
				var popup = SM.map.getOverlayById('popup-' + layerId);
				
				if(popup) SM.removeOverlay('popup-' + layerId);

				var target = SM.map.getLayers().getArray().filter(function (d) {   ;
				    return d.get("id") == "bscSvycompt" || d.get("id") == "bscStripland"
				});
				
				if(target.length == 0) SM.unSingleClick();
			}
		}
//		SM.getLayer(SM.map.getLayerGroup(),layerId).setVisible(checked);
	});
	
	//지적도 선택
	$(".btn-layer-selector.jijuk").on("click",function(){
		var selected = $(this).hasClass("selected");
		
		if(selected){
			$(this).removeClass("selected");
		}else{
			$(this).addClass("selected");
			if(SM.map.getView().getZoom() < 16){
				if(confirm("현재 지도 레벨에서는 지적도가 출력되지 않습니다.\n지적도 확인을 위하여 지도를 확대하시겠습니까?")){
					SM.map.getView().setZoom(17);
				}
			}
		}
		SM.getLayer(SM.map.getLayerGroup(),"jijuk").setVisible(!selected);
	});
	
	$("#hybrid").on("change",function(){
		var checked = false;
		if($(this).is(":checked")){
			checked = true;
		}
		SM.getLayer(SM.map.getLayerGroup(),"hybrid").setVisible(checked);
	});
	
	//사이드바 선택
	$(".nav-sidebar > ul > li").not(".home").on("click",function(){
		$(".nav-sidebar > ul > li").removeClass("on").removeClass("hold");
		$(this).addClass("on");
		
		var idx = $(this).index();
		$(".quick-box > div").removeClass("on");
		$(".quick-box > div:nth-child("+idx+")").addClass("on");
		
		if(!$(".quick-box").hasClass("on")){
			//$(".quick-box").addClass("on");
			$(".quick-box .navClose").trigger("click");
		}
		
		/* 2023.08.18 추가 */
		$('.quick-box').css('width', idx > 2 ? '780px' : '380px');

		var name = $(this).attr('class').substr(0,3);
		//$('#'+name+'SchRsltLst').css('min-height','450px');

//		var minDate = '2020-01';
		
//		$('.SvyComptStartDt').attr('min', minDate);
//		$('.SvyComptEndDt').attr('min', minDate);
		
	});
	
	//사이드바 닫기
	$(".quick-box .navClose").on("click",function(){
		if($(this).hasClass("on")){
			$("div.nav-sidebar > ul > li").removeClass("hold");
			$("div.nav-sidebar > ul > li.on").addClass("hold").removeClass("on");
			$(".quick-box").removeClass("on");
			$(".ol-scale-line").addClass("on");
			$(".nav-sidebar").removeClass("on");
			$(this).removeClass("on");
			$(".map-div").removeClass("on");
			
			/* 2023.08.18 추가 */
			$('.quick-box').css('width', '380px');
			$('.quick-box').css('left', '-325px');
		}else{
			$("div.nav-sidebar > ul > li.hold").addClass("on").removeClass("hold");
			$(".quick-box").addClass("on");
			$(".ol-scale-line").removeClass("on");
			$(".nav-sidebar").addClass("on");
			$(this).addClass("on");
			$(".map-div").addClass("on");

			/* 2023.08.18 추가 */
			var idx = $('.nav-sidebar > ul > li.on').index();
			$('.quick-box').css('width', idx > 2 ? '780px' : '380px');
			$('.quick-box').css('left', '50px');
			
			
		}
		//사이드바 transition 0.3s에 맞춰 지도영역을 적용함..
		//그로인한 지도갱신 이벤트가 먼저 실행되어 지도업데이트가 적용이 안됨..
		setTimeout(function(){
			SM.map.updateSize();
		},300);
	});
	
	//--accordion
	$('.acc-container li.has-sub>a').on('click', function(){
		$(this).removeAttr('href');
		var element = $(this).parent('li');

		if (element.hasClass('open')) {
			element.removeClass('open');
			element.find('li').removeClass('open');
			element.find('ul').slideUp();
		}
		else {
			element.addClass('open');
			element.children('ul').slideDown();
			element.siblings('li').children('ul').slideUp();
			element.siblings('li').removeClass('open');
			element.siblings('li').find('li').removeClass('open');
			element.siblings('li').find('ul').slideUp();
		}
	});
	
	//지도제어툴-홈
	$(".map-tools .home").on("click",function(){
		SM.home();
	});
	
	//지도제어툴-거리재기
	$(".map-tools .measure").on("click",function(){
		$(".map-tools .area").removeClass("on");
		$(this).addClass("on");
		$(".map-tools .del").removeClass("hidden");
		//싱글클릭 이벤트 해제
		SM.unSingleClick();
		SM.changeMapControl(CONFIG.MAPCONTROLTYPE.DISTANCE);
	});
	//지도제어툴-면적재기
	$(".map-tools .area").on("click",function(){
		$(".map-tools .measure").removeClass("on");
		$(this).addClass("on");
		$(".map-tools .del").removeClass("hidden");
		//싱글클릭 이벤트 해제
		SM.unSingleClick();
		SM.changeMapControl(CONFIG.MAPCONTROLTYPE.AREA);
	});
	//지도제어툴-지도저장
	$(".map-tools .save").on("click",function(){
		fncExportMap();
	});
	//지도제어툴-지도인쇄
	$(".map-tools .print").on("click",function(){
		fncPrintMapDialog();
	});
	//지도제어툴-지도초기화
	$(".map-tools .clear").on("click",function(){
		SM.clear();
	});
	//지도제어툴-거리,면적재기 초기화
	$(".map-tools .del").on("click",function(){
		$(".map-tools .del").addClass("hidden");
		$(".map-tools .area").removeClass("on");
		$(".map-tools .measure").removeClass("on");
		SM.changeMapControl(CONFIG.MAPCONTROLTYPE.MOVE);
		//싱글클릭이벤트 재등록
		SM.reSingleClick();
	});
	
	$(".map_tab > a").on("click", function(e){
		e.preventDefault();
		var maptab = $(this).parent();
		maptab.find("a").removeClass("on");
		$(this).addClass("on");
		
		maptab.next().find(".tab_content").removeClass("on");
		
		var $id = $(this).attr("href");
		maptab.next().find($id).addClass("on");
	});
	
	$("#search01").on("keypress",function(e){
		if(e.keyCode == 13){
			$("div.topsearch > button").trigger("click");
		}
	});
	
	$("div.topsearch > button").on("click", function(){
		//통합검색어
		wholeSearchQuery = $("#search01").val();
		//통합검색어 유무
		if(wholeSearchQuery.length == 0){
			alert("검색어가 입력되지 않았습니다.");
			return false;
		}
		//검색결과 초기화
		$(".map_tab > a:eq(0)").trigger("click");
		$(".map_tabcontent .txtsearch > span.txtblue").text("0");
		$("#allSchCntLst tbody tr td span.txtblue").text("0");
		
		wholeSearch("place",1);
		wholeSearch("parcel",1);
		wholeSearch("road",1);
		 
//		$(".nav-sidebar > ul > li.search").trigger("click");
	});
	
	
	/*주제도 조회 관련 처리 S*/
	
	//주제도 서비스분류 체인지
	$('#select01').on("change", function () {
		var url = "/sys/gis/selectApiServiceList.do";
		var param = { clsfyNm: $(this).val() };
		fncCmnSearch(url, param, function (data) {
			if(data.length > 0) {
				$('#select02').empty();
				$.each(data, function (i, d) {
					var opt = $('<option></option>').val(d.uri).text(d.serviceNm);
					$('#select02').append(opt);	
				})
			}
		});
	});

	// 주제도 조회
	$('#btnThemaSearch').on("click", function () {
		var id = $('#select02').val();
		var name = $("#select02 option[value=" + id + "]").text();
		var layer = SM.map.getLayer("vworld:wms");
		var imgurl = "http://api.vworld.kr/req/image";
		var legend = imgurl.concat("?service=image&request=getlegendgraphic&format=png&layer=").concat(id).concat("&style=").concat(id).concat("&key=").concat(CONFIG.KEYS.vworld);
			
		if($(".legend").length > 0) $('.legend').css("display","none");
		$('.legend').css("display","block");
        $('.legend-area').html("<img src='"+legend+"' alt ='범례이미지'>");
		if(layer) {
			layer.getSource().updateParams({'LAYERS': id, 'STYLES': id});
			layer.getSource().refresh();
			layer.setVisible(true);
		}
		else {
			layer = new ol.layer.Tile({
				id:"vworld:wms", 
				title:name, 
				visible: true, 
				source: new ol.source.TileWMS({
					url: CONFIG.URLS.VWORLD.wms,
					params: {
						SERVICE: 'WMS',
						REQUEST: 'GetMap',
						VERSION: '1.3.0',
						LAYERS: id,
						STYLES: id,
						CRS: 'EPSG:5186',
						WIDTH: 256,
						HEIGHT: 256,
						KEY: CONFIG.KEYS.vworld
					},
					crossOrigin: "anonymous",
					tileLoadFunction:function(imageTile,src){
						var url = SM.changeBbox(src);
						imageTile.getImage().src = "/cmm/proxy.do?url=".concat(url);
					}
				})	
			});
			
			SM.map.addLayer(layer);
		}
	});
	
	// 주제도 끄기
	$('#btnThemaReset').on('click', function () {
		var layer = SM.map.getLayer("vworld:wms");
		
		if(layer) { layer.setVisible(false) };
		$('.legend').css("display","none");
	});
	/*주제도 조회 관련 처리 E*/
	
	/*상세검색  페이지 행정구역 조회 처리 S*/
	/**
	 * 행정구역 시군구 조회 ajax
	 * @returns
	 */
	$('.SvyComptSd').on('change', function (event) {
		
		var code = event.target.value;
		var name = event.target.name;
		
		if(code == null || code == undefined || code.length == 0){
			$("#"+name.substr(0,11).concat("Sgg")).empty().append("<option value>전체</option>");
			$("#"+name.substr(0,11).concat("Emd")).empty().append("<option value>전체</option>");
			$("#"+name.substr(0,11).concat("Ri")).empty().append("<option value>전체</option>");
			return false;
		}
		
		var url = "/cmm/selectAdministZoneSigngu.do";
		var param = {sdCode: code};
		
		fncCmnSearch(url, param, function (list) {
			if(list.result){
	        	if(list.result.length > 0){
	        		$("#"+name.substr(0,11).concat("Sgg")).empty().append("<option value>전체</option>");
					$("#"+name.substr(0,11).concat("Emd")).empty().append("<option value>전체</option>");
					$("#"+name.substr(0,11).concat("Ri")).empty().append("<option value>전체</option>");
	        		$.each(list.result,function(idx,item){
	        			$("#"+name.substr(0,11).concat("Sgg")).append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        		});
	        	}
	        }
		});		
	});
	
	/**
	 * 행정구역 읍면동 조회 ajax
	 * @returns
	 */
	$('.SvyComptSgg').on('change', function (event) {
		var code = event.target.value;
		var name = event.target.name;
		
		if(code == null || code == undefined || code.length == 0){
			$("#"+name.substr(0,11).concat("Emd")).empty().append("<option value>전체</option>");
			$("#"+name.substr(0,11).concat("Ri")).empty().append("<option value>전체</option>");
			return false;
		}
		
		var url = "/cmm/selectAdministZoneEmd.do";
		var param = {sggCode: code};
		
		fncCmnSearch(url, param, function (list) {
			if(list.result){
	        	if(list.result.length > 0){
					$("#"+name.substr(0,11).concat("Emd")).empty().append("<option value>전체</option>");
					$("#"+name.substr(0,11).concat("Ri")).empty().append("<option value>전체</option>");
	        		$.each(list.result,function(idx,item){
	        			$("#"+name.substr(0,11).concat("Emd")).append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        		});
	        	}
	        }
		});				
	});
	
	/**
	 * 행정구역 리 조회 ajax
	 * @returns
	 */
	$('.SvyComptEmd').on('change', function (event) {
		var code = event.target.value;
		var name = event.target.name;
		
		if(code == null || code == undefined || code.length == 0){
			$("#"+name.substr(0,11).concat("Ri")).empty().append("<option value>전체</option>");
			return false;
		}
		
		var url = "/cmm/selectAdministZoneRi.do";
		var param = {emdCode: code};
		
		fncCmnSearch(url, param, function (list) {
			if(list.result){
	        	if(list.result.length > 0){
					$("#"+name.substr(0,11).concat("Ri")).empty().append("<option value>전체</option>");
	        		$.each(list.result,function(idx,item){
	        			$("#"+name.substr(0,11).concat("Ri")).append("<option value=\"".concat(item.adminCode,"\">").concat(item.adminNm,"</option>"));
	        		});
	        	}
	        }
		});		
	});
	/*상세검색  페이지 행정구역 조회 처리  E*/

	/*날짜 조회 조건처리 S*/
	$('.SvyComptStartDt').on('change', function (event) {
	//	var name = event.target.id.substr(0,3);
		var name = $(this).attr('name').substr(0,3);
		var startDateInput = document.getElementById(name+"SvyComptStartDt");
		var endDateInput = document.getElementById(name+"SvyComptEndDt");
		
		var startDate = new Date(startDateInput.value);
	    var endDate = new Date(endDateInput.value);
		
		if (startDate > endDate) {
		    var reDate = new Date();
	        alert("시작일은 종료일보다 이전 날짜여야 합니다.");
	 		$("#"+name+"SvyComptStartDt").val('reDate');
	 		$("#"+name+"SvyComptEndDt").val('reDate');
	    }
	    
		});
		
	$('.SvyComptEndDt').on('change', function (event) {
	//	var name = event.target.id.substr(0,3);
		var name = $(this).attr('name').substr(0,3);
		var startDateInput = document.getElementById(name+"SvyComptStartDt");
		var endDateInput = document.getElementById(name+"SvyComptEndDt");
		
		var startDate = new Date(startDateInput.value);
	    var endDate = new Date(endDateInput.value);
		
 		if (startDate > endDate) {
	    var reDate = new Date();
	        alert("종료일은 시작일보다 이후 날짜여야 합니다.");
	 		$("#"+name+"SvyComptStartDt").val('reDate');
	 		$("#"+name+"SvyComptEndDt").val('reDate');
	    } 
		
	});	
	/*날짜 조회 조건처리 E*/
	
	$(".SvyComptShpDownBtn").on("click", function(event){
		var name = event.target.name.substr(0,3);
		var startDateInput = document.getElementById(name+"SvyComptStartDt").value;
		var endDateInput = document.getElementById(name+"SvyComptEndDt").value;		
	
		if(endDateInput.length > 0 && startDateInput.length > 0){
			var url = "/sys/gis/selectSvyComptShpDown.do";
			var param = fnSerializeObject(name.concat('SvyComptSchForm'));
			param = $.extend(param, {type:name});
			
			if(confirm("공간정보를 다운로드하시겠습니까?")){
				$(".loading-div").show();
				
				var xhr = new XMLHttpRequest();
				var formdata = new FormData();
				$.each(param,function(key,value){
					formdata.set(key,value);
				});
				
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
						a.download = decodeURI(filename);
						document.body.appendChild(a);
						a.click();
						window.URL.revokeObjectURL(url);
						a.remove();
					}
				};
				xhr.onerror = function(){
					$(".loading-div").hide();
					alert("다운로드를 실패하였습니다.");
				};
				xhr.open('POST', url);
				xhr.responseType = 'blob';
				xhr.send(formdata);
			}
		} else {
		 	alert("날짜 기간을 설정해주세요");
		}
	});
	
	/*조사 상세검색  조회 처리  S*/
	$(".SvyComptBtn").on("click", function (event) {
		
		var name = event.target.name.substr(0,3);
		
		/* 2023.08.31 추가 -승현 */
		var startDateInput = document.getElementById(name+"SvyComptStartDt").value;
		var endDateInput = document.getElementById(name+"SvyComptEndDt").value;		
	
		if(endDateInput.length > 0  && startDateInput.length > 0){
			//행정구역 검색항목이 있을 경우 행정구역코드가 아닌 행정구역명칭을 파라메터로 넘겨주기 위한 처리
//			var sdnm = $("#".concat(name).concat("SvyComptSd option:selected")).text();
//			var sggnm = $("#".concat(name).concat("SvyComptSgg option:selected")).text();
//			var emdnm = $("#".concat(name).concat("SvyComptEmd option:selected")).text();
//			var rinm = $("#".concat(name).concat("SvyComptRi option:selected")).text();
//			
//			$("input[name=".concat(name).concat("SvyComptSdNm]")).val(sdnm == "전체" ? "": sdnm);
//			$("input[name=".concat(name).concat("SvyComptSggNm]")).val(sggnm == "전체" ? "": sggnm);
//			$("input[name=".concat(name).concat("SvyComptEmdNm]")).val(emdnm == "전체" ? "": emdnm);
//			$("input[name=".concat(name).concat("SvyComptRiNm]")).val(rinm == "전체" ? "": rinm);
			
			// 페이징 조회		
			fnSearchSvyComptLst(name, 1, true);
			
			var url = "/sys/gis/selectSvyComptWKT.do";
			var param = fnSerializeObject(name.concat('SvyComptSchForm'));
			
			param = $.extend(param, {type:name});
			
			// 벡터 조회
			fncCmnSearch(url, param, fnCallbackSvyComptFeature);
		
		} else {
		
		 	alert("날짜 기간을 설정해주세요");
		
		}
	});
	/*조사 상세검색  조회 처리  E*/
	
	/**
	 * 조사유형 별 입력 조건 변경
	 */
	$('.SvyComptType').on('change', function (event) {
		var name = event.target.name.substr(0,3);
		
		var type = $(this).val();
		var classNm = 'SvyComptInput';
		
		if($(this).val() == '') {
			if(name == 'bsc') {
				if(!$('.'.concat(classNm,'.bsc1')).hasClass('Hidden')) $('.'.concat(classNm,'.bsc1')).addClass("Hidden");
				if(!$('.'.concat(classNm,'.bsc2')).hasClass('Hidden')) $('.'.concat(classNm,'.bsc2')).addClass("Hidden");
			}
			if(name == 'wka') {
				if(!$('.'.concat(classNm,'.wka1')).hasClass('Hidden')) $('.'.concat(classNm,'.wka1')).addClass("Hidden");
				if(!$('.'.concat(classNm,'.wka2')).hasClass('Hidden')) $('.'.concat(classNm,'.wka2')).addClass("Hidden");
			}
			if(name == 'cnl') {
				if(!$('.'.concat(classNm,'.cnl1')).hasClass('Hidden')) $('.'.concat(classNm,'.cnl1')).addClass("Hidden");
				if(!$('.'.concat(classNm,'.cnl2')).hasClass('Hidden')) $('.'.concat(classNm,'.cnl2')).addClass("Hidden");
			}
			if(name == 'apr') {
				if(!$('.'.concat(classNm,'.apr1')).hasClass('Hidden')) $('.'.concat(classNm,'.apr1')).addClass("Hidden");
				if(!$('.'.concat(classNm,'.apr2')).hasClass('Hidden')) $('.'.concat(classNm,'.apr2')).addClass("Hidden");
				if(!$('.'.concat(classNm,'.apr3')).hasClass('Hidden')) $('.'.concat(classNm,'.apr3')).addClass("Hidden");
			}
		}
		else {
		
			if(name == 'bsc') {
				if(type == '산사태') {
					$('.'.concat(classNm,'.bsc1')).removeClass("Hidden");
					$('.'.concat(classNm,'.bsc2')).addClass("Hidden");
				}
				else {
					$('.'.concat(classNm,'.bsc1')).addClass("Hidden");
					$('.'.concat(classNm,'.bsc2')).removeClass("Hidden");
				}
			}
			if(name == 'wka') {
				if(type == '산사태') {
					$('.'.concat(classNm,'.wka1')).removeClass("Hidden");
					$('.'.concat(classNm,'.wka2')).addClass("Hidden");
				}
				else {
					$('.'.concat(classNm,'.wka1')).addClass("Hidden");
					$('.'.concat(classNm,'.wka2')).removeClass("Hidden");
				}
			}
			if(name == 'cnl') {
				if(type == '산사태') {
					$('.'.concat(classNm,'.cnl1')).removeClass("Hidden");
					$('.'.concat(classNm,'.cnl2')).addClass("Hidden");
				}
				else {
					$('.'.concat(classNm,'.cnl1')).addClass("Hidden");
					$('.'.concat(classNm,'.cnl2')).removeClass("Hidden");
				}
			}
			if(name == 'apr') {
				if(type == '사방댐 외관점검') {
					$('.'.concat(classNm,'.apr1')).removeClass("Hidden");
					$('.'.concat(classNm,'.apr2')).addClass("Hidden");
					$('.'.concat(classNm,'.apr3')).addClass("Hidden");
				}
				else if(type == '산지사방 외관점검') {
					$('.'.concat(classNm,'.apr1')).addClass("Hidden");
					$('.'.concat(classNm,'.apr2')).removeClass("Hidden");
					$('.'.concat(classNm,'.apr3')).addClass("Hidden");
				}
				else {
					$('.'.concat(classNm,'.apr1')).addClass("Hidden");
					$('.'.concat(classNm,'.apr2')).addClass("Hidden");
					$('.'.concat(classNm,'.apr3')).removeClass("Hidden");
				}
			}			
		}
	});
	
	/* 계측기 장비유형 별 검색조건 변경 */
	$("#mseSvyComptEqpmnType").on('change', function (event) {
		var type = $(this).val();
		var idx = this.selectedIndex;
		$("div[class*='mse']").addClass("Hidden");
		$(".mse".concat(idx)).removeClass("Hidden");
	});
	
	/* 정밀점검 조사유형 별 검색조건 변경 */
	$("#pcsSvyComptSvyType").on('change', function(event){
		var idx = this.selectedIndex;
		$("div[class*='pcs']").addClass("Hidden");
		$(".pcs".concat(idx)).removeClass("Hidden");
	});
	
	/*조사완료 초기화 버튼 클릭 이벤트 */
	$('.SvyComptBtnReset').on('click', function (event) {
		var name = event.target.name.substr(0,3);
		
		fnSvyComptScheRst(name);
	});


	//데이트피커 기본 옵션 
	$.datepicker.setDefaults($.datepicker.regional['ko']);
	
	var dateDefOpts = {
		changeMonth: true, 
        changeYear: true,
        nextText: '다음 달',
        prevText: '이전 달', 
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		dayNames: ['일','월','화','수','목','금','토'],
        dateFormat: "yy-mm-dd",
        maxDate: 0,
        showAnim:"slideDown",          
		showMonthAfterYear: true,
		minDate: new Date(2021, 1, 1),
		onClose: function (date, inst) {
			var id = inst.id;
		if(date != ''){
			if (id.indexOf('Start') > 0) {
				var name = id.replace('Start', 'End');
				var range = "minDate";
			} else if (id.indexOf('End') > 0) {
				var name = id.replace('End', 'Start');
				var range = "maxDate"; 
			}
	
			$("#" + name).datepicker("option", range, date);
			}
		}
	};
	
	/*기초조사*/
	$( "#bscSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#bscSvyComptEndDt" ).datepicker(dateDefOpts); 	
	/*땅밀림*/
	$( "#lcpSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#lcpSvyComptEndDt" ).datepicker(dateDefOpts); 	
	/*실태조사*/
	$( "#wkaSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#wkaSvyComptEndDt" ).datepicker(dateDefOpts); 	
	/*해제조사*/
	$( "#cnlSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#cnlSvyComptEndDt" ).datepicker(dateDefOpts); 	
	/*외관점검*/
	$( "#aprSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#aprSvyComptEndDt" ).datepicker(dateDefOpts);
	/*임도타당성평가*/
	$( "#frdSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#frdSvyComptEndDt" ).datepicker(dateDefOpts);
	/*계측기*/
	$( "#mseSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#mseSvyComptEndDt" ).datepicker(dateDefOpts);
	/*정밀점검*/
	$( "#pcsSvyComptStartDt" ).datepicker(dateDefOpts); 	
	$( "#pcsSvyComptEndDt" ).datepicker(dateDefOpts);
});