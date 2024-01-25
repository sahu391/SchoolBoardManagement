package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.Entity.School;
@Repository
public interface SchoolRepo extends JpaRepository<School, Integer>{

}
