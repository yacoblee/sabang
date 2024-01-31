/**
 * 주제도 바로가기
 * @param layer
 * @returns
 */
function fncGoToLayer(layer){
	var url = "/sys/gis/mainMap.do";
	var form = $("<form></form>").attr("action",url).attr("method","post");
	form.append($('<input/>', {type: 'hidden', name: "layer", value:layer }));
	form.appendTo("body").submit().remove();
}

/**
 * 도움말 바로가기
 * @returns
 */
function fncGoToHpcm(){
	alert("도움말 등록예정입니다.");
}

/**
 * 전자야장APK 다운로드 바로가기
 * @returns
 */
function fncGoToApk(){
	alert("전자야장 APK 등록예정입니다.");
}