package or.sabang.sys.vyt.ecb.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.cmm.service.impl.EgovComAbstractDAO;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.vyt.ecb.service.VytEcbAnalVO;
import or.sabang.sys.vyt.ecb.service.VytEcbStripLandVO;
import or.sabang.sys.vyt.ecb.service.VytEcbWorkVO;

@Repository("vytEcbAlsManageDAO")
public class VytEcbAlsManageDAO extends EgovComAbstractDAO {
	/**
	 * 사업 연도최대값을 조회한다.
	 * @throws Exception
	 */
	public String selectVytEcbWorkMaxYear() throws Exception{
		return selectOne("vytEcbAlsManageDAO.selectVytEcbWorkMaxYear","");
	}
	
	/**
	 * 사업 연도를 조회한다.
	 * @throws Exception
	 */
	public List<?> selectVytEcbWorkYear() throws Exception{
		return list("vytEcbAlsManageDAO.selectVytEcbWorkYear","");
	}
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 목록조회
	 */
	@SuppressWarnings("unchecked")
	public List<VytEcbAnalVO> selectVytEcbWorkList(VytEcbAnalVO searchVO) throws Exception{
		return (List<VytEcbAnalVO>) list("vytEcbAlsManageDAO.selectVytEcbWorkList", searchVO);
	};
	
	/**
	 * @param searchVO
	 * @return
	 * @throws Exception
	 * @description 사업 목록 갯수조회
	 */
	public int selectVytEcbWorkListTotCnt(VytEcbAnalVO searchVO) throws Exception{
		return (Integer) selectOne("vytEcbAlsManageDAO.selectVytEcbWorkListTotCnt", searchVO);
	};
	
	public VytEcbAnalVO selectVytEcbWorkDetail(VytEcbAnalVO searchVO) throws Exception{
		return selectOne("vytEcbAlsManageDAO.selectVytEcbWorkDetail", searchVO);
	};
	
	public List<AnalFileVO> selectVytEcbAnalDetail(VytEcbAnalVO searchVO) throws Exception{
		return selectList("vytEcbAlsManageDAO.selectVytEcbAnalDetail", searchVO);
	}
	
	/**
	 * 분석결과 파일 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<AnalFileVO> selectDownloadAnalAll(VytEcbAnalVO searchVO) throws Exception{
		return selectList("vytEcbAlsManageDAO.selectDownloadAnalAll", searchVO);
	}
	
	/**
	 * 분석결과 통계정보유무
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public int selectVytEcbAnalStatDataCnt(VytEcbAnalVO searchVO) throws Exception{
		return (Integer) selectOne("vytEcbAlsManageDAO.selectVytEcbAnalStatDataCnt", searchVO);
	};
	
	/**
	 * 분석결과 통계정보 엑셀 다운로드
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	public List<?> selectVytEcbAnalStatDataExcel(VytEcbAnalVO searchVO) throws Exception{
		return list("vytEcbAlsManageDAO.selectVytEcbAnalStatDataExcel",searchVO);
	};
	
	/**
	 * 지적조회
	 * @param cadastralWkt
	 * @return
	 * @throws Exception
	 */
	public List<EgovMap> selectCadastralDetail(String cadastralWkt) throws Exception{
		return selectList("vytEcbAlsManageDAO.selectCadastralDetail",cadastralWkt);
	}
}
