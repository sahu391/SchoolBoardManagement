package com.school.sba.ServiceImpl;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.AcademicProgram;
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

import jakarta.transaction.Transactional;


@Service
public class ClassHourServiceImpl2 implements ClassHourService {
	
	@Autowired
	private AcademicProgRepo academicProgramRepository;
	@Autowired
	private ClassHourRepo classHourRepository;
	@Autowired
	private ResponseStructure<ClassHourResponse> structure;
	@Autowired
	private ResponseStructure<List<ClassHour>> listStructure;
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
	public ResponseEntity<ResponseStructure<ClassHourResponse>> generateClassHour(int programId) {
	    return academicProgramRepository.findById(programId)
	            .map(academicProgram -> {
	                School school = academicProgram.getSchool();
	                Schedule schedule = school.getSchedule();

	                if (schedule != null) {
	                    int classHourPerDay = schedule.getClassHoursPerDay();
	                    int classHourLength = (int) schedule.getClassHourLengthInMinutes().toMinutes();

	                    LocalDateTime currentTime = LocalDateTime.now().with(schedule.getOpensAt());
	                    
	                    LocalDateTime lunchTimeStart = LocalDateTime.now().with(schedule.getLunchTime());
						LocalDateTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLength().toMinutes());
						LocalDateTime breakTimeStart = LocalDateTime.now().with(schedule.getBreakTime());
						LocalDateTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLength().toMinutes());
						
						int currentDayOfWeek = currentTime.getDayOfWeek().getValue();
	                    for (int day = 1; day <= ((7-currentDayOfWeek)+7); day++) {
	                    	
	                    	if(currentTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
	                        for (int hour = 1; hour <= classHourPerDay+2; hour++) {
	                        	
	                            ClassHour classHour = new ClassHour();
	                            LocalDateTime beginsAt = currentTime;
	                            LocalDateTime endsAt = beginsAt.plusMinutes(classHourLength);
	                            DayOfWeek dayOfWeek=beginsAt.getDayOfWeek();

	                            if (!isLunchTime(beginsAt, endsAt, schedule) && !isBreakTime(beginsAt, endsAt, schedule)) {
	                                classHour.setBeginsAt(beginsAt);
	                                classHour.setEndsAt(endsAt);
	                                classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);

	                                currentTime = endsAt;  // Move to the next time slot
	                            } else if (isBreakTime(beginsAt, endsAt, schedule)) {
	                                classHour.setBeginsAt(breakTimeStart);
	                                classHour.setEndsAt(breakTimeEnd);
	                                classHour.setClassStatus(ClassStatus.BREAK_TIME);

	                                // Skip the break and move to the next time slot
	                                currentTime = endsAt;
	                            } else {
	                                classHour.setBeginsAt(lunchTimeStart);
	                                classHour.setEndsAt(lunchTimeEnd);
	                                classHour.setClassStatus(ClassStatus.LUNCH_TIME);

	                                // Skip lunch and move to the next time slot
	                                currentTime = endsAt;
	                            }

	                            classHour.setDayOfWeek(dayOfWeek);
	                            classHour.setProglist(academicProgram);
	                            
	                            classHourRepository.save(classHour);
	                            

	                            // Output or logging can be added here
	                        }
	                    	}
	                        // Move to the next day's opensAt time
	                        currentTime = LocalDateTime.of(currentTime.toLocalDate().plusDays(1), schedule.getOpensAt());
	                        lunchTimeStart=lunchTimeStart.plusDays(1);
	                        lunchTimeEnd=lunchTimeEnd.plusDays(1);
	                        breakTimeStart=breakTimeStart.plusDays(1);
	                        breakTimeEnd=breakTimeEnd.plusDays(1);
	                        
	                    }
	                } else {
	                    throw new ScheduleNotFoundException("The school does not contain any schedule, please provide a schedule to the school");
	                }

	                ResponseStructure<ClassHourResponse> structure = new ResponseStructure<>();
	                structure.setStatus(HttpStatus.CREATED.value());
	                structure.setMessage("ClassHour generated successfully for the academic program");
	                return new ResponseEntity<>(structure, HttpStatus.CREATED);
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


		
		
		
		
		
		@Override
		public ResponseEntity<ResponseStructure<List<ClassHour>>> AutoRepeatNextWeekClassHours(int programId) {
			
			AcademicProgram academicProgram = academicProgramRepository.findById(programId).get();

			List<ClassHour> originalClassHours  = academicProgram.getClassHourList();
			List<ClassHour> newClassHours = new ArrayList<>();

			originalClassHours.forEach((cl) -> {
				ClassHour createNewClassHour = createNewClassHour(cl);
				newClassHours.add(createNewClassHour);

			});

			newClassHours.forEach((hour) -> {
				LocalDateTime plusDays = hour.getBeginsAt().plusDays(7);
				hour.setBeginsAt(plusDays);
				classHourRepository.save(hour);

			});

			listStructure.setData(null);
			listStructure.setMessage("New Class Hour Created For Next Week");
			listStructure.setStatus(HttpStatus.CREATED.value());

			return new ResponseEntity<ResponseStructure<List<ClassHour>>>(listStructure, HttpStatus.CREATED);

		}


		@Override
		public ClassHour createNewClassHour(ClassHour cl) {
			ClassHour classHour2 = new ClassHour();

			classHour2.setProglist(cl.getProglist());
			classHour2.setBeginsAt(cl.getBeginsAt());
			classHour2.setClassStatus(cl.getClassStatus());
			classHour2.setEndsAt(cl.getEndsAt());
			classHour2.setRoomNo(cl.getRoomNo());
			classHour2.setSubject(cl.getSubject());
			classHour2.setUser(cl.getUser());

			return classHour2;

	}
		
	
		private List<ClassHour> generateClassHoursForWeek(AcademicProgram program, LocalDate startingDate) {

			List<ClassHour> classHours = new ArrayList<>();
			Schedule schedule = program.getSchool().getSchedule();
			Duration classDuration = schedule.getClassHourLengthInMinutes();
			Duration lunchDuration = schedule.getLunchLength();
			Duration breakDuration = schedule.getBreakLength();
			LocalTime breakTime = schedule.getBreakTime();
			LocalTime lunchTime = schedule.getLunchTime();
			Duration topUp = Duration.ofMinutes(2);

			// Find the next Monday from the starting date
			LocalDate nextMonday = startingDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

			for (int dayOfWeek = 0; dayOfWeek < 6; dayOfWeek++) { // Iterate from Monday to Saturday

				LocalDate currentDate = nextMonday.plusDays(dayOfWeek);
				LocalTime opensAt = schedule.getOpensAt();
				LocalTime endsAt = opensAt.plus(classDuration);
				ClassStatus status = ClassStatus.NOT_SCHEDULED;

				for (int classPerDay = schedule.getClassHoursPerDay(); classPerDay > 0; classPerDay--) {
					ClassHour classHour = ClassHour.builder()
							.beginsAt(LocalDateTime.of(currentDate, opensAt))
							.endsAt(LocalDateTime.of(currentDate, endsAt))
							.roomNo(10)
							.classStatus(status)
							.proglist(program)
							.build();

					classHours.add(classHour);

					if (breakTime.isAfter(opensAt.minus(topUp)) && breakTime.isBefore(endsAt.plus(topUp))) {
						opensAt = opensAt.plus(breakDuration);
						endsAt = endsAt.plus(breakDuration);
					} else if (lunchTime.isAfter(opensAt.minus(topUp)) && lunchTime.isBefore(endsAt.plus(topUp))) {
						opensAt = opensAt.plus(lunchDuration);
						endsAt = endsAt.plus(lunchDuration);
					}

					opensAt = endsAt;
					endsAt = opensAt.plus(classDuration);
				}
			}

			return classHours;
		}

		
		
		public ResponseEntity<String> deleteClassHour(List<ClassHour> classHour) {
			 for (ClassHour classhour : classHour) {
			classHourRepository.delete(classhour);
			 }
			return ResponseEntity.ok("Class hours deleted successfully.");
		}
		
		
}

			
	

	



