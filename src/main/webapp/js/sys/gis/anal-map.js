/**
 * 지도 메인 스크립트
 * 
 * @module SM
 */

/*
 * openlayers getLayer 커스텀함수 
 */
ol.Map.prototype.getLayer = ol.Map.prototype.getLayer || function (id) {var layer;this.getLayerGroup().getLayersArray().forEach(function (lyr) {if (id == lyr.get('id')) {layer = lyr;return false;}});return layer;};


var SM = {
	map: null,
	draw:{
		layer: null,
		source: null,
		interaction: null,	
		feature: null,
		helpTooltip: null,
		measureTooltip: null,
		helpOverlay: null,
		measureOverlay: null
	},
	snap:{
		layer: null,
		source: null,
		interaction: null,
		list:[]
	},
	resultPointLayer: null,
	resultPolyLineLayer: null,
	resultPolygonLayer: null,
	ecrtcnlLayer: null,
	mntnTrntLayer: null,
	cadastralLayer: null,
	watershedLayer: null,
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
			interactions: ol.interaction.defaults({ doubleClickZoom: false }),
			view: new ol.View(CONFIG.VIEW),
			layers: [CONFIG.LAYERS.BASEGROUP]
		});
		this.map.on("pointermove",this.pointerMoveHandler);
		this.map.getView().on("change:resolution",this.changeResolution);
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
	home: function(){
		this.map.getView().animate({center:ol.proj.transform(CONFIG.CENTER, 'EPSG:4326', 'EPSG:5186'),zoom:CONFIG.ZOOM,duration:800})
	},
	changeResolution:function(event){
		console.log(event);
	},
	changeMapControl: function(type){
		CONFIG.CONTROLMODE = type;
		//this.drawClear();
		this.map.removeInteraction(this.draw.interaction);
		switch(type){
			case CONFIG.MAPCONTROLTYPE.MOVE:
				this.map.removeInteraction(this.draw.interaction);
				//this.draw.layer.getSource().clear();
				//this.hideTooltip();
				break;
			case CONFIG.MAPCONTROLTYPE.POINT:
				if(this.ecrtcnlLayer == null){
					this.ecrtcnlLayer = new ol.layer.Vector({
						source: new ol.source.Vector(),
						style: new ol.style.Style({
							fill: new ol.style.Fill({
								color: "rgba(170,0,0,0.5)"
							}),
							stroke: new ol.style.Stroke({
								color: "#aa0000",
								width: 3
							}),
							image: new ol.style.Circle({
								radius: 7,
								fill: new ol.style.Fill({
									color: "#aa0000"
								}),
								stroke: new ol.style.Stroke({
									color: "#000000",
									width: 1
								})
							})
						})
					});
					this.map.addLayer(this.ecrtcnlLayer);
				}
				this.draw.layer = this.ecrtcnlLayer;
				
				this.addInteraction("Point");
				break;
			case CONFIG.MAPCONTROLTYPE.POLYLINE:
				if(this.mntnTrntLayer == null){
					this.mntnTrntLayer = new ol.layer.Vector({
						source: new ol.source.Vector(),
						style: new ol.style.Style({
							fill: new ol.style.Fill({
								color: "rgba(170,0,0,0.5)"
							}),
							stroke: new ol.style.Stroke({
								color: "#aa0000",
								width: 3,
								lineDash: [1, 5],
								lineDashOffset:5
							}),
							image: new ol.style.Circle({
								radius: 7,
								fill: new ol.style.Fill({
									color: "#ffcc33"
								})
							})
						})
					});
					this.map.addLayer(this.mntnTrntLayer);
				}
				this.draw.layer = this.mntnTrntLayer;
				this.addInteraction("LineString");
				break;
			case CONFIG.MAPCONTROLTYPE.POLYGON:
				if(this.cadastralLayer == null){
					this.cadastralLayer = new ol.layer.Vector({
						source: new ol.source.Vector(),
						style: new ol.style.Style({
							fill: new ol.style.Fill({
								color: "rgba(255, 255, 255, 0.5)"
							}),
							stroke: new ol.style.Stroke({
								color: "rgba(170, 0, 0, 0.7)",
								width: 2
							}),
							image: new ol.style.Circle({
								radius: 7,
								fill: new ol.style.Fill({
									color: "#ffcc33"
								})
							})
						})
					});
					this.map.addLayer(this.cadastralLayer);
				}
				this.draw.layer = this.cadastralLayer;
				this.addInteraction("Polygon");
				break;
			case CONFIG.MAPCONTROLTYPE.WATERSHED:
				if(this.watershedLayer == null){
					this.watershedLayer = new ol.layer.Vector({
						source: new ol.source.Vector(),
						style: new ol.style.Style({
							fill: new ol.style.Fill({
								color: "rgba(255, 255, 255, 0.5)"
							}),
							stroke: new ol.style.Stroke({
								color: "rgba(0, 112, 192, 0.7)",
								width: 2
							}),
							image: new ol.style.Circle({
								radius: 7,
								fill: new ol.style.Fill({
									color: "#ffcc33"
								})
							})
						})
					});
					this.map.addLayer(this.watershedLayer);
				}
				this.draw.layer = this.watershedLayer;
				this.addInteraction("Polygon");
				break;
			default:
				break;
		}
	},
	addInteraction: function(type){
		var $this = this;
		var eventListener = null;
//		this.draw.source = new ol.source.Vector();
//		this.draw.layer = new ol.layer.Vector({
//			source: this.draw.source,
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
//					fill: new ol.style.Fill({
//						color: "#ffcc33"
//					})
//				})
//			})
//		});
//		
//		this.map.addLayer(this.draw.layer);
		
		this.draw.interaction = new ol.interaction.Draw({
			source: this.draw.layer.getSource(),
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
						color: (CONFIG.CONTROLMODE == 5 ? "rgba(0, 112, 192, 0.7)":"rgba(170, 0, 0, 0.7)")
					}),
					fill: new ol.style.Fill({
						color: "rgba(255, 255, 255, 0.2)"
					})
				})
			})
		});
		
		this.map.addInteraction(this.draw.interaction);
		
//		for(let snap of SM.snap.source){
//			this.addSnap(snap);
//		};
		
		if(SM.snap.list.length > 0){
			this.addSnap(SM.snap.source);
		}
		
		this.draw.interaction.on("drawstart",function(e){
			$this.draw.layer.getSource().clear();
		});
		
		this.draw.interaction.on("drawend",function(e){
			$this.map.removeInteraction($this.draw.interaction);
		});
		
	},
	addSnap: function(source){
		this.snap.interaction = new ol.interaction.Snap({
			source:source,
			pixelTolerance: 20
		});
		
		this.map.addInteraction(this.snap.interaction);
	},
	getLength: function(geom){
		var length = ol.sphere.getLength(geom);
		var output;

		if (length > 100) {
			output = (Math.round(length / 1000 * 100) / 100) + ' km';
		} else {
			output = (Math.round(length * 100) / 100) + ' m';
		}

		return output;
	},
	getArea: function(geom){
		var area = ol.sphere.getArea(geom);
		var output;

		if (area > 10000) {
			output = (Math.round(area / 1000000 * 100) / 100) + ' km<sup>2</sup>';
		} else {
			output = (Math.round(area * 100) / 100) + ' m<sup>2</sup>';
		}

		return output;
	},
	showTooltip: function(type){
		if(type == "help"){
			if (this.draw.helpTooltip && this.draw.helpTooltip.parentNode) {
				this.draw.helpTooltip.parentNode.removeChild(this.draw.helpTooltip);
			}

			this.draw.helpTooltip = document.createElement('div');
			this.draw.helpTooltip.className = 'tooltip hidden';

			this.draw.helpOverlay = new ol.Overlay({
				element: this.draw.helpTooltip,
				offset: [15, 0],
				positioning: 'center-left'
			});

			this.map.addOverlay(this.draw.helpOverlay);
		}else if(type == "measure"){
			if (this.draw.measureTooltip && this.draw.measureTooltip.parentNode) {
				this.draw.measureTooltip.parentNode.removeChild(this.draw.measureTooltip);
			}

			this.draw.measureTooltip = document.createElement('div');
			this.draw.measureTooltip.className = 'tooltip tooltip-measure';

			this.draw.measureOverlay = new ol.Overlay({
				element: this.draw.measureTooltip,
				offset: [0, -15],
				positioning: 'bottom-center'
			});

			this.map.addOverlay(this.draw.measureOverlay);
		}
	},
	hideTooltip: function(){
		var $this = this;
		this.map.getOverlays().getArray().slice(0).forEach(function(overlay){
			$this.map.removeOverlay(overlay);
		});
	},
	drawClear: function(){
		(this.draw.layer !== undefined && this.draw.layer !== null) ? (this.map.removeInteraction(this.draw.interaction),this.draw.layer.getSource().clear(),this.hideTooltip()) : null;
	},
	addLayer: function(id){
		var layer = null;
		$.each(CONFIG.LAYERS.SUPERMAP,function(idx,elm){
			if(id == elm.get("id")){
				return layer = elm;
			}
		});
		this.map.addLayer(layer);
	},
	createLayer: function(id){
		var title = $("input[value=".concat(id,"]")).text();
		var layer = new ol.layer.VectorTile({
			title: title,
			id: id,
			visible: true,
		    source: new ol.source.VectorTileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.concat("/",id),
		        wrapX: true,
		        projection: new ol.proj.Projection({
		    		code: 'EPSG:5186',
		    		extent: CONFIG.EXTENT,
		    		units: 'm'
		    	}),
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				tileType: "ScaleXY",
                format:  new ol.format.MVT({
                    featureClass: ol.Feature,
                    defaultDataProjection: new ol.proj.Projection({
                        code: 'EPSG:5186',
                        units: ol.proj.Units.TILE_PIXELS
                    })
                })
		    })
		})
		this.map.addLayer(layer);
	},
	removeLayer: function(id){
		var layer = this.map.getLayer(id);
		if(layer != undefined && layer != null){
			this.map.removeLayer(layer);
		}
	},
	pointerMoveHandler: function(e){
		$("#map").focus();
		if (e.dragging) {return;}

		var message = "지도를 클릭하여 측정을 시작하세요.";

		if (SM.draw.feature) {
			var geometry = SM.draw.feature.getGeometry();

			if (geometry instanceof ol.geom.Polygon) {
				message = "면적 측정을 계속하려면 마우스를 움직이십시오.";
			} else if (geometry instanceof ol.geom.LineString) {
				message = "거리 측정으을 계속하려면 클릭하십시오.";
			}
		}

		if (SM.draw.helpTooltip) {
			SM.draw.helpTooltip.innerHTML = "<div style=\"padding:3px;background-color:#fff;\">".concat(message,"</div>");
			SM.draw.helpTooltip.classList.remove('hidden');
		}

		if (SM.draw.helpOverlay) {
			SM.draw.helpOverlay.setPosition(e.coordinate);
		}
	},
	wholeMoveToPoint: function(x,y){
		if(this.map.getLayer("whole") == undefined){
			var source = new ol.source.Vector({ wrapX: false });
			var vLayer = new ol.layer.Vector({id:"whole",title:"통합검색레이어", source: source });
			this.map.addLayer(vLayer);
		}
		this.map.getLayer("whole").getSource().clear();
		
		var iconFeature = new ol.Feature({
			  name: 'AddressMarker',
			  geometry: new ol.geom.Point([parseFloat(x), parseFloat(y)])
			});

			var iconStyle = new ol.style.Style({
			  image: new ol.style.Icon({
			    anchor: [0.5, 46],
			    anchorXUnits: 'fraction',
			    anchorYUnits: 'pixels',
			    src: '/images/sub/marker.png'
			  })
			});
		
		iconFeature.setStyle(iconStyle);
		this.map.getLayer("whole").getSource().addFeature(iconFeature);
		this.map.getView().setCenter([parseFloat(x), parseFloat(y)]);
		this.map.getView().setZoom(18);
	},
	getFeaturesByIDs: function(url,param){
		var $map = this.map;
		var idsParam = new SuperMap.GetFeaturesByIDsParameters(param);
		// Send a request to the server and get the result
		new ol.supermap.FeatureService(url,{proxy:CONFIG.PROXY.concat("?")}).getFeaturesByIDs(idsParam, function (serviceResult) {
		    var features = serviceResult.result.features;
		    
		    
		    var format = new ol.format.GeoJSON();
		    
//		    var newformat = new ol.format.GeoJSON({
//		    	dataProjection: ol.proj.get('EPSG:3857'),
//		        featureProjection: ol.proj.get('EPSG:4326')
//		    });
		    
		    
		    var readfeatures = format.readFeatures(features);
//		    var nextfeatures = newformat.readFeatures(newfeatures);
		    
		    
		    //(new ol.format.GeoJSON()).readFeatures(newfeatures)
		    var vectorSource = new ol.source.Vector({
                features: readfeatures,
                wrapX: false
            });
            var resultLayer = new ol.layer.Vector({
                source: vectorSource
            });
            $map.addLayer(resultLayer);
            $map.getView().fit(vectorSource.getExtent());
            //vectorSource.getExtent()
		});
	},
	getFeatureByGeometry: function (id, url, param) {
	    var getFeaturesByGeometryParams = new SuperMap.GetFeaturesByGeometryParameters(param);

	    new ol.supermap.FeatureService(url,{proxy:CONFIG.PROXY.concat("?")}).getFeaturesByGeometry(getFeaturesByGeometryParams, function (response) {
	        if (!response.result) { return; }
	        
	        if(response.result.totalCount > 0 && response.result.succeed) {
	        	var feature = new ol.format.GeoJSON().readFeatures(response.result.features);
	        	//feature[0].getGeometry().transform('EPSG:5186', 'EPSG:3857');
	        	
	        	var id = "popup-";
	        	var coordinate = ol.extent.getCenter(feature[0].getGeometry().getExtent());
	        	var properties = feature[0].getProperties(); // 프로퍼티
	        	var content = "";
	        	var options = { type: null, text: null, callback: null };
	        	
	        	if(properties.hasOwnProperty('SVY_MEMO')){
	        		id += "bscSvycompt"; 
	        		content = fnCreateSvycomptPopupContent(JSON.parse(properties.SVY_MEMO));
	        		options = { type: 'search', text: '상세보기', callback: fnOpenDetailInfo };
	        	}
	        	else {
	        		id += "bscStripland";
	        		content = fnCreateStriplandPopupContent(properties);
	        	}
	        	
	        	fnOpenOverlay(id, coordinate, content, options);
	        }
	    });
	},
	queryRasterInfo: function(url,param){
	    if (param.X < -180.0 || param.X > 180.0 || param.Y < -90 || param.Y > 90) {
	        return;
	    }
	    // Set the parameter information of the raster query
	    var getGridCellInfosParam = new SuperMap.GetGridCellInfosParameters(param);
	    // Create a raster query instance
	    new ol.supermap.GridCellInfosService(url,{proxy:CONFIG.PROXY.concat("?")}).getGridCellInfos(getGridCellInfosParam, function (serviceResult) {
	        if (!serviceResult.result) {
	            return;
	        }
	        // Get the data returned by the server
	        var result = serviceResult.result;
	    });
		
	},
	queryFieldInfo: function(url,param){
		var params = new SuperMap.FieldParameters(param);
		
		
	},
	clear: function(){
		this.drawClear();
		this.removeOverlays();
//		var $removeLayers = [];
		SM.map.getLayers().forEach(function(layer, index){
			if(layer instanceof ol.layer.Vector){
				layer.getSource().clear();
//				$removeLayers.push(layer);
			}
		});
		
//		$.each($removeLayers, function(idx,elm){
//			SM.map.removeLayer(elm);
//		});
	},
	// 오버레이 팝업 엘리먼트 생성
	createOverlay: function (id, type, text, listener) {
		
		if(!this.map.getOverlayById(id)) {
			// 팝업 엘리먼트
			var popup = $('<div id=' + id + ' class="ol-popup wd" style="display:none;"></div>');
			// 컨텐츠 엘리먼트
			var content = $('<div class="ol-popup-content"></div>');
			// 버튼 그룹 엘리먼트
			var footer = $('<div class="ol-popup-footer"></div>');
			
			// 닫기 외 추가 버튼 필요한 경우 
			if(listener && text) {
				
				var btnClass = "search-btn";
				
				if(type == 'search') btnClass = "search-btn";
				else if(type == 'upload') btnClass = "upload-btn";	
				else if(type == 'download') btnClass = "download-btn";
				else if(type == 'add') btnClass = "addd-btn";
				else if(type == 'delete') btnClass = "del-btn";
				else if(type == 'reset') btnClass = "reset-btn";
				else if(type == 'modify') btnClass = "modify-btn";
				else if(type == 'back') btnClass = "back-btn";
				else btnClass = "list-btn";
				
				var info = $('<button class="' + btnClass + '">' + text + '</button>');
				$(info).on('click', listener);
				$(footer).append(info);
			}
			// 닫기 버튼
			var closer = $('<button class="close-btn" onclick="fnCloseOverlay(event)">닫기</button>');
			$(footer).append(closer);
			
			$(popup).append(content).append(footer);
			$(this.map.getTargetElement()).append(popup);
			
			SM.map.addOverlay(new ol.Overlay({
				id: id,
				element: document.getElementById(id),
				autoPan: true,
				autoPanAnimation: { duration: 250 }
			}));
		}
	},
	// 오버레이 삭제
	removeOverlays: function () {
		for(var n in this.map.getOverlays().getArray()) {
			var popup = this.map.getOverlays().getArray()[n];
			var element = popup.getElement();
			this.map.removeOverlay(popup);
			$(element).remove();
		}
	},
	// 오버레이 삭제
	removeOverlay: function (id) {
		var popup = this.map.getOverlayById(id);
		var element = popup.getElement();
		this.map.removeOverlay(popup);
		$(element).remove();
	},
	// vector layer selected feature
	addSelectFeature: function (id, callback) {
		
		var layer = this.map.getLayer(id);
		var features = [];

		SM.map.getInteractions().getArray().filter(function(d) {return d.get("id") == id})
		
		var d = this.map.getInteractions().getArray().filter(function (d) { return d instanceof ol.interaction.Select; });
		
		if(d.length > 0) this.map.removeInteraction(d[0]);
		
		var select = new ol.interaction.Select({
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: '#ffffff'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#ff0000',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			}),
			layers: [layer],
		});
		
		select.setProperties({"id": id});
		this.map.addInteraction(select);
		select.on('select', callback);
	},
	// vector layer select remove feature
	removeSelectFeature: function () {
		var d = this.map.getInteractions().getArray().filter(function (d) { return d instanceof ol.interaction.Select; });
		if(d.length > 0) this.map.removeInteraction(d[0]);
	},
	// vector layer select clear feature
	clearSelectFeature: function () {
		this.map.getInteractions().getArray().forEach(function (d) {
			if(d instanceof ol.interaction.Select) d.getFeatures().clear();
		});
	},
	// on singleclick event
	onSingleClick: function (callback) {
		this.map.on('singleclick', callback)
	},
	// un singleclick event	
	unSingleClick: function () {
		for(var n in this.map.getListeners('singleclick')) {
			this.eventListener.singleclick.push(this.map.getListeners('singleclick')[n]);
			this.map.un('singleclick',this.map.getListeners('singleclick')[n]);
		}
	},
	reSingleClick: function () {
		for(var n in this.eventListener.singleclick) {
			this.map.on('singleclick',this.eventListener.singleclick[n]);
		}
	},
	changeBbox: function(src){
		var urls = src.split('&');
        var bboxstr = urls.pop();
        var bbox = bboxstr.split('=');
        
        bbox = bbox[1].split('%2C');
        urls.push('BBOX='+bbox[1]+'%2C'+bbox[0]+'%2C'+bbox[3]+'%2C'+bbox[2]);
        
        return urls.join('&');
	},
	zoomToQueryLayer: function(url,params,snaps){
		var layerId = params.queryParameter.name.split("@")[0];
		var sqlParam = new SuperMap.GetFeaturesBySQLParameters(params);
		
		new ol.supermap.FeatureService(url,{proxy:CONFIG.PROXY.concat("?")}).getFeaturesBySQL(sqlParam, function (serviceResult) {
			var features = serviceResult.result.features;
			var format = new ol.format.GeoJSON();
			var readfeatures = format.readFeatures(features);
//			var originSource = new ol.source.Vector({features: readfeatures});
			
			var vectorSource = new ol.source.Vector({
				projection:"EPSG:5186",
                features: readfeatures,
                wrapX: false
            });
            var resultLayer = new ol.layer.Vector({
            	id:layerId,
                source: vectorSource
            });
            SM.map.addLayer(resultLayer);
            
            SM.map.getView().fit(vectorSource.getExtent());
            
            if(snaps != null && snaps.length > 0){
            	var boundParam = {datasetNames: snaps,bounds:[vectorSource.getExtent()[0]-500,vectorSource.getExtent()[1]-500,vectorSource.getExtent()[2]+500,vectorSource.getExtent()[3]+500],spatialQueryMode:"INTERSECT"};
        		SM.boundToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,boundParam);
            }
        }); 
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
	},
	getQueryLayer: function(url,params,cleared){
		if(url != null && params != null){
			var sqlParam = new SuperMap.GetFeaturesBySQLParameters(params); 
			new ol.supermap.FeatureService(url,{proxy:CONFIG.PROXY.concat("?")}).getFeaturesBySQL(sqlParam, function (serviceResult) {
				var features = serviceResult.result.features;
				if(features.features.length > 0){
					var format = new ol.format.GeoJSON();
					var readfeatures = format.readFeatures(features);
					var targetLayer = null;
					var type = readfeatures[0].getGeometry().getType();
					
					var styleObj = {
		    				fill: new ol.style.Fill({
		    					color: "rgba(170,0,0,0.1)"
		    				}),
		    				stroke: new ol.style.Stroke({
		    					color: "#C00000",
		    					width: 3
		    				}),
		    				image: new ol.style.Circle({
		    					radius: 7,
		    					fill: new ol.style.Fill({
		    						color: "#0070C0"
		    					}),
		    					stroke: new ol.style.Stroke({
			    					color: "#002060",
			    					width: 1
			    				})
		    				})
		    			};
					
					if(type == "Point"){
						if(SM.resultPointLayer == null || SM.resultPointLayer == undefined){
							var vectorSource = new ol.source.Vector({projection:"EPSG:5186",features: readfeatures,wrapX: false});
							
							SM.resultPointLayer = new ol.layer.Vector({
				                source: vectorSource,
				                style: new ol.style.Style(styleObj)
				            });
				            
				            SM.map.addLayer(SM.resultPointLayer);
						}else{
							if(cleared){
								SM.resultPointLayer.getSource().clear();
							}
							
							for (var i = 0; i < readfeatures.length; i++) {
								SM.resultPointLayer.getSource().addFeature(readfeatures[i]);
							}
						}
					}else if(type == "MultiPolygon"){
						if(SM.resultPolygonLayer == null || SM.resultPolygonLayer == undefined){
							var vectorSource = new ol.source.Vector({projection:"EPSG:5186",features: readfeatures,wrapX: false});
							
							SM.resultPolygonLayer = new ol.layer.Vector({
				                source: vectorSource,
				                style: new ol.style.Style(styleObj)
				            });
				            
				            SM.map.addLayer(SM.resultPolygonLayer);
						}else{
							if(cleared){
								SM.resultPolygonLayer.getSource().clear();
							}
							
							for (var i = 0; i < readfeatures.length; i++) {
								SM.resultPolygonLayer.getSource().addFeature(readfeatures[i]);
							}
						}
					}else if(type == "LineString"){
						if(SM.resultPolyLineLayer == null || SM.resultPolyLineLayer == undefined){
							var vectorSource = new ol.source.Vector({projection:"EPSG:5186",features: readfeatures,wrapX: false});
							SM.resultPolyLineLayer = new ol.layer.Vector({
				                source: vectorSource,
				                style: new ol.style.Style(styleObj)
				            });
				            
				            SM.map.addLayer(SM.resultPolyLineLayer);
						}else{
							if(cleared){
								SM.resultPolyLineLayer.getSource().clear();
							}
							for (var i = 0; i < readfeatures.length; i++) {
								SM.resultPolyLineLayer.getSource().addFeature(readfeatures[i]);
							}
						}
					}
				}
	        });
		}
	}
};
