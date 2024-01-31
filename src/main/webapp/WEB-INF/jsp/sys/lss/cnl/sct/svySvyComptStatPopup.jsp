<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<div>
			<div>
				<c:choose>
					<c:when test="${sttusimg != null}">
						<div><img src="/storage/fieldBook<c:url value="${sttusimg}" />" alt="취약지역 해제조사 현황도" onerror="this.remove ? this.remove() : this.removeNode();" style="width: 350px;"></div>
					</c:when>
				</c:choose>
			</div>
    	</div>
    </div>
</div>