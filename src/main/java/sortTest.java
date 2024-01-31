import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;

import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class sortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] photoTagNmArr= {"1.전경사진","2.유출구사진","3.현황사진1","4.현황사진2"};
		String fieldBookDir = "D:\\home\\tomcat\\";
		String fileStoreDir = "D:\\home\\tomcat\\temp\\";
		//File photoTagDir = new File(dir);
		String svyId = "";
		
		String test = "MULTIPOLYGON (((997394.3995741282 1894692.7158076232, 997369.4104565821 1894692.847203497, 997369.436735771 1894697.8450270751, 997374.434559336 1894697.8187478727, 997374.4871177952 1894707.8143949718, 997379.4849413326 1894707.7881157154, 997379.5112206029 1894712.785939238, 997384.5090441119 1894712.7596599525, 997384.5353234095 1894717.7574834481, 997364.5440292105 1894717.8626006956, 997364.4914706403 1894707.866953487, 997359.4936470229 1894707.893232745, 997359.4673677796 1894702.8954091128, 997339.4760730315 1894703.0005260352, 997339.5023522768 1894707.998349777, 997329.506704739 1894708.050908294, 997329.585542638 1894723.0443796813, 997339.5811901728 1894722.991821, 997339.6074695272 1894727.9896447412, 997349.6031169541 1894727.9370860057, 997349.6293963365 1894732.9349096932, 997364.6228672715 1894732.8560715085, 997364.6491466779 1894737.853895112, 997374.6447938312 1894737.8013362694, 997374.6710732677 1894742.7991598176, 997434.6449538827 1894742.4838064427, 997434.618674448 1894737.4859832216, 997439.6164976559 1894737.4597038028, 997439.6427770888 1894742.457526996, 997454.6362465435 1894742.3786886565, 997454.6625260048 1894747.376511766, 997469.655995213 1894747.297673345, 997469.6297157543 1894742.2998503172, 997474.6275387678 1894742.2735708712, 997474.5224212055 1894722.2822788642, 997479.5202441922 1894722.2559995274, 997479.4414063065 1894707.2625306032, 997474.4435833194 1894707.2888098569, 997474.4173040772 1894702.290986855, 997454.426011848 1894702.3961037644, 997454.4522910899 1894707.3939268757, 997419.4675286359 1894707.577881663, 997419.441249394 1894702.580058359, 997399.4499559604 1894702.6851752743, 997399.4236767454 1894697.687351862, 997394.4258533182 1894697.7136310632, 997394.3995741282 1894692.7158076232), (997434.5398363128 1894722.4925135595, 997434.566115663 1894727.4903367807, 997439.5639388721 1894727.4640574157, 997439.5902182495 1894732.461880609, 997429.5945718084 1894732.5144393938, 997429.5682924298 1894727.516616146, 997424.5704691677 1894727.5428955113, 997424.5967485456 1894732.540718787, 997414.6011019403 1894732.5932775722, 997414.5748225612 1894727.595454242, 997409.576999217 1894727.621733607, 997409.5507198652 1894722.6239102497, 997434.5398363128 1894722.4925135595), (997399.5550730958 1894722.6764689265, 997399.5813524486 1894727.6742923395, 997394.583529022 1894727.7005717051, 997394.5572496699 1894722.7027482647, 997399.5550730958 1894722.6764689265)))";
		
		String test1 = test.replaceAll("MULTIPOLYGON |\\(|\\)", "");
		
//		int photoTagCnt = 1, photoCnt = 1;
//		EgovMap map = new EgovMap();
//		map.put("mstnm", "228-한국치산기술협회-기초조사-경기(합)");
//		map.put("svyid", "SG22GGKYY010");
//		
//		
//		svyId = map.get("svyid").toString();
//		System.out.println("svyID : "+svyId);
//		
//		try {
//			File photoTagDir = new File(fileStoreDir.concat(map.get("svyid").toString()+File.separator+"1.현장사진"+File.separator));
//			File photoDir = new File(fileStoreDir.concat(map.get("svyid").toString()+File.separator+"2.기타사진"+File.separator));
//			if(EgovFileUtil.isExistsFile(photoTagDir.toString()) && EgovFileUtil.isExistsFile(photoDir.toString()) ) {
//				String photoTagArr = "[";
//				String photoArr = "[";
//				File[] photoTagList = photoTagDir.listFiles(); 
//				File[] photoList = photoDir.listFiles();
//	//			photoList = sortFileList(photoList,COMPARETYPE_NAME);
//				// 사진태그 작업
//				for(File j : photoTagList) {
//					String newFileNm = map.get("mstnm").toString().concat(".ncx/"+map.get("svyid").toString().concat(".000"+photoTagCnt+"."+FilenameUtils.getExtension(j.getName())));
//					String dbFileNm = "gimg:///"+newFileNm;
//					
//					// 파일 이동
//					File photoPath = new File(fieldBookDir+map.get("mstnm").toString()+".ncx");
//					if(!photoPath.exists()) photoPath.mkdirs();
//					Path filePath = Paths.get(j.getPath());
//					Path newFilePath = Paths.get(fieldBookDir+newFileNm);
//					
//					Files.copy(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
//					
//					// DB JSON 재조립
//					photoTagArr = photoTagArr.concat("{\"TAG\":\""+photoTagNmArr[photoTagCnt-1]+"\",\"FILENAME\":\""+dbFileNm+"\"},");
//					photoArr = photoArr.concat("\""+dbFileNm+"\",");
//					photoTagCnt++;
//				}
//				photoCnt = photoTagCnt;
//				photoTagArr = photoTagArr.substring(0, photoTagArr.length()-1).concat("]");
//				
//				// 사진 작업
//				for(File x : photoList) {
//					String photoNm = ".000"+ photoCnt;
//					if(photoCnt>= 10) photoNm = ".00"+photoCnt;
//					String newFileNm = map.get("mstnm").toString().concat(".ncx/"+map.get("svyid").toString().concat(photoNm+"."+FilenameUtils.getExtension(x.getName())));
//					String dbFileNm = "gimg:///"+newFileNm;
//					
//					// 파일 이동
//					File photoPath = new File(fieldBookDir+map.get("mstnm").toString()+".ncx");
//					if(!photoPath.exists()) photoPath.mkdirs();
//					Path filePath = Paths.get(x.getPath());
//					Path newFilePath = Paths.get(fieldBookDir+newFileNm);
//					Files.move(filePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
//					
//					// DB JSON 재조립
//					photoArr = photoArr.concat("\""+dbFileNm+"\",");
//					photoCnt++;
//				}
//				photoArr = photoArr.substring(0, photoArr.length()-1).concat("]");
//			}
//		}catch(Exception e) {
//			System.err.println(e.getLocalizedMessage());
//		}
	}

}
