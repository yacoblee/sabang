<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<c:set var="pageTitle"><spring:message code="sysVytFrd.stripLand.title"/></c:set>
<div class="wrap-right" id="Cont">
	<ul class="breadcrumbs">
		<li><a href="#">홈</a></li>
		<li><a href="#">타당성평가</a></li>
		<li><a href="#">임도</a></li>
	</ul>
	<h3>${pageTitle} <spring:message code="title.detail"/></h3>
	<div id="contents">
		<form id="searchForm" action="/sys/vyt/frd/sld/selectSldInfoDetail.do">
			<input name="schid" type="hidden" value="<c:out value='${result.id}'/>">
			<input name="schsld_nm" type="hidden" value="<c:out value='${searchMap.schsldNm}'/>">
			<input name="schsld_create_user" type="hidden" value="<c:out value='${searchMap.schsld_create_user}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
		</form>
		<form id="listForm" action="/sys/vyt/frd/sld/selectSldInfoUpdtView.do" method="post">
			<input name="smid" type="hidden" value="<c:out value='${result.smid}'/>">
			<input name="schsld_nm" type="hidden" value="<c:out value='${searchMap.schsldNm}'/>">
			<input name="schsld_create_user" type="hidden" value="<c:out value='${searchMap.schsld_create_user}'/>">
			<input name="schsvy_year" type="hidden" value="<c:out value='${searchMap.schsvy_year}'/>">
			<input name="schpageIndex" type="hidden" value="<c:out value='${searchMap.schpageIndex}'/>">
			<input name="schpageUnit" type="hidden" value="<c:out value='${searchMap.schpageUnit}'/>">
			<input name="schpageSubIndex" type="hidden" value="<c:out value='${searchMap.schpageSubIndex}'/>">
		<div class="BoardDetail">
			<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<colgroup>
					<col style="width: 16.7%;">
					<col style="width: 8.7%;">
					<col style="width: 10.7%;">
					<col style="width: 16.7%;">
					<col style="width: 20.7%;">
					<col style="width: 26.7%;">
				</colgroup>
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr>
							<th>조사ID</th><!-- 조사ID -->
							<td colspan="2"><c:out value="${result.sldlabel}"/></td>
							<th>임도종류</th><!--조사유형 -->
							<td colspan="2"><c:out value="${result.frdtype}"/></td>
						</tr>
						<tr>
							<th>예정거리</th><!-- 종류 -->
							<td colspan="2"><c:out value="${result.schdst}"/>km</td>						
							<th>노선종류</th><!-- 종류 -->
							<td colspan="2"><c:out value="${result.routetype}"/></td>						
						</tr>
						<tr>
							<th>소재지</th><!-- 소재지-->
							<td colspan="5">
								<c:choose>
									<c:when test="${result.parcelCnt eq '0'}">
										<c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/>
									</c:when>
									<c:otherwise>
										<c:out value="${result.sd}"/> <c:out value="${result.sgg}"/> <c:out value="${result.emd}"/> <c:out value="${result.ri}"/> <c:out value="${result.jibun}"/> 외 <span class="other-addr" style="color:red;font-weight:bold;text-decoration: underline;cursor:pointer;" onclick="javascript:fnOtherAddrPopup();"><c:out value="${result.parcelCnt}"/></span>필지
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th colspan="6">GPS좌표</th>
						</tr>
						<tr>
							<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lat" /></th><!-- 시점위도 -->
							<td colspan="2"><c:out value="${result.bpy}"/></td>
							<th><spring:message code="sysVytFrd.fieldBookVO.bp" /><spring:message code="sysVytFrd.stripLandVO.lon" /></th><!-- 시점경도 -->
							<td colspan="2"><c:out value="${result.bpx}"/></td>
						</tr>
						<tr>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />1</th><!-- 종점위도1 -->
							<td colspan="2"><c:out value="${result.epy1}"/></td>
							<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />1</th><!-- 종점경도1 -->
							<td colspan="2"><c:out value="${result.epx1}"/></td>
						</tr>
						<c:if test="${result.epy2 != null  && result.epx2 != null}">
							<tr>
								<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lat" />2</th><!-- 종점위도2 -->
								<td colspan="2"><c:out value="${result.epy2}"/></td>
								<th><spring:message code="sysVytFrd.fieldBookVO.ep" /><spring:message code="sysVytFrd.stripLandVO.lon" />2</th><!-- 종점경도2 -->
								<td colspan="2"><c:out value="${result.epx2}"/></td>
							</tr>
						</c:if>
						<c:if test="${not empty result.etc}">
							<tr>
								<th>기타</th>
								<td colspan="5"><c:out value="${result.etc}"/></td>
							</tr>
						</c:if>
						<tr>
							<th colspan="6">법적제한사항</th>
						</tr>
						<tr>
							<th>지목</th>
							<th>지번</th>
							<th>소유구분</th>
							<th colspan="2">소재지</th>
							<th></th>
						</tr>
						<c:forEach var="item" items="${parcelList}" varStatus="status">						
							<tr class="center">
								<td><c:out value="${item.jimok}"/></td>
								<td><c:out value="${item.jibun}"/></td>
								<td><c:out value="${item.posesnSe}"/></td>
								<td colspan="2"><c:out value="${item.addr}"/> <c:out value="${item.jibun}"/></td>
								<td><button type="button" class="search-btn ft-size13" onclick="fncLadUsePlanPopup('<c:out value="${item.pnuCode}"/>','<c:out value="${item.addr}"/>','<c:out value="${item.jibun}"/>','eum'); return false;">토지이음</button><button type="button" class="search-btn ft-size13" onclick="fncLadUsePlanPopup('<c:out value="${item.pnuCode}"/>'); return false;">SEE:REAL</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="mainMenu">□ 분석통계</div>
				<table>
					<tr>
						<th colspan="6">임황분석</th>
					</tr>
					<tr class="center">
						<th rowspan="2">임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;상</th>
						<th>침엽수림</th>
						<th>활엽수림</th>
						<th>혼효림</th>
						<th>죽림</th>
						<th>무립목지/비산림</th>
					</tr>
					<tr class="center">
						<c:set var="frtp" value="${statList.frtp}" scope="page" />
						<c:choose>
							<c:when test="${empty frtp['침엽수림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${frtp['침엽수림']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty frtp['활엽수림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${frtp['활엽수림']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty frtp['혼효림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${frtp['혼효림']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty frtp['죽림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${frtp['죽림']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty frtp['무립목지/비산림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${frtp['무립목지/비산림']}" /></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<colgroup>
						<col width="157.33px;">
						<col width="157.33px;">
						<col width="157.33px;">
						<col width="157.33px;">
						<col width="157.33px;">
						<col width="157.33px;">
					</colgroup>
					<tr class="center">
						<th rowspan="2">임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
						<th>인공림</th>
						<th>천연림</th>
						<th>무립목지/비산림</th>
						<th></th>
						<th></th>
					</tr>
					<tr class="center">
						<c:set var="fror" value="${statList.fror}" scope="page" />
						<c:choose>
							<c:when test="${empty fror['인공림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${fror['인공림']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty fror['천연림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${fror['천연림']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty fror['무립목지/비산림']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${fror['무립목지/비산림']}" /></td>
							</c:otherwise>
						</c:choose>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<colgroup>
						<col width="157.33px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
						<col width="87.4px;">
					</colgroup>
					<tr class="center">
						<th rowspan="2">영&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;급</th>
						<th>Ⅰ 영급</th>
						<th>Ⅱ 영급</th>
						<th>Ⅲ 영급</th>
						<th>Ⅳ 영급</th>
						<th>Ⅴ 영급</th>
						<th>Ⅵ 영급</th>
						<th>Ⅶ 영급</th>
						<th>Ⅷ 영급</th>
						<th>Ⅸ 영급</th>
					</tr>
					<tr class="center">
						<c:set var="agcls" value="${statList.agcls}" scope="page" />
						<c:choose>
							<c:when test="${empty agcls['1영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['1영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['2영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['2영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['3영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['3영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['4영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['4영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['5영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['5영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['6영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['6영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['7영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['7영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['8영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['8영급']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty agcls['9영급']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${agcls['9영급']}" /></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<tr class="center">
						<th rowspan="2">경&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;급</th>
						<th>치수</th>
						<th>소경목</th>
						<th>중경목</th>
						<th>대경목</th>
						<th></th>
					</tr>
					<tr class="center">
						<c:set var="dmcls" value="${statList.dmcls}" scope="page" />
						<c:choose>
							<c:when test="${empty dmcls['치수']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dmcls['치수']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty dmcls['소경목']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dmcls['소경목']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty dmcls['중경목']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dmcls['중경목']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty dmcls['대경목']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dmcls['대경목']}" /></td>
							</c:otherwise>
						</c:choose>
						<td></td>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<tr class="center">
						<th rowspan="2">밀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도</th>
						<th>소</th>                                                 
						<th>중</th>                                                
						<th>밀</th>                                                
						<th></th>                                                   
						<th></th>                                                   
					</tr>
					<tr class="center">
						<c:set var="dnst" value="${statList.dnst}" scope="page" />
						<c:choose>
							<c:when test="${empty dnst['소']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dnst['소']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty dnst['중']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dnst['중']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty dnst['밀']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${dnst['밀']}" /></td>
							</c:otherwise>
						</c:choose>
						<td></td>
						<td></td>
					</tr>
				</table>
				<div class="ovrflwx-auto">
					<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.koftr}">
									<colgroup>
										<c:forEach var="koftr" items="${statList.koftr}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
										<c:forEach var="koftr" items="${statList.koftr}">
											<c:choose>
												<c:when test="${koftr.key == '1' || koftr.key == '2' || koftr.key == '3' || koftr.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:otherwise>
													<th class="bt-none">
														<c:out value="${koftr.key}"/>												
													</th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
						<tr class="center">
							<c:choose>
								<c:when test="${not empty statList.koftr}">
									<c:forEach var="koftr" items="${statList.koftr}">
										<c:choose>
											<c:when test="${koftr.key == '1' || koftr.key == '2' || koftr.key == '3' || koftr.key == '4'}">
												<td></td>
											</c:when>
											<c:otherwise>
												<td>
													<c:out value="${koftr.value}"/>											
												</td>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<td class="bt-none">-</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</div>
					<br>
				<table class="tb-fixed">
					<colgroup>
						<col width="157.33px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
					</colgroup>
					<tr>
						<th colspan="10">지황분석</th>
					</tr>
					<tr class="center">
						<th rowspan="2">경&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;사</th>
						<th>10° 미만</th>
						<th>10°~20°</th>
						<th>20°~30°</th>
						<th>30°~40°</th>
						<th>40°~50°</th>
						<th>50°~60°</th>
						<th>60°~70°</th>
						<th>70°~80°</th>
						<th>80°이상</th>
					</tr>
					<tr class="center">
						<c:set var="slope" value="${statList.slope}" scope="page" />
						<c:choose>
							<c:when test="${empty slope['10도 미만']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['10도 미만']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['10-20도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['10-20도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['20-30도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['20-30도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['30-40도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['30-40도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['40-50도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['40-50도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['50-60도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['50-60도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['60-70도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['60-70도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['70-80도']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['70-80도']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty slope['80도 이상']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${slope['80도 이상']}" /></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<colgroup>
						<col width="157.33px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
						<col width="87.407px;">
					</colgroup>
					<tr class="center">
						<th rowspan="2">향</th>
						<th>평탄지</th>
						<th>북</th>
						<th>북동</th>
						<th>동</th>
						<th>남동</th>
						<th>남</th>
						<th>남서</th>
						<th>서</th>
						<th>북서</th>
					</tr>
					<tr class="center">
						<c:set var="aspect" value="${statList.aspect}" scope="page" />
						<c:choose>
							<c:when test="${empty aspect['평지']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['평지']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['북']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['북']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['북동']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['북동']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['동']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['동']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['남동']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['남동']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['남']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['남']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['남서']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['남서']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['서']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['서']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty aspect['북서']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${aspect['북서']}" /></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
				<div class="ovrflwx-auto">
					<table class="tb-fixed statTable">
						<c:choose>
							<c:when test="${not empty statList.dem}">
								<colgroup>
									<c:forEach var="dem" items="${statList.dem}">
										<col width="157.33px;">
									</c:forEach>
								</colgroup>
								<tr class="center">
									<th rowspan="2" class="bt-none">표&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고</th>
									<c:forEach var="dem" items="${statList.dem}">
										<c:choose>
											<c:when test="${dem.key == '1' || dem.key == '2' || dem.key == '3' || dem.key == '4'}">
												<th class="bt-none"></th>
											</c:when>
											<c:otherwise>
												<th class="bt-none">
													<c:out value="${dem.key}"/>												
												</th>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</tr>
							</c:when>
							<c:otherwise>
								<colgroup>
									<col width="157.33px;">
									<col width="786.67px;">
								</colgroup>
								<tr class="center">
									<th rowspan="2" class="bt-none">토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
									<th class="bt-none">-</th>
								</tr>
							</c:otherwise>
						</c:choose>
						<tr class="center">
							<c:choose>
								<c:when test="${not empty statList.dem}">
									<c:forEach var="dem" items="${statList.dem}">
										<c:choose>
											<c:when test="${dem.key == '1' || dem.key == '2' || dem.key == '3' || dem.key == '4'}">
												<td></td>
											</c:when>
											<c:otherwise>
												<td>
													<c:out value="${dem.value}"/>											
												</td>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<td class="bt-none">-</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</div>
				<div class="ovrflwx-auto">
					<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.soil}">
									<colgroup>
										<c:forEach var="soil" items="${statList.soil}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
										<c:forEach var="soil" items="${statList.soil}">
											<c:choose>
												<c:when test="${soil.key == '1' || soil.key == '2' || soil.key == '3' || soil.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:otherwise>
													<th class="bt-none">
														<c:out value="${soil.key}"/>												
													</th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
						<tr class="center">
							<c:choose>
								<c:when test="${not empty statList.soil}">
									<c:forEach var="soil" items="${statList.soil}">
										<c:choose>
											<c:when test="${soil.key == '1' || soil.key == '2' || soil.key == '3' || soil.key == '4'}">
												<td></td>
											</c:when>
											<c:otherwise>
												<td>
													<c:out value="${soil.value}"/>											
												</td>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<td class="bt-none">-</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</div>
				<div class="ovrflwx-auto">
					<table class="tb-fixed statTable">
							<c:choose>
								<c:when test="${not empty statList.geology}">
									<colgroup>
										<c:forEach var="geology" items="${statList.geology}">
											<col width="157.33px;">
										</c:forEach>
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
										<c:forEach var="geology" items="${statList.geology}">
											<c:choose>
												<c:when test="${geology.key == '1' || geology.key == '2' || geology.key == '3' || geology.key == '4'}">
													<th class="bt-none"></th>
												</c:when>
												<c:when test="${geology.key == '0'}">
													<th class="bt-none">수류지역</th>
												</c:when>
												<c:otherwise>
													<th class="bt-none">
														<c:out value="${geology.key}"/>												
													</th>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tr>
								</c:when>
								<c:otherwise>
									<colgroup>
										<col width="157.33px;">
										<col width="786.67px;">
									</colgroup>
									<tr class="center">
										<th rowspan="2" class="bt-none">지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
										<th class="bt-none">-</th>
									</tr>
								</c:otherwise>
							</c:choose>
						<tr class="center">
							<c:choose>
								<c:when test="${not empty statList.geology}">
									<c:forEach var="geology" items="${statList.geology}">
										<c:choose>
											<c:when test="${geology.key == '1' || geology.key == '2' || geology.key == '3' || geology.key == '4'}">
												<td></td>
											</c:when>
											<c:otherwise>
												<td>
													<c:out value="${geology.value}"/>											
												</td>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<td class="bt-none">-</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</div>
				<table class="tb-fixed statTable">
					<tr class="center">
						<th rowspan="2">모&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;암</th>
						<th>화성암</th>                                                   
						<th>퇴적암</th>                                                   
						<th>변성암</th>                                                   
						<th></th>                                                   
						<th></th>                                                   
					</tr>
					<tr class="center">
						<c:set var="prrck" value="${statList.prrck}" scope="page" />
						<c:choose>
							<c:when test="${empty prrck['화성암']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${prrck['화성암']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty prrck['퇴적암']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${prrck['퇴적암']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty prrck['변성암']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${prrck['변성암']}" /></td>
							</c:otherwise>
						</c:choose>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<tr class="center">
						<th rowspan="2">퇴&nbsp;적&nbsp;&nbsp;양&nbsp;식</th>
						<th>잔적토</th>                                                   
						<th>보행토(포행토)</th>                                                   
						<th>봉적토</th>                                                   
						<th></th>                                                   
						<th></th>                                                   
					</tr>
					<tr class="center">
						<c:set var="accma" value="${statList.accma}" scope="page" />
						<c:choose>
							<c:when test="${empty accma['잔적토']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${accma['잔적토']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty accma['보행토(포행토)']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${accma['보행토(포행토)']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty accma['봉적토']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${accma['봉적토']}" /></td>
							</c:otherwise>
						</c:choose>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table class="tb-fixed statTable">
					<tr class="center">
						<th rowspan="2">암&nbsp;석&nbsp;노&nbsp;출&nbsp;도</th>
						<th>10%이하</th>                                                   
						<th>10~30%</th>                                                   
						<th>30~50%</th>                                                   
						<th>50~75%</th>                                                   
						<th></th>                                                   
					</tr>
					<tr class="center">
						<c:set var="rock" value="${statList.rock}" scope="page" />
						<c:choose>
							<c:when test="${empty rock['10% 이하']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${rock['10% 이하']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty rock['10~30%']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${rock['10~30%']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty rock['30~50%']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${rock['30~50%']}" /></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${empty rock['50~75%']}">
								<td>-</td>
							</c:when>
							<c:otherwise>
								<td><c:out value="${rock['50~75%']}" /></td>
							</c:otherwise>
						</c:choose>
						<td></td>
					</tr>
				</table>
				<br>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr class="center">
							<td>
								<div class="photoWrap">
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											대상지 위치
											<c:if test="${analImg.destloc != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('01','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('01','jpg'); return false;"></button>
											</c:if>
										</div>
										<c:choose>
											<c:when test="${analImg.destloc != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.destloc }" />" alt="대상지 위치" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="01" value="<c:out value="${gidList.destloc}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											행정구역
											<c:if test="${analImg.admin != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('02','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('02','jpg'); return false;"></button>
											</c:if>
										</div>
										<c:choose>
											<c:when test="${analImg.admin != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.admin }" />" alt="행정구역" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="02" value="<c:out value="${gidList.admin}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>	
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											사업대상지 관계지적도
											<c:if test="${analImg.admin != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('03','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('03','jpg'); return false;"></button>
											</c:if>
										</div>
										<c:choose>
											<c:when test="${analImg.rllgstr != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.rllgstr }" />" alt="사업대상지 관계지적도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="03" value="<c:out value="${gidList.rllgstr}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">
											문화재보존관리지도
											<c:if test="${analImg.admin != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('04','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('04','jpg'); return false;"></button>
											</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.culture != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.culture }" />" alt="문화재보존관리지도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="04" value="<c:out value="${gidList.culture}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<table summary="조사완료지의 내역에 대한 상세조회 내역을 출력합니다.">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<colgroup>
						<col width="15%"/>
						<col width=";"/>
						<col width="15%"/>
						<col width=";"/>
						<col width="15%"/>
						<col width=";"/>
					</colgroup>
					<tbody>
						<tr class="center">
							<th colspan="6">규제대상문화재</th>
						</tr>
						<tr class="center">
							<th>국가지정문화재</th>
							<td>
								<c:choose>
									<c:when test="${not empty culCntMap.natlChoCul && culCntMap.natlChoCul ne '0'}"><c:out value="${culCntMap.natlChoCul}"/>건</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<th>시도지정문화재</th>
							<td>
								<c:choose>
									<c:when test="${not empty culCntMap.SidoChoCul && culCntMap.SidoChoCul ne '0'}"><c:out value="${culCntMap.SidoChoCul}"/>건</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
							<th>등록문화재</th>
							<td>
								<c:choose>
									<c:when test="${not empty culCntMap.natlRegCul && culCntMap.natlRegCul ne '0'}"><c:out value="${culCntMap.natlRegCul}"/>건</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:forEach items="${culNmList}" var="culNmItem" varStatus="status">
							<tr class="center">
								<td colspan="6">${culNmItem}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="mainMenu">□ 대상지분석</div>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
				<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
					<tbody>
						<tr class="center">
							<th colspan="6">임황분석</th>
						</tr>
						<tr class="center">
							<td>
							<div class="photoWrap">
								<div class="photoBox" style="width: 50%;">
									<div class="photoTag">
										임상분포도
										<c:if test="${analImg.frtp != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('05','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('05','jpg'); return false;"></button>
										</c:if>
									</div>
									<div class="photoUrl w450">
										<c:choose>
											<c:when test="${analImg.frtp != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.frtp }" />" alt="임상분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="05" value="<c:out value="${gidList.frtp}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="photoBox" style="width: 50%;">
									<div class="photoTag">
										임종분포도
										<c:if test="${analImg.fror != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('06','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('06','jpg'); return false;"></button>
										</c:if>
									</div>
									<div class="photoUrl w450">
										<c:choose>
											<c:when test="${analImg.fror != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.fror }" />" alt="임종분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="06" value="<c:out value="${gidList.fror}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>	
								<div class="photoBox" style="width: 50%;">
									<div class="photoTag">
										영급분포도
										<c:if test="${analImg.agcls != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('07','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('07','jpg'); return false;"></button>
										</c:if>
									</div>
									<div class="photoUrl w450">
										<c:choose>
											<c:when test="${analImg.agcls != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.agcls }" />" alt="영급분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="07" value="<c:out value="${gidList.agcls}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="photoBox" style="width: 50%;">
									<div class="photoTag">
										경급분포도
										<c:if test="${analImg.dmcls != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('08','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('08','jpg'); return false;"></button>
										</c:if>
									</div>
									<div class="photoUrl w450">
										<c:choose>
											<c:when test="${analImg.dmcls != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.dmcls }" />" alt="경급분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="08" value="<c:out value="${gidList.dmcls}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="photoBox" style="width: 50%;">
									<div class="photoTag">
										밀도분포도
										<c:if test="${analImg.dnst != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('09','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('09','jpg'); return false;"></button>
										</c:if>
									</div>
									<div class="photoUrl w450">
										<c:choose>
											<c:when test="${analImg.dnst != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.dnst }" />" alt="밀도분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="09" value="<c:out value="${gidList.dnst}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="photoBox" style="width: 50%;">
									<div class="photoTag">
										수종분포도
										<c:if test="${analImg.koftr != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('10','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('10','jpg'); return false;"></button>
										</c:if>
									</div>
									<div class="photoUrl w450">
										<c:choose>
											<c:when test="${analImg.koftr != null}">
												<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.koftr }" />" alt="수종분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												<input type="hidden" name="10" value="<c:out value="${gidList.koftr}"/>"/>
											</c:when>
											<c:otherwise>
												<div class="mt35pct">분석결과가 없습니다.</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
							</td>
						</tr>
					</tbody>
				</table>
				<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<tbody>
							<tr class="center">
								<th colspan="6">지황분석</th>
							</tr>
							<tr class="center">
								<td>
								<div class="photoWrap">
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">경사분포도
											<c:if test="${analImg.slope != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('11','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('11','jpg'); return false;"></button>
											</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.slope != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.slope }" />" alt="경사분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="11" value="<c:out value="${gidList.slope}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">향분포도
											<c:if test="${analImg.aspect != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('12','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('12','jpg'); return false;"></button>
											</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.aspect != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.aspect }" />" alt="향분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="12" value="<c:out value="${gidList.aspect}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>	
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">표고분포도
											<c:if test="${analImg.dem != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('13','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('13','jpg'); return false;"></button>
											</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.dem != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.dem }" />" alt="표고분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="13" value="<c:out value="${gidList.dem}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">토성분포도
										<c:if test="${analImg.soil != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('14','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('14','jpg'); return false;"></button>
										</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.soil != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.soil }" />" alt="토성분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="14" value="<c:out value="${gidList.soil}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">지질분포도
										<c:if test="${analImg.geology != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('15','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('15','jpg'); return false;"></button>
										</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.geology != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.geology }" />" alt="지질분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="15" value="<c:out value="${gidList.geology}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">모암분포도
										<c:if test="${analImg.prrck != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('16','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('16','jpg'); return false;"></button>
										</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.prrck != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.prrck }" />" alt="모암분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="16" value="<c:out value="${gidList.prrck}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>	
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">퇴적양식분포도
										<c:if test="${analImg.accma != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('17','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('17','jpg'); return false;"></button>
										</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.accma != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.accma }" />" alt="퇴적양식분포도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="17" value="<c:out value="${gidList.accma}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">암석노출도
										<c:if test="${analImg.rock != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('18','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('18','jpg'); return false;"></button>
										</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.rock != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.rock }" />" alt="암석노출도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="18" value="<c:out value="${gidList.rock}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="mainMenu">□ 기타현황</div>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<tbody>
							<tr class="center">
								<td>
								<div class="photoWrap">
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">생태자연도분석
										<c:if test="${analImg.nature != null}">
											<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('19','zip'); return false;"></button>
											<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('19','jpg'); return false;"></button>
										</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.nature != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.nature }" />" alt="생태자연도분석" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="19" value="<c:out value="${gidList.nature}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">멸종위기 동&middot;식물 분석 현황</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.animal != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.animal }" />" alt="멸종위기 동식물 분석 현황" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>	
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">산불취약지도</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.mtfire != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.mtfire }" />" alt="산불취약지도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="photoBox" style="width: 50%;">
										<div class="photoTag">산사태위험등급도
											<c:if test="${analImg.landslide != null}">
												<button class="process-btn-m sldDetailAnalBtn" title="분석파일 다운로드" onclick="fnAnalDataDownload('22','zip'); return false;"></button>
												<button class="download-btn-m sldDetailImgBtn" title="이미지 저장" onclick="fnAnalDataDownload('22','jpg'); return false;"></button>
											</c:if>
										</div>
										<div class="photoUrl w450">
											<c:choose>
												<c:when test="${analImg.landslide != null}">
													<div class="photoUrl w450"><img src="/storage/<c:url value="${analImg.landslide }" />" alt="산사태위험등급도" onerror="this.remove ? this.remove() : this.removeNode();"></div>
													<input type="hidden" name="22" value="<c:out value="${gidList.landslide}"/>"/>
												</c:when>
												<c:otherwise>
													<div class="mt35pct">분석결과가 없습니다.</div>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="mainMenu">□ 분석통계</div>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width="35%"/>
							<col width="15%"/>
							<col width="35%"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="4">임&nbsp;&nbsp;황&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr class="center">
								<th>임&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;상</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.frorMaxNm}"><c:out value="${statList.frorMaxNm}"/> <c:out value="${statList.frorMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>경&nbsp;&nbsp;&nbsp;급&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.dmclsMaxNm}"><c:out value="${statList.dmclsMaxNm}"/> <c:out value="${statList.dmclsMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>밀&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.dnstMaxNm}"><c:out value="${statList.dnstMaxNm}"/> <c:out value="${statList.dnstMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>영&nbsp;&nbsp;&nbsp;급&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.agclsMaxNm}">
											<c:choose>
												<c:when test="${statList.agclsMaxNm eq '1영급'}">Ⅰ영급 </c:when>
												<c:when test="${statList.agclsMaxNm eq '2영급'}">Ⅱ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '3영급'}">Ⅲ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '4영급'}">Ⅳ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '5영급'}">Ⅴ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '6영급'}">Ⅵ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '7영급'}">Ⅶ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '8영급'}">Ⅷ영급</c:when>
												<c:when test="${statList.agclsMaxNm eq '9영급'}">Ⅸ영급</c:when>
											</c:choose>
											<c:out value="${statList.agclsMaxVal}"/>
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width="35%"/>
							<col width="15%"/>
							<col width="35%"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="4">지&nbsp;&nbsp;황&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr class="center">
								<th>평&nbsp;&nbsp;&nbsp;균&nbsp;&nbsp;&nbsp;경&nbsp;&nbsp;&nbsp;사</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.slopeMaxVal}">
											<fmt:formatNumber value="${statList.slopeMinVal}" pattern="0.00"></fmt:formatNumber>°~<fmt:formatNumber value="${statList.slopeMaxVal}" pattern="0.00"></fmt:formatNumber>° / 평균 <fmt:formatNumber value="${statList.slopeAvgVal}" pattern="0.00"></fmt:formatNumber>°
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>토&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;성</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.soilMaxNm}"><c:out value="${statList.soilMaxNm}"/> <c:out value="${statList.soilMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>향&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.aspectMaxNm}"><c:out value="${statList.aspectMaxNm}"/> <c:out value="${statList.aspectMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>퇴&nbsp;&nbsp;&nbsp;적&nbsp;&nbsp;&nbsp;양&nbsp;&nbsp;&nbsp;식</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.accmaMaxNm}"><c:out value="${statList.accmaMaxNm}"/> <c:out value="${statList.accmaMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>표&nbsp;&nbsp;&nbsp;고&nbsp;&nbsp;&nbsp;분&nbsp;&nbsp;&nbsp;포</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.demMaxVal}">
											평균 <fmt:formatNumber value="${statList.demAvgVal}" pattern=".00"></fmt:formatNumber>m
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>모&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;암</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.prrckMaxNm}"><c:out value="${statList.prrckMaxNm}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr class="center">
								<th>지&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;질</th>
								<td>
									<c:choose>
										<c:when test="${statList.geologyCnt eq 1}">
											<c:out value="${statList.geologyMaxNm}"/>
										</c:when>
										<c:when test="${statList.geologyCnt > 1}">
											<c:out value="${statList.geologyMaxNm}"/> 등
										</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<th>암&nbsp;석&nbsp;노&nbsp;출&nbsp;도</th>
								<td>
									<c:choose>
										<c:when test="${not empty statList.rockMaxNm}"><c:out value="${statList.rockMaxNm}"/> <c:out value="${statList.rockMaxVal}"/></c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">산사태취약지역 및 산사태위험등급도</th>
							</tr>
							<tr class="center">
								<th rowspan="2">산불취약지도</th>
								<th>A등급</th>
								<th>B등급</th>
								<th>C등급</th>
								<th>D등급</th>
								<th></th>
							</tr>
							<tr class="center">
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td>-</td>
								<td></td>
							</tr>
							<tr class="center">
								<th rowspan="2">산사태위험도</th>
								<th>1등급</th>
								<th>2등급</th>
								<th>3등급</th>
								<th>4등급</th>
								<th>5등급</th>
							</tr>
							<tr class="center">
								<c:set var="landslide" value="${statList.landslide}" scope="page" />
								<c:choose>
									<c:when test="${empty landslide['1등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['1등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['2등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['2등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['3등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['3등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['4등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['4등급']}" /></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty landslide['5등급']}">
										<td>-</td>
									</c:when>
									<c:otherwise>
										<td><c:out value="${landslide['5등급']}" /></td>
									</c:otherwise>
								</c:choose>
							</tr>
						</tbody>
					</table>
					<table summary="<spring:message code="common.summary.inqire" arguments="${pageTitle}" />">
					<caption class="Hidden">${pageTitle} <spring:message code="title.detail" /></caption>
						<colgroup>
							<col width="15%"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
							<col width=";"/>
						</colgroup>
						<tbody>
							<tr class="center">
								<th colspan="6">기&nbsp;&nbsp;타&nbsp;&nbsp;현&nbsp;&nbsp;황</th>
							</tr>
							<tr>
								<th>생태자연도</th>
								<td colspan="5"><c:out value="${result.ecoNtrMap}"/></td>
							</tr>
							<tr>
								<th>멸종위기종서식 현황</th>
								<td colspan="5"><c:out value="${result.endSpc}"/></td>
							</tr>
							<tr>
								<th>특별산림보호종</th>
								<td colspan="5"><c:out value="${result.spcFrsPrt}"/></td>
							</tr>
						</tbody>
					</table>
				<div class="BtnGroup">
					<div class="BtnRightArea">
						<c:if test="${analList != '[]'}">
							<button type="button" class="download-btn" onclick="javascript:fncDownloadAnalAll('<c:out value='${result.smid}'/>'); return false;">분석파일 다운로드</button>
						</c:if>
						<button type="button" class="del-btn" onclick="javascript:fnSvySldInfoDelete(); return false;">삭제</button>
						<button type="button" class="modify-btn" onclick="javascript:fnSvySldInfoUpdtView(); return false;">수정</button>
						<button type="button" class="list-btn" onclick="javascript:fnList('/sys/vyt/frd/sld/selectFrdSldinfo.do'); return false;">목록</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
	function linkPage(pageNo){
		$("input[name=pageIndex]").val(pageNo);
		$("#listForm").submit();
	}
</script>