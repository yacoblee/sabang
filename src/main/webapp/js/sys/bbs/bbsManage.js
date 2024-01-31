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
 * 게시판 관련 스크립트
 * ******************************************************************************/

/**
 * 게시판 상세화면
 * @param codeId
 * @returns
 */
function fncArticleDetail(nttId){
	$("input[name=nttId]").val(nttId);
	$("#listForm").attr("action","/sys/bbs/art/selectArticleDetail.do");
	$("#listForm").submit();
}


/**
 * 게시판 등록화면
 * @returns
 */
function fncAddArticleInsert(){
	location.href="/sys/bbs/art/articleRegistView.do";
}


/**
 * 게시물 등록
 * @returns
 */
function fncArticleInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: form.action,
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/sys/bbs/art/articleList.do";
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
 * 게시물 수정화면
 * @returns
 */
function fncUpdateArticleView(nttId){
	$("input[name=nttId]").val(nttId);
	$("#listForm").attr("action","/sys/bbs/art/updateArticleView.do");
	$("#listForm").submit();
}

/**
 * 게시물 수정
 * @returns
 */
function fncArticleUpdate(){
	if(confirm("수정하시겠습니까?")){
        var form = $("#listForm")[0];
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: form.action,
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/sys/bbs/art/articleList.do";
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
 * 게시물 삭제
 * @param codeId
 * @returns
 */
function fncArticleDelete(nttId){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=nttId]").val(nttId);
		$("#listForm").ajaxForm({
			type: 'POST',
	        url: "/sys/bbs/art/articleDelete.do",
	        dataType: "json",
	        success: function(data) {
        	if (data.status == "success") {
                alert(data.message);
                location.href="/sys/bbs/art/articleList.do";
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
 * 게시판 댓글 관련 스크립트
 * ******************************************************************************/

/**
 * 댓글 등록
 * @returns
 */
function fncArticleCommentInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#articleCommentVO");
		form.submit();
    }
}

/**
 * 댓글 수정 
 * @returns
 */
function fncUpdateArticleCommentView(commentNo){
	$(".comment_"+commentNo).css("display","block");
	$(".comment_"+commentNo).focus();
	$(".btn_"+commentNo+" .btn_u").after(`<span class="btn_r"><a href="javascript:fncCancelArticleCommentUpdate(`+commentNo+`)">취소</a></span>`);
	$(".btn_"+commentNo+" .btn_u a").attr("href","javascript:fncArticleCommentUpdate("+commentNo+")");
}

/**
 * 댓글 수정 
 * @returns
 */
function fncArticleCommentUpdate(commentNo){
	if(confirm("해당 댓글을 수정하시겠습니까?")){
		$(".btn_"+commentNo+" .btn_r").remove();

		$("input[name=commentNo]").val(commentNo);
		var comment = $(".comment_"+commentNo).val();
		$("textarea[name=commentCn]").val(comment);
		
		$("#articleCommentVO").attr("action","/sys/bbs/art/articleCommentUpdate.do");
		$("#articleCommentVO").submit();
	}
}

/**
 * 댓글 수정 취소
 * @returns
 */
function fncCancelArticleCommentUpdate(commentNo){
	$(".comment_"+commentNo).css("display","none");
	$(".btn_"+commentNo+" .btn_u a").attr("href","javascript:fncUpdateArticleCommentView("+commentNo+")");
	$(".btn_"+commentNo+" .btn_r").remove();
}


/**
 * 댓글 삭제
 * @returns
 */
function fncArticleCommentDelete(commentNo){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=commentNo]").val(commentNo);
		$("#articleCommentVO").attr("action","/sys/bbs/art/articleCommentDelete.do");
		$("#articleCommentVO").submit();
	}
}

/* ******************************************************************************
 * 공지사항 관련 스크립트
 * ******************************************************************************/

/**
 * 공지사항 상세화면
 * @param codeId
 * @returns
 */
function fncNoticeDetail(nttId){
	$("input[name=nttId]").val(nttId);
	$("#listForm").attr("action","/sys/bbs/ntb/selectNoticeDetail.do");
	$("#listForm").submit();
}


/**
 * 공지사항 등록화면
 * @returns
 */
function fncAddNoticeInsert(){
	location.href="/sys/bbs/ntb/noticeRegistView.do";
}


/**
 * 공지사항 등록
 * @returns
 */
function fncNoticeInsert(){
	if(confirm("등록하시겠습니까?")){
		var form = $("#listForm")[0];
		form.submit();
    }
}

/**
 * 공지사항 수정화면
 * @returns
 */
function fncUpdateNoticeView(nttId){
	$("input[name=nttId]").val(nttId);
	$("#listForm").attr("action","/sys/bbs/ntb/updateNoticeView.do");
	$("#listForm").submit();
}

/**
 * 공지사항 수정
 * @returns
 */
function fncNoticeUpdate(){
	if(confirm("수정하시겠습니까?")){
		var form = $("#listForm")[0];
        form.submit();
    }
}

/**
 * 공지사항 삭제
 * @param codeId
 * @returns
 */
function fncNoticeDelete(nttId){
	if(confirm("삭제하시겠습니까?")){
		$("input[name=nttId]").val(nttId);
		$("#listForm").attr("action","/sys/bbs/ntb/noticeDelete.do");
		$("#listForm").submit();
	}
}

/* ******************************************************************************
 * 공통 스크립트
 * ******************************************************************************/

/**
 * 검색
 * @returns
 * @Description
 */
function fnSearch(){
	$("input[name=pageIndex]").val(1);
	
	var pageUnit = $("select[name=pageSelect]").val();
	if(pageUnit != null && pageUnit != '' && pageUnit != undefined) $("input[name=pageUnit]").val(pageUnit);
	
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}

function fnList(e){
	var hiddens = $("#listForm").find("input[name*=sch]");
	
	var form;
	
	if(e == 'notice'){
		form = $("<form></form>").attr("action","/sys/bbs/ntb/noticeList.do").attr("method","post");
	}else{
		form = $("<form></form>").attr("action","/sys/bbs/art/articleList.do").attr("method","post");
	}
	
	
	$.each(hiddens,function(idx,elm){
		form.append($('<input/>', {type: 'hidden', name: $(elm).attr("name").replace("sch",""), value:$(elm).val() }));
	});
	
	form.appendTo("body").submit().remove();
}