package com.school.sba.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import com.school.sba.Entity.School;
import com.school.sba.Service.SchoolService;
import com.school.sba.util.ResponseStructure;

@RestController
@RequestMapping(value="/school")
public class SchoolController {
	@Autowired
	private SchoolService service;
	
	@PostMapping("/schools")
	public ResponseEntity<ResponseStructure<School>> saveSchool(@RequestBody School school) {
		return service.saveSchool(school);
	}
	
	@GetMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<School>> findSchool(@PathVariable int schoolId) {
		return service.findSchool(schoolId);
	}
	
	@PutMapping("/schools/{school}")
	public ResponseEntity<ResponseStructure<School>> updateSchool(@PathVariable int schoolId,@RequestBody School school) {
		return service.updateSchool(schoolId,school);
	}
	
	@DeleteMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<School>> deleteSchool(@PathVariable int schoolId) {
		return service.deleteSchool(schoolId);
	}
}
