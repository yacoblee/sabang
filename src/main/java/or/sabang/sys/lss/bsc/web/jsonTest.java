package or.sabang.sys.lss.bsc.web;

import java.util.HashMap;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import egovframework.com.utl.fcc.service.EgovStringUtil;

public class jsonTest {
	public static void main(String[] args) {
		//db -> 야장 -> 서버
		//String data_for_update = "{&quot;_FID&quot;:1,&quot;_LON&quot;:1045153.87566853,&quot;_LAT&quot;:1889485.85676961,&quot;_DATA&quot;:&quot;POINT (1045153.87566853 1889485.85676961)&quot;,&quot;_KEYWORD&quot;:&quot;100999&quot;,&quot;_LABEL&quot;:&quot;100999&quot;,&quot;_STYLE&quot;:&quot;BRUSH(bc:#f6402c8f,fc:#f6402cff);SYMBOL(c:#f6402cff,o:#f6402cff,s:8)&quot;,&quot;_MEMO&quot;:{&quot;FID&quot;:1,&quot;FILE_NCX&quot;:&quot;\\/storage\\/emulated\\/0\\/가온지도\\/도면\\/한국치산기술협회-횡계.ncx&quot;,&quot;조사ID&quot;:&quot;100999&quot;,&quot;조사유형&quot;:&quot;산사태&quot;,&quot;관할1&quot;:&quot;10. 강원&quot;,&quot;관할2&quot;:&quot;평창군&quot;,&quot;시도&quot;:&quot;강원도&quot;,&quot;시군구&quot;:&quot;평창군&quot;,&quot;읍면동&quot;:&quot;대관령면&quot;,&quot;리&quot;:&quot;횡계리&quot;,&quot;사진&quot;:[&quot;gimg:\\/\\/\\/한국치산기술협회-횡계.ncx\\/100999.0001.jpg&quot;],&quot;연번&quot;:&quot;&quot;,&quot;조사일&quot;:&quot;2021-11-19&quot;,&quot;조사자&quot;:&quot;최장범&quot;,&quot;지번&quot;:&quot;83&quot;,&quot;시점좌표_X&quot;:247661.55042410403,&quot;시점좌표_Y&quot;:440038.6491923684,&quot;끝점좌표_X&quot;:247588.9579868227,&quot;끝점좌표_Y&quot;:440051.63703184045,&quot;시 점고도&quot;:&quot;95&quot;,&quot;끝점고도&quot;:&quot;97&quot;,&quot;경사길이_측정값&quot;:&quot;74&quot;,&quot;경사도_측정값&quot;:&quot;2&quot;,&quot;점수계&quot;:&quot;40&quot;,&quot;보호대상&quot;:&quot;1&quot;,&quot;사면형&quot;:&quot;상승&quot;,&quot;임상&quot;:&quot;혼치&quot;,&quot;모암&quot;:&quot;화강암&quot;,&quot;보호대상_측정값&quot;:&quot;1&quot;,&quot;사면형_측정값&quot;:&quot;상승&quot;,&quot;임상_측정값&quot;:&quot;혼치&quot;,&quot;모암_측정값&quot;:&quot;화강암&quot;,&quot;보호대상_점수&quot;:&quot;10&quot;,&quot;경사길이_점수&quot;:&quot;15&quot;,&quot;경사도_점수&quot;:&quot;0&quot;,&quot;사면형_점수&quot;:&quot;3&quot;,&quot;임상_점수&quot;:&quot;8&quot;,&quot;모암_점수&quot;:&quot;4&quot;,&quot;필요성&quot;:&quot;불필요&quot;,&quot;주요위험성&quot;:&quot;&quot;,&quot;조사자의견&quot;:&quot;특이사항 없음&quot;,&quot;컬러&quot;:-638932,&quot;완료&quot;:&quot;완료&quot;},&quot;_TAG1&quot;:&quot;&quot;,&quot;_TAG2&quot;:&quot;&quot;,&quot;_REG_DATE&quot;:&quot;2021\\/11\\/19 11:22:33           &quot;,&quot;_UPD_DATE&quot;:&quot;2021\\/11\\/19 11:29:15&quot;,&quot;ATTR&quot;:[{&quot;VALUE&quot;:&quot;100999&quot;,&quot;NAME&quot;:&quot;조사ID&quot;},{&quot;VALUE&quot;:&quot;산사태&quot;,&quot;NAME&quot;:&quot;조사유형&quot;},{&quot;VALUE&quot;:&quot;2021&quot;,&quot;NAME&quot;:&quot;조사연도&quot;},{&quot;VALUE&quot;:&quot;10. 강원&quot;,&quot;NAME&quot;:&quot;관할1&quot;},{&quot;VALUE&quot;:&quot;평창군&quot;,&quot;NAME&quot;:&quot;관할2&quot;},{&quot;VALUE&quot;:&quot;강원도&quot;,&quot;NAME&quot;:&quot;시도&quot;},{&quot;VALUE&quot;:&quot;평창군&quot;,&quot;NAME&quot;:&quot;시군구&quot;},{&quot;VALUE&quot;:&quot;대관령면&quot;,&quot;NAME&quot;:&quot;읍면동&quot;},{&quot;VALUE&quot;:&quot;횡계리&quot;,&quot;NAME&quot;:&quot;리&quot;},{&quot;VALUE&quot;:&quot;14-83&quot;,&quot;NAME&quot;:&quot;JIBUN&quot;},{&quot;VALUE&quot;:&quot;37° 42&apos; 10\\&quot;&quot;,&quot;NAME&quot;:&quot;위도&quot;},{&quot;VALUE&quot;:&quot;128° 43&apos; 27\\&quot;&quot;,&quot;NAME&quot;:&quot;경도&quot;}]}";
		
		String data_for_update = "{&quot;_FID&quot;:1,&quot;_LON&quot;:208408.54332512338,&quot;_LAT&quot;:534738.5415352408,&quot;_DATA&quot;:&quot;POINT (208408.54332512338 534738.5415352408)&quot;,&quot;_KEYWORD&quot;:&quot;241947&quot;,&quot;_LABEL&quot;:&quot;241947&quot;,&quot;_STYLE&quot;:&quot;BRUSH(bc:#0096878f,fc:#009687ff);SYMBOL(c:#009687ff,o:#009687ff,s:8)&quot;,&quot;_MEMO&quot;:{&quot;FID&quot;:-1,&quot;TITLE&quot;:&quot;조사야장&quot;,&quot;PNU&quot;:&quot;0000000000000000000&quot;,&quot;LON&quot;:208408.54332512338,&quot;LAT&quot;:534738.5415352408,&quot;JIBUN&quot;:&quot;0&quot;,&quot;BON_BUN&quot;:&quot;0000&quot;,&quot;BU_BUN&quot;:&quot;0000&quot;,&quot;JIMOK&quot;:&quot;&quot;,&quot;FILE_NCX&quot;:&quot;\\/storage\\/emulated\\/0\\/가온지도\\/도면\\/한국치산기술협회-기초조사.ncx&quot;,&quot;조사ID&quot;:&quot;241947&quot;,&quot;조사유형&quot;:&quot;산사태&quot;,&quot;시점좌표_X&quot;:208408.27616446264,&quot;시점좌표_Y&quot;:534739.9322832496,&quot;신규추가&quot;:&quot;신규추가&quot;,&quot;사진&quot;:[&quot;gimg:\\/\\/\\/한국치산기술협회-기초조사.ncx\\/241947.0001.jpg&quot;],&quot;연번&quot;:&quot;11&quot;,&quot;조사일&quot;:&quot;2021-11-24&quot;,&quot;조사자&quot;:&quot;테스트&quot;,&quot;시도&quot;:&quot;경기도&quot;,&quot;시군구&quot;:&quot;성남시 수정구&quot;,&quot;읍면동&quot;:&quot;시흥동&quot;,&quot;리&quot;:&quot;&quot;,&quot;지번&quot;:&quot;200&quot;,&quot;관할1&quot;:&quot;경기도&quot;,&quot;관할2&quot;:&quot;성남시&quot;,&quot;끝점좌표_X&quot;:209544.02784539328,&quot;끝점좌표_Y&quot;:535147.3624110433,&quot;시점고도&quot;:&quot;79&quot;,&quot;끝점고도&quot;:&quot;63&quot;,&quot;경사길이_측정값&quot;:&quot;1207&quot;,&quot;경사도_측정값&quot;:&quot;-1&quot;,&quot;점수계&quot;:&quot;65&quot;,&quot;보호대상&quot;:&quot;1&quot;,&quot;사면형&quot;:&quot;복합&quot;,&quot;임상&quot;:&quot;침소&quot;,&quot;모암&quot;:&quot;반암&quot;,&quot;보호대상_측정값&quot;:&quot;1&quot;,&quot;사면형_측정값&quot;:&quot;복합&quot;,&quot;임상_측정값&quot;:&quot;침소&quot;,&quot;모암_측정값&quot;:&quot;반암&quot;,&quot;보호대상_점수&quot;:&quot;10&quot;,&quot;경사길이_점수&quot;:&quot;20&quot;,&quot;경사도_점수&quot;:&quot;0&quot;,&quot;사면형_점수&quot;:&quot;10&quot;,&quot;임상_점수&quot;:&quot;15&quot;,&quot;모암_점수&quot;:&quot;10&quot;,&quot;필요성&quot;:&quot;필요&quot;,&quot;주요위험성&quot;:&quot;위험요소 없음&quot;,&quot;조사자의견&quot;:&quot;특이사항 없음&quot;,&quot;컬러&quot;:-16738681,&quot;완료&quot;:&quot;완료&quot;},&quot;_TAG1&quot;:null,&quot;_TAG2&quot;:null,&quot;_REG_DATE&quot;:&quot;2021\\/11\\/24 19:48:31&quot;,&quot;_UPD_DATE&quot;:&quot;2021\\/11\\/24 19:48:31&quot;,&quot;ATTR&quot;:null}";
		
		
		
		JSONParser parser = new JSONParser();
		
		try {
			EgovStringUtil util = new EgovStringUtil();
			
			String update2 = util.getHtmlStrCnvr(data_for_update);
			
			org.json.simple.JSONObject resultObj = (org.json.simple.JSONObject) parser.parse(update2);
			
			System.out.println(((HashMap<?, ?>) resultObj.get("_MEMO")).get("조사유형").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
