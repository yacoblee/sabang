package egovframework.com.cmm.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

/**
 * 심플 URL TO FILE
 * @author ipodo
 * @name EgovSimpleCopyURLToFile
 * @created 2022. 1. 18
 * @modified 2022. 1. 18
 */
public class EgovSimpleCopyURLToFile {

	/**
	 * URL 기반 파일 저장 
	 * @author ipodo
	 * @name copyURLToFile
	 * @param url - request url
	 * @param filename
	 * @param filepath
	 * @return filepath
	 * @throws IOException
	 */
	public static String copyURLToFile(String url, String filename, String filepath, String extension) throws IOException {

		// image url
		URL requestUrl = new URL(url);
		// file full path
		File file = new File(filepath + filename + "." + extension);
		// url to file
		FileUtils.copyURLToFile(requestUrl, file);
		
		return file.getPath();
	}
	
}
