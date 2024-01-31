package com.school.sba.ServiceImpl;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.school.sba.Entity.ClassHour;
import com.school.sba.Entity.Schedule;
import com.school.sba.Entity.School;
import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;
import com.school.sba.Exception.AcademicProgamNotFoundException;
import com.school.sba.Exception.IllegalException;
import com.school.sba.Exception.InvalidClassHourException;
import com.school.sba.Exception.ScheduleNotFoundException;
import com.school.sba.Exception.SubjectNotFoundException;
import com.school.sba.Exception.TeacherNotFoundException;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.AcademicProgRepo;
import com.school.sba.Repository.ClassHourRepo;
import com.school.sba.Repository.SubjectRepo;
import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.ClassHourService;
import com.school.sba.enums.ClassStatus;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.ClassHourDTO;
import com.school.sba.responsedto.ClassHourResponse;
import com.school.sba.util.ResponseStructure;


@Service
public class ClassHourServiceImpl2 implements ClassHourService {
	
	@Autowired
	private AcademicProgRepo academicProgramRepository;
	@Autowired
	private ClassHourRepo classHourRepository;
	@Autowired
	private ResponseStructure<ClassHourResponse> structure;
	@Autowired
	private SubjectRepo subjectrepo;
	@Autowired
	private UserRepository userrepo;
	
	
	private ClassHourResponse mapToClassHourResponse(ClassHour classhour) {
		ClassHourResponse res = new ClassHourResponse();
		res.setBeginsAt(classhour.getBeginsAt());
		res.setClassHourId(classhour.getClassHourId());
		res.setEndsAt(classhour.getEndsAt());
		res.setClassStatus(classhour.getClassStatus());
		res.setRoomNo(classhour.getRoomNo());
		return res;
	}
	
	private boolean isBreakTime(LocalDateTime beginsAt, LocalDateTime endsAt, Schedule schedule)
	{
		LocalTime breakTimeStart = schedule.getBreakTime();
		
		return ((breakTimeStart.isAfter(beginsAt.toLocalTime()) && breakTimeStart.isBefore(endsAt.toLocalTime())) || breakTimeStart.equals(beginsAt.toLocalTime()));
	}
	
	private boolean isLunchTime(LocalDateTime beginsAt, LocalDateTime endsAt , Schedule schedule)
	{
		LocalTime lunchTimeStart = schedule.getLunchTime();
		
		return ((lunchTimeStart.isAfter(beginsAt.toLocalTime()) && lunchTimeStart.isBefore(endsAt.toLocalTime())) || lunchTimeStart.equals(beginsAt.toLocalTime()));
    }
	    
	    
	@Override
	public ResponseEntity<ResponseStructure<ClassHourResponse>> generateClassHour(int programId) 
	{
		return academicProgramRepository.findById(programId)
				.map(academicProgarm -> {
					School school = academicProgarm.getSchool();
					Schedule schedule = school.getSchedule();
					if(schedule!=null)
					{
						int classHourPerDay = schedule.getClassHoursPerDay();
						int classHourLength = (int) schedule.getClassHourLengthInMinutes().toMinutes();
						
						LocalDateTime currentTime = LocalDateTime.now().with(schedule.getOpensAt());
						
						LocalDateTime lunchTimeStart = LocalDateTime.now().with(schedule.getLunchTime());
						LocalDateTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLength().toMinutes());
						LocalDateTime breakTimeStart = LocalDateTime.now().with(schedule.getBreakTime());
						LocalDateTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLength().toMinutes());
						
						for(int day = 1 ; day <= 6 ; day++)
						{
							for(int hour = 1 ; hour <= classHourPerDay+2 ; hour++)
							{
								ClassHour classHour = new ClassHour();
								LocalDateTime beginsAt = currentTime;
								LocalDateTime endsAt = beginsAt.plusMinutes(classHourLength);
								
								if(!isLunchTime(beginsAt, endsAt, schedule))
								{
									if(!isBreakTime(beginsAt, endsAt, schedule))
									{
										classHour.setBeginsAt(beginsAt);
										classHour.setEndsAt(endsAt);
										classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);
										
										currentTime = endsAt;
									}
									else
									{
										classHour.setBeginsAt(breakTimeStart);
										classHour.setEndsAt(breakTimeEnd);
										classHour.setClassStatus(ClassStatus.BREAK_TIME);
										currentTime = breakTimeEnd;
									}
								}
								else
								{
									classHour.setBeginsAt(lunchTimeStart);
									classHour.setEndsAt(lunchTimeEnd);
									classHour.setClassStatus(ClassStatus.LUNCH_TIME);
									currentTime = lunchTimeEnd;
								}
								classHour.setProglist(academicProgarm);
								classHourRepository.save(classHour);
								structure.setStatus(HttpStatus.CREATED.value());
								structure.setMessage("ClassHour generated successfully for the academic progarm");
							    structure.setData(mapToClassHourResponse(classHour));
							}
							currentTime = currentTime.plusDays(1).with(schedule.getOpensAt());
							
						}
						
			
					}
					else
						throw new ScheduleNotFoundException("The school does not contain any schedule, please provide a schedule to the school");
					return new ResponseEntity<ResponseStructure<ClassHourResponse>>(structure, HttpStatus.CREATED);

					
				})
				.orElseThrow(() -> new AcademicProgamNotFoundException("Invalid Program Id"));
			}

	@Override
	public ResponseEntity<String> updateClassHour(List<ClassHourDTO> classHourDTOList) {
		 validateClassHours(classHourDTOList);

	        List<ClassHour> classHoursToUpdate = new ArrayList<>();

	        for (ClassHourDTO classHourDTO : classHourDTOList) {
	            Subject subject = subjectrepo.findById(classHourDTO.getSubjectId())
	                    .orElseThrow(() -> new SubjectNotFoundException("Subject not found with id: " + classHourDTO.getSubjectId()));

	            User teacher = userrepo.findById(classHourDTO.getUserId())
	                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + classHourDTO.getUserId()));
	            
	           
	            // Check for duplicate class hours
	            if (classHourRepository.existsByRoomNoAndBeginsAtAndEndsAt(classHourDTO.getRoomNo(),
	                    classHourDTO.getBeginsAt(), classHourDTO.getEndsAt())) {
	                throw new InvalidClassHourException("Duplicate class hour found for room number "
	                        + classHourDTO.getRoomNo() + " at date and time " + classHourDTO.getBeginsAt());
	            }
	            if(teacher.getUserRole().equals(UserRole.TEACHER)) {

	            ClassHour classHour = classHourRepository.findById(classHourDTO.getClassHourId())
	            		.orElseThrow(()->new IllegalException("classhourId not found"));
	            classHour.setSubject(subject);
	            classHour.setUser(teacher);
	            classHour.setRoomNo(classHourDTO.getRoomNo());
	            classHour.setBeginsAt(classHourDTO.getBeginsAt());
	            classHour.setEndsAt(classHourDTO.getEndsAt());
	            classHour.setClassStatus(classHourDTO.getClassStatus());

	            classHoursToUpdate.add(classHour);
	        }
	       else {
		       throw new TeacherNotFoundException("userId entered is not Teacher!");
		     }

	        classHourRepository.saveAll(classHoursToUpdate);
	        
	        }
	        return ResponseEntity.ok("Class hours updated successfully.");
	    }

	    private void validateClassHours(List<ClassHourDTO> classHourDTOList) {
	        // Add any additional validation logic based on your requirements
	        if (classHourDTOList == null || classHourDTOList.isEmpty()) {
	            throw new InvalidClassHourException("Class hour list cannot be empty");
	        }
	    }

	
		public ResponseEntity<String> deleteClassHour(List<ClassHour> classHour) {
			 for (ClassHour classhour : classHour) {
			classHourRepository.delete(classhour);
			 }
			return ResponseEntity.ok("Class hours deleted successfully.");
		}

   

}		
	



