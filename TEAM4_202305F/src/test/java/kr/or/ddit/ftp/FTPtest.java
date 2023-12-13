package kr.or.ddit.ftp;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import kr.or.ddit.groupware.webhard.ftp.FTPControl;
import kr.or.ddit.vo.FTPVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class FTPtest {

	@Test
	@Disabled
	void downloadTest() {
		FTPVO fVO = new FTPVO();
		fVO.setLocalFile("C:/Users/PC-09/Downloads/123q.txt");
		fVO.setFtpFile("/123q.txt");
		FTPControl.ftpFileDownload(fVO);
	}

	@Test
	void readTest() {
		FTPVO fVO = new FTPVO();
		List<String> list = FTPControl.ftpReadFiles(fVO);
		for (String a : list) {
			System.out.println(a);
		}
	}

	@Test
	@Disabled
	void uploadTest() {
		FTPVO fVO = new FTPVO();
		fVO.setLocalFile("C:/Users/PC-09/Downloads/i015786180056.mp4");
		fVO.setFtpFile("/i015786180056.mp4");
		FTPControl.ftpFileUpload(fVO);
	}

	@Test
	@Disabled
	void deleteTest() {
		FTPVO fVO = new FTPVO();
		fVO.setFtpFile("/123.txt");
		FTPControl.ftpFileDelete(fVO);
	}

	@Test
	@Disabled
	void updateForderTest() {
		FTPVO fVO = new FTPVO();
		fVO.setFtpFile("/");
		FTPControl.ftpForderUpload(fVO);
	}

	@Test
	@Disabled
	void deleteForderTest() {
		FTPVO fVO = new FTPVO();
		fVO.setFtpFile("/새 폴더");
		FTPControl.ftpFolderDelete(fVO);
	}

	@Test
	@Disabled
	void fileTxtReader() {
		FTPVO fVO = new FTPVO();
		fVO.setFtpFile("/186/188.txt");
		String a=FTPControl.ftpTxtReader(fVO);
		log.info("{}",a);
	}

//	@Test
//	@Disabled
//	void fileImgReader() {
//		FTPVO fVO = new FTPVO();
//		fVO.setFtpFile("/186/190/191.png");
//		byte[] a=FTPControl.ftpImageReader(fVO);
//		log.info("{}",a);
//	}

	@Test
	@Disabled
	void test() {
//		String a="asd";
//		if(!a.contains(".")) {
//			System.out.println("1");
//		}else {
//			System.out.println("2");
//		}
		
//		File file = new File("D:/ftpLocal/최프 착수발표.txt");
//		file.delete();
		
		String a="abh.asbb.txt";
		String[] ab=a.split("\\.");
		log.info("{}",ab[ab.length-1]);
		log.info("{}",ab.length);
		log.info("{}",Arrays.toString(ab));
	}
}
