package or.sabang.sys.fck.pcs.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.fck.apr.service.FckAprFieldBookVO;


public interface FckPcsFieldBookService {
	
	
	/**
	 * 조직도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectOrgchtList() throws Exception;
	
	
	/** 
	 * 회원 목록를 조회한다.
	 * @throws Exception
	 */
	public List<EgovMap> selectMberList(FckPcsFieldBookVO vo) throws Exception;
	
	/**
	 * 부서목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptInfoList() throws Exception;
	
	/**
	 * 부서별 회원목록
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectDeptCreatList() throws Exception;
	
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 공유방 권한자 조회
	 */
	public List<FckPcsFieldBookVO> selectCnrsAuthorList(HashMap<String, Object> map) throws Exception;
	
	
	/**
	 * 사업지구명 중복 조회
	 * @author DEVWORK
	 * @param mst_partname
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 29.
	 * @modified 
	 */
	public int selectFckPcsFieldBookCheckMstName(String mst_partname) throws Exception;


	/**
	 * 공유방 등록
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @return
	 * @since 2023. 11. 29.
	 * @modified
	 */
	public String insertCnrsSpce(FckPcsFieldBookVO fieldBookVO) throws Exception;


	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @since 2023. 11. 29.
	 * @modified
	 */
	public void insertCnrsSpceAuthorMgt(FckPcsFieldBookVO fieldBookVO) throws Exception;


	/**
	 * 대상지 등록
	 * @author DEVWORK
	 * @param fieldBookVO
	 * @param mFile
	 * @return
	 * @since 2023. 11. 29.
	 * @modified
	 */
	public JSONObject insertStripLand(FckPcsFieldBookVO fieldBookVO, MultipartFile mFile) throws Exception;


	/**
	 * 공유방 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @since 2023. 11. 29.
	 * @modified
	 */
	public void deleteCnrsSpce(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 목록 최근 연도 조회
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public String selectFckPcsFieldBookMaxYear() throws Exception;


	/**
	 * 공유방 연도 목록 조회
	 * @author DEVWORK
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public List<?> selectFckPcsFieldBookYear() throws Exception;


	/**
	 * 공유방 목록 조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public List<FckPcsFieldBookVO> selectFckPcsFieldBookList(FckPcsFieldBookVO searchVO) throws Exception;


	/**
	 * 공유방 갯수 조회
	 * @author DEVWORK
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @since 2023. 11. 30.
	 * @modified
	 */
	public int selectFckPcsFieldBookListTotCnt(FckPcsFieldBookVO searchVO) throws Exception;


	/**
	 * 공유방 상세조회
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookVO selectFckPcsFieldBookDetail(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 조사데이터 리스트조회
	 * @author DEVWORK
	 * @param searchItemVO
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public List<FckPcsStripLandVO> selectFckPcsFieldBookItemList(FckPcsFieldBookItemVO searchItemVO) throws Exception;


	/**
	 * 공유방 조사데이터 전체 건수 조회
	 * @author DEVWORK
	 * @param vo
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public int selectFckPcsFieldBookItemListTotCnt(FckPcsFieldBookItemVO vo) throws Exception;


	/**
	 * 공유방 권한 확인
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public String selectAuthorCheck(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 단건 상세조회
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 1.
	 * @modified
	 */
	public FckPcsFieldBookItemVO selectFieldBookDetailOne(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 조사데이터 전체 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void deleteCnrsSpceAllItem(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 조사완료 데이터 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void deleteCnrsSpceComptItem(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 조사데이터 단건 삭제
	 * @author DEVWORK
	 * @param commandMap
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void deleteFieldBookDetailOne(HashMap<String, Object> commandMap) throws Exception;


	/**
	 * 공유방 권한 수정
	 * @author DEVWORK
	 * @param vo
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void updateCnrsSpceAuthorMgt(FckPcsFieldBookVO fieldBookVO) throws Exception;


	/**
	 * 좌표계 변환
	 * @author DEVWORK
	 * @param projMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public List<EgovMap> selectFckPcsProjBessel(HashMap<String, Object> projMap) throws Exception;


	/**
	 * 조사데이터 수정
	 * @author DEVWORK
	 * @param searchVO
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public void updateFieldBookDetailOne(FckPcsFieldBookItemVO searchVO) throws Exception;


	/**
	 * 정밀점검 조사종류 조회
	 * @author DEVWORK
	 * @param commandMap
	 * @return
	 * @throws Exception
	 * @since 2023. 12. 4.
	 * @modified
	 */
	public String selectFckPcsSvyType(HashMap<String, Object> commandMap) throws Exception;
}