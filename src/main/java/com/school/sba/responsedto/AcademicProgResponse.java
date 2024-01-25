package com.school.sba.responsedto;

import java.time.LocalDate;

import com.school.sba.enums.ProgramType;

public class AcademicProgResponse {
	
	private int programId;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private ProgramType programType;
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
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
	public AcademicProgResponse(int programId, String programName, LocalDate beginsAt, LocalDate endsAt,
			ProgramType programType) {
		super();
		this.programId = programId;
		this.programName = programName;
		this.beginsAt = beginsAt;
		this.endsAt = endsAt;
		this.programType = programType;
	}
	public AcademicProgResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
