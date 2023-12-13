package kr.or.ddit.groupware.webhard.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import kr.or.ddit.vo.FTPVO;

/**
 * @author 권도윤
 * @since 2023. 11. 9.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 9.      권도윤       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
public class FTPControl {

	// 선택된 디렉토리(폴더)에 존재하는 파일이름을 모두 불러옵니다.
	public static List<String> ftpReadFiles(FTPVO fVO) {
		List<String> fileNames=new ArrayList<String>();
		FTPClient ftp = null;
		try {
			String uri = fVO.getUri();
			String id = fVO.getId();
			String pw = fVO.getPw();
			int port=fVO.getPort();
			String directoryLocation = fVO.getDirectoryLocation();
			ftp = new FTPClient();
			ftp.setControlEncoding("UTF-8");
			ftp.connect(uri,port);
			ftp.login(id, pw);
			ftp.changeWorkingDirectory(directoryLocation);// 파일 가져올 디렉터리 위치
			ftp.setFileType(FTP.BINARY_FILE_TYPE);// 파일 타입 설정 기본적으로 파일을 전송할떄는 BINARY타입을 사용합니다.

			for (String fileName : ftp.listNames()) {
				System.out.println(fileName);
				fileNames.add(fileName);
			}

			ftp.logout();
		} catch (SocketException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();// ftp연결 끊기
				} catch (IOException e) {
				}
			}
		}
		return fileNames;
	}

	// 파일 다운
	public static void ftpFileServerDownload(FTPVO fVO) {
		FTPClient ftp = null;
		try {
			String uri = fVO.getUri();
			String id = fVO.getId();
			String pw = fVO.getPw();
			int port=fVO.getPort();
			String localFile = fVO.getLocalFile();
			String ftpFile = fVO.getFtpFile();
			String directoryLocation = fVO.getDirectoryLocation();
			String downloadRoute = "C:/download/";
			ftp = new FTPClient();
			ftp.setControlEncoding("UTF-8");
			ftp.connect(uri,port);
			ftp.login(id, pw);
			ftp.changeWorkingDirectory(directoryLocation);// 파일 가져올 디렉터리 위치
			ftp.setFileType(FTP.BINARY_FILE_TYPE);// 파일 타입 설정 기본적으로 파일을 전송할떄는 BINARY타입을 사용합니다.
			File donwloadFile = new File(downloadRoute);
			if(!donwloadFile.exists()) {
				donwloadFile.mkdir();
			}
			File f = new File(localFile);// 로컬에 다운받아 설정할 이름
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				boolean isSuccess = ftp.retrieveFile(ftpFile, fos);// ftp서버에 존재하는 해당명의 파일을 다운로드 하여 fos에 데이터를 넣습니다.
				if (isSuccess) {
					System.out.println("다운로드를 성공 하였습니다.");
				} else {
					System.out.println("다운로드 실패하였습니다.");
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} finally {
				if (fos != null)
					try {
						fos.close();
					} catch (IOException ex) {
					}
			}
			ftp.logout();
		} catch (SocketException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();// ftp연결 끊기
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static ResponseEntity<Resource> ftpFileDownload(FTPVO fVO) {
		FTPClient ftp = null;
		try {
			String uri = fVO.getUri();
			String id = fVO.getId();
			String pw = fVO.getPw();
			int port=fVO.getPort();
			String ftpFile = fVO.getFtpFile();
			String directoryLocation = fVO.getDirectoryLocation();
			ftp = new FTPClient();
			ftp.setControlEncoding("UTF-8");
			ftp.connect(uri,port);
			ftp.login(id, pw);
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory(directoryLocation);// 파일 가져올 디렉터리 위치
			ftp.setFileType(FTP.BINARY_FILE_TYPE);// 파일 타입 설정 기본적으로 파일을 전송할떄는 BINARY타입을 사용합니다.
			File donwloadFile = new File(ftpFile);
			FileOutputStream fos = new FileOutputStream(donwloadFile);
			boolean isSuccess = ftp.retrieveFile(ftpFile, fos);// ftp서버에 존재하는 해당명의 파일을 다운로드 하여 fos에 데이터를 넣습니다.
			if (isSuccess) {
				System.out.println("다운로드를 성공 하였습니다.");
			} else {
				System.out.println("다운로드 실패하였습니다.");
			}
			fos.close();
			ftp.logout();
			
			Path path = Paths.get(donwloadFile.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + donwloadFile.getName() + "\"")
                    .body(resource);
		} catch (SocketException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();// ftp연결 끊기
				} catch (IOException e) {
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

	public static void ftpFileUpload(FTPVO fVO) {
		FTPClient ftp = null;
		try {
			String uri = fVO.getUri();
			String id = fVO.getId();
			String pw = fVO.getPw();
			int port=fVO.getPort();
			String localFile = fVO.getLocalFile();
			String ftpFile = fVO.getFtpFile();
			String directoryLocation = fVO.getDirectoryLocation();
//			String route = fVO.getRoute();
			
			ftp = new FTPClient();
			ftp.setControlEncoding("UTF-8");
			ftp.connect(uri,port);
			ftp.login(id, pw);
			ftp.changeWorkingDirectory(directoryLocation);// 파일을 업로드할 ftp서버의 디렉토리(폴더)위치
			ftp.setFileType(FTP.BINARY_FILE_TYPE);// 파일 타입 설정 기본적으로 파일을 전송할떄는 BINARY타입을 사용합니다.

			File uploadFile = new File(localFile);// 업로드할 로컬 파일
			
//			String[] folder=ftpFile.split("/");
//			route=ftpFile.substring(0, ftpFile.length() - folder[folder.length - 1].length());
//			ftp.mkd(route);
//			ftpFileUpload(fVO);
			
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(uploadFile);
				boolean isSuccess = ftp.storeFile(ftpFile, fis);// ftp서버에 존재하는 해당명의 파일을 다운로드 하여 fos에 데이터를 넣습니다.
				if (isSuccess) {
					System.out.println("업로드를 성공하였습니다.");
				} else {
					System.out.println("업로드에 실패하였습니다.");
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			} finally {
				if (fis != null)
					try {
						fis.close();
					} catch (IOException ex) {
					}
			}
			ftp.logout();
		} catch (SocketException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();// ftp연결 끊기
				} catch (IOException e) {
				}
			}
		}
	}

	public static void ftpFileDelete(FTPVO fVO) {

		FTPClient ftp = null; // FTP Client 객체
		FileInputStream fis = null; // File Input Stream

		String uri = fVO.getUri();
		String id = fVO.getId();
		String pw = fVO.getPw();
		int port=fVO.getPort();
		String directoryLocation = fVO.getDirectoryLocation();
		String ftpFile = fVO.getFtpFile();

		try {
			ftp = new FTPClient(); // FTP Client 객체 생성
			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
			ftp.connect(uri,port); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호
			ftp.login(id, pw); // FTP 로그인 ID, PASSWORLD 입력
//    	      ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
			ftp.changeWorkingDirectory(directoryLocation); // 작업 디렉토리 변경
			ftp.setFileType(FTP.BINARY_FILE_TYPE); // 업로드 파일 타입 셋팅

			try {
				boolean isSuccess = ftp.deleteFile(ftpFile);// 파일삭제
				if (isSuccess) {
					System.out.println(ftpFile + "삭제하였습니다.");
				} else {
					System.out.println(ftpFile + "삭제하지 못 했습니다.");
				}
			} catch (IOException ex) {
				System.out.println("IO Exception : " + ex.getMessage());
			} finally {
				if (fis != null) {
					try {
						fis.close(); // Stream 닫기
					} catch (IOException ex) {
						System.out.println("IO Exception : " + ex.getMessage());
					}
				}
			}
			ftp.logout(); // FTP Log Out
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // 접속 끊기
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void ftpForderUpload(FTPVO fVO) {
		FTPClient ftp = null; // FTP Client 객체
		String uri = fVO.getUri();
		String id = fVO.getId();
		String pw = fVO.getPw();
		int port=fVO.getPort();
		String ftpFile = fVO.getFtpFile();
		String directoryLocation = fVO.getDirectoryLocation();
		try {
			ftp = new FTPClient(); // FTP Client 객체 생성
			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
			ftp.connect(uri,port); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호
			ftp.login(id, pw); // FTP 로그인 ID, PASSWORLD 입력
//    	      ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
			ftp.changeWorkingDirectory(directoryLocation); // 작업 디렉토리 변경
			ftp.setFileType(FTP.BINARY_FILE_TYPE); // 업로드 파일 타입 셋팅
			
			ftp.mkd(ftpFile);
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		}finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // 접속 끊기
				} catch (IOException e) {
				}
			}
		}
	}
		
	public static void ftpFolderDelete(FTPVO fVO) {
		FTPClient ftp = null; // FTP Client 객체
		String uri = fVO.getUri();
		String id = fVO.getId();
		String pw = fVO.getPw();
		int port=fVO.getPort();
		String ftpFile=fVO.getFtpFile();
		fVO.setDirectoryLocation(ftpFile);
		String directoryLocation = fVO.getDirectoryLocation();
		try {
			ftp = new FTPClient(); // FTP Client 객체 생성
			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
			ftp.connect(uri,port); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호
			ftp.login(id, pw); // FTP 로그인 ID, PASSWORLD 입력
//    	      ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
			ftp.changeWorkingDirectory(directoryLocation); // 작업 디렉토리 변경
			ftp.setFileType(FTP.BINARY_FILE_TYPE); // 업로드 파일 타입 셋팅
			
			while(ftpReadFiles(fVO).size()!=0) {
				List<String> fileNames= ftpReadFiles(fVO);
				for(String fileName : fileNames) {
					FTPVO fVO2=new FTPVO();
					fVO2.setFtpFile(directoryLocation+"/"+fileName);
					if(fileName.contains(".")) {
						ftpFileDelete(fVO2);
					}else {
						ftpFolderDelete(fVO2);
					}
				}
			}
			ftp.rmd(ftpFile);
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		}finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // 접속 끊기
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static String ftpTxtReader(FTPVO fVO) {
		FTPClient ftp = null; // FTP Client 객체
		String uri = fVO.getUri();
		String id = fVO.getId();
		String pw = fVO.getPw();
		int port=fVO.getPort();
		String ftpFile = fVO.getFtpFile();
		String directoryLocation = fVO.getDirectoryLocation();
		String fileContent="";
		try {
			ftp = new FTPClient(); // FTP Client 객체 생성
			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
			ftp.connect(uri,port); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호
			ftp.login(id, pw); // FTP 로그인 ID, PASSWORLD 입력
    	    ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
			ftp.changeWorkingDirectory(directoryLocation); // 작업 디렉토리 변경

            InputStream inputStream = ftp.retrieveFileStream(ftpFile);
            if (inputStream != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                inputStream.close();

                // UTF-8로 디코딩하여 문자열로 변환
                fileContent = new String(baos.toByteArray(), "UTF-8");
            }
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		}finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // 접속 끊기
				} catch (IOException e) {
					
				}
			}
		}
		return fileContent;
	}
	
//	public static byte[] ftpImageReader(FTPVO fVO) {
//		FTPClient ftp = null; // FTP Client 객체
//		String uri = fVO.getUri();
//		String id = fVO.getId();
//		String pw = fVO.getPw();
//		int port=fVO.getPort();
//		String ftpFile = fVO.getFtpFile();
//		String directoryLocation = fVO.getDirectoryLocation();
//		byte[] decodedBytes = null;
//		try {
//			ftp = new FTPClient(); // FTP Client 객체 생성
//			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
//			ftp.connect(uri,port); // 서버접속 " "안에 서버 주소 입력 또는 "서버주소", 포트번호
//			ftp.login(id, pw); // FTP 로그인 ID, PASSWORLD 입력
//    	    ftp.enterLocalPassiveMode(); // Passive Mode 접속일때 
//			ftp.changeWorkingDirectory(directoryLocation); // 작업 디렉토리 변경
//			
//			//URL url = new URL("ftp://"+id+":"+pw+"192.168.35.133/"+ftpFile);
//			//BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(ftpFile));
//			StringBuilder imageDataBuilder = new StringBuilder();
//			String line;
//            while ((line = reader.readLine()) != null) {
//                imageDataBuilder.append(line);
//            }
//
//            String encodedImageData = imageDataBuilder.toString();
//            
//            decodedBytes = Base64.getDecoder().decode(encodedImageData);
//            
//            ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
//            //InputStream inputStream = ftp.retrieveFileStream(ftpFile);
//            
//            BufferedImage img = ImageIO.read(bis);
//            if (img != null) {
//                // 예를 들어, 이미지를 저장하거나 화면에 표시할 수 있습니다.
//                ImageIO.write(img, "png", new File("downloaded_image.png"));
//            }
//		} catch (IOException e) {
//			System.out.println("IO:" + e.getMessage());
//		}finally {
//			if (ftp != null && ftp.isConnected()) {
//				try {
//					ftp.disconnect(); // 접속 끊기
//				} catch (IOException e) {
//					
//				}
//			}
//		}
//		return decodedBytes;
//	}
}
