package com.school.sba.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.school.sba.Entity.School;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;


public interface SchoolService {

	ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(int userId,SchoolRequest school);

	ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(int schoolId, SchoolRequest school);

	ResponseEntity<ResponseStructure<List<SchoolResponse>>> findAllSchool();

	ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId);

	ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(int schoolId);
	
	
}
