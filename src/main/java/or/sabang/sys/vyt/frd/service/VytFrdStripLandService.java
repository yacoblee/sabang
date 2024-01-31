package or.sabang.sys.vyt.frd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.grammar.v3.ANTLRParser.exceptionGroup_return;
import org.json.JSONObject;

import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;

public interface VytFrdStripLandService {
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 대상지 목록조회
	 */
	public List<VytFrdStripLandVO> selectVytFrdSldList(VytFrdStripLandVO searchVO) throws Exception;
	/**
	 * @param map
	 * @return
	 * @throws Exception
	 * @description 대상지 정보 상세조회
	 */
	public VytFrdStripLandVO selectVytFrdSldDetail(HashMap<String, Object> map) throws Exception;
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 대상지 조사데이터 리스트 조회
	 */
	public List<VytFrdStripLandVO> selectVytFrdSldItemList(VytFrdStripLandVO searchVO) throws Exception;
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @desciption 대상지 조사데이터 갯수조회
	 */
	public int selectVytFrdSldItemListTotCnt(VytFrdStripLandVO searchVO) throws Exception;
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 대상지 갯수조회
	 */
	public int selectVytFrdSldListTotCnt(VytFrdStripLandVO searchVO) throws Exception;
	/**
	 * 대상지 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectVytFrdSldMaxYear() throws Exception;
	/**
	 * 대상지 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytFrdSldYear() throws Exception;
	/*
	 * 대상지 사업 업로드 
	 */
	String insertSldSpce(VytFrdStripLandVO stripLandVO) throws Exception;
	/*
	 * shp파일 대상지 업로드
	 */
	public JSONObject insertFrdSvySld(EgovFormBasedFileVo vo) throws Exception;
	/**
	 * 대상지번호 없는 조회
	 * @throws Exception
	 */
	public List<VytFrdStripLandVO> selectNoSldId() throws Exception;
	
	/**
	 * 대상지 번호 부여
	 * @throws Exception
	 */
	public void updateNoSldId(HashMap<String, Object> commandMap) throws Exception;
	
	/**
	 * 대상지 단건 삭제
	 * @throws Exception
	 */
	public void deleteSldDetailOne(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 대상지 삭제
	 * @throws Exception
	 */
	public void deleteSldDetail(HashMap<String, Object> map) throws Exception;
	
	/*
	 * 대상지 단건 조회
	 */
	public VytFrdStripLandVO selectSldDetailOne(String smid) throws Exception;
	
	/**
	 * 대상지 단건 수정
	 */
	public void updateSldDetailOne(VytFrdStripLandVO searchVO) throws Exception;
	
	/**
	 * 대상지 분석
	 */
	public void createVytFrdAnalData(HashMap<String, Object> commandMap) throws Exception;
	
	public List<AnalFileVO> selectAnalImg(HashMap<String, Object> analParameterMap) throws Exception;
	
	/*
	 * 문화재 조회
	 */
	public List<EgovMap> selectCultureName(List<Integer> list) throws Exception;
	
	public AnalFileVO selectAnalFileDownDetail(String sn) throws Exception;
	
	/*
	 * 필지 조회
	 */
	public List<VytFrdStripLandVO> selectParcelList(String smid) throws Exception;
	
	/**
	 * 사업지구명 중복을 조회한다.
	 * @throws Exception
	 */
	public int selectVytFrdCheckSldName(String sldNm) throws Exception;
	
	/**
	 * 분석결과 파일 전체 다운로드
	 * @param searchVO
	 * @implNote 수정노선이 있는경우 수정 노선에 대해서만 다운로드
	 * @return
	 * @throws Exception
	 */
	public AnalFileVO downloadAnalAll(VytFrdStripLandVO searchVO) throws Exception;
}
