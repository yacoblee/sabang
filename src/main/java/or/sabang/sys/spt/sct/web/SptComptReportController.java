package or.sabang.sys.spt.sct.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.clipsoft.org.json.simple.JSONObject;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import or.sabang.cmm.service.AdministZoneService;
import or.sabang.cmm.service.AdministZoneVO;
import or.sabang.cmm.service.CmmUseService;
import or.sabang.cmm.service.EgovFileMngService;
import or.sabang.cmm.service.EgovFileMngUtil;
import or.sabang.mng.sec.drm.service.DeptAuthorService;
import or.sabang.sys.spt.rpt.service.SptRptReportListVO;
import or.sabang.sys.spt.sct.service.SptComptReportFileVO;
import or.sabang.sys.spt.sct.service.SptComptReportService;

@Controller
public class SptComptReportController {
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** cmmUseService */
	@Resource(name = "cmmUseService")
	private CmmUseService cmmUseService;
	
	/** egovMessageSource */
	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
	
	@Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
	@Resource(name = "sptComptReportService") 	
	private SptComptReportService sptComptReportService;
	
	@Resource(name = "administZoneService") 	
	private AdministZoneService administZoneService;
	
	/** deptAuthorService */
	@Resource(name = "deptAuthorService")
    private DeptAuthorService deptAuthorService;
	
	@Resource(name = "reportUploadPath")
	String reportUploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(SptComptReportController.class);
	
	int cnt_total = 0;

	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 기초조사 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectBscComptReportList.do")
    public String selectBscComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectBscComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectBscComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectBscComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptBscComptList";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 기초조사 완료보고서 상세조회
	 */
//	@RequestMapping(value = "/sys/spt/sct/selectRptBscComptDetail.do")
//    public String selectRptBscComptDetail (@RequestParam(value="mstId") String id, ModelMap model) throws Exception {
//		return "sys/spt/sct/rptBscComptDetail";
//	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 기초조사 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertBscComptReportPopup.do")
	public String insertRptBscFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		
		
		return "sys/spt/sct/uploadBscComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 기초조사 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertBscComptReportFile.do")
    public ModelAndView insertReportFile (HttpServletResponse res, HttpServletRequest req, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
		
		if(fileVO.getCorpNm().length() > 0) {
			if(fileVO.getCorpNm().toString().equals("ORGNZT_0000000000000")) {
				fileVO.setCorpNm("한국치산기술협회");
			}else {
				fileVO.setCorpNm("산림조합중앙회");
			}
		}
		
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "BSC_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyy").format(date)) + File.separator + (new SimpleDateFormat("MM").format(date)) + File.separator + (new SimpleDateFormat("dd").format(date)));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("BSC_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertBscComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Bsc");
		} catch(Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		} 
		
		return mv;
		//return "forward:/sys/spt/sct/rptBscComptList.do";
		
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 기초조사 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectBscComptReportDownload.do")
    public void selectBscFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectBscComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param fileVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description  기초조사 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteBscComptReportFile.do")
	public ModelAndView deleteBscFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteBscComptReportFile(fileVO);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
		    //delFile.delete();
		    mv.addObject("status","success");
		    model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 외관점검 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectAprComptReportList.do")
    public String selectAprComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectAprComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectAprComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectAprComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptAprComptList";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 외관점검 완료보고서 상세조회
	 */
//	@RequestMapping(value = "/sys/spt/sct/rptAprComptDetail.do")
//    public String rptAprComptDetail (@RequestParam(value="mstId") String id, ModelMap model) throws Exception {
//		return "sys/spt/sct/rptAprComptDetail";
//	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 외관점검 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertAprComptReportPopup.do")
	public String insertRptAprFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		
		
		return "sys/spt/sct/uploadAprComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 외관점검 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertAprComptReportFile.do")
    public ModelAndView insertAprReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "APR_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd")));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("APR_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertAprComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Apr");
		}catch (Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		}
		
//		return "forward:/sys/spt/sct/selectAprComptReportList.do";
		return mv;
		
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 외관점검 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectAprComptReportDownload.do")
    public void selectAprFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectAprComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description  외관점검 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteAprComptReportFile.do")
	public ModelAndView deleteAprFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteAprComptReportFile(fileVO);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 땅밀림실태조사 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectLcpComptReportList.do")
    public String selectLcpComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectLcpComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectLcpComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectLcpComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptLcpComptList";
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 땅밀림실태조사 완료보고서 상세조회
	 */
//	@RequestMapping(value = "/sys/spt/sct/selectRptLcpComptDetail.do")
//    public String selectRptLcpComptDetail (@RequestParam(value="mstId") String id, ModelMap model) throws Exception {
//		return "sys/spt/sct/rptLcpComptDetail";
//	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 땅밀림실태조사 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertLcpComptReportPopup.do")
	public String insertRptLcpFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		return "sys/spt/sct/uploadLcpComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 땅밀림실태조사 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertLcpComptReportFile.do")
    public ModelAndView insertLcpReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "LCP_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyy").format(date)) + File.separator + (new SimpleDateFormat("MM").format(date)) + File.separator + (new SimpleDateFormat("dd").format(date)));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("LCP_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertLcpComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Lcp");
		} catch(Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		} 
		
		return mv;
		//return "forward:/sys/spt/sct/rptLcpComptList.do";
		
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 땅밀림실태조사 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectLcpComptReportDownload.do")
    public void selectLcpFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectLcpComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param fileVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 땅밀림실태조사 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteLcpComptReportFile.do")
	public ModelAndView deleteLcpFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteLcpComptReportFile(fileVO);
		}catch (Exception e) {
		    e.printStackTrace();
		}finally {
		    //File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
		    //delFile.delete();
		    mv.addObject("status","success");
		    model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 취약지역 실태조사 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectWkaComptReportList.do")
    public String selectWkaComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectWkaComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectWkaComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectWkaComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptWkaComptList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 취약지역실태조사 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertWkaComptReportPopup.do")
	public String insertRptWkaFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		return "sys/spt/sct/uploadWkaComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 취약지역실태조사 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertWkaComptReportFile.do")
    public ModelAndView insertWkaReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("WKA_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertWkaComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Wka");
		} catch(Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		} 
		
		return mv;
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 취약지역실태조사 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectWkaComptReportDownload.do")
    public void selectWkaFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectWkaComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param fileVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 취약지역 실태조사 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteWkaComptReportFile.do")
	public ModelAndView deleteWkaFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteWkaComptReportFile(fileVO);
		}catch (Exception e) {
		    e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 취약지역해제조사 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectCnlComptReportList.do")
    public String selectCnlComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selecCnlComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectCnlComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectCnlComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptCnlComptList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 취약지역해제조사 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertCnlComptReportPopup.do")
	public String insertRptCnlFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		return "sys/spt/sct/uploadCnlComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 취약지역해제조사 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertCnlComptReportFile.do")
    public ModelAndView insertCnlReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("CNL_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertCnlComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Cnl");
		} catch(Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		} 
		
		return mv;
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 취약지역해제조사 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectCnlComptReportDownload.do")
    public void selectCnlFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectCnlComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param fileVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 취약지역 해제조사 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteCnlComptReportFile.do")
	public ModelAndView deleteCnlFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteCnlComptReportFile(fileVO);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}

	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 사방사업타당성평가 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectEcbComptReportList.do")
    public String selectEcbComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectEcbComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectEcbComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectEcbComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptEcbComptList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 사방사업타당성평가 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertEcbComptReportPopup.do")
	public String insertRptEcbFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		
		
		return "sys/spt/sct/uploadEcbComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 사방사업타당성평가 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertEcbComptReportFile.do")
    public ModelAndView insertEcbReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "APR_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd")));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("ECB_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertEcbComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Ecb");
		}catch (Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		}
		

		return mv;
		
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 사방사업타당성평가 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectEcbComptReportDownload.do")
    public void selectEcbFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectEcbComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 사방사업타당성평가 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteEcbComptReportFile.do")
	public ModelAndView deleteEcbFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteEcbComptReportFile(fileVO);
		}catch (Exception e) {
		    e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 임도타당성평가 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectFrdComptReportList.do")
    public String selectFrdComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectFrdComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectFrdComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectFrdComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptFrdComptList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 임도타당성평가 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertFrdComptReportPopup.do")
	public String insertRptFrdFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		
		
		return "sys/spt/sct/uploadFrdComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 임도타당성평가 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertFrdComptReportFile.do")
    public ModelAndView insertFrdReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "APR_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd")));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("FRD_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertFrdComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Frd");
		}catch (Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		}
		
//		return "forward:/sys/spt/sct/selectAprComptReportList.do";
		return mv;
		
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 임도타당성평가 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectFrdComptReportDownload.do")
    public void selectFrdFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectFrdComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description  임도타당성평가 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteFrdComptReportFile.do")
	public ModelAndView deleteFrdFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteFrdComptReportFile(fileVO);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 계측장비 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectMseComptReportList.do")
    public String selectMseComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectMseComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectMseComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectMseComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptMseComptList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 계측장비 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertMseComptReportPopup.do")
	public String insertRptMseFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		
		
		return "sys/spt/sct/uploadMseComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 계측장비 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertMseComptReportFile.do")
    public ModelAndView insertMseReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "APR_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd")));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("MSE_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertMseComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Mse");
		}catch (Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		}
		
		return mv;
		
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 계측장비 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectMseComptReportDownload.do")
    public void selectMseFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectMseComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description  계측장비 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deleteMseComptReportFile.do")
	public ModelAndView deleteMseFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deleteMseComptReportFile(fileVO);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
	/**
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 * @description 정밀점검 완료보고서 목록
	 */
	@RequestMapping(value = "/sys/spt/sct/selectPcsComptReportList.do")
    public String selectPcsComptReportList (@ModelAttribute("searchVO") SptComptReportFileVO searchVO, ModelMap model, HttpServletRequest request) throws Exception {
		
		logger.info("selectPcsComptReportList controller");
		
		int pageUnit = searchVO.getPageUnit();
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setPageUnit(pageUnit);
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<SptComptReportFileVO> list = sptComptReportService.selectPcsComptReportFileList(searchVO);
		int cnt_total = sptComptReportService.selectPcsComptReportFileTotCnt(searchVO);
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		paginationInfo.setTotalRecordCount(cnt_total);
		
		model.addAttribute("resultFileList", list);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userNm", loginVO.getName());

		return "sys/spt/sct/rptPcsComptList";
	}
	
	/**
	 * @param req
	 * @param model
	 * @throws Exception
	 * @description 정밀점검 완료보고서 등록 팝업
	 */
	@RequestMapping(value = "/sys/spt/sct/insertPcsComptReportPopup.do")
	public String insertRptPcsFilePopup(HttpServletRequest req, ModelMap model) throws Exception {
		
		//시도코드 조회		
		List<AdministZoneVO> sdCodeList = administZoneService.selectAdministZoneCtprvn();
		model.addAttribute("sdCodeList",sdCodeList);
		
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("orgnztId", "ORGNZT_0000000000000");
		
		// 부서명 조회
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		model.addAttribute("deptNmList", deptNmList);
		
		
		return "sys/spt/sct/uploadPcsComptReportPopup";
	}
	
	/**
	 * @param req
	 * @param model
	 * @param reportFileVO
	 * @param mhsr
	 * @throws Exception
	 * @description 정밀점검 완료보고서 등록
	 */
	@RequestMapping(value = "/sys/spt/sct/insertPcsComptReportFile.do")
    public ModelAndView insertPcsReportFile (HttpServletResponse res, ModelMap model, SptComptReportFileVO fileVO, MultipartHttpServletRequest mhsr) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		
		List<MultipartFile> mpf = mhsr.getFiles("files");
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Date date = new Date();
		UUID uuid = UUID.randomUUID();
		
//		List<FileVO> result = null;
//	    String atchFileId = null;
//		
//		if (!mpf.isEmpty()) {
//	    	result = fileUtil.parseFileInf(mpf, "APR_", 1, "", reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd")));
//	    	atchFileId = fileMngService.insertFileInfs(result);
//	    }
		try {
			for(int i=0; i<mpf.size(); i++){			
				long fileSize = mpf.get(i).getSize();
				File filePath = new File(reportUploadPath + File.separator + (new SimpleDateFormat("yyyyMMdd").format(date)));
				String fileOrignNm = mpf.get(i).getOriginalFilename();
				String fileStreNm = uuid+"-"+fileOrignNm;
				
				File file;
				if(!filePath.exists()){filePath.mkdirs();}
				do {
					file = new File(filePath + File.separator + fileStreNm);
				} while(file.exists());
				
				mpf.get(i).transferTo(file);
				fileVO.setFile_id("PCS_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(date)));
				fileVO.setFile_orginl_nm(fileOrignNm);
				fileVO.setFile_stre_nm(fileStreNm);
				fileVO.setFile_path(filePath.toString());
				fileVO.setFile_size(fileSize);
				fileVO.setCreate_de(date);
				fileVO.setFile_wrter(loginVO.getName());
				
				sptComptReportService.insertPcsComptReportFile(fileVO);
			}
			mv.addObject("status", "success");
			mv.addObject("svyType", "Pcs");
		}catch (Exception e) {
			mv.addObject("status", "fail");
			logger.error(e.getMessage());
		}
		
		return mv;
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 정밀점검 완료보고서 다운로드
	 */
	@RequestMapping(value = "/sys/spt/sct/selectPcsComptReportDownload.do")
    public void selectPcsFileDownload(@RequestParam(value="gid") String gid, HttpServletRequest req, HttpServletResponse res, SptComptReportFileVO fileVO) throws Exception {
		
		fileVO = sptComptReportService.selectPcsComptReportFileDetail(gid);
		
		String filename = fileVO.getFile_stre_nm();
		String orginal = fileVO.getFile_orginl_nm();
		String stordFilePath = fileVO.getFile_path();
		
		orginal = new String (orginal.getBytes("UTF-8"),"ISO-8859-1");
		String file = stordFilePath + File.separator + filename;
		
		res.setContentType("application/octet-stream; charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;filename="+orginal);
		
		final File fileToDownload = new File(file);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileToDownload);
			IOUtils.copy(inputStream, res.getOutputStream());
			res.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param req
	 * @param reportFileVO
	 * @param model
	 * @throws Exception
	 * @description 정밀점검 완료보고서 삭제
	 */
	@RequestMapping(value = "/sys/spt/sct/deletePcsComptReportFile.do")
	public ModelAndView deletePcsFile(SptComptReportFileVO fileVO, ModelMap model) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		try {
			sptComptReportService.deletePcsComptReportFile(fileVO);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//File delFile = new File(sptRptLcpFileVO.getFile_path() + File.separator + sptRptLcpFileVO.getFile_stre_nm());
			//delFile.delete();
			mv.addObject("status","success");
			model.addAttribute("message", egovMessageSource.getMessage("success.common.delete"));
		}
		return mv;
	}
	
    /**
     * 부서명을 조회한다.
     * @param adminVO
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/sys/spt/sct/selectOrgzntList.do")
	public ModelAndView selectAdministZoneSigngu(@RequestParam("corpNm") String corpNm,ModelMap model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orgnztId", corpNm);
		
		List<EgovMap> deptNmList = deptAuthorService.selectDeptNmList(map);
		
		
		mv.addObject("result",deptNmList);
		
		return mv;
	}
}