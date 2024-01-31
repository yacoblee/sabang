<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="sub-upload">
    <div class="BoardDetail">
    	<form:form id="insertForm" modelAttribute="fieldBookVO" commandName="fieldBookVO" method="post" action="/sys/lss/lcp/fbk/insertCnrsSpceSld.do">
    	<input type="hidden" value="${mst_id}" name="mst_id" />
    	<input name="sldStdgCd" type="hidden" value="">
    	<input name="sldCnt" type="hidden" value="">
	   	<input name="gvfStdgCd" type="hidden" value="">
	   	<input name="gvfCnt" type="hidden" value="">
    	<table class="fieldBookTable ov">
	    <c:set var="inputSelect">선택하세요</c:set>	   	
    	  <!-- 조사대상지 -->                                                                                                                                                
          <tr>                                                                                                                                                          
		      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.sldNm"/></c:set>                                                                           
		  <th rowspan="3">${title}</th>                                                                                                                             
		  <td class="addrArea">                                                                                                                                     
		      <form:select path="sldId" title="${title}${inputSelect}" cssClass="txt">                                                                              
			      <form:options items="${sldNmList}" itemValue="sldId" itemLabel="sldNm" />                                                                         
			  </form:select>                                                                                                                                        
			  <div><form:errors path="sldNm" cssClass="error" /></div>                                                                                              
		      </td>                                                                                                                                                     
	      </tr>                                                                                                                                                         
	      <tr>                                                                                                                                                          
	      	  <td class="addrArea addrWd13">                                                                                                                            
	      	  	  <form:select path="sldSd" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministCtprvnChange('sld'); return false;">                        
			      <form:option value="" label="${inputSelect}"/>                                                                                                    
			      <form:options items="${sdCodeList}" itemValue="adminCode" itemLabel="adminNm" />                                                                  
			  </form:select>                                                                                                                                        
			  <form:select path="sldSgg" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministSignguChange('sld'); return false;">                       
			      <form:option value="" label="${inputSelect}"/>                                                                                                    
			      <form:options items="${sggCodeList}" itemValue="adminCode" itemLabel="adminNm" />                                                                 
			  </form:select>                                                                                                                                        
			  <form:select path="sldEmd" title="${title}${inputTxt}" cssClass="txt">                                                                                
			      <form:option value="" label="${inputSelect}"/>                                                                                                    
			      <form:options items="${emdCodeList}" itemValue="adminCode" itemLabel="adminNm" />                                                                 
			  </form:select>                                                                                                                                        
		      <div class="AddrBtnRightArea">                                                                                                                        
			       <button type="button" class="search-btn" onclick="javascript:fnAddrSearch('sld'); return false;"><spring:message code="title.inquire" /></button>
		      </div>                                                                                                                                                
	      	  </td>                                                                                                                                                     
	      </tr>                                                                                                                                                         
	      <tr>                                                                                                                                                          
	      	  <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.sldCnt"/></c:set>                                                                          
		  <td>${title} : <span id="sldCnt"><strong style="color: #ff0000; font-weight: bold;">0</strong></span></td>                                                
	      </tr>                                                                                                                                                         
	      <!-- 제보대상지 -->
	      <tr>
		      <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.gvfNm"/></c:set>
			  <th rowspan="2">${title}</th>
			  <td class="addrArea addrWd13">
	      	  	  <form:select path="gvfSd" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministCtprvnChange('gvf'); return false;">
				      <form:option value="" label="${inputSelect}"/>
				      <form:options items="${sdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
				  </form:select>
				  <form:select path="gvfSgg" title="${title}${inputTxt}" cssClass="txt" onchange="fncAdministSignguChange('gvf'); return false;">
				      <form:option value="" label="${inputSelect}"/>
				      <form:options items="${sggCodeList}" itemValue="adminCode" itemLabel="adminNm" />
				  </form:select>
				  <form:select path="gvfEmd" title="${title}${inputTxt}" cssClass="txt">
				      <form:option value="" label="${inputSelect}"/>
				      <form:options items="${emdCodeList}" itemValue="adminCode" itemLabel="adminNm" />
				  </form:select>
			      <div class="AddrBtnRightArea">
				       <button type="button" class="search-btn" onclick="javascript:fnAddrSearch('gvf'); return false;"><spring:message code="title.inquire" /></button>
			      </div>
	      	  </td>
	      </tr>
	      <tr>
	      	  <c:set var="title"><spring:message code="sysLssLcp.fieldBookVO.gvfCnt"/></c:set>
			  <td>${title} : <span id="gvfCnt"><strong style="color: #ff0000; font-weight: bold;">0</strong></span></td>
	      </tr>
    	</table>        
        <div class="BtnGroup">
			<div class="BtnRightArea">
				<button type="button" class="add-btn" onclick="javascript:fnInsertCnrsSpceSld('<c:out value='${mst_id}'/>');"><spring:message code="title.create" /></button>				
			</div>
		</div>
		</form:form>
    </div>
</div>