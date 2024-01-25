package com.school.sba.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Service.SchoolService;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

@RestController

public class SchoolController {
	@Autowired
	private SchoolService SchoolService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/users/{userId}/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(@PathVariable int userId,@RequestBody  SchoolRequest school)
	{
		return SchoolService.saveSchool(userId,school);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(@PathVariable int schoolId,@RequestBody SchoolRequest school)
	{
		return SchoolService.updateSchool(schoolId,school);
	}

	@GetMapping("/schools")
	public ResponseEntity<ResponseStructure<List<SchoolResponse>>> findAllSchool()
	{
		return SchoolService.findAllSchool();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(@PathVariable int schoolId)
	{
		return SchoolService.deleteSchool(schoolId);
	}
	
	
	@GetMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(@PathVariable int schoolId)
	{
		return SchoolService.findSchoolById(schoolId);
	}
	
	 
}
