/**
 * 
 */

$(document).ready(function() {
    $('.search input').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
})

/**
 * 검색
 * @returns
 * @Description
 */
function fnSearch(){
	$("input[name=pageIndex]").val(1);
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

/**
 * 엑셀다운로드
 * @returns
 * @Description
 * *.do -> Excel.do 로 변경
 * form 자식 element 중 input hidden 객체만 추출하여 새 form에 추가
 */
function fnExcelDown(){
	var url = $("#listForm").attr("action");
	var hiddens = $("#listForm").find("input[type=hidden]");
	
	url = url.replace(".do","Excel.do");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name"), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}