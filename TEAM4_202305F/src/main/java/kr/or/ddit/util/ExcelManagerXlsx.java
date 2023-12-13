package kr.or.ddit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//엑셀파일 업로드시 쓰는 유틸
//엑셀 파일의 데이터를 읽어서 리스트로 반환하기 위한 클래스
/**
 * @author 작성자명
 * @since 2023. 11. 19.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 19.     김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public class ExcelManagerXlsx {
	private static ExcelManagerXlsx excelXlsxMng;

	public ExcelManagerXlsx() {
		// TODO Auto-generated constructor stub
	}

	public static ExcelManagerXlsx getInstance() {
		if (excelXlsxMng == null)
			excelXlsxMng = new ExcelManagerXlsx();
		return excelXlsxMng;
	}

	public List<HashMap<String, String>> getListXlsxRead(String excel) throws Exception {
		//결과를 저장할 리스트
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		File file = new File(excel);
		
        // 파일이 존재하고 읽을 수 있는지 확인
		if (!file.exists() || !file.isFile() || !file.canRead()) {
			throw new IOException(excel);
		}
		//WorkbookFactory이용시  XSSF,HSSF 모두 사용 가능
		Workbook workbook  = WorkbookFactory.create(new FileInputStream(file));
		//XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		//HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));

		int check = 0;

		try {
			for (int i = 0; i < 1; i++) {
				for (Row row : workbook.getSheetAt(i)) {
					// 첫 번째 행은 헤더로 생각하고 건너뛰기
					if (check != 0) {
						//행의 데이터를 저장할 HashMap 생성
						HashMap<String, String> hMap = new HashMap<String, String>();
						String valueStr = "";
						int cellLength = (int) row.getLastCellNum();
						
						//각 셀(cell)을 순회
						for (int j = 0; j < cellLength; j++) {
							Cell cell = row.getCell(j);
							
							// 셀이 비어있거나 빈 셀인 경우 빈 문자열로 처리
							if (cell == null || cell.getCellType() == CellType.BLANK) {
								valueStr = "";
							} else {
								// 셀의 데이터 유형에 따라 처리
								switch (cell.getCellType()) {
								case STRING:
									valueStr = cell.getStringCellValue();
									break;
								case NUMERIC: // 날짜 형식이든 숫자 형식이든 다 NUMERIC으로 인식함.
									if (DateUtil.isCellDateFormatted(cell)) { // 날짜 유형의 데이터일 경우,
										SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
										String formattedStr = dateFormat.format(cell.getDateCellValue());
										valueStr = formattedStr;
										break;
									} else { // 순수하게 숫자 데이터일 경우,
										Double numericCellValue = cell.getNumericCellValue();
										if (Math.floor(numericCellValue) == numericCellValue) { // 소수점 이하를 버린 값이 원래의 값과
																								// 같다면,,
											valueStr = numericCellValue.intValue() + ""; // int형으로 소수점 이하 버리고 String으로
																							// 데이터 담는다.
										} else {
											valueStr = numericCellValue + "";
										}
										break;
									}
								case BOOLEAN:// 불리언 셀인 경우 해당 값을 문자열로 처리
									valueStr = cell.getBooleanCellValue() + "";
									break;
								}
							}
							// 해당 열(column)의 값을 HashMap에 저장
							hMap.put("cell_"+j, valueStr);
						}
						// 해당 행의 데이터를 리스트에 추가
						list.add(hMap);
					}
					check++;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
