package com.school.sba.Service;

import org.springframework.http.ResponseEntity;

import com.school.sba.Entity.User;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(@Valid UserRequest user);

	ResponseEntity<ResponseStructure<UserResponse>> findRegisterdUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> deleteRegisterdUser(int userId);

}
