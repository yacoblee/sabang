package or.sabang.utl;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class JsonToArray {

	public static void main(String arg[]) {
		
	}
	
	public JSONObject setTransformEgovMap(List<Map<String, Object>> list) throws Exception {
		
		JSONObject result = new JSONObject();
	    JSONArray arr = new JSONArray();
		
		for(Map<String, Object> map : list) {
			arr.put(map);
		}
		
	    result.put("items", arr);
		return result;
	}

	public JSONObject setTransformMap(List<EgovMap> list) throws Exception {
		
		JSONObject result = new JSONObject();
	    JSONArray arr = new JSONArray();
		
	    for(EgovMap map : list) {
			arr.put(map);
		}
		
	    result.put("items", arr);
		return result;
	}

}