<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="svyComptVO" commandName="svyComptVO" method="post" action="/sys/lss/wka/sct/updateWkaSvyComptExcel.do">
        <div class="drag-div drag-active">
			<p class="drag-msg noselect">파일을 드래그하세요.</p>
		</div>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnUpdateBscSvyCompt(); return false;"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
</div>

<script>
function fnUpdateBscSvyCompt(){
	var returnStatus = false;
	if($app.upload == null || $app.upload == undefined || $app.upload.form == undefined){
		alert("파일이 선택되지 않았습니다.");
		return false;
	}
	
	$("#insertForm *[name]").each(function(idx,elm){
		if($(elm).val() == null || $(elm).val() == ""){
			var th = $(elm).parent().prev("th").text();
			alert(th == "" ? "입력되지 않은 정보가 있습니다." : th.concat(" 정보가 입력되지 않았습니다."));
			returnStatus = true;
			return false;
		}
	});
	
	if(returnStatus){
		return;
	}
	
	if(confirm("등록하시겠습니까?")) {
		
//		$app.upload.form.set("mst_corpname",$("#mst_corpname").val());
//		$app.upload.form.set("mst_partname",$("#mst_partname").val());
//		$app.upload.form.set("mst_create_user",$("#mst_create_user").val());
//		$app.upload.form.set("mst_password",$("#mst_password").val());
//		$app.upload.form.set("mst_adminpwd",$("#mst_adminpwd").val());
//		$app.upload.form.set("mst_readpwd",$("#mst_readpwd").val());
//
//		if($app.upload.form.get("svyType")){
//			$app.upload.form.set('svyType',$("#svyType").val());
//		}else{
//			$app.upload.form.append('svyType',$("#svyType").val());
//		}
		
		$(".drag-div").append('<div class="progress"><div></div></div>');
		
		var jqXHR=$.ajax({
			//url:"/sys/fck/apr/sld/uploadStripLandExcel.do",
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
	        	//$(".sub-upload").prepend('<div class="loading-div"><div class="loading-icon"></div></div>');
	        },
	        success: function(data){
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
	        },
	        error: function(request, status, error){
	        	$(".loading-div").remove();
	        	$(".progress").remove();
	        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
	        }
		});
//		$("#insertForm").ajaxForm({
//			type: 'POST',
//			url: $("#insertForm")[0].action,
//	        dataType: "json",
//	        success: function(data) {
//	        	if (data.status == "success") {
//	        		console.log(data);
//                    alert(data.message);
//                    window.location.reload();
//                } else {
//                    alert(data.message);
//                }
//        	},
//        	error: function(data) {
//        		alert(data.message);
//        	}
//		}).submit();
	}
}
</script>