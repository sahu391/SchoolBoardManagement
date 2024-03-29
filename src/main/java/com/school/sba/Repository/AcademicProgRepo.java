package com.school.sba.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.Entity.AcademicProgram;
import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;


public interface AcademicProgRepo extends JpaRepository<AcademicProgram, Integer>{

	Optional<User> findBySubject(Subject subject);
	
	
	List<AcademicProgram> findByIsDeleted(boolean isDeleted);
}
