package com.school.sba;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.school.sba.Repository.SchoolRepo;

@SpringBootApplication
public class SchoolBoardApplication {

	public static void main(String[] args) {
	ConfigurableApplicationContext	ac= SpringApplication.run(SchoolBoardApplication.class, args);
	
	}

}
