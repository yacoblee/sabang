package or.sabang.cmm.service.impl;

import java.util.List;

import or.sabang.cmm.service.AdministZoneVO;

import org.springframework.stereotype.Repository;

@Repository("administZoneDAO")
public class AdministZoneDAO extends EgovComAbstractDAO {

	public List<AdministZoneVO> selectAdministZoneCtprvn() throws Exception {
		return selectList("AdministZoneDAO.selectAdministZoneCtprvn");
	}
	
	public List<AdministZoneVO> selectAdministZoneSigngu(AdministZoneVO adminVO) throws Exception {
		return selectList("AdministZoneDAO.selectAdministZoneSigngu", adminVO);
	}
	
	public List<AdministZoneVO> selectAdministZoneEmd(AdministZoneVO adminVO) throws Exception {
		return selectList("AdministZoneDAO.selectAdministZoneEmd", adminVO);
	}
	
	public List<AdministZoneVO> selectAdministZoneRi(AdministZoneVO adminVO) throws Exception {
		return selectList("AdministZoneDAO.selectAdministZoneRi", adminVO);
	}
	
	public List<AdministZoneVO> selectLcpAdministZoneCtprvn() throws Exception {
		return selectList("AdministZoneDAO.selectLcpAdministZoneCtprvn");
	}
}
