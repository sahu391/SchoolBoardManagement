package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.Entity.School;

public interface SchoolRepo extends JpaRepository<School, Integer>{

}
