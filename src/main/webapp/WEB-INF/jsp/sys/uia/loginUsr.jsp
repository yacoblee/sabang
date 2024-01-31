<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<validator:javascript formName="mberVO" staticJavascript="false" xhtml="true" cdata="false"/>
<p class="SkipNav"><a href="#LOGINGo">로그인바로가기</a></p>
<div class="login">
	<div class="login_container">
		<h1><img src="<%=request.getContextPath() %>/images/common/logo2.png" alt="한국치산기술협회"></h1>
		<p class="capy">한국치산기술협회<br/><span>치산정보시스템</span>에 오신걸<br/>환영합니다.</p>
		<div class="login_right"> 
			<div class="login_content active"> <!-- 회원가입 버튼 누를시 active클래스 빼주세요 -->
				<form:form id="login" role="form" action="actionLogin.do" method="POST">
				<fieldset>
					<legend class="logintitle">LOGIN</legend>
					<label for="user_id" class="idlabel" ID="LOGINGo">아이디</label>
					<input type="text" id="user_id" name="id" placeholder="아이디를 입력하세요" /><br/>
					<label for="user_pswd" class="pwlabel">비밀번호</label>
					<input type="password" id="user_pswd" name="password" placeholder="비밀번호를 입력하세요"/>
<%-- 					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
					<button type="submit" id="submits">로그인</button>
				</fieldset>
				</form:form>
			</div>
			<div class="join"> 
				<a class="go_join" href="javascript:openJoinForm()">회원가입</a> <!-- 회원가입버튼 클릭시 Hidden 클래스 추가해주세요 -->
				<div class="join_content"><!-- 회원가입버튼 클릭시 active 클래스 추가해주세요 -->
					<form:form id="joinForm" role="form" commandName="mberVO" name="mberVO" action="" method="POST">
					<fieldset>
						<legend  class="logintitle">회원가입</legend>
						<label for="userName" >이름</label> <!-- label for값이랑 input id값이랑 똑같이해주세요  -->
						<input type="text" id="mberNm" name="mberNm" autocomplete="false"/><br/>
						<label for="userId">아이디</label>
						<input type="text" id="mberId" class="idinput" name="mberId" autocomplete="false" onkeyup="setUserId(event)"/>
						<button type="button" class="overlapbtn" onclick="idCheck()">중복확인</button>
						<br/>
						<label for="userPswd">비밀번호</label>
						<input type="password" id="mberPswd" name="mberPswd" autocomplete="false"/>
						<label for="userPswdCheck" >비밀번호확인</label>
						<input type="password" id="mberPswdCheck" name="mberPswdCheck"  autocomplete="false"/>
						<label for="insttId">기관명</label>
						<select name="insttId" id="insttId">
							<option value="">-- 선택 --</option>	
							<option value="ORGNZT_0000000000000">한국치산기술협회</option>
							<option value="ORGNZT_0000000000001">산림조합중앙회</option>
						</select>
						<br/>
						<label for="deptId">부서명</label>
						<select name="deptId" id="deptId">
							<option value="">-- 선택 --</option>
						</select>
						<br/>
						<label for="ofcpsNm">직책명</label>
						<select name="ofcpsNm" id="ofcpsNm">
							<option value="">-- 선택 --</option>
						</select>
						<br/>
						<label for="userTel">내선번호</label>
						<input type="text" id="mberTelno" name="mberTelno" placeholder="하이픈(-)을 제외하고 입력하세요." autocomplete="false"/>
						<button type="button" onclick="signUp()">회원가입</button>
						<button type="button" onclick="openJoinForm()">취소</button>
					</fieldset>
					</form:form>
				</div>
			</div>
<!-- 			<p class="loginex">농업정책보험금융원 통합 정보시스템은 크롬 브라우저와<br/> 해상도 1280px에  최적화 되어 있습니다.</p> -->
<!-- 			<P class="loginfoot"> Copyright ⓒ 농업정책보험금융원 All rights reserved.</P> -->
		</div>

	</div>
</div>

<script>
window.onload = function(){
	<c:if test="${not empty fn:trim(loginMessage) &&  loginMessage ne ''}">
    alert("loginMessage:<c:out value='${loginMessage}'/>");
    </c:if>
    
 	// 기업 명 변경 이벤트
	$('#insttId').on('change', insttNmListChangeListener);
}

function enterkey() {
    if (window.event.keyCode == 13) {
    	formSubmit()
    }
}

/**
 * @name insttNmListChangeListener
 * @param {Event}
 * @return 
 * @description 기업 명 변경 이벤트
 */
function insttNmListChangeListener(e) {
	e.preventDefault();
	
	var insttId = $(e.target).val();
	var url = '/system/appraisal/selectDeptNmList.do';
	var param = {
		insttId: insttId
	};
	searchAjax(url, param, deptNmListCallback);
}
 
 /**
 * @name deptNmListCallback
 * @param {Object}
 * @return 
 * @description 부서 목록조회 콜백함수
 */
var deptNmListCallback = function (response) {
	$('#deptId').empty();
	$('#ofcpsNm').empty();
	
	if(response.status == "success" ) {
		if(response.items.length > 0){
			var insttId = $('#insttId').val();
			var ofcpsOption;
			if(insttId == "ORGNZT_0000000000000"){
				ofcpsOption = $('<option value="회장">회장</option><option value="사무처장">사무처장</option><option value="연구조사처장">연구조사처장</option><option value="지부장">지부장</option><option value="부장">부장</option><option value="차장">차장</option><option value="과장">과장</option><option value="대리">대리</option><option value="사원">사원</option><option value="계약직">계약직</option>');		
			}else{
				ofcpsOption = $('<option value="상무">상무</option><option value="부장">부장</option><option value="실장">실장</option><option value="국장">국장</option><option value="본부장">본부장</option><option value="센터장">센터장</option><option value="소장">소장</option><option value="원장">원장</option><option value="팀장">팀장</option><option value="연구소장">연구소장</option><option value="연구실장">연구실장</option><option value="대외협력관">대외협력관</option><option value="과장">과장</option><option value="책임연구원">책임연구원</option><option value="선임연구원">선임연구원</option><option value="대리">대리</option><option value="계장">계장</option><option value="주임">주임</option><option value="사원">사원</option><option value="연구원">연구원</option>');		
			}
			$('#ofcpsNm').append(ofcpsOption);
			$.each(response.items, function (indx, data) {
				var option = $("<option value=\"" + ((data == null) ? '' : data.deptId) + "\">" + ((data == null) ? '' : data.deptNm) + "</option>");
				$('#deptId').append(option);
			});
			$('#deptId').trigger('change');
			$('#ofcpsNm').trigger('change');
		}else{
			$('#deptId').append("<option value=\"\">-- 선택 --</option>");
			$('#ofcpsNm').append("<option value=\"\">-- 선택 --</option>");
		}
		
	}else{
		$('#deptId').append("<option value=\"\">-- 선택 --</option>");
		$('#ofcpsNm').append("<option value=\"\">-- 선택 --</option>");
	}
}
 
/*
 * 공지사항 팝업 닫기 이벤트
 */
function fnNotice(e) {
	if( e == 'close'){
		$("#noticeArea").css("display","none");	
	}else {
		$(location).attr("href", "http://fdmis.or.kr");
	}
}
</script>