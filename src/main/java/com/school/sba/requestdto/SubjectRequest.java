package com.school.sba.requestdto;

import java.util.List;

public class SubjectRequest {
	private List<String >  subjectName;

	public List<String> getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(List<String> subjectName) {
		this.subjectName = subjectName;
	}

	public SubjectRequest(List<String> subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public SubjectRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
