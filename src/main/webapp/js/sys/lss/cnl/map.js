/**
 * 공간정보 수정 스크립트
 */

//proj4 정의
if(proj4 != undefined){
	proj4.defs("EPSG:3857","+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs");
	proj4.defs("EPSG:4326", "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs");
	proj4.defs("EPSG:5179", "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs"); //UTM-K 
	proj4.defs("EPSG:5186", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"); //GRS80 Middle y:60000
} else{
	console.log("[Error] not define openlayers 'proj4' Object.");
};

ol.proj.proj4.register(proj4);

ol.Map.prototype.getLayer = ol.Map.prototype.getLayer || function (id) {var layer;this.getLayerGroup().getLayersArray().forEach(function (lyr) {if (id == lyr.get('id')) {layer = lyr;return false;}});return layer;};

//var CONFIG = {
//		KEYS: {
//			vworld : "01132028-7EDA-3A6F-A478-254737ABB844"
//		},
//		DOMAIN: {
//			vworld: "https://api.vworld.kr",
//			supermap: "http://192.168.30.2:8090"
//		},
//		ALIAS:"feisdb",
//		PROXY:"/proxy.jsp",
//		EXTENT: [-1009874.7491480233, -204449.96712471684, 1323425.8965473738, 927994.9012311122],
//		CENTER: [ 127.8605510029681, 35.8722852764138 ],
//		ZOOM: 7,
//		CONTROLMODE: 1,
//		GETRESOLUTIONS: function (len){
//			var res = [];
//			for (var i = 0; i < len; i++) {
//				res.push(2048 / Math.pow(2, i));
//			}
//			return res;
//		},
//		MAPCONTROLTYPE: {MOVE: 1,POINT: 2,POLYLINE: 3,POLYGON: 4}
//	};

var CONFIG = {
	KEYS: {
		vworld : window.location.hostname.indexOf("localhost") > -1 ? "5FC3D022-7090-3248-91F5-332A546EE0A4" : "46344FA7-411E-393B-80D0-17CB9359F044"
	},
	DOMAIN: {
		vworld: "https://api.vworld.kr",
		supermap: "http://121.135.104.61:10090"
	},
	ALIAS:"sabang",
	PROXY:"/proxy.jsp",
	EXTENT: [-1009874.7491480233, -204449.96712471684, 1323425.8965473738, 927994.9012311122],
	CENTER: [ 127.8605510029681, 35.8722852764138 ],
	ZOOM: 7,
	CONTROLMODE: 1,
	GETRESOLUTIONS: function (len){
		var res = [];
		for (var i = 0; i < len; i++) {
			res.push(2048 / Math.pow(2, i));
		}
		return res;
	},
	MAPCONTROLTYPE: {MOVE: 1,POINT: 2,POLYLINE: 3,POLYGON: 4,STAT: 9}
};

CONFIG.URLS = {
	VWORLD: {
		base: CONFIG.DOMAIN.vworld.concat("/req/wmts/1.0.0/").concat(CONFIG.KEYS.vworld,"/Base/{z}/{y}/{x}.png"),
		satellite: CONFIG.DOMAIN.vworld.concat("/req/wmts/1.0.0/").concat(CONFIG.KEYS.vworld,"/Satellite/{z}/{y}/{x}.jpeg"),
		hybrid: CONFIG.DOMAIN.vworld.concat("/req/wmts/1.0.0/").concat(CONFIG.KEYS.vworld,"/Hybrid/{z}/{y}/{x}.png"),
		jijuk: CONFIG.DOMAIN.vworld.concat("/req/wms"),
		search: CONFIG.DOMAIN.vworld.concat("/req/search")
	},
	SUPERMAP: {
		REST: CONFIG.DOMAIN.supermap.concat("/iserver/services/map-feis-server/rest/maps"),
		DATA: CONFIG.DOMAIN.supermap.concat("/iserver/services/data-feis-server/rest/data")
	}
};

CONFIG.VIEW = {
		projection: ol.proj.get("EPSG:5186"),
		center: ol.proj.fromLonLat(CONFIG.CENTER, "EPSG:5186"),//ol.proj.fromLonLat(CONFIG.CENTER, "EPSG:3857"),//ol.proj.transform(CONFIG.CENTER, 'EPSG:4326', 'EPSG:5186'),
		zoom: CONFIG.ZOOM,
		minZoom: 6,
		maxZoom: 19
};

CONFIG.LAYERS = {
	BASEGROUP: new ol.layer.Group({
		layers: [
			new ol.layer.Tile({
				title: "일반지도",
				id: "base",
				visible: false,
				type: "base",
				source: new ol.source.XYZ({
					url: CONFIG.URLS.VWORLD.base,
					crossOrigin: "anonymous"
				})
			}),
			new ol.layer.Tile({
				title: "위성지도",
				id: "satellite",
				visible: true,
				source: new ol.source.XYZ({
					url: CONFIG.URLS.VWORLD.satellite,
					crossOrigin: "anonymous"
				})
			}),
			new ol.layer.Tile({
				title: "하이브리드지도",
				id: "hybrid",
				visible: false,
				source: new ol.source.XYZ({
					url: CONFIG.URLS.VWORLD.hybrid,
					crossOrigin: "anonymous"
				})
			})
		]
	}),
	SUPERMAP: [
		new ol.layer.Tile({
			title: "지적",
			id: "fs_lgstr",
			visible: false,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","lgstr"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		})
	],
	VECTORS: [
		new ol.layer.Vector({
			title: '사방댐',
			id: 'editLayer01',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: "rgba(170,0,0,0)"
				}),
				stroke: new ol.style.Stroke({
					color: "#3100D7",
					width: 2
				})
			})
		}),
		new ol.layer.Vector({
			title: '계류보전',
			id: 'editLayer02',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: "rgba(170,0,0,0)"
				}),
				stroke: new ol.style.Stroke({
					color: "#F0E28F",
					width: 2,
					lineDash:[10]
				})
			})
			
		}),
		new ol.layer.Vector({
			title: '유역면적',
			id: 'editLayer03',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: "rgba(170,0,0,0)"
				}),
				stroke: new ol.style.Stroke({
					color: "#3100D7",
					width: 2,
					lineDash:[10]
				})
			})
		}),
		new ol.layer.Vector({
			title: '산지사방',
			id: 'editLayer04',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: "rgba(170,0,0,0)"
				}),
				stroke: new ol.style.Stroke({
					color: "#CC0000",
					width: 2
				})
			})
		}),
		new ol.layer.Vector({
			title: '대피로',
			id: 'editLayer05',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "#AD1B1B",
					width: 2
				})
			})
		})
	]
};

var SM = {
	map: null,
	
	interaction:{
		layer: null,
		source: null,
		feature: null,
		saveLayer: null
	},
	draw:{interaction:null},
	snap:{interaction:null},
	modify:{interaction:null},
	eventListener: {
		singleclick: [],
		contextmenu: []
	},
	initialize: function(mapId){
		if(mapId == null || mapId == undefined){
			mapId = "map";
		}
		
		this.createMap(mapId);
	},
	createMap: function(mapId){
		this.map = new ol.Map({
			target: mapId,
			controls: ol.control.defaults({zoom:false,attributionOptions:{collapsed: false}}),
			view: new ol.View(CONFIG.VIEW),
			layers: [CONFIG.LAYERS.BASEGROUP]
		});
		//this.map.on("pointermove",this.pointerMoveHandler);
	},
	getLayer: function(layer,id){
		var result = null;
		if(layer.getLayersArray){
			var layers = layer.getLayersArray();
			$.each(layers,function(idx,item){
				if(item.get("id") === id){
					result = item;
				}
			});
		}
		return result;
	},
	changeMapControl: function(type){
		CONFIG.CONTROLMODE = type;
		//this.drawClear();
		this.removeInteractions();
		switch(type){
			case CONFIG.MAPCONTROLTYPE.MOVE:
				//this.map.removeInteraction(this.draw.interaction);
				//this.map.removeInteraction(this.snap.interaction);
				//this.map.removeInteraction(this.modify.interaction);
				//this.interaction.layer.getSource().clear();
				break;
			case CONFIG.MAPCONTROLTYPE.POINT:
				this.addInteraction("Point");
				break;
			case CONFIG.MAPCONTROLTYPE.POLYLINE:
				this.addInteraction("LineString");
				break;
			case CONFIG.MAPCONTROLTYPE.POLYGON:
				this.addInteraction("Polygon");
				break;
			default:
				break;
		}
	},
	addInteraction: function(type){
		var $this = this;
		var eventListener = null;
		//this.draw.source = new ol.source.Vector();
//		this.interaction.layer = new ol.layer.Vector({
//			source: new ol.source.Vector(),
//			style: new ol.style.Style({
//				fill: new ol.style.Fill({
//					color: "rgba(170,0,0,0.5)"
//				}),
//				stroke: new ol.style.Stroke({
//					color: "#aa0000",
//					width: 3
//				}),
//				image: new ol.style.Circle({
//					radius: 7,
//					filee: new ol.style.Fill({
//						color: "#ffcc33"
//					})
//				})
//			})
//		});
//		
//		this.map.addLayer(this.interaction.layer);
		//var chkLayer = this.map.getLayer("editLayer".concat(SM.draw.type));
		
		this.draw.interaction = new ol.interaction.Draw({
			source: this.interaction.layer.getSource(),
			type: type,
			style: new ol.style.Style({
				fill: new ol.style.Fill({
					color: "rgba(255, 255, 255, 0.2)"
				}),
				stroke: new ol.style.Stroke({
					color: "rgba(170, 0, 0, 0.5)",
					lineDash: [10, 4],
					width: 2
				}),
				image: new ol.style.Circle({
					radius: 5,
					stroke: new ol.style.Stroke({
						color: "rgba(170, 0, 0, 0.7)"
					}),
					fill: new ol.style.Fill({
						color: "rgba(255, 255, 255, 0.2)"
					})
				})
			})
		});
		
		this.snap.interaction = new ol.interaction.Snap({
			source: this.interaction.layer.getSource()
		});
		
		this.modify.interaction = new ol.interaction.Modify({
			source: this.interaction.layer.getSource()
		});
		
		this.map.addInteraction(this.draw.interaction);
		this.map.addInteraction(this.snap.interaction);
		this.map.addInteraction(this.modify.interaction);
		
		this.draw.interaction.on("drawstart",function(e){
//			var drawSource = SM.interaction.layer.getSource();
//			if(drawSource.getFeatures().length > 0){
//				SM.interaction.layer.getSource().clear();
//			}
			//SM.interaction.layer.getSource().getFeatures().length;
			//$this.drawClear();
//			var coordinate = e.coordinate;
//			
//			$this.draw.feature = e.feature;
//			eventListener = $this.draw.feature.getGeometry().on("change", function(e){
//				var geometry = e.target;
//				var output = null;
//				
//				if(geometry instanceof ol.geom.Polygon){
//					output = $this.getArea(geometry);
//					coordinate = geometry.getInteriorPoint().getCoordinates();
//				}else if(geometry instanceof ol.geom.LineString){
//					output = $this.getLength(geometry);
//					coordinate = geometry.getLastCoordinate();
//				}
//				
//				$this.draw.measureTooltip.innerHTML = output;
//				$this.draw.measureOverlay.setPosition(coordinate);
//			});
		},this);
		
		this.draw.interaction.on("drawend", function(event){
			//$this.draw.feature = null;
			//$this.draw.layer.getSource().getFeatures()
			
			//$this.interaction.saveLayer.getSource().clear();
			
//			var feature = event.feature;
//		    var features = $this.interaction.layer.getSource().getFeatures();
//		    features = features.concat(feature);
//			
//			$this.interaction.saveLayer.getSource().addFeatures(features);
//			$this.drawClear();
//			ol.Observable.unByKey(eventListener);
			var drawSource = SM.interaction.layer.getSource();
			if(drawSource.getFeatures().length > 0){
				SM.interaction.layer.getSource().clear();
			}
		});
	},
	drawClear: function(){
		if(this.interaction.layer !== undefined && this.interaction.layer !== null){
			this.map.removeInteraction(this.draw.interaction);
			this.map.removeInteraction(this.snap.interaction);
			this.map.removeInteraction(this.modify.interaction);
			this.interaction.layer.getSource().clear();
		}
		//(this.interaction.layer !== undefined && this.interaction.layer !== null) ? (this.map.removeInteraction(this.draw.interaction),this.interaction.layer.getSource().clear()) : null;
	},
	removeInteractions: function(){
		this.map.removeInteraction(this.draw.interaction);
		this.map.removeInteraction(this.snap.interaction);
		this.map.removeInteraction(this.modify.interaction);
	}
};

$(document).ready(function(){
	SM.initialize("map");
	
	var statMap = $("input[name=statmap]").val();//현황도
	var evaData = $("input[name=evadata]").val();//대피로 좌표
	var evaMapNm = $("input[name=evamapnm]").val();//대피로명
	
	var svyData_1 =  $("#vnaraPntWkt").val();
	var svyData_2 =  $("#svydata").val();
	
	var svyData = svyData_1 || svyData_2;//대상지 좌표
	
	// var vnaraPnt = $("input[name=vnaraPntWkt]").val();//유출구(조사대상 좌표)
	var vnaraLne = $("input[name=vnaraLneWkt]").val();//대피로 경로
	var vnaraPlgn01 = $("input[name=vnaraPlgnWkt01]").val();//사방댐
	var vnaraPlgn02 = $("input[name=vnaraPlgnWkt02]").val();//계류보전
	var vnaraPlgn03 = $("input[name=vnaraPlgnWkt03]").val();//유역면적
	var vnaraPlgn04 = $("input[name=vnaraPlgnWkt04]").val();//산지사방
	
	
	// 유출구 좌표로 세팅
	if(svyData != null && svyData != ""){
		let svyCenterPnt = svyData.match(/\d+.\d+/g);
		svyCenterPnt = svyCenterPnt.map(val => parseFloat(val));
		
		SM.map.getView().setZoom(15);
		SM.map.getView().setCenter(svyCenterPnt);
	}
	
	
	
	if(statMap != null && statMap != ""){
		var statMapJson = JSON.parse(statMap);
		
		$.each(statMapJson,function(key,value){
			console.log(key);
		});
		
	}
	
	if(evaData != null && evaData != ""){
		
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(evaData);
		var vectorSource = new ol.source.Vector({
            features: readfeatures,
            wrapX: false
        });
        var resultLayer = new ol.layer.Vector({
        	id:"evaDataLayer",
        	title:"대피지점", 
            source: vectorSource,
            style: new ol.style.Style({
            	image: new ol.style.Circle({
                    radius: 7,
                    fill: new ol.style.Fill({ color: '#E49C46' }),
                    stroke: new ol.style.Stroke({ color: '#000000', width: 1 })
                })
			})
        });
        SM.map.addLayer(resultLayer);
//        SM.map.getView().fit(vectorSource.getExtent());
	}

	if(vnaraPlgn01 != null && vnaraPlgn01 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(vnaraPlgn01);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[0].setSource(vectorSource);
	}
	
	if(vnaraPlgn02 != null && vnaraPlgn02 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(vnaraPlgn02);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[1].setSource(vectorSource);
	}
	
	if(vnaraPlgn03 != null && vnaraPlgn03 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(vnaraPlgn03);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[2].setSource(vectorSource);
	}
	
	if(vnaraPlgn04 != null && vnaraPlgn04 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(vnaraPlgn04);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[3].setSource(vectorSource);
	}
	
	if(vnaraLne != null && vnaraLne != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(vnaraLne);
		var vectorSource = new ol.source.Vector({
            features: readfeatures,
            wrapX: false
        });
		CONFIG.LAYERS.VECTORS[4].setSource(vectorSource);
	}
	
	
	
	//SM.map.getLayer("svyDataLayer").setSource()
	
	
	
	//vnarapnt 값 조회
	//없으면
	if(svyData != null && svyData != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(svyData);
		var vectorSource = new ol.source.Vector({
            features: readfeatures,
            wrapX: false
        });
        var resultLayer = new ol.layer.Vector({
        	id:"svyDataLayer",
        	title:"유출구", 
            source: vectorSource,
            style: new ol.style.Style({
            	image: new ol.style.Circle({
                    radius: 7,
                    fill: new ol.style.Fill({ color: '#FD0000' }),
                    stroke: new ol.style.Stroke({ color: '#000000', width: 1 })
                })
			})
        });
        SM.map.addLayer(resultLayer);
	}
	
	$.each(CONFIG.LAYERS.VECTORS,function(idx,layer){
		SM.map.addLayer(layer);
	});
	
	/*
	 * 이벤트 처리
	 */

	//배경지도 선택
	$(".btn-map-selector").on("click",function(){
		if($(this).hasClass("selected")){
			return false;
		}
		
		$(".btn-map-selector").removeClass("selected");
		$(this).addClass("selected");
		
		if($(this).val() == "base"){
			SM.getLayer(SM.map.getLayerGroup(),"satellite").setVisible(false);
			SM.getLayer(SM.map.getLayerGroup(),"hybrid").setVisible(false);
			$(".hybrid-check").hide();
		}else if($(this).val() == "satellite"){
			SM.getLayer(SM.map.getLayerGroup(),"base").setVisible(false);
			$("#hybrid").prop("checked",true).change();
			$(".hybrid-check").show();
		}
		
		SM.getLayer(SM.map.getLayerGroup(),$(this).val()).setVisible(true);
	});
	
	//하이브리드 선택
	$("#hybrid").on("change",function(){
		var checked = false;
		if($(this).is(":checked")){
			checked = true;
		}
		SM.getLayer(SM.map.getLayerGroup(),"hybrid").setVisible(checked);
	});
	
	//배경지도버튼 설정
	$.each(CONFIG.LAYERS.BASEGROUP.getLayersArray(),function(idx,el){
		var id = el.get("id");
		var visibled = el.getVisible();
		if(visibled && id != "hybrid"){
			$(".btn-map-selector[value=".concat(id).concat("]")).trigger("click");
		}
	});
	
	//공간정보 편집버튼 설정
	$(".ol-edit-control-btns button").on("click",function(){
		if($(this).hasClass("selected")){
			$(this).removeClass("selected");
			//SM.draw.type = null;
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.MOVE);
			
			$('.deletBtnArea').css("display", "none");
			return false;
		}
		
		$(".ol-edit-control-btns button").removeClass("selected");
		$(this).addClass("selected");
		
		SM.interaction.layer = SM.map.getLayer("editLayer".concat($(this).val()));
		
		//SM.draw.type = $(this).val();
		if(/01|02|03|04/.test($(this).val())){
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.POLYGON);
		}else if(/05/.test($(this).val())){
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.POLYLINE);
		}
		
		// 해당 버튼 클릭 시 레이어 삭제버튼 표출
		var btnTitleNm = $(this).attr('title');
		if($(this).hasClass("selected")){
			$('.deletBtnArea').css("display", "");
			if(btnTitleNm == '유역면적'){
				$('.deletBtnArea').css('padding-left','0px');
				$('.landDel-btn-m').css('width','84px');
				$('.landDel-btn-m').val('editLayer03');
			}else if(btnTitleNm == '사방댐'){
				$('.deletBtnArea').css('padding-left','87px');
				$('.landDel-btn-m').css('width','70px');
				$('.landDel-btn-m').val('editLayer01');
			}else if(btnTitleNm == '계류보전'){
				$('.deletBtnArea').css('padding-left','162px');
				$('.landDel-btn-m').css('width','84px');
				$('.landDel-btn-m').val('editLayer02');
			}else if(btnTitleNm == '산지사방'){
				$('.deletBtnArea').css('padding-left','250px');
				$('.landDel-btn-m').css('width','84px');
				$('.landDel-btn-m').val('editLayer04');
			}else if(btnTitleNm == '대피로'){
				$('.deletBtnArea').css('padding-left','338px');
				$('.landDel-btn-m').css('width','70px');
				$('.landDel-btn-m').val('editLayer05');
			}
				
			$('.landDel-btn-m').attr("title",btnTitleNm+" 삭제");
		}
	});
	
	// 삭제버튼 클릭
	$(".landDel-btn-m").on("click",function(){
		$(".ol-edit-control-btns button").removeClass("selected");
		SM.changeMapControl(CONFIG.MAPCONTROLTYPE.MOVE);
		var btnValue= $(this).val();
		SM.map.getLayer(btnValue).getSource().clear();
		
		if(btnValue == "editLayer01"){ //사방댐
			$('input[name=vnaraPlgnWkt01]').val('');
			
		}else if(btnValue == "editLayer02"){ //계류보전
			$('input[name=vnaraPlgnWkt02]').val('');
			
		}else if(btnValue == "editLayer03"){ //유역면적
			$('input[name=vnaraPlgnWkt03]').val('');
			
		}else if(btnValue == "editLayer04"){ //산지사방
			$('input[name=vnaraPlgnWkt04]').val('');
			
		}else if(btnValue == "editLayer05"){ //대피로
			$('input[name=vnaraLneWkt]').val('');
			
		}
		
		$('.landDel-btn-m').val('');
		$('.deletBtnArea').css("display", "none");
	});
	
});

//공간정보 저장
function fnGeomSave(){
	 if (confirm("저장하시겠습니까?")) {
		var vnaraPntFeature = SM.map.getLayer("svyDataLayer").getSource().getFeatures();//유출구
		var vnaraLneFeature = SM.map.getLayer("editLayer05").getSource().getFeatures();//대피로
		var vnaraPlgnFeature01 = SM.map.getLayer("editLayer01").getSource().getFeatures();//사방댐
		var vnaraPlgnFeature02 = SM.map.getLayer("editLayer02").getSource().getFeatures();//계류보전
		var vnaraPlgnFeature03 = SM.map.getLayer("editLayer03").getSource().getFeatures();//유역면적
		var vnaraPlgnFeature04 = SM.map.getLayer("editLayer04").getSource().getFeatures();//산지사방
		
		var format = new ol.format.WKT();
		
		var vnaraPntWkt = null, vnaraLneWkt = null, vnaraPlgnWkt01 = null, vnaraPlgnWkt02 = null, vnaraPlgnWkt03 = null, vnaraPlgnWkt04 = null;
		
		if(vnaraPntFeature.length > 0){
			vnaraPntWkt = format.writeGeometry(vnaraPntFeature[0].getGeometry());
			$("input[name=vnaraPntWkt]").val(vnaraPntWkt);
		}else{
			$("input[name=vnaraPntWkt]").val("");
		}
		
		if(vnaraLneFeature.length > 0){
			vnaraLneWkt = format.writeGeometry(vnaraLneFeature[0].getGeometry());
			$("input[name=vnaraLneWkt]").val(vnaraLneWkt);
		}else{
			$("input[name=vnaraLneWkt]").val("");
		}
		
		if(vnaraPlgnFeature01.length > 0){
			vnaraPlgnWkt01 = format.writeGeometry(vnaraPlgnFeature01[0].getGeometry());
			$("input[name=vnaraPlgnWkt01]").val(vnaraPlgnWkt01);
		}else{
			$("input[name=vnaraPlgnWkt01]").val("");
		}
		
		if(vnaraPlgnFeature02.length > 0){
			vnaraPlgnWkt02 = format.writeGeometry(vnaraPlgnFeature02[0].getGeometry());
			$("input[name=vnaraPlgnWkt02]").val(vnaraPlgnWkt02);
		}else{
			$("input[name=vnaraPlgnWkt02]").val("");
		}
		
		if(vnaraPlgnFeature03.length > 0){
			vnaraPlgnWkt03 = format.writeGeometry(vnaraPlgnFeature03[0].getGeometry());
			$("input[name=vnaraPlgnWkt03]").val(vnaraPlgnWkt03);
		}else{
			$("input[name=vnaraPlgnWkt03]").val("");
		}
		
		if(vnaraPlgnFeature04.length > 0){
			vnaraPlgnWkt04 = format.writeGeometry(vnaraPlgnFeature04[0].getGeometry());
			$("input[name=vnaraPlgnWkt04]").val(vnaraPlgnWkt04);
		}else{
			$("input[name=vnaraPlgnWkt04]").val("");
		}
		
		$(".loading-div").show();
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/lss/cnl/sct/updateCnlSvyComptUpdt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
	        		alert("저장되었습니다.");
	        		fnList();
	            } else {
	                alert("저장이 실패하였습니다.\n에러내용 : "+data.message);
	                $(".loading-div").hide();
	            }
	    },
	    error: function(data) {
	    	alert(data.message);
	    	$(".loading-div").hide();
	    },
	    complete: function(){
	    	$(".loading-div").hide();
	    }
		}).submit();
     }
}

//목록 바로가기
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/lss/cnl/sct/selectCnlSvyComptList.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}


/**
 * 현황도 이미지 페이지 팝업
 */
function fnShowStatImg(gid){
	
	var url = "/sys/lss/cnl/sct/selectSvyComptStatPopup.do?gid="+gid;
	
	window.open(url,'치산정보시스템','width=365px,height=675px,scrollbars=no');
	
}