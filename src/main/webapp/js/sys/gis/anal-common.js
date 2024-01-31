var wholeSearchQuery = "";

$(document).ready(function(){
	
	//map Div 높이 조절
	//checkSize();
	
	//메인지도 생성
	//fncMainMap();
	
	var jijukCode = $("input[name=lgstrCd]").val();
	var analId = $("input[name=analId]").val();
	var mstId = $("input[name=mstId]").val();
	var sldId = $("input[name=sldId]").val();
	
	
	
	SM.initialize("map");
	
	if(analId.length > 0){
		
		var param = {queryParameter: {name: "tf_feis_watershed@".concat(CONFIG.ALIAS),attributeFilter: "anal_id = '".concat(analId,"'")},datasetNames: [CONFIG.ALIAS.concat(":tf_feis_watershed")],proxy:"/cmm/proxy.do?url="};
		SM.zoomToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,param,null);
//		var param = {"analId":analId,"mstId":mstId,"sldId":sldId};
//		fncWaterShedResultLayer(param);
		var mntnParam = {queryParameter: {name: "tf_feis_mntntrnt@".concat(CONFIG.ALIAS),attributeFilter: "anal_id = '".concat(analId,"'")},datasetNames: [CONFIG.ALIAS.concat(":tf_feis_mntntrnt")],proxy:"/cmm/proxy.do?url="};
		SM.getQueryLayer(CONFIG.URLS.SUPERMAP.DATA,mntnParam,null);
		
		var ecrtcnlParam = {queryParameter: {name: "tf_feis_ecrtcnl@".concat(CONFIG.ALIAS),attributeFilter: "anal_id = '".concat(analId,"'")},datasetNames: [CONFIG.ALIAS.concat(":tf_feis_ecrtcnl")],proxy:"/cmm/proxy.do?url="};
		SM.getQueryLayer(CONFIG.URLS.SUPERMAP.DATA,ecrtcnlParam,null);
	}else{
		if(jijukCode.length > 0){
			var param = {queryParameter: {name: "tf_feis_lgstr@".concat(CONFIG.ALIAS),attributeFilter: "a1 = '".concat(jijukCode,"'")},datasetNames: [CONFIG.ALIAS.concat(":tf_feis_lgstr")],proxy:"/cmm/proxy.do?url="};
			SM.zoomToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,param,null);
		}
	}

	fncAddSuperMapLayers();
	//fncCheckLayers();
});

/*
 * 메인지도 생성
 */
function fncMainMap(){
	SM.initialize("map");
}

/**
 * @author ipodo
 * @name fnSelectMapBrowser
 * @param {Event} e - click event
 * @returns
 * @description 지도 싱글클릭 이벤트
 */
function fnSingleclickMap(e) {
    if (e.dragging) { return; }

    if(e) {
    	var resolution = (e.target.getView().getResolution());
    	var projection = (e.target.getView().getProjection());
    	var coordinate = ol.proj.transform(e.coordinate, 'EPSG:3857', 'EPSG:4326');
    	var extent = [14147570.357082546, 4427592.58336714, 14230512.26164389, 4467847.633381992];
    	var pixel = e.pixel;
    	
    	var geometry = new ol.geom.Polygon.circular(coordinate, 10);
    	var datasetNames = [];
    	
    	SM.map.getLayers().getArray().forEach(function(d) {
    		if(d instanceof ol.layer.Tile && d.get("id").indexOf("bsc") == 0) {
    			datasetNames.push(d.get("id").indexOf("Svycompt") > -1 ? CONFIG.ALIAS.concat(':tn_feis_bsc_svycompt') : CONFIG.ALIAS.concat(':comtsyslssbsc_stripland')); 
    		}
    	})
    	
    	var param = {
    		spatialQueryMode: SuperMap.SpatialQueryMode.INTERSECT,
    		datasetNames: datasetNames, 
    		geometry: geometry.transform('EPSG:4326', 'EPSG:5186')
    	};
    	
    	SM.getFeatureByGeometry('', CONFIG.URLS.SUPERMAP.DATA, param);
    }
}

/**
 * 지도 클릭이벤트 포인트 생성
 * @returns
 */
function fncAddPoint() {
	SM.changeMapControl(2);
}

function fncAddLine() {
	SM.changeMapControl(3);
}

function fncAddPolygon() {
	SM.changeMapControl(4);
}

function fncAddWaterShedPolygon(){
	SM.changeMapControl(5);
}

/**
 * 유역분석 유출구 레이어 초기화
 * @returns
 */
function fncAddPointClear(){
	if(SM.ecrtcnlLayer != null && SM.ecrtcnlLayer.getSource().getFeatures().length > 0){
		SM.ecrtcnlLayer.getSource().clear();
	}
}

/**
 * 유역분석 유역그리기 레이어 초기화
 * @returns
 */
function fncAddWaterShedPolygonClear(){
	if(SM.watershedLayer != null && SM.watershedLayer.getSource().getFeatures().length > 0){
		SM.watershedLayer.getSource().clear();
	}
}

/**
 * 계류경사분석 종단계류경사 레이어 초기화
 * @returns
 */
function fncAddLineClear(){
	if(SM.mntnTrntLayer != null && SM.mntnTrntLayer.getSource().getFeatures().length > 0){
		SM.mntnTrntLayer.getSource().clear();
	}
}

/**
 * 지적정보 범위설정 레이어 초기화
 * @returns
 */
function fncAddPolygonClear(){
	if(SM.cadastralLayer != null && SM.cadastralLayer.getSource().getFeatures().length > 0){
		SM.cadastralLayer.getSource().clear();
	}
}
/**
 * 유역분석
 * @returns
 */
function fncAnalWaterShed(){
	if(SM.ecrtcnlLayer == null || SM.ecrtcnlLayer.getSource().getFeatures().length == 0){
		alert("유출구 정보가 없습니다.");
		return;
	}
	
	if(confirm("유역분석 작업을 시작하시겠습니까?")){
		if(SM.resultPointLayer){
			SM.resultPointLayer.getSource().clear();
		}
		
		if(SM.resultPolygonLayer){
			SM.resultPolygonLayer.getSource().clear();
		}
		
		fncAddWaterShedPolygonClear();
		
		var $feature = SM.ecrtcnlLayer.getSource().getFeatures()[0].clone();
		
		//$feature.getGeometry().transform('EPSG:3857', 'EPSG:5186');
		
		var xy = $feature.getGeometry().getLastCoordinate();
		
		//var pt = new Proj4js.toPoint(xy[0],xy[1]);
		//var result = proj4.transform("EPSG:3857","EPSG:5179",pt);
		$(".loading-div").show();
		$.ajax({
			url:"/sys/gis/als/insertWaterShed.do",
			type:"POST",
	    	data: {
	    		x: xy[0],
	        	y: xy[1],
	        	mstId:$("input[name=mstId]").val(),
	        	sldId:$("input[name=sldId]").val(),
	        	svyType:$("input[name=svyType]").val(),
	        	lgstrCd:$("input[name=lgstrCd]").val()
		    },
	    	dataType:"json",
	        success:function(data){
		    	if(data.status == "success"){
	    			alert(data.message);
	    			$("input[name=analId]").val(data.result.anal_id);
	    			fncWaterShedResultLayer(data.result);
	    			//window.location.reload();
	        	} else {
		    		alert(data.message);
	    		}
	        },
		    error: function(data){
	    		alert(data.message);
	        },
	        complete: function(){
	        	$(".loading-div").hide();
	        }
		});
	}
}

/**
 * 유역분석 결과 벡터레이어 생성
 * @param params
 * @returns
 */
function fncWaterShedResultLayer(params){
	
	var filter = "mst_id = ".concat(params.mst_id).concat(" and sld_id = '").concat(params.sld_id).concat("' and anal_id = '").concat(params.anal_id).concat("'");
	
	var ecrtcnlParam = {queryParameter: {name: "tf_feis_ecrtcnl@".concat(CONFIG.ALIAS),attributeFilter: filter},datasetNames: [CONFIG.ALIAS.concat(":tf_feis_ecrtcnl")],proxy:"/cmm/proxy.do?url="};
	SM.getQueryLayer(CONFIG.URLS.SUPERMAP.DATA,ecrtcnlParam,true);
	
	var watershedVectorParam = {queryParameter: {name: "tf_feis_watershed@".concat(CONFIG.ALIAS),attributeFilter: filter},datasetNames: [CONFIG.ALIAS.concat(":tf_feis_watershed")],proxy:"/cmm/proxy.do?url="};
	SM.getQueryLayer(CONFIG.URLS.SUPERMAP.DATA,watershedVectorParam,true);
	
}


function fncSaveAnalImg(param){
	var analId = $("input[name=analId]").val();
	var url = "/sys/gis/als/selectWs$DownloadImg.do";//selectWsLocDownloadImg.do
	
	if(analId.length == 0){
		alert("유역분석 정보가 존재하지 않습니다.");
		return;
	}
	
	/*
	 selectWsLocDownloadImg
	 selectWsSlopeDownloadImg
	 selectWsSatDownloadImg
	 selectWsKoftrDownloadImg
	 selectWsAgclsDownloadImg
	 selectWsDnstDownloadImg
	 selectWsDmclsDownloadImg
	 selectWsDemDownloadImg
	 selectWsRiverDownloadImg
	 selectWsAspectDownloadImg
	 selectWsNatureDownloadImg
	 
	 */
	url = url.replace("$",param);
	
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}

function fncCreatStreamOrder(){
	var analId = $("input[name=analId]").val();
	var url = "/sys/gis/als/creatWsStreamOrder.do";
	
	if(analId.length == 0){
		alert("유역분석 정보가 존재하지 않습니다.");
		return;
	}

	var hiddens = $("#listForm").find("input[type=hidden]");
	
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.ajaxForm({
		type: 'POST',
    	dataType: "json",
    	success: function(data) {
    		if (data.status == "success") {
            	alert(data.message);
            	//location.href="/sys/lss/bsc/sld/selectStripLandList.do";
        	} else {
                alert(data.message);
            }
    	},
		error: function(data) {
			alert(data.message);
		}
	}).submit();
	//form.appendTo("body").submit().remove();
}

function fncCreateVytEcbReportImgAll(){
	if(confirm("공간분석 작업을 시작하시겠습니까?")){
		$(".loading-div").show();
		
		var format = new ol.format.WKT();
		var $feature = null;
		var $watershedFeature = null;
		var $ecrtcnlFeature = null;
		
		if(SM.mntnTrntLayer != null && SM.mntnTrntLayer.getSource().getFeatures().length > 0){
			$feature = format.writeGeometry(SM.mntnTrntLayer.getSource().getFeatures()[0].getGeometry());
		}else if(SM.resultPolyLineLayer != null && SM.resultPolyLineLayer.getSource().getFeatures().length > 0){
			$feature = format.writeGeometry(SM.resultPolyLineLayer.getSource().getFeatures()[0].getGeometry());
		}
		
		if(SM.watershedLayer != null && SM.watershedLayer.getSource().getFeatures().length > 0){
			$watershedFeature = format.writeGeometry(SM.watershedLayer.getSource().getFeatures()[0].getGeometry());
		}
		
		if(SM.ecrtcnlLayer != null && SM.ecrtcnlLayer.getSource().getFeatures().length > 0){
			$ecrtcnlFeature = format.writeGeometry(SM.ecrtcnlLayer.getSource().getFeatures()[0].getGeometry());
		}
		
		$.ajax({
			url:"/sys/gis/als/selectVytEcbReportImgAll.do",
			type:"POST",
	    	data: {
	        	mstId:$("input[name=mstId]").val(),
	        	sldId:$("input[name=sldId]").val(),
	        	analId:$("input[name=analId]").val(),
	        	cadastralPnt:$("input[name=cadastralPntChk]").is(":checked"),
	        	lneWkt:$feature,
	        	waterShedWkt:$watershedFeature,
	        	ecrtcnlWkt:$ecrtcnlFeature
		    },
	    	dataType:"json",
	        success:function(data){
		    	if(data.status == "success"){
		    		$("input[name=analId]").val(data.result.analId);
	    			alert(data.message);
	    			//window.location.reload();
	    			$("#listForm").attr("action","/sys/vyt/ecb/als/selectAnalStripLandDetail.do");
	    			$("#listForm").submit();
	        	} else {
		    		alert(data.message);
	    		}
	        },
		    error: function(data){
	    		alert(data.message);
	        },
	        complete: function(){
	        	$(".loading-div").hide();
	        }
		});
	}
}

function fncCheckLayers(){
	$.each($("input[name='layers']"),function(idx,el){
		var chkId = $(el).attr("id");
		var lyr = SM.map.getLayer("fs_".concat(chkId));
		if(lyr != undefined && lyr != null){
			$(el).prop("checked",lyr.getVisible());
		}else{
			$(el).attr("disabled",true);
		}
	});
}

function fncAddSuperMapLayers(){
	$.each(CONFIG.LAYERS.SUPERMAP,function(idx,el){
		SM.map.addLayer(el);
	});
}

/**
 * 분석통계정보 엑셀다운로드
 * @returns
 */
function fnStatDataExcel(){
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	var form = $("<form></form>").attr("action","selectVytEcbAnalStatDataExcel.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}

/**
 * 지적정보 조회
 * @returns
 */
function fncSearchCadastral(){
	var format = new ol.format.WKT();
	var $feature = null;
	
	if(SM.cadastralLayer != null && SM.cadastralLayer.getSource().getFeatures().length > 0){
		$(".loading-div").show();
		$feature = format.writeGeometry(SM.cadastralLayer.getSource().getFeatures()[0].getGeometry());
		$.ajax({
			url:"/sys/vyt/ecb/als/selectCadastralDetail.do",
			type:"POST",
	    	data: {
	        	cadastralWkt:$feature
		    },
	    	dataType:"json",
	        success:function(data){
		    	if(data.status == "200"){
	    			if(data.result != null && data.result != undefined){
	    				if(data.result.length == 0){
	    					alert("조회결과가 없습니다.");
	    				}else{
	    					
	    					if($("table.cadastralTb").length){
	    						$("table.cadastralTb").remove();
	    					}
	    					
	    					var $table = $("<table></table>");
	    					$table.addClass("cadastralTb");
    						$table.attr("summary","검색된 지적결과에 대한 주소정보를 출력합니다.");
    						
    						var $caption = $("<caption></caption>");
    						$caption.addClass("Hidden");
    						$caption.html("검색 지적결과에 대한 주소정보 출력");
    						
    						var $colgroup = $("<colgroup></colgroup>");
    						$colgroup.append("<col style=\"width: 15%;\">");
    						$colgroup.append("<col style=\"width: 15%;\">");
    						$colgroup.append("<col style=\"width: 15%;\">");
    						$colgroup.append("<col style=\"width: 45%;\">");
    						$colgroup.append("<col style=\"width: 10%;\">");
    						
    						var $thead = $("<thead></thead>");
    						var $theadTr = $("<tr></tr>");
    						$theadTr.append("<th>지목</th>");
    						$theadTr.append("<th>지번</th>");
    						$theadTr.append("<th>소유구분</th>");
    						$theadTr.append("<th>소재지</th>");
    						$theadTr.append("<th></th>");
    						$thead.append($theadTr);
    						
    						var $tbody = $("<tbody></tbody>");
    						
	    					$.each(data.result,function(idx,elm){
	    						var adminCd = elm.pnucode;
	    						var jimok = elm.jimok;
	    						var jibun = elm.jibun;
	    						var posesnse = elm.posesnse;
	    						var addr = elm.addr;
	    						
	    						var $tr = $("<tr></tr>");
	    						$tr.addClass("center");
	    						
	    						var $jimokTd = $("<td></td>");
	    						$jimokTd.html(jimok);
	    						
	    						var $jibunTd = $("<td></td>");
	    						$jibunTd.html(jibun);
	    						
	    						var $posesnseTd = $("<td></td>");
	    						$posesnseTd.html(posesnse);
	    						
	    						var $addrTd = $("<td></td>");
	    						$addrTd.html(addr.concat(" "+jibun));
	    						
	    						var $linkTd = $("<td></td>");
	    						var $eumBtn = $("<button type=\"button\" class=\"search-btn\" onclick=\"fncLadUsePlanPopup('".concat(adminCd).concat("'); return false;\">토지이음</button>"));
	    						$linkTd.append($eumBtn);
	    						
	    						$tr.append($jimokTd).append($jibunTd).append($posesnseTd).append($addrTd).append($linkTd);
	    						$tbody.append($tr);
	    					});
	    					
	    					$table.append($caption).append($colgroup).append($thead).append($tbody);
	    					
	    					$("#alsTb").after($table);
	    				}
	    			}
	        	} else {
		    		alert(data.message);
	    		}
	        },
		    error: function(data){
	    		alert(data.message);
	        },
	        complete: function(){
	        	$(".loading-div").hide();
	        }
		});
	}else{
		alert("범위설정 정보가 존재하지 않습니다.");
	}
}

/**
 * 토지이음 팝업
 * @param code
 * @returns
 */
function fncLadUsePlanPopup(code){
	if(code != null && code != undefined){
		window.open("http://www.eum.go.kr/web/ar/lu/luLandDet.jsp?mode=search&selGbn=umd&isNoScr=script&pnu=".concat(code), "토지이용계획열람",'width=750px,height=1100px,scrollbars=no');
	}
}

function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/vyt/ecb/als/selectVytEcbAnalList.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}
