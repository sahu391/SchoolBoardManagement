package com.school.sba.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.School;
import com.school.sba.Entity.User;
import com.school.sba.Exception.DuplicateAdminException;
import com.school.sba.Exception.InvalidUserRoleException;
import com.school.sba.Exception.SchoolNotFoundException;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.SchoolRepo;
import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.SchoolService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

@Service
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolRepo repo;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private ResponseStructure<SchoolResponse> structure;
	
	
	private School mapToSchool(SchoolRequest schoolrequest) {
		School school=new School();
		
		school.setSchoolName(schoolrequest.getSchoolName());
		school.setContactNo(schoolrequest.getContactNo());
		school.setAddress(schoolrequest.getAddress());
		school.setEmailId(schoolrequest.getEmailId());
		return school;
	}
	
	
	private SchoolResponse mapToSchoolResponse(School school) {
		SchoolResponse schoolresponse = new SchoolResponse();
		
		schoolresponse.setSchoolId(school.getSchoolId());
		schoolresponse.setSchoolName(school.getSchoolName());
		schoolresponse.setContactNo(school.getContactNo());
		schoolresponse.setAddress(school.getAddress());
		schoolresponse.setEmailId(school.getEmailId());
		
		return schoolresponse;
	}
	
	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(int userId,SchoolRequest schoolreq) {
		return userrepo.findById(userId).map(u->{
			if(u.getUserRole().equals(UserRole.ADMIN)) {
				if(u.getSchool()==null) {
					School school =mapToSchool(schoolreq);
					school=repo.save(school);
					u.setSchool(school);
					userrepo.save(u);
					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("school saved successfully");
					structure.setData(mapToSchoolResponse(school));
					return new ResponseEntity<ResponseStructure<SchoolResponse>>(structure,HttpStatus.CREATED);
					
				}else
					throw new DuplicateAdminException("already school exists");
				
			}else
				throw new DuplicateAdminException("admin does not exist");
		}).orElseThrow(()-> new UserNotFoundException("failed to save the data"));
		
	}
}
