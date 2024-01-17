package com.school.sba.Exception;

public class InvalidUserRoleException extends RuntimeException {
	
	private String message;

	public InvalidUserRoleException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
