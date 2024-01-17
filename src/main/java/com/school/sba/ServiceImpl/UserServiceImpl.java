package com.school.sba.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.User;
import com.school.sba.Exception.DuplicateAdminException;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.UserService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ResponseStructure<UserResponse> structure;
	
	
	private User mapToUser(UserRequest userrequest) {
		User user = new User();
		user.setUserName(userrequest.getUserName());
		user.setFirstName(userrequest.getFirstName());
		user.setLastName(userrequest.getLastName());
		user.setEmail(userrequest.getEmail());
		user.setContactNo(userrequest.getContactNo());
		user.setUserRole(userrequest.getUserRole());
		user.setPassword(userrequest.getPassword());
		
		
		return user;
	}

	private UserResponse mapToUserResponse(User user) {
		UserResponse userresponse = new UserResponse();
		
		userresponse.setUserId(user.getUserId());
		userresponse.setUserName(user.getUserName());
		userresponse.setFirstName(user.getFirstName());
		userresponse.setLastName(user.getLastName());
		userresponse.setEmail(user.getEmail());
		userresponse.setContactNo(user.getContactNo());
       userresponse.setUserRole(user.getUserRole());
       userresponse.setIsDeleted(user.getIsDeleted());
		
		return userresponse;
	}
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser( UserRequest userrequest) {
		if (userrequest.getUserRole() == UserRole.ADMIN && userRepo.existsByUserRole(UserRole.ADMIN)) { 
			throw new DuplicateAdminException("No Duplication of Admin Allowed");
        
		}
		User user = userRepo.save(mapToUser(userrequest));
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("User Registered Successfully");
	    structure.setData(mapToUserResponse(user));
	    
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findRegisterdUser(int userId) {
         User existinguser=userRepo.findById(userId)
        		 .orElseThrow(()->new UserNotFoundException("user not Found!!"));
        	
		
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("RegisteredUser Data Fetched Successfully");
	    structure.setData(mapToUserResponse(existinguser));
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteRegisterdUser(int userId) {
		User existinguser=userRepo.findById(userId).orElseThrow(()-> new UserNotFoundException("user not Found!!"));
		
		userRepo.delete(existinguser);
		existinguser.setIsDeleted(true);
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("RegisteredUser Data deleted Successfully");
	    structure.setData(mapToUserResponse(existinguser));
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);
	}
	

}
