import java.io.File;
import java.io.FilenameFilter;

import org.json.JSONObject;

import egovframework.rte.fdl.filehandling.EgovFileUtil;

public class imgCnt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject reqJson = null;
		String nm = "";
		//String folder = "E:\\문서\\사방협회\\현장사진(운영)\\기초조사\\한국치산기술협회-기초조사-강원도.ncx";
		String folder = "E:\\문서\\사방협회\\현장사진(운영)\\기초조사";
		
		File[] pfiles = new File(folder).listFiles();
		
		for(File f : pfiles) {
			if(f.isDirectory()) {
				//File folders = new File(folder);
				nm = f.getName();
				String fileList[] = f.list();
				reqJson = new JSONObject();
				for(int i=0; i<fileList.length; i++){
					String fileName = fileList[i];
					String svyId = fileName.split("\\.")[0];
					String pattern = "(^"+svyId+"\\.[0-9][0-9][0-9][0-9]\\.(?i)(jpg))$";
					String pattern1 = "(^"+svyId+"_위치도1\\.(?i)(png))$";
					if(!reqJson.has(svyId) && svyId.indexOf("위치도") < 0) {
						File[] files = f.listFiles(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								if(name.matches(pattern)) {
									File f = new File(dir, name);
									return f.isFile();
								}
								return false;
							}
						});
						
						File[] files1 = f.listFiles(new FilenameFilter() {
							@Override
							public boolean accept(File dir, String name) {
								if(name.matches(pattern1)) {
									File f = new File(dir, name);
									return f.isFile();
								}
								return false;
							}
						});
					
						reqJson.put(svyId, files.length+"_"+files1.length);
						//System.out.println(i+"번째 파일 저장 완료");
					}
					
					
//					for (int ii = 0; ii < files.length; ii++) {
//						String nm = files[ii].getName();
//						//EgovFileUtil.cp(storeFile+File.separator+nm, dir+File.separator+nm);//원본이미지 복사
//					}
				}
			}
			System.out.println(nm+" : "+reqJson.toString());
		}
	}

}
