package com.school.sba.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.util.ResponseStructure;

public interface SubjectService {

	ResponseEntity<ResponseStructure<AcademicProgResponse>> addSubjects(int programId, SubjectRequest subjectrequest);

	ResponseEntity<ResponseStructure<AcademicProgResponse>> updateSubjects(int programId, SubjectRequest subjectreq);

	ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects();

	

}
