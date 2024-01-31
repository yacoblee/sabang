$(document).ready(function() {
  	window.$app = window.$app || {};
	
	$app.func = $app.func || {};
	
    /*마이페이지 비밀번호 수정*/
    $('#modifyUserPassword').on('click', function(e) {
        var password = document.getElementById("newPassword")
        var confirm_password = document.getElementById("newPasswordConfirm");

        if (!isEmpty(password.value) && !isEmpty(confirm_password.value)) return alert("비밀번호를 입력하세요.");

        if (password.value == confirm_password.value) {
            $("#newPassword").val(password.value);
            updateUserPassword();
        } else {
            return alert("비밀번호를 확인하세요.");
        }
    });
	
	/* 비밀번호 변경 창 클릭 */
	$app.func.updatePassword = function() {
	    if ($("#popup_mask").hasClass('Hidden')) $("#popup_mask").removeClass('Hidden');
	}
	
    /*마이페이지 회원정보 수정*/
    $('#modifyMyinfo').on('click', function(e) {
        var mber_nm = $('#mberNm').val();
        var mberTelno = $('#mberTelno').val();
        /*var insttNm = $('#insttNm').val();
        var deptNm = $('#deptNm').val();*/


        if (!isEmpty(mber_nm)) {
            mber_nm = $('#mber_nm').data('whatever');
            alert("아이디를 입력하세요.");
        }
        if (!isEmpty(mberTelno)) {
            mberTelno = $('#mberTelno').data('whatever');
            alert("내선번호를 입력하세요.");
        }
        /*if (!isEmpty(insttNm)) {
            deptNm = $('#insttNm').data('whatever');
            alert("기관을 선택하세요.");
        }
        if (!isEmpty(deptNm)) {
            deptNm = $('#deptNm').data('whatever');
            alert("부서명을 입력하세요.");
        }*/

        //		$app.func.searchAjax('/updateUserInfo.do', 'POST', { "user_id" : user_id, "user_pswd" : user_pswd, "user_name" : user_name, "user_tel" : user_tel }, 'text');
        
        if(confirm("사용자 정보를 수정하시겠습니까?")) {
        	$("#updateForm").ajaxForm({
    			type: 'POST',
    	        url: '/sys/uia/updateUserInfo.do',
    	        dataType: "json",
    	        success: function(data) {
    	        	if (data.status == 200) {
                        alert("정상적으로 변경되었습니다.");
                        location.reload();
                    } else {
                        alert("저장에 실패했습니다.");
                    }
            },
            error: function(err) {
            	alert("저장에 실패했습니다.")
            }
    		}).submit();
        }
    });

/* 비밀번호 수정 창 닫기 */
$app.func.closeModal = function() {
    $('#popup_mask').addClass('Hidden');

    $("#oldPassword").val("");
    $("#oldPasswordConfirm").val(false);

    $("#newPassword").val("");
    $("#newPasswordConfirm").val("");

    $("#newPassword").val("");
    $("#newPasswordConfirm").val("");
}

/*마이페이지 비밀번호 수정 처리 */
function updateUserPassword() {
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
    var confPassword = $("#newPasswordConfirm").val();

/*    var passRule = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/; //최소 8 자, 최소 하나의 문자 및 하나의 숫자 

    if (!passRule.test(newPassword)) {
    	alert("비밀번호는 8자리 이상의 최소 하나의 문자,숫자 및 특수문자를 포함해주세요.")

        return false;
    } else */if (!isEmpty(oldPassword) || newPassword != confPassword || !isEmpty(newPassword)) {
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
     	        url: '/sys/uia/updateUserPswd.do',
     	        dataType: "json",
     	        success: function(data) {
                    if (data.status == 200) {
                        alert("정상적으로 변경되었습니다.");
                    } else {
                        alert("저장에 실패했습니다.")
                    }
             },
             error: function(err) {
            	 alert("저장에 실패했습니다.")
             },
             complete: function() {
                 window.location.href = window.location.href;
             }
     		}).submit();
	    }else{
	    	return false;
	    }
    }
}

/**
 * @author jooth
 * @name idCheck
 * @created 2020. 10. 20. 오후 5:47:23
 * @modifed 2020. 10. 20. 오후 5:47:23
 * @return any
 * @returns
 * @Description 
 */
function setUserId(e) {
	console.log('setUserId 사용');
    $(".overlapbtn").removeClass('Checked').removeAttr("disabled");
}

/*
 * @author podo
 * @name isEmpty
 * @param
 * @return
 * @description 공백체크
 */
function isEmpty(args) {
	console.log('isEmpty 사용');
    var result = true;

    if (!args || 0 === args.length) result = false;
    if (!args || (/^\s*$/).test(args)) result = false;
    if (!args || !args.trim()) result = false;

    return result;
}

/*
 * @author podo
 * @name hasSpc
 * @param
 * @return
 * @description 특수문자 체크
 */
function hasSpc(args) {
	console.log('hasSpc 사용');
    var result = true;

    if (!("/[~!@#$%^&*()_+|<>?:{}]/").test(args)) result = false;

    return result;
}

/*
 * @author podo
 * @name checkCharType
 * @param
 * @return
 * @description 영문 숫자 체크
 */
function checkCharType(args) {
	console.log('checkCharType 사용');
    var result = false;

    if ((/^[A-Za-z0-9+]*$/).test(args)) result = true;

    return result;

}
/*
 * @author podo
 * @name isValidate
 * @param
 * @return
 * @description 체크
 */
function isValidate(args) {
	console.log('isValidate 사용');
    var result = 0;

    if (args.length == 0) return;

    $.each(args, function(i, f) {
        if (!isEmpty(f)) result++;
    });

    return (result > 0) ? true : false;
}
});