package com.school.sba.ServiceImpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.Schedule;
import com.school.sba.Entity.School;
import com.school.sba.Entity.User;
import com.school.sba.Exception.DuplicateAdminException;
import com.school.sba.Exception.IllegalException;
import com.school.sba.Exception.ScheduleNotFoundException;
import com.school.sba.Exception.SchoolNotFoundException;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.ScheduleRepo;
import com.school.sba.Repository.SchoolRepo;
import com.school.sba.Service.ScheduleService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.ScheduleRequest;

import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

@Service
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleRepo repo;
	
	@Autowired
	private SchoolRepo schoolrepo;
	@Autowired
	private ResponseStructure<ScheduleResponse> structure;
	
	
	
	private Schedule mapToSchedule(ScheduleRequest schedulerequest) {
		Schedule schedule = new Schedule();
		
		schedule.setOpensAt(schedulerequest.getOpensAt());
		schedule.setClosesAt(schedulerequest.getClosesAt());
		schedule.setClassHoursPerDay(schedulerequest.getClassHoursPerDay());
        schedule.setLunchTime(schedulerequest.getLunchTime());
        schedule.setBreakTime(schedulerequest.getBreakTime());
		
		int classHourLengthInMinutes = schedulerequest.getClassHourLengthInMinutes();
        Duration classHourDuration = Duration.ofMinutes(classHourLengthInMinutes);
        schedule.setClassHourLengthInMinutes(classHourDuration);
        
        int breakLength = schedulerequest.getBreakLength();
        Duration breakLengthDuration=Duration.ofMinutes(breakLength);
        schedule.setBreakLength(breakLengthDuration);
        
        int lunchLength = schedulerequest.getLunchLength() ;
        Duration lunchLengthDuration=Duration.ofMinutes(lunchLength);
        schedule.setLunchLength(lunchLengthDuration);
		return schedule;
	}
	
	private ScheduleResponse mapToScheduleResponse(Schedule schedule) {
		ScheduleResponse scheduleres = new ScheduleResponse();
		scheduleres.setScheduleId(schedule.getScheduleId());
		scheduleres.setOpensAt(schedule.getOpensAt());
		scheduleres.setClosesAt(schedule.getClosesAt());
		scheduleres.setClassHoursPerDay(schedule.getClassHoursPerDay());
		scheduleres.setBreakTime(schedule.getBreakTime());
		scheduleres.setLunchTime(schedule.getLunchTime());

		int lunchLengthInMinutes = (int) schedule.getLunchLength().toMinutes();
		scheduleres.setLunchLength(lunchLengthInMinutes);
		
		int breakLengthInMinutes = (int) schedule.getBreakLength().toMinutes();
		scheduleres.setBreakLength(breakLengthInMinutes);
		
		int classHourLengthInMinutes = (int) schedule.getClassHourLengthInMinutes().toMinutes();
		scheduleres.setClassHourLengthInMinutes(classHourLengthInMinutes);

		return scheduleres;
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(int schoolId, ScheduleRequest schedulereq) {
		return schoolrepo.findById(schoolId).map(u->{
		if(u.getSchedule()==null) {
			 
				Schedule schedule =mapToSchedule(schedulereq);
				schedule=repo.save(schedule);
				u.setSchedule(schedule);
				schoolrepo.save(u);
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setMessage("schedule for schools saved successfully");
				structure.setData(mapToScheduleResponse(schedule));
				return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.CREATED);
				
			}else
				throw new IllegalException("school can schedule only one schedule");
	}).orElseThrow(()-> new UserNotFoundException("failed to save the data"));
	
	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(int scheduleId) {
		
		Schedule schedule=repo.findById(scheduleId)
        		 .orElseThrow(()->new UserNotFoundException("user not Found!!"));
        	
		
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("RegisteredUser Data Fetched Successfully");
	    structure.setData(mapToScheduleResponse(schedule));
		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(int scheduleId,ScheduleRequest schedulereq) {
		Schedule schedule=repo.findById(scheduleId).
				map(u->{
					Schedule updated = mapToSchedule(schedulereq);
					updated.setScheduleId(u.getScheduleId());
					return repo.save(updated);
				}).
				orElseThrow(()->new ScheduleNotFoundException("Schedule not Found!!"));

		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Schedule for school updated successfully");
		structure.setData(mapToScheduleResponse(schedule));
		return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.OK);
		
	}

	
	public ResponseEntity<String> deleteSchedule(Schedule schedule) {
		
		repo.delete(schedule);
		return ResponseEntity.ok("Schedule deleted successfully.");
	}
	
	
}
