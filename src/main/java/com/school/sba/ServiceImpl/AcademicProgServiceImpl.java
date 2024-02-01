package com.school.sba.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.AcademicProgram;
import com.school.sba.Entity.School;
import com.school.sba.Entity.Subject;
import com.school.sba.Entity.User;
import com.school.sba.Exception.AcademicProgamNotFoundException;
import com.school.sba.Exception.SchoolNotFoundException;
import com.school.sba.Exception.SubjectNotFoundException;
import com.school.sba.Exception.TeacherNotFoundException;
import com.school.sba.Exception.UserNotFoundException;
import com.school.sba.Repository.AcademicProgRepo;
import com.school.sba.Repository.ClassHourRepo;
import com.school.sba.Repository.SchoolRepo;
import com.school.sba.Repository.SubjectRepo;
import com.school.sba.Repository.UserRepository;
import com.school.sba.Service.AcademicProgService;
import com.school.sba.enums.UserRole;
import com.school.sba.requestdto.AcademicProgRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

@Service
public class AcademicProgServiceImpl implements AcademicProgService{

	
	@Autowired
	private AcademicProgRepo AcademicProgRepo;
	@Autowired
	private SchoolRepo SchoolRepo;
	@Autowired
	private SubjectRepo subjectrepo;
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private ClassHourRepo classhourrepo;
	@Autowired
	private ResponseStructure<AcademicProgResponse> responseStructure;
	@Autowired
	private ResponseStructure<List<AcademicProgResponse>> ListResponseStructure;
	
	
	
//	 public Subject getSubjectByName(String subjectName) {
//	        return subjectrepo.findBySubjectName(subjectName)
//	                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with name: " + subjectName));
//	    }
//	 
//	 public User getUserByUserName(String userName) {
//	        return userrepo.findByUserName(userName)
//	                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));
//	    }
	 
	 
	public AcademicProgram mapToAcademicProgram(AcademicProgRequest request) {
		AcademicProgram res =new AcademicProgram();
		res.setProgramName(request.getProgramName());
		res.setProgramType(request.getProgramType());
		res.setBeginsAt(request.getBeginsAt());
		res.setEndsAt(request.getEndsAt());
		return res;
	}

	public  static AcademicProgResponse mapToAcademicProgramResponse(AcademicProgram program) {
		
		AcademicProgResponse res=new AcademicProgResponse();
		res.setProgramId(program.getProgramId());
		res.setProgramName(program.getProgramName());
		res.setProgramType(program.getProgramType());
		res.setBeginsAt(program.getBeginsAt());
		res.setEndsAt(program.getEndsAt());
		return res;
	}

	
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> saveAcademicProgram(int schoolId,
			AcademicProgRequest academicProgramRequest) {

		School school = SchoolRepo.findById(schoolId)
				.orElseThrow(()-> new SchoolNotFoundException("school not found"));

		AcademicProgram academicProgram = AcademicProgRepo.save(mapToAcademicProgram(academicProgramRequest));
		school.getProg().add(academicProgram);
		academicProgram.setSchool(school);

		SchoolRepo.save(school);
		AcademicProgRepo.save(academicProgram );

		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("AcademicProgram saved successfully");
		responseStructure.setData(mapToAcademicProgramResponse(academicProgram));

		return new ResponseEntity<ResponseStructure<AcademicProgResponse>>(responseStructure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<AcademicProgResponse>>> findAcademicProgram(int schoolId) {
		{
			SchoolRepo.findById(schoolId)
			.orElseThrow(()-> new SchoolNotFoundException("school not found"));

			List<AcademicProgram> findAll = AcademicProgRepo.findAll();
			List<AcademicProgResponse> collect = findAll.stream()
					.map(u->mapToAcademicProgramResponse(u))
					.collect(Collectors.toList());

			if(findAll.isEmpty())
			{
				ListResponseStructure.setStatus(HttpStatus.FOUND.value());
				ListResponseStructure.setMessage("AcademicProgram is empty");
				ListResponseStructure.setData(collect);
			}

			ListResponseStructure.setStatus(HttpStatus.FOUND.value());
			ListResponseStructure.setMessage("AcademicProgram found successfully");
			ListResponseStructure.setData(collect);

			return new ResponseEntity<ResponseStructure<List<AcademicProgResponse>>>(ListResponseStructure,HttpStatus.FOUND);
		}
		
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> addTeacherToProgram(int programId,
			String userName,String subjectName)
	{
		 AcademicProgram academicProgram = AcademicProgRepo.findById(programId)
	                .orElseThrow(() -> new AcademicProgamNotFoundException("AcademicProgram not found with id: " + programId));

	        Subject subject = subjectrepo.findBySubjectName(subjectName)
	                .orElseThrow(() -> new SubjectNotFoundException("Subject not found with name: " + subjectName));
	        User user = userrepo.findByUserName(userName)
	                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));

	        if (user.getUserRole() != UserRole.TEACHER) {
	            throw new TeacherNotFoundException("User " + user.getUserName() + " is not a teacher.");
	        }

	        if (!user.getSubject().equals(subject)) {
	            throw new TeacherNotFoundException("Teacher " + user.getUserName() +
	                    " is not qualified to teach the subject " + subject.getSubjectName());
	        }
	        
	        	academicProgram.getUser().add(user);// Add the teacher to the academic program
	        AcademicProgRepo.save(academicProgram);
	        
	        responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Teacher added to each subject successfully");
			responseStructure.setData(mapToAcademicProgramResponse(academicProgram));
			return new ResponseEntity<ResponseStructure<AcademicProgResponse>>(responseStructure,HttpStatus.CREATED);

		}
	//soft delete
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> deleteByProgramId(int programId) {
		AcademicProgram program = AcademicProgRepo.findById(programId)
				.orElseThrow(()->new AcademicProgamNotFoundException("No Academic Program in the entered programId "+ programId));

		
		 program.setDeleted(true);
		 AcademicProgRepo.save(program);
		
	   responseStructure.setStatus(HttpStatus.OK.value());
	    responseStructure.setMessage("Academic  program deleted successfully");
	    responseStructure.setData(mapToAcademicProgramResponse(program));


		return new  ResponseEntity<ResponseStructure<AcademicProgResponse>>(responseStructure,HttpStatus.OK);
	}

	
	public void deleteAcademicProgramIfDeleted() {
	    for (AcademicProgram academicProgram : AcademicProgRepo.findAll()) {
	        if (academicProgram.isDeleted()) {
	            // Deleting all the Class Hours related to the Academic Program
	            if (!academicProgram.getClassHourList().isEmpty()) {
	            	classhourrepo.deleteAll(academicProgram.getClassHourList());
	            }

	            // Unlinking Academic Program from Users
	            for (User user : academicProgram.getUser()) {
	                user.getProg().remove(academicProgram);
	            }
	            userrepo.saveAll(academicProgram.getUser());

	            // Finally, delete the Academic Program
	            AcademicProgRepo.delete(academicProgram);
	        }
	    }
	}
}




