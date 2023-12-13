package kr.or.ddit.vo.pms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="empCd")
public class ChargerVO {
	
	@NotBlank
	private String empCd;		//사번
	@NotNull
	private Integer jobSn;		//일감순번
	@NotBlank
	private String proSn;		//프로젝트일련번호

	private String tempEmpCd;
	
	private String empLeadername;	//프로젝트 리더의 이름
	
	//has a
	private EmployeeVO emp;
}
