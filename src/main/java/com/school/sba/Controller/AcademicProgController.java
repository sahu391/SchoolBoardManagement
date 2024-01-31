package com.school.sba.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;
import com.school.sba.Service.AcademicProgService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.AcademicProgRequest;

import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

@RestController
public class AcademicProgController {

	
		@Autowired
		private AcademicProgService AcademicProgramService;

		@PreAuthorize("hasAuthority('ADMIN')")
		@PostMapping("/schools/{schoolId}/academic-programs")
		public ResponseEntity<ResponseStructure<AcademicProgResponse>> saveAcademicProgram(@PathVariable int schoolId,@RequestBody AcademicProgRequest academicProgramRequest)
		{
			return AcademicProgramService.saveAcademicProgram(schoolId,academicProgramRequest);
		}


		@GetMapping("/schools/{schoolId}/academic-programs")
		public ResponseEntity<ResponseStructure<List<AcademicProgResponse>>> findAcademicProgram(@PathVariable int schoolId)
		{
			return AcademicProgramService.findAcademicProgram(schoolId);
		}
		
		@PostMapping("/{programId}/addTeacher")
		public ResponseEntity<ResponseStructure<AcademicProgResponse>> addTeacherToProgram(
				@PathVariable int programId,
				@RequestParam String userName,
	            @RequestParam String subjectName)
		{
			return AcademicProgramService.addTeacherToProgram(programId,userName,subjectName);
		}
		
		@DeleteMapping("/academic-program/{programId}")
		public ResponseEntity<ResponseStructure<AcademicProgResponse>> deleteByProgramId(@PathVariable int programId)
	           
		{
			return AcademicProgramService.deleteByProgramId(programId);
		}
		
}
