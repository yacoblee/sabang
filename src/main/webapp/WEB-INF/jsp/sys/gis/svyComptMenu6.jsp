<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<!-- 오늘일자 -->
<c:set var="nowDt"><fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM"/></c:set>
<!-- 임도 입력 창 -->
<ul style="display: block;" class="acc-container-detail">
	<form name="frdSvyComptSchForm" action="">
	<li>
		<!-- 조회 조건 S-->
		<div>
			<label for="frdSvyComptFrdType"><spring:message code="sysGis.svyComptVO.frdtype"/></label>
			<select id="frdSvyComptFrdType" name="frdSvyComptFrdType" title="<spring:message code="sysGis.svyComptVO.frdtype"/>" class="w2">
				<option value=""><spring:message code="title.all" /></option>
				<option value="산불진화임도">산불진화임도</option>
				<option value="간선임도">간선임도</option>
				<option value="작업임도">작업임도</option>
			</select>
			
			<label for="frdSvyComptStartDt"><spring:message code="sysGis.stripLandVO.date"/></label>
			<%-- <input type="month" id= "frdSvyComptStartDt" class="SvyComptStartDt w3" name="frdSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" id= "frdSvyComptStartDt" class="SvyComptStartDt w3" name="frdSvyComptStartDt" title="<spring:message code="sysGis.startDt"/>" placeholder="시작일자"> 
			-
			<%-- <input type="month" id= "frdSvyComptEndDt" class="SvyComptEndDt w3" name="frdSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" max="<c:out value="${nowDt }"></c:out>"> --%> 
			<input type="text" id= "frdSvyComptEndDt" class="SvyComptEndDt w3" name="frdSvyComptEndDt" title="<spring:message code="sysGis.endDt"/>" placeholder="종료일자"> 
		</div>				
		<div>
			<label for="frdSvyComptSd" ><spring:message code="sysGis.SvyComptArea"/></label>
			<select id="frdSvyComptSd" class="SvyComptSd w4" name="frdSvyComptSd" title="<spring:message code="sysGis.stripLandVO.sd"/>">
				<option value=""<c:if test="${empty searchVO.svySd || searchVO.svySd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySdCodeInfo" items="${sdCodeList}" varStatus="status">
				<option value="<c:out value="${svySdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySd eq svySdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="frdSvyComptSgg" class="SvyComptSgg w4" name="frdSvyComptSgg" title="<spring:message code="sysGis.stripLandVO.sgg"/>">
				<option value=""<c:if test="${empty searchVO.svySgg || searchVO.svySgg == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svySggCodeInfo" items="${sggCodeList}" varStatus="status">
				<option value="<c:out value="${svySggCodeInfo.adminCode}"/>"<c:if test="${searchVO.svySgg eq svySggCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svySggCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="frdSvyComptEmd" class="SvyComptEmd w4" name="frdSvyComptEmd" title="<spring:message code="sysGis.stripLandVO.emd"/>">
				<option value=""<c:if test="${empty searchVO.svyEmd || searchVO.svyEmd == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyEmdCodeInfo" items="${emdCodeList}" varStatus="status">
				<option value="<c:out value="${svyEmdCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyEmd eq svyEmdCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyEmdCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>
			<select id="frdSvyComptRi" class="SvyComptRi w4" name="frdSvyComptRi" title="<spring:message code="sysGis.stripLandVO.ri"/>">
				<option value=""<c:if test="${empty searchVO.svyRi || searchVO.svyRi == '0'}">selected="selected"</c:if>><spring:message code="title.all" /></option>
				<c:forEach var="svyRiCodeInfo" items="${riCodeList}" varStatus="status">
				<option value="<c:out value="${svyRiCodeInfo.adminCode}"/>"<c:if test="${searchVO.svyRi eq svyRiCodeInfo.adminCode}">selected="selected"</c:if>><c:out value="${svyRiCodeInfo.adminNm}"/></option>
				</c:forEach>
			</select>		
				<input type="text" id="frdSvyComptJibun" name="frdSvyComptJibun"/>
		</div>
		<div>
			<label for="frdSvyComptId"><spring:message code="sysGis.svyComptVO.svyId"/></label>
			<input type="text" id="frdSvyComptId" name="frdSvyComptId" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptsvyUser"><spring:message code="sysGis.svyComptVO.svyUser"/></label>
			<input type="text" id="frdSvyComptsvyUser" name="frdSvyComptsvyUser" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptMstNm"><spring:message code="sysGis.svyComptVO.mstNm"/></label>
			<input type="text" id="frdSvyComptMstNm" name="frdSvyComptMstNm" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>		
		<div class="SvyComptInput">
			<label for="frdSvyComptSafefct"><spring:message code="sysGis.svyComptVO.safefct"/></label><!-- 보호시설 -->
<%-- 			<input type="text" id="frdSvyComptSafefct" name="frdSvyComptSafefct" value="<c:out value=''/>" class="mo_mt5 input_null w3" /> --%>
			<select id="frdSvyComptSafefct" name="frdSvyComptSafefct" title="<spring:message code="sysGis.svyComptVO.field"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="민가">민가</option>
				<option value="종교시설">종교시설</option>
				<option value="농경지">농경지</option>
			</select>	
			
			<label for="frdSvyComptField"><spring:message code="sysGis.svyComptVO.field"/></label><!-- 전답유무 -->
			<select id="frdSvyComptField" name="frdSvyComptField" title="<spring:message code="sysGis.svyComptVO.field"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>			
			<label for="frdSvyComptAcsbl"><spring:message code="sysGis.svyComptVO.acsbl"/></label><!-- 접근성 -->
			<select id="frdSvyComptAcsbl" name="frdSvyComptAcsbl" title="<spring:message code="sysGis.svyComptVO.acsbl"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="상">상</option>
				<option value="중">중</option>
				<option value="하">하</option>
			</select>
		</div>
		<div class="SvyComptInput">
			<label for="frdSvyComptLonslopeavg"><spring:message code="sysGis.svyComptVO.lonslopeavg"/></label><!-- 종단기울기 평균 -->
			<input type="text" id="frdSvyComptLonslopeavg" name="frdSvyComptLonslopeavg" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptMtslopeavg"><spring:message code="sysGis.svyComptVO.mtslopeavg"/></label><!-- 산지경사 평균 -->
			<input type="text" id="frdSvyComptMtslopeavg" name="frdSvyComptMtslopeavg" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptAfrstat"><spring:message code="sysGis.svyComptVO.afrstat"/></label><!-- 조림지 유무 -->
			<select id="frdSvyComptAfrstat" name="frdSvyComptAfrstat" title="<spring:message code="sysGis.svyComptVO.afrstat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>			
		</div>
		<div class="SvyComptInput">
			<label for="frdSvyComptCuttingat"><spring:message code="sysGis.svyComptVO.cuttingat"/></label><!-- 벌채지 유무 -->
			<select id="frdSvyComptCuttingat" name="frdSvyComptCuttingat" title="<spring:message code="sysGis.svyComptVO.cuttingat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
			<label for="frdSvyComptStonyland"><spring:message code="sysGis.svyComptVO.stonyland"/></label><!-- 석력지 유무 -->
			<select id="frdSvyComptStonyland" name="frdSvyComptStonyland" title="<spring:message code="sysGis.svyComptVO.stonyland"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
			<label for="frdSvyComptWetlandat"><spring:message code="sysGis.svyComptVO.wetlandat"/></label><!-- 습지 유무 -->
			<select id="frdSvyComptWetlandat" name="frdSvyComptWetlandat" title="<spring:message code="sysGis.svyComptVO.wetlandat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>	
		</div>
		<div class="SvyComptInput">
			<label for="frdSvyComptCmtryat"><spring:message code="sysGis.svyComptVO.cmtryat"/></label><!-- 묘지 유무 -->
			<select id="frdSvyComptCmtryat" name="frdSvyComptCmtryat" title="<spring:message code="sysGis.svyComptVO.cmtryat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
			<label for="frdSvyComptEltnwaterat"><spring:message code="sysGis.svyComptVO.eltnwaterat"/></label><!-- 용출슈 유무 -->
			<select id="frdSvyComptEltnwaterat" name="frdSvyComptEltnwaterat" title="<spring:message code="sysGis.svyComptVO.eltnwaterat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
			<label for="frdSvyComptSofrtgrndat"><spring:message code="sysGis.svyComptVO.sofrtgrndat"/></label><!-- 연약지반 유무 -->
			<select id="frdSvyComptSofrtgrndat" name="frdSvyComptSofrtgrndat" title="<spring:message code="sysGis.svyComptVO.sofrtgrndat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
		</div>
		<div class="SvyComptInput">
			<label for="frdSvyComptMaintreekndcnt"><spring:message code="sysGis.svyComptVO.maintreekndcnt"/></label><!-- 주요수종 건수 -->
			<input type="text" id="frdSvyComptMaintreekndcnt" name="frdSvyComptMaintreekndcnt" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptMainvegcnt"><spring:message code="sysGis.svyComptVO.mainvegcnt"/></label><!-- 주요식생 건수 -->
			<input type="text" id="frdSvyComptMainvegcnt" name="frdSvyComptMainvegcnt" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			
			<label for="frdSvyComptWtrpltnat"><spring:message code="sysGis.svyComptVO.wtrpltnat"/></label><!-- 상수원오염 유무 -->
			<select id="frdSvyComptWtrpltnat" name="frdSvyComptWtrpltnat" title="<spring:message code="sysGis.svyComptVO.wtrpltnat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
		</div>
		<div class="SvyComptInput">
			<label for="frdSvyComptDmgcncrnat"><spring:message code="sysGis.svyComptVO.dmgcncrnat"/></label><!-- 훼손우려지 유무 -->
			<select id="frdSvyComptDmgcncrnat" name="frdSvyComptDmgcncrnat" title="<spring:message code="sysGis.svyComptVO.dmgcncrnat"/>" class="mo_mt5 input_null w3">
				<option value=""><spring:message code="title.all" /></option>
				<option value="유">유</option>
				<option value="무">무</option>
			</select>
			<label for="frdSvyComptFrstdsstrcnt"><spring:message code="sysGis.svyComptVO.frstdsstrcnt"/></label><!-- 산림재해 건수-->
			<input type="text" id="frdSvyComptFrstdsstrcnt" name="frdSvyComptFrstdsstrcnt" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptWildanmlcnt"><spring:message code="sysGis.svyComptVO.wildanmlcnt"/></label><!-- 야생동물 건수 -->
			<input type="text" id="frdSvyComptWildanmlcnt" name="frdSvyComptWildanmlcnt" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
		<div class="SvyComptInput">
			<label for="frdSvyComptEcnrfcltyinstlcnt"><spring:message code="sysGis.svyComptVO.ecnrfcltyinstlcnt"/></label><!-- 사방시설설치 건수 -->
			<input type="text" id="frdSvyComptEcnrfcltyinstlcnt" name="frdSvyComptEcnrfcltyinstlcnt" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
			<label for="frdSvyComptEcnrfcltyncstycnt"><spring:message code="sysGis.svyComptVO.ecnrfcltyncstycnt"/></label><!-- 사방시설필요 건수 -->
			<input type="text" id="frdSvyComptEcnrfcltyncstycnt" name="frdSvyComptEcnrfcltyncstycnt" value="<c:out value=''/>" class="mo_mt5 input_null w3" />
		</div>
	</li>
	<li>
		<div class="acc-search-div">
			<button type="button" id="btnBscShpDown" class="SvyComptShpDownBtn" name="frdSvyComptShpDown">공간정보 다운로드</button>
        	<button type="button" id="btnaprSearch" class="SvyComptBtn" name="frdSvyComptSearch">검색</button>
        	<button type="button" id="btnaprReset" class="SvyComptBtnReset" name="frdSvyComptReset">초기화</button>
		</div>
	</li>
	
	<!-- 조회 결과 S-->
	<li>
		<div class="map_tabcontent">
			<div class="tab_content on">
			<p class="txtsearch frd">검색결과 총 <span class="txtblue">0</span> 건</p>
			<table id="frdSchRsltLst" summary="임도타당성평가 상세검색에 대한 결과를 리스트로 출력합니다.">
					<caption>검색결과리스트</caption>
					<colgroup>
						<col style="width:10%;">
						<col style="width:20%;">
						<col style="width:50%;">
						<col style="width:20%;">
					</colgroup>
					<thead>
	                	<tr>
	                		<th>번호</th>
	                		<th>임도종류</th>
	                		<th>조사지역</th>
	                		<th>더보기</th>
	                	</tr>
	                </thead>					
					<tbody>
					
					</tbody>
				</table>	
				<div class="map_Page">
				
				</div>			
			</div>
		</div>					
		<!-- 조회 결과 E-->	
	</li>	
	</form>
</ul>