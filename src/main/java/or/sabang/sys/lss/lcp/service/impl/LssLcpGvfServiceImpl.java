package or.sabang.sys.lss.lcp.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.utl.fcc.service.EgovFormBasedFileVo;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.lcp.service.LssLcpGvfService;
import or.sabang.sys.lss.lcp.service.LssLcpGvfVO;
import or.sabang.sys.lss.lcp.service.LssLcpRankVO;
import or.sabang.sys.lss.lcp.service.LssLcpSvyStripLandVO;
import or.sabang.utl.LssLcpSupMapUtils;
import or.sabang.utl.SuperMapBasicUtils;

@Service("lssLcpGvfService")
public class LssLcpGvfServiceImpl extends EgovAbstractServiceImpl implements LssLcpGvfService {
	private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.gvf");
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssLcpGvfDAO")
	private LssLcpGvfDAO lssLcpGvfDAO;

	/** egovFileIdGnrService */
	@Resource(name="egovGvfIdGnrService")
	private EgovIdGnrService egovGvfIdGnrService;
	
	@Override
	public JSONObject insertLcpSvyGvf(EgovFormBasedFileVo vo) throws Exception {
		JSONObject returnLog = new JSONObject();
		
		String midPath = vo.getServerSubPath();
		String originFileName = vo.getFileName();
		String zipFileName = vo.getPhysicalName();
		String uploadFilePath = uploadDir+midPath+File.separator+zipFileName;
		String deComFilePath = uploadFilePath.replace(".zip", "");

		boolean deCompressed = EgovFileCmprs.decmprsFile(uploadFilePath, deComFilePath);
		File shpDir = null;
		
		if(deCompressed) {
			shpDir = new File(deComFilePath);
			
			if(shpDir.isDirectory()) {
				
				File[] shpFiles = shpDir.listFiles(new FileFilter() {
					@Override 
					public boolean accept(File pathname) {
						if(pathname.isDirectory()) {
							return false;
						}
						return pathname.getName().toLowerCase().endsWith(".shp");
					}
				});
				
				int cnt = 1;
				if(shpFiles != null) {
					LssLcpSupMapUtils utils = new LssLcpSupMapUtils();
					for(File shp : shpFiles) {
						Map<String, Object> map = new HashMap<>();
				        map.put("url", shp.toURI().toURL());
				        
				        boolean delete_all = (cnt == 1 ? true : false);
				        cnt++;
				        
				        returnLog = utils.uploadInsertShape(shp.getAbsolutePath(), "tf_feis_lcp_gvf", false);
					}
					utils.closeWorkspace();
				}else {
					LOGGER.error("shp 파일이 존재하지 않습니다.");
					returnLog.put("status", "fail");
					returnLog.put("message", "shp 파일이 존재하지 않습니다.");
				}
				
//				if(shpDir != null) {
//					FileUtils.deleteDirectory(shpDir);
//				}
			}else {
				LOGGER.error("압축 해제 결과 폴더가 없습니다.");
				returnLog.put("status", "fail");
				returnLog.put("message", "압축 해제 결과 폴더가 없습니다.");
			}
		}else {
			LOGGER.error("압축 해제 중에 오류가 발생하였습니다.");
			returnLog.put("status", "fail");
			returnLog.put("message", "압축 해제 중에 오류가 발생하였습니다.");
		}
		return returnLog;
	}
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectLcpGvfListTotCnt(LssLcpRankVO searchVO) throws Exception {
        return lssLcpGvfDAO.selectLcpGvfListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<?> selectLcpGvfList(LssLcpRankVO searchVO) throws Exception {
		return lssLcpGvfDAO.selectLcpGvfList(searchVO);
	}
	
	/**
	 * 제보 등록 목록을 조회한다.
	 */
	@Override
	public List<EgovMap> selectLcpGvfAddList() throws Exception {
		return lssLcpGvfDAO.selectLcpGvfAddList();
	}
	
	/**
	 * 제보데이터 임상도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpGvfVO selectGvfImInfo(LssLcpGvfVO gvfVO) throws Exception {
		return lssLcpGvfDAO.selectGvfImInfo(gvfVO);
	}
	
	/**
	 * 제보데이터 입지도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpGvfVO selectGvfIjInfo(LssLcpGvfVO gvfVO) throws Exception {
		return lssLcpGvfDAO.selectGvfIjInfo(gvfVO);
	}
	
	/**
	 * 제보데이터 지질도 정보를 조회한다.
	 * @throws Exception
	 */
	public LssLcpGvfVO selectGvfGlInfo(LssLcpGvfVO gvfVO) throws Exception {
		return lssLcpGvfDAO.selectGvfGlInfo(gvfVO);
	}
	
	/**
	 * 제보 데이터를 수정한다.
	 */
	@Override
	public void updateLcpGvfData(LssLcpGvfVO gvfVO) throws Exception {

		//고유아이디 셋팅
		String uniqId = egovGvfIdGnrService.getNextStringId();
		gvfVO.setUniqId(uniqId);
		
		lssLcpGvfDAO.updateLcpGvfData(gvfVO);
		lssLcpGvfDAO.insertSvySldInfo(gvfVO);
		
	}
	
	/**
	 * 제보 데이터를 삭제한다.
	 */
	@Override
	public void deleteLcpGvfData(LssLcpGvfVO gvfVO) throws Exception {
		lssLcpGvfDAO.deleteLcpGvfData(gvfVO);
		lssLcpGvfDAO.deleteSvySldInfo(gvfVO);
		lssLcpGvfDAO.updateSvySldRegInfo(gvfVO);
	}
	
	/**
	 * 제보 쉐이프파일을 다운로드한다.
	 */
	@Override
	public AnalFileVO downloadLcpSvyGvf(String pnu) throws Exception{
		String[] fields = new String[]{"uniq_id","creat_dt","sld_id"};
		String where = (pnu != null ? "lgstr_cd like '"+pnu+"%'" : "1=1");
		
		LssLcpSupMapUtils utils = new LssLcpSupMapUtils();
		
		AnalFileVO vo = utils.exportDbToFile("tf_feis_lcp_gvf", where,fields);
		
		boolean isCompressed = EgovFileCmprs.cmprsFile(vo.getFileStreCours()+File.separator+vo.getStreFileNm(), vo.getFileStreCours()+File.separator+vo.getStreFileNm().concat(".zip"));
		//boolean isCompressed = EgovFileCmprs.cmprsFile("D:/home/tomcat/FEIStorage/analysis/2022/10/24/1666619558727/test", "test.zip");
		EgovFileUtil.rm(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
		
		vo.setFileExtsn("zip");
		return vo;
	}
}
