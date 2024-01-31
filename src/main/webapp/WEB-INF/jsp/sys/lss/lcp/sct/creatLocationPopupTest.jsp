<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
   <div class="BoardDetail">
	   <form id="insertForm" method="post" action="/sys/gis/als/creatLocationTest.do">
	       <table class="fieldBookTable ov">
		      <!-- 공유방번호 -->
          	  <tr>
				  <th>공유방번호</th>
				  <td>
				      <input type="text" name="mstId" value="544">
			      </td>
		      </tr>
		      <!-- 조사번호 -->
          	  <tr>
				  <th>조사번호</th>
				  <td>
				      <input type="text" name="sldId" value="강원-58">
			      </td>
		      </tr>
		      <!-- 위치도종류 -->
          	  <tr>
				  <th>위치도종류</th>
				  <td>
				      <select name="mapType">
				      	<option value="1">지적</option>
				      	<option value="2">영상</option>
				      </select>
			      </td>
		      </tr>
	       </table>
	       <div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="add-btn" onclick="javascript:fnCreatLocation(); return false;"><spring:message code="title.create" /></button>
				</div>
			</div>
	   </form>
   </div>
</div>