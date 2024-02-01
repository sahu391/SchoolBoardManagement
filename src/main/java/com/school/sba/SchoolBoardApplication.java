package com.school.sba;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.school.sba.Entity.User;
import com.school.sba.Repository.UserRepository;
import com.school.sba.enums.UserRole;

@EnableScheduling
@SpringBootApplication
public class SchoolBoardApplication {

		
		public static void main(String[] args) {
		SpringApplication.run(SchoolBoardApplication.class, args);
		
		
		
		}
		
		

}
