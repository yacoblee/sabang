<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<validator:javascript formName="passwordChgVO" staticJavascript="false" xhtml="true" cdata="false"/>
<c:set var="pageTitle"><spring:message code="mngUmt.userManage.title"/></c:set>
<!-- 백그라운드 창 -->
<div class="loginDiv" id="popup_mask">
	<!-- 백그라운드 창 -->
	<!-- 비밀번호 변경 창 -->
	<div class="popup" id="pswdPopup">
		<p class="pop_top">
			비밀번호&#32;변경<a href="javascript:closeModal();" class="btnpClose"><img src="/images/common/close.png" alt="닫기" /></a>
		</p>
		<div class="p_cont">
			<div class="tbspace">
				<form id="passwordChgForm" name="passwordChgVO" method="post" action="">
            	<input type="hidden" name="uId" value="<c:out value="${mberManageVO.uniqId}"/>">
				<table>
					<colgroup>
						<col width="30%" />
						<col width="70%" />
					</colgroup>
					<tbody>
						<tr>
							<td>현재&#32;비밀번호</td>
							<td>
								<Label for="oldPassword" class="Hidden">현재&#32;비밀번호</Label>
								<input type="password" class="layer_input" id="oldPassword" name="oldPassword" value="" title="기존 비밀번호">
								<input type="hidden" class="layer_input" id="oldPasswordConfirm" value="false"></td>
						</tr>
						<tr>
							<td>신규&#32;비밀번호</td>
							<td>
								<Label for="newPassword" class="Hidden">신규&#32;비밀번호</Label>
								<input type="password" class="layer_input" id="newPassword" name="newPassword" data-placement="top" title="영문숫자,특수문자 등의 조합으로 8자리 이상" value="">
							</td>
						</tr>
						<tr>
							<td>비밀번호&#32;확인</td>
							<td>
								<Label for="newPasswordConfirm" class="Hidden">비밀번호&#32;확인</Label>
								<input type="password" class="layer_input" id="newPasswordConfirm" name="newPasswordConfirm" value="" title="비밀번호 확인">
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>
			<!--// 표끝 -->
			<div class="bot_btn tc mt10">
				<button type="button" class="save-btn" id="modifyUserPassword">변경</button>
				<button type="button" class="close-btn" onclick="javascript:closeModal();">취소</button>
			</div>
		</div>
	</div>
</div>
<!-- 비밀번호 변경 창 -->

<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">관리서비스</a></li>
		<li><a href="#">사용자관리</a></li>
		<li><a href="#">${pageTitle}</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.update" /></h3>
	<div id="contents">
		<form id="updateForm" class="form-horizontal" role="form" method="post">
		<input type="hidden" name="uniqId" value="<c:out value="${mberManageVO.uniqId}"/>">
		<input type="hidden" name="groupId" value="<c:out value="${mberManageVO.groupId}"/>">
		<input type="hidden" name="mberSttus" value="<c:out value="${mberManageVO.mberSttus}"/>">
		<input type="hidden" name="sbscrbDe" value="<c:out value="${mberManageVO.sbscrbDe}"/>">
		<input type="hidden" name="lockAt" value="<c:out value="${mberManageVO.lockAt}"/>">
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.list"/>">
			<caption class="Hidden">${pageTitle} <spring:message code="title.create" /></caption>
				<tbody>
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageRegist.id"/></th>
					<td class="Title">
						<Label for="input_id" class="Hidden"><spring:message code="mngUmt.userManageRegist.id"/></Label>
						<input type="text" id="input_id" name="mberId" placeholder="<c:out value="${mberManageVO.mberId}"/>" value="<c:out value="${mberManageVO.mberId}"/>" disabled="disabled">
					</td>
				</tr>
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageRegist.name"/></th>
					<td>
						<Label for="input_name" class="Hidden"><spring:message code="mngUmt.userManageRegist.name"/></Label>
						<input type="text" id="input_name" name="mberNm" value="<c:out value="${mberManageVO.mberNm}"/>" data-whatever="<c:out value="${mberManageVO.mberNm}"/>">
					</td>
				</tr>
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageRegist.pass"/></th>
					<td>
						<button type="button" class="sub-btn" onclick="javascript:updatePassword();"><spring:message code="mngUmt.userManageModifyBtn.passwordChange"/></button>
						<button type="button" class="reset-btn" onclick="javascript:resetPassword();"><spring:message code="mngUmt.userManageModifyBtn.passReset"/></button>
						<input type="hidden" id="input_pswd" name="password" value="<c:out value="${mberManageVO.password}"/>" data-whatever="<c:out value="${mberManageVO.password}"/>">
						<!-- <button type="button" class="modify-btn" onclick="javascript:encryptPassword();">비밀번호 암호화</button> -->
					</td>
				</tr>
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageRegist.insttNm"/></th>
					<td>
						<Label for="insttId" class="Hidden"><spring:message code="mngUmt.userManageRegist.insttNm"/></Label>
						<select name="insttId" id="insttId">
							<option value="" <c:if test="${empty mberManageVO.insttId}">selected="selected"</c:if>>-- 선택 --</option>
							<option value="ORGNZT_0000000000000" <c:if test="${mberManageVO.insttId eq 'ORGNZT_0000000000000'}">selected="selected"</c:if>>한국치산기술협회</option>
							<option value="ORGNZT_0000000000001"  <c:if test="${mberManageVO.insttId eq 'ORGNZT_0000000000001'}">selected="selected"</c:if>>산림조합중앙회</option>
						</select>
					</td>
				</tr>
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageRegist.deptNm"/></th>
					<td>
						<Label for="deptId" class="Hidden"><spring:message code="comUatUia.myinfo.deptNm"/></Label>
						<select id="deptId" name="deptId">
							<c:choose>
								<c:when test="${not empty deptList}">
									<option value="">-- 선택 --</option>
									<c:forEach items="${deptList}" var="list" varStatus="status">
										<option value="<c:out value="${list.deptId}"/>" <c:if test="${list.deptId eq mberManageVO.deptId }">selected="selected"</c:if> ><c:out value="${list.deptNm }"/></option>							
									</c:forEach>
								</c:when>
								<c:otherwise>
									<option value="">-- 선택 --</option>
								</c:otherwise>
							</c:choose>
						</select>
					</td>
				</tr>				
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageRegist.ofcpsNm"/></th>
					<td>
						<Label for="ofcpsId" class="Hidden"><spring:message code="comUatUia.myinfo.ofcpsNm"/></Label>
						<select id="ofcpsNm" name="ofcpsNm">
							<c:choose>
								<c:when test="${mberManageVO.insttId eq 'ORGNZT_0000000000000'}">
									<option value="">-- 선택 --</option>
									<option value="회장" <c:if test="${mberManageVO.ofcpsNm eq '회장'}">selected="selected"</c:if>>회장</option>
									<option value="사무처장" <c:if test="${mberManageVO.ofcpsNm eq '사무처장'}">selected="selected"</c:if>>사무처장</option>
									<option value="연구조사처장" <c:if test="${mberManageVO.ofcpsNm eq '연구조사처장'}">selected="selected"</c:if>>연구조사처장</option>
									<option value="지부장" <c:if test="${mberManageVO.ofcpsNm eq '지부장'}">selected="selected"</c:if>>지부장</option>
									<option value="부장" <c:if test="${mberManageVO.ofcpsNm eq '부장'}">selected="selected"</c:if>>부장</option>
									<option value="차장" <c:if test="${mberManageVO.ofcpsNm eq '차장'}">selected="selected"</c:if>>차장</option>
									<option value="과장" <c:if test="${mberManageVO.ofcpsNm eq '과장'}">selected="selected"</c:if>>과장</option>
									<option value="대리" <c:if test="${mberManageVO.ofcpsNm eq '대리'}">selected="selected"</c:if>>대리</option>
									<option value="사원" <c:if test="${mberManageVO.ofcpsNm eq '사원'}">selected="selected"</c:if>>사원</option>
									<option value="계약직" <c:if test="${mberManageVO.ofcpsNm eq '계약직'}">selected="selected"</c:if>>계약직</option>
								</c:when>
								<c:when test="${mberManageVO.insttId eq 'ORGNZT_0000000000001'}">
									<option value="">-- 선택 --</option>
									<option value="계장" <c:if test="${mberManageVO.ofcpsNm eq '계장'}">selected="selected"</c:if>>계장</option>
									<option value="소장" <c:if test="${mberManageVO.ofcpsNm eq '소장'}">selected="selected"</c:if>>소장</option>
									<option value="팀장" <c:if test="${mberManageVO.ofcpsNm eq '팀장'}">selected="selected"</c:if>>팀장</option>
									<option value="과장" <c:if test="${mberManageVO.ofcpsNm eq '과장'}">selected="selected"</c:if>>과장</option>
									<option value="대리" <c:if test="${mberManageVO.ofcpsNm eq '대리'}">selected="selected"</c:if>>대리</option>
									<option value="책임" <c:if test="${mberManageVO.ofcpsNm eq '책임'}">selected="selected"</c:if>>책임</option>
									<option value="선임" <c:if test="${mberManageVO.ofcpsNm eq '선임'}">selected="selected"</c:if>>선임</option>
									<option value="연구원" <c:if test="${mberManageVO.ofcpsNm eq '연구원'}">selected="selected"</c:if>>연구원</option>
									<option value="사원" <c:if test="${mberManageVO.ofcpsNm eq '사원'}">selected="selected"</c:if>>사원</option>
								</c:when>
							</c:choose>
						</select>
					</td>
				</tr>				
				<tr>
					<th scope="row"><spring:message code="mngUmt.userManageList.phone"/></th>
					<td>
						<Label for="input_tel" class="Hidden"><spring:message code="mngUmt.userManageRegist.tel"/></Label>
						<input type="text" id="input_tel" name="mberTelno" value="<c:out value="${mberManageVO.mberTelno}"/>" data-whatever="<c:out value="${mberManageVO.mberTelno}"/>">
					</td>
				</tr>
				<tr>
					<th scope="row"><spring:message code="mngUmt.common.lockAt"/></th>
					<td>
					<c:if test="${mberManageVO.lockAt eq 'Y'}">예</c:if>
					<c:if test="${mberManageVO.lockAt == null || mberManageVO.lockAt eq '' || mberManageVO.lockAt eq 'N'}">아니오</c:if>
					<c:if test="${mberManageVO.lockAt eq 'Y'}">
					<button type="button" class="reset-btn" onclick="javascript:resetUserLogin();"><spring:message code="mngUmt.common.lockAtBtn"/></button>
					</c:if>
					</td>
				</tr>
				</tbody>
			</table>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="updateUserInfo()"><spring:message code="title.update"/></button>
					<button type="button" class="list-btn" onclick="location.href='/mng/umt/mberManage.do'"><spring:message code="title.list"/></button>
				</div>
			</div>
		</div>
		</form>
	</div>
</div>
<script>
document.addEventListener("DOMContentLoaded", function(){
	// 기업 명 변경 이벤트
	$('#insttId').on('change', insttNmListChangeListener);
});

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
				ofcpsOption = $('<option value="">-- 선택 --</option><option value="회장">회장</option><option value="사무처장">사무처장</option><option value="연구조사처장">연구조사처장</option><option value="지부장">지부장</option><option value="부장">부장</option><option value="차장">차장</option><option value="과장">과장</option><option value="대리">대리</option><option value="사원">사원</option><option value="계약직">계약직</option>');	
			}else{
				ofcpsOption = $('<option value="">-- 선택 --</option><option value="상무">상무</option><option value="부장">부장</option><option value="실장">실장</option><option value="국장">국장</option><option value="본부장">본부장</option><option value="센터장">센터장</option><option value="소장">소장</option><option value="원장">원장</option><option value="팀장">팀장</option><option value="연구소장">연구소장</option><option value="연구실장">연구실장</option><option value="대외협력관">대외협력관</option><option value="과장">과장</option><option value="책임연구원">책임연구원</option><option value="선임연구원">선임연구원</option><option value="대리">대리</option><option value="계장">계장</option><option value="주임">주임</option><option value="사원">사원</option><option value="연구원">연구원</option>');		
			}
			$('#ofcpsNm').append(ofcpsOption);
			$('#deptId').append("<option value=\"\">-- 선택 --</option>");
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
</script>