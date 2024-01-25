package com.school.sba.Exception;

public class AcademicProgamNotFoundException extends RuntimeException{

	private String message;

	public String getMessage() {
		return message;
	}

	public AcademicProgamNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	
	
}
