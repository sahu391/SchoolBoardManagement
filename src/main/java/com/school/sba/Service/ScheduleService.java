package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.Entity.Schedule;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.util.ResponseStructure;

public interface ScheduleService {

	ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(int schoolId, ScheduleRequest schedule);

	ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(int scheduleId);

	ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(int scheduleId,ScheduleRequest schedulereq);

	

}
