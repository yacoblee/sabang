var wholeSearchQuery = "";

$(document).ready(function() {

	$(document).on("ajaxStart", function() {
		$('.loading-div').show();
	});
	$(document).on("ajaxComplete", function() {
		$('.loading-div').hide();
	});
	// map Div 높이 조절
	// checkSize();

	// 메인지도 생성
	fncMainMap();

	fncCheckParam();
});

/**
 * @author ipodo
 * @name fncCmnSearch
 * @param {String}
 *            url - request url
 * @param {JSON}
 *            param - request param
 * @param {Function}
 *            callback - callback function
 * @returns
 * @description 공통조회
 */
function fncCmnSearch(url, param, callback) {
	$.ajax({
		url : url,
		data : param,
		dataType : 'json',
		success : callback
	})
}

/*
 * 메인지도 생성
 */
function fncMainMap() {
	SM.initialize("map");
}

/**
 * 레이어 바로가기
 * 
 * @returns
 */
function fncGoToLayer(layer) {
	var chkId = "#".concat(layer, "chk");

	$(chkId).trigger("click");
}

/**
 * @author ipodo
 * @name fncRequestFeature
 * @param feature
 * @returns
 * @description 기초조사완료지 지도 바로가기 처리
 */
function fncRequestFeature(featureSet) {

	var type = featureSet.type;
	var feature = featureSet.features;
	var properties = featureSet.features.properties;

	// feature
	var id = null;
	var style = null;
	var content = null;

	if (type == "BSC") {
		id = 'bscSvycomptLink';
		content = fnCreateBscSvycomptLinkPopupContent(properties);
	} else if (type == 'APR') {
		id = 'fckAprcomptLink';
		content = fnCreateAprSvycomptLinkPopupContent(properties);
	} else if (type == 'LCP') {
		id = 'lcpSvycomptLink';
		style = new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : '#fff',
				width : 2
			}),
			fill : new ol.style.Fill({
				color : 'rgba(254,46,100,0.7)'
			})
		});
		content = fnCreateLcpSvycomptLinkPopupContent(properties);
	} else if (type == 'WKA') {
		id = 'wkaSvycomptLink';
		content = fnCreateWkaSvycomptLinkPopupContent(properties);
	} else if (type == 'CNL') {
		id = 'cnlSvycomptLink';
		content = fnCreateCnlSvycomptLinkPopupContent(properties);
	} else if (type == 'FRD') {
		id = 'frdSvycomptLink';
		var color = properties.routetype == '수정노선' ? '#ffff00' : '#0000ff';
		style = new ol.style.Style({
			stroke : new ol.style.Stroke({
				color : color,
				width : 4
			})
		});
		content = fnCreateFrdSvycomptLinkPopupContent(properties);
	} else if (type == 'MSE') {
		id = 'mseSvycomptLink';
		content = fnCreateMseSvycomptLinkPopupContent(properties);
	} else if (type == 'PCS') {
		id = 'pcsSvycomptLink';
		content = fnCreatePcsSvycomptLinkPopupContent(properties);
	}

	if (style == null) {
		style = new ol.style.Style({
			image : new ol.style.Circle({
				radius : 10,
				stroke : new ol.style.Stroke({
					color : '#fff'
				}),
				fill : new ol.style.Fill({
					color : 'rgba(254,46,100,0.7)'
				})
			})
		})
	}

	if (id != null) {
		// 조사완료지 포인트 레이어 가져오기
		var layer = SM.map.getLayer(id);

		// 없을 경우 레이어 생성
		if (!layer) {
			layer = CONFIG.LAYERS.VECTORS.filter(function(l) {
				return l.get("id") == id;
			})[0]; // Config 설정에서 해당 레이어 정보 가져오기
			SM.map.addLayer(layer); // 레이어 맵 객체 등록
		}

		layer.getSource().addFeature(
				new ol.format.GeoJSON().readFeature(feature)); // 데이터 추가
		layer.setVisible(true); // 레이어 활성화
		layer.getSource().getFeatures()[0].setStyle(style);

		var coord = ol.extent.getCenter(layer.getSource().getExtent());

		SM.addSelectFeature(id, fnSelectFeature) // 셀렉트 이벤트 추가
		SM.map.getView().fit(layer.getSource().getExtent()); // 해당 지점으로 이동

		var options = {
			type : 'search',
			text : '상세보기',
			callback : fnOpenDetailInfo
		};
		// 오버레이 열기
		fnOpenOverlay('popup-' + id, coord, content, options);
	} else {
		alert("조사정보를 확인할 수 없습니다.");
	}
}

/**
 * @author ipodo
 * @name fnCreateStriplandPopupContent
 * @param {Object}
 *            props - feature properties
 * @returns {Element}
 * @description 기초조사등록지 오버레이 컨텐츠 생성
 */
function fnCreateStriplandPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="15%"/><col width=""/><col width="15%"/><col width=""/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.SD + " " + props.SGG + " " + props.EMD + " ";
	(props.RI.length > 0) ? addr += props.RI + " " + props.JIBUN : props.JIBUN;

	var tr1 = $('<tr><td class="title">조사지</td><td colspan="3"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">등록일</td><td colspan="3"></td></tr>');
	$(tr2).children("td:eq(1)").text(props.CREATEDT);
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사유형</td><td></td><td class="title">조사연도</td><td></td></tr>');
	$(tr3).children("td:eq(1)").text(props.TYPE); // 조사 유형
	$(tr3).children("td:eq(3)").text(props.YEAR); // 조사연도
	$(tbody).append(tr3);

	var tr4 = $('<tr><td class="title">조사ID</td><td></td><td class="title">작성자</td><td></td></tr>');
	$(tr4).children("td:eq(1)").text(props.ID); // 조사아이디
	$(tr4).children("td:eq(3)").text(props.WRITER); // 작성자
	$(tbody).append(tr4);

	var tr5 = $('<tr><td class="title">관할1</td><td></td><td class="title">관할2</td><td></td></tr>');
	$(tr5).children("td:eq(1)").text(props.REGION1); // 관할1
	$(tr5).children("td:eq(3)").text(props.REGION2); // 관할2
	$(tbody).append(tr5);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

/**
 * @author ipodo
 * @name fnCreateSvycomptPopupContent
 * @param {Object}
 *            props - feature properties
 * @returns {Element}
 * @description 기초조사완료지 오버레이 컨텐츠 생성
 */
function fnCreateSvycomptPopupContent(props) {

	var table = $('<table><thead><colgroup><col width="15%"/><col width=""/><col width="15%"/><col width=""/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props['시도'] + " " + props['시군구'] + " " + props['읍면동'] + " ";
	(props['리'].length > 0) ? addr += props['리'] + " " + props['지번']
			: props['지번'];

	var tr1 = $('<tr><td class="title">조사지</td><td colspan="3"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">조사유형</td><td></td><td class="title">조사일</td><td></td></tr>');
	$(tr2).children("td:eq(1)").text(props['조사유형']); // 조사 유형
	$(tr2).children("td:eq(3)").text(props['조사일']); // 조사일
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사ID</td><td></td><td class="title">조사자</td><td></td></tr>');
	$(tr3).children("td:eq(1)").text(props['조사ID']); // 조사아이디
	$(tr3).children("td:eq(3)").text(props['조사자']); // 조사자
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title">시점좌표X</td><td></td><td class="title">시점좌표Y</td><td></td></tr>');
	$(tr4).children("td:eq(1)").text(props['시점좌표_X']); // 시점좌표 X
	$(tr4).children("td:eq(3)").text(props['시점좌표_Y']); // 시점좌표 Y
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">끝점좌표X</td><td></td><td class="title">끝점좌표Y</td><td></td></tr>');
	$(tr5).children("td:eq(1)").text(props['끝점좌표_X']); // 종점좌표 X
	$(tr5).children("td:eq(3)").text(props['끝점좌표_Y']); // 종점좌표 Y
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">점수계</td><td></td><td class="title">주요위험성</td><td></td></tr>');
	$(tr6).children("td:eq(1)").text(props['점수계']); // 점수계
	$(tr6).children("td:eq(3)").text(props['주요위험성']); // 주요위험성
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">보호대상 측정값</td><td></td><td class="title">보호대상 점수</td><td></td></tr>');
	$(tr7).children("td:eq(1)").text(props['보호대상_측정값']);// 보호대상 측정값
	$(tr7).children("td:eq(3)").text(props['보호대상_점수']);// 보호대상 점수
	$(tbody).append(tr7);

	if (props['조사유형'] == "토석류") {
		var tr8 = $('<tr class="info close"><td class="title">총계류길이 측정값</td><td></td><td class="title">총계류길이 점수</td><td></td></tr>');
		$(tr8).children("td:eq(1)").text(props['총계류길이_측정값']);// 총계류길이 측정값
		$(tr8).children("td:eq(3)").text(props['총계류길이_점수']);// 총계류길이 점수
		$(tbody).append(tr8);

		var tr9 = $('<tr class="info close"><td class="title">계류평균경사 측정값</td><td></td><td class="title">계류평균경사 점수</td><td></td></tr>');
		$(tr9).children("td:eq(1)").text(props['계류평균경사_측정값']);// 계류평균경사 측정값
		$(tr9).children("td:eq(3)").text(props['계류평균경사_점수']);// 계류평균경사 점수
		$(tbody).append(tr9);

		var tr10 = $('<tr class="info close"><td class="title">황폐발생원 측정값</td><td></td><td class="title">황폐발생원 점수</td><td></td></tr>');
		$(tr10).children("td:eq(1)").text(props['황폐발생원_측정값']);// 황폐발생원 측정값
		$(tr10).children("td:eq(3)").text(props['황폐발생원_점수']);// 황폐발생원 점수
		$(tbody).append(tr10);

		var tr11 = $('<tr class="info close"><td class="title">집수면적 측정값</td><td></td><td class="title">집수면적 점수</td><td></td></tr>');
		$(tr11).children("td:eq(1)").text(props['집수면적_측정값']);// 집수면적 측정값
		$(tr11).children("td:eq(3)").text(props['집수면적_점수']);// 집수면적 점수
		$(tbody).append(tr11);

		var tr12 = $('<tr class="info close"><td class="title">전석분포비율 측정값</td><td></td><td class="title">전석분포비율 점수</td><td></td></tr>');
		$(tr12).children("td:eq(1)").text(props['전석분포비율_측정값']);// 전석분포비율 측정값
		$(tr12).children("td:eq(3)").text(props['전석분포비율_점수']);// 전석분포비율 점수
		$(tbody).append(tr12);
	} else if (props['조사유형'] == "산사태") {
		var tr13 = $('<tr class="info close"><td class="title">경사길이 측정값</td><td></td><td class="title">경사길이 점수</td><td></td></tr>');
		$(tr13).children("td:eq(1)").text(props['경사길이_측정값']);// 경사길이 측정값
		$(tr13).children("td:eq(3)").text(props['경사길이_점수']);// 경사길이 점수
		$(tbody).append(tr13);

		var tr14 = $('<tr class="info close"><td class="title">경사도 측정값</td><td></td><td class="title">경사도 점수</td><td></td></tr>');
		$(tr14).children("td:eq(1)").text(props['경사도_측정값']);// 경사도 측정값
		$(tr14).children("td:eq(3)").text(props['경사도_점수']);// 경사도 점수
		$(tbody).append(tr14);

		var tr15 = $('<tr class="info close"><td class="title">사면형 측정값</td><td></td><td class="title">사면형 점수</td><td></td></tr>');
		$(tr15).children("td:eq(1)").text(props['사면형_측정값']);// 사면형 측정값
		$(tr15).children("td:eq(3)").text(props['사면형_점수']);// 사면형 점수
		$(tbody).append(tr15);

		var tr16 = $('<tr class="info close"><td class="title">임상 측정값</td><td></td><td class="title">임상 점수</td><td></td></tr>');
		$(tr16).children("td:eq(1)").text(props['임상_측정값']);// 임상 측정값
		$(tr16).children("td:eq(3)").text(props['임상_점수']);// 임상 점수
		$(tbody).append(tr16);

		var tr17 = $('<tr class="info close"><td class="title">모암 측정값</td><td></td><td class="title">모암 점수</td><td></td></tr>');
		$(tr17).children("td:eq(1)").text(props['모암_측정값']);// 모암 측정값
		$(tr17).children("td:eq(3)").text(props['모암_점수']);// 모암 점수
		$(tbody).append(tr17);
	}

	var tr18 = $('<tr class="info close"><td class="title">조사자 의견</td><td colspan="3"></td></tr>');
	$(tr18).children("td:eq(1)").text(props['조사자의견']);// 조사자 의견
	$(tbody).append(tr18);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

/**
 * @author ipodo
 * @name fnCreateBscSvycomptLinkPopupContent
 * @param {Object}
 *            props - feature properties
 * @returns {Element}
 * @description 기초조사완료지 바로가기 오버레이 컨텐츠 생성
 */
function fnCreateBscSvycomptLinkPopupContent(props) {

	var table = $('<table><thead><colgroup><col width="15%"/><col width=""/><col width="15%"/><col width=""/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">조사지</td><td colspan="3"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">조사유형</td><td></td><td class="title">조사일</td><td></td></tr>');
	$(tr2).children("td:eq(1)").text(props.svytype); // 조사 유형
	$(tr2).children("td:eq(3)").text(props.svydt); // 조사일
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사ID</td><td></td><td class="title">조사자</td><td></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svyid); // 조사아이디
	$(tr3).children("td:eq(3)").text(props.svyuser); // 조사자
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title">시점좌표X</td><td></td><td class="title">시점좌표Y</td><td></td></tr>');
	$(tr4).children("td:eq(1)").text(props.bpx); // 시점좌표 X
	$(tr4).children("td:eq(3)").text(props.bpy); // 시점좌표 Y
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">끝점좌표X</td><td></td><td class="title">끝점좌표Y</td><td></td></tr>');
	$(tr5).children("td:eq(1)").text(props.epx); // 종점좌표 X
	$(tr5).children("td:eq(3)").text(props.epy); // 종점좌표 Y
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">점수계</td><td></td><td class="title">주요위험성</td><td></td></tr>');
	$(tr6).children("td:eq(1)").text(props.sm); // 점수계
	$(tr6).children("td:eq(3)").text(props.mainrisk); // 주요위험성
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">보호대상 측정값</td><td></td><td class="title">보호대상 점수</td><td></td></tr>');
	$(tr7).children("td:eq(1)").text(props.saftyval);// 보호대상 측정값
	$(tr7).children("td:eq(3)").text(props.saftyscore);// 보호대상 점수
	$(tbody).append(tr7);

	if (props.svytype == "토석류") {
		var tr8 = $('<tr class="info close"><td class="title">총계류길이 측정값</td><td></td><td class="title">총계류길이 점수</td><td></td></tr>');
		$(tr8).children("td:eq(1)").text(props.tltrntltval);// 총계류길이 측정값
		$(tr8).children("td:eq(3)").text(props.tltrntltscore);// 총계류길이 점수
		$(tbody).append(tr8);

		var tr9 = $('<tr class="info close"><td class="title">계류평균경사 측정값</td><td></td><td class="title">계류평균경사 점수</td><td></td></tr>');
		$(tr9).children("td:eq(1)").text(props.trntavgslpval);// 계류평균경사 측정값
		$(tr9).children("td:eq(3)").text(props.trntavgslpscore);// 계류평균경사 점수
		$(tbody).append(tr9);

		var tr10 = $('<tr class="info close"><td class="title">황폐발생원 측정값</td><td></td><td class="title">황폐발생원 점수</td><td></td></tr>');
		$(tr10).children("td:eq(1)").text(props.devoccauseval);// 황폐발생원 측정값
		$(tr10).children("td:eq(3)").text(props.devoccausescore);// 황폐발생원 점수
		$(tbody).append(tr10);

		var tr11 = $('<tr class="info close"><td class="title">집수면적 측정값</td><td></td><td class="title">집수면적 점수</td><td></td></tr>');
		$(tr11).children("td:eq(1)").text(props.wclctareaval);// 집수면적 측정값
		$(tr11).children("td:eq(3)").text(props.wclctareascore);// 집수면적 점수
		$(tbody).append(tr11);

		var tr12 = $('<tr class="info close"><td class="title">전석분포비율 측정값</td><td></td><td class="title">전석분포비율 점수</td><td></td></tr>');
		$(tr12).children("td:eq(1)").text(props.distbmdsbrateval);// 전석분포비율
																	// 측정값
		$(tr12).children("td:eq(3)").text(props.distbmdsbratescore);// 전석분포비율 점수
		$(tbody).append(tr12);
	} else if (props.svytype == "산사태") {
		var tr13 = $('<tr class="info close"><td class="title">경사길이 측정값</td><td></td><td class="title">경사길이 점수</td><td></td></tr>');
		$(tr13).children("td:eq(1)").text(props.slenval);// 경사길이 측정값
		$(tr13).children("td:eq(3)").text(props.slenscore);// 경사길이 점수
		$(tbody).append(tr13);

		var tr14 = $('<tr class="info close"><td class="title">경사도 측정값</td><td></td><td class="title">경사도 점수</td><td></td></tr>');
		$(tr14).children("td:eq(1)").text(props.slopeval);// 경사도 측정값
		$(tr14).children("td:eq(3)").text(props.slopescore);// 경사도 점수
		$(tbody).append(tr14);

		var tr15 = $('<tr class="info close"><td class="title">사면형 측정값</td><td></td><td class="title">사면형 점수</td><td></td></tr>');
		$(tr15).children("td:eq(1)").text(props.sformval);// 사면형 측정값
		$(tr15).children("td:eq(3)").text(props.sformscore);// 사면형 점수
		$(tbody).append(tr15);

		var tr16 = $('<tr class="info close"><td class="title">임상 측정값</td><td></td><td class="title">임상 점수</td><td></td></tr>');
		$(tr16).children("td:eq(1)").text(props.frstfrval);// 임상 측정값
		$(tr16).children("td:eq(3)").text(props.frstfrscore);// 임상 점수
		$(tbody).append(tr16);

		var tr17 = $('<tr class="info close"><td class="title">모암 측정값</td><td></td><td class="title">모암 점수</td><td></td></tr>');
		$(tr17).children("td:eq(1)").text(props.prntrckval);// 모암 측정값
		$(tr17).children("td:eq(3)").text(props.prntrckval);// 모암 점수
		$(tbody).append(tr17);
	}

	var tr18 = $('<tr class="info close"><td class="title">조사자 의견</td><td colspan="3"></td></tr>');
	$(tr18).children("td:eq(1)").text(props.opinion);// 조사자 의견
	$(tbody).append(tr18);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

/**
 * @author ipodo
 * @name fnCreateAprSvycomptLinkPopupContent
 * @param {Object}
 *            props - feature properties
 * @returns {Element}
 * @description 외관점검완료지 바로가기 오버레이 컨텐츠 생성
 */
function fnCreateAprSvycomptLinkPopupContent(props) {

	var table = $('<table><thead><colgroup><col width="15%"/><col width=""/><col width="15%"/><col width=""/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">조사지</td><td colspan="3"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">종류</td><td colspan="3"></td></tr>');
	$(tr2).children("td:eq(1)").text(props.knd); // 종류
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사유형</td><td></td><td class="title">점검일시</td><td></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svytype); // 조사 유형
	$(tr3).children("td:eq(3)").text(props.svydt); // 점검일시
	$(tbody).append(tr3);

	var tr4 = $('<tr><td class="title">조사아이디</td><td></td><td class="title">조사형식</td><td></td></tr>');
	$(tr4).children("td:eq(1)").text(props.svyid); // 조사아이디
	$(tr4).children("td:eq(3)").text(props.svyform); // 조사형식
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">조사자</td><td></td><td class="title">점검자</td><td></td></tr>');
	$(tr5).children("td:eq(1)").text(props.svyuser); // 조사자
	$(tr5).children("td:eq(3)").text(props.chkuser); // 점검자
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">위치정보X</td><td></td><td class="title">위치정보Y</td><td></td></tr>');
	$(tr6).children("td:eq(1)").text(props.px); // 위치정보_X
	$(tr6).children("td:eq(3)").text(props.py); // 위치정보_Y
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">기타판정값</td><td></td><td class="title">수문판정값</td><td></td></tr>');
	$(tr7).children("td:eq(1)").text(props.etcjdgval); // 기타판정값
	$(tr7).children("td:eq(3)").text(props.flugtjdgval); // 수문판정값
	$(tbody).append(tr7);

	var tr8 = $('<tr class="info close"><td class="title">국가지점번호</td><td></td><td class="title">시설제원 년도</td><td></td></tr>');
	$(tr8).children("td:eq(1)").text(props.nationspotnum); // 국가지점번호
	$(tr8).children("td:eq(3)").text(props.fcltyear); // 시설제원년도
	$(tbody).append(tr8);

	if (props.svytype == "사방댐 외관점검") {
		var tr9 = $('<tr class="info close"><td class="title">시설제원 종류</td><td></td><td class="title">시설제원 형식</td><td></td></tr>');
		$(tr9).children("td:eq(1)").text(props.fcltknd); // 시설제원종류 삭제
		$(tr9).children("td:eq(3)").text(props.fcltform); // 시설제원형식 삭제
		$(tbody).append(tr9);

		var tr14 = $('<tr class="info close"><td class="title">시설제원 상장</td><td></td><td class="title">시설제원 하장</td><td></td></tr>');
		$(tr14).children("td:eq(1)").text(props.fcltuprhg); // 시설제원 상장
		$(tr14).children("td:eq(3)").text(props.fcltlwrhg); // 시설제원 하장
		$(tbody).append(tr14);

		var tr13 = $('<tr class="info close"><td class="title">시설제원 유효고</td><td></td><td class="title">사방댐관리번호</td><td></td></tr>');
		$(tr13).children("td:eq(1)").text(props.fcltstkhg); // 시설제원 유효고
		$(tr13).children("td:eq(3)").text(props.ecnrrnum); // 사방댐관리번호
		$(tbody).append(tr13);

		var tr21 = $('<tr class="info close"><td class="title">본댐 측정값</td><td></td><td class="title">본댐 판정값</td><td></td></tr>');
		$(tr21).children("td:eq(1)").text(props.orginldamval); // 본댐측정값
		$(tr21).children("td:eq(3)").text(props.orginldamjdgval); // 본댐판정값
		$(tbody).append(tr21);

		var tr22 = $('<tr class="info close"><td class="title">측벽 측정값</td><td></td><td class="title">측벽 판정값</td><td></td></tr>');
		$(tr22).children("td:eq(1)").text(props.sidewallval); // 측벽측정값
		$(tr22).children("td:eq(3)").text(props.sidewalljdgval); // 측벽판정값
		$(tbody).append(tr22);

		var tr23 = $('<tr class="info close"><td class="title">물받이 측정값</td><td></td><td class="title">물받이 판정값</td><td></td></tr>');
		$(tr23).children("td:eq(1)").text(props.dwnsptval); // 물받이측정값
		$(tr23).children("td:eq(3)").text(props.dwnsptjdgval); // 물받이판정값
		$(tbody).append(tr23);

		var tr24 = $('<tr class="info close"><td class="title">저사상태 판정</td><td colspan="3"></td></tr>');
		$(tr24).children("td:eq(1)").text(props.snddpsitjdgval); // 저사상태측정값
		$(tbody).append(tr24);
	}

	if (props.svytype == "계류보전 외관점검") {
		var tr10 = $('<tr class="info close"><td class="title">시설제원 길이</td><td></td><td class="title">시설제원 깊이</td><td></td></tr>');
		$(tr10).children("td:eq(1)").text(props.fcltlng); // 시설제원길이
		$(tr10).children("td:eq(3)").text(props.fcltdept); // 시설제원깊이
		$(tbody).append(tr10);

		var tr11 = $('<tr class="info close"><td class="title">시설제원 폭</td><td></td><td class="title">시설제원 주요구조물</td><td></td></tr>');
		$(tr11).children("td:eq(1)").text(props.fcltrng); // 시설제원 폭
		$(tr11).children("td:eq(3)").text(props.fcltmainstrctu); // 시설제원
																	// 주요구조물 삭제
		$(tbody).append(tr11);

		var tr17 = $('<tr class="info close"><td class="title">골막이측정값</td><td></td><td class="title">골막이판정값</td><td></td></tr>');
		$(tr17).children("td:eq(1)").text(props.chkdamval); // 골막이측정값
		$(tr17).children("td:eq(3)").text(props.chkdamjdgval); // 골막이판정값
		$(tbody).append(tr17);

		var tr18 = $('<tr class="info close"><td class="title">기슭막이 측정값</td><td></td><td class="title">기슭막이 판정값</td><td></td></tr>');
		$(tr18).children("td:eq(1)").text(props.rvtmntval); // 기슭막이 측정값
		$(tr18).children("td:eq(3)").text(props.rvtmntjdgval); // 기슭막이 판정값
		$(tbody).append(tr18);

		var tr19 = $('<tr class="info close"><td class="title">바닥막이 측정값</td><td></td><td class="title">바닥막이 판정값</td><td></td></tr>');
		$(tr19).children("td:eq(1)").text(props.grdstablval); // 바닥막이 측정값
		$(tr19).children("td:eq(3)").text(props.grdstabljdgval); // 바닥막이 판정값
		$(tbody).append(tr19);

		var tr20 = $('<tr class="info close"><td class="title">계류상태 판정값</td><td colspan="3"></td></tr>');
		$(tr20).children("td:eq(1)").text(props.mooringjdgval); // 계류상태 측정값
		$(tbody).append(tr20);
	}

	if (props.svytype == "산지사방 외관점검") {
		var tr11 = $('<tr class="info close"><td class="title">시설제원 폭</td><td></td><td class="title">시설제원 높이</td><td></td></tr>');
		$(tr11).children("td:eq(1)").text(props.fcltrng); // 시설제원 폭
		$(tr11).children("td:eq(3)").text(props.fclthg); // 시설제원 높이
		$(tbody).append(tr11);

		var tr15 = $('<tr class="info close"><td class="title">시설제원 구조물</td><td></td><td class="title">시설제원 경사</td><td></td></tr>');
		$(tr15).children("td:eq(1)").text(props.fcltstrctu); // 시설제원 구조물 삭제
		$(tr15).children("td:eq(3)").text(props.fcltslp); // 시설제원 경사
		$(tbody).append(tr15);

		var tr25 = $('<tr class="info close"><td class="title">보강시설 측정값</td><td></td><td class="title">보강시설 판정값</td><td></td></tr>');
		$(tr25).children("td:eq(1)").text(props.reinfcval); // 보강시설측정값
		$(tr25).children("td:eq(3)").text(props.reinfcjdgval); // 보강시설판정값
		$(tbody).append(tr25);

		var tr26 = $('<tr class="info close"><td class="title">보호시설 측정값</td><td></td><td class="title">보호시설 판정값</td><td></td></tr>');
		$(tr26).children("td:eq(1)").text(props.prtcval); // 보호시설측정값
		$(tr26).children("td:eq(3)").text(props.prtcjdgval); // 보호시설판정값
		$(tbody).append(tr26);

		var tr27 = $('<tr class="info close"><td class="title">배수시설 측정값</td><td></td><td class="title">배수시설 판정값</td><td></td></tr>');
		$(tr27).children("td:eq(1)").text(props.drngval); // 배수시설측정값
		$(tr27).children("td:eq(3)").text(props.drngjdgval); // 배수시설판정값
		$(tbody).append(tr27);

		var tr28 = $('<tr class="info close"><td class="title">사면상태 측정값</td><td colspan="3"></td></tr>');
		$(tr28).children("td:eq(1)").text(props.slopejdgval); // 사면상태판정값
		$(tbody).append(tr28);
	}

	var tr33 = $('<tr class="info close"><td class="title">식생상태 판정값</td><td></td><td class="title">안전시설 판정값</td><td></td></tr>');
	$(tr33).children("td:eq(1)").text(props.vtnsttusjdgval); // 식생상태 판정값
	$(tr33).children("td:eq(3)").text(props.sffcjdgval); // 안전시설 판정값
	$(tbody).append(tr33);

	var tr34 = $('<tr class="info close"><td class="title">접근도로 판정값</td><td></td><td class="title">지정해제</td><td></td></tr>');
	$(tr34).children("td:eq(1)").text(props.accssrdjdgval); // 접근도로 판정값
	$(tr34).children("td:eq(3)").text(props.appnrelis); // 지정해제
	$(tbody).append(tr34);

	var tr35 = $('<tr><td class="title">최종점검결과</td><td colspan="3"></td></tr>');
	$(tr35).children("td:eq(1)").text(props.fckrslt); // 최종점검결과
	$(tbody).append(tr35);

	var tr36 = $('<tr><td class="title">조치사항</td><td colspan="3"></td></tr>');
	$(tr36).children("td:eq(3)").text(props.mngmtr); // 조치사항
	$(tbody).append(tr36);

	var tr37 = $('<tr><td class="title">종합의견</td><td colspan="3"></td></tr>');
	$(tr37).children("td:eq(1)").text(props.opinion); // 종합의견
	$(tbody).append(tr37);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

function fnCreateLcpSvycomptLinkPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">소재지</td><td colspan="3"></td><td class="title">판정등급</td><td></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tr1).children("td:eq(3)").text(props.lastgrd);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">조사ID</td><td colspan="2"></td><td class="title">조사일자</td><td colspan="2"></td></tr>');
	$(tr2).children("td:eq(1)").text(props.svyid);
	$(tr2).children("td:eq(3)").text(props.svydt);
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사자</td><td colspan="2"></td><td class="title">고도</td><td colspan="2"></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svyuser);
	$(tr3).children("td:eq(3)").text(props.pz);
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title" colspan="6">지질특성</td></tr>');
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">주<br>구성암석</td><td colspan="2"></td><td class="title" colspan="2">타지층 및 관입암</td><td></td></tr>');
	$(tr5).children("td:eq(1)").text(props.cmprokval);
	$(tr5).children("td:eq(3)").text(props.instrokat);
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">암석풍화</td><td></td><td class="title">지질구조<br>단층</td><td></td><td class="title">지질구조<br>습곡</td><td></td></tr>');
	$(tr6).children("td:eq(1)").text(props.rokwthrval);
	$(tr6).children("td:eq(3)").text(props.geologyflt);
	$(tr6).children("td:eq(5)").text(props.geologyfld);
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">불연속면<br>방향수</td><td></td><td class="title">불연속면<br>사면의<br>방향성</td><td></td><td class="title">불연속면<br>간격(cm)</td><td></td></tr>');
	$(tr7).children("td:eq(1)").text(props.disctnuplndrcno);
	$(tr7).children("td:eq(3)").text(props.disctnuplnslpval);
	$(tr7).children("td:eq(5)").text(props.disctnuplnintvlval);
	$(tbody).append(tr7);

	var tr8 = $('<tr class="info close"><td class="title" colspan="6">토양특성</td></tr>');
	$(tbody).append(tr8);

	var tr9 = $('<tr class="info close"><td class="title">토양형</td><td colspan="2"></td><td class="title" colspan="2">토심 B층까지의 깊이</td><td></td></tr>');
	$(tr9).children("td:eq(1)").text(props.soilty);
	$(tr9).children("td:eq(3)").text(props.soildeptbval);
	$(tbody).append(tr9);

	var tr10 = $('<tr class="info close"><td class="title" colspan="2">토성 B층기준(약30cm)부근<td></td><td class="title" colspan="2">토성 B층기준</td><td></td></tr>');
	$(tr10).children("td:eq(1)").text(props.soilclassbval);
	$(tr10).children("td:eq(3)").text(props.soilstrct);
	$(tbody).append(tr10);

	var tr11 = $('<tr class="info close"><td class="title">토양수분상태<td></td><td class="title">암석노출도</td><td></td><td class="title">너덜유무</td><td></td></tr>');
	$(tr11).children("td:eq(1)").text(props.soilwtrval);
	$(tr11).children("td:eq(3)").text(props.rokexpsr);
	$(tr11).children("td:eq(5)").text(props.talusat);
	$(tbody).append(tr11);

	var tr12 = $('<tr class="info close"><td class="title" colspan="6">지형특성</td></tr>');
	$(tbody).append(tr12);

	var tr13 = $('<tr class="info close"><td class="title">지형구분</td><td colspan="2"></td><td class="title">최대높이</td><td colspan="2"></td></tr>');
	$(tr13).children("td:eq(1)").text(props.tpgrphval);
	$(tr13).children("td:eq(3)").text(props.mxhg);
	$(tbody).append(tr13);

	var tr14 = $('<tr class="info close"><td class="title" colspan="2">조사지역위치(사면 10분비)</td><td colspan="2"></td><td class="title">평균경사도</td><td></td></tr>');
	$(tr14).children("td:eq(1)").text(
			props.arealcval + "(" + props.arealcridge + "부 능선/10)");
	$(tr14).children("td:eq(3)").text(props.slprngval);
	$(tbody).append(tr14);

	var tr15 = $('<tr class="info close"><td class="title" colspan="2">평면적(수평적)</td><td></td><td class="title" colspan="2">종단면형(수직적)</td><td></td></tr>');
	$(tr15).children("td:eq(1)").text(props.plnformval);
	$(tr15).children("td:eq(3)").text(props.lngformval);
	$(tbody).append(tr15);

	var tr13 = $('<tr class="info close"><td class="title" colspan="6">수리특성</td></tr>');
	$(tbody).append(tr13);

	var tr14 = $('<tr class="info close"><td class="title" colspan="5">상류로부터 지하수 유입 가능성<td></td></tr>');
	$(tr14).children("td:eq(1)").text(props.ugrwtr_posblty);
	$(tbody).append(tr14);

	var tr15 = $('<tr class="info close"><td class="title" colspan="2">하류계류의유무<td></td><td class="title" colspan="2">샘,소,저주지 유무<td></td></tr>');
	$(tr15).children("td:eq(1)").text(props.dwstrm_at);
	$(tr15).children("td:eq(3)").text(props.sprg_at);
	$(tbody).append(tr15);

	var tr16 = $('<tr class="info close"><td class="title" colspan="6">산림특성</td></tr>');
	$(tbody).append(tr16);

	var tr17 = $('<tr class="info close"><td class="title">임상<td colspan="2"></td><td class="title">주요수종<td colspan="2"></td></tr>');
	$(tr17).children("td:eq(1)").text(props.frstfrval);
	$(tr17).children("td:eq(3)").text(props.maintreeknd);
	$(tbody).append(tr17);

	var tr18 = $('<tr class="info close"><td class="title" colspan="6">기타특성</td></tr>');
	$(tbody).append(tr18);

	var tr19 = $('<tr class="info close"><td class="title" colspan="2">임지이용상태<td></td><td class="title" colspan="2">조사경계하부임지이용상태<td></td></tr>');
	$(tr19).children("td:eq(1)").text(props.frlndsttus);
	$(tr19).children("td:eq(3)").text(props.lwbndlwfrlndsttus);
	$(tbody).append(tr19);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

function fnCreateWkaSvycomptLinkPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">소재지</td><td colspan="5"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">조사자</td><td colspan="2"></td><td class="title">판정등급</td><td colspan="2"></td></tr>');
	$(tr2).children("td:eq(1)").text(props.svyuser);
	$(tr2).children("td:eq(3)").text(props.jdgmntgrad);
	$(tbody).append(tr2);
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사ID</td><td colspan="2"></td><td class="title">조사일자</td><td colspan="2"></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svyid);
	$(tr3).children("td:eq(3)").text(props.svydt);
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title">유역면적</td><td colspan="2"></td><td class="title">취약지역면적</td><td colspan="2"></td></tr>');
	$(tr4).children("td:eq(1)").text(props.dgrarea);
	$(tr4).children("td:eq(3)").text(props.frgltyrenarea);
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">보호시설</td><td colspan="2"></td><td class="title">인가또는호수</td><td colspan="2"></td></tr>');
	$(tr5).children("td:eq(1)").text(
			props.safefct + "(" + props.placelen + "개소)");
	$(tr5).children("td:eq(3)").text(props.house + "(" + props.lake + "개소)");
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">상부주요시설</td><td colspan="2"></td><td class="title">상부인가현황</td><td colspan="2"></td></tr>');
	$(tr6).children("td:eq(1)").text(props.highmainfct);
	$(tr6).children("td:eq(3)").text(props.highhousesttus);
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">하부주요시설</td><td colspan="2"></td><td class="title">하부인가현황</td><td colspan="2"></td></tr>');
	$(tr7).children("td:eq(1)").text(props.lowmainfct);
	$(tr7).children("td:eq(3)").text(props.lowhousesttus);
	$(tbody).append(tr7);

	if (props.svytype == "취약지역 실태조사(산사태)") {
		var tr8 = $('<tr class="info close"><td class="title" colspan="6">사면현황</td></tr>');
		$(tbody).append(tr8);

		var tr9 = $('<tr class="info close"><td class="title">사면상태</td><td></td><td class="title">모암</td><td></td><td class="title">토성</td><td></td></tr>');
		$(tr9).children("td:eq(1)").text(props.dirsttus);
		$(tr9).children("td:eq(3)").text(props.prntrock);
		$(tr9).children("td:eq(5)").text(props.soilchar);
		$(tbody).append(tr9);

		var tr10 = $('<tr class="info close"><td class="title">경사길이</td><td></td><td class="title">경사도</td><td></td><td class="title">경사위치</td><td></td></tr>');
		$(tr10).children("td:eq(1)").text(props.slopelen);
		$(tr10).children("td:eq(3)").text(
				props.slope + "° / " + props.slopemin + "° ~ " + props.slopemax
						+ "°");
		$(tr10).children("td:eq(5)").text(
				props.ridge + "(" + props.slopelc + "/10)");
		$(tbody).append(tr10);

		var tr11 = $('<tr class="info close"><td class="title">최고지점</td><td></td><td class="title">최저지점</td><td></td><td class="title">사면형</td><td></td></tr>');
		$(tr11).children("td:eq(1)").text(props.pntmax + "m");
		$(tr11).children("td:eq(3)").text(props.pntmin + "m");
		$(tr11).children("td:eq(5)").text(props.dirfrm);
		$(tbody).append(tr11);

		var tr12 = $('<tr class="info close"><td class="title" colspan="6">토질현황</td></tr>');
		$(tbody).append(tr12);

		var tr13 = $('<tr class="info close"><td class="title">토심</td><td></td><td class="title">균열</td><td></td><td class="title">함몰</td><td></td></tr>');
		$(tr13).children("td:eq(1)").text(props.soildep + "m");
		$(tr13).children("td:eq(3)").text(props.crck);
		$(tr13).children("td:eq(5)").text(props.sink);
		$(tbody).append(tr13);

		var tr13 = $('<tr class="info close"><td class="title">융기</td><td></td><td class="title">말단부압출</td><td></td><td class="title">확대예상</td><td></td></tr>');
		$(tr13).children("td:eq(1)").text(props.uplift);
		$(tr13).children("td:eq(3)").text(props.extdistalend);
		$(tr13).children("td:eq(5)").text(props.expandpredic);
		$(tbody).append(tr13);

		var tr14 = $('<tr class="info close"><td class="title" colspan="6">임상현황</td></tr>');
		$(tbody).append(tr14);

		var tr15 = $('<tr class="info close"><td class="title">임상</td><td></td><td class="title">임분밀도</td><td></td><td class="title">임분경급</td><td></td></tr>');
		$(tr15).children("td:eq(1)").text(props.frtp);
		$(tr15).children("td:eq(3)").text(props.stddnst);
		$(tr15).children("td:eq(5)").text(props.stddmcls);
		$(tbody).append(tr15);

		var tr16 = $('<tr class="info close"><td class="title" colspan="6">용수현황</td></tr>');
		$(tbody).append(tr16);

		var tr17 = $('<tr class="info close"><td class="title" colspan="2">용수상시여부</td><td></td><td class="title" colspan="2">강우시용수</td><td></td></tr>');
		$(tr17).children("td:eq(1)").text(props.uswtrordtmat);
		$(tr17).children("td:eq(3)").text(props.rainfallwater);
		$(tbody).append(tr17);

		var tr18 = $('<tr class="info close"><td class="title" colspan="2">사면이 축축함</td><td></td><td class="title" colspan="2">사면이 건조함</td><td></td></tr>');
		$(tr18).children("td:eq(1)").text(props.dirwet);
		$(tr18).children("td:eq(3)").text(props.dirdry);
		$(tbody).append(tr18);
	} else if (props.svytype == "취약지역 실태조사(토석류)") {
		var tr8 = $('<tr class="info close"><td class="title" colspan="6">기본정보</td></tr>');
		$(tbody).append(tr8);

		var tr9 = $('<tr class="info close"><td class="title">황폐발생원</td><td colspan="2"></td><td class="title">계류경사</td><td colspan="2"></td></tr>');
		$(tr9)
				.children("td:eq(1)")
				.text(
						props.scodsltn != null && props.scodsltn != undefined ? "산사태위험지 "
								+ props.scodsltn + " 유역"
								: "");
		$(tr9).children("td:eq(3)").text(
				props.mntntrntavg + "° / " + props.mntntrntmin + "° ~ "
						+ props.mntntrntmax + "°");
		$(tbody).append(tr9);

		var tr10 = $('<tr class="info close"><td class="title">계류길이</td><td></td><td class="title">변곡점길이</td><td></td><td class="title">변곡점고도</td><td></td></tr>');
		$(tr10).children("td:eq(1)").text(props.mntntrntlen + "m");
		$(tr10).children("td:eq(3)").text(props.iftnpntlen + "m");
		$(tr10).children("td:eq(5)").text(props.iftnpntevtn + "m");
		$(tbody).append(tr10);

		var tr11 = $('<tr class="info close"><td class="title">임상</td><td></td><td class="title">임분밀도</td><td></td><td class="title">임분경급</td><td></td></tr>');
		$(tr11).children("td:eq(1)").text(props.frtp);
		$(tr11).children("td:eq(3)").text(props.stddnst);
		$(tr11).children("td:eq(5)").text(props.stddmcls);
		$(tbody).append(tr11);

		var tr11 = $('<tr class="info close"><td class="title" colspan="2">종점부 특이사항</td><td colspan="4"></td></tr>');
		$(tr11).children("td:eq(1)").text(props.epntpartclr);
		$(tbody).append(tr11);

		var tr12 = $('<tr class="info close"><td class="title" colspan="2">시점부 특이사항</td><td colspan="4"></td></tr>');
		$(tr12).children("td:eq(1)").text(props.spntpartclr);
		$(tbody).append(tr12);

		var tr13 = $('<tr class="info close"><td class="title" colspan="6">상태정보</td></tr>');
		$(tbody).append(tr13);

		var tr14 = $('<tr class="info close"><td class="title">월류상태</td><td colspan="2"></td><td class="title">계류상황</td><td colspan="2"></td></tr>');
		$(tr14).children("td:eq(1)").text(props.ovrflwsttus);
		$(tr14).children("td:eq(3)").text(props.mntntrntsittn);
		$(tbody).append(tr14);

		var tr15 = $('<tr class="info close"><td class="title">곡류상태</td><td colspan="2"></td><td class="title">계류수</td><td colspan="2"></td></tr>');
		$(tr15).children("td:eq(1)").text(props.mdgfwsttus);
		$(tr15).children("td:eq(3)").text(props.mntntrntcnt);
		$(tbody).append(tr15);

		var tr16 = $('<tr class="info close"><td class="title">유목길이</td><td colspan="2"></td><td class="title">퇴적지</td><td colspan="2"></td></tr>');
		$(tr16).children("td:eq(1)").text(props.lwdlen + "m");
		$(tr16).children("td:eq(3)").text(props.sdtytopo);
		$(tbody).append(tr16);

		var tr17 = $('<tr class="info close"><td class="title">좌안붕괴지</td><td colspan="2"></td><td class="title">우안붕괴지</td><td colspan="2"></td></tr>');
		$(tr17).children("td:eq(1)").text(props.lslmplnd);
		$(tr17).children("td:eq(3)").text(props.rslmplnd);
		$(tbody).append(tr17);
	}

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

function fnCreateCnlSvycomptLinkPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">소재지</td><td colspan="5"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">조사자</td><td colspan="2"></td><td class="title">조사유형</td><td colspan="2"></td></tr>');
	$(tr2).children("td:eq(1)").text(props.svyuser);
	$(tr2).children("td:eq(3)").text(props.svytype);
	$(tbody).append(tr2);
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사ID</td><td colspan="2"></td><td class="title">조사일자</td><td colspan="2"></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svyid);
	$(tr3).children("td:eq(3)").text(props.svydt);
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title">지정일</td><td></td><td class="title">지정번호</td><td></td><td class="title">지정면적</td><td></td></tr>');
	$(tr4).children("td:eq(1)").text(props.appnyear);
	$(tr4).children("td:eq(3)").text(props.appnno);
	$(tr4).children("td:eq(5)").text(props.appnarea);
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">사업종류</td><td></td><td class="title">시공년도</td><td></td><td class="title">적용공법</td><td></td></tr>');
	$(tr5).children("td:eq(1)").text(props.biztype);
	$(tr5).children("td:eq(3)").text(props.cnstryear);
	$(tr5).children("td:eq(5)").text(props.applcegnermhd);
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">유역현황</td><td colspan="5"></td></tr>');
	$(tr6).children("td:eq(1)").text(props.dgrsttus);
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">피해이력</td><td colspan="2"></td><td class="title">특이사항</td><td colspan="2"></td></tr>');
	$(tr7).children("td:eq(1)").text(props.dmgehist);
	$(tr7).children("td:eq(3)").text(props.speclnote);
	$(tbody).append(tr7);

	var tr8 = $('<tr class="info close"><td class="title">근거</td><td colspan="2"></td><td class="title">세부사항</td><td colspan="2"></td></tr>');
	$(tr8).children("td:eq(1)").text(props.basis);
	$(tr8).children("td:eq(3)").text(props.detailmatter);
	$(tbody).append(tr8);

	if (props.svytype == "취약지역 해제조사(산사태)") {
		var tr9 = $('<tr class="info close"><td class="title" colspan="2">재해발생여부</td><td></td><td class="title" colspan="2">사면상태</td><td></td></tr>');
		$(tr9).children("td:eq(1)").text(props.cnlevl1);
		$(tr9).children("td:eq(3)").text(props.cnlevl2);
		$(tbody).append(tr9);

		var tr10 = $('<tr class="info close"><td class="title" colspan="2">안정해석 결과</td><td colspan="3"></td></tr>');
		$(tr10).children("td:eq(1)").text(props.cnlevl3);
		$(tbody).append(tr10);
	} else if (props.svytype == "취약지역 해제조사(토석류)") {
		var tr9 = $('<tr class="info close"><td class="title" colspan="2">재해발생여부</td><td></td><td class="title" colspan="2">계류상태</td><td></td></tr>');
		$(tr9).children("td:eq(1)").text(props.cnlevl1);
		$(tr9).children("td:eq(3)").text(props.cnlevl2);
		$(tbody).append(tr9);

		var tr10 = $('<tr class="info close"><td class="title" colspan="2">시뮬레이션 결과</td><td></td><td class="title" colspan="2">저감여부</td><td></td></tr>');
		$(tr10).children("td:eq(1)").text(props.cnlevl3);
		$(tr10).children("td:eq(3)").text(props.reducat);
		$(tbody).append(tr10);
	}

	var tr11 = $('<tr class="info close"><td class="title" colspan="3">최종 검토결과</td><td colspan="3"></td></tr>');
	var cnlYn = "";
	if (props.cnlYn == "가") {
		cnlYn = "해제 심의 대상지 적합";
	} else if (props.cnlYn == "부") {
		cnlYn = "해제 심의 대상지 부적합";
	}
	$(tr11).children("td:eq(1)").text(cnlYn);
	$(tbody).append(tr11);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

function fnCreateFrdSvycomptLinkPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="15%"/><col width=""/><col width="15%"/><col width=""/><col width="15%"/><col width=""/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">소재지</td><td colspan="3"></td><td class="title">조사일</td><td></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tr1).children("td:eq(3)").text(props.svydt);
	$(tbody).append(tr1);

	var tr2 = $('<tr><td class="title">임도종류</td><td></td><td class="title">노선종류</td><td></td><td class="title">조사구분</td><td></td></tr>');
	$(tr2).children("td:eq(1)").text(props.frdtype);// 임도종류
	$(tr2).children("td:eq(3)").text(props.routetype);// 노선종류
	$(tr2).children("td:eq(3)").text(props.frdrnk);// 조사종류
	$(tbody).append(tr2);

	var tr3 = $('<tr><td class="title">조사아이디</td><td colspan="2"></td><td class="title">조사자</td><td colspan="2"></td></tr>');
	$(tr3).children("td:eq(1)").text(props.svyid);// 조사아이디
	$(tr3).children("td:eq(3)").text(props.svyuser);// 조사자
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title">예정거리(km)</td><td colspan="2"></td><td class="title">임도연장(km)</td><td colspan="2"></td></tr>');
	$(tr4).children("td:eq(1)").text(props.schdst); // 예정거리(km)
	$(tr4).children("td:eq(3)").text(props.frdextns);// 임도연장(km)
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td class="title">시점위도</td><td colspan="2"></td><td class="title">시점경도</td><td colspan="2"></td></tr>');
	$(tr5).children("td:eq(1)").text(props.bpx);
	$(tr5).children("td:eq(3)").text(props.bpy);
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">종점위도</td><td colspan="2"></td><td class="title">종점경도</td><td colspan="2"></td></tr>');
	$(tr6).children("td:eq(1)").text(props.epx);
	$(tr6).children("td:eq(3)").text(props.epy);
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title" colspan="6">보호시설</td></tr>');
	$(tbody).append(tr7);

	var tr8 = $('<tr class="info close"><td class="title">보호시설</td><td></td><td class="title">전답</td><td></td><td class="title">접근성</td><td></td></tr>');
	$(tr8).children("td:eq(1)").text(props.safefct);
	$(tr8).children("td:eq(3)").text(props.field);
	$(tr8).children("td:eq(5)").text(props.acsbl);
	$(tbody).append(tr8);

	var tr9 = $('<tr class="info close"><td class="title" colspan="6">종단기울기</td></tr>');
	$(tbody).append(tr9);

	var tr10 = $('<tr class="info close"><td class="title">최소(%)</td><td></td><td class="title">최대(%)</td><td></td><td class="title">평균(%)</td><td></td></tr>');
	$(tr10).children("td:eq(1)").text(props.lonslopemin);
	$(tr10).children("td:eq(3)").text(props.lonslopemax);
	$(tr10).children("td:eq(5)").text(props.lonslopeavg);
	$(tbody).append(tr10);

	var tr11 = $('<tr class="info close"><td class="title" colspan="6">산지경사</td></tr>');
	$(tbody).append(tr11);

	var tr12 = $('<tr class="info close"><td class="title">최소(°)</td><td></td><td class="title">최대(°)</td><td></td><td class="title">평균(°)</td><td></td></tr>');
	$(tr12).children("td:eq(1)").text(props.mtslopemin);
	$(tr12).children("td:eq(3)").text(props.mtslopemax);
	$(tr12).children("td:eq(5)").text(props.mtslopeavg);
	$(tbody).append(tr12);

	var rocklist = props.rockexposlist != null ? JSON
			.parse(props.rockexposlist) : [];
	var rockSum = 0;

	$.each(rocklist,
			function(idx, val) {
				rockSum += parseFloat((val["암반노출"] == undefined
						|| val["암반노출"] == null ? "0" : val["암반노출"]));
			});

	var tr13 = $('<tr class="info close"><td class="title">암반노출길이</td><td></td><td class="title">조림지유무</td><td></td><td class="title">벌채지유무</td><td></td></tr>');
	$(tr13).children("td:eq(1)").text(rockSum + "m");
	$(tr13).children("td:eq(3)").text(props.afrstat);
	$(tr13).children("td:eq(5)").text(props.cuttingat);
	$(tbody).append(tr13);

	var stmilist = props.stmilist != null ? JSON.parse(props.stmilist) : [];
	var stmi = stmilist.length > 0 ? "유" : "무";

	var tr14 = $('<tr class="info close"><td class="title">석력지유무</td><td></td><td class="title">습지유무</td><td></td><td class="title">용출수유무</td><td></td></tr>');
	$(tr14).children("td:eq(1)").text(stmi);
	$(tr14).children("td:eq(3)").text(props.wetlandat);
	$(tr14).children("td:eq(5)").text(props.eltnwaterat);

	var tr15 = $('<tr class="info close"><td class="title">연약지반유무</td><td></td><td class="title">주요수종건수</td><td></td><td class="title">주요식생</td><td></td></tr>');
	$(tr15).children("td:eq(1)").text(props.sofrtgrndat);
	$(tr15).children("td:eq(3)").text(props.maintreekndcnt);
	$(tr15).children("td:eq(5)").text(props.mainvegcnt);

	var tr16 = $('<tr class="info close"><td class="title">상수원오염유무</td><td></td><td class="title">훼손우려지유무</td><td></td><td class="title">야생동물건수</td><td></td></tr>');
	$(tr16).children("td:eq(1)").text(props.wtrpltnat);
	$(tr16).children("td:eq(3)").text(props.dmgcncrnat);
	$(tr16).children("td:eq(5)").text(props.wildanmlcnt);

	var tr17 = $('<tr class="info close"><td class="title" colspan="6">산림재해</td></tr>');
	$(tbody).append(tr17);

	var frstdsstrlist = props.frstdsstrlist != null ? JSON
			.parse(props.frstdsstrlist) : [];
	var widefire = 0;
	var diseasenpest = 0;
	var etc = 0;
	$.each(frstdsstrlist, function(idx, val) {
		if (val["재해유형"] == "산불") {
			widefire++;
		} else if (val["재해유형"] == "병해충") {
			diseasenpest++;
		} else {
			etc++;
		}
	});

	var tr18 = $('<tr class="info close"><td class="title">산불(건)</td><td></td><td class="title">병해충(건)</td><td></td><td class="title">기타(건)</td><td></td></tr>');
	$(tr18).children("td:eq(1)").text(widefire);
	$(tr18).children("td:eq(3)").text(diseasenpest);
	$(tr18).children("td:eq(5)").text(etc);
	$(tbody).append(tr18);

	var tr19 = $('<tr class="info close"><td class="title">사방시설설치</td><td colspan="2"></td><td class="title">사방시설필요</td><td colspan="2"></td></tr>');
	$(tr19).children("td:eq(1)").text(props.ecnrfcltyinstlcnt);
	$(tr19).children("td:eq(3)").text(props.ecnrfcltyncstycnt);
	$(tbody).append(tr19);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

function fnCreateMseSvycomptLinkPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="15%"/><col width=""/><col width="15%"/><col width=""/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">소재지</td><td colspan="3"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tbody).append(tr1);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

function fnCreatePcsSvycomptLinkPopupContent(props) {
	var table = $('<table><thead><colgroup><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/><col width="16.6%"/></colgroup></thead></table>');
	var tbody = $('<tbody></tbody>');

	// 조사 주소
	var addr = props.sd + " " + props.sgg + " " + props.emd + " ";
	(props.ri.length > 0) ? addr += props.ri + " " + props.jibun : props.jibun;

	var tr1 = $('<tr><td class="title">소재지</td><td colspan="5"></td></tr>');
	$(tr1).children("td:eq(1)").text(addr);
	$(tr1).children("td:eq(3)").text(props.svyid);
	$(tbody).append(tr1);

	var tr1_1 = $('<tr><td class="title">조사유형</td><td colspan="2"></td><td class="title">조사ID</td><td colspan="2"></td></tr>');
	$(tr1_1).children("td:eq(1)").text(props.svytype);
	$(tr1_1).children("td:eq(3)").text(props.svyid);
	$(tbody).append(tr1_1);

	var tr2 = $('<tr><td class="title">점검일시</td><td colspan="2"></td><td class="title">점검자</td><td colspan="2"></td>');
	$(tr2).children("td:eq(1)").text(props.svydt);
	$(tr2).children("td:eq(3)").text(props.svyuser);
	$(tbody).append(tr2);

	var tr3 = $('<tr class="info close"><td class="title">사방댐유형</td><td colspan="2"></td><td class="title">사방댐형식</td><td colspan="2"></td></tr>');
	$(tr3).children("td:eq(1)").text(props.ecnrknd);
	$(tr3).children("td:eq(3)").text(props.svyform);
	$(tbody).append(tr3);

	var tr4 = $('<tr class="info close"><td class="title">사방댐관리번호</td><td colspan="2"></td><td class="title">국가지점번호</td><td colspan="2"></td></tr>');
	$(tr4).children("td:eq(1)").text(props.ecnrrnum);
	$(tr4).children("td:eq(3)").text(props.nationspotnum);
	$(tbody).append(tr4);

	var tr5 = $('<tr class="info close"><td colspan="6" class="title">시설제원</td></tr>');
	$(tbody).append(tr5);

	var tr6 = $('<tr class="info close"><td class="title">시설년도</td><td></td><td class="title">상장(m)</td><td></td><td class="title">하장(m)</td><td></td></tr>');
	$(tr6).children("td:eq(1)").text(props.fcltyear);
	$(tr6).children("td:eq(3)").text(props.fcltuprhg);
	$(tr6).children("td:eq(5)").text(props.fcltlwrhg);
	$(tbody).append(tr6);

	var tr7 = $('<tr class="info close"><td class="title">높이(m)<br>전고</td><td colspan="2"></td><td class="title">높이(m) 유효고</td><td colspan="2"></td></tr>');
	$(tr7).children("td:eq(1)").text(props.tthghjdgval);
	$(tr7).children("td:eq(3)").text(props.hsfcltstkhg);
	$(tbody).append(tr7);

	var tr8 = $('<tr class="info close"><td class="title">천단폭</td><td colspan="2"></td><td class="title">사공비(천원)</td><td colspan="2"></td></tr>');
	$(tr8).children("td:eq(1)").text(props.wotdjdgval);
	$(tr8).children("td:eq(3)").text(props.cnstrcost);
	$(tbody).append(tr8);

	var tr9 = $('<tr class="info close"><td colspan="6" class="title">비파괴검사</td></tr>');
	$(tbody).append(tr9);

	var tr8 = $('<tr class="info close"><td colspan="2" class="title">콘크리트압축강도(슈미트해머)</td><td colspan="3"></td><td></td></tr>');
	$(tr8).children("td:eq(1)").text(props.cncrtcmprsstrn);

	if (props.cncrtcmprsstrn == '설계기준강도 21MPa 이상') {
		$(tr8).children("td:eq(2)").text("만족");
	} else if (props.cncrtcmprsstrn == '설계기준강도 21MPa 미만') {
		$(tr8).children("td:eq(2)").text("미달");
	} else {
		$(tr8).children("td:eq(2)").text("");
	}

	$(tbody).append(tr8);

	var tr9 = $('<tr class="info close"><td colspan="2" class="title">콘크리트압축강도(초음파탐상)</td><td colspan="3"></td><td></td></tr>');
	$(tr9).children("td:eq(1)").text(props.cncrtcrkdpt);

	if (props.cncrtcrkdpt == '부재 두께의 50% 미만') {
		$(tr9).children("td:eq(2)").text("만족");
	} else if (props.cncrtcrkdpt == '부재 두께의 50% 이상') {
		$(tr9).children("td:eq(2)").text("미달");
	} else {
		$(tr9).children("td:eq(2)").text("");
	}
	$(tbody).append(tr9);

	var tr10 = $('<tr class="info close"><td colspan="6" class="title">사방댐 준설 평가표</td></tr>');
	$(tbody).append(tr10);

	var tr11 = $('<tr class="info close"><td colspan="3" class="title">총 점(인자별 준설기준 값의 합)</td><td colspan="3" class="title">사방댐 준설여부 판정 (총점기준)</td></tr>');
	$(tbody).append(tr11);

	var tr12 = $('<tr class="info close"><td colspan="3""></td><td colspan="3"></td></tr>');
	$(tr12).children("td:eq(0)").text(props.totalscore);
	$(tr12).children("td:eq(1)").text(props.drdgnatjdg);
	$(tbody).append(tr12);

	var tr13 = $('<tr class="info close"><td colspan="6" class="title">종합결과</td></tr>');
	$(tbody).append(tr13);

	var tr14 = $('<tr class="info close"><td colspan="3" class="title">종합점수</td><td colspan="3" class="title">최종 점검결과 등급</td></tr>');
	$(tbody).append(tr14);

	// 최종점수 계산
	var odd = props.orginldamdmgscore;
	var odc = props.orginldamcrkscore;
	var odb = props.orginldambasicscourscore;
	var odp = props.orginldamplngscore;
	var od4 = odc <= odp ? odp : odc;
	var odc = props.orginldamcncrtscore;
	var odw = props.orginldamwtgatescore;
	var sd = props.sidewalldmgscore;
	var sc = props.sidewallcrkscore;
	var sb = props.sidewallbasicscourscore;
	var dd = props.dwnsptdmgscore;
	var db = props.dwnsptbasicscourscore;
	var dc = props.dwnsptcrkscore;

	var lastScore = 0;
	lastScore += Number(odd);
	lastScore += Number(odc);
	lastScore += Number(odb);
	lastScore += Number(od4);
	lastScore += Number(odw);
	lastScore += Number(sd);
	lastScore += Number(sc);
	lastScore += Number(sb);
	lastScore += Number(dd);
	lastScore += Number(db);
	lastScore += Number(dc);

	var totalRank;
	if (lastScore < 30) {
		totalRank = "A(유지 또는 필요시 보수)";
	} else if (lastScore >= 30 && lastScore < 70) {
		totalRank = "B(보완ㆍ보수)";
	} else {
		totalRank = "";
	}
	var tr15 = $('<tr class="info close"><td colspan="3""></td><td colspan="3"></td></tr>');
	$(tr15).children("td:eq(0)").text(lastScore);
	$(tr15).children("td:eq(1)").text(totalRank);
	$(tbody).append(tr15);

	$(table).append(tbody);

	return $(table).prop('outerHTML');
}

/**
 * @author ipodo
 * @name fnOpenDetailInfo
 * @param {String}
 *            id - target element
 * @returns
 * @description 오버레이 정보 상세보기
 */
function fnOpenDetailInfo(e) {
	var id = e.target ? $(e.target).closest('.ol-popup').attr('id') : e;

	var popup = SM.map.getOverlayById(id);

	// 돌아가기 활성화
	if ($('#' + id + ' tr.info').hasClass('close')) {
		$(e.target).removeClass('search-btn');
		$(e.target).addClass('back-btn');
		$(e.target).text('돌아가기');
		$('#' + id + ' tr.info').removeClass('close');
	}
	// 상세보기 활성화
	else {
		$(e.target).removeClass('back-btn');
		$(e.target).addClass('search-btn');
		$(e.target).text('상세보기');
		$('#' + id + ' tr.info').addClass('close');
	}

	popup.panIntoView({
		duration : 500
	});

}

/**
 * @author ipodo
 * @name fnOpenPopup
 * @param {Event}
 *            e - singleclick event
 * @returns
 * @description 기초조사완료지 지도 바로가기 클릭 이벤트
 */
function fnSelectFeature(e) {
	var id = e.target.getProperties()['id'];
	var popupId = 'popup-' + id;

	// 선택된 도형이 있을 경우
	if (e.selected.length > 0) {
		var properties = e.selected[0].getProperties(); // 프로퍼티
		var coordinates = e.selected[0].getGeometry().getCoordinates(); // 좌표
		var content;
		var style = null;
		
		
		if(id.indexOf("bsc") == 0){
			content = fnCreateBscSvycomptLinkPopupContent(properties);
		}else if(id.indexOf("fckApr") == 0){
			content = fnCreateAprSvycomptLinkPopupContent(properties);
		}else if(id.indexOf("lcp") == 0){
			content = fnCreateLcpSvycomptLinkPopupContent(properties);
			style = new ol.style.Style({
				stroke : new ol.style.Stroke({
					color : '#fff',
					width : 2
				}),
				fill : new ol.style.Fill({
					color : 'rgba(254,46,100,0.7)'
				})
			});
		}else if(id.indexOf("wka") == 0){
			content = fnCreateWkaSvycomptLinkPopupContent(properties);
		}else if(id.indexOf("cnl") == 0){
			content = fnCreateCnlSvycomptLinkPopupContent(properties);
		}else if(id.indexOf("wka") == 0){
			content = fnCreateWkaSvycomptLinkPopupContent(properties);
		}else if(id.indexOf("frd") == 0){
			content = fnCreateWkaSvycomptLinkPopupContent(properties);
			
			var color = properties.routetype == '수정노선' ? '#ffff00' : '#0000ff';
			style = new ol.style.Style({
				stroke : new ol.style.Stroke({
					color : color,
					width : 4
				})
			});
		}else if(id.indexOf("mse") == 0){
			content = fnCreateMseSvycomptLinkPopupContent(properties);
		}else if(id.indexOf("pcs") == 0){
			content = fnCreatePcsSvycomptLinkPopupContent(properties);
		}
		
	
		if(e.target.get("id").indexOf('frdSvycomptLink') > -1 || e.target.get("id").indexOf('lcpSvycomptLink') > -1) {
			e.selected[0].setStyle(style);
		}
		
		var options = {
			type : 'search',
			text : '상세보기',
			callback : fnOpenDetailInfo
		};
		// 오버레이 열기
		fnOpenOverlay(popupId, coordinates, content, options);
	}
	// 선택된 도형이 없을 경우
	else {
		// 오버레이 닫기
		fnCloseOverlay(popupId);
	}
}

/**
 * @author ipodo
 * @name fnOpenOverlay
 * @param {String}
 *            id - overlay id
 * @param {Array}
 *            coord - coordinates
 * @param {Element}
 *            content - html content
 * @param {Object}
 *            options - option button
 * @param {JSON}
 *            data - data bind
 * @returns
 * @description 오버레이 열기
 * @modified 2023.08.24(taho)
 */
function fnOpenOverlay(id, coord, content, options) {

	var popup = SM.map.getOverlayById(id);

	if (!popup) {
		// 팝업 엘리먼트 추가
		options.data ? SM.createOverlay(id, options.type, options.text,
				options.callback, options.data) : SM.createOverlay(id,
				options.type, options.text, options.callback);
		popup = SM.map.getOverlayById(id);
	}

	popup.setPosition(coord);

	$('#' + id + ' > div.ol-popup-content').empty();
	$('#' + id + ' > div.ol-popup-content').append(content);
	$('#' + id).show();

}

/**
 * @author ipodo
 * @name fnCloseOverlay
 * @param {String}
 *            id - target id
 * @returns
 * @description 오버레이 닫기
 */
function fnCloseOverlay(e) {

	var id = e.target ? $(e.target).closest('.ol-popup').attr('id') : e;
	var layerId = id.replace('popup-', '');
	var layer = SM.map.getLayer(layerId);
	var popup = SM.map.getOverlayById(id);

	if (popup) {
		SM.clearSelectFeature();
		popup.setPosition(undefined);
		$('#' + id).hide();
	}
	
	if (layer && layer instanceof ol.layer.Vector){
		layer.getSource().getFeatures()[0].setStyle(layer.getSource().getFeatures()[0].getStyle());
	}
}

function fncCheckParam() {
	var featureSet = $("input[name=featureSet]").val();// '<c:out
														// value="${featureSet}"/>';
	var goToLayer = $("input[name=goToLayer]").val();// '<c:out
														// value="${goToLayer}"/>';
	featureSet = featureSet.replace(/\&#034;/gi, "\"");

	if (featureSet != undefined && featureSet.length != 0) {
		fncRequestFeature(JSON.parse(featureSet));
	}

	if (goToLayer != undefined && goToLayer.length != 0) {
		fncGoToLayer(goToLayer);
	}

	// var gid = $("input[name=gid]").val();
	//	
	// if(gid != "" && gid != null && gid != undefined){
	//		
	// var param =
	// {IDs:[gid],datasetNames:[CONFIG.ALIAS.concat(":tf_feis_bsc_svycompt")]}
	// SM.getFeaturesByIDs(CONFIG.URLS.SUPERMAP.DATA,param);
	// }
}

/*
 * 지도이미지 생성
 */
function saveMapImage() {
	const mapCanvas = document.createElement('canvas');
	const size = SM.map.getSize();
	mapCanvas.width = size[0];
	mapCanvas.height = size[1];
	const mapContext = mapCanvas.getContext('2d');
	Array.prototype.forEach.call(document.querySelectorAll('.ol-layer canvas'),
			function(canvas) {
				if (canvas.width > 0) {
					const opacity = canvas.parentNode.style.opacity;
					mapContext.globalAlpha = opacity === '' ? 1
							: Number(opacity);
					const transform = canvas.style.transform;
					const matrix = transform.match(/^matrix\(([^\(]*)\)$/)[1]
							.split(',').map(Number);

					CanvasRenderingContext2D.prototype.setTransform.apply(
							mapContext, matrix);
					mapContext.drawImage(canvas, 0, 0);
				}
			});
	return mapCanvas;
}

/*
 * 지도저장
 */
function fncExportMap() {
	SM.map.once('rendercomplete', function() {
		var mapCanvas = saveMapImage();

		if (navigator.msSaveBlob) {
			navigator.msSaveBlob(mapCanvas.msToBlob(), 'map.png');
		} else {
			var link = document.createElement('a');
			link.href = mapCanvas.toDataURL();
			link.download = "map.png";
			link.click();
		}
	});
	SM.map.renderSync();
}

/*
 * 지도인쇄 다이얼로그
 */
function fncPrintMapDialog() {
	SM.map
			.once(
					'rendercomplete',
					function() {
						var mapCanvas = saveMapImage();
						var url = mapCanvas.toDataURL();

						var printDiv = $("<div>").attr("id", "printDiv");
						var printTitle = $("<input>").attr("type", "text")
								.attr("id", "printTitle").attr("placeholder",
										"제목을 입력하세요.");
						printTitle.css("width", "100%").css("margin-bottom",
								"3px");

						var printImg = $("<img>").attr("src", url);
						printImg.css("width", "100%").css("border",
								"1px solid #cccccc");

						var printTextarea = $("<textarea>").attr("id",
								"printTextarea").css("width",
								"calc(100% - 1px)").css("height", "100px").css(
								"margin-top", "3px");
						var printBtn = $("<button>").attr("type", "button")
								.attr("id", "printBtn").attr("class",
										"ui-button ui-widget ui-corner-all")
								.css("margin-top", "3px").css("width",
										"calc(100% - 2px)").text("인쇄");

						printDiv.append(printTitle).append(printImg).append(
								printTextarea).append(printBtn);

						printBtn
								.on(
										"click",
										function() {

											var title = $("#printTitle").val();

											if (title.length > 0) {
												var h3 = $("<h3>").css("width",
														"100%").css(
														"margin-bottom", "3px")
														.css("text-align",
																"center").text(
																title);
												$("#printDiv").prepend(h3);
											}

											$("#printTitle").remove();

											var textarea = $("#printTextarea")
													.val();

											if (textarea.length > 0) {
												var textDiv = $("<div>")
														.css("margin-top",
																"3px")
														.css("border",
																"1px solid #cccccc");
												textDiv
														.append(textarea
																.replace(
																		/\n/gi,
																		'<br>'));
												$("#printTextarea").before(
														textDiv);
											}

											$("#printTextarea").remove();
											$("#printBtn").remove();

											var win = window.open();
											self.focus();
											win.document.open();
											win.document
													.write('<html><head><title></title><style>');
											win.document
													.write('body, td {font-falmily: Verdana; font-size: 10pt;}');
											win.document
													.write('</style></head><body>');
											win.document.write($("#printDiv")
													.html());
											win.document
													.write('</body></html>');
											win.document.close();
											win.print();
											win.close();
											$("#printDiv").empty().dialog(
													"destroy");
										});
						// var printTemplate = "<div id=\"printDiv\">";
						// printTemplate += "<input type=\"text\"
						// id=\"printTitle\" style=\"width: 100%;margin-bottom:
						// 3px;\" placeholder=\"제목을 입력하세요.\">";
						// printTemplate += "<img style=\"width:100%;\"
						// src=\""+url+"\">";
						// printTemplate += "<button type=\"button\"
						// id=\"printBtn\">인쇄</button>";
						// printTemplate += "</div>";
						//		
						$(printDiv).dialog({
							modal : true,
							close : function(e) {
								$(this).empty();
								$(this).dialog("destroy");
							},
							height : 618,
							width : 800,
							title : "지도인쇄"
						});
					});
	SM.map.renderSync();
}

/**
 * @author ipodo
 * @name fnSelectMapBrowser
 * @param {Event}
 *            e - click event
 * @returns
 * @description 지도 싱글클릭 이벤트
 */
function fnSingleclickMap(e) {
	if (e.dragging) {
		return;
	}

	if (e) {
		var resolution = (e.target.getView().getResolution());
		var projection = (e.target.getView().getProjection());
		var coordinate = ol.proj.transform(e.coordinate, 'EPSG:3857',
				'EPSG:4326');
		var extent = [ 14147570.357082546, 4427592.58336714, 14230512.26164389,
				4467847.633381992 ];
		var pixel = e.pixel;

		var geometry = new ol.geom.Polygon.circular(coordinate, 10);
		var datasetNames = [];

		SM.map
				.getLayers()
				.getArray()
				.forEach(
						function(d) {
							if (d instanceof ol.layer.Tile
									&& d.get("id").indexOf("bsc") == 0) {
								datasetNames
										.push(d.get("id").indexOf("Svycompt") > -1 ? CONFIG.ALIAS
												.concat(':tf_feis_bsc_svycompt')
												: CONFIG.ALIAS
														.concat(':tf_feis_bsc_fieldinfo'));
							}
						})

		var param = {
			spatialQueryMode : SuperMap.SpatialQueryMode.INTERSECT,
			datasetNames : datasetNames,
			geometry : geometry.transform('EPSG:4326', 'EPSG:5186')
		};

		SM.getFeatureByGeometry('', CONFIG.URLS.SUPERMAP.DATA, param);
	}
}

/*
 * 통합검색리스트 탭 이벤트
 */
function wholeSearchClick(idx) {
	$(".map_tab > a").eq(idx).trigger("click");
}

/*
 * 통합검색 수 설정
 */
function wholeSearchCnt(cnt) {

	cnt = cnt || 0;
	var all = $("#tAll > .txtsearch > span.txtblue").text();

	var total = parseInt(all.replace(/[^0-9]/g, "")) + parseInt(cnt);
	$("#tAll > .txtsearch > span.txtblue").text(
			(isNaN(total) ? "0" : numWithComma(total)));
}

/*
 * 통합검색
 */
function wholeSearch(type, page) {
	var param = {
		key : CONFIG.KEYS.vworld,
		service : "search",
		version : "2.0",
		request : "search",
		query : wholeSearchQuery,
		type : type,
		crs : "EPSG:5186",
		size : 5,
		page : parseInt(page)
	};

	if (/parcel|road/.test(type)) {
		param.type = "address";
		param.category = type;
	}

	pagingDataList(CONFIG.URLS.VWORLD.search, param, type);
}

function pagingDataList(url, param, type) {
	$
			.ajax({
				url : url,
				data : param,
				type : "get",
				dataType : "jsonp",
				success : function(json) {
					var total = null;
					$("#".concat(type, "List tbody")).empty();
					if (json.response.status == "OK") {
						if (json.response.result.type == 'place') {

							$("#allSchCntLst tbody tr:eq(0) td span.txtblue")
									.text(
											numWithComma(json.response.record.total));
							$("#tNmfpc > .txtsearch > span.txtblue").text(
									numWithComma(json.response.record.total));
							$
									.each(
											json.response.result.items,
											function(idx, value) {
												$(
														"<tr><td rowspan='2'>"
																+ value.title
																+ "</td><td><a href='javascript:SM.wholeMoveToPoint(\""
																+ value.point.x
																+ "\", \""
																+ value.point.y
																+ "\")'>"
																+ value.address.road
																+ "</td></tr><tr><td><a href='javascript:SM.wholeMoveToPoint(\""
																+ value.point.x
																+ "\", \""
																+ value.point.y
																+ "\", \""
																+ value.address.parcel
																+ "\")'>"
																+ value.address.parcel
																+ "</a></td></tr>")
														.appendTo(
																$("#"
																		.concat(
																				type,
																				"List tbody")));
											});
							total = json.response.record.total;
						} else {
							$
									.each(
											json.response.result.items,
											function(idx, value) {
												$(
														"<tr><td>"
																+ (value.address.zipcode.length == 0 ? "-"
																		: value.address.zipcode)
																+ "</td>"
																+ "<td><a href='javascript:SM.wholeMoveToPoint(\""
																+ value.point.x
																+ "\", \""
																+ value.point.y
																+ "\", \""
																+ (value.address.category == "road" ? ''
																		: value.address.parcel)
																+ "\")'>"
																+ (value.address.category == "road" ? value.address.road
																		: value.address.parcel)
																+ "</a></td></tr>")
														.appendTo(
																$("#"
																		.concat(
																				type,
																				"List tbody")));
											});

							if (type == "road") {
								$(
										"#allSchCntLst tbody tr:eq(2) td span.txtblue")
										.text(
												numWithComma(json.response.record.total));
								$("#tRn > .txtsearch > span.txtblue")
										.text(
												numWithComma(json.response.record.total));
							} else {
								$(
										"#allSchCntLst tbody tr:eq(1) td span.txtblue")
										.text(
												numWithComma(json.response.record.total));
								$("#tLnm > .txtsearch > span.txtblue")
										.text(
												numWithComma(json.response.record.total));
							}
							total = json.response.record.total;
						}
						wholeSearchCnt(total);
						$("#".concat(type, "List"))
								.next()
								.html(
										total > 0 ? createPaging(
												total,
												param.size,
												5,
												param.page,
												(param.type == "address" ? param.category
														: param.type))
												: "");
					}
				},
				error : function(error) {
					console.log(error);
					alert("검색 중 오류가 발생하였습니다.\n관리자에게 문의하세요.");
				}
			});
}

function createPaging(totalCnt, dataSize, pageSize, pageNo, token) {
	totalCnt = parseInt(totalCnt);// 전체레코드수
	dataSize = parseInt(dataSize); // 페이지당 보여줄 데이타수
	pageSize = parseInt(pageSize); // 페이지 그룹 범위 1 2 3 5 6 7 8 9 10
	pageNo = parseInt(pageNo); // 현재페이지

	var html = new Array();
	if (!totalCnt) {
		return resetPagination();
	}

	// 페이지 카운트 총블럭수
	var pageCnt = totalCnt % dataSize;
	if (pageCnt == 0) {
		pageCnt = parseInt(totalCnt / dataSize);
	} else {
		pageCnt = parseInt(totalCnt / dataSize) + 1;
	}

	// 페이지 그룹 번호
	var pRCnt = parseInt(pageNo / pageSize);
	if (pageNo % pageSize == 0) {
		pRCnt = parseInt(pageNo / pageSize) - 1;
	}

	// 첫번째 번호
	if (pRCnt != 0) {
		html
				.push("<button id=\"StartPagi\" type=\"button\" onclick=\"javascript:wholeSearch('"
						.concat(token,
								"',1)\"><img src=\"/images/sub/first_arrow.png\" alt=\"처음\"></button>"));
	}

	// 이전 번호
	if (pageNo > pageSize) {
		var s2;
		if (pageNo % pageSize == 0) {
			s2 = pageNo - pageSize;
		} else {
			s2 = pageNo - pageNo % pageSize;
		}

		html
				.push("<button id=\"prevPagi\" type=\"button\" onclick=\"javascript:wholeSearch('"
						.concat(token, "',")
						.concat(s2,
								")\"><img src=\"/images/sub/before.png\" alt=\"이전\"></button>"));
	} else {
		// html.push('<li><a href="javascript:void(0)"
		// class="disabled">&lt;</a></li>');
	}

	// 페이지 리스트
	for (var index = pRCnt * pageSize + 1; index < (pRCnt + 1) * pageSize + 1; index++) {
		if (index == pageNo) {
			html.push("<a id=\"nowpage\" href=\"#none\" class=\"on\">".concat(
					index, "</a>"));
		} else {
			html.push("<a class=\"page\" href=\"javascript:wholeSearch('"
					.concat(token, "',").concat(index, ")\">").concat(index,
							"</a>"));
		}

		if (index == pageCnt) {
			break;
		}
	}

	// 다음 번호
	if (pageCnt > (pRCnt + 1) * pageSize) {
		html
				.push("<button id=\"nextPagi\" type=\"button\" onclick=\"javascript:wholeSearch('"
						.concat(token, "',")
						.concat(((pRCnt + 1) * pageSize + 1),
								")\"><img src=\"/images/sub/next.png\" alt=\"다음\"></button>"));
	} else {
		// html.push('<li><a href="javascript:void(0)"
		// class="disabled">&gt;</a></li>');
	}

	// 마지막 번호
	if (pageCnt > (pRCnt + 1) * pageSize) {
		html
				.push("<button id=\"EndPagi\" type=\"button\" onclick=\"javascript:wholeSearch('"
						.concat(token, "',")
						.concat(pageCnt,
								")\"><img src=\"/images/sub/last_arrow.png\" alt=\"마지막\"></button>"));
	} else {
		// html.push('<li><a href="javascript:void(0)"
		// class="disabled">&gt;&gt;</a></li>');
	}

	return html.join("");
}

function resetPagination() {
	/*
	 * var html = '<li><a href="#">&lt;&lt;</a></li>' + '<li><a
	 * href="#">&lt;</a></li>' + '<li><a class="active" href="#">1</a></li>' + '<li><a
	 * href="#">&gt;</a></li>' + '<li><a href="#">&gt;&gt;</a></li>';
	 */

	/* 2023.08.21 수정 */
	var html = '<button type="button"><img src="/images/sub/first_arrow.png" alt="처음"></button>'
			+ '<button type="button"><img src="/images/sub/before.png" alt="이전"></button>'
			+ '<a class="on" href="#">1</a>'
			+ '<button type="button"><img src="/images/sub/next.png" alt="다음"></button>'
			+ '<button type="button"><img src="/images/sub/last_arrow.png" alt="마지막"></button>';

	return html;
}

/*
 * 3자리수 콤마 추가
 */
function numWithComma(num) {
	var str = num + "";
	return str.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * 토지이용계획열람 팝업 생성
 * 
 * @returns
 * @Description
 */
function fncLadUsePlanPopup(event) {
	var type = $(this).data('type');

	var props = $(this).data('props');

	var pnuCode = props.pnu;
	var sd = props.sd;
	var sgg = props.sgg;
	var emd = props.emd;
	var ri = props.ri;
	var jibun = props.jibun;

	var landGbn = 1;

	if (sd == "") {
		alert("시도 입력이 되지않았습니다.");
		return false;
	} else if (sgg == "") {
		alert("시군구 입력이 되지않았습니다.");
		return false;
	} else if (emd == "") {
		alert("읍면동 입력이 되지않았습니다.");
		return false;
	}
	/*
	 * else if(ri == ""){ alert("리 입력이 되지않았습니다."); return false; }
	 */
	else if (jibun == "") {
		alert("지번 입력이 되지않았습니다.");
		return false;
	}

	if (jibun.includes("산"))
		landGbn = 2;
	jibun = jibun.replaceAll(/[^0-9]/g, '');
	var pageTitle = null;
	var url = null;

	if (type == "eum") {
		pageTitle = "토지이용계획열람";
		url = "http://www.eum.go.kr/web/ar/lu/luLandDet.jsp?mode=search&selGbn=umd&isNoScr=script&pnu="
				+ pnuCode;
	} else {
		pageTitle = "씨리얼";
		url = "https://seereal.lh.or.kr/main.do?menuCd=goLotdetailInfo&pnu="
				+ pnuCode;
	}

	window.open(url, pageTitle, 'width=750px,height=1100px,scrollbars=yes');
}
