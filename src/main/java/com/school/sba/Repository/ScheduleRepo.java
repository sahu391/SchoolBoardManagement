package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.Entity.Schedule;

public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {

}
