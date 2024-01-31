<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="svyComptVO" commandName="svyComptVO" method="post" action="/sys/lss/lcp/sct/insertShpFile.do">
        <div class="drag-div drag-active">
			<p class="drag-msg noselect">파일을 드래그하세요.</p>
		</div>
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnUpdateLcpSvyCompt(); return false;"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
    <img class="loading-img" style='display:none;' src="<%=request.getContextPath() %>/images/common/progressbar_blue.gif" alt="로딩바 이미지">
</div>

<script>
function fnUpdateLcpSvyCompt(){
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
		$(".loading-img").show();
		
		var jqXHR=$.ajax({
			url: $("#insertForm")[0].action,
			type:"POST",
			contentType:false,
	        processData: false,
	        cache: false,
	        data: $app.upload.form,
	        dataType:"json",
	        beforeSend: function(){
	        },
	        success: function(data){
	        	$(".loading-img").hide();
	        	if(data.message){
	        		alert(data.message);
	        	}
	        	if(data.status == "success"){
	        		$app.upload = null;
	        		window.location.reload();
	        	}
	        },
	        error: function(request, status, error){
	        	$(".loading-img").hide();
	        }
		});
	}
}
</script>