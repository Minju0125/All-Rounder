package kr.or.ddit.admin.account.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.account.service.AccountService;
import kr.or.ddit.util.ExcelRequestManager;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 19.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 19.      김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@RequestMapping("/account/excel")
@Controller
public class ExcelController {

	@Autowired
	private AccountService service;


	//////////////////////// poi 활용) DB정보 >>excel 다운로드 /////////////////////////////

	@RequestMapping("download")
	public ResponseEntity<InputStreamResource> downloadExcel(HttpServletResponse response) throws IOException {

		try (Workbook workbook = new SXSSFWorkbook()) {// 엑셀 파일 생성
			Sheet sheet = workbook.createSheet("직원목록"); // 시트 생성
			int rowNo = 0;

			// cell style 설정
			CellStyle headStyle = workbook.createCellStyle();
			// 배경색 설정(둘이 세트로 있어야 함)
			headStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			// 폰트설정
			Font font = workbook.createFont();
			font.setColor(IndexedColors.WHITE.getIndex());
			font.setFontHeightInPoints((short) 13);
			headStyle.setFont(font);
			
			//헤더 행 만들기
			String[] headers = { 
					"사번","이름","주소","상세주소","우편번호"
					,"생년월일","주민번호","직책","직급"
					,"입사일","부서명","이메일","휴대전화","내선전화"};

			Row headerRow = sheet.createRow(rowNo++);
			for (int i = 0; i < headers.length; i++) {
				headerRow.createCell(i).setCellValue(headers[i]);
				headerRow.getCell(i).setCellStyle(headStyle);
			}


			List<EmployeeVO> list = service.retrieveEmpList();
			for (EmployeeVO excelEmployeeVO : list) {
				Row row = sheet.createRow(rowNo++);

				//행의 n번째 열에 셀을 생성하고 헤더 번호에 맞게 값을 넣어준다, 삼항연산을 이용해서 null 값일 경우 처리를 해준다
				row.createCell(0).setCellValue(excelEmployeeVO.getEmpCd() != null ? excelEmployeeVO.getEmpCd() : "");
				row.createCell(1).setCellValue(excelEmployeeVO.getEmpName() != null ? excelEmployeeVO.getEmpName() : "");
				row.createCell(2).setCellValue(excelEmployeeVO.getEmpAdres() != null ? excelEmployeeVO.getEmpAdres() : "");
				row.createCell(3).setCellValue(excelEmployeeVO.getEmpAdresDetail() != null ? excelEmployeeVO.getEmpAdresDetail() : "");
				row.createCell(4).setCellValue(excelEmployeeVO.getEmpZip() != null ? excelEmployeeVO.getEmpZip() : "");
				row.createCell(5).setCellValue(excelEmployeeVO.getEmpBirth() != null ? excelEmployeeVO.getEmpBirth() : "");
				row.createCell(6).setCellValue(excelEmployeeVO.getEmpSsn() != null ? excelEmployeeVO.getEmpSsn() : "");
				row.createCell(7).setCellValue(excelEmployeeVO.getEmpRank() != null ? excelEmployeeVO.getEmpRank() : "");
				row.createCell(8).setCellValue(excelEmployeeVO.getEmpPosition() != null ? excelEmployeeVO.getEmpPosition() : "");
				row.createCell(9).setCellValue(excelEmployeeVO.getEmpHiredate() != null ? excelEmployeeVO.getEmpHiredate() : "");
				row.createCell(10).setCellValue(excelEmployeeVO.getDept().getDeptName() != null ? excelEmployeeVO.getDept().getDeptName() : "");
				row.createCell(11).setCellValue(excelEmployeeVO.getEmpMail() != null ? excelEmployeeVO.getEmpMail() : "");
				row.createCell(12).setCellValue(excelEmployeeVO.getEmpTelno() != null ? excelEmployeeVO.getEmpTelno() : "");
				row.createCell(13).setCellValue(excelEmployeeVO.getEmpExtension() != null ? excelEmployeeVO.getEmpExtension() : "");
				

				// date 타입일 경우의 처리
//				String memBirStr = excelEmployeeVO.getMemBir() != null ? dateFormat.format(excelEmployeeVO.getMemBir()): "";
//				row.createCell(5).setCellValue(memBirStr);
//				// int 타입일 경우의 처리
//				Integer mileageObj = excelEmployeeVO.getMemMileage();
//				int mileage = (mileageObj != null) ? mileageObj.intValue() : 0;
//				row.createCell(17).setCellValue(mileage);
			}

			// 임시파일 생성 및 삭제 로직
			File tmpFile = File.createTempFile("TempAccount", ".xlsx");
			try (OutputStream fos = new FileOutputStream(tmpFile);) {
				workbook.write(fos);
			}
			InputStream res = new FileInputStream(tmpFile) {
				@Override
				public void close() throws IOException {
					super.close();
					if (tmpFile.delete()) {
						log.info("임시 파일 삭제 완료");
					}
				}
			};

			// 응답 생성
			return ResponseEntity.ok().contentLength(tmpFile.length())
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel")) // excel파일에 맞는 컨텐츠 타입 지정
					.header("Content-Disposition", "attachment;filename="+URLEncoder.encode("사조참치_직원목록","UTF-8")+".xlsx") // 파일 이름 설정
					.body(new InputStreamResource(res));
		}
	}

	///////////////////////////////poi 활용) 엑셀파일 >>DB insert 로직 ////////////////////////////


	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String createApplicant_action(@ModelAttribute("excelEmployeeVO") EmployeeVO excelEmployeeVO, 
			RedirectAttributes redirectAttributes,
			HttpServletRequest request, 
			final MultipartHttpServletRequest multiRequest, Model model)throws Exception {

		Map<String, String> resMap = new HashMap<String, String>();

		try {
			ExcelRequestManager excelmanager = new ExcelRequestManager();
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			List<HashMap<String, String>> applyList = null;

			//업로드된 Excel 파일을 파싱하여 HashMap의 리스트로 변환
			applyList = excelmanager.parseExcelSpringMultiPart(files, "applicant", 0, "", null);
			//Tempreserve폴더 하위에 applicant0이라는 이름의 임시파일이 생성된다. 


			for (int i = 0; i < applyList.size(); i++) {
				//번호랑 값이랑 맞게 세팅해준다
//				excelEmployeeVO.setEmpCd(applyList.get(i).get("cell_0")); //엑셀파일의 i번째 행 의 0번째 열을 가져와서 MemId에 담아준다
				excelEmployeeVO.setEmpName(applyList.get(i).get("cell_0"));
				excelEmployeeVO.setEmpAdres(applyList.get(i).get("cell_1"));
				excelEmployeeVO.setEmpAdresDetail(applyList.get(i).get("cell_2"));
				excelEmployeeVO.setEmpZip(applyList.get(i).get("cell_3"));
				excelEmployeeVO.setEmpBirth(applyList.get(i).get("cell_4"));
				
				excelEmployeeVO.setEmpSsn(applyList.get(i).get("cell_5"));
				
				CommonVO cVO = new CommonVO();
				cVO.setCommonCodeSj(applyList.get(i).get("cell_6"));
				excelEmployeeVO.setCommon(cVO);
				
				excelEmployeeVO.setEmpPosition(applyList.get(i).get("cell_7"));
				excelEmployeeVO.setEmpHiredate(applyList.get(i).get("cell_8"));
				
				DeptVO dVO = new DeptVO();
				dVO.setDeptName(applyList.get(i).get("cell_9"));
				excelEmployeeVO.setDept(dVO);
				
				excelEmployeeVO.setEmpMail(applyList.get(i).get("cell_10"));
				excelEmployeeVO.setEmpTelno(applyList.get(i).get("cell_11"));
				excelEmployeeVO.setEmpExtension(applyList.get(i).get("cell_12"));
				
				
				
				//Date타입으로 변환
//				String memBirValue = applyList.get(i).get("cell_5");
//				Date memBir = null;
//				if (memBirValue != null && !memBirValue.isEmpty()) {
//				    memBir = dateFormat.parse(memBirValue);
//				}
//				excelEmployeeVO.setMemBir(memBir);
				
				//int타입으로 변환
//				excelEmployeeVO.setMemMileage(Integer.parseInt(applyList.get(i).get("cell_17")));
				
				service.insertEmpExcel(excelEmployeeVO);
			}

			resMap.put("res", "ok");
			resMap.put("msg", "txt.success");

		} catch (Exception e) {
			System.out.println(e.toString());
			resMap.put("res", "error");
			resMap.put("msg", "txt.fail");
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("resMap", resMap);
		return "redirect:/account/home";
	}


}