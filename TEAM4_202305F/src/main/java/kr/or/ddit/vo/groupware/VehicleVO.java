package kr.or.ddit.vo.groupware;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 23.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode
public class VehicleVO implements Serializable{
	private String vhcleCd; /* 차량 코드 */
	@NotBlank
	private String vhcleNo; /* 차량 번호 */
	@Digits(integer=3, fraction=0) //최대 세자리 숫자(소수는 허용하지 않음)
	private int vhcleCapacity; /* 수용 인원 */ 
	private String vhcleModel; /* 차량 모델 */
	private String vhcleRegistDate; /* 등록일자 */
	private String vhcleFlag; /* 차량 상태 */
	
	private String vhcleImg; /* 차량 이미지 url */
	private MultipartFile vhcleImage;  
	
	private List<VehicleReservationVO> vhcleReserveList; // 1:N 관계
}
