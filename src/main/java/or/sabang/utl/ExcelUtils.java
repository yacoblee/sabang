package or.sabang.utl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


//@Component("excelUtils")
public class ExcelUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
	
	public static List<Object> setExcelReader(MultipartFile mf) throws Exception {
		List<Object> list = new ArrayList<>();
		try {
			Workbook wb = getWorkbook(mf);
			Sheet sheet = wb.getSheetAt(0);//첫번째 sheet
			Row row = null;
			Map<String, Object> vo = null;
			
			for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
				if (rowIndex > 7) {
					row = sheet.getRow(rowIndex);
					vo = new HashMap<String, Object>();
					vo.put("name",getCellValue(row.getCell(0)));
					vo.put("juminNum",getCellValue(row.getCell(1)));
					vo.put("jibun",getCellValue(row.getCell(2)));
					vo.put("load",getCellValue(row.getCell(3)));
					vo.put("familyGubun",getCellValue(row.getCell(4)));
					vo.put("familyNum",getCellValue(row.getCell(5)));
					vo.put("phone",getCellValue(row.getCell(6)));
					vo.put("homeNum",getCellValue(row.getCell(7)));
					list.add(vo);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return list;
	}
	
	public List<Object> setWorkExcelReader(String storeFilePath) throws Exception {
		List<Object> list = new ArrayList<>();
		
		File file = new File(storeFilePath);
		
		LOGGER.debug("is exists :"+file.exists()+">>>>  file name:"+file.getName());
		
		if(file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				Workbook workbook = WorkbookFactory.create(inputStream);
				
				Sheet sheet = null;
				Row row = null;
				Cell cell = null;
				Map<String, Object> vo = null;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
                for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                	sheet = workbook.getSheetAt(sheetIndex);
    				for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
    					if (rowIndex > 7) {
    						row = sheet.getRow(rowIndex);
    						vo = new HashMap<String, Object>();
    						String value = null;
    						if (row.getCell(0) != null) {
    							if (!"".equals(row.getCell(0).getStringCellValue())) {
    								for (int cellIndex = 0; cellIndex < row.getPhysicalNumberOfCells(); cellIndex++) {
    									cell = row.getCell(cellIndex);
    									
    									if (true) {
    										value = "";
    										
    										switch (cell.getCellType()) {
    											case Cell.CELL_TYPE_FORMULA:
    												value = cell.getCellFormula();
    											break;
    											case Cell.CELL_TYPE_NUMERIC://숫자
    												value = cell.getNumericCellValue() + "";
    												if (DateUtil.isCellDateFormatted(cell)){//날짜
    		                                            value = formatter.format(cell.getDateCellValue()) + "";
    		                                        }else{//숫자
    		                                            //long i =(long) cell.getNumericCellValue(); // 리턴타입이 double 임. integer 형변환 해주고--> integer 보다 값의 크기가 커서. long으로 형변환 했다.!
    		                                            //value = Long.toString(i); //long으로 형변환된 값을 String 으로 받아서 value값에 넣어준다.
    		                                        	value = String.format("%.0f", new Double(cell.getNumericCellValue())) + "";
    		                                        }
    											break;
    											case Cell.CELL_TYPE_STRING:
    												value = cell.getStringCellValue() + "";
    											break;
    											case Cell.CELL_TYPE_BLANK:
    												value = cell.getBooleanCellValue() + "";
    											break;
    											case Cell.CELL_TYPE_ERROR:
    												value = cell.getErrorCellValue() + "";
    											break;
    											default:
    												value = new String();
    											break;
    										}
    										
    										switch (cellIndex) {
    											case 0:
    												vo.put("",value);
    											break;
    											
    											default:
    											break;
    										}
    									}
    								}
    								list.add(vo);
    							}
    						}
    					}
    				}
    			}
                
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return list;
	}
	
	public List<Object> setXlsxExcelReader(MultipartFile ex) throws Exception {
		List<Object> list = new ArrayList<>();
		
		XSSFWorkbook workbook = null;
		
		try {
			workbook = new XSSFWorkbook(ex.getInputStream());
			
			XSSFSheet curSheet = null;
			XSSFRow curRow = null;
			XSSFCell curCell = null;
			Map<String, Object> vo = null;
			
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				curSheet = workbook.getSheetAt(sheetIndex);
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					if (rowIndex > 7) {
						curRow = curSheet.getRow(rowIndex);
						vo = new HashMap<String, Object>();
						String value = null;
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									
									if (true) {
										value = "";
										
										switch (curCell.getCellType()) {
											case HSSFCell.CELL_TYPE_FORMULA:
												value = curCell.getCellFormula();
											break;
											case HSSFCell.CELL_TYPE_NUMERIC:
												value = curCell.getNumericCellValue() + "";
											break;
											case HSSFCell.CELL_TYPE_STRING:
												value = curCell.getStringCellValue() + "";
											break;
											case HSSFCell.CELL_TYPE_BLANK:
												value = curCell.getBooleanCellValue() + "";
											break;
											case HSSFCell.CELL_TYPE_ERROR:
												value = curCell.getErrorCellValue() + "";
											break;
											default:
												value = new String();
											break;
										}
										
										switch (cellIndex) {
											case 0:
												vo.put("",value);
											break;
											
											default:
											break;
										}
									}
								}
								list.add(vo);
							}
						}
					}
				}
			}

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return list;
	}
	
	public List<Object> setXlsExcelReader(MultipartFile ex) throws Exception {
		List<Object> list = new ArrayList<>();
		
		HSSFWorkbook workbook = null;
		
		try {
			workbook = new HSSFWorkbook(ex.getInputStream());
			
			HSSFSheet curSheet = null;
			HSSFRow curRow = null;
			HSSFCell curCell = null;
			Map<String, Object> vo = null;
			
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				curSheet = workbook.getSheetAt(sheetIndex);
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						vo = new HashMap<String, Object>();
						String value = null;
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									
									if (true) {
										value = "";
										
										switch (curCell.getCellType()) {
											case HSSFCell.CELL_TYPE_FORMULA:
												value = curCell.getCellFormula();
											break;
											case HSSFCell.CELL_TYPE_NUMERIC:
												value = curCell.getNumericCellValue() + "";
											break;
											case HSSFCell.CELL_TYPE_STRING:
												value = curCell.getStringCellValue() + "";
											break;
											case HSSFCell.CELL_TYPE_BLANK:
												value = curCell.getBooleanCellValue() + "";
											break;
											case HSSFCell.CELL_TYPE_ERROR:
												value = curCell.getErrorCellValue() + "";
											break;
											default:
												value = new String();
											break;
										}
										
										switch (cellIndex) {
											case 0:
												vo.put("",value);
											break;
//											case 1: // 이름
//												vo.setCustName(value);
//											break;
//											case 2: // 나이
//												vo.setCustAge(value);
//											break;
//											case 3: // 이메일
//												vo.setCustEmail(value);
//											break;
											default:
											break;
										}
									}
								}
								list.add(vo);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return list;
	}
	
	public static Workbook getWorkbook(MultipartFile mf) {
		String fileExtention = FilenameUtils.getExtension(mf.getOriginalFilename()).toLowerCase();
		Workbook wb = null;
		
		try {
			if(fileExtention.equals("xlsx")) {
				wb = new XSSFWorkbook(mf.getInputStream());//Excel 2007 이후
			}else {
				wb = new HSSFWorkbook(mf.getInputStream());//Excel 2007 이전
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		return wb;
	}
	
	public static String getCellValue(Cell cell) {
		String value = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		if(cell == null) {
			value = new String();
		}else {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula();
			break;
			case Cell.CELL_TYPE_NUMERIC://숫자
				value = cell.getNumericCellValue() + "";
				if (DateUtil.isCellDateFormatted(cell)){//날짜
                    value = formatter.format(cell.getDateCellValue()) + "";
                }else{//숫자
                	Double db_value = new Double(cell.getNumericCellValue());
                	if((double)db_value.longValue() == db_value.doubleValue()) {
                		value = Long.toString(db_value.longValue());
                	}else {
                		value = db_value.toString();
                	}
                    //long i =(long) cell.getNumericCellValue(); // 리턴타입이 double 임. integer 형변환 해주고--> integer 보다 값의 크기가 커서. long으로 형변환 했다.!
                    //value = Long.toString(i); //long으로 형변환된 값을 String 으로 받아서 value값에 넣어준다.
                	//value = String.format("%.0f", new Double(cell.getNumericCellValue())) + "";
                }
			break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue() + "";
			break;
			case Cell.CELL_TYPE_BLANK:
				//value = cell.getBooleanCellValue() + "";
				value = cell.getStringCellValue() + "";
			break;
			case Cell.CELL_TYPE_ERROR:
				value = cell.getErrorCellValue() + "";
			break;
			default:
				value = new String();
			break;
		}
		}
		return value;
	}
}
