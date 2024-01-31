package or.sabang.sys.ext.web;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.sys.ext.service.ExtFieldBookItemVO;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.ExtFieldBookVO;
import or.sabang.sys.service.SysComptVO;

@Controller
public class ExtFieldBookController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "extFieldBookService") 	
	private ExtFieldBookService extFieldBookService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	@Resource(name = "fieldBookUploadPath")
	String fieldBookUploadPath;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtFieldBookController.class);
	
	int cnt_total = 0;
	
	/**
	 * @param keyword 조사키워드(아이디)
	 * @param mst_id 공유방ID
	 * @param worktype 업무구분
	 * @param user 공유방생성자
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 전자야장 공유방 목록조회
	 */
	@RequestMapping(value = "/ext/lss/bsc/selectCnrsSpceList.do")
	public void selectCnrsSpceList(
			@RequestParam(value="keyword", defaultValue="") String keyword, 
			@RequestParam(value="mst_id", defaultValue="") String mst_id,
			@RequestParam(value="worktype", defaultValue="") String work_type,
			@RequestParam(value="user", defaultValue="") String user,
			Model model,
			HttpServletResponse res) throws Exception {

		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		
		JSONObject result = new JSONObject();
		JSONObject error = new JSONObject();
		HashMap<String, Object> schMap = new HashMap<>();
		List<ExtFieldBookVO> list = null;
		
		try {
			//조사유형, 생성자
			schMap.put("mst_id", (mst_id.equals("") ? "" : Integer.parseInt(mst_id)));
			schMap.put("keyword", (keyword.equals("") ? "" : keyword));
			schMap.put("work_type", (work_type.equals("") ? "" : work_type));
			schMap.put("user", (user.equals("") ? "" : user));
			
			list = extFieldBookService.selectCnrsSpceList(schMap);
			
			JSONArray jsonArr = new JSONArray();
			
			for(int i=0; i<list.size(); i++) {
				JSONObject data= new JSONObject();
				data.put("mst_id",list.get(i).getId());
				data.put("mst_corpname",list.get(i).getMst_corpname());
				data.put("mst_partname",list.get(i).getMst_partname());
				data.put("mst_create_user",list.get(i).getMst_create_user());
				data.put("mst_admin_user",list.get(i).getMst_admin_user());
				data.put("mst_status",list.get(i).getMst_status());
				data.put("svy_type", list.get(i).getSvy_type());
				jsonArr.put(data);
			}
			error.put("code", "0000");
			error.put("message", "");
			result.put("error",error);
			result.put("result",jsonArr);
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			error.put("code", "5000");
			error.put("message", e.getMessage());
			result.put("error", error);
			result.put("result", "");
		} finally {
			out.print(result);
		}
	}

	/**
	 * @param mst_password
	 * @param mst_id
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 전자야장 공유방 단건조회
	 */
	@RequestMapping(value = "/ext/lss/bsc/selectCnrsSpceInfo.do")
    public void selectCnrsSpceInfo(
    		@RequestParam(value="password") String mst_password, 
			@RequestParam(value="mst_id") String mst_id, 
			Model model,
			HttpServletResponse res) throws Exception {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		
		JSONObject result = new JSONObject();
		JSONObject item = new JSONObject();
		JSONObject error = new JSONObject();
		HashMap<String, Object> schMap = new HashMap<>();;
		ExtFieldBookVO fieldBookVO = new ExtFieldBookVO();
		
		int mstAt = mst_id.equals("") ? 0 : extFieldBookService.selectCnrsSpceAt(Integer.parseInt(mst_id));
		
		try {
			
			if(Integer.toString(mstAt).equals("0")) {
				error.put("code", "2001");
				error.put("message", "선택한 공유방이 없습니다.");
				result.put("error", error);
				result.put("result", "");
			} else if(mst_password.equals("")) {
				error.put("code", "2002");
				error.put("message", "비밀번호가 입력되지 않았습니다.");
				result.put("error", error);
				result.put("result", "");
			} else {
				schMap.put("mst_password", mst_password);
				schMap.put("mst_id", Integer.parseInt(mst_id));
				List<EgovMap> pwList = extFieldBookService.selectCnrsSpcePwd(schMap);
				String chkPassword = (String) pwList.get(0).get("mstPassword");
				if(!mst_password.equals(chkPassword)) {
					error.put("code", "2003");
					error.put("message", "비밀번호가 틀립니다.");
					result.put("error", error);
					result.put("result", "");
				} else {
					fieldBookVO  = extFieldBookService.selectCnrsSpceInfo(schMap);
					item.put("mst_id", fieldBookVO.getId());
					item.put("mst_corpname", fieldBookVO.getMst_corpname());
					item.put("mst_partname", fieldBookVO.getMst_partname());
					item.put("totcnt", fieldBookVO.getTotcnt());
					error.put("code", "0000");
					error.put("message", "");
					result.put("error",error);
					result.put("result",item);
				}
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			error.put("code", "5000");
			error.put("message", e.getMessage());
			result.put("error", error);
			result.put("result", "");
		} finally {
			out.print(result);
		}
	}

	/**
	 * @param mst_password
	 * @param mst_id
	 * @param data_offset
	 * @param data_limit
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 전자야장 공유방 다운로드
	 */
	@RequestMapping(value = "/ext/lss/bsc/selectCnrsSpceDownload.do")
    public void selectCnrsSpceDownload(
    		@RequestParam(value="mst_password") String mst_password, 
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="data_offset") String data_offset,
			@RequestParam(value="data_limit", defaultValue="100") String data_limit,
			Model model,
			HttpServletResponse res) throws Exception {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		JSONObject result = new JSONObject();
		JSONObject error = new JSONObject();
		JSONObject header = new JSONObject();
		HashMap<String, Object> schMap = new HashMap<>();
		List<ExtFieldBookItemVO> list = null;
		
		int mstAt = mst_id.equals("") ? 0 : extFieldBookService.selectCnrsSpceAt(Integer.parseInt(mst_id));
		
		try {

			if(Integer.toString(mstAt).equals("0")) {
				error.put("code", "2001");
				error.put("message", "선택한 공유방이 없습니다.");
				result.put("error", error);
				result.put("result", "");
			} else if(mst_password.equals("")) {
				error.put("code", "2002");
				error.put("message", "비밀번호가 입력되지 않았습니다.");
				result.put("error", error);
				result.put("result", "");
			} else {
				schMap.put("mst_password", mst_password);
				schMap.put("mst_id", Integer.parseInt(mst_id));				
				List<EgovMap> pwList = extFieldBookService.selectCnrsSpcePwd(schMap);
				String chkPassword = (String) pwList.get(0).get("mstPassword");
				if(!mst_password.equals(chkPassword)) {
					error.put("code", "2003");
					error.put("message", "비밀번호가 틀립니다.");
					result.put("error", error);
					result.put("result", "");
				} else {					
					if(data_limit.equals("")) {
						error.put("code", "2004");
						error.put("message", "검색조건이 잘못되었습니다.");
						result.put("error", error);
						result.put("result", "");
					} else if(data_offset.equals("")) {
						error.put("code", "2005");
						error.put("message", "검색조건이 잘못되었습니다.");
						result.put("error", error);
						result.put("result", "");
					} else {
						Date date = new Date();
						String upd_date = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date));
						
						error.put("code", "0000");
						error.put("message", "");
						schMap.put("data_offset", Integer.parseInt(data_offset));
						schMap.put("data_limit", Integer.parseInt(data_limit));
						//전자야장 도면 다운로드
						list = extFieldBookService.selectCnrsSpceDownload(schMap);
						JSONArray jsonArr = new JSONArray();
						for(int i=0; i<list.size(); i++) {
							JSONObject data= new JSONObject();
//							data.put("_FID", Integer.toString(list.get(i).getFID()) == null ? "" : list.get(i).getFID());
							data.put("_KEYWORD",list.get(i).getKEYWORD() == null ? "" : list.get(i).getKEYWORD());
							data.put("_LABEL",list.get(i).getLABEL() == null ? "" : list.get(i).getLABEL());
							data.put("_DATA",list.get(i).getDATA() == null ? "" : list.get(i).getDATA());
							data.put("_LAT",list.get(i).getLAT() == null ? "" : list.get(i).getLAT());
							data.put("_LON",list.get(i).getLON() == null ? "" : list.get(i).getLON());
							data.put("_MEMO",list.get(i).getMEMO() == null ? "" : list.get(i).getMEMO());
							data.put("_REG_DATE",list.get(i).getREG_DATE() != null ? list.get(i).getREG_DATE() : "");
							data.put("_UPD_DATE",list.get(i).getUPD_DATE() != null ? list.get(i).getREG_DATE() : "");
							data.put("_STYLE",list.get(i).getSTYLE() == null ? "" : list.get(i).getSTYLE());
							data.put("_TAG1",list.get(i).getTAG1() == null ? "" : list.get(i).getTAG1());
							data.put("_TAG2",list.get(i).getTAG2() == null ? "" : list.get(i).getTAG2());
							data.put("ATTR",list.get(i).getATTR() == null ? "" : list.get(i).getATTR());
							jsonArr.put(data);
						}
						header.put("UPDATE_TIME", upd_date);
						error.put("code", "0000");
						error.put("message", "");
						result.put("error",error);
						result.put("result",jsonArr);
						result.put("header", header);
					}
				}
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			error.put("code", "5000");
			error.put("message", e.getMessage());
			result.put("error", error);
			result.put("result", "");
		} finally {
			out.print(result);
		}
	}

	/**
	 * 전자야장 조사완료 데이터 단건 조회
	 * @param mst_id
	 * @param mst_pwd
	 * @param label_no
	 * @param itemVO
	 * @param model
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/ext/lss/bsc/selectCnrsSpceCompItem.do")
	public void selectCnrsSpceCompItem(
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="mst_pwd") String mst_pwd,
			@RequestParam(value="label_no", defaultValue="") String label_no,
			ExtFieldBookItemVO itemVO,
			Model model,
			HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		
		JSONObject result = new JSONObject();
        JSONObject error = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        
        HashMap<String, Object> schMap = new HashMap<>();
        ExtFieldBookItemVO vo = null;
		
		try {
			int mstAt = mst_id.equals("") ? 0 : extFieldBookService.selectCnrsSpceAt(Integer.parseInt(mst_id));
			
			if(Integer.toString(mstAt).equals("0")) {
				error.put("code", "3004");
				error.put("message", "공유방ID가 없습니다. 관리자에게 문의하세요.MST_ID=null");
				result.put("error", error);
				result.put("result", "");
			} else if(label_no.equals("")) {
				error.put("code", "3004");
				error.put("message", "라벨번호가 없습니다. 관리자에게 문의하세요.");
				result.put("error", error);
				result.put("result", "");
	        } else if(mst_pwd.equals("")) {
	        	error.put("code", "2002");
				error.put("message", "비밀번호가 입력되지 않았습니다.");
				result.put("error", error);
				result.put("result", "");
			} else {
				schMap.put("mst_id", Integer.parseInt(mst_id));
				schMap.put("_label", label_no);
				
				List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
				
				if(!mst_pwd.equals((String) infoList.get(0).get("mstPassword"))) {
					if(!mst_pwd.equals((String) infoList.get(0).get("mstReadPwd"))){
						if(!mst_pwd.equals((String) infoList.get(0).get("mstAdminPwd"))){
							error.put("code", "2002");
							error.put("message", "비밀번호가 틀립니다.");
							result.put("error", error);
							result.put("result", "");
						}
					}
				} else {
					vo = extFieldBookService.selectCnrsSpceCompItem(schMap);
					if(vo != null) {
						JSONObject data = new JSONObject();
						data.put("_KEYWORD",vo.getKEYWORD());
						data.put("_LABEL",vo.getLABEL());
						data.put("_DATA",vo.getDATA());
						data.put("_LAT",vo.getLAT());
						data.put("_LON",vo.getLON());
						data.put("_MEMO",vo.getMEMO());
						data.put("_REG_DATE",vo.getREG_DATE());
						data.put("_UPD_DATE",vo.getUPD_DATE());
						data.put("_STYLE",vo.getSTYLE());
						data.put("_TAG1",vo.getTAG1());
						data.put("_TAG2",vo.getTAG2());
						data.put("ATTR",vo.getATTR());
						jsonArr.put(data);
					}
					error.put("code", "0000");
					error.put("message", "");
					result.put("error", error);
					result.put("result", jsonArr);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			error.put("code", "5000");
			error.put("message", e.getMessage());
			result.put("error", error);
			result.put("result", "");
		} finally {
			out.print(result);
		}
	}
	/**
	 * @param mst_id
	 * @param mst_pwd
	 * @param last_update
	 * @param data_for_update
	 * @param login_id
	 * @param label_no
	 * @param mhsr
	 * @param itemVO
	 * @param model
	 * @param res
	 * @throws Exception
	 * @description 전자야장 조사데이터 업로드
	 */
	@RequestMapping(value = "/ext/lss/bsc/upload.do")
	public void uploadCnrsSpceItem(
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="mst_pwd") String mst_pwd,
			@RequestParam(value="last_update", defaultValue="") String last_update,
			@RequestParam(value="data_for_update", defaultValue="") String data_for_update,			
			@RequestParam(value="login_id", defaultValue="") String login_id,
			@RequestParam(value="label_no", defaultValue="") String label_no,
			MultipartHttpServletRequest mhsr,
			ExtFieldBookItemVO itemVO,
			Model model,
			HttpServletResponse res) throws Exception {
		long start = System.currentTimeMillis();
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		JSONObject header = new JSONObject();

		LOGGER.info("================ 전자야장 저장정보 =====================");
		
		LOGGER.info("공유방ID : ".concat(mst_id));
		LOGGER.info("공유방PWD : ".concat(mst_pwd));
		LOGGER.info("last_update : ".concat(last_update));
		LOGGER.info("data_for_update : ".concat(data_for_update));
		LOGGER.info("login_id : ".concat(login_id));
		LOGGER.info("label_no : ".concat(label_no));
		
		LOGGER.info("==================================================");
		
        Date date = new Date();
		String upd_date = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date));
        int sn = 0;

        JSONParser parser = new JSONParser();
        JSONObject result = new JSONObject();
        JSONObject error = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        
        String dataForUpdate = EgovStringUtil.getHtmlStrCnvr(data_for_update);
        
        org.json.simple.JSONObject resultObj = (org.json.simple.JSONObject) parser.parse(dataForUpdate);
		HashMap<String, Object> schMap = new HashMap<>();
		List<ExtFieldBookItemVO> list = null;
		
		try {
			int mstAt = mst_id.equals("") ? 0 : extFieldBookService.selectCnrsSpceAt(Integer.parseInt(mst_id));
			
			if(Integer.toString(mstAt).equals("0")) {
				error.put("code", "3004");
				error.put("message", "공유방ID가 없습니다. 관리자에게 문의하세요.MST_ID=null");
				result.put("error", error);
				result.put("result", "");
			} else if(label_no.equals("")) {
				error.put("code", "3004");
				error.put("message", "라벨번호가 없습니다. 관리자에게 문의하세요.");
				result.put("error", error);
				result.put("result", "");
	        } else if(mst_pwd.equals("")) {
	        	error.put("code", "2002");
				error.put("message", "비밀번호가 입력되지 않았습니다.");
				result.put("error", error);
				result.put("result", "");
			} else {
				schMap.put("mst_id", Integer.parseInt(mst_id));
				schMap.put("_label", label_no);
				schMap.put("last_update", last_update);
				List<EgovMap> rowList = extFieldBookService.selectCnrsSpceItem(schMap);
				List<EgovMap> infoList = extFieldBookService.selectCnrsSpcePwd(schMap);
				
				if(!mst_pwd.equals((String) infoList.get(0).get("mstPassword"))) {
					if(!mst_pwd.equals((String) infoList.get(0).get("mstReadPwd"))){
						if(!mst_pwd.equals((String) infoList.get(0).get("mstAdminPwd"))){
							error.put("code", "2002");
							error.put("message", "비밀번호가 틀립니다.");
							result.put("error", error);
							result.put("result", "");
						}
					}
				} else {
					//조사유형 구분
					HashMap<?, ?> resultMemo = (HashMap<?, ?>) resultObj.get("_MEMO");

					String type = null;
					String uploadPathDir = fieldBookUploadPath.concat("/").concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/");
					String uploadMidPathDir = "/".concat(infoList.get(0).get("mstCorpname")+"-"+infoList.get(0).get("mstPartname")).concat(".ncx/");
					
					if(resultMemo.containsKey("조사유형")) {
						type = resultMemo.get("조사유형").toString();
					}
					LOGGER.info("조사유형 : "+type);
					
			        if(type != null) {
			        	if(type.equals("산사태") || type.equals("토석류")) {
							itemVO.setSE("BSC");
							schMap.put("SE","BSC");
				        } else if (type.contains("땅밀림")) {
				        	itemVO.setSE("LCP");
				        	schMap.put("SE","LCP");
				        } else if(type.contains("외관점검")) {
							itemVO.setSE("APR");
							schMap.put("SE","APR");
				        } else if(type.contains("취약지역 실태조사")){
							itemVO.setSE("WKA");
							schMap.put("SE","WKA");
				        } else if(type.contains("취약지역 해제조사")){	
				        	itemVO.setSE("CNL");
				        	schMap.put("SE","CNL");
				        } else if(type.contains("임도")){	
				        	itemVO.setSE("FRD");
				        	schMap.put("SE","FRD");
				        } else if(type.contains("계측장비")){	
				        	itemVO.setSE("MSE");
				        	schMap.put("SE","MSE");
				        } else if(type.contains("정밀점검")){	
				        	itemVO.setSE("PCS");
				        	schMap.put("SE","PCS");
				        }

						//UPD_DATE 서버 업데이트 날짜 비교 이전꺼면 버리는걸로
						String data_upd_date = resultObj.get("_UPD_DATE").toString();

						itemVO.setMST_ID(mst_id);
						itemVO.setLOGIN_ID(login_id);
						itemVO.setLON(resultObj.get("_LON") != null ? resultObj.get("_LON").toString() : Double.toString((double) rowList.get(0).get("Lon")));
						itemVO.setLAT(resultObj.get("_LAT") != null ? resultObj.get("_LAT").toString() : Double.toString((double) rowList.get(0).get("Lat")));			
						itemVO.setDATA(resultObj.get("_DATA") != null ? resultObj.get("_DATA").toString() : (String) rowList.get(0).get("Data"));
						itemVO.setKEYWORD(resultObj.get("_KEYWORD") != null ? resultObj.get("_KEYWORD").toString() : (String) rowList.get(0).get("Keyword"));
						itemVO.setLABEL(label_no);
						itemVO.setSTYLE(resultObj.get("_STYLE") != null ? resultObj.get("_STYLE").toString() : (String) rowList.get(0).get("Style"));
						itemVO.setMEMO(resultObj.get("_MEMO") != null ? resultObj.get("_MEMO").toString().replaceAll("\\\\/", "/") : (String) rowList.get(0).get("Memo"));
						itemVO.setREG_DATE(data_upd_date);
						itemVO.setUPD_DATE(data_upd_date);
						itemVO.setLAST_UPD_DATE(upd_date);
						itemVO.setATTR(resultObj.get("ATTR") != null ? resultObj.get("ATTR").toString() : "[]");
						
						int updAt = extFieldBookService.selectUpdDateAt(itemVO);
						int comptAt = extFieldBookService.selectUpsertAt(itemVO);
						
//						itemVO.setUPD_DATE(upd_date);
						
						if(updAt > 0 || comptAt == 0) {
							/* 파일 리스트 업로드*/
							mhsr.setCharacterEncoding("utf-8");
							Map fileMap = new HashMap();
							Enumeration enu = mhsr.getParameterNames();
							
							while(enu.hasMoreElements()) {
					            String name = (String)enu.nextElement();
					            String value = mhsr.getParameter(name);
					            fileMap.put(name, value);
					        }
							
							List<String> fileList = new ArrayList<String>();
					        Iterator<String> fileNames = mhsr.getFileNames();
				            
					        while(fileNames.hasNext()) {
					            String fileName = fileNames.next();
					            MultipartFile mFile = mhsr.getFile(fileName);
					            String originalFileName = mFile.getOriginalFilename();
					            fileList.add(originalFileName);

					            File file = new File(uploadPathDir + originalFileName);

					            if(mFile.getSize() != 0) {
									if(file.getParentFile().mkdirs()) {
				                        file.createNewFile();
				                    }
					                mFile.transferTo(file);
					            }
					        }

							if(type.equals("산사태") || type.equals("토석류")) {
								if(comptAt > 0 ){
									extFieldBookService.updateCnrsSpceItem(itemVO);
								} else {
									extFieldBookService.insertCnrsSpceItem(itemVO);
								}
							} else if (type.contains("땅밀림")) {
								// 땅밀림 판정표값 조회
								int totScore = 0;
								String memo = "["+itemVO.getMEMO().toString()+"]";
								JSONArray lcpMemo = new JSONArray(memo);
								JSONObject lcpScore = new JSONObject();
								
								
								ComDefaultCodeVO comVO = new ComDefaultCodeVO();
								String score;
								Boolean passAt = false;
								if(lcpMemo.getJSONObject(0).has("땅밀림.패스사유") && !(lcpMemo.getJSONObject(0).isNull("땅밀림.패스사유"))) {
									String passResn = lcpMemo.getJSONObject(0).getString("땅밀림.패스사유");
									if(passResn.equals("해당없음")) passAt = true;
								}else {
									passAt = true;
								}
								
								if(passAt) {
									//직접징후
									int crk = lcpMemo.getJSONObject(0).get("땅밀림현황.균열") != null ? lcpMemo.getJSONObject(0).get("땅밀림현황.균열").toString().length() : 0;
									int stp = lcpMemo.getJSONObject(0).get("땅밀림현황.단차") != null ? lcpMemo.getJSONObject(0).get("땅밀림현황.단차").toString().length() : 0;
									if(crk > 2 || stp > 2) {
										totScore = totScore + 22;
										lcpScore.put("직접징후", "유");
										lcpScore.put("직접징후.점수", "22");
									}else {
										lcpScore.put("직접징후", "무");
										lcpScore.put("직접징후.점수", "0");
									}
									
									//간접징후
									int wdpt = lcpMemo.getJSONObject(0).get("땅밀림현황.수목이상생장") != null ? lcpMemo.getJSONObject(0).get("땅밀림현황.수목이상생장").toString().length() : 0;
									int strct = lcpMemo.getJSONObject(0).get("땅밀림현황.구조물이상") != null ? lcpMemo.getJSONObject(0).get("땅밀림현황.구조물이상").toString().length() : 0;
									int ugrwtr = lcpMemo.getJSONObject(0).get("땅밀림현황.지하수용출") != null ? lcpMemo.getJSONObject(0).get("땅밀림현황.지하수용출").toString().length() : 0;
									if(wdpt > 2 || strct > 2 || ugrwtr > 2) {
										totScore = totScore + 14;
										lcpScore.put("간접징후", "유");
										lcpScore.put("간접징후.점수", "14");
									}else {
										lcpScore.put("간접징후", "무");
										lcpScore.put("간접징후.점수", "0");
									}
									
									//징후발생여부
									if(crk > 2 || stp > 2 || wdpt > 2 || strct > 2 || ugrwtr > 2) {
										lcpScore.put("땅밀림현황.징후발생여부", "유");
									}else {
										lcpScore.put("땅밀림현황.징후발생여부", "무");							
									}
									
									//주구성암석
									comVO.setCodeId("FEI048");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지질특성.주구성암석") != null ? lcpMemo.getJSONObject(0).get("지질특성.주구성암석").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지질특성.주구성암석.점수", score);
									
									//암석풍화
									comVO.setCodeId("FEI049");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지질특성.암석풍화") != null ? lcpMemo.getJSONObject(0).get("지질특성.암석풍화").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지질특성.암석풍화.점수", score);
									
									//불연속면방향성
									comVO.setCodeId("FEI050");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지질특성.불연속면방향성") != null ? lcpMemo.getJSONObject(0).get("지질특성.불연속면방향성").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지질특성.불연속면방향성.점수", score);
									
									//불연속면간격
			//						comVO.setCodeId("FEI051");
			//						comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지질특성.불연속면간격").toString());
			//						score = cmmUseService.selectLcpCodeDetail(comVO);
			//						if(score != null) totScore = totScore + Integer.parseInt(score);
			//						lcpScore.put("지질특성.불연속면간격.점수", score);
									
									//토성
									comVO.setCodeId("FEI052");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("토양특성.토성") != null ? lcpMemo.getJSONObject(0).get("토양특성.토성").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("토양특성.토성.점수", score);
									
									//토양수분
									comVO.setCodeId("FEI053");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("토양특성.토양수분") != null ? lcpMemo.getJSONObject(0).get("토양특성.토양수분").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("토양특성.토양수분.점수", score);
									
									//너덜유무
									comVO.setCodeId("FEI054");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("토양특성.너덜유무") != null ? lcpMemo.getJSONObject(0).get("토양특성.너덜유무").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("토양특성.너덜유무.점수", score);
									
									//토심판정값
									comVO.setCodeId("FEI055");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("토양특성.토심판정값") != null ? lcpMemo.getJSONObject(0).get("토양특성.토심판정값").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("토양특성.토심판정값.점수", score);
									
									//지형구분
									comVO.setCodeId("FEI056");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지형특성.지형구분") != null ? lcpMemo.getJSONObject(0).get("지형특성.지형구분").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지형특성.지형구분.점수", score);
									
									//지형형태(평면형)
									comVO.setCodeId("FEI057");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지형특성.평면형") != null ? lcpMemo.getJSONObject(0).get("지형특성.평면형").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지형특성.평면형.점수", score);
									
									//지형형태(종단면형)
									comVO.setCodeId("FEI057");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지형특성.종단면형") != null ? lcpMemo.getJSONObject(0).get("지형특성.종단면형").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지형특성.종단면형.점수", score);
									
									//경사도
									comVO.setCodeId("FEI059");
									comVO.setCodeDc(lcpMemo.getJSONObject(0).get("지형특성.경사도") != null ? lcpMemo.getJSONObject(0).get("지형특성.경사도").toString() : "");
									score = cmmUseService.selectLcpCodeDetail(comVO);
									if(score != null) totScore = totScore + Integer.parseInt(score);
									lcpScore.put("지형특성.경사도.점수", score);
									lcpScore.put("판정점수합계", totScore);
									
									if(totScore < 45) {
										lcpScore.put("판정표등급", "C");
										lcpScore.put("최종판정등급", "C");
									}else if(totScore < 65) {
										lcpScore.put("판정표등급", "B");
										lcpScore.put("최종판정등급", "B");
									}else {
										lcpScore.put("판정표등급", "A");
										lcpScore.put("최종판정등급", "A");
									}
									memo = itemVO.getMEMO().toString();
									String scoreList = lcpScore.toString();
									scoreList = ","+ scoreList.substring(1,scoreList.length());
									memo = memo.substring(0,memo.length()-1) + scoreList;
									itemVO.setMEMO(memo);
								}
								
								if(comptAt > 0 ){
									extFieldBookService.updateCnrsSpceItem(itemVO);
								} else {
									extFieldBookService.insertCnrsSpceItem(itemVO);
								}
								
							} else if(type.contains("외관점검")) {
								if(rowList.isEmpty()) {
									sn = extFieldBookService.selectAprCnrsSpceFieldMvl(Integer.parseInt(mst_id));
									String memo = itemVO.getMEMO().toString();
									JSONObject aprSn = new JSONObject();
									aprSn.put("일련번호", sn);

									String aprList = aprSn.toString();
									aprList = ","+ aprList.substring(1,aprList.length());
									memo = memo.substring(0,memo.length()-1) + aprList;
									itemVO.setMEMO(memo);
								}
								
								if(comptAt > 0 ){
									extFieldBookService.updateCnrsSpceItem(itemVO);
								} else {
									extFieldBookService.insertCnrsSpceItem(itemVO);
								}
							} else if(type.contains("취약지역 실태조사")){
								if(comptAt > 0 ){
									extFieldBookService.updateCnrsSpceItem(itemVO);
								} else {
									extFieldBookService.insertCnrsSpceItem(itemVO);
								}
							} else if(type.contains("취약지역 해제조사")){			        
								if(comptAt > 0 ){
									extFieldBookService.updateCnrsSpceItem(itemVO);
								} else {
									extFieldBookService.insertCnrsSpceItem(itemVO);
								}
							} else if(type.contains("임도")) {
								extFieldBookService.upsertCnrsSpceItem(itemVO);
							} else if(type.contains("계측장비")) {
								extFieldBookService.upsertCnrsSpceItem(itemVO);
							} else if(type.contains("정밀점검")) {
								extFieldBookService.upsertCnrsSpceItem(itemVO);
							}
						}
						
						list = extFieldBookService.selectCnrsSpceCompList(schMap);
						for(int i=0; i<list.size(); i++) {
							JSONObject data = new JSONObject();
							data.put("_KEYWORD",list.get(i).getKEYWORD());
							data.put("_LABEL",list.get(i).getLABEL());
							data.put("_DATA",list.get(i).getDATA());
							data.put("_LAT",list.get(i).getLAT());
							data.put("_LON",list.get(i).getLON());
							data.put("_MEMO",list.get(i).getMEMO());
							data.put("_REG_DATE",list.get(i).getREG_DATE());
							data.put("_UPD_DATE",list.get(i).getUPD_DATE());
							data.put("_STYLE",list.get(i).getSTYLE());
							data.put("_TAG1",list.get(i).getTAG1());
							data.put("_TAG2",list.get(i).getTAG2());
							data.put("ATTR",list.get(i).getATTR());
							jsonArr.put(data);
						}
						
						if(itemVO.getSE().equals("BSC") || itemVO.getSE().equals("APR")) {
							schMap.put("type", type); // 조사유형
							schMap.put("gid", itemVO.getGid()); //고유아이디
							schMap.put("path", uploadPathDir); //저장경로
							schMap.put("midPath",uploadMidPathDir);//중간저장경로
							schMap.put("mapType", "SATELLITE"); //지도유형
							
							LOGGER.info("====== 전자야장 저장 파라메터 ======");
							LOGGER.info("Parameters : "+schMap.toString());
							LOGGER.info("=============================");
			               
							LOGGER.info("====== 위치도 생성 시작 ======");
							extFieldBookService.updateComptLcMap(schMap); //위치도 저장처리
							LOGGER.info("====== 위치도 생성 종료 ======");
						}
			        }

               		header.put("UPDATE_TIME", upd_date);
					error.put("code", "0000");
					error.put("message", "");
					result.put("header", header);
					result.put("error", error);
					result.put("result", jsonArr);
					
					LOGGER.info("전자야장 저장 후 전송 건수 : "+(list != null ? list.size() : 0));
					LOGGER.info(result.toString());
					LOGGER.info("=============================");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			error.put("code", "5000");
			error.put("message", e.getMessage());
			result.put("error", error);
		} finally {
			out.print(result);
		}
		long end = System.currentTimeMillis();
		LOGGER.info("("+label_no+") - "+"Execution Time : "+(end-start)/1000.0+"ss");
	}
	
	/**
	 * 전자야장 공유방 동기화
	 * @param mst_id
	 * @param last_update
	 * @param model
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value = "/ext/lss/bsc/selectCnrsSpceSync.do")
	public void selectCnrsSpceSync(
			@RequestParam(value="mst_id") String mst_id,
			@RequestParam(value="last_update", defaultValue="") String last_update, 
			Model model,
			HttpServletResponse res) throws Exception {
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		JSONObject header = new JSONObject();
		JSONObject result = new JSONObject();
		JSONObject error = new JSONObject();
		HashMap<String, Object> schMap = new HashMap<>();
		List<ExtFieldBookItemVO> list = null;
		
		Date date = new Date();
		String upd_date = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date));
		
		int mstAt = mst_id.equals("") ? 0 : extFieldBookService.selectCnrsSpceAt(Integer.parseInt(mst_id));
		
		try {

			if(Integer.toString(mstAt).equals("0")) {
				error.put("code", "2001");
				error.put("message", "선택한 공유방이 없습니다.");
				result.put("error", error);
				result.put("result", "");
			} else {
				schMap.put("mst_id", Integer.parseInt(mst_id));
				schMap.put("last_update", last_update);
				list = extFieldBookService.selectCnrsSpceSyncItem(schMap);
				JSONArray jsonArr = new JSONArray();
				for(int i=0; i<list.size(); i++) {
					JSONObject data= new JSONObject();
//					data.put("_FID", Integer.toString(list.get(i).getFID()) == null ? "" : list.get(i).getFID());
					data.put("_KEYWORD",list.get(i).getKEYWORD() == null ? "" : list.get(i).getKEYWORD());
					data.put("_LABEL",list.get(i).getLABEL() == null ? "" : list.get(i).getLABEL());
					data.put("_DATA",list.get(i).getDATA() == null ? "" : list.get(i).getDATA());
					data.put("_LAT",list.get(i).getLAT() == null ? "" : list.get(i).getLAT());
					data.put("_LON",list.get(i).getLON() == null ? "" : list.get(i).getLON());
					data.put("_MEMO",list.get(i).getMEMO() == null ? "" : list.get(i).getMEMO());
					data.put("_REG_DATE",list.get(i).getREG_DATE() != null ? list.get(i).getREG_DATE() : "");
					data.put("_UPD_DATE",list.get(i).getUPD_DATE() != null ? list.get(i).getUPD_DATE() : "");
					data.put("_STYLE",list.get(i).getSTYLE() == null ? "" : list.get(i).getSTYLE());
					data.put("_TAG1",list.get(i).getTAG1() == null ? "" : list.get(i).getTAG1());
					data.put("_TAG2",list.get(i).getTAG2() == null ? "" : list.get(i).getTAG2());
					data.put("ATTR",list.get(i).getATTR() == null ? "" : list.get(i).getATTR());
					jsonArr.put(data);
				}
				
				header.put("UPDATE_TIME", upd_date);
				error.put("code", "0000");
				error.put("message", "");
				result.put("header",header);
				result.put("error",error);
				result.put("result",jsonArr);
				
				LOGGER.info("================ 전자야장 동기화 정보 =====================");
				
				LOGGER.info("공유방ID : ".concat(mst_id));
				LOGGER.info("last_update : ".concat(last_update));
				
				LOGGER.info("UPDATE_TIME : ".concat(upd_date));
				LOGGER.info("result : ".concat(result.toString()));
				
				LOGGER.info("==================================================");
				
			}
		} catch(Exception e) {
			error.put("code", "5000");
			error.put("message", e.getMessage());
			LOGGER.error(e.getMessage());
		} finally {
			out.print(result);
		}
	}
	
	//사진생성 샘플
	@RequestMapping(value = "/ext/createPhoto.do")
	public ModelAndView createPhoto(Model model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
//		int gid = 21114;
//		String tp = "산사태";
//		String lb = "S4183_32136";
//		String se = "BSC";
//		String mstpath = "한국치산기술협회-기초조사-북부청2";
		int gid = 1097;
		String tp = "사방댐 외관점검";
		String lb = "D12GW-CC0011";
		String se = "APR";
		String mstpath = "한국치산기술협회-춘천국유림관리소 외관점검 (사방댐)";
		HashMap<String, Object> schMap = new HashMap<String, Object>();

		schMap.put("gid", gid);
		schMap.put("type", tp);
		schMap.put("path", fieldBookUploadPath.concat("/").concat(mstpath).concat(".ncx/"));
		schMap.put("_label", lb);
		schMap.put("SE", se);
		try {
			extFieldBookService.updateComptLcMap(schMap);
			mv.addObject("status",200);
			mv.addObject("message","success");
		} catch (Exception e) {
			mv.addObject("status",400);
			mv.addObject("message",e.getMessage());
		}
		
		return mv;
	}
}
