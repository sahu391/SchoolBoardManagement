package com.school.sba.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.Entity.ClassHour;
import com.school.sba.requestdto.ClassHourDTO;
import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.responsedto.ClassHourResponse;
import com.school.sba.util.ResponseStructure;

public interface ClassHourService {

	ResponseEntity<ResponseStructure<ClassHourResponse>> generateClassHour(int programId);

	ResponseEntity<String> updateClassHour(List<ClassHourDTO> classHourDTOList);

	
	
	ResponseEntity<ResponseStructure<List<ClassHour>>> AutoRepeatNextWeekClassHours(int programId);

	ClassHour createNewClassHour(ClassHour cl);



}
