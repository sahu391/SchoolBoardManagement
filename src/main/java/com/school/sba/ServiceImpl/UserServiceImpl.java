package com.school.sba.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.AcademicProgram;
import com.school.sba.Entity.School;
import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;
import com.school.sba.Exception.AcademicProgamNotFoundException;
import com.school.sba.Exception.AdmineCannotBeAssignedToAcademicProgram;
import com.school.sba.Exception.DuplicateAdminException;
import com.school.sba.Exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sba.Exception.SchoolNotFoundException;
import com.school.sba.Exception.SubjectNotFoundException;
import com.school.sba.Exception.TeacherNotFoundException;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.AcademicProgRepo;
import com.school.sba.Repository.SubjectRepo;
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
	private AcademicProgRepo progrepo;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private SubjectRepo subjectrepo;
	
	@Autowired
	private ResponseStructure<UserResponse> structure;
	

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private User mapToUser(UserRequest userrequest) {
		User user = new User();
		user.setUserName(userrequest.getUserName());
		user.setFirstName(userrequest.getFirstName());
		user.setLastName(userrequest.getLastName());
		user.setEmail(userrequest.getEmail());
		user.setContactNo(userrequest.getContactNo());
		user.setUserRole(userrequest.getUserRole());
		user.setPassword(passwordEncoder.encode(userrequest.getPassword()));
		
		
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
       
		
		return userresponse;
	}
	
	private School findAdminSchool() {
		User admin =userrepo.findByUserRole(UserRole.ADMIN);
		
		if(admin!=null) {
			return admin.getSchool();
		}
		else {
			throw new SchoolNotFoundException("School not found");
		}
	}
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@Valid UserRequest userrequest) {
		
		if (userrepo.existsByUserRole(UserRole.ADMIN)) { 
			throw new DuplicateAdminException("No Duplication of Admin Allowed");
        
		}
		User user = userrepo.save(mapToUser(userrequest));
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("User Registered Successfully");
	    structure.setData(mapToUserResponse(user));
	    
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);

}
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser( UserRequest userrequest) {
		School adminSchool=findAdminSchool();
	
		if (userrequest.getUserRole() == UserRole.ADMIN && userrepo.existsByUserRole(UserRole.ADMIN)) { 
			throw new DuplicateAdminException("No Duplication of Admin Allowed");
        }
		else{
		User user = userrepo.save(mapToUser(userrequest));	
		if (userrequest.getUserRole() == UserRole.TEACHER || userrequest.getUserRole() ==UserRole.STUDENT) {
		user.setSchool(adminSchool);
		}	
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("User Registered Successfully");
	    structure.setData(mapToUserResponse(user));
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);
		}	
}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findRegisterdUser(int userId) {
         User existinguser=userrepo.findById(userId)
        		 .orElseThrow(()->new UserNotFoundException("user not Found!!"));
        	
		
		structure.setStatus(HttpStatus.FOUND.value());
		structure.setMessage("RegisteredUser Data Fetched Successfully");
	    structure.setData(mapToUserResponse(existinguser));
		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteRegisterdUser(int userId) {
		User user = userrepo.findById(userId)
				.orElseThrow(()->new UserNotFoundException("user not found"));

		if(user.getIsDeleted()==true)
		{
			throw new UserNotFoundException("user not found");
		}

		user.setIsDeleted(false);
		User save = userrepo.save(user);
		
		


		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("user deleted successfully");
	structure.setData(mapToUserResponse(save));


		return new  ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> assignUser(int userId, int programId) {
		User user = userrepo.findById(userId)
				.orElseThrow(()-> new UserNotFoundException("user not found"));

		AcademicProgram academicProgram = progrepo.findById(programId)
				.orElseThrow(()->new AcademicProgamNotFoundException("AcademicProgam not found"));

		if(user.getUserRole().equals(UserRole.ADMIN))
		{
			throw new AdmineCannotBeAssignedToAcademicProgram("admin cannot assign");
		}
		else
		{
			user.getProg().add(academicProgram);
			userrepo.save(user);
			academicProgram.getUser().add(user);
			progrepo.save(academicProgram );

			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("user associated with academic program successfully");
			structure.setData(mapToUserResponse(user));


			return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTheTeacher(int subjectId, int userId) {
		{
			Subject subject = subjectrepo.findById(subjectId)
					.orElseThrow(()-> new SubjectNotFoundException("subject not found"));

			User user = userrepo.findById(userId)
					.orElseThrow(()-> new UserNotFoundException("user not found"));

			if(user.getUserRole().equals(UserRole.TEACHER))
			{

				user.setSubject(subject);
				userrepo.save(user);

				structure.setStatus(HttpStatus.OK.value());
				structure.setMessage("subject added to the teacher successfully");
				structure.setData(mapToUserResponse(user));

				return new ResponseEntity<ResponseStructure<UserResponse>>(structure,HttpStatus.OK);

			}
			else
			{
				throw new OnlyTeacherCanBeAssignedToSubjectException("user is not a teacher");
			}
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addTeacherToProgram(int userId, Subject subject) {
		
		return null;
	}

	

	
	

}
