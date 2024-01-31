package com.school.sba.Service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.Entity.Subject;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.AcademicProgRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

public interface AcademicProgService {

	ResponseEntity<ResponseStructure<AcademicProgResponse>> saveAcademicProgram(int schoolId,
			AcademicProgRequest academicProgramRequest);

	ResponseEntity<ResponseStructure<List<AcademicProgResponse>>> findAcademicProgram(int schoolId);

	ResponseEntity<ResponseStructure<AcademicProgResponse>> addTeacherToProgram(int programId, String subjectName,
			String userName);

	ResponseEntity<ResponseStructure<AcademicProgResponse>> deleteByProgramId(int programId);

	

	

}
