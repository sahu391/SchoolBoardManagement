package com.school.sba.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.SubjectService;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.util.ResponseStructure;

@RestController
public class SubjectController {
	
	@Autowired
	private SubjectService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/academic-programs/{programId}/subjects")
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> addSubjects(@PathVariable int programId,@RequestBody SubjectRequest subjectrequest) {
		return service.addSubjects(programId,subjectrequest);
	}
	
	@GetMapping("/subjects")
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>>findAllSubjects()
	{
		return service.findAllSubjects();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/academic-programs/{programId}")
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> updateSubjects(@PathVariable int programId,@RequestBody SubjectRequest subjectrequest) {
		return service.updateSubjects(programId,subjectrequest);
	}
	
	
	
}
