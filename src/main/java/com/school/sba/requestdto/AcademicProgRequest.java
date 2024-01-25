package com.school.sba.requestdto;

import java.time.LocalDate;

import com.school.sba.Entity.Subject;
import com.school.sba.enums.ProgramType;

import jakarta.persistence.ManyToMany;

public class AcademicProgRequest {
	
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private ProgramType programType;
	
	@ManyToMany
	private Subject subject;
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public LocalDate getBeginsAt() {
		return beginsAt;
	}
	public void setBeginsAt(LocalDate beginsAt) {
		this.beginsAt = beginsAt;
	}
	public LocalDate getEndsAt() {
		return endsAt;
	}
	public void setEndsAt(LocalDate endsAt) {
		this.endsAt = endsAt;
	}
	public ProgramType getProgramType() {
		return programType;
	}
	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}
	public AcademicProgRequest(String programName, LocalDate beginsAt, LocalDate endsAt, ProgramType programType) {
		super();
		this.programName = programName;
		this.beginsAt = beginsAt;
		this.endsAt = endsAt;
		this.programType = programType;
	}
	public AcademicProgRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
