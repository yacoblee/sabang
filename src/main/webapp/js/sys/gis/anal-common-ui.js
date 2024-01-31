$(document).ready(function(){
	/**
	 * 타당성평가
	 */
	//레이어리스트 생성
	$.each(CONFIG.LAYERS.SUPERMAP,function(idx,el){
		//<input type="checkbox" id="river" name="layers" value="river"><label for="river">하천</label>
		var id = el.get("id");
		var title = el.get("title");
		
		var $div = $("<div></div>");
		var $visibleChk = $("<input type=\"checkbox\" id=\"".concat(id.replace("fs_","chk_")).concat("\" name=\"layers\" value=\"").concat(id.replace("fs_","")).concat("\">"));
		var $visibleLabel = $("<label for=\"".concat(id.replace("fs_","chk_")).concat("\">").concat(title).concat("</label>"));
		
		var $snapChk = $("<input type=\"checkbox\" id=\"".concat(id.replace("fs_","chk_snap_")).concat("\" name=\"snaps\">"));
		var $snapLabel = $("<label for=\"".concat(id.replace("fs_","chk_snap_")).concat("\"></label>"));
		//var $snapLabel = $("<label for=\"".concat(id.replace("fs_","chk_snap_")).concat("\" class=\"Hidden\">").concat(title).concat(" 스냅핑").concat("</label>"));
		
		$div.append($visibleChk);
		$div.append($visibleLabel);
		
		if(id.indexOf("fs") > -1){
			$div.append($snapChk);
			$div.append($snapLabel);
		}
		
		$("div.layers").append($div);
	});
	
	/* ******************************************
	 * 이벤트처리
	 ********************************************/
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
	
	//타당성평가 이벤트처리
	$("input[name=layers]").on("change",function(){
		var checked = $(this).is(":checked");
		var chkId = $(this).attr("id");
		var lyr = SM.map.getLayer(chkId.replace("chk_","fs_"));
		
		if(lyr != undefined && lyr != null){
			lyr.setVisible(checked);
		}
	});
	
	//타당성평가 레이어리스트 스냅핑 체크박스 이벤트
//	$(document).on("change","input[name=snaps]",function(){
//		var checked = $(this).is(":checked");
//		var chkId = $(this).attr("id");
//		var dsNm = chkId.replace("chk_snap","sabang:tf_feis");
//		if(checked){
//			SM.draw.snaps.push(dsNm);
//			var boundParam = {datasetNames: SM.draw.snaps,bounds:SM.map.getView().calculateExtent(),spatialQueryMode:"INTERSECT"};
//    		SM.boundToQueryLayer(CONFIG.URLS.SUPERMAP.DATA,boundParam);
//		}else{
//			
//		}
//	});
	$("input[name=snaps]").on("change",function(){
		var checked = $(this).is(":checked");
		var chkId = $(this).attr("id");
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