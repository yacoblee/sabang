package or.sabang.sys.fck.mse.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.fck.mse.service.FckMseFieldBookItemVO;
import or.sabang.sys.fck.mse.service.FckMseFieldBookService;
import or.sabang.sys.fck.mse.service.FckMseFieldBookVO;
import or.sabang.sys.fck.mse.service.FckMseStripLandVO;
import or.sabang.sys.service.SysComptVO;
import or.sabang.utl.ExcelUtils;

@Service("fckMseFieldBookService")
public class FckMseFieldBookServiceImpl extends EgovAbstractServiceImpl implements FckMseFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FckMseFieldBookServiceImpl.class);
	
	@Resource(name="fckMseFieldBookDAO")
	private FckMseFieldBookDAO fckMseFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<FckMseFieldBookVO> selectFckMseFieldBookList(FckMseFieldBookVO searchVO) throws Exception {		
		return fckMseFieldBookDAO.selectFckMseFieldBookList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectFckMseFieldBookListTotCnt(FckMseFieldBookVO searchVO) throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookListTotCnt(searchVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@Override
	public FckMseFieldBookVO selectFckMseFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookDetail(map);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<FckMseStripLandVO> selectFckMseFieldBookItemList(FckMseFieldBookItemVO searchVO) throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectFckMseFieldBookItemListTotCnt(FckMseFieldBookItemVO searchVO) throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookItemListTotCnt(searchVO);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@Override
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception {
		return fckMseFieldBookDAO.selectCnrsSpcePwd(map);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@Override
	public List<FckMseFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception {
		return fckMseFieldBookDAO.selectCnrsSpceList(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@Override
//	public FckMseFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception {
//		return fckMseFieldBookDAO.selectCnrsSpceInfo(map);
//	}

	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	@Override
//	public int selectCnrsSpceAt(int mst_id) throws Exception {
//		return fckMseFieldBookDAO.selectCnrsSpceAt(mst_id);
//	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
//	@Override
//	public List<FckMseFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception {
//		return fckMseFieldBookDAO.selectCnrsSpceDownload(map);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 조회 테스트
	 */
//	@Override
//	public List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception {
//		return fckMseFieldBookDAO.selectCnrsSpceItem(map);
//	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
	 */
//	@Override
//	public int selectCnrsSpceMvl(int mst_id) throws Exception {
//		return fckMseFieldBookDAO.selectCnrsSpceMvl(mst_id);
//	}
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
//	public void insertCnrsSpceItem(FckMseFieldBookItemVO itemVO) throws Exception{
//		fckMseFieldBookDAO.insertCnrsSpceItem(itemVO);
//	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
//	public void updateCnrsSpceItem(FckMseFieldBookItemVO itemVO) throws Exception{
//		fckMseFieldBookDAO.updateCnrsSpceItem(itemVO);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	@Override
//	public List<FckMseFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception {
//		return fckMseFieldBookDAO.selectCnrsSpceCompItem(map);
//	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(FckMseFieldBookVO fieldBookVO) throws Exception {
		fckMseFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	public void insertCnrsSpceAuthorMgt(FckMseFieldBookVO fieldBookVO) throws Exception {
		List<FckMseFieldBookVO> list = new ArrayList<FckMseFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();

		if(authorEsntlIdList != null) {
			for(int i =0; i<authorEsntlIdList.length;i++) {
				if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
					FckMseFieldBookVO vo = new FckMseFieldBookVO();
					vo.setId(fieldBookVO.getId());
					vo.setSvytype(fieldBookVO.getSvytype());
					vo.setEsntl_id(authorEsntlIdList[i].toString());
					vo.setUser_grade("USER");
					list.add(vo);				
				}
			}			
		}
		
		// 생성자 추가
		FckMseFieldBookVO vo = new FckMseFieldBookVO();
		vo.setId(fieldBookVO.getId());
		vo.setSvytype(fieldBookVO.getSvytype());
		vo.setEsntl_id(fieldBookVO.getMst_admin_id());
		vo.setUser_grade("ADMIN");
		list.add(vo);
		
		fckMseFieldBookDAO.insertCnrsSpceAuthorMgt(list);
	}
	
	/**
	 * 대상지를 일괄등록한다.
	 */
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(FckMseFieldBookVO fieldBookVO) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		JSONObject attrItem = null;
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		String userNm ="";
		
		if(fieldBookVO.getMst_create_user() == null) {
			userNm = user.getName();
		}else {
			userNm = fieldBookVO.getMst_create_user();
		}

		List<EgovMap> fieldBookList = new ArrayList<EgovMap>();
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;
		int cnt = 0;
		
		fieldBookList.addAll(fckMseFieldBookDAO.selectMseSldListAddr(fieldBookVO));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date_str = formatter.format(calendar.getTime());
		
		
		if(fieldBookList.size() > 0) {
			
			JSONArray attr = new JSONArray();
			SysComptVO vo = new SysComptVO();
			
    		//메모
    	    JSONObject memo = new JSONObject();
    	    for(int i=0;i<fieldBookList.size();i++) {
    	    	attr = new JSONArray();
    	    	vo = new SysComptVO();
    	    	
    	    	vo.setMst_id(Integer.valueOf(fieldBookVO.getId()));
    			vo.setLogin_id(userNm);
    			
	    		// 계측기 ID
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", fieldBookList.get(i).get("sldId").toString());
	    		attrItem.put("NAME","계측기ID");
	    		attr.put(attrItem);
	    		
	    		// 조사ID
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", fieldBookList.get(i).get("sldId").toString());
	    		attrItem.put("NAME","조사ID");
	    		attr.put(attrItem);
	    		
	    		//조사단계
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", fieldBookVO.getSvy_step().toString());
	    		attrItem.put("NAME","조사단계");
	    		attr.put(attrItem);	    		
	    		
	    		//조사연도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
	    		attrItem.put("NAME","조사연도");
	    		attr.put(attrItem);
	    		
	    		//시도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("sd") != null ? fieldBookList.get(i).get("sd").toString() : ""));
	    		attrItem.put("NAME","시도");
	    		attr.put(attrItem);
	    		
	    		//시군구
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("sgg") != null ? fieldBookList.get(i).get("sgg").toString() : ""));
	    		attrItem.put("NAME","시군구");
	    		attr.put(attrItem);
	    		
	    		//읍면동
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("emd") != null ? fieldBookList.get(i).get("emd").toString() : ""));
	    		attrItem.put("NAME","읍면동");
	    		attr.put(attrItem);
	    		
	    		//리
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("ri") != null ? fieldBookList.get(i).get("ri").toString() : ""));
	    		attrItem.put("NAME","리");
	    		attr.put(attrItem);
	    		
	    		//지번
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("jibun") != null ? fieldBookList.get(i).get("jibun").toString() : ""));
	    		attrItem.put("NAME","지번");
	    		attr.put(attrItem);
	    		
	    		//소유구분
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("owner") != null ? fieldBookList.get(i).get("owner").toString() : ""));
	    		attrItem.put("NAME","소유구분");
	    		attr.put(attrItem);
	    		
	    		//법적제한사항
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("lglLimit") != null ? fieldBookList.get(i).get("lglLimit").toString() : ""));
	    		attrItem.put("NAME","법적제한사항");
	    		attr.put(attrItem);
	    		
	    		//조사유형
	        	attrItem = new JSONObject();
	    		attrItem.put("VALUE", "계측장비 일반사항");
	    		attrItem.put("NAME","조사유형");
	    		attr.put(attrItem);
	    		
				//계측기ID
				memo.put("계측기ID", fieldBookList.get(i).get("sldId").toString());
				//조사단계
				memo.put("조사단계", fieldBookVO.getSvy_step().toString());
				//소유구분
				memo.put("소유구분", (fieldBookList.get(i).get("owner") != null ? fieldBookList.get(i).get("owner").toString() : ""));
				//법적제한사항
				memo.put("법적제한사항", (fieldBookList.get(i).get("lglLimit") != null ? fieldBookList.get(i).get("lglLimit").toString() : ""));
				
				vo.setSvy_attr(attr.toString());
				vo.setSvy_label(fieldBookList.get(i).get("sldId").toString());
				vo.setSvy_keyword(fieldBookList.get(i).get("sldId").toString());
				vo.setSvy_step(fieldBookVO.getSvy_step().toString());
				vo.setSvy_memo(memo.toString());
				
				vo.setSvy_lat(Double.parseDouble(fieldBookList.get(i).get("svyLat").toString()));
				vo.setSvy_lon(Double.parseDouble(fieldBookList.get(i).get("svyLon").toString()));
				vo.setSmgeometry(fieldBookList.get(i).get("svyData").toString());
				vo.setCreat_dt(date_str);
	    	    vo.setLast_updt_pnttm(date_str);
	    	    vo.setSvy_tag1("일반사항");
	    	    cnt++;
				try {
					fckMseFieldBookDAO.insertStripLandVO(vo);
					successCnt++;
				} catch (Exception e) {
					failedIdsArray.put(vo.getSvy_label());
					returnInsertLog.put("error", e.getMessage());
					LOGGER.error(e.getMessage());
				}
    		}
			for(int i=0;i<fieldBookList.size();i++) {
				attr = new JSONArray();
				vo = new SysComptVO();

				vo.setMst_id(Integer.valueOf(fieldBookVO.getId()));
	    		vo.setLogin_id(userNm);
	    		
	    		//메모
	    	    memo = new JSONObject();
	    		
	    		// 계측기 ID
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", fieldBookList.get(i).get("sldId").toString());
	    		attrItem.put("NAME","계측기ID");
	    		attr.put(attrItem);
	    		
	    		//조사유형
	        	attrItem = new JSONObject();
	    		attrItem.put("VALUE", "계측장비");
	    		attrItem.put("NAME","조사유형");
	    		attr.put(attrItem);
	    		
	    		//조사연도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
	    		attrItem.put("NAME","조사연도");
	    		attr.put(attrItem);
	    		
	    		//시도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("sd") != null ? fieldBookList.get(i).get("sd").toString() : ""));
	    		attrItem.put("NAME","시도");
	    		attr.put(attrItem);
	    		
	    		//시군구
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("sgg") != null ? fieldBookList.get(i).get("sgg").toString() : ""));
	    		attrItem.put("NAME","시군구");
	    		attr.put(attrItem);
	    		
	    		//읍면동
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("emd") != null ? fieldBookList.get(i).get("emd").toString() : ""));
	    		attrItem.put("NAME","읍면동");
	    		attr.put(attrItem);
	    		
	    		//리
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("ri") != null ? fieldBookList.get(i).get("ri").toString() : ""));
	    		attrItem.put("NAME","리");
	    		attr.put(attrItem);
	    		
	    		//지번
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("jibun") != null ? fieldBookList.get(i).get("jibun").toString() : ""));
	    		attrItem.put("NAME","지번");
	    		attr.put(attrItem);
	    		
	    		//소유구분
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("owner") != null ? fieldBookList.get(i).get("owner").toString() : ""));
	    		attrItem.put("NAME","소유구분");
	    		attr.put(attrItem);
	    		
	    		//법적제한사항
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", (fieldBookList.get(i).get("lglLimit") != null ? fieldBookList.get(i).get("lglLimit").toString() : ""));
	    		attrItem.put("NAME","법적제한사항");
	    		attr.put(attrItem);
	    						
	    		//조사단계
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", fieldBookVO.getSvy_step().toString());
	    		attrItem.put("NAME","조사단계");
	    		attr.put(attrItem);	  
	    		
				//계측장비
				String[] msSensorList = new String[]{"wireExt","licMeter","gwGauge","rainGauge","strcDpm","surDpm","gpsGauge","gateway","node"};
				String[] msSensorNmList = new String[]{"와이어신축계","지중경사계","지하수위계","강우계","구조물변위계","지표변위계","GPS변위계","게이트웨이","노드"};
				for(int j =0; j<msSensorList.length; j++) {
					if(fieldBookList.get(i).get(msSensorList[j]) != null) {
						String msSensorVal = fieldBookList.get(i).get(msSensorList[j]).toString();
						int msSensorCnt = (int) fieldBookList.get(i).get(msSensorList[j]+"Cnt");
						JSONArray val = new JSONArray(new JSONTokener(msSensorVal));
						JSONObject channelArray = new JSONObject();
						
						for (int k = 0; k < msSensorCnt; k++) {
							memo = new JSONObject();
							channelArray = new JSONObject();
							
							//지중경사계
							if(msSensorList[j].equals("licMeter")) {								
								//지중경사계 성능점검
								String licMeterPerVal = fieldBookList.get(i).get(msSensorList[j]+"Per").toString();
								JSONArray licMeterVal = new JSONArray(new JSONTokener(licMeterPerVal));
								JSONArray licMeterArr = new JSONArray();
								JSONObject licMeter = new JSONObject();
								for(int x=0; x<licMeterVal.length(); x++) {
									String liccnlNm = licMeterVal.getJSONObject(x).getString("채널명");
									if(liccnlNm.startsWith(val.getJSONObject(k).getString("채널명"))) {
										licMeterArr.put(licMeterVal.getJSONObject(x));
									}
								}
								licMeter.put("성능점검", licMeterArr);
								licMeter.put("채널명",val.getJSONObject(k).getString("채널명"));
								memo.put(msSensorNmList[j], licMeter);
							}else{
								if(msSensorList[j].equals("gateway")) {
									channelArray.put("연번",val.getJSONObject(k).getString("연번"));
									channelArray.put("양호전압","10~12V");
									
								}else if(msSensorList[j].equals("node")) {
									channelArray.put("연번",val.getJSONObject(k).getString("연번"));
									channelArray.put("양호전압 1","10~12V");
									channelArray.put("양호전압 2","3.0~3.6V");
								}else {
									channelArray.put("채널명",val.getJSONObject(k).getString("채널명"));									
								}
								memo.put(msSensorNmList[j], channelArray);
							}
							//계측기ID
							memo.put("계측기ID", fieldBookList.get(i).get("sldId").toString());
				    		//장비유형
							memo.put("장비유형", msSensorNmList[j]);
							//조사단계
							memo.put("조사단계", fieldBookVO.getSvy_step().toString());
							//소유구분
							memo.put("소유구분", (fieldBookList.get(i).get("owner") != null ? fieldBookList.get(i).get("owner").toString() : ""));
							//법적제한사항
							memo.put("법적제한사항", (fieldBookList.get(i).get("lglLimit") != null ? fieldBookList.get(i).get("lglLimit").toString() : ""));
							
							//조사ID
							String label = fieldBookList.get(i).get("sldId").toString();
							if(msSensorList[j].equals("gateway") || msSensorList[j].equals("node")) {
								label = label+ "_"+ msSensorList[j] + val.getJSONObject(k).getString("연번");
							}else {
								label = label+"_" + val.getJSONObject(k).getString("채널명");
							}
							
							JSONArray attr2 = new JSONArray();
				        	attrItem = new JSONObject();
				    		attrItem.put("VALUE", label);
				    		attrItem.put("NAME","조사ID");
				    		
				    		attr2.put(attrItem);
				    		attr2.putAll(attr);
							
							vo.setSvy_attr(attr2.toString());
							vo.setSvy_label(label);
							vo.setSvy_keyword(label);
							vo.setSvy_step(fieldBookVO.getSvy_step().toString());
							vo.setSvy_memo(memo.toString());
							
							vo.setSvy_lat(Double.parseDouble(fieldBookList.get(i).get("svyLat").toString()));
							vo.setSvy_lon(Double.parseDouble(fieldBookList.get(i).get("svyLon").toString()));
							vo.setSmgeometry(fieldBookList.get(i).get("svyData").toString());
							vo.setCreat_dt(date_str);
				    	    vo.setLast_updt_pnttm(date_str);
				    	    vo.setSvy_tag1(msSensorNmList[j]);
							cnt++;
							try {
								fckMseFieldBookDAO.insertStripLandVO(vo);
								successCnt++;
							} catch (Exception e) {
								failedIdsArray.put(vo.getSvy_label());
								returnInsertLog.put("error", e.getMessage());
								LOGGER.error(e.getMessage());
							}
						};
					}
				}	    	   
			}
		}
		
		
//		for (SysComptVO subVo : list) {
//			try {
//				fckMseFieldBookDAO.insertStripLandVO(subVo);
//				successCnt++;
//			} catch (Exception e) {
//				failedIdsArray.put(subVo.getSvy_label());
//				returnInsertLog.put("error", e.getMessage());
//				LOGGER.error(e.getMessage());
//			}
//		}
		
		returnInsertLog.put("total", cnt);
		returnInsertLog.put("successCnt", successCnt);
		returnInsertLog.put("failedIds", failedIdsArray);
		return returnInsertLog;
	}
	
	/**
	 * @param stripLandVO
	 * @return
	 * @description 대상지 목록조회
	 */
	@Override
	public List<FckMseStripLandVO> selectCnrsSpceSldList(FckMseStripLandVO stripLandVO) throws Exception {
		return fckMseFieldBookDAO.selectCnrsSpceSldList(stripLandVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<FckMseStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return fckMseFieldBookDAO.selectCnrsSpceSldDetail(id);
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = fckMseFieldBookDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
//	@Override
//	public void insertCnrsSpceSld(FckMseFieldBookItemVO itemVO) throws Exception {
//		fckMseFieldBookDAO.insertCnrsSpceSld(itemVO);
//	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<FckMseStripLandVO> selectFckMseFieldBookItemView(FckMseFieldBookItemVO searchVO) throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookItemView(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> map) throws Exception {
		fckMseFieldBookDAO.deleteCnrsSpce(map);
	}

	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception {
		fckMseFieldBookDAO.deleteCnrsSpceItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception {
		fckMseFieldBookDAO.deleteCnrsSpceAllItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception {
		fckMseFieldBookDAO.deleteCnrsSpceComptItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	public int selectUpsertAt(FckMseFieldBookItemVO searchVO) throws Exception {
//		return fckMseFieldBookDAO.selectUpsertAt(searchVO);
//	}
	
	/**
	 * 외관점검 조사정보 XSSF
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private FckMseFieldBookItemVO convertXSSFCellValues(String mType,XSSFRow row,FckMseFieldBookItemVO vo) throws Exception {
		//FckMseStripLand vo = new FckMseStripLand();
		JSONArray attr = new JSONArray();
		JSONObject attrItem = null;
		JSONObject memo = new JSONObject();
		
		Calendar calendar = Calendar.getInstance();
		
		//memo.put("조사ID", vo.getId());
		memo.put("조사유형", mType.concat(" 외관점검"));
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		XSSFCell cell = null;
		
		/* 220226 수정 */
		cell = row.getCell(0);  //일련번호
    	if(cell!=null){
    		String fid = ExcelUtils.getCellValue(cell);
    		if(fid.equals("")) {
    			return vo;
    		}
    		memo.put("일련번호", fid);
    		vo.setFID(Integer.parseInt(fid));
    	}else {
    		return vo;
    	}
    	
    	cell = row.getCell(1);  //조사ID
    	if(cell!=null){
    		String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		memo.put("조사ID", svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    		
    		vo.setKEYWORD(ExcelUtils.getCellValue(cell));
    		vo.setLABEL(ExcelUtils.getCellValue(cell));
    	}else {
    		return vo;
    	}
    	
    	attrItem = new JSONObject();
		attrItem.put("VALUE", mType.concat(" 외관점검"));
		attrItem.put("NAME","조사유형");
		attr.put(attrItem);
    	
		//String year = ExcelUtils.getCellValue(cell);
		attrItem = new JSONObject();
		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
		attrItem.put("NAME","조사연도");
		attr.put(attrItem);
		
    	cell = row.getCell(2);  //시설년도
    	if(cell!=null){memo.put("시설제원_년도", ExcelUtils.getCellValue(cell));}
    	
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
    		attrItem.put("NAME","JIBUN");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","JIBUN");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(8);  //속칭
    	if(cell!=null){memo.put("속칭", ExcelUtils.getCellValue(cell));}
    	
		if(mType.equals("사방댐")) {
			cell = row.getCell(9);  //종류
	    	if(cell!=null){memo.put("종류", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(10);  //형식
	    	if(cell!=null){memo.put("형식", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(11);  //시설제원_상장
	    	if(cell!=null){memo.put("시설제원_상장", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(12);  //시설제원_하장
	    	if(cell!=null){memo.put("시설제원_하장", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(13);  //시설제원_유효고
	    	if(cell!=null){memo.put("시설제원_유효고", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(14);  //점검일시
	    	if(cell!=null){
	    		String dt = ExcelUtils.getCellValue(cell).replace(".", "-");
	    		memo.put("점검일시", dt.equals("") ? dt : dt.substring(0, dt.length() - 1));
	    	}
	    	cell = row.getCell(15);  //조사자
	    	if(cell!=null){memo.put("조사자", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(16);  //사방댐관리번호
	    	if(cell!=null){memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(17);  //국가지점번호
	    	if(cell!=null){memo.put("국가지점번호", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(18);  //위도
	    	if(cell!=null){memo.put("위도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(19);  //위분
	    	if(cell!=null){memo.put("위분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(20);  //위초
	    	if(cell!=null){memo.put("위초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(21);  //경도
	    	if(cell!=null){memo.put("경도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(22);  //경분
	    	if(cell!=null){memo.put("경분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(23);  //경초
	    	if(cell!=null){memo.put("경초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(24);  //본댐 측정
	    	if(cell!=null){memo.put("본댐_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(25);  //측벽 측정
	    	if(cell!=null){memo.put("측벽_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(26);  //물받이 측정
	    	if(cell!=null){memo.put("물받이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(27);  //본댐
	    	if(cell!=null){memo.put("본댐_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(28);  //측벽
	    	if(cell!=null){memo.put("측벽_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(29);  //물받이
	    	if(cell!=null){memo.put("물받이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(30);  //수문
	    	if(cell!=null){memo.put("수문_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(31);  //식생상태
	    	if(cell!=null){memo.put("식생상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(32);  //안전시설
	    	if(cell!=null){memo.put("안전시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(33);  //접근도로
	    	if(cell!=null){memo.put("접근도로_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(34);  //기타
	    	if(cell!=null){memo.put("기타_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(35);  //저사상태
	    	
	    	if(cell!=null){
	    		String type = ExcelUtils.getCellValue(cell);
	    		if(type.contains("저")) {
	    			type = "저(50% 미만)";
	    		}else if(type.contains("중")) {
	    			type = "중(50%~80% 미만)";
	    		}else if(type.contains("고")) {
	    			type = "고(80% 이상)";
	    		}
	    		memo.put("저사상태_판정값", type);
	    		}
	    	cell = row.getCell(36);  //저사량
	    	if(cell!=null){memo.put("저사상태_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(37);  //최종점검결과
	    	if(cell!=null){memo.put("최종점검결과", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(38);  //조치사항
	    	if(cell!=null){memo.put("조치사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(39);  //지정해제
	    	if(cell!=null){memo.put("지정해제", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(40);  //종합의견_1
	    	if(cell!=null){memo.put("종합의견1", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(41);  //종합의견_2
	    	if(cell!=null){memo.put("종합의견2", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(42);  //종합의견_3
	    	if(cell!=null){memo.put("종합의견3", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(43);  //종합의견_4
	    	if(cell!=null){memo.put("종합의견4", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(44);  //종합의견_5
	    	if(cell!=null){memo.put("종합의견5", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(45);  //주요시설_특이사항
	    	if(cell!=null){memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(46);  //부대시설_특이사항
	    	if(cell!=null){memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(47);  //기타_특이사항
	    	if(cell!=null){memo.put("기타_특이사항", ExcelUtils.getCellValue(cell));}
	    	
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
		}else if(mType.equals("계류보전")) {
			cell = row.getCell(9);  //지정면적
	    	if(cell!=null){memo.put("지정면적", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(10);  //점검일시
	    	if(cell!=null){
	    		String dt = ExcelUtils.getCellValue(cell).replace(".", "-");
	    		memo.put("점검일시", dt.equals("") ? dt : dt.substring(0, dt.length() - 1));
	    	}
	    	cell = row.getCell(11);  //점검자
	    	if(cell!=null){memo.put("점검자", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(12);  //시점위도
	    	if(cell!=null){memo.put("시점위도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(13);  //시점위분
	    	if(cell!=null){memo.put("시점위분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(14);  //시점위초
	    	if(cell!=null){memo.put("시점위초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(15);  //시점경도
	    	if(cell!=null){memo.put("시점경도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(16);  //시점경분
	    	if(cell!=null){memo.put("시점경분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(17);  //시점경초
	    	if(cell!=null){memo.put("시점경초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(18);  //종점위도
	    	if(cell!=null){memo.put("종점위도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(19);  //종점위분
	    	if(cell!=null){memo.put("종점위분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(20);  //종점위초
	    	if(cell!=null){memo.put("종점위초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(21);  //종점경도
	    	if(cell!=null){memo.put("종점경도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(22);  //종점경분
	    	if(cell!=null){memo.put("종점경분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(23);  //종점경초
	    	if(cell!=null){memo.put("종점경초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(24);  //시설물종류
	    	if(cell!=null){memo.put("종류", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(25);  //시설제원_길이
	    	if(cell!=null){memo.put("시설제원_길이", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(26);  //시설제원_폭
	    	if(cell!=null){memo.put("시설제원_폭", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(27);  //시설제원_깊이
	    	if(cell!=null){memo.put("시설제원_깊이", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(28);  //골막이_측정값
	    	if(cell!=null){memo.put("골막이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(29);  //기슭막이_측정값
	    	if(cell!=null){memo.put("기슭막이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(30);  //바닥막이_측정값
	    	if(cell!=null){memo.put("바닥막이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(31);  //골막이_판정값
	    	if(cell!=null){memo.put("골막이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(32);  //기슭막이_판정값
	    	if(cell!=null){memo.put("기슭막이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(33);  //바닥막이_판정값
	    	if(cell!=null){memo.put("바닥막이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(34);  //계류상태_판정값
	    	if(cell!=null){memo.put("계류상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(35);  //수문_판정값
	    	if(cell!=null){memo.put("수문_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(36);  //식생상태_판정값
	    	if(cell!=null){memo.put("식생상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(37);  //안전시설_판정값
	    	if(cell!=null){memo.put("안전시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(38);  //접근도로_판정값
	    	if(cell!=null){memo.put("접근도로_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(39);  //기타_판정값
	    	if(cell!=null){memo.put("기타_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(40);  //최종점검결과
	    	if(cell!=null){memo.put("최종점검결과", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(41);  //조치사항
	    	if(cell!=null){memo.put("조치사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(42);  //지정해제
	    	if(cell!=null){memo.put("지정해제", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(43);  //종합의견_1
	    	if(cell!=null){memo.put("종합의견1", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(44);  //종합의견_2
	    	if(cell!=null){memo.put("종합의견2", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(45);  //종합의견_3
	    	if(cell!=null){memo.put("종합의견3", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(46);  //종합의견_4
	    	if(cell!=null){memo.put("종합의견4", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(47);  //종합의견_5
	    	if(cell!=null){memo.put("종합의견5", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(48);  //주요시설_특이사항
	    	if(cell!=null){memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(49);  //부대시설_특이사항
	    	if(cell!=null){memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(50);  //기타_특이사항
	    	if(cell!=null){memo.put("기타_특이사항", ExcelUtils.getCellValue(cell));}
	    	
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
		}else if(mType.equals("산지사방")) {
			cell = row.getCell(9);  //지정면적
	    	if(cell!=null){memo.put("지정면적", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(10);  //점검일시
	    	if(cell!=null){
	    		String dt = ExcelUtils.getCellValue(cell).replace(".", "-");
	    		memo.put("점검일시", dt.equals("") ? dt : dt.substring(0, dt.length() - 1));
	    	}
	    	cell = row.getCell(11);  //점검자
	    	if(cell!=null){memo.put("점검자", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(12);  //위도도
	    	if(cell!=null){memo.put("위도도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(13);  //위도분
	    	if(cell!=null){memo.put("위도분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(14);  //위도초
	    	if(cell!=null){memo.put("위도초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(15);  //경도도
	    	if(cell!=null){memo.put("경도도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(16);  //경도분
	    	if(cell!=null){memo.put("경도분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(17);  //경도초
	    	if(cell!=null){memo.put("경도초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(18);  //시설물종류
	    	if(cell!=null){memo.put("종류", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(19);  //보강시설 측정값
	    	if(cell!=null){memo.put("보강시설_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(20);  //보호시설_측정값
	    	if(cell!=null){memo.put("보호시설_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(21);  //배수시설_측정값
	    	if(cell!=null){memo.put("배수시설_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(22);  //보강시설
	    	if(cell!=null){memo.put("보강시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(23);  //보호시설_판정값
	    	if(cell!=null){memo.put("보호시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(24);  //배수시설_판정값
	    	if(cell!=null){memo.put("배수시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(25);  //사면상태_판정값
	    	if(cell!=null){memo.put("사면상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(26);  //수문_판정값
	    	if(cell!=null){memo.put("수문_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(27);  //식생상태_판정값
	    	if(cell!=null){memo.put("식생상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(28);  //안전시설_판정값
	    	if(cell!=null){memo.put("안전시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(29);  //접근도로_판정값
	    	if(cell!=null){memo.put("접근도로_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(30);  //기타_판정값
	    	if(cell!=null){memo.put("기타_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(31);  //최종점검결과
	    	if(cell!=null){memo.put("최종점검결과", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(32);  //조치사항
	    	if(cell!=null){memo.put("조치사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(33);  //지정해제
	    	if(cell!=null){memo.put("지정해제", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(34);  //종합의견_1
	    	if(cell!=null){memo.put("종합의견1", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(35);  //종합의견_2
	    	if(cell!=null){memo.put("종합의견2", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(36);  //종합의견_3
	    	if(cell!=null){memo.put("종합의견3", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(37);  //종합의견_4
	    	if(cell!=null){memo.put("종합의견4", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(38);  //종합의견_5
	    	if(cell!=null){memo.put("종합의견5", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(39);  //주요시설_특이사항
	    	if(cell!=null){memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(40);  //부대시설_특이사항
	    	if(cell!=null){memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(41);  //기타_특이사항
	    	if(cell!=null){memo.put("기타_특이사항", ExcelUtils.getCellValue(cell));}

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
		
		vo.setATTR(attr.toString());
		vo.setMEMO(memo.toString());
        vo.setLATDD(latDd);
        vo.setLONDD(lonDd);
        
		return vo;
	}
	
	/**
	 * 외관점검 조사정보 HSSF
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private FckMseFieldBookItemVO convertHSSFCellValues(String mType,HSSFRow row,FckMseFieldBookItemVO vo) throws Exception {
		//FckMseStripLand vo = new FckMseStripLand();
		JSONArray attr = new JSONArray();
		JSONObject attrItem = null;
		JSONObject memo = new JSONObject();
		
		Calendar calendar = Calendar.getInstance();
		
		memo.put("조사유형", mType.concat(" 외관점검"));
		
		double latDd = 0;
		double lonDd = 0;
		
		//int cells = row.getPhysicalNumberOfCells(); //cell 갯수 가져오기
		HSSFCell cell = null;
		
		/* 220226 수정 */
		cell = row.getCell(0);  //일련번호
    	if(cell!=null){
    		String fid = ExcelUtils.getCellValue(cell);
    		if(fid.equals("")) {
    			return vo;
    		}
    		memo.put("일련번호", fid);
    		//memo.put("FID", fid);
    		vo.setFID(Integer.parseInt(fid));
    	}
    	
    	cell = row.getCell(1);  //조사ID
    	if(cell!=null){
    		String svyId = ExcelUtils.getCellValue(cell);
    		if(svyId.equals("")) {
    			return vo;
    		}
    		memo.put("조사ID", svyId);
    		
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", svyId);
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    		
    		vo.setKEYWORD(ExcelUtils.getCellValue(cell));
    		vo.setLABEL(ExcelUtils.getCellValue(cell));
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","조사ID");
    		attr.put(attrItem);
    	}
    	
    	attrItem = new JSONObject();
		attrItem.put("VALUE", mType.concat(" 외관점검"));
		attrItem.put("NAME","조사유형");
		attr.put(attrItem);
    	
		//String year = ExcelUtils.getCellValue(cell);
		attrItem = new JSONObject();
		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
		attrItem.put("NAME","조사연도");
		attr.put(attrItem);
		
    	cell = row.getCell(2);  //시설년도
    	if(cell!=null){memo.put("시설제원_년도", ExcelUtils.getCellValue(cell));}
    	
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
    		attrItem.put("NAME","JIBUN");
    		attr.put(attrItem);
    	}else {
    		attrItem = new JSONObject();
    		attrItem.put("VALUE", "");
    		attrItem.put("NAME","JIBUN");
    		attr.put(attrItem);
    	}
    	
    	cell = row.getCell(8);  //속칭
    	if(cell!=null){memo.put("속칭", ExcelUtils.getCellValue(cell));}
    	
		if(mType.equals("사방댐")) {
			cell = row.getCell(9);  //종류
	    	if(cell!=null){memo.put("종류", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(10);  //형식
	    	if(cell!=null){memo.put("형식", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(11);  //시설제원_상장
	    	if(cell!=null){memo.put("시설제원_상장", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(12);  //시설제원_하장
	    	if(cell!=null){memo.put("시설제원_하장", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(13);  //시설제원_유효고
	    	if(cell!=null){memo.put("시설제원_유효고", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(14);  //점검일시
	    	if(cell!=null){
	    		String dt = ExcelUtils.getCellValue(cell).replace(".", "-");
	    		memo.put("점검일시", dt.equals("") ? dt : dt.substring(0, dt.length() - 1));
	    	}
	    	cell = row.getCell(15);  //조사자
	    	if(cell!=null){memo.put("조사자", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(16);  //사방댐관리번호
	    	if(cell!=null){memo.put("사방댐관리번호", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(17);  //국가지점번호
	    	if(cell!=null){memo.put("국가지점번호", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(18);  //위도
	    	if(cell!=null){memo.put("위도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(19);  //위분
	    	if(cell!=null){memo.put("위분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(20);  //위초
	    	if(cell!=null){memo.put("위초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(21);  //경도
	    	if(cell!=null){memo.put("경도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(22);  //경분
	    	if(cell!=null){memo.put("경분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(23);  //경초
	    	if(cell!=null){memo.put("경초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(24);  //본댐 측정
	    	if(cell!=null){memo.put("본댐_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(25);  //측벽 측정
	    	if(cell!=null){memo.put("측벽_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(26);  //물받이 측정
	    	if(cell!=null){memo.put("물받이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(27);  //본댐
	    	if(cell!=null){memo.put("본댐_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(28);  //측벽
	    	if(cell!=null){memo.put("측벽_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(29);  //물받이
	    	if(cell!=null){memo.put("물받이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(30);  //수문
	    	if(cell!=null){memo.put("수문_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(31);  //식생상태
	    	if(cell!=null){memo.put("식생상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(32);  //안전시설
	    	if(cell!=null){memo.put("안전시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(33);  //접근도로
	    	if(cell!=null){memo.put("접근도로_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(34);  //기타
	    	if(cell!=null){memo.put("기타_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(35);  //저사상태
	    	if(cell!=null){memo.put("저사상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(36);  //저사량
	    	if(cell!=null){memo.put("저사상태_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(37);  //최종점검결과
	    	if(cell!=null){memo.put("최종점검결과", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(38);  //조치사항
	    	if(cell!=null){memo.put("조치사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(39);  //지정해제
	    	if(cell!=null){memo.put("지정해제", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(40);  //종합의견_1
	    	if(cell!=null){memo.put("종합의견1", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(41);  //종합의견_2
	    	if(cell!=null){memo.put("종합의견2", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(42);  //종합의견_3
	    	if(cell!=null){memo.put("종합의견3", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(43);  //종합의견_4
	    	if(cell!=null){memo.put("종합의견4", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(44);  //종합의견_5
	    	if(cell!=null){memo.put("종합의견5", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(45);  //주요시설_특이사항
	    	if(cell!=null){memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(46);  //부대시설_특이사항
	    	if(cell!=null){memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(47);  //기타_특이사항
	    	if(cell!=null){memo.put("기타_특이사항", ExcelUtils.getCellValue(cell));}
	    	
	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("위도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경초").toString())) {
	    		latDd =  Integer.parseInt(memo.get("위도").toString()) + (Integer.parseInt(memo.get("위분").toString())/60) + (Double.parseDouble(memo.get("위초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도").toString()) + (Integer.parseInt(memo.get("경분").toString())/60) + (Double.parseDouble(memo.get("경초").toString())/3600);
				
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
		}else if(mType.equals("계류보전")) {
			cell = row.getCell(9);  //지정면적
	    	if(cell!=null){memo.put("지정면적", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(10);  //점검일시
	    	if(cell!=null){
	    		String dt = ExcelUtils.getCellValue(cell).replace(".", "-");
	    		memo.put("점검일시", dt.equals("") ? dt : dt.substring(0, dt.length() - 1));
	    	}
	    	cell = row.getCell(11);  //점검자
	    	if(cell!=null){memo.put("점검자", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(12);  //시점위도
	    	if(cell!=null){memo.put("시점위도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(13);  //시점위분
	    	if(cell!=null){memo.put("시점위분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(14);  //시점위초
	    	if(cell!=null){memo.put("시점위초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(15);  //시점경도
	    	if(cell!=null){memo.put("시점경도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(16);  //시점경분
	    	if(cell!=null){memo.put("시점경분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(17);  //시점경초
	    	if(cell!=null){memo.put("시점경초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(18);  //종점위도
	    	if(cell!=null){memo.put("종점위도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(19);  //종점위분
	    	if(cell!=null){memo.put("종점위분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(20);  //종점위초
	    	if(cell!=null){memo.put("종점위초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(21);  //종점경도
	    	if(cell!=null){memo.put("종점경도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(22);  //종점경분
	    	if(cell!=null){memo.put("종점경분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(23);  //종점경초
	    	if(cell!=null){memo.put("종점경초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(24);  //시설물종류
	    	if(cell!=null){memo.put("종류", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(25);  //시설제원_길이
	    	if(cell!=null){memo.put("시설제원_길이", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(26);  //시설제원_폭
	    	if(cell!=null){memo.put("시설제원_폭", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(27);  //시설제원_깊이
	    	if(cell!=null){memo.put("시설제원_깊이", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(28);  //골막이_측정값
	    	if(cell!=null){memo.put("골막이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(29);  //기슭막이_측정값
	    	if(cell!=null){memo.put("기슭막이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(30);  //바닥막이_측정값
	    	if(cell!=null){memo.put("바닥막이_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(31);  //골막이_판정값
	    	if(cell!=null){memo.put("골막이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(32);  //기슭막이_판정값
	    	if(cell!=null){memo.put("기슭막이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(33);  //바닥막이_판정값
	    	if(cell!=null){memo.put("바닥막이_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(34);  //계류상태_판정값
	    	if(cell!=null){memo.put("계류상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(35);  //수문_판정값
	    	if(cell!=null){memo.put("수문_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(36);  //식생상태_판정값
	    	if(cell!=null){memo.put("식생상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(37);  //안전시설_판정값
	    	if(cell!=null){memo.put("안전시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(38);  //접근도로_판정값
	    	if(cell!=null){memo.put("접근도로_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(39);  //기타_판정값
	    	if(cell!=null){memo.put("기타_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(40);  //최종점검결과
	    	if(cell!=null){memo.put("최종점검결과", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(41);  //조치사항
	    	if(cell!=null){memo.put("조치사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(42);  //지정해제
	    	if(cell!=null){memo.put("지정해제", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(43);  //종합의견_1
	    	if(cell!=null){memo.put("종합의견1", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(44);  //종합의견_2
	    	if(cell!=null){memo.put("종합의견2", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(45);  //종합의견_3
	    	if(cell!=null){memo.put("종합의견3", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(46);  //종합의견_4
	    	if(cell!=null){memo.put("종합의견4", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(47);  //종합의견_5
	    	if(cell!=null){memo.put("종합의견5", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(48);  //주요시설_특이사항
	    	if(cell!=null){memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(49);  //부대시설_특이사항
	    	if(cell!=null){memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(50);  //기타_특이사항
	    	if(cell!=null){memo.put("기타_특이사항", ExcelUtils.getCellValue(cell));}
	    	
	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("시점위도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점위분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점위초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("시점경초").toString())) {
	    		latDd = Integer.parseInt(memo.get("시점위도").toString()) + (Integer.parseInt(memo.get("시점위분").toString())/60) + (Double.parseDouble(memo.get("시점위초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("시점경도").toString()) + (Integer.parseInt(memo.get("시점경분").toString())/60) + (Double.parseDouble(memo.get("시점경초").toString())/3600);
				
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
		}else if(mType.equals("산지사방")) {
			cell = row.getCell(9);  //지정면적
	    	if(cell!=null){memo.put("지정면적", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(10);  //점검일시
	    	if(cell!=null){
	    		String dt = ExcelUtils.getCellValue(cell).replace(".", "-");
	    		memo.put("점검일시", dt.equals("") ? dt : dt.substring(0, dt.length() - 1));
	    	}
	    	cell = row.getCell(11);  //점검자
	    	if(cell!=null){memo.put("점검자", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(12);  //위도도
	    	if(cell!=null){memo.put("위도도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(13);  //위도분
	    	if(cell!=null){memo.put("위도분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(14);  //위도초
	    	if(cell!=null){memo.put("위도초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(15);  //경도도
	    	if(cell!=null){memo.put("경도도", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(16);  //경도분
	    	if(cell!=null){memo.put("경도분", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(17);  //경도초
	    	if(cell!=null){memo.put("경도초", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(18);  //시설물종류
	    	if(cell!=null){memo.put("종류", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(19);  //보강시설 측정값
	    	if(cell!=null){memo.put("보강시설_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(20);  //보호시설_측정값
	    	if(cell!=null){memo.put("보호시설_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(21);  //배수시설_측정값
	    	if(cell!=null){memo.put("배수시설_측정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(22);  //보강시설
	    	if(cell!=null){memo.put("보강시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(23);  //보호시설_판정값
	    	if(cell!=null){memo.put("보호시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(24);  //배수시설_판정값
	    	if(cell!=null){memo.put("배수시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(25);  //사면상태_판정값
	    	if(cell!=null){memo.put("사면상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(26);  //수문_판정값
	    	if(cell!=null){memo.put("수문_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(27);  //식생상태_판정값
	    	if(cell!=null){memo.put("식생상태_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(28);  //안전시설_판정값
	    	if(cell!=null){memo.put("안전시설_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(29);  //접근도로_판정값
	    	if(cell!=null){memo.put("접근도로_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(30);  //기타_판정값
	    	if(cell!=null){memo.put("기타_판정값", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(31);  //최종점검결과
	    	if(cell!=null){memo.put("최종점검결과", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(32);  //조치사항
	    	if(cell!=null){memo.put("조치사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(33);  //지정해제
	    	if(cell!=null){memo.put("지정해제", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(34);  //종합의견_1
	    	if(cell!=null){memo.put("종합의견1", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(35);  //종합의견_2
	    	if(cell!=null){memo.put("종합의견2", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(36);  //종합의견_3
	    	if(cell!=null){memo.put("종합의견3", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(37);  //종합의견_4
	    	if(cell!=null){memo.put("종합의견4", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(38);  //종합의견_5
	    	if(cell!=null){memo.put("종합의견5", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(39);  //주요시설_특이사항
	    	if(cell!=null){memo.put("주요시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(40);  //부대시설_특이사항
	    	if(cell!=null){memo.put("부대시설_특이사항", ExcelUtils.getCellValue(cell));}
	    	cell = row.getCell(41);  //기타_특이사항
	    	if(cell!=null){memo.put("기타_특이사항", ExcelUtils.getCellValue(cell));}

	    	if(EgovNumberUtil.getNumberValidCheck(memo.get("위도도").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위도분").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("위도초").toString()) || 
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도도").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도분").toString()) ||
	    			EgovNumberUtil.getNumberValidCheck(memo.get("경도초").toString())) {
	    		latDd = Integer.parseInt(memo.get("위도도").toString()) + (Integer.parseInt(memo.get("위도분").toString())/60) + (Double.parseDouble(memo.get("위도초").toString())/3600);
				lonDd = Integer.parseInt(memo.get("경도도").toString()) + (Integer.parseInt(memo.get("경도분").toString())/60) + (Double.parseDouble(memo.get("경도초").toString())/3600);
				
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
		vo.setATTR(attr.toString());
		vo.setMEMO(memo.toString());
        vo.setLATDD(latDd);
        vo.setLONDD(lonDd);
        
		return vo;
	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectFckMseFieldBookMaxYear() throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookMaxYear();
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectFckMseFieldBookYear() throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookYear();
	}
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectFckMseFieldBookCheckMstName(String mst_partname) throws Exception {
		return fckMseFieldBookDAO.selectFckMseFieldBookCheckMstName(mst_partname);
	}
	
	/**
	 * 공유방 단건 상세조회
	 */
	@Override
	public FckMseFieldBookItemVO selectFieldBookDetailOne(HashMap<String, Object> map) throws Exception {
		return fckMseFieldBookDAO.selectFieldBookDetailOne(map);
	}
	
	/**
	 * 공유방 단건 수정
	 */
	@Override
	public void updateFieldBookDetailOne(FckMseFieldBookItemVO searchVO) throws Exception {
		fckMseFieldBookDAO.updateFieldBookDetailOne(searchVO);
	}
	
	/**
	 * 공유방 단건 삭제
	 */
	@Override
	public void deleteFieldBookDetailOne(HashMap<String, Object> map) throws Exception {
		fckMseFieldBookDAO.deleteFieldBookDetailOne(map);
	}
	
	/**
	 * EPSG:4326 좌표를 5186 형식의 좌표로 변환한다.
	 */
	@Override
	public List<EgovMap> selectFckMseProjBessel(HashMap<String, Object> map) throws Exception{
		return fckMseFieldBookDAO.selectFckMseProjBessel(map);
	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception {
		return fckMseFieldBookDAO.selectOrgchtList();
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(FckMseFieldBookVO vo) throws Exception {
		return fckMseFieldBookDAO.selectMberList(vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	@Override
	public List<FckMseFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception {
		return fckMseFieldBookDAO.selectCnrsAuthorList(map);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서목록
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return fckMseFieldBookDAO.selectDeptInfoList();
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서별 회원목록
	 */
	@Override
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return fckMseFieldBookDAO.selectDeptCreatList();
	}
	
	/**
	 * 공유방 사용자 권한 수정
	 * @throws Exception
	 */
	public void updateCnrsSpceAuthorMgt(FckMseFieldBookVO fieldBookVO) throws Exception{
		
		List<FckMseFieldBookVO> list = new ArrayList<FckMseFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		for(int i =0; i<authorEsntlIdList.length;i++) {
			if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
				FckMseFieldBookVO vo = new FckMseFieldBookVO();
				vo.setId(fieldBookVO.getId());
				vo.setSvytype(fieldBookVO.getSvytype());
				vo.setEsntl_id(authorEsntlIdList[i].toString());
				vo.setUser_grade("USER");
				list.add(vo);				
			}
		}		
		fckMseFieldBookDAO.deleteCnrsSpceAuthorMgt(fieldBookVO);
		if(list.size() != 0) {
			fckMseFieldBookDAO.insertCnrsSpceAuthorMgt(list);
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 계측장비 대상지 조회
	 */
	@Override
	public List<EgovMap> selectMseSldListAddr(FckMseFieldBookVO fieldBookVO) throws Exception{
		return fckMseFieldBookDAO.selectMseSldListAddr(fieldBookVO);
	}
}
