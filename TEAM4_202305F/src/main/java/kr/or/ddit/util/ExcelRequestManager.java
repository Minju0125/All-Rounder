package kr.or.ddit.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 엑셀파일 업로드시 쓰는 유틸
 * 임시파일을 만들고 업로드된 파일을 처리하는 클래스
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
public class ExcelRequestManager {
	public List<HashMap<String, String>> parseExcelSpringMultiPart(Map<String, MultipartFile> files, String KeyStr,
			int fileKeyParam, String atchFileId, String storePath) throws Exception {
		List<HashMap<String, String>> list = null;
		int fileKey = fileKeyParam;

		// 임시 파일 저장 경로
		String storePathString = "";
		String atchFileIdString = "";

		if ("".equals(storePath) || storePath == null) {
			storePathString = "D:/team4img/temp"; //에러나나 안나나 확인용
		} else {
			storePathString = "D:/team4img/temp" + storePath;
		}

		if (!"".equals(atchFileId) || atchFileId != null) {
			atchFileIdString = atchFileId;
		}

		File saveFolder = new File(storePathString);

		// 저장 폴더가 존재하지 않으면 생성
		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		// 업로드된 파일을 처리하기 위해 반복문 실행
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";

		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();

			if ("".equals(orginFileName)) {
				continue;
			}

			int index = orginFileName.lastIndexOf(".");
			String fileExt = orginFileName.substring(index + 1);
			String newName = KeyStr + fileKey;

			if (!"".equals(orginFileName)) {
				filePath = storePathString + File.separator + newName + "." + fileExt;
				// 업로드된 파일을 지정된 경로로 저장
				file.transferTo(new File(filePath));
			}
			// 저장된 파일의 경로를 ExcelManagerXlsx 클래스의 getListXlsxRead() 메서드에 전달하여 엑셀 데이터를 읽어오고,
			// 엑셀 파일을 파싱하여 리스트로 변환
			list = ExcelManagerXlsx.getInstance().getListXlsxRead(filePath);

			fileKey++;
		}
		return list;
	}

}
