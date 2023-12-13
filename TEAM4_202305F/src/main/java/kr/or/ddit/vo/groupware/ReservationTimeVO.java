package kr.or.ddit.vo.groupware;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReservationTimeVO implements Serializable{
	@NotBlank
	private String reservationCd;
	@NotBlank
	private String useTimeCd;
}
