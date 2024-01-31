<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<!-- 좌측 메뉴 선택 바 s-->
<div class="nav-sidebar on">
   <ul>
      <li class="home" onclick="window.location.href='/sys/main.do'"><a href="#"></a></li>
      <li class="search on"><a href="javascript:void(0)">검색</a></li>
      <li class="thema"><a href="javascript:void(1)">주제도</a></li>
      <li class="bscExmn"><a href="javascript:void(2)">기초</a></li><!-- 기초조사 -->
      <li class="lcpExmn"><a href="javascript:void(3)">땅밀림</a></li><!-- 땅밀림 -->
      <li class="wkaExmn"><a href="javascript:void(4)">실태</a></li><!-- 실태조사 -->
      <li class="cnlExmn"><a href="javascript:void(5)">해제</a></li><!-- 해제조사 -->
      <li class="aprExmn"><a href="javascript:void(6)">외관</a></li><!-- 외관점검 -->
      <li class="frdExmn"><a href="javascript:void(7)">임도</a></li><!-- 임도타당성평가 -->
      <li class="mseExmn"><a href="javascript:void(8)">계측기</a></li><!-- 계측기 -->
      <li class="pcsExmn"><a href="javascript:void(9)">정밀</a></li><!-- 정밀점검 -->
   </ul>
</div>
<!-- 좌측 메뉴 선택 바 e-->

<!-- 좌측 서브 메뉴 선택 바 s-->
<div class="quick-box on">
	<!-- 검색 메뉴 s -->
	<div class="on">
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span>통합검색</span>
						<span class="holder"></span>
					</a>
					<ul style="display: block;">
						<li>
							<div class="topsearch">
								<label for="search01" class="Hidden">통합검색</label>
								<input type="text" id="search01">
								<button type="button" class="search-btn">검색</button>
							</div>
							<div class="map_tab">
								<a href="#tAll" class="on">전체</a>
	                           	<a href="#tNmfpc">지명</a>
	                           	<a href="#tLnm">지번</a>
	                           	<a href="#tRn">도로명</a>
							</div>
							<div class="map_tabcontent">
								<div id="tAll" class="tab_content on">
									<p class="txtsearch">검색결과 총 <span class="txtblue">0</span> 건</p>
									<table id="allSchCntLst" summary="통합검색에 대한 결과를 리스트로 출력합니다.">
										<caption>통합검색결과리스트</caption>
										<colgroup>
											<col style="width:30%;">
											<col style="width:50%;">
											<col style="width:20%;">
										</colgroup>
										<tbody>
											<tr>
												<th>지명</th>
												<td><span class="txtblue">0</span> 건</td>
												<td><button type="button" class="more" onclick="javascript:wholeSearchClick(1);">더보기</button>
											</tr>
											<tr>
												<th>지번</th>
												<td><span class="txtblue">0</span> 건</td>
												<td><button type="button" class="more" onclick="javascript:wholeSearchClick(2);">더보기</button>
											</tr>
											<tr>
												<th>도로명</th>
												<td><span class="txtblue">0</span> 건</td>
												<td><button type="button" class="more" onclick="javascript:wholeSearchClick(3);">더보기</button>
											</tr>
										</tbody>
									</table>
								</div>
								<div id="tNmfpc" class="tab_content">
									<p class="txtsearch">검색결과 총 <span class="txtblue">0</span> 건</p>
									<table id="placeList" summary="">
										<caption>통합검색결과지명리스트</caption>
										<colgroup>
		                                	<col style="width: 20%;">
		                                	<col style="width: 80%;">
	                                 	</colgroup>
	                                 	<thead>
	                                 		<tr>
	                                 			<th>지명</th>
	                                 			<th>주소</th>
	                                 		</tr>
	                                 	</thead>
	                                 	<tbody>
	                                 		
	                                 	</tbody>
									</table>
									<div class="map_Page"></div>
								</div>
								<div id="tLnm" class="tab_content">
									<p class="txtsearch">검색결과 총 <span class="txtblue">0</span> 건</p>
									<table id="parcelList" summary="">
										<caption>통합검색결과지번리스트</caption>
										<colgroup>
		                                	<col style="width: 20%;">
		                                	<col style="width: 80%;">
	                                 	</colgroup>
	                                 	<thead>
	                                 		<tr>
	                                 			<th>지번</th>
	                                 			<th>주소</th>
	                                 		</tr>
	                                 	</thead>
	                                 	<tbody>
	                                 		
	                                 	</tbody>
									</table>
									<div class="map_Page"></div>
								</div>
								<div id="tRn" class="tab_content">
									<p class="txtsearch">검색결과 총 <span class="txtblue">0</span> 건</p>
									<table id="roadList" summary="">
										<caption>통합검색결과도로명리스트</caption>
										<colgroup>
		                                	<col style="width: 20%;">
		                                	<col style="width: 80%;">
	                                 	</colgroup>
	                                 	<thead>
	                                 		<tr>
	                                 			<th>도로명</th>
	                                 			<th>주소</th>
	                                 		</tr>
	                                 	</thead>
	                                 	<tbody>
	                                 		
	                                 	</tbody>
									</table>
									<div class="map_Page"></div>
								</div>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- 검색 메뉴e -->
	
	<!-- 주제도 메뉴 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span>주제도</span>
						<span class="holder"></span>
					</a>
					<ul style="display: block;">
						<li>
							<div>
								<label for="select01">서비스분류</label>
								<select id="select01">
								<c:if test="${fn:length(apiClsfyList) == 0}">
									<option>선택하세요</option>
								</c:if>
								<c:forEach var="list" items="${apiClsfyList}" varStatus="status">
									<option value='<c:out value="${list.clsfyNm}"/>'><c:out value="${list.clsfyNm}"/></option>
								</c:forEach>
								</select>
							</div>
							<div>
								<label for="select02">서비스상세</label>
								<select id="select02">
								<c:if test="${fn:length(apiServiceList) == 0}">
									<option>선택하세요</option>
								</c:if>
								<c:forEach var="list" items="${apiServiceList}" varStatus="status">
									<option value='<c:out value="${list.uri}"/>'><c:out value="${list.serviceNm}"/></option>
								</c:forEach>
								</select>
							</div>							
						</li>
						<li>
	                     	<div class="acc-search-div">
                        		<button type="button" id="btnThemaSearch">검색</button>
                        		<button type="button" id="btnThemaReset">끄기</button>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	<!-- 주제도 메뉴 e -->

	<!-- 기초조사 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.bsc"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu1.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 기초조사 e -->
	<!-- 땅밀림실태조사 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.lcp"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu2.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 땅밀림실태조사 e -->
	<!-- 취약지역실태조사 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.wka"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu3.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 취약지역실태조사 e -->
	<!-- 취약지역해제조사 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.cnl"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu4.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 취약지역해제조사 e -->
	<!-- 외관점검 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.apr"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu5.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 외관점검 e -->
	<!-- 임도타당성평가 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.frd"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu6.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 임도타당성평가 e -->
	<!-- 계측기 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.mse"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu7.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 계측기 e -->
	<!-- 정밀점검 s -->
	<div>
		<div class="acc-container">
			<ul>
				<li class="has-sub open">
					<a href='#'>
						<span><spring:message code="sysGis.svyComptVO.pcs"/></span>
						<span class="holder"></span>
					</a>
					<jsp:include page="./svyComptMenu8.jsp"></jsp:include>
				</li>
			</ul>
		</div>
	</div>
	<!-- 정밀점검 e -->
	<div class="nav-btn">
		<a href="javascript:void(0)" class="navClose on">닫기</a>
	</div>
</div>
<!-- 좌측 서브 메뉴 선택 바 e-->