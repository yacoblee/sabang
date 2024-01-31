/**
 * 지도 설정정보 스크립트
 * 
 * @module MAPCONFIG
 */

/*
 * proj4 정의
 */
if(proj4 != undefined){
	proj4.defs("EPSG:2096", "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"); //Bassel East
	proj4.defs("EPSG:2097", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"); //Bassel Middle
	proj4.defs("EPSG:2098", "+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"); //Bassel West
//	proj4.defs("EPSG:3857","+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs");
	proj4.defs("EPSG:4004", "+proj=longlat +ellps=bessel +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"); //Bassel 1841
	proj4.defs("EPSG:4019", "+proj=longlat +ellps=GRS80 +no_defs"); //GRS80
	proj4.defs("EPSG:5174", "+proj=+proj=tmerc +lat_0=38 +lon_0=127.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs");
	proj4.defs("EPSG:5179", "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs"); //UTM-K 
	proj4.defs("EPSG:5185", "+proj=tmerc +lat_0=38 +lon_0=125 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"); //GRS80 West y:60000 
	proj4.defs("EPSG:5186", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"); //GRS80 Middle y:60000
	proj4.defs("EPSG:5187", "+proj=tmerc +lat_0=38 +lon_0=129 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"); //GRS80 East y:60000
	proj4.defs("EPSG:5188", "+proj=tmerc +lat_0=38 +lon_0=131 +k=1 +x_0=200000 +y_0=600000 +ellps=GRS80 +units=m +no_defs"); //GRS80 EastSea y:60000
	proj4.defs("EPSG:5181", "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs"); 
	proj4.defs("NGI60:MIDDLE", "+proj=tmerc +title=KOREA_CENTER_TM_3PARAM +lat_0=38.0 +lon_0=127.0028902777777777776 +x_0=200000.0 +y_0=600000.0 +k=1.0 +a=6377397.155 +b=6356078.9633422494 +towgs84=-146.43,507.89,681.46,0,0,0,0");
} else{
	console.log("[Error] not define openlayers 'proj4' Object.");
};

ol.proj.proj4.register(proj4);
//EXTENT: [13107137.135992318, 3750162.8848523116, 15443052.720387306, 4883876.8883780455]  epsg:5186

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
//		CENTER: [ 126.52149237499951, 35.852395 ],//[128.2353595624997, 36.119086495126226],//[ 126.52149237499951, 35.852395 ],//[1067638.966,1772847.6319],
//		ZOOM: 7,
//		CONTROLMODE: 1,
//		GETRESOLUTIONS: function (len){
//			var res = [];
//			for (var i = 0; i < len; i++) {
//				res.push(2048 / Math.pow(2, i));
//			}
//			return res;
//		},
//		MAPCONTROLTYPE: {
//			MOVE: 1,
//			DISTANCE: 2,
//			AREA: 3
//		}
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
	CENTER: [ 126.52149237499951, 35.852395 ],//[128.2353595624997, 36.119086495126226],//[ 126.52149237499951, 35.852395 ],//[1067638.966,1772847.6319],
	ZOOM: 7,
	CONTROLMODE: 1,
	GETRESOLUTIONS: function (len){
		var res = [];
		for (var i = 0; i < len; i++) {
			res.push(2048 / Math.pow(2, i));
		}
		return res;
	},
	MAPCONTROLTYPE: {
		MOVE: 1,
		DISTANCE: 2,
		AREA: 3
	}
};

CONFIG.URLS = {
	VWORLD: {
		base: CONFIG.DOMAIN.vworld.concat("/req/wmts/1.0.0/").concat(CONFIG.KEYS.vworld,"/Base/{z}/{y}/{x}.png"),
		satellite: CONFIG.DOMAIN.vworld.concat("/req/wmts/1.0.0/").concat(CONFIG.KEYS.vworld,"/Satellite/{z}/{y}/{x}.jpeg"),
		hybrid: CONFIG.DOMAIN.vworld.concat("/req/wmts/1.0.0/").concat(CONFIG.KEYS.vworld,"/Hybrid/{z}/{y}/{x}.png"),
		wms: CONFIG.DOMAIN.vworld.concat("/req/wms"),
		search: CONFIG.DOMAIN.vworld.concat("/req/search")
	},
	SUPERMAP: {
		REST: CONFIG.DOMAIN.supermap.concat("/iserver/services/map-feis-server/rest/maps"),
		DATA: CONFIG.DOMAIN.supermap.concat("/iserver/services/data-feis-server/rest/data")
	},
//,
//	SUPERMAP: {
//		basic:"http://220.78.192.3:8090/iserver/services/map-sabangMap/rest/maps/comtsyslssbsc_stripland",
//		ctprvn:"http://220.78.192.3:8090/iserver/services/map-sabangMap/rest/maps/tf_feis_ctprvn"
//	}
};

CONFIG.VIEW = {
	projection: ol.proj.get("EPSG:5186"),
	//resolutions: CONFIG.GETRESOLUTIONS(15),
	//extent: CONFIG.EXTENT,
	//zoomFactor: 1,
	center: ol.proj.fromLonLat(CONFIG.CENTER, "EPSG:5186"),//ol.proj.transform(CONFIG.CENTER, 'EPSG:4326', 'EPSG:5186'),
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
				visible: true,
				type: "base",
				source: new ol.source.XYZ({
					url: CONFIG.URLS.VWORLD.base,
					crossOrigin: "anonymous"
				})
			}),
			new ol.layer.Tile({
				title: "위성지도",
				id: "satellite",
				visible: false,
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
	JIJUK: new ol.layer.Tile({
		title: "지적도",
		id: "jijuk",
		visible: false,
		source: new ol.source.TileWMS({
			url: CONFIG.URLS.VWORLD.wms,
			params: {
				SERVICE: "WMS",
				REQUEST: "GetMap",
				VERSION: "1.3.0",
				LAYERS: "lp_pa_cbnd_bubun,lp_pa_cbnd_bonbun",
				STYLES: "lp_pa_cbnd_bubun,lp_pa_cbnd_bonbun",
				CRS: "EPSG:5186",
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
	}),
	SUPERMAP: [
		new ol.layer.Tile({
			title: "임상도(1:5000)-수종별",
			id: "fs_im5000_koftr",
			visible: true,
			source: new ol.source.TileSuperMapRest({
		        url: CONFIG.DOMAIN.supermap.concat("/","iserver/services/map-ugcv5-imsangkoftr/rest/maps/imsang_koftr"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "임상도(1:5000)-경급별",
			id: "fs_im5000_dmcls",
			visible: true,
			source: new ol.source.TileSuperMapRest({
		        url: CONFIG.DOMAIN.supermap.concat("/","iserver/services/map-ugcv5-imsangdmcls/rest/maps/imsang_dmcls"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "임상도(1:5000)-임상별",
			id: "fs_im5000_frtp",
			visible: true,
			source: new ol.source.TileSuperMapRest({
				url: CONFIG.DOMAIN.supermap.concat("/","iserver/services/map-ugcv5-imsangfrtp/rest/maps/imsang_frtp"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "임상도(1:5000)-밀도별",
			id: "fs_im5000_dnst",
			visible: true,
			source: new ol.source.TileSuperMapRest({
				url: CONFIG.DOMAIN.supermap.concat("/","iserver/services/map-ugcv5-imsangdnst/rest/maps/imsang_dnst"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "임상도(1:5000)-영급별",
			id: "fs_im5000_agcls",
			visible: true,
			source: new ol.source.TileSuperMapRest({
				url: CONFIG.DOMAIN.supermap.concat("/","iserver/services/map-ugcv5-imsangagcls/rest/maps/imsang_agcls"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "시도행정경계",
			id: "ctprvn",
			visible: true,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","ctprvn"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "하천",
			id: "river",
			visible: true,
			minZoom:15,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","river"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "산사태위험지도",
			id: "landslide",
			visible: true,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","landslide"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "생태자연도",
			id: "nature",
			visible: true,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","eclgy_nature"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "도로",
			id: "road",
			visible: true,
			minZoom:15,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","road"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "등고선",
			id: "ctrln",
			visible: true,
			minZoom:15,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","ctrln"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "건물",
			id: "building",
			visible: true,
			minZoom:15,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","building"),
		        wrapX: true,
		    	tileGrid: new ol.tilegrid.TileGrid({
					extent: CONFIG.EXTENT,
					origin: [CONFIG.EXTENT[0], CONFIG.EXTENT[1]],
					resolutions: CONFIG.GETRESOLUTIONS(15)
				}),
				crossOrigin: "anonymous",
				tileProxy: "/cmm/proxy.do?url="
		    })
		}),
		new ol.layer.Tile({
			title: "임도망도",
			id: "forestload",
			visible: true,
		    source: new ol.source.TileSuperMapRest({
		        url: CONFIG.URLS.SUPERMAP.REST.concat("/","forestload"),
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
			title: '기초조사완료지바로가기',
			id: 'bscSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '외관점검완료지바로가기',
			id: 'fckAprcomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '땅밀림실태조사완료지바로가기',
			id: 'lcpSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '취약지역실태조사완료지바로가기',
			id: 'wkaSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '취약지역해제조사완료지바로가기',
			id: 'cnlSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '임도타당성평가완료지바로가기',
			id: 'frdSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '계측장비완료지바로가기',
			id: 'mseSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		}),
		new ol.layer.Vector({
			title: '정밀점검완료지바로가기',
			id: 'pcsSvycomptLink',
			zIndex: 9,
			source: new ol.source.Vector({
				format: new ol.format.GeoJSON()
			}),
			style: new ol.style.Style({
			     image: new ol.style.Circle({
			         fill: new ol.style.Fill({
			        	 color: 'rgba(255,0,0,0.8)'
			         }),
			         stroke: new ol.style.Stroke({
			        	 color: '#3399CC',
			        	 width: 1.25
			         }),
			         radius: 10
			     }),
			})
		})
	]
};