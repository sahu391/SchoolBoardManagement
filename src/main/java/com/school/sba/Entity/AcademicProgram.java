package com.school.sba.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.school.sba.enums.ProgramType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicProgram {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	private ProgramType programType;
	private boolean isDeleted;
	
	@ManyToOne
	private School school;
	
	@ManyToMany( mappedBy = "prog",fetch  = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<User> user=new ArrayList<User>();
	
	@ManyToMany
	private List<Subject> subject;
	
	@OneToMany(mappedBy = "proglist", fetch = FetchType.EAGER)
	private List<ClassHour> classHourList;;
	
	
	
	
	public List<Subject> getSubject() {
		return subject;
	}
	public void setSubject(List<Subject>  subject) {
		this.subject = subject;
	}
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
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
	
	
	
	
}
