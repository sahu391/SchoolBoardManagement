package com.school.sba.Controller;

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

import com.school.sba.Service.ScheduleService;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.util.ResponseStructure;

@RestController
public class ScheduleController {
	
	@Autowired
	private ScheduleService service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(@PathVariable int schoolId,@RequestBody ScheduleRequest schedule) {
		return service.saveSchedule(schoolId,schedule);
	}
	
	@GetMapping("/schedules/{scheduleId}")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(@PathVariable int scheduleId) {
		return service.findSchedule(scheduleId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/schedules/{scheduleId}")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@PathVariable int scheduleId,@RequestBody ScheduleRequest schedulereq) {
		return service.updateSchedule(scheduleId,schedulereq);
	}
	
	
}
