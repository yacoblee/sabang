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
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.lss.lcp.service.LssLcpRankService;
import or.sabang.sys.lss.lcp.service.LssLcpRankVO;
import or.sabang.utl.LssLcpSupMapUtils;

@Service("lssLcpRankService")
public class LssLcpRankServiceImpl extends EgovAbstractServiceImpl implements LssLcpRankService {
	private final String uploadDir = EgovProperties.getProperty("Globals.fileStorePath.rank");
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="lssLcpRankDAO")
	private LssLcpRankDAO lssLcpRankDAO;
	
	@Override
	public JSONObject insertLcpSvyRank(EgovFormBasedFileVo vo) throws Exception {
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
				        
				        returnLog = utils.uploadInsertShape(shp.getAbsolutePath(), "tf_feis_lcp_rank", delete_all);
				        lssLcpRankDAO.updateLcpRankDate();
					}
				}else {
					LOGGER.error("shp 파일이 존재하지 않습니다.");
					returnLog.put("status", "fail");
					returnLog.put("message", "shp 파일이 존재하지 않습니다.");
				}
				
				if(shpDir != null) {
					FileUtils.deleteDirectory(shpDir);
				}
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
	 * 랭크 고도값 추출(zonal statistics)
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public JSONObject createLcpSvyRankElevation() throws Exception{
//		JSONObject returnLogs = new JSONObject();
//		
//		returnLogs = superMapUtils.zonalStatisticsProcessing("tf_feis_lcp_rank", "tf_feis_dem", "ta_feis_lcp_rank_result", "ta_feis_lcp_rank_result_tb", "SmID");
//		return returnLogs;
//	}
	
	/**
	 * 랭크 경사도 추출(zonal statistics)
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public JSONObject createLcpSvyRankSlope() throws Exception{
//		JSONObject returnLogs = new JSONObject();
//		
//		returnLogs = superMapUtils.zonalStatisticsProcessing("tf_feis_lcp_rank", "tf_feis_slope", "ta_feis_lcp_slope_result", "ta_feis_lcp_rank_slope_tb", "SmID");
//		return returnLogs;
//	}
	
	/**
	 * 랭크 토심값 추출(zonal statistics)
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public JSONObject createLcpSvyRankSld() throws Exception{
//		JSONObject returnLogs = new JSONObject();
//		
//		returnLogs = superMapUtils.zonalStatisticsProcessing("tf_feis_lcp_rank", "tf_feis_ij100_sld", "ta_feis_lcp_sld_result", "ta_feis_lcp_rank_sld_tb", "SmID");
//		return returnLogs;
//	}
	
	/**
	 * 대상지 총 갯수를 조회한다.
	 */
	@Override
	public int selectLcpRankListTotCnt(LssLcpRankVO searchVO) throws Exception {
        return lssLcpRankDAO.selectLcpRankListTotCnt(searchVO);
	}

	/**
	 * 대상지 목록을 조회한다.
	 */
	@Override
	public List<LssLcpRankVO> selectLcpRankList(LssLcpRankVO searchVO) throws Exception {
		return lssLcpRankDAO.selectLcpRankList(searchVO);
	}
	
	/**
	 * 랭크 쉐이프파일을 다운로드한다.
	 */
	@Override
	public AnalFileVO downloadLcpSvyRank() throws Exception{
		String[] fields = new String[]{"y18","y19","y20","y21","y22","creat_dt","sld_id"};
		
		LssLcpSupMapUtils utils = new LssLcpSupMapUtils();
		
		AnalFileVO vo = utils.exportDbToFile("tf_feis_lcp_rank", null,fields);
		
		boolean isCompressed = EgovFileCmprs.cmprsFile(vo.getFileStreCours()+File.separator+vo.getStreFileNm(), vo.getFileStreCours()+File.separator+vo.getStreFileNm().concat(".zip"));
		//boolean isCompressed = EgovFileCmprs.cmprsFile("D:/home/tomcat/FEIStorage/analysis/2022/10/24/1666619558727/test", "test.zip");
		EgovFileUtil.rm(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
		
		vo.setFileExtsn("zip");
		return vo;
	}
	/**
	 * 랭크 등록일자 업데이트
	 * @throws Exception
	 */
//	public void updateLcpRankDate() throws Exception{
//		lssLcpRankDAO.updateLcpRankDate();
//	}

}