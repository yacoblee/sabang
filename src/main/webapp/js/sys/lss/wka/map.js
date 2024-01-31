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
		maxZoom: 25
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
			minZoom:15,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","lgstr"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(25)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "지적라벨",
			id: "fs_lgstr_label",
			visible: false,
			minZoom:15,
			source: new ol.source.TileSuperMapRest({
				url: CONFIG.URLS.SUPERMAP.REST.concat("/","lgstr_label"),
				wrapX: true,
				tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(25)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
			})
		}),
		new ol.layer.Tile({
			title: "등고선",
			id: "fs_ctrln",
			visible: false,
			minZoom:15,
			source: new ol.source.TileSuperMapRest({
				url: CONFIG.URLS.SUPERMAP.REST.concat("/","ctrln"),
				wrapX: true,
				tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(25)
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
		}),
		new ol.layer.Vector({
			title: '중간부',
			id: 'editLayer06',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.WKT()
			}),
			style: new ol.style.Style({
				image: new ol.style.Circle({
					radius: 7,
					fill: new ol.style.Fill({ color: '#33CCFF' }),
					stroke: new ol.style.Stroke({ color: '#000000', width: 1 })
				})
			})
		}),
		new ol.layer.Vector({
			title: '최상부',
			id: 'editLayer07',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.WKT()
			}),
			style: new ol.style.Style({
				image: new ol.style.Circle({
					radius: 7,
					fill: new ol.style.Fill({ color: '#99FF33' }),
					stroke: new ol.style.Stroke({ color: '#000000', width: 1 })
				})
			})
		}),
		new ol.layer.Vector({
			title: '대피지점',
			id: 'editLayer08',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.WKT()
			}),
			style: new ol.style.Style({
				image: new ol.style.Circle({
					radius: 7,
					fill: new ol.style.Fill({ color: '#E49C46' }),
					stroke: new ol.style.Stroke({ color: '#000000', width: 1 })
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
	snap:{
		layer: null,
		source: null,
		interaction: null,
		list:[]
	},
	addSnap: function(source){
		this.snap.interaction = new ol.interaction.Snap({
			source:source,
			pixelTolerance: 20
		});
		
		this.map.addInteraction(this.snap.interaction);
	},
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
			case CONFIG.MAPCONTROLTYPE.STAT:
				this.modifyInteraction("Point");
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
		
		if(SM.snap.list.length > 0){
			this.addSnap(SM.snap.source);
		}
		
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
	modifyInteraction: function(type){
		var $this = this;
		var eventListener = null;
		
		this.snap.interaction = new ol.interaction.Snap({
			source: this.interaction.layer.getSource()
		});
		
		this.modify.interaction = new ol.interaction.Modify({
			source: this.interaction.layer.getSource()
		});
		
		this.map.addInteraction(this.snap.interaction);
		this.map.addInteraction(this.modify.interaction);
		
		if(SM.snap.list.length > 0){
			this.addSnap(SM.snap.source);
		}
		
		this.modify.interaction.on('modifyend', function (event) {
			var drawSource = SM.interaction.layer.getSource();
			if(drawSource.getFeatures().length > 0){
					
				var features = SM.map.getLayer("editLayer10").getSource().getFeatures();
				var coordinates = [];

				features.forEach(function(feature) {
					var geometry = feature.getGeometry();
					var coordinate = geometry.getCoordinates();
					var style = feature.getStyle();
					
					if (style instanceof ol.style.Style) {
						var imageStyle = style.getImage();
						if (imageStyle instanceof ol.style.Icon) {
							var imageUrl = imageStyle.getSrc();
							var match = imageUrl.match(/statMap(\d+)\.png/);
						      if (match) {
								var number = match[1];
								
								$("input[name=lon" + number + "]").val(coordinate[0]);
								$("input[name=lat" + number + "]").val(coordinate[1]);
								
								var x = coordinate[0];
								var y = coordinate[1];
								
								var s_srs = new proj4.Proj("EPSG:5186");
								var t_srs = new proj4.Proj("EPSG:4326");
								
								var pt = new proj4.Point(parseFloat(x),parseFloat(y));
								var result =proj4.transform(s_srs,t_srs,pt);
								
								var convertLon = degree2Dms(result.x)+"E";
								var convertLat = degree2Dms(result.y)+"N";
								
								var td1 = $("#statTable tr:eq("+(parseInt(number)+1)+") td:eq(1)").text(convertLat);
								var td2 = $("#statTable tr:eq("+(parseInt(number)+1)+") td:eq(2)").text(convertLon);
								
					        }
						}
					}
				});
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
	},
	boundToQueryLayer: function(url,params){
		var params = new SuperMap.GetFeaturesByBoundsParameters(params);
		new ol.supermap.FeatureService(url,{proxy:CONFIG.PROXY.concat("?")}).getFeaturesByBounds(params, function (serviceResult) {
			var features = serviceResult.result.features;
			console.log(features.features.length);
			var format = new ol.format.GeoJSON();
			var readfeatures = format.readFeatures(features);
		    
			//SM.snap.source.push(snapSource);
			if(SM.snap.layer){
				SM.snap.source.clear();
				SM.snap.source.addFeatures(readfeatures);
			}else{
				SM.snap.source = new ol.source.Vector({
	                features: readfeatures,
	                wrapX: false
	            });
				
				SM.snap.layer = new ol.layer.Vector({
	                source: SM.snap.source
	            });
	            SM.map.addLayer(SM.snap.layer);
			}
		});
	}
};

$(document).ready(function(){
	SM.initialize("map");
	
	$.each(CONFIG.LAYERS.SUPERMAP,function(idx,value){
		SM.map.addLayer(value);
	});
	
	var statMap = $("input[name=statmap]").val();//현황도
	var evaData = $("input[name=evadata]").val();//대피로 좌표
	var evaMapNm = $("input[name=evamapnm]").val();//대피로명
	
	//var svyData_1 =  $("#vnaraPntWkt").val();
	//var svyData_2 =  $("#svydata").val();
	
	var svyData = $("#svydata").val();//대상지 좌표
	
	
	// var vnaraPnt = $("input[name=vnaraPntWkt]").val();//유출구(조사대상 좌표)
	var vnaraLne = $("input[name=vnaraLneWkt]").val();//대피로 경로
	//var vnaraPlgn01_1 = $("input[name=vnaraPlgnWkt01]").val();//사방댐
	//var vnaraPlgn02_1 = $("input[name=vnaraPlgnWkt02]").val();//계류보전
	//var vnaraPlgn03_1 = $("input[name=vnaraPlgnWkt03]").val();//유역면적
	//var vnaraPlgn04_1 = $("input[name=vnaraPlgnWkt04]").val();//산지사방
	
	//var lndslddgWkt = $("input[name=lndslddgWkt]").val();//사방댐
	//var mntntrntWkt = $("input[name=mntntrntWkt]").val();//계류보전
	//var dgrareaWkt = $("input[name=dgrareaWkt]").val();//유역면적
	//var mtcecnrWkt = $("input[name=mtcecnrWkt]").val();//산지사방
	//var evasysWkt = $("input[name=evasysWkt]").val();//대피체계
	
	
	/*
	 * 토석류 -> 사방댐 계류보전
	 * 산사태 -> 산지사방 
	 */
	var svyType = $("input[name=svyType]").val();

	if(svyType == '취약지역 실태조사(토석류)'){
		var vnaraPlgn01 = $("input[name=lndslddgWkt]").val();//사방댐
		var vnaraPlgn02 = $("input[name=mntntrntWkt]").val();//계류보전
	}else{
		var vnaraPlgn04 = $("input[name=mtcecnrWkt]").val();//산지사방
	}
	
	var vnaraPlgn03 = $("input[name=dgrareaWkt]").val();//유역면적

	
	var uploc = $("input[name=uploc]").val() || "";	//최상부
	var mdlloc = $("input[name=mdlloc]").val() || "";	//중간부

	// 유출구 좌표로 세팅
	if(svyData != null && svyData != ""){
		let svyCenterPnt = svyData.match(/\d+.\d+/g);
		svyCenterPnt = svyCenterPnt.map(val => parseFloat(val));
		
		SM.map.getView().setZoom(19);
		SM.map.getView().setCenter(svyCenterPnt);
		
		document.addEventListener("wheel", function (e) {
			
//			console.log(SM.map.getView().getZoom());
		});
	}
	
	
	// 현황도
	if(statMap != null && statMap != ""){
		var statMapJson = JSON.parse(statMap);
		
		var statmapHtml = '';
		$.each(statMapJson,function(key,value){
			
			statmapHtml += '<tr class="origin_stat">';
			statmapHtml += 	'<td>'+(key+1)+'</td>';
			statmapHtml += 	'<td>'+value.convertLat+'</td>';
			statmapHtml += 	'<td>'+value.convertLon+'</td>';
			statmapHtml += 	'<td><input type="text" name="spclNote'+(key+1)+'" value="'+value.spclNote+'"/></td>';
			statmapHtml += 	'<td><button type="button" class="del-btn" onclick="javascript:fnStatBtn(this); return false;">삭제</button>';
			statmapHtml += 	'<input type="hidden" name="lon'+(key+1)+'" value="'+value.lon+'"/>';
			statmapHtml += 	'<input type="hidden" name="lat'+(key+1)+'" value="'+value.lat+'"/>';
			statmapHtml += 	'<input type="hidden" name="heigth'+(key+1)+'" value="'+value.heigth+'"/>';
			statmapHtml += 	'<input type="hidden" name="length'+(key+1)+'" value="'+value.length+'"/>';
			statmapHtml += 	'<input type="hidden" name="dept'+(key+1)+'" value="'+value.dept+'"/></td>';
			statmapHtml += '</tr>';
			
		});
		
		$("#statTable").append(statmapHtml);
		
		var coordinates = [];
		
		for(var i=1; i<=5; i++){
			var lon = $("input[name=lon" + i + "]").val();
			var lat = $("input[name=lat" + i + "]").val();
			
			if (lon != null && lon !== '' && lat != null && lat !== '') {
				coordinates.push([lon, lat]);
			}
		}
		
		var multiPoint = new ol.geom.MultiPoint(coordinates);
		
		var features = [];
		
		var pointStyles = [
			{src : '/images/common/statMap1.png', scale: 0.25},
			{src : '/images/common/statMap2.png', scale: 0.25},
			{src : '/images/common/statMap3.png', scale: 0.25},
			{src : '/images/common/statMap4.png', scale: 0.25},
			{src : '/images/common/statMap5.png', scale: 0.25}
		]
		
		coordinates.forEach(function (coord, index) {
			  var lon = coord[0];
			  var lat = coord[1];

			  var style = new ol.style.Style({
			    image: new ol.style.Icon({
			      src: pointStyles[index].src,
			      scale: pointStyles[index].scale,
			    })
			  });

			  var pointFeature = new ol.Feature({
			    geometry: new ol.geom.Point([lon, lat]),
			  });

			  pointFeature.setStyle(style);
			  features.push(pointFeature);
		});
		
		var vectorLayer = new ol.layer.Vector({
			  id:"editLayer10",
			  source: new ol.source.Vector({
			    features: features,
			  })
		});
		SM.map.addLayer(vectorLayer);
	}
	
	
	if(evaData != null && evaData != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(evaData);
		var vectorSource = new ol.source.Vector({
            features: readfeatures,
            wrapX: false
        });
		CONFIG.LAYERS.VECTORS[7].setSource(vectorSource);
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
	
	if(uploc != null && uploc != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(uploc);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[6].setSource(vectorSource);
	}
	
	if(mdlloc != null && mdlloc != ""){
		var format = new ol.format.WKT();
		var readfeatures = format.readFeatures(mdlloc);
		var vectorSource = new ol.source.Vector({
			features: readfeatures,
			wrapX: false
		});
		CONFIG.LAYERS.VECTORS[5].setSource(vectorSource);
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
		
		console.log($(this).val());
		
		//SM.draw.type = $(this).val();
		if(/01|02|03|04/.test($(this).val())){
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.POLYGON);
		}else if(/05/.test($(this).val())){
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.POLYLINE);
		}else if(/06|07|08/.test($(this).val())){
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.POINT);
		}else if(/10/.test($(this).val())){
			SM.changeMapControl(CONFIG.MAPCONTROLTYPE.STAT);
		}
		
		// 해당 버튼 클릭 시 레이어 삭제버튼 표출
		var btnTitleNm = $(this).attr('title');
		var svyType = $('input[name=svyType]').val();
		if($(this).hasClass("selected")){
			$('.deletBtnArea').css("display", "");
			if(svyType == '취약지역 실태조사(산사태)'){
				if(btnTitleNm == '유역면적'){
					$('.deletBtnArea').css('padding-left','0px');
					$('.landDel-btn-m').css('width','84px');
					$('.landDel-btn-m').val('editLayer03');
				}else if(btnTitleNm == '산지사방'){
					$('.deletBtnArea').css('padding-left','87px');
					$('.landDel-btn-m').css('width','84px');
					$('.landDel-btn-m').val('editLayer04');
				}else if(btnTitleNm == '대피로'){
					$('.deletBtnArea').css('padding-left','175px');
					$('.landDel-btn-m').css('width','70px');
					$('.landDel-btn-m').val('editLayer05');
				}else if(btnTitleNm == '중간부'){
					$('.deletBtnArea').css('padding-left','250px');
					$('.landDel-btn-m').css('width','70px');
					$('.landDel-btn-m').val('editLayer06');
				}else if(btnTitleNm == '최상부'){
					$('.deletBtnArea').css('padding-left','323px');
					$('.landDel-btn-m').css('width','70px');
					$('.landDel-btn-m').val('editLayer07');
				}else if(btnTitleNm == '대피지점'){
					$('.deletBtnArea').css('padding-left','398px');
					$('.landDel-btn-m').css('width','84px');
					$('.landDel-btn-m').val('editLayer08');
				}else if(btnTitleNm == '현황도'){
					$('.deletBtnArea').css("display", "none");
				}
			}else{
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
				}else if(btnTitleNm == '대피로'){
					$('.deletBtnArea').css('padding-left','250px');
					$('.landDel-btn-m').css('width','70px');
					$('.landDel-btn-m').val('editLayer05');
				}else if(btnTitleNm == '중간부'){
					$('.deletBtnArea').css('padding-left','324px');
					$('.landDel-btn-m').css('width','70px');
					$('.landDel-btn-m').val('editLayer06');
				}else if(btnTitleNm == '최상부'){
					$('.deletBtnArea').css('padding-left','398px');
					$('.landDel-btn-m').css('width','70px');
					$('.landDel-btn-m').val('editLayer07');
				}else if(btnTitleNm == '대피지점'){
					$('.deletBtnArea').css('padding-left','472px');
					$('.landDel-btn-m').css('width','84px');
					$('.landDel-btn-m').val('editLayer08');
				}else if(btnTitleNm == '현황도'){
					$('.deletBtnArea').css("display", "none");
				}
				
			}
			$('.landDel-btn-m').attr("title",btnTitleNm+" 삭제");
		}
		
	});
	
	// 이벤트처리(지적)
	$("#chk_lgstr").on("click",function(){
		var checked;
		if($("#chk_lgstr").hasClass("selected") === true){
			$("#chk_lgstr").removeClass("selected");
			checked = false;
			$("input[name=snaps]").prop("checked", false);
			$(".lgstr-check").css("display","none");
			$(".lgstr-label-check").css("display","none");
			
			var chkId = $("input[name=snaps]").attr("id");
			var dsNm = chkId.replace("chk_snap",CONFIG.ALIAS.concat(":tf_feis"));
			var idx = SM.snap.list.findIndex(function(item){return item === dsNm});
			
			if(idx > -1) {
				SM.snap.list.splice(idx,1);
			}
			var boundParam = {datasetNames: SM.snap.list,bounds:SM.map.getView().calculateExtent(),fromIndex: 0,toIndex: 9999,maxFeatures: 10000,proxy:"/cmm/proxy.do?url="};
			SM.boundToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,boundParam);
			
			$("input[name=lgstrLabel]").prop("checked", false);
			SM.map.getLayer("fs_lgstr_label").setVisible(checked);
			
		}else{
			$("#chk_lgstr").addClass("selected");
			$(".lgstr-check").css("display","");
			$(".lgstr-label-check").css("display","");
			checked = true;
			
		}
		
		var chkId = $(this).attr("id");
		var lyr = SM.map.getLayer(chkId.replace("chk_","fs_"));
		
		if(lyr != undefined && lyr != null){
			lyr.setVisible(checked);
		}
	});
	
	$("input[name=lgstrLabel]").on("change",function(e){
		
		var checked = $(this).is(":checked") ? true : false;
		SM.map.getLayer("fs_lgstr_label").setVisible(checked);
		
	});
	
	$("#chk_ctrln").on("click",function(){
		var checked;
		
		if($("#chk_ctrln").hasClass("selected") === true){
			$("#chk_ctrln").removeClass("selected");
			checked = false;
			
		}else{
			$("#chk_ctrln").addClass("selected");
			checked = true;
		}
		
		var chkId = $(this).attr("id");
		var lyr = SM.map.getLayer(chkId.replace("chk_","fs_"));
		
		if(lyr != undefined && lyr != null){
			lyr.setVisible(checked);
		}
	});
	
	$("input[name=snaps]").on("change",function(e){
		var zoomLevel = SM.map.getView().getZoom();
		
		var checked = $(this).is(":checked");
		var chkId = $(this).attr("id");
		if(zoomLevel < 15 && checked == true){
			if(confirm("현재의 줌레벨은 "+Math.round(zoomLevel)+"입니다.\n스내핑 기능은 줌레벨 15이상부터 사용가능합니다.\n줌레벨 15로 이동하시겠습니까?")){
				SM.map.getView().setZoom(15);
				
				var dsNm = chkId.replace("chk_snap",CONFIG.ALIAS.concat(":tf_feis"));
				if(checked){
					SM.snap.list.push(dsNm);
				}else{
					var idx = SM.snap.list.findIndex(function(item){return item === dsNm});
					if(idx > -1) {
						SM.snap.list.splice(idx,1);
					}
				}
				var boundParam = {datasetNames: SM.snap.list,bounds:SM.map.getView().calculateExtent(),fromIndex: 0,toIndex: 9999,maxFeatures: 10000,proxy:"/cmm/proxy.do?url="};
				SM.boundToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,boundParam);
			}else{
				$(this).prop("checked", false);
				return false;
			}
		}else{
			var dsNm = chkId.replace("chk_snap",CONFIG.ALIAS.concat(":tf_feis"));
			if(checked){
				SM.snap.list.push(dsNm);
			}else{
				var idx = SM.snap.list.findIndex(function(item){return item === dsNm});
				if(idx > -1) {
					SM.snap.list.splice(idx,1);
				}
			}
			var boundParam = {datasetNames: SM.snap.list,bounds:SM.map.getView().calculateExtent(),fromIndex: 0,toIndex: 9999,maxFeatures: 10000,proxy:"/cmm/proxy.do?url="};
			SM.boundToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,boundParam);
		}
	});
	
	// 위치도
	var lcmap = JSON.parse($("input[name=lcmap]").val());
	
	var branchAreaTotal = 0;
	var incorAreaTotal = 0;
	for (var i = 0; i < lcmap.length; i++) {
		var lm = lcmap[i];
		
		var lcmapHtml = '';
		
		var pos = lm.소유구분;
		
		
		lcmapHtml += '<tr class="origin_lm">'
		lcmapHtml += 	'<td>'+lm.시도+'</td>'
		lcmapHtml += 	'<td>'+lm.시군구+'</td>'
		lcmapHtml += 	'<td>'+lm.읍면동+'</td>'
		lcmapHtml += 	'<td>'+lm.리+'</td>'
		lcmapHtml += 	'<td>'+lm.지번+'</td>'
		lcmapHtml += 	'<td>'+lm.지목+'</td>'
		lcmapHtml += 	'<td>'+lm.지적면적+'</td>'
		lcmapHtml +=	'<td>'+lm.편입면적+'</td>'
		if(pos == "" || pos == null || pos == undefined){
			lcmapHtml += 	'<td>-</td>'
		}else{
			lcmapHtml += 	'<td>'+lm.소유구분+'</td>'
		}
		lcmapHtml += '</tr>'
		
		branchAreaTotal += parseFloat((lm.지적면적).replace(/,/g,""));
		incorAreaTotal += parseFloat((lm.편입면적).replace(/,/g,""));
		
		if(branchAreaTotal > 0){
			$("#branchAreaTotal").text(branchAreaTotal.toFixed(2).replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
		}
		if(incorAreaTotal > 0){
			$("#incorAreaTotal").text(incorAreaTotal.toFixed(2).replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") );
		}
		
		$("#lcmapTable").append(lcmapHtml);
		
	}
	
	// 삭제버튼 클릭
	$(".landDel-btn-m").on("click",function(){
		$(".ol-edit-control-btns button").removeClass("selected");
		SM.changeMapControl(CONFIG.MAPCONTROLTYPE.MOVE);
		var btnValue= $(this).val();
		SM.map.getLayer(btnValue).getSource().clear();
		
		if(btnValue == "editLayer01"){ //사방댐
			$('input[name=vnaraPlgnWkt01]').val('');
			$('input[name=calcMntrntWkt]').val('');
			
		}else if(btnValue == "editLayer02"){ //계류보전
			$('input[name=vnaraPlgnWkt02]').val('');
			$('input[name=calcLndslddgWkt]').val('');
			
		}else if(btnValue == "editLayer03"){ //유역면적
			$('input[name=vnaraPlgnWkt03]').val('');
			
		}else if(btnValue == "editLayer04"){ //산지사방
			$('input[name=vnaraPlgnWkt04]').val('');
			$('input[name=calcMtcecnrwktWkt]').val('');
			
		}else if(btnValue == "editLayer05"){ //대피로
			$('input[name=vnaraLneWkt]').val('');
			
		}else if(btnValue == "editLayer06"){ //중간부
			$('input[name=mdlloc]').val('');
			
		}else if(btnValue == "editLayer07"){ //최상부
			$('input[name=uploc]').val('');
			
		}else if(btnValue == "editLayer08"){ //대피지점
			$('input[name=evadata]').val('');
			
		}
		
		$('.landDel-btn-m').val('');
		$('.deletBtnArea').css("display", "none");
	});
	
	// 새로운 좌표 입력
	$("#statTable").on("click", "#addGeom", function () {
		
		$(".land10-btn-m").removeClass("selected");
		
		var rowIndex = $(this).closest('tr').index()-1;
		
		SM.interaction.layer = SM.map.getLayer("editLayer10");
		SM.changeMapControl(CONFIG.MAPCONTROLTYPE.POINT);
		
		var tr = $(this).closest("tr");
		var tdMiddle = tr.find("td:eq(1)");
		
		SM.map.once('click', function (event) {
		      var coordinate = event.coordinate;
		      tdMiddle.empty();
		      $("input[name=lon" + rowIndex + "]").val(coordinate[0]);
		      $("input[name=lat" + rowIndex + "]").val(coordinate[1]);
		      tdMiddle.removeAttr("colspan");
		      
		      var x = coordinate[0] 
		      var y = coordinate[1] 
		      
		      var s_srs = new proj4.Proj("EPSG:5186");
		      var t_srs = new proj4.Proj("EPSG:4326");
		      
		      var pt = new proj4.Point(x,y);
		      var result =proj4.transform(s_srs,t_srs,pt);
		      
		      var convertLon = degree2Dms(result.x);
		      var convertLat = degree2Dms(result.y);
		      
		      var tdLongitude = $("<td>").text(convertLon+"E");
		      var tdLatitude = $("<td>").text(convertLat+"N");
		      
		      tdMiddle.after(tdLatitude, tdLongitude);
		      tdMiddle.remove();
		      
		      var coordinates = [];
		      for(var i=1; i<=rowIndex-1; i++){
		          // 좌표정보 재조립 (1~i번까지)
		          var lon = $("input[name=lon" + i + "]").val();
		          var lat = $("input[name=lat" + i + "]").val();
		          
		          if (lon != null && lon !== '' && lat != null && lat !== '') {
		              coordinates.push([lon, lat]);
		          }
		      }
		      coordinates.push([coordinate[0].toString(), coordinate[1].toString()]);

		      
		      var multiPoint = new ol.geom.MultiPoint(coordinates);
		      
		      var features = [];
		      
		      var pointStyles = [
		          {src : '/images/common/statMap1.png', scale: 0.25},
		          {src : '/images/common/statMap2.png', scale: 0.25},
		          {src : '/images/common/statMap3.png', scale: 0.25},
		          {src : '/images/common/statMap4.png', scale: 0.25},
		          {src : '/images/common/statMap5.png', scale: 0.25}
		      ]
		      
		      coordinates.forEach(function (coord, index) {
		          var lon = coord[0];
		          var lat = coord[1];

		          var style = new ol.style.Style({
			            image: new ol.style.Icon({
			              src: pointStyles[index].src,
			              scale: pointStyles[index].scale,
			            })
		          });

		          var pointFeature = new ol.Feature({
		            geometry: new ol.geom.Point([lon, lat]),
		          });

		          pointFeature.setStyle(style);
		          features.push(pointFeature);
		    });
		      
		    var vectorLayer = new ol.layer.Vector({
	          id:"editLayer10",
	          source: new ol.source.Vector({
	            features: features,
	          })
		    });
		    
		    SM.map.getLayer("editLayer10").getSource().clear();
		    
		    SM.map.addLayer(vectorLayer);
		    SM.changeMapControl(CONFIG.MAPCONTROLTYPE.MOVE);
	    });
    });
});

//공간정보 저장
function fnGeomSave(){
	 if (confirm("저장하시겠습니까?")) {
		 
		 var validCoordinates = true;
		 
		var vnaraPntFeature = SM.map.getLayer("svyDataLayer").getSource().getFeatures();//유출구
		var vnaraLneFeature = SM.map.getLayer("editLayer05").getSource().getFeatures();//대피로
		var vnaraPlgnFeature01 = SM.map.getLayer("editLayer01").getSource().getFeatures();//사방댐
		var vnaraPlgnFeature02 = SM.map.getLayer("editLayer02").getSource().getFeatures();//계류보전
		var vnaraPlgnFeature03 = SM.map.getLayer("editLayer03").getSource().getFeatures();//유역면적
		var vnaraPlgnFeature04 = SM.map.getLayer("editLayer04").getSource().getFeatures();//산지사방
		var mdllocFeature = SM.map.getLayer("editLayer06").getSource().getFeatures();//중간부
		var uplocFeature = SM.map.getLayer("editLayer07").getSource().getFeatures();//최상부
		var evadataFeature = SM.map.getLayer("editLayer08").getSource().getFeatures();//대피지점
		
		var statmapFeature = SM.map.getLayer("editLayer10").getSource().getFeatures();//현황도(멀티포인트)
		
		
		var format = new ol.format.WKT();
		var vnaraPntWkt = null, vnaraLneWkt = null, vnaraPlgnWkt01 = null, vnaraPlgnWkt02 = null, vnaraPlgnWkt03 = null, vnaraPlgnWkt04 = null;
		
		var mdllocWkt = null, uplocWkt = null, evadataWkt = null;
		
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
		
		if(uplocFeature.length > 0){
			uplocWkt = format.writeGeometry(uplocFeature[0].getGeometry());
			$("input[name=uploc]").val(uplocWkt);
		}else{
			$("input[name=uploc]").val("");
		}
		
		if(mdllocFeature.length > 0){
			mdllocWkt = format.writeGeometry(mdllocFeature[0].getGeometry());
			$("input[name=mdlloc]").val(mdllocWkt);
		}else{
			$("input[name=mdlloc]").val("");
		}
		
		if(evadataFeature.length > 0){
			evadataWkt = format.writeGeometry(evadataFeature[0].getGeometry());
			$("input[name=evadata]").val(evadataWkt);
		}else{
			$("input[name=evadata]").val("");
		}
		// 대피지점    "POINT\\s*\\(\\s*(\\d+\\.\\d+)\\s+(\\d+\\.\\d+)\\s*\\)";
		
		var evamapnm = $("input[name=evamapnm]").val();
		var evaPnt = $("input[name=evadata]").val();
		var evaRegex = /^POINT\(([\d\.]+)\s+([\d\.]+)\)$/;
		var evaMatches = evaPnt.match(evaRegex);
		if (evaMatches) {
			  var x = parseFloat(evaMatches[1]);
			  var y = parseFloat(evaMatches[2]);
			  var evaParam = {
					  "경도" : x,
					  "대피장소" : evamapnm,
					  "위도" : y
			  }
			  $("input[name=evamap]").val(JSON.stringify(evaParam));
		}
		
		
		if(statmapFeature.length > 0){
			var rowIndex = $("#statTable tr").length-2;
						
			var lon = $("input[name=lon"+rowIndex+"]").val();
			var lat = $("input[name=lat"+rowIndex+"]").val();
			
			if(lon == undefined && lon == '' && lat == undefined && lat == ''){
				$("input[name=lon"+rowIndex+"]").remove();
				$("input[name=length"+rowIndex+"]").remove();
				$("input[name=heigth"+rowIndex+"]").remove();
				$("input[name=lat"+rowIndex+"]").remove();
				$("input[name=spclNote"+rowIndex+"]").remove();	
			}
			
			var StatParam = [];
			$("#statTable tr.origin_stat").each(function () {
		        var tr = $(this);
		        var trleng = tr.find("td:first").text();
		        
		        var lon = tr.find("input[name^='lon']").val();
		        var length = tr.find("input[name^='length']").val();
		        var dept = tr.find("input[name^='dept']").val();
		        var heigth = tr.find("input[name^='heigth']").val();
		        var lat = tr.find("input[name^='lat']").val();
		        var spclNote = tr.find("input[name^='spclNote']").val();
		        
		        if(lon != '' && lon != null && lat != '' && lat != null){
		        	 var data = {
	        	            "경도": lon,
	        	            "길이": length,
	        	            "깊이": dept,
	        	            "높이": heigth,
	        	            "위도": lat,
	        	            "특이사항": spclNote
	        	        };
		        	 StatParam.push(data);
		        	 validCoordinates = true;
		        }else{
		        	alert("입력되지 않은 좌표가 있습니다.");
		        	validCoordinates = false;
		        }
		      });
			$("input[name=statmap]").val(JSON.stringify(StatParam));
		}else{
			$("input[name=statmap]").val("");
		}
		
		
		if (!validCoordinates) {
			return false;
        }

		$(".loading-div").show();
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/lss/wka/sct/updateWkaSvyComptUpdt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
	        		alert("저장되었습니다.");
	        		fnList();
	            } else {
	                alert("저장이 실패하였습니다.\n에러내용 : "+data.message);
	                $(".loading-img").hide();
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
	var form = $("<form></form>").attr("action","/sys/lss/wka/sct/selectWkaSvyComptList.do").attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}

//편입면적 계산하기
function fnCalcInArea() {
	
	var svyType = $("input[name=svyType").val();
	
	var vnaraPlgnFeature01 = SM.map.getLayer("editLayer01").getSource().getFeatures();//사방댐
	var vnaraPlgnFeature02 = SM.map.getLayer("editLayer02").getSource().getFeatures();//계류보전
	
	var vnaraPlgnFeature04 = SM.map.getLayer("editLayer04").getSource().getFeatures();//산지사방
	
	var format = new ol.format.WKT();
	
	var	vnaraPlgnWkt01 = null, vnaraPlgnWkt02 = null, vnaraPlgnWkt04 = null;
	
	if(svyType == '취약지역 실태조사(토석류)'){
		if(vnaraPlgnFeature01.length > 0){
			vnaraPlgnWkt01 = format.writeGeometry(vnaraPlgnFeature01[0].getGeometry());
			$("input[name=calcMntrntWkt]").val(vnaraPlgnWkt01);
		}
		if(vnaraPlgnFeature02.length > 0){
			vnaraPlgnWkt02 = format.writeGeometry(vnaraPlgnFeature02[0].getGeometry());
			$("input[name=calcLndslddgWkt]").val(vnaraPlgnWkt02);
		}
	}else{
		if(vnaraPlgnFeature04.length > 0){
			vnaraPlgnWkt04 = format.writeGeometry(vnaraPlgnFeature04[0].getGeometry());
			$("input[name=calcMtcecnrwktWkt]").val(vnaraPlgnWkt04);
		}
	}
	  if (confirm("면적을 계산하시겠습니까?")) {
		  
		  	$(".loading-div").show();
		  	
		  	$("input[name=calcInArea]").val("Y");
			$("#listForm").ajaxForm({
				type: 'POST',
		        url: "/sys/lss/wka/sct/updateWkaSvyComptUpdt.do",
		        dataType: "json",
		        success: function(data) {
		        	if (data.status == "success") {
		        		
		        		if(data.calcData !=null && data.calcData != ''){
		        			alert("계산이 완료되었습니다.");
		        		}else{
		        			$("#branchAreaTotal").text("0");
		        			$("#incorAreaTotal").text("0");
		        			alert("계산 결과가 없습니다.");
		        		}
		        		
		        		var lcmapHtml = '';
		        		var totalCadArea = 0;
		        		var totalTransferMap = 0;
		        		
		        		var lcmapArray = [];
		        		
		        		$("#totalCadArea").text('0');
        				$("#totalTransferMap").text('0');
        				
        				console.log($(".scoreLine").nextAll());
		        		
        				$(".scoreLine").nextAll().remove();
        				
        				var branchAreaTotal = data.branchAreaTotal;
    					var incorAreaTotal = data.incorAreaTotal;
        				
		        		if(data.calcData !=null && data.calcData != ''){
		        			console.log(data.calcData);
		        			$.each(data.calcData, function(index, value){		        				
		        				
								var sd			 = value.svySd;
								var sgg 		 = value.svySgg;
								var emd 		 = value.svyEmd;
								var li 			 = value.svyRi;
								var jibun		 = value.svyJibun;
								var jimok 		 = value.svyJimok;
								var branchArea 	 = value.branchArea;
								var incorArea 	 = value.incorArea;
								var sm 			 = value.smGeometry;
								var pnu 		 = value.pnuCode;
								var posesnSe	 = value.posesnSe;
		        				
		        				lcmapHtml += '<tr>'
	        					lcmapHtml += 	'<td>'+sd+'</td>'
	        					lcmapHtml += 	'<td>'+sgg+'</td>'
	        					lcmapHtml += 	'<td>'+emd+'</td>'
	        					lcmapHtml += 	'<td>'+li+'</td>'
	        					lcmapHtml += 	'<td>'+jibun+'</td>'
	        					lcmapHtml += 	'<td>'+jimok+'</td>'
	        					lcmapHtml += 	'<td>'+branchArea+'</td>'
	        					lcmapHtml +=	'<td>'+incorArea+'</td>'
	        					lcmapHtml +=	'<td>'+posesnSe+'</td>'
	        					lcmapHtml += '</tr>'
	        					
		        				// lcmap
		        					var param = {
	        							"시도" : sd,
	        							"시군구" : sgg,
	        							"읍면동" : emd,
	        							"리" : li,
	        							"지번" : jibun,
	        							"지목" : jimok,
	        							"지적면적" : branchArea,
	        							"편입면적" : incorArea,
	        							"WKT" : sm,
	        							"소유구분" : posesnSe
	        						}
	        					lcmapArray.push(param);
		        				
		        			})
		        		}
		        		
		        		$("#branchAreaTotal").text(branchAreaTotal);
		        		$("#incorAreaTotal").text(incorAreaTotal);
		        		
		        		$(".origin_lm").remove();
		        		$("#lcmapTable").append(lcmapHtml);
		        		
		        		$("input[name=lcmap]").val(JSON.stringify(lcmapArray));
		        		
		            } else {
		                alert("계산이 실패하였습니다.\n에러내용 : "+data.message);
		            }
		        	
		    },
		    error: function(data) {
		    	alert(data.message);
		    	$(".loading-div").hide();
		    },
		    complete: function(){
		    	$(".loading-div").hide();
		    	$("input[name=calcInArea]").val("N");
		    }
			}).submit();
      }
}


function fnStatBtn(e) {
	
	var rowIndex = $("#statTable tr").length-1;
	
	var lon = $("input[name=lon"+(rowIndex-1)+"]").val();
	var lat = $("input[name=lat"+(rowIndex-1)+"]").val();
	
	if(e == 'add'){
		if( rowIndex <= 5){
			
			if(lon != undefined && lon != '' && lat != undefined && lat != '' || rowIndex === 1){
				var addHtml = "";
					addHtml += '<tr class="origin_stat">';
					addHtml += 	'<td>'+rowIndex+'</td>';
					addHtml += 	'<td colspan="2"><button type="button" class="write-btn" id="addGeom" value="'+rowIndex+'">좌표 입력</button></td>';
					addHtml += 	'<td><input type="text" name="spclNote'+rowIndex+'" value=""/></td>';
					addHtml += 	'<td><button type="button" class="del-btn" onclick="javascript:fnStatBtn(this); return false;">삭제</button>';
					addHtml += 	'<input type="hidden" name="lon'+rowIndex+'" value=""/>';
					addHtml += 	'<input type="hidden" name="lat'+rowIndex+'" value=""/>';
					addHtml += 	'<input type="hidden" name="heigth'+rowIndex+'" value=""/>';
					addHtml += 	'<input type="hidden" name="length'+rowIndex+'" value=""/>';
					addHtml += 	'<input type="hidden" name="dept'+rowIndex+'" value=""/></td>';
					addHtml += '</tr>';
					
				$("#statTable").append(addHtml);
			    updateRowIndexes();
			}else{
				alert((rowIndex-1)+"번의 좌표가 입력되지 않았습니다.");
			}
		}else{
			alert("최대 5개까지 입력 가능합니다.");
		}
	}else{
		var tr = $(e).parent().parent();
		tr.remove();
		
		var oldNum = $(e).closest("tr").find("td:first").text();
		updateRowIndexes(oldNum);
		
		SM.map.getLayer("editLayer10").getSource().clear();
		
	    var coordinates = [];
	    for(var i=1; i<=rowIndex-1; i++){
	        // 좌표정보 재조립 (1~i번까지)
	        var lon = $("input[name=lon" + i + "]").val();
	        var lat = $("input[name=lat" + i + "]").val();
	        
	        if (lon != null && lon !== '' && lat != null && lat !== '') {
	            coordinates.push([lon, lat]);
	        }
	    }
	    
	    var multiPoint = new ol.geom.MultiPoint(coordinates);
	    
	    var features = [];
	    
	    var pointStyles = [
	        {src : '/images/common/statMap1.png', scale: 0.25},
	        {src : '/images/common/statMap2.png', scale: 0.25},
	        {src : '/images/common/statMap3.png', scale: 0.25},
	        {src : '/images/common/statMap4.png', scale: 0.25},
	        {src : '/images/common/statMap5.png', scale: 0.25}
	    ]
	    
	    coordinates.forEach(function (coord, index) {
	        var lon = coord[0];
	        var lat = coord[1];

	        var style = new ol.style.Style({
		          image: new ol.style.Icon({
		            src: pointStyles[index].src,
		            scale: pointStyles[index].scale,
		          })
	        });

	        var pointFeature = new ol.Feature({
	          geometry: new ol.geom.Point([lon, lat]),
	        });

	        pointFeature.setStyle(style);
	        features.push(pointFeature);
	    });
	      
	    var vectorLayer = new ol.layer.Vector({
          id:"editLayer10",
          source: new ol.source.Vector({
            features: features,
          })
	    });
	    
	    SM.map.addLayer(vectorLayer);
	    SM.changeMapControl(CONFIG.MAPCONTROLTYPE.MOVE);
	}
	
	function updateRowIndexes() {
		
		$("#statTable tr").each(function (index) {
			//연번
			$(this).find("td:first").text((index-1));
			
			//특이사항
			$(this).find("td:eq(-2) input[type='text']").each(function () {
			    var currentName = $(this).attr("name");
			    var newName = currentName.replace(/\d+$/, index-1);
			    $(this).attr("name", newName);
			  });
			// 삭제td 속 hidden
			$(this).find("input[type='hidden']").each(function () {
				var currentName = $(this).attr("name");
			    var newName = currentName.replace(/\d+$/, index-1);
			    $(this).attr("name", newName);
			 });
		});
	}
}



function degree2Dms(coord) {
	var dms;
	if(coord !="") {
        if(!isNaN(coord)){
        	coord = Number(coord);
            var d = Math.floor(coord);
            var m = Math.floor((coord - d)*60);
            var s = ( ((coord - d)*60) - Math.floor((coord - d)*60) ) *60;
            
            dms = d+"°"+m+"'"+s.toFixed(2)+"\"";
        }
	}
	return dms;
}
