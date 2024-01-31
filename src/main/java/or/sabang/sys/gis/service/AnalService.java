package or.sabang.sys.gis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @author ipodo
 * @name MapService
 * @created 2021. 11. 18
 * @modified 2021. 11. 18
 * @description 
 *
 *
 */
public interface AnalService {
	
	public void updateWaterShedAnalId(AnalFileVO vo) throws Exception;
	
	public void insertEcrtcnlGeom(AnalFileVO vo) throws Exception;
	
	public void updateEcrtcnlGeom(AnalFileVO vo) throws Exception;
	
	public void insertWaterShedGeom(AnalFileVO vo) throws Exception;
	
	public void insertAnalFile(AnalFileVO vo) throws Exception;
	
	public AnalFileVO selectAnalFileDownDetail(String sn) throws Exception;
	
	public String selectIntersectAdminCode(AnalFileVO vo) throws Exception;
	
	public String selectEcrtcnlLonLatText(AnalFileVO vo) throws Exception;
	
	public void deleteMntnTrnt(Map<String, Object> params) throws Exception;
}
