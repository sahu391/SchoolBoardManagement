package com.school.sba.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.Entity.ClassHour;
import com.school.sba.Entity.Subject;
@Repository
public interface ClassHourRepo extends JpaRepository<ClassHour, Integer>{

	

	boolean existsByRoomNoAndBeginsAtAndEndsAt(int roomNo, LocalDateTime beginsAt, LocalDateTime endsAt);
	

}
