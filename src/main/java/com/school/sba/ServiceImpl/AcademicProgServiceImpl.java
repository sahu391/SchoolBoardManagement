package com.school.sba.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.Entity.AcademicProgram;
import com.school.sba.Entity.School;
import com.school.sba.Entity.Subject;
import com.school.sba.Exception.SchoolNotFoundException;
import com.school.sba.Repository.AcademicProgRepo;
import com.school.sba.Repository.SchoolRepo;
import com.school.sba.Service.AcademicProgService;
import com.school.sba.requestdto.AcademicProgRequest;
import com.school.sba.responsedto.AcademicProgResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.util.ResponseStructure;

@Service
public class AcademicProgServiceImpl implements AcademicProgService{

	
	@Autowired
	private AcademicProgRepo AcademicProgRepo;


	@Autowired
	private SchoolRepo SchoolRepo;


	@Autowired
	private ResponseStructure<AcademicProgResponse> responseStructure;

	@Autowired
	private ResponseStructure<List<AcademicProgResponse>> ListResponseStructure;

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

	
	
}
