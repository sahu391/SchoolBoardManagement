package com.school.sba.Exception;

public class DuplicateAdminException extends RuntimeException{
	
	private String message;

	public DuplicateAdminException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
