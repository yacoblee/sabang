package or.sabang.sys.lss.lcp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookItemVO;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookService;
import or.sabang.sys.lss.lcp.service.LssLcpFieldBookVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.sys.service.SysComptVO;

@Service("lssLcpFieldBookService")
public class LssLcpFieldBookServiceImpl extends EgovAbstractServiceImpl implements LssLcpFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LssLcpFieldBookServiceImpl.class);
	
	@Resource(name="lssLcpFieldBookDAO")
	private LssLcpFieldBookDAO lssLcpFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 목록조회
	 */
	@Override
	public List<LssLcpFieldBookVO> selectLssLcpFieldBookList(LssLcpFieldBookVO searchVO) throws Exception {		
		return lssLcpFieldBookDAO.selectLssLcpFieldBookList(searchVO);
	}	

	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 공유방 갯수조회
	 */
	@Override
	public int selectLssLcpFieldBookListTotCnt(LssLcpFieldBookVO searchVO) throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookListTotCnt(searchVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 정보 상세조회
	 */
	@Override
	public LssLcpFieldBookVO selectLssLcpFieldBookDetail(HashMap<String, Object> map) throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookDetail(map);
	}
	
	/**
	 * 공유방 권한 확인
	 * @throws Exception
	 */
	public String selectAuthorCheck(HashMap<String, Object> map) throws Exception{
		String result = lssLcpFieldBookDAO.selectAuthorCheck(map);
		if(result == null) result = "UNAUTHORD";
		
		return result;
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 리스트 조회
	 */
	public List<LssLcpSvyStripLandVO> selectLssLcpFieldBookItemList(LssLcpFieldBookItemVO searchVO) throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookItemList(searchVO);
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 갯수조회
	 */
	public int selectLssLcpFieldBookItemListTotCnt(LssLcpFieldBookItemVO searchVO) throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookItemListTotCnt(searchVO);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 비밀번호 조회
	 */
	@Override
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception {
		return lssLcpFieldBookDAO.selectCnrsSpcePwd(map);
	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 목록조회 테스트
	 */
	@Override
	public List<LssLcpFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception {
		return lssLcpFieldBookDAO.selectCnrsSpceList(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 단건조회 테스트
	 */
//	@Override
//	public LssLcpFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception {
//		return lssLcpFieldBookDAO.selectCnrsSpceInfo(map);
//	}

	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 목록 여부
	 */
//	@Override
//	public int selectCnrsSpceAt(int mst_id) throws Exception {
//		return lssLcpFieldBookDAO.selectCnrsSpceAt(mst_id);
//	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사유형
	 */
//	public String selectCnrsSpceType(HashMap<String, Object> map) throws Exception {
//		return lssLcpFieldBookDAO.selectCnrsSpceType(map);
//	}

	/**
	 * @param map
	 * @return
	 * @description 공유방 다운로드 테스트
	 */
	@Override
	public List<LssLcpFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception {
		return lssLcpFieldBookDAO.selectCnrsSpceDownload(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 조회 테스트
	 */
//	@Override
//	public List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception {
//		return lssLcpFieldBookDAO.selectCnrsSpceItem(map);
//	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 공유방 조사데이터 FID 최대값 조회 테스트
	 */
//	@Override
//	public int selectCnrsSpceMvl(int mst_id) throws Exception {
//		return lssLcpFieldBookDAO.selectCnrsSpceMvl(mst_id);
//	}
	@Override
	public int selectLcpCnrsSpceMvl(int mst_id) throws Exception {
		return lssLcpFieldBookDAO.selectLcpCnrsSpceMvl(mst_id);
	}
	@Override
	public int selectAprCnrsSpceFieldMvl(int mst_id) throws Exception {
		return lssLcpFieldBookDAO.selectAprCnrsSpceFieldMvl(mst_id);
	}
	@Override
	public int selectAprCnrsSpceComptMvl(int mst_id) throws Exception {
		return lssLcpFieldBookDAO.selectAprCnrsSpceComptMvl(mst_id);
	}
	
	/**
	 * @return
	 * @description 땅밀림실태조사 FID 갱신
	 */
	@Override
	public int selectLcpUpdtFid() throws Exception {
		return lssLcpFieldBookDAO.selectLcpUpdtFid();
	}
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
//	public void insertCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
//		lssLcpFieldBookDAO.insertCnrsSpceItem(itemVO);
//	};
//	public int insertLcpCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
//		return lssLcpFieldBookDAO.insertLcpCnrsSpceItem(itemVO);
//	};
//	public void insertAprCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
//		lssLcpFieldBookDAO.insertAprCnrsSpceItem(itemVO);
//	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 공유방 조사데이터 업데이트 테스트
	 */
	public void updateCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
		lssLcpFieldBookDAO.updateCnrsSpceItem(itemVO);
	};
//	public int updateLcpCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
//		return lssLcpFieldBookDAO.updateLcpCnrsSpceItem(itemVO);
//	};
//	public void updateAprCnrsSpceItem(LssLcpFieldBookItemVO itemVO) throws Exception{
//		lssLcpFieldBookDAO.updateAprCnrsSpceItem(itemVO);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 공유방 조사데이터 업로드 결과 목록 테스트
	 */
//	@Override
//	public List<LssLcpFieldBookItemVO> selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception {
//		return lssLcpFieldBookDAO.selectCnrsSpceCompItem(map);
//	}
//	@Override
//	public List<LssLcpFieldBookItemVO> selectLcpCnrsSpceCompItem(HashMap<String, Object> map) throws Exception {
//		return lssLcpFieldBookDAO.selectLcpCnrsSpceCompItem(map);
//	}
//	@Override
//	public List<LssLcpFieldBookItemVO> selectAprCnrsSpceCompItem(HashMap<String, Object> map) throws Exception {
//		return lssLcpFieldBookDAO.selectAprCnrsSpceCompItem(map);
//	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 등록
	 */
	public String insertCnrsSpce(LssLcpFieldBookVO fieldBookVO) throws Exception {
		lssLcpFieldBookDAO.insertCnrsSpce(fieldBookVO);
		return fieldBookVO.getId();
	}

	/**
	 * @param fieldBookVO
	 * @throws Exception
	 * @description 공유방 사용자 권한부여
	 */
	public void insertCnrsSpceAuthorMgt(LssLcpFieldBookVO fieldBookVO) throws Exception {
		List<LssLcpFieldBookVO> list = new ArrayList<LssLcpFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		if(authorEsntlIdList != null) {
			for(int i =0; i<authorEsntlIdList.length;i++) {
				if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
					LssLcpFieldBookVO vo = new LssLcpFieldBookVO();
					vo.setId(fieldBookVO.getId());
					vo.setSvytype(fieldBookVO.getSvytype());
					vo.setEsntl_id(authorEsntlIdList[i].toString());
					vo.setUser_grade("USER");
					list.add(vo);				
				}
			}
		}
		
		// 생성자 추가
		LssLcpFieldBookVO vo = new LssLcpFieldBookVO();
		vo.setId(fieldBookVO.getId());
		vo.setSvytype(fieldBookVO.getSvytype());
		vo.setEsntl_id(fieldBookVO.getMst_admin_id());
		vo.setUser_grade("ADMIN");
		list.add(vo);
		
		lssLcpFieldBookDAO.insertCnrsSpceAuthorMgt(list);
	}
	
	/**
	 * 대상지를 일괄등록한다.
	 */
	@Transactional(noRollbackFor = {PSQLException.class,Exception.class})
	@Override
	public JSONObject insertStripLand(LssLcpFieldBookVO fieldBookVO) throws Exception {
		List<SysComptVO> list = new ArrayList<SysComptVO>();
		
		Calendar calendar = Calendar.getInstance();
		JSONObject attrItem = null;
		JSONArray attrArr = new JSONArray();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		String userNm ="";
		
		if(fieldBookVO.getMst_create_user() == null) {
			userNm = user.getName();
		}else {
			userNm = fieldBookVO.getMst_create_user();
		}
		
//		if(!fieldBookItemVO.getATTR().equals("")) {			
//			attrArr = new JSONArray(fieldBookItemVO.getATTR());
//		}
		
		List<EgovMap> fieldBookList = new ArrayList<EgovMap>();
		int sldCnt = 0;
		int gvfCnt = 0;
		if(!fieldBookVO.getSldCnt().isEmpty()) sldCnt = Integer.parseInt(fieldBookVO.getSldCnt().toString());
		if(!fieldBookVO.getGvfCnt().isEmpty()) gvfCnt = Integer.parseInt(fieldBookVO.getGvfCnt().toString());
		
		if(fieldBookVO.getSldStdgCd().length() > 0 || sldCnt > 0) {
			fieldBookList = lssLcpFieldBookDAO.selectLcpSldListAddr(fieldBookVO);
		}
		
		if(fieldBookVO.getGvfStdgCd().length() > 0 || gvfCnt > 0) {
			fieldBookList.addAll(lssLcpFieldBookDAO.selectLcpGvfListAddr(fieldBookVO));
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date_str = formatter.format(calendar.getTime());
		
		
		if(fieldBookList.size() > 0) {
			for(int i=0;i<fieldBookList.size();i++) {
				JSONArray attr = new JSONArray();
				SysComptVO vo = new SysComptVO();

				vo.setMst_id(Integer.valueOf(fieldBookVO.getId()));
	    		vo.setLogin_id(userNm);
	    		
	    		//조사ID
	        	attrItem = new JSONObject();
	    		attrItem.put("VALUE", fieldBookList.get(i).get("label").toString());
	    		attrItem.put("NAME","조사ID");
	    		attr.put(attrItem);
	    		
	    		//조사유형
	        	attrItem = new JSONObject();
	    		attrItem.put("VALUE", "땅밀림 실태조사");
	    		attrItem.put("NAME","조사유형");
	    		attr.put(attrItem);

	    		//조사연도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", String.valueOf(calendar.get(Calendar.YEAR)));
	    		attrItem.put("NAME","조사연도");
	    		attr.put(attrItem);
	    		
	    		String stdgNm[] = fieldBookList.get(i).get("addr").toString().split(" ",5);
	    		
	    		String attrSd = "";
	    		String attrSgg = "";
	    		String attrEmd = "";
	    		String attrRi = "";
	    		String attrJibun = "";
	    		
	    		if(stdgNm.length == 1) {
	    			attrSd = stdgNm[0];
	    		} else if(stdgNm.length == 2) {
	    			attrSd = stdgNm[0];
	    			attrSgg = stdgNm[1];
	    		} else if(stdgNm.length == 3) {
	    			attrSd = stdgNm[0];
	    			attrSgg = stdgNm[1];
	    			attrEmd = stdgNm[2];
	    		} else if(stdgNm.length == 4) {
	    			attrSd = stdgNm[0];
	    			attrSgg = stdgNm[1];
	    			attrEmd = stdgNm[2];
	    			attrRi = stdgNm[3];
	    		} else {
	    			attrSd = stdgNm[0];
	    			attrSgg = stdgNm[1];
	    			attrEmd = stdgNm[2];
	    			attrRi = stdgNm[3];
	    			attrJibun = stdgNm[4];
	    		}
	    		
	    		//시도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", attrSd);
	    		attrItem.put("NAME","시도");
	    		attr.put(attrItem);
	    		
	    		//시군구
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", attrSgg);
	    		attrItem.put("NAME","시군구");
	    		attr.put(attrItem);
	    		
	    		//읍면동
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", attrEmd);
	    		attrItem.put("NAME","읍면동");
	    		attr.put(attrItem);
	    		
	    		//리
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", attrRi);
	    		attrItem.put("NAME","리");
	    		attr.put(attrItem);
	    		
	    		//지번
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", attrJibun);
	    		attrItem.put("NAME","지번");
	    		attr.put(attrItem);
	    		
	    		//위도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", Double.parseDouble(fieldBookList.get(i).get("lat").toString()));
	    		attrItem.put("NAME","위도");
	    		attr.put(attrItem);
	    		
	    		//경도
	    		attrItem = new JSONObject();
	    		attrItem.put("VALUE", Double.parseDouble(fieldBookList.get(i).get("lon").toString()));
	    		attrItem.put("NAME","경도");
	    		attr.put(attrItem);
	    		
	    		vo.setSvy_attr(attr.toString());
//	    		vo.setFID(Integer.parseInt(attrObj.get("gid").toString()));
	    		vo.setUniq_id(fieldBookList.get(i).get("uniqId").toString());
	    		vo.setSvy_label(fieldBookList.get(i).get("label").toString());
	    		vo.setSvy_keyword(fieldBookList.get(i).get("label").toString());
	    		vo.setSvy_lat(Double.parseDouble(fieldBookList.get(i).get("lat").toString()));
	    	    vo.setSvy_lon(Double.parseDouble(fieldBookList.get(i).get("lon").toString()));
	    	    vo.setSmgeometry(fieldBookList.get(i).get("geom").toString());
	    	    
	    	    ComDefaultCodeVO comVO = new ComDefaultCodeVO();
	    	    
	    	    //메모

	    	    JSONObject memo = new JSONObject();
	    	    
	    	    //임상
	    	    if(fieldBookList.get(i).containsKey("frtpCd")) {
		    	    comVO.setCodeId("FEI071");
		    	    comVO.setCode(fieldBookList.get(i).get("frtpCd").toString());
		    		List<CmmnDetailCode> frtpCd_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("산림특성.임상", frtpCd_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("산림특성.임상", "");
	    	    }
	    	    
	    	    //지질(대)
	    	    if(fieldBookList.get(i).containsKey("prrckLarg")) {
	    	    	comVO.setCodeId("FEI072");
		    	    comVO.setCode(fieldBookList.get(i).get("prrckLarg").toString());
		    		List<CmmnDetailCode> prrckLarg_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("지질특성.주구성암석", prrckLarg_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("지질특성.주구성암석", "");
	    	    }
	    	    
	    	    //지질(중)
	    	    if(fieldBookList.get(i).containsKey("prrckMddl")) {
	    	    	comVO.setCodeId("FEI073");
		    	    comVO.setCode(fieldBookList.get(i).get("prrckMddl").toString());
		    		List<CmmnDetailCode> prrckMddl_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("지질특성.모암", prrckMddl_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("지질특성.모암", "");
	    	    }
	            
	    	    //최소경사
	    	    if(fieldBookList.get(i).containsKey("slpMin")) {
	                memo.put("지형특성.경사_최소", fieldBookList.get(i).get("slpMin").toString());
	            }else{
	                memo.put("지형특성.경사_최소", "");
	            }
	    	    
	            //최대경사
	    	    if(fieldBookList.get(i).containsKey("slpMax")) {
	                memo.put("지형특성.경사_최대", fieldBookList.get(i).get("slpMax").toString());
	            }else{
	                memo.put("지형특성.경사_최대", "");
	            }

	    	    //평균경사
	    	    if(fieldBookList.get(i).containsKey("slpAvg")) {
	                memo.put("지형특성.경사_평균", fieldBookList.get(i).get("slpAvg").toString());
	            }else{
	                memo.put("지형특성.경사_평균", "");
	            }
	    	    
	    	    //토양형
	    	    if(fieldBookList.get(i).containsKey("sltpCd")) {
	    	    	comVO.setCodeId("FEI074");
		    	    comVO.setCode(fieldBookList.get(i).get("sltpCd").toString());
		    		List<CmmnDetailCode> sltpCd_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("토양특성.토양형", sltpCd_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("토양특성.토양형", "");
	    	    }
	    	    
	    	    //토성
	    	    if(fieldBookList.get(i).containsKey("sibflrScs")) {
	    	    	comVO.setCodeId("FEI075");
		    	    comVO.setCode(fieldBookList.get(i).get("sibflrScs").toString());
		    		List<CmmnDetailCode> sibflrScs_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("토양특성.토성", sibflrScs_result.get(0).getCodeDc().concat("(").concat(sibflrScs_result.get(0).getCodeNm().concat(")")));
	    	    }else {
	    	    	memo.put("토양특성.토성", "");
	    	    }
	    	    
	    	    //토양구조
	    	    if(fieldBookList.get(i).containsKey("sibflrStr")) {
	    	    	comVO.setCodeId("FEI076");
		    	    comVO.setCode(fieldBookList.get(i).get("sibflrStr").toString());
		    		List<CmmnDetailCode> sibflrStr_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("토양특성.토양구조", sibflrStr_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("토양특성.토양구조", "");
	    	    }
	    	    
	    	    //암노출도
	    	    if(fieldBookList.get(i).containsKey("rockExdgr")) {
	    	    	comVO.setCodeId("FEI077");
		    	    comVO.setCode(fieldBookList.get(i).get("rockExdgr").toString());
		    	    List<CmmnDetailCode> rockExdgr_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("토양특성.암석노출도", rockExdgr_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("토양특성.암석노출도", "");
	    	    }
	    	    
	    	    //토양석력함량
	    	    if(fieldBookList.get(i).containsKey("sibflrCbs")) {
	    	    	comVO.setCodeId("FEI078");
		    	    comVO.setCode(fieldBookList.get(i).get("sibflrCbs").toString());
		    		List<CmmnDetailCode> sibflrCbs_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("임시.토양석력", sibflrCbs_result.get(0).getCodeDc());
	    	    }else {
	    	    	memo.put("임시.토양석력", "");
	    	    }
	    	    
	    	    //풍화정도
	    	    if(fieldBookList.get(i).containsKey("wteffDgr")) {
	    	    	//풍화정도
		    		comVO.setCodeId("FEI079");
		    	    comVO.setCode(fieldBookList.get(i).get("wteffDgr").toString());
		    		List<CmmnDetailCode> wteffDgr_result = cmmUseService.selectCmmCodeDetail(comVO);	    		
		    		memo.put("임시.풍화정도", wteffDgr_result.get(0).getCodeNm());
	    	    }else {
	    	    	memo.put("임시.풍화정도", "");
	    	    }
	    	    vo.setSvy_memo(memo.toString());
	    	    
	    	    vo.setCreat_dt(date_str);
	    	    vo.setLast_updt_pnttm(date_str);
	    	    
	    	    list.add(vo);
			}
		}
		
		JSONObject returnInsertLog = new JSONObject();
		JSONArray failedIdsArray = new JSONArray();
		int successCnt = 0;
		int epsgVal = 5179;

		for (SysComptVO subVo : list) {
			try {
				lssLcpFieldBookDAO.insertStripLandVO(subVo);
				
//				String query = "mst_id = ".concat(String.valueOf(subVo.getMst_id())).concat(" and svy_label = '").concat(subVo.getSvy_label());
//				superMapUtils.upsertRecordSet(subVo, "tf_feis_lcp_fieldinfo",query,epsgVal);
				successCnt++;
			} catch (Exception e) {
				failedIdsArray.put(subVo.getSvy_label());
				returnInsertLog.put("error", e.getMessage());
				LOGGER.error(e.getMessage());
			}
		}
		
		returnInsertLog.put("total", list.size());
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
	public List<LssLcpSvyStripLandVO> selectCnrsSpceSldList(LssLcpSvyStripLandVO stripLandVO) throws Exception {
		return lssLcpFieldBookDAO.selectCnrsSpceSldList(stripLandVO);
	}
	
	/**
	 * @param id
	 * @return
	 * @description 대상지 단건조회
	 */
	@SuppressWarnings("unchecked")
	public List<LssLcpSvyStripLandVO> selectCnrsSpceSldDetail(String id) throws Exception{
		return lssLcpFieldBookDAO.selectCnrsSpceSldDetail(id);
	}

	/**
	 * @param itemVO
	 * @throws Exception
	 * @description 대상지 추가
	 */
	@Override
	public void insertCnrsSpceSld(LssLcpFieldBookItemVO itemVO) throws Exception {
		lssLcpFieldBookDAO.insertCnrsSpceSld(itemVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 공유방 조사데이터 수정조회 완료여부 추가
	 */
	public List<LssLcpSvyStripLandVO> selectLssLcpFieldBookItemView(LssLcpFieldBookItemVO searchVO) throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookItemView(searchVO);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 삭제
	 */
	@Override
	public void deleteCnrsSpce(HashMap<String, Object> map) throws Exception {
		lssLcpFieldBookDAO.deleteCnrsSpce(map);
	}

	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceItem(HashMap<String, Object> map) throws Exception {
		lssLcpFieldBookDAO.deleteCnrsSpceItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사데이터 일괄삭제
	 */
	@Override
	public void deleteCnrsSpceAllItem(HashMap<String, Object> map) throws Exception {
		lssLcpFieldBookDAO.deleteCnrsSpceAllItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 공유방 조사완료데이터 삭제
	 */
	@Override
	public void deleteCnrsSpceComptItem(HashMap<String, Object> map) throws Exception {
		lssLcpFieldBookDAO.deleteCnrsSpceAllItem(map);
 		lssLcpFieldBookDAO.deleteCnrsSpceComptItem(map);
	}
	
	/**
	 * @param map
	 * @throws Exception
	 * @desciption 완료테이블 조사데이터 여부
	 */
//	public int selectUpsertAt(LssLcpFieldBookItemVO searchVO) throws Exception {
//		return lssLcpFieldBookDAO.selectUpsertAt(searchVO);
//	}
	
	/**
	 * 공유방 연도최대값을 조회한다.
	 * @throws Exception
	 */
	@Override
	public String selectLssLcpFieldBookMaxYear() throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookMaxYear();
	}
	
	/**
	 * 공유방 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectLssLcpFieldBookYear() throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookYear();
	}
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectLssLcpFieldBookCheckMstName(String mst_partname) throws Exception {
		return lssLcpFieldBookDAO.selectLssLcpFieldBookCheckMstName(mst_partname);
	}

	/**
	 * 제보테이블 정보를 조회한다.
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpGvfListAddr(LssLcpFieldBookVO vo) throws Exception {
		return lssLcpFieldBookDAO.selectLcpGvfListAddrCnt(vo);
	}
	
	/**
	 * 조사대상지등록 테이블 정보를 조회한다. 
	 * @throws Exception
	 */
	@Override
	public List<LssLcpFieldBookVO> selectLcpSldRegList() throws Exception {
		return lssLcpFieldBookDAO.selectLcpSldRegList();
	}
	
	/**
	 * 조사대상지정보 테이블 정보를 조회한다.
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectLcpSldListAddr(LssLcpFieldBookVO vo) throws Exception {
		
		return lssLcpFieldBookDAO.selectLcpSldListAddrCnt(vo);
	}
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception {
		return lssLcpFieldBookDAO.selectOrgchtList();
	}
	
	/**
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(LssLcpFieldBookVO vo) throws Exception {
		return lssLcpFieldBookDAO.selectMberList(vo);
	}
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	@Override
	public List<LssLcpFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception {
		return lssLcpFieldBookDAO.selectCnrsAuthorList(map);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서목록
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception{
		return lssLcpFieldBookDAO.selectDeptInfoList();
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @description 부서별 회원목록
	 */
	@Override
	public List<EgovMap> selectDeptCreatList() throws Exception{
		return lssLcpFieldBookDAO.selectDeptCreatList();
	}
	
	/**
	 * 공유방 사용자 권한 수정
	 * @throws Exception
	 */
	public void updateCnrsSpceAuthorMgt(LssLcpFieldBookVO fieldBookVO) throws Exception{
		
		List<LssLcpFieldBookVO> list = new ArrayList<LssLcpFieldBookVO>();
		
		String[] authorEsntlIdList = fieldBookVO.getAuthorEsntlIdList();
		
		for(int i =0; i<authorEsntlIdList.length;i++) {
			if(!fieldBookVO.getMst_admin_id().equals(authorEsntlIdList[i].toString())) {
				LssLcpFieldBookVO vo = new LssLcpFieldBookVO();
				vo.setId(fieldBookVO.getId());
				vo.setSvytype(fieldBookVO.getSvytype());
				vo.setEsntl_id(authorEsntlIdList[i].toString());
				vo.setUser_grade("USER");
				list.add(vo);				
			}
		}		
		lssLcpFieldBookDAO.deleteCnrsSpceAuthorMgt(fieldBookVO);
		if(list.size() != 0) {
			lssLcpFieldBookDAO.insertCnrsSpceAuthorMgt(list);
		}
	}
}
