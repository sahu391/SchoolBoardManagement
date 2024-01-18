package com.school.sba.Exception;

public class IllegalException extends RuntimeException{
	
	private String message;

	public IllegalException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
