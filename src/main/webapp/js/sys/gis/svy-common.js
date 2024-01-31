/**
 * 기초조사 ~ 외관점검 등 조사 관련 공통 함수
 * 
 * 
 */
$(document).ready(function(){
	// 조사결과 조회 페이지 초기화
	fnResetSvyPagination();
});

/**
 * @author ipodo
 * @param {String} name - target name
 * @param {Integer} page - page number 
 * @param {Boolean} yn - 초기 검색 여부
 * @returns
 * @description 조사결과 조회 
 */
function fnSearchSvyComptLst(name,page, yn){
	var url = "/sys/gis/selectSvyComptLst.do";

	var param = fnSerializeObject(name.concat('SvyComptSchForm'));
	
	param = $.extend(param, {type:name, page:page});
	
	if(yn) fnSvyComptFeatureClear();
	fnSvyComptDataList(url, param);
}

/**
 * @author ipodo
 * @param {JSON} data - response data
 * @returns
 * @description 조사결과 조회 콜백 함수 
 */
function fnCallbackSvyComptLst(data) {
	 var type = data.request.type;

	var totalCnt = data.totalCnt;
	
	var pageNo = data.request.page;
	
	var targetId = type.concat("SchRsltLst");
	
	$("#".concat(targetId," tbody")).empty();
	
	if (data.status == "success") {
		$(".txtsearch.".concat(type," > span.txtblue")).text(numWithComma(totalCnt));
		if(data.response.length > 0){
			$.each(data.response, function (idx, value) {
				
				$.extend(value, {type:type});
				var addr = value.sd.concat(' ', value.sgg, ' ', value.emd, ' ', value.ri, ' ', value.jibun); 
			
				var item = $("<tr></tr>");
				$("<td>"+value.num+"</td>").appendTo(item); // 순번
				
				if(value.type == "lcp"){
					$("<td>"+value.lastgrd+"</td>").appendTo(item); //조사유형
				}else if(value.type == "frd"){
					$("<td>"+value.frdtype+"</td>").appendTo(item); //조사유형
				}else{
					$("<td>"+value.svytype+"</td>").appendTo(item); //조사유형
				}
				
				//var t3 =  $("<td><a href='javascript:SM.moveToPoint(\"" + value.lon + "\", \"" + value.lat + "\")'>"+addr+"</a></td>").appendTo(item); //주소
				$("<td>"+addr+"</td>")/*.data("props", value).on('click', fnSvyComptInfo)*/.appendTo(item); //주소
				var t4 = $("<td></td>").appendTo(item); // 더보기
				
				$("<button type='button' class='map-btn-m' name='"+type+"SvyComptBtnInfo'></button>").data("props", value).on('click', fnSvyComptInfo).appendTo(t4); // 정보 팝업 호출 버튼 
				$("<button type='button' class='search-btn-m' name='"+type+"SvyComptBtnDetail'></button>").data("props", value).on('click', fnSvyComptDetail).appendTo(t4); // 상세화면 이동 버튼
			
				item.appendTo($("#".concat(targetId," tbody")));
		    });
			
			if(data.response.length < 10) {
				var idx = 10 - data.response.length;
				
				while(idx > 0) {
					var item = $("<tr><td></td><td></td><td></td><td></td></tr>");
					item.appendTo($("#".concat(targetId," tbody")));
					idx--;
				}
			}
		}else{
			$("<tr><td colspan=\"4\">검색결과가 없습니다.</td></tr>").appendTo($("#".concat(targetId," tbody")));
		}

	    $("#"+targetId).next().html(totalCnt > 0 ? fnSvyComptPagination(totalCnt, 10, 5, pageNo, type) : resetPagination());
	}
}

/**
 * @author ipodo
 * @param {String} url - request url 
 * @param {JSON} param - request parameter 
 * @returns
 * @description 조사결과 조회 페이징 처리 
 */
function fnSvyComptDataList(url, param){
	
	$.ajax({
		url: url,
	    data: param,
	    type: "get",
	    dataType: "json",
	    success: fnCallbackSvyComptLst,
		error: function (error) {
	        console.log(error);
	        alert("검색 중 오류가 발생하였습니다.\n관리자에게 문의하세요.");
	    }
	});
}

/**
 * @author ipodo
 * @param {Integer} totalCnt - 전체레코드수
 * @param {Integer} dataSize - 페이지당 보여줄 데이타수
 * @param {Integer} pageSize - 페이지 그룹 범위 1 2 3 5 6 7 8 9 10
 * @param {Integer} pageNo - 현재페이지
 * @param {String} token - 타겟 엘리먼트 
 * @returns
 * @description 조사결과 조회 페이징 처리 
 */
function fnSvyComptPagination(totalCnt, dataSize, pageSize, pageNo, token){
	totalCnt = parseInt(totalCnt);// 전체레코드수
	dataSize = parseInt(dataSize); // 페이지당 보여줄 데이타수
	pageSize = parseInt(pageSize); // 페이지 그룹 범위 1 2 3 5 6 7 8 9 10
	pageNo = parseInt(pageNo); // 현재페이지

	var html = new Array();
  	if(!totalCnt){
  		return resetPagination();
  	}

  	// 페이지 카운트 총블럭수
  	var pageCnt = totalCnt % dataSize;
  	if(pageCnt == 0){
  		pageCnt = parseInt(totalCnt / dataSize);
  	}else{
  		pageCnt = parseInt(totalCnt / dataSize) + 1;
  	}

  	//페이지 그룹 번호
  	var pRCnt = parseInt(pageNo / pageSize);
  	if(pageNo % pageSize == 0){
  		pRCnt = parseInt(pageNo / pageSize) - 1;
  	}

  	//첫번째 번호
  	if(pRCnt != 0){
  		html.push("<button id=\"StartPagi\" type=\"button\" onclick=\"javascript:fnSearchSvyComptLst('".concat(token,"',1,false)\"><img src=\"/images/sub/first_arrow.png\" alt=\"맨처음\"></button>"));
  	}

  	//이전 번호
  	if(pageNo > pageSize){
  		var s2;
  		if(pageNo % pageSize == 0){
  			s2 = pageNo - pageSize;
  		}else{
  			s2 = pageNo - pageNo % pageSize;
  		}

  		html.push("<button id=\"prevPagi\" type=\"button\" onclick=\"javascript:fnSearchSvyComptLst('".concat(token,"',").concat(s2,",false)\"><img src=\"/images/sub/before.png\" alt=\"이전\"></button>"));
  	}else{
  		//html.push('<li><a href="javascript:void(0)" class="disabled">&lt;</a></li>');
  	}

  	//페이지 리스트
  	for(var index=pRCnt * pageSize + 1;index<(pRCnt + 1)*pageSize + 1;index++){
  		if(index == pageNo){
  			html.push("<a id=\"nowpage\" href=\"#none\" class=\"on\">".concat(index,"</a>"));
  		}else{
  			html.push("<a class=\"page\" href=\"javascript:fnSearchSvyComptLst('".concat(token,"',").concat(index,")\">").concat(index,"</a>"));
  		}

  		if(index == pageCnt){
  			break;
  		}
  	}

  	//다음 번호
  	if(pageCnt > (pRCnt + 1) * pageSize){
  		html.push("<button id=\"nextPagi\" type=\"button\" onclick=\"javascript:fnSearchSvyComptLst('".concat(token,"',").concat(((pRCnt + 1)*pageSize+1),",false)\"><img src=\"/images/sub/next.png\" alt=\"다음\"></button>")); 
  	}else{
  		//html.push('<li><a href="javascript:void(0)" class="disabled">&gt;</a></li>');
  	}

  	//마지막 번호
  	if(pageCnt > (pRCnt + 1) * pageSize){
  		html.push("<button id=\"EndPagi\" type=\"button\" onclick=\"javascript:fnSearchSvyComptLst('".concat(token,"',").concat(pageCnt,",false)\"><img src=\"/images/sub/last_arrow.png\" alt=\"맨끝\"></button>"));
  	}else{
  		//html.push('<li><a href="javascript:void(0)" class="disabled">&gt;&gt;</a></li>');
  	}

  	return html.join("");
}

/**
 * @author ipodo
 * @param 
 * @returns
 * @description 조사결과 조회 페이징 초기화 
 */
function fnResetSvyPagination() {
	$("#bscSchRsltLst").next().html(resetPagination());
	$("#lcpSchRsltLst").next().html(resetPagination());
	$("#wkaSchRsltLst").next().html(resetPagination());
	$("#cnlSchRsltLst").next().html(resetPagination());
	$("#aprSchRsltLst").next().html(resetPagination());
	$("#frdSchRsltLst").next().html(resetPagination());
	$("#mseSchRsltLst").next().html(resetPagination());
	$("#pcsSchRsltLst").next().html(resetPagination());
}


/**
 * @author ipodo
 * @param {String} name - form tag name
 * @returns {JSON}
 * @description 폼 데이터 json 객체로 변환
 */
function fnSerializeObject(name) {
	var result = {};
	var items = $('form[name='.concat(name,']')).serializeArray();
	$.each(items, function(i,f){ 
		result[f.name.substr(11,f.name.length).toLowerCase()] = f.value;
	});
	
	return result;
}

/**
 * @author ipodo
 * @param {String} name - 조사 종류명
 * @returns {JSON}
 * @description 폼 데이터 json 객체로 변환
 */
function fnSvyComptScheRst(name) {
	// 폼 태그 초기화
	$("form[name="+name+"SvyComptSchForm]")[0].reset();
	// 조사유형 초기화
	$('#'+name+'SvyComptSvyType').trigger('change');
	// 조사지역 초기화 
	$('#'+name+'SvyComptSd').trigger('change');
	// 검색 결과 목록 초기화
	$("#"+name+"SchRsltLst tbody").empty();
	// 페이지네이션 초기화
	$("#"+name+"SchRsltLst").next().html(resetPagination());
	// 검색 결과 건수 초기화 	
	$(".txtsearch.".concat(name," > span.txtblue")).text(0);

	fnResetDatePicker(name);
	fnSvyComptFeatureClear();	
}

/**
 * @author ipodo
 * @param {Event} event - 클릭 이벤트
 * @returns 
 * @description 조사완료 상세화면 버튼 클릭
 */
function fnSvyComptDetail(event) {
	var name = event.target.name.substr(0,3);
	var props = $(this).data('props');
	
	fnMoveDetailPage(name, props);
}

/**
 * @author ipodo
 * @param {Event} event - 클릭 이벤트
 * @returns 
 * @description 조사완료 상세화면(오버레이) 버튼 클릭
 */
function fnSvyComptDetail2(event) {
	//var name = $(this).data('name');
	//var gid = $(this).data('gid');
	var props = $(this).data('props');
	var name = props.type;
	fnMoveDetailPage(name, props);
}

/**
 * @author ipodo
 * @param {String} name - 조사분류
 * @param {String} gid - 고유아이디
 * @returns 
 * @description 조사완료 상세화면 이동
 */
function fnMoveDetailPage(name, props) {
	var gid = props.gid;
	var targetNm = name+'detailinfo'+gid;
	window.open('',targetNm);
	
	var url = "";
	
	if(name == 'bsc') url = "/sys/lss/bsc/sct/selectBscSvyComptDetail.do";
	if(name == 'lcp') url = "/sys/lss/lcp/sct/selectLcpSvyComptDetail.do";
	if(name == 'wka') url = "/sys/lss/wka/sct/selectWkaSvyComptDetail.do";
	if(name == 'cnl') url = "/sys/lss/cnl/sct/selectCnlSvyComptDetail.do";
	if(name == 'apr') url = "/sys/fck/apr/sct/selectFckAprComptDetail.do";
	if(name == 'frd') url = "/sys/vyt/frd/sct/selectFrdSvyComptDetail.do";
	if(name == 'mse') url = "/sys/fck/mse/sct/selectFckMseComptDetail.do";
	if(name == 'pcs') url = "/sys/fck/pcs/sct/selectFckPcsComptDetail.do";
	
	var form = $('<form></form>')
	form.attr('action', url);
	form.attr('method', 'POST');
	form.attr('target', targetNm);
	
	//$('<input type="text" name="type"></input>').val(name).appendTo(form);
//	$('<input type="text" name="gid"></input>').val(gid).appendTo(form);
	form.append($('<input type="text" name="gid"></input>').val(gid));
	if(name == "frd"){
		var mstId = props.mstId;
		var svyLabel = props.svyid;
		form.append($('<input type="text" name="mstId"></input>').val(mstId));
		form.append($('<input type="text" name="svyLabel"></input>').val(svyLabel));
	}
	document.body.appendChild(form[0]);
	
	form[0].submit();	
	
	document.body.removeChild(form[0]);
}

/**
 * @author ipodo
 * @param {JSON} data - 콜백 데이터
 * @returns 
 * @description 조사완료 벡터 이동
 */
function fnCallbackSvyComptFeature(data) {
	
	var id = 'svyCompt';
	var totalCnt = data.totalCnt;
	var type = data.request.type;
	var dataLayer = SM.map.getLayer(id.concat('Feature'));
	var clusterLayer = SM.map.getLayer(id.concat('Cluster'));
	
	if (data.status == "success" && totalCnt > 0) {
		
		var cluster = [];
		var features = [];
		$.each(data.response, function(i, f) { 
	
			var format = new ol.format.WKT();
			// 구분자로 배열 분리
			var items = f.split(';'); 
			// 첫번째 WKT 추출
			var feature  = items.splice(0,1).shift();
		    var readfeature = format.readFeatureFromText(feature);
			var props = fnSetSvyComptProps(type, items); 
			var coord = ol.extent.getCenter(readfeature.getGeometry().getExtent());
		
			// 중심좌표 추가
			$.extend(props, {coord: coord});

			readfeature.setProperties(props);
			cluster.push(new ol.Feature(new ol.geom.Point(coord)));
			features.push(readfeature);
			
			if(props.type == 'frd') {

			}
		});
	
		// 레이어 없을 시 
		if(!dataLayer) {
			var style;
			// 실 데이터 소스
			var dataSource = new ol.source.Vector({ wrapX: false, features:features,format: new ol.format.WKT() });
			// 클러스터 소스
			//var clusterSource = new ol.source.Cluster({ distance: 100, source: new ol.source.Vector({ wrapX: false, features:cluster }) });
			
			// 레이어 그룹
			var layers = fnSetClusterLayer(data.cluster);
			
			// 실 데이터 레이어
			var layer = new ol.layer.Vector({
				id: id.concat('Feature'),
				source: dataSource,
				visible: true, minZoom: 13, maxZoom: 19,
				style: fnSetFeatureStyle
			})
			
			layers.push(layer);
			
			var layerGroup = new ol.layer.Group({
				id: id.concat('Group'),
				layers: layers,
				visible: true
			});
			
			SM.map.addLayer(layerGroup);
		} 
		/*else {
			// 데이터 초기화
			dataLayer.getSource().clear();
			dataLayer.getSource().addFeatures(features);

			clusterLayer.getSource().getSource().clear();
			clusterLayer.getSource().getSource().addFeatures(features);
		}*/

		// 데이터 영역으로 줌 
	    //SM.map.getView().fit(dataSource.getExtent());

		// 싱글클릭 해제
		SM.unSingleClick();
		// 싱글클릭 등록
		SM.onSingleClick(fnOnSvyComptClick);

	}
}

/**
 * @author ipodo
 * @param {Feature} feature - 스타일 피처
 * @returns {Array}
 * @description 벡터 클러스터 레이어 생성 
 */
function fnSetClusterLayer(data) {
	var id = 'svyCompt';
	
	//레이어 그룹 
	var layers = [
		new ol.layer.Vector({
			id: id.concat("Sido"),
			source: new ol.source.Vector({
				wrapX: true,
				format: new ol.format.WKT()
			}),
			visible: true, minZoom: 6, maxZoom: 10,
			style: fnSetStyleByCluster
		}),
		new ol.layer.Vector({
			id: id.concat("Sgg"),
			source: new ol.source.Vector({
				wrapX: true,
				format: new ol.format.WKT()
			}),
			visible: true, minZoom: 10, maxZoom: 13,
			style: fnSetStyleByCluster
		}),
		/*new ol.layer.Vector({
			id: id.concat("Emd"),
			source: new ol.source.Vector({
				wrapX: true,
				format: new ol.format.WKT()
			}),
			visible: true, minZoom: 11, maxZoom: 13,
			style: fnSetStyleByCluster
		}),*/
	];
	
	// 시도
	var sido = [];
	$.each(data.sido, function(i, f) { 
		
		var format = new ol.format.WKT();
		// 구분자로 배열 분리
		var items = f.split(';'); 
		// 첫번째 WKT 추출
		var feature  = items[2];
	    var readfeature = format.readFeatureFromText(feature);
		var props = {
			adm_lv: 'sido',
			adm_code: items[0],
			adm_nm: items[1],
			adm_cnt: items[3]
		}; 
	
		readfeature.setProperties(props);
		sido.push(readfeature);
	});		

	//시군구 
	var sgg = [];
	$.each(data.sgg, function(i, f) { 
		
		var format = new ol.format.WKT();
		// 구분자로 배열 분리
		var items = f.split(';'); 
		// 첫번째 WKT 추출
		var feature  = items[2];
	    var readfeature = format.readFeatureFromText(feature);
		var props = {
			adm_lv: 'sgg',
			adm_code: items[0],
			adm_nm: items[1],
			adm_cnt: items[3]
		}; 
	
		readfeature.setProperties(props);
		sgg.push(readfeature);
	});		

	//읍면동
	/*
	var emd = [];
	$.each(data.emd, function(i, f) { 
		
		var format = new ol.format.WKT();
		// 구분자로 배열 분리
		var items = f.split(';'); 
		// 첫번째 WKT 추출
		var feature  = items[2];
	    var readfeature = format.readFeatureFromText(feature);
		var props = {
			adm_lv: 'emd',
			adm_code: items[0],
			adm_nm: items[1],
			adm_cnt: items[3]
		}; 
	
		readfeature.setProperties(props);
		emd.push(readfeature);
	});	
	*/	
	
	// 피처 추가
	layers[0].getSource().addFeatures(sido);
	layers[1].getSource().addFeatures(sgg);
	//layers[2].getSource().addFeatures(emd);
	
	return layers;
}

/**
 * @author ipodo
 * @param {Feature} feature - 스타일 피처
 * @returns 
 * @description 벡터 클러스터 스타일 함수 
 */
function fnSetStyleBySvyCompt(feature) {
	var styleCache = {};
	var size = feature.get('features').length;
	var style = styleCache[size];
	var radius = (size == 1 ? 5 : (size < 25 ? 10 : (size < 50 ? 15 : (size < 75 ? 20 : (size < 100 ? 25 : 30))))); 
	
	if (!style) {
	  style = new ol.style.Style({
	    image: new ol.style.Circle({
	      radius: radius,
	      stroke: new ol.style.Stroke({
	        color: '#fff'
	      }),
	      fill: new ol.style.Fill({
	        color: 'rgba(254,46,100,0.7)'
	      })
	    }),
	    text: size > 1 ? new ol.style.Text({
	      text: size.toString(),
	      fill: new ol.style.Fill({
	        color: '#fff'
	      })
	    }) : null
	  });
	  styleCache[size] = style;
	}
	
	return style;
}

/**
 * @author ipodo
 * @param {Feature} feature - 스타일 피처
 * @returns 
 * @description 벡터 클러스터 스타일 함수 
 */
function fnSetStyleByCluster(feature) {
	
	var props = feature.getProperties();
	var radius = (props.adm_lv == 'emd' ? 20 : (props.adm_lv == 'sgg' ? 30 : 35)); 
	var text = props.adm_cnt;
	
	var style = new ol.style.Style({
	    image: new ol.style.Circle({
	      radius: radius,
	      stroke: new ol.style.Stroke({
	        color: '#fff'
	      }),
	      fill: new ol.style.Fill({
	        color: 'rgba(254,46,100,0.7)'
	      })
	    }),
	    text: new ol.style.Text({
	      text: text,
		  scale: 2, 
	      fill: new ol.style.Fill({
	        color: '#fff'
	      })
	    })
	  });
	
	return style;
}

/**
 * @author ipodo
 * @param 
 * @returns 
 * @description 조사완료 벡터 레이어 초기화 
 */
function fnSvyComptFeatureClear() {
	var id = 'svyCompt';
	// 그룹 레이어
	var group = SM.map.getLayer(id.concat('Group'));
	
	if(!group) return false;
	
	var layers = group.getLayers().getArray();
	// 실제 벡터 레이어
	var layer = layers[2];
	// 클러스터 
	var sido = layers[0];
	var sgg = layers[1];
	//var emd = layers[2];
	
	// 벡터 오버레이
	var overlay = SM.map.getOverlayById(id.concat('Overlay'));
	
	//레이어 삭제
	if(layer) {
		
		layer.getSource().clear();
		sido.getSource().clear();
		sgg.getSource().clear();
		//emd.getSource().clear();

		SM.map.removeLayer(group);
	}
	
	//오버레이 삭제
	if(overlay) {
		overlay.setPosition(undefined);
		SM.map.removeOverlay(overlay);
	}
}

/**
 * @author ipodo
 * @param {String} type - 조사분류(사용보류)
 * @param {Array} items - 속성정보 값
 * @returns {JSON}
 * @description 조사완료 벡터 레이어 속성 정보 설정 
 */
function fnSetSvyComptProps(type, items) {
	var props = {};
	
	props.type = type;
	props.smid = items[0];
	props.mst_id = items[1];
	props.gid = items[2];
	props.svyid = items[3];
	props.svytype = items[4];
	props.svydt = items[5];
	props.svyuser = items[6];
	props.sd = items[7];
	props.sgg = items[8];
	props.emd = items[9];
	props.ri = items[10];
	props.jibun = items[11];
	props.lon = items[12];
	props.lat = items[13];
	props.pnu = items[14];
	
	if(type == 'apr') {
		props.fcltyear = items[15];
		props.fcltuprhg = items[16];
		props.fcltlwrhg = items[17];
		props.fcltstkhg = items[18];
		props.orginldamval = items[19];
		props.orginldamjdgval = items[20];
		props.sidewallval = items[21];
		props.sidewalljdgval = items[22];
		props.dwnsptval = items[23];
		props.dwnsptjdgval = items[24];
		props.reinfcval = items[25];
		props.reinfcjdgval = items[26];
		props.prtcval = items[27];
		props.prtcjdgval = items[28];
		props.drngval = items[29];
		props.drngjdgval = items[30];
		props.chkdamval = items[31];
		props.chkdamjdgval = items[32];
		props.rvtmntval = items[33];
		props.rvtmntjdgval = items[34];
		props.grdstablval = items[35];
		props.grdstabljdgval = items[36];
		props.etcjdgval = items[37];
		props.flugtjdgval = items[38];
		props.cstrdcprvnjdgval = items[39];
		props.vtnsttusjdgval = items[40];
		props.snddpsitjdgval = items[41];
		props.snddpsitval = items[42];
		props.fckrslt = items[43];
		props.appnrelis = items[44];
		props.mngmtr = items[45];
	}
	if(type == 'frd') {
		props.route_type = items[15];
	}
	
	return props 
}

/**
 * @author ipodo
 * @param {Event} e - single click event
 * @returns 
 * @description 조사완료 벡터 레이어 클릭 이벤트 핸들러 
 */
function fnOnSvyComptClick(e) {
	if (e.dragging) { return; }
	
    var coordinate = e.coordinate;

    if(e) {
    	var overlayId = "svyComptOverlay";
        e.target.forEachFeatureAtPixel(e.pixel, function (f) {
			var props = f.getProperties().features ? f.getProperties().features[0].getProperties() : f.getProperties();  
			var content = fnSetPopupContents(props);
			
			var options = { type: 'search', text: '상세보기', callback: fnSvyComptDetail2, data: { props: props } };
			
			// 오버레이 열기
			fnOpenOverlay(overlayId, coordinate, content, options);

            return true;    
        }, {
        	layerFilter: function (e) {
        		if(e.get("id") && e.get("id").includes("svyComptFeature")) return true;
        	}
        });
    }
}

/**
 * @author ipodo
 * @param {JSON} props - properties
 * @returns {String}
 * @description 조사완료 팝업 컨텐츠 설정 
 */
function fnSetPopupContents(props) {
	var table = $('<table><thead><colgroup><col width="25%"/><col width="25%"/><col width="25%"/><col width="25%"/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>'); 

	
	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

//	var tr1 = $('<tr><td class="title">조사지</td><td></td><td class="title">법적제한</td><td></td></tr>');
	var tr1 = $('<tr><td class="title">조사지</td><td class="alignL" colspan="3">'+addr+'</td></tr>');
	//법적제한 팝업 호출 이벤트 
//	$("<button class='search-btn-m'></button>").data("props", props).on('click', fncLadUsePlanPopup).appendTo($(tr1).children('td:eq(3)'));
	$("<button type='button' class='search-btn-s'>법적제한</button>").data("type","eum").data("props", props).on('click', fncLadUsePlanPopup).appendTo($(tr1).children('td:eq(1)'));
	
	$(tbody).append(tr1);
	
	var type = "";
	var typeValue = "";
	
	if(props.type == 'frd'){
		type = "임도종류";
		typeValue = props.frdtype;
	}else if(props.type == 'lcp'){
		type = "판정등급";
		typeValue = props.lastgrd;
	}else{
		type = "조사유형";
		typeValue = props.svytype;
	}
	
	var tr2 = $('<tr><td class="title">'+type+'</td><td></td><td class="title">조사일</td><td></td></tr>');
	$(tr2).children("td:eq(1)").text(typeValue); // 조사 유형
	$(tr2).children("td:eq(3)").text(props.svydt);	// 조사일
	$(tbody).append(tr2);
	
	var tr3 = $('<tr><td class="title">조사ID</td><td></td><td class="title">조사자</td><td></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svyid); 	// 조사아이디
	$(tr3).children("td:eq(3)").text(props.svyuser);	// 조사자 
	$(tbody).append(tr3);	
	
	var tr4 = $('<tr class="info close"><td class="title">시점좌표X</td><td></td><td class="title">시점좌표Y</td><td></td></tr>');
	$(tr4).children("td:eq(1)").text(props.lon);
	$(tr4).children("td:eq(3)").text(props.lat);
	//$(tbody).append(tr4);		
	
	// 외관점검 상세정보 노출
	if(props.type == 'apr') {
	var name = props.svytype.substr(0,4).trim();
	
		if(name == '사방댐'){
		var tr5 = $('<tr><td class="title" colspan="4">시설제원</td></tr>');
		var tr6 = $('<tr><td class="title">시설연도</td><td class="title">유효고</td><td class="title">상장</td><td class="title">하장</td></tr>');
		var tr7 =  $('<tr><td></td> <td></td><td></td> <td></td></tr>');
		
		var tr8 = $('<tr><td class="title" colspan="4">주요시설</td></tr>');
		var tr9 = $('<tr><td class="title">본댐 판정값</td><td class="title">측벽판정값</td><td class="title">물받이 판정값</td><td></td></tr>');
		var tr10 = $('<tr><td></td><td></td><td></td><td></td></tr>');
		
		var tr11 = $('<tr><td class="title" colspan="4">부대시설</td></tr>');
		var tr12 = $('<tr><td class="title">수문</td><td class="title">식생상태</td><td class="title">안전시설</td><td class="title">접근도로</td></tr>');
		var tr13 =  $('<tr><td></td><td></td><td></td><td></td></tr>');
		
		//var tr14 = $('<tr><td class="title" colspan = "2">기타</td><td class="title" colspan="6"></td><td></td></tr>');
		var tr14 = $('<tr><td class="title">저사량</td><td></td><td class="title">저사상태판정값</td><td></td></tr>');
		
		$(tr7).children("td:eq(0)").text(props.fcltyear);	
		$(tr7).children("td:eq(1)").text(props.fcltstkhg);
		$(tr7).children("td:eq(2)").text(props.fcltuprhg);			
		$(tr7).children("td:eq(3)").text(props.fcltlwrhg);
		
		
		$(tr10).children("td:eq(0)").text(props.orginldamjdgval);	
		$(tr10).children("td:eq(1)").text(props.sidewalljdgval);	
		$(tr10).children("td:eq(2)").text(props.dwnsptjdgval);	
		
		$(tr13).children("td:eq(0)").text(props.flugtjdgval);	
		$(tr13).children("td:eq(1)").text(props.vtnsttusjdgval);
		$(tr13).children("td:eq(2)").text(props.sffcjdgval);	
		$(tr13).children("td:eq(3)").text(props.accssrdjdgval);
		
		$(tr14).children("td:eq(1)").text(props.snddpsitval);	
		$(tr14).children("td:eq(3)").text(props.snddpsitjdgval);
		$(tbody).append(tr5).append(tr6).append(tr7).append(tr8).append(tr9).append(tr10).append(tr11).append(tr12).append(tr13).append(tr14)	
	} else if(name == '산지사방') {
			var tr5 = $('<tr><td class="title" colspan="4">시설제원</td></tr>');
			var tr6 = $('<tr><td class="title">시설연도</td><td class="title">높이</td><td class="title">폭</td><td class="title">평균경사</td></tr>');
			var tr7 =  $('<tr><td></td> <td></td><td></td> <td></td></tr>');		

			var tr8 = $('<tr><td class="title" colspan="4">주요시설</td></tr>');
			var tr9 = $('<tr><td class="title">보강시설 판정값</td><td class="title">보호시설 판정값</td><td class="title">배수시설 판정값</td><td></td></tr>');
			var tr10 = $('<tr><td></td><td></td><td></td><td></td></tr>');
			
			var tr11 = $('<tr><td class="title" colspan="4">부대시설</td></tr>');
			var tr12 = $('<tr><td class="title">수문</td><td class="title">식생상태</td><td class="title">안전시설</td><td class="title">접근도로</td></tr>');
			var tr13 =  $('<tr><td></td><td></td><td></td><td></td></tr>');			
			
			$(tr7).children("td:eq(0)").text(props.fcltyear);	
			$(tr7).children("td:eq(1)").text(props.fclthg);
			$(tr7).children("td:eq(2)").text(props.fcltrng);			
			$(tr7).children("td:eq(3)").text(props.fcltslp);
			
			
			$(tr10).children("td:eq(0)").text(props.reinfcjdgval);	
			$(tr10).children("td:eq(1)").text(props.prtcjdgval);	
			$(tr10).children("td:eq(2)").text(props.drngjdgval);	
			
			$(tr13).children("td:eq(0)").text(props.flugtjdgval);	
			$(tr13).children("td:eq(1)").text(props.vtnsttusjdgval);
			$(tr13).children("td:eq(2)").text(props.sffcjdgval);	
			$(tr13).children("td:eq(3)").text(props.accssrdjdgval);
			
			
			$(tbody).append(tr5).append(tr6).append(tr7).append(tr8).append(tr9).append(tr10).append(tr11).append(tr12).append(tr13)	
		
		} else if(name == '계류보전') {
			var tr5 = $('<tr><td class="title" colspan="4">시설제원</td></tr>');
			var tr6 = $('<tr><td class="title">시설연도</td><td class="title">길이</td><td class="title">폭</td><td class="title">깊이</td></tr>');
			var tr7 =  $('<tr><td></td> <td></td><td></td> <td></td></tr>');		

			var tr8 = $('<tr><td class="title" colspan="4">주요시설</td></tr>');
			var tr9 = $('<tr><td class="title">골막이 판정값</td><td class="title">기슭막이 판정값</td><td class="title">바닥막이 판정값</td><td></td></tr>');
			var tr10 = $('<tr><td></td><td></td><td></td><td></td></tr>');
			
			var tr11 = $('<tr><td class="title" colspan="4">부대시설</td></tr>');
			var tr12 = $('<tr><td class="title">수문</td><td class="title">식생상태</td><td class="title">안전시설</td><td class="title">접근도로</td></tr>');
			var tr13 =  $('<tr><td></td><td></td><td></td><td></td></tr>');			
			

			$(tr7).children("td:eq(0)").text(props.fcltyear);	
			$(tr7).children("td:eq(1)").text(props.fcltlng);
			$(tr7).children("td:eq(2)").text(props.fcltrng);			
			$(tr7).children("td:eq(3)").text(props.fcltdept);
			
			
			$(tr10).children("td:eq(0)").text(props.chkdamjdgval);	
			$(tr10).children("td:eq(1)").text(props.rvtmntjdgval);	
			$(tr10).children("td:eq(2)").text(props.grdstabljdgval);	
			
			$(tr13).children("td:eq(0)").text(props.flugtjdgval);	
			$(tr13).children("td:eq(1)").text(props.vtnsttusjdgval);
			$(tr13).children("td:eq(2)").text(props.sffcjdgval);	
			$(tr13).children("td:eq(3)").text(props.accssrdjdgval);
			
			
			$(tbody).append(tr5).append(tr6).append(tr7).append(tr8).append(tr9).append(tr10).append(tr11).append(tr12).append(tr13)		
		}
	
	}
	$(table).append(tbody);
	return table;	
}

/**
 * @author ipodo
 * @param {Event} event - click event
 * @returns  
 * @description 조사완료 리스트 오버레이  
 */
function fnSvyComptInfo(event) {
	
	var name = $(this).data('props').type;
	
	var overlayId = "svyComptOverlay";
	
	// 오버레이 닫기
	fnCloseOverlay(overlayId);
	
	var props = $(this).data('props');  
	var content = fnSetPopupContents(props);
	var coordinate = [props.lon, props.lat];
	var options = { type: 'search', text: '상세보기', callback: fnSvyComptDetail2, data: { props: props } };
			
	// 오버레이 열기
	fnOpenOverlay(overlayId, coordinate, content, options);
	
	// 결과 창 닫기
	if($('.navClose').hasClass('on')) $('.navClose').trigger('click')
	
	var lon = (name == 'apr' ? parseFloat(props.lon) + 60 : props.lon);
	var lat = (name == 'apr' ? parseFloat(props.lat) + 80 : props.lat);
	
	//위치 이동
	SM.moveToPoint(lon, lat);
	
	SM.map.getView().setZoom(18);
	
}

/**
 * @author ipodo
 * @param {String} name - target Name
 * @returns  
 * @description 조사완료 리스트 오버레이  
 */
function fnResetDatePicker(name) {
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
	
	$( "#"+name+"SvyComptStartDt" ).datepicker("destroy").datepicker(dateDefOpts); 	
	$( "#"+name+"SvyComptEndDt" ).datepicker("destroy").datepicker(dateDefOpts); 	
}

function fnQueryBySQL() {
	
	var url = CONFIG.URLS.SUPERMAP.DATA;
	
	var param = new SuperMap.GetFeaturesBySQLParameters({
	        queryParameter: {
	            name: "tf_feis_bsc_svycompt",
	            attributeFilter: "to_date(svydt,'YYYY-MM-DD') between to_date('2023-01-01', 'YYYY-MM-DD') and to_date('2023-05-01', 'YYYY-MM-DD')"
	        },
	        datasetNames: ["sabang:tf_feis_bsc_svycompt"],
			fromIndex: 0,
			toIndex: 999
	});
    new ol.supermap.FeatureService(url).getFeaturesBySQL(param, function (data) {
        console.log(data);
    });
}

/**
 * @author ipodo
 * @param {Feature} feature 
 * @returns {Style}
 * @description 임도 피처 스타일 지정
 */
function fnSetFeatureStyle(feature) {
	
	var style;
	var props = feature.getProperties();
	var type = props.type;

	if(type == 'lcp') {
		style = new ol.style.Style({stroke: new ol.style.Stroke({color: '#fff',width: 2}), fill: new ol.style.Fill({color: 'rgba(254,46,100,0.7)'})});	
	}
	else if(type == 'frd') {
		var color = props.route_type == '수정노선' ?  '#0000ff' : '#ffff00';
		style = new ol.style.Style({ stroke: new ol.style.Stroke({color: color, width: 4}) })		
	}
	else {
		style = new ol.style.Style({ 
			image: new ol.style.Circle({
				radius: 10, 
				stroke: new ol.style.Stroke({ color: '#fff' }), 
				fill: new ol.style.Fill({ color: 'rgba(254,46,100,0.7)' })
			})
		})
	}
				
	return style;
}