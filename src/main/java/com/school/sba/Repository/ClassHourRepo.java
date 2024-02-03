package com.school.sba.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.Entity.AcademicProgram;
import com.school.sba.Entity.ClassHour;
import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;

public interface ClassHourRepo extends JpaRepository<ClassHour, Integer>{

	

	boolean existsByRoomNoAndBeginsAtAndEndsAt(int roomNo, LocalDateTime beginsAt, LocalDateTime endsAt);


	List<ClassHour> findByUser(User u);
	
	
}
