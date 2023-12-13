package kr.or.ddit.groupware.webhard.controller;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.groupware.webhard.ftp.FTPControl;
import kr.or.ddit.groupware.webhard.service.WebHardService;
import kr.or.ddit.vo.FTPVO;
import kr.or.ddit.vo.FileAuthorVO;
import kr.or.ddit.vo.groupware.WebHardVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/web")
public class WebHardController {	

	@Inject
	private WebHardService service;
	
	@ModelAttribute("web")
	public WebHardVO web() {
		WebHardVO web=new WebHardVO();
		return web;
	}
	
	@GetMapping("{cours}")
	public String selectListFileUI(Model model, @PathVariable String cours) {
		model.addAttribute("cours",cours);
		return "webhard/webhardList";
	}
	
	@GetMapping("list")
	public String selectListFile(
			@RequestParam(value="webCours", required = false, defaultValue = "/") String webCours
			, @RequestParam(value="who", required = false, defaultValue = "") String who
			, Model model
			, Authentication auth) {
		WebHardVO webVO=new WebHardVO();
		webVO.setWebCours(webCours);
		webVO.setWho(who);
		FileAuthorVO faVO=new FileAuthorVO();
		faVO.setFaNm(auth.getName());
		webVO.setFileAuthor(faVO);
		List<WebHardVO> webList= service.selectListFile(webVO);
		model.addAttribute("webList",webList);
		model.addAttribute("webCours",webCours);
		return "jsonView";
	}
	
	@PostMapping
	@ResponseBody
	public void InsertFile(WebHardVO webVO,Authentication auth) {
		webVO.setWebMaker(auth.getName());
		service.insertFile(webVO);
	}
	
	@PostMapping("download")
	@ResponseBody
	public void ftpFileDownload(FTPVO ftpVO) {
		ResponseEntity<Resource> a = service.download(ftpVO);
		log.info("asdasd : {}",a);
	}
	
	@PostMapping("delete")
	@ResponseBody
	public void ftpFileDelete(WebHardVO webVO) {
		service.delete(webVO);
	}
	
	@PostMapping("update")
	@ResponseBody
	public void ftpFileUpdate(@RequestBody WebHardVO webVO) {
		service.update(webVO);
	}
	
	@GetMapping(value = "txtRead", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String ftpTxtReader(@RequestParam String ftpFile) {
		FTPVO ftpVO=new FTPVO();
		ftpVO.setFtpFile(ftpFile);
		return FTPControl.ftpTxtReader(ftpVO);
	}
	
	@PostMapping("detail")
	@ResponseBody
	public WebHardVO ftpFileDetail(@RequestBody WebHardVO webVO) {
		return service.selectFile(webVO);
	}
	
//	@GetMapping(value = "imgRead", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
//	@GetMapping("imgRead")
//	@ResponseBody
//	public byte[] ftpImgReader(@RequestParam String ftpFile) {
//		FTPVO ftpVO=new FTPVO();
//		ftpVO.setFtpFile(ftpFile);
//		return FTPControl.ftpImageReader(ftpVO);
//	}
}
