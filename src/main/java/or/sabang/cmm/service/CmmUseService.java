package or.sabang.cmm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;



/**
 *
 * 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기 위한 서비스 인터페이스
 * @author 공통서비스 개발팀 이삼섭
 * @since 2009.04.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.11  이삼섭          최초 생성
 *
 * </pre>
 */
public interface CmmUseService {

    /**
     * 공통코드를 조회한다.
     *
     * @param vo
     * @return List(코드)
     * @throws Exception
     */
    public List<CmmnDetailCode> selectCmmCodeDetail(ComDefaultCodeVO vo) throws Exception;

    /**
     * ComDefaultCodeVO의 리스트를 받아서 여러개의 코드 리스트를 맵에 담아서 리턴한다.
     *
     * @param voList
     * @return Map(코드)
     * @throws Exception
     */
    public Map<String, List<CmmnDetailCode>> selectCmmCodeDetails(List<?> voList) throws Exception;

    /**
     * 조직정보를 코드형태로 리턴한다.
     *
     * @param 조회조건정보 vo
     * @return 조직정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectOgrnztIdDetail(ComDefaultCodeVO vo) throws Exception;

    /**
     * 그룹정보를 코드형태로 리턴한다.
     *
     * @param 조회조건정보 vo
     * @return 그룹정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectGroupIdDetail(ComDefaultCodeVO vo) throws Exception;
    
    /**
     * 관할정보를 코드형태로 리턴한다.
     * @param 조회조건정보 vo
     * @return 관할정보 List
     * @throws Exception
     */
    public List<CmmnDetailCode> selectRegionDetail(ComDefaultCodeVO vo) throws Exception;

    /**
     * 프록시 설정.
     * @param request
     * @return response
     * @throws Exception
     */
	public void proxy(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * API KEY 목록 조회
	 * @author ipodo
	 * @name selectAPIKeyList
	 * @param map
	 * @return List<EgovMap>
	 */
	public List<EgovMap> selectAPIKeyList(HashMap<String, Object> map);
	
	/**
     * 땅밀림 코드정보 조회
     *
     * @param 조회조건정보 vo
     * @return 
     * @throws Exception
     */
	public String selectLcpCodeDetail(ComDefaultCodeVO vo);
	
	/**
	 * 취약지역 실태조사 코드정보 조회
	 *
	 * @param 조회조건정보 vo
	 * @return 
	 * @throws Exception
	 */
	public List<CmmnDetailCode> selectWkaCodeDetail(ComDefaultCodeVO vo);

	/**
	 * 조사 코드 목록 조회
	 * @author DEVWORK
	 * @return
	 * @since 2023. 8. 18.
	 * @modified
	 */
	public List<EgovMap> selectSvyCodeLst();
}
