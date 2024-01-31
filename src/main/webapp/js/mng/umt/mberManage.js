/**
 * 
 */

$(document).ready(function() {
    $("#popup_mask").css("display", "none");
    $("#pswdPopup").css("display", "none");

    $('#modifyUserPassword').click(function() {
        var password = document.getElementById("newPassword")
        var confirm_password = document.getElementById("newPasswordConfirm");

        if (password.value == "") return alert("비밀번호를 입력하세요.")

        if (password.value == confirm_password.value) {
            $("#newPassword").val(password.value);
            updateUserPassword();
        } else {
            return alert("비밀번호를 확인하세요.");
        }
    })
    
    $('#searchKeywordView, #searchWrdView').keypress(function(event) {    		
    	if (event.which == 13) {
    		fnSearch();
    	}    	
    });
})

function updatePassword() {
    $("#pswdPopup").css({
        "top": "50%",
        "left": "50%",
        "transform": "translate(-50%,-50%)"
    });

    $("#popup_mask").css("display", "block");
    $("#pswdPopup").css("display", "block");
}

function closeModal() {
    $("#popup_mask").css("display", "none");
    $("#pswdPopup").css("display", "none");

    $("#oldPassword").val("");
    $("#oldPasswordConfirm").val(false);

    $("#newPassword").val("");
    $("#newPasswordConfirm").val("");

    $("#newPassword").val("");
    $("#newPasswordConfirm").val("");
}

function updateUserInfo() {
	if(confirm("사용자 정보를 수정하시겠습니까?")) {
//		$("#updateForm").attr("action","/mng/umt/memberSelectUpdt.do");
//		$("#updateForm").submit();
		$("#updateForm").ajaxForm({
			type: 'POST',
	        url: '/mng/umt/mberSelectUpdt.do',
	        dataType: "json",
	        success: function(data) {
	        	if (data.status == 200) {
                    alert("정상적으로 변경되었습니다.");
                    location.href="/mng/umt/mberManage.do";
                } else {
                    alert("저장에 실패했습니다.");
                }
        },
        error: function(err) {
        	alert("저장에 실패했습니다.")
        }
		}).submit();
	}
}

function updateUserPassword() {
    var user_id = document.getElementById("input_id");
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
    var confPassword = $("#newPasswordConfirm").val();

    var passRule = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/; //최소 8 자, 최소 하나의 문자 및 하나의 숫자


    /*if (!passRule.test(newPassword)) {
        alert("비밀번호는 8자리 이상의 최소 하나의 문자,숫자 및 특수문자를 포함해주세요.")
        return false;
    } else*/ if (oldPassword == "" || newPassword != confPassword || newPassword == "") {
        $("#oldPassword").val("");
        $("#newPassword").val("");
        $("#newPasswordConfirm").val("");
        return alert("비밀번호를 확인하세요.");
    }

    if (confirm("비밀번호를 변경하시겠습니까?")) {
    	var form = $("#passwordChgForm")[0];
    	
    	if(validatePasswordChgVO(form)){
    		$("#passwordChgForm").ajaxForm({
    			type: 'POST',
    	        url: '/mng/umt/mberPasswordUpdt.do',
    	        dataType: "json",
    	        success: function(data) {
	        		if (data.status == 200) {
	        			alert("정상적으로 변경되었습니다.");
	        			location.reload();
	        		} else {
	        			alert("저장에 실패했습니다.")
	        		}
	            },
	            error: function(err) {
	           	 	alert("저장에 실패했습니다.")
	            }
    		}).submit();
		}else{
			return false;
		}
    }
}

function resetUserLogin() {
	
	if(confirm("'" + $("#input_id").val() + "'의 로그인인증제한을 해제하시겠습니까?")) {
		$.ajax({
			url: "/mng/umt/mberLockIncorrect.do",
			type: "POST",
			data: {
				"uniqId": $("input[name=uniqId]").val()
			},
			dataType: "json",
			success: function (data) {
				if(data.status = 200) {
					alert("로그인인증제한을 해제하였습니다.");
					location.reload();
				}
				else {
					alert("로그인인증제한 해제를 실패하였습니다.\n관리자에게 문의하세요.");
				}
			},
			error: function (err) { console.log(err); }
		});
	}
}

function resetPassword() {
	if(confirm("'" + $("#input_id").val() + "'의 비밀번호를 초기화 하시겠습니까?")) {
		$.ajax({
			url: "/mng/umt/mberPasswordReset.do",
			type: "POST",
			data: { 
				"userId": $("#input_id").val(),
				"uniqId": $("input[name=uniqId]").val() 
				},
			dataType: "json",
			success: function (data) {
				if(data.status = 200) {
					alert("비밀번호를 초기화했습니다.");
					location.reload();
				}
				else {
					alert("비밀번호 초기화에 실패하였습니다.");
				}
			},
			error: function (err) { console.log(err); }
		});
	}
}

function fnSelectMember(uniqId){
	var form = $("<form></form>").attr("action","/mng/umt/mberSelectUpdtView.do").attr("method","post");
	form.append($('<input/>', {type: 'hidden', name: 'selectedId', value:uniqId }));
	form.appendTo("body");
	form.submit();
}

function fnDeleteMember(userId,uniqId) {

	if (confirm(userId + ' 회원을 삭제하시겠습니까?')) {
		$.ajax({
			type : 'POST',
			url : '/mng/umt/deleteMber.do',
			data : {
				"uniqId" : uniqId
			},
			dataType : 'json',
			success : function(data) {
				if(data.status = 200) {
					alert("회원이 삭제되었습니다.");
				}
				else {
					alert("회원 삭제를 실패하였습니다.\n관리자에게 문의하세요.");
				}
			},
			error : function(request, status) {
				console.log("error");
			},
			complete : function() {
				location.reload();
			}
		});
	}
}

function encryptPassword(){
	if(confirm("'" + $("#input_id").val() + "'의 비밀번호를 암호화 하시겠습니까?")) {
		$.ajax({
			url: "/mng/umt/mberPasswordEncrypt.do",
			type: "POST",
			data: { 
				"uniqId": $("input[name=uniqId]").val()
			},
			dataType: "json",
			success: function (data) {
				if(data.status = 200) {
					alert("비밀번호가 암호화되었습니다.");
					location.reload();
				}
				else {
					alert("비밀번호 암호화에 실패하였습니다.");
				}
			},
			error: function (err) { console.log(err); }
		});
	}
}

function fnAcceptJoin(userId,uniqId){
	if(confirm(userId+'회원의 회원가입을 승인하시겠습니까?')){
		$.ajax({
			type : 'POST',
			url : '/mng/umt/updateAcceptYMber.do',
			data : {
				"uniqId" : uniqId
			},
			dataType : 'json',
			success : function(data) {
				alert(data.message);
				if(data.status = 200) {
					location.reload();
				}
			},
			error : function(request, status) {
				console.log("error");
			}
		});
	}
}

function fnDeleteJoin(userId,uniqId){
	if(confirm(userId+'회원의 회원가입을 거절하시겠습니까?')){
		$.ajax({
			type : 'POST',
			url : '/mng/umt/updateAcceptNMber.do',
			data : {
				"uniqId" : uniqId
			},
			dataType : 'json',
			success : function(data) {
				alert(data.message);
				if(data.status = 200) {
					location.reload();
				}
			},
			error : function(request, status) {
				console.log("error");
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