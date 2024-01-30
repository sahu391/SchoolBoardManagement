package com.school.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidClassHourException extends RuntimeException {
	private String message;
	

}
