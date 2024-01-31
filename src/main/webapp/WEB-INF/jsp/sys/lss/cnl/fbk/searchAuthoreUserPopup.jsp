<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="sub-upload">
	<div class="BoardList">
		<div class="tree">
			<c:forEach var="dept" items="${dept_list}" varStatus="status" >
			<fmt:parseNumber value="${dept.deptId}" var="deptId"/>
			<div>
				<c:choose>
				<c:when test="${status.first}">
				<a id="a_${deptId}" href="javascript:openCloseEx(<c:out value='${deptId}'/>,0)">
					<img src="/images/common/utl/menu_plus.gif" border="0" alt="">
				</a>
				</c:when>
				<c:when test="${status.last}">
				<a id="a_${deptId}" href="javascript:openCloseEx(<c:out value='${deptId}'/>,0)">
					<img src="/images/common/utl/menu_plusbottom.gif" border="0" alt="">
				</a>
				</c:when>
				<c:otherwise>
				<a id="a_${deptId}" href="javascript:openCloseEx(<c:out value='${deptId}'/>,0)">
					<img src="/images/common/utl/menu_plus.gif" border="0" alt="">
				</a>
				</c:otherwise>
				</c:choose>
				<input type="checkbox" id="chk_${deptId}" name="chk_${deptId}" class="check1" value="${deptId}">
				<label for="chk_${deptId}"><c:out value="${dept.deptNm}"/></label>
			</div>
			<c:forEach var="mber" items="${dept_mber_list}" varStatus="status1" >
			<c:if test="${mber.deptId eq dept.deptId}">
			<c:if test="${empty prevId || prevId ne dept.deptId}">
			<div class="deptMber_${deptId}" style="display:none;">
			</c:if>
			<div>
				<img src="/images/common/utl/menu_line.gif" border="0" align="absbottom" alt="">
				<img src="/images/common/utl/menu_join.gif" border="0" align="absbottom" alt="">
				<input type="checkbox" id="mber_${status1.count}" name="mber_${status1.count}" class="check2" value="${mber.esntlId}" <c:if test="${fn:contains(authorEsntlIdList, mber.esntlId)}">checked</c:if>>
				<label for="mber_${status1.count}"><c:out value="${mber.mberNm}"/></label>
			</div>
			<c:set var="prevId" value="${dept.deptId}"/>
			</c:if>
			<c:if test="${status1.last}">
			</div>
			</c:if>
			</c:forEach>
			
			</c:forEach>
			<script>
				var authorEsntlIdList = '<c:out value="${authorEsntlIdList}"/>';
				var creatNm = $("#insertForm div table:eq(0) input[type=hidden] ").val();
				
				$.each($(".tree div[class*=deptMber] div input[type=checkbox]"),function(){
					var uniqId = $(this).val();
					if(authorEsntlIdList.indexOf(uniqId) > -1){
						$(this).trigger("change");
						var deptMberDivClass = $(this).parent("div").parent("div").attr("class");
						var aDivClass = deptMberDivClass.replace("deptMber","a");
						var aDivHref = $("#".concat(aDivClass)).attr("href");
						var params = aDivHref.replace(/[a-zA-Z\(\\:)]/g,'');
						var openArr = params.split(",");

						if(openArr[1] == "0"){
							openCloseEx(openArr[0],parseInt(openArr[1]));
						}
						//$(this).parent("div").parent("div").show();
						if(creatNm == uniqId){
							$(this).attr("disabled","disabled");
						}
					}
				});
			</script>
		</div>
   </div>
   <div class="BtnGroup">
		<div class="BtnRightArea">
			<button type="button" class="add-btn" onclick="javascript:fnAddAuthorUser(); return false;"><spring:message code="button.confirm" /></button>
		</div>
	</div>
</div>