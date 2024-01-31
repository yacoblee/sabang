package or.sabang.cmm.service.impl;

import java.util.HashMap;
import java.util.List;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.cmm.service.CmmnDetailCode;

import org.springframework.stereotype.Repository;

/**
 * @Class Name : CmmUseDAO.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 데이터 접근 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.     이삼섭
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Repository("cmmUseDAO")
public class CmmUseDAO extends EgovComAbstractDAO {

    /**
     * 주어진 조건에 따른 공통코드를 불러온다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectCmmCodeDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("CmmUseDAO.selectCmmCodeDetail", vo);
    }

    /**
     * 공통코드로 사용할 조직정보를 불러온다.
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectOgrnztIdDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("CmmUseDAO.selectOgrnztIdDetail", vo);
    }

    /**
     * 공통코드로 사용할그룹정보를 불러온다.
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectGroupIdDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("CmmUseDAO.selectGroupIdDetail", vo);
    }
    
    /**
     * 공통코드로 사용할 관할정보를 불러온다.
     * @param vo
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<CmmnDetailCode> selectRegionDetail(ComDefaultCodeVO vo) throws Exception {
	return (List<CmmnDetailCode>) list("CmmUseDAO.selectRegionDetail", vo);
    }

	/**
	 * API KEY 목록 조회
	 * @author ipodo
	 * @name selectAPIKeyList
	 * @param map
	 * @return List<EgovMap>
	 */
	public List<EgovMap> selectAPIKeyList(HashMap<String, Object> map) {
		return (List<EgovMap>) list("CmmUseDAO.selectAPIKeyList", map);
	}
	
	/**
     * 땅밀림 코드정보 조회
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public String selectLcpCodeDetail(ComDefaultCodeVO vo){
    	return selectOne("CmmUseDAO.selectLcpCodeDetail", vo);
    }
    
    /**
     * 취약지역 실태조사 코드정보 조회
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<CmmnDetailCode> selectWkaCodeDetail(ComDefaultCodeVO vo){
    	return (List<CmmnDetailCode>) list("CmmUseDAO.selectWkaCodeDetail", vo);
    }

	/**
	 * 조사 코드 목록 조회
	 * @author DEVWORK
	 * @return
	 * @since 2023. 8. 18.
	 * @modified
	 */
	public List<EgovMap> selectSvyCodeLst() {
		return (List<EgovMap>) list("CmmUseDAO.selectSvyCodeLst", null);
	}

}
