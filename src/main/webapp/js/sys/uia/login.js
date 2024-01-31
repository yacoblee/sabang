/**
 * 로그인 관련 스크립트
 */
$(document).ready(function() {
	/*로그인버튼 클릭*/
    $('#submits').on('click', function() {
        var id = $("input[id='user_id']").val();
        var pswd = $("input[id='user_pswd']").val();

        if (!isEmpty(id)) {
            alert("아이디를 입력해 주세요");
            return false;
        }
        if (!checkCharType(id)) {
            alert("아이디는 영문 숫자만 입력 해주시기 바랍니다.");
            return false;
        }
        if (!isEmpty(pswd)) {
            alert("비밀번호를 입력해 주세요")
            return false;
        }
    });
});

/*회원가입버튼 클릭*/
function openJoinForm(e) {
    if (!$('.go_join').hasClass('Hidden')) {
        $('.go_join').addClass('Hidden');
        $('.login_content').removeClass('active');
        $('.join_content').addClass('active');
    } else {
        $('.go_join').removeClass('Hidden');
        $('.login_content').addClass('active');
        $('.join_content').removeClass('active');
    }
}

/*회원가입 */
function signUp() {
    var mberPswd = $('#mberPswd').val();
    var mberPswdCheck = $('#mberPswdCheck').val();
    
    if (mberPswd != mberPswdCheck) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    } else if (!$(".overlapbtn").hasClass('Checked')) {
        alert("중복체크를 하지 않았습니다.");
        $(".overlapbtn").removeClass('Checked').removeAttr("disabled");
        return false;
    } else {
    	if(confirm("등록하시겠습니까?")){   		
    		var form = $("#joinForm")[0];
    		
    		if(validateMberVO(form)){
    			$("#joinForm").ajaxForm({
        			type: 'POST',
        	        url: '/signup.do',
        	        dataType: "json",
        	        success: function(data) {
        	        	if(data.status == "200"){
        	        		alert("회원가입이 요청되었습니다.");
        	        	}else{
        	        		alert("회원가입에 실패하였습니다.");
        	        	}	
                },
                error: function(err) {
                	alert("회원가입에 실패하였습니다.");
                },
                complete: function() {
                    window.location.href = window.location.href;
                }
        		}).submit();
    		}
    	}
    }
}

function idCheck() {
    var mberId = $('#mberId').val();
    var flag = "false";

    if (!isEmpty(mberId)) {
        alert("아이디를 입력하여 주시기 바랍니다.");
        $('#mberId').focus();
        return false;
    } else if (!checkCharType(mberId)) {
        alert("아이디는 영문 및 숫자만 입력 해주시기 바랍니다.");
        $('mberId').focus();
        return false;
    } else {
        $.ajax({
            type: 'POST',
            url: '/idCheck.do',
            dataType: 'json',
            data: {
            	mberId: mberId
            },
            success: function(data) {
                flag = data;
            },
            beforeSend: function(xhr) {
                $("#mberId").addClass("check");
                $('.overlapbtn').addClass('Checked').attr("disabled");
            },
            error: function(request, status) {
                $("#mberId").removeClass("check");
                $('.overlapbtn').removeClass('Checked').removeAttr("disabled");
                console.log("error");
            },
            complete: function() {
                if (flag.usedCnt == "false") {
                    $('.overlapbtn').addClass('Checked').attr("disabled");
                    alert("사용가능한 아이디입니다.");
                } else {
                    $("#mberId").removeClass("check");
                    $('.overlapbtn').removeClass('Checked').removeAttr("disabled");
                    alert("이미 사용중인 아이디입니다.")
                }
            }
        });
    }
}

function setUserId(e) {
    $(".overlapbtn").removeClass('Checked').removeAttr("disabled");
}

function updatePassword() {
    if ($("#popup_mask").hasClass('Hidden')) $("#popup_mask").removeClass('Hidden');
}
