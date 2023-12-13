package kr.or.ddit.groupware.mailing.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author 작성자명
 * @since 2023. 11. 13.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          박민주               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 13.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/mail")
public class MailImageUploadController {
	
	@Value("#{appInfo.mailImagesUrl}")
	private String mailImagesUrl;
	
	@Value("#{appInfo.mailImagesUrl}")
	private Resource mailImages;

	@PostMapping("image")
	public String imageUpload(MultipartFile upload, Model model, HttpServletRequest req ) throws IOException {
		if(!upload.isEmpty()) {	//파일이 정상적으로 업로드 되고 있다.
			// 로컬의 파일의 url이 필요하므로 webResource형태로 저장이 되야함
			String saveName = UUID.randomUUID().toString();
			File saveFolder =  mailImages.getFile(); //파일로 만들어줘야 저장이 가능
			File saveFile = new File(saveFolder, saveName);
			upload.transferTo(saveFile);	// upload 완료
			
			String url = req.getContextPath()+mailImagesUrl + "/" + saveName;	// 이거 비동기의 응답데이터로 나가야함
			model.addAttribute("uploaded",1);
			model.addAttribute("fileName",upload.getOriginalFilename());	//원본파일명
			model.addAttribute("url",url);
		
		} else {	// 업로드가 되지 않았을때.
			model.addAttribute("uploaded",0);
			model.addAttribute("error", Collections.singletonMap("message", "업로드 된 파일 없음"));	// map의 엔트리가 1개밖에 없을 때 사용
		}
		return "jsonView";
	}
	
}
