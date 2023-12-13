package kr.or.ddit.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FTPVO {
//	private String uri="fe80::5615:36d9:2a4c:f329%12";	// 305호 컴
//	private String uri="fe80::b74a:7e8e:f33f:506%9";	// 노트북
	private String uri="192.168.35.133";	// 노트북 ipv4
	private int port=21;
	private String id="l4";
	private String pw="java";
	private String directoryLocation="/";
	private String localFile;		// 작업할 로컬 파일
	private String ftpFile;			// ftp서버에서 작업할 파일
	private String route;
}
