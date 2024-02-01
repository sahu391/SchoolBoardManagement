package com.school.sba.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.school.sba.Entity.AcademicProgram;
import com.school.sba.Entity.ClassHour;
import com.school.sba.Entity.School;
import com.school.sba.Entity.User;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.AcademicProgRepo;
import com.school.sba.Repository.ClassHourRepo;
import com.school.sba.Repository.SchoolRepo;
import com.school.sba.Repository.UserRepository;
import com.school.sba.ServiceImpl.AcademicProgServiceImpl;
import com.school.sba.ServiceImpl.ClassHourServiceImpl2;
import com.school.sba.ServiceImpl.SchoolServiceImpl;
import com.school.sba.ServiceImpl.UserServiceImpl;
import com.school.sba.enums.UserRole;

@Component
public class ScheduledJobs {
	@Autowired
	private UserServiceImpl userservice;
	@Autowired
	private SchoolServiceImpl schoolservice;
    @Autowired
    private AcademicProgServiceImpl programservice;
	@Autowired
	private AcademicProgRepo academicProgramRepository;
	@Autowired
	private ClassHourRepo classHourRepository;
	@Autowired
	private UserRepository userepo;
	
	
	@Scheduled(fixedDelay = 1000L*60*5)
	public void test() {
		
		//userservice.deleteUserIfDeleted();
		//schoolservice.deleteSchoolIfDeleted();
		//programservice.acadeleteAcademicProgramIfDeleted();
		
	}
	


}
