package com.school.sba.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(@Valid UserRequest user);

	ResponseEntity<ResponseStructure<UserResponse>> findRegisterdUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> deleteRegisterdUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@Valid UserRequest user);

	ResponseEntity<ResponseStructure<UserResponse>> assignUser(int userId, int programId);

	ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTheTeacher(int subjectId, int userId);

	 ResponseEntity<ResponseStructure<List<UserResponse>>> fetchUserByUserRole(int programId,String role);

	

	

}
