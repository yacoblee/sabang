<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="pageTitle"><spring:message code="sysLssLcp.sldRegInfo.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">산사태조사</a></li>
		<li><a href="#">땅밀림실태조사</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail"/></h3>
	<div id="contents">		
			<div class="BoardDetail">
				<fieldset>
					<form id="listForm" action="/sys/lss/lcp/sld/selectLcpSvySldList.do" method="post">						
						<input name="sldId" type="hidden" value="<c:out value="${result.sldId }"/>"/>
						<input name="uniqId" type="hidden" value="<c:out value="${result.uniqId }"/>"/>
					</form>			
				</fieldset>
				<table class="svysldInfoDetail" summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tr>
						<th rowspan="2">일반사항</th>
						<th>고유번호</th>
						<td><c:out value="${result.gid}"/></td>
						<th>rank</th>
						<td><c:out value="${result.rank}"/></td>
						<th>권역</th>
						<td><c:out value="${fn:split(result.label,'-')[0]}"/></td>
					</tr>
					<tr>
						<th>라벨</th>
						<td><c:out value="${result.label}"/></td>
						<th>주소</th>
						<td colspan="3"><c:out value="${result.addr}"/></td>
					</tr>
					<tr>
						<th>산림특성</th>
						<th>임상</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${frtpCdResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<td colspan="4" style="background: #f2f2f2;"></td>
					</tr>
					<tr>
						<th>고도특성</th>
						<th>평균고도(m)</th>
						<td><c:out value="${result.evtAvg}"/> m</td>
						<th>최대고도(m)</th>
						<td><c:out value="${result.evtMax}"/> m</td>
						<th>최소고도(m)</th>
						<td><c:out value="${result.evtMin}"/> m</td>
					</tr>
					<tr>
						<th>경사도특성</th>
						<th>평균경사(º)</th>
						<td><c:out value="${result.slpAvg}"/> º</td>
						<th>최대경사(º)</th>
						<td><c:out value="${result.slpMax}"/> º</td>
						<th>최소경사(º)</th>
						<td><c:out value="${result.slpMin}"/> º</td>
					</tr>
					<tr>
						<th>토심특성</th>
						<th>평균토심(cm)</th>
						<td><c:out value="${result.sldpAvg}"/> cm</td>
						<th>최대토심(cm)</th>
						<td><c:out value="${result.sldpMax}"/> cm</td>
						<th>최소토심(cm)</th>
						<td><c:out value="${result.sldpAvg}"/> cm</td>
					</tr>
					<tr>
						<th>지질특성</th>
						<th>지질(대)</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${prrckLargResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<th>지질(중)</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${prrckMddlResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<td colspan="2" style="background: #f2f2f2;"></td>
					</tr>
					<tr>
						<th rowspan="2">토양특성</th>
						<th>토양형</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${sltpResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<th>토성</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${sibscsResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<th>토양구조</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${sidstrctResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
					</tr>
					<tr>	
						<th>암노출도</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${rckexdResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<th>토양석력함량</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${sibcbsResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>
						</td>
						<th>풍화정도</th>
						<td>
							<form:select path="instrokat" title="${title} ${inputSelect}" cssClass="txt" onchange="select(this);"> <!-- 타 지층 및 관입암 -->
								<form:option value="" label="${inputSelect}"></form:option>
								<form:options items="${wteffResult}" itemValue="codeNm" itemLabel="codeNm" />
							</form:select>	
						</td>
					</tr>
				</table>
			</div>
			<div class="BtnGroup">
				<div class="BtnRightArea">
					<button type="button" class="modify-btn" onclick="javascript:fncUpdateSvySldInfoView(); return false;"><spring:message code="title.update" /></button>
					<button type="button" class="list-btn" onclick="location.href='/sys/lss/lcp/sld/selectLcpSvySldList.do'"><spring:message code="button.list" /></button>
				</div>
			</div>
			<div class="Page">
				<c:if test="${not empty paginationInfo}">
				<ui:pagination paginationInfo="${paginationInfo}" type="custom" jsFunction="linkPage"/>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script>
function linkPage(pageNo){
	$("input[name=pageSubIndex]").val(pageNo);
	$("#listForm").submit();
}
</script>