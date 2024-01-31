package or.sabang.mng.prm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.utl.fcc.service.EgovFileUploadUtil;
import or.sabang.mng.prm.service.ProgrmManageService;
import or.sabang.mng.prm.service.ProgrmManageDtlVO;
import or.sabang.mng.prm.service.ProgrmManageVO;
import or.sabang.sys.fck.apr.service.FckAprFieldBookItemVO;
import or.sabang.sys.fck.apr.service.impl.FckAprFieldBookServiceImpl;
import or.sabang.utl.ExcelUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.excel.EgovExcelService;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로그램목록관리 및 프로그램변경관리에 관한 비즈니스 구현 클래스를 정의한다.
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *
 * </pre>
 */
@Service("progrmManageService")
public class ProgrmManageServiceImpl extends EgovAbstractServiceImpl implements ProgrmManageService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgrmManageServiceImpl.class);
	@Resource(name="progrmManageDAO")
    private ProgrmManageDAO progrmManageDAO;

	@Resource(name = "excelZipService")
    private EgovExcelService excelZipService;
	/**
	 * 프로그램 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public ProgrmManageVO selectProgrm(ProgrmManageVO vo) throws Exception{
         	return progrmManageDAO.selectProgrm(vo);
	}
	/**
	 * 프로그램 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectProgrmList(ComDefaultVO vo) throws Exception {
   		return progrmManageDAO.selectProgrmList(vo);
	}
	/**
	 * 프로그램목록 총건수를 조회한다.
	 * @param vo  ComDefaultVO
	 * @return Integer
	 * @exception Exception
	 */
    @Override
	public int selectProgrmListTotCnt(ComDefaultVO vo) throws Exception {
        return progrmManageDAO.selectProgrmListTotCnt(vo);
	}
	/**
	 * 프로그램 정보를 등록
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void insertProgrm(ProgrmManageVO vo) throws Exception {
    	progrmManageDAO.insertProgrm(vo);
	}

	/**
	 * 프로그램 정보를 수정
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void updateProgrm(ProgrmManageVO vo) throws Exception {
    	progrmManageDAO.updateProgrm(vo);
	}

	/**
	 * 프로그램 정보를 삭제
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void deleteProgrm(ProgrmManageVO vo) throws Exception {
    	progrmManageDAO.deleteProgrm(vo);
	}

	/**
	 * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
	 * @param checkedProgrmFileNmForDel String
	 * @exception Exception
	 */
	@Override
	public void deleteProgrmManageList(String checkedProgrmFileNmForDel) throws Exception {
		ProgrmManageVO vo = null;
		String [] delProgrmFileNm = checkedProgrmFileNmForDel.split(",");
		for (int i=0; i<delProgrmFileNm.length ; i++){
			vo = new ProgrmManageVO();
			vo.setProgrmFileNm(delProgrmFileNm[i]);
			progrmManageDAO.deleteProgrm(vo);
		}
	}
	
	/**
	 * 프로그램 파일 존재여부를 조회
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	@Override
	public int selectProgrmNMTotCnt(ComDefaultVO vo) throws Exception{
		return progrmManageDAO.selectProgrmNMTotCnt(vo);
	}

	/**
	 * 프로그램 파일 엑셀 업로드
	 */
	@Override
	public void uploadProgramExcel(MultipartFile mFile) throws Exception{
		List<ProgrmManageVO> list = new ArrayList<ProgrmManageVO>();
		
		String extention = EgovFileUploadUtil.getFileExtension(mFile.getOriginalFilename());
		InputStream inputStream = mFile.getInputStream();
		
		int sheetRowCnt = 0;
		int rowsCnt = 0;
		
		//String pattern = "[^0-9.]+";
		
		if(extention.matches("xlsx")) {
			XSSFWorkbook xssfWB = null;// = (XSSFWorkbook) excelZipService.loadWorkbook(inputStream,null);
	    	try {
	    		xssfWB = new XSSFWorkbook(inputStream);

	    	} catch (IOException e) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
	    		LOGGER.debug("=====>>>>> ERR : IOException "+e.getMessage());
	    	} catch (Exception e) {
	    		LOGGER.debug("=====>>>>> ERR : "+e.getMessage());
	    	}
	    	if (xssfWB != null && xssfWB.getNumberOfSheets() == 1) {
	    		XSSFSheet xssfSheet = xssfWB.getSheetAt(0);  //시트 가져오기
	            XSSFRow xssfRow = xssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = xssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=xssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	XSSFRow row=xssfSheet.getRow(j); //row 가져오기
	            	if(row!=null){
	            		ProgrmManageVO vo = convertXSSFCellValues(row);
	                    list.add(vo);
	            	}
	            }
	    	}
		}else {
			HSSFWorkbook hssfWB = (HSSFWorkbook) excelZipService.loadWorkbook(inputStream);
			
			if (hssfWB.getNumberOfSheets() == 1) {
				HSSFSheet hssfSheet = hssfWB.getSheetAt(0);  //시트 가져오기
	            HSSFRow hssfRow = hssfSheet.getRow(1); //row 가져오기
	            sheetRowCnt = hssfRow.getPhysicalNumberOfCells(); //cell Cnt
	            
	            rowsCnt=hssfSheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
	            
	            for(int j=1; j<rowsCnt; j++){ //row 루프
	            	
	            	HSSFRow row=hssfSheet.getRow(j); //row 가져오기
	                if(row!=null){
	                	ProgrmManageVO vo = convertHSSFCellValues(row);
	                    list.add(vo);
	                }
	            }
			}
		}
		
		progrmManageDAO.uploadProgramExcel(list);
		
	}
	
	private ProgrmManageVO convertXSSFCellValues(XSSFRow row) throws Exception{
		ProgrmManageVO vo = new ProgrmManageVO();
		
		XSSFCell cell = null;
		
		cell = row.getCell(0);  //프로그램명
    	if(cell!=null){vo.setProgrmFileNm(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(1);  //프로그램한글명
    	if(cell!=null){vo.setProgrmKoreanNm(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(3);  //프로그램저장경로
    	if(cell!=null){vo.setProgrmStrePath(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(4);  //URL
    	if(cell!=null){vo.setURL(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(5);  //프로그램설명
    	if(cell!=null){vo.setProgrmDc(ExcelUtils.getCellValue(cell));}
    	
		return vo;
	}
	
	private ProgrmManageVO convertHSSFCellValues(HSSFRow row) throws Exception{
		ProgrmManageVO vo = new ProgrmManageVO();
		
		HSSFCell cell = null;
		
		cell = row.getCell(0);  //프로그램명
    	if(cell!=null){vo.setProgrmFileNm(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(1);  //프로그램한글명
    	if(cell!=null){vo.setProgrmKoreanNm(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(3);  //프로그램저장경로
    	if(cell!=null){vo.setProgrmStrePath(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(4);  //URL
    	if(cell!=null){vo.setURL(ExcelUtils.getCellValue(cell));}
    	
    	cell = row.getCell(5);  //프로그램설명
    	if(cell!=null){vo.setProgrmDc(ExcelUtils.getCellValue(cell));}
    	
		return vo;
	}
}