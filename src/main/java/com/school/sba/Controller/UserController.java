package com.school.sba.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;
import com.school.sba.Service.UserService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@RequestBody @Valid UserRequest user) {
		return userService.registerAdmin(user);
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequest user) {
		return userService.registerUser(user);
	}
	

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findRegisteredUser(@PathVariable int userId) {
		return userService.findRegisterdUser(userId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteRegisteredUser(@PathVariable int userId) {

		return userService.deleteRegisterdUser(userId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/academic-programs/{programId}/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> assignUser(@PathVariable int userId,@PathVariable int programId)
	{
		return userService.assignUser(userId,programId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/subjects/{subjectId}/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTheTeacher(@PathVariable int subjectId,@PathVariable int userId)
	{
		return userService.addSubjectToTheTeacher(subjectId,userId);
	}
	
	@GetMapping("/academic-programs/{programId}/user-roles/{role}/users")
	public  ResponseEntity<ResponseStructure<List<UserResponse>>> fetchUserByUserRole(@PathVariable int programId,@PathVariable String role)
            
	{
		return userService.fetchUserByUserRole(programId,role);
	}
}
