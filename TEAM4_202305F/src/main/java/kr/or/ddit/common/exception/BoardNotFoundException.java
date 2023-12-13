package kr.or.ddit.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BoardNotFoundException extends ResponseStatusException {

	public BoardNotFoundException(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
		// TODO Auto-generated constructor stub
	}

	public BoardNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
		// TODO Auto-generated constructor stub
	}

	public BoardNotFoundException(HttpStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public BoardNotFoundException(int rawStatusCode, String reason, Throwable cause) {
		super(rawStatusCode, reason, cause);
		// TODO Auto-generated constructor stub
	}

}
