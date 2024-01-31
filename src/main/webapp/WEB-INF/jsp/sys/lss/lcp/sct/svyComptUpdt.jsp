<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <validator:javascript formName="lssLcpStripLand" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<c:set var="pageTitle"><spring:message code="sysLssLcp.svyComptList.update"/></c:set>
<div class="wrap-right" id="Cont">
   <ul class="breadcrumbs">
      <li><a href="#">홈</a></li>
      <li><a href="#">산사태조사</a></li>
      <li><a href="#">땅밀림실태조사</a></li>
   </ul>
   <h3>${pageTitle}</h3><!-- 대상지 수정 -->
   <div id="contents">
   	  <input type="hidden" name="photolist" value="<c:out value="${orginl_photo_result}"/>">
      <form:form id="listForm" commandName="result" action="${pageContext.request.contextPath}/sys/lss/lcp/sct/updateLcpSvyCompt.do" method="post">
         <input name="gid" type="hidden" value="<c:out value="${result.gid}" />">
         <input name="mstId" type="hidden" value="<c:out value='${result.mstId}'/>"/>
         <input name="svyId" type="hidden" value="<c:out value='${result.svyId}'/>"/>
         <input name="schsvyType" type="hidden" value="<c:out value='${searchVO.svyType}'/>"/>
         <input name="schsvyYear" type="hidden" value="<c:out value='${searchVO.svyYear}'/>"/>
         <input name="schsvyMonth" type="hidden" value="<c:out value='${searchVO.svyMonth}'/>"/>
         <input name="schsvySd" type="hidden" value="<c:out value='${searchVO.svySd}'/>"/>
         <input name="schsvySgg" type="hidden" value="<c:out value='${searchVO.svySgg}'/>"/>
         <input name="schsvyEmd" type="hidden" value="<c:out value='${searchVO.svyEmd}'/>"/>
         <input name="schsvyRi" type="hidden" value="<c:out value='${searchVO.svyRi}'/>"/>
         <input name="schsvyId" type="hidden" value="<c:out value='${searchVO.svyId}'/>"/>
         <input name="schsvyUser" type="hidden" value="<c:out value='${searchVO.svyUser}'/>"/>
         <input name="schmstNm" type="hidden" value="<c:out value='${searchVO.mstNm}'/>"/>
         <input name="schpageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>">
         <input name="schpageUnit" type="hidden" value="<c:out value='${searchVO.pageUnit}'/>">
		 <input name="schlastgrd" type="hidden" value="<c:out value='${searchVO.lastgrd}'/>">
         <input name="photoTagList" type="hidden"  value="">
         <input name="dirsymptmval" type="hidden" value="${result.dirsymptmval }" />
         <input name="dirsymptmscore" type="hidden" value="${result.dirsymptmscore }" />
         <input name="indirsymptmsval" type="hidden" value="${result.indirsymptmval }" />
         <input name="indirsymptmscore" type="hidden" value="${result.indirsymptmscore }" />
         <input name="cmprokscore" type="hidden" value="${result.cmprokscore }" />
         <input name="rokwthrscore" type="hidden" value="${result.rokwthrscore }" />
         <input name="disctnuplnslpscore" type="hidden" value="${result.disctnuplnslpscore }" />
         <input name="disctnuplnintvlscore" type="hidden" value="${result.disctnuplnintvlscore }" />
         <input name="soilclassbscore" type="hidden" value="${result.soilclassbscore }" />
         <input name="soilwtscore" type="hidden" value="${result.soilwtscore }" />
         <input name="talusatscore" type="hidden" value="${result.talusatscore }" />
         <input name="soildeptbscore" type="hidden" value="${result.soildeptbscore }" />
         <input name="tpgrphscore" type="hidden" value="${result.tpgrphscore }" />
         <input name="plnformscore" type="hidden" value="${result.plnformscore }" />
         <input name="lngformscore" type="hidden" value="${result.lngformscore }" />
         <input name="slprngscore" type="hidden" value="${result.slprngscore }" />
         <input name="lssfrsm" type="hidden" value="${result.lssfrsm }" />
         <input name="lssfrgrd" type="hidden" value="${result.lssfrgrd }" />
         <input name="lcpSttusCrk" type="hidden" value="" />
         <input name="lcpSttusStp" type="hidden" value="" />
         <input name="lcpSttusWdpt" type="hidden" value="" />
         <input name="lcpSttusStrct" type="hidden" value="" />
         <input name="lcpSttusUgrwtr" type="hidden" value="" />
         <div class="LcpBoardDetail">
         
         <!-- 조사ID -->
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
         		<col width="15%">
         		<col width="17%">
         		<col width="15%">
         		<col width="17%">
         		<col width="15%">
         		<col width="17%">
         	</colgroup>
         	<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
            <c:set var="inputSelect">선택</c:set>
			<tr>
				<th><spring:message code="sysLssLcp.stripLandVO.svyId" /></th>
				<td><c:out value="${result.svyId}"/></td>
				<th><spring:message code="sysLssLcp.svyComptVO.svyDt" /></th>
				<td><c:out value="${result.svyDt}"/></td>
				<th><spring:message code="sysLssLcp.svyComptVO.svyUser" /></th>
				<td><c:out value="${result.svyUser}"/></td>
			</tr>
         </table>
         <br>
         <!-- 일반사항 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptList.gnrlMatter" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
         		<col width="15%">
         		<col width="50%">
         		<col width="15%">
         		<col width="20%">
         	</colgroup>
			<tr>
				<th><spring:message code="sysLssLcp.svyComptList.svyPlace" /></th>
				<td colspan="3"><c:out value="${result.svyAddr}"/></td>
			</tr>
			<tr>
				<th>GPS 좌표</th>
				<td><c:out value="${result.svyLatLon}"/></td>
				<th><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
                <td><c:out value="${result.pz}"/>m</td>
			</tr>
         </table>
         <br>
         <!-- 지질특성 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.geologyChartr" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
				<col width="10%">         	
				<col width="10%">         	
				<col width="10%">         	
				<col width="10%">         	
				<col width="10%">         	
				<col width="10%">         	
				<col width="10%">         	
         	</colgroup>
         	  <tr>
			    <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.cmprok" /></c:set>
			    <th>${title }</th>
			    <td colspan="2">
		            <form:select path="cmprokval" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);">	<!-- 주 구성암석 -->
                          <form:option value="" label="${inputSelect}"/>
                          <form:options items="${prrcklargrvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                    </form:select>
			    </td>
				<c:set var="title"><spring:message code="sysLssLcp.svyComptVO.instrokat" /></c:set>
                <th colspan="2">${title}</th>
				<td colspan="2">
					<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
						<form:option value="" label="${inputSelect}"></form:option>
						<form:options items="${existenceCodeList}" itemValue="codeDc" itemLabel="codeDc" />
					</form:select>
                </td>
			  </tr>
			  <tr>
			  	<c:set var="title"><spring:message code="sysLssLcp.svyComptVO.rokwthr" /></c:set>
			    <th>${title}</th>
			    <td>
					<form:select path="rokwthrval" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 암석풍화 -->
						<form:option value="" label="${inputSelect}"/>
						<form:options items="${rokwthrvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
					</form:select>
			    </td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.geology" /></c:set>
                <th>${title}</th>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.geologyflt" /></c:set>
                <th>${title}</th>
       			<td>
                   <form:select path="geologyflt" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 지질구조 단층 -->
                      <form:option value="" label="${inputSelect}"></form:option>
                      <form:options items="${existenceCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                </td>
			    <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.geologyfld" /></c:set>
                <th>${title}</th>
			    <td>
	                <form:select path="geologyfld" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 지질구조 습곡 -->
                		<form:option value="" label="${inputSelect}"></form:option>
                		<form:options items="${existenceCodeList}" itemValue="codeDc" itemLabel="codeDc" />
    	            </form:select>
                </td>
			  </tr>
			  </table>
			  <!-- 지질특성 추가 -->
			  <table class="svyCompt" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			  	<colgroup>
			  		<col width="18%">
			  		<col width="18%">
			  		<col width="18%">
			  		<col width="12%">
			  		<col width="17%">
			  		<col width="17%">
			  	</colgroup>
			  <tr>
			    <th colspan="3"><spring:message code="sysLssLcp.svyComptVO.disctnupln" /></th>
			    <th><spring:message code="sysLssLcp.svyComptVO.disctnuplndrcno" /></th>
			    <th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.disctnuplnslpval" /></th>
			    <th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.disctnuplnintvlval" /></th>
			  </tr>
			  <tr>
                <th>
                1.(<c:set var="title"><spring:message code="sysLssLcp.svyComptVO.disctnupln"/></c:set>					
                   <form:select path="disctnupln1" title="${title} ${inputSelect}" cssClass="wd70" onchange="select(this);"><!-- 불연속면 조사 1 -->
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${disctnuplnCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                )</th>
                <th>
                2.(<c:set var="title"><spring:message code="sysLssLcp.svyComptVO.disctnupln"/></c:set>
                   <form:select path="disctnupln2" title="${title} ${inputSelect}" cssClass="wd70" onchange="select(this);"><!-- 불연속면 조사 2 -->
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${disctnuplnCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                )</th>
                <th>
                3.(<c:set var="title"><spring:message code="sysLssLcp.svyComptVO.disctnupln"/></c:set>
                   <form:select path="disctnupln3" title="${title} ${inputSelect}" cssClass="wd70" onchange="select(this);"><!-- 불연속면 조사 3 -->
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${disctnuplnCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                )</th>
			    <td rowspan="2">
			    <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.disctnuplndrcno" /></c:set>
                  	<form:select path="disctnuplndrcno" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 불연속면 방향수 -->
                        <form:option value="" label="${inputSelect}"/>
                        <form:options items="${disctnuplndrcnoCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                     </form:select>
			    </td>
			    <td rowspan="2">
			    <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.disctnuplnslpval" /></c:set>
                    <form:select path="disctnuplnslpval" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 불연속면 방향성 -->
                       <form:option value="" label="${inputSelect}"/>
                       <form:options items="${disctnuplnslpvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                    </form:select>
			    </td>
			    <td rowspan="2">
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.disctnuplnintvlval" /></c:set>
                     <form:select path="disctnuplnintvlval" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 불연속면 간격 -->
                        <form:option value="" label="${inputSelect}"/>
                        <form:options items="${disctnuplnintvlvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                     </form:select>
			    </td>
			  </tr>
			  <tr>
			     <td class="pdL-10p">					 
                     <input type="text" name="disctnupln1Strk" value="${result.disctnupln1_strk}" /> /
                     <input type="text" name="disctnupln1Dip" value="${result.disctnupln1_dip}" />
                 </td>
			     <td class="pdL-10p">	 
                     <input type="text" name="disctnupln2Strk" value="${result.disctnupln2_strk}" /> /
                     <input type="text" name="disctnupln2Dip" value="${result.disctnupln2_dip}" />
                 </td>
			     <td class="pdL-10p">
                     <input type="text" name="disctnupln3Strk" value="${result.disctnupln3_strk}" /> /
                     <input type="text" name="disctnupln3Dip" value="${result.disctnupln3_dip}" />
                 </td>
			  </tr>
         </table>
         <br>
         <!-- 토양특성 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.soilChartr" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
         		<col width="20%">
         		<col width="25%">
         		<col width="20%">
         		<col width="10%">
         		<col width="10%">
         		<col width="15%">
         	</colgroup>
			<tr>
				<c:set var="title"><spring:message code="sysLssLcp.svyComptVO.soilty" /></c:set>
                <th>${title}</th>
				<td class="pdL-10p" colspan="2">
                    <form:select path="soilty" id="soiltySelect" title="${title} ${inputSelect}" cssClass="wd190" onchange="select(this);"><!-- 토양형 -->                    	
                    	<!-- 값 있는지 확인 -->
                    	<c:set var="soiltyEtcSelected" value="" />
                    	<c:if test="${!empty result.soilty}">
                    		<c:set var="chk" value="false"/>
                    		<c:forEach var="item" items="${soiltyCodeList}">
                    			<c:if test="${item.codeDc eq result.soilty }"><c:set var="chk" value="true"/></c:if>
                    		</c:forEach>
                    		<c:if test="${!chk}"><c:set var="soiltyEtcSelected" value="selected"/></c:if>
                    	</c:if>
                    	<form:option value="" label="${inputSelect}"/>
                       	<form:options items="${soiltyCodeList}" itemValue="codeDc" itemLabel="codeDc"/>
                        <form:option value="soiltyEtc" selected="${soiltyEtcSelected}">기타</form:option>
                    </form:select>
                    <input type="text" name="soiltyEtc" value="${result.soilty}" <c:if test="${empty soiltyEtcSelected}">style="display:none; width:170px;" disabled </c:if>/>
                </td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.soilclassb30" /></c:set>
                <th colspan="2">${title }</th><!-- 30cm부근 -->
                <td class="pdL-10p">
					<form:select path="soilclassbval" id="soilclassbSelect" title="${title} ${inputSelect}" cssClass="txt"  onchange="select(this);"><!-- 토성 30cm 부근-->
                   		<!-- 값 있는지 확인 -->
			            <c:set var="soilclassbEtcSelected" value="" />
					    <c:if test="${!empty result.soilclassbval}">
					        <c:set var="chk" value="false"/>
					        <c:forEach var="item" items="${soilclassbvalCodeList}">
					            <c:if test="${item.codeDc eq result.soilclassbval }"><c:set var="chk" value="true"/></c:if>
					        </c:forEach>
					        <c:if test="${!chk}"><c:set var="soilclassbEtcSelected" value="selected"/></c:if>
					    </c:if>	
						<form:option value="" label="${inputSelect}"/>
                      	<form:options items="${soilclassbvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                      	<form:option value="soilclassbEtc" selected="${soilclassbEtcSelected}">기타</form:option>
                   	</form:select>
                   	<input type="text" name="soilclassbEtc" value="${result.soilclassbval}" <c:if test="${empty soilclassbEtcSelected}">style="display:none;" disabled </c:if>/>
                </td>
			</tr>
			<tr>
                 <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.soilclassb" /></c:set>
                 <th class="line-ht20">${title}</th>
                 <td class="pdL-10p" colspan="2">
					<form:select path="soilstrct" id="soilstrctSelect" title="${title} ${inputSelect}" cssClass="wd190"  onchange="select(this);">	<!-- 토성 B층 기준 -->
						<form:option value="" label="${inputSelect}"/>
						<form:options items="${soilclassbCodeList}" itemValue="codeDc" itemLabel="codeDc" />
					</form:select>
                 </td>
                 <th><spring:message code="sysLssLcp.svyComptVO.soilwtr" /></th>
                 <td class="pdL-10p" colspan="2">
                 <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.soilwtr" /></c:set>
                 	<form:select path="soilwtrval" id="soilwtrvalSelect" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 토양수분상태 -->
                    	<!-- 값 있는지 확인 -->
			            <c:set var="soilwtrvalEtcSelected" value="" />
			            <c:if test="${!empty result.soilwtrval}">
			            	<c:set var="chk" value="false"/>
					        <c:forEach var="item" items="${soilwtrvalCodeList}">
					            <c:if test="${item.codeDc eq result.soilwtrval }"><c:set var="chk" value="true"/></c:if>
					        </c:forEach>
					        <c:if test="${!chk}"><c:set var="soilwtrvalEtcSelected" value="selected"/></c:if>
			            </c:if>
						<form:option value="" label="${inputSelect}"/>
                       	<form:options items="${soilwtrvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                       	<form:option value="soilwtrvalEtc" selected="${soilwtrvalEtcSelected}">기타</form:option>
                    </form:select>
                    <input type="text" name="soilwtrvalEtc" value="${result.soilwtrval}" <c:if test="${!soilwtrvalEtcSelected}">style="display:none;" disabled </c:if> />
                 </td>
             </tr>
             <tr>
             	<th class="line-ht20"><spring:message code="sysLssLcp.svyComptVO.soildeptb" /></th>
			    <td class="pdL-10p">
	                <input type="text" name="soildeptbval" value="${result.soildeptbval}" /> <!-- 토심 B층까지의 깊이 -->
                </td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.rokexpsr" /></c:set>
                 <th>${title}</th>
                 <td class="pdL-10p">
                    <form:select path="rokexpsr" title="${title} ${inputSelect}" cssClass="wd80" onchange="select(this);"><!-- 암석노출도 -->
                       <form:option value="" label="${inputSelect}"/>
                       <form:options items="${rokexpsrCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                    </form:select>
                 </td>
                 <th><spring:message code="sysLssLcp.svyComptVO.talusat" /></th>
                 <td class="pdL-10p">
                 <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.talusat" /></c:set>
                    <form:select path="talusat" title="${title} ${inputSelect}" cssClass="wd85" onchange="select(this);"><!-- 너덜유무 -->
                       <form:option value="" label="${inputSelect}"/>
                       <form:options items="${talusatCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                    </form:select>
                 </td>
             </tr>
         </table>
         <br>
         <!-- 지형특성 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartr" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<tr>
                <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartrSe" /></th>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.mxhg" /></c:set>
                <th>${title}</th>
                <td colspan="2">
                   <form:input path="mxhg" title="${title} ${inputTxt}" cssClass="wd110" />m
                      <div><form:errors path="mxhg" cssClass="error" /></div>
                </td>
                <th colspan="2" rowspan="2"><spring:message code="sysLssLcp.svyComptVO.arealcval" /></th><!-- 조사지역 위치 -->
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.arealcridge" /></c:set>
                <th colspan="2"><!-- n부능선 -->
				<form:select path="arealcridge" title="${title} ${inputSelect }" cssClass="wd60">
             		<form:option value="" label="${inputSelect}"/>
               	 	<c:forEach var="j" begin="1" end="10">
            				<form:option value="${j}">${j}</form:option>
           	   		</c:forEach>
            	</form:select>
                ${title}
                </th>
             </tr>
             <tr>
                <td colspan="3">
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartrSe" /></c:set>
                	<form:select path="tpgrphval" id="tpgrphvalSelect" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 지형구분 -->
                   		<!-- 값 있는지 확인 -->
			            <c:set var="tpgrphvalEtcSelected" value="" />
			        	<c:if test="${!empty result.tpgrphval}">
			        		<c:set var="chk" value="false"/>
					        <c:forEach var="item" items="${tpgrphvalCodeList}">
					            <c:if test="${item.codeDc eq result.tpgrphval}"><c:set var="chk" value="true"/></c:if>
					        </c:forEach>
					        <c:if test="${!chk}"><c:set var="tpgrphvalEtcSelected" value="selected"/></c:if>
				        </c:if>	
                      	<form:option value="" label="${inputSelect}"/>
                      	<form:options items="${tpgrphvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                      	<form:option value="tpgrphvalEtc" selected="${tpgrphvalEtcSelected}">기타</form:option>
                   	</form:select>
                   	<input type="text" name="tpgrphvalEtc" value="${result.tpgrphval}" <c:if test="${empty tpgrphvalEtcSelected}">style="display:none;" disabled</c:if>/>
                </td>
                <td colspan="2">
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.arealcval" /></c:set>
                	<form:select path="arealcval" id="arealcvalSelect" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 사면 10분비 -->
                   		<!-- 값 있는지 확인 -->
					    <c:set var="arealcvalEtcSelected" value="" />
					    <c:if test="${!empty result.arealcval}">
					        <c:set var="chk" value="false"/>
					        <c:forEach var="item" items="${arealcvalCodeList}">
					            <c:if test="${item.codeDc eq result.arealcval}"><c:set var="chk" value="true"/></c:if>
					        </c:forEach>
					        <c:if test="${!chk}"><c:set var="arealcvalEtcSelected" value="selected"/></c:if>
					    </c:if>
						<form:option value="" label="${inputSelect}"/>
                      	<form:options items="${arealcvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                      	<form:option value="arealcvalEtc" selected="${arealcvalEtcSelected}">기타</form:option>
                	</form:select>
                   	<input type="text" name="arealcvalEtc" value="${result.arealcval}" <c:if test="${empty arealcvalEtcSelected}"> style="display:none;" disabled</c:if>/>
                </td>
             </tr>
             </table>
             <table class="svyCompt" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
             	<colgroup>
             		<col width="16%">
             		<col width="4%">
             		<col width="16%">
             		<col width="4%">
             		<col width="17%">
             		<col width="15%">
             	</colgroup>
             <tr>
	            <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.plnformval" /></c:set>
	            <th>${title}</th>
	            <td class="plnform">
               		<form:select path="plnformval" id="plnformvalSelect" title="${title} ${inputSelect}" cssClass="wd120" onchange="select(this);"><!-- 평면형(수평적) -->
               			<!-- 값 있는지 확인 -->
					    <c:set var="plnformvalEtcSelected" value="" />
					    <c:if test="${!empty result.plnformval}">
					        <c:set var="chk" value="false"/>
					        <c:forEach var="item" items="${formvalCodeList}">
					            <c:if test="${item.codeDc eq result.plnformval }"><c:set var="chk" value="true"/></c:if>
					        </c:forEach>
					        <c:if test="${!chk}"><c:set var="plnformvalEtcSelected" value="selected"/></c:if>
					    </c:if>
                  		<form:option value="" label="${inputSelect}"/>
	                  	<form:options items="${formvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
	                  	<form:option value="plnformvalEtc" selected="${plnformvalEtcSelected}">기타</form:option>
	           		</form:select>
	               	<input type="text" name="plnformvalEtc" value="${result.plnformval}" <c:if test="${empty plnformvalEtcSelected}">style="display:none;" disabled </c:if>/>
	            </td>
	            <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.lngformval" /></c:set>
	            <th>${title}</th>
	            <td class="lngform">
               		<form:select path="lngformval" id="lngformvalSelect" title="${title} ${inputSelect}" cssClass="wd120" onchange="select(this);"><!-- 평면형(수직적) -->
               			<!-- 값 있는지 확인 -->
					    <c:set var="lngformvalEtcSelected" value="" />
					    <c:if test="${!empty result.lngformval}">
					        <c:set var="chk" value="false"/>
					        <c:forEach var="item" items="${formvalCodeList}">
					            <c:if test="${item.codeDc eq result.lngformval }"><c:set var="chk" value="true"/></c:if>
					        </c:forEach>
					        <c:if test="${!chk}"><c:set var="lngformvalEtcSelected" value="selected"/></c:if>
					    </c:if>
                  		<form:option value="" label="${inputSelect}"/>
	                  	<form:options items="${formvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
	                  	<form:option value="lngformvalEtc" selected="${lngformvalEtcSelected}">기타</form:option>
	           		</form:select>
	               	<input type="text" name="lngformvalEtc" value="${result.lngformval}" <c:if test="${empty lngformvalEtcSelected}">style="display:none;" disabled </c:if>/>
	            </td>
	            <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.slprngavrg" /></c:set>
	            <th>${title}</th>
	            <td>
	               <form:select path="slprngval" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 평균경사도 -->
	                  <form:option value="" label="${inputSelect}"/>
	                  <form:options items="${slprngvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
	               </form:select>
	            </td>
	         </tr>
         </table>
         <br>
         <!-- 수리특성 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.hydrlChartr" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
	         <tr>
	            <th><spring:message code="sysLssLcp.svyComptVO.ugrwtrPosblty" /></th>
	            <th><spring:message code="sysLssLcp.svyComptVO.dwstrmAt" /></th>
	            <th><spring:message code="sysLssLcp.svyComptVO.sprgAt" /></th>
	         </tr> 
             <tr>
                <td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.ugrwtrPosblty" /></c:set>
                   <form:select path="ugrwtr_posblty" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 상류로부터 지하수 유입 가능성 -->
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${existenceCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                </td>
                <td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.dwstrmAt" /></c:set>
                   <form:select path="dwstrm_at" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 하류계류의 유무 -->
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${existenceCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                </td>
                <td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.sprgAt" /></c:set>
                   <form:select path="sprg_at" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"><!-- 샘,소,저수지 유무 -->
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${existenceCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                   </form:select>
                </td>
             </tr>
         </table>
         <br>
         <!-- 산림특성 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.mtstChartr" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
         		<col width="15%">
         		<col width="20%">
         	</colgroup>
            <tr>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.frstfrval" /></c:set>	<!-- 임상 -->
              <th>${title }</th>
              <td>
                 <form:select path="frstfrCd" id ="frstfrval" title="${title} ${inputSelect}" cssClass="txt" onchange="fnGetFrstfrVal(event);">
                    <form:option value="" label="${inputSelect}"/>
                    <form:options items="${frstfrvalCodeList}" itemValue="codeDc" itemLabel="codeDc" />
                    <form:option value="기타"/>
                 </form:select>
                 <input type="text" name="frstfrval" value="${result.frstfrval}" />
              </td>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.maintreeknd" /></c:set>	<!-- 주요수종 -->
              <th>${title}</th>
              <td>
               	<form:select path="maintreekndCd" id="maintrknd" title="${title} ${inputSelect}" cssClass="wd200" onChange="fncGetSelectVal(event);">
               		<form:option value="" label="${inputSelect}"/>
               		<c:choose>
                		<c:when test="${fn:contains(result.frstfrval, '침엽수') }">
                			<form:options items="${maintreeNeedleCodeList}" itemValue="codeNm" itemLabel="codeNm" />
						</c:when>
						<c:when test="${fn:contains(result.frstfrval, '활엽수') }">
							<form:options items="${maintreeBroadCodeList}" itemValue="codeNm" itemLabel="codeNm" />	
						</c:when>
						<c:when test="${fn:contains(result.frstfrval, '상록활엽수') }">
							<form:options items="${maintreeGrBroadCodeList}" itemValue="codeNm" itemLabel="codeNm" />
						</c:when>
						<c:when test="${fn:contains(result.frstfrval, '죽림') }">
							<form:options items="${maintreeBambooCodeList}" itemValue="codeNm" itemLabel="codeNm" />
						</c:when>
						<c:when test="${fn:contains(result.frstfrval, '혼효림') }">
							<form:option value="기타"/>
						</c:when>
						<c:when test="${fn:contains(result.frstfrval, '기타') }">
							<form:option value="기타"/>
						</c:when>
					</c:choose>
                 </form:select>
         		<form:input class="maintreeVal" path="maintreeknd" title="${title} ${inputTxt}" cssClass="wd205"/>
                 <div><form:errors path="maintreeknd" cssClass="error" /></div>
              </td>
           </tr>
         </table>
         <br>
         <!-- 기타 -->
         <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.svyEtc" /></p></div>
         <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
         	<colgroup>
         		<col width="15%">
         		<col width="35%">
         		<col width="15%">
         		<col width="35%">
         	</colgroup>
              <tr>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.frlndsttus" /></c:set><!-- 임지이용상태 -->
                <th>${title}</th>
                <td>
                   <form:select path="frlndsttusCd" title="${title} ${inputSelect}" cssClass="wd100" onchange="fncGetSelectVal(event);">
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${lngformvalCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                   </form:select>
                   <input type="hidden" name="soilclassbEtc" value="" />
                   <div><form:errors path="frlndsttus" cssClass="error" /></div>
                </td>
                <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.lwbndlwfrlndsttus" /></c:set>
                <th>${title}</th>
                <td>
					<form:select path="lwbndlwfrlndsttusCd" title="${title} ${inputSelect}" cssClass="wd100" onchange="fncGetSelectVal(event);">
                      <form:option value="" label="${inputSelect}"/>
                      <form:options items="${lngformvalCodeList}" itemValue="codeNm" itemLabel="codeNm" />
                   </form:select>
                   <input type="hidden" name="soilclassbEtc" value="" />
                   <div><form:errors path="lwbndlwfrlndsttus" cssClass="error" /></div>
                </td>
             </tr>
         </table>
         <br>
        <!-- 땅밀림 징후 균열 -->
       	<div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.lcpSttus" />(균열)</p></div>
		<table class="svyCompt lcpSttus brdT-3p" id="lcpSttusCrk" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<colgroup>
				<col width="8%">
				<col width="28%">
				<col width="28%">
				<col width="8%">
				<col width="28%">
			</colgroup>
		    <tr>
		    	<th rowspan="2">시점/종점</th>
	     		<th colspan="2" class="line-ht20">GPS 좌표 <br>(MAP DATUM : WGS84)</th>
		    	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
		     	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.scale" /></th>
			 </tr>
			 <tr>
			     <th><spring:message code="sysLssLcp.svyComptVO.lat" /></th>
			     <th><spring:message code="sysLssLcp.svyComptVO.lon" /></th>
			 </tr>
			 <c:if test="${lcpsttus_crk_result.size() > 0 }">
			     <c:forEach items="${lcpsttus_crk_result}" var="item" varStatus="status"> 
			         <tr class="dirsymptm lcpSttusCrk center${status.count}">
			         	 <th>시점</th>
			             <td>
			             	<input type="text" name="bpx1" value="<c:out value="${fn:split(item.bpx,' ')[0]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="bpx2" value="<c:out value="${fn:split(item.bpx,' ')[1]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="bpx3" value="<c:out value="${fn:split(item.bpx,' ')[2]}"/>" onchange="fnLcpSttusChange('Crk')" style="width:120px;"/>
			             </td>
			             <td>
			             	<input type="text" name="bpy1" value="<c:out value="${fn:split(item.bpy,' ')[0]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="bpy2" value="<c:out value="${fn:split(item.bpy,' ')[1]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="bpy3" value="<c:out value="${fn:split(item.bpy,' ')[2]}"/>" onchange="fnLcpSttusChange('Crk')" style="width:120px;"/>
			             </td>
			             <td><input type="text" name="bpz" value="<c:out value="${item.bpz}"/>" onchange="fnLcpSttusChange('Crk')"/></td>
			             <td rowspan="2">
			                 <input type="text" name="l" value="<c:out value="${item.length }"/>" onchange="fnLcpSttusChange('Crk')"/>
			                 X <input type="text" name="h" value="<c:out value="${item.height }"/>" onchange="fnLcpSttusChange('Crk')"/>
			                 X <input type="text" name="d" value="<c:out value="${item.depth }"/>" onchange="fnLcpSttusChange('Crk')"/>
			             </td>
			         </tr>
			         <tr class="dirsymptm lcpSttusCrk center${status.count}">
			         	<th>종점</th>
			         	<td>
			             	<input type="text" name="epx1" value="<c:out value="${fn:split(item.epx,' ')[0]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="epx2" value="<c:out value="${fn:split(item.epx,' ')[1]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="epx3" value="<c:out value="${fn:split(item.epx,' ')[2]}"/>" onchange="fnLcpSttusChange('Crk')" style="width:120px;"/>
			             </td>
			             <td>
			             	<input type="text" name="epy1" value="<c:out value="${fn:split(item.epy,' ')[0]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="epy2" value="<c:out value="${fn:split(item.epy,' ')[1]}"/>" onchange="fnLcpSttusChange('Crk')"/>
			             	<input type="text" name="epy3" value="<c:out value="${fn:split(item.epy,' ')[2]}"/>" onchange="fnLcpSttusChange('Crk')" style="width:120px;"/>
			             </td>
			             <td><input type="text" name="epz" value="<c:out value="${item.epz}"/>" onchange="fnLcpSttusChange('Crk')"/></td>
			         </tr>
			     </c:forEach>
		 	</c:if>
		 </table>
		 <br/>
		 <div class="BtnGroup">
			<div class="BtnRightArea">
   				<button type="button" class="add-btn" onclick="javascript:fnLcpSttusAdd('Crk'); return false;"><spring:message code="button.add" /></button>
           		<button type="button" class="del-btn" onclick="javascript:fnLcpSttusDel('Crk'); return false;"><spring:message code="button.delete" /></button>
	   		</div>
		</div>
		 <!-- 땅밀림 징후 단차 -->
       	 <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.lcpSttus" />(단차)</p></div>
		 <table class="svyCompt lcpSttus brdT-3p" id="lcpSttusStp" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
		 	<colgroup>
				<col width="8%">
				<col width="28%">
				<col width="28%">
				<col width="8%">
				<col width="28%">
			</colgroup>
		    <tr>
		    	<th rowspan="2">시점/종점</th>
	     		<th colspan="2" class="line-ht20">GPS 좌표 <br>(MAP DATUM : WGS84)</th>
		    	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
	     		<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.scale" /></th>
			</tr>
		 	<tr>
		    	<th><spring:message code="sysLssLcp.svyComptVO.lat" /></th>
		     	<th><spring:message code="sysLssLcp.svyComptVO.lon" /></th>
		 	</tr>
		 	<c:if test="${lcpsttus_stp_result.size() > 0 }">
			    <c:forEach items="${lcpsttus_stp_result}" var="item" varStatus="status"> 
			        <tr class="dirsymptm lcpSttusStp center${status.count}">
			            <th>시점</th>
			            <td>
			                <input type="text" name="bpx1" value="<c:out value="${fn:split(item.bpx,' ')[0]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="bpx2" value="<c:out value="${fn:split(item.bpx,' ')[1]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="bpx3" value="<c:out value="${fn:split(item.bpx,' ')[2]}"/>" onchange="fnLcpSttusChange('Stp')" style="width:120px;"/>
			            </td>
			            <td>
			                <input type="text" name="bpy1" value="<c:out value="${fn:split(item.bpy,' ')[0]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="bpy2" value="<c:out value="${fn:split(item.bpy,' ')[1]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="bpy3" value="<c:out value="${fn:split(item.bpy,' ')[2]}"/>" onchange="fnLcpSttusChange('Stp')" style="width:120px;"/>
			            </td>
			            <td><input type="text" name="bpz" value="<c:out value="${item.bpz}"/>" onchange="fnLcpSttusChange('Stp')"/></td>
			            <td rowspan="2">
			                <input type="text" name="l" value="<c:out value="${item.length }"/>" onchange="fnLcpSttusChange('Stp')"/>
			                X <input type="text" name="h" value="<c:out value="${item.height }"/>" onchange="fnLcpSttusChange('Stp')"/>
			                X <input type="text" name="d" value="<c:out value="${item.depth }"/>" onchange="fnLcpSttusChange('Stp')"/>
			            </td>
			        </tr>
			        <tr class="dirsymptm lcpSttusStp center${status.count}">
			            <th>종점</th>
			            <td>
			                <input type="text" name="epx1" value="<c:out value="${fn:split(item.epx,' ')[0]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="epx2" value="<c:out value="${fn:split(item.epx,' ')[1]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="epx3" value="<c:out value="${fn:split(item.epx,' ')[2]}"/>" onchange="fnLcpSttusChange('Stp')" style="width:120px;"/>
			            </td>
			            <td>
			                <input type="text" name="epy1" value="<c:out value="${fn:split(item.epy,' ')[0]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="epy2" value="<c:out value="${fn:split(item.epy,' ')[1]}"/>" onchange="fnLcpSttusChange('Stp')"/>
			                <input type="text" name="epy3" value="<c:out value="${fn:split(item.epy,' ')[2]}"/>" onchange="fnLcpSttusChange('Stp')" style="width:120px;"/>
			            </td>
			            <td><input type="text" name="epz" value="<c:out value="${item.epz}"/>" onchange="fnLcpSttusChange('Stp')"/></td>
			        </tr>
			    </c:forEach>
			</c:if>
		 </table>
		 <br/>
		 <div class="BtnGroup">
			<div class="BtnRightArea">
   				<button type="button" class="add-btn" onclick="javascript:fnLcpSttusAdd('Stp'); return false;"><spring:message code="button.add" /></button>
           		<button type="button" class="del-btn" onclick="javascript:fnLcpSttusDel('Stp'); return false;"><spring:message code="button.delete" /></button>
	   		</div>
		</div>
		 <!-- 땅밀림 징후 수목이상생장 -->
      	 <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.lcpSttus" />(수목이상생장)</p></div>
		 <table class="svyCompt lcpSttus brdT-3p" id="lcpSttusWdpt" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<colgroup>
				<col width="30%">
				<col width="30%">
				<col width="10%">
				<col width="30%">
			</colgroup>
		    <tr>
	     		<th colspan="2" class="line-ht20">GPS 좌표 <br>(MAP DATUM : WGS84)</th>
		    	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
		     	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.scale" /></th>
			 </tr>
			 <tr>
			     <th><spring:message code="sysLssLcp.svyComptVO.lat" /></th>
			     <th><spring:message code="sysLssLcp.svyComptVO.lon" /></th>
			 </tr>
			 <c:if test="${lcpsttus_wdpt_result.size() > 0 }">
			     <c:forEach items="${lcpsttus_wdpt_result}" var="item" varStatus="status"> 
			         <tr class="indirsymptm lcpSttusWdpt center${status.count}">
			             <td>
			             	<input type="text" name="bpx1" value="<c:out value="${fn:split(item.bpx,' ')[0]}"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			             	<input type="text" name="bpx2" value="<c:out value="${fn:split(item.bpx,' ')[1]}"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			             	<input type="text" name="bpx3" value="<c:out value="${fn:split(item.bpx,' ')[2]}"/>" onchange="fnLcpSttusChange('Wdpt')" style="width:120px;"/>
			             </td>
			             <td>
			             	<input type="text" name="bpy1" value="<c:out value="${fn:split(item.bpy,' ')[0]}"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			             	<input type="text" name="bpy2" value="<c:out value="${fn:split(item.bpy,' ')[1]}"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			             	<input type="text" name="bpy3" value="<c:out value="${fn:split(item.bpy,' ')[2]}"/>" onchange="fnLcpSttusChange('Wdpt')" style="width:120px;"/>
			             </td>
			             <td><input type="text" name="bpz" value="<c:out value="${item.bpz}"/>" onchange="fnLcpSttusChange('Wdpt')"/></td>
			             <td>
			                 <input type="text" name="l" value="<c:out value="${item.length }"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			                 X <input type="text" name="h" value="<c:out value="${item.height }"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			                 X <input type="text" name="d" value="<c:out value="${item.depth }"/>" onchange="fnLcpSttusChange('Wdpt')"/>
			             </td>
			         </tr>
			     </c:forEach>
			 </c:if>
		 </table>
		 <br/>
		 <div class="BtnGroup">
			<div class="BtnRightArea">
   				<button type="button" class="add-btn" onclick="javascript:fnLcpSttusAdd('Wdpt'); return false;"><spring:message code="button.add" /></button>
           		<button type="button" class="del-btn" onclick="javascript:fnLcpSttusDel('Wdpt'); return false;"><spring:message code="button.delete" /></button>
	   		</div>
		</div>
		 <!-- 땅밀림 징후 구조물이상 -->
      	 <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.lcpSttus" />(구조물이상)</p></div>
		 <table class="svyCompt lcpSttus brdT-3p" id="lcpSttusStrct" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<colgroup>
				<col width="30%">
				<col width="30%">
				<col width="10%">
				<col width="30%">
			</colgroup>
		    <tr>
	     		<th colspan="2" class="line-ht20">GPS 좌표 <br>(MAP DATUM : WGS84)</th>
		    	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
		     	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.scale" /></th>
			 </tr>
			 <tr>
			     <th><spring:message code="sysLssLcp.svyComptVO.lat" /></th>
			     <th><spring:message code="sysLssLcp.svyComptVO.lon" /></th>
			 </tr>
			 <c:if test="${lcpsttus_strct_result.size() > 0 }">
			     <c:forEach items="${lcpsttus_strct_result}" var="item" varStatus="status"> 
			         <tr class="indirsymptm lcpSttusStrct center${status.count}">
			             <td>
			             	<input type="text" name="bpx1" value="<c:out value="${fn:split(item.bpx,' ')[0]}"/>" onchange="fnLcpSttusChange('Strct')"/>
			             	<input type="text" name="bpx2" value="<c:out value="${fn:split(item.bpx,' ')[1]}"/>" onchange="fnLcpSttusChange('Strct')"/>
			             	<input type="text" name="bpx3" value="<c:out value="${fn:split(item.bpx,' ')[2]}"/>" onchange="fnLcpSttusChange('Strct')" style="width:120px;"/>
			             </td>
			             <td>
			             	<input type="text" name="bpy1" value="<c:out value="${fn:split(item.bpy,' ')[0]}"/>" onchange="fnLcpSttusChange('Strct')"/>
			             	<input type="text" name="bpy2" value="<c:out value="${fn:split(item.bpy,' ')[1]}"/>" onchange="fnLcpSttusChange('Strct')"/>
			             	<input type="text" name="bpy3" value="<c:out value="${fn:split(item.bpy,' ')[2]}"/>" onchange="fnLcpSttusChange('Strct')" style="width:120px;"/>
			             </td>
			             <td><input type="text" name="bpz" value="<c:out value="${item.bpz}"/>" onchange="fnLcpSttusChange('Strct')"/></td>
			             <td>
			                 <input type="text" name="l" value="<c:out value="${item.length }"/>" onchange="fnLcpSttusChange('Strct')"/>
			                 X <input type="text" name="h" value="<c:out value="${item.height }"/>" onchange="fnLcpSttusChange('Strct')"/>
			                 X <input type="text" name="d" value="<c:out value="${item.depth }"/>" onchange="fnLcpSttusChange('Strct')"/>
			             </td>
			         </tr>
			     </c:forEach>
			 </c:if>
		 </table>
		 <br/>
		 <div class="BtnGroup">
			<div class="BtnRightArea">
   				<button type="button" class="add-btn" onclick="javascript:fnLcpSttusAdd('Strct'); return false;"><spring:message code="button.add" /></button>
           		<button type="button" class="del-btn" onclick="javascript:fnLcpSttusDel('Strct'); return false;"><spring:message code="button.delete" /></button>
	   		</div>
		</div>
		 <!-- 땅밀림 징후 지하수용출 -->
      	 <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p"><spring:message code="sysLssLcp.svyComptVO.lcpSttus" />(지하수용출)</p></div>
		 <table class="svyCompt lcpSttus brdT-3p" id="lcpSttusUgrwtr" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
			<colgroup>
				<col width="30%">
				<col width="30%">
				<col width="10%">
				<col width="30%">
			</colgroup>
		    <tr>
	     		<th colspan="2" class="line-ht20">GPS 좌표 <br>(MAP DATUM : WGS84)</th>
		    	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.altitude" /></th>
		     	<th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.scale" /></th>
			 </tr>
			 <tr>
			     <th><spring:message code="sysLssLcp.svyComptVO.lat" /></th>
			     <th><spring:message code="sysLssLcp.svyComptVO.lon" /></th>
			 </tr>
			 <c:if test="${lcpsttus_ugrwtr_result.size() > 0 }">
			     <c:forEach items="${lcpsttus_ugrwtr_result}" var="item" varStatus="status"> 
			         <tr class="indirsymptm lcpSttusUgrwtr center${status.count}">
			             <td>
			             	<input type="text" name="bpx1" value="<c:out value="${fn:split(item.bpx,' ')[0]}"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			             	<input type="text" name="bpx2" value="<c:out value="${fn:split(item.bpx,' ')[1]}"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			             	<input type="text" name="bpx3" value="<c:out value="${fn:split(item.bpx,' ')[2]}"/>" onchange="fnLcpSttusChange('Ugrwtr')" style="width:120px;"/>
			             </td>
			             <td>
			             	<input type="text" name="bpy1" value="<c:out value="${fn:split(item.bpy,' ')[0]}"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			             	<input type="text" name="bpy2" value="<c:out value="${fn:split(item.bpy,' ')[1]}"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			             	<input type="text" name="bpy3" value="<c:out value="${fn:split(item.bpy,' ')[2]}"/>" onchange="fnLcpSttusChange('Ugrwtr')" style="width:120px;"/>
			             </td>
			             <td><input type="text" name="bpz" value="<c:out value="${item.bpz}"/>" onchange="fnLcpSttusChange('Ugrwtr')"/></td>
			             <td>
			                 <input type="text" name="l" value="<c:out value="${item.length }"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			                 X <input type="text" name="h" value="<c:out value="${item.height }"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			                 X <input type="text" name="d" value="<c:out value="${item.depth }"/>" onchange="fnLcpSttusChange('Ugrwtr')"/>
			             </td>
			         </tr>
			     </c:forEach>
			 </c:if>
		 </table>
         <br>
	   	<div class="BtnGroup">
			<div class="BtnRightArea">
   				<button type="button" class="add-btn" onclick="javascript:fnLcpSttusAdd('Ugrwtr'); return false;"><spring:message code="button.add" /></button>
           		<button type="button" class="del-btn" onclick="javascript:fnLcpSttusDel('Ugrwtr'); return false;"><spring:message code="button.delete" /></button>
	   		</div>
		</div>
         <!-- 종합의견 -->
        <div class="mgB-5p"><p class="ft-wBold cr-333 ft-s15p">종합의견</p></div>
        <table class="svyCompt brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
              <tr>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.opinion1" /></c:set>
                 <th class="opinion">${title}</th>
                 <td colspan="8">
                    <form:input path="opinion1" title="${title} ${inputTxt}"/>
                       <div><form:errors path="opinion1" cssClass="error" /></div>
                 </td>
              </tr>
              <tr>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.opinion2" /></c:set>
                 <th class="opinion">${title}</th>
                 <td colspan="8">
                    <form:input path="opinion2" title="${title} ${inputTxt}"/>
                       <div><form:errors path="opinion2" cssClass="error" /></div>
                 </td>
              </tr>
              <tr>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.opinion3" /></c:set>
                 <th class="opinion">${title}</th>
                 <td colspan="8">
                    <form:input path="opinion3" title="${title} ${inputTxt}"/>
                       <div><form:errors path="opinion3" cssClass="error" /></div>
                 </td>
              </tr>
              <tr>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.opinion4" /></c:set>
                 <th class="opinion">${title}</th>
                 <td colspan="8">
                    <form:input path="opinion4" title="${title} ${inputTxt}"/>
                       <div><form:errors path="opinion4" cssClass="error" /></div>
                 </td>
              </tr>
              <tr>
              <c:set var="title"><spring:message code="sysLssLcp.svyComptVO.opinion5" /></c:set>
                 <th class="opinion">${title}</th>
                 <td colspan="8">
                    <form:input path="opinion5" title="${title} ${inputTxt}"/>
                       <div><form:errors path="opinion5" cssClass="error" /></div>
                 </td>
              </tr>
           </tbody>
         </table>
        <table class="svyComptGrade brdT-3p" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
	    	<thead>
	      		<tr>
		        	<th colspan="8"><spring:message code="sysLssLcp.svyComptVO.lcpJudge"/></th>        
		      	</tr>
		      	<tr>
            	    <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.indexNo"/></th>
        	        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.cause"/></th>
    	            <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.point"/></th>
	                <th colspan="5"><spring:message code="sysLssLcp.svyComptVO.category"/></th>
      			</tr>
                <tr>
         			<c:forEach var="j" begin="1" end="5">
                    	<th><c:out value="${j}"/></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
			      <tr>
			        <th rowspan="2">1</th> 
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.dirsymptmval"/></th>
					<th class="dirsymptmval"><c:out value="${result.dirsymptmval}"/></th>
	                <c:forEach var="existence" items="${existenceCodeList}" varStatus="status">
	                    <td><c:out value="${existence.codeDc }"/></td>   
	               	</c:forEach>
					<td colspan="3"></td>
			      </tr>
			      <tr class="point">
			     	<th class="dirsymptmscore"><c:out value="${result.dirsymptmscore}"/></th>
					<td>22</td>
					<td>0</td>
			        <td colspan="3"></td>
			      </tr>
			      <tr>
			        <th rowspan="2">2</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.indirsymptmval"/></th>
			     	<th class="indirsymptmval"><c:out value="${result.indirsymptmval}"/></th>
	                <c:forEach var="existence" items="${existenceCodeList}" varStatus="status">
	                    <td><c:out value="${existence.codeDc }"/></td>   
	               	</c:forEach>
			        <td colspan="3"></td>
			      </tr>
			      <tr class="point">
			        <th class="indirsymptmscore"><c:out value="${result.indirsymptmscore}"/></th>
					<td>14</td>
					<td>0</td>
			        <td colspan="3"></td>
			      </tr>
			      <tr>
			        <th rowspan="2">3</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.prntRck"/></th>
			        <th class="cmprokval"><c:out value="${result.cmprokval}"/></th>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpSedimentary"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpMetamorphic"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpVolcanic"/></td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="cmprokscore"><c:out value="${result.cmprokscore}"/></th>
			        <td>8</td>
			        <td>5</td>
			        <td>2</td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">4</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.rokwthr" /></th>
			        <th class="rokwthrval"><c:out value="${result.rokwthrval}"/></th>
			        <c:forEach var="rokwthrval" items="${rokwthrvalCodeList}" varStatus="status">
   						<td><c:out value="${rokwthrval.codeDc }"/></td>	
					</c:forEach>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="rokwthrscore"><c:out value="${result.rokwthrscore}"/></th>
			        <td>7</td>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">5</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.disctnuplnslpval"/></th>
			        <th class="disctnuplnslpval"><c:out value="${result.disctnuplnslpval}"/></th>
			        <c:forEach var="disctnuplnslpval" items="${disctnuplnslpvalCodeList}" varStatus="status">
   						<td><c:out value="${disctnuplnslpval.codeDc }"/></td>	
					</c:forEach>
			      </tr>
			      <tr class="point">
                    <th class="disctnuplnslpscore"><c:out value="${result.disctnuplnslpscore}"/></th>
			        <td>9</td>
			        <td>5</td>
			        <td>4</td>
			        <td>3</td>
			        <td>0</td>
			      </tr>
			      <tr>
			        <th rowspan="2">6</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.disctnuplnintvlval"/></th>
			        <th class="disctnuplnintvlval"><c:out value="${result.disctnuplnintvlval}"/></th>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpBigDense"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpDense"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpNormal"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpAbroad"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpNone"/></td>
			      </tr>
			      <tr class="point">
                    <th class="disctnuplnintvlscore"><c:out value="${result.disctnuplnintvlscore}"/></th>
			        <td>5</td>
			        <td>4</td>
			        <td>2</td>
			        <td>1</td>
			        <td>0</td>
			      </tr>
			      <tr>
			        <th rowspan="2">7</th>
			        <th rowspan="2">토성</th>
			        <th class="soilclassbval"><c:out value="${result.soilclassbval}"/></th>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpClaysoil"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpSandyloam"/></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpLoam"/></td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="soilclassbscore"><c:out value="${result.soilclassbscore}"/></th>
			        <td>5</td>
			        <td>3</td>
			        <td>2</td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">8</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.soilwtr" /></th>
			        <th class="soilwtrval"><c:out value="${result.soilwtrval}"/></th>
			        <c:forEach var="soilwtrval" items="${soilwtrvalCodeList}" varStatus="status">
   						<td><c:out value="${soilwtrval.codeDc }"/></td>	
					</c:forEach>
			      </tr>
			      <tr class="point">
                    <th class="soilwtscore"><c:out value="${result.soilwtscore}"/></th>
			        <td>5</td>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td>1</td>
			      </tr>
			      <tr>
			        <th rowspan="2">9</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.talusat" /></th>
			        <th class="talusat"><c:out value="${result.talusat}"/></th>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpYes" /></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpNo" /></td>
			        <td></td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="talusatscore"><c:out value="${result.talusatscore}"/></th>
			        <td>4</td>
			        <td>0</td>
			        <td></td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">10</th>
			        <th rowspan="2">이동대<br>(토심)깊이</th>
			        <th class="soildeptbval"><c:out value="${result.soildeptbval}"/></th>
			        <c:forEach var="soildeptscore" items="${soildeptscoreCodeList}" varStatus="status">
   						<td><c:out value="${soildeptscore.codeDc }"/></td>	
					</c:forEach>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="soildeptbscore"><c:out value="${result.soildeptbscore}"/></th>
			        <td>5</td>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">11</th>
			        <th rowspan="2"><spring:message code="sysLssLcp.svyComptVO.tpgrphChartrSe" /></th>
			        <th class="tpgrphval"><c:out value="${result.tpgrphval}"/></th>
			        <c:forEach var="tpgrphval" items="${tpgrphvalCodeList}" varStatus="status">
   						<td><c:out value="${tpgrphval.codeDc }"/></td>	
					</c:forEach>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="tpgrphscore"><c:out value="${result.tpgrphscore}"/></th>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td></td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">12</th>
			        <th rowspan="2">지형형태<br>(수평면형)</th>
			        <th class="plnformval"><c:out value="${result.plnformval}"/></th>
			        <c:forEach var="formval" items="${formvalCodeList}" varStatus="status">
   						<td><c:out value="${formval.codeDc }"/></td>	
					</c:forEach>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="plnformscore"><c:out value="${result.plnformscore}"/></th>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td>1</td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">13</th>
			        <th rowspan="2">지형형태<br>(종단면형)</th>
			        <th class="lngformval"><c:out value ="${result.lngformval}"/></th>
			        <c:forEach var="formval" items="${formvalCodeList}" varStatus="status">
   						<td><c:out value="${formval.codeDc }"/></td>	
					</c:forEach>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="lngformscore"><c:out value ="${result.lngformscore}"/></th>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td>1</td>
			        <td></td>
			      </tr>
			      <tr>
			        <th rowspan="2">14</th>
			        <th rowspan="2">사면경사</th>
			        <th class="slprngval"><c:out value="${result.slprngval}"/></th>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegree2030" /></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegree1020" /></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegreeOver30" /></td>
			        <td><spring:message code="sysLssLcp.svyComptVO.lcpDegreeUnder10" /></td>
			        <td></td>
			      </tr>
			      <tr class="point">
                    <th class="slprngscore"><c:out value="${result.slprngscore}"/></th>
			        <td>4</td>
			        <td>3</td>
			        <td>2</td>
			        <td>1</td>
			        <td></td>
			      </tr>
                  <tr>
                     <th colspan="2"><spring:message code="sysLssLcp.svyComptVO.judgePoint"/></th>
                     <th colspan="2" id="sum"><c:out value="${result.lssfrsm}"/></th>
                     <th colspan="2"><spring:message code="sysLssLcp.svyComptVO.judgeGrade"/></th>
                     <th colspan="2" id="grade"><c:out value="${result.lssfrgrd}"/></th>
                  </tr>
                <tr>
                	<th colspan="2">자문판정등급</th>
                	<th colspan="2">
                		<select name="cnsutgrd" onchange="changeLastGrd(this)">
                			<option value="">선택</option>
                			<option value="A" <c:if test="${result.cnsutgrd eq 'A'}">selected="selected"</c:if>>A</option>
                			<option value="B" <c:if test="${result.cnsutgrd eq 'B'}">selected="selected"</c:if>>B</option>
                			<option value="C" <c:if test="${result.cnsutgrd eq 'C'}">selected="selected"</c:if>>C</option>
                		</select>
                	</th>
                	<th colspan="2">최종판정등급</th>
                	<th colspan="2" class="lastgrd">
                		<input type="hidden" name="lastgrd" value="${result.lastgrd}" />
                		<span><c:out value="${result.lastgrd}"/></span>
                	</th>
                </tr>
                <tr>
                	<th colspan="2">자문사유</th>
                	<td colspan="7"><input type="text" name="cnsutresn" placeholder="${result.cnsutresn}"/></td>
                </tr>
                <tr><th colspan="9">현장사진</th></tr>
		    </tbody>
	    </table>
		<div class="photoWrap">
	    	<c:choose>
	    		<c:when test="${photo_result != null }">
	    			<c:forEach items="${photo_result}" var="item" varStatus="status">
				    <c:choose>
				        <c:when test="${not empty item.FILENAME}">
				            <c:set var="noImage" value="false"/>
				            <c:set var="photoUrl" value="${item.FILENAME}"/>
				        </c:when>
				        <c:otherwise>
				            <c:set var="noImage" value="true"/>
				            <c:set var="photoUrl" value="../../../../../../images/common/noimage.png"/>
				        </c:otherwise>
				    </c:choose>
				    <div class="photoBox" style="width: 50%;">
				    	<div class="photoTag">
					        <input style="width:55%; float:left; margin-left:10px;" type="text" name="photoTag<c:out value="${status.count }"/>"
				            	<c:choose>
					                <c:when test="${fn:contains(item,'TAG') && fn:contains(item.TAG,'.') }">placeholder="<c:out value="${fn:split(item.TAG,'.')[1]}"/>" value="<c:out value="${fn:split(item.TAG,'.')[1]}"/>"</c:when>
					                <c:when test="${fn:contains(item,'TAG') && fn:length(item.TAG) > 0 }">placeholder="<c:out value="${item.TAG}"/>" value="<c:out value="${item.TAG}"/>"</c:when>
					                <c:otherwise>placeholder="사진태그없음"</c:otherwise>
					            </c:choose> 
					        /> 
					        <button type="button" class="del-btn" style="float:right; height:35px;" onclick="javascript:fncLssSvyDeletePhoto(event); return false;">삭제</button>
				    	</div>
				    	<div class="photoUrl">
				    		<input type="hidden" <c:if test="${noImage eq false}">value="<c:url value='${photoUrl}'/>"</c:if> name="photoSrc<c:out value="${status.count}"/>" />
		                    <img src="/storage/fieldBook<c:url value='${photoUrl}'/>" <c:if test="${noImage eq true}">style="width:30%;"</c:if> class="photoSrc<c:out value="${status.count}"/>" alt="<c:out value="${status.count}"/>번 현장사진" onerror="this.remove ? this.remove() : this.removeNode();">
		                    <span class="thumb-div noselect">변경</span>
				    	</div>
				    </div>
		    		</c:forEach>
	    		</c:when>
	    		<c:otherwise>
	    			<div class="noPhotoTagInfo">사진태그 정보 없음</div>
	    		</c:otherwise>
	    	</c:choose>
		</div>             
        <div class="BtnGroup">
           <div class="BtnRightArea">
           	  <button type="button" class="add-btn" onclick="javascript:fncLssSvyAddPhoto(); return false;">현장사진 <spring:message code="button.add" /></button>
              <button type="button" class="modify-btn" onclick="javascript:fncSvyComptUpdate(); return false;"><spring:message code="title.update" /></button>
              <button type="button" class="list-btn" onclick="javascript:fnList(); return false;"><spring:message code="button.list" /></button>
           </div>
        </div>
       </div>
      </form:form>
   </div>
</div>