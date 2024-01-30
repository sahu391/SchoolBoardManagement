package com.school.sba.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Entity.ClassHour;
import com.school.sba.Service.ClassHourService;
import com.school.sba.requestdto.ClassHourDTO;
import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.responsedto.ClassHourResponse;
import com.school.sba.util.ResponseStructure;

@RestController
public class ClassHourController {

	@Autowired
	private ClassHourService service;
	
	@PostMapping("/academic-program/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<ClassHourResponse>> generateClassHour(@PathVariable int programId){
		return service.generateClassHour(programId);
	}
	
	@PutMapping("/class-hours")
	public ResponseEntity<String> updateClassHour(@RequestBody List<ClassHourDTO> classHourDTOList){
		return service.updateClassHour(classHourDTOList);
		 
	}
}
