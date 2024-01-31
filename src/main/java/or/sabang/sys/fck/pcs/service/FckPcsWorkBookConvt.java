package or.sabang.sys.fck.pcs.service;

import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import org.json.JSONArray;
import org.json.JSONObject;

import egovframework.com.utl.fcc.service.EgovNumberUtil;
import or.sabang.utl.ExcelUtils;

/**
 * 정밀점검 엑셀 변환
 * @author DEVWORK
 *
 */
public class FckPcsWorkBookConvt {

	
	/**
	 * 정밀점검 조사정보 XSSF 포맷 처리용
	 * @author DEVWORK
	 * @param mType
	 * @param row
	 * @param vo
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public static FckPcsFieldBookItemVO convertXSSFCellValues(String mType,XSSFRow row,FckPcsFieldBookItemVO vo) throws Exception {

		JSONArray attr = new JSONArray();
		JSONObject attrItem = null;
		JSONObject memo = new JSONObject();

		Calendar calendar = Calendar.getInstance();
		
		XSSFCell cell = null;
		
		String ecnrType = ""; 
		
		if(mType.equals("사방댐")) {
			cell = row.getCell(48); // 전:조사유형, 후:종류
			if(cell != null) {
			//	memo.put("종류", ExcelUtils.getCellValue(cell));
				if(ExcelUtils.getCellValue(cell).contains("강재")) {
					ecnrType = "강재 ";
				}else if(ExcelUtils.getCellValue(cell).contains("콘크리트")) {
					ecnrType = "콘크리트 ";
				}else if(ExcelUtils.getCellValue(cell).contains("석재")) {
					ecnrType = "석재 ";
				}
			}
		}
		
		memo.put("조사유형", ecnrType.concat(mType).concat(" 정밀점검"));

		double latDd = 0;
		double lonDd = 0;


		cell = row.getCell(0); // 일련번호

		if(cell != null) {
			String fid = ExcelUtils.getCellValue(cell);

			if(fid.equals("")) { return vo; }

			memo.put("일련번호", fid);

			vo.setFID(Integer.parseInt(fid));
		} else { return vo; }

		cell = row.getCell(1);

		if(cell != null) {
			String svyId = ExcelUtils.getCellValue(cell);
    		
			if(svyId.equals("")) { return vo; }
    		
			memo.put("조사ID", svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    		
    		vo.setKEYWORD(ExcelUtils.getCellValue(cell));
    		vo.setLABEL(ExcelUtils.getCellValue(cell));
		} else { return vo; }

		attrItem = new JSONObject();
		attrItem.put("VALUE", ecnrType.concat(mType).concat(" 정밀점검"));
		attrItem.put("NAME","조사유형");
		attr.put(attrItem);
    	
		attrItem = new JSONObject();
		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
		attrItem.put("NAME","조사연도");
		attr.put(attrItem);	

		if(mType.equals("사방댐")) {

			cell = row.getCell(2);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(3);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(5);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(6);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(8);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(10); // 사방댐_형식
			if(cell != null) { memo.put("사방댐_형식", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(11); // 상장
			if(cell != null) { memo.put("상장(m)", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(12); // 하장
			if(cell != null) { memo.put("하장(m)", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13); // 전:유효, 후:높이(m) 유효고
			if(cell != null) { memo.put("높이(m) 유효고", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(14); // 점검일시
			if(cell != null) { memo.put("점검일시", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(15); // 전:조사자, 후:점검자
			if(cell != null) { memo.put("점검자", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(16); // 사방댐관리번호
			if(cell != null) {
				memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(17); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}

			cell = row.getCell(18); // 위도
			if(cell != null) { memo.put("위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 위분
			if(cell != null) { memo.put("위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 위초
			if(cell != null) { memo.put("위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 경도
			if(cell != null) { memo.put("경도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 경분
			if(cell != null) { memo.put("경분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 경초
			if(cell != null) { memo.put("경초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 본댐측정
			if(cell != null) { memo.put("본댐 측정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 측벽측정
			if(cell != null) { memo.put("측벽 측정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 물받이측정
			if(cell != null) { memo.put("물받이 측정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 본댐
			if(cell != null) { memo.put("본댐", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 측벽
			if(cell != null) { memo.put("측벽", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 물받이
			if(cell != null) { memo.put("물받이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 수문
			if(cell != null) { memo.put("수문", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(31); // 식생
			if(cell != null) { memo.put("식생", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(32); // 안전시설
			if(cell != null) { memo.put("안전시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(33); // 접근도로
			if(cell != null) { memo.put("접근도로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(34); // 기타
			if(cell != null) { memo.put("기타", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(35); // 저사상태
			if(cell != null) { memo.put("저사상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(36); // 저사량
			if(cell != null) { memo.put("저사량", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(37); // 점검결과
			if(cell != null) { memo.put("점검결과", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(38); // 조치사항
			if(cell != null) { memo.put("조치사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(39); // 지정해제
			if(cell != null) { memo.put("지정해제", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(40); // 조사자의견1
			if(cell != null) { memo.put("조사자의견1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(41); // 조사자의견2
			if(cell != null) { memo.put("조사자의견2", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(42); // 조사자의견3
			if(cell != null) { memo.put("조사자의견3", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(43); // 조사자의견4
			if(cell != null) { memo.put("조사자의견4", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(44); // 조사자의견5
			if(cell != null) { memo.put("조사자의견5", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(45); // 주요시설_특이사항
			if(cell != null) { memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(46); // 부대시설_특이사항
			if(cell != null) { memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(47); // 기타_특이사항
			if(cell != null) { memo.put("기타_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(48); // 종류
			if(cell != null) { memo.put("종류", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(49); // 전:전고, 후:높이(m) 전고
			if(cell != null) { memo.put("높이(m) 전고", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(50); // 천단폭
			if(cell != null) { memo.put("천단폭", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(51); // 전:시공비, 후:시공비(천원)
			if(cell != null) { 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell)); 
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}


			cell = row.getCell(52); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(53); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(54); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(55); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(56); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(57); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(58); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(59); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(60); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(61); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(62); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(63); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(64); // 전:측벽길이, 후:측벽 길이
			if(cell != null) {  memo.put("측벽 길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(65); // 전:측벽높이, 후:측벽 높이
			if(cell != null) {  memo.put("측벽 높이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(66); // 전:물받이가로, 후:물받이 가로
			if(cell != null) {  memo.put("물받이 가로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(67); // 전:물받이세로, 후:물받이 세로
			if(cell != null) {  memo.put("물받이 세로", ExcelUtils.getCellValue(cell)); }

			if(EgovNumberUtil.getNumberValidCheck(memo.get("위도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경초").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도").toString()) + (Double.parseDouble(memo.get("위분").toString())/60) + (Double.parseDouble(memo.get("위초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도").toString()) + (Double.parseDouble(memo.get("경분").toString())/60) + (Double.parseDouble(memo.get("경초").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("위도").toString()+"° "+memo.get("위분").toString()+"'' "+memo.get("위초").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("경도").toString()+"° "+memo.get("경분").toString()+"'' "+memo.get("경초").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}			

			cell = row.getCell(9); // 사방댐_종류
			if(cell != null) {
				memo.put("사방댐_종류", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐_종류");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐_종류");
				attr.put(attrItem);
			}
		}
		
		if(mType.equals("계류보전")) {
			cell = row.getCell(2);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(3);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(5);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(6);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(8);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(9); // 지정면적
			if(cell != null) { memo.put("지정면적", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(10); // 점검일시
			if(cell != null) { memo.put("점검일시", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(11); // 점검자
			if(cell != null) { memo.put("점검자", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(12); // 시점위도
			if(cell != null) { memo.put("시점위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13); // 시점위분
			if(cell != null) { memo.put("시점위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(14); // 시점위초
			if(cell != null) { memo.put("시점위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(15); // 시점경도
			if(cell != null) { memo.put("시점경도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(16); // 시점경분
			if(cell != null) { memo.put("시점경분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(17); // 시점경초
			if(cell != null) { memo.put("시점경초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(18); // 종점위도
			if(cell != null) { memo.put("종점위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 종점위분
			if(cell != null) { memo.put("종점위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 종점위초
			if(cell != null) { memo.put("종점위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 종점경도
			if(cell != null) { memo.put("종점위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 종점경분
			if(cell != null) { memo.put("종점위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 종점경초
			if(cell != null) { memo.put("종점위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 시설물종류 -> 종류
			if(cell != null) { memo.put("종류", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 길이
			if(cell != null) { memo.put("길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 폭
			if(cell != null) { memo.put("폭", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 깊이
			if(cell != null) { memo.put("깊이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 골막이_판정
			if(cell != null) { memo.put("골막이_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 기슭막이_판정
			if(cell != null) { memo.put("기슭막이_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 바닥막이_판정
			if(cell != null) { memo.put("바닥막이_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(31); // 골막이
			if(cell != null) { memo.put("골막이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(32); // 기슭막이
			if(cell != null) { memo.put("기슭막이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(33); // 바닥막이
			if(cell != null) { memo.put("바닥막이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(34); // 계류상태
			if(cell != null) { memo.put("계류상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(35); // 수문
			if(cell != null) { memo.put("수문", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(36); // 식생상태
			if(cell != null) { memo.put("식생상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(37); // 안전시설
			if(cell != null) { memo.put("안전시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(38); // 접근도로
			if(cell != null) { memo.put("접근도로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(39); // 기타
			if(cell != null) { memo.put("기타", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(40); // 점검결과
			if(cell != null) { memo.put("점검결과", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(41); // 조치사항
			if(cell != null) { memo.put("조치사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(42); // 지정해제
			if(cell != null) { memo.put("지정해제", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(43); // 종합의견1
			if(cell != null) { memo.put("종합의견1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(44); // 종합의견2
			if(cell != null) { memo.put("종합의견2", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(45); // 종합의견3
			if(cell != null) { memo.put("종합의견3", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(46); // 종합의견4
			if(cell != null) { memo.put("종합의견4", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(47); // 종합의견5
			if(cell != null) { memo.put("종합의견5", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(48); // 주요시설_특이사항
			if(cell != null) { memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(49); // 부대시설_특이사항
			if(cell != null) { memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(50); // 기타_특이사항
			if(cell != null) { memo.put("기타_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(51); // 사방사업명
			if(cell != null) { memo.put("사방사업명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(52); // 관리번호
			if(cell != null) {
				memo.put("관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(53); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}

			cell = row.getCell(54); // 주요공작물_시설명
			if(cell != null) { memo.put("주요공작물_시설명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(55); // 전:시공비, 후:시공비(천원)
			if(cell != null) { 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell)); 
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}

			cell = row.getCell(56); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(57); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(58); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(59); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(60); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(61); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(62); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(63); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(64); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(65); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(66); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(67); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(68); // 측벽 길이
			if(cell != null) {  memo.put("측벽 길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(69); // 측벽 높이
			if(cell != null) {  memo.put("측벽 높이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(70); // 물받이 가로
			if(cell != null) {  memo.put("물받이 가로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(71); // 물받이 세로
			if(cell != null) {  memo.put("물받이 세로", ExcelUtils.getCellValue(cell)); }

	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("시점위도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점위분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점위초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경초").toString())) {
	    		latDd = Integer.parseInt(memo.get("시점위도").toString()) + (Double.parseDouble(memo.get("시점위분").toString())/60) + (Double.parseDouble(memo.get("시점위초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("시점경도").toString()) + (Double.parseDouble(memo.get("시점경분").toString())/60) + (Double.parseDouble(memo.get("시점경초").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("시점위도").toString()+"° "+memo.get("시점위분").toString()+"'' "+memo.get("시점위초").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("시점경도").toString()+"° "+memo.get("시점경분").toString()+"'' "+memo.get("시점경초").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}				
		}
		
		if(mType.equals("산지사방")) {
			cell = row.getCell(2);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(3);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(5);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(6);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(8);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(9); // 지정면적
			if(cell != null) { memo.put("지정면적", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(10); // 점검일시
			if(cell != null) { memo.put("점검일시", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(11); // 점검자
			if(cell != null) { memo.put("점검자", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(12); // 위도도
			if(cell != null) { memo.put("위도도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13); // 위도분
			if(cell != null) { memo.put("위도분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(14); // 위도초
			if(cell != null) { memo.put("위도초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(15); // 경도도
			if(cell != null) { memo.put("경도도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(16); // 경도분
			if(cell != null) { memo.put("경도분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(17); // 경도초
			if(cell != null) { memo.put("경도초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(18); // 시설물종류
			if(cell != null) { memo.put("시설물종류", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 보강시설_판정
			if(cell != null) { memo.put("보강시설_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 보호시설_판정
			if(cell != null) { memo.put("보호시설_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 배수시설_판정
			if(cell != null) { memo.put("배수시설_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 보강시설
			if(cell != null) { memo.put("보강시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 보호시설
			if(cell != null) { memo.put("보호시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 배수시설
			if(cell != null) { memo.put("배수시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 사면상태
			if(cell != null) { memo.put("사면상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 수문
			if(cell != null) { memo.put("수문", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 식생상태
			if(cell != null) { memo.put("식생상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 안전시설
			if(cell != null) { memo.put("안전시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 접근도로
			if(cell != null) { memo.put("접근도로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 기타
			if(cell != null) { memo.put("기타", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(31); // 점검결과
			if(cell != null) { memo.put("점검결과", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(32); // 조치사항
			if(cell != null) { memo.put("조치사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(33); // 지정해제
			if(cell != null) { memo.put("지정해제", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(34); // 종합의견1
			if(cell != null) { memo.put("종합의견1", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(35); // 종합의견2
			if(cell != null) { memo.put("종합의견2", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(36); // 종합의견3
			if(cell != null) { memo.put("종합의견3", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(37); // 종합의견4
			if(cell != null) { memo.put("종합의견4", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(38); // 종합의견5
			if(cell != null) { memo.put("종합의견5", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(39); // 주요시설_특이사항
			if(cell != null) { memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(40); // 부대시설_특이사항
			if(cell != null) { memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(41); // 기타_특이사항
			if(cell != null) { memo.put("기타_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(42); // 사방사업명
			if(cell != null) { memo.put("사방사업명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(43); // 사방댐관리번호
			if(cell != null) {
				memo.put("사방댐사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(44); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}

			cell = row.getCell(45); // 주요공작물_시설명
			if(cell != null) { memo.put("주요공작물_시설명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(46); // 전:시공비, 후:시공비(천원)
			if(cell != null) { 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell)); 
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}			

			cell = row.getCell(47); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(48); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(49); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(50); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(51); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(52); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(53); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(54); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(55); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(56); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(57); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(58); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(59); // 측벽 길이
			if(cell != null) {  memo.put("측벽 길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(60); // 측벽 높이
			if(cell != null) {  memo.put("측벽 높이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(61); // 물받이 가로
			if(cell != null) {  memo.put("물받이 가로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(62); // 물받이 세로
			if(cell != null) {  memo.put("물받이 세로", ExcelUtils.getCellValue(cell)); }

	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("위도도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위도분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위도초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도초").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도도").toString()) + (Double.parseDouble(memo.get("위도분").toString())/60) + (Double.parseDouble(memo.get("위도초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도도").toString()) + (Double.parseDouble(memo.get("경도분").toString())/60) + (Double.parseDouble(memo.get("경도초").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("위도도").toString()+"° "+memo.get("위도분").toString()+"'' "+memo.get("위도초").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("경도도").toString()+"° "+memo.get("경도분").toString()+"'' "+memo.get("경도초").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}
		}
		
		if(mType.equals("해안침식방지") || mType.equals("해안방재림")) {
			cell = row.getCell(2); // 사방사업명
			if(cell != null) { memo.put("사방사업명", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(3); // 지정면적
			if(cell != null) { memo.put("지정면적", ExcelUtils.getCellValue(cell)); }
			

			cell = row.getCell(6);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(8);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(9);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(10);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(11);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(4); // 사방댐관리번호
			if(cell != null) {
				memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(5); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}


			cell = row.getCell(12);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); } 
			cell = row.getCell(13);  //주요공작물_시설명
			if(cell!=null){ memo.put("주요공작물_시설명", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(14);  //전:시공비 후:시공비(천원)
			if(cell!=null){ 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}			

			cell = row.getCell(15); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(16); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(17); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(18); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 길이_주요시설1
			if(cell != null) {  memo.put("길이_주요시설1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 높이_주요시설1
			if(cell != null) {  memo.put("높이_주요시설1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 가로_주요시설2
			if(cell != null) {  memo.put("길이_주요시설2", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 세로_주요시설2
			if(cell != null) {  memo.put("높이_주요시설2", ExcelUtils.getCellValue(cell)); }

	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("위도_현지계측").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위분_현지계측").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위초_현지계측").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도_현지계측").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경분_현지계측").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경초_현지계측").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도_현지계측").toString()) + (Double.parseDouble(memo.get("위분_현지계측").toString())/60) + (Double.parseDouble(memo.get("위초_현지계측").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도_현지계측").toString()) + (Double.parseDouble(memo.get("경분_현지계측").toString())/60) + (Double.parseDouble(memo.get("경초_현지계측").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("위도_현지계측").toString()+"° "+memo.get("위분_현지계측").toString()+"'' "+memo.get("위초_현지계측").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("경도_현지계측").toString()+"° "+memo.get("경분_현지계측").toString()+"'' "+memo.get("경초_현지계측").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}
		}
		
		vo.setATTR(attr.toString());
		vo.setMEMO(memo.toString());
        vo.setLATDD(latDd);
        vo.setLONDD(lonDd);

		return vo;
	}
	
	/**
	 * 정밀점검 조사정보 HSSF 포맷 처리용
	 * @author DEVWORK
	 * @param mType
	 * @param row
	 * @param vo
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public static FckPcsFieldBookItemVO convertHSSFCellValues(String mType,HSSFRow row,FckPcsFieldBookItemVO vo) throws Exception {

		JSONArray attr = new JSONArray();
		JSONObject attrItem = null;
		JSONObject memo = new JSONObject();

		Calendar calendar = Calendar.getInstance();

		HSSFCell cell = null;
		
		String ecnrType = ""; 
		
		if(mType.equals("사방댐")) {
			cell = row.getCell(48); // 전:조사유형, 후:종류
			if(cell != null) {
			//	memo.put("종류", ExcelUtils.getCellValue(cell));
				if(ExcelUtils.getCellValue(cell).contains("강재")) {
					ecnrType = "강재 ";
				}else if(ExcelUtils.getCellValue(cell).contains("콘크리트")) {
					ecnrType = "콘크리트 ";
				}else if(ExcelUtils.getCellValue(cell).contains("석재")) {
					ecnrType = "석재 ";
				}
			}
		}
		
		memo.put("조사유형", ecnrType.concat(mType).concat(" 정밀점검"));
		
		double latDd = 0;
		double lonDd = 0;


		cell = row.getCell(0); // 일련번호

		if(cell != null) {
			String fid = ExcelUtils.getCellValue(cell);

			if(fid.equals("")) { return vo; }

			memo.put("일련번호", fid);

			vo.setFID(Integer.parseInt(fid));
		} else { return vo; }

		cell = row.getCell(1);

		if(cell != null) {
			String svyId = ExcelUtils.getCellValue(cell);
    		
			if(svyId.equals("")) { return vo; }
    		
			memo.put("조사ID", svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    		
    		vo.setKEYWORD(ExcelUtils.getCellValue(cell));
    		vo.setLABEL(ExcelUtils.getCellValue(cell));
		} else { return vo; }

		attrItem = new JSONObject();
		attrItem.put("VALUE", ecnrType.concat(mType).concat(" 정밀점검"));
		attrItem.put("NAME","조사유형");
		attr.put(attrItem);
    	
		attrItem = new JSONObject();
		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
		attrItem.put("NAME","조사연도");
		attr.put(attrItem);	

		if(mType.equals("사방댐")) {

			cell = row.getCell(2);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(3);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(5);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(6);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(8);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(9); // 사방댐_종류
			if(cell != null) {
				memo.put("사방댐_종류", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐_종류");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐_종류");
				attr.put(attrItem);
			}

			cell = row.getCell(10); // 사방댐_형식
			if(cell != null) { memo.put("사방댐_형식", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(11); // 전:상장
			if(cell != null) { memo.put("상장(m)", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(12); // 전:하장
			if(cell != null) { memo.put("하장(m)", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13); // 전:유효, 후:높이(m) 유효고
			if(cell != null) { memo.put("높이(m) 유효고", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(14); // 점검일시
			if(cell != null) { memo.put("점검일시", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(15); // 전:조사자, 후:점검자
			if(cell != null) { memo.put("점검자", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(16); // 전:사방댐관리번호
			if(cell != null) {
				memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(17); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}

			cell = row.getCell(18); // 위도
			if(cell != null) { memo.put("위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 위분
			if(cell != null) { memo.put("위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 위초
			if(cell != null) { memo.put("위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 경도
			if(cell != null) { memo.put("경도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 경분
			if(cell != null) { memo.put("경분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 경초
			if(cell != null) { memo.put("경초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 본댐측정
			if(cell != null) { memo.put("본댐 측정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 측벽측정
			if(cell != null) { memo.put("측벽 측정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 물받이측정
			if(cell != null) { memo.put("물받이 측정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 본댐
			if(cell != null) { memo.put("본댐", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 측벽
			if(cell != null) { memo.put("측벽", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 물받이
			if(cell != null) { memo.put("물받이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 수문
			if(cell != null) { memo.put("수문", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(31); // 식생
			if(cell != null) { memo.put("식생", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(32); // 안전시설
			if(cell != null) { memo.put("안전시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(33); // 접근도로
			if(cell != null) { memo.put("접근도로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(34); // 기타
			if(cell != null) { memo.put("기타", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(35); // 저사상태
			if(cell != null) { memo.put("저사상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(36); // 저사량
			if(cell != null) { memo.put("저사량", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(37); // 점검결과
			if(cell != null) { memo.put("점검결과", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(38); // 조치사항
			if(cell != null) { memo.put("조치사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(39); // 지정해제
			if(cell != null) { memo.put("지정해제", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(40); // 종합의견1
			if(cell != null) { memo.put("종합의견1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(41); // 종합의견2
			if(cell != null) { memo.put("종합의견2", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(42); // 종합의견3
			if(cell != null) { memo.put("종합의견3", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(43); // 종합의견4
			if(cell != null) { memo.put("종합의견4", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(44); // 종합의견5
			if(cell != null) { memo.put("종합의견5", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(45); // 주요시설_특이사항
			if(cell != null) { memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(46); // 부대시설_특이사항
			if(cell != null) { memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(47); // 기타_특이사항
			if(cell != null) { memo.put("기타_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(48); // 종류
			if(cell != null) { memo.put("종류", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(49); // 전:전고, 후:높이(m) 전고
			if(cell != null) { memo.put("높이(m) 전고", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(50); // 천단폭
			if(cell != null) { memo.put("천단폭", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(51); // 전:시공비, 후:시공비(천원)
			if(cell != null) { 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell)); 
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}

			cell = row.getCell(52); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(53); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(54); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(55); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(56); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(57); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(58); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(59); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(60); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(61); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(62); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(63); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(64); // 전:측벽길이, 후:측벽 길이
			if(cell != null) {  memo.put("측벽 길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(65); // 전:측벽높이, 후:측벽 높이
			if(cell != null) {  memo.put("측벽 높이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(66); // 전:물받이가로, 후:물받이 가로
			if(cell != null) {  memo.put("물받이 가로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(67); // 전:물받이세로, 후:물받이 세로
			if(cell != null) {  memo.put("물받이 세로", ExcelUtils.getCellValue(cell)); }

	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("위도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경초").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도").toString()) + (Double.parseDouble(memo.get("위분").toString())/60) + (Double.parseDouble(memo.get("위초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도").toString()) + (Double.parseDouble(memo.get("경분").toString())/60) + (Double.parseDouble(memo.get("경초").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("위도").toString()+"° "+memo.get("위분").toString()+"'' "+memo.get("위초").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("경도").toString()+"° "+memo.get("경분").toString()+"'' "+memo.get("경초").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}			
				
		}
		
		if(mType.equals("계류보전")) {
			cell = row.getCell(2);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(3);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(5);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(6);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(8);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(9); // 지정면적
			if(cell != null) { memo.put("지정면적", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(10); // 점검일시
			if(cell != null) { memo.put("점검일시", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(11); // 점검자
			if(cell != null) { memo.put("점검자", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(12); // 시점위도
			if(cell != null) { memo.put("시점위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13); // 시점위분
			if(cell != null) { memo.put("시점위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(14); // 시점위초
			if(cell != null) { memo.put("시점위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(15); // 시점경도
			if(cell != null) { memo.put("시점경도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(16); // 시점경분
			if(cell != null) { memo.put("시점경분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(17); // 시점경초
			if(cell != null) { memo.put("시점경초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(18); // 종점위도
			if(cell != null) { memo.put("종점위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 종점위분
			if(cell != null) { memo.put("종점위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 종점위초
			if(cell != null) { memo.put("종점위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 종점경도
			if(cell != null) { memo.put("종점위도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 종점경분
			if(cell != null) { memo.put("종점위분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 종점경초
			if(cell != null) { memo.put("종점위초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 시설물종류
			if(cell != null) { memo.put("시설물종류", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 길이
			if(cell != null) { memo.put("길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 폭
			if(cell != null) { memo.put("폭", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 깊이
			if(cell != null) { memo.put("깊이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 골막이_판정
			if(cell != null) { memo.put("골막이_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 기슭막이_판정
			if(cell != null) { memo.put("기슭막이_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 바닥막이_판정
			if(cell != null) { memo.put("바닥막이_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(31); // 골막이
			if(cell != null) { memo.put("골막이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(32); // 기슭막이
			if(cell != null) { memo.put("기슭막이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(33); // 바닥막이
			if(cell != null) { memo.put("바닥막이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(34); // 계류상태
			if(cell != null) { memo.put("계류상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(35); // 수문
			if(cell != null) { memo.put("수문", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(36); // 식생상태
			if(cell != null) { memo.put("식생상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(37); // 안전시설
			if(cell != null) { memo.put("안전시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(38); // 접근도로
			if(cell != null) { memo.put("접근도로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(39); // 기타
			if(cell != null) { memo.put("기타", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(40); // 점검결과
			if(cell != null) { memo.put("점검결과", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(41); // 조치사항
			if(cell != null) { memo.put("조치사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(42); // 지정해제
			if(cell != null) { memo.put("지정해제", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(43); // 종합의견1
			if(cell != null) { memo.put("종합의견1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(44); // 종합의견2
			if(cell != null) { memo.put("종합의견2", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(45); // 종합의견3
			if(cell != null) { memo.put("종합의견3", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(46); // 종합의견4
			if(cell != null) { memo.put("종합의견4", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(47); // 종합의견5
			if(cell != null) { memo.put("종합의견5", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(48); // 주요시설_특이사항
			if(cell != null) { memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(49); // 부대시설_특이사항
			if(cell != null) { memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(50); // 기타_특이사항
			if(cell != null) { memo.put("기타_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(51); // 사방사업명
			if(cell != null) { memo.put("사방사업명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(52); // 사방댐관리번호
			if(cell != null) {
				memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(53); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}

			cell = row.getCell(54); // 주요공작물_시설명
			if(cell != null) { memo.put("주요공작물_시설명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(55); // 전:시공비, 후:시공비(천원)
			if(cell != null) { 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell)); 
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}

			cell = row.getCell(56); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(57); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(58); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(59); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(60); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(61); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(62); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(63); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(64); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(65); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(66); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(67); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(68); // 측벽 길이
			if(cell != null) {  memo.put("측벽 길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(69); // 측벽 높이
			if(cell != null) {  memo.put("측벽 높이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(70); // 물받이 가로
			if(cell != null) {  memo.put("물받이 가로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(71); // 물받이 세로
			if(cell != null) {  memo.put("물받이 세로", ExcelUtils.getCellValue(cell)); }

			if(EgovNumberUtil.getNumberValidCheck(memo.get("시점위도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점위분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점위초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경초").toString())) {
	    		latDd = Integer.parseInt(memo.get("시점위도").toString()) + (Double.parseDouble(memo.get("시점위분").toString())/60) + (Double.parseDouble(memo.get("시점위초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("시점경도").toString()) + (Double.parseDouble(memo.get("시점경분").toString())/60) + (Double.parseDouble(memo.get("시점경초").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("시점위도").toString()+"° "+memo.get("시점위분").toString()+"'' "+memo.get("시점위초").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("시점경도").toString()+"° "+memo.get("시점경분").toString()+"'' "+memo.get("시점경초").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}				
		}
		
		if(mType.equals("산지사방")) {
			cell = row.getCell(2);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(3);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(5);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(6);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(8);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}

			cell = row.getCell(9); // 지정면적
			if(cell != null) { memo.put("지정면적", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(10); // 점검일시
			if(cell != null) { memo.put("점검일시", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(11); // 점검자
			if(cell != null) { memo.put("점검자", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(12); // 위도도
			if(cell != null) { memo.put("위도도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13); // 위도분
			if(cell != null) { memo.put("위도분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(14); // 위도초
			if(cell != null) { memo.put("위도초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(15); // 경도도
			if(cell != null) { memo.put("경도도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(16); // 경도분
			if(cell != null) { memo.put("경도분", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(17); // 경도초
			if(cell != null) { memo.put("경도초", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(18); // 시설물종류
			if(cell != null) { memo.put("시설물종류", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 보강시설_판정
			if(cell != null) { memo.put("보강시설_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 보호시설_판정
			if(cell != null) { memo.put("보호시설_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 배수시설_판정
			if(cell != null) { memo.put("배수시설_판정", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 보강시설
			if(cell != null) { memo.put("보강시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 보호시설
			if(cell != null) { memo.put("보호시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 배수시설
			if(cell != null) { memo.put("배수시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 사면상태
			if(cell != null) { memo.put("사면상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 수문
			if(cell != null) { memo.put("수문", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 식생상태
			if(cell != null) { memo.put("식생상태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 안전시설
			if(cell != null) { memo.put("안전시설", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 접근도로
			if(cell != null) { memo.put("접근도로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 기타
			if(cell != null) { memo.put("기타", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(31); // 점검결과
			if(cell != null) { memo.put("점검결과", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(32); // 조치사항
			if(cell != null) { memo.put("조치사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(33); // 지정해제
			if(cell != null) { memo.put("지정해제", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(34); // 종합의견1
			if(cell != null) { memo.put("종합의견1", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(35); // 종합의견2
			if(cell != null) { memo.put("종합의견2", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(36); // 종합의견3
			if(cell != null) { memo.put("종합의견3", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(37); // 종합의견4
			if(cell != null) { memo.put("종합의견4", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(38); // 종합의견5
			if(cell != null) { memo.put("종합의견5", ExcelUtils.getCellValue(cell)); }			
			cell = row.getCell(39); // 주요시설_특이사항
			if(cell != null) { memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(40); // 부대시설_특이사항
			if(cell != null) { memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(41); // 기타_특이사항
			if(cell != null) { memo.put("기타_특이사항", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(42); // 사방사업명
			if(cell != null) { memo.put("사방사업명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(43); // 사방댐관리번호
			if(cell != null) {
				memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(44); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}

			cell = row.getCell(45); // 주요공작물_시설명
			if(cell != null) { memo.put("주요공작물_시설명", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(46); // 전:시공비, 후:시공비(천원)
			if(cell != null) { 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell)); 
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}			

			cell = row.getCell(47); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(48); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(49); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(50); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(51); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(52); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(53); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(54); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(55); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(56); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(57); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(58); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(59); // 측벽 길이
			if(cell != null) {  memo.put("측벽 길이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(60); // 측벽 높이
			if(cell != null) {  memo.put("측벽 높이", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(61); // 물받이 가로
			if(cell != null) {  memo.put("물받이 가로", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(62); // 물받이 세로
			if(cell != null) {  memo.put("물받이 세로", ExcelUtils.getCellValue(cell)); }

	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("위도_산사태").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위분_산사태").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위초_산사태").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도_산사태").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경분_산사태").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경초_산사태").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도_산사태").toString()) + (Double.parseDouble(memo.get("위분_산사태").toString())/60) + (Double.parseDouble(memo.get("위초_산사태").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도_산사태").toString()) + (Double.parseDouble(memo.get("경분_산사태").toString())/60) + (Double.parseDouble(memo.get("경초_산사태").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("위도_산사태").toString()+"° "+memo.get("위분_산사태").toString()+"'' "+memo.get("위초_산사태").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("경도_산사태").toString()+"° "+memo.get("경분_산사태").toString()+"'' "+memo.get("경초_산사태").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}
		}
		
		if(mType.equals("해안침식방지") || mType.equals("해안방재림")) {
			cell = row.getCell(2); // 사방사업명
			if(cell != null) { memo.put("사방사업명", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(3); // 지정면적
			if(cell != null) { memo.put("지정면적", ExcelUtils.getCellValue(cell)); }
			
			cell = row.getCell(6);  //시도
			if(cell!=null){
				memo.put("시도", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시도");
				attr.put(attrItem);
			}
			
			cell = row.getCell(7);  //시군구
			if(cell!=null){
				memo.put("시군구", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시군구");
				attr.put(attrItem);
			}
			
			cell = row.getCell(8);  //읍면동
			if(cell!=null){
				memo.put("읍면동", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","읍면동");
				attr.put(attrItem);
			}
			
			cell = row.getCell(9);  //리
			if(cell!=null){
				memo.put("리", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","리");
				attr.put(attrItem);
			}
			
			cell = row.getCell(10);  //지번
			if(cell!=null){
				memo.put("지번", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","지번");
				attr.put(attrItem);
			}

			cell = row.getCell(11);  //속칭
			if(cell!=null){ 
				memo.put("속칭", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","속칭");
				attr.put(attrItem);
			}
			
			cell = row.getCell(4); // 사방댐관리번호
			if(cell != null) {
				memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","사방댐관리번호");
				attr.put(attrItem);
			}

			cell = row.getCell(5); // 국가지점번호
			if(cell != null) {
				memo.put("국가지점번호", ExcelUtils.getCellValue(cell));
				
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","국가지점번호");
				attr.put(attrItem);
			}


			cell = row.getCell(12);  //시설년도
			if(cell!=null){ memo.put("시설년도", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(13);  //주요공작물_시설명
			if(cell!=null){ memo.put("주요공작물_시설명", ExcelUtils.getCellValue(cell)); }

			cell = row.getCell(14);  //전:시공비 후:시공비(천원)
			if(cell!=null){ 
				memo.put("시공비(천원)", ExcelUtils.getCellValue(cell));
								
				attrItem = new JSONObject();
				attrItem.put("VALUE", ExcelUtils.getCellValue(cell));
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			} else {
				attrItem = new JSONObject();
				attrItem.put("VALUE", "");
				attrItem.put("NAME","시공비(천원)");
				attr.put(attrItem);
			}			

			cell = row.getCell(15); // 위도_산사태
			if(cell != null) {  memo.put("위도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(16); // 위분_산사태
			if(cell != null) {  memo.put("위분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(17); // 위초_산사태
			if(cell != null) {  memo.put("위초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(18); // 경도_산사태
			if(cell != null) {  memo.put("경도_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(19); // 경분_산사태
			if(cell != null) {  memo.put("경분_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(20); // 경초_산사태
			if(cell != null) {  memo.put("경초_산사태", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(21); // 위도_현지계측
			if(cell != null) {  memo.put("위도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(22); // 위분_현지계측
			if(cell != null) {  memo.put("위분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(23); // 위초_현지계측
			if(cell != null) {  memo.put("위초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(24); // 경도_현지계측
			if(cell != null) {  memo.put("경도_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(25); // 경분_현지계측
			if(cell != null) {  memo.put("경분_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(26); // 경초_현지계측
			if(cell != null) {  memo.put("경초_현지계측", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(27); // 길이_주요시설1
			if(cell != null) {  memo.put("길이_주요시설1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(28); // 높이_주요시설1
			if(cell != null) {  memo.put("높이_주요시설1", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(29); // 가로_주요시설2
			if(cell != null) {  memo.put("길이_주요시설2", ExcelUtils.getCellValue(cell)); }
			cell = row.getCell(30); // 세로_주요시설2
			if(cell != null) {  memo.put("높이_주요시설2", ExcelUtils.getCellValue(cell)); }

			if(EgovNumberUtil.getNumberValidCheck(memo.get("위도_현지계측").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위분_현지계측").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위초_현지계측").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도_현지계측").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경분_현지계측").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경초_현지계측").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도_현지계측").toString()) + (Double.parseDouble(memo.get("위분_현지계측").toString())/60) + (Double.parseDouble(memo.get("위초_현지계측").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도_현지계측").toString()) + (Double.parseDouble(memo.get("경분_현지계측").toString())/60) + (Double.parseDouble(memo.get("경초_현지계측").toString())/3600);
				
				attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("위도_현지계측").toString()+"° "+memo.get("위분_현지계측").toString()+"'' "+memo.get("위초_현지계측").toString()+"\"");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", memo.get("경도_현지계측").toString()+"° "+memo.get("경분_현지계측").toString()+"'' "+memo.get("경초_현지계측").toString()+"\"");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}else {
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", "");
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    	}
		}
		
		vo.setATTR(attr.toString());
		vo.setMEMO(memo.toString());
        vo.setLATDD(latDd);
        vo.setLONDD(lonDd);
        
		return vo;
	}
}
