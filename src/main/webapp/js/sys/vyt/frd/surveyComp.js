$(document).ready(function() {
	window.$app = {};
	$app.upload = {};
	
	$('#svyId,#svyUser,#mstNm').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
	
	$(document).on({
    	"dragenter":function(e){
        	$(this).addClass("over");
        },
        "dragleave":function(e){
        	$(this).removeClass("over");
        },
        "dragover":function(e){
        	e.preventDefault();
        },
        "drop":function(e){
        	
        	
        	// 임도 노선 추가등록 시 노선 종류 미 선택 시 노선종류 안넘어감
        	var routeType = $("select[name=routetype]").val();
        	if(routeType == null || routeType == "" || routeType == undefined){
        		alert("노선종류 먼저 선택하세요.");
        		return false;
        	}
        	
//        	alert("droped!");
        	var files = e.originalEvent.dataTransfer.files;
        	if(files.length > 0){
        		var fd = new FormData($("#insertForm")[0]);
        		fd.append('file', files[0]);
        		//fd.append('svyType',$("#svyType").val());
        		var nm = files[0].name.split(".");
        		nm.pop();
        		var filenm = nm.join(".");
        		var size = files[0].size;
        		var sizeMb = size ? size/1024/1024 : 0;
        		
        		$app.upload.nm = nm;
        		$app.upload.size = size;
        		$app.upload.form = fd;
        		console.log("file name is ",filenm,", file size is ",size);
        		
        		var table_html = '<table summary="업로드할 파일의 정보를 출력합니다.">';
        		table_html += '<caption class="Hidden">업로드파일 목록</caption>';
        		table_html += '<colgroup>';
        		table_html += '<col style="width: 60%;">';
        		table_html += '<col style="width: 30%;">';
        		table_html += '<col style="width: 10%;">';
        		table_html += '</colgroup>';
        		table_html += '<thead>';
        		table_html += '<tr>';
        		table_html += '<th>파일명</th>';
        		table_html += '<th>파일용량</th>';
        		table_html += '<th></th>';
        		table_html += '</tr>';
        		table_html += '</thead>';
        		table_html += '<tbody>';
        		table_html += '</tbody>';
        		table_html += '</table>';
        		
        		var $table = $(table_html);
        		
        		var $tr = $("<tr></tr>");
        		
        		$tr.append("<td>".concat(nm,"</td>"));
        		$tr.append("<td>".concat(sizeMb.toFixed(2)," Mb</td>"));
        		
        		var $td = $("<td><button></button></td>");
        		//var $del = $("<button></button>");
        		$td.find("button").attr("type","button").attr("class","del-file-btn");
        		
        		$tr.append($td);
        		$table.find("tbody").append($tr);
        		
        		$td.find("button").on("click", function(){
        			$(".drag-div").addClass("drag-active").removeClass("BoardList");
        			$(".drag-div").empty().append('<p class="drag-msg noselect">파일을 드래그하세요.</p>');
        		});
        		
        		$(".drag-div").addClass("BoardList").removeClass("drag-active");
        		$(".drag-div").empty().append($table);
        	}
        	$(this).removeClass("over");
        	e.preventDefault();
        	e.stopPropagation();
        }
    },".drag-div");
});


/*
 * 임도 완료지 갱신
 */
function fnExcelUpload(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("updateFrdSvyComptPopup.do")
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 300,
		width: 400,
		title: "임도 완료지 갱신"
});
}
 
/**
 * 조사완료지 상세화면
 * @param id
 * @returns
 */
function fncSvyComptDetail(gid, mstId, svyId) {
	$(".loading-div").show();
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	
	
	$("input[name=gid]").val(gid);
	$("input[name=mstId]").val(mstId);
	$("input[name=svyLabel]").val(svyId);
	$("#listForm").attr("action","/sys/vyt/frd/sct/selectFrdSvyComptDetail.do");
	$("#listForm").submit();
}
 
/**
 * 조사완료지 수정화면
 * @returns
 */
function fncUpdateSvyComptView(gid, mstId, svyId){
	$(".loading-div").show();
	
	var hiddens = $("#listForm").find("input[name*=sch]");
	
	$.each(hiddens,function(idx,elm){
		$("#listForm").append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	$("input[name=gid]").val(gid);
	$("input[name=mstId]").val(mstId);
	$("input[name=svyLabel]").val(svyId);
	$("#listForm").attr("action","/sys/vyt/frd/sct/updateFrdSvyComptView.do");
	$("#listForm").submit();
}

/**
 * 조사완료지 수정
 * @returns
 */
function fncSvyComptUpdate(){
	var returnStatus = false;
	var menuNm, menuCode, tagType;
	
	if(confirm("수정 시 일괄 분석을 진행하시겠습니까?")) {
		$("input[name=analAt]").val("Y");
	}else{
		$("input[name=analAt]").val("N");
	}
	
	if(confirm("수정하시겠습니까?")) {
		
		$(".loading-div").show();
		
		// 기본 좌표 세팅 (bpx, bpy, epx, epy)
		var bpxD = $("input[name='bpxD']").val();
		var bpxM = $("input[name='bpxM']").val();
		var bpxS = $("input[name='bpxS']").val();
		
		var bpyD = $("input[name='bpyD']").val();
		var bpyM = $("input[name='bpyM']").val();
		var bpyS = $("input[name='bpyS']").val();
		
		var epxD = $("input[name='epx1D']").val();
		var epxM = $("input[name='epx1M']").val();
		var epxS = $("input[name='epx1S']").val();
		
		var epyD = $("input[name='epy1D']").val();
		var epyM = $("input[name='epy1M']").val();
		var epyS = $("input[name='epy1S']").val();
		
		if(bpxD == '' || bpxM == '' || bpxS == ''|| bpyD == '' || bpyM == '' || bpyS == '' || epxD == '' || epxM == '' || epxS == ''|| epyD == '' || epyM == '' || epyS == ''){
			
			if(epxD == ''){
				menuNm = "종점경도 도";
				menuCode="epxD";
			}else if(epxM == ''){
				menuNm = "종점경도 분";
				menuCode="epxM";
			}else if(epxS == ''){
				menuNm = "종점경도 초";
				menuCode="epxS";
			}else if(epyD == ''){
				menuNm = "종점위도 도";
				menuCode="epyD";
			}else if(epyM == ''){
				menuNm = "종점위도 분";
				menuCode="epyM";
			}else if(bpxS == ''){
				menuNm = "종점경도 초";
				menuCode="epyS";
			}else if(bpxD == ''){
				menuNm = "시점경도 도";
				menuCode="bpxD";
			}else if(bpxM == ''){
				menuNm = "시점경도 분";
				menuCode="bpxM";
			}else if(bpxS == ''){
				menuNm = "시점경도 초";
				menuCode="bpxS";
			}else if(bpyD == ''){
				menuNm = "시점위도 도";
				menuCode="bpyD";
			}else if(bpyM == ''){
				menuNm = "시점위도 분";
				menuCode="bpyM";
			}else if(bpyS == ''){
				menuNm = "시점위도 초";
				menuCode="bpyS";
			}
			
			$(".loading-div").hide();
			alert(menuNm+"가 입력되지 않았습니다.");
			$("input[name='"+menuCode+"']").focus();
			return false;
		}
		
		var bpx = bpxD+"°"+ bpxM+"'"+bpxS+"\"E";
		var bpy = bpyD+"°"+ bpyM+"'"+bpyS+"\"N";
		
		var bpx1 = dms2Degree(bpx);
		var bpy1 = dms2Degree(bpy);
		
		var convertLon5186_bpx = convertCoordi(bpx1, bpy1, '4326', '5186')[0];
		var convertLat5186_bpy = convertCoordi(bpx1, bpy1, '4326', '5186')[1];
		
		$("input[name=bpx]").val(convertLon5186_bpx);
		$("input[name=bpy]").val(convertLat5186_bpy);
		
		var epx = epxD+"°"+ epxM+"'"+epxS+"\"E";
		var epy = epyD+"°"+ epyM+"'"+epyS+"\"N";
		
		var epx1 = dms2Degree(epx);
		var epy1 = dms2Degree(epy);
		
		var convertLon5186_epx = convertCoordi(epx1, epy1, '4326', '5186')[0];
		var convertLat5186_epy = convertCoordi(epx1, epy1, '4326', '5186')[1];
		
		$("input[name=epx]").val(convertLon5186_epx);
		$("input[name=epy]").val(convertLat5186_epy);
		
		
		function validSetZero(value) {
			return value === "" || value === undefined ? "0" : value;
		}
		// 대계류
		var bigMrngTotal;
		var bigMrngTotal1 = validSetZero($("input[name=bigMrngTotal1]").val());
		var bigMrngTotal2 = validSetZero($("input[name=bigMrngTotal2]").val());
		var bigMrngTotal3 = validSetZero($("input[name=bigMrngTotal3]").val());
		
		bigMrngTotal = bigMrngTotal1 + "/" + bigMrngTotal2 + "/" + bigMrngTotal3;
		$("input[name=mrngBig]").val(bigMrngTotal);
		
		// 붕괴우려 사면
		var clpsCnrn;
		var slope1 = validSetZero($("input[name=slope1]").val());
		var slope2 = validSetZero($("input[name=slope2]").val());
		var slope3 = validSetZero($("input[name=slope3]").val());
		var slope4 = validSetZero($("input[name=slope4]").val());
		var slope5 = validSetZero($("input[name=slope5]").val());
		
		clpsCnrn = slope1 + "/" + slope2 + "/" + slope3 + "/" + slope4 + "/" + slope5;
		$("input[name=clpsCnrn]").val(clpsCnrn);
		
		
		// 주요수종 건수
		var maintreekndCnt = $("#maintreekndCnt").text();
		$("input[name=maintreekndCnt]").val(maintreekndCnt);
		
		// 주요식생 건수
		var mainvegCnt = $("#mainvegCnt").text();
		$("input[name=mainvegCnt]").val(mainvegCnt);
		
		// 보호시설 세팅(key-> 경도, 위도, 유형)
		var safeFctArr = [];
		$("#safeFctTable tr[class*='safeFctList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var type = $("select[name='safeFctType"+i+"']").val();
			
			var lonD = $("input[name='safeFctLonD"+i+"']").val();
			var lonM = $("input[name='safeFctLonM"+i+"']").val();
			var lonS = $("input[name='safeFctLonS"+i+"']").val();
			
			var latD = $("input[name='safeFctLatD"+i+"']").val();
			var latM = $("input[name='safeFctLatM"+i+"']").val();
			var latS = $("input[name='safeFctLatS"+i+"']").val();
			
			// 공백체크
			if(type == '' || lonD == '' || lonM == '' || lonS == ''|| latD == '' || latM == '' || latS == ''){
				
				if (type == '') {
					menuNm = "유형이";
					menuCode="Type";
					tagType ="select";
				}else if (latD == '') {
					menuNm = "위도 도가";
					menuCode="LatD";
					tagType ="input";
				}else if (latM == '') {
					menuNm = "위도 분이";
					menuCode="LatM";
					tagType ="input";
				}else if (latS == '') {
					menuNm = "위도 초가";
					menuCode="LatS";
					tagType ="input";
				}else if (lonD == '') {
					menuNm = "경도 도가";
					menuCode="LonD";
					tagType ="input";
				}else if (lonM == '') {
					menuNm = "경도 분이";
					menuCode="LonM";
					tagType ="input";
				}else if (lonS == '') {
					menuNm = "경도 초가";
					menuCode="LonS";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 보호시설 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='safeFct"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			
			var lon = lonD+"°"+ lonM+"'"+lonS+"\"E";
			var lat = latD+"°"+ latM+"'"+latS+"\"N";
			
			var x = dms2Degree(lon);
			var y = dms2Degree(lat);
			
			var convertLon5186 = convertCoordi(x, y, '4326', '5186')[0];
			var convertLat5186 = convertCoordi(x, y, '4326', '5186')[1];
			
			var item = {
					"경도":convertLon5186,
					"위도":convertLat5186,
					"유형":type
				};
			
			safeFctArr.push(item);
		});
		$("input[name=safeFctList]").val(JSON.stringify(safeFctArr));
		
		// 종단기울기 세팅(key-> 위도1, 위도2, 경도1, 경도2, 기울기, 사진, 사진태그)
		var lonSlopeArr = [];
		$("#lonSlopeTable tr[class*='lonSlopeList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var lonSlope = $("input[name*='lonSlope"+i+"']").val();
			
			var lonD1 = $("input[name*='lonSlopeLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='lonSlopeLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='lonSlopeLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='lonSlopeLatD1_"+i+"']").val();
			var latM1 = $("input[name*='lonSlopeLatM1_"+i+"']").val();
			var latS1 = $("input[name*='lonSlopeLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='lonSlopeLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='lonSlopeLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='lonSlopeLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='lonSlopeLatD2_"+i+"']").val();
			var latM2 = $("input[name*='lonSlopeLatM2_"+i+"']").val();
			var latS2 = $("input[name*='lonSlopeLatS2_"+i+"']").val();
			
			
			// 공백체크
			if(lonSlope == '' || lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				if (lonSlope == '') {
					menuNm = "기울기가";
					menuCode="";
					tagType ="input";
				}else if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 종단기울기 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='lonSlope"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
		
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=lonSlopePhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=lonSlopePhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"기울기":lonSlope
			};
			
			lonSlopeArr.push(item);
		});
		$("input[name=lonSlopeList]").val(JSON.stringify(lonSlopeArr));
		
		// 산지경사 세팅(key-> 위도1, 위도2, 경도1, 경도2, 경사도, 사진, 사진태그)
		var mtSlopeArr = [];
		$("#mtSlopeTable tr[class*='mtSlopeList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var mtSlope = $("input[name*='mtSlope"+i+"']").val();
			
			var lonD1 = $("input[name*='mtSlopeLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='mtSlopeLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='mtSlopeLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='mtSlopeLatD1_"+i+"']").val();
			var latM1 = $("input[name*='mtSlopeLatM1_"+i+"']").val();
			var latS1 = $("input[name*='mtSlopeLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='mtSlopeLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='mtSlopeLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='mtSlopeLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='mtSlopeLatD2_"+i+"']").val();
			var latM2 = $("input[name*='mtSlopeLatM2_"+i+"']").val();
			var latS2 = $("input[name*='mtSlopeLatS2_"+i+"']").val();
			
			// 공백체크
			if(mtSlope == '' || lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (mtSlope == '') {
					menuNm = "경사가";
					menuCode="";
					tagType ="input";
				}else if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 산지경사 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='mtSlope"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=mtSlopePhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=mtSlopePhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"경사도":mtSlope
			};
			
			mtSlopeArr.push(item);
		});
		$("input[name=mtSlopeList]").val(JSON.stringify(mtSlopeArr));
		
		// 암반노출 세팅(key-> 위도1, 위도2, 경도1, 경도2, 암반노출, 사진, 사진태그)
		var rockExposArr = [];
		$("#rockExposTable tr[class*='rockExposList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var rockExpos = $("input[name*='rockExpos"+i+"']").val();
			
			var lonD1 = $("input[name*='rockExposLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='rockExposLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='rockExposLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='rockExposLatD1_"+i+"']").val();
			var latM1 = $("input[name*='rockExposLatM1_"+i+"']").val();
			var latS1 = $("input[name*='rockExposLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='rockExposLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='rockExposLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='rockExposLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='rockExposLatD2_"+i+"']").val();
			var latM2 = $("input[name*='rockExposLatM2_"+i+"']").val();
			var latS2 = $("input[name*='rockExposLatS2_"+i+"']").val();
			
			// 공백체크
			if(rockExpos == '' || lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (rockExpos == '') {
					menuNm = "암반노출도가";
					menuCode="";
					tagType ="input";
				}else if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 암반노출 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='rockExpos"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=rockExposPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=rockExposPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"암반노출":rockExpos
			};
			
			rockExposArr.push(item);
		});
		$("input[name=rockExposList]").val(JSON.stringify(rockExposArr));
		
		// 조림지 세팅(key-> 위도1, 위도2, 경도1, 경도2, 조림지, 사진, 사진태그)
		var afrstArr = [];
		$("#afrstTable tr[class*='afrstList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var afrst = $("input[name*='afrst_"+i+"']").val()
			
			var lonD1 = $("input[name*='afrstLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='afrstLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='afrstLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='afrstLatD1_"+i+"']").val();
			var latM1 = $("input[name*='afrstLatM1_"+i+"']").val();
			var latS1 = $("input[name*='afrstLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='afrstLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='afrstLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='afrstLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='afrstLatD2_"+i+"']").val();
			var latM2 = $("input[name*='afrstLatM2_"+i+"']").val();
			var latS2 = $("input[name*='afrstLatS2_"+i+"']").val();
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 조림지 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='afrst"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=afrstPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=afrstPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"조림지":afrst
			};
			
			afrstArr.push(item);
		});
		$("input[name=afrstList]").val(JSON.stringify(afrstArr));
		
		// 벌채지 세팅(key-> 위도1, 위도2, 경도1, 경도2, 벌채지, 사진, 사진태그)
		var cuttingArr = [];
		$("#cuttingTable tr[class*='cuttingList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var cutting = $("input[name*='cutting_"+i+"']").val();
			
			var lonD1 = $("input[name*='cuttingLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='cuttingLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='cuttingLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='cuttingLatD1_"+i+"']").val();
			var latM1 = $("input[name*='cuttingLatM1_"+i+"']").val();
			var latS1 = $("input[name*='cuttingLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='cuttingLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='cuttingLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='cuttingLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='cuttingLatD2_"+i+"']").val();
			var latM2 = $("input[name*='cuttingLatM2_"+i+"']").val();
			var latS2 = $("input[name*='cuttingLatS2_"+i+"']").val();
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 벌채지 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='cutting"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=cuttingPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=cuttingPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"벌채지":cutting
			};
			
			cuttingArr.push(item);
		});
		$("input[name=cuttingList]").val(JSON.stringify(cuttingArr));
		
		// 석력지 세팅(key-> 위도1, 위도2, 경도1, 경도2, 석력지, 사진, 사진태그)
		var stmiArr = [];
		$("#stmiTable tr[class*='stmiList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var stmi = $("input[name*='stmi_"+i+"']").val();
			
			var lonD1 = $("input[name*='stmiLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='stmiLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='stmiLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='stmiLatD1_"+i+"']").val();
			var latM1 = $("input[name*='stmiLatM1_"+i+"']").val();
			var latS1 = $("input[name*='stmiLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='stmiLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='stmiLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='stmiLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='stmiLatD2_"+i+"']").val();
			var latM2 = $("input[name*='stmiLatM2_"+i+"']").val();
			var latS2 = $("input[name*='stmiLatS2_"+i+"']").val();
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 석력지 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='stmi"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=stmiPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=stmiPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"석력지":stmi
			};
			
			stmiArr.push(item);
		});
		$("input[name=stmiList]").val(JSON.stringify(stmiArr));
		
		// 계류종류 및 현황 세팅(key-> 위도1, 위도2, 경도1, 경도2, 대분류, 소분류, 사진, 사진태그)
		var mrngArr = [];
		$("#mrngTable tr[class*='mrngList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var bigMrng = $("select[name*='bigMrng"+i+"']").val();
			var smallMrng = $("select[name*='smallMrng"+i+"']").val();
			
			var lonD1 = $("input[name*='mrngLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='mrngLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='mrngLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='mrngLatD1_"+i+"']").val();
			var latM1 = $("input[name*='mrngLatM1_"+i+"']").val();
			var latS1 = $("input[name*='mrngLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			// 공백체크
//			if(bigMrng == '' || smallMrng == '' || lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 계류종류 및 현황 "+menuNm+" 입력되지 않았습니다.");
				
				$(tagType+"[name='mrng"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var photoCnt;
			$("input[name*=mrngPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=mrngPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"대분류":bigMrng,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"소분류":smallMrng
			};
			
			mrngArr.push(item);
		});
		$("input[name=mrngKind]").val(JSON.stringify(mrngArr));
		
		// 습지 세팅(key-> 위도1, 위도2, 경도1, 경도2, 습지, 사진, 사진태그)
		var wetLandArr = [];
		$("#wetLandTable tr[class*='wetLandList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var wetLand = $("input[name*='wetLand_"+i+"']").val()
			
			var lonD1 = $("input[name*='wetLandLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='wetLandLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='wetLandLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='wetLandLatD1_"+i+"']").val();
			var latM1 = $("input[name*='wetLandLatM1_"+i+"']").val();
			var latS1 = $("input[name*='wetLandLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='wetLandLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='wetLandLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='wetLandLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='wetLandLatD2_"+i+"']").val();
			var latM2 = $("input[name*='wetLandLatM2_"+i+"']").val();
			var latS2 = $("input[name*='wetLandLatS2_"+i+"']").val();
			
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 습지 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='wetLand"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=wetLandPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=wetLandPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"습지":wetLand
			};
			
			wetLandArr.push(item);
		});
		$("input[name=wetLandList]").val(JSON.stringify(wetLandArr));
		
		// 묘지 세팅(key-> 위도1, 위도2, 경도1, 경도2, 습지, 사진, 사진태그)
		var cmtryArr = [];
		$("#cmtryTable tr[class*='cmtryList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var cmtryLoc = $("select[name*='cmtryLoc"+i+"']").val();
			var cmtryMng = $("select[name*='cmtryMng"+i+"']").val();
			
			var lonD1 = $("input[name*='cmtryLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='cmtryLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='cmtryLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='cmtryLatD1_"+i+"']").val();
			var latM1 = $("input[name*='cmtryLatM1_"+i+"']").val();
			var latS1 = $("input[name*='cmtryLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 묘지 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='cmtry"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			
			var photoCnt;
			$("input[name*=cmtryPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=cmtryPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"묘지_유무":"",
					"묘지_위치":cmtryLoc,
					"묘지_관리":cmtryMng
			};
			
			cmtryArr.push(item);
		});
		$("input[name=cmtryList]").val(JSON.stringify(cmtryArr));
		
		// 용출수 세팅(key-> 위도1, 위도2, 경도1, 경도2, 용출수, 사진, 사진태그)
		var eltnWaterArr = [];
		$("#eltnWaterTable tr[class*='eltnWaterList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var eltnWater = $("input[name*='eltnWater_"+i+"']").val();
			
			var lonD1 = $("input[name*='eltnWaterLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='eltnWaterLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='eltnWaterLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='eltnWaterLatD1_"+i+"']").val();
			var latM1 = $("input[name*='eltnWaterLatM1_"+i+"']").val();
			var latS1 = $("input[name*='eltnWaterLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 용출수 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='eltnWater"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var photoCnt;
			$("input[name*=eltnWaterPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=eltnWaterPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"용출수":eltnWater
			};
			
			eltnWaterArr.push(item);
		});
		$("input[name=eltnWaterList]").val(JSON.stringify(eltnWaterArr));
		
		// 연약지반 세팅(key-> 위도1, 위도2, 경도1, 경도2, 용출수, 사진, 사진태그)
		var sofrtGrndArr = [];
		$("#sofrtGrndTable tr[class*='sofrtGrndList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var sofrtGrnd = $("input[name*='sofrtGrnd_"+i+"']").val();
			
			var lonD1 = $("input[name*='sofrtGrndLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='sofrtGrndLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='sofrtGrndLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='sofrtGrndLatD1_"+i+"']").val();
			var latM1 = $("input[name*='sofrtGrndLatM1_"+i+"']").val();
			var latS1 = $("input[name*='sofrtGrndLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='sofrtGrndLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='sofrtGrndLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='sofrtGrndLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='sofrtGrndLatD2_"+i+"']").val();
			var latM2 = $("input[name*='sofrtGrndLatM2_"+i+"']").val();
			var latS2 = $("input[name*='sofrtGrndLatS2_"+i+"']").val();
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 연약지반 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='sofrtGrnd"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=sofrtGrndPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=sofrtGrndPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"연약지반":sofrtGrnd
			};
			
			sofrtGrndArr.push(item);
		});
		$("input[name=sofrtGrndList]").val(JSON.stringify(sofrtGrndArr));
		
		// 붕괴우려지역 세팅(key-> 위도1, 위도2, 경도1, 경도2, 붕괴우려_대분류, 붕괴우려_소분류, 사진, 사진태그)
		var clpsCnrnArr = [];
		$("#clpsCnrnTable tr[class*='clpsCnrnList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var clpsCnrnBig = $("select[name*='clpsCnrnBig"+i+"']").val();
			var clpsCnrnSmall = $("select[name*='clpsCnrnSmall"+i+"']").val();
			
			var lonD1 = $("input[name*='clpsCnrnLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='clpsCnrnLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='clpsCnrnLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='clpsCnrnLatD1_"+i+"']").val();
			var latM1 = $("input[name*='clpsCnrnLatM1_"+i+"']").val();
			var latS1 = $("input[name*='clpsCnrnLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='clpsCnrnLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='clpsCnrnLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='clpsCnrnLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='clpsCnrnLatD2_"+i+"']").val();
			var latM2 = $("input[name*='clpsCnrnLatM2_"+i+"']").val();
			var latS2 = $("input[name*='clpsCnrnLatS2_"+i+"']").val();
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 붕괴우려지역 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='clpsCnrn"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=clpsCnrnPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=clpsCnrnPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"붕괴우려_대분류":clpsCnrnBig,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"붕괴우려_소분류":clpsCnrnSmall
			};
			
			clpsCnrnArr.push(item);
		});
		$("input[name=clpsCnrnList]").val(JSON.stringify(clpsCnrnArr));
		
		// 주요수종 세팅(key-> 위도1, 위도2, 경도1, 경도2, 주요수종, 사진, 사진태그)
		var maintreekndArr = [];
		$("#maintreekndTable tr[class*='maintreekndList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var lonD1 = $("input[name*='maintreekndLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='maintreekndLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='maintreekndLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='maintreekndLatD1_"+i+"']").val();
			var latM1 = $("input[name*='maintreekndLatM1_"+i+"']").val();
			var latS1 = $("input[name*='maintreekndLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 주요수종 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='maintreeknd"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var maintreeknd = $("input[name*='maintreeknd"+i+"']").val();
			
			var photoCnt;
			$("input[name*=maintreekndPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=maintreekndPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"주요수종":maintreeknd
			};
			
			maintreekndArr.push(item);
		});
		$("input[name=maintreekndList]").val(JSON.stringify(maintreekndArr));
		
		// 주요식생 세팅(key-> 위도1, 위도2, 경도1, 경도2, 주요식생, 사진, 사진태그)
		var mainvegArr = [];
		$("#mainvegTable tr[class*='mainvegList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var lonD1 = $("input[name*='mainvegLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='mainvegLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='mainvegLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='mainvegLatD1_"+i+"']").val();
			var latM1 = $("input[name*='mainvegLatM1_"+i+"']").val();
			var latS1 = $("input[name*='mainvegLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 주요식생 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='mainveg"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var mainveg = $("input[name*='mainveg"+i+"']").val();
			
			var photoCnt;
			$("input[name*=mainvegPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=mainvegPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"주요식생":mainveg
			};
			
			mainvegArr.push(item);
		});
		$("input[name=mainvegList]").val(JSON.stringify(mainvegArr));
		
		// 상수원 오염 세팅(key-> 위도1, 위도2, 경도1, 경도2, 상수원오염, 사진, 사진태그)
		var wtrPltnArr = [];
		$("#wtrPltnTable tr[class*='wtrPltnList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			var wtrPltn = $("input[name*='wtrPltn_"+i+"']").val();
			
			var lonD1 = $("input[name*='wtrPltnLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='wtrPltnLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='wtrPltnLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='wtrPltnLatD1_"+i+"']").val();
			var latM1 = $("input[name*='wtrPltnLatM1_"+i+"']").val();
			var latS1 = $("input[name*='wtrPltnLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 상수원 오염 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='wtrPltn"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var photoCnt;
			$("input[name*=wtrPltnPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=wtrPltnPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"상수원오염":wtrPltn
			};
			
			wtrPltnArr.push(item);
		});
		$("input[name=wtrPltnList]").val(JSON.stringify(wtrPltnArr));
		
		// 과다한 훼손우려지 세팅(key-> 위도1, 위도2, 경도1, 경도2, 훼손우려지, 사진, 사진태그)
		var dmgCncrnArr = [];
		$("#dmgCncrnTable tr[class*='dmgCncrnList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var dmgCncrn = $("input[name*='dmgCncrn_"+i+"']").val();
			
			var lonD1 = $("input[name*='dmgCncrnLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='dmgCncrnLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='dmgCncrnLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='dmgCncrnLatD1_"+i+"']").val();
			var latM1 = $("input[name*='dmgCncrnLatM1_"+i+"']").val();
			var latS1 = $("input[name*='dmgCncrnLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='dmgCncrnLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='dmgCncrnLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='dmgCncrnLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='dmgCncrnLatD2_"+i+"']").val();
			var latM2 = $("input[name*='dmgCncrnLatM2_"+i+"']").val();
			var latS2 = $("input[name*='dmgCncrnLatS2_"+i+"']").val();
			
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 과도한 훼손우려지 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='dmgCncrn"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=dmgCncrnPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=dmgCncrnPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"훼손우려지":dmgCncrn
			};
			
			dmgCncrnArr.push(item);
		});
		$("input[name=dmgCncrnList]").val(JSON.stringify(dmgCncrnArr));
		
		// 산림재해 세팅(key-> 위도1, 위도2, 경도1, 경도2, 재해유형, 산림재해, 사진, 사진태그)
		var frstDsstrArr = [];
		$("#frstDsstrTable tr[class*='frstDsstrList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var frstDsstrDmgType = $("select[name*='frstDsstrDmgType"+i+"']").val() != '기타' ? $("select[name*='frstDsstrDmgType"+i+"']").val() : $("input[name*='frstDsstrDmgEtc_"+i+"']").val();
			
			var lonD1 = $("input[name*='frstDsstrLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='frstDsstrLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='frstDsstrLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='frstDsstrLatD1_"+i+"']").val();
			var latM1 = $("input[name*='frstDsstrLatM1_"+i+"']").val();
			var latS1 = $("input[name*='frstDsstrLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			var lonD2 = $("input[name*='frstDsstrLonD2_"+i+"']").val();
			var lonM2 = $("input[name*='frstDsstrLonM2_"+i+"']").val();
			var lonS2 = $("input[name*='frstDsstrLonS2_"+i+"']").val();
			
			var latD2 = $("input[name*='frstDsstrLatD2_"+i+"']").val();
			var latM2 = $("input[name*='frstDsstrLatM2_"+i+"']").val();
			var latS2 = $("input[name*='frstDsstrLatS2_"+i+"']").val();
			
			// 공백체크
			if(frstDsstrDmgType == '' || lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''|| lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				
				if (frstDsstrDmgType == '') {
					menuNm = "재해유형이";
					menuCode="DmgType";
					tagType ="select";
				}else if (latD1 == '') {
					menuNm = "위도1 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도1 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도1 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도1 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도1 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도1 초가";
					menuCode="LonS1_";
					tagType ="input";
				}else if (latD2 == '') {
					menuNm = "위도2 도가";
					menuCode="LatD2_";
					tagType ="input";
				}else if (latM2 == '') {
					menuNm = "위도2 분이";
					menuCode="LatM2_";
					tagType ="input";
				}else if (latS2 == '') {
					menuNm = "위도2 초가";
					menuCode="LatS2_";
					tagType ="input";
				}else if (lonD2 == '') {
					menuNm = "경도2 도가";
					menuCode="LonD2_";
					tagType ="input";
				}else if (lonM2 == '') {
					menuNm = "경도2 분이";
					menuCode="LonM2_";
					tagType ="input";
				}else if (lonS2 == '') {
					menuNm = "경도2 초가";
					menuCode="LonS2_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 산불/병해충 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='frstDsstr"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var convertLon5186_2;
			var convertLat5186_2; 
			
			if(lonD2 == '' || lonM2 == '' || lonS2 == ''|| latD2 == '' || latM2 == '' || latS2 == ''){
				convertLon5186_2 = "";
				convertLat5186_2 = "";
			}else{
				var lon2 = lonD2+"°"+ lonM2+"'"+lonS2+"\"E";
				var lat2 = latD2+"°"+ latM2+"'"+latS2+"\"N";
				
				var x2 = dms2Degree(lon2);
				var y2 = dms2Degree(lat2);
				
				convertLon5186_2 = convertCoordi(x2, y2, '4326', '5186')[0];
				convertLat5186_2 = convertCoordi(x2, y2, '4326', '5186')[1];
			}
			
			var photoCnt;
			$("input[name*=frstDsstrPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=frstDsstrPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_2,
					"재해유형":frstDsstrDmgType,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_2,
					"산림재해":""
			};
			
			frstDsstrArr.push(item);
		});
		$("input[name=frstDsstrList]").val(JSON.stringify(frstDsstrArr));
		
		// 야생동물 세팅(key-> 위도1, 위도2, 경도1, 경도2, 야생동물_유무, 야생동물_유형, 사진, 사진태그)
		var wildAnmlArr = [];
		var wildAnmlLength;
		$("#wildAnmlTable tr[class*='wildAnmlList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var wildAnmlAt = $("input[name*='wildAnmlAt"+i+"']").val();
			var wildAnmlKind = $("input[name*='wildAnmlKind"+i+"']").val();
			
			var lonD1 = $("input[name*='wildAnmlLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='wildAnmlLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='wildAnmlLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='wildAnmlLatD1_"+i+"']").val();
			var latM1 = $("input[name*='wildAnmlLatM1_"+i+"']").val();
			var latS1 = $("input[name*='wildAnmlLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 야생동물 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='wildAnml"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var photoCnt;
			$("input[name*=wildAnmlPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=wildAnmlPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"야생동물_유형":wildAnmlAt,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"야생동물_종류":wildAnmlKind
			};
			
			wildAnmlArr.push(item);
		});
		$("input[name=wildAnmlList]").val(JSON.stringify(wildAnmlArr));
		
		
		// 사방시설설치 세팅(key-> 위도1, 위도2, 경도1, 경도2, 사방시설설치_유무, 사방시설설치_유형, 사진, 사진태그)
		var ecnrFcltyInstlArr = [];
		$("#ecnrFcltyInstlTable tr[class*='ecnrFcltyInstlList'][class*='checkPart']").each(function(idx, value){
			var i = idx + 1;
			
			var ecnrFcltyInstlAt = "유";
			var ecnrFcltyInstlType = $("input[name*='ecnrFcltyInstlType"+i+"']").val();
			
			var lonD1 = $("input[name*='ecnrFcltyInstlLonD1_"+i+"']").val();
			var lonM1 = $("input[name*='ecnrFcltyInstlLonM1_"+i+"']").val();
			var lonS1 = $("input[name*='ecnrFcltyInstlLonS1_"+i+"']").val();
			
			var latD1 = $("input[name*='ecnrFcltyInstlLatD1_"+i+"']").val();
			var latM1 = $("input[name*='ecnrFcltyInstlLatM1_"+i+"']").val();
			var latS1 = $("input[name*='ecnrFcltyInstlLatS1_"+i+"']").val();
			
			var lon1 = lonD1+"°"+ lonM1+"'"+lonS1+"\"E";
			var lat1 = latD1+"°"+ latM1+"'"+latS1+"\"N";
			
			// 공백체크
			if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 사방시설 설치 여부 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='ecnrFcltyInstl"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
			var x1 = dms2Degree(lon1);
			var y1 = dms2Degree(lat1);
			
			var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
			var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];
			
			var photoCnt;
			$("input[name*=ecnrFcltyInstlPhoto"+i+"_]").each(function(idx, value){
				photoCnt = (idx+1);
			});
			
			var photoArr = [];
			
			for(var j=1; j<=photoCnt; j++){
				var photo = $("input[name*=ecnrFcltyInstlPhoto"+i+"_"+j+"]").val();
				
				if(photo != undefined){
					photoArr.push("gimg:///"+photo);
				}
			}
			
			var item = {
					"사진":photoArr,
					"사진태그":[],
					"위도1":convertLat5186_1,
					"위도2":convertLat5186_1,
					"사방시설설치_유무":ecnrFcltyInstlAt,
					"경도1":convertLon5186_1,
					"경도2":convertLon5186_1,
					"사방시설설치_유형":ecnrFcltyInstlType
			};
			
			ecnrFcltyInstlArr.push(item);
		});
		$("input[name=ecnrFcltyInstlList]").val(JSON.stringify(ecnrFcltyInstlArr));
		
		// 사방시설필요 세팅(key-> 위도1, 위도2, 경도1, 경도2, 사방시설필요_유무, 사방시설필요_유형, 사진, 사진태그)
		var ecnrFcltyNcstyArr = [];
		$("#ecnrFcltyNcstyTable tr[class*='ecnrFcltyNcstyList'][class*='checkPart']").each(function(idx, value) {
		    var i = idx + 1;
		    
		    var ecnrFcltyNcstyAt = "유";
		    var ecnrFcltyNcstyType = $("input[name*='ecnrFcltyNcstyType" + i + "']").val();
		    
		    var lonD1 = $("input[name*='ecnrFcltyNcstyLonD1_" + i + "']").val();
		    var lonM1 = $("input[name*='ecnrFcltyNcstyLonM1_" + i + "']").val();
		    var lonS1 = $("input[name*='ecnrFcltyNcstyLonS1_" + i + "']").val();

		    var latD1 = $("input[name*='ecnrFcltyNcstyLatD1_" + i + "']").val();
		    var latM1 = $("input[name*='ecnrFcltyNcstyLatM1_" + i + "']").val();
		    var latS1 = $("input[name*='ecnrFcltyNcstyLatS1_" + i + "']").val();

		    var lon1 = lonD1 + "°" + lonM1 + "'" + lonS1 + "\"E";
		    var lat1 = latD1 + "°" + latM1 + "'" + latS1 + "\"N";


		    // 공백체크
		    if(lonD1 == '' || lonM1 == '' || lonS1 == ''|| latD1 == '' || latM1 == '' || latS1 == ''){
				
				if (latD1 == '') {
					menuNm = "위도 도가";
					menuCode="LatD1_";
					tagType ="input";
				}else if (latM1 == '') {
					menuNm = "위도 분이";
					menuCode="LatM1_";
					tagType ="input";
				}else if (latS1 == '') {
					menuNm = "위도 초가";
					menuCode="LatS1_";
					tagType ="input";
				}else if (lonD1 == '') {
					menuNm = "경도 도가";
					menuCode="LonD1_";
					tagType ="input";
				}else if (lonM1 == '') {
					menuNm = "경도 분이";
					menuCode="LonM1_";
					tagType ="input";
				}else if (lonS1 == '') {
					menuNm = "경도 초가";
					menuCode="LonS1_";
					tagType ="input";
				}
				
				$(".loading-div").hide();
				
				alert(i+"번 사방시설 필요 여부 "+menuNm+" 입력되지 않았습니다.");
				$(tagType+"[name='ecnrFcltyNcsty"+menuCode+i+"']").focus();
				
				returnStatus = true;
				return false;
			}
		    
		    var x1 = dms2Degree(lon1);
		    var y1 = dms2Degree(lat1);

		    var convertLon5186_1 = convertCoordi(x1, y1, '4326', '5186')[0];
		    var convertLat5186_1 = convertCoordi(x1, y1, '4326', '5186')[1];

			
		    var photoCnt;
		    $("input[name*=ecnrFcltyNcstyPhoto" + i + "_]").each(function(idx, value) {
		        photoCnt = (idx + 1);
		    });

		    var photoArr = [];

		    for (var j=1; j<=photoCnt; j++) {
		        var photo = $("input[name*=ecnrFcltyNcstyPhoto" + i + "_" + j + "]").val();

		        if (photo != undefined) {
		            photoArr.push("gimg:///" + photo);
		        }
		    }

		    var item = {
		        "사진": photoArr,
		        "사진태그": [],
		        "위도1": convertLat5186_1,
		        "위도2": convertLat5186_1,
		        "사방시설필요_유무": ecnrFcltyNcstyAt,
		        "경도1": convertLon5186_1,
		        "경도2": convertLon5186_1,
		        "사방시설필요_유형": ecnrFcltyNcstyType
		    };

		    ecnrFcltyNcstyArr.push(item);
		});
		$("input[name=ecnrFcltyNcstyList]").val(JSON.stringify(ecnrFcltyNcstyArr));
		
		if(returnStatus){
			return false;
		}
		
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: $("#listForm")[0].action,
	        dataType: "json",
	        processData: false,
	        contentType: false,
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/vyt/frd/sct/selectFrdSvyComptList.do";
                } else {
                	$(".loading-div").hide();
                    alert(data.message);
                }
        },
        error: function(data) {
        	$(".loading-div").hide();
        	alert(data.message);
        }
		}).submit();
	}else{
		$("input[name=analAt]").val("N");
	}
	
	
}

/**
 * 조사완료지 삭제
 * @param id
 * @returns
 */
function fncDeleteSvyCompt(gid, mstId, svyId){
	
	if(confirm("삭제하시겠습니까?")) {
		$("input[name=gid]").val(gid);
		$("input[name=svyLabel]").val(svyId);
		$("input[name=mstId]").val(mstId);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/vyt/frd/sct/deleteFrdSvyCompt.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/sys/vyt/frd/sct/selectFrdSvyComptList.do";
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
function fncOpenMap(type, gid){
	var url = "/sys/gis/mainMap.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	form.append($('<input/>', {type: 'hidden', name: "gid", value:gid })).append($('<input/>', {type: 'hidden', name: "type", value:type }));
	form.appendTo("body").submit().remove();
}

/**
 * 클립리포트 보고서
 * @param gid
 * @returns
 */
function fncOpenClipReport(){
	var url = "/cmm/openClipReport.do";
	var fileName = "";
	var form = null;
	
	
	
	return false;
}

/*
 * 조사완료지 엑셀 다운로드
 */
function fnExcelDown(){
	
	var svyYear = $("input[name=svyYear]").val();
	
	var url = $("#listForm").attr("action");
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	url = url.replace(".do","Excel.do");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
	
}

function fnUnfoldTable(type){	
	if(document.getElementById(type+'Table').style.display === 'block') {
		document.getElementById(type+'Table').style.display = 'none';
		document.getElementById(type+'ShowTab').textContent = '[열기]';
	} else {
		document.getElementById(type+'Table').style.display = 'block';
		document.getElementById(type+'ShowTab').textContent = '[닫기]';
	}
}

/*
 * 관계지적팝업
 */
function fnOtherAddrPopup(smid){
	
	$('<div id="otherAddrDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("selectOtherAddrPopup.do?smid="+smid);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 150,
		width: 300,
		title: "관계지적 목록"
	});
}

/**
 * 토지이용계획열람 팝업 생성
 * @returns
 * @Description
 */
function fncLadUsePlanPopup(pnuCode, addr, jibun, type){
	
	var pageTitle;
	var url;
	
	if(type == 'eum'){
		
		var addrArray = addr.split(" ");
		
		var sd = addrArray[0];
		var sgg = addrArray[1];
		var emd = addrArray[2];
		var ri = addrArray[3] !== undefined ? addrArray[3] : "";
		
		var landGbn = 1;

		if(jibun.includes("산")) landGbn = 2;
		jibun = jibun.replaceAll(/[^0-9]/g,'');
		
		pageTitle = "토지이용계획열람";
		url = "http://www.eum.go.kr/web/ar/lu/luLandDet.jsp?mode=search&selGbn=umd&isNoScr=script&pnu="+pnuCode+"&selSido="+sd+"&selSgg="+sgg+"&selUmd="+emd+"&selRi="+ri+"&landGbn="+landGbn+"&bobn="+jibun+"&bubn=0000";
	}else{
		pageTitle = "씨리얼 (SEE:REAL)";
		url = "https://seereal.lh.or.kr/main.do?menuCd=goLotdetailInfo&pnu="+pnuCode;
	}
	
	window.open(url, pageTitle,'width=750px,height=1100px,scrollbars=no');
	
}

/**
 * 대상지 추가등록 팝업
 * @param smid
 * @returns
 */
function fncAddStripLandInsertView(id,sldlabel,mstId,frdtype,compentauth){
	
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertStripLandViewAddPopup.do?id="+id+"&sldlabel="+sldlabel+"&mstId="+mstId+"&frdtype="+frdtype+"&compentauth="+compentauth);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 420,
		width: 600,
		title: "임도 노선 추가 등록"
	});
}

/**
 * 대상지 추가 등록
 * @param smid
 * @returns
 */
function fnSvySldInsert() {
	
	var routeType = $("select[name=routetype]").val();
	if(routeType == null || routeType == undefined || routeType == ""){
		alert("노선종류를 선택해주세요");
		return false;
	}
	
	var returnStatus = false;
	if($app.upload == null || $app.upload == undefined || $app.upload.form == undefined){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
	if(confirm("등록하시겠습니까?")) {
		
		$(".drag-div").append('<div class="progress"><div></div></div>');
		
		var jqXHR=$.ajax({
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
	        	$(".sub-upload").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
	        },
	        success: function(data){
	        	if(data.cnt == 'fail') {
					alert("중복된 사업명이 존재합니다. \n다시 한 번 확인해주세요.");
	        		$(".loading-div").remove();
	            	$(".progress").remove();
	            	$("input[name=sld_nm]").val('');
					return false;
	        	}else{
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

/*
 * 분석파일 다운로드
 */
function fnAnalDataDownload(analType, fileExtsn) {
    var fileSn = $("input[name="+analType+"]").val();
    window.location.href = "/sys/vyt/frd/sld/selectAnalFileDown.do?fileSn="+fileSn+"&fileExtsn="+fileExtsn;
}


function fnSvyItemAddDelBtn(part, type){
	var appendHtml;
	var rowIndex = $("#"+part+"Table tr").length;
	
	if(part == 'safeFct'){	// 보호시설
		
		var trCnt = rowIndex / 2;
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 보호시설</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>유형</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<select name='safeFctType"+(trCnt)+"'>";
			appendHtml += 			"<option value=\"\">선택하세요</option>";
			appendHtml += 			"<option value='민가'>민가</option>";
			appendHtml += 			"<option value='종교시설'>종교시설</option>";
			appendHtml += 			"<option value='농경지'>농경지</option>";
			appendHtml += 		"</select>";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='safeFctLatD"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='safeFctLatM"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='safeFctLatS"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='safeFctLonD"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='safeFctLonM"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='safeFctLonS"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		
		var checkRowIndex = $("#"+part+"Table tr").length;
		$("input[name="+part+"]").val((checkRowIndex-2)/2);
		
		
		if(((checkRowIndex-2)/2) == 0){
			$("select[name=acsbl]").val("").prop("selected", true);
			$("select[name=field]").val("").prop("selected", true);
		}
		
	}else if(part == 'lonSlope'){ // 종단기울기
		
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 종단기울기</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += 	"<th rowspan='2'>기울기(%)</th>";
			appendHtml += 	"<td rowspan='2' class='"+part+"CalcArea center'>";
			appendHtml +=   	"<input type='number' name='"+part+(trCnt)+"' class='wd30' value=\"\" onchange=\"fnCalcValue('"+part+"')\" />%";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			if(trCnt == 2){
				$("input[name="+part+"Min]").val('0');
				$("input[name="+part+"Max]").val('0');
				$("input[name="+part+"Avg]").val('0');
			}
			$("."+part+"List"+(trCnt-1)).remove();
		}
	}else if(part == 'mtSlope'){ // 산지경사
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 산지경사</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += 	"<th rowspan='2'>경사(°)</th>";
			appendHtml += 	"<td rowspan='2' class='"+part+"CalcArea center'>";
			appendHtml +=   	"<input type='number' name='"+part+(trCnt)+"' class='wd30' value=\"\" onchange=\"fnCalcValue('"+part+"')\" />°";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			if(trCnt == 2){
				$("input[name="+part+"Min]").val('0');
				$("input[name="+part+"Max]").val('0');
				$("input[name="+part+"Avg]").val('0');
			}
			$("."+part+"List"+(trCnt-1)).remove();
		}
	}else if(part == 'rockExpos'){ // 암반노출
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 암반노출</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += 	"<th rowspan='2'>암반노출(m)</th>";
			appendHtml += 	"<td rowspan='2' class='"+part+"CalcArea center'>";
			appendHtml +=   	"<input type='number' name='"+part+(trCnt)+"' class='wd30' value=\"\" onchange=\"fnCalcValue('"+part+"')\" />m";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td class='center'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			if(trCnt == 2){
				$("input[name="+part+"Sum]").val('0');
			}
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'afrst'){ // 조림지
		var trCnt = ((rowIndex-2)/5)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 조림지</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>조림지</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			if(trCnt == 2){
				$("input[name="+part+"At]").val('무');
			}
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'cutting'){ // 벌채지
		var trCnt = ((rowIndex-2)/5)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 벌채지</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>벌재지</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'stmi'){ // 석력지
		var trCnt = ((rowIndex-2)/5)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 석력지</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>석력지</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'mrng'){ // 계류종류 및 현황
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 계류종류 및 현황</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>대분류</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea center'>";
			appendHtml +=	"<select name='bigMrng"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml +=		"<option value=\"\">선택하세요</option>";
			appendHtml +=		"<option value='대계류'>대계류</option>";
			appendHtml +=		"<option value='중계류'>중계류</option>";
			appendHtml +=		"<option value='소계류'>소계류</option>";
			appendHtml +=	"</select>"
			appendHtml += 	"</td>";
			appendHtml += 	"<th>소분류</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea center'>";
			appendHtml +=	"<select name='smallMrng"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml +=		"<option value=\"\">선택하세요</option>";
			appendHtml +=		"<option value='상시천'>상시천</option>";
			appendHtml +=		"<option value='건천'>건천</option>";
			appendHtml +=	"</select>"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'wetLand'){// 습지
		
		var trCnt = ((rowIndex-2)/5)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 습지</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>습지</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'cmtry'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){ //묘지
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 묘지</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>묘지 위치</th>";
			appendHtml += 	"<td colspan='2' class='cmtryCalcArea'>";
			appendHtml +=		"<select name='"+part+"Loc"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml +=			"<option value=\"\">선택하세요</option>";
			appendHtml +=			"<option value='노선 상부'>노선 상부</option>";
			appendHtml +=			"<option value='노선 하부'>노선 하부</option>";
			appendHtml +=		"</select>"
			appendHtml += 	"</td>";
			appendHtml += 	"<th>묘지 관리</th>";
			appendHtml += 	"<td colspan='2' class='cmtryCalcArea'>";
			appendHtml +=		"<select name='"+part+"Mng"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml +=			"<option value=\"\">선택하세요</option>";
			appendHtml +=			"<option value='관리O'>관리O</option>";
			appendHtml +=			"<option value='관리X'>관리X</option>";
			appendHtml +=		"</select>"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			if(trCnt == 2){
				$("input[name="+part+"Cnt]").val('0');
				$("input[name="+part+"Loc1]").val('0');
				$("input[name="+part+"Loc2]").val('0');
				$("input[name="+part+"Mng1]").val('0');
				$("input[name="+part+"Mng2]").val('0');
			}
			
			
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'eltnWater'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 용출수</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>용출수</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'sofrtGrnd'){
		var trCnt = ((rowIndex-2)/5)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 연약지반</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>연약지반</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'clpsCnrn'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 붕괴우려지역</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>붕괴우려 대분류</th>";
			appendHtml += 	"<td class='"+part+"CalcArea center'>";
			appendHtml += 		"<select name='"+part+"Big"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml +=			"<option value=\"\">선택하세요</option>";
			appendHtml +=			"<option value='사면'>사면</option>";
			appendHtml +=			"<option value='계류'>계류</option>";
			appendHtml +=		"</select>"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>붕괴우려 소분류</th>";
			appendHtml += 	"<td class='"+part+"CalcArea center'>";
			appendHtml += 		"<select name='"+part+"Small"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml +=			"<option value=\"\">선택하세요</option>";
			appendHtml +=			"<option value='침식'>침식</option>";
			appendHtml +=			"<option value='붕괴'>붕괴</option>";
			appendHtml +=			"<option value='포락'>포락</option>";
			appendHtml +=		"</select>"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'maintreeknd'){
		var trCnt = ((rowIndex)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 주요수종</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>주요수종</th>";
			appendHtml += 	"<td colspan='5' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'mainveg'){
		var trCnt = ((rowIndex)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 주요식생</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>주요식생</th>";
			appendHtml += 	"<td colspan='5' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'wtrPltn'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 상수원 오염</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>상수원오염</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'dmgCncrn'){
		var trCnt = ((rowIndex-2)/5)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 과다한 훼손우려지</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>훼손우려지</th>";
			appendHtml += 	"<td colspan='5'>";
			appendHtml += 		"<input type='text' name='"+part+"_"+(trCnt)+"' value=\"\">";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'frstDsstr'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 산불 / 병해충</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += 	"<th rowspan='2'>재해유형</th>";
			appendHtml += 	"<td  rowspan='2' class='"+part+"CalcArea "+part+"InputArea'>";
			appendHtml += 		"<select name='"+part+"DmgType"+(trCnt)+"' onchange=\"fnCalcValue('"+part+"'); fnCheckOptionVal(this.value, "+(trCnt)+"); \">";
			appendHtml +=			"<option value=\"\">선택하세요</option>";
			appendHtml +=			"<option value='산불'>산불</option>";
			appendHtml +=			"<option value='병해충'>병해충</option>";
			appendHtml +=			"<option value='기타'>기타</option>";
			appendHtml +=		"</select>"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도2</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LatD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도2</th>";
			appendHtml += 	"<td>";
			appendHtml += 		"<input type='text' name='"+part+"LonD2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS2_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'wildAnml'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 야생동물</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도1</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>형태</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea center'>";
			appendHtml += 		"<input type='text' name='"+part+"At"+(trCnt)+"' value=\"\" onchange=\"fnCalcValue('"+part+"')\">"
			appendHtml += 	"</td>";
			appendHtml += 	"<th>종류</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea center'>";
			appendHtml += 		"<input type='text' name='"+part+"Kind"+(trCnt)+"' value=\"\" oninput=\"fnCalcValue('"+part+"')\">"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'ecnrFcltyInstl'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 사방시설 설치 여부</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>종류</th>";
			appendHtml += 	"<td colspan='5' class='"+part+"CalcArea center'>";
			appendHtml += 		"<input type='text' name='"+part+"Type"+(trCnt)+"' value=\"\" oninput=\"fnCalcValue('"+part+"')\">"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}else if(part == 'ecnrFcltyNcsty'){
		var trCnt = ((rowIndex-2)/4)+1;
		
		if(type == 'add'){
			appendHtml += "<tr class='"+part+"List"+(trCnt)+" checkPart'>";
			appendHtml += 	"<th colspan='6'>"+(trCnt)+"번 사방시설 필요 여부</th>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<td colspan='6' class='center'></td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>위도</th>";
			appendHtml += 	"<td colspan='2'>";
			appendHtml += 		"<input type='text' name='"+part+"LatD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LatM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LatS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"N";
			appendHtml += 	"</td>";
			appendHtml += 	"<th>경도</th>";
			appendHtml += 	"<td colspan='2' class='"+part+"CalcArea'>";
			appendHtml += 		"<input type='text' name='"+part+"LonD1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>°";
			appendHtml += 		"<input type='text' name='"+part+"LonM1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^0-9]/g,'');\" style='width: 30px;'>\'";
			appendHtml += 		"<input type='text' name='"+part+"LonS1_"+(trCnt)+"' value=\"\" onKeyup=\"this.value=this.value.replace(/[^-\.0-9]/g,'');\" style='width: 50px;'>\"E";
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			appendHtml += "<tr class='"+part+"List"+(trCnt)+"'>";
			appendHtml += 	"<th>종류</th>";
			appendHtml += 	"<td colspan='5' class='"+part+"CalcArea center'>";
			appendHtml += 		"<input type='text' name='"+part+"Type"+(trCnt)+"' value=\"\" oninput=\"fnCalcValue('"+part+"')\">"
			appendHtml += 	"</td>";
			appendHtml += "</tr>";
			
			$("#"+part+"Table").append(appendHtml);
		}else{
			$("."+part+"List"+(trCnt-1)).remove();
		}
		fnCalcValue(part);
	}
}

function fnCalcValue(type) {
	var sum = 0;
	
	var max = 0;
	var min = Infinity;
	var avg = 0;
	
	var itemCnt = 0; 
	
	var arr = [];
	if(type == 'lonSlope' || type == 'mtSlope'){
		$("."+type+"CalcArea > input[name*="+type+"]").each(function(idx){
			max = Number($(this).val()) > max ? Number($(this).val()) : max;
			min = Number($(this).val()) < min ? Number($(this).val()) : min;
			sum += Number($(this).val());
			
			itemCnt = idx;
		});
		avg = sum / (itemCnt+1);
		
		$("input[name="+type+"Min]").val(min);
		$("input[name="+type+"Max]").val(max);
		$("input[name="+type+"Avg]").val((Math.round(avg*100)/100).toFixed(2));
		
	}else if(type == 'rockExpos'){
		
		$("."+type+"CalcArea > input[name*="+type+"]").each(function(idx){
			sum += Number($(this).val());
		});
		$("input[name="+type+"Sum]").val((Math.round(sum*100)/100).toFixed(1));
		
	}else if(type == 'afrst' || type == 'cutting' || type == 'stmi' || type == 'wetLand' || type == 'eltnWater' || type == 'sofrtGrnd' || type == 'wtrPltn' || type == 'dmgCncrn'){
		
		var tbodyLength = $("#"+type+"Table > tbody > tr").length;
		if(tbodyLength > 2){
			$("input[name="+type+"At]").val("유");
		}else{
			$("input[name="+type+"At]").val("무");
		}
	}else if(type == 'mrng'){
		
		var bigMrng1 = 0, bigMrng2 = 0, bigMrng3 = 0;
		var middleMrng1 = 0, middleMrng2 = 0, middleMrng3 = 0;
		var smallMrng1 = 0, smallMrng2 = 0, smallMrng3 = 0;
		
		$("."+type+"CalcArea > select[name*=bigMrng]").each(function(idx){
			if($(this).val() == "대계류") { 
				bigMrng1 += 1;
			}else if($(this).val() == "중계류") {
				middleMrng1 += 1;
			}else if($(this).val() == "소계류") {
				smallMrng1 += 1;
			}
		});
		
		$("."+type+"CalcArea > select[name*=smallMrng]").each(function(idx){
			var bigMrngVal = $("select[name=bigMrng"+(idx+1)+"]").val();
			
			if(bigMrngVal == "대계류"){
				if($(this).val() == "상시천"){
					bigMrng2 += 1;
				}else if($(this).val() == "건천"){
					bigMrng3 += 1;
				}
			}else if(bigMrngVal == "중계류"){
				if($(this).val() == "상시천"){
					middleMrng2 += 1;
				}else if($(this).val() == "건천"){
					middleMrng3 += 1;
				}
			}else if(bigMrngVal == "소계류"){
				if($(this).val() == "상시천"){
					smallMrng2 += 1;
				}else if($(this).val() == "건천"){
					smallMrng3 += 1;
				}
			}
		});
		
		$("input[name=bigMrngTotal1]").val(bigMrng1);
		$("input[name=bigMrngTotal2]").val(bigMrng2);
		$("input[name=bigMrngTotal3]").val(bigMrng3);
		
		$("input[name=middleMrngTotal1]").val(middleMrng1);
		$("input[name=middleMrngTotal2]").val(middleMrng2);
		$("input[name=middleMrngTotal3]").val(middleMrng3);
		
		$("input[name=smallMrngTotal1]").val(smallMrng1);
		$("input[name=smallMrngTotal2]").val(smallMrng2);
		$("input[name=smallMrngTotal3]").val(smallMrng3);
		
	}else if(type == 'cmtry'){
		var cmtryLoc1 = 0, cmtryLoc2 = 0;
		var cmtryMng1 = 0, cmtryMng2 = 0;
		
		$("."+type+"CalcArea > select[name*=cmtryLoc]").each(function(idx){
			var cmtryLocVal = $("select[name=cmtryLoc"+(idx+1)+"]").val();
			$("input[name=cmtryCnt]").val(idx+1);
			if(cmtryLocVal == '노선 상부'){
				cmtryLoc1 += 1;
			}else if(cmtryLocVal == '노선 하부'){
				cmtryLoc2 += 1;
			}
		});
		
		$("input[name=cmtryLoc1]").val(cmtryLoc1);
		$("input[name=cmtryLoc2]").val(cmtryLoc2);
		
		$("."+type+"CalcArea > select[name*=cmtryMng]").each(function(idx){
			var cmtryMngVal = $("select[name=cmtryMng"+(idx+1)+"]").val();
			
			if(cmtryMngVal == '관리O'){
				cmtryMng1 += 1;
			}else if(cmtryMngVal == '관리X'){
				cmtryMng2 += 1;
			}
		});
		
		$("input[name=cmtryMng1]").val(cmtryMng1);
		$("input[name=cmtryMng2]").val(cmtryMng2);
		
	}else if(type == 'clpsCnrn'){
		
		// 붕괴우려 사면
		var slope1 = 0, slope2 = 0, slope3 = 0, slope4 = 0, slope5 = 0;
		
		// 붕괴우려 계류
		var mtTrnt1 = 0, mtTrnt2 = 0, mtTrnt3 = 0, mtTrnt4 = 0, mtTrnt5 = 0;
		
		$("."+type+"CalcArea > select[name*=clpsCnrnBig]").each(function(idx){
			if($(this).val() == "사면") { 
				slope1 += 1;
			}else if($(this).val() == "계류") {
				mtTrnt1 += 1;
			}
		});
		
		$("."+type+"CalcArea > select[name*=clpsCnrnSmall]").each(function(idx){
			var clpsCnrnBigVal = $("select[name=clpsCnrnBig"+(idx+1)+"]").val();
			
			if(clpsCnrnBigVal == "사면"){
				if($(this).val() == "침식"){
					slope2 += 1;
				}else if($(this).val() == "붕괴"){
					slope3 += 1;
				}else if($(this).val() == "포락"){
					slope4 += 1;
				}else if($(this).val() == ""){
					slope5 += 1;
				}
			}else if(clpsCnrnBigVal == "계류"){
				if($(this).val() == "침식"){
					mtTrnt2 += 1;
				}else if($(this).val() == "붕괴"){
					mtTrnt3 += 1;
				}else if($(this).val() == "포락"){
					mtTrnt4 += 1;
				}else if($(this).val() == ""){
					mtTrnt5 += 1;
				}
			}
		});
		
		$("input[name=slope1]").val(slope1);
		$("input[name=slope2]").val(slope2);
		$("input[name=slope3]").val(slope3);
		$("input[name=slope4]").val(slope4);
		$("input[name=slope5]").val(slope5);
		
		$("input[name=mtTrnt1]").val(mtTrnt1);
		$("input[name=mtTrnt2]").val(mtTrnt2);
		$("input[name=mtTrnt3]").val(mtTrnt3);
		$("input[name=mtTrnt4]").val(mtTrnt4);
		$("input[name=mtTrnt5]").val(mtTrnt5);
	}else if(type == 'maintreeknd' || type == 'mainveg'){
		var tbodyLength = $("#"+type+"Table > tr").length;
		if(tbodyLength > 0){
			$("."+type+"CalcArea > input[name*="+type+"]").each(function(idx){
				$("#"+type+"Cnt").text(idx+1);
			});
		}else{
			$("#"+type+"Cnt").text("0");
		}
		
	}else if(type == 'frstDsstr'){
		
		var frstFireCnt = 0, pestCnt = 0, etcCnt = 0;
		
		var tbodyLength = $("#"+type+"Table > tbody > tr").length;
		if(tbodyLength > 2){
			
			$("."+type+"CalcArea > select[name*="+type+"DmgType]").each(function(idx){
				$("#frstDsstrCnt").text(idx+1);
				
				if($(this).val() == '산불'){
					frstFireCnt += 1;
				}else if($(this).val() == '병해충'){
					pestCnt += 1;
				}else if($(this).val() == '기타'){
					etcCnt += 1;
				}
			});
			
			$("input[name=frstDsstrFire]").val(frstFireCnt);
			$("input[name=pestCnt]").val(pestCnt);
			$("input[name=etcCnt]").val(etcCnt);
			
		}else{
			$("#frstDsstrCnt").text("0");
			$("input[name=frstDsstrFire]").val("0");
			$("input[name=pestCnt]").val("0");
			$("input[name=etcCnt]").val("0");
		}
		
	}else if(type == 'ecnrFcltyInstl' || type == 'ecnrFcltyNcsty'){
		
		var totalKind = [];
		
		var tbodyLength = $("#"+type+"Table > tbody > tr").length;
		if(tbodyLength > 2){
			
			$("."+type+"CalcArea > input[name*="+type+"Type]").each(function(idx){
				$("input[name="+type+"Cnt]").val(idx+1);
				totalKind.push($(this).val());
			});
			
			$("input[name="+type+"Type]").val(totalKind);
			
		}else{
			$("input[name="+type+"Cnt]").val("0");
			$("input[name="+type+"Type]").val("");
		}
		
	}else if(type == 'wildAnml'){
		var totalKind = [];
		
		var tbodyLength = $("#"+type+"Table > tbody > tr").length;
		if(tbodyLength > 2){
			
			$("."+type+"CalcArea > input[name*="+type+"At]").each(function(idx){
				$("input[name="+type+"Cnt]").val(idx+1);
				totalKind.push($(this).val());
			});
			
			$("input[name="+type+"Kind]").val(totalKind);
			
		}else{
			$("input[name="+type+"Cnt]").val("0");
			$("input[name="+type+"Kind]").val("");
		}
	}
	
}
function fncDownloadAnalAll(svyId, mstId){
	$(".loading-div").show();
	
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	formdata.append("svyId",svyId);
	formdata.append("mstId",mstId);
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4){
			if(this.status == 200){
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
	xhr.open('POST', '/sys/vyt/frd/sct/selectDownloadAnalAll.do');
	xhr.responseType = 'blob';
	xhr.send(formdata);
}

function selectDownloadAnalPntAll(analType){
	$(".loading-div").show();
	
	var gidList = [];
	
	var fileInputs = $("input[name="+analType+"]");
	var fileLen = fileInputs.length;
	for (var i = 0; i < fileLen; i++) {
        var fileSn = $(fileInputs[i]).val();
        gidList.push(fileSn);
    }
	
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	formdata.append("gidList",gidList);
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4){
			if(this.status == 200){
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
	xhr.open('POST', '/sys/vyt/frd/sct/downloadAnalPntAll.do');
	xhr.responseType = 'blob';
	xhr.send(formdata);
}




/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	var hiddens = $("#listForm").find("input[name*=sch]");
	var form = $("<form></form>").attr("action","/sys/vyt/frd/sct/selectFrdSvyComptList.do").attr("method","post");
	
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
	
	$("#searchForm").attr("action","/sys/vyt/frd/sct/selectFrdSvyComptList.do");
//	var params = $("#searchForm *[name*=View]");
//	$.each(params,function(idx,elm){
//		var val = $(elm).val();
//		var hidden = $(elm).attr("name").replace("View","");
//		$("input[name=".concat(hidden,"]")).val(val);
//	});
	$("#searchForm").submit();
	
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

function dms2Degree(coord) {
	var decimalDegree;
	if(coord !="") {
	    var dmsPattern = /(\d+)°\s*(\d+)'\s*([\d\.]+)"/;
	    var match = coord.match(dmsPattern);

	    if (match) {
	        var degrees = parseFloat(match[1]);
	        var minutes = parseFloat(match[2]);
	        var seconds = parseFloat(match[3]);
	
	        decimalDegree = degrees + (minutes / 60) + (seconds / 3600);
	
	        return decimalDegree;
	    }
    }
}

function convertCoordi(x, y, s, t){
	
	var pt = new proj4.toPoint([x, y]);
	var s_srs = new proj4.Proj("EPSG:"+s);
	var t_srs = new proj4.Proj("EPSG:"+t);
	
	var result = proj4.transform(s_srs,t_srs,pt);
	var convertLon5186 = result.x;
	var convertLat5186 = result.y;
	
	return [convertLon5186, convertLat5186];
}


/*
 * 대상지 등록 관할청 
 */
function fncCompentAuthChange(){
	var compentAssort = $("#compentAssort").val();
	
	if(compentAssort == null || compentAssort == undefined || compentAssort.length == 0){
		$("#compentauth").empty().append("<option value>선택</option>");
		$("#subcompentauth").empty().append("<option value>선택</option>");
		return false;
	}
	
	$.ajax({
		url:"/sys/vyt/frd/sct/selectCompentAuth.do",
		type:"POST",
        data: {compentType:"compentAuth1",compentValue:compentAssort},
        dataType:"json",
        async: true,
        success:function(list){
        	if(list.result){
        		if(list.result.length > 0){
        			$("#compentauth").empty().append("<option value>선택</option>");
        			$("#subcompentauth").empty().append("<option value>선택</option>");
        			$.each(list.result,function(idx,item){
        				if(compentAssort == '국유림'){
        					$("#compentauth").append("<option value=\"".concat(item.code,"\">").concat(item.codeNm,"</option>"));
        				}else if(compentAssort == '민유림'){
        					$("#compentauth").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
        				}
        			});
        		}
        	}
        },
        error: function(request, status, error){
        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
        }
	});
}

/*
 * 대상지 등록 세부관할 
 */
function fncSubCompentAuthChange(){
	var compentauth = $("#compentauth").val();
	var compentAssort = $("#compentAssort").val();
	
	if(compentauth == null || compentauth == undefined || compentauth.length == 0){
		$("#subcompentauth").empty().append("<option value>선택</option>");
		return false;
	}
	
	$.ajax({
		url:"/sys/vyt/frd/sct/selectCompentAuth.do",
		type:"POST",
		data: {compentType:"compentAuth2",compentValue:compentauth},
		dataType:"json",
		async: true,
		success:function(list){
			if(list.result){
				if(list.result.length > 0){
					$("#subcompentauth").empty().append("<option value>선택</option>");
					$.each(list.result,function(idx,item){
						if(compentAssort == '국유림'){
							$("#subcompentauth").append("<option value=\"".concat(item.codeNm,"\">").concat(item.codeNm,"</option>"));
						}else if(compentAssort == '민유림'){
							$("#subcompentauth").append("<option value=\"".concat(item.adminNm,"\">").concat(item.adminNm,"</option>"));
						}
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
 * 현장사진 압축파일 다운로드
 * @param gid
 * @returns
 */
function fncDownloadPhotoListZip(gid){
	$(".loading-div").show();
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	
	formdata.append("gid",gid);
	xhr.open('POST', '/sys/vyt/frd/sct/selectDownloadPhotoListZip.do');
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
 * 조사완료 엑셀 다운로드
 */
function fnExcelDown(gid){
	
	$(".loading-div").show();
	
	var url = window.location.pathname;
	if(url.indexOf("selectFrdSvyComptDetail.do") > -1){
		url = url.replace(".do","Excel.do");
		var form = $("<form></form>").attr("action",url).attr("method","post");
		form.append($('<input/>', {type: 'hidden', name: "gid", value:gid }));
		form.appendTo("body").submit().remove();
	}
	
	$(".loading-div").hide();
	
}

/**
 * 대상지 분석 팝업표출
 * @param per
 * @returns
 */
function fnSldAnalPopup(svyLabel, mstId){
	
	
	$('<div id="analDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("selectSvyComptAnalPopup.do?svyLabel="+svyLabel+"&mstId="+mstId);
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 745,
		width: 900,
		title: "임도 대상지 분석"
	});
}

/**
 * 대상지 분석 체크박스 이벤트
 * @returns
 */
function fnCheckList(type){
	if( type == 'all'){
		if($("#checkAll").is(":checked")) $("input[name=checkList]").prop("checked", true);
		else $("input[name=checkList]").prop("checked", false);
	}else{
		var total = $("input[name=checkList]").length;
		var checked = $("input[name=checkList]:checked").length;
		
		if(total != checked) $("#checkAll").prop("checked", false);
		else $("#checkAll").prop("checked", true); 
	}
	
}

/**
 * 대상지 분석 등록
 * @param per
 * @returns
 */
function fnSvySldAnalRegist(svyLabel, mstId){
	
	var bs = $("input[name=bufferSize]").val();
	
	var analList = [];
	
	$("input[name='checkList']:checked").each(function() {
	    var checkVal = $(this).val();
	    if (analList.indexOf(checkVal) === -1) {
	        analList.push(checkVal);
	    }
	});
	
	if(analList.length == 0) {
		alert("분석종류를 선택해주세요.");
		return false;
	}
	
	if(confirm("공간분석 작업을 시작하시겠습니까?")){
		$(".loading-div").show();
		$.ajax({
			url:"/sys/vyt/frd/sct/selectSvyComptReportImgAll.do",
			type:"POST",
			traditional : true,
	    	data: {
	    		analList:analList,
	    		mstId:mstId,
	    		svyLabel:svyLabel,
	    		bufferSize:bs
		    },
	    	dataType:"json",
	        success:function(data){
	        	$(".loading-div").hide();
		    	if(data.status == "success"){
	    			alert(data.message);
	    			fnList("/sys/vyt/frd/sct/selectFrdSvyComptDetail.do");
	        	} else {
		    		alert(data.message);
	    		}
	        },
		    error: function(data){
		    	$(".loading-div").hide();
	    		alert(data.message);
	        },
	        complete: function(){
	        	$(".loading-div").hide();
	        }
		});
	}
}

/**
 * Point SHP파일 다운로드
 * @param per
 * @returns
 */
function fnDownloadAnalPnt(gid, analType, mstId, svyLabel) {
	
	$(".loading-div").show();
	
	var xhr = new XMLHttpRequest();
	var formdata = new FormData();
	formdata.append("gid",gid);
	formdata.append("analType",analType);
	formdata.append("mstId",mstId);
	formdata.append("svyLabel",svyLabel);
	
	xhr.onreadystatechange = function(){
		if (this.readyState == 4){
			if(this.status == 200){
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
	xhr.open('POST', '/sys/vyt/frd/sct/selectDownloadPointShp.do');
	xhr.responseType = 'blob';
	xhr.send(formdata);
	
}


/**
* 산림재해 재해유형 선택값 동적처리
* @param e, idx
* @returns
*/
function fnCheckOptionVal(e, idx){
	
	if(e == '기타'){
		$(".frstDsstrList"+idx+" > .frstDsstrCalcArea").append("<input type='text' name='frstDsstrDmgEtc_"+idx+"' value=''/>");
	}else{
		$("input[name=frstDsstrDmgEtc_"+idx+"]").remove();
	}
	
}
/**
 * 조사일자 월 선택 초기화
 */
function fncSvyYearChange(){
	$("#svyMonth").val("").prop("selected","true");
}
