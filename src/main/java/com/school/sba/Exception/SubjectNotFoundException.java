package com.school.sba.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubjectNotFoundException extends RuntimeException {
private String message;
}
