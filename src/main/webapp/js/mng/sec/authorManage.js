/**
 * 
 */

$(document).ready(function() {
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
})

/* ******************************************************************************
 * 권한 관련 스크립트
 * ******************************************************************************/

/**
 * 권한 등록화면
 * @returns
 */
function fncAddAuthorInsert(){
	location.href="/mng/sec/ram/authorInsertView.do";
}

/**
 * 권한 등록
 * @returns
 */
function fncAuthorInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateAuthorManage(form)){
        	return false;
        }else{
			$("#listForm").ajaxForm({
				type: 'POST',
	        	url: form.action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/mng/sec/ram/authorList.do";
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
}

/**
 * 권한 상세조회
 * @param author
 * @returns
 */
function fncSelectAuthor(author) {
	$("input[name=authorCode]").val(author);
	$("#listForm").attr("action","/mng/sec/ram/author.do");
	$("#listForm").submit();
}

/**
 * 권한 수정화면
 * @param author
 * @returns
 */
function fncUpdateAuthorView(author){
	$("input[name=authorCode]").val(author);
	$("#listForm").attr("action","/mng/sec/ram/authorUpdateView.do");
	$("#listForm").submit();
}

/**
 * 권한 수정
 * @returns
 */
function fncAuthorUpdate() {
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateAuthorManage(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
	        	url: form.action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/mng/sec/ram/authorList.do";
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
}

/**
 * 권한 삭제
 * @param author
 * @returns
 */
function fncDeleteAuthor(author){
	if(confirm("삭제하시겠습니까?")) {
		$("input[name=authorCode]").val(author);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/sec/ram/authorDelete.do",
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/mng/sec/ram/authorList.do";
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

/* ******************************************************************************
 * 권한 롤 관련 스크립트
 * ******************************************************************************/

/**
 * 권한 롤 목록조회
 * @param author
 * @returns
 */
function fncSelectAuthorRole(author) {
	$("input[name=searchKeyword]").val(author);
	$("#listForm").attr("action","/mng/sec/rpm/authorRoleList.do");
	$("#listForm").submit();
}

/**
 * 권한 롤 등록화면
 * @returns
 */
function fncAddAuthorRoleInsert(){
	location.href="/mng/sec/rpm/authorRoleInsertView.do";
}

/**
 * 권한 롤 등록
 * @returns
 */
function fncAuthorRoleInsert(){
	if(confirm("등록하시겠습니까?")) {
		var form = $("#listForm")[0];
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: form.action,
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == "success") {
                    alert(data.message);
                    location.href="/mng/sec/rpm/authorRoleList.do";
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
 * 권한 롤 삭제
 * @returns
 */
function fncDeleteAuthorRole(authorCode, roleCode){
	if(confirm("삭제하시겠습니까?")) {
		$("input[name=authorCode]").val(authorCode);
		$("input[name=roleCode]").val(roleCode);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/sec/rpm/authorRoleDelete.do",
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/mng/sec/rpm/authorRoleList.do";
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

/* ******************************************************************************
 * 롤 관련 스크립트
 * ******************************************************************************/

/**
 * 롤 수정화면
 * @param author
 * @returns
 */
function fncUpdateRoleView(role){
	$("input[name=roleCode]").val(role);
	$("#listForm").attr("action","/mng/sec/rmt/roleUpdateView.do");
	$("#listForm").submit();
//	var hiddens = $("#listForm").find("input[name*=sch]");
//	var form = $("<form></form>").attr("action","/mng/sec/rmt/roleUpdateView.do").attr("method","post");
//
//	$.each(hiddens,function(idx,elm){
//		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
//	});
//	
//	form.append($('<input/>', {type: 'hidden', name: "roleCode", value:role }));
//	
//	form.appendTo("body").submit().remove();
}

/**
 * 롤 수정
 * @returns
 */
function fncRoleUpdate() {
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateRoleManage(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
	        	url: form.action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/mng/sec/rmt/roleList.do";
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
}

/**
 * 롤 삭제
 * @param role
 * @returns
 */
function fncDeleteRole(role){
	if(confirm("삭제하시겠습니까?")) {
		$("input[name=roleCode]").val(role);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/mng/sec/rmt/roleDelete.do",
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/mng/sec/rmt/roleList.do";
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
 * 롤 등록화면
 * @returns
 */
function fncAddRoleInsert(){
	location.href="/mng/sec/rmt/roleInsertView.do";
}

/**
 * 롤 등록
 * @returns
 */
function fncRoleInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
        if(!validateRoleManage(form)){
        	return false;
        }else{
        	$("#listForm").ajaxForm({
				type: 'POST',
	        	url: form.action,
	        	dataType: "json",
	        	success: function(data) {
	        		if (data.status == "success") {
                    	alert(data.message);
                    	location.href="/mng/sec/rmt/roleList.do";
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
}

/**
 * 롤 상세조회
 * @param roleCode
 * @returns
 */
function fncSelectRole(roleCode){
	$("input[name=roleCode]").val(roleCode);
	$("#listForm").attr("action","/mng/sec/rmt/role.do");
	$("#listForm").submit();
}
/* ******************************************************************************
 * 롤상하관계 관련 스크립트
 * ******************************************************************************/

/**
 * 롤상하관계등록화면
 * @param O
 * @returns
 */
function fncAddRoleHierarchyInsert(){
	location.href="/mng/sec/rhm/roleHierarchyInsertView.do";
}

/**
 * 롤상하관계등록
 * @returns
 */
function fncRoleHierarchyInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: form.action,
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/mng/sec/rhm/roleHierarchyList.do";
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

/* ******************************************************************************
 * 부서별 권한 관련 스크립트
 * ******************************************************************************/

/**
 * 부서명 조회
 * @param uniqId
 * @returns
 */
function searchAjax(url, params, callback) {
	$.ajax({
		url: url,
	    data: params,
	    dataType: "jsonp",
	    success: callback
	});
}

/* ******************************************************************************
 * 권한부여 관련 스크립트
 * ******************************************************************************/

/**
 * 권한부여 수정화면
 * @param uniqId
 * @returns
 */
function fncUpdateUserAuthorView(uniqId){
	$("input[name=uniqId]").val(uniqId);
	$("#listForm").attr("action","/mng/sec/urm/userAuthorSelectUpdtView.do");
	$("#listForm").submit();	
}

/**
 * 권한부여 수정
 * @returns
 */
function fncUserAuthorUpdate(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: form.action,
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                fnList("/mng/sec/urm/userAuthorList.do");
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

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/
function fnList(url){
	$("input[name=roleCode]").val("");
//	$("#listForm").attr("action",url);
//	$("#listForm").submit();
	var hiddens = $("#listForm").find("input[type=hidden]");
	var form = $("<form></form>").attr("action",url).attr("method","post");
	
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
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}