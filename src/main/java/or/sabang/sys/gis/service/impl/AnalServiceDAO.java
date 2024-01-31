package or.sabang.sys.gis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.gis.service.AnalFileVO;

/**
 * @author ipodo
 * @name MapServiceDAO
 * @created 2021. 11. 18
 * @modified 2021. 11. 18
 * @description
 *
 *
 */
@Repository("analServiceDAO")
public class AnalServiceDAO extends EgovComAbstractDAO{
	
	public void updateWaterShedAnalId(AnalFileVO vo) throws Exception {
		update("analServiceDAO.updateWaterShedAnalId",vo);
	}
	
	public void insertEcrtcnlGeom(AnalFileVO vo) throws Exception {
		insert("analServiceDAO.insertEcrtcnlGeom", vo);
	}
	
	public void updateEcrtcnlGeom(AnalFileVO vo) throws Exception {
		insert("analServiceDAO.updateEcrtcnlGeom", vo);
	}
	
	public void insertWaterShedGeom(AnalFileVO vo) throws Exception {
		insert("analServiceDAO.insertWaterShedGeom", vo);
	}
	
	public void insertAnalFile(AnalFileVO vo) throws Exception {
		insert("analServiceDAO.insertAnalFile", vo);
	}
	
	public AnalFileVO selectAnalFileDownDetail(String sn) throws Exception{
		return (AnalFileVO) selectOne("analServiceDAO.selectAnalFileDownDetail", sn);
	}
	
	public String selectIntersectAdminCode(AnalFileVO vo) throws Exception{
		return selectOne("analServiceDAO.selectIntersectAdminCode", vo);
	}
	
	public String selectEcrtcnlLonLatText(AnalFileVO vo) throws Exception{
		return selectOne("analServiceDAO.selectEcrtcnlLonLatText", vo);
	}
	
	public String deleteMntnTrnt(Map<String, Object> params) throws Exception{
		return selectOne("analServiceDAO.deleteMntnTrnt", params);
	}
}
