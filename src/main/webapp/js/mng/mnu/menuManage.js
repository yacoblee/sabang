/**
 * 
 */

$(document).ready(function() {
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
    
    $(document).on("keypress","#searchPopupKeyword",function(event){
    	if (event.which == 13) {
    		fnSelectProgramListSearch();
    	} 
    });
})

/* ********************************************************
 * 팝업처리
 ******************************************************** */
/**
 * 메뉴목록 팝업
 * @returns
 */
function fncPopupUpperMenuId(){
	$('<div id="upperMenuPopupDiv">').dialog({
		modal:true,
		open: function(){
			// 모달 오버레이 설정
			$(".ui-widget-overlay").css({
				opacity: 0.5,
				filter: "Alpha(Opacity=50)",
				backgroundColor: "black"
			});
			$(this).load("/mng/mnu/mpm/menuListSelectMvmnPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 600,
		width: 550,
		title: "메뉴목록"
	});
}

/**
 * 메뉴목록 선택
 * @param nodeNum
 * @returns
 */
function choiceNodes(nodeNum){
	var nodeValues = treeNodes[nodeNum].split("|");
	document.menuManageVO.upperMenuId.value = nodeValues[4];
	$('.ui-dialog-content').dialog('close');
}

/**
 * 프로그램목록 팝업
 * @returns
 */
function fncPopupProgrmFile(){
	$('<div id="prmPopupDiv">').dialog({
		modal:true,
		open: function(){
			// 모달 오버레이 설정
			$(".ui-widget-overlay").css({
				opacity: 0.5,
				filter: "Alpha(Opacity=50)",
				backgroundColor: "black"
			});
			$(this).load("/mng/prm/programListSearchPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 600,
		width: 900,
		title: "프로그램목록"
	});
}

/**
 * 팝업창 검색 및 페이징처리시 reload
 * @param params
 * @returns
 */
function fncPopupProgrmFileReload(params){
	$("#prmPopupDiv").empty();
	$("#prmPopupDiv").dialog("destroy");
	
	var urls = [];
	for(o in params){
		urls.push(o.concat("=",params[o]));
	}
	
	$('<div id="prmPopupDiv">').dialog({
		modal:true,
		open: function(){
			// 모달 오버레이 설정
			$(".ui-widget-overlay").css({
				opacity: 0.5,
				filter: "Alpha(Opacity=50)",
				backgroundColor: "black"
			});
			$(this).load("/mng/prm/programListSearchPopup.do".concat("?",urls.join("&")));
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 600,
		width: 900,
		title: "프로그램목록"
	});
}
/**
 * 프로그램목록 선택 처리
 * @param vFileNm
 * @returns
 */
function choisProgramListSearch(vFileNm) {
	//eval("opener.document.all."+opener.document.all.tmp_SearchElementName.value).value = vFileNm;
	//opener.document.menuManageVO.progrmFileNm.value = vFileNm;
	var parentFrom = parent.document.getElementsByTagName('form');
	parentFrom[0].progrmFileNm.value = vFileNm;
    parent.$('.ui-dialog-content').dialog('close');
}

/**
 * 팝업창 목록 페이징처리
 * @param pageNo
 * @returns
 */
function linkPopupPage(pageNo){
	var searchKeyword = $("input[name=searchKeyword]").val();
	var param = {"pageIndex":pageNo,"searchKeyword":searchKeyword};
	fncPopupProgrmFileReload(param);
}

/**
 * 팝업창 프로그램명 검색
 * @returns
 */
function fnSelectProgramListSearch(){
	$("input[name=searchKeyword]").val($("#searchPopupKeyword").val());
	var searchKeyword = $("input[name=searchKeyword]").val();
	$("input[name=pageIndex]").val(1);
	var pageNo = $("input[name=pageIndex]").val();
	
	var param = {"pageIndex":pageNo,"searchKeyword":searchKeyword};
	fncPopupProgrmFileReload(param);
}
/* ******************************************************************************
 * 메뉴목록 관련 스크립트
 * ******************************************************************************/

/**
 * 메뉴 상세화면
 * @param codeId
 * @returns
 */
function fncMenuManageDetail(menuNo){
	$("input[name=req_menuNo]").val(menuNo);
	$("#listForm").attr("action","/mng/mnu/mpm/menuManageListDetail.do");
	$("#listForm").submit();
}

/**
 * 메뉴 등록화면
 * @returns
 */
function fncInsertMenuManageView(){
	location.href="/mng/mnu/mpm/menuRegistInsertView.do";
}

/**
 * 메뉴등록
 * @returns
 */
function fncInsertMenuManage(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateMenuManageVO(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
    			type: "POST",
    			dataType: "json",
    			success: function(data){
    				if(data.status == "success"){
    					alert(data.message);
    					location.href="/mng/mnu/mpm/menuManageSelect.do";
    				}
    			},
    			error: function(data){
    				alert(data.message);
    			}
    		}).submit();
        }
    }
}

/**
 * 메뉴삭제
 * @returns
 */
function fncDeleteMenuManage() {
	if(confirm("삭제하시겠습니까?")){
		$("#listForm").ajaxForm({
			type: "POST",
			url: "/mng/mnu/mpm/menuManageDelete.do",
			dataType: "json",
			success: function(data){
				if(data.status == "success"){
					alert(data.message);
					location.href="/mng/mnu/mpm/menuManageSelect.do";
				}
			},
			error: function(data){
				alert(data.message);
			}
		}).submit();
	}
}

/**
 * 메뉴수정 화면
 * @returns
 */
function fncUpdateMenuManageView(){
	$("#listForm").attr("action","/mng/mnu/mpm/menuManageListUpdateView.do");
	$("#listForm").submit();
}

/**
 * 메뉴수정
 * @returns
 */
function fncUpdateMenuManage(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
		if(!validateMenuManageVO(form)){
			return;
		}else{
			$("#listForm").ajaxForm({
				type: "POST",
				dataType: "json",
				success: function(data){
					alert(data.message);
					if(data.status == "success"){
						location.href="/mng/mnu/mpm/menuManageSelect.do";
					}
				},
				error: function(data){
					alert(data.message);
				}
			}).submit();
		}
	}
}

/**
 * 메뉴 일괄등록화면
 * @returns
 */
function fncmenuBndeRegistView(){
	$("#listForm").attr("action","/mng/mnu/mpm/menuBndeRegistView.do");
	$("#listForm").submit();
}

/**
 * 메뉴 일괄등록
 * @returns
 */
function fncBndeInsertMenuManage(){
	if(confirm("메뉴 일괄등록을 하시겠습니까?. \n 메뉴정보와  프로그램목록 존재시 삭제 하실 수 없습니다.")){
	   if(checkFile()){
		   $("#listForm").ajaxForm({
				type: "POST",
				dataType: "json",
				success: function(data){
					if(data.status == "success"){
						alert(data.message);
						window.location.reload();
					}
				},
				error: function(data){
					alert(data.message);
				}
			}).submit();
		   //document.menuManageRegistForm.action ="<c:url value='/sym/mnu/mpm/EgovMenuBndeRegist.do' />";
	      //document.menuManageRegistForm.submit();
	   }
	}
}

/**
 * 메뉴일괄삭제
 * @returns
 */
function fncBndeDeleteMenuList() {
	if(confirm("메뉴일괄삭제를 하시겠습니까?. \n 메뉴정보와  프로그램목록 데이터 모두 삭제 됩니다.")){
		$("#listForm").ajaxForm({
			type: "POST",
			url: "/mng/mnu/mpm/menuBndeAllDelete.do",
			dataType: "json",
			success: function(data){
				if(data.status == "success"){
					alert(data.message);
					window.location.reload();
				}
			},
			error: function(data){
				alert(data.message);
			}
		}).submit();
	}
}

/**
 * 메뉴일괄등록시 등록파일 체크 함수
 * @returns
 */
function checkFile(){
	var menufile = $("input[name=file]").val();
	if(menufile==""){
	   alert("업로드 할 파일을 지정해 주세요");
	   return false;
	}

	var  str_dotlocation,str_ext,str_low;
	//str_value  = document.menuManageRegistForm.file.value;
	str_low   = menufile.toLowerCase(menufile);
	str_dotlocation = str_low.lastIndexOf(".");
	str_ext   = str_low.substring(str_dotlocation+1);

	switch (str_ext) {
	  case "xls" :
	  case "xlsx" :
		 return true;
	     break;
	  default:
	     alert("파일 형식이 맞지 않습니다.\n xls,xlsx만 업로드가 가능합니다."); //
	     return false;
	}
}
/* ******************************************************************************
 * 메뉴생성 관련 스크립트
 * ******************************************************************************/

/**
 * 메뉴생성 화면 호출
 * @param vAuthorCode
 * @returns
 */
function selectMenuCreat(vAuthorCode) {
	$("input[name=authorCode]").val(vAuthorCode);
	$("#listForm").attr("action","/mng/mnu/mcm/menuCreatSelect.do");
	$("#listForm").submit();
}

/**
 * 메뉴생성 멀티입력 처리 함수
 * @returns
 */
function fInsertMenuCreat() {
	var checkField = $("input[name=checkField]");
    var checkMenuNos = "";
    var checkedCount = 0;
    if(checkField) {
    	if(checkField.length > 1) {
    		checkField.each(function(idx,elm){
        		if($(elm).is(":checked")){
        			checkMenuNos += ((checkedCount==0? "" : ",") + $(elm).val());
        			checkedCount++;
        			console.log("checkMenuNos = "+checkMenuNos);
                    console.log("checkedCount = "+checkedCount);
        		}
        	});
        } else {
        	if(checkField.is(":checked")){
        		checkMenuNos = checkField.val();
        	}
        }
    }
    if(checkedCount == 0){
        alert("선택된 메뉴가 없습니다.");
        return false;
    }
    if(confirm("생성하시겠습니까?")){
    	$("input[name=checkedMenuNoForInsert]").val(checkMenuNos);
        $("input[name=checkedAuthorForInsert]").val($("input[name=authorCode]").val());
        
		$("#listForm").ajaxForm({
			type: "POST",
			dataType: "json",
			success: function(data){
				if(data.status == "success"){
					alert(data.message);
					location.href = "/mng/mnu/mcm/menuCreatManageSelect.do";
				}
			},
			error: function(data){
				alert(data.message);
			}
		}).submit();
	}
//    document.menuCreatManageForm.checkedMenuNoForInsert.value=checkMenuNos;
//    document.menuCreatManageForm.checkedAuthorForInsert.value=document.menuCreatManageForm.authorCode.value;
//    document.menuCreatManageForm.action = "<c:url value='/mng/mnu/mcm/EgovMenuCreatInsert.do'/>";
//    document.menuCreatManageForm.submit();
}

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(){
	$("#listForm").attr("action","/mng/mnu/mpm/menuManageSelect.do");
	$("#listForm").submit();
}
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