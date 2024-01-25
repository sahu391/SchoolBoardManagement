package com.school.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdmineCannotBeAssignedToAcademicProgram extends RuntimeException {
	private String message;
	
}
