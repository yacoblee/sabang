package or.sabang.utl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovSimpleCopyURLToFile;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.lss.bsc.service.impl.LssBscFieldBookServiceImpl;

/**
 * 정적 지도 이미지 저장 처리 
 * @author ipodo
 * @name StaticMapImageUtils
 * @created 2022. 1. 19
 * @modified 2022. 1. 19
 */
public class StaticMapImageUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(LssBscFieldBookServiceImpl.class);
//	private static final String REQ_URL = "http://api.vworld.kr/req/image?service=image&version=2.0&request=GetMap&errorFormat=json&basemap=PHOTO";
//	private static final String API_URL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
//	private static final String CLIENT_ID = "jrefvh2hua";
//	private static final String CLIENT_KEY = "mSLeig1M4Chktd2J0sAu1yN4rmNqiZYjgkIwXvvh";
	private static final String API_URL = EgovProperties.getProperty("lcMap.apiUrl");
	private static final String CLIENT_ID = EgovProperties.getProperty("lcMap.clientId");
	private static final String CLIENT_KEY = EgovProperties.getProperty("lcMap.clientKey");
	
	/**
	 * 정적 이미지 저장
	 * @author ipodo
	 * @name saveImageByXY
	 * @param lon
	 * @param lat
	 * @throws Exception
	 */
	public static String saveImageByXY(HashMap<String, Object> map) throws Exception {
		
//		String staticImagePath = (String) EgovComponentChecker.getBean("programImagePath");
		String staticImagePath = map.get("path").toString();
		String format = map.get("format").toString();
		String filename = map.get("name").toString();
		String label = map.get("label").toString();
		String type = map.get("type").toString();
		int width = (int) map.get("width");
		int height = (int) map.get("height");
		
		String url = convertParamToUrl(map);
		
		boolean resize = (boolean) map.get("resize");
		
		String filepath = EgovSimpleCopyURLToFile.copyURLToFile(url, filename, staticImagePath, format);
		
		if(filepath != null) {
			// 이미지 자르기
			resizeImage(filepath, 0, 50, width, (height - 100));
			// 이미지 병합
			mergeImage(filepath, label, type);
		}
		return filepath;
	}
	
	/**
	 * setting for url parameter
	 * @author ipodo
	 * @name convertParamToUrl
	 * @param coord
	 * @param crs
	 * @return param
	 * @throws UnsupportedEncodingException 
	 */
	private static String convertParamToUrl(HashMap<String, Object> item) throws UnsupportedEncodingException {
		/*
		 * maptype
		 * basic: 일반
		 * traffic: 교통지도
		 * satellite: 위성
		 * satellite_base: 위성 배경
		 * terrain: 지형도
		 */
		
		String svyid = item.get("svyid").toString();
		String se = item.get("se").toString();
		
		String crs = item.get("crs").toString();
		String format = item.get("format").toString();
//		String maptype = "satellite_base";
		String maptype = "";
		if(item.containsKey("mapType")) {
			if(item.get("mapType").toString().equals("NORMAL")) {
				maptype += "basic";
			}else {
				maptype += "satellite_base";
			}
		}else {
			maptype += "satellite_base";
		}
		
		int w = (int) item.get("width");
		int h = (int) item.get("height");
		int zoom = (int) item.get("zoom");
		String center = String.join(",", (String[]) item.get("center"));
		String marker = "";
	    
		if(item.get("marker").toString().split(";").length > 1) {
			String[] markers = item.get("marker").toString().split(";");
			String[] labels = item.get("label").toString().split(";");
			if(se.equals("BSC")) {
//				marker += "&markers=type:t|color:blue|pos:" + URLEncoder.encode(markers[0].toString()) + "|label:" + URLEncoder.encode(svyid + "_시점");
				marker += "&markers=type:t|color:blue|pos:" + URLEncoder.encode(markers[0].toString()) + "|label:" + URLEncoder.encode(svyid + "_시점","UTF-8");
//				marker += "&markers=type:t|color:red|pos:" + URLEncoder.encode(markers[1].toString()) + "|label:" + URLEncoder.encode(svyid + "_종점");
				marker += "&markers=type:t|color:red|pos:" + URLEncoder.encode(markers[1].toString()) + "|label:" + URLEncoder.encode(svyid + "_종점","UTF-8");
			}else if(markers[0].toString().equals(markers[1].toString())) {			
//				marker += "&markers=type:t|color:red|pos:" + URLEncoder.encode(markers[0].toString()) + "|label:" + URLEncoder.encode(labels[0].toString());
				marker += "&markers=type:t|color:red|pos:" + URLEncoder.encode(markers[0].toString()) + "|label:" + URLEncoder.encode(labels[0].toString(),"UTF-8");
			}else {
//				marker += "&markers=type:t|color:blue|pos:" + URLEncoder.encode(markers[0].toString()) + "|label:" + URLEncoder.encode(labels[0].toString());
				marker += "&markers=type:t|color:blue|pos:" + URLEncoder.encode(markers[0].toString()) + "|label:" + URLEncoder.encode(labels[0].toString(),"UTF-8");
//				marker += "&markers=type:t|color:red|pos:" + URLEncoder.encode(markers[1].toString()) + "|label:" + URLEncoder.encode(labels[1].toString());
				marker += "&markers=type:t|color:red|pos:" + URLEncoder.encode(markers[1].toString()) + "|label:" + URLEncoder.encode(labels[1].toString(),"UTF-8");
			}
		}
		else {
//			marker += "&markers=type:t|color:0xFE0000|pos:" + URLEncoder.encode(item.get("marker").toString()) + "|label:" + URLEncoder.encode(item.get("label").toString());
//			marker += "&markers=type:t|color:0xFE0000|pos:" + URLEncoder.encode(item.get("marker").toString()) + "|label:" + URLEncoder.encode(item.get("label").toString().split(" ")[0] + ", " + item.get("label").toString().split(" ")[1]);			
			marker += "&markers=type:t|color:0xFE0000|pos:" + URLEncoder.encode(item.get("marker").toString()) + "|label:" + URLEncoder.encode(item.get("label").toString().split(" ")[0] + ", " + item.get("label").toString().split(" ")[1],"UTF-8");			
		}
		
		return API_URL + "X-NCP-APIGW-API-KEY-ID=" + CLIENT_ID + "&X-NCP-APIGW-API-KEY=" + CLIENT_KEY 
				+ "&crs=" + crs + "&center=" + center + "&level=" + zoom + "&w=" + w + "&h=" + h
				+ "&maptype=" + maptype + "&format=" + format + "&scale=1" + marker;
	}
	
	/**
	 * 이미지 자르기 
	 * @author ipodo
	 * @name resizeImage
	 * @param path
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	private static void resizeImage(String path, int x, int y, int width, int height) throws IOException {

		File file = new File(path);

		BufferedImage inputStream = ImageIO.read(file);

		BufferedImage outputStream = inputStream.getSubimage(x, y, width, height);

		ImageIO.write(outputStream, "png", file);
	}
	
	/**
	 * 위치도 방위표 및 축척도 이미지 병합
	 * @author ipodo
	 * @name mergeImage
	 * @param path
	 * @returns
	 */
	private static void mergeImage(String filepath, String label, String type) throws Exception {
		try {
			// 방위표 이미지 가져오기
			InputStream io1 = new ClassPathResource("sabang/static/images/compass.png").getInputStream();
			// 축척도 이미지 가져오기
			InputStream io2 = new ClassPathResource("sabang/static/images/scale.png").getInputStream();
			// 외관점검 범례 가져오기
			InputStream io3 = null;
			if(type.contains("외관점검")) {
				if(type.contains("사방댐")) {
					io3 = new ClassPathResource("sabang/static/images/lgd1.jpg").getInputStream();
				}else if(type.contains("산지사방")) {
					io3 = new ClassPathResource("sabang/static/images/lgd2.jpg").getInputStream();
				}else {
					io3 = new ClassPathResource("sabang/static/images/lgd3.jpg").getInputStream();
				}
			}
			
			// 임시 파일 생성
			File tempimg1 = File.createTempFile("compas", ".png");
			File tempimg2 = File.createTempFile("scale", ".png");
			FileUtils.copyInputStreamToFile(io1, tempimg1);
			FileUtils.copyInputStreamToFile(io2, tempimg2);

			File merged = new File(filepath);
			BufferedImage img = ImageIO.read(merged); // 배경지도
			BufferedImage img1 = ImageIO.read(tempimg1); // 방위표
			BufferedImage img2 = ImageIO.read(tempimg2); // 축척도
			BufferedImage img3 = null;
			int h4 = 0;
			if(type.contains("외관점검")) {
				File tempimg3 = File.createTempFile("lgd", ".jpg");				
				FileUtils.copyInputStreamToFile(io3, tempimg3);
				img3 = ImageIO.read(tempimg3); // 범례				
				int w4 = img3.getWidth(); // 범례 넓이
				h4 = img3.getHeight(); // 범례 높이
			}
			
			int w1 = img.getWidth(); // 배경지도 넓이
			int h1 = img.getHeight(); // 배경지도 높이
			int w2 = img1.getWidth(); // 방위표 넓이
			int h2 = img1.getHeight(); // 방위표 높이
			int w3 = img2.getWidth(); // 축척도 넓이
			int h3 = img2.getHeight(); // 축척도 높이
			int t = ((w1 / 2) - 65); // 라벨 넓이
			
			BufferedImage mergeimg = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);

			Graphics2D gpc = (Graphics2D) mergeimg.getGraphics();

			//배경색 설정
			gpc.setBackground(Color.WHITE);
			//배경지도 그리기
			gpc.drawImage(img, 0, 0, null);
			//라벨 배경 색상 설정
//			gpc.setColor(new Color(255,255,209));
			//라벨 배경 생성
//			gpc.fillRect(t, h1/2, 160, 20);
//			gpc.fillRoundRect(t, h1/2, 160, 20, 10, 10);
			//라벨 텍스트 색상  설정
//			gpc.setColor(Color.black);
			//라벨 텍스트 폰트 설정
//			gpc.setFont(new Font(System.getProperty("os.name").toLowerCase().contains("win") ? "굴림" : "백묵 굴림", Font.PLAIN, 11));
			//라벨 텍스트 그리기
//			gpc.drawString(label, t+8, ((h1/2) + 13));
			//방위표 그리기
			gpc.drawImage(img1, (w1-w2-3), 3, null);
			//축척도 그리기
			gpc.drawImage(img2, (w1-w3), (h1-h3), null);
			//범례 그리기
			if(type.contains("외관점검")) {
				//축척도 그리기
				gpc.drawImage(img3, 0, (h1-h4), null);
			}
			//이미지 덮어씌기
			ImageIO.write(mergeimg, "png", merged);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
