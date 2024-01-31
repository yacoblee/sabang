package or.sabang.sys.gis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.gis.service.AnalService;
import or.sabang.sys.gis.service.MapService;

/**
 * @author ipodo
 * @name MapServiceImpl
 * @created 2021. 11. 18
 * @modified 2021. 11. 18
 * @description
 *
 *
 */
@Service("analService")
public class AnalServiceImpl extends EgovAbstractServiceImpl implements AnalService{

	@Resource(name = "analServiceDAO")
	private AnalServiceDAO analServiceDAO;
	
	@Override
	public void updateWaterShedAnalId(AnalFileVO vo) throws Exception{
		analServiceDAO.updateWaterShedAnalId(vo);
	}
	
	@Override
	public void insertEcrtcnlGeom(AnalFileVO vo) throws Exception{
		analServiceDAO.insertEcrtcnlGeom(vo);
	}
	
	@Override
	public void updateEcrtcnlGeom(AnalFileVO vo) throws Exception{
		analServiceDAO.updateEcrtcnlGeom(vo);
	}
	
	@Override
	public void insertWaterShedGeom(AnalFileVO vo) throws Exception{
		analServiceDAO.insertWaterShedGeom(vo);
	}
	
	@Override
	public void insertAnalFile(AnalFileVO vo) throws Exception {
		analServiceDAO.insertAnalFile(vo);
	}
	
	@Override
	public AnalFileVO selectAnalFileDownDetail(String sn) throws Exception{
		return analServiceDAO.selectAnalFileDownDetail(sn);
	}
	
	@Override
	public String selectIntersectAdminCode(AnalFileVO vo) throws Exception{
		return analServiceDAO.selectIntersectAdminCode(vo);
	}
	
	@Override
	public String selectEcrtcnlLonLatText(AnalFileVO vo) throws Exception{
		return analServiceDAO.selectEcrtcnlLonLatText(vo);
	}
	
	@Override
	public void deleteMntnTrnt(Map<String, Object> params) throws Exception{
		analServiceDAO.deleteMntnTrnt(params);
	}
}
