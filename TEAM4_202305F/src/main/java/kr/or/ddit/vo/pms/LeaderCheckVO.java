package kr.or.ddit.vo.pms;

import lombok.Data;

@Data
public class LeaderCheckVO {
	 private int page;
	 private String proSn;
	 private String empCd;
	 private String searchType;
	 private String searchWord;
}
