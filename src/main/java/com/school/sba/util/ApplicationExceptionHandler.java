package com.school.sba.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.school.sba.Exception.DuplicateAdminException;
import com.school.sba.Exception.UserNotFoundException;



@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	private ResponseEntity<Object> structure(HttpStatus status,String message,Object rootCause){
		return new ResponseEntity<Object> (Map.of(
				"status",status.value(),
				"message",message,
				"rootCause",rootCause
				),status);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundById(UserNotFoundException ex){
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"User not found with the given Id");
	}
	
	@ExceptionHandler(DuplicateAdminException.class)
	public ResponseEntity<Object> duplicateAdmin(DuplicateAdminException ex){
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"Only one user can exists as Admin");
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> allErrors = ex.getAllErrors();
		
		Map<String,String> errors=new HashMap<String,String>();
		
		allErrors.forEach(error ->{
			FieldError fielderror=(FieldError)error;
			errors.put(fielderror.getField(),fielderror.getDefaultMessage());
			
		});
		
		return structure(HttpStatus.BAD_REQUEST,"Failed to save the data",errors);
	}
	
}
