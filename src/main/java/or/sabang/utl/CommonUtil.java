package or.sabang.utl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.vyt.ecb.web.VytEcbAlsManageController;

public class CommonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
	
	private static final String VWORLD_API_URL = EgovProperties.getProperty("vworld.landUrl");
	private static final String VWORLD_API_KEY = EgovProperties.getProperty("vworld.apiKey");
	/**
	 * 토지소유정보속성조회 API
	 * @return
	 * @throws Exception
	 */
	public static String searchPosesnSe(String pnuCode, String domain) throws Exception {
		//api b0bcda058893e823d50809
		//String apiKey = "b0bcda058893e823d50809";
		StringBuilder urlBuilder = new StringBuilder(VWORLD_API_URL); /* URL */   
        StringBuilder parameter  = new StringBuilder();   
        
        parameter.append("?" + URLEncoder.encode("key","UTF-8") + "="+VWORLD_API_KEY); /*Key*/     
        parameter.append("&" + URLEncoder.encode("domain","UTF-8") + "="+domain); /*domain*/
        parameter.append("&" + URLEncoder.encode("pnu","UTF-8") + "=" + URLEncoder.encode(pnuCode, "UTF-8")); /* 고유번호(8자리 이상) */  
        parameter.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /* 응답결과 형식(xml 또는 json) */  
        parameter.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 검색건수 */  
        parameter.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지 번호 */  
    
        URL url = new URL(urlBuilder.toString() + parameter.toString());     
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();     
        conn.setRequestMethod("GET");     
        conn.setRequestProperty("Content-type", "application/json");     
        BufferedReader rd;     
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {     
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));     
        } else {     
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));     
            System.out.println("Response code: " + conn.getResponseCode());     
        }     
        StringBuilder sb = new StringBuilder();     
        String line;     
        while ((line = rd.readLine()) != null) {     
            sb.append(line);     
        }     
        rd.close();     
        conn.disconnect();     
        
        String result = sb.toString();
        
        JSONParser jsonParser = new JSONParser();
    	org.json.simple.JSONObject jsonObject1 = (org.json.simple.JSONObject)jsonParser.parse(result);
    	org.json.simple.JSONObject possessions = (org.json.simple.JSONObject)jsonObject1.get("possessions");
    	
    	org.json.simple.JSONArray fieldArray =  (org.json.simple.JSONArray) possessions.get("field");
    	if(fieldArray.size() > 0) {
    		org.json.simple.JSONObject fieldObject = (org.json.simple.JSONObject) fieldArray.get(0);
    		String posesnSeCodeNm = (String) fieldObject.get("posesnSeCodeNm");
    		String regstrSeCodeNm = (String) fieldObject.get("regstrSeCodeNm");
    		
    		// API에서 받아오는 값(regstrSeCodeNm)이 임야대장이면 산을 붙여야함.
    		LOGGER.info("주소"+"PNU("+(String) fieldObject.get("pnu")+")"+ ": "+(String) fieldObject.get("ldCodeNm") + " "+ (String) fieldObject.get("mnnmSlno") + " 소유구분 : "+ posesnSeCodeNm+" "+ ", 대장구분 : "+regstrSeCodeNm);
    		
    		String returnValue = posesnSeCodeNm +"," +regstrSeCodeNm;
    		
    		return returnValue;
    	}else {
    		return "-";
    	}
    }
}