package com.school.sba.Entity;

import java.util.ArrayList;
import java.util.List;

import com.school.sba.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(unique = true)
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	 
	private Boolean isDeleted;
	
	@Column(unique = true)
	private String email;
	private UserRole userRole;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private School school;
	
	@ManyToMany
	private List<AcademicProgram> prog=new ArrayList<AcademicProgram>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Subject subject;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<ClassHour> classHours;
	
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public List<AcademicProgram> getProg() {
		return prog;
	}
	public void setProg(List<AcademicProgram> prog) {
		this.prog = prog;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public List<ClassHour> getClassHours() {
		return classHours;
	}
	public void setClassHours(List<ClassHour> classHours) {
		this.classHours = classHours;
	}
	
}
