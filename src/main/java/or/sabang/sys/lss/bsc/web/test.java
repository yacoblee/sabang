package or.sabang.sys.lss.bsc.web;

import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class test {
	public static void main(String[] args) throws JSONException, URISyntaxException {
//		 TODO Auto-generated method stub
		String jObjSend = "{\"FID\": 2, \"리\": \"두무리\", \"사진\": [\"gimg:///한국치산기술협회-테스트.ncx/P00002.0001.jpg\", \"gimg:///한국치산기술협회-테스트.ncx/P00002.0002.jpg\", \"gimg:///한국치산기술협회-테스트.ncx/P00002.0003.jpg\", \"gimg:///한국치산기술협회-테스트.ncx/P00002.0004.jpg\"], \"시도\": \"강원도\", \"연번\": \"p2\", \"완료\": \"완료\", \"지번\": \"산12임\", \"컬러\": -638932, \"관할1\": \"18. 북부청\", \"관할2\": \"민북국유림관리소\", \"FILE_NCX\": \"/storage/emulated/0/가온지도/도면/한국치산기술협회-테스트.ncx\", \"조사ID\": \"P00002\", \"시군구\": \"양구군\", \"읍면동\": \"남면\", \"점수계\": \"44\", \"조사일\": \"2022-02-22\", \"조사자\": \"테스트\", \"필요성\": \"불필요\", \"끝점고도\": \"137\", \"보호대상\": \"5\", \"사진태그\": [{\"TAG\": \"테스트 태그1\", \"FILENAME\": \"/한국치산기술협회-테스트.ncx/P00002.0001.jpg\"}, {\"TAG\": \"테스트 태그2\", \"FILENAME\": \"/한국치산기술협회-테스트.ncx/P00002.0003.jpg\"}, {\"TAG\": \"테스트 태그3\", \"FILENAME\": \"/한국치산기술협회-테스트.ncx/P00002.0002.jpg\"}, {\"TAG\": \"\", \"FILENAME\": \"/한국치산기술협회-테스트.ncx/P00002.0004.jpg\"}], \"시점고도\": \"48\", \"조사유형\": \"토석류\", \"모암_점수\": \"\", \"끝점좌표_X\": 208349.8071518109, \"끝점좌표_Y\": 532759.2824375093, \"시점좌표_X\": 208789.58641011125, \"시점좌표_Y\": 533618.4704407423, \"조사자의견\": \"특이사항 없음\", \"주요위험성\": \"2\", \"황폐발생원\": \"2등급 이상\", \"모암_측정값\": \"\", \"보호대상_점수\": \"15\", \"위험인자_점수\": \"0\", \"집수면적_점수\": \"3\", \"보호대상_측정값\": \"5\", \"위험인자_측정값\": \"\", \"집수면적_측정값\": \"1\", \"총계류길이_점수\": \"10\", \"황폐발생원_점수\": \"9\", \"계류평균경사_점수\": \"9\", \"총계류길이_측정값\": \"965\", \"황폐발생원_측정값\": \"5\", \"계류평균경사_측정값\": \"5\"}";
		MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)                
                .addFormDataPart("label_no", "P00002")
				.addFormDataPart("mst_pwd", "123456")
				.addFormDataPart("mst_id", "70")
				.addFormDataPart("login_id", "test")
				.addFormDataPart("last_update", "2021-11-01 12:11:11")
				.addFormDataPart("data_for_update", jObjSend);
		
		String result = "";
//		String cameraDir = "D:\\01. 포도\\[2021] 사방정보관리시스템\\99. 참고자료\\기초조사 자료\\현장조사 결과 저장 양식\\사진 납품서식\\DG21GGSB001";
		JSONObject getBundleParameter = new JSONObject();
		
//		JSONArray images = new JSONArray();
//		
//		images.put("gimg:///사진/DG21GGSB001-1.jpg");
//		images.put("gimg:///사진/DG21GGSB001-2.jpg");
//		images.put("gimg:///사진/DG21GGSB001-3.jpg");
//		images.put("gimg:///사진/DG21GGSB001-4.jpg");
//		
//		getBundleParameter.put("사진", images);
//		
//		JSONArray jArrayImg = getBundleParameter.optJSONArray("사진");
//		if (jArrayImg != null) {
//		for (int a = 0; a < jArrayImg.length(); a++) {
//		        URI uri = new URI(jArrayImg.optString(a, ""));//Uri.parse(jArrayImg.optString(a, ""));
//		        File fileImg = null;
//		        if (uri.getScheme().equals("gimg")) {
//		        fileImg = new File(cameraDir, uri.getPath());
//		        } else {
//		        fileImg = new File(uri.getPath());
//		        }
//
//		        if (fileImg != null && fileImg.exists()) {
//		        formBodyBuilder.addFormDataPart("file" + a, fileImg.getName(), RequestBody.create(MediaType.parse("image/jpeg"), fileImg));
//		        }
//		   }
//		}
//		MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("mst_id", "55")
//                .addFormDataPart("password", "123456");
//		
		
		
		RequestBody requestBody = formBodyBuilder.build();
		
		//.url("http://220.78.192.2:30889/ext/lss/bsc/upload.do")
		//.url("http://172.30.1.50:8081/ext/lss/bsc/upload.do")
		//ext/lss/bsc/uploadCnrsSpceItem.do
		///ext/lss/bsc/selectCnrsSpceInfo.do
		Request request = new Request.Builder()
				.url("http://172.30.1.100:8081/ext/lss/bsc/upload.do")
		        .post(requestBody)
		        .build();
		
		OkHttpClient client = new OkHttpClient();

	      client.newCall(request).enqueue(new Callback() {
	         @Override
	         public void onFailure(Call call, IOException e) {
	        	 System.err.println(e.getMessage());
	         }

	         @Override
	         public void onResponse(Call call, Response response) throws IOException {
	            System.out.println(response.body().string());
	         }
	      });
	}
}
