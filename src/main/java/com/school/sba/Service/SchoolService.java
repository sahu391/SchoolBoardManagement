package com.school.sba.Service;

import org.springframework.http.ResponseEntity;
import com.school.sba.Entity.School;
import com.school.sba.util.ResponseStructure;


public interface SchoolService {
	
	public ResponseEntity<ResponseStructure<School>> saveSchool(School school);

	public ResponseEntity<ResponseStructure<School>> findSchool(int schoolId);
	
	public ResponseEntity<ResponseStructure<School>>  updateSchool(int schoolId,School school);

	public ResponseEntity<ResponseStructure<School>>  deleteSchool(int schoolId);
}
