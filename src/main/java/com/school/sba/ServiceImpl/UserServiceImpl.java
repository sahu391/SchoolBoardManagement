package com.school.sba.ServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Service.UserService;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.requestdto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@Valid UserRequest user) {
		// TODO Auto-generated method stub
		return null;
	}

}
