package com.school.sba.Exception;

public class SchoolNotFoundException extends RuntimeException{
	
	private String message;

	public SchoolNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
