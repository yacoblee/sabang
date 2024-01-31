/**
 * 조사완료지 상세조회 map
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
			title: '기존노선',
			id: 'editLayer01',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "#FFFF33",
					width: 2
				})
			})
		}),
		new ol.layer.Vector({
			title: '수정노선',
			id: 'editLayer02',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "#0019F4",
					width: 2
				})
			})
		}),
		new ol.layer.Vector({
			title: '검토제안노선(1)',
			id: 'editLayer03',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "#FF0000", // 빨간색 점선
					width: 2,
					lineDash: [4,8]
				})
			})
		}),
		new ol.layer.Vector({
			title: '검토제안노선(2)',
			id: 'editLayer04',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "#FF8000", // 주황색 점선
					width: 2,
					lineDash: [4,8]
				})
			})
		}),
		new ol.layer.Vector({
			title: '검토제안노선(3)',
			id: 'editLayer05',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: "#CC00CC", // 보라색 점선
					width: 2,
					lineDash: [4,8]
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
		},this);
		
		this.draw.interaction.on("drawend", function(event){
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
	},
	removeInteractions: function(){
		this.map.removeInteraction(this.draw.interaction);
		this.map.removeInteraction(this.snap.interaction);
		this.map.removeInteraction(this.modify.interaction);
	}
};

$(document).ready(function(){
	SM.initialize("map");
	
	// 타당성 평가 라인 center point로 지도 중심 세팅
	var frdLneCntPntWkt = $("input[name=frdLneCntPntWkt]").val();//대피로 경로
	
	if(frdLneCntPntWkt != null && frdLneCntPntWkt != ""){
		let frdCenterPnt = frdLneCntPntWkt.match(/\d+.\d+/g);
		frdCenterPnt = frdCenterPnt.map(val => parseFloat(val));
		
		SM.map.getView().setZoom(15);
		SM.map.getView().setCenter(frdCenterPnt);
	}
	
	var frdExstnLneWkt = $("input[name=frdExstnLneWkt]").val();	// 기존노선
	var frdModLne = $("input[name=frdModLne]").val();	// 수정노선
	var frdRvwLne1 = $("input[name=frdRvwLne1]").val();	// 검토제안노선(1)
	var frdRvwLne2 = $("input[name=frdRvwLne2]").val();	// 검토제안노선(2)
	var frdRvwLne3 = $("input[name=frdRvwLne3]").val();	// 검토제안노선(3)
	
	var lneCnt = 0;
	
	if(frdExstnLneWkt != null && frdExstnLneWkt != ""){
        $("div[id=frdLine01]").addClass("on");
        lneCnt++;
    }

    if(frdModLne != null && frdModLne != ""){
        $("div[id=frdLine02]").addClass("on");
        lneCnt++;
    }

    if(frdRvwLne1 != null && frdRvwLne1 != ""){
        $("div[id=frdLine03]").addClass("on");
        lneCnt++;
    }

    if(frdRvwLne2 != null && frdRvwLne2 != ""){
        $("div[id=frdLine04]").addClass("on");
        lneCnt++;
    }

    if(frdRvwLne3 != null && frdRvwLne3 != ""){
        $("div[id=frdLine05]").addClass("on");
        lneCnt++;
    }
    
	if(lneCnt == 0){
		$(".frd-legend-div").css("display","none");
	}else if(lneCnt == 1){
		$(".frd-legend-div").css("margin-top","56%");
	}else if(lneCnt == 2){
		$(".frd-legend-div").css("margin-top","53.5%");
	}else if(lneCnt == 3){
		$(".frd-legend-div").css("margin-top","51%");
	}else if(lneCnt == 4){
		$(".frd-legend-div").css("margin-top","48.5%");
	}else if(lneCnt == 5){
		$(".frd-legend-div").css("margin-top","46%");
	}
    
    
	if(frdExstnLneWkt != null && frdExstnLneWkt != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(frdExstnLneWkt);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[0].setSource(vectorSource);
	}
	
	if(frdModLne != null && frdModLne != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(frdModLne);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[1].setSource(vectorSource);
	}
	if(frdRvwLne1 != null && frdRvwLne1 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(frdRvwLne1);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[2].setSource(vectorSource);
	}
	if(frdRvwLne2 != null && frdRvwLne2 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(frdRvwLne2);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[3].setSource(vectorSource);
	}
	if(frdRvwLne3 != null && frdRvwLne3 != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(frdRvwLne3);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[4].setSource(vectorSource);
	}
	
	$.each(CONFIG.LAYERS.VECTORS,function(idx,layer){
		SM.map.addLayer(layer);
	});
	
	
	$("input[name=frdLne]").on("change",function(){
		var checked = false;
		if($(this).is(":checked")){
			checked = true;
		}
		SM.map.getLayer($(this).val()).setVisible(checked);
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
});
