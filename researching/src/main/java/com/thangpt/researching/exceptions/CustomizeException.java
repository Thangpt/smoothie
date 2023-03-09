package com.thangpt.researching.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomizeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected long code = 20201;
	protected String error;
	protected String message;
	protected HttpStatus httpStatus = HttpStatus.OK;

	public CustomizeException() {
	}
	
	public CustomizeException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public CustomizeException(long code, String message,String error,HttpStatus httpStatus) {
		this.message = message;
		this.code = code;
		this.error= error;
		this.httpStatus = httpStatus;
	}
	public CustomizeException(String error, String message) {
		this.message = message;
		this.error = error;
	}
	public CustomizeException(long code, String message,HttpStatus httpStatus) {
		this.message = message;
		this.code = code;
		this.httpStatus = httpStatus;
	}
	
	public CustomizeException(String message) {
		this.message = message;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public CustomizeException(String message, Object... objects) {
		this.message = String.format(message, objects);
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	// public CustomizeException(Error error, Object... objects) {
	// 	this.message = String.format(error.getMessage(), objects);
	// 	this.code = error.getCode();
	// 	this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	// }

	// public CustomizeException(Error error) {
	// 	this.message = error.getMessage();
	// 	this.code = error.getCode();
	// 	this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	// }

	public CustomizeException(long code, String message) {
		this.message = message;
		this.code = code;
	}

	public CustomizeException(String message, Exception ex) {
		this.message = message + "\n" + ex.getMessage();

	}

	public CustomizeException(Exception ex) {
		super(ex);
	}

	public CustomizeException(String message, CustomizeException ex) {
		this.message = message + "\n" + ex.getMessage();
	}

	@Override
	public String getMessage() {
		return message;
	}
}