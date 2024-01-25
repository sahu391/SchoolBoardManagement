package com.school.sba.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.Subject;
import com.school.sba.Exception.AcademicProgamNotFoundException;
import com.school.sba.Exception.IllegalException;
import com.school.sba.Repository.AcademicProgRepo;
import com.school.sba.Repository.SubjectRepo;
import com.school.sba.Service.SubjectService;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.util.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService{
	
	@Autowired
	private AcademicProgRepo academicrepo;
	@Autowired
	private SubjectRepo subjectrepo;
	@Autowired
	private ResponseStructure<AcademicProgResponse> structure;
	@Autowired
	private ResponseStructure<List<SubjectResponse>>  responsestructure;
	@Autowired
	private AcademicProgServiceImpl academicProgramServiceImpl;
	
	
	
	private SubjectResponse mapToSubjectResponse(Subject subject)
	{
		SubjectResponse res =new SubjectResponse();
		res.setSubjectName(subject.getSubjectName());
		res.setSubjectId(subject.getSubjectId());
		return res;

	}

	
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> addSubjects(int programId,
			SubjectRequest subjectRequest)
	{
		return academicrepo.findById(programId).map(program->{ //found academic program
			List<Subject>subjects= (program.getSubject()!= null)?program.getSubject(): new ArrayList<Subject>();

			//to add new subjects that are specified by the client
			subjectRequest.getSubjectName().forEach(name->{
				boolean isPresent =false;
				for(Subject subject:subjects) {
					isPresent = (name.equalsIgnoreCase(subject.getSubjectName()))?true:false;
					if(isPresent)break;
				}
				if(!isPresent)
					subjects.add(subjectrepo.findBySubjectName(name)
						.orElseGet(()->subjectrepo.save(Subject.builder().subjectName(name).build())));
			});
			
			//to remove the subjects that are not specified by the client
			List<Subject>toBeRemoved= new ArrayList<Subject>();
			subjects.forEach(subject->{
				boolean isPresent = false;
				for(String name:subjectRequest.getSubjectName()) {
					isPresent=(subject.getSubjectName().equalsIgnoreCase(name))?true :false;
					if(isPresent)break;
				}
				if(!isPresent)toBeRemoved.add(subject);
			});
			subjects.removeAll(toBeRemoved);


			program.setSubject(subjects);//set subjects list to the academic program
			academicrepo.save(program);//saving updated program to the database
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("updated the subject list to academic program");
			structure.setData(academicProgramServiceImpl.mapToAcademicProgramResponse(program));
			return new ResponseEntity<ResponseStructure<AcademicProgResponse>>(structure,HttpStatus.CREATED);
		}).orElseThrow(()-> new AcademicProgamNotFoundException("AcademicProgram not found"));

	
	}


	@Override
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects() {
		List<Subject> findAll = subjectrepo.findAll();

		List<SubjectResponse> collect = findAll.stream()
				.map(u->mapToSubjectResponse(u))
				.collect(Collectors.toList());



		responsestructure.setStatus(HttpStatus.FOUND.value());
		responsestructure.setMessage(" sujects found successfully ");
		responsestructure.setData(collect);

		return new ResponseEntity<ResponseStructure<List<SubjectResponse>>>(responsestructure,HttpStatus.FOUND);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgResponse>> updateSubjects(int programId,
			SubjectRequest subjectreq) {
		
		return null;
	}
	
}



