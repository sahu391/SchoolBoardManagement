package com.school.sba.responsedto;

import java.util.List;

public class SubjectResponse {
	private String subjectName;
	private int subjectId;
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public SubjectResponse(String subjectName, int subjectId) {
		super();
		this.subjectName = subjectName;
		this.subjectId = subjectId;
	}
	public SubjectResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
	
}
