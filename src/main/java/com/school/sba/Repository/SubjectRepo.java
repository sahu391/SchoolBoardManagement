package com.school.sba.Repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.Entity.Subject;



@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {

	Optional<Subject> findBySubjectName(String name);

	
	
	
}
