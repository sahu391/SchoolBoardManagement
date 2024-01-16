package com.school.sba.ServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.School;
import com.school.sba.Exception.SchoolNotFoundException;
import com.school.sba.Repository.SchoolRepo;
import com.school.sba.Service.SchoolService;
import com.school.sba.util.ResponseStructure;

@Service
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	private SchoolRepo repo;
	
	@Override
	public ResponseEntity<ResponseStructure<School>> saveSchool(School school) {
	School s1=repo.save(school);
	
	ResponseStructure<School> rs= new ResponseStructure();
	rs.setStatus(HttpStatus.CREATED.value());
	rs.setMessage("School object created successfully!!");
	rs.setData(s1);
	
	return new ResponseEntity<ResponseStructure<School>>(rs,HttpStatus.CREATED);
		
	}

	@Override
	public ResponseEntity<ResponseStructure<School>> findSchool(int schoolId) {
		Optional<School> s=repo.findById(schoolId);
		
		if(s.isPresent()) {
			School s1=s.get();
			ResponseStructure<School> rs= new ResponseStructure();
			rs.setStatus(HttpStatus.FOUND.value());
			rs.setMessage("School object fetched successfully!!");
			rs.setData(s1);
			return new ResponseEntity<ResponseStructure<School>>(rs,HttpStatus.FOUND);
	}else {
		throw new SchoolNotFoundException("School not Found!!");
	}
	}

	@Override
	public ResponseEntity<ResponseStructure<School>> updateSchool(int schoolId, School updatedschool) {
		Optional<School> optional=repo.findById(schoolId);
		
		if(optional.isPresent()) {
			School existingSchool=optional.get();
		    updatedschool.setSchoolId(existingSchool.getSchoolId());	
		    School school=repo.save(updatedschool);
		    
		    ResponseStructure<School> rs= new ResponseStructure();
			rs.setStatus(HttpStatus.OK.value());
			rs.setMessage("School object updated successfully!!");
			rs.setData(school);
			return new ResponseEntity<ResponseStructure<School>>(rs,HttpStatus.OK);
		}else {
			throw new SchoolNotFoundException("School not Found!!");
		}
		
	}

	@Override
	public ResponseEntity<ResponseStructure<School>> deleteSchool(int schoolId) {
		Optional<School> optional=repo.findById(schoolId);
		if(optional.isPresent()) {
			School school=optional.get();
			repo.delete(school);
			ResponseStructure<School> rs= new ResponseStructure();
			rs.setStatus(HttpStatus.OK.value());
			rs.setMessage("School object deleted successfully!!");
			rs.setData(school);
			return new ResponseEntity<ResponseStructure<School>>(rs,HttpStatus.OK);
		}
		else {
			throw new SchoolNotFoundException("School not Found!!");
		}
		}

}
