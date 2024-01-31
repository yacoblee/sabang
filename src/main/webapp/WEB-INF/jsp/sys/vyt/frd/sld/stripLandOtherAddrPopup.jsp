<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="otherAddrDiv">
	<div class="BoardDetail">
		<c:set var="pageTitle"><spring:message code="sysVytFrd.stripLand.title"/></c:set>
		<ul>
			<c:forEach var="item" items="${parcelList}" varStatus="status">
				<li><c:out value="${item.addr}"/> <c:out value="${item.jibun}"/></li>						
			</c:forEach>
		</ul>
   </div>
</div>