package or.sabang.sys.gis.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovBrowserUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovBasicLogger;
import egovframework.com.cmm.util.EgovResourceCloseHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.sim.service.EgovFileCmprs;
import egovframework.rte.fdl.filehandling.EgovFileUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import or.sabang.sys.gis.service.AnalFileVO;
import or.sabang.sys.gis.service.AnalService;
import or.sabang.utl.SuperMapBasicUtils;
import or.sabang.utl.VytEcbSupMapUtils;
import or.sabang.utl.WaterShedUtils;

@Controller
public class AnalController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
//	@Resource(name="superMapUtils")
//	private SuperMapUtils superMapUtils;
	
//	@Resource(name = "administZoneService") 	
//	private AdministZoneService administZoneService;
	
	@Resource(name = "analService")
	private AnalService analService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AnalController.class);
	
	/**
	 * 유역분석
	 * @param x
	 * @param y
	 * @param mstId
	 * @param sldId
	 * @param svyType
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/gis/als/insertWaterShed.do")
	public ModelAndView insertWaterShed(
			@RequestParam(value="x") String x, 
			@RequestParam(value="y") String y,
			@RequestParam(value="mstId") String mstId,
			@RequestParam(value="sldId") String sldId,
			@RequestParam(value="svyType") String svyType,
			@RequestParam(value="lgstrCd") String lgstrCd,ModelMap model) throws Exception {
		long start = System.currentTimeMillis();
		// 추후 수정
		ModelAndView mv = null;
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			LOGGER.info("00.유역분석");
			mv = new ModelAndView("jsonView");
			
			try {
				String processId = SuperMapBasicUtils.getDatasetUuid();
	 			
				WaterShedUtils utils = new WaterShedUtils(processId);
				
	 			Map<String, Object> attr = new HashMap<String, Object>();
	 			
	 			attr.put("mst_id", Integer.valueOf(mstId));
	 			attr.put("sld_id", sldId);
	 			attr.put("anal_id", processId);
	 			attr.put("svy_type", svyType);
	 			attr.put("creat_user", loginVO.getId());
	 			attr.put("creat_dt", new Date());
	 			
	 			//x = "958349.879033332";
	 			//y = "1964555.02505774";
				AnalFileVO vo = utils.waterShedProcessing(Double.valueOf(x), Double.valueOf(y),lgstrCd,attr);
				vo.setMstId(Integer.valueOf(mstId));
				vo.setSldId(sldId);
				
				//zip파일 처리
				LOGGER.info(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
				boolean isCompressed = EgovFileCmprs.cmprsFile(vo.getFileStreCours()+File.separator+vo.getStreFileNm(), vo.getFileStreCours()+File.separator+vo.getStreFileNm().concat(".zip"));
				//boolean isCompressed = EgovFileCmprs.cmprsFile("D:/home/tomcat/FEIStorage/analysis/2022/10/24/1666619558727/test", "test.zip");
				EgovFileUtil.rm(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
				
				analService.insertAnalFile(vo);
				//vo 디비저장
				
				mv.addObject("status","success");
				mv.addObject("message","분석이 완료되었습니다.");
				mv.addObject("result",attr);
				//mv.addObject("message", egovMessageSource.getMessage("success.common.insert"));
				long end = System.currentTimeMillis();
				LOGGER.info("00.유역분석 소요시간 : "+(end-start)/1000.0+"ss");
			} catch (Exception e) {
				mv.addObject("status","fail");
				mv.addObject("message","분석을 실패하였습니다.\n에러내용 : "+e.getMessage());
				//--오류내용--
				//슈퍼맵 라이센스 안될경우 : connect types is: 11002 , 11001 , 11008 , 11000 , 11201 , 11202 , 11203 , 11204 , 10603 , 10602 , 10601 , 10636 , 10502 , 10501 , 10523 , 10503 , 10065 , 10066 , 10070 , 10071 , 10628 , 10002 , 10075 , 10001 , 10074 , 65400 , 65401 , 65402 , 65403 , 65404 , 65405 , 65406 , 65407 , 65408 , 65409 , 65410 , 65411 , 65412 , hasp_feature_not_found
			}
			
		}
		return mv;
	}
	
	/**
	 * 타당성평가 일괄분석
	 * @param mstId
	 * @param sldId
	 * @param analId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/gis/als/selectVytEcbReportImgAll.do")
	public ModelAndView selectVytEcbReportImgAll(@RequestParam("mstId") String mstId,
			@RequestParam("sldId") String sldId,
			@RequestParam("analId") String analId,
			@RequestParam("lneWkt") String lneWkt,
			@RequestParam("cadastralPnt") boolean cadastralPnt,
			@RequestParam("waterShedWkt") String waterShedWkt,
			@RequestParam("ecrtcnlWkt") String ecrtcnlWkt,
			ModelMap model) throws Exception{
		long start = System.currentTimeMillis();
		ModelAndView mv = null;
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		if(!isAuthenticated) {
			mv = new ModelAndView("redirect:/login.do");
			throw new ModelAndViewDefiningException(mv);
		} else {
			mv = new ModelAndView("jsonView");
			
			List<AnalFileVO> list = null;
			String oldAnalId = analId;
			//유역분석 분석ID 갱신
			AnalFileVO vo = new AnalFileVO();
			vo.setMstId(Integer.parseInt(mstId));
			vo.setSldId(sldId);
			vo.setAnalId(analId);
			
			if(waterShedWkt != null && waterShedWkt.length() > 0) {
				//유역분석 공간데이터를 업데이트
				//쉐이프파일로 저장 후 zip으로 압축처리
				//db에 저장 : analService.insertAnalFile(vo);
				analId = SuperMapBasicUtils.getDatasetUuid();
				String multiPolygon = waterShedWkt.replace("POLYGON", "MULTIPOLYGON").replace("((", "(((").replace("))", ")))");

				vo.setAnalId(analId);
				vo.setSvyType("vytecb");
				vo.setCreatUser(loginVO.getId());
				vo.setWaterShedWkt(multiPolygon);
				
				analService.insertWaterShedGeom(vo);
				
				WaterShedUtils waterShedUtils = new WaterShedUtils(analId);
				
				AnalFileVO waterShedVo = waterShedUtils.waterShedDrawProcessing(vo);
				
				//zip파일 처리
				LOGGER.info(waterShedVo.getFileStreCours()+File.separator+waterShedVo.getStreFileNm());
				boolean isCompressed = EgovFileCmprs.cmprsFile(waterShedVo.getFileStreCours()+File.separator+waterShedVo.getStreFileNm(), waterShedVo.getFileStreCours()+File.separator+waterShedVo.getStreFileNm().concat(".zip"));
				//boolean isCompressed = EgovFileCmprs.cmprsFile("D:/home/tomcat/FEIStorage/analysis/2022/10/24/1666619558727/test", "test.zip");
				EgovFileUtil.rm(waterShedVo.getFileStreCours()+File.separator+waterShedVo.getStreFileNm());
				
				analService.insertAnalFile(waterShedVo);
			}else {
				analService.updateWaterShedAnalId(vo);
			}
			
			if(ecrtcnlWkt != null && ecrtcnlWkt.length() > 0) {
				vo.setEcrtcnlWkt(ecrtcnlWkt);
				analService.insertEcrtcnlGeom(vo);//유출구 입력시					
			}else {
				if(!oldAnalId.equals(vo.getAnalId())) {
					vo.setOldAnalId(oldAnalId);
					analService.updateEcrtcnlGeom(vo);
				}
			}
			
			String adminCode = analService.selectIntersectAdminCode(vo);
			String ecrtcnlLonLatText = analService.selectEcrtcnlLonLatText(vo);
			
			VytEcbSupMapUtils utils = new VytEcbSupMapUtils(analId);
			//1. 대상지위치(시도)
			LOGGER.info("01. 대상지위치(시도)");
			list = utils.createEcbSidoClipImg(mstId,sldId,analId,adminCode);
			insertAnalFileList(list);
			
			//2. 대상지위치(시군구)
			LOGGER.info("02. 대상지위치(시군구)");
			list = utils.createEcbSignguClipImg(mstId,sldId,analId,adminCode);
			insertAnalFileList(list);
			
			//3. 대상지위치(수치지형도:1/25000)
			LOGGER.info("03. 대상지위치(수치지형도)");
			list = utils.createEcbSldLocationTopoMapImg(mstId,sldId,analId,ecrtcnlLonLatText);
			insertAnalFileList(list);
			
			//4. 사업대상지 관계지적도(1/1200)
			LOGGER.info("04. 사업대상지 관계지적도(1/1200)");
			list = utils.createEcbCadastralMapImg(mstId,sldId,analId,cadastralPnt,0.001029);
			insertAnalFileList(list);
			
			//5. 사업대상지 관계지적도(1/2400)
			LOGGER.info("05. 사업대상지 관계지적도(1/2400)");
			list = utils.createEcbCadastralMapImg(mstId,sldId,analId,cadastralPnt,0.0005145);
			insertAnalFileList(list);
			
			//6. 대상지위치도(지적)
			LOGGER.info("06. 대상지위치도(지적)");
			list = utils.createEcbSldLocationImg(mstId,sldId,analId);
			insertAnalFileList(list);
			
			//7. 대상지위치도(영상)
			LOGGER.info("07. 대상지위치도(영상)");
			list = utils.createEcbSldLocationSatImg(mstId,sldId,analId);
			insertAnalFileList(list);
			
			//8. 수계망(3ha)
			LOGGER.info("08. 수계망(3ha)");
			list = utils.createEcbStream3haClipImg(mstId,sldId,analId);
			insertAnalFileList(list);
			
			//9. 수계망(5ha)
			LOGGER.info("09. 수계망(5ha)");
			list = utils.createEcbStream5haClipImg(mstId,sldId,analId);
			insertAnalFileList(list);
			
			//지황분석
			//10. 경사분포도
			LOGGER.info("10. 경사분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"slope","range");
			insertAnalFileList(list);
			
			//11. 향분포도
			LOGGER.info("11. 향분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"aspect","single");
			insertAnalFileList(list);
			
			//12. 표고분포도
			LOGGER.info("12. 표고분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"dem","range");
			insertAnalFileList(list);
			
			//13. 지질분포도
			//가지고 있는 지질도 심볼설정방법...범례...예시의 항목이 없음..
			LOGGER.info("13. 지질분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"geological","uniqe");
			insertAnalFileList(list);
			
			//임황분석
			//14. 임상분포도
			LOGGER.info("14. 임상분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"koftr","uniqe");
			insertAnalFileList(list);
			
			//15. 영급분포도
			LOGGER.info("15. 영급분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"agcls","uniqe");
			insertAnalFileList(list);
			
			//16. 소밀도분포도
			LOGGER.info("16. 소밀도분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"dnst","uniqe");
			insertAnalFileList(list);
			
			//17. 경급분포도
			LOGGER.info("17. 경급분포도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"dmcls","uniqe");
			insertAnalFileList(list);
			
			//생태자연도
			//18. 생태자연도
			LOGGER.info("18. 생태자연도");
			//list = utils.createEcbClipImg(mstId,sldId,analId,"nature","uniqe");
			list = utils.createEcbEclgyNatureMapImg(mstId,sldId,analId);
			insertAnalFileList(list);
			//
			//토사유출량(USLE)
			//18. 유사전달률,토사유출량(tonnes/year),토사유출량(m2/year) : zonal statistics as table
			
			//19. 산사태위험등급도
			LOGGER.info("19. 산사태위험등급도");
			list = utils.createEcbClipImg(mstId,sldId,analId,"landslide","single");
			insertAnalFileList(list);
			
			//20. 계류경사분석
			LOGGER.info("20. 계류경사분석");
			if(lneWkt != null && lneWkt.length() > 0) {
				
				Map<String, Object> attr = new HashMap<String, Object>();
	 			
	 			attr.put("mst_id", Integer.valueOf(mstId));
	 			attr.put("sld_id", sldId);
	 			attr.put("anal_id", analId);
	 			attr.put("svy_type", "vytecb");
	 			attr.put("creat_user", loginVO.getId());
	 			attr.put("creat_dt", new Date());
	 			
	 			analService.deleteMntnTrnt(attr);
	 			
				list = utils.createEcbMntnTrntSlopeImg(mstId,sldId,analId,lneWkt,attr);
				insertAnalFileList(list);
			}else {
				LOGGER.info("20. 계류경사분석 : 라인정보가 없습니다.");
			}
			
			utils.closeWorkspace();
			
			mv.addObject("result",vo);
			mv.addObject("status","success");
			mv.addObject("message","분석이 완료되었습니다.");
		}
		long end = System.currentTimeMillis();
		LOGGER.debug("Execution Time : "+(end-start)/1000.0+"ss");
		return mv;
	}
	
	/**
	 * 분석파일 및 이미지 다운로드
	 * @param fileSn
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/gis/als/selectAnalFileDown.do")
	public void selectAnalFileDown(@RequestParam("fileSn") String fileSn,
			ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		AnalFileVO vo = analService.selectAnalFileDownDetail(fileSn);
		saveAnalFileImg(vo, request, response);
	}
	
	/**
	 * 분석결과리스트 저장
	 * @param list
	 * @throws Exception
	 */
	private void insertAnalFileList(List<AnalFileVO> list) throws Exception{
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();   
		for (AnalFileVO vo : list) {
			vo.setCreatUser(loginVO.getId());
			
			if(vo.getFileExtsn().equals("zip")) {
				boolean isCompressed = EgovFileCmprs.cmprsFile(vo.getFileStreCours()+File.separator+vo.getStreFileNm(), vo.getFileStreCours()+File.separator+vo.getStreFileNm().concat(".zip"));
				//boolean isCompressed = EgovFileCmprs.cmprsFile("D:/home/tomcat/FEIStorage/analysis/2022/10/24/1666619558727/test", "test.zip");
				vo.setStatData(null);
				EgovFileUtil.rm(vo.getFileStreCours()+File.separator+vo.getStreFileNm());
			}
			analService.insertAnalFile(vo);
		}
	}
	
	/**
	 * 이미지 다운로드
	 * @param vo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void saveAnalFileImg(AnalFileVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String saveFileNm = vo.getFileStreCours()+File.separator+vo.getStreFileNm()+".".concat(vo.getFileExtsn());
		
		File uFile = new File(saveFileNm);
		long fSize = uFile.length();
		if (fSize > 0) {
			String mimetype = "application/x-msdownload";
			
			String userAgent = request.getHeader("User-Agent");
			HashMap<String,String> result = EgovBrowserUtil.getBrowser(userAgent);
			if ( !EgovBrowserUtil.MSIE.equals(result.get(EgovBrowserUtil.TYPEKEY)) ) {
				mimetype = "application/x-stuff";
			}

			String contentDisposition = EgovBrowserUtil.getDisposition(vo.getSldId().concat("_").concat(vo.getOrignlFileNm().concat(".").concat(vo.getFileExtsn())),userAgent,"UTF-8");
			response.setContentType(mimetype);
			response.setHeader("Content-Disposition", contentDisposition);
			response.setContentLengthLong(fSize);

			BufferedInputStream in = null;
			BufferedOutputStream out = null;

			try {
				in = new BufferedInputStream(new FileInputStream(uFile));
				out = new BufferedOutputStream(response.getOutputStream());

				FileCopyUtils.copy(in, out);
				out.flush();
			} catch (IOException ex) {
				// 다음 Exception 무시 처리
				// Connection reset by peer: socket write error
				EgovBasicLogger.ignore("IO Exception", ex);
			} finally {
				EgovResourceCloseHelper.close(in, out);
			}
		}
	}
}
