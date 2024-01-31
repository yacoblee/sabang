package or.sabang.cmm.service;

import java.util.List;
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;

public interface AdministZoneService {

	/**
	 * 시도리스트를 리턴한다.
	 * @return 행정구역 시도정보 List
	 * @throws Exception
	 */
	public List<AdministZoneVO> selectAdministZoneCtprvn() throws Exception;
	
	/**
	 * 시군구리스트를 리턴한다.
	 * @param adminVO
	 * @return
	 * @throws Exception
	 */
	public List<AdministZoneVO> selectAdministZoneSigngu(AdministZoneVO adminVO) throws Exception;

	/**
	 * 읍면동리스트를 리턴한다.
	 * @param adminVO
	 * @return
	 * @throws Exception
	 */
	public List<AdministZoneVO> selectAdministZoneEmd(AdministZoneVO adminVO) throws Exception;
	
	/**
	 * 리 리스트를 리턴한다.
	 * @param adminVO
	 * @return
	 * @throws Exception
	 */
	public List<AdministZoneVO> selectAdministZoneRi(AdministZoneVO adminVO) throws Exception;
	
	/**
	 * 땅밀림 시도리스트를 리턴한다.
	 * @return 행정구역 시도정보 List
	 * @throws Exception
	 */
	public List<AdministZoneVO> selectLcpAdministZoneCtprvn() throws Exception;
}
