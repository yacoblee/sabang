package or.sabang.cmm.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import or.sabang.cmm.service.CmmnDetailCode;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovCmmUseServiceImpl.java
 * @Description : 공통코드등 전체 업무에서 공용해서 사용해야 하는 서비스를 정의하기위한 서비스 구현 클래스
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
@Service("administZoneService")
public class AdministZoneServiceImpl extends EgovAbstractServiceImpl implements AdministZoneService {

    @Resource(name = "administZoneDAO")
    private AdministZoneDAO administZoneDAO;

    /**
     * 행정구역 시도리스트를 조회한다.
     * @return
     * @throws Exception
     */
    public List<AdministZoneVO> selectAdministZoneCtprvn() throws Exception {
    	return administZoneDAO.selectAdministZoneCtprvn();
    }
    
    /**
     * 행정구역 시군구리스트를 조회한다.
     * @param adminVO
     * @return
     * @throws Exception
     */
    public List<AdministZoneVO> selectAdministZoneSigngu(AdministZoneVO adminVO) throws Exception {
    	return administZoneDAO.selectAdministZoneSigngu(adminVO);
    }
    
    /**
     * 행정구역 읍면동리스트를 조회한다.
     * @param adminVO
     * @return
     * @throws Exception
     */
    public List<AdministZoneVO> selectAdministZoneEmd(AdministZoneVO adminVO) throws Exception {
    	return administZoneDAO.selectAdministZoneEmd(adminVO);
    }
    
    /**
     * 행정구역 리 리스트를 조회한다.
     * @param adminVO
     * @return
     * @throws Exception
     */
    public List<AdministZoneVO> selectAdministZoneRi(AdministZoneVO adminVO) throws Exception {
    	return administZoneDAO.selectAdministZoneRi(adminVO);
    }
    
    /**
     * 땅밀림 행정구역 시도리스트를 조회한다.
     * @return
     * @throws Exception
     */
    public List<AdministZoneVO> selectLcpAdministZoneCtprvn() throws Exception {
    	return administZoneDAO.selectLcpAdministZoneCtprvn();
    }
}
