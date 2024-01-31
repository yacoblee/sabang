package or.sabang.sys.ext.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.ext.service.ExtFieldBookItemVO;
import or.sabang.sys.ext.service.ExtFieldBookService;
import or.sabang.sys.ext.service.ExtFieldBookVO;
import or.sabang.sys.ext.service.LocImgInfoVO;
import or.sabang.sys.service.SysComptVO;
import or.sabang.utl.StaticMapImageUtils;

@Service("extFieldBookService")
public class ExtFieldBookServiceImpl extends EgovAbstractServiceImpl implements ExtFieldBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtFieldBookServiceImpl.class);
	
	@Resource(name="extFieldBookDAO")
	private ExtFieldBookDAO extFieldBookDAO;
	
	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 기초조사 공유방 체크
	 */
//	@Override
//	public int selectBscCnrsSpceCheck(HashMap<String, Object> map) throws Exception {
//		return extFieldBookDAO.selectBscCnrsSpceCheck(map);
//	}

	/**
	 * @param map
	 * @return
	 * @description 전자야장 외관점검 공유방 체크
	 */
//	@Override
//	public int selectAprCnrsSpceCheck(HashMap<String, Object> map) throws Exception {
//		return extFieldBookDAO.selectAprCnrsSpceCheck(map);
//	}

	/**
	 * @param map
	 * @return
	 * @description 전자야장 땅밀림실태조사 공유방 체크
	 */
//	@Override
//	public int selectLcpCnrsSpceCheck(HashMap<String, Object> map) throws Exception {
//		return extFieldBookDAO.selectLcpCnrsSpceCheck(map);
//	}

	/**
	 * @param map
	 * @return
	 * @description 전자야장 취약지역실태조사 공유방 체크
	 */
//	@Override
//	public int selectWkaCnrsSpceCheck(HashMap<String, Object> map) throws Exception {
//		return extFieldBookDAO.selectWkaCnrsSpceCheck(map);
//	};
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 취약지역해제조사 공유방 체크
	 */
//	@Override
//	public int selectCnlCnrsSpceCheck(HashMap<String, Object> map) throws Exception {
//		return extFieldBookDAO.selectCnlCnrsSpceCheck(map);
//	};

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 비밀번호 조회
	 */
	@Override
	public List<EgovMap> selectCnrsSpcePwd(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpcePwd(map);
	}

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 목록조회
	 */
	@Override
	public List<ExtFieldBookVO> selectCnrsSpceList(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceList(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 단건조회
	 */
	@Override
	public ExtFieldBookVO selectCnrsSpceInfo(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceInfo(map);
	}

	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 목록 여부
	 */
	@Override
	public int selectCnrsSpceAt(int mst_id) throws Exception {
		return extFieldBookDAO.selectCnrsSpceAt(mst_id);
	}

	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 다운로드
	 */
	@Override
	public List<ExtFieldBookItemVO> selectCnrsSpceDownload(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceDownload(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사데이터 조회
	 */
	@Override
	public List<EgovMap> selectCnrsSpceItem(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceItem(map);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 기초조사 조사데이터 FID 최대값 조회
	 */
	@Override
	public int selectBscCnrsSpceMvl(int mst_id) throws Exception {
		return extFieldBookDAO.selectBscCnrsSpceMvl(mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 땅밀림 실태조사 조사데이터 FID 최대값 조회
	 */
	@Override
	public int selectLcpCnrsSpceMvl(int mst_id) throws Exception {
		return extFieldBookDAO.selectLcpCnrsSpceMvl(mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 외관점검 조사데이터 FID 최대값 조회
	 */
	@Override
	public int selectAprCnrsSpceFieldMvl(int mst_id) throws Exception {
		return extFieldBookDAO.selectAprCnrsSpceFieldMvl(mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 조사완료 외관점검 조사데이터 FID 최대값 조회
	 */
	@Override
	public int selectAprCnrsSpceComptMvl(int mst_id) throws Exception {
		return extFieldBookDAO.selectAprCnrsSpceComptMvl(mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 취약지역 실태조사 조사데이터 FID 최대값 조회
	 */
	@Override
	public int selectWkaCnrsSpceMvl(int mst_id) throws Exception {
		return extFieldBookDAO.selectWkaCnrsSpceMvl(mst_id);
	}
	
	/**
	 * @param mst_id
	 * @return
	 * @description 전자야장 공유방 취약지역 해제조사 조사데이터 FID 최대값 조회
	 */
	@Override
	public int selectCnlCnrsSpceMvl(int mst_id) throws Exception {
		return extFieldBookDAO.selectCnlCnrsSpceMvl(mst_id);
	}
	
	/**
	 * @return
	 * @description 전자야장 취약지역 해제조사 FID 갱신
	 */
	@Override
	public int selectCnlUpdtFid() throws Exception {
		return extFieldBookDAO.selectCnlUpdtFid();
	}
	
	/**
	 * @return
	 * @description 전자야장 취약지역 실태조사 FID 갱신
	 */
	@Override
	public int selectWkaUpdtFid() throws Exception {
		return extFieldBookDAO.selectWkaUpdtFid();
	}
	
	/**
	 * @return
	 * @description 전자야장 땅밀림 실태조사 FID 갱신
	 */
	@Override
	public int selectLcpUpdtFid() throws Exception {
		return extFieldBookDAO.selectLcpUpdtFid();
	}
	
	/**
	 * @return
	 * @description 전자야장 기초조사 FID 갱신
	 */
	@Override
	public int selectBscUpdtFid() throws Exception {
		return extFieldBookDAO.selectBscUpdtFid();
	}
	
	/**
	 * @param itemVO
	 * @return
	 * @description 전자야장 공유방 조사데이터 생성
	 */
	public void insertCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception{
		extFieldBookDAO.insertCnrsSpceItem(itemVO);
	};
	
	/**
	 * @param itemVO
	 * @return
	 * @description 전자야장 공유방 조사데이터 업데이트
	 */
	public void updateCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception{
		extFieldBookDAO.updateCnrsSpceItem(itemVO);
	};
	
	public void upsertCnrsSpceItem(ExtFieldBookItemVO itemVO) throws Exception{
		extFieldBookDAO.upsertCnrsSpceItem(itemVO);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사 완료데이터 단건 조회
	 */
	@Override
	public ExtFieldBookItemVO selectCnrsSpceCompItem(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceCompItem(map);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 조사데이터 업로드 결과 목록
	 */
	@Override
	public List<ExtFieldBookItemVO> selectCnrsSpceCompList(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceCompList(map);
	}

	/**
	 * @param map
	 * @throws Exception
	 * @desciption 전자야장 완료테이블 조사데이터 여부
	 */
	public int selectUpsertAt(ExtFieldBookItemVO searchVO) throws Exception {
		return extFieldBookDAO.selectUpsertAt(searchVO);
	}
	
	/**
	 * @param updDate
	 * @throws Exception
	 * @desciption 전자야장 전송 데이터 업데이트 날짜와 완료테이블 데이터 날짜 비교
	 */
	public int selectUpdDateAt(ExtFieldBookItemVO searchVO) throws Exception {
		return extFieldBookDAO.selectUpdDateAt(searchVO);
	}
	
	/**
	 * 전자야장 위치도 업데이트
	 * @author ipodo
	 * @name updateComptLcMap
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateComptLcMap(HashMap<String, Object> schMap) throws Exception {
		
		List<String> result = new ArrayList<String>();
		HashMap<String, Object> param = new HashMap<String, Object>();

		// 위치도 중심 및 라벨 조회
		List<EgovMap> list = extFieldBookDAO.selectComptLcMapLonLat(schMap);
		
		String type = schMap.get("type").toString();
		if(type.contains("외관점검") && list.size() == 0) {
			list = extFieldBookDAO.selectAprComptLcMapLonLat(schMap);
		}
		
		if(type.contains("정밀점검") && list.size() == 0) {
			list = extFieldBookDAO.selectPcsComptLcMapLonLat(schMap);
		}
		
		String mapType = null;
		if(schMap.containsKey("mapType")) {
			mapType = schMap.get("mapType").toString();
		}else {
			mapType = "SATELLITE";
		}
		
		
		int i=1;
		for(EgovMap item : list) {
			if(item != null) {
				if(item.containsKey("lon") && item.containsKey("lat") ) {
					// 위치도 조회 파라미터 설정
					String[] center = new String[] {item.get("lon").toString(),item.get("lat").toString()};
					int zoom = schMap.containsKey("zoom") ? Integer.parseInt(schMap.get("zoom").toString()) : Integer.parseInt(String.valueOf(item.get("zoom")));
					param.put("path", schMap.get("path").toString());
					param.put("center", center); //중심점
					param.put("crs", "EPSG:3857"); //좌표계
					param.put("format", "png"); //포맷
					param.put("label", item.get("label").toString()); //라벨
					param.put("width", 586);
					param.put("height",516);
					param.put("resize", true);				
					param.put("zoom", zoom); // 위치도 줌레벨에 대한 변경 여부 처리
					param.put("marker", item.get("marker").toString());
					param.put("svyid",schMap.get("_label").toString());
					param.put("se",schMap.get("SE").toString());
					param.put("type",schMap.get("type").toString());
					param.put("name", schMap.get("_label").toString() + "_위치도" + i); // 파일명
					param.put("mapType", mapType); // 지도 타입
					
					String fullfilepath = schMap.get("midPath") + param.get("name").toString() + "." + param.get("format").toString(); 
					StaticMapImageUtils.saveImageByXY(param);
					result.add(fullfilepath);
					
					//위치도 줌레벨 저장
					LocImgInfoVO vo = new LocImgInfoVO();
					vo.setGid(Integer.parseInt(String.valueOf(schMap.get("gid"))));
					vo.setSvySe((String)schMap.get("SE"));
					vo.setSvyType(type);
					vo.setSvyZoom(zoom);
					vo.setSvyIdx(i);
					vo.setMapType(mapType);
					extFieldBookDAO.insertUpdtLocImgInfo(vo);
				}
				i++;
			}
		}
		
		schMap.put("lcMap", new ObjectMapper().writeValueAsString(result));
		
		return extFieldBookDAO.updateComptLcMap(schMap);
	}
	
	/**
	 * @param map
	 * @return
	 * @description 전자야장 공유방 동기화
	 */
	@Override
	public List<ExtFieldBookItemVO> selectCnrsSpceSyncItem(HashMap<String, Object> map) throws Exception {
		return extFieldBookDAO.selectCnrsSpceSyncItem(map);
	}
	
	/**
	 * 위치도 줌레벨 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int selectLocImgInfo(LocImgInfoVO map) throws Exception{
		return extFieldBookDAO.selectLocImgInfo(map);
	};
	
	/**
	 * 위치도 줌레벨 추가 및 수정
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public void insertUpdtLocImgInfo(LocImgInfoVO map) throws Exception{
		extFieldBookDAO.insertUpdtLocImgInfo(map);
	}

	/**
	 * 위치도 정보 조회
	 * @param schMap
	 * @return
	 * @throws Exception
	 */	
	@Override
	public List<EgovMap> selectComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception {
		return extFieldBookDAO.selectComptLcMapLonLat(schMap);
	};
	
	/**
	 * 위치도 정보 조회
	 * @param schMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<EgovMap> selectAprComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception {
		return extFieldBookDAO.selectAprComptLcMapLonLat(schMap);
	}

	/**
	 * 위치도 정보 조회
	 * @author DEVWORK
	 * @param schMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 14.
	 * @modified
	 * @see or.sabang.sys.ext.service.ExtFieldBookService#selectPcsComptLcMapLonLat(java.util.HashMap)
	 */
	@Override
	public List<EgovMap> selectPcsComptLcMapLonLat(HashMap<String, Object> schMap) throws Exception {
		return extFieldBookDAO.selectPcsComptLcMapLonLat(schMap);
	}
}
