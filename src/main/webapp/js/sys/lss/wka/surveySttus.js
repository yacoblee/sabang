var svySum = '${svySum}';
$(document).ready(function() {
    $('#searchKeyword').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
    
//    c3.generate({
//    	bindto:"#barSurveyAll",
//    	data:{
//    		type:"bar",
//    		json:JSON.parse(${svyMonth}),
//    		keys:{
//    			x:"nm",
//    			value:["strp","comp"]
//    		}
//    	},
//    	bar:{
//    		width:50
//    	}
//    });
    
//    c3.generate({
//    	bindto:"#pieSurveyAll",
//    	data:{
//    		json:'<c:out value="${svySum}"/>',
//    		type:"pie"
//    	}
//    });
    
    c3.generate({
    	bindto:"#barSurveyMonth",
    	data:{
    		columns:[
    			["조사수",25,20,10,5,11,15,2,0,0,3,1,0]
    		],
    		type:"bar"
    	},
    	axis:{
    		x:{
    			type:"category",
    			categories:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
    		}
    	},
    	bar:{
    		width:50
    	}
    });
});

function fnInsertWkaSvyPopultnPopup(){
	$('<div id="uploadDiv">').dialog({
		modal:true,
		open: function(){
			$(this).load("insertWkaSvyPopultnPopup.do");
		},
		close: function(e){
			$(this).empty();
			$(this).dialog("destroy");
		},
		height: 200,
		width: 400,
		title: "기초조사 모집단 변경"
	});
}

function fnInsertWkaSvyPopultn(){
	
	var mberId = $('#mberId').val();
	var popultnCnt = $('#popultnCnt').val();
	
	if(popultnCnt.length == 0){
		alert("모집단 건수를 입력하세요.");
		return;
	}
		
	if(confirm("취약지역실태조사 모집단 건수를 변경하시겠습니까?")) {		
		$.ajax({
			url:"/sys/lss/wka/sts/insertWkaSvyPopultn.do",
			type:"POST",
        	data: {
        		mberId: mberId,
        		popultnCnt: popultnCnt
    	    },
        	dataType:"json",
	        success:function(data){
				if(data.status == "success"){
	        		alert(data.message);
	        		window.location.reload();
	        	} else {
	        		alert(data.message);
	        	}
	        },
    	    error: function(data){
        		alert(data.message);
	        }
		});
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
	var params = $("#listForm *[name*=View]");
	$.each(params,function(idx,elm){
		var val = $(elm).val();
		var hidden = $(elm).attr("name").replace("View","");
		$("input[name=".concat(hidden,"]")).val(val);
	});
	$("#listForm").submit();
}